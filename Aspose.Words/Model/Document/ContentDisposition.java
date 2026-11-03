// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates different ways of presenting the document at the client browser. 
/// </summary>
/// <remarks>
/// <para>Note that the actual behavior on the client browser might be affected by security configuration of the browser.</para>
/// </remarks>
public /*enum*/ final class ContentDisposition
{
    private ContentDisposition(){}
    
    /// <summary>
    /// Send the document to the browser and present an option to save the document to disk or open in the application
    /// associated with the document's extension.
    /// </summary>
    public static final int ATTACHMENT = 0;

    /// <summary>
    /// Send the document to the browser and presents an option to save the document to disk or open inside the browser.
    /// </summary>
    public static final int INLINE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ContentDisposition value.
    /// </summary>
    public static String getName(/*ContentDisposition*/int contentDisposition)
    {
    	switch (contentDisposition)
    	{
    		case ATTACHMENT: return "ATTACHMENT";
    		case INLINE: return "INLINE";
    		default: return "Unknown ContentDisposition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ContentDisposition value.
    /// </summary>
    public static String toString(/*ContentDisposition*/int contentDisposition)
    {
    	switch (contentDisposition)
    	{
    		case ATTACHMENT: return "Attachment";
    		case INLINE: return "Inline";
    		default: return "Unknown ContentDisposition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ContentDisposition name.
    /// </summary>
    public static /*ContentDisposition*/int fromName(String contentDispositionName)
    {
    	if ("ATTACHMENT".equals(contentDispositionName)) return ATTACHMENT;
    	if ("INLINE".equals(contentDispositionName)) return INLINE;
    	throw new IllegalArgumentException("Unknown ContentDisposition name.");
    }
    
    /// <summary>
    /// Returns array of ContentDisposition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ATTACHMENT,
    		INLINE,
    	};
    }

    public static final int length = 2;
}

