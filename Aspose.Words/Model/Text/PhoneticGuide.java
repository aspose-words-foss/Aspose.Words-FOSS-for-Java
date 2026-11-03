// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/04/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Represents Phonetic Guide.
/// </summary>
public class PhoneticGuide
{
    /// <summary>
    /// Creates a new instance of <see cref="PhoneticGuide"/> object.
    /// </summary>
    PhoneticGuide(Run parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Gets base text of the phonetic guide.
    /// </summary>
    public String getBaseText()
    {
        Ruby ruby = getRuby();
        if (ruby == null)
            return "";

        return ruby.getBase().getText();
    }

    /// <summary>
    /// Gets ruby text of the phonetic guide.
    /// </summary>
    public String getRubyText()
    {
        Ruby ruby = getRuby();
        if (ruby == null)
            return "";

        return ruby.getTop().getText();
    }

    /// <summary>
    /// Gets Ruby object of the parent Run.
    /// </summary>
    private Ruby getRuby() { return ms.as(mParent.getRunPr().get(FontAttr.RUBY), Ruby.class); }

    private /*final*/ Run mParent;
}

