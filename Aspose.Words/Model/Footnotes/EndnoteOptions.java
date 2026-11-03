// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the endnote numbering options for a document or section.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-footnote-and-endnote/">Working with Footnote and Endnote</a> documentation article.</para>
/// </summary>
/// <seealso cref="Document.EndnoteOptions"/>
/// <seealso cref="PageSetup.EndnoteOptions"/>
public final class EndnoteOptions implements IFootnoteOptions
{
    EndnoteOptions(ISectionAttrSource parent)
    {
        Debug.assertTrue(parent != null);
        mParent = parent;
    }

    /// <summary>
    /// Specifies the endnotes position.
    /// </summary>
    public /*EndnotePosition*/int getPosition() { return (/*EndnotePosition*/Integer)fetchAttr(SectAttr.ENDNOTE_LOCATION); }
    public void setPosition(/*EndnotePosition*/int value) { setAttr(SectAttr.ENDNOTE_LOCATION, value); }

    /// <summary>
    /// Specifies the number format for automatically numbered endnotes.
    /// </summary>
    /// <remarks>
    /// <para>Not all number styles are applicable for this property. For the list of applicable
    /// number styles see the Insert Footnote or Endnote dialog box in Microsoft Word. If you select
    /// a number style that is not applicable, Microsoft Word will revert to a default value.</para>
    /// </remarks>
    public /*NumberStyle*/int getNumberStyle() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.ENDNOTE_NUMBER_STYLE); }
    public void setNumberStyle(/*NumberStyle*/int value) { setAttr(SectAttr.ENDNOTE_NUMBER_STYLE, value); }

    /// <summary>
    /// Specifies the starting number or character for the first automatically numbered endnotes.
    /// </summary>
    /// <remarks>
    /// <para>This property has effect only when <see cref="RestartRule"/> is set to
    /// <see cref="FootnoteNumberingRule.Continuous"/>.</para>
    /// </remarks>
    public int getStartNumber() { return (/*int*/Integer)fetchAttr(SectAttr.ENDNOTE_START_NUMBER); }
    public void setStartNumber(int value) { setAttr(SectAttr.ENDNOTE_START_NUMBER, value); }

    /// <summary>
    /// Determines when automatic numbering restarts.
    /// </summary>
    /// <remarks>
    /// <para>Not all values are applicable to endnotes.
    /// To ascertain which values are applicable see <see cref="FootnoteNumberingRule"/>.</para>
    /// </remarks>
    public /*FootnoteNumberingRule*/int getRestartRule() { return (/*FootnoteNumberingRule*/Integer)fetchAttr(SectAttr.ENDNOTE_NUMBERING_RULE); }
    public void setRestartRule(/*FootnoteNumberingRule*/int value) { setAttr(SectAttr.ENDNOTE_NUMBERING_RULE, value); }

    /// <summary>
    /// Specifies the endnotes position.
    /// </summary>
    /// <remarks>
    /// <para>Not all values are applicable to endnotes.
    /// To ascertain which values are applicable see <see cref="FootnoteLocation"/>.</para>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*FootnoteLocation*/int /*IFootnoteOptions.*/getLocation() { return (/*FootnoteLocation*/int)getPosition(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFootnoteOptions.*/setLocation(/*FootnoteLocation*/int value) { setPosition((/*EndnotePosition*/int)value); }

    private Object fetchAttr(int key)
    {
        return mParent.fetchSectionAttr(key);
    }

    private void setAttr(int key, Object value)
    {
        mParent.setSectionAttr(key, value);
    }

    private /*final*/ ISectionAttrSource mParent;
}

