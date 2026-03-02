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
                <img src="data:image/png;base64,${billTemplate.getCompanyInformation().getLogoBase64()}" alt=""
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
                <img src="data:image/png;base64,${billTemplate.getCompanyInformation().getAlternateLogoBase64()}" alt=""
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
                    <img src="data:image/png;base64,${billTemplate.getBillSummary().getPaymentQuickResponseBase64()}"
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
    <table class="small">
        @for(var contact : billTemplate.getContactInformationList())
            <tr>
                <td style="padding-bottom: -15px;"><b>${contact.getDesignation()}:</b><br>
                    <b>${contact.getOfficeName()}</b></td>
                <td><b>${contact.getName()}<br> ${contact.getContactNo()}</b></td>
            </tr>
        @endfor

        <%--<tr>
            <td style="padding-bottom: -15px;"><b>Assistant / Junior Engineer:</b><br> <b>Samgam Nagar Zone</b></td>
            <td><b>Mr. Rahul Singh <br> (1800-233-1912) </b></td>
            TODO:<td rowspan="2" style="text-align: center; padding: -15px;">
                <strong><span
                            style="font-size:11px;">WhatsApp ChatBot / Call Centre: ${billTemplate.getCompanyInformation().getCustomerCareNumber()}</span></strong>
                <div>
                    <img src="data:image/png;base64,${billTemplate.getCompanyInformation().getWhatsappQuickResponseBase64()}"
                         style="height: 54px; width: 54px;" alt="">
                </div>
            </td>
        </tr>
        <tr>
            <td><b>Executive Engineer:</b><br> <b>Indore East Division</b></td>
            <td><b>Mr. Shyam Singh Rathore <br>(1800-233-1912)</b></td>
        </tr>--%>
    </table>
    <!-- Consumption Trend -->
    <div class="mt-1">
        <div class="band-title">CONSUMPTION TREND (IN UNITS)</div>
        <div class="chart-wrap">
            <img style="width: 100%; height: 140px;"
                 src="data:image/png;base64,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"
                 alt="Consumption Trend">
        </div>
    </div>

    <!-- Major components + awareness image -->
    <div class="mt-1">
        <div class="band-title">ADVERTISEMENT / AWARENESS</div>
        <div class="poster" style="height: 130px;">
            <img src="advertisement_img1.jpg" style="height: 100%; width: 100%;">
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

            <div class="chart-wrap" style="margin-top:2px; height: 225px;">
                <img src="data:image/png;base64,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"
                     alt="">
            </div>
        </div>
    </div>

    @if(!billTemplate.getConnectionInformation().isNetMeter()
            && billTemplate.getTodInformation().getTodDetailList() != null && !billTemplate.getTodInformation().getTodDetailList().isEmpty())
        <div class="band-title">TOD DETAILS</div>
        <div class="mt-8 two-col">
            <div class="l">
                <div class="chart-wrap" style="margin-top:2px; height: 180px;">
                    <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUQAAACcCAYAAAAH8NIwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsEAAA7BAbiRa+0AACd5SURBVHhe7Z15lBzFnee/mVXV1fepbrXUp+4DIamFuIUwNhgY8PnsZ/CMGZ/rfWtm7F3vepnZ/WN3Zmfe2zee8Y49a4wZrwcMmMvYYLAQIIQ5JCSh+2zd6m713V191l2ZG9/oTFRqunV2t7qqfh9IVWfkEZFxfOMXkRGRhq2AIAiCANP5FQRByHhEEAVBEBxEEAVBEBykD1GYNJizDGPkl5nMtoBQCLCUg6kO8Jhh2LAsG16v2vdYGAzH4TENJJQbbPWrLvJ6DBRk+7S74VxHnB9BmDBEEIUJYyQrKRFL2IjGDISClhI7YHjYRqDPRleXhSH1d1C5B0PqnCgQi41c27DSi5yqQTz09G69n5PlQZ7fi4IcH8ryslBW4Mfc8jxUl+VihvrbVoJZmOtDcW4W/D6PVl1TCaYgXA4iiMJlEY+PCFE4DPT1J5TIGQj0JnC61UZvwFJCOCKK52O0IJ4L6l51aS5qy/KwpKoQc5RQUjyrSnNQqASU+DzSGyRcPCKIwkXDHEORC4dttLZZqhlso7ubfyfQ03thAjiaixHE0VAg65UoLpldhPmVBcqi9OGq6mJlXXpVc9uUprVwwYggChcMm8Lsw+vusXD6tIWu7gQ6Omwtgpebiy5HEEczpzwfS5XlOLciD8trS1A/I1f3YYrVKJwPEUThvNDi49bamsCpZkuJYEKJoa0txIliIgXRJT/bi4XKYlw4qxCr6kuwsrYYPq+IojA+IojCOYnHgbb2BA4cVCLYxT5BW/cbTjSTIYguFMGa0lwtjnevmIUFlfnIyfI6RwXhDCKIwphQCAcHLWz9IKYsQwsDgxwe4xycBCZTEF04fGdmYTaWVRfj/ptqUZbv11akILiIIAofIRq1sXtPHDt3x3WzOJFwDkwiUyGILuxL5AuXO6+eha+sqRNrUfgQ6VARzqKr28LTz4Xx3uaYHj84FWI41cQSFnqHonj6/Sb8h3/bjgOn+zEYdgZEChmNCKKgCQZtbNsewxNPhdHTY1/2W+NUgI2jk13D+I4SxWfeb0ZLbygjnlsYHxHEDIf9grQK178ew7vvZa6V9OR7J/GjdQdxpH1AW5BCZiKCmMHwxcnxEwm8si6Kk6fUToaz42Qf/va3+/HHg10Yjkh8ZCIiiBkK5xHv2RvHG29GEQiIReTSEgjhJ68dxvNbm9EzFHFchUxBBDED4ZvjLdti2LQ5pqfdCWczEIrhqU2n8G9vn0DngIhiJiGCmGFQAN95N4YdO2OITcIA63QhGrewbncbHn7jqH4jLWQGIogZBMcXbnwrioONiUkdZJ0ucE3Gdxo78c/rD2M4IsNyMgERxAyBw0nYX3jkWEIv0iBcGBTFTYe78MNXGuXtcwYggpgBcHD1ho1KDI+KZXgpxLWl2IUf/aERoZhEYDojgpgBNB6OY+++uIjhZUBLcd2eNry0vQXBaBpO3xE0IohpTnNzAutfl5cCE8XPNhzFnqaAzGhJU0QQ0xQW2N5eC69tEDGcaP5JNZ2beoIjH8IS0goRxDSFX7d7d1MUAwNSaCearsEIHt5wRP8K6YUIYhrClygHG+M4dlw6DSeLLUd78MbedkTiEsfphAhimsGmcmeXhe07ZNzcZPPCBy3Y19Ivw3HSCBHENIMLNmzdNrKWoTC5BIaj+PWmU4jLuM60QQQxjeCA6+Mn4jjVJMNCpordTQG8e7gLYRmKkxaIIKYRbLlt3hJLy1Wupyu0Dh9/5wSk0ZweiCCmEcePJ9DXJ823qeZ0IITX97YjJFZiyiOCmCZwFZv3t8YyesCwacVh2COixF/TiunN4L465u67bhMF4/yZ909JszkNEEFMAyzLRsvpBAKBiVVD27YQi/UgER/S+/yNRNr1Zllhdazvw/1YtEtdcOUajt54CLUdb6Csf78OR3lgF+raXsWcjvXwxQYwo2+PPj6n9RXUdbwGrwr/RNLWF8aupj5YMoUlpRFBTANCqmzv3DXxS97HYr1oOvkz9PS8pfYsdHb+Aa3Nj6Gz/bcIh5rRF9is/25pehTNTb9AIhEcuXCKoTVYOHxCieKI/15bWYHKrbdoKSKeAi2WXaWr0Fx5O4ZyqzCUU42Yma3PnUie39qEwZAMd0plRBDTgFDQRmvbxFpnlhVFf2DLhyJnJcIIDh9HTt5cFBVfB7+/EuUVd2N21Z8hJ6ceRUWr4fHm63OnmtxIN3KivQhnFev9uOlHe9l1ShQtRFWYLE+Wds8NtcEf7UNv4WK9P9EcOD2AzoGJtTyFqUUEMcXhG+XGI4kJ7zsMDh9GJNqJ7OzZIw6GieISJXqeXAR6N6O/7wPtPDh4UDWXe1FStkbvTzWGEr2CoeMwlIB7EhFkUxzDXSgaOob+vHp4EyHkB5v1eXnB0whmz0RCCeZk8eaBToRj0peYqoggpjgUxMOHJ7YA2nZcNYe3IK6azBS74HCjtg5ty0Jp2W3IyipTYtmlzxsa3Iu8gsUwJ6EJeqEM5VarrUaLtu1sBUOnUBHYAV9iGCF/OTx2FP5Yv/p7hnPV5LBRCSI/PyCkJiKIKU5/fwL9E7yAg6EEpbRsLcpm3KGbwV5vCXy+UkRj3Wht+RXC4SbVRF6lm9PRaLeyImudK6ceih/FsK9gATrLVqO7ZBXC/jL0lK7Q/YXdxcsRyq6AZfjQVdKgj00mHf1h/YJFSE0MW+H8LaQg778fw+atk9eRH410KsMrSwlisX6rnIgPaEuMfYjucYql6bk8C7FhpRc5VYN46Ondjkvq8sCaOfjztXNgTuTYHmFKEAsxheFHo5paJre/KstfocWQ8Dc7p1ZZhNXKivTqzZ89+7LFMN3YfqJXPkqVooggpjCGskDaO8TAn24caO1X1qGYh6mICGIKw75D+YLe9IOdUCe7r8yYTOHyEEFMYTo7RQynK0faB52/hFRCBDFF4XCbtjYZ7zZdOdoxCDHeUw8RxBQlGmWTWca7TVc49CYUmfjplMLkIoKYstgYklWxpy3dg2H5Kl8KIoKYopimgWBQCtx0JTAcgyFvmlMOEcQUxTRtROQrmNOWYdVc9kjpSjkkyVIUDu2wpAtx2sI1KqXFnHqIIKYotp1ezbHSUhPZhTZqynIdl9RHFotNPWQuc4rC5vJPH0m9wb/sVmPfmmHYKC83UVdv4OqrvMjy2vBF22B4/QgnDBzqMvH6vg5sONCpxH/E2qLApFJu/d1/ugVFOT5nT0gFRBBTlFgM+JeHU0cQvV4DPp+N/DwD1XUmquYlYORE0YdhVHp8qGzfgPiBn+pzzeJFQPESGOXXw8ytRHfYj23NUWw42If2/hCicSAYTUz7dQdf+v5aFGR7nT0hFRBBTFG4sMNPHwlNa4vJq7QgO9tAnhLBmTMNlNRE4S0LoSs2jE39J9E41IXv1lyDVYN7ENvzT85VZ2P4S2EUzVcCuRTekiWIIBcnBnOxpSmOnc1D4Hed+oJx9AxFptUH4zmX+aXv34I8vwhiKiGCmKJEIjb+32MhhKfZ0numCW0FFhYZul+woCIGu2wAPdYQjkS78Fb3cQzFo5jlL8DXqlZgzfABJPb8o3P1+TEL6pUH9TCVONrZFQgkinEwkIPtpznUJYJ+FR9tfSF0D17ZV/B5yjJ85sGbRBBTDBHEFIWfHX32N2H09k6P5MvPVxZgiYEZZUoESy2Ei/vQZHchYA1jR38bToUCzpnATH8+vlq9CjeHjsLY/Q+AFXWOXBxGVhGQOxuGEkkzvxZRbynaYuVo7C/E8Z4YhtRtm3sjONqhLMn41Davq8ty8cjXVyM3SwQxlRBBTFE4KHvd+iiamq9cP5rfTwFUzeFKA0VKDCMFAzhutqE9MoiTkV4cGOx0zjxDiS8H36xZjbWxVhj7fwI71OEcuUwME0Z2OYycmTDyqmGr32FfFZojM3FsqAiDwQhOD1g41DqApp7J73ttqC/B/75vBXwyGDGlEEFMUTiX+Z13Y9izb+oXIp0500RNtanfEseyQ2g0W7BvqA39VgjHgwEEE2NbfH7Ti2/XXos7jEEYe/8Z9nCTc2TiMby5SiBLYfvL1G8FEjnV6PPWox216Bo20B00sP/0ELYe60V8EgZ0fm51Nf7izoUT+kF8YfIRQUxRmGr79sfxxpuX1ty8WIqLDdTWeFBf51HKFsNhoxWvBQ4hYsXRHRtGIBZyzhyfr6lm8mezvfDs+jslhi2O61SgrMesfICfSc0qVBZkDaJ5CxDIWoiAUYXBUASNXRY2H+nWq9RMBD+4dwnuXjHL2RNSBRHEFKary8YTvz6/EF0qfENcW2Ni0WKP7iNstruwLnAAh4M9CFkxDMQv/I3OFyuvwpfLZsG36buww92O6xXC9KktS1mROUogi4DCxYjmL0G4eBWGE160Dhh4uzGA9XvbnAsunn/91nWYV3FlvlMtXDoiiCkMh948/PPQhE/hq6v1YLESwTl1ShzCA3ht4BBe7jgIZpQErIuegXFPxSJ8rXIx/G/dB8Sm2xfp9Ehx9b+pnk9ZkoXzYM5YCavsetiF89E3FMbW5jhe3dOGg6cHnGvOz6s/uBV+n7KmhZRCBDGF4ZCbF38fRmvbpSkiZ43wy3AeL1BaYmDOHA/mLrIRsmPYFj6Jp5t2IdrShfK3j6Hz4wsQmVmA0q1NKGjs1Bd2KLdYrg+zX2uEGY6j54Y6DC4od+6u7qtEZm1pPR6sWo7sbT+A1dfoHEkRfAXwlC2HUboCRvl1iBs+nOz3YcOhASWQHbpiSKioj6l/kpf6WlpdiB/e34CcLBHEVEMEMYWJx21s2RbD1m0XtxCpz2fA7wdyVIuxqpqzRuKIZ4fRHO3D+t5D2Ds48uY3pzmAwsPdyOoLomvNXMQK/Ch/7wQCDdUo2dGMSHkBDE4b8ZgYrivRYtlx+0JYfi9tLVxfWoOHqlfCu//HsFo36numMmbBHD2DxixrgJFfjcGYFzvbfdh4ZBDN3UFEEsCQ+ueBW+rx+dXV8qGpFEQEMcXp7LTw1DPh885Y8ShjhTNGOGh6RrmJkqoIjLJhtIeGsTPUjI09x50zz8Y7FMGs9Ye0IIaVhUiyOwdRuvkU+q6tQcGBdsSKczGwuAIVG46g8xMLEM/3o6FwFr5fczXKTjyD+Inf6evSClqPRfNUs3oBTCWSCU8+WsNF2NZq4Jq6Isyrnu2cKKQSIogpDuc0P/5ECAODYydjQYGhmsMmilWTOK8kBqu8H23xATTFerGp79R53w6PFsTs1n4U72nV1mHfytmY8d5x2F4vBpbORNk7x9GlLMT55bPxPdVMrmt5EfHjzzl3Sm/MvGogvxZGxQ3wlC6DWbTAOSKkEiKIKQ4/NrV5SwzbPjgzHpFWYFnZyNS5vCIL0bI+HIm3o1+J38FwB44Fe50zz0+yINLyq3z9EMxwAkOLyhGqLNR9h8X7lECW5sJUTfiyu67D12uWY2nLS7BOPO/cJXPwLvgzeBd9Tb/FFlIPEcQ0oKs7gWeei6BCNYVnz/agoBCI5A3iiLcFzcF+tMb7cXDoo7NGLgQzlkBucx/Cs9RNFblNgZFBkIpoWR6ixTnIO9ULI26htqYWX25Yg2Vt62AcfVKfk2lk3fb4SF+jkJKIIKYBUWWZnTyRQNgbwWFvE3YMtmAoEUFzpE/9Ts3A7ersInxvzk1Y1LkRxpHHYMcy70PtpmoqZ93wj8pMTJ9FbjMNEcQ0gEl4KtSP/3XkLfQlgqppPLVj/Yq92fir+WuxrH83LK5pGL3w8XrpRNa1fwOz8lZVqmT+cqoiKZcGcAXqYl82onZsysWQ0DJcFjwJa99PMlYMjdxZMEobRAxTHLEQ0wj2E/7l/pedvanhPyox/GSWifjb33BcLp9Q3MT/fb8Oi2YMY2nFEH62tQbRxMgg59vndWNNXQA/2jQH/WEvvrLyNFZX9etjVxJfw0Pw1Nzj7AmpilRnacTc3FK1lTh7k0u2x6sXa1g7wWLICR87W4vQOpit9+eVBvEPdzXiv649jqrCMK6uHMQfT5bi2qo+fO+mk9jcXIJg/MrOCDFyK+Gput3ZE1IZEcQ0gstrPVDdgBzP5H7YKFfd/3OVV+FPcrPh2/pfHNeJ4fRADna0FqKm8Mz4yIRl4MUDFVg1qx/1xSF0D48MaSnOjiESNxCJXcFsbHjgXfYXqiT5HQchlRFBTDNWFs7GjSW1zt7Ek61E90/KF+K+gkLk7P0R7PCFj2k8HwnbwLunSpCbZcGGga6gH4GQF22DfvRHfFgyc1ifl+21EFFN6GjCgMew4TGvXK+PZ0YDzIobnD0h1RFBTDPyPFn4zMwl+iXLROMxDNxePh/3F5XCd/DnsPsOOEcmBi6UUJ4XQUEWl//3oifow0DEi2O9ucj1xVGeO/KdlMXlQ2ju8+vmcmluXAnklVk1nJ8w8Cz+JgwZhJ02yEuVNCRqJfBC+z78onm74zIxfKJsLr5RXovixp8j0f6e4zo57GorRF5WAgvKhnGkJw/DUQ9Wzhp5gx2Om9jaUoywairPKQ3pc64E3sXfgHf+nyqzQr69nC6IIKYpffEQHj65BW+Os2jDxXJjcS2+WrkQVYf+BXbHZsc1c/HMvBHelQ/pz6QK6YM0mdOUQk82vlLdgHLf5c+auLa4Gn86aykqD/xYxFBhZM+Ad+m/V03mYsdFSBdEENMUrsVXlV2I781d47hcGlcVzMS/q7kG9Sceh9m9zXHNbHxXfxdGfr1SRik+6YakaBpjqP+WFc7E3yy8tDFyNdlFeGjeWsw++RvYTa8C9sR/nS7V4Go2RpnMSElXJFXTnFzTh+WFlfjy7BUXtYJzkS8b/12JYXnr67CO/kqJ4ZX7/vO0QAmgZ9Zt8My7b+QD+UJaIoKYAXAozperVuLaomr9nZPzUej146E5N6Gubzdie3/kuGYwhgdmxXXwNvyViGGaI4KYIfhND75bf6Ne2p/jCcejLCsX3665BouGDiO24386rhmMqkDMGavga/hvI58tFdIaEcQMotyfjwfrb8DNJXXwjmEpzlBi+OVZy3FjrB2+XX/vuGYyFMPV8C3/z/JGOUOQcYgZCL+j8qvTO/FmzwkMx0dmf3BmywNVDbjN6oVv3/+BHWrX7hmL6YNZeQt8i7828kZZyAhEEDOUASWEr3YfwQtt+xBMRPHA7AZ8KisG7Psx7IFjzlkZijcPnvpPwzfn80BOpeMoZAIiiBlM2Ipjc6AJPdFh3J3tgXfPD2H3H3GOZii5lfAteACe2bcCvpHvyAiZgwhihhOzLQzGgihofBSJEy84rpmJWboCniXfglm8GIZHlvPKREQQBdj8b7gViaaXkTjyhOOaWXgX/jk81XfCyKtSpULeNWYqIojCGRIhJPqPIr7j72EHWxzH9MbInQ1fw18rq3AB4JGv5WU6IojCR7ApjAd/jkTzOtjxcPrNUlEWIMcUeus+DQ8/Ku+R8YXCCCKIwthEArBCXUgcexJW737Y4e7UF0bDo5frMkqWwrfwK0BuNQxfnnNQEEQQhfORCMMKHETi5G9hDZwYaUpbcedgimB6ddPYLJwLT+09MEuXy8fkhTERQRQuCNuKwVaWotW2UQuj1X8YiF+ZlaovGG8ezII6oHABvJU3wyy/RuV4r9rkpYkwNiKIwsVhx2EPNSHRsUX9noLVdxD2wMSsyj1RGPk1MFWz2CyYo5rHy/TfylVbioJwLkQQhUvCZrM5NqjE8LDaTsIaVFZjYL8WSVyBLGXk16mm8NUwlEVo5lXBKFoMw1+sDihr0Liy320WUgcRROHy4KKxarMjvbCHW2BH+9Tvadh9h1TT+hjsYOukCKTuEyxZDLNooZ5eZ2QVKjFUFiEXYeBqPiKCwiUggihMHO6K2okIbGU9giLJv8PdIyKprEc71DHyBjuqjrMP0oqOXDMa0wfDl6+2AsBfogXQyKvWA6eNnAp1PAtGdtnIW2L9kXiVjUUEhctEBFGYRJysZXG4jrIiE7EREVQiySE8hmnCsvmhA1qZzrl6rUY2c7ljKo3zjggev33MPkCK3ocvRcZf11EQLgURREEQBAcZfyAIguAggigIguAggigIguAggigIguAggigIguAggigIguAggigIguCQseMQuwYjeO9wl7M3Nncsq0Se/8yCAIcOHcIrr7zi7AFZWVn4/Oc/j9zcXLz22mvo7e1FeXk5PvnJT8Lv9+P5559He3u7Pm/lypW4/vrr9d/JJBIJbNy4Eddddx0KC8981IjJsmfPHsyZM0e7nzp1Ch0dHfq8SCSCN998U+/ffvvtqK6udq4C3nvvPbz//vv678rKSn08JycHv/nNb3D33XdrN8uy8MILL2DZsmVYvHixPpfhOH36NGpra/X+eBw8eBCbNm3S53384x+Hx3NmdkgoFMLvf/97rF27VvszMDAwrr9er1fHUU1NjXP1+NixISROv+HsjY1ZehXMwgXOHrTfv/71rzE0NOS4ADfccAOuueYanVZ81vz8fJ1WM2fOPCttGdbbbrsNs2fP1vuE6bF7925s374dFRUVH8arS09PD1pbW7F06VKdNk1NTbjpppuwZMkS54wReB79v+eee3S60t8dO3bgC1/4gs4bPP6HP/wBn/nMZz7MD3yWWCyGsrIyvT8WwWAQ69evRzQaxcc+9jH9TMmM9nei0ibdyFgLMZ6w8fAbR8fdXvygRWWSs+uKBQsW4Dvf+Y4WA4rbt771LRQVFWnhY2H7+te/juXLl+t9ipZhGNrtm9/8ps5w69atc+40Qn9/Px577DFs2bIF8fjZawy2tLToAlZQUKCPP/HEE1oAyebNmzFjxgzce++9eOuttxAOh7U7oQDcddddOpxr1qzRAsXjvB8LH+nq6kJjY6MuFC50++Mf/6gLHsPS19d31n0Jw7Nr1y7cf//9usDwnsnwnt3d3di2bZve533G87eqqkrfiwX4vNgWrNa3kNj/k3E3PfslCcbbV7/6VR0XTCPGx+rVq/Hcc89h7ty5Ol0oai+++KJOB4aJacrzKBIvvfTSh/FNOjs7dXjvu+8+zJo1C2+//bZzZEQsmSYULFYWFMovfvGLOH78uM4HyezcuROBQAB79+7V+/T32LFjaGtr0/v8m9e5+cGtGPfv36/zENOH1/M3mTfeeAP19fW48847sWHDBi2QyYz2d8LSJs3I6CZzNG6Nu8VHiSGhNZSdna1rUJ/Pp/+m5VZSUoK6ujotEgsXLtRCSAuEvzyHBeTqq6/WmZ5WFDM2N55PcWEBHQ0z7rx58/Q9Fi1apAuYa13S+mChpCgyTKMzP90YRmZohtM0TW3t0FqlyLHQMbzJjQMKIK0IisAvfvELbQlRrGm9uDQ3N+tfHqfwMgwufJ59+/bhjjvu0IVrcHBQu4/nL+OE4WL8XRB2Qv0fHXcbDeON8cs4oz9MB1rwFBJabTzG8NPaO3LkiA6Tm6alpaXaamI803Lms9HiosDyOEWf1qUL443Py/vxefi8v/zlL3XFmdwiYDpRhBhHR48e1aJEf9kKYBi4z3yTbPHTbzddWCE9+uijWiAfeeSRs+JueHhY50PGK/MYRd5lLH/JhKVNGiF9iJcJMxAFiBmX8JduLJDJsGDRjRmbYsPamIWFmW8sKFDM4KS4uPis+7kFnYz2iyJI64XNIdb+n/3sZ3XmZrOe92OBoLUzujlEYWChZ+FggfzEJz6hLSXXgiD0h4XtwQcf1H+zCelCsWQhpOjwGMWRYTuXv7SoeP5UwfAwLvjr7rvpkgzdKRo8RsuM6UXripUALXIKDFsELjzGZqgbP7TUvv3tb2vLnunoQtGjINONAsY4IYwTih6P8z6MMxfmLYozxZWCyK6XW2+9FTfffLMOmwu7Ul599VWd7rxX8jON5+90SpvpggjiZcJaltYBMx0z7OHDh7XIsS+R4sQamLXzgQMHdKamFcF+O/49uiAmQ2uFhWssKFys0SlCvAfPdaFQsv/rS1/6Ej71qU/p5qILrVf2MbIQ0NJJhvdh844iQFGkcDPcbj8W4b3YFOUxnk9hJqwE2MRik5N9aCywfF7eh4znL/1z7zEV0PKjfxQSptWJEyd0GJgWhIJBdzZZGfe03LkxvWhxs/uBIsHnTLbKGf98Vgop8wPFlOe5lhahINHqZ9zwfhQ0NmMZd65VymY3WwWj4T0YNoadTVrGP61wVpQuTHdWYuwmobC5Fux4/rpW4nRJm+mCCOIlwAzndnAzI99yyy3akqIIUBCZ6SgcrNlZuJghKZrMrMxkPJaXl/ehINKiGJ35WDDYfHLhNW4Tlf2UtMZYs1911VVnNd94Ds9NhvemHyys7B9iE5zhd5+BsDCwCc7Cx3BT4Fmo2e/mwqYdz+EzsvBzn7BQs3CyKUq/6df8+fO1uI7nLwskC3myVTIZMDyuyPAZKRqsSJhWTJeGhgb9TAwTxYPubJKuWLFCV2qMW96DAkHR4XluX6kLrWvGAS1IWmq0GBl/fHY3LVg58lkpsHSjOPE6VjK8J9ORFR3janR+oDvji8covrw39/kCxIXubOKyoqS7myfG85d570qnzXQkY98yt/WF8dfP7nH2PkpRjhd/+4WrUZBzxvqaSiigH3zwAW688cazau50gYLCgk1ry7WixsOODiC+i9+KbnVcPop3xQ9glpwRiKmG3Qe0CGlxpToXkzbpRsYKYiAYxYGWMx3PY7GyruSsYTdTDfuCWNOnY9PFfeniWlDngsNu7J5dzt7YmEXz9crZVwpaYmzO0tpMdS4mbdKNjBXExBhvkcfCY47fzydMERf6PWhZMVu4TGSBWEEQBAd5qSIIguAggigIguAggigIguAggigIguAggigIguCQsW+ZQ4kYArGxp8a5lPvz4EsaysFxgZwJ4MJ5phzhz9+TJ0/q6VscnMtZAXTjjAbOXuC0L8564IyA5IGunBnB6/jLifWcvZIM55dyJgPHt3FhCM6K4cyC0YNlOUOBMxV4jHCGC6eecbYI4awWTknjoGGGizCsydPvxoL34KwOZhGOh2T4CWeguM/GsHB2A6e/cZriWP5yn4N9OePiQgb6BkM2opFzZ8v8fFPFq7Oj4DMnz+zhrBLOnOHcXfcZOEaQccS/z5c2LryWc375jMkwbTg7hzM6ODOFcJaQO9WRM1u4VBrHJ9IPd644BzxzJhDTkjD9mXZu3BKOA2ReOF9cMV/w+egv04T5gPucQZQ8eyk5bujOvMYwccYR8yr9cuOEccE8wi0TxyF6/ofC+Tuj6IkF8fOmbdjR3zrmdjTYi+UFlfDz4+gOzIDMdJy3y8LuzkHlPo9R2OjOuaYsfJxsz4zFqVAUU85/ZaFwYYbkiioUJ2ZYiopbCFjQOIWMgrh161YtXhQVHk+ew0oBoj8UHi5PRv84N5Wr1HAKFwsbZ7xwniyno3GfmZ/3ZiHivRhubrzWnU5IODWQUw95DQWG810JCzuXMnOfzS1sLFBj+cuVfigEF1rIenotbN4SR1OzNeYW6LMxexZX9HEuUFCgWGlwLUhOo6P/FHt33i7DzHDy+SgAyeEfK20I3Xke7+NWNsRNG4oY/eM93OmL7mIdFDVeSzGlKDIe+TfXLGRcczoh3X/3u99pv5l2hOHkohAUUMY595lOFLBkmG/effddLcascJhWnJvNvMR44AIdbl5iGtCNz85KgHmFafn000/r/EuBpvuzzz6r8xufgXlzrMo33cmspx3Fu4FT4257Btuds87AjMMFRTnHk4sYcIFUChIzJ1c/4QR6zmWlMHAeMAsAF1rgeZw3ylVTWBhcKA4UCwpr8txYwrnRPM77M9NzsVFaH1xFJhn6zfNYKCkKhBmfGxdnYAFhAUie/sdzGUYKBC1eFiwueMBVcpLDx0LM52DYRluSyc/Ggks/xvOXwsPCxeltFOML4eixxLhbZ+dH70FRYNqwIuIisEwLxiH954K13KflxMqFz3++tKEQMbw8j+cn46YNn5UWHuOIlllyHPE5KToME/3hcaYj70dLmiLIipNCyvu4sGLlfGpXdLm2JEWdIpkcdwwThYvXE87L5nNwHj3jP3nxCcI4YVgovExz3othoYjyuVnRM654X1YMTHM3P2USGS2IEwEzOQsHMxfh36xVWeMmw8JBcWMhYIZnQWJThzUyrRkKSHLBY1ONFohrlb388staDJMzOi0HWqcsiNxonTGjs4DRAqFVRlGikCY3xylQbDoxrCxIbCZR5OknV1FxoYixIDEctDIYRhdawlyIlBYP/aSwn8tfWiIs5LSKpgpaeIxj18qh1UXBYbwlMzptKBIUQ1p8Yy1w4KaN+9ysHAkt4mQoKLTeGUesTHkv5hPGhbsQA0UsWRCZjowzVjIUXqYB/eLfFFUX3ouVgPtsTBvGN69zLV8X/k1hZVhY8XGREZ7DbgD6zXAyPAwL4T357LQqMw0RxMuEmZsiwgJFKISscSl0yfAcZnRmWmY2ChLFiJYIVzphIXX7ogiFg5mVBYRNXS7ywAzL611oadA/Fkiuk8d9igChEFC0KJjuqjQuzPC8Ly0MhptWFUWRBdwtSHwGNvvY38R+JoaNYXXhs3ClGK6GQ6uE15Px/KWfvL9r0UwFfC7XGiJMA1YCrICSGZ02jBeKAa1oPgcrIjd9iZs2FCj+UphoVbESSIb3YvxwY/q5/XqsNGiRsoJgfCXjNvXd5jJFnHHLdEkWudHPwHszPEw3nucKJaH4sa+b4WBLxm2e051py1W++RwMrwub6MkWc6YggniZsAZnnxlXMH788cf1atKrVq3SVgfF6ZlnntHHWDtz6SkWOloLrJ1Z8Pi5AS5rz0JAcXXhORQhZmwWyHfeeUdbCRQhF+7TQmF/EcWSGZ3NK8L7sbCxsNPCGA3vy08GcDkorvP35JNPauGgv4QFgiLNfiWGj34kizGPM7wUHYqMy3j+0jJzRXiqoAhRdBj/TBd+64SfDeCznyttKEL89AAX12X8UkySuxzctKGIsu+OcURrmemeDEWN8cON57q4lQZFlHGSDEWKYaZFxwqF+eOpp57S90iO/9Gw0qQlyn5B5q3kCplix2t5D6ZZcn8k/aB1SGFMFlHGz+g+1UwgY98yt0cG8cCu5529j1KVU4gfL70XBd7JXWmGFhO30Z3mbL7x5cjnPvc5naFTHfaB0QKhuJyP9o4Enn727G+RJMMXKp++169EynGYYtItbUbDVge/0cIFhinQmUTGCmJHZAj/2nx2n89o/rL+xkkXxHPBN320Rs5lGaQCbMLRmmXTPtkKGY/OrgQ+2H7uFW4+/rGsKyaIJF3SZiz4so3pNLo5nwlkrCAGExfWP5LruTILxGYyF/reRbVIBWFCkeW/BEEQHOSliiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIgoMIoiAIggb4/3oUScW+fhNwAAAAAElFTkSuQmCC"
                         alt="">
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
        <table class="reading-table">
            <thead>
            <tr>
                <th colspan="4" style="text-align:left;">Vigilance / O&M Panchanama Detail</th>
            </tr>
            <tr>
                <th>Description</th>
                <th class="right">Amount (₹)</th>
                <th>Description</th>
                <th class="right">Amount (₹)</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Vigilance assessment – 15-01-2024 (unauthorised load)</td>
                <td class="right">5,250.00</td>
                <td>O&M Panchanama – 10-03-2024 (meter inspection)</td>
                <td class="right">0.00</td>
            </tr>
            <tr>
                <td>Vigilance surcharge adjustment – 05-05-2024</td>
                <td class="right">1,100.00</td>
                <td>Reversal of vigilance charge – 20-08-2024</td>
                <td class="right">-2,000.00</td>
            </tr>
            </tbody>
        </table>


        <table class="reading-table" style="margin-top:3px;">
            <thead>
            <tr>
                <th colspan="4" style="text-align:left;">CCB Adjustment Detail</th>
            </tr>
            <tr>
                <th>Description</th>
                <th class="right">Amount (₹)</th>
                <th>Description</th>
                <th class="right">Amount (₹)</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>CCB adjustment – rounding difference (JAN-2025)</td>
                <td class="right">-15.00</td>
                <td>CCB adjustment – subsidy reconciliation (FEB-2025)</td>
                <td class="right">-120.50</td>
            </tr>
            <tr>
                <td>CCB adjustment – delayed payment charge waiver</td>
                <td class="right">-85.25</td>
                <td>CCB adjustment – arrear correction (APR-2025)</td>
                <td class="right">230.00</td>
            </tr>
            </tbody>
        </table>
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
