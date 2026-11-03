// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/02/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the predefined gallery into which a building block is classified.
/// </summary>
/// <remarks>
/// 
/// <para>Corresponds to the <b>ST_DocPartGallery</b> type in OOXML.</para>
/// 
/// <seealso cref="BuildingBlock.Gallery"/>
/// </remarks>
public /*enum*/ final class BuildingBlockGallery
{
    private BuildingBlockGallery(){}
    
    /// <summary>
    /// Specifies that this glossary document entry shall be associated with all possible gallery classification values.
    /// </summary>
    public static final int ALL = 0;
    /// <summary>
    /// </summary>
    public static final int AUTO_TEXT = 1;
    /// <summary>
    /// </summary>
    public static final int BIBLIOGRAPHY = 2;
    /// <summary>
    /// </summary>
    public static final int COVER_PAGE = 3;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_AUTO_TEXT = 4;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_BIBLIOGRAPHY = 5;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_COVER_PAGE = 6;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_EQUATIONS = 7;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_FOOTERS = 8;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_HEADERS = 9;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_1 = 10;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_2 = 11;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_3 = 12;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_4 = 13;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_5 = 14;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_PAGE_NUMBER = 15;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_PAGE_NUMBER_AT_BOTTOM = 16;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_PAGE_NUMBER_AT_MARGIN = 17;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_PAGE_NUMBER_AT_TOP = 18;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_QUICK_PARTS = 19;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_TABLE_OF_CONTENTS = 20;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_TABLES = 21;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_TEXT_BOX = 22;
    /// <summary>
    /// </summary>
    public static final int CUSTOM_WATERMARKS = 23;
    /// <summary>
    /// </summary>
    public static final int NO_GALLERY = 24;
    /// <summary>
    /// </summary>
    public static final int QUICK_PARTS = 25;
    /// <summary>
    /// </summary>
    public static final int EQUATIONS = 26;
    /// <summary>
    /// </summary>
    public static final int FOOTERS = 27;
    /// <summary>
    /// </summary>
    public static final int HEADERS = 28;
    /// <summary>
    /// </summary>
    public static final int PAGE_NUMBER = 29;
    /// <summary>
    /// </summary>
    public static final int PAGE_NUMBER_AT_BOTTOM = 30;
    /// <summary>
    /// </summary>
    public static final int PAGE_NUMBER_AT_MARGIN = 31;
    /// <summary>
    /// </summary>
    public static final int PAGE_NUMBER_AT_TOP = 32;
    /// <summary>
    /// </summary>
    public static final int STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT = 33;
    /// <summary>
    /// </summary>
    public static final int TABLE_OF_CONTENTS = 34;
    /// <summary>
    /// </summary>
    public static final int TABLES = 35;
    /// <summary>
    /// </summary>
    public static final int TEXT_BOX = 36;
    /// <summary>
    /// </summary>
    public static final int WATERMARKS = 37;

    /// <summary>
    /// Same as <see cref="All"/>.
    /// </summary>
    public static final int DEFAULT = ALL;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BuildingBlockGallery value.
    /// </summary>
    public static String getName(/*BuildingBlockGallery*/int buildingBlockGallery)
    {
    	switch (buildingBlockGallery)
    	{
    		case ALL:
    		/*case DEFAULT:*/
    			return "ALL" + " | " + "DEFAULT";
    		case AUTO_TEXT: return "AUTO_TEXT";
    		case BIBLIOGRAPHY: return "BIBLIOGRAPHY";
    		case COVER_PAGE: return "COVER_PAGE";
    		case CUSTOM_AUTO_TEXT: return "CUSTOM_AUTO_TEXT";
    		case CUSTOM_BIBLIOGRAPHY: return "CUSTOM_BIBLIOGRAPHY";
    		case CUSTOM_COVER_PAGE: return "CUSTOM_COVER_PAGE";
    		case CUSTOM_EQUATIONS: return "CUSTOM_EQUATIONS";
    		case CUSTOM_FOOTERS: return "CUSTOM_FOOTERS";
    		case CUSTOM_HEADERS: return "CUSTOM_HEADERS";
    		case CUSTOM_1: return "CUSTOM_1";
    		case CUSTOM_2: return "CUSTOM_2";
    		case CUSTOM_3: return "CUSTOM_3";
    		case CUSTOM_4: return "CUSTOM_4";
    		case CUSTOM_5: return "CUSTOM_5";
    		case CUSTOM_PAGE_NUMBER: return "CUSTOM_PAGE_NUMBER";
    		case CUSTOM_PAGE_NUMBER_AT_BOTTOM: return "CUSTOM_PAGE_NUMBER_AT_BOTTOM";
    		case CUSTOM_PAGE_NUMBER_AT_MARGIN: return "CUSTOM_PAGE_NUMBER_AT_MARGIN";
    		case CUSTOM_PAGE_NUMBER_AT_TOP: return "CUSTOM_PAGE_NUMBER_AT_TOP";
    		case CUSTOM_QUICK_PARTS: return "CUSTOM_QUICK_PARTS";
    		case CUSTOM_TABLE_OF_CONTENTS: return "CUSTOM_TABLE_OF_CONTENTS";
    		case CUSTOM_TABLES: return "CUSTOM_TABLES";
    		case CUSTOM_TEXT_BOX: return "CUSTOM_TEXT_BOX";
    		case CUSTOM_WATERMARKS: return "CUSTOM_WATERMARKS";
    		case NO_GALLERY: return "NO_GALLERY";
    		case QUICK_PARTS: return "QUICK_PARTS";
    		case EQUATIONS: return "EQUATIONS";
    		case FOOTERS: return "FOOTERS";
    		case HEADERS: return "HEADERS";
    		case PAGE_NUMBER: return "PAGE_NUMBER";
    		case PAGE_NUMBER_AT_BOTTOM: return "PAGE_NUMBER_AT_BOTTOM";
    		case PAGE_NUMBER_AT_MARGIN: return "PAGE_NUMBER_AT_MARGIN";
    		case PAGE_NUMBER_AT_TOP: return "PAGE_NUMBER_AT_TOP";
    		case STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT: return "STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT";
    		case TABLE_OF_CONTENTS: return "TABLE_OF_CONTENTS";
    		case TABLES: return "TABLES";
    		case TEXT_BOX: return "TEXT_BOX";
    		case WATERMARKS: return "WATERMARKS";
    		default: return "Unknown BuildingBlockGallery value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BuildingBlockGallery value.
    /// </summary>
    public static String toString(/*BuildingBlockGallery*/int buildingBlockGallery)
    {
    	switch (buildingBlockGallery)
    	{
    		case ALL:
    		/*case DEFAULT:*/
    			return "All" + " | " + "Default";
    		case AUTO_TEXT: return "AutoText";
    		case BIBLIOGRAPHY: return "Bibliography";
    		case COVER_PAGE: return "CoverPage";
    		case CUSTOM_AUTO_TEXT: return "CustomAutoText";
    		case CUSTOM_BIBLIOGRAPHY: return "CustomBibliography";
    		case CUSTOM_COVER_PAGE: return "CustomCoverPage";
    		case CUSTOM_EQUATIONS: return "CustomEquations";
    		case CUSTOM_FOOTERS: return "CustomFooters";
    		case CUSTOM_HEADERS: return "CustomHeaders";
    		case CUSTOM_1: return "Custom1";
    		case CUSTOM_2: return "Custom2";
    		case CUSTOM_3: return "Custom3";
    		case CUSTOM_4: return "Custom4";
    		case CUSTOM_5: return "Custom5";
    		case CUSTOM_PAGE_NUMBER: return "CustomPageNumber";
    		case CUSTOM_PAGE_NUMBER_AT_BOTTOM: return "CustomPageNumberAtBottom";
    		case CUSTOM_PAGE_NUMBER_AT_MARGIN: return "CustomPageNumberAtMargin";
    		case CUSTOM_PAGE_NUMBER_AT_TOP: return "CustomPageNumberAtTop";
    		case CUSTOM_QUICK_PARTS: return "CustomQuickParts";
    		case CUSTOM_TABLE_OF_CONTENTS: return "CustomTableOfContents";
    		case CUSTOM_TABLES: return "CustomTables";
    		case CUSTOM_TEXT_BOX: return "CustomTextBox";
    		case CUSTOM_WATERMARKS: return "CustomWatermarks";
    		case NO_GALLERY: return "NoGallery";
    		case QUICK_PARTS: return "QuickParts";
    		case EQUATIONS: return "Equations";
    		case FOOTERS: return "Footers";
    		case HEADERS: return "Headers";
    		case PAGE_NUMBER: return "PageNumber";
    		case PAGE_NUMBER_AT_BOTTOM: return "PageNumberAtBottom";
    		case PAGE_NUMBER_AT_MARGIN: return "PageNumberAtMargin";
    		case PAGE_NUMBER_AT_TOP: return "PageNumberAtTop";
    		case STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT: return "StructuredDocumentTagPlaceholderText";
    		case TABLE_OF_CONTENTS: return "TableOfContents";
    		case TABLES: return "Tables";
    		case TEXT_BOX: return "TextBox";
    		case WATERMARKS: return "Watermarks";
    		default: return "Unknown BuildingBlockGallery value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BuildingBlockGallery name.
    /// </summary>
    public static /*BuildingBlockGallery*/int fromName(String buildingBlockGalleryName)
    {
    	if ("ALL".equals(buildingBlockGalleryName)) return ALL;
    	if ("AUTO_TEXT".equals(buildingBlockGalleryName)) return AUTO_TEXT;
    	if ("BIBLIOGRAPHY".equals(buildingBlockGalleryName)) return BIBLIOGRAPHY;
    	if ("COVER_PAGE".equals(buildingBlockGalleryName)) return COVER_PAGE;
    	if ("CUSTOM_AUTO_TEXT".equals(buildingBlockGalleryName)) return CUSTOM_AUTO_TEXT;
    	if ("CUSTOM_BIBLIOGRAPHY".equals(buildingBlockGalleryName)) return CUSTOM_BIBLIOGRAPHY;
    	if ("CUSTOM_COVER_PAGE".equals(buildingBlockGalleryName)) return CUSTOM_COVER_PAGE;
    	if ("CUSTOM_EQUATIONS".equals(buildingBlockGalleryName)) return CUSTOM_EQUATIONS;
    	if ("CUSTOM_FOOTERS".equals(buildingBlockGalleryName)) return CUSTOM_FOOTERS;
    	if ("CUSTOM_HEADERS".equals(buildingBlockGalleryName)) return CUSTOM_HEADERS;
    	if ("CUSTOM_1".equals(buildingBlockGalleryName)) return CUSTOM_1;
    	if ("CUSTOM_2".equals(buildingBlockGalleryName)) return CUSTOM_2;
    	if ("CUSTOM_3".equals(buildingBlockGalleryName)) return CUSTOM_3;
    	if ("CUSTOM_4".equals(buildingBlockGalleryName)) return CUSTOM_4;
    	if ("CUSTOM_5".equals(buildingBlockGalleryName)) return CUSTOM_5;
    	if ("CUSTOM_PAGE_NUMBER".equals(buildingBlockGalleryName)) return CUSTOM_PAGE_NUMBER;
    	if ("CUSTOM_PAGE_NUMBER_AT_BOTTOM".equals(buildingBlockGalleryName)) return CUSTOM_PAGE_NUMBER_AT_BOTTOM;
    	if ("CUSTOM_PAGE_NUMBER_AT_MARGIN".equals(buildingBlockGalleryName)) return CUSTOM_PAGE_NUMBER_AT_MARGIN;
    	if ("CUSTOM_PAGE_NUMBER_AT_TOP".equals(buildingBlockGalleryName)) return CUSTOM_PAGE_NUMBER_AT_TOP;
    	if ("CUSTOM_QUICK_PARTS".equals(buildingBlockGalleryName)) return CUSTOM_QUICK_PARTS;
    	if ("CUSTOM_TABLE_OF_CONTENTS".equals(buildingBlockGalleryName)) return CUSTOM_TABLE_OF_CONTENTS;
    	if ("CUSTOM_TABLES".equals(buildingBlockGalleryName)) return CUSTOM_TABLES;
    	if ("CUSTOM_TEXT_BOX".equals(buildingBlockGalleryName)) return CUSTOM_TEXT_BOX;
    	if ("CUSTOM_WATERMARKS".equals(buildingBlockGalleryName)) return CUSTOM_WATERMARKS;
    	if ("NO_GALLERY".equals(buildingBlockGalleryName)) return NO_GALLERY;
    	if ("QUICK_PARTS".equals(buildingBlockGalleryName)) return QUICK_PARTS;
    	if ("EQUATIONS".equals(buildingBlockGalleryName)) return EQUATIONS;
    	if ("FOOTERS".equals(buildingBlockGalleryName)) return FOOTERS;
    	if ("HEADERS".equals(buildingBlockGalleryName)) return HEADERS;
    	if ("PAGE_NUMBER".equals(buildingBlockGalleryName)) return PAGE_NUMBER;
    	if ("PAGE_NUMBER_AT_BOTTOM".equals(buildingBlockGalleryName)) return PAGE_NUMBER_AT_BOTTOM;
    	if ("PAGE_NUMBER_AT_MARGIN".equals(buildingBlockGalleryName)) return PAGE_NUMBER_AT_MARGIN;
    	if ("PAGE_NUMBER_AT_TOP".equals(buildingBlockGalleryName)) return PAGE_NUMBER_AT_TOP;
    	if ("STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT".equals(buildingBlockGalleryName)) return STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT;
    	if ("TABLE_OF_CONTENTS".equals(buildingBlockGalleryName)) return TABLE_OF_CONTENTS;
    	if ("TABLES".equals(buildingBlockGalleryName)) return TABLES;
    	if ("TEXT_BOX".equals(buildingBlockGalleryName)) return TEXT_BOX;
    	if ("WATERMARKS".equals(buildingBlockGalleryName)) return WATERMARKS;
    	if ("DEFAULT".equals(buildingBlockGalleryName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown BuildingBlockGallery name.");
    }
    
    /// <summary>
    /// Returns array of BuildingBlockGallery values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALL,
    		AUTO_TEXT,
    		BIBLIOGRAPHY,
    		COVER_PAGE,
    		CUSTOM_AUTO_TEXT,
    		CUSTOM_BIBLIOGRAPHY,
    		CUSTOM_COVER_PAGE,
    		CUSTOM_EQUATIONS,
    		CUSTOM_FOOTERS,
    		CUSTOM_HEADERS,
    		CUSTOM_1,
    		CUSTOM_2,
    		CUSTOM_3,
    		CUSTOM_4,
    		CUSTOM_5,
    		CUSTOM_PAGE_NUMBER,
    		CUSTOM_PAGE_NUMBER_AT_BOTTOM,
    		CUSTOM_PAGE_NUMBER_AT_MARGIN,
    		CUSTOM_PAGE_NUMBER_AT_TOP,
    		CUSTOM_QUICK_PARTS,
    		CUSTOM_TABLE_OF_CONTENTS,
    		CUSTOM_TABLES,
    		CUSTOM_TEXT_BOX,
    		CUSTOM_WATERMARKS,
    		NO_GALLERY,
    		QUICK_PARTS,
    		EQUATIONS,
    		FOOTERS,
    		HEADERS,
    		PAGE_NUMBER,
    		PAGE_NUMBER_AT_BOTTOM,
    		PAGE_NUMBER_AT_MARGIN,
    		PAGE_NUMBER_AT_TOP,
    		STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT,
    		TABLE_OF_CONTENTS,
    		TABLES,
    		TEXT_BOX,
    		WATERMARKS,
    		DEFAULT,
    	};
    }

    public static final int length = 39;
}

