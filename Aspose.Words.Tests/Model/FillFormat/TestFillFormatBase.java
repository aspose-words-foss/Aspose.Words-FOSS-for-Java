// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeTest;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// The base class for testing FillFormat object.
/// </summary>
public class TestFillFormatBase
{
    @BeforeTest
    public void oneTimeSetUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Creates test document with a specified markup language.
    /// </summary>
    static DocumentBuilder createDocument(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = new Document(DocumentCtorMode.EMPTY_DOCUMENT_NODE);

        if (markupLanguage == ShapeMarkupLanguage.DML)
        {
            doc.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);
            OoxmlComplianceInfo.markAsHasDrawingExtensions(doc);
        }

        DocumentBuilder builder = new DocumentBuilder(doc);
        return builder;
    }

    /// <summary>
    /// Opens test document with a specified markup language.
    /// </summary>
    static Document open(String fileName, /*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        String testFilePath = getTestPath(fileName, markupLanguage);
        /*LoadFormat*/int lf = markupLanguageToLoadFormat(markupLanguage);

        return TestUtil.open(testFilePath, lf);
    }

    /// <summary>
    /// Returns first fillable node in a specified node.
    /// </summary>
    static Node getFirstFillable(Node rootNode) throws Exception
    {
        NodeMatcher matcher = new NodeTypeMatcher(new /*NodeType*/int[] {NodeType.RUN, NodeType.SHAPE});
        return NodeUtil.findChildOrSelf(rootNode, matcher);
    }

    /// <summary>
    /// Returns FillFormat object of the first fillable node in a specified node.
    /// </summary>
    static Fill getFill(Node rootNode) throws Exception
    {
        Node node = getFirstFillable(rootNode);
        return (node.getNodeType() == NodeType.RUN) ? ((Run)node).getFont().getFill() : ((Shape)node).getFill();
    }

    /// <summary>
    /// Creates SaveOptions by a specified markup language.
    /// </summary>
    static SaveOptions createSaveOptions(/*ShapeMarkupLanguage*/byte markupLanguage)
    {
        /*SaveFormat*/int sf = markupLanguageToSaveFormat(markupLanguage);
        return SaveOptions.createSaveOptions(sf);
    }

    /// <summary>
    /// Converts markup language to extension.
    /// </summary>
    protected static String markupLanguageToExtension(/*ShapeMarkupLanguage*/byte markupLanguage)
    {
        /*LoadFormat*/int lf = markupLanguageToLoadFormat(markupLanguage);
        return FileFormatUtil.loadFormatToExtension(lf);
    }

    /// <summary>
    /// Creates a column chart.
    /// </summary>
    protected static Chart createChart(DocumentBuilder builder) throws Exception
    {
        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection series = chart.getSeries();

        // Delete default generated series.
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2" };

        series.add("Series 1", categories, new double[] { 1.0, 2.0 });
        series.add("Series 2", categories, new double[] { 3.0, 4.0 });

        return chart;
    }

    /// <summary>
    /// Checks color with diagnostic message.
    /// </summary>
    protected static void checkColor(Color expectedColor, Color actualColor)
	{
		checkColor(expectedColor, actualColor, "");
	}

    /// <summary>
    /// Checks color with diagnostic message.
    /// </summary>
    protected static void checkColor(Color expectedColor,Color actualColor, String msg)
    {
        int expectedValue = expectedColor.getRGB();
        int actualValue = actualColor.getRGB();
        msAssert.areEqual(expectedValue, actualValue, msString.format(                "Expected: 0x{0:X8}, Actual: 0x{1:X8}, {2}", expectedValue, actualValue, msg));
    }

    /// <summary>
    /// Checks gradient stop.
    /// </summary>
    protected static void checkGradientStop(
        GradientStop gradientStop,
        Color expectedColor,
        double expectedPosition)
    {
        checkGradientStop(gradientStop, expectedColor, expectedPosition, 0.0);
    }

    /// <summary>
    /// Checks gradient stop.
    /// </summary>
    protected static void checkGradientStop(
        GradientStop gradientStop,
        Color expectedColor,
        double expectedPosition,
        double expectedTransparency)
    {
        checkColor(expectedColor, gradientStop.getColor());
        msAssert.areEqual(expectedPosition, 0.01, gradientStop.getPosition());
        msAssert.areEqual(expectedTransparency, 0.01, gradientStop.getTransparency());
    }

    /// <summary>
    /// Returns true, if a specified gradient style represents linear gradient.
    /// </summary>
    private static boolean isLinearGradient(/*GradientStyle*/int style)
    {
        return (style == GradientStyle.HORIZONTAL) ||
               (style == GradientStyle.VERTICAL) ||
               (style == GradientStyle.DIAGONAL_DOWN) ||
               (style == GradientStyle.DIAGONAL_UP);
    }

    /// <summary>
    /// Returns expected angle degrees for a specified gradient style and variant.
    /// </summary>
    private static double getExpectedAngle(/*GradientStyle*/int style, /*GradientVariant*/int variant)
    {
        switch (style)
        {
            case GradientStyle.HORIZONTAL:
                return G_EXPECTED_HORIZONTAL_ANGLES[(int)variant - 1];
            case GradientStyle.VERTICAL:
                return G_EXPECTED_VERTICAL_ANGLES[(int)variant - 1];
            case GradientStyle.DIAGONAL_UP:
                return G_EXPECTED_DIAGONAL_UP_ANGLES[(int)variant - 1];
            case GradientStyle.DIAGONAL_DOWN:
                return G_EXPECTED_DIAGONAL_DOWN_ANGLES[(int)variant - 1];
            default:
                return 0.0;
        }
    }

    /// <summary>
    /// Checks gradient angle of all shapes in a specified document.
    /// </summary>
    protected static void checkGradientAngleShape(Document doc)
    {
        for (Shape shape : (Iterable<Shape>) doc.getFirstSection().getBody().getShapes())
        {
            /*GradientStyle*/int style = shape.getFill().getGradientStyle();
            /*GradientVariant*/int variant = shape.getFill().getGradientVariant();

            double expectedAngle = getExpectedAngle(style, variant);
            msAssert.areEqual(expectedAngle, shape.getFill().getGradientAngle(), msString.format("[{0}, {1}]", style, variant));

            // Set some arbitrary angle.
            final double ANGLE = 137.0;
            shape.getFill().setGradientAngle(ANGLE);

            // The angle is only available in linear gradients.
            expectedAngle = isLinearGradient(style) ? ANGLE : 0.0;
            msAssert.areEqual(expectedAngle, shape.getFill().getGradientAngle(), msString.format("[{0}, {1}]", style, variant));
        }
    }

    /// <summary>
    /// Checks gradient angle of all styles and variants in text of a specified document.
    /// </summary>
    protected static void checkGradientAngleText(Document doc)
    {
        /*GradientStyle*/int[] styles = new /*GradientStyle*/int[]
        {
            GradientStyle.HORIZONTAL, GradientStyle.VERTICAL, GradientStyle.DIAGONAL_DOWN, GradientStyle.DIAGONAL_UP,
            GradientStyle.FROM_CENTER, GradientStyle.FROM_CORNER
        };
        for (/*GradientStyle*/int style : styles)
        {
            for (int variant = 1; variant <= 4; variant++)
            {
                String runText = msString.format("...{0}_{1}...", GradientStyle.toString(style), variant);
                Run run = TestUtil.getRunWithText(doc, runText);

                double expectedAngle = getExpectedAngle(style, (/*GradientVariant*/int)variant);
                msAssert.areEqual(expectedAngle, run.getFont().getFill().getGradientAngle(), runText);

                // Set some arbitrary angle.
                final double ANGLE = 137.0;
                run.getFont().getFill().setGradientAngle(ANGLE);

                // The angle is only available in linear gradients.
                expectedAngle = isLinearGradient(style) ? ANGLE : 0.0;
                msAssert.areEqual(expectedAngle, run.getFont().getFill().getGradientAngle(), runText);
            }
        }
    }

    /// <summary>
    /// Converts markup language to LoadFormat.
    /// </summary>
    private static /*LoadFormat*/int markupLanguageToLoadFormat(/*ShapeMarkupLanguage*/byte markupLanguage)
    {
        // FOSS We do not have DOC. But we have DOCX with compatibility VML shapes, so load DOCX in any case.
        return (markupLanguage == ShapeMarkupLanguage.DML) ? LoadFormat.DOCX : LoadFormat.DOCX;
    }

    /// <summary>
    /// Converts markup language to SaveFormat.
    /// </summary>
    private static /*SaveFormat*/int markupLanguageToSaveFormat(/*ShapeMarkupLanguage*/byte markupLanguage)
    {
        // FOSS We do not have DOC. But we have DOCX with compatibility VML shapes, so load DOCX in any case.
        return (markupLanguage == ShapeMarkupLanguage.DML) ? SaveFormat.DOCX : SaveFormat.DOCX;
    }

    /// <summary>
    /// Gets full test path by a specified test file name and markup language.
    /// </summary>
    private static String getTestPath(String fileName, /*ShapeMarkupLanguage*/byte markupLanguage)
    {
        return msString.format("Model\\FillFormat\\{0}\\{1}", markupLanguageToString(markupLanguage), fileName);
    }

    /// <summary>
    /// Converts a specified markup language to a string.
    /// </summary>
    private static String markupLanguageToString(/*ShapeMarkupLanguage*/byte markupLanguage)
    {
        return (markupLanguage == ShapeMarkupLanguage.DML) ? "Dml" : "Vml";
    }

    private static /*final*/ double[] G_EXPECTED_HORIZONTAL_ANGLES = new double[] { 90.0, 270.0, 90.0, 270.0 };
    private static /*final*/ double[] G_EXPECTED_VERTICAL_ANGLES = new double[] { 0.0, 180.0, 0.0, 180.0 };
    private static /*final*/ double[] G_EXPECTED_DIAGONAL_DOWN_ANGLES = new double[] { 135.0, 315.0, 135.0, 315.0 };
    private static /*final*/ double[] G_EXPECTED_DIAGONAL_UP_ANGLES = new double[] { 45.0, 225.0, 45.0, 225.0 };
}

