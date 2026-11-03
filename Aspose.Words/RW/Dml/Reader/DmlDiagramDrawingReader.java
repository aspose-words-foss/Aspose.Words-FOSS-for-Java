// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Class used to read DML diagram drawing.
/// </summary>
class DmlDiagramDrawingReader extends DmlReaderBase
{
    private DmlDiagramDrawingReader()
    {
    }

    static GroupShape read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        if (!"drawing".equals(xmlReader.getLocalName()))
            return null;

        GroupShape diagramDrawing = null;
        while (xmlReader.readChild("drawing"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"spTree"*/0:
                    diagramDrawing = DmlCompositeNodeReader.readGroup(reader);
                    break;
                default:
                    warnNotSupportedAndIgnoreElement(xmlReader);
                    break;
            }
        }

        return diagramDrawing;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"spTree"
	);

}

