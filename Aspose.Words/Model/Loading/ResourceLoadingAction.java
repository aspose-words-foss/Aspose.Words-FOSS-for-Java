// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2011 by Kamimura Andrey

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the mode of resource loading.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-load-options/">Specify Load Options</a> documentation article.</para>
/// </summary>
public /*enum*/ final class ResourceLoadingAction
{
    private ResourceLoadingAction(){}
    
    /// <summary>
    /// Aspose.Words will load this resource as usual.
    /// </summary>
    public static final int DEFAULT = 0; 

    /// <summary>
    /// Aspose.Words will skip loading of this resource.
    /// Only link without data will be stored for an image, CSS style sheet will be ignored for HTML format.
    /// </summary>
    public static final int SKIP = 1;

    /// <summary>
    /// Aspose.Words will use byte array provided by user in <see cref="ResourceLoadingArgs.SetData"/> as resource data.
    /// </summary>
    public static final int USER_PROVIDED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ResourceLoadingAction value.
    /// </summary>
    public static String getName(/*ResourceLoadingAction*/int resourceLoadingAction)
    {
    	switch (resourceLoadingAction)
    	{
    		case DEFAULT: return "DEFAULT";
    		case SKIP: return "SKIP";
    		case USER_PROVIDED: return "USER_PROVIDED";
    		default: return "Unknown ResourceLoadingAction value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ResourceLoadingAction value.
    /// </summary>
    public static String toString(/*ResourceLoadingAction*/int resourceLoadingAction)
    {
    	switch (resourceLoadingAction)
    	{
    		case DEFAULT: return "Default";
    		case SKIP: return "Skip";
    		case USER_PROVIDED: return "UserProvided";
    		default: return "Unknown ResourceLoadingAction value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ResourceLoadingAction name.
    /// </summary>
    public static /*ResourceLoadingAction*/int fromName(String resourceLoadingActionName)
    {
    	if ("DEFAULT".equals(resourceLoadingActionName)) return DEFAULT;
    	if ("SKIP".equals(resourceLoadingActionName)) return SKIP;
    	if ("USER_PROVIDED".equals(resourceLoadingActionName)) return USER_PROVIDED;
    	throw new IllegalArgumentException("Unknown ResourceLoadingAction name.");
    }
    
    /// <summary>
    /// Returns array of ResourceLoadingAction values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		SKIP,
    		USER_PROVIDED,
    	};
    }

    public static final int length = 3;
}

