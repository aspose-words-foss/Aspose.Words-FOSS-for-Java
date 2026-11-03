// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.MathUtil;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.5.12 sp3d (Apply 3D shape properties)
/// This element defines the 3D properties associated with a particular shape 
/// in DrawingML. The 3D properties which can be applied to a shape are top and 
/// bottom bevels, a contour and an extrusion.
/// </summary>
class DmlShape3DProperties extends DmlExtensionListSource implements Cloneable
{
    DmlShape3DProperties()
    {
    	this(false);
	
    }

    DmlShape3DProperties(boolean isTheme)
    {
        mIsTheme = isTheme;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlShape3DProperties value = (DmlShape3DProperties)obj;

        return (value.getPresetMaterial() == getPresetMaterial()) &&
               MathUtil.areEqual(value.getZ(), getZ()) &&
               MathUtil.areEqual(value.getContourWidth(), getContourWidth()) &&
               MathUtil.areEqual(value.getExtrusionHeight(), getExtrusionHeight()) &&
               msObject.equals(value.getBevelTop(), getBevelTop()) &&
               msObject.equals(value.getBevelBottom(), getBevelBottom()) &&
               msObject.equals(value.getContourColor(), getContourColor()) &&
               msObject.equals(value.getExtrusionColor(), getExtrusionColor());
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        if (getBevelTop() != null)
            hash ^= getBevelTop().hashCode();
        if (getBevelBottom() != null)
            hash ^= getBevelBottom().hashCode();
        if (getContourColor() != null)
            hash ^= getContourColor().hashCode();
        if (getExtrusionColor() != null)
            hash ^= getExtrusionColor().hashCode();
        hash ^= msNumber.doubleHashCode(getZ());
        hash ^= msNumber.doubleHashCode(getContourWidth());
        hash ^= msNumber.doubleHashCode(getExtrusionHeight());
        hash ^= /*enum.hashCode()=(int)value*/getPresetMaterial();

        return hash;
    }

    DmlShape3DProperties deepClone()
    {
        DmlShape3DProperties lhs = (DmlShape3DProperties)memberwiseClone();
        if (mBevelTop != null)
            lhs.mBevelTop = mBevelTop.deepClone();

        if (mBevelBottom != null)
            lhs.mBevelBottom = mBevelBottom.deepClone();

        if (mContourColor != null)
            lhs.mContourColor = mContourColor.deepClone();

        if (mExtrusionColor != null)
            lhs.mExtrusionColor = mExtrusionColor.deepClone();

        lhs.mZ = mZ;
        lhs.setContourWidth(getContourWidth());
        lhs.setExtrusionHeight(getExtrusionHeight());
        lhs.setPresetMaterial(getPresetMaterial());

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// This element holds the properties associated with defining a bevel on the bottom or back face of a shape.
    /// </summary>
    DmlBevel getBevelTop() { return mBevelTop; }
    void setBevelTop(DmlBevel value) { mBevelTop = value; }

    /// <summary>
    /// This element holds the properties associated with defining a bevel on the top or front face of a shape.
    /// </summary>
    DmlBevel getBevelBottom() { return mBevelBottom; }
    void setBevelBottom(DmlBevel value) { mBevelBottom = value; }

    /// <summary>
    /// This element defines the color for the contour on a shape.
    /// </summary>
    DmlColor getContourColor() { return mContourColor; }
    void setContourColor(DmlColor value) { mContourColor = value; }

    /// <summary>
    /// This element defines the color of the extrusion applied to a shape.
    /// </summary>
    DmlColor getExtrusionColor() { return mExtrusionColor; }
    void setExtrusionColor(DmlColor value) { mExtrusionColor = value; }

    /// <summary>
    /// Defines the z coordinate for the 3D shape.
    /// </summary>
    double getZ() { return mZ; }
    void setZ(double value) { mZ = value; }

    /// <summary>
    /// Defines the width of the contour on the shape.
    /// </summary>
    double getContourWidth() { return mContourWidth; }
    void setContourWidth(double value) { mContourWidth = value; }

    /// <summary>
    /// Defines the height of the extrusion applied to the shape.
    /// </summary>
    double getExtrusionHeight() { return mExtrusionHeight; }
    void setExtrusionHeight(double value) { mExtrusionHeight = value; }

    /// <summary>
    /// Defines the preset material which is combined with the lighting properties 
    /// to give the final look and feel of a shape.
    /// </summary>
    /*DmlPresetMaterialType*/int getPresetMaterial() { return mPresetMaterial; }
    void setPresetMaterial(/*DmlPresetMaterialType*/int value) { mPresetMaterial = value; }

    /// <summary>
    /// Flag indicates that 3D properties represented by this class are applied through document theme.
    /// </summary>
    boolean isTheme() { return mIsTheme; }

    private DmlBevel mBevelTop;
    private DmlBevel mBevelBottom;
    private DmlColor mContourColor;
    private DmlColor mExtrusionColor;
    private double mZ;
    private double mContourWidth;
    private double mExtrusionHeight;
    private /*DmlPresetMaterialType*/int mPresetMaterial = DmlPresetMaterialType.WARM_MATTE;
    private /*final*/ boolean mIsTheme;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

