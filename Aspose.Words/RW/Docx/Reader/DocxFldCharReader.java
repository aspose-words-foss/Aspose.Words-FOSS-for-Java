// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/01/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Helps to read fldChar from DOCX document. 
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxFldCharReader extends NrxFldCharReaderBase
{
    protected /*override*/ boolean readFormatSpecificAttribute(String attrName, NrxDocumentReaderBase reader, RunPr runPr)
    {
        switch (gStringSwitchMap.of(attrName))
        {
            case /*"ffData"*/0:
                DocxFfDataReader.read(reader.getXmlReader(), reader.getComplianceInfo());
                return true;
            default:
                return false;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ffData"
	);

}
