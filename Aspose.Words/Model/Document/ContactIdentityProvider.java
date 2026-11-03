// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies an identity provider that issues user identifiers for a person contact.
/// </summary>
/*enum*/ final class ContactIdentityProvider
{
    private ContactIdentityProvider(){}
    
    /// <summary>
    /// No provider.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Active Directory.
    /// </summary>
    public static final int ACTIVE_DIRECTORY = 1;

    /// <summary>
    /// Windows Live ID.
    /// </summary>
    public static final int WINDOWS_LIVE_ID = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ContactIdentityProvider value.
    /// </summary>
    public static String getName(/*ContactIdentityProvider*/int contactIdentityProvider)
    {
    	switch (contactIdentityProvider)
    	{
    		case NONE: return "NONE";
    		case ACTIVE_DIRECTORY: return "ACTIVE_DIRECTORY";
    		case WINDOWS_LIVE_ID: return "WINDOWS_LIVE_ID";
    		default: return "Unknown ContactIdentityProvider value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ContactIdentityProvider value.
    /// </summary>
    public static String toString(/*ContactIdentityProvider*/int contactIdentityProvider)
    {
    	switch (contactIdentityProvider)
    	{
    		case NONE: return "None";
    		case ACTIVE_DIRECTORY: return "ActiveDirectory";
    		case WINDOWS_LIVE_ID: return "WindowsLiveId";
    		default: return "Unknown ContactIdentityProvider value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ContactIdentityProvider name.
    /// </summary>
    public static /*ContactIdentityProvider*/int fromName(String contactIdentityProviderName)
    {
    	if ("NONE".equals(contactIdentityProviderName)) return NONE;
    	if ("ACTIVE_DIRECTORY".equals(contactIdentityProviderName)) return ACTIVE_DIRECTORY;
    	if ("WINDOWS_LIVE_ID".equals(contactIdentityProviderName)) return WINDOWS_LIVE_ID;
    	throw new IllegalArgumentException("Unknown ContactIdentityProvider name.");
    }
    
    /// <summary>
    /// Returns array of ContactIdentityProvider values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ACTIVE_DIRECTORY,
    		WINDOWS_LIVE_ID,
    	};
    }

    public static final int length = 3;
}

