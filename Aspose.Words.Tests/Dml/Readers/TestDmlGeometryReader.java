// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;


/// <summary>
/// Tests for <see cref="DmlGeometryReader"/>.
/// </summary>
@Test
public class TestDmlGeometryReader
{
    @Test
    public void build_GeometryWithAdjustableValues_GuidesInitialized()
    {
        // Arrange
        String xml =
            "<a:custGeom xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:avLst>" +
                    "<a:gd name=\"myGuide\" fmla=\"val 2\"/>" +
                "</a:avLst>" +
            "</a:custGeom>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlGeometry result = DmlGeometryReader.read(reader, null);
        // Assert
        DmlGuides guides = result.getGuides();
        msAssert.areEqual(1, guides.getAdjustableValues().size());
        DmlGuide guide = guides.getAdjustableValues().get(0);
        msAssert.areEqual("myGuide", guide.getName());
        msAssert.isTrue(guide.getFormula() instanceof DmlOneArgumentFormula);
    }

    @Test
    public void build_PresetGeometry_PresetGeometryReaded()
    {
        String xml = "<prstGeom prst=\"moon\"/>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlGeometry result = DmlGeometryReader.read(reader, null);
        // Assert
        msAssert.areEqual("moon", result.getPresetName());
        DmlGuides guides = result.getGuides();
        msAssert.areEqual(1, guides.getAdjustableValues().size());
        DmlGuide guide = guides.getAdjustableValues().get(0);
        msAssert.areEqual("adj", guide.getName());
        msAssert.isTrue(guide.getFormula() instanceof DmlOneArgumentFormula);
    }

    @Test
    public void build_PresetGeometryWithAdjustableValues_AdjustableValuesShouldBeReplacedAfterCalculation()
    {
        String xml =
            "<a:prstGeom prst=\"moon\" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:avLst xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                    "<a:gd name=\"myGuide\" fmla=\"val 100\"/>" +
                "</a:avLst>" +
            "</a:prstGeom>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlGeometry geom = DmlGeometryReader.read(reader, null);
        DmlGuides guides = geom.getGuides();
        guides.calculate(100.0, 100.0);
        double result = guides.getValue("myGuide");
        // Assert
        msAssert.areEqual(100, result);
    }

    @Test
    public void build_GeometryWithGuides_GuidesInitialized()
    {
        // Arrange
        String xml =
            "<a:custGeom xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:gdLst>" +
                    "<a:gd name=\"myGuide\" fmla=\"val 2\"/>" +
                "</a:gdLst>" +
            "</a:custGeom>";
        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlGeometry result = DmlGeometryReader.read(reader, null);
        // Assert
        DmlGuides guides = result.getGuides();
        msAssert.areEqual(1, guides.getGuides().size());
        DmlGuide guide = guides.getGuides().get(0);
        msAssert.areEqual("myGuide", guide.getName());
        msAssert.isTrue(guide.getFormula() instanceof DmlOneArgumentFormula);
    }

    @Test
    public void build_PathDefined_ShapeHasPath()
    {
        // Arrange
        String xml =
            "<a:custGeom xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:pathLst>" +
                    "<a:path>" +
                    "</a:path>" +
                "</a:pathLst>" +
            "</a:custGeom>";

        NrxXmlReader reader = new NrxXmlReader(xml, null);
        // Act
        DmlGeometry result = DmlGeometryReader.read(reader, null);
        // Assert
        msAssert.areEqual(1, result.getPaths().size());
        msAssert.isNotNull(result.getPaths().get(0));
    }

    /// <summary>
    /// WORDSNET-14145 Unsupported DML element cxnLst.  
    /// Test checks that reader fills connection sites into the model.
    /// </summary>
    @Test
    public void build_GeometryWithSimpleConnectionSite()
    {   
        // Prepare input data.
        String xml =
            "<a:custGeom xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:avLst/>" +
                "<a:gdLst/>" +
                "<a:ahLst/>" +
                "<a:cxnLst>" +
                    "<a:cxn ang=\"0\">" +
                        "<a:pos x=\"707786\" y=\"507786\"/>" +
                    "</a:cxn>" +
                "</a:cxnLst>" +
            "</a:custGeom>";

        // Perform reading.
        NrxXmlReader reader = new NrxXmlReader(xml, null);            
        DmlGeometry result = DmlGeometryReader.read(reader, null);

        // Check results.
        ArrayList<DmlConnectionSite> sites = result.getConnectionSites();
        DmlConnectionSite site = sites.get(0);

        msAssert.areEqual(1, sites.size());
        msAssert.areEqual("0", site.getAngle().getString());
        msAssert.areEqual("707786", site.getCoordinates().getX().getString());
        msAssert.areEqual("507786", site.getCoordinates().getY().getString());
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14145
    /// Checks that connection sites are validated after reading and
    /// invalid sites were skipped while reading.
    /// </summary>
    @Test
    public void build_GeometryWithConnectionSiteReferencedToGuide()
    {
        // Prepare input data.
        String xml =                
            "<a:custGeom xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:avLst>"  +
                    "<a:gd name=\"myXGuide\" fmla=\"val 473660\"/>" +
                    "<a:gd name=\"myAngle\" fmla=\"val 16200000\"/>" +
                "</a:avLst>" +
                "<a:gdLst>" +
                    "<a:gd name=\"myYGuide\" fmla=\"val 873660\"/>" +
                "</a:gdLst>" +
                "<a:ahLst/>" +
                "<a:cxnLst>" +
                    "<a:cxn ang=\"MissedAngleGuide\">" +
                        "<a:pos x=\"707786\" y=\"507786\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"myAngle\">" +
                        "<a:pos x=\"MissaedXGuide\" y=\"507786\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"0\">" +
                        "<a:pos x=\"myXGuide\" y=\"MissaedYGuide\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"myAngle\">" +
                        "<a:pos x=\"myXGuide\" y=\"myYGuide\"/>" +
                    "</a:cxn>" +
                "</a:cxnLst>" +
            "</a:custGeom>";

        // Perform reading.     
        TestWarningCallback warningCallback = new TestWarningCallback();
        NrxXmlReader reader = new NrxXmlReader(xml, null, warningCallback);
        DmlGeometry result = DmlGeometryReader.read(reader, null);
 
        // Check results.
        ArrayList<DmlConnectionSite> sites = result.getConnectionSites();
        DmlConnectionSite site = sites.get(0);

        msAssert.areEqual(1, sites.size());
        msAssert.areEqual("myAngle", site.getAngle().getString());
        msAssert.areEqual("myXGuide", site.getCoordinates().getX().getString());
        msAssert.areEqual("myYGuide", site.getCoordinates().getY().getString());

        msAssert.areEqual(3, warningCallback.getCount());
        checkWarnings(warningCallback);      
    }

    /// <summary>
    /// Test is concerned with WORDSNET-14145
    /// Checks that in case when attributes of the sites and child elements are missed or empty
    /// then such sites were skipped while reading.
    /// </summary>
    @Test
    public void build_GeometryWithConnectionSiteMissedAttributes()
    {
        // Prepare input data.
        String xml =
            "<a:custGeom xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "<a:avLst>" +
                    "<a:gd name=\"myXGuide\" fmla=\"val 473660\"/>" +
                    "<a:gd name=\"myAngle\" fmla=\"val 16200000\"/>" +
                "</a:avLst>" +
                "<a:gdLst>" +
                    "<a:gd name=\"myYGuide\" fmla=\"val 873660\"/>" +
                "</a:gdLst>" +
                "<a:ahLst/>" +
                "<a:cxnLst>" +
                    "<a:cxn>" +
                        "<a:pos x=\"707786\" y=\"507786\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"\">" +
                        "<a:pos x=\"myXGuide\" y=\"507786\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"0\">" +
                        "<a:pos y=\"507786\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"myAngle\">" +
                        "<a:pos x=\"\" y=\"myYGuide\"/>" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"0\">" +
                        "<a:pos x=\"707786\" />" +
                    "</a:cxn>" +
                    "<a:cxn ang=\"0\">" +
                        "<a:pos x=\"707786\" y=\"\" z=\"\"/>" +
                    "</a:cxn>" +
                "</a:cxnLst>" +
            "</a:custGeom>";

        // Perform reading.
        TestWarningCallback warningCallback = new TestWarningCallback();
        NrxXmlReader reader = new NrxXmlReader(xml, null, warningCallback);
        DmlGeometry result = DmlGeometryReader.read(reader, null);

        // Check results.
        ArrayList<DmlConnectionSite> sites = result.getConnectionSites();
     
        msAssert.areEqual(0, sites.size());
        msAssert.areEqual(6, warningCallback.getCount());
        checkWarnings(warningCallback);
    }

    /// <summary>
    /// Checks the registered warnings while geometry reading.
    /// </summary>
    /// <param name="warningCallback">Object with collected warnings.</param>
    private static void checkWarnings(TestWarningCallback warningCallback)
    {
        for (int i = 0; i < warningCallback.getCount(); ++i)
        {
            msAssert.areEqual(WarningSource.DRAWING_ML, warningCallback.get(i).getSource());
            msAssert.areEqual(WarningType.UNEXPECTED_CONTENT, warningCallback.get(i).getWarningType());
            msAssert.areEqual(WarningStrings.UNACCEPTABLE_MARKUP, warningCallback.get(i).getDescription());
        }   
    }
}

