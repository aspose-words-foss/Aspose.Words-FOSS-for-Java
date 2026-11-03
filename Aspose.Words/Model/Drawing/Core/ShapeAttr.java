// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Defines shape attributes that occur in the model.
/// For more info see VML, OOXML and Word 2007 Drawing Format specifications.
/// </summary>
/*static*/ class ShapeAttr
{
	/* Simulation of static class by using private constructor */
	private ShapeAttr()
	{}

    // 0x0000 - 0x003f
    //>>>>>>>> #region  Transform Attributes

    /// <summary>
    /// Fixed. Degrees. Default 0.
    /// Positive value corresponds to clockwise rotation angle.
    /// </summary>
    static final int TRANSFORM_ROTATION = 4;

    //<<<<<<<< #endregion 

    // 0x0040 - 0x007f
    //>>>>>>>> #region  Protection Attributes

    /// <summary>
    /// Default false.
    /// </summary>
    static final int LOCK_AGAINST_UNGROUPING = 118;
    /// <summary>
    /// No rotation. Default false.
    /// </summary>
    static final int LOCK_ROTATION = 119;
    /// <summary>
    /// Don't allow changes in aspect ratio. Default false.
    /// </summary>
    static final int LOCK_ASPECT_RATIO = 120;
    /// <summary>
    /// Don't allow the shape to be moved. Default false.
    /// </summary>
    static final int LOCK_POSITION = 121;
    /// <summary>
    /// Shape may not be selected. Default false.
    /// </summary>
    static final int LOCK_AGAINST_SELECT = 122;
    /// <summary>
    /// No cropping this shape. Default false.
    /// </summary>
    static final int LOCK_CROPPING = 123;
    /// <summary>
    /// Edit Points not allowed. Default false.
    /// </summary>
    static final int LOCK_VERTICES = 124;
    /// <summary>
    /// Do not edit text. Default false.
    /// </summary>
    static final int LOCK_TEXT = 125;
    /// <summary>
    /// Do not adjust. Default false.
    /// </summary>
    static final int LOCK_ADJUST_HANDLES = 126;
    /// <summary>
    /// Do not group this shape. Default false.
    /// </summary>
    static final int LOCK_AGAINST_GROUPING = 127;

    //<<<<<<<< #endregion 

    // 0x0080 - 0x00bf
    //>>>>>>>> #region  Textbox Attributes

    /// <summary>
    /// int. id for the textbox.
    ///
    /// Although this attribute will be present in the model during write to DOC,
    /// it does not have any meaning for the model.
    ///
    /// This is an index into plcfTxbxs, but need to deduct one and shift right by 16 bits.
    /// int txid = (int)((txbxIdx + 1) shl 16);
    ///
    /// The lower 16 bits indicate the index of the linked checkbox.
    ///
    /// This attribute is only used during reading and writing into binary DOC.
    /// There is a two-way link between shape and txbx in a DOC file and during read.
    ///
    /// Also see OleTxid. OleTxid is written instead of TextboxTxid for OLE objects.
    /// </summary>
    static final int TEXTBOX_TXID = 128;
    /// <summary>
    /// int. margins relative to shape's inscribed text rectangle (in EMUs)
    /// Default 1/10inch
    /// </summary>
    static final int TEXTBOX_LEFT = 129;
    /// <summary>
    /// int. margins relative to shape's inscribed text rectangle (in EMUs)
    /// Default 1/20inch
    /// </summary>
    static final int TEXTBOX_TOP = 130;
    /// <summary>
    /// int. margins relative to shape's inscribed text rectangle (in EMUs)
    /// Default 1/10inch
    /// </summary>
    static final int TEXTBOX_RIGHT = 131;
    /// <summary>
    /// int. margins relative to shape's inscribed text rectangle (in EMUs)
    /// Default 1/20inch
    /// </summary>
    static final int TEXTBOX_BOTTOM = 132;
    /// <summary>
    /// <see cref="Aspose.Words.Drawing.TextBoxWrapMode"/>. Specifies how text wraps inside the shape.
    /// </summary>
    static final int TEXTBOX_WRAP_MODE = 133;
    /// <summary>
    /// int. Text zoom/scale (used if fFitTextToShape), default 0.
    /// </summary>
    static final int TEXTBOX_ZOOM = 134;
    /// <summary>
    /// <see cref="TextBoxAnchor"/>. How to anchor the text. Default Top.
    /// </summary>
    static final int TEXTBOX_ANCHOR = 135;
    /// <summary>
    /// <see cref="LayoutFlow"/>. Default HorZ
    /// </summary>
    static final int TEXTBOX_LAYOUT_FLOW = 136;
    /// <summary>
    /// <see cref="FontRotation"/>. Font rotation. Default Right.
    /// </summary>
    static final int TEXTBOX_FONT_ROTATION = 137;
    /// <summary>
    /// int. ID of the next shape (used by Word for linked textboxes).
    /// </summary>
    static final int TEXTBOX_NEXT_SHAPE_ID = 138;
    /// <summary>
    /// int. Bi-Di Text direction. Default LTR.
    /// </summary>
    static final int TEXT_DIRECTION = 139;
    /// <summary>
    /// int. Seen in TestCommunity15706.doc, value 2.
    /// Untested.
    /// Default 1.
    /// Count of columns.
    /// Ver: XP
    /// </summary>
    static final int TEXTBOX_COLUMNS = 140;
    /// <summary>
    /// int. Seen BusinessBrochure.doc, value 73152.
    /// Untested.
    /// Default 91440.
    /// EMUs, column margin on both sides.
    /// Ver: XP
    /// </summary>
    static final int TEXTBOX_COLUMN_MARGIN = 141;
    /// <summary>
    /// string. Original ID of the next shape (used by Word for linked textboxes).
    /// It is a temporary attribute that should never appear in AW model after load.
    /// </summary>
    static final int SYS_TEXTBOX_NEXT_SHAPE_ID_RAW = 142;
    /// <summary>
    /// TRUE if single click selects text, FALSE if two clicks. Default true.
    /// </summary>
    static final int TEXTBOX_SELECT_TEXT = 187;
    /// <summary>
    /// use host's margin calculations. Default false.
    /// </summary>
    static final int TEXTBOX_AUTO_TEXT_MARGIN = 188;
    /// <summary>
    /// Rotate text with shape. Default false.
    /// </summary>
    static final int TEXTBOX_ROTATE_TEXT = 189;
    /// <summary>
    /// Size shape to fit text size. Default false.
    /// </summary>
    static final int TEXTBOX_FIT_SHAPE_TO_TEXT = 190;
    /// <summary>
    /// Size text to fit shape size. Default false.
    /// </summary>
    static final int TEXTBOX_FIT_TEXT_TO_SHAPE = 191;

    //<<<<<<<< #endregion 

    // 0x00c0 - 0x00ff
    //>>>>>>>> #region  GeoText (WordArt) Attributes

    /// <summary>
    /// UNICODE text string
    /// </summary>
    static final int GEO_TEXT_TEXT = 192;
    /// <summary>
    /// RTF text string
    /// </summary>
    static final int GEO_TEXT_RTF = 193;
    /// <summary>
    /// <see cref="TextPathAlignment"/>. alignment on curve
    /// </summary>
    static final int GEO_TEXT_ALIGN = 194;
    /// <summary>
    /// Fixed. Font size in points.
    /// </summary>
    static final int GEO_TEXT_SIZE = 195;
    /// <summary>
    /// Fixed. Adjust the spacing between characters (1.0 is normal).
    /// </summary>
    static final int GEO_TEXT_SPACING = 196;
    /// <summary>
    /// String. Font name.
    /// </summary>
    static final int GEO_TEXT_FONT = 197;
    /// <summary>
    /// Untested.
    /// Ver: 2000
    /// String. To preserve CSS font selectors.
    /// Default empty string.
    /// </summary>
    static final int GEO_TEXT_CSS_FONT = 198;
    /// <summary>
    /// bool. Reverse row order.
    /// By default multiple rows of text are laid out with the first at the top for horizontal
    /// text and with the first at the left for vertical text, this flag reverses that behavior
    /// (bottom to top or right to left).
    /// Default false.
    /// </summary>
    static final int GEO_TEXT_REVERSE_ROWS = 240;
    /// <summary>
    /// bool. True if the text effect properties (gtext*) are used.
    /// False if these properties are ignored.
    /// </summary>
    static final int GEO_TEXT_ON = 241;
    /// <summary>
    /// bool. Rotate characters
    /// </summary>
    static final int GEO_TEXT_VERTICAL = 242;
    /// <summary>
    /// bool. Kern characters
    /// </summary>
    static final int GEO_TEXT_KERNING = 243;
    /// <summary>
    /// bool. Tightening or tracking is on.
    /// </summary>
    static final int GEO_TEXT_TIGHT = 244;
    /// <summary>
    /// bool. Stretch to fit shape
    /// </summary>
    static final int GEO_TEXT_STRETCH = 245;
    /// <summary>
    /// bool. Char bounding box
    /// </summary>
    static final int GEO_TEXT_SHRINK_FIT = 246;
    /// <summary>
    /// bool. Scale text-on-path
    /// </summary>
    static final int GEO_TEXT_BEST_FIT = 247;
    /// <summary>
    /// bool. Stretch char height. Mall characters same height.
    /// </summary>
    static final int GEO_TEXT_NORMALIZE = 248;
    /// <summary>
    /// bool. Do not measure along path.
    /// </summary>
    static final int GEO_TEXT_DX_MEASURE  = 249;
    /// <summary>
    /// bool.
    /// </summary>
    static final int GEO_TEXT_BOLD = 250;
    /// <summary>
    /// bool.
    /// </summary>
    static final int GEO_TEXT_ITALIC = 251;
    /// <summary>
    /// bool.
    /// </summary>
    static final int GEO_TEXT_UNDERLINE = 252;
    /// <summary>
    /// bool.
    /// </summary>
    static final int GEO_TEXT_SHADOW = 253;
    /// <summary>
    /// bool.
    /// </summary>
    static final int GEO_TEXT_SMALL_CAPS = 254;
    /// <summary>
    /// bool.
    /// </summary>
    static final int GEO_TEXT_STRIKE_THROUGH = 255;

    //<<<<<<<< #endregion 

    // 0x0100 - 0x013f
    //>>>>>>>> #region  Image (Blip) Attributes

    /// <summary>
    /// Fixed. Fraction of the image width or height.
    /// </summary>
    static final int IMAGE_CROP_TOP = 256;
    static final int IMAGE_CROP_BOTTOM = 257;
    static final int IMAGE_CROP_LEFT = 258;
    static final int IMAGE_CROP_RIGHT = 259;

    /// <summary>
    /// Specifies the width of the extents rectangle in EMUs. This rectangle shall dictate the size
    /// of the object as displayed (the result of any scaling to the original object).
    /// </summary>
    static final int GRAPHIC_FRAME_EXT_WIDTH = 260;

    /// <summary>
    /// Specifies the length of the extents rectangle in EMUs. This rectangle shall dictate the size
    /// of the object as displayed (the result of any scaling to the original object).
    /// </summary>
    static final int GRAPHIC_FRAME_EXT_HEIGHT = 261;

    /// <summary>
    /// Color. transparent color.
    /// The default value for this property is 0xFFFFFFFF, but we don't yet support 0xff in the top byte
    /// that means "ignore this color".
    /// </summary>
    static final int IMAGE_TRANSPARENT = 263;
    /// <summary>
    /// Fixed. Contrast setting. Min value is 0, max value is 0x7fffffff, but it is not linear.
    /// There is no documentation for the algorithm, but I guess it in <see cref="ImageData.ContrastToPercent"/>.
    /// </summary>
    static final int IMAGE_CONTRAST = 264;
    /// <summary>
    /// Fixed. Brightness setting. Has to be a fixed value -0.5 to 0.5.
    /// </summary>
    static final int IMAGE_BRIGHTNESS = 265;
    /// <summary>
    /// Fixed. gamma. Have never seen it in MS Word.
    /// Actually, this is not even in the SPEC.
    /// </summary>
    static final int IMAGE_GAMMA = 266;
    /// <summary>
    /// Int. Host-defined ID for OLE objects. MS Word spec says this is "pictureId", but
    /// I've discovered this is actually TXID for floating OLE objects.
    /// This property is ignored during read and synthesized on write.
    /// Also see TextboxTxid.
    /// </summary>
    static final int OLE_TXID = 267;
    /// <summary>
    /// Int. This property specifies a shadow color for pictures with a double shadow
    /// used in black-and-white display mode.
    /// The SPEC says: This value SHOULD be ignored.
    /// The default value for this property is 0x100000F4, but we don't support color flags yet.
    /// Seen in TestShadow.doc. Seen 0x100000f4 in TestDefect1402.doc.
    /// MSOCLR type.
    /// </summary>
    static final int IMAGE_DBL_CR_MOD = 268;
    /// <summary>
    /// Int. This property specifies the fill color modification used in black-and-white display mode.
    /// Seen in TestDefect1402.doc.
    /// MSOCLR
    /// </summary>
    static final int IMAGE_FILL_CR_MOD = 269;
    /// <summary>
    /// Int. This property specifies the line color modification used in black-and-white display mode.
    /// Seen in TestDefect1402.doc.
    /// MSOCLR
    /// </summary>
    static final int IMAGE_LINE_CR_MOD = 270;
    /// <summary>
    /// This property specifies the transparent pixel extended color.
    /// </summary>
    static final int IMAGE_TRANSPARENT_EXT = 277;
    /// <summary>
    /// This property specifies the color modification of the transparent pixel extended color.
    /// </summary>
    static final int IMAGE_TRANSPARENT_EXT_MOD = 279;
    /// <summary>
    /// Int. This property specifies the color used to recolor the image.
    /// Seen 0xffffffff in TestDefect1402.doc
    /// Ver: XP
    /// </summary>
    static final int IMAGE_RECOLOR = 282;
    /// <summary>
    /// Int. This property specifies the extended color used to recolor the image.
    /// Seen 0xffffffff in TestDefect1402.doc
    /// </summary>
    static final int IMAGE_RECOLOR_EXT = 283;
    /// <summary>
    /// Int. MUST be 0xFFFFFFFF, and MUST be ignored.
    /// Seen 0xffffffff in TestDefect1402.doc
    /// </summary>
    static final int UNDOCUMENTED_284 = 284;
    /// <summary>
    /// Int. This property specifies the color modification of the extended color used to recolor the image.
    /// Seen 0x20000000 in TestDefect1402.doc
    /// </summary>
    static final int IMAGE_RECOLOR_EXT_CR_MOD = 285;
    /// <summary>
    /// Seen in 1402. MUST be zero, and MUST be ignored.
    /// Complex property, no data.
    /// </summary>
    static final int UNDOCUMENTED_286 = 286;
    /// <summary>
    /// Int. MUST be 0xFFFFFFFF, and MUST be ignored.
    /// Seen 0xffffffff in TestDefect1402.doc
    /// </summary>
    static final int UNDOCUMENTED_287 = 287;
    /// <summary>
    /// bool. Seen in TestDefect1402.doc in extended flags. Default false? 0x0040
    /// When doing a color modification to a picture, leave grays unmodified.
    /// Ver: XP
    /// </summary>
    static final int IMAGE_PRESERVE_GRAYS = 313;
    /// <summary>
    /// bool, Do not hit test the picture, default false. 0x0008
    /// </summary>
    static final int IMAGE_NO_HIT_TEST = 316;
    /// <summary>
    /// bool, grayscale display, default false. 0x0004
    /// </summary>
    static final int IMAGE_GRAY_SCALE = 317;
    /// <summary>
    /// bool, bi-level display, default false. 0x0002
    /// </summary>
    static final int IMAGE_BI_LEVEL = 318;
    /// <summary>
    /// bool, Server is active (OLE objects only), default false. 0x0001
    /// </summary>
    static final int IMAGE_ACTIVE = 319;

    //<<<<<<<< #endregion 


    // This property set specifies the structure of a shape, including its points and how those points
    // are connected. These properties also specify how the shape scales in size and how the user may
    // manipulate that scaling.
    // 0x0140 - 0x017f
    //>>>>>>>> #region  Geometry Attributes

    /// <summary>
    /// Array of <see cref="PathPoint"/>.
    /// Specifies the set of points that make up this shape.
    /// Corresponds to 2.3.6.7 pVertices_complex in the DOC SPEC.
    /// </summary>
    static final int GEOMETRY_VERTICES = 325;
    /// <summary>
    /// Array of <see cref="PathInfo"/>.
    /// Specifies how the vertices specified by the <see cref="GeometryVertices"/> property are connected.
    /// Corresponds to 2.3.6.8 pSegmentInfo in the DOC SPEC.
    /// This attribute MUST be present and be up to date with GeometryVertices.
    /// </summary>
    static final int GEOMETRY_SEGMENT_INFO = 326;
    /// <summary>
    /// Int. Adjustment values corresponding to the positions of the adjust handles of the shape.
    /// The number of values used and their allowable ranges vary from shape type to shape type.
    /// Default 0.
    /// </summary>
    static final int GEOMETRY_ADJUST_1 = 327;
    static final int GEOMETRY_ADJUST_2 = 328;
    static final int GEOMETRY_ADJUST_3 = 329;
    static final int GEOMETRY_ADJUST_4 = 330;
    static final int GEOMETRY_ADJUST_5 = 331;
    static final int GEOMETRY_ADJUST_6 = 332;
    static final int GEOMETRY_ADJUST_7 = 333;
    static final int GEOMETRY_ADJUST_8 = 334;
    static final int GEOMETRY_ADJUST_9 = 335;
    static final int GEOMETRY_ADJUST_10 = 336;
    /// <summary>
    /// Array of <see cref="PathPoint"/>. These are G unit values.
    /// Connector locations for shapes imported from clipart.
    /// </summary>
    static final int GEOMETRY_CONNECT_LOCS = 337;
    /// <summary>
    /// This property specifies an array of angles corresponding to the connection sites
    /// in the pConnectionSites_complex property that are used to determine the direction
    /// that a connector links to the corresponding connection site.
    ///
    /// Array of <see cref="int"/>. But these are actually Fixed values.
    /// </summary>
    static final int GEOMETRY_CONNECT_ANGLES = 338;
    /// <summary>
    /// int. A signed integer x coordinate in geometry space that specifies a value above which limousine
    /// scaling is used.
    /// The spec says default is 0x8000000, but we default to 0 at the moment!
    ///
    /// Limo stretches are points on a shape's edge that define where and how a shape may be stretched
    /// by a user in a graphical editor.
    ///
    /// 2.3.6.22 xLimo in the DOC SPEC
    /// </summary>
    static final int GEOMETRY_X_LIMO = 339;
    /// <summary>
    /// int. A signed integer y coordinate in geometry space that specifies a value above which limousine
    /// scaling is used.
    /// The spec says default is 0x8000000, but we default to 0 at the moment!
    /// 2.3.6.23 yLimo in the DOC SPEC.
    /// </summary>
    static final int GEOMETRY_Y_LIMO = 340;
    /// <summary>
    /// This property specifies an array of adjust handles which allow a user to manipulate the
    /// geometry of this shape.
    /// Array of <see cref="Handle"/>.
    /// </summary>
    static final int GEOMETRY_HANDLES = 341;
    /// <summary>
    /// This property specifies a set of formulas used to calculate values for use in defining
    /// the geometry of this shape.
    /// Array of <see cref="Formula"/>.
    /// </summary>
    static final int GEOMETRY_FORMULAS = 342;
    /// <summary>
    /// This property specifies an array of rectangles specifying how text should be inscribed
    /// within this shape.
    /// 2.3.6.28 pInscribe in the DOC SPEC.
    /// Array of <see cref="PathRectangle"/>. Corresponds to path.textboxrect in VML.
    /// </summary>
    static final int GEOMETRY_PATH_TEXT_BOX_RECTS = 343;
    /// <summary>
    /// This property specifies where connection points are on the shape.
    /// <see cref="ConnectionSiteType"/>. Corresponds to o:connecttype in WordML.
    /// </summary>
    static final int GEOMETRY_CONNECTION_SITE_TYPE = 344;
    /// <summary>
    /// A bit that specifies whether to display the shadow of this shape. The value SHOULD be ignored.
    /// Default true.
    /// </summary>
    static final int GEOMETRY_SHADOW_OK = 378;
    /// <summary>
    /// A bit that specifies whether to display the extrusion effect of this shape.
    /// Default true.
    /// </summary>
    static final int GEOMETRY_THREE_DOK = 379;
    /// <summary>
    /// A bit that specifies whether to display the line of this shape.
    /// Default true.
    /// </summary>
    static final int GEOMETRY_LINE_OK = 380;
    /// <summary>
    /// A bit that specifies whether to display the text aligned to this shape.
    /// Default false.
    /// </summary>
    static final int GEOMETRY_G_TEXT_OK = 381;
    /// <summary>
    /// A bit that specifies whether the fill is aligned to the shape. A value of 0x0 specifies that
    /// the fill is aligned to the bounding rectangle of the shape. A value of 0x1 specifies that
    /// the fill is aligned to the shape itself.
    /// Default false.
    /// </summary>
    static final int GEOMETRY_FILL_SHADE_SHAPE_OK = 382;
    /// <summary>
    /// A bit that specifies whether to display the fill of this shape.
    /// Default true.
    /// </summary>
    static final int GEOMETRY_FILL_OK = 383;

    //<<<<<<<< #endregion 


    // 0x0180 - 0x01bf
    //>>>>>>>> #region  Fill Attributes

    /// <summary>
    /// <see cref="FillType"/> enumeration. Type of fill. Default Solid.
    /// </summary>
    static final int FILL_TYPE = 384;
    /// <summary>
    /// Foreground fill color. Default white.
    /// </summary>
    static final int FILL_COLOR = 385;
    /// <summary>
    /// Fixed.
    /// </summary>
    static final int FILL_OPACITY = 386;
    /// <summary>
    /// Background color. Default white.
    /// Can have special values such as 0xeff00266 which means "fill lighten(102)",
    /// where I guess 0x66 means "102" and 0x00200 means "lighten".
    /// VA: VmlColor.ColorToVml decodes such values.
    /// </summary>
    static final int FILL_BACK_COLOR = 387;
    /// <summary>
    /// Fixed. Used only for gradient fills.
    /// </summary>
    static final int FILL_BACK_OPACITY = 388;
    /// <summary>
    /// Color. Modification for BW views? Undocumented. Have not seen in files.
    /// </summary>
    static final int FILL_CR_MOD = 389;
    /// <summary>
    /// string.
    /// </summary>
    static final int FILL_BLIP_NAME = 391;
    /// <summary>
    /// int.
    /// </summary>
    static final int FILL_BLIP_NAME_FLAGS = 392;
    /// <summary>
    /// int? Never seen in files.
    /// </summary>
    static final int FILL_WIDTH = 393;
    /// <summary>
    /// int? Never seen in files.
    /// </summary>
    static final int FILL_HEIGHT = 394;
    /// <summary>
    /// Fixed. Gradient angle in degrees.
    /// </summary>
    static final int FILL_ANGLE = 395;
    /// <summary>
    /// int. Linear gradient fill focus. Integer percent -100..100
    /// </summary>
    static final int FILL_FOCUS = 396;
    /// <summary>
    /// Fixed. Focus position. Fraction of the shape size.
    /// </summary>
    static final int FILL_TO_LEFT = 397;
    /// <summary>
    /// Fixed. Focus position. Fraction of the shape size.
    /// </summary>
    static final int FILL_TO_TOP = 398;
    /// <summary>
    /// Fixed. Focus position. Fraction of the shape size.
    /// </summary>
    static final int FILL_TO_RIGHT = 399;
    /// <summary>
    /// Fixed. Focus position. Fraction of the shape size.
    /// </summary>
    static final int FILL_TO_BOTTOM = 400;
    /// <summary>
    /// Fixed. Seen in TestDefect1402.doc
    /// </summary>
    static final int FILL_RECT_LEFT = 401;
    /// <summary>
    /// Fixed. Seen in TestDefect1402.doc
    /// </summary>
    static final int FILL_RECT_TOP = 402;
    /// <summary>
    /// Fixed. Seen in TestDefect1402.doc
    /// </summary>
    static final int FILL_RECT_RIGHT = 403;
    /// <summary>
    /// Fixed. Seen in TestDefect1402.doc
    /// </summary>
    static final int FILL_RECT_BOTTOM = 404;
    /// <summary>
    /// <see cref="FillDimensionType"/>. Specifies units for fill dimensions.
    /// </summary>
    static final int FILL_DIMENSION_TYPE = 405;
    /// <summary>
    /// int. Identifies one of the preset gradients. Exists in DOC and RTF only. WML and DOCX don't have it.
    /// MS Word figures this value out itself, therefore we ignore it. Does not occur in the model.
    /// </summary>
    static final int FILL_SHADE_PRESET = 406;
    /// <summary>
    /// Array of <see cref="GradientColor"/>.
    /// </summary>
    static final int FILL_SHADE_COLORS = 407;
    /// <summary>
    /// int. The units are according to FillDzType. Undocumented.
    /// </summary>
    static final int FILL_ORIGIN_X = 408;
    /// <summary>
    /// int. The units are according to FillDzType. Undocumented.
    /// </summary>
    static final int FILL_ORIGIN_Y = 409;
    /// <summary>
    /// int. The units are according to FillDzType. Undocumented.
    /// </summary>
    static final int FILL_SHAPE_ORIGIN_X = 410;
    /// <summary>
    /// int. The units are according to FillDzType. Undocumented.
    /// </summary>
    static final int FILL_SHAPE_ORIGIN_Y = 411;
    /// <summary>
    /// <see cref="GradientType"/>. Type of shading for gradient fill.
    /// </summary>
    static final int FILL_SHADE_TYPE = 412;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.7.33 fillColorExt.
    /// This property specifies the extended foreground color.
    /// </summary>
    /// <remarks>
    /// The RGB value of the base foreground color before modification.
    /// <para>In WordML this is the value inside rgb() brackets.</para>
    /// <para>ex: "fillcolor="#228bb5 [rgb(34,139,181) lighten(102)]"</para>
    /// </remarks>
    static final int FILL_COLOR_EXT = 414;
    /// <summary>
    /// Undocumented.
    /// Seen in TestGeometry.doc, value -1. In extended properties.
    /// Seein BusinessBrochureBrief.doc, value 0xff20.
    /// </summary>
    static final int FILL_COLOR_EXT_CMY = 415;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.7.35 fillColorExtMod.
    /// This property specifies the color modification of the extended foreground color.
    /// </summary>
    /// <remarks>
    /// Degree of the foreground color lightening or darkening.
    /// Calculated as (1 - MsoTintShade) * 255, where MsoTintShade is the shading double value between 0 and 1.
    /// <para>In WordML this is the value inside brackets with the modifier name (lighten or darken).</para>
    /// <para>ex1: "fillcolor="#228bb5 [rgb(34,139,181) lighten(102)]"</para>
    /// <para>ex2: "fillcolor="#228bb5 [rgb(34,139,181) darken(179)]"</para>
    /// </remarks>
    static final int FILL_COLOR_EXT_MOD = 416;
    /// <summary>
    /// Undocumented. Seen in TestGeometry.doc. Complex property. Seen empty value only. In extended properties.
    /// </summary>
    static final int UNDOCUMENTED_417 = 417;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.7.37 fillBackColorExt.
    /// This property specifies the extended background color.
    /// </summary>
    /// <remarks>
    /// Similar with <see cref="FillColorExt"/>.
    /// </remarks>
    static final int FILL_BACK_COLOR_EXT = 418;
    /// <summary>
    /// Undocumented. Seen in BusinessBrochure.doc, value 8. Simple.
    /// </summary>
    static final int FILL_BACK_COLOR_EXT_CMY = 419;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.7.39 fillBackColorExtMod.
    /// This property specifies the color modification of the extended background color.
    /// </summary>
    /// <remarks>
    /// Similar with <see cref="FillColorExtMod"/>.
    /// </remarks>
    static final int FILL_BACK_COLOR_EXT_MOD = 420;
    /// <summary>
    /// Undocumented. Seen in TestWatermark.doc.
    /// </summary>
    static final int UNDOCUMENTED_421 = 421;
    /// <summary>
    /// Undocumented. Seen in TestGeometry.doc, value -1. In extended properties.
    /// Seen in BusinessBrochure.doc, value 0.
    /// </summary>
    static final int FILL_COLOR_EXT_K = 422;
    /// <summary>
    /// Undocumented. Seen in BusinessBrochure.doc, value 0.
    /// </summary>
    static final int FILL_BACK_COLOR_EXT_K = 423;
    /// <summary>
    /// bool. Extended fill flag. Not exactly sure what it means.
    /// </summary>
    static final int FILL_RECOLOR_AS_PICTURE = 441;
    /// <summary>
    /// bool. Extended fill flag. Corresponds to 'Fill Effects | Gradient | Rotate fill effect with shape' setting in MS Word.
    /// </summary>
    static final int FILL_USE_SHAPE_ANCHOR = 442;
    /// <summary>
    /// bool. Is shape filled?
    /// </summary>
    static final int FILLED = 443;
    /// <summary>
    /// bool. undocumented.
    /// </summary>
    static final int FILL_HIT_TEST = 444;
    /// <summary>
    /// bool. undocumented.
    /// </summary>
    static final int FILL_SHAPE = 445;
    /// <summary>
    /// bool. undocumented.
    /// </summary>
    static final int FILL_USE_RECT = 446;
    /// <summary>
    /// bool. undocumented.
    /// Corresponds to o:detectmouseclick in WordML
    /// </summary>
    static final int FILL_NO_FILL_HIT_TEST = 447;

    //<<<<<<<< #endregion 


    // 0x01c0 - 0x01ff
    //>>>>>>>> #region  Line Attributes

    /// <summary>
    /// Color of line. Default is black.
    /// </summary>
    static final int LINE_COLOR = 448;
    /// <summary>
    /// Fixed. Line color opacity.
    /// </summary>
    static final int LINE_OPACITY = 449;
    /// <summary>
    /// Background color, default is white.
    /// </summary>
    static final int LINE_BACK_COLOR = 450;
    /// <summary>
    /// Color. Modification for BW Views. never seen.
    /// </summary>
    static final int LINE_CR_MOD = 451;
    /// <summary>
    /// <see cref="Core.LineFillType"/> enumeration. Type of line, default is solid.
    /// </summary>
    static final int LINE_FILL_TYPE = 452;
    /// <summary>
    /// String. Seen "" in TestDefect1402.doc. Never seen, unsupported at the moment.
    /// </summary>
    static final int LINE_FILL_BLIP_NAME = 454;
    /// <summary>
    /// Int. Seen 0 in TestDefect1402.doc.
    /// </summary>
    static final int LINE_FILL_BLIP_NAME_FLAGS = 455;
    /// <summary>
    /// Int? Seen 0 in TestDefect1402.doc.
    /// </summary>
    static final int LINE_FILL_WIDTH = 456;
    /// <summary>
    /// Int? Seen 0 in TestDefect1402.doc.
    /// </summary>
    static final int LINE_FILL_HEIGHT = 457;
    /// <summary>
    /// <see cref="FillDimensionType"/>. Specifies units for fill dimensions.
    /// </summary>
    static final int LINE_FILL_DIMENSION_TYPE = 458;
    /// <summary>
    /// EMUs, line width. 1pt == 12700 EMUs. Default 9525 (0.75pt). Stored as EMUs in the model.
    /// </summary>
    static final int LINE_WIDTH = 459;
    /// <summary>
    /// Fixed. ratio.
    /// </summary>
    static final int LINE_MITER_LIMIT = 460;
    /// <summary>
    /// <see cref="ShapeLineStyle"/> enumeration. Default single.
    /// </summary>
    static final int LINE_STYLE = 461;
    /// <summary>
    /// <see cref="DashStyle"/>. Default Solid. Can be overridden by LineDashStyle.
    /// </summary>
    static final int LINE_DASH_STYLE = 462;
    /// <summary>
    /// Complex property. An array that defines a custom dash pattern. Never seen, not supported at the moment.
    /// </summary>
    static final int LINE_DASH_DATA = 463;
    /// <summary>
    /// <see cref="ArrowType"/>. Arrow at start, default NoEnd
    /// </summary>
    static final int LINE_START_ARROW = 464;
    /// <summary>
    /// <see cref="ArrowType"/>. Arrow at end, default NoEnd
    /// </summary>
    static final int LINE_END_ARROW = 465;
    /// <summary>
    /// <see cref="ArrowWidth"/>.
    /// </summary>
    static final int LINE_START_ARROW_WIDTH = 466;
    /// <summary>
    /// <see cref="ArrowLength"/>.
    /// </summary>
    static final int LINE_START_ARROW_LENGTH = 467;
    /// <summary>
    /// <see cref="ArrowWidth"/>.
    /// </summary>
    static final int LINE_END_ARROW_WIDTH = 468;
    /// <summary>
    /// <see cref="ArrowLength"/>.
    /// </summary>
    static final int LINE_END_ARROW_LENGTH = 469;
    /// <summary>
    /// <see cref="JoinStyle"/>. Defines the join style of a polyline.
    /// </summary>
    static final int LINE_JOIN_STYLE = 470;
    /// <summary>
    /// <see cref="EndCap"/>. How to end lines.
    /// </summary>
    static final int LINE_END_CAP_STYLE = 471;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.8.28 lineColorExt.
    /// This property specifies the extended foreground color of the shape outline.
    /// </summary>
    /// <remarks>
    /// Similar with <see cref="FillColorExt"/>.
    /// </remarks>
    static final int LINE_COLOR_EXT = 473;
    /// <summary>
    /// Undocumented. Seen in TestLeorMeller.doc int 0xffffffff
    /// </summary>
    static final int LINE_COLOR_EXT_CMY = 474;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.8.30 lineColorExtMod.
    /// This property specifies the color modification of the outline extended foreground color.
    /// </summary>
    /// <remarks>
    /// Similar with <see cref="FillColorExtMod"/>.
    /// </remarks>
    static final int LINE_COLOR_EXT_MOD = 475;
    /// <summary>
    /// 2.3.8.31 reserved476. MUST be zero, and MUST be ignored.
    /// Undocumented. Seen in TestWatermark.doc. Complex property. Seen simple in arabic 4129.doc
    /// </summary>
    static final int UNDOCUMENTED_476 = 476;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.8.32 lineBackColorExt.
    /// This property specifies the extended background color of the shape outline.
    /// </summary>
    /// <remarks>
    /// Similar with <see cref="FillColorExt"/>.
    /// </remarks>
    static final int LINE_BACK_COLOR_EXT = 477;
    /// <summary>
    /// This is line background color in the "cmyk" notation.
    /// Seen in BusinessBrochureBrief.doc, 0x00feffff.
    /// In WordML this is v:stroke color2="#fffffe [rgb(255,255,254) cmyk(0,0,0,0)]"
    /// </summary>
    static final int LINE_BACK_COLOR_EXT_CMY = 478;
    /// <summary>
    /// SPEC: [MS-ODRAW]. 2.3.8.34 lineBackColorExtMod.
    /// This property specifies the color modification of the outline extended background color.
    /// </summary>
    /// <remarks>
    /// Similar with <see cref="FillColorExtMod"/>.
    /// </remarks>
    static final int LINE_BACK_COLOR_EXT_MOD = 479;
    /// <summary>
    /// Undocumented. Seen in TestWatermark.doc. Complex property, no data.
    /// </summary>
    static final int UNDOCUMENTED_480 = 480;
    /// <summary>
    /// Undocumented. Seen in TestLeorMeller.doc int 0xffffffff
    /// </summary>
    static final int LINE_COLOR_EXT_K = 481;
    /// <summary>
    /// Undocumented. Seen in BusinessBrochure.doc, value 0.
    /// </summary>
    static final int LINE_BACK_COLOR_EXT_K = 482;
    /// <summary>
    /// bool.
    /// </summary>
    static final int LINE_RECOLOR_FILL_AS_PICTURE = 503;
    /// <summary>
    /// bool.
    /// </summary>
    static final int LINE_USE_SHAPE_ANCHOR = 504;
    /// <summary>
    /// bool. True when Inset pen (the pen is aligned on the inside of the edge of the path).
    /// False when Center pen (line drawn at center of path stroke).
    /// If LineInsetPenOK is false, this value MUST be ignored.
    /// </summary>
    static final int LINE_INSET_PEN = 505;
    /// <summary>
    /// bool. Specifies whether insetting the pen is allowed.
    /// </summary>
    static final int LINE_INSET_PEN_OK = 506;
    /// <summary>
    /// bool. Allow arrowheads if prop. is set
    /// </summary>
    static final int LINE_ARROW_HEADS_OK = 507;
    /// <summary>
    /// bool. Any line? Probably Stroked in VML.
    /// </summary>
    static final int LINE_ON = 508;
    /// <summary>
    /// bool. Should we hit test lines? Undocumented.
    /// </summary>
    static final int LINE_HIT_TEST = 509;
    /// <summary>
    /// bool. Register pattern on shape. Undocumented.
    /// </summary>
    static final int LINE_FILL_SHAPE = 510;
    /// <summary>
    /// bool. Draw a dashed line if no line. Probably ForceDash in VML.
    /// </summary>
    static final int LINE_NO_LINE_DRAW_DASH = 511;

    //<<<<<<<< #endregion 


    // 0x0200 - 0x023f
    //>>>>>>>> #region  Shadow Attributes

    /// <summary>
    /// <see cref="ShadowType"/>.
    /// </summary>
    static final int SHADOW_TYPE = 512;
    /// <summary>
    /// Color. Foreground shadow color.
    /// </summary>
    static final int SHADOW_COLOR = 513;
    /// <summary>
    /// Color. Embossed color.
    /// </summary>
    static final int SHADOW_HIGHLIGHT = 514;
    /// <summary>
    /// Color. Modification for BW views. undocumented.
    /// </summary>
    static final int SHADOW_CR_MOD = 515;
    /// <summary>
    /// Fixed.
    /// </summary>
    static final int SHADOW_OPACITY = 516;
    /// <summary>
    /// int. Shadow offset. EMUs.
    /// </summary>
    static final int SHADOW_OFFSET_X = 517;
    /// <summary>
    /// int. Shadow offset. EMUs.
    /// </summary>
    static final int SHADOW_OFFSET_Y = 518;
    /// <summary>
    /// int. Double shadow offset. EMUs.
    /// </summary>
    static final int SHADOW_SECOND_OFFSET_X = 519;
    /// <summary>
    /// int. Double shadow offset. EMUs.
    /// </summary>
    static final int SHADOW_SECOND_OFFSET_Y = 520;
    /// <summary>
    /// Fixed. undocumented.
    /// </summary>
    static final int SHADOW_SCALE_XTO_X = 521;
    /// <summary>
    /// Fixed. undocumented.
    /// </summary>
    static final int SHADOW_SCALE_YTO_X = 522;
    /// <summary>
    /// Fixed. undocumented.
    /// </summary>
    static final int SHADOW_SCALE_XTO_Y = 523;
    /// <summary>
    /// Fixed. undocumented.
    /// </summary>
    static final int SHADOW_SCALE_YTO_Y = 524;
    /// <summary>
    /// Fixed. Ratio of weight.
    /// </summary>
    static final int SHADOW_PERSPECTIVE_X = 525;
    /// <summary>
    /// Fixed. Ratio of weight.
    /// </summary>
    static final int SHADOW_PERSPECTIVE_Y = 526;
    /// <summary>
    /// What units?
    /// </summary>
    static final int SHADOW_WEIGHT = 527;
    /// <summary>
    /// int? Undocumented.
    /// </summary>
    static final int SHADOW_ORIGIN_X = 528;
    /// <summary>
    /// int? Undocumented.
    /// </summary>
    static final int SHADOW_ORIGIN_Y = 529;
    /// <summary>
    /// Undocumented. Seen in TestLeorMeller.doc int 0xffffffff
    /// </summary>
    static final int SHADOW_COLOR_EXT = 530;
    /// <summary>
    /// Undocumented. Seen in TestLeorMeller.doc int 0xffffffff
    /// </summary>
    static final int SHADOW_COLOR_EXT_CMY = 531;
    /// <summary>
    /// Undocumented. Seen in TestLeorMeller.doc int 0x20000000
    /// </summary>
    static final int SHADOW_COLOR_EXT_MOD = 532;
    /// <summary>
    /// Undocumented. Seen in TestWatermark.doc. Complex property.
    /// </summary>
    static final int UNDOCUMENTED_533 = 533;
    /// <summary>
    /// This is shadow background color in the "cmyk" notation.
    /// Seen in BusinessBrochureBrief.doc, 0xdsd5db.
    /// In WordML this is color2="#dbd5d3 [rgb(219,213,211) cmyk(12.5,9.8,8.63,3.14)]"
    /// </summary>
    static final int SHADOW_HIGHLIGHT_EXT = 534;
    /// <summary>
    /// Undocumented. In extended properties.
    /// Seen in BusinessBrochure.doc, value 0x2c3241e8.
    /// </summary>
    static final int SHADOW_HIGHLIGHT_EXT_CMY = 535;
    /// <summary>
    /// Seen in TestDefect1402.doc. 0xffffffff
    /// </summary>
    static final int SHADOW_HIGHLIGHT_EXT_MOD = 536;
    /// <summary>
    /// Undocumented. Seen in TestWatermark.doc. Complex property.
    /// </summary>
    static final int UNDOCUMENTED_537 = 537;
    /// <summary>
    /// Undocumented. Seen in TestLeorMeller.doc int 0xffffffff
    /// </summary>
    static final int SHADOW_COLOR_EXT_K = 538;
    /// <summary>
    /// Undocumented. In extended properties.
    /// Seen in BusinessBrochure.doc, value 0x20.
    /// </summary>
    static final int SHADOW_HIGHLIGHT_EXT_K = 539;
    /// <summary>
    /// bool. Shadow is on or not.
    /// </summary>
    static final int SHADOW_ON = 574;
    /// <summary>
    /// bool. Excel5-style shadow
    /// </summary>
    static final int SHADOW_OBSCURED = 575;

    //<<<<<<<< #endregion 


    // 0x0240 - 0x027f
    //>>>>>>>> #region  Perspective Attributes

    /// <summary>
    /// <see cref="PerspectiveType"/>. Where transform applies.
    /// </summary>
    static final int PERSPECTIVE_TYPE = 576;
    /// <summary>
    /// int. The LONG values define a transformation matrix, effectively, each value is scaled by the perspectiveWeight parameter.
    /// </summary>
    static final int PERSPECTIVE_OFFSET_X = 577;
    /// <summary>
    /// int. The LONG values define a transformation matrix, effectively, each value is scaled by the perspectiveWeight parameter.
    /// </summary>
    static final int PERSPECTIVE_OFFSET_Y = 578;
    /// <summary>
    /// int. The LONG values define a transformation matrix, effectively, each value is scaled by the perspectiveWeight parameter.
    /// </summary>
    static final int PERSPECTIVE_SCALE_X_TO_X = 579;
    /// <summary>
    /// int. The LONG values define a transformation matrix, effectively, each value is scaled by the perspectiveWeight parameter.
    /// </summary>
    static final int PERSPECTIVE_SCALE_Y_TO_X = 580;
    /// <summary>
    /// int. The LONG values define a transformation matrix, effectively, each value is scaled by the perspectiveWeight parameter.
    /// </summary>
    static final int PERSPECTIVE_SCALE_X_TO_Y = 581;
    /// <summary>
    /// int. The LONG values define a transformation matrix, effectively, each value is scaled by the perspectiveWeight parameter.
    /// </summary>
    static final int PERSPECTIVE_SCALE_Y_TO_Y = 582;
    /// <summary>
    /// int. undocumented.
    /// </summary>
    static final int PERSPECTIVE_X = 583;
    /// <summary>
    /// int. undocumented.
    /// </summary>
    static final int PERSPECTIVE_Y = 584;
    /// <summary>
    /// Fixed? Scaling factor.
    /// </summary>
    static final int PERSPECTIVE_WEIGHT = 585;
    /// <summary>
    /// Fixed. undocumented.
    /// </summary>
    static final int PERSPECTIVE_ORIGIN_X = 586;
    /// <summary>
    /// Fixed undocumented.
    /// </summary>
    static final int PERSPECTIVE_ORIGIN_Y = 587;
    /// <summary>
    /// bool.
    /// </summary>
    static final int PERSPECTIVE_ON = 639;

    //<<<<<<<< #endregion 


    // 0x0280 - 0x02bf
    //>>>>>>>> #region  3D Object Attributes

    /// <summary>
    /// Fixed-point 16.16
    /// </summary>
    static final int TD_SPECULAR_AMOUNT = 640;
    /// <summary>
    /// Fixed-point 16.16
    /// </summary>
    static final int TD_DIFFUSE_AMOUNT = 641;
    /// <summary>
    /// int? Default gives OK results
    /// </summary>
    static final int TD_SHININESS = 642;
    /// <summary>
    /// int. Specular edge thickness in EMUs.
    /// </summary>
    static final int TD_EDGE_THICKNESS = 643;
    /// <summary>
    /// int. Distance of extrusion in EMUs
    /// </summary>
    static final int TD_EXTRUDE_FORWARD = 644;
    /// <summary>
    /// int. EMUs
    /// </summary>
    static final int TD_EXTRUDE_BACKWARD = 645;
    /// <summary>
    /// <see cref="PlaneType"/>. Extrusion direction
    /// </summary>
    static final int TD_EXTRUDE_PLANE = 646;
    /// <summary>
    /// Color. Basic color of extruded part of shape; the lighting model used will determine
    /// the exact shades used when rendering.
    /// </summary>
    static final int TD_EXTRUSION_COLOR = 647;
    /// <summary>
    /// Color. Modification for BW views
    /// </summary>
    static final int TD_CR_MOD = 648;
    /// <summary>
    /// Seen in TestDefect1402.doc, 0xffffffff
    /// </summary>
    static final int TD_EXTRUSION_COLOR_EXT = 649;
    /// <summary>
    /// Seen in TestDefect1402.doc, 0xffffffff
    /// </summary>
    static final int TD_EXTRUSION_COLOR_EXT_CMY = 650;
    /// <summary>
    /// Seen in TestDefect1402.doc, 0x20000000 (CrMod?)
    /// </summary>
    static final int TD_EXTRUSION_COLOR_EXT_MOD = 651;
    /// <summary>
    /// Undocumented. Seen in TestWatermark.doc. Complex property.
    /// </summary>
    static final int UNDOCUMENTED_652 = 652;
    /// <summary>
    /// Seen in TestDefect1402.doc, 0xffffffff
    /// </summary>
    static final int TD_EXTRUSION_COLOR_EXT_K = 653;
    /// <summary>
    /// Undocumented. Seen in TestJira18071.doc
    /// </summary>
    static final int UNDOCUMENTED_663 = 663;
    /// <summary>
    /// Undocumented. Seen in TestJira18071.doc
    /// </summary>
    static final int UNDOCUMENTED_666 = 666;
    /// <summary>
    /// bool. Does this shape have a 3D effect.
    /// </summary>
    static final int TD_ON = 700;
    /// <summary>
    /// bool. Use metallic specularity.
    /// </summary>
    static final int TD_METALLIC = 701;
    /// <summary>
    /// bool. Undocumented.
    /// </summary>
    static final int TD_USE_EXTRUSION_COLOR = 702;
    /// <summary>
    /// bool. Determines whether the front face of the extrusion will respond to changes in the lighting.
    /// </summary>
    static final int TD_LIGHT_FACE = 703;

    //<<<<<<<< #endregion 


    // 0x02c0 - 0x03ff
    //>>>>>>>> #region  3D Style Attributes

    /// <summary>
    /// Fixed. degrees (16.16) about y axis
    /// </summary>
    static final int TD_ROTATION_ANGLE_Y = 704;
    /// <summary>
    /// Fixed. degrees (16.16) about x axis
    /// </summary>
    static final int TD_ROTATION_ANGLE_X = 705;
    /// <summary>
    /// int. These specify the rotation axis; only their relative magnitudes matter.
    /// </summary>
    static final int TD_ROTATION_AXIS_X = 706;
    /// <summary>
    /// int. These specify the rotation axis; only their relative magnitudes matter.
    /// </summary>
    static final int TD_ROTATION_AXIS_Y = 707;
    /// <summary>
    /// int. These specify the rotation axis; only their relative magnitudes matter.
    /// </summary>
    static final int TD_ROTATION_AXIS_Z = 708;
    /// <summary>
    /// Fixed. degrees (16.16) about axis
    /// </summary>
    static final int TD_ROTATION_ANGLE = 709;
    /// <summary>
    /// Fixed. rotation center x (16.16 or g-units)
    /// </summary>
    static final int TD_ROTATION_CENTER_X = 710;
    /// <summary>
    /// Fixed. rotation center y (16.16 or g-units)
    /// </summary>
    static final int TD_ROTATION_CENTER_Y = 711;
    /// <summary>
    /// int. rotation center z (absolute (emus))
    /// </summary>
    static final int TD_ROTATION_CENTER_Z = 712;
    /// <summary>
    /// <see cref="ThreeDRenderMode"/>. Full, wireframe or bcube
    /// </summary>
    static final int TD_RENDER_MODE = 713;
    /// <summary>
    /// Fixed. pixels (16.16). Defines the number of facets used to describe curved surfaces of an extrusion.
    /// </summary>
    static final int TD_TOLERANCE = 714;
    /// <summary>
    /// Emus. Defines the viewpoint of the observer (X).
    /// </summary>
    static final int TD_VIEWPOINT_X = 715;
    /// <summary>
    /// Emus. Defines the viewpoint of the observer (Y).
    /// </summary>
    static final int TD_VIEWPOINT_Y = 716;
    /// <summary>
    /// Emus. Z view distance.
    /// </summary>
    static final int TD_VIEWPOINT_Z = 717;
    /// <summary>
    /// Fixed. Defines the origin X of the viewpoint within the bounding box of the shape.
    /// </summary>
    static final int TD_ORIGIN_X = 718;
    /// <summary>
    /// Fixed. Defines the origin Y of the viewpoint within the bounding box of the shape.
    /// </summary>
    static final int TD_ORIGIN_Y = 719;
    /// <summary>
    /// Fixed. degree (16.16) skew angle
    /// </summary>
    static final int TD_SKEW_ANGLE = 720;
    /// <summary>
    /// int. Percentage skew amount
    /// </summary>
    static final int TD_SKEW_AMOUNT = 721;
    /// <summary>
    /// Fixed. Fixed point intensity
    /// </summary>
    static final int TD_AMBIENT_INTENSITY = 722;
    /// <summary>
    /// Int? Key light source direction; only their relative magnitudes matter.
    /// </summary>
    static final int TD_KEY_X = 723;
    /// <summary>
    /// Int? Key light source direction; only their relative magnitudes matter.
    /// </summary>
    static final int TD_KEY_Y = 724;
    /// <summary>
    /// Int? Key light source direction; only their relative magnitudes matter.
    /// </summary>
    static final int TD_KEY_Z = 725;
    /// <summary>
    /// Int? Fixed point intensity.
    /// </summary>
    static final int TD_KEY_INTENSITY = 726;
    /// <summary>
    /// Int? Fill light source direction; only their relative magnitudes matter.
    /// </summary>
    static final int TD_FILL_X = 727;
    /// <summary>
    /// Int? Fill light source direction; only their relative magnitudes matter.
    /// </summary>
    static final int TD_FILL_Y = 728;
    /// <summary>
    /// Int? Fill light source direction; only their relative magnitudes matter.
    /// </summary>
    static final int TD_FILL_Z = 729;
    /// <summary>
    /// Int? Fixed point intensity
    /// </summary>
    static final int TD_FILL_INTENSITY = 730;
    /// <summary>
    /// bool. Determines whether the rotation of the extruded object is specified by the RotationAngle attribute.
    /// </summary>
    static final int TD_CONSTRAIN_ROTATION = 763;
    /// <summary>
    /// bool. Determines whether the center of rotation will be the geometric center of the extrusion.
    /// </summary>
    static final int TD_ROTATION_CENTER_AUTO = 764;
    /// <summary>
    /// bool. Parallel projection.
    /// </summary>
    static final int TD_PARALLEL = 765;
    /// <summary>
    /// bool. Is key lighting harsh.
    /// </summary>
    static final int TD_KEY_HARSH = 766;
    /// <summary>
    /// bool. Is fill lighting harsh.
    /// </summary>
    static final int TD_FILL_HARSH = 767;

    //<<<<<<<< #endregion 


    // 0x0300 - 0x033f
    //>>>>>>>> #region  Shape Attributes

    /// <summary>
    /// Test22389.doc
    /// </summary>
    static final int UNDOCUMENTED_770 = 770;

    /// <summary>
    /// <see cref="ConnectorType"/>. Default none.
    /// </summary>
    static final int CONNECTOR_TYPE = 771;
    /// <summary>
    /// <see cref="BWMode"/>. Default Automatic.
    /// </summary>
    static final int BW_MODE = 772;
    /// <summary>
    /// <see cref="BWMode"/>. Default Automatic.
    /// </summary>
    static final int BW_PURE = 773;
    /// <summary>
    /// <see cref="BWMode"/>. Default Automatic.
    /// </summary>
    static final int BW_NORMAL = 774;
    /// <summary>
    /// Test22389.doc
    /// </summary>
    static final int UNDOCUMENTED_775 = 775;
    /// <summary>
    /// <see cref="Core.DiagramNodeLayout"/>.
    /// </summary>
    static final int DIAGRAM_NODE_LAYOUT = 777;
    /// <summary>
    /// <see cref="Core.DiagramNodeKind"/>.
    /// This property specifies kind of node in a diagram.
    /// </summary>
    static final int DIAGRAM_NODE_KIND = 778;
    /// <summary>
    /// MSODGMLO type. Most recently used layout for its child.
    /// Ver: XP.
    /// </summary>
    static final int DIAGRAM_LAYOUT_MRU = 779;
    /// <summary>
    /// Untested.
    /// String.
    /// This property is present if the shape represents an equation generated by Office 2007
    /// or later.  The property is a string of XML representing a Word 2003 XML document.
    /// The original equation is stored within the “oMathPara” tag within the document.
    /// Refer to the Office Open XML documentation for details on this XML representation
    /// of equations. If the document containing the shape is opened in Office 2007 or later,
    /// the shape is replaced with the equation in this document.
    /// Ver: 2007
    /// </summary>
    static final int EQUATION_XML = 780;

    /// <summary>
    /// byte[].
    /// Specifies a base-64 encoded package as defined in ISO/IEC 29500-2 that contains
    /// DrawingML content as defined in ISO/IEC 29500-1. [Rationale: This attribute allows an
    /// application to use VML to represent graphical content for a legacy document while still
    /// persisting DrawingML for consuming applications that support DrawingML.For example,
    /// a diagram stored within this attribute would have the four parts defined for a DrawingML
    /// diagram, as well as any number of application-defined parts and relationships.end
    /// rationale].
    /// </summary>
    static final int GFX_DATA = 781;

    /// <summary>
    /// Untested.
    /// Bool. Default false. Ver: 2007.
    /// True if the shape is a policy label representing metadata about a document.
    /// </summary>
    static final int POLICY_LABEL = 822;
    /// <summary>
    /// Untested.
    /// Bool. Default false. Ver: 2007.
    /// True if the shape represents a barcode as part of a barcode policy for record management.
    /// </summary>
    static final int POLICY_BAR_CODE = 823;
    /// <summary>
    /// Untested.
    /// Bool. Default false. Ver: XP.
    /// The value of this property should match the value of the fFlipH property
    /// (in the transform property set) if the pib property exists (in the blip property set.)
    /// </summary>
    static final int FLIP_HQFE_5152 = 824;
    /// <summary>
    /// Untested.
    /// Bool. Default false. Ver: XP.
    /// The value of this property should match the value of the fFlipV property
    /// (in the transform property set) if the pib property exists (in the blip property set.)
    /// </summary>
    static final int FLIP_VQFE_5152 = 825;
    /// <summary>
    /// bool. For OLE objects, whether the object is in icon form.
    /// What is stored in DOC escher does not seem very reliable, so I confirm it by extracting
    /// a bit from the OLE object data. For DOCX and WordML this is DrawAspect.
    /// </summary>
    static final int OLE_ICON = 826;
    /// <summary>
    /// bool. For UI only. Prefer relative resizing.
    /// </summary>
    static final int PREFER_RELATIVE_RESIZE = 827;
    /// <summary>
    /// bool. Lock the shape type (don't allow Change Shape)
    /// </summary>
    static final int LOCK_SHAPE_TYPE = 828;
    /// <summary>
    /// Bool. Set by the solver.
    /// </summary>
    static final int INITIATOR = 829;
    /// <summary>
    /// bool. undocumented.
    /// </summary>
    static final int DELETE_ATTACHED_OBJECT = 830;

    //<<<<<<<< #endregion 


    // 0x0340 - 0x037f
    //>>>>>>>> #region  Callout Attributes

    /// <summary>
    /// <see cref="CalloutType"/>.
    /// </summary>
    static final int CALLOUT_TYPE = 832;
    /// <summary>
    /// Int. Distance from box to first point.(EMUs)
    /// </summary>
    static final int CALLOUT_GAP = 833;
    /// <summary>
    /// <see cref="CalloutAngle"/>.
    /// </summary>
    static final int CALLOUT_ANGLE = 834;
    /// <summary>
    /// <see cref="CalloutDropType"/>.
    /// </summary>
    static final int CALLOUT_DROP_TYPE = 835;
    /// <summary>
    /// Int. if CalloutDropType.Specified, the actual drop distance. EMUs?
    /// </summary>
    static final int CALLOUT_DROP_DISTANCE = 836;
    /// <summary>
    /// Int. if fCalloutLengthSpecified, the actual distance.
    /// </summary>
    static final int CALLOUT_LENGTH = 837;
    /// <summary>
    /// Bool. Is the shape a callout.
    /// </summary>
    static final int CALLOUT_ON = 889;
    /// <summary>
    /// Bool. does callout have accent bar.
    /// </summary>
    static final int CALLOUT_ACCENT_BAR = 890;
    /// <summary>
    /// Bool. does callout have a text border
    /// </summary>
    static final int CALLOUT_TEXT_BORDER = 891;
    /// <summary>
    /// Bool. undocumented.
    /// </summary>
    static final int CALLOUT_MINUS_X = 892;
    /// <summary>
    /// Bool. undocumented.
    /// </summary>
    static final int CALLOUT_MINUS_Y = 893;
    /// <summary>
    /// Bool. If true, then we occasionally invert the drop distance.
    /// </summary>
    static final int CALLOUT_DROP_AUTO = 894;
    /// <summary>
    /// Bool. if true, we look at CalloutLength.
    /// </summary>
    static final int CALLOUT_LENGTH_SPECIFIED = 895;

    //<<<<<<<< #endregion 


    // 0x0380 - 0x03bf
    //>>>>>>>> #region  Group Shape Attributes

    /// <summary>
    /// Shape Name (present only if explicitly set).
    ///
    /// RK One scenario where this is used is to create relationships between diagram nodes.
    /// Instead of basing relationships on shape ids, the relationships are created based on shape names
    /// (in DOC, RTF and model based on hashes of shape names).
    /// Shape id might change (at least can change in Aspose.Words when document is saved), shape name,
    /// on the other hand remains the same and makes the life easier (no need to update diagram relations).
    /// Shape name is usually generated by MS Word based on the shape id.
    /// </summary>
    static final int SHAPE_NAME = 896;
    /// <summary>
    /// Alternative text.
    /// </summary>
    static final int SHAPE_DESCRIPTION = 897;
    /// <summary>
    /// String. The Uri of the hyperlink.
    /// If the link is to a local bookmark, it will be prepended with "#" in the model.
    /// </summary>
    static final int HYPERLINK_ADDRESS = 898;
    /// <summary>
    /// Array of <see cref="PathPoint"/>. The polygon that text will be wrapped around (Word).
    /// In the DOC file this is actually just normal POINT array, but it is simpler for us to work with PathPoint.
    /// </summary>
    static final int WRAP_POLYGON_VERTICES = 899;
    /// <summary>
    /// Left wrapping distance from text. Default 1/8 inch. EMUs.
    /// </summary>
    static final int DISTANCE_LEFT = 900;
    /// <summary>
    /// Top wrapping distance from text. Default 0. EMUs.
    /// </summary>
    static final int DISTANCE_TOP = 901;
    /// <summary>
    /// Right wrapping distance from text. Default 1/8 inch. EMUs.
    /// </summary>
    static final int DISTANCE_RIGHT = 902;
    /// <summary>
    /// Bottom wrapping distance from text. Default 0. EMUs in Escher.
    /// </summary>
    static final int DISTANCE_BOTTOM = 903;
    /// <summary>
    /// NOT IN THE MODEL. We ignore this.
    /// </summary>
    static final int REGROUP_ID = 904;
    /// <summary>
    /// String. The tooltip (also used for hyperlinks).
    /// </summary>
    static final int SCREEN_TIP = 909;
    /// <summary>
    /// String. Looks like can be java script.
    /// </summary>
    static final int SCRIPT_TEXT = 910;
    /// <summary>
    /// <see cref="Aspose.Words.Drawing.HorizontalAlignment"/>
    ///
    /// When this attribute is specified, the shape is positioned using alignment relative to some object.
    /// The relative anchor is specified using the  relative horizontal position value, all values are valid.
    ///
    /// When the alignment value is Inside our Outside (in MS Word this is in the Book Layout dropdown),
    /// then MS Word allows the relative anchor to be only Margin or Page.
    ///
    /// Occurs in EsShapeOptionsEx record.
    /// Note the values in the SPEC are wrong (off by 1).
    /// </summary>
    static final int HORIZONTAL_ALIGNMENT = 911;
    /// <summary>
    /// <see cref="Aspose.Words.Drawing.RelativeHorizontalPosition"/>
    /// Occurs in EsShapeOptionsEx record.
    /// Note the values in the SPEC are wrong (off by 1).
    /// </summary>
    static final int RELATIVE_HORIZONTAL_POSITION = 912;
    /// <summary>
    /// <see cref="Aspose.Words.Drawing.VerticalAlignment"/>
    /// Also see comments above in <see cref="HorizontalAlignment"/>.
    ///
    /// Occurs in EsShapeOptionsEx record.
    /// Note the values in the SPEC are wrong (off by 1).
    /// </summary>
    static final int VERTICAL_ALIGNMENT = 913;
    /// <summary>
    /// <see cref="Aspose.Words.Drawing.RelativeVerticalPosition"/>
    /// Occurs in EsShapeOptionsEx record.
    /// Note the values in the SPEC are wrong (off by 1).
    /// </summary>
    static final int RELATIVE_VERTICAL_POSITION = 914;
    /// <summary>
    /// int. Defines the length of a horizontal rule as a percentage of page width.
    /// Note that value 500 means 50%.
    /// </summary>
    static final int HR_PCT = 915;
    /// <summary>
    /// <see cref="HorizontalRuleAlignment"/>. Defines the alignment of a horizontal rule.
    /// </summary>
    static final int HR_ALIGN = 916;
    /// <summary>
    /// NOT IN THE MODEL. Seems this attribute provides more exactly shape height.
    /// We read this during DOC/RTF import and remove from model after shape height is adjusted.
    /// Int. Defines the thickness of a horizontal rule in twips.
    /// </summary>
    static final int HR_HEIGHT = 917;
    /// <summary>
    /// NOT IN THE MODEL. We ignore it on read. On write we use shape width instead.
    /// Int. Defines the length of a horizontal rule in twips.
    /// </summary>
    static final int HR_WIDTH = 918;
    /// <summary>
    /// String. Looks like "mime" type of the script. I've seen a value " type="text/javascript"".
    /// </summary>
    static final int SCRIPT_TYPE = 919;
    /// <summary>
    /// Int. Probably Id of the script language. 4 = JavaScript.
    /// </summary>
    static final int SCRIPT_LANGUAGE = 920;
    /// <summary>
    /// Untested.
    /// Id script attribute of the script?
    /// </summary>
    static final int SCRIPT_ID_ATTR = 921;
    /// <summary>
    /// String. Looks like string name of the script language.
    /// </summary>
    static final int SCRIPT_LANGUAGE_NAME = 922;
    /// <summary>
    /// These are 32bit color for the inline picture borders.
    /// Occur in EsShapeOptionsEx record.
    /// We read this attribute into the model initially, so it could occur in the model.
    ///
    /// If the shape is an inline picture, then when the PICF structure is loaded,
    /// the 32bit colors specified by these attributes are applied to the borders.
    /// The border attributes BorderXXX are added to the model and these attributes are removed.
    /// </summary>
    static final int BORDER_TOP_COLOR = 923;
    static final int BORDER_LEFT_COLOR = 924;
    static final int BORDER_BOTTOM_COLOR = 925;
    static final int BORDER_RIGHT_COLOR = 926;
    /// <summary>
    /// Untested.
    /// Seen complex value in defect 3397.
    /// The shape’s 2007 representation in Office Open XML format.  The actual data is a package
    /// in Office XML format, which can simply be opened as a zip file.  This zip file contains
    /// an XML file with the root element “sp”.  Refer to the publically available Office
    /// Open XML documentation for more information about this data.  In case we lose any
    /// property when converting a 2007 Office Art shape to 2003 shape, we use this blob to
    /// retrieve the original Office Art property data when opening the file in 2007.
    /// See Appendix F for more information.
    /// Ver: 2007
    /// </summary>
    static final int METRO_BLOB = 937;
    /// <summary>
    /// Untested.
    /// The shape’s unique z-order, ranging from 1 to 503316479.  The higher the number,
    /// the closer to “in-front” the shape is.
    /// Ver: 2007.
    /// </summary>
    static final int DHGT = 938;
    /// <summary>
    /// 0x8000 flag. bool. Allows shape to anchor and position inside table cells.
    /// Occurs in extended attributes.
    /// Not sure about default value. From DOC files looks like default is true, but RTF spec says its false.
    /// </summary>
    static final int ALLOW_IN_CELL = 944;
    /// <summary>
    /// 0x4000 flag. Set to true to indicate this shape is a picture bullet.
    /// Occurs in extended properties.
    /// </summary>
    static final int PICTURE_BULLET = 945;
    /// <summary>
    /// 0x2000 flag. Set to true for a "standard" horizontal rule shape.
    /// This means the horizontal rule does not have a picture.
    /// </summary>
    static final int HR_STANDARD = 946;
    /// <summary>
    /// 0x1000 flag. Set to true to turn a shadow on the horizontal rule shape off.
    /// </summary>
    static final int HR_NO_SHADE = 947;
    /// <summary>
    /// 0x0800 flag. Set to true for a shape that is a horizontal rule.
    /// </summary>
    static final int HR_ON = 948;
    /// <summary>
    /// 0x0400 flag.
    /// Seems to be PowerPoint related.
    /// </summary>
    static final int USER_DRAWN = 949;
    /// <summary>
    /// 0x0200 flag. bool. Default is true. Occurs in extended attributes.
    /// Allows shape to overlap other shapes unless it is a shape with None wrapping (\shpwr3),
    /// in which case it can always overlap an object with other types of wrapping and vice-versa.
    /// </summary>
    static final int ALLOW_OVERLAP = 950;
    /// <summary>
    /// 0x0100 flag. Occurs in extended attributes.
    /// </summary>
    static final int REALLY_HIDDEN = 951;
    /// <summary>
    /// 0x0080 flag.
    /// </summary>
    static final int SCRIPT_ANCHOR = 952;
    /// <summary>
    /// Has the wrap polygon been edited? Default false.
    /// </summary>
    static final int EDITED_WRAP = 953;
    /// <summary>
    /// Word-only (shape is behind text), default false.
    /// </summary>
    static final int BEHIND_TEXT = 954;
    /// <summary>
    /// Notify client on a double click, default false.
    /// </summary>
    static final int ON_DBL_CLICK_NOTIFY = 955;
    /// <summary>
    /// A button shape (i.e., clicking performs an action). Set for shapes with attached hyperlinks or macros.
    /// Default false.
    /// </summary>
    static final int BUTTON = 956;
    /// <summary>
    /// 1D adjustment, default false.
    /// </summary>
    static final int ONE_D = 957;
    /// <summary>
    /// Do not display. default false.
    /// </summary>
    static final int HIDDEN = 958;
    /// <summary>
    /// Print this shape, default true.
    /// </summary>
    static final int PRINT = 959;

    //<<<<<<<< #endregion 


    // 0x0400 - 0x43f - ID properties from generic HTML. Whatever this means.
    //>>>>>>>> #region  Unknown HTML Attributes

    /// <summary>
    /// Seen in TobyHenderson6.doc and in TestInlinePictures.doc. Looks like another set of flags.
    /// </summary>
    static final int UNKNOWN_HTML_FLAGS = 1087;

    //<<<<<<<< #endregion 


    // 0x0500 - 0x053f
    //>>>>>>>> #region  Diagram Attributes

    /// <summary>
    /// Enum <see cref="Core.EditAs"/>.
    /// This property could be set for a group shape to identify it as a canvas or a diagram.
    /// </summary>
    static final int EDIT_AS = 1280;
    /// <summary>
    /// Int. In the RTF specification there is a detailed info which value means what diagram style,
    /// but the problem is that different diagram types have different sets of diagram styles,
    /// so I really prefer to store this just as integer in the model. WordML does the same.
    /// </summary>
    static final int DIAGRAM_STYLE = 1281;
    static final int UNDOCUMENTED_1282 = 1282;
    static final int UNDOCUMENTED_1283 = 1283;
    /// <summary>
    /// Array of <see cref="DiagramNodeRelation"/>.
    /// It is important to write this property, otherwise MS Word does not recognize the shape as a diagram.
    /// </summary>
    static final int DIAGRAM_RELATIONS_TABLE = 1284;
    /// <summary>
    /// Fixed. The scale factor for width of a diagram.
    /// </summary>
    static final int DIAGRAM_SCALE_X = 1285;
    /// <summary>
    /// Fixed. The scale factor for height of a diagram.
    /// </summary>
    static final int DIAGRAM_SCALE_Y = 1286;
    /// <summary>
    /// Int. Specifies the font size in points of text for new nodes in a diagram.
    /// </summary>
    static final int DIAGRAM_FONT_SIZE = 1287;
    /// <summary>
    /// int[] consisting of four items. Specifies the bounds that the diagram nodes are constrained to.
    /// </summary>
    static final int DIAGRAM_CONSTRAIN_BOUNDS = 1288;
    /// <summary>
    /// This property specifies the amount to scale text. This property MAY be ignored.
    /// A FixedPoint, as specified in [MS-OSHARED] section 2.2.1.6, that specifies the amount to scale text.
    /// </summary>
    static final int DIAGRAM_BASE_TEXT_SCALE = 1289;
    /// <summary>
    /// Bool. True when diagram uses auto format.
    /// </summary>
    static final int DIAGRAM_AUTO_FORMAT = 1340;
    /// <summary>
    /// Bool. Not fully sure, but looks very much reverse flag.
    /// </summary>
    static final int DIAGRAM_REVERSE = 1341;
    /// <summary>
    /// Bool.
    /// </summary>
    static final int DIAGRAM_AUTO_LAYOUT = 1342;
    /// <summary>
    /// Bool. I guess this corresponds to fPseudoInline in RTF. This is set for inline shapes.
    /// Affects not only diagrams, but all shapes.
    ///
    /// The shape is pseudo-inline, meaning it behaves like an inline image as far as positioning goes,
    /// but has the features of shapes.
    /// </summary>
    static final int PSEUDO_INLINE = 1343;

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Line Side Attributes

    // 0x0540 - Line Left Style
    // 0x0580 - Line Top Style
    // 0x05c0 - Line Right Style
    // 0x0600 - Line Bottom Style
    // 0x0640 - Line Column Style
    // Attributes from 0x0540 to 0x067f are same as line attributes, but for
    // the left, top, right, bottom and column sides of the stroke.

    //<<<<<<<< #endregion 

    // 0x680 - 0x6bf Properties related to Web Component Functionality.

    // 0x6c0 - 0x6ff This property set defines an additional clipping path for a shape or group.
    //>>>>>>>> #region  Clip Attributes
    /// <summary>
    /// Untested.
    /// Seen in TestArtSchool8039.doc. Complex, contains 0x2e bytes of data.
    /// </summary>
    static final int CLIP_VERTICES = 1728;
    /// <summary>
    /// Untested.
    /// </summary>
    static final int CLIP_SEGMENT_INFO = 1729;
    /// <summary>
    /// Untested.
    /// </summary>
    static final int CLIP_SHAPE_PATH = 1730;
    /// <summary>
    /// Untested. bool.
    /// </summary>
    static final int CLIP_TO_WRAP = 1790;
    /// <summary>
    /// Untested. bool. Seen in BusinessBrochure.doc.
    /// </summary>
    static final int CLIP_OK = 1791;

    //<<<<<<<< #endregion 


    // 0x700 - 0x73f
    //>>>>>>>> #region  TablePC Ink Attributes
    /// <summary>
    /// InkData as byte array. Complex.
    /// </summary>
    static final int INK_DATA = 1792;
    /// <summary>
    /// Specifies that Ink should be treated as annotation.
    /// </summary>
    static final int INK_ANNOTATION = 1855;
    //<<<<<<<< #endregion 

    // 0x740 - 0x77f unused

    // 0x780 - 0x7bf Signature. These properties specify that the shape is a signature
    // line a document generated by Office 2007 or later.  A signature line provides
    // a visual representation of a signature in a document that is digitally signed.
    //>>>>>>>> #region  Signature Line Attributes

    /// <summary>
    /// This property specifies the GUID of the signature line.
    /// </summary>
    static final int SIG_SETUP_ID = 0x781;
    /// <summary>
    /// This property specifies the GUID of the provider that creates the signature line.
    /// </summary>
    static final int SIG_SETUP_PROV_ID = 0x782;
    /// <summary>
    /// This property specifies the name of the suggested signer.
    /// </summary>
    static final int SIG_SETUP_SUGG_SIGNER = 0x783;
    /// <summary>
    /// This property specifies the title or additional information of the suggested signer.
    /// </summary>
    static final int SIG_SETUP_SUGG_SIGNER_2 = 0x784;
    /// <summary>
    /// This property specifies the e-mail address of the suggested signer.
    /// </summary>
    static final int SIG_SETUP_SUGG_SIGNER_EMAIL = 0x785;
    /// <summary>
    /// This property specifies the signing instruction displayed to the signer.
    /// </summary>
    static final int SIG_SETUP_SIGN_INST = 0x786;
    /// <summary>
    /// 2.3.27.13 wzSigSetupAddlXml
    /// This property specifies the additional information in XML format provided by the provider that creates the signature line.
    /// </summary>
    static final int SIG_SETUP_ADDL_XML = 0x787;
    /// <summary>
    /// 2.3.27.15 wzSigSetupProvUrl
    /// This property specifies the URL of the provider that creates the signature line.
    /// </summary>
    static final int SIG_SETUP_PROV_URL = 0x788;

    /// <summary>
    /// bool. Specifies whether the signing date is displayed.
    /// </summary>
    static final int SIG_SETUP_SHOW_SIGN_DATE = 0x07bc;
    /// <summary>
    /// bool. Specifies whether the comment from the signer is allowed.
    /// </summary>
    static final int SIG_SETUP_ALLOW_COMMENTS = 0x7bd;
    /// <summary>
    /// bool. Specifies whether the signing instruction is overridden.
    /// </summary>
    static final int SIG_SETUP_SIGN_INST_SET = 0x7be;
    /// <summary>
    /// bool. Specifies whether the shape is a signature line.
    /// </summary>
    static final int IS_SIGNATURE_LINE = 0x7bf;

    //<<<<<<<< #endregion 

    // 0x07c0 - 0x07ff Relative position and size properties for shapes and textboxes in Word.
    //>>>>>>>> #region  Group Shape 2 Attributes
    /// <summary>
    /// int. Relative left position of the shape in 1/10th of percent.
    /// When present, logically overrides the <see cref="Left"/> attribute.
    /// Available since Word 2007.
    /// </summary>
    static final int LEFT_PERCENT = 1986;
    /// <summary>
    /// int. Relative top position of the shape in 1/10th of percent.
    /// When present, logically overrides the <see cref="Top"/> attribute.
    /// Available since Word 2007.
    /// </summary>
    static final int TOP_PERCENT = 1987;
    /// <summary>
    /// int. Relative width of the shape in 1/10th of percent.
    /// When present, logically overrides the <see cref="Width"/> attribute.
    /// Available since Word 2007.
    /// </summary>
    static final int WIDTH_PERCENT = 1984;
    /// <summary>
    /// int. Relative height of the shape in 1/10th of percent.
    /// When present, logically overrides the <see cref="Height"/> attribute.
    /// Available since Word 2007.
    /// </summary>
    static final int HEIGHT_PERCENT = 1985;
    /// <summary>
    /// <see cref="RelativeHorizontalSize"/> 
    /// Specifies what shape width is relative to.
    /// Available since Word 2007.
    /// </summary>
    static final int RELATIVE_WIDTH = 1988;
    /// <summary>
    /// <see cref="RelativeVerticalSize"/> 
    /// Specifies what shape width is relative to.
    /// Available since Word 2007.
    /// </summary>
    static final int RELATIVE_HEIGHT = 1989;
    /// <summary>
    /// Untested.
    /// Starting column.
    /// </summary>
    static final int COL_START = 1990;
    /// <summary>
    /// Untested.
    /// Number of columns to span.
    /// </summary>
    static final int COL_SPAN = 1991;

    //<<<<<<<< #endregion 

    // 0x1000 - 0x103f
    //>>>>>>>> #region  Aspose Attributes

    /// <summary>
    /// <see cref="FlipOrientation"/>. Not stored as a property in Escher, but stored in the EsShape record.
    /// </summary>
    static final int FLIP = 4096;
    /// <summary>
    /// <see cref="WrapType"/>. Not stored as a property in Escher, but stored in the FSPA structure.
    /// </summary>
    static final int WRAP_TYPE = 4097;
    /// <summary>
    /// <see cref="WrapSide"/>. Not stored as a property in Escher, but stored in the FSPA structure.
    /// </summary>
    static final int WRAP_SIDE = 4098;
    /// <summary>
    /// bool in the model. Not stored as a property in Escher, but stored in the FSPA structure.
    /// </summary>
    static final int ANCHOR_LOCKED = 4099;
    /// <summary>
    /// byte[] in the model. Not stored as a property in Escher.
    /// </summary>
    static final int IMAGE_BYTES = 4102;
    /// <summary>
    /// string in the model. Not stored as a property in Escher.
    /// If present, specifies a "comment" for a non-linked image.
    /// Usually, it is the original name of the image, sometimes without the extension.
    /// </summary>
    static final int IMAGE_TITLE = 4103;
    /// <summary>
    /// string in the model. Not stored as a property in Escher.
    /// If present, this attribute specifies the URL to the linked image.
    /// </summary>
    static final int IMAGE_SOURCE_FULL_NAME = 4104;
    /// <summary>
    /// Border in the model. Not stored as a property in Escher. Stored in PICF for inline pictures only.
    /// </summary>
    static final int BORDER_TOP = 4106;
    /// <summary>
    /// Border in the model. Not stored as a property in Escher. Stored in PICF for inline pictures only.
    /// </summary>
    static final int BORDER_LEFT = 4107;
    /// <summary>
    /// Border in the model. Not stored as a property in Escher. Stored in PICF for inline pictures only.
    /// </summary>
    static final int BORDER_BOTTOM = 4108;
    /// <summary>
    /// Border in the model. Not stored as a property in Escher. Stored in PICF for inline pictures only.
    /// </summary>
    static final int BORDER_RIGHT = 4109;
    /// <summary>
    /// byte[] in the model. Not stored as a property in Escher.
    /// </summary>
    /// <remarks>
    /// WORDSNET-16360 The key values for FillImageBytes and LineImageBytes is important for DOC writer.
    /// Interchanged, so FillImageBytes goes before LineImageBytes.
    /// </remarks>
    static final int FILL_IMAGE_BYTES = 4110;
    /// <summary>
    /// byte[] in the model. Not stored as a property in Escher.
    /// </summary>
    static final int LINE_IMAGE_BYTES = 4111;
    /// <summary>
    /// The instance of <see cref="IEmbeddedObject"/> object.
    /// </summary>
    static final int OLE_OBJECT = 4112;
    /// <summary>
    /// String that specifies ProgID of the OLE object.
    /// </summary>
    static final int OLE_PROG_ID = 4113;
    /// <summary>
    /// String that specifies full name of the linked file.
    /// </summary>
    static final int OLE_SOURCE_FULL_NAME = 4114;
    /// <summary>
    /// String that specifies the range within the linked file. Optional.
    /// </summary>
    static final int OLE_SOURCE_ITEM = 4115;
    /// <summary>
    /// Bool that specifies whether the link is auto updated or not.
    /// </summary>
    static final int OLE_AUTO_UPDATE = 4116;
    /// <summary>
    /// Bool that specifies whether the OLE link is locked for updates or not.
    /// </summary>
    static final int OLE_LOCKED = 4117;
    /// <summary>
    /// OleLinkType value. Specifies type of the OLE link.
    /// </summary>
    static final int OLE_LINK_TYPE = 4118;
    /// <summary>
    /// Int. Corresponds to the value for the \f field switch in OLE fields.
    /// </summary>
    static final int OLE_FORMAT_UPDATE_TYPE = 4119;
    /// <summary>
    /// string that specifies the hyperlink target frame.
    /// </summary>
    static final int HYPERLINK_TARGET = 4120;
    /// <summary>
    /// <see cref="PresetTexture"/>, specifies the id of the built-in texture for the stroke fill pattern.
    /// </summary>
    static final int LINE_FILL_PRESET_TEXTURE = 4121;
    /// <summary>
    /// <see cref="PresetTexture"/>, specifies the id of the built-in texture for the fill pattern.
    /// </summary>
    static final int FILL_PRESET_TEXTURE = 4122;

    /// <summary>
    /// int.
    /// Shape id as loaded from the file or generated when a shape is created.
    /// All shape identifiers are regenerated into MS Word-like sequence before a document is saved.
    /// </summary>
    /// <remarks>
    /// Note, DrawingML id is of unsigned int type.
    /// </remarks>
    static final int SHAPE_ID = 4124;

    /// <summary>
    /// CoordOrigin.X in coordinate units.
    /// Normally occurs for group shapes and also shapes with custom geometry.
    /// </summary>
    static final int COORD_ORIGIN_X = 4125;
    /// <summary>
    /// CoordOrigin.Y in coordinate units.
    /// Normally occurs for group shapes and also shapes with custom geometry.
    /// </summary>
    static final int COORD_ORIGIN_Y = 4126;
    /// <summary>
    /// CoordSize.Width in coordinate units.
    /// Normally occurs for group shapes and also shapes with custom geometry.
    /// </summary>
    static final int COORD_SIZE_WIDTH = 4127;
    /// <summary>
    /// CoordSize.Height in coordinate units.
    /// Normally occurs for group shapes and also shapes with custom geometry.
    /// </summary>
    static final int COORD_SIZE_HEIGHT = 4128;

    /// <summary>
    /// double.
    /// Left position of the shape. Has effect for floating shapes only.
    /// For top level shapes these are points, for child shapes these are in the parent coordinate system.
    /// </summary>
    static final int LEFT = 4129;
    /// <summary>
    /// double.
    /// Top position of the shape. Has effect for floating shapes only.
    /// For top level shapes these are points, for child shapes these are in the parent coordinate system.
    /// </summary>
    static final int TOP = 4130;
    /// <summary>
    /// double. Width of the shape.
    /// For top level shapes these are points, for child shapes these are in the parent coordinate system.
    /// </summary>
    static final int WIDTH = 4131;
    /// <summary>
    /// double. Height of the shape.
    /// For top level shapes these are points, for child shapes these are in the parent coordinate system.
    /// </summary>
    static final int HEIGHT = 4132;

    /// <summary>
    /// Boolean. DrawingML property.
    /// </summary>
    static final int DML_LOCK_ARROW_HEADS = 4140;
    /// <summary>
    /// Boolean. DrawingML property.
    /// </summary>
    static final int DML_LOCK_RESIZE = 4141;
    /// <summary>
    /// Boolean. DrawingML property.
    /// </summary>
    static final int DML_LOCK_DRILL_DOWN = 4142;

    /// <summary>
    /// int. EMUs. DrawingML property.
    /// </summary>
    static final int DML_EFFECT_EXTENT_LEFT = 4143;
    /// <summary>
    /// int. EMUs. DrawingML property.
    /// </summary>
    static final int DML_EFFECT_EXTENT_TOP = 4144;
    /// <summary>
    /// int. EMUs. DrawingML property.
    /// </summary>
    static final int DML_EFFECT_EXTENT_RIGHT = 4145;
    /// <summary>
    /// int. EMUs. DrawingML property.
    /// </summary>
    static final int DML_EFFECT_EXTENT_BOTTOM = 4146;

    /// <summary>
    /// At the moment this is an unparsed string value.
    /// </summary>
    static final int THEME_COLOR = 4147;
    /// <summary>
    /// At the moment this is an unparsed string value.
    /// </summary>
    static final int THEME_SHADE = 4148;
    /// <summary>
    /// At the moment this is an unparsed string value.
    /// </summary>
    static final int THEME_TINT = 4149;

    /// <summary>
    /// int.
    /// Determines the display order of overlapping shapes.
    /// </summary>
    static final int Z_ORDER = 4154;

    /// <summary>
    /// <see cref="Aspose.Words.Drawing.ShapeType"/>.
    /// </summary>
    static final int SHAPE_TYPE = 4155;

    /// <summary>
    /// <see cref="ConnectorRule" /> object.
    /// </summary>
    static final int CONNECTOR_RULE = 4156;

    /// <summary>
    /// String. Shape title (caption).
    /// </summary>
    static final int SHAPE_TITLE = 4157;

    /// <summary>
    /// Shape width used by Word when document is layouted. See TestJira13258().
    /// </summary>
    static final int SYS_LAYOUT_WIDTH = 4160;

    /// <summary>
    /// Shape height used by Word when document is layouted. See TestJira13258().
    /// </summary>
    static final int SYS_LAYOUT_HEIGHT = 4161;

    //<<<<<<<< #endregion 
}

