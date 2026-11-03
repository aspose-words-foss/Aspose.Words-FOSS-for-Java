// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Builds lists used to maintain LISTNUM and AUTONUM fields' functionality.
/// </summary>
/// <remarks>
/// Created lists are not persisted in a document. They are designed to be used within
/// <see cref="FieldNumListLabelUpdater"/> only.
/// </remarks>
/*static*/ class FieldNumListFactory
{
	/* Simulation of static class by using private constructor */
	private FieldNumListFactory()
	{}

    /// <summary>
    /// Creates a list used to maintain LISTNUM field functionality.
    /// </summary>
    /// <param name="document"></param>
    /// <param name="listDef"></param>
    /// <param name="listType"></param>
    /// <returns></returns>
    static List createListNumList(DocumentBase document, ListDef listDef, /*FieldNumListType*/int listType)
    {
        List list = createFieldNumList(document, listDef, LIST_NUM_LIST_ID);
        switch (listType)
        {
            case FieldNumListType.NUMBER:
                initListNumNumberList(list);
                break;
            case FieldNumListType.OUTLINE:
                // Use ListDef level definitions.
                break;
            case FieldNumListType.LEGAL:
                initListNumLegalList(list);
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "listType");
        }

        return list;
    }

    /// <summary>
    /// Creates a list used to maintain AUTONUM field functionality.
    /// </summary>
    /// <param name="document"></param>
    /// <param name="listDef"></param>
    /// <param name="listType"></param>
    /// <param name="separator"></param>
    /// <param name="hasTrailingSeparator"></param>
    /// <param name="numberStyle"></param>
    /// <returns></returns>
    static List createAutoNumList(DocumentBase document, ListDef listDef, /*FieldNumListType*/int listType, char separator, boolean hasTrailingSeparator, /*NumberStyle*/int numberStyle)
    {
        List list = createFieldNumList(document, listDef, AUTO_NUM_LIST_ID);
        switch (listType)
        {
            case FieldNumListType.NUMBER:
                initAutoNumNumberList(list, separator, numberStyle);
                break;
            case FieldNumListType.OUTLINE:
                // Use ListDef level definitions.
                break;
            case FieldNumListType.LEGAL:
                initAutoNumLegalList(list, separator, hasTrailingSeparator, numberStyle);
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "listType");
        }

        return list;
    }

    /// <summary>
    /// Creates a list used to maintain LISTNUM or AUTONUM field functionality.
    /// </summary>
    /// <param name="document"></param>
    /// <param name="listDef"></param>
    /// <param name="listId"></param>
    /// <returns></returns>
    private static List createFieldNumList(DocumentBase document, ListDef listDef, int listId)
    {
        if (listDef != null)
            return new List(listDef, listId);

        // Create a ListDef instance if a null reference is passed.
        listDef = new ListDef(document, FIELD_NUM_LIST_DEF_ID, ListType.MULTI_LEVEL, 0);
        List list = new List(listDef, listId);

        // Outline numbering format is the same for all of the LISTNUM and AUTONUM fields,
        // so let's use it to define ListDef levels.
        initFieldNumListLevel(list, 0, NumberStyle.UPPERCASE_ROMAN, "\u0000.", false);
        initFieldNumListLevel(list, 1, NumberStyle.UPPERCASE_LETTER, "\u0001.", false);
        initFieldNumListLevel(list, 2, NumberStyle.ARABIC, "\u0002.", false);
        initFieldNumListLevel(list, 3, NumberStyle.LOWERCASE_LETTER, "\u0003)", false);
        initFieldNumListLevel(list, 4, NumberStyle.ARABIC, "(\u0004)", false);
        initFieldNumListLevel(list, 5, NumberStyle.LOWERCASE_LETTER, "(\u0005)", false);
        initFieldNumListLevel(list, 6, NumberStyle.LOWERCASE_ROMAN, "(\u0006)", false);
        initFieldNumListLevel(list, 7, NumberStyle.LOWERCASE_LETTER, "(\u0007)", false);
        initFieldNumListLevel(list, 8, NumberStyle.LOWERCASE_ROMAN, "(\u0008)", false);

        return list;
    }

    /// <summary>
    /// Fills list levels for LISTNUM field's NumberDefault list.
    /// </summary>
    /// <param name="list"></param>
    private static void initListNumNumberList(List list)
    {
        initFieldNumListLevel(list, 0, NumberStyle.ARABIC, "\u0000)");
        initFieldNumListLevel(list, 1, NumberStyle.LOWERCASE_LETTER, "\u0001)");
        initFieldNumListLevel(list, 2, NumberStyle.LOWERCASE_ROMAN, "\u0002)");
        initFieldNumListLevel(list, 3, NumberStyle.ARABIC, "(\u0003)");
        initFieldNumListLevel(list, 4, NumberStyle.LOWERCASE_LETTER, "(\u0004)");
        initFieldNumListLevel(list, 5, NumberStyle.LOWERCASE_ROMAN, "(\u0005)");
        initFieldNumListLevel(list, 6, NumberStyle.ARABIC, "\u0006.");
        initFieldNumListLevel(list, 7, NumberStyle.LOWERCASE_LETTER, "\u0007.");
        initFieldNumListLevel(list, 8, NumberStyle.LOWERCASE_ROMAN, "\u0008.");
    }

    /// <summary>
    /// Fills list levels for LISTNUM field's LegalDefault list.
    /// </summary>
    /// <param name="list"></param>
    private static void initListNumLegalList(List list)
    {
        initFieldNumListLevel(list, 0, NumberStyle.ARABIC, "\u0000.");
        initFieldNumListLevel(list, 1, NumberStyle.ARABIC, "\u0000.\u0001.");
        initFieldNumListLevel(list, 2, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.");
        initFieldNumListLevel(list, 3, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.\u0003.");
        initFieldNumListLevel(list, 4, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.\u0003.\u0004.");
        initFieldNumListLevel(list, 5, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.");
        initFieldNumListLevel(list, 6, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.");
        initFieldNumListLevel(list, 7, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.\u0007.");
        initFieldNumListLevel(list, 8, NumberStyle.ARABIC, "\u0000.\u0001.\u0002.\u0003.\u0004.\u0005.\u0006.\u0007.\u0008.");
    }

    /// <summary>
    /// Fills list levels for AUTONUM field's list.
    /// </summary>
    /// <param name="list"></param>
    /// <param name="separator"></param>
    /// <param name="numberStyle"></param>
    private static void initAutoNumNumberList(List list, char separator, /*NumberStyle*/int numberStyle)
    {
        initFieldNumListLevel(list, 0, numberStyle, "\u0000" + separator);
        initFieldNumListLevel(list, 1, numberStyle, "\u0001" + separator);
        initFieldNumListLevel(list, 2, numberStyle, "\u0002" + separator);
        initFieldNumListLevel(list, 3, numberStyle, "\u0003" + separator);
        initFieldNumListLevel(list, 4, numberStyle, "\u0004" + separator);
        initFieldNumListLevel(list, 5, numberStyle, "\u0005" + separator);
        initFieldNumListLevel(list, 6, numberStyle, "\u0006" + separator);
        initFieldNumListLevel(list, 7, numberStyle, "\u0007" + separator);
        initFieldNumListLevel(list, 8, numberStyle, "\u0008" + separator);
    }

    /// <summary>
    /// Fills list levels for AUTONUMLGL field's list.
    /// </summary>
    /// <param name="list"></param>
    /// <param name="separator"></param>
    /// <param name="hasTrailingSeparator"></param>
    /// <param name="numberStyle"></param>
    private static void initAutoNumLegalList(List list, char separator, boolean hasTrailingSeparator, /*NumberStyle*/int numberStyle)
    {
        // Use the single boxed separator object.
        Object boxedSeparator = separator;
        Object boxedTrailingSeparator = hasTrailingSeparator ? boxedSeparator : null;

        initFieldNumListLevel(list, 0, numberStyle,
            msString.format("\u0000{0}",
                boxedTrailingSeparator));
        initFieldNumListLevel(list, 1, numberStyle,
            msString.format("\u0000{0}\u0001{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 2, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 3, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{0}\u0003{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 4, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{0}\u0003{0}\u0004{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 5, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{0}\u0003{0}\u0004{0}\u0005{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 6, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{0}\u0003{0}\u0004{0}\u0005{0}\u0006{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 7, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{0}\u0003{0}\u0004{0}\u0005{0}\u0006{0}\u0007{1}",
                boxedSeparator, boxedTrailingSeparator));
        initFieldNumListLevel(list, 8, numberStyle,
            msString.format("\u0000{0}\u0001{0}\u0002{0}\u0003{0}\u0004{0}\u0005{0}\u0006{0}\u0007{0}\u0008{1}",
                boxedSeparator, boxedTrailingSeparator));
    }

    /// <summary>
    /// Fills a list level of a list used to maintain LISTNUM or AUTONUM field functionality.
    /// </summary>
    /// <param name="list"></param>
    /// <param name="levelNumber"></param>
    /// <param name="numberStyle"></param>
    /// <param name="numberFormat"></param>
    private static void initFieldNumListLevel(List list, int levelNumber, /*NumberStyle*/int numberStyle, String numberFormat)
    {
        initFieldNumListLevel(list, levelNumber, numberStyle, numberFormat, true);
    }

    /// <summary>
    /// Fills a list level of a list used to maintain LISTNUM or AUTONUM field functionality.
    /// </summary>
    /// <param name="list"></param>
    /// <param name="levelNumber"></param>
    /// <param name="numberStyle"></param>
    /// <param name="numberFormat"></param>
    /// <param name="useLevelOverride"></param>
    private static void initFieldNumListLevel(
        List list,
        int levelNumber,
        /*NumberStyle*/int numberStyle,
        String numberFormat,
        boolean useLevelOverride)
    {
        ListLevel level;
        if (useLevelOverride)
        {
            ListLevelOverride levelOverride = new ListLevelOverride(list.getDocument(), levelNumber);
            levelOverride.IsFormatting = true;
            list.getOverrides().add(levelOverride);

            level = levelOverride.getListLevel();
        }
        else
        {
            level = list.getListLevels().get(levelNumber);
        }

        ListLevelInitializer.initNumberListLevel(level, levelNumber, numberStyle, numberFormat,
            ListLevelAlignment.LEFT, 0, 0, 0, StyleIndex.NIL, ListTrailingCharacter.NOTHING);
    }

    /// <summary>
    /// Represents the value of an identifier for a <see cref="ListDef"/> instance used to maintain
    /// LISTNUM and AUTONUM fields functionality.
    /// </summary>
    /// <remarks>
    /// This identifier must be out of a normal <see cref="ListDef"/> identifier range.
    /// According to MS-OI29500 (see http://msdn.microsoft.com/en-us/library/ff530966(v=office.12).aspx),
    /// DOC format specification (see http://msdn.microsoft.com/en-us/library/dd907589(v=office.12).aspx) and
    /// RTF format specification (see http://msdn.microsoft.com/en-us/library/office/aa140301(v=office.12).aspx)
    /// the value of -1 can be surely used for this purpose.
    /// WORDSNET-1767 ensures this (see <see cref="ListCollection.GetNextAvailableListDefId()"/>).
    /// </remarks>
    static final int FIELD_NUM_LIST_DEF_ID = -1;
    /// <summary>
    /// Represents the value of an identifier for a <see cref="List"/> instance used to maintain
    /// LISTNUM field functionality.
    /// </summary>
    /// <remarks>
    /// The value could be any as it does not represent a real list in a document.
    /// </remarks>
    private static final int LIST_NUM_LIST_ID = 0;
    /// <summary>
    /// Represents the value of an identifier for a <see cref="List"/> instance used to maintain
    /// AUTONUM field functionality.
    /// </summary>
    /// <remarks>
    /// The value could be any as it does not represent a real list in a document.
    /// </remarks>
    private static final int AUTO_NUM_LIST_ID = 1;
}

