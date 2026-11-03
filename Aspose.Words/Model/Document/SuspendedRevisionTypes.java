// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2021 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents types of revisions that are disabled to track.
/// </summary>
/*enum*/ final class SuspendedRevisionTypes
{
    private SuspendedRevisionTypes(){}
    
    /// <summary>
    /// Indicates that tracking revisions of all types is disabled.
    /// </summary>
    public static final int ALL = 0;
    /// <summary>
    /// Indicates that tracking only move revisions is disabled. Move revisions are represented as
    /// <see cref="RevisionType.Deletion"/> and <see cref="RevisionType.Insertion"/> in this case.
    /// </summary>
    public static final int MOVE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SuspendedRevisionTypes value.
    /// </summary>
    public static String getName(/*SuspendedRevisionTypes*/int suspendedRevisionTypes)
    {
    	switch (suspendedRevisionTypes)
    	{
    		case ALL: return "ALL";
    		case MOVE: return "MOVE";
    		default: return "Unknown SuspendedRevisionTypes value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SuspendedRevisionTypes value.
    /// </summary>
    public static String toString(/*SuspendedRevisionTypes*/int suspendedRevisionTypes)
    {
    	switch (suspendedRevisionTypes)
    	{
    		case ALL: return "All";
    		case MOVE: return "Move";
    		default: return "Unknown SuspendedRevisionTypes value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SuspendedRevisionTypes name.
    /// </summary>
    public static /*SuspendedRevisionTypes*/int fromName(String suspendedRevisionTypesName)
    {
    	if ("ALL".equals(suspendedRevisionTypesName)) return ALL;
    	if ("MOVE".equals(suspendedRevisionTypesName)) return MOVE;
    	throw new IllegalArgumentException("Unknown SuspendedRevisionTypes name.");
    }
    
    /// <summary>
    /// Returns array of SuspendedRevisionTypes values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALL,
    		MOVE,
    	};
    }

    public static final int length = 2;
}

