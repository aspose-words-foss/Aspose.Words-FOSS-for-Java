// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/05/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents an end of an editable range in a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A complete editable range  in a Word document consists of a <see cref="EditableRangeStart"/>
/// and a matching <see cref="EditableRangeEnd"/> with the same Id.</p>
///
/// <p><see cref="EditableRangeStart"/> and <see cref="EditableRangeEnd"/> are just markers inside a document
/// that specify where the editable range starts and ends.</p>
///
/// <p>Use the <see cref="EditableRange"/> class as a "facade" to work with an editable range 
/// as a single object.</p>
///
/// <note>Currently editable ranges are supported only at the inline-level, that is inside <see cref="Paragraph"/>,
/// but editable range start and editable range end can be in different paragraphs.</note>
/// </remarks>
public final class EditableRangeEnd extends Node implements IDisplaceableByCustomXml, INodeWithAnnotationId
{
    EditableRangeEnd(DocumentBase doc)
    {
    	super(doc);
	
    }

    EditableRangeEnd(DocumentBase doc, int id)
    {
    	super(doc);
	
        mId = id;
    }

    /// <summary>
    /// Corresponding <see cref="Aspose.Words.EditableRangeStart"/>, received by ID.
    /// </summary>
    public EditableRangeStart getEditableRangeStart() throws Exception { return EditableRangeFinder.findEditableRangeStart(this.getDocument(), getId()); }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitEditableRangeEnd"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitEditableRangeEnd(this));
    }

    /// <summary>
    /// Specifies the identifier of the editable range.
    /// </summary>
    public int getId() { return mId; }
    public void setId(int value) { mId = value; }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.EditableRangeEnd"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.EDITABLE_RANGE_END; }

    /// <summary>
    /// Specifies that placement of the editable range node is directly linked with the location of the physical
    /// presentation of a custom XML element in the document.
    /// </summary>
    /*DisplacedByType*/int getDisplacedBy() { return mDisplacedBy; }
    void setDisplacedBy(/*DisplacedByType*/int value) { mDisplacedBy = value; }

    /// <summary>
    /// Specifies that placement of the editable range node is directly linked with the location of the physical
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

    private int mId;
    private /*DisplacedByType*/int mDisplacedBy = DisplacedByType.UNSPECIFIED;
}

