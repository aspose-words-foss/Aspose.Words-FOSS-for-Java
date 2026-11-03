// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.IntToIntDictionary;


/// <summary>
/// Umbrella class for storing methods and constants for managing StyleIndex in styles.
///  StyleIndexor sti, seems to be the most important thing that unites and defines istd and names for Built-In styles.
/// DD: I will move methods here from elsewhere for better cohesion. Someday we will encapsulate all style-id related stuff here.
/// </summary>
/// <remarks>
/// Contains: 
/// 1. static methods that allow conversion of regular to built in styles
/// 
/// 2. Constants that map between sti's of built-in style to ISTD (index into the style sheet) for some built in styles.
/// Other built in styles have no standard istd values for them.
/// In Word 97 to 2007 there are 15 fixed istd styles. Last two entries are unused according to the SPEC.
/// </remarks>
/*static*/ class StyleIndex
{
	/* Simulation of static class by using private constructor */
	private StyleIndex()
	{}

    /// <summary>
    /// Returns <see cref="Style.StyleIdentifier"/> from style name for MS Word 2007 English localization.
    /// If style is not recognized as Built-in by its name, returns User style.   
    /// </summary>
    /// <remarks>This function may fail for any-non English localization.</remarks>
    static /*StyleIdentifier*/int builtInStyleNameEnglish(String name)
    {
        return StyleConvertUtil.xmlToStyleIdentifier(StyleConvertUtil.styleNameToXml(name));
    }

    /// <summary>
    /// Removes built-in values and make the style regular.
    /// </summary>
    static void convertToRegularStyle(Style style, Style oldStyle)
    {
        Debug.assertTrue(style.getStyles() != null);

        style.setStyleIdentifier(StyleIdentifier.USER, true);
        
        // remove style special values: reserved istd, sti, update caches.
        if (oldStyle != null)
            style.setIstd(oldStyle.getIstd(), true);
        else if (isIstdReserved(style.getIstd()))
            style.setIstd(style.getStyles().getNextFreeIstd(), true);

    }

    /// <summary>
    /// Converts the style into Built-In.
    /// Returns true if istd change happened.
    /// </summary>
    static boolean convertToBuiltInStyle(Style style, /*StyleIdentifier*/int sti, Style oldStyle, boolean updateCollection)
    {
        boolean result = false;

        style.setStyleIdentifier(sti, updateCollection);
        
        int defIstd = styleIdentifierToIstd(sti);
        if (defIstd != NIL) // now check if built-in style has reserved istd.
        {
            style.setIstd(defIstd, updateCollection);
            result = true;
        }
        else if (oldStyle != null) // else check if we need to override old style with new style.
        {
            style.setIstd(oldStyle.getIstd(), updateCollection);
            result = true;
        }

        return result;
    }

    /// <summary>
    /// Converts a style identifier into style index (istd). There is only a handful of styles
    /// with fixed istd values, for all others returns StyleIndex.Nil.
    /// </summary>
    static int styleIdentifierToIstd(/*StyleIdentifier*/int sti)
    {
        int result = G_ALL_RESERVED_ISTD.get((int)sti);
        if (IntToIntDictionary.isNullSubstitute(result))
            result = NIL;

        return result;
    }

    /// <summary>
    /// Returns true if given istd is reserved for a built-in style.
    /// </summary>
    static boolean isIstdReserved(int istd)
    {
        return G_ALL_RESERVED_ISTD.containsValue(istd);
    }

    /// <summary>
    /// Returns a value indicating whether the specified style identifier corresponds to a heading style.
    /// </summary>
    static boolean isHeadingIstd(int istd)
    {
        return ((istd >= HEADING_1) && (istd <= HEADING_9));
    }

    /// <summary>
    /// Special handling sometimes is required for default paragraph font style and table normal because they cannot be modified in Microsoft Word. 
    /// </summary>
    static boolean isNonModifiable(Style srcStyle)
    {
        return (srcStyle.getStyleIdentifier() == StyleIdentifier.DEFAULT_PARAGRAPH_FONT) ||
               (srcStyle.getStyleIdentifier() == StyleIdentifier.TABLE_NORMAL);
    }

    static final int NORMAL = 0;
    static final int HEADING_1 = 1;
    static final int HEADING_2 = 2;
    static final int HEADING_3 = 3;
    static final int HEADING_4 = 4;
    static final int HEADING_5 = 5;
    static final int HEADING_6 = 6;
    static final int HEADING_7 = 7;
    static final int HEADING_8 = 8;
    static final int HEADING_9 = 9;
    static final int DEFAULT_PARAGRAPH_FONT = 10;
    static final int TABLE_NORMAL = 11;
    static final int NO_LIST = 12;

    /// <summary>
    /// Nil style index.
    /// </summary>
    /// <remarks>
    /// Note, this value is different than Nil value used for styles in Word document.
    /// Actually this could be something like Int32.MaxValue but lets have some limitation for a while.
    /// </remarks>
    static final int NIL = 0x2FFF;

    /// <summary>
    /// Maximal style index.
    /// </summary>
    /// <remarks>
    /// Note, this value is different than Limit value used for styles in Word document.
    /// </remarks>
    static final int LIMIT = 0x2FFE;

    private static /*final*/ IntToIntDictionary G_ALL_RESERVED_ISTD = new IntToIntDictionary();
    
    static/* StyleIndex()*/
    {
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.NORMAL, NORMAL);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_1, HEADING_1);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_2, HEADING_2);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_3, HEADING_3);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_4, HEADING_4);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_5, HEADING_5);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_6, HEADING_6);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_7, HEADING_7);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_8, HEADING_8);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.HEADING_9, HEADING_9);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.DEFAULT_PARAGRAPH_FONT, DEFAULT_PARAGRAPH_FONT);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.TABLE_NORMAL, TABLE_NORMAL);
        G_ALL_RESERVED_ISTD.add((int)StyleIdentifier.NO_LIST, NO_LIST);
    }
}

