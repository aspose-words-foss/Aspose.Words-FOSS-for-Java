// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.RandomUtil;
import com.aspose.HashSetGeneric;
import com.aspose.OpcPackagePart;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.DictionaryExtension;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.StringToIntDictionary;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.UriUtil;
import com.aspose.IntToIntDictionary;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.FileSystem;
import com.aspose.StreamUtil;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.FileFormat;
import com.aspose.ImageUtil;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.FileFormatCore;
import com.aspose.DrColor;
import com.aspose.IntToObjDictionary;
import com.aspose.ms.System.StringComparison;
import java.util.ArrayList;
import java.util.HashMap;
import com.aspose.OpcRelationship;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.Guid;
import com.aspose.OpcPackageBase;
import com.aspose.ObjToIntDictionary;
import com.aspose.words.ref.Ref;


/// <summary>
/// Base class for main and glossary document writers.
/// </summary>
abstract class DocxDocumentWriterBase extends
    DocumentVisitor implements
    IVmlShapeWriterContext,
    IDmlShapeWriterContext,
    INrxWriterContext,
    IMathWriterContext
{
    protected DocxDocumentWriterBase(
        DocxWriter writer,
        DocumentBase doc,
        IWarningCallback warningCallback)
    {
        Debug.assertTrue(writer != null);
        Debug.assertTrue(doc != null);

        mWriter = writer;
        mDoc = doc;

        mStyleIdGenerator = new NrxStyleIdGenerator(mDoc.getStyles());
        mRunWriter = new DocxRunWriter(this);
        mFieldsWriter = new DocxFieldsWriter(this, mRunWriter);
        mWarningCallback = warningCallback;
    }

    /// <summary>
    /// Generates unique paraId value.
    /// </summary>
    int generateParaId(/*StoryType*/int storyType)
    {
        while (true)
        {
            int paraId = RandomUtil.newGuid().hashCode();
            // WORDSNET-16092 ID should be a positive number, otherwise MS Word cannot find such paragraph.
            paraId &= 0x7fffffff;

            if (paraId == 0)
                continue;

            // Check that paraId is not used.
            HashSetGeneric<Integer> usedParaId = (HashSetGeneric<Integer>)mWriter.getSaveInfo().UsedParaIdArray[(int)storyType];
            if (usedParaId.contains(paraId))
                continue;

            usedParaId.add(paraId);
            return paraId;
        }
    }

    void write() throws Exception
    {
        mDocumentPart = doCreateDocumentPart();

        mFootnotesWriter = new DocxFootnotesWriter(FootnoteType.FOOTNOTE, this);

        mEndnotesWriter = new DocxFootnotesWriter(FootnoteType.ENDNOTE, this);

        DocxSettingsWriter.write(this);
        DocxWebSettingsWriter.write(this, getSaveInfo().HasHtmlBlockReferences);
        DocxFontTableWriter.write(this);

        pushBuilder(new DocxBuilder(mDocumentPart, getSaveOptions().getPrettyFormat(), getCompliance(), getMsWordExtensionsVersion(),
            mWarningCallback));

        doWrite();

        DocxNumberingWriter.write(this);
        DocxStylesWriter.write(this);
        DocxPeopleWriter.write(this);

        // Close all auxiliary part writers.
        if (mCommentsWriter != null)
            mCommentsWriter.close();

        mFootnotesWriter.close();
        mEndnotesWriter.close();

        popBuilder();
    }

    protected abstract OpcPackagePart doCreateDocumentPart() throws Exception;

    protected abstract void doWrite() throws Exception;

    /// <summary>
    /// Writes an OOXML "body" of the main document or a building block.
    /// </summary>
    protected void writeSections(CompositeNode sectionsContainer) throws Exception
    {
        for (Node node = sectionsContainer.getFirstChild(); node != null; node = node.getNextSibling())
        {
            setCurrentSection((Section)node);
            // RK I'd like to refactor so I can delete this member.
            mLastParagraphOfCurrentSection = getCurrentSection().getBody().getLastParagraph();

            // This writes the main story of the section.
            getCurrentSection().getBody().accept(this);
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    public void warn(/*WarningType*/int warningType, String description)
    {
        warn(warningType, WarningSource.WORD_ML, description);
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    public void warn(/*WarningType*/int warningType, /*WarningSource*/int source, String description)
    {
        if (mWarningCallback != null)
            mWarningCallback.warning(new WarningInfo(warningType, source, description));
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitTableStart(Table table) throws Exception
    {
        mBuilder.startElement("w:tbl");

        NrxRowPrWriter.write(table.getFirstRow().getTablePr(), true, false, this);

        table.cellWidthsToTableGrid();
        NrxTableGrid.writeTblGrid(mBuilder, table);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitTableEnd(Table table)
    {
        mBuilder.endElement("w:tbl");
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Collapses table borders.
    /// </summary>
    /// <remarks>
    /// AM. The same as AttrCollection.Collapse but limited to borders attributes only.
    /// Actually we should process every row-level attributes here in the same way.
    /// </remarks>
    private static void collapseAttrs(TablePr srcTablePr, TablePr dstTablePr)
    {
        for (int key : TablePr.POSSIBLE_BORDER_KEYS.getValues())
        {
            Border dst = (Border)dstTablePr.getDirectAttr(key);
            Border src = (Border)srcTablePr.getDirectAttr(key);

            if ((dst == null) && (src != null) && (src.getLineStyle() != LineStyle.NONE) && !src.isInherited())
                dstTablePr.setAttr(key, TablePr.fetchDefaultAttr(key));
            else if (msObject.equals(dst, src))
                dstTablePr.remove(key);
        }

        // There are additional conditions for borders above, lets do separate loop for the rest.
        // Collapse only limited attribute set to reduce affected golds.
        for (int key : new int[]
            {
                TableAttr.TOP_PADDING, TableAttr.LEFT_PADDING, TableAttr.RIGHT_PADDING, TableAttr.BOTTOM_PADDING,
                TableAttr.CELL_SPACING, TableAttr.ALIGNMENT
            })
        {
            Object dst = dstTablePr.getDirectAttr(key);
            Object src = srcTablePr.getDirectAttr(key);

            if ((dst == null) && (src != null))
                dstTablePr.setAttr(key, TablePr.fetchDefaultAttr(key));
            else if (msObject.equals(dst, src))
                dstTablePr.remove(key);
        }
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitRowStart(Row row) throws Exception
    {
        mBuilder.startElement("w:tr");

        if (getCompliance() != OoxmlComplianceCore.ECMA_376)
        {
            if (getSaveOptions().getWriteExtendedIds())
            {
                if (row.getParaId() > 0)
                    mBuilder.writeAttribute("w14:paraId", NrxXmlUtil.intToHex(row.getParaId()));

                if (row.getTextId() > 0)
                    mBuilder.writeAttribute("w14:textId", NrxXmlUtil.intToHex(row.getTextId()));
            }
        }

        TablePr effectiveRowPr = AttrCollectionExtension.deepClone(row.getTablePr());

        // Collapse row attributes to get table in DOCX format.
        collapseAttrs(row.getParentTable().getFirstRow().getTablePr(), effectiveRowPr);

        NrxRowPrWriter.write(effectiveRowPr, false, false, this);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitRowEnd(Row row)
    {
        mBuilder.endElement("w:tr");
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell) throws Exception
    {
        mBuilder.startElement("w:tc");
        NrxCellPrWriter.write(cell.getCellPr(), this);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitCellEnd(Cell cell)
    {
        mBuilder.endElement("w:tc");
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para) throws Exception
    {
        mBuilder.startElement("w:p");

        // Actually we should save rsidRDefault here. But we have not found it yet.
        mBuilder.setRsidR(NrxXmlUtil.intToHex(para.getParagraphBreakRunPr().get(FontAttr.RSID_R)));
        mBuilder.writeAttribute("w:rsidR", mBuilder.getRsidR());

        mBuilder.writeAttribute("w:rsidRPr", NrxXmlUtil.intToHex(para.getParagraphBreakRunPr().get(FontAttr.RSID_R_PR)));

        // Not found yet.
        // mBuilder.WriteAttribute("w:rsidRDefault", ...)

        mBuilder.writeAttribute("w:rsidP", NrxXmlUtil.intToHex(para.getParaPr().get(ParaAttr.RSID_P)));

        if (getCompliance() != OoxmlComplianceCore.ECMA_376)
        {
            if (getCommentParagraphToParaId().containsKey(para))
            {
                // Generated paraId which holds comment tree structure should be written regardless option set.
                mBuilder.writeAttribute("w14:paraId", NrxXmlUtil.intToHex(getCommentParagraphToParaId().get(para)));
            }
            else
            {
                if (getSaveOptions().getWriteExtendedIds())
                {
                    if (para.getParaId() > 0)
                        mBuilder.writeAttribute("w14:paraId", NrxXmlUtil.intToHex(para.getParaId()));

                    if (para.getTextId() > 0)
                        mBuilder.writeAttribute("w14:textId", NrxXmlUtil.intToHex(para.getTextId()));
                }
            }
        }

        writeParaPr(para);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitParagraphEnd(Paragraph para) throws Exception
    {
        // Close "w:dir" and "w:bdo" elements for the current paragraph if any.
        msStack<String> bidiControlChars = DictionaryExtension.getValueOrNull(mParaToBidiControlChars, para);
        if (bidiControlChars != null)
            while (bidiControlChars.getCount() > 0)
                mBuilder.endElement(bidiControlChars.pop());

        mBuilder.endElement("w:p");

        if (ProgressProcessor != null)
            ProgressProcessor.step();

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitSmartTagStart(SmartTag smartTag)
    {
        DocxSmartTagWriter.writeStart(smartTag, mBuilder);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitSmartTagEnd(SmartTag smartTag)
    {
        DocxSmartTagWriter.writeEnd(mBuilder);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagStart(StructuredDocumentTag sdt) throws Exception
    {
        DocxSdtWriter.writeStart(sdt, this);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagRangeStart(StructuredDocumentTagRangeStart rangeStart) throws Exception
    {
        // Here we should write SDT.
        StructuredDocumentTag sdt = rangeStart.getInternalSdt();
        StructuredDocumentTagRangeEnd rangeEnd = rangeStart.getRangeEnd();

        // Write SDT only if all parts of "ranged" SDT is found.
        if (ArgumentUtil.bothAreNotNull(sdt, rangeEnd) && StructuredDocumentTag.isRangeValid(rangeStart, rangeEnd))
        {
            DocxSdtWriter.writeStart(sdt, this);
            mSdtRangeStartMap.add(rangeStart.getId(), rangeStart);
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagRangeEnd(StructuredDocumentTagRangeEnd rangeEnd) throws Exception
    {
        int id = rangeEnd.getId();

        StructuredDocumentTagRangeStart rangeStart = mSdtRangeStartMap.get(id);
        StructuredDocumentTag sdt = (rangeStart != null) ? rangeStart.getInternalSdt() : null;

        // Write SDT only if all parts of "ranged" SDT is found.
        if (ArgumentUtil.bothAreNotNull(sdt, rangeStart) && StructuredDocumentTag.isRangeValid(rangeStart, rangeEnd))
            DocxSdtWriter.writeEnd(sdt, this);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagEnd(StructuredDocumentTag sdt) throws Exception
    {
        DocxSdtWriter.writeEnd(sdt, this);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath) throws Exception
    {
        DocxMathWriter.writeStart(officeMath.getMathObject(), officeMath.getRunPr(), this);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitOfficeMathEnd(OfficeMath officeMath)
    {
        DocxMathWriter.writeEnd(mBuilder);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitRun(Run run) throws Exception
    {
        // Skip field code for HYPERLINK field if it is written as hyperlink.
        if (mFieldsWriter.getSkipThisInline())
            return VisitorAction.CONTINUE;

        mRunWriter.writeInline(run);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitSpecialChar(SpecialChar specialChar) throws Exception
    {
        // WORDSNET-18081 Preserve presentation of “DateShortChar” special character in the document.
        if (getSaveOptions().getKeepLegacyControlChars() && (msString.equals(specialChar.getText(), ControlChar.NON_BREAKING_HYPHEN)))
        {
            writeDateShortCharAsField(specialChar);
        }
        else
        {
            mRunWriter.writeInline(specialChar);
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitAbsolutePositionTab(AbsolutePositionTab tab)
    {
        mBuilder.startElement("w:r"); // ptab is stored in a run per spec.
        mBuilder.startElement("w:ptab");
        mBuilder.writeAttribute("w:relativeTo", DocxEnum.absolutePositionTabPositioningBaseToDocx(tab.getPositioningBase()));
        mBuilder.writeAttribute("w:alignment", DocxEnum.absolutePositionTabAlignmentToDocx(tab.getAlignment()));
        mBuilder.writeAttribute("w:leader", DocxEnum.absolutePositionTabLeaderCharToDocx(tab.getLeaderChar()));
        mBuilder.endElement("w:ptab");
        mBuilder.endElement("w:r");

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart) throws Exception
    {
        mFieldsWriter.writeFldChar(fieldStart);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator) throws Exception
    {
        mFieldsWriter.writeFldChar(fieldSeparator);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd) throws Exception
    {
        mFieldsWriter.writeFldChar(fieldEnd);
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
    {
        int id;

        String bookmarkKey = BookmarkValidatorItem.getKey(bookmarkStart);

        // Use separate dictionary for bookmarks located in fallback.
        boolean isFallback = bookmarkStart.getTopmostAncestor().getNodeType() != NodeType.DOCUMENT;
        StringToIntDictionary dict = isFallback ? mBookmarkIdsByNameFallback : mBookmarkIdsByName;

        if (dict.containsKey(bookmarkKey))
        {
            id = dict.get(bookmarkKey);
        }
        else
        {
            id = getNextAnnotationId();
            dict.add(bookmarkKey, id);
        }

        mBuilder.startElement("w:bookmarkStart");

        mBuilder.writeAttribute("w:id", id);
        // TestBookmarkEmptyName - Name should be written even if it is an empty string.
        mBuilder.writeAttributeString("w:name", bookmarkStart.getName());

        if (bookmarkStart.isColumn())
        {
            mBuilder.writeAttribute("w:colFirst", bookmarkStart.getFirstColumn());
            mBuilder.writeAttribute("w:colLast", bookmarkStart.getLastColumn());
        }

        writeDisplacedByCustomXmlAttribute(bookmarkStart);

        mBuilder.endElement("w:bookmarkStart");

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd)
    {
        String bookmarkKey = BookmarkValidatorItem.getKey(bookmarkEnd);

        // Use separate dictionary for bookmarks located in fallback.
        boolean isFallback = bookmarkEnd.getTopmostAncestor().getNodeType() != NodeType.DOCUMENT;
        StringToIntDictionary dict = isFallback ? mBookmarkIdsByNameFallback : mBookmarkIdsByName;

        int id = dict.get(bookmarkKey);
        if (StringToIntDictionary.isNullSubstitute(id))
            throw new IllegalStateException();

        mBuilder.startElement("w:bookmarkEnd");
        mBuilder.writeAttribute("w:id", id);

        writeDisplacedByCustomXmlAttribute(bookmarkEnd);

        mBuilder.endElement("w:bookmarkEnd");

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitEditableRangeStart(EditableRangeStart editableRangeStart)
    {
        mBuilder.startElement("w:permStart");

        mBuilder.writeAttribute("w:id", idToAnnotationId(editableRangeStart.getId()));

        writeDisplacedByCustomXmlAttribute(editableRangeStart);

        if (editableRangeStart.isColumn())
        {
            mBuilder.writeAttribute("w:colFirst", editableRangeStart.getFirstColumn());
            mBuilder.writeAttribute("w:colLast", editableRangeStart.getLastColumn());
        }

        mBuilder.writeAttribute("w:ed", editableRangeStart.getSingleUser());
        mBuilder.writeAttribute("w:edGrp", DocxDopEnum.editorTypeToDocx(editableRangeStart.getEditorGroup()));

        mBuilder.endElement("w:permStart");

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitEditableRangeEnd(EditableRangeEnd editableRangeEnd)
    {
        writeAnnotation(editableRangeEnd, "w:permEnd");
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment) throws Exception
    {
        if(!canBeWritten(comment))
            return VisitorAction.SKIP_THIS_NODE;

        int annotationId = idToAnnotationId(comment.getId());

        // Write comment reference.
        mRunWriter.writeRunStart(comment.getRunPr(), comment.getRunPr(), true); // MS Word seems to write rsids for comment references, so do we.

        writeElementWithId("w:commentReference", annotationId);
        mRunWriter.writeRunEnd(comment.getRunPr());

        if (mCommentsWriter == null)
            mCommentsWriter = new DocxCommentsWriter(this);

        mCommentsWriter.writeStart(comment, annotationId);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitCommentEnd(Comment comment)
    {
        mCommentsWriter.writeEnd();
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitCommentRangeStart(CommentRangeStart commentRangeStart)
    {
        writeAnnotation(commentRangeStart, "w:commentRangeStart");
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitCommentRangeEnd(CommentRangeEnd commentRangeEnd)
    {
        writeAnnotation(commentRangeEnd, "w:commentRangeEnd");
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    /*override*/ /*VisitorAction*/int visitMoveFromRangeStart(MoveRangeStart moveFromRangeStart)
    {
        mBuilder.startElement("w:moveFromRangeStart");
        writeMoveRangeStartCore(moveFromRangeStart);
        mBuilder.endElement("w:moveFromRangeStart");

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    /*override*/ /*VisitorAction*/int visitMoveFromRangeEnd(MoveRangeEnd moveFromRangeEnd)
    {
        writeAnnotation(moveFromRangeEnd, "w:moveFromRangeEnd");
        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    /*override*/ /*VisitorAction*/int visitMoveToRangeStart(MoveRangeStart moveToRangeStart)
    {
        mBuilder.startElement("w:moveToRangeStart");
        writeMoveRangeStartCore(moveToRangeStart);
        mBuilder.endElement("w:moveToRangeStart");

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    /*override*/ /*VisitorAction*/int visitMoveToRangeEnd(MoveRangeEnd moveToRangeEnd)
    {
        writeAnnotation(moveToRangeEnd, "w:moveToRangeEnd");
        return VisitorAction.CONTINUE;
    }

    private void writeMoveRangeStartCore(MoveRangeStart moveRangeStart)
    {
        mBuilder.writeAttribute("w:id", idToAnnotationId(moveRangeStart.getId()));
        mBuilder.writeAttribute("w:author", moveRangeStart.getAuthor());
        // MS Word writes range start date as is, without conversion to UTC.
        mBuilder.writeAttribute("w:date", moveRangeStart.getDate(), DateTimeKind.UNSPECIFIED);
        mBuilder.writeAttribute("w:name", moveRangeStart.getName());

        writeDisplacedByCustomXmlAttribute(moveRangeStart);

        if (moveRangeStart.isColumn())
        {
            mBuilder.writeAttribute("w:colFirst", moveRangeStart.getFirstColumn());
            mBuilder.writeAttribute("w:colLast", moveRangeStart.getLastColumn());
        }
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitSubDocument(SubDocument subDocument)
    {
        OpcPackagePart docPart = getPackage().getPartByRelationshipType(null, getRelTypes().getOfficeDocument());
        String relId = docPart.getRels().add(getRelTypes().getSubDocument(), UriUtil.escapeHref(subDocument.getFileName()), true);
        mBuilder.writeRelationshipId("w:subDoc", relId);

        return VisitorAction.CONTINUE;
    }

    private static boolean canBeWritten(Comment comment)
    {
        // WORDSNET-13825 Although comment as direct child of cell is supported in RTF, it cannot be written to docx.
        if (comment.getParentNode().getNodeType() == NodeType.CELL)
            return false;

        return true;
    }

    /// <summary>
    /// We map model comment/editable range ids into DOCX annotation ids when writing.
    /// </summary>
    private int idToAnnotationId(int objectId)
    {
        int annotationId = mIdToAnnotationId.get(objectId);
        if (!IntToIntDictionary.isNullSubstitute(annotationId))
            return annotationId;

        annotationId = getNextAnnotationId();
        mIdToAnnotationId.set(objectId, annotationId);
        return annotationId;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote) throws Exception
    {
        mRunWriter.writeRunStart(footnote.getRunPr(), footnote.getRunPr(), false);

        switch (footnote.getFootnoteType())
        {
            case FootnoteType.FOOTNOTE:
                mRunWriter.setCurrentFootnoteType(FootnoteType.FOOTNOTE);
                mFootnotesWriter.writeFootnoteStart();
                break;
            case FootnoteType.ENDNOTE:
                mRunWriter.setCurrentFootnoteType(FootnoteType.ENDNOTE);
                mEndnotesWriter.writeFootnoteStart();
                break;
            default:
                throw new IllegalStateException("Unknown footnote type.");
        }

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitFootnoteEnd(Footnote footnote) throws Exception
    {
        DocxFootnotesWriter writer;
        String elemName;

        switch (footnote.getFootnoteType())
        {
            case FootnoteType.FOOTNOTE:
                writer = mFootnotesWriter;
                elemName = "w:footnoteReference";
                break;
            case FootnoteType.ENDNOTE:
                writer = mEndnotesWriter;
                elemName = "w:endnoteReference";
                break;
            default:
                throw new IllegalStateException("Unknown footnote type.");
        }

        int id = writer.writeFootnoteEnd();

        // WORDSNET-4993 writing customMarkFollows attribute if required along with element id.
        mBuilder.startElement(elemName);
        mBuilder.writeAttributeIfTrue("w:customMarkFollows", !footnote.isAuto());
        mBuilder.writeAttribute("w:id", id);
        mBuilder.endElement(elemName);

        if (!footnote.isAuto())
            writeReferenceMark(mBuilder, footnote);

        mRunWriter.writeRunEnd(footnote.getRunPr());
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// WORDSNET-4993, 5323, 5324, 12842
    /// Writing reference mark
    /// </summary>
    static void writeReferenceMark(NrxXmlBuilder builder, Footnote footnote)
    {
        boolean isWritten = false;

        if (footnote.getReferenceMark().length() == 1)
        {
            char refMark = footnote.getReferenceMark().charAt(0);
            if (refMark == ControlChar.LINE_BREAK_CHAR)
            {
                isWritten = true;
                builder.writeElement("w:br", footnote.getReferenceMark());
            }
            if (refMark == ControlChar.TAB_CHAR)
            {
                isWritten = true;
                builder.writeEmptyElement("w:tab");
            }
            else if (StringUtil.isPrivateUseCategory(refMark))
            {
                isWritten = true;
                builder.writeElementWithAttributes(
                    "w:sym",
                    "w:font", footnote.getRunPr().getNameAscii(),
                    "w:char", FormatterPal.intToStrX4(refMark));
            }
        }

        if (!isWritten)
        {
            String elementName = "w:t";

            if (footnote.isDeleteRevision())
                elementName = "w:delText";
            else if (footnote.isInsertRevision())
                elementName = "w:instrText";

            // WORDSNET-5858 We should specify xml:space="preserve", to preserve space char in footnote's referenceMark.
            builder.writeElementWithAttributesStart(elementName, "xml:space", "preserve");
            builder.writeString(footnote.getReferenceMark());
            builder.endElement();
        }
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitGroupShapeStart(GroupShape groupShape) throws Exception
    {
        if (groupShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLStart(groupShape);

        if (groupShape.isTopLevel())
            writeTopLevelShapeStart(groupShape);

        VmlShapeWriter.write(groupShape, mBuilder, this);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitGroupShapeEnd(GroupShape groupShape) throws Exception
    {
        if (groupShape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLEnd(groupShape);

        VmlShapeWriter.writeWrapAndAnchorLock(groupShape, mBuilder);
        mBuilder.endElement("v:group");

        if (groupShape.isTopLevel())
            writeTopLevelShapeEnd(groupShape);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape) throws Exception
    {
        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
        {
            Shape dmlShape = shape;
            if ((!shape.hasHyperlink()) && mFieldsWriter.isInsideField() && mFieldsWriter.isHyperlinkLastChild(shape))
                dmlShape = mFieldsWriter.getHyperlinkShape(shape);
            return visitDrawingMLStart(dmlShape);
        }

        // WORDSNET-19075 Write as DML only top level images
        boolean isNeedConvertToDml = mDoc.getDocPr().CompatibilityOptions.isNeedConvertToNewerVersion();
        // WORDSNET-11815 Do not convert shapes to DML when used Ecma376.
        isNeedConvertToDml &= getCompliance() != OoxmlComplianceCore.ECMA_376;

        // FOSS

        // If the shape is a hyperlinked inline image, then we need to enclose it inside "w:hyperlink" declaration.
        if (shape.isHyperlinkedInline())
            mFieldsWriter.writeHyperlinkStart(shape);

        if (shape.isTopLevel())
            writeTopLevelShapeStart(shape);

        VmlShapeWriter.write(shape, mBuilder, this);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    public /*override*/ /*VisitorAction*/int visitShapeEnd(Shape shape) throws Exception
    {
        if (mDmlsConvertedFromVmls.getCount() > 0)
        {
            Shape dml = mDmlsConvertedFromVmls.pop();
            return visitDrawingMLEnd(dml);
        }

        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLEnd(shape);

        // If the shape has child nodes, they are written inside 'v:textbox', which is a child element of 'v:shape'.
        // 'v:textbox' and 'w:txbxContent' elements start is written inside VmlShapeWriter in this case.
        // That's why you don't see starting of these elements here in WmlWriter.
        if (shape.hasChildNodes())
        {
            mBuilder.endElement("w:txbxContent");
            mBuilder.endElement("v:textbox");
        }

        // shape element is NOT written for OLE controls that are inline.
        VmlShapeWriter.writeWrapAndAnchorLock(shape, mBuilder);
        mBuilder.endElement();    // "v:shape" or "v:oval", "v:rect" etc.
        if(shape.isOle())
            writeOle(shape);

        if (shape.isTopLevel())
            writeTopLevelShapeEnd(shape);

        if (shape.isHyperlinkedInline())
            mFieldsWriter.writeHyperlinkEnd();

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    private /*VisitorAction*/int visitDrawingMLStart(ShapeBase drawingML) throws Exception
    {
        Debug.assertTrue(drawingML != null);

        // Skip nested DrawingML nodes in charts, they are written as user shapes in a special way.
        // Chart can not contain nested shapes according to 5.7.2.26 part (Reference to Chart Part).
        // Keep DML fallbacks for chartEx shapes.
        if ((mCurrentDmlChart != null) &&
            !((drawingML == mCurrentDmlChart.getFallbackShape()) && DmlUtil.needDmlFallback(mCurrentDmlChart)))
            return VisitorAction.SKIP_THIS_NODE;

        // Set current context, i.e. currently DML chart is in process.
        if ((drawingML.getDmlNode().getDmlNodeType() == DmlNodeType.CHART) ||
            (drawingML.getDmlNode().getDmlNodeType() == DmlNodeType.CHART_EX))
            mCurrentDmlChart = drawingML;

        if (drawingML.isTopLevel())
        {
            // FOSS We can't generate a fallback shape (DML->VML conversion needs rendering),
            // so only wrap in mc:AlternateContent when there is an existing FallbackShape to preserve.
            if (DmlUtil.needFallback(drawingML, getCompliance()) && drawingML.getFallbackShape() != null)
            {
                mRunWriter.writeRunStart(drawingML.getRunPr(), drawingML, true);
                startAlternateContent(drawingML);
            }
            else if (mFallbackDeeps == 0) // We do not need to open new run if it is already opened for AlternateContent.
            {
                mRunWriter.writeRunStart(drawingML.getRunPr(), drawingML, true);
            }
        }

        DmlDrawingWriter.writeStart(drawingML, this);

        return VisitorAction.CONTINUE;
    }

    @ReservedForInternalUse @Deprecated
    private /*VisitorAction*/int visitDrawingMLEnd(ShapeBase drawingML) throws Exception
    {
        Debug.assertTrue(drawingML != null);

        DmlDrawingWriter.writeEnd(drawingML, this);

        if (drawingML.isTopLevel())
        {
            // FOSS Mirror of the gate in VisitDrawingMLStart.
            if (DmlUtil.needFallback(drawingML, getCompliance()) && drawingML.getFallbackShape() != null)
            {
                endAlternateContent(drawingML);
                mRunWriter.writeRunEnd(drawingML.getRunPr());
            }
            else if (mFallbackDeeps == 0)
            {
                mRunWriter.writeRunEnd(drawingML.getRunPr());
            }
        }

        // Reset context.
        if ((drawingML.getDmlNode().getDmlNodeType() == DmlNodeType.CHART) ||
            (drawingML.getDmlNode().getDmlNodeType() == DmlNodeType.CHART_EX))
            mCurrentDmlChart = null;

        return VisitorAction.CONTINUE;
    }

    void startAlternateContent(ShapeBase shape)
    {
        mBuilder.startElement("mc:AlternateContent");
        mBuilder.startElement("mc:Choice");
        mBuilder.writeAttribute("Requires", getChoiceRequires(shape));
    }

    void endAlternateContent(ShapeBase shape) throws Exception
    {
        mBuilder.endElement("mc:Choice");
        mBuilder.startElement("mc:Fallback");
        mFallbackDeeps++;

        // Try getting alternate content read from the document.
        if (shape.getFallbackShape() != null)
        {
            shape.getFallbackShape().accept(this);
        }
        else
        {
            // FOSS
        }

        mBuilder.endElement("mc:Fallback");
        mFallbackDeeps--;

        mBuilder.endElement("mc:AlternateContent");
    }

    /// <summary>
    /// Writes necessary elements for Ole controls and objects.
    /// </summary>
    private void writeOle(Shape shape) throws Exception
    {
        Debug.assertTrue(shape.isOle());

        switch (shape.getShapeType())
        {
            case ShapeType.OLE_OBJECT:
                writeOleObject(shape);
                break;
            case ShapeType.OLE_CONTROL:
                writeOleControl(shape);
                break;
            default:
                // Do nothing.
                break;
        }
    }

    /// <summary>
    /// Writes 'o:OLEObject' element for the specified shape.
    /// </summary>
    private void writeOleObject(Shape shape) throws Exception
    {
        Debug.assertTrue(shape.isOleObject());

        //    <o:OLEObject Type="Embed" ProgID="Equation.3" ShapeID="_x0000_i1025" DrawAspect="Content" ObjectID="_1254857050" r:id="rId5" />

        OleFormat oleFormat = shape.getOleFormat();

        mBuilder.startElement("o:OLEObject");

        mBuilder.writeAttribute("Type", oleFormat.getNeedEmbeddedPart() ? "Embed" : "Link");
        mBuilder.writeAttribute("ProgID", oleFormat.getProgId());
        mBuilder.writeAttribute("ShapeID", VmlUtil.getOleObjectShapeId(shape));
        mBuilder.writeAttribute("DrawAspect", oleFormat.getOleIcon() ? "Icon" : "Content");

        if (!oleFormat.getNeedEmbeddedPart())
        {
            // Write a link to the object.
            String moniker = StringUtil.hasChars(oleFormat.getSourceItem())
                ? msString.format("{0}!{1}", oleFormat.getSourceFullName(), oleFormat.getSourceItem())
                : oleFormat.getSourceFullName();

            String relId = mBuilder.getPart().getRels().add(getRelTypes().getOleObject(), moniker, true);
            mBuilder.writeAttribute("r:id", relId);

            mBuilder.writeAttribute("UpdateMode", oleFormat.getAutoUpdate() ? "Always" : "OnCall");

            mBuilder.writeElement("o:LinkType", VmlEnum.oleLinkTypeToVml(oleFormat.getOleLinkType()));

            mBuilder.writeValIfTrue("o:LockedField", oleFormat.isLocked());

            // RK Note in WordML this is o:WordFieldCodes
            mBuilder.writeElement("o:FieldCodes", "\\f " + oleFormat.getFormatUpdateType());
        }
        else
        {
            // Write an embedded OLE or OOXML object.
            mBuilder.writeAttribute("ObjectID", OleUtil.getMsWordId(oleFormat.getEmbeddedObject()));

            if (oleFormat.getOleObject() != null)
                writeEmbeddedOleObject(oleFormat);
            else if (oleFormat.getOoxmlObject() != null)
                writeEmbeddedOoxmlObject(oleFormat);
        }

        mBuilder.endElement("o:OLEObject");
    }

    /// <summary>
    /// Writes an embedded object as an OLE object.
    /// </summary>
    private void writeEmbeddedOleObject(OleFormat oleFormat) throws Exception
    {
        OpcPackagePart part = createEmbeddedObjectPart(oleFormat, "oleObject", getRelTypes().getOleObject());
        FileSystem fs = new FileSystem(oleFormat.getOleObject().getData());
        fs.save(part.getStream());
    }

    /// <summary>
    /// Writes an embedded object as an OOXML package.
    /// </summary>
    private void writeEmbeddedOoxmlObject(OleFormat oleFormat) throws Exception
    {
        OpcPackagePart part = createEmbeddedObjectPart(oleFormat, "ooxmlPackage", getRelTypes().getPackage());

        // andrnosk: WORDSNET-7678 We have to copy the stream before giving it to the part,
        // to avoid referencing two or more parts to the same stream (for example upon appending documents).
        oleFormat.getOoxmlObject().getStream().setPosition(0);
        StreamUtil.copyStream(oleFormat.getOoxmlObject().getStream(), part.getStream());
        part.getStream().setPosition(0);
    }


    /// <summary>
    /// Gets the content type valid for saving into an OOXML document.
    /// </summary>
    private static String getContentType(OleFormat oleFormat)
    {
        OoxmlObject ooxmlObject = oleFormat.getOoxmlObject();
        if ((ooxmlObject != null) && StringUtil.hasChars(ooxmlObject.getContentType()))
            return ooxmlObject.getContentType();

        return OleUtil.getContentType(oleFormat.getProgId());
    }

    private OpcPackagePart createEmbeddedObjectPart(OleFormat oleFormat, String fileNameBase, String relType) throws Exception
    {
        String contentType = getContentType(oleFormat);

        // OLE objects for both main document and glossary are stored in the same hardcoded place.
        String partName = msString.format("/word/embeddings/{0}{1}{2}",
            fileNameBase, getNextEmbeddedPartNumber(relType), DocxEnum.contentTypeToExtension(contentType));

        String relId = null;
        Ref<String> referenceToRelId = new Ref<String>(relId);
        OpcPackagePart part = getPackage().createChildPart(mBuilder.getPart(), partName, contentType, relType, /*out*/ referenceToRelId);
        relId = referenceToRelId.get();
        mBuilder.writeAttribute("r:id", relId);

        return part;
    }

    /// <summary>
    /// Writes 'w:control' element for the specified shape.
    /// </summary>
    private void writeOleControl(Shape shape) throws Exception
    {
        Debug.assertTrue(shape.isOleControl());
        Debug.assertTrue(shape.getEmbeddedObject() != null);

        mBuilder.startElement("w:control");

        int partNumber = getNextEmbeddedPartNumber(getRelTypes().getControl());

        String xmlRelId = null;
        Ref<String> referenceToXmlRelId = new Ref<String>(xmlRelId);
        OpcPackagePart xmlPart = getPackage().createChildPart(
            mBuilder.getPart(), msString.format("/word/activeX/activeX{0}.xml", partNumber), DocxContentType.CONTROL, getRelTypes().getControl(), /*out*/ referenceToXmlRelId);
        xmlRelId = referenceToXmlRelId.get();

        // It can be either OOXmlObject or OleObject, or one of the its parsed versions of OleControl type.
        // See OleControl.Create() for details.
        String name = shape.isOoxmlControl() ? writeOoxmlControl(shape, xmlPart) : writeBinaryControl(shape, xmlPart, partNumber);

        // Write all attributes and close tag.
        mBuilder.writeAttribute("r:id", xmlRelId);
        mBuilder.writeAttributeString("w:name", name);
        mBuilder.writeAttributeString("w:shapeid", NrxXmlUtil.getShapeId(shape));
        mBuilder.endElement("w:control");
    }

    /// <summary>
    /// Writes control represented as OoxmlObject.
    /// </summary>
    /// <returns>Name of written control.</returns>
    private static String writeOoxmlControl(ShapeBase shape, OpcPackagePart xmlPart) throws Exception
    {
        OoxmlObject ooxmlObject = ms.as(shape.getEmbeddedObject(), OoxmlObject.class);
        Debug.assertTrue(ooxmlObject != null);

        ooxmlObject.getStream().setPosition(0);
        StreamUtil.copyStream(ooxmlObject.getStream(), xmlPart.getStream());

        return ((IEmbeddedObject)ooxmlObject).getName();
    }

    /// <summary>
    /// Writes ActiveX control represented as OleObject.
    /// </summary>
    /// <returns>Name of written control.</returns>
    private String writeBinaryControl(ShapeBase shape, OpcPackagePart xmlPart, int partNumber) throws Exception
    {
        IEmbeddedObject embeddedObject = shape.getEmbeddedObject();
        Debug.assertTrue(embeddedObject != null);
        OleObject oleObject = embeddedObject.getOleObject();
        Debug.assertTrue(oleObject != null);

        FileSystem fs = new FileSystem(oleObject.getData());

        xmlPart.getRels().add("rId1", getRelTypes().getControlBinary(),
                         msString.format("activeX{0}.bin", partNumber), false);

        // XML control part is always the same so I decided to write it from constant string.
        String xml = msString.format(CONTROL_XML_PART_CONTENT, fs.getRoot().getClsid().toString().toUpperCase(),
                                   getDocxNamespaces().getActiveX(), getDocxNamespaces().getRelationships());
        byte[] xmlBytes = Encoding.getUTF8().getBytes(xml);
        xmlPart.getStream().write(xmlBytes, 0, xmlBytes.length);

        OpcPackagePart binPart = getPackage().createChildPart(
            xmlPart, msString.format("/word/activeX/activeX{0}.bin", partNumber), DocxContentType.CONTROL_BINARY,
            getRelTypes().getControlBinary());

        fs.save(binPart.getStream());
        binPart.getStream().setPosition(0);

        // WORDSNET-11823 If name of shape was explicitly set, we should use it as the name for this OLE control.
        String name = (StringUtil.hasChars(shape.getName())) ? shape.getName(): embeddedObject.getName();

        // WORDSNET-18020 Generate default name of the OLE control if it is not specified.
        return (StringUtil.hasChars(name)) ? name : msString.format("OleControl {0}", shape.getId());
    }

    /// <summary>
    /// Writes the 'w:pPr' element.
    /// </summary>
    /// <param name="para">Paragraph to write properties for.</param>
    private void writeParaPr(Paragraph para) throws Exception
    {
        // TODO 6139. See "[MS-OI29500] 2.1.242 Part 1 Section 17.7.5.2, pPr (Paragraph Properties)".
        // The standard states that the cnfStyle, divId, pPrChange, pStyle, rPr, and sectPr elements are valid children of the pPr element.
        // Word does not allow these elements to be children of the pPr element.
        ParaPr paraPr = para.getParaPr();

        // The logic involving pPrStartWritten is implemented to avoid writing empty w:pPr elements.
        boolean pPrStartWritten = NrxParaPrWriter.writeStart(paraPr, this);

        // if w:pPr start element is not written yet - write it from RunPrWriter.
        // We pass !pPrStartWritten parameter to control that.
        boolean rPrWritten = DocxRunPrWriter.getInstance().writeForNodes(para.getParagraphBreakRunPr(), para, !pPrStartWritten, this);

        // If rPr was written inside mRunWriter.WriteProps, then we can be sure that pPr is already written too.
        pPrStartWritten = pPrStartWritten || rPrWritten;

        // if the paragraph is a last paragraph of non last section
        if (para == mLastParagraphOfCurrentSection && !mCurrentSection.isLastChild())
        {
            // If w:pPr start element was not written yet, then we should write it here to enclose w:sectPr element.
            if (!pPrStartWritten)
            {
                mBuilder.startElement("w:pPr");
                pPrStartWritten = true;
            }

            // In WordML section properties are written inside paragraph properties (w:pPr element) of the last paragraph of a section.
            // However, section properties of the last section of the document are written on document body level (w:body).
            NrxSectPrWriter.write(mCurrentSection, this);
        }

        // if w:pPr start element was written, write end element for it here.
        if (pPrStartWritten)
            mBuilder.endElement("w:pPr");
    }

    private void writeTopLevelShapeStart(ShapeBase shape) throws Exception
    {
        if (needFallbackForVml(shape))
        {
            mRunWriter.writeRunStart(shape.getRunPr(), shape, true);
            startAlternateContent(shape);
        }
        else if (mFallbackDeeps == 0) // We do not need to open new run if it is already opened for AlternateContent.
        {
            mRunWriter.writeRunStart(shape.getRunPr(), shape, false);
        }

        // RK This is DOCX specific. Inline OLE objects are written as w:object,
        // but all other shapes are written as w:pict.
        if (shape.isInline() && (shape.isOleControl() || shape.isOleObject()))
        {
            mBuilder.startElement("w:object");
            // RK There are w:dxaOrig and w:dyaOrig attributes that we don't write here.
            // They seem to be optional so we can live without.
            // If we really need them, they must be taked from Picf.dxaGoal and dyaGoal.
        }
        else
        {
            mBuilder.startElement("w:pict");
        }
    }

    private void writeTopLevelShapeEnd(ShapeBase shape) throws Exception
    {
        mBuilder.endElement();    //w:pict or w:object
        if (needFallbackForVml(shape))
        {
            endAlternateContent(shape);
            mRunWriter.writeRunEnd(shape.getRunPr());
        }
        else if (mFallbackDeeps == 0)
        {
            mRunWriter.writeRunEnd(shape.getRunPr());
        }
    }

    /// <summary>
    /// Returns true if VML shape should be written as alternate content block.
    /// </summary>
    private boolean needFallbackForVml(ShapeBase shape)
    {
        return shape.isTopLevel() && (shape.getFallbackShape() != null) && (getCompliance() == OoxmlComplianceCore.ISO_STRICT);
    }

    /// <summary>
    /// Returns value of the "Requires" attribute of the "Choice" element for specified shape.
    /// </summary>
    /// <param name="dml">Shape to obtain required value.</param>
    /// <returns>Requested value of the attribute.</returns>
    /// <remarks>
    /// This method called to obtain "Requires" attribute when fallback shape is needed.
    /// MS Word always writes fallback shape for Wordprocessing Shape, Group and Canvas - so we do the same.
    /// Graphic frame is not used as top level shape, so this method does not use frame value.
    /// </remarks>
    static String getChoiceRequires(ShapeBase dml)
    {
        Debug.assertTrue(dml != null);

        if (dml.getMarkupLanguage() == ShapeMarkupLanguage.VML)
            return "v";

        /*DmlNodeType*/int dmlType = dml.getDmlNode().getDmlNodeType();

        switch (dmlType)
        {
            case DmlNodeType.WORDPROCESSING_CANVAS:
                return "wpc";
            case DmlNodeType.WORDPROCESSING_GROUP_SHAPE:
                return "wpg";
            case DmlNodeType.WORDPROCESSING_SHAPE:
                return "wps";
            case DmlNodeType.CONTENT_PART:
                return "wpi";
            case DmlNodeType.CHART_EX:
                return "cx1";
            default:
                throw new IllegalArgumentException("Unexpected DrawingML type.");
        }
    }

    /// <summary>
    /// IVmlShapeWriterContext, IDmlShapeWriterContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String writeImageBinData(byte[] imageBytes) throws Exception
    {
        String imagePartName = mWriter.getImagePartName(imageBytes);
        if (!StringUtil.hasChars(imagePartName))
        {
            /*FileFormat*/int imageType = ImageUtil.getImageType(imageBytes);

            // WORDSNET-13400 Try DIB image.
            if ((imageType == FileFormat.UNKNOWN) && ImageUtil.isDib(imageBytes))
            {
                BinaryReader reader = new BinaryReader(new MemoryStream(imageBytes));
                imageBytes = ImageUtil.prependBmpHeader(reader, imageBytes.length);
                imageType = FileFormat.BMP;
            }
            else if (imageType == FileFormat.PICT)
            {
                imageBytes = VmlUtil.packPictImage(imageBytes);
            }

            // RK This is by design. Images for both main document and glossary are stored in one place.
            String partFileNameWithoutExt = "/word/media/image" + getNextEmbeddedPartNumber(getRelTypes().getImage());
            OpcPackagePart part = createImagePart(imageType, imageBytes, partFileNameWithoutExt);
            getPackage().getParts().add(part);

            imagePartName = part.getName();
            mWriter.addImageBytes(imageBytes, imagePartName);
        }

        return mBuilder.getPart().getRels().add(getRelTypes().getImage(), imagePartName, false);
    }

    static OpcPackagePart createImagePart(/*FileFormat*/int imageType, byte[] bytesToWrite,
        String partFileNameWithoutExt) throws Exception
    {
        String imageContentType = FileFormatCore.toContentType(imageType);

        // Placed here to avoid write unknown content type for other than image document parts.
        if (!StringUtil.hasChars(imageContentType))
            imageContentType = "image/unknown";

        String imageExt = FileFormatCore.toExt(imageType);
        if (!StringUtil.hasChars(imageExt))
            imageExt = "bin";

        switch (imageType)
        {
            case FileFormat.BMP:
            case FileFormat.GIF:
            case FileFormat.PNG:
            case FileFormat.JPEG:
            case FileFormat.EMF:
            case FileFormat.WMF:

            // WORDSNET-21107 Implement WebP image format rendering
            case FileFormat.WEB_P:

            // WORDSNET-19122 Added support for Ico format.
            case FileFormat.ICO:

            // WORDSNET-21677 The problem occurred because we did not handle tiff images.
            // Fixed by adding a case for Tiff format.
            case FileFormat.TIFF:

            // WORDSNET-16164 Added support for svgBlip drawing extension.
            case FileFormat.SVG:

            // andrnosk: WORDSNET-5024 (part 1) There is PDF in media in the DOCX document. To preserve it upon
            // roundtrip process it as an media data. Since we do not know what formats can also appear in media,
            // currently process only PDFs, otherwise throw an exception.
            // WORDSNET-8002 Added QuickTime MOV to be processed as media data too.
            case FileFormat.PDF:
            case FileFormat.MOV:
            // WORDSNET-15267 Allow image with unknown format to be written.
            case FileFormat.UNKNOWN:
                break;

            case FileFormat.PICT:
                imageExt = "pcz";
                imageContentType = DocxContentType.IMAGE_PICT_COMPRESSED;
                break;

            default:
                throw new IllegalStateException("Unrecognized image type encountered during DOCX export.");
        }

        String imagePartName = partFileNameWithoutExt + "." + imageExt;

        OpcPackagePart part = new OpcPackagePart(imagePartName, imageContentType);
        part.getStream().write(bytesToWrite, 0, bytesToWrite.length);

        return part;
    }

    /// <summary>
    /// IVmlShapeWriterContext, IDmlShapeWriterContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String writeImageLink(String link)
    {
        // andrnosk: WORDSNET-7681 There is unescaped char in the link, we should escape this before writing to rels.
        return mBuilder.getPart().getRels().add(getRelTypes().getImage(), UriUtil.escapeHref(link), true);
    }

    /// <summary>
    /// Converts the specified <see cref="DrColor"/> to VML color string.
    /// </summary>
    /// <remarks>
    /// Logs a warning to the user-provided warning callback if this is a special color definition (system or scheme
    /// color or palette index) that is currently not supported.
    /// </remarks>
    public String colorToVml(DrColor color)
    {
        String colorString = VmlColor.colorToVml(color);
        if (colorString == null)
        {
            // Restore original 'A' value that was inverted in WordColorUtil.WordXPColorToColor.
            DrColor originalColor = new DrColor(~color.getA() & 0xff, color);
            String colorCode = FormatterPal.intToStrX8(originalColor.toArgb());
            String message = msString.format(WarningStrings.UNSUPPORTED_VML_COLOR, "DOCX", "0x" + colorCode);

            warn(WarningType.MAJOR_FORMATTING_LOSS, message);
        }

        return colorString;
    }

    /// <summary>
    /// Gets diagram member name by its hashcode.
    /// MS Word stores hashcodes, not names, in diagram relation tables. And in WordML we need to write names.
    /// </summary>
    /// <param name="key">Hashcode of the diagram member.</param>
    /// <returns>Name of the diagram member.</returns>
    public String /*IVmlShapeWriterContext.*/getDiagramMemberName(int key)
    {
        if (mDiagramReferences == null)
        {
            mDiagramReferences = new IntToObjDictionary<String>();

            for (ShapeBase shape : (Iterable<ShapeBase>) getDocument().getChildNodes(NodeType.SHAPE, true))
            {
                String name = shape.getName();
                if (msString.startsWith(name, "_s", StringComparison.ORDINAL))
                {
                    // WORDSNET-7710 The problem occurred because there are two shapes with the same name.
                    // Fixed by checking if name of the shape is already added to DiagramReferences.
                    // The same code is used in WmlContext class. Consider refactoring.
                    mDiagramReferences.set(VmlUtil.calculateDiagramHash(name), name);
                }
            }
        }

        return mDiagramReferences.get(key);
    }

    public String /*IVmlShapeWriterContext.*/getImageSrcAttributeName() { return "r:id"; }

    public String /*IVmlShapeWriterContext.*/getImageHrefAttributeName() { return "r:href"; }

    /// <summary>
    /// Keeps record of shapetype definitions already written to output document.
    /// </summary>
    public ArrayList<String> /*IVmlShapeWriterContext.*/getShapeTypesWritten() { return mBuilder.getShapeTypesWritten(); }

    /// <summary>
    /// IVmlShapeWriterContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public SaveInfo getSaveInfo() { return mWriter.getSaveInfo(); }

    /// <summary>
    /// INrxWriterContext
    ///
    /// Gets the WordML-compliant style identifier for the specified style's istd.
    /// Use for 'w:styleId', 'w:basedOn', 'w:next', 'w:link', 'w:pStyle' and 'w:rStyle' attributes.
    /// And also in 'w:styleLink' and 'w:listStyleLink' elements in list definitions.
    ///
    /// This value is for XML cross-reference only.
    /// It should be unique and be compliant with xml specification for id fields.
    /// Means it can include only english letters, digits or '-' sign. No other characters are allowed.
    /// Otherwise, xsl processors won't be able to handle the produced WordML file correctly.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String getStyleId(int istd)
    {
        return mStyleIdGenerator.getStyleId(istd);
    }

    /// <summary>
    /// INrxWriterContext, IMathWriterContext, IDmlShapeWriterContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public NrxXmlBuilder getBuilder() { return mBuilder; }

    /// <summary>
    /// INrxWriterContext, IMathWriterContext, IVmlShapeWriterContext
    /// </summary>
    public boolean isDocx() { return true; }

    /// <summary>
    /// INrxWriterContext, IMathWriterContext, IDmlShapeWriterContext. Based on the value of this data member we will
    /// write different attributes, elements into the document.
    /// </summary>
    public /*OoxmlComplianceCore*/int getCompliance() { return OoxmlComplianceInfo.getCompliance(mWriter.getDocument().getComplianceInfo(), getSaveOptions()); }

    /// <summary>
    /// INrxWriterContext, IMathWriterContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public DocumentBase getDocument() { return mDoc; }

    public void /*INrxWriterContext.*/writeHeaderFooter(HeaderFooter headerFooter) throws Exception
    {
        // WORDSNET-18748 By default, sections other than the first section inherit the previous header and footer
        // references, unless that section specifies header and footer references
        if (!headerFooter.isLinkedToPrevious())
        {
            String partName;
            String contentType;
            String rootName;
            String refName;

            if (headerFooter.isHeader())
            {
                partName = msString.format("header{0}.xml", getNextHeaderNumber());
                contentType = DocxContentType.HEADER;
                rootName = "w:hdr";
                refName = "w:headerReference";
            }
            else
            {
                partName = msString.format("footer{0}.xml", getNextFooterNumber());
                contentType = DocxContentType.FOOTER;
                rootName = "w:ftr";
                refName = "w:footerReference";
            }

            String relType = headerFooter.isHeader() ? getRelTypes().getHeader() : getRelTypes().getFooter();
            String relId = null;
            Ref<String> referenceToRelId = new Ref<String>(relId);
            DocxBuilder builder = createChildPartAndBuilder(partName, contentType, relType, /*out*/ referenceToRelId);
            relId = referenceToRelId.get();
            builder.startDocumentWithStandardNamespaces(rootName);

            // Accept visitor for the header/footer builder.
            pushBuilder(builder);
            headerFooter.accept(this);
            popBuilder();

            builder.endDocument();

            // Write the relationship source into the main document part.
            NrxXmlBuilder mainDocumentBuilder = getCurrentBuilder();
            mainDocumentBuilder.startElement(refName);
            mainDocumentBuilder.writeAttributeString("w:type", NrxSectEnum.headerFooterTypeToXml(headerFooter.getHeaderFooterType(), true));
            mainDocumentBuilder.writeAttributeString("r:id", relId);
            mainDocumentBuilder.endElement();
        }
    }

    /// <summary>
    /// INrxWriterContext
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public void writeFootnotePr(AttrCollection attrs, boolean writeSeparators) throws Exception
    {
        mFootnotesWriter.writeFootnotePr(attrs, writeSeparators);
        mEndnotesWriter.writeFootnotePr(attrs, writeSeparators);
    }

    @ReservedForInternalUse @Deprecated
    public void writeW14Attributes(INrxWriterContext writer, RunPr runPr) throws Exception
    {
        Debug.assertTrue(writer.isDocx());

        DocxDocumentWriterBase docxWriter = (DocxDocumentWriterBase)writer;

        // Do not write these attrs if customer requests for ECMA386 document.
        if (docxWriter.getCompliance() == OoxmlComplianceCore.ECMA_376)
            return;

        NrxXmlBuilder builder = writer.getBuilder();

        Object value = runPr.getDirectAttr(FontAttr.EFFECT_GLOW);
        if (!isNullDmlEffect(value))
            DmlShapeEffectsWriter.writeGlow((DmlShapeGlowEffect)value, docxWriter, true);

        value = runPr.getDirectAttr(FontAttr.EFFECT_SHADOW);
        if (!isNullDmlEffect(value))
            DmlShapeEffectsWriter.writeOuterShadow((DmlShapeOuterShadowEffect)value, docxWriter, true);

        value = runPr.getDirectAttr(FontAttr.EFFECT_REFLECTION);
        if (!isNullDmlEffect(value))
            DmlShapeEffectsWriter.writeReflection((DmlShapeReflectionEffect)value, docxWriter, true);

        value = runPr.getDirectAttr(FontAttr.EFFECT_OUTLINE);
        if (!isNullDmlEffect(value))
            DmlOutlineWriter.write("w14:textOutline", (DmlOutline)value, docxWriter);

        value = runPr.getDirectAttr(FontAttr.EFFECT_FILL);
        if (!isNullDmlEffect(value))
        {
            builder.startElement("w14:textFill");
            DmlFillWriter.write((DmlFill)value, docxWriter, true);
            builder.endElement("w14:textFill");
        }

        value = runPr.getDirectAttr(FontAttr.EFFECT_SCENE_3_D);
        if (!isNullDmlEffect(value))
            Dml3DPropertiesWriter.writeScene3D((DmlScene3DProperties)value, docxWriter, true);

        value = runPr.getDirectAttr(FontAttr.EFFECT_PROPS_3_D);
        if (!isNullDmlEffect(value))
            Dml3DPropertiesWriter.writeShape3D((DmlShape3DProperties)value, docxWriter, true);

        value = runPr.getDirectAttr(FontAttr.OPEN_TYPE_LIGATURE);
        if (value != null)
            builder.writeVal("w14:ligatures", DocxEnum.ligatureToXml((/*Ligature*/Integer)value));

        value = runPr.getDirectAttr(FontAttr.OPEN_TYPE_NUM_FORM);
        if (value != null)
            builder.writeVal("w14:numForm", DocxEnum.numFormToXml((/*NumForm*/Integer)value));

        value = runPr.getDirectAttr(FontAttr.OPEN_TYPE_NUM_SPACING);
        if (value != null)
            builder.writeVal("w14:numSpacing", DocxEnum.numSpacingToXml((/*NumSpacing*/Integer)value));

        value = runPr.getDirectAttr(FontAttr.OPEN_TYPE_STYLISTIC_SETS);
        if (value != null)
            writeStylisticSets(builder, (/*StylisticSets*/Integer)value);

        if (runPr.contains(FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES))
            builder.writeBoolValExplicit("w14:cntxtAlts", (/*boolean*/Boolean)runPr.getDirectAttr(FontAttr.OPEN_TYPE_CONTEXTUAL_ALTERNATES));
    }

    public String getCurrentParagraphListLabelString() { return null; }

    public String getCurrentParagraphListLabelFontName() { return null; }

    public void /*IMathWriterContext.*/writeRunPr(RunPr runPr) throws Exception
    {
        DocxRunPrWriter.getInstance().writeForNodes(runPr, null, false, this);
    }

    /// <summary>
    /// Writes an element of the specified name that has only the w:id attribute.
    /// Used for comments, footnotes and endnotes.
    /// </summary>
    /// <param name="elemName">String, containing the name of the element to write.</param>
    /// <param name="id">Reference id.</param>
    void writeElementWithId(String elemName, int id)
    {
        mBuilder.startElement(elemName);
        mBuilder.writeAttribute("w:id", id);
        mBuilder.endElement(elemName);
    }

    /// <summary>
    /// Check attribute with DmlEffect to null value.
    /// </summary>
    /// <param name="attr">Attribute to check.</param>
    /// <returns>true if the attribute is null.</returns>
    private static boolean isNullDmlEffect(Object attr)
    {
        return (attr == null) || (attr instanceof DmlNullEffect);
    }

    private int getNextHeaderNumber()
    {
        mHeaderNumber++;
        return mHeaderNumber;
    }

    private int getNextFooterNumber()
    {
        mFooterNumber++;
        return mFooterNumber;
    }

    /// <summary>
    /// Gets a next unique value for writing as w:id of annotation elements in DOCX/WordML
    /// (comments, bookmarks and revision marks, etc).
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public int getNextAnnotationId()
    {
        return mAnnotationId++;
    }

    /// <summary>
    /// Generated names for embedded parts are sequentially numbered. There is one sequence per relationship type.
    /// One sequence goes across the main and glossary documents.
    /// </summary>
    int getNextEmbeddedPartNumber(String relType)
    {
        return mWriter.getNextEmbeddedPartNumber(relType);
    }

    /// <summary>
    /// Adds a hyperlink relationship to the current part and returns the id.
    /// </summary>
    public String addHyperlinkRelationship(String dest)
    {
        return mBuilder.getPart().getRels().add(getRelTypes().getHyperlink(), dest, !UriUtil.isSubAddressOnly(dest));
    }

    /// <summary>
    /// Adds a external relationship to the ole object to the current part and returns the id.
    /// </summary>
    String addExternalOleObjectRelationship(String dest)
    {
        return mBuilder.getPart().getRels().add(getRelTypes().getOleObject(), dest, true);
    }

    /// <summary>
    /// Creates a part that is a child to the document part and creates an XML builder for the new part.
    /// </summary>
    DocxBuilder createChildPartAndBuilder(String partName, String contentType, String relType) throws Exception
    {
        String dummyRelId = null;
        Ref<String> referenceToDummyRelId = new Ref<String>(dummyRelId);
        DocxBuilder result = createChildPartAndBuilder(partName, contentType, relType, /*out*/ referenceToDummyRelId);
        dummyRelId = referenceToDummyRelId.get();
        return result;  // Need to be on a separate line for autoporter to work.
    }

    /// <summary>
    /// Creates a part that is a child to the document part, creates an XML builder for the new part and returns the relationship id for the part.
    /// </summary>
    private DocxBuilder createChildPartAndBuilder(String partName, String contentType, String relType, /*out*/Ref<String> relId) throws Exception
    {
        return createChildPartAndBuilder(mDocumentPart, partName, contentType, relType, /*out*/ relId);
    }

    DocxBuilder createChildPartAndBuilder(OpcPackagePart parentPart, String partName, String contentType, String relType, /*out*/Ref<String> relId) throws Exception
    {
        OpcPackagePart part = getPackage().createChildPart(parentPart, partName, contentType, relType, /*out*/ relId);
        return new DocxBuilder(part, getSaveOptions().getPrettyFormat(), getCompliance(), getMsWordExtensionsVersion(), mWarningCallback);
    }

    void pushBuilder(DocxBuilder builder)
    {
        mBuilderStack.push(mBuilder);
        mBuilder = builder;

        // WORDSNET-21997, 21998. Remember current count of field beginnings and start new counting.
        if (getNeedResetFieldStartContext())
            mFieldsWriter.pushFieldStartCounter();
    }

    void popBuilder()
    {
        if (getNeedResetFieldStartContext())
            mFieldsWriter.popFieldStartCounter();

        mBuilder = mBuilderStack.pop();
    }

    private boolean getNeedResetFieldStartContext()
    {
        String contentType = mBuilder.getPart().getContentType();
        return DocxContentType.COMMENTS.equals(contentType) ||
            DocxContentType.HEADER.equals(contentType) ||
            DocxContentType.FOOTER.equals(contentType);
    }

    /// <summary>
    /// Writes rels of unparsed OPC part.
    /// used for writing themes and Dml Content parts.
    /// </summary>
    /// <param name="oldPart"></param>
    /// <param name="oldRelatedParts"></param>
    /// <param name="newPart"></param>
    /// <param name="writer"></param>
    static void writeUnparsedRels(
        OpcPackagePart oldPart,
        HashMap<String, OpcPackagePart> oldRelatedParts,
        OpcPackagePart newPart,
        DocxDocumentWriterBase writer) throws Exception
    {
        // This all is actually quite a crude hack to preserve all the theme related parts that
        // were specified in the original DOCX. It is a hack because we do not parse the theme XML.
        for (OpcRelationship rel : oldPart.getRels())
        {
            if (rel.isExternal())
            {
                // For external relationships, it is easy, just use all the values from the relationship
                // to add a relationship. There is no part to copy.
                newPart.getRels().add(rel.getId(), rel.getType(), rel.getTarget(), rel.isExternal());
            }
            else
            {
                // For internal relationships, it is a bit more tricky.
                // We need to add a part using the same relId, but we need to watch out and not override
                // any existing part names with the same name.
                String absolutePartName = newPart.getRelatedPartName(rel);
                if (writer.getPackage().getPartByName(absolutePartName) != null)
                {
                    // A part with this name already exists (say image1.jpeg that was written by
                    // the document writing code). We adjust our part name so it is unique.
                    absolutePartName =
                        Path.changeExtension(
                            absolutePartName,
                            Guid.newGuid().toString() + Path.getExtension(absolutePartName));
                }

                // This is the part that was in the original DOCX.
                OpcPackagePart oldRelatedPart = DictionaryExtension.getValueOrNull(oldRelatedParts, rel.getId());
                String relativePartName = OpcPackageBase.makeRelative(newPart.getName(), absolutePartName);

                if (oldRelatedPart != null)
                {
                    // This is the new part.
                    OpcPackagePart newRelatedPart = new OpcPackagePart(absolutePartName, oldRelatedPart.getContentType());
                    newRelatedPart.setStream(oldRelatedPart.getStream());
                    writer.getPackage().getParts().add(newRelatedPart);
                }
                else
                {
                    // Looks like relation defined, but the part is missing.
                    relativePartName = "NULL";
                }

                newPart.getRels().add(rel.getId(), rel.getType(), relativePartName, rel.isExternal());
            }
        }
    }

    /// <summary>
    /// Returns existing or creates new BiDi control character stack for the specified paragraph.
    /// </summary>
    msStack<String> getOrCreateBiDiControlCharStack(Paragraph paragraph)
    {
        msStack<String> bidiControlChars = DictionaryExtension.getValueOrNull(mParaToBidiControlChars, paragraph);
        if (bidiControlChars == null)
        {
            bidiControlChars = new msStack<String>();
            mParaToBidiControlChars.put(paragraph, bidiControlChars);
        }

        return bidiControlChars;
    }

    /// <summary>
    /// Writes special character with code "DocControlChar.DateShortChar" as date field.
    /// </summary>
    private void writeDateShortCharAsField(SpecialChar specialChar) throws Exception
    {
        Debug.assertTrue((specialChar != null) && (msString.equals(specialChar.getText(), ControlChar.NON_BREAKING_HYPHEN)));

        RunPr runPr = specialChar.getRunPr();

        // 1. Field start.
        /*FieldType*/int fldType = FieldType.FIELD_DATE;
        visitFieldStart(new FieldStart(getDocument(), AttrCollectionExtension.deepClone(runPr), fldType));

        // 2. Field code.
        String datePattern = FormatterPal.getShortDatePatternCurrent();
        mRunWriter.writeInline(new Run(getDocument(), "date \\@ \"" + datePattern + "\"", AttrCollectionExtension.deepClone(runPr)));

        // 3. Field separator.
        visitFieldSeparator(new FieldSeparator(getDocument(), AttrCollectionExtension.deepClone(specialChar.getRunPr()), fldType));

        // Skip writing of the field result, because looks like Word update it while opening the document.

        // 4. Field end.
        visitFieldEnd(new FieldEnd(getDocument(), AttrCollectionExtension.deepClone(runPr), fldType, true));
    }

    /// <summary>
    /// Writes the displacedByCustomXml attribute of an annotation node.
    /// </summary>
    private void writeDisplacedByCustomXmlAttribute(IDisplaceableByCustomXml node)
    {
        if (node.getDisplacedByCustomXml() != DisplacedByType.UNSPECIFIED)
        {
            mBuilder.writeAttribute("w:displacedByCustomXml",
                DocxDopEnum.displacedByTypeToDocx(node.getDisplacedByCustomXml()));
        }
    }

    /// <summary>
    /// Writes an element for cross-structure annotation node that has only the w:id and w:displacedByCustomXml
    /// attributes. Used for comment ranges and move range ends.
    /// </summary>
    private void writeAnnotation(Node node, String elementName)
    {
        mBuilder.startElement(elementName);
        mBuilder.writeAttribute("w:id", idToAnnotationId(((INodeWithAnnotationId)node).getIdInternal()));
        writeDisplacedByCustomXmlAttribute((IDisplaceableByCustomXml)node);
        mBuilder.endElement(elementName);
    }

    /// <summary>
    /// Writes the 'w14:stylisticSets' element.
    /// </summary>
    private static void writeStylisticSets(NrxXmlBuilder builder, /*StylisticSets*/int stylisticSets)
    {
        Debug.assertTrue(builder != null);

        if (stylisticSets == StylisticSets.DEFAULT)
        {
            builder.writeRaw(
                "<w14:stylisticSets xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" />");
            return;
        }

        // In this case, we don't need to write the namespace.
        // The namespace in the previous case is written to avoid changing old gold tests.
        builder.startElement("w14:stylisticSets");
        for (int i = (int)StylisticSets.SET_01; i <= (int)StylisticSets.SET_20; i <<= 1)
        {
            /*StylisticSets*/int set = (/*StylisticSets*/int)i;
            if ((stylisticSets & set) != 0)
                builder.writeElementWithAttributes("w14:styleSet", "w14:id", DocxEnum.stylisticSetsToXml(set));
        }
        builder.endElement("w14:stylisticSets");
    }

    /// <summary>
    /// Builder used for writing the current XML part.
    /// </summary>
    DocxBuilder getCurrentBuilder() { return mBuilder; }

    OpcPackagePart getDocumentPart() { return mDocumentPart; }
    void setDocumentPart(OpcPackagePart value) { mDocumentPart = value; }

    /// <summary>
    /// Indicates that field code is currently written.
    /// </summary>
    boolean isInsideFieldCode() { return mFieldsWriter.isInsideFieldCode(); }

    public boolean /*IVmlShapeWriterContext.*/isInsideField() { return mFieldsWriter.isInsideField(); }

    Section getCurrentSection() { return mCurrentSection; }
    void setCurrentSection(Section value) { mCurrentSection = value; }

    OpcPackageBase getPackage() { return mWriter.getPackage(); }

    OoxmlSaveOptions getSaveOptions() { return (OoxmlSaveOptions)getSaveInfo().SaveOptions; }

    DocxRelationshipTypes getRelTypes() { return mWriter.getRelTypes(); }

    DocxNamespaces getDocxNamespaces() { return mWriter.getDocxNamespaces(); }

    protected DocxWriter getWriter() { return mWriter; }

    /// <summary>
    /// Maps last paragraph in comment to paraId.
    /// </summary>
    /// <remarks>
    /// ParaId attribute exists in OOXML formats only.
    /// When source document is DOC for example we need to manually generate paraId for at least last paragraph in comment to be able to
    /// save comment tree structure (comment tree structure is specified using paraId).
    /// </remarks>
    ObjToIntDictionary<Paragraph> getCommentParagraphToParaId() { return mCommentParagraphToParaId; }

    /// <summary>
    /// Returns MS Word version (max), which MS Word Extensions of the document have.
    /// </summary>
    /*MsWordVersionCore*/int getMsWordExtensionsVersion()
    {
        Document doc = mWriter.getDocument();
        if (doc.getComplianceInfo() != null)
            return doc.getComplianceInfo().getMsWordExtensionsVersion();

        return MsWordVersionCore.UNSPECIFIED;
    }

    protected SavingProgressProcessor ProgressProcessor;

    /// <summary>
    /// Counter indicates whether we write FallBack, is used when we write shape to avoid opening redundant Run.
    /// WORDSNET-14513 AlternateContent inside Fallback VML shape have to be placed into the run.
    /// </summary>
    private int mFallbackDeeps;

    /// <summary>
    /// The current top-level DrawingML node. Used to skip nested DrawingML nodes if required.
    /// </summary>
    private ShapeBase mCurrentDmlChart;

    /// <summary>
    /// This provides the global context.
    /// </summary>
    private /*final*/ DocxWriter mWriter;
    private /*final*/ DocumentBase mDoc;

    private /*final*/ NrxStyleIdGenerator mStyleIdGenerator;
    private /*final*/ DocxRunWriter mRunWriter;
    private /*final*/ DocxFieldsWriter mFieldsWriter;

    /// <summary>
    /// Stores case insensitive bookmark name->id pairs.
    /// Bookmark id is assigned when visiting bookmark start and we need to be able to get it later when visiting bookmark end.
    /// </summary>
    private /*final*/ StringToIntDictionary mBookmarkIdsByName = new StringToIntDictionary(false);

    /// <summary>
    /// Additional dictionary for bookmarks located in fallback, used to continue bookmark id enumeration.
    /// </summary>
    private /*final*/ StringToIntDictionary mBookmarkIdsByNameFallback = new StringToIntDictionary(false);

    /// <summary>
    /// The key is integer comment/editable range id (from the model).
    /// The value is integer annotation id that is valid for writing into the output file.
    /// </summary>
    private /*final*/ IntToIntDictionary mIdToAnnotationId = new IntToIntDictionary();

    private /*final*/ msStack<DocxBuilder> mBuilderStack = new msStack<DocxBuilder>();
    private DocxBuilder mBuilder;
    private OpcPackagePart mDocumentPart;
    private DocxCommentsWriter mCommentsWriter;
    private DocxFootnotesWriter mFootnotesWriter;
    private DocxFootnotesWriter mEndnotesWriter;

    private int mAnnotationId;

    private Paragraph mLastParagraphOfCurrentSection;
    private Section mCurrentSection;

    /// <summary>
    /// Header/footer document part names are formed using an incrementing suffix, like 'header1.xml', 'header2.xml', etc.
    /// </summary>
    private int mHeaderNumber;
    private int mFooterNumber;
    private IntToObjDictionary<String> mDiagramReferences;

    private /*final*/ ObjToIntDictionary<Paragraph> mCommentParagraphToParaId = new ObjToIntDictionary<Paragraph>();

    private /*final*/ IWarningCallback mWarningCallback;
    private static final String CONTROL_XML_PART_CONTENT =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ax:ocx ax:classid=\"{{{0}}}\" ax:persistence=\"persistStorage\" r:id=\"rId1\" xmlns:ax=\"{1}\" xmlns:r=\"{2}\"/>";

    /// <summary>
    /// Stack of DML images that were converted from the corresponded VML images.
    /// </summary>
    private /*final*/ msStack<Shape> mDmlsConvertedFromVmls = new msStack<Shape>();

    /// <summary>
    /// Maps paragraph to the stack of BiDi control characters in it.
    /// It is used to mimic Word behavior: adding PDF BiDi control character
    /// for each opened 'w:dir' and 'w:bdo' element to the end of paragraph.
    /// </summary>
    private /*final*/ HashMap<Paragraph, msStack<String>> mParaToBidiControlChars =
        new HashMap<Paragraph, msStack<String>>();

    private /*final*/ IntToObjDictionary<StructuredDocumentTagRangeStart> mSdtRangeStartMap =
        new IntToObjDictionary<StructuredDocumentTagRangeStart>();
}

