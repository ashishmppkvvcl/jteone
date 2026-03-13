package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillTemplate;
import com.mppkvvcl.jteone.dtos.templates.pdfbill.*;
import com.mppkvvcl.jteone.enums.SortOrder;
import com.mppkvvcl.jteone.service.daos.mis.BillMessageService;
import com.mppkvvcl.jteone.service.daos.mis.DiscomService;
import com.mppkvvcl.jteone.service.daos.ngb.*;
import com.mppkvvcl.jteone.utility.GlobalConstant;
import com.mppkvvcl.jteone.utility.GlobalUtil;
import com.mppkvvcl.jteone.utility.UrjasCypher;
import com.mppkvvcl.misdao.dtos.BillIdentifierDTO;
import com.mppkvvcl.misdao.interfaces.DiscomInterface;
import com.mppkvvcl.ngbdao.interfaces.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BillNgbTemplateService {
    private static final Logger log = LoggerFactory.getLogger(BillNgbTemplateService.class);

    @Autowired
    private AdjustmentTypeService adjustmentTypeService;

    @Autowired
    @Qualifier("ngbAdjustmentService")
    private AdjustmentService ngbAdjustmentService;

    @Autowired
    private BillService billService;

    @Autowired
    private BillCalculationLineService billCalculationLineService;

    @Autowired
    private BillTODService billTODService;

    @Autowired
    private BillTypeCodeService billTypeCodeService;

    @Autowired
    private BillMessageService billMessageService;

    @Autowired
    private ConsumerInformationService consumerInformationService;

    @Autowired
    private ConsumerConnectionInformationService consumerConnectionInformationService;

    @Autowired
    private ConsumerMiscellaneousInformationService consumerMiscellaneousInformationService;

    @Autowired
    private ConsumerPanchnamaInformationService consumerPanchnamaInformationService;

    @Autowired
    private ConsumerMeterMappingService consumerMeterMappingService;

    @Autowired
    private CircleECGRFInformationService circleECGRFInformationService;

    @Autowired
    @Qualifier("misDiscomService")
    private DiscomService discomService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private EmployeeConnectionMappingService employeeConnectionMappingService;

    @Autowired
    private EEDivisionMappingService eeDivisionMappingService;

    @Autowired
    private MeterMasterService meterMasterService;

    @Autowired
    private MeterReaderInformationService meterReaderInformationService;

    @Autowired
    private NetMeterAccountingService netMeterAccountingService;

    @Autowired
    private NetMeteringArrangementService netMeteringArrangementService;

    @Autowired
    private PanchnamaPaymentInformationService panchnamaPaymentInformationService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReadMasterService readMasterService;

    @Autowired
    private ReadMasterTODService readMasterTODService;

    @Autowired
    private ReadMasterExportService readMasterExportService;

    @Autowired
    private ReadMasterGeneratorService readMasterGeneratorService;

    @Autowired
    private ReadMasterExportTODService readMasterExportTODService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private SecurityDepositService securityDepositService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private QRService qrService;

    @Autowired
    private XChartService xChartService;

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

        final DivisionInterface division = setConsumerAndConnectionInformation(templateData, consumerNo);
        setPaymentQRInformation(templateData);
        setContactInformation(templateData, division.getId());
        setBillMessage(templateData, consumerPreferredLanguage);
        setCircleECGRFInformation(templateData, division.getCircleId());

        setReadAndTODAndMeterReplacementAndVNMGNMChildInformation(templateData, billIdentifierDTO, billId);

        setReadAndPaymentHistoryAndAverageDetail(templateData);
        setBillCalculationAndAdjustmentInformation(templateData, billId);

        setAdvertisement(templateData);

        return templateData;
    }

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
            if (pdcMiscellaneous != null)
                pdcDate = pdcMiscellaneous.getEffectiveEndDate();
        }

        String billBasis = null;
        if (billIdentifierDTO.isCC4()) billBasis = "Revised Bill";
        final SecurityDepositInterface securityDeposit = securityDepositService.getConsumerNoAndEffectiveStartDateAndEffectiveEndDateOrderByIdAsc(consumerNo, bill.getBillDate());
        final BigDecimal sdHeld = (securityDeposit != null) ? securityDeposit.getAmount() : BigDecimal.ZERO;
        final BigDecimal wheelingCharges = ngbAdjustmentService.getAmountByConsumerNoAndCodeAndPostingBillMonthAndApprovalStatusAndPostedAndDeleted(consumerNo, AdjustmentInterface.WHEELING_CHARGES, billMonth, GlobalConstant.APPROVED, true, false);
        final BigDecimal todSurchargeAndRebate = GlobalUtil.add(bill.getOtherAdjustment(), bill.getXrayFixedCharge());

        Long unpostedPayment = paymentService.getAmountByConsumerNoAndPostingBillMonthAndPostedDeleted(consumerNo, billMonth, false, false);
        BigDecimal rcdcAmount = null;
        if (unpostedPayment == null) unpostedPayment = 0L;
        //121 RC/DC Management Changes
        if (unpostedPayment > 340L) {
            rcdcAmount = ngbAdjustmentService.getAmountByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeleted(consumerNo, AdjustmentInterface.ADJUSTMENT_CODE_SMART_METER_RC_DC_CHARGE, GlobalConstant.APPROVED, false, false);
            if (rcdcAmount == null) rcdcAmount = BigDecimal.ZERO;
            if (rcdcAmount.compareTo(BigDecimal.ZERO) > 0) {
                unpostedPayment = unpostedPayment - rcdcAmount.longValue();
            }
        }

        //Vigilance Changes
        BigDecimal totalPanchanamaBillAmount = BigDecimal.ZERO;
        BigDecimal totalPanchnamaPaymentAmount = BigDecimal.ZERO;
        BigDecimal vigilenceEnergyCharge = BigDecimal.ZERO;
        BigDecimal vigilenceSurcharge = BigDecimal.ZERO;
        final List<ConsumerPanchnamaInformationInterface> consumerPanchnamaInformationList = consumerPanchnamaInformationService.getByConsumerNoAndPostedAndDeleted(consumerNo, false, false);
        if (!GlobalUtil.isEmpty(consumerPanchnamaInformationList)) {
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
        BigDecimal payableAmount = GlobalUtil.subtract(bill.getNetBill(), deduction);
        payableAmount = GlobalUtil.add(payableAmount, totalPanchanamaBillAmount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal payableTotalingAmount = payableAmount;
        BigDecimal payableBeforeDueDate = payableAmount;
        BigDecimal payableAfterDueDate = payableAmount;
        if (LocalDate.now().isAfter(bill.getDueDate()) && payableAmount.compareTo(BigDecimal.ZERO) > 0) {
            payableAmount = payableAfterDueDate = GlobalUtil.add(payableBeforeDueDate, bill.getCurrentBillSurcharge());
        }
        if (payableAmount.compareTo(BigDecimal.ZERO) < 0) {
            payableAmount = payableBeforeDueDate = payableAfterDueDate = BigDecimal.ZERO;
        }

        final BillSummary billSummary = new BillSummary(String.valueOf(bill.getId()), bill.getBillMonth(), payableAmount, payableBeforeDueDate, payableAfterDueDate, bill.getCurrentBill(), bill.getArrear().add(bill.getSurchargeDemanded()), null, null, null,
                bill.getDueDate(), bill.getChequeDueDate(), bill.getBilledUnit(), null, null,
                bill.getBillDate(), billType, billBasis, null, null, sdHeld, bill.getAsdArrear(), pdcDate);

        final BigDecimal subTotalOne = GlobalUtil.add(bill.getEnergyCharge(), bill.getFcaCharge(), bill.getFixedCharge());
        final BigDecimal subTotalTwo = GlobalUtil.add(bill.getElectricityDuty(), bill.getCcbAdjustment());
        final BigDecimal penalCharge = GlobalUtil.add(bill.getAdditionalFixedCharges1(), bill.getAdditionalFixedCharges2());
        final BigDecimal subTotalThree = GlobalUtil.add(penalCharge, bill.getPfCharge(), bill.getAsdInstallment(), todSurchargeAndRebate, bill.getSdInterest(), bill.getLoadFactorIncentive(), bill.getLockCredit(), bill.getEmployeeRebate(), bill.getPrepaidMeterRebate(),
                bill.getOnlinePaymentRebate(), bill.getPromptPaymentIncentive(), bill.getAdvancePaymentIncentive(), bill.getDemandSideIncentive(), wheelingCharges);
        final BigDecimal subTotalFour = bill.getCurrentBill();
        final BillInformation billInformation = new BillInformation(bill.getEnergyCharge(), bill.getFcaCharge(), bill.getFixedCharge(), subTotalOne, bill.getElectricityDuty(), bill.getCcbAdjustment(), subTotalTwo,
                penalCharge, bill.getPfCharge(), bill.getAsdInstallment(), todSurchargeAndRebate, bill.getSdInterest(), bill.getLoadFactorIncentive(), bill.getLockCredit(), bill.getEmployeeRebate(), bill.getPrepaidMeterRebate(),
                bill.getOnlinePaymentRebate(), bill.getPromptPaymentIncentive(), bill.getAdvancePaymentIncentive(), bill.getDemandSideIncentive(), wheelingCharges == null ? BigDecimal.ZERO : wheelingCharges, subTotalThree,
                bill.getSubsidy(), subTotalFour, bill.getArrear(), bill.getCumulativeSurcharge(), bill.getAsdArrear(), bill.getNetBill(), unpostedPayment, rcdcAmount.longValue(),
                vigilenceEnergyCharge, vigilenceSurcharge, payableTotalingAmount, bill.getBilledPF(), bill.getBilledMD(), bill.getPreviousRead(), bill.getMeteredUnit(), bill.getCurrentRead(), bill.getSurchargeDemanded());

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
            templateData.getBillSummary().setPaymentQuickResponseBase64(qrService.getAsBase64Image(link, 200, 200));
        }
    }

    //Set History and Average Information
    private void setReadAndPaymentHistoryAndAverageDetail(final BillTemplate templateData) {
        if (templateData == null) return;
        final String consumerNo = templateData.getConsumerInformation().getConsumerNo();
        final String billMonth = templateData.getBillSummary().getBillMonth();

        //Reading History
        if (ConsumerConnectionInformationInterface.METERING_STATUS_METERED.equals(templateData.getConnectionInformation().getMeteringStatus())) {
            final List<ReadMasterInterface> readMasterList = readMasterService.getByConsumerNoAndReplacementFlagAndUsedOnBillAndBillMonthLessThanOrderByBillMonthDESC(consumerNo, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL, billMonth, 6);
            if (!GlobalUtil.isEmpty(readMasterList)) {
                final List<String> xAxisLabels = new ArrayList();
                final List<BigDecimal> barChartCurrentYearReadList = new ArrayList<>();
                final List<BigDecimal> barChartLastYearReadList = new ArrayList<>();
                xAxisLabels.add(billMonth.substring(0, 3));
                barChartCurrentYearReadList.add(templateData.getBillSummary().getConsumption().setScale(2, RoundingMode.HALF_UP));

                int maxIndex = readMasterList.size() - 1;
                final List<ReadHistoryInformation> readHistoryInformationList = new ArrayList<>();
                if (maxIndex >= 0) {
                    ReadMasterInterface readMaster = readMasterList.getFirst();
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(1, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                        xAxisLabels.add(readMaster.getBillMonth().substring(0, 3));
                        barChartCurrentYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));
                    }
                }
                if (maxIndex >= 1) {
                    ReadMasterInterface readMaster = readMasterList.get(1);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(2, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                        xAxisLabels.add(readMaster.getBillMonth().substring(0, 3));
                        barChartCurrentYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));
                    }
                }
                if (maxIndex >= 2) {
                    ReadMasterInterface readMaster = readMasterList.get(2);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(3, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                        xAxisLabels.add(readMaster.getBillMonth().substring(0, 3));
                        barChartCurrentYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));
                    }
                }
                if (maxIndex >= 3) {
                    ReadMasterInterface readMaster = readMasterList.get(3);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(4, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                        xAxisLabels.add(readMaster.getBillMonth().substring(0, 3));
                        barChartCurrentYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));
                    }
                }
                if (maxIndex >= 4) {
                    ReadMasterInterface readMaster = readMasterList.get(4);
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(5, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                        xAxisLabels.add(readMaster.getBillMonth().substring(0, 3));
                        barChartCurrentYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));
                    }
                }
                //Last year's same month reading
                final int year = Integer.parseInt(billMonth.substring(4));
                final String lastYearMonth = billMonth.substring(0, 4).concat(String.valueOf(year - 1));
                final Sort sort = GlobalUtil.getSortObject(SortOrder.DESC);
                List<ReadMasterInterface> lastYearReadMasterInterfaces = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, lastYearMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL, sort);
                if (lastYearReadMasterInterfaces != null && lastYearReadMasterInterfaces.size() == 1) {
                    ReadMasterInterface readMaster = lastYearReadMasterInterfaces.getFirst();
                    if (readMaster != null) {
                        final ReadHistoryInformation readHistoryInformation = new ReadHistoryInformation(6, readMaster.getBillMonth(), readMaster.getReadingDate(), readMaster.getReading(), readMaster.getTotalConsumption());
                        readHistoryInformationList.add(readHistoryInformation);
                        barChartLastYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));
                    }
                }

                if (!GlobalUtil.isEmpty(xAxisLabels) && !GlobalUtil.isEmpty(barChartCurrentYearReadList)) {
                    final Map<String, List<BigDecimal>> barChartData = new HashMap<>();
                    barChartData.put("This Year", barChartCurrentYearReadList);

                    final List<ReadMasterInterface> lastYearReadMasterList = readMasterService.getByConsumerNoAndReplacementFlagAndUsedOnBillAndBillMonthLessThanOrderByBillMonthDESC(consumerNo, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL, lastYearMonth, 5);
                    if (!GlobalUtil.isEmpty(lastYearReadMasterList)) {
                        for (ReadMasterInterface readMaster : lastYearReadMasterList)
                            barChartLastYearReadList.add(readMaster.getTotalConsumption().setScale(2, RoundingMode.HALF_UP));

                        if (barChartCurrentYearReadList.size() == barChartLastYearReadList.size())
                            barChartData.put("Last Year", barChartLastYearReadList);
                    }

                    templateData.getBillSummary().setConsumptionTrendBarChartBase64(xChartService.getBarChartBase64Image(xAxisLabels, barChartData, 742, 140));
                }

                templateData.setReadHistoryInformationList(readHistoryInformationList);
            }
        }

        //Payment History
        final List<PaymentInterface> paymentList = paymentService.getByConsumerNoAndDeletedAndPostingBillMonthLessThanEqualOrderByPayDateDESC(consumerNo, false, billMonth, 2);
        if (!GlobalUtil.isEmpty(paymentList)) {
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
            if (!GlobalUtil.isEmpty(templateData.getReadHistoryInformationList())) {
                final LocalDate previousReadDate = templateData.getReadHistoryInformationList().getFirst().getReadDate();
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
        if (!GlobalUtil.isEmpty(adjustmentList)) {
            final List<AdjustmentInformation> adjustmentInformationList = new ArrayList<>();
            for (int index = 0; index < adjustmentList.size(); index++) {
                final AdjustmentInterface adjustment = adjustmentList.get(index);
                final AdjustmentInformation adjustmentInformation = new AdjustmentInformation(index + 1, adjustmentTypeService.getDescriptionByCode(adjustment.getCode()), adjustment.getAmount());
                adjustmentInformationList.add(adjustmentInformation);
            }
            templateData.setAdjustmentInformationList(adjustmentInformationList);
        }

        final List<BillCalculationLineInterface> billCalculationLineList = billCalculationLineService.getByBillIdAndHeadIn(billId, Arrays.asList(BillCalculationLineInterface.HEAD_ENERGY_CHARGE, BillCalculationLineInterface.HEAD_FIXED_CHARGE));
        if (!GlobalUtil.isEmpty(billCalculationLineList)) {
            billCalculationLineList.sort(Comparator.comparing(BillCalculationLineInterface::getHead).thenComparingLong(calc -> Long.valueOf(calc.getStartSlab())));
            final List<BillCalculationInformation> billCalculationInformationList = new ArrayList<>();
            for (int index = 0; index < billCalculationLineList.size(); index++) {
                BillCalculationLineInterface billCalculationLine = billCalculationLineList.get(index);
                final String slab = (StringUtils.isNotEmpty(billCalculationLine.getStartSlab())) ? billCalculationLine.getStartSlab() + " - " + billCalculationLine.getEndSlab() : "";
                billCalculationInformationList.add(new BillCalculationInformation(index + 1, billCalculationLine.getHead(), slab, billCalculationLine.getRate(),
                        billCalculationLine.getUnit(), billCalculationLine.getAmount()));
            }
            templateData.setBillCalculationInformationList(billCalculationInformationList);
        }
    }

    //Set Vigilance Information
    private void setVigilanceInformation(final BillTemplate templateData, final List<ConsumerPanchnamaInformationInterface> consumerPanchnamaInformationList) {
        if (templateData == null || GlobalUtil.isEmpty(consumerPanchnamaInformationList)) return;

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
                (String) consumerInformationObj[2], (String) consumerInformationObj[4], GlobalUtil.maskMobileNO((String) consumerInformationObj[5]), GlobalUtil.maskEmailId((String) consumerInformationObj[6]), GlobalUtil.genericMask(employeeNo), (String) consumerInformationObj[11]);
        templateData.setConsumerInformation(consumerInformation);

        final ZoneInterface zone = zoneService.getByCode(locationCode);
        final DivisionInterface division = divisionService.getById(zone.getDivisionId());
        final Object[] consumerConnectionInformationObj = consumerConnectionInformationService.getForBillTemplateByConsumerNo(consumerNo);
        final LocalDate connectionDate = (LocalDate) consumerConnectionInformationObj[10];

        final ConnectionInformation connectionInformation = new ConnectionInformation(division.getName(), zone.getName(), locationCode, (String) consumerInformationObj[9], (String) consumerInformationObj[10],
                (String) consumerConnectionInformationObj[14], (String) consumerConnectionInformationObj[13], (String) consumerConnectionInformationObj[12], (String) consumerConnectionInformationObj[11],
                (String) consumerConnectionInformationObj[4], (String) consumerConnectionInformationObj[0], (String) consumerConnectionInformationObj[1], connectionDate,
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

        final MeterReaderInformationInterface meterReaderInformation = meterReaderInformationService.getByReadingDiaryNoId(Long.parseLong(templateData.getConnectionInformation().getReadingDiaryNo()));
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
                        final BigDecimal totalPendingInstallmentAmount = adjustmentList.stream().filter(a -> !a.isPosted()).map(AdjustmentInterface::getAmount).reduce(GlobalUtil::add).orElse(BigDecimal.ZERO);

                        argList.add(otsType);
                        argList.add(tagDate);
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

        final List<CircleECGRFInformationInterface> circleECGRFInformationList = circleECGRFInformationService.getByCircleIdAndMonthOrderByIndexAsc(circleId, templateData.getBillSummary().getBillMonth());
        if (circleECGRFInformationList == null || circleECGRFInformationList.isEmpty()) return;

        final List<ECGRFInformation> ecgrfInformationList = new ArrayList<>();
        for (int index = 0; index < circleECGRFInformationList.size(); index++) {
            final CircleECGRFInformationInterface circleECGRFInformation = circleECGRFInformationList.get(index);
            final ECGRFInformation ecgrfInformation = new ECGRFInformation(index + 1, circleECGRFInformation.getMemberType(), circleECGRFInformation.getName(), circleECGRFInformation.getContactNo(), circleECGRFInformation.getCaseHandling());
            ecgrfInformationList.add(ecgrfInformation);
        }

        if (!ecgrfInformationList.isEmpty()) templateData.setEcgrfInformationList(ecgrfInformationList);
    }

    //Setting Read, Meter replacement and VNM GNM Child Information
    private void setReadAndTODAndMeterReplacementAndVNMGNMChildInformation(final BillTemplate templateData, final BillIdentifierDTO billIdentifierDTO, final long billId) {
        if (templateData == null) return;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();
        boolean isEligibleForTOD = false;
        final boolean isNetMeter = templateData.getConnectionInformation().isNetMeter();
        final ReadInformation readInformation = new ReadInformation();
        final Sort sort = GlobalUtil.getSortObject(SortOrder.DESC);
        final List<ReadMasterInterface> readMasterList = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true, sort);
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

                isEligibleForTOD = isEligibleForTOD(templateData.getConnectionInformation());
                if (isEligibleForTOD) {
                    readInformation.setTOD(true);
                    setTODInformation(templateData, billId, readMaster.getId());
                }
            }
        }

        templateData.setReadInformation(readInformation);

        final ConnectionInformation connectionInformation = templateData.getConnectionInformation();
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

            //Export Read Detail
            final String previousBillMonth = GlobalUtil.getPreviousMonth(billMonth);
            final List<ReadMasterExportInterface> readMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true, sort);
            long readMasterExportId = 0L;
            long previousReadMasterExportId = 0L;
            if (!GlobalUtil.isEmpty(readMasterExportList) && readMasterExportList.size() == 1) {
                final ReadMasterExportInterface readMasterExport = readMasterExportList.getFirst();
                readMasterExportId = readMasterExport.getId();
                BigDecimal readMasterExportPreviousRead = null;
                List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(consumerNo, previousBillMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true, sort);
                if (GlobalUtil.isEmpty(previousReadMasterExportList)) {
                    previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlagAndUsedOnBill(consumerNo, ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION, true, sort);
                }
                if (!GlobalUtil.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                    readMasterExportPreviousRead = previousReadMasterExportList.getFirst().getReading();
                    previousReadMasterExportId = previousReadMasterExportList.getFirst().getId();
                }

                BigDecimal importMeteredUnit = readInformation.getReadDetailList().getFirst().getMeteredUnit();
                BigDecimal importBilledUnit = readInformation.getReadDetailList().getFirst().getBilledUnit();
                readInformation.setNetMeteredUnit(importMeteredUnit.subtract(readMasterExport.getConsumption()));
                readInformation.setNetBilledUnit(importBilledUnit.subtract(readMasterExport.getTotalConsumption()));

                readInformation.addRead(1, "EXPORT", readMasterExport.getReading(), readMasterExportPreviousRead,
                        readMasterExport.getMf(), readMasterExport.getConsumption(), readMasterExport.getAssessment(), readMasterExport.getTotalConsumption());
            }

            //Generator Read Detail
            /*final List<ReadMasterGeneratorInterface> readMasterGeneratorList = readMasterGeneratorService.getByConsumerNoAndBillMonthAndReplacementFlag(consumerNo, billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
            if (!GlobalUtility.isEmpty(readMasterGeneratorList) && readMasterGeneratorList.size() == 1) {
                BigDecimal readMasterGeneratorPreviousRead = null;
                final List<ReadMasterGeneratorInterface> previousReadMasterGeneratorList = readMasterGeneratorService.getByConsumerNoAndBillMonthAndReplacementFlag(consumerNo, previousBillMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, sort);
                if (!GlobalUtility.isEmpty(previousReadMasterGeneratorList) && previousReadMasterGeneratorList.size() == 1) {
                    readMasterGeneratorPreviousRead = previousReadMasterGeneratorList.getFirst().getReading();
                }

                final ReadMasterGeneratorInterface readMasterGenerator = readMasterGeneratorList.getFirst();
                readInformation.addRead(2, "GENERATOR", readMasterGenerator.getReading(), readMasterGeneratorPreviousRead,
                        readMasterGenerator.getMf(), readMasterGenerator.getConsumption(), readMasterGenerator.getAssessment(), readMasterGenerator.getTotalConsumption());
            }*/

            final NetMeterAccountingInterface netMeterAccounting = netMeterAccountingService.getByBillId(billId);
            if (netMeterAccounting != null) {
                readInformation.setResidualUnit(netMeterAccounting.getResidualUnit());
                readInformation.setResidualUnitAdjusted(netMeterAccounting.getUsedExportUnit());
            }
            templateData.setReadInformation(readInformation);

            //Export TOD Detail
            if (isEligibleForTOD) {
                final ReadMasterExportTODInterface readMasterExportTOD = readMasterExportTODService.getByReadMasterExportId(readMasterExportId);
                final ReadMasterExportTODInterface previousReadMasterExportTOD = readMasterExportTODService.getByReadMasterExportId(previousReadMasterExportId);
                if (readMasterExportTOD != null) {
                    final List<TODNetMeterDetail> todNetMeterDetailList = templateData.getTodInformation().getTodNetMeterDetailList();

                    todNetMeterDetailList.add(new TODNetMeterDetail(1, ReadMasterTODInterface.TOD1, "Peak (10 PM to 6 AM)", "EXPORT", readMasterExportTOD.getTod1Reading(), (previousReadMasterExportTOD != null) ? previousReadMasterExportTOD.getTod1Reading() : null,
                            readMasterExportTOD.getTod1Consumption(), readMasterExportTOD.getTod1Assessment(), readMasterExportTOD.getTod1TotalConsumption(), null, null));
                    todNetMeterDetailList.add(new TODNetMeterDetail(3, ReadMasterTODInterface.TOD2, "Peak (6 AM to 9 AM)", "EXPORT", readMasterExportTOD.getTod2Reading(), (previousReadMasterExportTOD != null) ? previousReadMasterExportTOD.getTod2Reading() : null,
                            readMasterExportTOD.getTod2Consumption(), readMasterExportTOD.getTod2Assessment(), readMasterExportTOD.getTod2TotalConsumption(), null, null));
                    todNetMeterDetailList.add(new TODNetMeterDetail(5, ReadMasterTODInterface.TOD3, "Off Peak (9 AM to 5 PM)", "EXPORT", readMasterExportTOD.getTod3Reading(), (previousReadMasterExportTOD != null) ? previousReadMasterExportTOD.getTod3Reading() : null,
                            readMasterExportTOD.getTod3Consumption(), readMasterExportTOD.getTod3Assessment(), readMasterExportTOD.getTod3TotalConsumption(), null, null));
                    todNetMeterDetailList.add(new TODNetMeterDetail(7, ReadMasterTODInterface.TOD4, "Peak (5 PM to  10 PM)", "EXPORT", readMasterExportTOD.getTod4Reading(), (previousReadMasterExportTOD != null) ? previousReadMasterExportTOD.getTod4Reading() : null,
                            readMasterExportTOD.getTod4Consumption(), readMasterExportTOD.getTod4Assessment(), readMasterExportTOD.getTod4TotalConsumption(), null, null));

                    todNetMeterDetailList.getFirst().setNetConsumption(todNetMeterDetailList.getFirst().getFinalConsumption().subtract(readMasterExportTOD.getTod1TotalConsumption()));
                    todNetMeterDetailList.get(1).setNetConsumption(todNetMeterDetailList.get(1).getFinalConsumption().subtract(readMasterExportTOD.getTod2TotalConsumption()));
                    todNetMeterDetailList.get(2).setNetConsumption(todNetMeterDetailList.get(2).getFinalConsumption().subtract(readMasterExportTOD.getTod3TotalConsumption()));
                    todNetMeterDetailList.get(3).setNetConsumption(todNetMeterDetailList.get(3).getFinalConsumption().subtract(readMasterExportTOD.getTod4TotalConsumption()));

                    todNetMeterDetailList.sort(Comparator.comparingInt(TODNetMeterDetail::getIndex));
                }
            }

            //VNM GNM Child Detail
            if (connectionInformation.isNetMeterParent()) {
                final List<NetMeteringArrangementInterface> netMetereteringChildList = netMeteringArrangementService.getNonParentByParentConsumerNoAndBillMonthBetween(consumerNo, billMonth);
                if (!GlobalUtil.isEmpty(netMetereteringChildList)) {
                    final List<NetMeterChildInformation> netMeterChildInformationList = new ArrayList<>();
                    for (int index = 0; index < netMetereteringChildList.size(); index++) {
                        final NetMeteringArrangementInterface netMeteringArrangement = netMetereteringChildList.get(index);
                        final List<ReadMasterExportInterface> childReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(netMeteringArrangement.getConsumerNo(), billMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true, sort);
                        if (!GlobalUtil.isEmpty(childReadMasterExportList) && childReadMasterExportList.size() == 1) {
                            ReadMasterExportInterface readMasterExport = childReadMasterExportList.getFirst();
                            BigDecimal previousReadMasterExportRead = null;
                            List<ReadMasterExportInterface> previousReadMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(netMeteringArrangement.getConsumerNo(), previousBillMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, true, sort);
                            if (GlobalUtil.isEmpty(previousReadMasterExportList)) {
                                previousReadMasterExportList = readMasterExportService.getByConsumerNoAndReplacementFlagAndUsedOnBill(netMeteringArrangement.getConsumerNo(), ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION, true, sort);
                            }
                            if (!GlobalUtil.isEmpty(previousReadMasterExportList) && previousReadMasterExportList.size() == 1) {
                                final ReadMasterExportInterface previousReadMasterExport = previousReadMasterExportList.getFirst();
                                previousReadMasterExportRead = previousReadMasterExport.getReading();
                            }

                            netMeterChildInformationList.add(new NetMeterChildInformation(index + 1, consumerNo, netMeteringArrangement.getRatio(), readMasterExport.getReading(),
                                    previousReadMasterExportRead, readMasterExport.getMf(), readMasterExport.getConsumption(), readMasterExport.getAssessment(), readMasterExport.getTotalConsumption()));
                        }
                    }
                    templateData.setNetMeterChildInformationList(netMeterChildInformationList);
                }
            }
        }

        setMeterReplacementInformation(templateData, billIdentifierDTO);
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
                    : GlobalUtil.convertHPToKW(connectionInformation.getContractDemand()).setScale(0, RoundingMode.HALF_UP);
            if (contractDemandInKW != null && contractDemandInKW.compareTo(BigDecimal.TEN) > 0) {
                isEligibleForTOD = true;
            }
        }
        return isEligibleForTOD;
    }

    //Setting TOD / Import TOD Information
    private void setTODInformation(final BillTemplate templateData, final long billId, final long readMasterId) {
        if (templateData == null) return;
        final boolean isNetMeter = templateData.getConnectionInformation().isNetMeter();

        final ReadMasterTODInterface readMasterTOD = readMasterTODService.getByReadMasterId(readMasterId);
        final BillTODInterface billTOD = billTODService.getByBillId(billId);
        if (readMasterTOD != null && billTOD != null) {
            final TODInformation todInformation = new TODInformation();
            if (isNetMeter) {
                long previousReadMasterId = 0L;
                final Sort sort = GlobalUtil.getSortObject(SortOrder.DESC);
                final String previousBillMonth = GlobalUtil.getPreviousMonth(templateData.getBillSummary().getBillMonth());
                final List<ReadMasterInterface> previousReadMasterList = readMasterService.getByConsumerNoAndBillMonthAndReplacementFlagAndUsedOnBill(templateData.getConsumerInformation().getConsumerNo(), previousBillMonth, ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ, ReadMasterInterface.USED_ON_BILL, sort);
                if (!GlobalUtil.isEmpty(previousReadMasterList)) {
                    final ReadMasterInterface previousReadMaster = previousReadMasterList.getFirst();
                    previousReadMasterId = previousReadMaster.getId();
                }
                final ReadMasterTODInterface previousReadMasterTOD = readMasterTODService.getByReadMasterId(previousReadMasterId);

                final List<TODNetMeterDetail> todNetMeterDetailList = new ArrayList<>();
                todNetMeterDetailList.add(new TODNetMeterDetail(0, ReadMasterTODInterface.TOD1, "Peak (10 PM to 6 AM)", "IMPORT", readMasterTOD.getTod1Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod1Reading() : null,
                        readMasterTOD.getTod1Consumption(), readMasterTOD.getTod1Assessment(), readMasterTOD.getTod1TotalConsumption(), null, billTOD.getTod1()));
                todNetMeterDetailList.add(new TODNetMeterDetail(2, ReadMasterTODInterface.TOD2, "Peak (6 AM to 9 AM)", "IMPORT", readMasterTOD.getTod2Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod2Reading() : null,
                        readMasterTOD.getTod2Consumption(), readMasterTOD.getTod2Assessment(), readMasterTOD.getTod2TotalConsumption(), null, billTOD.getTod2()));
                todNetMeterDetailList.add(new TODNetMeterDetail(4, ReadMasterTODInterface.TOD3, "Off Peak (9 AM to 5 PM)", "IMPORT", readMasterTOD.getTod3Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod3Reading() : null,
                        readMasterTOD.getTod3Consumption(), readMasterTOD.getTod3Assessment(), readMasterTOD.getTod3TotalConsumption(), null, billTOD.getTod3()));
                todNetMeterDetailList.add(new TODNetMeterDetail(6, ReadMasterTODInterface.TOD4, "Peak (5 PM to  10 PM)", "IMPORT", readMasterTOD.getTod4Reading(), (previousReadMasterTOD != null) ? previousReadMasterTOD.getTod4Reading() : null,
                        readMasterTOD.getTod4Consumption(), readMasterTOD.getTod4Assessment(), readMasterTOD.getTod4TotalConsumption(), null, billTOD.getTod4()));

                todInformation.setTodNetMeterDetailList(todNetMeterDetailList);
            } else {
                todInformation.setTotalUnit(GlobalUtil.add(readMasterTOD.getTod1TotalConsumption(), readMasterTOD.getTod2TotalConsumption(), readMasterTOD.getTod3TotalConsumption(), readMasterTOD.getTod4TotalConsumption()));
                todInformation.setTotalAmount(GlobalUtil.add(billTOD.getTod1(), billTOD.getTod2(), billTOD.getTod3(), billTOD.getTod4()));
                final List<TODDetail> todDetailList = new ArrayList<>();
                todDetailList.add(new TODDetail(0, ReadMasterTODInterface.TOD1, "Peak", "10 PM to 6 AM", readMasterTOD.getTod1TotalConsumption(), billTOD.getTod1()));
                todDetailList.add(new TODDetail(1, ReadMasterTODInterface.TOD2, "Peak", "6 AM to 9 AM", readMasterTOD.getTod2TotalConsumption(), billTOD.getTod2()));
                todDetailList.add(new TODDetail(2, ReadMasterTODInterface.TOD3, "Off Peak", "9 AM to 5 PM", readMasterTOD.getTod3TotalConsumption(), billTOD.getTod3()));
                todDetailList.add(new TODDetail(3, ReadMasterTODInterface.TOD4, "Peak", "5 PM to  10 PM", readMasterTOD.getTod4TotalConsumption(), billTOD.getTod4()));

                Map<String, BigDecimal> pieChartMap = new TreeMap<>(
                        Map.of(ReadMasterTODInterface.TOD1, readMasterTOD.getTod1TotalConsumption().setScale(2, RoundingMode.HALF_UP),
                                ReadMasterTODInterface.TOD2, readMasterTOD.getTod2TotalConsumption().setScale(2, RoundingMode.HALF_UP),
                                ReadMasterTODInterface.TOD3, readMasterTOD.getTod3TotalConsumption().setScale(2, RoundingMode.HALF_UP),
                                ReadMasterTODInterface.TOD4, readMasterTOD.getTod4TotalConsumption().setScale(2, RoundingMode.HALF_UP)));
                todInformation.setTodPieChartBase64Image(xChartService.getPieChartBase64Image(pieChartMap, 350, 172));

                todInformation.setTodDetailList(todDetailList);
            }
            templateData.setTodInformation(todInformation);
        }
    }

    //Setting Meter Replacement Information
    private void setMeterReplacementInformation(final BillTemplate templateData, final BillIdentifierDTO billIdentifierDTO) {
        if (templateData == null) return;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();
        final boolean isNetMeter = templateData.getConnectionInformation().isNetMeter();

        final List<ConsumerMeterMappingInterface> meterMappingList = consumerMeterMappingService.getByConsumerNoAndBillMonthOrderByInstallationDateDescIdDesc(consumerNo, billMonth);
        if (GlobalUtil.isEmpty(meterMappingList)) return;

        final MeterReplacementInformation meterReplacementInformation = new MeterReplacementInformation();

        if (isNetMeter) {
            final Sort sort = GlobalUtil.getSortObject(SortOrder.DESC);
            final List<NetMeterReplacementDetail> meterReplacementDetailList = new ArrayList<>();
            final BillSummary billSummary = templateData.getBillSummary();
            final BillInformation billInformation = templateData.getBillInformation();
            for (int index = 0; index < meterMappingList.size(); index++) {
                ConsumerMeterMappingInterface meterMapping = meterMappingList.get(index);
                BigDecimal finalRead = meterMapping.getFinalRead();
                if (finalRead == null) finalRead = billInformation.getCurrentRead();
                BigDecimal importConsumption = finalRead.subtract(meterMapping.getStartRead());
                BigDecimal exportFinalRead = null;
                BigDecimal exportStartRead = null;
                BigDecimal exportConsumption = null;
                BigDecimal netConsumption = null;
                final List<ReadMasterExportInterface> readMasterExportList = readMasterExportService.getByConsumerNoAndBillMonthAndMeterIdentifier(consumerNo, billMonth, meterMapping.getMeterIdentifier(), sort);
                if (!GlobalUtil.isEmpty(readMasterExportList)) {
                    for (ReadMasterExportInterface readMasterExport : readMasterExportList) {
                        if (ReadMasterInterface.REPLACEMENT_FLAG_START_READ.equals(readMasterExport.getReplacementFlag())
                                || ReadMasterInterface.REPLACEMENT_FLAG_NEW_CONNECTION.equals(readMasterExport.getReplacementFlag())) {
                            exportStartRead = readMasterExport.getReading();
                        }
                        if (ReadMasterInterface.REPLACEMENT_FLAG_FINAL_READ.equals(readMasterExport.getReplacementFlag())
                                || ReadMasterInterface.REPLACEMENT_FLAG_NORMAL_READ.equals(readMasterExport.getReplacementFlag())) {
                            exportFinalRead = readMasterExport.getReading();
                        }
                    }
                    if (exportFinalRead != null && exportStartRead != null) {
                        exportConsumption = exportFinalRead.subtract(exportStartRead);
                        netConsumption = exportConsumption.subtract(importConsumption);
                    }
                }

                meterReplacementDetailList.add(new NetMeterReplacementDetail(index + 1, meterMapping.getMeterIdentifier(), meterMapping.getRemovalDate(), meterMapping.getStartRead()
                        , finalRead, importConsumption, exportStartRead, exportFinalRead, exportConsumption, netConsumption));
                meterReplacementInformation.setNetMeterReplacementDetailList(meterReplacementDetailList);
            }
        } else {
            final List<MeterReplacementDetail> meterReplacementDetailList = new ArrayList<>();
            final BillSummary billSummary = templateData.getBillSummary();
            final BillInformation billInformation = templateData.getBillInformation();
            for (int index = 0; index < meterMappingList.size(); index++) {
                ConsumerMeterMappingInterface meterMapping = meterMappingList.get(index);
                BigDecimal finalRead = meterMapping.getFinalRead();
                if (finalRead == null) finalRead = billInformation.getCurrentRead();

                final BigDecimal proratedBillAmount = billSummary.getCurrentBill().subtract(billInformation.getSurchargeDemanded()).divide(billSummary.getConsumption(), 3, RoundingMode.HALF_UP).multiply(finalRead.subtract(meterMapping.getStartRead()));
                meterReplacementDetailList.add(new MeterReplacementDetail(index + 1, meterMapping.getMeterIdentifier(), meterMapping.getRemovalDate(), meterMapping.getStartRead()
                        , finalRead, finalRead.subtract(meterMapping.getStartRead()), proratedBillAmount.setScale(3, RoundingMode.HALF_UP)));
                meterReplacementInformation.setMeterReplacementDetailList(meterReplacementDetailList);
            }
        }

        templateData.setMeterReplacementInformation(meterReplacementInformation);
    }

    //Setting Meter Replacement Information
    private void setAdvertisement(final BillTemplate templateData) {
        if (templateData == null) return;
        final AdvertisementInformation advertisementInformation = new AdvertisementInformation();

        String pageOneZeroAdBase64 = resourceService.convertImageToBase64(resourceService.getImage("zero.jpeg", ResourceService.IMAGE_TYPE_BILL_ADVERTISEMENT));
        if (StringUtils.isNotEmpty(pageOneZeroAdBase64)) {
            pageOneZeroAdBase64 = ResourceService.JPEG_HTML_BASE54_PREFIX + pageOneZeroAdBase64;
            advertisementInformation.setPageOneZeroBase64Image(pageOneZeroAdBase64);
        }

        String pageTwoOneAdBase64 = resourceService.convertImageToBase64(resourceService.getImage("one.jpeg", ResourceService.IMAGE_TYPE_BILL_ADVERTISEMENT));
        if (StringUtils.isNotEmpty(pageTwoOneAdBase64)) {
            pageTwoOneAdBase64 = ResourceService.JPEG_HTML_BASE54_PREFIX + pageTwoOneAdBase64;
            advertisementInformation.setPageTwoOneBase64Image(pageTwoOneAdBase64);
        }

        final List<URL> imageUrlList = resourceService.getImageFromDirectoryExcept(ResourceService.IMAGE_TYPE_BILL_ADVERTISEMENT, ResourceService.IMAGE_EXTENSION_JPEG, Arrays.asList("zero.jpeg", "one.jpeg"));
        if (!GlobalUtil.isEmpty(imageUrlList)) {
            final List<String> lastPageBase64ImageList = new ArrayList<>();
            for (URL url : imageUrlList) {
                String base64Ad = resourceService.convertImageToBase64(url);
                if (StringUtils.isNotEmpty(base64Ad)) {
                    base64Ad = ResourceService.JPEG_HTML_BASE54_PREFIX + base64Ad;
                    lastPageBase64ImageList.add(base64Ad);
                }
            }
            if (!GlobalUtil.isEmpty(lastPageBase64ImageList))
                advertisementInformation.setLastPageBase64Image(lastPageBase64ImageList);
        }

        templateData.setAdvertisementInformation(advertisementInformation);
    }
}
