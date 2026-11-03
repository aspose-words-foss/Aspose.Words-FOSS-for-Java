// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents the footnote numbering options for a document or section.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-footnote-and-endnote/">Working with Footnote and Endnote</a> documentation article.</para>
/// </summary>
/// <seealso cref="Document.FootnoteOptions"/>
/// <seealso cref="PageSetup.FootnoteOptions"/>
public final class FootnoteOptions implements IFootnoteOptions
{
    FootnoteOptions(ISectionAttrSource parent)
    {
        Debug.assertTrue(parent != null);
        mParent = parent;
    }

    /// <summary>
    /// Specifies the footnotes position.
    /// </summary>
    public /*FootnotePosition*/int getPosition() { return (/*FootnotePosition*/Integer)fetchAttr(SectAttr.FOOTNOTE_LOCATION); }
    public void setPosition(/*FootnotePosition*/int value) { setAttr(SectAttr.FOOTNOTE_LOCATION, value); }

    /// <summary>
    /// Specifies the number format for automatically numbered footnotes.
    /// </summary>
    /// <remarks>
    /// <para>Not all number styles are applicable for this property. For the list of applicable
    /// number styles see the Insert Footnote or Endnote dialog box in Microsoft Word. If you select
    /// a number style that is not applicable, Microsoft Word will revert to a default value.</para>
    /// </remarks>
    public /*NumberStyle*/int getNumberStyle() { return (/*NumberStyle*/Integer)fetchAttr(SectAttr.FOOTNOTE_NUMBER_STYLE); }
    public void setNumberStyle(/*NumberStyle*/int value) { setAttr(SectAttr.FOOTNOTE_NUMBER_STYLE, value); }

    /// <summary>
    /// Specifies the starting number or character for the first automatically numbered footnotes.
    /// </summary>
    /// <remarks>
    /// <para>This property has effect only when <see cref="RestartRule"/> is set to
    /// <see cref="FootnoteNumberingRule.Continuous"/>.</para>
    /// </remarks>
    public int getStartNumber() { return (/*int*/Integer)fetchAttr(SectAttr.FOOTNOTE_START_NUMBER); }
    public void setStartNumber(int value) { setAttr(SectAttr.FOOTNOTE_START_NUMBER, value); }

    /// <summary>
    /// Determines when automatic numbering restarts.
    /// </summary>
    public /*FootnoteNumberingRule*/int getRestartRule() { return (/*FootnoteNumberingRule*/Integer)fetchAttr(SectAttr.FOOTNOTE_NUMBERING_RULE); }
    public void setRestartRule(/*FootnoteNumberingRule*/int value) { setAttr(SectAttr.FOOTNOTE_NUMBERING_RULE, value); }

    /// <summary>
    /// Specifies the number of columns with which the footnotes area is formatted.
    /// </summary>
    /// <remarks>
    /// If this property has the value of 0, the footnotes area is formatted with a number of columns based on
    /// the number of columns on the displayed page. The default value is 0.
    /// </remarks>
    public int getColumns() { return (/*int*/Integer)fetchAttr(SectAttr.FOOTNOTE_COLUMNS); }
    public void setColumns(int value) { setAttr(SectAttr.FOOTNOTE_COLUMNS, value); }

    /// <summary>
    /// Specifies the footnotes position.
    /// </summary>
    /// <remarks>
    /// <para>Not all values are applicable to footnotes.
    /// To ascertain which values are applicable see <see cref="FootnoteLocation"/>.</para>
    /// </remarks>
    @ReservedForInternalUse @Deprecated
    public /*FootnoteLocation*/int /*IFootnoteOptions.*/getLocation() { return (/*FootnoteLocation*/int)getPosition(); }
    @ReservedForInternalUse @Deprecated
    public void /*IFootnoteOptions.*/setLocation(/*FootnoteLocation*/int value) { setPosition((/*FootnotePosition*/int)value); }

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

