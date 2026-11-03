// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base class for MoveToRangeEnd and  MoveFromRangeEnd nodes of a Word document.
/// </summary>
abstract class MoveRangeEnd extends Node implements INodeWithAnnotationId, IDisplaceableByCustomXml
{
    protected MoveRangeEnd(DocumentBase doc, int id, /*DisplacedByType*/int displacedBy)
    {
    	super(doc);
	
        setId(id);
        setDisplacedBy(displacedBy);
    }

    /// <summary>
    /// Specifies the identifier of the move range.
    /// </summary>
    int getId() { return mId; }; void setId(int value) { mId = value; };

    private int mId;

    /// <summary>
    /// Specifies that the parent annotation's placement shall be directly linked with the location
    /// of the physical presentation of a custom XML element in the document.
    /// </summary>
    /*DisplacedByType*/int getDisplacedBy() { return mDisplacedBy; }; void setDisplacedBy(/*DisplacedByType*/int value) { mDisplacedBy = value; };

    private /*DisplacedByType*/int mDisplacedBy;

    /// <summary>
    /// Specifies that the parent annotation's placement shall be directly linked with the location
    /// of the physical presentation of a custom XML element in the document.
    /// </summary>
    /// <dev>
    /// The two same properties DisplacedBy and IDisplaceableByCustomXml.DisplacedByCustomXml have been implemented
    /// to be similar as in bookmark nodes, in which it is done for Java porter. The porter does not support a case
    /// when a class contains "internal" property and explicitly defined interface property with same name.
    /// </dev>
    public /*DisplacedByType*/int /*IDisplaceableByCustomXml.*/getDisplacedByCustomXml() { return getDisplacedBy(); }
    public void /*IDisplaceableByCustomXml.*/setDisplacedByCustomXml(/*DisplacedByType*/int value) { setDisplacedBy(value); }

    public int /*INodeWithAnnotationId.*/getIdInternal() { return getId(); }
    public void /*INodeWithAnnotationId.*/setIdInternal(int value) { setId(value); }

    public int /*INodeWithAnnotationId.*/getParentIdInternal() { return Comment.NO_PARENT; }
    public void /*INodeWithAnnotationId.*/setParentIdInternal(int value) { }
}

