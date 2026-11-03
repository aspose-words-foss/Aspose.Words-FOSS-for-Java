// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/08/2025 by Edward

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Used to track field numbering changes.
/// </summary>
class FieldNumberRevision extends RevisionBase implements IComplexAttr, Cloneable
{
    FieldNumberRevision(String author, DateTime dateTime, String original)
    {
    	super(author, dateTime);
	
        mOriginal = original;
    }

    /// <summary>
    /// Original field numbering.
    /// </summary>
    public String getOriginal() { return mOriginal; };

    private  String mOriginal;

    public boolean /*IComplexAttr.*/isInheritedComplexAttr() { return false; }

    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        return (IComplexAttr)memberwiseClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

