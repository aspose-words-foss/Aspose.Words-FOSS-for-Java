// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/08/2025 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the available recovery options when a document encounters errors during loading.
/// </summary>
public /*enum*/ final class DocumentRecoveryMode
{
    private DocumentRecoveryMode(){}
    
    /// <summary>
    /// No recovery is attempted.
    /// If the document is invalid, loading will fail with an error.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Attempts to recover the document while preserving as much data as possible.
    /// </summary>
    public static final int TRY_RECOVER = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentRecoveryMode value.
    /// </summary>
    public static String getName(/*DocumentRecoveryMode*/int documentRecoveryMode)
    {
    	switch (documentRecoveryMode)
    	{
    		case NONE: return "NONE";
    		case TRY_RECOVER: return "TRY_RECOVER";
    		default: return "Unknown DocumentRecoveryMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentRecoveryMode value.
    /// </summary>
    public static String toString(/*DocumentRecoveryMode*/int documentRecoveryMode)
    {
    	switch (documentRecoveryMode)
    	{
    		case NONE: return "None";
    		case TRY_RECOVER: return "TryRecover";
    		default: return "Unknown DocumentRecoveryMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DocumentRecoveryMode name.
    /// </summary>
    public static /*DocumentRecoveryMode*/int fromName(String documentRecoveryModeName)
    {
    	if ("NONE".equals(documentRecoveryModeName)) return NONE;
    	if ("TRY_RECOVER".equals(documentRecoveryModeName)) return TRY_RECOVER;
    	throw new IllegalArgumentException("Unknown DocumentRecoveryMode name.");
    }
    
    /// <summary>
    /// Returns array of DocumentRecoveryMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		TRY_RECOVER,
    	};
    }

    public static final int length = 2;
}

