// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/18/2015 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;


@Test
public class TestDocBuilderCharts
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
        SystemPal.saveCulture();
        // JAVA-deleted SaveUICulture(): No analog in Java.
        SystemPal.setStandardCulture();
        // JAVA-deleted SetStandardUICulture(): No analog in Java.
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown()
    {
        SystemPal.restoreCulture();
        // JAVA-deleted RestoreUICulture(): No analog in Java.
    }

    @Test
    public void testInsertChartAll() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
            chart.getSeries().clear();
            handleChartTypeSpecifics(chartType, chart);
        }

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestInsertChartAll.docx");

        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        msAssert.areEqual(42, shapes.getCount());
    }

    @Test
    public void testInsertChartAllWithDefaultData() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));
        }

        TestUtil.saveCheckGoldExportOnly(doc, "DocBuilder\\Chart\\TestInsertChartAllWithDefaultData.docx");
    }

    @Test
    public void testInsertChartColumn() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        msAssert.isTrue(chart.getTitle().getShow());

        // Setting chart Title.
        chart.getTitle().setText("Sample Column Chart Title");

        ChartSeriesCollection seriesColl = chart.getSeries();

        // Check default generated series count.
        msAssert.areEqual(3, seriesColl.getCount());

        // Delete default generated series.
        seriesColl.clear();

        // Check Series count after clear.
        msAssert.areEqual(0, seriesColl.getCount());

        // Create category names array.
        String[] categories = new String[] {"AW Category 1", "AW Category 2"};

        // Adding new series
        seriesColl.add("AW Series 1", categories, new double[] { 1.0, 2.0 });
        seriesColl.add("AW Series 2", categories, new double[] { 3.0, 4.0 });
        seriesColl.add("AW Series 3", categories, new double[] { 5.0, 6.0 });
        seriesColl.add("AW Series 4", categories, new double[] { 7.0, 8.0 });
        seriesColl.add("AW Series 5", categories, new double[] { 9.0, 10.0 });

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestInsertChartColumn.docx");

        Shape s1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        // Check size is correct.
        msAssert.areEqual(432, 0.5, s1.getWidth());
        msAssert.areEqual(252, 0.5, s1.getHeight());

        chart = s1.getChart();

        seriesColl = chart.getSeries();

        // Check chart Title text.
        msAssert.areEqual("Sample Column Chart Title", chart.getTitle().getText());

        // Check Series count.
        msAssert.areEqual(5, seriesColl.getCount());

        // Get first chart series.
        ChartSeries firstChartSeries = seriesColl.get(0);

        // Check chart series name.
        msAssert.areEqual("AW Series 1", firstChartSeries.getName());

        // Get ChartDataPoint collection for the first series.
        ChartDataPointCollection dmlChartDataPointCollection = firstChartSeries.getDataPoints();
        msAssert.isFalse(dmlChartDataPointCollection.getMaterializedDataPoints().iterator().hasNext());

        // Get default ChartDataPoint for the first series.
        msAssert.areEqual(-1, firstChartSeries.getExplosion());
        msAssert.areEqual(0, firstChartSeries.getIndex());
        msAssert.isFalse(firstChartSeries.getInvertIfNegative());
        msAssert.areEqual(7, firstChartSeries.getMarker().getSize());
        msAssert.areEqual(MarkerSymbol.DEFAULT, firstChartSeries.getMarker().getSymbol());
    }

    @Test
    public void testShowAutoTitle() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        msAssert.isTrue(chart.getTitle().getShow());

        chart.getTitle().setShow(false);
        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestShowAutoTitle.docx");

        Shape s1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.isFalse(s1.getChart().getTitle().getShow());
    }

    @Test
    public void testPieChartExplosion() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape1 = builder.insertChart(ChartType.PIE, 432.0, 252.0);
        Chart shape1Chart = shape1.getChart();
        shape1Chart.getTitle().setText("Pie Chart Explosion");

        Shape shape2 = builder.insertChart(ChartType.PIE_3_D, 432.0, 252.0);
        Chart shape2Chart = shape2.getChart();
        shape2Chart.getTitle().setText("Pie3D Chart Explosion");

        // Delete default generated series.
        shape1Chart.getSeries().clear();
        shape2Chart.getSeries().clear();

        createPieChartData(shape1Chart);
        createPieChartData(shape2Chart);

        // Change default explosion.
        shape1Chart.getSeries().get(0).setExplosion(20);
        shape2Chart.getSeries().get(0).setExplosion(5);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestPieChartExplosion.docx");

        shape1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.areEqual(20, shape1.getChart().getSeries().get(0).getExplosion());

        shape2 = (Shape)doc.getChild(NodeType.SHAPE, 1, true);
        msAssert.areEqual(5, shape2.getChart().getSeries().get(0).getExplosion());
    }

    @Test
    public void testLineChartMarkers() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Line Chart With Markers");

        // Delete default generated series.
        chart.getSeries().clear();

        createStandardChartData(chart);

        // Change default marker size and symbol.
        ChartMarker marker = chart.getSeries().get(0).getMarker();
        marker.setSize(15);
        marker.setSymbol(MarkerSymbol.DIAMOND);

        marker = chart.getSeries().get(1).getMarker();
        marker.setSize(10);
        marker.setSymbol(MarkerSymbol.CIRCLE);

        marker = chart.getSeries().get(2).getMarker();
        marker.setSize(5);
        marker.setSymbol(MarkerSymbol.TRIANGLE);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestLineChartMarkers.docx");

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        marker = shape.getChart().getSeries().get(0).getMarker();
        msAssert.areEqual(15, marker.getSize());
        msAssert.areEqual(MarkerSymbol.DIAMOND, marker.getSymbol());

        marker = shape.getChart().getSeries().get(1).getMarker();
        msAssert.areEqual(10, marker.getSize());
        msAssert.areEqual(MarkerSymbol.CIRCLE, marker.getSymbol());

        marker = shape.getChart().getSeries().get(2).getMarker();
        msAssert.areEqual(5, marker.getSize());
        msAssert.areEqual(MarkerSymbol.TRIANGLE, marker.getSymbol());
    }

    @Test
    public void testChartDataPoints1() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Line Chart With Custom Data Points");

        // Delete default generated series.
        chart.getSeries().clear();

        createStandardChartData(chart);

        ChartSeries series0 = chart.getSeries().get(0);
        ChartSeries series1 = chart.getSeries().get(1);

        // Change first series name.
        series0.setName("NewSeries1Name");

        msAssert.isFalse(series0.getDataPoints().getMaterializedDataPoints().iterator().hasNext());
        msAssert.areEqual(4, series0.getDataPoints().getCount());

        // Get DataPoint of the first and second point of the first series.
        ChartDataPoint dataPoint00 = series0.getDataPoints().get(0);
        ChartDataPoint dataPoint01 = series0.getDataPoints().get(1);

        // Change some DataPoint props.
        dataPoint00.setExplosion(50);
        dataPoint00.getMarker().setSymbol(MarkerSymbol.CIRCLE);
        dataPoint00.getMarker().setSize(15);

        dataPoint01.getMarker().setSymbol(MarkerSymbol.DIAMOND);
        dataPoint01.getMarker().setSize(20);

        // Get DataPoint of the third point of the second series.
        ChartDataPoint dataPoint12 = series1.getDataPoints().get(2);
        dataPoint12.setInvertIfNegative(true);
        dataPoint12.getMarker().setSymbol(MarkerSymbol.STAR);
        dataPoint12.getMarker().setSize(20);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartDataPoints1.docx");

        // Check everything were preserved.
        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        series0 = shape.getChart().getSeries().get(0);
        series1 = shape.getChart().getSeries().get(1);

        msAssert.areEqual(4, series0.getDataPoints().getCount());
        msAssert.areEqual(4, series1.getDataPoints().getCount());

        dataPoint00 = series0.getDataPoints().get(0);

        // There is no way to set explosion for line chart, that is why -1.
        msAssert.areEqual(-1, dataPoint00.getExplosion());
        msAssert.areEqual(MarkerSymbol.CIRCLE, dataPoint00.getMarker().getSymbol());
        msAssert.areEqual(15, dataPoint00.getMarker().getSize());

        dataPoint01 = series0.getDataPoints().get(1);
        msAssert.areEqual(-1, dataPoint01.getExplosion());
        msAssert.areEqual(MarkerSymbol.DIAMOND, dataPoint01.getMarker().getSymbol());
        msAssert.areEqual(20, dataPoint01.getMarker().getSize());

        dataPoint12 = series1.getDataPoints().get(2);
        msAssert.isTrue(dataPoint12.getInvertIfNegative());
    }

    @Test
    public void testLineChartSmooth() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        msAssert.isTrue(shape.hasChart());
        Chart chart = shape.getChart();
        chart.getTitle().setText("Line Chart Smooth");

        // Delete default generated series.
        chart.getSeries().clear();

        createStandardChartData(chart);

        ChartSeries series0 = chart.getSeries().get(0);
        msAssert.isFalse(series0.getSmooth());
        series0.setSmooth(true);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestLineChartSmooth.docx");

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        series0 = shape.getChart().getSeries().get(0);
        msAssert.isTrue(series0.getSmooth());
    }

    @Test
    public void testChartDataLabels() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Line Chart With Data Labels");

        // Delete default generated series.
        chart.getSeries().clear();

        createStandardChartData(chart);

        ChartSeries series0 = chart.getSeries().get(0);

        // Count returns 0 since data labels are not displayed.
        msAssert.areEqual(0, series0.getDataLabels().getCount());

        series0.hasDataLabels(true);
        msAssert.areEqual(4, series0.getDataLabels().getCount());

        // Get data labels of the first and second point of the first series.
        ChartDataLabel chartDataLabel00 = series0.getDataLabels().get(0);
        ChartDataLabel chartDataLabel01 = series0.getDataLabels().get(1);

        // Set some properties.
        chartDataLabel00.setShowLegendKey(true);
        chartDataLabel00.setShowLeaderLines(true);
        chartDataLabel00.setShowCategoryName(true);
        chartDataLabel00.setShowPercentage(true);
        chartDataLabel00.setShowSeriesName(true);
        chartDataLabel00.setShowValue(true);
        chartDataLabel00.setSeparator("/");

        chartDataLabel01.setShowValue(true);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartDataLabels.docx");

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        series0 = shape.getChart().getSeries().get(0);

        msAssert.areEqual(4, series0.getDataLabels().getCount());

        chartDataLabel00 = series0.getDataLabels().get(0);
        msAssert.isTrue(chartDataLabel00.isVisible());
        msAssert.isTrue(chartDataLabel00.getShowLegendKey());
        msAssert.isTrue(chartDataLabel00.getShowLeaderLines());
        msAssert.isTrue(chartDataLabel00.getShowCategoryName());
        msAssert.isTrue(chartDataLabel00.getShowPercentage());
        msAssert.isTrue(chartDataLabel00.getShowSeriesName());
        msAssert.isTrue(chartDataLabel00.getShowValue());
        msAssert.areEqual("/", chartDataLabel00.getSeparator());

        chartDataLabel01 = series0.getDataLabels().get(1);
        msAssert.isTrue(chartDataLabel01.getShowValue());
    }

    @Test
    public void testChartDataLegend() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Line Chart With Legend on/off");

        msAssert.isFalse(chart.getLegend().getOverlay());
        msAssert.areEqual(LegendPosition.BOTTOM, chart.getLegend().getPosition());

        chart.getLegend().setPosition(LegendPosition.LEFT);
        chart.getLegend().setOverlay(true);

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartDataLegend.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        chart = shape.getChart();

        msAssert.isTrue(chart.getLegend().getOverlay());
        msAssert.areEqual(LegendPosition.LEFT, chart.getLegend().getPosition());
    }

    @Test
    public void testChartTitleFont() throws Exception
    {
        // Default Chart Title depends on the culture.
        String currentCulture = SystemPal.getCurrentCultureName();
        SystemPal.setCulture("en-US");

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        msAssert.areEqual("Chart Title", shape.getChart().getTitle().getText());

        Chart chart = shape.getChart();
        Font cFont = chart.getTitle().getFont();

        // Check defaults.
        msAssert.areEqual(14, cFont.getSize());
        msAssert.areEqual(12, cFont.getKerning());
        msAssert.areEqual(0, cFont.getSpacing());
        msAssert.areEqual("Calibri", cFont.getName());
        msAssert.areEqual("Calibri", cFont.getNameAscii());
        msAssert.areEqual("Times New Roman", cFont.getNameBi());
        msAssert.areEqual("Times New Roman", cFont.getNameFarEast());
        msAssert.areEqual("Times New Roman", cFont.getNameOther());
        msAssert.isFalse(cFont.getBold());
        msAssert.isFalse(cFont.getItalic());
        msAssert.isFalse(cFont.getStrikeThrough());
        msAssert.isFalse(cFont.getDoubleStrikeThrough());
        msAssert.areEqual(/*unchecked*/((int)(int) 0xFF595959L), cFont.getColor().getRGB());
        msAssert.areEqual(Underline.NONE, cFont.getUnderline());

        // Setting chart Title.
        cFont.setSize(10.0);
        cFont.setKerning(11.0);
        cFont.setSpacing(12.0);
        cFont.setName("Arial");
        cFont.setBold(true);
        cFont.setItalic(true);
        cFont.setStrikeThrough(false);
        cFont.setDoubleStrikeThrough(true);
        cFont.setAllCaps(false);
        cFont.setSmallCaps(true);
        cFont.setColor(Color.RED);
        cFont.setUnderline(Underline.DOUBLE);
        chart.getTitle().setText("Sample Column Chart Title");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartTitleFont.docx");

        shape = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        chart = shape.getChart();
        cFont = chart.getTitle().getFont();

        msAssert.areEqual(10, cFont.getSize());
        msAssert.areEqual(11, cFont.getKerning());
        msAssert.areEqual(12, cFont.getSpacing());
        msAssert.areEqual("Arial", cFont.getName());
        msAssert.areEqual("Arial", cFont.getNameAscii());
        msAssert.areEqual("Arial", cFont.getNameBi());
        msAssert.areEqual("Arial", cFont.getNameFarEast());
        msAssert.areEqual("Arial", cFont.getNameOther());
        msAssert.isTrue(cFont.getBold());
        msAssert.isTrue(cFont.getItalic());
        msAssert.isFalse(cFont.getStrikeThrough());
        msAssert.isTrue(cFont.getDoubleStrikeThrough());
        msAssert.isFalse(cFont.getAllCaps());
        msAssert.isTrue(cFont.getSmallCaps());
        msAssert.areEqual(Color.RED.getRGB(), cFont.getColor().getRGB());
        msAssert.areEqual(Underline.DOUBLE, cFont.getUnderline());

        SystemPal.setCulture(currentCulture);

        chart.getTitle().setText(null);
        cFont.setSize(12.0);

        chart.getTitle().setText("123");

        msAssert.areEqual(12, cFont.getSize());
        msAssert.areEqual(11, cFont.getKerning());
        msAssert.areEqual(Color.RED.getRGB(), cFont.getColor().getRGB());
    }


    @Test
    public void testChartImport() throws Exception
    {
        Document dstDoc = new Document();
        Document srcDoc = new Document();
        DocumentBuilder builder = new DocumentBuilder(srcDoc);

        // Add chart with default data.
        builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        NodeImporter importer = new NodeImporter(srcDoc, dstDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        for (Node node : (Iterable<Node>) srcDoc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false))
        {
            dstDoc.getLastSection().getBody().appendChild(importer.importNode(node, true));
        }

        Node[] shapes = dstDoc.getChildNodes(NodeType.SHAPE, true).toArray();
        msAssert.areEqual(1, shapes.length);
    }

    @Test
    public void testChartClone() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("TestName");
        DmlChartSpace space = (DmlChartSpace)shape.getDmlNode();
        DmlChartFormat format = space.getChartFormat();
        DmlChartPlotArea area = format.getPlotArea();
        ArrayList<DmlChart> charts = area.getCharts();
        DmlChart dmlChart = charts.get(0);

        Shape shapeClone = (Shape)shape.deepClone(true);
        Chart chartClone = shapeClone.getChart();
        DmlChartSpace spaceClone = (DmlChartSpace)shapeClone.getDmlNode();
        DmlChartFormat formatClone = spaceClone.getChartFormat();
        DmlChartPlotArea areaClone = formatClone.getPlotArea();
        ArrayList<DmlChart> chartsClone = areaClone.getCharts();
        DmlChart dmlChartClone = chartsClone.get(0);

        msAssert.areNotSame(space, spaceClone);
        msAssert.areNotSame(format, formatClone);
        msAssert.areNotSame(area, areaClone);
        msAssert.areNotSame(charts, chartsClone);
        msAssert.areNotSame(dmlChart, dmlChartClone);

        msAssert.areNotSame(chart, chartClone);
        msAssert.areNotSame(chart.getSeries(), chartClone.getSeries());

        ChartSeries series = chart.getSeries().get(0);
        ChartSeries seriesClone = chartClone.getSeries().get(0);

        msAssert.areNotSame(series, seriesClone);
        msAssert.areNotSame(series.getDefaultDataPoint(), seriesClone.getDefaultDataPoint());
        msAssert.areNotSame(series.getDataLabels(), seriesClone.getDataLabels());
        msAssert.areNotSame(chart.getTitle(), chartClone.getTitle());
        msAssert.areNotSame(chart.getTitle().getFont(), chartClone.getTitle().getFont());

        // Test DmlTextBody clone.
        msAssert.areEqual(chart.getTitle().getText(), chartClone.getTitle().getText());

        DmlParagraph paragraph = (DmlParagraph)(format.getDCTitle().getTx().getRichText().getParagraphs().get(0));
        DmlParagraph paragraphCloned = (DmlParagraph)(formatClone.getDCTitle().getTx().getRichText().getParagraphs().get(0));

        msAssert.areNotSame(paragraph, paragraphCloned);
    }

    @Test
    public void testChartAppendDocument() throws Exception
    {
        Document dstDoc = new Document();
        Document srcDoc = new Document();
        DocumentBuilder builder = new DocumentBuilder(srcDoc);

        // Add chart with default data.
        builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        TestUtil.saveOpen(dstDoc, "DocBuilder\\Chart\\TestChartAppendDocument.docx");
    }

    @Test
    public void testChartImportFormatMode() throws Exception
    {
        Document dstDoc = TestUtil.open("DocBuilder\\Chart\\ChartMsWord2013.docx");
        Document srcDoc = TestUtil.open("DocBuilder\\Chart\\ChartMsWord2007.docx");

        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);
        Shape shape = (Shape)dstDoc.getChild(NodeType.SHAPE, 0, true);

        // ThemeOverride is null, destination theme will be used.
        msAssert.isNull(((DmlChartSpace)shape.getDmlNode()).getThemeOverride());

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);
        shape = (Shape)dstDoc.getChild(NodeType.SHAPE, 1, true);
        msAssert.isNotNull(((DmlChartSpace)shape.getDmlNode()).getThemeOverride());
    }

    @Test
    public void testChartWithEmptySeriesName() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection seriesColl = chart.getSeries();

        // Get first chart series.
        ChartSeries firstChartSeries = seriesColl.get(0);

        // Set name as string.Empty.
        firstChartSeries.setName("");

        // In MS Word there is no way to set empty series name, that is why generate name as Series plus one based index.
        msAssert.areEqual("Series1", firstChartSeries.getName());
    }

    /// <summary>
    /// Adding single series vs adding multiple series.
    /// </summary>
    @Test
    public void testChartAddingSingleSeries() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        chart.getSeries().clear();
        msAssert.areEqual(0, chart.getSeries().getCount());

        // Create category names array.
        String[] categories = new String[] { "AW Category 1", "AW Category 2" };

        // Add just one single series.
        chart.getSeries().add("Single Series Name", categories, new double[] { 1.0, 2.0 });
        msAssert.areEqual(1, chart.getSeries().getCount());

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartAddingSingleSeries.docx");
        Shape s1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        chart = s1.getChart();

        // Check Series count.
        msAssert.areEqual(1, chart.getSeries().getCount());

        chart.getSeries().add("One More Series Name", categories, new double[] { 3.0, 4.0 });
        msAssert.areEqual(2, chart.getSeries().getCount());
    }

    /// <summary>
    /// Adding only default data markers vs. custom data markers.
    /// </summary>
    @Test
    public void testChartDataMarkers() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Get first chart series.
        ChartSeries firstChartSeries = chart.getSeries().get(0);

        // Get ChartDataPoint collection for the first series.
        ChartDataPointCollection dmlChartDataPointCollection = firstChartSeries.getDataPoints();
        msAssert.isFalse(dmlChartDataPointCollection.getMaterializedDataPoints().iterator().hasNext());

        // Check default ChartMarker symbol.
        msAssert.areEqual(MarkerSymbol.NONE, firstChartSeries.getMarker().getSymbol());

        // Change default data marker for this series.
        firstChartSeries.getMarker().setSize(12);
        firstChartSeries.getMarker().setSymbol(MarkerSymbol.STAR);

        // Gets DataPoint of the first and second point of the first series.
        ChartDataPoint dataPoint00 = firstChartSeries.getDataPoints().get(0);
        ChartDataPoint dataPoint01 = firstChartSeries.getDataPoints().get(1);

        // Add custom data marker for the first data point.
        dataPoint00.getMarker().setSymbol(MarkerSymbol.CIRCLE);
        dataPoint00.getMarker().setSize(15);

        // Add custom data marker for the second data point.
        dataPoint01.getMarker().setSymbol(MarkerSymbol.DIAMOND);
        dataPoint01.getMarker().setSize(20);

        TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartDataMarkers.docx");
    }

    /// <summary>
    /// Adding data labels.
    /// </summary>
    @Test
    public void testAddChartDataLabels() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getSeries().clear();

        // Create category names array.
        String[] categories = new String[] { "AW Category 1", "AW Category 2", "AW Category 3" };

        // Add just one single series.
        ChartSeries series0 = chart.getSeries().add("AW Series 1", categories, new double[] { 1.0, 7.0, 3.0 });

        // Count returns 0 since data labels are not displayed.
        msAssert.areEqual(0, series0.getDataLabels().getCount());

        series0.hasDataLabels(true);
        msAssert.areEqual(3, series0.getDataLabels().getCount());

        ChartDataLabel chartDataLabel00 = series0.getDataLabels().get(0);
        ChartDataLabel chartDataLabel01 = series0.getDataLabels().get(1);
        ChartDataLabel chartDataLabel02 = series0.getDataLabels().get(2);

        // Set some properties.
        chartDataLabel00.setShowLegendKey(true);
        chartDataLabel00.setShowValue(true);

        // Set some properties.
        chartDataLabel01.setShowValue(true);

        // Set some properties.
        chartDataLabel02.setShowCategoryName(true);
        chartDataLabel02.setShowPercentage(true);
        chartDataLabel02.setShowValue(true);
        chartDataLabel02.setSeparator("/");

        TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestAddChartDataLabels.docx");
    }

    /// <summary>
    /// Customizing title.
    /// </summary>
    @Test
    public void testChartCustomizingTitle() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        msAssert.isTrue(chart.getTitle().getShow());

        // Setting chart Title.
        chart.getTitle().setText("Sample Column Chart Title");
        chart.getTitle().setOverlay(true);

        // Add chart with default data.
        Shape shape1 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart1 = shape1.getChart();

        msAssert.isTrue(chart1.getTitle().getShow());

        // Setting chart Title.
        chart1.getTitle().setText("Sample Column Chart Title");
        chart1.getTitle().setOverlay(false);

        TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartCustomizingTitle.docx");
    }

    @Test
    public void testChartSeriesIndex() throws Exception
    {
        String currentCulture = SystemPal.getCurrentCultureName();
        SystemPal.setCulture("en-US");
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        msAssert.areEqual("Chart Title", shape.getChart().getTitle().getText());

        int i = 0;
        for (ChartSeries series : chart.getSeries())
            msAssert.areEqual(i++, series.getIndex());

        i = 0;
        for (ChartSeries series : chart.getSeries())
            msAssert.areEqual(i++, series.getOrder());

        SystemPal.setCulture(currentCulture);
    }

    @Test
    public void testChartSeriesName() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        chart.getSeries().get(0).setName("TestName");

        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestChartSeriesName.docx");
        Shape s1 = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        chart = s1.getChart();

        // Check Series name.
        msAssert.areEqual("TestName", chart.getSeries().get(0).getName());
        msAssert.areEqual("Series 2", chart.getSeries().get(1).getName());
    }

    /// <summary>
    /// WORDSNET-13428 Add feature to insert empty values in chart series.
    /// </summary>
    @Test
    public void testEmptyCategorAndDataValues() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection seriesColl = chart.getSeries();
        seriesColl.clear();

        // Create category names array.
        String[] categories = new String[] { "Cat1", null, "Cat3", "Cat4", "Cat5" };

        // Adding new series
        seriesColl.add("AW Series 1", categories, new double[] { 1.0, 2.0, Double.NaN, 4.0, 5.0 });
        seriesColl.add("AW Series 2", categories, new double[] { 2.0, 3.0, Double.NaN, 5.0, 6.0 });
        seriesColl.add("AW Series 3", categories, new double[] { Double.NaN, 4.0, 5.0, Double.NaN, Double.NaN });

        TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestEmptyCategorAndDataValues.docx");
    }


    /// <summary>
    /// WORDSNET-27204 InvalidOperationException is raised when saving document optimized for Word 2013 with added
    /// Word 2016 charts
    /// A VML fallback shape was generated for a Word 2016 chart shape that <see cref="DocxDocumentWriterBase"/>
    /// could not process because a DML fallback is expected.
    /// </summary>
    @Test
    public void test27204() throws Exception
    {
        Document doc = new Document();
        doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2013);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertChart(ChartType.WATERFALL, 432.0, 252.0);
        builder.insertChart(ChartType.FUNNEL, 432.0, 252.0);

        // Check that no exception occurs.
        doc = TestUtil.saveOpen(doc, "DocBuilder\\Chart\\Test27204.docx", null, false);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        // In the FOSS version there are no fallback shapes because there is no rendering code to create them.
    }

    /// <summary>
    /// WORDSNET-15521 Adding the data labels format in a chart.
    /// </summary>
    @Test
    public void testDataLabelsDifferentNumberFormat() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Add chart with default data.
        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getTitle().setText("Data Labels With Different Number Format");

        // Delete default generated series.
        chart.getSeries().clear();

        // Add new series
        ChartSeries series0 = chart.getSeries().add(
            "AW Series 1",
            new String[] { "AW1", "AW2", "AW3", "AW4" ,"AW5", "AW6", "AW7", "AW8" , "AW9", "AW10", "AW11", "AW12", "AW13"},
            new double[] {2.5, 1.5, 3.5, 2.5, 4.5, 3.5, 5.5, 4.5, 6.5, 5.5, 7.5, 6.5, 8.5});

        series0.hasDataLabels(true);
        ChartDataLabel chartDataLabel = series0.getDataLabels().get(0);

        // Check default values.
        msAssert.isNotNull(chartDataLabel.getNumberFormat());
        msAssert.isTrue(chartDataLabel.getNumberFormat().isLinkedToSource());
        msAssert.areEqual(chartDataLabel.getNumberFormat().getFormatCode(), "");

        setChartDataLabel(series0, 0, NUMBER_FORMAT_CODE);
        setChartDataLabel(series0, 1, CURRENCY_FORMAT_CODE);
        setChartDataLabel(series0, 2, TIME_FORMAT_CODE);
        setChartDataLabel(series0, 3, DATE_FORMAT_CODE);
        setChartDataLabel(series0, 4, PERCENTAGE_FORMAT_CODE);
        setChartDataLabel(series0, 5, FRACTION_FORMAT_CODE);
        setChartDataLabel(series0, 6, SCIENTIFIC_FORMAT_CODE);
        setChartDataLabel(series0, 7, TEXT_FORMAT_CODE);
        setChartDataLabel(series0, 8, ACCOUNTING_FORMAT_CODE);
        setChartDataLabel(series0, 9, CUSTOM_FORMAT_CODE);
        setChartDataLabel(series0, 10, ""); // Set empty string
        setChartDataLabel(series0, 11, null); // Set null

        chartDataLabel = series0.getDataLabels().get(12);

        chartDataLabel.setShowValue(true);
        chartDataLabel.getNumberFormat().isLinkedToSource(false);

        TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestDataLabelsDifferentNumberFormat.docx");

        series0 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        msAssert.areEqual(13, series0.getDataLabels().getCount());

        checkNumberFormat(series0, 0, NUMBER_FORMAT_CODE);
        checkNumberFormat(series0, 1, CURRENCY_FORMAT_CODE);
        checkNumberFormat(series0, 2, TIME_FORMAT_CODE);
        checkNumberFormat(series0, 3, DATE_FORMAT_CODE);
        checkNumberFormat(series0, 4, PERCENTAGE_FORMAT_CODE);
        checkNumberFormat(series0, 5, FRACTION_FORMAT_CODE);
        checkNumberFormat(series0, 6, SCIENTIFIC_FORMAT_CODE);
        checkNumberFormat(series0, 7, TEXT_FORMAT_CODE);
        checkNumberFormat(series0, 8, ACCOUNTING_FORMAT_CODE);
        checkNumberFormat(series0, 9, CUSTOM_FORMAT_CODE);
        checkNumberFormat(series0, 10, GENERAL_FORMAT_CODE);
        checkNumberFormat(series0, 11, GENERAL_FORMAT_CODE);
        checkNumberFormat(series0, 12, GENERAL_FORMAT_CODE);
    }

    /// <summary>
    /// WORDSNET-27030 Cannot add Word 2016 chart to Aspose.Words-generated document in MS Word even if the document
    /// has charts of the same type inserted using <see cref="DocumentBuilder"/>
    /// When using the InsertChart methods to add a Word 2016 chart, the document is now automatically optimized for
    /// Word 2013, which resolves the issue.
    /// </summary>
    @Test
    public void test27030() throws Exception
    {
        Document doc = new Document();
        TestWarningCallback warningCallback = new TestWarningCallback();
        doc.setWarningCallback(warningCallback);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertChart(ChartType.TREEMAP, 432.0, 252.0);

        msAssert.areEqual(MsWordVersionCore.WORD_2013, doc.getCompatibilityOptions().getMswVersion());
        msAssert.isTrue(warningCallback.contains(WarningSource.UNKNOWN, WarningType.MINOR_FORMATTING_LOSS,
                WarningStrings.OPTIMIZED_FOR_WORD_2013));
    }

    private static void setChartDataLabel(ChartSeries series, int lableIndex, String numberFormat)
    {
        ChartDataLabel chartDataLabel = series.getDataLabels().get(lableIndex);
        chartDataLabel.setShowValue(true);

        // Set new format code.
        chartDataLabel.getNumberFormat().setFormatCode(numberFormat);
    }

    private void checkNumberFormat(ChartSeries series, int lableIndex, String numberFormat)
    {
        ChartDataLabel dataLable = series.getDataLabels().get(lableIndex);

        msAssert.areEqual(dataLable.getNumberFormat().getFormatCode(), numberFormat);
        msAssert.isFalse(dataLable.getNumberFormat().isLinkedToSource());
    }

    private static final String NUMBER_FORMAT_CODE = "#,##0.00";
    private static final String CURRENCY_FORMAT_CODE = "\"$\"#,##0.00";
    private static final String TIME_FORMAT_CODE = "[$-x-systime]h:mm:ss AM/PM";
    private static final String DATE_FORMAT_CODE = "d/mm/yyyy";
    private static final String PERCENTAGE_FORMAT_CODE = "0.00%";
    private static final String FRACTION_FORMAT_CODE = "# ?/?";
    private static final String SCIENTIFIC_FORMAT_CODE = "0.00E+00";
    private static final String TEXT_FORMAT_CODE = "@";
    private static final String ACCOUNTING_FORMAT_CODE = "_-\"$\"* #,##0.00_-;-\"$\"* #,##0.00_-;_-\"$\"* \"-\"??_-;_-@_-";
    private static final String CUSTOM_FORMAT_CODE = "[Red]-#,##0.0";
    private static final String GENERAL_FORMAT_CODE = "General";

    /// <summary>
    /// WORDSNET-15521 Adding the data labels format in a chart.
    /// </summary>
    @Test
    public void testJira15521() throws Exception
    {
        Document doc = TestUtil.open("DocBuilder\\Chart\\TestJira15521.docx");

        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        Chart chart = ((Shape)shapes.get(0)).getChart();

        for (ChartDataLabel dataLabel : chart.getSeries().get(0).getDataLabels())
        {
            msAssert.isFalse(dataLabel.getNumberFormat().isLinkedToSource());

            dataLabel.getNumberFormat().isLinkedToSource(true);
            msAssert.isTrue(dataLabel.getNumberFormat().isLinkedToSource());
            msAssert.areEqual("", dataLabel.getNumberFormat().getFormatCode());
        }

        TestUtil.saveOpen(doc, "DocBuilder\\Chart\\TestJira15521.docx");
    }

    /// <summary>
    /// WORDSNET-15923 DocumentBuilder.InsertChart throws System.NullReferenceException
    /// When create chart with negative or zero dimensions, scale default size to parents paragraph/cell size
    /// </summary>
    @Test
    public void testJira15923() throws Exception
    {
        // Load document with DML Textbox and insert into it new chart with negative dimensions,
        // need to load from file because there is no way to crete it dynamically
        Document doc = TestUtil.open("DocBuilder\\Chart\\TestJira15923.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(1, 0);
        Shape shape = builder.insertChart(ChartType.AREA_3_D, -2, -3);
        Shape parentShape = (Shape)shape.getAncestor(NodeType.SHAPE);
        msAssert.less(0, shape.getWidth());
        msAssert.less(0, shape.getHeight());
        // One dimension must match parent shape, others scales to it
        msAssert.isTrue((shape.getWidth() == parentShape.getWidth()) || (shape.getHeight() == parentShape.getHeight()));

        // Check chart creation with zero/negative size
        builder.insertSection(SectionStart.NEW_PAGE);
        shape = builder.insertChart(ChartType.COLUMN, 0.0, 0.0);
        msAssert.less(0, shape.getWidth());
        msAssert.less(0, shape.getHeight());
        Shape newShape = builder.insertChart(ChartType.COLUMN, -1, -1);
        msAssert.areEqual(shape.getWidth(), newShape.getWidth());
        msAssert.areEqual(shape.getHeight(), newShape.getHeight());

        // Check creation with zero/negative dimensions in table
        Cell cell = builder.insertCell();
        // Set cell width to 100 points
        cell.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(100.0));
        shape = builder.insertChart(ChartType.COLUMN, 0.0, 0.0);
        msAssert.less(0, shape.getWidth());
        msAssert.less(0, shape.getHeight());

        // Set second cell width to 2*100 points
        cell = builder.insertCell();
        cell.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(200.0));
        newShape = builder.insertChart(ChartType.COLUMN, -100, -1);
        // Check that second shape is 2 times (+-10 percents) larger than first
        msAssert.isTrue((newShape.getWidth() * 0.9) < (shape.getWidth() * 2.0) && (shape.getWidth() * 2.0) < newShape.getWidth() * 1.1);
        msAssert.isTrue((newShape.getHeight() * 0.9) < (shape.getHeight() * 2.0) && (shape.getHeight() * 2.0) < newShape.getHeight() * 1.1);
        builder.endRow();
        builder.endTable();
    }

    /// <summary>
    /// Tests that when a parameter of the <see cref="ChartStyle"/> type is provided when inserting a chart,
    /// the style is applied to all chart elements.
    /// </summary>
    @Test (dataProvider = "testChartStyleDataProvider")
    public void testChartStyle(/*ChartStyle*/int chartStyle) throws Exception
    {
        // DocWithActualTheme.docx is used to be able to compare output chart with a chart created in MS Word.
        Document doc = TestUtil.open("DocBuilder\\Chart\\DocWithActualTheme.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0, chartStyle);
            msAssert.areEqual(chartStyle, shape.getChart().getStyle());
        }

        // The same file names are also used for the TestSettingStyle tests.
        String fileName = msString.format("DocBuilder\\Chart\\Test{0}ChartStyle.docx", ChartStyle.toString(chartStyle));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testChartStyleDataProvider")
	public static Object[][] testChartStyleDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartStyle.MUTED},
			{ChartStyle.SATURATED},
			{ChartStyle.SHADED},
			{ChartStyle.FLAT},
			{ChartStyle.SHADOWED},
			{ChartStyle.GRADIENT},
			{ChartStyle.ORIGINAL},
			{ChartStyle.TRANSPARENT_1},
			{ChartStyle.TRANSPARENT_2},
			{ChartStyle.OUTLINE},
			{ChartStyle.OUTLINE_BLACK},
			{ChartStyle.BLACK},
			{ChartStyle.GREY},
			{ChartStyle.BLUE},
			{ChartStyle.SHADED_PLOT},
		};
	}

    /// <summary>
    /// Tests that a style specified for a chart is applied to chart elements that are added/displayed on the chart.
    /// Only styles for which it is easy to identify that the style is being applied incorrectly or that there are
    /// possible issues with the style (for example, color of data labels for some label positions blends with
    /// the background) are included in the test.
    /// </summary>
    @Test (dataProvider = "testApplyingStyleToAddedChartElementsDataProvider")
    public void testApplyingStyleToAddedChartElements(/*ChartStyle*/int chartStyle) throws Exception
    {
        // DocWithActualTheme.docx is used to be able to compare output chart with a chart created in MS Word.
        Document doc = TestUtil.open("DocBuilder\\Chart\\DocWithActualTheme.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        ArrayList<Integer> chartTypes = new ArrayList<Integer>();
        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            builder.insertChart(chartType, 432.0, 252.0, chartStyle);
            msArrayList.add(chartTypes, chartType);
        }

        // Insert a combo chart.
        Shape comboChartShape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0, chartStyle);
        Chart comboChart = comboChartShape.getChart();
        ChartSeriesGroup group = comboChart.getSeriesGroups().add(ChartSeriesType.SCATTER);
        group.getSeries().add("Scatter", new double[] { 1.0, 2.0, 3.0 }, new double[] { 5.0, 10.0, 3.0 });
        msArrayList.add(chartTypes, ChartType.COLUMN);

        // Hide chart elements to clear their properties after resaving.
        for (Shape shape : (Iterable<Shape>) doc.getFirstSection().getBody().getShapes())
        {
            Chart chart = shape.getChart();
            chart.getTitle().setShow(false);
            chart.getLegend().setPosition(LegendPosition.NONE);
            for (ChartAxis axis : chart.getAxes())
                axis.setHidden(true);
        }

        String fileName = msString.format("DocBuilder\\Chart\\TestApplying{0}StyleToAddedChartElements.docx", ChartStyle.toString(chartStyle));
        doc = TestUtil.saveOpen(doc, fileName, null, false);

        for (int i = 0; i < chartTypes.size(); i++)
        {
            Shape shape = doc.getFirstSection().getBody().getShapes().get(i);
            Chart chart = shape.getChart();

            chart.getTitle().setShow(true);
            chart.getTitle().setText("Chart Title");
            chart.getLegend().setPosition(LegendPosition.BOTTOM);
            if (chart.getDataTable().isDataTableSupported())
                chart.getDataTable().setShow(true);

            for (ChartAxis axis : chart.getAxes())
            {
                axis.setHidden(false);
                axis.getTitle().setShow(true);
                axis.getTitle().setText(ChartAxisType.toString(axis.getType()));
            }

            chart.getSeries().clear();
            handleChartTypeSpecifics(chartTypes.get(i), chart);

            if (i == chartTypes.size() - 1)
            {
                // Combo chart.
                chart.getSeriesGroups().get(1).getSeries().add("Scatter", new double[] { 1.0, 2.0, 3.0 }, new double[] { 5.0, 10.0, 3.0 });
                chart.getSeriesGroups().add(ChartSeriesType.LINE);
                chart.getSeriesGroups().get(2).getSeries().add(
                    "Line",
                    new String[] { "AW Category 1", "AW Category 2", "AW Category 3", "AW Category 4" },
                    new double[] { 1.0, 3.0, 3.5, 2.0 });
            }

            for (ChartSeries series : chart.getSeries())
            {
                series.hasDataLabels(true);
                series.getDataLabels().setShowValue(false);
                series.getDataLabels().setShowCategoryName(false);
                series.getDataLabels().setShowSeriesName(false);
                series.getDataLabels().setShowLegendKey(false);
                if ((series.getSeriesType() == ChartSeriesType.TREEMAP) || (series.getSeriesType() == ChartSeriesType.SUNBURST))
                    series.getDataLabels().setShowCategoryName(true);
                else
                    series.getDataLabels().setShowValue(true);
            }
        }

        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testApplyingStyleToAddedChartElementsDataProvider")
	public static Object[][] testApplyingStyleToAddedChartElementsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartStyle.SATURATED},
			{ChartStyle.ORIGINAL},
			{ChartStyle.BLACK},
			{ChartStyle.GREY},
			{ChartStyle.BLUE},
		};
	}

    /// <summary>
    /// Tests that setting the property <see cref="Chart.Style"/> applies the style to all chart elements.
    /// </summary>
    @Test (dataProvider = "testSettingStyleDataProvider")
    public void testSettingStyle(/*ChartStyle*/int chartStyle) throws Exception
    {
        // DocWithActualTheme.docx is used to be able to compare output chart with a chart created in MS Word.
        Document doc = TestUtil.open("DocBuilder\\Chart\\DocWithActualTheme.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        for (/*ChartType*/int chartType : ChartType.getValues())
        {
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();
            chart.setStyle(chartStyle);
        }

        // The same file names are used for the TestChartStyle tests.
        String fileName = msString.format("DocBuilder\\Chart\\Test{0}ChartStyle.docx", ChartStyle.toString(chartStyle));
        TestUtil.saveCheckGoldExportOnly(doc, fileName);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSettingStyleDataProvider")
	public static Object[][] testSettingStyleDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ChartStyle.MUTED},
			{ChartStyle.SATURATED},
			{ChartStyle.SHADED},
			{ChartStyle.FLAT},
			{ChartStyle.SHADOWED},
			{ChartStyle.GRADIENT},
			{ChartStyle.ORIGINAL},
			{ChartStyle.TRANSPARENT_1},
			{ChartStyle.TRANSPARENT_2},
			{ChartStyle.OUTLINE},
			{ChartStyle.OUTLINE_BLACK},
			{ChartStyle.BLACK},
			{ChartStyle.GREY},
			{ChartStyle.BLUE},
			{ChartStyle.SHADED_PLOT},
		};
	}

    /// <summary>
    /// Tests that the <see cref="ChartStyleResolver.GetChartStyle"/> method returns the <see cref="ChartStyle.Normal"/>
    /// value for all unknown style IDs.
    /// </summary>
    @Test
    public void testChartStyleOfUnknownStyleId()
    {
        msAssert.areEqual(ChartStyle.NORMAL, ChartStyleResolver.getChartStyle(Integer.MAX_VALUE));
    }

    private static void handleChartTypeSpecifics(/*ChartType*/int chartType, Chart chart)
    {
        switch (chartType)
        {
            case ChartType.AREA:
            case ChartType.AREA_STACKED:
            case ChartType.AREA_PERCENT_STACKED:
            case ChartType.AREA_3_D:
            case ChartType.AREA_3_D_STACKED:
            case ChartType.AREA_3_D_PERCENT_STACKED:
            case ChartType.RADAR:
                createAreaChartData(chart);
                break;
            case ChartType.BAR:
            case ChartType.BAR_STACKED:
            case ChartType.BAR_PERCENT_STACKED:
            case ChartType.BAR_3_D:
            case ChartType.BAR_3_D_STACKED:
            case ChartType.BAR_3_D_PERCENT_STACKED:
            case ChartType.COLUMN:
            case ChartType.COLUMN_STACKED:
            case ChartType.COLUMN_PERCENT_STACKED:
            case ChartType.COLUMN_3_D:
            case ChartType.COLUMN_3_D_CLUSTERED:
            case ChartType.COLUMN_3_D_STACKED:
            case ChartType.COLUMN_3_D_PERCENT_STACKED:
            case ChartType.LINE:
            case ChartType.LINE_STACKED:
            case ChartType.LINE_PERCENT_STACKED:
            case ChartType.LINE_3_D:
            case ChartType.SURFACE:
            case ChartType.SURFACE_3_D:
                createStandardChartData(chart);
                break;
            case ChartType.BUBBLE:
            case ChartType.BUBBLE_3_D:
                createBubbleChartData(chart);
                break;
            case ChartType.PIE:
            case ChartType.PIE_OF_BAR:
            case ChartType.PIE_OF_PIE:
            case ChartType.PIE_3_D:
            case ChartType.DOUGHNUT:
                createPieChartData(chart);
                break;
            case ChartType.SCATTER:
                createScatterChartData(chart);
                break;
            case ChartType.STOCK:
                createStockChartData(chart);
                break;
            case ChartType.TREEMAP:
            case ChartType.SUNBURST:
                createMultilevelChartData(chart);
                break;
            case ChartType.HISTOGRAM:
                createHistogramChartData(chart);
                break;
            case ChartType.PARETO:
                createParetoChartData(chart);
                break;
            case ChartType.BOX_AND_WHISKER:
                createBoxAndWhiskerChartData(chart);
                break;
            case ChartType.WATERFALL:
                createWaterfallChartData(chart);
                break;
            case ChartType.FUNNEL:
                createFunnelChartData(chart);
                break;
            default:
                break;
        }
    }

    private static void createStandardChartData(Chart chart)
    {
        // Create category names array
        String[] categories = new String[] { "AW Category 1", "AW Category 2", "AW Category 3", "AW Category 4" };

        // Adding new series
        chart.getSeries().add("AW Series 1", categories, new double[] { 4.3, 2.5, 3.5, 4.5 });
        chart.getSeries().add("AW Series 2", categories, new double[] { 2.4, 4.4, 1.8, 2.8 });
        chart.getSeries().add("AW Series 3", categories, new double[] { 2.0, 2.0, 3.0, 5.0 });
    }

    private static void createStockChartData(Chart chart)
    {
        // Adding new series, java.util.Date for Java to check public api change.
        chart.getSeries().add("AW High", gDateTimes, new double[] { 55.0, 57.0, 57.0, 58.0, 58.0 });
        chart.getSeries().add("AW Low", gDateTimes, new double[] { 11.0, 12.0, 13.0, 11.0, 35.0 });
        ChartSeries closeSeries = chart.getSeries().add("AW Close", gDateTimes, new double[] { 32.0, 35.0, 34.0, 35.0, 43.0 });
        // Set the marker symbol to MarkerSymbol.Default to show the marker and apply its style. MarkerSymbol.Default
        // is the default value of the property, thus, if it is not changed during applying style, remove the
        // corresponding attribute to not have troubles with export/import gold.
        closeSeries.getMarker().setSymbol(MarkerSymbol.DEFAULT);
        if (closeSeries.getMarker().getSymbol() == MarkerSymbol.DEFAULT)
            closeSeries.getMarker().getMarkerPr().removeProperty(DmlChartMarkerAttr.SYMBOL);
    }

    private static void createScatterChartData(Chart chart)
    {
        // Adding new series
        chart.getSeries().add("AW Series 1", new double[] { 0.7, 1.8, 2.6 }, new double[] { 2.7, 3.2, 0.8 });
    }

    private static void createPieChartData(Chart chart)
    {
        // Create category names array
        String[] categoriesDoughnut = new String[] { "AW 1st Qtr", "AW 2nd Qtr", "AW 3rd Qtr", "AW 4th Qtr" };

        // Adding new series
        chart.getSeries().add("AW Sales", categoriesDoughnut, new double[] { 8.2, 3.2, 1.4, 1.2 });
    }

    private static void createBubbleChartData(Chart chart)
    {
        // Adding new series
        ChartSeries series = chart.getSeries().add(
            "AW Series 1",
            new double[] { 0.7, 1.8, 2.6 },
            new double[] { 2.7, 3.2, 0.8 },
            new double[] { 10.0, 4.0, 8.0 });

        series.setBubble3D(((DmlBubbleChart)chart.getChartSpace().getFirstChart()).getBubble3D());
    }

    private static void createAreaChartData(Chart chart)
    {
        // Adding new series, java.util.Date for Java to check public api change.
        chart.getSeries().add("AW Series 1", gDateTimes, new double[] { 32.0, 32.0, 28.0, 12.0, 15.0 });
        chart.getSeries().add("AW Series 2", gDateTimes, new double[] { 12.0, 12.0, 12.0, 21.0, 28.0 });
    }

    /// <summary>
    /// Creates chart data for the <see cref="ChartType.Treemap"/> and <see cref="ChartType.Sunburst"/> chart types
    /// that have multi-level categories.
    /// </summary>
    private static void createMultilevelChartData(Chart chart)
    {
        ChartMultilevelValue[] xValues = new ChartMultilevelValue[]
        {
            new ChartMultilevelValue("Asia", "Japan", "Tokyo"),
            new ChartMultilevelValue("Asia", "Japan", "Osaka"),
            new ChartMultilevelValue("Asia", "India", "Delhi"),
            new ChartMultilevelValue("Asia", "India", "Mumbai"),
            new ChartMultilevelValue("Asia", "China", "Shanghai"),
            new ChartMultilevelValue("Asia", "China", "Beijing"),
            new ChartMultilevelValue("Asia", "Bangladesh", "Dhaka"),
            new ChartMultilevelValue("Asia", "Pakistan", "Karachi"),
            new ChartMultilevelValue("America", "Brazil", "São Paulo"),
            new ChartMultilevelValue("America", "Mexico", "Mexico City"),
            new ChartMultilevelValue("America", "United States", "New York-Newark"),
            new ChartMultilevelValue("Africa", "Egypt", "Cairo")
        };

        double[] yValues = new double[]
        {
            37468000.0, 19281000.0, 28514000.0, 19980000.0, 25582000.0, 19618000.0,
            19578000.0, 15400000.0, 21650000.0, 21581000.0, 18819000.0, 20076000.0
        };

        ChartSeries series = chart.getSeries().add("Largest Cities", xValues, yValues);
        series.hasDataLabels(true);
        series.getDataLabels().setShowCategoryName(true);
        series.getDataLabels().setShowValue(true);
    }

    /// <summary>
    /// Creates data for a Histogram chart.
    /// </summary>
    private static void createHistogramChartData(Chart chart)
    {
        chart.getSeries().add(
            "AW Series 1",
            new double[]
            {
                -5, -4.5, -4, -3.9, -3.3, -3.3, -3, -2.9, -2.8, -1.9, -1.8, -1, -0.8, -0.7, 0.0, 0.5, 1.1, 1.3,
                1.9, 2.6
            });
        chart.getSeries().add(
            "AW Series 2",
            new double[]
            {
                -6, -5, -4, -4, -3, -3, -3, -2, -2, -1, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0, 3.0, 3.0, 4.0
            });

        chart.getLegend().setPosition(LegendPosition.BOTTOM);
    }

    /// <summary>
    /// Creates data for a Pareto chart.
    /// </summary>
    private static void createParetoChartData(Chart chart)
    {
        chart.getSeries().add(
            "Sales",
            new String[]
            {
                "AW", "GD", "AW", "AW", "App", "AW", "GD", "App", "AW", "AW", "App", "AW", "GD", "AW", "AW"
            },
            new double[]
            {
                5.0, 1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 2.0, 1.0, 10.0, 1.0, 3.0, 1.0, 1.0, 4.0
            });
    }

    /// <summary>
    /// Creates data for a Box and Whisker chart.
    /// </summary>
    private static void createBoxAndWhiskerChartData(Chart chart)
    {
        chart.getSeries().add(
            "Points",
            new String[]
            {
                "WC", "WC", "WC", "WC", "WC", "WC",
                "NR", "NR", "NR", "NR", "NR", "NR",
                "NA", "NA", "NA", "NA", "NA", "NA"
            },
            new double[]
            {
                91.0, 80.0, 100.0, 77.0, 90.0, 104.0,
                114.0, 107.0, 110.0, 60.0, 79.0, 78.0,
                94.0, 93.0, 84.0, 71.0, 80.0, 103.0
            });
    }

    /// <summary>
    /// Creates data for a Waterfall chart.
    /// </summary>
    private static void createWaterfallChartData(Chart chart)
    {
        ChartSeries series = chart.getSeries().add(
            "New Zealand GDP",
            new String[] { "2018", "2019 growth", "2020 growth", "2020", "2021 growth", "2022 growth", "2022" },
            new double[] { 100.0, 0.57, -0.25, 100.32, 20.22, -2.92, 117.62 },
            new boolean[] { true, false, false, true, false, false, true });

        series.hasDataLabels(true);
    }

    /// <summary>
    /// Creates data for a Funnel chart.
    /// </summary>
    private static void createFunnelChartData(Chart chart)
    {
        createPieChartData(chart);
        chart.getSeries().get(0).hasDataLabels(true);
    }

    // Create DateTimes array, java.util.Date for Java to check public api change.
    private static java.util.Date[] gDateTimes = new java.util.Date[]
                    {
                        new java.util.GregorianCalendar(2002, 5-1, 1).getTime(),
                        new java.util.GregorianCalendar(2002, 6-1, 1).getTime(),
                        new java.util.GregorianCalendar(2002, 7-1, 1).getTime(),
                        new java.util.GregorianCalendar(2002, 8-1, 1).getTime(),
                        new java.util.GregorianCalendar(2002, 9-1, 1).getTime(),
                    };
}

