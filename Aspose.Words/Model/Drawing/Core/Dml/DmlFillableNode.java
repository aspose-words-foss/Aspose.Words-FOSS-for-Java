// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;


abstract class DmlFillableNode extends DmlNode implements IDmlFillProvider
{
    /// <summary>
    /// Clones this instance of <see cref="DmlFillableNode"/>.
    /// </summary>
    /*override*/ DmlNode deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        DmlFillableNode lhs = (DmlFillableNode)super.deepClone(isCloneChildren, cloningListener);

        if (mFill != null)
            lhs.mFill = mFill.deepClone();

        if (mEffects != null)
            lhs.mEffects = mEffects.deepClone();

        return lhs;
    }

    public DmlFill getFill()
    {
        // If fill isn't specified then use the fill provided by the shape's style.
        if (mFill == null)
            mFill = new DmlStyleFill();
        return mFill;
    }
    public void setFill(DmlFill value) { mFill = value; }
    
    public /*virtual*/ DmlShapeEffectsCollection getEffects() { return mEffects; }
    public /*virtual*/ void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    /// <summary>
    /// Returns true if DmlNode has transparency. Special shadow processing is required in this case.
    /// </summary>
    
    public /*virtual*/ boolean hasTransparency() throws Exception
    {
        // If fill has transparency we also should build shadow in a special way.
        if ((getFill() == null) || (getFill().getDmlFillType() == DmlFillType.NO_FILL))
            return false;

        return ((getFill().getOpacity() < 1) || (getFill().getOpacity2() < 1));
    }

    /// <summary>
    /// Finds the first fill in parent hierarchy that can be drawn.
    /// </summary>
    /// <returns></returns>
    public DmlFill /*IDmlFillProvider.*/findDrawableFillInParents()
    {
        if (!(getFill() instanceof DmlGroupFill))
            return getFill();

        ShapeBase dml = ms.as(getParent(), ShapeBase.class);
        if (dml != null && dml.getDmlNode() instanceof IDmlFillProvider)
            return ((IDmlFillProvider)dml.getDmlNode()).findDrawableFillInParents();
        return null;
    }

    private DmlFill mFill;
    protected DmlShapeEffectsCollection mEffects;
}

