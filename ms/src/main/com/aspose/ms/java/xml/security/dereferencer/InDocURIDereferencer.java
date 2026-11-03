package com.aspose.ms.java.xml.security.dereferencer;

import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;
import com.aspose.ms.java.xml.XmlUtils;
import org.w3c.dom.Element;

import javax.xml.crypto.Data;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.xpath.XPathConstants;

/**
 * A custom dereferencer which uses XPath to resolve objects by its Id.
 */
class InDocURIDereferencer implements IDereferencer
{
    @Override
    public boolean canDereference(URIReference reference, XMLCryptoContext context)
    {
        return mDocument != null && reference.getURI() != null && reference.getURI().startsWith(SHARP);
    }

    @Override
    public Data dereference(URIReference uriReference, XMLCryptoContext context) throws URIReferenceException
    {
        if (uriReference == null || context == null)
            throw new IllegalArgumentException("URIReference and XMLCryptoContext cannot be null");

        try
        {
            // we use XPath to resolve the object by its Id.
            String id = uriReference.getURI().substring(1);
            javax.xml.xpath.XPath xpath = XmlUtils.getXPathFactory().newXPath();
            final org.w3c.dom.NodeList nodeList =
                    (org.w3c.dom.NodeList) xpath.evaluate("//*[@Id='" + id + "']", mDocument, XPathConstants.NODESET);

            if (nodeList.getLength() == 0)
                throw new URIReferenceException("Cannot resolve the object with Id=" + id);

            // create XMLSignatureInput in order to prepare data correctly.
            CustomXmlSignatureInput result = new CustomXmlSignatureInput(nodeList.item(0));
            result.setExcludeComments(true);
            return new CustomNodeSetData(result);
        }
        catch (Exception ex)
        {
            Log.warn(ex);
        }

        throw new URIReferenceException(
                "Cannot resolve the object with Id=" + uriReference.getURI() + " and Type=" + uriReference.getType());
    }

    private static final String SHARP = "#";

    /**
     * Initializes InDocURIDereferencer with the document to search within.
     *
     * @param document to search within.
     */
    public InDocURIDereferencer(Element document)
    {
        mDocument = document;
    }

    private final Element mDocument;
}
