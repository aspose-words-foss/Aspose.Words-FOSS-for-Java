// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the appearance of a structured document tag.
/// </summary>
/// <dev>
/// Represents items of the 2.5.4.2 ST_SdtAppearance simple type.
/// </dev>
public /*enum*/ final class SdtAppearance
{
    private SdtAppearance(){}
    
    /// <summary>
    /// Represents a structured document tag shown as a shaded rectangle or bounding box.
    /// </summary>
    public static final int BOUNDING_BOX = 0;

    /// <summary>
    /// Represents a structured document tag shown as start and end markers.
    /// </summary>
    public static final int TAGS = 1;

    /// <summary>
    /// Represents a structured document tag that is not shown.
    /// </summary>
    public static final int HIDDEN = 2;

    /// <summary>
    /// Defaults to <see cref="BoundingBox"/>.
    /// </summary>
    public static final int DEFAULT = BOUNDING_BOX;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SdtAppearance value.
    /// </summary>
    public static String getName(/*SdtAppearance*/int sdtAppearance)
    {
    	switch (sdtAppearance)
    	{
    		case BOUNDING_BOX:
    		/*case DEFAULT:*/
    			return "BOUNDING_BOX" + " | " + "DEFAULT";
    		case TAGS: return "TAGS";
    		case HIDDEN: return "HIDDEN";
    		default: return "Unknown SdtAppearance value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SdtAppearance value.
    /// </summary>
    public static String toString(/*SdtAppearance*/int sdtAppearance)
    {
    	switch (sdtAppearance)
    	{
    		case BOUNDING_BOX:
    		/*case DEFAULT:*/
    			return "BoundingBox" + " | " + "Default";
    		case TAGS: return "Tags";
    		case HIDDEN: return "Hidden";
    		default: return "Unknown SdtAppearance value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SdtAppearance name.
    /// </summary>
    public static /*SdtAppearance*/int fromName(String sdtAppearanceName)
    {
    	if ("BOUNDING_BOX".equals(sdtAppearanceName)) return BOUNDING_BOX;
    	if ("TAGS".equals(sdtAppearanceName)) return TAGS;
    	if ("HIDDEN".equals(sdtAppearanceName)) return HIDDEN;
    	if ("DEFAULT".equals(sdtAppearanceName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown SdtAppearance name.");
    }
    
    /// <summary>
    /// Returns array of SdtAppearance values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOUNDING_BOX,
    		TAGS,
    		HIDDEN,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

