// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.Debug;
import com.aspose.StringUtil;


/// <summary>
/// Base simple CSS selector class.
/// http://www.w3.org/TR/css3-selectors/
/// </summary>
abstract class CssSelector
{

    /// <summary>
    /// Creates a new type selector for the specified element name.
    /// </summary>
    static CssTypeSelector element(String elementName)
    {
        return new CssTypeSelector(CssNamespace.ANY, elementName);
    }

    /// <summary>
    /// Creates a new class selector for the specified class name.
    /// </summary>
    static CssClassSelector classSelector(String className)
    {
        return new CssClassSelector(className);
    }

    /// <summary>
    /// Creates a new ::before pseudo-element selector on an element with the specified name.
    /// </summary>
    static CssCompoundSelector before(String elementName)
    {
        return new CssCompoundSelector(element(elementName),
            new CssPseudoElementSelector(HtmlElementPart.BEFORE, "before"));
    }

    /// <summary>
    /// Identifies what part of an HTML element this selector selects. It is either the element itself or any of its
    /// pseudo-elements.
    /// </summary>
    /*virtual*/ /*HtmlElementPart*/int getSelectedPart()
    {
        // Most selectors work not with pseudo-elements but with whole HTML elements.
        return HtmlElementPart.ELEMENT;
    }

    /// <summary>
    /// Gets a style class name for the CSS selector.
    /// </summary>
    String getPreferableStyleName()
    {
        String styleName = makePreferableStyleName();
        Debug.assertTrue(StringUtil.hasChars(styleName));
        return styleName;
    }

    /// <summary>
    /// CSS Specificity of the selector.
    /// http://www.w3.org/TR/selectors/#specificity
    /// </summary>
    abstract CssSelectorSpecificity getSpecificity();

    /// <summary>
    /// Gets the CSS declaration of the selector.
    /// </summary>
    abstract String toCss();

    /// <summary>
    /// Indicates whether the value of the specified attribute should be compared by selectors using 
    /// a case-insensitive method.
    /// </summary>
    /// <param name="attributeName">The name of the attribute.</param>
    /// <returns>
    /// <c>true</c>, if the value of the attribute should be treated by selectors in a case-insensitive way.
    /// <c>false</c> otherwise.
    /// </returns>
    /// <remarks>
    /// Although the HTML 5 specification requires to treat almost all attribute values as case-sensitive
    /// for the purpose of CSS selector matching (see http://www.w3.org/TR/html5/selectors.html#case-sensitivity),
    /// there is a group of attributes whose values are treated as case-insensitive by all modern browsers
    /// for historical reasons. See http://rakaz.nl/2006/10/css-selector-bugs-case-sensitivity.html for detais.
    /// </remarks>
    static boolean attributeValueIsCaseInsensitive(String attributeName)
    {
        Debug.assertTrue(StringUtil.hasChars(attributeName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(attributeName));

        return G_NAMES_OF_ATTRIBUTES_WITH_CASE_INSENSITIVE_VALUES.containsKey(attributeName);
    }


    /// <summary>
    /// Creates an instance of <see cref="CssSelectorMatcher"/> to match this selector against HTML elements 
    /// in the specified mode.
    /// </summary>
    abstract CssSelectorMatcher createMatcher(/*CssDocumentMode*/int documentMode);

    /// <summary>
    /// Override in derived classes and return a style class name for the CSS selector.
    /// </summary>
    /// <returns>The style class name for the CSS selector.</returns>
    protected abstract String makePreferableStyleName();

    /// <summary>
    /// The list of the attributes whose values should be compared by selectors in a case-insensitive way.
    /// </summary>
    private static /*final*/ HashMap<String, Boolean> G_NAMES_OF_ATTRIBUTES_WITH_CASE_INSENSITIVE_VALUES =
        new HashMap<String, Boolean>();
    static/* CssSelector()*/
    {
        //>>>>>>>> #region  Names of attributes whose values are case-insensitive

        // The list of attributes is taken from here: http://rakaz.nl/2006/10/css-selector-bugs-case-sensitivity.html
        // This list is also used at least by Konqueror (https://bugs.kde.org/show_bug.cgi?id=135505)
        // and by Mozilla (https://bugzilla.mozilla.org/show_bug.cgi?id=357614)
        String[] names = new String[]
        {
            "accept",
            "accept-charset",
            "align",
            "alink",
            "axis",
            "bgcolor",
            "charset",
            "checked",
            "clear",
            "codetype",
            "color",
            "compact",
            "declare",
            "defer",
            "dir",
            "disabled",
            "enctype",
            "face",
            "frame",
            "hreflang",
            "http-equiv",
            "lang",
            "language",
            "link",
            "media",
            "method",
            "multiple",
            "nohref",
            "noresize",
            "noshade",
            "nowrap",
            "readonly",
            "rel",
            "rev",
            "rules",
            "scope",
            "scrolling",
            "selected",
            "shape",
            "target",
            "text",
            "type",
            "valign",
            "valuetype",
            "vlink"
        };

        //<<<<<<<< #endregion 

        // Attribute names are placed into a hash table for performance reasons.
        G_NAMES_OF_ATTRIBUTES_WITH_CASE_INSENSITIVE_VALUES = new HashMap<String, Boolean>();
        for (String name : names)
        {
            msDictionary.add(G_NAMES_OF_ATTRIBUTES_WITH_CASE_INSENSITIVE_VALUES, name, false);
        }
    }
}

