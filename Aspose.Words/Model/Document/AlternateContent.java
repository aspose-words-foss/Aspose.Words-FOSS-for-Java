// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

 
/// <summary>
/// Container to store FallBack of AlternateContent and Requires attribute. 
/// In case when we need FallBack, Choice is in the model.  
/// </summary>
class AlternateContent implements IComplexAttr, Cloneable
{
    public /*override*/ int hashCode()
    {
        // Nodes do not support GetHashCode now: take from type.
        int hashCode = (mFallBack != null) ? mFallBack.getClass().hashCode() : 0;
        hashCode = (hashCode * 397) ^ ((mRequires != null) ? mRequires.hashCode() : 0);
        return hashCode;
    }

    public boolean isInheritedComplexAttr() { return false; }

    public IComplexAttr deepCloneComplexAttr()
    {
        AlternateContent altc = (AlternateContent)memberwiseClone();
        altc.mFallBack = (mFallBack != null) ? (CompositeNode)mFallBack.deepClone(true) : null;
        return altc;
    }

    /// <summary>
    /// Fallback element allows clients that do not support Requires namespace to see an appropriate alternative
    /// representation of AlternateContent.
    /// </summary>
    /// <remarks>
    /// When we clone whole document, parent is changed for each node, but FallBack in this case it is alternate node outside of the main tree, 
    /// that is why FallBack still belongs to the original document, and that is why we need to set parent directly to FallBack shape.
    /// Please see <see cref="Drawing.ShapeBase.UpdateFallBackParent"/> method.
    /// </remarks>
    CompositeNode getFallBack() { return mFallBack; }
    void setFallBack(CompositeNode value) { mFallBack = (value == null) ? value :  value; }

    String getRequires() { return mRequires; }
    void setRequires(String value) { mRequires = value; }

    private CompositeNode mFallBack;
    private String mRequires;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

