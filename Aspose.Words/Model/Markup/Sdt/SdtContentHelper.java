// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/17/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.msString;
import com.aspose.LocaleConverter;
import com.aspose.SystemPal;
import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import java.util.TimeZone;
import com.aspose.BidiRun;
import com.aspose.BidiAlgorithm;
import java.util.ArrayList;
import com.aspose.CharacterClassifier;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Helps to insert content inside Sdt node. Covers two major cases:
/// -1. Creation and insertion of default content upon creation.
///   MS Word will not show sdt if default content is not specified, so AW has to populate default content upon Sdt creation.
/// -2. Insertion of bound content from custom xml parts. Databinding use case is not yet supported, but will be using this class facilities, when databinding supported.
/// </summary>
/*static*/ class SdtContentHelper
{
	/* Simulation of static class by using private constructor */
	private SdtContentHelper()
	{}

    /// <summary>
    /// Replaces content of Sdt with placeholder.
    /// </summary>
    static void replaceContentWithPlaceholder(StructuredDocumentTag sdt)
    {
        // WORDSNET-9881 Quick fix to remove regression introduced by WORDSNET-9337
        if (sdt.getLevel() != MarkupLevel.INLINE)
            return;

        // WORDSNET-12052 MS Word does not replace sdt content if 'showingPlcHdr' element set to true.
        if ((sdt.getPlaceholder() != null) && !sdt.isShowingPlaceholderText())
        {
            Body placeholderBody = (Body)sdt.getPlaceholder().getChild(NodeType.BODY, 0, true);

            sdt.removeAllChildren();

            for (Node node : (Iterable<Node>) placeholderBody.getChildNodes(NodeType.ANY, false))
                insertContent(sdt, node, true);

            // Sdt content has been changed, it is necessary to set IsShowingPlaceholderText flag.
            sdt.isShowingPlaceholderText(true);
        }
    }

    /// <summary>
    /// - Inserts default content from placeholders into sdt,
    /// or
    /// - Builds default content and inserts it into Sdt node if sdt has no applicable placeholders.
    /// </summary>
    static void insertDefaultContent(StructuredDocumentTag sdt, boolean safe)
    {
        if (sdt.getPlaceholder() != null)
        {
            insertPlaceholderContent(sdt);
        }
        else
        {
            // picture does not have a placeholder, but has default dml content.
            switch (sdt.getSdtType())
            {
                case SdtType.PICTURE:
                    insertPictureDefaultContent(sdt);
                    break;
                case SdtType.CHECKBOX:
                    updateCheckboxContent(sdt);
                    break;
                case SdtType.REPEATING_SECTION:
                case SdtType.REPEATING_SECTION_ITEM:
                    // No default content for repeating section and item.
                    break;
                case SdtType.CITATION:
                    // No default content for citation.
                    break;
                case SdtType.PLAIN_TEXT:
                    insertContent(sdt, new Run(sdt.getDocument(), DEFAULT_CONTENT_IF_NO_PLACEHOLDER), true);
                    break;
                default:
                    if (!safe)
                        throw new IllegalStateException("Unknown sdt type. Please report exception.");
                    break;
            }
        }

        // Sdt content has been changed, it is necessary to set IsShowingPlaceholderText flag.
        if (!sdt.isShowingPlaceholderText() && (sdt.getPlaceholder() != null))
            sdt.isShowingPlaceholderText(true);
    }

    /// <summary>
    /// Inserts content inside sdt. Verifies that Markup level of sdt is compatible with level of content being inserted.
    /// Performs some minor resiliencies.
    /// </summary>
    static Node insertContent(StructuredDocumentTag sdt, Node content, boolean clearChildNodes)
    {
        Debug.assertTrue((sdt != null) && (content != null));

        if (content.getNodeType() == NodeType.DOCUMENT)
            return insertDocument(sdt, (Document)content);

        Node referenceNode = null;

        // andrnosk: WORDSNET-7009 We need to apply SDT RunPr to SDT content to preserve formatting.
        if ((content.getNodeType() == NodeType.RUN) &&
            (sdt.getSdtType() != SdtType.CHECKBOX)) // DD: Checkboxes do not seem to have RunPr in their SDT props.
        {
            ((Run)content).setRunPr(AttrCollectionExtension.deepClone(sdt.getContentsRunPr()));
        }

        // andrnosk: WORDSNET-7273 We have to remove child nodes the same type as content node only,
        // to preserve parent nodes formatting.
        if (clearChildNodes)
        {
            NodeCollection childNodes = sdt.getChildNodes(content.getNodeType(), true);

            // WORDSNET-21562 We need to insert updated sdt content to the proper position. If referenceNode is null
            // it will be added to the end of the sdt child nodes collection (default behavior).
            if (childNodes.getCount() != 0)
                referenceNode = childNodes.get(childNodes.getCount() - 1).getNextSibling();

            childNodes.clear();
        }

        Node newNode;
        switch (sdt.getLevel())
        {
            case MarkupLevel.INLINE:
                newNode = insertInlineContent(sdt, content, referenceNode);
                break;
            case MarkupLevel.BLOCK:
                newNode = sdt.getDocument().importNode(content, true);
                insertBlockContent(sdt, newNode);
                break;
            case MarkupLevel.CELL:
            case MarkupLevel.ROW:
                newNode = sdt.getDocument().importNode(content, true);
                insertCellRowContent(sdt, newNode);
                break;
            default:
                throw new IllegalStateException("Unknown markup level.");
        }

        if (sdt.isShowingPlaceholderText() && (sdt.getPlaceholder() == null))
            sdt.isShowingPlaceholderText(false);

        return newNode;
    }

    /// <summary>
    /// Cleans existing Checkbox content and inserts new one based on the value of Checked property.
    /// </summary>
    static void updateCheckboxContent(StructuredDocumentTag sdt)
    {
        SdtCheckBox checkBox = (SdtCheckBox)sdt.getControlProperties();

        if (!StringUtil.hasChars(checkBox.getCheckedStateInfo().getFontName()))
            checkBox.setCheckedStateInfo(SdtCheckBoxStateInfo.DEFAULT_CHECKED_STATE_INFO);

        if (!StringUtil.hasChars(checkBox.getUncheckedStateInfo().getFontName()))
            checkBox.setUncheckedStateInfo(SdtCheckBoxStateInfo.DEFAULT_UNCHECKED_STATE_INFO);

        Run curRun = (Run) sdt.getChild(NodeType.RUN, 0, true);
        SdtCheckBoxStateInfo sdtCheckBoxStateInfo =
            (checkBox.getChecked()) ? checkBox.getCheckedStateInfo() : checkBox.getUncheckedStateInfo();
        String characterText = Character.toString(((char)sdtCheckBoxStateInfo.getCharacterCode()));
        String fontName = sdtCheckBoxStateInfo.getFontName();

        if (curRun != null)
        {
            curRun.setText(characterText);
            curRun.getRunPr().setName(fontName);
        }
        else
        {
            Run r = new Run(sdt.getDocument(), characterText);
            RunPr rPr = r.getRunPr();
            rPr.setName(fontName);
            insertContent(sdt, r, true);
        }
    }

    /// <summary>
    /// Replaces content of CustomXML/Core properties if SDT is data bound.
    /// </summary>
    static boolean replaceDataBoundContent(StructuredDocumentTag sdt, Regex pattern, String replacement) throws Exception
    {
        String xmlValue = sdt.getXmlMapping().getValue();

        if (StringUtil.hasChars(xmlValue))
        {
            String result = pattern.replace(xmlValue, replacement);
            if (!msString.equals(result, xmlValue))
            {
                sdt.getXmlMapping().setValue(result);
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Returns Sdt display value according to the current sdt datetime display format.
    /// </summary>
    static String getSdtDateDisplayValue(StructuredDocumentTag sdt, String datetime, XmlMappingContext ctx)
    {
        String fieldUpdateCultureName = LocaleConverter.localeToDocxTag(sdt.getDateDisplayLocale());
        boolean needSetLocation = StringUtil.hasChars(fieldUpdateCultureName);

        try
        {
            if (needSetLocation)
            {
                SystemPal.saveCulture();
                SystemPal.setCulture(fieldUpdateCultureName);
            }

            String dateDisplayFormat = convertFormatString(sdt.getDateDisplayFormat());

            // Use value as date only if it's in strict Xml dateTime format otherwise render it as is.
            DateTime dateTime = FormatterPal.xmlToDateTimeExact(datetime);

            return DateTime.equals(dateTime, DateTime.MinValue)
                    ? datetime
                    : DateTime.ConvertTime(dateTime, ctx.CustomTimeZone).toString(dateDisplayFormat);
        }
        finally
        {
            if (needSetLocation)
                SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// Clears contents of the specified structured document tag and displays a placeholder if it is defined.
    /// </summary>
    static void clear(StructuredDocumentTag sdt) throws Exception
    {
        // Removing contents of an SDT may cause that, for example, it will not be removed after accepting
        // revisions. Do nothing at this case.
        if (RevisionUtil.hasRevisions(sdt))
            return;

        /*SdtType*/int sdtType = sdt.getSdtType();

        // Do nothing for some SDT types if there is no placeholder.
        if (!isCleanable(sdt))
            return;

        // Clear XML node only for simple type SDTs.
        if (!sdt.getXmlMapping().isEmpty() && !sdt.isRepeatingSection() && !sdt.isRepeatingSectionItem())
        {
            try
            {
                sdt.getXmlMapping().setValue(null);
            }
            catch(Exception e)
            {
                // Catch exceptions since mapping may be incorrect.
            }
        }

        if (sdtType == SdtType.COMBO_BOX || sdtType == SdtType.DROP_DOWN_LIST)
        {
            ((SdtDropDownListBase)sdt.getControlProperties()).getListItems().setSelectedValue(null);
        }

        switch (sdt.getLevel())
        {
            case MarkupLevel.ROW:
            {
                // Placeholder of a row-level SDT works only on a single-column rows. If there are several cells,
                // MS Word raises an error on editing their contents.
                NodeCollection cells = sdt.getChildNodes(NodeType.CELL, true);
                if (cells.getCount() == 1)
                    clearCell((Cell)cells.get(0), sdt); // placeholder is inserted into a cell
                else
                    return; // skip setting IsShowingPlaceholderText

                break;
            }
            case MarkupLevel.CELL:
            {
                Cell cell = (Cell)sdt.getChild(NodeType.CELL, 0, true);
                if (cell != null)
                    clearCell(cell, sdt);

                break;
            }
            default:
            {
                Paragraph formatSourceParagraph = getLastParagraph(sdt);

                sdt.removeAllChildren();
                insertDefaultContent(sdt, true);

                if (sdt.getCount() == 0)
                {
                    insertContentForEmptySdt(
                        sdt,
                        sdt.getContentsRunPr(),
                        sdt.getLevel() == MarkupLevel.BLOCK,
                        sdt.getEndCharacterRunPr());
                }

                if (formatSourceParagraph != null)
                {
                    Paragraph paragraph = getLastParagraph(sdt);
                    if (paragraph != null)
                    {
                        paragraph.setParaPr(AttrCollectionExtension.deepClone(formatSourceParagraph.getParaPr()));
                        paragraph.setParagraphBreakRunPr(AttrCollectionExtension.deepClone(formatSourceParagraph.getParagraphBreakRunPr()));
                    }
                }

                break;
            }
        }

        sdt.isShowingPlaceholderText(true);
    }

    private static boolean isCleanable(StructuredDocumentTag sdt)
    {
        if (sdt.getPlaceholder() != null)
            return true;

        if (sdt.isRepeatingSection())
            return false;

        if (sdt.isRepeatingSectionItem())
            return false;

        if (sdt.getLevel() == MarkupLevel.ROW)
            return false;

        switch (sdt.getSdtType())
        {
            case SdtType.CHECKBOX:
            case SdtType.CITATION:
            case SdtType.GROUP:
            case SdtType.ENTITY_PICKER:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Inserts five spaces as contents of the specified node. MS Word inserts such contents on clearing an SDT
    /// if it has no placeholder.
    /// </summary>
    static void insertContentForEmptySdt(CompositeNode parentNode, RunPr runPr, boolean addParagraph,
        RunPr breakRunPr)
    {
        CompositeNode runParent = parentNode;
        if (addParagraph)
        {
            runParent = new Paragraph(parentNode.getDocument());
            ((Paragraph)runParent).setParagraphBreakRunPr(AttrCollectionExtension.deepClone(breakRunPr));
            parentNode.appendChild(runParent);
        }

        // If a structured document tag has no placeholder, MS Word inserts five spaces on clearing its contents
        // to be able to select the SDT in the document.
        Run run = new Run(parentNode.getDocument(), DEFAULT_CONTENT_IF_NO_PLACEHOLDER);
        run.setRunPr(AttrCollectionExtension.deepClone(runPr));
        runParent.appendChild(run);
    }

    static void setDisplayText(StructuredDocumentTag sdt, String value)
    {
        Run r = new Run(sdt.fetchDocument(), resolveLineBreaks(value));
        insertContent(sdt, r, true);
    }

    /// <summary>
    /// Returns true, if a specified paragraph can be removed from bound document while updating SDT.
    /// </summary>
    /// <remarks>
    /// When updating SDT from bound document we mimic Word behavior and remove last empty paragraph
    /// that also meets some other requirements.
    /// </remarks>
    static boolean canRemove(Paragraph paragraph)
    {
        return canOverrideAttrs(paragraph) && paragraph.isEmptyOrContainsOnlyCrossAnnotation();
    }

    /// <summary>
    /// Returns true, if attributes of a specified paragraph can be overriden with attributes of reference paragraph
    /// while updating SDT with bound document.
    /// </summary>
    /// <remarks>
    /// When updating SDT from bound document we mimic Word behavior by overriding attributes of last paragraph
    /// in bound document with attributes of reference node before which the bound document is inserted.
    /// </remarks>
    private static boolean canOverrideAttrs(Paragraph paragraph)
    {
        if (paragraph == null)
            return false;

        if (paragraph.isListItemOriginal() || paragraph.isListItemFinal())
            return false;

        return true;
    }

    /// <summary>
    /// Inserts document inside sdt.
    /// </summary>
    private static Node insertDocument(StructuredDocumentTag sdt, Document doc)
    {
        Debug.assertTrue((sdt != null) && (doc != null));

        if (sdt.getLevel() == MarkupLevel.ROW)
            return insertDocumentAtRowLevel(sdt, doc);

        Paragraph lastPara = doc.getLastSection().getBody().getLastParagraph();
        boolean canOverrideAttrs = canOverrideAttrs(lastPara);

        // AM. Some of the below comments may be not relevant due to logic changes.
        // WORDSNET-21636 Word deletes the last paragraph if it's empty, so do we.
        // WORDSNET-23213 Word treats numbered paragraphs as non-empty.
        // WORDSNET-23284 Word also doesn't remove an empty paragraph when it has some built-in style.

        // WORDSNET-22815 Microsoft has changed XmlMapping logic and now last paragraph is not deleted except
        // the case with empty document. We need to insert placeholder, that's why paragraph should be removed.
        if (isDocumentEmpty(doc))
            lastPara.remove();

        // WORDSNET-25442 Move out SDT in case of updating inline SDT from multiline document.
        boolean movedOut = false;
        if ((sdt.getLevel() == MarkupLevel.INLINE) && (sdt.getSdtType() == SdtType.RICH_TEXT) && isMultilineDocument(doc))
        {
            sdt.convertToBlock();
            movedOut = true;
        }
        else
        {
            // WORDSNET-26027 Preserve last SDT paragraph if SDT is the last child of story.
            // There is some inconsistency in Word behavior, see related tests.
            Story parentStory = (Story)sdt.getAncestor(Story.class);
            Paragraph lastSdtPara = ms.as(sdt.getLastChild(), Paragraph.class);
            if ((lastSdtPara != null) && (sdt == parentStory.getLastChild()))
            {
                parentStory.insertAfter(lastSdtPara, sdt);
                lastSdtPara.removeAllChildren();
            }
        }

        // We need node inside SDT to insert document before.
        Node refNode = createReferenceNode(sdt);

        // If referenceNode is null, nothing to do.
        if (refNode == null)
            return null;

        DocumentBuilder builder = new DocumentBuilder(sdt.fetchDocument());
        builder.moveTo(refNode);

        ImportFormatOptions importOptions = new ImportFormatOptions();
        {
            // WORDSNET-23889 Use "UseExistingLists" to avoid creating new lists each time an SDT is updated.
            importOptions.setUseExistingLists(true);
            importOptions.setRenameDuplicateBookmarks(false);
        }

        // WORDSNET-27335 Update target document annotation id counter before XML mapping update.
        builder.getDocument().updateAnnotationId();

        Node newNode = builder.insertDocument(doc, ImportFormatMode.USE_DESTINATION_STYLES, importOptions);

        // WORDSNET-23284 Word overrides attributes of last paragraph only when this paragraph is not deleted,
        // is not numbered and its style is not built-in.
        if (isCellOrBlockLevel(sdt.getLevel()) && canOverrideAttrs && !lastPara.isRemoved())
        {
            // The SDT content now consists of 2 parts: the child nodes from the SDT xml and the referenceNode.
            // If the SDT is at the cell or block level, then the referenceNode is a Paragraph.
            // We need to set the referenceNode attributes to the last paragraph of the inserted nodes.
            Paragraph para = ms.as(refNode.previousSiblingOfType(NodeType.PARAGRAPH), Paragraph.class);
            if (para != null)
            {
                Paragraph refPara = (Paragraph)refNode;

                // WORDSNET-21636 Copy formatting only if there is at least one property. In other words, we do should
                // not reset formatting. Setting empty ParaPr actually is the same as remove all formatting.
                // WORDSNET-23284 Actually, there seems more complex behavior in Word. Something similar to
                // importing document with KeepSourceFormatting mode. But at the moment, we just copy attributes.
                if (movedOut)
                    copyProperties(refPara, para);

                // WORDSNET-21436 Copying ParaId and TextId.
                copyIdentifiers(refPara, para);
            }
        }

        // WORDSNET-22091 Keep the referenceNode if the previous node is a table.
        if (!(refNode.getPrevNode() instanceof Table))
        {
            // WORDSNET-23284 Word has special logic when removing reference node in a cell-level SDT.
            if (sdt.getLevel() == MarkupLevel.CELL)
            {
                if (!lastPara.isRemoved() && canOverrideAttrs)
                    refNode.remove();
            }
            else
            {
                if (isOnlyParagraphInSection(refNode))
                {
                    // WORDSNET-23370 Keep reference paragraph to ensure that section has at least one.
                    //
                    // AM. There is total mess in which paragraph we delete or keep,
                    // it could be reference or last paragraph in document being inserted,
                    // sometimes we copy attributes which means we actually remove wrong paragraph, etc.
                    //
                    // It feels this logic should be reconsidered and simplified.
                }
                else
                    refNode.remove();
            }
        }

        return newNode;
    }

    /// <summary>
    /// Updates row-level SDT from mapped document.
    /// </summary>
    private static Row insertDocumentAtRowLevel(StructuredDocumentTag sdt, Document doc)
    {
        sdt.removeAllChildren();
        Row row = ms.as(doc.getChild(NodeType.ROW, 0, true), Row.class);

        if (row != null)
        {
            Row newRow = (Row)row.deepClone(true);
            newRow.setDocument(sdt.getDocument());

            sdt.appendChild(newRow);

            // Copy following bookmarks.
            Node node = row.getNextSibling();
            while(node instanceof IBookmarkNode)
            {
                Node newBookmarkNode = node.deepClone(true);
                newBookmarkNode.setDocument(sdt.getDocument());

                sdt.appendChild(newBookmarkNode);

                node = node.getNextSibling();
            }
        }

        return row;
    }

    /// <summary>
    /// Checks if document has more than one line.
    /// </summary>
    private static boolean isMultilineDocument(Document doc)
    {
        return (doc.getFirstSection().getBody().getParagraphs().getCount() > 1) || (doc.getFirstSection().getBody().getTables().getCount() > 0);
    }

    /// <summary>
    /// Checks that given document is empty.
    /// </summary>
    private static boolean isDocumentEmpty(Document doc)
    {
        if (doc.getSections().getCount() > 1)
            return false;

        if (doc.getFirstSection().getBody().getParagraphs().getCount() > 1)
            return false;

        if (doc.getFirstSection().getBody().getTables().getCount() > 0)
            return false;

        if (doc.getFirstSection().getBody().getFirstParagraph().getCount() > 0)
            return false;

        return true;
    }

    /// <summary>
    /// Checks that given node is only paragraph in section.
    /// </summary>
    private static boolean isOnlyParagraphInSection(Node refNode)
    {
        if (refNode.getNodeType() != NodeType.PARAGRAPH)
            return false;

        if (refNode.getParentNode().getNodeType() != NodeType.BODY)
            return false;

        Body body = (Body)refNode.getParentNode();
        return body.getParentSection().getChildNodes(NodeType.PARAGRAPH, true).getCount() == 1;
    }

    /// <summary>
    /// It seems MS Word replaces all line breaks with soft line break (\v). This method does the same.
    /// </summary>
    private static String resolveLineBreaks(String input)
    {
        return input
            .replace(ControlChar.CR_LF, ControlChar.LINE_BREAK)
            .replace(ControlChar.CR, ControlChar.LINE_BREAK)
            .replace(ControlChar.LF, ControlChar.LINE_BREAK);
    }

    /// <summary>
    /// Returns the last paragraph of the specified structured document tag.
    /// </summary>
    private static Paragraph getLastParagraph(StructuredDocumentTag sdt)
    {
        // NodeCollection skips SDTs, let's find in a cycle.
        Node paragraph = sdt.getLastChild();
        while (paragraph != null && (paragraph.getNodeType() != NodeType.PARAGRAPH))
            paragraph = paragraph.getPreviousSibling();
        return (Paragraph)paragraph;
    }

    /// <summary>
    /// Clears contents of the specified cell of the structured document tag and inserts a placeholder or
    /// default contents.
    /// </summary>
    private static void clearCell(Cell cell, StructuredDocumentTag sdt)
    {
        // Keep paragraph formatting.
        Paragraph formatSourceParagraph = (Paragraph)cell.getChild(NodeType.PARAGRAPH, -1, false);

        cell.removeAllChildren();
        if (sdt.getPlaceholder() != null)
            cell.appendChild(sdt.getDocument().importNode(sdt.getPlaceholder().getFirstSection().getBody().getFirstParagraph(), true));
        else
            insertContentForEmptySdt(cell, sdt.getContentsRunPr(), true, sdt.getEndCharacterRunPr());

        if (formatSourceParagraph != null)
        {
            Paragraph paragraph = (Paragraph)cell.getChild(NodeType.PARAGRAPH, 0, false);
            if (paragraph != null)
            {
                paragraph.setParaPr(AttrCollectionExtension.deepClone(formatSourceParagraph.getParaPr()));
                paragraph.setParagraphBreakRunPr(AttrCollectionExtension.deepClone(formatSourceParagraph.getParagraphBreakRunPr()));
            }
        }
    }

    /// <summary>
    /// Creates and finds out correct place for the reference node depending on SDT MarkupLevel.
    /// </summary>
    private static Node createReferenceNode(StructuredDocumentTag sdt)
    {
        // For Block, Inline and Cell remove all child nodes and replace them with a new node.
        switch (sdt.getLevel())
        {
            case MarkupLevel.BLOCK:
                return replaceChildrenWithParagraph(sdt);
            case MarkupLevel.INLINE:
                return replaceChildrenWithNode(sdt, new Run(sdt.getDocument()));
            case MarkupLevel.CELL:
            {
                // WORDSNET-21138 We need to remove all child nodes from the Cell
                // but keep the properties of the last paragraph (if it exists).
                return replaceChildrenWithParagraph((CompositeNode)sdt.getChild(NodeType.CELL, 0, false));
            }
            // After WORDSNET-9813 will be fixed we can came back to implement this case.
            case MarkupLevel.ROW:
            default:
                throw new IllegalStateException("Unknown markup level.");
        }
    }

    /// <summary>
    /// Creates a reference node, replacing the ancestor's child nodes with an empty paragraph.
    /// If the last paragraph of the replaced child nodes has got properties,
    /// stores them in the inserted paragraph.
    /// </summary>
    private static Node replaceChildrenWithParagraph(CompositeNode ancestor)
    {
        if (ancestor == null)
            return null;

        Paragraph para = ms.as(ancestor.getLastNonAnnotationChild(), Paragraph.class);
        if (para != null)
            para.getChildNodes(NodeType.ANY, false).clear();
        else
            para = new Paragraph(ancestor.getDocument());

        return replaceChildrenWithNode(ancestor, para);
    }

    /// <summary>
    /// Replaces child nodes of the ancestor with specified node.
    /// </summary>
    private static Node replaceChildrenWithNode(CompositeNode ancestor, Node newChild)
    {
        Debug.assertTrue((ancestor != null) && (newChild != null));

        ancestor.getChildNodes(NodeType.ANY, false).clear();
        return ancestor.appendChild(newChild);
    }

    /// <summary>
    /// Inserts content from placeholder building block into sdtContent.
    /// </summary>
    private static void insertPlaceholderContent(StructuredDocumentTag sdt)
    {
        Debug.assertTrue((sdt != null) && (sdt.getPlaceholder() != null));

        boolean isRowLevel = (sdt.getLevel() == MarkupLevel.ROW);

        // WORDSNET-21607 We need to use the same logic as when inserting a document.
        Node refNode = isRowLevel ? null : createReferenceNode(sdt);

        for (Paragraph para : (Iterable<Paragraph>) sdt.getPlaceholder().getFirstSection().getBody().getParagraphs())
        {
            boolean clearChildNodes = isRowLevel && para.isFirstChild();

            if (para.isLastChild())
            {
                // Prevent modification of the original paragraph.
                Paragraph paraClone = (Paragraph)para.deepClone(true);
                paraClone.getParaPr().clear();

                Node newNode = insertContent(sdt, paraClone, clearChildNodes);

                // Copy the 'refNode' properties and identifiers to the last paragraph.
                // WORDSNET-24116 Apply formatting AFTER content has been imported.
                if (isCellOrBlockLevel(sdt.getLevel()) && (refNode != null))
                {
                    Paragraph refPara = (Paragraph)refNode;
                    Paragraph newPara = (Paragraph)newNode;
                    copyProperties(refPara, newPara);
                    copyIdentifiers(refPara, newPara);
                }
            }
            else
            {
                insertContent(sdt, para, clearChildNodes);
            }
        }

        if (refNode != null)
            refNode.remove();
    }

    /// <summary>
    /// Inserts default content for <see cref="SdtType.Picture"/> inside sdtContent.
    /// </summary>
    private static void insertPictureDefaultContent(StructuredDocumentTag sdt)
    {
        Paragraph container = new Paragraph(sdt.getDocument());
        container.appendChild(getPlaceholderDrawingFromInternalResource(sdt.getDocument()));
        insertContent(sdt, container, true);
    }

    /// <summary>
    /// This method is using non-imported nodes contrary to <see cref="InsertBlockContent"/> and others,
    /// because here we first need to figure out which part of the content to import.
    /// Then the required part is selected (run or paragraph) and then imported and inserted as usual.
    /// </summary>
    private static Node insertInlineContent(StructuredDocumentTag sdt, Node content, Node referenceNode)
    {
        Node newNode = null;

        if (NodeUtil.isBlockLevelNode(content) && content.isComposite())
        {
            CompositeNode node = (CompositeNode)content;

            // WORDSNET-11759 Fixed situation when Placeholder contains paragraph but doesn't contain run with content.
            if (node.getFirstChild() != null)
            {
                newNode = sdt.getDocument().importNode(node.getFirstChild(), false);

                // By default all placeholders contain para as wrappers, so we take the first run if sdt is inline.
                sdt.appendChild(newNode);
            }
        }
        else if (NodeUtil.isInlineLevelNode(content))
        {
            newNode = sdt.getDocument().importNode(content, false);

            sdt.insertBefore(newNode, referenceNode);
        }
        else
        {
            throw new IllegalStateException("Cannot insert such node at Inline level.");
        }

        return newNode;
    }

    private static void insertBlockContent(StructuredDocumentTag sdt, Node content)
    {
        if (NodeUtil.isBlockLevelNode(content))
        {
            sdt.appendChild(content);
        }
        else if (NodeUtil.isInlineLevelNode(content))
        {
            insertInlineNode(content, sdt);
        }
        else
        {
            throw new IllegalStateException("Cannot insert such node at Block level.");
        }
    }

    /// <summary>
    /// Handles insertion of cell/row - level content into sdt. Does wrapping of block elements if needed.
    /// Wrapping allows insertion of block content from placeholder building block into row/cell sdt.
    /// </summary>
    private static void insertCellRowContent(StructuredDocumentTag sdt, Node content)
    {
        boolean isRowLevel = (sdt.getLevel() == MarkupLevel.ROW);
        boolean isCellLevel = (sdt.getLevel() == MarkupLevel.CELL);

        if ((isRowLevel && NodeUtil.isRowLevelNode(content)) || (isCellLevel && NodeUtil.isCellLevelNode(content)))
        {
            sdt.appendChild(content);
        }
        else if (NodeUtil.isBlockLevelNode(content))
        {
            CompositeNode wrapper;

            if (isCellLevel)
            {
                if (sdt.hasChildNodes() && (sdt.getFirstChild().getNodeType() == NodeType.CELL))
                {
                    // Use existing cell, do not create wrapper.
                    wrapper = (CompositeNode)sdt.getFirstChild();
                }
                else
                {
                    wrapper = new Cell(sdt.getDocument());
                    sdt.appendChild(wrapper);
                }
            }
            else
            {
                wrapper = new Row(sdt.getDocument());
                sdt.appendChild(wrapper);
            }

            if (isRowLevel && !NodeUtil.isCellLevelNode(content)) // wrap content into cell so we can put it inside row.
            {
                Cell innerWrapper = new Cell(sdt.getDocument());
                wrapper.appendChild(innerWrapper);
                wrapper = innerWrapper;
            }

            wrapper.appendChild(content);
        }
        else if (NodeUtil.isInlineLevelNode(content))
        {
            insertInlineNode(content, sdt);
        }
        else
        {
            throw new IllegalStateException("Can not insert node at this level.");
        }
    }

    /// <summary>
    /// Inserts inline node into the paragraph if it exists. If not, creates new paragraph and inserts node into it.
    /// </summary>
    private static void insertInlineNode(Node content, StructuredDocumentTag sdt)
    {
        // Only inline content nodes is expected.
        if (NodeUtil.isBlockLevelNode(content) && !NodeUtil.isInlineLevelNode(content))
            throw new IllegalArgumentException("Only inline content nodes is expected.");

        Paragraph paragraph = (Paragraph)sdt.getChild(NodeType.PARAGRAPH, 0, true);
        if (paragraph == null)
        {
            paragraph = new Paragraph(sdt.getDocument());
            sdt.appendChild(paragraph);
        }

        // WORDSNET-26045 Try to append content BiDi aware.
        if (!appendRunBiDiAware(paragraph, ms.as(content, Run.class)))
            paragraph.appendChild(content);
    }

    /// <summary>
    /// Appends a specified run to a specified paragraph BiDi aware.
    /// </summary>
    /// <remarks>
    /// Splits passed run using <see cref="BidiAlgorithm.Apply(string,bool)"/> and then
    /// splits by <see cref="CharacterClassifier.IsRtlScript"/> attribute to mimic Word behavior.
    /// </remarks>
    /// <returns>True, if appended successfully.</returns>
    private static boolean appendRunBiDiAware(Paragraph paragraph, Run run)
    {
        if (run == null)
            return false;

        BidiRun[] bidiRuns = BidiAlgorithm.apply(run.getText(), paragraph.getParaPr().getBidi());
        for (BidiRun bidiRun : bidiRuns)
        {
            Run bidiAwareRun;
            // Word also splits text in SDT by rtl script,
            // if there are more than one BiDi run and it is RTL.
            if ((bidiRuns.length > 1) && bidiRun.getRtl())
            {
                ArrayList<BidiRun> subRuns = splitByRtlScript(bidiRun.getText());
                for (BidiRun subRun : subRuns)
                {
                    bidiAwareRun = new Run(run.getDocument(), subRun.getText(), AttrCollectionExtension.deepClone(run.getRunPr()));
                    // Set BiDi property only if it has been changed to avoid writing of extra BiDi property.
                    if (bidiAwareRun.getRunPr().getBidi().toBool() != subRun.getRtl())
                        bidiAwareRun.getRunPr().setBidi(AttrBoolEx.fromBool(subRun.getRtl()));
                    paragraph.appendChild(bidiAwareRun);
                }
            }
            else
            {
                bidiAwareRun = new Run(run.getDocument(), bidiRun.getText(), AttrCollectionExtension.deepClone(run.getRunPr()));
                // Set BiDi property only if it has been changed to avoid writing of extra BiDi property.
                if (bidiAwareRun.getRunPr().getBidi().toBool() != bidiRun.getRtl())
                    bidiAwareRun.getRunPr().setBidi(AttrBoolEx.fromBool(bidiRun.getRtl()));
                paragraph.appendChild(bidiAwareRun);
            }
        }

        return true;
    }

    /// <summary>
    /// Splits text within a specified Run by <see cref="CharacterClassifier.IsRtlScript"/> attribute.
    /// </summary>
    private static ArrayList<BidiRun> splitByRtlScript(String text)
    {
        ArrayList<BidiRun> bidiRuns = new ArrayList<BidiRun>();

        if (text.length() == 0)
            return bidiRuns;

        BidiRun bidiRun;
        boolean isRtl = CharacterClassifier.isRtlScript(text.charAt(0));
        int start = 0;
        for (int i = 1; i < text.length(); i++)
        {
            if (CharacterClassifier.isRtlScript(text.charAt(i)) == isRtl)
                continue;

            // Found text with different rtl script.
            bidiRun = new BidiRun(text.substring((start), (start) + (i - start)), isRtl ? 1 : 0);
            msArrayList.add(bidiRuns, bidiRun);

            isRtl = !isRtl;
            start = i;
        }

        // Proceed with very last piece of Run text.
        bidiRun = new BidiRun(text.substring((start), (start) + (text.length() - start)), isRtl ? 1 : 0);
        msArrayList.add(bidiRuns, bidiRun);

        return bidiRuns;
    }

    /// <summary>
    /// Extract DrawingML placeholder for image Sdt from internally stored docx.
    /// This is done due to two limitations:
    /// 1) MS Word only expects DrawingML inside SdtContent of <see cref="SdtType.Picture"/>.
    /// 2) We only have ctors in DrawingML that allow reading from XmlDocument. So we have
    /// to read something pre-created rather than create new DrawingML on the fly.
    ///
    /// There are no limitations now.
    /// </summary>
    private static Shape getPlaceholderDrawingFromInternalResource(DocumentBase dstDocument)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	Node dml;

        	final String RESOURCE_NAME = "Aspose.Words.Resources.SdtImageDefaultContent.docx";
        	Stream stream = ResourceUtil.fetchResourceStream(RESOURCE_NAME);
        	try /*JAVA: was using*/
        	{
            	// alexnosk WORDSNET-19906 Specify LoadFormat.Docx explicitly to skip file format detection to minimize document creation time.
            	// Since we are sure SdtImageDefaultContent.docx is not encrypted DOCX file, it is safe to specify it explicitly.
            	LoadOptions loadOptions = new LoadOptions();
            	loadOptions.setLoadFormat(LoadFormat.DOCX);
            	Document doc = new Document(stream, loadOptions, false);
            	dml = doc.getChild(NodeType.SHAPE, 0, true);
        	}
        	finally { if (stream != null) stream.close(); }

        	return (Shape)dstDocument.importNode(dml, true);
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Copy properties from the specified Paragraph to another.
    /// </summary>
    private static void copyProperties(Paragraph paraFrom, Paragraph paraTo)
    {
        Debug.assertTrue((paraFrom != null) && (paraTo != null));
        paraTo.setParaPr(AttrCollectionExtension.deepClone(paraFrom.getParaPr()));
    }

    /// <summary>
    /// Copy ParaId and TextId from the specified Paragraph to another.
    /// </summary>
    private static void copyIdentifiers(Paragraph paraFrom, Paragraph paraTo)
    {
        Debug.assertTrue((paraFrom != null) && (paraTo != null));
        paraTo.setParaId(paraFrom.getParaId());
        paraTo.setTextId(paraFrom.getTextId());
    }

    private static boolean isCellOrBlockLevel(/*MarkupLevel*/int markupLevel)
    {
        return (markupLevel == MarkupLevel.CELL) || (markupLevel == MarkupLevel.BLOCK);
    }

    /// <summary>
    /// Converts MS Word date display mask to C# DateTime format string.
    /// See ISO 29500 §17.5.2.8 dateFormat (Date Display Mask) for details.
    /// </summary>
    private static String convertFormatString(String format)
    {
        // Note date is converted to local time as Word does.
        // WORDSNET-10270 The "tt" custom format specifier represents the entire AM/PM designator.
        // Also, replace it case-insensitive.
        String result = Regex.replace(format, "AM/PM", "tt", RegexOptions.IGNORE_CASE);

        // WORDSNET-25629 Spec allows a case insensitive year format.
        result = result.replace("YY", "yy");

        return result;
    }

    static final String DEFAULT_CONTENT_IF_NO_PLACEHOLDER = "     ";
}

