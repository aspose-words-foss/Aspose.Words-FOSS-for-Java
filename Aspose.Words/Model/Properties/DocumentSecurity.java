// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Used as a value for the <see cref="BuiltInDocumentProperties.Security"/> property.
/// Specifies the security level of a document as a numeric value.
/// </summary>
public /*enum*/ final class DocumentSecurity
{
    private DocumentSecurity(){}
    
    /// <summary>
    /// There are no security states specified by the property.
    /// </summary>
    public static final int NONE = 0x0000;
    /// <summary>
    /// The document is password protected. (Note has never been seen in a document so far).
    /// </summary>
    public static final int PASSWORD_PROTECTED = 0x0001;
    /// <summary>
    /// The document to be opened read-only if possible, but the setting can be overridden.
    /// </summary>
    public static final int READ_ONLY_RECOMMENDED = 0x0002;
    /// <summary>
    /// The document to always be opened read-only.
    /// </summary>
    public static final int READ_ONLY_ENFORCED = 0x0004;
    /// <summary>
    /// The document to always be opened read-only except for annotations.
    /// </summary>
    public static final int READ_ONLY_EXCEPT_ANNOTATIONS = 0x0008;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocumentSecurity value.
    /// </summary>
    public static String getName(/*DocumentSecurity*/int documentSecurity)
    {
    	switch (documentSecurity)
    	{
    		case NONE: return "NONE";
    		case PASSWORD_PROTECTED: return "PASSWORD_PROTECTED";
    		case READ_ONLY_RECOMMENDED: return "READ_ONLY_RECOMMENDED";
    		case READ_ONLY_ENFORCED: return "READ_ONLY_ENFORCED";
    		case READ_ONLY_EXCEPT_ANNOTATIONS: return "READ_ONLY_EXCEPT_ANNOTATIONS";
    		default: return "Unknown DocumentSecurity value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer DocumentSecurity value.
    /// </summary>
    public static Set<String> getNames(/*DocumentSecurity*/int documentSecurity)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & documentSecurity) == NONE) names.add("NONE");
    	if ((PASSWORD_PROTECTED & documentSecurity) == PASSWORD_PROTECTED) names.add("PASSWORD_PROTECTED");
    	if ((READ_ONLY_RECOMMENDED & documentSecurity) == READ_ONLY_RECOMMENDED) names.add("READ_ONLY_RECOMMENDED");
    	if ((READ_ONLY_ENFORCED & documentSecurity) == READ_ONLY_ENFORCED) names.add("READ_ONLY_ENFORCED");
    	if ((READ_ONLY_EXCEPT_ANNOTATIONS & documentSecurity) == READ_ONLY_EXCEPT_ANNOTATIONS) names.add("READ_ONLY_EXCEPT_ANNOTATIONS");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentSecurity value.
    /// </summary>
    public static String toString(/*DocumentSecurity*/int documentSecurity)
    {
    	switch (documentSecurity)
    	{
    		case NONE: return "None";
    		case PASSWORD_PROTECTED: return "PasswordProtected";
    		case READ_ONLY_RECOMMENDED: return "ReadOnlyRecommended";
    		case READ_ONLY_ENFORCED: return "ReadOnlyEnforced";
    		case READ_ONLY_EXCEPT_ANNOTATIONS: return "ReadOnlyExceptAnnotations";
    		default: return toStringSet(documentSecurity);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocumentSecurity value.
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
    /// Returns integer representation by DocumentSecurity name.
    /// </summary>
    public static /*DocumentSecurity*/int fromName(String documentSecurityName)
    {
    	if ("NONE".equals(documentSecurityName)) return NONE;
    	if ("PASSWORD_PROTECTED".equals(documentSecurityName)) return PASSWORD_PROTECTED;
    	if ("READ_ONLY_RECOMMENDED".equals(documentSecurityName)) return READ_ONLY_RECOMMENDED;
    	if ("READ_ONLY_ENFORCED".equals(documentSecurityName)) return READ_ONLY_ENFORCED;
    	if ("READ_ONLY_EXCEPT_ANNOTATIONS".equals(documentSecurityName)) return READ_ONLY_EXCEPT_ANNOTATIONS;
    	throw new IllegalArgumentException("Unknown DocumentSecurity name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by DocumentSecurity names.
    /// </summary>
    public static /*DocumentSecurity*/int fromNames(Set<String> documentSecurityNames)
    {
    	/*DocumentSecurity*/int result = 0;
    	for (String name : documentSecurityNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of DocumentSecurity values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PASSWORD_PROTECTED,
    		READ_ONLY_RECOMMENDED,
    		READ_ONLY_ENFORCED,
    		READ_ONLY_EXCEPT_ANNOTATIONS,
    	};
    }

    public static final int length = 5;
}

