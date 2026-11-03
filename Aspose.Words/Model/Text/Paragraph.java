// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ArrayUtil;
import com.aspose.DrColor;
import com.aspose.HSLColor;
import com.aspose.StringUtil;
import com.aspose.IntList;
import com.aspose.words.ref.RefDouble;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Represents a paragraph of text.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-paragraphs/">Working with Paragraphs</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="Paragraph"/> is a block-level node and can be a child of classes derived from
/// <see cref="Aspose.Words.Story"/> or <see cref="Aspose.Words.InlineStory"/>.</p>
///
/// <p><see cref="Paragraph"/> can contain any number of inline-level nodes and bookmarks.</p>
///
/// <p>The complete list of child nodes that can occur inside a paragraph consists of
/// <see cref="BookmarkStart"/>, <see cref="BookmarkEnd"/>,
/// <see cref="Fields.FieldStart"/>, <see cref="Fields.FieldSeparator"/>,
/// <see cref="Fields.FieldEnd"/>, <see cref="Fields.FormField"/>,
/// <see cref="Comment"/>, <see cref="Footnote"/>,
/// <see cref="Run"/>, <see cref="SpecialChar"/>,
/// <see cref="Aspose.Words.Drawing.Shape"/>, <see cref="Aspose.Words.Drawing.GroupShape"/>,
/// <see cref="Aspose.Words.Markup.SmartTag"/>.</p>
///
/// <p>A valid paragraph in Microsoft Word always ends with a paragraph break character and
/// a minimal valid paragraph consists just of a paragraph break. The <see cref="Paragraph"/>
/// class automatically appends the appropriate paragraph break character at the end
/// and this character is not part of the child nodes of the <see cref="Paragraph"/>, therefore
/// a <see cref="Paragraph"/> can be empty.</p>
///
/// <p>Do not include the end of paragraph <see cref="ControlChar.ParagraphBreak"/>
/// or end of cell <see cref="ControlChar.Cell"/> characters inside the text of
/// the paragraph as it might make the paragraph invalid when the document is opened in Microsoft Word.</p>
/// </remarks>
public class Paragraph extends CompositeNode<Node> implements
    IParaAttrSource,
    IRunAttrSource,
    ITrackableNode
{
    /// <summary>
    /// Initializes a new instance of the <see cref="Paragraph"/> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Paragraph"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    /// <p>To append <see cref="Paragraph"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the story where you want the paragraph inserted.</p>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Paragraph(DocumentBase doc)
    {
    	this(doc, new ParaPr(), new RunPr());
	
    }

    Paragraph(DocumentBase doc, ParaPr paraPr, RunPr runPr)
    {
    	super(doc);
	
        // RK I had nasty problems with importers creating paragraphs with null properties
        // and forgetting to initialize them when reading some corrupted documents.
        // Now it is disallowed to create paragraphs with null properties.
        Debug.assertTrue(paraPr != null);
        Debug.assertTrue(runPr != null);

        mParaPr = paraPr;
        mParagraphBreakRunPr = runPr;
    }

    /// <summary>
    /// Returns <see cref="NodeType.Paragraph"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.PARAGRAPH; }

    /// <summary>
    /// Retrieves the parent section-level story that can be <see cref="Body"/> or <see cref="HeaderFooter"/>.
    /// </summary>
    public Story getParentStory()
    {
        return (Story)getAncestor(Story.class);
    }

    /// <summary>
    /// Retrieves the parent <see cref="Section"/> of the paragraph.
    /// </summary>
    public Section getParentSection() { return (Section)getAncestor(NodeType.SECTION); }

    /// <summary>
    /// True if this paragraph is an immediate child of <see cref="Cell"/>; false otherwise.
    /// </summary>
    public boolean isInCell() { return (getFirstNonMarkupParentNode() instanceof Cell); }

    /// <summary>
    /// True if this paragraph is the first paragraph in a Cell.
    /// </summary>
    boolean isStartOfCell()
    {
        CompositeNode parent = getParentNode();
        if (parent == null)
            return false;

        if (NodeUtil.isMarkupNode(parent))
        {
            // node can be first child of Sdt/CustomXml but it does not mean that it is first child of cell, so we check differently
            return isInCell() && isAncestorNode(getFirstNonMarkupParentNode().getFirstChild());
        }

        return isInCell() && (this == parent.getFirstNonMarkupCompositeDescendant());
    }

    /// <summary>
    /// True if this paragraph is the last paragraph in a <see cref="Cell"/>; false otherwise.
    /// </summary>
    public boolean isEndOfCell()
    {
        CompositeNode parent = getFirstNonMarkupParentNode();
        return (parent instanceof Cell) && (parent.getLastNonMarkupCompositeDescendant() == this);
    }

    /// <summary>
    /// True if this paragraph break is a Style Separator. A style separator allows one
    /// paragraph to consist of parts that have different paragraph styles.
    /// </summary>
    public boolean getBreakIsStyleSeparator()
    {
        return InlineHelper.getBool(mParagraphBreakRunPr, FontAttr.HIDDEN) &&
            InlineHelper.getBool(mParagraphBreakRunPr, FontAttr.SPECIAL_HIDDEN);
    }

    /// <summary>
    /// True if this paragraph is the first paragraph in the first cell of a row.
    /// </summary>
    boolean isStartOfRow() { return (isStartOfCell() && getParentCell().isFirstCell()); }

    /// <summary>
    /// True if this paragraph is the last paragraph in the last cell of a row.
    /// </summary>
    boolean isEndOfRow() { return (isEndOfCell() && getParentCell().isLastCell()); }

    boolean isStartOfTable() { return (isStartOfRow() && getParentRow().isFirstRow()); }

    boolean isEndOfTable() { return (isEndOfRow() && getParentRow().isLastRow()); }

    Cell getParentCell() { return ms.as(getFirstNonMarkupParentNode(), Cell.class); }

    Row getParentRow()
    {
        // WORDSNET-11447 IsInCell is true, but ParentCell is null when its child is cloned.
        return (getParentCell() != null) ? getParentCell().getParentRow() : null;
    }

    Table getParentTable()
    {
        // WORDSNET-11447 IsInCell is true, but ParentRow is null when its child is cloned.
        return (getParentRow() != null) ? getParentRow().getParentTable() : null;
    }

    /// <summary>
    /// True if this paragraph is an immediate (ignoring SDTs) child of <see cref="Drawing.Shape"/>.
    /// </summary>
    boolean isInShape() { return getFirstNonMarkupParentNode() instanceof Shape; }

    /// <summary>
    /// True if this paragraph is the last paragraph in the <see cref="Body"/> (main text story) of a <see cref="Section"/>; false otherwise.
    /// </summary>
    public boolean isEndOfSection()
    {
        Story parentStory = getParentStory();
        return ((parentStory != null) &&
            (parentStory.getStoryType() == StoryType.MAIN_TEXT) &&
            (this == parentStory.getLastNonMarkupCompositeDescendant()));
    }

    /// <summary>
    /// True if this paragraph is the last paragraph in the <see cref="HeaderFooter"/> (main text story) of a <see cref="Section"/>; false otherwise.
    /// </summary>
    public boolean isEndOfHeaderFooter()
    {
        Story parentStory = getParentStory();
        return (parentStory instanceof HeaderFooter && parentStory.getLastNonMarkupCompositeDescendant() == this);
    }

    /// <summary>
    /// True if this paragraph is the last paragraph in the last section of the document.
    /// </summary>
    public boolean isEndOfDocument()
    {
        if (!isEndOfSection())
            return false;

        // DV NodeType.System is used during merge. We don't need it to be considered as other nodes.
        for (Node node = getDocument().getLastChild(); node != null; node = node.getPreviousSibling())
            if (node instanceof Section)
                return (node == getParentSection());

        // This happens if document has no sections which shall never happen.
        return true;
    }

    boolean isSectionBreakParagraph()
    {
        if (getNextNonAnnotationSibling() != null)
            return false;

        if (getParentNode().getNodeType() != NodeType.BODY)
            return false;

        if (getParentSection().isLastChild())
            return false;

        return true;
    }

    /// <summary>
    /// Provides access to the paragraph formatting properties.
    /// </summary>
    public ParagraphFormat getParagraphFormat()
    {
        if (mParagraphFormat == null)
            mParagraphFormat = new ParagraphFormat(this, getDocument().getStyles());
        return mParagraphFormat;
    }

    /// <summary>
    /// Provides access to the list formatting properties of the paragraph.
    /// </summary>
    public ListFormat getListFormat()
    {
        if (mListFormat == null)
            mListFormat = new ListFormat(this, this, getDocument().getLists());
        return mListFormat;
    }

    /// <summary>
    /// Provides access to the frame formatting properties.
    /// </summary>
    public FrameFormat getFrameFormat()
    {
        if (mFrameFormat == null)
            mFrameFormat = new FrameFormat(this);
        return mFrameFormat;
    }

    /// <summary>
    /// Gets a <see cref="ListLabel"/> object that provides access to list numbering value and formatting
    /// for this paragraph.
    /// </summary>
    public ListLabel getListLabel()
    {
        if (mListLabel == null)
            mListLabel = new ListLabel(this);
        return mListLabel;
    }

    /// <summary>
    /// Provides access to the typed collection of pieces of text inside the paragraph.
    /// </summary>
    public RunCollection getRuns()
    {
        if (mRuns == null)
            mRuns = new RunCollection(this);
        return mRuns;
    }

    /// <summary>
    /// Provides access to the font formatting of the paragraph break character.
    /// </summary>
    public Font getParagraphBreakFont() { return new Font(this, getDocument()); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsInsertRevision"]/*'/>
    public boolean isInsertRevision() { return mParagraphBreakRunPr.hasInsertRevision(); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsDeleteRevision"]/*'/>
    public boolean isDeleteRevision() { return mParagraphBreakRunPr.hasDeleteRevision(); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveFromRevision"]/*'/>
    public boolean isMoveFromRevision() { return mParagraphBreakRunPr.hasMoveFromRevision(); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveToRevision"]/*'/>
    public boolean isMoveToRevision() { return mParagraphBreakRunPr.hasMoveToRevision(); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsFormatRevision"]/*'/>
    public boolean isFormatRevision() { return mParaPr.hasFormatRevision(); }

    /// <summary>
    /// Gets the paragraph style assigned to this paragraph.
    /// This sometimes is more efficient than using ParagraphFormat.Style.
    /// </summary>
    Style getParagraphStyle() { return getParagraphStyle(RevisionsView.ORIGINAL); }

    Style getParagraphStyle(/*RevisionsView*/int view)
    {
        Object value = mParaPr.getDirectAttr(ParaAttr.ISTD, view);
        int istd = (value != null) ? (/*int*/Integer)value : StyleIndex.NORMAL;
        return getDocument().getStyles().fetchByIstd(istd, StyleIndex.NORMAL);
    }

    /// <summary>
    /// Gets the character style assigned to the paragraph break character.
    /// </summary>
    Style getParagraphBreakCharacterStyle() { return getDocument().getStyles().fetchByIstd(mParagraphBreakRunPr.getIstd(), StyleIndex.DEFAULT_PARAGRAPH_FONT); }

    ParaPr getParaPr() { return mParaPr; }
    void setParaPr(ParaPr value)
    {
        Debug.assertTrue(value != null);
        mParaPr = value;
    }

    // WORDSNET-7485
    void cloneListLabelStringAndValueIfNeeded(Paragraph other)
    {
        if ((other.getListLabel().getLabelFragments() != null) && (other.getListLabel().getNumberState() != null))
        {
            getListLabel().setLabelStringAndValue(other.getListLabel().getLabelFragments(), other.getListLabel().getLabelArabicNumbers(),
                other.getListLabel().getNumberState().snapshot(), RevisionsView.ORIGINAL);
        }
        if ((other.getListLabel().getLabelFragmentsFinal() != null) && (other.getListLabel().getNumberStateFinal() != null))
        {
            getListLabel().setLabelStringAndValue(other.getListLabel().getLabelFragmentsFinal(), other.getListLabel().getLabelArabicNumbersFinal(),
                other.getListLabel().getNumberStateFinal().snapshot(), RevisionsView.FINAL);
        }
    }

    void resetListLabel(/*RevisionsView*/int view)
    {
        if(mListLabel != null)
            mListLabel.setLabelStringAndValue(null, null, null, view);
    }

    /// <summary>
    /// Gets or sets the font formatting of the paragraph mark.
    /// </summary>
    RunPr getParagraphBreakRunPr() { return mParagraphBreakRunPr; }
    void setParagraphBreakRunPr(RunPr value)
    {
        Debug.assertTrue(value != null);
        mParagraphBreakRunPr = value;
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Paragraph lhs = (Paragraph)super.deepClone(isCloneChildren, cloningListener);

        ParaPr paraPr = AttrCollectionExtension.deepClone(mParaPr);
        lhs.mParaPr = paraPr;

        RunPr paragraphBreakRunPr = AttrCollectionExtension.deepClone(mParagraphBreakRunPr);
        lhs.mParagraphBreakRunPr = paragraphBreakRunPr;

        //Don't clone these because created on demand.
        lhs.mParagraphFormat = null;
        lhs.mFrameFormat = null;
        lhs.mListFormat = null;
        lhs.mListLabel = null;
        lhs.mRuns = null;

        return lhs;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitParagraphStart"/>, then calls <see cref="Node.Accept"/> for all child nodes
    /// of the paragraph and calls <see cref="DocumentVisitor.VisitParagraphEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }


    /// <summary>
    /// Accepts a visitor for visiting the start of the document's paragraph.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitParagraphStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the document's paragraph.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitParagraphEnd(this);
    }

    /// <summary>
    /// Gets the text of this paragraph including the end of paragraph character.
    /// </summary>
    ///<remarks>
    ///<p>The text of all child nodes is concatenated and the end of paragraph character is appended as follows:</p>
    ///<list type="bullet">
    ///<item>If the paragraph is the last paragraph of <see cref="Body"/>, then
    ///<see cref="ControlChar.SectionBreak"/> (\x000c) is appended.</item>
    ///<item>If the paragraph is the last paragraph of <see cref="Cell"/>, then
    ///<see cref="ControlChar.Cell"/> (\x0007) is appended.</item>
    ///<item>For all other paragraphs
    ///<see cref="ControlChar.ParagraphBreak"/> (\r) is appended.</item>
    ///</list>
    ///<p>The returned string includes all control and special characters as described in <see cref="ControlChar"/>.</p>
    ///</remarks>
    public /*override*/ String getText()
    {
        // RK Although resharper says this is redundant method override, I want to keep it here
        // by design. It has customized API documentation comment that applies to paragraph's GetText.
        return super.getText();
    }

    /// <summary>
    /// Returns fully expanded paragraph properties.
    /// </summary>
    ParaPr getExpandedParaPr(/*ParaPrExpandFlags*/int flags)
    {
        ParaPr dstParaPr = new ParaPr();
        expandParaPr(dstParaPr, flags);

        // Expand legacy tab stop if requested.
        if ((flags & ParaPrExpandFlags.EXPAND_LEGACY_TAB) != 0)
        {
            TabStop legacyTabStop = LegacyListFormattingConverter.calculateLegacyTab(this);
            if (legacyTabStop != null)
            {
                if (!dstParaPr.containsKey(ParaAttr.TAB_STOPS))
                    dstParaPr.setAttr(ParaAttr.TAB_STOPS, new TabStopCollection());

                dstParaPr.getTabStops().add(legacyTabStop);
            }
        }

        return dstParaPr;
    }

    /// <summary>
    /// Fully expands paragraph properties of this object into one collection.
    /// </summary>
    void expandParaPr(ParaPr dstParaPr, /*ParaPrExpandFlags*/int flags)
    {
        boolean globalDefaults = ((flags & ParaPrExpandFlags.GLOBAL_DEFAULTS) != 0);
        boolean documentDefaults = ((flags & ParaPrExpandFlags.DOCUMENT_DEFAULTS) != 0);
        boolean directFormatting = ((flags & ParaPrExpandFlags.NO_DIRECT_FORMATTING) == 0);
        // Reset flag immediately as we need to ignore direct formatting only for this entity.
        flags &= ~ParaPrExpandFlags.NO_DIRECT_FORMATTING;

        if (documentDefaults)
            getDocument().getStyles().getDefaultParaPr().expandTo(dstParaPr, globalDefaults);

        if ((flags & ParaPrExpandFlags.EXPAND_TABLE_STYLE) != 0)
        {
            // andrnosk: WORDSNET-6467 We need to expand table style too, because paragraphs inside table inherit table style.
            // Also AW does not need to skip direct formatting while expanding table style, otherwise direct formatting
            // for this style will be lost. See related test TestImportTableStyle.
            Cell cell = getParentCell();
            if (cell != null)
            {
                Table table = cell.getParentTable();
                if (table != null)
                {
                    TableStyle tableStyle = ms.as(table.getStyle(), TableStyle.class);

                    if (tableStyle != null)
                    {
                        tableStyle.expandParaPr(cell, dstParaPr);
                    }
                }
            }
        }

        ParaPr srcParaPr = mParaPr.getSourceParaPr(flags);

        // Make sure layout knows that paragraph had format revision so that it can be marked in output.
        if ((flags & ParaPrExpandFlags.REVISED) != 0 && mParaPr.hasFormatRevision())
            dstParaPr.setFormatRevision((FormatRevision)((IComplexAttr)mParaPr.getFormatRevision()).deepCloneComplexAttr());

        // Expand style formatting
        Style paraStyle = getDocument().getStyles().fetchByIstd(srcParaPr.getIstd(), StyleIndex.NORMAL);

        // Global defaults values and defaults values of the document already were expanded. So, AW does not need
        // to expand defaults values one more time.
        /*ParaPrExpandFlags*/int paraStyleExpFlags = documentDefaults ? flags & ~ParaPrExpandFlags.DOCUMENT_DEFAULTS : flags;
        paraStyleExpFlags = globalDefaults ? paraStyleExpFlags & ~ParaPrExpandFlags.GLOBAL_DEFAULTS : paraStyleExpFlags;

        paraStyle.expandParaPr(dstParaPr, paraStyleExpFlags);

        // Style has been expanded into dstParaPr so we have listId defined by style in it.
        // Check if direct formatting defines either different listId or different list level.
        if ((dstParaPr.getListId() != srcParaPr.getListId()) || (dstParaPr.getListLevel() != srcParaPr.getListLevel()))
        {
            // WORDSNET-11291 If srcPara has no list level defined we should inherit it from parent style.
            // Here I use the fact that we already have parent style expanded into dstParaPr and
            // don't need to calculate it once again.
            ParaPr newParaPr = srcParaPr;
            if(!srcParaPr.contains(ParaAttr.LIST_LEVEL) && dstParaPr.contains(ParaAttr.LIST_LEVEL))
            {
                // We should not modify original collection so create new temporary.
                newParaPr = AttrCollectionExtension.deepClone(srcParaPr);
                newParaPr.setAttr(ParaAttr.LIST_LEVEL, dstParaPr.get(ParaAttr.LIST_LEVEL));
            }

            getDocument().getLists().expandDirectList(newParaPr, dstParaPr);
        }

        if ((flags & ParaPrExpandFlags.EXPAND_HTML_BLOCKS) != 0)
        {
            if (mParaPr.contains(ParaAttr.HTML_BLOCK_ID))
                getDocument().getHtmlBlockCollection().expand(mParaPr, dstParaPr, (getParentTable() == null));
        }

        // Expand direct formatting
        if (directFormatting)
            srcParaPr.expandTo(dstParaPr);

        srcParaPr.frameInheritanceHack(dstParaPr);

        // AM. Maybe we should use more complex logic as ListCollection.ExpandDirectList does?
        if (srcParaPr.isExplicitlyNotListItem())
        {
            // Do not overwrite direct indent that might be set.
            if (!srcParaPr.contains(ParaAttr.FIRST_LINE_INDENT))
                dstParaPr.setFirstLineIndent(0);

            if (!srcParaPr.contains(ParaAttr.LEFT_INDENT))
                dstParaPr.setLeftIndent(0);

            // WORDSNET-21040 LeftIndentUnits and FirstLineIndentUnits should be also handled here.
            // Note, we need to remove only inherited values and
            // return zero, if global defaults were requested.
            if (!srcParaPr.contains(ParaAttr.LEFT_INDENT_UNITS))
            {
                if (globalDefaults)
                    dstParaPr.set(ParaAttr.LEFT_INDENT_UNITS, 0);
                else
                    dstParaPr.remove(ParaAttr.LEFT_INDENT_UNITS);

            }

            if (!srcParaPr.contains(ParaAttr.FIRST_LINE_INDENT_UNITS))
            {
                if (globalDefaults)
                    dstParaPr.set(ParaAttr.FIRST_LINE_INDENT_UNITS, 0);
                else
                    dstParaPr.remove(ParaAttr.FIRST_LINE_INDENT_UNITS);
            }
        }

        // WORDSNET-7115 It seems that Word ignores floating attributes for textboxes.
        if (isInShape())
            dstParaPr.removeFloatingAttrs();

        if ((flags & ParaPrExpandFlags.REMOVE_CLEAR_TAB_STOPS) != 0)
            dstParaPr.removeClearTabStops();
    }

    /// <summary>
    /// Fully expands font properties of the paragraph break.
    /// </summary>
    RunPr getExpandedParagraphBreakRunPr(/*RunPrExpandFlags*/int flags)
    {
        RunPr dstRunPr = new RunPr();
        expandParagraphBreakRunPr(dstRunPr, flags);
        return dstRunPr;
    }

    void expandParagraphBreakRunPr(RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        InlineHelper.expandRunPr(this, dstRunPr, flags);
    }

    /// <summary>
    /// Removes direct formatting that duplicates inherited formatting.
    /// </summary>
    void removeDuplicateDirectFormatting(/*RevisionsView*/int view)
    {
        // We enumerate attributes in the reverse order, because we remove duplicate attributes in place and want to make
        // sure this doesn't change indexes of other attributes that we haven't checked yet.
        for (int i = mParaPr.getCount() - 1; i >= 0; i--)
        {
            int key = mParaPr.getKey(i);

            // We don't want the style or list properties of the paragraph to change in the process,
            // because resolution of inherited formatting depends on these values.
            if ((key == ParaAttr.ISTD) || (key == ParaAttr.LIST_ID) || (key == ParaAttr.LIST_LEVEL))
            {
                continue;
            }

            Object value = mParaPr.getDirectAttr(key, view);
            Debug.assertTrue(value != null);
            Object inheritedReferenceValue = fetchInheritedParaAttr(key, view);
            if (value.equals(inheritedReferenceValue))
            {
                // Remove direct formatting. The inherited value will be used instead.
                mParaPr.removeAt(i);
            }
        }
    }

    /// <summary>
    /// True if this paragraph is the last paragraph in a Comment; false otherwise.
    /// </summary>
    boolean isEndOfComment()
    {
        CompositeNode parent = getFirstNonMarkupParentNode();
        return (parent instanceof Comment) && (this == parent.getLastNonMarkupCompositeDescendant());
    }

    /// <summary>
    /// True if this paragraph is the last paragraph in a Footnote; false otherwise.
    /// </summary>
    boolean isEndOfFootnote()
    {
        CompositeNode parent = getFirstNonMarkupParentNode();
        return (parent instanceof Footnote) && (this == parent.getLastNonMarkupCompositeDescendant());
    }

    /// <summary>
    /// True if this paragraph is the last paragraph of an endnote/footnote separator; false otherwise.
    /// </summary>
    boolean isEndOfNoteSeparator()
    {
        // All note separator types are stored under FootnoteSeparator in the model.
        // Despite the type name, this includes endnote and continuation separators as well.
        CompositeNode parent = getFirstNonMarkupParentNode();
        return (parent instanceof FootnoteSeparator) && (this == parent.getLastNonMarkupCompositeDescendant());
    }

    /// <summary>
    /// True if this paragraph is the last paragraph in a shape; false otherwise.
    /// </summary>
    boolean isEndOfTextbox()
    {
        CompositeNode parent = getFirstNonMarkupParentNode();
        return (parent instanceof Shape && parent.getLastNonMarkupCompositeDescendant() == this);
    }

    /// <summary>
    /// Calculates minimum and maximum line height in points and criterion whether it can change from inherited.
    /// Maximum is basically the biggest font size used in the paragraph. Maybe some other attributes should be analyzed.
    /// Note that like in MS Word, the font size of the paragraph mark is only taken into account
    /// when the paragraph is empty.
    /// Changing criterion is needed to properly output attributes with relative size (percentage).
    /// I don't like this patch. It would be better to calculate this all if only needed.
    /// This code doesn't consider list label if any. Maybe we need it too.
    /// </summary>
    void getMinMaxFontSize(/*out*/RefDouble minFontSize, /*out*/RefDouble maxFontSize, /*out*/RefBoolean sizeCanChange)
    {
        minFontSize.set((double)Integer.MAX_VALUE);
        maxFontSize.set((double)0.0);
        sizeCanChange.set(false);
        for (Node node = getFirstNonMarkupDescendant(); node != null; node = node.getNextNonMarkupNodeLimited())
        {
            // I should do this using interfaces. Looking at base classes is not really good.
            if (node instanceof Inline)
                considerInMinMaxFontSize((Inline)node, /*ref*/ minFontSize, /*ref*/ maxFontSize, /*ref*/ sizeCanChange);
        }

        // If the paragraph is empty, then the paragraph mark itself dictates the font size.
        // It also can have non-default size.
        if (maxFontSize.get() == 0)
        {
            // Passing this as IRunAttrSource means paragraph break properties.
            // I don't like this. Maybe it's better to make more explicit.
            considerInMinMaxFontSize(this, /*ref*/ minFontSize, /*ref*/ maxFontSize, /*ref*/ sizeCanChange);
        }
    }

    /// <summary>
    /// Considers one IRunAttrSource in the min/max font size and changing criterion.
    /// </summary>
    private static void considerInMinMaxFontSize(IRunAttrSource attrSource, /*ref*/RefDouble minFontSize, /*ref*/RefDouble maxFontSize, /*ref*/RefBoolean sizeCanChange)
    {
        Object directSize = attrSource.getDirectRunAttr(FontAttr.SIZE);
        double curSize = ConvertUtilCore.halfPointToPoint((/*int*/Integer)((directSize != null) ? directSize : attrSource.fetchInheritedRunAttr(FontAttr.SIZE)));

        minFontSize.set(Math.min(curSize, minFontSize.get()));
        maxFontSize.set(Math.max(curSize, maxFontSize.get()));

        if (!sizeCanChange.get())
        {
            sizeCanChange.set((directSize != null));
            if (!sizeCanChange.get())
            {
                Object istdObj = attrSource.getDirectRunAttr(FontAttr.ISTD);
                sizeCanChange.set((istdObj != null) && ((/*int*/Integer)istdObj != StyleIndex.DEFAULT_PARAGRAPH_FONT));
            }
        }
    }

    /// <summary>
    /// Returns the appropriate end of paragraph character.
    /// </summary>
    /*override*/ String getEndText()
    {
        if (isEndOfCell())
            return ControlChar.CELL;
        else if (isEndOfSection())
            return ControlChar.SECTION_BREAK;
        else
            return ControlChar.PARAGRAPH_BREAK;
    }

    /// <summary>
    /// Allows to insert inline-level elements.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        // WORDSNET-22783, 23418. Not allowing textboxes and line shapes to be child to textboxes.
        Shape childShape = ms.as(newChild, Shape.class);
        if ((childShape != null) &&
            (childShape.isTextBox() || (childShape.getShapeType() == ShapeType.LINE)))
        {
            Shape parentShape = (Shape)getAncestor(NodeType.SHAPE);
            if ((parentShape != null) && parentShape.isTextBox())
                return false;
        }

        return NodeUtil.isInlineLevelNode(newChild);
    }

    //>>>>>>>> #region  IParaAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key)
    {
        return mParaPr.getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mParaPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchInheritedParaAttr(int key)
    {
        return fetchInheritedParaAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchParaAttr(int key)
    {
        return fetchParaAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/setParaAttr(int key, Object value)
    {
        mParaPr.setAttr(key, value);

        switch (key)
        {
            case ParaAttr.SPACE_BEFORE_UNITS:
            case ParaAttr.SPACE_AFTER_UNITS:
            case ParaAttr.LEFT_INDENT_UNITS:
            case ParaAttr.RIGHT_INDENT_UNITS:
            case ParaAttr.FIRST_LINE_INDENT_UNITS:
                // Setting unit spacing/indent updates normal spacing/indent accordingly.
                updateFromUnits();
                break;

            default:
                break;
        }
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/removeParaAttr(int key)
    {
        mParaPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/clearParaAttrs()
    {
        mParaPr.clear();
    }

    //<<<<<<<< #endregion 

    Object fetchInheritedParaAttr(int key, /*RevisionsView*/int view)
    {
        Object value;

        // 1. RK When fetching inherited value we need to look at the list formatting first.
        ListLevel listLevel = getDocument().getLists().getDirectListLevel(getParaPr(), view);
        if (listLevel != null)
        {
            value = listLevel.getParaPr().getDirectAttr(key);
            if (value != null)
                return value;
        }
        else
        {
            Object listId = mParaPr.getDirectAttr(ParaAttr.LIST_ID, view);

            // WORDSNET-18137 (related to 1545) - Microsoft Word seems to be resetting to zero indents for a paragraph
            // that has bulleting explicitly turned off, but formatted with a style that has bullets.)
            // WORDSNET-21040 Note, the reason of such behavior is actually not related to bullets,
            // but rather just relates to the fact that listId explicitly set to zero.
            // This is a special case in Word described in 17.9.18 numId
            // (Numbering Definition Instance Reference) of ISO29500-1.
            if (((listId != null) && ((/*int*/Integer)listId == 0)) && ParaPr.isNoListAttr(key))
                return 0;
        }

        // 2. Look at the paragraph styles but skip document-wide or global defaults.
        value = getParagraphStyle(view).getParaAttr(key, view);
        if (value != null)
            return value;

        // 3. Return either from table styles or defaults.
        if (isTableStyleAffected())
        {
            TableStyle tableStyle = (TableStyle) getParentTable().getStyle();
            value = tableStyle.getParaAttr(key, getParentCell());
            if (value != null)
                return value;
        }

        return getDocument().getStyles().getDefaultParaPr().fetchAttr(key);
    }

    /// <summary>
    /// Indicates that paragraph formatting can be affected by table styles.
    /// </summary>
    private boolean isTableStyleAffected()
    {
        Cell cell = getParentCell();
        if (cell == null)
            return false;

        Row row = cell.getParentRow();
        if (row == null)
            return false;

        Table table = row.getParentTable();
        if (table == null)
            return false;

        TableStyle tableStyle = ms.as(getDocument().getStyles().getByIstd(getParentTable().getIstd(), false), TableStyle.class);

        if (tableStyle == null)
            return false;

        // TableNormal style has predefined attribute set and has no paragraph properties.
        if (tableStyle.getStyleIdentifier() == StyleIdentifier.TABLE_NORMAL)
            return false;

        return true;
    }

    Object fetchParaAttr(int key, /*RevisionsView*/int view)
    {
        Object value = mParaPr.getDirectAttr(key, view);
        return (value != null) ? value : fetchInheritedParaAttr(key, view);
    }

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return ((IRunAttrSource)this).getDirectRunAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mParagraphBreakRunPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        //TODO 2 Same code as in Inline, maybe can refactor.

        //Try to find the value specified in the character style or one of the based on character styles.
        Object value = getParagraphBreakCharacterStyle().getFontAttr(key, false);
        if (value != null)
            return value;

        //Get the value from the paragraph styles or from the default attributes.
        return getParagraphStyle().getFontAttr(key, true);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        mParagraphBreakRunPr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        mParagraphBreakRunPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        mParagraphBreakRunPr.clear();
    }

    //<<<<<<<< #endregion 

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getInsertRevision() { return getParagraphBreakRunPr().getInsertRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { getParagraphBreakRunPr().setInsertRevision(value); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return getParagraphBreakRunPr().getDeleteRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { getParagraphBreakRunPr().setDeleteRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision() { return getParagraphBreakRunPr().getMoveFromRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value) { getParagraphBreakRunPr().setMoveFromRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision() { return getParagraphBreakRunPr().getMoveToRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value) { getParagraphBreakRunPr().setMoveToRevision(value); }

    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        getParagraphBreakRunPr().remove(RevisionAttr.MOVE_FROM_REVISION);
        getParagraphBreakRunPr().remove(RevisionAttr.MOVE_TO_REVISION);
    }

    /// <summary>
    /// Conforming paragraphs in MS Word have same left and right indent and a few other
    /// attributes that cause MS Word to draw borders in a special way.
    /// MS Word does not draw the Top and Bottom borders between the conforming paragraphs,
    /// but draws the Between border between them.
    /// </summary>
    boolean isConformingWithNext() throws Exception
    {
        Node next = getNextNonMarkupCompositeLimited();
        if (!(next instanceof Paragraph))
            return false;

        Paragraph nextParagraph = (Paragraph)next;
        ParagraphFormat pf = getParagraphFormat();
        ParagraphFormat pfNext = nextParagraph.getParagraphFormat();

        if (pfNext.getPageBreakBefore())
            return false;

        if (nextParagraph.getParaPr().getHtmlBlockId() != getParaPr().getHtmlBlockId())
            return false;

        // We ignore (convert to zero) positive first line indent since it does not affect conformity
        // of the paragraphs. But we add negative first line indent (hanging) since MS Word treats
        // it like it affects left indent and therefore affects conformity.
        double thisLeft = pf.getLeftIndent() + Math.min(pf.getFirstLineIndent(), 0.0);
        double nextLeft = pfNext.getLeftIndent() + Math.min(pfNext.getFirstLineIndent(), 0.0);
        if (thisLeft != nextLeft)
            return false;

        if (pf.getRightIndent() != pfNext.getRightIndent())
            return false;

        if (!pf.getBorders().getByBorderType(BorderType.LEFT).equals(pfNext.getBorders().getByBorderType(BorderType.LEFT)))
            return false;

        if (!pf.getBorders().getByBorderType(BorderType.RIGHT).equals(pfNext.getBorders().getByBorderType(BorderType.RIGHT)))
            return false;

        if (!pf.getBorders().getByBorderType(BorderType.TOP).equals(pfNext.getBorders().getByBorderType(BorderType.TOP)))
            return false;

        if (!pf.getBorders().getByBorderType(BorderType.BOTTOM).equals(pfNext.getBorders().getByBorderType(BorderType.BOTTOM)))
            return false;

        if (!isSameFloatingPositioning(nextParagraph))
            return false;

        return true;
    }

    /// <summary>
    /// Returns true if this paragraph has same absolute positioning with the specified paragraph.
    /// </summary>
    boolean isSameFloatingPositioning(Paragraph rhs)
    {
        // In MS Word I hope text frame positioning attributes cannot be inherited from the style,
        // so it is cheaper to compare attributes in ParaPr directly, without looking at ParagraphFormat.
        return mParaPr.isSameFloatingPositioning(rhs.getParaPr());
    }

    /// <summary>
    /// Gets the first run.
    /// </summary>
    Run getFirstRun() { return (Run)getChild(NodeType.RUN, 0, false); }

    /// <summary>
    /// Gets the last run in the paragraph. The returned run can be empty.
    /// </summary>
    Run getLastRun()
    {
        return getLastRun(false);
    }

    /// <summary>
    /// Gets the last run in the paragraph optionally skipping empty ones.
    /// </summary>
    Run getLastRun(boolean skipEmpty)
    {
        Run lastRun = null;
        for (Node node = getFirstNonMarkupDescendant(); node != null; node = node.getNextNonMarkupNodeLimited())
        {
            if ((node.getNodeType() == NodeType.RUN) && (!skipEmpty || !msString.isNullOrEmpty(node.getText())))
                lastRun = (Run)node;
        }

        return lastRun;
    }

    boolean isInMainTextStory()
    {
        Story parentStory = getParentStory();
        return ((parentStory != null) && (parentStory.getStoryType() == StoryType.MAIN_TEXT));
    }

    /// <summary>
    /// True when the paragraph is an item in a bulleted or numbered list in original revision.
    /// </summary>
    public boolean isListItem() { return isListItemOriginal(); }

    /// <summary>
    /// Returns array of all tab stops applied to this paragraph, including applied indirectly by styles or lists.
    /// </summary>
    public TabStop[] getEffectiveTabStops()
    {
        ParaPr expandedParaPr = getExpandedParaPr(ParaPrExpandFlags.NORMAL);

        int tabStopCount = expandedParaPr.contains(ParaAttr.TAB_STOPS) ? expandedParaPr.getTabStops().getCount() : 0;

        TabStop[] tabStops = new TabStop[tabStopCount];

        for (int i = 0; i < tabStopCount; i++)
            tabStops[i] = expandedParaPr.getTabStops().get(i).deepClone();

        return tabStops;
    }

    /// <summary>
    /// Joins runs with the same formatting in the paragraph.
    /// </summary>
    /// <returns>Number of joins performed. When <b>N</b> adjacent runs are being joined they count as <b>N - 1</b> joins.</returns>
    public int joinRunsWithSameFormatting()
    {
        return joinRunsWithSameFormatting(new StringBuilder(), null);
    }

    /// <summary>
    /// Joins runs with the same formatting in the paragraph.
    /// </summary>
    /// <param name = "options" > Additional options</param>
    /// <returns>Number of joins performed. When <b>N</b> adjacent runs are being joined they count as <b>N - 1</b> joins.</returns>
    public int joinRunsWithSameFormatting(JoinRunsOptions options)
    {
        return joinRunsWithSameFormatting(new StringBuilder(), options);
    }


    /// <summary>
    /// Returns true if the paragraph has list label in original revision.
    /// </summary>
    /// <remarks>
    /// A paragraph can be list item and have no list label.
    /// </remarks>
    boolean hasListLabel() { return isListItemOriginal() && getListLabel().hasChars(); }

    /// <summary>
    /// Returns true if the paragraph has list label in final revision.
    /// </summary>
    /// <remarks>
    /// A paragraph can be list item and have no list label.
    /// </remarks>
    boolean hasListLabelFinal() { return isListItemFinal() && getListLabel().hasCharsFinal(); }

    /// <summary>
    /// Returns true if the paragraph has insert, delete, formatting, or list numbering revision.
    /// </summary>
    boolean hasRevisions() { return (getParagraphBreakRunPr().hasRevisions() || getParaPr().hasRevisions() || getParaPr().hasNumberRevision()); }

    /// <summary>
    /// Returns true if the paragraph contains text other than whitespaces or any other nodes.
    /// </summary>
    boolean hasNonWhitespaceChildren()
    {
        for (Node childNode = getFirstChild(); childNode != null; childNode = childNode.getNextSibling())
        {
            if (NodeUtil.isNonWhitespace(childNode))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Checks that paragraph is empty or contains only bookmark and comment range nodes.
    /// </summary>
    boolean isEmptyOrContainsOnlyCrossAnnotation()
    {
        for (Node node : (Iterable<Node>) getChildNodes(NodeType.ANY, false))
            if (!NodeUtil.isCrossStructureAnnotation(node))
                return false;

        return true;
    }

    /// <summary>
    /// Joins runs with same formatting in the paragraph.
    /// StringBuilder is passed to avoid its recreation since we process many paragraphs.
    /// Someday we can make override without parameters public and retain this one internal.
    /// </summary>
    /// <param name="sb">Utility StringBuilder</param>
    /// <param name="options">Additional options</param>
    /// <returns>Number of joins performed</returns>
    int joinRunsWithSameFormatting(StringBuilder sb, JoinRunsOptions options)
    {
        return joinRunsSpecificParent(this, sb, options);
    }

    private static int joinRunsSpecificParent(CompositeNode parent, StringBuilder sb, JoinRunsOptions options)
    {
        // Here we cannot use Runs collection because we should join only adjacent runs.
        // What is done in the loop:
        //   Remember the first found run and try joining with the very next.
        //   On success remove the first of them from the parent. Otherwise remember the second
        //   as candidate for joining with next.
        //   If current node is not a run then forget what we remembered.
        //   There are many cases with multiple runs in a sequence. So collect text parts in StringBuilder.

        Debug.assertTrue((sb != null) && (sb.length() == 0));

        int joinCount = 0;
        Run prevRun = null;

        final /*RunPrExpandFlags*/int EXPAND_FLAGS = RunPrExpandFlags.DOCUMENT_DEFAULTS | RunPrExpandFlags.GLOBAL_DEFAULTS;
        RunPr prevRunPrExp = null;

        for (Node curNode = parent.getFirstChild(); curNode != null; curNode = curNode.getNextSibling())
        {
            if (curNode.getNodeType() == NodeType.RUN)
            {
                Run curRun = (Run)curNode;

                if (prevRun != null)
                {
                    // Found two adjacent runs. Let's compare their completely expanded properties.
                    // During comparison some attributes should be ignored. See Run.KeysToIgnoreInComparisonOnJoin.
                    // Then collect texts in StringBuilder if properties are equal.
                    // Empty StringBuilder means that it is first join in the sequence.
                    RunPr curRunPrExp = curRun.getExpandedRunPr(EXPAND_FLAGS);
                    if (prevRunPrExp == null)
                        prevRunPrExp = prevRun.getExpandedRunPr(EXPAND_FLAGS);

                    Run joinRun = peekJoinRun(curRun, prevRun, curRunPrExp, prevRunPrExp, options);
                    if (joinRun != null)
                    {
                        if (sb.length() == 0)
                            msStringBuilder.append(sb, prevRun.getText());
                        msStringBuilder.append(sb, curRun.getText());

                        if (joinRun == prevRun)
                        {
                            prevRun = curRun;
                            curRun = joinRun;
                            curNode = joinRun;
                        }

                        ++joinCount;

                        parent.removeChild(prevRun);
                    }
                    else
                    {
                        flushJoinedRunsText(prevRun, sb);
                        prevRunPrExp = null;
                    }
                }

                prevRun = curRun;
            }
            else
            {
                // Flush if any collected and forget last candidate since the sequence is finished.
                flushJoinedRunsText(prevRun, sb);
                prevRun = null;
                prevRunPrExp = null;
            }

            // WORDSNET-20358 Join runs within StructuredDocumentTag.
            if (curNode.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            {
                StructuredDocumentTag sdt = (StructuredDocumentTag)curNode;
                joinRunsSpecificParent(sdt, sb, options);
            }
        }

        // Reached the end of paragraph. Flush if any collected.
        flushJoinedRunsText(prevRun, sb);
        Debug.assertTrue(sb.length() == 0);

        return joinCount;
    }

    /// <summary>
    /// Returns True if the specified attribute is to be ignored in IgnoreSpacing mode.
    /// </summary>
    private static boolean ignoreInIgnoreSpacing(int attr, boolean containsOnlyWhitespaces)
    {
        return containsOnlyWhitespaces
            ? ArrayUtil.contains(Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_IGNORE_SPACING, attr)
            : attr == FontAttr.SPACING;
    }

    /// <summary>
    /// Returns True if the specified attribute is to be ignored in IgnoreRedundant mode.
    /// </summary>
    private static boolean ignoreInIgnoreRedundant(int attr, /*CharacterCategory*/int charCategory, boolean runContainsOnlyWhitespaces)
    {
        if (runContainsOnlyWhitespaces && ArrayUtil.contains(Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_IGNORE_REDUNDANT_SPACING, attr))
            return true;

        if (!ArrayUtil.contains(Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_IGNORE_REDUNDANT, attr))
            return false;


        switch (charCategory)
        {
            case CharacterCategory.OTHER:
                return !ArrayUtil.contains(Run.KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_OTHER, attr);
            case CharacterCategory.FAR_EAST:
                return !ArrayUtil.contains(Run.KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_FAR_EAST, attr);
            case CharacterCategory.COMPLEX_SCRIPT:
                return !ArrayUtil.contains(Run.KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_COMPLEX_SCRIPT, attr);
        }

        return !ArrayUtil.contains(Run.KEYS_TO_CONSIDER_ON_JOIN_IGNORE_REDUNDANT_ASCII, attr);
    }

    /// <summary>
    /// Returns True if the specified attribute is to be ignored in IgnoreInsignificant mode.
    /// </summary>
    private static boolean ignoreInIgnoreInsignificant(int attr, boolean containsOnlyWhitespaces, Object valA, Object valB, String val1, String val2)
    {
        if (containsOnlyWhitespaces && ArrayUtil.contains(Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_INSIGNIFICANT_SPACING, attr))
            return true;

        switch (attr)
        {
            case FontAttr.SIZE:
            case FontAttr.SIZE_BI:
                int sizeA = (/*int*/Integer)valA;
                int sizeB = (/*int*/Integer)valB;

                return Math.abs(sizeB - sizeA) < 4;
            case FontAttr.COLOR:
            case FontAttr.HIGHLIGHT_COLOR:
            case FontAttr.UNDERLINE_COLOR:
                DrColor colorA = (DrColor)valA;
                DrColor colorB = (DrColor)valB;
                if ((colorA.isEmpty() && !colorB.isEmpty()) || (!colorA.isEmpty() && colorB.isEmpty()))
                    return false;

                return HSLColor.colorDiff(colorA.toArgb(), colorB.toArgb()) < 0.04;
            case FontAttr.SPACING:
                int spacingA = (/*int*/Integer)valA;
                int spacingB = (/*int*/Integer)valB;

                return Math.abs(spacingB - spacingA) < 12;
            case FontAttr.NO_PROOFING:
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns a suitable run into where the adjacent run may be joined.
    /// Returns null if the runs cannot be joined.
    /// </summary>
    private static Run peekJoinRun(Run runA, Run runB, RunPr runPrExpA, RunPr runPrExpB, JoinRunsOptions options)
    {
        Run result = null;

        if (RunPr.isSameFormatting(runPrExpA, runPrExpB))
            return runA;

        if (options == null)
            return null;

        boolean runAContainsOnlyWhitespaces =  StringUtil.containsOnlyWhitespaces(runA.getText());
        boolean runBContainsOnlyWhitespaces = StringUtil.containsOnlyWhitespaces(runB.getText());
        /*CharacterCategory*/int charCategoryA = runA.getFont().getCharacterCategory();
        /*CharacterCategory*/int charCategoryB = runB.getFont().getCharacterCategory();

        IntList keysToIgnore = new IntList();

        for (int attr : Run.KEYS_TO_IGNORE_IN_COMPARISON_ON_JOIN_ALL)
        {
            if (options.getIgnoreSpacing())
            {
                if (ignoreInIgnoreSpacing(attr, runAContainsOnlyWhitespaces || runBContainsOnlyWhitespaces))
                {
                    keysToIgnore.add(attr);
                    continue;
                }
            }

            if (options.getIgnoreRedundant())
            {
                if (ignoreInIgnoreRedundant(attr, charCategoryA, runAContainsOnlyWhitespaces) &&
                    ignoreInIgnoreRedundant(attr, charCategoryB, runBContainsOnlyWhitespaces))
                {
                    keysToIgnore.add(attr);
                    continue;
                }
            }

            if (options.getIgnoreInsignificant())
            {
                
                if (ignoreInIgnoreInsignificant(attr, runAContainsOnlyWhitespaces || runBContainsOnlyWhitespaces, runA.getRunPr().fetchAttr(attr), runB.getRunPr().fetchAttr(attr), runA.getText(), runB.getText()))
                {
                    keysToIgnore.add(attr);
                    continue;
                }
            }
        }

        if (RunPr.isSameFormatting(runPrExpA, runPrExpB, keysToIgnore.toArray()))
        {
            result = !StringUtil.containsOnlyWhitespaces(runA.getText())
                ? runA
                : runB;
        }

        return result;
    }

    /// <summary>
    /// Flushes collected text into the given run and reset StringBuilder.
    /// </summary>
    private static void flushJoinedRunsText(Run run, StringBuilder sb)
    {
        if ((run != null) && (sb.length() != 0))
        {
            run.setText(sb.toString());
            sb.setLength(0);
        }
    }

    /// <overloads>Appends a Word field to this paragraph.</overloads>
    /// <summary>
    /// Appends a field to this paragraph.
    /// </summary>
    /// <param name="fieldType">The type of the field to append.</param>
    /// <param name="updateField">Specifies whether to update the field immediately.</param>
    /// <returns>A <see cref="Field"/> object that represents the appended field.</returns>
    public Field appendField(/*FieldType*/int fieldType, boolean updateField) throws Exception
    {
        return insertField(fieldType, updateField, null, true);
    }

    /// <summary>
    /// Appends a field to this paragraph.
    /// </summary>
    /// <param name="fieldCode">The field code to append (without curly braces).</param>
    /// <returns>A <see cref="Field"/> object that represents the appended field.</returns>
    public Field appendField(String fieldCode) throws Exception
    {
        return insertField(fieldCode, null, true);
    }

    /// <summary>
    /// Appends a field to this paragraph.
    /// </summary>
    /// <param name="fieldCode">The field code to append (without curly braces).</param>
    /// <param name="fieldValue">The field value to append. Pass <c>null</c> for fields that do not have a value.</param>
    /// <returns>A <see cref="Field"/> object that represents the appended field.</returns>
    public Field appendField(String fieldCode, String fieldValue)
    {
        return insertField(fieldCode, fieldValue, null, true);
    }

    /// <overloads>Inserts a Word field into this paragraph.</overloads>
    /// <summary>
    /// Inserts a field into this paragraph.
    /// </summary>
    /// <param name="fieldType">The type of the field to insert.</param>
    /// <param name="updateField">Specifies whether to update the field immediately.</param>
    /// <param name="refNode">Reference node inside this paragraph (if <paramref name="refNode"/> is <c>null</c>, then appends to the end of the paragraph).</param>
    /// <param name="isAfter">Whether to insert the field after or before reference node.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertField(/*FieldType*/int fieldType, boolean updateField, Node refNode, boolean isAfter) throws Exception
    {
        return FieldAppender.insertField(fieldType, updateField, getRunPr(refNode), this, refNode, isAfter);
    }

    /// <summary>
    /// Inserts a field into this paragraph.
    /// </summary>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <param name="refNode">Reference node inside this paragraph (if <paramref name="refNode"/> is <c>null</c>, then appends to the end of the paragraph).</param>
    /// <param name="isAfter">Whether to insert the field after or before reference node.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertField(String fieldCode, Node refNode, boolean isAfter) throws Exception
    {
        return FieldAppender.insertField(fieldCode, getRunPr(refNode), this, refNode, isAfter);
    }

    /// <summary>
    /// Inserts a field into this paragraph.
    /// </summary>
    /// <param name="fieldCode">The field code to insert (without curly braces).</param>
    /// <param name="fieldValue">The field value to insert. Pass <c>null</c> for fields that do not have a value.</param>
    /// <param name="refNode">Reference node inside this paragraph (if <paramref name="refNode"/> is <c>null</c>, then appends to the end of the paragraph).</param>
    /// <param name="isAfter">Whether to insert the field after or before reference node.</param>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field insertField(String fieldCode, String fieldValue, Node refNode, boolean isAfter)
    {
        return FieldAppender.insertField(fieldCode, fieldValue, getRunPr(refNode), this, refNode, isAfter);
    }

    boolean isListItemOriginal() { return (/*int*/Integer)fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.ORIGINAL) != 0; }

    boolean isListItemFinal() { return (/*int*/Integer)fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.FINAL) != 0; }

    /// <summary>
    /// Returns original list level applied to the current paragraph.
    /// </summary>
    ListLevel getListLevel() { return getListLevel(false); }

    /// <summary>
    /// Returns either original or final list applied to the current paragraph.
    /// </summary>
    List getList(boolean isRevised)
    {
        /*RevisionsView*/int view = isRevised ? RevisionsView.FINAL : RevisionsView.ORIGINAL;

        int listId = (/*int*/Integer)fetchParaAttr(ParaAttr.LIST_ID, view);
        if (listId == 0)
            return null;

        List list = getDocument().getLists().fetchListByListId(listId);

        return list;
    }

    /// <summary>
    /// Returns either original or final list level applied to the current paragraph.
    /// </summary>
    ListLevel getListLevel(boolean isRevised)
    {
        /*RevisionsView*/int view = isRevised ? RevisionsView.FINAL : RevisionsView.ORIGINAL;

        int listId = (/*int*/Integer)fetchParaAttr(ParaAttr.LIST_ID, view);
        if (listId == 0)
            return null;

        List list = getDocument().getLists().fetchListByListId(listId);

        int listLevel = (/*int*/Integer)fetchParaAttr(ParaAttr.LIST_LEVEL, view);

        ListLevel level = list.getListLevelOverrideAware(listLevel);

        return level;
    }


    /// <summary>
    /// <para>
    /// Gets integer value representing nesting level of BlockQuote
    /// when the paragraph belongs to some <see cref="HtmlBlock"/>.
    /// </para>
    /// <para>
    /// A <c>zero</c> value means the paragraph is not in BlockQuote.
    /// </para>
    /// </summary>
    int getHtmlBlockQuoteLevel()
    {
        HtmlBlock htmlBlock = getDocument().getHtmlBlockCollection().getHtmlBlockById(getParaPr().getHtmlBlockId());
        int level = 0;
        while (htmlBlock != null)
        {
            if (htmlBlock.getHtmlBlockType() == HtmlBlockType.BLOCK_QUOTE)
                level++;

            htmlBlock = getDocument().getHtmlBlockCollection().getHtmlBlockById(htmlBlock.getParentId());
        }

        return level;
    }

    /// <summary>
    /// Specifies an identifier for a paragraph that is unique within the document part.
    /// </summary>
    /// <remarks>
    /// See [MS-DOCX] 2.4.1 paraId.
    ///
    /// AM. I don't want to put this into attribute collection since this property is not inheritable.
    /// We already put rsid related attributes to collection and now have to write some additional code,
    /// for example AttrCollection.IsIgnoredOnCollapse() method. I want to avoid it.
    /// </remarks>
    int getParaId() { return mParaId; }; void setParaId(int value) { mParaId = value; };

    private int mParaId;

    /// <summary>
    /// Specifies version identifier for the paragraph.
    /// </summary>
    /// <remarks>
    /// See [MS-DOCX] 2.4.2 textId
    /// </remarks>
    int getTextId() { return mTextId; }; void setTextId(int value) { mTextId = value; };

    private int mTextId;

    private RunPr getRunPr(Node refNode)
    {
        if (refNode == null)
        {
            if (getRuns().getCount() > 0)
                return getRuns().get(getRuns().getCount() - 1).getRunPr();
        }
        else
        {
            if (refNode instanceof Run && refNode.getParentNode() == this)
                return ((Run)refNode).getRunPr();
        }

        return getParagraphBreakRunPr();
    }

    /// <summary>
    /// Updates normal spacings/indents from unit attribute values.
    /// </summary>
    private void updateFromUnits()
    {
        // Do not update if paragraph outside document section.
        if ((getDocument() == null) || (getParentSection() == null) || (getDocument().getNodeType() != NodeType.DOCUMENT))
            return;

        UnitConverter converter = new UnitConverter();
        converter.visitDocumentStart((Document)getDocument());
        // WORDSNET-26551 Update from units depending on story type.
        if (getParentStory().getNodeType() == NodeType.BODY)
            converter.visitBodyStart((Body)getParentStory());
        else
            converter.visitHeaderFooterStart((HeaderFooter)getParentStory());
        converter.visitParagraphStart(this);
    }

    // These need to be cloned.
    private ParaPr mParaPr;
    /// <summary>
    /// The formatting of the paragraph mark.
    /// </summary>
    private RunPr mParagraphBreakRunPr;

    // These should not be cloned.
    private ParagraphFormat mParagraphFormat;
    private FrameFormat mFrameFormat;
    private ListFormat mListFormat;
    private ListLabel mListLabel;
    private RunCollection mRuns;
}

