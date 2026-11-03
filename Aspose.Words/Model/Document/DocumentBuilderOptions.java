// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2024 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify additional options for the document building process.
/// </summary>
public class DocumentBuilderOptions
{
    private boolean mContextTableFormatting = true;

    /// <summary>
    /// True if the formatting applied to table content does not affect the formatting of the content that follows it.
    /// Default value is <c>true</c>.
    /// </summary>
    public boolean getContextTableFormatting() { return mContextTableFormatting; }
    public void setContextTableFormatting(boolean value) { mContextTableFormatting = value; }

    /// <summary>
    /// Corresponds to Design Mode in Microsoft Word.
    /// </summary>
    public boolean getDesignMode() { return mDesignMode; }; public void setDesignMode(boolean value) { mDesignMode = value; };

    private boolean mDesignMode;
}

