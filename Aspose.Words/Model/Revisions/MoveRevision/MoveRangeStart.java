// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Base class for MoveToRangeStart and  MoveFromRangeStart nodes of a Word document.
/// </summary>
abstract class MoveRangeStart extends Node implements INodeWithAnnotationId, IDisplaceableByCustomXml
{
    protected MoveRangeStart(
        DocumentBase doc,
        int id,
        String author,
        String name,
        DateTime dateTime,
        /*DisplacedByType*/int displacedBy)
    {
    	super(doc);
	
        setId(id);
        setAuthor(author);
        setName(name);
        setDate(dateTime);
        setDisplacedBy(displacedBy);
    }

    /// <summary>
    /// Specifies the identifier of the move range.
    /// </summary>
    int getId() { return mId; }; void setId(int value) { mId = value; };

    private int mId;

    /// <summary>
    /// Specifies the author for an annotation within a Word document.
    /// </summary>
    String getAuthor() { return mAuthor; }; void setAuthor(String value) { mAuthor = value; };

    private String mAuthor;

    /// <summary>
    /// Specifies the date information for an annotation within a Word document.
    /// </summary>
    DateTime getDate() { return mDate; }; void setDate(DateTime value) { mDate = value; };

    private DateTime mDate;

    /// <summary>
    /// Specifies the bookmark name.
    /// </summary>
    String getName() { return mName; }; void setName(String value) { mName = value; };

    private String mName;

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

    /// <summary>
    /// Returns original MS Word move range flags that sometimes specify if its a table column move range.
    /// </summary>
    int getFlags() { return mFlags; }; private void setFlags(int value) { mFlags = value; };

    private int mFlags;

    /// <summary>
    /// Returns true if this move range is a table column range.
    /// </summary>
    boolean isColumn() { return (getFlags() & 0x8000) == 0x8000; }

    /// <summary>
    /// Gets or sets the index of the first column in move range. Only valid if <see cref="IsColumn"/> is true.
    /// </summary>
    int getFirstColumn() { return getFlags() & 0x007F; }
    void setFirstColumn(int value)
    {
        int newValue = getFlags();

        newValue &= ~0x007F;
        newValue |= value & 0x007F;

        // Sets the flag indicating this is a table column move range.
        newValue |= 0x8000;
        setFlags(newValue);
    }

    /// <summary>
    /// Gets or sets the index of the last column in move range. Only valid if <see cref="IsColumn"/> is true.
    /// </summary>
    int getLastColumn()
    {
        // The flags contains the zero-based index of the first column beyond the end of the table column range
        // associated with the move range.
        return ((getFlags() & 0x7F00) >> 8) - 1;
    }
    void setLastColumn(int value)
    {
        int newValue = getFlags();

        newValue &= ~0x7F00;
        // Put value + 1, i.e. index of a column beyond the last column of the column range associated with
        // the move range.
        newValue |= ((value + 1) & 0x007F) << 8;

        // Sets the flag indicating this is a table column move range.
        newValue |= 0x8000;
        setFlags(newValue);
    }
}

