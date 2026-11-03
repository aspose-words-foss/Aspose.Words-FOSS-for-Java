// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : System.Xml/System/Xml/Core/XmlReader.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// ported 31/07/2008 by Konstantin Sidorenko

package com.aspose.ms.System.Xml;

import com.aspose.ms.java.xml.XmlUtils;

import javax.xml.stream.XMLStreamConstants;

/**
 * Copycat of System.Xml.XmlReader.
 *
 * Only methods used by AW are ported.
 */
public abstract class XmlReader implements XMLStreamConstants
{
	// Read the next node in the input stream.
	public abstract boolean read();

	// Move to the content part of an element node.
	public int moveToContent()
	{
	    do
	    {
	        int type = getNodeType();
	        if (type == XmlNodeType.ATTRIBUTE)
	        {
	            moveToElement();
	            return getNodeType();
	        }
	        else if (type == XmlNodeType.ELEMENT ||
	                type == XmlNodeType.END_ELEMENT ||
	                type == XmlNodeType.TEXT ||
	                type == XmlNodeType.CDATA ||
	                type == XmlNodeType.ENTITY ||
	                type == XmlNodeType.ENTITY_REFERENCE)
	        {
	            return type;
	        }
	    }
	    while (read());
	    return getNodeType();
	}

	// Move to the element that owns the current attribute.
	public abstract boolean moveToElement();

	// Move to the first attribute owned by the current element.
	public abstract boolean moveToFirstAttribute();

	// Move to the next attribute owned by the current element.
	public abstract boolean moveToNextAttribute();

	// Skip the current element in the input.
	public void skip()
	{
		skipSubtree();
		read();
	}

    // RK Code copied from Rotor.
    private boolean skipSubtree()
    {
        moveToElement();
        if ((getNodeType() == XmlNodeType.ELEMENT) && !isEmptyElement())
        {
            int originalDepth = getDepth();
            while (read() && (originalDepth < getDepth()))
            {
                // Do nothing here because read() is called.
            }
        }
		return (getNodeType() == XmlNodeType.END_ELEMENT);
    }

	// Calls MoveToContent and tests if the current content node is a start tag or empty element tag (XmlNodeType.Element).
	public boolean isStartElement()
	{
	    return moveToContent() == XmlNodeType.ELEMENT;
	}

    public abstract String readInnerXml() throws Exception;

    public abstract String readOuterXml() throws Exception;

	// Determine if the current node is an empty element.
	//JAVA: not applicable for java's XMLStreamReader since StAX parser internally
	//converts empty elements to ordinary non-empty elements.
	public abstract boolean isEmptyElement();

	// Get the local name of the current node.
	public abstract String getLocalName();

	// Get the namespace URI associated with the current node.
	public abstract String getNamespaceURI();

	// Get the type of the current node.
	public abstract int getNodeType();

	// Get the namespace prefix associated with the current node.
	public abstract String getPrefix();

	// Determine if the current node has attributes.
	//JAVA: was non-abstract in .Net.
	public abstract boolean hasAttributes();

	// Get the text value of the current node.
	public abstract String getValue() throws Exception;

    // The depth of the current node in the XML document.
    public abstract int getDepth();

	public abstract int getAttributeCount();

	public abstract boolean moveToAttribute(int idx);

	public abstract String getAttribute(int idx);

    public abstract boolean canResolveEntity();

    public abstract void resolveEntity();
}
