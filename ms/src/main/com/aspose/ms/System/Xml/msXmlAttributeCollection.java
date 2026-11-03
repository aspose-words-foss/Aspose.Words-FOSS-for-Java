// Konstantin Sidorenko 10/10/2019.

package com.aspose.ms.System.Xml;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

/**
 * Extension methods for porting System.Xml.XmlAttributeCollection.
 */
public class msXmlAttributeCollection
{
    public static Attr get(NamedNodeMap attributes, String localName, String namespaceURI)
    {
        return (Attr) attributes.getNamedItemNS(namespaceURI, localName);
    }

    public static void remove(NamedNodeMap attributes, Attr attribute)
    {
        attributes.removeNamedItem(attribute.getName());
    }
}
