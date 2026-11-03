// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2015 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Helps to propagate alignment styles from 'sup' or 'sub' tags to nested inline elements.
/// </summary>
class SupSubScriptResolver
{
    /// <summary>
    /// Ctor.
    /// </summary>
    SupSubScriptResolver()
    {
        mInfoStack = new msStack<CssVerticalAlign>();
    }

    CssVerticalAlign getCurrentElementInfo() { return mInfoStack.peek(); }

    /// <summary>
    /// Adds information about styles to stacks.
    /// </summary> 
    void push(HtmlElementInfo elementInfo)
    {
        CssPropertyValue verticalAlignValue = null;

        // The style propagation to the nested element is allowed if element is not located in block-level element.
        if (!elementInfo.IsBlockLevelElement)
        {
            CssComputedDeclaration verticalAlignDeclaration = ms.as(elementInfo.getDeclarations().get("vertical-align"), CssComputedDeclaration.class);
            if (verticalAlignDeclaration != null)
            {
                verticalAlignValue = verticalAlignDeclaration.getValue();
            }

            if ((verticalAlignValue == null) && (mInfoStack.getCount() > 0))
            {
                CssVerticalAlign prevInfo = mInfoStack.peek();
                verticalAlignValue = prevInfo.getVerticalAlignValue();
            }
        }

        mInfoStack.push(new CssVerticalAlign(verticalAlignValue));
    }

    /// <summary>
    /// Removes a top info.
    /// </summary>
    void pop()
    {
        mInfoStack.pop();
    }

    /// <summary>
    /// Stack stores information about 'sup' or 'sub' elements and their nested elements and
    /// style which is needed to propagate to nested inline elements.
    /// </summary>
    private /*final*/ msStack<CssVerticalAlign> mInfoStack;
}
