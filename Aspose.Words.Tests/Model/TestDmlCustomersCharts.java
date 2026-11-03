// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/25/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import java.awt.Color;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.OpcPackage;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Simple open/save/compare tests for DOCX document with charts.
/// Used to make sure charts are written properly from the model.
/// Use test documents from customers charts rendering tests.
/// </summary>
@Test
public class TestDmlCustomersCharts
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

    @Test
    public void testJira6960() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Customers\\TestJira6960.docx");
    }










    @Test
    public void testJira7408() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Customers\\TestJira7408.docx");
    }



    @Test
    public void testJira7657() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Customers\\TestJira7657.docx");
    }















    @Test
    public void testJira8362() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Customers\\TestJira8362.docx");
    }

























    @Test
    public void testJira9289() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Customers\\TestJira9289.docx");
    }





    @Test
    public void testJira9254() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Charts\\Customers\\TestJira9254.docx");
    }





















    /// <summary>
    /// Tests removing an embedded XLSX file containing chart data in a <see cref="DmlChartType.ChartExChart"/> chart.
    /// </summary>
    @Test
    public void testRemovingEmbeddedXlsxInChartExChart() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\Word2016Charts\\BoxWhisker.docx";
        Document doc = TestUtil.open(FILE_NAME);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        Chart chart = shape.getChart();

        chart.getChartSpace().removeExternalDataLinkage();

        TestUtil.save(doc, FILE_NAME, null, true, GoldLevel.EXPORT_ONLY);

        checkForNoEmbeddedXlsx(doc);
    }




    /// <summary>
    /// WORDSNET-26356 Font Control in Bar Chart
    /// The customer needs to set font properties for various chart elements. The required features have been added
    /// to the API. And this test generates a chart similar to the one contained in the document that the customer
    /// sent us as an example.
    /// </summary>
    @Test
    public void test26356() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getPageSetup().setPaperSize(PaperSize.A4);
        doc.getFirstSection().getPageSetup().setLeftMargin(72.0);

        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();

        ChartSeriesCollection series = chart.getSeries();
        series.clear();
        double[] xValues = new double[] { 2020.0, 2021.0, 2022.0, 2023.0 };
        series.add("Equity Fund", xValues, new double[] { 21.05, 18.89, 1.03, 12.56 });
        series.add("Debt Fund", xValues, new double[] { 6.01, 7.23, 7.56, 6.05 });
        series.add("Index", xValues, new double[] { 14.23, 12.32, 8.09, 14.11 });

        for (ChartSeries currentSeries : series)
        {
            currentSeries.hasDataLabels(true);
            ChartDataLabelCollection dataLabels = currentSeries.getDataLabels();
            dataLabels.setShowSeriesName(false);
            dataLabels.setShowCategoryName(false);
            dataLabels.setShowLegendKey(false);
            dataLabels.setShowValue(true);
            // This just makes dataLabels.TxPr non empty to write the rotation value set below to the document.
            dataLabels.getFont().setSize(9.0);

            // This is not currently supported in API.
            dataLabels.getTxPr().getBodyPr().setRotation(new DmlAngle(-5400000));
        }

        series.get(0).getFormat().getFill().setForeColor(new Color((0x8f), (0xaa), (0xdc), (0xff)));
        series.get(1).getDataPoints().get(0).getFormat().getFill().setForeColor(new Color((0xbf), (0x90), (0), (0xff)));
        series.get(2).getFormat().getFill().setForeColor(new Color((0x76), (0x71), (0x71), (0xff)));

        ChartTitle title = chart.getTitle();
        title.setText("Performance Comparison");
        title.getFont().setSize(16.0);
        title.getFont().setBold(true);

        chart.getDataTable().setShow(true);
        Font dataTableFont = chart.getDataTable().getFont();
        dataTableFont.setName("Aptos Display");
        dataTableFont.setBold(true);

        ChartAxisTitle axisXTitle = chart.getAxisX().getTitle();
        axisXTitle.setShow(true);
        axisXTitle.setText("Calendar Year");
        axisXTitle.getFont().setSize(14.0);
        axisXTitle.getFont().setItalic(true);
        ChartAxisTitle axisYTitle = chart.getAxisY().getTitle();
        axisYTitle.setShow(true);
        axisYTitle.setText("Performance");
        axisYTitle.getFont().setSize(12.0);
        axisYTitle.getFont().setItalic(true);

        chart.getLegend().setPosition(LegendPosition.LEFT);

        chart.getSeriesGroups().get(0).setOverlap(0);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\Customers\\Test26356.docx");
    }



    /// <summary>
    /// Checks that the document doesn't have an embedded XLSX file containing chart data.
    /// </summary>
    private static void checkForNoEmbeddedXlsx(Document doc) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            doc.save(stream, SaveFormat.DOCX);
            stream.setPosition(0);
            OpcPackage _package = new OpcPackage(stream);
            msAssert.isNull(_package.getPartByName("/word/embeddings/Microsoft_Excel_Worksheet1.xlsx"));
        }
        finally { if (stream != null) stream.close(); }
    }
}

