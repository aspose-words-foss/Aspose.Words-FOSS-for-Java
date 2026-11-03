// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/03/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;


/// <summary>
/// The class for testing stroke fill of the Chart.
/// </summary>
@Test
public class TestStrokeFillChart extends TestFillFormatBase
{
    /// <summary>
    /// Tests Solid fill.
    /// </summary>
    @Test
    public void testSolid() throws Exception
    {
        Document doc = TestUtil.open(msString.format("{0}TestSolidStrokeFill.docx", TEST_DIR));
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        Fill fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(0.93, 0.01, fill.getOpacity());

        checkColor(new Color((0xFF), (0x00), (0x00)), fill.getForeColor());
    }

    /// <summary>
    /// Tests Gradient fill.
    /// </summary>
    @Test
    public void testGradient() throws Exception
    {
        Document doc = TestUtil.open(msString.format("{0}TestGradientStrokeFill.docx", TEST_DIR));
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        Fill fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();

        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(0.91, 0.01, fill.getOpacity());

        GradientStopCollection gradients = fill.getGradientStops();

        msAssert.areEqual(3, gradients.getCount());
        checkGradientStop(gradients.get(0), Color.WHITE, 0.0, 0.09);
        checkGradientStop(gradients.get(1), Color.RED, 0.4, 0.07);
        checkGradientStop(gradients.get(2), Color.YELLOW, 1.0, 0.12);
    }

    /// <summary>
    /// Tests NoFill.
    /// </summary>
    @Test
    public void testNoFill() throws Exception
    {
        Document doc = TestUtil.open(msString.format("{0}TestNoFillStroke.docx", TEST_DIR));
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        Fill fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.isFalse(fill.getVisible());

        checkColor(msColor.Empty, fill.getForeColor());
    }

    /// <summary>
    /// Tests setting Solid fill.
    /// </summary>
    @Test
    public void testSetSolid() throws Exception
    {
        DocumentBuilder builder = createDocument(ShapeMarkupLanguage.DML);
        Chart chart = createChart(builder);

        Fill fill = chart.getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        fill.solid(Color.RED);

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.isTrue(fill.getVisible());
        checkColor(Color.RED, fill.getForeColor());

        Document doc = builder.getDocument();
        // Roundtrip the document.
        String outFileName = msString.format("{0}\\SetSolidChartStroke.docx", TEST_DIR);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        checkColor(Color.RED, fill.getForeColor());
    }

    /// <summary>
    /// Tests setting gradient fill.
    /// </summary>
    @Test
    public void testSetGradient() throws Exception
    {
        DocumentBuilder builder = createDocument(ShapeMarkupLanguage.DML);
        Chart chart = createChart(builder);

        Fill fill = chart.getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();

        fill.oneColorGradient(Color.RED, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.3);

        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.isTrue(fill.getVisible());

        GradientStopCollection gradients = fill.getGradientStops();
        checkGradientStop(gradients.get(0), Color.RED, 0.0, 0.0);
        checkGradientStop(gradients.get(1), new Color((0x99), (0x00), (0x00)), 1.0, 0.0);
        msAssert.areEqual(2, gradients.getCount());

        Document doc = builder.getDocument();
        // Roundtrip the document.
        String outFileName = msString.format("{0}\\SetGradientChartStroke.docx", TEST_DIR);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.isTrue(fill.getVisible());

        gradients = fill.getGradientStops();
        checkGradientStop(gradients.get(0), Color.RED, 0.0, 0.0);
        checkGradientStop(gradients.get(1), new Color((0x99), (0x00), (0x00)), 1.0, 0.0);
        msAssert.areEqual(2, gradients.getCount());
    }

    /// <summary>
    /// Tests <see cref="Fill.Visible"/> property.
    /// </summary>
    @Test
    public void testVisible() throws Exception
    {
        DocumentBuilder builder = createDocument(ShapeMarkupLanguage.DML);
        Chart chart = createChart(builder);

        Fill fill = chart.getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.isFalse(fill.getVisible());

        // Check set true.
        fill.setVisible(true);
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(FillType.SOLID, fill.getFillType());

        Document doc = builder.getDocument();
        // Roundtrip the document.
        String outFileName = msString.format("{0}\\SetVisibleChartStrokeTrue.docx", TEST_DIR);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);

        // Check Visible is preserved after round-trip.
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.isTrue(fill.getVisible());
        msAssert.areEqual(FillType.SOLID, fill.getFillType());

        // Make invisible.
        fill.setVisible(false);
        msAssert.isFalse(fill.getVisible());
        msAssert.areEqual(FillType.SOLID, fill.getFillType());

        // Roundtrip the document.
        outFileName = msString.format("{0}\\SetVisibleChartStrokeFalse.docx", TEST_DIR);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);

        // Check Visible is preserved after round-trip.
        shape = doc.getFirstSection().getBody().getShapes().get(0);
        fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.isFalse(fill.getVisible());
        msAssert.areEqual(FillType.SOLID, fill.getFillType());
    }

    /// <summary>
    /// Tests <see cref="Fill.Transparency"/> property.
    /// </summary>
    @Test
    public void testTransparency() throws Exception
    {
        DocumentBuilder builder = createDocument(ShapeMarkupLanguage.DML);
        Chart chart = createChart(builder);

        Fill fill = chart.getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(1.0, 0.01, fill.getTransparency());

        // Change transparency.
        fill.setTransparency(0.87);
        msAssert.areEqual(0.87, 0.01, fill.getTransparency());

        Document doc = builder.getDocument();
        // Roundtrip the document.
        String outFileName = msString.format("{0}\\SetTransparencyChartStroke.docx", TEST_DIR);
        doc = TestUtil.saveOpen(doc, outFileName, SaveOptions.createSaveOptions(SaveFormat.DOCX), false);

        // Check Transparency is preserved after round-trip.
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        fill = shape.getChart().getSeries().get(0).getDataPoints().get(0).getFormat().getStroke().getFill();
        msAssert.areEqual(0.87, 0.01, fill.getTransparency());
    }

    private static final String TEST_DIR = "Model\\Charts\\Stroke\\Fill\\";
}

