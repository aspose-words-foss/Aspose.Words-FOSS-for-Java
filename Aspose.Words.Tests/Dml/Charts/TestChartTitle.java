// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import com.aspose.SystemPal;
import org.testng.annotations.AfterMethod;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;


/// <summary>
/// Tests members of the <see cref="ChartTitle"/> and <see cref="ChartAxisTitle"/> classes.
/// </summary>
@Test
public class TestChartTitle
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void testSetUp()
    {
        RandomUtil.reset();

        SystemPal.saveCulture();
        // Set standard culture because default chart title depends on current culture. 
        SystemPal.setStandardCulture();
    }

    @AfterMethod (alwaysRun = true)
    public void testTearDown()
    {
        SystemPal.restoreCulture();
    }

    /// <summary>
    /// Tests the <see cref="ChartTitle.Font"/> property.
    /// </summary>
    @Test
    public void testChartTitleFont() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestChartTitleFont";

        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        // Check font.
        ChartTitle title2 = shapes.get(1).getChart().getTitle();
        Color defaultColor = new Color((89), (89), (89));
        checkFontProperties(title2.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 14.0, 12.0, 0.0,
            false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartTitle title3 = shapes.get(2).getChart().getTitle();
        Color title3Color = new Color((68), (114), (196));
        checkFontProperties(title3.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 10.0,
            12.0, 0.0, false, false, false, false, false, false, title3Color, Underline.NONE);
        ChartTitle title6 = shapes.get(5).getChart().getTitle();
        checkFontProperties(title6.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman", 14.0, 0.0,
            0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartTitle title7 = shapes.get(6).getChart().getTitle();
        Color title7Color = new Color((255), (192), (0));
        checkFontProperties(title7.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman", 16.0,
            0.0, 0.0, false, false, false, false, false, false, title7Color, Underline.NONE);

        // Change chart title from default to explicitly specified, and then check that font properties are defined
        // correctly in this case.
        title2.setText("Explicitly Defined Title (in code)");
        checkFontProperties(title2.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 14.0, 12.0, 0.0,
            false, false, false, false, false, false, defaultColor, Underline.NONE);
        title6.setText("Explicitly Defined Title (in code)");
        checkFontProperties(title6.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 14.0, 0.0,
            0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);

        // Change chart title from explicitly specified to default (setting empty string sets the default title text),
        // and then check that font properties are defined correctly in this case.
        ChartTitle title4 = shapes.get(3).getChart().getTitle();
        checkFontProperties(title4.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 10.0, 12.0, 0.0,
            false, false, false, false, false, false, title3Color, Underline.NONE);
        title4.setText("");
        checkFontProperties(title4.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 10.0, 12.0, 0.0,
            false, false, false, false, false, false, title3Color, Underline.NONE);
        ChartTitle title8 = shapes.get(7).getChart().getTitle();
        checkFontProperties(title8.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman", 16.0,
            0.0, 0.0, false, false, false, false, false, false, title7Color, Underline.NONE);
        title8.setText("");
        checkFontProperties(title8.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman", 16.0,
            0.0, 0.0, false, false, false, false, false, false, title7Color, Underline.NONE);

        // Add new charts at runtime.

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();

        builder.insertChart(ChartType.LINE, 432.0, 200.0);

        builder.insertParagraph();
        builder.insertParagraph();

        Shape shape10 = builder.insertChart(ChartType.LINE, 432.0, 200.0);
        ChartTitle title10 = shape10.getChart().getTitle();
        checkFontProperties(title10.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 14.0, 12.0, 0.0,
            false, false, false, false, false, false, defaultColor, Underline.NONE);
        title10.setText("Explicitly Defined Title (in code)");

        // Change title font.

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            Font font = shape.getChart().getTitle().getFont();
            setFontProperties(font);
            checkFontProperties(font);
        }

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);
        shapes = doc.getFirstSection().getBody().getShapes();

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            Font font = shape.getChart().getTitle().getFont();
            checkFontProperties(font);

            // Check that font format is kept when switching to the default title text.
            shape.getChart().getTitle().setText("");
            checkFontProperties(font);
        }
    }

    /// <summary>
    /// Tests the <see cref="ChartAxisTitle.Font"/> property.
    /// </summary>
    @Test
    public void testChartAxisTitleFont() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\TestChartAxisTitleFont";

        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        // Check font.
        ChartAxisTitle titleX1 = shapes.get(0).getChart().getAxisX().getTitle();
        Color defaultColor = new Color((89), (89), (89));
        checkFontProperties(titleX1.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 10.0, 12.0, 0.0,
            false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartAxisTitle titleX2 = shapes.get(1).getChart().getAxisX().getTitle();
        Color titleX2Color = new Color((237), (125), (49));
        checkFontProperties(titleX2.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 8.0, 12.0, 0.0,
            false, false, false, false, false, false, titleX2Color, Underline.NONE);
        ChartAxisTitle titleY2 = shapes.get(1).getChart().getAxisY().getTitle();
        checkFontProperties(titleY2.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 11.0, 12.0, 0.0,
            false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartAxisTitle titleX3 = shapes.get(2).getChart().getAxisX().getTitle();
        checkFontProperties(titleX3.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman",
            9.0, 0.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartAxisTitle titleX4 = shapes.get(3).getChart().getAxisX().getTitle();
        checkFontProperties(titleX4.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman",
            8.0, 0.0, 0.0, false, false, false, false, false, false, titleX2Color, Underline.NONE);
        ChartAxisTitle titleY4 = shapes.get(3).getChart().getAxisY().getTitle();
        checkFontProperties(titleY4.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman",
            11.0, 0.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);

        // Change chart title from default to explicitly specified, and then check that font properties are defined
        // correctly in this case.
        ChartAxisTitle titleY1 = shapes.get(0).getChart().getAxisY().getTitle();
        titleY1.setShow(true);
        titleY1.setText("Y axis");
        checkFontProperties(titleY1.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman",
            10.0, 12.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartAxisTitle titleY3 = shapes.get(2).getChart().getAxisY().getTitle();
        titleY3.setShow(true);
        titleY3.setText("Y axis");
        checkFontProperties(titleY3.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman",
            9.0, 0.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);

        // Change chart title from explicitly specified to default (setting empty string sets the default title text),
        // and then check that font properties are defined correctly in this case.
        titleY2.setText("");
        checkFontProperties(titleY2.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman", 11.0, 12.0, 0.0,
            false, false, false, false, false, false, defaultColor, Underline.NONE);
        titleY4.setText("");
        checkFontProperties(titleY4.getFont(), "Calibri", "Times New Roman", "Times New Roman", "Times New Roman",
            11.0, 0.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);

        // Add a new chart at runtime.

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToDocumentEnd();

        Shape shape5 = builder.insertChart(ChartType.LINE, 432.0, 200.0);

        ChartAxisTitle titleX5 = shape5.getChart().getAxisX().getTitle();
        titleX5.setShow(true);
        checkFontProperties(titleX5.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman",
            10.0, 12.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);
        titleX5.setText("X axis");
        checkFontProperties(titleX5.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman",
            10.0, 12.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);
        ChartAxisTitle titleY5 = shape5.getChart().getAxisY().getTitle();
        titleY5.setShow(true);
        checkFontProperties(titleY5.getFont(), "Calibri", "Times New Roman", "游明朝", "Times New Roman",
            10.0, 12.0, 0.0, false, false, false, false, false, false, defaultColor, Underline.NONE);

        // Change title font.

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            Font fontX = shape.getChart().getAxisX().getTitle().getFont();
            setFontProperties(fontX);
            checkFontProperties(fontX);

            Font fontY = shape.getChart().getAxisY().getTitle().getFont();
            setFontProperties(fontY);
            checkFontProperties(fontY);
        }

        doc = TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);
        shapes = doc.getFirstSection().getBody().getShapes();

        for (Shape shape : (Iterable<Shape>) shapes)
        {
            Chart chart = shape.getChart();
            Font fontX = chart.getAxisX().getTitle().getFont();
            checkFontProperties(fontX);

            Font fontY = chart.getAxisY().getTitle().getFont();
            checkFontProperties(fontY);

            // Check that font format is kept when switching to the default title text.
            chart.getAxisX().getTitle().setText("");
            chart.getAxisY().getTitle().setText("");
            checkFontProperties(fontX);
            checkFontProperties(fontY);
        }
    }

    /// <summary>
    /// Tests font size of a chart title, in which font properties are not specified directly. MS Word uses
    /// 1.2 * [font size of chart space] value at this case.
    /// </summary>
    @Test
    public void testChartTitleRelativeFontSize() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestChartFormat.docx");
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        checkFontProperties(chart.getTitle().getFont(), "Arial", "Arial", "Times New Roman", "Times New Roman", 11.0, 4.0, 0.4,
            true, false, false, false, false, false, new Color((0xed), (0xed), (0xed)), Underline.NONE);
    }

    /// <summary>
    /// Tests chart and axis title orientation and rotation in a new chart.
    /// </summary>
    @Test
    public void testTitleOrientationInNewChart() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getPageSetup().setLeftMargin(20.0);
        doc.getFirstSection().getPageSetup().setRightMargin(20.0);
        doc.getFirstSection().getPageSetup().setTopMargin(20.0);
        doc.getFirstSection().getPageSetup().setBottomMargin(20.0);

        DocumentBuilder builder = new DocumentBuilder(doc);

        final /*ShapeTextOrientation*/int MAX_ORIENTATION = ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT;
        final int START_ROTATION = -15;

        for (int i = 0; i <= (int)MAX_ORIENTATION; i++)
        {
            /*ShapeTextOrientation*/int orientation = (/*ShapeTextOrientation*/int)i;
            int rotation = START_ROTATION - i;

            Shape shape = builder.insertChart(ChartType.COLUMN, 285.0, 370.0);
            Chart chart = shape.getChart();
            chart.getLegend().setPosition(LegendPosition.NONE);
            ChartTitle title = chart.getTitle();

            title.setOrientation(orientation);
            title.setRotation(rotation);
            title.setText("Orientation " + orientation);
            title.getFont().setSize(8.0);

            for (ChartAxis axis : chart.getAxes())
            {
                ChartAxisTitle axisTitle = axis.getTitle();
                axisTitle.setShow(true);
                axisTitle.setOrientation((/*ShapeTextOrientation*/int)i);
                axisTitle.setRotation(rotation);
                axisTitle.setText("Orientation " + orientation);
                axisTitle.getFont().setSize(6.0);
            }
        }

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTitleOrientationInNewChart.docx", null, true);

        for (int i = 0; i <= (int)MAX_ORIENTATION; i++)
        {
            /*ShapeTextOrientation*/int orientation = (/*ShapeTextOrientation*/int)i;
            int rotation = START_ROTATION - i;

            Shape shape = doc.getFirstSection().getBody().getShapes().get(i);
            Chart chart = shape.getChart();

            checkOrientation(chart.getTitle().getOrientation(), chart.getTitle().getRotation(), orientation, rotation);

            for (ChartAxis axis : chart.getAxes())
                checkOrientation(axis.getTitle().getOrientation(), axis.getTitle().getRotation(), orientation, rotation);
        }
    }

    /// <summary>
    /// Tests chart and axis title orientation and rotation in an existing chart.
    /// </summary>
    @Test
    public void testTitleOrientationInExistingChart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestTitleOrientation.docx");

        Chart chart1 = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        ChartTitle title1 = chart1.getTitle();
        ChartAxisTitle x1Title = chart1.getAxisX().getTitle();
        ChartAxisTitle y1Title = chart1.getAxisY().getTitle();
        ChartAxisTitle secondaryY1Title = chart1.getAxes().get(2).getTitle();

        checkOrientation(title1.getOrientation(), title1.getRotation(), ShapeTextOrientation.HORIZONTAL, 30);
        checkOrientation(x1Title.getOrientation(), x1Title.getRotation(), ShapeTextOrientation.HORIZONTAL, 90);
        checkOrientation(y1Title.getOrientation(), y1Title.getRotation(), ShapeTextOrientation.HORIZONTAL, -90);
        checkOrientation(secondaryY1Title.getOrientation(), secondaryY1Title.getRotation(), ShapeTextOrientation.HORIZONTAL, 90);

        Chart chart2 = doc.getFirstSection().getBody().getShapes().get(1).getChart();
        ChartTitle title2 = chart2.getTitle();
        ChartAxisTitle x2Title = chart2.getAxisX().getTitle();
        ChartAxisTitle y2Title = chart2.getAxisY().getTitle();

        checkOrientation(title2.getOrientation(), title2.getRotation(), ShapeTextOrientation.VERTICAL_FAR_EAST, 0);
        checkOrientation(x2Title.getOrientation(), x2Title.getRotation(), ShapeTextOrientation.HORIZONTAL, 0);
        checkOrientation(y2Title.getOrientation(), y2Title.getRotation(), ShapeTextOrientation.HORIZONTAL, -75);

        title1.setOrientation(ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT);
        x1Title.setOrientation(ShapeTextOrientation.VERTICAL_FAR_EAST);
        x1Title.setRotation(-90);
        y1Title.setOrientation(ShapeTextOrientation.UPWARD);
        y1Title.setRotation(0);
        secondaryY1Title.setRotation(-45);

        checkOrientation(title1.getOrientation(), title1.getRotation(), ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, 30);
        checkOrientation(x1Title.getOrientation(), x1Title.getRotation(), ShapeTextOrientation.VERTICAL_FAR_EAST, -90);
        checkOrientation(y1Title.getOrientation(), y1Title.getRotation(), ShapeTextOrientation.UPWARD, 0);
        checkOrientation(secondaryY1Title.getOrientation(), secondaryY1Title.getRotation(), ShapeTextOrientation.HORIZONTAL, -45);

        title2.setOrientation(ShapeTextOrientation.HORIZONTAL);
        title2.setRotation(45);
        x2Title.setOrientation(ShapeTextOrientation.WORD_ART_VERTICAL);
        x2Title.setRotation(-22);
        y2Title.setOrientation(ShapeTextOrientation.DOWNWARD);

        checkOrientation(title2.getOrientation(), title2.getRotation(), ShapeTextOrientation.HORIZONTAL, 45);
        checkOrientation(x2Title.getOrientation(), x2Title.getRotation(), ShapeTextOrientation.WORD_ART_VERTICAL, -22);
        checkOrientation(y2Title.getOrientation(), y2Title.getRotation(), ShapeTextOrientation.DOWNWARD, -75);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTitleOrientation.docx", null, true);

        chart1 = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        title1 = chart1.getTitle();
        x1Title = chart1.getAxisX().getTitle();
        y1Title = chart1.getAxisY().getTitle();
        secondaryY1Title = chart1.getAxes().get(2).getTitle();

        checkOrientation(title1.getOrientation(), title1.getRotation(), ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT, 30);
        checkOrientation(x1Title.getOrientation(), x1Title.getRotation(), ShapeTextOrientation.VERTICAL_FAR_EAST, -90);
        checkOrientation(y1Title.getOrientation(), y1Title.getRotation(), ShapeTextOrientation.UPWARD, 0);
        checkOrientation(secondaryY1Title.getOrientation(), secondaryY1Title.getRotation(), ShapeTextOrientation.HORIZONTAL, -45);

        chart2 = doc.getFirstSection().getBody().getShapes().get(1).getChart();
        title2 = chart2.getTitle();
        x2Title = chart2.getAxisX().getTitle();
        y2Title = chart2.getAxisY().getTitle();

        checkOrientation(title2.getOrientation(), title2.getRotation(), ShapeTextOrientation.HORIZONTAL, 45);
        checkOrientation(x2Title.getOrientation(), x2Title.getRotation(), ShapeTextOrientation.WORD_ART_VERTICAL, -22);
        checkOrientation(y2Title.getOrientation(), y2Title.getRotation(), ShapeTextOrientation.DOWNWARD, -75);
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QThe property cannot be set in a Word 2016 chart.\\E")
    public void testExceptionWheSettingTitleOrientationOfWord2016Chart() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\Word2016Charts\\Waterfall.docx");
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        // This throws an exception.
        chart.getTitle().setOrientation(ShapeTextOrientation.DOWNWARD);
    }

    /// <summary>
    /// Checks the specified orientation and rotation values.
    /// </summary>
    private static void checkOrientation(/*ShapeTextOrientation*/int actualOrientation, int actualRotation,
        /*ShapeTextOrientation*/int expectedOrientation, int expectedRotation)
    {
        msAssert.areEqual(expectedOrientation, actualOrientation);
        msAssert.areEqual(expectedRotation, actualRotation);
    }

    /// <summary>
    /// Sets some predefined values to the properties of the specified <see cref="Font"/> instance.
    /// </summary>
    private static void setFontProperties(Font font)
    {
        Color color = new Color((60), (120), (220));
        TestChartUtil.setFontProperties(font, "Arial", 11.0, 4.0, 3.0, true, true, true, false, true, false, color,
            Underline.DOT_DOT_DASH, (/*RunVerticalAlignment*/int)(-1), 0);
    }

    /// <summary>
    /// Checks properties of the specified <see cref="Font"/> instance for the values defined in the
    /// <see cref="SetFontProperties(Font)"/> method.
    /// </summary>
    private static void checkFontProperties(Font font)
    {
        Color color = new Color((60), (120), (220));
        checkFontProperties(font, "Arial", "Arial", "Arial", "Arial", 11.0, 4.0, 3.0, true, true, true, false, true,
            false, color, Underline.DOT_DOT_DASH);
    }

    /// <summary>
    /// Checks properties of the specified <see cref="Font"/> instance.
    /// </summary>
    private static void checkFontProperties(Font font, String name, String nameBi, String nameFarEast,
        String nameOther, double size, double kerning, double spacing, boolean bold, boolean italic, boolean strikeThrough,
        boolean doubleStrikeThrough, boolean allCaps, boolean smallCaps, Color color, /*Underline*/int underline)
    {
        TestChartUtil.checkFontProperties(font, name, nameBi, nameFarEast, nameOther, size, kerning, spacing,
            bold, italic, strikeThrough, doubleStrikeThrough, allCaps, smallCaps, color, msColor.Empty, underline,
            RunVerticalAlignment.BASELINE, 0);
    }
}

