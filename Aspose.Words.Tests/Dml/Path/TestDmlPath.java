// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlPath
{
    @Test
    public void addPathPart_Null_NothingAdded()
    {
        DmlPath path = createPath();
        // Act
        path.addPathPart(null);
        // Assert
        msAssert.areEqual(0, path.getPathParts().size());
    }

    @Test
    public void addPathPart_PathPart_PathPartAdded()
    {
        DmlPath path = createPath();
        // Arrange
        DmlClose pathPart = new DmlClose();
        // Act
        path.addPathPart(pathPart);
        // Assert
        msAssert.areEqual(1, path.getPathParts().size());
        msAssert.areEqual(pathPart, path.getPathParts().get(0));
    }

    private static DmlPath createPath()
    {
        DmlPath path = new DmlPath();
        path.setHeight(10.0);
        path.setWidth(20.0);
        
        return path;
    }
}
