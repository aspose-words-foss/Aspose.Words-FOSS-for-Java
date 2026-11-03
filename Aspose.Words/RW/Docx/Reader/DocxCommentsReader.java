// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;


/// <summary>
/// Provides static method for reading comments.
/// </summary>
/*static*/ class DocxCommentsReader
{
	/* Simulation of static class by using private constructor */
	private DocxCommentsReader()
	{}

    /// <summary>
    /// Reads the "Comments" part.
    /// </summary>
    static void read(DocxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.switchToPartReaderByRelType(reader.getRelTypes().getComments());
        if (xmlReader == null)
            return;

        while (xmlReader.readChild("comments"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"comment"*/0:
                {
                    NrxAnnotation annotation = new NrxAnnotation(xmlReader);

                    Comment comment = new Comment(reader.getDocument(), new RunPr());
                    // When reading, we use the annotation id from the document for the model.
                    ((INodeWithAnnotationId)comment).setIdInternal(annotation.getId()); 
                    comment.setAuthor(annotation.getAuthor());
                    comment.setInitial(annotation.getInitials());

                    if (!DateTime.equals(annotation.getDate(), DateTime.MinValue))
                    {
                        // WORDSNET-21481 MS Word (and AW) writes comment local time without conversion to UTC
                        // on saving a document. So, on reading, we need to set Local kind of the date.
                        comment.setLocalDateTime(DateTime.specifyKind(annotation.getDate(), DateTimeKind.LOCAL));
                    }

                    DocxReaderFactory.getStoryReader().read(reader, comment);
                    
                    reader.addComment(comment);
                    break;
                }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }

        reader.restorePartReader();
    }

    /// <summary>
    /// Reads a w:commentRangeStart element.
    /// </summary>
    static void readCommentRangeStart(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        // We use the annotation id from the document as a comment id for the model.
        String id = xmlReader.readId();
        if (!StringUtil.hasChars(id))
            return;

        CommentRangeStart commentRangeStart = new CommentRangeStart(reader.getDocument(), FormatterPal.xmlToInt(id));
        commentRangeStart.setDisplacedBy(readDisplacedByCustomXmlAttribute(xmlReader));

        reader.addCrossStructureAnnotation(commentRangeStart);
    }

    /// <summary>
    /// Reads a w:commentRangeEnd element.
    /// </summary>
    static void readCommentRangeEnd(NrxDocumentReaderBase reader)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String id = xmlReader.readId();
        if (!StringUtil.hasChars(id))
            return;

        CommentRangeEnd commentRangeEnd = new CommentRangeEnd(reader.getDocument(), FormatterPal.xmlToInt(id));
        commentRangeEnd.setDisplacedBy(readDisplacedByCustomXmlAttribute(xmlReader));

        reader.addCrossStructureAnnotation(commentRangeEnd);
    }

    /// <summary>
    /// Reads the displacedByCustomXml attribute of comment range element.
    /// </summary>
    private static /*DisplacedByType*/int readDisplacedByCustomXmlAttribute(NrxXmlReader xmlReader)
    {
        String value = xmlReader.readAttribute("displacedByCustomXml", null);
        return DocxDopEnum.docxToDisplacedByType(value);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"comment"
	);

}

