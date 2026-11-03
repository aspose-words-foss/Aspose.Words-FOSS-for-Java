// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.FontUtil;
import com.aspose.ms.System.msObject;


/// <summary>
/// Base class for inline-level nodes that can have character formatting associated with them, but cannot have child nodes of their own.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/logical-levels-of-nodes-in-a-document/">Logical Levels of Nodes in a Document</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A class derived from <see cref="Inline"/> can be a child of <see cref="Paragraph"/>.</p>
/// </remarks>
public abstract class Inline extends Node implements IInline, ITrackableNode
{
    Inline(DocumentBase doc, RunPr runPr)
    {
    	super(doc);
	
        if (runPr == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "runPr");

        mRunPr = runPr;
    }

    /// <summary>
    /// Retrieves the parent <see cref="Paragraph"/> of this node.
    /// </summary>
    public Paragraph getParentParagraph() { return (Paragraph)getAncestor(NodeType.PARAGRAPH); }

    /// <summary>
    /// Provides access to the font formatting of this object.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
            mFont = new Font(this, getDocument());
        return mFont;
    }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsInsertRevision"]/*'/>
    public boolean isInsertRevision() { return InlineHelper.isInsertRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsDeleteRevision"]/*'/>
    public boolean isDeleteRevision() { return InlineHelper.isDeleteRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveFromRevision"]/*'/>
    public boolean isMoveFromRevision() { return InlineHelper.isMoveFromRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsMoveToRevision"]/*'/>
    public boolean isMoveToRevision() { return InlineHelper.isMoveToRevision(this); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="IsFormatRevision"]/*'/>
    public boolean isFormatRevision() { return InlineHelper.isFormatRevision(this); }

    /// <summary>
    /// Gets a boolean value indicating whether the node accepts the given visitor.
    /// </summary>
    /// <remarks>
    /// This is used to skip the nodes marked for deletion.
    /// </remarks>
    /// <param name="visitor"></param>
    /// <returns></returns>
    boolean isVisitorAcceptable(DocumentVisitor visitor)
    {
        return !isDeleteRevision() || visitor.getVisitsDeletedNodes();
    }

    /// <summary>
    /// Gets a boolean value indicating if the node is hidden or deleted.
    /// </summary>
    /// <remarks>
    /// It's a shortcut as these two checks often go together.
    /// </remarks>
    /// <returns></returns>
    boolean isHiddenOrDeleted() { return isDeleteRevision() || getFont().getHidden();}

    /// <summary>
    /// Gets the character style assigned to this inline object.
    /// This sometimes is more efficient than using Font.Style.
    /// </summary>
    Style getCharacterStyle() { return InlineHelper.getCharacterStyle(this); }

    /// <summary>
    /// If you set, do so with care.
    /// </summary>
    RunPr getRunPr() { return mRunPr; }
    void setRunPr(RunPr value)
    {
        mRunPr = value;
    }

    @ReservedForInternalUse @Deprecated
    public RunPr /*IInline.*/getRunPr_IInline() { return mRunPr; }
    @ReservedForInternalUse @Deprecated
    public void /*IInline.*/setRunPr_IInline(RunPr value) { mRunPr = value; }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        Inline lhs = (Inline)super.deepClone(isCloneChildren, cloningListener);
        RunPr runPr = AttrCollectionExtension.deepClone(mRunPr);
        lhs.mRunPr = runPr;
        lhs.mFont = null;   //Don't clone this because created on demand.
        return lhs;
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

    void expandRunPr(RunPr dstRunPr, /*RunPrExpandFlags*/int flags)
    {
        InlineHelper.expandRunPr(this, dstRunPr, flags);
    }

    /// <summary>
    /// Indicates that inline should be written as Symbol.
    /// </summary>
    boolean isWriteAsSymbol()
    {
        String text = getText();

        Theme theme = getDocument().getNodeType() == NodeType.DOCUMENT
            ? ((Document)getDocument()).getThemeInternal()
            : null;

        String fontNameOther = getRunPr().getComplexNameOther().isThemeFont()
            ? getRunPr().getComplexNameOther().resolve(theme)
            : getRunPr().getNameOther();

        // WORDSNET-22241 Saving the Checkbox SDT with the symbolic font.
        if (getParentNode() != null)
        {
            // WORDSNET-27559 Do not write character as symbol inside PlainText SDT.
            StructuredDocumentTag parentTag = ms.as(getParentNode(), StructuredDocumentTag.class);
            if ((parentTag != null) && (parentTag.getSdtType() == SdtType.PLAIN_TEXT))
                return false;

            StructuredDocumentTag ancestorTag = ms.as(getParentNode().getParentNode(), StructuredDocumentTag.class);
            if ((ancestorTag != null) && (ancestorTag.getSdtType() == SdtType.CHECKBOX) && FontUtil.isSymbolic(fontNameOther))
                return true;
        }

        // WORDSNET-17859 This character excluded from common SYM processing and should be written as symbol.
        // WORDSNET-19401 The font shouldn't be the theme font.
        if ((text.length() > 0) && (text.charAt(0) == 0xA0) && FontUtil.isSymbolic(fontNameOther))
            return true;

        // WORDSNET-23237 Write SYM if font is symbolic only.
        if (!Run.isSymbolicCharacter(text) || !FontUtil.isSymbolic(fontNameOther))
            return false;

        if (getRunPr().contains(FontAttr.CHARACTER_CATEGORY_HINT))
            return false;

        if (!getRunPr().contains(FontAttr.NAME_OTHER))
            return false;

        if (!msObject.equals(getRunPr().getComplexNameAscii(), getRunPr().getComplexNameOther()))
            return false;

        return true;
    }

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return mRunPr.getDirectAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return mRunPr.getDirectAttr(key, revisionsView);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int fontAttr)
    {
        return InlineHelper.fetchInheritedAttr(this, fontAttr);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        // WORDSNET-26171 Translate decoration attribute into MathStyle.
        if ((key == FontAttr.ITALIC) && (getAncestor(NodeType.OFFICE_MATH) != null))
        {
            // AM. Actually we need more complex logic here, we should consider currently applied decoration,
            // but seems this feature is rarely used so lets start with very minimum solution.
            mRunPr.setAttr(FontAttr.MATH_STYLE, ((AttrBoolEx)value).toBool() ? MathStyle.ITALIC : MathStyle.PLAIN);
        }
        else
            mRunPr.setAttr(key, value);
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
    }

    //<<<<<<<< #endregion 

    private RunPr mRunPr;
    private Font mFont;

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getInsertRevision() { return getRunPr().getInsertRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { getRunPr().setInsertRevision(value); }

    @ReservedForInternalUse @Deprecated
    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return getRunPr().getDeleteRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { getRunPr().setDeleteRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision() { return getRunPr().getMoveFromRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value) { getRunPr().setMoveFromRevision(value); }

    @ReservedForInternalUse @Deprecated
    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision() { return getRunPr().getMoveToRevision(); }
    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value) { getRunPr().setMoveToRevision(value); }

    @ReservedForInternalUse @Deprecated
    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        getRunPr().remove(RevisionAttr.MOVE_FROM_REVISION);
        getRunPr().remove(RevisionAttr.MOVE_TO_REVISION);
    }
}

