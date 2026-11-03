// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;


/// <summary>
/// Represents info about a format revision (change of attributes) of a run, paragraph or a section.
/// </summary>
class FormatRevision extends RevisionBase implements  IComplexAttr, Cloneable
{
    FormatRevision(WordAttrCollection revPr, String author, DateTime dateTime)
    {
    	super(author, dateTime);
	
        if (revPr == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "revPr");
        if (author == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "author");

        setRevPr(revPr);
    }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        FormatRevision lhs = (FormatRevision)memberwiseClone();
        lhs.setRevPr(AttrCollectionExtension.deepClone(getRevPr()));
        return lhs;
    }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean isInheritedComplexAttr() { return false; }

    /// <summary>
    /// Gets the revised (modified) attributes.
    /// This will not be null, but could be an empty collection.
    /// </summary>
    WordAttrCollection getRevPr() { return mRevPr; }; void setRevPr(WordAttrCollection value) { mRevPr = value; };

    private WordAttrCollection mRevPr;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

