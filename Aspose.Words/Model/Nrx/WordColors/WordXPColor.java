// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Useful constants for dealing with 32 bit colors as they stored in DOC files.
/// They are different from .NET Color.ToArgb() in order and in transparency/opacity.
/// The bytes are as follows (from high to low) T, B, G, R
/// </summary>
/*static*/ class WordXPColor
{
	/* Simulation of static class by using private constructor */
	private WordXPColor()
	{}

    /// <summary>
    /// This is auto color as encountered in DOC files.
    /// It can be translated as "full transparency black".
    /// </summary>
    static final int AUTO        = /*unchecked*/((int)(int) 0xff000000L);
    static final int BLACK       = 0x00000000;
    static final int BLUE        = 0x00ff0000;
    static final int CYAN        = 0x00ffff00;
    static final int GREEN       = 0x0000ff00;
    static final int MAGENTA     = 0x00ff00ff;
    static final int RED         = 0x000000ff;
    static final int YELLOW      = 0x0000ffff;
    static final int WHITE       = 0x00ffffff;
    static final int DARK_BLUE    = 0x00800000;
    static final int DARK_CYAN    = 0x00808000;
    static final int DARK_GREEN   = 0x00008000;
    static final int DARK_MAGENTA = 0x00800080;
    static final int DARK_RED     = 0x00000080;
    static final int DARK_YELLOW  = 0x00008080;
    static final int DARK_GRAY    = 0x00808080;
    static final int LIGHT_GRAY   = 0x00C0C0C0;
}

