// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackagePart;
import com.aspose.ms.System.IO.Stream;
import java.util.Map;


/// <summary>
/// Encapsulates an XML reader together with a package part.
/// </summary>
class DocxXmlReader extends NrxXmlReader
{
    DocxXmlReader(OpcPackagePart part, OoxmlComplianceInfo cInfo) throws Exception
    {
    	this(part, null, WarningSource.DOCX, cInfo);
	
    }

    DocxXmlReader(OpcPackagePart part, IWarningCallback warningCallback, /*WarningSource*/int warningSource, OoxmlComplianceInfo cInfo) throws Exception
    {
    	super(part.getStream(), warningCallback, warningSource);
	
        initialize(part, cInfo);
    }

    DocxXmlReader(Stream stream, OoxmlComplianceInfo cInfo) throws Exception
    {
    	this(stream, null, cInfo);
	
    }

    DocxXmlReader(Stream stream, IWarningCallback warningCallback, OoxmlComplianceInfo cInfo) throws Exception
    {
    	super(stream, warningCallback, WarningSource.DOCX);
	
        initialize(null, cInfo);
    }

    DocxXmlReader(OpcPackagePart part, OoxmlComplianceInfo cInfo, String xml,
        Map<String, String> namespaces)
    {
    	super(xml, namespaces);
	
        initialize(part, cInfo);
    }

    /// <summary>
    /// Defined in <see cref="NrxXmlReader"/>, allows to share the implementation of reading borders.
    /// </summary>
    protected /*override*/ /*LineStyle*/int readLineStyle()
    {
        return DocxEnum.docxToLineStyle(getValue());
    }

    /// <summary>
    /// Initializes state of the reader.
    /// </summary>
    private void initialize(OpcPackagePart part, OoxmlComplianceInfo cInfo)
    {
        mPart = part;
        mComplianceInfo = cInfo;
    }

    /// <summary>
    /// The OPC part that this reader is reading.
    /// </summary>
    OpcPackagePart getPart() { return mPart; }

    OoxmlComplianceInfo getComplianceInfo() { return mComplianceInfo; }

    private OpcPackagePart mPart;
    private OoxmlComplianceInfo mComplianceInfo;
}

