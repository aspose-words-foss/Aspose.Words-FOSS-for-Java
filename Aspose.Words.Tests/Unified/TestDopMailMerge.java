// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Tests for the mail merge settings in various formats.
/// 
/// These tests are "manual unified" because it is difficult to automatically convert these documents 
/// into "ms" files without damaging the mail merge settings in them.
/// </summary>
@Test
public class TestDopMailMerge
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    // FOSS: only the OOXML scenario survives — Doc/Wml/Rtf load+save were removed.
    // MailMergeSettings/ODSO model and their DOCX persistence are unaffected.
    private static /*final*/ /*UnifiedScenario*/int[] G_TEST_SCENARIOS = new /*UnifiedScenario*/int[]
   {
       UnifiedScenario.DOCX_2_DOCX_NO_GOLD
   };

    /// <summary>
    /// Outlook data source and some settings.
    /// 
    /// JAVAGOLD Java is better than .NET in outputting several uniqueTag numbers as Unicode chars. 
    /// It looks like .NET screws 4 types but Java only 2 times in this document.
    /// </summary>
    @Test
    public void testMailMergeSettings1() throws Exception
    {
        for (/*UnifiedScenario*/int scenario : G_TEST_SCENARIOS)
            testMailMergeSettings1(scenario);
    }
    
    private static void testMailMergeSettings1(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\MailMerge\\TestMailMergeSettings1", scenario);

        // Check mail merge settings.
        MailMergeSettings mmSettings = doc.getDocPr().MailMergeSettings;
        msAssert.areEqual(66, mmSettings.getActiveRecord());
        msAssert.areEqual("Email_Address", mmSettings.getAddressFieldName());
        msAssert.areEqual(MailMergeCheckErrors.PAUSE_ON_ERROR, mmSettings.getCheckErrors());
        msAssert.areEqual("Provider=Microsoft.ACE.OLEDB.12.0;User ID=Admin;Data Source=\"\";Mode=Share Deny None;Extended " +
                "Properties=\"Outlook 9.0;MAPILEVEL=Personal Folders|;TABLETYPE=0;DATABASE=C:\\Users\\romeok\\AppData" +
                "\\Local\\Temp\\OfficeMMergeTempDir\\OLKA53B.tmp;COLSETVERSION=12.0\";Je", mmSettings.getConnectString());
        msAssert.areEqual("C:\\Program Files (x86)\\Microsoft Office\\Office12\\~~~_virtual_file_~~~Personal Folders|", mmSettings.getDataSource());
        msAssert.areEqual(MailMergeDataType.NATIVE, mmSettings.getDataType());
        msAssert.areEqual(MailMergeDestination.NEW_DOCUMENT, mmSettings.getDestination());
        msAssert.areEqual(false, mmSettings.getDoNotSupressBlankLines());
        msAssert.areEqual("", mmSettings.getHeaderSource());
        msAssert.areEqual(false, mmSettings.getLinkToQuery());
        msAssert.areEqual(false, mmSettings.getMailAsAttachment());
        msAssert.areEqual("test subject", mmSettings.getMailSubject());
        msAssert.areEqual(MailMergeMainDocumentType.FORM_LETTERS, mmSettings.getMainDocumentType());
        msAssert.areEqual("SELECT * FROM `Contacts` ", mmSettings.getQuery());
        msAssert.areEqual(true, mmSettings.getViewMergedData());

        // Check ODSO settings.
        Odso odso = mmSettings.getOdso();
        msAssert.areEqual('\t', odso.getColumnDelimiter());
        msAssert.areEqual("Personal Folders|", odso.getDataSource());
        msAssert.areEqual(OdsoDataSourceType.EMAIL, odso.getDataSourceType());
        msAssert.areEqual(true, odso.getFirstRowContainsColumnNames());
        msAssert.areEqual("Contacts", odso.getTableName());

        // This cuts to the "common size" and converts "OLK58DD.tmp" to "OLK.tmp" so it can always compare ok.
        String udl = msString.remove(odso.getUdlConnectString().substring((0), (0) + (255)), 224, 4);
        msAssert.areEqual("Provider=Microsoft.ACE.OLEDB.12.0;User ID=Admin;Data Source=\"\";Mode=Share Deny None;Extended " +
                "Properties=\"Outlook 9.0;MAPILEVEL=Personal Folders|;TABLETYPE=0;DATABASE=C:\\Users\\romeok\\AppData" +
                "\\Local\\Temp\\OfficeMMergeTempDir\\OLK.tmp;COLSETVERSION=12.0\";Je", udl);

        // Check field map data.
        OdsoFieldMapDataCollection fieldMap = odso.getFieldMapDatas();
        msAssert.areEqual(30, fieldMap.getCount());
        
        // Check a mapped field.
        OdsoFieldMapData fieldMapData = fieldMap.get(0);
        msAssert.areEqual(62, fieldMapData.getColumn());
        msAssert.areEqual(OdsoFieldMappingType.COLUMN, fieldMapData.getType());
        msAssert.areEqual("Customer ID", fieldMapData.getName());
        msAssert.areEqual("Unique Identifier", fieldMapData.getMappedName());
        
        // Check a non mapped field
        fieldMapData = fieldMap.get(10);
        msAssert.areEqual(0, fieldMapData.getColumn());
        msAssert.areEqual(OdsoFieldMappingType.NULL, fieldMapData.getType());
        msAssert.areEqual("", fieldMapData.getName());
        msAssert.areEqual("", fieldMapData.getMappedName());


        // Check recipient data.
        OdsoRecipientDataCollection recipientDatas = odso.getRecipientDatas();
        msAssert.areEqual(70, recipientDatas.getCount());

        // Check an active recipient.
        OdsoRecipientData recipient = recipientDatas.get(0);
        msAssert.areEqual(true, recipient.getActive());
        msAssert.areEqual(62, recipient.getColumn());
        msAssert.isTrue(ArrayUtil.compareBytes(new byte[] { (byte) 0x56, (byte) 0x2e, (byte) 0x9f, (byte) 0x0b }, recipient.getUniqueTag(), 4));

        // Check an inactive recipient.
        recipient = recipientDatas.get(2);
        msAssert.areEqual(false, recipient.getActive());
        msAssert.areEqual(62, recipient.getColumn());
        msAssert.isTrue(ArrayUtil.compareBytes(new byte[] { (byte) 0xba, (byte) 0x4c, (byte) 0x56, (byte) 0x5c }, recipient.getUniqueTag(), 4));
    }

    /// <summary>
    /// MS Access data source and some other settings.
    /// </summary>
    @Test
    public void testMailMergeSettings2() throws Exception
    {
        for (/*UnifiedScenario*/int scenario : G_TEST_SCENARIOS)
            testMailMergeSettings2(scenario);
    }

    private static void testMailMergeSettings2(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\MailMerge\\TestMailMergeSettings2", scenario);

        // Check mail merge settings.
        MailMergeSettings mmSettings = doc.getDocPr().MailMergeSettings;
        msAssert.areEqual(1, mmSettings.getActiveRecord());
        msAssert.areEqual("", mmSettings.getAddressFieldName());
        msAssert.areEqual(MailMergeCheckErrors.PAUSE_ON_ERROR, mmSettings.getCheckErrors());

        String connectString =
            "Provider=Microsoft.ACE.OLEDB.12.0;User ID=Admin;Data Source=X:\\Aspose\\Aspose.Words\\Demos\\Common\\Database\\Northwind.mdb;" +
            "Mode=Read;Extended Properties=\"\";Jet OLEDB:System database=\"\";Jet OLEDB:Registry Path=\"\";Jet OLEDB:Engine Type=5;" +
            "Jet OLEDB:Database Locking Mode=0;Jet OLEDB:Global Partial Bulk Ops=2;Jet OLEDB:Global Bulk Transactions=1;Jet OLEDB:New Database Password=\"\";" +
            "Jet OLEDB:Create System Database=False;Jet OLEDB:Encrypt Database=False;Jet OLEDB:Don't Copy Locale on Compact=False;Jet OLEDB:Compact " +
            "Without Replica Repair=False;Jet OLEDB:SFP=False;Jet OLEDB:Support Complex Data=False";
        // In this case (probably because it is an ODSO connection), MS Word does not write this connection string into main mail
        // merge settings in the DOC format.
        if ((TestUtil.getLoadFormat(scenario) == LoadFormat.DOC) || (TestUtil.getLoadFormat(scenario) == LoadFormat.RTF))
            connectString = "";
        msAssert.areEqual(connectString, mmSettings.getConnectString());

        msAssert.areEqual("X:\\Aspose\\Aspose.Words\\Demos\\Common\\Database\\Northwind.mdb", mmSettings.getDataSource());
        msAssert.areEqual(MailMergeDataType.NATIVE, mmSettings.getDataType());
        msAssert.areEqual(MailMergeDestination.NEW_DOCUMENT, mmSettings.getDestination());
        msAssert.areEqual(false, mmSettings.getDoNotSupressBlankLines());
        msAssert.areEqual("", mmSettings.getHeaderSource());
        msAssert.areEqual(true, mmSettings.getLinkToQuery());
        msAssert.areEqual(false, mmSettings.getMailAsAttachment());
        msAssert.areEqual("", mmSettings.getMailSubject());
        msAssert.areEqual(MailMergeMainDocumentType.CATALOG, mmSettings.getMainDocumentType());
        msAssert.areEqual("SELECT * FROM `Customers`  ORDER BY `CompanyName` DESC ", mmSettings.getQuery());
        msAssert.areEqual(false, mmSettings.getViewMergedData());

        // Check ODSO settings.
        Odso odso = mmSettings.getOdso();
        msAssert.areEqual('\t', odso.getColumnDelimiter());
        msAssert.areEqual("X:\\Aspose\\Aspose.Words\\Demos\\Common\\Database\\Northwind.mdb", odso.getDataSource());
        msAssert.areEqual(OdsoDataSourceType.DATABASE, odso.getDataSourceType());
        msAssert.areEqual(true, odso.getFirstRowContainsColumnNames());
        msAssert.areEqual("Customers", odso.getTableName());

        // This cuts to the "common size" and converts "OLK58DD.tmp" to "OLK.tmp" so it can always compare ok.
        String udl = odso.getUdlConnectString().substring((0), (0) + (255));
        msAssert.areEqual("Provider=Microsoft.ACE.OLEDB.12.0;User ID=Admin;Data Source=X:\\Aspose\\Aspose.Words\\Demos\\Common\\Database\\Northwind.mdb;" +
                "Mode=Read;Extended Properties=\"\";Jet OLEDB:System database=\"\";Jet OLEDB:Registry Path=\"\";Jet OLEDB:Engine Type=5;Jet OLEDB:Database Lock", udl);

        // Check field map data.
        OdsoFieldMapDataCollection fieldMap = odso.getFieldMapDatas();
        msAssert.areEqual(30, fieldMap.getCount());

        // Check a mapped field.
        OdsoFieldMapData fieldMapData = fieldMap.get(0);
        msAssert.areEqual(0, fieldMapData.getColumn());
        msAssert.areEqual(OdsoFieldMappingType.COLUMN, fieldMapData.getType());
        msAssert.areEqual("CustomerID", fieldMapData.getName());
        msAssert.areEqual("Unique Identifier", fieldMapData.getMappedName());

        // Check a non mapped field
        fieldMapData = fieldMap.get(1);
        msAssert.areEqual(0, fieldMapData.getColumn());
        msAssert.areEqual(OdsoFieldMappingType.NULL, fieldMapData.getType());
        msAssert.areEqual("", fieldMapData.getName());
        msAssert.areEqual("", fieldMapData.getMappedName());


        // Check recipient data.
        OdsoRecipientDataCollection recipientDatas = odso.getRecipientDatas();
        switch (TestUtil.getLoadFormat(scenario))
        {
            case LoadFormat.DOCX:
            case LoadFormat.DOC:
            case LoadFormat.RTF:
            {
                msAssert.areEqual(91, recipientDatas.getCount());

                // Check an active recipient.
                OdsoRecipientData recipient = recipientDatas.get(0);
                msAssert.areEqual(true, recipient.getActive());
                msAssert.areEqual(0, recipient.getColumn());
                msAssert.areEqual(0, recipient.getHash());
                msAssert.areEqual("WOLZA", Encoding.getUnicode().getString(recipient.getUniqueTag(), 0, 10));
                break;
            }
            case LoadFormat.WORD_ML:
            {
                // For some reason there is one more recipient in WML.
                msAssert.areEqual(92, recipientDatas.getCount());

                // Check an active recipient.
                OdsoRecipientData recipient = recipientDatas.get(0);
                msAssert.areEqual(true, recipient.getActive());
                msAssert.areEqual(0, recipient.getColumn());
                // In this WML there is a "hash" value, but no "uniqueTag".
                msAssert.areEqual(5177431, recipient.getHash());
                msAssert.areEqual(null, recipient.getUniqueTag());
                break;
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }
    }


    private static void testMailMergeSettings97(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Dop\\MailMerge\\TestMailMergeSettings97", scenario);

        // Check mail merge settings.
        MailMergeSettings mmSettings = doc.getDocPr().MailMergeSettings;
        msAssert.areEqual(1, mmSettings.getActiveRecord());
        msAssert.areEqual("", mmSettings.getAddressFieldName());
        msAssert.areEqual(MailMergeCheckErrors.COLLECT_ERRORS, mmSettings.getCheckErrors());
        msAssert.areEqual("", mmSettings.getConnectString());
        msAssert.areEqual("X:\\Aspose\\Aspose.Words.TestData\\Model\\Dop\\MailMerge\\TestMailMergeSettings97_Data.doc", mmSettings.getDataSource());
        msAssert.areEqual(MailMergeDataType.TEXT_FILE, mmSettings.getDataType());
        msAssert.areEqual(MailMergeDestination.PRINTER, mmSettings.getDestination());
        msAssert.areEqual(true, mmSettings.getDoNotSupressBlankLines());
        msAssert.areEqual("X:\\Aspose\\Aspose.Words.TestData\\Model\\Dop\\MailMerge\\TestMailMergeSettings97_Header.doc", mmSettings.getHeaderSource());
        msAssert.areEqual(true, mmSettings.getLinkToQuery());
        msAssert.areEqual(false, mmSettings.getMailAsAttachment());
        msAssert.areEqual("", mmSettings.getMailSubject());
        msAssert.areEqual(MailMergeMainDocumentType.MAILING_LABELS, mmSettings.getMainDocumentType());
        msAssert.areEqual("SELECT * FROM X:\\Aspose\\Aspose.Words.TestData\\Model\\Dop\\MailMerge\\TestMailMergeSettings97_Data.doc", mmSettings.getQuery());
        msAssert.areEqual(true, mmSettings.getViewMergedData());

        // Check ODSO settings.
        Odso odso = mmSettings.getOdso();
        msAssert.areEqual('\u0000', odso.getColumnDelimiter());
        // Do not check odso.DataSource because it has some "left overs" in WML. DataSourceType is None anyway so ignore DataSource.
        msAssert.areEqual(OdsoDataSourceType.NONE, odso.getDataSourceType());
        msAssert.areEqual(false, odso.getFirstRowContainsColumnNames());
        msAssert.areEqual("", odso.getTableName());
        msAssert.areEqual("", odso.getUdlConnectString());

        // Check legacy separators. They are only preserved during doc2doc conversion, in all other cases they are default.
        if (scenario == UnifiedScenario.DOC_2_DOC)
        {
            msAssert.areEqual(MailMergeLegacySeparator.FIELD_END, mmSettings.getLegacyDataFieldSeparator());
            msAssert.areEqual(MailMergeLegacySeparator.TABLE_CELL, mmSettings.getLegacyDataRowSeparator());
            msAssert.areEqual(MailMergeLegacySeparator.FIELD_END, mmSettings.getLegacyHeaderFieldSeparator());
            msAssert.areEqual(MailMergeLegacySeparator.TABLE_CELL, mmSettings.getLegacyHeaderRowSeparator());
        }
        else
        {
            // WORDSNET-7853 LegacyDataFiledSeparator should be semicolon otherwise Word for Mac fails mail merge.
            msAssert.areEqual(MailMergeLegacySeparator.SEMICOLON, mmSettings.getLegacyDataFieldSeparator());
            msAssert.areEqual(MailMergeLegacySeparator.ENTER, mmSettings.getLegacyDataRowSeparator());
            msAssert.areEqual(MailMergeLegacySeparator.TAB, mmSettings.getLegacyHeaderFieldSeparator());
            msAssert.areEqual(MailMergeLegacySeparator.ENTER, mmSettings.getLegacyHeaderRowSeparator());
        }

        // Check field mapping.
        OdsoFieldMapDataCollection fieldMap = odso.getFieldMapDatas();
        switch (TestUtil.getLoadFormat(scenario))
        {
            case LoadFormat.DOC:
            case LoadFormat.RTF:
            {
                // This file was created using Word 97 and does not have field a map.
                msAssert.areEqual(0, fieldMap.getCount());
                break;
            }
            case LoadFormat.DOCX:
            case LoadFormat.WORD_ML:
            {
                // These files were created by Word 2007 and it always writes a field map.
                msAssert.areEqual(30, fieldMap.getCount());

                // Check a non mapped field
                OdsoFieldMapData fieldMapData = fieldMap.get(0);
                msAssert.areEqual(0, fieldMapData.getColumn());
                msAssert.areEqual(OdsoFieldMappingType.NULL, fieldMapData.getType());
                msAssert.areEqual("", fieldMapData.getName());
                msAssert.areEqual("", fieldMapData.getMappedName());

                // Check a mapped field.
                fieldMapData = fieldMap.get(1);
                msAssert.areEqual(0, fieldMapData.getColumn());
                msAssert.areEqual(OdsoFieldMappingType.COLUMN, fieldMapData.getType());
                msAssert.areEqual("Title", fieldMapData.getName());
                msAssert.areEqual("Courtesy Title", fieldMapData.getMappedName());
                break;
            }
            default:
                throw new IllegalStateException("Unknown file format.");
        }

        // Check recipient data.
        OdsoRecipientDataCollection recipientDatas = odso.getRecipientDatas();
        msAssert.areEqual(0, recipientDatas.getCount());
    }

    /// <summary>
    /// Tests that we write exactly 30 field map entries and in a specific order.
    /// </summary>
    /// <remarks>FOSS: originally exercised the DOC writer; the field-map validation is
    /// format-agnostic, so this now round-trips through DOCX (the .doc input/save were removed).</remarks>
    @Test
    public void testFieldMapValid() throws Exception
    {
        Document doc = TestUtil.open("Model\\Dop\\MailMerge\\TestMailMergeSettings1 ms.docx");
        OdsoFieldMapDataCollection fieldMap = doc.getDocPr().MailMergeSettings.getOdso().getFieldMapDatas();

        msAssert.areEqual(30, fieldMap.getCount());

        // Swap 2nd and 3rd items to verify they will be sorted back to order.
        OdsoFieldMapData temp = fieldMap.get(1);
        fieldMap.set(1, fieldMap.get(2));
        fieldMap.set(2, temp);

        // Remove one item to verify it will be added.
        fieldMap.removeAt(0);

        doc = TestUtil.saveOpen(doc, "Model\\Dop\\MailMerge\\TestFieldMapValid.docx", null, false);

        // Check field map data was corrected by the document validation.
        fieldMap = doc.getDocPr().MailMergeSettings.getOdso().getFieldMapDatas();
        msAssert.areEqual(30, fieldMap.getCount());

        // Check the deleted item got "restored".
        OdsoFieldMapData fieldMapData = fieldMap.get(0);
        msAssert.areEqual(0, fieldMapData.getColumn());
        msAssert.areEqual(OdsoFieldMappingType.NULL, fieldMapData.getType());
        msAssert.areEqual("", fieldMapData.getName());
        msAssert.areEqual("", fieldMapData.getMappedName());

        // Check the swappted item "came back".
        fieldMapData = fieldMap.get(1);
        msAssert.areEqual(2, fieldMapData.getColumn());
        msAssert.areEqual(OdsoFieldMappingType.COLUMN, fieldMapData.getType());
        msAssert.areEqual("Title", fieldMapData.getName());
        msAssert.areEqual("Courtesy Title", fieldMapData.getMappedName());
    }

    // FOSS: TestDefect6392 removed — it preserves a mail-merge data source through a binary
    // .doc open/save (removed format) and its input is a customer .doc.

    @Test
    public void testClear() throws Exception
    {
        // FOSS: repointed to the DOCX input + no-gold DOCX round-trip; Clear() is model-level.
        Document doc = TestUtil.open("Model\\Dop\\MailMerge\\TestMailMergeSettings1 ms.docx");
        doc.getMailMergeSettings().clear();

        doc = TestUtil.saveOpen(doc, "Model\\Dop\\MailMerge\\TestClear.docx", null, false);
        msAssert.areEqual(MailMergeDataType.NONE, doc.getMailMergeSettings().getDataType());
    }

    // FOSS: TestCreateFile / TestCreateFileCustom / TestCreateFileOdso (and their CreateSampleDoc
    // helper) were removed. They only saved a sample document to the removed .doc format for manual
    // inspection in Word (no assertions), and TestCreateFileCustom relied on DOC-only legacy
    // field/record separators. MailMergeSettings serialization to DOCX is covered by the tests above.
}

