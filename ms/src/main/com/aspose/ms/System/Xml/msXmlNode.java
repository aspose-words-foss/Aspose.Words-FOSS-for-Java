// Konstantin Sidorenko 2/07/2016.
package com.aspose.ms.System.Xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Extension methods for porting System.Xml.XmlNode.
 */
public class msXmlNode
{
    /**
     * Retrieves the first child element with the specified name.
     */
    public static Element get(Node node, String name)
    {
        for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling())
        {
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(name))
                return(Element) n;
        }

        return null;
    }

    public static Element get(Node node, String name, String namespace)
    {
        if (namespace == null || "".equals(namespace))
            return get(node, name);
        else
            for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling())
                if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(name)
                            && n.getNamespaceURI() != null && n.getNamespaceURI().equals(namespace))
                   return (Element) n;

        return null;
    }
}
