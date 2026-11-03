// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents info about an moveFrom or moveTo revision, occurs on runs of text.
///
/// Two different move revision operations are possible on a run: moveFrom, and moveTo.
/// </summary>
class MoveRevision extends RevisionBase implements IComplexAttr, Cloneable
{
    MoveRevision(/*MoveRevisionType*/int type)
    {
    	this(type, "", DateTime.MinValue);
	
    }

    MoveRevision(/*MoveRevisionType*/int type, String author, DateTime dateTime)
    {
    	super(author, dateTime);
	
        mType = type;
    }

    /// <summary>
    /// Clones this instance of revision.
    /// </summary>
    MoveRevision deepClone()
    {
        return (MoveRevision)memberwiseClone();
    }

    /// <summary>
    /// Indicates whether the run is 'moved from' or 'moved to' during the revision.
    /// </summary>
    /*MoveRevisionType*/int getType() { return mType; };

    private /*MoveRevisionType*/int mType;

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean isInheritedComplexAttr() { return false; }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        return deepClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

