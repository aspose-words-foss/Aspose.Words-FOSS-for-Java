// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/09/2014 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.Debug;


/// <summary>
/// Combines functionality for work with background.
/// Stores and calculates current values of background colors.
/// Applies current background color for paragraph, font, etc.  
/// 
/// Helps to emulate browser rendering mechanism.
/// In some cases parent element background color is visible through child elements because child 
/// elements are transparent and have their background-color as 'transparent' by default.
/// 
/// Potential problems:
/// 1) Since we avoid duplicating container and child colors, 
///   explicit 'background-color' declaration will be omitted for childs having the same color as the container.
/// 2) The element position in the document is not considered. Position may be absolute.
/// </summary>
class BackgroundResolver
{
    BackgroundResolver()
    {
        mBackgroundStack = new msStack<ElementBackgroundInfo>();
    }

    /// <summary>
    /// Adds background info of element to the stack.
    /// </summary>
    void pushBackground(HtmlElementInfo elementInfo)
    {
        Debug.assertTrue(elementInfo != null);

        if (elementInfo == null)
            return;

        CssComputedDeclaration backgroundDeclaration = ms.as(elementInfo.getDeclarations().get("background-color"), CssComputedDeclaration.class);
        if ((backgroundDeclaration != null) && backgroundDeclaration.getValue().equals(CssValue.Transparent))
        {
            // Ignore transparent backgrounds so lower-level backgrounds will be visible through them.
            backgroundDeclaration = null;
        }

        // Background color already processed for these elements.
        boolean isTopLevelContainer = ("html".equals(elementInfo.Element.getElementName())) ||
                                   ("body".equals(elementInfo.Element.getElementName()));

        CssComputedDeclaration blockBackgroundColor = getCurrentElementBackground().getBlockBackgroundDeclaration();
        CssComputedDeclaration inlineBackgroundColor = getCurrentElementBackground().getInlineBackgroundDeclaration();


        // Defines current background colors for block or inline elements.
        if (!isTopLevelContainer && (backgroundDeclaration != null))
        {
            if (elementInfo.IsBlockLevelElement)
            {
                // Addition background for block elements resets background for inline elements.
                blockBackgroundColor = backgroundDeclaration;
                inlineBackgroundColor = null;
            }
            else
            {
                inlineBackgroundColor = backgroundDeclaration;
            }
        }

        ElementBackgroundInfo item = new ElementBackgroundInfo(
                                            blockBackgroundColor,
                                            inlineBackgroundColor,
                                            isTopLevelContainer,
                                            getCurrentElementBackground(),
                                            isContainer(elementInfo));

        mBackgroundStack.push(item);
    }

    /// <summary>
    /// Removes a top background info.
    /// </summary>
    void popBackground()
    {
        if (mBackgroundStack.getCount() == 0)
            return;

        mBackgroundStack.pop();
    }

    /// <summary>
    /// Determines element which may contain other elements.
    /// Container is a html element which has counterpart in AW model.
    /// </summary>
    private static boolean isContainer(HtmlElementInfo elementInfo)
    {
        return ("body".equals(elementInfo.Element.getElementName())) || 
               (elementInfo.DisplayType == CssDisplayType.TABLE) ||
               (elementInfo.DisplayType == CssDisplayType.TABLE_CELL);
    }

    ElementBackgroundInfo getCurrentElementBackground()
    {
        if ((mBackgroundStack.getCount() == 0) || mBackgroundStack.peek().isTopLevelContainer())
            return ElementBackgroundInfo.getEmptyTopLevelContainer();
        return mBackgroundStack.peek();
    }

    private /*final*/ msStack<ElementBackgroundInfo> mBackgroundStack;
}

