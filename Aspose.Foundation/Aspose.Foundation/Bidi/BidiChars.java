// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Characters with a special meaning for the Unicode bidirectional algorithm.
/// </summary>
public /*static*/ class BidiChars
{
	/* Simulation of static class by using private constructor */
	private BidiChars()
	{}

    /// <summary>
    /// LEFT-TO-RIGHT EMBEDDING.
    /// </summary>
    public static final char LRE = '‪';

    /// <summary>
    /// RIGHT-TO-LEFT EMBEDDING.
    /// </summary>
    public static final char RLE = '‫';

    /// <summary>
    /// POP DIRECTIONAL FORMATTING, ends the last embedding or override.
    /// </summary>
    public static final char PDF = '‬';

    /// <summary>
    /// LEFT-TO-RIGHT OVERRIDE.
    /// </summary>
    public static final char LRO = '‭';

    /// <summary>
    /// RIGHT-TO-LEFT OVERRIDE.
    /// </summary>
    public static final char RLO = '‮';

    /// <summary>
    /// A noncharacter used where "no character" is needed, for example past the end of text.
    /// </summary>
    public static final char NOT_A_CHAR = '￿';
}

