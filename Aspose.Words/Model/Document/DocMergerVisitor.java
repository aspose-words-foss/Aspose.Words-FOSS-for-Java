// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/10/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The visitor for <see cref="DocumentMerger"/> that collects some helper information about the document.
/// </summary>
class DocMergerVisitor extends DocumentVisitor
{
    /// <summary>
    /// Initializes a new instance of <see cref="DocMergerVisitor"/> class.
    /// </summary>
    /// <param name="stopSection">The section after which we stop the visitor.
    /// This is need as we are going to collect data when the source document already has been merged
    /// with the destination one and we need to distinguish the documents in their original state.
    /// </param>
    DocMergerVisitor(Section stopSection)
    {
        mStopSection = stopSection;
    }

    /// <summary>
    /// Called when enumeration of a section has ended.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitSectionEnd(Section section) throws Exception
    {
        if (section == mStopSection)
            return VisitorAction.STOP;

        return super.visitSectionEnd(section);
    }

    /// <summary>
    /// Called when enumeration of a footnote or endnote text has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote) throws Exception
    {
        if (footnote.getFootnoteType() == FootnoteType.FOOTNOTE)
            hasFootnotes(true);

        return super.visitFootnoteStart(footnote);
    }

    /// <summary>
    /// Called when a form field is encountered in the document.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFormField(FormField formField) throws Exception
    {
        hasFormFields(true);
        return super.visitFormField(formField);
    }

    /// <summary>
    /// Gets a boolean value indicating if there are any footnotes in the visiting document.
    /// </summary>
    boolean hasFootnotes() { return mHasFootnotes; }; private void hasFootnotes(boolean value) { mHasFootnotes = value; };

    private boolean mHasFootnotes;

    /// <summary>
    /// Gets a boolean value indicating if there are any form fields in the visiting document.
    /// </summary>
    boolean hasFormFields() { return mHasFormFields; }; private void hasFormFields(boolean value) { mHasFormFields = value; };

    private boolean mHasFormFields;

    /// <summary>
    /// Specifies the last section after which we stop the visitor work.
    /// </summary>
    private /*final*/ Section mStopSection;
}

