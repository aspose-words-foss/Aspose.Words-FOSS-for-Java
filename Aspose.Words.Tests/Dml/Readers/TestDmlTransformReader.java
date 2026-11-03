// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="DmlTransformReader"/>.
/// </summary>
@Test
public class TestDmlTransformReader
{
    @Test
    public void buildGroupTransform_AllPropertiesSpecified_AllPropertiesReaded()
    {
        // Arrange
        String xml = "<a:xfrm xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" "+
                        "flipH=\"1\" flipV=\"true\" rot=\"3600000\">" +
                        "<a:ext cx=\"1676400\" cy=\"1095375\" />" +
                        "<a:off x=\"6400\" y=\"109\" />" +
                        "<a:chExt cx=\"64001\" cy=\"1091\" />" +
                        "<a:chOff x=\"1\" y=\"2\" />" +
                     "</a:xfrm>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlGroupTransform result = DmlTransformReader.readGroupTransform(reader, null);
        // Assert
        msAssert.areEqual(3600000.0, result.getRotation().getValue());
        msAssert.areEqual(FlipOrientation.BOTH, result.getFlipOrientation());
        msAssert.areEqual(1676400.0, result.getWidth());
        msAssert.areEqual(1095375.0, result.getHeight());
        msAssert.areEqual(6400.0, result.getX());
        msAssert.areEqual(109.0, result.getY());
        msAssert.areEqual(64001.0, result.getChildWidth());
        msAssert.areEqual(1091.0, result.getChildHeight());
        msAssert.areEqual(1.0, result.getChildX());
        msAssert.areEqual(2.0, result.getChildY());
    }

    @Test
    public void buildTransform_AllPropertiesSpecified_AllPropertiesReaded()
    {
        // Arrange
        String xml = "<a:xfrm xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" " +
                        "flipH=\"1\" flipV=\"true\" rot=\"3600000\">" +
                        "<a:ext cx=\"1676400\" cy=\"1095375\" />" +
                        "<a:off x=\"6400\" y=\"109\" />" +
                     "</a:xfrm>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlTransform result = DmlTransformReader.readTransform(reader, null);
        // Assert
        msAssert.areEqual(3600000.0, result.getRotation().getValue());
        msAssert.areEqual(FlipOrientation.BOTH, result.getFlipOrientation());
        msAssert.areEqual(1676400.0, result.getWidth());
        msAssert.areEqual(1095375.0, result.getHeight());
        msAssert.areEqual(6400.0, result.getX());
        msAssert.areEqual(109.0, result.getY());
    }

}
