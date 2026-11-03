// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2018 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Allows to specify additional options when loading <see cref="LoadFormat.Rtf"/> document into a <see cref="Document"/> object.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-load-options/">Specify Load Options</a> documentation article.</para>
/// </summary>
public class RtfLoadOptions extends LoadOptions
{
    /// <summary>
    /// Initializes a new instance of this class with default values.
    /// </summary>
    public RtfLoadOptions()
    {
    }

    /// <summary>
    /// Initializes a new instance of this class with <see cref="LoadOptions"/> instance.
    /// </summary>
    RtfLoadOptions(LoadOptions loadOptions)
    {
    	super(loadOptions);
	
    }

    /*override*/ LoadOptions deepClone()
    {
        return new RtfLoadOptions(this);
    }

    /// <summary>
    /// <para> When set to <c>true</c>, <dev><see cref="CharsetDetector"/></dev> will try to detect UTF8 characters, 
    /// they will be preserved during import.
    /// </para>
    /// </summary>
    /// <remarks>
    /// <para>Default value is <c>false</c>.</para>
    /// </remarks>
    public boolean getRecognizeUtf8Text() { return mRecognizeUtf8Text; }
    public void setRecognizeUtf8Text(boolean value) { mRecognizeUtf8Text = value; }

    private boolean mRecognizeUtf8Text;
}

