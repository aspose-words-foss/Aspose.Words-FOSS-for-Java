// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Interface for web element.
/// </summary>
interface IElementProvider
{
    /// <summary>
    /// Gets the parent of this node (for nodes that can have parents).
    /// </summary>
    public IElementProvider getParentElement();

    /// <summary>
    /// Gets the first child element. May be <c>null</c>.
    /// </summary>
    public IElementProvider getFirstChildElement();

    /// <summary>
    /// Gets the sibling element that precedes this element. May be <c>null</c>.
    /// </summary>
    public IElementProvider getPreviousSiblingElement();

    /// <summary>
    /// Gets the sibling element that follows this element. May be <c>null</c>.
    /// </summary>
    public IElementProvider getNextSiblingElement();

    /// <summary>
    /// Returns child elements of this node.
    /// </summary>
    public IElementProvider[] getChildElements();

    /// <summary>
    /// Gets the value of an HTML node attribute. If the attribute is not found, null value will be returned.
    /// </summary>
    /// <param name="attribName">The name of the attribute to get. May not be null.</param>
    /// <returns>The value of the attribute if found; null value if not found.</returns>
    public String getAttributeValue(String attribName);

    /// <summary>
    /// Gets the class atribute contents split into separate classes.
    /// </summary>
    /// <returns>
    /// An array of classes declared on this element, one array item per class. If the element has no class attribute
    /// or the class attribute contains no classes, the method returns a zero-length array. The result is never <c>null</c>.
    /// </returns>
    public String[] getClasses();

    /// <summary>
    /// Gets the language of this element.
    /// </summary>
    /// <returns>
    /// The language string of the HTML element. If the element's language is unknown, an empty string is returned.
    /// </returns>
    /// <remarks>
    /// In HTML the language of elements is set by the 'lang' attribute and is inherited by child elements.
    /// If element's ancestors do not have the 'lang' attribute, the HTML document's metadata are used
    /// to determine the language of the element. If no language information is present, the element's language
    /// is unknown. For details see http://www.w3.org/TR/html5/dom.html#language
    /// </remarks>
    public String getLanguage();

    /// <summary>
    /// Helper method to get the value of an attribute of this node. 
    /// If the attribute is not found, the default value will be returned.
    /// </summary>
    /// <param name="attribName">The name of the attribute to get. May not be null.</param>
    /// <param name="def">The default value to return if not found.</param>
    /// <returns>The value of the attribute if found, the default value if not found.</returns>
    public String getAttributeValue(String attribName, String def);

    public String getInnerText();

    /// <summary>
    /// HTML element name.
    /// </summary>
    public String getElementName();

    /// <summary>
    /// HTML element namespace.
    /// </summary>
    public String getElementNamespace();

    /// <summary>
    /// Indicates whether the element is implicit. 
    /// </summary>
    /// <remarks>
    /// The elements you can see in HTML code are explicit elements (e.g. span element in <span>text</span>). But in some cases we can use implicit elements.
    /// E.g. we add span element in <p>text</p> code to emulate <p><span>text</span></p> code that we can process correctly. See also WORDSNET-8899
    /// </remarks>
    public boolean isImplicit();
}

