// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringToIntBidirectionalMap;
import com.aspose.IntToIntBidirectionalMap;
import com.aspose.ms.System.Drawing.Drawing2D.HatchStyle;
import com.aspose.DrPixels;
import com.aspose.BitmapPal;
import com.aspose.HashUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Converts shape enumerated types between enum and WML string.
/// </summary>
class VmlEnum
{
    static /*ScriptLanguage*/int vmlToScriptLanguage(String value)
    {
        return (/*ScriptLanguage*/int)G_SCRIPT_LANGUAGE_MAP.getValue(value, (int)ScriptLanguage.NONE);
    }

    static String scriptLanguageToVml(/*ScriptLanguage*/int value)
    {
        return G_SCRIPT_LANGUAGE_MAP.getValue((int)value, "");
    }

    static String scriptLanguageToVml(Object value)
    {
        // "JavaScript" is a default value.
        if (value == null)
            return "JavaScript";

        if (!(value instanceof /*int*/Integer))
            throw new IllegalStateException("Invalid value for script language attribute.");

        return scriptLanguageToVml((/*ScriptLanguage*/int)(/*int*/Integer)value);
    }

    static /*Operation*/int vmlToOperation(String value)
    {
        return (/*Operation*/int)G_OPERATION_MAP.getValue(value, (int)Operation.SUM);
    }

    static String operationToVml(/*Operation*/int value)
    {
        return G_OPERATION_MAP.getValue((int)value, "");
    }

    static /*BWMode*/int vmlToBWMode(String value)
    {
        return (/*BWMode*/int)G_BW_MODE_MAP.getValue(value, (int)BWMode.AUTOMATIC);
    }

    static String bWModeToVml(/*BWMode*/int value)
    {
        return G_BW_MODE_MAP.getValue((int)value, "");
    }

    static /*OleLinkType*/int vmlToOleLinkType(String value)
    {
        return (/*OleLinkType*/int)G_OLE_LINK_TYPE_MAP.getValue(value, (int)OleLinkType.DEFAULT);
    }

    static String oleLinkTypeToVml(/*OleLinkType*/int value)
    {
        return G_OLE_LINK_TYPE_MAP.getValue((int)value, "");
    }

    static /*HorizontalRuleAlignment*/int vmlToHRAlign(String value)
    {
        return (/*HorizontalRuleAlignment*/int)G_HR_ALIGN_MAP.getValue(value, (int)HorizontalRuleAlignment.LEFT);
    }

    static String hRAlignToVml(/*HorizontalRuleAlignment*/int value)
    {
        return G_HR_ALIGN_MAP.getValue((int)value, "");
    }

    static /*TextPathAlignment*/int vmlToTextPathAlignment(String value)
    {
        return (/*TextPathAlignment*/int)G_TEXT_PATH_ALIGNMENT_MAP.getValue(value, (int)TextPathAlignment.LEFT);
    }

    static String textPathAlignmentToVml(/*TextPathAlignment*/int value)
    {
        return G_TEXT_PATH_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*ConnectionSiteType*/int vmlToConnectionSiteType(String value)
    {
        return (/*ConnectionSiteType*/int)G_CONNECTION_SITE_TYPE_MAP.getValue(value, (int)ConnectionSiteType.NONE);
    }

    static String connectionSiteTypeToVml(/*ConnectionSiteType*/int value)
    {
        return G_CONNECTION_SITE_TYPE_MAP.getValue((int)value, "");
    }

    static /*LineStyle*/int vmlToLineStyle(String value)
    {
        return (/*LineStyle*/int)G_LINE_STYLE_MAP.getValue(value, (int)LineStyle.NONE);
    }

    static String lineStyleToVml(/*LineStyle*/int value)
    {
        return G_LINE_STYLE_MAP.getValue((int)value, "");
    }

    static /*CalloutDropType*/int vmlToCalloutDropType(String value)
    {
        return (/*CalloutDropType*/int)G_CALLOUT_DROP_TYPE_MAP.getValue(value, (int)CalloutDropType.SPECIFIED);
    }

    static String calloutDropTypeToVml(/*CalloutDropType*/int value)
    {
        return G_CALLOUT_DROP_TYPE_MAP.getValue((int)value, "");
    }

    static /*CalloutAngle*/int vmlToCalloutAngle(String value)
    {
        return (/*CalloutAngle*/int)G_CALLOUT_ANGLE_MAP.getValue(value, (int)CalloutAngle.ANY);
    }

    static String calloutAngleToVml(/*CalloutAngle*/int value)
    {
        return G_CALLOUT_ANGLE_MAP.getValue((int)value, "");
    }

    static /*DashStyle*/int vmlToDashStyle(String value)
    {
        return (/*DashStyle*/int)G_DASH_STYLE_MAP.getValue(value, (int)DashStyle.SOLID);
    }

    static String dashStyleToVml(/*DashStyle*/int value)
    {
        return G_DASH_STYLE_MAP.getValue((int)value, "");
    }

    static /*JoinStyle*/int vmlToJoinStyle(String value)
    {
        return (/*JoinStyle*/int)G_JOIN_STYLE_MAP.getValue(value, (int)JoinStyle.ROUND);
    }

    static String joinStyleToVml(/*JoinStyle*/int value)
    {
        return G_JOIN_STYLE_MAP.getValue((int)value, "");
    }

    static /*ShapeLineStyle*/int vmlToShapeLineStyle(String value)
    {
        return (/*ShapeLineStyle*/int)G_SHAPE_LINE_STYLE_MAP.getValue(value, (int)ShapeLineStyle.SINGLE);
    }

    static String shapeLineStyleToVml(/*ShapeLineStyle*/int value)
    {
        return G_SHAPE_LINE_STYLE_MAP.getValue((int)value, "");
    }

    static /*EndCap*/int vmlToEndCap(String value)
    {
        return (/*EndCap*/int)G_END_CAP_MAP.getValue(value, (int)EndCap.FLAT);
    }

    static String endCapToVml(/*EndCap*/int value)
    {
        return G_END_CAP_MAP.getValue((int)value, "");
    }

    static /*ArrowWidth*/int vmlToArrowWidth(String value)
    {
        return (/*ArrowWidth*/int)G_ARROW_WIDTH_MAP.getValue(value, (int)ArrowWidth.MEDIUM);
    }

    static String arrowWidthToVml(/*ArrowWidth*/int value)
    {
        return G_ARROW_WIDTH_MAP.getValue((int)value, "");
    }

    static /*ArrowLength*/int vmlToArrowLength(String value)
    {
        return (/*ArrowLength*/int)G_ARROW_LENGTH_MAP.getValue(value, (int)ArrowLength.MEDIUM);
    }

    static String arrowLengthToVml(/*ArrowLength*/int value)
    {
        return G_ARROW_LENGTH_MAP.getValue((int)value, "");
    }

    static /*ArrowType*/int vmlToArrowType(String value)
    {
        return (/*ArrowType*/int)G_ARROW_TYPE_MAP.getValue(value, (int)ArrowType.NONE);
    }

    static String arrowTypeToVml(/*ArrowType*/int value)
    {
        return G_ARROW_TYPE_MAP.getValue((int)value, "");
    }

    static /*LineFillType*/int vmlToLineFillType(String value)
    {
        return (/*LineFillType*/int)G_LINE_FILL_TYPE_MAP.getValue(value, (int)LineFillType.SOLID);
    }

    static String lineFillTypeToVml(/*LineFillType*/int value)
    {
        return G_LINE_FILL_TYPE_MAP.getValue((int)value, "");
    }

    static /*PlaneType*/int vmlToPlaneType(String value)
    {
        return (/*PlaneType*/int)G_PLANE_TYPE_MAP.getValue(value, (int)PlaneType.XY);
    }

    static String planeTypeToVml(/*PlaneType*/int value)
    {
        return G_PLANE_TYPE_MAP.getValue((int)value, "");
    }

    static /*ThreeDRenderMode*/int vmlToThreeDRenderMode(String value)
    {
        return (/*ThreeDRenderMode*/int)G_THREE_D_RENDER_MODE_MAP.getValue(value, (int)ThreeDRenderMode.FULL_RENDER);
    }

    static String threeDRenderModeToVml(/*ThreeDRenderMode*/int value)
    {
        return G_THREE_D_RENDER_MODE_MAP.getValue((int)value, "");
    }

    static /*ShadowTypeCore*/int vmlToShadowType(String value)
    {
        return (/*ShadowTypeCore*/int)G_SHADOW_TYPE_MAP.getValue(value, (int)ShadowTypeCore.OFFSET);
    }

    static String shadowTypeToVml(/*ShadowTypeCore*/int value)
    {
        return G_SHADOW_TYPE_MAP.getValue((int)value, "");
    }

    static /*CalloutType*/int vmlToCalloutType(String value)
    {
        return (/*CalloutType*/int)G_CALLOUT_TYPE_MAP.getValue(value, (int)CalloutType.ONE_SEGMENT);
    }

    static String calloutTypeToVml(/*CalloutType*/int value)
    {
        return G_CALLOUT_TYPE_MAP.getValue((int)value, "");
    }

    static /*LayoutFlow*/int vmlToLayoutFlow(String value)
    {
        return (/*LayoutFlow*/int)G_LAYOUT_FLOW_MAP.getValue(value, (int)LayoutFlow.HORIZONTAL);
    }

    static String layoutFlowToVml(/*LayoutFlow*/int value)
    {
        return G_LAYOUT_FLOW_MAP.getValue((int)value, "");
    }

    static /*TextBoxAnchor*/int vmlToTextboxAnchor(String value)
    {
        return (/*TextBoxAnchor*/int)G_TEXTBOX_ANCHOR_MAP.getValue(value, (int)TextBoxAnchor.TOP);
    }

    static String textboxAnchorToVml(/*TextBoxAnchor*/int value)
    {
        return G_TEXTBOX_ANCHOR_MAP.getValue((int)value, "");
    }

    static /*TextBoxWrapMode*/int vmlToTextboxWrapMode(String value)
    {
        // WORDSNET-10078.
        // TextBoxWrapMode.Square seems to be the default value for MW.
        return (/*TextBoxWrapMode*/int)G_TEXTBOX_WRAP_MODE_MAP.getValue(value, (int)TextBoxWrapMode.SQUARE);
    }

    static String textboxWrapModeToVml(/*TextBoxWrapMode*/int value)
    {
        return G_TEXTBOX_WRAP_MODE_MAP.getValue((int)value, "");
    }

    static /*WrapSide*/int vmlToWrapSide(String value)
    {
        return (/*WrapSide*/int)G_WRAP_SIDE_MAP.getValue(value, (int)WrapSide.BOTH);
    }

    static String wrapSideToVml(/*WrapSide*/int value)
    {
        return G_WRAP_SIDE_MAP.getValue((int)value, "");
    }

    static /*ConnectorType*/int vmlToConnectorType(String value)
    {
        return (/*ConnectorType*/int)G_CONNECTOR_TYPE_MAP.getValue(value, (int)ConnectorType.NONE);
    }

    static String connectorTypeToVml(/*ConnectorType*/int value)
    {
        return G_CONNECTOR_TYPE_MAP.getValue((int)value, "");
    }

    static String dzTypeToVml(/*FillDimensionType*/int value)
    {
        switch (value)
        {
            case FillDimensionType.FIXED_RATIO_ENLARGE:
                return "atLeast";
            case FillDimensionType.FIXED_ASPECT:
                return "atMost";
            default:
                return "";
        }
    }

    //JAVA: declarations moved here to exclude java's illegal forward reference.

    private static /*final*/ StringToIntBidirectionalMap G_SCRIPT_LANGUAGE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_OPERATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_BW_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_OLE_LINK_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_HR_ALIGN_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_PATH_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CONNECTION_SITE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LINE_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CALLOUT_DROP_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CALLOUT_ANGLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_DASH_STYLE_MAP = new StringToIntBidirectionalMap(false);
    private static /*final*/ StringToIntBidirectionalMap G_JOIN_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SHAPE_LINE_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_END_CAP_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ARROW_WIDTH_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ARROW_LENGTH_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ARROW_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LINE_FILL_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PLANE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_THREE_D_RENDER_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SHADOW_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CALLOUT_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LAYOUT_FLOW_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXTBOX_ANCHOR_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXTBOX_WRAP_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_WRAP_SIDE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CONNECTOR_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_EDIT_AS_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_FLIP_ORIENTATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_WRAP_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PATH_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_VML_HATCH_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_VML_FILL_BLIP_NAME_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ IntToIntBidirectionalMap G_LOCKS_MAP = new IntToIntBidirectionalMap();

    static /*FillDimensionType*/int vmlToDzType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"atLeast"*/0:
                return FillDimensionType.FIXED_RATIO_ENLARGE;
            case /*"atMost"*/1:
                return FillDimensionType.FIXED_ASPECT;
            default:
                return FillDimensionType.DEFAULT;
        }
    }

    static /*EditAs*/int vmlToEditAs(String value)
    {
        return (/*EditAs*/int)G_EDIT_AS_MAP.getValue(value, (int)EditAs.GROUP);
    }

    static String editAsToVml(/*EditAs*/int value)
    {
        return G_EDIT_AS_MAP.getValue((int)value, "");
    }

    static String fillTypeToVml(/*FillTypeCore*/int value)
    {
        // We don't use EnumUtil here, because the relationship is not one-to-one.
        switch (value)
        {
            case FillTypeCore.SOLID:
                return "solid";
            case FillTypeCore.SHADE_SHAPE:
            case FillTypeCore.SHADE_CENTER:
                return "gradientRadial";
            case FillTypeCore.SHADE_SCALE:
            case FillTypeCore.SHADE_UNSCALE:
            // WORDSNET-18702 The analogue of FillType.Shade in VML is fill type 'gradient'.
            case FillTypeCore.SHADE:
                return "gradient";
            case FillTypeCore.PATTERN:
                return "pattern";
            case FillTypeCore.PICTURE:
                return "frame";
            case FillTypeCore.TEXTURE:
                return "tile";
            default:
                return "";
        }
    }

    static /*FillTypeCore*/int vmlToFillType(String value)
    {
        // We don't use EnumUtil here, because the relationship is not one-to-one.
        switch (gStringSwitchMap.of(value))
        {
            case /*"solid"*/2:
                return FillTypeCore.SOLID;
            case /*"gradientRadial"*/3:
                // Can also be ShadeCenter, to find out additionally check o:fill child of v:fill element.
                return FillTypeCore.SHADE_SHAPE;
            case /*"gradient"*/4:
                // andrnosk: WORDSNET-4146 Can also be ShadeUnscale, to find out additionally check o:fill child of v:fill element.
                return FillTypeCore.SHADE_SCALE;
            case /*"pattern"*/5:
                return FillTypeCore.PATTERN;
            case /*"frame"*/6:
                return FillTypeCore.PICTURE;
            case /*"tile"*/7:
                return FillTypeCore.TEXTURE;
            default:
                return FillTypeCore.SOLID;
        }
    }

    static /*FlipOrientation*/int vmlToFlipOrientation(String value)
    {
        return (/*FlipOrientation*/int)G_FLIP_ORIENTATION_MAP.getValue(value, (int)FlipOrientation.NONE);
    }

    static String flipOrientationToVml(/*FlipOrientation*/int value)
    {
        return G_FLIP_ORIENTATION_MAP.getValue((int)value, "");
    }

    static /*WrapType*/int vmlToWrapType(String value)
    {
        return (/*WrapType*/int)G_WRAP_TYPE_MAP.getValue(value, (int)WrapType.NONE);
    }

    static String wrapTypeToVml(/*WrapType*/int value)
    {
        // WrapType.None and WrapType.Inline are specified via the shape "position".
        return (value == WrapType.NONE || value == WrapType.INLINE)
            ? ""
            : G_WRAP_TYPE_MAP.getValue((int)value, "");
    }

    static /*PathType*/int vmlToPathType(String value)
    {
        return (/*PathType*/int)G_PATH_TYPE_MAP.getValue(value, (int)PathType.UNKNOWN);
    }

    static String pathTypeToVml(/*PathType*/int value)
    {
        return G_PATH_TYPE_MAP.getValue((int)value, "");
    }

    static String hatchStyleToVml(/*HatchStyle*/int value)
    {
        if (value == HatchStyle.LARGE_GRID)
            return gLGridFillName;

        return G_VML_HATCH_STYLE_MAP.getValue((int)value, "");
    }

    /// <summary>
    /// Determines hatch style using specified image data.
    /// </summary>
    /// <param name="binData">Image data to determine hatch style.</param>
    /// <returns>Hatch style.</returns>
    static /*HatchStyle*/int getHatchStyle(byte[] binData) throws Exception
    {
        if (binData == null)
            return HatchStyle.MIN;

        DrPixels pixels;

        // Use colors of the bitonal image to determine hatch style.
        BitmapPal bitmap = new BitmapPal(binData);
        try /*JAVA: was using*/
    	{
            pixels = bitmap.getPixels(true);
    	}
        finally { if (bitmap != null) bitmap.dispose(); }

        // alexnosk: In netstandard, .net and mono different algorithms are used to calculate hash code of Guid.
        // This leads into different key values. To fix this use code from .NET to calculate hash code of Guid.
        int hash = HashUtil.getSHA512Hash(pixels.getColorValues()).hashCode();
        String fillName = G_VML_FILL_BLIP_NAME_MAP.getValue(hash, "").toLowerCase();

        if (msString.equals(fillName, gLGridFillName))
            return HatchStyle.LARGE_GRID;

        return (/*HatchStyle*/int)G_VML_HATCH_STYLE_MAP.getValue(fillName, (int)HatchStyle.MIN);
    }

    /// <summary>
    /// Determines PatternType using specified image data.
    /// </summary>
    /// <param name="binData">Image data to determine hatch style.</param>
    /// <returns><see cref="PatternType"/></returns>
    static /*PatternType*/int getPatternType(byte[] binData) throws Exception
    {
        /*HatchStyle*/int style = getHatchStyle(binData);
        return FillUtil.hatchStyleToPatternType(style);
    }

    static /*DmlLock*/int vmlToDmlLock(int lockKey)
    {
        return (/*DmlLock*/int)G_LOCKS_MAP.getValueDirect(lockKey, (int)DmlLock.NO_CHANGE_ASPECT);
    }

    /// <summary>
    /// Add values to hatch mapping collections.
    /// </summary>
    /// <param name="fName">Hatch name.</param>
    /// <param name="hStyle">Hatch value.</param>
    /// <param name="hash">Hash code of the gif image for specified hatch style name.</param>
    private static void addToFillMap(String fName, /*HatchStyle*/int hStyle, int hash)
    {
        if (!msString.equals(fName.toLowerCase(), gLGridFillName))
            G_VML_HATCH_STYLE_MAP.addEntry(fName, (int)hStyle);

        if (hash != 0)
            G_VML_FILL_BLIP_NAME_MAP.addEntry(fName, hash);
    }

    static final /*GradientType*/int LINEAR_SIGMA_GRADIENT = (/*GradientType*/int)0x4000000b;
    private static String gLGridFillName = "large grid";

    static/* VmlEnum()*/
    {
        G_WRAP_TYPE_MAP.addEntry("none", (int)WrapType.NONE);
        G_WRAP_TYPE_MAP.addEntry("inline", (int)WrapType.INLINE);
        G_WRAP_TYPE_MAP.addEntry("square", (int)WrapType.SQUARE);
        G_WRAP_TYPE_MAP.addEntry("tight", (int)WrapType.TIGHT);
        G_WRAP_TYPE_MAP.addEntry("topAndBottom", (int)WrapType.TOP_BOTTOM);
        G_WRAP_TYPE_MAP.addEntry("through", (int)WrapType.THROUGH);

        G_FLIP_ORIENTATION_MAP.addEntry("x", (int)FlipOrientation.HORIZONTAL);
        G_FLIP_ORIENTATION_MAP.addEntry("y", (int)FlipOrientation.VERTICAL);
        G_FLIP_ORIENTATION_MAP.addEntry("x y", (int)FlipOrientation.BOTH);

        G_EDIT_AS_MAP.addEntry("canvas", (int)EditAs.CANVAS);
        G_EDIT_AS_MAP.addEntry("orgchart", (int)EditAs.ORGANIZATIONAL_CHART);
        G_EDIT_AS_MAP.addEntry("radial", (int)EditAs.RADIAL);
        G_EDIT_AS_MAP.addEntry("cycle", (int)EditAs.CYCLE);
        G_EDIT_AS_MAP.addEntry("stacked", (int)EditAs.PYRAMID);
        G_EDIT_AS_MAP.addEntry("venn", (int)EditAs.VENN);
        G_EDIT_AS_MAP.addEntry("bullseye", (int)EditAs.TARGET);

        G_CONNECTOR_TYPE_MAP.addEntry("none", (int)ConnectorType.NONE);
        G_CONNECTOR_TYPE_MAP.addEntry("straight", (int)ConnectorType.STRAIGHT);
        G_CONNECTOR_TYPE_MAP.addEntry("elbow", (int)ConnectorType.BENT);
        G_CONNECTOR_TYPE_MAP.addEntry("curved", (int)ConnectorType.CURVED);

        G_WRAP_SIDE_MAP.addEntry("", (int)WrapSide.BOTH);
        G_WRAP_SIDE_MAP.addEntry("left", (int)WrapSide.LEFT);
        G_WRAP_SIDE_MAP.addEntry("right", (int)WrapSide.RIGHT);
        G_WRAP_SIDE_MAP.addEntry("largest", (int)WrapSide.LARGEST);

        G_TEXTBOX_WRAP_MODE_MAP.addEntry("none", (int)TextBoxWrapMode.NONE);
        G_TEXTBOX_WRAP_MODE_MAP.addEntry("square", (int)TextBoxWrapMode.SQUARE);

        G_TEXTBOX_ANCHOR_MAP.addEntry("top", (int)TextBoxAnchor.TOP);
        G_TEXTBOX_ANCHOR_MAP.addEntry("middle", (int)TextBoxAnchor.MIDDLE);
        G_TEXTBOX_ANCHOR_MAP.addEntry("bottom", (int)TextBoxAnchor.BOTTOM);
        G_TEXTBOX_ANCHOR_MAP.addEntry("top-center", (int)TextBoxAnchor.TOP_CENTERED);
        G_TEXTBOX_ANCHOR_MAP.addEntry("middle-center", (int)TextBoxAnchor.MIDDLE_CENTERED);
        G_TEXTBOX_ANCHOR_MAP.addEntry("bottom-center", (int)TextBoxAnchor.BOTTOM_CENTERED);
        G_TEXTBOX_ANCHOR_MAP.addEntry("top-baseline", (int)TextBoxAnchor.TOP_BASELINE);
        G_TEXTBOX_ANCHOR_MAP.addEntry("bottom-baseline", (int)TextBoxAnchor.BOTTOM_BASELINE);
        G_TEXTBOX_ANCHOR_MAP.addEntry("top-center-baseline", (int)TextBoxAnchor.TOP_CENTERED_BASELINE);
        G_TEXTBOX_ANCHOR_MAP.addEntry("bottom-center-baseline", (int)TextBoxAnchor.BOTTOM_CENTERED_BASELINE);

        G_LAYOUT_FLOW_MAP.addEntry("horizontal", (int)LayoutFlow.HORIZONTAL);
        G_LAYOUT_FLOW_MAP.addEntry("vertical", (int)LayoutFlow.TOP_TO_BOTTOM);
        G_LAYOUT_FLOW_MAP.addEntry("vertical-ideographic", (int)LayoutFlow.TOP_TO_BOTTOM_IDEOGRAPHIC);
        G_LAYOUT_FLOW_MAP.addEntry("horizontal-ideographic", (int)LayoutFlow.HORIZONTAL_IDEOGRAPHIC);

        G_CALLOUT_TYPE_MAP.addEntry("oneSegment", (int)CalloutType.ONE_SEGMENT);
        G_CALLOUT_TYPE_MAP.addEntry("twoSegment", (int)CalloutType.TWO_SEGMENT);
        G_CALLOUT_TYPE_MAP.addEntry("threeSegment", (int)CalloutType.THREE_SEGMENT);
        G_CALLOUT_TYPE_MAP.addEntry("rightAngle", (int)CalloutType.RIGHT_ANGLE);

        G_SHADOW_TYPE_MAP.addEntry("single", (int)ShadowTypeCore.OFFSET);
        G_SHADOW_TYPE_MAP.addEntry("double", (int)ShadowTypeCore.DOUBLE);
        G_SHADOW_TYPE_MAP.addEntry("perspective", (int)ShadowTypeCore.RICH);
        G_SHADOW_TYPE_MAP.addEntry("shapeRelative", (int)ShadowTypeCore.SHAPE);
        G_SHADOW_TYPE_MAP.addEntry("drawingRelative", (int)ShadowTypeCore.DRAWING);
        G_SHADOW_TYPE_MAP.addEntry("emboss", (int)ShadowTypeCore.EMBOSS_OR_ENGRAVE);

        G_THREE_D_RENDER_MODE_MAP.addEntry("solid", (int)ThreeDRenderMode.FULL_RENDER);
        G_THREE_D_RENDER_MODE_MAP.addEntry("wireFrame", (int)ThreeDRenderMode.WIREFRAME);
        G_THREE_D_RENDER_MODE_MAP.addEntry("boundingCube", (int)ThreeDRenderMode.BOUNDING_CUBE);

        G_PLANE_TYPE_MAP.addEntry("XY", (int)PlaneType.XY);
        G_PLANE_TYPE_MAP.addEntry("ZX", (int)PlaneType.ZX);
        G_PLANE_TYPE_MAP.addEntry("YZ", (int)PlaneType.YZ);

        G_LINE_FILL_TYPE_MAP.addEntry("solid", (int)LineFillType.SOLID);
        G_LINE_FILL_TYPE_MAP.addEntry("tile", (int)LineFillType.TEXTURE);
        G_LINE_FILL_TYPE_MAP.addEntry("pattern", (int)LineFillType.PATTERN);
        G_LINE_FILL_TYPE_MAP.addEntry("frame", (int)LineFillType.PICTURE);

        G_ARROW_TYPE_MAP.addEntry("none", (int)ArrowType.NONE);
        G_ARROW_TYPE_MAP.addEntry("classic", (int)ArrowType.STEALTH);
        G_ARROW_TYPE_MAP.addEntry("block", (int)ArrowType.ARROW);
        G_ARROW_TYPE_MAP.addEntry("diamond", (int)ArrowType.DIAMOND);
        G_ARROW_TYPE_MAP.addEntry("oval", (int)ArrowType.OVAL);
        G_ARROW_TYPE_MAP.addEntry("open", (int)ArrowType.OPEN);

        G_ARROW_LENGTH_MAP.addEntry("short", (int)ArrowLength.SHORT);
        G_ARROW_LENGTH_MAP.addEntry("medium", (int)ArrowLength.MEDIUM);
        G_ARROW_LENGTH_MAP.addEntry("long", (int)ArrowLength.LONG);

        G_ARROW_WIDTH_MAP.addEntry("narrow", (int)ArrowWidth.NARROW);
        G_ARROW_WIDTH_MAP.addEntry("medium", (int)ArrowWidth.MEDIUM);
        G_ARROW_WIDTH_MAP.addEntry("wide", (int)ArrowWidth.WIDE);

        G_END_CAP_MAP.addEntry("flat", (int)EndCap.FLAT);
        G_END_CAP_MAP.addEntry("square", (int)EndCap.SQUARE);
        G_END_CAP_MAP.addEntry("round", (int)EndCap.ROUND);

        G_SHAPE_LINE_STYLE_MAP.addEntry("single", (int)ShapeLineStyle.SINGLE);
        G_SHAPE_LINE_STYLE_MAP.addEntry("thinThin", (int)ShapeLineStyle.DOUBLE);
        G_SHAPE_LINE_STYLE_MAP.addEntry("thinThick", (int)ShapeLineStyle.THIN_THICK);
        G_SHAPE_LINE_STYLE_MAP.addEntry("thickThin", (int)ShapeLineStyle.THICK_THIN);
        G_SHAPE_LINE_STYLE_MAP.addEntry("thickBetweenThin", (int)ShapeLineStyle.TRIPLE);

        G_JOIN_STYLE_MAP.addEntry("round", (int)JoinStyle.ROUND);
        G_JOIN_STYLE_MAP.addEntry("bevel", (int)JoinStyle.BEVEL);
        G_JOIN_STYLE_MAP.addEntry("miter", (int)JoinStyle.MITER);

        G_DASH_STYLE_MAP.addEntry("solid", (int)DashStyle.SOLID);
        G_DASH_STYLE_MAP.addEntry("shortDash", (int)DashStyle.SHORT_DASH);
        G_DASH_STYLE_MAP.addEntry("1 1", (int)DashStyle.SHORT_DOT);
        G_DASH_STYLE_MAP.addEntry("shortDashDot", (int)DashStyle.SHORT_DASH_DOT);
        G_DASH_STYLE_MAP.addEntry("shortDashDotDot", (int)DashStyle.SHORT_DASH_DOT_DOT);
        G_DASH_STYLE_MAP.addEntry("dot", (int)DashStyle.DOT);
        G_DASH_STYLE_MAP.addEntry("dash", (int)DashStyle.DASH);
        G_DASH_STYLE_MAP.addEntry("longDash", (int)DashStyle.LONG_DASH);
        G_DASH_STYLE_MAP.addEntry("dashDot", (int)DashStyle.DASH_DOT);
        G_DASH_STYLE_MAP.addEntry("longDashDot", (int)DashStyle.LONG_DASH_DOT);
        G_DASH_STYLE_MAP.addEntry("longDashDotDot", (int)DashStyle.LONG_DASH_DOT_DOT);

        G_CALLOUT_ANGLE_MAP.addEntry("auto", (int)CalloutAngle.ANY);
        G_CALLOUT_ANGLE_MAP.addEntry("30", (int)CalloutAngle.ANGLE_30);
        G_CALLOUT_ANGLE_MAP.addEntry("45", (int)CalloutAngle.ANGLE_45);
        G_CALLOUT_ANGLE_MAP.addEntry("60", (int)CalloutAngle.ANGLE_60);
        G_CALLOUT_ANGLE_MAP.addEntry("90", (int)CalloutAngle.ANGLE_90);

        G_CALLOUT_DROP_TYPE_MAP.addEntry("top", (int)CalloutDropType.TOP);
        G_CALLOUT_DROP_TYPE_MAP.addEntry("center", (int)CalloutDropType.CENTER);
        G_CALLOUT_DROP_TYPE_MAP.addEntry("bottom", (int)CalloutDropType.BOTTOM);

        G_LINE_STYLE_MAP.addEntry("none", (int)LineStyle.NONE);
        G_LINE_STYLE_MAP.addEntry("single", (int)LineStyle.SINGLE);
        G_LINE_STYLE_MAP.addEntry("thick", (int)LineStyle.THICK);
        G_LINE_STYLE_MAP.addEntry("double", (int)LineStyle.DOUBLE);
        G_LINE_STYLE_MAP.addEntry("hairline", (int)LineStyle.HAIRLINE);
        G_LINE_STYLE_MAP.addEntry("dot", (int)LineStyle.DOT);
        G_LINE_STYLE_MAP.addEntry("dash", (int)LineStyle.DASH_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("dotDash", (int)LineStyle.DOT_DASH);
        G_LINE_STYLE_MAP.addEntry("dashDotDot", (int)LineStyle.DOT_DOT_DASH);
        G_LINE_STYLE_MAP.addEntry("triple", (int)LineStyle.TRIPLE);
        G_LINE_STYLE_MAP.addEntry("thinThickSmall", (int)LineStyle.THIN_THICK_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thickThinSmall", (int)LineStyle.THICK_THIN_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thickBetweenThinSmall", (int)LineStyle.THIN_THICK_THIN_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThick", (int)LineStyle.THIN_THICK_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thickThin", (int)LineStyle.THICK_THIN_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thickBetweenThin", (int)LineStyle.THIN_THICK_THIN_MEDIUM_GAP);
        G_LINE_STYLE_MAP.addEntry("thinThickLarge", (int)LineStyle.THIN_THICK_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("thickThinLarge", (int)LineStyle.THICK_THIN_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("thickBetweenThinLarge", (int)LineStyle.THIN_THICK_THIN_LARGE_GAP);
        G_LINE_STYLE_MAP.addEntry("wave", (int)LineStyle.WAVE);
        G_LINE_STYLE_MAP.addEntry("doubleWave", (int)LineStyle.DOUBLE_WAVE);
        G_LINE_STYLE_MAP.addEntry("dashedSmall", (int)LineStyle.DASH_SMALL_GAP);
        G_LINE_STYLE_MAP.addEntry("dashDotStroked", (int)LineStyle.DASH_DOT_STROKER);
        G_LINE_STYLE_MAP.addEntry("threeDEmboss", (int)LineStyle.EMBOSS_3_D);
        G_LINE_STYLE_MAP.addEntry("threeDEngrave", (int)LineStyle.ENGRAVE_3_D);
        G_LINE_STYLE_MAP.addEntry("HTMLOutset", (int)LineStyle.OUTSET);
        G_LINE_STYLE_MAP.addEntry("HTMLInset", (int)LineStyle.INSET);

        G_CONNECTION_SITE_TYPE_MAP.addEntry("none", (int)ConnectionSiteType.NONE);
        G_CONNECTION_SITE_TYPE_MAP.addEntry("rect", (int)ConnectionSiteType.RECT);
        G_CONNECTION_SITE_TYPE_MAP.addEntry("segments", (int)ConnectionSiteType.SEGMENTS);
        G_CONNECTION_SITE_TYPE_MAP.addEntry("custom", (int)ConnectionSiteType.CUSTOM);

        G_TEXT_PATH_ALIGNMENT_MAP.addEntry("left", (int)TextPathAlignment.LEFT);
        G_TEXT_PATH_ALIGNMENT_MAP.addEntry("right", (int)TextPathAlignment.RIGHT);
        G_TEXT_PATH_ALIGNMENT_MAP.addEntry("center", (int)TextPathAlignment.CENTER);
        G_TEXT_PATH_ALIGNMENT_MAP.addEntry("justify", (int)TextPathAlignment.WORD_JUSTIFY);
        G_TEXT_PATH_ALIGNMENT_MAP.addEntry("letter-justify", (int)TextPathAlignment.LETTER_JUSTIFY);
        G_TEXT_PATH_ALIGNMENT_MAP.addEntry("stretch-justify", (int)TextPathAlignment.STRETCH);

        G_HR_ALIGN_MAP.addEntry("left", (int)HorizontalRuleAlignment.LEFT);
        G_HR_ALIGN_MAP.addEntry("center", (int)HorizontalRuleAlignment.CENTER);
        G_HR_ALIGN_MAP.addEntry("right", (int)HorizontalRuleAlignment.RIGHT);

        G_OLE_LINK_TYPE_MAP.addEntry("Bitmap", (int)OleLinkType.BITMAP);
        G_OLE_LINK_TYPE_MAP.addEntry("Html", (int)OleLinkType.HTML);
        G_OLE_LINK_TYPE_MAP.addEntry("Picture", (int)OleLinkType.PICTURE);
        G_OLE_LINK_TYPE_MAP.addEntry("Rtf", (int)OleLinkType.RTF);
        G_OLE_LINK_TYPE_MAP.addEntry("Text", (int)OleLinkType.TEXT);
        G_OLE_LINK_TYPE_MAP.addEntry("Unicode", (int)OleLinkType.UNICODE);

        // Names taken from VML Specification http://msdn.microsoft.com/library/default.asp?url=/workshop/author/vml/SHAPE/introduction.asp?frame=true
        // First letters moved to low register.
        // Several names were tested in MS Word.
        // For DOCX other names should be used, defined in ECMA TC 45 5.1.12.10 ST_BlackWhiteMode (Black and White Mode)
        G_BW_MODE_MAP.addEntry("auto", (int)BWMode.AUTOMATIC);
        G_BW_MODE_MAP.addEntry("black", (int)BWMode.BLACK);
        G_BW_MODE_MAP.addEntry("blackTextAndLines", (int)BWMode.BLACK_TEXT_LINE);
        G_BW_MODE_MAP.addEntry("highContrast", (int)BWMode.HIGH_CONTRAST);
        G_BW_MODE_MAP.addEntry("color", (int)BWMode.COLOR);
        G_BW_MODE_MAP.addEntry("grayScale", (int)BWMode.GRAY_SCALE);
        G_BW_MODE_MAP.addEntry("grayOutline", (int)BWMode.GRAY_OUTLINE);
        G_BW_MODE_MAP.addEntry("undrawn", (int)BWMode.DONT_SHOW);
        G_BW_MODE_MAP.addEntry("inverseGray", (int)BWMode.INVERSE_GRAY);
        G_BW_MODE_MAP.addEntry("lightGrayScale", (int)BWMode.LIGHT_GRAY_SCALE);
        G_BW_MODE_MAP.addEntry("white", (int)BWMode.WHITE);

        G_OPERATION_MAP.addEntry("abs", (int)Operation.ABS);
        G_OPERATION_MAP.addEntry("atan2", (int)Operation.ATAN_2);
        G_OPERATION_MAP.addEntry("cos", (int)Operation.COS);
        G_OPERATION_MAP.addEntry("cosatan2", (int)Operation.COS_ATAN_2);
        G_OPERATION_MAP.addEntry("ellipse", (int)Operation.ELLIPSE);
        G_OPERATION_MAP.addEntry("if", (int)Operation.IF);
        G_OPERATION_MAP.addEntry("max", (int)Operation.MAX);
        G_OPERATION_MAP.addEntry("mid", (int)Operation.MID);
        G_OPERATION_MAP.addEntry("min", (int)Operation.MIN);
        G_OPERATION_MAP.addEntry("mod", (int)Operation.MOD);
        G_OPERATION_MAP.addEntry("prod", (int)Operation.PROD);
        G_OPERATION_MAP.addEntry("sin", (int)Operation.SIN);
        G_OPERATION_MAP.addEntry("sinatan2", (int)Operation.SIN_ATAN_2);
        G_OPERATION_MAP.addEntry("sqrt", (int)Operation.SQRT);
        G_OPERATION_MAP.addEntry("sum", (int)Operation.SUM);
        G_OPERATION_MAP.addEntry("sumangle", (int)Operation.SUM_ANGLE);
        G_OPERATION_MAP.addEntry("tan", (int)Operation.TAN);

        G_SCRIPT_LANGUAGE_MAP.addEntry("JavaScript", (int)ScriptLanguage.JAVA_SCRIPT);

        G_PATH_TYPE_MAP.addEntry("m", (int)PathType.MOVE_TO);
        G_PATH_TYPE_MAP.addEntry("l", (int)PathType.LINE_TO);
        G_PATH_TYPE_MAP.addEntry("c", (int)PathType.CURVE_TO);
        G_PATH_TYPE_MAP.addEntry("x", (int)PathType.CLOSE);
        G_PATH_TYPE_MAP.addEntry("e", (int)PathType.END);
        G_PATH_TYPE_MAP.addEntry("nf", (int)PathType.NO_FILL);
        G_PATH_TYPE_MAP.addEntry("ns", (int)PathType.NO_LINE);
        G_PATH_TYPE_MAP.addEntry("ae", (int)PathType.ANGLE_ELLIPSE_TO);
        G_PATH_TYPE_MAP.addEntry("al", (int)PathType.ANGLE_ELLIPSE);
        G_PATH_TYPE_MAP.addEntry("at", (int)PathType.ARC_TO);
        G_PATH_TYPE_MAP.addEntry("ar", (int)PathType.ARC);
        G_PATH_TYPE_MAP.addEntry("wa", (int)PathType.CLOCKWISE_ARC_TO);
        G_PATH_TYPE_MAP.addEntry("wr", (int)PathType.CLOCKWISE_ARC);
        G_PATH_TYPE_MAP.addEntry("qx", (int)PathType.ELLIPTICAL_QUADRANT_X);
        G_PATH_TYPE_MAP.addEntry("qy", (int)PathType.ELLIPTICAL_QUADRANT_Y);
        G_PATH_TYPE_MAP.addEntry("qb", (int)PathType.QUADRATIC_BEZIER);
        G_PATH_TYPE_MAP.addEntry("ha", (int)PathType.ESCAPE_AUTO_LINE);
        G_PATH_TYPE_MAP.addEntry("hb", (int)PathType.ESCAPE_AUTO_CURVE);
        G_PATH_TYPE_MAP.addEntry("hc", (int)PathType.ESCAPE_CORNER_LINE);
        G_PATH_TYPE_MAP.addEntry("hd", (int)PathType.ESCAPE_CORNER_CURVE);
        G_PATH_TYPE_MAP.addEntry("he", (int)PathType.ESCAPE_SMOOTH_LINE);
        G_PATH_TYPE_MAP.addEntry("hf", (int)PathType.ESCAPE_SMOOTH_CURVE);
        G_PATH_TYPE_MAP.addEntry("hg", (int)PathType.ESCAPE_SYMMETRIC_LINE);
        G_PATH_TYPE_MAP.addEntry("hh", (int)PathType.ESCAPE_SYMMETRIC_CURVE);
        G_PATH_TYPE_MAP.addEntry("hi", (int)PathType.ESCAPE_FREE_FORM);
        G_PATH_TYPE_MAP.addEntry("hj", (int)PathType.FILL_COLOR);
        G_PATH_TYPE_MAP.addEntry("hk", (int)PathType.LINE_COLOR);

        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_AGAINST_GROUPING, (int)DmlLock.NO_GROUP);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_AGAINST_SELECT,(int)DmlLock.NO_SELECT);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_ROTATION, (int)DmlLock.NO_ROTATION);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_ASPECT_RATIO, (int)DmlLock.NO_CHANGE_ASPECT);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_POSITION, (int)DmlLock.NO_MOVE);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_VERTICES, (int)DmlLock.NO_EDIT_POINTS);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_ADJUST_HANDLES, (int)DmlLock.NO_ADJUST_HANDLES);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_SHAPE_TYPE, (int)DmlLock.NO_CHANGE_SHAPE_TYPE);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_AGAINST_UNGROUPING, (int)DmlLock.NO_UNGROUP);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_CROPPING, (int)DmlLock.NO_CROP);
        G_LOCKS_MAP.addEntry(ShapeAttr.LOCK_TEXT, (int)DmlLock.NO_TEXT_EDIT);
        // These locks DML related only.
        G_LOCKS_MAP.addEntry(ShapeAttr.DML_LOCK_ARROW_HEADS, (int)DmlLock.NO_CHANGE_ARROWHEADS);
        G_LOCKS_MAP.addEntry(ShapeAttr.DML_LOCK_RESIZE, (int)DmlLock.NO_RESIZE);
        G_LOCKS_MAP.addEntry(ShapeAttr.DML_LOCK_DRILL_DOWN, (int)DmlLock.NO_DRILLDOWN);
        // These locks VML related only, there are no match in DML.
        // ShapeAttr.AnchorLocked
        // ShapeAttr.OleLocked
        // Vml codes for hatch types were determined experimentally.
        addToFillMap("cross", HatchStyle.CROSS, 0);
        addToFillMap("dashed downward diagonal", HatchStyle.DASHED_DOWNWARD_DIAGONAL, 708767958);
        addToFillMap("dashed horizontal", HatchStyle.DASHED_HORIZONTAL, -902166618);
        addToFillMap("dashed upward diagonal", HatchStyle.DASHED_UPWARD_DIAGONAL, 1943013054);
        addToFillMap("dashed vertical", HatchStyle.DASHED_VERTICAL, 907995120);
        addToFillMap("diagonal brick", HatchStyle.DIAGONAL_BRICK, 1442594541);
        addToFillMap("diagonal cross", HatchStyle.DIAGONAL_CROSS, 0);
        addToFillMap("divot", HatchStyle.DIVOT, 584574198);
        addToFillMap("dark downward diagonal", HatchStyle.DARK_DOWNWARD_DIAGONAL, 703509273);
        addToFillMap("dark horizontal", HatchStyle.DARK_HORIZONTAL, -1645040544);
        addToFillMap("dark upward diagonal", HatchStyle.DARK_UPWARD_DIAGONAL, 1516936182);
        addToFillMap("dark vertical", HatchStyle.DARK_VERTICAL, -1484066896);
        addToFillMap("downward diagonal", HatchStyle.BACKWARD_DIAGONAL, 0);
        addToFillMap("dotted diamond", HatchStyle.DOTTED_DIAMOND, -738923971);
        addToFillMap("dotted grid", HatchStyle.DOTTED_GRID, 1695561988);
        addToFillMap("horizontal", HatchStyle.HORIZONTAL, 0);
        addToFillMap("horizontal brick", HatchStyle.HORIZONTAL_BRICK, 1723978230);
        addToFillMap("large checker board", HatchStyle.LARGE_CHECKER_BOARD, 577586775);
        addToFillMap("large confetti", HatchStyle.LARGE_CONFETTI, 400097116);
        // Strange thing HatchStyle.LargeGrid has the same integer value as HatchStyle.Cross.
        // That is why skip this value. handle it manually in the converter method.
        // "large grid" = HatchStyle.LargeGrid
        addToFillMap("light downward diagonal", HatchStyle.LIGHT_DOWNWARD_DIAGONAL, -233870241);
        addToFillMap("light horizontal", HatchStyle.LIGHT_HORIZONTAL, -2144098250);
        addToFillMap("light upward diagonal", HatchStyle.LIGHT_UPWARD_DIAGONAL, -516687836);
        addToFillMap("light vertical", HatchStyle.LIGHT_VERTICAL, -1060290979);
        addToFillMap("narrow horizontal", HatchStyle.NARROW_HORIZONTAL, 811627745);
        addToFillMap("narrow vertical", HatchStyle.NARROW_VERTICAL, 971821860);
        addToFillMap("open diamond", HatchStyle.OUTLINED_DIAMOND, -1273002823);
        addToFillMap("5%", HatchStyle.PERCENT_05, -612221790);
        addToFillMap("10%", HatchStyle.PERCENT_10, -120615383);
        addToFillMap("20%", HatchStyle.PERCENT_20, -1500494643);
        addToFillMap("25%", HatchStyle.PERCENT_25, 2062819310);
        addToFillMap("30%", HatchStyle.PERCENT_30, -1266175546);
        addToFillMap("40%", HatchStyle.PERCENT_40, 1597532706);
        addToFillMap("50%", HatchStyle.PERCENT_50, 146870144);
        addToFillMap("60%", HatchStyle.PERCENT_60, -1074143645);
        addToFillMap("70%", HatchStyle.PERCENT_70, 1811227497);
        addToFillMap("75%", HatchStyle.PERCENT_75, -69754362);
        addToFillMap("80%", HatchStyle.PERCENT_80, 732808692);
        addToFillMap("90%", HatchStyle.PERCENT_90, -679324745);
        addToFillMap("plaid", HatchStyle.PLAID, -842645683);
        addToFillMap("shingle", HatchStyle.SHINGLE, 1346777725);
        addToFillMap("small checker board", HatchStyle.SMALL_CHECKER_BOARD, -1374783248);
        addToFillMap("small confetti", HatchStyle.SMALL_CONFETTI, 1379582820);
        addToFillMap("small grid", HatchStyle.SMALL_GRID, -1324759092);
        addToFillMap("solid diamond", HatchStyle.SOLID_DIAMOND, 1673964194);
        addToFillMap("sphere", HatchStyle.SPHERE, -1815305409);
        addToFillMap("trellis", HatchStyle.TRELLIS, 1984576894);
        addToFillMap("upward diagonal", HatchStyle.FORWARD_DIAGONAL, 0);
        addToFillMap("vertical", HatchStyle.VERTICAL, 0);
        addToFillMap("wave", HatchStyle.WAVE, 652020124);
        addToFillMap("wide downward diagonal", HatchStyle.WIDE_DOWNWARD_DIAGONAL, 136926604);
        addToFillMap("wide upward diagonal", HatchStyle.WIDE_UPWARD_DIAGONAL, 1944184317);
        addToFillMap("weave", HatchStyle.WEAVE, -1278091726);
        addToFillMap("zig zag", HatchStyle.ZIG_ZAG, -1753090367);

        // Vml shapes uses "large grid" hatch name instead of "cross"
        addToFillMap("large grid", HatchStyle.LARGE_GRID, -550909115);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"atLeast",
		"atMost",
		"solid",
		"gradientRadial",
		"gradient",
		"pattern",
		"frame",
		"tile"
	);


}

