@import com.mppkvvcl.jteone.utility.GlobalUtil
@import com.mppkvvcl.jteone.utility.JteUtil

@param com.mppkvvcl.jteone.dtos.templates.BillFRT6MonthlyTemplate billTemplate

!{
    var companyInfo = billTemplate.getCompanyInformation();
    var billInfo = billTemplate.getBillInformation();
    var consumerInfo = billTemplate.getConsumerInformation();
    var contactInfo = billTemplate.getContactInformation();
}

<!DOCTYPE html>
<html lang="hi">
<head>
    <meta charset="UTF-8"/>
    <title>WZ बिजली बिल</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <style>

        @page { size: A4; margin: 8mm; }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body{
          font-family: "DejaVu Sans", Arial, Helvetica, sans-serif;
          color:#0f172a;
          background:#ffffff;
          -webkit-print-color-adjust: exact;
          print-color-adjust: exact;
        }

        .page{
          width: 200mm;
          min-height: 287mm;
          margin: 0 auto;
          position: relative;
          padding: 0;
          page-break-after: always;
          padding-bottom: 12mm;
        }
        .page:last-child{ page-break-after: auto; }

        :root{
          --blue:#1f6fb2;
          --blue2:#0f4f87;
          --line:#b9c7d6;
          --soft:#f3f7fb;
          --soft2:#eef4fa;
          --ink:#0f172a;
          --muted:#334155;
          --warn:#e74c3c;
        }

        /* HEADER (UNCHANGED) */
        .header-top{
          text-align:center;
          padding: 6px 0 6px;
          border: 2px solid var(--blue);
          margin-bottom: 8px;
        }
        .header-left{
          display: table;
          width: 100%;
          border-collapse: collapse;
        }
        .header-cell{
          display: table-cell;
          vertical-align: middle;
        }
        .header-cell.left,
        .header-cell.right{
          width: 60px;
          text-align:center;
        }
        .logo-circle{
          width: 54px;
          height: 54px;
          border: 1px solid var(--line);
          border-radius: 50%;
          display:inline-block;
          overflow:hidden;
          background:#fff;
        }
        .logo-circle img{
          width: 54px;
          height: 54px;
          object-fit: contain;
          display:block;
        }
        .company-info{ padding: 0 2px; }
        .company-title{
          font-size: 16px;
          font-weight: 900;
          text-transform: none;
          color:#0b2b74;
          line-height: 1.2;
        }
        .company-subtitle{
          font-size: 15px;
          color:#1f2937;
          font-weight: 700;
          line-height: 1.2;
          margin-top: 1px;
        }
        .tagline{
          font-size: 11px;
          color:#475569;
          margin-top: 2px;
          line-height: 1.2;
        }
        .tagline a{ color:#0b2b74; text-decoration:none; font-weight:800; }

        .box{ border: 1px solid var(--line); background:#fff; }
        .box.soft{ background: var(--soft); }
        .pad-2{ padding: 2px; }
        .pad-5{ padding: 4px; }
        .pad-6{ padding: 6px; }
        .pad-8{ padding: 8px; }
        .mt-2{ margin-top: 2px; }
        .mt-6{ margin-top: 6px; }
        .mt-8{ margin-top: 8px; }
        .mt-10{ margin-top: 10px; }
        .center{ text-align:center; }
        .right{ text-align:right; }
        .small{ font-size: 9px; color:#475569; }
        .muted{ color:#475569; }

        .band-title{
          background: var(--blue);
          color:#fff;
          font-weight: 800;
          text-transform: none;
          font-size: 11px;
          padding: 3px 4px;
          letter-spacing: .3px;
          border: 1px solid var(--blue2);
          margin-top: 1px;
        }

        table{ width: 100%;
          border-collapse: collapse !important;
         border-spacing: 0 !important;
     }
        th, td{
          border: 1px solid var(--line);
          padding: 4px 4px;
          font-size: 10.5px;
          vertical-align: top;
           border-collapse: collapse !important;
        }
        th{
          background: var(--soft2);
          font-weight: 900;
          color:#0f2f52;
        }
        .no-border td, .no-border th{ border: none; }
        .tight td, .tight th{ padding: 5px; }

        /* Page-1 top banner */
        .p1-top{
          display: table;
          width: 100%;
          border-collapse: collapse;
          margin-top: 6px;
        }
        .p1-top .left{
          display: table-cell;
          width: 60mm;
          background: var(--blue);
          color:#fff;
          border: 2px solid var(--blue);
          vertical-align: middle;
          text-align:center;
          padding: 10px 8px;
          font-size: 22px;
          font-weight: 900;
          line-height: 1.05;
        }
        .p1-top .right{
          display: table-cell;
          border: 2px solid var(--blue);
          padding: 8px;
          vertical-align: top;
        }
        .kv{
          display: table;
          width:100%;
          border-collapse: collapse;
          margin-top: 2px;
        }
        .kv .r{ display: table-row; }
        .kv .k, .kv .v{
          display: table-cell;
          font-size: 11px;
          padding: 1px 0;
          vertical-align: top;
        }
        .kv .k{ width: 28mm; font-weight: 900; color:#0f2f52; }
        .kv .v{ color:#0f172a; }

        /* Mid strip */
        .info-strip td{
          border: none;
          /* padding: 6px 8px; */
          font-size: 10.5px;
        }
        .info-strip .lbl{
          font-weight: 900;
          text-transform: none;
          color:#0f2f52;
          font-size: 10px;
          display:block;
          /* margin-bottom: 2px; */
        }
        .info-strip .val{ font-weight: 600; }

        /* Payment area */
        .pay-grid{
          display: table;
          width:100%;
          border-collapse: collapse;
        }
        .pay-grid .c1, .pay-grid .c2{
          display: table-cell;
          vertical-align: top;
        }
        .pay-grid .c1{ width: 58mm; }
        .qr-box{
          border: 1px solid var(--line);
          padding: 6px;
          background:#fff;
        }
        .qr-row{
          display: table;
          width:100%;
          border-collapse: collapse;
        }
        .qr-row .qr{
          display: table-cell;
          width: 28mm;
          text-align:center;
          vertical-align: middle;
        }
        .qr-row img{ width: 90px; height: 90px; object-fit: cover; border: 1px solid var(--line); }
        .qr-row .amt{
          display: table-cell;
          padding-left: 8px;
          vertical-align: top;
        }
        .big-rupee{ font-size: 18px; font-weight: 950; margin-top: 6px; }
        .due-pill{
          margin-top: 6px;
          background: var(--warn);
          color:#fff;
          font-weight: 900;
          font-size: 11px;
          padding: 5px 6px;
          text-align:center;
        }
        .pay-options{
          margin-top: 6px;
          border: 1px solid var(--line);
          background: var(--soft);
          padding: 6px;
          font-weight: 900;
          color:#0f2f52;
          text-transform: none;
          font-size: 10px;
        }

        /* Charts */
        .chart-wrap{
          border: 1px solid var(--line);
          background:#fff;
          padding: 6px;
        }
        .chart{ width: 100% !important; height: 140px !important; }
        .chart-lg{ height: 165px !important; }
        .chart-md{ height: 150px !important; }
        .chart-sm{ height: 125px !important; }

        .two-col{
          display: table;
          width:100%;
          border-collapse: collapse;
        }
        .two-col .l, .two-col .r{
          display: table-cell;
          vertical-align: top;
        }
        .two-col .l{ width: 95mm; padding-right: 6px; }
        .two-col .r{ width: 95mm; padding-left: 6px; }

        .poster{
          border: 1px solid var(--line);
          background:#fff;
          height: 80px;
          overflow:hidden;
          text-align:center;
        }
        .poster img{
          width: 100%;
          height: 90px;
          object-fit: cover;
          display:block;
        }

        .meter-strip td{
          font-size: 10px;
          font-weight: 800;
          text-align:center;
        }
        .meter-strip th{
          font-size: 10px;
          text-transform: none;
          letter-spacing: .2px;
        }

        /* Footer (page-2 style) */
        .footer{
          position: absolute;
          left: 0;
          right: 0;
          bottom: 0;
          padding-top: 0.5px;
        }
        .footer-line{
          border-top: 1px dashed #7aa7cf;
          margin-top: 10px;
        }
        .barcode{
          margin-top: 1px;
          text-align:center;
          font-size: 10px;
          letter-spacing: 2px;
          color:#111827;
          font-weight: 900;
        }
        .footer-ids{
          margin-top: 6px;
          font-size: 10px;
          font-weight: 900;
          color:#0f2f52;
        }
        .footer-ids table td{ border:none; padding: 2px 4px; }

        @media print{
          body{ background:#fff; }
          a{ color:#0b2b74; text-decoration:none; }
        }
         .page-header {
            margin-bottom: 10px;
            overflow: hidden;
        }
        .page-header-bill-no {
            font-size: 12px;
            font-weight: bolder;
            text-transform: none;
            float: left;
        }
        .page-header-month {
            font-size: 12px;
            font-weight: 600;
            float: right;
        }
     .page-footer{
      position: absolute;
      left: 0;
      right: 0;
      bottom: 0;
      height: 5mm;
      border-top: 1px solid var(--line);
      background: #fff;
      display: table;
      width: 100%;
      table-layout: fixed;
      font-size: 9px;
      font-weight: 900;
      color: var(--ink);
    }

    .page-footer-left,
    .page-footer-center,
    .page-footer-right{
      display: table-cell;
      vertical-align: middle;
      width: 33.33%;
    }

    .page-footer-left{ text-align: left; }
    .page-footer-center{ text-align: center; }
    .page-footer-right{ text-align: right; }

      /* -------- responsive helpers (added, but keeping your design language) -------- */
      @media (max-width: 980px){
        .page{ width: auto; min-height: auto; padding: 0 6px; }
      }
      @media (max-width: 760px){
        .header-cell.left,.header-cell.right{ width: 52px; }
        .company-title{ font-size: 14px; }
        .p1-top .left{ font-size: 18px; padding: 8px 6px; }
        th,td{ font-size: 10px; }
      }
      @media (max-width: 620px){
        .p1-top, .p1-top .left, .p1-top .right{ display:block; width:100%; }
        .p1-top .left{ border-bottom:0; }
        .p1-top .right{ border-top:0; }
        .pay-grid, .pay-grid .c1, .pay-grid .c2{ display:block; width:100%; }
        .two-col, .two-col .l, .two-col .r{ display:block; width:100%; padding:0 !important; }
      }
    </style>

    <style>
        /*
           Ubuntu/Debian: sudo apt-get install fonts-noto-core fonts-noto-extra fonts-dejavu-core
        */
        body, * {
          font-family: "Noto Sans Devanagari","Noto Sans","DejaVu Sans","FreeSans", Arial, Helvetica, sans-serif !important;
        }
        .band-title, .section-band, .title, .lbl {
          text-transform: none !important;
        }
    </style>

</head>
<body>
<div class="page">
    <div class="header-top">
        <div class="header-left">
            <div class="header-cell left">
                <img src="${companyInfo.getLogoBase64()}" alt="" style="width:60px; height:60px;">
            </div>

            <div class="header-cell">
                <div class="company-info">
                    <div class="company-title"><strong>${companyInfo.getFullNameHindi()}</strong></div>
                    <div class="company-subtitle">${companyInfo.getAddressHindi()}</div>
                    <div class="company-subtitle">(Wholly owned by Govt. of M.P.)</div>
                    <div class="tagline">
                        <a href="${companyInfo.getWebsite()}" target="_blank">वेबसाइट: ${companyInfo.getWebsite()}</a> |
                        <a href="tel:${companyInfo.getCustomerCareNumber()}">कॉल सेंटर नं. ${companyInfo.getCustomerCareNumber()}</a>
                    </div>
                    <div class="tagline">
                        GST No: <strong>${companyInfo.getGstNo()}</strong> |
                        CIN No: <strong>${companyInfo.getCiNo()}</strong>
                    </div>
                </div>
            </div>

            <div class="header-cell right">
                <img src="${companyInfo.getAlternateLogoBase64()}" alt="" style="width:50px; height:50px;">
            </div>
        </div>
    </div>

    <div class="page-header box" style="padding: 8px; margin-bottom: -8px;">
        <div class="page-header-bill-no"> छह माही फ्लैट रेट विद्युत बिल कृषि उपभोक्ता के लिए (LV5.4)</div>
        <div class="page-header-month">बिल अवधि: <b>${billInfo.getBillDuration()}</b></div>
    </div>

    <div class="box pad-6">
        <table class="no-border info-strip" aria-label="Bill key info">
            <tr>
                <td>
                    <span class="lbl">उपभोक्ता नंबर</span>
                    <span class="val">${billTemplate.getConsumerNo()}</span>
                </td>
                <td>
                    <span class="lbl">उपभोक्ता नाम</span>
                    <span class="val">स${consumerInfo.getConsumerNameHindi()}</span>
                </td>
                <td>
                    <span class="lbl">मोबाइल नंबर</span>
                    <span class="val">${consumerInfo.getMobileNo()}</span>
                </td>
                <td>
                    <span class="lbl">स्वीकृत भार</span>
                    <span class="val">${JteUtil.get(consumerInfo.getSanctionedLoad())} ${consumerInfo.getSanctionedLoadUnit()}</span>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <span class="lbl">पता</span>
                    <span class="val">${consumerInfo.getAddressHindi()}</span>
                </td>
                <td>
                    <span class="lbl">फेज</span>
                    <span class="val">${consumerInfo.getPhase()}</span>
                </td>
            </tr>
        </table>
    </div>

    <div class="box pad-6">
        <table class="no-border info-strip" aria-label="Bill key info">
            <tr>
                <td>
                    <span class="lbl">क्षेत्र</span>
                    <span class="val">${consumerInfo.getRegionName()}</span>
                </td>
                <td>
                    <span class="lbl">संभाग</span>
                    <span class="val">${consumerInfo.getDivisionName()}</span>
                </td>
                <td>
                    <span class="lbl">डीसी/ज़ोन नाम</span>
                    <span class="val">${consumerInfo.getLocationCode()} - ${consumerInfo.getZoneName()}</span>
                </td>
                <td>
                    <span class="lbl">ग्रुप - आरडी</span>
                    <span class="val">${consumerInfo.getGroupNo()} - ${consumerInfo.getReadingDiaryNo()}</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="lbl">डी.सी. प्रभारी नाम</span>
                    <span class="val">${contactInfo.getName()}</span>
                </td>
                <td>
                    <span class="lbl">पदनाम</span>
                    <span class="val">${contactInfo.getDesignation()}</span>
                </td>
                <td>
                    <span class="lbl">संपर्क नंबर</span>
                    <span class="val">${contactInfo.getContactNo()}</span>
                </td>
                <td>
                    <span class="lbl">कस्टमर केयर नंबर </span>
                    <span class="val">${companyInfo.getCustomerCareNumber()}</span>
                </td>
            </tr>
        </table>
    </div>

    <div class="mt-6">
        <div class="band-title">भुगतान / देय सारांश</div>
        <div class="pay-grid">
            <div class="c1" style="text-align: center;">
                <div class="box"><strong><span style="font-size:11px;">भुगतान हेतु स्कैन करें</span></strong>
                    <div>
                        <img style="height: 85px; width: 85px;" src="${billInfo.getPaymentQuickResponseBase64()}" alt="">
                    </div>
                </div>
            </div>

            <div class="c2">
                <div class="box">
                    <table class="info-strip" style="width:100%;">
                        <tr>
                            <td>
                                <span class="lbl">बिल दिनांक</span>
                                <span class="val">${JteUtil.get(billInfo.getBillDate())}</span>
                            </td>
                            <td>
                                <span class="lbl">देय तिथि</span>
                                <span class="val">${JteUtil.get(billInfo.getDueDate())}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="lbl">सुरक्षानिधि जमा राशि</span>
                                <span class="val">${JteUtil.get(billInfo.getSdHeld())}</span>
                            </td>
                            <td>
                                <span class="lbl">कुल देय राशि</span>
                                <span class="val">${JteUtil.get(billInfo.getPayableAmount())}</span>
                            </td>
                        </tr>
                    </table>

                    <table>
                        <tr>
                            <td class="lbl" style="font-size: 13px; font-weight: 600;">
                                मध्य प्रदेश शासन द्वारा देय सब्सिडी
                            </td>
                            <td class="val" style="font-size: 13px; font-weight: 600;">
                                ${JteUtil.get(billInfo.getSubsidy())}
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="mt-6">
        <div class="band-title">फ्लैट रेट के अनुसार ऊर्जा शुल्क का विवरण</div>
        <table aria-label="Energy Charges">
            <tr>
                <th>विवरण</th>
                <th class="right">राशि (रु.)</th>
            </tr>
            <tr>
                <td>एमपीईआरसी द्वारा निर्धारित राशि</td>
                <td class="right">${JteUtil.get(billInfo.getActualBill())}</td>
            </tr>
            <tr>
                <td>सरकार द्वारा प्रदत्त कुल सब्सिडी</td>
                <td class="right">${JteUtil.get(billInfo.getSubsidy())}</td>
            </tr>
            <tr>
                <td>उपभोक्ता द्वारा देय राशि (फ्लैट रेट अनुसार)</td>
                <td class="right">${JteUtil.get(billInfo.getCurrentBill())}</td>
            </tr>
            <tr>
                <td>बकाया</td>
                <td class="right">${JteUtil.get(billInfo.getArrear())}</td>
            </tr>
            <tr>
                <td>सरचार्ज मांग</td>
                <td class="right">${JteUtil.get(billInfo.getSurchargeDemanded())}</td>
            </tr>
            <tr>
                <td>सीसीबी समायोजन</td>
                <td class="right">${JteUtil.get(billInfo.getCcbAdjustment())}</td>
            </tr>
            <tr style="background:#ffe9e7;">
                <td><strong>नेट बिल (अ)</strong></td>
                <td class="right"><strong>${JteUtil.get(billInfo.getSubTotalOne())}</strong></td>
            </tr>
            <tr>
                <td>ब) समाधान योजना 2025-26 के अंतर्गत सरचार्ज माफी</td>
                <td class="right">${JteUtil.get(billInfo.getOts2025SurchargeWaiver())}</td>
            </tr>
            <tr>
                <td>स) समाधान योजना 2025-26 के अंतर्गत किस्त देय</td>
                <td class="right">${JteUtil.get(billInfo.getOts2025UnpostedInstallmentAmount())}</td>
            </tr>
            <tr>
                <td>द ) समाधान योजना 2025-26 के अंतर्गत किस्त लागू</td>
                <td class="right">${JteUtil.get(billInfo.getOts2025postedInstallmentAmount())}</td>
            </tr>
            <tr>
                <td>इ ) जमा राशि</td>
                <td class="right">${billInfo.getUnpostedPayment()}</td>
            </tr>
            <tr>
                <td style="background:#ffe9e7;">F) <strong>नेट देय (फ = ए − (बी + सी + ई))</strong></td>
                <td class="right" style="background:#ffe9e7;"><b>₹ ${JteUtil.get(billInfo.getPayableAmount())}</b></td>
            </tr>
        </table>
    </div>

    @if(!GlobalUtil.isEmpty(billTemplate.getMessageList()))
        <div class="mt-6 box">
            <div class="band-title">संदेश / नोट्स:</div>
            @for(var message : billTemplate.getMessageList())
                <div class="soft pad-5" style="font-size:9px;">${message}</div>
            @endfor
        </div>
    @endif

    <div class="mt-6 box">
        <div class="footer-line"></div>
        <div class="band-title">देय राशि रसीद (केवल कार्यालय उपयोग हेतु)</div>
        <table class="tight info-strip" aria-label="Receipt stub">
            <tr>
                <td>बिल अवधि</td>
                <td>${billInfo.getBillDuration()}</td>
                <td rowspan="4">रसीद नं. व दिनांक __________ <br><br><br>जमा राशि __________</td>
            </tr>
            <tr>
                <td>कनेक्शन क्रमांक</td>
                <td>${consumerInfo.getConsumerNo()}</td>
            </tr>
            <tr>
                <td>उपभोक्ता का नाम</td>
                <td>${consumerInfo.getConsumerNameHindi()}</td>
            </tr>
            <tr>
                <td>पता</td>
                <td>${consumerInfo.getAddressHindi()}</td>
            </tr>
            <tr>
                <td>कुल देय राशि</td>
                <td><b>₹ ${JteUtil.get(billInfo.getPayableAmount())}</b></td>
                <td class="center"><b>जमा राशि रसीद</b></td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
