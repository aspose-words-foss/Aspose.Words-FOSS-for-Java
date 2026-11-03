// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.StackExtension;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.Collections.msStack;
import java.util.ArrayList;


/// <summary>
/// The class responsible for saving document or a fragment of a document in markdown format.
/// </summary>
class MarkdownWriter extends TxtWriterBase
{
    /// <summary>
    /// Initializes a new instance of <see cref="MarkdownWriter"/> class.
    /// </summary>
    MarkdownWriter()
    {
        RawHtmlSaveOptions.setOfficeMathOutputMode(HtmlOfficeMathOutputMode.MATH_ML);
    }

    /// <summary>
    /// Called when enumeration of a header or footer in a section has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitHeaderFooterStart(HeaderFooter headerFooter)
    {
        mPrevParagraphWriter = null;
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a table has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitTableStart(Table table) throws Exception
    {
        if (isInTable())
        {
            setTableWriter(getTableWriter().startNestedTable(table));
        }
        else
        {
            setTableWriter(new MarkdownTableWriter(table, this));
            if (getTableWriter().getHtmlSyntaxRequired())
                mHtmlSyntaxConsumers++;

            getTableWriter().onTableStart();
        }

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a Office Math object has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath)
    {
        switch (getSaveOptions().getOfficeMathExportMode())
        {
            // FOSS MathML export routed OfficeMath through the removed HTML writer; degrade
            // gracefully by skipping (emit nothing), consistent with the Latex/MarkItDown modes.
            case MarkdownOfficeMathExportMode.MATH_ML:

            // WORDSNET-28492 Added export OfficeMath to Latex.
            case MarkdownOfficeMathExportMode.LATEX:
            // WORDSNET-28766 Added export OfficeMath to MarkItDown-compatible Latex.
            case MarkdownOfficeMathExportMode.MARK_IT_DOWN:
            {
                // FOSS
                return VisitorAction.SKIP_THIS_NODE;
            }

            default:
                return VisitorAction.CONTINUE;
        }
    }

    /// <summary>
    /// Called when enumeration of a table has ended.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitTableEnd(Table table) throws Exception
    {
        getTableWriter().onTableEnd();

        if (getTableWriter().getHtmlSyntaxRequired())
            mHtmlSyntaxConsumers--;

        mTableWriters.pop();

        if (isInTable())
            getTableWriter().onNestedTableEnd();

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a table row has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitRowStart(Row row)
    {
        getTableWriter().onRowStart(row);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a table cell has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell)
    {
        getTableWriter().onCellStart(cell);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a table row has ended.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitRowEnd(Row row)
    {
        getTableWriter().onRowEnd(row);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a table cell has ended.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitCellEnd(Cell cell) throws Exception
    {
        getTableWriter().onCellEnd(cell);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a paragraph has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph) throws Exception
    {
        setParagraphWriter(new MarkdownParagraphWriter(paragraph, this));

        if (getParagraphWriter().getHtmlSyntaxRequired())
            mHtmlSyntaxConsumers++;

        // WORDSNET-24544 A new option is introduced. When it is enabled, we should write
        // list labels as raw text.
        if (getSaveOptions().getListExportMode() == MarkdownListExportMode.PLAIN_TEXT)
        {
            // To process possible emphasizes in list item, lets add dummy run and process it in a usual way.
            // Then we remove it in VisitParagraphEnd().
            Run listLabelRun = getListLabelAsRun(paragraph);
            if (listLabelRun != null)
                paragraph.prependChild(listLabelRun);
        }

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a paragraph has ended.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitParagraphEnd(Paragraph paragraph) throws Exception
    {
        // Flush all opened fields.
        for (MarkdownFieldWriter fieldWriter : mFieldWriters)
            getParagraphWriter().appendText(fieldWriter.flushText(), false);

        // Write closing emphases for a very last run.
        getParagraphWriter().onRun(null);

        // WORDSNET-24544 Remove dummy run we created in VisitParagraphStart().
        if ((getSaveOptions().getListExportMode() == MarkdownListExportMode.PLAIN_TEXT) && hasNonEmptyListLabel(paragraph))
            paragraph.removeChild(paragraph.getFirstRun());

        if (isInTable())
        {
            getTableWriter().addParagraph(getParagraphWriter());
        }
        else if (isInFootnote())
        {
            getFootnoteWriter().addParagraph(getParagraphWriter());
        }
        else
        {
            getParagraphWriter().write();
        }

        if (getParagraphWriter().getHtmlSyntaxRequired())
            mHtmlSyntaxConsumers--;

        setParagraphWriter(null);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when a run is encountered in the document.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitRun(Run run) throws Exception
    {
        // WORDSNET-24585 Don't write hidden runs.
        if (InlineHelper.fetchAttr(run, FontAttr.HIDDEN) == AttrBoolEx.TRUE)
            return VisitorAction.CONTINUE;

        // WORDSNET-25772 Don't write runs that are child for TextBox.
        if (isInTextBox(run))
            return VisitorAction.CONTINUE;

        // Writes all the open bookmarks before the upcoming Run.
        flushBookmarks();

        if (isInField())
            getFieldWriter().onRun(run);
        else
            getParagraphWriter().onRun(run);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when a start of a bookmark is encountered in the document.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
    {
        if (!"_GoBack".equals(bookmarkStart.getName()))
            // ToLowerInvariant() is a workaround for html import of mixed case links.
            // See TestExportMixedCaseLinks() for more details.
            msArrayList.add(mBookmarks, msString.toLowerInvariant(bookmarkStart.getName()));

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when a field starts in the document.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        setFieldWriter(MarkdownFieldWriter.create(fieldStart, getLinkDefinitionWriter(), this));
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when a field separator is encountered in the document.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator) throws Exception
    {
        getFieldWriter().onFieldSeparator(fieldSeparator);
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when a field ends in the document.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        String text = getFieldWriter().onFieldEnd(fieldEnd);
        mFieldWriters.pop();

        // If the field is nested, then append its text to a parent Field.
        if (isInField())
            getFieldWriter().appendToFieldResult(text);
        else
            appendText(text, false);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a shape has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape) throws Exception
    {
        if ((shape.getHorizontalRule() != null) && shape.getHorizontalRule().getOn())
            appendText(HORIZONTAL_RULE);
        else if (getImageWriter().onShape(shape))
            // WORDSNET-24969 We don't need to escape characters (* and _) for ImageWriter, as there is either file name, or base64 image data.
            appendText(getImageWriter().getText(), false);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a group shape has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitGroupShapeStart(GroupShape group) throws Exception
    {
        if (getImageWriter().onShape(group))
            appendText(getImageWriter().getText());

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a footnote or endnote text has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        getFootnoteWriter().onFootnoteStart(footnote);

        // Append emphases to the parent paragraph of the footnote.
        getParagraphWriter().appendEmphases(footnote);

        // Append footnote reference mark.
        getParagraphWriter().appendText(getFootnoteWriter().getReference(), false);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a footnote or endnote text has ended.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitFootnoteEnd(Footnote footnote)
    {
        getFootnoteWriter().onFootnoteEnd();
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of the document has finished.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitDocumentEnd(Document doc) throws Exception
    {
        getFootnoteWriter().writeDefinitions(this);

        getLinkDefinitionWriter().writeDefinitions(this);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Updates list labels of the Document being written.
    /// </summary>
    void updateListLabels() throws Exception
    {
        if (mIsListLabelsUpdated)
            return;

        ((Document)getDocument()).updateListLabels();
        mIsListLabelsUpdated = true;
    }

    /// <summary>
    /// Gets <see cref="MarkdownParagraphWriter"/> corresponded to a specified paragraph node.
    /// </summary>
    MarkdownParagraphWriter getParagraphWriterByNode(Paragraph paragraph)
    {

        if (mParagraphWriters.getCount() > 0)
            return mParagraphWriters.peek().getParagraphWriterByNode(paragraph);

        if (getPrevParagraphWriter() != null)
            return getPrevParagraphWriter().getParagraphWriterByNode(paragraph);

        return null;
    }

    /// <summary>
    /// Writes all headers and footers of a specified section.
    /// </summary>
    protected /*override*/ void writeAllHeadersFooters(Section section) throws Exception
    {
        // In markdown we need to separate a content of the HeadersFooters from a content of the Bodies
        // using a blank line to avoid undesirable lazy paragraph continuation.
        if (section.isFirstSection())
            msStringBuilder.append(getHeadersFootersBuilder(), getSaveOptions().getParagraphBreak());

        super.writeAllHeadersFooters(section);
    }

    /// <summary>
    /// Writes all the pending bookmarks.
    /// </summary>
    private void flushBookmarks()
    {
        for (String bookmarkName : mBookmarks)
        {
            // Most Markdown editors don't currently process the markup inside
            // wide-range anchor html tag. Therefore, BookmarkEnd (</a>) comes right after BookmarkStart (<a name="bmk">).
            String htmlAnchor = msString.format("<a name=\"{0}\"></a>", bookmarkName);
            appendText(htmlAnchor, false);
        }

        // Since we're writing BookmarkEnd right after BookmarkStart, there's no need to resolve
        // bookmark nesting. We just write the entire list and clear it.
        mBookmarks.clear();
    }

    /// <summary>
    /// Appends a specified text.
    /// </summary>
    private void appendText(String text)
	{
		appendText(text, true);
	}

    /// <summary>
    /// Appends a specified text.
    /// </summary>
    private void appendText(String text, boolean isNeedEscaping)
    {
        Debug.assertTrue(getParagraphWriter() != null);
        getParagraphWriter().appendText(text, isNeedEscaping);
    }

    /// <summary>
    /// Returns list label of the numbered paragraph as standalone run.
    /// </summary>
    private Run getListLabelAsRun(Paragraph paragraph) throws Exception
    {
        String label = getListLabel(paragraph);
        if (msString.isNullOrEmpty(label))
            return null;

        // Let's add by default space character at the end of the list label, if it is not there yet.
        // This improves visual looking and also avoids possible collisions when list item is emphasized.
        if (!StringUtil.isWhiteSpace(label.charAt(label.length() - 1)))
            label = msString.format("{0} ", label);

        RunPr runPr = paragraph.getListLabel().getExpandedRunPr(true, RunPrExpandFlags.DOCUMENT_DEFAULTS);
        return new Run(getDocument(), label, runPr);
    }

    /// <summary>
    /// Returns list label string of a specified paragraph.
    /// </summary>
    private String getListLabel(Paragraph paragraph) throws Exception
    {
        if (!paragraph.isListItem())
            return null;

        updateListLabels();

        return paragraph.getListLabel().getLabelString();
    }

    /// <summary>
    /// Returns true, if a specified paragraph has non-empty list label string.
    /// </summary>
    private boolean hasNonEmptyListLabel(Paragraph paragraph) throws Exception
    {
        return !msString.isNullOrEmpty(getListLabel(paragraph));
    }

    /// <summary>
    /// Returns true if the specified node is in a TextBox.
    /// </summary>
    private static boolean isInTextBox(Node node)
    {
        Shape shape = ms.as(node.getAncestor(NodeType.SHAPE), Shape.class);
        return (shape != null) && (shape.getShapeType() == ShapeType.TEXT_BOX);
    }

    /// <summary>
    /// Gets markdown save options.
    /// </summary>
    MarkdownSaveOptions getSaveOptions() { return (MarkdownSaveOptions)getSaveOptionsBase(); }

    /// <summary>
    /// The ParagraphWriter of the previously processed paragraph.
    /// </summary>
    MarkdownParagraphWriter getPrevParagraphWriter() { return mPrevParagraphWriter; }
    private void setPrevParagraphWriter(MarkdownParagraphWriter value)
    {
        if (value != null)
            mPrevParagraphWriter = value;
    }

    /// <summary>
    /// Gets MarkdownFootnoteWriter object.
    /// </summary>
    MarkdownFootnoteWriter getFootnoteWriter()
    {
        if (mFootnoteWriter == null)
            mFootnoteWriter = new MarkdownFootnoteWriter();
        return mFootnoteWriter;
    }

    /// <summary>
    /// Gets MarkdownFieldWriter object for most inner started field.
    /// </summary>
    MarkdownFieldWriter getFieldWriter() { return (isInField()) ? mFieldWriters.peek() : null; }
    void setFieldWriter(MarkdownFieldWriter value) { mFieldWriters.push(value); }

    /// <summary>
    /// Returns true, if the current writer is in Html syntax mode.
    /// </summary>
    boolean getUseHtmlSyntax() { return mHtmlSyntaxConsumers > 0; }

    /// <summary>
    /// Gets a boolean value indicating either any table is started.
    /// </summary>
    private boolean isInTable() { return (mTableWriters.getCount() > 0); }

    /// <summary>
    /// Gets a boolean value indicating either any field is started.
    /// </summary>
    private boolean isInField() { return (mFieldWriters.getCount() > 0); }

    /// <summary>
    /// Gets a boolean value indicating either a Footnote is started.
    /// </summary>
    private boolean isInFootnote() { return (mFootnoteWriter.isInFootnote()); }

    /// <summary>
    /// Gets MarkdownTableWriter object for most inner started table.
    /// </summary>
    private MarkdownTableWriter getTableWriter() { return (isInTable()) ? mTableWriters.peek() : null; }
    private void setTableWriter(MarkdownTableWriter value) { mTableWriters.push(value); }

    /// <summary>
    /// The writer of a currently processing paragraph.
    /// </summary>
    private MarkdownParagraphWriter getParagraphWriter() { return StackExtension.top(mParagraphWriters); }
    private void setParagraphWriter(MarkdownParagraphWriter value)
    {
        setPrevParagraphWriter(getParagraphWriter());
        if (value == null)
        {
            if (mParagraphWriters.getCount() > 0)
                mParagraphWriters.pop();
        }
        else
        {
            mParagraphWriters.push(value);
        }
    }

    /// <summary>
    /// Gets MarkdownImageWriter object.
    /// </summary>
    private MarkdownImageWriter getImageWriter() throws Exception
    {
        if (mImageWriter == null)
        {
            // WORDSNET-24219 Set FileName in automatic mode when saving into FileStream.
            String fileName = getSaveInfo().getFileName();
            if ((fileName == null) && (getSaveInfo().Stream instanceof FileStream))
                fileName = ((FileStream)(getSaveInfo().Stream)).getName();

            mImageWriter = new MarkdownImageWriter(fileName, getSaveOptions(), getLinkDefinitionWriter());
        }

        return mImageWriter;
    }

    /// <summary>
    /// Gets MarkdownLinkDefinitionWriter object.
    /// </summary>
    private MarkdownLinkDefinitionWriter getLinkDefinitionWriter()
    {
        if (mLinkDefinitionWriter == null)
            mLinkDefinitionWriter = new MarkdownLinkDefinitionWriter(this);

        return mLinkDefinitionWriter;
    }

    /// <summary>
    /// Specifies HTML save options to write nodes as raw HTML.
    /// </summary>
    /*final*/ HtmlSaveOptions RawHtmlSaveOptions = HtmlSaveOptions.createSaveFragmentOptions();

    /// <summary>
    /// The counter of objects that require Html syntax use.
    /// </summary>
    private int mHtmlSyntaxConsumers = 0;

    /// <summary>
    /// The collection of table writers.
    /// </summary>
    private /*final*/ msStack<MarkdownTableWriter> mTableWriters = new msStack<MarkdownTableWriter>();

    /// <summary>
    /// The collection of field writers.
    /// </summary>
    private /*final*/ msStack<MarkdownFieldWriter> mFieldWriters = new msStack<MarkdownFieldWriter>();

    /// <summary>
    /// The collection of paragraph writers.
    /// </summary>
    private /*final*/ msStack<MarkdownParagraphWriter> mParagraphWriters = new msStack<MarkdownParagraphWriter>();

    /// <summary>
    /// ParagraphWriter for previously processed paragraph.
    /// </summary>
    private MarkdownParagraphWriter mPrevParagraphWriter;

    /// <summary>
    /// The writer for images.
    /// </summary>
    private MarkdownImageWriter mImageWriter;

    private /*final*/ ArrayList<String> mBookmarks = new ArrayList<String>();

    /// <summary>
    /// The writer for footnotes.
    /// </summary>
    private MarkdownFootnoteWriter mFootnoteWriter;

    /// <summary>
    /// The writer for link definitions.
    /// </summary>
    private MarkdownLinkDefinitionWriter mLinkDefinitionWriter;

    /// <summary>
    /// Indicates whether list labels for the Document were updated.
    /// </summary>
    private boolean mIsListLabelsUpdated;

    private static final String HORIZONTAL_RULE = "-----";
}

