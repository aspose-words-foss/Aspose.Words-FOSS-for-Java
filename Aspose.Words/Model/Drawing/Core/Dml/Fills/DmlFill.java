// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import com.aspose.DrColor;
import com.aspose.Debug;


/// <summary>
/// Base class for DrawingML fills.
/// </summary>
abstract class DmlFill implements IDmlColorable, IFill, IComplexAttr
{
    /// <summary>
    /// Creates <see cref="DmlFill"/> of a specified type.
    /// </summary>
    static DmlFill create(/*DmlFillType*/int type)
    {
        switch (type)
        {
            case DmlFillType.BLIP_FILL:
                return new DmlBlipFill();
            case DmlFillType.GRADIENT_FILL:
                return new DmlGradientFill();
            case DmlFillType.GROUP_FILL:
                return new DmlGroupFill();
            case DmlFillType.NO_FILL:
                return new DmlNoFill();
            case DmlFillType.PATTERN_FILL:
                return new DmlPatternFill();
            case DmlFillType.SOLID_FILL:
                return new DmlSolidFill();
            case DmlFillType.STYLE_FILL:
                return new DmlStyleFill();
            default:
                throw new IllegalArgumentException(null + "\r\nParameter name: " + "type" + "\r\nParameter value: " + type);
        }
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlFill value = (DmlFill)obj;

        return (value.getDmlFillType() == getDmlFillType());
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getDmlFillType();
        return hash;
    }

    /// <summary>
    /// Set style color to any color placeholders used in the fill.
    /// </summary>
    public /*virtual*/ void applyStyleColor(DmlColor styleColor)
    {
        // By default does nothing.
    }

    abstract DmlFill deepClone();

    /// <summary>
    /// Resolves theme colors to a concrete RGB colors using a specified theme provider.
    /// </summary>
    /*virtual*/ void resolveThemeColors(IThemeProvider themeProvider)
    {
        // Resolve ForeColor.
        DmlSchemeColor schemeColor = ms.as(getDmlColorInternal(), DmlSchemeColor.class);
        if (schemeColor != null)
            setDmlColorInternal(schemeColor.resolve(themeProvider));

        // Resolve BackColor.
        schemeColor = ms.as(getDmlColor2Internal(), DmlSchemeColor.class);
        if (schemeColor != null)
            setDmlColor2Internal(schemeColor.resolve(themeProvider));
    }

    /// <summary>
    /// Gets type of the Dml Fill.
    /// </summary>
    abstract /*DmlFillType*/int getDmlFillType();

    //>>>>>>>> #region  IFill interface implementation.

    public void setImageBytes(byte[] imageBytes)
    {
        if (getDmlFillType() == DmlFillType.BLIP_FILL)
            ((DmlBlipFill)this).getBlip().setEmbedImage(imageBytes);
    }

    /// <summary>
    /// Gets or sets a color.
    /// </summary>
    public DrColor getColorInternal() { return (getDmlColorInternal() != null) ? getDmlColorInternal().createDrColor(getThemeProvider(), null) : DrColor.EMPTY; }
    public void setColorInternal(DrColor value) { setDmlColorInternal(DmlHexRgbColor.fromDrColor(value)); }


    /// <summary>
    /// Gets a base color without modifiers.
    /// </summary>
    public DrColor getColorInternalUnmodified() { return (getDmlColorInternal() != null) ? getDmlColorInternal().createUnmodifiedDrColor(getThemeProvider()) : DrColor.EMPTY; }

    public DrColor getColor2Internal() { return (getDmlColor2Internal() != null) ? getDmlColor2Internal().createDrColor(getThemeProvider(), null) : DrColor.EMPTY; }
    public void setColor2Internal(DrColor value) { setDmlColor2Internal(DmlHexRgbColor.fromDrColor(value)); }

    /// <summary>Gets the array of custom gradient colors.</summary>
    /// <remarks>
    /// <p>The default value is null.</p>
    /// </remarks>
    public /*virtual*/ GradientColor[] getGradientColors() { return null; }

    /// <summary>
    /// Gets or sets fill color opacity.
    /// </summary>
    public /*virtual*/ double getOpacity()
    {
        if (getDmlColorInternal() != null)
        {
            DmlAlpha alpha = getDmlColorInternal().getAlpha();
            if (alpha != null)
                return alpha.getValue();
        }

        return 1.0;
    }
    public /*virtual*/ void setOpacity(double value)
    {
        if (getDmlColorInternal() != null)
            getDmlColorInternal().updateAlpha(value);
    }

    /// <summary>
    /// Gets or sets fill opacity of the second fill color.
    /// </summary>
    public /*virtual*/ double getOpacity2()
    {
        if (getDmlColor2Internal() != null)
        {
            DmlAlpha alpha = getDmlColor2Internal().getAlpha();
            if (alpha != null)
                return alpha.getValue();
        }

        return 1.0;
    }
    public /*virtual*/ void setOpacity2(double value)
    {
        if (getDmlColor2Internal() != null)
            getDmlColor2Internal().updateAlpha(value);
    }

    /// <summary>
    /// Gets or sets a boolean value indicating whether a parent object is filled.
    /// </summary>
    public /*virtual*/ boolean getOn()
    {
        return true;
    }
    public /*virtual*/ void setOn(boolean value)
    {
        // Check if nothing to do.
        if (value)
            return;

        // IN. Actually, Word also saves an existing fill to the 'a14:hiddenFill' extension.
        getParent().setFill(new DmlNoFill());
    }

    /// <summary>
    /// <p>Determines whether the fill rotates with the specified shape.</p>
    /// <p>The default value is <c>false</c>.</p>
    /// </summary>
    /// <dev>
    /// IN. Word VBA allows to get this property even for fills that cannot have this property by returning <c>false</c>.
    /// But setting this property for such fills throws an exception.
    /// </dev>
    public /*virtual*/ boolean getRotateWithShape() { return false; }
    public /*virtual*/ void setRotateWithShape(boolean value) { throw new IllegalStateException(MSG_VALUE_OUT_OF_RANGE); }

    /// <summary>
    /// Gets the raw bytes of the fill texture or pattern.
    /// </summary>
    /// <remarks>
    /// <p>The default value is null.</p>
    /// </remarks>
    
    public /*virtual*/ byte[] getImageBytes() throws Exception { return null; }

    public /*virtual*/ double getAngle() { return 0.0; }
    public /*virtual*/ void setAngle(double value)
    {
        // Ignore.
    }

    /// <summary>
    /// Gets the gradient style for the fill.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientstyle
    /// </summary>
    public /*virtual*/ /*GradientStyle*/int getGradientStyle() { return GradientStyle.NONE; }

    /// <summary>
    /// Gets the gradient variant for the fill as an integer value from 1 to 4 for most gradient fills or 0 if not defined.
    /// https://docs.microsoft.com/en-us/office/vba/api/word.fillformat.gradientvariant
    /// </summary>
    public /*virtual*/ /*GradientVariant*/int getGradientVariant() { return GradientVariant.NONE; }

    /// <summary>
    /// Gets or sets the parent object.
    /// </summary>
    public IFillable getParent() { return mParent; }
    public void setParent(IFillable value) { mParent = value; }

    public boolean getLockAspectRatio()
    {
        // Return false for Dml shape fill. Cannot find this attribute for Dml blip fill.
        return false;
    }
    public void setLockAspectRatio(boolean value)
    {
        // Do nothing for Dml shape fill.
    }

    public double getFocusLeft()
    {
        // Return zero for Dml shape fill. Cannot find suitable attribute for Dml fill.
        return 0.0;
    }
    public void setFocusLeft(double value)
    {
        // Do nothing for Dml shape fill.
    }

    public double getFocusTop()
    {
        // Return zero for Dml shape fill. Cannot find suitable attribute for Dml fill.
        return 0.0;
    }
    public void setFocusTop(double value)
    {
        // Do nothing for Dml shape fill.
    }

    public int getFocus()
    {
        // Return zero for Dml shape fill. Cannot find suitable attribute for Dml fill.
        return 0;
    }
    public void setFocus(int value)
    {
        // Do nothing for Dml shape fill.
    }

    /// <summary>
    /// Gets or sets the type of fill.
    /// </summary>
    public /*virtual*/ /*FillTypeCore*/int getFillType() { return FillTypeCore.SOLID; }
    public /*virtual*/ void setFillType(/*FillTypeCore*/int value)
    {
        // Do nothing for DML shape fill.
        Debug.assertTrue(false, "FillType сan not be changed at DML fill.");
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets foreground color of the fill.
    /// </summary>
    /*virtual*/ DmlColor getDmlColorInternal() { return null; }
    /*virtual*/ void setDmlColorInternal(DmlColor value) { Debug.assertTrue(value != null); }

    /// <summary>
    /// Gets or sets background color of the fill.
    /// </summary>
    /*virtual*/ DmlColor getDmlColor2Internal() { return null; }
    /*virtual*/ void setDmlColor2Internal(DmlColor value) { Debug.assertTrue(value != null); }

    //>>>>>>>> #region  IComplexAttr implementation

    /// <summary>
    /// Returns true if the attribute inherits the value from some parent.
    /// </summary>
    public boolean isInheritedComplexAttr() { return false; }

    /// <summary>
    /// Called to create a deep clone of the attribute. Will be called only for non inherited attribute values.
    /// </summary>
    public IComplexAttr deepCloneComplexAttr()
    {
        return deepClone();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets IThemeProvider object.
    /// </summary>
    IThemeProvider getThemeProvider() { return mThemeProvider == null ? ((mParent != null) ? mParent.getFillableThemeProvider() : null) : mThemeProvider; }
    void setThemeProvider(IThemeProvider value) { mThemeProvider = value; }

    private IFillable mParent;
    private IThemeProvider mThemeProvider;
    // These constant messages for exceptions are borrowed from VBA.
    static final String MSG_INVALID_ACTION = "Object doesn't support this action.";
    static final String MSG_VALUE_OUT_OF_RANGE = "The specified value is out of range.";
}

