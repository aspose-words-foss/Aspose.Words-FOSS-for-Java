// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Builds locked canvases or groups shapes from XML.
/// </summary>
class DmlCompositeNodeReader extends DmlReaderBase
{
    private DmlCompositeNodeReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Reads locked canvases and word processing canvases from XML.
    /// </summary>
    /// <remarks> 20.3.2.1 lockedCanvas (Locked Canvas Container)
    /// The locked canvas element acts as a container for more advanced drawing objects.
    /// The notion of a locked canvas comes from the fact that the generating application
    /// opening the file cannot create this object and can thus not perform edits either.
    /// Thus the drawing object is locked from all UI adjustments that would normally take place.
    ///
    /// 2.3.31 wpc (Wordprocessing canvas) element that specifies a drawing canvas in WordprocessingML.
    /// </remarks>
    static GroupShape readLockedCanvas(DocxDocumentReaderBase reader) throws Exception
    {
        String tagName = reader.getXmlReader().getLocalName();
        if ((!"lockedCanvas".equals(tagName)) && (!"wpc".equals(tagName)))
            return null;

        return readGroupShape(reader, tagName);
    }

    /// <summary>
    /// Reads group shapes from XML.
    /// </summary>
    /// <remarks>
    /// 20.1.2.2.20 grpSp (Group shape)
    /// This element specifies a group shape that represents many shapes grouped together.
    /// This shape is to be treated just as if it were a regular shape but instead of being
    /// described by a single geometry it is made up of all the shape geometries encompassed
    /// within it. Within a group shape each of the shapes that make up the group are specified
    /// just as they normally would. The idea behind grouping elements however is that a single
    /// transform can apply to many shapes at the same time.
    ///
    /// 2.3.30 wgp (Wordprocessing Group Shape) element that specifies a group in WordprocessingML.
    /// </remarks>
    static GroupShape readGroup(DocxDocumentReaderBase reader) throws Exception
    {
        String tagName = reader.getXmlReader().getLocalName();
        if ((!"grpSp".equals(tagName)) && (!"wgp".equals(tagName)) && (!"spTree".equals(tagName)))
            return null;

        GroupShape dml = readGroupShape(reader, tagName);

        // Set IsDrawingExtensions because there is ISO 29500 extension Wordprocessing group shape.
        if (dml.getDmlNode().getDmlNodeType() == DmlNodeType.WORDPROCESSING_GROUP_SHAPE)
            reader.getComplianceInfo().isDrawingExtensions(true);

        return dml;
    }

    private void readCompositeNode(GroupShape dml) throws Exception
    {
        String tagName = getXmlReader().getLocalName();
        /*DmlNodeType*/int dmlNodeType = dml.getDmlNode().getDmlNodeType();

        while (getXmlReader().readChild(tagName))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"grpSpPr"*/0:
                    DmlNodePropertiesReader.readVisualGroupShapeProperties(mDocumentReader, (DmlCompositeNode)dml.getDmlNode());
                    break;
                case /*"sp"*/1:
                case /*"cxnSp"*/2:
                case /*"wsp"*/3:
                {
                    Shape childDml = DmlShapeReader.read(mDocumentReader);
                    mDocumentReader.addToDmlTextboxesMap(childDml);
                    break;
                }
                case /*"grpSp"*/4:
                case /*"wgp"*/5:
                    readGroup(mDocumentReader);
                    break;
                case /*"pic"*/6:
                    DmlPictureReader.read(mDocumentReader);
                    break;
                case /*"txSp"*/7:
                {
                    if (dml.getDmlNode() instanceof DmlGroupShape)
                        ((DmlGroupShape)dml.getDmlNode()).setTextShape(DmlTextShapeReader.read(mDocumentReader));
                    else
                        warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
                case /*"nvGrpSpPr"*/8:
                {
                    if (dml.getDmlNode().getNonVisualPr() == null)
                        dml.getDmlNode().setNonVisualPr(new DmlNvPrGroupShape());

                    DmlNodePropertiesReader.readNonVisualProperties(dml.getDmlNode().getNonVisualPr(), mDocumentReader);
                    break;
                }
                case /*"cNvPr"*/9:
                    readNonVisualCanvasProperties(dml.getDmlNode());
                    break;
                case /*"cNvFrPr"*/10: // 20.4.2.25 cNvFrPr (Non-Visual Graphic Frame Drawing Properties).
                case /*"cNvGrpSpPr"*/11:
                    readNonVisualDrawingProperties(dml.getDmlNode());
                    break;
                case /*"bg"*/12:
                {
                    if (dmlNodeType == DmlNodeType.WORDPROCESSING_CANVAS)
                        ((DmlLockedCanvas)dml.getDmlNode()).setFill(readCanvasBackground());
                    else
                        warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
                case /*"whole"*/13:
                {
                    if (dmlNodeType == DmlNodeType.WORDPROCESSING_CANVAS)
                        ((DmlLockedCanvas)dml.getDmlNode()).setOutline(readCanvasOutline());
                    else
                        warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
                case /*"graphicFrame"*/14:
                    readGroupShape(mDocumentReader, getXmlReader().getLocalName());
                    break;
                case /*"contentPart"*/15: // ISO 29500 20.4.2.29 contentPart (Content Part)
                    DmlContentPartReader.read(mDocumentReader);
                    break;
                case /*"xfrm"*/16:
                {
                    // According to schema definition of the graphical object container,
                    // this element has transformation element of the type “CT_Transform2D” (not “CT_GroupTransform2D”).
                    if (dmlNodeType == DmlNodeType.GRAPHIC_FRAME)
                        dml.getDmlNode().setTransform(DmlTransformReader.readTransform(getXmlReader(), mDocumentReader.getComplianceInfo()));
                    else
                        warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
                case /*"graphic"*/17:
                {
                    if (dmlNodeType == DmlNodeType.GRAPHIC_FRAME)
                        DmlGraphicsReader.read(mDocumentReader);
                    else
                        warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
                case /*"extLst"*/18:
                {
                    if (dmlNodeType == DmlNodeType.GRAPHIC_FRAME)
                        ((DmlGroupShape)dml.getDmlNode()).setExtensions(DmlExtensionListReader.read(mDocumentReader));
                    else
                        warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
                case /*"nvGraphicFramePr"*/19:
                {
                    Debug.assertTrue(dmlNodeType == DmlNodeType.GRAPHIC_FRAME);
                    dml.getDmlNode().setNonVisualPr(new DmlNvPrGraphicFrame());
                    DmlNodePropertiesReader.readNonVisualProperties(dml.getDmlNode().getNonVisualPr(), mDocumentReader);
                    break;
                }
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
    }

    /// <summary>
    /// Reads non-visual canvas properties.
    /// </summary>
    /// <param name="dml">DrawingML shape.</param>
    private void readNonVisualCanvasProperties(DmlNode dml) throws Exception
    {
        Debug.assertTrue(dml != null);

        checkNonVisualProperties(dml);
        dml.getNonVisualPr().setNvDrawingProperties(DmlNodePropertiesReader.readNonVisualDrawingProperties(mDocumentReader));
    }

    /// <summary>
    /// Reads non-Visual drawing properties.
    /// </summary>
    /// <param name="dml">DrawingML shape.</param>
    private void readNonVisualDrawingProperties(DmlNode dml) throws Exception
    {
        Debug.assertTrue(dml != null);

        checkNonVisualProperties(dml);
        dml.getNonVisualPr().setCNvProperties(DmlNodePropertiesReader.readNonVisualConnectorProperties(mDocumentReader));
    }

    /// <summary>
    /// Initializes non-visual properties of the DrawingML shape.
    /// </summary>
    /// <param name="dml">DrawingML shape.</param>
    private static void checkNonVisualProperties(DmlNode dml)
    {
        Debug.assertTrue(dml != null);

        boolean isGraphicFrame = (dml.getDmlNodeType() == DmlNodeType.GRAPHIC_FRAME);

        if (dml.getNonVisualPr() == null)
            dml.setNonVisualPr(isGraphicFrame ? new DmlNvPrGraphicFrame() : (DmlNvPrBase)new DmlNvPrGroupShape());
    }

    /// <summary>
    /// Reads group shape data of the DrawingML shape.
    /// </summary>
    /// <param name="reader">Document reader.</param>
    /// <param name="tagName">Name of the tag.</param>
    private static GroupShape readGroupShape(DocxDocumentReaderBase reader, String tagName) throws Exception
    {
        GroupShape dml = new GroupShape(reader.getDocument(), ShapeMarkupLanguage.DML);

        /*DmlNodeType*/int dmlType = getShapeType(tagName);
        boolean isCanvas = (dmlType == DmlNodeType.WORDPROCESSING_CANVAS) ||
                        (dmlType == DmlNodeType.LOCKED_CANVAS);

        reader.addAndPushContainer(dml);
        dml.setDmlNode(isCanvas ? new DmlLockedCanvas(dmlType) : (DmlNode)new DmlGroupShape(dmlType));
        DmlCompositeNodeReader compositeNodeReader = new DmlCompositeNodeReader(reader);
        compositeNodeReader.readCompositeNode(dml);
        reader.popContainer(NodeType.GROUP_SHAPE);

        return dml;
    }

    private DmlOutline readCanvasOutline() throws Exception
    {
        DmlOutline outline = null;
        while (getXmlReader().readChild("whole"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"ln"*/20:
                    outline = DmlOutlineReader.read(mDocumentReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return outline;
    }

    private DmlFill readCanvasBackground() throws Exception
    {
        DmlFill fill = null;
        while (getXmlReader().readChild("bg"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"blipFill"*/21:
                case /*"gradFill"*/22:
                case /*"grpFill"*/23:
                case /*"noFill"*/24:
                case /*"pattFill"*/25:
                case /*"solidFill"*/26:
                    fill = DmlFillReader.read(mDocumentReader);
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }
        return fill;
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"grpSpPr",
		"sp",
		"cxnSp",
		"wsp",
		"grpSp",
		"wgp",
		"pic",
		"txSp",
		"nvGrpSpPr",
		"cNvPr",
		"cNvFrPr",
		"cNvGrpSpPr",
		"bg",
		"whole",
		"graphicFrame",
		"contentPart",
		"xfrm",
		"graphic",
		"extLst",
		"nvGraphicFramePr",
		"ln",
		"blipFill",
		"gradFill",
		"grpFill",
		"noFill",
		"pattFill",
		"solidFill"
	);

}

