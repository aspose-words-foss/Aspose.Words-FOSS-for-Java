// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;


/// <summary>
/// The class responsible for writing Footnotes into markdown.
/// </summary>
class MarkdownFootnoteWriter
{
    /// <summary>
    /// Processes the start of the specified footnote.
    /// </summary>
    void onFootnoteStart(Footnote footnote)
    {
        // IN. I think footnote definitions most frequently contain one single paragraph, so init with capacity 1.
        mCurFootnoteParagraphs = new ArrayList<MarkdownParagraphWriter>(1);
        msArrayList.add(mFootnotes, mCurFootnoteParagraphs);

        setFootnote(footnote);
    }

    /// <summary>
    /// Processes the end of the specified footnote.
    /// </summary>
    void onFootnoteEnd()
    {
        setFootnote(null);
    }

    /// <summary>
    /// Adds a specified paragraph to the list of paragraphs pending for writing.
    /// </summary>
    /// <remarks>
    /// Footnote definitions must be written at the very end of the document,
    /// so we just collect them till the document processing is finished.
    /// </remarks>
    void addParagraph(MarkdownParagraphWriter paragraphWriter)
    {
        msArrayList.add(mCurFootnoteParagraphs, paragraphWriter);
    }

    /// <summary>
    /// Writes collected paragraphs of definitions to a specified writer.
    /// </summary>
    void writeDefinitions(MarkdownWriter writer) throws Exception
    {
        for (int i = 0; i < mFootnotes.size(); i++)
        {
            msStringBuilder.append(writer.getBuilder(), writer.getSaveOptions().getParagraphBreak());

            // Append reference mark.
            msStringBuilder.append(writer.getBuilder(), msString.format("{0}: ", getReference(i + 1)));

            ArrayList<MarkdownParagraphWriter> paragraphWriters = mFootnotes.get(i);
            for (int j = 0; j < paragraphWriters.size(); j++)
            {
                paragraphWriters.get(j).write();

                // Write paragraph break for all paragraphs except of the very last.
                if (j < (paragraphWriters.size() - 1))
                    msStringBuilder.append(writer.getBuilder(), writer.getSaveOptions().getParagraphBreak());
            }
        }
    }

    /// <summary>
    /// Returns string representing a reference mark for a specified reference index.
    /// </summary>
    private static String getReference(int refIndex)
    {
        return msString.format("{0}{1}{2}",
            FootnoteReferenceBlock.OPENING_DELIMITER, refIndex, FootnoteReferenceBlock.CLOSING_DELIMITER);
    }

    /// <summary>
    /// The currently processing footnote.
    /// </summary>
    Footnote getFootnote() { return mFootnote; }; private void setFootnote(Footnote value) { mFootnote = value; };

    private Footnote mFootnote;

    /// <summary>
    /// Gets a boolean value indicating either any Footnote is currently processing.
    /// </summary>
    boolean isInFootnote() { return (getFootnote() != null); }

    /// <summary>
    /// Gets a Footnote reference mark in brackets for the currently processing footnote.
    /// </summary>
    String getReference() { return getReference(mFootnotes.size()); }

    /// <summary>
    /// The collection of paragraph writers of the current footnote.
    /// </summary>
    private ArrayList<MarkdownParagraphWriter> mCurFootnoteParagraphs;

    /// <summary>
    /// The collection of paragraph writers of all footnotes.
    /// </summary>
    private /*final*/ ArrayList<ArrayList<MarkdownParagraphWriter>> mFootnotes = new ArrayList<ArrayList<MarkdownParagraphWriter>>();
}

