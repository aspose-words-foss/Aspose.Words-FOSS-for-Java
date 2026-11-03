// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.FileSystem;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.TestZipUtil;
import com.aspose.DateTimeUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests for OLE and OOXML embedded objects in DOCX documents.
/// </summary>
@Test
public class TestImportDocxEmbedded
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// This document contains embedded Word OOXML packages.
    /// </summary>
    @Test
    public void testEmbeddedWordOoxml() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedWordOoxml.docx");
        checkOoxmlObjectBySize(doc, 0, DocxContentType.DOCX, 12791);
        checkOoxmlObjectBySize(doc, 1, DocxContentType.DOCM, 12913);
        checkOoxmlObjectBySize(doc, 2, DocxContentType.DOTX, 12918);
        checkOoxmlObjectBySize(doc, 3, DocxContentType.DOTM, 12934);
    }

    /// <summary>
    /// This document contains embedded Excel OOXML packages.
    /// </summary>
    @Test
    public void testEmbeddedExcelOoxml() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedExcelOoxml.docx");
        // We use golds here because we unhide the workbook and the resulting ZIP on Java is different.
        // WORDSNET-14899 MSW hides workbook when it inserted as icon (DrawAspect="Icon"). Current DOCX
        // contains shapes with DrawAspect="Content", so unhide of workbooks for Excel OOXML packages
        // will be skipped.
        checkOoxmlObjectByGold(doc, 0, DocxContentType.XLSX);
        checkOoxmlObjectByGold(doc, 1, DocxContentType.XLSM);
        checkOoxmlObjectBySize(doc, 2, DocxContentType.XLSB, 8754);
        // Although I tried to embed Excel Templates XLTX and XLTM here, MS Word inserts them as Spreadsheets, so be it.
        checkOoxmlObjectByGold(doc, 3, DocxContentType.XLSX);
        checkOoxmlObjectByGold(doc, 4, DocxContentType.XLSM);
    }

    /// <summary>
    /// This test is a pert of the fix concerned with WORDSNET-14899
    /// Checks that workbook with DrawAspect="Icon" will be unhidden after saving to file.
    /// </summary>
    @Test
    public void testEmbeddedExcelOoxmlIcon() throws Exception
    {
        final String PATH = "ImportDocx\\Embedded\\TestEmbeddedExcelOoxmlIcon";
        Document doc = TestUtil.openSaveOpen(PATH, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        checkOoxmlObjectByGold(doc, 0, DocxContentType.XLSX);
        checkOoxmlObjectByGold(doc, 1, DocxContentType.XLSM);
        checkOoxmlObjectByGold(doc, 3, DocxContentType.XLSX);
        checkOoxmlObjectByGold(doc, 4, DocxContentType.XLSM);
    }

    /// <summary>
    /// WORDSNET-14899 (fixes) - Saving Embedded Spreadsheet produces a different file every time.
    /// The reason of the problem is that OOXML package is re-created while saving to external file.
    /// Package re-creation is caused of attempt to unhide a workbook. Workbook has to be unhidden when
    /// attribute "DrawAspect" has value "Icon" to fix the problem.
    /// </summary>
    @Test (groups = "SingleThreadGroup")
    public void testJira14899() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\Ole\\TestJira14899", LoadFormat.DOCX);
        Shape excelWb = (Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 0, true);

        // Expected that "DrawAspect" is a "Content".
        msAssert.isFalse((/*boolean*/Boolean)excelWb.fetchShapeAttrInternal(ShapeAttr.OLE_ICON));
        // Compare output streams when "DrawAspect" is a "Content".
        compareWorkbookData(excelWb, true, false);

        // Set "DrawAspect" value to "Icon".
        excelWb.setShapeAttrInternal(ShapeAttr.OLE_ICON, true);
        // Compare output streams when "DrawAspect" is a "Icon".
        // In Java Streams are different in debg mode but identical in flat run.
        // Output streams must have the same content when "DrawAspect" is a "Icon"
        // and output files creates at the same time.
        compareWorkbookData(excelWb, true, true);
    }

    @Test
    public void testEmbeddedPowerPointOoxml() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedPowerPointOoxml.docx");

        checkOoxmlObjectBySize(doc, 0, DocxContentType.PPTX, 31138);
        checkOoxmlObjectBySize(doc, 1, DocxContentType.PPTM, 31161);

        // Although I tried to embed POTX and POTM, MS Word embeds them as OLE objects, not as OOXML packages.
        checkOleObjectBySize(doc, 2, "Package", ".potx", 31096);
        checkOleObjectBySize(doc, 3, "Package", ".potm", 32139);

        // Although I tried to embed PPSX and PPSM (PowerPoint Slides), MS Word embeds them as PPTX and PPTM, so be it.
        checkOoxmlObjectBySize(doc, 4, DocxContentType.PPTX, 31146);
        checkOoxmlObjectBySize(doc, 5, DocxContentType.PPTM, 31169);
    }

    // FOSS TestSaveEmbeddedOoxmlToDoc removed: it tests how embedded OOXML packages are converted to
    // OLE objects when saving to the removed DOC format.

    private static void checkOoxmlToOle(Document doc, int shapeIdx)
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.isNotNull(shape.getOleFormat().getOleObject());
    }

    /// <summary>
    /// This document contains Word documents in various formats embedded as OLE objects.
    /// </summary>
    @Test
    public void testEmbeddedWordOle() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedWordOle.docx");

        checkOleObjectBySize(doc, 0, "Word.Document.8", ".doc", 22016);
        // Although I tried to embed DOT, MS Word embeds it as DOC, so be it.
        checkOleObjectBySize(doc, 1, "Word.Document.8", ".doc", 22016);
        // Although I tried to embed RTF, MS Word embeds it as DOC, so be it.
        checkOleObjectBySize(doc, 2, "Word.Document.8", ".doc", 22016);
        // Although I tried to embed WML, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 3, "Package", ".xml", 10860);
        // Although I tried to embed ODT, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 4, "Word.OpenDocumentText.12", ".odt", 4419);
    }

    /// <summary>
    /// This document contains Excel documents in various formats embedded as OLE objects.
    /// </summary>
    @Test
    public void testEmbeddedExcelOle() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedExcelOle.docx");

        checkOleObjectBySize(doc, 0, "Excel.Sheet.8", ".xls", 18432);
        // Although I tried to embed XLT, MS Word embeds it as XLS, so be it.
        checkOleObjectBySize(doc, 1, "Excel.Sheet.8", ".xls", 18432);
        // Although I tried to embed SpreadsheetML, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 2, "Package", ".xml", 3123);
        // Although I tried to embed ODS, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 3, "Excel.OpenDocumentSpreadsheet.12", ".ods", 2834);
        // Although I tried to embed TXT, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 4, "Package", ".txt", 5);


        // WORDSNET-9995 XLS workbook is hidden in Excel when extracted from an OLE object.
        String xlsFile = TestUtil.getInTestOutPath("ImportDocx\\Embedded\\TestEmbeddedExcelOle Out.docx 0 Out.xls");
        FileSystem fs = new FileSystem(xlsFile);
        MemoryStream workbook = fs.getRoot().getStreamSafe("Workbook");
        workbook.setPosition(0xee);
        int flags = workbook.readByte();
        // This flag is 0x39 in the original document, but we reset the hidden bit when exporting, check it.
        msAssert.areEqual(0x38, flags);
    }

    /// <summary>
    /// This document contains PowerPoint documents in various formats embedded as OLE objects.
    /// </summary>
    @Test
    public void testEmbeddedPowerPointOle() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedPowerPointOle.docx");

        checkOleObjectBySize(doc, 0, "PowerPoint.Show.8", ".ppt", 47616);
        // Although I tried to embed POT, MS Word embeds it as PPT, so be it.
        checkOleObjectBySize(doc, 1, "PowerPoint.Show.8", ".ppt", 47616);
        // Although I tried to embed PPS, MS Word embeds it as PPT, so be it.
        checkOleObjectBySize(doc, 2, "PowerPoint.Show.8", ".ppt", 47616);

        // Although I tried to embed PowerPoint XML, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 3, "Package", ".xml", 89540);
        // Although I tried to embed ODP, MS Word embeds it wrapped into a structured storage.
        checkOleObjectBySize(doc, 4, "PowerPoint.OpenDocumentPresentation.12", ".odp", 24278);
    }

    /// <summary>
    /// This document contains various other non-MS Office embedded objects.
    /// </summary>
    @Test
    public void testEmbeddedVarious() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Embedded\\TestEmbeddedVarious.docx");

        checkOleObjectBySize(doc, 0, "AcroExch.Document.7", ".pdf", 57066);
        checkOleObjectBySize(doc, 1, "Package", ".xps", 48261);
        checkOleObjectBySize(doc, 2, "Package", ".xml", 34484); // FlatOPC
        checkOleObjectBySize(doc, 3, "Package", ".wps", 5120); // Works
        checkOleObjectBySize(doc, 4, "Package", ".htm", 21088); // HTML
        checkOleObjectBySize(doc, 5, "Package", ".mht", 28683); // MHTML
    }

    /// <summary>
    /// WORDSNET-12589 Extracted .xlsx file is not valid.
    /// The XLSX file is wrapped into a structured storage inside a DOC file. If saved as a structured storage,
    /// it cannot be opened in Excel. Added code to unwrap it before saving.
    /// </summary>
    // FOSS TestEmbeddedXlsx removed: the input is a binary DOC (removed load format) and the test is about
    // unwrapping an XLSX from the DOC's structured storage - binary-DOC-specific behavior.



    private static void checkOoxmlObjectByGold(Document doc, int shapeIdx, String contentType) throws Exception
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.areEqual(shape.getOleFormat().getOoxmlObject().getContentType(), contentType);

        String outFileName = saveOleObject(shape, shapeIdx);
        String goldFileName = TestUtil.buildGoldFileName(outFileName, "", SaveFormat.UNKNOWN);

        TestZipUtil.verifyFile("Embedded OOXML Object", null, outFileName, goldFileName, null);
    }

    /// <summary>
    /// Saves embedded data of the shape twice - with current time and fixed test time, compares output streams.
    /// </summary>
    /// <param name="excelWb">Shape with embedded Excel workbook.</param>
    /// <param name="isEqual">True - expected that output streams are equal.</param>
    /// <param name="preserveTestMode">True - do not reset test mode while saving embedded object.</param>
    private static void compareWorkbookData(Shape excelWb, boolean isEqual, boolean preserveTestMode) throws Exception
    {
        try
        {
            MemoryStream ms1 = new MemoryStream();
            try /*JAVA: was using*/
        	{
            MemoryStream ms2 = new MemoryStream();
            try /*JAVA: was using*/
            {
                excelWb.getOleFormat().save(ms1);
                // Reset test mode to get different current time while writing a package.
                DateTimeUtil.setTestMode(preserveTestMode);
                excelWb.getOleFormat().save(ms2);

                ms1.setPosition(0);
                ms2.setPosition(0);

                // Check streams data.
                msAssert.areEqual(isEqual, TestUtil.compareStreams(ms1, ms2));
            }
            finally { if (ms2 != null) ms2.close(); }
        	}
            finally { if (ms1 != null) ms1.close(); }
        }
        finally
        {
            // Reset to original value.
            DateTimeUtil.setTestMode(true);
        }
    }

    private static void checkOoxmlObjectBySize(Document doc, int shapeIdx, String contentType, int expectedFileSize) throws Exception
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.areEqual(shape.getOleFormat().getOoxmlObject().getContentType(), contentType);

        String outFileName = saveOleObject(shape, shapeIdx);
        compareFileSize(outFileName, expectedFileSize);
    }

    private static void checkOleObjectBySize(Document doc, int shapeIdx, String progId, String extension, int expectedFileSize) throws Exception
    {
        Shape shape = (Shape)doc.getChild(NodeType.SHAPE, shapeIdx, true);
        msAssert.areEqual(progId, shape.getOleFormat().getProgId());
        msAssert.areEqual(extension, shape.getOleFormat().getSuggestedExtension());

        String outFileName = saveOleObject(shape, shapeIdx);
        compareFileSize(outFileName, expectedFileSize);
    }

    /// <summary>
    /// Compares file sizes with 5% tolerance for platform neutrality.
    ///
    /// Sizes can differ since platform-specific zip libraries are used to compress underlying streams.
    /// </summary>
    private static void compareFileSize(String outFileName, int expectedFileSize) throws Exception
    {
        int savedFileSize = TestUtil.getFileSize(outFileName);
        double difference = Math.abs((savedFileSize - expectedFileSize) / expectedFileSize);

        msAssert.isTrue(difference <= 0.05, msString.format("Difference is more than 5%! Expected FileSize: {0}, Saved FileSize: {1}, Difference: {2}",                           expectedFileSize, savedFileSize, difference));
    }

    private static String saveOleObject(Shape shape, int shapeIdx) throws Exception
    {
        String outFileName = msString.format("{0} {1} Out{2}",
            shape.fetchDocument().getOriginalFileName(), shapeIdx, shape.getOleFormat().getSuggestedExtension());
        shape.getOleFormat().save(outFileName);

        return outFileName;
    }



    /// <summary>
    /// WORDSNET-24632 Implement OoxmlObject.GetFileNameForUser().
    /// Previously implementation returned empty string. Added logic to return file name from the package part name.
    /// </summary>
    @Test
    public void test24632() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Embedded\\Test24632.docx");
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();

        msAssert.areEqual("Microsoft_Word_Document", shapes.get(0).getOleFormat().getSuggestedFileName());
        msAssert.areEqual("Microsoft_PowerPoint_Presentation", shapes.get(1).getOleFormat().getSuggestedFileName());
        msAssert.areEqual("Microsoft_Excel_Worksheet", shapes.get(2).getOleFormat().getSuggestedFileName());

        // Actually in the package file name is "oleObject1". However, AW takes the name from the OLE data.
        msAssert.areEqual("enquiry", shapes.get(3).getOleFormat().getSuggestedFileName());
    }

}

