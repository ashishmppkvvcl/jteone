package com.mppkvvcl.jteone.service.process;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillTemplate;
import com.mppkvvcl.jteone.dtos.templates.pdfbill.*;
import com.mppkvvcl.jteone.service.daos.mis.BillMessageService;
import com.mppkvvcl.jteone.service.daos.mis.DiscomService;
import com.mppkvvcl.jteone.service.daos.ngb.*;
import com.mppkvvcl.jteone.utility.GlobalConstant;
import com.mppkvvcl.jteone.utility.GlobalUtility;
import com.mppkvvcl.jteone.utility.UrjasCypher;
import com.mppkvvcl.misdao.dtos.BillIdentifierDTO;
import com.mppkvvcl.misdao.interfaces.DiscomInterface;
import com.mppkvvcl.ngbdao.interfaces.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class BillNgbTemplateService {
    private static final Logger log = LoggerFactory.getLogger(BillNgbTemplateService.class);

    @Autowired
    private BillService billService;

    @Autowired
    private BillTypeCodeService billTypeCodeService;

    @Autowired
    private AdjustmentTypeService adjustmentTypeService;

    @Autowired
    private ConsumerInformationService consumerInformationService;

    @Autowired
    private ConsumerConnectionInformationService consumerConnectionInformationService;

    @Autowired
    private ConsumerMiscellaneousInformationService consumerMiscellaneousInformationService;

    @Autowired
    private EmployeeConnectionMappingService employeeConnectionMappingService;

    @Autowired
    private ConsumerPanchnamaInformationService consumerPanchnamaInformationService;

    @Autowired
    private PanchnamaPaymentInformationService panchnamaPaymentInformationService;

    @Autowired
    private BillMessageService billMessageService;

    @Autowired
    private EEDivisionMappingService eeDivisionMappingService;

    @Autowired
    private CircleECGRFInformationService circleECGRFInformationService;

    @Autowired
    private MeterMasterService meterMasterService;

    @Autowired
    private ReadMasterService readMasterService;

    @Autowired
    private ReadMasterTODService readMasterTODService;

    @Autowired
    private ReadMasterExportService readMasterExportService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SecurityDepositService securityDepositService;

    @Autowired
    private MeterReaderInformationService meterReaderInformationService;

    @Autowired
    @Qualifier("ngbAdjustmentService")
    private AdjustmentService ngbAdjustmentService;

    @Autowired
    private BillCalculationLineService billCalculationLineService;

    @Autowired
    private BillTODService billTODService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    @Qualifier("misDiscomService")
    private DiscomService discomService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private ZoneService zoneService;

    public BillTemplate prepareBillTemplateObject(final BillIdentifierDTO billIdentifierDTO, final String templateVersion, final MessageDTO messageDTO) {
        if (billIdentifierDTO == null || StringUtils.isEmpty(templateVersion))
            return null;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String consumerPreferredLanguage = billIdentifierDTO.getConsumerPreferredLanguage();
        final BillTemplate templateData = new BillTemplate();

        Long billId = setBillSummaryAndInformation(templateData, billIdentifierDTO, messageDTO);
        if (billId == null || StringUtils.isNotEmpty(messageDTO.getMessage())) return null;

        setTemplateInformation(templateData, templateVersion);
        setCompanyInformation(templateData);

        setPaymentQRInformation(templateData);

        final DivisionInterface division = setConsumerAndConnectionInformation(templateData, consumerNo);
        setContactInformation(templateData, division.getId());
        setBillMessage(templateData, consumerPreferredLanguage);
        setCircleECGRFInformation(templateData, division.getCircleId());

        setReadAndTODAndMeterReplacementAndVNMGNMChildInformation(templateData, billIdentifierDTO, billId);
        setReadAndPaymentHistory(templateData);
        setBillCalculationAndAdjustmentInformation(templateData, billId);

        return templateData;
    }


    /*************************************
     * NGB BIll Template Data Preparation
     *************************************/

    //Setting Bill Summary and Information
    private Long setBillSummaryAndInformation(final BillTemplate templateData, final BillIdentifierDTO billIdentifierDTO, final MessageDTO messageDTO) {
        if (templateData == null || messageDTO == null) return null;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();

        final BillInterface bill = billService.getByConsumerNoAndBillMonthAndDeleted(consumerNo, billMonth, false);
        if (bill == null) {
            messageDTO.setMessage("Bill not found for " + consumerNo + ", " + billMonth);
            log.info(messageDTO.getMessage());
            return null;
        }

        final String billType = billTypeCodeService.getDescriptionByCode(bill.getBillTypeCode());
        LocalDate pdcDate = null;
        if (BillTypeCodeInterface.CODE_LB.equals(bill.getBillTypeCode()) || BillTypeCodeInterface.CODE_PDC.equals(bill.getBillTypeCode())) {
            final ConsumerMiscellaneousInformationInterface pdcMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_PDC_BILL_MONTH);
            if (pdcMiscellaneous != null) pdcDate = pdcMiscellaneous.getEffectiveEndDate();
        }

        String billBasis = null;
        if (billIdentifierDTO.isCC4()) billBasis = "Revised Bill";
        final SecurityDepositInterface securityDeposit = securityDepositService.getConsumerNoAndEffectiveStartDateAndEffectiveEndDateOrderByIdAsc(consumerNo, bill.getBillDate());
        final BigDecimal sdHeld = (securityDeposit != null) ? securityDeposit.getAmount() : BigDecimal.ZERO;
        final BigDecimal wheelingCharges = ngbAdjustmentService.getAmountByConsumerNoAndCodeAndPostingBillMonthAndApprovalStatusAndPostedAndDeleted(consumerNo, AdjustmentInterface.WHEELING_CHARGES, billMonth, GlobalConstant.APPROVED, true, false);
        final BigDecimal todSurchargeAndRebate = GlobalUtility.add(bill.getOtherAdjustment(), bill.getXrayFixedCharge());

        Long unpostedPayment = paymentService.getAmountByConsumerNoAndPostingBillMonthAndPostedDeleted(consumerNo, billMonth, false, false);
        BigDecimal rcdcAmount = null;
        if (unpostedPayment == null) unpostedPayment = 0L;
        //121 RC/DC Management Changes
        if (unpostedPayment > 340L) {
            rcdcAmount = ngbAdjustmentService.getAmountByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeleted(consumerNo, AdjustmentInterface.ADJUSTMENT_CODE_SMART_METER_RC_DC_CHARGE, GlobalConstant.APPROVED, false, false);
            if (rcdcAmount != null && rcdcAmount.compareTo(BigDecimal.ZERO) > 0) {
                unpostedPayment = unpostedPayment - rcdcAmount.longValue();
            }
        }

        //Vigilance Changes
        BigDecimal totalPanchanamaBillAmount = BigDecimal.ZERO;
        BigDecimal totalPanchnamaPaymentAmount = BigDecimal.ZERO;
        BigDecimal vigilenceEnergyCharge = BigDecimal.ZERO;
        BigDecimal vigilenceSurcharge = BigDecimal.ZERO;
        final List<ConsumerPanchnamaInformationInterface> consumerPanchnamaInformationList = consumerPanchnamaInformationService.getByConsumerNoAndPostedAndDeleted(consumerNo, false, false);
        if (!GlobalUtility.isEmpty(consumerPanchnamaInformationList)) {
            for (ConsumerPanchnamaInformationInterface consumerPanchnamaInformation : consumerPanchnamaInformationList) {
                if (consumerPanchnamaInformation.getTotalBill() != null)
                    totalPanchanamaBillAmount = totalPanchanamaBillAmount.add(consumerPanchnamaInformation.getTotalBill());
                final BigDecimal panchnamaPayment = panchnamaPaymentInformationService.getByConsumerNoAndPanchnamaNoAndPanchnamaDateAndDeleted(consumerNo, consumerPanchnamaInformation.getPanchnamaNo(), consumerPanchnamaInformation.getPanchnamaDate(), false);
                if (panchnamaPayment != null)
                    totalPanchnamaPaymentAmount = totalPanchnamaPaymentAmount.add(panchnamaPayment);
                if (consumerPanchnamaInformation.getEnergyCharge() != null)
                    vigilenceEnergyCharge = vigilenceEnergyCharge.add(consumerPanchnamaInformation.getEnergyCharge());
                if (consumerPanchnamaInformation.getSurchargeDemanded() != null)
                    vigilenceSurcharge = vigilenceSurcharge.add(consumerPanchnamaInformation.getSurchargeDemanded());
            }

            if (totalPanchnamaPaymentAmount.compareTo(BigDecimal.ZERO) > 0) {
                final long panchanamaPaymentAmount = totalPanchnamaPaymentAmount.longValue();
                unpostedPayment = unpostedPayment + panchanamaPaymentAmount;
            }

            setVigilanceInformation(templateData, consumerPanchnamaInformationList);
        }

        // Payable Calculation
        final BigDecimal deduction = new BigDecimal(unpostedPayment);
        BigDecimal payableAmount = GlobalUtility.subtract(bill.getNetBill(), deduction);
        payableAmount = GlobalUtility.add(payableAmount, totalPanchanamaBillAmount).setScale(2, RoundingMode.HALF_UP);
        final BigDecimal payableBeforeDueDate = payableAmount;
        final BigDecimal payableAfterDueDate = GlobalUtility.add(payableBeforeDueDate, bill.getCurrentBillSurcharge());
        if (LocalDate.now().isAfter(bill.getDueDate())) payableAmount = payableAfterDueDate;

        final BillSummary billSummary = new BillSummary(String.valueOf(bill.getId()), bill.getBillMonth(), payableAmount, payableBeforeDueDate, payableAfterDueDate, bill.getCurrentBill(), bill.getArrear().add(bill.getSurchargeDemanded()), null, null,
                bill.getDueDate(), bill.getChequeDueDate(), bill.getBilledUnit(), null, null, bill.getBillDate(), billType, billBasis, null, null, sdHeld, bill.getAsdArrear(), pdcDate);

        final BigDecimal subTotalOne = GlobalUtility.add(bill.getEnergyCharge(), bill.getFcaCharge(), bill.getFixedCharge());
        final BigDecimal subTotalTwo = GlobalUtility.add(bill.getElectricityDuty(), bill.getCcbAdjustment());
        final BigDecimal penalCharge = GlobalUtility.add(bill.getAdditionalFixedCharges1(), bill.getAdditionalFixedCharges2());
        final BigDecimal subTotalThree = GlobalUtility.add(penalCharge, bill.getPfCharge(), bill.getAsdInstallment(), todSurchargeAndRebate, bill.getSdInterest(), bill.getLoadFactorIncentive(), bill.getLockCredit(), bill.getEmployeeRebate(), bill.getPrepaidMeterRebate(),
                bill.getOnlinePaymentRebate(), bill.getPromptPaymentIncentive(), bill.getAdvancePaymentIncentive(), bill.getDemandSideIncentive(), wheelingCharges);
        final BigDecimal subTotalFour = bill.getSubsidy();
        final BillInformation billInformation = new BillInformation(bill.getEnergyCharge(), bill.getFcaCharge(), bill.getFixedCharge(), subTotalOne, bill.getElectricityDuty(), bill.getCcbAdjustment(), subTotalTwo,
                penalCharge, bill.getPfCharge(), bill.getAsdInstallment(), todSurchargeAndRebate, bill.getSdInterest(), bill.getLoadFactorIncentive(), bill.getLockCredit(), bill.getEmployeeRebate(), bill.getPrepaidMeterRebate(),
                bill.getOnlinePaymentRebate(), bill.getPromptPaymentIncentive(), bill.getAdvancePaymentIncentive(), bill.getDemandSideIncentive(), wheelingCharges, subTotalThree,
                bill.getSubsidy(), subTotalFour, bill.getArrear(), bill.getCumulativeSurcharge(), bill.getAsdArrear(), bill.getNetBill(), unpostedPayment, rcdcAmount,
                vigilenceEnergyCharge, vigilenceSurcharge, payableAmount, bill.getBilledPF(), bill.getBilledMD(), bill.getPreviousRead(), bill.getMeteredUnit());

        templateData.setBillSummary(billSummary);
        templateData.setBillInformation(billInformation);

        return bill.getId();
    }

    private void setPaymentQRInformation(final BillTemplate templateData) {
        if (templateData == null) return;

        final String consumerNo = templateData.getConsumerInformation().getConsumerNo();
        final String discomShortName = templateData.getCompanyInformation().getShortName();
        String link = "";
        if (GlobalConstant.DISCOM_MPWZ.equalsIgnoreCase(discomShortName)) {
            final String randomToken = UrjasCypher.RANDOM_TOKENS[new Random().ints(0, UrjasCypher.RANDOM_TOKENS.length).findAny().orElse(0)];
            final String urjasCypher = UrjasCypher.encryptAndEncode("N" + consumerNo + "|" + randomToken);
            link = StringUtils.isEmpty(urjasCypher) ? "" : "https://tinyurl.com/ebilwz?q=".concat(URLEncoder.encode(urjasCypher, StandardCharsets.UTF_8));
        } else if (GlobalConstant.DISCOM_MPCZ.equalsIgnoreCase(discomShortName)) {
            link = "https://py.mpcz.in/x?k=N".concat(consumerNo);
        } else if (GlobalConstant.DISCOM_MPEZ.equalsIgnoreCase(discomShortName)) {
            link = "https://tinyurl.com/ebillsez?ivrs=N".concat(consumerNo);
        }

        if (StringUtils.isNotEmpty(link)) {
            templateData.getBillSummary().setPaymentQuickResponseString(link);

            try {
                final String data = UrjasCypher.getEncryptedPublicURL(link);
                final BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(matrix, "png", bos);
                String paymentQRAsBase64 = Base64.getEncoder().encodeToString(bos.toByteArray());
                templateData.getBillSummary().setPaymentQuickResponseBase64(paymentQRAsBase64);
            } catch (WriterException | IOException e) {
                log.error("Error occurred while generating payment QR for {}", link);
            }
        }
    }

    private boolean isEligibleForTOD(final ConnectionInformation connectionInformation) {
        if (connectionInformation == null) return false;

        boolean isEligibleForTOD = false;
        if (ConsumerConnectionInformationInterface.TARIFF_CATEGORY_LV6.equals(connectionInformation.getTariffCategory())) {
            isEligibleForTOD = true;
        } else if (connectionInformation.getMeterAttribute() != null && MeterMasterInterface.METER_ATTRIBUTE_SMART.equals(connectionInformation.getMeterAttribute())
                && ConsumerConnectionInformationInterface.TARIFF_CATEGORIES_LV1_2_3_4.contains(connectionInformation.getTariffCategory())) {
            isEligibleForTOD = true;
        } else if (ConsumerConnectionInformationInterface.TARIFF_CATEGORIES_LV2_4.contains(connectionInformation.getTariffCategory()) && connectionInformation.getContractDemand() != null) {
            final BigDecimal contractDemandInKW = ConsumerConnectionInformationInterface.LOAD_UNIT_KW.equals(connectionInformation.getContractDemandUnit()) ? connectionInformation.getContractDemand()
                    : GlobalUtility.convertHPToKW(connectionInformation.getContractDemand()).setScale(0, RoundingMode.HALF_UP);
            if (contractDemandInKW != null && contractDemandInKW.compareTo(BigDecimal.TEN) > 0) {
                isEligibleForTOD = true;
            }
        }
        return isEligibleForTOD;
    }

    //Setting TOD Information
    private void setTODInformation(final BillTemplate templateData, final long billId, final long readMasterId) {
        if (templateData == null) return;
        final boolean isNetMeter = templateData.getConnectionInformation().isNetMeter();

        final ReadMasterTODInterface readMasterTOD = readMasterTODService.getByReadMasterId(readMasterId);
        final BillTODInterface billTOD = billTODService.getByBillId(billId);
        if (readMasterTOD != null && billTOD != null) {
            final TODInformation todInformation = new TODInformation();
            todInformation.setTotalUnit(GlobalUtility.add(readMasterTOD.getTod1TotalConsumption(), readMasterTOD.getTod2TotalConsumption(), readMasterTOD.getTod3TotalConsumption(), readMasterTOD.getTod4TotalConsumption()));
            todInformation.setTotalUnit(GlobalUtility.add(billTOD.getTod1(), billTOD.getTod2(), billTOD.getTod3(), billTOD.getTod4()));
            final List<TODDetail> todDetailList = new ArrayList<>();
            todDetailList.add(new TODDetail(0, ReadMasterTODInterface.TOD1, "Peak", "10 PM to 6 AM", readMasterTOD.getTod1TotalConsumption(), billTOD.getTod1()));
            todDetailList.add(new TODDetail(1, ReadMasterTODInterface.TOD2, "Peak", "6 AM to 9 AM", readMasterTOD.getTod2TotalConsumption(), billTOD.getTod2()));
            todDetailList.add(new TODDetail(2, ReadMasterTODInterface.TOD3, "Off Peak", "9 AM to 5 PM", readMasterTOD.getTod3TotalConsumption(), billTOD.getTod3()));
            todDetailList.add(new TODDetail(3, ReadMasterTODInterface.TOD4, "Peak", "5 PM to  10 PM", readMasterTOD.getTod4TotalConsumption(), billTOD.getTod4()));
            todInformation.setTodDetailList(todDetailList);

            if (isNetMeter) {
                long previousReadMasterId = 0L;
                final String previousBillMonth = GlobalUtility.getPreviousMonth(templateData.getBillSummary().getBillMonth());
                final List<ReadMasterInterface> previousReadMasterList = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBillOrderByIdDesc(templateData.getConsumerInformation().getConsumerNo(), previousBillMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL);
                if (!GlobalUtility.isEmpty(previousReadMasterList)) {
                    final ReadMasterInterface previousReadMaster = previousReadMasterList.getFirst();
                    previousReadMasterId = previousReadMaster.getId();
                }
                final ReadMasterTODInterface previousReadMasterTOD = readMasterTODService.getByReadMasterId(previousReadMasterId);

                final List<TODNetMeterDetail> todNetMeterDetailList = new ArrayList<>();
                todNetMeterDetailList.add(new TODNetMeterDetail(0, ReadMasterTODInterface.TOD1, "Peak (10 PM to 6 AM)", "IMPORT", readMasterTOD.getTod1Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod1Reading() : null,
                        readMasterTOD.getTod1Consumption(), readMasterTOD.getTod1Assessment(), readMasterTOD.getTod1TotalConsumption(), null, null));
                todNetMeterDetailList.add(new TODNetMeterDetail(1, ReadMasterTODInterface.TOD2, "Peak (6 AM to 9 AM)", "IMPORT", readMasterTOD.getTod2Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod2Reading() : null,
                        readMasterTOD.getTod2Consumption(), readMasterTOD.getTod2Assessment(), readMasterTOD.getTod2TotalConsumption(), null, null));
                todNetMeterDetailList.add(new TODNetMeterDetail(2, ReadMasterTODInterface.TOD3, "Off Peak (9 AM to 5 PM)", "IMPORT", readMasterTOD.getTod3Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod3Reading() : null,
                        readMasterTOD.getTod3Consumption(), readMasterTOD.getTod3Assessment(), readMasterTOD.getTod3TotalConsumption(), null, null));
                todNetMeterDetailList.add(new TODNetMeterDetail(3, ReadMasterTODInterface.TOD4, "Peak (5 PM to  10 PM)", "IMPORT", readMasterTOD.getTod4Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod4Reading() : null,
                        readMasterTOD.getTod4Consumption(), readMasterTOD.getTod4Assessment(), readMasterTOD.getTod4TotalConsumption(), null, null));

                todInformation.setTodNetMeterDetailList(todNetMeterDetailList);
            }
        }
    }

    //TODO:Setting Meter Replacement Information
    private void setMeterReplacementInformation(final BillTemplate templateData, final BillIdentifierDTO billIdentifierDTO) {
        if (templateData == null) return;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();

        final MeterReplacementInformation meterReplacementInformation = new MeterReplacementInformation();
        final List<ReadMasterInterface> readMasterList = readMasterService.getByConsumerNoAndBillMonthOrderByIdAsc(consumerNo, billMonth);
        if (readMasterList == null || readMasterList.size() <= 1) return;

        /*for (ReadMasterInterface readMaster : readMasterList) {
            if (ReadMasterInterface.REPLACEMENT_FLAG_START_READ.equals(readMaster.getReplacementFlag())) {
                billDTO.setStartReadTwo(readMaster.getReading());
            }
            if (ReadMasterInterface.REPLACEMENT_FLAG_FINAL_READ.equals(readMaster.getReplacementFlag())) {
                billDTO.setFinalReadOne(readMaster.getReading());
                billDTO.setMeterIdentifierOldOne(readMaster.getMeterIdentifier());
                final MeterMasterInterface meterMaster = meterMasterService.getByIdentifier(readMaster.getcmpMeterIdentifier());
                if (meterMaster != null) {
                    billDTO.setMeterAttributeOldOne(meterMaster.getAttribute() != null && MISConstants.METER_ATTRIBUTE_SMART.equals(meterMaster.getAttribute()) ? meterMaster.getAttribute() : "NON SMART");
                    billDTO.setMeterIdentifierOldOne(meterMaster.getSerialNo());
                }
            }
        }*/

        /*final List<ReadMasterExportInterface> readMasterExportInterfaceList = readMasterExportService.getByConsumerNoAndBillMonthOrderByIdAsc(consumerMaster.getConsumerNo(), billDTO.getBillMonth());
        if (readMasterExportInterfaceList != null && readMasterExportInterfaceList.size() > 1) {
            for (ReadMasterExportInterface readMasterExport : readMasterExportInterfaceList) {
                if (ReadMasterExportInterface.REPLACEMENT_FLAG_START_READ.equals(readMasterExport.getReplacementFlag())
                        || ReadMasterExportInterface.REPLACEMENT_FLAG_NEW_CONNECTION.equals(readMasterExport.getReplacementFlag())) {
                    billDTO.setStartReadExportTwo(readMasterExport.getReading());
                }
                if (ReadMasterExportInterface.REPLACEMENT_FLAG_FINAL_READ.equals(readMasterExport.getReplacementFlag())) {
                    billDTO.setFinalReadExportOne(readMasterExport.getReading());
                }
            }
        }*/
    }

    //Setting Read, Meter replacement and VNM GNM Child Information
    private void setReadAndTODAndMeterReplacementAndVNMGNMChildInformation(final BillTemplate templateData, final BillIdentifierDTO billIdentifierDTO, final long billId) {
        if (templateData == null) return;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();
        final boolean isNetMeter = templateData.getConnectionInformation().isNetMeter();
        final ReadInformation readInformation = new ReadInformation();

        final List<ReadMasterInterface> readMasterList = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBillOrderByIdDesc(consumerNo, billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true);
        if (readMasterList != null && readMasterList.size() == 1) {
            ReadMasterInterface readMaster = readMasterList.getFirst();
            readInformation.setType(readMaster.getReadingType());
            readInformation.setMeterSrNo(readMaster.getMeterIdentifier());
            readInformation.setDate(readMaster.getReadingDate());
            readInformation.setSource(readMaster.getSource());

            readInformation.addRead(0, "IMPORT", readMaster.getReading(), templateData.getBillInformation().getPreviousRead(),
                    readMaster.getMf(), templateData.getBillInformation().getMeteredUnit(), readMaster.getAssessment(), readMaster.getTotalConsumption());

            if (ConsumerConnectionInformationInterface.METERING_STATUS_METERED.equals(templateData.getConnectionInformation().getMeteringStatus())) {
                final MeterMasterInterface meterMaster = meterMasterService.getByIdentifier(readMaster.getMeterIdentifier());
                if (meterMaster != null)
                    templateData.getConnectionInformation().setMeterAttribute(meterMaster.getAttribute());

                boolean isEligibleForTOD = isEligibleForTOD(templateData.getConnectionInformation());
                if (isEligibleForTOD) {
                    readInformation.setTOD(true);
                    setTODInformation(templateData, billId, readMaster.getId());
                }
            }
        }

        templateData.setReadInformation(readInformation);

        /*final ConnectionInformation connectionInformation = templateData.getConnectionInformation();
        if (connectionInformation.isNetMeter() || connectionInformation.isNetMeterParent() || connectionInformation.isNetMeterChild()) {
            String solarPlantCapacityMiscellaneous = null;
            if ("NORMAL".equals(connectionInformation.getNetMeterType())) {
                solarPlantCapacityMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_SOLAR_PLANT_CAPACITY);
            } else if ("VNM".equals(connectionInformation.getNetMeterType())) {
                solarPlantCapacityMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_GROUP_TYPE_VNM_SOLAR_PLANT_CAPACITY);
            } else if ("GNM".equals(connectionInformation.getNetMeterType())) {
                solarPlantCapacityMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_GROUP_TYPE_GNM_SOLAR_PLANT_CAPACITY);
            }
            if (StringUtils.isNotEmpty(solarPlantCapacityMiscellaneous))
                readInformation.setSolarPlantCapacity(new BigDecimal(solarPlantCapacityMiscellaneous));
            readInformation.setChild(connectionInformation.isNetMeterChild());

            final String previousBillMonth = GlobalUtility.getPreviousMonth(billMonth);
            final Sort sort = GlobalUtility.getSortObject(SortOrder.DESC);
            final List<ReadMasterExportInterface> readMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true, sort);
            long readMasterExportId = 0L;
            long previousReadMasterExportId = 0L;
            if (!GlobalUtility.isEmpty(readMasterExportList) && readMasterExportList.size() == 1) {
                final ReadMasterExportInterface readMasterExport = readMasterExportList.getFirst();
                readMasterExportId = readMasterExport.getId();

                readInformation.addRead(1, "EXPORT", readMasterExport.getReading(), getPreviousRead(),
                        readMasterExport.getMf(), readMasterExport.getConsumption(), readMasterExport.getAssessment(), readMasterExport.getTotalConsumption());

                billDTO.setExportCurrentRead(readMasterExport.getReading());
                billDTO.setExportAssessment(readMasterExport.getAssessment());
                billDTO.setExportMeteredUnit(readMasterExport.getConsumption());
                billDTO.setExportMF(readMasterExport.getMf());
                billDTO.setExportTotalUnit(readMasterExport.getTotalConsumption());
                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, GlobalUtility.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                if (MISUtility.isEmpty(previousReadMasterExportList)) {
                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlag(consumerNo, ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION);
                }
                if (!MISUtility.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                    billDTO.setExportPreviousRead(previousReadMasterExportList.get(0).getReading());
                    previousReadMasterExportId = previousReadMasterExportList.get(0).getId();
                }
                billDTO.setNetMeteredUnit(MISUtility.subtract(billDTO.getMeteredUnit(), billDTO.getExportMeteredUnit()));
                billDTO.setNetTotalUnit(MISUtility.subtract(billDTO.getTotalUnit(), billDTO.getExportTotalUnit()));
            }
            final List<ReadMasterGeneratorInterface> readMasterGeneratorList = readMasterGeneratorService.getByConsumerNoAndBillMonthAndReplacementFlag(consumerNo, billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
            if (!MISUtility.isEmpty(readMasterGeneratorList) && readMasterGeneratorList.size() == 1) {
                final ReadMasterGeneratorInterface readMasterGenerator = readMasterGeneratorList.get(0);
                billDTO.setSolarGeneratorCurrentRead(readMasterGenerator.getReading());
                billDTO.setSolarGeneratorAssessment(readMasterGenerator.getAssessment());
                billDTO.setSolarGeneratorMeteredUnit(readMasterGenerator.getConsumption());
                billDTO.setSolarGeneratorMF(readMasterGenerator.getMf());
                billDTO.setSolarGeneratorTotalUnit(readMasterGenerator.getTotalConsumption());
                final List<ReadMasterGeneratorInterface> previousReadMasterGeneratorList = readMasterGeneratorService.getByConsumerNoAndBillMonthAndReplacementFlag(consumerNo, GlobalResources.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                if (!MISUtility.isEmpty(previousReadMasterGeneratorList) && previousReadMasterGeneratorList.size() == 1) {
                    billDTO.setSolarGeneratorPreviousRead(previousReadMasterGeneratorList.get(0).getReading());
                }
            }
            final NetMeterAccountingInterface netMeterAccounting = netMeterAccountingService.getByBillId(billId);
            billDTO.setCarryForwardUnit(netMeterAccounting != null ? netMeterAccounting.getResidualUnit() : BigDecimal.ZERO);

            if (isEligibleForTOD) {
                final ReadMasterExportTODInterface readMasterExportTOD = readMasterExportTODService.getByReadMasterExportId(readMasterExportId);
                final ReadMasterExportTODInterface previousReadMasterExportTOD = readMasterExportTODService.getByReadMasterExportId(previousReadMasterExportId);
                if (readMasterExportTOD != null) {
                    billDTO.setTod1ExportCurrentRead(readMasterExportTOD.getTod1Reading());
                    if (previousReadMasterExportTOD != null)
                        billDTO.setTod1ExportPreviousRead(previousReadMasterExportTOD.getTod1Reading());
                    billDTO.setTod1ExportMeterConsumption(readMasterExportTOD.getTod1Consumption());
                    billDTO.setTod1ExportAssessment(readMasterExportTOD.getTod1Assessment());
                    billDTO.setTod1ExportFinalConsumption(readMasterExportTOD.getTod1TotalConsumption());
                    billDTO.setTod1Net(MISUtility.subtract(billDTO.getTod1ImportFinalConsumption(), billDTO.getTod1ExportFinalConsumption()));

                    billDTO.setTod2ExportCurrentRead(readMasterExportTOD.getTod2Reading());
                    if (previousReadMasterExportTOD != null)
                        billDTO.setTod2ExportPreviousRead(previousReadMasterExportTOD.getTod2Reading());
                    billDTO.setTod2ExportMeterConsumption(readMasterExportTOD.getTod2Consumption());
                    billDTO.setTod2ExportAssessment(readMasterExportTOD.getTod2Assessment());
                    billDTO.setTod2ExportFinalConsumption(readMasterExportTOD.getTod2TotalConsumption());
                    billDTO.setTod2Net(MISUtility.subtract(billDTO.getTod2ImportFinalConsumption(), billDTO.getTod2ExportFinalConsumption()));

                    billDTO.setTod3ExportCurrentRead(readMasterExportTOD.getTod3Reading());
                    if (previousReadMasterExportTOD != null)
                        billDTO.setTod3ExportPreviousRead(previousReadMasterExportTOD.getTod3Reading());
                    billDTO.setTod3ExportMeterConsumption(readMasterExportTOD.getTod3Consumption());
                    billDTO.setTod3ExportAssessment(readMasterExportTOD.getTod3Assessment());
                    billDTO.setTod3ExportFinalConsumption(readMasterExportTOD.getTod3TotalConsumption());
                    billDTO.setTod3Net(MISUtility.subtract(billDTO.getTod3ImportFinalConsumption(), billDTO.getTod3ExportFinalConsumption()));

                    billDTO.setTod4ExportCurrentRead(readMasterExportTOD.getTod4Reading());
                    if (previousReadMasterExportTOD != null)
                        billDTO.setTod4ExportPreviousRead(previousReadMasterExportTOD.getTod4Reading());
                    billDTO.setTod4ExportMeterConsumption(readMasterExportTOD.getTod4Consumption());
                    billDTO.setTod4ExportAssessment(readMasterExportTOD.getTod4Assessment());
                    billDTO.setTod4ExportFinalConsumption(readMasterExportTOD.getTod4TotalConsumption());
                    billDTO.setTod4Net(MISUtility.subtract(billDTO.getTod4ImportFinalConsumption(), billDTO.getTod4ExportFinalConsumption()));
                }
            }

            //VNM GNM Child Detail
            if (isVirtualNetMeterParent || isGroupNetMeterParent) {
                final List<NetMeteringArrangementInterface> netMetereteringChildList = netMeterArrangementService.getNonParentByParentConsumerNoAndBillMonthBetween(consumerNo, billMonth);
                if (!MISUtility.isEmpty(netMetereteringChildList)) {
                    int index = 1;
                    for (NetMeteringArrangementInterface netMeteringArrangement : netMetereteringChildList) {
                        if (index == 1) {
                            billDTO.setRatio1(netMeteringArrangement.getRatio());
                            billDTO.setChildExportConsumerNo1(netMeteringArrangement.getConsumerNo());
                            final List<ReadMasterExportInterface> childReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                            if (!MISUtility.isEmpty(childReadMasterExportList) && childReadMasterExportList.size() == 1) {
                                final ReadMasterExportInterface readMasterExport = childReadMasterExportList.get(0);
                                billDTO.setChildExportCurrentRead1(readMasterExport.getReading());
                                billDTO.setChildExportAssessment1(readMasterExport.getAssessment());
                                billDTO.setChildExportMeteredUnit1(readMasterExport.getConsumption());
                                billDTO.setChildExportMF1(readMasterExport.getMf());
                                billDTO.setChildExportTotalUnit1(readMasterExport.getTotalConsumption());
                                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), GlobalResources.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                                if (MISUtility.isEmpty(previousReadMasterExportList)) {
                                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlag(netMeteringArrangement.getConsumerNo(), ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION);
                                }
                                if (!MISUtility.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                                    billDTO.setChildExportPreviousRead1(previousReadMasterExportList.get(0).getReading());
                                }
                            }
                        }
                        if (index == 2) {
                            billDTO.setRatio2(netMeteringArrangement.getRatio());
                            billDTO.setChildExportConsumerNo2(netMeteringArrangement.getConsumerNo());
                            final List<ReadMasterExportInterface> childReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                            if (!MISUtility.isEmpty(childReadMasterExportList) && childReadMasterExportList.size() == 1) {
                                final ReadMasterExportInterface readMasterExport = childReadMasterExportList.get(0);
                                billDTO.setChildExportCurrentRead2(readMasterExport.getReading());
                                billDTO.setChildExportAssessment2(readMasterExport.getAssessment());
                                billDTO.setChildExportMeteredUnit2(readMasterExport.getConsumption());
                                billDTO.setChildExportMF2(readMasterExport.getMf());
                                billDTO.setChildExportTotalUnit2(readMasterExport.getTotalConsumption());
                                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), GlobalResources.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                                if (MISUtility.isEmpty(previousReadMasterExportList)) {
                                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlag(netMeteringArrangement.getConsumerNo(), ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION);
                                }
                                if (!MISUtility.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                                    billDTO.setChildExportPreviousRead2(previousReadMasterExportList.get(0).getReading());
                                }
                            }
                        }
                        if (index == 3) {
                            billDTO.setRatio3(netMeteringArrangement.getRatio());
                            billDTO.setChildExportConsumerNo3(netMeteringArrangement.getConsumerNo());
                            final List<ReadMasterExportInterface> childReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                            if (!MISUtility.isEmpty(childReadMasterExportList) && childReadMasterExportList.size() == 1) {
                                final ReadMasterExportInterface readMasterExport = childReadMasterExportList.get(0);
                                billDTO.setChildExportCurrentRead3(readMasterExport.getReading());
                                billDTO.setChildExportAssessment3(readMasterExport.getAssessment());
                                billDTO.setChildExportMeteredUnit3(readMasterExport.getConsumption());
                                billDTO.setChildExportMF3(readMasterExport.getMf());
                                billDTO.setChildExportTotalUnit3(readMasterExport.getTotalConsumption());
                                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), GlobalResources.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                                if (MISUtility.isEmpty(previousReadMasterExportList)) {
                                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlag(netMeteringArrangement.getConsumerNo(), ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION);
                                }
                                if (!MISUtility.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                                    billDTO.setChildExportPreviousRead3(previousReadMasterExportList.get(0).getReading());
                                }
                            }
                        }
                        if (index == 4) {
                            billDTO.setRatio4(netMeteringArrangement.getRatio());
                            billDTO.setChildExportConsumerNo4(netMeteringArrangement.getConsumerNo());
                            final List<ReadMasterExportInterface> childReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                            if (!MISUtility.isEmpty(childReadMasterExportList) && childReadMasterExportList.size() == 1) {
                                final ReadMasterExportInterface readMasterExport = childReadMasterExportList.get(0);
                                billDTO.setChildExportCurrentRead4(readMasterExport.getReading());
                                billDTO.setChildExportAssessment4(readMasterExport.getAssessment());
                                billDTO.setChildExportMeteredUnit4(readMasterExport.getConsumption());
                                billDTO.setChildExportMF4(readMasterExport.getMf());
                                billDTO.setChildExportTotalUnit4(readMasterExport.getTotalConsumption());
                                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), GlobalResources.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                                if (MISUtility.isEmpty(previousReadMasterExportList)) {
                                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlag(netMeteringArrangement.getConsumerNo(), ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION);
                                }
                                if (!MISUtility.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                                    billDTO.setChildExportPreviousRead4(previousReadMasterExportList.get(0).getReading());
                                }
                            }
                        }
                        if (index == 5) {
                            billDTO.setRatio5(netMeteringArrangement.getRatio());
                            billDTO.setChildExportConsumerNo5(netMeteringArrangement.getConsumerNo());
                            final List<ReadMasterExportInterface> childReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                            if (!MISUtility.isEmpty(childReadMasterExportList) && childReadMasterExportList.size() == 1) {
                                final ReadMasterExportInterface readMasterExport = childReadMasterExportList.get(0);
                                billDTO.setChildExportCurrentRead5(readMasterExport.getReading());
                                billDTO.setChildExportAssessment5(readMasterExport.getAssessment());
                                billDTO.setChildExportMeteredUnit5(readMasterExport.getConsumption());
                                billDTO.setChildExportMF5(readMasterExport.getMf());
                                billDTO.setChildExportTotalUnit5(readMasterExport.getTotalConsumption());
                                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlag(netMeteringArrangement.getConsumerNo(), GlobalResources.getPreviousMonth(billMonth), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                                if (MISUtility.isEmpty(previousReadMasterExportList)) {
                                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlag(netMeteringArrangement.getConsumerNo(), ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION);
                                }
                                if (!MISUtility.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                                    billDTO.setChildExportPreviousRead5(previousReadMasterExportList.get(0).getReading());
                                }
                            }
                        }
                        index = index + 1;
                    }
                }
            }
        }


        if (isNetMeter) {


            this.solarPlantCapacity = solarPlantCapacity;
            this.residualUnit = residualUnit;
            this.lastMonthResidualUnit = lastMonthResidualUnit;
            this.isChild = isChild;
            this.residualUnitAdjusted = residualUnitAdjusted;
            this.netMeteredUnit = netMeteredUnit;
            this.netBilledUnit = netBilledUnit;
        }


        //TOD Processing
        boolean isEligibleForTOD = isEligibleForTOD(billDTO);
        if (isEligibleForTOD) {
            billDTO.setTOD(MISConstants.TRUE);
            long readMasterId = 0L;
            final List<ReadMasterInterface> readMasterInterfaces = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBillOrderByIdDesc(consumerMaster.getConsumerNo(), billDTO.getBillMonth(), ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL);
            if (readMasterInterfaces != null && readMasterInterfaces.size() == 1) {
                ReadMasterInterface readMaster = readMasterInterfaces.get(0);
                readMasterId = readMaster.getId();
            }
            final ReadMasterTODInterface readMasterTOD = readMasterTODService.getByReadMasterId(readMasterId);
            final BillTODInterface billTOD = billTODService.getByBillId(billId);

            long previousReadMasterId = 0L;
            final String previousBillMonth = GlobalResources.getPreviousMonth(billDTO.getBillMonth());
            final List<ReadMasterInterface> previousReadMasterInterfaces = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBillOrderByIdDesc(consumerMaster.getConsumerNo(), previousBillMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL);
            if (previousReadMasterInterfaces != null && previousReadMasterInterfaces.size() == 1) {
                ReadMasterInterface readMaster = previousReadMasterInterfaces.get(0);
                previousReadMasterId = readMaster.getId();
            }
            final ReadMasterTODInterface previousReadMasterTOD = readMasterTODService.getByReadMasterId(previousReadMasterId);

            if (readMasterTOD != null && billTOD != null) {
                billDTO.setTod1Unit(readMasterTOD.getTod1TotalConsumption());
                billDTO.setTod1Amount(billTOD.getTod1());
                billDTO.setTod2Unit(readMasterTOD.getTod2TotalConsumption());
                billDTO.setTod2Amount(billTOD.getTod2());
                billDTO.setTod3Unit(readMasterTOD.getTod3TotalConsumption());
                billDTO.setTod3Amount(billTOD.getTod3());
                billDTO.setTod4Unit(readMasterTOD.getTod4TotalConsumption());
                billDTO.setTod4Amount(billTOD.getTod4());

                billDTO.setTod1ImportCurrentRead(readMasterTOD.getTod1Reading());
                if (previousReadMasterTOD != null)
                    billDTO.setTod1ImportPreviousRead(previousReadMasterTOD.getTod1Reading());
                billDTO.setTod1ImportMeterConsumption(readMasterTOD.getTod1Consumption());
                billDTO.setTod1ImportAssessment(readMasterTOD.getTod1Assessment());
                billDTO.setTod1ImportFinalConsumption(readMasterTOD.getTod1TotalConsumption());

                billDTO.setTod2ImportCurrentRead(readMasterTOD.getTod2Reading());
                if (previousReadMasterTOD != null)
                    billDTO.setTod2ImportPreviousRead(previousReadMasterTOD.getTod2Reading());
                billDTO.setTod2ImportMeterConsumption(readMasterTOD.getTod2Consumption());
                billDTO.setTod2ImportAssessment(readMasterTOD.getTod2Assessment());
                billDTO.setTod2ImportFinalConsumption(readMasterTOD.getTod2TotalConsumption());

                billDTO.setTod3ImportCurrentRead(readMasterTOD.getTod3Reading());
                if (previousReadMasterTOD != null)
                    billDTO.setTod3ImportPreviousRead(previousReadMasterTOD.getTod3Reading());
                billDTO.setTod3ImportMeterConsumption(readMasterTOD.getTod3Consumption());
                billDTO.setTod3ImportAssessment(readMasterTOD.getTod3Assessment());
                billDTO.setTod3ImportFinalConsumption(readMasterTOD.getTod3TotalConsumption());

                billDTO.setTod4ImportCurrentRead(readMasterTOD.getTod4Reading());
                if (previousReadMasterTOD != null)
                    billDTO.setTod4ImportPreviousRead(previousReadMasterTOD.getTod4Reading());
                billDTO.setTod4ImportMeterConsumption(readMasterTOD.getTod4Consumption());
                billDTO.setTod4ImportAssessment(readMasterTOD.getTod4Assessment());
                billDTO.setTod4ImportFinalConsumption(readMasterTOD.getTod4TotalConsumption());
            }
        }*/

        //TODO:

        templateData.setReadInformation(readInformation);

        setMeterReplacementInformation(templateData, billIdentifierDTO);
    }

    //Set History Information
    private void setReadAndPaymentHistory(final BillTemplate templateData) {
        if (templateData == null) return;
        final String consumerNo = templateData.getConsumerInformation().getConsumerNo();
        final String billMonth = templateData.getBillSummary().getBillMonth();

        //Reading History
        if (ConsumerConnectionInformationInterface.METERING_STATUS_METERED.equals(templateData.getConnectionInformation().getMeteringStatus())) {
            final List<ReadMasterInterface> readMasterList = readMasterService.getByConsumerNoAndReplacementFlagAndUsedOnBillAndBillMonthLessThanOrderByBillMonthDESC(consumerNo, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL, billMonth, 6);
            if (!GlobalUtility.isEmpty(readMasterList)) {
                int maxIndex = readMasterList.size() - 1;
                final List<ReadHistoryInformation> readHistoryInformationList = new ArrayList<>();
                if (maxIndex >= 0) {
                    ReadMasterInterface readMaster = readMasterList.getFirst();
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(1, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }
                if (maxIndex >= 1) {
                    ReadMasterInterface readMaster = readMasterList.get(1);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(2, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }
                if (maxIndex >= 2) {
                    ReadMasterInterface readMaster = readMasterList.get(2);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(3, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }
                if (maxIndex >= 3) {
                    ReadMasterInterface readMaster = readMasterList.get(3);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(4, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }
                if (maxIndex >= 4) {
                    ReadMasterInterface readMaster = readMasterList.get(4);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(5, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }
                /*if (maxIndex >= 5) {
                    ReadMasterInterface readMaster = readMasterList.get(5);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(6, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }*/
                //Last year's same month reading
                final int year = Integer.valueOf(billMonth.substring(4));
                final String lastYearMonth = billMonth.substring(0, 4).concat(String.valueOf(year - 1));
                List<ReadMasterInterface> lastYearReadMasterInterfaces = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBillOrderByIdDesc(consumerNo, lastYearMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL);
                if (lastYearReadMasterInterfaces != null && lastYearReadMasterInterfaces.size() == 1) {
                    ReadMasterInterface readMaster = lastYearReadMasterInterfaces.getFirst();
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(6, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                    }
                }

                templateData.setReadHistoryInformationList(readHistoryInformationList);
            }
        }

        //Payment History
        final List<PaymentInterface> paymentList = paymentService.getByConsumerNoAndDeletedAndPostingBillMonthLessThanEqualOrderByPayDateDESC(consumerNo, false, billMonth, 2);
        if (!GlobalUtility.isEmpty(paymentList)) {
            int maxIndex = paymentList.size() - 1;
            final List<PaymentHistoryInformation> paymentHistoryInformationList = new ArrayList<>();
            if (maxIndex >= 0) {
                PaymentInterface payment = paymentList.getFirst();
                if (payment != null) {
                    final PaymentHistoryInformation paymentHistoryInformation = new PaymentHistoryInformation(1, payment.getAmount(), payment.getPayDate(), payment.getPostingBillMonth(), payment.getCacNo(), payment.getPayMode());
                    paymentHistoryInformationList.add(paymentHistoryInformation);
                }
            }
            if (maxIndex >= 1) {
                PaymentInterface payment = paymentList.get(1);
                if (payment != null) {
                    final PaymentHistoryInformation paymentHistoryInformation = new PaymentHistoryInformation(2, payment.getAmount(), payment.getPayDate(), payment.getPostingBillMonth(), payment.getCacNo(), payment.getPayMode());
                    paymentHistoryInformationList.add(paymentHistoryInformation);
                }
            }
            templateData.setPaymentHistoryInformationList(paymentHistoryInformationList);
        }

        //Average Calculation
        long days = 30L; // For PDC and Un-metered Consumer
        final LocalDate currentReadDate = templateData.getReadInformation().getDate();
        if (currentReadDate != null) {
            LocalDate previousReadDate = null;
            if (!GlobalUtility.isEmpty(templateData.getReadHistoryInformationList())) {
                previousReadDate = templateData.getReadHistoryInformationList().getFirst().getReadDate();
                days = ChronoUnit.DAYS.between(previousReadDate, currentReadDate);
                if (days != 0) {
                    templateData.getBillSummary().setDailyAverageBill(templateData.getBillSummary().getCurrentBill().divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP));
                    templateData.getBillSummary().setDailyAverageConsumption(templateData.getBillSummary().getConsumption().divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP));
                } else {
                    templateData.getBillSummary().setDailyAverageBill(templateData.getBillSummary().getCurrentBill());
                    templateData.getBillSummary().setDailyAverageConsumption(templateData.getBillSummary().getConsumption());
                }
            }
        } else {
            templateData.getBillSummary().setDailyAverageBill(templateData.getBillSummary().getCurrentBill().divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP));
        }
    }

    //Set Adjustment and Bill calculation Information
    private void setBillCalculationAndAdjustmentInformation(final BillTemplate templateData, final long billId) {
        if (templateData == null) return;
        final String consumerNo = templateData.getConsumerInformation().getConsumerNo();
        final String billMonth = templateData.getBillSummary().getBillMonth();

        final List<AdjustmentInterface> adjustmentList = ngbAdjustmentService.getByConsumerNoAndPostingBillMonthAndApprovalStatusAndPostedAndDeletedOrderByCodeAscIdAsc(consumerNo, billMonth, GlobalConstant.APPROVED, true, false);
        if (!GlobalUtility.isEmpty(adjustmentList)) {
            final List<AdjustmentInformation> adjustmentInformationList = new ArrayList<>();
            for (int index = 0; index < adjustmentList.size(); index++) {
                final AdjustmentInterface adjustment = adjustmentList.get(index);
                final AdjustmentInformation adjustmentInformation = new AdjustmentInformation(index + 1, adjustmentTypeService.getDescriptionByCode(adjustment.getCode()), adjustment.getAmount());
                adjustmentInformationList.add(adjustmentInformation);
            }
            templateData.setAdjustmentInformationList(adjustmentInformationList);
        }

        //Bill Calculation Line: Not feasible as of now; there are duplicate rows in bill calculation line table
    }

    //Set Vigilance Information
    private void setVigilanceInformation(final BillTemplate templateData, final List<ConsumerPanchnamaInformationInterface> consumerPanchnamaInformationList) {
        if (templateData == null || GlobalUtility.isEmpty(consumerPanchnamaInformationList)) return;

        List<VigilanceInformation> vigilanceInformationList = new ArrayList<>();
        for (int index = 0; index < consumerPanchnamaInformationList.size(); index++) {
            final ConsumerPanchnamaInformationInterface consumerPanchnamaInformation = consumerPanchnamaInformationList.get(index);
            final VigilanceInformation vigilanceInformation = new VigilanceInformation(index + 1, consumerPanchnamaInformation.getPanchnamaNo() + " (" + consumerPanchnamaInformation.getPanchnamaDate() + ")", consumerPanchnamaInformation.getOutstandingAmount());
            vigilanceInformationList.add(vigilanceInformation);
        }
        templateData.setVigilanceInformationList(vigilanceInformationList);
    }

    //Setting Template information
    private void setTemplateInformation(final BillTemplate templateData, final String templateVersion) {
        if (templateData == null) return;

        templateData.setTemplateInformation(new TemplateInformation(templateVersion, null, null));
    }

    //Setting Company Information
    private void setCompanyInformation(final BillTemplate templateData) {
        if (templateData == null) return;

        final DiscomInterface discom = discomService.get();
        if (discom == null) return;

        final CompanyInformation companyInformation = new CompanyInformation(discom.getShortName(), discom.getFullName(), discom.getAddress(), discom.getFullNameH(), discom.getAddressH(), discom.getSupportEmail(), discom.getOfficialWebsite(), discom.getCallCenterNo(),
                discom.getGstNo(), discom.getCin(), discom.getWhatsappNo(), discom.getWhatsappLink(), discomService.getWhatsappQRAsBase64(), null, discomService.getDiscomLogoAsBase64(), null, discomService.getMPStateLogoAsBase64());
        templateData.setCompanyInformation(companyInformation);
    }

    //Setting Consumer and Connection Information
    private DivisionInterface setConsumerAndConnectionInformation(final BillTemplate templateData, final String consumerNo) {
        if (templateData == null) return null;

        final Object[] consumerInformationObj = consumerInformationService.getForBillTemplateByConsumerNo(consumerNo);
        if (consumerInformationObj == null) {
            log.info("Consumer information not found for {}", consumerNo);
            return null;
        }

        final String locationCode = (String) consumerInformationObj[8];
        final boolean isEmployee = consumerInformationObj[7] != null ? (Boolean) consumerInformationObj[7] : false;
        String employeeNo = null;
        if (isEmployee) {
            final EmployeeConnectionMappingInterface employeeConnectionMapping = employeeConnectionMappingService.getActiveEmployeeByConsumerNo(consumerNo);
            employeeNo = employeeConnectionMapping.getEmployeeNo();
        }
        final ConsumerInformation consumerInformation = new ConsumerInformation((String) consumerInformationObj[0], (String) consumerInformationObj[1], (String) consumerInformationObj[3],
                (String) consumerInformationObj[2], (String) consumerInformationObj[4], (String) consumerInformationObj[5], (String) consumerInformationObj[6], employeeNo, (String) consumerInformationObj[11]);
        templateData.setConsumerInformation(consumerInformation);

        final ZoneInterface zone = zoneService.getByCode(locationCode);
        final DivisionInterface division = divisionService.getById(zone.getDivisionId());
        final Object[] consumerConnectionInformationObj = consumerConnectionInformationService.getForBillTemplateByConsumerNo(consumerNo);

        final ConnectionInformation connectionInformation = new ConnectionInformation(division.getName(), zone.getName(), locationCode, (String) consumerInformationObj[9], (String) consumerInformationObj[10],
                (String) consumerConnectionInformationObj[14], (String) consumerConnectionInformationObj[13], (String) consumerConnectionInformationObj[12], (String) consumerConnectionInformationObj[11],
                (String) consumerConnectionInformationObj[4], (String) consumerConnectionInformationObj[0], (String) consumerConnectionInformationObj[1], (LocalDate) consumerConnectionInformationObj[10],
                (String) consumerConnectionInformationObj[9], (BigDecimal) consumerConnectionInformationObj[5], (String) consumerConnectionInformationObj[6], (BigDecimal) consumerConnectionInformationObj[7],
                (String) consumerConnectionInformationObj[8], (String) consumerConnectionInformationObj[2], (String) consumerConnectionInformationObj[3], false, false, false, null, false, false, null, null);

        final String netMeterMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_IS_NET_METER);
        final String virtualNetMeterParentMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_VNM_PARENT_CONSUMER);
        final String groupNetMeterParentMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_GNM_PARENT_CONSUMER);
        final String virtualNetMeterChildMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_VNM_CHILD_CONSUMER);
        final String groupNetMeterChildMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_GNM_CHILD_CONSUMER);
        final String memberOfHTGroupHousingSocietyMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyValueByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_MEMBER_OF_HT_GROUP_HOUSING_SOCIETY);
        connectionInformation.setNetMeter(GlobalConstant.YES_ABBREVIATION.equals(netMeterMiscellaneous));
        connectionInformation.setNetMeterParent(GlobalConstant.YES_ABBREVIATION.equals(virtualNetMeterParentMiscellaneous) || GlobalConstant.YES_ABBREVIATION.equals(groupNetMeterParentMiscellaneous));
        connectionInformation.setNetMeterChild(GlobalConstant.YES_ABBREVIATION.equals(virtualNetMeterChildMiscellaneous) || GlobalConstant.YES_ABBREVIATION.equals(groupNetMeterChildMiscellaneous));
        String netMeterType = null;
        if (connectionInformation.isNetMeter()) netMeterType = "NORMAL";
        if (GlobalConstant.YES_ABBREVIATION.equals(virtualNetMeterParentMiscellaneous) || GlobalConstant.YES_ABBREVIATION.equals(virtualNetMeterChildMiscellaneous))
            netMeterType = "VNM";
        if (GlobalConstant.YES_ABBREVIATION.equals(groupNetMeterParentMiscellaneous) || GlobalConstant.YES_ABBREVIATION.equals(groupNetMeterChildMiscellaneous))
            netMeterType = "GNM";
        connectionInformation.setNetMeterType(netMeterType);
        connectionInformation.setMemberOfHTGroupHousingSociety(GlobalConstant.YES_ABBREVIATION.equals(memberOfHTGroupHousingSocietyMiscellaneous));
        final ConsumerMiscellaneousInformationInterface prepaidMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_IS_PREPAID);
        if (prepaidMiscellaneous != null) {
            connectionInformation.setPrepaid(GlobalConstant.YES_ABBREVIATION.equals(prepaidMiscellaneous.getPropertyValue()));
            if (connectionInformation.isPrepaid())
                connectionInformation.setPrepaidDate(prepaidMiscellaneous.getEffectiveStartDate());
        }

        templateData.setConnectionInformation(connectionInformation);

        final MeterReaderInformationInterface meterReaderInformation = meterReaderInformationService.getByReadingDiaryNoId(Long.valueOf(templateData.getConnectionInformation().getReadingDiaryNo()));
        if (meterReaderInformation != null) {
            templateData.getBillSummary().setMeterReaderName(meterReaderInformation.getName());
            templateData.getBillSummary().setMeterReaderContactNo(meterReaderInformation.getMobileNo());
        }

        return division;
    }

    //Setting Contact Information
    private void setContactInformation(final BillTemplate templateData, final long divisionId) {
        if (templateData == null) return;

        final List<ContactInformation> contactInformationList = new ArrayList<>();

        final List<UserDetailInterface> userDetailList = userDetailService.getByLocationCodeAndRoleAndStatus(templateData.getConnectionInformation().getLocationCode(), UserDetailInterface.ROLE_OIC, GlobalConstant.STATUS_ACTIVE);
        if (userDetailList != null && userDetailList.size() == 1) {
            final UserDetailInterface oic = userDetailList.getFirst();
            final ContactInformation contactInformation = new ContactInformation(0, oic.getDesignation(), oic.getName(), oic.getMobileNo(), templateData.getConnectionInformation().getZoneName());
            contactInformationList.add(contactInformation);
        }

        final EEDivisionMappingInterface eeDivisionMapping = eeDivisionMappingService.getByDivisionId(divisionId);
        if (eeDivisionMapping != null) {
            final UserDetailInterface ee = userDetailService.getByUsername(eeDivisionMapping.getUsername());
            final ContactInformation contactInformation = new ContactInformation(1, ee.getDesignation(), ee.getName(), ee.getMobileNo(), templateData.getConnectionInformation().getDivisionName());
            contactInformationList.add(contactInformation);
        }

        templateData.setContactInformationList(contactInformationList);
    }

    //Setting Bill Message
    private void setBillMessage(final BillTemplate templateData, String language) {
        if (templateData == null) return;
        final List<String> billMessageList = new ArrayList<>();
        if (StringUtils.isEmpty(language)) language = "ENGLISH";

        //Message One
        final String messageOne = billMessageService.getMessageOne(language);
        if (StringUtils.isNotEmpty(messageOne)) billMessageList.add(messageOne);

        //Message Two
        final String consumerNo = templateData.getConsumerInformation().getConsumerNo();
        final ConsumerMiscellaneousInformationInterface otsMiscellaneous = consumerMiscellaneousInformationService.getActivePropertyByConsumerNoAndPropertyName(consumerNo, ConsumerMiscellaneousInformationInterface.PROPERTY_NAME_OTS_SCHEME_OPT_TYPE);
        if (otsMiscellaneous != null) {
            final List<Object> argList = new ArrayList<>();
            String otsType = otsMiscellaneous.getPropertyValue();
            if (GlobalConstant.OTS_OPTION_INSTALLMENT.equals(otsMiscellaneous.getPropertyValue())) {
                final List<AdjustmentInterface> adjustmentList = ngbAdjustmentService.getByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeletedOrderByPostingBillMonthASC(consumerNo, AdjustmentInterface.OTS_SCHEME_CUMULATIVE_SURCHARGE_WAIVER_REVERSAL_ADJUSTMENT_CODE, GlobalConstant.APPROVED, true, false);
                if (adjustmentList != null && !adjustmentList.isEmpty()) {
                    otsType = GlobalConstant.DEFAULTER;
                    argList.add(otsType);
                }
            }

            if (GlobalConstant.OTS_OPTION_LUMPSUM.equals(otsMiscellaneous.getPropertyValue())) {
                final List<AdjustmentInterface> adjustmentList = ngbAdjustmentService.getByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeletedOrderByPostingBillMonthASC(consumerNo, AdjustmentInterface.OTS_SCHEME_CUMULATIVE_SURCHARGE_WAIVER_ADJUSTMENT_CODE, GlobalConstant.APPROVED, true, false);
                if (adjustmentList != null && !adjustmentList.isEmpty()) {
                    argList.add(otsType);
                    argList.add(adjustmentList.stream().map(AdjustmentInterface::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toPlainString());
                }
            } else if (GlobalConstant.OTS_OPTION_INSTALLMENT.equals(otsMiscellaneous.getPropertyValue())) {
                if (!GlobalConstant.DEFAULTER.equals(otsType)) {
                    final LocalDate tagDate = otsMiscellaneous.getEffectiveStartDate();
                    List<AdjustmentInterface> adjustmentList = ngbAdjustmentService.getByConsumerNoAndCodeAndApprovalStatusAndDeletedOrderByPostingBillMonthASC(consumerNo, AdjustmentInterface.OTS_SCHEME_ARREAR_INSTALLMENT_ADJUSTMENT_CODE, GlobalConstant.APPROVED, false);
                    if (adjustmentList != null && !adjustmentList.isEmpty() && adjustmentList.stream().anyMatch(adj -> !adj.isPosted())) {
                        //If installment is going on
                        final int installmentCount = adjustmentList.size();
                        final BigDecimal installmentAmount = adjustmentList.getFirst().getAmount();
                        final String firstMonth = adjustmentList.getFirst().getPostingBillMonth();
                        final String lastMonth = adjustmentList.get(installmentCount - 1).getPostingBillMonth();
                        final long pendingInstallmentCount = adjustmentList.stream().filter(a -> !a.isPosted()).count();
                        final BigDecimal totalPendingInstallmentAmount = adjustmentList.stream().filter(a -> !a.isPosted()).map(AdjustmentInterface::getAmount).reduce(GlobalUtility::add).orElse(BigDecimal.ZERO);

                        argList.add(otsType);
                        argList.add(GlobalUtility.getDateInStringFromDate(tagDate, GlobalUtility.EXPORT_DATE_FORMAT));
                        argList.add(String.valueOf(installmentCount));
                        argList.add(installmentAmount.toPlainString());
                        argList.add(firstMonth);
                        argList.add(lastMonth);
                        argList.add(String.valueOf(pendingInstallmentCount));
                        argList.add(installmentAmount.toPlainString());
                        argList.add(totalPendingInstallmentAmount.toPlainString());
                    } else {
                        //Matured
                        adjustmentList = ngbAdjustmentService.getByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeletedOrderByPostingBillMonthASC(consumerNo, AdjustmentInterface.OTS_SCHEME_CUMULATIVE_SURCHARGE_WAIVER_ADJUSTMENT_CODE, GlobalConstant.APPROVED, true, false);
                        if (adjustmentList != null && !adjustmentList.isEmpty()) {
                            argList.add(GlobalConstant.OTS_OPTION_LUMPSUM);
                            argList.add(adjustmentList.stream().map(AdjustmentInterface::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toPlainString());
                        }
                    }
                }
            }

            final Object[] argsTwo = argList.toArray();
            final String messageTwo = billMessageService.getMessageTwo(language, argsTwo);
            if (StringUtils.isNotEmpty(messageTwo)) billMessageList.add(messageTwo);

        }

        //Message Three
        final String messageThree = billMessageService.getMessageThree(language);
        if (StringUtils.isNotEmpty(messageThree)) billMessageList.add(messageThree);

        templateData.setMessages(billMessageList);
    }

    //Setting Circle ECGRF Information
    private void setCircleECGRFInformation(final BillTemplate templateData, final long circleId) {
        if (templateData == null) return;

        final List<CircleECGRFInformationInterface> circleECGRFInformationList = circleECGRFInformationService.getByCircleIdOrderByIndexAsc(circleId);
        if (circleECGRFInformationList == null || circleECGRFInformationList.isEmpty()) return;

        final List<ECGRFInformation> ecgrfInformationList = new ArrayList<>();
        for (int index = 0; index < circleECGRFInformationList.size(); index++) {
            final CircleECGRFInformationInterface circleECGRFInformation = circleECGRFInformationList.get(index);
            final ECGRFInformation ecgrfInformation = new ECGRFInformation(index + 1, circleECGRFInformation.getMemberType(), circleECGRFInformation.getName(), circleECGRFInformation.getContactNo(), circleECGRFInformation.getCaseHandling());
            ecgrfInformationList.add(ecgrfInformation);
        }

        if (!ecgrfInformationList.isEmpty()) templateData.setEcgrfInformationList(ecgrfInformationList);
    }
}
