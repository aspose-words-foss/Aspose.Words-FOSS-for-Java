// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.NullableInt32;


/// <summary>
/// Implements the INDEX field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Builds an index using the index entries specified by XE fields, and inserts that index at this place in the document.
/// </remarks>
public class FieldIndex extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        // Ensure that field code is valid.
        FieldIndexValidateResult validateResult = validate(getUpdater());
        if (validateResult.isError())
            return new FieldUpdateActionInsertErrorMessage(this, validateResult.getErrorMessage());

        getUpdater().reflowLayout();

        // An INDEX field result influences on the layout of XE fields located after it in the document.
        //
        // MS Word calculates page numbers for such XE fields before an INDEX field update and uses them during the update.
        // By this reason page numbers in INDEX field results after the first (when there is no result) and any subsequent
        // update (when there is an old result) may be different. Let's mimic this behavior by page numbers' calculating
        // before the old field result removal.

        FieldCodeIndex fieldCode = validateResult.getFieldCode();

        FieldSeqDataProvider fieldSeqDataProvider = fieldCode.hasSequenceName()
            ? getUpdater().getDataProviders().ensure(new FieldSeqDataProvider(getUpdater()))
            : null;

        // Build an index entry/subentry tree based on the whole document or its part contained within the specified
        // bookmark if any.
        IndexEntry rootEntry;
        if (fieldCode.hasBookmarkName())
        {
            Bookmark bookmark = validateResult.getBookmark();

            // Do not use Bookmark.GetNodeRange() since MS Word processes table column bookmarks as common ones here.
            NodeRange bookmarkRange = new NodeRange(bookmark.getBookmarkStart(), false, bookmark.getBookmarkEnd(), false);
            rootEntry = IndexEntry.getRootEntry(fieldCode, fieldSeqDataProvider, bookmarkRange);
        }
        else
        {
            rootEntry = IndexEntry.getRootEntry(fieldCode, fieldSeqDataProvider, getDocument());
        }

        if (!rootEntry.hasSubentries())
            return new FieldUpdateActionInsertErrorMessage(this, "No index entries found.");

        // An INDEX field result is quite complex, so let's build it right away.
        IDisposable iDisposableResource = getUpdateContext().removeOldResultSafe();
        try /*JAVA: was using*/
        {
            DocumentBuilder builder = FieldIndexAndTablesUtil.getDocumentBuilder(this);
            FieldIndexAndTablesUtil.convertOuterInlineSdtToBlock(this);

            SectPr initialColumnsSectPr = null;

            if (fieldCode.hasNumberOfColumns())
            {
                // If a number of columns is provided then remember the columns settings (count, spacing, widths) for the current section
                // and insert a section break with the provided number of columns. Note, that it should be done regardless of
                // whether the provided number of columns is equal to the current one or not. MS Word behavior.

                SectPr indexColumnsSectPr = new SectPr();
                indexColumnsSectPr.setColumnsCount(fieldCode.getNumberOfColumns());
                indexColumnsSectPr.setColumnsSpacing(720); // MS Word uses this value here.

                initialColumnsSectPr = insertSection(builder, indexColumnsSectPr);
            }

            // Write the entry/subentry tree.
            mWriteContext = new FieldIndexWriteContext(fieldCode, builder);
            writeSubentries(null, rootEntry, IndexEntry.MIN_SUBENTRY_LEVEL);
            mWriteContext = null;

            if (fieldCode.hasNumberOfColumns())
            {
                // Insert a section break with the old columns settings.
                builder.moveTo(getEnd());
                insertSection(builder, initialColumnsSectPr);
            }
        }
        finally { if (iDisposableResource != null) iDisposableResource.dispose(); }

        // Everything is done, simply return empty action.
        return new FieldUpdateActionDoNothing(this);
    }

    /// <summary>
    /// Checks whether the field code provides valid arguments for the field to be updated.
    /// </summary>
    /// <param name="updater">
    /// A <see cref="FieldUpdater"/> instance.
    /// It is passed as an argument as the method can be used while updating of another field (typically SEQ).
    /// </param>
    /// <returns>
    /// True if all of the field code arguments are valid.
    /// </returns>
    private FieldIndexValidateResult validate(FieldUpdater updater) throws Exception
    {
        String errorMessage = null;
        FieldCodeIndex fieldCode = null;
        Bookmark bookmark = null;

        if (!hasBodyAsStoryAncestor(getStart()))
        {
            // An INDEX field can not be contained outside a document body (even in shapes within a body).
            errorMessage = "Error! Index not allowed in footnote, endnote, header, footer, or comment.";
        }
        else
        {
            // Cache FieldIndex properties as they are used multiple times during the update.
            fieldCode = new FieldCodeIndex(this);

            if (fieldCode.hasLetterRange() && !fieldCode.isLetterRangeValid())
            {
                errorMessage = "Error! Not a valid range of characters.";
            }
            else if (fieldCode.hasBookmarkName())
            {
                // Check whether a bookmark with the specified name exists in a document body.
                // Note, that even if the bookmark exists in another story, it still can not be used.
                // SPEED Get a bookmark from a cache.
                bookmark = updater.getCachedBookmark(fieldCode.getBookmarkName());
                if ((bookmark != null) && !hasBodyAsStoryAncestor(bookmark.getBookmarkStart()))
                    bookmark = null;

                if (bookmark == null)
                    errorMessage = Bookmark.ERROR_BOOKMARK_NOT_DEFINED;
            }
        }

        return new FieldIndexValidateResult(errorMessage, fieldCode, bookmark);
    }

    /// <summary>
    /// A shortcut method.
    /// </summary>
    private static boolean hasBodyAsStoryAncestor(Node node)
    {
        return node.getStoryAncestor(NodeType.BODY) != null;
    }

    /// <summary>
    /// Insert a continuous section break with the specified columns settings.
    /// </summary>
    private static SectPr insertSection(DocumentBuilder builder, SectPr columnsSectPr)
    {
        StructuredDocumentTag.convertOuterSdtsToRanges(builder.getCurrentNode());

        SectPr oldSectPr = new SectPr();
        builder.getCurrentSection().getSectPr().mirrorTo(
            oldSectPr,
            SectAttr.COLUMNS,
            SectAttr.COLUMNS_COUNT,
            SectAttr.COLUMNS_SPACING,
            SectAttr.COLUMNS_EVENLY_SPACED);

        builder.insertSection(SectionStart.CONTINUOUS);

        SectPr newSectPr = builder.getCurrentSection().getSectPr();
        columnsSectPr.mirrorTo(
            newSectPr,
            SectAttr.COLUMNS,
            SectAttr.COLUMNS_COUNT,
            SectAttr.COLUMNS_SPACING,
            SectAttr.COLUMNS_EVENLY_SPACED);
        columnsSectPr.expandToInclusive(newSectPr, SectAttr.COLUMNS); // Deep clone columns (complex attr).

        return oldSectPr;
    }

    /// <summary>
    /// Writes subentries for the given index entry.
    /// </summary>
    private void writeSubentries(Node dummyRefNode, IndexEntry entry, int subentryLevel) throws Exception
    {
        if (!entry.hasSubentries())
            return;

        boolean isMinSubentryLevel = subentryLevel == IndexEntry.MIN_SUBENTRY_LEVEL;
        boolean needWriteHeading = isMinSubentryLevel && mWriteContext.getFieldCode().hasHeading();
        /*StyleIdentifier*/int styleIdentifier = getStyleIdentifier(subentryLevel);
        char lastStartChar = Character.MIN_VALUE;

        for (int index = 0; index < entry.getSubentryCount(); index++)
        {
            String subentryText = entry.getSubentryText(index);
            int startCharIndex = StringUtil.indexOfNonWhitespace(subentryText);

            // Do not write an empty subentry. But write its subentries though. MS Word behavior.
            if (startCharIndex == -1)
            {
                // Ensure that a subentry of the min level is not empty which should be guaranteed
                // by processing of valid entries only (see FieldXE.HasValidText).
                Debug.assertTrue(!isMinSubentryLevel);

                writeSubentries(dummyRefNode, entry.getSubentry(index), subentryLevel + 1);
                continue;
            }

            if (needWriteHeading)
            {
                char startChar = msChar.toUpperInvariant(subentryText.charAt(startCharIndex));

                // Write a heading only if the start character is changed.
                if (lastStartChar != startChar)
                {
                    writeHeading(startChar);
                    lastStartChar = startChar;
                }
            }

            writeSubentry(dummyRefNode, entry, subentryLevel, index, styleIdentifier);
        }
    }

    /// <summary>
    /// Gets an index style identifier for the given subentry level.
    /// </summary>
    private static /*StyleIdentifier*/int getStyleIdentifier(int subentryLevel)
    {
        switch (subentryLevel)
        {
            case 1:
                return StyleIdentifier.INDEX_1;
            case 2:
                return StyleIdentifier.INDEX_2;
            case 3:
                return StyleIdentifier.INDEX_3;
            case 4:
                return StyleIdentifier.INDEX_4;
            case 5:
                return StyleIdentifier.INDEX_5;
            case 6:
                return StyleIdentifier.INDEX_6;
            case 7:
                return StyleIdentifier.INDEX_7;
            case 8:
                return StyleIdentifier.INDEX_8;
            case 9:
                return StyleIdentifier.INDEX_9;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "subentryLevel");
        }
    }

    /// <summary>
    /// Writes a heading for the given start character.
    /// </summary>
    private void writeHeading(char startChar) throws Exception
    {
        FieldIndexAndTablesUtil.setUpEntryParagraph(this, mWriteContext.getDocumentBuilder(), StyleIdentifier.INDEX_HEADING);
        FieldIndexAndTablesUtil.ensureEntryParagraphTabStop(mWriteContext.getDocumentBuilder(), null);

        Paragraph currentParagraph = mWriteContext.getDocumentBuilder().getCurrentParagraph();
        FieldIndexAndTablesUtil.setFontForTabStopOrParagraphBreak(mWriteContext.getDocumentBuilder().getDocument(), currentParagraph);
        currentParagraph.getParaPr().setKeepWithNext(true);

        String heading = mWriteContext.getFieldCode().getHeading(startChar);
        FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), heading);
    }

    /// <summary>
    /// Writes a subentry of the given entry with the specified index.
    /// </summary>
    private void writeSubentry(
        Node dummyRefNode,
        IndexEntry entry,
        int subentryLevel,
        int subentryIndex,
        /*StyleIdentifier*/int styleIdentifier) throws Exception
    {
        // Initialize a dummy (i.e. temporary) reference node together with containing paragraph if it was not
        // initialized yet. This can happen in the following cases:
        //   - subentries are written on separate paragraphs (i.e. \r switch is not used).
        //   - subentries are written on the same paragraph (i.e. \r switch is used) but we have encountered
        //     a subentry with the min level (i.e. an entry).
        if (dummyRefNode == null)
        {
            FieldIndexAndTablesUtil.setUpEntryParagraph(this, mWriteContext.getDocumentBuilder(), styleIdentifier);
            FieldIndexAndTablesUtil.ensureEntryParagraphTabStop(mWriteContext.getDocumentBuilder(), null);

            dummyRefNode = FieldIndexAndTablesUtil.createAndInsertDummyRefNode(mWriteContext.getDocumentBuilder());
            mWriteContext.getDocumentBuilder().moveTo(dummyRefNode);
        }

        IndexEntry subentry = entry.getSubentry(subentryIndex);

        if (mWriteContext.getFieldCode().getRunSubentriesOnSameLine())
        {
            // Precede a subentry with a non-min level by non-overridable separator.
            if ((subentryIndex == 0) && (subentryLevel == IndexEntry.MIN_SUBENTRY_LEVEL + 1) && !entry.hasPageNumberInfos())
            {
                FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), ": ");
            }
            else if (subentryLevel != IndexEntry.MIN_SUBENTRY_LEVEL)
            {
                FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), "; ");
            }
        }

        Node precedingNode = dummyRefNode.getPreviousSibling();

        copySubentryNodes(subentry.getNodeRange(), dummyRefNode, styleIdentifier);

        Node startNode = (precedingNode != null) ? precedingNode.getNextSibling() : dummyRefNode.getParentNode().getFirstChild();
        Node endNode = dummyRefNode.getPreviousSibling();

        // Subentry nodes should be trimmed. MS Word behavior.
        trimSubentryNodes(startNode, endNode, true);
        trimSubentryNodes(endNode, startNode, false);

        writePageNumbers(dummyRefNode, subentry, styleIdentifier);

        // Remove a dummy reference node if subentries are written on separate paragraphs (i.e. \r switch is not used)
        // and set it to null, it not to be used for subentries of this subentry.
        if (!mWriteContext.getFieldCode().getRunSubentriesOnSameLine())
        {
            dummyRefNode.remove();
            dummyRefNode = null;
        }

        writeSubentries(dummyRefNode, subentry, subentryLevel + 1);

        // If subentries are written on the same paragraph (i.e. \r switch is used) we should remove a dummy refererence
        // node only at the min level i.e. when all of the subentries are written.
        if ((subentryLevel == IndexEntry.MIN_SUBENTRY_LEVEL) && mWriteContext.getFieldCode().getRunSubentriesOnSameLine())
            dummyRefNode.remove();
    }

    /// <summary>
    /// Copies subentry text nodes performing common modifications of their contents and font attributes.
    /// </summary>
    private void copySubentryNodes(NodeRange range, Node refNode, /*StyleIdentifier*/int styleIdentifier) throws Exception
    {
        CompositeModifier compositeModifier = new CompositeModifier(
            new FieldXETextArgumentDecoderNodeModifier(range),
            new IndexEntryAttributeModifier(styleIdentifier, getDocument().getStyles()));

        if (range.getDocument() != getDocument())
        {
            compositeModifier.addModifier(
                new ExternalDocumentModifier(
                    range.getDocument(),
                    getDocument(),
                    ImportFormatMode.USE_DESTINATION_STYLES));
        }

        LinearNodeCopier.copy(range, compositeModifier, refNode);
    }

    /// <summary>
    /// Performs trimming (i.e. removal of whitespaces) of subentry text nodes moving forward or backward.
    /// </summary>
    private static void trimSubentryNodes(Node startNode, Node endNode, boolean isForward)
    {
        Node node = startNode;
        while (node.getNodeType() == NodeType.RUN)
        {
            Run run = (Run)node;

            // The node can be removed below so advance to the next one here.
            node = node.getNearestSibling(isForward);

            // All of the whitespaces are replaced with spaces by FieldXETextArgumentDecoder
            // so check for spaces only as it works faster.
            if (StringUtil.containsOnlySpaces(run.getText()))
            {
                run.remove();
                if (run == endNode)
                    break;
            }
            else
            {
                run.setText(isForward ? msString.trimStart(run.getText()) : msString.trimEnd(run.getText()));
                break;
            }
        }
    }

    /// <summary>
    /// Writes page numbers for the given subentry.
    /// </summary>
    private void writePageNumbers(Node refNode, IndexEntry subentry, /*StyleIdentifier*/int styleIdentifier) throws Exception
    {
        if (!subentry.hasPageNumberInfos())
            return;

        mWriteContext.startWritePageNumbers();

        int lastFirstPageNumber = -1;
        int lastFirstSequenceValue = -1;

        for (IndexEntryPageNumberInfo pageNumberInfo : subentry.getPageNumberInfos())
        {
            // Page number replacements should be written after all of the direct page numbers.
            if (pageNumberInfo.getRefFieldCode().hasPageNumberReplacement())
                continue;

            int firstSequenceValue = 0;
            int lastSequenceValue = 0;

            // Get a sequence value(s) for the page number if any sequence is used to build page numbers.
            if (mWriteContext.getFieldCode().hasSequenceName())
            {
                if (pageNumberInfo.hasPageRange())
                {
                    firstSequenceValue = pageNumberInfo.getFirstSequenceNumber();
                    lastSequenceValue = pageNumberInfo.getLastSequenceNumber();
                }
                else
                {
                    firstSequenceValue = pageNumberInfo.getFirstSequenceNumber();
                }
            }

            // MS Word merges sequential identical single page numbers. Let's call this a merged sequence.
            // Note, that such a sequence can contain a single item.
            //
            // A merged sequence can be interrupted by a valid or invalid page number range (a page number range
            // is considered to be invalid if corresponding bookmark is missing). The first page number of an interrupting
            // valid page range can be merged either if it satisfies the condition of single page numbers.
            //
            // A valid page range which has identical the first and the last page numbers is considered to be a single
            // page number (i.e. it does not break a merged sequence). IndexEntryPageNumberInfo.HasSinglePageRange
            // indicates whether this is the case.
            //
            // Only the first item of a merged sequence is written using corresponding formatting.
            //
            // As we do not know whether a merged sequence is going to be interrupted by a valid page number range,
            // we should write a merged sequence only at the moment of its interruption (not necessarily by a valid page
            // number range). However, page number ranges should be written immediately as they do not form any sequences.
            // That's the explanation of a cumbersome code below.
            if ((pageNumberInfo.getFirstPageNumber() != lastFirstPageNumber) || (firstSequenceValue != lastFirstSequenceValue))
            {
                // A merged sequence is broken, so write it if any.
                writeSinglePageNumber(lastFirstSequenceValue);

                lastFirstPageNumber = pageNumberInfo.getFirstPageNumber();
                lastFirstSequenceValue = firstSequenceValue;
            }

            if (pageNumberInfo.getRefFieldCode().hasPageRangeBookmarkName() && !pageNumberInfo.hasSinglePageRange())
            {
                if (pageNumberInfo.hasPageRange())
                {
                    // Write a valid page number range considering possible merged sequence presence.
                    writePageNumberRange(pageNumberInfo, firstSequenceValue, lastSequenceValue);
                }
                else
                {
                    // A merged sequence is broken, so write it if any.
                    writeSinglePageNumber(firstSequenceValue);

                    // Write an invalid page number range.
                    writePageRangeBookmarkError(pageNumberInfo, firstSequenceValue);
                }
            }
            else if (!mWriteContext.hasCurrentSinglePageNumberInfo())
            {
                // Remember the first item of a merged sequence.
                mWriteContext.setCurrentSinglePageNumberInfo(pageNumberInfo);
            }
        }

        // Write the last merged sequence if any.
        writeSinglePageNumber(lastFirstSequenceValue);

        // And now it's time to write page number replacements.
        writePageNumberReplacements(refNode, subentry, styleIdentifier);
    }

    /// <summary>
    /// Writes a single page number contained within the current write context if any.
    /// </summary>
    private void writeSinglePageNumber(int sequenceValue) throws Exception
    {
        if (!mWriteContext.hasCurrentSinglePageNumberInfo())
            return;

        StringBuilder pageNumberBuilder = getPageNumberBuilder();
        IndexEntryPageNumberInfo pageNumberInfo = mWriteContext.getCurrentSinglePageNumberInfo();

        buildPageNumberBase(pageNumberBuilder, null, pageNumberInfo, pageNumberInfo.getFirstPageNumber(), sequenceValue);
        endWritePageNumber(pageNumberBuilder, pageNumberInfo);
    }

    /// <summary>
    /// Writes a valid page number range. Considers a single page number contained within the current write context if any.
    /// </summary>
    private void writePageNumberRange(IndexEntryPageNumberInfo pageNumberInfo, int firstSequenceValue, int lastSequenceValue) throws Exception
    {
        IndexEntryPageNumberInfo firstPageNumberInfo = mWriteContext.hasCurrentSinglePageNumberInfo()
            ? mWriteContext.getCurrentSinglePageNumberInfo()
            : pageNumberInfo;

        StringBuilder pageNumberBuilder = getPageNumberBuilder();

        buildPageNumberBase(
            pageNumberBuilder,
            null,
            firstPageNumberInfo,
            firstPageNumberInfo.getFirstPageNumber(),
            firstSequenceValue);

        buildPageNumberPart(
            pageNumberBuilder,
            mWriteContext.getFieldCode().getPageRangeSeparator(),
            firstPageNumberInfo.getRefFieldCode(),
            null);

        buildPageNumberBase(
            pageNumberBuilder,
            null,
            pageNumberInfo,
            pageNumberInfo.getLastPageNumber(),
            lastSequenceValue);

        endWritePageNumber(pageNumberBuilder, pageNumberInfo);
    }

    /// <summary>
    /// Writes an invalid (i.e. with missing corresponding bookmark) page number range.
    /// </summary>
    private void writePageRangeBookmarkError(IndexEntryPageNumberInfo pageNumberInfo, int sequenceValue) throws Exception
    {
        StringBuilder pageNumberBuilder = getPageNumberBuilder();
        writePageNumberPart(pageNumberBuilder, null);

        // Save current font properties.
        RunPr runPr = mWriteContext.getDocumentBuilder().getRunPrCopy();

        // Set bold formatting for the error message.
        IRunAttrSource runAttrSource = mWriteContext.getDocumentBuilder();
        runAttrSource.setRunAttr(FontAttr.BOLD, AttrBoolEx.TRUE);
        if (mWriteContext.getDocumentBuilder().getDocument().getFieldOptions().isBidiTextSupportedOnUpdate())
            runAttrSource.setRunAttr(FontAttr.BOLD_BI, AttrBoolEx.TRUE);

        FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), "Error! Not a valid bookmark in entry on page ");

        // Restore font properties.
        mWriteContext.getDocumentBuilder().setFont(runPr, false);

        buildPageNumberBase(
            pageNumberBuilder,
            null,
            pageNumberInfo,
            pageNumberInfo.getFirstPageNumber(),
            sequenceValue);

        endWritePageNumber(pageNumberBuilder, pageNumberInfo);
    }

    /// <summary>
    /// Initializes a <see cref="StringBuilder"/> instance used to build page numbers.
    /// Appends page number or page number list separator to it according to the current write context.
    /// </summary>
    private StringBuilder getPageNumberBuilder()
    {
        String separator = mWriteContext.getUsePageNumberListSeparator()
            ? mWriteContext.getFieldCode().getPageNumberListSeparator()
            : mWriteContext.getFieldCode().getPageNumberSeparator();

        return new StringBuilder(separator);
    }

    /// <summary>
    /// Finalizes writing of a page number (i.e. flushes the buffer and changes the state of the current write context).
    /// </summary>
    private void endWritePageNumber(StringBuilder pageNumberBuilder, IndexEntryPageNumberInfo pageNumberInfo) throws Exception
    {
        writePageNumberPart(pageNumberBuilder, pageNumberInfo.getRefFieldCode());
        mWriteContext.endWritePageNumber();
    }

    /// <summary>
    /// Appends a page number base (i.e. the number itself) to the specified page number builder.
    /// </summary>
    private void buildPageNumberBase(
        StringBuilder pageNumberBuilder,
        FieldCodeXE lastFieldCodeXE,
        IndexEntryPageNumberInfo pageNumberInfo,
        int pageNumber,
        int sequenceValue) throws Exception
    {
        String formattedPageNumber = pageNumberInfo.getRefField().formatPageNumber(pageNumber);
        String part = mWriteContext.getFieldCode().hasSequenceName() ? Integer.toString(sequenceValue) : formattedPageNumber;

        buildPageNumberPart(pageNumberBuilder, part, lastFieldCodeXE, pageNumberInfo.getRefFieldCode());

        if (mWriteContext.getFieldCode().hasSequenceName())
        {
            // These parts use the same text formatting, so append them right away.
            msStringBuilder.append(pageNumberBuilder, mWriteContext.getFieldCode().getSequenceSeparator());
            msStringBuilder.append(pageNumberBuilder, formattedPageNumber);
        }
    }

    /// <summary>
    /// A core method to build any page number part.
    /// Flushes the buffer (i.e. contents of the given page number builder) if text formatting has changed.
    /// </summary>
    private void buildPageNumberPart(
        StringBuilder pageNumberBuilder,
        String part,
        FieldCodeXE lastFieldCodeXE,
        FieldCodeXE currentFieldCodeXE) throws Exception
    {
        if ((needInvertBold(currentFieldCodeXE) != needInvertBold(lastFieldCodeXE)) ||
            (needInvertItalic(currentFieldCodeXE) != needInvertItalic(lastFieldCodeXE)))
        {
            writePageNumberPart(pageNumberBuilder, lastFieldCodeXE);
        }

        msStringBuilder.append(pageNumberBuilder, part);
    }

    /// <summary>
    /// Writes a page number part using text formatting corresponding to the specified XE field code.
    /// </summary>
    private void writePageNumberPart(StringBuilder pageNumberBuilder, FieldCodeXE fieldCodeXE) throws Exception
    {
        if (pageNumberBuilder.length() == 0)
            return;

        // Flush the buffer.
        String pageNumberPart = pageNumberBuilder.toString();
        pageNumberBuilder.setLength(0);

        boolean invertBold = needInvertBold(fieldCodeXE);
        boolean invertItalic = needInvertItalic(fieldCodeXE);

        // If there are no font attrs to invert, write the part right away.
        if (!invertBold && !invertItalic)
        {
            FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), pageNumberPart);
            return;
        }

        // Save current font attrs.
        RunPr runPr = mWriteContext.getDocumentBuilder().getRunPrCopy();

        // Invert font attrs where needed.
        boolean isBidiTextSupported = mWriteContext.getDocumentBuilder().getDocument().getFieldOptions().isBidiTextSupportedOnUpdate();
        if (invertBold)
            invertAttrBoolEx(mWriteContext.getDocumentBuilder(), FontAttr.BOLD);
        if (invertItalic)
            invertAttrBoolEx(mWriteContext.getDocumentBuilder(), FontAttr.ITALIC);
        if (invertBold && isBidiTextSupported)
            invertAttrBoolEx(mWriteContext.getDocumentBuilder(), FontAttr.BOLD_BI);
        if (invertItalic && isBidiTextSupported)
            invertAttrBoolEx(mWriteContext.getDocumentBuilder(), FontAttr.ITALIC_BI);

        FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), pageNumberPart);

        // Restore old font attrs.
        mWriteContext.getDocumentBuilder().setFont(runPr, false);
    }

    private static boolean needInvertBold(FieldCodeXE fieldCodeXE)
    {
        return (fieldCodeXE != null) && fieldCodeXE.isBold();
    }

    private static boolean needInvertItalic(FieldCodeXE fieldCodeXE)
    {
        return (fieldCodeXE != null) && fieldCodeXE.isItalic();
    }

    /// <summary>
    /// Inverts <see cref="AttrBoolEx"/> value for a font attr specified by its key for the given
    /// <see cref="IRunAttrSource"/> instance.
    /// </summary>
    private static void invertAttrBoolEx(IRunAttrSource runAttrSource, int key)
    {
        AttrBoolEx value = (AttrBoolEx)InlineHelper.fetchAttr(runAttrSource, key);
        runAttrSource.setRunAttr(key, value.invert());
    }

    /// <summary>
    /// Writes page number replacements for the given subentry if any.
    /// </summary>
    private void writePageNumberReplacements(Node refNode, IndexEntry subentry, /*StyleIdentifier*/int styleIdentifier) throws Exception
    {
        if (!subentry.hasPageNumberReplacements())
            return;

        for (IndexEntryPageNumberInfo pageNumberInfo : subentry.getPageNumberInfos())
        {
            if (pageNumberInfo.getRefFieldCode().hasPageNumberReplacement())
                writePageNumberReplacement(refNode, pageNumberInfo.getRefFieldCode(), styleIdentifier);
        }
    }

    /// <summary>
    /// Writes a single page number replacement.
    /// </summary>
    private void writePageNumberReplacement(Node refNode, FieldCodeXE fieldCodeXE, /*StyleIdentifier*/int styleIdentifier) throws Exception
    {
        String separator;
        if (mWriteContext.getUsePageNumberListSeparator())
        {
            separator = mWriteContext.getFieldCode().getPageNumberListSeparator();
        }
        else if (hasPageNumberSeparator())
        {
            // MS Word uses page number separator here if it is overridden.
            separator = mWriteContext.getFieldCode().getPageNumberSeparator();
        }
        else
        {
            // MS Word uses cross reference separator only if page number separator is not overridden.
            separator = mWriteContext.getFieldCode().getCrossReferenceSeparator();
        }

        FieldTextHelper.writeTextBidiAware(this, mWriteContext.getDocumentBuilder(), separator);
        copySubentryNodes(fieldCodeXE.getPageNumberReplacementRange(), refNode, styleIdentifier);
        mWriteContext.endWritePageNumber();
    }

    /*override*/ boolean isRtlResultParagraph()
    {
        // Experiments with MS Word show that SectPr.Bidi influences on this.
        Section parentSection = (Section)getStart().getAncestor(NodeType.SECTION);
        return parentSection.getSectPr().getBidi();
    }

    /// <summary>
    /// Returns a value indicating whether the field's code contains any invalid arguments.
    /// </summary>
    /// <param name="updater">
    /// A <see cref="FieldUpdater"/> instance.
    /// It is passed as an argument as the method can be used while updating of another field (typically SEQ).
    /// </param>
    /// <returns>
    /// True if any of the field code arguments is not valid.
    /// </returns>
    boolean isError(FieldUpdater updater) throws Exception
    {
        FieldIndexValidateResult validateResult = validate(updater);
        return validateResult.isError();
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*RUN_SUBENTRIES_ON_SAME_LINE_SWITCH*/0:
            case /*USE_YOMI_SWITCH*/1:
                return FieldSwitchType.FLAG;
            case /*BOOKMARK_NAME_SWITCH*/2:
            case /*NUMBER_OF_COLUMNS_SWITCH*/3:
            case /*SEQUENCE_SEPARATOR_SWITCH*/4:
            case /*PAGE_NUMBER_SEPARATOR_SWITCH*/5:
            case /*ENTRY_TYPE_SWITCH*/6:
            case /*PAGE_RANGE_SEPARATOR_SWITCH*/7:
            case /*HEADING_SWITCH*/8:
            case /*CROSS_REFERENCE_SEPARATOR_SWITCH*/9:
            case /*PAGE_NUMBER_LIST_SEPARATOR_SWITCH*/10:
            case /*LETTER_RANGE_SWITCH*/11:
            case /*SEQUENCE_NAME_SWITCH*/12:
            case /*LANGUAGE_ID_SWITCH*/13:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets or sets the name of the bookmark that marks the portion of the document used to build the index.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getSwitchArgumentAsString(BOOKMARK_NAME_SWITCH); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setSwitch(BOOKMARK_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets the number of columns per page used when building the index.
    /// </summary>
    public String getNumberOfColumns() { return getFieldCodeCache().getSwitchArgumentAsString(NUMBER_OF_COLUMNS_SWITCH); }
    public void setNumberOfColumns(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(NUMBER_OF_COLUMNS_SWITCH, value); }

    boolean hasNumberOfColumnsSwitch() { return getFieldCodeCache().hasSwitch(NUMBER_OF_COLUMNS_SWITCH); }

    NullableInt32 getNumberOfColumnsAsInt32() { return getFieldCodeCache().getSwitchArgumentAsInt32(NUMBER_OF_COLUMNS_SWITCH); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate sequence numbers and page numbers.
    /// </summary>
    public String getSequenceSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(SEQUENCE_SEPARATOR_SWITCH); }
    public void setSequenceSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(SEQUENCE_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate an index entry and its page number.
    /// </summary>
    public String getPageNumberSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_NUMBER_SEPARATOR_SWITCH); }
    public void setPageNumberSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_NUMBER_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets a value indicating whether a page number separator is overridden through the field's code.
    /// </summary>
    public boolean hasPageNumberSeparator() { return getFieldCodeCache().hasSwitch(PAGE_NUMBER_SEPARATOR_SWITCH); }

    /// <summary>
    /// Gets or sets an index entry type used to build the index.
    /// </summary>
    public String getEntryType() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_TYPE_SWITCH); }
    public void setEntryType(String value) throws Exception { getFieldCodeCache().setSwitch(ENTRY_TYPE_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate the start and end of a page range.
    /// </summary>
    public String getPageRangeSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_RANGE_SEPARATOR_SWITCH); }
    public void setPageRangeSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_RANGE_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets a heading that appears at the start of each set of entries for any given letter.
    /// </summary>
    public String getHeading() { return getFieldCodeCache().getSwitchArgumentAsString(HEADING_SWITCH); }
    public void setHeading(String value) throws Exception { getFieldCodeCache().setSwitch(HEADING_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate cross references and other entries.
    /// </summary>
    public String getCrossReferenceSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(CROSS_REFERENCE_SEPARATOR_SWITCH); }
    public void setCrossReferenceSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(CROSS_REFERENCE_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets the character sequence that is used to separate two page numbers in a page number list.
    /// </summary>
    public String getPageNumberListSeparator() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_NUMBER_LIST_SEPARATOR_SWITCH); }
    public void setPageNumberListSeparator(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_NUMBER_LIST_SEPARATOR_SWITCH, value); }

    /// <summary>
    /// Gets or sets a range of letters to which limit the index.
    /// </summary>
    public String getLetterRange() { return getFieldCodeCache().getSwitchArgumentAsString(LETTER_RANGE_SWITCH); }
    public void setLetterRange(String value) throws Exception { getFieldCodeCache().setSwitch(LETTER_RANGE_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether run subentries into the same line as the main entry.
    /// </summary>
    public boolean getRunSubentriesOnSameLine() { return getFieldCodeCache().hasSwitch(RUN_SUBENTRIES_ON_SAME_LINE_SWITCH); }
    public void setRunSubentriesOnSameLine(boolean value) throws Exception { getFieldCodeCache().setSwitch(RUN_SUBENTRIES_ON_SAME_LINE_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of a sequence whose number is included with the page number.
    /// </summary>
    public String getSequenceName() { return getFieldCodeCache().getSwitchArgumentAsString(SEQUENCE_NAME_SWITCH); }
    public void setSequenceName(String value) throws Exception { getFieldCodeCache().setSwitch(SEQUENCE_NAME_SWITCH, value); }

    /// <summary>
    /// Gets a value indicating whether a sequence should be used while the field's result building.
    /// </summary>
    public boolean hasSequenceName() { return getFieldCodeCache().hasSwitch(SEQUENCE_NAME_SWITCH); }

    /// <summary>
    /// Gets or sets whether to enable the use of yomi text for index entries.
    /// </summary>
    public boolean getUseYomi() { return getFieldCodeCache().hasSwitch(USE_YOMI_SWITCH); }
    public void setUseYomi(boolean value) throws Exception { getFieldCodeCache().setSwitch(USE_YOMI_SWITCH, value); }

    /// <summary>
    /// Gets or sets the language ID used to generate the index.
    /// </summary>
    public String getLanguageId() { return getFieldCodeCache().getSwitchArgumentAsString(LANGUAGE_ID_SWITCH); }
    public void setLanguageId(String value) throws Exception { getFieldCodeCache().setSwitch(LANGUAGE_ID_SWITCH, value); }

    private FieldIndexWriteContext mWriteContext;

    private static final String BOOKMARK_NAME_SWITCH = "\\b";
    private static final String NUMBER_OF_COLUMNS_SWITCH = "\\c";
    private static final String SEQUENCE_SEPARATOR_SWITCH = "\\d";
    private static final String PAGE_NUMBER_SEPARATOR_SWITCH = "\\e";
    private static final String ENTRY_TYPE_SWITCH = "\\f";
    private static final String PAGE_RANGE_SEPARATOR_SWITCH = "\\g";
    private static final String HEADING_SWITCH = "\\h";
    private static final String CROSS_REFERENCE_SEPARATOR_SWITCH = "\\k";
    private static final String PAGE_NUMBER_LIST_SEPARATOR_SWITCH = "\\l";
    private static final String LETTER_RANGE_SWITCH = "\\p";
    private static final String RUN_SUBENTRIES_ON_SAME_LINE_SWITCH = "\\r";
    private static final String SEQUENCE_NAME_SWITCH = "\\s";
    private static final String USE_YOMI_SWITCH = "\\y";
    private static final String LANGUAGE_ID_SWITCH = "\\z";

    private static class LinearNodeCopier
    {
        private LinearNodeCopier(NodeRange range, INodeModifier compositeModifier, Node refNode)
        {
            DocumentBase document = refNode.getDocument();

            Run run = new Run(document);
            Paragraph paragraph = new Paragraph(document);
            Body body = new Body(document);
            Section section = new Section(document);

            section.appendChild(body);
            body.appendChild(paragraph);
            paragraph.appendChild(run);

            mDocument = document;
            mVirtualDomRefNode = run;
            mRefNode = refNode;
            mRange = range;
            mCompositeModifier = compositeModifier;
        }

        public static void copy(NodeRange range, INodeModifier compositeModifier, Node refNode) throws Exception
        {
            LinearNodeCopier copier = new LinearNodeCopier(range, compositeModifier, refNode);
            copier.copy();
        }

        private void copy() throws Exception
        {
            NodeRange result = NodeCopier.copyWithoutFields(
                mRange,
                mVirtualDomRefNode,
                mCompositeModifier,
                null,
                true,
                NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR | NodeCopierOptions.SKIP_CROSS_STRUCTURE_ANNOTATIONS | NodeCopierOptions.CLONE_NODE);

            if (result == null)
                return;

            for (Node node : result)
            {
                if (needSkipNode(node))
                    continue;

                switch (node.getNodeType())
                {
                    case NodeType.PARAGRAPH:
                        visitParagraph((Paragraph)node);
                        break;

                    case NodeType.ROW:
                        visitTableRow((Row)node);
                        break;

                    default:
                        if (node instanceof IInline)
                            visitInline(node);

                        break;
                }
            }
        }

        private void visitParagraph(Paragraph paragraph)
        {
            if (mLastTableRow != null && paragraph.getParentRow() != mLastTableRow)
                appendTableRowSeparator();

            appendParagraphSeparator();

            mParagraphSeparator = new Run(mDocument, SEPARATOR, paragraph.getParagraphBreakRunPr());
        }

        private void visitTableRow(Row row)
        {
            appendParagraphSeparator();
            appendTableRowSeparator();

            mTableRowSeparator = new Run(mDocument, SEPARATOR);
            mLastTableRow = row;
        }

        private void visitInline(Node node)
        {
            mLastInline = node;
            appendResultNode(node.deepClone(true));
        }

        private boolean needSkipNode(Node node)
        {
            if (mLastInline == null)
                return false;

            if (!node.isAncestorNode(mLastInline))
                return false;

            return true;
        }

        private void appendParagraphSeparator()
        {
            if (mParagraphSeparator == null)
                return;

            appendResultNode(mParagraphSeparator);

            mParagraphSeparator = null;
        }

        private void appendTableRowSeparator()
        {
            if (mTableRowSeparator == null)
                return;

            appendResultNode(mTableRowSeparator);

            mLastTableRow = null;
            mTableRowSeparator = null;
        }

        private void appendResultNode(Node node)
        {
            mRefNode.insertPrevious(node);
        }

        private Run mParagraphSeparator;
        private Run mTableRowSeparator;
        private Row mLastTableRow;
        private Node mLastInline;

        private /*final*/ Node mVirtualDomRefNode;
        private /*final*/ NodeRange mRange;
        private /*final*/ INodeModifier mCompositeModifier;
        private /*final*/ Node mRefNode;
        private /*final*/ DocumentBase mDocument;

        private static final String SEPARATOR = " ";
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\r",
		"\\y",
		"\\b",
		"\\c",
		"\\d",
		"\\e",
		"\\f",
		"\\g",
		"\\h",
		"\\k",
		"\\l",
		"\\p",
		"\\s",
		"\\z"
	);

}

