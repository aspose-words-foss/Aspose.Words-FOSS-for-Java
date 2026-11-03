// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.FileSystem;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Convert;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Implements reading OLE specific elements in WordML and DOCX files.
/// </summary>
/*static*/ class VmlOleReader
{
	/* Simulation of static class by using private constructor */
	private VmlOleReader()
	{}

    /// <summary>
    /// Reads DOCX 'control' element which specifies activeX object.
    /// </summary>
    static Shape readDocxControl(IVmlShapeReaderContext context) throws Exception
    {
        String name = "";
        FileSystem fs = null;
        String shapeId = "";
        String id = "";

        NrxXmlReader reader = context.getXmlReader();

        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"id"*/0:
                    id = value;
                    break;
                case /*"shapeid"*/1:
                    shapeId = value;
                    break;
                case /*"name"*/2:
                    name = value;
                    break;
                default:
                    throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + reader.getLocalName());
            }
        }

        Shape shape = (Shape)context.getShapeById(shapeId);

        // WORDSNET-11823 If there is no shape with the specified in OLE control 'id', MS Word creates new one from the scratch.
        if (shape == null)
        {
            shape = new Shape(context.getDocument());
            shape.setWrapType(WrapType.INLINE);
        }

        shape.setShapeType(ShapeType.OLE_CONTROL);

        DocxDocumentReaderBase docxReader = (DocxDocumentReaderBase)context;

        // Read XML wrapper for activeX.
        OpcPackagePart xmlPart = docxReader.fetchPartByName(context.getRelationshipTarget(id));

        NrxXmlReader xmlPartReader = new NrxXmlReader(xmlPart.getStream());
        String persistence = xmlPartReader.readAttribute("persistence", null);
        String clsid = xmlPartReader.readAttribute("classid", null);

        // ActiveX control can be saved in several ways specified by persistence option:
        //
        // 1) persistPropertyBag - control is serialized as XML data. In such case we cannot save this control into different format such as DOC or WML because there is no
        //    way to convert XML serialization to binary serialization without control interaction i.e only control itself knows data being serialized.
        // 2) persistStorage - control is serialized as structured storage. This is the way control is stored in DOC and WML files. Control with such persistence option can be
        //    easily saved into DOC or WML.
        // 3) persistStream - "lightweight" version of persistStorage. Only serialized data is saved without structured storage. Can be easily converted to persistStorage.
        // 4) persistStreamInit - never seen.
        switch (gStringSwitchMap.of(persistence))
        {
            case /*"persistPropertyBag"*/3:
            {
                // Save whole XML serialization data as OoxmlObject.
                shape.getOleFormat().setEmbeddedObject(new OoxmlObject(xmlPart.getStream(), DocxContentType.CONTROL, name, new Guid(clsid)));
                break;
            }
            case /*"persistStorage"*/4:
            case /*"persistStream"*/5:
            {
                // Read control itself. target is always rId1.
                OpcPackagePart binPart = docxReader.fetchPartByName(xmlPart.getRelationshipTarget("rId1"));

                if ("persistStorage".equals(persistence))
                {
                    // Data is structured storage and ready for Model.
                    fs = new FileSystem(binPart.getAsMemoryStream());
                }
                else
                {
                    // convert persistStream into persistStorage: make structured storage, set read ClsId and add serialized binary data as stream with certain name.
                    fs = new FileSystem(new Guid(clsid));
                    fs.getRoot().add(Ole2StreamBase.OCX_DATA_STREAM_NAME, binPart.getAsMemoryStream());
                }

                // OleObject doesn't have name in Model and it is specified in OCXNAME stream.
                // In DOCX this stream might be skipped so we should add it to get uniform behavior with DOC format.
                OcxNameStream ocxNameStream = new OcxNameStream(name);
                ocxNameStream.write(fs.getRoot());

                ObjInfoStream objInfoStream = ObjInfoStream.defaultControl();
                objInfoStream.write(fs.getRoot());

                shape.getOleFormat().setEmbeddedObject(new OleObject(shape.getId(), fs.getRoot()));

                break;
            }
            case /*"persistStreamInit"*/6:
                reader.ignoreElement(WarningType.DATA_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                break;
            default:
                throw new IllegalStateException("Invalid persistence value.");
        }

        return shape;
    }

    /// <summary>
    /// This is WordML only at the moment.
    ///
    /// Reads OCX control. Reader should be positioned to 'w:ocx' element start.
    /// </summary>
    static Shape readWmlControl(IVmlShapeReaderContext context) throws Exception
    {
        String data = "";
        String name = "";
        String shapeId = "";
        String w = "";
        String h = "";
        String classid = "";

        NrxXmlReader reader = context.getXmlReader();
        while (reader.moveToNextAttribute())
        {
            String value = reader.getValue();

            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"data"*/7:
                    data = value;
                    break;
                case /*"id"*/0:
                    // That is a duplication of "name".
                    break;
                case /*"name"*/2:
                    name = value;
                    break;
                case /*"classid"*/8:
                    classid = value;
                    break;
                case /*"shapeid"*/1:
                    shapeId = value;
                    break;
                case /*"class"*/9:
                    // Always "shape" as far as I have seen. Ignore.
                    break;
                case /*"w"*/10:
                    w = value;
                    break;
                case /*"h"*/11:
                    h = value;
                    break;
                case /*"align"*/12:
                    // Not in the model. Ignore.
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
                case /*"iPersistPropertyBag"*/13: // wx:iPersistPropertyBag
                    // Not in the model. Ignore.
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }

        Shape shape = (Shape)context.getShapeById(shapeId);
        if (shape == null)
        {
            shape = new Shape(context.getDocument());
            shape.setWrapType(WrapType.INLINE);
        }

        // The shape for this OCX might been read already as Image type.
        // But as we read OCX control for that shape now, we change its type to OleControl.
        shape.setShapeType(ShapeType.OLE_CONTROL);

        if (StringUtil.hasChars(w))
            shape.setWidthSafe(ConvertUtil.pixelToPoint(FormatterPal.parseDouble(w)));

        if (StringUtil.hasChars(h))
            shape.setHeightSafe(ConvertUtil.pixelToPoint(FormatterPal.parseDouble(h)));

        // RK In most cases "data" is an id, but in TestTobyHenderson6 ms.wml it is actually an OCXDATA stream.
        final String INLINE_DATA_HEADER = "DATA:application/x-oleobject;BASE64,";
        FileSystem fs = null;
        if (msString.startsWith(data, INLINE_DATA_HEADER, StringComparison.ORDINAL))
        {
            data = data.substring(INLINE_DATA_HEADER.length());
            byte[] ocxData = Convert.fromBase64String(data);

            final String CLSID_PREFIX = "CLSID:";
            if (msString.startsWith(classid, CLSID_PREFIX, StringComparison.ORDINAL))
                classid = classid.substring(CLSID_PREFIX.length());

            Guid guid = StringUtil.hasChars(classid) ? new Guid(classid) : Guid.Empty;
            fs = new FileSystem(new MemoryStorage(guid));
            fs.getRoot().add(Ole2StreamBase.OCX_DATA_STREAM_NAME, new MemoryStream(ocxData));
        }
        else
        {
            // This is the most common case.
            byte[] binData = context.getBinData(data);
            if (binData != null)
                fs = new FileSystem(new MemoryStream(binData));
        }

        if (fs != null)
        {
            // Add some "standard" streams to the structured storage.
            OcxNameStream ocxNameStream = new OcxNameStream(name);
            ocxNameStream.write(fs.getRoot());

            ObjInfoStream objInfoStream = ObjInfoStream.defaultControl();
            objInfoStream.write(fs.getRoot());

            shape.getOleFormat().setEmbeddedObject(new OleObject(shape.getId(), fs.getRoot()));
        }

        return shape;
    }

    /// <summary>
    /// Reads shape's OLE data for the specified shape. Reader should be positioned to 'o:OLEObject' element start.
    /// </summary>
    static void readOleObject(IVmlShapeReaderContext context) throws Exception
    {
        //    <o:OLEObject Type="Embed" ProgID="Equation.3" ShapeID="_x0000_i1025" DrawAspect="Content" ObjectID="_1254857050" r:id="rId5" />
        boolean isLink = false;
        String progId = null;
        String shapeId = null;
        String objectId = null;
        String moniker = "";  // WordML thing.
        boolean isAutoUpdate = false;
        boolean isOleIcon = false;
        String relId = null;    // DOCX thing.

        NrxXmlReader reader = context.getXmlReader();
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"Type"*/14:
                    // Comparing without case just adds resiliency like for the DrawAspect fix.
                    isLink = (StringUtil.equalsIgnoreCase(reader.getValue(), "Link"));
                    break;
                case /*"ProgID"*/15:
                    progId = reader.getValue();
                    break;
                case /*"ShapeID"*/16:
                    shapeId = reader.getValue();
                    break;
                case /*"DrawAspect"*/17:
                    // WORDSNET-7946 DrawAspect is changed from Icon to Content. In fact the value is "icon" in the file,
                    // this is incorrect according to the OOXML schema, but let's read it as MS Word does.
                    isOleIcon = (StringUtil.equalsIgnoreCase(reader.getValue(), "Icon"));
                    break;
                case /*"ObjectID"*/18:
                    objectId = reader.getValue();
                    break;
                case /*"Moniker"*/19:
                    // WordML thing.
                    moniker = reader.getValue();
                    break;
                case /*"UpdateMode"*/20:
                    // Comparing without case just adds resiliency like for the DrawAspect fix.
                    isAutoUpdate = (StringUtil.equalsIgnoreCase(reader.getValue(), "Always"));
                    break;
                case /*"id"*/0:
                    // DOCX thing.
                    relId = reader.getValue();
                    break;
                default:
                    reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }

        Shape shape = (Shape)context.getShapeById(shapeId);
        if (shape == null)
            return;

        shape.setShapeType(ShapeType.OLE_OBJECT);

        OleFormat oleFormat = shape.getOleFormat();
        oleFormat.setAutoUpdate(isAutoUpdate);
        oleFormat.setOleIcon(isOleIcon);
        if (progId != null)
            oleFormat.setProgId(progId);

        if (isLink)
        {
            // DOCX specific.
            if (StringUtil.hasChars(relId))
                moniker = context.getRelationshipTarget(relId);

            if (StringUtil.hasChars(moniker))
            {
                String[] monikerParts = msString.split(moniker, new char[] { '!' }, 2);
                oleFormat.setSourceFullName(monikerParts[0]);

                if (monikerParts.length > 1)
                    oleFormat.setSourceItem(monikerParts[1]);
            }
        }
        else
        {
            // If relationship id is specified, this must be a DOCX file and we should use the relationship id.
            // Otherwise it is a WordML file and we need to use the object id to get the data.
            NrxDocumentReaderBase ooxmlContext = ms.as(context, NrxDocumentReaderBase.class);
            String effectiveId = StringUtil.hasChars(relId) ? relId : objectId;

            // WORDSNET-24477 Ignore embedded OLE objects according to settings.
            if ((ooxmlContext == null) || !ooxmlContext.getLoadOptions().getIgnoreOleData())
            {
                IEmbeddedObject embeddedObject = context.getEmbeddedObject(effectiveId);

                if (embeddedObject != null)
                {
                    int objectIdInt = NrxXmlUtil.tryParseId(objectId);
                    if (objectIdInt != Integer.MIN_VALUE)
                        embeddedObject.setId(objectIdInt);

                    oleFormat.setEmbeddedObject(embeddedObject);
                }
            }
        }

        while (reader.readChild("OLEObject")) // o:OLEObject
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"LinkType"*/21:
                    oleFormat.setOleLinkType(VmlEnum.vmlToOleLinkType(reader.readString()));
                    break;
                case /*"LockedField"*/22:
                    // RK When o:LockedField is present, but empty it means true.
                    oleFormat.isLocked(reader.readBoolString(true));
                    break;
                case /*"WordFieldCodes"*/23:
                case /*"FieldCodes"*/24:
                {
                    String value = reader.readString();
                    if (value.length() > 0)
                    {
                        char linkTypeDigitChar = value.charAt(value.length() - 1);
                        if (linkTypeDigitChar >= '0' && linkTypeDigitChar <= '5')
                            oleFormat.setFormatUpdateType(linkTypeDigitChar - '0');
                    }
                    else
                    {
                        oleFormat.setFormatUpdateType(0);
                    }
                    break;
                }
                default:
                    reader.ignoreElement(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msString.format(WARNING_MESSAGE_FORMAT, reader.getLocalName()));
                    break;
            }
        }
    }

    private static final String WARNING_MESSAGE_FORMAT = "Import of element '{0}' is not supported upon reading OLE specific elements by Aspose.Words.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"shapeid",
		"name",
		"persistPropertyBag",
		"persistStorage",
		"persistStream",
		"persistStreamInit",
		"data",
		"classid",
		"class",
		"w",
		"h",
		"align",
		"iPersistPropertyBag",
		"Type",
		"ProgID",
		"ShapeID",
		"DrawAspect",
		"ObjectID",
		"Moniker",
		"UpdateMode",
		"LinkType",
		"LockedField",
		"WordFieldCodes",
		"FieldCodes"
	);

}

