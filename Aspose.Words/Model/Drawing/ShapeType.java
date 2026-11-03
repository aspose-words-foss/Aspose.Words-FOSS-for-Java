// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/02/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the type of shape in a Microsoft Word document.
/// </summary>
/// <seealso cref="ShapeBase.ShapeType"/>
public /*enum*/ final class ShapeType
{
    private ShapeType(){}
    
    /// <summary>
    /// The shape is an image. 
    /// </summary>
    public static final int IMAGE = 75;
    /// <summary>
    /// The shape is a textbox. Note that shapes of many other types can also have text inside them too.
    /// A shape does not have to have this type to contain text.
    /// </summary>
    public static final int TEXT_BOX = 202;
    /// <summary>
    /// <p>The shape is a group shape.</p>
    /// </summary>
    /// <dev>In DOC file, shape group has shape type NonPrimitive, we don't want this in the model,
    /// so in the model it is our own constant.</dev>
    public static final int GROUP = -1;
    /// <summary>
    /// The shape is an OLE object.
    /// <p>You cannot create shapes of this type in the document.</p>
    /// </summary>
    /// <dev>
    /// In Microsoft Word, shapes that represent OLE objects have shape type picture,
    /// but in our model, they are distinguished into their own shape type.
    /// </dev>
    public static final int OLE_OBJECT = -2;
    /// <summary>
    /// The shape is an ActiveX control.
    /// <p>You cannot create shapes of this type in the document.</p>
    /// </summary>
    /// <dev>
    /// In DOC and RTF, shapes that represent ActiveX controls have shape type picture.
    /// In WordML, ActiveX controls have their own shape type and so is in the model.
    /// </dev>
    public static final int OLE_CONTROL = 201;
    /// <summary>
    /// A shape drawn by user and consisting of multiple segments and/or vertices (curve, freeform or scribble).
    /// <p>You cannot create shapes of this type in the document.</p>
    /// </summary>
    public static final int NON_PRIMITIVE = 0;
    /// <summary>
    /// Rectangle.
    /// </summary>
    public static final int RECTANGLE = 1;
    /// <summary>
    /// Round rectangle.
    /// </summary>
    public static final int ROUND_RECTANGLE = 2;
    /// <summary>
    /// Ellipse.
    /// </summary>
    public static final int ELLIPSE = 3;
    /// <summary>
    /// Diamond.
    /// </summary>
    public static final int DIAMOND = 4;
    /// <summary>
    /// Triangle.
    /// </summary>
    public static final int TRIANGLE = 5;
    /// <summary>
    /// Right triangle.
    /// </summary>
    public static final int RIGHT_TRIANGLE = 6;
    /// <summary>
    /// Parallelogram.
    /// </summary>
    public static final int PARALLELOGRAM = 7;
    /// <summary>
    /// Trapezoid.
    /// </summary>
    public static final int TRAPEZOID = 8;
    /// <summary>
    /// Hexagon.
    /// </summary>
    public static final int HEXAGON = 9;
    /// <summary>
    /// Octagon.
    /// </summary>
    public static final int OCTAGON = 10;
    /// <summary>
    /// Plus.
    /// </summary>
    public static final int PLUS = 11;
    /// <summary>
    /// Star.
    /// </summary>
    public static final int STAR = 12;
    /// <summary>
    /// Arrow.
    /// </summary>
    public static final int ARROW = 13;
    /// <summary>
    /// Thick arrow.
    /// </summary>
    public static final int THICK_ARROW = 14;
    /// <summary>
    /// Home plate.
    /// </summary>
    public static final int HOME_PLATE = 15;
    /// <summary>
    /// Cube.
    /// </summary>
    public static final int CUBE = 16;
    /// <summary>
    /// Balloon.
    /// </summary>
    public static final int BALLOON = 17;
    /// <summary>
    /// Seal.
    /// </summary>
    public static final int SEAL = 18;
    /// <summary>
    /// Arc.
    /// </summary>
    public static final int ARC = 19;
    /// <summary>
    /// Line.
    /// </summary>
    public static final int LINE = 20;
    /// <summary>
    /// Plaque.
    /// </summary>
    public static final int PLAQUE = 21;
    /// <summary>
    /// Can.
    /// </summary>
    public static final int CAN = 22;
    /// <summary>
    /// Donut.
    /// </summary>
    public static final int DONUT = 23;
    /// <summary>
    /// Text simple.
    /// </summary>
    public static final int TEXT_SIMPLE = 24;
    /// <summary>
    /// Text octagon.
    /// </summary>
    public static final int TEXT_OCTAGON = 25;
    /// <summary>
    /// Text hexagon.
    /// </summary>
    public static final int TEXT_HEXAGON = 26;
    /// <summary>
    /// Text curve.
    /// </summary>
    public static final int TEXT_CURVE = 27;
    /// <summary>
    /// Text wave.
    /// </summary>
    public static final int TEXT_WAVE = 28;
    /// <summary>
    /// Text ring.
    /// </summary>
    public static final int TEXT_RING = 29;
    /// <summary>
    /// Text on curve.
    /// </summary>
    public static final int TEXT_ON_CURVE = 30;
    /// <summary>
    /// Text on ring.
    /// </summary>
    public static final int TEXT_ON_RING = 31;
    /// <summary>
    /// A straight connector shape.
    /// </summary>
    public static final int STRAIGHT_CONNECTOR_1 = 32;
    /// <summary>
    /// A bent connector shape with two segments.
    /// </summary>
    public static final int BENT_CONNECTOR_2 = 33;
    /// <summary>
    /// A bent connector shape with three segments.
    /// </summary>
    public static final int BENT_CONNECTOR_3 = 34;
    /// <summary>
    /// A bent connector shape with four segments.
    /// </summary>
    public static final int BENT_CONNECTOR_4 = 35;
    /// <summary>
    /// A bent connector shape with five segments.
    /// </summary>
    public static final int BENT_CONNECTOR_5 = 36;
    /// <summary>
    /// A curved connector shape with two segments.
    /// </summary>
    public static final int CURVED_CONNECTOR_2 = 37;
    /// <summary>
    /// A curved connector shape with three segments.
    /// </summary>
    public static final int CURVED_CONNECTOR_3 = 38;
    /// <summary>
    /// A curved connector shape with four segments.
    /// </summary>
    public static final int CURVED_CONNECTOR_4 = 39;
    /// <summary>
    /// A curved connector shape with five segments.
    /// </summary>
    public static final int CURVED_CONNECTOR_5 = 40;
    /// <summary>
    /// A callout shape with one arrow.
    /// </summary>
    public static final int CALLOUT_1 = 41;
    /// <summary>
    /// A callout shape with two arrows.
    /// </summary>
    public static final int CALLOUT_2 = 42;
    /// <summary>
    /// A callout shape with three arrows.
    /// </summary>
    public static final int CALLOUT_3 = 43;
    /// <summary>
    /// An accent callout shape with one arrow.
    /// </summary>
    public static final int ACCENT_CALLOUT_1 = 44;
    /// <summary>
    /// An accent callout shape with two arrows.
    /// </summary>
    public static final int ACCENT_CALLOUT_2 = 45;
    /// <summary>
    /// An accent callout shape with three arrows.
    /// </summary>
    public static final int ACCENT_CALLOUT_3 = 46;
    /// <summary>
    /// Border callout 1.
    /// </summary>
    public static final int BORDER_CALLOUT_1 = 47;
    /// <summary>
    /// Border callout 2.
    /// </summary>
    public static final int BORDER_CALLOUT_2 = 48;
    /// <summary>
    /// Border callout 3.
    /// </summary>
    public static final int BORDER_CALLOUT_3 = 49;
    /// <summary>
    /// Accent border callout 1.
    /// </summary>
    public static final int ACCENT_BORDER_CALLOUT_1 = 50;
    /// <summary>
    /// Accent border callout 2.
    /// </summary>
    public static final int ACCENT_BORDER_CALLOUT_2 = 51;
    /// <summary>
    /// Accent border callout 3.
    /// </summary>
    public static final int ACCENT_BORDER_CALLOUT_3 = 52;
    /// <summary>
    /// Ribbon.
    /// </summary>
    public static final int RIBBON = 53;
    /// <summary>
    /// Ribbon 2.
    /// </summary>
    public static final int RIBBON_2 = 54;
    /// <summary>
    /// Chevron.
    /// </summary>
    public static final int CHEVRON = 55;
    /// <summary>
    /// Pentagon.
    /// </summary>
    public static final int PENTAGON = 56;
    /// <summary>
    /// NoSmoking.
    /// </summary>
    public static final int NO_SMOKING = 57;
    /// <summary>
    /// Eight-pointed star.
    /// </summary>
    public static final int SEAL_8 = 58;
    /// <summary>
    /// 16-pointed star.
    /// </summary>
    public static final int SEAL_16 = 59;
    /// <summary>
    /// 32-pointed star.
    /// </summary>
    public static final int SEAL_32 = 60;
    /// <summary>
    /// Wedge rect callout.
    /// </summary>
    public static final int WEDGE_RECT_CALLOUT = 61;
    /// <summary>
    /// Wedge R rect callout.
    /// </summary>
    public static final int WEDGE_R_RECT_CALLOUT = 62;
    /// <summary>
    /// Wedge ellipse callout.
    /// </summary>
    public static final int WEDGE_ELLIPSE_CALLOUT = 63;
    /// <summary>
    /// Wave.
    /// </summary>
    public static final int WAVE = 64;
    /// <summary>
    /// Folded corner.
    /// </summary>
    public static final int FOLDED_CORNER = 65;
    /// <summary>
    /// Left arrow.
    /// </summary>
    public static final int LEFT_ARROW = 66;
    /// <summary>
    /// Down arrow.
    /// </summary>
    public static final int DOWN_ARROW = 67;
    /// <summary>
    /// Up arrow.
    /// </summary>
    public static final int UP_ARROW = 68;
    /// <summary>
    /// Left right arrow.
    /// </summary>
    public static final int LEFT_RIGHT_ARROW = 69;
    /// <summary>
    /// Up down arrow.
    /// </summary>
    public static final int UP_DOWN_ARROW = 70;
    /// <summary>
    /// Irregular seal 1.
    /// </summary>
    public static final int IRREGULAR_SEAL_1 = 71;
    /// <summary>
    /// Irregular seal 2.
    /// </summary>
    public static final int IRREGULAR_SEAL_2 = 72;
    /// <summary>
    /// Lightning bolt.
    /// </summary>
    public static final int LIGHTNING_BOLT = 73;
    /// <summary>
    /// Heart.
    /// </summary>
    public static final int HEART = 74;
    /// <summary>
    /// Quad arrow.
    /// </summary>
    public static final int QUAD_ARROW = 76;
    /// <summary>
    /// Left arrow callout.
    /// </summary>
    public static final int LEFT_ARROW_CALLOUT = 77;
    /// <summary>
    /// Right arrow callout
    /// </summary>
    public static final int RIGHT_ARROW_CALLOUT = 78;
    /// <summary>
    /// Up arrow callout.
    /// </summary>
    public static final int UP_ARROW_CALLOUT = 79;
    /// <summary>
    /// Down arrow callout.
    /// </summary>
    public static final int DOWN_ARROW_CALLOUT = 80;
    /// <summary>
    /// Left right arrow callout.
    /// </summary>
    public static final int LEFT_RIGHT_ARROW_CALLOUT = 81;
    /// <summary>
    /// Up down arrow callout.
    /// </summary>
    public static final int UP_DOWN_ARROW_CALLOUT = 82;
    /// <summary>
    /// Quad arrow callout.
    /// </summary>
    public static final int QUAD_ARROW_CALLOUT = 83;
    /// <summary>
    /// Bevel.
    /// </summary>
    public static final int BEVEL = 84;
    /// <summary>
    /// Left bracket.
    /// </summary>
    public static final int LEFT_BRACKET = 85;
    /// <summary>
    /// Right bracket.
    /// </summary>
    public static final int RIGHT_BRACKET = 86;
    /// <summary>
    /// Left brace.
    /// </summary>
    public static final int LEFT_BRACE = 87;
    /// <summary>
    /// Right brace.
    /// </summary>
    public static final int RIGHT_BRACE = 88;
    /// <summary>
    /// Left up arrow.
    /// </summary>
    public static final int LEFT_UP_ARROW = 89;
    /// <summary>
    /// Bent up arrow.
    /// </summary>
    public static final int BENT_UP_ARROW = 90;
    /// <summary>
    /// Bent arrow.
    /// </summary>
    public static final int BENT_ARROW = 91;
    /// <summary>
    /// 24-pointed star.
    /// </summary>
    public static final int SEAL_24 = 92;
    /// <summary>
    /// Striped right arrow.
    /// </summary>
    public static final int STRIPED_RIGHT_ARROW = 93;
    /// <summary>
    /// Notched right arrow.
    /// </summary>
    public static final int NOTCHED_RIGHT_ARROW = 94;
    /// <summary>
    /// Block arc.
    /// </summary>
    public static final int BLOCK_ARC = 95;
    /// <summary>
    /// Smiley face.
    /// </summary>
    public static final int SMILEY_FACE = 96;
    /// <summary>
    /// Vertical scroll.
    /// </summary>
    public static final int VERTICAL_SCROLL = 97;
    /// <summary>
    /// Horizontal scroll.
    /// </summary>
    public static final int HORIZONTAL_SCROLL = 98;
    /// <summary>
    /// Circular arrow.
    /// </summary>
    public static final int CIRCULAR_ARROW = 99;
    /// <summary>
    /// This shape type seems to be set for shapes that are not part of the standard set of the
    /// auto shapes in Microsoft Word. For example, if you insert a new auto shape from ClipArt.
    /// <p>You cannot create shapes of this type in the document.</p>
    /// </summary>
    public static final int CUSTOM_SHAPE = 100;
    /// <summary>
    /// Uturn arrow.
    /// </summary>
    public static final int UTURN_ARROW = 101;
    /// <summary>
    /// Curved right arrow.
    /// </summary>
    public static final int CURVED_RIGHT_ARROW = 102;
    /// <summary>
    /// Curved left arrow.
    /// </summary>
    public static final int CURVED_LEFT_ARROW = 103;
    /// <summary>
    /// Curved up arrow
    /// </summary>
    public static final int CURVED_UP_ARROW = 104;
    /// <summary>
    /// Curved down arrow.
    /// </summary>
    public static final int CURVED_DOWN_ARROW = 105;
    /// <summary>
    /// Cloud callout.
    /// </summary>
    public static final int CLOUD_CALLOUT = 106;
    /// <summary>
    /// Ellipse ribbon.
    /// </summary>
    public static final int ELLIPSE_RIBBON = 107;
    /// <summary>
    /// Ellipse ribbon 2.
    /// </summary>
    public static final int ELLIPSE_RIBBON_2 = 108;
    /// <summary>
    /// Flow chart process.
    /// </summary>
    public static final int FLOW_CHART_PROCESS = 109;
    /// <summary>
    /// Flow chart decision.
    /// </summary>
    public static final int FLOW_CHART_DECISION = 110;
    /// <summary>
    /// Flow chart input output.
    /// </summary>
    public static final int FLOW_CHART_INPUT_OUTPUT = 111;
    /// <summary>
    /// Flow chart predefined process
    /// </summary>
    public static final int FLOW_CHART_PREDEFINED_PROCESS = 112;
    /// <summary>
    /// Flow chart internal storage.
    /// </summary>
    public static final int FLOW_CHART_INTERNAL_STORAGE = 113;
    /// <summary>
    /// Flow chart document.
    /// </summary>
    public static final int FLOW_CHART_DOCUMENT = 114;
    /// <summary>
    /// Flow chart multi document.
    /// </summary>
    public static final int FLOW_CHART_MULTIDOCUMENT = 115;
    /// <summary>
    /// Flow chart terminator.
    /// </summary>
    public static final int FLOW_CHART_TERMINATOR = 116;
    /// <summary>
    /// Flow chart preparation.
    /// </summary>
    public static final int FLOW_CHART_PREPARATION = 117;
    /// <summary>
    /// Flow chart manual input.
    /// </summary>
    public static final int FLOW_CHART_MANUAL_INPUT = 118;
    /// <summary>
    /// Flow chart manual operation.
    /// </summary>
    public static final int FLOW_CHART_MANUAL_OPERATION = 119;
    /// <summary>
    /// Flow chart connector.
    /// </summary>
    public static final int FLOW_CHART_CONNECTOR = 120;
    /// <summary>
    /// Flow chart punched card.
    /// </summary>
    public static final int FLOW_CHART_PUNCHED_CARD = 121;
    /// <summary>
    /// Flow chart punched tape.
    /// </summary>
    public static final int FLOW_CHART_PUNCHED_TAPE = 122;
    /// <summary>
    /// Flow chart summing junction.
    /// </summary>
    public static final int FLOW_CHART_SUMMING_JUNCTION = 123;
    /// <summary>
    /// Flow chart or.
    /// </summary>
    public static final int FLOW_CHART_OR = 124;
    /// <summary>
    /// Flow chart collate.
    /// </summary>
    public static final int FLOW_CHART_COLLATE = 125;
    /// <summary>
    /// Flow chart sort.
    /// </summary>
    public static final int FLOW_CHART_SORT = 126;
    /// <summary>
    /// Flow chart extract.
    /// </summary>
    public static final int FLOW_CHART_EXTRACT = 127;
    /// <summary>
    /// Flow chart merge.
    /// </summary>
    public static final int FLOW_CHART_MERGE = 128;
    /// <summary>
    /// Flow chart off-line storage.
    /// </summary>
    public static final int FLOW_CHART_OFFLINE_STORAGE = 129;
    /// <summary>
    /// Flow chart on-line storage.
    /// </summary>
    public static final int FLOW_CHART_ONLINE_STORAGE = 130;
    /// <summary>
    /// Flow char magnetic tape.
    /// </summary>
    public static final int FLOW_CHART_MAGNETIC_TAPE = 131;
    /// <summary>
    /// Flow chart magnetic disk.
    /// </summary>
    public static final int FLOW_CHART_MAGNETIC_DISK = 132;
    /// <summary>
    /// Flow chart magnetic drum.
    /// </summary>
    public static final int FLOW_CHART_MAGNETIC_DRUM = 133;
    /// <summary>
    /// Flow chart display.
    /// </summary>
    public static final int FLOW_CHART_DISPLAY = 134;
    /// <summary>
    /// Flow chart delay.
    /// </summary>
    public static final int FLOW_CHART_DELAY = 135;
    /// <summary>
    /// Plain-text, WordArt object.
    /// </summary>
    public static final int TEXT_PLAIN_TEXT = 136;
    /// <summary>
    /// Stop, WordArt object.
    /// </summary>
    public static final int TEXT_STOP = 137;
    /// <summary>
    /// Triangle, WordArt object.
    /// </summary>
    public static final int TEXT_TRIANGLE = 138;
    /// <summary>
    /// Triangle inverted, WordArt object.
    /// </summary>
    public static final int TEXT_TRIANGLE_INVERTED = 139;
    /// <summary>
    /// Chevron, WordArt object.
    /// </summary>
    public static final int TEXT_CHEVRON = 140;
    /// <summary>
    /// Chevron inverted, WordArt object.
    /// </summary>
    public static final int TEXT_CHEVRON_INVERTED = 141;
    /// <summary>
    /// Ring inside, WordArt object.
    /// </summary>
    public static final int TEXT_RING_INSIDE = 142;
    /// <summary>
    /// Ring outside, WordArt object.
    /// </summary>
    public static final int TEXT_RING_OUTSIDE = 143;
    /// <summary>
    /// Arch up curve, WordArt object.
    /// </summary>
    public static final int TEXT_ARCH_UP_CURVE = 144;
    /// <summary>
    /// Arch down curve, WordArt object.
    /// </summary>
    public static final int TEXT_ARCH_DOWN_CURVE = 145;
    /// <summary>
    /// Circle curve, WordArt object.
    /// </summary>
    public static final int TEXT_CIRCLE_CURVE = 146;
    /// <summary>
    /// Button curve, WordArt object.
    /// </summary>
    public static final int TEXT_BUTTON_CURVE = 147;
    /// <summary>
    /// Arch up pour, WordArt object.
    /// </summary>
    public static final int TEXT_ARCH_UP_POUR = 148;
    /// <summary>
    /// Arch down pour, WordArt object.
    /// </summary>
    public static final int TEXT_ARCH_DOWN_POUR = 149;
    /// <summary>
    /// Circle pour, WordArt object.
    /// </summary>
    public static final int TEXT_CIRCLE_POUR = 150;
    /// <summary>
    /// Button pour, WordArt object.
    /// </summary>
    public static final int TEXT_BUTTON_POUR = 151;
    /// <summary>
    /// Curve up, WordArt object.
    /// </summary>
    public static final int TEXT_CURVE_UP = 152;
    /// <summary>
    /// Curve down, WordArt object.
    /// </summary>
    public static final int TEXT_CURVE_DOWN = 153;
    /// <summary>
    /// Cascade up, WordArt object.
    /// </summary>
    public static final int TEXT_CASCADE_UP = 154;
    /// <summary>
    /// Cascade down, WordArt object.
    /// </summary>
    public static final int TEXT_CASCADE_DOWN = 155;
    /// <summary>
    /// Wave 1, WordArt object.
    /// </summary>
    public static final int TEXT_WAVE_1 = 156;
    /// <summary>
    /// Wave 2, WordArt object.
    /// </summary>
    public static final int TEXT_WAVE_2 = 157;
    /// <summary>
    /// Wave 3, WordArt object.
    /// </summary>
    public static final int TEXT_WAVE_3 = 158;
    /// <summary>
    /// Wave 4, WordArt object.
    /// </summary>
    public static final int TEXT_WAVE_4 = 159;
    /// <summary>
    /// Inflate, WordArt object.
    /// </summary>
    public static final int TEXT_INFLATE = 160;
    /// <summary>
    /// Deflate, WordArt object.
    /// </summary>
    public static final int TEXT_DEFLATE = 161;
    /// <summary>
    /// Inflate bottom, WordArt object.
    /// </summary>
    public static final int TEXT_INFLATE_BOTTOM = 162;
    /// <summary>
    /// Deflate bottom, WordArt object.
    /// </summary>
    public static final int TEXT_DEFLATE_BOTTOM = 163;
    /// <summary>
    /// Inflate top, WordArt object.
    /// </summary>
    public static final int TEXT_INFLATE_TOP = 164;
    /// <summary>
    /// Deflate top, WordArt object.
    /// </summary>
    public static final int TEXT_DEFLATE_TOP = 165;
    /// <summary>
    /// Deflate inflate, WordArt object.
    /// </summary>
    public static final int TEXT_DEFLATE_INFLATE = 166;
    /// <summary>
    /// Deflate inflate deflate, WordArt object.
    /// </summary>
    public static final int TEXT_DEFLATE_INFLATE_DEFLATE = 167;
    /// <summary>
    /// Fade right, WordArt object.
    /// </summary>
    public static final int TEXT_FADE_RIGHT = 168;
    /// <summary>
    /// Fade left, WordArt object.
    /// </summary>
    public static final int TEXT_FADE_LEFT = 169;
    /// <summary>
    /// Fade up, WordArt object.
    /// </summary>
    public static final int TEXT_FADE_UP = 170;
    /// <summary>
    /// Fade down, WordArt object.
    /// </summary>
    public static final int TEXT_FADE_DOWN = 171;
    /// <summary>
    /// Slant up, WordArt object.
    /// </summary>
    public static final int TEXT_SLANT_UP = 172;
    /// <summary>
    /// Slant down, WordArt object.
    /// </summary>
    public static final int TEXT_SLANT_DOWN = 173;
    /// <summary>
    /// Can up, WordArt object.
    /// </summary>
    public static final int TEXT_CAN_UP = 174;
    /// <summary>
    /// Can down, WordArt object.
    /// </summary>
    public static final int TEXT_CAN_DOWN = 175;
    /// <summary>
    /// Flow chart alternate process.
    /// </summary>
    public static final int FLOW_CHART_ALTERNATE_PROCESS = 176;
    /// <summary>
    /// Flow chart off page connector.
    /// </summary>
    public static final int FLOW_CHART_OFFPAGE_CONNECTOR = 177;
    /// <summary>
    /// Callout 90.
    /// </summary>
    public static final int CALLOUT_90 = 178;
    /// <summary>
    /// Accent callout 90.
    /// </summary>
    public static final int ACCENT_CALLOUT_90 = 179;
    /// <summary>
    /// Border callout 90.
    /// </summary>
    public static final int BORDER_CALLOUT_90 = 180;
    /// <summary>
    /// Accent border callout 90.
    /// </summary>
    public static final int ACCENT_BORDER_CALLOUT_90 = 181;
    /// <summary>
    /// Left right up arrow.
    /// </summary>
    public static final int LEFT_RIGHT_UP_ARROW = 182;
    /// <summary>
    /// Sun.
    /// </summary>
    public static final int SUN = 183;
    /// <summary>
    /// Moon.
    /// </summary>
    public static final int MOON = 184;
    /// <summary>
    /// Bracket pair.
    /// </summary>
    public static final int BRACKET_PAIR = 185;
    /// <summary>
    /// Brace pair
    /// </summary>
    public static final int BRACE_PAIR = 186;
    /// <summary>
    /// Four-pointed star.
    /// </summary>
    public static final int SEAL_4 = 187;
    /// <summary>
    /// Double wave.
    /// </summary>
    public static final int DOUBLE_WAVE = 188;
    /// <summary>
    /// Action button blank.
    /// </summary>
    public static final int ACTION_BUTTON_BLANK = 189;
    /// <summary>
    /// Action button home.
    /// </summary>
    public static final int ACTION_BUTTON_HOME = 190;
    /// <summary>
    /// Action button help.
    /// </summary>
    public static final int ACTION_BUTTON_HELP = 191;
    /// <summary>
    /// Action button information.
    /// </summary>
    public static final int ACTION_BUTTON_INFORMATION = 192;
    /// <summary>
    /// Action button forward next.
    /// </summary>
    public static final int ACTION_BUTTON_FORWARD_NEXT = 193;
    /// <summary>
    /// Action button back previous.
    /// </summary>
    public static final int ACTION_BUTTON_BACK_PREVIOUS = 194;
    /// <summary>
    /// Action button end.
    /// </summary>
    public static final int ACTION_BUTTON_END = 195;
    /// <summary>
    /// Action button beginning.
    /// </summary>
    public static final int ACTION_BUTTON_BEGINNING = 196;
    /// <summary>
    /// Action button return.
    /// </summary>
    public static final int ACTION_BUTTON_RETURN = 197;
    /// <summary>
    /// Action button document.
    /// </summary>
    public static final int ACTION_BUTTON_DOCUMENT = 198;
    /// <summary>
    /// Action button sound.
    /// </summary>
    public static final int ACTION_BUTTON_SOUND = 199;
    /// <summary>
    /// Action button movie.
    /// </summary>
    public static final int ACTION_BUTTON_MOVIE = 200;
    /// <summary>
    /// Snip single corner rectangle object.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SINGLE_CORNER_SNIPPED = 203;
    /// <summary>
    /// Snip same side corner rectangle.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int TOP_CORNERS_SNIPPED = 204;
    /// <summary>
    /// Snip diagonal corner rectangle.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int DIAGONAL_CORNERS_SNIPPED = 205;
    /// <summary>
    /// Snip and round single corner rectangle.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED = 206;
    /// <summary>
    /// Round single corner rectangle.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SINGLE_CORNER_ROUNDED = 207;
    /// <summary>
    /// Round same side corner rectangle.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int TOP_CORNERS_ROUNDED = 208;
    /// <summary>
    /// Round diagonal corner rectangle.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int DIAGONAL_CORNERS_ROUNDED = 209;
    /// <summary>
    /// Heptagon.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int HEPTAGON = 210;
    /// <summary>
    /// Cloud.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CLOUD = 211;
    /// <summary>
    /// Six-pointed star.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SEAL_6 = 212;
    /// <summary>
    /// Seven-pointed star.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SEAL_7 = 213;
    /// <summary>
    /// Ten-pointed star.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SEAL_10 = 214;
    /// <summary>
    /// Twelve-pointed star.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SEAL_12 = 215;
    /// <summary>
    /// Swoosh arrow.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SWOOSH_ARROW = 216;
    /// <summary>
    /// Teardrop.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int TEARDROP = 217;
    /// <summary>
    /// Square tabs.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int SQUARE_TABS = 218;
    /// <summary>
    /// Plaque tabs.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int PLAQUE_TABS = 219;
    /// <summary>
    /// Pie.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int PIE = 220;
    /// <summary>
    /// Wedge pie.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int WEDGE_PIE = 221;
    /// <summary>
    /// Inverse line.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int INVERSE_LINE = 222;
    /// <summary>
    /// Math plus.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int MATH_PLUS = 223;
    /// <summary>
    /// Math minus.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int MATH_MINUS = 224;
    /// <summary>
    /// Math multiply.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int MATH_MULTIPLY = 225;
    /// <summary>
    /// Math divide.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int MATH_DIVIDE = 226;
    /// <summary>
    /// Math equal.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int MATH_EQUAL = 227;
    /// <summary>
    /// Math not equal.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int MATH_NOT_EQUAL = 228;
    /// <summary>
    /// Non-isosceles trapezoid.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int NON_ISOSCELES_TRAPEZOID = 229;
    /// <summary>
    /// Left-right circular arrow.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int LEFT_RIGHT_CIRCULAR_ARROW = 230;
    /// <summary>
    /// Left-right ribbon.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int LEFT_RIGHT_RIBBON = 231;
    /// <summary>
    /// Left circular arrow.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int LEFT_CIRCULAR_ARROW = 232;
    /// <summary>
    /// Frame.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int FRAME = 233;
    /// <summary>
    /// Half frame.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int HALF_FRAME = 234;
    /// <summary>
    /// Funnel.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int FUNNEL = 235;
    /// <summary>
    /// Six-tooth gear.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int GEAR_6 = 236;
    /// <summary>
    /// Nine-tooth gear.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int GEAR_9 = 237;
    /// <summary>
    /// Decagon.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int DECAGON = 238;
    /// <summary>
    /// Dodecagon.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int DODECAGON = 239;
    /// <summary>
    /// Diagonal stripe.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int DIAGONAL_STRIPE = 240;
    /// <summary>
    /// Corner.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CORNER = 241;
    /// <summary>
    /// Corner tabs.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CORNER_TABS = 242;
    /// <summary>
    /// Chord.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CHORD = 243;
    /// <summary>
    /// Chart plus.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CHART_PLUS = 244;
    /// <summary>
    /// Chart star.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CHART_STAR = 245;
    /// <summary>
    /// Chart X.
    /// </summary>
    /// <remarks>
    /// Applicable only for DML shapes.
    /// </remarks>
    public static final int CHART_X = 246;
    /// <summary>
    /// Reserved for the system use.
    /// </summary>
    public static final int MIN_VALUE = OLE_OBJECT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ShapeType value.
    /// </summary>
    public static String getName(/*ShapeType*/int shapeType)
    {
    	switch (shapeType)
    	{
    		case IMAGE: return "IMAGE";
    		case TEXT_BOX: return "TEXT_BOX";
    		case GROUP: return "GROUP";
    		case OLE_OBJECT:
    		/*case MIN_VALUE:*/
    			return "OLE_OBJECT" + " | " + "MIN_VALUE";
    		case OLE_CONTROL: return "OLE_CONTROL";
    		case NON_PRIMITIVE: return "NON_PRIMITIVE";
    		case RECTANGLE: return "RECTANGLE";
    		case ROUND_RECTANGLE: return "ROUND_RECTANGLE";
    		case ELLIPSE: return "ELLIPSE";
    		case DIAMOND: return "DIAMOND";
    		case TRIANGLE: return "TRIANGLE";
    		case RIGHT_TRIANGLE: return "RIGHT_TRIANGLE";
    		case PARALLELOGRAM: return "PARALLELOGRAM";
    		case TRAPEZOID: return "TRAPEZOID";
    		case HEXAGON: return "HEXAGON";
    		case OCTAGON: return "OCTAGON";
    		case PLUS: return "PLUS";
    		case STAR: return "STAR";
    		case ARROW: return "ARROW";
    		case THICK_ARROW: return "THICK_ARROW";
    		case HOME_PLATE: return "HOME_PLATE";
    		case CUBE: return "CUBE";
    		case BALLOON: return "BALLOON";
    		case SEAL: return "SEAL";
    		case ARC: return "ARC";
    		case LINE: return "LINE";
    		case PLAQUE: return "PLAQUE";
    		case CAN: return "CAN";
    		case DONUT: return "DONUT";
    		case TEXT_SIMPLE: return "TEXT_SIMPLE";
    		case TEXT_OCTAGON: return "TEXT_OCTAGON";
    		case TEXT_HEXAGON: return "TEXT_HEXAGON";
    		case TEXT_CURVE: return "TEXT_CURVE";
    		case TEXT_WAVE: return "TEXT_WAVE";
    		case TEXT_RING: return "TEXT_RING";
    		case TEXT_ON_CURVE: return "TEXT_ON_CURVE";
    		case TEXT_ON_RING: return "TEXT_ON_RING";
    		case STRAIGHT_CONNECTOR_1: return "STRAIGHT_CONNECTOR_1";
    		case BENT_CONNECTOR_2: return "BENT_CONNECTOR_2";
    		case BENT_CONNECTOR_3: return "BENT_CONNECTOR_3";
    		case BENT_CONNECTOR_4: return "BENT_CONNECTOR_4";
    		case BENT_CONNECTOR_5: return "BENT_CONNECTOR_5";
    		case CURVED_CONNECTOR_2: return "CURVED_CONNECTOR_2";
    		case CURVED_CONNECTOR_3: return "CURVED_CONNECTOR_3";
    		case CURVED_CONNECTOR_4: return "CURVED_CONNECTOR_4";
    		case CURVED_CONNECTOR_5: return "CURVED_CONNECTOR_5";
    		case CALLOUT_1: return "CALLOUT_1";
    		case CALLOUT_2: return "CALLOUT_2";
    		case CALLOUT_3: return "CALLOUT_3";
    		case ACCENT_CALLOUT_1: return "ACCENT_CALLOUT_1";
    		case ACCENT_CALLOUT_2: return "ACCENT_CALLOUT_2";
    		case ACCENT_CALLOUT_3: return "ACCENT_CALLOUT_3";
    		case BORDER_CALLOUT_1: return "BORDER_CALLOUT_1";
    		case BORDER_CALLOUT_2: return "BORDER_CALLOUT_2";
    		case BORDER_CALLOUT_3: return "BORDER_CALLOUT_3";
    		case ACCENT_BORDER_CALLOUT_1: return "ACCENT_BORDER_CALLOUT_1";
    		case ACCENT_BORDER_CALLOUT_2: return "ACCENT_BORDER_CALLOUT_2";
    		case ACCENT_BORDER_CALLOUT_3: return "ACCENT_BORDER_CALLOUT_3";
    		case RIBBON: return "RIBBON";
    		case RIBBON_2: return "RIBBON_2";
    		case CHEVRON: return "CHEVRON";
    		case PENTAGON: return "PENTAGON";
    		case NO_SMOKING: return "NO_SMOKING";
    		case SEAL_8: return "SEAL_8";
    		case SEAL_16: return "SEAL_16";
    		case SEAL_32: return "SEAL_32";
    		case WEDGE_RECT_CALLOUT: return "WEDGE_RECT_CALLOUT";
    		case WEDGE_R_RECT_CALLOUT: return "WEDGE_R_RECT_CALLOUT";
    		case WEDGE_ELLIPSE_CALLOUT: return "WEDGE_ELLIPSE_CALLOUT";
    		case WAVE: return "WAVE";
    		case FOLDED_CORNER: return "FOLDED_CORNER";
    		case LEFT_ARROW: return "LEFT_ARROW";
    		case DOWN_ARROW: return "DOWN_ARROW";
    		case UP_ARROW: return "UP_ARROW";
    		case LEFT_RIGHT_ARROW: return "LEFT_RIGHT_ARROW";
    		case UP_DOWN_ARROW: return "UP_DOWN_ARROW";
    		case IRREGULAR_SEAL_1: return "IRREGULAR_SEAL_1";
    		case IRREGULAR_SEAL_2: return "IRREGULAR_SEAL_2";
    		case LIGHTNING_BOLT: return "LIGHTNING_BOLT";
    		case HEART: return "HEART";
    		case QUAD_ARROW: return "QUAD_ARROW";
    		case LEFT_ARROW_CALLOUT: return "LEFT_ARROW_CALLOUT";
    		case RIGHT_ARROW_CALLOUT: return "RIGHT_ARROW_CALLOUT";
    		case UP_ARROW_CALLOUT: return "UP_ARROW_CALLOUT";
    		case DOWN_ARROW_CALLOUT: return "DOWN_ARROW_CALLOUT";
    		case LEFT_RIGHT_ARROW_CALLOUT: return "LEFT_RIGHT_ARROW_CALLOUT";
    		case UP_DOWN_ARROW_CALLOUT: return "UP_DOWN_ARROW_CALLOUT";
    		case QUAD_ARROW_CALLOUT: return "QUAD_ARROW_CALLOUT";
    		case BEVEL: return "BEVEL";
    		case LEFT_BRACKET: return "LEFT_BRACKET";
    		case RIGHT_BRACKET: return "RIGHT_BRACKET";
    		case LEFT_BRACE: return "LEFT_BRACE";
    		case RIGHT_BRACE: return "RIGHT_BRACE";
    		case LEFT_UP_ARROW: return "LEFT_UP_ARROW";
    		case BENT_UP_ARROW: return "BENT_UP_ARROW";
    		case BENT_ARROW: return "BENT_ARROW";
    		case SEAL_24: return "SEAL_24";
    		case STRIPED_RIGHT_ARROW: return "STRIPED_RIGHT_ARROW";
    		case NOTCHED_RIGHT_ARROW: return "NOTCHED_RIGHT_ARROW";
    		case BLOCK_ARC: return "BLOCK_ARC";
    		case SMILEY_FACE: return "SMILEY_FACE";
    		case VERTICAL_SCROLL: return "VERTICAL_SCROLL";
    		case HORIZONTAL_SCROLL: return "HORIZONTAL_SCROLL";
    		case CIRCULAR_ARROW: return "CIRCULAR_ARROW";
    		case CUSTOM_SHAPE: return "CUSTOM_SHAPE";
    		case UTURN_ARROW: return "UTURN_ARROW";
    		case CURVED_RIGHT_ARROW: return "CURVED_RIGHT_ARROW";
    		case CURVED_LEFT_ARROW: return "CURVED_LEFT_ARROW";
    		case CURVED_UP_ARROW: return "CURVED_UP_ARROW";
    		case CURVED_DOWN_ARROW: return "CURVED_DOWN_ARROW";
    		case CLOUD_CALLOUT: return "CLOUD_CALLOUT";
    		case ELLIPSE_RIBBON: return "ELLIPSE_RIBBON";
    		case ELLIPSE_RIBBON_2: return "ELLIPSE_RIBBON_2";
    		case FLOW_CHART_PROCESS: return "FLOW_CHART_PROCESS";
    		case FLOW_CHART_DECISION: return "FLOW_CHART_DECISION";
    		case FLOW_CHART_INPUT_OUTPUT: return "FLOW_CHART_INPUT_OUTPUT";
    		case FLOW_CHART_PREDEFINED_PROCESS: return "FLOW_CHART_PREDEFINED_PROCESS";
    		case FLOW_CHART_INTERNAL_STORAGE: return "FLOW_CHART_INTERNAL_STORAGE";
    		case FLOW_CHART_DOCUMENT: return "FLOW_CHART_DOCUMENT";
    		case FLOW_CHART_MULTIDOCUMENT: return "FLOW_CHART_MULTIDOCUMENT";
    		case FLOW_CHART_TERMINATOR: return "FLOW_CHART_TERMINATOR";
    		case FLOW_CHART_PREPARATION: return "FLOW_CHART_PREPARATION";
    		case FLOW_CHART_MANUAL_INPUT: return "FLOW_CHART_MANUAL_INPUT";
    		case FLOW_CHART_MANUAL_OPERATION: return "FLOW_CHART_MANUAL_OPERATION";
    		case FLOW_CHART_CONNECTOR: return "FLOW_CHART_CONNECTOR";
    		case FLOW_CHART_PUNCHED_CARD: return "FLOW_CHART_PUNCHED_CARD";
    		case FLOW_CHART_PUNCHED_TAPE: return "FLOW_CHART_PUNCHED_TAPE";
    		case FLOW_CHART_SUMMING_JUNCTION: return "FLOW_CHART_SUMMING_JUNCTION";
    		case FLOW_CHART_OR: return "FLOW_CHART_OR";
    		case FLOW_CHART_COLLATE: return "FLOW_CHART_COLLATE";
    		case FLOW_CHART_SORT: return "FLOW_CHART_SORT";
    		case FLOW_CHART_EXTRACT: return "FLOW_CHART_EXTRACT";
    		case FLOW_CHART_MERGE: return "FLOW_CHART_MERGE";
    		case FLOW_CHART_OFFLINE_STORAGE: return "FLOW_CHART_OFFLINE_STORAGE";
    		case FLOW_CHART_ONLINE_STORAGE: return "FLOW_CHART_ONLINE_STORAGE";
    		case FLOW_CHART_MAGNETIC_TAPE: return "FLOW_CHART_MAGNETIC_TAPE";
    		case FLOW_CHART_MAGNETIC_DISK: return "FLOW_CHART_MAGNETIC_DISK";
    		case FLOW_CHART_MAGNETIC_DRUM: return "FLOW_CHART_MAGNETIC_DRUM";
    		case FLOW_CHART_DISPLAY: return "FLOW_CHART_DISPLAY";
    		case FLOW_CHART_DELAY: return "FLOW_CHART_DELAY";
    		case TEXT_PLAIN_TEXT: return "TEXT_PLAIN_TEXT";
    		case TEXT_STOP: return "TEXT_STOP";
    		case TEXT_TRIANGLE: return "TEXT_TRIANGLE";
    		case TEXT_TRIANGLE_INVERTED: return "TEXT_TRIANGLE_INVERTED";
    		case TEXT_CHEVRON: return "TEXT_CHEVRON";
    		case TEXT_CHEVRON_INVERTED: return "TEXT_CHEVRON_INVERTED";
    		case TEXT_RING_INSIDE: return "TEXT_RING_INSIDE";
    		case TEXT_RING_OUTSIDE: return "TEXT_RING_OUTSIDE";
    		case TEXT_ARCH_UP_CURVE: return "TEXT_ARCH_UP_CURVE";
    		case TEXT_ARCH_DOWN_CURVE: return "TEXT_ARCH_DOWN_CURVE";
    		case TEXT_CIRCLE_CURVE: return "TEXT_CIRCLE_CURVE";
    		case TEXT_BUTTON_CURVE: return "TEXT_BUTTON_CURVE";
    		case TEXT_ARCH_UP_POUR: return "TEXT_ARCH_UP_POUR";
    		case TEXT_ARCH_DOWN_POUR: return "TEXT_ARCH_DOWN_POUR";
    		case TEXT_CIRCLE_POUR: return "TEXT_CIRCLE_POUR";
    		case TEXT_BUTTON_POUR: return "TEXT_BUTTON_POUR";
    		case TEXT_CURVE_UP: return "TEXT_CURVE_UP";
    		case TEXT_CURVE_DOWN: return "TEXT_CURVE_DOWN";
    		case TEXT_CASCADE_UP: return "TEXT_CASCADE_UP";
    		case TEXT_CASCADE_DOWN: return "TEXT_CASCADE_DOWN";
    		case TEXT_WAVE_1: return "TEXT_WAVE_1";
    		case TEXT_WAVE_2: return "TEXT_WAVE_2";
    		case TEXT_WAVE_3: return "TEXT_WAVE_3";
    		case TEXT_WAVE_4: return "TEXT_WAVE_4";
    		case TEXT_INFLATE: return "TEXT_INFLATE";
    		case TEXT_DEFLATE: return "TEXT_DEFLATE";
    		case TEXT_INFLATE_BOTTOM: return "TEXT_INFLATE_BOTTOM";
    		case TEXT_DEFLATE_BOTTOM: return "TEXT_DEFLATE_BOTTOM";
    		case TEXT_INFLATE_TOP: return "TEXT_INFLATE_TOP";
    		case TEXT_DEFLATE_TOP: return "TEXT_DEFLATE_TOP";
    		case TEXT_DEFLATE_INFLATE: return "TEXT_DEFLATE_INFLATE";
    		case TEXT_DEFLATE_INFLATE_DEFLATE: return "TEXT_DEFLATE_INFLATE_DEFLATE";
    		case TEXT_FADE_RIGHT: return "TEXT_FADE_RIGHT";
    		case TEXT_FADE_LEFT: return "TEXT_FADE_LEFT";
    		case TEXT_FADE_UP: return "TEXT_FADE_UP";
    		case TEXT_FADE_DOWN: return "TEXT_FADE_DOWN";
    		case TEXT_SLANT_UP: return "TEXT_SLANT_UP";
    		case TEXT_SLANT_DOWN: return "TEXT_SLANT_DOWN";
    		case TEXT_CAN_UP: return "TEXT_CAN_UP";
    		case TEXT_CAN_DOWN: return "TEXT_CAN_DOWN";
    		case FLOW_CHART_ALTERNATE_PROCESS: return "FLOW_CHART_ALTERNATE_PROCESS";
    		case FLOW_CHART_OFFPAGE_CONNECTOR: return "FLOW_CHART_OFFPAGE_CONNECTOR";
    		case CALLOUT_90: return "CALLOUT_90";
    		case ACCENT_CALLOUT_90: return "ACCENT_CALLOUT_90";
    		case BORDER_CALLOUT_90: return "BORDER_CALLOUT_90";
    		case ACCENT_BORDER_CALLOUT_90: return "ACCENT_BORDER_CALLOUT_90";
    		case LEFT_RIGHT_UP_ARROW: return "LEFT_RIGHT_UP_ARROW";
    		case SUN: return "SUN";
    		case MOON: return "MOON";
    		case BRACKET_PAIR: return "BRACKET_PAIR";
    		case BRACE_PAIR: return "BRACE_PAIR";
    		case SEAL_4: return "SEAL_4";
    		case DOUBLE_WAVE: return "DOUBLE_WAVE";
    		case ACTION_BUTTON_BLANK: return "ACTION_BUTTON_BLANK";
    		case ACTION_BUTTON_HOME: return "ACTION_BUTTON_HOME";
    		case ACTION_BUTTON_HELP: return "ACTION_BUTTON_HELP";
    		case ACTION_BUTTON_INFORMATION: return "ACTION_BUTTON_INFORMATION";
    		case ACTION_BUTTON_FORWARD_NEXT: return "ACTION_BUTTON_FORWARD_NEXT";
    		case ACTION_BUTTON_BACK_PREVIOUS: return "ACTION_BUTTON_BACK_PREVIOUS";
    		case ACTION_BUTTON_END: return "ACTION_BUTTON_END";
    		case ACTION_BUTTON_BEGINNING: return "ACTION_BUTTON_BEGINNING";
    		case ACTION_BUTTON_RETURN: return "ACTION_BUTTON_RETURN";
    		case ACTION_BUTTON_DOCUMENT: return "ACTION_BUTTON_DOCUMENT";
    		case ACTION_BUTTON_SOUND: return "ACTION_BUTTON_SOUND";
    		case ACTION_BUTTON_MOVIE: return "ACTION_BUTTON_MOVIE";
    		case SINGLE_CORNER_SNIPPED: return "SINGLE_CORNER_SNIPPED";
    		case TOP_CORNERS_SNIPPED: return "TOP_CORNERS_SNIPPED";
    		case DIAGONAL_CORNERS_SNIPPED: return "DIAGONAL_CORNERS_SNIPPED";
    		case TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED: return "TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED";
    		case SINGLE_CORNER_ROUNDED: return "SINGLE_CORNER_ROUNDED";
    		case TOP_CORNERS_ROUNDED: return "TOP_CORNERS_ROUNDED";
    		case DIAGONAL_CORNERS_ROUNDED: return "DIAGONAL_CORNERS_ROUNDED";
    		case HEPTAGON: return "HEPTAGON";
    		case CLOUD: return "CLOUD";
    		case SEAL_6: return "SEAL_6";
    		case SEAL_7: return "SEAL_7";
    		case SEAL_10: return "SEAL_10";
    		case SEAL_12: return "SEAL_12";
    		case SWOOSH_ARROW: return "SWOOSH_ARROW";
    		case TEARDROP: return "TEARDROP";
    		case SQUARE_TABS: return "SQUARE_TABS";
    		case PLAQUE_TABS: return "PLAQUE_TABS";
    		case PIE: return "PIE";
    		case WEDGE_PIE: return "WEDGE_PIE";
    		case INVERSE_LINE: return "INVERSE_LINE";
    		case MATH_PLUS: return "MATH_PLUS";
    		case MATH_MINUS: return "MATH_MINUS";
    		case MATH_MULTIPLY: return "MATH_MULTIPLY";
    		case MATH_DIVIDE: return "MATH_DIVIDE";
    		case MATH_EQUAL: return "MATH_EQUAL";
    		case MATH_NOT_EQUAL: return "MATH_NOT_EQUAL";
    		case NON_ISOSCELES_TRAPEZOID: return "NON_ISOSCELES_TRAPEZOID";
    		case LEFT_RIGHT_CIRCULAR_ARROW: return "LEFT_RIGHT_CIRCULAR_ARROW";
    		case LEFT_RIGHT_RIBBON: return "LEFT_RIGHT_RIBBON";
    		case LEFT_CIRCULAR_ARROW: return "LEFT_CIRCULAR_ARROW";
    		case FRAME: return "FRAME";
    		case HALF_FRAME: return "HALF_FRAME";
    		case FUNNEL: return "FUNNEL";
    		case GEAR_6: return "GEAR_6";
    		case GEAR_9: return "GEAR_9";
    		case DECAGON: return "DECAGON";
    		case DODECAGON: return "DODECAGON";
    		case DIAGONAL_STRIPE: return "DIAGONAL_STRIPE";
    		case CORNER: return "CORNER";
    		case CORNER_TABS: return "CORNER_TABS";
    		case CHORD: return "CHORD";
    		case CHART_PLUS: return "CHART_PLUS";
    		case CHART_STAR: return "CHART_STAR";
    		case CHART_X: return "CHART_X";
    		default: return "Unknown ShapeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ShapeType value.
    /// </summary>
    public static String toString(/*ShapeType*/int shapeType)
    {
    	switch (shapeType)
    	{
    		case IMAGE: return "Image";
    		case TEXT_BOX: return "TextBox";
    		case GROUP: return "Group";
    		case OLE_OBJECT:
    		/*case MIN_VALUE:*/
    			return "OleObject" + " | " + "MinValue";
    		case OLE_CONTROL: return "OleControl";
    		case NON_PRIMITIVE: return "NonPrimitive";
    		case RECTANGLE: return "Rectangle";
    		case ROUND_RECTANGLE: return "RoundRectangle";
    		case ELLIPSE: return "Ellipse";
    		case DIAMOND: return "Diamond";
    		case TRIANGLE: return "Triangle";
    		case RIGHT_TRIANGLE: return "RightTriangle";
    		case PARALLELOGRAM: return "Parallelogram";
    		case TRAPEZOID: return "Trapezoid";
    		case HEXAGON: return "Hexagon";
    		case OCTAGON: return "Octagon";
    		case PLUS: return "Plus";
    		case STAR: return "Star";
    		case ARROW: return "Arrow";
    		case THICK_ARROW: return "ThickArrow";
    		case HOME_PLATE: return "HomePlate";
    		case CUBE: return "Cube";
    		case BALLOON: return "Balloon";
    		case SEAL: return "Seal";
    		case ARC: return "Arc";
    		case LINE: return "Line";
    		case PLAQUE: return "Plaque";
    		case CAN: return "Can";
    		case DONUT: return "Donut";
    		case TEXT_SIMPLE: return "TextSimple";
    		case TEXT_OCTAGON: return "TextOctagon";
    		case TEXT_HEXAGON: return "TextHexagon";
    		case TEXT_CURVE: return "TextCurve";
    		case TEXT_WAVE: return "TextWave";
    		case TEXT_RING: return "TextRing";
    		case TEXT_ON_CURVE: return "TextOnCurve";
    		case TEXT_ON_RING: return "TextOnRing";
    		case STRAIGHT_CONNECTOR_1: return "StraightConnector1";
    		case BENT_CONNECTOR_2: return "BentConnector2";
    		case BENT_CONNECTOR_3: return "BentConnector3";
    		case BENT_CONNECTOR_4: return "BentConnector4";
    		case BENT_CONNECTOR_5: return "BentConnector5";
    		case CURVED_CONNECTOR_2: return "CurvedConnector2";
    		case CURVED_CONNECTOR_3: return "CurvedConnector3";
    		case CURVED_CONNECTOR_4: return "CurvedConnector4";
    		case CURVED_CONNECTOR_5: return "CurvedConnector5";
    		case CALLOUT_1: return "Callout1";
    		case CALLOUT_2: return "Callout2";
    		case CALLOUT_3: return "Callout3";
    		case ACCENT_CALLOUT_1: return "AccentCallout1";
    		case ACCENT_CALLOUT_2: return "AccentCallout2";
    		case ACCENT_CALLOUT_3: return "AccentCallout3";
    		case BORDER_CALLOUT_1: return "BorderCallout1";
    		case BORDER_CALLOUT_2: return "BorderCallout2";
    		case BORDER_CALLOUT_3: return "BorderCallout3";
    		case ACCENT_BORDER_CALLOUT_1: return "AccentBorderCallout1";
    		case ACCENT_BORDER_CALLOUT_2: return "AccentBorderCallout2";
    		case ACCENT_BORDER_CALLOUT_3: return "AccentBorderCallout3";
    		case RIBBON: return "Ribbon";
    		case RIBBON_2: return "Ribbon2";
    		case CHEVRON: return "Chevron";
    		case PENTAGON: return "Pentagon";
    		case NO_SMOKING: return "NoSmoking";
    		case SEAL_8: return "Seal8";
    		case SEAL_16: return "Seal16";
    		case SEAL_32: return "Seal32";
    		case WEDGE_RECT_CALLOUT: return "WedgeRectCallout";
    		case WEDGE_R_RECT_CALLOUT: return "WedgeRRectCallout";
    		case WEDGE_ELLIPSE_CALLOUT: return "WedgeEllipseCallout";
    		case WAVE: return "Wave";
    		case FOLDED_CORNER: return "FoldedCorner";
    		case LEFT_ARROW: return "LeftArrow";
    		case DOWN_ARROW: return "DownArrow";
    		case UP_ARROW: return "UpArrow";
    		case LEFT_RIGHT_ARROW: return "LeftRightArrow";
    		case UP_DOWN_ARROW: return "UpDownArrow";
    		case IRREGULAR_SEAL_1: return "IrregularSeal1";
    		case IRREGULAR_SEAL_2: return "IrregularSeal2";
    		case LIGHTNING_BOLT: return "LightningBolt";
    		case HEART: return "Heart";
    		case QUAD_ARROW: return "QuadArrow";
    		case LEFT_ARROW_CALLOUT: return "LeftArrowCallout";
    		case RIGHT_ARROW_CALLOUT: return "RightArrowCallout";
    		case UP_ARROW_CALLOUT: return "UpArrowCallout";
    		case DOWN_ARROW_CALLOUT: return "DownArrowCallout";
    		case LEFT_RIGHT_ARROW_CALLOUT: return "LeftRightArrowCallout";
    		case UP_DOWN_ARROW_CALLOUT: return "UpDownArrowCallout";
    		case QUAD_ARROW_CALLOUT: return "QuadArrowCallout";
    		case BEVEL: return "Bevel";
    		case LEFT_BRACKET: return "LeftBracket";
    		case RIGHT_BRACKET: return "RightBracket";
    		case LEFT_BRACE: return "LeftBrace";
    		case RIGHT_BRACE: return "RightBrace";
    		case LEFT_UP_ARROW: return "LeftUpArrow";
    		case BENT_UP_ARROW: return "BentUpArrow";
    		case BENT_ARROW: return "BentArrow";
    		case SEAL_24: return "Seal24";
    		case STRIPED_RIGHT_ARROW: return "StripedRightArrow";
    		case NOTCHED_RIGHT_ARROW: return "NotchedRightArrow";
    		case BLOCK_ARC: return "BlockArc";
    		case SMILEY_FACE: return "SmileyFace";
    		case VERTICAL_SCROLL: return "VerticalScroll";
    		case HORIZONTAL_SCROLL: return "HorizontalScroll";
    		case CIRCULAR_ARROW: return "CircularArrow";
    		case CUSTOM_SHAPE: return "CustomShape";
    		case UTURN_ARROW: return "UturnArrow";
    		case CURVED_RIGHT_ARROW: return "CurvedRightArrow";
    		case CURVED_LEFT_ARROW: return "CurvedLeftArrow";
    		case CURVED_UP_ARROW: return "CurvedUpArrow";
    		case CURVED_DOWN_ARROW: return "CurvedDownArrow";
    		case CLOUD_CALLOUT: return "CloudCallout";
    		case ELLIPSE_RIBBON: return "EllipseRibbon";
    		case ELLIPSE_RIBBON_2: return "EllipseRibbon2";
    		case FLOW_CHART_PROCESS: return "FlowChartProcess";
    		case FLOW_CHART_DECISION: return "FlowChartDecision";
    		case FLOW_CHART_INPUT_OUTPUT: return "FlowChartInputOutput";
    		case FLOW_CHART_PREDEFINED_PROCESS: return "FlowChartPredefinedProcess";
    		case FLOW_CHART_INTERNAL_STORAGE: return "FlowChartInternalStorage";
    		case FLOW_CHART_DOCUMENT: return "FlowChartDocument";
    		case FLOW_CHART_MULTIDOCUMENT: return "FlowChartMultidocument";
    		case FLOW_CHART_TERMINATOR: return "FlowChartTerminator";
    		case FLOW_CHART_PREPARATION: return "FlowChartPreparation";
    		case FLOW_CHART_MANUAL_INPUT: return "FlowChartManualInput";
    		case FLOW_CHART_MANUAL_OPERATION: return "FlowChartManualOperation";
    		case FLOW_CHART_CONNECTOR: return "FlowChartConnector";
    		case FLOW_CHART_PUNCHED_CARD: return "FlowChartPunchedCard";
    		case FLOW_CHART_PUNCHED_TAPE: return "FlowChartPunchedTape";
    		case FLOW_CHART_SUMMING_JUNCTION: return "FlowChartSummingJunction";
    		case FLOW_CHART_OR: return "FlowChartOr";
    		case FLOW_CHART_COLLATE: return "FlowChartCollate";
    		case FLOW_CHART_SORT: return "FlowChartSort";
    		case FLOW_CHART_EXTRACT: return "FlowChartExtract";
    		case FLOW_CHART_MERGE: return "FlowChartMerge";
    		case FLOW_CHART_OFFLINE_STORAGE: return "FlowChartOfflineStorage";
    		case FLOW_CHART_ONLINE_STORAGE: return "FlowChartOnlineStorage";
    		case FLOW_CHART_MAGNETIC_TAPE: return "FlowChartMagneticTape";
    		case FLOW_CHART_MAGNETIC_DISK: return "FlowChartMagneticDisk";
    		case FLOW_CHART_MAGNETIC_DRUM: return "FlowChartMagneticDrum";
    		case FLOW_CHART_DISPLAY: return "FlowChartDisplay";
    		case FLOW_CHART_DELAY: return "FlowChartDelay";
    		case TEXT_PLAIN_TEXT: return "TextPlainText";
    		case TEXT_STOP: return "TextStop";
    		case TEXT_TRIANGLE: return "TextTriangle";
    		case TEXT_TRIANGLE_INVERTED: return "TextTriangleInverted";
    		case TEXT_CHEVRON: return "TextChevron";
    		case TEXT_CHEVRON_INVERTED: return "TextChevronInverted";
    		case TEXT_RING_INSIDE: return "TextRingInside";
    		case TEXT_RING_OUTSIDE: return "TextRingOutside";
    		case TEXT_ARCH_UP_CURVE: return "TextArchUpCurve";
    		case TEXT_ARCH_DOWN_CURVE: return "TextArchDownCurve";
    		case TEXT_CIRCLE_CURVE: return "TextCircleCurve";
    		case TEXT_BUTTON_CURVE: return "TextButtonCurve";
    		case TEXT_ARCH_UP_POUR: return "TextArchUpPour";
    		case TEXT_ARCH_DOWN_POUR: return "TextArchDownPour";
    		case TEXT_CIRCLE_POUR: return "TextCirclePour";
    		case TEXT_BUTTON_POUR: return "TextButtonPour";
    		case TEXT_CURVE_UP: return "TextCurveUp";
    		case TEXT_CURVE_DOWN: return "TextCurveDown";
    		case TEXT_CASCADE_UP: return "TextCascadeUp";
    		case TEXT_CASCADE_DOWN: return "TextCascadeDown";
    		case TEXT_WAVE_1: return "TextWave1";
    		case TEXT_WAVE_2: return "TextWave2";
    		case TEXT_WAVE_3: return "TextWave3";
    		case TEXT_WAVE_4: return "TextWave4";
    		case TEXT_INFLATE: return "TextInflate";
    		case TEXT_DEFLATE: return "TextDeflate";
    		case TEXT_INFLATE_BOTTOM: return "TextInflateBottom";
    		case TEXT_DEFLATE_BOTTOM: return "TextDeflateBottom";
    		case TEXT_INFLATE_TOP: return "TextInflateTop";
    		case TEXT_DEFLATE_TOP: return "TextDeflateTop";
    		case TEXT_DEFLATE_INFLATE: return "TextDeflateInflate";
    		case TEXT_DEFLATE_INFLATE_DEFLATE: return "TextDeflateInflateDeflate";
    		case TEXT_FADE_RIGHT: return "TextFadeRight";
    		case TEXT_FADE_LEFT: return "TextFadeLeft";
    		case TEXT_FADE_UP: return "TextFadeUp";
    		case TEXT_FADE_DOWN: return "TextFadeDown";
    		case TEXT_SLANT_UP: return "TextSlantUp";
    		case TEXT_SLANT_DOWN: return "TextSlantDown";
    		case TEXT_CAN_UP: return "TextCanUp";
    		case TEXT_CAN_DOWN: return "TextCanDown";
    		case FLOW_CHART_ALTERNATE_PROCESS: return "FlowChartAlternateProcess";
    		case FLOW_CHART_OFFPAGE_CONNECTOR: return "FlowChartOffpageConnector";
    		case CALLOUT_90: return "Callout90";
    		case ACCENT_CALLOUT_90: return "AccentCallout90";
    		case BORDER_CALLOUT_90: return "BorderCallout90";
    		case ACCENT_BORDER_CALLOUT_90: return "AccentBorderCallout90";
    		case LEFT_RIGHT_UP_ARROW: return "LeftRightUpArrow";
    		case SUN: return "Sun";
    		case MOON: return "Moon";
    		case BRACKET_PAIR: return "BracketPair";
    		case BRACE_PAIR: return "BracePair";
    		case SEAL_4: return "Seal4";
    		case DOUBLE_WAVE: return "DoubleWave";
    		case ACTION_BUTTON_BLANK: return "ActionButtonBlank";
    		case ACTION_BUTTON_HOME: return "ActionButtonHome";
    		case ACTION_BUTTON_HELP: return "ActionButtonHelp";
    		case ACTION_BUTTON_INFORMATION: return "ActionButtonInformation";
    		case ACTION_BUTTON_FORWARD_NEXT: return "ActionButtonForwardNext";
    		case ACTION_BUTTON_BACK_PREVIOUS: return "ActionButtonBackPrevious";
    		case ACTION_BUTTON_END: return "ActionButtonEnd";
    		case ACTION_BUTTON_BEGINNING: return "ActionButtonBeginning";
    		case ACTION_BUTTON_RETURN: return "ActionButtonReturn";
    		case ACTION_BUTTON_DOCUMENT: return "ActionButtonDocument";
    		case ACTION_BUTTON_SOUND: return "ActionButtonSound";
    		case ACTION_BUTTON_MOVIE: return "ActionButtonMovie";
    		case SINGLE_CORNER_SNIPPED: return "SingleCornerSnipped";
    		case TOP_CORNERS_SNIPPED: return "TopCornersSnipped";
    		case DIAGONAL_CORNERS_SNIPPED: return "DiagonalCornersSnipped";
    		case TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED: return "TopCornersOneRoundedOneSnipped";
    		case SINGLE_CORNER_ROUNDED: return "SingleCornerRounded";
    		case TOP_CORNERS_ROUNDED: return "TopCornersRounded";
    		case DIAGONAL_CORNERS_ROUNDED: return "DiagonalCornersRounded";
    		case HEPTAGON: return "Heptagon";
    		case CLOUD: return "Cloud";
    		case SEAL_6: return "Seal6";
    		case SEAL_7: return "Seal7";
    		case SEAL_10: return "Seal10";
    		case SEAL_12: return "Seal12";
    		case SWOOSH_ARROW: return "SwooshArrow";
    		case TEARDROP: return "Teardrop";
    		case SQUARE_TABS: return "SquareTabs";
    		case PLAQUE_TABS: return "PlaqueTabs";
    		case PIE: return "Pie";
    		case WEDGE_PIE: return "WedgePie";
    		case INVERSE_LINE: return "InverseLine";
    		case MATH_PLUS: return "MathPlus";
    		case MATH_MINUS: return "MathMinus";
    		case MATH_MULTIPLY: return "MathMultiply";
    		case MATH_DIVIDE: return "MathDivide";
    		case MATH_EQUAL: return "MathEqual";
    		case MATH_NOT_EQUAL: return "MathNotEqual";
    		case NON_ISOSCELES_TRAPEZOID: return "NonIsoscelesTrapezoid";
    		case LEFT_RIGHT_CIRCULAR_ARROW: return "LeftRightCircularArrow";
    		case LEFT_RIGHT_RIBBON: return "LeftRightRibbon";
    		case LEFT_CIRCULAR_ARROW: return "LeftCircularArrow";
    		case FRAME: return "Frame";
    		case HALF_FRAME: return "HalfFrame";
    		case FUNNEL: return "Funnel";
    		case GEAR_6: return "Gear6";
    		case GEAR_9: return "Gear9";
    		case DECAGON: return "Decagon";
    		case DODECAGON: return "Dodecagon";
    		case DIAGONAL_STRIPE: return "DiagonalStripe";
    		case CORNER: return "Corner";
    		case CORNER_TABS: return "CornerTabs";
    		case CHORD: return "Chord";
    		case CHART_PLUS: return "ChartPlus";
    		case CHART_STAR: return "ChartStar";
    		case CHART_X: return "ChartX";
    		default: return "Unknown ShapeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ShapeType name.
    /// </summary>
    public static /*ShapeType*/int fromName(String shapeTypeName)
    {
    	if ("IMAGE".equals(shapeTypeName)) return IMAGE;
    	if ("TEXT_BOX".equals(shapeTypeName)) return TEXT_BOX;
    	if ("GROUP".equals(shapeTypeName)) return GROUP;
    	if ("OLE_OBJECT".equals(shapeTypeName)) return OLE_OBJECT;
    	if ("OLE_CONTROL".equals(shapeTypeName)) return OLE_CONTROL;
    	if ("NON_PRIMITIVE".equals(shapeTypeName)) return NON_PRIMITIVE;
    	if ("RECTANGLE".equals(shapeTypeName)) return RECTANGLE;
    	if ("ROUND_RECTANGLE".equals(shapeTypeName)) return ROUND_RECTANGLE;
    	if ("ELLIPSE".equals(shapeTypeName)) return ELLIPSE;
    	if ("DIAMOND".equals(shapeTypeName)) return DIAMOND;
    	if ("TRIANGLE".equals(shapeTypeName)) return TRIANGLE;
    	if ("RIGHT_TRIANGLE".equals(shapeTypeName)) return RIGHT_TRIANGLE;
    	if ("PARALLELOGRAM".equals(shapeTypeName)) return PARALLELOGRAM;
    	if ("TRAPEZOID".equals(shapeTypeName)) return TRAPEZOID;
    	if ("HEXAGON".equals(shapeTypeName)) return HEXAGON;
    	if ("OCTAGON".equals(shapeTypeName)) return OCTAGON;
    	if ("PLUS".equals(shapeTypeName)) return PLUS;
    	if ("STAR".equals(shapeTypeName)) return STAR;
    	if ("ARROW".equals(shapeTypeName)) return ARROW;
    	if ("THICK_ARROW".equals(shapeTypeName)) return THICK_ARROW;
    	if ("HOME_PLATE".equals(shapeTypeName)) return HOME_PLATE;
    	if ("CUBE".equals(shapeTypeName)) return CUBE;
    	if ("BALLOON".equals(shapeTypeName)) return BALLOON;
    	if ("SEAL".equals(shapeTypeName)) return SEAL;
    	if ("ARC".equals(shapeTypeName)) return ARC;
    	if ("LINE".equals(shapeTypeName)) return LINE;
    	if ("PLAQUE".equals(shapeTypeName)) return PLAQUE;
    	if ("CAN".equals(shapeTypeName)) return CAN;
    	if ("DONUT".equals(shapeTypeName)) return DONUT;
    	if ("TEXT_SIMPLE".equals(shapeTypeName)) return TEXT_SIMPLE;
    	if ("TEXT_OCTAGON".equals(shapeTypeName)) return TEXT_OCTAGON;
    	if ("TEXT_HEXAGON".equals(shapeTypeName)) return TEXT_HEXAGON;
    	if ("TEXT_CURVE".equals(shapeTypeName)) return TEXT_CURVE;
    	if ("TEXT_WAVE".equals(shapeTypeName)) return TEXT_WAVE;
    	if ("TEXT_RING".equals(shapeTypeName)) return TEXT_RING;
    	if ("TEXT_ON_CURVE".equals(shapeTypeName)) return TEXT_ON_CURVE;
    	if ("TEXT_ON_RING".equals(shapeTypeName)) return TEXT_ON_RING;
    	if ("STRAIGHT_CONNECTOR_1".equals(shapeTypeName)) return STRAIGHT_CONNECTOR_1;
    	if ("BENT_CONNECTOR_2".equals(shapeTypeName)) return BENT_CONNECTOR_2;
    	if ("BENT_CONNECTOR_3".equals(shapeTypeName)) return BENT_CONNECTOR_3;
    	if ("BENT_CONNECTOR_4".equals(shapeTypeName)) return BENT_CONNECTOR_4;
    	if ("BENT_CONNECTOR_5".equals(shapeTypeName)) return BENT_CONNECTOR_5;
    	if ("CURVED_CONNECTOR_2".equals(shapeTypeName)) return CURVED_CONNECTOR_2;
    	if ("CURVED_CONNECTOR_3".equals(shapeTypeName)) return CURVED_CONNECTOR_3;
    	if ("CURVED_CONNECTOR_4".equals(shapeTypeName)) return CURVED_CONNECTOR_4;
    	if ("CURVED_CONNECTOR_5".equals(shapeTypeName)) return CURVED_CONNECTOR_5;
    	if ("CALLOUT_1".equals(shapeTypeName)) return CALLOUT_1;
    	if ("CALLOUT_2".equals(shapeTypeName)) return CALLOUT_2;
    	if ("CALLOUT_3".equals(shapeTypeName)) return CALLOUT_3;
    	if ("ACCENT_CALLOUT_1".equals(shapeTypeName)) return ACCENT_CALLOUT_1;
    	if ("ACCENT_CALLOUT_2".equals(shapeTypeName)) return ACCENT_CALLOUT_2;
    	if ("ACCENT_CALLOUT_3".equals(shapeTypeName)) return ACCENT_CALLOUT_3;
    	if ("BORDER_CALLOUT_1".equals(shapeTypeName)) return BORDER_CALLOUT_1;
    	if ("BORDER_CALLOUT_2".equals(shapeTypeName)) return BORDER_CALLOUT_2;
    	if ("BORDER_CALLOUT_3".equals(shapeTypeName)) return BORDER_CALLOUT_3;
    	if ("ACCENT_BORDER_CALLOUT_1".equals(shapeTypeName)) return ACCENT_BORDER_CALLOUT_1;
    	if ("ACCENT_BORDER_CALLOUT_2".equals(shapeTypeName)) return ACCENT_BORDER_CALLOUT_2;
    	if ("ACCENT_BORDER_CALLOUT_3".equals(shapeTypeName)) return ACCENT_BORDER_CALLOUT_3;
    	if ("RIBBON".equals(shapeTypeName)) return RIBBON;
    	if ("RIBBON_2".equals(shapeTypeName)) return RIBBON_2;
    	if ("CHEVRON".equals(shapeTypeName)) return CHEVRON;
    	if ("PENTAGON".equals(shapeTypeName)) return PENTAGON;
    	if ("NO_SMOKING".equals(shapeTypeName)) return NO_SMOKING;
    	if ("SEAL_8".equals(shapeTypeName)) return SEAL_8;
    	if ("SEAL_16".equals(shapeTypeName)) return SEAL_16;
    	if ("SEAL_32".equals(shapeTypeName)) return SEAL_32;
    	if ("WEDGE_RECT_CALLOUT".equals(shapeTypeName)) return WEDGE_RECT_CALLOUT;
    	if ("WEDGE_R_RECT_CALLOUT".equals(shapeTypeName)) return WEDGE_R_RECT_CALLOUT;
    	if ("WEDGE_ELLIPSE_CALLOUT".equals(shapeTypeName)) return WEDGE_ELLIPSE_CALLOUT;
    	if ("WAVE".equals(shapeTypeName)) return WAVE;
    	if ("FOLDED_CORNER".equals(shapeTypeName)) return FOLDED_CORNER;
    	if ("LEFT_ARROW".equals(shapeTypeName)) return LEFT_ARROW;
    	if ("DOWN_ARROW".equals(shapeTypeName)) return DOWN_ARROW;
    	if ("UP_ARROW".equals(shapeTypeName)) return UP_ARROW;
    	if ("LEFT_RIGHT_ARROW".equals(shapeTypeName)) return LEFT_RIGHT_ARROW;
    	if ("UP_DOWN_ARROW".equals(shapeTypeName)) return UP_DOWN_ARROW;
    	if ("IRREGULAR_SEAL_1".equals(shapeTypeName)) return IRREGULAR_SEAL_1;
    	if ("IRREGULAR_SEAL_2".equals(shapeTypeName)) return IRREGULAR_SEAL_2;
    	if ("LIGHTNING_BOLT".equals(shapeTypeName)) return LIGHTNING_BOLT;
    	if ("HEART".equals(shapeTypeName)) return HEART;
    	if ("QUAD_ARROW".equals(shapeTypeName)) return QUAD_ARROW;
    	if ("LEFT_ARROW_CALLOUT".equals(shapeTypeName)) return LEFT_ARROW_CALLOUT;
    	if ("RIGHT_ARROW_CALLOUT".equals(shapeTypeName)) return RIGHT_ARROW_CALLOUT;
    	if ("UP_ARROW_CALLOUT".equals(shapeTypeName)) return UP_ARROW_CALLOUT;
    	if ("DOWN_ARROW_CALLOUT".equals(shapeTypeName)) return DOWN_ARROW_CALLOUT;
    	if ("LEFT_RIGHT_ARROW_CALLOUT".equals(shapeTypeName)) return LEFT_RIGHT_ARROW_CALLOUT;
    	if ("UP_DOWN_ARROW_CALLOUT".equals(shapeTypeName)) return UP_DOWN_ARROW_CALLOUT;
    	if ("QUAD_ARROW_CALLOUT".equals(shapeTypeName)) return QUAD_ARROW_CALLOUT;
    	if ("BEVEL".equals(shapeTypeName)) return BEVEL;
    	if ("LEFT_BRACKET".equals(shapeTypeName)) return LEFT_BRACKET;
    	if ("RIGHT_BRACKET".equals(shapeTypeName)) return RIGHT_BRACKET;
    	if ("LEFT_BRACE".equals(shapeTypeName)) return LEFT_BRACE;
    	if ("RIGHT_BRACE".equals(shapeTypeName)) return RIGHT_BRACE;
    	if ("LEFT_UP_ARROW".equals(shapeTypeName)) return LEFT_UP_ARROW;
    	if ("BENT_UP_ARROW".equals(shapeTypeName)) return BENT_UP_ARROW;
    	if ("BENT_ARROW".equals(shapeTypeName)) return BENT_ARROW;
    	if ("SEAL_24".equals(shapeTypeName)) return SEAL_24;
    	if ("STRIPED_RIGHT_ARROW".equals(shapeTypeName)) return STRIPED_RIGHT_ARROW;
    	if ("NOTCHED_RIGHT_ARROW".equals(shapeTypeName)) return NOTCHED_RIGHT_ARROW;
    	if ("BLOCK_ARC".equals(shapeTypeName)) return BLOCK_ARC;
    	if ("SMILEY_FACE".equals(shapeTypeName)) return SMILEY_FACE;
    	if ("VERTICAL_SCROLL".equals(shapeTypeName)) return VERTICAL_SCROLL;
    	if ("HORIZONTAL_SCROLL".equals(shapeTypeName)) return HORIZONTAL_SCROLL;
    	if ("CIRCULAR_ARROW".equals(shapeTypeName)) return CIRCULAR_ARROW;
    	if ("CUSTOM_SHAPE".equals(shapeTypeName)) return CUSTOM_SHAPE;
    	if ("UTURN_ARROW".equals(shapeTypeName)) return UTURN_ARROW;
    	if ("CURVED_RIGHT_ARROW".equals(shapeTypeName)) return CURVED_RIGHT_ARROW;
    	if ("CURVED_LEFT_ARROW".equals(shapeTypeName)) return CURVED_LEFT_ARROW;
    	if ("CURVED_UP_ARROW".equals(shapeTypeName)) return CURVED_UP_ARROW;
    	if ("CURVED_DOWN_ARROW".equals(shapeTypeName)) return CURVED_DOWN_ARROW;
    	if ("CLOUD_CALLOUT".equals(shapeTypeName)) return CLOUD_CALLOUT;
    	if ("ELLIPSE_RIBBON".equals(shapeTypeName)) return ELLIPSE_RIBBON;
    	if ("ELLIPSE_RIBBON_2".equals(shapeTypeName)) return ELLIPSE_RIBBON_2;
    	if ("FLOW_CHART_PROCESS".equals(shapeTypeName)) return FLOW_CHART_PROCESS;
    	if ("FLOW_CHART_DECISION".equals(shapeTypeName)) return FLOW_CHART_DECISION;
    	if ("FLOW_CHART_INPUT_OUTPUT".equals(shapeTypeName)) return FLOW_CHART_INPUT_OUTPUT;
    	if ("FLOW_CHART_PREDEFINED_PROCESS".equals(shapeTypeName)) return FLOW_CHART_PREDEFINED_PROCESS;
    	if ("FLOW_CHART_INTERNAL_STORAGE".equals(shapeTypeName)) return FLOW_CHART_INTERNAL_STORAGE;
    	if ("FLOW_CHART_DOCUMENT".equals(shapeTypeName)) return FLOW_CHART_DOCUMENT;
    	if ("FLOW_CHART_MULTIDOCUMENT".equals(shapeTypeName)) return FLOW_CHART_MULTIDOCUMENT;
    	if ("FLOW_CHART_TERMINATOR".equals(shapeTypeName)) return FLOW_CHART_TERMINATOR;
    	if ("FLOW_CHART_PREPARATION".equals(shapeTypeName)) return FLOW_CHART_PREPARATION;
    	if ("FLOW_CHART_MANUAL_INPUT".equals(shapeTypeName)) return FLOW_CHART_MANUAL_INPUT;
    	if ("FLOW_CHART_MANUAL_OPERATION".equals(shapeTypeName)) return FLOW_CHART_MANUAL_OPERATION;
    	if ("FLOW_CHART_CONNECTOR".equals(shapeTypeName)) return FLOW_CHART_CONNECTOR;
    	if ("FLOW_CHART_PUNCHED_CARD".equals(shapeTypeName)) return FLOW_CHART_PUNCHED_CARD;
    	if ("FLOW_CHART_PUNCHED_TAPE".equals(shapeTypeName)) return FLOW_CHART_PUNCHED_TAPE;
    	if ("FLOW_CHART_SUMMING_JUNCTION".equals(shapeTypeName)) return FLOW_CHART_SUMMING_JUNCTION;
    	if ("FLOW_CHART_OR".equals(shapeTypeName)) return FLOW_CHART_OR;
    	if ("FLOW_CHART_COLLATE".equals(shapeTypeName)) return FLOW_CHART_COLLATE;
    	if ("FLOW_CHART_SORT".equals(shapeTypeName)) return FLOW_CHART_SORT;
    	if ("FLOW_CHART_EXTRACT".equals(shapeTypeName)) return FLOW_CHART_EXTRACT;
    	if ("FLOW_CHART_MERGE".equals(shapeTypeName)) return FLOW_CHART_MERGE;
    	if ("FLOW_CHART_OFFLINE_STORAGE".equals(shapeTypeName)) return FLOW_CHART_OFFLINE_STORAGE;
    	if ("FLOW_CHART_ONLINE_STORAGE".equals(shapeTypeName)) return FLOW_CHART_ONLINE_STORAGE;
    	if ("FLOW_CHART_MAGNETIC_TAPE".equals(shapeTypeName)) return FLOW_CHART_MAGNETIC_TAPE;
    	if ("FLOW_CHART_MAGNETIC_DISK".equals(shapeTypeName)) return FLOW_CHART_MAGNETIC_DISK;
    	if ("FLOW_CHART_MAGNETIC_DRUM".equals(shapeTypeName)) return FLOW_CHART_MAGNETIC_DRUM;
    	if ("FLOW_CHART_DISPLAY".equals(shapeTypeName)) return FLOW_CHART_DISPLAY;
    	if ("FLOW_CHART_DELAY".equals(shapeTypeName)) return FLOW_CHART_DELAY;
    	if ("TEXT_PLAIN_TEXT".equals(shapeTypeName)) return TEXT_PLAIN_TEXT;
    	if ("TEXT_STOP".equals(shapeTypeName)) return TEXT_STOP;
    	if ("TEXT_TRIANGLE".equals(shapeTypeName)) return TEXT_TRIANGLE;
    	if ("TEXT_TRIANGLE_INVERTED".equals(shapeTypeName)) return TEXT_TRIANGLE_INVERTED;
    	if ("TEXT_CHEVRON".equals(shapeTypeName)) return TEXT_CHEVRON;
    	if ("TEXT_CHEVRON_INVERTED".equals(shapeTypeName)) return TEXT_CHEVRON_INVERTED;
    	if ("TEXT_RING_INSIDE".equals(shapeTypeName)) return TEXT_RING_INSIDE;
    	if ("TEXT_RING_OUTSIDE".equals(shapeTypeName)) return TEXT_RING_OUTSIDE;
    	if ("TEXT_ARCH_UP_CURVE".equals(shapeTypeName)) return TEXT_ARCH_UP_CURVE;
    	if ("TEXT_ARCH_DOWN_CURVE".equals(shapeTypeName)) return TEXT_ARCH_DOWN_CURVE;
    	if ("TEXT_CIRCLE_CURVE".equals(shapeTypeName)) return TEXT_CIRCLE_CURVE;
    	if ("TEXT_BUTTON_CURVE".equals(shapeTypeName)) return TEXT_BUTTON_CURVE;
    	if ("TEXT_ARCH_UP_POUR".equals(shapeTypeName)) return TEXT_ARCH_UP_POUR;
    	if ("TEXT_ARCH_DOWN_POUR".equals(shapeTypeName)) return TEXT_ARCH_DOWN_POUR;
    	if ("TEXT_CIRCLE_POUR".equals(shapeTypeName)) return TEXT_CIRCLE_POUR;
    	if ("TEXT_BUTTON_POUR".equals(shapeTypeName)) return TEXT_BUTTON_POUR;
    	if ("TEXT_CURVE_UP".equals(shapeTypeName)) return TEXT_CURVE_UP;
    	if ("TEXT_CURVE_DOWN".equals(shapeTypeName)) return TEXT_CURVE_DOWN;
    	if ("TEXT_CASCADE_UP".equals(shapeTypeName)) return TEXT_CASCADE_UP;
    	if ("TEXT_CASCADE_DOWN".equals(shapeTypeName)) return TEXT_CASCADE_DOWN;
    	if ("TEXT_WAVE_1".equals(shapeTypeName)) return TEXT_WAVE_1;
    	if ("TEXT_WAVE_2".equals(shapeTypeName)) return TEXT_WAVE_2;
    	if ("TEXT_WAVE_3".equals(shapeTypeName)) return TEXT_WAVE_3;
    	if ("TEXT_WAVE_4".equals(shapeTypeName)) return TEXT_WAVE_4;
    	if ("TEXT_INFLATE".equals(shapeTypeName)) return TEXT_INFLATE;
    	if ("TEXT_DEFLATE".equals(shapeTypeName)) return TEXT_DEFLATE;
    	if ("TEXT_INFLATE_BOTTOM".equals(shapeTypeName)) return TEXT_INFLATE_BOTTOM;
    	if ("TEXT_DEFLATE_BOTTOM".equals(shapeTypeName)) return TEXT_DEFLATE_BOTTOM;
    	if ("TEXT_INFLATE_TOP".equals(shapeTypeName)) return TEXT_INFLATE_TOP;
    	if ("TEXT_DEFLATE_TOP".equals(shapeTypeName)) return TEXT_DEFLATE_TOP;
    	if ("TEXT_DEFLATE_INFLATE".equals(shapeTypeName)) return TEXT_DEFLATE_INFLATE;
    	if ("TEXT_DEFLATE_INFLATE_DEFLATE".equals(shapeTypeName)) return TEXT_DEFLATE_INFLATE_DEFLATE;
    	if ("TEXT_FADE_RIGHT".equals(shapeTypeName)) return TEXT_FADE_RIGHT;
    	if ("TEXT_FADE_LEFT".equals(shapeTypeName)) return TEXT_FADE_LEFT;
    	if ("TEXT_FADE_UP".equals(shapeTypeName)) return TEXT_FADE_UP;
    	if ("TEXT_FADE_DOWN".equals(shapeTypeName)) return TEXT_FADE_DOWN;
    	if ("TEXT_SLANT_UP".equals(shapeTypeName)) return TEXT_SLANT_UP;
    	if ("TEXT_SLANT_DOWN".equals(shapeTypeName)) return TEXT_SLANT_DOWN;
    	if ("TEXT_CAN_UP".equals(shapeTypeName)) return TEXT_CAN_UP;
    	if ("TEXT_CAN_DOWN".equals(shapeTypeName)) return TEXT_CAN_DOWN;
    	if ("FLOW_CHART_ALTERNATE_PROCESS".equals(shapeTypeName)) return FLOW_CHART_ALTERNATE_PROCESS;
    	if ("FLOW_CHART_OFFPAGE_CONNECTOR".equals(shapeTypeName)) return FLOW_CHART_OFFPAGE_CONNECTOR;
    	if ("CALLOUT_90".equals(shapeTypeName)) return CALLOUT_90;
    	if ("ACCENT_CALLOUT_90".equals(shapeTypeName)) return ACCENT_CALLOUT_90;
    	if ("BORDER_CALLOUT_90".equals(shapeTypeName)) return BORDER_CALLOUT_90;
    	if ("ACCENT_BORDER_CALLOUT_90".equals(shapeTypeName)) return ACCENT_BORDER_CALLOUT_90;
    	if ("LEFT_RIGHT_UP_ARROW".equals(shapeTypeName)) return LEFT_RIGHT_UP_ARROW;
    	if ("SUN".equals(shapeTypeName)) return SUN;
    	if ("MOON".equals(shapeTypeName)) return MOON;
    	if ("BRACKET_PAIR".equals(shapeTypeName)) return BRACKET_PAIR;
    	if ("BRACE_PAIR".equals(shapeTypeName)) return BRACE_PAIR;
    	if ("SEAL_4".equals(shapeTypeName)) return SEAL_4;
    	if ("DOUBLE_WAVE".equals(shapeTypeName)) return DOUBLE_WAVE;
    	if ("ACTION_BUTTON_BLANK".equals(shapeTypeName)) return ACTION_BUTTON_BLANK;
    	if ("ACTION_BUTTON_HOME".equals(shapeTypeName)) return ACTION_BUTTON_HOME;
    	if ("ACTION_BUTTON_HELP".equals(shapeTypeName)) return ACTION_BUTTON_HELP;
    	if ("ACTION_BUTTON_INFORMATION".equals(shapeTypeName)) return ACTION_BUTTON_INFORMATION;
    	if ("ACTION_BUTTON_FORWARD_NEXT".equals(shapeTypeName)) return ACTION_BUTTON_FORWARD_NEXT;
    	if ("ACTION_BUTTON_BACK_PREVIOUS".equals(shapeTypeName)) return ACTION_BUTTON_BACK_PREVIOUS;
    	if ("ACTION_BUTTON_END".equals(shapeTypeName)) return ACTION_BUTTON_END;
    	if ("ACTION_BUTTON_BEGINNING".equals(shapeTypeName)) return ACTION_BUTTON_BEGINNING;
    	if ("ACTION_BUTTON_RETURN".equals(shapeTypeName)) return ACTION_BUTTON_RETURN;
    	if ("ACTION_BUTTON_DOCUMENT".equals(shapeTypeName)) return ACTION_BUTTON_DOCUMENT;
    	if ("ACTION_BUTTON_SOUND".equals(shapeTypeName)) return ACTION_BUTTON_SOUND;
    	if ("ACTION_BUTTON_MOVIE".equals(shapeTypeName)) return ACTION_BUTTON_MOVIE;
    	if ("SINGLE_CORNER_SNIPPED".equals(shapeTypeName)) return SINGLE_CORNER_SNIPPED;
    	if ("TOP_CORNERS_SNIPPED".equals(shapeTypeName)) return TOP_CORNERS_SNIPPED;
    	if ("DIAGONAL_CORNERS_SNIPPED".equals(shapeTypeName)) return DIAGONAL_CORNERS_SNIPPED;
    	if ("TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED".equals(shapeTypeName)) return TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED;
    	if ("SINGLE_CORNER_ROUNDED".equals(shapeTypeName)) return SINGLE_CORNER_ROUNDED;
    	if ("TOP_CORNERS_ROUNDED".equals(shapeTypeName)) return TOP_CORNERS_ROUNDED;
    	if ("DIAGONAL_CORNERS_ROUNDED".equals(shapeTypeName)) return DIAGONAL_CORNERS_ROUNDED;
    	if ("HEPTAGON".equals(shapeTypeName)) return HEPTAGON;
    	if ("CLOUD".equals(shapeTypeName)) return CLOUD;
    	if ("SEAL_6".equals(shapeTypeName)) return SEAL_6;
    	if ("SEAL_7".equals(shapeTypeName)) return SEAL_7;
    	if ("SEAL_10".equals(shapeTypeName)) return SEAL_10;
    	if ("SEAL_12".equals(shapeTypeName)) return SEAL_12;
    	if ("SWOOSH_ARROW".equals(shapeTypeName)) return SWOOSH_ARROW;
    	if ("TEARDROP".equals(shapeTypeName)) return TEARDROP;
    	if ("SQUARE_TABS".equals(shapeTypeName)) return SQUARE_TABS;
    	if ("PLAQUE_TABS".equals(shapeTypeName)) return PLAQUE_TABS;
    	if ("PIE".equals(shapeTypeName)) return PIE;
    	if ("WEDGE_PIE".equals(shapeTypeName)) return WEDGE_PIE;
    	if ("INVERSE_LINE".equals(shapeTypeName)) return INVERSE_LINE;
    	if ("MATH_PLUS".equals(shapeTypeName)) return MATH_PLUS;
    	if ("MATH_MINUS".equals(shapeTypeName)) return MATH_MINUS;
    	if ("MATH_MULTIPLY".equals(shapeTypeName)) return MATH_MULTIPLY;
    	if ("MATH_DIVIDE".equals(shapeTypeName)) return MATH_DIVIDE;
    	if ("MATH_EQUAL".equals(shapeTypeName)) return MATH_EQUAL;
    	if ("MATH_NOT_EQUAL".equals(shapeTypeName)) return MATH_NOT_EQUAL;
    	if ("NON_ISOSCELES_TRAPEZOID".equals(shapeTypeName)) return NON_ISOSCELES_TRAPEZOID;
    	if ("LEFT_RIGHT_CIRCULAR_ARROW".equals(shapeTypeName)) return LEFT_RIGHT_CIRCULAR_ARROW;
    	if ("LEFT_RIGHT_RIBBON".equals(shapeTypeName)) return LEFT_RIGHT_RIBBON;
    	if ("LEFT_CIRCULAR_ARROW".equals(shapeTypeName)) return LEFT_CIRCULAR_ARROW;
    	if ("FRAME".equals(shapeTypeName)) return FRAME;
    	if ("HALF_FRAME".equals(shapeTypeName)) return HALF_FRAME;
    	if ("FUNNEL".equals(shapeTypeName)) return FUNNEL;
    	if ("GEAR_6".equals(shapeTypeName)) return GEAR_6;
    	if ("GEAR_9".equals(shapeTypeName)) return GEAR_9;
    	if ("DECAGON".equals(shapeTypeName)) return DECAGON;
    	if ("DODECAGON".equals(shapeTypeName)) return DODECAGON;
    	if ("DIAGONAL_STRIPE".equals(shapeTypeName)) return DIAGONAL_STRIPE;
    	if ("CORNER".equals(shapeTypeName)) return CORNER;
    	if ("CORNER_TABS".equals(shapeTypeName)) return CORNER_TABS;
    	if ("CHORD".equals(shapeTypeName)) return CHORD;
    	if ("CHART_PLUS".equals(shapeTypeName)) return CHART_PLUS;
    	if ("CHART_STAR".equals(shapeTypeName)) return CHART_STAR;
    	if ("CHART_X".equals(shapeTypeName)) return CHART_X;
    	if ("MIN_VALUE".equals(shapeTypeName)) return MIN_VALUE;
    	throw new IllegalArgumentException("Unknown ShapeType name.");
    }
    
    /// <summary>
    /// Returns array of ShapeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		IMAGE,
    		TEXT_BOX,
    		GROUP,
    		OLE_OBJECT,
    		OLE_CONTROL,
    		NON_PRIMITIVE,
    		RECTANGLE,
    		ROUND_RECTANGLE,
    		ELLIPSE,
    		DIAMOND,
    		TRIANGLE,
    		RIGHT_TRIANGLE,
    		PARALLELOGRAM,
    		TRAPEZOID,
    		HEXAGON,
    		OCTAGON,
    		PLUS,
    		STAR,
    		ARROW,
    		THICK_ARROW,
    		HOME_PLATE,
    		CUBE,
    		BALLOON,
    		SEAL,
    		ARC,
    		LINE,
    		PLAQUE,
    		CAN,
    		DONUT,
    		TEXT_SIMPLE,
    		TEXT_OCTAGON,
    		TEXT_HEXAGON,
    		TEXT_CURVE,
    		TEXT_WAVE,
    		TEXT_RING,
    		TEXT_ON_CURVE,
    		TEXT_ON_RING,
    		STRAIGHT_CONNECTOR_1,
    		BENT_CONNECTOR_2,
    		BENT_CONNECTOR_3,
    		BENT_CONNECTOR_4,
    		BENT_CONNECTOR_5,
    		CURVED_CONNECTOR_2,
    		CURVED_CONNECTOR_3,
    		CURVED_CONNECTOR_4,
    		CURVED_CONNECTOR_5,
    		CALLOUT_1,
    		CALLOUT_2,
    		CALLOUT_3,
    		ACCENT_CALLOUT_1,
    		ACCENT_CALLOUT_2,
    		ACCENT_CALLOUT_3,
    		BORDER_CALLOUT_1,
    		BORDER_CALLOUT_2,
    		BORDER_CALLOUT_3,
    		ACCENT_BORDER_CALLOUT_1,
    		ACCENT_BORDER_CALLOUT_2,
    		ACCENT_BORDER_CALLOUT_3,
    		RIBBON,
    		RIBBON_2,
    		CHEVRON,
    		PENTAGON,
    		NO_SMOKING,
    		SEAL_8,
    		SEAL_16,
    		SEAL_32,
    		WEDGE_RECT_CALLOUT,
    		WEDGE_R_RECT_CALLOUT,
    		WEDGE_ELLIPSE_CALLOUT,
    		WAVE,
    		FOLDED_CORNER,
    		LEFT_ARROW,
    		DOWN_ARROW,
    		UP_ARROW,
    		LEFT_RIGHT_ARROW,
    		UP_DOWN_ARROW,
    		IRREGULAR_SEAL_1,
    		IRREGULAR_SEAL_2,
    		LIGHTNING_BOLT,
    		HEART,
    		QUAD_ARROW,
    		LEFT_ARROW_CALLOUT,
    		RIGHT_ARROW_CALLOUT,
    		UP_ARROW_CALLOUT,
    		DOWN_ARROW_CALLOUT,
    		LEFT_RIGHT_ARROW_CALLOUT,
    		UP_DOWN_ARROW_CALLOUT,
    		QUAD_ARROW_CALLOUT,
    		BEVEL,
    		LEFT_BRACKET,
    		RIGHT_BRACKET,
    		LEFT_BRACE,
    		RIGHT_BRACE,
    		LEFT_UP_ARROW,
    		BENT_UP_ARROW,
    		BENT_ARROW,
    		SEAL_24,
    		STRIPED_RIGHT_ARROW,
    		NOTCHED_RIGHT_ARROW,
    		BLOCK_ARC,
    		SMILEY_FACE,
    		VERTICAL_SCROLL,
    		HORIZONTAL_SCROLL,
    		CIRCULAR_ARROW,
    		CUSTOM_SHAPE,
    		UTURN_ARROW,
    		CURVED_RIGHT_ARROW,
    		CURVED_LEFT_ARROW,
    		CURVED_UP_ARROW,
    		CURVED_DOWN_ARROW,
    		CLOUD_CALLOUT,
    		ELLIPSE_RIBBON,
    		ELLIPSE_RIBBON_2,
    		FLOW_CHART_PROCESS,
    		FLOW_CHART_DECISION,
    		FLOW_CHART_INPUT_OUTPUT,
    		FLOW_CHART_PREDEFINED_PROCESS,
    		FLOW_CHART_INTERNAL_STORAGE,
    		FLOW_CHART_DOCUMENT,
    		FLOW_CHART_MULTIDOCUMENT,
    		FLOW_CHART_TERMINATOR,
    		FLOW_CHART_PREPARATION,
    		FLOW_CHART_MANUAL_INPUT,
    		FLOW_CHART_MANUAL_OPERATION,
    		FLOW_CHART_CONNECTOR,
    		FLOW_CHART_PUNCHED_CARD,
    		FLOW_CHART_PUNCHED_TAPE,
    		FLOW_CHART_SUMMING_JUNCTION,
    		FLOW_CHART_OR,
    		FLOW_CHART_COLLATE,
    		FLOW_CHART_SORT,
    		FLOW_CHART_EXTRACT,
    		FLOW_CHART_MERGE,
    		FLOW_CHART_OFFLINE_STORAGE,
    		FLOW_CHART_ONLINE_STORAGE,
    		FLOW_CHART_MAGNETIC_TAPE,
    		FLOW_CHART_MAGNETIC_DISK,
    		FLOW_CHART_MAGNETIC_DRUM,
    		FLOW_CHART_DISPLAY,
    		FLOW_CHART_DELAY,
    		TEXT_PLAIN_TEXT,
    		TEXT_STOP,
    		TEXT_TRIANGLE,
    		TEXT_TRIANGLE_INVERTED,
    		TEXT_CHEVRON,
    		TEXT_CHEVRON_INVERTED,
    		TEXT_RING_INSIDE,
    		TEXT_RING_OUTSIDE,
    		TEXT_ARCH_UP_CURVE,
    		TEXT_ARCH_DOWN_CURVE,
    		TEXT_CIRCLE_CURVE,
    		TEXT_BUTTON_CURVE,
    		TEXT_ARCH_UP_POUR,
    		TEXT_ARCH_DOWN_POUR,
    		TEXT_CIRCLE_POUR,
    		TEXT_BUTTON_POUR,
    		TEXT_CURVE_UP,
    		TEXT_CURVE_DOWN,
    		TEXT_CASCADE_UP,
    		TEXT_CASCADE_DOWN,
    		TEXT_WAVE_1,
    		TEXT_WAVE_2,
    		TEXT_WAVE_3,
    		TEXT_WAVE_4,
    		TEXT_INFLATE,
    		TEXT_DEFLATE,
    		TEXT_INFLATE_BOTTOM,
    		TEXT_DEFLATE_BOTTOM,
    		TEXT_INFLATE_TOP,
    		TEXT_DEFLATE_TOP,
    		TEXT_DEFLATE_INFLATE,
    		TEXT_DEFLATE_INFLATE_DEFLATE,
    		TEXT_FADE_RIGHT,
    		TEXT_FADE_LEFT,
    		TEXT_FADE_UP,
    		TEXT_FADE_DOWN,
    		TEXT_SLANT_UP,
    		TEXT_SLANT_DOWN,
    		TEXT_CAN_UP,
    		TEXT_CAN_DOWN,
    		FLOW_CHART_ALTERNATE_PROCESS,
    		FLOW_CHART_OFFPAGE_CONNECTOR,
    		CALLOUT_90,
    		ACCENT_CALLOUT_90,
    		BORDER_CALLOUT_90,
    		ACCENT_BORDER_CALLOUT_90,
    		LEFT_RIGHT_UP_ARROW,
    		SUN,
    		MOON,
    		BRACKET_PAIR,
    		BRACE_PAIR,
    		SEAL_4,
    		DOUBLE_WAVE,
    		ACTION_BUTTON_BLANK,
    		ACTION_BUTTON_HOME,
    		ACTION_BUTTON_HELP,
    		ACTION_BUTTON_INFORMATION,
    		ACTION_BUTTON_FORWARD_NEXT,
    		ACTION_BUTTON_BACK_PREVIOUS,
    		ACTION_BUTTON_END,
    		ACTION_BUTTON_BEGINNING,
    		ACTION_BUTTON_RETURN,
    		ACTION_BUTTON_DOCUMENT,
    		ACTION_BUTTON_SOUND,
    		ACTION_BUTTON_MOVIE,
    		SINGLE_CORNER_SNIPPED,
    		TOP_CORNERS_SNIPPED,
    		DIAGONAL_CORNERS_SNIPPED,
    		TOP_CORNERS_ONE_ROUNDED_ONE_SNIPPED,
    		SINGLE_CORNER_ROUNDED,
    		TOP_CORNERS_ROUNDED,
    		DIAGONAL_CORNERS_ROUNDED,
    		HEPTAGON,
    		CLOUD,
    		SEAL_6,
    		SEAL_7,
    		SEAL_10,
    		SEAL_12,
    		SWOOSH_ARROW,
    		TEARDROP,
    		SQUARE_TABS,
    		PLAQUE_TABS,
    		PIE,
    		WEDGE_PIE,
    		INVERSE_LINE,
    		MATH_PLUS,
    		MATH_MINUS,
    		MATH_MULTIPLY,
    		MATH_DIVIDE,
    		MATH_EQUAL,
    		MATH_NOT_EQUAL,
    		NON_ISOSCELES_TRAPEZOID,
    		LEFT_RIGHT_CIRCULAR_ARROW,
    		LEFT_RIGHT_RIBBON,
    		LEFT_CIRCULAR_ARROW,
    		FRAME,
    		HALF_FRAME,
    		FUNNEL,
    		GEAR_6,
    		GEAR_9,
    		DECAGON,
    		DODECAGON,
    		DIAGONAL_STRIPE,
    		CORNER,
    		CORNER_TABS,
    		CHORD,
    		CHART_PLUS,
    		CHART_STAR,
    		CHART_X,
    		MIN_VALUE,
    	};
    }

    public static final int length = 250;
}

