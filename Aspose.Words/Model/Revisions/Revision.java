// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents a revision (tracked change) in a document node or style.
/// Use <see cref="RevisionType"/> to check the type of this revision.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/track-changes-in-a-document/">Track Changes in a Document</a> documentation article.</para>
/// </summary>
public class Revision
{
    Revision(
        /*RevisionType*/int revType,
        RevisionBase revBase,
        Node parentNode,
        RevisionCollection parentCollection)
    {
    	this(revType, revBase, parentCollection);
	
        Debug.assertTrue(parentNode != null);
        mParentNode = parentNode;
        mIsInline = parentNode instanceof IInline;
    }

    /// <summary>
    /// ctor for revisions of styles. Currently we support creation of Revisions for Character, Paragraph and List style types.
    /// </summary>
    Revision(
        /*RevisionType*/int revType,
        RevisionBase revBase,
        Style parentStyle,
        RevisionCollection parentCollection)
    {
    	this(revType, revBase, parentCollection);
	
        Debug.assertTrue(parentStyle != null);
        mParentStyle = parentStyle;
    }

    private Revision(/*RevisionType*/int revType, RevisionBase revBase, RevisionCollection parentCollection)
    {
        Debug.assertTrue(parentCollection != null);
        mParentCollection = parentCollection;

        mRevisionType = revType;
        mRevisionBase = revBase;
    }

    /// <summary>
    /// Accepts this revision.
    /// </summary>
    public void accept() throws Exception
    {
        handleRevisions(true, new RevisionHandlingContext(true, getRevisionType()));
    }

    /// <summary>
    /// Reject this revision.
    /// </summary>
    public void reject() throws Exception
    {
        handleRevisions(true, new RevisionHandlingContext(false, getRevisionType()));
    }

    /// <summary>
    /// Common method for accept/reject revisions both for individual revisions and for whole collection.
    /// </summary>
    /// <param name="notifyCollection">True to notify parent collection so it can update its live status.
    /// Used for operations on individual revisions.</param>
    /// <param name="context">Object to store current state of revision handling including nodes for delayed
    /// deletion.</param>
    void handleRevisions(boolean notifyCollection, RevisionHandlingContext context) throws Exception
    {
        int documentTreeChangeCount = getDocument().getTreeChangeCount();

        if (mParentNode != null) // dealing with Node revision
        {
            RevisionUtil.handleNodeRevision(mParentNode, context);
        }
        else // dealing with style revision
        {
            if (context.isAcceptance())
            {
                mParentStyle.getRunPr().acceptFormatRevision();
                mParentStyle.getParaPr().acceptFormatRevision();
            }
            else
            {
                mParentStyle.getRunPr().rejectFormatRevision();
                mParentStyle.getParaPr().rejectFormatRevision();
            }
        }

        // If it's a revision that doesn't cause a change in Document.TreeChangeCount,
        // we shall make this change ourselves to keep all Range.Revisions collections up-to-date.
        if (getDocument().getTreeChangeCount() == documentTreeChangeCount)
            getDocument().markChanged();

        if (notifyCollection)
            mParentCollection.invalidate(this);
    }

    /// <summary>
    /// Gets or sets the author of this revision. Can not be empty string or <c>null</c>.
    /// </summary>
    public String getAuthor() { return mRevisionBase.getAuthor(); }

    public void setAuthor(String value)
    {
        if (!StringUtil.hasChars(value))
            throw new IllegalArgumentException(value);

        mRevisionBase.setAuthor(value);
    }

    /// <summary>
    /// Gets or sets the date/time of this revision.
    /// </summary>
    //JAVA: property accessor is internalized
	DateTime getDateTimeInternal() { return mRevisionBase.getDateTime(); }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getDateTime() { return DateTime.toJava(getDateTimeInternal()); }
    //JAVA: property accessor is internalized
	void setDateTimeInternal(DateTime value) { mRevisionBase.setDateTime(value); }
	//JAVA-added public wrapper for internalized property accessor
	public void setDateTime(java.util.Date value) { setDateTimeInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Gets the type of this revision.
    /// </summary>
    public /*RevisionType*/int getRevisionType() { return mRevisionType; };

    private /*RevisionType*/int mRevisionType;

    /// <summary>
    /// Gets the immediate parent node (owner) of this revision.
    /// This property will work for any revision type other than <see cref="Aspose.Words.RevisionType.StyleDefinitionChange"/>.
    /// </summary>
    /// <remarks>
    /// If this revision relates to change of Style formatting, use <see cref="ParentStyle"/> instead.
    /// </remarks>
    public Node getParentNode()
    {
        if (mParentNode == null)
            throw new IllegalStateException("Can not access ParentNode for a style revision. Use ParentStyle instead.");

        return mParentNode;
    }

    /// <summary>
    /// Gets the immediate parent style (owner) of this revision.
    /// This property will work for only for the <see cref="Aspose.Words.RevisionType.StyleDefinitionChange"/> revision type.
    /// </summary>
    /// <remarks>
    /// If this revision relates to changes on document nodes, use <see cref="ParentNode"/> instead.
    /// </remarks>
    public Style getParentStyle()
    {
        if (mParentStyle == null)
            throw new IllegalStateException("Can not access ParentStyle for a node revision. Use ParentNode instead.");

        return mParentStyle;
    }

    /// <summary>
    /// Gets the revision group. Returns <c>null</c> if the revision does not belong to any group.
    /// </summary>
    /// <remarks>
    /// Revision has no group if revision type is <see cref="RevisionType.StyleDefinitionChange"/> or
    /// if the revision is not longer exist in document context (accepted/rejected).
    /// </remarks>
    public RevisionGroup getGroup()
    {
        if (getRevisionType() == RevisionType.STYLE_DEFINITION_CHANGE)
            return null;

        return mParentCollection.getGroups().get(mRevisionBase);
    }

    boolean isInline() { return (getRevisionType() != RevisionType.STYLE_DEFINITION_CHANGE) && mIsInline; }

    private DocumentBase getDocument()
    {
        return mParentNode != null
            ? mParentNode.getDocument()
            : mParentStyle.getDocument();
    }

    private /*final*/ RevisionBase mRevisionBase;
    private /*final*/ Node mParentNode;
    private /*final*/ Style mParentStyle;
    private /*final*/ boolean mIsInline;
    private /*final*/ RevisionCollection mParentCollection;
}

