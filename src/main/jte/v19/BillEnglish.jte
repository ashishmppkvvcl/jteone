@param com.mppkvvcl.jteone.dtos.templates.BillTemplate billTemplate

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>WZ Electricity Bill</title>
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
        .company-info{ padding: 0 6px; }
        .company-title{
          font-size: 14px;
          font-weight: 900;
          text-transform: uppercase;
          color:#0b2b74;
          line-height: 1.2;
        }
        .company-subtitle{
          font-size: 11px;
          color:#1f2937;
          font-weight: 700;
          line-height: 1.2;
          margin-top: 1px;
        }
        .tagline{
          font-size: 10px;
          color:#475569;
          margin-top: 2px;
          line-height: 1.2;
        }
        .tagline a{ color:#0b2b74; text-decoration:none; font-weight:800; }

        .box{ border: 1px solid var(--line); background:#fff; }
        .box.soft{ background: var(--soft); }
        .pad-5{ padding: 4px; }
        .pad-6{ padding: 6px; }
        .pad-8{ padding: 8px; }
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
          text-transform: uppercase;
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
          padding: 6px 6px;
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
          text-transform: uppercase;
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
          text-transform: uppercase;
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
          text-transform: uppercase;
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
          margin-top: 6px;
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
            text-transform: uppercase;
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
    </style>
</head>

<body>
<!-- PAGE 1 -->
<div class="page">
    <!-- HEADER -->
    <div class="header-top">
        <div class="header-left">
            <div class="header-cell left">
                <img src="${billTemplate.getCompanyInformation().getLogoBase64()}" alt=""
                     style="width:60px; height:60px;">
            </div>
            <div class="header-cell">
                <div class="company-info">
                    <div class="company-title">
                        <strong>${billTemplate.getCompanyInformation().getFullName()}</strong>
                    </div>
                    <div class="company-subtitle">${billTemplate.getCompanyInformation().getAddress()}</div>
                    <div class="company-subtitle">(Wholly owned by Govt. of M.P.)</div>
                    <div class="tagline">
                        <a href="${billTemplate.getCompanyInformation().getWebsite()}"
                           target="_blank">Website: ${billTemplate.getCompanyInformation().getWebsite()}</a> |
                        <a href="tel:${billTemplate.getCompanyInformation().getCustomerCareNumber()}">Call Centre
                            No. ${billTemplate.getCompanyInformation().getCustomerCareNumber()}</a>
                    </div>
                    <div class="tagline">
                        GST No: <strong>${billTemplate.getCompanyInformation().getGstNo()}</strong> |
                        CIN No: <strong>${billTemplate.getCompanyInformation().getCiNo()}</strong>
                    </div>
                </div>
            </div>

            <div class="header-cell right">
                <img src="${billTemplate.getCompanyInformation().getAlternateLogoBase64()}" alt=""
                     style="width:60px; height:60px;">
            </div>
        </div>
    </div>

    <div class="page-header box" style="padding: 5px; margin-bottom: -5px;">
        <div class="page-header-bill-no">
            Electricity Bill No: ${billTemplate.getBillSummary().getBillNo()}
        </div>
        <div class="page-header-month">
            BILL MONTH: ${billTemplate.getBillSummary().getBillMonth()}
        </div>
    </div>
    <!-- Top Banner + Consumer -->
    <div class="box soft pad-6">
        <table class="info-strip" style="width:100%;">
            <!-- Row 1 -->
            <tr>
                <td style="width:25%;">
                    <span class="lbl">CONSUMER NO</span>
                    <span class="val">N${billTemplate.getConsumerInformation().getConsumerNo()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">MOBILE</span>
                    <span class="val">${billTemplate.getConsumerInformation().getMobileNo()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">EMAIL</span>
                    <span class="val">${billTemplate.getConsumerInformation().getEmailId()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">EMPLOYEE NO</span>
                    <span class="val">${billTemplate.getConsumerInformation().getEmployeeNo()}</span>
                </td>
            </tr>
            <!-- Name & Address -->
            <tr>
                <td colspan="2">
                    <span class="lbl">CONSUMER NAME</span>
                    <span class="val">${billTemplate.getConsumerInformation().getConsumerName()}</span>
                </td>
                <td colspan="2">
                    <span class="lbl">ADDRESS</span>
                    <span class="val">${billTemplate.getConsumerInformation().getAddress()}</span>
                </td>
            </tr>
        </table>
    </div>

    <!-- Mid info strip -->
    <div class="box soft mt-1">
        <table class="info-strip" style="width:100%;">
            <tr>
                <td style="width:25%;">
                    <span class="lbl">DIVISION NAME</span>
                    <span class="val">${billTemplate.getConnectionInformation().getDivisionName()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">ZONE / DC NAME</span>
                    <span class="val">${billTemplate.getConnectionInformation().getLocationCode()} - ${billTemplate.getConnectionInformation().getZoneName()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">GROUP / RD NO</span>
                    <span class="val">${billTemplate.getConnectionInformation().getGroupNo()} - ${billTemplate.getConnectionInformation().getReadingDiaryNo()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">CONNECTION TYPE</span>
                    <span class="val">${billTemplate.getConnectionInformation().getConnectionType()}</span>
                </td>
            </tr>
            <tr>
                <td style="width:25%;">
                    <span class="lbl"> Feeder Code</span>
                    <span class="val">${billTemplate.getConnectionInformation().getFeederCode()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">DTR  Code</span>
                    <span class="val">${billTemplate.getConnectionInformation().getDtrCode()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl"> Pole No</span>
                    <span class="val">${billTemplate.getConnectionInformation().getPoleNo()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">Purpose</span>
                    <span class="val">${billTemplate.getConnectionInformation().getPurpose()}</span>
                </td>
            </tr>
            <tr>
                <td style="width:25%;">
                    <span class="lbl">Premise Type</span>
                    <span class="val">${billTemplate.getConnectionInformation().getAreaType()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">TARIFF CATEGORY</span>
                    <span class="val">${billTemplate.getConnectionInformation().getTariffCategory()} (${billTemplate.getConnectionInformation().getTariffCode()})</span>
                </td>
                <td style="width:25%;">
                    @if("ACTIVE".equals(billTemplate.getConsumerInformation().getStatus()))
                        <span class="lbl">CONNECTION DATE</span>
                        <span class="val">${billTemplate.getConnectionInformation().getConnectionDate()}</span>
                    @else
                        <span class="lbl">PD DATE</span>
                        <span class="val">${billTemplate.getBillSummary().getPdcDate()}</span>
                    @endif
                </td>
                <td style="width:25%;">
                    <span class="lbl">PHASE</span>
                    <span class="val">${billTemplate.getConnectionInformation().getPhase()}</span>
                </td>
            </tr>
            <tr>
                <td style="width:25%;">
                    <span class="lbl">SANCTIONED LOAD</span>
                    <span class="val">${billTemplate.getConnectionInformation().getSanctionedLoad()} ${billTemplate.getConnectionInformation().getSanctionedLoadUnit()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">Contract Demand</span>
                    <span class="val">${billTemplate.getConnectionInformation().getContractDemand()} ${billTemplate.getConnectionInformation().getContractDemandUnit()}</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">Net Meter</span>
                    <span class="val">@if(billTemplate.getConnectionInformation().isNetMeter()) Yes @else No @endif</span>
                </td>
                <td style="width:25%;">
                    <span class="lbl">Prepaid</span>
                    <span class="val">@if(billTemplate.getConnectionInformation().isPrepaid()) Yes (${billTemplate.getConnectionInformation().getPrepaidDate()}) @else No @endif</span>
                </td>
            </tr>
        </table>
    </div>
    <table class="tight" style="margin-bottom: -8px;">
        <tr>
            <th style="width:16%;">Due Date</th>
            <th style="width:16%;">Consumption</th>
            <th style="width:16%;">Current Month Bill</th>
            <th style="width:16%;">Previous Outstanding</th>
            <th style="width:18%;">Pay Till Due Date</th>
            <th style="width:18%;">Pay After Due Date</th>
        </tr>
        <tr>
            <td class="center" style="font-size: 15px;"><b>${billTemplate.getBillSummary().getDueDate()}</b></td>
            <td class="center" style="font-size: 15px;"><b>${billTemplate.getBillSummary().getConsumption()}</b></td>
            <td class="center" style="font-size: 15px;"><b>₹ ${billTemplate.getBillSummary().getCurrentBill()}</b></td>
            <td class="center" style="font-size: 15px;"><b>₹ ${billTemplate.getBillSummary().getArrear()}</b></td>
            <td class="center" style="font-size: 15px;">
                <b>₹ ${billTemplate.getBillSummary().getPayableTillDueDate()}</b></td>
            <td class="center" style="font-size: 15px;">
                <b>₹ ${billTemplate.getBillSummary().getPayableAfterDueDate()}</b></td>
        </tr>
    </table>
    <!-- Payment + summary row -->
    <div class="mt-8 pay-grid">
        <div class="c1" style="text-align: center;">
            <div class="box"><strong><span style="font-size:11px;">Scan to Pay</span></strong>
                <div>
                    <img src="${billTemplate.getBillSummary().getPaymentQuickResponseBase64()}"
                         style="height: 54px; width: 54px;" alt="">
                </div>
            </div>
        </div>

        <!-- Right: Bill month summary -->
        <div class="c2">
            <div class="box">
                <table class="info-strip" style="width:100%;">
                    <!-- Row 1 -->
                    <tr>
                        <td style="width:25%;">
                            <span class="lbl">BILL DATE</span>
                            <span class="val">${billTemplate.getBillSummary().getBillDate()}</span>
                        </td>
                        <td style="width:25%;">
                            <span class="lbl">BILL TYPE</span>
                            <span class="val">${billTemplate.getBillSummary().getBillType()}</span>
                        </td>
                        <td style="width:25%;">
                            <span class="lbl">METER READER</span>
                            <span class="val">${billTemplate.getBillSummary().getMeterReaderName()}</span>
                        </td>
                        <td style="width:25%;">
                            <span class="lbl">BILL BASIS</span>
                            <span class="val">${billTemplate.getBillSummary().getBillBasis()}</span>
                        </td>
                    </tr>

                    <!-- Row 2 -->
                    <tr>
                        <td>
                            <span class="lbl">DAILY AVG OF BILL</span>
                            <span class="val">${billTemplate.getBillSummary().getDailyAverageBill()}</span>
                        </td>
                        <td>
                            <span class="lbl">DAILY AVG UNITS</span>
                            <span class="val">${billTemplate.getBillSummary().getDailyAverageConsumption()}</span>
                        </td>
                        <td>
                            <span class="lbl">S.D. HELD</span>
                            <span class="val">${billTemplate.getBillSummary().getSdHeld()}</span>
                        </td>
                        <td>
                            <span class="lbl">S.D. PENDING</span>
                            <span class="val">${billTemplate.getBillSummary().getSdPending()}</span>
                        </td>
                    </tr>
                </table>

            </div>
        </div>

    </div>
    <table>
        <tr>
            <td>
                <table class="info-strip">
                    @for(var contact : billTemplate.getContactInformationList())
                        <tr>
                            <td><b>${contact.getDesignation()}:</b><br>
                                <b>${contact.getOfficeName()}</b></td>
                            <td><b>${contact.getName()}<br> ${contact.getContactNo()}</b></td>
                        </tr>
                    @endfor
                </table>
            </td>
            <td style="text-align: center; padding: -15px;">
                <strong><span
                            style="font-size:11px;">WhatsApp ChatBot / Call Centre: ${billTemplate.getCompanyInformation().getCustomerCareNumber()}</span></strong>
                <div>
                    <img src="${billTemplate.getCompanyInformation().getWhatsappQuickResponseBase64()}"
                         style="height: 54px; width: 54px;" alt="">
                </div>
            </td>
        </tr>
    </table>

    <!-- Consumption Trend -->
    <div class="mt-1">
        <div class="band-title">CONSUMPTION TREND (IN UNITS)</div>
        <div class="chart-wrap">
            <img style="width: 100%; height: 140px;" src="${billTemplate.getBillSummary().getConsumptionTrendBarChartBase64()}" alt="">
        </div>
    </div>

    <!-- Major components + awareness image -->
    <div class="mt-1">
        <div class="band-title">ADVERTISEMENT / AWARENESS</div>
        <div class="poster" style="height: 130px;">
            <img src="${billTemplate.getAdvertisementInformation().getPageOneZeroBase64Image()}" style="height: 100%; width: 100%;">
        </div>
    </div>
    <div class="mt-1 box">
        <div class="band-title">Messages / Notes:</div>
        @for(var message : billTemplate.getMessages())
            <div class="soft pad-5" style="font-size:9px;">${message}</div>
        @endfor
    </div>

    <div class="page-footer">
        <div class="page-footer-left">${billTemplate.getConsumerInformation().getConsumerNo()}</div>
        <div class="page-footer-center">${billTemplate.getBillSummary().getBillMonth()}</div>
        <div class="page-footer-right">Page 1 of 4</div>
    </div>
</div>

<!-- PAGE 2 -->
<div class="page">
    <div class="band-title" style=" margin-top:3px;">CURRENT READING DETAILS</div>
    <div class="box soft pad-6">
        <table class="info-strip" style="width:100%;">
            <!-- Row 1 -->
            <tr>
                <td style="width:16.66%;">
                    <span class="lbl">METER S.NO.</span>
                    <span class="val">${billTemplate.getReadInformation().getMeterSrNo()}</span>
                </td>
                <td style="width:16.66%;">
                    <span class="lbl">READ DATE</span>
                    <span class="val">${billTemplate.getReadInformation().getDate()}</span>
                </td>
                <td style="width:16.66%;">
                    <span class="lbl">READ TYPE</span>
                    <span class="val">${billTemplate.getReadInformation().getType()}</span>
                </td>
                <td style="width:16.66%;">
                    <span class="lbl">READ SOURCE</span>
                    <span class="val">${billTemplate.getReadInformation().getSource()}</span>
                </td>
                <td style="width:16.66%;">
                    <span class="lbl">P.F</span>
                    <span class="val">${billTemplate.getBillInformation().getPf()}</span>
                </td>
                <td style="width:16.66%;">
                    <span class="lbl">MD</span>
                    <span class="val">${billTemplate.getBillInformation().getMd()}</span>
                </td>
            </tr>

            <!-- Row 2 -->
            @if(billTemplate.getConnectionInformation().isNetMeter())
                <tr>
                    <td>
                        <span class="lbl">SOLAR PLANT CAPACITY</span>
                        <span class="val">${billTemplate.getReadInformation().getSolarPlantCapacity()}</span>
                    </td>
                    <td>
                        <span class="lbl">RESIDUAL UNITS</span>
                        <span class="val">${billTemplate.getReadInformation().getResidualUnit()}</span>
                    </td>
                    <td>
                        <span class="lbl">VNM / GNM</span>
                        <span class="val">@if(billTemplate.getConnectionInformation().isNetMeterChild()) CHILD
                            @elseif(billTemplate.getConnectionInformation().isNetMeterParent()) PARENT @endif
                        </span>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            @endif
        </table>
    </div>

    <div class="box pad-6">
        @if(billTemplate.getConnectionInformation().isNetMeter())
            <table class="reading-table">
                <thead>
                <tr>
                    <th>Particulars</th>
                    <th class="right">Current Reading</th>
                    <th class="right">Previous Reading</th>
                    <th class="right">M.F.</th>
                    <th class="right">Metered Units</th>
                    <th class="right">Assessed Units</th>
                    <th class="right">Final Consumption</th>
                </tr>
                </thead>
                <tbody>
                !{var importRead = billTemplate.getReadInformation().getReadDetailList().getFirst();}
                !{var exportRead = billTemplate.getReadInformation().getReadDetailList().get(1);}
                <tr>
                    <td>Import</td>
                    <td class="right"><b>${importRead.getCurrentRead()}</b></td>
                    <td class="right"><b>${importRead.getPreviousRead()}</b></td>
                    <td class="right"><b>${importRead.getMf()}</b></td>
                    <td class="right"><b>${importRead.getMeteredUnit()}</b></td>
                    <td class="right"><b>${importRead.getAssessment()}</b></td>
                    <td class="right"><b>${importRead.getBilledUnit()}</b></td>
                </tr>
                <tr>
                    <td>Export</td>
                    <td class="right"><b>${exportRead.getCurrentRead()}</b></td>
                    <td class="right"><b>${exportRead.getPreviousRead()}</b></td>
                    <td class="right"><b>${exportRead.getMf()}</b></td>
                    <td class="right"><b>${exportRead.getMeteredUnit()}</b></td>
                    <td class="right"><b>${exportRead.getAssessment()}</b></td>
                    <td class="right"><b>${exportRead.getBilledUnit()}</b></td>
                </tr>
                <tr class="row-highlight">
                    <td colspan="4" style="text-align:center;">Residual Units Adjusted</td>
                    <td class="right" colspan="3">${billTemplate.getReadInformation().getResidualUnitAdjusted()}</td>
                </tr>
                <tr class="row-total">
                    <td colspan="4" style="text-align:center;">Net</td>
                    <td class="right">${billTemplate.getReadInformation().getNetMeteredUnit()}</td>
                    <td class="right"></td>
                    <td class="right">${billTemplate.getReadInformation().getNetBilledUnit()}</td>
                </tr>
                @if(billTemplate.getReadInformation().getReadDetailList().size() == 3)
                    !{var generateRead = billTemplate.getReadInformation().getReadDetailList().get(2);}
                    <tr>
                        <td class="right"><b>${generateRead.getCurrentRead()}</b></td>
                        <td class="right"><b>${generateRead.getPreviousRead()}</b></td>
                        <td class="right"><b>${generateRead.getMf()}</b></td>
                        <td class="right"><b>${generateRead.getMeteredUnit()}</b></td>
                        <td class="right"><b>${generateRead.getAssessment()}</b></td>
                        <td class="right"><b>${generateRead.getBilledUnit()}</b></td>
                    </tr>
                @endif
                </tbody>
            </table>
        @else
            !{var readInfo = billTemplate.getReadInformation().getReadDetailList().getFirst();}
            <table class="tight">
                <tr>
                    <th class="right">Current Reading</th>
                    <th class="right">Previous Reading</th>
                    <th class="right">M.F.</th>
                    <th class="right">Metered Units</th>
                    <th class="right">Assessed Units</th>
                    <th class="right">Total Units</th>
                </tr>
                <tr>
                    <td class="right"><b>${readInfo.getCurrentRead()}</b></td>
                    <td class="right"><b>${readInfo.getPreviousRead()}</b></td>
                    <td class="right"><b>${readInfo.getMf()}</b></td>
                    <td class="right"><b>${readInfo.getMeteredUnit()}</b></td>
                    <td class="right"><b>${readInfo.getAssessment()}</b></td>
                    <td class="right"><b>${readInfo.getBilledUnit()}</b></td>
                </tr>
            </table>
        @endif
    </div>

    <div class="band-title mt-10">DETAILED BILL BREAKUP</div>
    <div class="two-col mt-8">
        <div class="l">
            <table class="tight">
                <tr>
                    <th colspan="2">Billing Charges Information</th>
                </tr>

                <tr>
                    <td colspan="2" style="background:var(--soft2); font-weight:900; text-align: right;">Energy</td>
                </tr>
                <tr>
                    <td>Energy charges</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getEnergyCharge()}</b></td>
                </tr>
                <tr>
                    <td>Fuel &amp; Power Purchase Adjustment Surcharge</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getFppas()}</b></td>
                </tr>
                <tr>
                    <td>Fixed charges</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getFixedCharge()}</b></td>
                </tr>
                <tr>
                    <td style="background:rgba(16,185,129,0.12); font-weight:900;">Sub Total</td>
                    <td class="right" style="background:rgba(16,185,129,0.12); font-weight:900;">
                        <b>${billTemplate.getBillInformation().getSubTotalOne()}</b></td>
                </tr>

                <tr>
                    <td>Electricity Duty</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getElectricityDuty()}</b></td>
                </tr>
                <tr>
                    <td>CCB Adjustment</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getCcbAdjustment()}</b></td>
                </tr>
                <tr>
                    <td style="background:rgba(16,185,129,0.12); font-weight:900;">Sub Total</td>
                    <td class="right" style="background:rgba(16,185,129,0.12); font-weight:900;">
                        <b>${billTemplate.getBillInformation().getSubTotalTwo()}</b></td>
                </tr>

                <tr>
                    <td colspan="2" style="background:var(--soft2); font-weight:900;text-align: right;">Rebate /
                        Charges
                    </td>
                </tr>
                <tr>
                    <td>Penal Charge</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPenalCharge()}</b></td>
                </tr>
                <tr>
                    <td>PF Surcharge / Incentive</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPfSurchargeOrIncentive()}</b></td>
                </tr>
                <tr>
                    <td>ASD Installments</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getAsdInstallment()}</b></td>
                </tr>
                <tr>
                    <td>TOD Rebate / Surcharge</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getTodRebateOrSurcharge()}</b></td>
                </tr>
                <tr>
                    <td>Interest on Security Deposit</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getSdInterest()}</b></td>
                </tr>
                <tr>
                    <td>Load Factor Incentive</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getLocdFactorIncentive()}</b></td>
                </tr>
                <tr>
                    <td>Lock Credit</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getLockCredit()}</b></td>
                </tr>
                <tr>
                    <td>Employee Rebate</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getEmployeeRebate()}</b></td>
                </tr>
                <tr>
                    <td>Prepaid Payment Rebate</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPrepaidPaymentRebate()}</b></td>
                </tr>
                <tr>
                    <td>Online Payment Rebate</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getOnlinePaymentRebate()}</b></td>
                </tr>
                <tr>
                    <td>Prompt Payment Incentive</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPromptPaymentIncentive()}</b></td>
                </tr>
                <tr>
                    <td>Advance Payment Incentive</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getAdvancePaymentIncentive()}</b></td>
                </tr>
                <tr>
                    <td>Demand Side Incentive</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getDemandSideIncentive()}</b></td>
                </tr>
                <tr>
                    <td>Wheeling Charge / Green Energy Charge</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getWheelingCharge()}</b></td>
                </tr>
                <tr>
                    <td style="background:rgba(16,185,129,0.12); font-weight:900;">Sub Total</td>
                    <td class="right" style="background:rgba(16,185,129,0.12); font-weight:900;">
                        <b>${billTemplate.getBillInformation().getSubTotalThree()}</b></td>
                </tr>
            </table>
        </div>

        <div class="r">
            <table class="tight">
                <tr>
                    <th colspan="2">Billing Charges Information</th>
                </tr>

                <tr>
                    <td><b>Subsidy (M.P. Govt.)</b></td>
                    <td class="right"><b>${billTemplate.getBillInformation().getSubsidy()}</b></td>
                </tr>
                <tr>
                    <td style="background:rgba(16,185,129,0.12); font-weight:900;">Current Month Bill</td>
                    <td class="right" style="background:rgba(16,185,129,0.12); font-weight:900;">
                        <b>${billTemplate.getBillInformation().getSubTotalFour()}</b></td>
                </tr>

                <tr>
                    <td colspan="2" style="background:var(--soft2); font-weight:900;text-align: right;">Arrear</td>
                </tr>
                <tr>
                    <td>Principal arrear</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPrincipalArrear()}</b></td>
                </tr>
                <tr>
                    <td>Cumulative surcharge</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getCumulativeSurcharge()}</b></td>
                </tr>
                <tr>
                    <td>ASD Arrear</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getAsdArrear()}</b></td>
                </tr>
                <tr>
                    <td style="background:rgba(16,185,129,0.12); font-weight:900;">Net Bill</td>
                    <td class="right" style="background:rgba(16,185,129,0.12); font-weight:900;">
                        <b>${billTemplate.getBillInformation().getNetBill()}</b></td>
                </tr>
                <tr>
                    <td colspan="2" style="background:var(--soft2); font-weight:900;text-align: right;">Payments</td>
                </tr>
                <tr>
                    <td>Payment Received Against Bill & Vigilance</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPaymentForBillAndVigilance()}</b></td>
                </tr>
                <tr>
                    <td>Payment Received Against Smart Meter RC/DC</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getPaymentForSmartMeterRCDC()}</b></td>
                </tr>
                <tr>
                    <td colspan="2" style="background:var(--soft2); font-weight:900;text-align: right;">Dues</td>
                </tr>
                <tr>
                    <td>Vigilance / O&M Due Amount</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getVigilanceDueAmount()}</b></td>
                </tr>
                <tr>
                    <td>Interest on Vigilance / O&M Due (Till Billing Period)</td>
                    <td class="right"><b>${billTemplate.getBillInformation().getVigilanceDueInterestAmount()}</b></td>
                </tr>
                <tr>
                    <td style="background:rgba(245,158,11,0.18); font-weight:950;">Total Amount Payable</td>
                    <td class="right" style="background:rgba(245,158,11,0.18); font-weight:950;">
                        <b>${billTemplate.getBillInformation().getTotalPayableAmount()}</b></td>
                </tr>
            </table>

            <%--<div class="chart-wrap" style="margin-top:2px; height: 225px;">
                <img src="" alt=""/>
            </div>--%>
        </div>
    </div>

    @if(!billTemplate.getConnectionInformation().isNetMeter()
            && billTemplate.getTodInformation().getTodDetailList() != null && !billTemplate.getTodInformation().getTodDetailList().isEmpty())
        <div class="band-title">TOD DETAILS</div>
        <div class="mt-8 two-col">
            <div class="l">
                <div class="chart-wrap" style="margin-top:2px; height: 180px;">
                    <img src="${billTemplate.getTodInformation().getTodPieChartBase64Image()}" alt="">
                </div>
            </div>
            <div class="r">
                <table class="tight" style="margin-top:2px; height: 180px;">
                    <tr>
                        <th colspan="2">TIMING</th>
                        <th class="right">Units</th>
                        <th class="right">Amount (₹)</th>
                    </tr>
                    @for(var tod : billTemplate.getTodInformation().getTodDetailList())
                        <tr>
                            <td><b>${tod.getType()}</b></td>
                            <td>${tod.getDemandHour()}</td>
                            <td class="right">${tod.getUnit()}</td>
                            <td class="right">${tod.getAmount()}</td>
                        </tr>
                    @endfor
                </table>
            </div>
        </div>
    @endif

    <div class="page-footer">
        <div class="page-footer-left">${billTemplate.getConsumerInformation().getConsumerNo()}</div>
        <div class="page-footer-center">${billTemplate.getBillSummary().getBillMonth()}</div>
        <div class="page-footer-right">Page 2 of 4</div>
    </div>
</div>


<!-- PAGE 3 -->
<div class="page">
    @if(billTemplate.getConnectionInformation().isNetMeter()
            && billTemplate.getTodInformation().getTodNetMeterDetailList() != null && !billTemplate.getTodInformation().getTodNetMeterDetailList().isEmpty())
        <div class="band-title">TOD DETAILS</div>
        <div class="table-scroll" style="margin-top:6px;">
            <table class="reading-table">
                <thead>
                <tr>
                    <th>TOD</th>
                    <th>Particulars</th>
                    <th class="right">Current Read</th>
                    <th class="right">Previous Read</th>
                    <th class="right">Meter Consumption</th>
                    <th class="right">Assessed Units</th>
                    <th class="right">Final Consumption</th>
                    <th class="right">Net</th>
                    <th class="right">Amount</th>
                </tr>
                </thead>
                <tbody>
                @for(var tod : billTemplate.getTodInformation().getTodNetMeterDetailList())
                    <tr>
                        @if("IMPORT".equals(tod.getNetMeterType()))
                            <td rowspan="2">
                                <strong>${tod.getType()}</strong><br>
                                <span class="small">${tod.getDemandHour()}</span>
                            </td>
                        @endif
                        <td>${tod.getNetMeterType()}</td>
                        <td class="right">${tod.getCurrentRead()}</td>
                        <td class="right">${tod.getPreviousRead()}</td>
                        <td class="right">${tod.getMeterConsumption()}</td>
                        <td class="right">${tod.getAssessment()}</td>
                        <td class="right">${tod.getFinalConsumption()}</td>
                        <td class="right">${tod.getNetConsumption()}</td>
                        <td class="right">${tod.getAmount()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    @endif

    <div class="band-title mt-10">LAST TWO PAYMENT</div>
    <div class="box pad-6">
        <table class="tight">
            <tr>
                <th>Last Payment Month</th>
                <th class="right">Amount Paid (₹)</th>
                <th>Payment Ref. No.</th>
                <th>Payment Date</th>
                <th>Mode</th>
            </tr>
            @for(var payment : billTemplate.getPaymentHistoryInformationList())
                <tr>
                    <td><b>${payment.getPayMonth()}</b></td>
                    <td class="right"><b>${payment.getAmount()}</b></td>
                    <td>${payment.getCacNo()}</td>
                    <td>${payment.getPayDate()}</td>
                    <td>${payment.getPayMode()}</td>
                </tr>
            @endfor
        </table>
    </div>
    <div style="margin-top:1px; width:100%;">
        <div class="band-title">Last Six Months Consumption</div>
        <table class="tight">
            <tbody>
            <tr>
                <th>Bill Month</th>
                <th>Bill Date</th>
                <th class="right">Reading</th>
                <th class="right">Units</th>
            </tr>
            @for(var read : billTemplate.getReadHistoryInformationList())
                <tr>
                    <td>${read.getBillMonth()}</td>
                    <td>${read.getReadDate()}</td>
                    <td class="right">${read.getReading()}</td>
                    <td class="right">${read.getUnit()}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>

    @if(billTemplate.getConnectionInformation().isNetMeter())
        @if(billTemplate.getMeterReplacementInformation().getNetMeterReplacementDetailList() != null
                && !billTemplate.getMeterReplacementInformation().getNetMeterReplacementDetailList().isEmpty())
            <div class="band-title" style="margin-top:3px;">Meter Replacement and Read Detail</div>
            <div class="table-scroll" style="margin-top:6px;">
                <table class="reading-table" style="margin-top:6px;">
                    <thead>
                    <tr>
                        <th rowspan="2">Meter Detail</th>
                        <th rowspan="2">Replacement Date</th>
                        <th colspan="3" style="text-align:center; background-color:#eaf0f3;">Import</th>
                        <th colspan="3" style="text-align:center; background-color:#e4ecea;">Export</th>
                        <th rowspan="2">Net Consumption</th>
                    </tr>
                    <tr>
                        <th class="right">Prev / Start Read</th>
                        <th class="right">Final / Current Read</th>
                        <th class="right">Consumption</th>
                        <th class="right">Prev / Start Read</th>
                        <th class="right">Final / Current Read</th>
                        <th class="right">Consumption</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(var replacement : billTemplate.getMeterReplacementInformation().getNetMeterReplacementDetailList())
                        <tr>
                            <td>${replacement.getMeterDetail()}</td>
                            <td class="right">${replacement.getReplacementDate()}</td>
                            <td class="right">${replacement.getImportPreviousRead()}</td>
                            <td class="right">${replacement.getImportCurrentRead()}</td>
                            <td class="right">${replacement.getImportConsumption()}</td>
                            <td class="right">${replacement.getExportPreviousRead()}</td>
                            <td class="right">${replacement.getExportCurrentRead()}</td>
                            <td class="right">${replacement.getExportConsumption()}</td>
                            <td class="right">${replacement.getNetConsumption()}</td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        @endif
    @else
        @if(billTemplate.getMeterReplacementInformation().getMeterReplacementDetailList() != null
                && !billTemplate.getMeterReplacementInformation().getMeterReplacementDetailList().isEmpty())
            <div class="band-title" style="margin-top:3px;">Meter Replacement and Read Detail</div>
            <div class="table-scroll">
                <table class="reading-table">
                    <thead>
                    <tr>
                        <th>Meter Detail</th>
                        <th class="right">Replacement Date</th>
                        <th class="right">Prev / Start Read</th>
                        <th class="right">Final / Current Read</th>
                        <th class="right">Consumption</th>
                        <th class="right">Prorated Current Bill</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(var replacement : billTemplate.getMeterReplacementInformation().getMeterReplacementDetailList())
                        <tr>
                            <td>${replacement.getMeterDetail()}</td>
                            <td class="right">${replacement.getReplacementDate()}</td>
                            <td class="right">${replacement.getPreviousRead()}</td>
                            <td class="right">${replacement.getCurrentRead()}</td>
                            <td class="right">${replacement.getConsumption()}</td>
                            <td class="right">${replacement.getProratedCurrentBill()}</td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            </div>
        @endif
    @endif

    <div class="page-footer">
        <div class="page-footer-left">${billTemplate.getConsumerInformation().getConsumerNo()}</div>
        <div class="page-footer-center">${billTemplate.getBillSummary().getBillMonth()}</div>
        <div class="page-footer-right">Page 3 of 4</div>
    </div>
</div>

<!-- Page 4 -->
<div class="page">
    @if(billTemplate.getConnectionInformation().isNetMeterParent() && billTemplate.getNetMeterChildInformationList() != null && !billTemplate.getNetMeterChildInformationList().isEmpty())
        <div class="band-title" style="margin-top:3px;">Virtual / Group Net Meter Child Connection Export Detail</div>
        <div class="table-scroll">
            <table class="reading-table">
                <thead>
                <tr>
                    <th>Consumer No</th>
                    <th>Ratio</th>
                    <th class="right">Current Read</th>
                    <th class="right">Previous Read</th>
                    <th class="right">M. F.</th>
                    <th class="right">Meter Consumption</th>
                    <th class="right">Assessment</th>
                    <th class="right">Final Consumption</th>
                </tr>
                </thead>
                <tbody>
                @for(var child : billTemplate.getNetMeterChildInformationList())
                    <tr>
                        <td>${child.getConsumerNo()}</td>
                        <td class="right">${child.getRatio()}</td>
                        <td class="right">${child.getCurrentRead()}</td>
                        <td class="right">${child.getPreviousRead()}</td>
                        <td class="right">${child.getMf()}</td>
                        <td class="right">${child.getMeterConsumption()}</td>
                        <td class="right">${child.getAssessment()}</td>
                        <td class="right">${child.getFinalConsumption()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    @endif

    @if(billTemplate.getBillCalculationInformationList() != null && !billTemplate.getBillCalculationInformationList().isEmpty())
        <div class="band-title" style="margin-top:3px;">HEAD WISE BILL CALCULATION DETAIL</div>
        <div class="table-scroll">
            <table class="reading-table" style="min-width:0;">
                <thead>
                <tr>
                    <th>Head</th>
                    <th>Slab</th>
                    <th class="right">Rate (₹)</th>
                    <th class="right">Unit</th>
                    <th class="right">Amount (₹)</th>
                </tr>
                </thead>
                <tbody>
                @for(var calculation : billTemplate.getBillCalculationInformationList())
                    <tr>
                        <td>${calculation.getHead()}</td>
                        <td>${calculation.getSlab()}</td>
                        <td class="right">${calculation.getRate()}</td>
                        <td class="right">${calculation.getUnit()}</td>
                        <td class="right">${calculation.getAmount()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    @endif

    <div class="band-title" style="margin-top:3px;">OTHER ACCOUNT DETAILS</div>
    <div class="table-scroll">
        @if(billTemplate.getVigilanceInformationList() != null && !billTemplate.getVigilanceInformationList().isEmpty())
            !{var mid = (int) Math.ceil((double) billTemplate.getVigilanceInformationList().size()/2);}

            <table>
                <tr>
                    <th colspan="4" style="text-align:left;">Vigilance / O&M Panchanama Detail</th>
                </tr>
                <tr>
                    <td>
                        <table class="reading-table">
                            <thead>
                            <tr>
                                <th>Description</th>
                                <th class="right">Amount (₹)</th>
                            </tr>
                            </thead>
                            <tbody>
                            @for(int index = 0; index < mid; index++)
                                !{var vigilance = billTemplate.getVigilanceInformationList().get(index);}
                                <tr>
                                    <td>${index} - ${vigilance.getDescription()}</td>
                                    <td class="right">${vigilance.getAmount()}</td>
                                </tr>
                            @endfor
                            </tbody>
                        </table>
                    </td>
                    <td>
                        @if(mid < billTemplate.getVigilanceInformationList().size())
                            <table class="reading-table">
                                <thead>
                                <tr>
                                    <th>Description</th>
                                    <th class="right">Amount (₹)</th>
                                </tr>
                                </thead>
                                <tbody>
                                @for(int index = mid; index < billTemplate.getVigilanceInformationList().size(); index++)
                                    !{var vigilance = billTemplate.getVigilanceInformationList().get(index);}
                                    <tr>
                                        <td>${index} - ${vigilance.getDescription()}</td>
                                        <td class="right">${vigilance.getAmount()}</td>
                                    </tr>
                                @endfor
                                </tbody>
                            </table>
                        @endif
                    </td>
                </tr>
            </table>
        @endif

        @if(billTemplate.getAdjustmentInformationList() != null && !billTemplate.getAdjustmentInformationList().isEmpty())
            !{var mid = (int) Math.ceil((double) billTemplate.getAdjustmentInformationList().size()/2);}
            <table>
                <tr>
                    <th colspan="4" style="text-align:left;">CCB Adjustment Detail</th>
                </tr>
                <tr>
                    <td>
                        <table class="reading-table" style="margin-top:3px;">
                            <thead>
                            <tr>
                                <th>Description</th>
                                <th class="right">Amount (₹)</th>
                            </tr>
                            </thead>
                            <tbody>
                            @for(int index = 0; index < mid; index++)
                                !{var adjustment = billTemplate.getAdjustmentInformationList().get(index);}
                                <tr>
                                    <td>${index} - ${adjustment.getDescription()}</td>
                                    <td class="right">${adjustment.getAmount()}</td>
                                </tr>
                            @endfor
                            </tbody>
                        </table>
                    </td>
                    <td>
                        @if(mid < billTemplate.getVigilanceInformationList().size())
                            <table class="reading-table" style="margin-top:3px;">
                                <thead>
                                <tr>
                                    <th>Description</th>
                                    <th class="right">Amount (₹)</th>
                                </tr>
                                </thead>
                                <tbody>
                                @for(int index = mid; index < billTemplate.getAdjustmentInformationList().size(); index++)
                                    !{var adjustment = billTemplate.getAdjustmentInformationList().get(index);}
                                    <tr>
                                        <td>${index} - ${adjustment.getDescription()}</td>
                                        <td class="right">${adjustment.getAmount()}</td>
                                    </tr>
                                @endfor
                                </tbody>
                            </table>
                        @endif
                    </td>
                </tr>
            </table>
        @endif
    </div>
    <div class="band-title" style="margin-top:6px;">CIRCLE LEVEL ELECTRICITY CONSUMERS GRIEVANCE REDRESSAL FORUM
        (ECGRF)
    </div>
    <div class="table-scroll">
        <table class="reading-table">
            <thead>
            <tr>
                <th>Member Type</th>
                <th>Name</th>
                <th class="right">Contact No.</th>
                <th>Member Type</th>
            </tr>
            </thead>
            <tbody>
            @for(var ecgrf : billTemplate.getEcgrfInformationList())
                <tr>
                    <td>${ecgrf.getMemberType()}</td>
                    <td>${ecgrf.getName()}</td>
                    <td class="right">${ecgrf.getContactNo()}</td>
                    <td>${ecgrf.getCaseHandling()}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>

    <div class="page-footer">
        <div class="page-footer-left">${billTemplate.getConsumerInformation().getConsumerNo()}</div>
        <div class="page-footer-center">${billTemplate.getBillSummary().getBillMonth()}</div>
        <div class="page-footer-right">Page 4 of 4</div>
    </div>
</div>

</body>
</html>
