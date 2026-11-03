// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.W3CNamespaces;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Represents an element node of an HTML document tree.
/// </summary>
class HtmlElementNode extends HtmlNode implements IHtmlElementProvider
{
    /// <summary>
    /// Constructor. Initializes a new instance of the class. The element will be in the HTML namespace and will have no
    /// attributes.
    /// </summary>
    /// <param name="name">The element's name.</param>
    HtmlElementNode(String name)
    {
    	this(name, W3CNamespaces.XHTML, new HtmlAttributeCollection());
	
        // This constructor is empty. The call is delegated to another constructor.
    }

    /// <summary>
    /// Constructor. Initializes a new instance of the class. The element will be in the HTML namespace and will have no
    /// attributes.
    /// </summary>
    /// <param name="name">The element's name.</param>
    /// <param name="isImplicit">Indicates whether the element is implicit.</param>
    HtmlElementNode(String name, boolean isImplicit)
    {
    	this(name, W3CNamespaces.XHTML, new HtmlAttributeCollection());
	
        mIsImplicit = isImplicit;
    }

    /// <summary>
    /// Constructor. Initializes a new instance of the class.
    /// </summary>
    /// <param name="name">The element's name.</param>
    /// <param name="ns">The element's namespace.</param>
    /// <param name="attributes">The element's attributes.</param>
    HtmlElementNode(String name, String ns, HtmlAttributeCollection attributes)
    {
        Debug.assertTrue(StringUtil.hasChars(name));
        Debug.assertTrue(ns != null);
        Debug.assertTrue(attributes != null);

        mName = name;
        mNamespace = ns;
        mChildren = new HtmlNodeCollection(this);
        mAttributes = attributes;
    }

    /// <summary>
    /// Searches for an element with the specified name in the depth-first manner.
    /// </summary>
    /// <param name="name">The name of the required element.</param>
    /// <returns>
    /// The first met element that has the specified name, or <c>null</c> if no such element is found.
    /// </returns>
    HtmlElementNode findSingleElementByName(String name)
    {
        HtmlTreeEnumerator enumerator = new HtmlTreeEnumerator(this);
        while (enumerator.moveNext())
        {
            if (enumerator.isStart())
            {
                HtmlElementNode currentElement = ms.as(enumerator.getCurrent(), HtmlElementNode.class);
                if ((currentElement != null) && (msString.equals(currentElement.getName(), name)))
                {
                    return currentElement;
                }
            }
        }

        return null;
    }

    public IElementProvider /*IElementProvider.*/getParentElement()
    {
        return getParent();
    }

    public IElementProvider /*IElementProvider.*/getFirstChildElement()
    {
        for (int i = 0; i < mChildren.getCount(); i++)
        {
            if (mChildren.get(i) instanceof IHtmlElementProvider)
                return (IHtmlElementProvider)mChildren.get(i);
        }
        return null;
    }

    public IElementProvider /*IElementProvider.*/getPreviousSiblingElement()
    {
        HtmlNode siblingElement = getPreviousSibling();
        while (siblingElement != null)
        {
            if (siblingElement instanceof IHtmlElementProvider)
                return (IHtmlElementProvider)siblingElement;
            siblingElement = siblingElement.getPreviousSibling();
        }
        return null;
    }

    public IElementProvider /*IElementProvider.*/getNextSiblingElement()
    {
        HtmlNode siblingElement = getNextSibling();
        while (siblingElement != null)
        {
            if (siblingElement instanceof IHtmlElementProvider)
                return (IHtmlElementProvider)siblingElement;
            siblingElement = siblingElement.getNextSibling();
        }
        return null;
    }

    /// <summary>
    /// Returns child elements of this node.
    /// </summary>
    public IElementProvider[] /*IElementProvider.*/getChildElements()
    {
        // Use cached array if it's still actual.
        if (mChildCollectionVersion == mChildren.getVersion())
        {
            Debug.assertTrue(mCachedChildElements != null);
            return mCachedChildElements;
        }

        int elementsCount = 0;
        for (HtmlNode childNode : mChildren)
        {
            if (childNode instanceof IHtmlElementProvider)
                elementsCount++;
        }

        mCachedChildElements = new IHtmlElementProvider[elementsCount];
        int index = 0;
        for (HtmlNode childNode : mChildren)
        {
            if (childNode instanceof IHtmlElementProvider)
                mCachedChildElements[index++] = (IHtmlElementProvider)childNode;
        }

        mChildCollectionVersion = mChildren.getVersion();
        return mCachedChildElements;
    }

    /// <summary>
    /// Returns a value indicating whether this element contains only one child element optionally surrounded by whitespace.
    /// </summary>
    public boolean hasOnlyOneChildElementWithOptionalWhitespace()
    {
        boolean oneChildElementFound = false;
        for (HtmlNode node : mChildren)
        {
            if (node instanceof HtmlElementNode)
            {
                if (oneChildElementFound)
                {
                    // There are more than one child element.
                    return false;
                }
                oneChildElementFound = true;
            }
            else if (node.containsText())
            {
                // There are non-whitespace child text nodes.
                return false;
            }
        }
        return oneChildElementFound;
    }

    /// <summary>
    /// Gets an array of classes declared on this element (contents of the 'class' attribute).
    /// </summary>
    public String[] /*IElementProvider.*/getClasses()
    {
        cacheClassesIfNeeded();
        return mCachedClasses;
    }

    public boolean /*IHtmlElementProvider.*/startsWithImplicitBox() { return mStartsWithImplicitBox; }

    public boolean /*IHtmlElementProvider.*/endsWithImplicitBox() { return mEndsWithImplicitBox; }

    public boolean /*IHtmlElementProvider.*/containsChildBoxes() { return mContainsChildBoxes; }

    public boolean /*IHtmlElementProvider.*/isElementContainsText() { return mContainsText; }

    public boolean /*IHtmlElementProvider.*/isLastChildBox() { return mIsLastChildBox; }

    public String /*IElementProvider.*/getAttributeValue(String attribName)
    {
        return getAttributes().getAttributeValue(attribName, null);
    }

    public String /*IElementProvider.*/getAttributeValue(String attribName, String def)
    {
        return getAttributes().getAttributeValue(attribName, def);
    }

    public String /*IElementProvider.*/getLanguage()
    {
        return getLanguageFromLangAttribute(this);
    }

    public String /*IElementProvider.*/getElementNamespace() { return mNamespace; }

    public String /*IElementProvider.*/getElementName() { return mName; }

    public boolean /*IElementProvider.*/isImplicit() { return mIsImplicit; }

    /// <summary>
    /// Gets the text of the first child text node.
    /// </summary>
    /// <returns>
    /// The text of the first text child node, if the current node has one. If the current node does not have any text child nodes,
    /// an empty string is returned.
    /// </returns>
    public String getInnerText()
    {
        for (int i = 0; i < mChildren.getCount(); i++)
        {
            if (mChildren.get(i) instanceof HtmlTextNode)
            {
                return ((HtmlTextNode)mChildren.get(i)).getText();
            }
        }
        return "";
    }

    /// <summary>
    /// Serializes the node to XML text.
    /// </summary>
    /// <param name="result">
    /// The text buffer to which the XML text of this node will be appended.
    /// </param>
    /// <param name="serializationContext">Serialization context.</param>
    /// <remarks>
    /// This method follows the algorithm described here: http://www.w3.org/TR/DOM-Parsing/#serializing
    /// </remarks>
    /*override*/ void serializeToXml(StringBuilder result, XmlSerializationContext serializationContext)
    {
        serializationContext.startNode(this);

        // Start tag.
        msStringBuilder.append(result.append('<'), mName);
        String attributes = mAttributes.serialize(serializationContext);
        if (!msString.equals(attributes, ""))
        {
            msStringBuilder.append(result.append(' '), attributes);
        }

        if (mChildren.getCount() == 0)
        {
            // Self closing tag.
            msStringBuilder.append(result, " />");
        }
        else
        {
            // Finish the start tag.
            result.append('>');

            // In elements with preformatted text, restore the first LF character, which was removed by HTML parser.
            if ((W3CNamespaces.XHTML.equals(mNamespace)) &&
                (("pre".equals(mName)) || ("textarea".equals(mName)) || ("listing".equals(mName))))
            {
                if ((mChildren.getCount() > 0) && (mChildren.get(0) instanceof HtmlTextNode))
                {
                    String firstChildText = ((HtmlTextNode)mChildren.get(0)).getText();
                    if ((firstChildText.length() > 0) && (firstChildText.charAt(0) == '\n'))
                    {
                        result.append('\n');
                    }
                }
            }

            // Serialize children.
            for (HtmlNode child : mChildren)
            {
                child.serializeToXml(result, serializationContext);
            }

            // End tag.
            msStringBuilder.append(msStringBuilder.append(result, "</"), mName).append('>');
        }

        serializationContext.endNode();
    }

    /*override*/ boolean isBoxOrContainsBoxes()
    {
        return mIsBoxOrContainsBoxes;
    }

    /*override*/ boolean containsText()
    {
        return mContainsText;
    }

    /// <summary>
    /// Gets the element's language from the nearest 'lang' attribute.
    /// </summary>
    /// <returns>
    /// The language defined in the element's 'lang' attribute or in a 'lang' attribute of the nearest parent.
    /// If neither the element itself nor its parents has the 'lang' attribute, empty string is returned;
    /// </returns>
    private static String getLanguageFromLangAttribute(HtmlElementNode node)
    {
        HtmlAttribute langAttribute = node.getAttributes().get("lang");
        if (langAttribute != null)
            return langAttribute.getValue();

        // Recursively examine element's parents until we met one that has the 'lang' attribute.
        return (node.getParent() != null)
                   ? getLanguageFromLangAttribute(node.getParent())
                   : "";
    }

    /// <summary>
    /// Gets the name of the element. Names of standard HTML elements created by the HTML parser are always in lowercase.
    /// </summary>
    String getName() { return mName; }

    /// <summary>
    /// Gets the namespace of the element.
    /// </summary>
    String getNamespace() { return mNamespace; }

    /// <summary>
    /// Gets the children of the element.
    /// </summary>
    HtmlNodeCollection getChildren() { return mChildren; }

    /// <summary>
    /// Gets the attributes of the element;
    /// </summary>
    HtmlAttributeCollection getAttributes() { return mAttributes; }

    protected /*override*/ boolean buildNodeBoxLayout(boolean isStart, boolean preformattedWhitespace)
    {
        return isStart
            ? beginBuildNodeBoxLayout()
            : endBuildNodeBoxLayout();
    }

    protected /*override*/ boolean isPreformattedWhitespaceNode()
    {
        // The following elements by default have preformatted contents with meaningful whitespace (text of the element
        // must be processed even if it contains whitespace characters only).
        if (("pre".equals(mName)) || ("xmp".equals(mName)) || ("listing".equals(mName)) || ("plaintext".equals(mName)))
        {
            return true;
        }

        // Whitespace of other elements is not preformatted by default.
        return false;
    }

    private boolean beginBuildNodeBoxLayout()
    {
        mStartsWithImplicitBox = false;
        mEndsWithImplicitBox = false;
        mContainsChildBoxes = false;
        mContainsText = false;
        mIsBoxOrContainsBoxes = false;
        mIsLastChildBox = false;

        switch (gStringSwitchMap.of(mName))
        {
            case /*"script"*/0:
            case /*"style"*/1:
                // <script> and <style> elements have special meaning, and their text is ignored by the CSS box model.
                return false;
            case /*"img"*/2:
            case /*"svg"*/3:
            case /*"br"*/4:
            case /*"hr"*/5:
                // These elements have their own representations in Word documents, so they are considered non-empty
                // even though they do not contain any text. They are like anonymous (implicit) text blocks.
                mContainsText = true;
                break;
            default:
                // All other elements require no special processing.
                break;
        }

        return true;
    }

    private boolean endBuildNodeBoxLayout()
    {
        // At this point, box layout of all child nodes has already been built.

        HtmlElementNode lastChildBox = null;
        boolean isFirstElementWithText = true;
        for (HtmlNode child : mChildren)
        {
            if (child.containsText())
            {
                if (isFirstElementWithText)
                {
                    mStartsWithImplicitBox = !child.isBoxOrContainsBoxes();
                    isFirstElementWithText = false;
                }
                mContainsText = true;
                mEndsWithImplicitBox = !child.isBoxOrContainsBoxes();
            }

            if (child.isBoxOrContainsBoxes())
            {
                mContainsChildBoxes = true;
                HtmlElementNode childElement = ms.as(child, HtmlElementNode.class);
                if (childElement != null)
                {
                    lastChildBox = childElement;
                }
            }
        }

        mIsBoxOrContainsBoxes =
            (HtmlElementCategorizer.isBlockElementByDefault(mName) && mContainsText) ||
            mContainsChildBoxes;

        if (lastChildBox != null)
        {
            lastChildBox.mIsLastChildBox = true;
        }
        if (getParent() == null)
        {
            mIsLastChildBox = true;
        }

        return true;
    }

    private void cacheClassesIfNeeded()
    {
        if (mAttributesVersion != mAttributes.getVersion())
        {
            cacheClasses();
        }
    }

    private void cacheClasses()
    {
        String classValue = mAttributes.getAttributeValue("class", "");
        mCachedClasses = (!msString.equals(classValue, ""))
            ? CssUtil.splitClassAttributeValue(classValue)
            : G_NO_CLASSES;
        mAttributesVersion = mAttributes.getVersion();
    }

    /// <summary>
    /// A zero-length array of CSS class names.
    /// </summary>
    private static /*final*/ String[] G_NO_CLASSES = new String[0];

    private /*final*/ String mName;
    private /*final*/ String mNamespace;
    private /*final*/ HtmlNodeCollection mChildren;
    private /*final*/ HtmlAttributeCollection mAttributes;

    private boolean mStartsWithImplicitBox;
    private boolean mEndsWithImplicitBox;
    private boolean mContainsChildBoxes;
    private boolean mIsLastChildBox;
    private boolean mContainsText;
    private boolean mIsBoxOrContainsBoxes;
    private /*final*/ boolean mIsImplicit;

    /// <summary>
    /// Cached child elements. Used for performance improvement.
    /// </summary>
    private IHtmlElementProvider[] mCachedChildElements;
    private long mChildCollectionVersion;

    /// <summary>
    /// An array of individual CSS classes declared on this element via the 'class' attribute.
    /// </summary>
    /// <remarks>
    /// SPEED. Individual classes are used when matching CSS selectors and HTML elements, which is a very frequent
    /// operation. We improve performance by splitting classes only once and caching the result.
    /// </remarks>
    private String[] mCachedClasses;
    private long mAttributesVersion;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"script",
		"style",
		"img",
		"svg",
		"br",
		"hr"
	);

}

