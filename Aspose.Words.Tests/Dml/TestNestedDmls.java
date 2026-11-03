// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestNestedDmls
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }


    @Test
    public void testJira10790() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\DrawingML\\TestJira10790.docx");
        GroupShape dml = (GroupShape)doc.getChild(NodeType.GROUP_SHAPE, 0, true);
        msAssert.isTrue(dml.getDmlNode().getDmlNodeType() == DmlNodeType.WORDPROCESSING_CANVAS);
        DmlLockedCanvas canvas = (DmlLockedCanvas)dml.getDmlNode();
        msAssert.isNotNull(canvas.getFill());
        msAssert.isTrue(canvas.getFill().getDmlFillType() == DmlFillType.SOLID_FILL);
        msAssert.isNotNull(canvas.getOutline());
        msAssert.isFalse(canvas.getOutline().getDirectPropertiesCount() <= 0);
    }
}

