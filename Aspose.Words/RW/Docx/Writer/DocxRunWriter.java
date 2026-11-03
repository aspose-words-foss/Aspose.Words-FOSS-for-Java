// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.BidiControlCharUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Writes character formatting properties to WordML.
/// </summary>
class DocxRunWriter extends NrxRunWriter
{
    DocxRunWriter(DocxDocumentWriterBase writer)
    {
        mWriter = writer;
    }

    /*override*/ void writeInline(Inline inline) throws Exception
    {
        String text = inline.getText();

        if (inline.getRunPr().contains(FontAttr.RUBY))
        {
            writeRuby(mWriter, inline);
            return;
        }

        // We are not writing runs which do not have text, even if they exist in the model.
        // That is to be consistent with the our reader, which does not read runs with no text to the model.
        if (!StringUtil.hasChars(text))
            return;

        // TestFormFieldOldFormat2.doc contains runs with \x0001 character as text.
        // MS Word writes this runs as the runs with no text.
        // I don't want to do the same because it does not make sense and will be inconsistent 
        // with our reader, which ignores runs with no text elements inside.
        // So I will just skip writing such runs.
        if (text.charAt(0) == ControlChar.PICTURE_CHAR)
            return;

        // We need to check if the run text should be output as symbol character(inside 'w:sym' element).
        // Example:
        //   <w:sym w:font="Wingdings" w:char="F0A8" />
        //
        // MS Word uses a slightly different approach, it splits the run into individual characters but outputs
        // them inside 'w:t' element. And the symbol nature of the element is expressed by 'wx:sym' element
        // inside run properties 'w:rPr' element. I think it is ok to just output this chars as 'w:sym' runs.
        //
        // Generally MS Word handles symbols correctly even if they are inside 'w:t' element.
        // It is wordml2html.xslt that we are caring about here.
        //
        // RK Sometimes MS Word writes symbols as normal text, not as symbols like in ManualAdmin ms.docx.
        // It looks like it does so when the font attribute is not specified directly. I do the same here.
        if (inline.isWriteAsSymbol())
        {
            // We should also check for cases when there are multiple symbol characters in the run.
            // As symbol characters use specific fonts they are probably never mixed with conventional characters.
            // So we don't need to use any complex algorithms to separate them from conventional characters,
            // we should just output each character in a separate run with 'w:sym' element representing run's text.
            for (int i = 0; i < text.length(); i++)
            {
                writeRunStart(inline);
                DocxBuilder builder = mWriter.getCurrentBuilder();

                // andrnosk: WORDSNET-8777 Font of a symbol can be specified through the theme, 
                // we have to check this and get correct font name.

                // alexnosk: WORDSNET-5876 Use NameOther for symbols.
                // Since symbolic characters are supposed to be outside ASCII range. MS Word seems to do the same.
                String fontName = inline.getRunPr().getComplexNameOther().resolve(mWriter.getDocument().getThemeInternal());

                builder.writeElementWithAttributes(
                    "w:sym",
                    "w:font", fontName,
                    "w:char", FormatterPal.intToStrX4(text.charAt(i)));

                writeRunEnd(inline.getRunPr());
            }
        }
        else
        {
            // BiDi control char should be written outside of 'w:r' elements
            // and it has its own XML element ('w:dir' or 'w:bdo').
            // So, do not write 'w:r' element for it.
            int initialRunStartedCount = mRunStartedCount;
            if (!BidiControlCharUtil.isBidiControlCharStart(text.charAt(0)))
                writeRunStart(inline);

            writeRunContent(inline);

            // Write the end only when a run was actually started. 
            if (mRunStartedCount > initialRunStartedCount)
                writeRunEnd(inline.getRunPr());
        }
    }

    /// <summary>
    /// Writes sub nodes of the run element. If break chars are present we split up run text to simple text cuts
    /// and break chars and output them differently.
    /// </summary>
    private void writeRunContent(Inline inline) throws Exception
    {
        String text = inline.getText();
        boolean isDeleteRevision = inline.isDeleteRevision();
        int textStart = 0;
        DocxBuilder builder = mWriter.getCurrentBuilder();

        for (int i = 0; i < text.length(); i++)
        {
            switch (text.charAt(i))
            {
                case ControlChar.FOOTNOTE_REF_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        writeFootnoteRef();
                        break;
                    }
                case ControlChar.FOOTNOTE_SEPARATOR_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        builder.writeEmptyElement("w:separator");
                        break;
                    }
                case ControlChar.FOOTNOTE_CONTINUATION_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        builder.writeEmptyElement("w:continuationSeparator");
                        break;
                    }
                case ControlChar.ANNOTATION_REF_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        builder.writeEmptyElement("w:annotationRef");
                        break;
                    }
                case ControlChar.TAB_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        writeTab();
                        break;
                    }
                case ControlChar.PARAGRAPH_BREAK_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        builder.writeEmptyElement("w:cr");
                        break;
                    }
                case ControlChar.PAGE_BREAK_CHAR:
                case ControlChar.LINE_BREAK_CHAR:
                case ControlChar.COLUMN_BREAK_CHAR:
                    {
                        writeText(text, textStart, i, isDeleteRevision);
                        writeBr(text.charAt(i), inline.getRunPr().getLineBreakClear());
                        break;
                    }
                case ControlChar.NON_BREAKING_HYPHEN_CHAR:
                    {

                        // If the hyphen is not the first char in the run, then MS Word seems to break
                        // the run's text and writes 'w:softHyphen' in the new 'w:r' element.
                        // I think it is a good idea to do the same.
                        writeTextAndStartNewRun(text, textStart, i, inline);
                        builder.writeEmptyElement("w:noBreakHyphen");
                        break;
                    }
                case ControlChar.OPTIONAL_HYPHEN_CHAR:
                    {
                        // If soft hyphen is not the first char in the run, then MS Word seems to break
                        // the run's text and writes 'w:softHyphen' in the new 'w:r' element.
                        // I think it is a good idea to do the same.
                        writeTextAndStartNewRun(text, textStart, i, inline);
                        builder.writeEmptyElement("w:softHyphen");
                        break;
                    }
                case ControlChar.LEFT_TO_RIGHT_EMBEDDING:
                case ControlChar.RIGHT_TO_LEFT_EMBEDDING:
                case ControlChar.LEFT_TO_RIGHT_OVERRIDE:
                case ControlChar.RIGHT_TO_LEFT_OVERRIDE:
                    {
                        writeText(text, textStart, i, isDeleteRevision);

                        // BiDi control chars should be written as special
                        // tags ('w:dir' or 'w:bdo') outside of any 'w:r' element.
                        // WORDSNET-18525 BiDi control char occurs at the beginning of "inline" node, so there is not an
                        // appropriate start of run.
                        if (i > 0)
                            writeRunEnd(inline.getRunPr());

                        writeBidiControlChar(text.charAt(i), inline.getParentParagraph());

                        // Start new 'w:r' for the rest of the text,
                        // if next char is not BiDi again.
                        int nextIndex = i + 1;
                        if ((nextIndex < text.length()) && !BidiControlCharUtil.isBidiControlCharStart(text.charAt(nextIndex)))
                            writeRunStart(inline);

                        break;
                    }
                case ControlChar.POP_DIRECTIONAL_FORMATTING:
                    {
                        writeTextAndStartNewRun(text, textStart, i, inline);
                        // Write BiDi 'PDF' control char into a separate run as Unicode character.
                        writeTextAndStartNewRun(text, i, i + 1, inline);
                        break;
                    }
                default:
                    {
                        // Usual text.
                        // Continue to skip setting textStart.
                        continue;
                    }
            }

            textStart = i + 1;
        }

        // Write rest of the text.
        writeText(text, textStart, text.length(), isDeleteRevision);
    }

    /*override*/ void writeRunStart(Inline inline) throws Exception
    {
        writeRunStart(inline.getRunPr(), inline, true);
    }

    /// <summary>
    /// Writes w:ins and/or w:del if needed, then writes start of w:r and writes w:rPr.
    /// </summary>
    void writeRunStart(RunPr runPr, IRunAttrSource attrSource, boolean isWriteRsid) throws Exception
    {
        mRunStartedCount++;

        DocxBuilder builder = mWriter.getCurrentBuilder();

        if (runPr.hasInsertRevision())
            builder.writeRevisionStart(runPr.getInsertRevision(), mWriter.getNextAnnotationId());
            
        if (runPr.hasDeleteRevision())
            builder.writeRevisionStart(runPr.getDeleteRevision(), mWriter.getNextAnnotationId());

        if (runPr.hasMoveFromRevision())
            builder.writeRevisionStart(runPr.getMoveFromRevision(), mWriter.getNextAnnotationId());

        if (runPr.hasMoveToRevision())
            builder.writeRevisionStart(runPr.getMoveToRevision(), mWriter.getNextAnnotationId());

        builder.startElement(builder.isInMath() ? "m:r" : "w:r");

        if (isWriteRsid)
        {
            builder.writeAttribute("w:rsidRPr", NrxXmlUtil.intToHex(runPr.getDirectAttr(FontAttr.RSID_R_PR)));

            if (runPr.containsKey(FontAttr.RSID_R))
            {
                String rsidR = NrxXmlUtil.intToHex(runPr.getDirectAttr(FontAttr.RSID_R));

                // Actually, we must compare with parent paragraph's rsidRDefault here.
                // But I have not found rsidRDefault among paragraph attributes.
                if (!msString.equals(rsidR, builder.getRsidR()))
                    builder.writeAttribute("w:rsidR", rsidR);
            }
        }

        DocxRunPrWriter.getInstance().writeForNodes(runPr, attrSource, false, mWriter);
    }

    /// <summary>
    /// Ends a w:r element (that could be wrapped into w:ins and/or w:del).
    /// </summary>
    /*override*/ void writeRunEnd(RunPr runPr) throws Exception
    {
        if (!isRunStarted())
            return;

        DocxBuilder builder = mWriter.getCurrentBuilder();

        builder.endElement();    //w:r

        if (runPr.hasDeleteRevision())
            builder.writeRevisionEnd();
        
        if (runPr.hasInsertRevision())
            builder.writeRevisionEnd();

        if (runPr.hasMoveFromRevision())
            builder.writeRevisionEnd();

        if (runPr.hasMoveToRevision())
            builder.writeRevisionEnd();

        mRunStartedCount--;
    }

    /// <summary>
    /// Writes a footnoteRef or endnoteRef element.
    /// </summary>
    private void writeFootnoteRef()
    {
        String elemName = (mCurrentFootnoteType == FootnoteType.FOOTNOTE) ? "w:footnoteRef" : "w:endnoteRef";
        mWriter.getCurrentBuilder().writeEmptyElement(elemName);
    }

    /// <summary>
    /// Writes a literal text element.
    /// </summary>
    /// <param name="text">A string that contains text to write.</param>
    /// <param name="fromIndex">An index, from which text substring will be written.</param>
    /// <param name="toIndex">An index, until which text substring will be written.
    /// The character at this index is not included.</param>
    /// <param name="isDeletion">A flag indicating that destination element is a deleted text element.</param>
    private void writeText(String text, int fromIndex, int toIndex, boolean isDeletion)
    {
        if (fromIndex >= toIndex)
            return;
        
        DocxBuilder builder = mWriter.getCurrentBuilder();

        String elementName;

        // Check if this text is actually a field code text.
        if (mWriter.isInsideFieldCode())
        {
            elementName = isDeletion ? "w:delInstrText" : "w:instrText";
        }
        else
        {
            if (isDeletion && !builder.isInMath()) // MS Word does not write w:delText for deleted math runs, it writes m:t
            {
                elementName = "w:delText";
            }
            else
            {
                elementName = builder.isInMath() ? "m:t" : "w:t";
            }
        }

        builder.startElement(elementName);

        if ((text.charAt(fromIndex) == ControlChar.SPACE_CHAR) || (text.charAt(toIndex - 1) == ControlChar.SPACE_CHAR))
            builder.writeAttributeString("xml:space", "preserve");

        builder.writeString(text.substring((fromIndex), (fromIndex) + (toIndex - fromIndex)));

        builder.endElement();
    }

    private void writeTab()
    {
        // Example: 
        //   <w:tab wx:wTab="1080" wx:tlc="none" wx:cTlc="17" />

        // My guess is that wx:wTab, wx:tlc and wx:cTlc are calculated based on default tab settings
        // or on tab settings provided in ParentParagraph.ParaPr.TabStops.
        // I don't think we are able to calculate it without rendering.

        mWriter.getCurrentBuilder().writeEmptyElement("w:tab");
    }

    //<w:br w:type="text-wrapping" w:clear="all" />
    private void writeBr(char br, /*LineBreakClear*/int lineBreakClear)
    {
        String clear = null;

        // WORDSNET-28089 MS Word writes the 'clear' attribute for each break type: do the same in Aspose.Words.
        if (lineBreakClear != LineBreakClear.NONE)
            clear = NrxRunEnum.lineBreakClearToXml(lineBreakClear);

        if (br == ControlChar.LINE_BREAK_CHAR && lineBreakClear == LineBreakClear.NONE)
            mWriter.getCurrentBuilder().writeEmptyElement("w:br");
        else
            mWriter.getCurrentBuilder().writeElementWithAttributes(
                "w:br",
                "w:type", NrxRunEnum.breakToXml(br, true),
                "w:clear", clear);
    }

    /// <summary>
    /// Flushes text from the <paramref name="fromIndex"/> to the <paramref name="toIndex"/>,
    /// then closes current run and starts new one.
    /// </summary>
    private void writeTextAndStartNewRun(String text, int fromIndex, int toIndex, Inline inline) throws Exception
    {
        // Flush text.
        writeText(text, fromIndex, toIndex, inline.isDeleteRevision());

        // If we at the start of run already or at the end of text,
        // then it is not necessary to start new run.
        if ((toIndex == 0) || (toIndex >= text.length()))
            return;

        writeRunEnd(inline.getRunPr());
        writeRunStart(inline);
    }

    /// <summary>
    /// Writes 'dir' or 'bdo' elements for RLE, LRE, RLO, LRO.
    /// Note, these elements should be written outside of 'w:r' element.
    /// </summary>
    private void writeBidiControlChar(char bidiControlChar, Paragraph curParagraph)
    {
        String bidiControlCharType = msString.format("w:{0}", BidiControlCharUtil.toTypeString(bidiControlChar));
        String bidiControlCharDirection = BidiControlCharUtil.toDirectionString(bidiControlChar);

        mWriter.getCurrentBuilder().writeElementWithAttributesStart(bidiControlCharType, "w:val", bidiControlCharDirection);

        // Word closes 'w:dir' and 'w:bdo' elements at the end of paragraph,
        // so we collect it here to use at the end of the current paragraph.
        msStack<String> bidiControlChars = mWriter.getOrCreateBiDiControlCharStack(curParagraph);
        bidiControlChars.push(bidiControlCharType);
    }
    
    /*FootnoteType*/int getCurrentFootnoteType() { return mCurrentFootnoteType; }
    void setCurrentFootnoteType(/*FootnoteType*/int value) { mCurrentFootnoteType = value; }

    private boolean isRunStarted() { return mRunStartedCount > 0; }

    private /*final*/ DocxDocumentWriterBase mWriter;
    private /*FootnoteType*/int mCurrentFootnoteType = FootnoteType.FOOTNOTE;

    private int mRunStartedCount;
}

