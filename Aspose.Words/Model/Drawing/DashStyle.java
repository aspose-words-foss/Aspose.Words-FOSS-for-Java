// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Dashed line style.
/// </summary>
/// <seealso cref="Stroke.DashStyle"/>
public /*enum*/ final class DashStyle
{
    private DashStyle(){}
    
    /// <summary>
    /// Solid (continuous) pen.
    /// </summary>
    public static final int SOLID = 0;
    /// <summary>
    /// System dash style.
    /// </summary>
    public static final int SHORT_DASH = 1;
    /// <summary>
    /// System dash style.
    /// </summary>
    public static final int SHORT_DOT = 2;
    /// <summary>
    /// System dash style.
    /// </summary>
    public static final int SHORT_DASH_DOT = 3;
    /// <summary>
    /// System dash style.
    /// </summary>
    public static final int SHORT_DASH_DOT_DOT = 4;
    /// <summary>
    /// Square dot style.
    /// </summary>
    public static final int DOT = 5;
    /// <summary>
    /// Dash style.
    /// </summary>
    public static final int DASH = 6;
    /// <summary>
    /// Long dash style.
    /// </summary>
    public static final int LONG_DASH = 7;
    /// <summary>
    /// Dash short dash.
    /// </summary>
    public static final int DASH_DOT = 8;
    /// <summary>
    /// Long dash short dash.
    /// </summary>
    public static final int LONG_DASH_DOT = 9;
    /// <summary>
    /// Long dash short dash short dash.
    /// </summary>
    public static final int LONG_DASH_DOT_DOT = 10;

    /// <summary>
    /// Same as <see cref="Solid"/>.
    /// </summary>
    public static final int DEFAULT = SOLID;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DashStyle value.
    /// </summary>
    public static String getName(/*DashStyle*/int dashStyle)
    {
    	switch (dashStyle)
    	{
    		case SOLID:
    		/*case DEFAULT:*/
    			return "SOLID" + " | " + "DEFAULT";
    		case SHORT_DASH: return "SHORT_DASH";
    		case SHORT_DOT: return "SHORT_DOT";
    		case SHORT_DASH_DOT: return "SHORT_DASH_DOT";
    		case SHORT_DASH_DOT_DOT: return "SHORT_DASH_DOT_DOT";
    		case DOT: return "DOT";
    		case DASH: return "DASH";
    		case LONG_DASH: return "LONG_DASH";
    		case DASH_DOT: return "DASH_DOT";
    		case LONG_DASH_DOT: return "LONG_DASH_DOT";
    		case LONG_DASH_DOT_DOT: return "LONG_DASH_DOT_DOT";
    		default: return "Unknown DashStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DashStyle value.
    /// </summary>
    public static String toString(/*DashStyle*/int dashStyle)
    {
    	switch (dashStyle)
    	{
    		case SOLID:
    		/*case DEFAULT:*/
    			return "Solid" + " | " + "Default";
    		case SHORT_DASH: return "ShortDash";
    		case SHORT_DOT: return "ShortDot";
    		case SHORT_DASH_DOT: return "ShortDashDot";
    		case SHORT_DASH_DOT_DOT: return "ShortDashDotDot";
    		case DOT: return "Dot";
    		case DASH: return "Dash";
    		case LONG_DASH: return "LongDash";
    		case DASH_DOT: return "DashDot";
    		case LONG_DASH_DOT: return "LongDashDot";
    		case LONG_DASH_DOT_DOT: return "LongDashDotDot";
    		default: return "Unknown DashStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DashStyle name.
    /// </summary>
    public static /*DashStyle*/int fromName(String dashStyleName)
    {
    	if ("SOLID".equals(dashStyleName)) return SOLID;
    	if ("SHORT_DASH".equals(dashStyleName)) return SHORT_DASH;
    	if ("SHORT_DOT".equals(dashStyleName)) return SHORT_DOT;
    	if ("SHORT_DASH_DOT".equals(dashStyleName)) return SHORT_DASH_DOT;
    	if ("SHORT_DASH_DOT_DOT".equals(dashStyleName)) return SHORT_DASH_DOT_DOT;
    	if ("DOT".equals(dashStyleName)) return DOT;
    	if ("DASH".equals(dashStyleName)) return DASH;
    	if ("LONG_DASH".equals(dashStyleName)) return LONG_DASH;
    	if ("DASH_DOT".equals(dashStyleName)) return DASH_DOT;
    	if ("LONG_DASH_DOT".equals(dashStyleName)) return LONG_DASH_DOT;
    	if ("LONG_DASH_DOT_DOT".equals(dashStyleName)) return LONG_DASH_DOT_DOT;
    	if ("DEFAULT".equals(dashStyleName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown DashStyle name.");
    }
    
    /// <summary>
    /// Returns array of DashStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SOLID,
    		SHORT_DASH,
    		SHORT_DOT,
    		SHORT_DASH_DOT,
    		SHORT_DASH_DOT_DOT,
    		DOT,
    		DASH,
    		LONG_DASH,
    		DASH_DOT,
    		LONG_DASH_DOT,
    		LONG_DASH_DOT_DOT,
    		DEFAULT,
    	};
    }

    public static final int length = 12;
}

