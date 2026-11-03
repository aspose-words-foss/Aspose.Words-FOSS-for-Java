// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides methods for reading paragraph properties in DOCX.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxParaPrReader extends NrxParaPrReaderBase
{
    DocxParaPrReader(DocxRunPrReader runPrReader,
        DocxSectPrReader sectPrReader)
    {
    	super(runPrReader, sectPrReader);
	
    }

    protected /*override*/ boolean readFormatSpecific(String localName, 
        NrxDocumentReaderBase reader, ParaPr paraPr, RunPr runPr) throws Exception
    {
        switch (gStringSwitchMap.of(localName))
        {
            case /*"pPrChange"*/0:
                mAnnotationReader.readParaPrRevision(reader, paraPr, runPr);
                return true;
            default:
                return false;
        }
    }

    protected /*override*/ boolean readFormatSpecificNumPr(String localName, NrxXmlReader xmlReader, ParaPr paraPr)
    {
        switch (gStringSwitchMap.of(localName))
        {
            case /*"ins"*/1:
                DocxAnnotationReader.readNumberingInsertion(xmlReader, paraPr);
                return true;
            case /*"numberingChange"*/2:
                DocxAnnotationReader.readNumberingChange(xmlReader, paraPr);
                return true;
            default:
                return false;
        }
    }

    void setAnnotationReader(DocxAnnotationReader annotationReader)
    {
        Debug.assertTrue(annotationReader != null);
        mAnnotationReader = annotationReader;
    }

    private DocxAnnotationReader mAnnotationReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"pPrChange",
		"ins",
		"numberingChange"
	);

}
