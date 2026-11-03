// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Each style rule has an origin, which determines where it enters the cascade. 
/// </summary>
/*enum*/ final class CssDeclarationOrigin
{
    private CssDeclarationOrigin(){}
    
    /// <summary>
    /// The author specifies style sheets for a source document according to the conventions of the document language. 
    /// For instance, in HTML, style sheets may be included in the document or linked externally.
    /// </summary>
    public static final int AUTHOR = 0;
    /// <summary>
    /// The user may be able to specify style information for a particular document. 
    /// For example, the user may specify a file that contains a style sheet or the user agent may provide an interface 
    /// that generates a user style sheet (or behaves as if it did).
    /// </summary>
    public static final int USER = 1;
    /// <summary>
    /// Conforming user agents must apply a default style sheet (or behave as if they did). 
    /// A user agent's default style sheet should present the elements of the document language in ways that satisfy general 
    /// presentation expectations for the document language (e.g., for visual browsers, the EM element in HTML is presented using an italic font).
    /// </summary>
    public static final int USER_AGENT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssDeclarationOrigin value.
    /// </summary>
    public static String getName(/*CssDeclarationOrigin*/int cssDeclarationOrigin)
    {
    	switch (cssDeclarationOrigin)
    	{
    		case AUTHOR: return "AUTHOR";
    		case USER: return "USER";
    		case USER_AGENT: return "USER_AGENT";
    		default: return "Unknown CssDeclarationOrigin value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssDeclarationOrigin value.
    /// </summary>
    public static String toString(/*CssDeclarationOrigin*/int cssDeclarationOrigin)
    {
    	switch (cssDeclarationOrigin)
    	{
    		case AUTHOR: return "Author";
    		case USER: return "User";
    		case USER_AGENT: return "UserAgent";
    		default: return "Unknown CssDeclarationOrigin value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssDeclarationOrigin name.
    /// </summary>
    public static /*CssDeclarationOrigin*/int fromName(String cssDeclarationOriginName)
    {
    	if ("AUTHOR".equals(cssDeclarationOriginName)) return AUTHOR;
    	if ("USER".equals(cssDeclarationOriginName)) return USER;
    	if ("USER_AGENT".equals(cssDeclarationOriginName)) return USER_AGENT;
    	throw new IllegalArgumentException("Unknown CssDeclarationOrigin name.");
    }
    
    /// <summary>
    /// Returns array of CssDeclarationOrigin values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTHOR,
    		USER,
    		USER_AGENT,
    	};
    }

    public static final int length = 3;
}

