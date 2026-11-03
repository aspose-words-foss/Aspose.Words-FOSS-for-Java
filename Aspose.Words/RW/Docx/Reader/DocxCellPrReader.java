// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/01/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxCellPrReader extends NrxCellPrReaderBase
{
    DocxCellPrReader(DocxParaReader paraReader)
    {
    	super(paraReader);
	
    }

    protected /*override*/ boolean readFormatSpecific(String localName, NrxDocumentReaderBase reader, CellPr cellPr) throws Exception
    {
        switch (gStringSwitchMap.of(localName))
        {
            case /*"tcPrChange"*/0: //aml:annotation
                DocxAnnotationReader.readCellPrRevision(reader, cellPr);
                return true;
            case /*"cellIns"*/1:
                DocxAnnotationReader.readEditRevision(reader, cellPr, EditRevisionType.INSERTION);
                return true;
            case /*"cellDel"*/2:
                DocxAnnotationReader.readEditRevision(reader, cellPr, EditRevisionType.DELETION);
                return true;
            default:
                return false;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"tcPrChange",
		"cellIns",
		"cellDel"
	);

}
