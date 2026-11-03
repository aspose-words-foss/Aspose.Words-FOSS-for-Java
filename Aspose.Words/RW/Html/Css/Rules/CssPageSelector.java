// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/06/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.CssEscape;


/// <summary>
/// A list of page names of a @page rule.
/// </summary>
/// <remarks>
/// When working with @page rules, we support only selectors that consist of a single page name only and we don't support
/// page pseudo-classes. As a result, there's no need to cascade values of @page rules and to calculate specificity of their
/// selectors.
/// </remarks>
class CssPageSelector
{
    /// <summary>
    /// Constructor. Initializes a new instance of the class.
    /// </summary>
    /// <param name="pageNames">
    /// Names of pages that the @page rule matches. Null if the rule matches every page. Empty if the rule matches no pages.
    /// </param>
    CssPageSelector(String[] pageNames)
    {
        mPageNames = pageNames;
    }

    boolean matches(String pageName)
    {
        // According to the specification, the reserved keyword "auto" is a valid selector but it doesn't match any page.
        // This is because "auto" is the default value of the "page" CSS property.
        if ("auto".equals(StringUtil.asciiLowerCase(pageName)))
        {
            return false;
        }

        // Check if this selector matches every page.
        if (mPageNames == null)
        {
            return true;
        }

        for (String acceptedPageName : mPageNames)
        {
            if (msString.equals(acceptedPageName, pageName))
            {
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Indicates whether the CSS page selector contains any page names.
    /// </summary>
    /// <returns></returns>
    boolean hasPageNames()
    {
        return (mPageNames != null) && (mPageNames.length > 0);
    }

    String toCss()
    {
        // An empty selector. Matches any page.
        if (mPageNames == null)
        {
            return "";
        }

        // According to the specification, the reserved keyword "auto" is a valid selector but it doesn't match any
        // page. We use it as a placeholder for selectors that we recognize but don't support.
        if (mPageNames.length == 0)
        {
            return "auto";
        }

        // List of page names (CSS identifiers).
        StringBuilder list = new StringBuilder();
        for (String pageName : mPageNames)
        {
            if (list.length() > 0)
            {
                msStringBuilder.append(list, ", ");
            }
            msStringBuilder.append(list, CssEscape.escapeIdentifier(pageName));
        }
        return list.toString();
    }

    /// <summary>
    /// Names of pages that this selector matches. null - matches any page. Empty - matches nothing.
    /// </summary>
    private /*final*/ String[] mPageNames;
}

