// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2006 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.DrColor;


/// <summary>
/// Attributes of a shape.
/// </summary>
class ShapePr extends AttrCollection
{
    boolean getAllowInCell() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.ALLOW_IN_CELL); }

    boolean getBehindText() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.BEHIND_TEXT); }

    int getZOrder() { return (/*int*/Integer)fetchAttr(ShapeAttr.Z_ORDER); }

    /*WrapType*/int getWrapType() { return (/*WrapType*/Integer)fetchAttr(ShapeAttr.WRAP_TYPE); }

    /*WrapSide*/int getWrapSide() { return (/*WrapSide*/Integer)fetchAttr(ShapeAttr.WRAP_SIDE); }

    /*HorizontalAlignment*/int getHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)fetchAttr(ShapeAttr.HORIZONTAL_ALIGNMENT); }

    /*VerticalAlignment*/int getVerticalAlignment() { return (/*VerticalAlignment*/Integer)fetchAttr(ShapeAttr.VERTICAL_ALIGNMENT); }

    /*RelativeHorizontalPosition*/int getRelativeHorizontalPosition() { return (/*RelativeHorizontalPosition*/Integer)fetchAttr(ShapeAttr.RELATIVE_HORIZONTAL_POSITION); }

    /*RelativeVerticalPosition*/int getRelativeVerticalPosition() { return (/*RelativeVerticalPosition*/Integer)fetchAttr(ShapeAttr.RELATIVE_VERTICAL_POSITION); }

    double getLeft() { return (/*double*/Double)fetchAttr(ShapeAttr.LEFT); }

    double getTop() { return (/*double*/Double)fetchAttr(ShapeAttr.TOP); }

    boolean isInline() { return (getWrapType() == WrapType.INLINE); }

    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    static Object fetchDefaultAttr(int key)
    {
        return G_DEFAULTS.fetchAttr(key);
    }

    /// <summary>
    /// Stores default values for all shape attributes.
    /// </summary>
    private static /*final*/ ShapePr G_DEFAULTS;

    static final boolean BEHIND_TEXT_DEFAULT = false;
    static final boolean ANCHOR_LOCKED_DEFAULT = false;
    static final boolean ALLOW_IN_CELL_DEFAULT = true;
    static final boolean HIDDEN_DEFAULT = false;
    static final boolean ALLOW_OVERLAP_DEFAULT = true;

    static final int DML_EFFECT_EXTENT_LEFT_DEFAULT = 0;
    static final int DML_EFFECT_EXTENT_TOP_DEFAULT = 0;
    static final int DML_EFFECT_EXTENT_RIGHT_DEFAULT = 0;
    static final int DML_EFFECT_EXTENT_BOTTOM_DEFAULT = 0;

    /// <summary>
    /// In points.
    /// </summary>
    static final double GEO_TEXT_SIZE_DEFAULT = 36.0;

    static final int SHAPE_ID_DEFAULT = 0;
    static final String SHAPE_NAME_DEFAULT = "";

    static final /*HorizontalAlignment*/int HORIZONTAL_ALIGNMENT_DEFAULT = HorizontalAlignment.NONE;
    static final /*VerticalAlignment*/int VERTICAL_ALIGNMENT_DEFAULT = VerticalAlignment.NONE;
    static final /*RelativeHorizontalPosition*/int RELATIVE_HORIZONTAL_POSITION_DEFAULT = RelativeHorizontalPosition.DEFAULT;
    static final /*RelativeVerticalPosition*/int RELATIVE_VERTICAL_POSITION_DEFAULT = RelativeVerticalPosition.TEXT_FRAME_DEFAULT;

    static final /*WrapType*/int WRAP_TYPE_DEFAULT = WrapType.NONE;

    static final double LEFT_DEFAULT = 0.0;
    static final double TOP_DEFAULT = 0.0;
    static final double WIDTH_DEFAULT = 0.0;
    static final double HEIGHT_DEFAULT = 0.0;

    static final int LEFT_PERCENT_DEFAULT = 0;
    static final int TOP_PERCENT_DEFAULT = 0;
    static final int WIDTH_PERCENT_DEFAULT = 0;
    static final int HEIGHT_PERCENT_DEFAULT = 0;

    static/* ShapePr()*/
    {
        G_DEFAULTS = new ShapePr();

        // Transform properties
        G_DEFAULTS.add(ShapeAttr.TRANSFORM_ROTATION, 0);

        // Protection properties
        G_DEFAULTS.add(ShapeAttr.LOCK_ROTATION, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_ASPECT_RATIO, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_POSITION, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_AGAINST_SELECT, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_CROPPING, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_VERTICES, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_TEXT, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_ADJUST_HANDLES, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_AGAINST_GROUPING, false);

        // Text properties
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_TXID, 0);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_LEFT, ConvertUtilCore.pointToEmu(7.2)); // 1/10 inch
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_RIGHT, ConvertUtilCore.pointToEmu(7.2)); // 1/10 inch
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_TOP, ConvertUtilCore.pointToEmu(3.6)); // 1/20 inch
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_BOTTOM, ConvertUtilCore.pointToEmu(3.6)); // 1/20 inch
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_WRAP_MODE, TextBoxWrapMode.SQUARE);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_ZOOM, 0);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_ANCHOR, TextBoxAnchor.TOP);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_LAYOUT_FLOW, LayoutFlow.HORIZONTAL);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_FONT_ROTATION, FontRotation.RIGHT);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_NEXT_SHAPE_ID, 0);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_SELECT_TEXT, true);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_AUTO_TEXT_MARGIN, false);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_ROTATE_TEXT, false);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT, false);
        G_DEFAULTS.add(ShapeAttr.TEXTBOX_FIT_TEXT_TO_SHAPE, false);

        // GeoText (WordArt) properties.
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_TEXT, "");
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_RTF, "");
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_ALIGN, TextPathAlignment.CENTER);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_SIZE, ConvertUtilCore.doubleToFixed(GEO_TEXT_SIZE_DEFAULT));
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_SPACING, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_FONT, "Arial");    // MS Word says default is null, but I don't think its good.
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_REVERSE_ROWS, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_ON, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_VERTICAL, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_KERNING, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_TIGHT, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_STRETCH, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_SHRINK_FIT, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_BEST_FIT, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_NORMALIZE, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_DX_MEASURE, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_BOLD, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_ITALIC, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_UNDERLINE, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_SHADOW, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_SMALL_CAPS, false);
        G_DEFAULTS.add(ShapeAttr.GEO_TEXT_STRIKE_THROUGH, false);

        // Blip properties
        G_DEFAULTS.add(ShapeAttr.IMAGE_CROP_TOP, 0);
        G_DEFAULTS.add(ShapeAttr.IMAGE_CROP_BOTTOM, 0);
        G_DEFAULTS.add(ShapeAttr.IMAGE_CROP_LEFT, 0);
        G_DEFAULTS.add(ShapeAttr.IMAGE_CROP_RIGHT, 0);

        G_DEFAULTS.add(ShapeAttr.GRAPHIC_FRAME_EXT_HEIGHT, 0);
        G_DEFAULTS.add(ShapeAttr.GRAPHIC_FRAME_EXT_WIDTH, 0);

        G_DEFAULTS.add(ShapeAttr.FILL_RECT_BOTTOM, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_RECT_TOP, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_RECT_LEFT, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_RECT_RIGHT, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_BLIP_NAME, "");

        // No defaults for ImageBlipId, ImageName, ImageNameFlags.
        // According to the SPEC it should be 0xFFFFFFFF, but in Escher color top 8 bits are special
        // and we don't handle them yet, therefore let's make it just empty color for now.
        G_DEFAULTS.add(ShapeAttr.IMAGE_TRANSPARENT, DrColor.EMPTY);
        G_DEFAULTS.add(ShapeAttr.IMAGE_CONTRAST, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.IMAGE_BRIGHTNESS, 0);
        G_DEFAULTS.add(ShapeAttr.IMAGE_GAMMA, 0);
        // No default for OleTxid
        // No defaults for ImageDblCrMod, ImageFillCrMod, ImageLineCrMod
        // No defaults for ImagePrint, ImagePrintName, ImagePrintNameFlags
        G_DEFAULTS.add(ShapeAttr.IMAGE_NO_HIT_TEST, false);
        G_DEFAULTS.add(ShapeAttr.IMAGE_GRAY_SCALE, false);
        G_DEFAULTS.add(ShapeAttr.IMAGE_BI_LEVEL, false);
        G_DEFAULTS.add(ShapeAttr.IMAGE_ACTIVE, false);

        // Geometry properties.
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_VERTICES, null);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_SEGMENT_INFO, null);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_1, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_2, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_3, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_4, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_5, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_6, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_7, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_8, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_9, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_ADJUST_10, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_CONNECT_LOCS, null);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_CONNECT_ANGLES, null);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_X_LIMO, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_Y_LIMO, 0);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_HANDLES, null);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_FORMULAS, null);

        G_DEFAULTS.add(ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS, null);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_CONNECTION_SITE_TYPE, ConnectionSiteType.NONE);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_SHADOW_OK, true);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_THREE_DOK, true);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_LINE_OK, true);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_G_TEXT_OK, false);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_FILL_SHADE_SHAPE_OK, false);
        G_DEFAULTS.add(ShapeAttr.GEOMETRY_FILL_OK, true);

        // Fill properties.
        G_DEFAULTS.add(ShapeAttr.FILL_TYPE, FillTypeCore.SOLID);
        G_DEFAULTS.add(ShapeAttr.FILL_COLOR, DrColor.WHITE);
        G_DEFAULTS.add(ShapeAttr.FILL_OPACITY, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.FILL_BACK_COLOR, DrColor.WHITE);
        G_DEFAULTS.add(ShapeAttr.FILL_BACK_OPACITY, ConvertUtilCore.doubleToFixed(1.0));
        // No default for FillCrMod, FillBlipId, FillBlipName, FillBlipNameFlags.
        // No default for FillWidth, FillHeight
        G_DEFAULTS.add(ShapeAttr.FILL_ANGLE, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_FOCUS, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_TO_LEFT, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_TO_TOP, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_TO_RIGHT, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_TO_BOTTOM, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_DIMENSION_TYPE, FillDimensionType.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.FILL_SHADE_PRESET, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_SHADE_COLORS, null);
        G_DEFAULTS.add(ShapeAttr.FILL_ORIGIN_X, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_ORIGIN_Y, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_SHAPE_ORIGIN_X, 0);
        G_DEFAULTS.add(ShapeAttr.FILL_SHAPE_ORIGIN_Y, 0);
        // No default FillShadeType.
        G_DEFAULTS.add(ShapeAttr.FILL_RECOLOR_AS_PICTURE, false);
        G_DEFAULTS.add(ShapeAttr.FILL_USE_SHAPE_ANCHOR, false); // DB: Found experimentally.
        G_DEFAULTS.add(ShapeAttr.FILLED, true);
        G_DEFAULTS.add(ShapeAttr.FILL_HIT_TEST, true);
        G_DEFAULTS.add(ShapeAttr.FILL_SHAPE, true);
        G_DEFAULTS.add(ShapeAttr.FILL_USE_RECT, false);
        G_DEFAULTS.add(ShapeAttr.FILL_NO_FILL_HIT_TEST, false);

        // Stroke properties.
        G_DEFAULTS.add(ShapeAttr.LINE_COLOR, DrColor.BLACK);
        G_DEFAULTS.add(ShapeAttr.LINE_OPACITY, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.LINE_BACK_COLOR, DrColor.WHITE);
        G_DEFAULTS.add(ShapeAttr.LINE_FILL_TYPE, LineFillType.SOLID);
        // No default for LineFillBlipId, LineFillBlipName, LineFillBlipNameFlags.
        // No default for LineFillWidth, LineFillHeight
        G_DEFAULTS.add(ShapeAttr.LINE_FILL_DIMENSION_TYPE, FillDimensionType.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.LINE_WIDTH, 9525);
        // No default for LineMiterLimit.
        G_DEFAULTS.add(ShapeAttr.LINE_STYLE, ShapeLineStyle.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.LINE_DASH_STYLE, DashStyle.SOLID);
        G_DEFAULTS.add(ShapeAttr.LINE_DASH_DATA, null);
        G_DEFAULTS.add(ShapeAttr.LINE_START_ARROW, ArrowType.NONE);
        G_DEFAULTS.add(ShapeAttr.LINE_END_ARROW, ArrowType.NONE);
        G_DEFAULTS.add(ShapeAttr.LINE_START_ARROW_WIDTH, ArrowWidth.MEDIUM);
        G_DEFAULTS.add(ShapeAttr.LINE_START_ARROW_LENGTH, ArrowLength.MEDIUM);
        G_DEFAULTS.add(ShapeAttr.LINE_END_ARROW_WIDTH, ArrowWidth.MEDIUM);
        G_DEFAULTS.add(ShapeAttr.LINE_END_ARROW_LENGTH, ArrowLength.MEDIUM);
        G_DEFAULTS.add(ShapeAttr.LINE_JOIN_STYLE, JoinStyle.ROUND);
        G_DEFAULTS.add(ShapeAttr.LINE_END_CAP_STYLE, EndCap.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.LINE_ARROW_HEADS_OK, false);
        G_DEFAULTS.add(ShapeAttr.LINE_ON, true);
        G_DEFAULTS.add(ShapeAttr.LINE_HIT_TEST, true);
        G_DEFAULTS.add(ShapeAttr.LINE_FILL_SHAPE, true);
        G_DEFAULTS.add(ShapeAttr.LINE_NO_LINE_DRAW_DASH, false);
        G_DEFAULTS.add(ShapeAttr.LINE_INSET_PEN, false);

        // Shadow properties
        G_DEFAULTS.add(ShapeAttr.SHADOW_TYPE, ShadowTypeCore.OFFSET);
        G_DEFAULTS.add(ShapeAttr.SHADOW_COLOR, new DrColor(0x80, 0x80, 0x80));
        G_DEFAULTS.add(ShapeAttr.SHADOW_HIGHLIGHT, new DrColor(0xcb, 0xcb, 0xcb));
        // No default for ShadowCrMod.
        G_DEFAULTS.add(ShapeAttr.SHADOW_OPACITY, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.SHADOW_OFFSET_X, 25400);
        G_DEFAULTS.add(ShapeAttr.SHADOW_OFFSET_Y, 25400);
        G_DEFAULTS.add(ShapeAttr.SHADOW_SECOND_OFFSET_X, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_SECOND_OFFSET_Y, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_SCALE_XTO_X, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.SHADOW_SCALE_YTO_X, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_SCALE_XTO_Y, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_SCALE_YTO_Y, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.SHADOW_PERSPECTIVE_X, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_PERSPECTIVE_Y, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_WEIGHT, 256);
        G_DEFAULTS.add(ShapeAttr.SHADOW_ORIGIN_X, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_ORIGIN_Y, 0);
        G_DEFAULTS.add(ShapeAttr.SHADOW_ON, false);
        G_DEFAULTS.add(ShapeAttr.SHADOW_OBSCURED, false);

        // Perspective properties
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_TYPE, PerspectiveType.SHAPE);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_OFFSET_X, 0);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_OFFSET_Y, 0);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_SCALE_X_TO_X, 65536);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_SCALE_Y_TO_X, 0);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_SCALE_X_TO_Y, 0);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_SCALE_Y_TO_Y, 65536);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_X, 0);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_Y, 0);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_WEIGHT, 256);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_ORIGIN_X, 32768);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_ORIGIN_Y, 32768);
        G_DEFAULTS.add(ShapeAttr.PERSPECTIVE_ON, false);

        // 3D object properties
        G_DEFAULTS.add(ShapeAttr.TD_SPECULAR_AMOUNT, 0);
        G_DEFAULTS.add(ShapeAttr.TD_DIFFUSE_AMOUNT, 65536);
        G_DEFAULTS.add(ShapeAttr.TD_SHININESS, 5);
        G_DEFAULTS.add(ShapeAttr.TD_EDGE_THICKNESS, 12700);
        G_DEFAULTS.add(ShapeAttr.TD_EXTRUDE_FORWARD, 0);
        G_DEFAULTS.add(ShapeAttr.TD_EXTRUDE_BACKWARD, 457200);
        // See "[MS-OI29500] 2.1.1799 Part 4 Section 14.2.2.11, extrusion (3D Extrusion)".
        // Word does not support the plane attribute.
        G_DEFAULTS.add(ShapeAttr.TD_EXTRUDE_PLANE, PlaneType.XY);
        // According to the spec the default is 0x100000F7, but we can't represent this in the model yet.
        G_DEFAULTS.add(ShapeAttr.TD_EXTRUSION_COLOR, DrColor.EMPTY);
        // According to the spec the default is 0x20000000, but we can't represent this in the model yet.
        G_DEFAULTS.add(ShapeAttr.TD_CR_MOD, DrColor.EMPTY);
        G_DEFAULTS.add(ShapeAttr.TD_ON, false);
        G_DEFAULTS.add(ShapeAttr.TD_METALLIC, false);
        G_DEFAULTS.add(ShapeAttr.TD_USE_EXTRUSION_COLOR, false);
        G_DEFAULTS.add(ShapeAttr.TD_LIGHT_FACE, true);

        // 3D style properties
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_ANGLE_Y, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_ANGLE_X, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_AXIS_X, 100);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_AXIS_Y, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_AXIS_Z, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_ANGLE, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_CENTER_X, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_CENTER_Y, 0);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_CENTER_Z, 0);
        G_DEFAULTS.add(ShapeAttr.TD_RENDER_MODE, ThreeDRenderMode.FULL_RENDER);
        G_DEFAULTS.add(ShapeAttr.TD_TOLERANCE, 30000);
        G_DEFAULTS.add(ShapeAttr.TD_VIEWPOINT_X, 1250000);
        G_DEFAULTS.add(ShapeAttr.TD_VIEWPOINT_Y, -1250000);
        G_DEFAULTS.add(ShapeAttr.TD_VIEWPOINT_Z, 9000000);
        G_DEFAULTS.add(ShapeAttr.TD_ORIGIN_X, 32768);
        G_DEFAULTS.add(ShapeAttr.TD_ORIGIN_Y, -32768);
        G_DEFAULTS.add(ShapeAttr.TD_SKEW_ANGLE, -8847360);
        G_DEFAULTS.add(ShapeAttr.TD_SKEW_AMOUNT, 50);
        G_DEFAULTS.add(ShapeAttr.TD_AMBIENT_INTENSITY, 20000);
        G_DEFAULTS.add(ShapeAttr.TD_KEY_X, 50000);
        G_DEFAULTS.add(ShapeAttr.TD_KEY_Y, 0);
        G_DEFAULTS.add(ShapeAttr.TD_KEY_Z, 10000);
        G_DEFAULTS.add(ShapeAttr.TD_KEY_INTENSITY, 38000);
        G_DEFAULTS.add(ShapeAttr.TD_FILL_X, -50000);
        G_DEFAULTS.add(ShapeAttr.TD_FILL_Y, 0);
        G_DEFAULTS.add(ShapeAttr.TD_FILL_Z, 10000);
        G_DEFAULTS.add(ShapeAttr.TD_FILL_INTENSITY, 38000);
        G_DEFAULTS.add(ShapeAttr.TD_CONSTRAIN_ROTATION, true);
        G_DEFAULTS.add(ShapeAttr.TD_ROTATION_CENTER_AUTO, false);
        G_DEFAULTS.add(ShapeAttr.TD_PARALLEL, true);
        G_DEFAULTS.add(ShapeAttr.TD_KEY_HARSH, true);
        G_DEFAULTS.add(ShapeAttr.TD_FILL_HARSH, false);

        // Shape properties (non group shape only)
        G_DEFAULTS.add(ShapeAttr.CONNECTOR_TYPE, ConnectorType.NONE);
        G_DEFAULTS.add(ShapeAttr.BW_MODE, BWMode.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.BW_PURE, BWMode.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.BW_NORMAL, BWMode.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_NODE_LAYOUT, DiagramNodeLayout.STANDARD);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_NODE_KIND, DiagramNodeKind.NODE);
        G_DEFAULTS.add(ShapeAttr.OLE_ICON, false);
        G_DEFAULTS.add(ShapeAttr.PREFER_RELATIVE_RESIZE, false);
        G_DEFAULTS.add(ShapeAttr.LOCK_SHAPE_TYPE, false);
        G_DEFAULTS.add(ShapeAttr.DELETE_ATTACHED_OBJECT, false);
        // No default for Background.

        // Callout properties
        G_DEFAULTS.add(ShapeAttr.CALLOUT_TYPE, CalloutType.TWO_SEGMENT);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_GAP, ConvertUtilCore.pointToEmu(6));    //1/12 inch.
        G_DEFAULTS.add(ShapeAttr.CALLOUT_ANGLE, CalloutAngle.ANY);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_DROP_TYPE, CalloutDropType.SPECIFIED);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_DROP_DISTANCE, ConvertUtilCore.pointToEmu(9));
        G_DEFAULTS.add(ShapeAttr.CALLOUT_LENGTH, 0);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_ON, false);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_ACCENT_BAR, false);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_TEXT_BORDER, true);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_MINUS_X, false);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_MINUS_Y, false);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_DROP_AUTO, false);
        G_DEFAULTS.add(ShapeAttr.CALLOUT_LENGTH_SPECIFIED, false);

        // Group shape properties (applicable to normal and group shapes).
        G_DEFAULTS.add(ShapeAttr.SHAPE_NAME, SHAPE_NAME_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.SHAPE_DESCRIPTION, "");
        G_DEFAULTS.add(ShapeAttr.SHAPE_TITLE, "");
        G_DEFAULTS.add(ShapeAttr.HYPERLINK_ADDRESS, "");
        G_DEFAULTS.add(ShapeAttr.WRAP_POLYGON_VERTICES, null);
        G_DEFAULTS.add(ShapeAttr.DISTANCE_LEFT, ConvertUtilCore.pointToEmu(9));    // 1/8inch
        G_DEFAULTS.add(ShapeAttr.DISTANCE_TOP, 0);
        G_DEFAULTS.add(ShapeAttr.DISTANCE_RIGHT, ConvertUtilCore.pointToEmu(9));    // 1/8inch
        G_DEFAULTS.add(ShapeAttr.DISTANCE_BOTTOM, 0);
        G_DEFAULTS.add(ShapeAttr.SCREEN_TIP, "");
        G_DEFAULTS.add(ShapeAttr.SCRIPT_TEXT, "");
        G_DEFAULTS.add(ShapeAttr.HORIZONTAL_ALIGNMENT, HORIZONTAL_ALIGNMENT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.VERTICAL_ALIGNMENT, VERTICAL_ALIGNMENT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.RELATIVE_HORIZONTAL_POSITION, RELATIVE_HORIZONTAL_POSITION_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.RELATIVE_VERTICAL_POSITION, RELATIVE_VERTICAL_POSITION_DEFAULT);
        // No default for BorderXXXColor
        G_DEFAULTS.add(ShapeAttr.HR_PCT, 1000);
        G_DEFAULTS.add(ShapeAttr.HR_ALIGN, HorizontalRuleAlignment.LEFT);
        // No default for HRHeight and HRWidth because we don't have them in the model.
        G_DEFAULTS.add(ShapeAttr.SCRIPT_TYPE, "");
        G_DEFAULTS.add(ShapeAttr.ALLOW_IN_CELL, ALLOW_IN_CELL_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.PICTURE_BULLET, false);
        G_DEFAULTS.add(ShapeAttr.HR_STANDARD, false);
        G_DEFAULTS.add(ShapeAttr.HR_NO_SHADE, false);
        G_DEFAULTS.add(ShapeAttr.HR_ON, false);
        G_DEFAULTS.add(ShapeAttr.ALLOW_OVERLAP, ALLOW_OVERLAP_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.REALLY_HIDDEN, false);
        G_DEFAULTS.add(ShapeAttr.SCRIPT_ANCHOR, false);
        G_DEFAULTS.add(ShapeAttr.EDITED_WRAP, false);
        G_DEFAULTS.add(ShapeAttr.BEHIND_TEXT, BEHIND_TEXT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.ON_DBL_CLICK_NOTIFY, false);
        G_DEFAULTS.add(ShapeAttr.BUTTON, false);
        G_DEFAULTS.add(ShapeAttr.ONE_D, false);
        G_DEFAULTS.add(ShapeAttr.HIDDEN, HIDDEN_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.PRINT, true);

        // Diagram attributes.
        G_DEFAULTS.add(ShapeAttr.EDIT_AS, EditAs.GROUP);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_STYLE, 0);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_RELATIONS_TABLE, null);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_SCALE_X, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_SCALE_Y, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_FONT_SIZE, 12);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_CONSTRAIN_BOUNDS, null);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_BASE_TEXT_SCALE, ConvertUtilCore.doubleToFixed(1.0));
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_AUTO_FORMAT, false);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_REVERSE, false);
        G_DEFAULTS.add(ShapeAttr.DIAGRAM_AUTO_LAYOUT, false);
        G_DEFAULTS.add(ShapeAttr.PSEUDO_INLINE, false);

        // Attributes that are not like normal properties in Escher.
        G_DEFAULTS.add(ShapeAttr.FLIP, FlipOrientation.NONE);
        G_DEFAULTS.add(ShapeAttr.WRAP_TYPE, WRAP_TYPE_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.WRAP_SIDE, WrapSide.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.ANCHOR_LOCKED, ANCHOR_LOCKED_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.COORD_ORIGIN_X, 0);
        G_DEFAULTS.add(ShapeAttr.COORD_ORIGIN_Y, 0);
        // This is the default value for coord size for all shapes except group. For group it is 1000.
        // I think it is okay to have only one default value here because all requests for coord size
        // when it is missing, will actually be satisfied from the shape definitions resource.
        G_DEFAULTS.add(ShapeAttr.COORD_SIZE_WIDTH, 21600);
        G_DEFAULTS.add(ShapeAttr.COORD_SIZE_HEIGHT, 21600);
        G_DEFAULTS.add(ShapeAttr.IMAGE_BYTES, null);
        G_DEFAULTS.add(ShapeAttr.IMAGE_TITLE, "");
        G_DEFAULTS.add(ShapeAttr.IMAGE_SOURCE_FULL_NAME, "");
        G_DEFAULTS.add(ShapeAttr.BORDER_TOP, new Border());
        G_DEFAULTS.add(ShapeAttr.BORDER_LEFT, new Border());
        G_DEFAULTS.add(ShapeAttr.BORDER_BOTTOM, new Border());
        G_DEFAULTS.add(ShapeAttr.BORDER_RIGHT, new Border());
        G_DEFAULTS.add(ShapeAttr.LINE_IMAGE_BYTES, null);
        G_DEFAULTS.add(ShapeAttr.FILL_IMAGE_BYTES, null);
        G_DEFAULTS.add(ShapeAttr.OLE_OBJECT, null);
        G_DEFAULTS.add(ShapeAttr.OLE_PROG_ID, "");
        G_DEFAULTS.add(ShapeAttr.OLE_SOURCE_FULL_NAME, "");
        G_DEFAULTS.add(ShapeAttr.OLE_SOURCE_ITEM, "");
        G_DEFAULTS.add(ShapeAttr.OLE_AUTO_UPDATE, false);
        G_DEFAULTS.add(ShapeAttr.OLE_LOCKED, false);
        G_DEFAULTS.add(ShapeAttr.OLE_LINK_TYPE, OleLinkType.PICTURE);
        G_DEFAULTS.add(ShapeAttr.OLE_FORMAT_UPDATE_TYPE, 0);
        G_DEFAULTS.add(ShapeAttr.HYPERLINK_TARGET, "");
        G_DEFAULTS.add(ShapeAttr.LINE_FILL_PRESET_TEXTURE, PresetTextureCore.CUSTOM);
        G_DEFAULTS.add(ShapeAttr.FILL_PRESET_TEXTURE, PresetTextureCore.CUSTOM);
        G_DEFAULTS.add(ShapeAttr.SHAPE_ID, SHAPE_ID_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.LEFT, LEFT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.TOP, TOP_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.WIDTH, WIDTH_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.HEIGHT, HEIGHT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.LEFT_PERCENT, LEFT_PERCENT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.TOP_PERCENT, TOP_PERCENT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.WIDTH_PERCENT, WIDTH_PERCENT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.HEIGHT_PERCENT, HEIGHT_PERCENT_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.RELATIVE_WIDTH, RelativeHorizontalSize.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.RELATIVE_HEIGHT, RelativeVerticalSize.DEFAULT);
        G_DEFAULTS.add(ShapeAttr.Z_ORDER, 0);
        G_DEFAULTS.add(ShapeAttr.SHAPE_TYPE, ShapeType.NON_PRIMITIVE);
        G_DEFAULTS.add(ShapeAttr.INK_ANNOTATION, false);
        G_DEFAULTS.add(ShapeAttr.DML_EFFECT_EXTENT_BOTTOM, 0);
        G_DEFAULTS.add(ShapeAttr.DML_EFFECT_EXTENT_LEFT, 0);
        G_DEFAULTS.add(ShapeAttr.DML_EFFECT_EXTENT_RIGHT, 0);
        G_DEFAULTS.add(ShapeAttr.DML_EFFECT_EXTENT_TOP, 0);


        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_ADDL_XML, null);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_ALLOW_COMMENTS, false);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_ID, null);
        G_DEFAULTS.add(ShapeAttr.IS_SIGNATURE_LINE, false);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_PROV_ID, null);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE, true);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_SIGN_INST, null);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_SIGN_INST_SET, false);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_PROV_URL, null);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_SUGG_SIGNER, null);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_SUGG_SIGNER_2, null);
        G_DEFAULTS.add(ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL, null);

        G_DEFAULTS.add(ShapeAttr.SYS_LAYOUT_WIDTH, WIDTH_DEFAULT);
        G_DEFAULTS.add(ShapeAttr.SYS_LAYOUT_HEIGHT, HEIGHT_DEFAULT);
    }
}

