// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


class DocxCustomXmlMarkupReader
{
    DocxCustomXmlMarkupReader(DocxInlineReader inlineReader, /*MarkupLevel*/int level)
    {
    	this(inlineReader, level, null);
	
    }

    DocxCustomXmlMarkupReader(DocxInlineReader inlineReader, /*MarkupLevel*/int level, TablePr objPr)
    {
        Debug.assertTrue(inlineReader != null);
        mInlineReader = inlineReader;
        mLevel = level;
        mObjPr = objPr;
    }

    /// <summary>
    /// Reads customXml object of any level.
    /// </summary>
    /// <returns>
    /// For all readers except <see cref="DocxRowReader"/> this parameter is not used.
    /// For <see cref="DocxRowReader"/> it returns gridBefore value.
    /// </returns>
    /// <param name="reader">DocxReader to read from. Should be positioned to element start.</param>
    void read(NrxDocumentReaderBase reader) throws Exception
    {
        // WORDSNET-10749 CustomXml is no longer supported.
        reader.warn(WarningType.DATA_LOSS, WarningSource.DOCX, WarningStrings.VIOLATION_I_4_I);
        
        NrxXmlReader xmlReader = reader.getXmlReader();
        
        // Read elements.
        xmlReader.moveToElement();
        while (xmlReader.readChild("customXml"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"customXmlPr"*/0:
                    // WORDSNET-10749 We are not reading CustomXml now and should skip reading its properties too.
                    break;
                default:
                    readCustomXmlContent(reader);
                    break;
            }
        }
    }

    /// <summary>
    /// Read contents of customXml.
    /// </summary>
    private void readCustomXmlContent(NrxDocumentReaderBase reader) throws Exception
    {
        switch (mLevel)
        {
            case MarkupLevel.INLINE:
                mInlineReader.readChild(reader);
                break;
            case MarkupLevel.BLOCK:
                DocxReaderFactory.getStoryReader().readChild(reader);
                break;
            case MarkupLevel.ROW:
                DocxTableReader.readChild(reader, mObjPr);
                break;
            case MarkupLevel.CELL:
                DocxRowReader.readChild(reader, mObjPr);
                break;
            default:
                throw new IllegalStateException("Unexpected custom XML markup level.");
        }
    }

    private /*final*/ DocxInlineReader mInlineReader;
    private /*final*/ /*MarkupLevel*/int mLevel;

    /// <summary>
    /// The same Pr is used either for storing TablePr or rowPr.
    /// </summary>
    private /*final*/ TablePr mObjPr;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"customXmlPr"
	);

}


