// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/08/2007 by Vladimir Averkin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.RandomUtil;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.IntToIntDictionary;


/// <summary>
/// Instance class for writing comments into the "Comments" package part.
/// </summary>
class DocxCommentsWriter
{
    DocxCommentsWriter(DocxDocumentWriterBase writer) throws Exception
    {
        mWriter = writer;

        if (writer.getCompliance() != OoxmlComplianceCore.ECMA_376)
        {
            mBuilderEx = mWriter.createChildPartAndBuilder("commentsExtended.xml", DocxContentType.COMMENTS_EXTENDED, writer.getRelTypes().getCommentsEx());
            mBuilderEx.startDocumentWithStandardNamespaces("w15:commentsEx");

            if (mWriter.getMsWordExtensionsVersion() >= MsWordVersionCore.WORD_2016)
            {
                mBuilderIds = mWriter.createChildPartAndBuilder("commentsIds.xml",
                    DocxContentType.COMMENTS_IDS, writer.getRelTypes().getCommentsIds());
                mBuilderIds.startDocumentWithStandardNamespaces("w16cid:commentsIds");
            }

            if (mWriter.getMsWordExtensionsVersion() >= MsWordVersionCore.WORD_2019)
            {
                mBuilderExtensible = mWriter.createChildPartAndBuilder("commentsExtensible.xml",
                    DocxContentType.COMMENTS_EXTENSIBLE, writer.getRelTypes().getCommentsExtensible());
                mBuilderExtensible.startDocumentWithStandardNamespaces("w16cex:commentsExtensible");
            }
        }

        mBuilder = mWriter.createChildPartAndBuilder("comments.xml", DocxContentType.COMMENTS, writer.getRelTypes().getComments());
        mBuilder.startDocumentWithStandardNamespaces("w:comments");
    }

    /// <summary>
    /// Writes a w:comment element start into the "Comments" package part.
    /// </summary>
    void writeStart(Comment comment, int annotationId)
    {
        int paraId = getOrGenerateParaId(comment);

        writeCommentEx(comment, paraId);
        writeCommentId(comment, paraId);
        writeCommentExtensible(comment);

        mBuilder.startElement("w:comment");
        mBuilder.writeAttribute("w:id", annotationId);
        mBuilder.writeAttribute("w:author", comment.getAuthor());

        if (!DateTime.equals(comment.getLocalDateTime(), DateTime.MinValue))
        {
            // On saving a document, MS Word writes comment local time as is, without conversion to UTC. Do the same.
            mBuilder.writeAttribute("w:date", comment.getLocalDateTime(), DateTimeKind.UNSPECIFIED);
        }

        mBuilder.writeAttribute("w:initials", comment.getInitial());

        // All the following story nodes will be written to "Comments" part.
        mWriter.pushBuilder(mBuilder);
    }

    /// <summary>
    /// Writes a w:comment element end into the "Comments" package part.
    /// </summary>
    void writeEnd()
    {
        mBuilder.endElement(); //w:comment

        // Restore current writer's builder to "Main Document" part.
        mWriter.popBuilder();
    }

    void close()
    {
        mBuilder.endDocument();

        if (mBuilderEx != null)
            mBuilderEx.endDocument();

        if (mBuilderIds != null)
            mBuilderIds.endDocument();

        if (mBuilderExtensible != null)
            mBuilderExtensible.endDocument();
    }

    private void writeCommentEx(Comment comment, int paraId)
    {
        if (mBuilderEx == null)
            return;

        mBuilderEx.startElement("w15:commentEx");

        // Note here is w15 prefix.
        mBuilderEx.writeAttribute("w15:paraId", NrxXmlUtil.intToHex(paraId));

        if ((comment.getParentId() != Comment.NO_PARENT) && mCommentIdToParaIdMap.containsKey(comment.getParentId()))
        {
            mBuilderEx.writeAttribute("w15:paraIdParent",
                NrxXmlUtil.intToHex(mCommentIdToParaIdMap.get(comment.getParentId())));
        }

        mBuilderEx.writeAttribute("w15:done", comment.getDone());

        mBuilderEx.endElement(); //w15:commentEx
    }

    /// <summary>
    /// Gets ID of the last paragraph of the comment. If the paragraph has no ID, generates it.
    /// </summary>
    private int getOrGenerateParaId(Comment comment)
    {
        int paraId = comment.getLastParagraph().getParaId();

        if (paraId == 0)
            paraId = mWriter.generateParaId(comment.getLastParagraph().getParentStory().getStoryType());

        mWriter.getCommentParagraphToParaId().add(comment.getLastParagraph(), paraId);
        mCommentIdToParaIdMap.add(comment.getId(), paraId);

        return paraId;
    }

    /// <summary>
    /// Writes a commentId element.
    /// </summary>
    private void writeCommentId(Comment comment, int paraId)
    {
        if (mBuilderIds == null)
            return;

        mBuilderIds.startElement("w16cid:commentId");

        mBuilderIds.writeAttribute("w16cid:paraId", NrxXmlUtil.intToHex(paraId));

        int durableId = comment.getDurableId();
        if (durableId == 0)
            comment.setDurableId(generateDurableId());

        mBuilderIds.writeAttribute("w16cid:durableId", NrxXmlUtil.intToHex(comment.getDurableId()));

        mBuilderIds.endElement(); //w16cid:commentId
    }

    /// <summary>
    /// Generates an unique comment durable ID.
    /// </summary>
    private int generateDurableId()
    {
        while (true)
        {
            int durableId = RandomUtil.newGuid().hashCode();
            // Let's use positive values.
            durableId &= 0x7fffffff;

            if (durableId == 0)
                continue;

            if (mWriter.getSaveInfo().UsedCommentDurableIds.contains(durableId))
                continue;

            mWriter.getSaveInfo().UsedCommentDurableIds.add(durableId);

            return durableId;
        }
    }

    /// <summary>
    /// Writes a commentExtensible element.
    /// </summary>
    private void writeCommentExtensible(Comment comment)
    {
        if ((mBuilderExtensible == null) || 
            ((DateTime.equals(comment.getDateTimeUtcInternal(), DateTime.MinValue)) && !comment.isIntelligentPlaceholder() && !comment.hasReactions()))
            return;

        mBuilderExtensible.startElement("w16cex:commentExtensible");

        Debug.assertTrue(comment.getDurableId() != 0); // Generated in WriteCommentId
        mBuilderExtensible.writeAttribute("w16cex:durableId", NrxXmlUtil.intToHex(comment.getDurableId()));

        if (!DateTime.equals(comment.getDateTimeUtcInternal(), DateTime.MinValue))
            mBuilderExtensible.writeAttribute("w16cex:dateUtc", comment.getDateTimeUtcInternal());

        // It seems MS Word does not write 'intelligentPlaceholder' if it is 'false'.
        if (comment.isIntelligentPlaceholder())
            mBuilderExtensible.writeAttribute("w16cex:intelligentPlaceholder", comment.getDateTimeUtcInternal());

        if (comment.hasReactions())
            writeCommentReactionExtension(comment.getReactions());

        mBuilderExtensible.endElement(); //w16cex:commentExtensible
    }

    /// <summary>
    /// Writes an extension containing a 'reactions' element of the CT_CommentReaction complex type (§2.1.1.1 of
    /// [MS-OREACTXML]).
    /// </summary>
    private void writeCommentReactionExtension(CommentReactionCollection reactions)
    {
        mBuilderExtensible.startElement("w16cex:extLst");
        mBuilderExtensible.startElement("w16:ext");

        mBuilderExtensible.writeAttributeString("w16:uri", DocxCommentsExtensibleReader.REACTIONS_EXTENTION_URI);

        mBuilderExtensible.startElement("cr:reactions");
        DocxNamespaces namespaces =
            new DocxNamespaces(mBuilderExtensible.getOoxmlCompliance() == OoxmlComplianceCore.ISO_STRICT);
        mBuilderExtensible.writeAttributeString("xmlns:cr", namespaces.getCommentReaction());

        for (CommentReaction reaction : reactions)
            writeCommentReaction(reaction);

        mBuilderExtensible.endElement(); //cr:reactions
        mBuilderExtensible.endElement(); //w16:ext
        mBuilderExtensible.endElement(); //w16cex:extLst
    }

    /// <summary>
    /// Writes a 'reaction' element of the CT_CommentReaction complex type (§2.1.3.1 of [MS-OREACTXML]).
    /// </summary>
    private void writeCommentReaction(CommentReaction reaction)
    {
        mBuilderExtensible.startElement("cr:reaction");
        mBuilderExtensible.writeAttribute("reactionType", reaction.getReactionType());

        for (CommentReactionInfo reactionInfo : reaction.getReactionInfos())
            writeCommentReactionInfo(reactionInfo);

        mBuilderExtensible.endElement(); //cr:reaction
    }

    /// <summary>
    /// Writes a 'reactionInfo' element of the CT_CommentReactionInfo complex type (§2.1.3.2 of [MS-OREACTXML]).
    /// </summary>
    private void writeCommentReactionInfo(CommentReactionInfo reactionInfo)
    {
        mBuilderExtensible.startElement("cr:reactionInfo");

        if (!DateTime.equals(reactionInfo.getDateTimeUtc(), DateTime.MinValue))
            mBuilderExtensible.writeAttribute("dateUtc", reactionInfo.getDateTimeUtc());

        if (StringUtil.hasChars(reactionInfo.getUserId()))
        {
            mBuilderExtensible.startElement("cr:user");

            mBuilderExtensible.writeAttribute("userId", reactionInfo.getUserId());
            mBuilderExtensible.writeAttribute("userProvider", reactionInfo.getUserProvider());
            mBuilderExtensible.writeAttribute("userName", reactionInfo.getUserName());

            mBuilderExtensible.endElement(); //cr:user
        }

        mBuilderExtensible.endElement(); //cr:reactionInfo
    }

    private /*final*/ DocxDocumentWriterBase mWriter;
    private /*final*/ DocxBuilder mBuilder;
    private /*final*/ DocxBuilder mBuilderEx;
    private /*final*/ DocxBuilder mBuilderIds;
    private /*final*/ DocxBuilder mBuilderExtensible;

    private /*final*/ IntToIntDictionary mCommentIdToParaIdMap = new IntToIntDictionary();
}

