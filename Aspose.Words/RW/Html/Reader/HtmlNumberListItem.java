// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToIntDictionary;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ms.System.Convert;
import java.lang.NumberFormatException;
import com.aspose.words.net.System.OverflowException;


/// <summary>
/// Represents list item with a number.
/// </summary>
class HtmlNumberListItem extends HtmlListItemBase
{
    private HtmlNumberListItem(
        HtmlListLevelId listLevelId,
        /*HtmlMarkerType*/int markerType,
        String listLabelString,
        String numberFormat,
        IntToIntDictionary levelNumberToNumberStyleMap,
        IntToIntDictionary levelNumberToNumberValueMap)
    {
    	super(listLevelId, markerType, listLabelString);
	
        setListLevelType(HtmlModelListLevelType.NUMBER);
        setNumberFormat(numberFormat);

        IntToIntDictionary.Enumerator numberStyleEnumerator = levelNumberToNumberStyleMap.iterator();
        while (numberStyleEnumerator.moveNext())
        {
            /*NumberStyle*/int numberStyle = (/*NumberStyle*/int)numberStyleEnumerator.getCurrentValue();
            setNumberStyle(numberStyleEnumerator.getCurrentKey(), numberStyle);
        }

        IntToIntDictionary.Enumerator numberValueEnumerator = levelNumberToNumberValueMap.iterator();
        while (numberValueEnumerator.moveNext())
        {
            setNumberValue(numberValueEnumerator.getCurrentKey(), numberValueEnumerator.getCurrentValue());
        }
    }

    /// <summary>
    /// Creates <see cref="HtmlNumberListItem"/> object from CSS generated content.
    /// </summary>
    static HtmlNumberListItem create(
        DocumentFormatter documentFormatter,
        String generatedContent,
        int htmlListLevelNumber)
    {
        documentFormatter.switchToPart(HtmlElementPart.BEFORE, false);
        int listLevelsCount = documentFormatter.getListLevelCount();
        documentFormatter.switchToPart(HtmlElementPart.ELEMENT, false);

        if (listLevelsCount == 0)
        {
            return null;
        }

        HtmlListLevelId listLevelId = new HtmlListLevelId(htmlListLevelNumber);

        int nextListLevelNumber = listLevelId.getListLevelNumber() + 1;
        int skipListLevelsCount = Math.max(0, listLevelsCount - nextListLevelNumber);
        int startListLevelNumber = Math.max(0, nextListLevelNumber - listLevelsCount);

        documentFormatter.switchToPart(HtmlElementPart.BEFORE, false);

        GeneratedContentListLabelInfo listLabelInfo =
            documentFormatter.getListLabelInfo(skipListLevelsCount, startListLevelNumber);

        documentFormatter.switchToPart(HtmlElementPart.ELEMENT, false);

        HtmlNumberListItem numberListItem = new HtmlNumberListItem(
            listLevelId,
            HtmlMarkerType.PSEUDO_ELEMENT,
            generatedContent,
            listLabelInfo.getNumberFormat(),
            listLabelInfo.getLevelNumberToNumberStyleMap(),
            listLabelInfo.getLevelNumberToNumberValueMap());

        return numberListItem;
    }

    /// <summary>
    /// Creates <see cref="HtmlNumberListItem"/> object from &lt;ol&gt; HTML element.
    /// </summary>
    static HtmlNumberListItem create(
        int htmlListLevelNumber,
        int listLevelNumberValue,
        /*NumberStyle*/int listLevelNumberStyle)
    {
        HtmlListLevelId listLevelId = new HtmlListLevelId(htmlListLevelNumber);

        String listLevelNumberString = NumberConverter.numberToString(listLevelNumberValue, listLevelNumberStyle, true);
        String listLabelString = listLevelNumberString + ".";

        String numberFormat = msString.newString((char)listLevelId.getListLevelNumber(), 1) + ".";

        IntToIntDictionary levelNumberToNumberStyleMap = new IntToIntDictionary();
        levelNumberToNumberStyleMap.add(listLevelId.getListLevelNumber(), (int)listLevelNumberStyle);

        IntToIntDictionary levelNumberToNumberValueMap = new IntToIntDictionary();
        levelNumberToNumberValueMap.add(listLevelId.getListLevelNumber(), listLevelNumberValue);

        HtmlNumberListItem numberListItem = new HtmlNumberListItem(
            listLevelId,
            HtmlMarkerType.HTML,
            listLabelString,
            numberFormat,
            levelNumberToNumberStyleMap,
            levelNumberToNumberValueMap);

        return numberListItem;
    }

    /// <summary>
    /// Creates <see cref="HtmlNumberListItem"/> object from -aw-* CSS properties.
    /// </summary>
    static HtmlNumberListItem create(
        String listNumberFormatValue,
        String listNumberValuesValue,
        String listNumberStylesValue,
        int htmlListLevelNumber)
    {
        HtmlListLevelId listLevelId = new HtmlListLevelId(htmlListLevelNumber);
        String numberFormat = getNumberFormat(listNumberFormatValue);

        String[] numberStyles = parseValues(listNumberStylesValue);
        IntToIntDictionary levelNumberToNumberStyleMap = getLevelNumberToNumberStyleMap(
            numberStyles,
            numberFormat,
            listLevelId.getListLevelNumber());

        String[] numberValues = parseValues(listNumberValuesValue);
        IntToIntDictionary levelNumberToNumberValueMap = getLevelNumberToNumberValueMap(
            numberValues,
            numberFormat,
            levelNumberToNumberStyleMap);

        String listLabelString =
            getListLabelString(numberFormat, levelNumberToNumberStyleMap, levelNumberToNumberValueMap);

        HtmlNumberListItem numberListItem = new HtmlNumberListItem(
            listLevelId,
            HtmlMarkerType.AW,
            listLabelString,
            numberFormat,
            levelNumberToNumberStyleMap,
            levelNumberToNumberValueMap);

        return numberListItem;
    }

    /*override*/ void postModifyList(HtmlModelList modelList)
    {
        HtmlModelListLevel modelListLevel = modelList.getListLevel(getListLevelId().getListLevelNumber());
        removeFontAttributesForOrderedList(modelListLevel.getListLevel().getRunPr());
    }

    private static String getNumberFormat(String numberFormatValue)
    {
        StringBuilder builder = new StringBuilder(numberFormatValue);
        for (int i = ListLevel.MIN_LEVEL; i < ListLevel.MAX_LEVELS; i++)
        {
            msStringBuilder.replace(builder, HtmlUtil.getListLevelNumberString(i), msString.newString((char)i, 1));
        }

        return builder.toString();
    }

    private static IntToIntDictionary getLevelNumberToNumberStyleMap(
        String[] numberStyles,
        String numberFormat,
        int currentListLevelNumber)
    {
        IntToIntDictionary levelNumberToNumberStyleMap = new IntToIntDictionary();

        int numberIndex = 0;

        for (int i = ListLevel.MIN_LEVEL; i < ListLevel.MAX_LEVELS; i++)
        {
            // Number style for current list level should be always specified.
            char levelNumberChar = (char)i;
            if ((numberFormat.indexOf(levelNumberChar) != -1) || (i == currentListLevelNumber))
            {
                if (numberIndex < numberStyles.length)
                {
                    levelNumberToNumberStyleMap.add(i, (int)getNumberStyle(numberStyles[numberIndex]));
                }
                else
                {
                    levelNumberToNumberStyleMap.add(i, (int)LIST_LEVEL_NUMBER_STYLE_DEFAULT);
                }

                numberIndex++;
            }
        }

        return levelNumberToNumberStyleMap;
    }

    private static IntToIntDictionary getLevelNumberToNumberValueMap(
        String[] numberValues,
        String numberFormat,
        IntToIntDictionary levelNumberToNumberStyleMap)
    {
        IntToIntDictionary levelNumberToNumberValueMap = new IntToIntDictionary();

        int numberIndex = 0;

        for (int i = ListLevel.MIN_LEVEL; i < ListLevel.MAX_LEVELS; i++)
        {
            char levelNumberChar = (char)i;
            if ((numberFormat.indexOf(levelNumberChar) != -1) &&
                ListLabelUtil.hasCounter((/*NumberStyle*/int)levelNumberToNumberStyleMap.get(i)))
            {
                if (numberIndex < numberValues.length)
                {
                    levelNumberToNumberValueMap.add(i, getNumberValue(numberValues[numberIndex]));
                }
                else
                {
                    levelNumberToNumberValueMap.add(i, LIST_LEVEL_NUMBER_VALUE_DEFAULT);
                }

                numberIndex++;
            }
        }

        return levelNumberToNumberValueMap;
    }

    private static String[] parseValues(String values)
    {
        return msString.split(values, new char[] { ' ' }, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
    }

    private static /*NumberStyle*/int getNumberStyle(String numberStyleValue)
    {
        return DocxEnum.docxToNumberStyle(numberStyleValue);
    }

    private static int getNumberValue(String numberValueValue)
    {
        int numberValue;
        try
        {
            numberValue = Convert.toInt32(numberValueValue);
        }
        catch (NumberFormatException e)
        {
            numberValue = LIST_LEVEL_NUMBER_VALUE_DEFAULT;
        }
        catch (OverflowException e)
        {
            numberValue = LIST_LEVEL_NUMBER_VALUE_DEFAULT;
        }

        return numberValue;
    }

    private static String getListLabelString(
        String numberFormat,
        IntToIntDictionary levelNumberToNumberStyleMap,
        IntToIntDictionary levelNumberToNumberValueMap)
    {
        StringBuilder listLabelStringBuilder = new StringBuilder(numberFormat);

        for (int i = ListLevel.MIN_LEVEL; i < ListLevel.MAX_LEVELS; i++)
        {
            char levelNumberChar = (char)i;
            if (numberFormat.indexOf(levelNumberChar) != -1)
            {
                /*NumberStyle*/int numberStyle = (/*NumberStyle*/int)levelNumberToNumberStyleMap.get(i);
                int numberValue = levelNumberToNumberValueMap.get(i);

                String numberString = NumberConverter.numberToString(numberValue, numberStyle, true);

                msStringBuilder.replace(listLabelStringBuilder, Character.toString(levelNumberChar), numberString);
            }
        }

        return listLabelStringBuilder.toString();
    }

    private static final int LIST_LEVEL_NUMBER_VALUE_DEFAULT = 1;
    private static final /*NumberStyle*/int LIST_LEVEL_NUMBER_STYLE_DEFAULT = NumberStyle.ARABIC;
}

