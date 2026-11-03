// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2013 by Vyacheslav Durin
package com.aspose.ms.System.Security.Cryptography.Xml;

import com.aspose.ms.System.IO.Stream;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents the object element of an XML signature that holds the data to be signed.
 * http://msdn.microsoft.com/ru-ru/library/system.security.cryptography.xml.dataobject(v=vs.71).aspx
 */
public class DataObject
{
    public void setId(String id)
    {
        this.id = id;
    }

    public void setData(NodeList data)
    {
        this.data = data;

        if (this.data != null && this.id == null)
        {
            if (data instanceof Element)
            {
                Element el = (Element ) data;
                Attr id = el.getAttributeNode("Id");
                this.id = id.getValue();
            }
        }
    }

    public NodeList getData()
    {
        return this.data;
    }

    public String getId()
    {
        return id;
    }

    private NodeList data;
    private String id;
}
