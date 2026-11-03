// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2012 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of change being tracked in <see cref="Revision"/>.
/// </summary>
// Additional comments are placed to indicate correspondence to MS Word DOM wdRevisionType enum.
public /*enum*/ final class RevisionType
{
    private RevisionType(){}
    
    // wdRevisionInsert - Insertion. 
    // wdRevisionCellInsertion - Table cell inserted. 
    /// <summary>
    /// New content was inserted in the document.
    /// </summary>
    public static final int INSERTION = 0;

    // wdRevisionDelete - Deletion. 
    // wdRevisionCellDeletion - Table cell deleted. 
    /// <summary>
    /// Content was removed from the document.
    /// </summary>
    public static final int DELETION = 1;
    
    // wdRevisionSectionProperty - Section property changed. 
    // wdRevisionTableProperty - Table property changed. 
    // wdRevisionParagraphProperty - Paragraph property changed. 
    // wdRevisionStyle - Style changed. 
    // wdRevisionProperty - Property changed. 
    // wdRevisionDisplayField - Field display changed. 
    // wdRevisionParagraphNumber - Paragraph number changed. 
    /// <summary>
    /// Change of formatting was applied to the parent node.
    /// </summary>
    public static final int FORMAT_CHANGE = 2;

    // wdRevisionStyleDefinition - Style definition changed. 
    /// <summary>
    /// Change of formatting was applied to the parent style.
    /// </summary>
    public static final int STYLE_DEFINITION_CHANGE = 3;

    // wdRevisionMovedFrom - Content moved from. 
    // wdRevisionMovedTo - Content moved to. 
    /// <summary>
    /// Content was moved in the document.
    /// </summary>
    public static final int MOVING = 4;

    // not supported in model
    // wdRevisionReconcile - Revision marked as reconciled conflict. 
    // wdRevisionConflict - Revision marked as a conflict. 
    // wdRevisionReplace - Replaced.
    // wdRevisionCellMerge - Table cells merged. 

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RevisionType value.
    /// </summary>
    public static String getName(/*RevisionType*/int revisionType)
    {
    	switch (revisionType)
    	{
    		case INSERTION: return "INSERTION";
    		case DELETION: return "DELETION";
    		case FORMAT_CHANGE: return "FORMAT_CHANGE";
    		case STYLE_DEFINITION_CHANGE: return "STYLE_DEFINITION_CHANGE";
    		case MOVING: return "MOVING";
    		default: return "Unknown RevisionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RevisionType value.
    /// </summary>
    public static String toString(/*RevisionType*/int revisionType)
    {
    	switch (revisionType)
    	{
    		case INSERTION: return "Insertion";
    		case DELETION: return "Deletion";
    		case FORMAT_CHANGE: return "FormatChange";
    		case STYLE_DEFINITION_CHANGE: return "StyleDefinitionChange";
    		case MOVING: return "Moving";
    		default: return "Unknown RevisionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RevisionType name.
    /// </summary>
    public static /*RevisionType*/int fromName(String revisionTypeName)
    {
    	if ("INSERTION".equals(revisionTypeName)) return INSERTION;
    	if ("DELETION".equals(revisionTypeName)) return DELETION;
    	if ("FORMAT_CHANGE".equals(revisionTypeName)) return FORMAT_CHANGE;
    	if ("STYLE_DEFINITION_CHANGE".equals(revisionTypeName)) return STYLE_DEFINITION_CHANGE;
    	if ("MOVING".equals(revisionTypeName)) return MOVING;
    	throw new IllegalArgumentException("Unknown RevisionType name.");
    }
    
    /// <summary>
    /// Returns array of RevisionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INSERTION,
    		DELETION,
    		FORMAT_CHANGE,
    		STYLE_DEFINITION_CHANGE,
    		MOVING,
    	};
    }

    public static final int length = 5;
}

