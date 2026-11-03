// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/06/2019 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.IntToObjDictionary;


/// <summary>
/// A base class for classes that represent list items.
/// </summary>
abstract class HtmlListItemBase
{
    protected HtmlListItemBase(HtmlListLevelId listLevelId, /*HtmlMarkerType*/int markerType, String listLabelString)
    {
        Debug.assertTrue(listLevelId != null);

        mListLevelId = listLevelId;
        mMarkerType = markerType;
        mListLabelString = listLabelString;

        mPropertyValues = new IntToObjDictionary<HtmlModelListLevelPropertyValueCollection>();
    }

    /// <summary>
    /// Formats the list.
    /// </summary>
    abstract void postModifyList(HtmlModelList modelList) throws Exception;

    protected static void removeFontAttributesForOrderedList(RunPr runPr)
    {
        // WORDSNET-3456 Ordered lists shouldn't have any specified fonts for labels. They are applicable for bullets.
        runPr.remove(FontAttr.NAME_ASCII);
        runPr.remove(FontAttr.NAME_FAR_EAST);
        runPr.remove(FontAttr.NAME_OTHER);
        runPr.remove(FontAttr.NAME_BI);
    }

    /// <summary>
    /// Determines if list item can be added to the specified list.
    /// </summary>
    boolean canAddToList(HtmlModelList modelList)
    {
        IntToObjDictionary<HtmlModelListLevelPropertyValueCollection>.Enumerator enumerator =
            mPropertyValues.iterator();

        while (enumerator.moveNext())
        {
            HtmlModelListLevel modelListLevel = modelList.getListLevel(enumerator.getCurrentKey());
            HtmlModelListLevelPropertyValueCollection propertyValues = enumerator.getCurrentValue();
            if (!propertyValues.canModifyListLevel(modelListLevel))
            {
                return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Adds list item to the specified list.
    /// </summary>
    void addToList(HtmlModelList modelList) throws Exception
    {
        modelList.addListItem(getListLevelId().getListLevelNumber());

        IntToObjDictionary<HtmlModelListLevelPropertyValueCollection>.Enumerator enumerator =
            mPropertyValues.iterator();

        while (enumerator.moveNext())
        {
            HtmlModelListLevel modelListLevel = modelList.getListLevel(enumerator.getCurrentKey());
            HtmlModelListLevelPropertyValueCollection propertyValues = enumerator.getCurrentValue();
            propertyValues.modifyListLevel(modelListLevel);
        }

        postModifyList(modelList);
    }

    HtmlListLevelId getListLevelId() { return mListLevelId; };

    private HtmlListLevelId mListLevelId;

    String getListLabelString() { return mListLabelString; };

    private String mListLabelString;

    /// <summary>
    /// Determines if list item has a marker specified by ::before or ::after pseudo-elements.
    /// </summary>
    boolean isPseudoElement() { return getMarkerType() == HtmlMarkerType.PSEUDO_ELEMENT; }

    protected void setListLevelType(/*HtmlModelListLevelType*/int modelListLevelType)
    {
        getPropertyValues(getListLevelId().getListLevelNumber()).add(new HtmlListLevelTypePropertyValue(modelListLevelType));
    }

    protected void setNumberFormat(String numberFormat)
    {
        getPropertyValues(getListLevelId().getListLevelNumber()).add(new HtmlNumberFormatPropertyValue(numberFormat));
    }

    protected void setNumberStyle(int listLevelNumber, /*NumberStyle*/int numberStyle)
    {
        getPropertyValues(listLevelNumber).add(new HtmlNumberStylePropertyValue(numberStyle));
    }

    protected void setNumberValue(int listLevelNumber, int numberValue)
    {
        if (listLevelNumber != getListLevelId().getListLevelNumber())
        {
            getPropertyValues(listLevelNumber).add(new HtmlCurrentNumberValuePropertyValue(numberValue));
        }
        else
        {
            getPropertyValues(listLevelNumber).add(new HtmlNextNumberValuePropertyValue(numberValue));
        }
    }

    protected void setPictureBulletId(int pictureBulletId)
    {
        getPropertyValues(getListLevelId().getListLevelNumber()).add(new HtmlPictureBulletIdPropertyValue(pictureBulletId));
    }

    protected /*HtmlMarkerType*/int getMarkerType() { return mMarkerType; };

    private /*HtmlMarkerType*/int mMarkerType;

    private HtmlModelListLevelPropertyValueCollection getPropertyValues(int modelListLevelNumber)
    {
        if (!mPropertyValues.containsKey(modelListLevelNumber))
        {
            HtmlModelListLevelPropertyValueCollection propertyValues =
                new HtmlModelListLevelPropertyValueCollection();

            mPropertyValues.add(modelListLevelNumber, propertyValues);

            return propertyValues;
        }

        return mPropertyValues.get(modelListLevelNumber);
    }

    private /*final*/ IntToObjDictionary<HtmlModelListLevelPropertyValueCollection> mPropertyValues;
}

