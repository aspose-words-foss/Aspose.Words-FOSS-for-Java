// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/06/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the set of possible aliases (or editing groups) which can be used as aliases to
/// determine if the current user shall be allowed to edit a single range 
/// defined by an editable range within a document.
/// </summary>
public /*enum*/ final class EditorType
{
    private EditorType(){}
    
    /// <summary>
    /// Means that editor type is not specified.
    /// </summary>
    public static final int UNSPECIFIED = 0;

    /// <summary>
    /// Specifies that users associated with the Administrators group shall be allowed to edit editable ranges using
    /// this editing type when document protection is enabled.
    /// </summary>
    public static final int ADMINISTRATORS = 1;

    /// <summary>
    /// Specifies that users associated with the Contributors group shall be allowed to edit editable ranges using
    /// this editing type when document protection is enabled.
    /// </summary>
    public static final int CONTRIBUTORS = 2;

    /// <summary>
    /// Specifies that users associated with the Current group shall be allowed to edit editable ranges using this
    /// editing type when document protection is enabled.
    /// </summary>
    public static final int CURRENT = 3;

    /// <summary>
    /// Specifies that users associated with the Editors group shall be allowed to edit editable ranges using this
    /// editing type when document protection is enabled.
    /// </summary>
    public static final int EDITORS = 4;

    /// <summary>
    /// Specifies that all users that open the document shall be allowed to edit editable ranges using this editing
    /// type when document protection is enabled.
    /// </summary>
    public static final int EVERYONE = 5;

    /// <summary>
    /// Specifies that none of the users that open the document shall be allowed to edit editable ranges
    /// using this editing type when document protection is enabled.
    /// </summary>
    public static final int NONE = 6;

    /// <summary>
    /// Specifies that users associated with the Owners group shall be allowed to edit editable ranges using this
    /// editing type when document protection is enabled.
    /// </summary>
    public static final int OWNERS = 7;

    /// <summary>
    /// Same as <see cref="Unspecified"/>.
    /// </summary>
    public static final int DEFAULT = UNSPECIFIED;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EditorType value.
    /// </summary>
    public static String getName(/*EditorType*/int editorType)
    {
    	switch (editorType)
    	{
    		case UNSPECIFIED:
    		/*case DEFAULT:*/
    			return "UNSPECIFIED" + " | " + "DEFAULT";
    		case ADMINISTRATORS: return "ADMINISTRATORS";
    		case CONTRIBUTORS: return "CONTRIBUTORS";
    		case CURRENT: return "CURRENT";
    		case EDITORS: return "EDITORS";
    		case EVERYONE: return "EVERYONE";
    		case NONE: return "NONE";
    		case OWNERS: return "OWNERS";
    		default: return "Unknown EditorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EditorType value.
    /// </summary>
    public static String toString(/*EditorType*/int editorType)
    {
    	switch (editorType)
    	{
    		case UNSPECIFIED:
    		/*case DEFAULT:*/
    			return "Unspecified" + " | " + "Default";
    		case ADMINISTRATORS: return "Administrators";
    		case CONTRIBUTORS: return "Contributors";
    		case CURRENT: return "Current";
    		case EDITORS: return "Editors";
    		case EVERYONE: return "Everyone";
    		case NONE: return "None";
    		case OWNERS: return "Owners";
    		default: return "Unknown EditorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EditorType name.
    /// </summary>
    public static /*EditorType*/int fromName(String editorTypeName)
    {
    	if ("UNSPECIFIED".equals(editorTypeName)) return UNSPECIFIED;
    	if ("ADMINISTRATORS".equals(editorTypeName)) return ADMINISTRATORS;
    	if ("CONTRIBUTORS".equals(editorTypeName)) return CONTRIBUTORS;
    	if ("CURRENT".equals(editorTypeName)) return CURRENT;
    	if ("EDITORS".equals(editorTypeName)) return EDITORS;
    	if ("EVERYONE".equals(editorTypeName)) return EVERYONE;
    	if ("NONE".equals(editorTypeName)) return NONE;
    	if ("OWNERS".equals(editorTypeName)) return OWNERS;
    	if ("DEFAULT".equals(editorTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown EditorType name.");
    }
    
    /// <summary>
    /// Returns array of EditorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNSPECIFIED,
    		ADMINISTRATORS,
    		CONTRIBUTORS,
    		CURRENT,
    		EDITORS,
    		EVERYONE,
    		NONE,
    		OWNERS,
    		DEFAULT,
    	};
    }

    public static final int length = 9;
}

