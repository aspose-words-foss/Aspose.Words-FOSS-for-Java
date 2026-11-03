// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/10/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;


/// <summary>
/// Class for collecting all necessary information about cloned nodes during import operations.
/// </summary>
class ImportInfoCollector implements INodeCloningListener
{
    /// <summary>
    /// Collects necessary information about current importing node.
    /// </summary>
    public void notifyNodeCloned(Node source, Node clone)
    {
        switch (clone.getNodeType())
        {
            case NodeType.SECTION:
            {
                if (mSrcDoc == null)
                    mSrcDoc = ((Section)source).getDocument();

                // Initialize at each new section.
                init();
                isLastSection(((Section)source).isLastSection());
                isFirstSection(((Section)source).isFirstSection());
                break;
            }
            case NodeType.BODY:
            {
                mBody = (Body)clone;
                mIsInBody = true;
                break;
            }
            case NodeType.HEADER_FOOTER:
            {
                mIsInBody = false;
                break;
            }
            case NodeType.PARAGRAPH:
            {
                // WORDSNET-22709 Collect info about used in direct attributes ListDefIds within source document.
                Paragraph srcPara = (Paragraph)source;
                if (!hasStyleListInDirectAttrs())
                {
                    Object listId = srcPara.getParaPr().get(ParaAttr.LIST_ID);
                    if (listId != null)
                    {
                        List list = mSrcDoc.getLists().getListByListId((/*int*/Integer)listId);
                        if ((list != null) && getSrcStylesListDefIds().contains(list.getListDefId()))
                            hasStyleListInDirectAttrs(true);
                    }
                }

                // We need info about nodes only inside a Body.
                if (mIsInBody)
                {
                    Paragraph para = (Paragraph)clone;

                    int listId = (/*int*/Integer)((IParaAttrSource)para).fetchParaAttr(ParaAttr.LIST_ID);
                    if (listId != 0)
                        mListIds.add(listId);
                }
                break;
            }
            case NodeType.RUN:
            {
                if ((getFirstRun() == null) && mIsInBody)
                    setFirstRun((Run)clone);
                break;
            }
            default:
                // do nothing
                break;
        }
    }

    /// <summary>
    /// Initializes class members.
    /// </summary>
    private void init()
    {
        mIsInBody = false;
        mBody = null;
        mFirstNode = null;
        mLastNode = null;
        mImportedParagraphs = null;
        mListIds.clear();
    }

    /// <summary>
    /// Gets first imported non-annotation node inside Body.
    /// </summary>
    Node getFirstNode()
    {
        if ((mFirstNode == null) && (mBody != null))
            mFirstNode = mBody.getFirstNonAnnotationChild();

        return mFirstNode;
    }

    /// <summary>
    /// Gets last imported non-annotation node inside Body.
    /// </summary>
    Node getLastNode()
    {
        if ((mLastNode == null) && (mBody != null))
            mLastNode = mBody.getLastNonAnnotationChild();

        return mLastNode;
    }

    /// <summary>
    /// Returns true, if first non-annotation imported node inside Body is a Table.
    /// </summary>
    boolean isFirstNodeTable() { return ((getFirstNode() != null) && (getFirstNode().getNodeType() == NodeType.TABLE)); }

    /// <summary>
    /// Returns true, if first non-annotation imported node inside Body is a SDT.
    /// </summary>
    boolean isFirstNodeSdt() { return ((getFirstNode() != null) && (getFirstNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)); }

    /// <summary>
    /// Returns true, if last non-annotation imported node inside Body is a SDT.
    /// </summary>
    boolean isLastNodeSdt() { return ((getLastNode() != null) && (getLastNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)); }

    /// <summary>
    /// Returns true, if imported content has lists.
    /// </summary>
    boolean hasLists() { return (mListIds.getCount() > 0); }

    /// <summary>
    /// Returns true, if imported content has exactly one list.
    /// </summary>
    boolean hasSingleList() { return (mListIds.getCount() == 1); }

    /// <summary>
    /// Gets deep collection of imported paragraphs inside Body.
    /// </summary>
    NodeCollection getImportedParagraphs()
    {
        if ((mImportedParagraphs == null) && (mBody != null))
            mImportedParagraphs = new NodeCollection(mBody, NodeType.PARAGRAPH, true);

        return mImportedParagraphs;
    }

    /// <summary>
    /// Returns true, if importing last section.
    /// </summary>
    boolean isLastSection() { return mIsLastSection; }; private void isLastSection(boolean value) { mIsLastSection = value; };

    private boolean mIsLastSection;

    /// <summary>
    /// Returns true, if importing first section.
    /// </summary>
    boolean isFirstSection() { return mIsFirstSection; }; private void isFirstSection(boolean value) { mIsFirstSection = value; };

    private boolean mIsFirstSection;

    /// <summary>
    /// Gets first imported Run inside a Body.
    /// </summary>
    Run getFirstRun() { return mFirstRun; }; private void setFirstRun(Run value) { mFirstRun = value; };

    private Run mFirstRun;

    /// <summary>
    /// The collection of ListDefIds used in styles of source document.
    /// </summary>
    private HashSetGeneric<Integer> getSrcStylesListDefIds()
    {
        if (mSrcStylesListDefIds == null)
        {
            mSrcStylesListDefIds = new HashSetGeneric<Integer>();
            for (Style style : mSrcDoc.getStyles())
            {
                Object listId = style.getParaPr().get(ParaAttr.LIST_ID);
                if (listId != null)
                {
                    List list = mSrcDoc.getLists().getListByListId((/*int*/Integer)listId);
                    if ((list != null) && !mSrcStylesListDefIds.contains(list.getListDefId()))
                        mSrcStylesListDefIds.add(list.getListDefId());
                }
            }
        }

        return mSrcStylesListDefIds;
    }

    /// <summary>
    /// Indicates either some imported paragraph has ListId in direct attributes in source document,
    /// with the same ListDefId as one of the numbered styles in this document.
    /// </summary>
    boolean hasStyleListInDirectAttrs() { return mHasStyleListInDirectAttrs; }; private void hasStyleListInDirectAttrs(boolean value) { mHasStyleListInDirectAttrs = value; };

    private boolean mHasStyleListInDirectAttrs;

    /// <summary>
    /// Indicates either cloned node is inside a Body.
    /// </summary>
    /// <remarks>
    /// Note, we mostly need info about nodes only inside a body. So, this flag is introduced just for a speed reason
    /// to avoid checking ancestor type for every cloned node.
    /// </remarks>
    private boolean mIsInBody;

    /// <summary>
    /// The HashSet of list ids of imported paragraphs.
    /// </summary>
    private /*final*/ HashSetGeneric<Integer> mListIds = new HashSetGeneric<Integer>();

    /// <summary>
    /// The HashSet of ListDefIds used in styles of source document.
    /// </summary>
    private HashSetGeneric<Integer> mSrcStylesListDefIds;

    private DocumentBase mSrcDoc;
    private Body mBody;
    private Node mFirstNode;
    private Node mLastNode;

    private NodeCollection mImportedParagraphs;
}

