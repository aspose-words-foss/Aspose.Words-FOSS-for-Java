// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides static method for reading header/footer parts.
/// </summary>
/*static*/ class DocxHeaderFooterReader
{
	/* Simulation of static class by using private constructor */
	private DocxHeaderFooterReader()
	{}

    /// <summary>
    /// Reads header/footer document part.
    /// </summary>
    /// <param name="reader">DocxReader to read from. Should be positioned to
    /// headerReference or footerReference element start.</param>
    static void read(NrxDocumentReaderBase reader) throws Exception
    {
        Debug.assertTrue(reader instanceof DocxDocumentReaderBase);
        DocxDocumentReaderBase docReader = (DocxDocumentReaderBase) reader;

        NrxXmlReader xmlReader = docReader.getXmlReader();

        boolean isHeader = ("headerReference".equals(xmlReader.getLocalName()));

        // Read attributes.
        String type = null;
        String id = null;
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/0: // w:type
                    type = xmlReader.getValue();
                    break;
                case /*"id"*/1: // r:id
                    id = xmlReader.getValue();
                    break;
                default:
                    // Ignore.
                    break;
            }
        }

        // Create the header object.
        /*HeaderFooterType*/int headerFooterType = NrxSectEnum.xmlToHeaderFooterType(type, isHeader);
        HeaderFooter headerFooter = new HeaderFooter(docReader.getDocument(), headerFooterType);

        // Read the header part.
        DocxXmlReader partReader = docReader.switchToPartReaderByRelId(id);
        if (partReader != null)
        {
            docReader.isInHeaderFooter(true);
            DocxReaderFactory.getStoryReader().read(docReader, headerFooter);
            headerFooter.isLinkedToPrevious(false);
            docReader.isInHeaderFooter(false);
            docReader.restorePartReader();

            // WORDSNET-28848 Look for parent section ignoring current container node type.
            Section section = (Section)docReader.getCurContainer().getAncestor(NodeType.SECTION);
            docReader.insertHeaderFooter(section, headerFooter);
        }
        else
        {
            docReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, WarningStrings.HEADER_FOOTER_PART_MISSING);
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"type",
		"id"
	);

}

