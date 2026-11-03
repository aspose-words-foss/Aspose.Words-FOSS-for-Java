// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents CT_ShapeProperties complex type.
/// </summary>
class DmlShapeProperties implements IDmlShapePrSource, IDmlCommonShapePrSource
{
    DmlShapeProperties(DocumentBase doc)
    {
        mDoc = doc;
    }

    public DmlTransform getTransform() { return mTransform; }
    public void setTransform(DmlTransform value) { mTransform = value; }
    
    public DmlGeometry getGeometry() { return mGeometry; }
    public void setGeometry(DmlGeometry value) { mGeometry = value; }

    public DmlFill getFill() { return mFill; }
    public void setFill(DmlFill value) { mFill = value; }

    public DmlOutline getOutline() { return mOutline; }
    public void setOutline(DmlOutline value) { mOutline = value; }

    public DmlShapeStyle getStyle() { return mStyle; }
    public void setStyle(DmlShapeStyle value) { mStyle = value; }

    public /*BWMode*/int getBWMode() { return mBwMode; }
    public void setBWMode(/*BWMode*/int value) { mBwMode = value; }

    /// <summary>
    /// Defines optional scene-level 3D properties to apply to an object.
    /// </summary>
    public DmlScene3DProperties getScene3DProperties() { return (mScene3DProperties != null) ? mScene3DProperties : getEffectsStyleResolver().getScene3DProperties(); }
    public void setScene3DProperties(DmlScene3DProperties value) { mScene3DProperties = value; }

    /// <summary>
    /// Defines the 3D properties associated with a particular shape in DrawingML.
    /// </summary>
    public DmlShape3DProperties getShape3DProperties() { return (mShape3DProperties != null) ? mShape3DProperties : getEffectsStyleResolver().getShape3DProperties(); }
    public void setShape3DProperties(DmlShape3DProperties value) { mShape3DProperties = value; }

    public StringToObjDictionary<DmlExtension> getSpPrExtensions() { return mExtLst; }
    public void setSpPrExtensions(StringToObjDictionary<DmlExtension> value) { mExtLst = value; }

    public DmlShapeEffectsCollection getEffects()
    {
        if ((mEffects == null) && (getEffectsStyleResolver().getEffects() != null))
            mEffects = getEffectsStyleResolver().getEffects();

        return mEffects;
    }
    public void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    private DmlEffectsStyleResolver getEffectsStyleResolver()
    {
        if (mEffectsStyleResolver == null)
            mEffectsStyleResolver = new DmlEffectsStyleResolver(getStyle(), mDoc.getThemeInternal());

        return mEffectsStyleResolver;
    }

    private /*BWMode*/int mBwMode = (/*BWMode*/int)255; // Set special value to avoid BWMode writing. 
    private DmlTransform mTransform;
    private DmlGeometry mGeometry;
    private DmlFill mFill;
    private DmlOutline mOutline;
    private DmlShapeStyle mStyle;
    private DmlShapeEffectsCollection mEffects;
    private DmlScene3DProperties mScene3DProperties;
    private DmlShape3DProperties mShape3DProperties;
    private /*final*/ DocumentBase mDoc;
    private DmlEffectsStyleResolver mEffectsStyleResolver;
    private StringToObjDictionary<DmlExtension> mExtLst;
}

