// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/02/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a building block type. The type might affect the visibility and behavior of the building block
/// in Microsoft Word.
/// </summary>
/// <remarks>
/// 
/// <para>Corresponds to the <b>ST_DocPartType</b> type in OOXML.</para>
/// 
/// <seealso cref="BuildingBlock.Type"/>
/// </remarks>
public /*enum*/ final class BuildingBlockType
{
    private BuildingBlockType(){}
    
    /// <summary>
    /// No type information is specified for the building block.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Allows the building block to be automatically inserted into the document whenever 
    /// its name is entered into an application.
    /// </summary>
    public static final int AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT = 1;
    /// <summary>
    /// The building block is a structured document tag placeholder text.
    /// </summary>
    public static final int STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT = 2;
    /// <summary>
    /// The building block is a form field help text.
    /// </summary>
    public static final int FORM_FIELD_HELP_TEXT = 3;
    /// <summary>
    /// The building block is a normal (i.e. regular) glossary document entry.
    /// </summary>
    public static final int NORMAL = 4;
    /// <summary>
    /// The building block is associated with the spelling and grammar tools.
    /// </summary>
    public static final int AUTO_CORRECT = 5;
    /// <summary>
    /// The building block is an AutoText entry.
    /// </summary>
    public static final int AUTO_TEXT = 6;
    /// <summary>
    /// The building block is associated with all types.
    /// </summary>
    public static final int ALL = 7;

    /// <summary>
    /// Save as <see cref="None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BuildingBlockType value.
    /// </summary>
    public static String getName(/*BuildingBlockType*/int buildingBlockType)
    {
    	switch (buildingBlockType)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT: return "AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT";
    		case STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT: return "STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT";
    		case FORM_FIELD_HELP_TEXT: return "FORM_FIELD_HELP_TEXT";
    		case NORMAL: return "NORMAL";
    		case AUTO_CORRECT: return "AUTO_CORRECT";
    		case AUTO_TEXT: return "AUTO_TEXT";
    		case ALL: return "ALL";
    		default: return "Unknown BuildingBlockType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BuildingBlockType value.
    /// </summary>
    public static String toString(/*BuildingBlockType*/int buildingBlockType)
    {
    	switch (buildingBlockType)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT: return "AutomaticallyReplaceNameWithContent";
    		case STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT: return "StructuredDocumentTagPlaceholderText";
    		case FORM_FIELD_HELP_TEXT: return "FormFieldHelpText";
    		case NORMAL: return "Normal";
    		case AUTO_CORRECT: return "AutoCorrect";
    		case AUTO_TEXT: return "AutoText";
    		case ALL: return "All";
    		default: return "Unknown BuildingBlockType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BuildingBlockType name.
    /// </summary>
    public static /*BuildingBlockType*/int fromName(String buildingBlockTypeName)
    {
    	if ("NONE".equals(buildingBlockTypeName)) return NONE;
    	if ("AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT".equals(buildingBlockTypeName)) return AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT;
    	if ("STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT".equals(buildingBlockTypeName)) return STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT;
    	if ("FORM_FIELD_HELP_TEXT".equals(buildingBlockTypeName)) return FORM_FIELD_HELP_TEXT;
    	if ("NORMAL".equals(buildingBlockTypeName)) return NORMAL;
    	if ("AUTO_CORRECT".equals(buildingBlockTypeName)) return AUTO_CORRECT;
    	if ("AUTO_TEXT".equals(buildingBlockTypeName)) return AUTO_TEXT;
    	if ("ALL".equals(buildingBlockTypeName)) return ALL;
    	if ("DEFAULT".equals(buildingBlockTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown BuildingBlockType name.");
    }
    
    /// <summary>
    /// Returns array of BuildingBlockType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		AUTOMATICALLY_REPLACE_NAME_WITH_CONTENT,
    		STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT,
    		FORM_FIELD_HELP_TEXT,
    		NORMAL,
    		AUTO_CORRECT,
    		AUTO_TEXT,
    		ALL,
    		DEFAULT,
    	};
    }

    public static final int length = 9;
}

