// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.msArray;
import java.util.HashMap;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.StringToObjDictionary;
import com.aspose.LanguageOnly;
import com.aspose.SystemPal;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Convert;
import com.aspose.SortedIntegerListGeneric;
import java.util.Map;


/// <summary>
/// Sequentially updates the data used to set the results of LISTNUM, AUTONUM, AUTONUMOUT and AUTONUMLGL fields
/// for a document. This data can be accessed through <see cref="Document.GetFieldNumListLabel"/> in further.
/// </summary>
/// <remarks>
/// This class may affect <see cref="ListNumberState"/> objects used to maintain paragraph list labels' updating
/// routine. By this reason its methods must be sequentially called within this routine to make it work properly.
/// </remarks>
/// <dev>
/// ECMA-376 describes how to build LISTNUM fields' results, but MS Word does it in its own way. Moreover,
/// AUTONUM, AUTONUMOUT and AUTONUMLGL are MS Word transitional fields which are not described in ECMA-376.
/// By this reason this class is made basing on experiments in MS Word main part of which is contained within
/// TestFieldsNumbering.TestFieldNumGeneral() test.
///
/// There are some shortcuts widely used in the comments of this class:
/// - FIELDNUM means LISTNUM, AUTONUM, AUTONUMOUT and AUTONUMLGL;
/// - AUTONUM sometimes means AUTONUM, AUTONUMOUT and AUTONUMLGL depending on a context.
/// </dev>
class FieldNumListLabelUpdater
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldNumListLabelUpdater(Document document)
    {
        mDocument = document;
        mDocument.clearFieldNumListLabels();
    }

    /// <summary>
    /// Processes a paragraph entry.
    /// </summary>
    void processParagraph(
        Paragraph paragraph,
        boolean isListLabelUpdated,
        boolean isInsideBody)
    {
        // Process current paragraph change.
        processParagraphHeadingListLevel(paragraph);

        // Set default values for per-paragraph member variables.
        mParagraphIsInsideBody = isInsideBody;
        mParagraphHasAutoNumField = false;
        mParagraphHasListNumBreak = false;
        mParagraphLastListNumFieldEnd = null;
        mParagraphListNumBaseList = null;

        clearParagraphListNumListLevels();

        // If the paragraph contains a list label and it is successfully updated then we should process it
        // like a LISTNUM entry before a LISTNUM break (see ProcessParagraphListNumEntry() and
        // GetNextListNumListNumberState() for details).
        if (isListLabelUpdated)
        {
            mParagraphListNumBaseList = paragraph.getListFormat().getListFinal();
            mCurrentListNumContext.setBaseList(mParagraphListNumBaseList);

            // AM. Not really sure what this code does, just added check for null.
            if (mParagraphListNumBaseList != null)
                setParagraphListNumListLevel(mParagraphListNumBaseList, paragraph.getListFormat().getListLevelNumberFinal() + 1);
        }
    }

    void processArea(ListNumberGenerator generator)
    {
        processCurrentListNumberGenerator(generator);
    }

    /// <summary>
    /// Processes current paragraph's heading level change.
    /// </summary>
    private void processParagraphHeadingListLevel(Paragraph paragraph)
    {
        // Remember the heading level of the last paragraph if it has a heading style applied.
        if (mParagraphHeadingListLevel != INVALID_LIST_LEVEL)
        {
            mCurrentListNumContext.setLastHeadingListLevel(mParagraphHeadingListLevel);

            // We should consider only paragraphs inside a body which contain a FIELDNUM entry for AUTONUM fields.
            if (mParagraphIsInsideBody && mParagraphHasAutoNumField)
                mLastAutoNumHeadingListLevel = mParagraphHeadingListLevel;
        }

        // Calculate the zero-based heading level for the new paragraph basing on a style or outline level applied.
        mParagraphHeadingListLevel = extractParagraphHeadingListLevel(paragraph);
    }

    private static int extractParagraphHeadingListLevel(Paragraph paragraph)
    {
        Style headingStyle = getHeadingStyle(paragraph.getParagraphStyle(RevisionsView.FINAL));
        if (headingStyle != null)
            return headingStyle.getIstd() - StyleIndex.HEADING_1;

        // WORDSNET-16900 Use paragraph outline level when it does not have a heading style applied
        /*OutlineLevel*/int outlineLevel = (/*OutlineLevel*/Integer)paragraph.fetchParaAttr(ParaAttr.OUTLINE_LEVEL, RevisionsView.FINAL);
        if (outlineLevel != OutlineLevel.BODY_TEXT)
            return (int)outlineLevel;

        return INVALID_LIST_LEVEL;
    }

    /// <summary>
    /// Processes current list number generator change.
    /// </summary>
    private void processCurrentListNumberGenerator(ListNumberGenerator generator)
    {
        // If current list number generator is not changed, simply return.
        if (mCurrentListNumberGenerator == generator)
            return;

        // Make a LISTNUM context relative to the current list number generator current.
        // We have different LISTNUM contexts relative to different list number generators because
        // each of the number generators is relative to some numbering area (i.e. a document area
        // where numbering is separated from numberings in other areas) and hence we get different
        // LISTNUM contexts relative to different numbering areas which are independent.
        mCurrentListNumContext = DictionaryExtension.getValueOrNull(mListNumContexts, generator);
        if (mCurrentListNumContext == null)
        {
            mCurrentListNumContext = new ListNumContext();
            mListNumContexts.put(generator, mCurrentListNumContext);
        }

        // Set current list number generator.
        mCurrentListNumberGenerator = generator;
    }

    /// <summary>
    /// Clears <see cref="mParagraphListNumListLevels"/> properly.
    /// </summary>
    private void clearParagraphListNumListLevels()
    {
        // SortedIntegerList.Clear() reallocates resources regardless of whether the underlying list
        // contains any elements or not. Let's handle this properly as the list is empty almost all the time.
        if (mParagraphListNumListLevels.getCount() != 0)
            mParagraphListNumListLevels.clear();
    }

    /// <summary>
    /// Sets the next list level to be applied for a LISTNUM field using the specified list.
    /// </summary>
    /// <remarks>
    /// The specified list level value is upper-normalized inside but it must be lower-normalized before
    /// any call of this method.
    /// </remarks>
    private void setParagraphListNumListLevel(List list, int level)
    {
        level = Math.min(level, G_LIST_LEVEL_OBJECTS.length - 1);
        mParagraphListNumListLevels.set(list.getListDefId(), G_LIST_LEVEL_OBJECTS[level]);
    }

    /// <summary>
    /// Gets the next list level to be applied for a LISTNUM field using the specified list.
    /// Returns <see cref="InvalidListLevel"/> if the requested value was not set previously.
    /// </summary>
    private int getParagraphListNumListLevel(List list)
    {
        Object levelObject = mParagraphListNumListLevels.get(list.getListDefId());
        if (levelObject == null)
            return INVALID_LIST_LEVEL;

        return msArray.indexOf((G_LIST_LEVEL_OBJECTS), (levelObject), 0, (G_LIST_LEVEL_OBJECTS).length);
    }

    /// <summary>
    /// Processes a field start entry.
    /// </summary>
    void processFieldStart(FieldStart fieldStart)
    {
        // Do not process deleted fields.
        if (fieldStart.isDeleteRevision())
            return;

        // Although MS-OE376 says that nested FIELDNUMs are ignored (see
        // http://msdn.microsoft.com/en-us/library/ff533802(v=office.12).aspx,
        // http://msdn.microsoft.com/en-us/library/ff534359(v=office.12).aspx, etc.)
        // this is not true for the fields like INCLUDETEXT, IF and SET according to experiments with MS Word.
        // MS Word processes FIELDNUM entries inside these fields' results as if they would not be nested.
        // Actually relative MS Word behavior is more complex than that so we just let FIELDNUMs to be nested
        // to any field's result hoping this will cover the majority of possible cases because if a user will
        // include FIELDNUM to any forbidden field's code and will not see its value in this field's result
        // then he probably will not use FIELDNUMs in this way at all.
        if (isNestedNumFieldIgnored(fieldStart.getFieldType()))
            mFieldLockCount++;

        if ((mFieldLockCount == 1) && FieldNumUtil.isFieldNum(fieldStart.getFieldType()))
            mDocument.setFieldNumListLabel(fieldStart, buildListLabel(fieldStart));
    }

    /// <summary>
    /// Builds <see cref="FieldNumListLabel"/> object for a FIELDNUM which start is specified.
    /// </summary>
    private FieldNumListLabel buildListLabel(FieldStart fieldStart)
    {
        switch (fieldStart.getFieldType())
        {
            case FieldType.FIELD_AUTO_NUM:
            case FieldType.FIELD_AUTO_NUM_OUTLINE:
            case FieldType.FIELD_AUTO_NUM_LEGAL:
                processAutoNumEntry();
                break;
            default:
                // Do nothing.
                break;
        }

        switch (fieldStart.getFieldType())
        {
            case FieldType.FIELD_AUTO_NUM:
            {
                FieldAutoNum fieldAutoNum = (FieldAutoNum)fieldStart.getField();

                // WORDSNET-9802 Build list label according to field formatting.
                /*GeneralFormat*/int fieldFormat = fieldAutoNum.getFormat().getGeneralFormats().get(0);
                return buildAutoNumListLabel(
                    FieldNumListType.NUMBER,
                    fieldAutoNum.getSeparatorCharacterCore(),
                    true,
                    fieldFormat);
            }
            case FieldType.FIELD_AUTO_NUM_OUTLINE:
            {
                return buildAutoNumListLabel(
                    FieldNumListType.OUTLINE,
                    FieldNumUtil.DEFAULT_SEPARATOR,
                    true,
                    GeneralFormat.NONE);
            }
            case FieldType.FIELD_AUTO_NUM_LEGAL:
            {
                FieldAutoNumLgl fieldAutoNumLgl = (FieldAutoNumLgl)fieldStart.getField();

                // WORDSNET-9802 Build list label according to field formatting.
                /*GeneralFormat*/int fieldFormat = fieldAutoNumLgl.getFormat().getGeneralFormats().get(0);
                return buildAutoNumListLabel(
                    FieldNumListType.LEGAL,
                    fieldAutoNumLgl.getSeparatorCharacterCore(),
                    !fieldAutoNumLgl.getRemoveTrailingPeriod(),
                    fieldFormat);
            }
            case FieldType.FIELD_LIST_NUM:
            {
                FieldListNum fieldListNum = (FieldListNum)fieldStart.getField();
                return buildListNumListLabel(fieldListNum);
            }
            default:
            {
                throw new IllegalStateException();
            }
        }
    }

    /// <summary>
    /// Processes FIELDNUM entry within the paragraph.
    /// </summary>
    private void processAutoNumEntry()
    {
        // Only the first occurance in paragraph should be considered.
        if (mParagraphHasAutoNumField)
            return;

        mParagraphHasAutoNumField = true;

        // AUTONUMs which are contained only within the body numbering area correspond to a numbering state.
        // This gives us an opportunity to use a single container for AUTONUM list numbers in contrast to
        // LISTNUM contexts. We do not use a single ListNumberState object for this purpose because AUTONUM's
        // list numbers are changed in the way which can not be effectively mimicked using a ListNumberState object.
        if (!mParagraphIsInsideBody)
            return;

        // Create on the first demand.
        if (mAutoNumListNumbers == null)
            mAutoNumListNumbers = new int[ListLevel.MAX_LEVELS + 1];

        // Increase AUTONUM list number for the current paragraph's heading level.
        // This is a place where LISTNUM fields affect AUTONUM fields.
        // Note, that we use an one-based index to access mParagraphHeadingListLevel items.
        // This is done to preserve an AUTONUM list number corresponding to InvalidListLevel heading
        // level as a zero item as it must be separated from the other ones not to influence on them.
        mAutoNumListNumbers[mParagraphHeadingListLevel + 1]++;
        if (mParagraphHeadingListLevel != INVALID_LIST_LEVEL)
        {
            // If we've encountered a non-InvalidListLevel heading level we should either set to zero
            // dependent AUTONUM list numbers.
            mAutoNumListNumbers[INVALID_LIST_LEVEL + 1] = 0;
            for (int i = mParagraphHeadingListLevel + 1; i < ListLevel.MAX_LEVELS; i++)
                mAutoNumListNumbers[i + 1] = 0;
        }
    }

    /// <summary>
    /// Builds <see cref="FieldNumListLabel"/> object for an AUTONUM field.
    /// </summary>
    private FieldNumListLabel buildAutoNumListLabel(/*FieldNumListType*/int listType, char separator, boolean hasTrailingSeparator, /*GeneralFormat*/int fieldFormat)
    {
        // Insert an error message as MS Word does in case of AUTONUM encountering outside of a document body.
        if (!mParagraphIsInsideBody)
            return buildListLabel("Main Document Only.");

        List list = getAutoNumList(listType, separator, hasTrailingSeparator, fieldFormat);
        ListNumberState state = getNextAutoNumListNumberState(list);

        return buildListLabel(state);
    }

    /// <summary>
    /// Gets an AUTONUM list.
    /// </summary>
    private List getAutoNumList(/*FieldNumListType*/int listType, char separator, boolean hasTrailingSeparator, /*GeneralFormat*/int fieldFormat)
    {
        // Use AUTONUM's list instead of AUTONUMOUT's one in case of a paragraph with a non-heading style applied.
        if ((listType == FieldNumListType.OUTLINE) && (mParagraphHeadingListLevel == INVALID_LIST_LEVEL))
            listType = FieldNumListType.NUMBER;

        /*NumberStyle*/int numberStyle = GeneralFormatUtil.generalFormatToNumberStyle(fieldFormat);
        if (numberStyle == NumberStyle.NONE)
            numberStyle = NumberStyle.ARABIC;

        // Try to find previously created AUTONUM list. Return if found.
        AutoNumListKey key = new AutoNumListKey(listType, separator, hasTrailingSeparator, numberStyle);
        List list = null;
        if (mAutoNumLists != null)
            list = DictionaryExtension.getValueOrNull(mAutoNumLists, key);
        if (list != null)
            return list;

        // Create AUTONUM list. We should use a single ListDef for all of the FIELDNUM lists.
        // Note, that it can be created together with the list if mFieldNumListDef is null
        // so we need to remember the list's ListDef in this case.
        list = FieldNumListFactory.createAutoNumList(
            mDocument,
            mFieldNumListDef,
            listType,
            separator,
            hasTrailingSeparator,
            numberStyle);

        mFieldNumListDef = list.getListDef();

        // Create on the first demand.
        if (mAutoNumLists == null)
            mAutoNumLists = new HashMap<FieldNumListLabelUpdater.AutoNumListKey, List>();

        // Remember and return created list.
        mAutoNumLists.put(key, list);
        return list;
    }

    /// <summary>
    /// Gets the next AUTONUM <see cref="ListNumberState"/> object to be used while
    /// <see cref="FieldNumListLabel"/> building.
    /// </summary>
    private ListNumberState getNextAutoNumListNumberState(List list)
    {
        Debug.assertTrue(list.getListLevels().getCount() == ListLevel.MAX_LEVELS);

        // Get a list level for the current paragraph and upper-normalize its value.
        int level = getListLevelByHeading(mLastAutoNumHeadingListLevel);
        level = Math.min(level, ListLevel.MAX_LEVELS - 1);

        // AUTONUM list numbers (i.e. mAutoNumListNumbers items) are used as follows.
        // 1. For a paragraph with a heading style applied numbers for heading levels from the lowest one
        //    (excluding the one for InvalidListLevel) to the given one are used.
        // 2. For a paragraph with a non-heading style applied numbers for heading levels from the lowest one
        //    (excluding the one for InvalidListLevel) to the last encountered non-InvalidListLevel one are used
        //    as and InvalidListLevel one in a place of a list number for the next heading level.
        // An one-based indexing used to access mParagraphHeadingListLevel items shortcuts these steps.
        int[] numbers = new int[ListLevel.MAX_LEVELS];
        numbers[level] = mAutoNumListNumbers[mParagraphHeadingListLevel + 1];
        for (int i = 0; i < level; i++)
            numbers[i] = mAutoNumListNumbers[i + 1];

        return new ListNumberState(list, level, numbers);
    }

    /// <summary>
    /// Gets a list level corresponding to the heading level of the current paragraph. If the current paragraph has
    /// non-heading style applied then the specified last encountered heading list level increased by 1 is returned.
    /// </summary>
    private int getListLevelByHeading(int lastHeadingListLevel)
    {
        if (mParagraphHeadingListLevel != INVALID_LIST_LEVEL)
            return mParagraphHeadingListLevel;

        if (lastHeadingListLevel != INVALID_LIST_LEVEL)
            return (lastHeadingListLevel + 1);

        return 0;
    }

    /// <summary>
    /// Builds a <see cref="FieldNumListLabel"/> object by the given error message.
    /// </summary>
    private FieldNumListLabel buildListLabel(String errorMessage)
    {
        // Make the error message bold as MS Word does.
        RunPr runPrOverrides = new RunPr();
        runPrOverrides.setBold(AttrBoolEx.TRUE);
        if (mDocument.getFieldOptions().isBidiTextSupportedOnUpdate())
            runPrOverrides.setBoldBi(AttrBoolEx.TRUE);

        return new FieldNumListLabel(errorMessage, runPrOverrides);
    }

    /// <summary>
    /// Builds a <see cref="FieldNumListLabel"/> object by the given <see cref="ListNumberState"/> object.
    /// </summary>
    private static FieldNumListLabel buildListLabel(ListNumberState state)
    {
        String text = ListLabelUtil.buildListLabel(state, null);

        RunPr runPrOverrides = null;
        ListLevel listLevel = state.getListLevel();
        if (listLevel.getNumberStyle() == NumberStyle.BULLET)
        {
            // Override font names for a bullet symbol.
            // Fortunately MS Word does not support shaped bullets for LISTNUM fields.
            runPrOverrides = new RunPr();
            listLevel.getRunPr().expandToInclusive(runPrOverrides, RunPr.FONT_NAME_ATTRIBUTES);
        }

        return new FieldNumListLabel(text, runPrOverrides, state);
    }

    /// <summary>
    /// Builds <see cref="FieldNumListLabel"/> object for a LISTNUM field.
    /// </summary>
    private FieldNumListLabel buildListNumListLabel(FieldListNum field)
    {
        processParagraphListNumGap(field.getStart());

        List list = getListNumList(field.getListName());
        ListNumberState state = getNextListNumListNumberState(field, list);

        processParagraphListNumEntry(field.getEnd(), list);

        return buildListLabel(state);
    }

    /// <summary>
    /// Processes a gap between the current LISTNUM entry and the last encountered one.
    /// </summary>
    private void processParagraphListNumGap(FieldStart fieldStart)
    {
        // If the current paragraph has a LISTNUM break then there is nothing left to do, return.
        if (mParagraphHasListNumBreak)
            return;

        // Process nodes of the gap to find a LISTNUM break.
        // Note, that mParagraphLastListNumFieldEnd can be null in case of the first LISTNUM entry,
        // in this case we correctly process the gap between this entry and the start of the current paragraph.
        for (Node node = fieldStart.getPreviousSibling(); node != mParagraphLastListNumFieldEnd; node = node.getPreviousSibling())
        {
            if (isListNumBreak(node))
            {
                mParagraphHasListNumBreak = true;
                break;
            }
        }
    }

    /// <summary>
    /// Returns a value indicating whether the specified node is a LISTNUM break or not.
    /// </summary>
    /// <remarks>
    /// LISTNUM breaks are nodes with some content which break a LISTNUM entry continuous sequence.
    /// LISTNUM breaks presence affects LISTNUM list-relative routines. See <see cref="mParagraphHasListNumBreak"/>
    /// dependencies for any details.
    /// </remarks>
    private static boolean isListNumBreak(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.RUN:
                return !StringUtil.containsOnlyWhitespaces(node.getText());
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
                // This needs further investigation: Maybe some other node types should be skipped too.
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Gets a LISTNUM list by the specified <see cref="ListDef"/> name.
    /// </summary>
    private List getListNumList(String listDefName)
    {
        // 1. If the ListDef name is not skipped, find and return the corresponding list.
        if (StringUtil.hasChars(listDefName))
            return getListNumListCore(listDefName);

        // 2. Return the last encountered list while current paragraph processing if any.
        if (mParagraphListNumBaseList != null)
            return mParagraphListNumBaseList;

        // 3. Return the last encountered list while current numbering area processing if any.
        List baseList = mCurrentListNumContext.getBaseList();
        if (baseList != null)
            return baseList;

        // 4. Return the NumberDefault list by default.
        return getListNumListCore(getListNumNumberListDefName());
    }

    /// <summary>
    /// Gets a LISTNUM list by the specified non-empty <see cref="ListDef"/> name.
    /// </summary>
    private List getListNumListCore(String listDefName)
    {
        Debug.assertTrue(StringUtil.hasChars(listDefName));

        // Try to find previously created LISTNUM list. Return if found.
        List list = (mListNumNamedLists != null) ? mListNumNamedLists.get(listDefName) : null;
        if (list != null)
            return list;

        /*FieldNumListType*/int listType = getListNumListType(listDefName);
        if (listType == FieldNumListType.NONE)
        {
            // Get a persisted list of the document.
            list = (mDocument.getLists().get(listDefName) != null ? mDocument.getLists().get(listDefName) : getListNumListCore(getListNumNumberListDefName()));
        }
        else
        {
            // Create LISTNUM list. We should use a single ListDef for all of the FIELDNUM lists.
            // Note, that it can be created together with the list if mFieldNumListDef is null
            // so we need to remember the list's ListDef in this case.
            list = FieldNumListFactory.createListNumList(mDocument, mFieldNumListDef, listType);
            mFieldNumListDef = list.getListDef();
        }

        // Create on the first demand.
        if (mListNumNamedLists == null)
            mListNumNamedLists = new StringToObjDictionary<List>(false);

        // Remember and return recieved list.
        mListNumNamedLists.set(listDefName, list);
        return list;
    }

    /// <summary>
    /// Gets a <see cref="FieldNumListType"/> by the specified predefined LISTNUM <see cref="ListDef"/> name.
    /// </summary>
    private static /*FieldNumListType*/int getListNumListType(String listDefName)
    {
        String[] fieldNumListDefNames = getCurrentLanguageFieldNumListDefNames();

        if (StringUtil.equalsIgnoreCase(listDefName, fieldNumListDefNames[LIST_NUM_NUMBER_LIST_DEF_NAME_INDEX]))
            return FieldNumListType.NUMBER;

        if (StringUtil.equalsIgnoreCase(listDefName, fieldNumListDefNames[LIST_NUM_OUTLINE_LIST_DEF_NAME_INDEX]))
            return FieldNumListType.OUTLINE;

        if (StringUtil.equalsIgnoreCase(listDefName, fieldNumListDefNames[LIST_NUM_LEGAL_LIST_DEF_NAME_INDEX]))
            return FieldNumListType.LEGAL;

        return FieldNumListType.NONE;
    }

    private static String getListNumNumberListDefName()
    {
        return getCurrentLanguageFieldNumListDefNames()[LIST_NUM_NUMBER_LIST_DEF_NAME_INDEX];
    }

    private static String[] getCurrentLanguageFieldNumListDefNames()
    {
        int language = LanguageOnly.toLanguageOnly(SystemPal.getCurrentCulture().getLCID());
        return DictionaryExtension.getValueOrDefault(G_FIELD_NUM_LIST_DEF_NAMES, language, G_DEFAULT_FIELD_NUM_LIST_DEF_NAMES);
    }

    /// <summary>
    /// Gets the next LISTNUM <see cref="ListNumberState"/> object to be used while
    /// <see cref="FieldNumListLabel"/> building.
    /// </summary>
    private ListNumberState getNextListNumListNumberState(FieldListNum field, List list)
    {
        ListNumberState state = mCurrentListNumberGenerator.getCurrentListNumberState(list);
        int nextLevel = getParagraphListNumListLevel(list);
        boolean isFirstListDefEntry = (nextLevel == INVALID_LIST_LEVEL);

        // 1. Use a list level override provided by the field's code if any.
        int level = field.getListLevelCore();
        if (level < 0)
        {
            if (!isFirstListDefEntry)
            {
                // 2. Use the next level to be applied calculated previously if any.
                level = nextLevel;
            }
            else if ((list.getListDefId() == FieldNumListFactory.FIELD_NUM_LIST_DEF_ID) &&
                     (field.hasListName() || (mCurrentListNumContext.getBaseList() == null)))
            {
                // 3. Use the current paragraph's heading level in case when:
                //    - we deal with one of the predefined LISTNUM lists and
                //    - the field's code provides any ListDef name (even if it is not one of the
                //      predefined ones) or there is no list encountered while the current list
                //      numbering area processing.
                level = getListLevelByHeading(mCurrentListNumContext.getLastHeadingListLevel());
            }
            else
            {
                // 4. If nothing above has triggered, use a stored value from a previous paragraph.
                level = state.getListNumStartAtLevel();
            }
        }

        // If the current paragraph does not contain a LISTNUM break at this point then a list level
        // for the next entry should be increased by 1. If the specified list's ListDef is firstly met
        // after a LISTNUM break, set a list level for the next entry without increase.
        if (!mParagraphHasListNumBreak || isFirstListDefEntry)
            setParagraphListNumListLevel(list, mParagraphHasListNumBreak ? level : (level + 1));

        // Advance the list numbering state.
        // If the current paragraph does not contain a LISTNUM break at this point, remember the list
        // level to use it in the next paragraph.
        state.nextItem(list, level, !mParagraphHasListNumBreak);

        // Use a list starting number override provided by the field's code if any.
        int number = field.getStartingNumberCore();
        if (number >= 0)
            state.setNumber(number);

        return state;
    }

    /// <summary>
    /// Performs final LISTNUM entry processing.
    /// </summary>
    private void processParagraphListNumEntry(FieldEnd fieldEnd, List list)
    {
        // Set the last LISTNUM field end.
        mParagraphLastListNumFieldEnd = fieldEnd;

        // The list used to build the current LISTNUM entry's result should be stored to be reused in the current
        // paragraph if it does not contain a LISTNUM break at this point or we deal with the first LISTNUM entry
        // within the current paragraph.
        if (!mParagraphHasListNumBreak || (mParagraphListNumBaseList == null))
            mParagraphListNumBaseList = list;

        // The list used to build the current LISTNUM entry's result should be stored to be reused in a following
        // paragraph of the same numbering area only if the current paragraph does not contain a LISTNUM break
        // at this point.
        if (!mParagraphHasListNumBreak)
            mCurrentListNumContext.setBaseList(list);
    }

    /// <summary>
    /// Processes a field separator entry.
    /// </summary>
    void processFieldSeparator(FieldSeparator fieldSeparator)
    {
        // Do not process deleted fields.
        if (fieldSeparator.isDeleteRevision())
            return;

        decrementFieldLockCount(fieldSeparator.getFieldType());
    }

    /// <summary>
    /// Reduces <see cref="mFieldLockCount"/> by 1 with check of a lower bound.
    /// </summary>
    private void decrementFieldLockCount(/*FieldType*/int fieldType)
    {
        if (isNestedNumFieldIgnored(fieldType) && mFieldLockCount > 0)
            mFieldLockCount--;
    }

    private static boolean isNestedNumFieldIgnored(/*FieldType*/int fieldType)
    {
        switch (fieldType)
        {
            case FieldType.FIELD_EQUATION:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Processes a field end entry.
    /// </summary>
    void processFieldEnd(FieldEnd fieldEnd)
    {
        // Do not process deleted fields.
        if (fieldEnd.isDeleteRevision())
            return;

        if (!fieldEnd.hasSeparator())
            decrementFieldLockCount(fieldEnd.getFieldType());
    }

    /// <summary>
    /// Returns 'Heading' style if it presents in a chain of base styles
    /// or the specified <paramref name="style"/> is 'Heading' style itself.
    /// If there is no such 'Heading' style, then returns null.
    /// </summary>
    private static Style getHeadingStyle(Style style)
    {
        if (style.isHeading())
            return style;

        Style baseStyle = style.getBaseStyle();
        if (baseStyle == null)
            return null;

        return getHeadingStyle(baseStyle);
    }

    /// <summary>
    /// Stores LISTNUM data dependent on the current numbering area.
    /// </summary>
    private static class ListNumContext
    {
        ListNumContext()
        {
            setLastHeadingListLevel(INVALID_LIST_LEVEL);
        }

        List getBaseList() { return mBaseList; }; void setBaseList(List value) { mBaseList = value; };

        private List mBaseList;

        int getLastHeadingListLevel() { return mLastHeadingListLevel; }; void setLastHeadingListLevel(int value) { mLastHeadingListLevel = value; };

        private int mLastHeadingListLevel;
    }

    /// <summary>
    /// Represents a key value used to distinguish AUTONUM lists stored in <see cref="mAutoNumLists"/>.
    /// </summary>
    private static class AutoNumListKey
    {
        AutoNumListKey(/*FieldNumListType*/int listType, char separator, boolean hasTrailingSeparator, /*NumberStyle*/int style)
        {
            mListType = listType;
            mSeparator = separator;
            mHasTrailingSeparator = hasTrailingSeparator;
            mStyle = style;
        }

        private boolean equals(AutoNumListKey other)
        {
            if (msObject.referenceEquals(other, null))
                return false;

            if (msObject.referenceEquals(other, this))
                return true;

            return (other.mListType == mListType) &&
                (other.mSeparator == mSeparator) &&
                (other.mHasTrailingSeparator == mHasTrailingSeparator) &&
                (other.mStyle == mStyle);
        }

        public /*override*/ boolean equals(Object obj)
        {
            return equals(ms.as(obj, AutoNumListKey.class));
        }

        public /*override*/ int hashCode()
        {
            // Char's size is 2 bytes.
            return (mSeparator | Convert.toInt32(mHasTrailingSeparator) << 16 | (int)mListType << 17);
        }

        private /*final*/ /*FieldNumListType*/int mListType;
        private /*final*/ char mSeparator;
        private /*final*/ boolean mHasTrailingSeparator;
        private /*final*/ /*NumberStyle*/int mStyle;
    }

    private /*final*/ Document mDocument;
    private ListDef mFieldNumListDef;
    private int mFieldLockCount;

    private ListNumberGenerator mCurrentListNumberGenerator;
    private ListNumContext mCurrentListNumContext;

    private int mParagraphHeadingListLevel = INVALID_LIST_LEVEL;
    private boolean mParagraphIsInsideBody;
    private boolean mParagraphHasAutoNumField;
    private boolean mParagraphHasListNumBreak;
    private Node mParagraphLastListNumFieldEnd;
    private List mParagraphListNumBaseList;
    private /*final*/ SortedIntegerListGeneric<Object> mParagraphListNumListLevels = new SortedIntegerListGeneric<Object>();

    private StringToObjDictionary<List> mListNumNamedLists;

    private /*final*/ HashMap<ListNumberGenerator, FieldNumListLabelUpdater.ListNumContext> mListNumContexts =
        new HashMap<ListNumberGenerator, FieldNumListLabelUpdater.ListNumContext>();

    private HashMap<FieldNumListLabelUpdater.AutoNumListKey, List> mAutoNumLists;
    private int[] mAutoNumListNumbers;
    private int mLastAutoNumHeadingListLevel = INVALID_LIST_LEVEL;

    private static /*final*/ Object[] G_LIST_LEVEL_OBJECTS = buildListLevelObjects();

    private static Object[] buildListLevelObjects()
    {
        // Using of this array reduces frequent boxing/unboxing operations.
        Object[] listLevelObjects = new Object[ListLevel.MAX_LEVELS];
        for (int i = 0; i < listLevelObjects.length; i++)
            listLevelObjects[i] = new Object();
        return listLevelObjects;
    }

    private static /*final*/ String[] G_DEFAULT_FIELD_NUM_LIST_DEF_NAMES =  { "NumberDefault", "OutlineDefault", "LegalDefault" };
    private static /*final*/ Map<Integer, String[]> G_FIELD_NUM_LIST_DEF_NAMES = G_FIELD_NUM_LIST_DEF_NAMESInitializer();
    /*JAVA-added private static Initializer*/
    private static Map<Integer, String[]> G_FIELD_NUM_LIST_DEF_NAMESInitializer()
    {
        Map<Integer, String[]> G_FIELD_NUM_LIST_DEF_NAMES = new HashMap<Integer, String[]>();
    	{
        	G_FIELD_NUM_LIST_DEF_NAMES.put( LanguageOnly.FRENCH, new String[] { "NuméroDéfaut", "PlanDéfaut", "LégalDéfaut" });
        	G_FIELD_NUM_LIST_DEF_NAMES.put( LanguageOnly.GERMAN, new String[] { "NummerStandard", "GliederungStandard", "DezimalStandard" });
        	G_FIELD_NUM_LIST_DEF_NAMES.put( LanguageOnly.ITALIAN, new String[] { "NumeroPredef", "StrutturaPredef", "LegalePredef" });
        	G_FIELD_NUM_LIST_DEF_NAMES.put( LanguageOnly.SPANISH, new String[] { "NúmeroPredeterminado", "EsquemaPredeterminado", "OficioPredeterminado" });
        	G_FIELD_NUM_LIST_DEF_NAMES.put( LanguageOnly.PORTUGUESE, new String[] { "Número Predefinido", "Destaque Predefinido", "Legal Predefinido" });
    	}
        return G_FIELD_NUM_LIST_DEF_NAMES;
    }

    private static final int LIST_NUM_NUMBER_LIST_DEF_NAME_INDEX = 0;
    private static final int LIST_NUM_OUTLINE_LIST_DEF_NAME_INDEX = 1;
    private static final int LIST_NUM_LEGAL_LIST_DEF_NAME_INDEX = 2;

    /// <summary>
    /// Invalid list level specifier.
    /// </summary>
    /// <remarks>
    /// If you are about to change the value of the constant consider that <see cref="mAutoNumListNumbers"/>'
    /// items access logic is tightly coupled with it.
    /// </remarks>
    private static final int INVALID_LIST_LEVEL = -1;
}

