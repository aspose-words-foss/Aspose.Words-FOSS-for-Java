// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2013 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>Specifies desired behavior of AW DOM as well as desired compatibility of documents when exported to MS Formats.</summary>
/// <remarks>
/// During OOXML document reading sometimes we're able to identify MS Word version, which allows us to better roundtrip and save
/// the document. Each MS Word version has more features than previous one, so we can define order between versions.
/// However during loading we can only guess a minimum version,
/// meaning that if we identified document as containing Word2007 features, it might be originating from 
/// any MS Word version not earlier than Word2007 but from later versions as well e.g. Word2010 and Word2013.
/// </remarks>
/*enum*/ final class MsWordVersionCore
{
    private MsWordVersionCore(){}
    
    /// <summary>
    /// Unspecified document version means all AW teams are free to proceed with their custom behavior logic since user
    /// does not specify preferences for MS Version and the document does not contain it as well.
    /// </summary>
    public static final int UNSPECIFIED = 0;

    // DD: These commented ones are for historical and info purpose only, we're not going to detect them or expose to public
    // Word1990 = 1, DD actually 1.1, first version ever.
    // Word1992 = 2,
    // Word1993 = 6, Wikipedia comment: Jumped from 2.0 to 6.0 so that it would have the same version number as the MS-DOS and Macintosh versions
    // Word1995 = 7,

    public static final int WORD_1997 = 8;

    public static final int WORD_2000 = 9;

    public static final int WORD_2002 = 10;

    public static final int WORD_2003 = 11;

    public static final int WORD_2007 = 12;
        
    public static final int WORD_2010 = 14;
        
    public static final int WORD_2013 = 15;

    public static final int WORD_2016 = 16;

    public static final int WORD_2019 = 17;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MsWordVersionCore value.
    /// </summary>
    public static String getName(/*MsWordVersionCore*/int msWordVersionCore)
    {
    	switch (msWordVersionCore)
    	{
    		case UNSPECIFIED: return "UNSPECIFIED";
    		case WORD_1997: return "WORD_1997";
    		case WORD_2000: return "WORD_2000";
    		case WORD_2002: return "WORD_2002";
    		case WORD_2003: return "WORD_2003";
    		case WORD_2007: return "WORD_2007";
    		case WORD_2010: return "WORD_2010";
    		case WORD_2013: return "WORD_2013";
    		case WORD_2016: return "WORD_2016";
    		case WORD_2019: return "WORD_2019";
    		default: return "Unknown MsWordVersionCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MsWordVersionCore value.
    /// </summary>
    public static String toString(/*MsWordVersionCore*/int msWordVersionCore)
    {
    	switch (msWordVersionCore)
    	{
    		case UNSPECIFIED: return "Unspecified";
    		case WORD_1997: return "Word1997";
    		case WORD_2000: return "Word2000";
    		case WORD_2002: return "Word2002";
    		case WORD_2003: return "Word2003";
    		case WORD_2007: return "Word2007";
    		case WORD_2010: return "Word2010";
    		case WORD_2013: return "Word2013";
    		case WORD_2016: return "Word2016";
    		case WORD_2019: return "Word2019";
    		default: return "Unknown MsWordVersionCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MsWordVersionCore name.
    /// </summary>
    public static /*MsWordVersionCore*/int fromName(String msWordVersionCoreName)
    {
    	if ("UNSPECIFIED".equals(msWordVersionCoreName)) return UNSPECIFIED;
    	if ("WORD_1997".equals(msWordVersionCoreName)) return WORD_1997;
    	if ("WORD_2000".equals(msWordVersionCoreName)) return WORD_2000;
    	if ("WORD_2002".equals(msWordVersionCoreName)) return WORD_2002;
    	if ("WORD_2003".equals(msWordVersionCoreName)) return WORD_2003;
    	if ("WORD_2007".equals(msWordVersionCoreName)) return WORD_2007;
    	if ("WORD_2010".equals(msWordVersionCoreName)) return WORD_2010;
    	if ("WORD_2013".equals(msWordVersionCoreName)) return WORD_2013;
    	if ("WORD_2016".equals(msWordVersionCoreName)) return WORD_2016;
    	if ("WORD_2019".equals(msWordVersionCoreName)) return WORD_2019;
    	throw new IllegalArgumentException("Unknown MsWordVersionCore name.");
    }
    
    /// <summary>
    /// Returns array of MsWordVersionCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNSPECIFIED,
    		WORD_1997,
    		WORD_2000,
    		WORD_2002,
    		WORD_2003,
    		WORD_2007,
    		WORD_2010,
    		WORD_2013,
    		WORD_2016,
    		WORD_2019,
    	};
    }

    public static final int length = 10;
}

