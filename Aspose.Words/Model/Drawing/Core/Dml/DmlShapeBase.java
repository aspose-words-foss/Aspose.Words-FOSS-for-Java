// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Represents a base class for all nodes having shape behavior.
/// </summary>
abstract class DmlShapeBase extends DmlFillableNode implements IDmlShapePrSource, IDmlCommonShapePrSource
{
    /// <summary>
    /// Shows if corrected size should be used for building geometry of the shape.
    /// Default is <c>false</c>.
    /// </summary>
    /*virtual*/ boolean shouldBuildUsingSqueezedSize()
    {
        return false;
    }

    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        DmlShapeBase lhs = (DmlShapeBase)super.deepClone(isCloneChildren, cloningListener);

        if (mTransform != null)
            lhs.mTransform = mTransform.deepClone();

        if (mGeometry != null)
            lhs.setGeometry(mGeometry.deepClone());

        if (mOutline != null)
            lhs.setOutline(mOutline.deepClone());

        if (mStyle != null)
            lhs.setStyle(mStyle.deepClone());

        if (mScene3DProperties != null)
            lhs.mScene3DProperties = mScene3DProperties.deepClone();

        if (mShape3DProperties != null)
            lhs.mShape3DProperties = mShape3DProperties.deepClone();

        lhs.mEffectsStyleResolver = null;

        if (mSpPrExtensions != null)
            lhs.mSpPrExtensions = DmlExtensionListSource.cloneExtensions(mSpPrExtensions);

        return lhs;
    }

    public DmlGeometry getGeometry()
    {
        if (mGeometry == null)
            mGeometry = new DmlGeometry();
        return mGeometry;
    }
    public void setGeometry(DmlGeometry value) { mGeometry = value; }

    public DmlOutline getOutline()
    {
        // If outline isn't specified then we will use the outline provided by the shape's style.
        // Please use Outline.DirectPropertiesCount to find out outline isn't specified directly. 
        if (mOutline == null)
            mOutline = new DmlOutline();

        return mOutline;
    }
    public void setOutline(DmlOutline value) { mOutline = value; }

    public /*BWMode*/int getBWMode() { return mDmlBWMode; }
    public void setBWMode(/*BWMode*/int value) { mDmlBWMode = value; }

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

    public /*override*/ DmlShapeEffectsCollection getEffects()
    {
        if ((mEffects == null) && (getEffectsStyleResolver().getEffects() != null))
            mEffects = getEffectsStyleResolver().getEffects();

        return mEffects;
    }
    public /*override*/ void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    public DmlShapeStyle getStyle() { return mStyle; }
    public void setStyle(DmlShapeStyle value) { mStyle = value; }

    public StringToObjDictionary<DmlExtension> getSpPrExtensions() { return mSpPrExtensions; }
    public void setSpPrExtensions(StringToObjDictionary<DmlExtension> value) { mSpPrExtensions = value; }

    private DmlEffectsStyleResolver getEffectsStyleResolver()
    {
        if (mEffectsStyleResolver == null)
            mEffectsStyleResolver = new DmlEffectsStyleResolver(getStyle(), getDml().getDocumentTheme());

        return mEffectsStyleResolver;
    }

    private StringToObjDictionary<DmlExtension> mSpPrExtensions;
    private DmlGeometry mGeometry;
    private DmlOutline mOutline;
    private DmlShapeStyle mStyle;
    private /*BWMode*/int mDmlBWMode = (/*BWMode*/int)255; // Set special value to avoid BWMode writing. 
    private DmlScene3DProperties mScene3DProperties;
    private DmlShape3DProperties mShape3DProperties;
    private DmlEffectsStyleResolver mEffectsStyleResolver;
}

