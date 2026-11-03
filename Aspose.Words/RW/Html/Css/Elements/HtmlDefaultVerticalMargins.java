// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/04/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MathUtil;

/// <summary>
/// Determines default (user agent) vertical margins of HTML elements.
/// </summary>
/// <remarks>
/// These margins cannot be declared directly in the user agent style sheet, because in this case we will not be able
/// to tell where a CSS margin property came from: the author style sheet or the user agent style sheet.
/// </remarks>
/*static*/ class HtmlDefaultVerticalMargins
{
	/* Simulation of static class by using private constructor */
	private HtmlDefaultVerticalMargins()
	{}

    /// <summary>
    /// Gets a value indicating whether an HTML element has non-zero default (user agent) vertical margins.
    /// </summary>
    static boolean areNonZero(IHtmlElementProvider element)
    {
        return !MathUtil.isZero(getMargins(element));
    }

    /// <summary>
    /// Returns default (user agent) vertical margins of HTML elements, in em.
    /// </summary>
    /// <param name="element">An HTML element.</param>
    /// <returns>
    /// Vertical margins of the HTML element as declared in the default (user agent) style sheet.
    /// </returns>
    static double getMargins(IHtmlElementProvider element)
    {
         switch (gStringSwitchMap.of(element.getElementName()))
         {
             case /*"h1"*/0:
                 return getDefaultH1VerticalMargins(element);
             case /*"h2"*/1:
                 return getDefaultHeadingVerticalMargins(1);
             case /*"h3"*/2:
                 return getDefaultHeadingVerticalMargins(2);
             case /*"h4"*/3:
                 return getDefaultHeadingVerticalMargins(3);
             case /*"h5"*/4:
                 return getDefaultHeadingVerticalMargins(4);
             case /*"h6"*/5:
                 return getDefaultHeadingVerticalMargins(5);
             case /*"dir"*/6:
             case /*"menu"*/7:
             case /*"ol"*/8:
             case /*"ul"*/9:
                 return getDefaultListVerticalMargins(element);
             case /*"blockquote"*/10:
             case /*"dl"*/11:
             case /*"figure"*/12:
             case /*"listing"*/13:
             case /*"plaintext"*/14:
             case /*"pre"*/15:
             case /*"xmp"*/16:
                 return 1.0;
             case /*"p"*/17:
                 // Implicit (anonymous) paragraphs do not have vertical margins.
                 return (element.isImplicit())
                     ? 0
                     : 1;
             case /*"hr"*/18:
                 return 0.5;
             default:
                 return 0.0;
         }
     }

    private static double getDefaultH1VerticalMargins(IHtmlElementProvider element)
    {
        int nestingLevel = 0;
        final int MAX_NESTING_LEVEL = 5;
        IElementProvider parent = element.getParentElement();
        while ((parent != null) && (nestingLevel < MAX_NESTING_LEVEL))
        {
            if (("article".equals(parent.getElementName())) ||
                ("aside".equals(parent.getElementName())) ||
                ("nav".equals(parent.getElementName())) ||
                ("section".equals(parent.getElementName())))
            {
                ++nestingLevel;
            }
            parent = parent.getParentElement();
        }

        return getDefaultHeadingVerticalMargins(nestingLevel);
    }

    private static double getDefaultHeadingVerticalMargins(int headingLevel)
    {
        double[] margins = new double[] { 0.67, 0.83, 1.0, 1.33, 1.67, 2.33 };
        return ((headingLevel >= 0) && (headingLevel < margins.length))
            ? margins[headingLevel]
            : 1.0;
    }

    private static double getDefaultListVerticalMargins(IHtmlElementProvider element)
    {
        boolean isNested = false;
        IElementProvider parent = element.getParentElement();
        while ((parent != null) && (!isNested))
        {
            if (("ol".equals(parent.getElementName())) ||
                ("ul".equals(parent.getElementName())) ||
                ("dir".equals(parent.getElementName())) ||
                ("menu".equals(parent.getElementName())))
            {
                isNested = true;
            }
            parent = parent.getParentElement();
        }
        return (isNested)
            ? 0
            : 1;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"dir",
		"menu",
		"ol",
		"ul",
		"blockquote",
		"dl",
		"figure",
		"listing",
		"plaintext",
		"pre",
		"xmp",
		"p",
		"hr"
	);

}

