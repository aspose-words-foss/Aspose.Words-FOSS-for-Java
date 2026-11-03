// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/02/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Denotes the start of a region of text that has a comment associated with it.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-comments/">Working with Comments</a> documentation article.</para>
/// </summary>
/// <remarks>
/// 
/// <para>To create a comment anchored to a region of text, you need to create a <see cref="Comment"/> and
/// then create <see cref="CommentRangeStart"/> and <see cref="CommentRangeEnd"/> and set their identifiers 
/// to the same <see cref="Comment.Id"/> value.</para>
/// 
/// <p><see cref="CommentRangeStart"/> is an inline-level node and can only be a child of <see cref="Paragraph"/>.</p>
/// 
/// <seealso cref="Comment"/>
/// <seealso cref="CommentRangeEnd"/>
/// </remarks>
public final class CommentRangeStart extends Node implements IDisplaceableByCustomXml, INodeWithAnnotationId
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <remarks>
    /// <p>When <see cref="CommentRangeStart"/> is created, it belongs to the specified document, but is not
    /// yet part of the document and <see cref="Node.ParentNode"/> is <c>null</c>.</p>
    ///
    /// <p>To append a <see cref="CommentRangeStart"/> to the document use InsertAfter or InsertBefore
    /// on the paragraph where you want the comment inserted.</p>
    ///
    /// </remarks>
    /// <param name="doc">The owner document.</param>
    /// <param name="id">The comment identifier to which this object is linked.</param>
    public CommentRangeStart(DocumentBase doc, int id)
    {
    	super(doc);
	
        mId = id;
    }

    /// <summary>
    /// Specifies the identifier of the comment to which this region is linked.
    /// </summary>
    public int getId() { return mId; }
    public void setId(int value) { mId = value; }

    /// <summary>
    /// Specifies that placement of the comment range node is directly linked with the location of the physical
    /// presentation of a custom XML element in the document.
    /// </summary>
    /*DisplacedByType*/int getDisplacedBy() { return mDisplacedBy; }
    void setDisplacedBy(/*DisplacedByType*/int value) { mDisplacedBy = value; }

    /// <summary>
    /// Specifies that placement of the comment range node is directly linked with the location of the physical
    /// presentation of a custom XML element in the document.
    /// </summary>
    /// <dev>
    /// The two same properties DisplacedBy and IDisplaceableByCustomXml.DisplacedByCustomXml have been implemented
    /// for Java porter. The porter does not support a case when a class contains "internal" property and explicitly
    /// defined interface property with same name.
    /// </dev>
    @ReservedForInternalUse @Deprecated
    public /*DisplacedByType*/int /*IDisplaceableByCustomXml.*/getDisplacedByCustomXml() { return mDisplacedBy; }
    @ReservedForInternalUse @Deprecated
    public void /*IDisplaceableByCustomXml.*/setDisplacedByCustomXml(/*DisplacedByType*/int value) { mDisplacedBy = value; }

    @ReservedForInternalUse @Deprecated
    public int /*INodeWithAnnotationId.*/getIdInternal() { return getId(); }
    @ReservedForInternalUse @Deprecated
    public void /*INodeWithAnnotationId.*/setIdInternal(int value) { mId = value; }

    @ReservedForInternalUse @Deprecated
    public int /*INodeWithAnnotationId.*/getParentIdInternal() { return Comment.NO_PARENT; }
    @ReservedForInternalUse @Deprecated
    public void /*INodeWithAnnotationId.*/setParentIdInternal(int value) { }

    /// <summary>
    /// Returns <see cref="Words.NodeType.CommentRangeStart"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.COMMENT_RANGE_START; }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitCommentRangeStart"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitCommentRangeStart(this));
    }


    private int mId;
    private /*DisplacedByType*/int mDisplacedBy = DisplacedByType.UNSPECIFIED;
}

