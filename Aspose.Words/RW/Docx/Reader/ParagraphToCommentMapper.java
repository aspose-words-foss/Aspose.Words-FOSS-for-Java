// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;
import com.aspose.IntToObjDictionary;
import java.util.HashMap;
import java.util.Map;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.RandomUtil;


/// <summary>
/// Helper class for mapping paragraphs to comments.
/// </summary>
class ParagraphToCommentMapper
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ParagraphToCommentMapper()
    {
        mDurableIdsUsedInComments = new HashSetGeneric<Integer>();
        mParaIdsUsedInComments = new HashSetGeneric<Integer>();
        mParaIdToCommentEx = new IntToObjDictionary<CommentEx>();
        mParaIdToCommentExtensible = new HashMap<Integer, CommentExtensible>();
        mParaIdToParagraphMap = new IntToObjDictionary<Paragraph>();
    }

    /// <summary>
    /// Applies the stored CommentEx data to the comments.
    /// </summary>
    void applyCommentExData()
    {
        IntToObjDictionary<CommentEx>.Enumerator enumerator = getParaIdToCommentEx().iterator();

        while (enumerator.moveNext())
        {
            int paraId = enumerator.getCurrentKey();
            CommentEx commentEx = enumerator.getCurrentValue();

            Paragraph para = getParaIdToParagraphMap().get(paraId);
            if (para == null)
                continue;

            Comment comment = (Comment)para.getAncestor(NodeType.COMMENT);

            // Update Done mark.
            comment.setDone(commentEx.Done);

            Paragraph parentPara = getParaIdToParagraphMap().get(commentEx.ParentParaId);
            if (parentPara == null)
                continue;

            Comment parentComment = (Comment)parentPara.getAncestor(NodeType.COMMENT);

            // Update reference to parent comment.
            comment.setParentId(parentComment.getId());
        }
    }

    /// <summary>
    /// Applies the stored CommentExtensible data to the comments.
    /// </summary>
    void applyCommentExtensibleData()
    {
        for (Map.Entry<Integer, CommentExtensible> item : getParaIdToCommentExtensible().entrySet())
        {
            int paraId = item.getKey();
            CommentExtensible extensible = item.getValue();

            Paragraph para = getParaIdToParagraphMap().get(paraId);
            if (para == null)
                continue;

            Comment comment = (Comment)para.getAncestor(NodeType.COMMENT);

            comment.setDurableId(extensible.getDurableId());
            comment.setDateTimeUtc(extensible.getUtcDateTime());
            comment.setReactions(extensible.getReactions());
            comment.isIntelligentPlaceholder(extensible.isIntelligentPlaceholder());
        }
    }

    /// <summary>
    /// Finds a <see cref="CommentExtensible"/> object stored on reading commentsIds document part.
    /// </summary>
    CommentExtensible findCommentExtensible(int durableId)
    {
        for (CommentExtensible extensible : (Iterable<CommentExtensible>) getParaIdToCommentExtensible().values())
        {
            if (extensible.getDurableId() == durableId)
                return extensible;
        }
        return null;
    }

    /// <summary>
    /// Maps the specified ParaId and DurableId.
    /// </summary>
    void mapParaIdAndDurableId(int paraId, int durableId)
    {
        int uniqueDurableId = durableId;

        if (getDurableIdsUsedInComments().contains(uniqueDurableId))
            uniqueDurableId = getParaIdToCommentExtensible().containsKey(paraId) ? uniqueDurableId : generateAndStoreDurableId();
        else
            getDurableIdsUsedInComments().add(uniqueDurableId);

        getParaIdToCommentExtensible().put(paraId, new CommentExtensible(paraId, uniqueDurableId));
    }

    /// <summary>
    /// Maps the specified ParaId and paragraph.
    /// </summary>
    void mapParaIdAndParagraph(int paraId, Paragraph para)
    {
        if ((paraId != Integer.MIN_VALUE) &&
            (
                getParaIdToCommentEx().containsKey(paraId) ||
                getParaIdToCommentExtensible().containsKey(paraId)
            ))
        {
            getParaIdToParagraphMap().add(paraId, para);
        }
    }

    /// <summary>
    /// Maps the specified ParaId and ParaId of the parent paragraph.
    /// </summary>
    void mapParaIdAndParentParaId(int paraId, int paraIdParent, boolean done)
    {
        // WORDSNET-24514 Use last "CommentEx" occurrence from the sequence.
        getParaIdToCommentEx().set(paraId, new CommentEx(paraIdParent, done));
    }

    /// <summary>
    /// Maps the specified ParaId and resolves duplicate ParaIds if needed.
    /// </summary>
    int mapParaIdAndResolveDuplicateIfNeeded(int paraId)
    {
        if (!getParaIdsUsedInComments().contains(paraId))
        {
            getParaIdsUsedInComments().add(paraId);
            return paraId;
        }

        int uniqueParaId = generateAndStoreParaId();
        if (getParaIdToCommentEx().containsKey(paraId))
        {
            getParaIdToCommentEx().add(uniqueParaId, new CommentEx(0, false));
            int newDurableId = generateAndStoreDurableId();
            msDictionary.add(getParaIdToCommentExtensible(), uniqueParaId, new CommentExtensible(uniqueParaId, newDurableId));
        }
        return uniqueParaId;
    }

    /// <summary>
    /// Generates a unique ParaId and stores it.
    /// </summary>
    private int generateAndStoreParaId()
    {
        return generateAndStoreIdToStorage(getParaIdsUsedInComments(), "ParaId");
    }

    /// <summary>
    /// Generates a unique DurableId and stores it.
    /// </summary>
    private int generateAndStoreDurableId()
    {
        return generateAndStoreIdToStorage(getDurableIdsUsedInComments(), "DurableId");
    }

    /// <summary>
    /// Generates a unique identifier and stores it in the specified storage.
    /// </summary>
    private static int generateAndStoreIdToStorage(HashSetGeneric<Integer> storage, String initializer)
    {
        final int MASK = 0x7FFFFFFF;

        while (true)
        {
            int id = (RandomUtil.newGuid(initializer).hashCode() & MASK);
            if ((id == 0) || storage.contains(id))
                continue;

            storage.add(id);
            return id;
        }
    }

    /// <summary>
    /// ParaIds used in comments.
    /// </summary>
    private HashSetGeneric<Integer> getParaIdsUsedInComments() { return mParaIdsUsedInComments; };

    private HashSetGeneric<Integer> mParaIdsUsedInComments;

    /// <summary>
    /// DurableIds used in comments.
    /// </summary>
    private HashSetGeneric<Integer> getDurableIdsUsedInComments() { return mDurableIdsUsedInComments; };

    private HashSetGeneric<Integer> mDurableIdsUsedInComments;

    /// <summary>
    /// Maps ParaId to CommentEx object which holds information about parent paragraph and done mark.
    /// </summary>
    private IntToObjDictionary<CommentEx> getParaIdToCommentEx() { return mParaIdToCommentEx; };

    private IntToObjDictionary<CommentEx> mParaIdToCommentEx;

    /// <summary>
    /// Maps ParaId to <see cref="CommentExtensible"/> object which stores additional comment information.
    /// </summary>
    private HashMap<Integer, CommentExtensible> getParaIdToCommentExtensible() { return mParaIdToCommentExtensible; };

    private HashMap<Integer, CommentExtensible> mParaIdToCommentExtensible;

    /// <summary>
    /// Maps ParaId to Paragraph node.
    /// </summary>
    /// <remarks>
    /// Currently collected only Ids referred from CommentEx part.
    /// </remarks>
    private IntToObjDictionary<Paragraph> getParaIdToParagraphMap() { return mParaIdToParagraphMap; };

    private IntToObjDictionary<Paragraph> mParaIdToParagraphMap;
}

