// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/02/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.IntToIntDictionary;
import com.aspose.IntToObjDictionary;
import com.aspose.StringToIntDictionary;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Converts DrawingML enumerated values between enum and XML string.
/// </summary>
/*static*/ class DmlEnum
{
	/* Simulation of static class by using private constructor */
	private DmlEnum()
	{}

    static /*BWMode*/int dmlToBWMode(String value)
    {
        return (/*BWMode*/int)G_BW_MODE_MAP.getValue(value, (int)BWMode.DEFAULT);
    }

    static String bWModeToDml(/*BWMode*/int value)
    {
        return G_BW_MODE_MAP.getValue((int)value, "");
    }

    static /*ShapeLineStyle*/int dmlToShapeLineStyle(String value)
    {
        return (/*ShapeLineStyle*/int)G_SHAPE_LINE_STYLE_MAP.getValue(value, (int)ShapeLineStyle.DEFAULT);
    }

    static String shapeLineStyleToDml(/*ShapeLineStyle*/int value)
    {
        return G_SHAPE_LINE_STYLE_MAP.getValue((int)value, "");
    }

    static /*EndCap*/int dmlToEndCap(String value)
    {
        return (/*EndCap*/int)G_END_CAP_MAP.getValue(value, (int)EndCap.FLAT);
    }

    static String endCapToDml(/*EndCap*/int value)
    {
        return G_END_CAP_MAP.getValue((int)value, "");
    }

    static boolean dmlToInsetPen(String value)
    {
        return msString.equals("in", value, StringComparison.ORDINAL);
    }

    static String insetPenToDml(boolean value)
    {
        return value ? "in" : "ctr";
    }

    static /*RelativeHorizontalPosition*/int dmlToRelativeHorizontalPosition(String value)
    {
        return (/*RelativeHorizontalPosition*/int)G_RELATIVE_HORIZONTAL_POSITION_MAP.getValue(value, (int)RelativeHorizontalPosition.DEFAULT);
    }

    static String relativeHorizontalPositionToDml(Object value)
    {
        /*RelativeHorizontalPosition*/int enumValue = (value != null)
            ? (/*RelativeHorizontalPosition*/Integer)value
            : RelativeHorizontalPosition.DEFAULT;

        return G_RELATIVE_HORIZONTAL_POSITION_MAP.getValue((int)enumValue);
    }

    static /*RelativeVerticalPosition*/int dmlToRelativeVerticalPosition(String value)
    {
        return (/*RelativeVerticalPosition*/int)G_RELATIVE_VERTICAL_POSITION_MAP.getValue(value, (int)RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
    }

    static String relativeVerticalPositionToDml(Object value)
    {
        /*RelativeVerticalPosition*/int enumValue = (value != null)
            ? (/*RelativeVerticalPosition*/Integer)value
            : RelativeVerticalPosition.TEXT_FRAME_DEFAULT;

        return G_RELATIVE_VERTICAL_POSITION_MAP.getValue((int)enumValue);
    }

    static /*RelativeHorizontalSize*/int dmlToRelativeWidth(String value)
    {
        return (/*RelativeHorizontalSize*/int)G_RELATIVE_HORIZONTAL_SIZE_MAP.getValue(value, (int)RelativeHorizontalSize.DEFAULT);
    }

    static String relativeWidthToDml(Object value)
    {
        /*RelativeHorizontalSize*/int enumValue = (value != null)
            ? (/*RelativeHorizontalSize*/Integer)value
            : RelativeHorizontalSize.DEFAULT;

        return G_RELATIVE_HORIZONTAL_SIZE_MAP.getValue((int)enumValue);
    }

    static /*RelativeVerticalSize*/int dmlToRelativeHeight(String value)
    {
        return (/*RelativeVerticalSize*/int)G_RELATIVE_VERTICAL_SIZE_MAP.getValue(value, (int)RelativeVerticalSize.DEFAULT);
    }

    static String relativeHeightToDml(Object value)
    {
        /*RelativeVerticalSize*/int enumValue = (value != null)
            ? (/*RelativeVerticalSize*/Integer)value
            : RelativeVerticalSize.DEFAULT;

        return G_RELATIVE_VERTICAL_SIZE_MAP.getValue((int)enumValue);
    }

    static /*HorizontalAlignment*/int dmlToHorizontalAlignment(String value)
    {
        return (/*HorizontalAlignment*/int)G_HORIZONTAL_ALIGNMENT_MAP.getValue(value, (int)HorizontalAlignment.DEFAULT);
    }

    static String horizontalAlignmentToDml(Object value)
    {
        /*HorizontalAlignment*/int enumValue = (value != null)
            ? (/*HorizontalAlignment*/Integer)value
            : HorizontalAlignment.DEFAULT;

        return G_HORIZONTAL_ALIGNMENT_MAP.getValue((int)enumValue);
    }

    static /*VerticalAlignment*/int dmlToVerticalAlignment(String value)
    {
        return (/*VerticalAlignment*/int)G_VERTICAL_ALIGNMENT_MAP.getValue(value, (int)VerticalAlignment.DEFAULT);
    }

    static String verticalAlignmentToDml(Object value)
    {
        /*VerticalAlignment*/int enumValue = (value != null)
            ? (/*VerticalAlignment*/Integer)value
            : VerticalAlignment.DEFAULT;

        return G_VERTICAL_ALIGNMENT_MAP.getValue((int)enumValue);
    }

    static /*WrapSide*/int dmlToWrapSide(String value)
    {
        return (/*WrapSide*/int)G_WRAP_SIDE_MAP.getValue(value, (int)WrapSide.DEFAULT);
    }

    static String wrapSideToDml(/*WrapSide*/int value)
    {
        return G_WRAP_SIDE_MAP.getValue((int)value, "");
    }

    static /*SchemeColor*/int dmlToSchemeColor(String value)
    {
        return (/*SchemeColor*/int)G_SCHEME_COLOR_MAP.getValue(value, (int)SchemeColor.DEFAULT);
    }

    static String schemeColorToDml(/*SchemeColor*/int value)
    {
        return G_SCHEME_COLOR_MAP.getValue((int)value, "");
    }

    static /*DashStyle*/int dmlToDashStyle(String value)
    {
        return (/*DashStyle*/int)G_DASH_STYLE_MAP.getValue(value, (int)DashStyle.DEFAULT);
    }

    static String dashStyleToDml(/*DashStyle*/int value)
    {
        return G_DASH_STYLE_MAP.getValue((int)value, "");
    }

    static /*ArrowWidth*/int dmlToArrowWidth(String value)
    {
        return (/*ArrowWidth*/int)G_ARROW_WIDTH_MAP.getValue(value, (int)ArrowWidth.MEDIUM); // In Dml by default Medium.
    }

    static String arrowWidthToDml(/*ArrowWidth*/int value)
    {
        return G_ARROW_WIDTH_MAP.getValue((int)value, "");
    }

    static /*ArrowLength*/int dmlToArrowLength(String value)
    {
        return (/*ArrowLength*/int)G_ARROW_LENGTH_MAP.getValue(value, (int)ArrowLength.MEDIUM); // In Dml by default Medium.
    }

    static String arrowLengthToDml(/*ArrowLength*/int value)
    {
        return G_ARROW_LENGTH_MAP.getValue((int)value, "");
    }

    static /*ArrowType*/int dmlToArrowType(String value)
    {
        return (/*ArrowType*/int)G_ARROW_TYPE_MAP.getValue(value, (int)ArrowType.DEFAULT);
    }

    static String arrowTypeToDml(/*ArrowType*/int value)
    {
        return G_ARROW_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlTileFlipMode*/int dmlToTileFlipMode(String value)
    {
        return (/*DmlTileFlipMode*/int)G_TILE_FLIP_MODE_MAP.getValue(value, (int)DmlTileFlipMode.NONE);
    }

    static String tileFlipModeToDml(/*DmlTileFlipMode*/int value)
    {
        return G_TILE_FLIP_MODE_MAP.getValue((int)value, "");
    }

    static /*DmlPathShadeType*/int dmlToPathShadeType(String value)
    {
        return (/*DmlPathShadeType*/int)G_PATH_SHADE_TYPE.getValue(value, (int)DmlPathShadeType.SHAPE);
    }

    static String pathShadeTypeToDml(/*DmlPathShadeType*/int value)
    {
        return G_PATH_SHADE_TYPE.getValue((int)value, "");
    }

    static /*EndCap*/int dmlToLineEndingCapType(String value)
    {
        return (/*EndCap*/int)G_LINE_ENDING_CAP_TYPE_MAP.getValue(value, (int)EndCap.FLAT);
    }

    static String lineEndingCapTypeToDml(/*EndCap*/int value)
    {
        return G_LINE_ENDING_CAP_TYPE_MAP.getValue((int)value, "");
    }

    static /*ShapeLineStyle*/int dmlToCompoundLineType(String value)
    {
        return (/*ShapeLineStyle*/int)G_COMPOUND_LINE_TYPE_MAP.getValue(value, (int)ShapeLineStyle.SINGLE);
    }

    static String compoundLineTypeToDml(/*ShapeLineStyle*/int value)
    {
        return G_COMPOUND_LINE_TYPE_MAP.getValue((int)value, "");
    }

    static /*DashStyle*/int dmlToPresetDashType(String value)
    {
        return (/*DashStyle*/int)G_PRESET_DASH_TYPE_MAP.getValue(value);
    }

    static String presetDashTypeToDml(/*DashStyle*/int value)
    {
        return G_PRESET_DASH_TYPE_MAP.getValue((int)value, "");
    }

    static /*ShapeTextOrientation*/int dmlToShapeTextOrientation(String value)
    {
        return (/*ShapeTextOrientation*/int)G_TEXT_ORIENTATION_MAP.getValue(value, (int)ShapeTextOrientation.HORIZONTAL);
    }

    static String shapeTextOrientationToDml(/*ShapeTextOrientation*/int value)
    {
        return G_TEXT_ORIENTATION_MAP.getValue((int)value, "");
    }

    static /*DmlTextHorizontalOverflowType*/int dmlToTextHorizontalOverflowType(String value)
    {
        return (/*DmlTextHorizontalOverflowType*/int)G_TEXT_HORIZONTAL_OVERFLOW_TYPE_MAP.getValue(value, (int)DmlTextHorizontalOverflowType.OVERFLOW);
    }

    static String textHorizontalOverflowTypeToDml(/*DmlTextHorizontalOverflowType*/int value)
    {
        return G_TEXT_HORIZONTAL_OVERFLOW_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlTextVerticalOverflowType*/int dmlToTextVerticalOverflowType(String value)
    {
        return (/*DmlTextVerticalOverflowType*/int)G_TEXT_VERTICAL_OVERFLOW_TYPE_MAP.getValue(value, (int)DmlTextVerticalOverflowType.OVERFLOW);
    }

    static String textVerticalOverflowTypeToDml(/*DmlTextVerticalOverflowType*/int value)
    {
        return G_TEXT_VERTICAL_OVERFLOW_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlTextAnchoringType*/int dmlToTextAnchoringType(String value)
    {
        return (/*DmlTextAnchoringType*/int)G_TEXT_ANCHORING_TYPE_MAP.getValue(value, (int)DmlTextAnchoringType.TOP);
    }

    static String textAnchoringTypeToDml(/*DmlTextAnchoringType*/int value)
    {
        return G_TEXT_ANCHORING_TYPE_MAP.getValue((int)value, "");
    }

    static /*TextBoxWrapMode*/int dmlToTextWrappingType(String value)
    {
        return (/*TextBoxWrapMode*/int)G_TEXT_WRAPPING_TYPE_MAP.getValue(value, (int)TextBoxWrapMode.SQUARE);
    }

    static String textWrappingTypeToDml(/*TextBoxWrapMode*/int value)
    {
        return G_TEXT_WRAPPING_TYPE_MAP.getValue((int)value, "");
    }

    static /*ParagraphAlignment*/int dmlToTextAlignment(String value)
    {
        return (/*ParagraphAlignment*/int)G_TEXT_ALIGNMENT_MAP.getValue(value, (int)ParagraphAlignment.LEFT);
    }

    static String textAlignmentToDml(/*ParagraphAlignment*/int value)
    {
        return G_TEXT_ALIGNMENT_MAP.getValue((int)fixUnsupportedValue(value), "");
    }

    /// <summary>
    /// Returns <c>true</c> if the specified paragraph alignment is not supported by DML paragraphs.
    /// </summary>
    static boolean isNotSupportedValue(/*ParagraphAlignment*/int value)
    {
        return fixUnsupportedValue(value) != value;
    }

    /// <summary>
    /// Returns supported value for the specified text alignment of a DML paragraph.
    /// </summary>
    static /*ParagraphAlignment*/int fixUnsupportedValue(/*ParagraphAlignment*/int value)
    {
        switch (value)
        {
            // These items of ParagraphAlignment are not supported by DML, convert them to nearest by behaviour.
            case ParagraphAlignment.ARABIC_MEDIUM_KASHIDA:
            case ParagraphAlignment.ARABIC_HIGH_KASHIDA:
                return ParagraphAlignment.JUSTIFY;
            default:
                return value;
        }
    }

    static /*DmlFontAlignment*/int dmlToFontAlignment(String value)
    {
        return (/*DmlFontAlignment*/int)G_FONT_ALIGNMENT_MAP.getValue(value, (int)DmlFontAlignment.BASELINE);
    }

    static String fontAlignmentToDml(/*DmlFontAlignment*/int value)
    {
        return G_FONT_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*DmlCapitalization*/int dmlToCapitalization(String value)
    {
        return (/*DmlCapitalization*/int)G_CAPITALIZATION_MAP.getValue(value, (int)DmlCapitalization.NONE);
    }

    static String capitalizationToDml(/*DmlCapitalization*/int value)
    {
        return G_CAPITALIZATION_MAP.getValue((int)value, "");
    }

    static /*DmlTextStrike*/int dmlToTextStrike(String value)
    {
        return (/*DmlTextStrike*/int)G_TEXT_STRIKE_MAP.getValue(value, (int)DmlTextStrike.NO);
    }

    static String textStrikeToDml(/*DmlTextStrike*/int value)
    {
        return G_TEXT_STRIKE_MAP.getValue((int)value, "");
    }

    static /*Underline*/int dmlToTextUnderlineType(String value)
    {
        return (/*Underline*/int)G_TEXT_UNDERLINE_TYPE_MAP.getValue(value, (int)Underline.NONE);
    }

    static String textUnderlineTypeToDml(/*Underline*/int value)
    {
        return G_TEXT_UNDERLINE_TYPE_MAP.getValue((int)value, "");
    }

    static /*PatternType*/int dmlToPatternType(String value)
    {
        return (/*PatternType*/int)G_PATTERN_TYPE_MAP.getValue(value, 0);
    }

    static String patternTypeToDml(/*PatternType*/int value)
    {
        return G_PATTERN_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlRectangleAlignment*/int dmlToRectangleAlignment(String value)
    {
        return (/*DmlRectangleAlignment*/int)G_RECTANGLE_ALIGNMENT_MAP.getValue(value, (int)DmlRectangleAlignment.TOP_LEFT);
    }

    static String rectangleAlignmentToDml(/*DmlRectangleAlignment*/int value)
    {
        return G_RECTANGLE_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*DmlCompressionState*/int dmlToCompressionState(String value)
    {
        return (/*DmlCompressionState*/int)G_COMPRESSION_STATE_MAP.getValue(value, (int)DmlCompressionState.EMAIL);
    }

    static String compressionStateToDml(/*DmlCompressionState*/int value)
    {
        return G_COMPRESSION_STATE_MAP.getValue((int)value, "");
    }

    static /*DmlEffectBlendMode*/int dmlToEffectBlendMode(String value)
    {
        return (/*DmlEffectBlendMode*/int)G_EFFECT_BLEND_MODE_MAP.getValue(value, (int)DmlEffectBlendMode.OVERLAY);
    }

    static String effectBlendModeToDml(/*DmlEffectBlendMode*/int value)
    {
        return G_EFFECT_BLEND_MODE_MAP.getValue((int)value, "");
    }

    static /*DmlPathFillMode*/int dmlToPathFillMode(String value)
    {
        return (/*DmlPathFillMode*/int)G_PATH_FILL_MODE_MAP.getValue(value, (int)DmlPathFillMode.NORM);
    }

    static String pathFillModeToDml(/*DmlPathFillMode*/int value)
    {
        return G_PATH_FILL_MODE_MAP.getValue((int)value, "");
    }

    static /*DmlPresetShadow*/int dmlToPresetShadow(String value)
    {
        return (/*DmlPresetShadow*/int)G_PRESET_SHADOW_MAP.getValue(value, (int)DmlPresetShadow.TOP_LEFT_DROP_SHADOW);
    }

    static String presetShadowToDml(/*DmlPresetShadow*/int value)
    {
        return G_PRESET_SHADOW_MAP.getValue((int)value, "");
    }

    static /*DmlFontCollectionIndex*/int dmlToFontCollectionIndex(String value)
    {
        return (/*DmlFontCollectionIndex*/int)G_FONT_COLLECTION_INDEX_MAP.getValue(value, (int)DmlFontCollectionIndex.MAJOR);
    }

    static String fontCollectionIndexToDml(/*DmlFontCollectionIndex*/int value)
    {
        return G_FONT_COLLECTION_INDEX_MAP.getValue((int)value, "");
    }

    static /*DmlTextShapeType*/int dmlToTextShapeType(String value)
    {
        return (/*DmlTextShapeType*/int)G_TEXT_SHAPE_TYPE_MAP.getValue(value, (int)DmlTextShapeType.TEXT_NO_SHAPE);
    }

    static String textShapeTypeToDml(/*DmlTextShapeType*/int value)
    {
        return G_TEXT_SHAPE_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlPresetCameraType*/int dmlToPresetCameraType(String value)
    {
        return (/*DmlPresetCameraType*/int)G_CAMERA_TYPE_MAP.getValue(value, (int)DmlPresetCameraType.ORTHOGRAPHIC_FRONT);
    }

    static String presetCameraTypeToDml(/*DmlPresetCameraType*/int value)
    {
        return G_CAMERA_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlLightRigType*/int dmlToLightRigType(String value)
    {
        return (/*DmlLightRigType*/int)G_LIGHT_RIG_TYPE_MAP.getValue(value, (int)DmlLightRigType.BALANCED);
    }

    static String lightRigTypeDml(/*DmlLightRigType*/int value)
    {
        return G_LIGHT_RIG_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlLightRigDirection*/int dmlToLightRigDirection(String value)
    {
        return (/*DmlLightRigDirection*/int)G_LIGHT_RIG_DIRECTION_MAP.getValue(value, (int)DmlLightRigDirection.TOP);
    }

    static String lightRigDirectionToDml(/*DmlLightRigDirection*/int value)
    {
        return G_LIGHT_RIG_DIRECTION_MAP.getValue((int)value, "");
    }

    static /*DmlPresetMaterialType*/int dmlToPresetMaterial(String value)
    {
        return (/*DmlPresetMaterialType*/int)G_PRESET_MATERIAL_MAP.getValue(value, (int)DmlPresetMaterialType.WARM_MATTE);
    }

    static String presetMaterialToDml(/*DmlPresetMaterialType*/int value)
    {
        return G_PRESET_MATERIAL_MAP.getValue((int)value, "");
    }

    static /*DmlBevelPresetType*/int dmlToBevelPresetType(String value)
    {
        return (/*DmlBevelPresetType*/int)G_BEVEL_PRESET_TYPE_MAP.getValue(value, (int)DmlBevelPresetType.CIRCLE);
    }

    static String bevelPresetTypeToDml(/*DmlBevelPresetType*/int value)
    {
        return G_BEVEL_PRESET_TYPE_MAP.getValue((int)value, "");
    }

    static /*DmlTextBulletType*/int dmlToTextBulletType(String value)
    {
        return (/*DmlTextBulletType*/int)G_TEXT_BULLET_TYPE_MAP.getValue(value, (int)DmlTextBulletType.ARABIC_PERIOD);
    }

    static String textBulletTypeToDml(/*DmlTextBulletType*/int value)
    {
        return G_TEXT_BULLET_TYPE_MAP.getValue((int)value, "");
    }

    static /*TabAlignment*/int dmlToTabAlignment(String value)
    {
        return (/*TabAlignment*/int)G_TAB_ALIGNMENT_MAP.getValue(value, (int)TabAlignment.CENTER);
    }

    static String tabAlignmentToDml(/*TabAlignment*/int value)
    {
        return G_TAB_ALIGNMENT_MAP.getValue((int)value, "");
    }

    static /*ShapeType*/int dmlPresetGeomToShapeType(String presetName)
    {
        if (!G_PRESET_TO_SHAPE_TYPE_MAP.containsKey(presetName))
            return ShapeType.CUSTOM_SHAPE;

        return (/*ShapeType*/int)G_PRESET_TO_SHAPE_TYPE_MAP.get(presetName);
    }

    static String shapeTypeToDmlPresetGeom(/*ShapeType*/int value)
    {
        if (!G_SHAPE_TYPE_TO_PRESET_MAP.containsKey((int)value))
            return "rect";

        return G_SHAPE_TYPE_TO_PRESET_MAP.get((int)value);
    }

    static String effectDagTypeToDml(/*EffectDagType*/int value)
    {
        return G_EFFECT_DAG_TYPE_MAP.getValue((int)value, "sib");
    }

    static /*EffectDagType*/int dmlToEffectDagType(String value)
    {
        return (/*EffectDagType*/int)G_EFFECT_DAG_TYPE_MAP.getValue(value, (int)EffectDagType.SIBLING);
    }

    static /*DmlTextShapeType*/int shapeTypeToDmlPresetTextWrapType(/*ShapeType*/int value)
    {
        if (!G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.containsKey((int)value))
            return DmlTextShapeType.NONE;

        return (/*DmlTextShapeType*/int)G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.get((int)value);
    }

    /// <summary>
    /// True, when specified shape type has appropriate preset VML geometry.
    /// </summary>
    static boolean hasVmlPresetGeometry(/*ShapeType*/int shapeType)
    {
        switch (shapeType)
        {
            case ShapeType.NON_PRIMITIVE:
            case ShapeType.CUSTOM_SHAPE:
            case ShapeType.SINGLE_CORNER_SNIPPED:
            case ShapeType.DIAGONAL_CORNERS_SNIPPED:
            case ShapeType.TOP_CORNERS_SNIPPED:
            case ShapeType.TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED:
            case ShapeType.SINGLE_CORNER_ROUNDED:
            case ShapeType.DIAGONAL_CORNERS_ROUNDED:
            case ShapeType.TOP_CORNERS_ROUNDED:
            case ShapeType.HEPTAGON:
            case ShapeType.CLOUD:
            case ShapeType.SEAL_6:
            case ShapeType.SEAL_7:
            case ShapeType.SEAL_10:
            case ShapeType.SEAL_12:
            case ShapeType.SWOOSH_ARROW:
            case ShapeType.TEARDROP:
            case ShapeType.SQUARE_TABS:
            case ShapeType.PLAQUE_TABS:
            case ShapeType.PIE:
            case ShapeType.WEDGE_PIE:
            case ShapeType.INVERSE_LINE:
            case ShapeType.MATH_DIVIDE:
            case ShapeType.MATH_EQUAL:
            case ShapeType.MATH_MINUS:
            case ShapeType.MATH_MULTIPLY:
            case ShapeType.MATH_NOT_EQUAL:
            case ShapeType.MATH_PLUS:
            case ShapeType.NON_ISOSCELES_TRAPEZOID:
            case ShapeType.LEFT_RIGHT_CIRCULAR_ARROW:
            case ShapeType.LEFT_RIGHT_RIBBON:
            case ShapeType.LEFT_CIRCULAR_ARROW:
            case ShapeType.FRAME:
            case ShapeType.FUNNEL:
            case ShapeType.GEAR_6:
            case ShapeType.GEAR_9:
            case ShapeType.HALF_FRAME:
            case ShapeType.DECAGON:
            case ShapeType.DIAGONAL_STRIPE:
            case ShapeType.DODECAGON:
            case ShapeType.CORNER:
            case ShapeType.CORNER_TABS:
            case ShapeType.CHORD:
            case ShapeType.CHART_PLUS:
            case ShapeType.CHART_STAR:
            case ShapeType.CHART_X:
            // Probably, DML has not appropriate analogue for shape types below.
            case ShapeType.OLE_CONTROL:
            case ShapeType.OLE_OBJECT:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Returns a flag indicating whether MS Word generates a custom geometry fallback shape for a DML shape of
    /// the specified type.
    /// </summary>
    static boolean isCustomGeometryShapeFallback(/*ShapeType*/int shapeType)
    {
        switch (shapeType)
        {
            case ShapeType.QUAD_ARROW_CALLOUT:
            case ShapeType.QUAD_ARROW:
            case ShapeType.LEFT_UP_ARROW:
            case ShapeType.BENT_ARROW:
            case ShapeType.BENT_UP_ARROW:
            case ShapeType.UTURN_ARROW:
            case ShapeType.TRAPEZOID:
            case ShapeType.LEFT_RIGHT_UP_ARROW:
            case ShapeType.HEART:
            case ShapeType.CIRCULAR_ARROW:
            case ShapeType.BLOCK_ARC:
            case ShapeType.ARC:
            case ShapeType.STAR:
                return true;
            default:
                return !hasVmlPresetGeometry(shapeType);
        }
    }

    private static /*final*/ IntToIntDictionary G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP = new IntToIntDictionary();

    // There are two collections because there is not only one to one relations between ShapeType and Preset geometry name.
    private static /*final*/ IntToObjDictionary<String> G_SHAPE_TYPE_TO_PRESET_MAP = new IntToObjDictionary<String>();
    private static /*final*/ StringToIntDictionary G_PRESET_TO_SHAPE_TYPE_MAP = new StringToIntDictionary();

    private static /*final*/ StringToIntBidirectionalMap G_BW_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SHAPE_LINE_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_END_CAP_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_HORIZONTAL_SIZE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_VERTICAL_SIZE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_HORIZONTAL_POSITION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RELATIVE_VERTICAL_POSITION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_HORIZONTAL_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_VERTICAL_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_WRAP_SIDE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_SCHEME_COLOR_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_DASH_STYLE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ARROW_WIDTH_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ARROW_LENGTH_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_ARROW_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TILE_FLIP_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PATH_SHADE_TYPE = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LINE_ENDING_CAP_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_COMPOUND_LINE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PRESET_DASH_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_ORIENTATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_VERTICAL_OVERFLOW_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_HORIZONTAL_OVERFLOW_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_ANCHORING_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_WRAPPING_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_FONT_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CAPITALIZATION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_STRIKE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_UNDERLINE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PATTERN_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_RECTANGLE_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_COMPRESSION_STATE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_EFFECT_BLEND_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PATH_FILL_MODE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PRESET_SHADOW_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_FONT_COLLECTION_INDEX_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_SHAPE_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_CAMERA_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LIGHT_RIG_DIRECTION_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_LIGHT_RIG_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_PRESET_MATERIAL_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_BEVEL_PRESET_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TEXT_BULLET_TYPE_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_TAB_ALIGNMENT_MAP = new StringToIntBidirectionalMap();
    private static /*final*/ StringToIntBidirectionalMap G_EFFECT_DAG_TYPE_MAP = new StringToIntBidirectionalMap();

    static/* DmlEnum()*/
    {
        G_BW_MODE_MAP.addEntry("auto", (int)BWMode.AUTOMATIC);
        G_BW_MODE_MAP.addEntry("black", (int)BWMode.BLACK);
        G_BW_MODE_MAP.addEntry("blackGray", (int)BWMode.BLACK_TEXT_LINE);
        G_BW_MODE_MAP.addEntry("blackWhite", (int)BWMode.HIGH_CONTRAST);
        G_BW_MODE_MAP.addEntry("clr", (int)BWMode.COLOR);
        G_BW_MODE_MAP.addEntry("gray", (int)BWMode.GRAY_SCALE);
        G_BW_MODE_MAP.addEntry("grayWhite", (int)BWMode.GRAY_OUTLINE);
        G_BW_MODE_MAP.addEntry("hidden", (int)BWMode.DONT_SHOW);
        G_BW_MODE_MAP.addEntry("invGray", (int)BWMode.INVERSE_GRAY);
        G_BW_MODE_MAP.addEntry("ltGray", (int)BWMode.LIGHT_GRAY_SCALE);
        G_BW_MODE_MAP.addEntry("white", (int)BWMode.WHITE);

        G_SHAPE_LINE_STYLE_MAP.addEntry("sng", (int)ShapeLineStyle.SINGLE);
        G_SHAPE_LINE_STYLE_MAP.addEntry("dbl", (int)ShapeLineStyle.DOUBLE);
        G_SHAPE_LINE_STYLE_MAP.addEntry("thinThick", (int)ShapeLineStyle.THIN_THICK);
        G_SHAPE_LINE_STYLE_MAP.addEntry("thickThin", (int)ShapeLineStyle.THICK_THIN);
        G_SHAPE_LINE_STYLE_MAP.addEntry("tri", (int)ShapeLineStyle.TRIPLE);

        G_END_CAP_MAP.addEntry("flat", (int)EndCap.FLAT);
        G_END_CAP_MAP.addEntry("sq", (int)EndCap.SQUARE);
        G_END_CAP_MAP.addEntry("rnd", (int)EndCap.ROUND);


        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("insideMargin", (int)RelativeHorizontalSize.INNER_MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("leftMargin", (int)RelativeHorizontalSize.LEFT_MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("margin", (int)RelativeHorizontalSize.MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("outsideMargin", (int)RelativeHorizontalSize.OUTER_MARGIN);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("page", (int)RelativeHorizontalSize.PAGE);
        G_RELATIVE_HORIZONTAL_SIZE_MAP.addEntry("rightMargin", (int)RelativeHorizontalSize.RIGHT_MARGIN);

        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("bottomMargin", (int)RelativeVerticalSize.BOTTOM_MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("insideMargin", (int)RelativeVerticalSize.INNER_MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("margin", (int)RelativeVerticalSize.MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("outsideMargin", (int)RelativeVerticalSize.OUTER_MARGIN);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("page", (int)RelativeVerticalSize.PAGE);
        G_RELATIVE_VERTICAL_SIZE_MAP.addEntry("topMargin", (int)RelativeVerticalSize.TOP_MARGIN);

        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("character", (int)RelativeHorizontalPosition.CHARACTER);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("column", (int)RelativeHorizontalPosition.COLUMN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("insideMargin", (int)RelativeHorizontalPosition.INSIDE_MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("leftMargin", (int)RelativeHorizontalPosition.LEFT_MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("margin", (int)RelativeHorizontalPosition.MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("outsideMargin", (int)RelativeHorizontalPosition.OUTSIDE_MARGIN);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("page", (int)RelativeHorizontalPosition.PAGE);
        G_RELATIVE_HORIZONTAL_POSITION_MAP.addEntry("rightMargin", (int)RelativeHorizontalPosition.RIGHT_MARGIN);

        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("bottomMargin", (int)RelativeVerticalPosition.BOTTOM_MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("insideMargin", (int)RelativeVerticalPosition.INSIDE_MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("line", (int)RelativeVerticalPosition.LINE);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("margin", (int)RelativeVerticalPosition.MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("outsideMargin", (int)RelativeVerticalPosition.OUTSIDE_MARGIN);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("page", (int)RelativeVerticalPosition.PAGE);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("paragraph", (int)RelativeVerticalPosition.PARAGRAPH);
        G_RELATIVE_VERTICAL_POSITION_MAP.addEntry("topMargin", (int)RelativeVerticalPosition.TOP_MARGIN);

        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("center", (int)HorizontalAlignment.CENTER);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("inside", (int)HorizontalAlignment.INSIDE);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("left", (int)HorizontalAlignment.LEFT);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("outside", (int)HorizontalAlignment.OUTSIDE);
        G_HORIZONTAL_ALIGNMENT_MAP.addEntry("right", (int)HorizontalAlignment.RIGHT);

        G_VERTICAL_ALIGNMENT_MAP.addEntry("bottom", (int)VerticalAlignment.BOTTOM);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("center", (int)VerticalAlignment.CENTER);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("inside", (int)VerticalAlignment.INSIDE);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("outside", (int)VerticalAlignment.OUTSIDE);
        G_VERTICAL_ALIGNMENT_MAP.addEntry("top", (int)VerticalAlignment.TOP);

        G_WRAP_SIDE_MAP.addEntry("bothSides", (int)WrapSide.BOTH);
        G_WRAP_SIDE_MAP.addEntry("largest", (int)WrapSide.LARGEST);
        G_WRAP_SIDE_MAP.addEntry("left", (int)WrapSide.LEFT);
        G_WRAP_SIDE_MAP.addEntry("right", (int)WrapSide.RIGHT);

        G_SCHEME_COLOR_MAP.addEntry("accent1", (int)SchemeColor.ACCENT_1);
        G_SCHEME_COLOR_MAP.addEntry("accent2", (int)SchemeColor.ACCENT_2);
        G_SCHEME_COLOR_MAP.addEntry("accent3", (int)SchemeColor.ACCENT_3);
        G_SCHEME_COLOR_MAP.addEntry("accent4", (int)SchemeColor.ACCENT_4);
        G_SCHEME_COLOR_MAP.addEntry("accent5", (int)SchemeColor.ACCENT_5);
        G_SCHEME_COLOR_MAP.addEntry("accent6", (int)SchemeColor.ACCENT_6);
        G_SCHEME_COLOR_MAP.addEntry("bg1", (int)SchemeColor.BACKGROUND_1);
        G_SCHEME_COLOR_MAP.addEntry("bg2", (int)SchemeColor.BACKGROUND_2);
        G_SCHEME_COLOR_MAP.addEntry("dk1", (int)SchemeColor.DARK_1);
        G_SCHEME_COLOR_MAP.addEntry("dk2", (int)SchemeColor.DARK_2);
        G_SCHEME_COLOR_MAP.addEntry("folHlink", (int)SchemeColor.FOLLOWED_HYPERLINK);
        G_SCHEME_COLOR_MAP.addEntry("hlink", (int)SchemeColor.HYPERLINK);
        G_SCHEME_COLOR_MAP.addEntry("lt1", (int)SchemeColor.LIGHT_1);
        G_SCHEME_COLOR_MAP.addEntry("lt2", (int)SchemeColor.LIGHT_2);
        G_SCHEME_COLOR_MAP.addEntry("phClr", (int)SchemeColor.STYLE);
        G_SCHEME_COLOR_MAP.addEntry("tx1", (int)SchemeColor.TEXT_1);
        G_SCHEME_COLOR_MAP.addEntry("tx2", (int)SchemeColor.TEXT_2);

        G_DASH_STYLE_MAP.addEntry("dash", (int)DashStyle.DASH);
        G_DASH_STYLE_MAP.addEntry("dashDot", (int)DashStyle.DASH_DOT);
        G_DASH_STYLE_MAP.addEntry("dot", (int)DashStyle.DOT);
        G_DASH_STYLE_MAP.addEntry("lgDash", (int)DashStyle.LONG_DASH);
        G_DASH_STYLE_MAP.addEntry("lgDashDot", (int)DashStyle.LONG_DASH_DOT);
        G_DASH_STYLE_MAP.addEntry("lgDashDotDot", (int)DashStyle.LONG_DASH_DOT_DOT);
        G_DASH_STYLE_MAP.addEntry("solid", (int)DashStyle.SOLID);
        G_DASH_STYLE_MAP.addEntry("sysDash", (int)DashStyle.SHORT_DASH);
        G_DASH_STYLE_MAP.addEntry("sysDashDot", (int)DashStyle.SHORT_DASH_DOT);
        G_DASH_STYLE_MAP.addEntry("sysDashDotDot", (int)DashStyle.SHORT_DASH_DOT_DOT);
        G_DASH_STYLE_MAP.addEntry("sysDot", (int)DashStyle.SHORT_DOT);

        G_ARROW_TYPE_MAP.addEntry("arrow", (int)ArrowType.OPEN);
        G_ARROW_TYPE_MAP.addEntry("diamond", (int)ArrowType.DIAMOND);
        G_ARROW_TYPE_MAP.addEntry("none", (int)ArrowType.NONE);
        G_ARROW_TYPE_MAP.addEntry("oval", (int)ArrowType.OVAL);
        G_ARROW_TYPE_MAP.addEntry("stealth", (int)ArrowType.STEALTH);
        G_ARROW_TYPE_MAP.addEntry("triangle", (int)ArrowType.ARROW);

        G_ARROW_LENGTH_MAP.addEntry("lg", (int)ArrowLength.LONG);
        G_ARROW_LENGTH_MAP.addEntry("med", (int)ArrowLength.MEDIUM);
        G_ARROW_LENGTH_MAP.addEntry("sm", (int)ArrowLength.SHORT);

        G_ARROW_WIDTH_MAP.addEntry("lg", (int)ArrowWidth.WIDE);
        G_ARROW_WIDTH_MAP.addEntry("med", (int)ArrowWidth.MEDIUM);
        G_ARROW_WIDTH_MAP.addEntry("sm", (int)ArrowWidth.NARROW);

        G_TILE_FLIP_MODE_MAP.addEntry("none", (int)DmlTileFlipMode.NONE);
        G_TILE_FLIP_MODE_MAP.addEntry("x", (int)DmlTileFlipMode.HORIZONTAL);
        G_TILE_FLIP_MODE_MAP.addEntry("y", (int)DmlTileFlipMode.VERTICAL);
        G_TILE_FLIP_MODE_MAP.addEntry("xy", (int)DmlTileFlipMode.HORIZONTAL_AND_VERTICAL);

        G_PATH_SHADE_TYPE.addEntry("circle", (int)DmlPathShadeType.CIRCLE);
        G_PATH_SHADE_TYPE.addEntry("rect", (int)DmlPathShadeType.RECTANGLE);
        G_PATH_SHADE_TYPE.addEntry("shape", (int)DmlPathShadeType.SHAPE);

        G_LINE_ENDING_CAP_TYPE_MAP.addEntry("flat", (int)EndCap.FLAT);
        G_LINE_ENDING_CAP_TYPE_MAP.addEntry("rnd", (int)EndCap.ROUND);
        G_LINE_ENDING_CAP_TYPE_MAP.addEntry("sq", (int)EndCap.SQUARE);

        G_COMPOUND_LINE_TYPE_MAP.addEntry("sng", (int)ShapeLineStyle.SINGLE);
        G_COMPOUND_LINE_TYPE_MAP.addEntry("dbl", (int)ShapeLineStyle.DOUBLE);
        G_COMPOUND_LINE_TYPE_MAP.addEntry("thickThin", (int)ShapeLineStyle.THICK_THIN);
        G_COMPOUND_LINE_TYPE_MAP.addEntry("thinThick", (int)ShapeLineStyle.THIN_THICK);
        G_COMPOUND_LINE_TYPE_MAP.addEntry("tri", (int)ShapeLineStyle.TRIPLE);

        G_PRESET_DASH_TYPE_MAP.addEntry("solid", (int)DashStyle.SOLID);
        G_PRESET_DASH_TYPE_MAP.addEntry("dot", (int)DashStyle.DOT);
        G_PRESET_DASH_TYPE_MAP.addEntry("dash", (int)DashStyle.DASH);
        G_PRESET_DASH_TYPE_MAP.addEntry("lgDash", (int)DashStyle.LONG_DASH);
        G_PRESET_DASH_TYPE_MAP.addEntry("dashDot", (int)DashStyle.DASH_DOT);
        G_PRESET_DASH_TYPE_MAP.addEntry("lgDashDot", (int)DashStyle.LONG_DASH_DOT);
        G_PRESET_DASH_TYPE_MAP.addEntry("lgDashDotDot", (int)DashStyle.LONG_DASH_DOT_DOT);
        G_PRESET_DASH_TYPE_MAP.addEntry("sysDash", (int)DashStyle.SHORT_DASH);
        G_PRESET_DASH_TYPE_MAP.addEntry("sysDot", (int)DashStyle.SHORT_DOT);
        G_PRESET_DASH_TYPE_MAP.addEntry("sysDashDot", (int)DashStyle.SHORT_DASH_DOT);
        G_PRESET_DASH_TYPE_MAP.addEntry("sysDashDotDot", (int)DashStyle.SHORT_DASH_DOT_DOT);

        G_TEXT_ORIENTATION_MAP.addEntry("eaVert", (int)ShapeTextOrientation.VERTICAL_FAR_EAST);
        G_TEXT_ORIENTATION_MAP.addEntry("horz", (int)ShapeTextOrientation.HORIZONTAL);
        G_TEXT_ORIENTATION_MAP.addEntry("mongolianVert", (int)ShapeTextOrientation.VERTICAL_ROTATED_FAR_EAST);
        G_TEXT_ORIENTATION_MAP.addEntry("vert", (int)ShapeTextOrientation.DOWNWARD);
        G_TEXT_ORIENTATION_MAP.addEntry("vert270", (int)ShapeTextOrientation.UPWARD);
        G_TEXT_ORIENTATION_MAP.addEntry("wordArtVert", (int)ShapeTextOrientation.WORD_ART_VERTICAL);
        G_TEXT_ORIENTATION_MAP.addEntry("wordArtVertRtl", (int)ShapeTextOrientation.WORD_ART_VERTICAL_RIGHT_TO_LEFT);

        G_TEXT_HORIZONTAL_OVERFLOW_TYPE_MAP.addEntry("overflow", (int)DmlTextHorizontalOverflowType.OVERFLOW);
        G_TEXT_HORIZONTAL_OVERFLOW_TYPE_MAP.addEntry("clip", (int)DmlTextHorizontalOverflowType.CLIP);

        G_TEXT_VERTICAL_OVERFLOW_TYPE_MAP.addEntry("overflow", (int)DmlTextVerticalOverflowType.OVERFLOW);
        G_TEXT_VERTICAL_OVERFLOW_TYPE_MAP.addEntry("ellipsis", (int)DmlTextVerticalOverflowType.ELLIPSIS);
        G_TEXT_VERTICAL_OVERFLOW_TYPE_MAP.addEntry("clip", (int)DmlTextVerticalOverflowType.CLIP);

        G_TEXT_ANCHORING_TYPE_MAP.addEntry("b", (int)DmlTextAnchoringType.BOTTOM);
        G_TEXT_ANCHORING_TYPE_MAP.addEntry("ctr", (int)DmlTextAnchoringType.CENTER);
        G_TEXT_ANCHORING_TYPE_MAP.addEntry("dist", (int)DmlTextAnchoringType.DISTRIBUTED);
        G_TEXT_ANCHORING_TYPE_MAP.addEntry("just", (int)DmlTextAnchoringType.JUSTIFIED);
        G_TEXT_ANCHORING_TYPE_MAP.addEntry("t", (int)DmlTextAnchoringType.TOP);

        G_TEXT_WRAPPING_TYPE_MAP.addEntry("none", (int)TextBoxWrapMode.NONE);
        G_TEXT_WRAPPING_TYPE_MAP.addEntry("square", (int)TextBoxWrapMode.SQUARE);

        G_TEXT_ALIGNMENT_MAP.addEntry("ctr", (int)ParagraphAlignment.CENTER);
        G_TEXT_ALIGNMENT_MAP.addEntry("dist", (int)ParagraphAlignment.DISTRIBUTED);
        G_TEXT_ALIGNMENT_MAP.addEntry("just", (int)ParagraphAlignment.JUSTIFY);
        G_TEXT_ALIGNMENT_MAP.addEntry("justLow", (int)ParagraphAlignment.ARABIC_LOW_KASHIDA);
        G_TEXT_ALIGNMENT_MAP.addEntry("l", (int)ParagraphAlignment.LEFT);
        G_TEXT_ALIGNMENT_MAP.addEntry("r", (int)ParagraphAlignment.RIGHT);
        G_TEXT_ALIGNMENT_MAP.addEntry("thaiDist", (int)ParagraphAlignment.THAI_DISTRIBUTED);

        G_FONT_ALIGNMENT_MAP.addEntry("auto", (int)DmlFontAlignment.AUTOMATIC);
        G_FONT_ALIGNMENT_MAP.addEntry("base", (int)DmlFontAlignment.BASELINE);
        G_FONT_ALIGNMENT_MAP.addEntry("b", (int)DmlFontAlignment.BOTTOM);
        G_FONT_ALIGNMENT_MAP.addEntry("ctr", (int)DmlFontAlignment.CENTER);
        G_FONT_ALIGNMENT_MAP.addEntry("t", (int)DmlFontAlignment.TOP);

        G_CAPITALIZATION_MAP.addEntry("all", (int)DmlCapitalization.ALL);
        G_CAPITALIZATION_MAP.addEntry("none", (int)DmlCapitalization.NONE);
        G_CAPITALIZATION_MAP.addEntry("small", (int)DmlCapitalization.SMALL);

        G_TEXT_STRIKE_MAP.addEntry("dblStrike", (int)DmlTextStrike.DOUBLE);
        G_TEXT_STRIKE_MAP.addEntry("noStrike", (int)DmlTextStrike.NO);
        G_TEXT_STRIKE_MAP.addEntry("sngStrike", (int)DmlTextStrike.SINGLE);

        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("none", (int)Underline.NONE);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("words", (int)Underline.WORDS);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("sng", (int)Underline.SINGLE);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dbl", (int)Underline.DOUBLE);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("heavy", (int)Underline.THICK);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dotted", (int)Underline.DOTTED);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dottedHeavy", (int)Underline.DOTTED_HEAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dash", (int)Underline.DASH);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dashHeavy", (int)Underline.DASH_HEAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dashLong", (int)Underline.DASH_LONG);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dashLongHeavy", (int)Underline.DASH_LONG_HEAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dotDash", (int)Underline.DOT_DASH);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dotDashHeavy", (int)Underline.DOT_DASH_HEAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dotDotDash", (int)Underline.DOT_DOT_DASH);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("dotDotDashHeavy", (int)Underline.DOT_DOT_DASH_HEAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("wavy", (int)Underline.WAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("wavyHeavy", (int)Underline.WAVY_HEAVY);
        G_TEXT_UNDERLINE_TYPE_MAP.addEntry("wavyDbl", (int)Underline.WAVY_DOUBLE);

        G_PATTERN_TYPE_MAP.addEntry("pct5", (int)PatternType.PERCENT_5);
        G_PATTERN_TYPE_MAP.addEntry("pct10", (int)PatternType.PERCENT_10);
        G_PATTERN_TYPE_MAP.addEntry("pct20", (int)PatternType.PERCENT_20);
        G_PATTERN_TYPE_MAP.addEntry("pct25", (int)PatternType.PERCENT_25);
        G_PATTERN_TYPE_MAP.addEntry("pct30", (int)PatternType.PERCENT_30);
        G_PATTERN_TYPE_MAP.addEntry("pct40", (int)PatternType.PERCENT_40);
        G_PATTERN_TYPE_MAP.addEntry("pct50", (int)PatternType.PERCENT_50);
        G_PATTERN_TYPE_MAP.addEntry("pct60", (int)PatternType.PERCENT_60);
        G_PATTERN_TYPE_MAP.addEntry("pct70", (int)PatternType.PERCENT_70);
        G_PATTERN_TYPE_MAP.addEntry("pct75", (int)PatternType.PERCENT_75);
        G_PATTERN_TYPE_MAP.addEntry("pct80", (int)PatternType.PERCENT_80);
        G_PATTERN_TYPE_MAP.addEntry("pct90", (int)PatternType.PERCENT_90);
        G_PATTERN_TYPE_MAP.addEntry("horz", (int)PatternType.HORIZONTAL);
        G_PATTERN_TYPE_MAP.addEntry("vert", (int)PatternType.VERTICAL);
        G_PATTERN_TYPE_MAP.addEntry("ltHorz", (int)PatternType.LIGHT_HORIZONTAL);
        G_PATTERN_TYPE_MAP.addEntry("ltVert", (int)PatternType.LIGHT_VERTICAL);
        G_PATTERN_TYPE_MAP.addEntry("dkHorz", (int)PatternType.DARK_HORIZONTAL);
        G_PATTERN_TYPE_MAP.addEntry("dkVert", (int)PatternType.DARK_VERTICAL);
        G_PATTERN_TYPE_MAP.addEntry("narHorz", (int)PatternType.NARROW_HORIZONTAL);
        G_PATTERN_TYPE_MAP.addEntry("narVert", (int)PatternType.NARROW_VERTICAL);
        G_PATTERN_TYPE_MAP.addEntry("dashHorz", (int)PatternType.DASHED_HORIZONTAL);
        G_PATTERN_TYPE_MAP.addEntry("dashVert", (int)PatternType.DASHED_VERTICAL);
        G_PATTERN_TYPE_MAP.addEntry("cross", (int)PatternType.CROSS);
        G_PATTERN_TYPE_MAP.addEntry("dnDiag", (int)PatternType.DOWNWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("upDiag", (int)PatternType.UPWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("ltDnDiag", (int)PatternType.LIGHT_DOWNWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("ltUpDiag", (int)PatternType.LIGHT_UPWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("dkDnDiag", (int)PatternType.DARK_DOWNWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("dkUpDiag", (int)PatternType.DARK_UPWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("wdDnDiag", (int)PatternType.WIDE_DOWNWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("wdUpDiag", (int)PatternType.WIDE_UPWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("dashDnDiag", (int)PatternType.DASHED_DOWNWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("dashUpDiag", (int)PatternType.DASHED_UPWARD_DIAGONAL);
        G_PATTERN_TYPE_MAP.addEntry("diagCross", (int)PatternType.DIAGONAL_CROSS);
        G_PATTERN_TYPE_MAP.addEntry("smCheck", (int)PatternType.SMALL_CHECKER_BOARD);
        G_PATTERN_TYPE_MAP.addEntry("lgCheck", (int)PatternType.LARGE_CHECKER_BOARD);
        G_PATTERN_TYPE_MAP.addEntry("smGrid", (int)PatternType.SMALL_GRID);
        G_PATTERN_TYPE_MAP.addEntry("lgGrid", (int)PatternType.LARGE_GRID);
        G_PATTERN_TYPE_MAP.addEntry("dotGrid", (int)PatternType.DOTTED_GRID);
        G_PATTERN_TYPE_MAP.addEntry("smConfetti", (int)PatternType.SMALL_CONFETTI);
        G_PATTERN_TYPE_MAP.addEntry("lgConfetti", (int)PatternType.LARGE_CONFETTI);
        G_PATTERN_TYPE_MAP.addEntry("horzBrick", (int)PatternType.HORIZONTAL_BRICK);
        G_PATTERN_TYPE_MAP.addEntry("diagBrick", (int)PatternType.DIAGONAL_BRICK);
        G_PATTERN_TYPE_MAP.addEntry("solidDmnd", (int)PatternType.SOLID_DIAMOND);
        G_PATTERN_TYPE_MAP.addEntry("openDmnd", (int)PatternType.OUTLINED_DIAMOND);
        G_PATTERN_TYPE_MAP.addEntry("dotDmnd", (int)PatternType.DOTTED_DIAMOND);
        G_PATTERN_TYPE_MAP.addEntry("plaid", (int)PatternType.PLAID);
        G_PATTERN_TYPE_MAP.addEntry("sphere", (int)PatternType.SPHERE);
        G_PATTERN_TYPE_MAP.addEntry("weave", (int)PatternType.WEAVE);
        G_PATTERN_TYPE_MAP.addEntry("divot", (int)PatternType.DIVOT);
        G_PATTERN_TYPE_MAP.addEntry("shingle", (int)PatternType.SHINGLE);
        G_PATTERN_TYPE_MAP.addEntry("wave", (int)PatternType.WAVE);
        G_PATTERN_TYPE_MAP.addEntry("trellis", (int)PatternType.TRELLIS);
        G_PATTERN_TYPE_MAP.addEntry("zigZag", (int)PatternType.ZIG_ZAG);

        G_RECTANGLE_ALIGNMENT_MAP.addEntry("tl", (int)DmlRectangleAlignment.TOP_LEFT);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("t", (int)DmlRectangleAlignment.TOP);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("tr", (int)DmlRectangleAlignment.TOP_RIGHT);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("l", (int)DmlRectangleAlignment.LEFT);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("ctr", (int)DmlRectangleAlignment.CENTER);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("r", (int)DmlRectangleAlignment.RIGHT);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("bl", (int)DmlRectangleAlignment.BOTTOM_LEFT);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("b", (int)DmlRectangleAlignment.BOTTOM);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("br", (int)DmlRectangleAlignment.BOTTOM_RIGHT);
        G_RECTANGLE_ALIGNMENT_MAP.addEntry("none", (int)DmlRectangleAlignment.NONE);

        G_COMPRESSION_STATE_MAP.addEntry("email", (int)DmlCompressionState.EMAIL);
        G_COMPRESSION_STATE_MAP.addEntry("screen", (int)DmlCompressionState.SCREEN);
        G_COMPRESSION_STATE_MAP.addEntry("print", (int)DmlCompressionState.PRINT);
        G_COMPRESSION_STATE_MAP.addEntry("hqprint", (int)DmlCompressionState.HIGH_QUALITY_PRINT);
        G_COMPRESSION_STATE_MAP.addEntry("none", (int)DmlCompressionState.NONE);

        G_EFFECT_BLEND_MODE_MAP.addEntry("darken", (int)DmlEffectBlendMode.DARKEN);
        G_EFFECT_BLEND_MODE_MAP.addEntry("lighten", (int)DmlEffectBlendMode.LIGHTEN);
        G_EFFECT_BLEND_MODE_MAP.addEntry("mult", (int)DmlEffectBlendMode.MULTIPLY);
        G_EFFECT_BLEND_MODE_MAP.addEntry("over", (int)DmlEffectBlendMode.OVERLAY);
        G_EFFECT_BLEND_MODE_MAP.addEntry("screen", (int)DmlEffectBlendMode.SCREEN);

        G_PATH_FILL_MODE_MAP.addEntry("darken", (int)DmlPathFillMode.DARKEN);
        G_PATH_FILL_MODE_MAP.addEntry("darkenLess", (int)DmlPathFillMode.DARKEN_LESS);
        G_PATH_FILL_MODE_MAP.addEntry("lighten", (int)DmlPathFillMode.LIGHTEN);
        G_PATH_FILL_MODE_MAP.addEntry("lightenLess", (int)DmlPathFillMode.LIGHTEN_LESS);
        G_PATH_FILL_MODE_MAP.addEntry("norm", (int)DmlPathFillMode.NORM);
        G_PATH_FILL_MODE_MAP.addEntry("none", (int)DmlPathFillMode.NONE);

        G_PRESET_SHADOW_MAP.addEntry("shdw1", (int)DmlPresetShadow.TOP_LEFT_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw10", (int)DmlPresetShadow.TOP_LEFT_LARGE_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw11", (int)DmlPresetShadow.BACK_LEFT_LONG_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw12", (int)DmlPresetShadow.BACK_RIGHT_LONG_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw13", (int)DmlPresetShadow.TOP_LEFT_DOUBLE_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw14", (int)DmlPresetShadow.BOTTOM_RIGHT_SMALL_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw15", (int)DmlPresetShadow.FRONT_LEFT_LONG_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw16", (int)DmlPresetShadow.FRONT_RIGHT_LONG_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw17", (int)DmlPresetShadow.OUTER_BOX_SHADOW_3_D);
        G_PRESET_SHADOW_MAP.addEntry("shdw18", (int)DmlPresetShadow.INNER_BOX_SHADOW_3_D);
        G_PRESET_SHADOW_MAP.addEntry("shdw19", (int)DmlPresetShadow.BACK_CENTER_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw2", (int)DmlPresetShadow.TOP_RIGHT_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw20", (int)DmlPresetShadow.FRONT_BOTTOM_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw3", (int)DmlPresetShadow.BACK_LEFT_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw4", (int)DmlPresetShadow.BACK_RIGHT_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw5", (int)DmlPresetShadow.BOTTOM_LEFT_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw6", (int)DmlPresetShadow.BOTTOM_RIGHT_DROP_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw7", (int)DmlPresetShadow.FRONT_LEFT_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw8", (int)DmlPresetShadow.FRONT_RIGHT_PERSPECTIVE_SHADOW);
        G_PRESET_SHADOW_MAP.addEntry("shdw9", (int)DmlPresetShadow.TOP_LEFT_SMALL_DROP_SHADOW);

        G_FONT_COLLECTION_INDEX_MAP.addEntry("major", (int)DmlFontCollectionIndex.MAJOR);
        G_FONT_COLLECTION_INDEX_MAP.addEntry("minor", (int)DmlFontCollectionIndex.MINOR);
        G_FONT_COLLECTION_INDEX_MAP.addEntry("none", (int)DmlFontCollectionIndex.NONE);

        G_TEXT_SHAPE_TYPE_MAP.addEntry("textArchDown", (int)DmlTextShapeType.TEXT_ARCH_DOWN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textArchDownPour", (int)DmlTextShapeType.TEXT_ARCH_DOWN_POUR);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textArchUp", (int)DmlTextShapeType.TEXT_ARCH_UP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textArchUpPour", (int)DmlTextShapeType.TEXT_ARCH_UP_POUR);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textButton", (int)DmlTextShapeType.TEXT_BUTTON);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textButtonPour", (int)DmlTextShapeType.TEXT_BUTTON_POUR);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCascadeDown", (int)DmlTextShapeType.TEXT_CAN_DOWN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCanUp", (int)DmlTextShapeType.TEXT_CAN_UP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCanDown", (int)DmlTextShapeType.TEXT_CASCADE_DOWN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCascadeUp", (int)DmlTextShapeType.TEXT_CASCADE_UP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textChevron", (int)DmlTextShapeType.TEXT_CHEVRON);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textChevronInverted", (int)DmlTextShapeType.TEXT_CHEVRON_INVERTED);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCircle", (int)DmlTextShapeType.TEXT_CIRCLE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCirclePour", (int)DmlTextShapeType.TEXT_CIRCLE_POUR);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCurveDown", (int)DmlTextShapeType.TEXT_CURVE_DOWN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textCurveUp", (int)DmlTextShapeType.TEXT_CURVE_UP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textDeflate", (int)DmlTextShapeType.TEXT_DEFLATE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textDeflateBottom", (int)DmlTextShapeType.TEXT_DEFLATE_BOTTOM);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textDeflateInflate", (int)DmlTextShapeType.TEXT_DEFLATE_INFLATE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textDeflateInflateDeflate", (int)DmlTextShapeType.TEXT_DEFLATE_INFLATE_DEFLATE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textDeflateTop", (int)DmlTextShapeType.TEXT_DEFLATE_TOP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textDoubleWave1", (int)DmlTextShapeType.TEXT_DOUBLE_WAVE_1);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textFadeDown", (int)DmlTextShapeType.TEXT_FADE_DOWN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textFadeLeft", (int)DmlTextShapeType.TEXT_FADE_LEFT);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textFadeRight", (int)DmlTextShapeType.TEXT_FADE_RIGHT);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textFadeUp", (int)DmlTextShapeType.TEXT_FADE_UP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textInflate", (int)DmlTextShapeType.TEXT_INFLATE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textInflateBottom", (int)DmlTextShapeType.TEXT_INFLATE_BOTTOM);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textInflateTop", (int)DmlTextShapeType.TEXT_INFLATE_TOP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textNoShape", (int)DmlTextShapeType.TEXT_NO_SHAPE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textPlain", (int)DmlTextShapeType.TEXT_PLAIN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textRingInside", (int)DmlTextShapeType.TEXT_RING_INSIDE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textRingOutside", (int)DmlTextShapeType.TEXT_RING_OUTSIDE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textSlantDown", (int)DmlTextShapeType.TEXT_SLANT_DOWN);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textSlantUp", (int)DmlTextShapeType.TEXT_SLANT_UP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textStop", (int)DmlTextShapeType.TEXT_STOP);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textTriangle", (int)DmlTextShapeType.TEXT_TRIANGLE);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textTriangleInverted", (int)DmlTextShapeType.TEXT_TRIANGLE_INVERTED);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textWave1", (int)DmlTextShapeType.TEXT_WAVE_1);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textWave2", (int)DmlTextShapeType.TEXT_WAVE_2);
        G_TEXT_SHAPE_TYPE_MAP.addEntry("textWave4", (int)DmlTextShapeType.TEXT_WAVE_4);

        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueTopLeft", (int)DmlPresetCameraType.LEGACY_OBLIQUE_TOP_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueTop", (int)DmlPresetCameraType.LEGACY_OBLIQUE_TOP);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueTopRight", (int)DmlPresetCameraType.LEGACY_OBLIQUE_TOP_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueLeft", (int)DmlPresetCameraType.LEGACY_OBLIQUE_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueFront", (int)DmlPresetCameraType.LEGACY_OBLIQUE_FRONT);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueRight", (int)DmlPresetCameraType.LEGACY_OBLIQUE_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueBottomLeft", (int)DmlPresetCameraType.LEGACY_OBLIQUE_BOTTOM_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueBottom", (int)DmlPresetCameraType.LEGACY_OBLIQUE_BOTTOM);
        G_CAMERA_TYPE_MAP.addEntry("legacyObliqueBottomRight", (int)DmlPresetCameraType.LEGACY_OBLIQUE_BOTTOM_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveTopLeft", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_TOP_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveTop", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_TOP);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveTopRight", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_TOP_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveLeft", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveFront", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_FRONT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveRight", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveBottomLeft", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_BOTTOM_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveBottom", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_BOTTOM);
        G_CAMERA_TYPE_MAP.addEntry("legacyPerspectiveBottomRight", (int)DmlPresetCameraType.LEGACY_PERSPECTIVE_BOTTOM_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("orthographicFront", (int)DmlPresetCameraType.ORTHOGRAPHIC_FRONT);
        G_CAMERA_TYPE_MAP.addEntry("isometricTopUp", (int)DmlPresetCameraType.ISOMETRIC_TOP_UP);
        G_CAMERA_TYPE_MAP.addEntry("isometricTopDown", (int)DmlPresetCameraType.ISOMETRIC_TOP_DOWN);
        G_CAMERA_TYPE_MAP.addEntry("isometricBottomUp", (int)DmlPresetCameraType.ISOMETRIC_BOTTOM_UP);
        G_CAMERA_TYPE_MAP.addEntry("isometricBottomDown", (int)DmlPresetCameraType.ISOMETRIC_BOTTOM_DOWN);
        G_CAMERA_TYPE_MAP.addEntry("isometricLeftUp", (int)DmlPresetCameraType.ISOMETRIC_LEFT_UP);
        G_CAMERA_TYPE_MAP.addEntry("isometricLeftDown", (int)DmlPresetCameraType.ISOMETRIC_LEFT_DOWN);
        G_CAMERA_TYPE_MAP.addEntry("isometricRightUp", (int)DmlPresetCameraType.ISOMETRIC_RIGHT_UP);
        G_CAMERA_TYPE_MAP.addEntry("isometricRightDown", (int)DmlPresetCameraType.ISOMETRIC_RIGHT_DOWN);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis1Left", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_1_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis1Right", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_1_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis1Top", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_1_TOP);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis2Left", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_2_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis2Right", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_2_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis2Top", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_2_TOP);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis3Left", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_3_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis3Right", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_3_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis3Bottom", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_3_BOTTOM);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis4Left", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_4_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis4Right", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_4_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("isometricOffAxis4Bottom", (int)DmlPresetCameraType.ISOMETRIC_OFF_AXIS_4_BOTTOM);
        G_CAMERA_TYPE_MAP.addEntry("obliqueTopLeft", (int)DmlPresetCameraType.OBLIQUE_TOP_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("obliqueTop", (int)DmlPresetCameraType.OBLIQUE_TOP);
        G_CAMERA_TYPE_MAP.addEntry("obliqueTopRight", (int)DmlPresetCameraType.OBLIQUE_TOP_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("obliqueLeft", (int)DmlPresetCameraType.OBLIQUE_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("obliqueRight", (int)DmlPresetCameraType.OBLIQUE_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("obliqueBottomLeft", (int)DmlPresetCameraType.OBLIQUE_BOTTOM_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("obliqueBottom", (int)DmlPresetCameraType.OBLIQUE_BOTTOM);
        G_CAMERA_TYPE_MAP.addEntry("obliqueBottomRight", (int)DmlPresetCameraType.OBLIQUE_BOTTOM_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveFront", (int)DmlPresetCameraType.PERSPECTIVE_FRONT);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveLeft", (int)DmlPresetCameraType.PERSPECTIVE_LEFT);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveRight", (int)DmlPresetCameraType.PERSPECTIVE_RIGHT);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveAbove", (int)DmlPresetCameraType.PERSPECTIVE_ABOVE);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveBelow", (int)DmlPresetCameraType.PERSPECTIVE_BELOW);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveAboveLeftFacing", (int)DmlPresetCameraType.PERSPECTIVE_ABOVE_LEFT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveAboveRightFacing", (int)DmlPresetCameraType.PERSPECTIVE_ABOVE_RIGHT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveContrastingLeftFacing",
            (int)DmlPresetCameraType.PERSPECTIVE_CONTRASTING_LEFT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveContrastingRightFacing",
            (int)DmlPresetCameraType.PERSPECTIVE_CONTRASTING_RIGHT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveHeroicLeftFacing", (int)DmlPresetCameraType.PERSPECTIVE_HEROIC_LEFT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveHeroicRightFacing", (int)DmlPresetCameraType.PERSPECTIVE_HEROIC_RIGHT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveHeroicExtremeLeftFacing",
            (int)DmlPresetCameraType.PERSPECTIVE_HEROIC_EXTREME_LEFT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveHeroicExtremeRightFacing",
            (int)DmlPresetCameraType.PERSPECTIVE_HEROIC_EXTREME_RIGHT_FACING);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveRelaxed", (int)DmlPresetCameraType.PERSPECTIVE_RELAXED);
        G_CAMERA_TYPE_MAP.addEntry("perspectiveRelaxedModerately", (int)DmlPresetCameraType.PERSPECTIVE_RELAXED_MODERATELY);

        G_LIGHT_RIG_DIRECTION_MAP.addEntry("tl", (int)DmlLightRigDirection.TOP_LEFT);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("t", (int)DmlLightRigDirection.TOP);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("tr", (int)DmlLightRigDirection.TOP_RIGHT);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("l", (int)DmlLightRigDirection.LEFT);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("r", (int)DmlLightRigDirection.RIGHT);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("bl", (int)DmlLightRigDirection.BOTTOM_LEFT);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("b", (int)DmlLightRigDirection.BOTTOM);
        G_LIGHT_RIG_DIRECTION_MAP.addEntry("br", (int)DmlLightRigDirection.BOTTOM_RIGHT);

        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyFlat1", (int)DmlLightRigType.LEGACY_FLAT_1);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyFlat2", (int)DmlLightRigType.LEGACY_FLAT_2);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyFlat3", (int)DmlLightRigType.LEGACY_FLAT_3);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyFlat4", (int)DmlLightRigType.LEGACY_FLAT_4);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyNormal1", (int)DmlLightRigType.LEGACY_NORMAL_1);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyNormal2", (int)DmlLightRigType.LEGACY_NORMAL_2);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyNormal3", (int)DmlLightRigType.LEGACY_NORMAL_3);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyNormal4", (int)DmlLightRigType.LEGACY_NORMAL_4);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyHarsh1", (int)DmlLightRigType.LEGACY_HARSH_1);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyHarsh2", (int)DmlLightRigType.LEGACY_HARSH_2);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyHarsh3", (int)DmlLightRigType.LEGACY_HARSH_3);
        G_LIGHT_RIG_TYPE_MAP.addEntry("legacyHarsh4", (int)DmlLightRigType.LEGACY_HARSH_4);
        G_LIGHT_RIG_TYPE_MAP.addEntry("threePt", (int)DmlLightRigType.THREE_PT);
        G_LIGHT_RIG_TYPE_MAP.addEntry("balanced", (int)DmlLightRigType.BALANCED);
        G_LIGHT_RIG_TYPE_MAP.addEntry("soft", (int)DmlLightRigType.SOFT);
        G_LIGHT_RIG_TYPE_MAP.addEntry("harsh", (int)DmlLightRigType.HARSH);
        G_LIGHT_RIG_TYPE_MAP.addEntry("flood", (int)DmlLightRigType.FLOOD);
        G_LIGHT_RIG_TYPE_MAP.addEntry("contrasting", (int)DmlLightRigType.CONTRASTING);
        G_LIGHT_RIG_TYPE_MAP.addEntry("morning", (int)DmlLightRigType.MORNING);
        G_LIGHT_RIG_TYPE_MAP.addEntry("sunrise", (int)DmlLightRigType.SUNRISE);
        G_LIGHT_RIG_TYPE_MAP.addEntry("sunset", (int)DmlLightRigType.SUNSET);
        G_LIGHT_RIG_TYPE_MAP.addEntry("chilly", (int)DmlLightRigType.CHILLY);
        G_LIGHT_RIG_TYPE_MAP.addEntry("freezing", (int)DmlLightRigType.FREEZING);
        G_LIGHT_RIG_TYPE_MAP.addEntry("flat", (int)DmlLightRigType.FLAT);
        G_LIGHT_RIG_TYPE_MAP.addEntry("twoPt", (int)DmlLightRigType.TWO_PT);
        G_LIGHT_RIG_TYPE_MAP.addEntry("glow", (int)DmlLightRigType.GLOW);
        G_LIGHT_RIG_TYPE_MAP.addEntry("brightRoom", (int)DmlLightRigType.BRIGHT_ROOM);

        G_PRESET_MATERIAL_MAP.addEntry("legacyMatte", (int)DmlPresetMaterialType.LEGACY_MATTE);
        G_PRESET_MATERIAL_MAP.addEntry("legacyPlastic", (int)DmlPresetMaterialType.LEGACY_PLASTIC);
        G_PRESET_MATERIAL_MAP.addEntry("legacyMetal", (int)DmlPresetMaterialType.LEGACY_METAL);
        G_PRESET_MATERIAL_MAP.addEntry("legacyWireframe", (int)DmlPresetMaterialType.LEGACY_WIREFRAME);
        G_PRESET_MATERIAL_MAP.addEntry("matte", (int)DmlPresetMaterialType.MATTE);
        G_PRESET_MATERIAL_MAP.addEntry("plastic", (int)DmlPresetMaterialType.PLASTIC);
        G_PRESET_MATERIAL_MAP.addEntry("metal", (int)DmlPresetMaterialType.METAL);
        G_PRESET_MATERIAL_MAP.addEntry("warmMatte", (int)DmlPresetMaterialType.WARM_MATTE);
        G_PRESET_MATERIAL_MAP.addEntry("translucentPowder", (int)DmlPresetMaterialType.TRANSLUCENT_POWDER);
        G_PRESET_MATERIAL_MAP.addEntry("powder", (int)DmlPresetMaterialType.POWDER);
        G_PRESET_MATERIAL_MAP.addEntry("dkEdge", (int)DmlPresetMaterialType.DARK_EDGE);
        G_PRESET_MATERIAL_MAP.addEntry("softEdge", (int)DmlPresetMaterialType.SOFT_EDGE);
        G_PRESET_MATERIAL_MAP.addEntry("clear", (int)DmlPresetMaterialType.CLEAR);
        G_PRESET_MATERIAL_MAP.addEntry("flat", (int)DmlPresetMaterialType.FLAT);
        G_PRESET_MATERIAL_MAP.addEntry("softmetal", (int)DmlPresetMaterialType.SOFT_METAL);

        G_BEVEL_PRESET_TYPE_MAP.addEntry("relaxedInset", (int)DmlBevelPresetType.RELAXED_INSET);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("circle", (int)DmlBevelPresetType.CIRCLE);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("slope", (int)DmlBevelPresetType.SLOPE);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("cross", (int)DmlBevelPresetType.CROSS);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("angle", (int)DmlBevelPresetType.ANGLE);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("softRound", (int)DmlBevelPresetType.SOFT_ROUND);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("convex", (int)DmlBevelPresetType.CONVEX);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("coolSlant", (int)DmlBevelPresetType.COOL_SLANT);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("divot", (int)DmlBevelPresetType.DIVOT);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("riblet", (int)DmlBevelPresetType.RIBLET);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("hardEdge", (int)DmlBevelPresetType.HARD_EDGE);
        G_BEVEL_PRESET_TYPE_MAP.addEntry("artDeco", (int)DmlBevelPresetType.ART_DECO);

        G_TEXT_BULLET_TYPE_MAP.addEntry("alphaLcParenBoth", (int)DmlTextBulletType.ALPHA_LC_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("alphaUcParenBoth", (int)DmlTextBulletType.ALPHA_UC_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("alphaLcParenR", (int)DmlTextBulletType.ALPHA_LC_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("alphaUcParenR", (int)DmlTextBulletType.ALPHA_UC_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("alphaLcPeriod", (int)DmlTextBulletType.ALPHA_LC_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("alphaUcPeriod", (int)DmlTextBulletType.ALPHA_UC_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabicParenBoth", (int)DmlTextBulletType.ARABIC_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabicParenR", (int)DmlTextBulletType.ARABIC_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabicPeriod", (int)DmlTextBulletType.ARABIC_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabicPlain", (int)DmlTextBulletType.ARABIC_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("romanLcParenBoth", (int)DmlTextBulletType.ROMAN_LC_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("romanUcParenBoth", (int)DmlTextBulletType.ROMAN_UC_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("romanLcParenR", (int)DmlTextBulletType.ROMAN_LC_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("romanUcParenR", (int)DmlTextBulletType.ROMAN_UC_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("romanLcPeriod", (int)DmlTextBulletType.ROMAN_LC_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("romanUcPeriod", (int)DmlTextBulletType.ROMAN_UC_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("circleNumDbPlain", (int)DmlTextBulletType.CIRCLE_NUM_DB_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("circleNumWdBlackPlain", (int)DmlTextBulletType.CIRCLE_NUM_WD_BLACK_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("circleNumWdWhitePlain", (int)DmlTextBulletType.CIRCLE_NUM_WD_WHITE_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabicDbPeriod", (int)DmlTextBulletType.ARABIC_DB_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabicDbPlain", (int)DmlTextBulletType.ARABIC_DB_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1ChsPeriod", (int)DmlTextBulletType.EA_1_CHS_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1ChsPlain", (int)DmlTextBulletType.EA_1_CHS_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1ChtPeriod", (int)DmlTextBulletType.EA_1_CHT_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1ChtPlain", (int)DmlTextBulletType.EA_1_CHT_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1JpnChsDbPeriod", (int)DmlTextBulletType.EA_1_JPN_CHS_DB_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1JpnKorPlain", (int)DmlTextBulletType.EA_1_JPN_KOR_PLAIN);
        G_TEXT_BULLET_TYPE_MAP.addEntry("ea1JpnKorPeriod", (int)DmlTextBulletType.EA_1_JPN_KOR_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabic1Minus", (int)DmlTextBulletType.ARABIC_1_MINUS);
        G_TEXT_BULLET_TYPE_MAP.addEntry("arabic2Minus", (int)DmlTextBulletType.ARABIC_2_MINUS);
        G_TEXT_BULLET_TYPE_MAP.addEntry("hebrew2Minus", (int)DmlTextBulletType.HEBREW_2_MINUS);
        G_TEXT_BULLET_TYPE_MAP.addEntry("thaiAlphaPeriod", (int)DmlTextBulletType.THAI_ALPHA_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("thaiAlphaParenR", (int)DmlTextBulletType.THAI_ALPHA_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("thaiAlphaParenBoth", (int)DmlTextBulletType.THAI_ALPHA_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("thaiNumPeriod", (int)DmlTextBulletType.THAI_NUM_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("thaiNumParenR", (int)DmlTextBulletType.THAI_NUM_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("thaiNumParenBoth", (int)DmlTextBulletType.THAI_NUM_PAREN_BOTH);
        G_TEXT_BULLET_TYPE_MAP.addEntry("hindiAlphaPeriod", (int)DmlTextBulletType.HINDI_ALPHA_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("hindiNumPeriod", (int)DmlTextBulletType.HINDI_NUM_PERIOD);
        G_TEXT_BULLET_TYPE_MAP.addEntry("hindiNumParenR", (int)DmlTextBulletType.HINDI_NUM_PAREN_R);
        G_TEXT_BULLET_TYPE_MAP.addEntry("hindiAlpha1Period", (int)DmlTextBulletType.HINDI_ALPHA_1_PERIOD);

        G_TAB_ALIGNMENT_MAP.addEntry("ctr", (int)TabAlignment.CENTER);
        G_TAB_ALIGNMENT_MAP.addEntry("dec", (int)TabAlignment.DECIMAL);
        G_TAB_ALIGNMENT_MAP.addEntry("l", (int)TabAlignment.LEFT);
        G_TAB_ALIGNMENT_MAP.addEntry("r", (int)TabAlignment.RIGHT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("accentBorderCallout1", (int)ShapeType.ACCENT_BORDER_CALLOUT_1);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("accentBorderCallout2", (int)ShapeType.ACCENT_BORDER_CALLOUT_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("accentBorderCallout3", (int)ShapeType.ACCENT_BORDER_CALLOUT_3);

        G_EFFECT_DAG_TYPE_MAP.addEntry("sib", (int)EffectDagType.SIBLING);
        G_EFFECT_DAG_TYPE_MAP.addEntry("tree", (int)EffectDagType.TREE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("accentCallout1", (int)ShapeType.ACCENT_CALLOUT_1);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("accentCallout2", (int)ShapeType.ACCENT_CALLOUT_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("accentCallout3", (int)ShapeType.ACCENT_CALLOUT_3);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonBackPrevious", (int)ShapeType.ACTION_BUTTON_BACK_PREVIOUS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonBeginning", (int)ShapeType.ACTION_BUTTON_BEGINNING);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonBlank", (int)ShapeType.ACTION_BUTTON_BLANK);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonDocument", (int)ShapeType.ACTION_BUTTON_DOCUMENT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonEnd", (int)ShapeType.ACTION_BUTTON_END);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonForwardNext", (int)ShapeType.ACTION_BUTTON_FORWARD_NEXT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonHelp", (int)ShapeType.ACTION_BUTTON_HELP);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonHome", (int)ShapeType.ACTION_BUTTON_HOME);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonInformation", (int)ShapeType.ACTION_BUTTON_INFORMATION);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonMovie", (int)ShapeType.ACTION_BUTTON_MOVIE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonReturn", (int)ShapeType.ACTION_BUTTON_RETURN);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("actionButtonSound", (int)ShapeType.ACTION_BUTTON_SOUND);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bentConnector2", (int)ShapeType.BENT_CONNECTOR_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bentConnector3", (int)ShapeType.BENT_CONNECTOR_3);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bentConnector4", (int)ShapeType.BENT_CONNECTOR_4);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bentConnector5", (int)ShapeType.BENT_CONNECTOR_5);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bevel", (int)ShapeType.BEVEL);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("borderCallout1", (int)ShapeType.BORDER_CALLOUT_1);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("borderCallout2", (int)ShapeType.BORDER_CALLOUT_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("borderCallout3", (int)ShapeType.BORDER_CALLOUT_3);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bracePair", (int)ShapeType.BRACE_PAIR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bracketPair", (int)ShapeType.BRACKET_PAIR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("callout1", (int)ShapeType.CALLOUT_1);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("callout2", (int)ShapeType.CALLOUT_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("callout3", (int)ShapeType.CALLOUT_3);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("can", (int)ShapeType.CAN);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("chevron", (int)ShapeType.CHEVRON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("cloudCallout", (int)ShapeType.CLOUD_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("cube", (int)ShapeType.CUBE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedConnector2", (int)ShapeType.CURVED_CONNECTOR_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedConnector3", (int)ShapeType.CURVED_CONNECTOR_3);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedConnector4", (int)ShapeType.CURVED_CONNECTOR_4);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedConnector5", (int)ShapeType.CURVED_CONNECTOR_5);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedDownArrow", (int)ShapeType.CURVED_DOWN_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedLeftArrow", (int)ShapeType.CURVED_LEFT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedRightArrow", (int)ShapeType.CURVED_RIGHT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("curvedUpArrow", (int)ShapeType.CURVED_UP_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("diamond", (int)ShapeType.DIAMOND);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("donut", (int)ShapeType.DONUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("doubleWave", (int)ShapeType.DOUBLE_WAVE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("downArrow", (int)ShapeType.DOWN_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("downArrowCallout", (int)ShapeType.DOWN_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("ellipse", (int)ShapeType.ELLIPSE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("ellipseRibbon", (int)ShapeType.ELLIPSE_RIBBON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("ellipseRibbon2", (int)ShapeType.ELLIPSE_RIBBON_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartAlternateProcess", (int)ShapeType.FLOW_CHART_ALTERNATE_PROCESS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartCollate", (int)ShapeType.FLOW_CHART_COLLATE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartConnector", (int)ShapeType.FLOW_CHART_CONNECTOR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartDecision", (int)ShapeType.FLOW_CHART_DECISION);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartDelay", (int)ShapeType.FLOW_CHART_DELAY);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartDisplay", (int)ShapeType.FLOW_CHART_DISPLAY);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartDocument", (int)ShapeType.FLOW_CHART_DOCUMENT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartExtract", (int)ShapeType.FLOW_CHART_EXTRACT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartInputOutput", (int)ShapeType.FLOW_CHART_INPUT_OUTPUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartInternalStorage", (int)ShapeType.FLOW_CHART_INTERNAL_STORAGE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartMagneticDisk", (int)ShapeType.FLOW_CHART_MAGNETIC_DISK);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartMagneticDrum", (int)ShapeType.FLOW_CHART_MAGNETIC_DRUM);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartMagneticTape", (int)ShapeType.FLOW_CHART_MAGNETIC_TAPE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartManualInput", (int)ShapeType.FLOW_CHART_MANUAL_INPUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartManualOperation", (int)ShapeType.FLOW_CHART_MANUAL_OPERATION);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartMerge", (int)ShapeType.FLOW_CHART_MERGE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartMultidocument", (int)ShapeType.FLOW_CHART_MULTIDOCUMENT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartOfflineStorage", (int)ShapeType.FLOW_CHART_OFFLINE_STORAGE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartOffpageConnector", (int)ShapeType.FLOW_CHART_OFFPAGE_CONNECTOR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartOnlineStorage", (int)ShapeType.FLOW_CHART_ONLINE_STORAGE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartOr", (int)ShapeType.FLOW_CHART_OR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartPredefinedProcess", (int)ShapeType.FLOW_CHART_PREDEFINED_PROCESS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartPreparation", (int)ShapeType.FLOW_CHART_PREPARATION);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartProcess", (int)ShapeType.FLOW_CHART_PROCESS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartPunchedCard", (int)ShapeType.FLOW_CHART_PUNCHED_CARD);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartPunchedTape", (int)ShapeType.FLOW_CHART_PUNCHED_TAPE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartSort", (int)ShapeType.FLOW_CHART_SORT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartSummingJunction", (int)ShapeType.FLOW_CHART_SUMMING_JUNCTION);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("flowChartTerminator", (int)ShapeType.FLOW_CHART_TERMINATOR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("foldedCorner", (int)ShapeType.FOLDED_CORNER);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("hexagon", (int)ShapeType.HEXAGON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("homePlate", (int)ShapeType.HOME_PLATE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("horizontalScroll", (int)ShapeType.HORIZONTAL_SCROLL);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("irregularSeal1", (int)ShapeType.IRREGULAR_SEAL_1);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("irregularSeal2", (int)ShapeType.IRREGULAR_SEAL_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftArrow", (int)ShapeType.LEFT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftArrowCallout", (int)ShapeType.LEFT_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftBrace", (int)ShapeType.LEFT_BRACE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftBracket", (int)ShapeType.LEFT_BRACKET);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftRightArrow", (int)ShapeType.LEFT_RIGHT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftRightArrowCallout", (int)ShapeType.LEFT_RIGHT_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("lightningBolt", (int)ShapeType.LIGHTNING_BOLT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("line", (int)ShapeType.LINE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("moon", (int)ShapeType.MOON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("noSmoking", (int)ShapeType.NO_SMOKING);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("notchedRightArrow", (int)ShapeType.NOTCHED_RIGHT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("octagon", (int)ShapeType.OCTAGON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("parallelogram", (int)ShapeType.PARALLELOGRAM);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("pentagon", (int)ShapeType.PENTAGON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("plaque", (int)ShapeType.PLAQUE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("plus", (int)ShapeType.PLUS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("rect", (int)ShapeType.RECTANGLE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("ribbon", (int)ShapeType.RIBBON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("ribbon2", (int)ShapeType.RIBBON_2);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("rightArrow", (int)ShapeType.ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("rightArrowCallout", (int)ShapeType.RIGHT_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("rightBrace", (int)ShapeType.RIGHT_BRACE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("rightBracket", (int)ShapeType.RIGHT_BRACKET);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("smileyFace", (int)ShapeType.SMILEY_FACE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star16", (int)ShapeType.SEAL_16);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star32", (int)ShapeType.SEAL_32);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star4", (int)ShapeType.SEAL_4);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star8", (int)ShapeType.SEAL_8);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("straightConnector1", (int)ShapeType.STRAIGHT_CONNECTOR_1);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("stripedRightArrow", (int)ShapeType.STRIPED_RIGHT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("sun", (int)ShapeType.SUN);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("triangle", (int)ShapeType.TRIANGLE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("upArrow", (int)ShapeType.UP_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("upArrowCallout", (int)ShapeType.UP_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("upDownArrow", (int)ShapeType.UP_DOWN_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("upDownArrowCallout", (int)ShapeType.UP_DOWN_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("verticalScroll", (int)ShapeType.VERTICAL_SCROLL);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("wave", (int)ShapeType.WAVE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("wedgeEllipseCallout", (int)ShapeType.WEDGE_ELLIPSE_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("wedgeRectCallout", (int)ShapeType.WEDGE_RECT_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("wedgeRoundRectCallout", (int)ShapeType.WEDGE_R_RECT_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("roundRect", (int)ShapeType.ROUND_RECTANGLE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("rtTriangle", (int)ShapeType.RIGHT_TRIANGLE);

        // MS Word writes fallback as a custom shape for these preset geometries.
        G_PRESET_TO_SHAPE_TYPE_MAP.add("quadArrowCallout", (int)ShapeType.QUAD_ARROW_CALLOUT);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("quadArrow", (int)ShapeType.QUAD_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftUpArrow", (int)ShapeType.LEFT_UP_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bentArrow", (int)ShapeType.BENT_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("bentUpArrow", (int)ShapeType.BENT_UP_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star5", (int)ShapeType.STAR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("uturnArrow", (int)ShapeType.UTURN_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("trapezoid", (int)ShapeType.TRAPEZOID);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftRightUpArrow", (int)ShapeType.LEFT_RIGHT_UP_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("heart", (int)ShapeType.HEART);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("circularArrow", (int)ShapeType.CIRCULAR_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("blockArc", (int)ShapeType.BLOCK_ARC);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("arc", (int)ShapeType.ARC);

        // Actually geometries below have these mapping only for DML shapes. VML shapes have "NonPrimitive"
        // shapes types for geometries below.
        G_PRESET_TO_SHAPE_TYPE_MAP.add("heptagon", (int)ShapeType.HEPTAGON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("cloud", (int)ShapeType.CLOUD);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star6", (int)ShapeType.SEAL_6);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star7", (int)ShapeType.SEAL_7);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star10", (int)ShapeType.SEAL_10);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star12", (int)ShapeType.SEAL_12);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("star24", (int)ShapeType.SEAL_24);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("swooshArrow", (int)ShapeType.SWOOSH_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("teardrop", (int)ShapeType.TEARDROP);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("squareTabs", (int)ShapeType.SQUARE_TABS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("plaqueTabs", (int)ShapeType.PLAQUE_TABS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("pie", (int)ShapeType.PIE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("pieWedge", (int)ShapeType.WEDGE_PIE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("lineInv", (int)ShapeType.INVERSE_LINE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("mathDivide", (int)ShapeType.MATH_DIVIDE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("mathEqual", (int)ShapeType.MATH_EQUAL);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("mathMinus", (int)ShapeType.MATH_MINUS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("mathMultiply", (int)ShapeType.MATH_MULTIPLY);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("mathNotEqual", (int)ShapeType.MATH_NOT_EQUAL);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("mathPlus", (int)ShapeType.MATH_PLUS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("nonIsoscelesTrapezoid", (int)ShapeType.NON_ISOSCELES_TRAPEZOID);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftRightCircularArrow", (int)ShapeType.LEFT_RIGHT_CIRCULAR_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftRightRibbon", (int)ShapeType.LEFT_RIGHT_RIBBON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("leftCircularArrow", (int)ShapeType.LEFT_CIRCULAR_ARROW);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("frame", (int)ShapeType.FRAME);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("funnel", (int)ShapeType.FUNNEL);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("gear6", (int)ShapeType.GEAR_6);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("gear9", (int)ShapeType.GEAR_9);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("halfFrame", (int)ShapeType.HALF_FRAME);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("decagon", (int)ShapeType.DECAGON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("diagStripe", (int)ShapeType.DIAGONAL_STRIPE);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("dodecagon", (int)ShapeType.DODECAGON);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("corner", (int)ShapeType.CORNER);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("cornerTabs", (int)ShapeType.CORNER_TABS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("chord", (int)ShapeType.CHORD);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("chartPlus", (int)ShapeType.CHART_PLUS);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("chartStar", (int)ShapeType.CHART_STAR);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("chartX", (int)ShapeType.CHART_X);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("snip1Rect", (int)ShapeType.SINGLE_CORNER_SNIPPED);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("snip2DiagRect", (int)ShapeType.DIAGONAL_CORNERS_SNIPPED);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("snip2SameRect", (int)ShapeType.TOP_CORNERS_SNIPPED);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("snipRoundRect", (int)ShapeType.TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("round1Rect", (int)ShapeType.SINGLE_CORNER_ROUNDED);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("round2DiagRect", (int)ShapeType.DIAGONAL_CORNERS_ROUNDED);
        G_PRESET_TO_SHAPE_TYPE_MAP.add("round2SameRect", (int)ShapeType.TOP_CORNERS_ROUNDED);

        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_BORDER_CALLOUT_1, "accentBorderCallout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_BORDER_CALLOUT_2, "accentBorderCallout2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_BORDER_CALLOUT_3, "accentBorderCallout3");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_CALLOUT_1, "accentCallout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_CALLOUT_2, "accentCallout2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_CALLOUT_3, "accentCallout3");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_BACK_PREVIOUS, "actionButtonBackPrevious");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_BEGINNING, "actionButtonBeginning");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_BLANK, "actionButtonBlank");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_DOCUMENT, "actionButtonDocument");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_END, "actionButtonEnd");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_FORWARD_NEXT, "actionButtonForwardNext");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_HELP, "actionButtonHelp");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_HOME, "actionButtonHome");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_INFORMATION, "actionButtonInformation");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_MOVIE, "actionButtonMovie");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_RETURN, "actionButtonReturn");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACTION_BUTTON_SOUND, "actionButtonSound");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ARC, "arc");
        // rightArrow preset name are same for ThickArrow and Arrow.
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.THICK_ARROW, "rightArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ARROW, "rightArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BENT_ARROW, "bentArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BENT_CONNECTOR_2, "bentConnector2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BENT_CONNECTOR_3, "bentConnector3");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BENT_CONNECTOR_4, "bentConnector4");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BENT_CONNECTOR_5, "bentConnector5");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BENT_UP_ARROW, "bentUpArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BEVEL, "bevel");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BLOCK_ARC, "blockArc");
        // borderCallout1 preset name are same for BorderCallout1, BorderCallout90 and AccentBorderCallout90.
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BORDER_CALLOUT_1, "borderCallout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BORDER_CALLOUT_90, "borderCallout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_BORDER_CALLOUT_90, "borderCallout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BORDER_CALLOUT_2, "borderCallout2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BORDER_CALLOUT_3, "borderCallout3");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BRACE_PAIR, "bracePair");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BRACKET_PAIR, "bracketPair");
        // callout1 preset name are same for Callout1, AccentCallout90 and Callout90.
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CALLOUT_1, "callout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CALLOUT_90, "callout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ACCENT_CALLOUT_90, "callout1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CALLOUT_2, "callout2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CALLOUT_3, "callout3");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CAN, "can");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CHEVRON, "chevron");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CIRCULAR_ARROW, "circularArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CLOUD_CALLOUT, "cloudCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CUBE, "cube");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_CONNECTOR_2, "curvedConnector2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_CONNECTOR_3, "curvedConnector3");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_CONNECTOR_4, "curvedConnector4");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_CONNECTOR_5, "curvedConnector5");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_DOWN_ARROW, "curvedDownArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_LEFT_ARROW, "curvedLeftArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_RIGHT_ARROW, "curvedRightArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CURVED_UP_ARROW, "curvedUpArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DIAMOND, "diamond");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DONUT, "donut");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DOUBLE_WAVE, "doubleWave");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DOWN_ARROW, "downArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DOWN_ARROW_CALLOUT, "downArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ELLIPSE, "ellipse");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ELLIPSE_RIBBON, "ellipseRibbon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ELLIPSE_RIBBON_2, "ellipseRibbon2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_ALTERNATE_PROCESS, "flowChartAlternateProcess");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_COLLATE, "flowChartCollate");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_CONNECTOR, "flowChartConnector");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_DECISION, "flowChartDecision");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_DELAY, "flowChartDelay");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_DISPLAY, "flowChartDisplay");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_DOCUMENT, "flowChartDocument");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_EXTRACT, "flowChartExtract");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_INPUT_OUTPUT, "flowChartInputOutput");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_INTERNAL_STORAGE, "flowChartInternalStorage");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MAGNETIC_DISK, "flowChartMagneticDisk");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MAGNETIC_DRUM, "flowChartMagneticDrum");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MAGNETIC_TAPE, "flowChartMagneticTape");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MANUAL_INPUT, "flowChartManualInput");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MANUAL_OPERATION, "flowChartManualOperation");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MERGE, "flowChartMerge");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_MULTIDOCUMENT, "flowChartMultidocument");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_OFFLINE_STORAGE, "flowChartOfflineStorage");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_OFFPAGE_CONNECTOR, "flowChartOffpageConnector");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_ONLINE_STORAGE, "flowChartOnlineStorage");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_OR, "flowChartOr");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_PREDEFINED_PROCESS, "flowChartPredefinedProcess");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_PREPARATION, "flowChartPreparation");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_PROCESS, "flowChartProcess");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_PUNCHED_CARD, "flowChartPunchedCard");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_PUNCHED_TAPE, "flowChartPunchedTape");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_SORT, "flowChartSort");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_SUMMING_JUNCTION, "flowChartSummingJunction");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FLOW_CHART_TERMINATOR, "flowChartTerminator");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FOLDED_CORNER, "foldedCorner");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.HEART, "heart");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.HEXAGON, "hexagon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.HOME_PLATE, "homePlate");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.HORIZONTAL_SCROLL, "horizontalScroll");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.IRREGULAR_SEAL_1, "irregularSeal1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.IRREGULAR_SEAL_2, "irregularSeal2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_ARROW, "leftArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_ARROW_CALLOUT, "leftArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_BRACE, "leftBrace");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_BRACKET, "leftBracket");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_RIGHT_ARROW, "leftRightArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_RIGHT_ARROW_CALLOUT, "leftRightArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_RIGHT_UP_ARROW, "leftRightUpArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_UP_ARROW, "leftUpArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LIGHTNING_BOLT, "lightningBolt");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LINE, "line");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MOON, "moon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.NO_SMOKING, "noSmoking");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.NOTCHED_RIGHT_ARROW, "notchedRightArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.OCTAGON, "octagon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.PARALLELOGRAM, "parallelogram");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.PENTAGON, "pentagon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.PLAQUE, "plaque");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.PLUS, "plus");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.QUAD_ARROW, "quadArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.QUAD_ARROW_CALLOUT, "quadArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RECTANGLE, "rect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RIBBON, "ribbon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RIBBON_2, "ribbon2");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RIGHT_ARROW_CALLOUT, "rightArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RIGHT_BRACE, "rightBrace");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RIGHT_BRACKET, "rightBracket");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.ROUND_RECTANGLE, "roundRect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.RIGHT_TRIANGLE, "rtTriangle");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SMILEY_FACE, "smileyFace");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.STAR, "star5");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.STRAIGHT_CONNECTOR_1, "straightConnector1");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.STRIPED_RIGHT_ARROW, "stripedRightArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SUN, "sun");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL, "star16");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_4, "star4");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_8, "star8");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_16, "star16");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_24, "star24");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_32, "star32");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.TRAPEZOID, "trapezoid"); // In MSW it is custGeom that is why the angles are different.
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.TRIANGLE, "triangle");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.UP_ARROW, "upArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.UP_ARROW_CALLOUT, "upArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.UP_DOWN_ARROW, "upDownArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.UP_DOWN_ARROW_CALLOUT, "upDownArrowCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.UTURN_ARROW, "uturnArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.VERTICAL_SCROLL, "verticalScroll");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.WAVE, "wave");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.WEDGE_ELLIPSE_CALLOUT, "wedgeEllipseCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.WEDGE_RECT_CALLOUT, "wedgeRectCallout");
        // wedgeRoundRectCallout preset name are same for WedgeRRectCallout and Balloon.
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.WEDGE_R_RECT_CALLOUT, "wedgeRoundRectCallout");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.BALLOON, "wedgeRoundRectCallout");
        // The following types can not be set for VML shape. These shape types has
        // to be converted to "NonPrimitive" shape type with custom geometry.
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.HEPTAGON, "heptagon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CLOUD, "cloud");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_6, "star6");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_7, "star7");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_10, "star10");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SEAL_12, "star12");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SWOOSH_ARROW, "swooshArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.TEARDROP, "teardrop");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SQUARE_TABS, "squareTabs");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.PLAQUE_TABS, "plaqueTabs");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.PIE, "pie");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.WEDGE_PIE, "pieWedge");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.INVERSE_LINE, "lineInv");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MATH_DIVIDE, "mathDivide");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MATH_EQUAL, "mathEqual");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MATH_MINUS, "mathMinus");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MATH_MULTIPLY, "mathMultiply");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MATH_NOT_EQUAL, "mathNotEqual");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.MATH_PLUS, "mathPlus");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.NON_ISOSCELES_TRAPEZOID, "nonIsoscelesTrapezoid");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_RIGHT_CIRCULAR_ARROW, "leftRightCircularArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_RIGHT_RIBBON, "leftRightRibbon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.LEFT_CIRCULAR_ARROW, "leftCircularArrow");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FRAME, "frame");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.FUNNEL, "funnel");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.GEAR_6, "gear6");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.GEAR_9, "gear9");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.HALF_FRAME, "halfFrame");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DECAGON, "decagon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DIAGONAL_STRIPE, "diagStripe");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DODECAGON, "dodecagon");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CORNER, "corner");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CORNER_TABS, "cornerTabs");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CHORD, "chord");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CHART_PLUS, "chartPlus");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CHART_STAR, "chartStar");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.CHART_X, "chartX");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SINGLE_CORNER_SNIPPED, "snip1Rect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.TOP_CORNERS_SNIPPED, "snip2SameRect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DIAGONAL_CORNERS_SNIPPED, "snip2DiagRect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED, "snipRoundRect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.SINGLE_CORNER_ROUNDED, "round1Rect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.TOP_CORNERS_ROUNDED, "round2SameRect");
        G_SHAPE_TYPE_TO_PRESET_MAP.add((int)ShapeType.DIAGONAL_CORNERS_ROUNDED, "round2DiagRect");

        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_SIMPLE, (int)DmlTextShapeType.TEXT_PLAIN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_OCTAGON, (int)DmlTextShapeType.TEXT_STOP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_HEXAGON, (int)DmlTextShapeType.TEXT_TRIANGLE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CURVE, (int)DmlTextShapeType.TEXT_CASCADE_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_WAVE, (int)DmlTextShapeType.TEXT_WAVE_1);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_RING, (int)DmlTextShapeType.TEXT_ARCH_UP_POUR);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_ON_CURVE, (int)DmlTextShapeType.TEXT_CASCADE_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_ON_RING, (int)DmlTextShapeType.TEXT_ARCH_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_PLAIN_TEXT, (int)DmlTextShapeType.TEXT_PLAIN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_STOP, (int)DmlTextShapeType.TEXT_STOP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_TRIANGLE, (int)DmlTextShapeType.TEXT_TRIANGLE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_TRIANGLE_INVERTED, (int)DmlTextShapeType.TEXT_TRIANGLE_INVERTED);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CHEVRON, (int)DmlTextShapeType.TEXT_CHEVRON);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CHEVRON_INVERTED, (int)DmlTextShapeType.TEXT_CHEVRON_INVERTED);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_RING_INSIDE, (int)DmlTextShapeType.TEXT_RING_INSIDE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_RING_OUTSIDE, (int)DmlTextShapeType.TEXT_RING_OUTSIDE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_ARCH_UP_CURVE, (int)DmlTextShapeType.TEXT_ARCH_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_ARCH_DOWN_CURVE, (int)DmlTextShapeType.TEXT_ARCH_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CIRCLE_CURVE, (int)DmlTextShapeType.TEXT_CIRCLE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_BUTTON_CURVE, (int)DmlTextShapeType.TEXT_BUTTON);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_ARCH_UP_POUR, (int)DmlTextShapeType.TEXT_ARCH_UP_POUR);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_ARCH_DOWN_POUR, (int)DmlTextShapeType.TEXT_ARCH_DOWN_POUR);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CIRCLE_POUR, (int)DmlTextShapeType.TEXT_CIRCLE_POUR);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_BUTTON_POUR, (int)DmlTextShapeType.TEXT_BUTTON_POUR);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CURVE_UP, (int)DmlTextShapeType.TEXT_CURVE_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CURVE_DOWN, (int)DmlTextShapeType.TEXT_CURVE_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CASCADE_UP, (int)DmlTextShapeType.TEXT_CASCADE_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CASCADE_DOWN, (int)DmlTextShapeType.TEXT_CAN_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_WAVE_1, (int)DmlTextShapeType.TEXT_WAVE_1);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_WAVE_2, (int)DmlTextShapeType.TEXT_WAVE_2);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_WAVE_3, (int)DmlTextShapeType.TEXT_DOUBLE_WAVE_1);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_WAVE_4, (int)DmlTextShapeType.TEXT_WAVE_4);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_INFLATE, (int)DmlTextShapeType.TEXT_INFLATE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_DEFLATE, (int)DmlTextShapeType.TEXT_DEFLATE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_INFLATE_BOTTOM, (int)DmlTextShapeType.TEXT_INFLATE_BOTTOM);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_DEFLATE_BOTTOM, (int)DmlTextShapeType.TEXT_DEFLATE_BOTTOM);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_INFLATE_TOP, (int)DmlTextShapeType.TEXT_INFLATE_TOP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_DEFLATE_TOP, (int)DmlTextShapeType.TEXT_DEFLATE_TOP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_DEFLATE_INFLATE, (int)DmlTextShapeType.TEXT_DEFLATE_INFLATE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_DEFLATE_INFLATE_DEFLATE, (int)DmlTextShapeType.TEXT_DEFLATE_INFLATE_DEFLATE);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_FADE_RIGHT, (int)DmlTextShapeType.TEXT_FADE_RIGHT);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_FADE_LEFT, (int)DmlTextShapeType.TEXT_FADE_LEFT);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_FADE_UP, (int)DmlTextShapeType.TEXT_FADE_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_FADE_DOWN, (int)DmlTextShapeType.TEXT_FADE_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_SLANT_UP, (int)DmlTextShapeType.TEXT_SLANT_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_SLANT_DOWN, (int)DmlTextShapeType.TEXT_SLANT_DOWN);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CAN_UP, (int)DmlTextShapeType.TEXT_CAN_UP);
        G_SHAPE_TYPE_TO_PRESET_TEXT_WRAP_TYPE_MAP.add((int)ShapeType.TEXT_CAN_DOWN, (int)DmlTextShapeType.TEXT_CASCADE_DOWN);
    }
}

