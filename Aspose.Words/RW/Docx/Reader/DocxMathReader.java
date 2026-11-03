// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Reads m:oMath and m:oMathPara objects and all possible children into a math tree.
/// </summary>
class DocxMathReader extends NrxMathReaderBase
{
    DocxMathReader(NrxDocumentReaderBase reader,
        DocxRunPrReader runPrReader,
        DocxInlineReader inlineReader,
        DocxAnnotationReader annotationReader)
    {
    	super(reader, runPrReader, inlineReader);
	
        Debug.assertTrue(annotationReader != null);
        mAnnotationReader = annotationReader;
    }

    protected /*override*/ boolean readFormatSpecificAttribute(String attrName, IMathRunPr rPr) throws Exception
    {
        switch (gStringSwitchMap.of(attrName))
        {
            case /*"del"*/0:
                mAnnotationReader.readMathRunPrRevision(getDocumentReader(), (RunPr)rPr, true);
                return true;
            case /*"ins"*/1:
                mAnnotationReader.readMathRunPrRevision(getDocumentReader(), (RunPr)rPr, false);
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Read root-level math object m:oMathPara
    /// </summary>
    static void readOMathPara(NrxDocumentReaderBase reader) throws Exception
    {
        DocxMathReader mathReader = DocxReaderFactory.createMathReader(reader);
        NrxMathReaderUtil.readOMathPara(mathReader);
    }
    
    /// <summary>
    /// Read root-level math object m:oMath
    /// </summary>
    static void readOMath(NrxDocumentReaderBase reader) throws Exception
    {
        DocxMathReader mathReader = DocxReaderFactory.createMathReader(reader);
        NrxMathReaderUtil.readOMath(mathReader);
    }

    private /*final*/ DocxAnnotationReader mAnnotationReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"del",
		"ins"
	);

}
