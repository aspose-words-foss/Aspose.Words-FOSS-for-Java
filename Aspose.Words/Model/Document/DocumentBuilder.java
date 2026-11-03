// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/04/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ArgumentUtil;
import java.awt.image.BufferedImage;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.BitmapPal;
import com.aspose.SystemPal;
import com.aspose.StreamUtil;
import com.aspose.ImageUtil;
import com.aspose.ImageSizeCore;
import com.aspose.ExifOrientation;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.UriUtil;
import com.aspose.BidiRun;
import com.aspose.BidiAlgorithm;
import com.aspose.Language;
import com.aspose.BidiCharacterType;
import com.aspose.UnicodeCharacterDataResolver;
import com.aspose.StackExtension;


/// <summary>
/// Provides methods to insert text, images and other content, specify font, paragraph and section formatting.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/document-builder-overview/">Document Builder Overview</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="DocumentBuilder"/> makes the process of building a <see cref="Aspose.Words.Document"/> easier.
/// <see cref="Aspose.Words.Document"/> is a composite object consisting of a tree of nodes and while inserting content
/// nodes directly into the tree is possible, it requires good understanding of the tree structure.
/// <see cref="DocumentBuilder"/> is a "facade" for the complex structure of <see cref="Aspose.Words.Document"/> and allows
/// to insert content and formatting quickly and easily.</p>
///
/// <p>Create a <see cref="DocumentBuilder"/> and associate it with a <see cref="Aspose.Words.Document"/>.</p>
///
/// <p>The <see cref="DocumentBuilder"/> has an internal cursor where the text will be inserted
/// when you call <see cref="Write"/>, <see cref="Writeln(string)"/>, <see cref="InsertBreak"/>
/// and other methods. You can navigate the <see cref="DocumentBuilder"/> cursor to a different location
/// in a document using various MoveToXXX methods.</p>
///
/// <p>Use the <see cref="Font"/> property to specify character formatting that will apply to
/// all text inserted from the current position in the document onwards.</p>
///
/// <p>Use the <see cref="ParagraphFormat"/> property to specify paragraph formatting for the current
/// and all paragraphs that will be inserted.</p>
///
/// <p>Use the <see cref="PageSetup"/> property to specify page and section properties for the current
/// section and all section that will be inserted.</p>
///
/// <p>Use the <see cref="CellFormat"/> and <see cref="RowFormat"/> properties to specify
/// formatting properties for table cells and rows. User the <see cref="InsertCell"/> and
/// <see cref="EndRow"/> methods to build a table.</p>
///
/// <p>Note that <see cref="Font"/>, <see cref="ParagraphFormat"/> and <see cref="PageSetup"/> properties are updated whenever
/// you navigate to a different place in the document to reflect formatting properties available at the new location.</p>
///
/// </remarks>
public class DocumentBuilder implements IRunAttrSource, IParaAttrSource, IRowAttrSource, ICellAttrSource
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <remarks>
    /// Creates a new <see cref="DocumentBuilder"/> object and attaches it to a new <see cref="Aspose.Words.Document"/> object.
    /// </remarks>
    public DocumentBuilder() throws Exception
    {
        setDocument(new Document());
        mDocumentBuilderOptions = new DocumentBuilderOptions();
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <remarks>
    /// Creates a new <see cref="DocumentBuilder"/> object and attaches it to a new <see cref="Aspose.Words.Document"/> object.
    /// Additional document building options can be specified.
    /// </remarks>
    public DocumentBuilder(DocumentBuilderOptions options) throws Exception
    {
        setDocument(new Document());
        mDocumentBuilderOptions = options;
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <remarks>
    /// Creates a new <see cref="DocumentBuilder"/> object, attaches to the specified <see cref="Aspose.Words.Document"/> object.
    /// The cursor is positioned at the beginning of the document.
    /// </remarks>
    /// <param name="doc">The <see cref="Aspose.Words.Document"/> object to attach to.</param>
    public DocumentBuilder(Document doc)
    {
        setDocument(doc);
        mDocumentBuilderOptions = new DocumentBuilderOptions();
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <remarks>
    /// Creates a new <see cref="DocumentBuilder"/> object, attaches to the specified <see cref="Aspose.Words.Document"/> object.
    /// The cursor is positioned at the beginning of the document.
    /// </remarks>
    /// <param name="doc">The <see cref="Aspose.Words.Document"/> object to attach to.</param>
    /// <param name="options">Additional options for the document building process.</param>
    public DocumentBuilder(Document doc, DocumentBuilderOptions options)
    {
        setDocument(doc);
        mDocumentBuilderOptions = options;
    }

    /// <summary>
    /// Moves the cursor to the beginning of the document.
    /// </summary>
    public void moveToDocumentStart()
    {
        moveTo(0, StoryType.MAIN_TEXT, 0, 0);
    }

    /// <summary>
    /// Moves the cursor to the end of the document.
    /// </summary>
    public void moveToDocumentEnd()
    {
        moveTo(-1, StoryType.MAIN_TEXT, -1, -1);

        // WORDSNET-4487 We should not move inside footnotes.
        Footnote curFootnote = (Footnote)getCurrentParagraph().getAncestor(NodeType.FOOTNOTE);
        if (curFootnote != null)
            moveTo(curFootnote.getParentParagraph());
    }

    /// <summary>
    /// Moves the cursor to the beginning of the body in a specified section.
    /// </summary>
    /// <remarks>
    /// <p>When <paramref name="sectionIndex"/> is greater than or equal to 0, it specifies an index from
    /// the beginning of the document with 0 being the first section. When <paramref name="sectionIndex"/> is less than 0,
    /// it specified an index from the end of the document with -1 being the last section.</p>
    /// <p>The cursor is moved to the first paragraph in the <see cref="Body"/> of the specified section.</p>
    /// </remarks>
    /// <param name="sectionIndex">The index of the section to move to.</param>
    public void moveToSection(int sectionIndex)
    {
        moveTo(sectionIndex, StoryType.MAIN_TEXT, 0, 0);
    }

    /// <summary>
    /// Moves the cursor to the beginning of a header or footer in the current section.
    /// </summary>
    /// <remarks>
    /// <p>After you moved the cursor into a header or footer, you can use the rest of <see cref="DocumentBuilder"/>
    /// methods to modify the contents of the header or footer.</p>
    /// <p>If you want to create headers and footers different for the first page, you need
    /// to set <see cref="Aspose.Words.PageSetup.DifferentFirstPageHeaderFooter"/>.</p>
    /// <p>If you want to create headers and footers different for even and odd pages, you need
    /// to set <see cref="Aspose.Words.PageSetup.OddAndEvenPagesHeaderFooter"/>.</p>
    /// <p>Use <see cref="MoveToSection"/> to move out of the header into the main text.</p>
    /// </remarks>
    /// <param name="headerFooterType">Specifies the header or footer to move to.</param>
    public void moveToHeaderFooter(/*HeaderFooterType*/int headerFooterType)
    {
        moveTo(getCurrentSection(), WordUtil.headerFooterTypeToStoryType(headerFooterType), 0, 0);
    }

    private void moveTo(int sectionIdx, /*StoryType*/int storyType, int paraIdx, int charIdx)
    {
        mDoc.ensureMinimum();

        //Select the section. Throw if not there.
        Section section = (Section)mDoc.getChild(NodeType.SECTION, sectionIdx, false);
        if (section == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "sectionIdx");

        moveTo(section, storyType, paraIdx, charIdx);
    }

    private void moveTo(Section section, /*StoryType*/int storyType, int paraIdx, int charIdx)
    {
        section.ensureMinimum();

        //Select the story, create header or footer on demand.
        Story story;
        if (storyType == StoryType.MAIN_TEXT)
        {
            story = section.getBody();
        }
        else
        {
            /*HeaderFooterType*/int headerFooterType = WordUtil.storyTypeToHeaderFooterType(storyType);
            story = section.getHeadersFooters().getByHeaderFooterType(headerFooterType);
            if (story == null)
                story = section.appendChild(new HeaderFooter(mDoc, headerFooterType));

            if (story.getFirstParagraph() == null)
                story.appendChild(new Paragraph(mDoc));
        }

        moveTo(story, paraIdx, charIdx);
    }

    private void moveTo(Story story, int paraIdx, int charIdx)
    {
        // Select the paragraph, throw if not there.
        // RK This has to use deep navigation because it was originally published this way
        // and also because it allows to move to paragraphs inside tables. Although it is
        // not always very easy for the user to find the paragraph index property because
        // paragraphs in shapes and textboxes also participate in this.
        Paragraph para = (Paragraph)story.getChild(NodeType.PARAGRAPH, paraIdx, true);
        if (para == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "paraIdx");

        moveTo(para, charIdx);
    }

    void moveTo(Paragraph para, int charIdx)
    {
        moveToCharIndex(para, charIdx);
    }

    /// <overloads>Moves the cursor to the specified merge field.</overloads>
    /// <summary>
    /// Moves the cursor to a position just beyond the specified merge field and removes the merge field.
    /// </summary>
    /// <remarks>
    /// <p>Note that this method deletes the merge field from the document after moving the cursor.</p>
    /// </remarks>
    /// <param name="fieldName">The case-insensitive name of the mail merge field.</param>
    /// <returns><c>true</c> if the merge field was found and the cursor was moved; <c>false</c> otherwise.</returns>
    public boolean moveToMergeField(String fieldName) throws Exception
    {
        return moveToMergeField(fieldName, true, true);
    }

    /// <summary>
    /// Moves the merge field to the specified merge field.
    /// </summary>
    /// <param name="fieldName">The case-insensitive name of the mail merge field.</param>
    /// <param name="isAfter">When <c>true</c>, moves the cursor to be after the field end.
    /// When <c>false</c>, moves the cursor to be before the field start. </param>
    /// <param name="isDeleteField">When <c>true</c>, deletes the merge field.</param>
    /// <returns><c>true</c> if the merge field was found and the cursor was moved; <c>false</c> otherwise.</returns>
    public boolean moveToMergeField(String fieldName, boolean isAfter, boolean isDeleteField) throws Exception
    {
        if (fieldName == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "fieldName");

        //This is reasonably slow.
        // WORDSNET-10934 Cursor may reference to removed node.
        FieldMergeField mergeField = getCursor().isRemoved()
            ? MergeFieldFinder.findMergeField(mDoc, fieldName)
            : MergeFieldFinder.findMergeFieldFromNode(getCursor(), fieldName);

        if (mergeField == null)
            return false;

        return moveToField(mergeField, isAfter, isDeleteField);
    }

    /// <summary>
    /// Moves the cursor to a field in the document.
    /// </summary>
    /// <param name="field">The field to move the cursor to.</param>
    /// <param name="isAfter">When <c>true</c>, moves the cursor to be after the field end.
    /// When <c>false</c>, moves the cursor to be before the field start.</param>
    public void moveToField(Field field, boolean isAfter) throws Exception
    {
        moveToField(field, isAfter, false);
    }

    private boolean moveToField(Field field, boolean isAfter, boolean isDeleteField) throws Exception
    {
        // WORDSNET-1489 Merge fields lose formatting.
        // Although the above call to MoveTo has already set font formatting, we are better off
        // by setting it to be like at the beginning of the field result.
        IFieldResultFormatProvider formatProvider = field.getFormat().getFieldResultFormatProvider();
        Inline formatSource = formatProvider.getSourceNode();

        Node newCursor;
        if (isDeleteField)
        {
            newCursor = field.remove();
            if (newCursor == null)
                return false;
        }
        else if (isAfter)
        {
            // Normally we would move to the next node after the field end, but if the field node is at
            // the end of the paragraph, have to move to the end of the paragraph (the paragraph node itself).
            newCursor = field.getEnd().getNextSibling();
            if (newCursor == null)
                newCursor = field.getEnd().getParentParagraph();
        }
        else
        {
            newCursor = field.getStart();
        }

        moveTo(newCursor);
        if (formatSource != null)
            setFont(formatSource.getRunPr(), true);

        return true;
    }

    /// <summary>
    /// Moves the cursor to a bookmark.
    /// </summary>
    /// <remarks>
    /// <p>Moves the cursor to a position just after the start of the bookmark with the
    /// specified name.</p>
    /// <p>The comparison is not case-sensitive. If the bookmark was not found, <c>false</c> is
    /// returned and the cursor is not moved.</p>
    /// <p>Inserting new text does not replace existing text of the bookmark.</p>
    /// <p>Note that some bookmarks in the document are assigned to form fields.
    /// Moving to such a bookmark and inserting text there inserts the text into the
    /// form field code. Although this will not invalidate the form field, the inserted
    /// text will not be visible because it becomes part of the field code.</p>
    /// </remarks>
    /// <param name="bookmarkName">The name of the bookmark to move the cursor to.</param>
    /// <returns><c>true</c> if the bookmark was found; <c>false</c> otherwise.</returns>
    public boolean moveToBookmark(String bookmarkName) throws Exception
    {
        return moveToBookmark(bookmarkName, true, true);
    }

    /// <summary>
    /// Moves the cursor to a bookmark with greater precision.
    /// </summary>
    /// <remarks>
    /// <p>Moves the cursor to a position before or after the bookmark start or end.</p>
    /// <p>If desired position is not at inline level, moves to the next paragraph.</p>
    /// <p>The comparison is not case-sensitive. If the bookmark was not found, <c>false</c> is
    /// returned and the cursor is not moved.</p>
    /// </remarks>
    /// <param name="bookmarkName">The name of the bookmark to move the cursor to.</param>
    /// <param name="isStart">When <c>true</c>, moves the cursor to the beginning of the bookmark.
    /// When <c>false</c>, moves the cursor to the end of the bookmark.</param>
    /// <param name="isAfter">When <c>true</c>, moves the cursor to be after the bookmark
    /// start or end position. When <c>false</c>, moves the cursor to be before the bookmark
    /// start or end position.</param>
    /// <returns><c>true</c> if the bookmark was found; <c>false</c> otherwise.</returns>
    public boolean moveToBookmark(String bookmarkName, boolean isStart, boolean isAfter) throws Exception
    {
        if (bookmarkName == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "bookmarkName");

        Node bookmarkNode;
        if (isStart)
            bookmarkNode = BookmarkFinder.findBookmarkStart(mDoc, bookmarkName);
        else
            bookmarkNode = BookmarkFinder.findBookmarkEnd(mDoc, bookmarkName);

        if (bookmarkNode == null)
            return false;

        BookmarkStart bookmarkStart = isStart
            ? (BookmarkStart)bookmarkNode
            : BookmarkFinder.findBookmarkStart(getDocument(), bookmarkName, (BookmarkEnd)bookmarkNode);

        if ((bookmarkStart != null) && bookmarkStart.isColumn())
        {
            // Position of a column bookmark is logical and does not refer to any node, so there is no difference
            // between 'before' and 'after': the 'isAfter' parameter is ignored for column bookmarks.

            BookmarkEnd bookmarkEnd = isStart
                ? BookmarkFinder.findBookmarkEnd(getDocument(), bookmarkName, bookmarkStart)
                : (BookmarkEnd)bookmarkNode;

            if (moveToColumnBookmark(bookmarkStart, bookmarkEnd, isStart))
                return true;
        }

        if (bookmarkNode.getNodeLevel() == NodeLevel.INLINE)
        {
            moveTo(bookmarkNode.getFirstNonMarkupParentNode(),
                isAfter ? bookmarkNode.getNextSibling() : bookmarkNode);
        }
        else
        {
            Paragraph paragraph = NodeUtil.findNextParagraph(bookmarkNode);
            if (paragraph == null)
                return false;
            moveTo(paragraph, paragraph.getFirstChild());
        }

        return true;
    }

    /// <summary>
    /// Moves the cursor to a column bookmark.
    /// </summary>
    /// <remarks>
    /// If <paramref name="toStart"/> is <b>true</b>, the cursor is moved to the beginning of the first cell of the
    /// bookmark area after any cross-structure annotation nodes. When it is <b>false</b>, the cursor is moved to
    /// the end of the last cell of the bookmark area.
    /// Cross-structure annotation nodes are skipped at the beginning of a cell so that written text is placed after
    /// them, since such annotation nodes should retain their position at the beginning of a cell/paragraph before
    /// text to behave correctly.
    /// </remarks>
    private boolean moveToColumnBookmark(BookmarkStart start, BookmarkEnd end, boolean toStart)
    {
        Debug.assertTrue(start.isColumn());

        Cell cell = toStart
            ? BookmarkUtil.getColumnBookmarkFirstCell(start, end)
            : BookmarkUtil.getColumnBookmarkLastCell(start, end);

        if (cell == null)
            return false;

        // If the cell contains a sub-table, move to its paragraphs, e.g. like MS Word places bookmark nodes in the
        // similar cases.
        Paragraph para = (Paragraph)cell.getChild(NodeType.PARAGRAPH, (toStart ? 0 : -1), true);
        if (para == null)
            return false;

        // Skip annotations at the beginning of the paragraph: see the comment in the method remarks.
        moveTo(para, toStart ? para.getFirstNonAnnotationChild() : null);

        return true;
    }

    /// <summary>
    /// Moves the cursor to a paragraph in the current section.
    /// </summary>
    /// <remarks>
    /// <p>The navigation is performed inside the current story of the current section.
    /// That is, if you moved the cursor to the primary header of the first section,
    /// then <paramref name="paragraphIndex"/> specified the index of the paragraph inside that header
    /// of that section.</p>
    /// <p>When <paramref name="paragraphIndex"/> is greater than or equal to 0, it specifies an index from
    /// the beginning of the section with 0 being the first paragraph. When <paramref name="paragraphIndex"/> is less than 0,
    /// it specified an index from the end of the section with -1 being the last paragraph.</p>
    /// </remarks>
    /// <param name="paragraphIndex">The index of the paragraph to move to.</param>
    /// <param name="characterIndex">The index of the character inside the paragraph.
    /// A negative value allows you to specify a position from the end of the paragraph. Use -1 to move to the end of
    /// the paragraph.</param>
    public void moveToParagraph(int paragraphIndex, int characterIndex)
    {
        moveTo(getCurrentStory(), paragraphIndex, characterIndex);
    }

    /// <summary>
    /// Moves the cursor to a structured document tag in the current section.
    /// </summary>
    /// <remarks>
    /// <p>The navigation is performed inside the current story of the current section. That is, if you moved the
    /// cursor to the primary header of the first section, then <paramref name="structuredDocumentTagIndex"/>
    /// specified the index of the structured document tag inside that header of that section.</p>
    /// <p>When <paramref name="structuredDocumentTagIndex"/> is greater than or equal to 0, it specifies an index
    /// from the beginning of the section with 0 being the first structured document tag. When
    /// <paramref name="structuredDocumentTagIndex"/> is less than 0, it specified an index from the end of the
    /// section with -1 being the last structured document tag.</p>
    /// </remarks>
    /// <param name="structuredDocumentTagIndex">The index of the structured document tag to move to.</param>
    /// <param name="characterIndex">The index of the character inside the structured document tag.
    /// A negative value allows you to specify a position from the end of the structured document tag. Use -1 to
    /// move to the end of the structured document tag. If the structured document tag is at the block level, and
    /// you want to move the cursor to the end of its last paragraph, specify -2.</param>
    public void moveToStructuredDocumentTag(int structuredDocumentTagIndex, int characterIndex)
    {
        StructuredDocumentTag sdt = (StructuredDocumentTag)getCurrentStory().getChild(NodeType.STRUCTURED_DOCUMENT_TAG,
            structuredDocumentTagIndex, true);
        if (sdt == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "structuredDocumentTagIndex");

        moveToStructuredDocumentTag(sdt, characterIndex);
    }

    /// <summary>
    /// Moves the cursor to the structured document tag.
    /// </summary>
    /// <param name="structuredDocumentTag">The structured document tag to move to.</param>
    /// <param name="characterIndex">The index of the character inside the structured document tag.
    /// A negative value allows you to specify a position from the end of the structured document tag. Use -1 to
    /// move to the end of the structured document tag. If the structured document tag is at the block level, and
    /// you want to move the cursor to the end of its last paragraph, specify -2.</param>
    public void moveToStructuredDocumentTag(StructuredDocumentTag structuredDocumentTag, int characterIndex)
    {
        if (structuredDocumentTag == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "structuredDocumentTag");

        if (characterIndex >= 0)
        {
            moveToCharIndexForward(structuredDocumentTag, characterIndex);
        }
        else
        {
            // characterIndex == -1 means 0 characters from the end of the SDT: increase such negative
            // characterIndex by one.
            characterIndex++;

            if (characterIndex == 0)
                moveToStructuredDocumentTagEnd(structuredDocumentTag);
            else
                moveToCharIndexBackward(structuredDocumentTag, characterIndex);
        }
    }

    /// <summary>
    /// Moves the cursor to a table cell in the current section.
    /// </summary>
    /// <remarks>
    /// <p>The navigation is performed inside the current story of the current section.</p>
    /// <p>For the index parameters, when index is greater than or equal to 0, it specifies an index from
    /// the beginning with 0 being the first element. When index is less than 0, it specified an index from
    /// the end with -1 being the last element.</p>
    /// </remarks>
    /// <param name="tableIndex">The index of the table to move to.</param>
    /// <param name="rowIndex">The index of the row in the table.</param>
    /// <param name="columnIndex">The index of the column in the table.</param>
    /// <param name="characterIndex">The index of the character inside the cell.
    /// A negative value allows you to specify a position from the end of the cell. Use -1 to move to the end of
    /// the cell.</param>
    public void moveToCell(int tableIndex, int rowIndex, int columnIndex, int characterIndex)
    {
        Row row = fetchRow(tableIndex, rowIndex);

        Cell cell = (Cell)row.getChild(NodeType.CELL, columnIndex, false);
        if (cell == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "columnIndex");

        cell.ensureMinimum();

        moveToCharIndex(cell, characterIndex);
    }

    /// <summary>
    /// Moves the cursor to an inline node or to the end of a paragraph.
    /// </summary>
    /// <remarks>
    /// <p>When <i>node</i> is an inline-level node, the cursor is moved to this node
    /// and further content will be inserted before that node.</p>
    /// <p>When <i>node</i> is a <see cref="Paragraph"/>, the cursor is moved to the end of the paragraph
    /// and further content will be inserted just before the paragraph break.</p>
    /// <p>When <i>node</i> is a block-level node but not a <see cref="Paragraph"/>, the cursor is moved to the end of the first paragraph into block-level node
    /// and further content will be inserted just before the paragraph break.</p>
    /// </remarks>
    /// <param name="node">The node must be a paragraph or a direct child of a paragraph.</param>
    public void moveTo(Node node)
    {
        if (node == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "node");

        if (node.getDocument() != getDocument())
            throw new IllegalArgumentException("The node belongs to a different document.");

        if (node.getParentNode() == null)
            throw new IllegalArgumentException("Parent cannot be null.");

        if ((node.getNodeType() == NodeType.PARAGRAPH) || (node.getNodeLevel() == NodeLevel.INLINE))
        {
            setCursor(node);
        }
        else if (NodeUtil.isCrossStructureAnnotation(node))
        {
            // On moving cursor to an annotation on the block level, move it to be before first child of a next
            // paragraph. It is acceptable since position of a bookmark before a paragraph logically equals to
            // a position of a bookmark as the first child of the paragraph. Also, such behaviour allows achieving
            // the same result of moving to a bookmark as in the mode with disabled block level bookmarks as with
            // the enabled ones.
            Paragraph paragraph = NodeUtil.findNextParagraph(node);
            if (paragraph == null)
                throw new IllegalStateException("There is no paragraph next to the specified node.");

            // Cursor is before first child of the paragraph.
            setCursor(paragraph.hasChildNodes() ? paragraph.getFirstChild() : paragraph);
        }
        else if (node.isComposite() && ((node.getNodeLevel() == NodeLevel.BLOCK) || (node.getNodeLevel() == NodeLevel.CELL)))
        {
            Node paragraph = ((CompositeNode)node).getChild(NodeType.PARAGRAPH, 0, true);
            if (paragraph == null)
                throw new IllegalStateException("The block level node doesn't contain paragraph.");

            setCursor(paragraph);
        }
        else
        {
            throw new IllegalStateException("The node must be a block or an inline.");
        }

        mCharOffset = 0;

        if (isAtEndOfParagraph())
        {
            attachFontToParagraph();
        }
        else
        {
            if (!attachFontToInline())
                attachFontToParagraph();
        }
    }

    /// <summary>
    /// If inline node is not null, moves to it, otherwise moves to the paragraph node.
    /// </summary>
    private void moveTo(CompositeNode compositeNode, Node inline)
    {
        if (inline != null)
            moveTo(inline);
        else
            moveTo(compositeNode);
    }

    private boolean attachFontToInline()
    {
        // Try to find a node that has character formatting properties.
        // For example, if the cursor was moved to a bookmark, then the node given to us is not a run
        // and we need to loop to next siblings until we find a run that can be a source for runPr.
        Node curNode = getCursor();

        // WORDSNET-15423 Skip shapes nodes while determining properties of the run.
        if (!(curNode instanceof Inline))
        {
            // Try to get previous Inline sibling first.
            while ((curNode != null) && !(curNode instanceof Inline))
                curNode = curNode.getPreviousSibling();

            // WORDSNET-23920 Get formatting from a parent SDT like MS Word does.
            if ((curNode == null) && (getCursor().getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
            {
                setFont(((StructuredDocumentTag)getCursor().getParentNode()).getContentsRunPr(), true);
                return true;
            }
        }

        if (curNode == null)
        {
            curNode = getCursor();
            while ((curNode != null) && !(curNode instanceof Inline))
                curNode = curNode.getNextSibling();
        }

        // If the source for the character properties was found, use it to select new font formatting.
        if (curNode != null)
        {
            Inline inlineNode = (Inline)curNode;
            // Require cloning of attrs because we don't want font of this run to be modified.
            setFont(inlineNode.getRunPr(), true);
            return true;
        }
        else
        {
            return false;
        }
    }

    private void attachFontToParagraph()
    {
        setFont(getCurrentParagraph().getParagraphBreakRunPr(), true);
    }

    /// <summary>
    /// Deletes a row from a table.
    /// </summary>
    /// <remarks>
    /// <p>If the cursor is inside the row that is being deleted, the cursor is moved
    /// out to the next row or to the next paragraph after the table.</p>
    /// <p>If you delete a row from a table that contains only one row, the whole
    /// table is deleted.</p>
    /// <p>For the index parameters, when index is greater than or equal to 0, it specifies an index from
    /// the beginning with 0 being the first element. When index is less than 0, it specified an index from
    /// the end with -1 being the last element.</p>
    /// </remarks>
    /// <param name="tableIndex">The index of the table.</param>
    /// <param name="rowIndex">The index of the row in the table.</param>
    /// <returns>The row node that was just removed.</returns>
    public Row deleteRow(int tableIndex, int rowIndex)
    {
        Row row = fetchRow(tableIndex, rowIndex);
        Table table = row.getParentTable();

        if (getCurTableBuilder() != null)
            throw new IllegalStateException("Cannot delete a table row while building a table.");

        if (getCursor().isAncestorNode(row))
        {
            //If the cursor is inside the deleted row, then need to move the cursor.
            boolean isLastRow = (row == table.getLastRow());
            if (!isLastRow)
            {
                //Move the cursor to the next row.
                moveToCell(tableIndex, rowIndex + 1, 0, 0);
            }
            else
            {
                //Move the cursor to just after the table.
                Paragraph nextPara = (Paragraph)table.getNextNonMarkupCompositeLimited();
                moveTo(nextPara, 0);
            }
        }

        //Remove the table row and also remove the table if this was the last row.
        row.remove();
        if (table.getFirstRow() == null)
            table.remove();

        return row;
    }

    /// <summary>
    /// Inserts a string into the document at the current insert position.
    /// </summary>
    /// <remarks>
    /// Current font formatting specified by the <see cref="Font"/> property is used.
    /// </remarks>
    /// <param name="text">The string to insert into the document.</param>
    public void write(String text)
    {
        writeCore(text, false);
    }

    /// <summary>
    /// Inserts a string and a paragraph break into the document.
    /// </summary>
    /// <remarks>
    /// Current font and paragraph formatting specified by the <see cref="Font"/> and <see cref="ParagraphFormat"/> properties are used.
    /// </remarks>
    /// <param name="text">The string to insert into the document.</param>
    public void writeln(String text)
    {
        writeCore(text, true);
    }

    /// <summary>
    /// Inserts a paragraph break into the document.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="InsertParagraph"/>.</p>
    /// </remarks>
    public void writeln()
    {
        insertParagraph();
    }

    /// <summary>
    /// Inserts a paragraph break into the document.
    /// </summary>
    /// <remarks>
    /// <p>Current paragraph formatting specified by the <see cref="ParagraphFormat"/> property is used.</p>
    /// <p>Breaks the current paragraph in two. After inserting the paragraph, the cursor is placed at the beginning of the new paragraph.</p>
    /// <p>An exception is thrown if it is not possible to insert a paragraph break at the current cursor position.</p>
    /// </remarks>
    /// <returns>The paragraph node that was just inserted. It is the same node as <see cref="CurrentParagraph"/>.</returns>
    public Paragraph insertParagraph()
    {
        // WORDSNET-24033 Disallow inserting if the cursor is inside an inline-level SDT.
        if ((getCursor() != null) &&
            (getCursor().getNodeLevel() == NodeLevel.INLINE) &&
            (isAtEndOfStructuredDocumentTag() || (getCursor().getParentNode() instanceof StructuredDocumentTag)))
        {
            throw new IllegalStateException("Cannot insert a node of this type at this location.");
        }

        ensureCurrentRunIsSplitByCharOffset();

        // AW DOM represents paragraph slightly different from Word.
        // InsertParagraph means insert paragraph break character for Word (at least it acts this way) and
        // paragraph break character should be marked as inserted when track revision.
        // AW inserts new paragraph node and may insert new run node.
        // These newly created node should not be marked as inserted so revision tracking should be suspended during internal
        // node manipulations.
        Paragraph lastCurrent = getCurrentParagraph();

        Paragraph newPara;
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(getDocument());
        try /*JAVA: was using*/
        {
            //New paragraph gets a copy of the current paragraph attributes.
            newPara = new Paragraph(mDoc, getParaPrCopy(), getRunPrCopy());

            if ((lastCurrent == null) && isAtEndOfStructuredDocumentTag())
            {
                // Expand SDT formatting to newly created paragraph.
                if ((getCurrentStructuredDocumentTag() != null) && (getCurrentStructuredDocumentTag().getLevel() == MarkupLevel.BLOCK))
                {
                    Paragraph lastSdtPara = ms.as(getCurrentStructuredDocumentTag().getLastChild(), Paragraph.class);

                    if (lastSdtPara != null)
                        lastSdtPara.getParaPr().expandTo(newPara.getParaPr());
                }

                // Let's allow AppendChild to generate an exception if Cursor is an SDT of non-block level.
                getCurrentStructuredDocumentTag().appendChild(newPara);
            }
            else
            {
                if (getAtLastParagraphOfLastSdt())
                {
                    // See Test25317Customer() for explanation.
                    getCurrentStructuredDocumentTag().insertNext(newPara);
                }
                else
                {
                    // Add new paragraph node after (for performance reasons) the current paragraph.
                    getCurrentParagraph().insertNext(newPara);
                }
            }

            if (isAtEndOfParagraph() || (lastCurrent == null))
            {
                // The cursor was at the end of the old paragraph and now it needs to move to the newly appended
                // empty paragraph.
                // Or the cursor was at the end of a block-level SDT, let's place it to the new paragraph.
                moveTo(newPara);
            }
            else
            {
                // The cursor was in the middle of the old paragraph and we inserted nodes before the cursor
                // and appended a new paragraph.
                // We move the nodes from the cursor to the end of the old paragraph into the new paragraph.
                // We don't need to move the cursor itself because it is just a pointer and it remains valid.
                newPara.insertAfter(getCursor(), null, newPara.getLastChild());
            }
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

        // Mark old paragraph as inserted. In other words it means that paragraph break character was inserted.
        if (getDocument().isTrackRevisionsEnabled())
            RevisionTrackingUtil.addInsertRevision((lastCurrent != null) ? lastCurrent : newPara, getDocument().getEditSession());

        return getCurrentParagraph();
    }

    /// <summary>
    /// Inserts a <see cref="StructuredDocumentTag" /> into the document.
    /// </summary>
    /// <returns>The <see cref="StructuredDocumentTag"/> node that was just inserted.</returns>
    /// <dev>
    /// AM. There are lots of tricky cases related to cursor positioning before SDT being inserted and these
    /// cases going to be breaking changes. I handled few, lets handle others on demand.
    /// </dev>
    public StructuredDocumentTag insertStructuredDocumentTag(/*SdtType*/int type)
    {
        switch (type)
        {
            case SdtType.PLAIN_TEXT:
            case SdtType.RICH_TEXT:
            case SdtType.CHECKBOX:
            case SdtType.DROP_DOWN_LIST:
            case SdtType.COMBO_BOX:
            case SdtType.PICTURE:
            case SdtType.DATE:
                return insertStructuredDocumentTagCore(type);

            // RepeatingSection cannot be inserted at cursor.
            case SdtType.REPEATING_SECTION:
            case SdtType.REPEATING_SECTION_ITEM:
                throw new IllegalStateException(
                    "Repeating section controls can only be inserted around entire paragraphs or rows.");

            // Postpone insertion of SDT types that are not present in MS Word UI.
            case SdtType.NONE:
            case SdtType.BIBLIOGRAPHY:
            case SdtType.CITATION:
            case SdtType.EQUATION:
            case SdtType.BUILDING_BLOCK_GALLERY:
            case SdtType.DOC_PART_OBJ:
            case SdtType.GROUP:
            case SdtType.ENTITY_PICKER:
            default:
                throw new UnsupportedOperationException("Structured document tag of given type cannot be inserted.");
        }
    }

    private StructuredDocumentTag insertStructuredDocumentTagCore(/*SdtType*/int type)
    {
        // Case when SDT insertion at document end while document end is inside existing SDT.
        if ((getCurrentStructuredDocumentTag() != null) && (isAtEndOfStructuredDocumentTag() == false) &&
            getCurrentParagraph().isLastChild() && (getCurrentStructuredDocumentTag().getChildNodes(NodeType.ANY, false).getCount() == 1))
        {
            // MS Word changes existing SDT markup level to inline and inserts new SDT at inline level as well.

            Paragraph para = getCurrentParagraph();
            StructuredDocumentTag sdt = getCurrentStructuredDocumentTag();
            SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(mDoc);
            try /*JAVA: was using*/
            {
                para.remove();
                sdt.getParentNode().insertBefore(para, sdt);
                sdt.remove();
                sdt.setLevel(MarkupLevel.INLINE);
                while (para.hasChildNodes())
                    sdt.appendChild(para.getFirstChild());
                para.appendChild(sdt);
            }
            finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }

            StructuredDocumentTag newSdt = new StructuredDocumentTag(mDoc, type, MarkupLevel.INLINE);
            para.appendChild(newSdt);

            moveTo(newSdt);

            return newSdt;
        }

        // Case when SDT is inserted at end of existing SDT.
        if ((getCurrentStructuredDocumentTag() != null) && (isAtEndOfParagraph() == false) && isAtEndOfStructuredDocumentTag())
        {
            // Insert block level SDT after existing SDT.

            StructuredDocumentTag newSdt = new StructuredDocumentTag(mDoc, type, MarkupLevel.BLOCK);

            Paragraph lastPara = (Paragraph)getCurrentStructuredDocumentTag().getChild(NodeType.PARAGRAPH, -1, true);
            if (lastPara != null)
                lastPara.getParaPr().expandTo(((Paragraph)newSdt.getFirstChild()).getParaPr());

            mRunPr.expandTo(newSdt.getContentsRunPr());

            // We might get mapping update here.
            insertNode(newSdt);

            return newSdt;
        }

        // Case when SDT is inserted at the end of empty paragraph.
        if (isAtEndOfParagraph() && getCurrentParagraph().isEmptyOrContainsOnlyCrossAnnotation())
        {
            // Insert block level SDT that surrounds current paragraph.

            StructuredDocumentTag newSdt = new StructuredDocumentTag(mDoc, type, MarkupLevel.BLOCK);
            newSdt.isShowingPlaceholderText(true);
            getParaPr().expandTo(((Paragraph)newSdt.getFirstChild()).getParaPr());

            getCurrentParagraph().insertPrevious(newSdt);

            // Surround current paragraph by newly created block level SDT.
            Paragraph currParagraphBackup = getCurrentParagraph();
            moveTo((Paragraph)newSdt.getFirstChild());
            currParagraphBackup.remove();

            return newSdt;
        }
        else
        {
            // Otherwise insert inline level SDT.

            StructuredDocumentTag newSdt = new StructuredDocumentTag(mDoc, type, MarkupLevel.INLINE);
            newSdt.isShowingPlaceholderText(true);

            mRunPr.expandTo(newSdt.getContentsRunPr());

            // Word copies formatting to SDT content in case of CheckBox SDT.
            if (type == SdtType.CHECKBOX)
                mRunPr.expandTo(((Run)newSdt.getFirstChild()).getRunPr());

            insertNode(newSdt);

            return newSdt;
        }
    }

    /// <summary>
    /// Inserts paragraph to current builder position in manner like Word does.
    /// </summary>
    /// <returns>Inserted paragraph.</returns>
    Paragraph insertParagraphAsWord()
    {
        ensureCurrentRunIsSplitByCharOffset();

        // Detect font formatting source.
        Node curNode = getCursor();
        Paragraph curPara = getCurrentParagraph();

        Run fontFormattingSource = isAtEndOfParagraph() ?
            curPara.getLastRun() : (Run)curNode.previousSiblingOfType(NodeType.RUN);

        if ((fontFormattingSource == null) && msObject.referenceEquals(curNode, curPara.getFirstRun()))
            fontFormattingSource = curPara.getFirstRun();

        // Prepare font formatting properties for the new paragraph.
        RunPr runPr = (fontFormattingSource != null) ?
            AttrCollectionExtension.deepClone(fontFormattingSource.getRunPr()) : AttrCollectionExtension.deepClone(curPara.getParagraphBreakRunPr());

        Paragraph newPara = new Paragraph(mDoc, getParaPrCopy(), runPr);
        curPara.insertPrevious(newPara);

        // Only new paragraph should be marked with insertion revision.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(mDoc);
        try /*JAVA: was using*/
        {
            Node lastNode = isAtEndOfParagraph() ? null : curNode;
            newPara.insertAfter(curPara.getFirstChild(), lastNode, newPara.getLastChild());
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }

        return newPara;
    }

    /// <summary>
    /// Inserts style separator into the document.
    /// </summary>
    /// <remarks>
    /// This method allows to apply different paragraph styles to two different parts of a text line.
    /// </remarks>
    public void insertStyleSeparator()
    {
        StyleSeparatorInserter.insertStyleSeparator(this);
    }

    /// <summary>
    /// Inserts a break of the specified type into the document.
    /// </summary>
    /// <remarks>
    /// Use this method to insert paragraph, page, column, section or line break into the document.
    /// </remarks>
    /// <param name="breakType">Specifies the type of the break to insert.</param>
    public void insertBreak(/*BreakType*/int breakType)
    {
        insertBreakCore(breakType, true);
    }

    /// <summary>
    /// Inserts a break of the specified type into the document.
    /// </summary>
    void insertBreakCore(/*BreakType*/int breakType, boolean throwOnError)
    {
        switch (breakType)
        {
            case BreakType.PARAGRAPH_BREAK:
                insertParagraph();
                return;
            case BreakType.PAGE_BREAK:
            {
                if (!checkCanSeriousBreak(throwOnError))
                    return;

                boolean inTheParaStart = isAtStartOfParagraph() && !isAtEndOfParagraph();

                insertRun(ControlChar.PAGE_BREAK);

                // WORDSNET-22834 Add paragraph after the page break depending from the compatibility options.
                CompatibilityOptions compatOptions = getDocument().getCompatibilityOptions();
                if (inTheParaStart && (!compatOptions.getSplitPgBreakAndParaMark() || compatOptions.isWord2013OrLaterCompatible()))
                    insertBreakCore(BreakType.PARAGRAPH_BREAK, throwOnError);

                return;
            }
            case BreakType.COLUMN_BREAK:
                if (checkCanSeriousBreak(throwOnError))
                    insertRun(ControlChar.COLUMN_BREAK);
                return;
            case BreakType.SECTION_BREAK_NEW_COLUMN:
                if (checkCanSeriousBreak(throwOnError))
                    insertSectionCore(SectionStart.NEW_COLUMN);
                return;
            case BreakType.SECTION_BREAK_NEW_PAGE:
                if (checkCanSeriousBreak(throwOnError))
                    insertSectionCore(SectionStart.NEW_PAGE);
                return;
            case BreakType.SECTION_BREAK_CONTINUOUS:
                if (checkCanSeriousBreak(throwOnError))
                    insertSectionCore(SectionStart.CONTINUOUS);
                return;
            case BreakType.SECTION_BREAK_EVEN_PAGE:
                if (checkCanSeriousBreak(throwOnError))
                    insertSectionCore(SectionStart.EVEN_PAGE);
                return;
            case BreakType.SECTION_BREAK_ODD_PAGE:
                if (checkCanSeriousBreak(throwOnError))
                    insertSectionCore(SectionStart.ODD_PAGE);
                return;
            case BreakType.LINE_BREAK:
                insertRun(ControlChar.LINE_BREAK);
                return;
            default:
                if (throwOnError)
                    throw new IllegalStateException("Unknown break type.");
                return;
        }
    }

    /// <summary>
    /// Inserts a TOC (table of contents) field into the document.
    /// </summary>
    /// <remarks>
    /// <p>This method inserts a TOC (table of contents) field into the document at
    /// the current position.</p>
    /// <p>A table of contents in a Word document can be built in a number of ways
    /// and formatted using a variety of options. The way the table is built and
    /// displayed by Microsoft Word is controlled by the field switches.</p>
    /// <p>The easiest way to specify the switches is to insert and configure a table of
    /// contents into a Word document using the Insert->Reference->Index and Tables menu,
    /// then switch display of field codes on to see the switches. You can press Alt+F9 in
    /// Microsoft Word to toggle display of field codes on or off.</p>
    /// <p>For example, after creating a table of contents, the following field is inserted
    /// into the document: <b>{ TOC &#92;o "1-3" &#92;h &#92;z <ms>&#92;u</ms> }</b>.
    /// You can copy <b>&#92;o "1-3" &#92;h &#92;z <ms>&#92;u</ms></b> and use it as the switches parameter.</p>
    /// <p>Note that <see cref="InsertTableOfContents"/> will only insert a TOC field, but
    /// will not actually build the table of contents. The table of contents is built by
    /// Microsoft Word when the field is updated.</p>
    /// <p>If you insert a table of contents using this method and then open the file
    /// in Microsoft Word, you will not see the table of contents because the TOC field
    /// has not yet been updated.</p>
    /// <p>In Microsoft Word, fields are not automatically updated when a document is opened,
    /// but you can update fields in a document at any time by pressing F9.</p>
    /// </remarks>
    /// <param name="switches">The TOC field switches.</param>
    public Field insertTableOfContents(String switches)
    {
        // RK I'm using &#92; instead of \ in the above XML comment for autoporting to Java.

        if (!StringUtil.hasChars(switches))
            throw new IllegalArgumentException("switches is required.");

        return insertField(msString.format("TOC {0}", switches), "");
    }

    /// <overloads>Inserts a Word field into a document.</overloads>
    /// <summary>
    /// Inserts a Word field into a document and optionally updates the field result.
    /// </summary>
    /// <remarks>
    /// <para>This method inserts a field into a document.
    /// Aspose.Words can update fields of most types, but not all. For more details see the
    /// <see cref="InsertField(string,string)"/> overload.</para>
    ///
    /// <seealso cref="Field"/>
    /// </remarks>
    /// <param name="fieldType">The type of the field to append.</param>
    /// <param name="updateField">Specifies whether to update the field immediately.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertField(/*FieldType*/int fieldType, boolean updateField) throws Exception
    {
        ensureCurrentRunIsSplitByCharOffset();

        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertField(fieldType, updateField, getRunPrCopy(), getCurrentParent(), getReferenceNode(),
            getReferenceNode() == null);
    }

    /// <summary>
    /// Inserts a Word field into a document and updates the field result.
    /// </summary>
    /// <remarks>
    /// <para>This method inserts a field into a document and updates the field result immediately.
    /// Aspose.Words can update fields of most types, but not all. For more details see the
    /// <see cref="InsertField(string,string)"/> overload.</para>
    ///
    /// <seealso cref="Field"/>
    /// </remarks>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertField(String fieldCode) throws Exception
    {
        ensureCurrentRunIsSplitByCharOffset();

        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertField(fieldCode, getRunPrCopy(), getCurrentParent(), getReferenceNode(),
            getReferenceNode() == null);
    }

    /// <summary>
    /// Inserts a Word field into a document without updating the field result.
    /// </summary>
    /// <remarks>
    /// <p>Fields in Microsoft Word documents consist of a field code and a field result.
    /// The field code is like a formula and the field result is like the value that
    /// the formula produces. The field code may also contain field switches
    /// that are like additional instructions to perform a specific action.</p>
    ///
    /// <p>You can switch between displaying field codes and results in your document in
    /// Microsoft Word using the keyboard shortcut Alt+F9. Field codes appear between curly braces ( { } ).</p>
    ///
    /// <p>To create a field, you need to specify a field type, field code and a "placeholder" field value.
    /// If you are not sure about a particular field code syntax, create the field in Microsoft Word first
    /// and switch to see its field code.</p>
    ///
    /// <para>Aspose.Words can calculate field results for most of the field types, but this method
    /// does not update the field result automatically. Because the field result is not calculated automatically,
    /// you are expected to pass some string value (or even an empty string) that will be inserted into the field result.
    /// This value will remain in the field result as a placeholder until the field is updated.
    /// To update the field result you can call <see cref="Field.Update()"/> on the field object returned
    /// to you or <see cref="Aspose.Words.Document.UpdateFields"/> to update fields in the whole document.</para>
    ///
    /// <seealso cref="Field"/>
    /// </remarks>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <param name="fieldValue">The field value to insert. Pass <c>null</c> for fields that do not have a value.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertField(String fieldCode, String fieldValue)
    {
        ensureCurrentRunIsSplitByCharOffset();

        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertField(fieldCode, fieldValue, getRunPrCopy(), getCurrentParent(), getReferenceNode(),
            getReferenceNode() == null);
    }

    /// <summary>
    /// Inserts a hyperlink into the document.
    /// </summary>
    /// <remarks>
    /// <p>Note that you need to specify font formatting for the hyperlink display text explicitly
    /// using the <see cref="DocumentBuilder.Font"/> property.</p>
    /// <p>This methods internally calls <see cref="InsertField(string)"/> to insert an MS Word HYPERLINK field
    /// into the document.</p>
    /// </remarks>
    /// <param name="displayText">Text of the link to be displayed in the document.</param>
    /// <param name="urlOrBookmark">Link destination. Can be a url or a name of a bookmark inside the document.
    /// This method always adds apostrophes at the beginning and end of the url.</param>
    /// <param name="isBookmark"><c>true</c> if the previous parameter is a name of a bookmark inside the document;
    /// <c>false</c> is the previous parameter is a URL.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertHyperlink(String displayText, String urlOrBookmark, boolean isBookmark)
    {
        ArgumentUtil.checkNotNull(displayText, "displayText");
        ArgumentUtil.checkNotNull(urlOrBookmark, "hrefOrBookmark");

        FieldBundle field = startHyperlink(urlOrBookmark, isBookmark, "", "");
        write(displayText);
        field.setEnd(endHyperlink());

        return field.getField();
    }

    /// <summary>
    /// Inserts a text form field at the current position.
    /// </summary>
    /// <remarks>
    /// <p>If you specify a name for the form field, then a bookmark is automatically created with the same name.</p>
    /// </remarks>
    /// <param name="name">The name of the form field. Can be an empty string.</param>
    /// <param name="type">Specifies the type of the text form field.</param>
    /// <param name="format">Format string used to format the value of the form field.</param>
    /// <param name="fieldValue">Text that will be shown in the field.</param>
    /// <param name="maxLength">Maximum length the user can enter into the form field. Set to zero for unlimited length.</param>
    /// <returns>The form field node that was just inserted.</returns>
    public FormField insertTextInput(
        String name,
        /*TextFormFieldType*/int type,
        String format,
        String fieldValue,
        int maxLength) throws Exception
    {
        if (name == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "name");
        if (format == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "format");
        if (fieldValue == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "fieldValue");
        if (maxLength < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "maxLength");

        insertFieldStart(FieldType.FIELD_FORM_TEXT_INPUT);

        if (StringUtil.hasChars(name))
            startBookmark(name);

        insertFieldCode(" FORMTEXT ");

        Node fieldSeparatorNode = insertFieldSeparator(FieldType.FIELD_FORM_TEXT_INPUT);

        // Create a field result that will be displayed in the document.
        // When there is no default there will be maximum 5 circles displayed.
        // Just mimic what MS Word is doing.
        String fieldResult = (StringUtil.hasChars(fieldValue)) ? fieldValue : FormField.DEFAULT_TEXT_INPUT_VALUE;

        Run fieldResultNode = new Run(mDoc, fieldResult, getRunPrCopy());
        insertNode(fieldResultNode);

        Node lastInsertedNode = insertFieldEnd(FieldType.FIELD_FORM_TEXT_INPUT, true);

        if (StringUtil.hasChars(name))
            lastInsertedNode = endBookmark(name);

        //Create and insert the actual form field node.
        FormField formField = insertFormFieldNode(fieldSeparatorNode);
        formField.setName(name);
        formField.setTextInputType(type);
        formField.setTextInputFormat(format);
        formField.setResult(fieldValue);
        formField.setMaxLength(maxLength);

        // WORDSNET-5287 Cursor must be right after the inserted FormField nodes.
        moveTo((Paragraph)lastInsertedNode.getFirstNonMarkupParentNode(), lastInsertedNode.getNextSibling());

        return formField;
    }

    /// <summary>
    /// Inserts a checkbox form field at the current position.
    /// </summary>
    /// <remarks>
    /// <p>If you specify a name for the form field, then a bookmark is automatically created with the same name.</p>
    /// </remarks>
    /// <param name="name">The name of the form field. Can be an empty string. The value longer than 20 characters will be truncated.</param>
    /// <param name="checkedValue">Checked status of the checkbox form field.</param>
    /// <param name="size">Specifies the size of the checkbox in points. Specify 0 for MS Word
    /// to calculate the size of the checkbox automatically.</param>
    /// <returns>The form field node that was just inserted.</returns>
    public FormField insertCheckBox(String name, boolean checkedValue, int size) throws Exception
    {
        // Default value equals to checked value at this case.
        return insertCheckBox(name, checkedValue, checkedValue, size);
    }

    /// <summary>
    /// Inserts a checkbox form field at the current position.
    /// </summary>
    /// <remarks>
    /// <p>If you specify a name for the form field, then a bookmark is automatically created with the same name.</p>
    /// </remarks>
    /// <param name="name">The name of the form field. Can be an empty string. The value longer than 20 characters will be truncated.</param>
    /// <param name="defaultValue">Default value of the checkbox form field.</param>
    /// <param name="checkedValue">Current checked status of the checkbox form field.</param>
    /// <param name="size">Specifies the size of the checkbox in points. Specify 0 for MS Word
    /// to calculate the size of the checkbox automatically.</param>
    /// <returns>The form field node that was just inserted.</returns>
    public FormField insertCheckBox(String name, boolean defaultValue, boolean checkedValue, int size) throws Exception
    {
        if (name == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "name");
        if (size < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "size");

        insertFieldStart(FieldType.FIELD_FORM_CHECK_BOX);

        if (StringUtil.hasChars(name))
            startBookmark(name);

        insertFieldCode(" FORMCHECKBOX ");

        // There is no field separator for this field.
        Node fieldEnd = insertFieldEnd(FieldType.FIELD_FORM_CHECK_BOX, false);

        if (StringUtil.hasChars(name))
            endBookmark(name);

        //Create and insert the actual form field node.
        FormField formField = insertFormFieldNode(fieldEnd);
        formField.setName(name);
        formField.setDefault(defaultValue);
        formField.setChecked(checkedValue);
        if (size != 0)
        {
            formField.isCheckBoxExactSize(true);
            formField.setCheckBoxSize(size);
        }
        else
        {
            formField.isCheckBoxExactSize(false);
            formField.setCheckBoxSize(10.0);    //Default size MS Word uses.
        }

        return formField;
    }

    /// <summary>
    /// Inserts a combobox form field at the current position.
    /// </summary>
    /// <remarks>
    /// <p>If you specify a name for the form field, then a bookmark is automatically created with the same name.</p>
    /// </remarks>
    /// <param name="name">The name of the form field. Can be an empty string. The value longer than 20 characters will be truncated.</param>
    /// <param name="items">The items of the ComboBox. Maximum is 25 items.</param>
    /// <param name="selectedIndex">The index of the selected item in the ComboBox.</param>
    /// <returns>The form field node that was just inserted.</returns>
    public FormField insertComboBox(String name, String[] items, int selectedIndex) throws Exception
    {
        if (name == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "name");
        if (items == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "items");
        if (items.length > DropDownItemCollection.MAX_ITEMS_COUNT)
            throw new IllegalArgumentException("items");
        if ((selectedIndex < 0) || (selectedIndex >= items.length))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "selectedIndex");

        insertFieldStart(FieldType.FIELD_FORM_DROP_DOWN);

        if (StringUtil.hasChars(name))
            startBookmark(name);

        insertFieldCode(" FORMDROPDOWN ");

        // There is no field separator for this field.
        Node fieldEnd = insertFieldEnd(FieldType.FIELD_FORM_DROP_DOWN, false);

        if (StringUtil.hasChars(name))
            endBookmark(name);

        //Create and insert the actual form field node.
        FormField formField = insertFormFieldNode(fieldEnd);
        formField.setName(name);
        formField.setDropDownSelectedIndex(selectedIndex);
        for (int i = 0; i < items.length; i++)
            formField.getDropDownItems().add(items[i]);

        return formField;
    }

    /// <summary>
    /// Inserts a footnote or endnote into the document.
    /// </summary>
    /// <param name="footnoteType">Specifies whether to insert a footnote or an endnote.</param>
    /// <param name="footnoteText">Specifies the text of the footnote.</param>
    /// <returns>Returns a footnote object that was just created.</returns>
    public Footnote insertFootnote(/*FootnoteType*/int footnoteType, String footnoteText)
    {
        return insertFootnote(footnoteType, footnoteText, null);
    }

    /// <summary>
    /// Inserts a footnote or endnote into the document.
    /// </summary>
    /// <param name="footnoteType">Specifies whether to insert a footnote or an endnote.</param>
    /// <param name="footnoteText">Specifies the text of the footnote.</param>
    /// <param name="referenceMark">Specifies the custom reference mark of the footnote.</param>
    /// <returns>Returns a footnote object that was just created.</returns>
    public Footnote insertFootnote(/*FootnoteType*/int footnoteType, String footnoteText, String referenceMark)
    {
        // Create the footnote.
        Footnote footnote = new Footnote(mDoc, footnoteType, !StringUtil.hasChars(referenceMark), referenceMark, getRunPrCopy());
        Style referenceStyle = getDocument().getStyles().fetchBySti(FootnoteUtil.getFootnoteReferenceStyleIdentifier(footnoteType));
        footnote.getRunPr().set(FontAttr.ISTD, referenceStyle.getIstd());
        insertNode(footnote);

        // Create a paragraph that will contain the footnote text.
        Paragraph footnotePara = new Paragraph(mDoc);
        Style paraStyle = getDocument().getStyles().fetchBySti(FootnoteUtil.getFootnoteTextStyleIdentifier(footnoteType));
        footnotePara.getParaPr().set(ParaAttr.ISTD, paraStyle.getIstd());

        footnote.getParagraphs().add(footnotePara);

        Inline footnoteNumber = (footnote.isAuto())
            ? (Inline)new SpecialChar(mDoc, ControlChar.FOOTNOTE_REF_CHAR, new RunPr())
            : new Run(mDoc, footnote.getReferenceMark(), new RunPr());
        footnoteNumber.getRunPr().set(FontAttr.ISTD, referenceStyle.getIstd());
        footnotePara.appendChild(footnoteNumber);

        if (StringUtil.hasChars(footnoteText))
        {
            Node saveCursor = getCursor();
            moveTo(footnotePara);
            write(" ");
            write(footnoteText);
            setCursor(saveCursor);
        }

        return footnote;
    }

    /// <overloads>Inserts an image into the document.</overloads>
    /// <summary>
    /// Inserts an image from a <ms>.NET <see cref="Image"/></ms><java><see cref="Image"/></java><cpp><see cref="Image"/></cpp>
    /// object into the document. The image is inserted inline and at 100% scale.
    /// </summary>
    /// <param name="image">The image to insert into the document.</param>
    /// <returns>The image node that was just inserted.</returns>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// <java>
    /// <p>Aspose.Words will insert the image in the PNG format and with default settings.If you want to insert a<tt>BufferedImage</tt> in
    /// another format or with other settings, you need to save the image into a byte array and use <see cref = "InsertImage(byte[])"/>.</p>
    /// </java >
    /// </remarks>
    public Shape insertImage(BufferedImage image) throws Exception
    {
        return insertImage(image, -1, -1);
    }

    /// <summary>
    /// Inserts an image from a file or URL into the document. The image is inserted inline and at 100% scale.
    /// </summary>
    /// <param name="fileName">The file with the image. Can be any valid local or remote URI.</param>
    /// <returns>The image node that was just inserted.</returns>
    /// <remarks>
    /// <p>This overload will automatically download the image before inserting into the document
    /// if you specify a remote URI.</p>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertImage(String fileName) throws Exception
    {
        return insertImage(fileName, -1, -1);
    }

    /// <summary>
    /// Inserts an image from a stream into the document. The image is inserted inline and at 100% scale.
    /// </summary>
    /// <param name="stream">The stream that contains the image.
    /// <java> The stream will be read from the current position, so one should be careful about stream position.</java>
    /// </param>
    /// <returns>The image node that was just inserted.</returns>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    /// <javaName>com.aspose.words.Shape insertImage(java.io.InputStream stream)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    // WORDSJAVA-25686 - Loading from InputStream always load into memory first
    //JAVA: method is internalized
	Shape insertImageInternal( Stream stream) throws Exception
    {
        return insertImageInternal(stream, -1, -1);
    }

	//JAVA-added public wrapper for internalized method
	public Shape insertImage( java.io.InputStream stream) throws Exception
	{
		return insertImageInternal(Stream.fromJavaInputStream(stream));
	}

    /// <summary>
    /// Inserts an image from a byte array into the document. The image is inserted inline and at 100% scale.
    /// </summary>
    /// <param name="imageBytes">The byte array that contains the image.</param>
    /// <returns>The image node that was just inserted.</returns>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertImage(byte[] imageBytes) throws Exception
    {
        return insertImage(imageBytes, -1, -1);
    }

    /// <summary>
    /// Inserts an inline image from a <ms>.NET <see cref="Image"/></ms><java><see cref="Image"/></java><cpp><see cref="Image"/></cpp>
    /// object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="image">The image to insert into the document.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// <java>
    /// <p>Aspose.Words will insert the image in the PNG format and with default settings.If you want to insert a<tt>BufferedImage</tt> in
    /// another format or with other settings, you need to save the image into a byte array and use <see cref = "InsertImage(byte[])"/>.</p>
    /// </java >
    /// </remarks>
    public Shape insertImage(BufferedImage image, double width, double height) throws Exception
    {
        return insertImage(image,
            RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT, 0.0,
            width, height, WrapType.INLINE);
    }

    /// <summary>
    /// Inserts an inline image from a file or URL into the document and scales it to the specified size.
    /// </summary>
    /// <param name="fileName">The file that contains the image.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertImage(String fileName, double width, double height) throws Exception
    {
        return insertImage(fileName,
            RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT, 0.0,
            width, height, WrapType.INLINE);
    }

    /// <summary>
    /// Inserts an inline image from a stream into the document and scales it to the specified size.
    /// </summary>
    /// <param name="stream">The stream that contains the image.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    /// <javaName>com.aspose.words.Shape insertImage(java.io.InputStream stream,double width,double height)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    // WORDSJAVA-25686 - Loading from InputStream always load into memory first
    //JAVA: method is internalized
	Shape insertImageInternal( Stream stream, double width, double height) throws Exception
    {
        return insertImageInternal(stream,
            RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT, 0.0,
            width, height, WrapType.INLINE);
    }

	//JAVA-added public wrapper for internalized method
	public Shape insertImage( java.io.InputStream stream, double width, double height) throws Exception
	{
		return insertImageInternal(Stream.fromJavaInputStream(stream), width, height);
	}

    /// <summary>
    /// Inserts an inline image from a byte array into the document and scales it to the specified size.
    /// </summary>
    /// <param name="imageBytes">The byte array that contains the image.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertImage(byte[] imageBytes, double width, double height) throws Exception
    {
        return insertImage(imageBytes,
            RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT, 0.0,
            width, height, WrapType.INLINE);
    }

    /// <summary>
    /// Inserts an image from a <ms>.NET <see cref="Image"/></ms><java><see cref="Image"/></java><cpp><see cref="Image"/></cpp>
    /// object at the specified position and size.
    /// </summary>
    /// <param name="image">The image to insert into the document.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// <java>
    /// <p>Aspose.Words will insert the image in the PNG format and with default settings.If you want to insert a<tt>BufferedImage</tt> in
    /// another format or with other settings, you need to save the image into a byte array and use <see cref = "InsertImage(byte[])"/>.</p>
    /// </java >
    /// </remarks>
    public Shape insertImage(
        BufferedImage image,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType) throws Exception
    {
        if (image == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "image");

        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            BitmapPal.saveNativeImageForWord97(image, stream);
            return insertImageInternal(stream, horzPos, left, vertPos, top, width, height, wrapType);
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Inserts an image from a file or URL at the specified position and size.
    /// </summary>
    /// <param name="fileName">The file that contains the image.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertImage(
        String fileName,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType) throws Exception
    {
        ArgumentUtil.checkHasChars(fileName, "fileName");

        // andrnosk: WORDSNET-927 Allow user to handle loading external resources.
        if (getDocument().getResourceLoadingCallback() != null)
        {
            ResourceLoadingArgs args = new ResourceLoadingArgs("", fileName, ResourceType.IMAGE);
            switch (getDocument().getResourceLoadingCallback().resourceLoading(args))
            {
                case ResourceLoadingAction.DEFAULT:
                    break;
                case ResourceLoadingAction.SKIP:
                    return null;
                case ResourceLoadingAction.USER_PROVIDED:
                    return insertImage(args.getData(), horzPos, left, vertPos, top, width, height, wrapType);
                default:
                    Debug.fail("Never invoked!");
                    return null;
            }
        }

        Stream stream = SystemPal.openStreamFromHref(fileName);
        try /*JAVA: was using*/
    	{
            return insertImageInternal(stream, horzPos, left, vertPos, top, width, height, wrapType);
    	}
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Inserts an image from a stream at the specified position and size.
    /// </summary>
    /// <param name="stream">The stream that contains the image.</param>
    /// <ms cpp='true'><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/></ms>
    /// <java><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageJavaParamsEx"]/*'/></java>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    /// <javaName>com.aspose.words.Shape insertImage(java.io.InputStream stream,int horzPos,double left,int vertPos,double top,double width,double height,int wrapType)</javaName>
    // JAVA: the first public api change map will be used: Stream -> java.io.InputStream
    // WORDSJAVA-25686 - Loading from InputStream always load into memory first
    //JAVA: method is internalized
	Shape insertImageInternal(
         Stream stream,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType) throws Exception
    {
        if (stream == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "stream");

        byte[] imageBytes = StreamUtil.copyStreamToByteArray(stream);
        return insertImage(imageBytes, horzPos, left, vertPos, top, width, height, wrapType);
    }

	//JAVA-added public wrapper for internalized method
	public Shape insertImage(
         java.io.InputStream stream,/*RelativeHorizontalPosition*/int horzPos,
        double left,/*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,/*WrapType*/int wrapType) throws Exception
	{
		return insertImageInternal(Stream.fromJavaInputStream(stream), horzPos, left, vertPos, top, width, height, wrapType);
	}

    /// <summary>
    /// Inserts an image from a byte array at the specified position and size.
    /// </summary>
    /// <param name="imageBytes">The byte array that contains the image.</param>
    /// <ms cpp='true'><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/></ms>
    /// <java><include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageJavaParamsEx"]/*'/></java>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertImage(
        byte[] imageBytes,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType) throws Exception
    {
        return insertImage(imageBytes, horzPos, left, vertPos, top, width, height, wrapType, true);
    }

    /// <summary>
    /// Inserts an image from a byte array at the specified position and size.
    /// </summary>
    Shape insertImage(
        byte[] imageBytes,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType,
        boolean considerExifOrientation) throws Exception
    {
        if (imageBytes == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "imageBytes");

        boolean isGif = false;
        if (ImageUtil.isGif(imageBytes))
        {
            // Passed GIF image must be valid. So, check that the bitmap may be created.
            BitmapPal bitmapPalResource = new BitmapPal(imageBytes);
            try /*JAVA: was using*/
            {
                isGif = true;
            }
            finally { if (bitmapPalResource != null) bitmapPalResource.dispose(); }
        }

        SizeD imageSize = null; // Size calculated based on the image data.

        boolean isWord2007OrHigher = (mDoc.getCompatibilityOptions().getMswVersion() > MsWordVersionCore.WORD_2007) ||
            (mDoc.getCompatibilityOptions().getMswVersion() == MsWordVersionCore.UNSPECIFIED);

        Shape shape;

        // If MS Word version is higher then Word2007 or not specified
        // insert image as DML shape, in other cases as VML shape.
        if (isWord2007OrHigher)
        {
            shape = new Shape(mDoc, ShapeMarkupLanguage.DML);

            DmlPicture picture = new DmlPicture();
            picture.setGeometry(DmlGeometryReader.getPresetGeometry("rect"));
            picture.setBlipFill(new DmlBlipFill());
            picture.getBlipFill().getBlip().setDocument(mDoc);

            // Dml shape requires NvDrawingProperties.Id.
            DmlNvPrPicture nvPrPicture = new DmlNvPrPicture();

            // WORDSNET-18867 Use unique value for "cNvPr" identifier which differ from identifier of the shape.
            nvPrPicture.setNvDrawingProperties(new DmlNvDrawingProperties(mDoc.getNextShapeId(), ""));
            nvPrPicture.setCNvProperties(new DmlCnvPrPicture());
            picture.setNonVisualPr(nvPrPicture);
            shape.setShapeType(ShapeType.IMAGE);
            shape.setDmlNode(picture);
        }
        else
        {
            shape = new Shape(mDoc, ShapeType.IMAGE);
        }

        // Check whether the image is SVG. In this case it can be stored in DOCX document as svgBlip extension.
        if (ImageUtil.isSvg(imageBytes))
            throw new UnsupportedOperationException("FOSS");

        // WORDSNET-1786 Allow inserting GIF images into the document.
        // For VML shapes still previous logic is used. I.e. GIF bytes will be converted to PNG.
        // A customer MUST NOT access ImageBytes for DML shape with GIF bytes. It causes conversion to PNG.
        if (isWord2007OrHigher && isGif)
        {
            ((DmlPicture)shape.getDmlNode()).getBlipFill().getBlip().setEmbedImage(imageBytes);

            // Set image size to avoid bytes conversion while updating shape size.
            ImageSizeCore gifSize = ImageUtil.getGifSize(imageBytes);
            imageSize = new SizeD(gifSize.getWidthPoints(), gifSize.getHeightPoints());
        }
        else
        {
            shape.getImageData().setImageBytes(imageBytes);
        }

        shape.setRunPr(getRunPrCopy());
        shape.setRelativeHorizontalPosition(horzPos);
        shape.setLeft(left);
        shape.setRelativeVerticalPosition(vertPos);
        shape.setTop(top);
        shape.setWrapType(wrapType);

        // WORDSNET-15504 Mimic MSW behavior, and set AspectRatioLocked true for ShapeType.Image.
        shape.setAspectRatioLocked(true);

        // We presume we are inserting a top level shape, it has to be inserted into the tree
        // for the shape size validation below to work properly.
        insertNode(shape);

        // WORDSNET-22466 Update shape orientation according to EXIF data for DML shape.
        // Note: Word does not change shape orientation while document import. See, test for issue 20914.
        // Perhaps, when loading progress will be implemented then it will be possible to remove "importMode" flag.
        double rotationAng = 0.0d;
        if (considerExifOrientation && isWord2007OrHigher && ImageUtil.isJpeg(imageBytes))
            rotationAng = updateImageOrientation(shape, imageBytes);

        // Set shape size either to the image size or to the specified size.
        shape.setSizeSafe(width, height, imageSize, rotationAng);

        // Set angle after the size. Because effect extends are added with the rotation.
        if (rotationAng > 0)
            shape.setRotation(rotationAng);

        return shape;
    }

    private double updateImageOrientation(Shape shape, byte[] imageBytes) throws Exception
    {
        /*ExifOrientation*/int exifOrientation = ImageUtil.getJpegOrientation(imageBytes);
        if (exifOrientation == ExifOrientation.HORIZONTAL)
            return 0.0d;

        /*FlipOrientation*/int flip = ExifOrientationUtils.getFlipOrientation(exifOrientation);
        if (flip != FlipOrientation.NONE)
            shape.setFlipOrientation(flip);

        double rotationAng = ExifOrientationUtils.getRotationAngle(exifOrientation);
        // Rotation must be updated after the shape size will set (effect extend will be updated to).
        return rotationAng;
    }

    /// <summary>
    /// Inserts <see cref="HtmlOleControl" /> object into current position.
    /// </summary>
    Shape insertHtmlOleControl(HtmlOleControl htmlOleControl, Stream presentation) throws Exception
    {
        Debug.assertTrue(presentation != null, "Image presentation cannot be null.");

        Shape shape = insertOleImage(presentation);
        shape.setShapeType(ShapeType.OLE_CONTROL);
        shape.setShapeAttrInternal(ShapeAttr.OLE_OBJECT, htmlOleControl);

        return shape;
    }

    /// <summary>
    /// Inserts <see cref="Forms2OleControl"/> object into current position.
    /// </summary>
    /// <returns><see cref="Shape"/> object that contains passed <see cref="Forms2OleControl"/></returns>
    /// <seealso cref="Shape.OleFormat"/><seealso cref="OleFormat.OleControl"/>.
    public Shape insertForms2OleControl(Forms2OleControl forms2OleControl)
    {
        Shape shape = new Shape(mDoc, ShapeMarkupLanguage.VML);
        shape.setShapeType(ShapeType.OLE_CONTROL);

        shape.setRunPr(getRunPrCopy());
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.DEFAULT);
        shape.setLeft(0.0);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
        shape.setTop(0.0);
        shape.setWrapType(WrapType.INLINE);

        // Note, it is important that the Width and Height would be greater than 0. Otherwise,
        // for example, DOC writer will not write this shape at all (see Picf.IsValid())
        shape.setWidthSafe(forms2OleControl.getWidth());
        shape.setHeightSafe(forms2OleControl.getHeight());

        shape.setShapeAttrInternal(ShapeAttr.OLE_OBJECT, forms2OleControl);

        insertNode(shape);

        return shape;
    }

    /// <summary>
    /// Inserts an HTML string into the document.
    /// </summary>
    /// <param name="html">An HTML string to insert into the document.</param>
    /// <remarks>
    /// You can use this method to insert an HTML fragment or whole HTML document.
    /// </remarks>
    public void insertHtml(String html) throws Exception
    {
        insertHtml(html, HtmlInsertOptions.NONE);
    }

    /// <summary>
    /// Inserts an HTML string into the document.
    /// </summary>
    /// <param name="html">An HTML string to insert into the document.</param>
    /// <param name="useBuilderFormatting">
    /// A value indicating whether formatting specified in <see cref="DocumentBuilder"/>
    /// is used as base formatting for text imported from HTML.
    /// </param>
    /// <remarks>
    /// <para>
    /// You can use this method to insert an HTML fragment or whole HTML document.
    /// </para>
    /// <para>
    /// When <paramref name="useBuilderFormatting"/> is <c>false</c>,
    /// <see cref="DocumentBuilder"/> formating is ignored and formatting of inserted text
    /// is based on default HTML formatting. As a result, the text looks as it is rendered in browsers.
    /// </para>
    /// <para>
    /// When <paramref name="useBuilderFormatting"/> is <c>true</c>,
    /// formatting of inserted text is based on <see cref="DocumentBuilder"/> formatting,
    /// and the text looks as if it were inserted with <see cref="Write"/>.
    /// </para>
    /// </remarks>
    public void insertHtml(String html, boolean useBuilderFormatting) throws Exception
    {
        /*HtmlInsertOptions*/int options = useBuilderFormatting
            ? HtmlInsertOptions.USE_BUILDER_FORMATTING
            : HtmlInsertOptions.NONE;
        insertHtml(html, options);
    }

    /// <summary>
    /// Inserts an HTML string into the document. Allows to specify additional options.
    /// </summary>
    /// <param name="html">An HTML string to insert into the document.</param>
    /// <param name="options">Options that are used when HTML string is inserted.</param>
    /// <remarks>
    /// You can use this method to insert an HTML fragment or whole HTML document.
    /// </remarks>
    public void insertHtml(String html, /*HtmlInsertOptions*/int options) throws Exception
    {
        insertHtml(html, options, null);
    }

    /// <summary>
    /// Inserts an HTML string into the document. Allows to specify a hash set of bookmark names to be used in
    /// <see cref="RW.Html.Reader.HtmlHyperlinkResolver"/> to avoid bookmark retrieval for each inserted HTML value.
    /// </summary>
    void insertHtml(String html, /*HtmlInsertOptions*/int options, HashSetGeneric<String> bookmarkNames) throws Exception
    {
        ensureCurrentRunIsSplitByCharOffset();

        // WORDSNET-28036 Do not strip formatting in Design mode.
        if (isStripFormatting() && !mDocumentBuilderOptions.getDesignMode())
        {
            DocumentBuilder builder = new DocumentBuilder();
            builder.insertHtml(html);
            write(stripControlChars(builder.getDocument().toString(SaveFormat.TEXT)));
        }
        else
        {
            IDocumentReader htmlInserter = ReaderFactory.createHtmlDocumentInserter(html, options, this, bookmarkNames);
            htmlInserter.read();
        }
    }

    /// <summary>
    /// Groups the shapes passed as a parameter into a new GroupShape node which is inserted into the current position.
    /// </summary>
    /// <param name="shapes">The list of shapes to be grouped.</param>
    /// <remarks>
    /// <p>The position and dimension of the new GroupShape will be calculated automatically.</p>
    /// <p>VML and DML shapes cannot be grouped together.</p>
    /// </remarks>
    public GroupShape insertGroupShape(/*params*/ ShapeBase... shapes) throws Exception
    {
        if (shapes.length == 0 || (shapes[0] == null))
            throw new IllegalArgumentException("The shapes parameter shall contain at least one shape.");

        /*ShapeMarkupLanguage*/byte markupLanguage = shapes[0].getMarkupLanguage();
        double leftMin = shapes[0].getLeft();
        double leftMax = leftMin;
        double topMin = shapes[0].getTop();
        double topMax = topMin;
        double width = shapes[0].getWidth();
        double height = shapes[0].getHeight();
        for (ShapeBase shape : shapes)
        {
            if (shape.getMarkupLanguage() != markupLanguage)
                throw new IllegalArgumentException("VML and DML shapes cannot be grouped together.");

            if (shape.getLeft() > leftMax)
            {
                leftMax = shape.getLeft();
                width = shape.getWidth();
            }

            if (shape.getLeft() < leftMin)
                leftMin = shape.getLeft();

            if (shape.getTop() > topMax)
            {
                topMax = shape.getTop();
                height = shape.getHeight();
            }

            if (shape.getTop() < topMin)
                topMin = shape.getTop();
        }

        return insertGroupShape(leftMin, topMin, width + leftMax - leftMin, height + topMax - topMin, shapes);
    }

    /// <summary>
    /// Groups the shapes passed as a parameter into a new GroupShape node of the specified size which is inserted into the specified position.
    /// </summary>
    /// <param name="left">Distance in points from the origin to the left side of the group shape.</param>
    /// <param name="top">Distance in points from the origin to the top side of the group shape.</param>
    /// <param name="width">The width of the group shape in points. A negative value is not allowed.</param>
    /// <param name="height">The height of the group shape in points. A negative value is not allowed.</param>
    /// <param name="shapes">The list of shapes to be grouped.</param>
    /// <remarks>
    /// VML and DML shapes cannot be grouped together.
    /// </remarks>
    public GroupShape insertGroupShape(double left, double top, double width, double height, /*params*/ ShapeBase... shapes) throws Exception
    {
        if (shapes.length == 0 || (shapes[0] == null))
            throw new IllegalArgumentException("The shapes parameter shall contain at least one shape.");

        /*ShapeMarkupLanguage*/byte markupLanguage = shapes[0].getMarkupLanguage();
        for (ShapeBase shape : shapes)
        {
            if (shape.getMarkupLanguage() != markupLanguage)
                throw new IllegalArgumentException("VML and DML shapes cannot be grouped together.");
            if (!shape.isTopLevel())
                throw new IllegalArgumentException("Only top-level shapes can be grouped.");
        }

        GroupShape group = new GroupShape(getDocument(), markupLanguage);
        group.setLeft(left);
        group.setTop(top);
        group.setWidth(width);
        group.setHeight(height);

        if (markupLanguage == ShapeMarkupLanguage.DML)
        {
            createDmlGroupShape(group, DmlNodeType.WORDPROCESSING_GROUP_SHAPE);
            updateTransforms(group, null);

            group.getGraphicData().setWidthCore(width, false);
            group.getGraphicData().setHeightCore(height, false);
        }
        else
        {
            group.getGraphicData().setName(msString.format("Group {0}", group.getId()));
            group.setCoordSizeSafe(msSize.ctor(ConvertUtilCore.pointToTwip(width), ConvertUtilCore.pointToTwip(height)));
        }

        for (ShapeBase shape : shapes)
        {
            if (shape.getWrapType() != WrapType.NONE)
                shape.setWrapType(WrapType.NONE);
            if (markupLanguage == ShapeMarkupLanguage.DML)
            {
                if (shape.getNodeType() == NodeType.GROUP_SHAPE)
                    createDmlGroupShape((GroupShape)shape, DmlNodeType.GROUP_SHAPE);

                updateTransforms(shape, group);
            }

            group.appendChild(shape);

            if (markupLanguage == ShapeMarkupLanguage.DML)
            {
                // Coordinates of child shapes should be in EMUs: copy from the transform.
                DmlTransform transform = shape.getDmlNode().getTransform();
                shape.getGraphicData().setWidthCore(transform.getWidth(), false);
                shape.getGraphicData().setHeightCore(transform.getHeight(), false);
                shape.setLeft(transform.getX());
                shape.setTop(transform.getY());
            }
            else
            {
                shape.getGraphicData().setWidthCore(ConvertUtilCore.pointToTwip(shape.getWidth()), false);
                shape.getGraphicData().setHeightCore(ConvertUtilCore.pointToTwip(shape.getHeight()), false);
                shape.setLeft(ConvertUtilCore.pointToTwip(shape.getLeft()));
                shape.setTop(ConvertUtilCore.pointToTwip(shape.getTop()));
            }
        }
        insertNode(group);

        return group;
    }

    /// <summary>
    /// Inserts an chart object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="chartType">The chart type to insert into the document.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertChart(/*ChartType*/int chartType, double width, double height) throws Exception
    {
        return insertChart(chartType, width, height, ChartStyle.NORMAL);
    }

    /// <summary>
    /// Inserts an chart object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="chartType">The chart type to insert into the document.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <param name="chartStyle">The style of the inserted chart.</param>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertChart(/*ChartType*/int chartType, double width, double height, /*ChartStyle*/int chartStyle) throws Exception
    {
        return insertChart(chartType, RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT,
            0.0, width, height, WrapType.INLINE, chartStyle);
    }


    /// <summary>
    /// Inserts an chart object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="chartType">The chart type to insert into the document.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertChart(
        /*ChartType*/int chartType,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType) throws Exception
    {
        return insertChart(chartType, horzPos, left, vertPos, top, width, height, wrapType, ChartStyle.NORMAL);
    }

    /// <summary>
    /// Inserts an chart object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="chartType">The chart type to insert into the document.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/>
    /// <param name="chartStyle">The style of the inserted chart.</param>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertChart(
        /*ChartType*/int chartType,
        /*RelativeHorizontalPosition*/int horzPos,
        double left,
        /*RelativeVerticalPosition*/int vertPos,
        double top,
        double width,
        double height,
        /*WrapType*/int wrapType,
        /*ChartStyle*/int chartStyle) throws Exception
    {
        IChartInserter chartInserter = ReaderFactory.createChartInserter();
        return chartInserter.insertChart(chartType, chartStyle, horzPos, left, vertPos,
            top, width, height, wrapType, this);
    }

    /// <summary>
    /// Inserts an online video object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="videoUrl">The URL to the video.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// <para>Insertion of online video from the following resources is supported:</para>
    /// <list type="bullet">
    /// <item>
    /// <description>https://www.youtube.com/</description>
    /// </item>
    /// <item>
    /// <description>https://vimeo.com/</description>
    /// </item>
    /// </list>
    /// <para>If your online video is not displaying correctly, use <see cref="InsertOnlineVideo(string, string, byte[], double, double)"/>, which accepts custom embedded html code.</para>
    /// <para>The code for embedding video can vary between providers, consult your corresponding provider of choice for details.</para>
    /// </remarks>
    public Shape insertOnlineVideo(String videoUrl, double width, double height) throws Exception
    {
        return insertOnlineVideo(videoUrl, RelativeHorizontalPosition.DEFAULT, 0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT,
             0.0, width, height, WrapType.INLINE);
    }

    /// <summary>
    /// Inserts an online video object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="videoUrl">The URL to the video.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// <para>Insertion of online video from the following resources is supported:</para>
    /// <list type="bullet">
    /// <item>
    /// <description>https://www.youtube.com/</description>
    /// </item>
    /// <item>
    /// <description>https://vimeo.com/</description>
    /// </item>
    /// </list>
    /// <para>If your online video is not displaying correctly, use <see cref="InsertOnlineVideo(string, string, byte[], double, double)"/>, which accepts custom embedded html code.</para>
    /// <para>The code for embedding video can vary between providers, consult your corresponding provider of choice for details.</para>
    /// </remarks>
    public Shape insertOnlineVideo(
       String videoUrl,
       /*RelativeHorizontalPosition*/int horzPos,
       double left,
       /*RelativeVerticalPosition*/int vertPos,
       double top,
       double width,
       double height,
       /*WrapType*/int wrapType) throws Exception
    {
        return new VideoInserter(this).insertFromUrl(videoUrl, horzPos, left, vertPos, top, width, height, wrapType);
    }

    /// <summary>
    /// Inserts an online video object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="videoUrl">The URL to the video.</param>
    /// <param name="videoEmbedCode">The embed code for the video.</param>
    /// <param name="thumbnailImageBytes">The thumbnail image bytes.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParams"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertOnlineVideo(
       String videoUrl,
       String videoEmbedCode,
       byte[] thumbnailImageBytes,
       double width,
       double height) throws Exception
    {
        return insertOnlineVideo(videoUrl, videoEmbedCode, thumbnailImageBytes, RelativeHorizontalPosition.DEFAULT,
            0.0, RelativeVerticalPosition.TEXT_FRAME_DEFAULT, 0.0, width, height, WrapType.INLINE);
    }

    /// <summary>
    /// Inserts an online video object into the document and scales it to the specified size.
    /// </summary>
    /// <param name="videoUrl">The URL to the video.</param>
    /// <param name="videoEmbedCode">The embed code for the video.</param>
    /// <param name="thumbnailImageBytes">The thumbnail image bytes.</param>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageParamsEx"]/*'/>
    /// <remarks>
    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="DocumentBuilder.InsertImageCommon"]/*'/>
    /// </remarks>
    public Shape insertOnlineVideo(
       String videoUrl,
       String videoEmbedCode,
       byte[] thumbnailImageBytes,
       /*RelativeHorizontalPosition*/int horzPos,
       double left,
       /*RelativeVerticalPosition*/int vertPos,
       double top,
       double width,
       double height,
       /*WrapType*/int wrapType) throws Exception
    {
        return new VideoInserter(this).insertFromVideoEmbedCode(videoUrl, videoEmbedCode, thumbnailImageBytes, horzPos,
            left, vertPos, top, width, height, wrapType);
    }

    /// <summary>
    /// Inserts a horizontal rule shape into the document.
    /// </summary>
    /// <returns>The shape that is a horizontal rule.</returns>
    public Shape insertHorizontalRule() throws Exception
    {
        Shape shape = Shape.createHorizontalRule(mDoc);
        insertNode(shape);
        return shape;
    }

    /// <summary>
    /// Returns <c>true</c> if we need to strip the formatting off the source string.
    /// </summary>
    private boolean isStripFormatting()
    {
        boolean stripFormatting = false;

        if (getCursor().getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
        {
            StructuredDocumentTag tag = (StructuredDocumentTag)getCursor().getParentNode();
            switch (tag.getSdtType())
            {
                // MS Word strips formatting off the content inserted into these SDT types.
                case SdtType.DATE:
                case SdtType.COMBO_BOX:
                case SdtType.PLAIN_TEXT:
                    stripFormatting = true;
                    break;
                case SdtType.PICTURE:
                    // DD: MS Word converts inserted content into an image and then inserts image into SDT. We will skip this for now
                    // until we get customer requests.
                    break;
                case SdtType.CHECKBOX:
                case SdtType.DROP_DOWN_LIST:
                    throw new IllegalStateException("Can not insert text into this StructuredDocumentTag.");
                default:
                    break;
            }
        }
        return stripFormatting;
    }

    /// <summary>
    /// Remove control chars from plain text string.
    /// </summary>
    private static String stripControlChars(String plaintext)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++)
        {
        	char c = plaintext.charAt(i);
            if ((c != ControlChar.LINE_BREAK_CHAR) &&
                (c != ControlChar.LINE_FEED_CHAR) &&
                (c != ControlChar.SECTION_BREAK_CHAR) &&
                (c != ControlChar.PARAGRAPH_BREAK_CHAR) &&
                (c != ControlChar.PAGE_BREAK_CHAR))
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /// <summary>
    /// Inserts a table cell into the document.
    /// </summary>
    /// <remarks>
    /// <p>To start a table, just call <see cref="InsertCell"/>. After this, any content you add using
    /// other methods of the <see cref="DocumentBuilder"/> class will be added to the current cell.</p>
    /// <p>To start a new cell in the same row, call <see cref="InsertCell"/> again.</p>
    /// <p>To end a table row call <see cref="EndRow"/>.</p>
    /// <p>Use the <see cref="CellFormat"/> property to specify cell formatting.</p>
    /// </remarks>
    /// <returns>The cell node that was just inserted.</returns>
    public Cell insertCell()
    {
        ensureCurrentRunIsSplitByCharOffset();

        //Calling StartTable is implicit for the first level table.
        if (getCurTableBuilder() == null)
            startTable();

        //User never gets to call StartRow, we always do it.
        if (getCurTableBuilder().getTableState() == TableState.IN_TABLE)
            getCurTableBuilder().startRow();

        //User never gets to call EndCell, we always do it.
        if (getCurTableBuilder().getTableState() == TableState.IN_CELL)
            getCurTableBuilder().endCell();

        return getCurTableBuilder().startCell();
    }

    /// <summary>
    /// Starts a table in the document.
    /// </summary>
    /// <remarks>
    /// <p>The next method to call is <see cref="InsertCell"/>.</p>
    /// <p>This method starts a nested table when called inside a cell.</p>
    /// </remarks>
    /// <returns>The table node that was just created.</returns>
    public Table startTable()
    {
        mTableBuilders.push(new TableBuilder(this, mDocumentBuilderOptions.getContextTableFormatting()));
        return getCurTableBuilder().startTable();
    }

    /// <summary>
    /// Ends a table in the document.
    /// </summary>
    /// <remarks>
    /// <p>This method should be called only once after <see cref="EndRow"/> was called. When called,
    /// <see cref="EndTable"/> moves the cursor out of the current cell to point just after the table.</p>
    /// </remarks>
    /// <returns>The table node that was just finished.</returns>
    public Table endTable()
    {
        if (getCurTableBuilder() == null)
            throw new IllegalStateException("Cannot end a table while not building a table.");

        Table table = getCurTableBuilder().endTable();
        mTableBuilders.pop();

        return table;
    }

    /// <summary>
    /// Ends a table row in the document.
    /// </summary>
    /// <remarks>
    /// <p>Call <see cref="EndRow"/> to end a table row. If you call <see cref="InsertCell"/> immediately
    /// after that, then the table continues on a new row.</p>
    /// <p>Use the <see cref="RowFormat"/> property to specify row formatting.</p>
    /// </remarks>
    /// <returns>The row node that was just finished.</returns>
    public Row endRow()
    {
        if (getCurTableBuilder() == null)
            throw new IllegalStateException("Cannot end a row while not building a table.");

        Row row = getCurTableBuilder().endRow();

        // WORDSNET-13772 Update row hidden attribute to get unified behavior.
        if ((mFont != null) && mFont.getHidden())
            row.getTablePr().setHidden(true);

        return row;
    }

    /// <summary>
    /// Marks the current position in the document as a bookmark start.
    /// </summary>
    /// <remarks>
    /// <p>Bookmarks in a document can overlap and span any range. To create a valid bookmark you need to
    /// call both <see cref="StartBookmark"/> and <see cref="EndBookmark"/> with the same <paramref name="bookmarkName"/>
    /// parameter.</p>
    /// <p>Badly formed bookmarks or bookmarks with duplicate names will be ignored when the document is saved.</p>
    /// </remarks>
    /// <param name="bookmarkName">Name of the bookmark.</param>
    /// <returns>The bookmark start node that was just created.</returns>
    public BookmarkStart startBookmark(String bookmarkName)
    {
        BookmarkStart bookmarkStart = new BookmarkStart(mDoc, bookmarkName, 0);
        insertNode(bookmarkStart);
        return bookmarkStart;
    }

    /// <summary>
    /// Marks the current position in the document as a bookmark end.
    /// </summary>
    /// <remarks>
    /// <p>Bookmarks in a document can overlap and span any range. To create a valid bookmark you need to
    /// call both <see cref="StartBookmark"/> and <see cref="EndBookmark"/> with the same <paramref name="bookmarkName"/>
    /// parameter.</p>
    /// <p>Badly formed bookmarks or bookmarks with duplicate names will be ignored when the document is saved.</p>
    /// </remarks>
    /// <param name="bookmarkName">Name of the bookmark.</param>
    /// <returns>The bookmark end node that was just created.</returns>
    public BookmarkEnd endBookmark(String bookmarkName)
    {
        BookmarkEnd bookmarkEnd = new BookmarkEnd(mDoc, bookmarkName);
        insertNode(bookmarkEnd);
        return bookmarkEnd;
    }

    /// <summary>
    /// Marks the current position in the document as a column bookmark start. The position must be in a table cell.
    /// </summary>
    /// <remarks>
    /// <p>A column bookmark covers one or more columns in a range of rows. To create a valid bookmark you
    /// need to call both <see cref="StartColumnBookmark"/> and <see cref="EndColumnBookmark"/> with the same
    /// <paramref name="bookmarkName"/> parameter.</p>
    /// <p>Badly formed bookmarks or bookmarks with duplicate names will be ignored when the document is saved.</p>
    /// <p>The actual position of the inserted <see cref="BookmarkStart"/> node may differ from the current document
    /// builder position.</p>
    /// </remarks>
    /// <param name="bookmarkName">Name of the bookmark.</param>
    /// <returns>The bookmark start node that was just created.</returns>
    public BookmarkStart startColumnBookmark(String bookmarkName)
    {
        Cell cell = getCurrentCell();
        if (cell == null)
            throw new IllegalStateException("A column bookmark can only be started in a table cell.");

        Cell firstCell = cell.getParentRow().getFirstCell();
        firstCell.ensureMinimum();

        BookmarkStart bookmarkStart = new BookmarkStart(mDoc, bookmarkName);
        firstCell.getFirstParagraph().insertAfter(bookmarkStart, null);

        // Get column index as it will be in saved document after converting HorizontalMerge to GridSpan.
        int cellIndex = cell.getMergedColumnIndex();
        bookmarkStart.setFirstColumn(cellIndex);

        return bookmarkStart;
    }

    /// <summary>
    /// Marks the current position in the document as a column bookmark end. The position must be in a table cell.
    /// </summary>
    /// <remarks>
    /// <p>A column bookmark covers one or more columns in a range of rows. To create a valid bookmark you
    /// need to call both <see cref="StartColumnBookmark"/> and <see cref="EndColumnBookmark"/> with the same
    /// <paramref name="bookmarkName"/> parameter.</p>
    /// <p>Badly formed bookmarks or bookmarks with duplicate names will be ignored when the document is saved.</p>
    /// <p>The actual position of the inserted <see cref="BookmarkEnd"/> node may differ from the current document
    /// builder position.</p>
    /// </remarks>
    /// <param name="bookmarkName">Name of the bookmark.</param>
    /// <returns>The bookmark end node that was just created.</returns>
    public BookmarkEnd endColumnBookmark(String bookmarkName)
    {
        Cell cell = getCurrentCell();
        if (cell == null)
            throw new IllegalStateException("A column bookmark can only be ended in a table cell.");

        Bookmark bookmark = cell.getParentTable().getRange().getBookmarks().get(bookmarkName);
        if (bookmark == null)
            throw new IllegalStateException("The corresponding bookmark start must be in the same table.");

        // Get column index as it will be in saved document after converting HorizontalMerge to GridSpan.
        int cellIndex = cell.getMergedColumnIndex();
        if (bookmark.getBookmarkStart().getFirstColumn() > cellIndex)
        {
            throw new IllegalStateException(
                "The end column index must be greater than or equal to the start column index.");
        }

        BookmarkEnd bookmarkEnd = new BookmarkEnd(mDoc, bookmarkName);
        cell.getParentTable().insertAfter(bookmarkEnd, cell.getParentRow());

        bookmark.getBookmarkStart().setLastColumn(cellIndex);

        return bookmarkEnd;
    }

    /// <summary>
    /// Marks the current position in the document as an editable range start.
    /// </summary>
    /// <remarks>
    /// <p>Editable range in a document can overlap and span any range. To create a valid editable range you need to
    /// call both <see cref="StartEditableRange"/> and <see cref="EndEditableRange()"/>
    /// or <see cref="EndEditableRange(EditableRangeStart)"/> methods.</p>
    /// <p>Badly formed editable range will be ignored when the document is saved.</p>
    /// </remarks>
    /// <returns>The editable range start node that was just created.</returns>
    public EditableRangeStart startEditableRange()
    {
        EditableRangeStart editableRangeStart = new EditableRangeStart(mDoc);
        mLastEditableRangeId = editableRangeStart.getId();
        insertNode(editableRangeStart);
        return editableRangeStart;
    }

    /// <summary>
    /// Marks the current position in the document as an editable range end.
    /// </summary>
    /// <remarks>
    /// <p>Editable range in a document can overlap and span any range. To create a valid editable range you need to
    /// call both <see cref="StartEditableRange"/> and <see cref="EndEditableRange()"/>
    /// or <see cref="EndEditableRange(EditableRangeStart)"/> methods.</p>
    /// <p>Badly formed editable range will be ignored when the document is saved.</p>
    /// </remarks>
    /// <returns>The editable range end node that was just created.</returns>
    public EditableRangeEnd endEditableRange()
    {
        if (mLastEditableRangeId == UNINITIALIZED)
            throw new IllegalStateException("EndEditableRange can not be called before StartEditableRange.");

        EditableRangeEnd editableRangeEnd = new EditableRangeEnd(mDoc, mLastEditableRangeId);
        insertNode(editableRangeEnd);
        return editableRangeEnd;
    }

    /// <summary>
    /// Marks the current position in the document as an editable range end.
    /// </summary>
    /// <remarks>
    /// <p>Use this overload during creating nested editable ranges.</p>
    /// <p>Editable range in a document can overlap and span any range. To create a valid editable range you need to
    /// call both <see cref="StartEditableRange"/> and <see cref="EndEditableRange()"/>
    /// or <see cref="EndEditableRange(EditableRangeStart)"/> methods.</p>
    /// <p>Badly formed editable range will be ignored when the document is saved.</p>
    /// </remarks>
    /// <param name="start">This editable range start.</param>
    /// <returns>The editable range end node that was just created.</returns>
    public EditableRangeEnd endEditableRange(EditableRangeStart start)
    {
        EditableRangeEnd editableRangeEnd = new EditableRangeEnd(mDoc, start.getId());
        insertNode(editableRangeEnd);
        return editableRangeEnd;
    }
    /// <summary>
    /// Inserts a document at the cursor position.
    /// </summary>
    /// <remarks>
    /// This method mimics the MS Word behavior, as if CTRL+'A' (select all content) was pressed,
    /// then CTRL+'C' (copy selected into the buffer) inside one document
    /// and then CTRL+'V' (insert content from the buffer) inside another document.
    /// </remarks>
    /// <param name="srcDoc">Source document for inserting.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <returns>First node of the inserted content.</returns>
    public Node insertDocument(Document srcDoc, /*ImportFormatMode*/int importFormatMode)
    {
        return insertDocument(srcDoc, importFormatMode, new ImportFormatOptions());
    }

    /// <summary>
    /// Inserts a document at the cursor position.
    /// </summary>
    /// <remarks>
    /// This method mimics the MS Word behavior, as if CTRL+'A' (select all content) was pressed,
    /// then CTRL+'C' (copy selected into the buffer) inside one document
    /// and then CTRL+'V' (insert content from the buffer) inside another document.
    /// </remarks>
    /// <param name="srcDoc">Source document for inserting.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <param name="importFormatOptions">Allows to specify options that affect formatting of a result document.</param>
    /// <returns>First node of the inserted content.</returns>
    public Node insertDocument(Document srcDoc, /*ImportFormatMode*/int importFormatMode, ImportFormatOptions importFormatOptions)
    {
        ArgumentUtil.checkNotNull(importFormatOptions, "ImportFormatOptions");
        ensureCurrentRunIsSplitByCharOffset();
        return DocumentInserter.insertDocument(this, srcDoc, importFormatMode, importFormatOptions);
    }

    /// <summary>
    /// Inserts a document inline at the cursor position.
    /// </summary>
    /// <remarks>
    /// <para>
    /// This method mimics the MS Word behavior, as if CTRL+'A' (select all content) was pressed,
    /// then CTRL+'C' (copy selected into the buffer) inside one document
    /// and then CTRL+'V' (insert content from the buffer) inside another document.
    /// </para>
    /// <para>As a difference from <see cref="InsertDocument(Aspose.Words.Document,Aspose.Words.ImportFormatMode,Aspose.Words.ImportFormatOptions)"/>
    /// this method moves the content of the paragraph of the destination document,
    /// before which the source document is inserted, into the last
    /// paragraph of the inserted source document. Actually, this means that
    /// paragraph break of the last inserted paragraph is removed.</para>
    /// <para>Note, if the last node of the source document is not a paragraph, then nothing will be done.</para>
    /// </remarks>
    /// <param name="srcDoc">Source document for inserting.</param>
    /// <param name="importFormatMode">Specifies how to merge style formatting that clashes.</param>
    /// <param name="importFormatOptions">Allows to specify options that affect formatting of a result document.</param>
    /// <returns>First node of the inserted content.</returns>
    public Node insertDocumentInline(Document srcDoc, /*ImportFormatMode*/int importFormatMode, ImportFormatOptions importFormatOptions)
    {
        ArgumentUtil.checkNotNull(importFormatOptions, "ImportFormatOptions");
        ensureCurrentRunIsSplitByCharOffset();

        importFormatOptions.setInlineMode(true);
        Node node = DocumentInserter.insertDocument(this, srcDoc, importFormatMode, importFormatOptions);
        importFormatOptions.setInlineMode(false);

        return node;
    }

    /// <summary>
    /// Gets or sets the <see cref="Document"/> object that this object is attached to.
    /// </summary>
    public Document getDocument()
    {
        return mDoc;
    }
    public void setDocument(Document value)
    {
        if (value == null)
            throw new NullPointerException();
        if (value == mDoc)
            return;

        // Detach from the current document and attach to the new document.
        mDoc = value;
        setCursor(null);

        mRunPr = new RunPr();
        mRunPrStack = null;
        mParaPrStack = null;
        mFont = null;

        mTableBuilders = new msStack<TableBuilder>();

        // RK We are moving to start here because moving to end was making document builder very slow.
        // I never had time to figure out why, but now it is too late to change since many clients rely on this.
        moveToDocumentStart();
    }

    /// <summary>
    /// Returns an object that represents current font formatting properties.
    /// </summary>
    /// <remarks>
    /// <p>Use <see cref="Font"/> to access and modify font formatting properties.</p>
    /// <p>Specify font formatting before inserting text.</p>
    /// </remarks>
    public Font getFont()
    {
        if (mFont == null)
            mFont = new Font(this, getDocument());
        return mFont;
    }

    /// <summary>
    /// True if the font is formatted as bold.
    /// </summary>
    public boolean getBold() { return getFont().getBold(); }
    public void setBold(boolean value) { getFont().setBold(value); }

    /// <summary>
    /// True if the font is formatted as italic.
    /// </summary>
    public boolean getItalic() { return getFont().getItalic(); }
    public void setItalic(boolean value) { getFont().setItalic(value); }

    /// <summary>
    /// Gets/sets underline type for the current font.
    /// </summary>
    public /*Underline*/int getUnderline() { return getFont().getUnderline(); }
    public void setUnderline(/*Underline*/int value) { getFont().setUnderline(value); }

    /// <summary>
    /// Returns an object that represents current paragraph formatting properties.
    /// </summary>
    public ParagraphFormat getParagraphFormat()
    {
        if (mParagraphFormat == null)
            mParagraphFormat = new ParagraphFormat(this, getDocument().getStyles());

        return mParagraphFormat;
    }

    /// <summary>
    /// Returns an object that represents current list formatting properties.
    /// </summary>
    public ListFormat getListFormat() { return (getCurrentParagraph() != null) ? getCurrentParagraph().getListFormat() : null; }

    /// <summary>
    /// Returns an object that represents current page setup and section properties.
    /// </summary>
    public PageSetup getPageSetup() { return getCurrentSection().getPageSetup(); }

    /// <summary>
    /// Returns an object that represents current table row formatting properties.
    /// </summary>
    public RowFormat getRowFormat()
    {
        if (mRowFormat == null)
            mRowFormat = new RowFormat(this);
        return mRowFormat;
    }

    /// <summary>
    /// Returns an object that represents current table cell formatting properties.
    /// </summary>
    public CellFormat getCellFormat()
    {
        if (mCellFormat == null)
            mCellFormat = new CellFormat(this);
        return mCellFormat;
    }

    void insertBeforeCurPara(Node node)
    {
        getCurrentParagraph().insertPrevious(node);
    }

    /// <summary>
    /// Saves current character formatting onto the stack.
    /// </summary>
    /// <seealso cref="Font"/>
    /// <seealso cref="PopFont"/>
    public void pushFont()
    {
        // WORDSNET-19483 ParagraphBreakRunPr should be considered.
        RunPr paraBreakRunPr = (getCurrentParagraph() != null)
            ? AttrCollectionExtension.deepClone(getCurrentParagraph().getParagraphBreakRunPr())
            : getRunPrCopy();

        getFontPrs().push(new FontPr(getRunPrCopy(), paraBreakRunPr));
    }

    /// <summary>
    /// Retrieves character formatting previously saved on the stack.
    /// </summary>
    /// <seealso cref="Font"/>
    /// <seealso cref="PushFont"/>
    public void popFont()
    {
        if (getFontPrs().getCount() > 0)
        {
            FontPr fontPr = getFontPrs().pop();
            //Don't request need cloning of these attrs since they are not connected to the model.
            setFont(fontPr.getRunPr(), false);
            if (getCurrentParagraph() != null)
                getCurrentParagraph().setParagraphBreakRunPr(fontPr.getParaBreakRunPr());
        }
    }

    /// <summary>
    /// Saves current run properties to the stack.
    /// </summary>
    void pushRunPr()
    {
        getRunPrStack().push(getRunPrCopy());
    }

    /// <summary>
    /// Restores current run properties from the stack.
    /// </summary>
    void popRunPr()
    {
        if (getRunPrStack().getCount() > 0)
        {
            //Don't request need cloning of these attrs since they are not connected to the model.
            setFont(getRunPrStack().pop(), false);
        }
    }

    /// <summary>
    /// Saves current paragraph properties to the stack.
    /// </summary>
    void pushParaPr()
    {
        getParaPrStack().push(getParaPrCopy());
    }

    /// <summary>
    /// Restores current paragraph properties from the stack.
    /// </summary>
    void popParaPr()
    {
        if (getParaPrStack().getCount() > 0)
        {
            ParaPr paraPr = getParaPrStack().pop();
            if (getCurrentParagraph() != null)
                getCurrentParagraph().setParaPr(paraPr);
        }
    }

    FieldBundle startHyperlink(String href, String target, String screenTip)
    {
        boolean isBookmark = UriUtil.isSubAddressOnly(href);
        String urlOrBookmark = (isBookmark) ? UriUtil.getSubAddress(href) : href;
        return startHyperlink(urlOrBookmark, isBookmark, target, screenTip);
    }

    /// <summary>
    /// Starts Hyperlink with the specified field code in the current position in the document.
    /// </summary>
    FieldBundle startHyperlink(FieldCodeHyperlink fieldCode)
    {
        FieldStart start = insertFieldStart(FieldType.FIELD_HYPERLINK);
        insertFieldCode(fieldCode.toFieldCodeString());
        FieldSeparator separator = insertFieldSeparator(FieldType.FIELD_HYPERLINK);
        return new FieldBundle(start, separator, null);
    }

    /// <summary>
    /// Needed so you can put any formatted text into hyperlink text.
    /// </summary>
    private FieldBundle startHyperlink(String urlOrBookmark, boolean isBookmark, String target, String screenTip)
    {
        FieldStart start = insertFieldStart(FieldType.FIELD_HYPERLINK);

        FieldCodeHyperlink fieldCode = new FieldCodeHyperlink();
        fieldCode.setTarget(target);
        fieldCode.setScreenTip(screenTip);
        if (isBookmark)
        {
            fieldCode.setSubAddress(urlOrBookmark);
        }
        else
        {
            fieldCode.setAddress(UriUtil.getAddress(urlOrBookmark));
            fieldCode.setSubAddress(UriUtil.getSubAddress(urlOrBookmark));
        }
        insertFieldCode(fieldCode.toFieldCodeString());

        FieldSeparator separator = insertFieldSeparator(FieldType.FIELD_HYPERLINK);
        return new FieldBundle(start, separator, null);
    }

    /// <summary>
    /// Call after you called StartHyperlink and inserted hyperlink text.
    /// No checks are made for proper start/end, so be careful.
    /// </summary>
    FieldEnd endHyperlink()
    {
        return insertFieldEnd(FieldType.FIELD_HYPERLINK, true);
    }

    RunPr getRunPrCopy()
    {
        return AttrCollectionExtension.deepClone(mRunPr);
    }

    ParaPr getParaPrCopy()
    {
        return AttrCollectionExtension.deepClone(getParaPr());
    }

    TablePr getTablePrCopy()
    {
        return AttrCollectionExtension.deepClone(getTablePr());
    }

    CellPr getCellPrCopy()
    {
        return AttrCollectionExtension.deepClone(getCellPr());
    }

    /// <summary>
    /// Returns <c>true</c> if the cursor is at the beginning of the current paragraph (no text before the cursor).
    /// </summary>
    public boolean isAtStartOfParagraph()
    {
        if (getCurrentParagraph() == null)
            return false;

        // Walk all nodes from the start of the paragraph.
        // If we have something other than bookmark, it means we are not at the start of the paragraph.
        Node node = getCurrentParagraph().getFirstChild();
        while ((node != null) && (node != getCursor()))
        {
            if ((node.getNodeType() == NodeType.BOOKMARK_START) || (node.getNodeType() == NodeType.BOOKMARK_END))
                node = node.getNextSibling();
            else
                return false;
        }
        return true;
    }

    /// <summary>
    /// Returns <c>true</c> if the cursor is at the end of the current paragraph.
    /// </summary>
    public boolean isAtEndOfParagraph() { return (getCursor().getNodeType() == NodeType.PARAGRAPH); }

    /// <summary>
    /// Returns <b>true</b> if the cursor is at the end of a structured document tag.
    /// </summary>
    public boolean isAtEndOfStructuredDocumentTag()
    {
        return mIsAtEndOfStructuredDocumentTag && (getCursor().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG);
    }

    /// <summary>
    /// This is a common implementation for Write and Writeln. It is required because even when calling
    /// Write, the text may contain paragraph breaks and it needs to be split to create more than one paragraph.
    /// </summary>
    private void writeCore(String text, boolean isWriteln)
    {
        if (text == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "text");

        if (isAtEndOfStructuredDocumentTag())
        {
            switch (((StructuredDocumentTag)getCursor()).getLevel())
            {
                case MarkupLevel.BLOCK:
                    insertParagraph();
                    isWriteln = false;
                    break;
                case MarkupLevel.INLINE:
                    break;
                default:
                    throw new IllegalStateException("Cannot insert text at this cursor position.");
            }
        }

        //Convert CrLf and Lf into Crs.
        String normalizedText = WordUtil.normalizeToWord(text);

        //If text had any Crs insert paragraphs as appropriate.
        int paraStart = 0;
        while (paraStart <= normalizedText.length())
        {
            int paraEnd = normalizedText.indexOf(ControlChar.PARAGRAPH_BREAK_CHAR, paraStart);
            if (paraEnd != -1)
            {
                int length = paraEnd - paraStart;
                if (length > 0)
                    insertBidiAwareRun(normalizedText.substring((paraStart), (paraStart) + (length)));

                // WORDSNET-27645 Ensure that whole multi-line text inserted into one location.
                ensureAtStructuredDocumentTagEnd();

                switch (mParagraphBreakCharReplacement)
                {
                    case ParagraphBreakCharReplacement.PARAGRAPH:
                        insertParagraph();
                        break;
                    case ParagraphBreakCharReplacement.PARAGRAPH_BREAK_CHAR:
                        insertRun(ControlChar.PARAGRAPH_BREAK);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }

                paraStart = paraEnd + 1;
            }
            else
            {
                //No more paragraph breaks are found.
                int length = normalizedText.length() - paraStart;
                if (length > 0)
                    insertBidiAwareRun(normalizedText.substring((paraStart), (paraStart) + (length)));

                if (isWriteln)
                    insertParagraph();

                break;
            }
        }
    }

    /// <summary>
    /// Inserts a node before the cursor.
    /// </summary>
    public void insertNode(Node node)
    {
        ensureCurrentRunIsSplitByCharOffset();

        //This is a hangover from the previous version where we end tables implicitly
        //whenever user starts inserting text after end of a table row.
        if ((getCurTableBuilder() != null) && (getCurTableBuilder().getTableState() == TableState.IN_TABLE))
            endTable();

        if (isAtEndOfParagraph())
            getCurrentParagraph().appendChild(node);
        else if (isAtEndOfStructuredDocumentTag())
            getCurrentStructuredDocumentTag().appendChild(node);
        else
            getCursor().insertPrevious(node);
    }

    /// <summary>
    /// Adding new section involves breaking current paragraph and breaking current section
    /// and moving content after cursor to the new section.
    /// </summary>
    void insertSection(/*SectionStart*/int sectionStart)
    {
        // Shall we also have an ability to insert a section in exception-safe manner?
        checkCanSeriousBreak(true);

        insertSectionCore(sectionStart);
    }

    private void insertSectionCore(/*SectionStart*/int sectionStart)
    {
        insertParagraph();

        // When Word tracks section insertion it just mark section break character as inserted.
        // We have no section break characters in DOM. Instead we have Section node.
        // I don't fully understand what should we do here so suspend revision tracking for this operation for a while.
        SuspendTrackRevisionsDocument suspendTrackRevisionsDocumentResource = new SuspendTrackRevisionsDocument(getDocument());
        try /*JAVA: was using*/
        {
            //New section gets a copy of the current section SEP.
            SectPr newSectPr = AttrCollectionExtension.deepClone(getCurrentSection().getSectPr());

            //Add new section node after current section.
            Section newSection = new Section(mDoc, newSectPr);
            newSection.getPageSetup().setSectionStart(sectionStart);
            newSection.appendChild(new Body(mDoc));

            getCurrentSection().insertNext(newSection);

            //We move only main text and do not copy headers, maybe could do in the future.
            newSection.getBody().insertAfter(getCurrentParagraph(), null, newSection.getBody().getLastChild());
        }
        finally { if (suspendTrackRevisionsDocumentResource != null) suspendTrackRevisionsDocumentResource.dispose(); }
    }

    FieldStart insertFieldStart(/*FieldType*/int fieldType)
    {
        ensureCurrentRunIsSplitByCharOffset();

        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertFieldStart(fieldType, getRunPrCopy(), getCurrentParent(), getReferenceNode(),
            getReferenceNode() == null);
    }

    Run insertFieldCode(String fieldCode)
    {
        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertFieldCode(fieldCode, getRunPrCopy(), getCurrentParent(), getReferenceNode(),
            getReferenceNode() == null);
    }

    FieldEnd insertFieldEnd(/*FieldType*/int fieldType, boolean hasSeparator)
    {
        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertFieldEnd(fieldType, hasSeparator, getRunPrCopy(), getCurrentParent(),
            getReferenceNode(), getReferenceNode() == null);
    }

    FieldSeparator insertFieldSeparator(/*FieldType*/int fieldType)
    {
        // FieldAppender has different behavior than e.g. the CompositeNode.Insert method. To append field
        // nodes to a composite node, the 'refNode' argument must be 'null' and isAfter must be 'true': pass
        // ReferencedNode == null as isAfter.
        return FieldAppender.insertFieldSeparator(fieldType, getRunPrCopy(), getCurrentParent(), getReferenceNode(),
            getReferenceNode() == null);
    }

    /// <summary>
    /// Reposition cursor to the end of SDT if current paragraph is the last child of SDT.
    /// This is needed to insert new paragraph after current paragraph,
    /// otherwise it will be inserted after current SDT.
    /// </summary>
    void ensureAtStructuredDocumentTagEnd()
    {
        if (isAtEndOfParagraph() && (getCurrentParagraph().getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
            moveToStructuredDocumentTag((StructuredDocumentTag)getCurrentParagraph().getParentNode(), -1);
    }

    /// <summary>
    /// Creates a form field node for the specified ffdata and inserts it before the specified node.
    /// </summary>
    private FormField insertFormFieldNode(Node insertBefore)
    {
        FormField formField = new FormField(mDoc, new FormFieldPr(), getRunPrCopy());

        CompositeNode parentNode = (insertBefore == null)
            ? getCurrentParent()
            : insertBefore.getParentNode();

        //Although the actual FormField node is just before the field separator or field end
        //I insert it last as it allows soft refreshable collection of form fields to work properly.
        parentNode.insertBefore(formField, insertBefore);
        return formField;
    }

    /// <summary>
    /// "Serious" breaks are page, column and section breaks. They are not always allowed.
    /// Throws if such a break is not allowed.
    /// </summary>
    private boolean checkCanSeriousBreak(boolean throwOnError)
    {
        boolean isOutsideMainStory = getCurrentStory().getStoryType() != StoryType.MAIN_TEXT;
        boolean isInsideTable = getCurTableBuilder() != null;

        if (throwOnError)
        {
            if (isOutsideMainStory)
                throw new IllegalStateException("Cannot insert the requested break outside of the main story.");

            if (isInsideTable)
                throw new IllegalStateException("Cannot insert the requested break inside a table.");
        }

        return throwOnError || !(isOutsideMainStory || isInsideTable);
    }

    /// <summary>
    /// Searches through tables in the current story of the current section.
    /// Returns the specified row of the specified table or throws if cannot complete successfully.
    /// </summary>
    private Row fetchRow(int tableIndex, int rowIndex)
    {
        //Need recursive search for tables since they can be nested.
        NodeCollection tables = getCurrentStory().getChildNodes(NodeType.TABLE, true);
        Table table = (Table)tables.get(tableIndex);
        if (table == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "tableIndex");

        Row row = (Row)table.getChild(NodeType.ROW, rowIndex, false);
        if (row == null)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "rowIndex");

        return row;
    }

    /// <summary>
    /// Inserts run, which takes into account whether it is rtl or ltr text.
    /// </summary>
    private void insertBidiAwareRun(String text)
    {
        if (isNeedBidiExplicitly(text))
        {
            for (BidiRun bidiRun : BidiAlgorithm.applyToInsertedText(text, getParagraphFormat().getBidi()))
            {
                AttrBoolEx newBidi = AttrBoolEx.fromBool(bidiRun.getRtl());

                RunPr runPr = getRunPrCopy();
                runPr.setAttr(FontAttr.BIDI, newBidi);

                // WORDSNET-13550 We have to set bidi locale to 'he-IL' on RTL runs containing numbers with any of
                // the following number separator characters: '+', '-', and '/'. Otherwise, these separators will be treated
                // as neutral characters in MS Word and parts of the number will be reordered.
                if (bidiRun.getRequiresHebrewLocaleBi())
                {
                    runPr.setLocaleIdBi((int)Language.HEBREW_ISRAEL);
                }

                Run newRunNode = new Run(mDoc, bidiRun.getText(), runPr);
                insertNode(newRunNode);
                ComplexScriptRunUpdater.doProcess(newRunNode);
            }
        }
        else
        {
            insertRun(text);
        }
    }

    /// <summary>
    /// Returns <c>true</c>, if for run, based on this text, BiDi property has to be set explicitly.
    /// </summary>
    private boolean isNeedBidiExplicitly(String text)
    {
        boolean isRtlRun = getFont().getBidi();

        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            /*BidiCharacterType*/int characterType = UnicodeCharacterDataResolver.getBidiCharacterType(c);
            boolean isStrongLtrCharacter = characterType == BidiCharacterType.L;
            boolean isStrongRtlCharacter = (characterType == BidiCharacterType.R) || (characterType == BidiCharacterType.AL);
            if ((isRtlRun && isStrongLtrCharacter) || (!isRtlRun && isStrongRtlCharacter))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Creates and sets a DmlGroupShape corresponding to the specified DmlNodeType.
    /// </summary>
    private static void createDmlGroupShape(GroupShape group, /*DmlNodeType*/int dmlNodeType)
    {
        DmlGroupShape dmlGroup = new DmlGroupShape(dmlNodeType);
        group.setDmlNode(dmlGroup);
        dmlGroup.setName(msString.format("Group {0}", group.getId()));

        dmlGroup.setNonVisualPr(new DmlNvPrGroupShape());
        dmlGroup.getNonVisualPr().setCNvProperties(new DmlCnvPrGroupShape());
        dmlGroup.getNonVisualPr().setNvDrawingProperties(new DmlNvDrawingProperties());
    }

    /// <summary>
    /// Updates DML transformation data.
    /// </summary>
    private static void updateTransforms(ShapeBase shape, GroupShape parentGroup)
    {
        DmlTransform transform = shape.getDmlNode().getTransform();

        // If child shape is a shape of another shape group, its bounds are already in EMUs and transform is defined.
        boolean areBoundsInEmus = (shape.getWidth() == transform.getWidth()) && (shape.getHeight() == transform.getHeight());
        if (!areBoundsInEmus)
        {
            transform.setWidth(ConvertUtilCore.pointToEmu(shape.getWidth()));
            transform.setHeight(ConvertUtilCore.pointToEmu(shape.getHeight()));
        }

        if (parentGroup != null)
        {
            transform.setX(areBoundsInEmus
                ? shape.getLeft() - ConvertUtilCore.pointToEmu(parentGroup.getLeft())
                : ConvertUtilCore.pointToEmu(shape.getLeft() - parentGroup.getLeft()));
            transform.setY(areBoundsInEmus
                ? shape.getTop() - ConvertUtilCore.pointToEmu(parentGroup.getTop())
                : ConvertUtilCore.pointToEmu(shape.getTop() - parentGroup.getTop()));
        }

        DmlGroupTransform grTransform = ms.as(transform, DmlGroupTransform.class);
        if (grTransform != null)
        {
            grTransform.setChildX(0.0);
            grTransform.setChildY(0.0);
            grTransform.setChildWidth(transform.getWidth());
            grTransform.setChildHeight(transform.getHeight());
        }
    }

    /// <summary>
    /// Sets font attributes to the specified attributes.
    /// </summary>
    /// <param name="runPr">New font attributes to use.</param>
    /// <param name="isNeedClone">Whether to clone the attributes before using or not.</param>
    void setFont(RunPr runPr, boolean isNeedClone)
    {
        mRunPr = (isNeedClone) ? AttrCollectionExtension.deepClone(runPr) : runPr;
    }

    /// <summary>
    /// Clears font attributes not affecting current paragraph break run attributes.
    /// </summary>
    void clearFont()
    {
        mRunPr.clear();
    }

    /// <summary>
    /// Saves formatting from the current paragraph cell to the builder's cell formatting.
    /// </summary>
    void saveCurCellFormatting()
    {
        if (getCurrentCell() != null)
        {
            CellPr currCellPr = getCurrentCell().getCellPr();
            mCellPr = AttrCollectionExtension.deepClone(currCellPr);
            // Clone() removes inherited complex attributes,
            // such as borders, so let's copy them explicitly.
            currCellPr.copyBordersTo(mCellPr);
        }
    }

    /// <summary>
    /// Inserts image as VML shape, for future use as container for embedded OLE object.
    /// </summary>
    private Shape insertOleImage(Stream imageStream) throws Exception
    {
        byte[] imageBytes = StreamUtil.copyStreamToByteArray(imageStream);
        return insertOleImage(imageBytes);
    }

    /// <summary>
    /// Inserts image as VML shape, for future use as container for embedded OLE object.
    /// </summary>
    private Shape insertOleImage(byte[] imageBytes) throws Exception
    {
        if (imageBytes == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "imageBytes");

        Shape shape = new Shape(mDoc, ShapeMarkupLanguage.VML);
        shape.setShapeType(ShapeType.IMAGE);

        shape.setRunPr(getRunPrCopy());
        shape.getImageData().setImageBytes(imageBytes);
        shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.DEFAULT);
        shape.setLeft(0.0);
        shape.setRelativeVerticalPosition(RelativeVerticalPosition.TEXT_FRAME_DEFAULT);
        shape.setTop(0.0);
        shape.setWrapType(WrapType.INLINE);

        // We presume we are inserting a top level shape, it has to be inserted into the tree
        // for the shape size validation below to work properly.
        insertNode(shape);

        // Set shape size either to the image size or to the specified size.
        // If auto-setting to the image size, limit the size to max allowed shape size to avoid throwing.
        ImageSize imageSize = shape.getImageData().getImageSize();
        shape.setSizeSafe(imageSize.getWidthPoints(), imageSize.getHeightPoints());

        return shape;
    }

    /// <summary>
    /// Moves the cursor to the child of the composite node at the specified position in text.
    /// </summary>
    private void moveToCharIndex(CompositeNode node, int charIdx)
    {
        if (charIdx >= 0)
            moveToCharIndexForward(node, charIdx);
        else
            moveToCharIndexBackward(node, charIdx);
    }

    /// <summary>
    /// Moves the cursor to the child of the composite node at the specified position from beginning of the node.
    /// </summary>
    private void moveToCharIndexForward(CompositeNode node, int charIdx)
    {
        DocumentPosition position = DocumentPosition.createPositionBefore(node);
        Node currentNode = null;

        while (currentNode != node)
        {
            position.move(null, true, true, true, false, true);
            currentNode = position.getNode();
            if (position.isStart() &&
                // Prefer position before an inline story than before the first node inside the story.
                !(currentNode.isComposite() && (currentNode.getNodeLevel() == NodeLevel.INLINE) && (charIdx == 0)))
            {
                continue;
            }

            int textLength = currentNode.isComposite()
                ? ((CompositeNode)currentNode).getEndText().length()
                : currentNode.getTextLength();

            // Skip this zero-length composite node end and go to the next node, if this is not the node in which
            // the position is being set.
            if (!position.isStart() && (textLength == 0) && currentNode.isComposite() && (currentNode != node))
                continue;

            int oldCharIdx = charIdx;
            charIdx -= textLength;
            if ((oldCharIdx == 0) || (charIdx < 0))
            {
                if (!position.isStart() && (currentNode == node) && (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG))
                {
                    moveToStructuredDocumentTagEnd((StructuredDocumentTag)node);
                }
                else
                {
                    moveTo(null, currentNode);
                    mCharOffset = oldCharIdx;
                }

                return;
            }
        }

        throw new IllegalStateException("The character index is too large.");
    }

    private void moveToStructuredDocumentTagEnd(StructuredDocumentTag structuredDocumentTag)
    {
        setCursor(structuredDocumentTag);
        mIsAtEndOfStructuredDocumentTag = true;
        mCharOffset = 0;

        // Mimic MS Word in setting inserting text font.

        RunPr runPr = structuredDocumentTag.getContentsRunPr();
        switch (structuredDocumentTag.getNodeLevel())
        {
            case NodeLevel.INLINE:
            {
                Node inlineLevelNode = structuredDocumentTag.getLastNonAnnotationChild();
                while ((inlineLevelNode != null) && !(inlineLevelNode instanceof Inline))
                    inlineLevelNode = inlineLevelNode.getPreviousSibling();

                if (inlineLevelNode != null)
                    runPr = ((Inline)inlineLevelNode).getRunPr();

                break;
            }
            case NodeLevel.BLOCK:
            {
                Paragraph paragraph = (Paragraph)structuredDocumentTag.getChild(NodeType.PARAGRAPH, -1, true);
                if (paragraph != null)
                    runPr = paragraph.getParagraphBreakRunPr();

                break;
            }
            default:
                // No need to set font.
                return;
        }

        setFont(runPr, true);
    }

    /// <summary>
    /// Moves the cursor to the child of the composite node at the specified position from ending of the node (the
    /// position is a negative number).
    /// </summary>
    private void moveToCharIndexBackward(CompositeNode node, int charIdx)
    {
        DocumentPosition position = DocumentPosition.createPositionAfter(node);

        while (position.getNode() != node || !position.isStart())
        {
            Node currentNode = position.getNode();
            int textLength = currentNode.isComposite()
                ? ((CompositeNode)currentNode).getEndText().length()
                : currentNode.getTextLength();

            charIdx += textLength;
            if (charIdx >= 0)
            {
                moveTo(null, currentNode);
                mCharOffset = charIdx;
                return;
            }

            while (position.move(null, false, true, true, false, true) &&
                   position.isStart() &&
                   (position.getNode() != node))
            {
                // Move if the current position is at the beginning of a node other than 'node'.
            }
        }

        throw new IllegalStateException("The character index is too small.");
    }

    /// <summary>
    /// Splits the current run at the character offset if it has been specified by the previous execution of a method
    /// that sets a position in text.
    /// </summary>
    private void ensureCurrentRunIsSplitByCharOffset()
    {
        int offset = mCharOffset;
        if (offset == 0)
            return;

        // Reset offset even if the current node cannot be split: to not use the value on cursor change.
        mCharOffset = 0;

        Run run = ms.as(getCurrentNode(), Run.class);
        if (run != null)
            run.splitBefore(offset);
    }

    private Run insertRun(String text)
    {
        Run newRunNode = new Run(mDoc, text, getRunPrCopy());
        insertNode(newRunNode);
        ComplexScriptRunUpdater.doProcess(newRunNode);

        return newRunNode;
    }

    /// <summary>
    /// True, when current position of the document builder is inside group shape.
    /// </summary>
    boolean isInGroupShape()
    {
        return !isAtEndOfParagraph() && (getCurrentNode().getParentNode().getNodeType() == NodeType.GROUP_SHAPE);
    }

    /*ParagraphBreakCharReplacement*/int getParagraphBreakCharReplacement() { return mParagraphBreakCharReplacement; }
    void setParagraphBreakCharReplacement(/*ParagraphBreakCharReplacement*/int value) { mParagraphBreakCharReplacement = value; }

    /// <summary>
    /// Gets the node that is currently selected in this DocumentBuilder.
    /// </summary>
    /// <remarks>
    /// <p><see cref="CurrentNode"/> is a cursor of <see cref="DocumentBuilder"/> and points to a <see cref="Node"/>
    /// that is a direct child of a <see cref="Paragraph"/>. Any insert operations you perform using
    /// <see cref="DocumentBuilder"/> will insert before the <see cref="CurrentNode"/>.</p>
    /// <p>When the current paragraph is empty or the cursor is positioned just
    /// before the end of a paragraph or structured document tag, <see cref="CurrentNode"/> returns <c>null</c>.</p>
    /// <seealso cref="CurrentParagraph"/>
    /// <seealso cref="CurrentStructuredDocumentTag"/>
    /// </remarks>
    public Node getCurrentNode() { return (isAtEndOfParagraph() || isAtEndOfStructuredDocumentTag()) ? null : getCursor(); }

    /// <summary>
    /// Gets the paragraph that is currently selected in this <see cref="DocumentBuilder"/>.
    /// </summary>
    /// <remarks>
    /// <see cref="CurrentNode"/>
    /// </remarks>
    public Paragraph getCurrentParagraph()
    {
        if (isAtEndOfParagraph())
            return (Paragraph)getCursor();
        else
            return (Paragraph)getCursor().getAncestor(NodeType.PARAGRAPH);
    }

    /// <summary>
    /// Gets the structured document tag that is currently selected in this <see cref="DocumentBuilder"/>.
    /// </summary>
    public StructuredDocumentTag getCurrentStructuredDocumentTag()
    {
        if (isAtEndOfStructuredDocumentTag())
            return (StructuredDocumentTag)getCursor();
        else
            return (StructuredDocumentTag)getCursor().getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG);
    }

    /// <summary>
    /// Gets the story that is currently selected in this <see cref="DocumentBuilder"/>.
    /// </summary>
    public Story getCurrentStory()
    {
        return (Story)getCursor().getAncestor(Story.class);
    }

    /// <summary>
    /// Gets the section that is currently selected in this <see cref="DocumentBuilder"/>.
    /// </summary>
    public Section getCurrentSection() { return (Section)getCurrentStory().getParentNode(); }

    /// <summary>
    /// Returns current table builder or <c>null</c> if not building a table.
    /// </summary>
    private TableBuilder getCurTableBuilder() { return StackExtension.top(mTableBuilders); }

    /// <summary>
    /// Gets the cell that is currently selected in this DocumentBuilder.
    /// </summary>
    private Cell getCurrentCell()
    {
        if (getCurrentParagraph() == null)
            return null;

        return getCurrentParagraph().getParentCell();
    }

    /// <summary>
    /// Gets the parent node of the cursor position.
    /// </summary>
    private CompositeNode getCurrentParent()
    {
        if (isAtEndOfParagraph() || isAtEndOfStructuredDocumentTag())
            return (CompositeNode)getCursor();
        else
            return getCursor().getParentNode();
    }

    /// <summary>
    /// Gets the next node of the cursor position.
    /// </summary>
    private Node getReferenceNode()
    {
        if (isAtEndOfParagraph() || isAtEndOfStructuredDocumentTag())
            return null;
        else
            return getCursor();
    }

    /// <summary>
    /// Gets stack of saved run character formatting. Created on demand.
    /// </summary>
    private msStack<RunPr> getRunPrStack()
    {
        if (mRunPrStack == null)
            mRunPrStack = new msStack<RunPr>();
        return mRunPrStack;
    }

    /// <summary>
    /// Gets stack of saved paragraph attributes.
    /// </summary>
    private msStack<ParaPr> getParaPrStack()
    {
        if (mParaPrStack == null)
            mParaPrStack = new msStack<ParaPr>();
        return mParaPrStack;
    }

    /// <summary>
    /// Gets stack of saved character formatting. Created on demand.
    /// </summary>
    private msStack<DocumentBuilder.FontPr> getFontPrs()
    {
        if (mFontPrs == null)
            mFontPrs = new msStack<DocumentBuilder.FontPr>();
        return mFontPrs;
    }

    //>>>>>>>> #region  IRunAttrSource

    /// <summary>
    /// Implements IRunAttrSource to provide access to the current run attributes in the document builder.
    ///
    /// DocumentBuilder has its own copy of run attributes, not connected to the model
    /// to allow user to set font formatting before inserting text into the model.
    ///
    /// The fact that the builder has its own disconnected copy of run attributes
    /// and provides the Font property requires the builder to perform proper
    /// run attribute resolution using character and paragraph styles.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int fontAttr)
    {
        return mRunPr.getDirectAttr(fontAttr);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mRunPr.getDirectAttr(key, revisionsView);
    }

    /// <summary>
    /// The logic in this method is same as in Run.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int fontAttr)
    {
        //Try to find the value specified in the character style or one of the based on character styles.
        Object value = getFont().getStyle().getFontAttr(fontAttr, false);
        if (value != null)
            return value;

        //Get the value from the paragraph styles or from the default attributes.
        return getParagraphFormat().getStyle().getFontAttr(fontAttr, true);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int fontAttr, Object value)
    {
        mRunPr.setAttr(fontAttr, value);

        if (getCurrentParagraph() == null)
            return;

        // andrnosk: WORDSNET-9823 Mimic MS Word behavior, apply run attrs to paragraph break when we at the end of paragraph or it is empty.
        if (isAtEndOfParagraph() || getCurrentParagraph().isEmptyOrContainsOnlyCrossAnnotation())
            getCurrentParagraph().getParagraphBreakRunPr().setAttr(fontAttr, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        mRunPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        mRunPr.clear();

        if (getCurrentParagraph() == null)
            return;

        // WORDSNET-10765 MS Word clears paragraph break when cursor is at the end of paragraph or it is empty.
        if (isAtEndOfParagraph() || getCurrentParagraph().isEmptyOrContainsOnlyCrossAnnotation())
            getCurrentParagraph().getParagraphBreakRunPr().clear();
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IParaAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key)
    {
        return getParaPr().getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getParaPr().getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchInheritedParaAttr(int key)
    {
        return (getCurrentParagraph() != null)
            ? getCurrentParagraph().fetchInheritedParaAttr(key, RevisionsView.ORIGINAL)
            : getDocument().getStyles().getDefaultParaPr().fetchAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchParaAttr(int key)
    {
        IParaAttrSource paraAttrSource = this;
        Object value = paraAttrSource.getDirectParaAttr(key);
        return (value != null) ? value : paraAttrSource.fetchInheritedParaAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/setParaAttr(int key, Object value)
    {
        if (getCurrentParagraph() != null)
            ((IParaAttrSource)getCurrentParagraph()).setParaAttr(key, value);
        else
            getParaPr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/removeParaAttr(int key)
    {
        getParaPr().remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/clearParaAttrs()
    {
        getParaPr().clear();
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IRowAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/getDirectRowAttr(int key)
    {
        return getTablePr().getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/fetchRowAttr(int key)
    {
        return getTablePr().fetchAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/fetchInheritedRowAttr(int key)
    {
        return getTablePr().fetchInheritedAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/setRowAttr(int key, Object value)
    {
        getTablePr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/clearRowAttrs()
    {
        ((IRowAttrSource)getTablePr()).clearRowAttrs();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/resetToDefaultAttrs()
    {
        // WORDSNET-5727 reset to default attributes. Clearing attributes results in ugly rendering.
        getTablePr().clear();
        TablePr.createMSWordLooking().expandTo(getTablePr());

        // DM 5727 comments:
        // Actually, IRowAttrSource.ResetToDefaultAttrs() was added per 5727 to support RowFormat.ClearFormatting().
        // RowFormat.ClearFormatting should revert to the default attributes values, according to the API reference.
        // However, clearing the attributes does not produce this result, as default attributes do have some values.

        // This implementation is different from Font.ClearFormatting() where clearing the attributes is sufficient.
        // This is because on clearing the font attributes, the missing attributes are resolved via styles.
        // There is not implemented for table styles and row attributes.
        // Per 5727, I considered introducing row attributes resolution via table styles too risky.
        // Besides, it would break HtmlTableReader which really needs empty attributes.
        // With style resolution, they will never be empty, because "Table Normal" style is a global default,
        // and some attributes are defined in "Table Normal".
        // But some day we'll need to do proper row attributes resolution via table styles.
        // End of 5727 comments.
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  ICellAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/getDirectCellAttr(int key)
    {
        return getCellPr().getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/fetchCellAttr(int key)
    {
        return getCellPr().fetchAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/fetchInheritedCellAttr(int key)
    {
        return getCellPr().fetchInheritedAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ICellAttrSource.*/setCellAttr(int key, Object value)
    {
        // Border is complex attribute. We should explicitly set its parent because DocumentBuilder
        // can have various cellPr source depending on where is builder's Cursor at the moment.
        Border border = ms.as(value, Border.class);
        if (border != null)
        {
            CellFormat cellFormat = (getCurrentCell() != null) ? getCurrentCell().getCellFormat() : getCellFormat();
            border.setParent(cellFormat);
            border = border;
        }

        getCellPr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ICellAttrSource.*/clearCellAttrs()
    {
        getCellPr().clear();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Provides safe cursor position. In case of cursor node was removed, tries to get its parent as cursor.
    /// </summary>
    /// <remarks>
    /// WORDSNET-6144 This workaround tries to recover cursor position
    /// if node at cursor is removed by external procedure.
    /// In this CR node is removed by field merge operation but I think
    /// there are many possible way to do this.
    ///
    /// Better approach is to use INodeChangingCallback here to update cursor runtime but I found
    /// that it's not easy to detect that node is really removed or it just
    /// in changing process during, for example, DocumentBuilder.InsertBreakCore method.
    ///
    /// So since it is rare case and it is's not decided yet should we update DocumentBuilder
    /// with runtime DOM changes or it's better for customer to create new DocumentBuilder
    /// instance we decided to put this "workaround" first.
    /// </remarks>
    private Node getCursor()
    {
        Node cursor;

        if ((mCursor != null) && (mCursor.getParentNode() == null))
            cursor = mCursorParentBackup;
        else
            cursor = mCursor;

        if ((cursor != null) && (cursor.getNodeLevel() == NodeLevel.SECTION_STORY))
        {
            Paragraph paragraph = ((Story)cursor).getFirstParagraph();
            if (paragraph == null)
                // get first paragraph from current section
                cursor = ((Section)cursor.getAncestor(NodeType.SECTION)).getBody().getFirstParagraph();
            else
                cursor = paragraph;
        }

        if (cursor == null)
            cursor = getDocument().getFirstSection().getBody().getFirstParagraph();

        mIsAtEndOfStructuredDocumentTag = mIsAtEndOfStructuredDocumentTag &&
            (cursor != null) && (cursor.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG);

        return cursor;
    }
    private void setCursor(Node value)
    {
        mCursor = value;

        if (mCursor != null)
        {
            mCursorParentBackup = mCursor.getParentNode();
        }

        // mIsAtEndOfStructuredDocumentTag is set only in MoveToStructuredDocumentTagEnd after setting Cursor.
        mIsAtEndOfStructuredDocumentTag = false;
    }

    /// <summary>
    /// Returns current paragraph formatting depending on the current position within document.
    /// </summary>
    /// <remarks>
    /// When we are inside a paragraph this exposes formatting of the paragraph, otherwise we return formatting from
    /// <see cref="mParaPr"/>.
    /// </remarks>
    private ParaPr getParaPr()
    {
        if (getCurrentParagraph() != null)
            return getCurrentParagraph().getParaPr();

        if (mParaPr == null)
            mParaPr = new ParaPr();

        return mParaPr;
    }

    /// <summary>
    /// Returns current row formatting depending on the current position within document.
    /// </summary>
    /// <remarks>
    /// When we are inside a row this exposes formatting of this row, otherwise we return formatting from <see cref="DocumentBuilder.mTablePr"/>.
    /// </remarks>
    private TablePr getTablePr()
    {
        // We are not started any row yet. Hence we can't change formatting of rows and we get it from the builder.
        if ((getCurTableBuilder() != null) && (getCurTableBuilder().getTableState() == TableState.IN_TABLE))
            return mTablePr;
        // We have started a row.
        // If we are inside of any cell it means we should work with row formatting of its table.
        if ((getCurrentParagraph() != null) && getCurrentParagraph().isInCell())
            return getCurrentParagraph().getParentRow().getTablePr();

        return mTablePr;
    }

    /// <summary>
    /// Returns current cell formatting depending on the current position within document.
    /// </summary>
    /// <remarks>
    /// When we are inside a cell this exposes formatting of this cell, otherwise we return formatting from <see cref="DocumentBuilder.mCellPr"/>.
    /// </remarks>
    private CellPr getCellPr()
    {
        // We are not started any cell yet. Hence we can't change formatting of cells and we get it from the builder.
        if ((getCurTableBuilder() != null) && (getCurTableBuilder().getTableState() != TableState.IN_CELL))
            return mCellPr;
        // We have started a cell.
        // If we are inside of any cell it means we should work with cell formatting of its table.
        if ((getCurrentParagraph() != null) && getCurrentParagraph().isInCell())
            return getCurrentParagraph().getParentCell().getCellPr();

        return mCellPr;
    }

    /// <summary>
    /// Indicates that cursor is at the last paragraph of the last SDT.
    /// </summary>
    /// <remarks>
    /// We apply different logic here when insert paragraph to allow to append content after SDT.
    /// </remarks>
    private boolean getAtLastParagraphOfLastSdt()
    {
        Paragraph currentPara = getCurrentParagraph();
        CompositeNode parentNode = currentPara.getParentNode();

        if (parentNode.getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG)
            return false;

        if (!parentNode.isLastChild())
            return false;

        if (!currentPara.isLastChild())
            return false;

        return isAtEndOfParagraph() && !isAtEndOfStructuredDocumentTag();
    }

    /// <summary>
    /// Stores font properties.
    /// </summary>
    private static class FontPr
    {
        public FontPr(RunPr runPr, RunPr paraBreakRunPr)
        {
            mRunPr = runPr;
            mParaBreakRunPr = paraBreakRunPr;
        }

        public RunPr getRunPr()
        {
            return mRunPr;
        }

        public RunPr getParaBreakRunPr()
        {
            return mParaBreakRunPr;
        }

        private /*final*/ RunPr mRunPr;
        private /*final*/ RunPr mParaBreakRunPr;
    }

    private Document mDoc;

    /// <summary>
    /// Additional options for the document building process.
    /// </summary>
    private /*final*/ DocumentBuilderOptions mDocumentBuilderOptions;

    /// <summary>
    /// The cursor position. Can be an inline node or a paragraph.
    /// When points to an inline node, inserts will be done before it.
    /// When points to a paragraph, inserts will be at the end of the paragraph.
    /// </summary>
    private Node mCursor;

    /// <summary>
    /// Parent node of node was last time cursor moved to.
    /// </summary>
    /// <remarks>
    /// See DocumentBuilder.Cursor property for details.
    /// </remarks>
    private Node mCursorParentBackup;

    /// <summary>
    /// This is our own run attributes, therefore changing font properties does not change
    /// them in the document, but changes them in this collection.
    /// </summary>
    private RunPr mRunPr;
    /// <summary>
    /// The facade object that exposes the run attributes.
    /// </summary>
    private Font mFont;

    /// <summary>
    /// The stack of saved run attributes.
    /// </summary>
    /// <remarks>Always use the getter property as it creates this on demand.</remarks>
    private msStack<RunPr> mRunPrStack;

    /// <summary>
    /// The stack of saved paragraph attributes.
    /// </summary>
    /// <remarks>Always use the getter property as it creates this on demand.</remarks>
    private msStack<ParaPr> mParaPrStack;

    /// <summary>
    /// Always use the getter property as it creates this on demand.
    /// </summary>
    private msStack<DocumentBuilder.FontPr> mFontPrs;

    /// <summary>
    /// Row formatting used when we are starting a new table.
    /// See more details in <see cref="TablePr"/> property.
    /// </summary>
    private /*final*/ TablePr mTablePr = TablePr.createMSWordLooking();
    /// <summary>
    /// Cell formatting used when we are starting a new table.
    /// See more details in <see cref="CellPr"/> property.
    /// </summary>
    private CellPr mCellPr = new CellPr();

    private ParaPr mParaPr;

    /// <summary>
    /// The facade objects that expose the table row and cell attributes.
    /// </summary>
    private RowFormat mRowFormat;
    private CellFormat mCellFormat;

    /// <summary>
    /// The facade object that exposes the paragraph formatting.
    /// </summary>
    private ParagraphFormat mParagraphFormat;

    private msStack<TableBuilder> mTableBuilders;
    private /*ParagraphBreakCharReplacement*/int mParagraphBreakCharReplacement = ParagraphBreakCharReplacement.PARAGRAPH;

    /// <summary>
    /// The last created EditableRange Id, used to end EditableRange which was previously started.
    /// </summary>
    private int mLastEditableRangeId = UNINITIALIZED;

    /// <summary>
    /// Offset of the current position within the run.
    /// </summary>
    private int mCharOffset;

    private boolean mIsAtEndOfStructuredDocumentTag;

    private static final int UNINITIALIZED = -1;
}

