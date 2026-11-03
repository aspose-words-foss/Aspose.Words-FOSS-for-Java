// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.AnyXmlReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Xml.XmlTextReader;
import com.aspose.ms.System.IO.TextReader;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ArrayUtil;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.Text.Encoding;
import java.util.ArrayList;
import com.aspose.Language;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.BitConverter;
import com.aspose.ms.System.Convert;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests mapping of a structured document tag to XML data.
/// </summary>
@Test
public class TestSdtXmlMapping
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests that a correct dataBinding element is written on mapping a rich-text SDT to XML data.
    /// </summary>
    @Test
    public void testMappingToRichTextSdt() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().insert(tag, null, false);

        tag.getXmlMapping().setMapping(doc.getCustomXmlParts().add("C8FFD3B6-7EF8-4963-895B-3565F68A03E7",
            "<root><text></text></root>"), "/root/text", "");

        Document doc2 = doc.deepClone();
        doc2.setComplianceInfo(new OoxmlComplianceInfo());

        // Check that the necessary ISO Transitional format and Compatibility Mode are set.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        msAssert.isTrue(tag.isDocxExtension());
        msAssert.isTrue(tag.getXmlMapping().isDocx15Extension());
        msAssert.isTrue(doc.getComplianceInfo().isDocxExtensions());
        msAssert.areEqual(OoxmlComplianceCore.ISO_TRANSITIONAL, doc.getComplianceInfo().getCompliance());
        msAssert.areEqual(MsWordVersionCore.WORD_2013, doc.getCompatibilityOptions().getMswVersion());

        // Check that if customer has explicitly set OOXML format and Compatibility Mode, they are not changed
        // on writing XML Mapping.
        doc2.getCompatibilityOptions().optimizeFor(MsWordVersion.WORD_2010);
        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions();
        saveOptions.setCompliance(OoxmlCompliance.ECMA_376_2006);
        TestUtil.executeValidator(doc2, saveOptions);

        msAssert.isFalse(doc2.getComplianceInfo().isDocxExtensions());
        msAssert.areEqual(OoxmlComplianceCore.ECMA_376, doc2.getComplianceInfo().getCompliance());
        msAssert.areEqual(MsWordVersionCore.WORD_2010, doc2.getCompatibilityOptions().getMswVersion());

        // Check that if customer has explicitly set OOXML format, incompatible XML Mapping is not written
        // and a warning is generated.
        TestWarningCallback warnings = new TestWarningCallback();
        doc2.setWarningCallback(warnings);
        doc2 = TestUtil.saveOpen(doc2, "Model\\Markup\\TestMappingToRichTextSdt.docx", saveOptions, false);

        tag = (StructuredDocumentTag)doc2.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.isTrue(tag.getXmlMapping().isEmpty());
        msAssert.isTrue(warnings.contains(WarningSource.DOCX, WarningType.DATA_LOSS,
                WarningStrings.ECMA_376_NOT_SUPPORTED_XML_MAPPING));
    }

    /// <summary>
    /// Tests whether an exception occurs on mapping a SDT of not supported type to XML data.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testMappingToBuildingBlockGallerySdt() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.BUILDING_BLOCK_GALLERY, MarkupLevel.BLOCK);
        tag.getXmlMapping().setMapping(doc.getCustomXmlParts().add("1", "<XMLNode/>"), "TestXPath", "TestPrefixes");
    }

    /// <summary>
    /// Tests whether an exception occurs if no XPath is defined on mapping to XML data.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testNoXPath() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.INLINE);
        tag.getXmlMapping().setMapping(doc.getCustomXmlParts().add("1", "<XMLNode/>"), "", "TestPrefixes");
    }

    /// <summary>
    /// Tests whether an exception occurs if no custom XML part is specified on mapping to XML data.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void testNoCustomXmlPart() throws Exception
    {
        StructuredDocumentTag tag = new StructuredDocumentTag(new Document(), SdtType.PLAIN_TEXT, MarkupLevel.INLINE);
        tag.getXmlMapping().setMapping((CustomXmlPart)null, "TestXPath", "TestPrefixes");
    }

    /// <summary>
    /// Tests mapping to correct and wrong XPath.
    /// </summary>
    @Test
    public void testMapping() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10270.docx");
        StructuredDocumentTag tag = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        msAssert.isFalse(tag.getXmlMapping().setMapping(doc.getCustomXmlParts().get(0), "/root/bad_path", "xmlns:ns='Namespace'"));
        checkMapping(tag, doc.getCustomXmlParts().get(0), "/root/bad_path", "xmlns:ns='Namespace'", false);

        msAssert.isTrue(tag.getXmlMapping().setMapping(doc.getCustomXmlParts().get(0), "/root/date", "xmlns:ns='Namespace'"));
        checkMapping(tag, doc.getCustomXmlParts().get(0), "/root/date", "xmlns:ns='Namespace'", true);
    }

    /// <summary>
    /// Checks data of the <see cref="StructuredDocumentTag.XmlMapping"/> property of the specified structured
    /// document tag.
    /// </summary>
    private static void checkMapping(StructuredDocumentTag tag, CustomXmlPart expectedPart, String expectedXPath,
        String expectedPrefixes, boolean expectedIsMapped) throws Exception
    {
        msAssert.areEqual(expectedIsMapped, tag.getXmlMapping().isMapped());
        msAssert.isFalse(tag.getXmlMapping().isEmpty());
        msAssert.areSame(expectedPart, tag.getXmlMapping().getCustomXmlPart());
        msAssert.areEqual(expectedXPath, tag.getXmlMapping().getXPath());
        msAssert.areEqual(expectedPrefixes, tag.getXmlMapping().getPrefixMappings());
    }

    /// <summary>
    /// Tests mapping to correct and wrong XPath.
    /// </summary>
    @Test
    public void testMappingByStoreItemId() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10270.docx");
        StructuredDocumentTag tag = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        tag.getXmlMapping().setMapping(doc.getCustomXmlParts().get(0).getId(), "/root/bad_path", "xmlns:ns='Namespace'");
        checkMapping(tag, doc.getCustomXmlParts().get(0), "/root/bad_path", "xmlns:ns='Namespace'", false);

        tag.getXmlMapping().setMapping(doc.getCustomXmlParts().get(0).getId(), "/root/date", "xmlns:ns='Namespace'");
        checkMapping(tag, doc.getCustomXmlParts().get(0), "/root/date", "xmlns:ns='Namespace'", true);
    }

    /// <summary>
    /// Tests cloning XML mapping on cloning a document.
    /// </summary>
    @Test
    public void testCloningXmlMapping() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10270.docx");
        Document clonedDoc = doc.deepClone();
        StructuredDocumentTag clonedTag =
            (StructuredDocumentTag)clonedDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        msAssert.areNotSame(clonedTag.getXmlMapping().getCustomXmlPart(), doc.getCustomXmlParts().get(0));
        checkMapping(clonedTag, clonedDoc.getCustomXmlParts().get(0), "/root[1]/date", "", true);
    }

    /// <summary>
    /// Tests getting and setting value of a SDT that is mapped to a custom XML part.
    /// </summary>
    @Test
    public void testGettingSettingValue() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10270.docx");
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        msAssert.areEqual("2013-04-02T06:52:20.9977400-06:00", sdt.getXmlMapping().getValue());

        final String NEW_VALUE = "2017-06-30T15:30";
        sdt.getXmlMapping().setValue(NEW_VALUE);

        CustomXmlPart customXmlPart = doc.getCustomXmlParts().getById(sdt.getXmlMapping().getStoreItemId());

        AnyXmlReader xmlReader = new AnyXmlReader(new MemoryStream(customXmlPart.getData()));
        msAssert.areEqual("root", xmlReader.getLocalName());
        xmlReader.readChild("root");
        msAssert.areEqual("date", xmlReader.getLocalName());
        msAssert.areEqual(NEW_VALUE, xmlReader.readString());
    }

    /// <summary>
    /// Tests getting and setting value of a SDT that is mapped to a document core property.
    /// </summary>
    @Test
    public void testGettingSettingValueFromToProperty() throws Exception
    {
        Document doc = new Document();

        doc.getBuiltInDocumentProperties().get(PropertyName.COMMENTS).setValue("Test Comments"); // core property
        doc.getBuiltInDocumentProperties().get(PropertyName.COMPANY).setValue("Test Company");   // extended property

        // Insert a SDT and map to the core property.
        StructuredDocumentTag sdt1 = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(sdt1);
        sdt1.getXmlMapping().setMapping("{6C3C8BC8-F283-45AE-878A-BAB7291924A1}",
            "/ns1:coreProperties[1]/ns0:description[1]",
            "xmlns:ns0='http://purl.org/dc/elements/1.1/' " +
            "xmlns:ns1='http://schemas.openxmlformats.org/package/2006/metadata/core-properties'");

        // Insert a SDT and map to the extended property.
        StructuredDocumentTag sdt2 = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(sdt2);
        sdt2.getXmlMapping().setMapping("{6668398D-A668-4E3E-A5EB-62B293D839F1}", "/ns0:Properties[1]/ns0:Company[1]",
            "xmlns:ns0='http://schemas.openxmlformats.org/officeDocument/2006/extended-properties'");

        msAssert.areEqual("Test Comments", sdt1.getXmlMapping().getValue());
        msAssert.areEqual("Test Company", sdt2.getXmlMapping().getValue());

        sdt1.getXmlMapping().setValue("New Comments");
        msAssert.areEqual("New Comments", doc.getBuiltInDocumentProperties().get(PropertyName.COMMENTS).getValue());

        sdt2.getXmlMapping().setValue("New Company");
        msAssert.areEqual("New Company", doc.getBuiltInDocumentProperties().get(PropertyName.COMPANY).getValue());
    }

    /// <summary>
    /// WORDSNET-16888 Document.UpdatePageLayout does not stop.
    /// Mapped SDT data contains another SDT which references to itself. This cyclic
    /// reference causes the problem. Word maps data for the topmost SDT and skips
    /// nested mapping at this case. Mimic Word behavior to fix the problem.
    /// </summary>
    @Test
    public void testJira16888() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira16888.docx");

        StructuredDocumentTag outerSdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getFirstChild();

        msAssert.areEqual(2, outerSdt.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, outerSdt.getChildNodes(NodeType.ANY, false).get(0).getNodeType());

        StructuredDocumentTag innerSdt = (StructuredDocumentTag)outerSdt.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(2, innerSdt.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(NodeType.PARAGRAPH, innerSdt.getFirstChild().getNodeType());
        msAssert.areEqual(NodeType.PARAGRAPH, innerSdt.getLastChild().getNodeType());
    }



    /// <summary>
    /// WORDSNET-20775 StructuredDocumentTag.Checked throws InvalidOperationException (Custom XML part is not found)
    /// Mimic Word and skip custom xml update if mapping is present but invalid.
    /// </summary>
    @Test
    public void test20775() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20775.docx");

        StructuredDocumentTag sdt = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true), StructuredDocumentTag.class);
        msAssert.isFalse(sdt.getChecked());
        msAssert.isFalse(sdt.getContentChecked());

        sdt.setChecked(true);

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test20775", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        sdt = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true), StructuredDocumentTag.class);

        msAssert.areEqual(true, sdt.getChecked());
        msAssert.areEqual(true, sdt.getContentChecked());
    }

    /// <summary>
    /// WORDSNET-22082 storeItemChecksum get/set method.
    /// Check the DataChecksum value for the opened document.
    /// </summary>
    @Test
    public void test22082_Existing() throws Exception
    {
        long expChecksum = 0xed5B91F2L;

        Document doc = TestUtil.open("Model\\Markup\\Test22082.docx");
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(expChecksum, sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());

        // Check value after roundtrip.
        doc = TestUtil.saveOpen(doc, "Test22082_Existing", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(expChecksum, sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());
    }

    /// <summary>
    /// WORDSNET-22082 storeItemChecksum get/set method.
    /// Check the DataChecksum value for the newly created sdt and after the mapping change.
    /// </summary>
    @Test
    public void test22082_Created() throws Exception
    {
        Document doc = new Document();

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().insert(sdt, null, false);

        sdt.getXmlMapping().setMapping(doc.getCustomXmlParts().add("C8FFD3B6-7EF8-4963-895B-3565F68A03E7",
            "<root><text>test</text></root>"), "/root/text", "");
        long expChecksum = 0x981484e0L;
        msAssert.areEqual(expChecksum, sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());

        sdt.getXmlMapping().setMapping(doc.getCustomXmlParts().add(("702E275F-454A-4A40-B627-53086ABDE80F"),
            "<root><text>changed test</text></root>"), "/root/text", "");
        expChecksum = 0xc900abc0L;
        msAssert.areEqual(expChecksum, sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());
    }

    /// <summary>
    /// WORDSNET-22082 storeItemChecksum get/set method.
    /// Check the DataChecksum value for the opened document and then after the CustomXmlPart.Data change.
    /// </summary>
    @Test
    public void test22082_Changed() throws Exception
    {
        // Check the StoreItemChecksum value for the opened document.
        Document doc = TestUtil.open("Model\\Markup\\Test22082.docx");

        // and then after the CustomXmlPart.Data change
        StructuredDocumentTag sdt = ms.as(doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true), StructuredDocumentTag.class);
        long expChecksum = 0xed5b91F2L;
        msAssert.areEqual(expChecksum, sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());

        byte[] data = TestUtil.readAllBytes("Model\\Markup\\Test22082.xml");
        sdt.getXmlMapping().getCustomXmlPart().setData(data);
        expChecksum = 0xa6be6354L;
        msAssert.areEqual(expChecksum, sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());
    }


    /// <summary>
    /// WORDSNET-22449 Calculate storeItemChecksum attribute on Save
    /// Checksum now saved always for IsoStrict compliance.
    /// </summary>
    @Test
    public void test22449() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test22449.docx");

        final String X_PATH_TEMPLATE = "//w:body/w:sdt[{0}]/w:sdtPr/w15:dataBinding/@w16sdtdh:storeItemChecksum";

        DocxExportContext context = new DocxExportContext(doc, OoxmlComplianceCore.ISO_STRICT);
        context.addCustomNamespaceMapping("w15", com.aspose.words.DocxNamespaces.getNamespace(com.aspose.words.DocxNamespace.W1_5_MARKUP, true));
        context.addCustomNamespaceMapping("w16sdtdh", com.aspose.words.DocxNamespaces.getNamespace(com.aspose.words.DocxNamespace.W1_6_SDTDH, true));

        msAssert.areEqual("gifxXQ==", context.getXmlNode(msString.format(X_PATH_TEMPLATE, 1)).getNodeValue());
        msAssert.areEqual("lRlr7A==", context.getXmlNode(msString.format(X_PATH_TEMPLATE, 2)).getNodeValue());
        // 3rd tag has no XmlMapping.
        msAssert.areEqual("Np+DQQ==", context.getXmlNode(msString.format(X_PATH_TEMPLATE, 4)).getNodeValue());
    }

    //<<<<<<<< #endregion 





    /// <summary>
    /// Related with WORDSNET-22617
    /// The Word does not update content of nested SDT when ancestor and child SDT mapped to OOXML document.
    /// This case also related with the document in the test <see cref="Import.Docx.TestImportDocx.Test20722" />.
    /// </summary>
    @Test
    public void test22617UpdateNestedSdt() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test22617UpdateNestedSdt.docx");

        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual("Data2\f", sdt.getText());
    }


    /// <summary>
    /// WORDSNET-22919 Word table formatting issue using Customer XML data
    /// Reuse existing cell to update cell level SDT.
    /// </summary>
    @Test
    public void test22919() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test22919.docx");

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);

        msAssert.areEqual(1, sdt.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(NodeType.CELL, sdt.getFirstChild().getNodeType());
    }


    /// <summary>
    /// WORDSNET-23284 The end-of-cell marker has been removed and revisions have changed when creating custom XML.
    /// Word doesn't remove last paragraph in bound document, if it has some Built-In style.
    /// Also it doesn't remove reference Node in cell-level SDT if last paragraph was removed or
    /// its attributes cannot be overriden from the reference paragraph.
    /// </summary>
    @Test
    public void test23284() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test23284.docx");

        int originalRevisionsCount = doc.getRevisions().getCount();

        // Set mapping by the customer's scenario.
        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (int i = 0; i < sdts.getCount(); i++)
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)sdts.get(i);
            CustomXmlPart cxp = doc.getCustomXmlParts().getById(msString.format("00000000-0000-0000-0000-00000000000{0}", i+1));
            sdt.getXmlMapping().setMapping(cxp, "/ns0:I[1]", msString.format("xmlns:ns0='urn:in:Component{0}'", i+1));
        }

        // Force to update SDT content.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        msAssert.areEqual(9, sdts.getCount());
        for (int i = 0; i < sdts.getCount(); i++)
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)sdts.get(i);
            msAssert.areEqual(1, sdt.getChildNodes(NodeType.ANY, false).getCount());

            Cell cell = (Cell)sdt.getFirstChild();

            msAssert.areEqual(3, cell.getChildNodes(NodeType.ANY, false).getCount());
            ParagraphCollection paras = cell.getParagraphs();

            msAssert.isTrue(paras.get(0).getText().startsWith(msString.format("Item{0}", i+1)));

            msAssert.isTrue(paras.get(1).getText().startsWith("Maecenas"));
            msAssert.isTrue(paras.get(1).isDeleteRevision());

            msAssert.areEqual("\u0007", paras.get(2).getText());
            msAssert.isFalse(paras.get(2).isDeleteRevision());
        }

        msAssert.areEqual(originalRevisionsCount, doc.getRevisions().getCount());
    }

    /// <summary>
    /// Relates to WORDSNET-23284
    /// Checks that non-empty paragraph is not removed, but reference node is removed in cell-level SDT.
    /// </summary>
    @Test
    public void test23284A() throws Exception
    {
        Cell cell = test23284Core("Model\\Markup\\Test23284A.docx");

        msAssert.areEqual(3, cell.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("Last CustomXml\u0007", cell.getLastChild().getText());
    }

    /// <summary>
    /// Relates to WORDSNET-23284
    /// Checks that non-empty paragraph is not removed and cannot be overriden as it has numbering,
    /// Thus reference node is also not removed in cell-level SDT.
    /// </summary>
    @Test
    public void test23284B() throws Exception
    {
        Cell cell = test23284Core("Model\\Markup\\Test23284B.docx");

        msAssert.areEqual(4, cell.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("\u0007", cell.getLastChild().getText());
        msAssert.areEqual("Last CustomXml\r", cell.getLastChild().getPreviousSibling().getText());
    }

    /// <summary>
    /// Relates to WORDSNET-23284
    /// Checks that empty paragraph is not removed and cannot be overriden as it has numbering,
    /// Thus reference node is also not removed in cell-level SDT.
    /// </summary>
    @Test
    public void test23284C() throws Exception
    {
        Cell cell = test23284Core("Model\\Markup\\Test23284C.docx");

        msAssert.areEqual(4, cell.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("\u0007", cell.getLastChild().getText());
        msAssert.areEqual("\r", cell.getLastChild().getPreviousSibling().getText());
    }

    /// <summary>
    /// Relates to WORDSNET-23284
    /// Checks that empty paragraph is removed and thus reference node is not removed in cell-level SDT.
    /// </summary>
    @Test
    public void test23284D() throws Exception
    {
        Cell cell = test23284Core("Model\\Markup\\Test23284D.docx");

        msAssert.areEqual(3, cell.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("\u0007", cell.getLastChild().getText());
    }

    /// <summary>
    /// Relates to WORDSNET-23284
    /// Checks that empty paragraph with non built-in style is removed and
    /// thus reference node is not removed in cell-level SDT.
    /// </summary>
    @Test
    public void test23284E() throws Exception
    {
        Cell cell = test23284Core("Model\\Markup\\Test23284E.docx");

        msAssert.areEqual(3, cell.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("\u0007", cell.getLastChild().getText());
    }

    /// <summary>
    /// Relates to WORDSNET-23284
    /// Checks that empty paragraph with built-in style is not removed and
    /// thus reference node is also not removed in cell-level SDT.
    /// </summary>
    @Test
    public void test23284F() throws Exception
    {
        Cell cell = test23284Core("Model\\Markup\\Test23284F.docx");

        msAssert.areEqual(4, cell.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual("\u0007", cell.getLastChild().getText());
        msAssert.areEqual("\r", cell.getLastChild().getPreviousSibling().getText());
    }

    /// <summary>
    /// WORDSNET-23284 Revisions changed after adding CustomXmlPart.
    /// AW should use "SmartStyleBehavior" while updating SDT from the mapped content.
    /// </summary>
    /// <remarks>
    /// AM. Test file has been updated, see WORDSNET-24739 for details.
    /// </remarks>
    @Test
    public void test23526() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test23526", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        ParaPr paraPr = ((Cell)sdt.getFirstChild()).getFirstParagraph().getParaPr();

        // Attributes should not be expanded while updating the SDT from the mapped content.
        msAssert.isNull(paraPr.get(ParaAttr.LIST_ID));
        msAssert.areEqual(74, paraPr.get(ParaAttr.ISTD));

        WordAttrCollection revPr = paraPr.getFormatRevision().getRevPr();
        msAssert.areEqual(1, revPr.getCount());
        msAssert.areEqual(91, revPr.get(ParaAttr.ISTD));
    }

    /// <summary>
    /// Opens a document and sets the mapping of the first SDT, specific to the Test23284 test suite.
    /// </summary>
    private static Cell test23284Core(String testName) throws Exception
    {
        Document doc = TestUtil.open(testName);
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        CustomXmlPart cxp = doc.getCustomXmlParts().get(0);
        sdt.getXmlMapping().setMapping(cxp, "/ns0:I[1]", "xmlns:ns0='urn:in:item1'");

        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        msAssert.areEqual(1, sdt.getChildNodes(NodeType.ANY, false).getCount());
        return (Cell)sdt.getFirstChild();
    }




    /// <summary>
    /// Additional test case for WORDSNET-23777
    /// </summary>
    @Test (dataProvider = "test23777ADataProvider")
    public void test23777A(String testName, String expectedText) throws Exception
    {
        Document doc = TestUtil.open(testName);
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        msAssert.areEqual(expectedText, sdt.getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23777ADataProvider")
	public static Object[][] test23777ADataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Markup\\Test23777A1.xml",  " "},
			{"Model\\Markup\\Test23777A2.xml",  "Klikk eller trykk her for å skrive inn tekst."},
			{"Model\\Markup\\Test23777A3.xml",  "Klikk eller trykk her for å skrive inn tekst."},
		};
	}



    /// <summary>
    /// Simplified test for WORDSNET-24190
    /// </summary>
    @Test
    public void test24190A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test24190A.docx");

        StructuredDocumentTagCollection tags = doc.getRange().getStructuredDocumentTags();
        msAssert.areEqual(2, tags.getCount());

        // WORDSNET-25442 Seems MS has changed mapping behavior when inline SDT is updated from multiline document.
        // Now parent paragraph is moved into SDT as reference paragraph while all content of this paragraph including
        // SDT is moved out.
        StructuredDocumentTag tag0 = (StructuredDocumentTag)tags.get(0);
        Paragraph para = (Paragraph)tag0.getFirstChild();
        msAssert.areEqual(0xf3, para.getParaPr().getIstd());
        msAssert.areEqual("Text 2", para.getParagraphFormat().getStyleName());

        msAssert.areEqual(0x43, para.getParagraphBreakRunPr().getIstd());
        msAssert.areEqual("Text Char", para.getParagraphBreakFont().getStyleName());

        StructuredDocumentTag tag1 = (StructuredDocumentTag)tags.get(1);
        para = (Paragraph)tag1.getFirstChild();
        msAssert.areEqual(0xf3, para.getParaPr().getIstd());
        msAssert.areEqual("Text 2", para.getParagraphFormat().getStyleName());
    }

    /// <summary>
    /// Simplified case for WORDSNET-22815
    /// </summary>
    @Test
    public void test22815A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test22815A.xml");
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(6, sdt.getChildNodes(NodeType.ANY, false).getCount());
    }

    /// <summary>
    /// WORDSNET-21964 Nested content control not deleted from document
    /// Implemented basic runtime mapped CustomXML update.
    /// </summary>
    @Test
    public void test21964() throws Exception
    {
        final String FILE_NAME = "Model\\Markup\\Test21964.docx";

        Document doc = TestUtil.open(FILE_NAME);

        msAssert.areEqual(2, doc.getRange().getStructuredDocumentTags().getCount());

        IStructuredDocumentTag outerSdt = doc.getRange().getStructuredDocumentTags().get(0);
        IStructuredDocumentTag innerSdt = doc.getRange().getStructuredDocumentTags().get(1);

        byte[] dataBefore = (byte[])outerSdt.getXmlMapping().getCustomXmlPart().getData().clone();

        innerSdt.getNode().remove();

        byte[] dataAfter = outerSdt.getXmlMapping().getCustomXmlPart().getData();

        // Check that mapped CustomXML of outer tag has been changed runtime.
        msAssert.isFalse(ArrayUtil.compareBytes(dataBefore, dataAfter, dataBefore.length));

        doc = TestUtil.saveOpen(doc, FILE_NAME, new OoxmlSaveOptions(), false);

        msAssert.areEqual(1, doc.getRange().getStructuredDocumentTags().getCount());
    }

    /// <summary>
    /// WORDSNET-24036 "w14:checked" is not Sdt.CheckBox direct value
    /// Word updates checkbox content only when value is changed.
    /// </summary>
    @Test (dataProvider = "test24036DataProvider")
    public void test24036(String fileName, String expContent) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        IStructuredDocumentTag tag = doc.getRange().getStructuredDocumentTags().get(0);
        msAssert.areEqual(expContent, tag.getNode().getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test24036DataProvider")
	public static Object[][] test24036DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Markup\\Test24036.docx",  "☒"},
			{"Model\\Markup\\Test13458 Checked Mapped 0.docx",  "N"},
			{"Model\\Markup\\Test13458 Checked Mapped 1.docx",  "X"},
			{"Model\\Markup\\Test13458 Unchecked Mapped 0.docx",  "X"},
		};
	}

    /// <summary>
    /// WORDSNET-24529 Hidden SDT becomes visible after open/save document.
    /// Do not update RichText SDT if visible content equals to mapped.
    /// </summary>
    @Test
    public void test24529() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test24529.docx");

        StructuredDocumentTag tag = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0).getNode();

        Run content = (Run)tag.getFirstChild();

        msAssert.areEqual("Test", content.getText());
        msAssert.isTrue(content.getFont().getHidden());
    }







    /// <summary>
    /// WORDSNET-25242 SDT value is changed after rendering document.
    /// Update nested XPath to absolute values.
    /// </summary>
    @Test (enabled = false)
    public void test25242(String fileName, int index, String xPath) throws Exception
    {
        Document doc = TestUtil.open(fileName);

        StructuredDocumentTag rs = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().getById(207924854);
        msAssert.areEqual(SdtType.REPEATING_SECTION, rs.getSdtType());

        StructuredDocumentTag rsItem = (StructuredDocumentTag)rs.getChildNodes(NodeType.ANY, false).get(index);
        StructuredDocumentTag nestedSdt = (StructuredDocumentTag)((Paragraph)rsItem.getFirstChild()).getFirstChild();

        msAssert.areEqual(xPath, nestedSdt.getXmlMapping().getXPath());
        msAssert.areEqual((index == 0 ? "CA1000700-001" : "CA1000700-002"), nestedSdt.getText());
    }


    /// <summary>
    /// Simplified test for WORDSNET-25442
    /// </summary>
    /// <remarks>
    /// Seems that MS constantly changes XML Mapping logic.
    /// This behavior was tested on Microsoft® Word 2019 MSO (Version 2306 Build 16.0.16529.20100) 32-bit
    /// </remarks>
    @Test
    public void test25442A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25442A.docx");
        msAssert.areEqual(1, doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getChildNodes(NodeType.ANY, false).getCount());

        Paragraph para = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY).getFirstParagraph();
        msAssert.areEqual(0x47, para.getParaPr().get(ParaAttr.ISTD));
        msAssert.isNotNull(para.getParaPr().get(ParaAttr.BORDER_BOTTOM));

        msAssert.areEqual(2, para.getCount());
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, para.getChild(NodeType.ANY, 0, false).getNodeType());
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, para.getChild(NodeType.ANY, 1, false).getNodeType());
    }


    /// <summary>
    /// Additional test for WORDSNET-25442
    /// Tests that adjacent bookmarks are correctly handled.
    /// </summary>
    @Test
    public void test25442Bookmarks1() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira17905_BlockSdtAndParaToStartOfPara.docx");

        // Check that bookmarks before SDT is preserved and moved before SDT.
        NodeCollection bodyChildNodes = doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false);
        msAssert.areEqual(NodeType.BOOKMARK_START, bodyChildNodes.get(0).getNodeType());
        msAssert.areEqual(NodeType.BOOKMARK_END, bodyChildNodes.get(1).getNodeType());
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, bodyChildNodes.get(2).getNodeType());
    }

    /// <summary>
    /// Additional test for WORDSNET-25442
    /// Tests that adjacent bookmarks are correctly handled.
    /// </summary>
    @Test
    public void test25442Bookmarks2() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira17905_ParaAndBlockSdtWithReplacingPara.docx");

        // Check that surrounding bookmarks are preserved and moved along with SDT.
        Bookmark bm1 = doc.getRange().getBookmarks().get("bm1");
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, bm1.getBookmarkEnd().getNextSibling().getNodeType());

        Bookmark bm2 = doc.getRange().getBookmarks().get("bm2");
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, bm2.getBookmarkStart().getPreviousSibling().getNodeType());
    }

    /// <summary>
    /// WORDSNET-25160 Updating content controls causing incorrect formatting
    /// Fixed per WORDSNET-26027.
    /// </summary>
    @Test
    public void test25160() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25160.docx");

        // Both SDTs should have 'Table Center' style applied for the last paragraph.
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);
            Paragraph para = (Paragraph)sdt.getLastChild();
            msAssert.areEqual(0x12, para.getParaPr().get(ParaAttr.ISTD));

        sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(1);
        para = (Paragraph)sdt.getLastChild();
        msAssert.areEqual(0x12, para.getParaPr().get(ParaAttr.ISTD));
        }

    /// <summary>
    /// WORDSNET-25469 Nested content controls CXP is not updating
    /// Handle the case when more than one outer SDT should be updated.
    /// </summary>
    @Test
    public void test25469() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test25469.docx");

        // Update inner SDT.
        StructuredDocumentTag innerSdt = ms.as(doc.getRange().getStructuredDocumentTags().getById(1499471227), StructuredDocumentTag.class);
        Paragraph para = (Paragraph)innerSdt.getFirstChild();
        para.removeAllChildren();
        para.appendChild(new Run(doc, "New Content Added"));

        // Extract mapped document for outer SDT.
        CustomXmlPart xmlPart = doc.getCustomXmlParts().getById("{1EB64CAA-E422-4916-9D40-2877F937F471}");
        org.w3c.dom.Document xmlDoc = XmlUtilPal.loadXml(new MemoryStream(xmlPart.getData()), false);
        byte[] innerBytes = Encoding.getUTF8().getBytes(xmlDoc.getFirstChild().getTextContent());
        LoadOptions options = new LoadOptions(); { options.setUpdateXmlMapping(false); }
        Document outerMappedDoc = new Document(new MemoryStream(innerBytes), options);

        // Check that mapped document for outer SDT has been updated properly.
        StructuredDocumentTag outerMappedSdt = (StructuredDocumentTag)outerMappedDoc.getRange().getStructuredDocumentTags().get(0);
        msAssert.areEqual("New Content Added\r", outerMappedSdt.getText());
    }

    /// <summary>
    /// WORDSNET-26419 Line break is added at the end of block level SDT.
    /// Ignore paragraph break character when PlainText SDT is updated from mapped XML.
    /// </summary>
    @Test
    public void test26419() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26419.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        NodeCollection listOfSdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) listOfSdts)
        {
            sdt.removeAllChildren();
            // Make sure the is node we can move to in SDT.
            if (sdt.getLevel() == MarkupLevel.BLOCK)
                sdt.appendChild(new Paragraph(doc));
            else if (sdt.getLevel() == MarkupLevel.INLINE)
                sdt.appendChild(new Run(doc));

            // The following is not required to reproduce the problem.
            // But in such case the problem is seen in the output document.
            builder.moveToStructuredDocumentTag(sdt, 0);
            builder.write("A whole para");
            sdt.removeSelfOnly();
        }

        msAssert.areEqual("A whole para\rHello: A whole para\f", doc.getText());
    }

    /// <summary>
    /// Relates to WORDSNET-26419.
    /// Simplified case.
    /// </summary>
    @Test
    public void test26419A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26419A.docx");

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);
        msAssert.areEqual("A whole para\r", sdt.getText());
    }

    /// <summary>
    /// WORDSNET-26381 Inserting text to cell in XML-mapped cell-level structured document tag makes document corrupted
    /// The document was corrupted because the end-of-cell character was written to a custom XML part. Now the character
    /// is removed from the text.
    /// </summary>
    @Test
    public void test26381() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26381.docx");
        DocumentBuilder builder = new DocumentBuilder(doc);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 2, true);
        msAssert.areEqual(MarkupLevel.CELL, sdt.getLevel());

        // Add text to the SDT.
        Node run = sdt.getChild(NodeType.RUN, 0, true);
        builder.moveTo(run);
        builder.write("<Text>");

        // Check that the end-of-cell character is not written to the custom XML.
        CustomXmlPart xmlPart = sdt.getXmlMapping().getCustomXmlPart();
        String xmlPartText = Encoding.getASCII().getString(xmlPart.getData());
        final String EXPECTED_TEXT = "<cell1>&lt;Text>Cell 1</cell1>";
        msAssert.isTrue(xmlPartText.contains(EXPECTED_TEXT));
    }

    /// <summary>
    /// Additional test for WORDSNET-26636.
    /// Simplified case.
    /// </summary>
    @Test
    public void test26636A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26636A.docx");

        // Both SDTs should have the same structure after update.
        for (IStructuredDocumentTag node  : doc.getRange().getStructuredDocumentTags())
        {
            StructuredDocumentTag sdt = (StructuredDocumentTag)node;
            msAssert.areEqual(MarkupLevel.BLOCK, sdt.getLevel());
            msAssert.areEqual(NodeType.PARAGRAPH, sdt.getFirstChild().getNodeType());
            msAssert.areEqual(NodeType.TABLE, sdt.getFirstChild().getNextSibling().getNodeType());
        }
    }


    /// <summary>
    /// Additional test for WORDSNET-26601.
    /// Tests that correct paragraph style is applied.
    /// </summary>
    @Test (enabled = false)
    public void test26601A(String testName, int istd, String styleName) throws Exception
    {
        Document doc = TestUtil.open(testName);
        Paragraph para = (Paragraph)doc.getRange().getStructuredDocumentTags().get(0).getChildNodes(NodeType.PARAGRAPH, true).get(0);

        msAssert.areEqual(istd, para.getParaPr().getIstd());
        msAssert.areEqual(styleName, para.getParagraphStyle().getName());
    }

    /// <summary>
    /// WORDSNET-27003 Extra paragraph added after SetMapping
    /// Updated XML mapping logic.
    /// </summary>
    @Test
    public void test27003() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test27003.docx");
        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getRows().get(1).getFirstCell();

        msAssert.areEqual(1, cell.getParagraphs().getCount());
    }

    /// <summary>
    /// Simplified case for WORDSNET-26905.
    /// </summary>
    @Test
    public void test26905A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26905A.docx");
        ArrayList<CommentRangeStart> starts = doc.getChildNodes(NodeType.COMMENT_RANGE_START, true).<CommentRangeStart>toList();

        // Check that first two CommentRangeStart nodes is not moved into imported para.
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, starts.get(0).getParentNode().getNodeType());
        msAssert.isTrue(starts.get(0).isFirstChild());

        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, starts.get(1).getParentNode().getNodeType());
        msAssert.isTrue(starts.get(1).isFirstChild());
    }

    /// <summary>
    /// WORDSNET-27328 Inconsistent XmlMapping.IsMapped property behavior with CustomXmlPart null value in SDTs
    /// Create runtime CustomXmlPart for core properties mapping.
    /// </summary>
    @Test
    public void test27328() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test27328.docx");

        StructuredDocumentTag sdt = ms.as(doc.getRange().getStructuredDocumentTags().getByTitle("Author"), StructuredDocumentTag.class);

        msAssert.isNotNull(sdt.getXmlMapping().getCustomXmlPart());
        msAssert.areEqual("http://schemas.openxmlformats.org/package/2006/metadata/core-properties", sdt.getXmlMapping().getCustomXmlPart().getSchemas().get(0));
        msAssert.areEqual("http://purl.org/dc/elements/1.1/", sdt.getXmlMapping().getCustomXmlPart().getSchemas().get(1));
    }

    /// <summary>
    /// WORDSNET-27447 System.InvalidOperationException when opening WordOpenXMLMinimal
    /// Remove macros when save WordOpenXML content.
    /// </summary>
    @Test
    public void test27447() throws Exception
    {
        Document document = TestUtil.open("Model\\Markup\\Test27447.docm");

        for (StructuredDocumentTag contentControl : (Iterable<StructuredDocumentTag>) document.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
        {
            if (contentControl.getSdtType() == SdtType.RICH_TEXT && contentControl.getTag().contains("ID"))
            {
                msAssert.isNotEmpty(contentControl.getWordOpenXMLMinimal());
                msAssert.isNotEmpty(contentControl.getWordOpenXML());
            }
        }
    }






    /// <summary>
    /// WORDSNET-28046 Unexpected loss of paragraph data and attributes after modifying SDT mapping and custom XML
    /// Preserve SDT paragraph attributes in case of update from placeholder.
    /// </summary>
    @Test
    public void test28046() throws Exception
    {
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setLoadFormat(LoadFormat.DOCX);
        loadOptions.setMswVersion(MsWordVersion.WORD_2019);
        Document sourceDocument = TestUtil.open("Model\\Markup\\Test28046", LoadFormat.DOCX);
        // No update page layout in FOSS.

        for (Node node : (Iterable<Node>) sourceDocument.getChildNodes(NodeType.ANY, true))
        {
            if (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            {
                StructuredDocumentTag sdt = (StructuredDocumentTag)node;
                sdt.getXmlMapping().delete();
            }
            if (node.getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START)
            {
                StructuredDocumentTagRangeStart sdt = (StructuredDocumentTagRangeStart)node;
                sdt.getXmlMapping().delete();
            }
        }
        sourceDocument.getCustomXmlParts().clear();

        // AM. There is something wrong with Document cloning, have no time to figure it out,
        // use manual SaveOpen for a while.
        String outFileName =
            TestUtil.buildOutFileName(TestUtil.buildTestFileName("Model\\Markup\\Test28046"), "", SaveFormat.DOCX);
        sourceDocument.save(outFileName);

        Document doc = TestUtil.open(outFileName);

        StructuredDocumentTag sdt1510876971 =
            (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().getById(1510876971);
        Paragraph para = (Paragraph)sdt1510876971.getFirstChild();

        msAssert.areEqual(0x4d1b13ec, para.getParaId());
        msAssert.areEqual(0x71ff5351, para.getTextId());

        msAssert.areEqual(1, para.getParaPr().getCount());
        msAssert.areEqual(0x00e06078, para.getParaPr().get(ParaAttr.RSID_P));

        msAssert.areEqual(2, para.getParagraphBreakRunPr().getCount());
        msAssert.areEqual(0x00810263, para.getParagraphBreakRunPr().get(FontAttr.RSID_R));
        msAssert.areEqual((int)Language.JAPANESE_JAPAN, para.getParagraphBreakRunPr().get(FontAttr.LOCALE_ID_FAR_EAST));
    }

    /// <summary>
    /// Relates to WORDSNET-28393.
    /// Tests that XML mapping updated correctly during import.
    /// </summary>
    @Test
    public void test28393A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test28393A.docx");

        // Check there is only only one bookmark.
        BookmarkCollection bookmarks = doc.getRange().getBookmarks();
        msAssert.areEqual(1, bookmarks.getCount());
        msAssert.areEqual("Bookmark1", bookmarks.get(0).getName());

        // Check bookmark is located inside the last SDT.
        StructuredDocumentTag sdt = (StructuredDocumentTag)bookmarks.get(0).getBookmarkStart()
            .getAncestor(NodeType.STRUCTURED_DOCUMENT_TAG);

        msAssert.areEqual(-561253186, sdt.getId());
    }

    /// <summary>
    /// WORDSNET-28542 Removing and adding child nodes to Date SDT produced invalid output.
    /// Map Date SDT data as text.
    /// </summary>
    @Test
    public void test28542() throws Exception
    {
        HashMap<String, String> values = new HashMap<String, String>();
        msDictionary.add(values, "DocumentNumber", "TestDocument");
        msDictionary.add(values, "RevisionIssueDate", "11/08/2025");

        // fill template document tags and save
        Document doc = TestUtil.open("Model\\Markup\\Test28542.docx");

        fillFields(doc, values);
        doc.updateFields();
        // No update page layout in FOSS.

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test28542.docx", new OoxmlSaveOptions(), false);

        // Check problematic SDT and mapped XML data.
        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getFirstSection().getBody().getRange().getStructuredDocumentTags().getByTag("RevisionIssueDate");

        msAssert.areEqual("11/08/2025", sdt.getText());

        org.w3c.dom.Document xmlDoc = XmlUtilPal.loadXml(new MemoryStream(sdt.getXmlMapping().getCustomXmlPart().getData()), false);

        if (xmlDoc.getChildNodes().item(1) != null)
        {
            org.w3c.dom.Node docNumber = xmlDoc.getChildNodes().item(1).getChildNodes().item(0);
            msAssert.areEqual("DocNumber", docNumber.getNodeName());
            msAssert.areEqual("TestDocument", docNumber.getTextContent());

            org.w3c.dom.Node revisionIssueDate = xmlDoc.getChildNodes().item(1).getChildNodes().item(1);
            msAssert.areEqual("RevisionIssueDate", revisionIssueDate.getNodeName());
            msAssert.areEqual("11/08/2025", revisionIssueDate.getTextContent());
        }
    }








    private static void fillFields(Document document, HashMap<String, String> reportValues)
    {
        NodeCollection nodeCollection = document.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        for (StructuredDocumentTag structuredDocumentTag : (Iterable<StructuredDocumentTag>) nodeCollection)
        {
            String tagName = structuredDocumentTag.getTag();
            if (reportValues.containsKey(tagName))
            {
                String value = reportValues.get(tagName);
                if (structuredDocumentTag.getFirstChild() != null)
                {
                    /*NodeType*/int nodeType = structuredDocumentTag.getFirstChild().getNodeType();
                    switch (nodeType)
                    {
                        case NodeType.RUN:
                            structuredDocumentTag.removeAllChildren();
                            Run run = new Run(document);
                            run.setText(value);
                            structuredDocumentTag.appendChild(run);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private static Run getFirstRunWithText(NodeCollection source, String text)
    {
        for (Node item : (Iterable<Node>) source)
        {
            if (msString.equals(item.getText(), text))
            {
                return (Run) item;
            }
        }
        return null;
    }

    /// <summary>
    /// Checks read checksum and actual checksum are equal to expected values.
    /// </summary>
    private static void verifyChecksum(StructuredDocumentTag sdt, long expReadChecksum, long expPublicChecksum) throws Exception
    {
        msAssert.areEqual(expReadChecksum, (long)(BitConverter.toUInt32(Convert.fromBase64String(sdt.getXmlMapping().getStoreItemChecksum()), 0) & 0xFFFFFFFFL));
        msAssert.areEqual(expPublicChecksum, (long)sdt.getXmlMapping().getCustomXmlPart().getDataChecksum());
    }
}

