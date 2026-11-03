// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies how Microsoft Word will report errors detected during mail merge.
/// </summary>
/// <seealso cref="MailMergeSettings.CheckErrors"/>
public /*enum*/ final class MailMergeCheckErrors
{
    private MailMergeCheckErrors(){}
    
    /// <summary>
    /// Simulate the merge and report errors in a new document.
    /// </summary>
    public static final int SIMULATE = 1;
    /// <summary>
    /// Complete the merge and pause to report errors.
    /// </summary>
    public static final int PAUSE_ON_ERROR = 2;
    /// <summary>
    /// Complete the merge and report errors in a new document.
    /// </summary>
    public static final int COLLECT_ERRORS = 3;

    /// <summary>
    /// Equals to the <see cref="PauseOnError"/> value.
    /// </summary>
    public static final int DEFAULT = PAUSE_ON_ERROR;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MailMergeCheckErrors value.
    /// </summary>
    public static String getName(/*MailMergeCheckErrors*/int mailMergeCheckErrors)
    {
    	switch (mailMergeCheckErrors)
    	{
    		case SIMULATE: return "SIMULATE";
    		case PAUSE_ON_ERROR:
    		/*case DEFAULT:*/
    			return "PAUSE_ON_ERROR" + " | " + "DEFAULT";
    		case COLLECT_ERRORS: return "COLLECT_ERRORS";
    		default: return "Unknown MailMergeCheckErrors value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MailMergeCheckErrors value.
    /// </summary>
    public static String toString(/*MailMergeCheckErrors*/int mailMergeCheckErrors)
    {
    	switch (mailMergeCheckErrors)
    	{
    		case SIMULATE: return "Simulate";
    		case PAUSE_ON_ERROR:
    		/*case DEFAULT:*/
    			return "PauseOnError" + " | " + "Default";
    		case COLLECT_ERRORS: return "CollectErrors";
    		default: return "Unknown MailMergeCheckErrors value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MailMergeCheckErrors name.
    /// </summary>
    public static /*MailMergeCheckErrors*/int fromName(String mailMergeCheckErrorsName)
    {
    	if ("SIMULATE".equals(mailMergeCheckErrorsName)) return SIMULATE;
    	if ("PAUSE_ON_ERROR".equals(mailMergeCheckErrorsName)) return PAUSE_ON_ERROR;
    	if ("COLLECT_ERRORS".equals(mailMergeCheckErrorsName)) return COLLECT_ERRORS;
    	if ("DEFAULT".equals(mailMergeCheckErrorsName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MailMergeCheckErrors name.");
    }
    
    /// <summary>
    /// Returns array of MailMergeCheckErrors values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SIMULATE,
    		PAUSE_ON_ERROR,
    		COLLECT_ERRORS,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

