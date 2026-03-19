package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.dtos.response.MessageDTO;
import com.mppkvvcl.jteone.dtos.templates.BillFRT6MonthlyTemplate;
import com.mppkvvcl.jteone.dtos.templates.pdfbill.*;
import com.mppkvvcl.jteone.service.daos.mis.BillMasterAgricultureService;
import com.mppkvvcl.jteone.service.daos.mis.BillMessageService;
import com.mppkvvcl.jteone.service.daos.mis.DiscomService;
import com.mppkvvcl.jteone.service.daos.ngb.AdjustmentService;
import com.mppkvvcl.jteone.service.daos.ngb.ConsumerMiscellaneousInformationService;
import com.mppkvvcl.jteone.service.daos.ngb.PaymentService;
import com.mppkvvcl.jteone.service.daos.ngb.UserDetailService;
import com.mppkvvcl.jteone.utility.GlobalConstant;
import com.mppkvvcl.jteone.utility.GlobalUtil;
import com.mppkvvcl.jteone.utility.UrjasCypher;
import com.mppkvvcl.misdao.interfaces.BillMasterAgricultureInterface;
import com.mppkvvcl.misdao.interfaces.DiscomInterface;
import com.mppkvvcl.ngbdao.dtos.BillIdentifierDTO;
import com.mppkvvcl.ngbdao.interfaces.AdjustmentInterface;
import com.mppkvvcl.ngbdao.interfaces.ConsumerMiscellaneousInformationInterface;
import com.mppkvvcl.ngbdao.interfaces.UserDetailInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class Agriculture6MonthlyFRTNgbTemplateService {
    private static final Logger log = LoggerFactory.getLogger(Agriculture6MonthlyFRTNgbTemplateService.class);

    @Autowired
    private BillMasterAgricultureService billMasterAgricultureService;

    @Autowired
    @Qualifier("ngbAdjustmentService")
    private AdjustmentService ngbAdjustmentService;

    @Autowired
    private ConsumerMiscellaneousInformationService consumerMiscellaneousInformationService;

    @Autowired
    @Qualifier("misDiscomService")
    private DiscomService discomService;

    @Autowired
    private BillMessageService billMessageService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QRService qrService;

    @Autowired
    private UserDetailService userDetailService;

    public BillFRT6MonthlyTemplate prepareBillTemplateObject(final BillIdentifierDTO billIdentifierDTO, final String templateVersion, final MessageDTO messageDTO) {
        if (billIdentifierDTO == null || StringUtils.isEmpty(templateVersion))
            return null;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();
        final String consumerPreferredLanguage = billIdentifierDTO.getConsumerPreferredLanguage();
        final BillFRT6MonthlyTemplate templateData = new BillFRT6MonthlyTemplate();

        final BillMasterAgricultureInterface bill = setBillInformation(templateData, billIdentifierDTO, messageDTO);
        if (bill == null || StringUtils.isNotEmpty(messageDTO.getMessage())) return null;

        setTemplateInformation(templateData, templateVersion);
        setCompanyInformation(templateData);

        setConsumerAndConnectionInformation(templateData, bill);
        setContactInformation(templateData);

        setPaymentQRInformation(templateData);
        setBillMessage(templateData, consumerPreferredLanguage);

        return templateData;
    }

    //Setting Template information
    private void setTemplateInformation(final BillFRT6MonthlyTemplate templateData, final String templateVersion) {
        if (templateData == null) return;

        templateData.setTemplateInformation(new TemplateInformation(templateVersion, null, null));
    }

    //Setting Company Information
    private void setCompanyInformation(final BillFRT6MonthlyTemplate templateData) {
        if (templateData == null) return;

        final DiscomInterface discom = discomService.get();
        if (discom == null) return;

        final CompanyInformation companyInformation = new CompanyInformation(discom.getShortName(), discom.getFullName(), discom.getAddress(), discom.getFullNameH(), discom.getAddressH(), discom.getSupportEmail(), discom.getOfficialWebsite(), discom.getCallCenterNo(),
                discom.getGstNo(), discom.getCin(), discom.getWhatsappNo(), discom.getWhatsappLink(), discomService.getWhatsappQRAsBase64(), null, discomService.getDiscomLogoAsBase64(), null, discomService.getMPStateLogoAsBase64());
        templateData.setCompanyInformation(companyInformation);
    }

    //Setting Bill Message
    private void setBillMessage(final BillFRT6MonthlyTemplate templateData, String language) {
        if (templateData == null) return;
        final FRT6MonthlyBillInformation billInformation = templateData.getBillInformation();
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

            BigDecimal otsSurchargeWaiver = null;
            List<AdjustmentInterface> adjustmentList = ngbAdjustmentService.getByConsumerNoAndCodeAndApprovalStatusAndPostedAndDeletedOrderByPostingBillMonthASC(consumerNo, AdjustmentInterface.OTS_SCHEME_CUMULATIVE_SURCHARGE_WAIVER_ADJUSTMENT_CODE, GlobalConstant.APPROVED, true, false);
            if (adjustmentList != null && !adjustmentList.isEmpty()) {
                otsSurchargeWaiver = adjustmentList.stream().map(AdjustmentInterface::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            }

            if (GlobalConstant.OTS_OPTION_LUMPSUM.equals(otsMiscellaneous.getPropertyValue())) {
                if (otsSurchargeWaiver != null) {
                    argList.add(otsType);
                    argList.add(otsSurchargeWaiver.toPlainString());
                }
            } else if (GlobalConstant.OTS_OPTION_INSTALLMENT.equals(otsMiscellaneous.getPropertyValue())) {
                if (!GlobalConstant.DEFAULTER.equals(otsType)) {
                    final LocalDate tagDate = otsMiscellaneous.getEffectiveStartDate();
                    adjustmentList = ngbAdjustmentService.getByConsumerNoAndCodeAndApprovalStatusAndDeletedOrderByPostingBillMonthASC(consumerNo, AdjustmentInterface.OTS_SCHEME_ARREAR_INSTALLMENT_ADJUSTMENT_CODE, GlobalConstant.APPROVED, false);
                    if (adjustmentList != null && !adjustmentList.isEmpty() && adjustmentList.stream().anyMatch(adj -> !adj.isPosted())) {
                        //If installment is going on
                        final int installmentCount = adjustmentList.size();
                        final BigDecimal installmentAmount = adjustmentList.getFirst().getAmount();
                        final String firstMonth = adjustmentList.getFirst().getPostingBillMonth();
                        final String lastMonth = adjustmentList.get(installmentCount - 1).getPostingBillMonth();
                        final long pendingInstallmentCount = adjustmentList.stream().filter(a -> !a.isPosted()).count();
                        final BigDecimal totalPendingInstallmentAmount = adjustmentList.stream().filter(a -> !a.isPosted()).map(AdjustmentInterface::getAmount).reduce(GlobalUtil::add).orElse(BigDecimal.ZERO);
                        final BigDecimal totalPostedInstallmentAmount = adjustmentList.stream().filter(a -> a.isPosted()).map(AdjustmentInterface::getAmount).reduce(GlobalUtil::add).orElse(BigDecimal.ZERO);

                        argList.add(otsType);
                        argList.add(tagDate);
                        argList.add(String.valueOf(installmentCount));
                        argList.add(installmentAmount.toPlainString());
                        argList.add(firstMonth);
                        argList.add(lastMonth);
                        argList.add(String.valueOf(pendingInstallmentCount));
                        argList.add(installmentAmount.toPlainString());
                        argList.add(totalPendingInstallmentAmount.toPlainString());

                        billInformation.setOts2025postedInstallmentAmount(totalPostedInstallmentAmount);
                        billInformation.setOts2025UnpostedInstallmentAmount(totalPendingInstallmentAmount);
                    } else {
                        //Matured
                        if (otsSurchargeWaiver != null) {
                            argList.add(GlobalConstant.OTS_OPTION_LUMPSUM);
                            argList.add(otsSurchargeWaiver.toPlainString());
                        }
                    }
                }
            }

            //Updating Bill amount as per OTS detail
            billInformation.setOts2025SurchargeWaiver(otsSurchargeWaiver);
            final BigDecimal diff = GlobalUtil.add(billInformation.getOts2025SurchargeWaiver(), billInformation.getOts2025UnpostedInstallmentAmount());
            billInformation.setNetBill(GlobalUtil.subtract(billInformation.getNetBill(), diff));
            if (billInformation.getPayableAmount().compareTo(BigDecimal.ZERO) > 0) {
                billInformation.setPayableAmount(GlobalUtil.subtract(billInformation.getPayableAmount(), diff));
                billInformation.setPayableTillDueDate(GlobalUtil.subtract(billInformation.getPayableTillDueDate(), diff));
                billInformation.setPayableAfterDueDate(GlobalUtil.subtract(billInformation.getPayableAfterDueDate(), diff));
            }

            final Object[] argsTwo = argList.toArray();
            final String messageTwo = billMessageService.getMessageTwo(language, argsTwo);
            if (StringUtils.isNotEmpty(messageTwo)) billMessageList.add(messageTwo);
        }

        //Message Three
        final String messageThree = billMessageService.getMessageThree(language);
        if (StringUtils.isNotEmpty(messageThree)) billMessageList.add(messageThree);

        templateData.setMessageList(billMessageList);
    }

    //Set Payment QR
    private void setPaymentQRInformation(final BillFRT6MonthlyTemplate templateData) {
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
            templateData.getBillInformation().setPaymentQuickResponseString(link);
            templateData.getBillInformation().setPaymentQuickResponseBase64(qrService.getAsBase64Image(link, 200, 200));
        }
    }

    //Setting Bill Information
    private BillMasterAgricultureInterface setBillInformation(final BillFRT6MonthlyTemplate templateData, final BillIdentifierDTO billIdentifierDTO, final MessageDTO messageDTO) {
        if (templateData == null || messageDTO == null) return null;
        final String consumerNo = billIdentifierDTO.getConsumerNo();
        final String billMonth = billIdentifierDTO.getBillMonth();

        final BillMasterAgricultureInterface bill = billMasterAgricultureService.getByConsumerNoAndBillMonth(consumerNo, billMonth);
        if (bill == null) {
            messageDTO.setMessage("Bill in MIS 6 Monthly not found for " + consumerNo + ", " + billMonth);
            log.info(messageDTO.getMessage());
            return null;
        }

        final List<String> billMonths = billMasterAgricultureService.getAgricultureBillMonthsForCycle(billMonth);
        String billDuration = billMonth;
        if (!GlobalUtil.isEmpty(billMonths)) {
            billDuration = billMonths.get(0) + " TO " + billMonths.get(billMonths.size() - 1);
        }
        Long unpostedPayment = paymentService.getAmountByConsumerNoAndPostingBillMonthInAndDeleted(consumerNo, billMonths, false);
        if (unpostedPayment == null) unpostedPayment = 0L;
        BigDecimal payableAmount = GlobalUtil.subtract(bill.getNetBill(), new BigDecimal(unpostedPayment));//.setScale(0, RoundingMode.HALF_UP));
        BigDecimal payableBeforeDueDate = payableAmount;
        BigDecimal payableAfterDueDate = payableAmount;
        if (payableAmount.compareTo(BigDecimal.ZERO) < 0) {
            payableAmount = payableBeforeDueDate = payableAfterDueDate = BigDecimal.ZERO;
        }

        final FRT6MonthlyBillInformation billInformation = new FRT6MonthlyBillInformation(billDuration, null, null, bill.getBillDate(), bill.getDueDate(), bill.getChequeDueDate(),
                bill.getSdHeld(), String.valueOf(bill.getId()), bill.getBillMonth(), payableAmount, payableBeforeDueDate, payableAfterDueDate, bill.getActualBill(), bill.getSubsidy(), bill.getCurrentBill(), bill.getArrear(), bill.getSurchargeDemanded(), bill.getCcbAdjustment(),
                bill.getNetBill(), unpostedPayment, payableAmount, null, null, null);

        templateData.setBillInformation(billInformation);

        return bill;
    }

    //Setting Consumer and Connection Information
    private void setConsumerAndConnectionInformation(final BillFRT6MonthlyTemplate templateData, final BillMasterAgricultureInterface bill) {
        if (templateData == null || bill == null) return;

        final String address = bill.getAddress1() + " " + bill.getAddress2() + " " + bill.getAddress3();
        final String addressHindi = bill.getAddress1H() + " " + bill.getAddress2H() + " " + bill.getAddress3H();

        final FRT6MonthlyConsumerInformation consumerInformation = new FRT6MonthlyConsumerInformation(bill.getConsumerNo(), bill.getConsumerName(), address, bill.getConsumerNameH(), addressHindi,
                GlobalUtil.maskMobileNO(bill.getConsumerMobileNo()), bill.getConsumerStatus(), bill.getConnectionDate(), bill.getPhase(), bill.getSanctionedLoad(), bill.getSanctionedLoadUnit(),
                bill.getRegionName(), bill.getCircleName(), bill.getDivisionName(), bill.getZoneName(), bill.getLocationCode(), bill.getGroupNo(), bill.getReadingDiaryNo());

        templateData.setConsumerInformation(consumerInformation);
    }

    //Setting Contact Information
    private void setContactInformation(final BillFRT6MonthlyTemplate templateData) {
        if (templateData == null) return;

        final List<UserDetailInterface> userDetailList = userDetailService.getByLocationCodeAndRoleAndStatus(templateData.getConsumerInformation().getLocationCode(), UserDetailInterface.ROLE_OIC, GlobalConstant.STATUS_ACTIVE);
        if (userDetailList != null && userDetailList.size() == 1) {
            final UserDetailInterface oic = userDetailList.getFirst();
            final ContactInformation contactInformation = new ContactInformation(0, oic.getDesignation(), oic.getName(), oic.getMobileNo(), templateData.getConsumerInformation().getZoneName());
            templateData.setContactInformation(contactInformation);
        }
    }
}
