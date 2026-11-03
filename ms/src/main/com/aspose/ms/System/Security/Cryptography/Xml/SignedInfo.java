// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//15/02/13 by Durin Vyacheslav
package com.aspose.ms.System.Security.Cryptography.Xml;

import java.util.ArrayList;
import java.util.List;

/**
 * http://msdn.microsoft.com/en-us/library/system.security.cryptography.xml.signedinfo.aspx
 */
public class SignedInfo
{
    public List<Reference> getReferences()
    {
        return mReferences;
    }

    public void addReference(Reference reference)
    {
        if (reference == null)
            throw new IllegalArgumentException("Reference cannot be null");

        mReferences.add(reference);
    }

    private final List<Reference> mReferences = new ArrayList<Reference>();
}
