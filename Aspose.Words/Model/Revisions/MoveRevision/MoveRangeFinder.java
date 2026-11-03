// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Finds a move range by Id or by Name.
/// </summary>
class MoveRangeFinder extends DocumentVisitor
{
    private MoveRangeFinder(int moveRangeId, boolean isLookingForStart)
    {
    	this(moveRangeId, isLookingForStart, "", false, MoveRevisionType.MOVE_TO);
	
    }

    private MoveRangeFinder(String moveRangeName, boolean isLookingForStart, /*MoveRevisionType*/int revisionType)
    {
    	this(-1, isLookingForStart, moveRangeName, true, revisionType);
	
    }

    private MoveRangeFinder(
        int moveRangeId,
        boolean isLookingForStart,
        String moveRangeName,
        boolean searchByName,
        /*MoveRevisionType*/int revisionType)
    {
        mMoveRangeId = moveRangeId;
        mIsLookingForStart = isLookingForStart;
        mMoveRangeName = moveRangeName;
        mDoSearchByName = searchByName;
        mRevisionType = revisionType;
    }

    static MoveRange findMoveRange(Node node, boolean isMoveTo)
    {
        return new MoveRange(node, isMoveTo);
    }

    /// <summary>
    /// Finds move range start.
    /// </summary>
    static MoveRangeStart findMoveRangeStart(Node node, boolean isMoveTo)
    {
        /*NodeType*/int nodeType = isMoveTo ? NodeType.MOVE_TO_RANGE_START : NodeType.MOVE_FROM_RANGE_START;
        // Need to return range around end of a paragraph. Probably it is the same with other composites.
        if (node.isComposite())
        {
            // Search for move range start as child of the node.
            MoveRangeStart start = (MoveRangeStart)((CompositeNode)node).getChild(nodeType, -1, true);
            if (start != null)
            {
                Node end = findMoveRangeEnd(node.getDocument(), start.getId());
                // Looks like move ranges cannot be nested: if the range is ended inside the input node,
                // just return null.
                return (end != null) && !end.isAncestorNode(node)
                    ? start
                    : null;
            }
        }

        return (MoveRangeStart)node.previousPreOrderOfType(node.getDocument(), nodeType);
    }

    /// <summary>
    /// Helper function to find an move range start in the document by Id. Returns the move range start or null.
    /// </summary>
    static MoveRangeStart findMoveRangeStart(Node node, int moveRangeId)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	MoveRangeFinder finder = new MoveRangeFinder(moveRangeId, true);
        	node.accept(finder);

        	return finder.mMoveRangeStart;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Helper function to find a move range start in the document by Name. Returns the move range start or null.
    /// </summary>
    /// <remarks>
    /// MoveRange Name is unique for a pair of MoveFromRange and MoveToRange. That why we should specify MoveRevisionType,
    /// when searching MoveToRangeStart or MoveFromRangeStart by Name.
    /// </remarks>
    static MoveRangeStart findMoveRangeStartByName(Node node, String moveRangeName, /*MoveRevisionType*/int revisionType)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	MoveRangeFinder finder = new MoveRangeFinder(moveRangeName, true, revisionType);
        	node.accept(finder);

        	return finder.mMoveRangeStart;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Helper function to find an move range end in the document by Id. Returns the move range end or null.
    /// </summary>
    static MoveRangeEnd findMoveRangeEnd(Node node, int moveRangeId)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	if (node == null)
            	throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "node");

        	MoveRangeFinder finder = new MoveRangeFinder(moveRangeId, false);
        	node.accept(finder);

        	return finder.mMoveRangeEnd;
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /*override*/ /*VisitorAction*/int visitMoveToRangeStart(MoveRangeStart moveToRangeStart)
    {
        return visitRangeStart(moveToRangeStart, MoveRevisionType.MOVE_TO);
    }

    /*override*/ /*VisitorAction*/int visitMoveToRangeEnd(MoveRangeEnd moveToRangeEnd)
    {
        return visitRangeEnd(moveToRangeEnd);
    }

    /*override*/ /*VisitorAction*/int visitMoveFromRangeStart(MoveRangeStart moveFromRangeStart)
    {
        return visitRangeStart(moveFromRangeStart, MoveRevisionType.MOVE_FROM);
    }

    /*override*/ /*VisitorAction*/int visitMoveFromRangeEnd(MoveRangeEnd moveFromRangeEnd)
    {
        return visitRangeEnd(moveFromRangeEnd);
    }

    private /*VisitorAction*/int visitRangeStart(MoveRangeStart moveRangeStart, /*MoveRevisionType*/int revisionType)
    {
        if (!mIsLookingForStart)
            return VisitorAction.CONTINUE;

        if (mDoSearchByName)
        {
            if ((mRevisionType == revisionType) && (msString.equals(mMoveRangeName, moveRangeStart.getName())))
            {
                mMoveRangeStart = moveRangeStart;
                return VisitorAction.STOP;
            }
        }
        else
        {
            if (mMoveRangeId == moveRangeStart.getId())
            {
                mMoveRangeStart = moveRangeStart;
                return VisitorAction.STOP;
            }
        }

        return VisitorAction.CONTINUE;
    }

    private /*VisitorAction*/int visitRangeEnd(MoveRangeEnd moveRangeEnd)
    {
        if (mIsLookingForStart)
            return VisitorAction.CONTINUE;

        if (mMoveRangeId != moveRangeEnd.getId())
            return VisitorAction.CONTINUE;

        mMoveRangeEnd = moveRangeEnd;
        return VisitorAction.STOP;
    }


    private /*final*/ String mMoveRangeName;
    private /*final*/ int mMoveRangeId;
    private /*final*/ boolean mIsLookingForStart;
    private /*final*/ boolean mDoSearchByName;
    private /*final*/ /*MoveRevisionType*/int mRevisionType;
    private MoveRangeStart mMoveRangeStart;
    private MoveRangeEnd mMoveRangeEnd;
}

