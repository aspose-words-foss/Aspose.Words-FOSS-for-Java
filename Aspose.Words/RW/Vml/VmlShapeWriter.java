// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2007 by Vladimir Averkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.IntToObjDictionary;
import com.aspose.FormatterPal;
import com.aspose.ConvertUtilCore;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.Drawing.msPoint;
import com.aspose.Debug;


/// <summary>
/// Writes shape to WordML.
/// This class is static.
/// TODO 2 Refactor this class, get rid of 'props' hashtable and put all shape sub elements writers into separate classes. See VmlDiagramWriter for example.
/// </summary>
/*static*/ class VmlShapeWriter
{
	/* Simulation of static class by using private constructor */
	private VmlShapeWriter()
	{}

    /// <summary>
    /// RK I think this method does not write the closing XML tag!
    /// </summary>
    static void write(ShapeBase shape, NrxXmlBuilder builder, IVmlShapeWriterContext context) throws Exception
    {
        write(shape, false, builder, context);
    }

    /// <summary>
    /// RK I think this method does not write the closing XML tag!
    ///
    /// RK This needs some serious refactoring!
    /// </summary>
    static void write(ShapeBase shape, boolean isBackGround, NrxXmlBuilder builder, IVmlShapeWriterContext context) throws Exception
    {
        String shapeElementName = "v:shape";
        String shapeTypeId = null;
        boolean needShapeTypeXml = false;

        switch (shape.getShapeType())
        {
            case ShapeType.GROUP:
                shapeElementName = "v:group";
                break;
            case ShapeType.NON_PRIMITIVE:
                // TODO 3 Sometimes NonPrimitive is output as polyline and sometimes as shape (see TestNonPrimitiveShape.doc).
                // Don't know how to distinguish between them yet.
                shapeElementName = "v:shape";
                break;
            case ShapeType.RECTANGLE:
                shapeElementName = "v:rect";
                break;
            case ShapeType.ROUND_RECTANGLE:
                shapeElementName = "v:roundrect";
                break;
            case ShapeType.ELLIPSE:
                shapeElementName = "v:oval";
                break;
            case ShapeType.LINE:
                shapeElementName = "v:line";
                break;
            case ShapeType.OLE_CONTROL:
            {
                // Word exports Forms2OleControls as Image shape type.
                IEmbeddedObject embeddedObject = shape.getEmbeddedObject();
                /*ShapeType*/int shapeType = ((embeddedObject != null) && embeddedObject.isForms2OleControlInternal())
                    ? ShapeType.IMAGE
                    : shape.getShapeType();
                shapeTypeId = msString.format("#_x0000_t{0}", (int)shapeType);
                needShapeTypeXml = true;
                break;
            }
            case ShapeType.OLE_OBJECT:
            {
                // This shape type in the model uses the Image shape type in the document.
                shapeTypeId = msString.format("#_x0000_t{0}", (int)ShapeType.IMAGE);
                needShapeTypeXml = true;
                break;
            }
            default:
            {
                shapeTypeId = msString.format("#_x0000_t{0}", (int)shape.getShapeType());
                needShapeTypeXml = true;
                break;
            }
        }

        // Other predefined shapes from VML spec are arc, image and curve. They are defined in the schema but not seen in the existing tests.

        if (isBackGround)
            shapeElementName = "v:background";

        VmlFillWriter fillWriter = new VmlFillWriter(shape, builder, context);
        VmlStrokeWriter strokeWriter = new VmlStrokeWriter(builder, context);
        VmlImagedataWriter imagedataWriter = new VmlImagedataWriter(shape, builder, context);
        VmlShadowWriter shadowWriter = new VmlShadowWriter(builder, context);
        VmlExtrusionWriter extrusionWriter = new VmlExtrusionWriter(builder, context);
        VmlGeoWriter geoWriter = new VmlGeoWriter(shape, builder);
        VmlGeoTextWriter geoTextWriter = new VmlGeoTextWriter(builder);
        VmlLockWriter lockWriter = new VmlLockWriter(builder);
        VmlDiagramWriter diagramWriter = new VmlDiagramWriter(context, builder);
        VmlCalloutWriter calloutWriter = new VmlCalloutWriter(builder);
        VmlSignatureLinesWriter signWriter = new VmlSignatureLinesWriter(builder);

        String diagramLayout = null;
        String diagramNodeKind = null;

        // Collect existing attributes.
        IntToObjDictionary<Object> props = new IntToObjDictionary<Object>();

        ShapePr shapePr = shape.getShapePr();
        for (int i = 0; i < shapePr.getCount(); i++)
        {
            int key = shapePr.getKey(i);
            Object value = shapePr.getByIndex(i);

            // Perform check what sub-elements of shape should be written.
            switch (key & ShapeAttrCategory.CATEGORY_MASK)
            {
                case ShapeAttrCategory.FILL:
                    fillWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.LINE:
                    if (key == ShapeAttr.LINE_ARROW_HEADS_OK)
                        geoWriter.addAttribute(key, value);
                    else
                        strokeWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.BLIP:
                    imagedataWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.SHADOW:
                    shadowWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.THREE_D_OBJECT:
                case ShapeAttrCategory.THREE_D_STYLE:
                    extrusionWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.GEOMETRY:
                    geoWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.GEO_TEXT:
                    geoTextWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.PROTECTION:
                    lockWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.DIAGRAM:
                    diagramWriter.addAttribute(key, value);
                    break;
                case ShapeAttrCategory.CALLOUT:
                    calloutWriter.addAttribute(key, value);
                    break;
                default:
                {
                    // All other attribute categories are handled to here. Act on the attribute key.
                    switch (key)
                    {
                        case ShapeAttr.FILL_IMAGE_BYTES:
                            fillWriter.addAttribute(key, value);
                            break;
                        case ShapeAttr.LINE_IMAGE_BYTES:
                            strokeWriter.addAttribute(key, value);
                            break;
                        case ShapeAttr.IMAGE_BYTES:
                        case ShapeAttr.IMAGE_SOURCE_FULL_NAME:
                        case ShapeAttr.IMAGE_TITLE:
                            imagedataWriter.addAttribute(key, value);
                            break;
                        case ShapeAttr.LOCK_SHAPE_TYPE:
                            lockWriter.addAttribute(key, value);
                            break;
                        case ShapeAttr.BW_MODE:
                        case ShapeAttr.BW_NORMAL:
                        case ShapeAttr.BW_PURE:
                            props.set(key, VmlEnum.bWModeToVml((/*BWMode*/Integer)value));
                            break;
                        case ShapeAttr.DIAGRAM_NODE_LAYOUT:
                            diagramLayout = FormatterPal.intToXml((/*int*/Integer)value);
                            break;
                        case ShapeAttr.DIAGRAM_NODE_KIND:
                            diagramNodeKind = FormatterPal.intToXml((/*int*/Integer)value);
                            break;
                        case ShapeAttr.COORD_ORIGIN_X:
                        case ShapeAttr.COORD_ORIGIN_Y:
                        case ShapeAttr.COORD_SIZE_WIDTH:
                        case ShapeAttr.COORD_SIZE_HEIGHT:
                            props.set(key, value);
                            break;
                        case ShapeAttr.FLIP:
                            props.set(key, VmlEnum.flipOrientationToVml((/*FlipOrientation*/Integer)value));
                            break;
                        case ShapeAttr.HORIZONTAL_ALIGNMENT:
                        {
                            /*HorizontalAlignment*/int ha = (/*HorizontalAlignment*/Integer)value;
                            if (ha != HorizontalAlignment.NONE)
                                props.set(key, StyleConvertUtil.horizontalAlignmentToXml(ha));
                            break;
                        }
                        case ShapeAttr.RELATIVE_HORIZONTAL_POSITION:
                        {
                            /*RelativeHorizontalPosition*/int rhp = (/*RelativeHorizontalPosition*/Integer)value;
                            if (rhp != RelativeHorizontalPosition.COLUMN)
                                props.set(key, StyleConvertUtil.relativeHorizontalPositionToXml(rhp));
                            break;
                        }
                        case ShapeAttr.VERTICAL_ALIGNMENT:
                        {
                            /*VerticalAlignment*/int va = (/*VerticalAlignment*/Integer)value;
                            if (va != VerticalAlignment.NONE)
                                props.set(key, StyleConvertUtil.verticalAlignmentToXml(va));
                            break;
                        }
                        case ShapeAttr.RELATIVE_VERTICAL_POSITION:
                        {
                            /*RelativeVerticalPosition*/int rvp = (/*RelativeVerticalPosition*/Integer)value;
                            if (rvp != RelativeVerticalPosition.PARAGRAPH)
                                props.set(key, StyleConvertUtil.relativeVerticalPositionToXml(rvp));
                            break;
                        }
                        case ShapeAttr.DISTANCE_BOTTOM:
                        case ShapeAttr.DISTANCE_LEFT:
                        case ShapeAttr.DISTANCE_RIGHT:
                        case ShapeAttr.DISTANCE_TOP:
                            props.set(key, ConvertUtilCore.emuToPoint((/*int*/Integer)value));
                            break;
                        case ShapeAttr.CONNECTOR_TYPE:
                            props.set(key, VmlEnum.connectorTypeToVml((/*ConnectorType*/Integer)value));
                            break;
                        case ShapeAttr.TEXTBOX_WRAP_MODE:
                            props.set(key, VmlEnum.textboxWrapModeToVml((/*TextBoxWrapMode*/Integer)value));
                            break;
                        case ShapeAttr.TEXTBOX_ANCHOR:
                            props.set(key, VmlEnum.textboxAnchorToVml((/*TextBoxAnchor*/Integer)value));
                            break;
                        case ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT:
                            if ((/*boolean*/Boolean)value)
                                props.set(key, "t");
                            break;
                        case ShapeAttr.HR_ALIGN:
                        {
                            /*HorizontalRuleAlignment*/int horizontalRuleAlignment = (/*HorizontalRuleAlignment*/Integer)value;
                            if (horizontalRuleAlignment != HorizontalRuleAlignment.LEFT)
                                props.set(key, VmlEnum.hRAlignToVml(horizontalRuleAlignment));
                            break;
                        }

                        case ShapeAttr.LEFT_PERCENT:
                        case ShapeAttr.TOP_PERCENT:
                        case ShapeAttr.WIDTH_PERCENT:
                        case ShapeAttr.HEIGHT_PERCENT:
                            props.set(key, FormatterPal.intToStr((/*int*/Integer)value));
                            break;
                        case ShapeAttr.RELATIVE_WIDTH:
                            props.set(key, StyleConvertUtil.relativeWidthToXml((/*RelativeHorizontalSize*/Integer)value));
                            break;
                        case ShapeAttr.RELATIVE_HEIGHT:
                            props.set(key, StyleConvertUtil.relativeHeightToXml((/*RelativeVerticalSize*/Integer)value));
                            break;
                        case ShapeAttr.SIG_SETUP_ADDL_XML:
                        case ShapeAttr.SIG_SETUP_ALLOW_COMMENTS:
                        case ShapeAttr.SIG_SETUP_ID:
                        case ShapeAttr.IS_SIGNATURE_LINE:
                        case ShapeAttr.SIG_SETUP_PROV_ID:
                        case ShapeAttr.SIG_SETUP_SHOW_SIGN_DATE:
                        case ShapeAttr.SIG_SETUP_SIGN_INST:
                        case ShapeAttr.SIG_SETUP_PROV_URL:
                        case ShapeAttr.SIG_SETUP_SUGG_SIGNER:
                        case ShapeAttr.SIG_SETUP_SUGG_SIGNER_2:
                        case ShapeAttr.SIG_SETUP_SUGG_SIGNER_EMAIL:
                        case ShapeAttr.SIG_SETUP_SIGN_INST_SET:
                            signWriter.addAttribute(key, value);
                            break;
                        default:
                            props.set(key, value);
                            break;
                    }
                    break;
                }
            }
        }

        // RK Here writing begins.

        if (!context.isDocx() && (props.get(ShapeAttr.SCRIPT_ANCHOR) != null) && (/*boolean*/Boolean)props.get(ShapeAttr.SCRIPT_ANCHOR))
        {
            String scriptLanguageName;

            if (props.get(ShapeAttr.SCRIPT_LANGUAGE_NAME) != null)
                scriptLanguageName = ms.as(props.get(ShapeAttr.SCRIPT_LANGUAGE_NAME), String.class);
            else
                scriptLanguageName = VmlEnum.scriptLanguageToVml(props.get(ShapeAttr.SCRIPT_LANGUAGE));

            builder.startElement("w:scriptAnchor");
            builder.writeElement("w:language", scriptLanguageName);
            builder.writeElement("w:args", ms.as(props.get(ShapeAttr.SCRIPT_TYPE), String.class));
            builder.writeElement("w:scriptText", ms.as(props.get(ShapeAttr.SCRIPT_TEXT), String.class));

            // RK Note we exit here.
            return;
        }

        // Write shape type definition from Resources\ShapeTypes.txt.
        if (needShapeTypeXml && !context.getShapeTypesWritten().contains(shapeTypeId))
        {
            context.getShapeTypesWritten().add(shapeTypeId);
            // shapeTypeId string has format "#_x0000_t{0}"
            String shapeTypeXml = ShapeTypeLibrary.getShapeTypeXml(shapeTypeId);
            if (StringUtil.hasChars(shapeTypeXml))
            {
                builder.writeRaw("\r\n");
                builder.writeRaw(shapeTypeXml);
                builder.writeRaw("\r\n");
            }
        }

        if (imagedataWriter.getImageBytes() != null)
        {
            imagedataWriter.setImageName(context.writeImageBinData(imagedataWriter.getImageBytes()));

            // andrnosk: WORDSNET-6431 If SourceFullName is an empty string or null, the image is not linked.
            if (StringUtil.hasChars(imagedataWriter.getImageSourceFullName()) && (!msString.equals(imagedataWriter.getImageSourceFullName(), imagedataWriter.getImageName())))
                imagedataWriter.setImageSourceFullName(context.writeImageLink(imagedataWriter.getImageSourceFullName()));
        }
        else if (StringUtil.hasChars(imagedataWriter.getImageSourceFullName()))
            imagedataWriter.setImageName(context.writeImageLink(imagedataWriter.getImageSourceFullName()));

        if (fillWriter.getImageBytes() != null)
            fillWriter.setImageName(context.writeImageBinData(fillWriter.getImageBytes()));

        if (strokeWriter.getImageBytes() != null)
            strokeWriter.setImageName(context.writeImageBinData(strokeWriter.getImageBytes()));

        VmlBuilder vmlBuilder = new VmlBuilder(builder);
        builder.startElement(shapeElementName);

        if (shape.getShapeType() == ShapeType.CUSTOM_SHAPE)
        {
            // RK This seems to be what MS Word is writing for custom shapes.
            String shapeName = (String)props.get(ShapeAttr.SHAPE_NAME);
            if (StringUtil.hasChars(shapeName))
            {
                vmlBuilder.writeVmlAttribute("id", shapeName);
                vmlBuilder.writeVmlAttribute("o:spid", NrxXmlUtil.getShapeId(shape));
            }
            else
            {
                vmlBuilder.writeVmlAttribute("id", NrxXmlUtil.getShapeId(shape));
            }

            // If the following attributes are null, then we assign some default values to them.
            if (strokeWriter.getLineJoinStyle() == null)
                strokeWriter.setLineJoinStyle("miter");

            // RK Custom shapes seem to always output coord size in MS Word.
            if (props.get(ShapeAttr.COORD_SIZE_WIDTH) == null)
                props.set(ShapeAttr.COORD_SIZE_WIDTH, shape.getCoordSizeWidth());
            if (props.get(ShapeAttr.COORD_SIZE_HEIGHT) == null)
                props.set(ShapeAttr.COORD_SIZE_HEIGHT, shape.getCoordSizeHeight());
        }
        else if (StringUtil.hasChars((String)props.get(ShapeAttr.SHAPE_NAME)) && !context.getSaveInfo().LinkedShapeNameConflict)
        {
            // RK Shape has name. In this case it is written is VML shape id
            // and the shape id from the model is written as VML shape spid.
            // WORDSNET-11823 If shape is OLE control, despite this shape has name
            // MS Word writes shape id to correctly link shape with OLE control.
            if (shape.isOleControl())
                vmlBuilder.writeVmlAttribute("id", NrxXmlUtil.getShapeId(shape));
            else
            {
                String shapeName = (String)props.get(ShapeAttr.SHAPE_NAME);
                vmlBuilder.writeVmlAttribute("id", shapeName);
                vmlBuilder.writeVmlAttribute("o:spid", NrxXmlUtil.getShapeId(shape));
            }
            vmlBuilder.writeVmlAttribute("type", shapeTypeId);
        }
        else
        {
            // RK Shape has no name. Shape id is written as VML shape id.
            String shapeName = NrxXmlUtil.getShapeId(shape);

            vmlBuilder.writeVmlAttribute("id", shapeName);
            vmlBuilder.writeVmlAttribute("type", shapeTypeId);
        }

        // WORDSNET-19028 We need write 'editas' if it present, the same as word.
        if (StringUtil.hasChars(diagramWriter.getEditAs()))
            vmlBuilder.writeVmlAttribute("editas", diagramWriter.getEditAs());

        // WORDSNET-16036 Write coordsize if only one dimension (width or height) is defined.
        if ((props.get(ShapeAttr.COORD_SIZE_WIDTH) == null) && (props.get(ShapeAttr.COORD_SIZE_HEIGHT) != null))
            props.set(ShapeAttr.COORD_SIZE_WIDTH, shape.getCoordSizeWidth());
        else if ((props.get(ShapeAttr.COORD_SIZE_WIDTH) != null) && (props.get(ShapeAttr.COORD_SIZE_HEIGHT) == null))
            props.set(ShapeAttr.COORD_SIZE_HEIGHT, shape.getCoordSizeHeight());

        String altText = ShapeBase.generateAltText((String)props.get(ShapeAttr.SHAPE_TITLE), (String)props.get(ShapeAttr.SHAPE_DESCRIPTION));


        if (!(shape.isInline() && shape.isImage()))
        {
            // These attributes are not written for inline pictures.
            // Inline pictures are enclosed in hyperlink field instead.
            vmlBuilder.writeVmlAttribute("href", props.get(ShapeAttr.HYPERLINK_ADDRESS));
            vmlBuilder.writeVmlAttribute("target", props.get(ShapeAttr.HYPERLINK_TARGET));
            vmlBuilder.writeVmlAttribute("title", props.get(ShapeAttr.SCREEN_TIP));
        }

        // andrnosk: WORDSNET-6836 Preserve shape description. Previously we preserve shape description
        // only if inline shape is signature line, but according to MS Word behavior we need to preserve it always.
        if (StringUtil.hasChars(altText))
            vmlBuilder.writeVmlAttribute("alt", altText);

        // Style attribute can have the following CSS attributes:
        //   position
        //   text-align: left
        //   top | margin-top
        //   left | margin-left
        //   width
        //   height
        //   rotation
        //   flip
        //   visibility - never seen in WordML
        //   z-index
        //   mso-wrap-distance-bottom
        //   mso-wrap-distance-left
        //   mso-wrap-distance-right
        //   mso-wrap-distance-top
        //   mso-position-horizontal
        //   mso-position-horizontal-relative
        //   mso-position-vertical
        //   mso-position-vertical-relative
        //   mso-wrap-style: none, square (TextboxWrapMode)
        //   v-text-anchor: bottom (TextboxAnchor)

        if (!isBackGround)
        {
            VmlCssStyleBuilder style = new VmlCssStyleBuilder();

            // Mimic MS Word behavior and write only "width"/"height" for picture bullet shape.
            if (shape.isPictureBullet())
            {
                style.add("width", VmlUtil.distanceToVml(shape.getWidth(), shape.isTopLevel()));
                style.add("height", VmlUtil.distanceToVml(shape.getHeight(), shape.isTopLevel()));
            }
            else
            {
                if (!shape.isInline())
                    style.add("position", "absolute");

                // Top level distances seem to be always written in points.
                // Nested shape distances seem to be always written without measurement units, they are relative to coordinates defined in containing shape.
                String marginPrefix = "";

                if (shape.isTopLevel())
                    marginPrefix = "margin-";

                if (shape.getShapeType() != ShapeType.LINE)
                {
                    if (!shape.isInline())
                    {
                        if (shape.isTopLevel() || shape.getLeft() != 0)
                            style.add(marginPrefix + "left", VmlUtil.distanceToVml(shape.getLeft(), shape.isTopLevel()));

                        if (shape.isTopLevel() || shape.getTop() != 0)
                            style.add(marginPrefix + "top", VmlUtil.distanceToVml(shape.getTop(), shape.isTopLevel()));
                    }

                    style.add("width", VmlUtil.distanceToVml(shape.getWidth(), shape.isTopLevel()));
                    style.add("height", VmlUtil.distanceToVml(shape.getHeight(), shape.isTopLevel()));
                }

                Object rotationObj = props.get(ShapeAttr.TRANSFORM_ROTATION);
                if (rotationObj != null)
                {
                    int rotation = (/*int*/Integer)rotationObj;
                    if (rotation != 0)
                        style.add("rotation", VmlUtil.fixedDegreesToVml(rotation));
                }

                if (StringUtil.hasChars((String)props.get(ShapeAttr.FLIP)))
                    style.add("flip", (String)props.get(ShapeAttr.FLIP));

                if (shape.isTopLevel() && !shape.isInline())
                    style.add("z-index", context.getSaveInfo().getShapeZIndex(shape));

                if (props.get(ShapeAttr.HIDDEN) != null)
                {
                    // Occurs in Demo2007\NewsletterFamily.doc and Demo2007\NewsletterTravel.doc.
                    style.add("visibility", (/*boolean*/Boolean)props.get(ShapeAttr.HIDDEN) ? "hidden" : "visible");
                }

                if (props.get(ShapeAttr.EDITED_WRAP) != null)
                {
                    // Only 'false' values are written.
                    style.add("mso-wrap-edited", (/*boolean*/Boolean)props.get(ShapeAttr.EDITED_WRAP) ? "" : "f");
                }

                style.add("mso-wrap-distance-left",
                    VmlUtil.distanceToVml(props.get(ShapeAttr.DISTANCE_LEFT), shape.isTopLevel()));
                style.add("mso-wrap-distance-top",
                    VmlUtil.distanceToVml(props.get(ShapeAttr.DISTANCE_TOP), shape.isTopLevel()));
                style.add("mso-wrap-distance-right",
                    VmlUtil.distanceToVml(props.get(ShapeAttr.DISTANCE_RIGHT), shape.isTopLevel()));
                style.add("mso-wrap-distance-bottom",
                    VmlUtil.distanceToVml(props.get(ShapeAttr.DISTANCE_BOTTOM), shape.isTopLevel()));

                style.add("mso-position-horizontal", (String)props.get(ShapeAttr.HORIZONTAL_ALIGNMENT));
                style.add("mso-position-horizontal-relative", (String)props.get(ShapeAttr.RELATIVE_HORIZONTAL_POSITION));
                style.add("mso-position-vertical", (String)props.get(ShapeAttr.VERTICAL_ALIGNMENT));
                style.add("mso-position-vertical-relative", (String)props.get(ShapeAttr.RELATIVE_VERTICAL_POSITION));

                // These are Word 2007 only attributes.
                if (context.isDocx())
                {
                    style.add("mso-left-percent", (String)props.get(ShapeAttr.LEFT_PERCENT));
                    style.add("mso-top-percent", (String)props.get(ShapeAttr.TOP_PERCENT));
                    style.add("mso-width-percent", (String)props.get(ShapeAttr.WIDTH_PERCENT));
                    style.add("mso-height-percent", (String)props.get(ShapeAttr.HEIGHT_PERCENT));
                    style.add("mso-width-relative", (String)props.get(ShapeAttr.RELATIVE_WIDTH));
                    style.add("mso-height-relative", (String)props.get(ShapeAttr.RELATIVE_HEIGHT));
                }

                style.add("mso-wrap-style", (String)props.get(ShapeAttr.TEXTBOX_WRAP_MODE));
                style.add("v-text-anchor", (String)props.get(ShapeAttr.TEXTBOX_ANCHOR));
            }

            builder.writeAttribute("style", style.toCss());
        }

        if(props.get(ShapeAttr.EQUATION_XML) != null)
        {
            builder.writeAttribute("equationxml", Encoding.getUTF8().getString((byte[])props.get(ShapeAttr.EQUATION_XML)));
        }

        builder.writeAttribute("w:themeColor", props.get(ShapeAttr.THEME_COLOR));
        builder.writeAttribute("w:themeShade", props.get(ShapeAttr.THEME_SHADE));
        builder.writeAttribute("w:themeTint", props.get(ShapeAttr.THEME_TINT));

        builder.writeAttribute("o:bwmode", props.get(ShapeAttr.BW_MODE));
        builder.writeAttribute("o:bwpure", props.get(ShapeAttr.BW_PURE));
        builder.writeAttribute("o:bwnormal", props.get(ShapeAttr.BW_NORMAL));

        if(!isBackGround)
            vmlBuilder.writeVmlAttribute("o:oleicon", props.get(ShapeAttr.OLE_ICON));

        if (shape.isInline() && shape.isOle())
            builder.writeAttributeString("o:ole", "");

        if (shape.isInline())
        {
            builder.writeAttribute("o:bordertopcolor",
                VmlUtil.borderToVmlColor((Border)props.get(ShapeAttr.BORDER_TOP), context));
            builder.writeAttribute("o:borderleftcolor",
                VmlUtil.borderToVmlColor((Border)props.get(ShapeAttr.BORDER_LEFT), context));
            builder.writeAttribute("o:borderbottomcolor",
                VmlUtil.borderToVmlColor((Border)props.get(ShapeAttr.BORDER_BOTTOM), context));
            builder.writeAttribute("o:borderrightcolor",
                VmlUtil.borderToVmlColor((Border)props.get(ShapeAttr.BORDER_RIGHT), context));
        }

        // write attributes for predefined shapes
        if (shape.getShapeType() == ShapeType.LINE)
            writeLineCoordinates(shape, vmlBuilder);

        if (shape.getShapeType() == ShapeType.ROUND_RECTANGLE)
            vmlBuilder.writeVmlAttribute("arcsize", VmlUtil.fixedToVml(adj1ToArcSize(shape)));

        // RK Is it supposed to be written always?
        writePoint(builder, props, ShapeAttr.COORD_ORIGIN_X, ShapeAttr.COORD_ORIGIN_Y, "coordorigin");
        writePoint(builder, props, ShapeAttr.COORD_SIZE_WIDTH, ShapeAttr.COORD_SIZE_HEIGHT, "coordsize");

        vmlBuilder.writeVmlAttributeIfNotDefault("o:allowincell", props.get(ShapeAttr.ALLOW_IN_CELL), true);
        vmlBuilder.writeVmlAttributeIfNotDefault("o:allowoverlap", props.get(ShapeAttr.ALLOW_OVERLAP), true);

        if (shape.getShapeType() == ShapeType.CUSTOM_SHAPE)
            vmlBuilder.writeVmlAttribute("o:spt", "100");

        if (diagramLayout != null)
            builder.writeAttribute("o:dgmlayout", diagramLayout);

        if (diagramNodeKind != null)
            builder.writeAttribute("o:dgmnodekind", diagramNodeKind);

        vmlBuilder.writeVmlAttribute("o:connectortype", props.get(ShapeAttr.CONNECTOR_TYPE));

        geoWriter.writeAdjAttribute();

        geoWriter.writePathAttribute();

        vmlBuilder.writeVmlAttribute("o:preferrelative", props.get(ShapeAttr.PREFER_RELATIVE_RESIZE));
        vmlBuilder.writeVmlAttribute("o:button", props.get(ShapeAttr.BUTTON));

        // VA: MS Word does not write o:hrheight and o:hrwidth values to WordML. So I won't either.
        // RK: I agree. In fact there HRWidth and HRHeight attributes are no longer in the model,
        // the height and width of the horizontal rule is determined by the shape height and width.
        vmlBuilder.writeVmlAttribute("o:hrpct", props.get(ShapeAttr.HR_PCT));
        vmlBuilder.writeVmlAttribute("o:hralign", props.get(ShapeAttr.HR_ALIGN));
        vmlBuilder.writeVmlAttribute("o:hrstd", props.get(ShapeAttr.HR_STANDARD));
        vmlBuilder.writeVmlAttribute("o:hrnoshade", props.get(ShapeAttr.HR_NO_SHADE));
        vmlBuilder.writeVmlAttribute("o:hr", props.get(ShapeAttr.HR_ON));

        vmlBuilder.writeVmlAttribute("o:bullet", props.get(ShapeAttr.PICTURE_BULLET));

        writeWrapPolygon(builder, (PathPoint[])props.get(ShapeAttr.WRAP_POLYGON_VERTICES));

        fillWriter.writeAttributes();

        if (!isBackGround)
            strokeWriter.writeAttributes();

        // Write 'v:fill' element.
        fillWriter.write();

        // Write 'v:stroke' element.
        strokeWriter.write();

        // Write 'v:imagedata' element.
        imagedataWriter.write();

        // Write 'v:shadow' element.
        shadowWriter.write();

        // Write 'o:extrusion' element.
        extrusionWriter.write();

        // andrnosk: WORDSNET-7738 v:path should be written for v:shape and for v:rect too.
        if (("v:shape".equals(shapeElementName)) || ("v:rect".equals(shapeElementName)))
        {
            geoWriter.writeFormulas();
            geoWriter.writePath();
            geoTextWriter.write();
            geoWriter.writeHandles();
        }

        // Write 'o:lock' element.
        if (!isBackGround)
            lockWriter.write();

        // Write 'o:ink' element.
        if (shape.getInkData() != null)
        {
            builder.startElement("o:ink");
            builder.writeAttribute("i", shape.getInkData());
            vmlBuilder.writeVmlAttribute("annotation", shape.getInkAnnotation());
            builder.endElement();
        }

        // Write 'o:signatureline' element.
        signWriter.write();

        // Write 'o:diagram' element.
        diagramWriter.writeDiagramElement();

        // Write 'o:callout' element.
        calloutWriter.write();

        writeW10Border("w10:bordertop", props.get(ShapeAttr.BORDER_TOP), builder);
        writeW10Border("w10:borderleft", props.get(ShapeAttr.BORDER_LEFT), builder);
        writeW10Border("w10:borderbottom", props.get(ShapeAttr.BORDER_BOTTOM), builder);
        writeW10Border("w10:borderright", props.get(ShapeAttr.BORDER_RIGHT), builder);

        boolean isShapeLinked = context.getSaveInfo().LinkedShapeIds.contains(shape.getId());

        if (!shape.isGroup() && (shape.hasChildNodes() || isShapeLinked))
        {
            builder.startElement("v:textbox");

            VmlCssStyleBuilder textboxStyle = new VmlCssStyleBuilder();

            if (props.containsKey(ShapeAttr.TEXTBOX_LAYOUT_FLOW))
            {
                /*LayoutFlow*/int layoutFlow = (/*LayoutFlow*/Integer)props.get(ShapeAttr.TEXTBOX_LAYOUT_FLOW);
                if (layoutFlow == LayoutFlow.BOTTOM_TO_TOP)
                {
                    textboxStyle.add("layout-flow", "vertical");
                    textboxStyle.add("mso-layout-flow-alt", "bottom-to-top");
                }
                else
                {
                    textboxStyle.add("layout-flow", VmlEnum.layoutFlowToVml(layoutFlow));
                }
            }

            int nextShapeId = shape.getTextboxNextShapeId();
            if (nextShapeId != 0)
            {
                ShapeBase nextShape = context.getSaveInfo().Shapes.get(nextShapeId);

                String link = StringUtil.hasChars(nextShape.getName()) && !context.getSaveInfo().LinkedShapeNameConflict
                                  ? msString.format("#{0}", nextShape.getName())
                                  : msString.format("#{0}", NrxXmlUtil.getShapeId(nextShape));

                textboxStyle.add("mso-next-textbox", link);
            }

            textboxStyle.add("mso-fit-shape-to-text", (String)props.get(ShapeAttr.TEXTBOX_FIT_SHAPE_TO_TEXT));
            vmlBuilder.writeVmlAttribute("style", textboxStyle.toCss());
            vmlBuilder.writeVmlAttribute("inset", VmlUtil.getInset(props));

            builder.startElement("w:txbxContent");

            if (!shape.hasChildNodes() && isShapeLinked)
            {
                builder.endElement("w:txbxContent");
                builder.endElement("v:textbox");
            }
        }
    }

    /// <summary>
    /// Write 'wrapcoords' attribute of 'w:shape' element.
    /// </summary>
    private static void writeWrapPolygon(NrxXmlBuilder builder, PathPoint[] wrapCoords)
    {
        if ((wrapCoords != null) && (wrapCoords.length > 0))
            builder.writeAttribute("wrapcoords", VmlUtil.pathPointsToVml(wrapCoords, ' ', ' '));
    }

    private static void writePoint(NrxXmlBuilder builder, IntToObjDictionary<Object> props, int keyX, int keyY, String attrName)
    {
        Object x = props.get(keyX);
        Object y = props.get(keyY);
        if ((x != null) && (y != null))
        {
            VmlBuilder vmlBuilder = new VmlBuilder(builder);
            vmlBuilder.writeVmlAttribute(attrName, VmlUtil.pointToVml(msPoint.ctor((/*int*/Integer)x, (/*int*/Integer)y)));
        }
    }

    static void writeWrapAndAnchorLock(ShapeBase shape, NrxXmlBuilder builder)
    {
        ShapePr shapePr = shape.getShapePr();

        String wrapType = shapePr.contains(ShapeAttr.WRAP_TYPE) ?
            VmlEnum.wrapTypeToVml(shape.getWrapType()) : "";

        if (shape.getShapePr().contains(ShapeAttr.WRAP_TYPE))
        {
            VmlBuilder vmlBuilder = new VmlBuilder(builder);
            String relativeHorizontalPosition = StyleConvertUtil.relativeHorizontalPositionToXml(shape.getRelativeHorizontalPosition());

            if ("text".equals(relativeHorizontalPosition))
                relativeHorizontalPosition = "";

            String relativeVerticalPosition = StyleConvertUtil.relativeVerticalPositionToXml(shape.getRelativeVerticalPosition());

            if ("text".equals(relativeVerticalPosition))
                relativeVerticalPosition = "";

            String wrapSide = shapePr.contains(ShapeAttr.WRAP_SIDE) ?
                VmlEnum.wrapSideToVml(shape.getWrapSide()) : "";

            if (shape.isInline())
            {
                if (("char".equals(relativeHorizontalPosition)) && ("line".equals(relativeVerticalPosition)) && StringUtil.hasChars(wrapType))
                {
                    builder.startElement("w10:wrap");
                    vmlBuilder.writeVmlAttribute("type", wrapType);
                    builder.endElement(); //w10:wrap
                }
            }
            else if (StringUtil.hasChars(wrapType))
            {
                builder.startElement("w10:wrap");
                vmlBuilder.writeVmlAttribute("type", wrapType);
                vmlBuilder.writeVmlAttribute("side", wrapSide);
                builder.endElement(); //w10:wrap
            }
            else if (StringUtil.hasChars(relativeHorizontalPosition) || StringUtil.hasChars(relativeVerticalPosition))
            {
                boolean writeWrapSide = StringUtil.hasChars(wrapSide);
                boolean writeAnchorX = isSupportedPropertyAndNotDefault(relativeHorizontalPosition);
                boolean writeAnchorY = isSupportedPropertyAndNotDefault(relativeVerticalPosition);

                // Skip writing an empty element.
                if (writeWrapSide || writeAnchorX || writeAnchorY)
                {
                    builder.startElement("w10:wrap");
                    vmlBuilder.writeVmlAttribute("side", wrapSide);

                    // Do not write not supported and default values.
                    if (writeAnchorX)
                        vmlBuilder.writeVmlAttribute("anchorx", relativeHorizontalPosition);
                    if (writeAnchorY)
                        vmlBuilder.writeVmlAttribute("anchory", relativeVerticalPosition);
                    builder.endElement(); //w10:wrap
                }
            }
        }

        if (shape.getAnchorLocked())
            builder.writeEmptyElement("w10:anchorlock");
    }

    /// <summary>
    /// According to spec, default value for anchorx and anchory is 'page'.
    /// So we can omit writing these attributes if they have default value.
    /// </summary>
    private static boolean isSupportedPropertyAndNotDefault(String value)
    {
        return (isSupportedProperty(value) && (!"page".equals(value)));
    }

    /// <summary>
    /// Word supports only these props (margin, page, text) for anchors.
    /// See "[MS-OI29500] 2.1.1827 Part 4 Section 14.3.3.3, ST_HorizontalAnchor (Horizontal Anchor Type), 2.1.1828 Part 4 Section 14.3.3.4, ST_VerticalAnchor (Vertical Anchor Type)",
    /// "part 4 transitional c059578_ISO_IEC_29500-4_2011 19.3.3.3 ST_HorizontalAnchor (Horizontal Anchor Type), 19.3.3.4 ST_VerticalAnchor (Vertical Anchor Type)".
    /// </summary>
    private static boolean isSupportedProperty(String value)
    {
        return (("margin".equals(value)) || ("page".equals(value)) || ("text".equals(value)));
    }

    private static void writeW10Border(String borderName, Object borderAttr, NrxXmlBuilder builder)
    {
        if (borderAttr instanceof Border)
        {
            Border border = (Border)borderAttr;

            if (border.getLineStyle() != LineStyle.NONE)
            {
                builder.startElement(borderName);
                builder.writeAttribute("type", VmlEnum.lineStyleToVml(border.getLineStyle()));
                builder.writeAttribute("width", FormatterPal.doubleToStr2Decimals(border.getLineWidth() * 8.0));
                builder.writeAttribute("shadow", VmlUtil.boolToVml(border.getShadow(), false));
                builder.endElement();
            }
        }
    }

    private static int adj1ToArcSize(ShapeBase shape)
    {
        Object adj1Obj = shape.getDirectShapeAttrInternal(ShapeAttr.GEOMETRY_ADJUST_1);
        if (adj1Obj != null)
        {
            // In the model arcsize is stored in adj1. In VML it is stored as percentage.
            int adj1 = (/*int*/Integer)adj1Obj;
            double percent = (double)adj1 / shape.getCoordSizeWidth();
            return ConvertUtilCore.doubleToFixed(percent);
        }
        else
        {
            final int DEFAULT_ARC_SIZE = 10923;
            return DEFAULT_ARC_SIZE;
        }
    }

    /// <summary>
    /// Writes line coordinates of start and end points.
    /// </summary>
    private static void writeLineCoordinates(ShapeBase shape, VmlBuilder builder)
    {
        Debug.assertTrue((shape != null) && (builder != null));

        double[] vertices =
            VmlUtil.getLineVertices(shape, shape.getTop(), shape.getLeft(), shape.getWidth(), shape.getHeight());

        double fromX = (vertices.length > 0) ? vertices[0] : shape.getLeft();
        double fromY = (vertices.length > 0) ? vertices[1] : shape.getTop();
        double toX = (vertices.length > 0) ? vertices[2] : shape.getLeft() + shape.getWidth();
        double toY = (vertices.length > 0) ? vertices[3] : shape.getTop() + shape.getHeight();

        builder.writeVmlAttribute("from", VmlUtil.pointToVml(fromX, fromY, shape.isTopLevel()));
        builder.writeVmlAttribute("to", VmlUtil.pointToVml(toX, toY, shape.isTopLevel()));
    }
}

