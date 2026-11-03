// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2017 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify options for document cleaning.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/clean-up-a-document/">Clean Up a Document</a> documentation article.</para>
/// </summary>
public class CleanupOptions
{
    /// <summary>
    /// Specifies whether unused styles should be removed from document.
    /// Default value is <c>true</c>.
    /// </summary>
    public boolean getUnusedStyles() { return mUnusedStyles; }
    public void setUnusedStyles(boolean value) { mUnusedStyles = value; }

    /// <summary>
    /// Specifies whether unused list and list definitions should be removed from document.
    /// Default value is <c>true</c>.
    /// </summary>
    public boolean getUnusedLists() { return mUnusedLists; }
    public void setUnusedLists(boolean value) { mUnusedLists = value; }

    /// <summary>
    /// Gets/sets a flag indicating whether duplicate styles should be removed from document.
    /// Default value is <c>false</c>.
    /// </summary>
    public boolean getDuplicateStyle() { return mDuplicateStyle; }
    public void setDuplicateStyle(boolean value) { mDuplicateStyle = value; }

    /// <summary>
    /// Specifies that unused <see cref="Style.BuiltIn"/> styles should be removed from document.
    /// </summary>
    public boolean getUnusedBuiltinStyles() { return mUnusedBuiltinStyles; }
    public void setUnusedBuiltinStyles(boolean value) { mUnusedBuiltinStyles = value; }

    private boolean mUnusedLists = true;
    private boolean mUnusedStyles = true;
    private boolean mDuplicateStyle = false;
    private boolean mUnusedBuiltinStyles = false;
}

