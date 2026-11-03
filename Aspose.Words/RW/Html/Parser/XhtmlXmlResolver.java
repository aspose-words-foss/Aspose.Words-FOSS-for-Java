// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2022 by Artem Shabarshin
// 30/05/2022 by Konstantin Sidorenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.SystemPal;
import com.aspose.ms.System.Xml.XmlNodeType;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;


/// <summary>
/// Resolves XHTML entities.
/// </summary>
/// <remarks>
/// We need to make sure the XML parser recognizes XHTML entities in case the document references XHTML DTD. However, we
/// cannot allow the XML parser to load DTD from external resources. As a partial solution, we provide an artificial
/// XHTML DTD containing only entity definitions.
/// </remarks>
//  [JavaAttributes.JavaManual(".Net XmlResolver.GetEntity() is changed to Java's XMLResolver.resolveEntity()")]
class XhtmlXmlResolver implements XMLResolver
{
    // Java-changed due of difference of .Net XmlResolver and Java's XMLResolver:
    // System.Xml.XmlResolver.GetEntity() is changed to
    // javax.xml.stream.XMLResolver.resolveEntity().

//    public override object GetEntity(
//        Uri absoluteUri,
//        string role,
//        Type ofObjectToReturn)
    @Override
    public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException
    {
        // The XHTML entity may be resolved during parsing the DocumentType node as well as during parsing Element nodes.
        // If the XHTML entity is resolved during the Element node then it is an external entity.
        // Like Adobe Digital Editions, we don't support external entities and resolve them to empty strings.
        if (mInsideElementNode)
            return null;

        // If XML parser resolves XHTML 1.1 DTD resource then we return all XHTML entities.
        // XHTML doctype declarations list: https://www.w3.org/QA/2002/04/valid-dtd-list.html
        if (("http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd".equals(systemID)) ||
            ("http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd".equals(systemID)) ||
            ("http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd".equals(systemID)) ||
            ("http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd".equals(systemID)) ||
            ("http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd".equals(systemID)))
        {
            InputStream stream = SystemPal.getResourceAsStream("Aspose.Words.Resources.XhtmlEntities.dtd", Document.class);
            StreamSource source = new StreamSource(stream, systemID);
            source.setPublicId(publicID);

            return source;
        }

        // The XML parser first tries to load the XHTML DTD using the public identifier. It appends the public
        // identifier to the base URL of the XHTML document and passes an URL like
        // "file:///C:/Users/Username/AppData/Local/Temp/-/W3C/DTD%20XHTML%201.1/EN".
        // We return null if we detect that the XML parser passed the public identifier. This makes the XML parser
        // try to load XHTML DTD using the system identifier (an URI). If we don't return null here, the XML parser
        // won't use the system identifier and won't load the DTD at all.
        // Note that public identifiers are case-insensitive.
        if (StringUtil.indexOfIgnoreCase(systemID, "-/W3C/DTD XHTML") >= 0)
        {
            return null;
        }

        // It is an unknown entity in the DocumentType node.
        // Resolve it to an empty string.
        return null;
    }

    void setCurrentNodeType(/*XmlNodeType*/int nodeType)
    {
        mInsideElementNode = nodeType == XmlNodeType.ELEMENT;
    }

    /// <summary>
    /// Indicates whether a current parsing XML node has the Element type.
    /// </summary>
    private boolean mInsideElementNode;
}

