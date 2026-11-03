// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2021 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Regenerates identifiers of cloned comment nodes (<see cref="Comment"/>,
/// <see cref="CommentRangeStart"/> and <see cref="CommentRangeEnd"/>).
/// </summary>
class CommentIdsRegenerator
{
    void regenerateCommentIds(Node node)
    {
        if ((node.getNodeType() != NodeType.COMMENT) &&
            (node.getNodeType() != NodeType.COMMENT_RANGE_START) &&
            (node.getNodeType() != NodeType.COMMENT_RANGE_END))
        {
            return;
        }

        INodeWithAnnotationId nodeWithAnnotationId = (INodeWithAnnotationId)node;

        if (mClonedCommentIdMap == null)
            mClonedCommentIdMap = new HashMap<Integer, Integer>();

        int newId = 0;
        RefInt referenceToNewId = new RefInt(newId);
        boolean outRefCondition0 = !msDictionary.tryGetValue(mClonedCommentIdMap, nodeWithAnnotationId.getIdInternal(), /*out*/ referenceToNewId);
        newId = referenceToNewId.get();
        if (outRefCondition0)
        {
            newId = node.getDocument().getNextAnnotationId();
            msDictionary.add(mClonedCommentIdMap, nodeWithAnnotationId.getIdInternal(), newId);
        }

        nodeWithAnnotationId.setIdInternal(newId);

        int parentNewId = 0;
        RefInt referenceToParentNewId = new RefInt(parentNewId);
        boolean outRefCondition1 = msDictionary.tryGetValue(mClonedCommentIdMap, nodeWithAnnotationId.getParentIdInternal(), /*out*/ referenceToParentNewId);
        parentNewId = referenceToParentNewId.get();
        if (outRefCondition1)
            nodeWithAnnotationId.setParentIdInternal(parentNewId);
    }

    private HashMap<Integer, Integer> mClonedCommentIdMap;
}

