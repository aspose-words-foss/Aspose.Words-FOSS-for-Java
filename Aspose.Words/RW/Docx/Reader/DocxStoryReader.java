// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;


/// <summary>
/// Provides method for reading story-level elements, 
/// i.e any element that can contain paragraphs or tables.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxStoryReader extends NrxStoryReaderBase
{
    DocxStoryReader(DocxParaReader paraReader)
    {
        Debug.assertTrue(paraReader != null);
        mParaReader = paraReader;
    }

    /// <summary>
    /// Reads all children of the current element into the specified node. 
    /// </summary>
    /// <param name="reader">DOCX parser used to read the document.</param>
    /// <param name="story">Node where to read the story to.</param>
    /*override*/ void read(NrxDocumentReaderBase reader, CompositeNode story) throws Exception
    {
        reader.readStory(story);
    }

    /// <summary>
    /// Reads all children of the current element into the current container.
    /// The current element is supposed to be a story element such as comment, footnote, header/footer or a shape.
    /// The children are supposed to be block-level elements.
    /// </summary>
    /*override*/ void readChildren(NrxDocumentReaderBase reader) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
            readChild(reader);
    }

    /// <summary>
    /// Reads one story child element (paragraph, table, bookmark start or end) and appends 
    /// to the current container in the model builder.
    /// </summary>
    /*override*/ void readChild(NrxDocumentReaderBase reader) throws Exception
    {
        boolean isSectionBreak = false;

        NrxXmlReader xmlReader = reader.getXmlReader();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"p"*/0:
                isSectionBreak = mParaReader.read(reader);
                break;
            case /*"tbl"*/1:
                DocxTableReader.read(reader);
                break;
            case /*"bookmarkStart"*/2:
                DocxBookmarkReader.readStart(reader);
                break;
            case /*"bookmarkEnd"*/3:
                DocxBookmarkReader.readEnd(reader);
                break;
            case /*"commentRangeStart"*/4:
                DocxCommentsReader.readCommentRangeStart(reader);
                break;
            case /*"commentRangeEnd"*/5:
                DocxCommentsReader.readCommentRangeEnd(reader);
                break;
            case /*"permStart"*/6:
                NrxEditableRangesReader.readEditableRangeStart(reader);
                break;
            case /*"permEnd"*/7:
                NrxEditableRangesReader.readEditableRangeEnd(reader);
                break;
            case /*"moveFromRangeStart"*/8:
                DocxAnnotationReader.readMoveRangeStart(reader, true);
                break;
            case /*"moveFromRangeEnd"*/9:
                DocxAnnotationReader.readMoveRangeEnd(reader, true);
                break;
            case /*"moveToRangeStart"*/10:
                DocxAnnotationReader.readMoveRangeStart(reader, false);
                break;
            case /*"moveToRangeEnd"*/11:
                DocxAnnotationReader.readMoveRangeEnd(reader, false);
                break;
            case /*"customXmlDelRangeStart"*/12:
            case /*"customXmlInsRangeStart"*/13:
                DocxAnnotationReader.readCustomXmlEditRevision(reader);
                break;
            case /*"customXmlMoveFromRangeStart"*/14:
            case /*"customXmlMoveToRangeStart"*/15:
                DocxAnnotationReader.readCustomXmlMoveRevision(reader);
                break;
            case /*"customXmlDelRangeEnd"*/16:
            case /*"customXmlInsRangeEnd"*/17:
            case /*"customXmlMoveFromRangeEnd"*/18:
            case /*"customXmlMoveToRangeEnd"*/19:
                DocxAnnotationReader.clearCustomXmlRevision(reader);
                break;
            case /*"sdt"*/20:
                DocxSdtReader sdtReader = DocxReaderFactory.createBlockLevelSdtReader();
                sdtReader.read(reader);
                break;
            case /*"customXml"*/21:
                DocxCustomXmlMarkupReader customXmlReader = DocxReaderFactory.createBlockLevelCustomXmlReader();
                customXmlReader.read(reader);
                break;
            case /*"altChunk"*/22:
                DocxAltChunkReader.read(reader);
                break;
            // WORDSNET-10436 <w:br> element inside body element. Read it and when new paragraph will be read, insert it there, as MSW does.
            case /*"br"*/23:
                xmlReader.getRunTextBuilder().append(NrxRunReaderBase.readBreak(xmlReader, new RunPr()));
                break;
            // WORDSNET-13091 The w:pict element is directly in the w:body element. Cover it with a new paragraph and
            // run like MS Word does.
            case /*"pict"*/24:
            // WORDSNET-22916 A w:drawing element is located directly in a w:body element. Fix it in the same way as
            // for 'pict'.
            case /*"drawing"*/25:
                CompositeNode savedContainer = null;
                if (!reader.getCurContainer().canInsert(new Run(reader.getDocument())))
                {
                    // Create dummy container and read into it.
                    savedContainer = reader.getCurContainer();
                    reader.setCurContainer(new Paragraph(reader.getDocument()));
                }

                DocxReaderFactory.getRunReader().readTextLevelElement(reader, new RunPr());
                reader.addPendingInline(reader.getCurContainer().getFirstChild(), savedContainer);

                if (savedContainer != null)
                {
                    // Postpone read inline node and restore container.
                    reader.setCurContainer(savedContainer);
                }

                break;
            case /*"sectPr"*/26:
                // WORDSNET-17627 w:sectPr element is directly in w:document element.
                reader.readGlobalSectPr(false);
                break;
            default:
                // DD: there seem to be other elements that we dont support, e.g. formulas, that require this ReadChildren.
                readChildren(reader);
                break;
        }

        if (isSectionBreak)
        {
            if(reader.getCurContainer().getNodeType() == NodeType.BODY)
                reader.startNewSection();
            else
                reader.warnUnexpected(WarningStrings.UNEXPECTED_SECTION_BREAK);
        }
    }

    private /*final*/ DocxParaReader mParaReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"p",
		"tbl",
		"bookmarkStart",
		"bookmarkEnd",
		"commentRangeStart",
		"commentRangeEnd",
		"permStart",
		"permEnd",
		"moveFromRangeStart",
		"moveFromRangeEnd",
		"moveToRangeStart",
		"moveToRangeEnd",
		"customXmlDelRangeStart",
		"customXmlInsRangeStart",
		"customXmlMoveFromRangeStart",
		"customXmlMoveToRangeStart",
		"customXmlDelRangeEnd",
		"customXmlInsRangeEnd",
		"customXmlMoveFromRangeEnd",
		"customXmlMoveToRangeEnd",
		"sdt",
		"customXml",
		"altChunk",
		"br",
		"pict",
		"drawing",
		"sectPr"
	);

}

