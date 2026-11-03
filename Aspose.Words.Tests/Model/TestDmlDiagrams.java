// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/29/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.StringToObjDictionary;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


@Test (enabled = false, description = "TestDmlDiagrams") // todo: remove explicit when writing diagrams implemented.
public class TestDmlDiagrams
{
    @BeforeClass (alwaysRun = true, enabled = false)
    public void setUp()
    {
        TestUtil.setUpTests();
    }












    @Test (enabled = false)
    public void testListDiagrams() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Diagrams\\TestListDiagrams.docx");
    }




    @Test (enabled = false)
    public void testRelationshipDiagrams() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Diagrams\\TestRelationshipDiagrams.docx");
    }



    @Test (enabled = false)
    public void testDiagramMath() throws Exception
    {
        TestUtil.openSaveOpen("Model\\Diagrams\\TestDiagramMath.docx");
    }



    /// <summary>
    /// WORDSNET-12476 Shape's hyperlink is removed after re-saving Docx.
    /// The 'dgm:extLst' element was not supported for diagram point.
    /// </summary>
    @Test (enabled = false)
    public void testJira12476() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Diagrams\\TestJira12476", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        DmlDiagram diagram = (DmlDiagram)doc.getFirstSection().getBody().getShapes().get(0).getDmlNode();

        StringToObjDictionary<DmlExtension> extensions = diagram.getData().getPointList()[1].getExtensions();
        msAssert.areEqual(2, extensions.getCount());

        DmlExtension extension0 = extensions.get("{A40237B7-FDA0-4F09-8148-C483321AD2D9}");
        msAssert.isNull(extension0.getNvPr());
        msAssert.isNotNull(extension0.getXmlDoc());

        DmlExtension extension1 = extensions.get("{E40237B7-FDA0-4F09-8148-C483321AD2D9}");
        DmlNvDrawingProperties nvPr = extension1.getNvPr();
        msAssert.isNotNull(nvPr);
        msAssert.isNull(extension1.getXmlDoc());
        msAssert.areEqual("http://www.rossmanith.com", nvPr.getHlinkClick().getId());
        msAssert.areEqual("http://www.hlinkhover.com", nvPr.getHlinkHover().getId());
    }

    /// <summary>
    /// WORDSNET-13319 Corrupt output when using Aspose to save MS Word document containing SmartArt.
    /// The problem occurs because DmlPointsTextSpacing values was written incorrectly.
    /// </summary>
    @Test (enabled = false)
    public void testJira13319() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Diagrams\\TestJira13319", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        DmlDiagram diagram = (DmlDiagram)doc.getFirstSection().getBody().getShapes().get(0).getDmlNode();
        DmlDiagramPoint dmlDiagramPoint = diagram.getData().getPointList()[7];
        DmlParagraph dmlParagraph = (DmlParagraph)dmlDiagramPoint.getTextBody().getParagraphs().get(3);
        msAssert.areEqual(336, ((DmlPointsTextSpacing)dmlParagraph.getProperties().getSpaceAfter()).getValue().getValue());
    }

    /// <summary>
    /// WORDSNET-14129 The recolorImg extension of the dataModel element is lost after re-saving a document.
    /// Fixed by adding mechanism to read/write data model extensions.
    /// </summary>
    @Test (enabled = false)
    public void testJira14129() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Diagrams\\TestJira14129", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        DocxExportContext context = new DocxExportContext(doc, "word/diagrams/data1.xml", OoxmlComplianceCore.ISO_TRANSITIONAL);

        org.w3c.dom.Element parent = (org.w3c.dom.Element)context.getXmlNode(msString.format("//a:ext[@uri = \"{0}\"]", DmlExtensionUri.RECOLOR_IMG.toUpperCase()));
        msAssert.isNotNull(parent);

        org.w3c.dom.Element node = (org.w3c.dom.Element)parent.getFirstChild();
        msAssert.isNotNull(node);

        String xPath = "//dgm14:recolorImg";

        msAssert.isNotNull(node, msString.format("Cannot find the node '{0}'.", xPath));
        msAssert.areEqual("1", node.getAttributes().item(0).getNodeValue(), msString.format("Value of the node '{0}' is wrong.", xPath));
    }

    /// <summary>
    /// WORDSNET-14137 Effect list is not supported in diagram background.
    /// Fixed by adding mechanism to read/write data model effect list.
    /// </summary>
    @Test (enabled = false)
    public void testJira14137() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\TestJira13384", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        Shape node = (Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 2, true);
        DmlShapeEffectsCollection effectsCollection = ((DmlDiagram)node.getGraphicData()).getData().getBackground().getEffects();
        msAssert.areEqual(1, effectsCollection.getCount());
        DmlShapeEffect dmlShapeEffect = effectsCollection.getByDmlShapeEffectType(DmlShapeEffectType.OUTER_SHADOW);
        msAssert.isNotNull(dmlShapeEffect);
    }
}

