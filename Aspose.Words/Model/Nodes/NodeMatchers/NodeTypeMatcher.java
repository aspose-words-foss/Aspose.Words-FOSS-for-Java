// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2012 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Matches the node by type.
/// It's a default matcher for <see cref="NodeCollection"/>
/// </summary>
class NodeTypeMatcher extends NodeMatcher
{
    protected NodeTypeMatcher(/*NodeType*/int type)
    {
        mType = type;
        setupIsSkipMarkupNodes();
    }

    /// <summary>
    /// Gets matcher for the specified node type.
    /// </summary>
    /// <remarks>
    /// Performance optimization: use cached instances for the most used node types to reduce
    /// memory allocations and improve performance.
    /// </remarks>
    /// <param name="type">Node type.</param>
    /// <returns></returns>
    static NodeTypeMatcher getMatcher(/*NodeType*/int type)
    {
        switch (type)
        {
            case NodeType.ANY:
                return gAnyMatcher;
            case NodeType.SPECIAL_CHAR:
                return gSpecialCharMatcher;
            case NodeType.RUN:
                return gRunMatcher;
            default:
                return new NodeTypeMatcher(type);
        }
    }

    private void setupIsSkipMarkupNodes()
    {
        if ((mTypes != null) && (mTypes.length > 0))
        {
            for (int i = 0; i < mTypes.length; i++)
            {
                // Check if at least one non-skipped type exists.
                if (!NodeUtil.isSkipMarkupNodesInFlatEnumeration(mTypes[i]))
                {
                    mIsSkipMarkupNodes = false;
                    return;
                }
            }

            mIsSkipMarkupNodes = true;
            return;
        }

        mIsSkipMarkupNodes = NodeUtil.isSkipMarkupNodesInFlatEnumeration(mType);
    }

    public NodeTypeMatcher(/*NodeType*/int[] types)
    {
        mTypes = types;
        setupIsSkipMarkupNodes();
    }

    /*override*/ boolean isMatch(Node node) throws Exception
    {
        if (mTypes != null)
        {
            for (/*NodeType*/int nodeType : mTypes)
            {
                if (node.getNodeType() == nodeType)
                    return true;
            }
            return false;
        }
        return (mType == NodeType.ANY) || (node.getNodeType() == mType);
    }

    /*override*/ boolean isSkipMarkupNodes() { return mIsSkipMarkupNodes;  }

    // Keeping two separate fields to avoid unnecessary array allocations 
    // for optimal performance.
    private /*final*/ /*NodeType*/int mType = NodeType.ANY;
    private /*final*/ /*NodeType*/int[] mTypes;
    private boolean mIsSkipMarkupNodes;

    private static NodeTypeMatcher gAnyMatcher = new NodeTypeMatcher(NodeType.ANY);
    private static NodeTypeMatcher gSpecialCharMatcher = new NodeTypeMatcher(NodeType.SPECIAL_CHAR);
    private static NodeTypeMatcher gRunMatcher = new NodeTypeMatcher(NodeType.RUN);
}

