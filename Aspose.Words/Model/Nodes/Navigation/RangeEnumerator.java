// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/12/2024 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Enumerates a range of nodes.
/// </summary>
class RangeEnumerator extends NodeEnumerator implements IDocumentPositionListener
{
    RangeEnumerator(NodeRange range)
    {
    	super(range, false);
	
    }

    /// <summary>
    /// Returns the suitable node for changing position when moving above.
    /// </summary>
    private Node findValidNodeAbove()
    {
        return getValidParent(getCurrentNode());
    }

    /// <summary>
    /// Returns the suitable node for changing position when moving below.
    /// </summary>
    private Node findValidNodeBelow()
    {
        if (getCurrentNode().getNodeType() == NodeType.BODY)
            return getCurrentNode();

        if (getCurrentNode().getNodeType() == NodeType.HEADER_FOOTER)
            return ((Section)getCurrentNode().getParentNode()).getBody();

        return getValidChild(getCurrentNode());
    }

    /// <summary>
    /// Returns True if the current range ends with the included StructuredDocumentTagRangeStart.
    /// </summary>
    private boolean isEndingRangedSdt()
    {
        return getRange().isEndIncluded() &&
               (getRange().getEnd().getNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START);
    }

    /// <summary>
    /// Returns True if the end node of the range is achieved and it is StructuredDocumentTagRangeStart.
    /// </summary>
    private boolean isRangedSdtStartAchieved()
    {
        return isEndingRangedSdt() &&
               isEndNode() && (getCurrentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START);
    }

    /// <summary>
    /// Returns True if StructuredDocumentTagRangeEnd node is achieved and it matches the start node of the ranged SDT.
    /// </summary>
    private boolean isRangedSdtEndAchieved()
    {
        if (!isEndingRangedSdt() || (getCurrentNode().getNodeType() != NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END))
            return false;

        StructuredDocumentTagRangeStart sdtStart = (StructuredDocumentTagRangeStart)getRange().getEnd().getNode();
        StructuredDocumentTagRangeEnd sdtEnd = (StructuredDocumentTagRangeEnd)getCurrentNode();

        return sdtStart.getRangeEnd() == sdtEnd;
    }

    public void /*IDocumentPositionListener.*/notifyMoved(/*DocumentPositionMovement*/int movement)
    {
        switch (movement)
        {
            case DocumentPositionMovement.ABOVE:
            {
                Node newNode = findValidNodeAbove();
                if (newNode != getCurrentNode())
                    setCurrentPosition(DocumentPosition.createPositionAfter(newNode));

                break;
            }
            case DocumentPositionMovement.BELOW:
            {
                Node newNode = findValidNodeBelow();
                if (newNode != getCurrentNode())
                    setCurrentPosition(DocumentPosition.createPositionBefore(newNode));

                break;
            }
            case DocumentPositionMovement.START_END:
            {
                if (isRangedSdtStartAchieved())
                    getCurrentPosition().move(null, true, true, true, false, true, this);
                else if (isRangedSdtEndAchieved())
                    setCurrentPosition(DocumentPosition.createPositionAfter(getRange().getEnd().getNode()));

                break;
            }
            default:
                break;
        }
    }
}

