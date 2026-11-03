// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//01/03/13 by Durin Vyacheslav
package com.aspose.ms.System.Security.Cryptography.Xml;

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.xml.reference.aspx
 */
public class Reference
{
    public String getUri()
    {
        return mUri;
    }

    public void setUri(String uri)
    {
        this.mUri = uri;
    }

    public void setType(String type)
    {
        this.mType = type;
    }

    public String getType()
    {
        return mType;
    }

    public List<XmlDsigC14NTransform> getTransforms() { return transforms; }

    public Stream getStream() { return mRefTarget; }

    public boolean equals(Object other)
    {
        if (other == null || !(other instanceof Reference))
            return false;
        if (other == this)
            return true;

        Reference ref = (Reference) other;
        return ref.getUri().equals(getUri());
    }

    public int hashCode()
    {
        int result = 32;
        return result + getUri().hashCode();
    }

    public void addTransform(XmlDsigC14NTransform transform)
    {
        this.transforms.add(transform);
    }

    /**
     * Initializes a new instance of the Reference class with a hash value of the specified Stream
     *
     * @param stream The Stream with which to initialize the new instance of Reference.
     */
    public Reference(MemoryStream stream)
    {
        this.mRefTarget = stream;
    }

    public Reference() { }

    private MemoryStream mRefTarget;

    private String mUri;
    private String mType;

    private List<XmlDsigC14NTransform> transforms = new ArrayList<XmlDsigC14NTransform>();
}
