// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/04/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents CT_ShapeProperties complex type for charts.
/// </summary>
class DmlChartShapeProperties implements IDmlShapePrSource, IDmlCommonShapePrSource
{
    public DmlTransform getTransform() { return mTransform; }
    public void setTransform(DmlTransform value) { mTransform = value; }

    /// <summary>
    /// Gets or sets preset geometric shape properties.
    /// </summary>
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
    /// Gets or sets scene-level 3D properties to apply to a shape.
    /// </summary>
    public DmlScene3DProperties getScene3DProperties() { return mScene3DProperties; }
    public void setScene3DProperties(DmlScene3DProperties value) { mScene3DProperties = value; }

    /// <summary>
    /// Gets or sets the 3D properties associated with a particular shape.
    /// </summary>
    public DmlShape3DProperties getShape3DProperties() { return mShape3DProperties; }
    public void setShape3DProperties(DmlShape3DProperties value) { mShape3DProperties = value; }

    public StringToObjDictionary<DmlExtension> getSpPrExtensions() { return mExtLst; }
    public void setSpPrExtensions(StringToObjDictionary<DmlExtension> value) { mExtLst = value; }

    public DmlShapeEffectsCollection getEffects() { return mEffects; }
    public void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    private /*BWMode*/int mBwMode = (/*BWMode*/int)255; // Set special value to avoid BWMode writing. 
    private DmlTransform mTransform;
    private DmlGeometry mGeometry;
    private DmlFill mFill;
    private DmlOutline mOutline;
    private DmlShapeStyle mStyle;
    private DmlShapeEffectsCollection mEffects;
    private DmlScene3DProperties mScene3DProperties;
    private DmlShape3DProperties mShape3DProperties;
    private StringToObjDictionary<DmlExtension> mExtLst;
}

