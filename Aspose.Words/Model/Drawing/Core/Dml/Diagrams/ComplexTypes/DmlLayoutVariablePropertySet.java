// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// CT_LayoutVariablePropertySet
/// </summary>
class DmlLayoutVariablePropertySet extends DmlDiagramLayoutNodeContentItem implements Cloneable
{
    DmlLayoutVariablePropertySet()
    {
        mPrBag.setParentBagProvider(new DmlLayoutVariablePropertySetParentBagProvider(G_DEFAULT_PR));
    }

    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitLayoutVariablePropertySet(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.LAYOUT_VARIABLE_PROPERTY_SET; }

    DmlLayoutVariablePropertySet deepClone()
    {
        DmlLayoutVariablePropertySet lhs = (DmlLayoutVariablePropertySet)memberwiseClone();
        lhs.mPrBag = mPrBag.deepClone();

        if (hasExtensions())
            lhs.setExtensions(cloneExtensions());

        return lhs;
    }

    /*override*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        return deepClone();
    }

    boolean getOrgChart() { return (/*boolean*/Boolean)getProperty(DmlLayoutVariableAttr.ORG_CHART); }
    void setOrgChart(boolean value) { setProperty(DmlLayoutVariableAttr.ORG_CHART, value); }

    DmlDiagramNodeCount getChildMax() { return (DmlDiagramNodeCount)getProperty(DmlLayoutVariableAttr.CHILD_MAX); }
    void setChildMax(DmlDiagramNodeCount value) { setProperty(DmlLayoutVariableAttr.CHILD_MAX, value); }

    DmlDiagramNodeCount getChildPref() { return (DmlDiagramNodeCount)getProperty(DmlLayoutVariableAttr.CHILD_PREF); }
    void setChildPref(DmlDiagramNodeCount value) { setProperty(DmlLayoutVariableAttr.CHILD_PREF, value); }

    boolean getBulletEnabled() { return (/*boolean*/Boolean)getProperty(DmlLayoutVariableAttr.BULLET_ENABLED); }
    void setBulletEnabled(boolean value) { setProperty(DmlLayoutVariableAttr.BULLET_ENABLED, value); }

    /*DmlDiagramDirection*/int getDirection() { return (/*DmlDiagramDirection*/Integer)getProperty(DmlLayoutVariableAttr.DIRECTION); }
    void setDirection(/*DmlDiagramDirection*/int value) { setProperty(DmlLayoutVariableAttr.DIRECTION, value); }

    /*DmlHierBranchStyle*/int getHierBranch() { return (/*DmlHierBranchStyle*/Integer)getProperty(DmlLayoutVariableAttr.HIER_BRANCH_STYLE); }
    void setHierBranch(/*DmlHierBranchStyle*/int value) { setProperty(DmlLayoutVariableAttr.HIER_BRANCH_STYLE, value); }

    /*DmlAnimOne*/int getAnimOne() { return (/*DmlAnimOne*/Integer)getProperty(DmlLayoutVariableAttr.ANIM_ONE); }
    void setAnimOne(/*DmlAnimOne*/int value) { setProperty(DmlLayoutVariableAttr.ANIM_ONE, value); }

    /*DmlAnimLevel*/int getAnimLevel() { return (/*DmlAnimLevel*/Integer)getProperty(DmlLayoutVariableAttr.ANIM_LEVEL); }
    void setAnimLevel(/*DmlAnimLevel*/int value) { setProperty(DmlLayoutVariableAttr.ANIM_LEVEL, value); }

    /*DmlResizeHandles*/int getResizeHandles() { return (/*DmlResizeHandles*/Integer)getProperty(DmlLayoutVariableAttr.RESIZE_HANDLES); }
    void setResizeHandles(/*DmlResizeHandles*/int value) { setProperty(DmlLayoutVariableAttr.RESIZE_HANDLES, value); }

    Object getProperty(/*DmlLayoutVariableAttr*/int attr)
    {
        return mPrBag.getProperty((int)attr);
    }

    void setProperty(/*DmlLayoutVariableAttr*/int attr, Object value)
    {
        mPrBag.setProperty((int)attr, value);
    }

    /// <summary>
    /// Returns null if property is not set explicitly
    /// </summary>
    Object getDirectProperty(/*DmlLayoutVariableAttr*/int attr)
    {
        return mPrBag.getDirectProperty((int)attr);
    }

    /// <summary>
    /// Determines whether the mPropertyBag contains the specified property, which was set directly.
    /// </summary>
    /// <param name="attr">the attr</param>
    /// <returns>"true", if the  property was set directly, "false" otherwise</returns>
    boolean isPropertySpecified(/*DmlLayoutVariableAttr*/int attr)
    {
        return mPrBag.isPropertySpecified((int)attr);
    }

    void setParent(DmlLayoutVariablePropertySet parent)
    {
        mPrBag.setParentBagProvider(new DmlLayoutVariablePropertySetParentBagProvider(parent));
    }

    private IDmlHierarchicalPropertyBag mPrBag = new DmlHierarchicalPropertyBag();

    private static /*final*/ DmlLayoutVariablePropertySet G_DEFAULT_PR;

    private static class DmlLayoutVariablePropertySetParentBagProvider implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
    {
        public DmlLayoutVariablePropertySetParentBagProvider(DmlLayoutVariablePropertySet parentPr)
        {
            mParentPr = parentPr;
        }

        public IDmlHierarchicalPropertyBag getParentBag() { return (mParentPr != null) ? mParentPr.mPrBag : null; }

        public IDmlHierarchicalPropertyBagParentProvider deepClone()
        {
            DmlLayoutVariablePropertySetParentBagProvider lhs =
                (DmlLayoutVariablePropertySetParentBagProvider)memberwiseClone();

            if (mParentPr != null)
                lhs.mParentPr = mParentPr.deepClone();

            return lhs;
        }

        private DmlLayoutVariablePropertySet mParentPr;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    static/* DmlLayoutVariablePropertySet()*/
    {
        G_DEFAULT_PR = new DmlLayoutVariablePropertySet();
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.ORG_CHART, false);
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.CHILD_MAX, new DmlDiagramNodeCount(-1));
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.CHILD_PREF, new DmlDiagramNodeCount(-1));
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.BULLET_ENABLED, false);
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.DIRECTION, DmlDiagramDirection.NORMAL);
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.HIER_BRANCH_STYLE, DmlHierBranchStyle.STANDARD);
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.ANIM_ONE, DmlAnimOne.ONE);
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.ANIM_LEVEL, DmlAnimLevel.NONE);
        G_DEFAULT_PR.setProperty(DmlLayoutVariableAttr.RESIZE_HANDLES, DmlResizeHandles.RELATIVE);
    }
}

