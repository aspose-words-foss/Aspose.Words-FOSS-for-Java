// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/08/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the level of a digital signature based on XML-DSig standard.
/// </summary>
public /*enum*/ final class XmlDsigLevel
{
    private XmlDsigLevel(){}
    
    /// <summary>
    /// Specifies XML-DSig signature level.
    /// </summary>
    /// <remarks>
    /// A simple digital signature that should not be trusted after its signing certificate expires.
    /// </remarks>
    public static final int XML_D_SIG = 0;
    /// <summary>
    /// Specifies XAdES-EPES signature level.
    /// </summary>
    /// <remarks>
    /// Adds information about the signing certificate to the XML-DSig signature.
    /// A malicious user cannot switch the signing certificate for another certificate with the same public/private key.
    /// </remarks>
    public static final int X_AD_ES_EPES = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer XmlDsigLevel value.
    /// </summary>
    public static String getName(/*XmlDsigLevel*/int xmlDsigLevel)
    {
    	switch (xmlDsigLevel)
    	{
    		case XML_D_SIG: return "XML_D_SIG";
    		case X_AD_ES_EPES: return "X_AD_ES_EPES";
    		default: return "Unknown XmlDsigLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer XmlDsigLevel value.
    /// </summary>
    public static String toString(/*XmlDsigLevel*/int xmlDsigLevel)
    {
    	switch (xmlDsigLevel)
    	{
    		case XML_D_SIG: return "XmlDSig";
    		case X_AD_ES_EPES: return "XAdEsEpes";
    		default: return "Unknown XmlDsigLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by XmlDsigLevel name.
    /// </summary>
    public static /*XmlDsigLevel*/int fromName(String xmlDsigLevelName)
    {
    	if ("XML_D_SIG".equals(xmlDsigLevelName)) return XML_D_SIG;
    	if ("X_AD_ES_EPES".equals(xmlDsigLevelName)) return X_AD_ES_EPES;
    	throw new IllegalArgumentException("Unknown XmlDsigLevel name.");
    }
    
    /// <summary>
    /// Returns array of XmlDsigLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		XML_D_SIG,
    		X_AD_ES_EPES,
    	};
    }

    public static final int length = 2;
}

