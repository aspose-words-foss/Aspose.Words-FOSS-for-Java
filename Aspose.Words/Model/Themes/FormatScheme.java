// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2011 by Alexey Titov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 20.1.4.1.14 fmtScheme (Format Scheme)
/// This element contains the background fill styles, 
/// effect styles, fill styles, and line styles which 
/// define the style matrix for a theme. The style matrix 
/// consists of subtle, moderate, and intense fills, lines, 
/// and effects. The background fills are not generally 
/// thought of to directly be associated with the matrix, 
/// but do play a role in the style of the overall document. 
/// Usually, a given object chooses a single line style, 
/// a single fill style, and a single effect style in order 
/// to define the overall final look of the object.
/// </summary>
class FormatScheme implements Cloneable
{
    /// <summary>
    /// Clones this instance of format scheme.
    /// </summary>
    FormatScheme deepClone()
    {
        FormatScheme lhs = (FormatScheme)memberwiseClone();

        lhs.mBackgroundFillStyles = new ArrayList<DmlFill>();
        for (DmlFill fill : mBackgroundFillStyles)
            msArrayList.add(lhs.mBackgroundFillStyles, fill.deepClone());

        lhs.mFillStyles = new ArrayList<DmlFill>();
        for (DmlFill fill : mFillStyles)
            msArrayList.add(lhs.mFillStyles, fill.deepClone());

        lhs.mLineStyles = new ArrayList<DmlOutline>();
        for (DmlOutline outline : mLineStyles)
            msArrayList.add(lhs.mLineStyles, outline.deepClone());

        lhs.mEffectStyles = msArrayList.ctor(mEffectStyles);

        return lhs;
    }

    DmlFill getBackgroundFillStyle(int index)
    {
        if (mBackgroundFillStyles.size() == 0)
            return new DmlNoFill();
        // If index is greater than number of elements 
        // the return the last element
        if (index >= mBackgroundFillStyles.size())
            index = mBackgroundFillStyles.size() - 1;

        return mBackgroundFillStyles.get(index);
    }

    int addBackgroundFillStyle(DmlFill fillStyle)
    {
        msArrayList.add(mBackgroundFillStyles, fillStyle);
        return mBackgroundFillStyles.size() - 1;
    }

    DmlFill getFillStyle(int index)
    {
        if (mFillStyles.size() == 0)
            return new DmlNoFill();

        // If index is greater than number of elements 
        // the return the last element
        if (index >= mFillStyles.size())
            index = mFillStyles.size() - 1;

        return mFillStyles.get(index);
    }

    int addFillStyle(DmlFill fillStyle)
    {
        msArrayList.add(mFillStyles, fillStyle);
        return mFillStyles.size() - 1;
    }

    DmlOutline getLineStyle(int index)
    {
        if (mLineStyles.size() == 0)
            return new DmlOutline(); // Return outline with no fill.

        // If index is greater than number of elements 
        // the return the last element
        if (index >= mLineStyles.size())
            index = mLineStyles.size() - 1;
        return mLineStyles.get(index);
    }

    int addLineStyle(DmlOutline lineStyle)
    {
        msArrayList.add(mLineStyles, lineStyle);
        return mLineStyles.size() - 1;
    }

    EffectStyle getEffectStyle(int index)
    {
        if (mEffectStyles.size() == 0)
            return new EffectStyle();

        // If index is greater than number of elements 
        // the return the last element
        if (index >= mEffectStyles.size())
            index = mEffectStyles.size() - 1;
        return mEffectStyles.get(index);
    }

    int addEffectStyle(EffectStyle effectStyle)
    {
        msArrayList.add(mEffectStyles, effectStyle);
        return mEffectStyles.size() - 1;
    }

    /// <summary>
    /// Defines the name for the format scheme. 
    /// The name is simply a human readable string which 
    /// identifies the format scheme in the user interface.
    /// </summary>
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    /// <summary>
    /// Returns number of background fill styles.
    /// </summary>
    int getBackgroundFillStyleCount() { return mBackgroundFillStyles.size(); }

    /// <summary>
    /// Returns number of fill styles.
    /// </summary>
    int getFillStyleCount() { return mFillStyles.size(); }

    /// <summary>
    /// Returns number of line styles.
    /// </summary>
    int getLineStyleCount() { return mLineStyles.size(); }

    /// <summary>
    /// Returns number of effect styles.
    /// </summary>
    int getEffectStyleCount() { return mEffectStyles.size(); }

    private ArrayList<DmlFill> mBackgroundFillStyles = new ArrayList<DmlFill>();
    private ArrayList<DmlFill> mFillStyles = new ArrayList<DmlFill>();
    private ArrayList<DmlOutline> mLineStyles = new ArrayList<DmlOutline>();
    private ArrayList<EffectStyle> mEffectStyles = new ArrayList<EffectStyle>();
    private String mName;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
