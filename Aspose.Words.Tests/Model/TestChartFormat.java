// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/03/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.MathUtil;


/// <summary>
/// Tests properties and methods of the <see cref="ChartFormat"/> class.
/// </summary>
@Test
public class TestChartFormat
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
    public void testNewSeriesFillFormat() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        ChartSeriesCollection series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        Fill fill1 = series.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.solid();
        fill1.setForeColor(msColor.getPink());
        fill1.setTransparency(0.5);
        checkFill(fill1, FillType.SOLID, new Color((0xff), (0xc0), (0xcb), (0xff)), msColor.Empty, true, 0.5, false, false);

        Fill fill2 = series.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.setForeColor(msColor.getGray());
        checkFill(fill2, FillType.SOLID, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true, 0.0, false, false);

        fill2.setVisible(false);
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        // Check that a fill becomes visible on changing color.
        fill2.setForeColor(Color.YELLOW);
        fill2.setTransparency(0.2);
        checkFill(fill2, FillType.SOLID, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true, 0.2, false, false);

        Fill fill3 = series.get(2).getFormat().getFill();
        fill3.setVisible(false);
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        Fill fill4 = series.get(3).getFormat().getFill();
        fill4.presetTextured(PresetTexture.BROWN_MARBLE);
        checkTexturedFill(fill4, PresetTexture.BROWN_MARBLE, msColor.Empty, msColor.Empty, true, 0.0);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewSeriesFillFormat.docx");
    }


    @Test
    public void testNewDataPointFillFormat() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        ChartSeriesCollection series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        Fill fill1_1 = series.get(0).getDataPoints().get(0).getFormat().getFill();
        checkFill(fill1_1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1_1.solid();
        fill1_1.setForeColor(msColor.getPink());
        fill1_1.setTransparency(0.5);
        checkFill(fill1_1, FillType.SOLID, new Color((0xff), (0xc0), (0xcb), (0xff)),
            msColor.Empty, true, 0.5, false, false);

        Fill fill1_2 = series.get(0).getDataPoints().get(1).getFormat().getFill();
        checkFill(fill1_2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1_2.setForeColor(msColor.getGray());
        checkFill(fill1_2, FillType.SOLID, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true, 0.0, false, false);

        fill1_2.setVisible(false);
        checkFill(fill1_2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        // Check that Visible == false can be replaced with solid fill.
        fill1_2.setForeColor(Color.YELLOW);
        fill1_2.setTransparency(0.2);
        checkFill(fill1_2, FillType.SOLID, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true, 0.2, false, false);

        series.get(1).getDataPoints().get(0).getFormat().getFill().setVisible(false);

        Fill fill3_1 = series.get(2).getDataPoints().get(0).getFormat().getFill();
        checkFill(fill3_1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);
        fill3_1.setForeColor(Color.BLACK);

        Fill fill3_2 = series.get(2).getDataPoints().get(1).getFormat().getFill();
        checkFill(fill3_2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        Fill seriesFill4 = series.get(3).getFormat().getFill();
        checkFill(seriesFill4, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        Fill fill4_1 = series.get(3).getDataPoints().get(0).getFormat().getFill();
        checkFill(fill4_1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);
        fill4_1.setForeColor(msColor.getGreen());

        Fill fill4_2 = series.get(3).getDataPoints().get(1).getFormat().getFill();
        checkFill(fill4_2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        seriesFill4.setForeColor(Color.BLUE);
        seriesFill4.setTransparency(0.3);
        checkFill(seriesFill4, FillType.SOLID, new Color((0), (0), (0xff), (0xff)), msColor.Empty, true, 0.3, false, false);

        // Check that an individual point with non-default formatting is not affected after changing series format.
        checkFill(fill4_1, FillType.SOLID, new Color((0), (0x80), (0), (0xff)),
            msColor.Empty, true, 0.0, false, false);
        // This data point uses format of the parent series: check that its format is changed after series format
        // changing.
        checkFill(fill4_2, FillType.SOLID, new Color((0), (0), (0xff), (0xff)),
            msColor.Empty, true, 0.3, false, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewDataPointFillFormat.docx");
    }


    @Test
    public void testNewMarkerFillFormat() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        series.getMarker().setSize(20);
        ChartDataPointCollection dataPoints = series.getDataPoints();

        Fill seriesFill = series.getMarker().getFormat().getFill();
        checkFill(seriesFill, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        Fill fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.solid();
        fill1.setForeColor(msColor.getPink());
        fill1.setTransparency(0.5);
        checkFill(fill1, FillType.SOLID, new Color((0xff), (0xc0), (0xcb), (0xff)),
            msColor.Empty, true, 0.5, false, false);

        Fill fill2 = dataPoints.get(1).getMarker().getFormat().getFill();
        fill2.setForeColor(Color.YELLOW);
        fill2.setTransparency(0.2);
        checkFill(fill2, FillType.SOLID, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true, 0.2, false, false);

        Fill fill3 = dataPoints.get(2).getMarker().getFormat().getFill();
        fill3.setVisible(false);
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        Fill fill4 = dataPoints.get(3).getMarker().getFormat().getFill();
        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        seriesFill.setForeColor(Color.BLUE);
        seriesFill.setTransparency(0.3);

        checkFill(seriesFill, FillType.SOLID, new Color((0), (0), (0xff), (0xff)),
            msColor.Empty, true, 0.3, false, false);
        checkFill(fill4, FillType.SOLID, new Color((0), (0), (0xff), (0xff)),
            msColor.Empty, true, 0.3, false, false);

        // Check that markers of individual points are not changed after changing series marker.
        checkFill(fill1, FillType.SOLID, new Color((0xff), (0xc0), (0xcb), (0xff)),
            msColor.Empty, true, 0.5, false, false);
        checkFill(fill2, FillType.SOLID, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true, 0.2, false, false);
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewMarkerFillFormat.docx");
    }


    @Test
    public void testNewSeriesStrokeFormat() throws Exception
    {
        Document doc = createDocumentWithTwoSeriesScatterChart();
        ChartSeriesCollection series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        Stroke stroke1 = series.get(0).getFormat().getStroke();
        checkStroke(stroke1, msColor.Empty, msColor.Empty, false,
            1.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        stroke1.setVisible(true);
        stroke1.setForeColor(Color.RED);
        stroke1.setTransparency(0.5);
        stroke1.setWeight(2.5);
        stroke1.setDashStyle(DashStyle.DASH_DOT);
        stroke1.setLineStyle(ShapeLineStyle.DOUBLE);
        stroke1.setJoinStyle(JoinStyle.MITER);
        stroke1.setEndCap(EndCap.SQUARE);
        stroke1.setStartArrowType(ArrowType.OPEN);
        stroke1.setStartArrowLength(ArrowLength.LONG);
        stroke1.setStartArrowWidth(ArrowWidth.NARROW);
        stroke1.setEndArrowType(ArrowType.ARROW);
        stroke1.setEndArrowLength(ArrowLength.LONG);
        stroke1.setEndArrowWidth(ArrowWidth.NARROW);
        checkStroke(stroke1, new Color((0xff), (0), (0), (0xff)), msColor.Empty, true,
            0.5, 2.5, DashStyle.DASH_DOT, ShapeLineStyle.DOUBLE, JoinStyle.MITER, EndCap.SQUARE,
            ArrowType.OPEN, ArrowType.ARROW, ArrowWidth.NARROW, ArrowLength.LONG,
            ArrowWidth.NARROW, ArrowLength.LONG, false);

        Stroke stroke2 = series.get(1).getFormat().getStroke();
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        stroke2.setVisible(true);
        stroke2.setForeColor(Color.BLUE);
        checkStroke(stroke2, new Color((0), (0), (0xff), (0xff)), msColor.Empty, true,
            0.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewSeriesStrokeFormat.docx");
    }


    @Test
    public void testNewDataPointStrokeFormat() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        ChartDataPointCollection dataPoints = series.getDataPoints();

        // A data point represents formatting of a line from the previous point to this one.
        Stroke stroke1 = dataPoints.get(1).getFormat().getStroke();
        checkStroke(stroke1, msColor.Empty, msColor.Empty, false,
            1.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        stroke1.setVisible(true);
        stroke1.setForeColor(msColor.getPink());
        stroke1.setTransparency(0.1);
        stroke1.setWeight(4.0);
        stroke1.setDashStyle(DashStyle.SHORT_DOT);
        stroke1.setLineStyle(ShapeLineStyle.TRIPLE);
        stroke1.setJoinStyle(JoinStyle.BEVEL);
        stroke1.setEndCap(EndCap.SQUARE);
        stroke1.setStartArrowType(ArrowType.DIAMOND);
        stroke1.setStartArrowLength(ArrowLength.SHORT);
        stroke1.setStartArrowWidth(ArrowWidth.WIDE);
        stroke1.setEndArrowType(ArrowType.OVAL);
        stroke1.setEndArrowLength(ArrowLength.SHORT);
        stroke1.setEndArrowWidth(ArrowWidth.WIDE);
        checkStroke(stroke1, new Color((0xff), (0xc0), (0xcb), (0xff)), msColor.Empty, true,
            0.1, 4.0, DashStyle.SHORT_DOT, ShapeLineStyle.TRIPLE, JoinStyle.BEVEL, EndCap.SQUARE,
            ArrowType.DIAMOND, ArrowType.OVAL, ArrowWidth.WIDE, ArrowLength.SHORT,
            ArrowWidth.WIDE, ArrowLength.SHORT, false);

        Stroke stroke2 = dataPoints.get(2).getFormat().getStroke();
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        stroke2.setForeColor(msColor.getGray());
        stroke2.setWeight(4.0);
        checkStroke(stroke2, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true,
            0.0, 4.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        Stroke nonDefinedStroke = dataPoints.get(3).getFormat().getStroke();
        checkStroke(nonDefinedStroke, msColor.Empty, msColor.Empty, false,
            1.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        Stroke seriesStroke = series.getFormat().getStroke();
        checkStroke(seriesStroke, msColor.Empty, msColor.Empty, false,
            1.0, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        seriesStroke.setVisible(true);
        seriesStroke.setWeight(0.5);
        seriesStroke.setDashStyle(DashStyle.SHORT_DASH);
        checkStroke(seriesStroke, msColor.Empty, msColor.Empty, true,
            0.0, 0.5, DashStyle.SHORT_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        // Check that defined line formatting of the data points is not changed after changing line formatting
        // of the entire series.
        checkStroke(stroke1, new Color((0xff), (0xc0), (0xcb), (0xff)), msColor.Empty, true,
            0.1, 4.0, DashStyle.SHORT_DOT, ShapeLineStyle.TRIPLE, JoinStyle.BEVEL, EndCap.SQUARE,
            ArrowType.DIAMOND, ArrowType.OVAL, ArrowWidth.WIDE, ArrowLength.SHORT,
            ArrowWidth.WIDE, ArrowLength.SHORT, false);
        checkStroke(stroke2, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true,
            0.0, 4.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        // If line formatting is not defined, formatting of parent series is taken.
        checkStroke(nonDefinedStroke, msColor.Empty, msColor.Empty, true,
            0.0, 0.5, DashStyle.SHORT_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.ROUND,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewDataPointStrokeFormat.docx");
    }


    @Test
    public void testNewMarkerStrokeFormat() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        series.getMarker().setSize(20);
        ChartDataPointCollection dataPoints = series.getDataPoints();

        Stroke seriesStroke = series.getMarker().getFormat().getStroke();
        checkStroke(seriesStroke, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        Stroke stroke1 = dataPoints.get(0).getMarker().getFormat().getStroke();
        checkStroke(stroke1, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        stroke1.setVisible(true);
        stroke1.setForeColor(Color.RED);
        stroke1.setTransparency(0.3);
        stroke1.setWeight(2.0);
        stroke1.setDashStyle(DashStyle.SHORT_DOT);
        stroke1.setLineStyle(ShapeLineStyle.DOUBLE);
        stroke1.setJoinStyle(JoinStyle.BEVEL);
        stroke1.setEndCap(EndCap.SQUARE);
        checkStroke(stroke1, new Color((0xff), (0), (0), (0xff)), msColor.Empty, true,
            0.3, 2.0, DashStyle.SHORT_DOT, ShapeLineStyle.DOUBLE, JoinStyle.BEVEL, EndCap.SQUARE);

        Stroke stroke2 = dataPoints.get(1).getMarker().getFormat().getStroke();
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        seriesStroke.setVisible(true);
        seriesStroke.setForeColor(Color.YELLOW);
        seriesStroke.setWeight(0.5);
        checkStroke(seriesStroke, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true,
            0.0, 0.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        // Check that format of the first marker is not changed after changing marker formatting of the series.
        checkStroke(stroke1, new Color((0xff), (0), (0), (0xff)), msColor.Empty, true,
            0.3, 2.0, DashStyle.SHORT_DOT, ShapeLineStyle.DOUBLE, JoinStyle.BEVEL, EndCap.SQUARE);

        // Check that format of the second marker is the same as series default marker format.
        checkStroke(stroke2, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true,
            0.0, 0.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewMarkerStrokeFormat.docx");
    }


    @Test
    public void testSeriesPatternedFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        ChartSeriesCollection series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        Fill fill1 = series.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.patterned(PatternType.CROSS);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill2 = series.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.patterned(PatternType.DASHED_DOWNWARD_DIAGONAL);
        fill2.setForeColor(Color.WHITE);
        fill2.setBackColor(Color.BLACK);
        checkPatternedFill(fill2, PatternType.DASHED_DOWNWARD_DIAGONAL, DrColor.WHITE.toNativeColor(),
            DrColor.BLACK.toNativeColor(), 0.0);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestSeriesPatternedFill.docx", null, true);
        series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        fill1 = series.get(0).getFormat().getFill();
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        fill1.patterned(PatternType.DIAGONAL_BRICK);
        checkPatternedFill(fill1, PatternType.DIAGONAL_BRICK, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill3 = series.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.patterned(PatternType.DARK_HORIZONTAL);
        fill3.setForeColor(Color.BLACK);
        fill3.setBackColor(Color.WHITE);
        checkPatternedFill(fill3, PatternType.DARK_HORIZONTAL, DrColor.BLACK.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0);

        Fill fill4 = series.get(3).getFormat().getFill();
        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill4.patterned(PatternType.DOTTED_DIAMOND);
        checkPatternedFill(fill4, PatternType.DOTTED_DIAMOND, new Color((0x4f), (0x81), (0xBD)),
            DrColor.WHITE.toNativeColor(), 0.0);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestSeriesPatternedFill2.docx");
    }

    @Test
    public void testDataPointPatternedFill() throws Exception
    {
        Document doc = createDocumentWithPieChart();
        ChartDataPointCollection dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        Fill fill1 = dataPoints.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.patterned(PatternType.CROSS);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill2 = dataPoints.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.patterned(PatternType.DASHED_DOWNWARD_DIAGONAL);
        fill2.setForeColor(Color.WHITE);
        fill2.setBackColor(Color.BLACK);
        checkPatternedFill(fill2, PatternType.DASHED_DOWNWARD_DIAGONAL, DrColor.WHITE.toNativeColor(),
            DrColor.BLACK.toNativeColor(), 0.0);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataPointPatternedFill.docx", null, true);
        dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        fill1 = dataPoints.get(0).getFormat().getFill();
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        fill1.patterned(PatternType.DIAGONAL_BRICK);
        checkPatternedFill(fill1, PatternType.DIAGONAL_BRICK, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill3 = dataPoints.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.patterned(PatternType.DARK_HORIZONTAL);
        fill3.setForeColor(Color.BLACK);
        fill3.setBackColor(Color.WHITE);
        checkPatternedFill(fill3, PatternType.DARK_HORIZONTAL, DrColor.BLACK.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0);

        Fill fill4 = dataPoints.get(3).getFormat().getFill();
        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill4.patterned(PatternType.DOTTED_DIAMOND);
        checkPatternedFill(fill4, PatternType.DOTTED_DIAMOND, new Color((0x4f), (0x81), (0xBD)),
            DrColor.WHITE.toNativeColor(), 0.0);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestDataPointPatternedFill2.docx");
    }

    @Test
    public void testMarkerPatternedFill() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        series.getMarker().setSize(40);
        series.getMarker().setSymbol(MarkerSymbol.SQUARE);
        ChartDataPointCollection dataPoints = series.getDataPoints();

        Fill fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.patterned(PatternType.CROSS);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill2 = dataPoints.get(1).getMarker().getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.patterned(PatternType.DASHED_DOWNWARD_DIAGONAL);
        fill2.setForeColor(Color.WHITE);
        fill2.setBackColor(Color.BLACK);
        checkPatternedFill(fill2, PatternType.DASHED_DOWNWARD_DIAGONAL, DrColor.WHITE.toNativeColor(),
            DrColor.BLACK.toNativeColor(), 0.0);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestMarkerPatternedFill.docx", null, true);
        dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        fill1.patterned(PatternType.DIAGONAL_BRICK);
        checkPatternedFill(fill1, PatternType.DIAGONAL_BRICK, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill3 = dataPoints.get(2).getMarker().getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.patterned(PatternType.DARK_HORIZONTAL);
        fill3.setForeColor(Color.BLACK);
        fill3.setBackColor(Color.WHITE);
        checkPatternedFill(fill3, PatternType.DARK_HORIZONTAL, DrColor.BLACK.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0);

        Fill fill4 = dataPoints.get(3).getMarker().getFormat().getFill();
        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill4.patterned(PatternType.DOTTED_DIAMOND);
        checkPatternedFill(fill4, PatternType.DOTTED_DIAMOND, new Color((0x4f), (0x81), (0xBD)),
            DrColor.WHITE.toNativeColor(), 0.0);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestMarkerPatternedFill2.docx");
    }


    @Test
    public void testSeriesOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        ChartSeriesCollection series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = series.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = series.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill2.setForeColor(Color.BLACK);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.BLACK.toNativeColor(),
            new Color((0x4f), (0x81), (0xbd)), 0.0, 60.0, 0.5, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestSeriesOneColorGradientFill.docx", null, true);
        series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        fill1 = series.get(0).getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.oneColorGradient(msColor.getGreen(), GradientStyle.VERTICAL, GradientVariant.VARIANT_1, 0.25);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1,
            DrColor.GREEN.toNativeColor(), 0.0, 0.0, 0.0, 0.5, themeProvider);

        Fill fill3 = series.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1,
            DrColor.BLUE.toNativeColor(), 0.0, 90.0, 0.5, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestSeriesOneColorGradientFill2.docx");
    }

    @Test
    public void testDataPointOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithPieChart();
        ChartDataPointCollection dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = dataPoints.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = dataPoints.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill2.setForeColor(Color.BLACK);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.BLACK.toNativeColor(),
            new Color((0x4f), (0x81), (0xbd)), 0.0, 60.0, 0.5, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataPointOneColorGradientFill.docx", null, true);
        dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        fill1 = dataPoints.get(0).getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.oneColorGradient(msColor.getGreen(), GradientStyle.VERTICAL, GradientVariant.VARIANT_1, 0.25);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1,
            DrColor.GREEN.toNativeColor(), 0.0, 0.0, 0.0, 0.5, themeProvider);

        Fill fill3 = dataPoints.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1,
            DrColor.BLUE.toNativeColor(), 0.0, 90.0, 0.5, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestDataPointOneColorGradientFill2.docx");
    }

    @Test
    public void testMarkerOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        series.getMarker().setSize(40);
        series.getMarker().setSymbol(MarkerSymbol.SQUARE);
        ChartDataPointCollection dataPoints = series.getDataPoints();
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = dataPoints.get(1).getMarker().getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill2.setForeColor(Color.BLACK);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.BLACK.toNativeColor(),
            new Color((0x4f), (0x81), (0xbd)), 0.0, 60.0, 0.5, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestMarkerOneColorGradientFill.docx", null, true);
        dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.oneColorGradient(msColor.getGreen(), GradientStyle.VERTICAL, GradientVariant.VARIANT_1, 0.25);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1,
            DrColor.GREEN.toNativeColor(), 0.0, 0.0, 0.0, 0.5, themeProvider);

        Fill fill3 = dataPoints.get(2).getMarker().getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1,
            DrColor.BLUE.toNativeColor(), 0.0, 90.0, 0.5, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestMarkerOneColorGradientFill2.docx");
    }


    @Test
    public void testSeriesTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        ChartSeriesCollection series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = series.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = series.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.twoColorGradient(msColor.getGreen(), Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.GREEN.toNativeColor(),
            DrColor.BLUE.toNativeColor(), 0.0, 60.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestSeriesTwoColorGradientFill.docx", null, true);
        series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();

        fill1 = series.get(0).getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.twoColorGradient(msColor.getBrown(), Color.WHITE, GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, DrColor.BROWN.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 0.0, 0.0, 0.0, themeProvider);

        Fill fill3 = series.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.BLUE.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 90.0, 0.0, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestSeriesTwoColorGradientFill2.docx");
    }

    @Test
    public void testDataPointTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithPieChart();
        ChartDataPointCollection dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = dataPoints.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = dataPoints.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.twoColorGradient(msColor.getGreen(), Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.GREEN.toNativeColor(),
            DrColor.BLUE.toNativeColor(), 0.0, 60.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataPointTwoColorGradientFill.docx", null, true);
        dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        fill1 = dataPoints.get(0).getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.twoColorGradient(msColor.getBrown(), Color.WHITE, GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, DrColor.BROWN.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 0.0, 0.0, 0.0, themeProvider);

        Fill fill3 = dataPoints.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.BLUE.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 90.0, 0.0, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestDataPointTwoColorGradientFill2.docx");
    }

    @Test
    public void testMarkerTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        series.getMarker().setSize(40);
        series.getMarker().setSymbol(MarkerSymbol.SQUARE);
        ChartDataPointCollection dataPoints = series.getDataPoints();
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = dataPoints.get(1).getMarker().getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.twoColorGradient(msColor.getGreen(), Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.GREEN.toNativeColor(),
            DrColor.BLUE.toNativeColor(), 0.0, 60.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestMarkerTwoColorGradientFill.docx", null, true);
        dataPoints = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataPoints();

        fill1 = dataPoints.get(0).getMarker().getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.twoColorGradient(msColor.getBrown(), Color.WHITE, GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, DrColor.BROWN.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 0.0, 0.0, 0.0, themeProvider);

        Fill fill3 = dataPoints.get(2).getMarker().getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.BLUE.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 90.0, 0.0, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestMarkerTwoColorGradientFill2.docx");
    }







    /// <summary>
    /// WORDSNET-24941 Setting color of a gradient stop of data point changes color of entire series
    /// This happened because the <see cref="ChartDataPoint.SpPr"/> property of the data point inherits its value
    /// from <see cref="ChartSeries.DefaultDataPoint"/> and should be materialized before changing the format.
    /// </summary>
    @Test
    public void testSettingDataPointGradientStopColor() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeries series = chart.getSeries().get(0);

        Fill seriesFill = series.getFormat().getFill();
        seriesFill.twoColorGradient(Color.YELLOW, Color.BLUE, GradientStyle.VERTICAL, GradientVariant.VARIANT_4);

        msAssert.areEqual(new Color((0xff), (0xff), (0), (0xff)), seriesFill.getGradientStops().get(0).getColor());

        ChartDataPoint dataPoint1 = series.getDataPoints().get(0);
        Fill pointFill = dataPoint1.getFormat().getFill();

        msAssert.areEqual(new Color((0xff), (0xff), (0), (0xff)), pointFill.getGradientStops().get(0).getColor());

        // Before the fix, this changed color of the gradient stop of the entire series.
        pointFill.getGradientStops().get(0).setColor(msColor.getLightBlue());

        msAssert.areEqual(new Color((0xff), (0xff), (0), (0xff)), seriesFill.getGradientStops().get(0).getColor());
        msAssert.areEqual(new Color((0xad), (0xd8), (0xe6), (0xff)), pointFill.getGradientStops().get(0).getColor());
    }


    /// <summary>
    /// Tests the <see cref="ChartFormat.Fill"/> property of data labels of a created chart.
    /// </summary>
    @Test
    public void testNewDataLabelFillFormat() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        ChartSeriesCollection seriesCollection = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        for (ChartSeries series : seriesCollection)
        {
            series.hasDataLabels(true);
            series.getDataLabels().setShowValue(true);
        }

        Fill fill1_1 = seriesCollection.get(0).getDataLabels().get(0).getFormat().getFill();
        checkFill(fill1_1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1_1.solid();
        fill1_1.setForeColor(msColor.getPink());
        fill1_1.setTransparency(0.5);
        checkFill(fill1_1, FillType.SOLID, new Color((0xff), (0xc0), (0xcb), (0xff)),
            msColor.Empty, true, 0.5, false, false);

        Fill fill1_2 = seriesCollection.get(0).getDataLabels().get(1).getFormat().getFill();
        checkFill(fill1_2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1_2.setForeColor(msColor.getGray());
        checkFill(fill1_2, FillType.SOLID, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true, 0.0, false, false);

        fill1_2.setVisible(false);
        checkFill(fill1_2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        // Check that Visible == false can be replaced with solid fill.
        fill1_2.setForeColor(Color.YELLOW);
        fill1_2.setTransparency(0.2);
        checkFill(fill1_2, FillType.SOLID, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true, 0.2, false, false);

        Fill labelsFill2 = seriesCollection.get(1).getDataLabels().getFormat().getFill();
        labelsFill2.solid(Color.YELLOW);
        checkFill(labelsFill2, FillType.SOLID, new Color((0xff), (0xff), (0), (0xff)), msColor.Empty, true, 0.0, false, false);
        labelsFill2.setVisible(false);

        Fill fill3_1 = seriesCollection.get(2).getDataLabels().get(0).getFormat().getFill();
        checkFill(fill3_1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);
        fill3_1.setForeColor(msColor.getAqua());

        Fill fill3_2 = seriesCollection.get(2).getDataLabels().get(1).getFormat().getFill();
        checkFill(fill3_2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        Fill labelsFill4 = seriesCollection.get(3).getDataLabels().getFormat().getFill();
        checkFill(labelsFill4, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        Fill fill4_1 = seriesCollection.get(3).getDataLabels().get(0).getFormat().getFill();
        checkFill(fill4_1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);
        fill4_1.setForeColor(msColor.getLightGreen());

        Fill fill4_2 = seriesCollection.get(3).getDataLabels().get(1).getFormat().getFill();
        checkFill(fill4_2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        labelsFill4.setForeColor(msColor.getLightBlue());
        labelsFill4.setTransparency(0.3);
        checkFill(labelsFill4, FillType.SOLID, new Color((0xad), (0xd8), (0xe6), (0xff)),
            msColor.Empty, true, 0.3, false, false);

        // Check that an individual label with non-default formatting is not affected after changing series format.
        checkFill(fill4_1, FillType.SOLID, new Color((0x90), (0xee), (0x90), (0xff)),
            msColor.Empty, true, 0.0, false, false);
        // This data label uses format of the parent collection: check that its format is changed after collection
        // format changing.
        checkFill(fill4_2, FillType.SOLID, new Color((0xad), (0xd8), (0xe6), (0xff)),
            msColor.Empty, true, 0.3, false, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewDataLabelFillFormat.docx");
    }


    /// <summary>
    /// Tests the <see cref="ChartFormat.Stroke"/> property of data labels of a created chart.
    /// </summary>
    @Test
    public void testNewDataLabelStrokeFormat() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        ChartSeries series = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        series.hasDataLabels(true);
        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowValue(true);
        dataLabels.getFont().setSize(32.0);

        Stroke stroke1 = dataLabels.get(0).getFormat().getStroke();
        checkStroke(stroke1, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        stroke1.setVisible(true);
        stroke1.setForeColor(msColor.getPink());
        stroke1.setTransparency(0.1);
        stroke1.setWeight(4.0);
        stroke1.setDashStyle(DashStyle.SHORT_DOT);
        stroke1.setLineStyle(ShapeLineStyle.TRIPLE);
        stroke1.setJoinStyle(JoinStyle.BEVEL);
        stroke1.setEndCap(EndCap.SQUARE);
        stroke1.setStartArrowType(ArrowType.DIAMOND);
        stroke1.setStartArrowLength(ArrowLength.SHORT);
        stroke1.setStartArrowWidth(ArrowWidth.WIDE);
        stroke1.setEndArrowType(ArrowType.OVAL);
        stroke1.setEndArrowLength(ArrowLength.SHORT);
        stroke1.setEndArrowWidth(ArrowWidth.WIDE);
        checkStroke(stroke1, new Color((0xff), (0xc0), (0xcb), (0xff)), msColor.Empty, true,
            0.1, 4.0, DashStyle.SHORT_DOT, ShapeLineStyle.TRIPLE, JoinStyle.BEVEL, EndCap.SQUARE,
            ArrowType.DIAMOND, ArrowType.OVAL, ArrowWidth.WIDE, ArrowLength.SHORT,
            ArrowWidth.WIDE, ArrowLength.SHORT, false);

        Stroke stroke2 = dataLabels.get(1).getFormat().getStroke();
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        stroke2.setForeColor(msColor.getGray());
        stroke2.setWeight(4.0);
        checkStroke(stroke2, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true,
            0.0, 4.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        Stroke nonDefinedStroke = dataLabels.get(2).getFormat().getStroke();
        checkStroke(nonDefinedStroke, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        Stroke collectionStroke = dataLabels.getFormat().getStroke();
        checkStroke(collectionStroke, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        collectionStroke.setVisible(true);
        collectionStroke.setWeight(0.5);
        collectionStroke.setDashStyle(DashStyle.SHORT_DASH);
        checkStroke(collectionStroke, msColor.Empty, msColor.Empty, true,
            0.0, 0.5, DashStyle.SHORT_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        // Check that defined line formatting of the data labels is not changed after changing line formatting
        // of the collection.
        checkStroke(stroke1, new Color((0xff), (0xc0), (0xcb), (0xff)), msColor.Empty, true,
            0.1, 4.0, DashStyle.SHORT_DOT, ShapeLineStyle.TRIPLE, JoinStyle.BEVEL, EndCap.SQUARE,
            ArrowType.DIAMOND, ArrowType.OVAL, ArrowWidth.WIDE, ArrowLength.SHORT,
            ArrowWidth.WIDE, ArrowLength.SHORT, false);
        checkStroke(stroke2, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true,
            0.0, 4.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        // If line formatting is not defined, formatting of parent collection is taken.
        checkStroke(nonDefinedStroke, msColor.Empty, msColor.Empty, true,
            0.0, 0.5, DashStyle.SHORT_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewDataLabelStrokeFormat.docx");
    }

    /// <summary>
    /// Tests applying a patterned fill to data labels.
    /// </summary>
    @Test
    public void testDataLabelPatternedFill() throws Exception
    {
        Document doc = createDocumentWithPieChart();
        ChartDataLabelCollection dataLabels = showDataLabels(doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0));

        Fill fill1 = dataLabels.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.patterned(PatternType.CROSS);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill2 = dataLabels.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.patterned(PatternType.DASHED_DOWNWARD_DIAGONAL);
        fill2.setForeColor(Color.WHITE);
        fill2.setBackColor(msColor.getGray());
        checkPatternedFill(fill2, PatternType.DASHED_DOWNWARD_DIAGONAL, DrColor.WHITE.toNativeColor(),
            DrColor.GRAY.toNativeColor(), 0.0);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataLabelPatternedFill.docx", null, true);
        dataLabels = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataLabels();

        Fill collectionFill = dataLabels.getFormat().getFill();
        checkFill(collectionFill, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);
        collectionFill.patterned(PatternType.LARGE_GRID, Color.BLUE, msColor.getOrange());
        checkFill(collectionFill, FillType.PATTERNED, DrColor.BLUE.toNativeColor(),
            DrColor.ORANGE.toNativeColor(), true, 0.0, false, false);

        fill1 = dataLabels.get(0).getFormat().getFill();
        checkPatternedFill(fill1, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        fill1.patterned(PatternType.DIAGONAL_BRICK);
        checkPatternedFill(fill1, PatternType.DIAGONAL_BRICK, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fill3 = dataLabels.get(2).getFormat().getFill();
        // Inherits format from the collection.
        checkFill(fill3, FillType.PATTERNED, DrColor.BLUE.toNativeColor(),
            DrColor.ORANGE.toNativeColor(), true, 0.0, false, false);

        fill3.patterned(PatternType.DARK_HORIZONTAL);
        fill3.setForeColor(Color.BLACK);
        fill3.setBackColor(Color.WHITE);
        checkPatternedFill(fill3, PatternType.DARK_HORIZONTAL, DrColor.BLACK.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0);

        Fill fill4 = dataLabels.get(3).getFormat().getFill();
        // Inherits format from the collection.
        checkFill(fill4, FillType.PATTERNED, DrColor.BLUE.toNativeColor(),
            DrColor.ORANGE.toNativeColor(), true, 0.0, false, false);

        fill4.patterned(PatternType.DOTTED_DIAMOND);
        checkPatternedFill(fill4, PatternType.DOTTED_DIAMOND, DrColor.BLUE.toNativeColor(),
            DrColor.ORANGE.toNativeColor(), 0.0);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestDataLabelPatternedFill2.docx");
    }

    /// <summary>
    /// Tests applying a one-color gradient fill to data labels.
    /// </summary>
    @Test
    public void testDataLabelOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithPieChart();
        ChartDataLabelCollection dataLabels = showDataLabels(doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0));
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = dataLabels.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = dataLabels.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fill2.setForeColor(Color.BLACK);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.BLACK.toNativeColor(),
            new Color((0x4f), (0x81), (0xbd)), 0.0, 60.0, 0.5, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataLabelOneColorGradientFill.docx", null, true);
        dataLabels = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataLabels();

        fill1 = dataLabels.get(0).getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.oneColorGradient(msColor.getGreen(), GradientStyle.VERTICAL, GradientVariant.VARIANT_1, 0.25);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1,
            DrColor.GREEN.toNativeColor(), 0.0, 0.0, 0.0, 0.5, themeProvider);

        Fill fill3 = dataLabels.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1,
            DrColor.BLUE.toNativeColor(), 0.0, 90.0, 0.5, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestDataLabelOneColorGradientFill2.docx");
    }

    /// <summary>
    /// Tests applying a two-color gradient fill to data labels.
    /// </summary>
    @Test
    public void testDataLabelTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithPieChart();
        ChartDataLabelCollection dataLabels = showDataLabels(doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0));
        IThemeProvider themeProvider = doc.getTheme();

        Fill fill1 = dataLabels.get(0).getFormat().getFill();
        checkFill(fill1, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill1.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill1.setForeColor(Color.RED);
        fill1.setBackColor(Color.YELLOW);
        fill1.setTransparency(0.5);
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fill2 = dataLabels.get(1).getFormat().getFill();
        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill2.twoColorGradient(msColor.getGreen(), Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fill2.setGradientAngle(60.0);
        checkGradientFill(fill2, GradientStyle.NONE, GradientVariant.NONE, DrColor.GREEN.toNativeColor(),
            DrColor.BLUE.toNativeColor(), 0.0, 60.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestDataLabelTwoColorGradientFill.docx", null, true);
        dataLabels = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0).getDataLabels();

        fill1 = dataLabels.get(0).getFormat().getFill();
        checkGradientFill(fill1, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fill1.twoColorGradient(msColor.getBrown(), Color.WHITE, GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill1, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, DrColor.BROWN.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 0.0, 0.0, 0.0, themeProvider);

        Fill fill3 = dataLabels.get(2).getFormat().getFill();
        checkFill(fill3, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        fill3.setForeColor(Color.BLUE);
        fill3.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        checkGradientFill(fill3, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.BLUE.toNativeColor(),
            DrColor.WHITE.toNativeColor(), 0.0, 90.0, 0.0, 0.0, themeProvider);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestDataLabelTwoColorGradientFill2.docx");
    }


    /// <summary>
    /// Tests that all <see cref="ChartShapeType"/> elements are correctly displayed in data labels.
    /// </summary>
    @Test
    public void testAllChartShapeTypes() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getPageSetup().setOrientation(Orientation.LANDSCAPE);

        DocumentBuilder builder = new DocumentBuilder(doc);
        Shape shape = builder.insertChart(ChartType.SCATTER, 648.0, 432.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection seriesCollection = chart.getSeries();
        seriesCollection.clear();

        /*ChartShapeType*/int[] shapeTypes = (/*ChartShapeType*/int[])ChartShapeType.getValues();
        double[] x = new double[shapeTypes.length];
        double[] y = new double[shapeTypes.length];

        // Fill data.
        for (int i = 0; i < shapeTypes.length; i++)
        {
            x[i] = i + 1;
            final int POINT_LINE_COUNT = 8;
            y[i] = 4 * (i % POINT_LINE_COUNT) + 1;
        }

        ChartSeries series = seriesCollection.add("Series 1", x, y);

        series.hasDataLabels(true);
        series.getDataLabels().setShowValue(true);
        series.getDataLabels().getLabelPr().setProperty(DmlChartDataLabelAttrs.D_LBL_POS, ChartDataLabelPosition.ABOVE);

        // Generate callouts.
        for (int i = 0; i < shapeTypes.length; i++)
        {
            ChartFormat format = series.getDataLabels().get(i).getFormat();
            format.getStroke().setForeColor(msColor.getDarkGray());
            format.setShapeType(shapeTypes[i]);

            msAssert.areEqual(shapeTypes[i], format.getShapeType());
        }

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestAllChartShapeTypes.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartFormat.Fill"/> property of axis tick labels of an existing chart.
    /// </summary>
    @Test
    public void testAxisTickLabelFillFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestAxisFormat.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        Chart chart1 = shapes.get(0).getChart();
        Chart chart2 = shapes.get(1).getChart();
        Chart chart3 = shapes.get(2).getChart();

        Fill patternFill = chart1.getAxisX().getFormat().getFill();
        checkPatternedFill(patternFill, PatternType.PERCENT_50, new Color((0x8d), (0xa9), (0xdb), (0xff)),
            new Color((0xff), (0xff), (0xff), (0xff)), 0.0);

        Fill texturedFill = chart1.getAxisY().getFormat().getFill();
        checkFill(texturedFill, FillType.TEXTURED, msColor.Empty, msColor.Empty, true, 0.0, true, true);

        Fill gradientFill = chart2.getAxisX().getFormat().getFill();
        checkFill(gradientFill, FillType.GRADIENT, new Color((0xbb), (0xd6), (0xee), (0xff)),
            new Color((0x25), (0x5d), (0x91), (0xff)), true, 0.0, true, false);

        Fill solidFill = chart2.getAxisY().getFormat().getFill();
        checkFill(solidFill, FillType.SOLID, new Color((0xb3), (0xc6), (0xe7), (0xff)),
            msColor.Empty, true, 0.15, false, false);

        Fill noFill = chart2.getAxes().get(2).getFormat().getFill();
        checkFill(noFill, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        // Word 2016 charts do not support fill of axis tick labels: check that the fill is empty.
        Fill fill2016 = chart3.getAxisX().getFormat().getFill();
        checkFill(fill2016, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);

        // Change format.

        patternFill.setForeColor(Color.BLACK);
        patternFill.setBackColor(msColor.getLime());
        checkPatternedFill(patternFill, PatternType.PERCENT_50, new Color((0), (0), (0), (0xff)),
            new Color((0), (0xff), (0), (0xff)), 0.0);

        texturedFill.setOpacity(0.55);
        checkFill(texturedFill, FillType.TEXTURED, msColor.Empty, msColor.Empty, true, 0.45, true, true);

        gradientFill.setForeColor(Color.BLUE);
        gradientFill.setBackColor(msColor.getGreen());
        checkFill(gradientFill, FillType.GRADIENT, new Color((0), (0), (0xff), (0xff)),
            new Color((0), (0x80), (0), (0xff)), true, 0.0, true, false);

        solidFill.setTransparency(0.0);
        checkFill(solidFill, FillType.SOLID, new Color((0xb3), (0xc6), (0xe7), (0xff)),
            msColor.Empty, true, 0.0, false, false);

        noFill.setForeColor(Color.RED);
        checkFill(noFill, FillType.SOLID, new Color((0xff), (0), (0), (0xff)),
            msColor.Empty, true, 0.0, false, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestAxisTickLabelFillFormat.docx");
    }

    /// <summary>
    /// Tests that an exception is generated when changing fill of axis tick labels of a Word 2016 chart.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QFill is not supported by this chart element.\\E")
    public void testChangingFillOfWord2016ChartTickLabel() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestAxisFormat.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        Chart chart3 = shapes.get(2).getChart();

        chart3.getAxisX().getFormat().getFill().setForeColor(Color.RED);
    }

    /// <summary>
    /// Tests the <see cref="ChartFormat.Fill"/> property of axis tick labels of a created chart.
    /// </summary>
    @Test
    public void testNewChartAxisTickLabelFillFormat() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        Fill xFill = chart.getAxisX().getFormat().getFill();
        checkFill(xFill, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        xFill.twoColorGradient(Color.YELLOW, msColor.getLime(), GradientStyle.DIAGONAL_DOWN, GradientVariant.VARIANT_1);
        xFill.setTransparency(0.5);
        checkFill(xFill, FillType.GRADIENT, new Color((0xff), (0xff), (0x00), (0xff)),
            new Color((0), (0xff), (0x00), (0xff)), true, 0.5, true, false);

        Fill yFill = chart.getAxisY().getFormat().getFill();
        checkFill(yFill, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        yFill.setForeColor(msColor.getLightGray());
        checkFill(yFill, FillType.SOLID, new Color((0xd3), (0xd3), (0xd3), (0xff)), msColor.Empty, true, 0.0, false, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewChartAxisTickLabelFillFormat.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartFormat.Stroke"/> property of axes of an existing chart.
    /// </summary>
    @Test
    public void testAxisStrokeFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestAxisFormat.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        Chart chart1 = shapes.get(0).getChart();
        Chart chart2 = shapes.get(1).getChart();
        Chart chart3 = shapes.get(2).getChart();

        Stroke strokeX1 = chart1.getAxisX().getFormat().getStroke();
        checkStroke(strokeX1, new Color((0xbb), (0xd6), (0xee), (0xff)), new Color((0x25), (0x5d), (0x91), (0xff)), true,
            0.0, 5.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        Stroke strokeY1 = chart1.getAxisY().getFormat().getStroke();
        checkStroke(strokeY1, new Color((0x2f), (0x54), (0x96), (0xff)), msColor.Empty, true,
            0.0, 1.25, DashStyle.SHORT_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT, ArrowType.NONE,
            ArrowType.STEALTH, ArrowWidth.MEDIUM, ArrowLength.MEDIUM, ArrowWidth.MEDIUM, ArrowLength.LONG, false);

        Stroke strokeX2 = chart2.getAxisX().getFormat().getStroke();
        checkStroke(strokeX2, new Color((0x2f), (0x54), (0x96), (0xff)), msColor.Empty, true,
            0.5, 3.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT, ArrowType.OVAL,
            ArrowType.OVAL, ArrowWidth.MEDIUM, ArrowLength.MEDIUM, ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        Stroke strokeY2 = chart2.getAxisY().getFormat().getStroke();
        checkStroke(strokeY2, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        Stroke strokeYR2 = chart2.getAxes().get(2).getFormat().getStroke();
        checkStroke(strokeYR2, new Color((0x2f), (0x54), (0x96), (0xff)), msColor.Empty, true,
            0.0, 1.75, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        Stroke strokeX3 = chart3.getAxisX().getFormat().getStroke();
        checkStroke(strokeX3, new Color((0xbf), (0x90), (0x00), (0xff)), new Color((0xff), (0xc0), (0x00), (0xff)), true,
            0.0, 5.25, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        Stroke strokeY3 = chart3.getAxisY().getFormat().getStroke();
        checkStroke(strokeY3, new Color((0x2f), (0x54), (0x96), (0xff)), msColor.Empty, true,
            0.0, 2.5, DashStyle.DASH, ShapeLineStyle.SINGLE, JoinStyle.BEVEL, EndCap.SQUARE);

        // Change format.

        strokeX1.setForeColor(Color.RED);
        strokeX1.setOpacity(0.2);
        checkStroke(strokeX1, new Color((0xff), (0), (0), (0xff)), new Color((0x25), (0x5d), (0x91), (0xff)), true,
            0.8, 5.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        strokeY1.setVisible(false);
        checkStroke(strokeY1, msColor.Empty, msColor.Empty, false,
            1.0, 1.25, DashStyle.SHORT_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT, ArrowType.NONE,
            ArrowType.STEALTH, ArrowWidth.MEDIUM, ArrowLength.MEDIUM, ArrowWidth.MEDIUM, ArrowLength.LONG, false);

        strokeX2.setForeColor(msColor.getGreen());
        strokeX2.setDashStyle(DashStyle.LONG_DASH_DOT_DOT);
        strokeX2.setLineStyle(ShapeLineStyle.DOUBLE);
        strokeX2.setJoinStyle(JoinStyle.BEVEL);
        strokeX2.setEndCap(EndCap.SQUARE);
        strokeX2.setStartArrowType(ArrowType.ARROW);
        strokeX2.setStartArrowLength(ArrowLength.SHORT);
        strokeX2.setStartArrowWidth(ArrowWidth.WIDE);
        strokeX2.setEndArrowType(ArrowType.ARROW);
        strokeX2.setEndArrowLength(ArrowLength.SHORT);
        strokeX2.setEndArrowWidth(ArrowWidth.WIDE);
        checkStroke(strokeX2, new Color((0), (0x80), (0), (0xff)), msColor.Empty, true,
            0.0, 3.0, DashStyle.LONG_DASH_DOT_DOT, ShapeLineStyle.DOUBLE, JoinStyle.BEVEL, EndCap.SQUARE, ArrowType.ARROW,
            ArrowType.ARROW, ArrowWidth.WIDE, ArrowLength.SHORT, ArrowWidth.WIDE, ArrowLength.SHORT, false);

        strokeY2.setVisible(true);
        checkStroke(strokeY2, msColor.Empty, msColor.Empty, true,
            0.0, 0.75, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        strokeYR2.getFill().twoColorGradient(Color.YELLOW, msColor.getLime(), GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2);
        strokeYR2.setTransparency(0.25);
        checkStroke(strokeYR2, new Color((0xff), (0xff), (0), (0xff)), new Color((0), (0xff), (0), (0xff)), true,
            0.25, 1.75, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        strokeX3.setDashStyle(DashStyle.LONG_DASH);
        strokeX3.setLineStyle(ShapeLineStyle.TRIPLE);
        strokeX3.setWeight(10.0);
        checkStroke(strokeX3, new Color((0xbf), (0x90), (0x00), (0xff)), new Color((0xff), (0xc0), (0x00), (0xff)), true,
            0.0, 10.0, DashStyle.LONG_DASH, ShapeLineStyle.TRIPLE, JoinStyle.ROUND, EndCap.FLAT);

        strokeY3.setForeColor(msColor.getOrange());
        strokeY3.setJoinStyle(JoinStyle.MITER);
        strokeY3.setEndCap(EndCap.ROUND);
        strokeY3.setStartArrowType(ArrowType.DIAMOND);
        strokeY3.setStartArrowLength(ArrowLength.LONG);
        strokeY3.setStartArrowWidth(ArrowWidth.NARROW);
        strokeY3.setEndArrowType(ArrowType.DIAMOND);
        strokeY3.setEndArrowLength(ArrowLength.LONG);
        strokeY3.setEndArrowWidth(ArrowWidth.NARROW);
        checkStroke(strokeY3, new Color((0xff), (0xa5), (0), (0xff)), msColor.Empty, true,
            0.0, 2.5, DashStyle.DASH, ShapeLineStyle.SINGLE, JoinStyle.MITER, EndCap.ROUND, ArrowType.DIAMOND,
            ArrowType.DIAMOND, ArrowWidth.NARROW, ArrowLength.LONG, ArrowWidth.NARROW, ArrowLength.LONG, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestAxisStrokeFormat.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartFormat.Stroke"/> property of axes of a created chart.
    /// </summary>
    @Test
    public void testNewChartAxisStrokeFormat() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        Stroke strokeX = chart.getAxisX().getFormat().getStroke();
        checkStroke(strokeX, new Color((0xd8), (0xd8), (0xd8), (0xff)), msColor.Empty, true,
            0.0, 0.75, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        strokeX.setForeColor(msColor.getGray());
        strokeX.setWeight(2.0);
        checkStroke(strokeX, new Color((0x80), (0x80), (0x80), (0xff)), msColor.Empty, true,
            0.0, 2.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        Stroke strokeY = chart.getAxisY().getFormat().getStroke();
        checkStroke(strokeY, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        strokeY.setVisible(true);
        strokeY.setForeColor(Color.BLUE);
        strokeY.setTransparency(0.1);
        strokeY.setWeight(2.0);
        strokeY.setDashStyle(DashStyle.SHORT_DOT);
        strokeY.setLineStyle(ShapeLineStyle.DOUBLE);
        strokeY.setJoinStyle(JoinStyle.BEVEL);
        strokeY.setEndCap(EndCap.SQUARE);
        strokeY.setStartArrowType(ArrowType.OVAL);
        strokeY.setStartArrowLength(ArrowLength.SHORT);
        strokeY.setStartArrowWidth(ArrowWidth.NARROW);
        strokeY.setEndArrowType(ArrowType.STEALTH);
        strokeY.setEndArrowLength(ArrowLength.LONG);
        strokeY.setEndArrowWidth(ArrowWidth.NARROW);
        checkStroke(strokeY, new Color((0x00), (0x00), (0xff), (0xff)), msColor.Empty, true,
            0.1, 2.0, DashStyle.SHORT_DOT, ShapeLineStyle.DOUBLE, JoinStyle.BEVEL, EndCap.SQUARE,
            ArrowType.OVAL, ArrowType.STEALTH, ArrowWidth.NARROW, ArrowLength.SHORT,
            ArrowWidth.NARROW, ArrowLength.LONG, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewChartAxisStrokeFormat.docx");
    }

    /// <summary>
    /// Tests applying a patterned fill to axis tick labels.
    /// </summary>
    @Test
    public void testAxisTickLabelPatternedFill() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        chart.getAxisX().getTickLabels().getFont().setSize(24.0);
        chart.getAxisY().getTickLabels().getFont().setSize(24.0);

        Fill fillX = chart.getAxisX().getFormat().getFill();
        checkFill(fillX, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        fillX.patterned(PatternType.CROSS);
        fillX.setForeColor(Color.RED);
        fillX.setBackColor(Color.YELLOW);
        fillX.setTransparency(0.5);
        checkPatternedFill(fillX, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        Fill fillY = chart.getAxisY().getFormat().getFill();
        checkFill(fillY, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        fillY.patterned(PatternType.DASHED_DOWNWARD_DIAGONAL);
        fillY.setForeColor(Color.WHITE);
        fillY.setBackColor(msColor.getGray());
        checkPatternedFill(fillY, PatternType.DASHED_DOWNWARD_DIAGONAL, DrColor.WHITE.toNativeColor(),
            DrColor.GRAY.toNativeColor(), 0.0);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestAxisTickLabelPatternedFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        fillX = chart.getAxisX().getFormat().getFill();
        checkPatternedFill(fillX, PatternType.CROSS, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5);

        fillY = chart.getAxisY().getFormat().getFill();
        checkPatternedFill(fillY, PatternType.DASHED_DOWNWARD_DIAGONAL, DrColor.WHITE.toNativeColor(),
            DrColor.GRAY.toNativeColor(), 0.0);
    }

    /// <summary>
    /// Tests applying a one-color gradient fill to axis tick labels.
    /// </summary>
    @Test
    public void testAxisTickLabelOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        chart.getAxisX().getTickLabels().getFont().setSize(24.0);
        chart.getAxisY().getTickLabels().getFont().setSize(24.0);
        IThemeProvider themeProvider = doc.getTheme();

        Fill fillX = chart.getAxisX().getFormat().getFill();
        checkFill(fillX, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        fillX.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fillX.setForeColor(Color.RED);
        fillX.setBackColor(Color.YELLOW);
        fillX.setTransparency(0.5);
        checkGradientFill(fillX, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fillY = chart.getAxisY().getFormat().getFill();
        checkFill(fillY, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        fillY.oneColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.75);
        fillY.setForeColor(Color.BLACK);
        fillY.setGradientAngle(60.0);
        checkGradientFill(fillY, GradientStyle.NONE, GradientVariant.NONE, DrColor.BLACK.toNativeColor(),
            new Color((0x4f), (0x81), (0xbd)), 0.0, 60.0, 0.5, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestAxisTickLabelOneColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        themeProvider = doc.getTheme();

        fillX = chart.getAxisX().getFormat().getFill();
        checkGradientFill(fillX, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fillY = chart.getAxisY().getFormat().getFill();
        checkGradientFill(fillY, GradientStyle.NONE, GradientVariant.NONE, DrColor.BLACK.toNativeColor(),
            new Color((0x4f), (0x81), (0xbd)), 0.0, 60.0, 0.5, 0.0, themeProvider);
    }

    /// <summary>
    /// Tests applying a two-color gradient fill to axis tick labels.
    /// </summary>
    @Test
    public void testAxisTickLabelTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        chart.getAxisX().getTickLabels().getFont().setSize(24.0);
        chart.getAxisY().getTickLabels().getFont().setSize(24.0);
        chart.getAxisY().getTickLabels().getFont().setColor(Color.WHITE);
        IThemeProvider themeProvider = doc.getTheme();

        Fill fillX = chart.getAxisX().getFormat().getFill();
        checkFill(fillX, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        fillX.twoColorGradient(GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fillX.setForeColor(Color.RED);
        fillX.setBackColor(Color.YELLOW);
        fillX.setTransparency(0.5);
        checkGradientFill(fillX, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        Fill fillY = chart.getAxisY().getFormat().getFill();
        checkFill(fillY, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);

        fillY.twoColorGradient(msColor.getGreen(), Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1);
        fillY.setGradientAngle(60.0);
        checkGradientFill(fillY, GradientStyle.NONE, GradientVariant.NONE, DrColor.GREEN.toNativeColor(),
            DrColor.BLUE.toNativeColor(), 0.0, 60.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestAxisTickLabelTwoColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        themeProvider = doc.getTheme();

        fillX = chart.getAxisX().getFormat().getFill();
        checkGradientFill(fillX, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, DrColor.RED.toNativeColor(),
            DrColor.YELLOW.toNativeColor(), 0.5, 90.0, 0.0, 0.0, themeProvider);

        fillY = chart.getAxisY().getFormat().getFill();
        checkGradientFill(fillY, GradientStyle.NONE, GradientVariant.NONE, DrColor.GREEN.toNativeColor(),
            DrColor.BLUE.toNativeColor(), 0.0, 60.0, 0.0, 0.0, themeProvider);
    }



    /// <summary>
    /// WORDSNET-23584 Support the "callout" data labels
    /// Tests roundtrip and changing shape type for individual data labels and the entire collection.
    /// </summary>
    @Test
    public void test23584() throws Exception
    {
        final String FILE_NAME = "Model\\Charts\\Test23584.docx";
        Document doc = TestUtil.open(FILE_NAME);
        ChartSeriesCollection seriesCollection = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        ChartDataLabelCollection dataLabels1 = seriesCollection.get(0).getDataLabels();

        msAssert.areEqual(ChartShapeType.DEFAULT, dataLabels1.getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.WEDGE_RECT_CALLOUT, dataLabels1.get(0).getFormat().getShapeType());

        doc = TestUtil.saveOpen(doc, FILE_NAME);
        seriesCollection = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        dataLabels1 = seriesCollection.get(0).getDataLabels();

        msAssert.areEqual(ChartShapeType.DEFAULT, dataLabels1.getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.WEDGE_RECT_CALLOUT, dataLabels1.get(0).getFormat().getShapeType());

        // Changing shape type.

        dataLabels1.setShowValue(true);
        dataLabels1.getFormat().setShapeType(ChartShapeType.STAR);
        dataLabels1.getFormat().getStroke().setForeColor(Color.RED);
        dataLabels1.get(0).getFormat().setShapeType(ChartShapeType.RECTANGLE);
        dataLabels1.get(1).getFormat().setShapeType(ChartShapeType.ELLIPSE);
        dataLabels1.get(2).getFont().setBold(true);

        msAssert.areEqual(ChartShapeType.STAR, dataLabels1.getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.RECTANGLE, dataLabels1.get(0).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.ELLIPSE, dataLabels1.get(1).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.STAR, dataLabels1.get(2).getFormat().getShapeType());
        msAssert.isTrue(dataLabels1.get(2).getFont().getBold());
        msAssert.areEqual(ChartShapeType.STAR, dataLabels1.get(3).getFormat().getShapeType());

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\Test23584Changed.docx");
        seriesCollection = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        dataLabels1 = seriesCollection.get(0).getDataLabels();

        msAssert.areEqual(ChartShapeType.STAR, dataLabels1.getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.RECTANGLE, dataLabels1.get(0).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.ELLIPSE, dataLabels1.get(1).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.STAR, dataLabels1.get(2).getFormat().getShapeType());
        msAssert.isTrue(dataLabels1.get(2).getFont().getBold());
        msAssert.areEqual(ChartShapeType.STAR, dataLabels1.get(3).getFormat().getShapeType());
    }


    /// <summary>
    /// Tests the public <see cref="ChartFormat.SetDefaultFill"/> method.
    /// </summary>
    @Test
    public void testSettingDefaultFill() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestCopyingDataPointFormat.docx");
        ChartSeries series1 = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries().get(0);
        ChartDataPoint dataPoint11 = series1.getDataPoints().get(0);
        ChartDataPoint dataPoint12 = series1.getDataPoints().get(1);
        ChartSeries series2 = doc.getFirstSection().getBody().getShapes().get(1).getChart().getSeries().get(0);
        ChartDataPoint dataPoint21 = series2.getDataPoints().get(0);
        ChartDataPoint dataPoint22 = series2.getDataPoints().get(1);

        msAssert.isFalse(dataPoint11.getFormat().isDefined());
        msAssert.isFalse(dataPoint21.getFormat().isDefined());
        msAssert.isTrue(dataPoint12.getFormat().isDefined());
        msAssert.isTrue(dataPoint22.getFormat().isDefined());

        // Fill of these data points is not defined.
        dataPoint11.getFormat().setDefaultFill();
        dataPoint21.getFormat().setDefaultFill();
        // Fill of these data points is defined.
        dataPoint12.getFormat().setDefaultFill();
        dataPoint22.getFormat().setDefaultFill();

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestSettingDefaultFillForItem.docx");

        series1.getFormat().getFill().twoColorGradient(msColor.getGreen(), Color.BLUE,
            GradientStyle.DIAGONAL_DOWN, GradientVariant.VARIANT_1);
        series2.getFormat().getFill().twoColorGradient(msColor.getGreen(), Color.BLUE,
            GradientStyle.DIAGONAL_DOWN, GradientVariant.VARIANT_1);
        series1.getFormat().setDefaultFill();
        series2.getFormat().setDefaultFill();

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestSettingDefaultFillForCollection.docx");
    }

    /// <summary>
    /// Tests the <see cref="Chart.Format"/> property of an existing chart.
    /// </summary>
    @Test
    public void testChartFillAndStrokeFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestChartFormat.docx");
        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        Chart chart1 = shape1.getChart();

        Paragraph paragraph1 = (Paragraph)shape1.getAncestor(NodeType.PARAGRAPH);
        Paragraph paragraph2 = (Paragraph)paragraph1.deepClone(true);
        paragraph1.getParentNode().insertAfter(paragraph2, paragraph1);
        paragraph1.getParentNode().insertAfter(new Paragraph(doc), paragraph1);
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        Chart chart2 = shape2.getChart();

        Fill fill1 = chart1.getFormat().getFill();
        checkFill(fill1, FillType.SOLID, new Color((0x0c), (0x0c), (0x0c)), msColor.Empty, true, 0.0, false, false);

        // Check the chart sub-elements that fill is not defined.
        checkFill(chart1.getAxisX().getFormat().getFill(), FillType.SOLID, msColor.Empty,
            msColor.Empty, true, 0.0, false, false);
        checkFill(chart1.getDataTable().getFormat().getFill(), FillType.SOLID, msColor.Empty,
            msColor.Empty, true, 0.0, false, false);
        checkFill(chart1.getSeries().get(0).getDataLabels().getFormat().getFill(), FillType.SOLID, msColor.Empty,
            msColor.Empty, true, 0.0, false, false);

        Stroke stroke1 = chart1.getFormat().getStroke();
        checkStroke(stroke1, new Color((0x82), (0), (0)), msColor.Empty, true,
            0.0, 4.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        // Check the chart sub-elements that stroke is not defined.
        checkStroke(chart1.getAxisX().getFormat().getStroke(), msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);
        checkStroke(chart1.getDataTable().getFormat().getStroke(), msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);
        checkStroke(chart1.getSeries().get(0).getDataLabels().getFormat().getStroke(), msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        Fill fill2 = chart2.getFormat().getFill();
        checkFill(fill2, FillType.SOLID, new Color((0x0c), (0x0c), (0x0c)), msColor.Empty, true, 0.0, false, false);

        Stroke stroke2 = chart2.getFormat().getStroke();
        checkStroke(stroke2, new Color((0x82), (0), (0)), msColor.Empty, true,
            0.0, 4.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        Shape shape3 = doc.getFirstSection().getBody().getShapes().get(2);
        Chart chart3 = shape3.getChart();

        Paragraph paragraph3 = (Paragraph)shape3.getAncestor(NodeType.PARAGRAPH);
        Paragraph paragraph4 = (Paragraph)paragraph3.deepClone(true);
        paragraph3.getParentNode().insertAfter(paragraph4, paragraph3);
        paragraph3.getParentNode().insertAfter(new Paragraph(doc), paragraph3);
        Shape shape4 = doc.getFirstSection().getBody().getShapes().get(3);
        Chart chart4 = shape4.getChart();

        Fill fill3 = chart3.getFormat().getFill();
        checkFill(fill3, FillType.SOLID, new Color((0), (0), (0)), msColor.Empty, true, 0.0, false, false);

        Stroke stroke3 = chart3.getFormat().getStroke();
        checkStroke(stroke3, new Color((0xc0), (0), (0)), msColor.Empty, true,
            0.0, 3.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        Fill fill4 = chart4.getFormat().getFill();
        checkFill(fill4, FillType.SOLID, new Color((0), (0), (0)), msColor.Empty, true, 0.0, false, false);

        Stroke stroke4 = chart4.getFormat().getStroke();
        checkStroke(stroke4, new Color((0xc0), (0), (0)), msColor.Empty, true,
            0.0, 3.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        // Change format.

        fill1.setColor(msColor.getDarkBlue());
        fill1.setOpacity(0.55);
        stroke1.setForeColor(Color.RED);
        stroke1.setOpacity(0.2);
        stroke1.setWeight(5.0);
        stroke1.setLineStyle(ShapeLineStyle.TRIPLE);

        checkFill(fill1, FillType.SOLID, new Color((0), (0), (0x8b)), msColor.Empty, true, 0.45, false, false);
        checkStroke(stroke1, new Color((0xff), (0), (0)), msColor.Empty, true,
            0.8, 5.0, DashStyle.SOLID, ShapeLineStyle.TRIPLE, JoinStyle.ROUND, EndCap.FLAT);

        fill2.setVisible(false);
        stroke2.setVisible(false);

        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 4.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        fill3.setColor(msColor.getDarkBlue());
        fill3.setOpacity(0.55);
        stroke3.setForeColor(Color.RED);
        stroke3.setOpacity(0.7);
        stroke3.setWeight(2.0);
        stroke3.setLineStyle(ShapeLineStyle.SINGLE);
        stroke3.setDashStyle(DashStyle.SHORT_DOT);

        checkFill(fill3, FillType.SOLID, new Color((0), (0), (0x8b)), msColor.Empty, true, 0.45, false, false);
        checkStroke(stroke3, new Color((0xff), (0), (0)), msColor.Empty, true,
            0.3, 2.0, DashStyle.SHORT_DOT, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill4.setVisible(false);
        stroke4.setVisible(false);

        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke4, msColor.Empty, msColor.Empty, false,
            1.0, 3.0, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestChartFillAndStrokeFormat.docx");
    }

    /// <summary>
    /// Tests the <see cref="Chart.Format"/> property of a created chart.
    /// </summary>
    @Test
    public void testNewChartFillAndStrokeFormat() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        Fill fill = chart.getFormat().getFill();
        checkFill(fill, FillType.SOLID, new Color((0xff), (0xff), (0xff)), msColor.Empty, true, 0.0, false, false);

        Stroke stroke = chart.getFormat().getStroke();
        checkStroke(stroke, new Color((0xd8), (0xd8), (0xd8)), msColor.Empty, true,
            0.0, 0.75, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        fill.solid();
        fill.setForeColor(Color.CYAN);
        fill.setTransparency(0.45);
        stroke.setColor(Color.BLUE);
        stroke.setTransparency(0.35);
        stroke.setWeight(2.5);
        stroke.setDashStyle(DashStyle.SHORT_DASH);
        stroke.setLineStyle(ShapeLineStyle.DOUBLE);

        checkFill(fill, FillType.SOLID, new Color((0), (0xff), (0xff)),
            msColor.Empty, true, 0.45, false, false);
        checkStroke(stroke, new Color((0), (0), (0xff)), msColor.Empty, true,
            0.35, 2.5, DashStyle.SHORT_DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT,
            ArrowType.NONE, ArrowType.NONE, ArrowWidth.MEDIUM, ArrowLength.MEDIUM,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestNewChartFillAndStrokeFormat.docx");
    }

    /// <summary>
    /// Tests applying a patterned fill to a chart.
    /// </summary>
    @Test
    public void testChartPatternedFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getFormat().getFill();

        fill.patterned(PatternType.HORIZONTAL_BRICK);
        fill.setForeColor(Color.RED);
        fill.setBackColor(Color.YELLOW);
        fill.setTransparency(0.8);
        checkPatternedFill(fill, PatternType.HORIZONTAL_BRICK, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.8);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestChartPatternedFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getFormat().getFill();

        checkPatternedFill(fill, PatternType.HORIZONTAL_BRICK, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.8);
    }

    /// <summary>
    /// Tests applying a one-color gradient fill to a chart.
    /// </summary>
    @Test
    public void testChartOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getFormat().getFill();
        IThemeProvider themeProvider = doc.getTheme();

        fill.oneColorGradient(Color.YELLOW, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, 0.25);
        fill.setTransparency(0.6);
        checkGradientFill(fill, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, new Color((0xff), (0xff), (0)),
            new Color((0xff), (0xff), (0)), 0.6, 270.0, 0.0, 0.5, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestChartOneColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getFormat().getFill();
        themeProvider = doc.getTheme();

        checkGradientFill(fill, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, new Color((0xff), (0xff), (0)),
            new Color((0xff), (0xff), (0)), 0.6, 270.0, 0.0, 0.5, themeProvider);
    }

    /// <summary>
    /// Tests applying a two-color gradient fill to a chart.
    /// </summary>
    @Test
    public void testChartTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getFormat().getFill();
        IThemeProvider themeProvider = doc.getTheme();

        fill.twoColorGradient(Color.RED, Color.YELLOW, GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        fill.setTransparency(0.7);
        checkGradientFill(fill, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.7, 0.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestChartTwoColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getFormat().getFill();
        themeProvider = doc.getTheme();

        checkGradientFill(fill, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.7, 0.0, 0.0, 0.0, themeProvider);
    }


    /// <summary>
    /// Tests the <see cref="ChartTitle.Format"/> and <see cref="ChartAxisTitle.Format"/> properties of
    /// an existing chart.
    /// </summary>
    @Test
    public void testTitleFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestTitleFormat.docx");
        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        Chart chart1 = shape1.getChart();

        Paragraph paragraph1 = (Paragraph)shape1.getAncestor(NodeType.PARAGRAPH);
        Paragraph paragraph2 = (Paragraph)paragraph1.deepClone(true);
        paragraph1.getParentNode().insertAfter(paragraph2, paragraph1);
        paragraph1.getParentNode().insertAfter(new Paragraph(doc), paragraph1);
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        Chart chart2 = shape2.getChart();

        Fill fill1 = chart1.getTitle().getFormat().getFill();
        checkFill(fill1, FillType.GRADIENT, new Color((0xff), (0xff), (0xff)), new Color((0xec), (0x7c), (0x31)),
            true, 0.4, true, false);

        Stroke stroke1 = chart1.getTitle().getFormat().getStroke();
        checkStroke(stroke1, new Color((0xc5), (0x5a), (0x11)), msColor.Empty, true,
            0.15, 3.5, DashStyle.SHORT_DOT, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        Fill axisFill1 = chart1.getAxisX().getTitle().getFormat().getFill();
        checkFill(axisFill1, FillType.GRADIENT, new Color((0xbb), (0xd6), (0xee)), new Color((0x25), (0x5d), (0x91)),
            true, 0.4, true, false);

        Stroke axisStroke1 = chart1.getAxisX().getTitle().getFormat().getStroke();
        checkStroke(axisStroke1, new Color((0x7b), (0x7b), (0x7b)), msColor.Empty, true,
            0.2, 2.75, DashStyle.LONG_DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT);

        Fill fill2 = chart2.getTitle().getFormat().getFill();
        Stroke stroke2 = chart2.getTitle().getFormat().getStroke();
        Fill axisFill2 = chart2.getAxisX().getTitle().getFormat().getFill();
        Stroke axisStroke2 = chart2.getAxisX().getTitle().getFormat().getStroke();

        Shape shape3 = doc.getFirstSection().getBody().getShapes().get(2);
        Chart chart3 = shape3.getChart();

        Paragraph paragraph3 = (Paragraph)shape3.getAncestor(NodeType.PARAGRAPH);
        Paragraph paragraph4 = (Paragraph)paragraph3.deepClone(true);
        paragraph3.getParentNode().insertAfter(paragraph4, paragraph3);
        paragraph3.getParentNode().insertAfter(new Paragraph(doc), paragraph3);
        Shape shape4 = doc.getFirstSection().getBody().getShapes().get(3);
        Chart chart4 = shape4.getChart();

        Fill fill3 = chart3.getTitle().getFormat().getFill();
        checkFill(fill3, FillType.GRADIENT, new Color((0xf7), (0xca), (0xac)), new Color((0x9d), (0x48), (0x0d)),
            true, 0.4, true, false);

        Stroke stroke3 = chart3.getTitle().getFormat().getStroke();
        checkStroke(stroke3, new Color((0x83), (0x3c), (0x0b)), msColor.Empty, true,
            0.25, 3.75, DashStyle.DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT);

        Fill axisFill3 = chart3.getAxisX().getTitle().getFormat().getFill();
        checkFill(axisFill3, FillType.GRADIENT, new Color((0xff), (0xff), (0xff)), new Color((0x5a), (0x9a), (0xd5)),
            true, 0.4, true, false);

        Stroke axisStroke3 = chart3.getAxisX().getTitle().getFormat().getStroke();
        checkStroke(axisStroke3, new Color((0x52), (0x52), (0x52)), msColor.Empty, true,
            0.3, 3.25, DashStyle.LONG_DASH_DOT, ShapeLineStyle.THIN_THICK, JoinStyle.ROUND, EndCap.FLAT);

        Fill fill4 = chart4.getTitle().getFormat().getFill();
        Stroke stroke4 = chart4.getTitle().getFormat().getStroke();
        Fill axisFill4 = chart4.getAxisX().getTitle().getFormat().getFill();
        Stroke axisStroke4 = chart4.getAxisX().getTitle().getFormat().getStroke();

        // Change format.

        fill1.setForeColor(msColor.getDarkBlue());
        fill1.setBackColor(msColor.getDarkGreen());
        fill1.setOpacity(0.35);
        stroke1.getFill().oneColorGradient(Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.3);
        stroke1.setWeight(2.0);
        stroke1.setDashStyle(DashStyle.SHORT_DOT);
        stroke1.setLineStyle(ShapeLineStyle.SINGLE);

        checkFill(fill1, FillType.GRADIENT, new Color((0), (0), (0x8b)), new Color((0), (0x64), (0)),
            true, 0.65, true, false);
        checkStroke(stroke1, new Color((0), (0), (0xff)), new Color((0), (0), (0x99)), true,
            0.0, 2.0, DashStyle.SHORT_DOT, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        axisFill1.solid(Color.CYAN);
        axisFill1.setOpacity(0.35);
        axisStroke1.setColor(Color.BLUE);
        axisStroke1.setWeight(1.5);
        axisStroke1.setLineStyle(ShapeLineStyle.SINGLE);

        checkFill(axisFill1, FillType.SOLID, new Color((0), (0xff), (0xff)), msColor.Empty,
            true, 0.65, false, false);
        checkStroke(axisStroke1, new Color((0), (0), (0xff)), msColor.Empty, true,
            0.0, 1.5, DashStyle.LONG_DASH, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill2.setVisible(false);
        stroke2.setVisible(false);
        axisFill2.setVisible(false);
        axisStroke2.setVisible(false);

        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 3.5, DashStyle.SHORT_DOT, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);
        checkFill(axisFill2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(axisStroke2, msColor.Empty, msColor.Empty, false,
            1.0, 2.75, DashStyle.LONG_DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT);

        fill3.setForeColor(msColor.getDarkBlue());
        fill3.setBackColor(msColor.getDarkGreen());
        fill3.setOpacity(0.35);
        stroke3.getFill().oneColorGradient(Color.BLUE, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_1, 0.3);
        stroke3.setWeight(2.0);
        stroke3.setDashStyle(DashStyle.SHORT_DOT);
        stroke3.setLineStyle(ShapeLineStyle.SINGLE);

        checkFill(fill3, FillType.GRADIENT, new Color((0), (0), (0x8b)), new Color((0), (0x64), (0)),
            true, 0.65, true, false);
        checkStroke(stroke3, new Color((0), (0), (0xff)), new Color((0), (0), (0x99)), true,
            0.0, 2.0, DashStyle.SHORT_DOT, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        axisFill3.solid(Color.CYAN);
        axisFill3.setOpacity(0.35);
        axisStroke3.setColor(Color.BLUE);
        axisStroke3.setWeight(1.5);
        axisStroke3.setLineStyle(ShapeLineStyle.SINGLE);

        checkFill(axisFill3, FillType.SOLID, new Color((0), (0xff), (0xff)), msColor.Empty,
            true, 0.65, false, false);
        checkStroke(axisStroke3, new Color((0), (0), (0xff)), msColor.Empty, true,
            0.0, 1.5, DashStyle.LONG_DASH_DOT, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill4.setVisible(false);
        stroke4.setVisible(false);
        axisFill4.setVisible(false);
        axisStroke4.setVisible(false);

        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke4, msColor.Empty, msColor.Empty, false,
            1.0, 3.75, DashStyle.DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT);
        checkFill(axisFill4, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(axisStroke4, msColor.Empty, msColor.Empty, false,
            1.0, 3.25, DashStyle.LONG_DASH_DOT, ShapeLineStyle.THIN_THICK, JoinStyle.ROUND, EndCap.FLAT);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestTitleFormat.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartTitle.Format"/> and <see cref="ChartAxisTitle.Format"/> properties of
    /// a created chart.
    /// </summary>
    @Test
    public void testTitleFormatInNewChart() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        Fill fill = chart.getTitle().getFormat().getFill();
        Fill axisFill = chart.getAxisX().getTitle().getFormat().getFill();
        Stroke stroke = chart.getTitle().getFormat().getStroke();
        Stroke axisStroke = chart.getAxisX().getTitle().getFormat().getStroke();

        checkFill(fill, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkFill(axisFill, FillType.SOLID, msColor.Empty, msColor.Empty, true, 0.0, false, false);
        checkStroke(stroke, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);
        checkStroke(axisStroke, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill.solid();
        fill.setForeColor(Color.CYAN);
        fill.setTransparency(0.45);
        axisFill.twoColorGradient(Color.CYAN, Color.BLUE, GradientStyle.DIAGONAL_UP, GradientVariant.VARIANT_1);
        axisFill.setTransparency(0.45);

        stroke.setColor(Color.BLUE);
        stroke.setTransparency(0.35);
        stroke.setWeight(2.5);
        stroke.setDashStyle(DashStyle.SHORT_DASH);
        stroke.setLineStyle(ShapeLineStyle.DOUBLE);
        axisStroke.getFill().twoColorGradient(msColor.getDarkBlue(), Color.BLUE, GradientStyle.VERTICAL, GradientVariant.VARIANT_2);
        axisStroke.setTransparency(0.15);
        axisStroke.setWeight(1.5);

        checkFill(fill, FillType.SOLID, new Color((0), (0xff), (0xff)),
            msColor.Empty, true, 0.45, false, false);
        checkFill(axisFill, FillType.GRADIENT, new Color((0), (0xff), (0xff)),
            new Color((0), (0), (0xff)), true, 0.45, true, false);
        checkStroke(stroke, new Color((0), (0), (0xff)), msColor.Empty, true,
            0.35, 2.5, DashStyle.SHORT_DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT);
        checkStroke(axisStroke, new Color((0), (0), (0x8b)), new Color((0), (0), (0xff)), true,
            0.15, 1.5, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestTitleFormatInNewChart.docx");
    }

    /// <summary>
    /// Tests applying a patterned fill to chart and axis titles.
    /// </summary>
    @Test
    public void testTitlePatternedFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getTitle().getFormat().getFill();
        Fill axisFill = chart.getAxisX().getTitle().getFormat().getFill();

        fill.patterned(PatternType.HORIZONTAL_BRICK);
        fill.setForeColor(Color.RED);
        fill.setBackColor(Color.YELLOW);
        fill.setTransparency(0.8);

        axisFill.patterned(PatternType.DOTTED_GRID);
        axisFill.setForeColor(msColor.getDarkBlue());
        axisFill.setBackColor(msColor.getLightGreen());
        axisFill.setTransparency(0.1);

        checkPatternedFill(fill, PatternType.HORIZONTAL_BRICK, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.8);
        checkPatternedFill(axisFill, PatternType.DOTTED_GRID, new Color((0), (0), (0x8b)),
            new Color((0x90), (0xee), (0x90)), 0.1);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTitlePatternedFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getTitle().getFormat().getFill();
        axisFill = chart.getAxisX().getTitle().getFormat().getFill();

        checkPatternedFill(fill, PatternType.HORIZONTAL_BRICK, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.8);
        checkPatternedFill(axisFill, PatternType.DOTTED_GRID, new Color((0), (0), (0x8b)),
            new Color((0x90), (0xee), (0x90)), 0.1);
    }

    /// <summary>
    /// Tests applying a one-color gradient fill to chart and axis titles.
    /// </summary>
    @Test
    public void testTitleOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getTitle().getFormat().getFill();
        Fill axisFill = chart.getAxisX().getTitle().getFormat().getFill();
        IThemeProvider themeProvider = doc.getTheme();

        fill.oneColorGradient(Color.YELLOW, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, 0.25);
        fill.setTransparency(0.6);
        axisFill.oneColorGradient(Color.CYAN, GradientStyle.VERTICAL, GradientVariant.VARIANT_2, 0.35);
        axisFill.setTransparency(0.4);

        checkGradientFill(fill, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, new Color((0xff), (0xff), (0)),
            new Color((0xff), (0xff), (0)), 0.6, 270.0, 0.0, 0.5, themeProvider);
        checkGradientFill(axisFill, GradientStyle.VERTICAL, GradientVariant.VARIANT_2, new Color((0), (0xff), (0xff)),
            new Color((0), (0xff), (0xff)), 0.4, 180.0, 0.0, 0.7, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTitleOneColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getTitle().getFormat().getFill();
        axisFill = chart.getAxisX().getTitle().getFormat().getFill();
        themeProvider = doc.getTheme();

        checkGradientFill(fill, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, new Color((0xff), (0xff), (0)),
            new Color((0xff), (0xff), (0)), 0.6, 270.0, 0.0, 0.5, themeProvider);
        checkGradientFill(axisFill, GradientStyle.VERTICAL, GradientVariant.VARIANT_2, new Color((0), (0xff), (0xff)),
            new Color((0), (0xff), (0xff)), 0.4, 180.0, 0.0, 0.7, themeProvider);
    }

    /// <summary>
    /// Tests applying a two-color gradient fill to chart and axis titles.
    /// </summary>
    @Test
    public void testTitleTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getTitle().getFormat().getFill();
        Fill axisFill = chart.getAxisX().getTitle().getFormat().getFill();
        IThemeProvider themeProvider = doc.getTheme();

        fill.twoColorGradient(Color.RED, Color.YELLOW, GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        fill.setTransparency(0.7);
        axisFill.twoColorGradient(Color.BLUE, msColor.getGreen(), GradientStyle.VERTICAL, GradientVariant.VARIANT_1);
        axisFill.setTransparency(0.65);

        checkGradientFill(fill, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.7, 0.0, 0.0, 0.0, themeProvider);
        checkGradientFill(axisFill, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, new Color((0), (0), (0xff)),
            new Color((0), (0x80), (0)), 0.65, 0.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestTitleTwoColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getTitle().getFormat().getFill();
        axisFill = chart.getAxisX().getTitle().getFormat().getFill();
        themeProvider = doc.getTheme();

        checkGradientFill(fill, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.7, 0.0, 0.0, 0.0, themeProvider);
        checkGradientFill(axisFill, GradientStyle.VERTICAL, GradientVariant.VARIANT_1, new Color((0), (0), (0xff)),
            new Color((0), (0x80), (0)), 0.65, 0.0, 0.0, 0.0, themeProvider);
    }


    /// <summary>
    /// Tests the <see cref="ChartLegend.Format"/> property of an existing chart.
    /// </summary>
    @Test
    public void testChartLegendFormat() throws Exception
    {
        Document doc = TestUtil.open("Model\\Charts\\TestChartLegendFormat.docx");
        Shape shape1 = doc.getFirstSection().getBody().getShapes().get(0);
        Chart chart1 = shape1.getChart();

        Paragraph paragraph1 = (Paragraph)shape1.getAncestor(NodeType.PARAGRAPH);
        Paragraph paragraph2 = (Paragraph)paragraph1.deepClone(true);
        paragraph1.getParentNode().insertAfter(paragraph2, paragraph1);
        paragraph1.getParentNode().insertAfter(new Paragraph(doc), paragraph1);
        Shape shape2 = doc.getFirstSection().getBody().getShapes().get(1);
        Chart chart2 = shape2.getChart();

        Fill fill1 = chart1.getLegend().getFormat().getFill();
        checkFill(fill1, FillType.SOLID, new Color((0xb3), (0xc6), (0xe7)), msColor.Empty,
            true, 0.0, false, false);

        Stroke stroke1 = chart1.getLegend().getFormat().getStroke();
        checkStroke(stroke1, new Color((0x2a), (0x4b), (0x86)), new Color((0x8d), (0xa9), (0xdb)), true,
            0.0, 3.5, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        Fill fill2 = chart2.getLegend().getFormat().getFill();
        Stroke stroke2 = chart2.getLegend().getFormat().getStroke();

        Shape shape3 = doc.getFirstSection().getBody().getShapes().get(2);
        Chart chart3 = shape3.getChart();

        Paragraph paragraph3 = (Paragraph)shape3.getAncestor(NodeType.PARAGRAPH);
        Paragraph paragraph4 = (Paragraph)paragraph3.deepClone(true);
        paragraph3.getParentNode().insertAfter(paragraph4, paragraph3);
        paragraph3.getParentNode().insertAfter(new Paragraph(doc), paragraph3);
        Shape shape4 = doc.getFirstSection().getBody().getShapes().get(3);
        Chart chart4 = shape4.getChart();

        Fill fill3 = chart3.getLegend().getFormat().getFill();
        checkFill(fill3, FillType.SOLID, new Color((0xf7), (0xca), (0xac)), msColor.Empty,
            true, 0.25, false, false);

        Stroke stroke3 = chart3.getLegend().getFormat().getStroke();
        checkStroke(stroke3, new Color((0xb0), (0x50), (0x0f)), new Color((0xf4), (0xb0), (0x81)), true,
            0.0, 3.75, DashStyle.SOLID, ShapeLineStyle.THIN_THICK, JoinStyle.ROUND, EndCap.FLAT);

        Fill fill4 = chart4.getLegend().getFormat().getFill();
        Stroke stroke4 = chart4.getLegend().getFormat().getStroke();

        // Change format.

        fill1.twoColorGradient(Color.YELLOW, msColor.getLightBlue(), GradientStyle.FROM_CENTER, GradientVariant.VARIANT_1);
        fill1.setOpacity(0.75);
        stroke1.getFill().solid(msColor.getDarkBlue());
        stroke1.setWeight(1.75);
        stroke1.setDashStyle(DashStyle.SHORT_DOT);
        stroke1.setLineStyle(ShapeLineStyle.SINGLE);
        stroke1.setOpacity(0.7);

        checkFill(fill1, FillType.GRADIENT, new Color((0xff), (0xff), (0)), new Color((0xad), (0xd8), (0xe6)),
            true, 0.25, true, false);
        checkStroke(stroke1, new Color((0), (0), (0x8b)), msColor.Empty, true,
            0.3, 1.75, DashStyle.SHORT_DOT, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill2.setVisible(false);
        stroke2.setVisible(false);

        checkFill(fill2, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke2, msColor.Empty, msColor.Empty, false,
            1.0, 3.5, DashStyle.SOLID, ShapeLineStyle.THICK_THIN, JoinStyle.ROUND, EndCap.FLAT);

        fill3.twoColorGradient(Color.YELLOW, msColor.getLightBlue(), GradientStyle.FROM_CENTER, GradientVariant.VARIANT_1);
        fill3.setOpacity(0.75);
        stroke3.getFill().solid(msColor.getDarkBlue());
        stroke3.setWeight(1.75);
        stroke3.setDashStyle(DashStyle.SHORT_DOT);
        stroke3.setLineStyle(ShapeLineStyle.SINGLE);
        stroke3.setOpacity(0.7);

        checkFill(fill3, FillType.GRADIENT, new Color((0xff), (0xff), (0)), new Color((0xad), (0xd8), (0xe6)),
            true, 0.25, true, false);
        checkStroke(stroke3, new Color((0), (0), (0x8b)), msColor.Empty, true,
            0.3, 1.75, DashStyle.SHORT_DOT, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill4.setVisible(false);
        stroke4.setVisible(false);

        checkFill(fill4, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke4, msColor.Empty, msColor.Empty, false,
            1.0, 3.75, DashStyle.SOLID, ShapeLineStyle.THIN_THICK, JoinStyle.ROUND, EndCap.FLAT);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestChartLegendFormat.docx");
    }

    /// <summary>
    /// Tests the <see cref="ChartLegend.Format"/> property of a created chart.
    /// </summary>
    @Test
    public void testChartLegendFormatInNewChart() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        Fill fill = chart.getLegend().getFormat().getFill();
        Stroke stroke = chart.getLegend().getFormat().getStroke();

        checkFill(fill, FillType.SOLID, msColor.Empty, msColor.Empty, false, 1.0, false, false);
        checkStroke(stroke, msColor.Empty, msColor.Empty, false,
            1.0, 0.0, DashStyle.SOLID, ShapeLineStyle.SINGLE, JoinStyle.ROUND, EndCap.FLAT);

        fill.setForeColor(Color.CYAN);
        fill.setTransparency(0.45);

        stroke.setColor(Color.BLUE);
        stroke.setTransparency(0.35);
        stroke.setWeight(2.5);
        stroke.setDashStyle(DashStyle.SHORT_DASH);
        stroke.setLineStyle(ShapeLineStyle.DOUBLE);

        checkFill(fill, FillType.SOLID, new Color((0), (0xff), (0xff)),
            msColor.Empty, true, 0.45, false, false);
        checkStroke(stroke, new Color((0), (0), (0xff)), msColor.Empty, true,
            0.35, 2.5, DashStyle.SHORT_DASH, ShapeLineStyle.DOUBLE, JoinStyle.ROUND, EndCap.FLAT);

        TestUtil.saveCheckGold(doc, "Model\\Charts\\TestChartLegendFormatInNewChart.docx");
    }

    /// <summary>
    /// Tests applying a patterned fill to a chart legend.
    /// </summary>
    @Test
    public void testChartLegendPatternedFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getLegend().getFormat().getFill();

        fill.patterned(PatternType.DIAGONAL_BRICK);
        fill.setForeColor(Color.RED);
        fill.setBackColor(Color.YELLOW);
        fill.setTransparency(0.75);

        checkPatternedFill(fill, PatternType.DIAGONAL_BRICK, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.75);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestChartLegendPatternedFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getLegend().getFormat().getFill();

        checkPatternedFill(fill, PatternType.DIAGONAL_BRICK, new Color((0xff), (0), (0)),
            new Color((0xff), (0xff), (0)), 0.75);
    }

    /// <summary>
    /// Tests applying a one-color gradient fill to a chart legend.
    /// </summary>
    @Test
    public void testChartLegendOneColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getLegend().getFormat().getFill();
        IThemeProvider themeProvider = doc.getTheme();

        fill.oneColorGradient(msColor.getLightGreen(), GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, 0.25);
        fill.setTransparency(0.25);

        checkGradientFill(fill, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, new Color((0x90), (0xee), (0x90)),
            new Color((0x90), (0xee), (0x90)), 0.25, 270.0, 0.0, 0.5, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestChartLegendOneColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getLegend().getFormat().getFill();
        themeProvider = doc.getTheme();

        checkGradientFill(fill, GradientStyle.HORIZONTAL, GradientVariant.VARIANT_2, new Color((0x90), (0xee), (0x90)),
            new Color((0x90), (0xee), (0x90)), 0.25, 270.0, 0.0, 0.5, themeProvider);
    }

    /// <summary>
    /// Tests applying a two-color gradient fill to a chart legend.
    /// </summary>
    @Test
    public void testChartLegendTwoColorGradientFill() throws Exception
    {
        Document doc = createDocumentWithColumnChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        Fill fill = chart.getLegend().getFormat().getFill();
        IThemeProvider themeProvider = doc.getTheme();

        fill.twoColorGradient(Color.BLUE, Color.YELLOW, GradientStyle.VERTICAL, GradientVariant.VARIANT_2);
        fill.setTransparency(0.7);

        checkGradientFill(fill, GradientStyle.VERTICAL, GradientVariant.VARIANT_2, new Color((0), (0), (0xff)),
            new Color((0xff), (0xff), (0)), 0.7, 180.0, 0.0, 0.0, themeProvider);

        doc = TestUtil.saveOpen(doc, "Model\\Charts\\TestChartLegendTwoColorGradientFill.docx", null, true);
        chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();
        fill = chart.getLegend().getFormat().getFill();
        themeProvider = doc.getTheme();

        checkGradientFill(fill, GradientStyle.VERTICAL, GradientVariant.VARIANT_2, new Color((0), (0), (0xff)),
            new Color((0xff), (0xff), (0)), 0.7, 180.0, 0.0, 0.0, themeProvider);
    }


    /// <summary>
    /// Tests creating a chart with a dark background and light font color.
    /// </summary>
    @Test
    public void testCreatingDarkChart() throws Exception
    {
        Document doc = createDocumentWithColumnChart(true);
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        chart.getFormat().getFill().solid(msColor.getDarkBlue());
        chart.getTitle().getFont().setColor(Color.YELLOW);
        chart.getSeries().get(0).getDataLabels().getFont().setColor(Color.YELLOW);
        chart.getDataTable().getFont().setColor(Color.YELLOW);
        chart.getAxisX().getTitle().getFont().setColor(Color.YELLOW);
        chart.getAxisY().getTickLabels().getFont().setColor(Color.YELLOW);
        chart.getLegend().getFont().setColor(Color.YELLOW);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Charts\\TestCreatingDarkChart.docx");
    }

    /// <summary>
    /// Checks <see cref="ChartFormat.ShapeType"/> property of data labels of the document being tested in
    /// the <see cref="TestChartShapeTypeApplying"/> method.
    /// </summary>
    private static void checkChartShapeTypeApplying(Document doc)
    {
        ChartSeriesCollection seriesCollection = doc.getFirstSection().getBody().getShapes().get(0).getChart().getSeries();
        ChartDataLabelCollection dataLabels1 = seriesCollection.get(0).getDataLabels();

        msAssert.areEqual(ChartShapeType.WEDGE_RECT_CALLOUT, dataLabels1.getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.WEDGE_RECT_CALLOUT, dataLabels1.get(0).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.UP_ARROW_CALLOUT, dataLabels1.get(1).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.DEFAULT, dataLabels1.get(2).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.DEFAULT, dataLabels1.get(3).getFormat().getShapeType());

        ChartDataLabelCollection dataLabels2 = seriesCollection.get(1).getDataLabels();

        msAssert.areEqual(ChartShapeType.DOWN_ARROW_CALLOUT, dataLabels2.getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.DOWN_ARROW_CALLOUT, dataLabels2.get(0).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.DOWN_ARROW_CALLOUT, dataLabels2.get(1).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.DOWN_ARROW_CALLOUT, dataLabels2.get(2).getFormat().getShapeType());
        msAssert.areEqual(ChartShapeType.DOWN_ARROW_CALLOUT, dataLabels2.get(3).getFormat().getShapeType());
    }

    /// <summary>
    /// Checks properties of an image <see cref="Fill"/> instance.
    /// </summary>
    private static void checkImageFill(Fill fill, double expectedTransparency, int expectedImageLength) throws Exception
    {
        msAssert.areEqual(FillType.PICTURE, fill.getFillType());
        msAssert.areEqual(true, fill.getVisible());
        msAssert.areEqual(expectedTransparency, 0.005, fill.getTransparency());
        msAssert.areEqual(1.0 - expectedTransparency, 0.005, fill.getOpacity());
        msAssert.areEqual(true, fill.getRotateWithObject());
        msAssert.isNotNull(fill.getImageBytes());
        msAssert.areEqual(expectedImageLength, fill.getImageBytes().length);
    }

    /// <summary>
    /// Checks properties of a gradient <see cref="Fill"/> instance.
    /// </summary>
    private static void checkGradientFill(Fill fill, /*GradientStyle*/int expectedStyle, /*GradientVariant*/int expectedVariant,
        Color expectedColor, double expectedTransparency, double expectedGradientAngle, double expectedTint,
        double expectedShade, IThemeProvider themeProvider) throws Exception
    {
        checkGradientFill(fill, expectedStyle, expectedVariant, expectedColor, expectedColor, expectedTransparency,
            expectedGradientAngle, expectedTint, expectedShade, themeProvider);
    }

    /// <summary>
    /// Checks properties of a gradient <see cref="Fill"/> instance.
    /// </summary>
    /// <dev>
    /// Not all combinations of <paramref name="expectedStyle"/> and <paramref name="expectedVariant"/> are
    /// currently supported.
    /// </dev>
    private static void checkGradientFill(Fill fill, /*GradientStyle*/int expectedStyle, /*GradientVariant*/int expectedVariant,
        Color expectedForeColor, Color expectedBackColor, double expectedTransparency, double expectedGradientAngle,
        double expectedTint, double expectedShade, IThemeProvider themeProvider) throws Exception
    {
        // BackColor is checked below.
        checkFill(fill, FillType.GRADIENT, expectedForeColor, fill.getBackColor(), true,
            expectedTransparency, true, false);

        msAssert.areEqual(expectedStyle, fill.getGradientStyle());
        msAssert.areEqual(expectedVariant, fill.getGradientVariant());
        msAssert.areEqual(expectedGradientAngle, fill.getGradientAngle());

        boolean isVariant3Or4 =
            ((expectedVariant == GradientVariant.VARIANT_3) || (expectedVariant == GradientVariant.VARIANT_4));
        int expectedGradientStopCount = isVariant3Or4 ? 3 : 2;
        msAssert.areEqual(expectedGradientStopCount, fill.getGradientStops().getCount());

        GradientStop firstGradientStop = fill.getGradientStops().get(0);
        GradientStop middleGradientStop = fill.getGradientStops().get(1);
        GradientStop lastGradientStop = fill.getGradientStops().get(expectedGradientStopCount - 1);

        switch (expectedVariant)
        {
            case GradientVariant.VARIANT_3:
                checkGradientStop(firstGradientStop, expectedForeColor, 0.0, 0.0, 0.0, themeProvider);
                checkGradientStop(middleGradientStop, expectedBackColor, 0.5, expectedTint, expectedShade, themeProvider);
                checkGradientStop(lastGradientStop, expectedForeColor, 1.0, 0.0, 0.0, themeProvider);
                break;
            case GradientVariant.VARIANT_4:
                checkGradientStop(firstGradientStop, expectedForeColor, 0.5, 0.0, 0.0, themeProvider);
                checkGradientStop(middleGradientStop, expectedBackColor, 0.0, expectedTint, expectedShade, themeProvider);
                checkGradientStop(lastGradientStop, expectedBackColor, 1.0, expectedTint, expectedShade, themeProvider);
                break;
            default:
                checkGradientStop(firstGradientStop, expectedForeColor, 0.0, 0.0, 0.0, themeProvider);
                checkGradientStop(lastGradientStop, expectedBackColor, 1.0, expectedTint, expectedShade, themeProvider);
                break;
        }

    }

    private static void checkGradientStop(GradientStop gradientStop, Color expectedColor,
        double expectedPosition, double expectedTint, double expectedShade, IThemeProvider themeProvider)
    {
        msAssert.areEqual(expectedPosition, 0.01, gradientStop.getPosition());

        DmlColor color = gradientStop.getDmlGradientStop().getColor();
        msAssert.isNotNull(color);
        if (expectedColor != msColor.Empty)
            msAssert.areEqual(expectedColor, color.createUnmodifiedDrColor(themeProvider).toNativeColor());

        DmlTint tintModifier = (DmlTint)color.getColorModifier(DmlColorModifierType.TINT);
        if (MathUtil.areEqual(expectedTint, 0.0))
        {
            msAssert.isNull(tintModifier);
        }
        else
        {
            msAssert.isNotNull(tintModifier);
            msAssert.areEqual(expectedTint, 0.01, tintModifier.getValue());
        }

        DmlShade shadeModifier = (DmlShade)color.getColorModifier(DmlColorModifierType.SHADE);
        if (MathUtil.areEqual(expectedShade, 0.0))
        {
            msAssert.isNull(shadeModifier);
        }
        else
        {
            msAssert.isNotNull(shadeModifier);
            msAssert.areEqual(expectedShade, 0.01, shadeModifier.getValue());
        }
    }

    /// <summary>
    /// Checks properties of a patterned <see cref="Fill"/> instance.
    /// </summary>
    private static void checkPatternedFill(Fill fill, /*PatternType*/int expectedPatternType, Color expectedForeColor,
        Color expectedBackColor, double expectedTransparency) throws Exception
    {
        checkFill(fill, FillType.PATTERNED, expectedForeColor, expectedBackColor, true,
            expectedTransparency, false, false);

        msAssert.areEqual(expectedPatternType, fill.getPattern());
    }

    /// <summary>
    /// Checks properties of a textured <see cref="Fill"/> instance.
    /// </summary>
    private static void checkTexturedFill(Fill fill, /*PresetTexture*/int expectedTexture, Color expectedForeColor,
        Color expectedBackColor, boolean expectedVisible, double expectedTransparency) throws Exception
    {
        checkFill(fill, FillType.TEXTURED, expectedForeColor, expectedBackColor, expectedVisible,
            expectedTransparency, true, true);

        msAssert.areEqual(expectedTexture, fill.getPresetTexture());
    }

    /// <summary>
    /// Checks properties of a <see cref="Fill"/> instance.
    /// </summary>
    private static void checkFill(Fill fill, /*FillType*/int expectedFillType, Color expectedForeColor,
        Color expectedBackColor, boolean expectedVisible, double expectedTransparency, boolean expectedRotateWithObject,
        boolean expectedHavingImage) throws Exception
    {
        msAssert.areEqual(expectedFillType, fill.getFillType());
        msAssert.areEqual(expectedForeColor, fill.getForeColor());
        msAssert.areEqual(expectedForeColor, fill.getColor());
        msAssert.areEqual(expectedBackColor, fill.getBackColor());
        msAssert.areEqual(expectedVisible, fill.getVisible());
        msAssert.areEqual(expectedTransparency, 0.005, fill.getTransparency());
        msAssert.areEqual(1.0 - expectedTransparency, 0.005, fill.getOpacity());
        msAssert.areEqual(expectedRotateWithObject, fill.getRotateWithObject());
        msAssert.areEqual(expectedHavingImage, fill.getImageBytes() != null);
    }

    /// <summary>
    /// Checks properties of a <see cref="Stroke"/> instance.
    /// </summary>
    private static void checkStroke(Stroke stroke, Color expectedForeColor, Color expectedBackColor,
        boolean expectedVisible, double expectedTransparency, double expectedWeight, /*DashStyle*/int expectedDashStyle,
        /*ShapeLineStyle*/int expectedLineStyle, /*JoinStyle*/int expectedJoinStyle, /*EndCap*/int expectedEndCap) throws Exception
    {
        checkStroke(stroke, expectedForeColor, expectedBackColor, expectedVisible, expectedTransparency, expectedWeight,
            expectedDashStyle, expectedLineStyle, expectedJoinStyle, expectedEndCap, ArrowType.NONE, ArrowType.NONE,
            ArrowWidth.MEDIUM, ArrowLength.MEDIUM, ArrowWidth.MEDIUM, ArrowLength.MEDIUM, false);
    }

    /// <summary>
    /// Checks properties of a <see cref="Stroke"/> instance.
    /// </summary>
    private static void checkStroke(Stroke stroke, Color expectedForeColor, Color expectedBackColor,
        boolean expectedVisible, double expectedTransparency, double expectedWeight, /*DashStyle*/int expectedDashStyle,
        /*ShapeLineStyle*/int expectedLineStyle, /*JoinStyle*/int expectedJoinStyle, /*EndCap*/int expectedEndCap,
        /*ArrowType*/int expectedStartArrowType, /*ArrowType*/int expectedEndArrowType, /*ArrowWidth*/int expectedStartArrowWidth,
        /*ArrowLength*/int expectedStartArrowLength, /*ArrowWidth*/int expectedEndArrowWidth, /*ArrowLength*/int expectedEndArrowLength,
        boolean expectedHavingImage) throws Exception
    {
        msAssert.areEqual(expectedForeColor, stroke.getForeColor());
        msAssert.areEqual(expectedForeColor, stroke.getColor());
        msAssert.areEqual(expectedBackColor, stroke.getBackColor());
        msAssert.areEqual(expectedBackColor, stroke.getColor2());
        msAssert.areEqual(expectedVisible, stroke.getVisible());
        msAssert.areEqual(expectedVisible, stroke.getOn());
        msAssert.areEqual(expectedTransparency, 0.005, stroke.getTransparency());
        msAssert.areEqual(1.0 - expectedTransparency, 0.005, stroke.getOpacity());
        msAssert.areEqual(expectedWeight, 0.005, stroke.getWeight());
        msAssert.areEqual(expectedDashStyle, stroke.getDashStyle());
        msAssert.areEqual(expectedLineStyle, stroke.getLineStyle());
        msAssert.areEqual(expectedJoinStyle, stroke.getJoinStyle());
        msAssert.areEqual(expectedEndCap, stroke.getEndCap());
        msAssert.areEqual(expectedStartArrowType, stroke.getStartArrowType());
        msAssert.areEqual(expectedEndArrowType, stroke.getEndArrowType());
        msAssert.areEqual(expectedStartArrowWidth, stroke.getStartArrowWidth());
        msAssert.areEqual(expectedStartArrowLength, stroke.getStartArrowLength());
        msAssert.areEqual(expectedEndArrowWidth, stroke.getEndArrowWidth());
        msAssert.areEqual(expectedEndArrowLength, stroke.getEndArrowLength());
        msAssert.areEqual(expectedHavingImage, stroke.getImageBytes() != null);
    }

    /// <summary>
    /// Creates a document that contains a column chart.
    /// </summary>
    private static Document createDocumentWithColumnChart() throws Exception
    {
        return createDocumentWithColumnChart(false);
    }

    /// <summary>
    /// Creates a document that contains a column chart with ability to show data labels, data table, axis title.
    /// </summary>
    private static Document createDocumentWithColumnChart(boolean showAllChartSubElements) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.COLUMN, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection series = chart.getSeries();

        // Delete default generated series.
        series.clear();

        String[] categories = new String[] { "Category 1", "Category 2" };

        series.add("Series 1", categories, new double[] { 1.0, 2.0 });
        series.add("Series 2", categories, new double[] { 3.0, 4.0 });
        series.add("Series 3", categories, new double[] { 5.0, 6.0 });
        series.add("Series 4", categories, new double[] { 7.0, 8.0 });
        series.add("Series 5", categories, new double[] { 9.0, 10.0 });

        if (showAllChartSubElements)
        {
            series.get(0).hasDataLabels(true);
            series.get(0).getDataLabels().setShowValue(true);
            chart.getDataTable().setShow(true);
            chart.getAxisX().getTitle().setShow(true);
        }

        return doc;
    }

    /// <summary>
    /// Creates a document that contains a scatter chart including one series.
    /// </summary>
    private static Document createDocumentWithOneSeriesScatterChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.SCATTER, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection series = chart.getSeries();

        // Delete default generated series.
        series.clear();

        series.add("AW Series 1", new double[] { 0.7, 1.8, 2.6, 3.4, 4.3, 5.0, 5.8 },
            new double[] { 2.7, 3.2, 0.8, 1.5, 4.4, 2.0, 3.0 });

        return doc;
    }

    /// <summary>
    /// Creates a document that contains a scatter chart including two series.
    /// </summary>
    private static Document createDocumentWithTwoSeriesScatterChart() throws Exception
    {
        Document doc = createDocumentWithOneSeriesScatterChart();
        Chart chart = doc.getFirstSection().getBody().getShapes().get(0).getChart();

        chart.getSeries().add("AW Series 2", new double[] { 1.2, 2.2, 3.2, 4.2, 5.2, 6.2, 7.2 },
            new double[] { 0.7, 6.4, 7.1, 1.9, 3.9, 7.6, 4.0 });

        return doc;
    }

    /// <summary>
    /// Creates a document that contains a pie chart.
    /// </summary>
    private static Document createDocumentWithPieChart() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = builder.insertChart(ChartType.PIE, 432.0, 252.0);
        Chart chart = shape.getChart();
        ChartSeriesCollection series = chart.getSeries();

        // Delete default generated series.
        series.clear();

        String[] categories = new String[] { "Grapefruit", "Oranges", "Plums", "Pears", "Apples", "Kiwi", "Mango" };

        series.add("Series 1", categories, new double[] { 2.7, 3.2, 0.8, 1.5, 4.4, 2.0, 3.0 });

        return doc;
    }

    /// <summary>
    /// Shows data labels of the series with setting their font size to 32.
    /// </summary>
    private static ChartDataLabelCollection showDataLabels(ChartSeries series)
    {
        series.hasDataLabels(true);
        ChartDataLabelCollection dataLabels = series.getDataLabels();
        dataLabels.setShowValue(true);
        dataLabels.getFont().setSize(32.0);
        return dataLabels;
    }
}

