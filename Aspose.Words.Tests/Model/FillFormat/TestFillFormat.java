// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/02/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.MathUtil;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.IO.File;
import com.aspose.ArrayUtil;


/// <summary>
/// The class for testing various properties of the new Fill (VBA FillFormat) object requested by the customers.
/// </summary>
@Test
public class TestFillFormat extends TestFillFormatBase
{
    /// <summary>
    /// WORDSNET-7788 Support Font.Fill property and provide public members in API.
    /// The new <see cref="Fill"/> (FillFormat) type and its related properties are exposed publicly.
    /// </summary>
    @Test
    public void test7788() throws Exception
    {
        Document doc = TestUtil.open("Model\\FillFormat\\Test7788.docx");
        RunCollection runs = doc.getFirstSection().getBody().getFirstParagraph().getRuns();

        // This is the customer's scenario to check the transparency of the font.
        msAssert.areEqual(0, runs.get(0).getFont().getFill().getTransparency());
        msAssert.areEqual(1, runs.get(1).getFont().getFill().getTransparency());
        msAssert.areEqual(0, runs.get(2).getFont().getFill().getTransparency());

        // Check all related types and their properties are exposed publicly.
    }

    /// <summary>
    /// WORDSNET-18317 The gradient fill support for WordArt shapes.
    /// Fixed per related WORDSNET-22003
    /// </summary>
    @Test
    public void test18317() throws Exception
    {
        Document doc = TestUtil.open("Model\\FillFormat\\Test18317.docx");

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.isTrue(shape.isTextBox());

        Fill fill = shape.getFirstParagraph().getFirstRun().getFont().getFill();
        msAssert.areEqual(new Color((0x20), (0x58), (0x67), (0xFF)), fill.getForeColor());
        msAssert.areEqual(new Color((0x92), (0xCC), (0xDC), (0xFF)), fill.getBackColor());
    }


    /// <summary>
    /// WORDSNET-21594 Provide API to Add Remove Gradient Color Fill for Text.
    /// Fixed per related WORDSNET-22003
    /// </summary>
    @Test
    public void test21594() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        // Add gradient fill.
        builder.getFont().getFill().twoColorGradient(Color.RED, Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2);
        builder.write("Some text");

        // Roundtrip and check.
        OoxmlComplianceInfo.markAsHasDrawingExtensions(builder.getDocument());
        Document doc = TestUtil.saveOpen(builder.getDocument(), "Model\\FillFormat\\Test21594", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        Fill fill = doc.getFirstSection().getBody().getFirstParagraph().getFirstRun().getFont().getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(new Color((0xFF), (0x00), (0x00), (0xFF)), fill.getForeColor());
        msAssert.areEqual(new Color((0x00), (0x00), (0xFF), (0xFF)), fill.getBackColor());

        // Remove fill.
        fill.solid();
        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(new Color((0x00), (0x00), (0x00), (0xFF)), fill.getForeColor());
        msAssert.areEqual(msColor.Empty, fill.getBackColor());
    }

      /// <summary>
    /// WORDSNET-16733 Add feature to get the gradient fill properties of a shape.
    /// Fixed per related WORDSNET-7788, WORDSNET-22003, WORDSNET-22808
    /// </summary>
    @Test
    public void test16733() throws Exception
    {
        Document doc = TestUtil.open("Model\\FillFormat\\Test16733.docx");

        Fill fill = doc.getFirstSection().getBody().getShapes().get(0).getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.HORIZONTAL, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_1, fill.getGradientVariant());
        msAssert.areEqual(90.0, 0.001, fill.getGradientAngle());
        msAssert.areEqual(4, fill.getGradientStops().getCount());
        checkGradientStop(fill.getGradientStops().get(1), new Color((0xA9), (0xBE), (0xE4)), 0.74, 0.0);

        fill = TestUtil.getRunWithText(doc, "...Text...").getFont().getFill();
        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(msColor.Empty, fill.getForeColor());
    }

    /// <summary>
    /// WORDSNET-18648 Text gradient support.
    /// Fixed per related WORDSNET-7788, WORDSNET-22003, WORDSNET-22808
    /// </summary>
    @Test
    public void test18648() throws Exception
    {
        Document doc = TestUtil.open("Model\\FillFormat\\Test18648.docx");

        Fill fill = TestUtil.getRunWithText(doc, "...First example...").getFont().getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.HORIZONTAL, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_1, fill.getGradientVariant());
        msAssert.areEqual(90.0, 0.001, fill.getGradientAngle());
        msAssert.areEqual(7, fill.getGradientStops().getCount());

        checkGradientStop(fill.getGradientStops().get(0), new Color((0xA6), (0x03), (0xAB)), 0.00);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0x08), (0x19), (0xFB)), 0.21);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0x1A), (0x8D), (0x48)), 0.35);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0xFF), (0xFF), (0x00)), 0.52);
        checkGradientStop(fill.getGradientStops().get(4), new Color((0xEE), (0x3F), (0x17)), 0.73);
        checkGradientStop(fill.getGradientStops().get(5), new Color((0xE8), (0x17), (0x66)), 0.88);
        checkGradientStop(fill.getGradientStops().get(6), new Color((0xA6), (0x03), (0xAB)), 1.00);

        // Let's, for example, interchange gradient stops 1 and 5.
        // For this purpose, first remove the gradient stops.
        GradientStop gradientStopA = fill.getGradientStops().get(1);
        GradientStop gradientStopB = fill.getGradientStops().get(5);
        gradientStopA.remove();
        gradientStopB.remove();
        // And then insert removed gradient stops to the new indexes.
        fill.getGradientStops().insert(1, gradientStopB);
        fill.getGradientStops().insert(5, gradientStopA);

        // Now, let's apply new gradient stop to one of the indexes.
        fill.getGradientStops().set(4, new GradientStop(msColor.getAqua(), fill.getGradientStops().get(4).getPosition(), 0.25));

        // At last, let's move one of the gradient stops at the new position.
        fill.getGradientStops().get(3).setPosition(fill.getGradientStops().get(3).getPosition() + 0.1);

        // Roundtrip and check stops.
        doc = TestUtil.saveOpen(doc, "Model\\FillFormat\\Test18648", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        fill = TestUtil.getRunWithText(doc, "...First example...").getFont().getFill();
        checkGradientStop(fill.getGradientStops().get(0), new Color((0xA6), (0x03), (0xAB)), 0.00);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0xE8), (0x17), (0x66)), 0.88);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0x1A), (0x8D), (0x48)), 0.35);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0xFF), (0xFF), (0x00)), 0.62);
        checkGradientStop(fill.getGradientStops().get(4), msColor.getAqua(), 0.73, 0.25);
        checkGradientStop(fill.getGradientStops().get(5), new Color((0x08), (0x19), (0xFB)), 0.21);
        checkGradientStop(fill.getGradientStops().get(6), new Color((0xA6), (0x03), (0xAB)), 1.00);
    }




    /// <summary>
    /// WORDSNET-18739 Gather data about gradient fills.
    /// Fixed per WORDSNET-7788, WORDSNET-22003, WORDSNET-22808
    /// Also added implementation of <see cref="IDmlCommonShapePrSource"/> to <see cref="DmlLockedCanvas"/>.
    /// </summary>
    @Test
    public void test18739() throws Exception
    {
        Document doc = TestUtil.open("Model\\FillFormat\\Test18739.docx");
        NodeCollection shapes = doc.getChildNodes(NodeType.GROUP_SHAPE, true);

        // Check first shape.
        Fill fill = ((GroupShape)shapes.get(0)).getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.DIAGONAL_DOWN, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_1, fill.getGradientVariant());
        msAssert.areEqual(135.0, 0.001, fill.getGradientAngle());
        msAssert.areEqual(4, fill.getGradientStops().getCount());
        checkGradientStop(fill.getGradientStops().get(0), new Color((0xFF), (0x00), (0x00)), 0.3);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0x1F), (0x49), (0x7D)), 0.0);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0xFF), (0xFF), (0x00)), 0.8);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0x00), (0x00), (0x00)), 1.0);

        // Check second shape.
        fill = ((GroupShape)shapes.get(1)).getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.FROM_CENTER, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_1, fill.getGradientVariant());
        msAssert.areEqual(5, fill.getGradientStops().getCount());
        checkGradientStop(fill.getGradientStops().get(0), new Color((0xFF), (0x00), (0x00)), 0.65);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0x00), (0x00), (0x00)), 0.3);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0x1F), (0x49), (0x7D)), 0.0);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0xFF), (0xFF), (0xFF)), 0.8);
        checkGradientStop(fill.getGradientStops().get(4), new Color((0x00), (0xB0), (0x50)), 1.0);

        // Check third shape.
        fill = ((GroupShape)shapes.get(2)).getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.FROM_CORNER, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_4, fill.getGradientVariant());
        msAssert.areEqual(6, fill.getGradientStops().getCount());
        checkGradientStop(fill.getGradientStops().get(0), new Color((0xFF), (0x00), (0x00)), 0.5);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0x00), (0x00), (0x00)), 0.25);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0x9A), (0xB5), (0xE3)), 0.0);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0x00), (0xB0), (0x50)), 0.9);
        checkGradientStop(fill.getGradientStops().get(4), new Color((0xF7), (0x96), (0x46)), 0.8);
        checkGradientStop(fill.getGradientStops().get(5), new Color((0x70), (0x30), (0xA0)), 1.0);

        // Check fourth shape.
        fill = ((GroupShape)shapes.get(3)).getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.FROM_CENTER, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_1, fill.getGradientVariant());
        msAssert.areEqual(5, fill.getGradientStops().getCount());
        checkGradientStop(fill.getGradientStops().get(0), new Color((0xFF), (0x00), (0x00)), 0.65);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0x00), (0x00), (0x00)), 0.3);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0x1F), (0x49), (0x7D)), 0.0);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0xFF), (0xFF), (0xFF)), 0.8);
        checkGradientStop(fill.getGradientStops().get(4), new Color((0x00), (0xB0), (0x50)), 1.0);

        // Check fifth shape.
        fill = ((GroupShape)shapes.get(4)).getFill();
        msAssert.areEqual(FillType.GRADIENT, fill.getFillType());
        msAssert.areEqual(GradientStyle.FROM_CENTER, fill.getGradientStyle());
        msAssert.areEqual(GradientVariant.VARIANT_1, fill.getGradientVariant());
        msAssert.areEqual(5, fill.getGradientStops().getCount());
        // Strangely enough, Word shows 0.72 in both UI and VBA.
        // But the corresponding element is as follows: <a:gs pos="70000">.
        checkGradientStop(fill.getGradientStops().get(0), new Color((0xFF), (0x00), (0x00)), 0.70);
        checkGradientStop(fill.getGradientStops().get(1), new Color((0x49), (0x44), (0x29)), 0.4);
        checkGradientStop(fill.getGradientStops().get(2), new Color((0x00), (0xB0), (0x50)), 0.2);
        checkGradientStop(fill.getGradientStops().get(3), new Color((0x1F), (0x49), (0x7D)), 0.0);
        checkGradientStop(fill.getGradientStops().get(4), new Color((0xFF), (0xFF), (0x00)), 1.0);
    }



    /// <summary>
    /// Ability to get foreground colour without modifiers in Fill and Stroke classes.
    /// Implemented <see cref="Fill.BaseForeColor"/> and <see cref="Stroke.BaseForeColor"/> properties.
    /// </summary>
    @Test
    public void test24933() throws Exception
    {
        Document doc = TestUtil.open("Model\\FillFormat\\Test24933.docx");
        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);

        msAssert.areEqual(new Color((0xFE), (0x5D), (0x5D)), shape.getFill().getForeColor());
        msAssert.areEqual(new Color((Color.RED).getRed(), (Color.RED).getGreen(), (Color.RED).getBlue(), (0xFF)), shape.getFill().getBaseForeColor());

        msAssert.areEqual(new Color((0x0D), (0xFF), (0x0D)), shape.getStroke().getForeColor());
        msAssert.areEqual(new Color((msColor.getGreen()).getRed(), (msColor.getGreen()).getGreen(), (msColor.getGreen()).getBlue(), (0xFF)), shape.getStroke().getBaseForeColor());

        msAssert.areEqual(new Color((0x0D), (0xFF), (0x0D)), shape.getStroke().getFill().getForeColor());
        msAssert.areEqual(new Color((msColor.getGreen()).getRed(), (msColor.getGreen()).getGreen(), (msColor.getGreen()).getBlue(), (0xFF)), shape.getStroke().getFill().getBaseForeColor());
    }

    /// <summary>
    /// Checks properties of a specified Fill object.
    /// </summary>
    private static void checkFill(Fill fill, Color expectedForeColor, boolean expectedVisible, double expectedOpacity)
    {
        // Fill.Color takes in account Opacity.
        checkColor(new Color((expectedForeColor).getRed(), (expectedForeColor).getGreen(), (expectedForeColor).getBlue(), (MathUtil.doubleToInt(expectedOpacity * 255d))), fill.getColor());
        checkColor(expectedForeColor, fill.getForeColor());

        msAssert.areEqual(expectedVisible, fill.getVisible());

        msAssert.areEqual(expectedOpacity, 0.01, fill.getOpacity());
    }

    /// <summary>
    /// Verifies that image set correctly to the shape with a specified index in test document.
    /// </summary>
    private static Shape checkSetImage(String testFileName, String imageFileName, int shapeIndex) throws Exception
    {
        final String DIR = "Model\\FillFormat\\";

        String imagePath = TestUtil.buildTestFileName(Path.combine(DIR, imageFileName));
        byte[] imageBytes = File.readAllBytes(imagePath);

        String testName = Path.combine(DIR, testFileName);
        Document doc = TestUtil.open(testName);
        Shape shape = doc.getFirstSection().getBody().getShapes().get(shapeIndex);

        shape.getFill().setImage(imagePath);

        // Roundtrip and check fill image.
        doc = TestUtil.saveOpen(doc, testName, SaveOptions.createSaveOptions(testFileName), false);

        shape = doc.getFirstSection().getBody().getShapes().get(shapeIndex);
        msAssert.isTrue(ArrayUtil.isArrayEqual(imageBytes, shape.getFill().getImageBytes()));

        return shape;
    }
}

