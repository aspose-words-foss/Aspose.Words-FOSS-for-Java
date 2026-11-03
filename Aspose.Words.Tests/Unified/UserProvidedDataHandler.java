// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


public class UserProvidedDataHandler implements IResourceLoadingCallback
{
    public /*ResourceLoadingAction*/int resourceLoading(ResourceLoadingArgs args)
    {
        if (args.getResourceType() == ResourceType.IMAGE)
        {
            msAssert.isNotEmpty(args.getOriginalUri());
                
            // Set test data.
            args.setData(new byte[5]);
            return ResourceLoadingAction.USER_PROVIDED;
        }

        return ResourceLoadingAction.DEFAULT;
    }
}

