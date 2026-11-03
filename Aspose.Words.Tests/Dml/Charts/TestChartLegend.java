// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests members of the <see cref="ChartLegend"/> class.
/// </summary>
@Test
public class TestChartLegend
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
    /// WORDSNET-21265 /chart/ Add feature to show/hide items in the chart's legend.
    /// </summary>
    @Test
    public void test23210() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\Test23210.docx";
        Document doc = TestUtil.open(FILE_NAME);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartLegendEntryCollection legendEntries = chart.getLegend().getLegendEntries();
        ChartSeriesCollection series = chart.getSeries();

        msAssert.areEqual(8, legendEntries.getCount());

        msAssert.areSame(legendEntries.get(0), series.get(0).getLegendEntry());
        msAssert.areSame(legendEntries.get(1), series.get(1).getLegendEntry());
        msAssert.areSame(legendEntries.get(2), series.get(2).getLegendEntry());
        msAssert.areSame(legendEntries.get(3), series.get(3).getLegendEntry());

        msAssert.isFalse(legendEntries.get(0).isHidden());
        msAssert.isFalse(legendEntries.get(6).isHidden());

        legendEntries.get(0).isHidden(true);
        legendEntries.get(6).isHidden(true);

        doc = TestUtil.saveOpen(doc, FILE_NAME, null, false);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        legendEntries = chart.getLegend().getLegendEntries();

        msAssert.areEqual(8, chart.getLegend().getLegendEntries().getCount());
        msAssert.isTrue(legendEntries.get(0).isHidden());
        msAssert.isTrue(legendEntries.get(6).isHidden());
    }

    /// <summary>
    /// WORDSNET-23353 Legend entry not removed when deleting chart series
    /// </summary>
    @Test
    public void test23353() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\Test23210.docx";
        Document doc = TestUtil.open(FILE_NAME);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartLegendEntryCollection legendEntries = chart.getLegend().getLegendEntries();

        msAssert.areEqual(8, legendEntries.getCount());

        Font font0 = legendEntries.get(0).getFont();
        Font font2 = legendEntries.get(2).getFont();
        Font font7 = legendEntries.get(7).getFont();
        checkFont(font0, "Calibri", 8.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font2, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font7, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);

        chart.getSeries().removeAt(1);

        // The original document contained 4 series and 4 trendlines. One series and one trendline has been removed.
        msAssert.areEqual(6, legendEntries.getCount());

        font0 = legendEntries.get(0).getFont();
        Font font1 = legendEntries.get(1).getFont();
        Font font5 = legendEntries.get(5).getFont();
        checkFont(font0, "Calibri", 8.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font1, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font5, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);

        doc = TestUtil.saveOpen(doc, FILE_NAME, null, false);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        legendEntries = chart.getLegend().getLegendEntries();

        msAssert.areEqual(6, legendEntries.getCount());

        font0 = legendEntries.get(0).getFont();
        font1 = legendEntries.get(1).getFont();
        font5 = legendEntries.get(5).getFont();
        checkFont(font0, "Calibri", 8.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font1, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font5, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);

        chart.getSeries().removeAt(0);

        // One series and two trendlines has been removed.
        msAssert.areEqual(3, legendEntries.getCount());

        font0 = legendEntries.get(0).getFont();
        font2 = legendEntries.get(2).getFont();
        checkFont(font0, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font2, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);

        chart.getSeries().add(
            "Series 5",
            new String[] { "Category1", "Category2", "Category3", "Category4" },
            new double[] { 7.0, 6.0, 5.0, 4.0 });

        msAssert.areEqual(4, legendEntries.getCount());

        font0 = legendEntries.get(0).getFont();
        font2 = legendEntries.get(2).getFont(); // Font of the added series.
        Font font3 = legendEntries.get(3).getFont();
        checkFont(font0, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font2, "Calibri", 10.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font3, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);
    }

    /// <summary>
    /// Tests the <see cref="ChartLegendEntry.Font"/> property.
    /// </summary>
    @Test
    public void testLegendEntryFont() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\Test23210.docx";
        Document doc = TestUtil.open(FILE_NAME);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartLegendEntryCollection legendEntries = chart.getLegend().getLegendEntries();

        msAssert.areEqual(8, legendEntries.getCount());

        Font font0 = legendEntries.get(0).getFont();
        Font font2 = legendEntries.get(2).getFont();
        Font font7 = legendEntries.get(7).getFont();
        checkFont(font0, "Calibri", 8.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font2, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font7, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);

        msAssert.areEqual(12, font2.getKerning());
        msAssert.areEqual(0, font2.getSpacing());
        msAssert.areEqual("Calibri Light", font2.getNameBi());
        msAssert.areEqual(false, font2.getDoubleStrikeThrough());
        msAssert.areEqual(false, font2.getStrikeThrough());
        msAssert.areEqual(false, font2.getAllCaps());
        msAssert.areEqual(false, font2.getSmallCaps());
        msAssert.areEqual(RunVerticalAlignment.BASELINE, font2.getVerticalAlignment());
        msAssert.areEqual(msColor.Empty, font2.getHighlightColor());
        msAssert.areEqual(1024, font2.getLocaleId());

        font2.setName("Times New Roman");
        font2.setNameBi("Courier New");
        font2.setNameFarEast("Arial");
        font2.setNameOther("Wingdings");
        font2.setSize(14.0);
        font2.setColor(msColor.getDarkRed());
        font2.setHighlightColor(Color.CYAN);
        font2.setBold(false);
        font2.setItalic(false);
        font2.setUnderline(Underline.NONE);
        font2.setDoubleStrikeThrough(true);
        font2.setAllCaps(true);
        font2.setVerticalAlignment(RunVerticalAlignment.SUPERSCRIPT);
        font2.setLocaleId(1033);
        font2.setKerning(10.0);
        font2.setSpacing(3.0);

        font7.setBold(true);
        font7.setItalic(true);
        font7.setStrikeThrough(true);
        font7.setSmallCaps(true);

        doc = TestUtil.saveOpen(doc, FILE_NAME, null, false);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        legendEntries = chart.getLegend().getLegendEntries();

        msAssert.areEqual(8, legendEntries.getCount());

        font2 = legendEntries.get(2).getFont();
        font7 = legendEntries.get(7).getFont();

        checkFont(font2, "Times New Roman", 14.0, new Color((0x8B), (0), (0), (0xFF)), false, false, Underline.NONE);
        checkFont(font7, "Calibri", 14.0, msColor.Empty, true, true, Underline.NONE);

        msAssert.areEqual(10, font2.getKerning());
        msAssert.areEqual(3, font2.getSpacing());
        msAssert.areEqual("Courier New", font2.getNameBi());
        msAssert.areEqual("Arial", font2.getNameFarEast());
        msAssert.areEqual("Wingdings", font2.getNameOther());
        msAssert.areEqual(true, font2.getDoubleStrikeThrough());
        msAssert.areEqual(false, font2.getStrikeThrough());
        msAssert.areEqual(true, font2.getAllCaps());
        msAssert.areEqual(false, font2.getSmallCaps());
        msAssert.areEqual(RunVerticalAlignment.SUPERSCRIPT, font2.getVerticalAlignment());
        msAssert.areEqual(new Color((0), (0xFF), (0xFF), (0xFF)), font2.getHighlightColor());
        msAssert.areEqual(1033, font2.getLocaleId());

        msAssert.areEqual(false, font7.getDoubleStrikeThrough());
        msAssert.areEqual(true, font7.getStrikeThrough());
        msAssert.areEqual(false, font7.getAllCaps());
        msAssert.areEqual(true, font7.getSmallCaps());
    }

    /// <summary>
    /// Tests getting inherited attributes of the <see cref="ChartLegendEntry.Font"/> property.
    /// </summary>
    @Test
    public void testLegendEntryFontInheritedProperties() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Test23210.docx");
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartLegendEntryCollection legendEntries = chart.getLegend().getLegendEntries();

        chart.getSeries().add(
            "Series 5",
            new String[] { "Category1", "Category2", "Category3", "Category4" },
            new double[] { 7.0, 6.0, 5.0, 4.0 });

        msAssert.areEqual(9, legendEntries.getCount());

        Font font0 = legendEntries.get(0).getFont();
        Font font2 = legendEntries.get(2).getFont();
        Font font4 = legendEntries.get(4).getFont();
        Font font8 = legendEntries.get(8).getFont();

        checkFont(font0, "Calibri", 8.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font2, "Calibri Light", 10.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font4, "Calibri", 10.0, msColor.Empty, false, false, Underline.NONE);
        checkFont(font8, "Calibri", 14.0, msColor.Empty, false, false, Underline.NONE);

        // Change chart space properties to check that if a property is not defined directly and is not defined in
        // legend, it is taken from chart space properties.

        DmlRunProperties chartSpaceProperties = chart.getChartSpace().getTxPr().getRunPr();
        chartSpaceProperties.setLatinFont(new DmlFont());
        chartSpaceProperties.getLatinFont().setTextTypeface("Times New Roman");
        chartSpaceProperties.setFontSize(new DmlTextPoints(1200));
        chartSpaceProperties.setItalics(true);
        chartSpaceProperties.setUnderline(Underline.DOUBLE);
        chartSpaceProperties.setFill(new DmlSolidFill(DmlColor.createFromArgb(1.0, 0, 0, 0x8B)));
        chart.getChartSpace().getTxPr().getFirstParagraph().getProperties().hasDefaultRunProperties(true);

        checkFont(font0, "Times New Roman", 8.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
        checkFont(font2, "Calibri Light", 12.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font4, "Times New Roman", 12.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
        checkFont(font8, "Times New Roman", 14.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestLegendEntryFontInheritedProperties.docx", null, false);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        legendEntries = chart.getLegend().getLegendEntries();
        font0 = legendEntries.get(0).getFont();
        font2 = legendEntries.get(2).getFont();
        font4 = legendEntries.get(4).getFont();
        font8 = legendEntries.get(8).getFont();

        checkFont(font0, "Times New Roman", 8.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
        checkFont(font2, "Calibri Light", 12.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font4, "Times New Roman", 12.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
        checkFont(font8, "Times New Roman", 14.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);

        // Change legend properties to check that if a property is not defined directly, it is taken from legend.

        DmlRunProperties legendProperties = chart.getLegend().getTxPr().getRunPr();
        legendProperties.setLatinFont(new DmlFont());
        legendProperties.getLatinFont().setTextTypeface("Arial");
        legendProperties.setFontSize(new DmlTextPoints(800));
        legendProperties.setBold(true);
        legendProperties.setItalics(false);
        legendProperties.setUnderline(Underline.NONE);
        chart.getLegend().getTxPr().getFirstParagraph().getProperties().hasDefaultRunProperties(true);

        checkFont(font0, "Times New Roman", 8.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
        checkFont(font2, "Calibri Light", 12.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font4, "Arial", 8.0, new Color((0), (0), (0x8B), (0xFF)), true, false, Underline.NONE);
        checkFont(font8, "Times New Roman", 14.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestLegendEntryFontInheritedProperties.docx", null, false);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        legendEntries = chart.getLegend().getLegendEntries();
        font0 = legendEntries.get(0).getFont();
        font2 = legendEntries.get(2).getFont();
        font4 = legendEntries.get(4).getFont();
        font8 = legendEntries.get(8).getFont();

        checkFont(font0, "Times New Roman", 8.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
        checkFont(font2, "Calibri Light", 12.0, new Color((0), (0x80), (0), (0xFF)), true, true, Underline.SINGLE);
        checkFont(font4, "Arial", 8.0, new Color((0), (0), (0x8B), (0xFF)), true, false, Underline.NONE);
        checkFont(font8, "Times New Roman", 14.0, new Color((0), (0), (0x8B), (0xFF)), false, true, Underline.DOUBLE);
    }

    /// <summary>
    /// WORDSNET-23543 Legend entry text becomes hidden when updating font of a new/empty legend entry
    /// </summary>
    @Test
    public void test23543() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        ChartSeries series = shape.getChart().getSeries().get(0);
        Font font = series.getLegendEntry().getFont();

        checkFont(font, "Calibri", 9.0, new Color((89), (89), (89)), false, false, Underline.NONE);

        shape.getChart().getLegend().getTxPr().getRunPr().setItalics(true);
        shape.getChart().getLegend().getTxPr().getRunPr().setUnderline(Underline.DOTTED);
        checkFont(font, "Calibri", 9.0, new Color((89), (89), (89)), false, true, Underline.DOTTED);

        font.setSize(12.0);

        checkFont(font, "Calibri", 12.0, new Color((89), (89), (89)), false, true, Underline.DOTTED);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\Test23543",
            UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);

        series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        font = series.getLegendEntry().getFont();

        checkFont(font, "Calibri", 12.0, new Color((89), (89), (89)), false, true, Underline.DOTTED);

        font.clearFormatting();
        checkFont(font, "Calibri", 9.0, new Color((89), (89), (89)), false, true, Underline.DOTTED);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\Test23543ClearFormat",
            UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);

        series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        font = series.getLegendEntry().getFont();
        checkFont(font, "Calibri", 9.0, new Color((89), (89), (89)), false, true, Underline.DOTTED);
    }

    /// <summary>
    /// Tests resolved legend entry format when direct legend entry format, legend format and chart space format
    /// are defined at the same time.
    /// </summary>
    @Test
    public void testLegendEntryFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestLegendEntryFormat.docx");

        checkLegendEntryFont(doc, 0, 0, "Arial", 11.0, new Color((0x80), (0), (0)), true, true, Underline.SINGLE);
        checkLegendEntryFont(doc, 0, 1, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 0, 2, "Calibri Light", 7.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 0, 3, "Times New Roman", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 0, 4, "Calibri Light", 9.0, new Color((0), (0x80), (0)), true, false, Underline.DASH);

        checkLegendEntryFont(doc, 1, 0, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 1, 1, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 1, 2, "Calibri Light", 7.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 1, 3, "Times New Roman", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 1, 4, "Calibri Light", 9.0, new Color((0), (0x80), (0)), true, false, Underline.DASH);

        checkLegendEntryFont(doc, 2, 0, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 2, 1, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 2, 2, "Calibri Light", 7.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 2, 3, "Times New Roman", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 2, 4, "Calibri Light", 9.0, new Color((0), (0x80), (0)), true, false, Underline.DASH);

        checkLegendEntryFont(doc, 3, 0, "Calibri", 10.0, msColor.Empty, false, false, Underline.NONE);
        checkLegendEntryFont(doc, 3, 1, "Calibri", 10.0, msColor.Empty, false, false, Underline.NONE);
        checkLegendEntryFont(doc, 3, 2, "Calibri", 7.0, msColor.Empty, false, false, Underline.NONE);
        checkLegendEntryFont(doc, 3, 3, "Times New Roman", 10.0, msColor.Empty, false, false, Underline.NONE);
        checkLegendEntryFont(doc, 3, 4, "Calibri", 10.0, new Color((0), (0x80), (0)), false, false, Underline.NONE);

        checkLegendEntryFont(doc, 4, 0, "Calibri Light", 12.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 4, 1, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 4, 2, "Calibri Light", 7.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 4, 3, "Times New Roman", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 4, 4, "Calibri Light", 9.0, new Color((0), (0x80), (0)), true, false, Underline.DASH);

        checkLegendEntryFont(doc, 5, 0, "Arial", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 5, 1, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 5, 2, "Calibri Light", 7.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 5, 3, "Times New Roman", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 5, 4, "Calibri Light", 9.0, new Color((0), (0x80), (0)), true, false, Underline.DASH);

        checkLegendEntryFont(doc, 6, 0, "Calibri Light", 9.0, new Color((0x80), (0), (0)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 6, 1, "Calibri Light", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 6, 2, "Calibri Light", 7.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 6, 3, "Times New Roman", 9.0, new Color((0), (0), (0x80)), true, false, Underline.DASH);
        checkLegendEntryFont(doc, 6, 4, "Calibri Light", 9.0, new Color((0), (0x80), (0)), true, false, Underline.DASH);
    }

    /// <summary>
    /// Tests that no exception occurs when assigning <see cref="Font"/> properties supported by a legend entry.
    /// </summary>
    @Test
    public void testSupportedFontProperties() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        ChartSeries series = shape.getChart().getSeries().get(0);
        Font font = series.getLegendEntry().getFont();

        font.setName("Arial");
        font.setNameAscii("Arial");
        font.setNameBi("Times New Roman");
        font.setNameFarEast("Calibri");
        font.setNameOther("Windings");
        font.setThemeFont(ThemeFont.NONE);
        font.setThemeFontAscii(ThemeFont.NONE);
        font.setThemeFontBi(ThemeFont.NONE);
        font.setThemeFontFarEast(ThemeFont.NONE);
        font.setThemeFontOther(ThemeFont.NONE);
        font.setSize(12.0);
        font.setSizeBi(12.0);
        font.setBold(false);
        font.setBoldBi(false);
        font.setItalic(true);
        font.setItalicBi(true);
        font.setColor(Color.BLUE);
        font.setStrikeThrough(false);
        font.setDoubleStrikeThrough(true);
        font.setSuperscript(false);
        font.setSubscript(true);
        font.setSmallCaps(false);
        font.setAllCaps(false);
        font.setUnderline(Underline.NONE);
        font.setSpacing(1.0);
        font.setKerning(1.0);
        font.setHighlightColor(Color.YELLOW);
        font.setLocaleId(0x1409);
        font.setLocaleIdBi(0x1409);
        font.setLocaleIdFarEast(0x1409);

        font.getFill().solid(Color.BLUE);
        font.getBorder().setLineStyle(LineStyle.NONE);

        checkFont(font, "Arial", 12.0, new Color((0), (0), (0xff)), false, true, Underline.NONE);
        msAssert.areEqual("Arial", font.getNameAscii());
        msAssert.areEqual("Times New Roman", font.getNameBi());
        msAssert.areEqual("Calibri", font.getNameFarEast());
        msAssert.areEqual("Windings", font.getNameOther());
        msAssert.areEqual(ThemeFont.NONE, font.getThemeFont());
        msAssert.areEqual(ThemeFont.NONE, font.getThemeFontAscii());
        msAssert.areEqual(ThemeFont.NONE, font.getThemeFontBi());
        msAssert.areEqual(ThemeFont.NONE, font.getThemeFontFarEast());
        msAssert.areEqual(ThemeFont.NONE, font.getThemeFontOther());
        msAssert.areEqual(12, font.getSizeBi());
        msAssert.isFalse(font.getBoldBi());
        msAssert.isTrue(font.getItalicBi());
        msAssert.isFalse(font.getStrikeThrough());
        msAssert.isTrue(font.getDoubleStrikeThrough());
        msAssert.isFalse(font.getSuperscript());
        msAssert.isTrue(font.getSubscript());
        msAssert.isFalse(font.getSmallCaps());
        msAssert.isFalse(font.getAllCaps());
        msAssert.areEqual(1, font.getSpacing());
        msAssert.areEqual(1, font.getKerning());
        msAssert.areEqual(new Color((0xff), (0xff), (0)), font.getHighlightColor());
        // DML has only one locale Id property.
        // Font.LocaleId/LocaleIdBi/LocaleIdFarEast refer to the same DML property.
        msAssert.areEqual(0x1409, font.getLocaleId());
        msAssert.areEqual(0x1409, font.getLocaleIdBi());
        msAssert.areEqual(0x1409, font.getLocaleIdFarEast());
        // FOSS: font.LineSpacing assertion removed - it now reflects the last-resort font
        // (font-specific metrics were removed with the font substitution engine).

        // Readonly properties and method:
        msAssert.isFalse(font.hasDmlEffect(TextDmlEffect.GLOW));
        msAssert.areEqual(FillType.SOLID, font.getFill().getFillType());
        msAssert.areEqual(new Color((0), (0), (0xff)), font.getAutoColor());
        // Now a new instance of Border and Shading are returned when accessing the corresponding Font properties.
        msAssert.areEqual(LineStyle.NONE, font.getBorder().getLineStyle());
        msAssert.areEqual(msColor.Empty, font.getShading().getBackgroundPatternColor());

        // Currently unsupported properties:
        msAssert.areEqual(ThemeColor.NONE, font.getThemeColor());
        msAssert.areEqual(0, font.getTintAndShade());
        msAssert.isFalse(font.getShadow());
        msAssert.isFalse(font.getOutline());
        msAssert.isFalse(font.getEmboss());
        msAssert.isFalse(font.getEngrave());
        msAssert.isFalse(font.getHidden());
        msAssert.areEqual(msColor.Empty, font.getUnderlineColor());
        msAssert.areEqual(100, font.getScaling());
        msAssert.areEqual(0, font.getPosition());
        msAssert.areEqual(TextEffect.NONE, font.getTextEffect());
        msAssert.isFalse(font.getBidi());
        msAssert.isFalse(font.getComplexScript());
        msAssert.isFalse(font.getNoProofing());
        msAssert.areEqual("Default Paragraph Font", font.getStyleName());
        msAssert.areEqual(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, font.getStyleIdentifier());
        msAssert.isTrue(font.getSnapToGrid());
        msAssert.areEqual(EmphasisMark.NONE, font.getEmphasisMark());
    }

    /// <summary>
    /// Tests that exception occurs when setting <see cref="Font"/> properties unsupported by a legend entry.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe property is not supported on a chart object.\\E")
    public void testUnsupportedFontProperties() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        ChartSeries series = shape.getChart().getSeries().get(0);
        Font font = series.getLegendEntry().getFont();

        // Throws an exception.
        font.setThemeColor(ThemeColor.ACCENT_1);
    }

    /// <summary>
    /// Tests the <see cref="Font.Fill"/> property of a legend entry.
    /// </summary>
    @Test
    public void testFontFill() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        ChartLegend legend = shape.getChart().getLegend();
        DmlRunProperties legendRunProperties = legend.getTxPr().getRunPr();
        Font font1 = legend.getLegendEntries().get(0).getFont();
        Font font2 = legend.getLegendEntries().get(1).getFont();

        // Reset legend fill.
        legendRunProperties.remove(DmlRunPropertiesIds.FILL);

        font1.getFill().solid(Color.BLUE);

        msAssert.areEqual(new Color((0), (0), (0xff)), font1.getColor());
        msAssert.areEqual(new Color((0), (0), (0xff)), font1.getFill().getForeColor());

        legendRunProperties.setFill(new DmlSolidFill(DmlColor.createFromArgb(1.0, 0xff, 0, 0)));

        msAssert.areEqual(new Color((0), (0), (0xff)), font1.getColor());
        msAssert.areEqual(new Color((0), (0), (0xff)), font1.getFill().getForeColor());
        msAssert.areEqual(new Color((0xff), (0), (0)), font2.getColor());
        msAssert.areEqual(new Color((0xff), (0), (0)), font2.getFill().getForeColor());
        msAssert.areEqual(new Color((0xff), (0), (0)), legendRunProperties.getFill().getColorInternal().toNativeColor());

        font2.setColor(msColor.getGreen());

        msAssert.areEqual(new Color((0), (0x80), (0)), font2.getColor());
        msAssert.areEqual(new Color((0), (0x80), (0)), font2.getFill().getForeColor());
        msAssert.areEqual(new Color((0xff), (0), (0)), legendRunProperties.getFill().getColorInternal().toNativeColor());

        TestUtil.save(doc, "Model\\Charts\\TestFontFill.docx", null, true);
    }

    /// <summary>
    /// Tests the <see cref="ChartLegend.Font"/> property.
    /// </summary>
    // Font is not defined in legend, chart space (pre Word 2016 chart).
    @Test (dataProvider = "testLegendFontDataProvider")
    // Font is defined in legend (pre Word 2016 chart).
    // Font is defined in chart space (pre Word 2016 chart).
    // Chart space is not used in Word 2016 charts to get legend font properties.
    // Font is not defined in legend (Word 2016 chart).
    // Font is defined in legend (Word 2016 chart).
    public void testLegendFont(String fileName, String expectedName, int expectedSize, /*uint*/int expectedColor) throws Exception
    {
        Document doc = TestUtil.open(msString.format("Model\\Charts\\{0}.docx", fileName));
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartLegend legend = chart.getLegend();
        Font font = legend.getFont();

        Color color = (expectedColor == 0) ? msColor.Empty : new Color((/*unchecked*/((int)(expectedColor & 0xFFFFFFFFL))), true);

        checkFont(font, expectedName, expectedSize, color, false, false, Underline.NONE);

        int expectedKerning = chart.getChartSpace().isChartEx() ? 0 : 12;
        msAssert.areEqual(expectedKerning, font.getKerning());
        msAssert.areEqual(0, font.getSpacing());
        msAssert.isFalse(font.getDoubleStrikeThrough());
        msAssert.isFalse(font.getStrikeThrough());
        msAssert.isFalse(font.getAllCaps());
        msAssert.isFalse(font.getSmallCaps());
        msAssert.areEqual(RunVerticalAlignment.BASELINE, font.getVerticalAlignment());
        msAssert.areEqual(msColor.Empty, font.getHighlightColor());

        font.setName("Times New Roman");
        font.setNameBi("Courier New");
        font.setNameFarEast("Arial");
        font.setNameOther("Wingdings");
        font.setSize(14.0);
        font.setColor(msColor.getDarkRed());
        font.setHighlightColor(Color.CYAN);
        font.setBold(false);
        font.setItalic(true);
        font.setUnderline(Underline.NONE);
        font.setDoubleStrikeThrough(true);
        font.setAllCaps(true);
        font.setVerticalAlignment(RunVerticalAlignment.SUPERSCRIPT);
        font.setLocaleId(1033);
        font.setKerning(10.0);
        font.setSpacing(3.0);

        doc = TestUtil.saveOpen(doc, msString.format("Model\\Charts\\{0}LegendFont.docx", fileName), null, false);
        legend = doc.getFirstSection().getBody().getShapes().get(0).getChart().getLegend();
        font = legend.getFont();

        checkFont(font, "Times New Roman", 14.0, new Color((0x8B), (0), (0), (0xFF)), false, true, Underline.NONE);

        msAssert.areEqual(10, font.getKerning());
        msAssert.areEqual(3, font.getSpacing());
        msAssert.areEqual("Courier New", font.getNameBi());
        msAssert.areEqual("Arial", font.getNameFarEast());
        msAssert.areEqual("Wingdings", font.getNameOther());
        msAssert.isTrue(font.getDoubleStrikeThrough());
        msAssert.isFalse(font.getStrikeThrough());
        msAssert.isTrue(font.getAllCaps());
        msAssert.isFalse(font.getSmallCaps());
        msAssert.areEqual(RunVerticalAlignment.SUPERSCRIPT, font.getVerticalAlignment());
        msAssert.areEqual(new Color((0), (0xFF), (0xFF), (0xFF)), font.getHighlightColor());
        msAssert.areEqual(1033, font.getLocaleId());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testLegendFontDataProvider")
	public static Object[][] testLegendFontDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Test23210",  "Calibri",  10,  (int) 0L},
			{"TestLegendEntriesOrderStacked",  "Calibri",  9,  (int) 0xff595959L},
			{"TestChartSpaceFont",  "Arial",  12,  (int) 0xff2F5496L},
			{"Word2016Charts\\Pareto",  "Calibri",  9,  (int) 0xff595959L},
			{"Word2016Charts\\TestLegendFont",  "Courier New",  11,  (int) 0xff6ca644L},
		};
	}

    /// <summary>
    /// Tests the <see cref="ChartLegend.Font"/> property in a new chart.
    /// </summary>
    @Test
    public void testLegendFontInNewChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape1 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        ChartLegend legend1 = shape1.getChart().getLegend();
        Font font1 = legend1.getFont();

        checkFont(font1, "Calibri", 9.0, new Color((0x59), (0x59), (0x59)), false, false, Underline.NONE);

        font1.setName("Times New Roman");
        font1.setNameBi("Courier New");
        font1.setNameFarEast("Arial");
        font1.setNameOther("Wingdings");
        font1.setSize(12.0);
        font1.setColor(msColor.getDarkRed());
        font1.setHighlightColor(Color.CYAN);
        font1.setBold(true);
        font1.setItalic(false);
        font1.setUnderline(Underline.SINGLE);
        font1.setStrikeThrough(true);
        font1.setSmallCaps(true);
        font1.setVerticalAlignment(RunVerticalAlignment.SUBSCRIPT);
        font1.setLocaleId(1033);
        font1.setKerning(3.0);
        font1.setSpacing(2.0);

        builder.insertParagraph();

        Shape shape2 = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);

        ChartLegend legend2 = shape2.getChart().getLegend();
        Font font2 = legend2.getFont();
        font2.setSize(15.0);
        font2.setBold(true);

        font2.getFill().twoColorGradient(Color.BLUE, msColor.getOrange(), GradientStyle.DIAGONAL_DOWN, GradientVariant.VARIANT_2);

        msAssert.areEqual(msColor.Empty, font2.getColor());
        msAssert.areEqual(new Color((0), (0), (0xff)), font2.getFill().getForeColor());
        msAssert.areEqual(new Color((0xff), (0xa5), (0)), font2.getFill().getBackColor());

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestLegendFontInNewChart.docx", null, false);
        legend1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getLegend();
        font1 = legend1.getFont();

        checkFont(font1, "Times New Roman", 12.0, new Color((0x8B), (0), (0), (0xFF)), true, false, Underline.SINGLE);

        msAssert.areEqual(3, font1.getKerning());
        msAssert.areEqual(2, font1.getSpacing());
        msAssert.areEqual("Courier New", font1.getNameBi());
        msAssert.areEqual("Arial", font1.getNameFarEast());
        msAssert.areEqual("Wingdings", font1.getNameOther());
        msAssert.isFalse(font1.getDoubleStrikeThrough());
        msAssert.isTrue(font1.getStrikeThrough());
        msAssert.isFalse(font1.getAllCaps());
        msAssert.isTrue(font1.getSmallCaps());
        msAssert.areEqual(RunVerticalAlignment.SUBSCRIPT, font1.getVerticalAlignment());
        msAssert.areEqual(new Color((0), (0xFF), (0xFF), (0xFF)), font1.getHighlightColor());
        msAssert.areEqual(1033, font1.getLocaleId());

        legend2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getLegend();
        font2 = legend2.getFont();

        checkFont(font2, "Calibri", 15.0, msColor.Empty, true, false, Underline.NONE);

        msAssert.areEqual(new Color((0), (0), (0xff)), font2.getFill().getForeColor());
        msAssert.areEqual(new Color((0xff), (0xa5), (0)), font2.getFill().getBackColor());
    }

    /// <summary>
    /// Tests the <see cref="ChartLegend.Position"/> property of a legend in a Word 2016 chart.
    /// </summary>
    @Test
    public void testWord2016ChartLegendPosition() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape1 = builder.insertChart(ChartType.TREEMAP, 432.0, 252.0);

        ChartLegend legend = shape1.getChart().getLegend();

        checkChartExLegendPosition(legend, LegendPosition.TOP, SidePosition.TOP, PositionAlignment.CENTER);

        legend.setPosition(LegendPosition.TOP_RIGHT);
        checkChartExLegendPosition(legend, LegendPosition.TOP_RIGHT, SidePosition.TOP, PositionAlignment.MAXIMUM);

        legend.setPosition(LegendPosition.BOTTOM);
        checkChartExLegendPosition(legend, LegendPosition.BOTTOM, SidePosition.BOTTOM, PositionAlignment.CENTER);

        legend.setSidePosition(SidePosition.LEFT);
        checkChartExLegendPosition(legend, LegendPosition.LEFT, SidePosition.LEFT, PositionAlignment.CENTER);

        legend.setPositionAlignment(PositionAlignment.MINIMUM);
        checkChartExLegendPosition(legend, LegendPosition.LEFT, SidePosition.LEFT, PositionAlignment.MINIMUM);

        legend.setPosition(LegendPosition.LEFT);
        checkChartExLegendPosition(legend, LegendPosition.LEFT, SidePosition.LEFT, PositionAlignment.MINIMUM);

        legend.setPosition(LegendPosition.TOP);
        checkChartExLegendPosition(legend, LegendPosition.TOP, SidePosition.TOP, PositionAlignment.CENTER);

        legend.setPositionAlignment(PositionAlignment.MAXIMUM);
        checkChartExLegendPosition(legend, LegendPosition.TOP_RIGHT, SidePosition.TOP, PositionAlignment.MAXIMUM);
    }

    /// <summary>
    /// Checks the position properties of a legend.
    /// </summary>
    private void checkChartExLegendPosition(ChartLegend legend, /*LegendPosition*/int expectedPosition,
        /*SidePosition*/int expectedSidePosition, /*PositionAlignment*/int expectedAlignment)
    {
        msAssert.areEqual(expectedPosition, legend.getPosition());
        msAssert.areEqual(expectedSidePosition, legend.getSidePosition());
        msAssert.areEqual(expectedAlignment, legend.getPositionAlignment());
    }

    /// <summary>
    /// Checks font properties of a legend entry specified by chart shape index and legend entry index.
    /// </summary>
    private static void checkLegendEntryFont(Document document, int shapeIndex, int entryIndex,
        String expectedFontName, double expectedFontSize, Color expectedColor, boolean expectedBold,
        boolean expectedItalic, /*Underline*/int expectedUnderline)
    {
        Chart chart = document.getFirstSection().getBody().getShapes().get(shapeIndex).getChart();
        ChartLegendEntry legendEntry = chart.getLegend().getLegendEntries().get(entryIndex);

        checkFont(legendEntry.getFont(), expectedFontName, expectedFontSize, expectedColor,
            expectedBold, expectedItalic, expectedUnderline);
    }

    /// <summary>
    /// Checks properties of a <see cref="Font"/> instance.
    /// </summary>
    private static void checkFont(Font font, String expectedFontName, double expectedFontSize, Color expectedColor,
        boolean expectedBold, boolean expectedItalic, /*Underline*/int expectedUnderline)
    {
        msAssert.areEqual(expectedFontName, font.getName());
        msAssert.areEqual(expectedFontSize, 0.1, font.getSize());
        msAssert.areEqual(expectedColor, font.getColor());
        msAssert.areEqual(expectedBold, font.getBold());
        msAssert.areEqual(expectedItalic, font.getItalic());
        msAssert.areEqual(expectedUnderline, font.getUnderline());
    }
}

