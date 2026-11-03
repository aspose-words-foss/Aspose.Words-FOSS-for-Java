// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.StringUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.IntList;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.Language;
import com.aspose.LocaleClassifier;
import com.aspose.FontUtil;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// This class is invoked just after a document was loaded.
/// Basically, it performs "cosmetic" validation tasks that are common for different readers.
/// For the detailed list of tasks performed see code.
/// </summary>
class DocumentPostLoader extends DocumentVisitor
{
    void execute(Document doc, LoadOptions loadOptions, /*LoadFormat*/int detectedFormat) throws Exception
    {
        mLoadOptions = loadOptions;

        // WORDSNET-16401 The language preferences are introduced to set up initial locales.
        applyLanguagePreferences(doc);

        mProgressProcessor = (loadOptions.getLoadFormat() != LoadFormat.PDF) && (detectedFormat != LoadFormat.PDF)
            ? new PostLoadingProgressProcessor(doc, loadOptions) : null;
        doc.accept(this);

        // Reset reference to avoid progress call invocation for the glossary document.
        mProgressProcessor = null;

        if (doc.getGlossaryDocument() != null)
            doc.getGlossaryDocument().accept(this);

        // WORDSNET-25580 Validate styles numbering references.
        validateStyleListLinks(doc.getStyles(), doc.getLists());

        // Synchronize public font embedding options stored in FontInfos
        // and internal font embedding options stored in DocPr.
        doc.getFontInfos().updateEmbedFontOptions(doc.getDocPr());

        // WORDSNET-14959. MS Word versions 2007 - 2019 get LCID from Windows registry.
        if (loadOptions.getUseSystemLcid())
            doc.getFirstSection().getSectPr().updateDefaultsByLcid();

        if ((doc.getThemeInternal() == null) && doc.getStyles().getDefaultRunPr().hasThemeFont())
            doc.setThemeInternal(Theme.getBuiltInTheme().deepClone());
    }

    public /*override*/ /*VisitorAction*/int visitDocumentStart(Document doc) throws Exception
    {
        if (mLoadOptions.getUpdateCharacterUnits())
        {
            mUnitConverter = new UnitConverter();
            mUnitConverter.visitDocumentStart(doc);
        }

        documentStartCore(doc);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitGlossaryDocumentStart(GlossaryDocument glossary)
    {
        documentStartCore(glossary);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitDocumentEnd(Document doc) throws Exception
    {
        documentEndCore();

        if (mLoadOptions.isLoadingBlankDocument())
            Debug.assertTrue(mFieldNodesStack.getCount() == 0);
        else
            visitNonBlankDocumentEnd(doc);

        // This call need for the case when tracked within progress nodes count is zero.
        if (mProgressProcessor != null)
            mProgressProcessor.execute(true);

        return VisitorAction.CONTINUE;
    }

    private void visitNonBlankDocumentEnd(Document doc)
    {
        // FOSS

        // WORDSNET-16638 Problematic document contains fields without ends nodes. Remove or repair such fields to fix
        // the problem.
        resolveInvalidFieldParts(doc);
    }

    /// <summary>
    /// Resolves problematic fields which are accumulated while post loading stage.
    /// </summary>
    private void resolveInvalidFieldParts(Document doc)
    {
        // There are not nodes of fields which pending to be resolved.
        if (mFieldNodesStack.getCount() == 0)
            return;

        while (mFieldNodesStack.getCount() > 0)
        {
            FieldChar fieldPart = mFieldNodesStack.pop();
            boolean isNextNodeFieldStart = (mFieldNodesStack.getCount() > 0) &&
                (mFieldNodesStack.peek().getNodeType() == NodeType.FIELD_START);

            // Mimic Word and try to repair problematic field. For example, see TestDefect12929.
            if (isNextNodeFieldStart && (fieldPart.getNodeType() == NodeType.FIELD_SEPARATOR))
            {
                repairFieldWithoutEnd(doc, (FieldSeparator)fieldPart);
            }
            else
            {
                // See TestJira15795 for details.
                fieldPart.remove();
            }
        }

        WarningUtil.warn(getWarningCallback(), WarningType.UNEXPECTED_CONTENT,
            WarningSource.VALIDATOR, "Invalid fields were updated or removed.");
    }

    /// <summary>
    /// Appends end for the field which is missed or removes problematic nodes according to Word behavior.
    /// </summary>
    /// <remarks>
    /// <para>Actually Word does not append ends for the field, however, when document is opened it looks like
    /// problematic field was repaired and can be updated. AW has to provide valid model at this case, so
    /// end nodes are inserted.</para>
    /// </remarks>
    private void repairFieldWithoutEnd(Document doc, FieldSeparator fieldSeparator)
    {
        Paragraph parentPara = fieldSeparator.getParentParagraph();
        if (parentPara == null)
            return;

        Node fieldEndRefNode = fieldSeparator.nextSiblingOfType(NodeType.FIELD_START);
        boolean isLastFieldInThePara = (fieldEndRefNode == null);

        fieldEndRefNode = isLastFieldInThePara ? parentPara.getLastChild() : fieldEndRefNode.getPreviousSibling();
        RunPr fieldEndRefNodeRunPr = (fieldSeparator.getRunPr() == null) ? new RunPr() : AttrCollectionExtension.deepClone(fieldSeparator.getRunPr());
        FieldEnd fieldEnd = new FieldEnd(doc, fieldEndRefNodeRunPr, fieldSeparator.getFieldType(), true);

        // if last node determined is the field separator, then insert expected end after the current node.
        if (msObject.referenceEquals(fieldEndRefNode, fieldSeparator) || isLastFieldInThePara)
            parentPara.insertAfter(fieldEnd, fieldEndRefNode);
        else
            parentPara.insertBefore(fieldEnd, fieldEndRefNode);

        // Extract start of the field.
        FieldStart fieldStart = (FieldStart)mFieldNodesStack.pop();
        // End node is missing for this field, so type of the field has to be updated from the start node.
        fieldEnd.setFieldType(fieldStart.getFieldType());
        fieldSeparator.setFieldType(fieldStart.getFieldType());
    }

    public /*override*/ /*VisitorAction*/int visitGlossaryDocumentEnd(GlossaryDocument glossary)
    {
        documentEndCore();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBodyStart(Body body) throws Exception
    {
        mStoryTypeStack.push(body.getStoryType());
        return mLoadOptions.getUpdateCharacterUnits()
            ? mUnitConverter.visitBodyStart(body)
            : VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBodyEnd(Body body)
    {
        mStoryTypeStack.pop(body.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterStart(HeaderFooter headerFooter) throws Exception
    {
        mStoryTypeStack.push(headerFooter.getStoryType());
        return mLoadOptions.getUpdateCharacterUnits()
            ? mUnitConverter.visitHeaderFooterStart(headerFooter)
            : VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitHeaderFooterEnd(HeaderFooter headerFooter)
    {
        mStoryTypeStack.pop(headerFooter.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkStart(BookmarkStart bookmarkStart)
    {
        mBookmarkValidator.visitBookmarkStart(bookmarkStart, StoryType.MAIN_TEXT);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitBookmarkEnd(BookmarkEnd bookmarkEnd)
    {
        mBookmarkValidator.visitBookmarkEnd(bookmarkEnd, StoryType.MAIN_TEXT);

        return VisitorAction.CONTINUE;
    }

    private void documentStartCore(DocumentBase doc)
    {
        fixUpListLevelCounts(doc.getLists());
        fixUpStyles(doc.getStyles());
        validateHtmlBlocks(doc.getHtmlBlockCollection());

        mAnnotationValidator = new AnnotationValidator(doc, null, getWarningCallback());

        // Do not truncate bookmark names on load.
        mBookmarkValidator = new BookmarkValidator(getWarningCallback(), false);
    }

    private void documentEndCore()
    {
        mAnnotationValidator.visitDocumentEnd();
        mBookmarkValidator.visitDocumentEnd();
    }

    private static void fixUpListLevelCounts(ListCollection lists)
    {
        int listDefCount = lists.getListDefCount();
        for (int i = 0; i < listDefCount; i++)
        {
            ListDef listDef = lists.getListDefByIndex(i);
            if (!listDef.isListStyleReference())
                listDef.getLevels().fixUpLevelCount(listDef.getListType(), lists.getDocument());
        }
    }

    private void fixUpStyles(StyleCollection styles)
    {
        styles.fixUpBasedOnStyles();

        for (Style style : styles)
            style.validate(getWarningCallback());
    }

    public /*override*/ /*VisitorAction*/int visitSectionStart(Section section)
    {
        section.getSectPr().validateColumns(getWarningCallback());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitShapeStart(Shape shape) throws Exception
    {
        mStoryTypeStack.push(shape.getStoryType());

        // Update size from relative just after loading document into the model,
        // this makes values of Width and Height of shapes to be up to date.
        shape.updateSizeAndPositionFromRelative();

        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLStart(shape);

        convertOleObjectWithoutDataIntoImage(shape);
        restoreProgIdFromOleData(shape);
        fixUpImageShapeZeroSize(shape);

        // Office Math is not fully supported in DOC and WML and it is represented as shape
        // with special attribute which holds OfficeMath xml.
        // Convert shape into Office Math according to loading setting.
        if ((mLoadOptions != null) && mLoadOptions.getConvertShapeToOfficeMath())
            OfficeMathUtil.convertShapeToOfficeMath(shape, getWarningCallback());

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitShapeEnd(Shape shape)
    {
        mStoryTypeStack.pop(shape.getStoryType());

        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            mBookmarkValidator.visitDrawingMLEnd(shape);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitGroupShapeStart(GroupShape shape) throws Exception
    {
        // Update size from relative just after loading document into the model,
        // this makes values of Width and Height of shapes to be up to date.
        shape.updateSizeAndPositionFromRelative();

        if (shape.getMarkupLanguage() == ShapeMarkupLanguage.DML)
            return visitDrawingMLStart(shape);

        return VisitorAction.CONTINUE;
    }

    private /*VisitorAction*/int visitDrawingMLStart(ShapeBase drawingML) throws Exception
    {
        mBookmarkValidator.visitDrawingMLStart(drawingML);

        processFallback(drawingML);
        return VisitorAction.CONTINUE;
    }

    private void processFallback(ShapeBase shape) throws Exception
    {
        if (shape.getFallbackShape() != null)
        {
            shape.getFallbackShape().accept(this);

            // Call MarkLoaded method to save calculated hash code, which will be used to invalidate fallback.
            shape.markLoaded();
        }
    }

    /// <summary>
    /// Restores "ProgId" from the OLE data when it missing.
    /// </summary>
    private static void restoreProgIdFromOleData(Shape shape) throws Exception
    {
        if (!shape.isOleObject())
            return;

        ShapePr shapePr = shape.getShapePr();
        String progId = (String)shapePr.get(ShapeAttr.OLE_PROG_ID);
        OleObject oleObject = ms.as(shapePr.get(ShapeAttr.OLE_OBJECT), OleObject.class);

        if (StringUtil.hasChars(progId) || (oleObject == null))
            return;

        // WORDSNET-18205 Try to obtain missing "ProgId" from the OLE data.
        CompObjStream compObj = CompObjStream.read(oleObject.getData());

        if (compObj != null && StringUtil.hasChars(compObj.getProgId()))
            shape.setShapeAttrInternal(ShapeAttr.OLE_PROG_ID, compObj.getProgId());
    }

    private void convertOleObjectWithoutDataIntoImage(Shape shape) throws Exception
    {
        // WORDSNET-21788 Avoid caching OLE package while calculating OLE object type.
        if (!shape.isOle())
            return;

        if (shape.getOleFormat().getEmbeddedObject() != null && !shape.getOleFormat().getEmbeddedObject().isEmpty())
            return;

        switch (shape.getOleObjectType())
        {
            case OleObjectType.EMBEDDED:
            case OleObjectType.CONTROL:
            // WORDSNET-3466 There is an embedded OLE object in the document but it has no data.
                // We fix it by turning the object into a simple image.
                // This is what MS Word does when working with WordML anyway.
                    warnUnexpected(WarningStrings.EMBEDDED_OLE_WITHOUT_DATA);
            shape.getOleFormat().setEmbeddedObject(null);
                    shape.setShapeType(ShapeType.IMAGE);
                break;
            case OleObjectType.LINKED:
                // Do nothing.
                break;
            default:
                throw new IllegalStateException("Unexpected OLE object type.");
        }
    }

    private void fixUpImageShapeZeroSize(Shape shape) throws Exception
    {
        if (shape.hasImage())
        {
            if (MathUtil.isZero(shape.getWidth()) && MathUtil.isZero(shape.getHeight()))
            {
                // WORDSNET-11685 This seems to be the value that MS Word uses when image shape size is zero.
                warnUnexpected(WarningStrings.ZERO_SHAPE_SIZE);

                shape.setWidthSafe(Shape.DefaultShapeSize);
                shape.setHeightSafe(Shape.DefaultShapeSize);
            }

            // WORDSNET-21857 Word uses different sizes when width is absent and when width is zero.
            if (!shape.getShapePr().contains(ShapeAttr.WIDTH))
                shape.setWidthSafe(Shape.INVALID_SHAPE_SIZE_DEFAULT);

            if (!shape.getShapePr().contains(ShapeAttr.HEIGHT))
                shape.setHeightSafe(Shape.INVALID_SHAPE_SIZE_DEFAULT);
        }
    }

    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell)
    {
        if (getReadFormatting())
        {
            // RK This is a quick fix for a problem in some readers (RTF especially).
            // The reader tends to leave null here and I don't want to crash.
            if (cell.getCellPr() == null)
                cell.setCellPr(new CellPr());

            // WORDSNET-5164 RK Zero widths in normal non-merged cell are bad for us, correct them.
            cell.getCellPr().validateWidth();
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para) throws Exception
    {
        ParaPr paraPr = para.getParaPr();

        if (isAcceptedOrDeadNumberRevision(para))
        {
            warnUnexpected(WarningStrings.INVALID_NUMBER_REVISION);
            paraPr.remove(RevisionAttr.NUMBER_REVISION);
        }

        Style style = para.getDocument().getStyles().getByIstd(paraPr.getIstd(), false);
        if (style != null)
            validateParagraphStyle(style, para);

        if(mLoadOptions.getUpdateCharacterUnits())
            mUnitConverter.visitParagraphStart(para);

        // WORDSNET-11324 List level exceed maximum allowed.
        if (para.getParaPr().getListLevel() > ListLevel.MAX_LEVELS)
        {
            para.getParaPr().setAttr(ParaAttr.LIST_ID, 0);
            para.getParaPr().setAttr(ParaAttr.LIST_LEVEL, 12);

            // Word shows the document differently depending on application version.
            if(mLoadOptions.getMswVersion() <= MsWordVersion.WORD_2013)
                para.insertBefore(new Run(para.getDocument(), ControlChar.TAB), para.getFirstChild());
        }

        if (mProgressProcessor != null)
            mProgressProcessor.execute(false);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Validates paragraph attributes depending on the applied paragraph style.
    /// </summary>
    private static void validateParagraphStyle(Style style, Paragraph para)
    {
        // WORDSNET-11034 Remove reference to style if style is a character style.
        if (style.getType() == StyleType.CHARACTER)
        {
            para.getParaPr().remove(ParaAttr.ISTD);
        }
        else if (style.isHeading())
        {
            // WORDSNET-19542 Mimic the Word behavior for paragraphs formatted with a "Heading" style and outline level.
            fixUpParagraphOutlineLevel(para, style);
        }
    }

    /// <summary>
    /// Makes changes in the paragraph outline level depending on the applied style.
    /// </summary>
    private static void fixUpParagraphOutlineLevel(Paragraph para, Style style)
    {
        Debug.assertTrue(style.isHeading());

        ParaPr paraPr = para.getParaPr();
        if (!paraPr.contains(ParaAttr.OUTLINE_LEVEL))
            return;

        // Word removes directly set outline level value or sets value from style when paragraph is in cell.
        if (para.isInCell())
            paraPr.setOutlineLevel(style.getParaPr().getOutlineLevel());
        else
            paraPr.remove(ParaAttr.OUTLINE_LEVEL);
    }

    private static boolean isAcceptedOrDeadNumberRevision(Paragraph para)
    {
        ParagraphNumberRevision numberRevision = para.getParaPr().getNumberRevision();
        if (numberRevision != null)
        {
            // *** An old formatting revision that remains after changes were accepted?
            // ParaPr.ListId any?
            // ParaPr.NumberRevision.IsInserted = false
            // ParaPr.NumberRevision.WasNumbered = false
            // ParaPr.NumberRevision.NumberLocations, NumberStyles, NumberValues set to "previous" value?
            boolean isDead = !numberRevision.isInsertion() && !numberRevision.getWasNumbered();
            if (isDead)
                return true;

            // *** List formatting accepted on a paragraph:
            // ParaPr.ListId is non-zero - but it is important to query the paragraph, not just the direct formatting.
            // ParaPr.NumberRevision.IsInserted = false
            // ParaPr.NumberRevision.WasNumbered = true
            // ParaPr.NumberRevision.NumberLocations, NumberStyles, NumberValues set to "previous" value.
            boolean isAccepted = !numberRevision.isInsertion() && numberRevision.getWasNumbered() && para.isListItem();
            if (isAccepted)
                return true;

            // WORDSNET-5720 Seems to be another variation of "dead" revision.
            // ParaPr.NumberRevision.IsInserted = true - this is insertion of list.
            // ParaPr.NumberRevision.WasNumbered = false - was not numbered before.
            // para.IsListItem = false - but currently it's not list item.
            boolean isZombie = numberRevision.isInsertion() && !numberRevision.getWasNumbered() && !para.isListItem();
            if (isZombie)
                return true;
        }
        return false;
    }

    public /*override*/ /*VisitorAction*/int visitSmartTagStart(SmartTag smartTag)
    {
        untangleSmartTag(smartTag);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitStructuredDocumentTagStart(StructuredDocumentTag sdt) throws Exception
    {
        sdt.updatePlaceholderReference(false);

        // We follow MS Word in ID 'always present and unique' behavior,
        // meaning that if we've read document with no id, we will create our own one.
        sdt.updateId();

        setSelectedValueBySdtContent(sdt);

        if (mLoadOptions.getUpdateXmlMapping())
        {
            // WORDSNET-18112 Update SDT bound content.
            if (sdt.getXmlMapping().checksumNotEqual(mXmlMappingContext))
            {
                // WORDSNET-16209 Do not update SDT marked for deletion.
            if (((ITrackableNode)sdt).getDeleteRevision() == null)
                sdt.getXmlMapping().updateContent(mXmlMappingContext);
        }
        }

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// andrnosk: WORDSNET-6911 Try to set SelectedValue using sdtContent.
    /// </summary>
    private void setSelectedValueBySdtContent(StructuredDocumentTag sdt)
    {
        // ListItems is only accessible for ComboBox or DropDownList SDT types.
        if ((sdt.getSdtType() == SdtType.DROP_DOWN_LIST) || (sdt.getSdtType() == SdtType.COMBO_BOX))
        {
            if (sdt.getListItems().getSelectedValue() == null)
            {
                String sdtContentValue = sdt.getContentValue();

                for (int idx = 0; idx < sdt.getListItems().getCount(); idx++)
                {
                    if (msString.equals(sdt.getListItems().get(idx).getDisplayText(), sdtContentValue))
                    {
                        sdt.getListItems().setSelectedValue(sdt.getListItems().get(idx));
                        return;
                    }
                }
                warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.WORD_ML, "Structured Document Tag content is messed up.");
            }
        }
    }

    private static void untangleSmartTag(SmartTag smartTag)
    {
        // RK MS Word seems to write nested smart tags that start at the same character position
        // (e.g. address/City) in random order in different formats. Sometimes it writes the outer
        // smart tag first, sometimes the inner smart tag first. I cannot figure out the algorithm
        // that it uses, but I have to represent smart tags in the correct way in the model and
        // when writing to documents so the smart tags appear properly in MS Word.
        // This method tries to "untagle" some well-known smart tags.
        // If you come across smart tags that are not untangled and cause problems, add them to this method.

        Node tagChild = smartTag.getFirstChild();
        if (tagChild instanceof SmartTag)
        {
            SmartTag childSmartTag = (SmartTag) tagChild;
            if (("address".equals(childSmartTag.getElement())) ||
                ("PersonName".equals(childSmartTag.getElement())) ||
                ("place".equals(childSmartTag.getElement())))
            {
                smartTag.swap(childSmartTag);
            }
        }
    }

    public /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment)
    {
        mStoryTypeStack.push(comment.getStoryType());
        return mAnnotationValidator.visitComment(comment);
    }

    public /*override*/ /*VisitorAction*/int visitCommentEnd(Comment comment)
    {
        mStoryTypeStack.pop(comment.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        mStoryTypeStack.push(footnote.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteEnd(Footnote footnote)
    {
        mStoryTypeStack.pop(footnote.getStoryType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeStart(CommentRangeStart commentRangeStart)
    {
        return mAnnotationValidator.visitCommentRangeStart(commentRangeStart);
    }

    public /*override*/ /*VisitorAction*/int visitCommentRangeEnd(CommentRangeEnd commentRangeEnd)
    {
        return mAnnotationValidator.visitCommentRangeEnd(commentRangeEnd);
    }

    public /*override*/ /*VisitorAction*/int visitEditableRangeStart(EditableRangeStart editableRangeStart)
    {
        return mAnnotationValidator.visitEditableRangeStart(editableRangeStart);
    }

    public /*override*/ /*VisitorAction*/int visitEditableRangeEnd(EditableRangeEnd editableRangeEnd)
    {
        return mAnnotationValidator.visitEditableRangeEnd(editableRangeEnd);
    }

    public /*override*/ /*VisitorAction*/int visitTableStart(Table table) throws Exception
    {
        // WORDSNET-23913 Remove empty rows.
        ArrayList<Row> rows = table.getRows().<Row>toList();
        for(Row row : rows)
        {
            if (row.getCells().getCount() == 0)
            {
                warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.VALIDATOR, WarningStrings.TABLE_VALIDATOR_EMPTY_ROW);
                row.remove();
            }
            // dmatv: ValidateAlignedIndent() moved to VisitTableEnd()
            // as it had been removing some indents needed to merge adjacent tables correctly.
        }

        if (getReadFormatting())
        {
            Row row = table.findDifferentPositioningOrStyle();
            if (row != null)
            {
                row.breakTableBefore();
                // Next sibling is cached in our visitor implementation so accept new table explicitly.
                row.getParentTable().accept(this);
            }

            table.fixNestedTableLayout();
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitTableEnd(Table table)
    {
        Table combinedTable = table;
        if (getReadFormatting())
        {
            // Do not bother with table merging and grid update if not in the main document.
            boolean isMainDocument = table.getDocument() instanceof Document;
            if (isMainDocument)
            {
                // Get grid and cell spans from cell widths for doc/rtf documents.
                DocRtfGridHandler.getGridAndSpansFromCellWidths(table);

                // Combine the adjacent tables.
                combinedTable =  mTableMerger.joinAdjacentTablesCombineGrids(table);

                // Sometimes, adjacent tables with similar properties are merged in the model.
                // Grid must be calculated for the merged table,
                // calculating for separate tables before merge may not be accurate.
                boolean waitForMergedTablesBeforeGridUpdate = (combinedTable == null);
                if (!waitForMergedTablesBeforeGridUpdate)
                {
                    // Now the grid can be updated for the merged table.

                    // WORDSNET-21928 remove unneeded gridBefore/widthBefore.
                    // Ideally that should be done by table grid calculation below,
                    // but currently it does not yet handle tables with gridBefore.
                    boolean isMergedTable = (table != combinedTable);
                    // Do not strip gridBefore from merged tables however.
                    // Though it is probably OK, the case is tricky and it was not investigated per 21928.
                    // For now it seems safer just to keep the old logic for merged adjacent tables.
                    if (!isMergedTable)
                        removeSameWidthBefore(combinedTable);
                    // New grid algorithm may become applicable if same gridBefore attributes were actually removed.

                    updateNestedTableGrids(combinedTable, getNestedTables(), true);
                }
            }
        }

        // Validate indents after combining adjacent tables.
        if (combinedTable != null)
            for (Row row : (Iterable<Row>) table.getRows())
                row.validateAlignedIndent();

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Removes gridBefore/widthBefore attributes if they are the same for all table rows.
    /// </summary>
    /// <remarks>
    /// It seems, MS Word removes gridBefore/widthBefore if they are the same for all table rows.
    /// In such case, there is actually no point in having them. The attributes are normally used in jagged tables
    /// to indicate cells missing before the first row cell. If they are the same for all rows, then every row starts
    /// from the same column and the attributes are not needed.
    /// It seems, WPS Office can generate documents with tables having the same gridBefore/widthBefore for all rows for some reason.
    /// Removing them fixes WORDSNET-21928 and WORDSNET-21935
    /// It would be best to handle the situation during grid calculation,
    /// but tables with gridBefore are not handled by the new algorithm yet.
    /// </remarks>
    private static void removeSameWidthBefore(Table table)
    {
        // Nothing to do for no rows.
        if (table == null || table.getFirstRow() == null)
            return;

        TablePr firstRowPr = table.getFirstRow().getTablePr();

        if (firstRowPr == null)
            return;

        int gridBefore = firstRowPr.getGridBefore();
        PreferredWidth widthBefore = firstRowPr.getWidthBefore();
        PreferredWidth widthBeforeOriginal = firstRowPr.getWidthBeforeOriginal();

        // No point scanning the table if there is no grid/width before on row 1.
        if (gridBefore == 0 && widthBefore.getValueRaw() == 0 && widthBeforeOriginal.getValueRaw() == 0)
            return;

        boolean allRowsHaveSameAttrs = true;
        boolean anyRowHasRevision = false;
        for (Row row : (Iterable<Row>) table.getRows())
        {
            TablePr rowPr = row.getTablePr();

            if (rowPr == null)
            {
                Debug.fail("Null Row.TablePr should not happen.");
                // Prevent removing gridBefore as default zeros in this case do not match the first row.
                allRowsHaveSameAttrs = false;
                break;
            }

            anyRowHasRevision = rowPr.hasRevisions() || rowPr.hasEmptyFormatRevision();
            if (anyRowHasRevision)
                break;

            allRowsHaveSameAttrs = (gridBefore == rowPr.getGridBefore()) &&
                widthBefore.equals(rowPr.getWidthBefore()) &&
                widthBeforeOriginal.equals(rowPr.getWidthBeforeOriginal());

            // TODO actually, a situation with rows have zero widths before of different types is possible.

            if (!allRowsHaveSameAttrs)
                break;
        }

        // Do not apply the logic to tables with revisions, it was not tested with them.
        if (!anyRowHasRevision && allRowsHaveSameAttrs)
        {
            for (Row row : (Iterable<Row>) table.getRows())
            {
                TablePr rowPr = row.getTablePr();
                rowPr.remove(TableAttr.SYS_GRID_BEFORE);
                rowPr.remove(TableAttr.WIDTH_BEFORE_ORIGINAL);
            }

            // The tables in WORDSNET-21928 and WORDSNET-21935 for which the logic applies also have
            // tblGrid elements with the first column corresponding to the invalid "gridBefore".
            // This column should be removed from tblGrid as well.
            // Otherwise it will affect taking the default width from tblGrid for
            // cells with "auto" preferred width in a fixed layout table. 21928 and 21935 both have this case.
            removeFirstColumnsFromGrid(table.getTablePr(), gridBefore);
        }
    }

    /// <summary>
    /// Removes the given number of columns from the beginning of grids stored in <see cref="TableAttr.Sys_TableGrid"/>
    /// and <see cref="TableAttr.Sys_TableGridForNewAlgorithm"/>.
    /// </summary>
    private static void removeFirstColumnsFromGrid(TablePr tablePr, int removeCount)
    {
        IntList grid = (IntList)tablePr.get(TableAttr.SYS_TABLE_GRID);
        removeFirstColumnsFromList(grid, removeCount);

        TableGridColumnsAttr gridAttr = (TableGridColumnsAttr)tablePr.get(TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM);
        if (gridAttr != null)
            removeFirstColumnsFromList(gridAttr.GridColumns, removeCount);
    }

    /// <summary>
    /// Removes the given number of items from the specified <see cref="IntList"/>.
    /// </summary>
    private static void removeFirstColumnsFromList(IntList list, int removeCount)
    {
        if (list == null)
            return;

        int checkedCount = Math.min(list.getCount(), removeCount);
        if (checkedCount > 0)
            list.removeRange(0, checkedCount);
    }

    public /*override*/ /*VisitorAction*/int visitRowStart(Row row)
    {
        // WORDSNET-12368 Should verify that style type is 'Table'.
        // We do this check here as well as in DocumentValidator because some of operations
        // can be performed before DocumentValidator is invoked. For example,
        // document comparison has thrown an exception before this fix.
        row.ensureTableStyleIsValid();

        TableValidator.removeInvalidAttributes(row);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        mFieldNodesStack.push(fieldStart);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        mFieldNodesStack.push(fieldSeparator);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        // Extract separator for the field, in case when it was found.
        StackExtension.popIfInstanceOf(mFieldNodesStack, FieldSeparator.class);

        // Extract start node for the field, in case when it was found.
        StackExtension.popIfInstanceOf(mFieldNodesStack, FieldStart.class);

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Adds the given table to the stack of the given nested tables and processes the whole stack if the table is not nested.
    /// </summary>
    /// <remarks>
    /// The method is supposed to be called from the document validator or postloader.
    /// </remarks>
    static void updateNestedTableGrids(Table table, msStack<Table> nestedTables, boolean isLoading)
    {
        nestedTables.push(table);

        if (!table.isNested())
        {
            while (nestedTables.getCount() != 0)
            {
                Table nextTable = nestedTables.pop();

                // FOSS The "new" table grid re-calculation from table/cell properties is removed.
                // The column widths stored in the document are used as is for all tables.
                // The removed calculation accessed the table style as a side effect,
                // which materialized the default TableNormal style in the document model.
                // Keep the access so that saved documents preserve the TableNormal style
                // until the model creates it unconditionally.
                TableStyle unused = ms.as(nextTable.getStyle(), TableStyle.class);
            }
        }
    }

    /// <summary>
    /// Applies <see cref="LanguagePreferences"/> to a specified document.
    /// </summary>
    private void applyLanguagePreferences(Document doc) throws Exception
    {
        if (LanguagePreferences.getTestMode())
            return;

        if (mLoadOptions == null)
            return;

        LanguagePreferences preferences = mLoadOptions.getLanguagePreferences();

        RunPr defaultRunPr = doc.getStyles().getDefaultRunPr();

        defaultRunPr.setAttrNoOverride(FontAttr.LOCALE_ID, preferences.getLocaleId());
        defaultRunPr.setAttrNoOverride(FontAttr.LOCALE_ID_FAR_EAST, preferences.getLocaleIdFarEast());
        defaultRunPr.setAttrNoOverride(FontAttr.LOCALE_ID_BI, preferences.getLocaleIdBi());

        // The setting is propagated to layout to imitate MS Word behavior with Asian preferred language set.
        // See Test26100Kinsoku().
        /*Language*/int preferredEditingLanguage = (/*Language*/int)preferences.getLocaleIdFarEastByDefaultLanguage();
        doc.getDocPr().PreferredEditingLanguage = preferredEditingLanguage;

        // WORDSNET-24172 Set additional properties for Asian languages to mimic Word.
        // Looks strange a bit, but Word only changes in one direction.
        // When one of the Asian languages is set as the default language it makes changes.
        // But, when NOT an Asian language is set, Word does nothing.
        if (LocaleClassifier.isChineseOrJapanese((int)preferences.getDefaultEditingLanguage()))
        {
            doc.getDocPr().CompatibilityOptions.setUseFELayout(true);

            Theme theme = doc.getThemeInternal();
            if ((theme != null) && (theme.getThemeFontLanguages() != null))
                theme.getThemeFontLanguages().EastAsia = preferredEditingLanguage;

             // WORDSNET-25854 Do not override East Asian font if it is already so.
            String fontName = defaultRunPr.getComplexNameFarEast().resolve(theme);
            if (!FontUtil.isAsianFont(doc.getFontProvider(), fontName))
                defaultRunPr.setAttr(FontAttr.NAME_FAR_EAST, ComplexFontName.fromTheme(ThemeFontCore.MINOR_EAST_ASIA));

            // Dmatv: this ensures setting correct font for Chinese and Japanese.
            // Checked by Test23520().
            defaultRunPr.setLocaleIdFarEast((int)preferredEditingLanguage);
        }
    }

    /// <summary>
    /// Validates HtmlBlock collections.
    /// </summary>
    private static void validateHtmlBlocks(HtmlBlockCollection htmlBlocks)
    {
        for(int i = 0; i < htmlBlocks.getCount(); i++)
        {
            HtmlBlock htmlBlock = htmlBlocks.getHtmlBlockByIndex(i);

            // Remove circular reference.
            if (htmlBlock.getParentId() == htmlBlock.getId())
                htmlBlock.setParentId(0);
        }
    }

    /// <summary>
    /// Ensures that only one style is linked to each list level.
    /// </summary>
    /// <remarks>
    /// Although code looks complex actually we just collect styles referred to each list level and then
    /// "remove" extra references (style become not numbered with list level formatting applied).
    ///
    /// This logic was obtained by tests.
    /// </remarks>
    private void validateStyleListLinks(StyleCollection styles, ListCollection lists)
    {
        // Style to ListId + ListLevel references table.
        HashMap<Integer, HashMap<Integer, ArrayList<Style>>> refCount = new HashMap<Integer, HashMap<Integer, ArrayList<Style>>>();

        // Loop for all styles and collect references to list levels.
        for (int istd = 0; istd < styles.getMaxIstd(); istd++)
        {
            Style style = styles.getByIstd(istd, false);

            if(style == null || style.getBuiltIn() || style.getParaPr().getListId() == 0)
                continue;

            int listId = style.getParaPr().getListId();
            List list = lists.getListByListId(listId);

            if (list == null)
                continue;

            // Seems lists that are definition for a style is not counted.
            if (list.getListDef().getListStyleIstd() == istd)
                continue;

            int listLevel = 0;

            // Find level referenced either by ListLevel attribute or ParaStyleIstd.
            if (style.getParaPr().containsKey(ParaAttr.LIST_LEVEL))
                listLevel = (/*int*/Integer)style.getParaPr().get(ParaAttr.LIST_LEVEL);
            else
            {
                ListDef listDef = list.getListDef();
                for (ListLevel level : listDef.getLevels())
                {
                    if (level.getParaStyleIstd() == style.getIstd())
                    {
                        listLevel = level.getLevelNumber();
                        break;
                    }
                }
            }

            // Fill reference count table.
            if (!refCount.containsKey(listId))
                refCount.put(listId, new HashMap<Integer, ArrayList<Style>>());

            if (!refCount.get(listId).containsKey(listLevel))
                refCount.get(listId).put(listLevel, new ArrayList<Style>());

            msArrayList.add(refCount.get(listId).get(listLevel), style);
        }

        // Unlink extra styles referenced to certain list level.
        for (List list : lists)
        {
            for (ListLevel level : list.getListDef().getLevels())
            {
                if(level.getNumberStyle() == NumberStyle.BULLET)
                    continue;

                if(!refCount.containsKey(list.getListId()) || !refCount.get(list.getListId()).containsKey(level.getLevelNumber()))
                    continue;

                ArrayList<Style> refStyles = refCount.get(list.getListId()).get(level.getLevelNumber());
                if (refStyles == null)
                    continue;

                if(refStyles.size() > 1)
                    for (int i = 1; i < refStyles.size(); i++)
                    {
                        Style invalidStyles = refStyles.get(i);
                        invalidStyles.getParaPr().remove(ParaAttr.LIST_ID);
                        invalidStyles.getParaPr().remove(ParaAttr.LIST_LEVEL);
                        level.getParaPr().expandTo(invalidStyles.getParaPr());

                        warn(WarningType.UNEXPECTED_CONTENT, WarningSource.VALIDATOR,
                            msString.format(WarningStrings.REMOVED_STYLE_LIST_REFERENCE, invalidStyles.getName()));
                    }
            }
        }
    }

    /// <summary>
    /// Logs a warning to the user-provided warning callback.
    /// </summary>
    private void warn(/*WarningType*/int warningType, /*WarningSource*/int warningSource, String description)
    {
        if (getWarningCallback() != null)
            getWarningCallback().warning(new WarningInfo(warningType, warningSource, description));
    }

    private void warnUnexpected(String description, /*params*/ Object... args)
    {
        WarningUtil.warnUnexpected(getWarningCallback(), description, args);
    }

    private IWarningCallback getWarningCallback() { return (mLoadOptions != null) ? mLoadOptions.getWarningCallback() : null; }

    private boolean getReadFormatting() { return (mLoadOptions != null) && !mLoadOptions.getSkipFormatting(); }

    /// <summary>
    /// Remembers created field start and separator nodes. Pops on field end.
    /// This collection contains nodes, which can be recognized as invalid while visiting end of the document.
    /// </summary>
    private /*final*/ msStack<FieldChar> mFieldNodesStack = new msStack<FieldChar>();

    /// <summary>
    /// Strictly speaking all features of the comment validator are required before save only.
    /// Here, after loading, we only need to regenerate comment ids, but it is so easy by running the complete comment validator.
    /// andrnosk: And the same we should do for editable ranges.
    /// </summary>
    private AnnotationValidator mAnnotationValidator;

    private BookmarkValidator mBookmarkValidator;

    private LoadOptions mLoadOptions;

    /// <summary>
    /// Stores nested tables stack for the grid update.
    /// </summary>
    private msStack<Table> getNestedTables() { return mNestedTables; }
    private /*final*/ msStack<Table> mNestedTables = new msStack<Table>();

    /// <summary>
    /// Handles adjacent table merging.
    /// </summary>
    private /*final*/ TableMerger mTableMerger = new TableMerger();

    private UnitConverter mUnitConverter = new UnitConverter();

    private /*final*/ XmlMappingContext mXmlMappingContext = new XmlMappingContext();

    private PostLoadingProgressProcessor mProgressProcessor;

    private /*final*/ StoryTypeStack mStoryTypeStack = new StoryTypeStack();
}

