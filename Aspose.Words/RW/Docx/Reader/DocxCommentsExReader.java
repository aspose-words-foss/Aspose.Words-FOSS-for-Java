// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/09/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Implements reading of comments extended data.
/// </summary>
/*static*/ class DocxCommentsExReader
{
	/* Simulation of static class by using private constructor */
	private DocxCommentsExReader()
	{}

    /// <summary>
    /// Reads the "CommentsEx" part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getCommentsEx());
        if (xmlReader == null)
            return;

        while (xmlReader.readChild("commentsEx"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"commentEx"*/0:
                {
                    int paraId = NrxXmlUtil.tryHexToInt(xmlReader.readAttribute("paraId", "00000000"));
                    int paraIdParent = NrxXmlUtil.tryHexToInt(xmlReader.readAttribute("paraIdParent", "00000000"));
                    boolean done = (!"0".equals(xmlReader.readAttribute("done", "0")));
                    reader.mapParaIdAndParentParaId(paraId, paraIdParent, done);
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"commentEx"
	);

}

