// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base class for inline-level nodes that can contain paragraphs and tables.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/logical-levels-of-nodes-in-a-document/">Logical Levels of Nodes in a Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="InlineStory"/> is a container for block-level nodes <see cref="Paragraph"/> and <see cref="Table"/>.</p>
///
/// <p>The classes that derive from <see cref="InlineStory"/> are inline-level nodes that can contain
/// their own text (paragraphs and tables). For example, a <see cref="Comment"/> node contains text of a comment
/// and a <see cref="Aspose.Words.Notes.Footnote"/> contains text of a footnote.</p>
/// </remarks>
public abstract class InlineStory extends CompositeNode<Node> implements IInline, IStory
{
    InlineStory(DocumentBase doc, RunPr runPr)
    {
    	super(doc);
	
        if (runPr == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "runPr");

        setRunPr(runPr);
    }

    /// <summary>
    /// Returns the type of the story.
    /// </summary>
    public abstract /*StoryType*/int getStoryType();

    /// <summary>
    /// Retrieves the parent <see cref="Paragraph"/> of this node.
    /// </summary>
    public Paragraph getParentParagraph() { return (Paragraph)getAncestor(NodeType.PARAGRAPH); }

    /// <summary>
    /// Gets the first paragraph in the story.
    /// </summary>
    public Paragraph getFirstParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, 0, false); }

    /// <summary>
    /// Gets the last paragraph in the story.
    /// </summary>
    public Paragraph getLastParagraph() { return (Paragraph)getChild(NodeType.PARAGRAPH, -1, false); }

    /// <summary>
    /// Gets a collection of paragraphs that are immediate children of the story.
    /// </summary>
    public ParagraphCollection getParagraphs() { return (mParagraphs != null ? mParagraphs : (mParagraphs = new ParagraphCollection(this))); }

    /// <summary>
    /// Gets a collection of tables that are immediate children of the story.
    /// </summary>
    public TableCollection getTables() { return (mTables != null ? mTables : (mTables = new TableCollection(this))); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsInsertRevision"]/*'/>
    public boolean isInsertRevision() { return InlineHelper.isInsertRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsDeleteRevision"]/*'/>
    public boolean isDeleteRevision() { return InlineHelper.isDeleteRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveFromRevision"]/*'/>
    public boolean isMoveFromRevision() { return InlineHelper.isMoveFromRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveToRevision"]/*'/>
    public boolean isMoveToRevision() { return InlineHelper.isMoveToRevision(this); }

    /// <summary>
    /// Provides access to the font formatting of the anchor character of this object.
    /// </summary>
    public Font getFont() { return (mFont != null ? mFont : (mFont = new Font(this, getDocument()))); }

    RunPr getRunPr() { return mRunPr; }; void setRunPr(RunPr value) { mRunPr = value; };

    private RunPr mRunPr;

    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getRunPr_IInline() { return getRunPr(); }
    @ReservedForInternalUse @Deprecated
    public void /*IInline.*/setRunPr_IInline(RunPr value) { setRunPr(value); }

    /// <summary>
    /// Added by VA for DOCX import.
    /// </summary>
    void setRunPrInternal(RunPr runPr)
    {
        setRunPr(runPr);
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        InlineStory lhs = (InlineStory)super.deepClone(isCloneChildren, cloningListener);
        lhs.setRunPr(AttrCollectionExtension.deepClone(getRunPr()));
        lhs.mFont = null;
        lhs.mParagraphs = null;
        lhs.mTables = null;
        return lhs;
    }

    /// <summary>
    /// If the last child is not a paragraph, creates and appends one empty paragraph.
    /// </summary>
    public void ensureMinimum()
    {
        WordUtil.ensureNonEmptyStory(this);
    }

    /// <summary>
    /// Allows to insert block level nodes.
    /// </summary>
    /*override*/ boolean canInsert(Node newChild)
    {
        return NodeUtil.isBlockLevelNode(newChild);
    }

    /// <summary>
    /// Creates a fully expanded version of the RunPr object.
    /// </summary>
    RunPr getExpandedRunPr(/*RunPrExpandFlags*/int flags)
    {
        return InlineHelper.getExpandedRunPr(this, flags);
    }

    @ReservedForInternalUse @Deprecated
    public Paragraph /*IInline.*/getParentParagraph_IInline() { return this.getParentParagraph(); }

    @ReservedForInternalUse @Deprecated
    public DocumentBase /*IInline.*/getDocument_IInline() { return this.getDocument(); }

    /// <summary>
    /// Creates a fully expanded version of the RunPr object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getExpandedRunPr_IInline(/*RunPrExpandFlags*/int flags)
    {
        return InlineHelper.getExpandedRunPr(this, flags);
    }

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return getRunPr().getDirectAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getRunPr().getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int fontAttr)
    {
        return InlineHelper.fetchInheritedAttr(this, fontAttr);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int fontAttr, Object value)
    {
        getRunPr().setAttr(fontAttr, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        getRunPr().remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        getRunPr().clear();
    }

    //<<<<<<<< #endregion 

    private Font mFont;
    private ParagraphCollection mParagraphs;
    private TableCollection mTables;
}

