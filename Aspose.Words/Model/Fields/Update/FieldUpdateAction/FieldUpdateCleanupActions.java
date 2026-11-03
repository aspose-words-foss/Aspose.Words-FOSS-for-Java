// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies which cleanup actions should be applied to a field after its update.
/// </summary>
/*enum*/ final class FieldUpdateCleanupActions
{
    private FieldUpdateCleanupActions(){}
    
    /// <summary>
    /// Specifies that no cleanup should be performed after a field update.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies that a field's code should be removed after its update, i.e. the field should be
    /// replaced with its result.
    /// </summary>
    public static final int REMOVE_FIELD_CODE = 1;
    /// <summary>
    /// Specifies that a field's result should be removed after its update.
    /// </summary>
    public static final int REMOVE_FIELD_RESULT = 2;
    /// <summary>
    /// Specifies that <see cref="RemoveFieldCode"/> should be applied to all ancestor fields of
    /// a particular field being updated.
    /// </summary>
    public static final int REMOVE_CONTAINING_FIELD_CODE = 4;
    /// <summary>
    /// Specifies that a paragraph containing a particular field being updated should be removed
    /// after the field is updated if it will become empty.
    /// </summary>
    public static final int REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY = 8;
    /// <summary>
    /// Specifies that a paragraph containing a particular field being updated should be removed
    /// after the field is updated if it will contain punctuation marks.
    /// </summary>
    public static final int REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldUpdateCleanupActions value.
    /// </summary>
    public static String getName(/*FieldUpdateCleanupActions*/int fieldUpdateCleanupActions)
    {
    	switch (fieldUpdateCleanupActions)
    	{
    		case NONE: return "NONE";
    		case REMOVE_FIELD_CODE: return "REMOVE_FIELD_CODE";
    		case REMOVE_FIELD_RESULT: return "REMOVE_FIELD_RESULT";
    		case REMOVE_CONTAINING_FIELD_CODE: return "REMOVE_CONTAINING_FIELD_CODE";
    		case REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY: return "REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY";
    		case REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS: return "REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS";
    		default: return "Unknown FieldUpdateCleanupActions value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer FieldUpdateCleanupActions value.
    /// </summary>
    public static Set<String> getNames(/*FieldUpdateCleanupActions*/int fieldUpdateCleanupActions)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & fieldUpdateCleanupActions) == NONE) names.add("NONE");
    	if ((REMOVE_FIELD_CODE & fieldUpdateCleanupActions) == REMOVE_FIELD_CODE) names.add("REMOVE_FIELD_CODE");
    	if ((REMOVE_FIELD_RESULT & fieldUpdateCleanupActions) == REMOVE_FIELD_RESULT) names.add("REMOVE_FIELD_RESULT");
    	if ((REMOVE_CONTAINING_FIELD_CODE & fieldUpdateCleanupActions) == REMOVE_CONTAINING_FIELD_CODE) names.add("REMOVE_CONTAINING_FIELD_CODE");
    	if ((REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY & fieldUpdateCleanupActions) == REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY) names.add("REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY");
    	if ((REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS & fieldUpdateCleanupActions) == REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS) names.add("REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateCleanupActions value.
    /// </summary>
    public static String toString(/*FieldUpdateCleanupActions*/int fieldUpdateCleanupActions)
    {
    	switch (fieldUpdateCleanupActions)
    	{
    		case NONE: return "None";
    		case REMOVE_FIELD_CODE: return "RemoveFieldCode";
    		case REMOVE_FIELD_RESULT: return "RemoveFieldResult";
    		case REMOVE_CONTAINING_FIELD_CODE: return "RemoveContainingFieldCode";
    		case REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY: return "RemoveContainingParagraphIfEmpty";
    		case REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS: return "RemoveContainingParagraphWithPunctuationMarks";
    		default: return toStringSet(fieldUpdateCleanupActions);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldUpdateCleanupActions value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by FieldUpdateCleanupActions name.
    /// </summary>
    public static /*FieldUpdateCleanupActions*/int fromName(String fieldUpdateCleanupActionsName)
    {
    	if ("NONE".equals(fieldUpdateCleanupActionsName)) return NONE;
    	if ("REMOVE_FIELD_CODE".equals(fieldUpdateCleanupActionsName)) return REMOVE_FIELD_CODE;
    	if ("REMOVE_FIELD_RESULT".equals(fieldUpdateCleanupActionsName)) return REMOVE_FIELD_RESULT;
    	if ("REMOVE_CONTAINING_FIELD_CODE".equals(fieldUpdateCleanupActionsName)) return REMOVE_CONTAINING_FIELD_CODE;
    	if ("REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY".equals(fieldUpdateCleanupActionsName)) return REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY;
    	if ("REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS".equals(fieldUpdateCleanupActionsName)) return REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS;
    	throw new IllegalArgumentException("Unknown FieldUpdateCleanupActions name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FieldUpdateCleanupActions names.
    /// </summary>
    public static /*FieldUpdateCleanupActions*/int fromNames(Set<String> fieldUpdateCleanupActionsNames)
    {
    	/*FieldUpdateCleanupActions*/int result = 0;
    	for (String name : fieldUpdateCleanupActionsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FieldUpdateCleanupActions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		REMOVE_FIELD_CODE,
    		REMOVE_FIELD_RESULT,
    		REMOVE_CONTAINING_FIELD_CODE,
    		REMOVE_CONTAINING_PARAGRAPH_IF_EMPTY,
    		REMOVE_CONTAINING_PARAGRAPH_WITH_PUNCTUATION_MARKS,
    	};
    }

    public static final int length = 6;
}

