// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides methods for reading run of text in DOCX.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxRunReader extends NrxRunReaderBase
{
    DocxRunReader(NrxStoryReaderBase storyReader, 
        DocxRunPrReader runPrReader, 
        DocxParaReader paraReader,
        DocxFldCharReader fldCharReader,
        NrxFldSimpleReader fldSimpleReader,
        DocxHyperlinkReader hyperlinkReader,
        DocxAnnotationReader annotationReader,
        INrxVmlReader vmlReader)
    {
    	super(storyReader, runPrReader, paraReader, fldCharReader, fldSimpleReader, hyperlinkReader, vmlReader);
	
        Debug.assertTrue(annotationReader != null);
        mAnnotationReader = annotationReader;
    }

    protected /*override*/ boolean readFormatSpecific(String localName, NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        switch (gStringSwitchMap.of(localName))
        {
            case /*"altChunk"*/0:
                DocxAltChunkReader.read(reader);
                return true;
            case /*"del"*/1:
                // WORDSNET-10596 there are revision 'del' elements inside math <m:r>.
                mAnnotationReader.readMathRunPrRevision(reader, runPr, true);
                return true;
            case /*"ins"*/2:
                // WORDSNET-10596 there are revision 'ins' elements inside math <m:r>.
                mAnnotationReader.readMathRunPrRevision(reader, runPr, false);
                return true;
            default:
                return false;
        }
    }

    protected /*override*/ void readTable(NrxDocumentReaderBase reader) throws Exception
    {
        DocxTableReader.read(reader);
    }

    protected /*override*/ Footnote getFootnote(NrxDocumentReaderBase reader, 
        int id, /*FootnoteType*/int footnoteType, RunPr runPr)
    {
        return reader.getFootnoteById(footnoteType, id);
    }

    protected /*override*/ Comment getComment(NrxDocumentReaderBase reader, RunPr runPr)
    {
        int id = reader.getXmlReader().readIntAttribute("id", 0);
        return reader.getCommentById(id);
    }

    private /*final*/ DocxAnnotationReader mAnnotationReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"altChunk",
		"del",
		"ins"
	);

}

