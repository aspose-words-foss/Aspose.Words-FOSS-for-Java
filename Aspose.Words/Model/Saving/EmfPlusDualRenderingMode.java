// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2012 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how Aspose.Words should render EMF+ Dual metafiles.
/// </summary>
public /*enum*/ final class EmfPlusDualRenderingMode
{
    private EmfPlusDualRenderingMode(){}
    
    /// <summary>
    /// Aspose.Words tries to render EMF+ part of EMF+ Dual metafile. If some of the EMF+ records are not supported
    /// then Aspose.Words renders EMF part of EMF+ Dual metafile.
    /// </summary>
    public static final int EMF_PLUS_WITH_FALLBACK = 0;
    /// <summary>
    /// Aspose.Words renders EMF+ part of EMF+ Dual metafile.
    /// </summary>
    public static final int EMF_PLUS = 1;
    /// <summary>
    /// Aspose.Words renders EMF part of EMF+ Dual metafile.
    /// </summary>
    public static final int EMF = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EmfPlusDualRenderingMode value.
    /// </summary>
    public static String getName(/*EmfPlusDualRenderingMode*/int emfPlusDualRenderingMode)
    {
    	switch (emfPlusDualRenderingMode)
    	{
    		case EMF_PLUS_WITH_FALLBACK: return "EMF_PLUS_WITH_FALLBACK";
    		case EMF_PLUS: return "EMF_PLUS";
    		case EMF: return "EMF";
    		default: return "Unknown EmfPlusDualRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EmfPlusDualRenderingMode value.
    /// </summary>
    public static String toString(/*EmfPlusDualRenderingMode*/int emfPlusDualRenderingMode)
    {
    	switch (emfPlusDualRenderingMode)
    	{
    		case EMF_PLUS_WITH_FALLBACK: return "EmfPlusWithFallback";
    		case EMF_PLUS: return "EmfPlus";
    		case EMF: return "Emf";
    		default: return "Unknown EmfPlusDualRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EmfPlusDualRenderingMode name.
    /// </summary>
    public static /*EmfPlusDualRenderingMode*/int fromName(String emfPlusDualRenderingModeName)
    {
    	if ("EMF_PLUS_WITH_FALLBACK".equals(emfPlusDualRenderingModeName)) return EMF_PLUS_WITH_FALLBACK;
    	if ("EMF_PLUS".equals(emfPlusDualRenderingModeName)) return EMF_PLUS;
    	if ("EMF".equals(emfPlusDualRenderingModeName)) return EMF;
    	throw new IllegalArgumentException("Unknown EmfPlusDualRenderingMode name.");
    }
    
    /// <summary>
    /// Returns array of EmfPlusDualRenderingMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EMF_PLUS_WITH_FALLBACK,
    		EMF_PLUS,
    		EMF,
    	};
    }

    public static final int length = 3;
}

