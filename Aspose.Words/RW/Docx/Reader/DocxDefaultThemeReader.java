// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.StreamUtil;


class DocxDefaultThemeReader implements IDefaultThemeProvider
{
    /// <summary>
    /// Reads default theme from the Aspose.Words.Resources.theme1.xml
    /// </summary>
    public Theme getDefaultTheme() throws Exception
    {
        if (gDefaultTheme == null)
        {
            synchronized (G_DEFAULT_THEME_LOCK_OBJECT)
            {
                if (gDefaultTheme == null)
                {
                    gDefaultTheme = readDefaultTheme();
                }
            }
        }
        return gDefaultTheme;
    }

    private static Theme readDefaultTheme() throws Exception
    {
        Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        try /*JAVA: was using*/
        {
            stream.setPosition(0);
            DocxXmlReader xmlReader = new DocxXmlReader(stream, new OoxmlComplianceInfo());
            DocxDocumentReaderBase reader = DocxReaderFactory.createDocumentReader(getDocxDocumentReaderStub());
            reader.pushPartReader(xmlReader);
            return DocxThemeReader.readThemeXml((DocxDocumentReader)reader);
        }
        finally { if (stream != null) stream.close(); }
    }

    private static OpcPackagePart getDocxDocumentReaderStub() throws Exception
    {
        OpcPackagePart documentPartStub = new OpcPackagePart("document.xml", "test");
        documentPartStub.setStream(new MemoryStream());
        StreamUtil.writeAnsiStringToStream("<document />", documentPartStub.getStream());
        documentPartStub.getStream().setPosition(0);
        return documentPartStub;
    }

    private static Theme gDefaultTheme;
    private static /*final*/ Object G_DEFAULT_THEME_LOCK_OBJECT = new Object();
    private static final String RESOURCE_NAME = "Aspose.Words.Resources.theme1.xml";
}

