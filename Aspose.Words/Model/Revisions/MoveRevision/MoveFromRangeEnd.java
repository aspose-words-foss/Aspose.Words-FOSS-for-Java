// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a end of an moveFrom range in a Word document.
/// </summary>
/// <remarks>
/// <p>A complete moveFrom range in a Word document consists of a <see cref="MoveFromRangeStart"/>
/// and a matching <see cref="MoveFromRangeEnd"/> with the same Id.</p>
///
/// <p><see cref="MoveFromRangeStart"/> and <see cref="MoveFromRangeEnd"/> are just markers inside a document
/// that specify where the moveFrom range starts and ends.</p>
///
/// <note>Currently moveFrom ranges are supported only at the inline-level, that is inside <see cref="Paragraph"/>.</note>
/// </remarks>
class MoveFromRangeEnd extends MoveRangeEnd
{
    MoveFromRangeEnd(DocumentBase doc, int id, /*DisplacedByType*/int displacedBy)
    {
    	super(doc, id, displacedBy);
	
    }

    /// <summary>
    /// Corresponding MoveFromRangeStart, received by ID.
    /// </summary>
    MoveFromRangeStart getMoveFromRangeStart() { return (MoveFromRangeStart)MoveRangeFinder.findMoveRangeStart(getDocument(), getId()); }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitMoveFromRangeEnd"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns>False if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitMoveFromRangeEnd(this));
    }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.MoveFromRangeEnd"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.MOVE_FROM_RANGE_END; }
}

