// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Contains methods for a bidirectional conversion from a preset geometry string to <see cref="ChartShapeType"/>.
/// </summary>
/*static*/ class ChartShapeTypeUtil
{
	/* Simulation of static class by using private constructor */
	private ChartShapeTypeUtil()
	{}

    /// <summary>
    /// Gets a <see cref="ChartShapeType"/> value for the specified preset geometry string.
    /// </summary>
    static /*ChartShapeType*/int presetGeometryToChartShapeType(String presetName)
    {
        return (/*ChartShapeType*/int)G_PRESET_TO_CHART_SHAPE_TYPE_MAP.getValue(presetName, (int)ChartShapeType.DEFAULT);
    }

    /// <summary>
    /// Gets a preset geometry string for the specified <see cref="ChartShapeType"/> value.
    /// </summary>
    static String chartShapeTypeToDmlPresetGeom(/*ChartShapeType*/int value)
    {
        return G_PRESET_TO_CHART_SHAPE_TYPE_MAP.getValue((int)value, "");
    }

    private static /*final*/ StringToIntBidirectionalMap G_PRESET_TO_CHART_SHAPE_TYPE_MAP = new StringToIntBidirectionalMap();

    /// <summary>
    /// Static ctor to generate a map between preset geometry strings and <see cref="ChartShapeType"/> values.
    /// </summary>
    static/* ChartShapeTypeUtil()*/
    {
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("accentBorderCallout1", (int)ChartShapeType.ACCENT_BORDER_CALLOUT_1);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("accentBorderCallout2", (int)ChartShapeType.ACCENT_BORDER_CALLOUT_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("accentBorderCallout3", (int)ChartShapeType.ACCENT_BORDER_CALLOUT_3);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("accentCallout1", (int)ChartShapeType.ACCENT_CALLOUT_1);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("accentCallout2", (int)ChartShapeType.ACCENT_CALLOUT_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("accentCallout3", (int)ChartShapeType.ACCENT_CALLOUT_3);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonBackPrevious", (int)ChartShapeType.ACTION_BUTTON_BACK_PREVIOUS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonBeginning", (int)ChartShapeType.ACTION_BUTTON_BEGINNING);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonBlank", (int)ChartShapeType.ACTION_BUTTON_BLANK);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonDocument", (int)ChartShapeType.ACTION_BUTTON_DOCUMENT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonEnd", (int)ChartShapeType.ACTION_BUTTON_END);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonForwardNext", (int)ChartShapeType.ACTION_BUTTON_FORWARD_NEXT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonHelp", (int)ChartShapeType.ACTION_BUTTON_HELP);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonHome", (int)ChartShapeType.ACTION_BUTTON_HOME);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonInformation", (int)ChartShapeType.ACTION_BUTTON_INFORMATION);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonMovie", (int)ChartShapeType.ACTION_BUTTON_MOVIE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonReturn", (int)ChartShapeType.ACTION_BUTTON_RETURN);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("actionButtonSound", (int)ChartShapeType.ACTION_BUTTON_SOUND);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bentConnector2", (int)ChartShapeType.BENT_CONNECTOR_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bentConnector3", (int)ChartShapeType.BENT_CONNECTOR_3);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bentConnector4", (int)ChartShapeType.BENT_CONNECTOR_4);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bentConnector5", (int)ChartShapeType.BENT_CONNECTOR_5);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bevel", (int)ChartShapeType.BEVEL);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("borderCallout1", (int)ChartShapeType.BORDER_CALLOUT_1);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("borderCallout2", (int)ChartShapeType.BORDER_CALLOUT_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("borderCallout3", (int)ChartShapeType.BORDER_CALLOUT_3);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bracePair", (int)ChartShapeType.BRACE_PAIR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bracketPair", (int)ChartShapeType.BRACKET_PAIR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("callout1", (int)ChartShapeType.CALLOUT_1);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("callout2", (int)ChartShapeType.CALLOUT_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("callout3", (int)ChartShapeType.CALLOUT_3);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("can", (int)ChartShapeType.CAN);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("chevron", (int)ChartShapeType.CHEVRON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("cloudCallout", (int)ChartShapeType.CLOUD_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("cube", (int)ChartShapeType.CUBE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedConnector2", (int)ChartShapeType.CURVED_CONNECTOR_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedConnector3", (int)ChartShapeType.CURVED_CONNECTOR_3);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedConnector4", (int)ChartShapeType.CURVED_CONNECTOR_4);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedConnector5", (int)ChartShapeType.CURVED_CONNECTOR_5);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedDownArrow", (int)ChartShapeType.CURVED_DOWN_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedLeftArrow", (int)ChartShapeType.CURVED_LEFT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedRightArrow", (int)ChartShapeType.CURVED_RIGHT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("curvedUpArrow", (int)ChartShapeType.CURVED_UP_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("diamond", (int)ChartShapeType.DIAMOND);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("donut", (int)ChartShapeType.DONUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("doubleWave", (int)ChartShapeType.DOUBLE_WAVE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("downArrow", (int)ChartShapeType.DOWN_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("downArrowCallout", (int)ChartShapeType.DOWN_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("ellipse", (int)ChartShapeType.ELLIPSE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("ellipseRibbon", (int)ChartShapeType.ELLIPSE_RIBBON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("ellipseRibbon2", (int)ChartShapeType.ELLIPSE_RIBBON_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartAlternateProcess", (int)ChartShapeType.FLOW_CHART_ALTERNATE_PROCESS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartCollate", (int)ChartShapeType.FLOW_CHART_COLLATE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartConnector", (int)ChartShapeType.FLOW_CHART_CONNECTOR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartDecision", (int)ChartShapeType.FLOW_CHART_DECISION);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartDelay", (int)ChartShapeType.FLOW_CHART_DELAY);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartDisplay", (int)ChartShapeType.FLOW_CHART_DISPLAY);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartDocument", (int)ChartShapeType.FLOW_CHART_DOCUMENT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartExtract", (int)ChartShapeType.FLOW_CHART_EXTRACT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartInputOutput", (int)ChartShapeType.FLOW_CHART_INPUT_OUTPUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartInternalStorage", (int)ChartShapeType.FLOW_CHART_INTERNAL_STORAGE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartMagneticDisk", (int)ChartShapeType.FLOW_CHART_MAGNETIC_DISK);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartMagneticDrum", (int)ChartShapeType.FLOW_CHART_MAGNETIC_DRUM);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartMagneticTape", (int)ChartShapeType.FLOW_CHART_MAGNETIC_TAPE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartManualInput", (int)ChartShapeType.FLOW_CHART_MANUAL_INPUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartManualOperation", (int)ChartShapeType.FLOW_CHART_MANUAL_OPERATION);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartMerge", (int)ChartShapeType.FLOW_CHART_MERGE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartMultidocument", (int)ChartShapeType.FLOW_CHART_MULTIDOCUMENT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartOfflineStorage", (int)ChartShapeType.FLOW_CHART_OFFLINE_STORAGE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartOffpageConnector", (int)ChartShapeType.FLOW_CHART_OFFPAGE_CONNECTOR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartOnlineStorage", (int)ChartShapeType.FLOW_CHART_ONLINE_STORAGE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartOr", (int)ChartShapeType.FLOW_CHART_OR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartPredefinedProcess",
            (int)ChartShapeType.FLOW_CHART_PREDEFINED_PROCESS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartPreparation", (int)ChartShapeType.FLOW_CHART_PREPARATION);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartProcess", (int)ChartShapeType.FLOW_CHART_PROCESS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartPunchedCard", (int)ChartShapeType.FLOW_CHART_PUNCHED_CARD);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartPunchedTape", (int)ChartShapeType.FLOW_CHART_PUNCHED_TAPE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartSort", (int)ChartShapeType.FLOW_CHART_SORT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartSummingJunction", (int)ChartShapeType.FLOW_CHART_SUMMING_JUNCTION);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("flowChartTerminator", (int)ChartShapeType.FLOW_CHART_TERMINATOR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("foldedCorner", (int)ChartShapeType.FOLDED_CORNER);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("hexagon", (int)ChartShapeType.HEXAGON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("homePlate", (int)ChartShapeType.HOME_PLATE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("horizontalScroll", (int)ChartShapeType.HORIZONTAL_SCROLL);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("irregularSeal1", (int)ChartShapeType.IRREGULAR_SEAL_1);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("irregularSeal2", (int)ChartShapeType.IRREGULAR_SEAL_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftArrow", (int)ChartShapeType.LEFT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftArrowCallout", (int)ChartShapeType.LEFT_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftBrace", (int)ChartShapeType.LEFT_BRACE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftBracket", (int)ChartShapeType.LEFT_BRACKET);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftRightArrow", (int)ChartShapeType.LEFT_RIGHT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftRightArrowCallout", (int)ChartShapeType.LEFT_RIGHT_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("lightningBolt", (int)ChartShapeType.LIGHTNING_BOLT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("line", (int)ChartShapeType.LINE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("moon", (int)ChartShapeType.MOON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("noSmoking", (int)ChartShapeType.NO_SMOKING);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("notchedRightArrow", (int)ChartShapeType.NOTCHED_RIGHT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("octagon", (int)ChartShapeType.OCTAGON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("parallelogram", (int)ChartShapeType.PARALLELOGRAM);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("pentagon", (int)ChartShapeType.PENTAGON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("plaque", (int)ChartShapeType.PLAQUE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("plus", (int)ChartShapeType.PLUS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("rect", (int)ChartShapeType.RECTANGLE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("ribbon", (int)ChartShapeType.RIBBON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("ribbon2", (int)ChartShapeType.RIBBON_2);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("rightArrow", (int)ChartShapeType.ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("rightArrowCallout", (int)ChartShapeType.RIGHT_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("rightBrace", (int)ChartShapeType.RIGHT_BRACE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("rightBracket", (int)ChartShapeType.RIGHT_BRACKET);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("smileyFace", (int)ChartShapeType.SMILEY_FACE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star16", (int)ChartShapeType.SEAL_16);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star32", (int)ChartShapeType.SEAL_32);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star4", (int)ChartShapeType.SEAL_4);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star8", (int)ChartShapeType.SEAL_8);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("straightConnector1", (int)ChartShapeType.STRAIGHT_CONNECTOR_1);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("stripedRightArrow", (int)ChartShapeType.STRIPED_RIGHT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("sun", (int)ChartShapeType.SUN);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("triangle", (int)ChartShapeType.TRIANGLE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("upArrow", (int)ChartShapeType.UP_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("upArrowCallout", (int)ChartShapeType.UP_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("upDownArrow", (int)ChartShapeType.UP_DOWN_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("upDownArrowCallout", (int)ChartShapeType.UP_DOWN_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("verticalScroll", (int)ChartShapeType.VERTICAL_SCROLL);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("wave", (int)ChartShapeType.WAVE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("wedgeEllipseCallout", (int)ChartShapeType.WEDGE_ELLIPSE_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("wedgeRectCallout", (int)ChartShapeType.WEDGE_RECT_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("wedgeRoundRectCallout", (int)ChartShapeType.WEDGE_R_RECT_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("roundRect", (int)ChartShapeType.ROUND_RECTANGLE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("rtTriangle", (int)ChartShapeType.RIGHT_TRIANGLE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("quadArrowCallout", (int)ChartShapeType.QUAD_ARROW_CALLOUT);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("quadArrow", (int)ChartShapeType.QUAD_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftUpArrow", (int)ChartShapeType.LEFT_UP_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bentArrow", (int)ChartShapeType.BENT_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("bentUpArrow", (int)ChartShapeType.BENT_UP_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star5", (int)ChartShapeType.STAR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("uturnArrow", (int)ChartShapeType.UTURN_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("trapezoid", (int)ChartShapeType.TRAPEZOID);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftRightUpArrow", (int)ChartShapeType.LEFT_RIGHT_UP_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("heart", (int)ChartShapeType.HEART);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("circularArrow", (int)ChartShapeType.CIRCULAR_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("blockArc", (int)ChartShapeType.BLOCK_ARC);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("arc", (int)ChartShapeType.ARC);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("heptagon", (int)ChartShapeType.HEPTAGON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("cloud", (int)ChartShapeType.CLOUD);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star6", (int)ChartShapeType.SEAL_6);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star7", (int)ChartShapeType.SEAL_7);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star10", (int)ChartShapeType.SEAL_10);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star12", (int)ChartShapeType.SEAL_12);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("star24", (int)ChartShapeType.SEAL_24);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("swooshArrow", (int)ChartShapeType.SWOOSH_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("teardrop", (int)ChartShapeType.TEARDROP);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("squareTabs", (int)ChartShapeType.SQUARE_TABS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("plaqueTabs", (int)ChartShapeType.PLAQUE_TABS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("pie", (int)ChartShapeType.PIE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("pieWedge", (int)ChartShapeType.WEDGE_PIE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("lineInv", (int)ChartShapeType.INVERSE_LINE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("mathDivide", (int)ChartShapeType.MATH_DIVIDE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("mathEqual", (int)ChartShapeType.MATH_EQUAL);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("mathMinus", (int)ChartShapeType.MATH_MINUS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("mathMultiply", (int)ChartShapeType.MATH_MULTIPLY);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("mathNotEqual", (int)ChartShapeType.MATH_NOT_EQUAL);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("mathPlus", (int)ChartShapeType.MATH_PLUS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("nonIsoscelesTrapezoid", (int)ChartShapeType.NON_ISOSCELES_TRAPEZOID);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftRightCircularArrow", (int)ChartShapeType.LEFT_RIGHT_CIRCULAR_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftRightRibbon", (int)ChartShapeType.LEFT_RIGHT_RIBBON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("leftCircularArrow", (int)ChartShapeType.LEFT_CIRCULAR_ARROW);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("frame", (int)ChartShapeType.FRAME);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("funnel", (int)ChartShapeType.FUNNEL);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("gear6", (int)ChartShapeType.GEAR_6);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("gear9", (int)ChartShapeType.GEAR_9);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("halfFrame", (int)ChartShapeType.HALF_FRAME);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("decagon", (int)ChartShapeType.DECAGON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("diagStripe", (int)ChartShapeType.DIAGONAL_STRIPE);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("dodecagon", (int)ChartShapeType.DODECAGON);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("corner", (int)ChartShapeType.CORNER);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("cornerTabs", (int)ChartShapeType.CORNER_TABS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("chord", (int)ChartShapeType.CHORD);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("chartPlus", (int)ChartShapeType.CHART_PLUS);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("chartStar", (int)ChartShapeType.CHART_STAR);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("chartX", (int)ChartShapeType.CHART_X);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("snip1Rect", (int)ChartShapeType.SINGLE_CORNER_SNIPPED);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("snip2DiagRect", (int)ChartShapeType.DIAGONAL_CORNERS_SNIPPED);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("snip2SameRect", (int)ChartShapeType.TOP_CORNERS_SNIPPED);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("snipRoundRect", (int)ChartShapeType.TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("round1Rect", (int)ChartShapeType.SINGLE_CORNER_ROUNDED);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("round2DiagRect", (int)ChartShapeType.DIAGONAL_CORNERS_ROUNDED);
        G_PRESET_TO_CHART_SHAPE_TYPE_MAP.addEntry("round2SameRect", (int)ChartShapeType.TOP_CORNERS_ROUNDED);
    }
}

