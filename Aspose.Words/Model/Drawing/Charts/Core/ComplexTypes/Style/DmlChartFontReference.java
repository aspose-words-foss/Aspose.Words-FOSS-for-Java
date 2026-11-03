// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 2.8.3.4 CT_FontReference [MS-ODRAWXML]
/// A reference to the document's font scheme. This element is identical to CT_FontReference ([ISO/IEC29500-1:2012]
/// section A.4.1) but also allows for a CT_StyleColor (section 2.8.3.6) element and a modifier list.
/// </summary>
class DmlChartFontReference extends DmlFontReference
{
    /// <summary>
    /// Clones this <see cref="DmlChartFontReference"/> object.
    /// </summary>
    /*override*/ DmlStyleReferenceBase deepClone()
    {
        DmlChartFontReference lhs = (DmlChartFontReference)super.deepClone();

        if (mModifiers != null)
        {
            lhs.mModifiers = new String[mModifiers.length];
            for (int i = 0; i < mModifiers.length; i++)
                lhs.mModifiers[i] = mModifiers[i];
        }

        return lhs;
    }

    /// <summary>
    /// Gets <see cref="DmlSolidFill"/>
    /// </summary>
    DmlSolidFill getDmlSolidFill()
    {
        DmlSolidFill fill = new DmlSolidFill();
        fill.setColor(getColor());
        return fill;
    }

    /// <summary>
    /// Represents mods: an ST_StyleReferenceModifierList attribute that specifies a list of modifiers for 
    /// this reference.
    /// </summary>
    String[] getModifiers() { return mModifiers; }
    void setModifiers(String[] value) { mModifiers = value; }

    private String[] mModifiers;
}

