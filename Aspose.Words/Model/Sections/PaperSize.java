// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies paper size.
/// </summary>
public /*enum*/ final class PaperSize
{
    private PaperSize(){}
    
    /// <summary>
    /// 297 x 420 mm.
    /// </summary>
    /// <javaName>A3</javaName>
    public static final int A3 = 0;
    /// <summary>
    /// 210 x 297 mm.
    /// </summary>
    /// <javaName>A4</javaName>
    public static final int A4 = 1;
    /// <summary>
    /// 148 x 210 mm.
    /// </summary>
    /// <javaName>A5</javaName>
    public static final int A5 = 2;
    /// <summary>
    /// 250 x 353 mm.
    /// </summary>
    /// <javaName>B4</javaName>
    public static final int B4 = 3;
    /// <summary>
    /// 176 x 250 mm.
    /// </summary>
    /// <javaName>B5</javaName>
    public static final int B5 = 4;
    /// <summary>
    /// 7.25 x 10.5 inches.
    /// </summary>
    public static final int EXECUTIVE = 5;
    /// <summary>
    /// 8.5 x 13 inches.
    /// </summary>
    public static final int FOLIO = 6;
    /// <summary>
    /// 17 x 11 inches.
    /// </summary>
    public static final int LEDGER = 7;
    /// <summary>
    /// 8.5 x 14 inches.
    /// </summary>
    public static final int LEGAL = 8;
    /// <summary>
    /// 8.5 x 11 inches.
    /// </summary>
    public static final int LETTER = 9;
    /// <summary>
    /// 110 x 220 mm.
    /// </summary>
    public static final int ENVELOPE_DL = 10;
    /// <summary>
    /// 8.47 x 10.83 inches.
    /// </summary>
    public static final int QUARTO = 11;
    /// <summary>
    /// 8.5 x 5.5 inches.
    /// </summary>
    public static final int STATEMENT = 12;
    /// <summary>
    /// 11 x 17 inches.
    /// </summary>
    public static final int TABLOID = 13;
    /// <summary>
    /// 10 x 14 inches.
    /// </summary>
    public static final int PAPER_10_X_14 = 14;
    /// <summary>
    /// 11 x 17 inches.
    /// </summary>
    public static final int PAPER_11_X_17 = 15;
    /// <summary>
    /// 4.125 x 9.5 inches.
    /// </summary>
    public static final int NUMBER_10_ENVELOPE = 16;
    /// <summary>
    /// 257 x 364 mm.
    /// </summary>
    public static final int JIS_B_4 = 17;
    /// <summary>
    /// 182 x 257 mm.
    /// </summary>
    public static final int JIS_B_5 = 18;
    /// <summary>
    /// Custom paper size.
    /// </summary>
    public static final int CUSTOM = 19;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PaperSize value.
    /// </summary>
    public static String getName(/*PaperSize*/int paperSize)
    {
    	switch (paperSize)
    	{
    		case A3: return "A3";
    		case A4: return "A4";
    		case A5: return "A5";
    		case B4: return "B4";
    		case B5: return "B5";
    		case EXECUTIVE: return "EXECUTIVE";
    		case FOLIO: return "FOLIO";
    		case LEDGER: return "LEDGER";
    		case LEGAL: return "LEGAL";
    		case LETTER: return "LETTER";
    		case ENVELOPE_DL: return "ENVELOPE_DL";
    		case QUARTO: return "QUARTO";
    		case STATEMENT: return "STATEMENT";
    		case TABLOID: return "TABLOID";
    		case PAPER_10_X_14: return "PAPER_10_X_14";
    		case PAPER_11_X_17: return "PAPER_11_X_17";
    		case NUMBER_10_ENVELOPE: return "NUMBER_10_ENVELOPE";
    		case JIS_B_4: return "JIS_B_4";
    		case JIS_B_5: return "JIS_B_5";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown PaperSize value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PaperSize value.
    /// </summary>
    public static String toString(/*PaperSize*/int paperSize)
    {
    	switch (paperSize)
    	{
    		case A3: return "A3";
    		case A4: return "A4";
    		case A5: return "A5";
    		case B4: return "B4";
    		case B5: return "B5";
    		case EXECUTIVE: return "Executive";
    		case FOLIO: return "Folio";
    		case LEDGER: return "Ledger";
    		case LEGAL: return "Legal";
    		case LETTER: return "Letter";
    		case ENVELOPE_DL: return "EnvelopeDL";
    		case QUARTO: return "Quarto";
    		case STATEMENT: return "Statement";
    		case TABLOID: return "Tabloid";
    		case PAPER_10_X_14: return "Paper10x14";
    		case PAPER_11_X_17: return "Paper11x17";
    		case NUMBER_10_ENVELOPE: return "Number10Envelope";
    		case JIS_B_4: return "JisB4";
    		case JIS_B_5: return "JisB5";
    		case CUSTOM: return "Custom";
    		default: return "Unknown PaperSize value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PaperSize name.
    /// </summary>
    public static /*PaperSize*/int fromName(String paperSizeName)
    {
    	if ("A3".equals(paperSizeName)) return A3;
    	if ("A4".equals(paperSizeName)) return A4;
    	if ("A5".equals(paperSizeName)) return A5;
    	if ("B4".equals(paperSizeName)) return B4;
    	if ("B5".equals(paperSizeName)) return B5;
    	if ("EXECUTIVE".equals(paperSizeName)) return EXECUTIVE;
    	if ("FOLIO".equals(paperSizeName)) return FOLIO;
    	if ("LEDGER".equals(paperSizeName)) return LEDGER;
    	if ("LEGAL".equals(paperSizeName)) return LEGAL;
    	if ("LETTER".equals(paperSizeName)) return LETTER;
    	if ("ENVELOPE_DL".equals(paperSizeName)) return ENVELOPE_DL;
    	if ("QUARTO".equals(paperSizeName)) return QUARTO;
    	if ("STATEMENT".equals(paperSizeName)) return STATEMENT;
    	if ("TABLOID".equals(paperSizeName)) return TABLOID;
    	if ("PAPER_10_X_14".equals(paperSizeName)) return PAPER_10_X_14;
    	if ("PAPER_11_X_17".equals(paperSizeName)) return PAPER_11_X_17;
    	if ("NUMBER_10_ENVELOPE".equals(paperSizeName)) return NUMBER_10_ENVELOPE;
    	if ("JIS_B_4".equals(paperSizeName)) return JIS_B_4;
    	if ("JIS_B_5".equals(paperSizeName)) return JIS_B_5;
    	if ("CUSTOM".equals(paperSizeName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown PaperSize name.");
    }
    
    /// <summary>
    /// Returns array of PaperSize values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		A3,
    		A4,
    		A5,
    		B4,
    		B5,
    		EXECUTIVE,
    		FOLIO,
    		LEDGER,
    		LEGAL,
    		LETTER,
    		ENVELOPE_DL,
    		QUARTO,
    		STATEMENT,
    		TABLOID,
    		PAPER_10_X_14,
    		PAPER_11_X_17,
    		NUMBER_10_ENVELOPE,
    		JIS_B_4,
    		JIS_B_5,
    		CUSTOM,
    	};
    }

    public static final int length = 20;
}

