// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ArgumentUtil;
import com.aspose.Debug;


/// <summary>
/// Extracted from DOCX styles reader.
/// </summary>
/*static*/ class NrxStyleUtil
{
	/* Simulation of static class by using private constructor */
	private NrxStyleUtil()
	{}

    /// <summary>
    /// Tries to recognize a style name read from a DOCX, WordML or RTF file
    /// and sets the name, style identifier, istd and next istd correctly for the model.
    /// </summary>
    /// <param name="name">Locale independent "standard" style name as read from a file.</param>
    /// <param name="styles">Collection of document styles (used to get next free istd value).</param>
    /// <param name="isForceCustom">True to force the style to be a custom style.</param>
    /// <param name="tryLowerCase">True to try style name in lower case when detect built-in styles.</param>
    /// <param name="isReplaceExistingBuiltInStyle">True to replace existing BuiltIn style. Otherwise, makes it custom style.</param>
    /// <param name="style">The style to update.</param>
    static void setStyleNameAndIdentifiers(
        String name,
        StyleCollection styles,
        boolean isForceCustom,
        boolean tryLowerCase,
        boolean isReplaceExistingBuiltInStyle,
        Style style)
    {
        ArgumentUtil.checkHasChars(name, "name");
        ArgumentUtil.checkNotNull(styles, "styles");
        ArgumentUtil.checkNotNull(style, "style");

        // Try to recognize a built-in style by the primary name.
        /*StyleIdentifier*/int styleIdentifier = StyleIdentifier.USER;

        if (!isForceCustom)
        {
            styleIdentifier = StyleConvertUtil.xmlToStyleIdentifier(name);

            if(tryLowerCase && (styleIdentifier == StyleIdentifier.USER))
                styleIdentifier = StyleConvertUtil.xmlToStyleIdentifier(name.toLowerCase());
        }

        // Some built-in style names on file are different from what we use in the model, translate such names.
        if (styleIdentifier != StyleIdentifier.USER)
            name = StyleConvertUtil.xmlToStyleName(name);

        // In some ugly documents (such as TestDefect1478 there are several styles with the same name.
        // If first is recognized as a built-in style, we have to recognize others as custom styles.
        if (styles.contains(styleIdentifier))
        {
            // WORDSNET-22587 Actually, Word takes only last occured built-in style. But for the moment this done
            // only for DOCX and WML and all other formats should be checked additionally because of various
            // specifics in different formats. For example, see RK comments in call of this method for ODT.
            if (isReplaceExistingBuiltInStyle)
            {
                Style existingStyle = styles.getBySti(styleIdentifier, false);
                Debug.assertTrue(existingStyle != null);

                // WORDSNET-26324 Handle duplicate name in styles with different types.
                // There are could be many combinations, handle this particular case only.
                if ((msString.equals(existingStyle.getName(), name)) &&
                    (existingStyle.getType() == StyleType.PARAGRAPH) &&
                    (style.getType() == StyleType.CHARACTER))
                {
                    name = msString.plusEqOperator(name, " Char");
                    styleIdentifier = StyleIdentifier.USER;
                }
                else
                {
                    styles.removeCore(existingStyle);
                }
            }
            else
            {
                styleIdentifier = StyleIdentifier.USER;
            }
        }

        int istd = StyleIndex.styleIdentifierToIstd(styleIdentifier);
        if (istd == StyleIndex.NIL)
            istd = styles.getNextFreeIstd();

        style.setStyleIdentifier(styleIdentifier);
        style.setNameCore(name);
        style.setIstd(istd);
        // We also have to set next istd because by default it should point to the style itself.
        style.setNextIstd(istd);
    }
}

