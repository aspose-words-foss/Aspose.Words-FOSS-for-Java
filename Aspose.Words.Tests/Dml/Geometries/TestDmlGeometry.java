// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlGeometry
{
    @Test
    public void addPath_Null_NothingAdded()
    {
        DmlGeometry geometry = new DmlGeometry();
        // Act
        geometry.addPath(null);
        // Assert
        msAssert.areEqual(0, geometry.getPaths().size());
    }

    @Test
    public void addPath_Path_PathAdded()
    {

        // Arrange
        DmlGeometry geometry = new DmlGeometry();
        DmlPath path = new DmlPath();
        // Act
        geometry.addPath(path);
        // Assert
        msAssert.areEqual(1, geometry.getPaths().size());
        msAssert.areEqual(path, geometry.getPaths().get(0));
    }

}

