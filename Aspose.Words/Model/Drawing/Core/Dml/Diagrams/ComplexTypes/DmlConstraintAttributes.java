// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.msObject;


/// <summary>
/// AG_ConstraintAttributes
/// </summary>
class DmlConstraintAttributes
{
    /// <summary>
    /// Create empty self constraint attributes.
    /// </summary>
    public DmlConstraintAttributes()
    {
    	this(DmlConstraintType.NONE);
	
    }
    
    /// <summary>
    /// Create self constraint attributes with specified type.
    /// </summary>
    public DmlConstraintAttributes(/*DmlConstraintType*/int type)
    {
    	this(type, DmlConstraintRelationship.SELF, "", DmlElementType.ALL);
	
        
    }
    
    /// <summary>
    /// Create constraint attributes.
    /// </summary>
    public DmlConstraintAttributes(/*DmlConstraintType*/int type, /*DmlConstraintRelationship*/int forAxis, String forName)
    {
    	this(type, forAxis, forName, DmlElementType.ALL);
	
        
    }
    
    /// <summary>
    /// Create constraint attributes.
    /// </summary>
    public DmlConstraintAttributes(/*DmlConstraintType*/int type, /*DmlConstraintRelationship*/int forAxis, String forName,
        /*DmlElementType*/int pointType)
    {
        mType = type;
        mFor = forAxis;
        mForName = forName;
        mPointType = pointType;
    }
    
    protected boolean equals(DmlConstraintAttributes other)
    {
        return getType() == other.getType() && getFor() == other.getFor() && msString.equals(getForName(), other.getForName()) &&
               getPointType() == other.getPointType();
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != getClass())
            return false;
        return equals((DmlConstraintAttributes)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hashCode = (int)getType();
            hashCode = (hashCode * 397) ^ (int)getFor();
            hashCode = (hashCode * 397) ^ (getForName() != null
                ? getForName().hashCode()
                : 0);
            hashCode = (hashCode * 397) ^ (int)getPointType();
            return hashCode;
        }
    }

    /*DmlConstraintType*/int getType() { return mType; };

    private /*DmlConstraintType*/int mType;
    /*DmlConstraintRelationship*/int getFor() { return mFor; };

    private /*DmlConstraintRelationship*/int mFor;
    String getForName() { return mForName; };

    private String mForName;
    /*DmlElementType*/int getPointType() { return mPointType; };

    private /*DmlElementType*/int mPointType;
}

