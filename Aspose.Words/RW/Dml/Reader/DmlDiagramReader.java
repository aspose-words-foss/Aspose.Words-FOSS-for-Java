// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/11/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Class used to read DML diagrams.
/// </summary>
class DmlDiagramReader extends DmlReaderBase
{
    private DmlDiagramReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
    }

    /// <summary>
    /// Builds <see cref="DmlDiagram"/>, current node must be 21.4.2.22 relIds (Explicit Relationships to Diagram Parts).
    /// </summary>
    static Shape read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        if (!"relIds".equals(xmlReader.getLocalName()))
            return null;

        DmlDiagramReader diagramReader = new DmlDiagramReader(reader);

        // Read id of the chart part and get it.
        Shape diagram = new Shape(reader.getDocument(), ShapeMarkupLanguage.DML);
        reader.addAndPushContainer(diagram);
        DmlDiagram diagramDml = new DmlDiagram();
        diagram.setDmlNode(diagramDml);
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"cs"*/0:
                    diagramDml.setColorTransformDefinition(diagramReader.readColors(xmlReader.getValue()));
                    break;
                case /*"dm"*/1:
                    diagramDml.setData(diagramReader.readDataModel(xmlReader.getValue()));
                    break;
                case /*"lo"*/2:
                    diagramDml.setLayout(diagramReader.readLayout(xmlReader.getValue()));
                    break;
                case /*"qs"*/3:
                    diagramDml.setStyleDefinition(diagramReader.readStyles(xmlReader.getValue()));
                    break;
                default:
                    warnUnexpected(xmlReader);
                    break;
            }
        }

        if (diagramDml.getData().getDrawing()!= null)
            diagramDml.getData().getDrawing().setParent(diagram);

        reader.popContainer(NodeType.SHAPE);

        return diagram;
    }

    private DmlDiagramLayout readLayout(String dataPartId) throws Exception
    {
        mDocumentReader.switchToPartReaderByRelId(dataPartId);
        DmlDiagramLayout layout = DmlDiagramLayoutReader.read(mDocumentReader, dataPartId);
        mDocumentReader.restorePartReader();

        return layout;
    }

    private DmlDiagramDataModel readDataModel(String dataPartId) throws Exception
    {
        mDocumentReader.switchToPartReaderByRelId(dataPartId);
        DmlDiagramDataModel dataModel = DmlDiagramDataReader.read(mDocumentReader);
        mDocumentReader.restorePartReader();

        dataModel.setDrawing(readDiagramDrawing(getDrawingRelId(dataModel)));

        return dataModel;
    }

    /// <summary>
    /// Gets DrawingRelId from dataModel extension.
    /// </summary>
    private static String getDrawingRelId(DmlDiagramDataModel dataModel)
    {
        String drawingId = dataModel.getDrawingId();

        // If dataModel extension is set, DrawingRelId from this extension have to be used.
        // Try to get DrawingRelId from dataModel extension.
        // If there are no extensions, there is nothing to do here.
        if (dataModel.hasExtensions())
        {
            DmlExtension dataModelExt = dataModel.getExtensions().get(DmlExtensionUri.DATA_MODEL_EXT);

            if (dataModelExt != null)
                drawingId = dataModelExt.getDrawingRelId();
        }

        return drawingId;
    }

    private ShapeBase readDiagramDrawing(String drawingRelId) throws Exception
    {
        if (!StringUtil.hasChars(drawingRelId))
            return null;

        // WORDSNET-6787 Skip reading drawing part if there is no rel target for it.
        String relTarget = mDocumentReader.getRelationshipTarget(drawingRelId);
        if (!StringUtil.hasChars(relTarget) || mDocumentReader.isExternalPackage(drawingRelId))
            return null;

        ShapeBase drawing = readDiagramDrawingContent(drawingRelId);

        // We immediately remove diagram drawing from parent drawingML, because it should not be present in the model.
        // This is simply last time successfully rendered diagram cache, we must use it for rendering only.
        if (drawing != null)
        {
            drawing.remove();
        }

        return drawing;
    }

    /// <summary>
    /// Read content of the diagram drawing object.
    /// </summary>
    /// <param name="drawingRelId">Link identifier to diagram drawing.</param>
    /// <returns>Object of the drawing layer.</returns>
    private ShapeBase readDiagramDrawingContent(String drawingRelId) throws Exception
    {
        ShapeBase drawing = null;
        final String E_MSG_PREF = "Detail message";
        final String E_MSG = "Can not parse content some of diagram drawing object from specified path. {0}: {1}";

        try
        {
            mDocumentReader.switchToPartReaderByRelId(drawingRelId);
            // "drawingReader" does not need check for null, when package part can not be found
            // "InvalidOperationException" will throw. Package part always has content data stream of the part.
            drawing = DmlDiagramDrawingReader.read(mDocumentReader);
        }
        // In Java here is XMLStreamException wrapped by IllegalStateException because
        // original XMLStreamException is thrown by external lib.
        catch (IllegalStateException ex)
        {
            // WORDSNET-13443 Skip any errors which occur while parsing diagram drawing object.
            String msg = msString.format(E_MSG, E_MSG_PREF, ex.getMessage());
            mDocumentReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DRAWING_ML, msg);
        }

        mDocumentReader.restorePartReader();
        return drawing;
    }

    private DmlDiagramColorTransform readColors(String dataPartId) throws Exception
    {
        mDocumentReader.switchToPartReaderByRelId(dataPartId);
        DmlDiagramColorTransform colorTransform =
            DmlDiagramColorTransformReader.read(mDocumentReader, mDocumentReader.getComplianceInfo());
        mDocumentReader.restorePartReader();

        return colorTransform;
    }

    private DmlDiagramStyleDefinition readStyles(String dataPartId) throws Exception
    {
        mDocumentReader.switchToPartReaderByRelId(dataPartId);
        DmlDiagramStyleDefinition styleDefinition =
            DmlDiagramStylesReader.read(mDocumentReader, mDocumentReader.getComplianceInfo());
        mDocumentReader.restorePartReader();

        return styleDefinition;
    }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"cs",
		"dm",
		"lo",
		"qs"
	);

}

