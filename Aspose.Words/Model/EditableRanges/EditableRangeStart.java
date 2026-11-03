// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/05/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a start of an editable range in a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A complete editable range in a Word document consists of a <see cref="EditableRangeStart"/>
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
public final class EditableRangeStart extends Node implements IDisplaceableByCustomXml, INodeWithAnnotationId
{
    EditableRangeStart(DocumentBase doc)
    {
    	this(doc, doc.getNextAnnotationId());
	
    }

    EditableRangeStart(DocumentBase doc, int id)
    {
    	super(doc);
	
        mId = id;
    }

    EditableRangeStart(DocumentBase doc, int id, String ed, /*EditorType*/int edGrp)
    {
    	this (doc, id, ed, edGrp, 0);
	
    }

    EditableRangeStart(DocumentBase doc, int id, String ed, /*EditorType*/int edGrp, int flags)
    {
    	super(doc);
	
        mId = id;
        mEd = ed;
        mEdGrp = edGrp;
        mFlags = flags;
    }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitEditableRangeStart"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitEditableRangeStart(this));
    }

    /// <summary>
    /// Specifies the identifier of the editable range.
    /// </summary>
    public int getId() { return mId; }
    public void setId(int value) { mId = value; }

    /// <summary>
    /// Gets the facade object that encapsulates this editable range start and end.
    /// </summary>
    public EditableRange getEditableRange() { return new EditableRange(this); }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.EditableRangeStart"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.EDITABLE_RANGE_START; }

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

    /// <summary>
    /// Returns original MS Word editable range flags that sometimes specify if its a table column editable range.
    /// </summary>
    int getFlags() { return mFlags; }

    /// <summary>
    /// Specifies the single user for editable range.
    /// </summary>
    String getSingleUser() { return mEd; }
    void setSingleUser(String value) { mEd = value; }

    /// <summary>
    /// Specifies the editor group for editable range.
    /// </summary>
    /*EditorType*/int getEditorGroup() { return mEdGrp; }
    void setEditorGroup(/*EditorType*/int value) { mEdGrp = value; }

    /// <summary>
    /// Returns true if this EditableRange is a table column range.
    /// </summary>
    boolean isColumn() { return ((mFlags & 0x8000) == 0x8000); }

    /// <summary>
    /// Gets or sets the index of the first column in editable range. Only valid if <see cref="IsColumn"/> is true.
    /// </summary>
    int getFirstColumn() { return (mFlags & 0x007F); }
    void setFirstColumn(int value)
    {
        int newValue = mFlags;

        newValue &= ~0x007F;
        newValue |= (value & 0x007F);

        // Sets the flag indicating this is a table column editable range.
        newValue |= 0x8000;
        mFlags = newValue;
    }

    /// <summary>
    /// Gets or sets the index of the last column in editable range. Only valid if <see cref="IsColumn"/> is true.
    /// </summary>
    int getLastColumn()
    {
        // The flags contains the zero-based index of the first column beyond the end of the table column range
        // associated with the editable range.
        return (((mFlags & 0x7F00) >> 8) - 1);
    }
    void setLastColumn(int value)
    {
        int newValue = mFlags;

        newValue &= ~0x7F00;
        // Put value + 1, i.e. index of a column beyond the last column of the column range associated with
        // the editable range.
        newValue |= (((value + 1) & 0x007F) << 8);

        // Sets the flag indicating this is a table column editable range.
        newValue |= 0x8000;
        mFlags = newValue;
    }

    private int mId;
    private int mFlags;
    private String mEd = "";
    private /*EditorType*/int mEdGrp;
    private /*DisplacedByType*/int mDisplacedBy = DisplacedByType.UNSPECIFIED;
}

