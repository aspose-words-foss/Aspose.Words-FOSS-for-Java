// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.FormatterPal;


/// <summary>
/// Tests for <see cref="DmlPathReader"/>.
/// </summary>
@Test
public class TestDmlPathReader
{
    private Object getPathPart(String xml)
    {
        // Arrange
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath result = DmlPathReader.read(reader, null);
        // Assert
        msAssert.areEqual(1, result.getPathParts().size());

        return result.getPathParts().get(0);
    }

    private void checkMoveToPoint(String xml, String x, String y)
    {
        DmlMoveTo result = buildMoveTo(xml);
        checkPoint(result.getPoint(), x, y);
    }

    private static void checkPoint(DmlAdjustablePoint point, String x, String y)
    {
        msAssert.areEqual(x, point.getX().getString());
        msAssert.areEqual(y, point.getY().getString());
    }

    private void checkMoveToPoint(String xml, double x, double y)
    {
        DmlMoveTo result = buildMoveTo(xml);
        checkPoint(result.getPoint(), FormatterPal.doubleToStr(x), FormatterPal.doubleToStr(y));
    }

    private DmlMoveTo buildMoveTo(String xml)
    {
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath path = DmlPathReader.read(reader, null);
        return (DmlMoveTo)path.getPathParts().get(0);
    }

    @Test
    public void build_PathWithAttributes_AttributesReaded()
    {
        // Arrange
        String xml =
            "<a:path w=\"101\" h=\"102\" stroke=\"false\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath result = DmlPathReader.read(reader, null);
        // Assert
        msAssert.areEqual(101, result.getWidth());
        msAssert.areEqual(102, result.getHeight());
        msAssert.areEqual(false, result.getStroke());
    }
    @Test
    public void build_BadXml_NullReturned()
    {
        // Arrange
        String xml =
            "<a:Badpath xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "</a:Badpath>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath result = DmlPathReader.read(reader, null);
        // Assert
        msAssert.isNull(result);
    }

    @Test
    public void build_LineToWithXandYDefined_PointReadedSuccesefuly()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:lnTo> " +
            "<a:pt x=\"77\" y=\"66\"/>" +
            "</a:lnTo> " +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath path = DmlPathReader.read(reader, null);
        // Assert
        DmlLineTo result = (DmlLineTo)path.getPathParts().get(0);
        msAssert.areEqual("77", result.getPoint().getX().getString());
        msAssert.areEqual("66", result.getPoint().getY().getString());
    }

    @Test
    public void build_MoveToWithXandYDefined_PointReadedSuccesefuly()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:moveTo> " +
            "<a:pt x=\"77\" y=\"66\"/>" +
            "</a:moveTo> " +
            "</a:path>";
        checkMoveToPoint(xml, 77.0, 66.0);
    }

    @Test
    public void build_MoveToWithXDefined_PointReadedSuccesefuly()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:moveTo> " +
            "<a:pt x=\"77\" />" +
            "</a:moveTo> " +
            "</a:path>";
        checkMoveToPoint(xml, 77.0, 0.0);
    }

    @Test
    public void build_MoveToWithXYDefinedAsGuideName_PointReadedSuccesefuly()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:moveTo> " +
            "<a:pt x=\"abc\" y=\"def\" />" +
            "</a:moveTo> " +
            "</a:path>";
        checkMoveToPoint(xml, "abc", "def");
    }

    @Test
    public void build_MoveToWithYDefined_PointReadedSuccesefuly()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:moveTo> " +
            "<a:pt y=\"77\" />" +
            "</a:moveTo> " +
            "</a:path>";
        checkMoveToPoint(xml, 0.0, 77.0);
    }

    @Test
    public void build_PathDefined_WidthAndHeightSetted()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" w=\"2824222\" h=\"590309\">" +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath result = DmlPathReader.read(reader, null);
        // Assert
        msAssert.areEqual(2824222.0, result.getWidth());
        msAssert.areEqual(590309.0, result.getHeight());
    }

    @Test
    public void build_PathWithArcTo_ArcToAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:arcTo/> " +
            "</a:path>";
        // Assert
        msAssert.isTrue(getPathPart(xml) instanceof DmlArcTo);
    }

    @Test
    public void build_PathWithArcTo_ArcToPropertiesDefined()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:arcTo hR=\"12556\" wR=\"trololo\" stAng=\"path\" swAng=\"34958\" /> " +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath path = DmlPathReader.read(reader, null);
        DmlArcTo result = (DmlArcTo)path.getPathParts().get(0);
        // Assert
        msAssert.areEqual("12556", result.getHeightRadius().getString());
        msAssert.areEqual("trololo", result.getWidthRadius().getString());
        msAssert.areEqual("34958", result.getSwingAngle().getString());
        msAssert.areEqual("path", result.getStartAngle().getString());
    }

    @Test
    public void build_PathWithClose_CloseAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:close/> " +
            "</a:path>";
        // Assert
        msAssert.isTrue(getPathPart(xml) instanceof DmlClose);
    }

    @Test
    public void build_PathWithCubicBezTo_CubicBezierToAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:cubicBezTo/> " +
            "</a:path>";
        // Assert
        msAssert.isTrue(getPathPart(xml) instanceof DmlCubicBezierTo);
    }

    @Test
    public void build_PathWithCubicBezToWithoutPoints_CubicBezToInitialized()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:cubicBezTo> " +
            "<a:pt x=\"77\" y=\"66\"/>" +
            "<a:pt x=\"88\" y=\"99\"/>" +
            "<a:pt x=\"1\" y=\"42\"/>" +
            "</a:cubicBezTo> " +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath path = DmlPathReader.read(reader, null);
        DmlCubicBezierTo result = (DmlCubicBezierTo)path.getPathParts().get(0);
        // Assert
        msAssert.areEqual("77", result.getControlPoint1().getX().getString());
        msAssert.areEqual("66", result.getControlPoint1().getY().getString());
        msAssert.areEqual("88", result.getControlPoint2().getX().getString());
        msAssert.areEqual("99", result.getControlPoint2().getY().getString());
        msAssert.areEqual("1", result.getEndPoint().getX().getString());
        msAssert.areEqual("42", result.getEndPoint().getY().getString());
    }

    @Test
    public void build_PathWithLineTo_LineToAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:lnTo/> " +
            "</a:path>";
        // Assert
        msAssert.isTrue(getPathPart(xml) instanceof DmlLineTo);
    }

    @Test
    public void build_PathWithMoveTo_MoveToAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:moveTo/> " +
            "</a:path>";
        // Assert
        msAssert.isTrue(getPathPart(xml) instanceof DmlMoveTo);
    }

    @Test
    public void build_PathWithQuadBezTo_QuadraticBezierToAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:quadBezTo/> " +
            "</a:path>";
        // Assert
        msAssert.isTrue(getPathPart(xml) instanceof DmlQuadraticBezierTo);
    }

    @Test
    public void build_PathWithQuadBezToWithoutPoints_QuadBezToInidialized()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:quadBezTo> " +
            "<a:pt x=\"77\" y=\"66\"/>" +
            "<a:pt x=\"88\" y=\"99\"/>" +
            "</a:quadBezTo> " +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath path = DmlPathReader.read(reader, null);
        DmlQuadraticBezierTo result = (DmlQuadraticBezierTo)path.getPathParts().get(0);
        // Assert
        msAssert.areEqual("77", result.getControlPoint().getX().getString());
        msAssert.areEqual("66", result.getControlPoint().getY().getString());
        msAssert.areEqual("88", result.getEndPoint().getX().getString());
        msAssert.areEqual("99", result.getEndPoint().getY().getString());
    }

    @Test
    public void build_PathWithSeveralParts_AllPartsAdded()
    {
        // Arrange
        String xml =
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "<a:moveTo/> " +
            "<a:lnTo/> " +
            "<a:lnTo/> " +
            "<a:close/> " +
            "</a:path>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath result = DmlPathReader.read(reader, null);
        // Assert
        msAssert.areEqual(4, result.getPathParts().size());
    }

    @Test
    public void build_FillNotSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>", 
            DmlPathFillMode.NORM);
    }

    private void checkFillProperty(String xml, /*DmlPathFillMode*/int expected)
    {
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlPath result = DmlPathReader.read(reader, null);
        // Assert
        msAssert.areEqual(expected, result.getFillMode());
    }

    @Test
    public void build_FillNoneSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"none\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>", 
            DmlPathFillMode.NONE);
    }

    @Test
    public void build_FillNormSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"norm\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>",
            DmlPathFillMode.NORM);
    }

    @Test
    public void build_FillDarkenSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"darken\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>",
            DmlPathFillMode.DARKEN);
    }

    @Test
    public void build_FillDarkenLessSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"darkenLess\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>",
            DmlPathFillMode.DARKEN_LESS);
    }

    @Test
    public void build_FillLightenSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"lighten\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>",
            DmlPathFillMode.LIGHTEN);
    }

    @Test
    public void build_FillLightenLessSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"lightenLess\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>",
            DmlPathFillMode.LIGHTEN_LESS);
    }

    @Test
    public void build_BadFillSpecified_PropertyInitialized()
    {
        checkFillProperty(
            "<a:path fill=\"sadf\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"/>",
            DmlPathFillMode.NORM);
    }
}
