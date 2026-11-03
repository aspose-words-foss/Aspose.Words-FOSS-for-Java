// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


class DmlNodePropertiesReader extends DmlReaderBase
{
    private DmlNodePropertiesReader()
    {
    }

    /// <summary>
    /// Reads the visual group shape properties.
    /// </summary>
    /// <remarks>
    /// 20.1.2.2.22 grpSpPr (Visual Group Shape Properties)
    /// This element specifies the properties that are to be common across all of the shapes
    /// within the corresponding group. If there are any conflicting properties within the
    /// group shape properties and the individual shape properties then the individual shape
    /// properties should take precedence.
    /// </remarks>
    static void readVisualGroupShapeProperties(DocxDocumentReaderBase reader, DmlCompositeNode node) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        if ((node.getDmlNodeType() == DmlNodeType.WORDPROCESSING_GROUP_SHAPE) ||
            (node.getDmlNodeType() == DmlNodeType.GROUP_SHAPE))
        {
            ((DmlGroupShape)node).setBWMode(DmlEnum.dmlToBWMode(xmlReader.readAttribute("bwMode", "auto")));
        }

        while (xmlReader.readChild("grpSpPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"xfrm"*/0:
                    node.setTransform(DmlTransformReader.readGroupTransform(xmlReader, complianceInfo));
                    break;
                case /*"blipFill"*/1:
                case /*"gradFill"*/2:
                case /*"grpFill"*/3:
                case /*"noFill"*/4:
                case /*"pattFill"*/5:
                case /*"solidFill"*/6:
                    node.setFill(DmlFillReader.read(reader));
                    break;
                case /*"effectLst"*/7:
                    node.setEffects(DmlShapeEffectReader.readEffects(reader, false, false));
                    break;
                case /*"effectDag"*/8:
                    node.setEffects(DmlShapeEffectReader.readEffects(reader, false, true));
                    break;
                case /*"scene3d"*/9:
                    node.setScene3DProperties(DmlScene3DReader.readScene3DProperties(reader, complianceInfo));
                    break;
                case /*"extLst"*/10:
                    node.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the shape properties.
    /// </summary>
    /// <remarks>
    /// 20.1.2.2.35 spPr (Shape Properties)
    /// This element specifies the visual shape properties that can be applied to a shape.
    /// </remarks>
    static void readShapeProperties(DocxDocumentReaderBase reader, IDmlShapePrSource shape) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"bwMode"*/11:
                    shape.setBWMode(DmlEnum.dmlToBWMode(xmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        while (xmlReader.readChild("spPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"custGeom"*/12:
                    shape.setGeometry(DmlGeometryReader.read(xmlReader, complianceInfo));
                    break;
                case /*"xfrm"*/0:
                    shape.setTransform(DmlTransformReader.readTransform(xmlReader, complianceInfo));
                    break;
                case /*"prstGeom"*/13:
                    shape.setGeometry(DmlGeometryReader.read(xmlReader, complianceInfo));
                    break;
                case /*"blipFill"*/1:
                case /*"gradFill"*/2:
                case /*"grpFill"*/3:
                case /*"noFill"*/4:
                case /*"pattFill"*/5:
                case /*"solidFill"*/6:
                    ((IDmlCommonShapePrSource)shape).setFill(DmlFillReader.read(reader));
                    break;
                case /*"ln"*/14:
                    ((IDmlCommonShapePrSource)shape).setOutline(DmlOutlineReader.read(reader));
                    break;
                case /*"effectLst"*/7:
                    shape.setEffects(DmlShapeEffectReader.readEffects(reader, false, false));
                    break;
                case /*"effectDag"*/8:
                    shape.setEffects(DmlShapeEffectReader.readEffects(reader, false, true));
                    break;
                case /*"scene3d"*/9:
                    shape.setScene3DProperties(DmlScene3DReader.readScene3DProperties(reader, complianceInfo));
                    break;
                case /*"sp3d"*/15:
                    shape.setShape3DProperties(DmlScene3DReader.readShape3DProperties(reader, complianceInfo));
                    break;
                case /*"extLst"*/10:
                    shape.setSpPrExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// 20.1.2.2.25 nvCxnSpPr (Non-Visual Properties for a Connection Shape)
    /// 20.1.2.2.26 nvGraphicFramePr (Non-Visual Properties for a Graphic Frame)
    /// 20.1.2.2.27 nvGrpSpPr (Non-Visual Properties for a Group Shape)
    /// 20.1.2.2.28 nvPicPr (Non-Visual Properties for a Picture)
    /// 20.1.2.2.29 nvSpPr (Non-Visual Properties for a Shape)
    /// </summary>
    static void readNonVisualProperties(DmlNvPrBase nvPr, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"cNvCxnSpPr"*/16:
                case /*"cNvGraphicFramePr"*/17:
                case /*"cNvGrpSpPr"*/18:
                case /*"cNvPicPr"*/19:
                case /*"cNvSpPr"*/20:
                case /*"cNvContentPartPr"*/21:
                    nvPr.setCNvProperties(readNonVisualConnectorProperties(reader));
                    break;
                case /*"cNvPr"*/22:
                    nvPr.setNvDrawingProperties(readNonVisualDrawingProperties(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    /// <summary>
    /// 20.1.2.2.4 cNvCxnSpPr (Non-Visual Connector Shape Drawing Properties)
    /// 20.1.2.2.5 cNvGraphicFramePr (Non-Visual Graphic Frame Drawing Properties)
    /// 20.1.2.2.6 cNvGrpSpPr (Non-Visual Group Shape Drawing Properties)
    /// 20.1.2.2.7 cNvPicPr (Non-Visual Picture Drawing Properties)
    /// 20.1.2.2.9 cNvSpPr (Non-Visual Shape Drawing Properties)
    /// </summary>
    static DmlCnvPrBase readNonVisualConnectorProperties(DocxDocumentReaderBase docReader) throws Exception
    {
        NrxXmlReader reader = docReader.getXmlReader();
        DmlCnvPrBase pr = createConnectorProperties(reader.getLocalName());

        // Read attributes.
        if (pr.getHolder() == DmlNvHolder.SHAPE)
            ((DmlCnvPrShape)pr).setTextBox(reader.readBoolAttribute("txBox", false));

        if(pr.getHolder() == DmlNvHolder.PICTURE)
            ((DmlCnvPrPicture)pr).setPreferRelativeResize(reader.readBoolAttribute("preferRelativeResize", true));

        if (pr.getHolder() == DmlNvHolder.CONTENT_PART)
            ((DmlCnvPrContentPart)pr).isComment(reader.readBoolAttribute("isComment", true));

        reader.moveToElement();

        String tagName = reader.getLocalName();
        while (reader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"extLst"*/10:
                    pr.setExtensions(DmlExtensionListReader.read(docReader));
                    break;
                case /*"cxnSpLocks"*/23:
                case /*"graphicFrameLocks"*/24:
                case /*"grpSpLocks"*/25:
                case /*"picLocks"*/26:
                case /*"spLocks"*/27:
                case /*"cpLocks"*/28:
                    readShapeLocks(pr.getLocks(), docReader);
                    break;
                case /*"stCxn"*/29:
                    ((DmlCnvPrConnectorShape)pr).setConnectionStart(readConnection(reader));
                    break;
                case /*"endCxn"*/30:
                    ((DmlCnvPrConnectorShape)pr).setConnectionEnd(readConnection(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(reader);
                    break;
            }
        }

        return pr;
    }

    private static DmlConnection readConnection(NrxXmlReader reader)
    {
        DmlConnection connection  = new DmlConnection();

        // WORDSNET-23908 Identifiers of shape connectors have "unsignedInt" type.
        connection.setId(reader.readUIntAttribute("id", 0));
        connection.setIndex(reader.readUIntAttribute("idx", 0));

        reader.moveToElement();
        reader.ignoreElementNoWarn();
        return connection;
    }

    private static DmlCnvPrBase createConnectorProperties(String tagName)
    {
        switch (gStringSwitchMap.of(tagName))
        {
            case /*"cNvCxnSpPr"*/16:
            case /*"cNvCnPr"*/31: // This connector shape properties in ISO 29500.
                return new DmlCnvPrConnectorShape();
            case /*"cNvFrPr"*/32:
            case /*"cNvGraphicFramePr"*/17:
                return new DmlCnvPrGraphicFrame();
            case /*"cNvGrpSpPr"*/18:
                return new DmlCnvPrGroupShape();
            case /*"cNvPicPr"*/19:
                return new DmlCnvPrPicture();
            case /*"cNvSpPr"*/20:
                return new DmlCnvPrShape();
            case /*"cNvContentPartPr"*/21:
                return new DmlCnvPrContentPart();
            default:
                throw new IllegalArgumentException("Unexpected connector properties type.");
        }
    }

    /// <summary>
    /// Reads 'cNvPr' Non-Visual Drawing Properties.
    /// </summary>
    static DmlNvDrawingProperties readNonVisualDrawingProperties(DocxDocumentReaderBase reader) throws Exception
    {
        DmlNvDrawingProperties nvPr = new DmlNvDrawingProperties();

        NrxXmlReader xmlReader = reader.getXmlReader();
        // Read attributes.
        nvPr.setDescription(xmlReader.readAttribute("descr", ""));
        nvPr.setName(xmlReader.readAttribute("name", ""));
        nvPr.setTitle(xmlReader.readAttribute("title", ""));
        nvPr.setHidden(xmlReader.readBoolAttribute("hidden", false));
        nvPr.setId(xmlReader.readIntAttribute("id", 0));

        while (xmlReader.readChild("cNvPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"hlinkClick"*/33:
                    nvPr.setHlinkClick(DmlHlinkReader.read(reader));
                    break;
                case /*"extLst"*/10:
                    nvPr.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                case /*"hlinkHover"*/34:
                    warnNotSupportedAndIgnoreElement(xmlReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return nvPr;
    }

    /// <summary>
    /// Reads Shape Locks.
    /// </summary>
    private static void readShapeLocks(DmlLocks locks, DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        readLocks(locks, xmlReader);

        xmlReader.moveToElement();

        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"extLst"*/10:
                    locks.setExtensions(DmlExtensionListReader.read(reader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(xmlReader);
                    break;
            }
        }
    }

    private static void readLocks(DmlLocks locks, NrxXmlReader reader)
    {
        while (reader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(reader.getLocalName()))
            {
                case /*"noAdjustHandles"*/35:
                    locks.addLock(DmlLock.NO_ADJUST_HANDLES, reader.getValueAsBool());
                    break;
                case /*"noChangeArrowheads"*/36:
                    locks.addLock(DmlLock.NO_CHANGE_ARROWHEADS, reader.getValueAsBool());
                    break;
                case /*"noChangeAspect"*/37:
                    locks.addLock(DmlLock.NO_CHANGE_ASPECT, reader.getValueAsBool());
                    break;
                case /*"noChangeShapeType"*/38:
                    locks.addLock(DmlLock.NO_CHANGE_SHAPE_TYPE, reader.getValueAsBool());
                    break;
                case /*"noEditPoints"*/39:
                    locks.addLock(DmlLock.NO_EDIT_POINTS, reader.getValueAsBool());
                    break;
                case /*"noGrp"*/40:
                    locks.addLock(DmlLock.NO_GROUP, reader.getValueAsBool());
                    break;
                case /*"noUngrp"*/41:
                    locks.addLock(DmlLock.NO_UNGROUP, reader.getValueAsBool());
                    break;
                case /*"noMove"*/42:
                    locks.addLock(DmlLock.NO_MOVE, reader.getValueAsBool());
                    break;
                case /*"noResize"*/43:
                    locks.addLock(DmlLock.NO_RESIZE, reader.getValueAsBool());
                    break;
                case /*"noRot"*/44:
                    locks.addLock(DmlLock.NO_ROTATION, reader.getValueAsBool());
                    break;
                case /*"noSelect"*/45:
                    locks.addLock(DmlLock.NO_SELECT, reader.getValueAsBool());
                    break;
                case /*"noCrop"*/46:
                    locks.addLock(DmlLock.NO_CROP, reader.getValueAsBool());
                    break;
                case /*"noTextEdit"*/47:
                    locks.addLock(DmlLock.NO_TEXT_EDIT, reader.getValueAsBool());
                    break;
                case /*"noDrilldown"*/48:
                    locks.addLock(DmlLock.NO_DRILLDOWN, reader.getValueAsBool());
                    break;
                default:
                    warnUnexpected(reader);
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"xfrm",
		"blipFill",
		"gradFill",
		"grpFill",
		"noFill",
		"pattFill",
		"solidFill",
		"effectLst",
		"effectDag",
		"scene3d",
		"extLst",
		"bwMode",
		"custGeom",
		"prstGeom",
		"ln",
		"sp3d",
		"cNvCxnSpPr",
		"cNvGraphicFramePr",
		"cNvGrpSpPr",
		"cNvPicPr",
		"cNvSpPr",
		"cNvContentPartPr",
		"cNvPr",
		"cxnSpLocks",
		"graphicFrameLocks",
		"grpSpLocks",
		"picLocks",
		"spLocks",
		"cpLocks",
		"stCxn",
		"endCxn",
		"cNvCnPr",
		"cNvFrPr",
		"hlinkClick",
		"hlinkHover",
		"noAdjustHandles",
		"noChangeArrowheads",
		"noChangeAspect",
		"noChangeShapeType",
		"noEditPoints",
		"noGrp",
		"noUngrp",
		"noMove",
		"noResize",
		"noRot",
		"noSelect",
		"noCrop",
		"noTextEdit",
		"noDrilldown"
	);

}

