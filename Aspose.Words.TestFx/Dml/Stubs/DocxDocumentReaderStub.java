// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2018 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.msString;
import com.aspose.OpcPackagePart;


class DocxDocumentReaderStub extends DocxDocumentReaderBase
{
    DocxDocumentReaderStub(String xml) throws Exception
    {
    	this(xml, null);
	
    }

    DocxDocumentReaderStub(String xml,
        IWarningCallback warningCallback) throws Exception
    {
    	super(new OpcPackage(),
            getDocxDocumentReaderStub(),
            new Document(),
            new LoadOptions(),
            new OoxmlComplianceInfo(),
            DocxReaderFactory.getStylesReader(),
            DocxReaderFactory.getStoryReader(),
            DocxReaderFactory.getNumberingReader(),
            DocxReaderFactory.getSectPrReader());
	
        MemoryStream stream = new MemoryStream();
        StreamUtil.writeAnsiStringToStream(xml, stream);
        stream.setPosition(0);

        DocxXmlReader xmlReader = new DocxXmlReader(stream, warningCallback, new OoxmlComplianceInfo());
        pushPartReader(xmlReader);
    }

    DocxDocumentReaderStub(Stream stream) throws Exception
    {
    	super(new OpcPackage(),
            getDocxDocumentReaderStub(),
            new Document(),
            new LoadOptions(),
            new OoxmlComplianceInfo(),
            DocxReaderFactory.getStylesReader(),
            DocxReaderFactory.getStoryReader(),
            DocxReaderFactory.getNumberingReader(),
            DocxReaderFactory.getSectPrReader());
	
        DocxXmlReader xmlReader = new DocxXmlReader(stream, new OoxmlComplianceInfo());
        pushPartReader(xmlReader);
    }

    protected /*override*/ void doRead()
    {
        throw new UnsupportedOperationException();
    }

    public /*override*/ String getRelationshipTarget(String relId)
    {
        return msString.format("reltaget/{0}", relId);
    }

    /*override*/ boolean isEquationXmlReader() { return false; }

    public /*override*/ byte[] getBinData(String relId)
    {
        return new byte[0];
    }

    private static OpcPackagePart getDocxDocumentReaderStub() throws Exception
    {
        OpcPackagePart documentPartStub = new OpcPackagePart("document.xml", "test");
        documentPartStub.setStream(new MemoryStream());
        StreamUtil.writeAnsiStringToStream("<document />", documentPartStub.getStream());
        documentPartStub.getStream().setPosition(0);
        return documentPartStub;
    }
}

