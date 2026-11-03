// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Test <see cref="DmlChartsEnum"/>.
/// </summary>
@Test
public class TestSimpleTypes
{
    @Test
    public void testToAxisPosition()
    {
        msAssert.areEqual(AxisPosition.BOTTOM, DmlChartsEnum.dmlToAxisPosition("b"));
        msAssert.areEqual(AxisPosition.LEFT, DmlChartsEnum.dmlToAxisPosition("l"));
        msAssert.areEqual(AxisPosition.RIGHT, DmlChartsEnum.dmlToAxisPosition("r"));
        msAssert.areEqual(AxisPosition.TOP, DmlChartsEnum.dmlToAxisPosition("t"));
    }

    @Test
    public void testToBarDirection()
    {
        msAssert.areEqual(BarDirection.BAR, DmlChartsEnum.dmlToBarDirection("bar"));
        msAssert.areEqual(BarDirection.COLUMN, DmlChartsEnum.dmlToBarDirection("col"));
    }

    @Test
    public void testToBarGrouping()
    {
        msAssert.areEqual(Grouping.CLUSTERED, DmlChartsEnum.dmlToGrouping("clustered"));
        msAssert.areEqual(Grouping.PERCENT_STACKED, DmlChartsEnum.dmlToGrouping("percentStacked"));
        msAssert.areEqual(Grouping.STACKED, DmlChartsEnum.dmlToGrouping("stacked"));
        msAssert.areEqual(Grouping.STANDARD, DmlChartsEnum.dmlToGrouping("standard"));
    }

    @Test
    public void testToBuiltInUnit()
    {
        msAssert.areEqual(AxisBuiltInUnit.BILLIONS, DmlChartsEnum.dmlToBuiltInUnit("billions"));
        msAssert.areEqual(AxisBuiltInUnit.HUNDRED_MILLIONS, DmlChartsEnum.dmlToBuiltInUnit("hundredMillions"));
        msAssert.areEqual(AxisBuiltInUnit.HUNDREDS, DmlChartsEnum.dmlToBuiltInUnit("hundreds"));
        msAssert.areEqual(AxisBuiltInUnit.HUNDRED_THOUSANDS, DmlChartsEnum.dmlToBuiltInUnit("hundredThousands"));
        msAssert.areEqual(AxisBuiltInUnit.MILLIONS, DmlChartsEnum.dmlToBuiltInUnit("millions"));
        msAssert.areEqual(AxisBuiltInUnit.TEN_MILLIONS, DmlChartsEnum.dmlToBuiltInUnit("tenMillions"));
        msAssert.areEqual(AxisBuiltInUnit.TEN_THOUSANDS, DmlChartsEnum.dmlToBuiltInUnit("tenThousands"));
        msAssert.areEqual(AxisBuiltInUnit.THOUSANDS, DmlChartsEnum.dmlToBuiltInUnit("thousands"));
        msAssert.areEqual(AxisBuiltInUnit.TRILLIONS, DmlChartsEnum.dmlToBuiltInUnit("trillions"));
    }

    @Test
    public void testToCrossBetween()
    {
        msAssert.areEqual(CrossBetween.BETWEEN, DmlChartsEnum.dmlToCrossBetween("between"));
        msAssert.areEqual(CrossBetween.MIDPOINT_OF_CATEGORY, DmlChartsEnum.dmlToCrossBetween("midCat"));
    }

    @Test
    public void testToCrosses()
    {
        msAssert.areEqual(AxisCrosses.AUTOMATIC, DmlChartsEnum.dmlToCrosses("autoZero"));
        msAssert.areEqual(AxisCrosses.MAXIMUM, DmlChartsEnum.dmlToCrosses("max"));
        msAssert.areEqual(AxisCrosses.MINIMUM, DmlChartsEnum.dmlToCrosses("min"));
    }

    @Test
    public void testToDataLabelPosition()
    {
        msAssert.areEqual(ChartDataLabelPosition.BELOW, DmlChartsEnum.dmlToDataLabelPosition("b"));
        msAssert.areEqual(ChartDataLabelPosition.BEST_FIT, DmlChartsEnum.dmlToDataLabelPosition("bestFit"));
        msAssert.areEqual(ChartDataLabelPosition.CENTER, DmlChartsEnum.dmlToDataLabelPosition("ctr"));
        msAssert.areEqual(ChartDataLabelPosition.INSIDE_BASE, DmlChartsEnum.dmlToDataLabelPosition("inBase"));
        msAssert.areEqual(ChartDataLabelPosition.INSIDE_END, DmlChartsEnum.dmlToDataLabelPosition("inEnd"));
        msAssert.areEqual(ChartDataLabelPosition.LEFT, DmlChartsEnum.dmlToDataLabelPosition("l"));
        msAssert.areEqual(ChartDataLabelPosition.OUTSIDE_END, DmlChartsEnum.dmlToDataLabelPosition("outEnd"));
        msAssert.areEqual(ChartDataLabelPosition.RIGHT, DmlChartsEnum.dmlToDataLabelPosition("r"));
        msAssert.areEqual(ChartDataLabelPosition.ABOVE, DmlChartsEnum.dmlToDataLabelPosition("t"));
    }

    @Test
    public void testToDisplayBlanksAs()
    {
        msAssert.areEqual(DisplayBlanksAs.GAP, DmlChartsEnum.dmlToDisplayBlanksAs("gap"));
        msAssert.areEqual(DisplayBlanksAs.SPAN, DmlChartsEnum.dmlToDisplayBlanksAs("span"));
        msAssert.areEqual(DisplayBlanksAs.ZERO, DmlChartsEnum.dmlToDisplayBlanksAs("zero"));
    }

    @Test
    public void testToErrorBarDirection()
    {
        msAssert.areEqual(ErrorBarDirection.X, DmlChartsEnum.dmlToErrorBarDirection("x"));
        msAssert.areEqual(ErrorBarDirection.Y, DmlChartsEnum.dmlToErrorBarDirection("y"));
    }

    @Test
    public void testToErrorBarType()
    {
        msAssert.areEqual(ErrorBarType.BOTH, DmlChartsEnum.dmlToErrorBarType("both"));
        msAssert.areEqual(ErrorBarType.MINUS, DmlChartsEnum.dmlToErrorBarType("minus"));
        msAssert.areEqual(ErrorBarType.PLUS, DmlChartsEnum.dmlToErrorBarType("plus"));
    }

    @Test
    public void testToErrorValueType()
    {
        msAssert.areEqual(ErrorValueType.CUSTOM_ERROR_BARS, DmlChartsEnum.dmlToErrorValueType("cust"));
        msAssert.areEqual(ErrorValueType.FIXED_VALUE, DmlChartsEnum.dmlToErrorValueType("fixedVal"));
        msAssert.areEqual(ErrorValueType.PERCENTAGE, DmlChartsEnum.dmlToErrorValueType("percentage"));
        msAssert.areEqual(ErrorValueType.STANDARD_DEVIATION, DmlChartsEnum.dmlToErrorValueType("stdDev"));
        msAssert.areEqual(ErrorValueType.STANDARD_ERROR, DmlChartsEnum.dmlToErrorValueType("stdErr"));
    }

    @Test
    public void testToLabelAlignment()
    {
        msAssert.areEqual(LabelAlignment.CENTER, DmlChartsEnum.dmlToLabelAlignment("ctr"));
        msAssert.areEqual(LabelAlignment.LEFT, DmlChartsEnum.dmlToLabelAlignment("l"));
        msAssert.areEqual(LabelAlignment.RIGHT, DmlChartsEnum.dmlToLabelAlignment("r"));
    }

    @Test
    public void testToLayoutMode()
    {
        msAssert.areEqual(LayoutMode.EDGE, DmlChartsEnum.dmlToLayoutMode("edge"));
        msAssert.areEqual(LayoutMode.FACTOR, DmlChartsEnum.dmlToLayoutMode("factor"));
    }

    @Test
    public void testToLayoutTarget()
    {
        msAssert.areEqual(LayoutTarget.INNER, DmlChartsEnum.dmlToLayoutTarget("inner"));
        msAssert.areEqual(LayoutTarget.OUTER, DmlChartsEnum.dmlToLayoutTarget("outer"));
    }

    @Test
    public void testToLegendPosition()
    {
        msAssert.areEqual(LegendPosition.BOTTOM, DmlChartsEnum.dmlToLegendPosition("b"));
        msAssert.areEqual(LegendPosition.LEFT, DmlChartsEnum.dmlToLegendPosition("l"));
        msAssert.areEqual(LegendPosition.RIGHT, DmlChartsEnum.dmlToLegendPosition("r"));
        msAssert.areEqual(LegendPosition.TOP, DmlChartsEnum.dmlToLegendPosition("t"));
        msAssert.areEqual(LegendPosition.TOP_RIGHT, DmlChartsEnum.dmlToLegendPosition("tr"));
    }

    @Test
    public void testToMarkerStyle()
    {
        msAssert.areEqual(MarkerSymbol.CIRCLE, DmlChartsEnum.dmlToMarkerStyle("circle"));
        msAssert.areEqual(MarkerSymbol.DASH, DmlChartsEnum.dmlToMarkerStyle("dash"));
        msAssert.areEqual(MarkerSymbol.DIAMOND, DmlChartsEnum.dmlToMarkerStyle("diamond"));
        msAssert.areEqual(MarkerSymbol.DOT, DmlChartsEnum.dmlToMarkerStyle("dot"));
        msAssert.areEqual(MarkerSymbol.NONE, DmlChartsEnum.dmlToMarkerStyle("none"));
        msAssert.areEqual(MarkerSymbol.PICTURE, DmlChartsEnum.dmlToMarkerStyle("picture"));
        msAssert.areEqual(MarkerSymbol.PLUS, DmlChartsEnum.dmlToMarkerStyle("plus"));
        msAssert.areEqual(MarkerSymbol.SQUARE, DmlChartsEnum.dmlToMarkerStyle("square"));
        msAssert.areEqual(MarkerSymbol.STAR, DmlChartsEnum.dmlToMarkerStyle("star"));
        msAssert.areEqual(MarkerSymbol.TRIANGLE, DmlChartsEnum.dmlToMarkerStyle("triangle"));
        msAssert.areEqual(MarkerSymbol.X, DmlChartsEnum.dmlToMarkerStyle("x"));
    }

    @Test
    public void testToOfPieType()
    {
        msAssert.areEqual(OfPieType.BAR, DmlChartsEnum.dmlToOfPieType("bar"));
        msAssert.areEqual(OfPieType.PIE, DmlChartsEnum.dmlToOfPieType("pie"));
    }

    @Test
    public void testToOrientation()
    {
        msAssert.areEqual(AxisOrientation.MAX_MIN, DmlChartsEnum.dmlToAxisOrientation("maxMin"));
        msAssert.areEqual(AxisOrientation.MIN_MAX, DmlChartsEnum.dmlToAxisOrientation("minMax"));
    }

    @Test
    public void testToPageSetupOrientation()
    {
        msAssert.areEqual(PageSetupOrientation.DEFAULT, DmlChartsEnum.dmlToPageSetupOrientation("default"));
        msAssert.areEqual(PageSetupOrientation.LANDSCAPE, DmlChartsEnum.dmlToPageSetupOrientation("landscape"));
        msAssert.areEqual(PageSetupOrientation.PORTRAIT, DmlChartsEnum.dmlToPageSetupOrientation("portrait"));
    }

    @Test
    public void testToPictureFormat()
    {
        msAssert.areEqual(PictureFormat.STACK, DmlChartsEnum.dmlToPictureFormat("stack"));
        msAssert.areEqual(PictureFormat.STACK_SCALE, DmlChartsEnum.dmlToPictureFormat("stackScale"));
        msAssert.areEqual(PictureFormat.STRETCH, DmlChartsEnum.dmlToPictureFormat("stretch"));
    }

    @Test
    public void testToRadarStyle()
    {
        msAssert.areEqual(RadarStyle.FILLED, DmlChartsEnum.dmlToRadarStyle("filled"));
        msAssert.areEqual(RadarStyle.MARKER, DmlChartsEnum.dmlToRadarStyle("marker"));
        msAssert.areEqual(RadarStyle.STANDARD, DmlChartsEnum.dmlToRadarStyle("standard"));
    }

    @Test
    public void testToScatterStyle()
    {
        msAssert.areEqual(ScatterStyle.LINE, DmlChartsEnum.dmlToScatterStyle("line"));
        msAssert.areEqual(ScatterStyle.LINE_MARKER, DmlChartsEnum.dmlToScatterStyle("lineMarker"));
        msAssert.areEqual(ScatterStyle.MARKER, DmlChartsEnum.dmlToScatterStyle("marker"));
        msAssert.areEqual(ScatterStyle.NONE, DmlChartsEnum.dmlToScatterStyle("none"));
        msAssert.areEqual(ScatterStyle.SMOOTH, DmlChartsEnum.dmlToScatterStyle("smooth"));
        msAssert.areEqual(ScatterStyle.SMOOTH_MARKER, DmlChartsEnum.dmlToScatterStyle("smoothMarker"));
    }

    @Test
    public void testToShape()
    {
        msAssert.areEqual(BarShape.BOX, DmlChartsEnum.dmlToShape("box"));
        msAssert.areEqual(BarShape.CONE, DmlChartsEnum.dmlToShape("cone"));
        msAssert.areEqual(BarShape.CONE_TO_MAX, DmlChartsEnum.dmlToShape("coneToMax"));
        msAssert.areEqual(BarShape.CYLINDER, DmlChartsEnum.dmlToShape("cylinder"));
        msAssert.areEqual(BarShape.PYRAMID, DmlChartsEnum.dmlToShape("pyramid"));
        msAssert.areEqual(BarShape.PYRAMID_TO_MAX, DmlChartsEnum.dmlToShape("pyramidToMax"));
    }

    @Test
    public void testToSizeRepresents()
    {
        msAssert.areEqual(SizeRepresents.AREA, DmlChartsEnum.dmlToSizeRepresents("area"));
        msAssert.areEqual(SizeRepresents.WIDTH, DmlChartsEnum.dmlToSizeRepresents("w"));
    }

    @Test
    public void testToSplitType()
    {
        msAssert.areEqual(SplitType.AUTO, DmlChartsEnum.dmlToSplitType("auto"));
        msAssert.areEqual(SplitType.CUSTOM, DmlChartsEnum.dmlToSplitType("cust"));
        msAssert.areEqual(SplitType.PERCENTAGE, DmlChartsEnum.dmlToSplitType("percent"));
        msAssert.areEqual(SplitType.POSITION, DmlChartsEnum.dmlToSplitType("pos"));
        msAssert.areEqual(SplitType.VALUE, DmlChartsEnum.dmlToSplitType("val"));
    }

    @Test
    public void testToTickLabelPosition()
    {
        msAssert.areEqual(AxisTickLabelPosition.HIGH, DmlChartsEnum.dmlToTickLabelPosition("high"));
        msAssert.areEqual(AxisTickLabelPosition.LOW, DmlChartsEnum.dmlToTickLabelPosition("low"));
        msAssert.areEqual(AxisTickLabelPosition.NEXT_TO_AXIS, DmlChartsEnum.dmlToTickLabelPosition("nextTo"));
        msAssert.areEqual(AxisTickLabelPosition.NONE, DmlChartsEnum.dmlToTickLabelPosition("none"));
    }

    @Test
    public void testToTickMark()
    {
        msAssert.areEqual(AxisTickMark.CROSS, DmlChartsEnum.dmlToTickMark("cross"));
        msAssert.areEqual(AxisTickMark.INSIDE, DmlChartsEnum.dmlToTickMark("in"));
        msAssert.areEqual(AxisTickMark.NONE, DmlChartsEnum.dmlToTickMark("none"));
        msAssert.areEqual(AxisTickMark.OUTSIDE, DmlChartsEnum.dmlToTickMark("out"));
    }

    @Test
    public void testToTimeUnit()
    {
        msAssert.areEqual(AxisTimeUnit.DAYS, DmlChartsEnum.dmlToTimeUnit("days"));
        msAssert.areEqual(AxisTimeUnit.MONTHS, DmlChartsEnum.dmlToTimeUnit("months"));
        msAssert.areEqual(AxisTimeUnit.YEARS, DmlChartsEnum.dmlToTimeUnit("years"));
    }

    @Test
    public void testToTrendlineType()
    {
        msAssert.areEqual(TrendlineType.EXPONENTIAL, DmlChartsEnum.dmlToTrendlineType("exp"));
        msAssert.areEqual(TrendlineType.LINEAR, DmlChartsEnum.dmlToTrendlineType("linear"));
        msAssert.areEqual(TrendlineType.LOGARITHMIC, DmlChartsEnum.dmlToTrendlineType("log"));
        msAssert.areEqual(TrendlineType.MOVING_AVERAGE, DmlChartsEnum.dmlToTrendlineType("movingAvg"));
        msAssert.areEqual(TrendlineType.POLYNOMIAL, DmlChartsEnum.dmlToTrendlineType("poly"));
        msAssert.areEqual(TrendlineType.POWER, DmlChartsEnum.dmlToTrendlineType("power"));
    }
}
