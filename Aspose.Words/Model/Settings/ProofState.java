// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the proofing tools state in this document.
/// </summary>
/*enum*/ final class ProofState
{
    private ProofState(){}
    
    /// <summary>
    /// No syntax check performed.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// No errors found.
    /// </summary>
    public static final int CLEAN = 1;
    /// <summary>
    /// Errors are present in the document.
    /// </summary>
    public static final int DIRTY = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ProofState value.
    /// </summary>
    public static String getName(/*ProofState*/int proofState)
    {
    	switch (proofState)
    	{
    		case NONE: return "NONE";
    		case CLEAN: return "CLEAN";
    		case DIRTY: return "DIRTY";
    		default: return "Unknown ProofState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ProofState value.
    /// </summary>
    public static String toString(/*ProofState*/int proofState)
    {
    	switch (proofState)
    	{
    		case NONE: return "None";
    		case CLEAN: return "Clean";
    		case DIRTY: return "Dirty";
    		default: return "Unknown ProofState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ProofState name.
    /// </summary>
    public static /*ProofState*/int fromName(String proofStateName)
    {
    	if ("NONE".equals(proofStateName)) return NONE;
    	if ("CLEAN".equals(proofStateName)) return CLEAN;
    	if ("DIRTY".equals(proofStateName)) return DIRTY;
    	throw new IllegalArgumentException("Unknown ProofState name.");
    }
    
    /// <summary>
    /// Returns array of ProofState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		CLEAN,
    		DIRTY,
    	};
    }

    public static final int length = 3;
}

