// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/12/2015 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The result type for functions that handle nodes.
/// </summary>
/*enum*/ final class HandleNodeAction
{
    private HandleNodeAction(){}
    
    public static final int NOT_HANDLED = 0;
    /// <summary>
    /// The reader needs to process all children elements of the current element and also needs to process the eng tag of this element.
    /// </summary>
    public static final int HANDLED_TRAVERSE_CHILDREN = 1;
    /// <summary>
    /// If this value is returned, it indicates the processing of the element is finished.
    /// The reader should skip all children of this element and the processing of the element end will not be invoked.
    /// </summary>
    public static final int HANDLED_SKIP_CHILDREN = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HandleNodeAction value.
    /// </summary>
    public static String getName(/*HandleNodeAction*/int handleNodeAction)
    {
    	switch (handleNodeAction)
    	{
    		case NOT_HANDLED: return "NOT_HANDLED";
    		case HANDLED_TRAVERSE_CHILDREN: return "HANDLED_TRAVERSE_CHILDREN";
    		case HANDLED_SKIP_CHILDREN: return "HANDLED_SKIP_CHILDREN";
    		default: return "Unknown HandleNodeAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HandleNodeAction value.
    /// </summary>
    public static String toString(/*HandleNodeAction*/int handleNodeAction)
    {
    	switch (handleNodeAction)
    	{
    		case NOT_HANDLED: return "NotHandled";
    		case HANDLED_TRAVERSE_CHILDREN: return "HandledTraverseChildren";
    		case HANDLED_SKIP_CHILDREN: return "HandledSkipChildren";
    		default: return "Unknown HandleNodeAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HandleNodeAction name.
    /// </summary>
    public static /*HandleNodeAction*/int fromName(String handleNodeActionName)
    {
    	if ("NOT_HANDLED".equals(handleNodeActionName)) return NOT_HANDLED;
    	if ("HANDLED_TRAVERSE_CHILDREN".equals(handleNodeActionName)) return HANDLED_TRAVERSE_CHILDREN;
    	if ("HANDLED_SKIP_CHILDREN".equals(handleNodeActionName)) return HANDLED_SKIP_CHILDREN;
    	throw new IllegalArgumentException("Unknown HandleNodeAction name.");
    }
    
    /// <summary>
    /// Returns array of HandleNodeAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NOT_HANDLED,
    		HANDLED_TRAVERSE_CHILDREN,
    		HANDLED_SKIP_CHILDREN,
    	};
    }

    public static final int length = 3;
}
