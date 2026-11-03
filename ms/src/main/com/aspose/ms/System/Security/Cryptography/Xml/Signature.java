// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2013 by Vyacheslav Durin
package com.aspose.ms.System.Security.Cryptography.Xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the <Signature> element of an XML signature.
 * http://msdn.microsoft.com/ru-ru/library/system.security.cryptography.xml.signature(v=vs.80).aspx
 */
public class Signature
{
    public void setId(String id)
    {
        this.mId = id;
    }

    public String getId()
    {
        return mId;
    }

    public void addObject(DataObject dobj)
    {
        mDataObjects.add(dobj);
    }

    public List<DataObject> getDataObjects()
    {
        return mDataObjects;
    }

    public Signature()
    {
        mDataObjects = new ArrayList<DataObject>();
    }

    private List<DataObject> mDataObjects;
    private String mId = "idPackageSignature";
}
