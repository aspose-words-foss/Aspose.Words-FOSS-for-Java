// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// This class is used to store default shape properties of a theme.
/// </summary>
/// <dev>
/// Corresponding ISO/IEC 29500 element is 20.1.2.2.35 spPr (Shape Properties). 
/// Implementing the following interfaces is necessary for reader/writer.
/// </dev>
class DefaultShapeProperties implements IDmlShapePrSource, IDmlCommonShapePrSource, Cloneable
{
    /// <summary>
    /// Clones this <see cref="DefaultShapeProperties"/> object.
    /// </summary>
    DefaultShapeProperties deepClone()
    {
        DefaultShapeProperties lhs = (DefaultShapeProperties)memberwiseClone();

        if (mTransform != null)
            lhs.mTransform = mTransform.deepClone();
        if (mGeometry != null)
            lhs.mGeometry = mGeometry.deepClone();
        if (mFill != null)
            lhs.mFill = mFill.deepClone();
        if (mOutline != null)
            lhs.mOutline = mOutline.deepClone();
        if (mStyle != null)
            lhs.mStyle = mStyle.deepClone();
        if (mEffects != null)
            lhs.mEffects = mEffects.deepClone();
        if (mScene3DProperties != null)
            lhs.mScene3DProperties = mScene3DProperties.deepClone();
        if (mShape3DProperties != null)
            lhs.mShape3DProperties = mShape3DProperties.deepClone();
        if (mSpPrExtensions != null)
            lhs.mSpPrExtensions = DmlExtensionListSource.cloneExtensions(mSpPrExtensions);

        return lhs;
    }

    /// <summary>
    /// Gets or sets a value that represents 2-D transforms for ordinary shapes.
    /// </summary>
    /// <dev>
    /// 20.1.7.6 xfrm (2D Transform for Individual Objects)
    /// </dev>
    public DmlTransform getTransform() { return mTransform; }
    public void setTransform(DmlTransform value) { mTransform = value; }

    /// <summary>
    /// Gets or sets a value that allows rendering picture using only black and white coloring.
    /// </summary>
    /// <dev>
    /// 20.1.2.2.35 spPr (Shape Properties), attribute bwMode (Black and White Mode)
    /// </dev>
    public /*BWMode*/int getBWMode() { return mBwMode; }
    public void setBWMode(/*BWMode*/int value) { mBwMode = value; }

    /// <summary>
    /// Gets or sets a value that allows defining custom geometric properties.
    /// </summary>
    /// <dev>
    /// 20.1.9.8 custGeom (Custom Geometry)
    /// </dev>
    public DmlGeometry getGeometry() { return mGeometry; }
    public void setGeometry(DmlGeometry value) { mGeometry = value; }

    /// <summary>
    /// Gets or sets a value that allows specifying a color fill.
    /// </summary>
    /// <dev>
    /// Elements of the EG_FillProperties choice group.
    /// </dev>
    public DmlFill getFill() { return mFill; }
    public void setFill(DmlFill value) { mFill = value; }

    /// <summary>
    /// Gets or sets a value that specifies an outline style.
    /// </summary>
    /// <dev>
    /// 20.1.2.2.24 ln (Outline)
    /// </dev>
    public DmlOutline getOutline() { return mOutline; }
    public void setOutline(DmlOutline value) { mOutline = value; }

    /// <summary>
    /// Gets or sets a value that defines shape style.
    /// </summary>
    /// <dev>
    /// 20.1.2.2.37 style (Shape Style)
    /// </dev>
    public DmlShapeStyle getStyle() { return mStyle; }
    public void setStyle(DmlShapeStyle value) { mStyle = value; }

    /// <summary>
    /// Gets or sets a collection that defines visual effects applied to a shape.
    /// </summary>
    /// <dev>
    /// 20.1.8.26 effectLst (Effect Container)
    /// </dev>
    public DmlShapeEffectsCollection getEffects() { return mEffects; }
    public void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    /// <summary>
    /// Gets or sets a value that defines optional scene-level 3D properties to apply to an object.
    /// </summary>
    /// <dev>
    /// 20.1.4.1.26 scene3d (3D Scene Properties)
    /// </dev>
    public DmlScene3DProperties getScene3DProperties() { return mScene3DProperties; }
    public void setScene3DProperties(DmlScene3DProperties value) { mScene3DProperties = value; }

    /// <summary>
    /// Gets or sets a value that defines the 3D properties associated with a particular shape in DrawingML.
    /// </summary>
    /// <dev>
    /// 20.1.5.12 sp3d (Apply 3D shape properties)
    /// </dev>
    public DmlShape3DProperties getShape3DProperties() { return mShape3DProperties; }
    public void setShape3DProperties(DmlShape3DProperties value) { mShape3DProperties = value; }

    /// <summary>
    /// Represents collection of spPr Dml extensions.
    /// </summary>
    public StringToObjDictionary<DmlExtension> getSpPrExtensions() { return mSpPrExtensions; }
    public void setSpPrExtensions(StringToObjDictionary<DmlExtension> value) { mSpPrExtensions = value; }

    /// <summary>
    /// Gets <see cref="DmlChartSpPr"/>
    /// </summary>
    DmlChartSpPr getDmlChartSpPr()
    {
        DmlChartSpPr spPr = new DmlChartSpPr();
        spPr.setOutline(getOutline());
        spPr.setFill(getFill());
        return spPr;
    }

    private StringToObjDictionary<DmlExtension> mSpPrExtensions;
    private DmlTransform mTransform;
    private /*BWMode*/int mBwMode = (/*BWMode*/int)255; // Set special value to avoid BWMode writing. 
    private DmlGeometry mGeometry;
    private DmlFill mFill;
    private DmlOutline mOutline;
    private DmlShapeStyle mStyle;
    private DmlShapeEffectsCollection mEffects;
    private DmlScene3DProperties mScene3DProperties;
    private DmlShape3DProperties mShape3DProperties;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

