// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents 2.8.1.2 colorStyle, 2.8.3.2 CT_ColorStyle [MS-ODRAWXML]
/// The complex type specifies colors used to resolve CT_StyleColor (section 2.8.3.6) colors in a CT_ChartStyle
/// (section 2.8.3.1). The color style consists of a list of colors, a list of variations and a method for
/// iterating the total set of colors.
/// </summary>
class DmlChartColorStyle extends DmlExtensionListSource implements Cloneable
{
    /// <summary>
    /// Clones this <see cref="DmlChartColorStyle"/> object.
    /// </summary>
    DmlChartColorStyle deepClone()
    {
        DmlChartColorStyle lhs = (DmlChartColorStyle)memberwiseClone();
        if (mColors != null)
        {
            lhs.mColors = new DmlColor[mColors.length];
            for (int i = 0; i < mColors.length; i++)
                lhs.mColors[i] = mColors[i].deepClone();
        }

        if (mVariations != null)
        {
            lhs.mVariations = new DmlChartColorStyleVariation[mVariations.length];
            for (int i = 0; i < mVariations.length; i++)
                lhs.mVariations[i] = mVariations[i].deepClone();
        }

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Gets the DML color at the specified index.
    /// </summary>
    DmlColor getColor(int index)
    {
        Debug.assertTrue(mColors.length > 0);

        int colorIndex = index % mColors.length;
        DmlColor color = mColors[colorIndex].deepClone();

        int variationIndex = index / mColors.length;
        if (mVariations.length > 0)
        {
            variationIndex %= mVariations.length;

            ArrayList<IDmlColorModifier> colorModifiers = mVariations[variationIndex].getColorModifiers();
            if (color.getColorModifiers() == null)
                color.setColorModifiers(new ArrayList<IDmlColorModifier>(colorModifiers.size()));

            for (IDmlColorModifier colorModifier : colorModifiers)
                msArrayList.add(color.getColorModifiers(), colorModifier.deepClone());
        }

        return color;
    }

    /// <summary>
    /// Gets or sets style colors. A color style can contain 6 colors.
    /// </summary>
    DmlColor[] getColors() { return mColors; }
    void setColors(DmlColor[] value) { mColors = value; }

    /// <summary>
    /// Gets or sets color variations.
    /// Represents variation: a CT_ColorStyleVariation element that specifies a variation applied to all colors to
    /// create a longer set of colors without having to explicitly list them all.
    /// </summary>
    DmlChartColorStyleVariation[] getVariations() { return mVariations; }
    void setVariations(DmlChartColorStyleVariation[] value) { mVariations = value; }

    /// <summary>
    /// Represents meth: an ST_ColorStyleMethod attribute that specifies the method for mapping an index for
    /// an element in a chart to the total set of colors contained in this CT_ColorStyle.
    /// </summary>
    String getMethod() { return mMethod; }
    void setMethod(String value) { mMethod = value; }

    /// <summary>
    /// Represents id: an unsignedInt ([XMLSCHEMA2] section 3.3.22) attribute that specifies the identifier for
    /// this CT_ColorStyle.
    /// </summary>
    String getId() { return mId; }
    void setId(String value) { mId = value; }

    private DmlColor[] mColors;
    private DmlChartColorStyleVariation[] mVariations;
    private String mMethod;
    private String mId;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

