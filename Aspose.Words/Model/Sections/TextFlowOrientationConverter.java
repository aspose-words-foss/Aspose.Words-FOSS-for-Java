// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2015 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*static*/ class TextFlowOrientationConverter
{
	/* Simulation of static class by using private constructor */
	private TextFlowOrientationConverter()
	{}

    static /*TextOrientation*/int fromTextFlow(/*TextFlow*/int textFlow)
    {
        switch (textFlow)
        {
            case TextFlow.HORIZONTAL:
                return TextOrientation.HORIZONTAL;
            case TextFlow.HORIZONTAL_ROTATED_FAR_EAST:
                return TextOrientation.HORIZONTAL_ROTATED_FAR_EAST;
            case TextFlow.LR_BT:
                return  TextOrientation.UPWARD;
            case TextFlow.V_NON_V:
                return TextOrientation.VERTICAL_FAR_EAST;
            case TextFlow.VERTICAL:
                return TextOrientation.DOWNWARD;
            case TextFlow.VERTICAL_ROTATED_FAR_EAST:
                return TextOrientation.VERTICAL_ROTATED_FAR_EAST;
            case TextFlow.RL_TB:
                return TextOrientation.UPWARD;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "textFlow");
        }
    }

    static /*TextFlow*/int fromTextOrientation(/*TextOrientation*/int textOrientation)
    {
        switch (textOrientation)
        {
                case TextOrientation.DOWNWARD:
                    return TextFlow.VERTICAL;
                case TextOrientation.HORIZONTAL:
                    return TextFlow.HORIZONTAL;
                case TextOrientation.HORIZONTAL_ROTATED_FAR_EAST:
                    return TextFlow.HORIZONTAL_ROTATED_FAR_EAST;
                case TextOrientation.UPWARD:
                    return TextFlow.LR_BT;
                case TextOrientation.VERTICAL_FAR_EAST:
                    return TextFlow.V_NON_V;
                case TextOrientation.VERTICAL_ROTATED_FAR_EAST:
                    return TextFlow.VERTICAL_ROTATED_FAR_EAST;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "textOrientation");
        }
    }
}

