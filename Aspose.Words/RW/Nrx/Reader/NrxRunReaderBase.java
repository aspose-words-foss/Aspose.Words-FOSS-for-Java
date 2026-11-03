// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/04/2010 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.AnyXmlTextHandlingConsts;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.CodePage;
import com.aspose.FontUtil;
import java.util.ArrayList;
import com.aspose.AnyXmlReader;
import com.aspose.LocaleConverter;
import com.aspose.Language;


/// <summary>
/// Base class, provides methods for reading run of text in DOCX and WML.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxRunReaderBase
{
    protected NrxRunReaderBase(NrxStoryReaderBase storyReader,
        NrxRunPrReaderBase runPrReader,
        NrxParaReaderBase paraReader,
        NrxFldCharReaderBase fldCharReader,
        NrxFldSimpleReader fldSimpleReader,
        NrxHyperlinkReaderBase hyperlinkReader,
        INrxVmlReader vmlReader)
    {
        Debug.assertTrue(storyReader != null);
        Debug.assertTrue(runPrReader != null);
        Debug.assertTrue(paraReader != null);
        Debug.assertTrue(fldCharReader != null);
        Debug.assertTrue(fldSimpleReader != null);
        Debug.assertTrue(hyperlinkReader != null);

        mStoryReader = storyReader;
        mRunPrReader = runPrReader;
        mParaReader = paraReader;
        mFldCharReader = fldCharReader;
        mFldSimpleReader = fldSimpleReader;
        mHyperlinkReader = hyperlinkReader;
        mVmlReader = vmlReader;
    }

    /// <summary>
    /// Reads 'w:r' element from the specified reader. Reader should be positioned to element start.
    /// </summary>
    /// <param name="reader">DocxParser/WmlReader to read from. Should be positioned to element start.</param>
    void read(NrxDocumentReaderBase reader) throws Exception
    {
        read(reader, null);
    }

    void read(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        readCore(reader, runPr);
    }

    void readText(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String elementName = xmlReader.getLocalName();

        // andrnosk: WORDSNET-4664 Get "Space" attribute either from document (WML) or from current run (DOCX).
        boolean preserveSpace = reader.getPreserveSpace() || ("preserve".equals(xmlReader.readAttribute("space", ""))) || (!"t".equals(elementName));

        while (xmlReader.readChildWithTextValues(elementName, AnyXmlTextHandlingConsts.TEXT_AND_SIGNIFICANT))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*""*/0:
                    {
                        String t = xmlReader.getValue();

                        // andrnosk: WORDSNET-5553 Simulate MS Word behavior replace \r\n chars with white space.
                        t = t.replace(ControlChar.CR_LF, ControlChar.SPACE);

                        // WORDSNET-7391 Trim LF at start and replace LF with Space in the rest of string.
                        t = msString.trimStart(t, ControlChar.LINE_FEED_CHAR);

                        t = t.replace(ControlChar.LF, ControlChar.SPACE);
                        t = t.replace(ControlChar.CR, ControlChar.SPACE);

                        // andrnosk: WORDSNET-4664 If PreserveSpace option of Run is not set then remove all leading and trailing white-spaces and tabs.
                        if (!preserveSpace)
                        {
                            t = msString.trim(t, G_WHITESPACE_CHARS);

                            // WORDSNET-3020 Word replaces tab characters with space if xml space is not preserved.
                            t = msString.replace(t, '\t', ' ');
                        }

                        if (isDefaultTextInputChar(t))
                            readSpecialChar(reader, runPr, t.charAt(0));
                        else if (!isJavaWoodstoxWhitespace(t))
                            msStringBuilder.append(xmlReader.getRunTextBuilder(), t);
                        break;
                    }
                case /*"noBreakHyphen"*/1:
                    msStringBuilder.append(xmlReader.getRunTextBuilder(), ControlChar.NON_BREAKING_HYPHEN);
                    break;
                case /*"p"*/2:
                    // RESILIENCY 20010 - a "p" tag is found inside "r" element.
                    resilientParaRead(reader, runPr);
                    break;
                case /*"tbl"*/3:
                    // rework for RESILIENCY 3760. "tbl" tag is found inside "r" element.
                    // DD: after "p" tag processing was added per fix for 20010, I had to add table processing as well.
                    resilientTableRead(reader);
                    break;
                default:
                    // There can be inline elements like pict or drawing inside 't' element.
                    readTextLevelElement(reader, runPr);
                    break;
            }
        }
    }

    /// <summary>Resilient version of <see cref="Read(NrxDocumentReaderBase)"/> allowing to specify runPr of a parent w:r for nested w:r.</summary>
    /// <param name="reader">DocxParser/WmlReader to read from. Should be positioned to element start.</param>
    /// <param name="runPr">When non null, used for resiliency, when we deal with nested runs inside runs, etc. in these cases we need to consider properties
    /// of runs for their nested runs.</param>
    private void readCore(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        if (runPr == null)
            runPr = new RunPr();

        // Read attributes.
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (!reader.getLoadOptions().getSkipFormatting())
        {
            while (xmlReader.moveToNextAttribute())
            {
                // The reading below is not entirely correct because not all rsid attributes are in the model now.
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"rsidDel"*/4:
                        // RK Not yet sure where to store in the model.
                        // Specifies a unique identifier used to track the editing session when
                        // the run was deleted from the main document.
                        break;
                    case /*"rsidR"*/5:
                        {
                            // Specifies a unique identifier used to track the editing session when
                            // the run was added to the main document.
                            int rsidR = NrxXmlUtil.tryHexToInt(xmlReader.getValue());
                            if ((rsidR != Integer.MIN_VALUE) && (rsidR != xmlReader.getRsidRDefault()))
                                runPr.setAttr(FontAttr.RSID_R, rsidR);
                            break;
                        }
                    case /*"rsidRPr"*/6:
                        {
                            // Specifies a unique identifier used to track the editing session when
                            // the run properties were last modified in the main document.
                            int rsidDpr = NrxXmlUtil.tryHexToInt(xmlReader.getValue());
                            if (rsidDpr != Integer.MIN_VALUE)
                                runPr.setAttr(FontAttr.RSID_R_PR, rsidDpr);
                            break;
                        }
                    default:
                    {
                        // Ignored.
                        break;
                    }
                }
            }
        }

        readRChildren(reader, runPr, false);

        flushRun(reader, runPr);
    }

    /// <summary>
    /// Reads elements that can occur as a child of an "r" or "mc:Fallback" element.
    /// </summary>
    private void readRChildren(NrxDocumentReaderBase reader, RunPr runPr, boolean selfRecursive) throws Exception
    {
        // RK Clones or runPr are not done everywhere here. We should fix sometime.

        NrxXmlReader xmlReader = reader.getXmlReader();

        // Indicates whether this run should be separated.
        boolean createNewRun = false;
        boolean isRunCreated = false;

        // Need to do this to find the name of the element whose contents we are reading.
        // It can be "r" or "Fallback".
        xmlReader.moveToElement();
        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            boolean rReadingCompleted = false;

            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rPr"*/7:
                {
                    // WORDSNET-8064 If this run contains several runPr with same prefix, we should create new run for each runPr.
                    // WORDSNET-13481 MS Word does it only when new 'w:t' or 'm:t' element is occurred.
                    // WORDSNET-17225 MS Word does it for 'w:tab' too.
                    if (createNewRun)
                    {
                        flushRun(reader, runPr);
                        runPr = new RunPr();
                        createNewRun = false;
                        isRunCreated = true;
                    }

                    mRunPrReader.read(reader, runPr);
                    break;
                }
                case /*"rFonts"*/8:
                    mRunPrReader.readFonts(xmlReader, runPr);
                    break;
                case /*"ruby"*/9:
                    readRuby(reader, runPr);
                    break;
                case /*"t"*/10:
                case /*"instrText"*/11:
                case /*"delText"*/12:
                case /*"delInstrText"*/13:
                {
                    // It does not matter how the text is declared in WordML - 'w:t', 'w:instrText', etc.
                    // It all goes to run's text anyway.
                    // The only exception is special chars. In WordML they are written as
                    //   <w:r>
                    //     <w:t>[special char]</w:t>
                    //   </w:r>
                    // where [special char] is a special char symbol.
                    readText(reader, runPr);
                    createNewRun = true;
                    break;
                }

                case /*"fldChar"*/14:
                    // WORDSNET-28498 Avoid run properties to be shared across nodes.
                    flushRun(reader, runPr);
                    runPr = AttrCollectionExtension.deepClone(runPr);
                    readFldChar(reader, runPr);
                    runPr = AttrCollectionExtension.deepClone(runPr);
                    break;

                case /*"tab"*/15:
                    // WORDSNET-19151 MS Word ignores the 'tab' element inside the unsupported child node.
                    if (!selfRecursive)
                    {
                        msStringBuilder.append(xmlReader.getRunTextBuilder(), ControlChar.TAB);
                        createNewRun = true;
                    }
                    break;
                case /*"cr"*/16:
                    msStringBuilder.append(xmlReader.getRunTextBuilder(), ControlChar.PARAGRAPH_BREAK);
                    break;
                case /*"softHyphen"*/17:
                    msStringBuilder.append(xmlReader.getRunTextBuilder(), ControlChar.OPTIONAL_HYPHEN);
                    break;
                case /*"noBreakHyphen"*/1:
                    msStringBuilder.append(xmlReader.getRunTextBuilder(), ControlChar.NON_BREAKING_HYPHEN);
                    break;
                case /*"sym"*/18:
                    readSymbol(reader, runPr);
                    // WORDSNET-26245. Create new attribute collection for runs after symbolic run.
                    runPr = AttrCollectionExtension.deepClone(runPr);
                    isRunCreated = true;
                    break;
                case /*"endnoteReference"*/19: // DOCX.
                case /*"endnote"*/20: // WML.
                    rReadingCompleted = readFootnote(reader, runPr, FootnoteType.ENDNOTE);
                    break;
                case /*"footnoteReference"*/21: // DOCX.
                case /*"footnote"*/22: // WML.
                    rReadingCompleted = readFootnote(reader, runPr, FootnoteType.FOOTNOTE);
                    break;
                case /*"footnoteRef"*/23:
                case /*"endnoteRef"*/24:
                    readSpecialChar(reader, runPr, ControlChar.FOOTNOTE_REF_CHAR);
                    break;
                case /*"separator"*/25:
                    readSpecialChar(reader, runPr, ControlChar.FOOTNOTE_SEPARATOR_CHAR);
                    break;
                case /*"continuationSeparator"*/26:
                    readSpecialChar(reader, runPr, ControlChar.FOOTNOTE_CONTINUATION_CHAR);
                    break;
                case /*"commentReference"*/27: // DOCX.
                case /*"annotation"*/28: // WML.
                case /*"content"*/29:
                    if(!reader.isEquationXmlReader())
                        readComment(reader, runPr);
                    break;
                case /*"annotationRef"*/30:
                    readSpecialChar(reader, runPr, ControlChar.ANNOTATION_REF_CHAR);
                    break;
                case /*"pgNum"*/31:
                    readLegacyPageNumber(reader, runPr);
                    break;
                case /*"AlternateContent"*/32: // Word 2010 DrawingML Extensions.
                    // We do not check namespaces at all, this is not good.
                    // This one is xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006".
                    // WORDSNET-14642 Create copy of the run properties to avoid references duplication
                    // to one "Fallback" from shapes.
                    readAlternateContent(reader, AttrCollectionExtension.deepClone(runPr));
                    break;
                case /*"ptab"*/33:
                    readAbsolutePositionTab(reader, runPr);
                    break;
                case /*"commentRangeStart"*/34:
                    // WORDSNET-23757 Support of commentRangeStart in unexpected places.
                    flushRun(reader, runPr);
                    DocxCommentsReader.readCommentRangeStart(reader);
                    break;
                case /*"commentRangeEnd"*/35:
                    // WORDSNET-23757 Support of commentRangeEnd in unexpected places.
                    flushRun(reader, runPr);
                    DocxCommentsReader.readCommentRangeEnd(reader);
                    break;
                default:
                    // AN: Move all cases that occur in incorrect place ('p' or 't' element) from this switch to ReadTextLevelElement method
                    // to avoid duplication of the code. I do not wand to move whole switch to this method, because using
                    // ReadTextLevelElement we process text level elements in incorrect places. This is not documented, so we do not know
                    // which of these elements can be in incorrect place and how we should handle them. Wait while a customer report a problem.
                    if (!readTextLevelElement(reader, runPr))
                    {
                        // WORDSNET-18361 Read child nodes of unsupported tag and place the read content into the current run.
                        readRChildren(reader, runPr, true);
                    }
                    break;
            }

            if (rReadingCompleted)
                break;
        }

        // WORDSNET-17225 Only needed when new runs were created inside this method.
        // We need to flush with the new last created runPr.
        if (isRunCreated)
            flushRun(reader, runPr);
    }

    /// <summary>
    /// Reads "ptab" element and attributes.
    /// </summary>
    private static void readAbsolutePositionTab(NrxDocumentReaderBase reader, RunPr runPr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        AbsolutePositionTab ptab = new AbsolutePositionTab(reader.getDocument(), runPr);
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"alignment"*/36:
                    ptab.setAlignment(DocxEnum.docxToAbsolutePositionTabAlignment(xmlReader.getValue()));
                    break;
                case /*"leader"*/37:
                    ptab.setLeaderChar(DocxEnum.docxToAbsolutePositionTabLeaderChar(xmlReader.getValue()));
                    break;
                case /*"relativeTo"*/38:
                    ptab.setPositioningBase(DocxEnum.docxToAbsolutePositionTabPositioningBase(xmlReader.getValue()));
                    break;
                default:
                    break;
            }
        }

        reader.addChild(ptab);
    }

    /// <summary>
    /// Adds <see cref="Run"/> to the model if non-empty run text elements were previously encountered.
    /// </summary>
    static void flushRun(NrxDocumentReaderBase reader, RunPr runPr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        String runText = xmlReader.getRunTextBuilder().toString();
        if (StringUtil.hasChars(runText) || runPr.contains(FontAttr.RUBY))
        {
            Run run = new Run(reader.getDocument(), runText, runPr);
            reader.addChild(run);

            // AM. Word ignores hpsBase attribute from ruby properties and takes it from ruby run.
            // That's why we need to update it after run is placed to DOM.
            if (runPr.contains(FontAttr.RUBY))
                processRuby(run);

            FormatRevisionUtil.convertToPositiveDifference(run);

            reader.detectToggleIssue(run);

            // Reset run text builder.
            xmlReader.getRunTextBuilder().setLength(0);
        }
    }

    /// <summary>
    /// If the current element is inline, then read it into the model and returns true.
    ///
    /// Reworked fix of issues: 1589, 11002, 14837, 20010, 3760, 22434.
    /// AN: There were several problems where Aspose.Words lost content because elements were in wrong place.
    /// Theoretically, any of elements that occurs in 'r' element can also occur in 't' or 'p' element,
    /// but for now I added cases only for elements that already occurred in 't' or 'p' element.
    /// To handle this, we use this method to read inline elements, if there is nothing to read just continue processing in regular way.
    /// </summary>
    boolean readTextLevelElement(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        boolean readElement = true;
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            // DD: Looking at all these resiliency cases I think someday we need to rework the design so it
            // is more generic towards fixing wrong placements of OOXML elements.
            // Consider this whenever we fix more of such cases across run or para levels.
            case /*"pic"*/39:
            case /*"pict"*/40:
            case /*"object"*/41:
                // RESILIENCY 1589 A "pict" element occurs inside a "t" element and MS Word reads it well. So do we now.

                // WORDSNET-26269 Shape and next run shared the same RunPr.
                // But we cannot start with new RunPr if we are within AlternateContent section.
                readElement = readVmlObject(reader, reader.IsInAlternateContent ? runPr : AttrCollectionExtension.deepClone(runPr));
                break;
            case /*"drawing"*/42:
                // RESILIENCY 22434 - a "drawing" tag is found inside "t" element.
                readDrawingML(reader, runPr);
                break;
            case /*"AlternateContent"*/32:
                // WORDSNET-23885 "AlternateContent" inside "p" element.
                readAlternateContent(reader, AttrCollectionExtension.deepClone(runPr));
                break;
            case /*"br"*/43:
                // RESILIENCY 11002 / 14837 - a "br" element occurs inside "t" element.
                xmlReader.getRunTextBuilder().append(readBreak(xmlReader, runPr));
                break;
            case /*"fldChar"*/14:
                // RESILIENCY 17632  - a "fldChar" tag is found inside "p" element.
                readFldChar(reader, runPr);
                break;
            case /*"fldSimple"*/44:
                // RESILIENCY 16450 - a 'fldSimple' tag is found inside 'r' element.
                mFldSimpleReader.read(reader, runPr);
                break;
            case /*"r"*/45:
                // WORDSNET-5056 'r' inside 'r' element.
                // WORDSNET-13961 Separate attributes for inner run.
                readCore(reader, AttrCollectionExtension.deepClone(runPr));
                break;
            case /*"tbl"*/3:
                resilientTableRead(reader);
                break;
            case /*"body"*/46:
                resilientBodyRead(reader);
                break;
            case /*"rPr"*/7:
                mRunPrReader.read(reader, runPr);
                break;
            case /*"p"*/2:
                // WORDSNET-9791 there is paragraph 'p' element inside 'sdtContent' of inline level sdt.
                // WORDSNET-10434 'p' elements inside 'r'.
                readElement = resilientParaReadInsideInline(reader, runPr);
                break;
            case /*"oMath"*/47:
                // WORDSNET-19522 Math equations placed as child of a run.
                DocxMathReader.readOMath(reader);
                break;
            case /*"hlink"*/48:
            case /*"hyperlink"*/49:
                // WORDSNET-13627 processing undocumented case in spec with hyper-link inside “Run” element.
                resilientHyperlink(reader, runPr);
                break;
            case /*"sect"*/50:
                // WORDSNET-13416 'sect' element inside run. This element is just a hint for MS Word,
                // so skip it and continue reading its content.
                readRChildren(reader, runPr, false);
                break;
            case /*"sectPr"*/51:
                // WORDSNET-13416 We have skipped 'sect' element above and continue reading its content.
                // There is 'sectPr' element inside it.
                reader.readGlobalSectPr(false);
                break;
            case /*"bookmarkStart"*/52:
                // WORDSNET-19438 Bookmark elements are child of w:r element.
                flushRun(reader, runPr);
                DocxBookmarkReader.readStart(reader);
                break;
            case /*"bookmarkEnd"*/53:
                flushRun(reader, runPr);
                DocxBookmarkReader.readEnd(reader);
                break;
            case /*"t"*/10:
                // WORDSNET-24761 't' element inside 'fldSimple' element.
                readText(reader, runPr);
                flushRun(reader, runPr);
                break;
            default:
                readElement = readFormatSpecific(xmlReader.getLocalName(), reader, runPr);
                break;
        }

        return readElement;
    }

    protected abstract boolean readFormatSpecific(String localName, NrxDocumentReaderBase reader, RunPr runPr) throws Exception;

    /// <summary>
    /// Make additional processing related to contained Ruby element.
    /// </summary>
    private static void processRuby(Run rubyRun)
    {
        int size = (/*int*/Integer)InlineHelper.fetchAttr(rubyRun, FontAttr.SIZE);
        Ruby ruby = (Ruby)rubyRun.getRunPr().get(FontAttr.RUBY);
        ruby.BaseSize = size;

        ruby.validate(rubyRun);
    }

    private static boolean isDefaultTextInputChar(String t)
    {
        return (t.length() == 1) && (t.charAt(0) == ControlChar.DEFAULT_TEXT_INPUT_CHAR);
    }

    /// <summary>
    /// RK This is a workaround for the problem where in one specific case Woodstox XML parser on Java reports text,
    /// whereas .NET parser reports whitespace. It is the case of an indented element with start and end tags, but no text.
    /// [parent]
    ///     [child]
    ///     [/child]
    /// [parent]
    ///
    /// Woodstox will report that [child] has "/r/n    " text inside it. It does not seem occur too often - only two tests had this problem.
    /// I cannot find a better solution hence a workaround.
    /// </summary>
    private static boolean isJavaWoodstoxWhitespace(String t)
    {
        if ((t.length() > 0) && (t.charAt(0) == '\r'))
            return StringUtil.containsOnlyWhitespaces(t);
        else
            return false;
    }

    /// <summary>
    /// Process case when found hyper-link inside run.
    /// </summary>
    /// <param name="reader">Document reader.</param>
    /// <param name="runPr">Object that contains "Run" attributes.</param>
    private void resilientHyperlink(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        // Add read "Run" data before hyper-link to the model.
        flushRun(reader, runPr);
        mHyperlinkReader.read(reader);
    }

    /// <summary>
    /// andrnosk: WORDSNET-9226 Found 'body' inside run: Finish current para, add a data from body and then
    /// create new para.
    /// </summary>
    private void resilientBodyRead(NrxDocumentReaderBase reader) throws Exception
    {
        Paragraph paragraph = (Paragraph)reader.getCurContainer();
        reader.popContainer(NodeType.PARAGRAPH);

        // Remove empty paragraph, Word does the same in this case.
        if (paragraph.getChildNodes(NodeType.ANY, false).getCount() == 0)
            paragraph.remove();

        mStoryReader.readChild(reader);

        reader.addAndPushContainer(new Paragraph(reader.getDocument()));
    }


    /// <summary>
    /// Reads table placed at inline level.
    /// </summary>
    private void resilientTableRead(NrxDocumentReaderBase reader) throws Exception
    {
        switch (reader.getCurContainer().getNodeType())
        {
            case NodeType.PARAGRAPH:
                resilientTableIntoParaRead(reader);
                break;
            // Table occurred into inline SDT.
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                resilientTableIntoSdtRead(reader);
                break;
            default:
                reader.getXmlReader().ignoreElementUnexpected(reader.isDocx());
                break;
        }
    }

    /// <summary>
    /// Implements reading table from inline SDT.
    /// </summary>
    private void resilientTableIntoSdtRead(NrxDocumentReaderBase reader) throws Exception
    {
        InlineMarkupResolver resolver = reader.getInlineResolver();
        StructuredDocumentTag sdt = resolver.getTopLevelInline();

        // Reserve position for table in the node sequence.
        int index = resolver.startPendingInline((StructuredDocumentTag)reader.getCurContainer(), NodeType.TABLE);

        // Read table.
        readTable(reader);

        // Resolve it later when inline SDT will be read completely.
        resolver.endPendingInline(sdt, (Table)reader.getCurContainer().getLastNonAnnotationChild(), index);
    }

    /// <summary>
    /// WORDSNET-9791 Found 'p' inside 'sdtContent' of inline level sdt.
    /// The contents of the first paragraph moved straight into sdtContent, all follow-up paragraphs move the outside, like MSW.
    /// </summary>
    private void resilientParaReadInsideInlineSdt(NrxDocumentReaderBase reader) throws Exception
    {
        InlineMarkupResolver resolver = reader.getInlineResolver();
        StructuredDocumentTag sdt = resolver.getTopLevelInline();

        // Reserve position for paragraph in the node sequence.
        int index = resolver.startPendingInline((StructuredDocumentTag)reader.getCurContainer(), NodeType.PARAGRAPH);

        // Read the paragraph.
        mParaReader.read(reader);

        // Read paragraph have to be resolved later.
        resolver.endPendingInline(sdt, (Paragraph)reader.getCurContainer().getLastNonAnnotationChild(), index);
    }

    /// <summary>
    /// Reworked fix for 3760: Found table inside para: Finish current para, add a table and then create new para.
    /// </summary>
    private void resilientTableIntoParaRead(NrxDocumentReaderBase reader) throws Exception
    {
        Paragraph paragraph = (Paragraph)reader.getCurContainer();
        reader.popContainer(NodeType.PARAGRAPH);

        readTable(reader);

        // MS Word places this paragraph after the table.
        // Runs before the table are moved to its first cell. And the first paragraph of the first cell gets
        // properties of the table parent paragraph, do the same with expanding.

        Table table = (Table)reader.getCurContainer().getLastChild();
        Row firstRow = table.getFirstRow();
        if ((firstRow == null) || (firstRow.getFirstCell() == null))
        {
            // Looks like MS Word removes tables without rows and cannot open a document if a row has no cells.
            table.remove();
            reader.setCurContainer(paragraph);
            return;
        }

        Paragraph firstCellParagraph = (Paragraph)firstRow.getFirstCell().getChild(NodeType.PARAGRAPH, 0, true);
        if (firstCellParagraph != null)
        {
            firstCellParagraph.insertAfter(paragraph.getFirstChild(), null, null);
            ParaPr paraPr = AttrCollectionExtension.deepClone(paragraph.getParaPr());
            firstCellParagraph.getParaPr().expandTo(paraPr);
            firstCellParagraph.setParaPr(paraPr);
        }

        paragraph.remove();
        reader.addAndPushContainer(paragraph);
    }

    protected abstract void readTable(NrxDocumentReaderBase reader) throws Exception;

    /// <summary>
    /// Fix for defect 20010: Found a para inside para: Finish current para, add a para then create a new para.
    /// </summary>
    /// <remarks>DD: I could have made resilient read without breaking para by creating temp container, reading into it
    /// and then inserting content from temp container into the current container.
    /// However I decided to do similar to <see cref="ResilientTableRead"/> for approach consistency.</remarks>
    private void resilientParaRead(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        flushRun(reader, runPr);

        CompositeNode curParagraph = reader.getCurContainer();
        reader.setCurContainer(curParagraph.getParentNode());

        boolean isSectionBreak = mParaReader.read(reader);

        Paragraph readParagraph = ms.as(reader.getCurContainer().getLastNonAnnotationChild(), Paragraph.class);

        // WORDSNET-11628 Need to start a new section if it was read within current paragraph.
        if (isSectionBreak)
        {
            reader.startNewSection();
            reader.getCurContainer().appendChild(curParagraph);
        }

        if (readParagraph != null)
        {
            // WORDSNET-19303 Move all content of 'curParagraph' as a block, i.e. without changing sequence.
            Paragraph targetParagraph = ((Paragraph)curParagraph.nextSiblingOfType(NodeType.PARAGRAPH) != null ? (Paragraph)curParagraph.nextSiblingOfType(NodeType.PARAGRAPH) : readParagraph);
            while (curParagraph.hasChildNodes())
                targetParagraph.prependChild(curParagraph.getLastChild());

            readParagraph.insertNext(curParagraph);

            if (curParagraph.getNodeType() == NodeType.PARAGRAPH)
            {
                // WORDSNET-24226 Copy formatting from parent container.
                ParaPr sourceParaPr = ((Paragraph)curParagraph).getParaPr();
                for (int key : sourceParaPr.getKeys())
                {
                    // Ignore some keys according to the Word behavior. See related Test19303.
                    if ((key == ParaAttr.ISTD) || (key == ParaAttr.RSID_P))
                        continue;

                    readParagraph.getParaPr().setAttrNoOverride(key, sourceParaPr.get(key));
                }
            }
        }

        reader.setCurContainer(curParagraph);
    }

    private static void readSpecialChar(NrxDocumentReaderBase reader, RunPr runPr, char c)
    {
        flushRun(reader, runPr);
        reader.addChild(new SpecialChar(reader.getDocument(), c, runPr));
    }

    private static void readSymbol(NrxDocumentReaderBase reader, RunPr runPr)
    {
        flushRun(reader, runPr);

        RunPr symbolRunPr = AttrCollectionExtension.deepClone(runPr);

        // WORDSNET-12033 Character category does not affect symbols so remove it.
        symbolRunPr.remove(FontAttr.CHARACTER_CATEGORY_HINT);
        char sym = reader.getXmlReader().readSymbol(symbolRunPr);
        symbolRunPr.setAttr(FontAttr.SYS_SYMBOL, true);

        // WORDSNET-17859 Exclude this character from common SYM processing. See Test17859() for details.
        if (sym != 0xA0)
        {
            // WORDSNET-17650 Detect symbolic font by charset.
            FontInfo fontInfo = reader.getDocument().getFontInfos().get(symbolRunPr.getNameAscii());
            if ((fontInfo != null) && (fontInfo.getCharset() == CodePage.SYMBOL_CHAR_SET))
                sym = FontUtil.symbolToUnicode(sym);
        }

        // WORDSNET-14953 Append symbol to preceding node when it is footnote and formatted as footnote reference mark.
        if (isReferenceMarkChunk(reader, symbolRunPr))
            ((Footnote)reader.getCurContainer().getLastNonAnnotationChild()).setReferenceMark(((Footnote)reader.getCurContainer().getLastNonAnnotationChild()).getReferenceMark() + sym);
        else
            reader.addChild(new Run(reader.getDocument(), Character.toString(sym), symbolRunPr));
    }

    /// <summary>
    /// Returns "True" when current "sym" content must be appended as the separate run node.
    /// </summary>
    private static boolean isReferenceMarkChunk(NrxDocumentReaderBase reader, RunPr runPr)
    {
        Debug.assertTrue((reader != null) && (runPr != null));

        // Last appended node must be "Footnote", otherwise interpret current node as separate run.
        Node prevNode = reader.getCurContainer().getLastNonAnnotationChild();
        Footnote footnote = (prevNode != null) && (prevNode.getNodeType() == NodeType.FOOTNOTE) ? (Footnote)prevNode : null;

        // Word uses not more than 10 characters.
        if ((footnote == null) || footnote.isAuto() || (footnote.getReferenceMark().length() >= 10))
            return false;

        // «FootnoteReference» style required to treat an "inline" node as part of the "Footnote".
        Style footnoteRefStyle = reader.getDocument().getStyles().getBySti(StyleIdentifier.FOOTNOTE_REFERENCE, false);
        if (!runPr.contains(FontAttr.ISTD) || (footnoteRefStyle == null) || (runPr.getIstd() != footnoteRefStyle.getIstd()))
            return false;

        // It is necessary to create separate run when properties of "inline" nodes are different.
        // Otherwise formatting will be lost.
        boolean runPrEquals = footnote.getRunPr().equals(runPr, RunPr.NON_COLLAPSIBLE_FONT_KEYS);
        return runPrEquals;
    }

    /// <summary>
    /// Reads a footnote reference and retrieves a corresponding footnote for it.
    /// </summary>
    /// <param name="reader">Where to read the footnote reference from.</param>
    /// <param name="runPr">The run properties to apply to the footnote node.</param>
    /// <param name="footnoteType">Whether it is a footnote or endnote.</param>
    /// <returns>
    /// Returns <c>true</c> if the <paramref name="reader"/> has read the `r` element.
    /// </returns>
    private boolean readFootnote(NrxDocumentReaderBase reader, RunPr runPr, /*FootnoteType*/int footnoteType) throws Exception
    {
        flushRun(reader, runPr);

        int id = -1;
        boolean suppressRef = false;

        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/54:
                    id = xmlReader.getValueAsInt();
                    break;
                case /*"type"*/55:    // WML.
                    // This is supposed to be empty string or "normal" indicating a normal footnote or endnote.
                    break;
                case /*"customMarkFollows"*/56:
                    // WORDSNET-4993 setting suppressRef if footnote with custom mark used.
                    suppressRef = xmlReader.getValueAsBool();
                    break;
                case /*"suppressRef"*/57: // WML
                    suppressRef = xmlReader.getValueAsBool();
                    break;
                default:
                    // Ignored.
                    break;
            }
        }

        Footnote footnote = getFootnote(reader, id, footnoteType, runPr);
        if (footnote == null)
            return false;

        // WORDSNET-4993 setting proper footnote type (changed IsAuto to be writable for this).
        footnote.isAuto(!suppressRef);
        footnote.setRunPrInternal(runPr);

        boolean rReadingCompleted = suppressRef && readCustomFootnoteMark(reader, runPr, footnote, id);

        reader.addChild(footnote);

        return rReadingCompleted;
    }

    protected abstract Footnote getFootnote(NrxDocumentReaderBase reader, int id, /*FootnoteType*/int footnoteType, RunPr runPr) throws Exception;

    /// <summary>
    /// WORDSNET-4993, 5323, 5324, 12842: reading reference mark:
    /// can be a single 't', 'br', 'sym'  or 'tab' right after footnote.
    /// </summary>
    /// <returns>
    /// Returns <c>true</c> if the <paramref name="reader"/> has read the `r` element.
    /// </returns>
    private static boolean readCustomFootnoteMark(NrxDocumentReaderBase reader, RunPr runPr, Footnote footnote, int id)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (xmlReader.readChild("r"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"t"*/10:
                case /*"delText"*/12:
                case /*"instrText"*/11:
                {
                    if (xmlReader.readChildWithTextValues("t", AnyXmlTextHandlingConsts.TEXT_AND_SIGNIFICANT) &&
                        ("".equals(xmlReader.getLocalName())))
                    {
                        footnote.setReferenceMark(xmlReader.getValue());
                    }
                    else
                    {
                        // WORDSNET-19063 Word doesn't generate error while reading custom footnote mark with empty
                        // reference mark. It is not a full solution because Word can bind reference mark to some
                        // paragraph while saving to docx.
                        reader.warn(
                            WarningType.DATA_LOSS,
                            WarningSource.DOCX,
                            msString.format(WarningStrings.FOOTNOTE_WITH_EMPTY_REFERENCE_MARK, id));
                    }

                    break;
                }
                case /*"br"*/43:
                    footnote.setReferenceMark(Character.toString(readBreak(xmlReader, runPr)));
                    break;
                case /*"sym"*/18:
                    footnote.setReferenceMark(Character.toString(reader.getXmlReader().readSymbol(runPr)));
                    break;
                case /*"tab"*/15:
                    footnote.setReferenceMark(ControlChar.TAB);
                    break;
                default:
                    throw new IllegalStateException("Unsupported footnote reference mark.");
            }

            return false;
        }

        // WORDSNET-16473 Word doesn't generate error while reading custom footnote mark without reference mark.
        // It is not a full solution because Word can bind reference mark to some paragraph while saving to docx.
        reader.warn(
            WarningType.DATA_LOSS,
            WarningSource.DOCX,
            msString.format(WarningStrings.FOOTNOTE_WITHOUT_REFERENCE_MARK, id));

        return true;
    }

    private void readComment(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        flushRun(reader, runPr);

        IWarningCallback warningCallback = reader.getLoadOptions().getWarningCallback();

        Comment comment = getComment(reader, runPr);

        //strubaev WORDSNET-13545 Do not process nodes of comment that is already a child of another node.
        if (comment != null)
        {
            if (comment.getParentNode() == null)
            {
                comment.setRunPrInternal(runPr);
                reader.addChild(comment);
            }
            else
            {
                if (warningCallback != null)
                {
                    warningCallback.warning(new WarningInfo(WarningType.DATA_LOSS, WarningSource.DOCX, msString.format("Custom comment with id={0} was already added", Integer.toString(comment.getId()))));
                }
            }
        }
    }

    protected abstract Comment getComment(NrxDocumentReaderBase reader, RunPr runPr) throws Exception;

    private boolean readVmlObject(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        flushRun(reader, runPr);
        if (mVmlReader == null)
            return false;

        ArrayList<ShapeBase> shapes = mVmlReader.read(reader);
        for (ShapeBase shapeBase : shapes)
        {
            if (shapeBase != null)
            {
                shapeBase.setRunPr(runPr);

                // WORDSNET-7796 Try to get image size from image data. Made for WML only. See TestJira7796 for details.
                // WORDSNET-10477 Replaces zero width and height with default values for DOCX.
                // WORDSNET-26342 MS Word doesn't fix shape zero size if 'position: absolute' shape style is
                // defined. We currently store position in the WrapType attribute, and this value can be
                // overwritten by wrap type itself. But let's ignore this case; if customers will complain,
                // we can implement temporary storage of position in IVmlShapeReaderContext.
                if (shapeBase.isImage() && (shapeBase.getWrapType() != WrapType.NONE))
                    ((Shape)shapeBase).fixZeroSize(reader.isDocx());

                reader.addChild(shapeBase);
            }
        }

        return true;
    }

    /// <summary>
    /// Reads 17.3.3.9 drawing (DrawingML Object)
    /// </summary>
    private static void readDrawingML(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        flushRun(reader, runPr);

        // I just cast here because DOCX reader implement this. WML reader does not, but DrawingML does not occur in WML.
        INrxDmlReader dmlReader = (INrxDmlReader)reader;
        ShapeBase drawingML = dmlReader.readDrawing(runPr);
        if (drawingML != null)
        {
            // Apply any current delete/insert/move revision.
            reader.StoryRevisionStack.apply(runPr, drawingML);

            drawingML.setRunPr(runPr);

            reader.addToDmlTextboxesMap(drawingML);
        }
    }

    private void readFldChar(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        flushRun(reader, runPr);
        mFldCharReader.read(reader, runPr);
    }

    /// <summary>
    /// As per a recommendation in OOXML we convert w:pgNum into a PAGE field.
    /// </summary>
    private static void readLegacyPageNumber(NrxDocumentReaderBase reader, RunPr runPr)
    {
        flushRun(reader, runPr);

        Node[] fieldNodes = MiscRwUtil.createPageField(reader.getDocument(), runPr);
        for (int curNode = 0; curNode < fieldNodes.length; curNode++)
            reader.addChild(fieldNodes[curNode]);
    }

    static char readBreak(NrxXmlReader xmlReader, RunPr runPr)
    {
        // Set the default character to line break. That corresponds to MS Word behavior.
        char br = ControlChar.LINE_BREAK_CHAR;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/55:
                    br = NrxRunEnum.xmlToBreak(xmlReader.getValue());
                    break;
                case /*"clear"*/58:
                    runPr.setAttr(FontAttr.LINE_BREAK_CLEAR, NrxRunEnum.xmlToLineBreakClear(xmlReader.getValue()));
                    break;
                default:
                    // Ignored.
                    break;
            }
        }

        return br;
    }

    /// <summary>
    /// [MS-ODRAWXML] 2.2.7 WordprocessingML Drawing
    /// Reads the mc:AlternateContent written by Word 2010. It can only occur inside an "r" element.
    /// </summary>
    void readAlternateContent(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        AlternateContent alternateContent = null;
        NrxXmlReader xmlReader = reader.getXmlReader();
        boolean choiceWasRead = false;

        reader.IsInAlternateContent = true;

        while (xmlReader.readChild("AlternateContent"))
        {
            // Choice and Fallback, are used to provide alternates for specified content.
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"Choice"*/59:
                    alternateContent = readChoice(reader, runPr);
                    choiceWasRead = true;
                    break;
                case /*"Fallback"*/60:
                    readFallback(reader, runPr, alternateContent);

                    // MS Word crashes when Fallback is specified before Choice.
                    // In this case we just read FallBack and stop the loop.
                    if (!choiceWasRead)
                        return;
                    else
                        reader.getComplianceInfo().markAsIsoTransitional();
                    break;
                default:
                    xmlReader.ignoreElement(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX, xmlReader.getLocalName());
                    break;
            }
        }

        reader.IsInAlternateContent = false;
    }

    /// <summary>
    /// Reads the Choice element. If there is a match between required namespaces (in this case Word 2010) the appropriate Choice is read.
    /// </summary>
    private AlternateContent readChoice(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        boolean choiceMatched = false;
        AlternateContent alternateContent = new AlternateContent();
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                // The Requires attribute specifies a set of space-delimited namespaces that must be understood in order to select that choice.
                case /*"Requires"*/61:
                {
                    String prefix = xmlReader.getValue();
                    if (StringUtil.hasChars(prefix) &&
                        isAllowedChoiceRequirement(xmlReader.lookupNamespace(prefix)))
                    {
                        choiceMatched = true;
                        alternateContent.setRequires(prefix);
                    }
                    break;
                }
                default:
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX, xmlReader.getLocalName());
                    break;
            }
        }

        if (choiceMatched)
        {
            // RK There is work here with a "temporary container". It is needed currently to work around the "DrawingML Text Box" problem e.g. WORDSNET-6710
            // In earlier OOXML documents DrawingML could not contain  MS Word content. But starting with some MS Word update, they can now have
            // DrawingML shapes with MS Word textbox content, e.g. paragraphs, tables and so on. Our model does not yet support DrawingML with MS Word content.
            // So this workaround detects this situation (that we cannot represent in the model) and uses the Fallback instead.
            //
            // I tried simplifying this code by just checking for the "wps" namespace (instead of checking for the "txbx" element, but that causes some
            // other VML shapes to be caught into Fallback use. This in turns breaks some unit tests. Since this all is a temporary solution,
            // I agree to leave this code working with temporary container for now. But the team needs to rework how to store VML in DrawingML.

            // Remember the current container and remove it from the tree, we will restore it when finish reading Choice.
            CompositeNode curContainer = reader.getCurContainer();
            reader.popContainer(curContainer.getNodeType());
            curContainer.remove();

            // Instead of the current container push a temporary container, where we will read Choice.
            CompositeNode temporaryContainer = (CompositeNode)curContainer.deepClone(false);
            reader.addAndPushContainer(temporaryContainer);

            // Read Choice to the temporary container.
            readRChildren(reader, runPr, false);

            // Remove the temporary container from the tree, and restore the previously remembered container.
            reader.popContainer(temporaryContainer.getNodeType());
            temporaryContainer.remove();
            reader.addAndPushContainer(curContainer);

            // If Choice does not contains unsupported elements, insert its content into the tree.
            while (temporaryContainer.hasChildNodes())
                reader.getCurContainer().appendChild(temporaryContainer.getFirstChild()); // AppendChild removes the child so using FirstChild every time is okay.

            return alternateContent;
        }

        return null;
    }

    /// <summary>
    /// Checks that a namespace referenced by value of the Requires attribute of the alternate content choice part
    /// is a known DOCX namespace.
    /// </summary>
    static boolean isAllowedChoiceRequirement(String nameSpace)
    {
        // Check whether this is MS Word 2010 or 2016 namespace or well known namespace.
        // WORDSNET-12000 Requires attribute of AlternateContent equals "v" it is well known namespace
        // so choice will be matched.
        return
            DocxNamespaces.isWord2010Namespace(nameSpace) ||
            DocxNamespaces.isWord2016Namespace(nameSpace) ||
            DocxNamespaces.isWellKnownNamespace(nameSpace);
    }

    /// <summary>
    /// Reads "Fallback" child elements with error suppression.
    /// </summary>
    /// <param name="reader">Provides methods for document reading.</param>
    /// <param name="runPr">Keeps run attributes values.</param>
    /// <param name="alternateContent">Container to store "AlternateContent" data.</param>
    private void readFallbackSafe(NrxDocumentReaderBase reader, RunPr runPr, AlternateContent alternateContent) throws Exception
    {
        Debug.assertTrue(alternateContent != null);

        String localeName = reader.getXmlReader().getLocalName();
        CompositeNode prevContainer = reader.getCurContainer();
        CompositeNode tmpContainer = (CompositeNode)reader.getCurContainer().deepClone(false);
        reader.addAndPushContainer(tmpContainer);

        // WORDSNET-13846 Preserve "Fallback" reading from any faults.
        // "FallBack" will be written by AW while processes validation.
        try
        {
            // Read Fallback to temporary container.
            readRChildren(reader, AttrCollectionExtension.deepClone(runPr), false);
        }
        catch (Exception e)
        {
            // Read broken element to the end.
            while (reader.getXmlReader().readChild(localeName))
                reader.getXmlReader().ignoreElementNoWarn();

            clearTmpContainer(reader, tmpContainer, prevContainer);
            // "Fallback" part set to null in case when some errors occur.
            tmpContainer = null;

            // Register error as warning.
            String msg = msString.format(READ_FALLBACK_ERR_TMPL, e.getMessage());
            reader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.SHAPES, msg);
        }

        alternateContent.setFallBack(tmpContainer);
        Node fallbackShape = (tmpContainer != null) ? tmpContainer.getFirstChild() : null;

        if (fallbackShape instanceof ShapeBase)
            ((ShapeBase)fallbackShape).isFallback(true);

        // Save AlternateContent to RunPr bag.
        runPr.setAlternateContent(alternateContent);
        clearTmpContainer(reader, tmpContainer, prevContainer);
    }


    /// <summary>
    /// Restores previous container and removes current temporary container.
    /// </summary>
    /// <param name="reader">Provides methods for document reading.</param>
    /// <param name="tmpCont">Temporary container to remove.</param>
    /// <param name="prevCont">Original container to restore.</param>
    private static void clearTmpContainer(NrxDocumentReaderBase reader, CompositeNode tmpCont, CompositeNode prevCont)
    {
        if (tmpCont == null)
            return;

        // Restore and remove unneeded container.
        reader.setCurContainer(prevCont);
        tmpCont.remove();
    }

    /// <summary>
    /// Read Fallback element which can be utilized as a default when no match with Choice occurs.
    /// </summary>
    private void readFallback(NrxDocumentReaderBase reader, RunPr runPr, AlternateContent alternateContent) throws Exception
    {
        // If Choice was matched, we should read Fallback to temporary container
        // and then use it for writing correct Ecma376_2006 document and mc:AlternateContent for Iso29500_2008_Transitional document.
        if (alternateContent != null)
            readFallbackSafe(reader, runPr, alternateContent);
        else
            readRChildren(reader, runPr, false);
    }

    /// <summary>
    /// Tries to recover 'p' elements if it unexpectedly occurred inside inline.
    /// </summary>
    private boolean resilientParaReadInsideInline(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        switch (reader.getCurContainer().getNodeType())
        {
            case NodeType.PARAGRAPH:
                resilientParaRead(reader, runPr);
                return true;
            case NodeType.STRUCTURED_DOCUMENT_TAG:
                resilientParaReadInsideInlineSdt(reader);
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Reads 'ruby' element.
    /// </summary>
    private void readRuby(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        Ruby ruby = new Ruby();
        runPr.setAttr(FontAttr.RUBY, ruby);

        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("ruby"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rubyPr"*/62:
                    readRubyPr(reader, ruby);
                    break;

                case /*"rt"*/63:
                    readRubyRun(reader, ruby.getTop());
                    break;

                case /*"rubyBase"*/64:
                    readRubyRun(reader, ruby.getBase());
                    break;

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'rt' or 'rubyBase' elements which are <see cref="Ruby"/> top and base runs.
    /// </summary>
    private void readRubyRun(NrxDocumentReaderBase reader, RubyChunkCollection rubyChunks) throws Exception
    {
        AnyXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();

        Paragraph tempContainer = new Paragraph(reader.getDocument());

        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                // AM. Not ready to read edit revisions for Ruby.
                // Read as ordinary content.
                case /*"ins"*/65:
                case /*"del"*/66:
                case /*"r"*/45:
                    // Switch current container to Ruby.
                    CompositeNode savedContainer = reader.getCurContainer();
                    reader.setCurContainer(tempContainer);
                    tempContainer.getChildNodes(NodeType.ANY, false).clear();

                    read(reader);

                    Run run = tempContainer.getFirstRun();
                    RubyChunk chunk = new RubyChunk();
                    chunk.Text = run.getText();
                    chunk.RunPr =  AttrCollectionExtension.deepClone(run.getRunPr());
                    rubyChunks.add(chunk);

                    // After read switch it back.
                    reader.setCurContainer(savedContainer);
                    break;

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'rubyPr' element which specifies <see cref="Ruby"/> properties.
    /// </summary>
    private static void readRubyPr(NrxDocumentReaderBase reader, Ruby ruby)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        while (xmlReader.readChild("rubyPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rubyAlign"*/67:
                    ruby.Alignment = NrxRunEnum.xmlToRubyAlignment(xmlReader.readVal());
                    break;

                case /*"hps"*/68:
                    ruby.TopSize = xmlReader.readValAsHalfPoints(complianceInfo);
                    break;

                case /*"hpsRaise"*/69:
                    ruby.Distance = xmlReader.readValAsHalfPoints(complianceInfo);
                    break;

                case /*"hpsBaseText"*/70:
                    ruby.BaseSize = xmlReader.readValAsHalfPoints(complianceInfo);
                    break;

                case /*"lid"*/71:
                    String lid = xmlReader.readVal();
                    int language = reader.isDocx()
                        ? LocaleConverter.docxTagToLocale(lid)
                        : LocaleConverter.wmlTagToLocale(lid);

                    ruby.Language = (/*Language*/int)language;
                    break;

                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static /*final*/ char[] G_WHITESPACE_CHARS = new char[] { ControlChar.SPACE_CHAR, ControlChar.TAB_CHAR };

    /// <summary>
    /// Message template for case when "Fallback" element cannot be loaded.
    /// </summary>
    private static final String READ_FALLBACK_ERR_TMPL = "Read 'Fallback' element failed. Error details: {0}";

    private /*final*/ NrxStoryReaderBase mStoryReader;
    private /*final*/ NrxRunPrReaderBase mRunPrReader;
    private /*final*/ NrxParaReaderBase mParaReader;
    private /*final*/ NrxFldCharReaderBase mFldCharReader;
    private /*final*/ NrxFldSimpleReader mFldSimpleReader;
    private /*final*/ NrxHyperlinkReaderBase mHyperlinkReader;
    private /*final*/ INrxVmlReader mVmlReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"",
		"noBreakHyphen",
		"p",
		"tbl",
		"rsidDel",
		"rsidR",
		"rsidRPr",
		"rPr",
		"rFonts",
		"ruby",
		"t",
		"instrText",
		"delText",
		"delInstrText",
		"fldChar",
		"tab",
		"cr",
		"softHyphen",
		"sym",
		"endnoteReference",
		"endnote",
		"footnoteReference",
		"footnote",
		"footnoteRef",
		"endnoteRef",
		"separator",
		"continuationSeparator",
		"commentReference",
		"annotation",
		"content",
		"annotationRef",
		"pgNum",
		"AlternateContent",
		"ptab",
		"commentRangeStart",
		"commentRangeEnd",
		"alignment",
		"leader",
		"relativeTo",
		"pic",
		"pict",
		"object",
		"drawing",
		"br",
		"fldSimple",
		"r",
		"body",
		"oMath",
		"hlink",
		"hyperlink",
		"sect",
		"sectPr",
		"bookmarkStart",
		"bookmarkEnd",
		"id",
		"type",
		"customMarkFollows",
		"suppressRef",
		"clear",
		"Choice",
		"Fallback",
		"Requires",
		"rubyPr",
		"rt",
		"rubyBase",
		"ins",
		"del",
		"rubyAlign",
		"hps",
		"hpsRaise",
		"hpsBaseText",
		"lid"
	);

}

