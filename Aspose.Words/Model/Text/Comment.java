// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/09/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.DateTime;
import com.aspose.ArgumentUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents a container for text of a comment.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-comments/">Working with Comments</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>A comment is an annotation which is anchored to a region of text or to a position in text.
/// A comment can contain an arbitrary amount of block-level content.</para>
///
/// <para>If a <see cref="Comment"/> object occurs on its own, the comment is anchored to
/// the position of the <see cref="Comment"/> object.</para>
///
/// <para>To anchor a comment to a region of text three objects are required: <see cref="Comment"/>,
/// <see cref="CommentRangeStart"/> and <see cref="CommentRangeEnd"/>. All three objects need to share the same
/// <see cref="Comment.Id"/> value.</para>
///
/// <p><see cref="Comment"/> is an inline-level node and can only be a child of <see cref="Paragraph"/>.</p>
///
/// <p><see cref="Comment"/> can contain <see cref="Paragraph"/> and <see cref="Aspose.Words.Tables.Table"/> child nodes.</p>
///
/// <seealso cref="CommentRangeStart"/>
/// <seealso cref="CommentRangeEnd"/>
/// </remarks>
public final class Comment extends InlineStory implements INodeWithAnnotationId, IMoveTrackableNode
{
    /// <summary>
    /// Main ctor used internally.
    /// </summary>
    Comment(DocumentBase doc, RunPr runPr)
    {
    	super(doc, runPr);
	
        // We must attempt to generate a unique id because comments with duplicate ids will be discarded during save.
        mId = doc.getNextAnnotationId();
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Comment"/> class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="Comment"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    ///
    /// <p>To append <see cref="Comment"/> to the document use <see cref="CompositeNode.InsertAfter{T}(T, Node)"/> or <see cref="CompositeNode.InsertBefore{T}(T, Node)"/>
    /// on the paragraph where you want the comment inserted.</p>
    ///
    /// <para>After creating a comment, don't forget to set its <see cref="Author"/>,
    /// <see cref="Initial"/> and <see cref="DateTime"/> properties.</para>
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    public Comment(DocumentBase doc)
    {
    	this(doc, "", "", DateTime.MinValue);
	
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Comment"/> class.
    /// </summary>
    /// <param name="doc">The owner document.</param>
    /// <param name="author">The author name for the comment. Cannot be <c>null</c>.</param>
    /// <param name="initial">The author initials for the comment. Cannot be <c>null</c>.</param>
    /// <param name="dateTime">The date and time for the comment.</param>
    //JAVA: ctor is internalized
	Comment(DocumentBase doc, String author, String initial, DateTime dateTime)
    {
    	this(doc, new RunPr());
	
        setAuthor(author);
        setInitial(initial);
        setLocalDateTime(dateTime);
        getFont().setStyleIdentifier(StyleIdentifier.COMMENT_REFERENCE);
    }

	//JAVA-added public wrapper for internalized ctor
	public Comment(DocumentBase doc, String author, String initial, java.util.Date dateTime)
	{
		this(doc, author, initial, DateTime.fromJava(dateTime));
	}

    /// <summary>
    /// Returns <see cref="NodeType.Comment"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.COMMENT; }

    /// <summary>
    /// Returns <see cref="StoryType.Comments"/>.
    /// </summary>
    public /*override*/ /*StoryType*/int getStoryType() { return StoryType.COMMENTS; }

    /// <summary>
    /// Gets or sets the comment identifier.
    /// </summary>
    /// <remarks>
    /// <para>The comment identifier allows to anchor a comment to a region of text in the document.
    /// The region must be demarcated using the <see cref="CommentRangeStart"/> and <see cref="CommentRangeEnd"/>
    /// object sharing the same identifier value as the <see cref="Comment"/> object.</para>
    ///
    /// <para>You would use this value when looking for the <see cref="CommentRangeStart"/> and
    /// <see cref="CommentRangeEnd"/> nodes that are linked to this comment.</para>
    ///
    /// <para>Comment identifiers are supposed to be unique across a document and Aspose.Words automatically
    /// maintains comment identifiers when loading, saving and combining documents.</para>
    /// </remarks>
    public int getId() { return mId; }
    public void setId(int value)
    {
        mId = value;
        if (getDocument() != null)
            getDocument().markChanged();
    }

    /// <summary>
    /// INeedImportCommentId
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public int /*INodeWithAnnotationId.*/getIdInternal() { return getId(); }
    @ReservedForInternalUse @Deprecated
    public void /*INodeWithAnnotationId.*/setIdInternal(int value) { setId(value); }

    @ReservedForInternalUse @Deprecated
    public int /*INodeWithAnnotationId.*/getParentIdInternal() { return getParentId(); }
    @ReservedForInternalUse @Deprecated
    public void /*INodeWithAnnotationId.*/setParentIdInternal(int value) { setParentId(value); }

    /// <summary>
    /// Returns or sets the initials of the user associated with a specific comment.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// <para>Default is empty string.</para>
    /// </remarks>
    public String getInitial() { return mInitial; }
    public void setInitial(String value)
    {
        ArgumentUtil.checkNotNull(value, "Initial");
        mInitial = value;
    }

    /// <summary>
    /// Gets the date and time that the comment was made.
    /// </summary>
    /// <remarks>
    /// Default is
    /// <ms><see cref="System.DateTime.MinValue"/></ms>
    /// <java>03.01.0001</java>
    /// <cpp><see cref="System.DateTime.MinValue"/></cpp>
    /// </remarks>
    //JAVA: property accessor is internalized
	DateTime getDateTimeInternal() { return mLocalDateTime; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getDateTime() { return DateTime.toJava(getDateTimeInternal()); }
    //JAVA: property accessor is internalized
	void setDateTimeInternal(DateTime value)
    {
        mLocalDateTime = value;

        // If mUtcDateTime is not defined, leave it as is, otherwise ComplianceInfo will be updated as having
        // Word 2019 extensions. If necessary, UtcDateTime will be set during validation.
        if (!DateTime.equals(mDateTimeUtc, DateTime.MinValue))
            mDateTimeUtc = (!DateTime.equals(value, DateTime.MinValue)) ? value.toUniversalTime() : DateTime.MinValue;
    }
	//JAVA-added public wrapper for internalized property accessor
	public void setDateTime(java.util.Date value) { setDateTimeInternal(DateTime.fromJava(value)); }

    /// <summary>
    /// Gets the UTC date and time that the comment was made.
    /// </summary>
    /// <remarks>
    /// The default value is
    /// <ms><see cref="System.DateTime.MinValue"/></ms>
    /// <java>03.01.0001</java>
    /// <cpp><see cref="System.DateTime.MinValue"/></cpp>
    /// </remarks>
    //JAVA: property accessor is internalized
	DateTime getDateTimeUtcInternal()
    {
        if (!DateTime.equals(mDateTimeUtc, DateTime.MinValue))
            return mDateTimeUtc;

        return (!DateTime.equals(mLocalDateTime, DateTime.MinValue))
            ? mLocalDateTime.toUniversalTime()
            : DateTime.MinValue;
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getDateTimeUtc() { return DateTime.toJava(getDateTimeUtcInternal()); }
    void setDateTimeUtc(DateTime value) { mDateTimeUtc = value; }

    /// <summary>
    /// Returns or sets the author name for a comment.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// <para>Default is empty string.</para>
    /// </remarks>
    public String getAuthor() { return mAuthor; }
    public void setAuthor(String value)
    {
        ArgumentUtil.checkNotNull(value, "Author");
        mAuthor = value;
    }

    /// <summary>
    /// Gets or sets the local date and time that the comment was made.
    /// </summary>
    DateTime getLocalDateTime() { return mLocalDateTime; }
    void setLocalDateTime(DateTime value) { mLocalDateTime = value; }

    /// <summary>
    /// Gets a flag indicating whether the UTC date and time of the comment is defined.
    /// </summary>
    boolean isDateTimeUtcDefined() { return (!DateTime.equals(mDateTimeUtc, DateTime.MinValue)); }

    /// <summary>
    /// Gets or sets a collection of reactions to the comment.
    /// </summary>
    CommentReactionCollection getReactions() { return mReactions; }; void setReactions(CommentReactionCollection value) { mReactions = value; };

    private CommentReactionCollection mReactions;

    /// <summary>
    /// Gets a flag indicating whether the comment has reactions.
    /// </summary>
    boolean hasReactions() { return (getReactions() != null) && (getReactions().getCount() > 0); }

    /// <include file='..\Docs\Text.xml' path='Topics/Topic[@name="Node.Accept"]/*'/>
    /// <remarks>
    /// Calls <see cref="DocumentVisitor.VisitCommentStart"/>, then calls <see cref="Node.Accept"/> for all
    /// child nodes of the comment and calls <see cref="DocumentVisitor.VisitCommentEnd"/> at the end.
    /// </remarks>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return acceptCore(visitor);
    }

    /// <summary>
    /// Adds a reply to this comment.
    /// </summary>
    /// <param name="author">The author name for the reply.</param>
    /// <param name="initial">The author initials for the reply.</param>
    /// <param name="dateTime">The date and time for the reply.</param>
    /// <param name="text">The reply text.</param>
    /// <returns>The created <see cref="Comment"/> node for the reply.</returns>
    /// <remarks>
    /// <para>Due to the existing MS Office limitations only 1 level of replies is allowed in the document.</para>
    /// </remarks>
    /// <exception cref="InvalidOperationException">Throws if this method is called on the existing Reply comment.</exception>
    //JAVA: method is internalized
	Comment addReplyInternal(String author, String initial, DateTime dateTime, String text) throws Exception
    {
        if (getParentId() != NO_PARENT)
            throw new IllegalStateException("Cannot add a reply to another reply.");

        if (getParentNode() == null)
            throw new IllegalStateException("No parent node.");

        Comment comment = new Comment(getDocument(), author, initial, dateTime);
        ((INodeWithAnnotationId)comment).setIdInternal(CommentIdGenerator.generate(getDocument()));
        comment.setParentId(mId);
        comment.setText(text);

        // Insert the new reply after the last reply, or after the comment itself if there is none.
        Node insertAfterNode = this;
        Comment nextComment = ms.as(getNextNonAnnotationSibling(), Comment.class);
        while ((nextComment != null) && (nextComment.getParentId() == mId))
        {
            insertAfterNode = nextComment;
            nextComment = ms.as(nextComment.getNextNonAnnotationSibling(), Comment.class);
        }

        getParentNode().insertAfter(comment, insertAfterNode);

        CommentRangeFinder finder = new CommentRangeFinder();
        if (finder.find(getDocument(), mId)) // Comment may have no range nodes.
        {
            insertReplyRangeStartNode(finder.getRangeStart(), new CommentRangeStart(getDocument(), comment.getId()));
            insertReplyRangeEndNode(finder.getRangeEnd(), new CommentRangeEnd(getDocument(), comment.getId()));
        }

        return comment;
    }

	//JAVA-added public wrapper for internalized method
	public Comment addReply(String author, String initial, java.util.Date dateTime, String text) throws Exception
	{
		return addReplyInternal(author, initial, DateTime.fromJava(dateTime), text);
	}

    /// <summary>
    /// Finds a necessary place and inserts a rande start node of a reply.
    /// </summary>
    private void insertReplyRangeStartNode(CommentRangeStart parentRangeStart, CommentRangeStart insertingRangeStart)
    {
        // A range start node of a reply is inserted after the corresponding node of the comment or after
        // the corresponding node of the last reply if any is present.
        // If commentRangeStart node of the comment has displacedByCustomXml attribute with the 'prev' value,
        // MS Word inserts commentRangeStart node of the reply before the corresponding node of the comment.
        // It is ignored now since we do not read the displacedByCustomXml attribute of the node.

        ArrayList<Integer> replyIds = new ArrayList<Integer>();
        for (Comment reply : (Iterable<Comment>) getReplies())
            msArrayList.add(replyIds, reply.getId());

        Node start = parentRangeStart;
        while (start.getNextSibling() != null)
        {
            Node nextNode = start.getNextSibling();
            if ((nextNode.getNodeType() == NodeType.COMMENT_RANGE_START) &&
                replyIds.contains(((CommentRangeStart)nextNode).getId()))
                start = nextNode;
            else
                break;
        }

        start.insertNext(insertingRangeStart);
    }

    /// <summary>
    /// Finds a necessary place and inserts a rande end node of a reply.
    /// </summary>
    private void insertReplyRangeEndNode(CommentRangeEnd parentRangeEnd, CommentRangeEnd insertingRangeEnd)
    {
        // A range end node of a reply is inserted after the corresponding node of the comment or after
        // the comment node itself if it is the next sibling of the comment range end node. If the comment
        // already has other replies, a range end is inserted after the corresponding nodes of the replies.

        ArrayList<Integer> replyIds = new ArrayList<Integer>();
        for (Comment reply : (Iterable<Comment>) getReplies())
        {
            if (reply.getId() != insertingRangeEnd.getId())
                msArrayList.add(replyIds, reply.getId());
        }

        Node end = parentRangeEnd;
        while (end.getNextSibling() != null)
        {
            Node nextNode = end.getNextSibling();

            int id = -1;
            if (nextNode.getNodeType() == NodeType.COMMENT_RANGE_END)
                id = ((CommentRangeEnd)nextNode).getId();
            else if (nextNode.getNodeType() == NodeType.COMMENT)
                id = ((Comment)nextNode).getId();
            else
                break;

            if (replyIds.contains(id) || (id == mId))
                end = nextNode;
            else
                break;
        }
        end.insertNext(insertingRangeEnd);
    }

    /// <summary>
    /// Removes the specified reply to this comment.
    /// </summary>
    /// <remarks>All constituent nodes of the reply will be deleted from the document.</remarks>
    /// <param name="reply">The comment node of the deleting reply.</param>
    public void removeReply(Comment reply) throws Exception
    {
        CommentRangeFinder finder = new CommentRangeFinder();
        if (finder.find(getDocument(), reply.getId()))
        {
            finder.getRangeStart().remove();
            finder.getRangeEnd().remove();
        }
        reply.remove();
    }

    /// <summary>
    /// Removes all replies to this comment.
    /// </summary>
    /// <remarks> All constituent nodes of the replies will be deleted from the document.</remarks>
    public void removeAllReplies() throws Exception
    {
        for (Comment reply : getReplies().<Comment>toList())
            removeReply(reply);
    }

    /// <summary>
    /// Accepts a visitor for visiting the start of the comment.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptStart(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitCommentStart(this);
    }

    /// <summary>
    /// Accepts a visitor for visiting the end of the comment.
    /// </summary>
    /// <param name="visitor">The document visitor.</param>
    /// <returns>The action to be taken by the visitor.</returns>
    public /*override*/ /*VisitorAction*/int acceptEnd(DocumentVisitor visitor) throws Exception
    {
        return visitor.visitCommentEnd(this);
    }

    /// <summary>
    /// This is a convenience method that allows to easily set text of the comment.
    /// </summary>
    /// <remarks>
    /// <para>This method allows to quickly set text of a comment from a string. The string can contain
    /// paragraph breaks, this will create paragraphs of text in the comment accordingly.
    /// If you want to insert more complex elements into the comment, for example bookmarks
    /// or tables or apply rich formatting, then you need to use the appropriate node classes to
    /// build up the comment text.</para>
    /// </remarks>
    /// <param name="text">The new text of the comment.</param>
    public void setText(String text)
    {
        removeAllChildren();

        Paragraph para = new Paragraph(getDocument());
        para.getParagraphFormat().setStyleIdentifier(StyleIdentifier.COMMENT_TEXT);
        appendChild(para);

        DocumentBuilder builder = new DocumentBuilder(fetchDocument());
        builder.moveTo(para);

        // Create the annotation reference character. It is a position inside the comment text where MS Word
        // displays the comment author and number. It can be omitted, but let's create it as MS Word does.
        SpecialChar annotationRef = new SpecialChar(fetchDocument(), ControlChar.ANNOTATION_REF_CHAR, new RunPr());
        annotationRef.getFont().setStyleIdentifier(StyleIdentifier.COMMENT_REFERENCE);
        builder.insertNode(annotationRef);

        builder.write(text);
    }

    /// <summary>
    /// Returns the parent <see cref="Comment"/> object. Returns <c>null</c> for top-level comments.
    /// </summary>
    
    public Comment getAncestor()
    {
        if ((getDocument() == null) || (getParentId() == NO_PARENT))
            return null;

        // Make simplest implementation for a while.
        for (Comment comment : (Iterable<Comment>) getDocument().getChildNodes(NodeType.COMMENT, true))
            if (comment.getId() == getParentId())
                return comment;

        return null;
    }

    /// <summary>
    /// Returns a collection of <see cref="Comment"/> objects that are immediate children of the specified comment.
    /// </summary>
    public CommentCollection getReplies()
    {
        if (mReplies == null)
            mReplies = new CommentCollection(getDocument(), this);

        return mReplies;
    }

    /// <summary>
    /// Gets or sets flag indicating that the comment has been marked done.
    /// </summary>
    public boolean getDone() { return mDone; }
    public void setDone(boolean value) { mDone = value; }

    /// <summary>
    /// Gets or sets the parent comment ID. A value of <c>-1</c> means the comment has no parent.
    /// </summary>
    public int getParentId() { return mParentId; }
    public void setParentId(int value)
    {
        mParentId = value;
        if (getDocument() != null)
            getDocument().markChanged();
    }

    /// <summary>
    /// Comment durable identifier.
    /// </summary>
    /// <dev>
    /// Introduced in MS Word 2016. Looks like it is comment identifier, not associated with paragraph ID. The ID
    /// is used to refer to a comment from a commentsExtensible document part (2.1.5 of [MS-DOCX]).
    /// It's not clear now whether it is used in MS shared document editing, so let's store it.
    /// </dev>
    int getDurableId() { return mDurableId; };
    void setDurableId(int value) { mDurableId = value; };

    private int mDurableId;

    /// <summary>
    /// Specifies whether the comment is a follow-up.
    /// </summary>
    /// <remarks>
    /// The default value is <c>false</c>.
    /// The attribute MUST NOT be present on comments that are replies to another comment.
    /// When the property is <c>true</c>, the Office 365 version of Word ignores the content of the comment. Other
    /// versions of Word treat the comment as a regular comment.
    /// </remarks>
    boolean isIntelligentPlaceholder() { return mIsIntelligentPlaceholder; }; void isIntelligentPlaceholder(boolean value) { mIsIntelligentPlaceholder = value; };

    private boolean mIsIntelligentPlaceholder;

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


    /// <summary>
    /// Value of <see cref="ParentId" /> indicating that comment has no parent.
    /// </summary>
    static final int NO_PARENT = -1;

    /// <summary>
    /// Indicates that comment as marked done.
    /// </summary>
    private boolean mDone;

    private int mId;
    private int mParentId = NO_PARENT;
    private String mInitial = "";
    private String mAuthor = "";
    private DateTime mLocalDateTime = DateTime.MinValue; // Init for Java.
    private DateTime mDateTimeUtc = DateTime.MinValue; // Init for Java.

    private CommentCollection mReplies;
}

