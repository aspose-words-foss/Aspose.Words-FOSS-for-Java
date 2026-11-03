// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ObjToDoubleDictionary;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.ArrayUtil;


class DmlGuides implements IDmlGuideValueProvider
{
    public DmlGuides deepClone()
    {
        DmlGuides lhs = new DmlGuides();
        lhs.mGuides = new ArrayList<DmlGuide>();
        for (DmlGuide guid : mGuides)
            msArrayList.add(lhs.mGuides, guid.deepClone());

        lhs.mAdjustableValues = new ArrayList<DmlGuide>();
        for (DmlGuide guid : mAdjustableValues)
            msArrayList.add(lhs.mAdjustableValues, guid.deepClone());

        // Let's clone calculated values for more reliability.
        lhs.mGuideNameToValue = new ObjToDoubleDictionary<String>();
        ObjToDoubleDictionary<String>.Enumerator enumerator = mGuideNameToValue.iterator();
        while (enumerator.moveNext())
            lhs.mGuideNameToValue.add(enumerator.getCurrentKey(), enumerator.getCurrentValue());
        if (mCommonGuides != null)
        {
            lhs.mCommonGuides = new ArrayList<DmlGuide>();
            for (DmlGuide guid : mCommonGuides)
                lhs.mCommonGuides.add(guid.deepClone());
        }

        return lhs;
    }

    /// <summary>
    /// Gets the value of a guide by its name. If value isn't found then an exception will be thrown.
    /// </summary>
    /// <param name="guideName">Name of the guide.</param>
    public double getValue(String guideName)
    {
        double result = mGuideNameToValue.get(guideName);
        if (ObjToDoubleDictionary.isNullSubstitute(result))
            throw new IllegalArgumentException(msString.format("Guide with name '{0}' not found.", guideName) + "\r\nParameter name: " + guideName);

        return result;
    }

    /// <summary>
    /// Adds the adjustable value to the end of adjustable value list.
    /// </summary>
    void addAdjustableValue(String name, String formula, boolean isPreset)
    {
        DmlGuide guide = getGuideFactory().createGuide(formula, name, isPreset);
        msArrayList.add(mAdjustableValues, guide);
    }

    /// <summary>
    /// Adds the adjustable value to the end of adjustable value list.
    /// </summary>
    void addAdjustableValue(String name, double value)
    {
        addAdjustableValue(name, msString.format("val {0}", FormatterPal.doubleToStr(value)), false);
    }

    /// <summary>
    /// Creates custom adjustable values based on changed preset values.
    /// </summary>
    void createCustomAdjustableValues()
    {
        int count = getAdjustableValues().size();
        for (int i = 0; i < count; i++)
            addAdjustableValue(getAdjustableValues().get(i).getName(), getAdjustableValues().get(i).getFormula().getSource(), false);
    }

    /// <summary>
    /// Returns true if it contains custom adjustable values.
    /// </summary>
    boolean hasCustomAdjustableValues()
    {
        for (DmlGuide guide : getAdjustableValues())
        {
            if (!guide.isPreset())
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true if it contains edited preset values.
    /// </summary>
    /// <param name="presetName">Name of the guide.</param>
    boolean isEditedPresetAdjustableValues(String presetName)
    {
        DmlGeometry presetGeom = DmlGeometryReader.getPresetGeometry(presetName);
        ArrayList<Integer> prstAdjustValues = getAdjustableValues(presetGeom.getGuides().getAdjustableValues());
        ArrayList<Integer> currAdjustValues = getAdjustableValues(getAdjustableValues());

        // Chart have no adjustable values, we need to skip them.
        if (presetGeom.getGuides().getAdjustableValues().size() == 0 ||
            currAdjustValues.size() == 0 ||
            ArrayUtil.isArrayEqual(msArrayList.toArray(prstAdjustValues, new Integer[0]), msArrayList.toArray(currAdjustValues, new Integer[0])))
            return false;

        return true;
    }

    /// <summary>
    /// Gets a list of values from the current adjustable values collection.
    /// </summary>
    /// <param name="adjustValues">Adjustable values collection.</param>
    private static ArrayList<Integer> getAdjustableValues(ArrayList<DmlGuide> adjustValues)
    {
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (DmlGuide guide : adjustValues)
        {
            int value = FormatterPal.tryParseInt(msString.split(guide.getFormula().getSource(), ' ')[1]);
            msArrayList.add(values, value);
        }

        return values;
    }

    /// <summary>
    /// Adds the guide to the end of guides list.
    /// </summary>
    void addGuide(String name, String formula, boolean isPreset)
    {
        DmlGuide guide = getGuideFactory().createGuide(formula, name, isPreset);
        msArrayList.add(mGuides, guide);
    }

    /// <summary>
    /// Calculates all guides.
    /// </summary>
    /// <param name="width">The width of shape.</param>
    /// <param name="height">The height of shape.</param>
    void calculate(double width, double height)
    {
        mGuideNameToValue.clear();
        mGuideNameToValue.set("w", width);
        mGuideNameToValue.set("h", height);
        // Calculate guides in correct sequence
        mCommonGuides = getGuideFactory().createCommonGuides();
        calculateGuides(mCommonGuides);
        calculateGuides(mAdjustableValues);
        calculateGuides(mGuides);
    }


    private void calculateGuides(ArrayList<DmlGuide> guides)
    {
        for (DmlGuide guide : guides)
        {
            // During calculation values of previously calculated guides will be used
            // Old existing values of guides with same name should be replaced by
            // ne ones. This is used in preset shapes. Preset shapes can have several guides
            // with the same name but only latest should be used.
            mGuideNameToValue.set(guide.getName(), guide.getFormula().calculate(this));
        }
    }

    /// <summary>
    /// Gets adjustable values.
    /// </summary>
    /// <remarks>
    /// 20.1.9.5 avLst (List of Shape Adjust Values)
    /// This element specifies
    /// the adjust values that are applied to the specified shape.
    /// An adjust value is simply a guide that has a value based formula
    /// specified. That is, no calculation takes place for an adjust value guide.
    /// Instead, this guide specifies a parameter value that is used for calculations within the shape guides.
    /// </remarks>
    ArrayList<DmlGuide> getAdjustableValues() { return mAdjustableValues; }

    /// <summary>
    /// Gets guides
    /// </summary>
    /// <remarks>
    /// 20.1.9.12 gdLst (List of Shape Guides)
    /// This element specifies all the guides that are used for this shape.
    /// A guide is specified by the gd element and defines a calculated value
    /// that can be used for the construction of the corresponding shape.
    /// </remarks>
    ArrayList<DmlGuide> getGuides() { return mGuides; }

    IDmlGuideFactory getGuideFactory() { return mGuideFactory; }
    void setGuideFactory(IDmlGuideFactory value) { mGuideFactory = value; }

    private ArrayList<DmlGuide> mAdjustableValues = new ArrayList<DmlGuide>();
    private ArrayList<DmlGuide> mCommonGuides;
    private IDmlGuideFactory mGuideFactory = new DmlGuideFactory();
    private ObjToDoubleDictionary<String> mGuideNameToValue = new ObjToDoubleDictionary<String>();
    private ArrayList<DmlGuide> mGuides = new ArrayList<DmlGuide>();
}

