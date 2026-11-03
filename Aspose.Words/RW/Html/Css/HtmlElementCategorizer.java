// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.Collection;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Helps to determine category of a HTML element.
/// </summary>
/// <remarks>
/// In general, HTML elements can be divided into two categories: block level and inline elements. 
/// </remarks>
class HtmlElementCategorizer
{
    HtmlElementCategorizer(CssClassFactory classFactory)
    {
        Debug.assertTrue(classFactory != null);
        mClassFactory = classFactory;
    }

    /// <summary>
    /// Determines whether the element is a block-level element according to its CSS declarations.
    /// </summary>
    /// <param name="element">HTML element.</param>
    /// <param name="elementDeclarations">HTML element actual declarations. The actual declarations are the used declarations after any such adjustments have been made.</param>
    boolean isBlockLevelElement(IHtmlElementProvider element, CssDeclarationCollection elementDeclarations)
    {
        Debug.assertTrue(element != null);
        Debug.assertTrue(elementDeclarations != null);

        CssDeclaration displayDeclaration = elementDeclarations.get("display");
        boolean isBlock;
        if ((displayDeclaration == null) || displayDeclaration.getValue().equals(CssValue.None))
            isBlock = isBlockElementByDefault(element.getElementName());
        else
            isBlock = !(displayDeclaration.getValue().equals(CssValue.Inline) ||
                        displayDeclaration.getValue().equals(CssValue.InlineBlock));
        return isBlock;
    }

    /*CssDisplayType*/int getDisplayType(CssDeclarationCollection elementDeclarations)
    {
        Debug.assertTrue(elementDeclarations != null);

        CssDeclaration displayDeclaration = elementDeclarations.get("display");

        if (displayDeclaration == null)
            return CssDisplayType.INLINE;

        if (M_CSS_VALUE_TO_DISPLAY_TYPE_MAP.containsKey(displayDeclaration.getValue().getFirstValue()))
            return  M_CSS_VALUE_TO_DISPLAY_TYPE_MAP.get(displayDeclaration.getValue().getFirstValue());

        return CssDisplayType.INLINE;
    }

    /// <summary>
    /// Determines whether the element is a block-level element.
    /// </summary>
    /// <param name="element">HTML element.</param>
    /// <param name="documentStyleRules">CSS style rules of a HTML document.</param>
    boolean isBlockLevelElement(
        IHtmlElementProvider element, 
        Collection<CssStyleRule> documentStyleRules)
    {
        Debug.assertTrue(element != null);
        Debug.assertTrue(documentStyleRules != null);

        msStack<IElementProvider> elementTreeStack = new msStack<IElementProvider>();
        IElementProvider htmlElement = element;
        while (htmlElement != null)
        {
            elementTreeStack.push(htmlElement);
            htmlElement = htmlElement.getParentElement();
        }

        CssResolver resolver = mClassFactory.createCssResolver(documentStyleRules);
        while (elementTreeStack.getCount() != 0)
        {
            resolver.pushElement(elementTreeStack.pop());
        }

        return isBlockLevelElement(element, resolver.getDeclarations());
    }

    /// <summary>
    /// Checks whether this element is represented as block-level element.
    /// </summary>
    static boolean isBlockElementByDefault(String elementName)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"address"*/0:
            case /*"article"*/1:
            case /*"aside"*/2:
            case /*"audio"*/3:
            case /*"blockquote"*/4:
            case /*"body"*/5:
            case /*"canvas"*/6:
            case /*"div"*/7:
            case /*"dl"*/8:
            case /*"dd"*/9:
            case /*"fieldset"*/10:
            case /*"figcaption"*/11:
            case /*"figure"*/12:
            case /*"footer"*/13:
            case /*"form"*/14:
            case /*"h1"*/15:
            case /*"h2"*/16:
            case /*"h3"*/17:
            case /*"h4"*/18:
            case /*"h5"*/19:
            case /*"h6"*/20:
            case /*"hr"*/21:
            case /*"html"*/22:
            case /*"header"*/23:
            case /*"hgroup"*/24:
            case /*"li"*/25:
            case /*"noscript"*/26:
            case /*"ol"*/27:
            case /*"p"*/28:
            case /*"pre"*/29:
            case /*"table"*/30:
            case /*"tfoot"*/31:
            case /*"ul"*/32:
            case /*"video"*/33:
            case /*"xmp"*/34:
            case /*"listing"*/35:
                return true;
            default:
                return false;
        }
    }

    private /*final*/ CssClassFactory mClassFactory;
    private static /*final*/ HashMap<CssValue, Integer> M_CSS_VALUE_TO_DISPLAY_TYPE_MAP;

    static/* HtmlElementCategorizer()*/
    {
        M_CSS_VALUE_TO_DISPLAY_TYPE_MAP = new HashMap<CssValue, Integer>();
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.Inline, CssDisplayType.INLINE);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.Block, CssDisplayType.BLOCK);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.InlineBlock, CssDisplayType.INLINE_BLOCK);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.ListItem, CssDisplayType.LIST_ITEM);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.RunIn, CssDisplayType.RUN_IN);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.Table, CssDisplayType.TABLE);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.InlineTable, CssDisplayType.INLINE_TABLE);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableCaption, CssDisplayType.TABLE_CAPTION);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableHeaderGroup, CssDisplayType.TABLE_HEADER_GROUP);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableColumnGroup, CssDisplayType.TABLE_COLUMN_GROUP);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableColumn, CssDisplayType.TABLE_COLUMN);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableRowGroup, CssDisplayType.TABLE_ROW_GROUP);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableRow, CssDisplayType.TABLE_ROW);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableCell, CssDisplayType.TABLE_CELL);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.TableFooterGroup, CssDisplayType.TABLE_FOOTER_GROUP);
        msDictionary.add(M_CSS_VALUE_TO_DISPLAY_TYPE_MAP, CssValue.None, CssDisplayType.NONE);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"address",
		"article",
		"aside",
		"audio",
		"blockquote",
		"body",
		"canvas",
		"div",
		"dl",
		"dd",
		"fieldset",
		"figcaption",
		"figure",
		"footer",
		"form",
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"hr",
		"html",
		"header",
		"hgroup",
		"li",
		"noscript",
		"ol",
		"p",
		"pre",
		"table",
		"tfoot",
		"ul",
		"video",
		"xmp",
		"listing"
	);

}

