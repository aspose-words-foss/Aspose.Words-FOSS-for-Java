// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;


/// <summary>
/// Represents SpPr element in charts. It is similar to SpPr in regular DML, but some elements
/// are not supported.
/// From the list of supported properties we need only Fill and Outline.
/// </summary>
class DmlChartSpPr extends DmlExtensionListSource implements Cloneable
{
    DmlChartSpPr deepClone()
    {
        DmlChartSpPr lhs = (DmlChartSpPr)memberwiseClone();

        if (mFill != null)
            lhs.mFill = mFill.deepClone();

        if (mOutline != null)
            lhs.mOutline = mOutline.deepClone();

        if (mEffects != null)
            lhs.mEffects = mEffects.deepClone();

        if (mScene3DProp != null)
            lhs.setScene3DProp(mScene3DProp.deepClone());

        if (mShape3DProp != null)
            lhs.setShape3DProp(mShape3DProp.deepClone());

        lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlChartSpPr value = (DmlChartSpPr)obj;

        return
            getFill().equals(value.getFill()) &&
            getOutline().equals(value.getOutline()) &&
            (ArgumentUtil.bothAreNull(getScene3DProp(), value.getScene3DProp()) ||
                ((getScene3DProp() != null) && getScene3DProp().equals(value.getScene3DProp()))) &&
            (ArgumentUtil.bothAreNull(getShape3DProp(), value.getShape3DProp()) ||
                ((getShape3DProp() != null) && getShape3DProp().equals(value.getShape3DProp()))) &&
            (ArgumentUtil.bothAreNull(getEffects(), value.getEffects()) ||
                ((getEffects() != null) && getEffects().equals(value.getEffects())));
    }

    public /*override*/ int hashCode()
    {
        int hash = 0;
        hash ^= getFill().hashCode();
        hash ^= getOutline().hashCode();
        if (getScene3DProp() != null)
            hash ^= getScene3DProp().hashCode();
        if (getShape3DProp() != null)
            hash ^= getShape3DProp().hashCode();
        if (getEffects() != null)
            hash ^= getEffects().hashCode();

        return hash;
    }

    /// <summary>
    /// Clears the properties so that default formatting is used.
    /// </summary>
    void clear()
    {
        mFill = null;
        mOutline = null;
        mEffects = null;
    }

    DmlFill getFill()
    {
        if (mFill != null)
            return mFill;

        return mFill = new DmlStyleFill();
    }
    void setFill(DmlFill value) { mFill = value; }

    DmlOutline getOutline()
    {
        if (mOutline != null)
            return mOutline;

        return mOutline = new DmlOutline();
    }
    void setOutline(DmlOutline value) { mOutline = value; }

    DmlFill getDirectFill() { return mFill; }

    DmlOutline getDirectOutline() { return mOutline; }

    boolean isEmpty() { return (mFill == null || mFill.getDmlFillType() == DmlFillType.STYLE_FILL) &&
            (mOutline == null || mOutline.getDirectPropertiesCount() == 0) && ((getEffects() == null) || (getEffects().getCount() == 0));
    }

    DmlShapeEffectsCollection getEffects() { return mEffects; }
    void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    /// <summary>
    /// Optional scene-level 3D properties.
    /// </summary>
    DmlScene3DProperties getScene3DProp() { return mScene3DProp; }
    void setScene3DProp(DmlScene3DProperties value) { mScene3DProp = value; }

    /// <summary>
    /// 3D properties associated with a shape.
    /// </summary>
    DmlShape3DProperties getShape3DProp() { return mShape3DProp; }
    void setShape3DProp(DmlShape3DProperties value) { mShape3DProp = value; }

    /// <summary>
    /// Indicates whether the outline is not specified.
    /// </summary>
    boolean isOutlineEmpty() { return DmlChartRenderingUtil.isOutlineEmpty(mOutline); }

    private DmlFill mFill;
    private DmlOutline mOutline;
    private DmlScene3DProperties mScene3DProp;
    private DmlShape3DProperties mShape3DProp;
    private DmlShapeEffectsCollection mEffects;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

