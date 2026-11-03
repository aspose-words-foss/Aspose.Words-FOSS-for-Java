// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/07/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.Encoding;
import com.aspose.ArgumentUtil;


/// <summary>
/// The base class for specifying additional options when saving a document into a text based formats.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-save-options/">Specify Save Options</a> documentation article.</para>
/// </summary>
public abstract class TxtSaveOptionsBase extends SaveOptions
{
    /// <summary>
    /// Specifies the encoding to use when exporting in text formats. 
    /// Default value is <ms><b>Encoding.UTF8</b></ms><java>'UTF-8' Charset</java><cpp><b>Encoding.UTF8</b></cpp>.
    /// </summary>
    /// <javaName>Encoding(java.nio.charset.Charset)</javaName>
    //JAVA: property accessor is internalized
	Encoding getEncodingInternal() { return mEncoding; }
	//JAVA-added public wrapper for internalized property accessor
	public java.nio.charset.Charset getEncoding() { return Encoding.toJava(getEncodingInternal()); }
    //JAVA: property accessor is internalized
	void setEncodingInternal(Encoding value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");
        mEncoding = value;
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setEncoding(java.nio.charset.Charset value) { setEncodingInternal(Encoding.fromJava(value)); }

    /// <summary>
    /// Specifies the string to use as a paragraph break when exporting in text formats.
    /// </summary>
    /// <remarks>
    /// <p>The default value is <see cref="ControlChar.CrLf"/>.</p>
    /// </remarks>
    public String getParagraphBreak() { return mParagraphBreak; }
    public void setParagraphBreak(String value)
    {
        ArgumentUtil.checkNotNull(value, "ParagraphBreak");
        mParagraphBreak = value;
    }

    /// <summary>
    /// <para>Allows to specify whether the page breaks should be preserved during export.</para>
    /// <para>The default value is <c>false</c>.</para>
    /// </summary>
    /// <remarks>
    /// The property affects only page breaks that are inserted explicitly into a document. 
    /// It is not related to page breaks that MS Word automatically inserts at the end of each page.
    /// </remarks>
    public boolean getForcePageBreaks() { return mForcePageBreaks; }
    public void setForcePageBreaks(boolean value) { mForcePageBreaks = value; }

    /// <summary>
    /// Specifies the way headers and footers are exported to the text formats.
    /// Default value is <see cref="TxtExportHeadersFootersMode.PrimaryOnly"/>.
    /// </summary>
    public /*TxtExportHeadersFootersMode*/int getExportHeadersFootersMode() { return mExportHeadersFootersMode; }
    public void setExportHeadersFootersMode(/*TxtExportHeadersFootersMode*/int value) { mExportHeadersFootersMode = value; }

    private Encoding mEncoding = Encoding.getUTF8();
    private String mParagraphBreak = ControlChar.CR_LF;

    private boolean mForcePageBreaks;
    private /*TxtExportHeadersFootersMode*/int mExportHeadersFootersMode = TxtExportHeadersFootersMode.PRIMARY_ONLY;
}

