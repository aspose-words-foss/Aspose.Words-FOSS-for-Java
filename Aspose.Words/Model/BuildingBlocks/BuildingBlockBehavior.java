// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/02/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the behavior that shall be applied to the contents of the building block
/// when it is inserted into the main document.
/// </summary>
/// <remarks>
/// 
/// <para>Corresponds to the <b>ST_DocPartBehavior</b> type in OOXML.</para>
/// 
/// <seealso cref="BuildingBlock.Behavior"/>
/// </remarks>
public /*enum*/ final class BuildingBlockBehavior
{
    private BuildingBlockBehavior(){}
    
    /// <summary>
    /// Specifies that the building block shall be inserted as inline content.
    /// </summary>
    public static final int CONTENT = 0;
    /// <summary>
    /// Specifies that the building block shall be inserted into its own paragraph.
    /// </summary>
    public static final int PARAGRAPH = 1;
    /// <summary>
    /// Specifies that the building block shall be added into its own page.
    /// </summary>
    public static final int PAGE = 2;

    /// <summary>
    /// Same as <see cref="Content"/>.
    /// </summary>
    public static final int DEFAULT = CONTENT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BuildingBlockBehavior value.
    /// </summary>
    public static String getName(/*BuildingBlockBehavior*/int buildingBlockBehavior)
    {
    	switch (buildingBlockBehavior)
    	{
    		case CONTENT:
    		/*case DEFAULT:*/
    			return "CONTENT" + " | " + "DEFAULT";
    		case PARAGRAPH: return "PARAGRAPH";
    		case PAGE: return "PAGE";
    		default: return "Unknown BuildingBlockBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BuildingBlockBehavior value.
    /// </summary>
    public static String toString(/*BuildingBlockBehavior*/int buildingBlockBehavior)
    {
    	switch (buildingBlockBehavior)
    	{
    		case CONTENT:
    		/*case DEFAULT:*/
    			return "Content" + " | " + "Default";
    		case PARAGRAPH: return "Paragraph";
    		case PAGE: return "Page";
    		default: return "Unknown BuildingBlockBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BuildingBlockBehavior name.
    /// </summary>
    public static /*BuildingBlockBehavior*/int fromName(String buildingBlockBehaviorName)
    {
    	if ("CONTENT".equals(buildingBlockBehaviorName)) return CONTENT;
    	if ("PARAGRAPH".equals(buildingBlockBehaviorName)) return PARAGRAPH;
    	if ("PAGE".equals(buildingBlockBehaviorName)) return PAGE;
    	if ("DEFAULT".equals(buildingBlockBehaviorName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown BuildingBlockBehavior name.");
    }
    
    /// <summary>
    /// Returns array of BuildingBlockBehavior values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CONTENT,
    		PARAGRAPH,
    		PAGE,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

