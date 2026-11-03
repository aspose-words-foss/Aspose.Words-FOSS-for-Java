package com.aspose.ms.System.Text;

import java.nio.charset.Charset;

public class EncodingDataItem
{
    public EncodingDataItem(int codePage, String encodingInfoName)
    {
        this(codePage, encodingInfoName, encodingInfoName, encodingInfoName, encodingInfoName, 1252);
    }

    public EncodingDataItem(int codePage, String encodingInfoName, String bodyName, String headerName, String webName, int windowsCodePage)
    {
        this(codePage, encodingInfoName, bodyName, headerName, webName, windowsCodePage, encodingInfoName);
    }

    public EncodingDataItem(int codePage, String encodingInfoName, String bodyName, String headerName, String webName, int windowsCodePage, String knownJavaCharsetName)
    {
        CodePage = codePage;
        EncodingInfoName = encodingInfoName; // use for search
        BodyName = bodyName;
        HeaderName = headerName;
        WebName = webName;
        WindowsCodePage = windowsCodePage;
        JavaCharsetName = knownJavaCharsetName == null ? encodingInfoName : knownJavaCharsetName;
    }

    public boolean isSupported()
    {
        return Charset.isSupported(JavaCharsetName)
                || Charset.isSupported(EncodingInfoName)
                || Charset.isSupported(BodyName)
                || Charset.isSupported(HeaderName)
                || Charset.isSupported(WebName);
    }

    public final int CodePage;
    public final String EncodingInfoName;
    public final String BodyName;
    public final String HeaderName;
    public final String WebName;
    public final int WindowsCodePage;
    public final String JavaCharsetName;
}
