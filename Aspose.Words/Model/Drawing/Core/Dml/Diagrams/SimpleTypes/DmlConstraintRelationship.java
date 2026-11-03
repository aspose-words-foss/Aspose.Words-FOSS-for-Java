// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.20 ST_ConstraintRelationship (Constraint Relationship)
/// </summary>
/*enum*/ final class DmlConstraintRelationship
{
    private DmlConstraintRelationship(){}
    
    public static final int CHILD = 0;
    public static final int DESCENDANT = 1;
    public static final int SELF = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlConstraintRelationship value.
    /// </summary>
    public static String getName(/*DmlConstraintRelationship*/int dmlConstraintRelationship)
    {
    	switch (dmlConstraintRelationship)
    	{
    		case CHILD: return "CHILD";
    		case DESCENDANT: return "DESCENDANT";
    		case SELF: return "SELF";
    		default: return "Unknown DmlConstraintRelationship value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlConstraintRelationship value.
    /// </summary>
    public static String toString(/*DmlConstraintRelationship*/int dmlConstraintRelationship)
    {
    	switch (dmlConstraintRelationship)
    	{
    		case CHILD: return "Child";
    		case DESCENDANT: return "Descendant";
    		case SELF: return "Self";
    		default: return "Unknown DmlConstraintRelationship value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlConstraintRelationship name.
    /// </summary>
    public static /*DmlConstraintRelationship*/int fromName(String dmlConstraintRelationshipName)
    {
    	if ("CHILD".equals(dmlConstraintRelationshipName)) return CHILD;
    	if ("DESCENDANT".equals(dmlConstraintRelationshipName)) return DESCENDANT;
    	if ("SELF".equals(dmlConstraintRelationshipName)) return SELF;
    	throw new IllegalArgumentException("Unknown DmlConstraintRelationship name.");
    }
    
    /// <summary>
    /// Returns array of DmlConstraintRelationship values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CHILD,
    		DESCENDANT,
    		SELF,
    	};
    }

    public static final int length = 3;
}

