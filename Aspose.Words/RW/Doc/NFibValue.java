// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2008 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The value for the Fib.nFib field.
/// </summary>
/*enum*/ final class NFibValue
{
    private NFibValue(){}
    
    public static final int WORD_60_FOR_WIN = 101;
    public static final int WORD_60_FOR_MAC = 103;

    /// <summary>
    /// Maybe Word95 is 105?
    /// </summary>
    public static final int WORD_95 = 104;

    /// <summary>
    /// Although Word97 is nFib = 193, the DOC spec sometimes refers to this value as Word 97.
    /// </summary>
    public static final int VALUE_106 = 106;

    /// <summary>
    /// This value occurred sometimes. Meaning is unknown.
    /// </summary>
    public static final int VALUE_191 = 191;

    /// <summary>
    /// A special empty document is installed with Word 97, Word 2000, Word 2002, and
    /// Office Word 2003 to allow "Create New Word Document" from the operating system.
    /// This document has an nFib of 0x00C0.
    /// </summary>
    public static final int WORD_97_EMPTY = 192;

    /// <summary>
    /// 0x00C1.
    /// </summary>
    public static final int WORD_97 = 193;

    /// <summary>
    /// The BiDi build of Word 97 differentiates its documents by saving 0x00C2 as the nFib.
    /// </summary>
    public static final int WORD_97_BI_DI = 194;

    /// <summary>
    /// 0x00D9
    /// </summary>
    public static final int WORD_2000 = 217;

    /// <summary>
    /// 0x0101
    /// </summary>
    public static final int WORD_2002 = 257;

    /// <summary>
    /// 0x010C
    /// </summary>
    public static final int WORD_2003 = 268;

    /// <summary>
    /// 0x0112
    /// </summary>
    public static final int WORD_2007 = 274;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NFibValue value.
    /// </summary>
    public static String getName(/*NFibValue*/int nFibValue)
    {
    	switch (nFibValue)
    	{
    		case WORD_60_FOR_WIN: return "WORD_60_FOR_WIN";
    		case WORD_60_FOR_MAC: return "WORD_60_FOR_MAC";
    		case WORD_95: return "WORD_95";
    		case VALUE_106: return "VALUE_106";
    		case VALUE_191: return "VALUE_191";
    		case WORD_97_EMPTY: return "WORD_97_EMPTY";
    		case WORD_97: return "WORD_97";
    		case WORD_97_BI_DI: return "WORD_97_BI_DI";
    		case WORD_2000: return "WORD_2000";
    		case WORD_2002: return "WORD_2002";
    		case WORD_2003: return "WORD_2003";
    		case WORD_2007: return "WORD_2007";
    		default: return "Unknown NFibValue value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NFibValue value.
    /// </summary>
    public static String toString(/*NFibValue*/int nFibValue)
    {
    	switch (nFibValue)
    	{
    		case WORD_60_FOR_WIN: return "Word60ForWin";
    		case WORD_60_FOR_MAC: return "Word60ForMac";
    		case WORD_95: return "Word95";
    		case VALUE_106: return "Value106";
    		case VALUE_191: return "Value191";
    		case WORD_97_EMPTY: return "Word97Empty";
    		case WORD_97: return "Word97";
    		case WORD_97_BI_DI: return "Word97BiDi";
    		case WORD_2000: return "Word2000";
    		case WORD_2002: return "Word2002";
    		case WORD_2003: return "Word2003";
    		case WORD_2007: return "Word2007";
    		default: return "Unknown NFibValue value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NFibValue name.
    /// </summary>
    public static /*NFibValue*/int fromName(String nFibValueName)
    {
    	if ("WORD_60_FOR_WIN".equals(nFibValueName)) return WORD_60_FOR_WIN;
    	if ("WORD_60_FOR_MAC".equals(nFibValueName)) return WORD_60_FOR_MAC;
    	if ("WORD_95".equals(nFibValueName)) return WORD_95;
    	if ("VALUE_106".equals(nFibValueName)) return VALUE_106;
    	if ("VALUE_191".equals(nFibValueName)) return VALUE_191;
    	if ("WORD_97_EMPTY".equals(nFibValueName)) return WORD_97_EMPTY;
    	if ("WORD_97".equals(nFibValueName)) return WORD_97;
    	if ("WORD_97_BI_DI".equals(nFibValueName)) return WORD_97_BI_DI;
    	if ("WORD_2000".equals(nFibValueName)) return WORD_2000;
    	if ("WORD_2002".equals(nFibValueName)) return WORD_2002;
    	if ("WORD_2003".equals(nFibValueName)) return WORD_2003;
    	if ("WORD_2007".equals(nFibValueName)) return WORD_2007;
    	throw new IllegalArgumentException("Unknown NFibValue name.");
    }
    
    /// <summary>
    /// Returns array of NFibValue values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		WORD_60_FOR_WIN,
    		WORD_60_FOR_MAC,
    		WORD_95,
    		VALUE_106,
    		VALUE_191,
    		WORD_97_EMPTY,
    		WORD_97,
    		WORD_97_BI_DI,
    		WORD_2000,
    		WORD_2002,
    		WORD_2003,
    		WORD_2007,
    	};
    }

    public static final int length = 12;
}

