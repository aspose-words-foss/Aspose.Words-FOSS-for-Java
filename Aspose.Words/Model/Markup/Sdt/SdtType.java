// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a structured document tag (SDT) node.
/// </summary>
public /*enum*/ final class SdtType
{
    private SdtType(){}
    
    /// <summary>
    /// No type is assigned to the SDT.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// The SDT represents a bibliography entry. 
    /// </summary>
    public static final int BIBLIOGRAPHY = 1;

    /// <summary>
    /// The SDT represents a citation.
    /// </summary>
    public static final int CITATION = 2;

    /// <summary>
    /// The SDT represents an equation.
    /// </summary>
    public static final int EQUATION = 3;

    /// <summary>
    /// The SDT represents a drop down list when displayed in the document.
    /// </summary>
    public static final int DROP_DOWN_LIST = 4;

    /// <summary>
    /// The SDT represents a combo box when displayed in the document.
    /// </summary>
    public static final int COMBO_BOX = 5;

    /// <summary>
    /// The SDT represents a date picker when displayed in the document.
    /// </summary>
    public static final int DATE = 6;

    /// <summary>
    /// The SDT represents a building block gallery type.
    /// </summary>
    public static final int BUILDING_BLOCK_GALLERY = 7;

    /// <summary>
    /// The SDT represents a document part type.
    /// </summary>
    public static final int DOC_PART_OBJ = 8;

    /// <summary>
    /// The SDT represents a restricted grouping when displayed in the document.
    /// </summary>
    public static final int GROUP = 9;

    /// <summary>
    /// The SDT represents a picture when displayed in the document.
    /// </summary>
    public static final int PICTURE = 10;

    /// <summary>
    /// The SDT represents a rich text box when displayed in the document.
    /// </summary>
    public static final int RICH_TEXT = 11;

    /// <summary>
    /// The SDT represents a plain text box when displayed in the document.
    /// </summary>
    public static final int PLAIN_TEXT = 12;

    /// <summary>
    /// The SDT represents a checkbox when displayed in the document.
    /// </summary>
    /// <remarks>
    /// This is MS-specific feature available since Office 2010 and not supported by the ISO/IEC 29500 OOXML standard.
    /// </remarks>
    public static final int CHECKBOX = 13;

    /// <summary>
    /// The SDT represents repeating section type when displayed in the document.
    /// </summary>
    /// <remarks>
    /// This is MS-specific feature available since Office 2013 and not supported by the ISO/IEC 29500 OOXML standard.
    /// </remarks>
    public static final int REPEATING_SECTION = 14;

    /// <summary>
    /// The SDT represents repeating section item.
    /// </summary>
    /// <remarks>
    /// This is MS-specific feature available since Office 2013 and not supported by the ISO/IEC 29500 OOXML standard.
    /// </remarks>
    public static final int REPEATING_SECTION_ITEM = 15;

    /// <summary>
    /// The SDT represents an entity picker that allows the user to select an instance of an external content type.
    /// </summary>
    /// <remarks>
    /// This is MS-specific feature available since Office 2010 and not supported by the ISO/IEC 29500 OOXML standard.
    /// </remarks>
    /// <dev>
    /// §2.6.1.15 entityPicker [MS-DOCX].
    /// Some additional information about entity pickers can be taken from the chapter USING EXTERNAL DATA IN WORD
    /// of Scot Hillier, Brad Stevenson. Professional Business Connectivity Services in SharePoint 2010.
    /// </dev>
    public static final int ENTITY_PICKER = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SdtType value.
    /// </summary>
    public static String getName(/*SdtType*/int sdtType)
    {
    	switch (sdtType)
    	{
    		case NONE: return "NONE";
    		case BIBLIOGRAPHY: return "BIBLIOGRAPHY";
    		case CITATION: return "CITATION";
    		case EQUATION: return "EQUATION";
    		case DROP_DOWN_LIST: return "DROP_DOWN_LIST";
    		case COMBO_BOX: return "COMBO_BOX";
    		case DATE: return "DATE";
    		case BUILDING_BLOCK_GALLERY: return "BUILDING_BLOCK_GALLERY";
    		case DOC_PART_OBJ: return "DOC_PART_OBJ";
    		case GROUP: return "GROUP";
    		case PICTURE: return "PICTURE";
    		case RICH_TEXT: return "RICH_TEXT";
    		case PLAIN_TEXT: return "PLAIN_TEXT";
    		case CHECKBOX: return "CHECKBOX";
    		case REPEATING_SECTION: return "REPEATING_SECTION";
    		case REPEATING_SECTION_ITEM: return "REPEATING_SECTION_ITEM";
    		case ENTITY_PICKER: return "ENTITY_PICKER";
    		default: return "Unknown SdtType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SdtType value.
    /// </summary>
    public static String toString(/*SdtType*/int sdtType)
    {
    	switch (sdtType)
    	{
    		case NONE: return "None";
    		case BIBLIOGRAPHY: return "Bibliography";
    		case CITATION: return "Citation";
    		case EQUATION: return "Equation";
    		case DROP_DOWN_LIST: return "DropDownList";
    		case COMBO_BOX: return "ComboBox";
    		case DATE: return "Date";
    		case BUILDING_BLOCK_GALLERY: return "BuildingBlockGallery";
    		case DOC_PART_OBJ: return "DocPartObj";
    		case GROUP: return "Group";
    		case PICTURE: return "Picture";
    		case RICH_TEXT: return "RichText";
    		case PLAIN_TEXT: return "PlainText";
    		case CHECKBOX: return "Checkbox";
    		case REPEATING_SECTION: return "RepeatingSection";
    		case REPEATING_SECTION_ITEM: return "RepeatingSectionItem";
    		case ENTITY_PICKER: return "EntityPicker";
    		default: return "Unknown SdtType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SdtType name.
    /// </summary>
    public static /*SdtType*/int fromName(String sdtTypeName)
    {
    	if ("NONE".equals(sdtTypeName)) return NONE;
    	if ("BIBLIOGRAPHY".equals(sdtTypeName)) return BIBLIOGRAPHY;
    	if ("CITATION".equals(sdtTypeName)) return CITATION;
    	if ("EQUATION".equals(sdtTypeName)) return EQUATION;
    	if ("DROP_DOWN_LIST".equals(sdtTypeName)) return DROP_DOWN_LIST;
    	if ("COMBO_BOX".equals(sdtTypeName)) return COMBO_BOX;
    	if ("DATE".equals(sdtTypeName)) return DATE;
    	if ("BUILDING_BLOCK_GALLERY".equals(sdtTypeName)) return BUILDING_BLOCK_GALLERY;
    	if ("DOC_PART_OBJ".equals(sdtTypeName)) return DOC_PART_OBJ;
    	if ("GROUP".equals(sdtTypeName)) return GROUP;
    	if ("PICTURE".equals(sdtTypeName)) return PICTURE;
    	if ("RICH_TEXT".equals(sdtTypeName)) return RICH_TEXT;
    	if ("PLAIN_TEXT".equals(sdtTypeName)) return PLAIN_TEXT;
    	if ("CHECKBOX".equals(sdtTypeName)) return CHECKBOX;
    	if ("REPEATING_SECTION".equals(sdtTypeName)) return REPEATING_SECTION;
    	if ("REPEATING_SECTION_ITEM".equals(sdtTypeName)) return REPEATING_SECTION_ITEM;
    	if ("ENTITY_PICKER".equals(sdtTypeName)) return ENTITY_PICKER;
    	throw new IllegalArgumentException("Unknown SdtType name.");
    }
    
    /// <summary>
    /// Returns array of SdtType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BIBLIOGRAPHY,
    		CITATION,
    		EQUATION,
    		DROP_DOWN_LIST,
    		COMBO_BOX,
    		DATE,
    		BUILDING_BLOCK_GALLERY,
    		DOC_PART_OBJ,
    		GROUP,
    		PICTURE,
    		RICH_TEXT,
    		PLAIN_TEXT,
    		CHECKBOX,
    		REPEATING_SECTION,
    		REPEATING_SECTION_ITEM,
    		ENTITY_PICKER,
    	};
    }

    public static final int length = 17;
}

