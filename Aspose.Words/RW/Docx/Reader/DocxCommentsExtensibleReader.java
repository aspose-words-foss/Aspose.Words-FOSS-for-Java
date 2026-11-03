// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;


/// <summary>
/// Implements reading 2.1.5 commentsExtensible [MS-DOCX] document parts.
/// </summary>
/*static*/ class DocxCommentsExtensibleReader
{
	/* Simulation of static class by using private constructor */
	private DocxCommentsExtensibleReader()
	{}

    /// <summary>
    /// Reads a commentsExtensible document part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getCommentsExtensible());
        if (xmlReader == null)
            return;

        reader.getComplianceInfo().markAsHasDocxExtensionsOf(MsWordVersionCore.WORD_2019);

        while (xmlReader.readChild("commentsExtensible"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"commentExtensible"*/0:
                    readCommentExtensible(reader);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Reads a commentExtensible element.
    /// </summary>
    private static void readCommentExtensible(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        int durableId = NrxXmlUtil.tryHexToInt(xmlReader.readAttribute("durableId", "0"));

        CommentExtensible extensible = reader.findCommentExtensible(durableId);
        if (extensible == null)
        {
            xmlReader.ignoreElement();
            return;
        }

        extensible.setUtcDateTime(FormatterPal.xmlToDateTime(xmlReader.readAttribute("dateUtc", "")));
        extensible.isIntelligentPlaceholder(xmlReader.readBoolAttribute("intelligentPlaceholder", false));

        while (xmlReader.readChild("commentExtensible"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"extLst"*/1:
                    readCommentExtensibleExtensionList(reader, extensible);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads an 'extLst' element located in a 'commentExtensible' element.
    /// </summary>
    private static void readCommentExtensibleExtensionList(DocxDocumentReaderBase reader, CommentExtensible extensible)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("extLst"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"ext"*/2:
                    readCommentExtensibleExtension(reader, extensible);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads an 'ext' element that represents an extension of a 'commentExtensible' element.
    /// Only the 'reactions' extension is currently supported.
    /// </summary>
    private static void readCommentExtensibleExtension(DocxDocumentReaderBase reader, CommentExtensible extensible)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String uri = xmlReader.readAttribute("uri", null);

        // Only one extension is known at the moment.
        if (!REACTIONS_EXTENTION_URI.equals(uri))
        {
            xmlReader.ignoreElement();
            return;
        }

        while (xmlReader.readChild("ext"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"reactions"*/3:
                    extensible.setReactions(readCommentReactions(reader));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads the 'reactions' element of the CT_CommentReactions complex type defined in §2.1.1.1 of [MS-OREACTXML]
    /// that specifies information for the reactions to a comment.
    /// </summary>
    private static CommentReactionCollection readCommentReactions(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        CommentReactionCollection reactions = new CommentReactionCollection();

        while (xmlReader.readChild("reactions"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"reaction"*/4:
                    reactions.add(readCommentReaction(reader));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        return reactions;
    }

    /// <summary>
    /// Reads the 'reaction' element of the CT_CommentReaction complex type defined in §2.1.3.1 of [MS-OREACTXML]
    /// that specifies information for a single reaction type.
    /// </summary>
    private static CommentReaction readCommentReaction(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        CommentReaction reaction = new CommentReaction();
        reaction.setReactionType(xmlReader.readIntAttribute("reactionType", 0));

        while (xmlReader.readChild("reaction"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"reactionInfo"*/5:
                    reaction.getReactionInfos().add(readCommentReactionInfo(reader));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        return reaction;
    }

    /// <summary>
    /// Reads the 'reactionInfo' element of the CT_CommentReactionInfo complex type defined in §2.1.3.2 of
    /// [MS-OREACTXML] that specifies the user who added the reaction and the time when the reaction was added.
    /// </summary>
    private static CommentReactionInfo readCommentReactionInfo(DocxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        CommentReactionInfo reactionInfo = new CommentReactionInfo();
        reactionInfo.setDateTimeUtc(FormatterPal.xmlToDateTime(xmlReader.readAttribute("dateUtc", "")));

        while (xmlReader.readChild("reactionInfo"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"user"*/6:
                    readCommentReactionUser(reader, reactionInfo);
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        return reactionInfo;
    }

    /// <summary>
    /// Reads the 'user' element of the CT_User complex type defined in §2.1.3.4 of [MS-OREACTXML] that specifies
    /// identity details of a user into the instance of <see cref="CommentReactionInfo"/>.
    /// </summary>
    private static void readCommentReactionUser(DocxDocumentReaderBase reader, CommentReactionInfo reactionInfo)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        reactionInfo.setUserId(xmlReader.readAttribute("userId", null));
        reactionInfo.setUserName(xmlReader.readAttribute("userName", null));
        reactionInfo.setUserProvider(xmlReader.readAttribute("userProvider", null));

        while (xmlReader.readChild("user"))
            xmlReader.ignoreElement();
    }

    static final String REACTIONS_EXTENTION_URI = "{CE6994B0-6A32-4C9F-8C6B-6E91EDA988CE}";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"commentExtensible",
		"extLst",
		"ext",
		"reactions",
		"reaction",
		"reactionInfo",
		"user"
	);

}

