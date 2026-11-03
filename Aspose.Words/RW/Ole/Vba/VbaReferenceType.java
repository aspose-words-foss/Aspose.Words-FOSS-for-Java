// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/09/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify the type of a <see cref="VbaReference"/> object.
/// </summary>
public /*enum*/ final class VbaReferenceType
{
    private VbaReferenceType(){}
    
    /// <summary>
    /// Specifies an Automation type library reference type.
    /// </summary>
    /// <remarks>
    /// This type corresponds to 2.3.4.2.2.5 REFERENCEREGISTERED Record of [MS-OVBA]:
    /// https://docs.microsoft.com/en-us/openspecs/office_file_formats/ms-ovba/6c39388e-96f5-4b93-b90a-ae625a063fcf 
    /// </remarks>
    public static final int REGISTERED = 0x0d;

    /// <summary>
    /// Specified an external VBA project reference type.
    /// </summary>
    /// <remarks>
    /// This type corresponds to 2.3.4.2.2.6 REFERENCEPROJECT Record of [MS-OVBA]:
    /// https://docs.microsoft.com/en-us/openspecs/office_file_formats/ms-ovba/08280eb0-d628-495c-867f-5985ed020142 
    /// </remarks>
    public static final int PROJECT = 0x0e;

    /// <summary>
    /// Specifies an original Automation type library reference type.
    ///  </summary>
    /// <remarks>
    /// This type corresponds to 2.3.4.2.2.4 REFERENCEORIGINAL Record of [MS-OVBA]:
    /// https://docs.microsoft.com/en-us/openspecs/office_file_formats/ms-ovba/3ba66994-8c7a-4634-b2da-f9331ace6686 
    /// </remarks>
    public static final int ORIGINAL = 0x33;

    /// <summary>
    /// Specifies a twiddled type library reference type.
    ///  </summary>
    /// <remarks>
    /// This type corresponds to 2.3.4.2.2.3 REFERENCECONTROL Record of [MS-OVBA]:
    /// https://docs.microsoft.com/en-us/openspecs/office_file_formats/ms-ovba/d64485fa-8562-4726-9c5e-11e8f01a81c0 
    /// </remarks>
    public static final int CONTROL = 0x2f;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer VbaReferenceType value.
    /// </summary>
    public static String getName(/*VbaReferenceType*/int vbaReferenceType)
    {
    	switch (vbaReferenceType)
    	{
    		case REGISTERED: return "REGISTERED";
    		case PROJECT: return "PROJECT";
    		case ORIGINAL: return "ORIGINAL";
    		case CONTROL: return "CONTROL";
    		default: return "Unknown VbaReferenceType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VbaReferenceType value.
    /// </summary>
    public static String toString(/*VbaReferenceType*/int vbaReferenceType)
    {
    	switch (vbaReferenceType)
    	{
    		case REGISTERED: return "Registered";
    		case PROJECT: return "Project";
    		case ORIGINAL: return "Original";
    		case CONTROL: return "Control";
    		default: return "Unknown VbaReferenceType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by VbaReferenceType name.
    /// </summary>
    public static /*VbaReferenceType*/int fromName(String vbaReferenceTypeName)
    {
    	if ("REGISTERED".equals(vbaReferenceTypeName)) return REGISTERED;
    	if ("PROJECT".equals(vbaReferenceTypeName)) return PROJECT;
    	if ("ORIGINAL".equals(vbaReferenceTypeName)) return ORIGINAL;
    	if ("CONTROL".equals(vbaReferenceTypeName)) return CONTROL;
    	throw new IllegalArgumentException("Unknown VbaReferenceType name.");
    }
    
    /// <summary>
    /// Returns array of VbaReferenceType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		REGISTERED,
    		PROJECT,
    		ORIGINAL,
    		CONTROL,
    	};
    }

    public static final int length = 4;
}

