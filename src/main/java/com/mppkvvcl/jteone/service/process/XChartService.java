package com.mppkvvcl.jteone.service.process;

import com.mppkvvcl.jteone.utility.GlobalUtil;
import org.knowm.xchart.*;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class XChartService {
    private static final Logger log = LoggerFactory.getLogger(XChartService.class);

    public String getBarChartBase64Image(final List<String> xAxisLabels, final Map<String, List<BigDecimal>> seriesDataMap, final int width, final int height) {
        if (GlobalUtil.isEmpty(xAxisLabels) || seriesDataMap == null || seriesDataMap.isEmpty() || width == 0 || height == 0)
            return null;

        final CategoryChart chart = new CategoryChartBuilder().width(width).height(height)
                //.xAxisTitle("MONTH")//X Axis Label
                //.yAxisTitle("CONSUMPTION")//Y Axis Label
                //.title("READ HISTORY")// Chart Title
                .theme(Styler.ChartTheme.Matlab).build();

        final CategoryStyler styler = chart.getStyler();
        styler.setSeriesColors(new Color[]{new Color(235, 122, 82), new Color(255, 231, 146)});//Color Option
        styler.setPlotGridVerticalLinesVisible(false);
        styler.setOverlapped(false);
        styler.setAvailableSpaceFill(.8);
        styler.setLegendBorderColor(Color.WHITE);
        styler.setLegendPosition(Styler.LegendPosition.OutsideS);
        styler.setLegendLayout(Styler.LegendLayout.Horizontal);
        styler.setLabelsVisible(true);
        styler.setLabelsRotation(90);
        styler.setLabelsPosition(0.4);

        seriesDataMap.forEach((seriesName, yAxisData) -> {
            if (!GlobalUtil.isEmpty(yAxisData)) chart.addSeries(seriesName, xAxisLabels, yAxisData);
        });

        String base64String = null;
        try {
            final byte[] bitmapBytes = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
            base64String = ResourceService.PNG_HTML_BASE54_PREFIX + Base64.getEncoder().encodeToString(bitmapBytes);
        } catch (IOException exception) {
            log.error("Exception occurred while Creating bar Chart", exception);
        }
        return base64String;
    }

    public String getPieChartBase64Image(final Map<String, BigDecimal> dataMap, final int width, final int height) {
        if (dataMap == null || dataMap.isEmpty() || width == 0 || height == 0) return null;

        final PieChart chart = new PieChartBuilder().width(width).height(height)
                //.title("TOD Detail") //Chart Title
                .theme(Styler.ChartTheme.Matlab).build();

        final PieStyler styler = chart.getStyler();
        styler.setSeriesColors(new Color[]{new Color(131, 175, 112), new Color(198, 201, 106), new Color(230, 127, 131), new Color(248, 178, 103)});
        styler.setLegendBorderColor(Color.white);
        styler.setLabelType(PieStyler.LabelType.Value);
        styler.setChartBackgroundColor(Color.white);
        styler.setPlotBorderVisible(false);
        styler.setLegendPosition(Styler.LegendPosition.InsideSE);

        dataMap.forEach(chart::addSeries);

        String base64String = null;
        try {
            final byte[] bitmapBytes = BitmapEncoder.getBitmapBytes(chart, BitmapEncoder.BitmapFormat.PNG);
            base64String = ResourceService.PNG_HTML_BASE54_PREFIX + Base64.getEncoder().encodeToString(bitmapBytes);
        } catch (IOException exception) {
            log.error("Exception occurred while Creating pie Chart", exception);
        }
        return base64String;
    }
}

