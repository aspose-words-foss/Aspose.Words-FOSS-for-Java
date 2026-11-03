// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/12/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.BitUtil;
import com.aspose.ArrayUtil;
import java.lang.Character;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.words.ref.RefInt;


interface IListLabelBuildBehaviour
{
    /// <summary>
    /// This method is invoked when list number is appended.
    /// </summary>
    public void notifyListNumberAppended(int listLabelLength);

    /// <summary>
    /// Gets a value indicating whether not list number char should be appended to list label or not.
    /// </summary>
    public boolean shouldAppendNotListNumberChar(char c);

    /// <summary>
    /// Truncates list label length.
    /// </summary>
    public int finalizeListLabelLength(int listLabelLength);

    /// <summary>
    /// Normalizes number style if necessary.
    /// </summary>
    public /*NumberStyle*/int normalizeNumberStyle(/*NumberStyle*/int numberStyle);
}

/// <summary>
/// <para>
/// For specified paragraph, updates <see cref="Paragraph.ListLabel"/> with textual and numeric representations
/// of list label.
/// </para>
/// <para>
/// Also forms representation of list label of a paragraph to use in document fields as this representation
/// can be different from list label inside the paragraph itself.
/// </para>
/// </summary>
/// <dev>
/// This class is a product of a <see cref="ListNumberGenerator"/> class refactoring, i.e. moving of its members
/// which are not relative to a number generation process. All of the source code was left untouched while moving.
/// </dev>
/*static*/ class ListLabelUtil
{
	/* Simulation of static class by using private constructor */
	private ListLabelUtil()
	{}

    static boolean updateParagraphListLabel(Paragraph para, ListNumberGenerator generator, /*RevisionsView*/int view)
    {
        Debug.assertTrue(para != null);
        Debug.assertTrue(generator != null);

        /*ListLabelAction*/int listLabelAction = getListLabelAction(para, view);
        Debug.assertTrue(listLabelAction != ListLabelAction.UNDETERMINED);

        int originalListId = (/*int*/Integer)para.fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.ORIGINAL);
        int finalListId = (/*int*/Integer)para.fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.FINAL);

        int listId = (view == RevisionsView.ORIGINAL) ? originalListId : finalListId;

        // WORDSNET-26889 Ignore numbering revision inherited from style format revision.
        if ((originalListId != finalListId) && !para.getParaPr().hasFormatRevision())
            listId = finalListId;

        List list = para.getDocument().getLists().getListByListId(listId);
        // WORDSNET-13576 Do not number paragraphs referred to not existent list.
        if (list == null)
            listLabelAction = ListLabelAction.CLEAR;

        boolean needUpdate = listLabelAction != ListLabelAction.CLEAR;
        if (needUpdate)
        {
            int listLevel = (/*int*/Integer)para.fetchParaAttr(ParaAttr.LIST_LEVEL, view);

            ListNumberState state = generator.getNextListNumberState(list, listLevel);

            // WORDSNET-5554 Set LocaleId to be able to determine correct LocalizedOrdinalSuffix.
            state.setLocaleId(para.getParagraphBreakFont().getLocaleId());

            if (listLabelAction == ListLabelAction.UPDATE_AND_SET)
            {
                String[] labelFragments = buildListLabelFragments(state, false);
                String[] labelArabicNumbers = buildListLabelFragments(state, true);

                para.getListLabel().setLabelStringAndValue(labelFragments, labelArabicNumbers, state.snapshot(), view);

                // Update both labels at first pass.
                if (view == RevisionsView.ORIGINAL)
                    para.getListLabel().setLabelStringAndValue(labelFragments, labelArabicNumbers, state.snapshot(), RevisionsView.FINAL);

                return true;
            }
        }

        para.resetListLabel(view);
        // Update both labels at first pass.
        if (view == RevisionsView.ORIGINAL)
            para.resetListLabel(RevisionsView.FINAL);

        return needUpdate;
    }

    /// <summary>
    /// Checks if the list label of the paragraph is visible in MS Word.
    /// </summary>
    static boolean isListLabelVisible(Paragraph para, /*RevisionsView*/int view)
    {
        /*ListLabelAction*/int listLabelAction = getListLabelAction(para, view);
        return (listLabelAction != ListLabelAction.UPDATE_AND_CLEAR) && (listLabelAction != ListLabelAction.CLEAR);
    }

    /// <summary>
    /// Returns action needed to correctly update list label of paragraph.
    /// </summary>
    /// <remarks>
    /// <para>First check is whether the paragraph is list item.</para>
    /// <para>Second check is whether the paragraph is inside merged table cell.</para>
    /// <para>Third check is whether the paragraph contains any characters except page breaks.
    /// List labels for paragraphs with page breaks only are not written as is MS Word.</para>
    /// <para>The last check is whether the paragraph is section break.
    /// List label is not written for such a paragraph.</para>
    /// </remarks>
    private static /*ListLabelAction*/int getListLabelAction(Paragraph para, /*RevisionsView*/int view)
    {
        // If paragraph is marked for deletion it is not finally numbered.
        if ((view == RevisionsView.FINAL) && para.isDeleteRevision())
            return ListLabelAction.CLEAR;

        // If paragraph is marked for insertion it is not numbered originally.
        if ((view == RevisionsView.ORIGINAL) && para.isInsertRevision())
            return ListLabelAction.CLEAR;

        int listId = (/*int*/Integer)para.fetchParaAttr(ParaAttr.LIST_ID, view);
        if ((listId == 0) || (para.isInCell() && para.getParentCell().getCellPr().isMergedToPrevious()))
        {
            return ListLabelAction.CLEAR;
        }

        /*ListLabelAction*/int result = analyzeEmptinessAndPageBreaksInParagraph(para);

        if (result == ListLabelAction.UNDETERMINED)
        {
            // Section breaks (breaks only, not the last paragraph in the document) seem to never update and display
            // list labels, unless they are in sdt (dd: customxml is probably also the case, so add generic markup check here
            // instead of just checking for sdt).
            result = (!para.isEndOfSection() || para.isEndOfDocument() || NodeUtil.isMarkupNode(para.getParentNode()))
                ? ListLabelAction.UPDATE_AND_SET : ListLabelAction.CLEAR;
        }

        return result;
    }

    /// <summary>
    /// <para>Analyzes characters of the paragraph.</para>
    /// <para>If the paragraph is not empty, list label must be written.</para>
    /// <para>There is exception from the rule. If the paragraph contains only page breaks, list label
    /// number must be incremented but list label itself must not be written.</para>
    /// </summary>
    /// <remarks>
    /// Not writing of list label for paragraph with page breaks only is MS Word behavior.
    /// </remarks>
    private static /*ListLabelAction*/int analyzeEmptinessAndPageBreaksInParagraph(Paragraph para)
    {
        /*ListLabelAction*/int result = ListLabelAction.UNDETERMINED;

        for (Node node : (Iterable<Node>) para)
        {
            switch (node.getNodeType())
            {
                case NodeType.BOOKMARK_START:
                case NodeType.BOOKMARK_END:
                // WORDSNET-25064 Skip editable range nodes as well.
                case NodeType.EDITABLE_RANGE_START:
                case NodeType.EDITABLE_RANGE_END:
                {
                    // Skip bookmark nodes.
                    continue;
                }

                case NodeType.RUN:
                {
                    String runText = ((Run)node).getText();

                    if (!StringUtil.hasChars(runText))
                        continue;

                    if (isActionUpdateAndSet(para) || containsNonPageBreakChars(runText))
                        return ListLabelAction.UPDATE_AND_SET;

                    result = ListLabelAction.UPDATE_AND_CLEAR;
                    break;
                }

                default:
                {
                    // A shape or something? The label is visible then.
                    return ListLabelAction.UPDATE_AND_SET;
                }
            }
        }

        return result;
    }

    /// <summary>
    /// Returns true, if list label action for the specified paragraph is <see cref="ListLabelAction.UpdateAndSet"/>.
    /// </summary>
    private static boolean isActionUpdateAndSet(Paragraph para)
    {
        // andrnosk: WORDSNET-8481 If PageBreakChar is in cell we have to update and set its list label.
        if (para.isInCell())
            return true;

        // WORDSNET-13523 MS Word displays list label for PageBreakChar,
        // if following compatibility option is set.
        if (para.getDocument().getDocPr().CompatibilityOptions.getSplitPgBreakAndParaMark())
            return true;

        return false;
    }

    /// <summary>
    /// Returns true, if text contains characters other than <see cref="ControlChar.PageBreakChar"/>.
    /// </summary>
    private static boolean containsNonPageBreakChars(String text)
    {
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            if (c != ControlChar.PAGE_BREAK_CHAR)
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets the complete label for the current list number for most cases.
    /// <returns>List label split to fragments</returns>
    /// </summary>
    private static String[] buildListLabelFragments(ListNumberState state, boolean isArabicNumbersWithoutSeparators)
    {
        int dummy = 0;
        RefInt referenceToDummy = new RefInt(dummy);
        String[] buildLevelLabel = buildLevelLabelFragments(state, state.getListLevel(), null, isArabicNumbersWithoutSeparators, /*out*/ referenceToDummy);
        dummy = referenceToDummy.get();
        return buildLevelLabel;
    }

    /// <summary>
    /// Gets the full list label (including the labels of the previous levels) for the specified list numbering state.
    /// </summary>
    /// <remarks>
    /// Previous level labels are not included, if the previous levels are present in a number format for the lower levels.
    /// </remarks>
    static String buildFullListLabel(ListNumberState state, IListLabelBuildBehaviour behaviour, String separator)
    {
        return buildFullListLabel(state, ListLevel.MIN_LEVEL, behaviour, separator);
    }

    /// <summary>
    /// Gets the full list label (including the labels of the previous levels) for the specified list numbering state, up to the specified level.
    /// </summary>
    /// <remarks>
    /// Previous level labels are not included, if the previous levels are present in the number format for the lower levels.
    /// </remarks>
    static String buildFullListLabel(ListNumberState state, int minLevel, IListLabelBuildBehaviour behaviour, String separator)
    {
        StringBuilder fullLabel = new StringBuilder();

        // Go back from the current level.
        int minLevelUsed = state.getCurrentLevel() + 1;
        for (int level = state.getCurrentLevel(); level >= minLevel; --level)
        {
            // Do not add a label for the level if the level was already used via NumberFormat of the previous levels.
            if (level >= minLevelUsed)
                continue;

            if (!isAccountedForRef(state, level))
                continue;

            ListLevel levelPr = state.getListLevel(level);

            int minLevelUsedForThisLevel = 0;
            RefInt referenceToMinLevelUsedForThisLevel = new RefInt(minLevelUsedForThisLevel);
            String levelLabel = buildLevelLabelString(state, levelPr, behaviour, /*out*/ referenceToMinLevelUsedForThisLevel);
            minLevelUsedForThisLevel = referenceToMinLevelUsedForThisLevel.get();

            // WORDSNET-13141 MS Word misses a dot symbol, if it is the last symbol of list level and next list level starts with parentheses.
            if (msString.endsWith(levelLabel, ".", StringComparison.ORDINAL) &&
                fullLabel.length() > 0 && fullLabel.charAt(0) == '(')
                levelLabel = msString.remove(levelLabel, levelLabel.length() - 1);

            if (!msString.isNullOrEmpty(separator) && fullLabel.length() != 0)
                fullLabel.insert(0, separator);

            fullLabel.insert(0, levelLabel);

            if (minLevelUsedForThisLevel < minLevelUsed)
                minLevelUsed = minLevelUsedForThisLevel;
        }

        return fullLabel.toString();
    }

    /// <summary>
    /// Checks that given level should be present in REF list label.
    /// </summary>
    /// <remarks>
    /// AM. It seems that Word does this way:
    /// If level was used during numbering then level is accounted.
    /// If level is less than first level used during numbering then level is accounted.
    /// Otherwise level is not accounted.
    /// </remarks>
    private static boolean isAccountedForRef(ListNumberState state, int level)
    {
        final int ALL_LEVELS = 0x1ff;

        // Bit mask to test whether higher levels were used.
        int higherLevels = ALL_LEVELS >> (ListLevel.MAX_LEVELS - level);

        int thisLevel = 1 << level;

        // Level was used so account it.
        if (BitUtil.isSetInt32(state.getUsedLevels(), thisLevel))
            return true;

        // Otherwise account level only if it before first used level.
        return (state.getUsedLevels() & higherLevels) == 0;
    }

    /// <summary>
    /// Returns a string representation of list label built by <see cref="BuildLevelLabelFragments"/>.
    /// </summary>
    private static String buildLevelLabelString(ListNumberState state, ListLevel levelPr, IListLabelBuildBehaviour behaviour, /*out*/RefInt minLevelUsed)
    {
        String[] fragments = buildLevelLabelFragments(state, levelPr, behaviour, false, /*out*/ minLevelUsed);
        return ArrayUtil.stringArrayToString(fragments);
    }

    /// <summary>
    /// Builds a list label for the given list numbering state and level.
    /// </summary>
    /// <remarks>
    /// If the upper level labels are included in the current level label (like A.1., where A is at upper level),
    /// they should not be included twice when building a full label. That's why this method returns the minimal level used.
    /// </remarks>
    /// <returns>Minimal level used in this level's label split to fragments.</returns>
    private static String[] buildLevelLabelFragments(
        ListNumberState state,
        ListLevel levelPr,
        IListLabelBuildBehaviour behaviour,
        boolean isArabicNumbersWithoutSeparators,
        /*out*/RefInt minLevelUsed)
    {
        ListLabelFragmentsBuilder builder = createListLabelFragmentsBuilder();
        minLevelUsed.set(buildLevelLabel(builder, state, levelPr, behaviour, isArabicNumbersWithoutSeparators));
        return builder.getFragments();
    }

    /// <summary>
    /// Builds a list label for the given list numbering state and level using the specified <see cref="IListLabelBuilder"/>.
    /// </summary>
    private static int buildLevelLabel(
        IListLabelBuilder builder,
        ListNumberState state,
        ListLevel levelPr,
        IListLabelBuildBehaviour behaviour,
        boolean isArabicNumbersWithoutSeparators)
    {
        if (behaviour == null)
            behaviour = EmptyListLabelBuildBehaviour.INSTANCE;

        int minLevelUsed = levelPr.getLevelNumber();

        for (int i = 0; i < levelPr.getNumberFormat().length(); i++)
        {
            char c = levelPr.getNumberFormat().charAt(i);

            // WORDSNET-14314 MS Word does not show non-printable characters in list label
            // in fixed file formats. Some of them are characters from Unicode category 'Format'.
            //
            // WORDSJAVA-1908 '-' is acceptable symbol
            if (Character.getType(c) == Character.FORMAT && c != '\u00AD')
                continue;

            if (ListLevel.isLevelNumberValid(c))
            {
                // WORDSNET-12589 Avoid to build label if NumberStyle is None.
                // WORDSNET-17863 Check NumberStyle of list level of current placeholder instead of NumberStyle of list
                // level of current paragraph. In addition, Word ignores bulleted list levels in placeholders.
                // See [MS-OI29500], 2.1.285: If the numFmt is bullet, Word will use the literal text in lvlText,
                // ignoring %x syntax in lvlText.
                ListLevel placeHolderLevel = state.getListLevel(c);
                if (hasCounter(placeHolderLevel.getNumberStyle()))
                {
                    String labelPart = buildListLabelPart(state, levelPr, c, isArabicNumbersWithoutSeparators, behaviour);
                    if (labelPart.length() > 0)
                    {
                        builder.append(labelPart);
                        behaviour.notifyListNumberAppended(builder.getLength());
                    }

                    if (minLevelUsed > c)
                        minLevelUsed = c;
                }
            }
            else if (!isArabicNumbersWithoutSeparators && behaviour.shouldAppendNotListNumberChar(c))
            {
                builder.append(c);
            }
        }

        builder.setLength(behaviour.finalizeListLabelLength(builder.getLength()));

        return minLevelUsed;
    }

    /// <summary>
    /// Gets the label for the specified level of the current list.
    /// </summary>
    private static String buildListLabelPart(
        ListNumberState state,
        ListLevel currentLevelPr,
        int levelOfLabel,
        boolean isArabicNumbersWithoutSeparators,
        IListLabelBuildBehaviour behaviour)
    {
        int number = state.getNumber(levelOfLabel);

        ListLevel levelPrOfLabel = state.getListLevel(levelOfLabel);

        // WORDSNET-9829 Skip level with picture number style
        if (levelPrOfLabel.hasPictureBullet())
            return "";

        /*NumberStyle*/int numberStyle = behaviour.normalizeNumberStyle(levelPrOfLabel.getNumberStyle());

        if (numberStyle == NumberStyle.BULLET)
        {
            return levelPrOfLabel.getNumberFormat();
        }

        // Most likely we don't need to check anything regarding levelOfLabel.
        // All levels output here are less or equal than current. And all they
        // should be output as Arabic for legal lists.
        // Several number styles are not affected by IsLegal.
        if (isArabicNumbersWithoutSeparators ||
            currentLevelPr.isLegal() && hasCounter(numberStyle) && (numberStyle != NumberStyle.LEADING_ZERO))
        {
            numberStyle = NumberStyle.ARABIC;
        }

        // WORDSNET-5554 We should take state.LocaleId into consideration, to handle language depended OrdinalSuffix.
        return NumberConverter.numberToLocalizedString(number, numberStyle, levelPrOfLabel.getCustomNumberStyle(), true, state.getLocaleId());
    }

    /// <summary>
    /// Gets a list label text for the given list numbering state with optionally trimmed non-level-number characters
    /// from start and end.
    /// </summary>
    static String buildListLabel(ListNumberState state, IListLabelBuildBehaviour behaviour)
    {
        ListLabelBuilder builder = createListLabelBuilder();
        buildLevelLabel(builder, state, state.getListLevel(), behaviour, false);
        return builder.toString();
    }

    /// <summary>
    /// A factory method to create a <see cref="ListLabelBuilder"/> instance.
    /// </summary>
    /// <remarks>
    /// This method should be an instance member the code to be autoportable to Java since
    /// instances of nested classes should be created within instance members of an outer class.
    /// </remarks>
    private static ListLabelBuilder createListLabelBuilder()
    {
        return new ListLabelBuilder();
    }

    /// <summary>
    /// A factory method to create a <see cref="ListLabelFragmentsBuilder"/> instance.
    /// </summary>
    /// <remarks>
    /// This method should be an instance member the code to be autoportable to Java since
    /// instances of nested classes should be created within instance members of an outer class.
    /// </remarks>
    private static ListLabelFragmentsBuilder createListLabelFragmentsBuilder()
    {
        return new ListLabelFragmentsBuilder();
    }

    static boolean hasCounter(/*NumberStyle*/int numberStyle)
    {
        return (numberStyle != NumberStyle.NONE) && (numberStyle != NumberStyle.BULLET);
    }

    /// <summary>
    /// Specifies an action that should be applied to a list label.
    /// </summary>
    private /*enum*/ final class ListLabelAction
    {
        private ListLabelAction(){}
        
        public static final int UNDETERMINED = 0;
        public static final int UPDATE_AND_SET = 1;
        public static final int UPDATE_AND_CLEAR = 2;
        public static final int CLEAR = 3;

        public static final int length = 4;
    }

    /// <summary>
    /// Represents a list label builder.
    /// </summary>
    private interface IListLabelBuilder
    {
        public void append(char c);
        public void append(String s);
        public int getLength(); public void setLength(int value);
    }

    /// <summary>
    /// Implements <see cref="IListLabelBuilder"/> in the way to build a list label string.
    /// </summary>
    private static class ListLabelBuilder implements IListLabelBuilder
    {
        public /*override*/ String toString()
        {
            return mBuilder.toString();
        }

        public void /*IListLabelBuilder.*/append(char c)
        {
            mBuilder.append(c);
        }

        public void /*IListLabelBuilder.*/append(String s)
        {
            msStringBuilder.append(mBuilder, s);
        }

        public int /*IListLabelBuilder.*/getLength() { return mBuilder.length(); }
        public void /*IListLabelBuilder.*/setLength(int value) { mBuilder.setLength(value); }

        private /*final*/ StringBuilder mBuilder = new StringBuilder();
    }

    /// <summary>
    /// Implements <see cref="IListLabelBuilder"/> in the way to build a list label fragment string array.
    /// </summary>
    private static class ListLabelFragmentsBuilder implements IListLabelBuilder
    {
        String[] getFragments()
        {
            return msArrayList.toArray(mList, new String[0]);
        }

        public void /*IListLabelBuilder.*/append(char c)
        {
            msArrayList.add(mList, Character.toString(c));
        }

        public void /*IListLabelBuilder.*/append(String s)
        {
            msArrayList.add(mList, s);
        }

        public int /*IListLabelBuilder.*/getLength() { return mList.size(); }
        public void /*IListLabelBuilder.*/setLength(int value)
        {
            // Setter of length is used for trim non level number chars.
            // In case of list label fragment  we should not trim it
        }

        private /*final*/ ArrayList<String> mList = new ArrayList<String>();
    }

    private static class EmptyListLabelBuildBehaviour implements IListLabelBuildBehaviour
    {
        private EmptyListLabelBuildBehaviour()
        {
        }

        public void notifyListNumberAppended(int listLabelLength)
        {
            // Nobody to notify.
        }

        public boolean shouldAppendNotListNumberChar(char c)
        {
            return true;
        }

        public int finalizeListLabelLength(int listLabelLength)
        {
            return listLabelLength;
        }

        public /*NumberStyle*/int normalizeNumberStyle(/*NumberStyle*/int numberStyle)
        {
            return numberStyle;
        }

        static /*final*/ IListLabelBuildBehaviour INSTANCE = new EmptyListLabelBuildBehaviour();
    }
}

