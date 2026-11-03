// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.FormatterPal;
import com.aspose.MathUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ArgumentUtil;
import com.aspose.ListUtil;
import java.util.ArrayList;


/// <summary>
/// Represents a base class for colors.
/// </summary>
abstract class DmlColor
{
    /// <summary>
    /// Creates DmlColor from ARGB.
    /// </summary>
    /// <remarks>
    /// Use this overload to avoid scaling "opacity" using "A" part of the "DrColor"
    /// because "opacity" uses 2 bytes to hold value unlike the alpha part of DrColor uses only one byte.
    /// </remarks>
    /// <param name="alpha">Opacity, valid range is from 0 to 1.</param>
    /// <param name="r">R</param>
    /// <param name="g">G</param>
    /// <param name="b">B</param>
    public static DmlColor createFromArgb(double alpha, int r, int g, int b)
    {
        DmlHexRgbColor color = new DmlHexRgbColor();

        // Exclude drColor.A, it will be added as color modifier.
        DrColor drColorWithoutA = new DrColor(0, r, g, b);
        color.setValue(FormatterPal.intToStrX6(drColorWithoutA.toArgb()));

        // Add modifier for alpha.
        if (!MathUtil.areEqual(alpha, 1.0))
            addAlphaModifier(color, alpha);

        return color;
    }

    /// <summary>
    /// Creates DmlColor from DrColor.
    /// </summary>
    public static DmlColor createFromDrColor(DrColor drColor)
    {
        return createFromArgb((double)drColor.getA()/255.0, drColor.getR(), drColor.getG(), drColor.getB());
    }

    /// <summary>
    /// Appends "Alpha" modifier to color.
    /// </summary>
    /// <param name="color">Color to add modifier.</param>
    /// <param name="value">Modifier value.</param>
    static void addAlphaModifier(DmlColor color, double value)
    {
        DmlAlpha alpha = new DmlAlpha();
        alpha.setValue(value);
        msArrayList.add(color.getColorModifiers(), alpha);
    }

    public DrColor createDrColor(IThemeProvider themeProvider, IDmlColorModifier additionalModifier)
    {
        DrColor color = createUnmodifiedDrColor(themeProvider);
        color = applyColorModifiersTo(color, additionalModifier);
        return color;
    }

    public abstract DmlColor deepClone();

    public /*virtual*/ void applyStyleColor(DmlColor styleColor)
    {
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlColor value = (DmlColor)obj;

        return (getColorType() == value.getColorType()) && ListUtil.checkAreEquals(value.getColorModifiers(), getColorModifiers());
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getColorType();
        hash ^= getModifiersHashCode();
        return hash;
    }

    /// <summary>
    /// Gets the base color without modifiers.
    /// </summary>
    DrColor createUnmodifiedDrColor(IThemeProvider themeProvider)
    {
        return createUnmodifiedColor(themeProvider);
    }

    /// <summary>
    /// Gets or sets an alpha modifier.
    /// </summary>
    DmlAlpha getAlpha()
    {
        int index = indexOf(DmlColorModifierType.ALPHA);
        if (index == -1)
            return null;

        return (DmlAlpha)getColorModifiers().get(index);
    }
    void setAlpha(DmlAlpha value)
    {
        int index = indexOf(DmlColorModifierType.ALPHA);
        if (index != -1)
        {
            if (value != null)
                getColorModifiers().set(index, value);
            else
                getColorModifiers().remove(index);
        }
        else
        {
            if (value != null)
                msArrayList.add(getColorModifiers(), value);
        }
    }

    /// <summary>
    /// Updates an alpha modifier with a specified value.
    /// </summary>
    /// <remarks>If there is no alpha modifier, then creates a new one.</remarks>
    DmlColor updateAlpha(double value)
    {
        DmlAlpha alpha = getAlpha();
        if (alpha == null)
        {
            alpha = new DmlAlpha();
            msArrayList.add(getColorModifiers(), alpha);
        }
        alpha.setValue(value);

        return this;
    }

    /// <summary>
    /// Returns color modifier of the specified type or <b>null</b> if not found.
    /// </summary>
    IDmlColorModifier getColorModifier(/*DmlColorModifierType*/int type)
    {
        int index = indexOf(type);
        return (index >= 0) ? getColorModifiers().get(index) : null;
    }

    protected int getModifiersHashCode()
    {
        int hash = 0;
        for (IDmlColorModifier modifier : getColorModifiers())
            hash ^= modifier.hashCode();

        return hash;
    }

    /// <summary>
    /// Returns color modifier index of the specified type or -1 if not found.
    /// </summary>
    private int indexOf(/*DmlColorModifierType*/int type)
    {
        for (int i = 0; i < getColorModifiers().size(); i++)
        {
            if (getColorModifiers().get(i).getModifierType() == type)
                return i;
        }

        return -1;
    }

    private DrColor applyColorModifiersTo(DrColor color, IDmlColorModifier additionalModifier)
    {
        for (IDmlColorModifier colorModificator : getColorModifiers())
            color = colorModificator.modify(color);

        if (additionalModifier != null)
            color = additionalModifier.modify(color);

        return color;
    }

    protected abstract DrColor createUnmodifiedColor(IThemeProvider themeProvider);

    protected void copyColorModifiersTo(DmlColor color)
    {
        ArrayList<IDmlColorModifier> newModifiers = new ArrayList<IDmlColorModifier>(getColorModifiers().size());
        for (IDmlColorModifier colorModifier : getColorModifiers())
            msArrayList.add(newModifiers, colorModifier.deepClone());

        color.setColorModifiers(newModifiers);
    }

    public ArrayList<IDmlColorModifier> getColorModifiers()
    {
        if (mColorModifiers == null)
            mColorModifiers = new ArrayList<IDmlColorModifier>();
        return mColorModifiers;
    }
    public void setColorModifiers(ArrayList<IDmlColorModifier> value) { mColorModifiers = value; }

    /// <summary>
    /// Returns true if this color is empty.
    /// </summary>
    /*virtual*/ boolean isEmpty() { return false; }

    /// <summary>
    /// Gets Empty color. This is full transparency black color of <see cref="DmlHexRgbColor"/> type.
    ///</summary>
    static /*final*/ DmlColor EMPTY;

    public abstract /*DmlColorType*/int getColorType();

    private ArrayList<IDmlColorModifier> mColorModifiers;

    static/* DmlColor()*/
    {
        EMPTY = DmlHexRgbColor.fromDrColor(DrColor.EMPTY);
    }
}

