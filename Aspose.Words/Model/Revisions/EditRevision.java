// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents info about an insert or delete revision, occurs on runs of text.
///
/// Three different revision operations are possible on a run: inserted, deleted or formatted.
/// Formatted revision can be combined with deleted or inserted revision.
/// A piece of text can be inserted by one author and deleted by another author,
/// in this case both insert and delete revisions can be present on a run.
///
/// 1. Formatted is same as formatting change revision on other objects, for example section properties.
/// There is an sprm that is followed by sprms for the properties that have changed. We read this
/// into revision attributes collection and store it as an attribute in the run attribute collection.
/// 2. Inserted and deleted in WordML actually come not inside rPr element, but at the level of the r element.
/// </summary>
class EditRevision extends RevisionBase implements IComplexAttr, Cloneable
{
    EditRevision(/*EditRevisionType*/int type)
    {
    	this(type, "", DateTime.MinValue);
	
    }

    EditRevision(/*EditRevisionType*/int type, String author, DateTime dateTime)
    {
    	super(author, dateTime);
	
        mType = type;
    }

    /// <summary>
    /// Clones this instance of revision.
    /// </summary>
    EditRevision deepClone()
    {
        return (EditRevision)memberwiseClone();
    }

    /// <summary>
    /// Indicates whether the run is inserted or deleted during the revision.
    /// </summary>
    /*EditRevisionType*/int getType() { return mType; };

    private /*EditRevisionType*/int mType;

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

