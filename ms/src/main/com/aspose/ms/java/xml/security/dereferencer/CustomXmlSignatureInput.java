package com.aspose.ms.java.xml.security.dereferencer;

import com.aspose.ms.java.xml.XmlUtils;
import org.w3c.dom.Node;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Simplified version of org.apache.xml.security.signature.XMLSignatureInput
 * The main method is getNodeSet(). It returns a set of nodes without comments.
 */
class CustomXmlSignatureInput
{
    public CustomXmlSignatureInput(Node rootNode)
    {
        this.mSubNode = rootNode;
    }

    public void setExcludeComments(boolean excludeComments)
    {
        this.mExcludeComments = excludeComments;
    }

    public Set<Node> getNodeSet()
    {
        LinkedHashSet inputNodeSet = new LinkedHashSet<Node>();
        XmlUtils.getNodeSetRecursively(mSubNode, inputNodeSet, null, mExcludeComments);
        return inputNodeSet;
    }

    private final Node mSubNode;
    private boolean mExcludeComments;
}
