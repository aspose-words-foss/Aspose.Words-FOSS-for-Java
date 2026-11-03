// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/10/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a model in a VBA project.
/// </summary>
public /*enum*/ final class VbaModuleType
{
    private VbaModuleType(){}
    
    /// <summary>
    /// A type of VBA project item that specifies a module for embedded macros and programmatic access operations 
    /// that are associated with a document.
    /// </summary>
    public static final int DOCUMENT_MODULE = 0;

    /// <summary>
    /// A collection of subroutines and functions.
    /// </summary>
    public static final int PROCEDURAL_MODULE = 1;

    /// <summary>
    /// A module that contains the definition for a new object. Each instance of a class creates a new object,
    /// and procedures that are defined in the module become properties and methods of the object.
    /// </summary>
    public static final int CLASS_MODULE = 2;

    /// <summary>
    /// A VBA module that extends the methods and properties of an ActiveX control that has been registered with the project.
    /// </summary>
    public static final int DESIGNER_MODULE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer VbaModuleType value.
    /// </summary>
    public static String getName(/*VbaModuleType*/int vbaModuleType)
    {
    	switch (vbaModuleType)
    	{
    		case DOCUMENT_MODULE: return "DOCUMENT_MODULE";
    		case PROCEDURAL_MODULE: return "PROCEDURAL_MODULE";
    		case CLASS_MODULE: return "CLASS_MODULE";
    		case DESIGNER_MODULE: return "DESIGNER_MODULE";
    		default: return "Unknown VbaModuleType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VbaModuleType value.
    /// </summary>
    public static String toString(/*VbaModuleType*/int vbaModuleType)
    {
    	switch (vbaModuleType)
    	{
    		case DOCUMENT_MODULE: return "DocumentModule";
    		case PROCEDURAL_MODULE: return "ProceduralModule";
    		case CLASS_MODULE: return "ClassModule";
    		case DESIGNER_MODULE: return "DesignerModule";
    		default: return "Unknown VbaModuleType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by VbaModuleType name.
    /// </summary>
    public static /*VbaModuleType*/int fromName(String vbaModuleTypeName)
    {
    	if ("DOCUMENT_MODULE".equals(vbaModuleTypeName)) return DOCUMENT_MODULE;
    	if ("PROCEDURAL_MODULE".equals(vbaModuleTypeName)) return PROCEDURAL_MODULE;
    	if ("CLASS_MODULE".equals(vbaModuleTypeName)) return CLASS_MODULE;
    	if ("DESIGNER_MODULE".equals(vbaModuleTypeName)) return DESIGNER_MODULE;
    	throw new IllegalArgumentException("Unknown VbaModuleType name.");
    }
    
    /// <summary>
    /// Returns array of VbaModuleType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DOCUMENT_MODULE,
    		PROCEDURAL_MODULE,
    		CLASS_MODULE,
    		DESIGNER_MODULE,
    	};
    }

    public static final int length = 4;
}

