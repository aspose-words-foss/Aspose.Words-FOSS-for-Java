// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.6 ST_AxisType (Axis Type)
/// </summary>
/*enum*/ final class DmlAxisType
{
    private DmlAxisType(){}
    
    public static final int ANCESTOR = 0;
    public static final int ANCESTOR_OR_SELF = 1;
    public static final int CHILD = 2;
    public static final int DESCENDANT = 3;
    public static final int DESCENDANT_OR_SELF = 4;
    public static final int FOLLOW = 5;
    public static final int FOLLOW_SIBLING = 6;
    public static final int NONE = 7;
    public static final int PARENT = 8;
    public static final int PRECEDING = 9;
    public static final int PRECEDING_SIBLING = 10;
    public static final int ROOT = 11;
    public static final int SELF = 12;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlAxisType value.
    /// </summary>
    public static String getName(/*DmlAxisType*/int dmlAxisType)
    {
    	switch (dmlAxisType)
    	{
    		case ANCESTOR: return "ANCESTOR";
    		case ANCESTOR_OR_SELF: return "ANCESTOR_OR_SELF";
    		case CHILD: return "CHILD";
    		case DESCENDANT: return "DESCENDANT";
    		case DESCENDANT_OR_SELF: return "DESCENDANT_OR_SELF";
    		case FOLLOW: return "FOLLOW";
    		case FOLLOW_SIBLING: return "FOLLOW_SIBLING";
    		case NONE: return "NONE";
    		case PARENT: return "PARENT";
    		case PRECEDING: return "PRECEDING";
    		case PRECEDING_SIBLING: return "PRECEDING_SIBLING";
    		case ROOT: return "ROOT";
    		case SELF: return "SELF";
    		default: return "Unknown DmlAxisType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlAxisType value.
    /// </summary>
    public static String toString(/*DmlAxisType*/int dmlAxisType)
    {
    	switch (dmlAxisType)
    	{
    		case ANCESTOR: return "Ancestor";
    		case ANCESTOR_OR_SELF: return "AncestorOrSelf";
    		case CHILD: return "Child";
    		case DESCENDANT: return "Descendant";
    		case DESCENDANT_OR_SELF: return "DescendantOrSelf";
    		case FOLLOW: return "Follow";
    		case FOLLOW_SIBLING: return "FollowSibling";
    		case NONE: return "None";
    		case PARENT: return "Parent";
    		case PRECEDING: return "Preceding";
    		case PRECEDING_SIBLING: return "PrecedingSibling";
    		case ROOT: return "Root";
    		case SELF: return "Self";
    		default: return "Unknown DmlAxisType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlAxisType name.
    /// </summary>
    public static /*DmlAxisType*/int fromName(String dmlAxisTypeName)
    {
    	if ("ANCESTOR".equals(dmlAxisTypeName)) return ANCESTOR;
    	if ("ANCESTOR_OR_SELF".equals(dmlAxisTypeName)) return ANCESTOR_OR_SELF;
    	if ("CHILD".equals(dmlAxisTypeName)) return CHILD;
    	if ("DESCENDANT".equals(dmlAxisTypeName)) return DESCENDANT;
    	if ("DESCENDANT_OR_SELF".equals(dmlAxisTypeName)) return DESCENDANT_OR_SELF;
    	if ("FOLLOW".equals(dmlAxisTypeName)) return FOLLOW;
    	if ("FOLLOW_SIBLING".equals(dmlAxisTypeName)) return FOLLOW_SIBLING;
    	if ("NONE".equals(dmlAxisTypeName)) return NONE;
    	if ("PARENT".equals(dmlAxisTypeName)) return PARENT;
    	if ("PRECEDING".equals(dmlAxisTypeName)) return PRECEDING;
    	if ("PRECEDING_SIBLING".equals(dmlAxisTypeName)) return PRECEDING_SIBLING;
    	if ("ROOT".equals(dmlAxisTypeName)) return ROOT;
    	if ("SELF".equals(dmlAxisTypeName)) return SELF;
    	throw new IllegalArgumentException("Unknown DmlAxisType name.");
    }
    
    /// <summary>
    /// Returns array of DmlAxisType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ANCESTOR,
    		ANCESTOR_OR_SELF,
    		CHILD,
    		DESCENDANT,
    		DESCENDANT_OR_SELF,
    		FOLLOW,
    		FOLLOW_SIBLING,
    		NONE,
    		PARENT,
    		PRECEDING,
    		PRECEDING_SIBLING,
    		ROOT,
    		SELF,
    	};
    }

    public static final int length = 13;
}

