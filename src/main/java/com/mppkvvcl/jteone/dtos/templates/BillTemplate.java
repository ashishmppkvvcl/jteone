package com.mppkvvcl.jteone.dtos.templates;

import com.mppkvvcl.jteone.dtos.templates.pdfbill.*;
import com.mppkvvcl.jteone.interfaces.JteTemplateInterface;

import java.util.List;

public class BillTemplate implements JteTemplateInterface {

    private TemplateInformation templateInformation;
    private CompanyInformation companyInformation;
    private ConsumerInformation consumerInformation;
    private ConnectionInformation connectionInformation;
    private List<ContactInformation> contactInformationList;
    private List<String> messages;
    private ReadInformation readInformation;
    private BillSummary billSummary;
    private BillInformation billInformation;
    private TODInformation todInformation;
    private List<PaymentHistoryInformation> paymentHistoryInformationList;
    private List<ReadHistoryInformation> readHistoryInformationList;
    private MeterReplacementInformation meterReplacementInformation;
    private List<BillCalculationInformation> billCalculationInformationList;
    private List<NetMeterChildInformation> netMeterChildInformationList;
    private List<VigilanceInformation> vigilanceInformationList;
    private List<AdjustmentInformation> adjustmentInformationList;
    private List<ECGRFInformation> ecgrfInformationList;
    private AdvertisementInformation advertisementInformation;

    public TemplateInformation getTemplateInformation() {
        return templateInformation;
    }

    public void setTemplateInformation(TemplateInformation templateInformation) {
        this.templateInformation = templateInformation;
    }

    public CompanyInformation getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(CompanyInformation companyInformation) {
        this.companyInformation = companyInformation;
    }

    /*public void setCompanyInformation(String name, String shortName, String address, String website, String customerCareNumber, String gstNo, String ciNo, String whatsappNo, String whatsappQuickResponseString, String logoURL, String logoBase64, String alternateLogoURL, String alternateLogoBase64) {
        if (consumerInformation == null) consumerInformation = new ConsumerInformation();

        companyInformation.setName(name);
        companyInformation.setShortName(shortName);
        companyInformation.setAddress(address);
        companyInformation.setWebsite(website);
        companyInformation.setCustomerCareNumber(customerCareNumber);
        companyInformation.setGstNo(gstNo);
        companyInformation.setCiNo(ciNo);
        companyInformation.setWhatsappNo(whatsappNo);
        companyInformation.setWhatsappQuickResponseString(whatsappQuickResponseString);
        companyInformation.setLogoURL(logoURL);
        companyInformation.setLogoBase64(logoBase64);
        companyInformation.setAlternateLogoURL(alternateLogoURL);
        companyInformation.setAlternateLogoBase64(alternateLogoBase64);
    }*/

    public ConsumerInformation getConsumerInformation() {
        return consumerInformation;
    }

    public void setConsumerInformation(ConsumerInformation consumerInformation) {
        this.consumerInformation = consumerInformation;
    }

    /*public void setConsumerInformation(String consumerNo, String consumerName, String address, String mobileNo, String emailId, String employeeNo) {
        if (consumerInformation == null) consumerInformation = new ConsumerInformation();

        consumerInformation.setConsumerNo(consumerNo);
        consumerInformation.setConsumerName(consumerName);
        consumerInformation.setAddress(address);
        consumerInformation.setMobileNo(mobileNo);
        consumerInformation.setEmailId(emailId);
        consumerInformation.setEmployeeNo(employeeNo);
    }*/

    public BillSummary getBillSummary() {
        return billSummary;
    }

    public void setBillSummary(BillSummary billSummary) {
        this.billSummary = billSummary;
    }

    /*public void setBillSummary(String billNo, String billMonth, BigDecimal payableAmount, BigDecimal payableTillDueDate, BigDecimal payableAfterDueDate, BigDecimal currentBill, BigDecimal arrear, String paymentQuickResponseString, Date dueDate, Date chequeDueDate, BigDecimal consumption, String meterReaderName, String meterReaderContactNo, Date billDate, String billType, String billBasis, Boolean dailyAverageConsumption, Boolean dailyAverageBill, BigDecimal sdHeld, BillSummary sdPending) {
        if (billSummary == null) billSummary = new BillSummary();

        billSummary.setBillNo(billNo);
        billSummary.setBillMonth(billMonth);
        billSummary.setPayableAmount(payableAmount);
        billSummary.setPayableTillDueDate(payableTillDueDate);
        billSummary.setPayableAfterDueDate(payableAfterDueDate);
        billSummary.setCurrentBill(currentBill);
        billSummary.setArrear(arrear);
        billSummary.setPaymentQuickResponseString(paymentQuickResponseString);
        billSummary.setDueDate(dueDate);
        billSummary.setChequeDueDate(chequeDueDate);
        billSummary.setConsumption(consumption);
        billSummary.setMeterReaderName(meterReaderName);
        billSummary.setMeterReaderContactNo(meterReaderContactNo);
        billSummary.setBillDate(billDate);
        billSummary.setBillType(billType);
        billSummary.setBillBasis(billBasis);
        billSummary.setDailyAverageConsumption(dailyAverageConsumption);
        billSummary.setDailyAverageBill(dailyAverageBill);
        billSummary.setSdHeld(sdHeld);
        billSummary.setSdPending(sdPending);
    }*/

    public List<ContactInformation> getContactInformationList() {
        return contactInformationList;
    }

    public void setContactInformationList(List<ContactInformation> contactInformationList) {
        this.contactInformationList = contactInformationList;
    }

    /*public void addContactInformation(int index, String designation, String name, String contactNo) {
        if (contactInformationList == null) contactInformationList = new ArrayList<>();

        final ContactInformation contactInformation = new ContactInformation(index, designation, name, contactNo);
        contactInformationList.add(contactInformation);
    }*/

    public ConnectionInformation getConnectionInformation() {
        return connectionInformation;
    }

    public void setConnectionInformation(ConnectionInformation connectionInformation) {
        this.connectionInformation = connectionInformation;
    }

    /*public void setConnectionInformation(String divisionName, String zoneName, String locationCode, String groupNo, String readingDiaryNo, String feederCode, String dtrCode, String poleNo, String purpose, String areaType, String tariffCategory, String tariffCode, Date connectionDate, String phase, BigDecimal sanctionedLoad, String sanctionedLoadUnit, BigDecimal contractDemand, boolean isNetMeter, boolean isPrepaid, Date prepaidDate) {
        if (connectionInformation == null) connectionInformation = new ConnectionInformation();
        connectionInformation.setDivisionName(divisionName);
        connectionInformation.setZoneName(zoneName);
        connectionInformation.setLocationCode(locationCode);
        connectionInformation.setGroupNo(groupNo);
        connectionInformation.setReadingDiaryNo(readingDiaryNo);
        connectionInformation.setFeederCode(feederCode);
        connectionInformation.setDtrCode(dtrCode);
        connectionInformation.setPoleNo(poleNo);
        connectionInformation.setPurpose(purpose);
        connectionInformation.setAreaType(areaType);
        connectionInformation.setTariffCategory(tariffCategory);
        connectionInformation.setTariffCode(tariffCode);
        connectionInformation.setConnectionDate(connectionDate);
        connectionInformation.setPhase(phase);
        connectionInformation.setSanctionedLoad(sanctionedLoad);
        connectionInformation.setSanctionedLoadUnit(sanctionedLoadUnit);
        connectionInformation.setContractDemand(contractDemand);
        connectionInformation.setNetMeter(isNetMeter);
        connectionInformation.setPrepaid(isPrepaid);
        connectionInformation.setPrepaidDate(prepaidDate);
    }*/

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    /*public void addMessages(String message) {
        if (messages == null) messages = new ArrayList<>();

        messages.add(message);
    }*/

    public ReadInformation getReadInformation() {
        return readInformation;
    }

    public void setReadInformation(ReadInformation readInformation) {
        this.readInformation = readInformation;
    }

    /*public void setReadInformation(String meterSrNo, Date date, String type, String source, BigDecimal pf, BigDecimal md, BigDecimal solarPlantCapacity, BigDecimal residualUnit, BigDecimal lastMonthResidualUnit, boolean isChild, BigDecimal residualUnitAdjusted, BigDecimal netMeteredUnit, BigDecimal netBilledUnit) {
        if (readInformation == null) readInformation = new ReadInformation();

        readInformation.setMeterSrNo(meterSrNo);
        readInformation.setDate(date);
        readInformation.setType(type);
        readInformation.setSource(source);
        readInformation.setPf(pf);
        readInformation.setMd(md);
        readInformation.setSolarPlantCapacity(solarPlantCapacity);
        readInformation.setResidualUnit(residualUnit);
        readInformation.setLastMonthResidualUnit(lastMonthResidualUnit);
        readInformation.setChild(isChild);
        readInformation.setResidualUnitAdjusted(residualUnitAdjusted);
        readInformation.setNetMeteredUnit(netMeteredUnit);
        readInformation.setNetBilledUnit(netBilledUnit);
    }

    public void addReadToReadInformation(int index, String type, BigDecimal currentRead, BigDecimal previousRead, BigDecimal mf, BigDecimal meteredUnit, BigDecimal assessment, BigDecimal billedUnit) {
        if (readInformation == null) return;

        readInformation.addRead(index, type, currentRead, previousRead, mf, meteredUnit, assessment, billedUnit);
    }*/

    public BillInformation getBillInformation() {
        return billInformation;
    }

    public void setBillInformation(BillInformation billInformation) {
        this.billInformation = billInformation;
    }

    public TODInformation getTodInformation() {
        return todInformation;
    }

    public void setTodInformation(TODInformation todInformation) {
        this.todInformation = todInformation;
    }

    public List<PaymentHistoryInformation> getPaymentHistoryInformationList() {
        return paymentHistoryInformationList;
    }

    public void setPaymentHistoryInformationList(List<PaymentHistoryInformation> paymentHistoryInformationList) {
        this.paymentHistoryInformationList = paymentHistoryInformationList;
    }

    public List<ReadHistoryInformation> getReadHistoryInformationList() {
        return readHistoryInformationList;
    }

    public void setReadHistoryInformationList(List<ReadHistoryInformation> readHistoryInformationList) {
        this.readHistoryInformationList = readHistoryInformationList;
    }

    public MeterReplacementInformation getMeterReplacementInformation() {
        return meterReplacementInformation;
    }

    public void setMeterReplacementInformation(MeterReplacementInformation meterReplacementInformation) {
        this.meterReplacementInformation = meterReplacementInformation;
    }

    public List<NetMeterChildInformation> getNetMeterChildInformationList() {
        return netMeterChildInformationList;
    }

    public void setNetMeterChildInformationList(List<NetMeterChildInformation> netMeterChildInformationList) {
        this.netMeterChildInformationList = netMeterChildInformationList;
    }

    public List<BillCalculationInformation> getBillCalculationInformationList() {
        return billCalculationInformationList;
    }

    public void setBillCalculationInformationList(List<BillCalculationInformation> billCalculationInformationList) {
        this.billCalculationInformationList = billCalculationInformationList;
    }

    public List<VigilanceInformation> getVigilanceInformationList() {
        return vigilanceInformationList;
    }

    public void setVigilanceInformationList(List<VigilanceInformation> vigilanceInformationList) {
        this.vigilanceInformationList = vigilanceInformationList;
    }

    public List<AdjustmentInformation> getAdjustmentInformationList() {
        return adjustmentInformationList;
    }

    public void setAdjustmentInformationList(List<AdjustmentInformation> adjustmentInformationList) {
        this.adjustmentInformationList = adjustmentInformationList;
    }

    public List<ECGRFInformation> getEcgrfInformationList() {
        return ecgrfInformationList;
    }

    public void setEcgrfInformationList(List<ECGRFInformation> ecgrfInformationList) {
        this.ecgrfInformationList = ecgrfInformationList;
    }

    public AdvertisementInformation getAdvertisementInformation() {
        return advertisementInformation;
    }

    public void setAdvertisementInformation(AdvertisementInformation advertisementInformation) {
        this.advertisementInformation = advertisementInformation;
    }

    @Override
    public String toString() {
        return "BillTemplate{" +
                "templateInformation=" + templateInformation +
                ", companyInformation=" + companyInformation +
                ", consumerInformation=" + consumerInformation +
                ", connectionInformation=" + connectionInformation +
                ", contactInformationList=" + contactInformationList +
                ", messages=" + messages +
                ", readInformation=" + readInformation +
                ", billSummary=" + billSummary +
                ", billInformation=" + billInformation +
                ", todInformation=" + todInformation +
                ", paymentHistoryInformationList=" + paymentHistoryInformationList +
                ", readHistoryInformationList=" + readHistoryInformationList +
                ", meterReplacementInformation=" + meterReplacementInformation +
                ", billCalculationInformationList=" + billCalculationInformationList +
                ", netMeterChildInformationList=" + netMeterChildInformationList +
                ", vigilanceInformationList=" + vigilanceInformationList +
                ", adjustmentInformationList=" + adjustmentInformationList +
                ", ecgrfInformationList=" + ecgrfInformationList +
                ", advertisementInformation=" + advertisementInformation +
                '}';
    }
}
