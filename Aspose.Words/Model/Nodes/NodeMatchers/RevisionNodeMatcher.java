// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/07/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Selects only nodes with revisions.
/// </summary>
class RevisionNodeMatcher extends NodeMatcher
{
    /*override*/ boolean isMatch(Node node)
    {
        Debug.assertTrue(node != null);

        if ((getParentNode() != null) && !((getParentNode() == node) || node.isAncestorNode(getParentNode())))
            return false;

        return RevisionUtil.hasRevision(node);
    }

    /*override*/ boolean isSkipMarkupNodes() { return false; }

    Node getParentNode() { return mParentNode; }; void setParentNode(Node value) { mParentNode = value; };

    private Node mParentNode;
}

