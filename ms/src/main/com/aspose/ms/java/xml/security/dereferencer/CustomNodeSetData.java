package com.aspose.ms.java.xml.security.dereferencer;


import org.w3c.dom.Node;

import javax.xml.crypto.NodeSetData;
import java.util.Collections;
import java.util.Iterator;

/**
 * Simplified version of internal class org.apache.jcp.xml.dsig.internal.dom.ApacheNodeSetData
 */
class CustomNodeSetData implements NodeSetData
{
    public CustomNodeSetData(CustomXmlSignatureInput signatureInput)
    {
        this.mSignatureInput = signatureInput;
    }

    @Override
    public Iterator<Node> iterator()
    {
        return Collections.unmodifiableSet(mSignatureInput.getNodeSet()).iterator();
    }

    private CustomXmlSignatureInput mSignatureInput;
}
