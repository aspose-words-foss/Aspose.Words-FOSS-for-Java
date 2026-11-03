// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.LanguageOnly;
import com.aspose.Language;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Provides utility methods shared between <see cref="FieldRef"/>, <see cref="FieldPageRef"/>,
/// <see cref="FieldStyleRef"/> and <see cref="FieldUnknown"/>.
/// </summary>
/*static*/ class FieldRefUtil
{
	/* Simulation of static class by using private constructor */
	private FieldRefUtil()
	{}

    /// <summary>
    /// Returns the specified field's update stage.
    /// </summary>
    static /*FieldUpdateStage*/int getFieldUpdateStage(Field field, String bookmarkName, boolean isParagraphNumber) throws Exception
    {
        // No bookmark name means error. Do not defer error message inserting.
        if (StringUtil.containsOnlyWhitespaces(bookmarkName))
            return FieldUpdateStage.MAIN_LOOP;

        // No bookmark means error. Do not defer error message inserting.
        // SPEED Get a bookmark from a cache.
        Bookmark bookmark = FieldUtil.getCachedBookmark(field, bookmarkName);
        if (bookmark == null)
            return FieldUpdateStage.MAIN_LOOP;

        // WORDSNET-8226 If the referenced bookmark is dynamic (i.e. contained within a SET or ASK field result),
        // then we should update the field right away, because the bookmark's value can be changed in further.
        if (FieldUtil.isDynamicBookmark(bookmark.getBookmarkStart()))
            return FieldUpdateStage.MAIN_LOOP;

        // WORDSNET-6545 Update REF field later if it references a paragraph number.
        if (isParagraphNumber)
            return FieldUpdateStage.DEFERRED_UPDATE_REF;

        boolean containsAnyField = false;
        for (Node node : bookmark.getNodeRange())
        {
            // A special case of bookmark self-reference. See EndFieldUpdate for details.
            if (node == field.getStart())
                return FieldUpdateStage.MAIN_LOOP;

            if (node.getNodeType() == NodeType.FIELD_START)
                containsAnyField = true;
        }

        // WORDSNET-6545 We need to update the field later if it references other fields (which may be updated by that time).
        return containsAnyField ? FieldUpdateStage.DEFERRED_UPDATE_REF : FieldUpdateStage.MAIN_LOOP;
    }

    /// <summary>
    /// Begins update of the specified field. Returns a parent paragraph for the specified bookmark's start.
    /// </summary>
    static Paragraph beginFieldUpdate(
        Field field, Bookmark bookmark,
        boolean isParagraphNumber,
        /*out*/RefBoolean hasBookmarkInResult) throws Exception
    {
        Debug.assertTrue(field != null);
        Debug.assertTrue(bookmark != null);

        // WORDSNET-19495 Below code removes filed result nodes as usual, but if there is a referenced bookmark, removes all but the bookmark.

        NodeRange resultRange = new NodeRange(field.getSeparator(), false, getFieldResultRangeEnd(field), false);
        BookmarkExclusiveNodeRemover nodeRemover = new BookmarkExclusiveNodeRemover(resultRange, bookmark.getName());

        nodeRemover.removeCore();
        hasBookmarkInResult.set(nodeRemover.hasBookmark());

        // List labels should be up-to-date before the final update of the field if it references a paragraph number.
        updateListLabelsIfNeeded(field, isParagraphNumber);

        return getBookmarkParentParagraph(bookmark.getBookmarkStart(), true);
    }

    private static Node getFieldResultRangeEnd(Field field)
    {
       return (getFieldResultRangeEndOverride(field) != null ? getFieldResultRangeEndOverride(field) : field.getEnd());
    }

    private static Node getFieldResultRangeEndOverride(Field field)
    {
        // WORDSNET-24538 If field end is inside Office Math but field separator is not,
        // then MS Words retains Office Math nodes during field update.
        OfficeMath endParent = getTopLevelOfficeMathAncestor(field.getEnd());
        if (endParent == null)
            return null;

        OfficeMath separatorParent = getTopLevelOfficeMathAncestor(field.getSeparator());
        if (separatorParent == null || separatorParent != endParent)
            return endParent;

        return null;
    }

    private static OfficeMath getTopLevelOfficeMathAncestor(Node node)
    {
        OfficeMath officeMath = (OfficeMath)node.getAncestor(NodeType.OFFICE_MATH);
        return officeMath != null
            ? officeMath.getTopLevelOfficeMath()
            : null;
    }

    /// <summary>
    /// Ends update of the specified field, returning a field update action to be applied to the field.
    /// </summary>
    static FieldUpdateAction endFieldUpdate(
        Field field,
        Bookmark bookmark,
        NodeRange range,
        Paragraph bookmarkParagraph,
        boolean hasBookmarkInResult,
        boolean includeNoteOrComment) throws Exception
    {
        Debug.assertTrue(field != null);
        Debug.assertTrue(bookmark != null);
        Debug.assertTrue(bookmarkParagraph != null);

        if (includeNoteOrComment)
        {
            // WORDSNET-26220 the \f switch (include note or comment)
            // is ignored for fields in footnote/endnote, comment or header/footer.
            IStory story = field.getEnd().getAncestorIStory();
            includeNoteOrComment = story.getStoryType() == StoryType.MAIN_TEXT;
        }

        String error = validateBookmarkRange(field, range, includeNoteOrComment);
        if (error != null)
            return new FieldUpdateActionInsertErrorMessage(field, error);

        boolean needUpdateListLabels = needUpdateListLabels(range);
        updateListLabelsIfNeeded(field, needUpdateListLabels);

        Node rangeStart = getRangeStartInTableRow(bookmark, range);
        if (rangeStart != null)
            range = new NodeRange(rangeStart, true, range.getEnd().getNode(), range.isEndIncluded());

        CompositeNodeCopierListener listener = new CompositeNodeCopierListener();
        listener.add(new FieldFakeResultAppender(field));
        // WORDSNET-13026 Replace En spaces with regular spaces.
        listener.add(new FormFieldSpecialCharReplacer(field.getSeparator(), field.getEnd()));

        if (!hasBookmarkInResult)
        {
            final /*NodeCopierOptions*/int OPTIONS =
                NodeCopierOptions.USE_SOURCE_START_ANCESTOR_PR |
                NodeCopierOptions.PROCESS_BOUND_BLOCK_ANNOTATION_AS_INLINE |
                NodeCopierOptions.CLONE_NODE;

            NodeCopier.copyWithoutFields(
                range,
                getFieldResultRangeEnd(field),
                new CompositeModifier(
                    new FieldRefNodeModifier(includeNoteOrComment),
                    new CommentIdsRegeneratorNodeModifier()),
                listener,
                true,
                OPTIONS);

            if (bookmarkParagraph != getBookmarkParentParagraph(bookmark.getBookmarkEnd(), false))
                NodeCopier.cloneParaPrPreserveFrameAttrs(field.getStart().getParentParagraph(), bookmarkParagraph.getParaPr());
        }
        else
        {
            // If the referenced bookmark is contained within field's result, then all old extra result nodes have been removed in
            // BeginFieldUpdate. We need to left out the bookmark content but remove the bookmark start and end nodes.
            bookmark.remove();
        }

        return new FieldUpdateActionFormatResult(field);
    }

    private static Paragraph getBookmarkParentParagraph(Node bookmarkNode, boolean isStart)
    {
        if (bookmarkNode.getNodeLevel() == NodeLevel.INLINE)
            return (Paragraph)bookmarkNode.getFirstMeaningfulParentNode();

        return isStart
            ? NodeUtil.findNextParagraph(bookmarkNode)
            : NodeUtil.findPreviousParagraph(bookmarkNode);
    }

    private static String validateBookmarkRange(Field field, NodeRange range, boolean includeNoteOrComment)
    {
        boolean fieldInsideTable = field.getStart().getStoryAncestor(NodeType.TABLE) != null;

        NodeEnumerator enumerator = new NodeEnumerator(range);
        boolean isDeep = true;

        while (enumerator.moveToNextNode(isDeep))
        {
            Node node = enumerator.getCurrentNode();

            // WORDSNET-6503 We need to check whether the range contains no REF field itself.
            if (node == field.getStart())
                return "Error! Not a valid bookmark self-reference.";

            // WORDSNET-16134 REF field within a table can not refer to range with section break.
            if (fieldInsideTable && (node.getNodeType() == NodeType.SECTION))
                return "Error! Not a valid result for table.";

            isDeep = includeNoteOrComment || !(node instanceof IStory);
        }

        return null;
    }

    private static boolean needUpdateListLabels(NodeRange range)
    {
        for (Node node : range)
        {
            // If the bookmark contains a FIELDNUM, then list labels should be built before the final update of the field.
            if ((node.getNodeType() == NodeType.FIELD_START) && FieldNumUtil.isFieldNum(((FieldStart)node).getFieldType()))
                return true;
        }

        return false;
    }

    private static Node getRangeStartInTableRow(Bookmark bookmark, NodeRange range)
    {
        CompositeNode bookmarkStartParentNode = bookmark.getBookmarkStart().getParentNode();
        boolean bookmarkStartInsideOrBetweenTableRows =
            bookmarkStartParentNode.getNodeType() == NodeType.ROW ||
            bookmarkStartParentNode.getNodeType() == NodeType.TABLE;
        if (!bookmarkStartInsideOrBetweenTableRows)
            return null;

        for (Node node : range)
        {
            // WORDSNET-18470/WORDSNET-18630: If the bookmark start locates in or between table rows, move range start into first paragraph in table cell.
            if (node.getNodeType() == NodeType.PARAGRAPH)
                return ((CompositeNode)node).getFirstChild();
        }

        return null;
    }

    static boolean isRelativePositionAvailable(Field field)
    {
        if (field.isInHeaderFooter())
            return false;

        return field.getStart().getAncestor(NodeType.FOOTNOTE) == null;
    }

    /// <summary>
    /// Returns a string representing the relative bookmark position to place in field result.
    /// </summary>
    static String getRelativeBookmarkPosition(Field field, Bookmark targetBookmark)
    {
        Debug.assertTrue(targetBookmark != null);

        return getRelativeNodePosition(field, targetBookmark.getBookmarkStart());
    }

    static String getRelativeNodePosition(Field field, Node node)
    {
        if (!isRelativePositionAvailable(field))
            return "";

        return RelativePositionTranslator.translate(
            field.getLocaleId(),
            node.isAbove(field.getStart()));
    }

    /// <summary>
    /// Returns a string representing the relative node position to place in field result.
    /// </summary>
    static String getRelativePosition(Field field, boolean isAbove)
    {
        if (!isRelativePositionAvailable(field))
            return "";

        return RelativePositionTranslator.translate(field.getLocaleId(), isAbove);
    }

    private static void updateListLabelsIfNeeded(Field field, boolean needUpdateListLabels) throws Exception
    {
        if (needUpdateListLabels)
            field.getUpdater().requestExternalAction(new ExternalActionUpdateListLabels(field.fetchDocument()));
    }

    static String getParagraphNumber(
        Field field,
        Bookmark bookmark,
        Paragraph sourceParagraph,
        boolean suppressNonDelimiters,
        boolean insertRelativePosition) throws Exception
    {
        String result = getLabelStringInParagraphNumberContext(bookmark, sourceParagraph, suppressNonDelimiters);
        return finalizeParagraphNumberResult(field, bookmark, sourceParagraph, insertRelativePosition, result);
    }

    static String getTrimmedParagraphNumber(
        Field field,
        Paragraph sourceParagraph,
        boolean insertRelativePosition)
    {
        String result = getTrimmedLabelStringInParagraphNumberContext(sourceParagraph);
        return finalizeParagraphNumberResult(field, null, sourceParagraph, insertRelativePosition, result);
    }

    static String getParagraphNumberInRelativeContext(
        Field field,
        Bookmark bookmark,
        Paragraph sourceParagraph,
        Paragraph relativeParagraph,
        boolean suppressNonDelimiters,
        String numberSeparator,
        boolean insertRelativePosition) throws Exception
    {
        String result = getLabelStringInRelativeContext(field, bookmark, sourceParagraph, relativeParagraph, suppressNonDelimiters, numberSeparator);
        return finalizeParagraphNumberResult(field, bookmark, sourceParagraph, insertRelativePosition, result);
    }

    static String getParagraphNumberInFullContext(
        Field field,
        Bookmark bookmark,
        Paragraph sourceParagraph,
        boolean suppressNonDelimiters,
        String numberSeparator,
        boolean insertRelativePosition) throws Exception
    {
        String result = getLabelStringInFullContext(bookmark, sourceParagraph, suppressNonDelimiters, numberSeparator);
        return finalizeParagraphNumberResult(field, bookmark, sourceParagraph, insertRelativePosition, result);
    }

    private static String getLabelStringInParagraphNumberContext(
        Bookmark bookmark,
        Paragraph sourceParagraph,
        boolean suppressNonDelimiters) throws Exception
    {
        String labelString = getListLabel(bookmark, sourceParagraph, suppressNonDelimiters);
        return trimTrailingDot(labelString);
    }

    /// <summary>
    /// Returns substring of a label string between first and last placeholders.
    /// </summary>
    private static String getTrimmedLabelStringInParagraphNumberContext(Paragraph sourceParagraph)
    {
        if (!sourceParagraph.hasListLabelFinal())
            return trimTrailingDot("");

        ListLabel listLabel = sourceParagraph.getListLabel();
        String numberFormat = listLabel.getListLevelFinal().getNumberFormat();
        int trimStart = -1;
        int trimEnd = -1;
        for (int i = 0; i < numberFormat.length(); i++)
        {
            char c = numberFormat.charAt(i);
            if (ListLevel.isPlaceholder(c))
            {
                trimStart = trimStart != -1 ? trimStart : i;
                trimEnd = numberFormat.length() - i - 1;
            }
        }

        String labelString = listLabel.getLabelStringFinal();
        if (trimStart > 0)
            labelString = msString.remove(labelString, 0, trimStart);
        if (trimEnd > 0)
            labelString = msString.remove(labelString, labelString.length() - trimEnd);

        return trimTrailingDot(labelString);
    }

    /// <summary>
    /// Returns a label string consisting of all or some levels' labels based on relative position
    /// of the specified paragraph.
    /// </summary>
    /// <remarks>
    /// [Quote 2.1.489 Part 1 Section 17.16.5.51, REF
    /// If the REF field is contained within paragraph 4.3.1 and the referenced paragraph is numbered 4.5.2, the result shall be 5.2. end Quote]
    /// DM: Actually, at least with Word 2007 I used for testing, the ref field can be in any place, not necessarily within a numbered list paragraph.
    /// What matters is the last instance of the referenced list formatting before the field.
    /// So in the example above any reference after 4.5, but before 4.6 would be just "2". After 4.6. that would be "5.2" etc.
    /// See TestRefRelative for more examples.
    /// </remarks>
    /// <param name="field"></param>
    /// <param name="bookmark">The referenced bookmark.</param>
    /// <param name="sourceParagraph">The paragraph where the bookmark is located (REF field target).</param>
    /// <param name="relativeParagraph">The paragraph where the field is located, used to calculate the relative context.</param>
    /// <param name="suppressNonDelimiters">
    ///     Causes the REF field to suppress non-delimiter or non-numerical text when used in conjunction with the \r switch.
    ///     With this flag, for example, you can reference "Section 1.01," and only "1.01" is displayed in the result.
    /// </param>
    /// <param name="numberSeparator">Specifies custom level labels separator.</param>
    /// <returns></returns>
    private static String getLabelStringInRelativeContext(
        Field field,
        Bookmark bookmark,
        Paragraph sourceParagraph,
        Paragraph relativeParagraph,
        boolean suppressNonDelimiters,
        String numberSeparator) throws Exception
    {
        if (!isRelativePositionAvailable(field))
            return getLabelStringInFullContext(bookmark, sourceParagraph, suppressNonDelimiters, numberSeparator);

        ListNumberState sourceParaNumberState = getListNumberState(bookmark, sourceParagraph);

        if (sourceParaNumberState == null)
            return trimTrailingDot(null);

        // Find the last paragraph with the same numbering format that starts before the field.
        ListNumberState relativeNumberState = getLastNumberStateBeforeParagraph(sourceParaNumberState, relativeParagraph);

        int lastDifferentLevel = ListLevel.MIN_LEVEL;
        if (relativeNumberState != null)
        {
            // Check how many levels match before the referenced paragraph level.
            for (int level = ListLevel.MIN_LEVEL; level < sourceParaNumberState.getCurrentLevel(); ++level)
            {
                ListLevel relativeLevel = relativeNumberState.getListLevel(level);
                ListLevel bookmarkLevel = sourceParaNumberState.getListLevel(level);

                int relativeNumber = relativeNumberState.getNumber(level);
                int bookMarkNumber = sourceParaNumberState.getNumber(level);

                if ((relativeLevel == bookmarkLevel) && (relativeNumber == bookMarkNumber))
                    lastDifferentLevel++;
                else
                    break;
            }
        }

        // Make a list label up to the last different level.
        String relativeParagraphLabel = ListLabelUtil.buildFullListLabel(
            sourceParaNumberState,
            lastDifferentLevel,
            getListLabelBuildBehaviour(suppressNonDelimiters),
            numberSeparator);

        return trimTrailingDot(relativeParagraphLabel);
    }

    /// <summary>
    /// Finds the last paragraph with the given list formatting before the given paragraph.
    /// </summary>
    private static ListNumberState getLastNumberStateBeforeParagraph(
        ListNumberState refNumberState,
        Paragraph fieldParagraph)
    {
        ListLevel refListLevel = refNumberState.getListLevel(ListLevel.MIN_LEVEL);
        ListNumberState lastNumberState = null;

        // Traverse the paragraphs from the start of the document.
        NodeCollection paragraphs = fieldParagraph.fetchDocument().getChildNodes(NodeType.PARAGRAPH, true);
        for (Paragraph paragraph : (Iterable<Paragraph>) paragraphs)
        {
            ListNumberState currentNumberState = getParagraphListNumberState(paragraph);
            boolean isTheSameList = (currentNumberState != null) &&
                                 (currentNumberState.getListLevel(ListLevel.MIN_LEVEL) == refListLevel);

            if (isTheSameList)
                lastNumberState = currentNumberState;

            // Stop after the paragraph containing the field is checked.
            if (paragraph == fieldParagraph)
                break;
        }

        return lastNumberState;
    }

    /// <summary>
    /// Returns a label string consisting of all levels' labels down to the current.
    /// </summary>
    /// <returns></returns>
    private static String getLabelStringInFullContext(
        Bookmark bookmark,
        Paragraph sourceParagraph,
        boolean suppressNonDelimiters,
        String numberSeparator) throws Exception
    {
        ListNumberState state = getListNumberState(bookmark, sourceParagraph);

        String fullContextLabel = null;
        if (state != null)
            fullContextLabel = ListLabelUtil.buildFullListLabel(state, getListLabelBuildBehaviour(suppressNonDelimiters), numberSeparator);

        return trimTrailingDot(fullContextLabel);
    }

    private static String finalizeParagraphNumberResult(
        Field field,
        Bookmark bookmark,
        Paragraph sourceParagraph,
        boolean insertRelativePosition,
        String fieldResult)
    {
        String relativeBookmarkPosition = insertRelativePosition
            ? getRelativePosition(field, bookmark, sourceParagraph)
            : "";

        // Cannot combine string and capacity in one call because not available on Java.
        StringBuilder result = new StringBuilder(fieldResult.length() + relativeBookmarkPosition.length() + 2);

        result.append(ControlChar.LEFT_TO_RIGHT_MARK_CHAR);
        msStringBuilder.append(result, fieldResult);

        if (!msString.isNullOrEmpty(relativeBookmarkPosition))
        {
            result.append(' ');
            msStringBuilder.append(result, relativeBookmarkPosition);
        }

        return result.toString();
    }

    private static String getRelativePosition(Field field, Bookmark bookmark, Paragraph sourceParagraph)
    {
        return bookmark != null
            ? getRelativeBookmarkPosition(field, bookmark)
            : getRelativeNodePosition(field, sourceParagraph);
    }

    /// <summary>
    /// <para>Trims the last '.' character from string. The string can be <c>null</c>.</para>
    /// <para>Used when paragraph list label is inserted in document as a field.</para>
    /// </summary>
    private static String trimTrailingDot(String source)
    {
        if (!StringUtil.hasChars(source))
            return "0";

        return source.charAt(source.length() - 1) == '.' ? msString.remove(source, source.length() - 1, 1) : source;
    }

    private static ListNumberState getParagraphListNumberState(Paragraph paragraph)
    {
        return (getParagraphListNumberStateFromListNumField(paragraph) != null ? getParagraphListNumberStateFromListNumField(paragraph) : paragraph.getListLabel().getNumberStateFinal());
    }

    private static String getListLabel(Bookmark bookmark, Paragraph paragraph, boolean suppressNonDelimiters) throws Exception
    {
        ListNumberState state = getListNumberState(bookmark, paragraph);
        if (state == null)
            return null;

        return ListLabelUtil.buildListLabel(state, getListLabelBuildBehaviour(suppressNonDelimiters));
    }

    private static ListNumberState getListNumberState(Bookmark bookmark, Paragraph paragraph) throws Exception
    {
        // WORDSNET-17447 MS Word extracts list number state in following order:
        // 1. LISTNUM field which contain referenced bookmark inside field code
        // 2. Paragraph list label
        // 3. First LISTNUM field within bookmark
        // 4. First LISTNUM field after bookmark (not implemented)
        // 5. First LISTNUM field within paragraph

        ArrayList<Field> fields = extractListNumFields(paragraph);

        return (((getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) != null ? getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) : paragraph.getListLabel().getNumberStateFinal()) != null ? (getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) != null ? getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) : paragraph.getListLabel().getNumberStateFinal()) : getFieldListNumberState(getListNumFieldFromParagraphInsideBookmark(bookmark, fields))) != null ? ((getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) != null ? getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) : paragraph.getListLabel().getNumberStateFinal()) != null ? (getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) != null ? getFieldListNumberState(getListNumFieldFromParagraphWithBookmark(bookmark, fields)) : paragraph.getListLabel().getNumberStateFinal()) : getFieldListNumberState(getListNumFieldFromParagraphInsideBookmark(bookmark, fields))) : getFieldListNumberState(getFirstField(fields)));
    }

    private static ListNumberState getFieldListNumberState(Field field)
    {
        if (field == null)
            return null;

        return FieldNumUtil.getFieldListNumberState(field);
    }

    private static ListNumberState getParagraphListNumberStateFromListNumField(Paragraph paragraph)
    {
        // WORDSNET-11690 MS Word considers the LISTNUM fields in referenced paragraph.
        Field field = getListNumFieldFromParagraph(paragraph);
        return getFieldListNumberState(field);
    }

    private static Field getListNumFieldFromParagraph(Paragraph paragraph)
    {
        ArrayList<Field> fields = FieldExtractor.extractToCollection(paragraph, false, FieldType.FIELD_LIST_NUM);
        return getFirstField(fields);
    }

    private static Field getFirstField(ArrayList<Field> fields)
    {
        if (fields.size() == 0)
            return null;

        return fields.get(0);
    }

    private static Field getListNumFieldFromParagraphWithBookmark(Bookmark bookmark, ArrayList<Field> fields)
    {
        if (fields.size() == 0)
            return null;

        if (bookmark == null)
            return null;

        for (Field field : fields)
        {
            ArrayList<Node> fieldBookmarks = NodeFinder.findNodes(field.getFieldRange(), NodeType.BOOKMARK_START);
            if (fieldBookmarks.contains(bookmark.getBookmarkStart()))
                return field;
        }

        return null;
    }

    private static Field getListNumFieldFromParagraphInsideBookmark(Bookmark bookmark, ArrayList<Field> fields) throws Exception
    {
        if (fields.size() == 0)
            return null;

        if (bookmark == null)
            return null;

        ArrayList<Node> rangeFieldStarts = NodeFinder.findNodes(bookmark.getNodeRange(), NodeType.FIELD_START);
        for (Field field : fields)
        {
            if (rangeFieldStarts.contains(field.getStart()))
                return field;
        }

        return null;
    }

    private static ArrayList<Field> extractListNumFields(Paragraph paragraph)
    {
        return FieldExtractor.extractToCollection(paragraph, false, FieldType.FIELD_LIST_NUM);
    }

    private static IListLabelBuildBehaviour getListLabelBuildBehaviour(boolean suppressNonDelimiters)
    {
        return suppressNonDelimiters
            ? FieldRefListLabelBuildBehaviour.INSTANCE
            : null;
    }

    private static class FormFieldSpecialCharReplacer extends NodeReplacer
    {
        FormFieldSpecialCharReplacer(FieldSeparator fieldRefSeparator, FieldEnd fieldRefEnd)
        {
        	super(NodeType.FIELD_SEPARATOR, NodeType.FIELD_END);
	
            mFieldRefSeparator = fieldRefSeparator;
            mFieldRefEnd = fieldRefEnd;
        }

        protected /*override*/ void collectClone(Node source, Node clone)
        {
            FieldChar fieldChar = (FieldChar)source;
            if (fieldChar.getFieldType() != FieldType.FIELD_FORM_TEXT_INPUT)
                return;

            super.collectClone(source, clone);
        }

        protected /*override*/ void replaceCollectedNode(Node node)
        {
            FieldChar fieldChar = (FieldChar)node;

            Debug.assertTrue(fieldChar.getFieldType() == FieldType.FIELD_FORM_TEXT_INPUT);

            switch (fieldChar.getNodeType())
            {
                case NodeType.FIELD_SEPARATOR:
                    mLastSeparator = (FieldSeparator)fieldChar;
                    break;
                case NodeType.FIELD_END:

                    if (mLastSeparator == null)
                        mLastSeparator = mFieldRefSeparator;

                    replaceSpecialCharsInRange(mLastSeparator, fieldChar);

                    mLastSeparator = null;

                    break;
                default:
                    Debug.fail("Unexpected node type");
                    break;
            }
        }

        protected /*override*/ void finalizeReplace() throws Exception
        {
            super.finalizeReplace();

            if (mLastSeparator != null)
                replaceSpecialCharsInRange(mLastSeparator, mFieldRefEnd);
        }

        private static void replaceSpecialCharsInRange(Node start, Node end)
        {
            ArrayList<SpecialChar> specialChars = findSpecialChars(start, end);
            if (specialChars == null)
                return;

            for (SpecialChar specialChar : specialChars)
            {
                specialChar.getParentNode().insertBefore(new Run(specialChar.getDocument(), ControlChar.SPACE, AttrCollectionExtension.deepClone(specialChar.getRunPr())), specialChar);
                specialChar.remove();
            }
        }

        private static ArrayList<SpecialChar> findSpecialChars(Node start, Node end)
        {
            NodeRange resultRange = new NodeRange(start, false, end, false);
            ArrayList<SpecialChar> result = null;
            NodeEnumerator nodeEnumerator = new NodeEnumerator(resultRange);
            try /*JAVA: was using*/
            {
                while (nodeEnumerator.moveToNextNode())
                {
                    if (nodeEnumerator.getCurrentNode().getNodeType() != NodeType.SPECIAL_CHAR)
                        continue;

                    SpecialChar specialChar = (SpecialChar)nodeEnumerator.getCurrentNode();
                    if (!msString.equals(specialChar.getText(), ControlChar.DEFAULT_TEXT_INPUT))
                        continue;

                    if (result == null)
                        result = new ArrayList<SpecialChar>();

                    msArrayList.add(result, specialChar);
                }
            }
            finally { if (nodeEnumerator != null) nodeEnumerator.dispose(); }

            return result;
        }

        private FieldSeparator mLastSeparator;

        private /*final*/ FieldSeparator mFieldRefSeparator;
        private /*final*/ FieldEnd mFieldRefEnd;
    }

    private static class RelativePositionTranslator
    {
        static String translate(int language, boolean above)
        {
            switch (LanguageOnly.toLanguageOnly(language))
            {
                // ReSharper disable StringLiteralTypo
                case LanguageOnly.AFRIKAANS: return above ? "hierbo" : "hieronder";
                case LanguageOnly.ARMENIAN: return above ? "վեռժում" : "նեռքժում";
                case LanguageOnly.AZERI:
                    switch ((/*Language*/int)language)
                    {
                        case Language.AZERI_CYRILLIC: return above ? "јухары" : "ашағы";
                        default: return above ? "yuxarı" : "aşağı";
                    }
                case LanguageOnly.BASQUE: return above ? "goian" : "behean";
                case LanguageOnly.BELARUSIAN: return above ? "вышэй" : "ніжэй";
                case LanguageOnly.BULGARIAN: return above ? "отгоре" : "отдолу";
                case LanguageOnly.CATALAN: return above ? "a dalt" : "més avail";
                case LanguageOnly.CHINESE:
                    switch ((/*Language*/int)language)
                    {
                        case Language.CHINESE_CHINA:return above ? "见上方" : "见下方";
                        default: return above ? "如上" : "如下";
                    }
                case LanguageOnly.CROATIAN: return above ? "gore" : "dolje";
                case LanguageOnly.CZECH: return above ? "nahoře" : "dole";
                case LanguageOnly.DANISH: return above ? "ovenfor" : "nedenfor";
                case LanguageOnly.DUTCH: return above ? "hierboven" : "hieronder";
                case LanguageOnly.ESTONIAN: return above ? "ülalpool" : "allpool";
                case LanguageOnly.FINNISH: return above ? "yllä" : "alla";
                case LanguageOnly.FRENCH: return above ? "ci-dessus" : "ci-dessous";
                case LanguageOnly.GEORGIAN: return above ? "ზემოთ" : "ქვემოთ";
                case LanguageOnly.GERMAN: return above ? "oben" : "unten";
                case LanguageOnly.GREEK: return above ? "παραπάνω" : "παρακάτω";
                case LanguageOnly.HUNGARIAN: return above ? "előbb" : "alább";
                case LanguageOnly.ITALIAN: return above ? "più sopra" : "più sotto";
                case LanguageOnly.JAPANESE: return above ? "上の" : "下の";
                case LanguageOnly.KAZAKH: return above ? "жоғарғы" : "төменгі";
                case LanguageOnly.KISWAHILI: return above ? "juu ya" : "chini ya";
                case LanguageOnly.KOREAN: return above ? "위참조" : "아래참조";
                case LanguageOnly.LATVIAN: return above ? "virs" : "zem";
                case LanguageOnly.LITHUANIAN: return above ? "aukščiau" : "žemiau";
                case LanguageOnly.MALAY: return above ? "Atas" : "Bawah";
                case LanguageOnly.NORWEGIAN: return above ? "ovenfor" : "nedenfor";
                case LanguageOnly.POLISH: return above ? "wyżej" : "niżej";
                case LanguageOnly.PORTUGUESE: return above ? "acima" : "abaixo";
                case LanguageOnly.ROMANIAN: return above ? "deasupra" : "dedesubt";
                case LanguageOnly.RUSSIAN: return above ? "выше" : "ниже";
                case LanguageOnly.SLOVAK: return above ? "vyššie" : "nižšie";
                case LanguageOnly.SLOVENIAN: return above ? "zgoraj" : "spodaj";
                case LanguageOnly.SPANISH: return above ? "más atrás" : "más adelante";
                case LanguageOnly.SWEDISH: return above ? "ovan" : "nedan";
                case LanguageOnly.TATAR: return above ? "өск" : "аска";
                case LanguageOnly.TURKISH: return above ? "üst" : "alt";
                case LanguageOnly.UKRAINIAN: return above ? "вище" : "нижче";
                default: return above ? "above" : "below";
                // ReSharper restore StringLiteralTypo
            }
        }
    }

    private static class FieldRefListLabelBuildBehaviour implements IListLabelBuildBehaviour
    {
        private FieldRefListLabelBuildBehaviour()
        {
        }

        public void /*IListLabelBuildBehaviour.*/notifyListNumberAppended(int listLabelLength)
        {
            // Nothing to do.
        }

        public boolean /*IListLabelBuildBehaviour.*/shouldAppendNotListNumberChar(char c)
        {
            return isDelimiterChar(c);
        }

        public int /*IListLabelBuildBehaviour.*/finalizeListLabelLength(int listLabelLength)
        {
            return listLabelLength;
        }

        public /*NumberStyle*/int /*IListLabelBuildBehaviour.*/normalizeNumberStyle(/*NumberStyle*/int numberStyle)
        {
            return numberStyle;
        }

        private static boolean isDelimiterChar(char c)
        {
            switch (c)
            {
                case '{':
                case '}':
                case '[':
                case ']':
                case '(':
                case ')':
                case '\\':
                case '|':
                case '/':
                case '-':
                case ':':
                case ';':
                case '.':
                case ',':
                    return true;
                default:
                    return false;
            }
        }

        static /*final*/ IListLabelBuildBehaviour INSTANCE = new FieldRefListLabelBuildBehaviour();
    }
}

