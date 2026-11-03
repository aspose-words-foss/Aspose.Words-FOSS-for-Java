// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Implements reading 2.1.4 commentsIds [MS-DOCX] document parts.
/// </summary>
/*static*/ class DocxCommentsIdsReader
{
	/* Simulation of static class by using private constructor */
	private DocxCommentsIdsReader()
	{}

    /// <summary>
    /// Reads a commentsIds document part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getCommentsIds());
        if (xmlReader == null)
            return;

        reader.getComplianceInfo().markAsHasDocxExtensionsOf(MsWordVersionCore.WORD_2016);

        while (xmlReader.readChild("commentsIds"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"commentId"*/0:
                {
                    int paraId = NrxXmlUtil.tryHexToInt(xmlReader.readAttribute("paraId", "0"));
                    int durableId = NrxXmlUtil.tryHexToInt(xmlReader.readAttribute("durableId", "0"));
                    reader.mapParaIdAndDurableId(paraId, durableId);
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
		"commentId"
	);

}

