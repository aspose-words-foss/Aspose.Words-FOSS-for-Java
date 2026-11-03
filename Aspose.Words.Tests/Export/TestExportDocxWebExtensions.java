// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ImageUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements tests which checks export of task pane add-ins.
/// </summary>
@Test
public class TestExportDocxWebExtensions
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }

    /// <summary>
    /// WORDSNET-18681 Support for adding dynamic WebExtension element nodes in Word Document.
    /// Appropriate public interface added.
    /// </summary>
    @Test
    public void test18681() throws Exception
    {
        TaskPane taskPane = new TaskPane();
        taskPane.setWidth(350.0);
        taskPane.setRow(4);

        WebExtension webExension = taskPane.getWebExtension();
        webExension.setId("{90416049-4341-4B33-9824-972826A82B7D}");
        webExension.getReference().setId("e504fb41-a92a-4526-b101-542f357b7acb");
        webExension.getReference().setStoreType(WebExtensionStoreType.FILE_SYSTEM);
        webExension.getReference().setStore("\\\\W10-JAVADEV2001\\manifest");
        webExension.getReference().setVersion("1.0.0.0");

        webExension.getProperties().add(new WebExtensionProperty("Office.AutoShowTaskpaneWithDocument", "true"));

        Document doc = new Document();
        doc.getWebExtensionTaskPanes().add(taskPane);

        doc = TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18681", UnifiedScenario.DOCX_2_DOCX);
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getCount());
    }

    /// <summary>
    /// WORDSNET-10593 Add support Web Extensions into the model.
    /// Appropriate public interface added in scope of the WORDSNET-18681
    /// </summary>
    @Test
    public void test10593() throws Exception
    {
        Document doc = TestUtil.open("ExportDocx\\Test10593.docx");

        TaskPane taskPane = new TaskPane();
        taskPane.isVisible(true);
        taskPane.setWidth(350.0);
        taskPane.setRow(3);

        WebExtension webExension = taskPane.getWebExtension();
        webExension.setId("{8FB3EB6F-F75E-4E2C-B57F-27DFDC0CA5FE}");
        webExension.getReference().setId("77086593-cfc7-4abb-8711-085bb2ed3995");
        webExension.getReference().setStoreType(WebExtensionStoreType.FILE_SYSTEM);
        webExension.getReference().setStore("\\\\SHAREPOINT\\Apps");
        webExension.getReference().setVersion("1.0.0.12");

        doc.getWebExtensionTaskPanes().add(taskPane);

        doc = TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test10593", UnifiedScenario.DOCX_2_DOCX);
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getCount());
    }

    /// <summary>
    /// WORDSNET-18634 Provide support for adding dynamic WebExtension element nodes in Word Document.
    /// Appropriate public interface added in scope of the WORDSNET-18681
    /// </summary>
    @Test
    public void test18634() throws Exception
    {
        TaskPane taskPane = new TaskPane();
        taskPane.isVisible(true);
        taskPane.setWidth(350.0);

        WebExtension webExension = taskPane.getWebExtension();
        webExension.setId("{52811C31-4593-43B8-A697-EB873422D156}");
        webExension.getReference().setId("af8fa5ba-4010-4bcc-9e03-a91ddadf6dd3");
        webExension.getReference().setStoreType(WebExtensionStoreType.EX_CATALOG);
        webExension.getReference().setStore("EXCatalog");
        webExension.getReference().setVersion("1.0.0.0");

        Document doc = new Document();
        doc.getWebExtensionTaskPanes().add(taskPane);

        doc = TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18634", UnifiedScenario.DOCX_2_DOCX);
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getCount());
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks export of extension without bindings, properties and etc.
    /// </summary>
    @Test
    public void test18681ExportWiki() throws Exception
    {
        Document doc = createDocumentWithTaskPane("{EF15991F-8C78-4838-B0D8-FABD0105D596}", "wa104099688", "1.3.0.0");
        TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18681ExportWiki", UnifiedScenario.DOCX_2_DOCX);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks export of "snapshot" element.
    /// </summary>
    @Test
    public void test18681ExportSnapShot() throws Exception
    {
        Document doc = createDocumentWithTaskPane("{EF15991F-8C78-4838-B0D8-FABD0105D596}", "wa104099688", "1.3.0.0");

        DmlBlip blip = new DmlBlip();
        blip.setEmbedImage(ImageUtil.getNoImageBytes());
        doc.getWebExtensionTaskPanes().getFirst().getWebExtension().setSnapshot(blip);

        TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18681ExportSnapShot", UnifiedScenario.DOCX_2_DOCX);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks export of custom properties.
    /// </summary>
    @Test
    public void test18681PropertiesExport() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\TestJira12228.docx");

        org.w3c.dom.Document xmlDoc = DocxExportContext.saveAndGetXmlDocument(doc, new OoxmlSaveOptions(), getFirstWebExtensionPart());

        org.w3c.dom.NodeList propertyNodes = xmlDoc.getElementsByTagName("we:property");
        msAssert.areEqual(1, propertyNodes.getLength());
        msAssert.areEqual("mailchimpCampaign", propertyNodes.item(0).getAttributes().getNamedItem("name").getNodeValue());
        msAssert.areEqual("mailchimpCampaign", propertyNodes.item(0).getAttributes().getNamedItem("value").getNodeValue());

        org.w3c.dom.Node referenceNode = xmlDoc.getElementsByTagName("we:reference").item(0);
        msAssert.areEqual("omex", referenceNode.getAttributes().getNamedItem("storeType").getNodeValue());
    }



    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks export to the strict DOCX.
    /// </summary>
    @Test
    public void test18681StrictExport() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681ReadDockState.docx");
        OoxmlSaveOptions so = new OoxmlSaveOptions();
        {
            so.setCompliance(OoxmlCompliance.ISO_29500_2008_STRICT);
            so.setSaveFormat(SaveFormat.DOCX);
        }
        so.setTestMode();

        TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18681StrictExport", UnifiedScenario.DOCX_2_DOCX, so);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks export to a non-OOXML target format.
    /// </summary>
    /// <remarks>
    /// RK FOSS: the warning is gated on <c>!IsOoxmlFormat</c>, so any non-OOXML save format exercises
    /// the same code path. SaveFormat.Doc was removed in the FOSS reduction; SaveFormat.Markdown still
    /// triggers the WebExtensionValidator warning and keeps the regression coverage intact.
    /// </remarks>
    @Test
    public void test18681DocExport() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\WebExtensions\\Test18681ReadDockState.docx");
        TestWarningCallback wc = new TestWarningCallback();
        doc.setWarningCallback(wc);

        TestUtil.executeValidator(doc, SaveFormat.MARKDOWN);

        // Expected warning due to task pane add-in only supported by the OOXML.
        msAssert.isTrue(wc.contains(WarningSource.VALIDATOR, WarningType.DATA_LOSS_CATEGORY,
                WarningStrings.TASK_PANE_ADDINS_WILL_BE_SKIPPED_ON_SAVING));
        // In the model web extension still exists.
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getCount());
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks that just created web extension from the model does not produce corrupted document.
    /// </summary>
    @Test
    public void test18681NotInitializedWebExtensionExport() throws Exception
    {
        Document doc = new Document();
        TaskPane taskPane = new TaskPane();
        doc.getWebExtensionTaskPanes().add(taskPane);

        org.w3c.dom.Document xmlDoc = DocxExportContext.saveAndGetXmlDocument(doc, new OoxmlSaveOptions(), getFirstWebExtensionPart());

        org.w3c.dom.Node reference = xmlDoc.getElementsByTagName("we:reference").item(0);
        msAssert.isEmpty(reference.getAttributes().getNamedItem("id").getNodeValue());
        msAssert.isEmpty(reference.getAttributes().getNamedItem("store").getNodeValue());
        msAssert.isEmpty(reference.getAttributes().getNamedItem("version").getNodeValue());
        msAssert.areEqual("spcatalog", reference.getAttributes().getNamedItem("storeType").getNodeValue());

        org.w3c.dom.Node extension = xmlDoc.getElementsByTagName("we:webextension").item(0);
        msAssert.isEmpty(extension.getAttributes().getNamedItem("id").getNodeValue());
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks that document can be opened after removing of the task pane add-ins.
    /// </summary>
    @Test
    public void test18681RemoveWebExtensions() throws Exception
    {
        Document doc = createDocumentWithTaskPane("{EF15991F-8C78-4838-B0D8-FABD0105D596}", "wa104099688", "1.3.0.0");

        doc = TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18681ExportWiki", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(1, doc.getWebExtensionTaskPanes().getCount());

        doc.getWebExtensionTaskPanes().clear();
        msAssert.areEqual(0, doc.getWebExtensionTaskPanes().getCount());

        TestUtil.saveOpen(doc, "ExportDocxWebExtensions\\Test18681RemoveWebExtensions", UnifiedScenario.DOCX_2_DOCX);
    }

    /// <summary>
    /// Related to WORDSNET-18681
    /// Checks export of two task pane add-ins.
    /// </summary>
    @Test
    public void test18681TwoWebExtensionsExport() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\WebExtensions\\Test18681TwoTaskPanes", UnifiedScenario.DOCX_2_DOCX);
        msAssert.areEqual(2, doc.getWebExtensionTaskPanes().getCount());
    }

    /// <summary>
    /// Checks content of the binding node.
    /// </summary>
    private static void assertBindingMarkup(org.w3c.dom.Node bindingNode, WebExtensionBinding expectedBinding)
    {
        msAssert.areEqual(expectedBinding.getId(), bindingNode.getAttributes().getNamedItem("id").getNodeValue());
        msAssert.areEqual(expectedBinding.getAppRef(), bindingNode.getAttributes().getNamedItem("appref").getNodeValue());
        msAssert.areEqual(DocxEnum.webExtensionBindingTypeToDocx(expectedBinding.getBindingType()), bindingNode.getAttributes().getNamedItem("type").getNodeValue());

        msAssert.areEqual(3, bindingNode.getAttributes().getLength());
    }

    /// <summary>
    /// Creates document and inserts task pane add-in.
    /// </summary>
    private static Document createDocumentWithTaskPane(String webExtensionId, String reference, String version) throws Exception
    {
        Document doc = new Document();

        TaskPane taskPane = new TaskPane();
        taskPane.isVisible(true);
        taskPane.setWidth(350d);
        taskPane.setRow(7);

        taskPane.getWebExtension().setId(webExtensionId);
        fillReference(taskPane.getWebExtension().getReference(), reference, version);

        doc.getWebExtensionTaskPanes().add(taskPane);

        return doc;
    }

    /// <summary>
    /// Fills passed reference with specified parameters.
    /// </summary>
    private static void fillReference(WebExtensionReference reference, String id, String version)
    {
        reference.setId(id);
        reference.setVersion(version);

        reference.setStore("en-US");
        reference.setStoreType(WebExtensionStoreType.OMEX);
    }

    /// <summary>
    /// Path to "webextension" part with index two.
    /// </summary>
    private String getSecondWebExtensionPart() { return msString.format(M_WEB_EXTENSION_PART_NAME, 2); }

    /// <summary>
    /// Path to "webextension" part with index one.
    /// </summary>
    private String getFirstWebExtensionPart() { return msString.format(M_WEB_EXTENSION_PART_NAME, 1); }

    /// <summary>
    /// Path pattern to "webextension" part.
    /// </summary>
    private static /*final*/ String M_WEB_EXTENSION_PART_NAME = "word/webextensions/webextension{0}.xml";
}

