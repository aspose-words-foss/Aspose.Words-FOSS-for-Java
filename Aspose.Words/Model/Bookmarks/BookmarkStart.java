// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;


/// <summary>
/// Represents a start of a bookmark in a Word document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-bookmarks/">Working with Bookmarks</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>A complete bookmark in a Word document consists of a <see cref="BookmarkStart"/>
/// and a matching <see cref="BookmarkEnd"/> with the same bookmark name.</p>
///
/// <p><see cref="BookmarkStart"/> and <see cref="BookmarkEnd"/> are just markers inside a document
/// that specify where the bookmark starts and ends.</p>
///
/// <p>Use the <see cref="Bookmark"/> class as a "facade" to work with a bookmark
/// as a single object.</p>
/// </remarks>
public class BookmarkStart extends Node implements IBookmarkNode, IDisplaceableByCustomXml
{
    /// <summary>
    /// Initializes a new instance of the <see cref="BookmarkStart"/> class.
    /// </summary>
    /// <param name="doc">The owner document.</param>
    /// <param name="name">The name of the bookmark. Cannot be <c>null</c>.</param>
    public BookmarkStart(DocumentBase doc, String name)
    {
    	this(doc, name, 0);
	
    }

    BookmarkStart(DocumentBase doc)
    {
    	super(doc);
	
    }

    BookmarkStart(DocumentBase doc, String name, int flags)
    {
    	super(doc);
	
        ArgumentUtil.checkNotNull(name, "name");
        mName = name;
        mFlags = flags;
    }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.BookmarkStart"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.BOOKMARK_START; }

    /// <summary>
    /// Get the bookmark name.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// </remarks>
    String getNameInternal()
    {
        return mName;
    }

    /// <summary>
    /// Gets the facade object that encapsulates this bookmark start and end.
    /// </summary>
    public Bookmark getBookmark() { return new Bookmark(this); }

    /// <summary>
    /// Returns original MS Word bookmark flags that sometimes specify if its a table column bookmark.
    /// </summary>
    int getFlags() { return mFlags; }

    /// <summary>
    /// Returns <c>true</c> if this bookmark is a table column bookmark.
    /// </summary>
    boolean isColumn() { return ((mFlags & 0x8000) == 0x8000); }

    /// <summary>
    /// Gets the zero-based index of the first column of the table column range associated with the bookmark.
    /// </summary>
    /// <remarks>
    /// Returns <b>-1</b> if this bookmark is not a table column bookmark.
    /// </remarks>
    int getFirstColumn() { return (isColumn() ? (mFlags & 0x007F) : -1); }
    void setFirstColumn(int value)
    {
        int newValue = mFlags;

        newValue &= ~0x007F;
        newValue |= (value & 0x007F);
        // Sets the flag indicating this is a table column bookmark.
        newValue |= 0x8000;

        mFlags = newValue;
    }

    /// <summary>
    /// Gets the zero-based index of the last column of the table column range associated with the bookmark.
    /// </summary>
    /// <remarks>
    /// Returns <b>-1</b> if this bookmark is not a table column bookmark.
    /// </remarks>
    int getLastColumn()
    {
        // The flags contains the zero-based index of the first column beyond the end of the table column range
        // associated with the bookmark.
        return (isColumn() ? (((mFlags & 0x7F00) >> 8) - 1) : -1);
    }
    void setLastColumn(int value)
    {
        int newValue = mFlags;

        newValue &= ~0x7F00;
        // Put value + 1, i.e. index of a column beyond the last column of the column range associated with
        // the bookmark.
        newValue |= (((value + 1) & 0x007F) << 8);
        // Sets the flag indicating this is a table column bookmark.
        newValue |= 0x8000;

        mFlags = newValue;
    }

    /// <summary>
    /// Specifies that placement of the bookmark node is directly linked with the location of the physical 
    /// presentation of a custom XML element in the document.
    /// </summary>
    /*DisplacedByType*/int getDisplacedBy() { return mDisplacedBy; }
    void setDisplacedBy(/*DisplacedByType*/int value) { mDisplacedBy = value; }

    /// <summary>
    /// Specifies that placement of the bookmark node is directly linked with the location of the physical 
    /// presentation of a custom XML element in the document.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public /*DisplacedByType*/int /*IDisplaceableByCustomXml.*/getDisplacedByCustomXml() { return mDisplacedBy; }
    @ReservedForInternalUse @Deprecated
    public void /*IDisplaceableByCustomXml.*/setDisplacedByCustomXml(/*DisplacedByType*/int value) { mDisplacedBy = value; }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitBookmarkStart"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns><c>false</c> if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitBookmarkStart(this));
    }

    /// <summary>
    /// Returns an empty string.
    /// </summary>
    /// <returns>An empty string.</returns>
    public /*override*/ String getText()
    {
        return "";
    }

    /// <summary>
    /// Sets name of the bookmark.
    /// </summary>
    void setNameInternal(String name)
    {
        ArgumentUtil.checkNotNull(name, "name");
        mName = name;
    }


    //>>>>>>>> #region  IBookmarkNode implementation.
    /// <summary>
    /// Gets or sets the bookmark name.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// </remarks>
    public String getName() { return getNameInternal(); }
    public void setName(String value) { setNameInternal(value); }
    //<<<<<<<< #endregion 

    private String mName = "";
    private int mFlags;
    private /*DisplacedByType*/int mDisplacedBy = DisplacedByType.UNSPECIFIED;
}

