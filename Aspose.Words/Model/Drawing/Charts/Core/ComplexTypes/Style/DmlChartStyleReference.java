// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 2.8.3.8 CT_StyleReference [MS-ODRAWXML]
/// A reference to the document's theme style matrix. This element is identical to CT_StyleMatrixReference 
/// ([ISO/IEC29500-1:2012] section A.4.1) but also allows for a CT_StyleColor (section 2.8.3.6) element and 
/// a modifier list.
/// </summary>
class DmlChartStyleReference extends DmlEffectReference
{
    /// <summary>
    /// Clones this <see cref="DmlChartStyleReference"/> object.
    /// </summary>
    /*override*/ DmlStyleReferenceBase deepClone()
    {
        DmlChartStyleReference lhs = (DmlChartStyleReference)super.deepClone();

        if (mModifiers != null)
        {
            lhs.mModifiers = new String[mModifiers.length];
            for (int i = 0; i < mModifiers.length; i++)
                lhs.mModifiers[i] = mModifiers[i];
        }

        return lhs;
    }

    /// <summary>
    /// Represents mods: an ST_StyleReferenceModifierList attribute that specifies a list of modifiers for 
    /// this reference.
    /// </summary>
    String[] getModifiers() { return mModifiers; }
    void setModifiers(String[] value) { mModifiers = value; }

    private String[] mModifiers;
}

