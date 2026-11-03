// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Represents a class reading DrawingML shape by xml.
/// </summary>
class DmlShapeReader extends DmlReaderBase
{
    private DmlShapeReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    static Shape read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlShapeReader shapeReader = new DmlShapeReader(reader);
        return shapeReader.readCore();
    }

    private Shape readCore() throws Exception
    {
        try
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"sp"*/0:
                case /*"wsp"*/1:
                case /*"cxnSp"*/2:
                    readShape();
                    break;
                default:
                    return null;
            }

            return mShape;
        }
        catch (UnsupportedOperationException e)
        {
            // Not a broken shape: the caller asked for something this build cannot do.
            throw e;
        }
        catch (Exception e)
        {
            // We are failed to read shape from xml.
            // Lets warn and rollback made changes, removing partially read shape.
            warnShapeIgnored(getXmlReader(), e.getMessage());
            CompositeNode curContainer = mDocumentReader.getCurContainer();
            mDocumentReader.popContainer(NodeType.SHAPE);
            curContainer.remove();

            return null;
        }
    }

    /// <summary>
    /// Reads the shape.
    /// </summary>
    /// <remarks>
    /// 20.1.2.2.33 sp (Shape)
    /// This element specifies the existence of a single shape. A shape can either be a preset or a
    /// custom geometry, defined using the DrawingML framework. In addition to a geometry each shape
    /// can have both visual and non-visual properties attached. Text and corresponding styling
    /// information can also be attached to a shape. This shape is specified along with all other
    /// shapes within either the shape tree or group shape elements.
    /// </remarks>
    private void readShape() throws Exception
    {
        String shapeTag = getXmlReader().getLocalName();
        mShape = new Shape(mDocumentReader.getDocument(), ShapeMarkupLanguage.DML);
        mDocumentReader.addAndPushContainer(mShape);
        DmlShape dmlShapeNode = new DmlShape(getShapeType(shapeTag));
        mShape.setDmlNode(dmlShapeNode);

        // Set IsDrawingExtensions because there is ISO 29500 extension Wordprocessing shape.
        if (dmlShapeNode.getDmlNodeType() == DmlNodeType.WORDPROCESSING_SHAPE)
            getComplianceInfo().isDrawingExtensions(true);

        dmlShapeNode.setNormalEastAsianFlow(getXmlReader().readBoolAttribute("normalEastAsianFlow", false));

        // Read model Id for diagram drawing shapes.
        dmlShapeNode.setModelId(DmlDiagramEnum.dmlToModelId(getXmlReader().readAttribute("modelId", "")));
        getXmlReader().moveToElement();

        while (getXmlReader().readChild(shapeTag))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"spPr"*/3:
                    DmlNodePropertiesReader.readShapeProperties(mDocumentReader, dmlShapeNode);
                    break;
                case /*"style"*/4:
                    dmlShapeNode.setStyle(DmlShapeStyleReader.read(getXmlReader(), getComplianceInfo()));
                    break;
                case /*"txbx"*/5:
                    readTextbox();
                    break;
                case /*"linkedTxbx"*/6:
                    {
                        mShape.setLinkedTextboxId(getXmlReader().readIntAttribute("id", 0));
                        mShape.setLinkedTextboxSeq(getXmlReader().readIntAttribute("seq", 0));
                        break;
                    }
                case /*"txSp"*/7:
                    dmlShapeNode.setTextShape(DmlTextShapeReader.read(mDocumentReader));
                    break;
                case /*"txBody"*/8:
                    {
                        // txBody element occur directly in sp element in chart's drawing,
                        // so simply generate text shape to handle it.
                        if (dmlShapeNode.getTextShape() == null)
                            dmlShapeNode.setTextShape(new DmlTextShape());

                        DmlTextShapeReader.readTextBody(mDocumentReader, dmlShapeNode.getTextShape().getTextBody(), "txBody");
                        break;
                    }
                case /*"bodyPr"*/9:
                    {
                        // bodyPr element occur directly in wsp element in chart's drawing,
                        // so simply generate text shape to handle it.
                        if (dmlShapeNode.getTextShape() == null)
                            dmlShapeNode.setTextShape(new DmlTextShape());

                        DmlTextShapeReader.readTextBodyProperties(mDocumentReader,
                            dmlShapeNode.getTextShape().getTextBody().getProperties(), getComplianceInfo());
                        break;
                    }
                case /*"txXfrm"*/10:
                    {
                        // I did not found this element in the specification,
                        // but it seems it defines target rectangle of the text shape.
                        if (dmlShapeNode.getTextShape() == null)
                            dmlShapeNode.setTextShape(new DmlTextShape());

                        dmlShapeNode.getTextShape().setTransform(DmlTransformReader.readTransform(getXmlReader(), getComplianceInfo()));
                        break;
                    }
                case /*"nvSpPr"*/11:
                    {
                        if (dmlShapeNode.getNonVisualPr() == null)
                            dmlShapeNode.setNonVisualPr(new DmlNvPrShape());
                        DmlNodePropertiesReader.readNonVisualProperties(dmlShapeNode.getNonVisualPr(), mDocumentReader);
                        break;
                    }
                case /*"nvCxnSpPr"*/12:
                    {
                        if (dmlShapeNode.getNonVisualPr() == null)
                            dmlShapeNode.setNonVisualPr(new DmlNvPrConnectionShape());
                        DmlNodePropertiesReader.readNonVisualProperties(dmlShapeNode.getNonVisualPr(), mDocumentReader);
                        break;
                    }
                case /*"cNvPr"*/13:
                    {
                        if (dmlShapeNode.getNonVisualPr() == null)
                        {
                            if (dmlShapeNode.getDmlNodeType() == DmlNodeType.CONNECTOR_SHAPE)
                                dmlShapeNode.setNonVisualPr(new DmlNvPrConnectionShape());
                            else
                                dmlShapeNode.setNonVisualPr(new DmlNvPrShape());
                        }

                        dmlShapeNode.getNonVisualPr().setNvDrawingProperties(DmlNodePropertiesReader.readNonVisualDrawingProperties(mDocumentReader));
                        break;
                    }
                case /*"cNvSpPr"*/14:
                    {
                        if (dmlShapeNode.getNonVisualPr() == null)
                            dmlShapeNode.setNonVisualPr(new DmlNvPrShape());
                        dmlShapeNode.getNonVisualPr().setCNvProperties(DmlNodePropertiesReader.readNonVisualConnectorProperties(mDocumentReader));
                        break;
                    }
                case /*"cNvCnPr"*/15:
                    {
                        if (dmlShapeNode.getNonVisualPr() == null)
                            dmlShapeNode.setNonVisualPr(new DmlNvPrConnectionShape());
                        dmlShapeNode.getNonVisualPr().setCNvProperties(DmlNodePropertiesReader.readNonVisualConnectorProperties(mDocumentReader));
                        break;
                    }
                default:
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
            }
        }

        mDocumentReader.popContainer(NodeType.SHAPE);
    }

    /// <summary>
    /// Reads 'txbx' element that specifies the textual contents of the shape if the shape
    /// is the first in the series of shapes for the same text box story.
    /// </summary>
    private void readTextbox() throws Exception
    {
        mShape.setTextboxId(getXmlReader().readIntAttribute("id", 0));
        while (getXmlReader().readChild("txbx"))
        {
            switch (gStringSwitchMap.of(getXmlReader().getLocalName()))
            {
                case /*"txbxContent"*/16: // w:txbxContent
                {
                    DocxReaderFactory.getStoryReader().read(mDocumentReader, mDocumentReader.getCurContainer());
                    break;
                }
                default:
                {
                    warnUnexpectedAndIgnoreElement(getXmlReader());
                    break;
                }
            }
        }
    }

    private NrxXmlReader getXmlReader() { return mDocumentReader.getXmlReader(); }

    private OoxmlComplianceInfo getComplianceInfo() { return mDocumentReader.getComplianceInfo(); }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;
    private Shape mShape;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"sp",
		"wsp",
		"cxnSp",
		"spPr",
		"style",
		"txbx",
		"linkedTxbx",
		"txSp",
		"txBody",
		"bodyPr",
		"txXfrm",
		"nvSpPr",
		"nvCxnSpPr",
		"cNvPr",
		"cNvSpPr",
		"cNvCnPr",
		"txbxContent"
	);

}

