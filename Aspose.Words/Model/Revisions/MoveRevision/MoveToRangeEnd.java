// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a end of an moveTo range in a Word document.
/// </summary>
/// <remarks>
/// <p>A complete moveTo range in a Word document consists of a <see cref="MoveToRangeStart"/>
/// and a matching <see cref="MoveToRangeEnd"/> with the same Id.</p>
///
/// <p><see cref="MoveToRangeStart"/> and <see cref="MoveToRangeEnd"/> are just markers inside a document
/// that specify where the moveTo range starts and ends.</p>
///
/// <note>Currently moveTo ranges are supported only at the inline-level, that is inside <see cref="Paragraph"/>.</note>
/// </remarks>
class MoveToRangeEnd extends MoveRangeEnd
{
    MoveToRangeEnd(DocumentBase doc, int id, /*DisplacedByType*/int displacedBy)
    {
    	super(doc, id, displacedBy);
	
    }

    /// <summary>
    /// Corresponding MoveToRangeStart, received by ID.
    /// </summary>
    MoveToRangeStart getMoveToRangeStart() { return (MoveToRangeStart)MoveRangeFinder.findMoveRangeStart(getDocument(), getId()); }

    /// <summary>
    /// Accepts a visitor.
    /// </summary>
    /// <remarks>
    /// <p>Calls <see cref="DocumentVisitor.VisitMoveToRangeEnd"/>.</p>
    /// <p>For more info see the Visitor design pattern.</p>
    /// </remarks>
    /// <param name="visitor">The visitor that will visit the node.</param>
    /// <returns>False if the visitor requested the enumeration to stop.</returns>
    public /*override*/ boolean accept(DocumentVisitor visitor) throws Exception
    {
        return visitorActionToBool(visitor.visitMoveToRangeEnd(this));
    }

    /// <summary>
    /// Returns <see cref="Aspose.Words.NodeType.MoveToRangeEnd"/>.
    /// </summary>
    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.MOVE_TO_RANGE_END; }
}

