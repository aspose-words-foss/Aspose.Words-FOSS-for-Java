// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import java.util.ArrayList;
import com.aspose.SystemPal;
import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests behavior of the <see cref="ChartDataLabel"/> and <see cref="ChartDataLabelCollection"/> classes.
/// </summary>
@Test
public class TestChartDataLabel
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }


    /// <summary>
    /// WORDSNET-19124 Tests label count if a collection contains a label with index larger than the last
    /// used index.
    /// </summary>
    @Test
    public void testDataLabelCountWithOutsideLabel() throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartSeries series = chart.getSeries().get(0);

        checkLabelCount(series, 0);

        series.hasDataLabels(true);
        checkLabelCount(series, 3);

        ChartDataLabel label = series.getDataLabels().get(5);
        checkLabelCount(series, 3);

        label.setShowCategoryName(true);
        // Now the label #5 has non-default value and is included in Count value.
        checkLabelCount(series, 4);
    }

    /// <summary>
    /// WORDSNET-19124 Tests that <see cref="ChartDataLabel.ShowDataLabelsRange"/> property is
    /// written/read correctly.
    /// </summary>
    @Test
    public void testShowDataLabelsRangeOfLabel() throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartSeries series = chart.getSeries().get(0);
        ChartDataLabelCollection labels = series.getDataLabels();

        series.hasDataLabels(true);
        labels.setShowValue(true);
        labels.get(1).setShowDataLabelsRange(true);

        series.getDataLabelsRangeData().getDataSource().setValueRef(new DmlChartValueRef(DmlChartValueType.STRING));
        series.getDataLabelsRangeData().getDataSource().getValueRef().getFormula().setValue("Sheet1!$A$2:$A$5");

        DmlChartValueCollection valueCollection = series.getDataLabelsRangeData().getValues();
        valueCollection.add(new DmlChartStrValue(0, "Text1"));
        valueCollection.add(new DmlChartStrValue(1, "Text2"));
        valueCollection.add(new DmlChartStrValue(2, "Text3"));
        valueCollection.setValueCount(3);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestShowDataLabelsRangeOfLabel.docx", null, true);

        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        series = chart.getSeries().get(0);
        labels = series.getDataLabels();

        msAssert.isTrue(labels.getShowValue());
        msAssert.isFalse(labels.getShowDataLabelsRange());

        for (ChartDataLabel label : labels)
        {
            boolean isLabel1 = label.getIndex() == 1;
            msAssert.areEqual(isLabel1, label.getShowDataLabelsRange());
            msAssert.areEqual(isLabel1, label.hasNonDefaultFormatting());
        }
    }

    /// <summary>
    /// WORDSNET-19124 Tests that <see cref="ChartDataLabelCollection.ShowDataLabelsRange"/> property is
    /// written/read correctly.
    /// </summary>
    @Test
    public void testShowDataLabelsRangeOfCollection() throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartSeries series = chart.getSeries().get(0);

        series.hasDataLabels(true);
        ChartDataLabelCollection labels = series.getDataLabels();
        labels.setShowValue(true);
        labels.setShowDataLabelsRange(true);
        msAssert.isTrue(labels.get(1).getShowDataLabelsRange());
        labels.get(1).setShowDataLabelsRange(false);

        series.getDataLabelsRangeData().getDataSource().setValueRef(new DmlChartValueRef(DmlChartValueType.STRING));
        series.getDataLabelsRangeData().getDataSource().getValueRef().getFormula().setValue("Sheet1!$A$2:$A$5");

        DmlChartValueCollection valueCollection = series.getDataLabelsRangeData().getValues();
        valueCollection.add(new DmlChartStrValue(0, "Text1"));
        valueCollection.add(new DmlChartStrValue(1, "Text2"));
        valueCollection.add(new DmlChartStrValue(2, "Text3"));
        valueCollection.setValueCount(3);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestShowDataLabelsRangeOfCollection.docx", null, true);

        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        series = chart.getSeries().get(0);
        labels = series.getDataLabels();

        msAssert.isTrue(labels.getShowValue());
        msAssert.isTrue(labels.getShowDataLabelsRange());

        for (ChartDataLabel label : labels)
        {
            boolean isLabel1 = label.getIndex() == 1;
            msAssert.areEqual(!isLabel1, label.getShowDataLabelsRange());
            msAssert.areEqual(isLabel1, label.hasNonDefaultFormatting());
        }
    }

    /// <summary>
    /// WORDSNET-19124 Tests the <see cref="ChartDataLabel.ClearFormat"/> public method.
    /// </summary>
    @Test
    public void testDataLabelClearFormat() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries series = chart.getSeries().get(0);
        series.hasDataLabels(true);

        ChartDataLabelCollection labels = chart.getSeries().get(0).getDataLabels();
        ChartDataLabel label = labels.get(1);

        labels.setShowValue(true);

        msAssert.isFalse(label.getShowDataLabelsRange());
        msAssert.isTrue(label.getShowValue());

        label.setShowDataLabelsRange(true);
        label.setShowValue(false);

        msAssert.isTrue(label.getShowDataLabelsRange());
        msAssert.isFalse(label.getShowValue());

        label.clearFormat();

        msAssert.isFalse(label.getShowDataLabelsRange());
        msAssert.isTrue(label.getShowValue());
    }

    /// <summary>
    /// WORDSNET-19124 Tests the <see cref="ChartDataLabel.IsHidden"/> public property.
    /// </summary>
    @Test
    public void testHidingChartDataLabel() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries series = chart.getSeries().get(0);

        msAssert.isFalse(series.hasDataLabels());

        series.hasDataLabels(true);

        ChartDataLabel label1 = series.getDataLabels().get(1);
        ChartDataLabel label2 = series.getDataLabels().get(2);
        msAssert.isFalse(label1.isHidden());

        label1.isHidden(true);
        label2.isHidden(false);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDeletingChartDataLabel.docx", null, false);

        shape = doc.getFirstSection().getBody().getShapes().get(0);
        chart = shape.getChart();
        series = chart.getSeries().get(0);

        msAssert.isTrue(series.hasDataLabels());

        label1 = series.getDataLabels().get(1);
        label2 = series.getDataLabels().get(2);

        msAssert.isTrue(label1.isHidden());
        msAssert.isFalse(label2.isHidden());
    }

    /// <summary>
    /// WORDSNET-20203 Implemented writing of ShowXXX and DLblPos property values even if a property has
    /// the same value as in the parent collection. It seems MS Word does not take parent values into account on
    /// displaying.
    /// </summary>
    @Test
    public void test20203() throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        ChartSeries series = chart.getSeries().get(0);

        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.D_LBL_POS, ChartDataLabelPosition.ABOVE);

        ChartDataLabel label = series.getDataLabels().get(1);
        label.setShowCategoryName(true);
        label.setPosition(ChartDataLabelPosition.BELOW);
        msAssert.isTrue(label.getShowValue()); // Inherited value is retrieved.

        // It is expected that value and category name are displayed in the second data label.
        TestUtil.save(doc, "Model\\Charts\\Test20203.docx", null, true, GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// WORDSNET-21286 Implemented writing ShowXXX property values of data label collection even if
    /// the value of the property is not defined, since default values in AW and MS Word are different on document
    /// versions higher than 12 (Word2007). Fixed issues with data label number format.
    /// </summary>
    @Test (dataProvider = "test21286DataProvider")
    public void test21286(int docVersion) throws Exception
    {
        Document doc = createDocumentWithChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        doc.getBuiltInDocumentProperties().setVersion(docVersion << 16);

        ChartSeries series = chart.getSeries().get(0);

        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().getNumberFormat().setFormatCode("#,##0.00");

        series.getDataLabels().get(1).setShowCategoryName(true);
        series.getDataLabels().get(1).setShowValue(true);
        series.getDataLabels().get(1).getNumberFormat().setFormatCode("#,##0.000");

        series.getDataLabels().get(2).getNumberFormat().isLinkedToSource(true);

        msAssert.areEqual("#,##0.00", series.getDataLabels().getNumberFormat().getFormatCode());
        msAssert.areEqual("#,##0.00", series.getDataLabels().get(0).getNumberFormat().getFormatCode());
        msAssert.areEqual("#,##0.000", series.getDataLabels().get(1).getNumberFormat().getFormatCode());
        msAssert.isFalse(series.getDataLabels().getNumberFormat().isLinkedToSource());
        msAssert.isTrue(series.getDataLabels().get(2).getNumberFormat().isLinkedToSource());

        String fileName = msString.format("Model\\Charts\\Test20203Version{0}.docx", docVersion);
        TestUtil.save(doc, fileName, null, true, GoldLevel.EXPORT_ONLY);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test21286DataProvider")
	public static Object[][] test21286DataProvider() throws Exception
	{
		return new Object[][]
		{
			{12},
			{16},
		};
	}

    /// <summary>
    /// WORDSNET-23582 Issue with How to Define Default Options for ChartDataLabels of ChartSeries sample
    /// Data labels were not visible in the output document.
    /// </summary>
    @Test
    public void test23582() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.PIE, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getSeries().clear();

        ChartSeries series = chart.getSeries().add("Series 1",
            new String[] { "Category1", "Category2", "Category3" },
            new double[] { 2.7, 3.2, 0.8 });

        msAssert.isFalse(series.hasDataLabels());

        ChartDataLabelCollection labels = series.getDataLabels();
        labels.setShowPercentage(true);
        labels.setShowValue(true);
        labels.setShowLeaderLines(false);
        labels.setSeparator(" - ");

        msAssert.isTrue(series.hasDataLabels());

        TestUtil.save(doc, "Model\\Charts\\Test23582.docx", null, true, GoldLevel.EXPORT_ONLY);
    }

    /// <summary>
    /// Tests the <see cref="ChartDataLabel.Font"/> property.
    /// </summary>
    @Test
    public void testDataLabelFont() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestDataLabelFont";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);

        // Test pre-Word 2016 chart.

        ChartSeries series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        ChartDataLabelCollection labels1 = series1.getDataLabels();

        msAssert.areEqual(6, labels1.getCount());

        // Label with index 0 has txPr element with Times New Roman italic font.
        Font font1 = labels1.get(0).getFont();
        // Label with index 1 has tx element with changed font size and bold state for the first run (field).
        Font font2 = labels1.get(1).getFont();
        // Label with index 2 has tx, txPr and spPr elements.
        Font font3 = labels1.get(2).getFont();
        // Non-materialized label.
        Font font4 = labels1.get(3).getFont();
        // Non-materialized label.
        Font font5 = labels1.get(4).getFont();
        // Label with index 5 has tx element with Verdana 8pt bold font.
        Font font6 = labels1.get(5).getFont();

        checkFont(font1, "Times New Roman", 12.0, new Color((0), (128), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        // Font is taken from the first run.
        checkFont(font2, "Verdana", 10.0, new Color((99), (123), (156)), msColor.Empty, true, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(font3, "Arial", 18.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(font4, "Verdana", 12.0, new Color((99), (123), (156)), msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(font5, "Verdana", 12.0, new Color((99), (123), (156)), msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(font6, "Verdana", 8.0, new Color((0), (0x80), (0)), msColor.Empty, true, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);

        changeFont(font1, "Courier New", 14.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
        font1.setHighlightColor(Color.CYAN);

        changeFont(font2, "Times New Roman", 12.0, msColor.getGreen(), false, true,
            Underline.SINGLE, 12, 4, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);
        changeFont(font3, "Courier New", 10.0, msColor.getDarkBlue(), true, true,
            Underline.DOTTED, 2, 2, false, true, true, false, RunVerticalAlignment.SUBSCRIPT, 1049);
        changeFont(font4, "Arial", 10.0, msColor.getBrown(), false, false,
            Underline.DASH, 5, 1, true, false, false, true, RunVerticalAlignment.SUPERSCRIPT, 1049);

        font5.setNameAscii("Arial");

        font6.clearFormatting();

        // Test Word 2016 chart.

        ChartSeries series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        ChartDataLabelCollection labels2 = series2.getDataLabels();

        msAssert.areEqual(8, labels2.getCount());

        // Label with index 0 has txPr element with Times New Roman italic 12pt font.
        font1 = labels2.get(0).getFont();
        // Non-materialized label.
        font2 = labels2.get(1).getFont();
        // Label with index 2 has txPr element with Calibri bold font.
        font3 = labels2.get(2).getFont();
        // Label with index 3 has spPr element only.
        font4 = labels2.get(3).getFont();

        checkFont(font1, "Times New Roman", 11.0, new Color((0), (128), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(font2, "Verdana", 12.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(font3, "Calibri", 10.0, new Color((89), (89), (89)), msColor.Empty, true, true,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(font4, "Verdana", 12.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);

        changeFont(font1, "Courier New", 14.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        font1.setHighlightColor(Color.CYAN);

        changeFont(font2, "Times New Roman", 9.0, msColor.getGreen(), false, true,
            Underline.SINGLE, 12, 2, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);

        font3.clearFormatting();

        font4.setNameAscii("Arial");

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);
        series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        labels1 = series1.getDataLabels();

        // Check results.

        msAssert.areEqual(6, labels1.getCount());

        checkFont(labels1.get(0).getFont(), "Courier New", 14.0, new Color((0x8b), (0), (0)), new Color((0), (0xff), (0xff)),
            true, false, Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
        checkFont(labels1.get(1).getFont(), "Times New Roman", 12.0, new Color((0), (0x80), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 12, 4, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);
        checkFont(labels1.get(2).getFont(), "Courier New", 10.0, new Color((0), (0), (0x8b)), msColor.Empty, true, true,
            Underline.DOTTED, 2, 2, false, true, true, false, RunVerticalAlignment.SUBSCRIPT, 1049);
        checkFont(labels1.get(3).getFont(), "Arial", 10.0, new Color((0xa5), (0x2a), (0x2a)), msColor.Empty, false, false,
            Underline.DASH, 5, 1, true, false, false, true, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(labels1.get(4).getFont(), "Arial", 12.0, new Color((99), (123), (156)), msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(labels1.get(5).getFont(), "Verdana", 12.0, new Color((99), (123), (156)), msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        labels2 = series2.getDataLabels();

        msAssert.areEqual(8, labels2.getCount());

        checkFont(labels2.get(0).getFont(), "Courier New", 14.0, new Color((0x8b), (0), (0)), new Color((0), (0xff), (0xff)),
            true, false, Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(labels2.get(1).getFont(), "Times New Roman", 9.0, new Color((0), (128), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 12, 2, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);
        checkFont(labels2.get(2).getFont(), "Verdana", 12.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels2.get(3).getFont(), "Arial", 12.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
    }

    /// <summary>
    /// Tests the <see cref="ChartDataLabelCollection.Font"/> property.
    /// </summary>
    @Test
    public void testDataLabelCollectionFont() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestDataLabelFont.docx");

        // Test pre-Word 2016 chart.

        ChartSeries series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        ChartDataLabelCollection labels1 = series1.getDataLabels();
        Font font1 = labels1.getFont();
        checkFont(font1, "Verdana", 12.0, new Color((99), (123), (156)), msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        changeFont(font1, "Courier New", 14.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
        font1.setHighlightColor(Color.CYAN);

        // Test Word 2016 chart.

        ChartSeries series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        ChartDataLabelCollection labels2 = series2.getDataLabels();
        Font font2 = labels2.getFont();

        checkFont(font2, "Verdana", 12.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);

        changeFont(font2, "Courier New", 14.0, msColor.getDarkRed(), true, false,
            Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        font2.setHighlightColor(Color.CYAN);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataLabelCollectionFont.docx",
            UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);

        // Check results.

        series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        labels1 = series1.getDataLabels();
        font1 = labels1.getFont();

        Color cyanColor = new Color((0), (0xff), (0xff));

        checkFont(font1, "Courier New", 14.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        checkFont(labels1.get(0).getFont(), "Times New Roman", 12.0, new Color((0), (128), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(labels1.get(1).getFont(), "Courier New", 10.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
        checkFont(labels1.get(2).getFont(), "Arial", 18.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels1.get(3).getFont(), "Courier New", 14.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
        checkFont(labels1.get(4).getFont(), "Courier New", 14.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
        checkFont(labels1.get(5).getFont(), "Courier New", 8.0, new Color((0), (0x80), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 3, true, false, true, false, RunVerticalAlignment.BASELINE, 1033);

        series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        labels2 = series2.getDataLabels();
        font2 = labels2.getFont();

        checkFont(font2, "Courier New", 14.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);

        checkFont(labels2.get(0).getFont(), "Times New Roman", 11.0, new Color((0), (128), (0)), cyanColor, false, true,
            Underline.SINGLE, 10, 1, false, false, true, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels2.get(1).getFont(), "Courier New", 14.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);
        checkFont(labels2.get(2).getFont(), "Calibri", 10.0, new Color((89), (89), (89)), cyanColor, true, true,
            Underline.NONE, 10, 1, false, false, true, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels2.get(3).getFont(), "Courier New", 14.0, new Color((0x8b), (0), (0)), cyanColor, true, false,
            Underline.DOUBLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1049);

        // Test ClearFormatting.
        // After clearing formatting chart space font options are used for pre-Word 2016 charts.

        font1.clearFormatting();
        font2.clearFormatting();

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataLabelCollectionFontClearFormatting.docx",
            UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);
        series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        labels1 = series1.getDataLabels();
        font1 = labels1.getFont();

        checkFont(font1, "Calibri", 9.0, msColor.Empty, msColor.Empty, false, false,
            Underline.SINGLE, 9, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        checkFont(labels1.get(0).getFont(), "Times New Roman", 12.0, new Color((0), (128), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(labels1.get(1).getFont(), "Calibri", 10.0, msColor.Empty, msColor.Empty, true, false,
            Underline.SINGLE, 9, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels1.get(2).getFont(), "Arial", 18.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels1.get(3).getFont(), "Calibri", 9.0, msColor.Empty, msColor.Empty, false, false,
            Underline.SINGLE, 9, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(labels1.get(4).getFont(), "Calibri", 9.0, msColor.Empty, msColor.Empty, false, false,
            Underline.SINGLE, 9, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(labels1.get(5).getFont(), "Calibri", 8.0, new Color((0), (0x80), (0)), msColor.Empty, true, false,
            Underline.SINGLE, 9, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);

        series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        labels2 = series2.getDataLabels();
        font2 = labels2.getFont();

        checkFont(font2, "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        checkFont(labels2.get(0).getFont(), "Times New Roman", 11.0, new Color((0), (128), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels2.get(1).getFont(), "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
        checkFont(labels2.get(2).getFont(), "Calibri", 10.0, new Color((89), (89), (89)), msColor.Empty, true, true,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1033);
        checkFont(labels2.get(3).getFont(), "Calibri", 9.0, new Color((89), (89), (89)), msColor.Empty, false, false,
            Underline.NONE, 0, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);
    }

    /// <summary>
    /// Tests the <see cref="ChartDataLabel.Font"/> property when creating a new chart.
    /// </summary>
    @Test
    public void testDataLabelFontInNewChart() throws Exception
    {
        // TODO: add testing of Word 2016 charts when they can be created in AW.

        Document doc = new Document();
        DocumentBuilder builder  = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        ChartSeries series = shape.getChart().getSeries().get(0);
        ChartDataLabelCollection labels = series.getDataLabels();
        series.hasDataLabels(true);
        labels.setShowValue(true);

        Font font = labels.getFont();
        Font font1 = labels.get(0).getFont();

        checkFont(font, "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        checkFont(font1, "Calibri", 10.0, msColor.Empty, msColor.Empty, false, false,
            Underline.NONE, 12, 0, false, false, false, false, RunVerticalAlignment.BASELINE, 1024);

        // Change font.

        changeFont(font, "Arial", 11.0, msColor.getDarkRed(), false, true,
            Underline.SINGLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        changeFont(font1, "Times New Roman", 14.0, Color.RED, true, false,
            Underline.DOUBLE, 0, 0, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);

        labels.get(1).getFont().setSize(9.0);

        // Check.

        checkFont(font, "Arial", 11.0, new Color((0x8b), (0), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        checkFont(font1, "Times New Roman", 14.0, new Color((0xff), (0), (0)), msColor.Empty, true, false,
            Underline.DOUBLE, 0, 0, false, true, false, true, RunVerticalAlignment.SUBSCRIPT, 1049);

        checkFont(labels.get(1).getFont(), "Arial", 9.0, new Color((0x8b), (0), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);

        checkFont(labels.get(2).getFont(), "Arial", 11.0, new Color((0x8b), (0), (0)), msColor.Empty, false, true,
            Underline.SINGLE, 10, 1, true, false, true, false, RunVerticalAlignment.SUPERSCRIPT, 1033);
    }

    /// <summary>
    /// WORDSNET-25711 Data label font size and color changes are not applied
    /// Item txPr element was not generated correctly when the corresponding element of the collection is empty.
    /// </summary>
    @Test
    public void test25711() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Check font size.
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        ChartSeries series = shape.getChart().getSeries().get(0);

        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().get(1).getFont().setSize(15.0);

        msAssert.areEqual(15, 0.01, series.getDataLabels().get(1).getFont().getSize());

        // Check font color.
        shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        series = shape.getChart().getSeries().get(0);

        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().get(1).getFont().setColor(Color.RED);

        msAssert.areEqual(new Color((0xff), (0), (0)), series.getDataLabels().get(1).getFont().getColor());

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\Test25711.docx");
    }

    /// <summary>
    /// Tests data label orientation and rotation in a new chart.
    /// </summary>
    @Test
    public void testDataLabelOrientationInNewChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape1 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        ChartSeries series1 = shape1.getChart().getSeries().get(0);
        ChartDataLabelCollection dataLabels1 = series1.getDataLabels();

        series1.hasDataLabels(true);
        dataLabels1.setShowValue(true);
        dataLabels1.setShowCategoryName(true);
        dataLabels1.getFormat().setShapeType(ChartShapeType.UP_ARROW);
        dataLabels1.getFormat().getStroke().getFill().solid(msColor.getDarkBlue());

        dataLabels1.setOrientation(ShapeTextOrientation.VERTICAL_FAR_EAST);
        dataLabels1.setRotation(-10);

        dataLabels1.get(1).setOrientation(ShapeTextOrientation.HORIZONTAL);
        dataLabels1.get(1).setRotation(45);

        dataLabels1.get(2).setOrientation(ShapeTextOrientation.WORD_ART_VERTICAL);

        dataLabels1.get(3).setRotation(5);

        msAssert.areEqual(ShapeTextOrientation.VERTICAL_FAR_EAST, dataLabels1.getOrientation());
        msAssert.areEqual(-10, dataLabels1.getRotation());
        checkOrientation(dataLabels1.get(0), ShapeTextOrientation.VERTICAL_FAR_EAST, -10);
        checkOrientation(dataLabels1.get(1), ShapeTextOrientation.HORIZONTAL, 45);
        checkOrientation(dataLabels1.get(2), ShapeTextOrientation.WORD_ART_VERTICAL, -10);
        checkOrientation(dataLabels1.get(3), ShapeTextOrientation.VERTICAL_FAR_EAST, 5);

        // Test setting properties of individual data labels without setting properties of the collection.

        Shape shape2 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        ChartSeries series2 = shape2.getChart().getSeries().get(0);
        ChartDataLabelCollection dataLabels2 = series2.getDataLabels();

        series2.hasDataLabels(true);
        dataLabels2.setShowValue(true);
        dataLabels2.setShowCategoryName(true);
        dataLabels2.getFormat().setShapeType(ChartShapeType.UP_ARROW);
        dataLabels2.getFormat().getStroke().getFill().solid(msColor.getDarkBlue());

        msAssert.areEqual(ShapeTextOrientation.HORIZONTAL, dataLabels2.getOrientation());
        msAssert.areEqual(0, dataLabels2.getRotation());

        dataLabels2.get(1).setOrientation(ShapeTextOrientation.DOWNWARD);
        dataLabels2.get(1).setRotation(10);

        dataLabels2.get(2).setOrientation(ShapeTextOrientation.UPWARD);

        dataLabels2.get(3).setRotation(15);

        msAssert.areEqual(ShapeTextOrientation.HORIZONTAL, dataLabels2.getOrientation());
        msAssert.areEqual(0, dataLabels2.getRotation());
        checkOrientation(dataLabels2.get(0), ShapeTextOrientation.HORIZONTAL, 0);
        checkOrientation(dataLabels2.get(1), ShapeTextOrientation.DOWNWARD, 10);
        checkOrientation(dataLabels2.get(2), ShapeTextOrientation.UPWARD, 0);
        checkOrientation(dataLabels2.get(3), ShapeTextOrientation.HORIZONTAL, 15);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestLabelOrientationInNewChart.docx", null, true);

        shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        series1 = shape1.getChart().getSeries().get(0);
        dataLabels1 = series1.getDataLabels();

        msAssert.areEqual(ShapeTextOrientation.VERTICAL_FAR_EAST, dataLabels1.getOrientation());
        msAssert.areEqual(-10, dataLabels1.getRotation());
        checkOrientation(dataLabels1.get(0), ShapeTextOrientation.VERTICAL_FAR_EAST, -10);
        checkOrientation(dataLabels1.get(1), ShapeTextOrientation.HORIZONTAL, 45);
        checkOrientation(dataLabels1.get(2), ShapeTextOrientation.WORD_ART_VERTICAL, -10);
        checkOrientation(dataLabels1.get(3), ShapeTextOrientation.VERTICAL_FAR_EAST, 5);

        shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        series2 = shape2.getChart().getSeries().get(0);
        dataLabels2 = series2.getDataLabels();

        msAssert.areEqual(ShapeTextOrientation.HORIZONTAL, dataLabels2.getOrientation());
        msAssert.areEqual(0, dataLabels2.getRotation());
        checkOrientation(dataLabels2.get(0), ShapeTextOrientation.HORIZONTAL, 0);
        checkOrientation(dataLabels2.get(1), ShapeTextOrientation.DOWNWARD, 10);
        checkOrientation(dataLabels2.get(2), ShapeTextOrientation.UPWARD, 0);
        checkOrientation(dataLabels2.get(3), ShapeTextOrientation.HORIZONTAL, 15);
    }

    /// <summary>
    /// Tests data label orientation and rotation in an existing chart.
    /// </summary>
    @Test
    public void testDataLabelOrientationInExistingChart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestDataLabelOrientation.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        ChartSeries series = shape.getChart().getSeries().get(0);
        ChartDataLabelCollection dataLabels = series.getDataLabels();

        msAssert.areEqual(ShapeTextOrientation.HORIZONTAL, dataLabels.getOrientation());
        msAssert.areEqual(0, dataLabels.getRotation());
        checkOrientation(dataLabels.get(0), ShapeTextOrientation.HORIZONTAL, 15);
        checkOrientation(dataLabels.get(1), ShapeTextOrientation.VERTICAL_FAR_EAST, 15);
        checkOrientation(dataLabels.get(2), ShapeTextOrientation.VERTICAL_ROTATED_FAR_EAST, 15);
        checkOrientation(dataLabels.get(3), ShapeTextOrientation.DOWNWARD, 15);
        checkOrientation(dataLabels.get(4), ShapeTextOrientation.UPWARD, 15);
        checkOrientation(dataLabels.get(5), ShapeTextOrientation.WORD_ART_VERTICAL, 15);
        checkOrientation(dataLabels.get(6), ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, 15);

        dataLabels.get(0).setOrientation(ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT);
        dataLabels.get(0).setRotation(0);

        dataLabels.get(1).setOrientation(ShapeTextOrientation.HORIZONTAL);

        dataLabels.get(2).setRotation(-15);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataLabelOrientation.docx", null, true);

        shape = doc.getFirstSection().getBody().getShapes().get(0);
        series = shape.getChart().getSeries().get(0);
        dataLabels = series.getDataLabels();

        checkOrientation(dataLabels.get(0), ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, 0);
        checkOrientation(dataLabels.get(1), ShapeTextOrientation.HORIZONTAL, 15);
        checkOrientation(dataLabels.get(2), ShapeTextOrientation.VERTICAL_ROTATED_FAR_EAST, -15);
    }

    /// <summary>
    /// Tests the data label position for all series types.
    /// </summary>
    @Test
    public void testPosition() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        ArrayList<Integer> supportedPositions = new ArrayList<Integer>();

        // Box&Whisker chart has many labels, let's set each position value to two labels.
        int[] boxWhiskerLabelStartIndexes = new int[] { 9, 20, 30 };
        final int BOX_WHISKER_NEXT_TEST_LABEL_OFFSET = 2;

        for (/*ChartSeriesType*/int seriesType : ChartSeriesType.getValues())
        {
            // These series types has no corresponding chart type; charts of these types cannot be created: skip them.
            if ((seriesType == ChartSeriesType.PARETO_LINE) || (seriesType == ChartSeriesType.REGION_MAP))
                continue;

            if (!fillSupportedPositions(seriesType, supportedPositions))
                continue;

            /*ChartType*/int chartType = DmlChartUtil.seriesTypeToChartType(seriesType);
            Shape shape = builder.insertChart(chartType, 432.0, 252.0);
            Chart chart = shape.getChart();

            chart.getTitle().setText(ChartType.toString(chartType));

            // Prepare necessary number of data labels.

            ChartSeriesCollection seriesCollection = chart.getSeries();
            if (seriesType != ChartSeriesType.STOCK)
            {
                while (seriesCollection.getCount() > 1)
                    seriesCollection.removeAt(seriesCollection.getCount() - 1);
            }

            ChartSeries series =  seriesCollection.get(0);
            if (seriesType != ChartSeriesType.HISTOGRAM)
            {
                while (series.getValueCount() < supportedPositions.size())
                {
                    ChartXValue xValue = getNewXValue(series);
                    ChartYValue yValue = ChartYValue.fromDouble(series.getValueCount());
                    if ((seriesType == ChartSeriesType.BUBBLE) || (seriesType == ChartSeriesType.BUBBLE_3_D))
                        series.add(xValue, yValue, series.getBubbleSizes().get(0));
                    else
                        series.add(xValue, yValue);
                }

                if (!chart.getChartSpace().isChartEx() && (seriesType != ChartSeriesType.PIE_OF_PIE) &&
                    (seriesType != ChartSeriesType.PIE_OF_BAR))
                {
                    while (series.getValueCount() > supportedPositions.size())
                        series.remove(series.getValueCount() - 1);
                }
            }

            // Show data labels and set their positions.

            series.hasDataLabels(true);
            ChartDataLabelCollection dataLabels = series.getDataLabels();
            dataLabels.setPosition(supportedPositions.get(0));
            if (seriesType == ChartSeriesType.BOX_AND_WHISKER)
            {
                for (int i = 1; i < supportedPositions.size(); i++)
                {
                    for (int j = 0; j <= 2; j += BOX_WHISKER_NEXT_TEST_LABEL_OFFSET)
                        dataLabels.get(boxWhiskerLabelStartIndexes[i - 1] + j).setPosition(supportedPositions.get(i));
                }
            }
            else
            {
                for (int i = 0; i < supportedPositions.size(); i++)
                {
                    ChartDataLabel label = dataLabels.get(i);

                    // Write position value as label text (it doesn't work for Word 2016 charts).
                    if (!chart.getChartSpace().isChartEx())
                    {
                        label.getLabelPr().setProperty(
                            DmlChartDataLabelAttrs.TX,
                            DmlChartRenderingUtil.createTx(ChartDataLabelPosition.toString(supportedPositions.get(i))));
                    }

                    // The first label gets value from the collection.
                    if (i > 0)
                        label.setPosition(supportedPositions.get(i));
                }
            }
        }

        doc = TestUtil.saveOpenDocxExportOnly(doc, "Model\\Charts\\TestDataLabelPosition.docx");

        // Check the positions after saving/opening.

        for (Shape shape : (Iterable<Shape>) doc.getFirstSection().getBody().getShapes())
        {
            ChartSeries series = shape.getChart().getSeries().get(0);
            fillSupportedPositions(series.getSeriesType(), supportedPositions);
            msAssert.isTrue(supportedPositions.size() > 0);

            ChartDataLabelCollection dataLabels = series.getDataLabels();
            msAssert.areEqual(supportedPositions.get(0), dataLabels.getPosition());
            if (series.getSeriesType() == ChartSeriesType.BOX_AND_WHISKER)
            {
                for (int i = 1; i < supportedPositions.size(); i++)
                {
                    int startIndex = boxWhiskerLabelStartIndexes[i - 1];
                    for (int j = 0; j <= 2; j += BOX_WHISKER_NEXT_TEST_LABEL_OFFSET)
                        msAssert.areEqual(supportedPositions.get(i), dataLabels.get(startIndex + j).getPosition());
                }
            }
            else
            {
                for (int i = 0; i < supportedPositions.size(); i++)
                    msAssert.areEqual(supportedPositions.get(i), dataLabels.get(i).getPosition());
            }
        }
    }

    /// <summary>
    /// Tests specifying location of a data label using absolute coordinates.
    /// </summary>
    @Test
    public void testLeftTopWithAbsoluteCoordinates() throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();

            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);

            // Test moving labels of a doughnut chart so that they are outside of the chart ring as some
            // customers requested.
            // Let's generate a lots of values, and place the labels around a large circle.

            // All constants are in points.
            final int WIDTH = 432;
            final int HEIGHT = 252;
            final int RING_R = 88;
            final int RING_CENTER_Y = 139;
            final int TITLE_AREA = 22;
            final int MARGIN_Y = 3;

            Shape shape = builder.insertChart(ChartType.DOUGHNUT, WIDTH, HEIGHT);
            Chart chart = shape.getChart();
            chart.getTitle().setText("Test moving labels");
            chart.getLegend().setPosition(LegendPosition.NONE);

            ChartSeries series = chart.getSeries().get(0);
            series.clearValues();
            double total = 0.0;
            for (double n = 5.0; n >= 1; n -= 0.2)
            {
                series.add(ChartXValue.fromString(msString.format("XYZ{0:F1}", n)), ChartYValue.fromDouble(n));
                total += n;
            }

            series.hasDataLabels(true);
            ChartDataLabelCollection dataLabels = series.getDataLabels();
            dataLabels.setShowCategoryName(true);
            dataLabels.setShowValue(false);
            dataLabels.setShowLeaderLines(true);

            double labelHeight = dataLabels.getFont().getSize() * 1.65;
            double labelWidth = dataLabels.getFont().getSize() * series.getXValues().get(0).getStringValue().length() * 0.5875;
            double newLabelRingR = RING_R + labelWidth;
            // These coordinates are calculated with a center located at the chart ring center and the Y-axis
            // pointing upward.
            double maxY = RING_CENTER_Y - (TITLE_AREA + MARGIN_Y + labelHeight / 2.0);
            double minY = RING_CENTER_Y - (HEIGHT - MARGIN_Y - labelHeight / 2.0);
            double totalAngle = 0.0;
            double previousX = 0.0;
            double previousY = 0.0;

            for (int i = 0; i < series.getValueCount(); i++)
            {
                ChartDataLabel dataLabel = dataLabels.get(i);
                if ((i > 0) && dataLabels.get(i - 1).isHidden())
                {
                    dataLabel.isHidden(true);
                    continue;
                }

                double angle = series.getYValues().get(i).getDoubleValue() / total * 2.0 * Math.PI;
                double labelAngle = angle / 2.0 + totalAngle;

                // These coordinates are calculated with a center located at the chart ring center and the Y-axis
                // pointing upward.
                double newLocationX = newLabelRingR * Math.sin(labelAngle);
                double newLocationY = newLabelRingR * Math.cos(labelAngle);
                if (newLocationY > maxY)
                    newLocationY = maxY;
                if (newLocationY < minY)
                    newLocationY = minY;
                if ((i > 0) && (Math.abs(newLocationY - previousY) < labelHeight))
                {
                    if (MathUtil.areEqual(newLocationY, minY))
                    {
                        if (newLocationX > previousX - labelWidth)
                            newLocationX = previousX - labelWidth;
                    }
                    else
                    {
                        newLocationY = previousY + ((labelAngle < Math.PI)
                            ? -labelHeight
                            : labelHeight);
                        if (newLocationY > maxY)
                        {
                            // Hide this and further data labels.
                            dataLabel.isHidden(true);
                            continue;
                        }
                    }
                }

                dataLabel.setLeft(newLocationX + WIDTH / 2d - labelWidth / 2.0);
                dataLabel.setLeftMode(ChartDataLabelLocationMode.ABSOLUTE);
                dataLabel.setTop(RING_CENTER_Y - newLocationY - labelHeight / 2.0);
                dataLabel.setTopMode(ChartDataLabelLocationMode.ABSOLUTE);

                totalAngle += angle;
                previousX = newLocationX;
                previousY = newLocationY;
            }

            TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestDataLabelLeftTopWithAbsoluteCoordinates.docx");
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Tests specifying location of a data label using coordinates relative to its position specified by the
    /// <see cref="ChartDataLabel.Position"/> property.
    /// </summary>
    @Test
    public void testLeftTopWithRelativeCoordinates() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        // Test moving data labels of a bar chart so that they are in front of bars.

        Shape shape = builder.insertChart(ChartType.BAR, 432.0, 252.0);
        Chart chart = shape.getChart();
        chart.getAxisX().setHidden(true);
        chart.getAxisY().getScaling().setMinimum(new AxisBound(-1));

        // Let's move the data labels so they are further apart vertically.
        final double VERTICAL_MOVE_PER_LABEL = 0.75;
        double topOffset = (chart.getSeries().getCount() - 1) / 2 * VERTICAL_MOVE_PER_LABEL;

        for (ChartSeries series : chart.getSeries())
        {
            series.hasDataLabels(true);
            ChartDataLabelCollection dataLabels = series.getDataLabels();
            dataLabels.setShowValue(true);
            dataLabels.setShowLeaderLines(false);
            dataLabels.setPosition(ChartDataLabelPosition.INSIDE_BASE);

            for (ChartDataLabel dataLabel : dataLabels)
            {
                dataLabel.setLeftMode(ChartDataLabelLocationMode.OFFSET);
                dataLabel.setLeft(-27);
                dataLabel.setTopMode(ChartDataLabelLocationMode.OFFSET);
                dataLabel.setTop(topOffset);
            }

            topOffset -= VERTICAL_MOVE_PER_LABEL;
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestDataLabelLeftTopWithRelativeCoordinates.docx");
    }

    /// <summary>
    /// Tests data label position in an existing chart.
    /// </summary>
    @Test
    public void testPositionInExistingChart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestDataLabelPosition.docx");
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        ChartDataLabelCollection dataLabels = chart.getSeries().get(0).getDataLabels();
        msAssert.areEqual(ChartDataLabelPosition.LEFT, dataLabels.getPosition());

        checkDataLabelPosition(dataLabels.get(0), ChartDataLabelPosition.LEFT,
            0.0, ChartDataLabelLocationMode.OFFSET, 0.0, ChartDataLabelLocationMode.OFFSET);
        checkDataLabelPosition(dataLabels.get(1), ChartDataLabelPosition.BELOW,
            0.0, ChartDataLabelLocationMode.OFFSET, 0.0, ChartDataLabelLocationMode.OFFSET);
        checkDataLabelPosition(dataLabels.get(2), ChartDataLabelPosition.ABOVE,
            0.0, ChartDataLabelLocationMode.OFFSET, 0.0, ChartDataLabelLocationMode.OFFSET);
        checkDataLabelPosition(dataLabels.get(3), ChartDataLabelPosition.RIGHT,
            15.0, ChartDataLabelLocationMode.OFFSET, 18.0, ChartDataLabelLocationMode.OFFSET);
        checkDataLabelPosition(dataLabels.get(4), ChartDataLabelPosition.RIGHT,
            -39.75, ChartDataLabelLocationMode.OFFSET, -15, ChartDataLabelLocationMode.OFFSET);
    }

    /// <summary>
    /// Tests specifying different location modes for left and top location of a data label.
    /// </summary>
    @Test
    public void testDifferentLocationModes() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        for (ChartSeries series : chart.getSeries())
        {
            series.hasDataLabels(true);
            ChartDataLabelCollection dataLabels = series.getDataLabels();
            dataLabels.setShowValue(true);
            dataLabels.setPosition(ChartDataLabelPosition.OUTSIDE_END);

            for (ChartDataLabel dataLabel : dataLabels)
            {
                dataLabel.setLeftMode(ChartDataLabelLocationMode.OFFSET);
                dataLabel.setLeft(1.5);
                dataLabel.setTopMode(ChartDataLabelLocationMode.ABSOLUTE);
                dataLabel.setTop(93.0);
            }
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestDataLabelDifferentLocationModes.docx");
    }

    /// <summary>
    /// Checks the position properties of the data label.
    /// </summary>
    private static void checkDataLabelPosition(ChartDataLabel dataLabel, /*ChartDataLabelPosition*/int expectedPosition,
        double expectedLeft, /*ChartDataLabelLocationMode*/int expectedLeftMode,
        double expectedTop, /*ChartDataLabelLocationMode*/int expectedTopMode)
    {
        msAssert.areEqual(expectedPosition, dataLabel.getPosition());
        msAssert.areEqual(expectedLeft, 0.15, dataLabel.getLeft());
        msAssert.areEqual(expectedLeftMode, dataLabel.getLeftMode());
        msAssert.areEqual(expectedTop, 0.15, dataLabel.getTop());
        msAssert.areEqual(expectedTopMode, dataLabel.getTopMode());
    }

    /// <summary>
    /// Gets a new X value to add to the specified chart series.
    /// </summary>
    private static ChartXValue getNewXValue(ChartSeries series)
    {
        int valueCount = series.getValueCount();
        Debug.assertTrue(valueCount > 1);

        switch (series.getXValues().get(0).getValueType())
        {
            case ChartXValueType.STRING:
                return ChartXValue.fromString("Category " + valueCount);
            case ChartXValueType.DOUBLE:
                double lastX = series.getXValues().get(valueCount - 1).getDoubleValue();
                return ChartXValue.fromDouble(lastX + (lastX - series.getXValues().get(valueCount - 2).getDoubleValue()));
            case ChartXValueType.MULTILEVEL:
                ChartMultilevelValue lastValue = series.getXValues().get(valueCount - 1).getMultilevelValue();
                return ChartXValue.fromMultilevelValue(
                    new ChartMultilevelValue(lastValue.getLevel1(), lastValue.getLevel2(), "Leaf " + valueCount));
            case ChartXValueType.DATE_TIME:
            case ChartXValueType.TIME:
            default:
                throw new IllegalStateException("Unexpected X value type.");
        }
    }

    /// <summary>
    /// Fills the list with supported data label positions for the specified chart series type. Returns a value
    /// indicating whether the series type allows setting data label position.
    /// </summary>
    private static boolean fillSupportedPositions(/*ChartSeriesType*/int seriesType, ArrayList<Integer> list)
    {
        list.clear();

        for (/*ChartDataLabelPosition*/int position : ChartDataLabelPosition.getValues())
        {
            if (DmlChartUtil.isDataLabelPositionSupported(seriesType, position))
                msArrayList.add(list, position);
        }

        return (list.size() > 0);
    }

    /// <summary>
    /// Checks the <see cref="ChartDataLabel.Orientation"/> and <see cref="ChartDataLabel.Rotation"/>
    /// properties of a data label.
    /// </summary>
    private static void checkOrientation(ChartDataLabel dataLabel, /*ShapeTextOrientation*/int expectedOrientation,
        int expectedRotation)
    {
        msAssert.areEqual(expectedOrientation, dataLabel.getOrientation());
        msAssert.areEqual(expectedRotation, dataLabel.getRotation());
    }

    /// <summary>
    /// Checks properties of a <see cref="Font"/> instance.
    /// </summary>
    private static void checkFont(Font font, String expectedFontName, double expectedFontSize, Color expectedColor,
        Color expectedHighlightColor, boolean expectedBold, boolean expectedItalic, /*Underline*/int expectedUnderline,
        int expectedKerning, int expectedSpacing, boolean expectedDoubleStrikeThrough, boolean expectedStrikeThrough,
        boolean expectedAllCaps, boolean expectedSmallCaps, /*RunVerticalAlignment*/int expectedVerticalAlignment,
        int expectedLocaleId)
    {
        TestChartUtil.checkFontProperties(font, expectedFontName, null, null, null, expectedFontSize,
            expectedKerning, expectedSpacing, expectedBold, expectedItalic, expectedStrikeThrough,
            expectedDoubleStrikeThrough, expectedAllCaps, expectedSmallCaps, expectedColor, expectedHighlightColor,
            expectedUnderline, expectedVerticalAlignment, expectedLocaleId);
    }

    /// <summary>
    /// Changes properties of a <see cref="Font"/> instance.
    /// </summary>
    private static void changeFont(Font font, String fontName, double fontSize, Color color, boolean bold,
        boolean italic, /*Underline*/int underline, int kerning, int spacing, boolean doubleStrikeThrough,
        boolean strikeThrough, boolean allCaps, boolean smallCaps, /*RunVerticalAlignment*/int verticalAlignment, int localeId)
    {
        TestChartUtil.setFontProperties(font, fontName, fontSize, kerning, spacing, bold, italic, strikeThrough,
            doubleStrikeThrough, allCaps, smallCaps, color, underline, verticalAlignment, localeId);
    }

    /// <summary>
    /// Checks label count of the series.
    /// </summary>
    private static void checkLabelCount(ChartSeries series, int expectedCount)
    {
        msAssert.areEqual(expectedCount, series.getDataLabels().getCount());

        // Check enumerator too.
        int count = 0;
        for (ChartDataLabel label : series.getDataLabels())
            count++;
        msAssert.areEqual(expectedCount, count);
    }

    /// <summary>
    /// Creates a document that contains one chart shape.
    /// </summary>
    private static Document createDocumentWithChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.LINE, 432.0, 252.0);
        Chart chart = shape.getChart();

        // Delete default generated series.
        chart.getSeries().clear();

        String[] categories = new String[] { "AW Category 1", "AW Category 2", "AW Category 3" };
        chart.getSeries().add("AW Series 1", categories, new double[] { 4.3, 2.5, 3.5 });

        return doc;
    }
}

