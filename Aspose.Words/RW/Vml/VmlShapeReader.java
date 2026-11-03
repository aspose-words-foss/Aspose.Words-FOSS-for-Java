// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.DrColor;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Convert;
import java.lang.NumberFormatException;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.StringComparison;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Refactored reading of 'w:pict" for DOCX and WordML.
/// </summary>
class VmlShapeReader extends VmlShapeReaderBase implements INrxVmlReader
{
    /// <summary>
    /// This is the main method to read a VML object.
    ///
    /// Reads a shape container element such as w:pict or w:object.
    /// Reader should be positioned to element start.
    /// </summary>
    public ArrayList<ShapeBase> read(IVmlShapeReaderContext context) throws Exception
    {
        NrxXmlReader reader = context.getXmlReader();

        ArrayList<ShapeBase> shapes = new ArrayList<ShapeBase>();

        int dxaOrigin = 0;
        int dyaOrigin = 0;
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"dxaOrig"*/0:
                    dxaOrigin = reader.getValueAsInt();
                    break;
                case /*"dyaOrig"*/1:
                    dyaOrigin = reader.getValueAsInt();
                    break;
                default:
                    // Ignore.
                    break;
            }
        }

        reader.moveToElement();
        String tagName = reader.getLocalName();
        while (reader.readChild(tagName))
        {
            ShapeBase curShape = readShapeLevelElement(context);
            // RK There are usually several shape level elements shapetype, shape, OLEObject,
            // some return null. For example, when o:OLEObject occurs after v:shape, we want
            // to keep the shape object, not null.
            // WORDSNET-11137 We should check, that current shape is not within an array already.
            // For example, this possible if current shape is OLE control.
            if ((curShape != null) && (!shapes.contains(curShape)))
                msArrayList.add(shapes, curShape);
        }

        // WORDSNET-28422 Deferred shape source resolution.
        if ((shapes.size() > 0) && (shapes.get(0) instanceof Shape))
        {
            Shape shape = (Shape)shapes.get(0);
            String possibleMissingSrc = context.getMissingSource(shape);

            if (possibleMissingSrc != null)
                shape.getImageData().setImageSafe(context.getBinData(possibleMissingSrc));
        }

        // WORDSNET-15024 Update shape bounds with dxaOrigin and dyaOrigin.
        if (shapes.size() > 0)
        {
            final double ALLOWED_DELTA = 0.1;
            ShapeBase shape = (ShapeBase)shapes.get(0);
            if (dxaOrigin > 0)
            {
                // This calculation is based on <see cref="Picf" /> shape bounds calculation.
                // Picf.mx is an integer value so we round value and convert it back.
                double mx = msMath.round(shape.getWidth() * 20.0 / dxaOrigin * 1000.0);
                double newWidth = mx * dxaOrigin / 1000.0 / 20.0;
                double widthDelta = Math.abs(newWidth - shape.getWidth());
                if (widthDelta > ALLOWED_DELTA)
                    shape.setWidthSafe(newWidth);
            }

            if (dyaOrigin > 0)
            {
                double my = msMath.round(shape.getHeight() * 20.0 / dyaOrigin * 1000.0);
                double newHeight = my * dyaOrigin / 1000.0 / 20.0;
                double heightDelta = Math.abs(newHeight - shape.getHeight());
                if (heightDelta > ALLOWED_DELTA)
                    shape.setHeightSafe(newHeight);
            }
        }

        return shapes;
    }

    /// <summary>
    /// Reads a shapeType element and returns parsed shape properties.
    /// </summary>
    static ShapePr readShapeType(IVmlShapeReaderContext context) throws Exception
    {
        Debug.assertTrue("shapetype".equals(context.getXmlReader().getLocalName()));
        Shape shape = new Shape(context.getDocument());
        readShape(context, shape);
        return shape.getShapePr();
    }

    /// <summary>
    /// Reads a WML 'w:bgPict' element from the specified reader. Reader should be positioned to element start.
    /// </summary>
    static void readWmlBackground(IVmlShapeReaderContext context) throws Exception
    {
        Shape shape = new Shape(context.getDocument(), ShapeType.RECTANGLE);
        context.getDocument().setBackgroundShapeSafe(shape);

        NrxXmlReader reader = context.getXmlReader();
        while (reader.readChild("bgPict"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"binData"*/2: // w:binData
                    context.readBinData();
                    break;
                case /*"background"*/3: // w:background
                    if ("w".equals(reader.getPrefix()))
                        readWmlBackgroundCore(context, shape);
                    else if ("v".equals(reader.getPrefix()))
                        readShape(context, shape);
                    break;
                default:
                    reader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'w:background' element from the specified reader. Reader should be positioned to element start.
    /// </summary>
    private static void readWmlBackgroundCore(IVmlShapeReaderContext context, ShapeBase shape) throws Exception
    {
        NrxXmlReader reader = context.getXmlReader();
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"bgcolor"*/4: // w:bgcolor
                    if (!"white".equals(value))
                        shape.setShapeAttrInternal(ShapeAttr.FILL_COLOR, VmlColor.vmlToColor(value));
                    break;
                case /*"background"*/3: // w:background
                    shape.setShapeAttrInternal(ShapeAttr.FILL_IMAGE_BYTES, context.getBinData(value));
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }
    }

    /// <summary>
    /// Reads a shape level element from the specified reader.
    /// Shape level elements are "top level" elements of a shape such as:
    /// shape, group, rect, line, oval etc and shapetype.
    ///
    /// Reader should be positioned to element start.
    /// </summary>
    static ShapeBase readShapeLevelElement(IVmlShapeReaderContext context) throws Exception
    {
        ShapeBase shape = null;

        NrxXmlReader reader = context.getXmlReader();
        switch (gStringSwitchMap.of(reader.getLocalName()))
        {
            case /*"shapetype"*/5:
            {
                // [ECMA-376] M.5.4 ShapeType Element.
                // The ShapeType element defines a definition, or template, for a shape.
                // Such a template is 'instantiated' by creating a Shape element that references the ShapeType.
                // The shape can override any value specified by its ShapeType, or define attributes and elements the ShapeType does not provide.
                //
                // AM. Spec says that there are can be several shapes referred to one shape template so we need to map them.
                // Currently these shape types used only to obtain valid "o:spt" value.
                String id = reader.readAttribute("id", null);
                ShapePr shapePr = readShapeType(context);
                validateShapeType(shapePr);
                context.addShapeType(id, shapePr);
                break;
            }
            case /*"binData"*/2:
                // WordML only.
                context.readBinData();
                break;
            case /*"group"*/6:
                shape = new GroupShape(context.getDocument());
                readShape(context, shape);
                break;
            case /*"shape"*/7:
            case /*"polyline"*/8:
            // [ECMA-376] 6.1.2.10 image (Image File) element.
            case /*"image"*/9:
                shape = new Shape(context.getDocument());
                readShape(context, shape);
                break;
            case /*"rect"*/10:
                shape = new Shape(context.getDocument(), ShapeType.RECTANGLE);
                readShape(context, shape);
                break;
            case /*"roundrect"*/11:
                shape = new Shape(context.getDocument(), ShapeType.ROUND_RECTANGLE);
                readShape(context, shape);
                break;
            case /*"line"*/12:
                shape = new Shape(context.getDocument(), ShapeType.LINE);
                readShape(context, shape);
                break;
            case /*"oval"*/13:
                shape = new Shape(context.getDocument(), ShapeType.ELLIPSE);
                readShape(context, shape);
                break;
            case /*"scriptAnchor"*/14:
                // This is not defined in DOCX, but we still read it. Maybe they will fix the spec.
                shape = readScriptAnchor(context);
                break;
            case /*"arc"*/15:
            case /*"curve"*/16:
                // These exist in schema but never seen in WordML. Ignore for now.
                reader.ignoreElement();
                break;
            case /*"ocx"*/17: //w:ocx
                // WordML only?
                // This appears after v:shape and contains additional OLE-related data for the shape.
                shape = VmlOleReader.readWmlControl(context);
                break;
            case /*"control"*/18:
                shape = VmlOleReader.readDocxControl(context);
                break;
            case /*"OLEObject"*/19:
                // This appears after v:shape and contains additional OLE-related data for the shape.
                VmlOleReader.readOleObject(context);
                break;
            default:
                reader.ignoreElement();
                break;
        }

        // WORDSNET-22742 Deferred binding of the fill sources.
        // In case some bindings were unavailable at the time the markup was parsed.
        resolveFillSources(context);

        return shape;
    }

    /// <summary>
    /// Checks that specified shape properties does not match to shape type and updates
    /// shape properties to state which means that shape type can not be written. Also
    /// in this case properties of the type has to be merged with shape properties
    /// (see fix for WORDSNET-13556).
    /// </summary>
    private static void validateShapeType(ShapePr shapePr)
    {
        Debug.assertTrue(shapePr != null);

        Object shapeType = shapePr.get(ShapeAttr.SHAPE_TYPE);
        ShapePr expectedShapePr = null;

        if (shapeType != null)
            expectedShapePr = ShapeTypeLibrary.getShapeTypePr((/*ShapeType*/Integer)shapeType);

        if (expectedShapePr == null)
            return;

        PathPoint[] expectedVertices = (PathPoint[])expectedShapePr.fetchAttr(ShapeAttr.GEOMETRY_VERTICES);
        PathPoint[] curVertices = (PathPoint[])shapePr.fetchAttr(ShapeAttr.GEOMETRY_VERTICES);

        Handle[] expectedHandles = (Handle[])expectedShapePr.fetchAttr(ShapeAttr.GEOMETRY_HANDLES);
        Handle[] curHandles = (Handle[])shapePr.fetchAttr(ShapeAttr.GEOMETRY_HANDLES);

        // WORDSNET-15308 Remove shape type to avoid loading it from predefined types file.
        // It is necessary when properties of the detected shape type does not match to real
        // properties of the shape type specified in the input stream. Fluent comparison currently is
        // implemented (just compare vertices count). Removed "ShapeAttr.ShapeType" attribute for type of
        // the shape demonstrates that shape type from template can not be found and properties of the type
        // will be merged with shape properties. Also, if type for shape will not set explicitly,
        // such shape will has default "NonPrimitive" type.
        // WORDSNET-26377 see Test26377 for more cases.
        if (((expectedVertices == null) && (curVertices != null)) ||
            ((expectedHandles != null) && (curHandles == null)))
            shapePr.remove(ShapeAttr.SHAPE_TYPE);
        if ((expectedVertices != null) && (curVertices != null))
            if ((expectedVertices.length != curVertices.length))
                shapePr.remove(ShapeAttr.SHAPE_TYPE);
    }

    /// <summary>
    /// Reads script anchor. Reader should be positioned to 'w:scriptAnchor' element start.
    /// RK Note scriptAnchor is not defined in DOCX but we still read it. Mainly to allow
    /// our gold ExportImport tests to pass.
    /// </summary>
    private static Shape readScriptAnchor(IVmlShapeReaderContext context)
    {
        Shape shape = new Shape(context.getDocument());
        shape.setShapeType(ShapeType.IMAGE);

        // Set attributes that should be present by default.
        shape.setShapeAttrInternal(ShapeAttr.PRINT, false);
        shape.setShapeAttrInternal(ShapeAttr.LINE_ON, false);
        shape.setShapeAttrInternal(ShapeAttr.WRAP_TYPE, WrapType.INLINE);
        shape.setShapeAttrInternal(ShapeAttr.LOCK_AGAINST_GROUPING, true);
        shape.setShapeAttrInternal(ShapeAttr.FLIP, FlipOrientation.NONE);
        shape.setShapeAttrInternal(ShapeAttr.IMAGE_TITLE, "Normal");
        shape.setShapeAttrInternal(ShapeAttr.HIDDEN, true);
        shape.setShapeAttrInternal(ShapeAttr.REALLY_HIDDEN, false);

        shape.setShapeAttrInternal(ShapeAttr.SCRIPT_ANCHOR, true);

        NrxXmlReader reader = context.getXmlReader();
        while (reader.readChild("scriptAnchor"))
        {
            String value = reader.readString();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"language"*/20: //w:language
                {
                    shape.setShapeAttrInternal(ShapeAttr.SCRIPT_LANGUAGE_NAME, value);
                    /*ScriptLanguage*/int scriptLanguage = VmlEnum.vmlToScriptLanguage(value);

                    if (scriptLanguage != ScriptLanguage.NONE)
                        shape.setShapeAttrInternal(ShapeAttr.SCRIPT_LANGUAGE, scriptLanguage);

                    break;
                }
                case /*"args"*/21: //w:args
                    shape.setShapeAttrInternal(ShapeAttr.SCRIPT_TYPE, value);
                    break;
                case /*"scriptText"*/22: //w:scriptText
                    shape.setShapeAttrInternal(ShapeAttr.SCRIPT_TEXT, value);
                    break;
                default:
                    reader.ignoreElement();
                    break;
            }
        }

        return shape;
    }

    /// <summary>
    /// Reads a shape element, such as group, line, rect, shape etc from the specified reader.
    /// The reader should be positioned to element start.
    /// </summary>
    static void readShape(IVmlShapeReaderContext context, ShapeBase shape) throws Exception
    {
        NrxXmlReader reader = context.getXmlReader();

        String shapeElementName = reader.getLocalName();

        String id = null;
        String spid = null;
        String spt = null;
        String type = null;

        // RESILIENCY 17731 - The problem occurred because in the document 'to' attribute was specified before 'from'.
        // That is why width and height of the line shape was calculated improperly.
        // To make code resilient, first read both 'to' and 'from' and then set shape's attributes.
        VmlQuantity[] from = null;
        VmlQuantity[] to = null;

        // Read shape attributes.
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"id"*/23:
                    id = value;
                    break;
                case /*"spid"*/24: // o:spid
                    spid = value;
                    break;
                case /*"spt"*/25:
                    spt = value;
                    break;
                case /*"type"*/26:
                    type = value;
                    break;
                case /*"editas"*/27:
                    shape.setShapeAttrInternal(ShapeAttr.EDIT_AS, VmlEnum.vmlToEditAs(value));
                    break;
                case /*"alt"*/28:
                    shape.setShapeAttrInternal(ShapeAttr.SHAPE_DESCRIPTION, value);
                    break;
                case /*"style"*/29:
                    readShapeStyle(shape, value, context, reader);
                    break;
                case /*"ole"*/30: // o:ole
                    // This attribute is set in WordML for inline OLE controls and objects.
                    // It does not seem to be of any use during import. Hence, ignored.
                    break;
                case /*"bordertopcolor"*/31: // o:bordertopcolor
                    shape.setShapeAttrInternal(ShapeAttr.BORDER_TOP, createBorderWithColor(value));
                    break;
                case /*"borderleftcolor"*/32: // o:borderleftcolor
                    shape.setShapeAttrInternal(ShapeAttr.BORDER_LEFT, createBorderWithColor(value));
                    break;
                case /*"borderbottomcolor"*/33: // o:borderbottomcolor
                    shape.setShapeAttrInternal(ShapeAttr.BORDER_BOTTOM, createBorderWithColor(value));
                    break;
                case /*"borderrightcolor"*/34: // o:borderrightcolor
                    shape.setShapeAttrInternal(ShapeAttr.BORDER_RIGHT, createBorderWithColor(value));
                    break;
                case /*"allowincell"*/35: // o:allowincell
                    setBoolAttribute(shape, ShapeAttr.ALLOW_IN_CELL, value);
                    break;
                case /*"allowoverlap"*/36: // o:allowoverlap
                    setBoolAttribute(shape, ShapeAttr.ALLOW_OVERLAP, value);
                    break;
                case /*"coordorigin"*/37:
                    if (StringUtil.hasChars(value))
                    {
                        int[] values = VmlUtil.vmlToIntArray(value);
                        int x = (values.length > 0) ? values[0] : 0;
                        int y = (values.length > 1) ? values[1] : 0;
                        shape.setCoordOriginInternal(msPoint.ctor(x, y));
                    }
                    break;
                case /*"coordsize"*/38:
                {
                    /*Size*/long coordSize = msSize.Empty;
                    if (StringUtil.hasChars(value))
                    {
                        int[] values = VmlUtil.vmlToIntArray(value);
                        int width = (values.length > 0) ? values[0] : 0;
                        int height = (values.length > 1) ? values[1] : 0;
                        coordSize = msSize.ctor(width, height);
                    }
                    else
                    {
                        // WORDSNET-4014 Index was outside the bounds of the array.
                        // RK coordsize is an empty string in this file. MS Word seems to default to 1000.
                        coordSize = msSize.ctor(1000, 1000);
                    }
                    shape.setCoordSizeSafe(coordSize);
                    break;
                }
                case /*"adj"*/39:
                    setAdjustments(shape, value);
                    break;
                case /*"path"*/40:
                    setPath(shape, value);
                    break;
                case /*"points"*/41:
                    setPoints(shape, value);
                    break;
                case /*"preferrelative"*/42: // o:preferrelative
                    setBoolAttribute(shape, ShapeAttr.PREFER_RELATIVE_RESIZE, value);
                    break;
                case /*"fill"*/43:
                case /*"filled"*/44:
                    setBoolAttribute(shape, ShapeAttr.FILLED, value);
                    break;
                case /*"fillcolor"*/45:
                    shape.setShapeAttrInternal(ShapeAttr.FILL_COLOR, VmlColor.vmlToColor(value));

                    DrColor baseColor = VmlColor.getBaseColor(value);
                    if (baseColor != null)
                        shape.setShapeAttrInternal(ShapeAttr.FILL_COLOR_EXT, baseColor);
                    int colorModifier = VmlColor.getColorModifier(value);
                    if (colorModifier != 0)
                        shape.setShapeAttrInternal(ShapeAttr.FILL_COLOR_EXT_MOD, colorModifier);
                    break;
                case /*"dgmlayout"*/46: // o:dgmlayout
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_NODE_LAYOUT, (/*DiagramNodeLayout*/int)reader.getValueAsInt());
                    break;
                case /*"dgmnodekind"*/47: // o:dgmnodekind
                    shape.setShapeAttrInternal(ShapeAttr.DIAGRAM_NODE_KIND, (/*DiagramNodeKind*/int)reader.getValueAsInt());
                    break;
                case /*"connectortype"*/48: // o:connectortype
                    shape.setShapeAttrInternal(ShapeAttr.CONNECTOR_TYPE, VmlEnum.vmlToConnectorType(value));
                    break;
                case /*"stroked"*/49:
                    setBoolAttribute(shape, ShapeAttr.LINE_ON, value);
                    break;
                case /*"strokecolor"*/50:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_COLOR, VmlColor.vmlToColor(value));

                    DrColor baseStrokeColor = VmlColor.getBaseColor(value);
                    if (baseStrokeColor != null)
                        shape.setShapeAttrInternal(ShapeAttr.LINE_COLOR_EXT, baseStrokeColor);
                    int colorStrokeModifier = VmlColor.getColorModifier(value);
                    if (colorStrokeModifier != 0)
                        shape.setShapeAttrInternal(ShapeAttr.LINE_COLOR_EXT_MOD, colorStrokeModifier);
                    break;
                case /*"strokeweight"*/51:
                    setStrokeWeight(shape, value);
                    break;
                case /*"insetpen"*/52:
                    setBoolAttribute(shape, ShapeAttr.LINE_INSET_PEN, value);
                    break;
                case /*"href"*/53:
                    shape.setShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS, value);
                    break;
                case /*"target"*/54:
                    shape.setShapeAttrInternal(ShapeAttr.HYPERLINK_TARGET, value);
                    break;
                case /*"title"*/55:
                    shape.setShapeAttrInternal(ShapeAttr.SCREEN_TIP, value);
                    break;
                case /*"bullet"*/56:
                    // WordML only?
                    setBoolAttribute(shape, ShapeAttr.PICTURE_BULLET, value);
                    break;
                case /*"button"*/57: // o:button
                    setBoolAttribute(shape, ShapeAttr.BUTTON, value);
                    break;
                case /*"oleicon"*/58: // o:oleicon
                    setBoolAttribute(shape, ShapeAttr.OLE_ICON, value);
                    break;
                case /*"bwmode"*/59: // o:bwmode
                    shape.setShapeAttrInternal(ShapeAttr.BW_MODE, VmlEnum.vmlToBWMode(value));
                    break;
                case /*"bwpure"*/60: // o:bwpure
                    shape.setShapeAttrInternal(ShapeAttr.BW_PURE, VmlEnum.vmlToBWMode(value));
                    break;
                case /*"bwnormal"*/61: // o:bwnormal
                    shape.setShapeAttrInternal(ShapeAttr.BW_NORMAL, VmlEnum.vmlToBWMode(value));
                    break;
                case /*"hr"*/62: // o:hr
                    setBoolAttribute(shape, ShapeAttr.HR_ON, value);
                    break;
                case /*"hrstd"*/63: // o:hrstd
                    setBoolAttribute(shape, ShapeAttr.HR_STANDARD, value);
                    break;
                case /*"hrnoshade"*/64: // o:hrnoshade
                    setBoolAttribute(shape, ShapeAttr.HR_NO_SHADE, value);
                    break;
                case /*"hrpct"*/65: // o:hrpct
                    if (StringUtil.hasChars(value))
                        shape.setShapeAttrInternal(ShapeAttr.HR_PCT, (int)FormatterPal.parseDouble(value));
                    break;
                case /*"hralign"*/66: // o:hralign
                    shape.setShapeAttrInternal(ShapeAttr.HR_ALIGN, VmlEnum.vmlToHRAlign(value));
                    break;
                case /*"from"*/67:
                    if (shape.getShapeType() == ShapeType.LINE)
                        from = vmlToQuantityArray(value, getDefaultUnits(context));
                    break;
                case /*"to"*/68:
                    if (shape.getShapeType() == ShapeType.LINE)
                        to = vmlToQuantityArray(value, getDefaultUnits(context));
                    break;
                case /*"arcsize"*/69:
                    arcSizeToAdj1(shape, value);
                    break;
                case /*"wrapcoords"*/70:
                {
                    PathPoint[] wrapPoints = readWrapPolygon(reader.getValue());
                    if (wrapPoints != null)
                        shape.setShapeAttrInternal(ShapeAttr.WRAP_POLYGON_VERTICES, wrapPoints);
                    break;
                }
                case /*"equationxml"*/71:
                    shape.setShapeAttrInternal(ShapeAttr.EQUATION_XML, Encoding.getUTF8().getBytes(value));
                    break;
                case /*"gfxdata"*/72:
                    readGfxData(shape, value, reader);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }

        // AN: Set from and to of line shape here to fix the issue WORDSNET-17731.
        // In the document 'to' is specified before 'from', that is why width and height were calculated improperly.
        // WORDSNET-14928 Preserve vertices of the line to avoid inversion of the slope.
        // This code placed before detection of the shape type to avoid processing of the "line" type (o:spt="20").
        fillLinePosAndSize(from, to, shape, context.getGroupNestingLevel() > 0);

        // Model ShapeType attribute corresponds to 'o:spt' element.
        if (StringUtil.hasChars(spt))
        {
            // Local spt is defined so set it as ShapeType.
            setShapeType(shape, spt);
        }
        else if (StringUtil.hasChars(type))
        {
            // Local 'spt' is not defined but 'type' is defined instead. Try to find corresponding local shape type.
            ShapePr shapeTypePr = context.getShapeTypePr(type);
            if ((shapeTypePr != null) && shapeTypePr.contains(ShapeAttr.SHAPE_TYPE))
            {
                // Corresponding shape type found, obtain 'spt' value from it.
                shape.setShapeAttrInternal(ShapeAttr.SHAPE_TYPE, shapeTypePr.get(ShapeAttr.SHAPE_TYPE));
            }
            else
            {
                // Local shape type is not found. In facts we should get 'spt' value from VmlShapeLibrary which
                // defines global shape types but result will be the same (each global shape type string identifier '_x0000_tXX'
                // strictly corresponds to XX ShapeType value) so leave it without changes.

                // WORDSNET-13556 Get shape properties form template when spt is missing.
                if (shapeTypePr != null)
                {

                    // AM. Actually we always should do this way.
                    // Currently we relies on spt property but it is not fully correct and works only
                    // with MS Word produced files. According to VML standard shape is defined as
                    // shape template properties + shape itself properties and we can even get
                    // gain in performance since we don't need to load shape template from VmlShapeLibrary.
                    // But this leads us to global VML shape rework as we need to store
                    // both shape template property collection and shape itself in Shape class.
                    //
                    // So this fix is local for a while and handle the case when spt property is missing.
                    ShapePr shapePr = new ShapePr();
                    shapeTypePr.expandTo(shapePr);
                    shape.getShapePr().expandTo(shapePr);
                    shape.setShapePr(shapePr);
                }

                setShapeType(shape, type);
                // WORDSNET-26377 ShapeType in the direct attribute shall also be validated.
                if (shapeTypePr != null)
                    validateShapeType(shape.getShapePr());
            }
        }

        // Set shape id and name.
        if (StringUtil.hasChars(spid))
        {
            // RK VML shape has spid. In this case spid is "_x000_s1112" and it becomes shape id in the model.
            setIdentifierAttrSafe(shape, ShapeAttr.SHAPE_ID, spid);
            // If id is present, it becomes the shape name.
            if (StringUtil.hasChars(id))
                shape.setName(id);
        }
        else if (StringUtil.hasChars(id))
        {
            // RK VML spid is missing. In this case id is usually "_x000_s1112" and becomes shape id in the model.
            //
            // When reading a shape type, we will have "id    = _x0000_t57" and this code will execute
            // and it will set shape id equal to the shape type. I guess this is alright for us.
            setIdentifierAttrSafe(shape, ShapeAttr.SHAPE_ID, id);
        }

        // It is important to add the shape to the map using the original "id" value read from VML because
        // we cannot parse it into a model shape id in documents created by some 3rd party generators.
        if (StringUtil.hasChars(id))
            context.addToShapeMap(id, shape);

        // WORDSNET-24467 Fix Shape type for GroupShape.
        if ((shape.getNodeType() == NodeType.GROUP_SHAPE) && (shape.getShapeType() == ShapeType.IMAGE))
            shape.setShapeType(ShapeType.GROUP);

        // Read shape child elements.
        boolean isContinueReading = true;
        boolean isNextChildAlreadyRead = false;
        while (true)
        {
            if (!isNextChildAlreadyRead)
                isContinueReading = reader.readChild(shapeElementName);

            if (!isContinueReading)
                break;

            isNextChildAlreadyRead = false;
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"ink"*/73:
                    readInk(shape, context);
                    break;
                case /*"binData"*/2:
                    // WordML only?
                    context.readBinData();
                    break;
                case /*"fill"*/43: // v:fill
                    VmlFillReader.readFill(shape, context);
                    break;
                case /*"stroke"*/74: // v:stroke
                    readStroke(shape, context);
                    break;
                case /*"imagedata"*/75: // v:imagedata
                case /*"imageData"*/76:
                    if (!shape.isGroup())
                    {
                        Shape shapeInst = (Shape)shape;
                        readImageData(shapeInst, context);

                        // andrnosk: WORDSNET-5198 Image disappears after rendering the document.
                        // If Shape has image it means that ShapeType cannot be NonPrimitive.
                        // So we need to set o:spt=100 it is equivalent of ShapeType.CustomShape.
                        // We mimic MS Word behavior here.
                        if (shapeInst.hasImage() && (shape.getShapeType() == ShapeType.NON_PRIMITIVE))
                            shape.setShapeAttrInternal(ShapeAttr.SHAPE_TYPE, ShapeType.CUSTOM_SHAPE);
                    }
                    break;
                case /*"signatureline"*/77: // o:signatureline
                    readSignatureLine(shape, reader);
                    break;
                case /*"shadow"*/78: // v:shadow
                    VmlShadowReader.readShadow(shape, reader);
                    break;
                case /*"extrusion"*/79: // o:extrusion
                    VmlExtrusionReader.readExtrusion(shape, reader);
                    break;
                case /*"formulas"*/80: // v:formulas
                    readFormulas(shape, reader);
                    break;
                case /*"path"*/40: // v:path
                    readPath(shape, reader);
                    break;
                case /*"textpath"*/81: // v:textpath
                    readTextPath(shape, reader);
                    break;
                case /*"handles"*/82: // v:handles
                    readHandles(shape, reader);
                    break;
                case /*"lock"*/83: // o:lock
                    readLock(shape, reader);
                    break;
                case /*"textbox"*/84: // v:textbox
                    isNextChildAlreadyRead = readTextbox(context, shape);
                    break;
                case /*"diagram"*/85: // o:diagram
                    VmlDiagramReader.readDiagram(shape, reader);
                    break;
                case /*"callout"*/86: // o:callout
                    readCallout(shape, reader);
                    break;
                case /*"wrap"*/87: // w10:wrap
                    readWrap(shape, reader);
                    break;
                case /*"anchorlock"*/88: // w10:anchorlock
                    shape.setShapeAttrInternal(ShapeAttr.ANCHOR_LOCKED, true);
                    break;
                case /*"bordertop"*/89: // w10:bordertop
                    readBorder(shape, ShapeAttr.BORDER_TOP, reader);
                    break;
                case /*"borderleft"*/90: // w10:borderleft
                    readBorder(shape, ShapeAttr.BORDER_LEFT, reader);
                    break;
                case /*"borderbottom"*/91: // w10:borderbottom
                    readBorder(shape, ShapeAttr.BORDER_BOTTOM, reader);
                    break;
                case /*"borderright"*/92: // w10:borderright
                    readBorder(shape, ShapeAttr.BORDER_RIGHT, reader);
                    break;
                default:
                    if (shape.getShapeType() == ShapeType.GROUP)
                    {
                        // WORDSNET-26597 Limit maximum allowed group nesting level.
                        if (context.getGroupNestingLevel() < MAX_GROUP_NESTING_LEVEL)
                        {
                            // Flag is used to determine whether shape is inside group shape,
                            // to decide whether is required to use default units upon reading.
                            context.setGroupNestingLevel(context.getGroupNestingLevel() + 1)/*Property++*/;

                            // Read child shapes of a group shape.
                            ShapeBase shapeChild = readShapeLevelElement(context);
                            if (shapeChild != null)
                                shape.appendChildForLoad(shapeChild);

                            context.setGroupNestingLevel(context.getGroupNestingLevel() - 1)/*Property--*/;
                        }
                    }
                    else
                    {
                        reader.ignoreElement();
                    }
                    break;
            }
        }

        // andrnosk: WORDSNET-4576 Sometimes shape has zero size, in this case it might become invisible during export,
        // so need to update size and position of shape.
        if (shape.requiresBoundsRecalculation())
            shape.updateSizeAndPosition();

        // WORDSNET-22742 Deferred binding of the fill sources.
        // In case some bindings were unavailable at the time the markup was parsed.
        resolveFillSources(context);
    }

    private static String getDefaultUnits(IVmlShapeReaderContext context)
    {
        return context.getGroupNestingLevel() > 0 ? VmlQuantity.POINT_UNITS : VmlQuantity.PIXEL_UNITS;
    }

    /// <summary>
    /// Read base64 gfxdata.
    /// </summary>
    /// <remarks>Sometimes value can be corrupted.</remarks>
    private static void readGfxData(ShapeBase shape, String value, NrxXmlReader reader)
    {
        try
        {
            shape.setShapeAttrInternal(ShapeAttr.GFX_DATA, Convert.fromBase64String(value));
        }
        catch (NumberFormatException e)
        {
            reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, WarningStrings.CORRUPTED_GFX_DATA);
        }
    }

    private static void setStrokeWeight(ShapeBase shape, String value)
    {
        // WORDSNET-10198 The problem occurred because line weight was read improperly.
        // According to the specification if units are not specified value is in Emus.
        // VmlQuantity supposes that value without units are in point. Fixed by checking units.
        VmlQuantity quantity = new VmlQuantity(value);
        // DistanceToPoints returns source number if there are no units.
        if (quantity.isWithoutUnits())
            shape.setShapeAttrInternal(ShapeAttr.LINE_WIDTH, (int)quantity.distanceToPoints());
        else
            setEmusAttribute(shape, ShapeAttr.LINE_WIDTH, quantity);
    }

    /// <summary>
    /// Populates size, position and coordinates of the line according to specified parameters.
    /// </summary>
    /// <param name="from">Coordinates of the first point of the line.</param>
    /// <param name="to">Coordinates of the second point of the line.</param>
    /// <param name="shape">Current shape which is reading.</param>
    /// <param name="isInGroup">True - shape is nested to group.</param>
    private static void fillLinePosAndSize(VmlQuantity[] from, VmlQuantity[] to,
        ShapeBase shape, boolean isInGroup)
    {
        Debug.assertTrue(shape != null);

        if (shape.getShapeType() != ShapeType.LINE)
            return;

        PathPoint[] vertices = (PathPoint[])shape.getDirectShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES);

        // Expected two vertices for the line.
        if ((vertices == null) || (vertices.length < 2))
            vertices = new PathPoint[2];

        // Populate vertices points with values according to "from" and "to" attributes values.
        PathPoint fromVal = processLinePoint(from, true, isInGroup, shape);
        PathPoint toVal = processLinePoint(to, false, isInGroup, shape);

        // Calculate and set line size and position.
        int top = (fromVal.getY().getValue() < toVal.getY().getValue()) ? fromVal.getY().getValue() : toVal.getY().getValue();
        int left = (fromVal.getX().getValue() < toVal.getX().getValue()) ? fromVal.getX().getValue() : toVal.getX().getValue();

        shape.setLeft(isInGroup ? left : ConvertUtilCore.twipToPoint(left));
        shape.setTop(isInGroup ? top : ConvertUtilCore.twipToPoint(top));

        // WORDSNET-4811 Safe set of shape of props.
        int deltaW = toVal.getX().getValue() - fromVal.getX().getValue();
        shape.setWidthSafe(Math.abs(isInGroup ? deltaW : ConvertUtilCore.twipToPoint(deltaW)));

        int deltaH = toVal.getY().getValue() - fromVal.getY().getValue();
        shape.setHeightSafe(Math.abs(isInGroup ? deltaH : ConvertUtilCore.twipToPoint(deltaH)));

        // Actually we do not need to store coordinates exactly to save the document. It is necessary
        // to know start corner and end corner of the rectangle. However, DOC format requires to populate
        // geometry within the local coordinates.
        final int DEFAULT_COORD_SIZE = 21600;
        vertices[0] = new PathPoint((deltaW < 0) ? DEFAULT_COORD_SIZE : 0,
            (deltaH < 0) ? DEFAULT_COORD_SIZE : 0);
        vertices[1] = new PathPoint((deltaW >= 0) ? DEFAULT_COORD_SIZE : 0,
            (deltaH >= 0) ? DEFAULT_COORD_SIZE : 0);

        // Save vertices for cases when coordinates on existing model parameters Left, Top and
        // Left + width, Top + height can not describe actual geometry of the line.
        if ((deltaW < 0) || (deltaH < 0))
            shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES, vertices);
    }

    /// <summary>
    /// Creates value of the line vertices coordinate.
    /// </summary>
    /// <param name="coordinates">Obtained coordinates values from VML.</param>
    /// <param name="isStartPoint">True - first point of the line is in processing.</param>
    /// <param name="isInGroup">True - shape is nested to group.</param>
    /// <param name="shape">Current shape which is reading.</param>
    private static PathPoint processLinePoint(VmlQuantity[] coordinates, boolean isStartPoint, boolean isInGroup, ShapeBase shape)
    {
        // According to ooxml spec default value for coordinates of the end point of the line are 10
        // points, however looks like MSW uses 50 points as default value. Also when line is nested to
        // group and last vertice of the line is missed, then MSW put 1000 for each coordinates of the
        // last point.
        // Vertices store in coordinates which represented with "PathValue" type and these
        // values should be in "Twips".
        int defaultCoord = isInGroup ? 1000 : ConvertUtilCore.pointToTwip(50.0);

        // MSW uses 0 as default for first vertice.
        defaultCoord = isStartPoint ? 0 : defaultCoord;

        int x = getX(isStartPoint, shape, defaultCoord, isInGroup);
        int y = getY(isStartPoint, shape, defaultCoord, isInGroup);

        PathValue coordX = getVerticeCoordinate(coordinates, x, 0, isInGroup);
        PathValue coordY = getVerticeCoordinate(coordinates, y, 1, isInGroup);

        return new PathPoint(coordX, coordY);
    }

    /// <summary>
    /// Gets x-coordinate with taking left and width of the shape into consideration.
    /// </summary>
    private static int getX(boolean isStartPoint, ShapeBase shape, int defaultCoord, boolean isInGroup)
    {
        ShapePr pr = shape.getShapePr();

        if (isStartPoint)
        {
            return pr.containsKey(ShapeAttr.LEFT) ? getCoordinate(shape.getLeft(), isInGroup) : defaultCoord;
        }
        else
        {
            return (pr.containsKey(ShapeAttr.LEFT) && pr.containsKey(ShapeAttr.WIDTH))
                ? getCoordinate(shape.getLeft() + shape.getWidth(), isInGroup)
                : defaultCoord;
        }
    }

    /// <summary>
    /// Gets y-coordinate with taking top and height of the shape into consideration.
    /// </summary>
    private static int getY(boolean isStartPoint, ShapeBase shape, int defaultCoord, boolean isInGroup)
    {
        ShapePr pr = shape.getShapePr();

        if (isStartPoint)
        {
            return pr.containsKey(ShapeAttr.TOP) ? getCoordinate(shape.getTop(), isInGroup) : defaultCoord;
        }
        else
        {
            return (pr.containsKey(ShapeAttr.TOP) && pr.containsKey(ShapeAttr.HEIGHT))
                ? getCoordinate(shape.getTop() + shape.getHeight(), isInGroup)
                : defaultCoord;
        }
    }

    private static int getCoordinate(double pos, boolean isInGroup)
    {
        // WORDSNET-19773 Position of nested shape does not need the conversion to "twips".
        return isInGroup ? MathUtil.castDoubleToInt(pos) : ConvertUtilCore.pointToTwip(pos);
    }

    /// <summary>
    /// Calculates coordinate for line vertices.
    /// </summary>
    /// <param name="coordinates">Obtained coordinates values from VML.</param>
    /// <param name="defaultCoord">Default coordinate value.</param>
    /// <param name="index">Index of the value among coordinates.</param>
    /// <param name="isInGroup">True - shape is nested to group.</param>
    /// <returns>Coordinate.</returns>
    private static PathValue getVerticeCoordinate(VmlQuantity[] coordinates, int defaultCoord,
        int index, boolean isInGroup)
    {
        if ((coordinates == null) || (index > (coordinates.length - 1)))
            return new PathValue(defaultCoord);

        VmlQuantity quantity = coordinates[index];

        if (!quantity.isDistance())
            return new PathValue(defaultCoord);

        return isInGroup ? new PathValue(MathUtil.doubleToInt(quantity.distanceToPoints()))
            : coordinates[index].distanceToPathValue();
    }

    private static void readSignatureLine(ShapeBase shape, NrxXmlReader reader)
    {
        shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE, true);
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"addlxml"*/93:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_ADDL_XML, reader.getValue());
                    break;
                case /*"allowcomments"*/94:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_ALLOW_COMMENTS, reader.getValueAsBool());
                    break;
                case /*"ext"*/95:
                    //ignore this element.
                    break;
                case /*"id"*/23:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_ID, reader.getValue());
                    break;
                case /*"issignatureline"*/96:
                    shape.setShapeAttrInternal(ShapeAttr.IS_SIGNATURE_LINE, reader.getValueAsBool());
                    break;
                case /*"provid"*/97:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_ID, reader.getValue());
                    break;
                case /*"showsigndate"*/98:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE, reader.getValueAsBool());
                    break;
                case /*"signinginstructions"*/99:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST, reader.getValue());
                    break;
                case /*"signinginstructionsset"*/100:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SIGN_INST_SET, reader.getValueAsBool());
                    break;
                case /*"sigprovurl"*/101:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_PROV_URL, reader.getValue());
                    break;
                case /*"suggestedsigner"*/102:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER, reader.getValue());
                    break;
                case /*"suggestedsigner2"*/103:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_2, reader.getValue());
                    break;
                case /*"suggestedsigneremail"*/104:
                    shape.setShapeAttrInternal(ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL, reader.getValue());
                    break;
                default:
                    reader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.SHAPES,
                        msString.format(WarningStrings.UNEXPECTED_TAG_OR_ATTRIBUTE, reader.getLocalName()));
                    break;
            }
        }
    }

    /// <summary>
    /// Reads wrapcoords (Shape Bounding Polygon)
    /// This element specifies the bounding polygon that surrounds a shape.
    /// This is used when text is tightly wrapped around a shape.
    /// </summary>
    private static PathPoint[] readWrapPolygon(String value)
    {
        Regex numberRegex = new Regex("-?\\d+");
        MatchCollection numbers = numberRegex.matches(value);

        String[] values = new String[numbers.getCount()];

        for (int i = 0; i < numbers.getCount(); i++)
            values[i] = numbers.get(i).getValue();

        PathPoint[] wrapPolygon = new PathPoint[values.length / 2];
        for (int i = 0; i < values.length / 2; i++)
        {
            wrapPolygon[i] = new PathPoint(
                VmlPathReader.parsePathValue(values[2 * i]),
                VmlPathReader.parsePathValue(values[2 * i + 1]));
        }

        return (wrapPolygon.length != 0) ? wrapPolygon : null;
    }

    /// <summary>
    /// Sets the attribute which is an identifier, only if it can parse it from the provided VML string.
    /// Additionally, checks that such shape type identifier exists in the template, when shape type
    /// attribute has to be set.
    /// </summary>
    private static void setIdentifierAttrSafe(ShapeBase shape, int shapeAttr, String id) throws Exception
    {
        int value = NrxXmlUtil.tryParseId(id);

        if ((value == Integer.MIN_VALUE) ||
            (shapeAttr == ShapeAttr.SHAPE_TYPE) && (ShapeTypeLibrary.getShapeTypeXml((/*ShapeType*/int)value) == null))
        {
            return;
        }

        shape.setShapeAttrInternal(shapeAttr, value);
    }

    private static void readShapeStyle(ShapeBase shape, String style, IVmlShapeReaderContext context, NrxXmlReader reader)
    {
        VmlNameValue[] props = vmlToNameValueArray(style);

        boolean isInline = true;

        for (int i = 0; i < props.length; i++)
        {
            String name = props[i].getName();
            String value = props[i].getValue();

            if (("position".equals(name)) && ("absolute".equals(value)))
            {
                // alexnosk Part of WORDSNET-8783 Fix: If position of shape is set to absolute, set wrap type to none.
                // Later we will check whether wrap type is set in direct attributes and determine whether it should be reset.
                shape.setShapeAttrInternal(ShapeAttr.WRAP_TYPE, WrapType.NONE);
                isInline = false;
            }

            readShapeStyleProperty(shape, name, value, context, reader);
        }

        if (isInline)
            shape.setShapeAttrInternal(ShapeAttr.WRAP_TYPE, WrapType.INLINE);
    }

    /// <summary>
    /// Reads property from 'style' attribute of a shape.
    /// </summary>
    private static void readShapeStyleProperty(
        ShapeBase shape,
        String name,
        String value,
        IVmlShapeReaderContext context,
        NrxXmlReader reader)
    {
        boolean inGroup = (context.getGroupNestingLevel() > 0);

        switch (gStringSwitchMap.of(name))
        {
            case /*"text-align"*/105:
                // Not found. Ignored.
                break;
            case /*"top"*/106:
            case /*"margin-top"*/107:
                shape.setTop(readDistancePoints(value, 0.0, !inGroup));
                break;
            case /*"left"*/108:
            case /*"margin-left"*/109:
                shape.setLeft(readDistancePoints(value, 0.0, !inGroup));
                break;
            case /*"width"*/110:
                // Do not apply default units for elements inside group shape.
                shape.setWidthSafe(readDistancePoints(value, ShapeBase.INVALID_SHAPE_SIZE_DEFAULT, !inGroup));
                break;
            case /*"height"*/111:
                shape.setHeightSafe(readDistancePoints(value, ShapeBase.INVALID_SHAPE_SIZE_DEFAULT, !inGroup));
                break;
            case /*"rotation"*/112:
                shape.setShapeAttrInternal(ShapeAttr.TRANSFORM_ROTATION, readDegrees(value));
                break;
            case /*"flip"*/113:
                // WORDSNET-14444 In the document flip is specified two times, earlier we take in account only the last value,
                // MS Word takes in account both. Do the same now.
                /*FlipOrientation*/int flip = VmlEnum.vmlToFlipOrientation(value);
                if (shape.getDirectShapeAttrInternal(ShapeAttr.FLIP) != null)
                    flip |= (/*FlipOrientation*/Integer)shape.getDirectShapeAttrInternal(ShapeAttr.FLIP);

                shape.setShapeAttrInternal(ShapeAttr.FLIP, flip);
                break;
            case /*"visibility"*/114:
                setBoolAttribute(shape, ShapeAttr.HIDDEN, "hidden", "visible", value);
                break;
            case /*"z-index"*/115:
            {
                // WORDSNET-14636 Implement reading of the non-numeric value "auto" for "z-index" attribute.
                // According to spec: "auto - Uses the order that the shapes appear in the page, bottom to top".
                int zIndex = StringUtil.equalsIgnoreCase(value, "auto")
                    ? 0
                    // WORDSNET-22259 Resilient reading of Int64 values (truncating for a while).
                    : ZOrderUtil.truncateLongToInt(FormatterPal.parseInt64(value));

                if (zIndex < 0)
                    shape.setShapeAttrInternal(ShapeAttr.BEHIND_TEXT, true);

                // Store z-index in the ZOrder property, it will be recalculated after the document is loaded.
                // For DOCX convert using z-index base values.
                shape.setZOrder(ZOrderUtil.zIndexToZOrder(zIndex, shape.getBehindText()));

                context.addToZOrderList(shape);
                break;
            }

            case /*"mso-wrap-edited"*/116:
                setBoolAttribute(shape, ShapeAttr.EDITED_WRAP, value);
                break;
            case /*"mso-wrap-distance-left"*/117:
                setEmusAttribute(shape, ShapeAttr.DISTANCE_LEFT, value);
                break;
            case /*"mso-wrap-distance-top"*/118:
                setEmusAttribute(shape, ShapeAttr.DISTANCE_TOP, value);
                break;
            case /*"mso-wrap-distance-right"*/119:
                setEmusAttribute(shape, ShapeAttr.DISTANCE_RIGHT, value);
                break;
            case /*"mso-wrap-distance-bottom"*/120:
                setEmusAttribute(shape, ShapeAttr.DISTANCE_BOTTOM, value);
                break;
            case /*"mso-position-horizontal"*/121:
                shape.setShapeAttrInternal(ShapeAttr.HORIZONTAL_ALIGNMENT, StyleConvertUtil.xmlToHorizontalAlignment(value));
                break;
            case /*"mso-position-horizontal-relative"*/122:
                shape.setShapeAttrInternal(ShapeAttr.RELATIVE_HORIZONTAL_POSITION, StyleConvertUtil.xmlToRelativeHorizontalPosition(value));
                break;
            case /*"mso-position-vertical"*/123:
                shape.setShapeAttrInternal(ShapeAttr.VERTICAL_ALIGNMENT, StyleConvertUtil.xmlToVerticalAlignment(value));
                break;
            case /*"mso-position-vertical-relative"*/124:
                shape.setShapeAttrInternal(ShapeAttr.RELATIVE_VERTICAL_POSITION, StyleConvertUtil.xmlToRelativeVerticalPosition(value, RelativeVerticalPosition.TEXT_FRAME_DEFAULT));
                break;
            case /*"mso-wrap-style"*/125:
                shape.setShapeAttrInternal(ShapeAttr.TEXTBOX_WRAP_MODE, VmlEnum.vmlToTextboxWrapMode(value));
                break;
            case /*"position"*/126:
                // andrnosk: Ignored. We do not need to read this attribute, because we write it depending on wrap type.
                break;

            case /*"v-text-anchor"*/127:
                shape.setShapeAttrInternal(ShapeAttr.TEXTBOX_ANCHOR, VmlEnum.vmlToTextboxAnchor(value));
                break;

            // These are available since Word 2007 and specify shape position and size in percent.
            // WORDSNET-21025 MS Word ignores the decimal part of mso-XXX-percent values.
            case /*"mso-left-percent"*/128:
                shape.setShapeAttrInternal(ShapeAttr.LEFT_PERCENT, FormatterPal.xmlToInt(value));
                break;
            case /*"mso-top-percent"*/129:
                shape.setShapeAttrInternal(ShapeAttr.TOP_PERCENT, FormatterPal.xmlToInt(value));
                break;
            case /*"mso-width-percent"*/130:
                shape.setShapeAttrInternal(ShapeAttr.WIDTH_PERCENT, FormatterPal.xmlToInt(value));
                break;
            case /*"mso-height-percent"*/131:
                shape.setShapeAttrInternal(ShapeAttr.HEIGHT_PERCENT, FormatterPal.xmlToInt(value));
                break;
            case /*"mso-width-relative"*/132:
                shape.setShapeAttrInternal(ShapeAttr.RELATIVE_WIDTH, StyleConvertUtil.xmlToRelativeWidth(value));
                break;
            case /*"mso-height-relative"*/133:
                shape.setShapeAttrInternal(ShapeAttr.RELATIVE_HEIGHT, StyleConvertUtil.xmlToRelativeHeight(value));
                break;
            default:
                reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                break;
        }
    }

    private static void readInk(ShapeBase shape, IVmlShapeReaderContext context)
    {
        NrxXmlReader reader = context.getXmlReader();

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"i"*/134:
                    shape.setShapeAttrInternal(ShapeAttr.INK_DATA, Convert.fromBase64String(value));
                    break;
                case /*"annotation"*/135:
                    shape.setShapeAttrInternal(ShapeAttr.INK_ANNOTATION, ("t".equals(value)));
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }
    }

    private static void readStroke(ShapeBase shape, IVmlShapeReaderContext context) throws Exception
    {
        NrxXmlReader reader = context.getXmlReader();
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"id"*/23:  // r:id - DOCX
                case /*"src"*/136: // WordML.
                {
                    // DOCX only.
                    byte[] binData = context.getBinData(value);
                    shape.setShapeAttrInternal(ShapeAttr.LINE_IMAGE_BYTES, binData);
                    break;
                }
                case /*"title"*/55: // o:title
                {
                    if (shape.getShapePr().getDirectAttr(ShapeAttr.LINE_FILL_BLIP_NAME) == null)
                        shape.setShapeAttrInternal(ShapeAttr.LINE_FILL_BLIP_NAME, value);
                    break;
                }
                case /*"joinstyle"*/137:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_JOIN_STYLE, VmlEnum.vmlToJoinStyle(value));
                    break;
                case /*"dashstyle"*/138:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_DASH_STYLE, VmlEnum.vmlToDashStyle(value));
                    break;
                case /*"linestyle"*/139:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_STYLE, VmlEnum.vmlToShapeLineStyle(value));
                    break;
                case /*"endcap"*/140:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_END_CAP_STYLE, VmlEnum.vmlToEndCap(value));
                    break;
                case /*"startarrow"*/141:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_START_ARROW, VmlEnum.vmlToArrowType(value));
                    break;
                case /*"startarrowwidth"*/142:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_START_ARROW_WIDTH, VmlEnum.vmlToArrowWidth(value));
                    break;
                case /*"startarrowlength"*/143:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_START_ARROW_LENGTH, VmlEnum.vmlToArrowLength(value));
                    break;
                case /*"endarrow"*/144:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_END_ARROW, VmlEnum.vmlToArrowType(value));
                    break;
                case /*"endarrowwidth"*/145:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_END_ARROW_WIDTH, VmlEnum.vmlToArrowWidth(value));
                    break;
                case /*"endarrowlength"*/146:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_END_ARROW_LENGTH, VmlEnum.vmlToArrowLength(value));
                    break;
                case /*"opacity"*/147:
                    setFixedAttribute(shape, ShapeAttr.LINE_OPACITY, value);
                    break;
                case /*"color"*/148:
                    // WORDSNET-11252 Seems, MS Word reads this attribute, but does not write this again to stroke element upon resaving.
                    // Instead, it overrides 'strokecolor' attribute with this color.
                    shape.setShapeAttrInternal(ShapeAttr.LINE_COLOR, VmlColor.vmlToColor(value));
                    break;
                case /*"color2"*/149:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_BACK_COLOR, VmlColor.vmlToColor(value));

                    DrColor baseColor = VmlColor.getBaseColor(value);
                    if (baseColor != null)
                        shape.setShapeAttrInternal(ShapeAttr.LINE_BACK_COLOR_EXT, baseColor);
                    int colorModifier = VmlColor.getColorModifier(value);
                    if (colorModifier != 0)
                        shape.setShapeAttrInternal(ShapeAttr.LINE_BACK_COLOR_EXT_MOD, colorModifier);
                    break;
                case /*"filltype"*/150:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_FILL_TYPE, VmlEnum.vmlToLineFillType(value));
                    break;
                case /*"on"*/151:
                    shape.setShapeAttrInternal(ShapeAttr.LINE_ON, reader.getValueAsBool());
                    break;
                case /*"weight"*/152:
                    setStrokeWeight(shape, value);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static void readImageData(Shape shape, IVmlShapeReaderContext context) throws Exception
    {
        NrxXmlReader reader = context.getXmlReader();

        boolean hasId = (reader.readAttribute("id", null) != null);
        boolean hasSrc = (reader.readAttribute("src", null) != null);

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"id"*/23:  // r:id DOCX.
                {
                    if ("r".equals(reader.getPrefix()))
                        readImageSource(context, value, shape);
                    break;
                }
                case /*"src"*/136: // WordML.
                    readImageSource(context, value, shape);
                    break;
                case /*"pict"*/153: // r:pict DOCX.
                {
                    // MS Word outputs Macintosh PICT images to WordML/Docx in two formats: WMF (primary) and PICT (pcz) (alternate).
                    // r:id - primary, r:pict - alternate.
                    // We import only in primary format, ignoring alternate format.
                    // But for v:image, r:pict is primary image source and we should read it.
                    if (!hasId && !hasSrc)
                        readImageSource(context, value, shape);
                    break;
                }
                case /*"href"*/53: // r:href
                    shape.setShapeAttrInternal(ShapeAttr.IMAGE_SOURCE_FULL_NAME, context.getRelationshipTarget(value));
                    break;
                case /*"title"*/55: // o:title
                    shape.setShapeAttrInternal(ShapeAttr.IMAGE_TITLE, value);
                    break;
                case /*"croptop"*/154:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_CROP_TOP, value);
                    break;
                case /*"cropbottom"*/155:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_CROP_BOTTOM, value);
                    break;
                case /*"cropleft"*/156:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_CROP_LEFT, value);
                    break;
                case /*"cropright"*/157:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_CROP_RIGHT, value);
                    break;
                case /*"chromakey"*/158:
                    shape.setShapeAttrInternal(ShapeAttr.IMAGE_TRANSPARENT, VmlColor.vmlToColor(value));
                    break;
                case /*"gain"*/159:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_CONTRAST, value);
                    break;
                case /*"blacklevel"*/160:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_BRIGHTNESS, value);
                    break;
                case /*"gamma"*/161:
                    setFixedAttribute(shape, ShapeAttr.IMAGE_GAMMA, value);
                    break;
                case /*"grayscale"*/162:
                    setBoolAttribute(shape, ShapeAttr.IMAGE_GRAY_SCALE, value);
                    break;
                case /*"bilevel"*/163:
                    setBoolAttribute(shape, ShapeAttr.IMAGE_BI_LEVEL, value);
                    break;
                case /*"embosscolor"*/164:
                    shape.setShapeAttrInternal(ShapeAttr.IMAGE_DBL_CR_MOD, VmlColor.vmlToColor(value));
                    break;
                // WORDSNET-3801 The problem occurred because recolortarget attribute of image was not supported.
                case /*"recolortarget"*/165:
                {
                    DrColor recolorTarget = VmlColor.vmlToColor(value);
                    int recolorTargetValue = (recolorTarget.getB() << 16) + (recolorTarget.getG() << 8) + (recolorTarget.getR());
                    shape.setShapeAttrInternal(ShapeAttr.IMAGE_RECOLOR, recolorTargetValue);
                    break;
                }
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static void readImageSource(IVmlShapeReaderContext context, String value, Shape shape) throws Exception
    {
        if (StringUtil.hasChars(value) && context.isExternalImage(value))
        {
            // RK If the relationship is external, it means the image is linked only.
            shape.setShapeAttrInternal(ShapeAttr.IMAGE_SOURCE_FULL_NAME, context.getRelationshipTarget(value));
        }
        else
        {
            // If the relationship is internal, it refers to an image inside the package and
            // the link to the image will come in the href attribute.

            byte[] binData = context.getBinData(value);

            if (binData == null)
            {
                // Try to resolve shape source later.
                context.markMissingSource(shape, value);
            }
            else
                shape.getImageData().setImageSafe(context.getBinData(value));
        }
    }

    private static void readFormulas(ShapeBase shape, NrxXmlReader reader)
    {
        ArrayList<Formula> formulaList = new ArrayList<Formula>();

        while (reader.readChild("formulas"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"f"*/166: // v:f
                {
                    String eqn = reader.readAttribute("eqn", "");
                    if (StringUtil.hasChars(eqn))
                        msArrayList.add(formulaList, readFormula(eqn));
                    break;
                }
                default:
                    reader.ignoreElement();
                    break;
            }
        }

        Formula[] formulas = msArrayList.toArray(formulaList, new Formula[0]);
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_FORMULAS, formulas);
    }

    private static Formula readFormula(String eqn)
    {
        // Formula consists of opcode and 1 to 3 operands, space delimited.
        String[] formulaParts = msString.split(eqn, ' ');

        Formula formula = new Formula();

        formula.Operation = VmlEnum.vmlToOperation(formulaParts[0]);

        if (formulaParts.length > 1)
        {
            String value = formulaParts[1];

            // WORDSNET-10350 Value can be negative, so should check all integers, not only positive ones.
            // This also done for all other formula Params below.
            if (!FormatterPal.isInteger(value))
            {
                formula.Flags = formula.Flags | 0x20;
                formula.Param1 = getRefOperand(value);
            }
            else
            {
                // WORDSNET-10350 MSW converts all values to the values in range 0-65535.
                formula.Param1 = FormatterPal.xmlToIntAsUnsigned(value);
            }
        }

        if (formulaParts.length > 2)
        {
            String value = formulaParts[2];

            if (!FormatterPal.isInteger(value))
            {
                formula.Flags = formula.Flags | 0x40;
                formula.Param2 = getRefOperand(value);
            }
            else
            {
                formula.Param2 = FormatterPal.xmlToIntAsUnsigned(value);
            }
        }

        if (formulaParts.length > 3)
        {
            String value = formulaParts[3];

            if (!FormatterPal.isInteger(value))
            {
                formula.Flags = formula.Flags | 0x80;
                formula.Param3 = getRefOperand(value);
            }
            else
            {
                formula.Param3 = FormatterPal.xmlToIntAsUnsigned(value);
            }
        }

        return formula;
    }

    private static int getRefOperand(String value)
    {
        // WORDSNET-21467 Word ignores string case and reads both 'lineDrawn' and 'linedrawn'.
        value = value.toLowerCase();
        switch (gStringSwitchMap.of(value))
        {
            case /*"xcenter"*/167:
                return 0x140; // The x ordinate of the center of coordorigin, coordsize (x+w/2).
            case /*"ycenter"*/168:
                return 0x141; // The y ordinate of the center of coordorigin, coordsize (y+h/2).
            case /*"width"*/110:
                return 0x142; // The width defined by the coordsize attribute.
            case /*"height"*/111:
                return 0x143; // The height defined by the coordsize attribute.
            case /*"xlimo"*/169:
                return 0x153; // The x value of the limo attribute.
            case /*"ylimo"*/170:
                return 0x154; // The y value of the limo attribute.
            case /*"linedrawn"*/171:
                return 0x1fc; // unknown
            case /*"pixellinewidth"*/172:
                // The line width in output device pixels. This is used to outset lines from the edge of a rectangle
                // on the assumption that the implementation draws to lower right pixel in preference to the upper left pixel when a line is on a pixel boundary.
                return 0x4f7;
            case /*"pixelwidth"*/173:
                return 0x4f8; // The width of the shape in device pixels (i.e. the coordsize width transformed into device space).
            case /*"pixelheight"*/174:
                return 0x4f9; // The height of the coordsize in device pixels.
            case /*"emuwidth"*/175:
                return 0x4fc; // The width of the coordsize in EMUs.
            case /*"emuheight"*/176:
                return 0x4fd; // The height of the coordsize in EMUs.
            case /*"emuwidth2"*/177:
                return 0x4fe; // Half the width of the coordsize in EMUs.
            case /*"emuheight2"*/178:
                return 0x4ff; // Half the height of the coordsize in EMUs.
            default:
            {
                if (msString.startsWith(value, "@", StringComparison.ORDINAL))
                    return FormatterPal.parseInt(msString.trimStart(value, '@')) + 0x400;  // formula reference
                else if (msString.startsWith(value, "#", StringComparison.ORDINAL))
                    return FormatterPal.parseInt(msString.trimStart(value, '#')) + 0x147; // adjustment reference
                else
                    throw new IllegalStateException("Unrecognized operand value in the formula.");
            }
        }
    }

    private static void readPath(ShapeBase shape, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"arrowok"*/179:
                    setBoolAttribute(shape, ShapeAttr.LINE_ARROW_HEADS_OK, value);
                    break;
                case /*"fillok"*/180:
                    setBoolAttribute(shape, ShapeAttr.GEOMETRY_FILL_OK, value);
                    break;
                case /*"strokeok"*/181:
                    setBoolAttribute(shape, ShapeAttr.GEOMETRY_LINE_OK, value);
                    break;
                case /*"shadowok"*/182:
                    setBoolAttribute(shape, ShapeAttr.GEOMETRY_SHADOW_OK, value);
                    break;
                case /*"extrusionok"*/183:
                {
                    // WORDSNET-22286 Resilent reading of non-standard boolean values as false.
                    setBoolAttribute(shape, ShapeAttr.GEOMETRY_THREE_DOK, value, false);
                    break;
                }
                case /*"gradientshapeok"*/184:
                    setBoolAttribute(shape, ShapeAttr.GEOMETRY_FILL_SHADE_SHAPE_OK, value);
                    break;
                case /*"textpathok"*/185:
                    setBoolAttribute(shape, ShapeAttr.GEOMETRY_G_TEXT_OK, value);
                    break;
                case /*"limo"*/186:
                {
                    int[] limo = VmlUtil.vmlToIntArray(value);
                    int x = (limo.length > 0) ? limo[0] : 0;
                    int y = (limo.length > 1) ? limo[1] : 0;
                    shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_X_LIMO, x);
                    shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_Y_LIMO, y);
                    break;
                }
                case /*"connecttype"*/187: // o:connecttype
                    shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_CONNECTION_SITE_TYPE, VmlEnum.vmlToConnectionSiteType(value));
                    break;
                case /*"connectlocs"*/188: // o:connectlocs
                    setConnectLocs(shape, value);
                    break;
                case /*"connectangles"*/189: // o:connectangles
                    setConnectAngles(shape, value);
                    break;
                case /*"textboxrect"*/190:
                    setTexboxRect(shape, value);
                    break;
                // andrnosk: WORDSNET-9633 Read Path Definition string containing the commands that define the shape's path.
                case /*"v"*/191:
                    setPath(shape, value);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static void setConnectLocs(ShapeBase shape, String value)
    {
        String[] values = msString.split(value, G_CONNECT_LOCS_SPLIT_CHARS);
        PathPoint[] connectLocs = new PathPoint[values.length / 2];

        for (int i = 0; i < values.length / 2; i++)
        {
            connectLocs[i] = new PathPoint(
                VmlPathReader.parsePathValue(values[2 * i]),
                VmlPathReader.parsePathValue(values[2 * i + 1]));
        }

        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_CONNECT_LOCS, connectLocs);
    }

    private static void setConnectAngles(ShapeBase shape, String value)
    {
        String[] values = msString.split(value, ',');
        int[] connectAngles = new int[values.length];

        for (int i = 0; i < values.length; i++)
            connectAngles[i] = readDegrees(values[i]);

        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_CONNECT_ANGLES, connectAngles);
    }

    private static void setTexboxRect(ShapeBase shape, String value)
    {
        String[] rectStrings = msString.split(value, ';');
        PathRectangle[] rects = new PathRectangle[rectStrings.length];

        for (int i = 0; i < rects.length; i++)
        {
            String[] values = msString.split(rectStrings[i], ',');
            PathRectangle rect = new PathRectangle();
            if (values.length == 4)
            {
                rect.Left = VmlPathReader.parsePathValue(values[0]);
                rect.Top = VmlPathReader.parsePathValue(values[1]);
                rect.Right = VmlPathReader.parsePathValue(values[2]);
                rect.Bottom = VmlPathReader.parsePathValue(values[3]);
            }
            rects[i] = rect;
        }

        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_PATH_TEXT_BOX_RECTS, rects);
    }

    private static void readTextPath(ShapeBase shape, NrxXmlReader reader)
    {
        // The following attributes are 'true' by default for all GeoText(TextPath) shapes.
        shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_ON, true);
        shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_STRETCH, true);

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"on"*/151:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_ON, value);
                    break;
                case /*"style"*/29:
                    readTextPathStyle(shape, value);
                    break;
                case /*"fitshape"*/192:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_STRETCH, value);
                    break;
                case /*"trim"*/193:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_SHRINK_FIT, value);
                    break;
                case /*"fitpath"*/194:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_BEST_FIT, value);
                    break;
                case /*"xscale"*/195:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_DX_MEASURE, value);
                    break;
                case /*"string"*/196:
                    shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_TEXT, value);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static void readTextPathStyle(ShapeBase shape, String style)
    {
        VmlNameValue[] props = vmlToNameValueArray(style);

        for (int i = 0; i < props.length; i++)
        {
            String value = props[i].getValue();

            switch (gStringSwitchMap.of(props[i].getName()))
            {
                case /*"font-family"*/197:
                    // Normally in MS generated WordML the font-family name is enclosed in double quotes ("). But we should expect it to be enclosed in single quotes(') as well.
                    shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_FONT, msString.trim(value, new char[] { '"', '\'' }));
                    break;
                case /*"font-size"*/198:
                    // andrnosk: Pass UseDefaultUnits=false because according to specification ECMA 376 6.1.2.23 the font size is always defined in points,
                    // and we do not need to change units if it is not specified.
                    shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_SIZE, ConvertUtilCore.doubleToFixed(readDistancePoints(value, ShapePr.GEO_TEXT_SIZE_DEFAULT, false)));
                    break;
                case /*"font-style"*/199:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_ITALIC, "italic", "normal", value);
                    break;
                case /*"font-weight"*/200:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_BOLD, "bold", "normal", value);
                    break;
                case /*"font-variant"*/201:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_SMALL_CAPS, "small-caps", "normal", value);
                    break;
                case /*"text-decoration"*/202:
                {
                    switch (gStringSwitchMap.of(value))
                    {
                        case /*"underline"*/211:
                            shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_UNDERLINE, true);
                            break;
                        case /*"line-through"*/212:
                            shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_STRIKE_THROUGH, true);
                            break;
                        default:
                            // Skip other values.
                            break;
                    }
                    break;
                }
                case /*"mso-text-shadow"*/203:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_SHADOW, "auto", "normal", value);
                    break;
                case /*"v-text-align"*/204:
                    shape.setShapeAttrInternal(ShapeAttr.GEO_TEXT_ALIGN, VmlEnum.vmlToTextPathAlignment(value));
                    break;
                case /*"v-text-spacing-mode"*/205:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_TIGHT, "tightening", "tracking", value);
                    break;
                case /*"v-text-spacing"*/206:
                    setFixedAttribute(shape, ShapeAttr.GEO_TEXT_SPACING, value);
                    break;
                case /*"v-text-kern"*/207:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_KERNING, value);
                    break;
                case /*"v-text-reverse"*/208:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_REVERSE_ROWS, value);
                    break;
                case /*"v-same-letter-heights"*/209:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_NORMALIZE, value);
                    break;
                case /*"v-rotate-letters"*/210:
                    setBoolAttribute(shape, ShapeAttr.GEO_TEXT_VERTICAL, value);
                    break;
                default:
                    // Ignore others.
                    break;
            }
        }
    }

    private static void readHandles(ShapeBase shape, NrxXmlReader reader)
    {
        ArrayList<Handle> handleList = new ArrayList<Handle>();

        while (reader.readChild("handles"))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"h"*/213: // v:h
                    msArrayList.add(handleList, readHandle(reader));
                    break;
                default:
                    reader.ignoreElement();
                    break;
            }
        }

        Handle[] handles = msArrayList.toArray(handleList, new Handle[0]);
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_HANDLES, handles);
    }

    private static Handle readHandle(NrxXmlReader reader)
    {
        Handle h = new Handle();

        // Set these values to 'empty' values.
        h.XMin = new PathValue(Integer.MIN_VALUE);
        h.XMax = new PathValue(Integer.MAX_VALUE);

        h.YMin = new PathValue(Integer.MIN_VALUE);
        h.YMax = new PathValue(Integer.MAX_VALUE);

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"position"*/126:
                {
                    HandlePoint position = readHandlePoint(value);
                    h.PositionX = position.getX();
                    h.PositionY = position.getY();
                    break;
                }
                case /*"switch"*/214:
                    h.HasSwitch = true;
                    break;
                case /*"polar"*/215:
                {
                    h.HasPolar = true;
                    PathPoint polar = readPathPoint(value);
                    h.PolarX = polar.getX();
                    h.PolarY = polar.getY();
                    break;
                }
                case /*"map"*/216:
                {
                    h.HasMap = true;
                    PathPoint polar = readPathPoint(value);
                    h.PolarX = polar.getX();
                    h.PolarY = polar.getY();
                    break;
                }
                case /*"radiusrange"*/217:
                {
                    h.HasRadiusRange = true;
                    PathPoint range = readPathPoint(value);
                    h.XMin = range.getX();
                    h.XMax = range.getY();
                    break;
                }
                case /*"xrange"*/218:
                {
                    h.HasXYRange = true;
                    PathPoint range = readPathPoint(value);
                    h.XMin = range.getX();
                    h.XMax = range.getY();
                    break;
                }
                case /*"yrange"*/219:
                {
                    h.HasXYRange = true;
                    PathPoint range = readPathPoint(value);
                    h.YMin = range.getX();
                    h.YMax = range.getY();
                    break;
                }
                default:
                    // Ignore other attributes.
                    break;
            }
        }

        return h;
    }

    private static HandlePoint readHandlePoint(String value)
    {
        String[] subvalues = msString.split(value, ',');
        return new HandlePoint(getHandleValue(subvalues[0]), getHandleValue(subvalues[1]));
    }

    private static PathPoint readPathPoint(String value)
    {
        String[] subvalues = msString.split(value, ',');
        return new PathPoint(
            getPathValue(subvalues[0]),
            getPathValue(subvalues[1]));
    }

    private static PathValue getPathValue(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"topLeft"*/220:
                return new PathValue(0, true);
            case /*"bottomRight"*/221:
                return new PathValue(1, true);
            case /*"center"*/222:
                return new PathValue(2, true);
            default:
                // Process other values below.
                break;
        }

        if (msString.startsWith(value, "@", StringComparison.ORDINAL))
            return new PathValue(FormatterPal.parseInt(msString.trimStart(value, '@')) + 3, true);

        if (msString.startsWith(value, "#", StringComparison.ORDINAL))
            return new PathValue(FormatterPal.parseInt(msString.trimStart(value, '#')) + 256, true);

        return new PathValue(FormatterPal.parseInt(value), false);
    }

    private static HandlePositionValue getHandleValue(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"topLeft"*/220:
                return new HandlePositionValue(HandlePositionType.LEFT_TOP, 0);
            case /*"bottomRight"*/221:
                return new HandlePositionValue(HandlePositionType.RIGHT_BOTTOM, 0);
            case /*"center"*/222:
                return new HandlePositionValue(HandlePositionType.CENTER, 0);
            default:
                // Process other values below.
                break;
        }

        if (msString.startsWith(value, "@", StringComparison.ORDINAL))
            return new HandlePositionValue(HandlePositionType.FORMULA, FormatterPal.parseInt(msString.trimStart(value, '@')));

        if (msString.startsWith(value, "#", StringComparison.ORDINAL))
            return new HandlePositionValue(HandlePositionType.ADJUST, FormatterPal.parseInt(msString.trimStart(value, '#')));

        return new HandlePositionValue(HandlePositionType.CONSTANT, FormatterPal.parseInt(value));
    }

    private static void readLock(ShapeBase shape, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"ext"*/95: // v:ext
                    // Always "edit", ignore.
                    break;
                case /*"aspectratio"*/223:
                    setBoolAttribute(shape, ShapeAttr.LOCK_ASPECT_RATIO, value);
                    break;
                case /*"verticies"*/224:
                    setBoolAttribute(shape, ShapeAttr.LOCK_VERTICES, value);
                    break;
                case /*"text"*/225:
                    setBoolAttribute(shape, ShapeAttr.LOCK_TEXT, value);
                    break;
                case /*"shapetype"*/5:
                    setBoolAttribute(shape, ShapeAttr.LOCK_SHAPE_TYPE, value);
                    break;
                case /*"grouping"*/226:
                    setBoolAttribute(shape, ShapeAttr.LOCK_AGAINST_GROUPING, value);
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }
    }

    private static boolean readTextbox(IVmlShapeReaderContext context, ShapeBase shape) throws Exception
    {
        NrxXmlReader reader = context.getXmlReader();

        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"style"*/29:
                    readTextboxStyle(shape, reader.getValue());
                    break;
                case /*"inset"*/227:
                    setInset(shape, reader.getValue());
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }

        // When shape is loaded, it is not added to the document tree immediately,
        // it is returned to the caller and the caller adds it to the tree.
        return context.readTextboxContent(shape);
    }

    private static void readTextboxStyle(ShapeBase shape, String style)
    {
        VmlNameValue[] props = vmlToNameValueArray(style);

        String layoutFlow = null;
        String layoutFlowAlt = null;

        for (int i = 0; i < props.length; i++)
        {
            String value = props[i].getValue();

            switch (gStringSwitchMap.of(props[i].getName()))
            {
                case /*"layout-flow"*/228:
                    layoutFlow = value;
                    break;
                case /*"mso-layout-flow-alt"*/229:
                    layoutFlowAlt = value;
                    break;
                case /*"mso-fit-shape-to-text"*/230:
                    setBoolAttribute(shape, ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT, value);
                    break;
                case /*"mso-next-textbox"*/231:
                    // Textboxes linked by "id" rather than by "spid" in DOCX.
                    shape.setShapeAttrInternal(ShapeAttr.SYS_TEXTBOX_NEXT_SHAPE_ID_RAW, msString.trim(value, '#'));
                    break;
                default:
                    // Ignore other properties.
                    break;
            }
        }

        if (layoutFlow != null)
        {
            if ("vertical".equals(layoutFlow) && "bottom-to-top".equals(layoutFlowAlt))
                shape.setShapeAttrInternal(ShapeAttr.TEXTBOX_LAYOUT_FLOW, LayoutFlow.BOTTOM_TO_TOP);
            else
                shape.setShapeAttrInternal(ShapeAttr.TEXTBOX_LAYOUT_FLOW, VmlEnum.vmlToLayoutFlow(layoutFlow));
        }
    }

    private static void readCallout(ShapeBase shape, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"ext"*/95:
                    // Always "edit", ignore.
                    break;
                case /*"type"*/26:
                    shape.setShapeAttrInternal(ShapeAttr.CALLOUT_TYPE, VmlEnum.vmlToCalloutType(value));
                    break;
                case /*"gap"*/232:
                    setEmusAttribute(shape, ShapeAttr.CALLOUT_GAP, value);
                    break;
                case /*"angle"*/233:
                    shape.setShapeAttrInternal(ShapeAttr.CALLOUT_ANGLE, VmlEnum.vmlToCalloutAngle(value));
                    break;
                case /*"drop"*/234:
                    shape.setShapeAttrInternal(ShapeAttr.CALLOUT_DROP_TYPE, VmlEnum.vmlToCalloutDropType(value));
                    break;
                case /*"distance"*/235:
                    setEmusAttribute(shape, ShapeAttr.CALLOUT_DROP_DISTANCE, value);
                    break;
                case /*"length"*/236:
                    setEmusAttribute(shape, ShapeAttr.CALLOUT_LENGTH, value);
                    break;
                case /*"on"*/151:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_ON, value);
                    break;
                case /*"accentbar"*/237:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_ACCENT_BAR, value);
                    break;
                case /*"textborder"*/238:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_TEXT_BORDER, value);
                    break;
                case /*"minusx"*/239:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_MINUS_X, value);
                    break;
                case /*"minusy"*/240:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_MINUS_Y, value);
                    break;
                case /*"dropauto"*/241:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_DROP_AUTO, value);
                    break;
                case /*"lengthspecified"*/242:
                    setBoolAttribute(shape, ShapeAttr.CALLOUT_LENGTH_SPECIFIED, value);
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'w10:wrap' element.
    /// </summary>
    private static void readWrap(ShapeBase shape, NrxXmlReader reader)
    {
        // alexnosk Part of WORDSNET-8783 Fix: Do not set default wrap type,
        // instead default value will be taken from inherited defaults.

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"type"*/26:
                {
                    // alexnosk Part of WORDSNET-8783 Fix: if wrap type is inline
                    // and wrap type is already set in direct attributes - leave attribute unchanged.
                    // It is already set to None or Inline value depending on position.
                    /*WrapType*/int wrapType = VmlEnum.vmlToWrapType(value);
                    if (shape.getShapePr().contains(ShapeAttr.WRAP_TYPE) && (shape.getWrapType() == WrapType.INLINE))
                        break;

                    shape.setShapeAttrInternal(ShapeAttr.WRAP_TYPE, wrapType);
                    break;
                }
                case /*"side"*/243:
                    shape.setShapeAttrInternal(ShapeAttr.WRAP_SIDE, VmlEnum.vmlToWrapSide(value));
                    break;
                case /*"anchorx"*/244:
                case /*"anchory"*/245:
                    // RK These settings seem to duplicate mso-horizontal-position-relative
                    // and mso-vertical-position-relative, yet they sometimes contradict them.
                    // I think it is safer to ignore these values.
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }
    }

    private static void readBorder(ShapeBase shape, int borderAttr, NrxXmlReader reader)
    {
        ShapePr shapePr = shape.getShapePr();
        Border border = (Border)shapePr.getDirectAttr(borderAttr);

        // If there is no border in shape yet, then create it.
        if (border == null)
        {
            border = new Border();
            shapePr.setAttr(borderAttr, border);
        }

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"type"*/26:
                    border.setLineStyleInternal(VmlEnum.vmlToLineStyle(value));
                    break;
                case /*"width"*/110:
                    border.setLineWidthSafe(FormatterPal.parseDouble(value) / 8.0);
                    border.validateNoneBorder();
                    break;
                case /*"shadow"*/78:
                    border.setShadow(("t".equals(value)));
                    break;
                default:
                    // Ignore other attributes.
                    break;
            }
        }
    }

    private static Border createBorderWithColor(String vmlcolor)
    {
        Border border = new Border();
        border.setColorInternal(VmlColor.vmlToColor(vmlcolor));
        return border;
    }

    private static void setAdjustments(ShapeBase shape, String adjustments)
    {
        if (!StringUtil.hasChars(adjustments))
            return;

        String[] values = msString.split(adjustments, ',');

        for (int i = 0; i < values.length; i++)
        {
            String value = values[i];

            if (!"".equals(value))
                shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_ADJUST_1 + i, FormatterPal.parseInt(value));
        }
    }

    private static void setPath(ShapeBase shape, String path)
    {
        if (!StringUtil.hasChars(path))
            return;

        VmlPathReader pathReader = new VmlPathReader(path);
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_SEGMENT_INFO, pathReader.getPathInfos());
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES, pathReader.getPoints());
    }

    private static void setPoints(ShapeBase shape, String value)
    {
        if (!StringUtil.hasChars(value))
            return;

        VmlQuantity[] values = vmlToQuantityArray(value);

        // Build an array of points and store in the model.
        PathPoint[] points = new PathPoint[values.length / 2];
        for (int i = 0; i < points.length; i++)
        {
            points[i] = new PathPoint(
                values[2 * i].distanceToPathValue(),
                values[2 * i + 1].distanceToPathValue());
        }
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_VERTICES, points);

        // Build an array of path infos and store in the model.
        // This seems to be how MS Word does it for DOC files.
        PathInfo[] pathInfos = new PathInfo[points.length * 2 + 1];
        pathInfos[0] = new PathInfo(PathType.MOVE_TO, 0);
        for (int i = 1; i < pathInfos.length - 1; i++)
        {
            if (MathUtil.isOdd(i))
                pathInfos[i] = new PathInfo(PathType.ESCAPE_AUTO_LINE, 0);
            else
                pathInfos[i] = new PathInfo(PathType.LINE_TO, 1);
        }
        pathInfos[pathInfos.length - 1] = new PathInfo(PathType.END, 0);
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_SEGMENT_INFO, pathInfos);
    }

    private static void setInset(ShapeBase shape, String inset)
    {
        String[] values = msString.split(inset, ',');

        int index = 0;
        int maxIndex = values.length - 1;

        for (int attr = ShapeAttr.TEXTBOX_LEFT; attr <= ShapeAttr.TEXTBOX_BOTTOM; attr++)
        {
            setInsetAttribute(shape, attr, values[index]);

            index++;

            if (index > maxIndex)
                return;
        }
    }

    /// <summary>
    /// Sets the specified inset margin attribute.
    /// </summary>
    private static void setInsetAttribute(ShapeBase shape, int shapeAttr, String value)
    {
        VmlQuantity quantity = new VmlQuantity(value);
        if (!quantity.isDistance())
            return;

        // VmlQuantity uses points as default units when units are not specified, but inset margins expect EMUs
        // as default units. So, use VmlQuantity to perform the necessary conversions only if units are specified
        // in 'value'.
        // Since quantity.IsDistance is 'true', it means that the value was successfully parsed as double, do the
        // same here and convert to 'int'.
        int margin = quantity.isWithoutUnits()
            ? MathUtil.doubleToInt(FormatterPal.parseDouble(value))
            : quantity.distanceToEmus();

        shape.setShapeAttrInternal(shapeAttr, margin);
    }

    /// <summary>
    /// Converts distance from WordML string to numeric value in points.
    /// If units in specified string are 'in' or 'mm' then the numeric value is converted to points.
    /// If units are "pt" or not specified then the value is returned as is.
    /// If the value is not recognized, the defaultValue is returned.
    /// </summary>
    private static double readDistancePoints(String value, double defaultValue, boolean usePixelDefaultUnits)
    {
        // andrnosk: WORDSNET-7668 According to specification ECMA 376 (6.1.2.1 page 4362).
        // Upon height/weight specifying if no units are given, pixels (px) is assumed.
        // This rule is not applied for shapes and other elements inside group shape (like lines etc).
        VmlQuantity q = new VmlQuantity(value, usePixelDefaultUnits ? VmlQuantity.PIXEL_UNITS : VmlQuantity.POINT_UNITS);

        return q.isDistance() ? q.distanceToPoints() : defaultValue;
    }

    /// <summary>
    /// Converts angle magnitude from WordML string to numeric value.
    /// If units in specified string are 'fd' then the value is returned as is.
    /// If units are not specified then the value is returned multiplied by 0x10000.
    /// If the units are not recognized or no valid numeric value is found then 0 is returned.
    /// </summary>
    private static int readDegrees(String value)
    {
        VmlQuantity q = new VmlQuantity(value);
        return q.isDegrees() ? q.degreesToFixed() : 0;
    }

    /// <summary>
    /// Reads shape's style attribute.
    /// </summary>
    private static VmlNameValue[] vmlToNameValueArray(String style)
    {
        String[] props = msString.split(style, ';');

        VmlNameValue[] nameValues = new VmlNameValue[props.length];

        for (int i = 0; i < props.length; i++)
            nameValues[i] = VmlNameValue.fromString(props[i]);

        // WORDSNET-10921 The problem occurred because there was empty entry in the props array.
        // WORDSNET-13610 The problem is very similar to 10921, but in this case in props array there is invalid data.
        // In both cases final nameValues array can contain null entries. so to fix we need to remove null entries from array.
        return removeNullEntries(nameValues);
    }

    /// <summary>
    /// Removes null entries from VmlNameValue array.
    /// </summary>
    private static VmlNameValue[] removeNullEntries(VmlNameValue[] input)
    {
        // First we should optimize the array, i.e. move all not null entries to the beginning of the array.
        int newIndex = 0;
        for (int oldIndex = 0; oldIndex < input.length; oldIndex++)
        {
            if (input[oldIndex] != null)
                input[newIndex++] = input[oldIndex];
        }

        // Now just copy array from 0 to newIndex position to another array.
        VmlNameValue[] output = new VmlNameValue[newIndex];
        for (int i = 0; i < output.length; i++)
            output[i] = input[i];

        return output;
    }

    /// <summary>
    /// In VML roundrect arcsize is stored in the "arcsize" attribute, but in other formats
    /// and in the model it is stored as adj1 and in different units.
    /// </summary>
    private static void arcSizeToAdj1(ShapeBase shape, String value)
    {
        VmlQuantity q = new VmlQuantity(value);

        // The arcsize value in VML is percent but can be specified either as percent or fixed.
        double percent;
        if (q.isPercent())
            percent = (double)q.percentToInt() / 100.0;
        else if (q.isFixed())
            percent = ConvertUtilCore.fixedToDouble(q.toFixed());
        else
            return;

        // In the model arcsize is stored as adj1 value from 0 to coordsizewidth.
        int adj1 = MathUtil.doubleToInt(percent * shape.getCoordSizeWidth());
        shape.setShapeAttrInternal(ShapeAttr.GEOMETRY_ADJUST_1, adj1);
    }

    /// <summary>
    /// Sets shape type from the specified string <paramref name="type"/>.
    /// </summary>
    private static void setShapeType(ShapeBase shape, String type) throws Exception
    {
        // WORDSNET-14341 It seems, MS Word recognizes "#imageNN" in type string as custom shape type.
        if (msString.startsWith(type, "#image", StringComparison.ORDINAL))
            shape.setShapeAttrInternal(ShapeAttr.SHAPE_TYPE, ShapeType.CUSTOM_SHAPE);
        else
            setIdentifierAttrSafe(shape, ShapeAttr.SHAPE_TYPE, type);
    }

    /// <summary>
    /// Sets the <see cref="ShapeAttr.FillImageBytes"/> attributes from the deferred bindings.
    /// </summary>
    private static void resolveFillSources(IVmlShapeReaderContext context) throws Exception
    {
        Debug.assertTrue((context != null) && (context.getXmlReader() != null) &&
            (context.getXmlReader().getFillSourceMap() != null));

        HashMap<ShapeBase, String> fillSources = context.getXmlReader().getFillSourceMap();
        if (fillSources.size() == 0)
            return;

        ArrayList<ShapeBase> processedShapes = new ArrayList<ShapeBase>();

        for (ShapeBase shape : (Iterable<ShapeBase>) fillSources.keySet())
            if (processFillSource(context, shape, fillSources.get(shape)))
                msArrayList.add(processedShapes, shape);

        for (ShapeBase shape : processedShapes)
            msDictionary.remove(fillSources, shape);

        processedShapes.clear();
    }

    /// <summary>
    /// Processes the fill source attribute using the specified context.
    /// </summary>
    private static boolean processFillSource(IVmlShapeReaderContext context, ShapeBase shape, String sourcePath) throws Exception
    {
        Debug.assertTrue((context != null) && (shape != null) && (sourcePath != null));

        boolean isExternalImage = context.isExternalImage(sourcePath);

        // andrnosk: WORDSNET-6001 In the document there were fill with the Gif image,
        // we do not fully support Gif in model, so convert it to PNG as we do for image data.
        byte[] imageBytes = (isExternalImage)
            ? ImageDataUtil.loadImageBytes(context.getRelationshipTarget(sourcePath), context.getDocument())
            : ImageDataCore.getImageBytes(context.getBinData(sourcePath));

        if ((imageBytes == null) || (imageBytes.length == 0))
            return isExternalImage;

        shape.setShapeAttrInternal(ShapeAttr.FILL_IMAGE_BYTES, imageBytes);
        return true;
    }

    private static /*final*/ char[] G_CONNECT_LOCS_SPLIT_CHARS = new char[] { ',', ';' };

    /// <summary>
    /// Specifies maximum allowed nesting level for group shapes.
    /// </summary>
    private static final int MAX_GROUP_NESTING_LEVEL = 500;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"dxaOrig",
		"dyaOrig",
		"binData",
		"background",
		"bgcolor",
		"shapetype",
		"group",
		"shape",
		"polyline",
		"image",
		"rect",
		"roundrect",
		"line",
		"oval",
		"scriptAnchor",
		"arc",
		"curve",
		"ocx",
		"control",
		"OLEObject",
		"language",
		"args",
		"scriptText",
		"id",
		"spid",
		"spt",
		"type",
		"editas",
		"alt",
		"style",
		"ole",
		"bordertopcolor",
		"borderleftcolor",
		"borderbottomcolor",
		"borderrightcolor",
		"allowincell",
		"allowoverlap",
		"coordorigin",
		"coordsize",
		"adj",
		"path",
		"points",
		"preferrelative",
		"fill",
		"filled",
		"fillcolor",
		"dgmlayout",
		"dgmnodekind",
		"connectortype",
		"stroked",
		"strokecolor",
		"strokeweight",
		"insetpen",
		"href",
		"target",
		"title",
		"bullet",
		"button",
		"oleicon",
		"bwmode",
		"bwpure",
		"bwnormal",
		"hr",
		"hrstd",
		"hrnoshade",
		"hrpct",
		"hralign",
		"from",
		"to",
		"arcsize",
		"wrapcoords",
		"equationxml",
		"gfxdata",
		"ink",
		"stroke",
		"imagedata",
		"imageData",
		"signatureline",
		"shadow",
		"extrusion",
		"formulas",
		"textpath",
		"handles",
		"lock",
		"textbox",
		"diagram",
		"callout",
		"wrap",
		"anchorlock",
		"bordertop",
		"borderleft",
		"borderbottom",
		"borderright",
		"addlxml",
		"allowcomments",
		"ext",
		"issignatureline",
		"provid",
		"showsigndate",
		"signinginstructions",
		"signinginstructionsset",
		"sigprovurl",
		"suggestedsigner",
		"suggestedsigner2",
		"suggestedsigneremail",
		"text-align",
		"top",
		"margin-top",
		"left",
		"margin-left",
		"width",
		"height",
		"rotation",
		"flip",
		"visibility",
		"z-index",
		"mso-wrap-edited",
		"mso-wrap-distance-left",
		"mso-wrap-distance-top",
		"mso-wrap-distance-right",
		"mso-wrap-distance-bottom",
		"mso-position-horizontal",
		"mso-position-horizontal-relative",
		"mso-position-vertical",
		"mso-position-vertical-relative",
		"mso-wrap-style",
		"position",
		"v-text-anchor",
		"mso-left-percent",
		"mso-top-percent",
		"mso-width-percent",
		"mso-height-percent",
		"mso-width-relative",
		"mso-height-relative",
		"i",
		"annotation",
		"src",
		"joinstyle",
		"dashstyle",
		"linestyle",
		"endcap",
		"startarrow",
		"startarrowwidth",
		"startarrowlength",
		"endarrow",
		"endarrowwidth",
		"endarrowlength",
		"opacity",
		"color",
		"color2",
		"filltype",
		"on",
		"weight",
		"pict",
		"croptop",
		"cropbottom",
		"cropleft",
		"cropright",
		"chromakey",
		"gain",
		"blacklevel",
		"gamma",
		"grayscale",
		"bilevel",
		"embosscolor",
		"recolortarget",
		"f",
		"xcenter",
		"ycenter",
		"xlimo",
		"ylimo",
		"linedrawn",
		"pixellinewidth",
		"pixelwidth",
		"pixelheight",
		"emuwidth",
		"emuheight",
		"emuwidth2",
		"emuheight2",
		"arrowok",
		"fillok",
		"strokeok",
		"shadowok",
		"extrusionok",
		"gradientshapeok",
		"textpathok",
		"limo",
		"connecttype",
		"connectlocs",
		"connectangles",
		"textboxrect",
		"v",
		"fitshape",
		"trim",
		"fitpath",
		"xscale",
		"string",
		"font-family",
		"font-size",
		"font-style",
		"font-weight",
		"font-variant",
		"text-decoration",
		"mso-text-shadow",
		"v-text-align",
		"v-text-spacing-mode",
		"v-text-spacing",
		"v-text-kern",
		"v-text-reverse",
		"v-same-letter-heights",
		"v-rotate-letters",
		"underline",
		"line-through",
		"h",
		"switch",
		"polar",
		"map",
		"radiusrange",
		"xrange",
		"yrange",
		"topLeft",
		"bottomRight",
		"center",
		"aspectratio",
		"verticies",
		"text",
		"grouping",
		"inset",
		"layout-flow",
		"mso-layout-flow-alt",
		"mso-fit-shape-to-text",
		"mso-next-textbox",
		"gap",
		"angle",
		"drop",
		"distance",
		"length",
		"accentbar",
		"textborder",
		"minusx",
		"minusy",
		"dropauto",
		"lengthspecified",
		"side",
		"anchorx",
		"anchory"
	);

}

