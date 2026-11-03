// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.UriUtil;


/// <summary>
/// Provides methods to read "inline" DOCX elements. Inline elements are those that normally
/// occur inside a paragraph. But in DOCX the same set of elements can occur inside hyperlink,
/// fldSimple, smartTag and few other elements, therefore this common code is factored out here.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxInlineReader extends NrxInlineReaderBase
{
    DocxInlineReader(DocxHyperlinkReader hyperlinkReader,
        DocxSmartTagReader smartTagReader)
    {
        Debug.assertTrue(hyperlinkReader != null);
        Debug.assertTrue(smartTagReader != null);
        mHyperlinkReader = hyperlinkReader;
        mSmartTagReader = smartTagReader;
    }

    /// <summary>
    /// Reads all children of the current element.
    /// The current element is supposed to a container for inline elements such as hyperlink, fldSimple etc.
    /// Reads all children of the current element.
    /// </summary>
    /*override*/ void readChildren(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
            readChild(reader, runPr);
    }

    /// <summary>
    /// Reads one current element. Factored into a separate method to make reading of child
    /// of paragraph elements possible since pPr is not an inline element and is read in the
    /// paragraph reader.
    /// </summary>
    protected /*override*/ void readChild(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        runPr = runPr != null
            ? AttrCollectionExtension.deepClone(runPr)
            : new RunPr();

        NrxXmlReader xmlReader = reader.getXmlReader();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"bookmarkStart"*/0:
                DocxBookmarkReader.readStart(reader);
                break;
            case /*"bookmarkEnd"*/1:
                DocxBookmarkReader.readEnd(reader);
                break;
            case /*"commentRangeStart"*/2:
                DocxCommentsReader.readCommentRangeStart(reader);
                break;
            case /*"commentRangeEnd"*/3:
                DocxCommentsReader.readCommentRangeEnd(reader);
                break;
            case /*"permStart"*/4:
                NrxEditableRangesReader.readEditableRangeStart(reader);
                break;
            case /*"permEnd"*/5:
                NrxEditableRangesReader.readEditableRangeEnd(reader);
                break;
            case /*"subDoc"*/6:
            {
                String relId = reader.getXmlReader().readAttribute("id", "");
                String docFileName = ((Document)reader.getDocument()).getOriginalFileName();
                String target = UriUtil.unescapeHref(reader.getRelationshipTarget(relId));
                reader.addChild(new SubDocument(reader.getDocument(), UriUtil.getAbsoluteFileName(docFileName, target)));
                break;
            }
            case /*"r"*/7:
            {
                DocxReaderFactory.getRunReader().read(reader, runPr);
                break;
            }
            case /*"hyperlink"*/8:
                mHyperlinkReader.read(reader);
                break;
            case /*"smartTag"*/9:
                mSmartTagReader.read(reader);
                break;
            case /*"del"*/10:
                mAnnotationReader.readInlineDeletion(reader);
                break;
            case /*"ins"*/11:
                mAnnotationReader.readInlineInsertion(reader);
                break;
            case /*"moveFromRangeStart"*/12:
                DocxAnnotationReader.readMoveRangeStart(reader, true);
                break;
            case /*"moveFromRangeEnd"*/13:
                DocxAnnotationReader.readMoveRangeEnd(reader, true);
                break;
            case /*"moveToRangeStart"*/14:
                DocxAnnotationReader.readMoveRangeStart(reader, false);
                break;
            case /*"moveToRangeEnd"*/15:
                DocxAnnotationReader.readMoveRangeEnd(reader, false);
                break;
            case /*"moveFrom"*/16:
                mAnnotationReader.readInlineMove(reader, false);
                break;
            case /*"moveTo"*/17:
                mAnnotationReader.readInlineMove(reader, true);
                break;
            case /*"customXmlDelRangeStart"*/18:
            case /*"customXmlInsRangeStart"*/19:
                DocxAnnotationReader.readCustomXmlEditRevision(reader);
                break;
            case /*"customXmlMoveFromRangeStart"*/20:
            case /*"customXmlMoveToRangeStart"*/21:
                DocxAnnotationReader.readCustomXmlMoveRevision(reader);
                break;
            case /*"customXmlDelRangeEnd"*/22:
            case /*"customXmlInsRangeEnd"*/23:
            case /*"customXmlMoveFromRangeEnd"*/24:
            case /*"customXmlMoveToRangeEnd"*/25:
                DocxAnnotationReader.clearCustomXmlRevision(reader);
                break;
            case /*"sdt"*/26:
                DocxSdtReader sdtReader = DocxReaderFactory.createInlineLevelSdtReader();
                sdtReader.read(reader);
                break;
            case /*"customXml"*/27:
                DocxCustomXmlMarkupReader customXmlReader = DocxReaderFactory.createInlineLevelCustomXmlReader();
                customXmlReader.read(reader);
                break;
            case /*"oMath"*/28:
                DocxMathReader.readOMath(reader);
                break;
            case /*"oMathPara"*/29:
                DocxMathReader.readOMathPara(reader);
                break;
            case /*"altChunk"*/30:
                DocxAltChunkReader.read(reader);
                break;
            // WORDSNET-11281 numPr element was not loaded if it is in p element
            case /*"numPr"*/31:
                if (reader.getCurContainer().getNodeType() == NodeType.PARAGRAPH)
                {
                    ParaPr paraPr = ((Paragraph)reader.getCurContainer()).getParaPr();
                    DocxReaderFactory.getParaPrReader().readNumPrWithCollectingListId(reader, paraPr);
                }
                break;
            case /*"fldData"*/32:
                NrxFldCharReaderBase.readFieldData(reader);
                break;
            case /*"bdo"*/33:
            case /*"dir"*/34:
                DocxBidiControlCharReader.read(reader, this);
                break;
            default:
            {
                // WORDSNET-20919 There is drawing element in 'p'. Fixed by reading text level elements here.
                if (!DocxReaderFactory.getRunReader().readTextLevelElement(reader, runPr))
                    // DD: Left for resiliency, per spec it seems that we read all inlines that are out there.
                    readChildren(reader);
                break;
            }
        }
    }

    void setAnnotationReader(DocxAnnotationReader annotationReader)
    {
        Debug.assertTrue(annotationReader != null);
        mAnnotationReader = annotationReader;
    }

    private DocxAnnotationReader mAnnotationReader;
    private /*final*/ DocxHyperlinkReader mHyperlinkReader;
    private /*final*/ DocxSmartTagReader mSmartTagReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"bookmarkStart",
		"bookmarkEnd",
		"commentRangeStart",
		"commentRangeEnd",
		"permStart",
		"permEnd",
		"subDoc",
		"r",
		"hyperlink",
		"smartTag",
		"del",
		"ins",
		"moveFromRangeStart",
		"moveFromRangeEnd",
		"moveToRangeStart",
		"moveToRangeEnd",
		"moveFrom",
		"moveTo",
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
		"oMath",
		"oMathPara",
		"altChunk",
		"numPr",
		"fldData",
		"bdo",
		"dir"
	);

}

