// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Implements 2.9.167 ODTPersist2. See http://msdn.microsoft.com/en-us/library/gg132373(v=office.12).aspx.
/// </summary>
/*enum*/ final class OdtPersist2
{
    private OdtPersist2(){}
    
    public static final int NONE = 0x0000;
    /// <summary>
    /// Specifies that the presentation of this OLE object in the document is in the Enhanced Metafile format. 
    /// This is different from StoredAsEMF in the case of an object being represented as an icon. 
    /// For icons, the icon can be an Enhanced Metafile even if the OLE object does not support the Enhanced Metafile format.
    /// </summary>
    public static final int EMF = 0x0001;
    public static final int RESERVED_1 = 0x0002;
    /// <summary>
    /// Specifies whether the application that saved this Word Binary file had queried this OLE object 
    /// to determine whether it supported the Enhanced Metafile format.
    /// </summary>
    public static final int QUERIED_EMF = 0x0004;
    /// <summary>
    /// Specifies that this OLE object supports the Enhanced Metafile format.
    /// </summary>
    public static final int STORED_AS_EMF = 0x0008;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OdtPersist2 value.
    /// </summary>
    public static String getName(/*OdtPersist2*/int odtPersist2)
    {
    	switch (odtPersist2)
    	{
    		case NONE: return "NONE";
    		case EMF: return "EMF";
    		case RESERVED_1: return "RESERVED_1";
    		case QUERIED_EMF: return "QUERIED_EMF";
    		case STORED_AS_EMF: return "STORED_AS_EMF";
    		default: return "Unknown OdtPersist2 value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer OdtPersist2 value.
    /// </summary>
    public static Set<String> getNames(/*OdtPersist2*/int odtPersist2)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & odtPersist2) == NONE) names.add("NONE");
    	if ((EMF & odtPersist2) == EMF) names.add("EMF");
    	if ((RESERVED_1 & odtPersist2) == RESERVED_1) names.add("RESERVED_1");
    	if ((QUERIED_EMF & odtPersist2) == QUERIED_EMF) names.add("QUERIED_EMF");
    	if ((STORED_AS_EMF & odtPersist2) == STORED_AS_EMF) names.add("STORED_AS_EMF");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdtPersist2 value.
    /// </summary>
    public static String toString(/*OdtPersist2*/int odtPersist2)
    {
    	switch (odtPersist2)
    	{
    		case NONE: return "None";
    		case EMF: return "Emf";
    		case RESERVED_1: return "Reserved1";
    		case QUERIED_EMF: return "QueriedEmf";
    		case STORED_AS_EMF: return "StoredAsEmf";
    		default: return toStringSet(odtPersist2);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdtPersist2 value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by OdtPersist2 name.
    /// </summary>
    public static /*OdtPersist2*/int fromName(String odtPersist2Name)
    {
    	if ("NONE".equals(odtPersist2Name)) return NONE;
    	if ("EMF".equals(odtPersist2Name)) return EMF;
    	if ("RESERVED_1".equals(odtPersist2Name)) return RESERVED_1;
    	if ("QUERIED_EMF".equals(odtPersist2Name)) return QUERIED_EMF;
    	if ("STORED_AS_EMF".equals(odtPersist2Name)) return STORED_AS_EMF;
    	throw new IllegalArgumentException("Unknown OdtPersist2 name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by OdtPersist2 names.
    /// </summary>
    public static /*OdtPersist2*/int fromNames(Set<String> odtPersist2Names)
    {
    	/*OdtPersist2*/int result = 0;
    	for (String name : odtPersist2Names)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of OdtPersist2 values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		EMF,
    		RESERVED_1,
    		QUERIED_EMF,
    		STORED_AS_EMF,
    	};
    }

    public static final int length = 5;
}

