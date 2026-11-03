// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Collections.msStack;
import java.util.ArrayList;


/// <summary>
/// Implements removing field nodes and field codes from a given node range.
/// Field result is not removed.
/// </summary>
class FieldRemover
{
    private FieldRemover()
    {
        // It is called from static method only.
    }

    /// <summary>
    /// Replaces fields with field results in the given node range. Preserves fields filtered by <paramref name="filter"/>.
    /// </summary>
    /// <param name="range">The node range.</param>
    /// <param name="filter">Determines wherever certain field should be replaced with result or not.</param>
    /// <remarks>
    /// A field must start inside the range to be processed correctly.
    /// Any stranded field characters (for example, a field separator without a matching field start inside the range), are removed.
    /// </remarks>
    /// <returns>
    /// A NodeRange object containing the remaining nodes of the original range. Null is returned if everything is removed.
    /// </returns>
    static NodeRange replaceFieldsWithResults(NodeRange range, IFieldRemoverFilter filter) throws Exception
    {
        if (range == null)
        {
            // Nothing to do.
            return null;
        }

        // Starting/ending range in the middle of the run causes range enumerator to clone the node.
        // For starting, it does not make any difference. A field cannot start before such node because it's the first node of the range.
        // For ending, the node may not be removed because range enumerator will split and clone such node.
        Debug.assertTrue(range.getEnd().isStart() || range.getEnd().isEnd(), "Split runs may not be removed correctly.");

        NodeEnumerator rangeEnumerator = new NodeEnumerator(range);
        try /*JAVA: was using*/
        {
            FieldRemover fieldRemover = new FieldRemover();

            Node lastVisitedNode = null;
            Node firstRemainingNode = null;
            Node lastRemainingNode = null;

            // Collect nodes to be removed.
            while (rangeEnumerator.moveToNextNode())
            {
                lastVisitedNode = rangeEnumerator.getCurrentNode();

                switch (lastVisitedNode.getNodeType())
                {
                    case NodeType.FIELD_START:
                    {
                        FieldStart fieldStart = (FieldStart)lastVisitedNode;
                        if (filter.needReplaceFieldWithResult(fieldStart))
                            fieldRemover.processFieldStart(fieldStart);

                        break;
                    }
                    case NodeType.FIELD_SEPARATOR:
                    {
                        FieldSeparator fieldSeparator = (FieldSeparator)lastVisitedNode;
                        if (filter.needReplaceFieldWithResult(fieldSeparator))
                            fieldRemover.processFieldSeparator(fieldSeparator);

                        break;
                    }
                    case NodeType.FIELD_END:
                    {
                        FieldEnd fieldEnd = (FieldEnd)lastVisitedNode;
                        if (filter.needReplaceFieldWithResult(fieldEnd))
                            fieldRemover.processFieldEnd(fieldEnd);

                        break;
                    }
                    default:
                    {
                        if (!fieldRemover.isInsideField())
                        {
                            // Remember the last node that was not removed.
                            lastRemainingNode = lastVisitedNode;
                            // Set the first node that was not removed.
                            if (firstRemainingNode == null)
                                firstRemainingNode = lastVisitedNode;
                        }

                        break;
                    }
                }
            }

            // Remove nodes.
            fieldRemover.removeFieldNodes();
            fieldRemover.removeLastRangeForIncompleteField(lastVisitedNode);

            return (firstRemainingNode != null)
                ? new NodeRange(firstRemainingNode, lastRemainingNode)
                : null;
        }
        finally { if (rangeEnumerator != null) rangeEnumerator.dispose(); }
    }

    private void processFieldStart(FieldStart fieldStart)
    {
        mFieldChars.push(fieldStart);
    }

    private void processFieldSeparator(FieldSeparator fieldSeparator)
    {
        if (isInsideField())
        {
            FieldChar lastFieldChar = mFieldChars.pop();
            if (lastFieldChar.getNodeType() == NodeType.FIELD_START)
            {
                if (isInsideField())
                {
                    // Remember that separator for this level is already met.
                    mFieldChars.push(fieldSeparator);
                }
                else if (FieldUtil.isFieldResultInvisible((FieldStart)lastFieldChar, fieldSeparator))
                {
                    // Field with invisible result must be treated as it has no separator, its result to be ignored.
                    mFieldChars.push(lastFieldChar);
                }
                else
                {
                    msArrayList.add(mNodeRangesToRemove, new NodePair(lastFieldChar, fieldSeparator));
                }
            }
            else
            {
                // A stranded separator in a field result of an inner field, ignore, push back.
                mFieldChars.push(lastFieldChar);
            }
        }
        else
        {
            // Remove a stranded separator.
            msArrayList.add(mNodesToRemove, fieldSeparator);
        }
    }

    private void processFieldEnd(FieldEnd fieldEnd)
    {
        if (isInsideField())
        {
            FieldChar lastFieldChar = mFieldChars.pop();
            if (lastFieldChar.getNodeType() == NodeType.FIELD_START)
            {
                // Field had no separator.
                if (!isInsideField())
                    msArrayList.add(mNodeRangesToRemove, new NodePair(lastFieldChar, fieldEnd));
            }
        }
        else
        {
            // A stranded field end, or maybe field separator is already met.
            msArrayList.add(mNodesToRemove, fieldEnd);
        }
    }

    /// <summary>
    /// Removes the range for the last field if it was not removed range ended before the field ended.
    /// </summary>
    /// <param name="leftBoundary"></param>
    private void removeLastRangeForIncompleteField(Node leftBoundary) throws Exception
    {
        if (isInsideField())
        {
            // Get the bottom of the stack
            FieldChar outmostFieldStart;
            do
            {
                outmostFieldStart = mFieldChars.pop();
            } while (mFieldChars.getCount() > 0);

            NodeRemover.remove(outmostFieldStart, leftBoundary);
        }
    }

    /// <summary>
    /// Removes the field nodes and ranges collected during range traversal.
    /// </summary>
    private void removeFieldNodes() throws Exception
    {
        for (Node node : mNodesToRemove)
            node.remove();

        // WORDSNET-16204 MS Word uses formating of next sibling paragraph.
        for (NodePair pair : (Iterable<NodePair>) mNodeRangesToRemove)
            NodeRemover.remove(pair.Start, true, pair.End, true, NodeJoinMode.JOIN_TO_NEXT_SIBLING);
    }

    /// <summary>
    /// Gets a boolean value indicating whether the current position is inside field code.
    /// </summary>
    private boolean isInsideField() { return mFieldChars.getCount() > 0; }

    private static class NodePair
    {
        NodePair(Node start, Node end)
        {
            Start = start;
            End = end;
        }

        /*final*/ Node Start;
        /*final*/ Node End;
    }

    private /*final*/ msStack<FieldChar> mFieldChars = new msStack<FieldChar>();
    private /*final*/ ArrayList<Node> mNodesToRemove = new ArrayList<Node>();
    private /*final*/ ArrayList<FieldRemover.NodePair> mNodeRangesToRemove = new ArrayList<FieldRemover.NodePair>();
}

