// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.StreamReader;


/// <summary>
/// Tests how different field types are created and modified.
/// </summary>
@Test
public class TestFieldTypes extends TestFieldsBase
{
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QField type 'FieldCannotParse' is invalid or not supported.\\E")
    public void testCannotParse() throws Exception
    {
        createField(FieldType.FIELD_CANNOT_PARSE);
    }

    @Test
    public void testAddIn() throws Exception
    {
        FieldAddIn field = (FieldAddIn)createField(FieldType.FIELD_ADDIN);

        msAssert.areEqual(FieldType.FIELD_ADDIN, field.getType());
        msAssert.areEqual(" ADDIN ", field.getFieldCode());
    }

    @Test
    public void testAddressBlock() throws Exception
    {
        FieldAddressBlock field = (FieldAddressBlock)createField(FieldType.FIELD_ADDRESS_BLOCK);

        msAssert.areEqual(FieldType.FIELD_ADDRESS_BLOCK, field.getType());
        msAssert.areEqual(" ADDRESSBLOCK ", field.getFieldCode());

        msAssert.isNull(field.getIncludeCountryOrRegionName());
        msAssert.isFalse(field.getFormatAddressOnCountryOrRegion());
        msAssert.isNull(field.getExcludedCountryOrRegionName());
        msAssert.isNull(field.getNameAndAddressFormat());
        msAssert.isNull(field.getLanguageId());

        field.setIncludeCountryOrRegionName("1");
        msAssert.areEqual(" ADDRESSBLOCK  \\c 1", field.getFieldCode());

        field.setFormatAddressOnCountryOrRegion(true);
        msAssert.areEqual(" ADDRESSBLOCK  \\c 1 \\d", field.getFieldCode());

        field.setExcludedCountryOrRegionName("Test2");
        msAssert.areEqual(" ADDRESSBLOCK  \\c 1 \\d \\e Test2", field.getFieldCode());

        field.setNameAndAddressFormat("Test3");
        msAssert.areEqual(" ADDRESSBLOCK  \\c 1 \\d \\e Test2 \\f Test3", field.getFieldCode());

        field.setLanguageId("Test 4");
        msAssert.areEqual(" ADDRESSBLOCK  \\c 1 \\d \\e Test2 \\f Test3 \\l \"Test 4\"", field.getFieldCode());

        msAssert.areEqual("1", field.getIncludeCountryOrRegionName());
        msAssert.isTrue(field.getFormatAddressOnCountryOrRegion());
        msAssert.areEqual("Test2", field.getExcludedCountryOrRegionName());
        msAssert.areEqual("Test3", field.getNameAndAddressFormat());
        msAssert.areEqual("Test 4", field.getLanguageId());
    }

    @Test
    public void testAdvance() throws Exception
    {
        FieldAdvance field = (FieldAdvance)createField(FieldType.FIELD_ADVANCE);

        msAssert.areEqual(FieldType.FIELD_ADVANCE, field.getType());
        msAssert.areEqual(" ADVANCE ", field.getFieldCode());

        msAssert.isNull(field.getDownOffset());
        msAssert.isNull(field.getLeftOffset());
        msAssert.isNull(field.getRightOffset());
        msAssert.isNull(field.getUpOffset());
        msAssert.isNull(field.getHorizontalPosition());
        msAssert.isNull(field.getVerticalPosition());

        field.setDownOffset("10");
        msAssert.areEqual(" ADVANCE  \\d 10", field.getFieldCode());

        field.setLeftOffset("10");
        msAssert.areEqual(" ADVANCE  \\d 10 \\l 10", field.getFieldCode());

        field.setRightOffset("-3.3");
        msAssert.areEqual(" ADVANCE  \\d 10 \\l 10 \\r -3.3", field.getFieldCode());

        field.setUpOffset("0");
        msAssert.areEqual(" ADVANCE  \\d 10 \\l 10 \\r -3.3 \\u 0", field.getFieldCode());

        field.setHorizontalPosition("100");
        msAssert.areEqual(" ADVANCE  \\d 10 \\l 10 \\r -3.3 \\u 0 \\x 100", field.getFieldCode());

        field.setVerticalPosition("100");
        msAssert.areEqual(" ADVANCE  \\d 10 \\l 10 \\r -3.3 \\u 0 \\x 100 \\y 100", field.getFieldCode());

        msAssert.areEqual("10", field.getDownOffset());
        msAssert.areEqual("10", field.getLeftOffset());
        msAssert.areEqual("-3.3", field.getRightOffset());
        msAssert.areEqual("0", field.getUpOffset());
        msAssert.areEqual("100", field.getHorizontalPosition());
        msAssert.areEqual("100", field.getVerticalPosition());
    }

    @Test
    public void testAsk() throws Exception
    {
        FieldAsk field = (FieldAsk)createField(FieldType.FIELD_ASK);

        msAssert.areEqual(FieldType.FIELD_ASK, field.getType());
        msAssert.areEqual(" ASK ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isNull(field.getPromptText());
        msAssert.isNull(field.getDefaultResponse());
        msAssert.isFalse(field.getPromptOnceOnMailMerge());

        field.setBookmarkName("Test 1");
        msAssert.areEqual(" ASK  \"Test 1\"", field.getFieldCode());

        field.setPromptText("Test2");
        msAssert.areEqual(" ASK  \"Test 1\" Test2", field.getFieldCode());

        field.setDefaultResponse("Test3");
        msAssert.areEqual(" ASK  \"Test 1\" Test2 \\d Test3", field.getFieldCode());

        field.setPromptOnceOnMailMerge(true);
        msAssert.areEqual(" ASK  \"Test 1\" Test2 \\d Test3 \\o", field.getFieldCode());

        msAssert.areEqual("Test 1", field.getBookmarkName());
        msAssert.areEqual("Test2", field.getPromptText());
        msAssert.areEqual("Test3", field.getDefaultResponse());
        msAssert.isTrue(field.getPromptOnceOnMailMerge());
    }

    @Test
    public void testAuthor() throws Exception
    {
        FieldAuthor field = (FieldAuthor)createField(FieldType.FIELD_AUTHOR);

        msAssert.areEqual(FieldType.FIELD_AUTHOR, field.getType());
        msAssert.areEqual(" AUTHOR ", field.getFieldCode());

        msAssert.isNull(field.getAuthorName());

        field.setAuthorName("Test1");
        msAssert.areEqual(" AUTHOR  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getAuthorName());
    }

    @Test
    public void testAutoNum() throws Exception
    {
        FieldAutoNum field = (FieldAutoNum)createField(FieldType.FIELD_AUTO_NUM);

        msAssert.areEqual(FieldType.FIELD_AUTO_NUM, field.getType());
        msAssert.areEqual(" AUTONUM ", field.getFieldCode());

        msAssert.isNull(field.getSeparatorCharacter());

        field.setSeparatorCharacter("Test1");
        msAssert.areEqual(" AUTONUM  \\s Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSeparatorCharacter());
    }

    @Test
    public void testAutoNumLgl() throws Exception
    {
        FieldAutoNumLgl field = (FieldAutoNumLgl)createField(FieldType.FIELD_AUTO_NUM_LEGAL);

        msAssert.areEqual(FieldType.FIELD_AUTO_NUM_LEGAL, field.getType());
        msAssert.areEqual(" AUTONUMLGL ", field.getFieldCode());

        msAssert.isNull(field.getSeparatorCharacter());
        msAssert.isFalse(field.getRemoveTrailingPeriod());

        field.setSeparatorCharacter("Test1");
        msAssert.areEqual(" AUTONUMLGL  \\s Test1", field.getFieldCode());

        field.setRemoveTrailingPeriod(true);
        msAssert.areEqual(" AUTONUMLGL  \\s Test1 \\e", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSeparatorCharacter());
        msAssert.isTrue(field.getRemoveTrailingPeriod());
    }

    @Test
    public void testAutoNumOut() throws Exception
    {
        FieldAutoNumOut field = (FieldAutoNumOut)createField(FieldType.FIELD_AUTO_NUM_OUTLINE);

        msAssert.areEqual(FieldType.FIELD_AUTO_NUM_OUTLINE, field.getType());
        msAssert.areEqual(" AUTONUMOUT ", field.getFieldCode());
    }

    @Test
    public void testAutoText() throws Exception
    {
        FieldAutoText field = (FieldAutoText)createField(FieldType.FIELD_AUTO_TEXT);

        msAssert.areEqual(FieldType.FIELD_AUTO_TEXT, field.getType());
        msAssert.areEqual(" AUTOTEXT ", field.getFieldCode());

        msAssert.isNull(field.getEntryName());

        field.setEntryName("Test1");
        msAssert.areEqual(" AUTOTEXT  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getEntryName());
    }

    @Test
    public void testAutoTextList() throws Exception
    {
        FieldAutoTextList field = (FieldAutoTextList)createField(FieldType.FIELD_AUTO_TEXT_LIST);

        msAssert.areEqual(FieldType.FIELD_AUTO_TEXT_LIST, field.getType());
        msAssert.areEqual(" AUTOTEXTLIST ", field.getFieldCode());

        msAssert.isNull(field.getEntryName());
        msAssert.isNull(field.getListStyle());
        msAssert.isNull(field.getScreenTip());

        field.setEntryName("Test1");
        msAssert.areEqual(" AUTOTEXTLIST  Test1", field.getFieldCode());

        field.setListStyle("Test 2");
        msAssert.areEqual(" AUTOTEXTLIST  Test1 \\s \"Test 2\"", field.getFieldCode());

        field.setScreenTip("Test3");
        msAssert.areEqual(" AUTOTEXTLIST  Test1 \\s \"Test 2\" \\t Test3", field.getFieldCode());

        msAssert.areEqual("Test1", field.getEntryName());
        msAssert.areEqual("Test 2", field.getListStyle());
        msAssert.areEqual("Test3", field.getScreenTip());
    }

    @Test
    public void testBarcode() throws Exception
    {
        FieldBarcode field = (FieldBarcode)createField(FieldType.FIELD_BARCODE);

        msAssert.areEqual(FieldType.FIELD_BARCODE, field.getType());
        msAssert.areEqual(" BARCODE ", field.getFieldCode());

        msAssert.isNull(field.getPostalAddress());
        msAssert.isFalse(field.isBookmark());
        msAssert.isNull(field.getFacingIdentificationMark());
        msAssert.isFalse(field.isUSPostalAddress());

        field.setPostalAddress("Test1");
        msAssert.areEqual(" BARCODE  Test1", field.getFieldCode());

        field.isBookmark(true);
        msAssert.areEqual(" BARCODE  Test1 \\b", field.getFieldCode());

        field.setFacingIdentificationMark("Test2");
        msAssert.areEqual(" BARCODE  Test1 \\b \\f Test2", field.getFieldCode());

        field.isUSPostalAddress(true);
        msAssert.areEqual(" BARCODE  Test1 \\b \\f Test2 \\u", field.getFieldCode());

        msAssert.areEqual("Test1", field.getPostalAddress());
        msAssert.isTrue(field.isBookmark());
        msAssert.areEqual("Test2", field.getFacingIdentificationMark());
        msAssert.isTrue(field.isUSPostalAddress());
    }

    @Test
    public void testBibliography() throws Exception
    {
        FieldBibliography field = (FieldBibliography)createField(FieldType.FIELD_BIBLIOGRAPHY);

        msAssert.areEqual(FieldType.FIELD_BIBLIOGRAPHY, field.getType());
        msAssert.areEqual(" BIBLIOGRAPHY ", field.getFieldCode());

        msAssert.isNull(field.getFormatLanguageId());
        msAssert.isNull(field.getFilterLanguageId());
        msAssert.isNull(field.getSourceTag());

        field.setFormatLanguageId("Test1");
        msAssert.areEqual(" BIBLIOGRAPHY  \\l Test1", field.getFieldCode());

        field.setFilterLanguageId("Test2");
        msAssert.areEqual(" BIBLIOGRAPHY  \\l Test1 \\f Test2", field.getFieldCode());

        field.setSourceTag("Test3");
        msAssert.areEqual(" BIBLIOGRAPHY  \\l Test1 \\f Test2 \\m Test3", field.getFieldCode());

        msAssert.areEqual("Test1", field.getFormatLanguageId());
        msAssert.areEqual("Test2", field.getFilterLanguageId());
        msAssert.areEqual("Test3", field.getSourceTag());
    }

    @Test
    public void testBidiOutline() throws Exception
    {
        FieldBidiOutline field = (FieldBidiOutline)createField(FieldType.FIELD_BIDI_OUTLINE);

        msAssert.areEqual(FieldType.FIELD_BIDI_OUTLINE, field.getType());
        msAssert.areEqual(" BIDIOUTLINE ", field.getFieldCode());
    }

    @Test
    public void testCitation() throws Exception
    {
        FieldCitation field = (FieldCitation)createField(FieldType.FIELD_CITATION);

        msAssert.areEqual(FieldType.FIELD_CITATION, field.getType());
        msAssert.areEqual(" CITATION ", field.getFieldCode());

        msAssert.isNull(field.getSourceTag());
        msAssert.isNull(field.getFormatLanguageId());
        msAssert.isNull(field.getPrefix());
        msAssert.isNull(field.getSuffix());
        msAssert.isFalse(field.getSuppressAuthor());
        msAssert.isFalse(field.getSuppressTitle());
        msAssert.isFalse(field.getSuppressYear());
        msAssert.isNull(field.getPageNumber());
        msAssert.isNull(field.getVolumeNumber());
        msAssert.isNull(field.getAnotherSourceTag());

        field.setSourceTag("Test1");
        msAssert.areEqual(" CITATION  Test1", field.getFieldCode());

        field.setFormatLanguageId("Test2");
        msAssert.areEqual(" CITATION  Test1 \\l Test2", field.getFieldCode());

        field.setPrefix("Test3");
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3", field.getFieldCode());

        field.setSuffix("Test4");
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4", field.getFieldCode());

        field.setSuppressAuthor(true);
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4 \\n", field.getFieldCode());

        field.setSuppressTitle(true);
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4 \\n \\t", field.getFieldCode());

        field.setSuppressYear(true);
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4 \\n \\t \\y", field.getFieldCode());

        field.setPageNumber("Test5");
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4 \\n \\t \\y \\p Test5", field.getFieldCode());

        field.setVolumeNumber("Test6");
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4 \\n \\t \\y \\p Test5 \\v Test6", field.getFieldCode());

        field.setAnotherSourceTag("Test7");
        msAssert.areEqual(" CITATION  Test1 \\l Test2 \\f Test3 \\s Test4 \\n \\t \\y \\p Test5 \\v Test6 \\m Test7", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSourceTag());
        msAssert.areEqual("Test2", field.getFormatLanguageId());
        msAssert.areEqual("Test3", field.getPrefix());
        msAssert.areEqual("Test4", field.getSuffix());
        msAssert.isTrue(field.getSuppressAuthor());
        msAssert.isTrue(field.getSuppressTitle());
        msAssert.isTrue(field.getSuppressYear());
        msAssert.areEqual("Test5", field.getPageNumber());
        msAssert.areEqual("Test6", field.getVolumeNumber());
        msAssert.areEqual("Test7", field.getAnotherSourceTag());
    }

    @Test
    public void testComments() throws Exception
    {
        FieldComments field = (FieldComments)createField(FieldType.FIELD_COMMENTS);

        msAssert.areEqual(FieldType.FIELD_COMMENTS, field.getType());
        msAssert.areEqual(" COMMENTS ", field.getFieldCode());

        msAssert.isNull(field.getText());

        field.setText("Test1");
        msAssert.areEqual(" COMMENTS  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getText());
    }

    @Test
    public void testCompare() throws Exception
    {
        FieldCompare field = (FieldCompare)createField(FieldType.FIELD_COMPARE);

        msAssert.areEqual(FieldType.FIELD_COMPARE, field.getType());
        msAssert.areEqual(" COMPARE ", field.getFieldCode());

        msAssert.isNull(field.getLeftExpression());
        msAssert.isNull(field.getComparisonOperator());
        msAssert.isNull(field.getRightExpression());

        field.setLeftExpression("123");
        field.setComparisonOperator(">=");
        field.setRightExpression("a b c");
        msAssert.areEqual(" COMPARE  123 >= \"a b c\"", field.getFieldCode());

        msAssert.areEqual("123", field.getLeftExpression());
        msAssert.areEqual(">=", field.getComparisonOperator());
        msAssert.areEqual("\"a b c\"", field.getRightExpression());
    }

    @Test
    public void testCreateDate() throws Exception
    {
        FieldCreateDate field = (FieldCreateDate)createField(FieldType.FIELD_CREATE_DATE);

        msAssert.areEqual(FieldType.FIELD_CREATE_DATE, field.getType());
        msAssert.areEqual(" CREATEDATE ", field.getFieldCode());

        msAssert.isFalse(field.getUseLunarCalendar());
        msAssert.isFalse(field.getUseSakaEraCalendar());
        msAssert.isFalse(field.getUseUmAlQuraCalendar());

        field.setUseLunarCalendar(true);
        msAssert.areEqual(" CREATEDATE  \\h", field.getFieldCode());

        field.setUseSakaEraCalendar(true);
        msAssert.areEqual(" CREATEDATE  \\h \\s", field.getFieldCode());

        field.setUseUmAlQuraCalendar(true);
        msAssert.areEqual(" CREATEDATE  \\h \\s \\u", field.getFieldCode());

        msAssert.isTrue(field.getUseLunarCalendar());
        msAssert.isTrue(field.getUseSakaEraCalendar());
        msAssert.isTrue(field.getUseUmAlQuraCalendar());
    }

    @Test
    public void testData() throws Exception
    {
        FieldData field = (FieldData)createField(FieldType.FIELD_DATA);

        msAssert.areEqual(FieldType.FIELD_DATA, field.getType());
        msAssert.areEqual(" DATA ", field.getFieldCode());
    }

    @Test
    public void testDatabase() throws Exception
    {
        FieldDatabase field = (FieldDatabase)createField(FieldType.FIELD_DATABASE);

        msAssert.areEqual(FieldType.FIELD_DATABASE, field.getType());
        msAssert.areEqual(" DATABASE ", field.getFieldCode());

        msAssert.isNull(field.getFormatAttributes());
        msAssert.isNull(field.getConnection());
        msAssert.isNull(field.getFileName());
        msAssert.isNull(field.getFirstRecord());
        msAssert.isFalse(field.getInsertHeadings());
        msAssert.isNull(field.getTableFormat());
        msAssert.isFalse(field.getInsertOnceOnMailMerge());
        msAssert.isNull(field.getQuery());
        msAssert.isNull(field.getLastRecord());

        field.setFormatAttributes("16");
        msAssert.areEqual(" DATABASE  \\b 16", field.getFieldCode());

        field.setConnection("Test1");
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1", field.getFieldCode());

        field.setFileName("Test2");
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2", field.getFieldCode());

        field.setFirstRecord("0");
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2 \\f 0", field.getFieldCode());

        field.setInsertHeadings(true);
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2 \\f 0 \\h", field.getFieldCode());

        field.setTableFormat("2");
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2 \\f 0 \\h \\l 2", field.getFieldCode());

        field.setInsertOnceOnMailMerge(true);
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2 \\f 0 \\h \\l 2 \\o", field.getFieldCode());

        field.setQuery("Test3");
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2 \\f 0 \\h \\l 2 \\o \\s Test3", field.getFieldCode());

        field.setLastRecord("10");
        msAssert.areEqual(" DATABASE  \\b 16 \\c Test1 \\d Test2 \\f 0 \\h \\l 2 \\o \\s Test3 \\t 10", field.getFieldCode());

        msAssert.areEqual("16", field.getFormatAttributes());
        msAssert.areEqual("Test1", field.getConnection());
        msAssert.areEqual("Test2", field.getFileName());
        msAssert.areEqual("0", field.getFirstRecord());
        msAssert.isTrue(field.getInsertHeadings());
        msAssert.areEqual("2", field.getTableFormat());
        msAssert.isTrue(field.getInsertOnceOnMailMerge());
        msAssert.areEqual("Test3", field.getQuery());
        msAssert.areEqual("10", field.getLastRecord());
    }

    @Test
    public void testDate() throws Exception
    {
        FieldDate field = (FieldDate)createField(FieldType.FIELD_DATE);

        msAssert.areEqual(FieldType.FIELD_DATE, field.getType());
        msAssert.areEqual(" DATE ", field.getFieldCode());

        msAssert.isFalse(field.getUseLunarCalendar());
        msAssert.isFalse(field.getUseLastFormat());
        msAssert.isFalse(field.getUseSakaEraCalendar());
        msAssert.isFalse(field.getUseUmAlQuraCalendar());

        field.setUseLunarCalendar(true);
        msAssert.areEqual(" DATE  \\h", field.getFieldCode());

        field.setUseLastFormat(true);
        msAssert.areEqual(" DATE  \\h \\l", field.getFieldCode());

        field.setUseSakaEraCalendar(true);
        msAssert.areEqual(" DATE  \\h \\l \\s", field.getFieldCode());

        field.setUseUmAlQuraCalendar(true);
        msAssert.areEqual(" DATE  \\h \\l \\s \\u", field.getFieldCode());

        msAssert.isTrue(field.getUseLunarCalendar());
        msAssert.isTrue(field.getUseLastFormat());
        msAssert.isTrue(field.getUseSakaEraCalendar());
        msAssert.isTrue(field.getUseUmAlQuraCalendar());
    }

    @Test
    public void testDde() throws Exception
    {
        FieldDde field = (FieldDde)createField(FieldType.FIELD_DDE);

        msAssert.areEqual(FieldType.FIELD_DDE, field.getType());
        msAssert.areEqual(" DDE ", field.getFieldCode());

        msAssert.isNull(field.getProgId());
        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getSourceItem());
        msAssert.isFalse(field.getAutoUpdate());
        msAssert.isFalse(field.getInsertAsBitmap());
        msAssert.isFalse(field.isLinked());
        msAssert.isFalse(field.getInsertAsHtml());
        msAssert.isFalse(field.getInsertAsPicture());
        msAssert.isFalse(field.getInsertAsRtf());
        msAssert.isFalse(field.getInsertAsText());
        msAssert.isFalse(field.getInsertAsUnicode());

        field.setProgId("Test1");
        msAssert.areEqual(" DDE  Test1", field.getFieldCode());

        field.setSourceFullName("Test2");
        msAssert.areEqual(" DDE  Test1 Test2", field.getFieldCode());

        field.setSourceItem("Test3");
        msAssert.areEqual(" DDE  Test1 Test2 Test3", field.getFieldCode());

        field.setAutoUpdate(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a", field.getFieldCode());

        field.setInsertAsBitmap(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b", field.getFieldCode());

        field.isLinked(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b \\d", field.getFieldCode());

        field.setInsertAsHtml(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b \\d \\h", field.getFieldCode());

        field.setInsertAsPicture(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b \\d \\h \\p", field.getFieldCode());

        field.setInsertAsRtf(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b \\d \\h \\p \\r", field.getFieldCode());

        field.setInsertAsText(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b \\d \\h \\p \\r \\t", field.getFieldCode());

        field.setInsertAsUnicode(true);
        msAssert.areEqual(" DDE  Test1 Test2 Test3 \\a \\b \\d \\h \\p \\r \\t \\u", field.getFieldCode());

        msAssert.areEqual("Test1", field.getProgId());
        msAssert.areEqual("Test2", field.getSourceFullName());
        msAssert.areEqual("Test3", field.getSourceItem());
        msAssert.isTrue(field.getAutoUpdate());
        msAssert.isTrue(field.getInsertAsBitmap());
        msAssert.isTrue(field.isLinked());
        msAssert.isTrue(field.getInsertAsHtml());
        msAssert.isTrue(field.getInsertAsPicture());
        msAssert.isTrue(field.getInsertAsRtf());
        msAssert.isTrue(field.getInsertAsText());
        msAssert.isTrue(field.getInsertAsUnicode());
    }

    @Test
    public void testDdeAuto() throws Exception
    {
        FieldDdeAuto field = (FieldDdeAuto)createField(FieldType.FIELD_DDE_AUTO);

        msAssert.areEqual(FieldType.FIELD_DDE_AUTO, field.getType());
        msAssert.areEqual(" DDEAUTO ", field.getFieldCode());

        msAssert.isNull(field.getProgId());
        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getSourceItem());
        msAssert.isFalse(field.getInsertAsBitmap());
        msAssert.isFalse(field.isLinked());
        msAssert.isFalse(field.getInsertAsHtml());
        msAssert.isFalse(field.getInsertAsPicture());
        msAssert.isFalse(field.getInsertAsRtf());
        msAssert.isFalse(field.getInsertAsText());
        msAssert.isFalse(field.getInsertAsUnicode());

        field.setProgId("Test1");
        msAssert.areEqual(" DDEAUTO  Test1", field.getFieldCode());

        field.setSourceFullName("Test2");
        msAssert.areEqual(" DDEAUTO  Test1 Test2", field.getFieldCode());

        field.setSourceItem("Test3");
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3", field.getFieldCode());

        field.setInsertAsBitmap(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b", field.getFieldCode());

        field.isLinked(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b \\d", field.getFieldCode());

        field.setInsertAsHtml(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b \\d \\h", field.getFieldCode());

        field.setInsertAsPicture(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b \\d \\h \\p", field.getFieldCode());

        field.setInsertAsRtf(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b \\d \\h \\p \\r", field.getFieldCode());

        field.setInsertAsText(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b \\d \\h \\p \\r \\t", field.getFieldCode());

        field.setInsertAsUnicode(true);
        msAssert.areEqual(" DDEAUTO  Test1 Test2 Test3 \\b \\d \\h \\p \\r \\t \\u", field.getFieldCode());

        msAssert.areEqual("Test1", field.getProgId());
        msAssert.areEqual("Test2", field.getSourceFullName());
        msAssert.areEqual("Test3", field.getSourceItem());
        msAssert.isTrue(field.getInsertAsBitmap());
        msAssert.isTrue(field.isLinked());
        msAssert.isTrue(field.getInsertAsHtml());
        msAssert.isTrue(field.getInsertAsPicture());
        msAssert.isTrue(field.getInsertAsRtf());
        msAssert.isTrue(field.getInsertAsText());
        msAssert.isTrue(field.getInsertAsUnicode());

    }

    @Test
    public void testDocProperty() throws Exception
    {
        FieldDocProperty field = (FieldDocProperty)createField(FieldType.FIELD_DOC_PROPERTY);

        msAssert.areEqual(FieldType.FIELD_DOC_PROPERTY, field.getType());
        msAssert.areEqual(" DOCPROPERTY ", field.getFieldCode());

        msAssert.isNull(field.getPropertyName());

        field.setPropertyName("Test1");
        msAssert.areEqual(" DOCPROPERTY  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getPropertyName());
    }

    @Test
    public void testDocVariable() throws Exception
    {
        FieldDocVariable field = (FieldDocVariable)createField(FieldType.FIELD_DOC_VARIABLE);

        msAssert.areEqual(FieldType.FIELD_DOC_VARIABLE, field.getType());
        msAssert.areEqual(" DOCVARIABLE ", field.getFieldCode());

        msAssert.isNull(field.getVariableName());

        field.setVariableName("Test1");
        msAssert.areEqual(" DOCVARIABLE  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getVariableName());
    }

    @Test
    public void testEditTime() throws Exception
    {
        FieldEditTime field = (FieldEditTime)createField(FieldType.FIELD_EDIT_TIME);

        msAssert.areEqual(FieldType.FIELD_EDIT_TIME, field.getType());
        msAssert.areEqual(" EDITTIME ", field.getFieldCode());
    }

    @Test
    public void testEmbed() throws Exception
    {
        FieldEmbed field = (FieldEmbed)createField(FieldType.FIELD_EMBED);

        msAssert.areEqual(FieldType.FIELD_EMBED, field.getType());
        msAssert.areEqual(" EMBED ", field.getFieldCode());
    }

    @Test
    public void testEQ() throws Exception
    {
        FieldEQ field = (FieldEQ)createField(FieldType.FIELD_EQUATION);

        msAssert.areEqual(FieldType.FIELD_EQUATION, field.getType());
        msAssert.areEqual(" EQ ", field.getFieldCode());
    }

    @Test
    public void testFileName() throws Exception
    {
        FieldFileName field = (FieldFileName)createField(FieldType.FIELD_FILE_NAME);

        msAssert.areEqual(FieldType.FIELD_FILE_NAME, field.getType());
        msAssert.areEqual(" FILENAME ", field.getFieldCode());

        msAssert.isFalse(field.getIncludeFullPath());

        field.setIncludeFullPath(true);
        msAssert.areEqual(" FILENAME  \\p", field.getFieldCode());

        msAssert.isTrue(field.getIncludeFullPath());
    }

    @Test
    public void testFileSize() throws Exception
    {
        FieldFileSize field = (FieldFileSize)createField(FieldType.FIELD_FILE_SIZE);

        msAssert.areEqual(FieldType.FIELD_FILE_SIZE, field.getType());
        msAssert.areEqual(" FILESIZE ", field.getFieldCode());

        msAssert.isFalse(field.isInKilobytes());
        msAssert.isFalse(field.isInMegabytes());

        field.isInKilobytes(true);
        msAssert.areEqual(" FILESIZE  \\k", field.getFieldCode());

        field.isInMegabytes(true);
        msAssert.areEqual(" FILESIZE  \\k \\m", field.getFieldCode());

        msAssert.isTrue(field.isInKilobytes());
        msAssert.isTrue(field.isInMegabytes());
    }

    @Test
    public void testFillIn() throws Exception
    {
        FieldFillIn field = (FieldFillIn)createField(FieldType.FIELD_FILL_IN);

        msAssert.areEqual(FieldType.FIELD_FILL_IN, field.getType());
        msAssert.areEqual(" FILLIN ", field.getFieldCode());

        msAssert.isNull(field.getPromptText());
        msAssert.isNull(field.getDefaultResponse());
        msAssert.isFalse(field.getPromptOnceOnMailMerge());

        field.setPromptText("Test1");
        msAssert.areEqual(" FILLIN  Test1", field.getFieldCode());

        field.setDefaultResponse("Test2");
        msAssert.areEqual(" FILLIN  Test1 \\d Test2", field.getFieldCode());

        field.setPromptOnceOnMailMerge(true);
        msAssert.areEqual(" FILLIN  Test1 \\d Test2 \\o", field.getFieldCode());

        msAssert.areEqual("Test1", field.getPromptText());
        msAssert.areEqual("Test2", field.getDefaultResponse());
        msAssert.isTrue(field.getPromptOnceOnMailMerge());
    }

    @Test
    public void testFootnoteRef() throws Exception
    {
        FieldFootnoteRef field = (FieldFootnoteRef)createField(FieldType.FIELD_FOOTNOTE_REF);

        msAssert.areEqual(FieldType.FIELD_FOOTNOTE_REF, field.getType());
        msAssert.areEqual(" FOOTNOTEREF ", field.getFieldCode());
    }

    @Test
    public void testFormCheckBox() throws Exception
    {
        FieldFormCheckBox field = (FieldFormCheckBox)createField(FieldType.FIELD_FORM_CHECK_BOX);

        msAssert.areEqual(FieldType.FIELD_FORM_CHECK_BOX, field.getType());
        msAssert.areEqual(" FORMCHECKBOX ", field.getFieldCode());
    }

    @Test
    public void testFormDropDown() throws Exception
    {
        FieldFormDropDown field = (FieldFormDropDown)createField(FieldType.FIELD_FORM_DROP_DOWN);

        msAssert.areEqual(FieldType.FIELD_FORM_DROP_DOWN, field.getType());
        msAssert.areEqual(" FORMDROPDOWN ", field.getFieldCode());
    }

    @Test
    public void testFormText() throws Exception
    {
        FieldFormText field = (FieldFormText)createField(FieldType.FIELD_FORM_TEXT_INPUT);

        msAssert.areEqual(FieldType.FIELD_FORM_TEXT_INPUT, field.getType());
        msAssert.areEqual(" FORMTEXT ", field.getFieldCode());
    }

    @Test
    public void testFormula() throws Exception
    {
        FieldFormula field = (FieldFormula)createField(FieldType.FIELD_FORMULA);

        msAssert.areEqual(FieldType.FIELD_FORMULA, field.getType());
        msAssert.areEqual(" = ", field.getFieldCode());
    }

    @Test
    public void testGlossary() throws Exception
    {
        FieldGlossary field = (FieldGlossary)createField(FieldType.FIELD_GLOSSARY);

        msAssert.areEqual(FieldType.FIELD_GLOSSARY, field.getType());
        msAssert.areEqual(" GLOSSARY ", field.getFieldCode());

        msAssert.isNull(field.getEntryName());

        field.setEntryName("Test1");
        msAssert.areEqual(" GLOSSARY  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getEntryName());
    }

    @Test
    public void testGoToButton() throws Exception
    {
        FieldGoToButton field = (FieldGoToButton)createField(FieldType.FIELD_GO_TO_BUTTON);

        msAssert.areEqual(FieldType.FIELD_GO_TO_BUTTON, field.getType());
        msAssert.areEqual(" GOTOBUTTON ", field.getFieldCode());

        msAssert.isNull(field.getLocation());
        msAssert.isNull(field.getDisplayText());

        field.setLocation("Test1");
        msAssert.areEqual(" GOTOBUTTON  Test1", field.getFieldCode());

        field.setDisplayText("Test 2");
        msAssert.areEqual(" GOTOBUTTON  Test1 Test 2", field.getFieldCode());

        msAssert.areEqual("Test1", field.getLocation());
        msAssert.areEqual("Test 2", field.getDisplayText());
    }

    @Test
    public void testGreetingLine() throws Exception
    {
        FieldGreetingLine field = (FieldGreetingLine)createField(FieldType.FIELD_GREETING_LINE);

        msAssert.areEqual(FieldType.FIELD_GREETING_LINE, field.getType());
        msAssert.areEqual(" GREETINGLINE ", field.getFieldCode());

        msAssert.isNull(field.getAlternateText());
        msAssert.isNull(field.getNameFormat());
        msAssert.isNull(field.getLanguageId());

        field.setAlternateText("Test1");
        msAssert.areEqual(" GREETINGLINE  \\e Test1", field.getFieldCode());

        field.setNameFormat("Test 2");
        msAssert.areEqual(" GREETINGLINE  \\e Test1 \\f \"Test 2\"", field.getFieldCode());

        field.setLanguageId("Test3");
        msAssert.areEqual(" GREETINGLINE  \\e Test1 \\f \"Test 2\" \\l Test3", field.getFieldCode());

        msAssert.areEqual("Test1", field.getAlternateText());
        msAssert.areEqual("Test 2", field.getNameFormat());
        msAssert.areEqual("Test3", field.getLanguageId());
    }

    @Test
    public void testHyperlink() throws Exception
    {
        FieldHyperlink field = (FieldHyperlink)createField(FieldType.FIELD_HYPERLINK);

        msAssert.areEqual(FieldType.FIELD_HYPERLINK, field.getType());
        msAssert.areEqual(" HYPERLINK ", field.getFieldCode());

        msAssert.isNull(field.getAddress());
        msAssert.isNull(field.getSubAddress());
        msAssert.isFalse(field.isImageMap());
        msAssert.isFalse(field.getOpenInNewWindow());
        msAssert.isNull(field.getScreenTip());
        msAssert.isNull(field.getTarget());
        msAssert.isNull(field.getDocLocation());
        msAssert.isFalse(field.getNoHistory());

        field.setAddress("Test1");
        msAssert.areEqual(" HYPERLINK  Test1", field.getFieldCode());

        field.setSubAddress("Test2");
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2", field.getFieldCode());

        field.isImageMap(true);
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2 \\m", field.getFieldCode());

        field.setOpenInNewWindow(true);
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2 \\m \\n", field.getFieldCode());

        field.setScreenTip("Test3");
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2 \\m \\n \\o Test3", field.getFieldCode());

        field.setTarget("Test4");
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2 \\m \\n \\o Test3 \\t Test4", field.getFieldCode());

        field.setDocLocation("Test5");
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2 \\m \\n \\o Test3 \\t Test4 \\s Test5", field.getFieldCode());

        field.setNoHistory(true);
        msAssert.areEqual(" HYPERLINK  Test1 \\l Test2 \\m \\n \\o Test3 \\t Test4 \\s Test5 \\h", field.getFieldCode());

        msAssert.areEqual("Test1", field.getAddress());
        msAssert.areEqual("Test2", field.getSubAddress());
        msAssert.isTrue(field.isImageMap());
        msAssert.isTrue(field.getOpenInNewWindow());
        msAssert.areEqual("Test3", field.getScreenTip());
        msAssert.areEqual("Test4", field.getTarget());
        msAssert.areEqual("Test5", field.getDocLocation());
        msAssert.isTrue(field.getNoHistory());
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QField type 'FieldHtmlActiveX' is invalid or not supported.\\E")
    public void testHtmlActiveX() throws Exception
    {
        createField(FieldType.FIELD_HTML_ACTIVE_X);
    }

    @Test
    public void testIf() throws Exception
    {
        FieldIf field = (FieldIf)createField(FieldType.FIELD_IF);

        msAssert.areEqual(FieldType.FIELD_IF, field.getType());
        msAssert.areEqual(" IF ", field.getFieldCode());

        msAssert.isNull(field.getLeftExpression());
        msAssert.isNull(field.getComparisonOperator());
        msAssert.isNull(field.getRightExpression());
        msAssert.isNull(field.getTrueText());
        msAssert.isNull(field.getFalseText());

        field.setLeftExpression("123");
        field.setComparisonOperator(">=");
        field.setRightExpression("a b c");
        field.setTrueText("True");
        field.setFalseText("False");
        msAssert.areEqual(" IF  123 >= \"a b c\" True False", field.getFieldCode());

        msAssert.areEqual("123", field.getLeftExpression());
        msAssert.areEqual(">=", field.getComparisonOperator());
        msAssert.areEqual("\"a b c\"", field.getRightExpression());
        msAssert.areEqual("True", field.getTrueText());
        msAssert.areEqual("False", field.getFalseText());
    }

    @Test
    public void testImport() throws Exception
    {
        FieldImport field = (FieldImport)createField(FieldType.FIELD_IMPORT);

        msAssert.areEqual(FieldType.FIELD_IMPORT, field.getType());
        msAssert.areEqual(" IMPORT ", field.getFieldCode());

        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getGraphicFilter());
        msAssert.isFalse(field.isLinked());

        field.setSourceFullName("Test1");
        msAssert.areEqual(" IMPORT  Test1", field.getFieldCode());

        field.setGraphicFilter("Test2");
        msAssert.areEqual(" IMPORT  Test1 \\c Test2", field.getFieldCode());

        field.isLinked(true);
        msAssert.areEqual(" IMPORT  Test1 \\c Test2 \\d", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSourceFullName());
        msAssert.areEqual("Test2", field.getGraphicFilter());
        msAssert.isTrue(field.isLinked());
    }

    @Test
    public void testIncludePicture() throws Exception
    {
        FieldIncludePicture field = (FieldIncludePicture)createField(FieldType.FIELD_INCLUDE_PICTURE);

        msAssert.areEqual(FieldType.FIELD_INCLUDE_PICTURE, field.getType());
        msAssert.areEqual(" INCLUDEPICTURE ", field.getFieldCode());

        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getGraphicFilter());
        msAssert.isFalse(field.isLinked());
        msAssert.isFalse(field.getResizeHorizontally());
        msAssert.isFalse(field.getResizeVertically());

        field.setSourceFullName("Test1");
        msAssert.areEqual(" INCLUDEPICTURE  Test1", field.getFieldCode());

        field.setGraphicFilter("Test2");
        msAssert.areEqual(" INCLUDEPICTURE  Test1 \\c Test2", field.getFieldCode());

        field.isLinked(true);
        msAssert.areEqual(" INCLUDEPICTURE  Test1 \\c Test2 \\d", field.getFieldCode());

        field.setResizeHorizontally(true);
        msAssert.areEqual(" INCLUDEPICTURE  Test1 \\c Test2 \\d \\x", field.getFieldCode());

        field.setResizeVertically(true);
        msAssert.areEqual(" INCLUDEPICTURE  Test1 \\c Test2 \\d \\x \\y", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSourceFullName());
        msAssert.areEqual("Test2", field.getGraphicFilter());
        msAssert.isTrue(field.isLinked());
        msAssert.isTrue(field.getResizeHorizontally());
        msAssert.isTrue(field.getResizeVertically());
    }

    @Test
    public void testInclude() throws Exception
    {
        FieldInclude field = (FieldInclude)createField(FieldType.FIELD_INCLUDE);

        msAssert.areEqual(FieldType.FIELD_INCLUDE, field.getType());
        msAssert.areEqual(" INCLUDE ", field.getFieldCode());

        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getBookmarkName());
        msAssert.isFalse(field.getLockFields());
        msAssert.isNull(field.getTextConverter());

        field.setSourceFullName("Test1");
        msAssert.areEqual(" INCLUDE  Test1", field.getFieldCode());

        field.setBookmarkName("Test2");
        msAssert.areEqual(" INCLUDE  Test1 Test2", field.getFieldCode());

        field.setLockFields(true);
        msAssert.areEqual(" INCLUDE  Test1 Test2 \\!", field.getFieldCode());

        field.setTextConverter("Test3");
        msAssert.areEqual(" INCLUDE  Test1 Test2 \\! \\c Test3", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSourceFullName());
        msAssert.areEqual("Test2", field.getBookmarkName());
        msAssert.isTrue(field.getLockFields());
        msAssert.areEqual("Test3", field.getTextConverter());
    }

    @Test
    public void testIncludeText() throws Exception
    {
        FieldIncludeText field = (FieldIncludeText)createField(FieldType.FIELD_INCLUDE_TEXT);

        msAssert.areEqual(FieldType.FIELD_INCLUDE_TEXT, field.getType());
        msAssert.areEqual(" INCLUDETEXT ", field.getFieldCode());

        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getBookmarkName());
        msAssert.isFalse(field.getLockFields());
        msAssert.isNull(field.getTextConverter());
        msAssert.isNull(field.getEncoding());
        msAssert.isNull(field.getMimeType());
        msAssert.isNull(field.getNamespaceMappings());
        msAssert.isNull(field.getXslTransformation());
        msAssert.isNull(field.getXPath());

        field.setSourceFullName("Test1");
        msAssert.areEqual(" INCLUDETEXT  Test1", field.getFieldCode());

        field.setBookmarkName("Test2");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2", field.getFieldCode());

        field.setLockFields(true);
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\!", field.getFieldCode());

        field.setTextConverter("Test3");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\! \\c Test3", field.getFieldCode());

        field.setEncoding("Test4");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\! \\c Test3 \\e Test4", field.getFieldCode());

        field.setMimeType("Test5");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\! \\c Test3 \\e Test4 \\m Test5", field.getFieldCode());

        field.setNamespaceMappings("Test6");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\! \\c Test3 \\e Test4 \\m Test5 \\n Test6", field.getFieldCode());

        field.setXslTransformation("Test7");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\! \\c Test3 \\e Test4 \\m Test5 \\n Test6 \\t Test7", field.getFieldCode());

        field.setXPath("Test8");
        msAssert.areEqual(" INCLUDETEXT  Test1 Test2 \\! \\c Test3 \\e Test4 \\m Test5 \\n Test6 \\t Test7 \\x Test8", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSourceFullName());
        msAssert.areEqual("Test2", field.getBookmarkName());
        msAssert.isTrue(field.getLockFields());
        msAssert.areEqual("Test3", field.getTextConverter());
        msAssert.areEqual("Test4", field.getEncoding());
        msAssert.areEqual("Test5", field.getMimeType());
        msAssert.areEqual("Test6", field.getNamespaceMappings());
        msAssert.areEqual("Test7", field.getXslTransformation());
        msAssert.areEqual("Test8", field.getXPath());
    }

    @Test
    public void testIndex() throws Exception
    {
        FieldIndex field = (FieldIndex)createField(FieldType.FIELD_INDEX);

        msAssert.areEqual(FieldType.FIELD_INDEX, field.getType());
        msAssert.areEqual(" INDEX ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isNull(field.getNumberOfColumns());
        msAssert.isFalse(field.hasNumberOfColumnsSwitch());
        msAssert.isNull(field.getSequenceSeparator());
        msAssert.isNull(field.getEntryType());
        msAssert.isNull(field.getPageRangeSeparator());
        msAssert.isNull(field.getHeading());
        msAssert.isNull(field.getCrossReferenceSeparator());
        msAssert.isNull(field.getPageNumberListSeparator());
        msAssert.isNull(field.getLetterRange());
        msAssert.isFalse(field.getRunSubentriesOnSameLine());
        msAssert.isNull(field.getSequenceName());
        msAssert.isFalse(field.getUseYomi());
        msAssert.isNull(field.getLanguageId());

        field.setBookmarkName("Test1");
        msAssert.areEqual(" INDEX  \\b Test1", field.getFieldCode());

        field.setNumberOfColumns("4");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4", field.getFieldCode());

        field.setSequenceSeparator("Test2");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2", field.getFieldCode());

        field.setEntryType("Test3");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3", field.getFieldCode());

        field.setPageRangeSeparator("Test4");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4", field.getFieldCode());

        field.setHeading("Test5");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5", field.getFieldCode());

        field.setCrossReferenceSeparator("Test6");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6", field.getFieldCode());

        field.setPageNumberListSeparator("Test7");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6 \\l Test7", field.getFieldCode());

        field.setLetterRange("Test8");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6 \\l Test7 \\p Test8", field.getFieldCode());

        field.setRunSubentriesOnSameLine(true);
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6 \\l Test7 \\p Test8 \\r", field.getFieldCode());

        field.setSequenceName("Test9");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6 \\l Test7 \\p Test8 \\r \\s Test9", field.getFieldCode());

        field.setUseYomi(true);
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6 \\l Test7 \\p Test8 \\r \\s Test9 \\y", field.getFieldCode());

        field.setLanguageId("Test10");
        msAssert.areEqual(" INDEX  \\b Test1 \\c 4 \\d Test2 \\f Test3 \\g Test4 \\h Test5 \\k Test6 \\l Test7 \\p Test8 \\r \\s Test9 \\y \\z Test10", field.getFieldCode());

        msAssert.areEqual("Test1", field.getBookmarkName());
        msAssert.areEqual("4", field.getNumberOfColumns());
        msAssert.isTrue(field.hasNumberOfColumnsSwitch());
        msAssert.areEqual(4, field.getNumberOfColumnsAsInt32().getValue());
        msAssert.areEqual("Test2", field.getSequenceSeparator());
        msAssert.areEqual("Test3", field.getEntryType());
        msAssert.areEqual("Test4", field.getPageRangeSeparator());
        msAssert.areEqual("Test5", field.getHeading());
        msAssert.areEqual("Test6", field.getCrossReferenceSeparator());
        msAssert.areEqual("Test7", field.getPageNumberListSeparator());
        msAssert.areEqual("Test8", field.getLetterRange());
        msAssert.isTrue(field.getRunSubentriesOnSameLine());
        msAssert.areEqual("Test9", field.getSequenceName());
        msAssert.isTrue(field.getUseYomi());
        msAssert.areEqual("Test10", field.getLanguageId());
    }

    @Test
    public void testInfo() throws Exception
    {
        FieldInfo field = (FieldInfo)createField(FieldType.FIELD_INFO);

        msAssert.areEqual(FieldType.FIELD_INFO, field.getType());
        msAssert.areEqual(" INFO ", field.getFieldCode());

        msAssert.isNull(field.getInfoType());
        msAssert.isNull(field.getNewValue());

        field.setInfoType("Test1");
        msAssert.areEqual(" INFO  Test1", field.getFieldCode());

        field.setNewValue("Test2");
        msAssert.areEqual(" INFO  Test1 Test2", field.getFieldCode());

        msAssert.areEqual("Test1", field.getInfoType());
        msAssert.areEqual("Test2", field.getNewValue());
    }

    @Test
    public void testKeywords() throws Exception
    {
        FieldKeywords field = (FieldKeywords)createField(FieldType.FIELD_KEYWORD);

        msAssert.areEqual(FieldType.FIELD_KEYWORD, field.getType());
        msAssert.areEqual(" KEYWORDS ", field.getFieldCode());

        msAssert.isNull(field.getText());

        field.setText("Test1");
        msAssert.areEqual(" KEYWORDS  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getText());
    }

    @Test
    public void testLastSavedBy() throws Exception
    {
        FieldLastSavedBy field = (FieldLastSavedBy)createField(FieldType.FIELD_LAST_SAVED_BY);

        msAssert.areEqual(FieldType.FIELD_LAST_SAVED_BY, field.getType());
        msAssert.areEqual(" LASTSAVEDBY ", field.getFieldCode());
    }

    @Test
    public void testLink() throws Exception
    {
        FieldLink field = (FieldLink)createField(FieldType.FIELD_LINK);

        msAssert.areEqual(FieldType.FIELD_LINK, field.getType());
        msAssert.areEqual(" LINK ", field.getFieldCode());

        msAssert.isNull(field.getProgId());
        msAssert.isNull(field.getSourceFullName());
        msAssert.isNull(field.getSourceItem());
        msAssert.isFalse(field.getAutoUpdate());
        msAssert.isFalse(field.getInsertAsBitmap());
        msAssert.isFalse(field.isLinked());
        msAssert.isNull(field.getFormatUpdateType());
        msAssert.isFalse(field.getInsertAsHtml());
        msAssert.isFalse(field.getInsertAsPicture());
        msAssert.isFalse(field.getInsertAsRtf());
        msAssert.isFalse(field.getInsertAsText());
        msAssert.isFalse(field.getInsertAsUnicode());

        field.setProgId("Test1");
        msAssert.areEqual(" LINK  Test1", field.getFieldCode());

        field.setSourceFullName("Test2");
        msAssert.areEqual(" LINK  Test1 Test2", field.getFieldCode());

        field.setSourceItem("Test3");
        msAssert.areEqual(" LINK  Test1 Test2 Test3", field.getFieldCode());

        field.setAutoUpdate(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a", field.getFieldCode());

        field.setInsertAsBitmap(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b", field.getFieldCode());

        field.isLinked(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d", field.getFieldCode());

        field.setFormatUpdateType("1");
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d \\f 1", field.getFieldCode());

        field.setInsertAsHtml(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d \\f 1 \\h", field.getFieldCode());

        field.setInsertAsPicture(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d \\f 1 \\h \\p", field.getFieldCode());

        field.setInsertAsRtf(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d \\f 1 \\h \\p \\r", field.getFieldCode());

        field.setInsertAsText(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d \\f 1 \\h \\p \\r \\t", field.getFieldCode());

        field.setInsertAsUnicode(true);
        msAssert.areEqual(" LINK  Test1 Test2 Test3 \\a \\b \\d \\f 1 \\h \\p \\r \\t \\u", field.getFieldCode());

        msAssert.areEqual("Test1", field.getProgId());
        msAssert.areEqual("Test2", field.getSourceFullName());
        msAssert.areEqual("Test3", field.getSourceItem());
        msAssert.isTrue(field.getAutoUpdate());
        msAssert.isTrue(field.getInsertAsBitmap());
        msAssert.isTrue(field.isLinked());
        msAssert.areEqual("1", field.getFormatUpdateType());
        msAssert.isTrue(field.getInsertAsHtml());
        msAssert.isTrue(field.getInsertAsPicture());
        msAssert.isTrue(field.getInsertAsRtf());
        msAssert.isTrue(field.getInsertAsText());
        msAssert.isTrue(field.getInsertAsUnicode());
    }

    @Test
    public void testListNum() throws Exception
    {
        FieldListNum field = (FieldListNum)createField(FieldType.FIELD_LIST_NUM);

        msAssert.areEqual(FieldType.FIELD_LIST_NUM, field.getType());
        msAssert.areEqual(" LISTNUM ", field.getFieldCode());

        msAssert.isNull(field.getListName());
        msAssert.isNull(field.getListLevel());
        msAssert.areEqual(-1, field.getListLevelAsInt32());
        msAssert.isNull(field.getListLevel());
        msAssert.areEqual(-1, field.getStartingNumberAsInt32());
        msAssert.isNull(field.getStartingNumber());

        field.setListName("Test1");
        msAssert.areEqual(" LISTNUM  Test1", field.getFieldCode());

        field.setListLevel("3");
        msAssert.areEqual(" LISTNUM  Test1 \\l 3", field.getFieldCode());

        field.setStartingNumber("1");
        msAssert.areEqual(" LISTNUM  Test1 \\l 3 \\s 1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getListName());
        msAssert.areEqual("3", field.getListLevel());
        msAssert.areEqual(3, field.getListLevelAsInt32());
        msAssert.areEqual("1", field.getStartingNumber());
        msAssert.areEqual(1, field.getStartingNumberAsInt32());
    }

    @Test
    public void testMacroButton() throws Exception
    {
        FieldMacroButton field = (FieldMacroButton)createField(FieldType.FIELD_MACRO_BUTTON);

        msAssert.areEqual(FieldType.FIELD_MACRO_BUTTON, field.getType());
        msAssert.areEqual(" MACROBUTTON ", field.getFieldCode());

        msAssert.isNull(field.getMacroName());
        msAssert.isNull(field.getDisplayText());

        field.setMacroName("Test1");
        msAssert.areEqual(" MACROBUTTON  Test1", field.getFieldCode());

        field.setDisplayText("Test 2");
        msAssert.areEqual(" MACROBUTTON  Test1 Test 2", field.getFieldCode());

        msAssert.areEqual("Test1", field.getMacroName());
        msAssert.areEqual("Test 2", field.getDisplayText());
    }

    @Test
    public void testMergeField() throws Exception
    {
        FieldMergeField field = (FieldMergeField)createField(FieldType.FIELD_MERGE_FIELD);

        msAssert.areEqual(FieldType.FIELD_MERGE_FIELD, field.getType());
        msAssert.areEqual(" MERGEFIELD ", field.getFieldCode());

        msAssert.isNull(field.getFieldName());
        msAssert.isNull(field.getTextBefore());
        msAssert.isNull(field.getTextAfter());
        msAssert.isFalse(field.isMapped());
        msAssert.isFalse(field.isVerticalFormatting());

        field.setFieldName("Test1");
        msAssert.areEqual(" MERGEFIELD  Test1", field.getFieldCode());

        field.setTextBefore("Test2");
        msAssert.areEqual(" MERGEFIELD  Test1 \\b Test2", field.getFieldCode());

        field.setTextAfter("Test3");
        msAssert.areEqual(" MERGEFIELD  Test1 \\b Test2 \\f Test3", field.getFieldCode());

        field.isMapped(true);
        msAssert.areEqual(" MERGEFIELD  Test1 \\b Test2 \\f Test3 \\m", field.getFieldCode());

        field.isVerticalFormatting(true);
        msAssert.areEqual(" MERGEFIELD  Test1 \\b Test2 \\f Test3 \\m \\v", field.getFieldCode());

        msAssert.areEqual("Test1", field.getFieldName());
        msAssert.areEqual("Test2", field.getTextBefore());
        msAssert.areEqual("Test3", field.getTextAfter());
        msAssert.isTrue(field.isMapped());
        msAssert.isTrue(field.isVerticalFormatting());
    }

    @Test
    public void testMergeRec() throws Exception
    {
        FieldMergeRec field = (FieldMergeRec)createField(FieldType.FIELD_MERGE_REC);

        msAssert.areEqual(FieldType.FIELD_MERGE_REC, field.getType());
        msAssert.areEqual(" MERGEREC ", field.getFieldCode());
    }

    @Test
    public void testMergeSeq() throws Exception
    {
        FieldMergeSeq field = (FieldMergeSeq)createField(FieldType.FIELD_MERGE_SEQ);

        msAssert.areEqual(FieldType.FIELD_MERGE_SEQ, field.getType());
        msAssert.areEqual(" MERGESEQ ", field.getFieldCode());
    }

    @Test
    public void testNext() throws Exception
    {
        FieldNext field = (FieldNext)createField(FieldType.FIELD_NEXT);

        msAssert.areEqual(FieldType.FIELD_NEXT, field.getType());
        msAssert.areEqual(" NEXT ", field.getFieldCode());
    }

    @Test
    public void testNextIf() throws Exception
    {
        FieldNextIf field = (FieldNextIf)createField(FieldType.FIELD_NEXT_IF);

        msAssert.areEqual(FieldType.FIELD_NEXT_IF, field.getType());
        msAssert.areEqual(" NEXTIF ", field.getFieldCode());

        msAssert.isNull(field.getLeftExpression());
        msAssert.isNull(field.getComparisonOperator());
        msAssert.isNull(field.getRightExpression());

        field.setLeftExpression("123");
        field.setComparisonOperator(">=");
        field.setRightExpression("a b c");
        msAssert.areEqual(" NEXTIF  123 >= \"a b c\"", field.getFieldCode());

        msAssert.areEqual("123", field.getLeftExpression());
        msAssert.areEqual(">=", field.getComparisonOperator());
        msAssert.areEqual("\"a b c\"", field.getRightExpression());
    }

    @Test
    public void testFieldNoneType() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field =  builder.insertField(FieldType.FIELD_NONE, false);
        msAssert.isInstanceOf(FieldUnknown.class, field);
        ArrayList<Node> range = msArrayList.ctor(field.getFieldRange());
        msAssert.areEqual(NodeType.FIELD_START, range.get(0).getNodeType());
        msAssert.areEqual(NodeType.RUN, range.get(1).getNodeType());
        msAssert.areEqual("  ", range.get(1).getText());
        msAssert.areEqual(NodeType.FIELD_END, range.get(2).getNodeType());
    }

    @Test
    public void testFieldNoneCode() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field =  builder.insertField("");
        msAssert.isInstanceOf(FieldUnknown.class, field);
        ArrayList<Node> range = msArrayList.ctor(field.getFieldRange());
        msAssert.areEqual(NodeType.FIELD_START, range.get(0).getNodeType());
        msAssert.areEqual(NodeType.FIELD_END, range.get(1).getNodeType());
    }

    @Test
    public void testNoteRef() throws Exception
    {
        FieldNoteRef field = (FieldNoteRef)createField(FieldType.FIELD_NOTE_REF);

        msAssert.areEqual(FieldType.FIELD_NOTE_REF, field.getType());
        msAssert.areEqual(" NOTEREF ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isFalse(field.getInsertReferenceMark());
        msAssert.isFalse(field.getInsertHyperlink());
        msAssert.isFalse(field.getInsertRelativePosition());

        field.setBookmarkName("Test1");
        msAssert.areEqual(" NOTEREF  Test1", field.getFieldCode());

        field.setInsertReferenceMark(true);
        msAssert.areEqual(" NOTEREF  Test1 \\f", field.getFieldCode());

        field.setInsertHyperlink(true);
        msAssert.areEqual(" NOTEREF  Test1 \\f \\h", field.getFieldCode());

        field.setInsertRelativePosition(true);
        msAssert.areEqual(" NOTEREF  Test1 \\f \\h \\p", field.getFieldCode());

        msAssert.areEqual("Test1", field.getBookmarkName());
        msAssert.isTrue(field.getInsertReferenceMark());
        msAssert.isTrue(field.getInsertHyperlink());
        msAssert.isTrue(field.getInsertRelativePosition());
    }

    @Test
    public void testNumChars() throws Exception
    {
        FieldNumChars field = (FieldNumChars)createField(FieldType.FIELD_NUM_CHARS);

        msAssert.areEqual(FieldType.FIELD_NUM_CHARS, field.getType());
        msAssert.areEqual(" NUMCHARS ", field.getFieldCode());
    }

    @Test
    public void testNumPages() throws Exception
    {
        FieldNumPages field = (FieldNumPages)createField(FieldType.FIELD_NUM_PAGES);

        msAssert.areEqual(FieldType.FIELD_NUM_PAGES, field.getType());
        msAssert.areEqual(" NUMPAGES ", field.getFieldCode());
    }

    @Test
    public void testNumWords() throws Exception
    {
        FieldNumWords field = (FieldNumWords)createField(FieldType.FIELD_NUM_WORDS);

        msAssert.areEqual(FieldType.FIELD_NUM_WORDS, field.getType());
        msAssert.areEqual(" NUMWORDS ", field.getFieldCode());
    }

    @Test
    public void testOcx() throws Exception
    {
        FieldOcx field = (FieldOcx)createField(FieldType.FIELD_OCX);

        msAssert.areEqual(FieldType.FIELD_OCX, field.getType());
        msAssert.areEqual(" OCX ", field.getFieldCode());
    }

    @Test
    public void testPage() throws Exception
    {
        FieldPage field = (FieldPage)createField(FieldType.FIELD_PAGE);

        msAssert.areEqual(FieldType.FIELD_PAGE, field.getType());
        msAssert.areEqual(" PAGE ", field.getFieldCode());
    }

    @Test
    public void testPageRef() throws Exception
    {
        FieldPageRef field = (FieldPageRef)createField(FieldType.FIELD_PAGE_REF);

        msAssert.areEqual(FieldType.FIELD_PAGE_REF, field.getType());
        msAssert.areEqual(" PAGEREF ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isFalse(field.getInsertHyperlink());
        msAssert.isFalse(field.getInsertRelativePosition());

        field.setBookmarkName("Test1");
        msAssert.areEqual(" PAGEREF  Test1", field.getFieldCode());

        field.setInsertHyperlink(true);
        msAssert.areEqual(" PAGEREF  Test1 \\h", field.getFieldCode());

        field.setInsertRelativePosition(true);
        msAssert.areEqual(" PAGEREF  Test1 \\h \\p", field.getFieldCode());

        msAssert.areEqual("Test1", field.getBookmarkName());
        msAssert.isTrue(field.getInsertHyperlink());
        msAssert.isTrue(field.getInsertRelativePosition());
    }

    @Test
    public void testPrint() throws Exception
    {
        FieldPrint field = (FieldPrint)createField(FieldType.FIELD_PRINT);

        msAssert.areEqual(FieldType.FIELD_PRINT, field.getType());
        msAssert.areEqual(" PRINT ", field.getFieldCode());

        msAssert.isNull(field.getPrinterInstructions());
        msAssert.isNull(field.getPostScriptGroup());

        field.setPrinterInstructions("Test1");
        msAssert.areEqual(" PRINT  Test1", field.getFieldCode());

        field.setPostScriptGroup("Test2");
        msAssert.areEqual(" PRINT  Test1 \\p Test2", field.getFieldCode());

        msAssert.areEqual("Test1", field.getPrinterInstructions());
        msAssert.areEqual("Test2", field.getPostScriptGroup());
    }

    @Test
    public void testPrintDate() throws Exception
    {
        FieldPrintDate field = (FieldPrintDate)createField(FieldType.FIELD_PRINT_DATE);

        msAssert.areEqual(FieldType.FIELD_PRINT_DATE, field.getType());
        msAssert.areEqual(" PRINTDATE ", field.getFieldCode());

        msAssert.isFalse(field.getUseLunarCalendar());
        msAssert.isFalse(field.getUseSakaEraCalendar());
        msAssert.isFalse(field.getUseUmAlQuraCalendar());

        field.setUseLunarCalendar(true);
        msAssert.areEqual(" PRINTDATE  \\h", field.getFieldCode());

        field.setUseSakaEraCalendar(true);
        msAssert.areEqual(" PRINTDATE  \\h \\s", field.getFieldCode());

        field.setUseUmAlQuraCalendar(true);
        msAssert.areEqual(" PRINTDATE  \\h \\s \\u", field.getFieldCode());

        msAssert.isTrue(field.getUseLunarCalendar());
        msAssert.isTrue(field.getUseSakaEraCalendar());
        msAssert.isTrue(field.getUseUmAlQuraCalendar());
    }

    @Test
    public void testPrivate() throws Exception
    {
        FieldPrivate field = (FieldPrivate)createField(FieldType.FIELD_PRIVATE);

        msAssert.areEqual(FieldType.FIELD_PRIVATE, field.getType());
        msAssert.areEqual(" PRIVATE ", field.getFieldCode());
    }

    @Test
    public void testQuote() throws Exception
    {
        FieldQuote field = (FieldQuote)createField(FieldType.FIELD_QUOTE);

        msAssert.areEqual(FieldType.FIELD_QUOTE, field.getType());
        msAssert.areEqual(" QUOTE ", field.getFieldCode());

        msAssert.isNull(field.getText());

        field.setText("Test 1");
        msAssert.areEqual(" QUOTE  \"Test 1\"", field.getFieldCode());

        msAssert.areEqual("Test 1", field.getText());
    }

    @Test
    public void testRD() throws Exception
    {
        FieldRD field = (FieldRD)createField(FieldType.FIELD_REF_DOC);

        msAssert.areEqual(FieldType.FIELD_REF_DOC, field.getType());
        msAssert.areEqual(" RD ", field.getFieldCode());

        msAssert.isNull(field.getFileName());
        msAssert.isFalse(field.isPathRelative());

        field.setFileName("Test1");
        msAssert.areEqual(" RD  Test1", field.getFieldCode());

        field.isPathRelative(true);
        msAssert.areEqual(" RD  Test1 \\f", field.getFieldCode());

        msAssert.areEqual("Test1", field.getFileName());
        msAssert.isTrue(field.isPathRelative());
    }

    @Test
    public void testRef() throws Exception
    {
        FieldRef field = (FieldRef)createField(FieldType.FIELD_REF);

        msAssert.areEqual(FieldType.FIELD_REF, field.getType());
        msAssert.areEqual(" REF ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isNull(field.getNumberSeparator());
        msAssert.isFalse(field.getInsertHyperlink());
        msAssert.isFalse(field.getInsertParagraphNumber());
        msAssert.isFalse(field.getInsertRelativePosition());
        msAssert.isFalse(field.getInsertParagraphNumberInRelativeContext());
        msAssert.isFalse(field.getSuppressNonDelimiters());
        msAssert.isFalse(field.getInsertParagraphNumberInFullContext());

        field.setBookmarkName("Test1");
        msAssert.areEqual(" REF  Test1", field.getFieldCode());

        field.setNumberSeparator("Test2");
        msAssert.areEqual(" REF  Test1 \\d Test2", field.getFieldCode());

        field.setInsertHyperlink(true);
        msAssert.areEqual(" REF  Test1 \\d Test2 \\h", field.getFieldCode());

        field.setInsertParagraphNumber(true);
        msAssert.areEqual(" REF  Test1 \\d Test2 \\h \\n", field.getFieldCode());

        field.setInsertRelativePosition(true);
        msAssert.areEqual(" REF  Test1 \\d Test2 \\h \\n \\p", field.getFieldCode());

        field.setInsertParagraphNumberInRelativeContext(true);
        msAssert.areEqual(" REF  Test1 \\d Test2 \\h \\n \\p \\r", field.getFieldCode());

        field.setSuppressNonDelimiters(true);
        msAssert.areEqual(" REF  Test1 \\d Test2 \\h \\n \\p \\r \\t", field.getFieldCode());

        field.setInsertParagraphNumberInFullContext(true);
        msAssert.areEqual(" REF  Test1 \\d Test2 \\h \\n \\p \\r \\t \\w", field.getFieldCode());

        msAssert.areEqual("Test1", field.getBookmarkName());
        msAssert.areEqual("Test2", field.getNumberSeparator());
        msAssert.isTrue(field.getInsertHyperlink());
        msAssert.isTrue(field.getInsertParagraphNumber());
        msAssert.isTrue(field.getInsertRelativePosition());
        msAssert.isTrue(field.getInsertParagraphNumberInRelativeContext());
        msAssert.isTrue(field.getSuppressNonDelimiters());
        msAssert.isTrue(field.getInsertParagraphNumberInFullContext());
    }

    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QField type 'FieldRefNoKeyword' is invalid or not supported.\\E")
    public void testRefNoKeyword() throws Exception
    {
        createField(FieldType.FIELD_REF_NO_KEYWORD);
    }

    @Test
    public void testRevNum() throws Exception
    {
        FieldRevNum field = (FieldRevNum)createField(FieldType.FIELD_REVISION_NUM);

        msAssert.areEqual(FieldType.FIELD_REVISION_NUM, field.getType());
        msAssert.areEqual(" REVNUM ", field.getFieldCode());
    }

    @Test
    public void testSaveDate() throws Exception
    {
        FieldSaveDate field = (FieldSaveDate)createField(FieldType.FIELD_SAVE_DATE);

        msAssert.areEqual(FieldType.FIELD_SAVE_DATE, field.getType());
        msAssert.areEqual(" SAVEDATE ", field.getFieldCode());

        msAssert.isFalse(field.getUseLunarCalendar());
        msAssert.isFalse(field.getUseSakaEraCalendar());
        msAssert.isFalse(field.getUseUmAlQuraCalendar());

        field.setUseLunarCalendar(true);
        msAssert.areEqual(" SAVEDATE  \\h", field.getFieldCode());

        field.setUseSakaEraCalendar(true);
        msAssert.areEqual(" SAVEDATE  \\h \\s", field.getFieldCode());

        field.setUseUmAlQuraCalendar(true);
        msAssert.areEqual(" SAVEDATE  \\h \\s \\u", field.getFieldCode());

        msAssert.isTrue(field.getUseLunarCalendar());
        msAssert.isTrue(field.getUseSakaEraCalendar());
        msAssert.isTrue(field.getUseUmAlQuraCalendar());
    }

    @Test
    public void testSection() throws Exception
    {
        FieldSection field = (FieldSection)createField(FieldType.FIELD_SECTION);

        msAssert.areEqual(FieldType.FIELD_SECTION, field.getType());
        msAssert.areEqual(" SECTION ", field.getFieldCode());
    }

    @Test
    public void testSectionPages() throws Exception
    {
        FieldSectionPages field = (FieldSectionPages)createField(FieldType.FIELD_SECTION_PAGES);

        msAssert.areEqual(FieldType.FIELD_SECTION_PAGES, field.getType());
        msAssert.areEqual(" SECTIONPAGES ", field.getFieldCode());
    }

    @Test
    public void testSeq() throws Exception
    {
        FieldSeq field = (FieldSeq)createField(FieldType.FIELD_SEQUENCE);

        msAssert.areEqual(FieldType.FIELD_SEQUENCE, field.getType());
        msAssert.areEqual(" SEQ ", field.getFieldCode());

        msAssert.isNull(field.getSequenceIdentifier());
        msAssert.isNull(field.getBookmarkName());
        msAssert.isFalse(field.getInsertClosestPrecedingNumber());
        msAssert.isFalse(field.getHideFieldResult());
        msAssert.isFalse(field.getInsertNextNumber());
        msAssert.isNull(field.getResetNumber());
        msAssert.isFalse(field.hasResetNumberSwitch());
        msAssert.isNull(field.getResetHeadingLevel());
        msAssert.isFalse(field.hasResetHeadingLevelSwitch());

        field.setSequenceIdentifier("Test1");
        msAssert.areEqual(" SEQ  Test1", field.getFieldCode());

        field.setBookmarkName("Test2");
        msAssert.areEqual(" SEQ  Test1 Test2", field.getFieldCode());

        field.setInsertClosestPrecedingNumber(true);
        msAssert.areEqual(" SEQ  Test1 Test2 \\c", field.getFieldCode());

        field.setHideFieldResult(true);
        msAssert.areEqual(" SEQ  Test1 Test2 \\c \\h", field.getFieldCode());

        field.setInsertNextNumber(true);
        msAssert.areEqual(" SEQ  Test1 Test2 \\c \\h \\n", field.getFieldCode());

        field.setResetNumber("10");
        msAssert.areEqual(" SEQ  Test1 Test2 \\c \\h \\n \\r 10", field.getFieldCode());

        field.setResetHeadingLevel("20");
        msAssert.areEqual(" SEQ  Test1 Test2 \\c \\h \\n \\r 10 \\s 20", field.getFieldCode());

        msAssert.areEqual("Test1", field.getSequenceIdentifier());
        msAssert.areEqual("Test2", field.getBookmarkName());
        msAssert.isTrue(field.getInsertClosestPrecedingNumber());
        msAssert.isTrue(field.getHideFieldResult());
        msAssert.isTrue(field.getInsertNextNumber());
        msAssert.areEqual("10", field.getResetNumber());
        msAssert.isTrue(field.hasResetNumberSwitch());
        msAssert.areEqual(10, field.getResetNumberAsInt32().getValue());
        msAssert.areEqual("20", field.getResetHeadingLevel());
        msAssert.isTrue(field.hasResetHeadingLevelSwitch());
        msAssert.areEqual(20, field.getResetHeadingLevelAsInt32().getValue());
    }

    @Test
    public void testSet() throws Exception
    {
        FieldSet field = (FieldSet)createField(FieldType.FIELD_SET);

        msAssert.areEqual(FieldType.FIELD_SET, field.getType());
        msAssert.areEqual(" SET ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isNull(field.getBookmarkText());

        field.setBookmarkName("Test1");
        msAssert.areEqual(" SET  Test1", field.getFieldCode());

        field.setBookmarkText("Test2");
        msAssert.areEqual(" SET  Test1 Test2", field.getFieldCode());

        msAssert.areEqual("Test1", field.getBookmarkName());
        msAssert.areEqual("Test2", field.getBookmarkText());
    }

    @Test
    public void testShape() throws Exception
    {
        FieldShape field = (FieldShape)createField(FieldType.FIELD_SHAPE);

        msAssert.areEqual(FieldType.FIELD_SHAPE, field.getType());
        msAssert.areEqual(" SHAPE ", field.getFieldCode());

        msAssert.isNull(field.getText());

        field.setText("Test 1");
        msAssert.areEqual(" SHAPE  \"Test 1\"", field.getFieldCode());

        msAssert.areEqual("Test 1", field.getText());
    }

    @Test
    public void testSkipIf() throws Exception
    {
        FieldSkipIf field = (FieldSkipIf)createField(FieldType.FIELD_SKIP_IF);

        msAssert.areEqual(FieldType.FIELD_SKIP_IF, field.getType());
        msAssert.areEqual(" SKIPIF ", field.getFieldCode());

        msAssert.isNull(field.getLeftExpression());
        msAssert.isNull(field.getComparisonOperator());
        msAssert.isNull(field.getRightExpression());

        field.setLeftExpression("123");
        field.setComparisonOperator(">=");
        field.setRightExpression("a b c");
        msAssert.areEqual(" SKIPIF  123 >= \"a b c\"", field.getFieldCode());

        msAssert.areEqual("123", field.getLeftExpression());
        msAssert.areEqual(">=", field.getComparisonOperator());
        msAssert.areEqual("\"a b c\"", field.getRightExpression());
    }

    @Test
    public void testStyleRef() throws Exception
    {
        FieldStyleRef field = (FieldStyleRef)createField(FieldType.FIELD_STYLE_REF);

        msAssert.areEqual(FieldType.FIELD_STYLE_REF, field.getType());
        msAssert.areEqual(" STYLEREF ", field.getFieldCode());

        msAssert.isNull(field.getStyleName());
        msAssert.isFalse(field.getSearchFromBottom());
        msAssert.isFalse(field.getInsertParagraphNumber());
        msAssert.isFalse(field.getInsertRelativePosition());
        msAssert.isFalse(field.getInsertParagraphNumberInRelativeContext());
        msAssert.isFalse(field.getSuppressNonDelimiters());
        msAssert.isFalse(field.getInsertParagraphNumberInFullContext());

        field.setStyleName("Test1");
        msAssert.areEqual(" STYLEREF  Test1", field.getFieldCode());

        field.setSearchFromBottom(true);
        msAssert.areEqual(" STYLEREF  Test1 \\l", field.getFieldCode());

        field.setInsertParagraphNumber(true);
        msAssert.areEqual(" STYLEREF  Test1 \\l \\n", field.getFieldCode());

        field.setInsertRelativePosition(true);
        msAssert.areEqual(" STYLEREF  Test1 \\l \\n \\p", field.getFieldCode());

        field.setInsertParagraphNumberInRelativeContext(true);
        msAssert.areEqual(" STYLEREF  Test1 \\l \\n \\p \\r", field.getFieldCode());

        field.setSuppressNonDelimiters(true);
        msAssert.areEqual(" STYLEREF  Test1 \\l \\n \\p \\r \\t", field.getFieldCode());

        field.setInsertParagraphNumberInFullContext(true);
        msAssert.areEqual(" STYLEREF  Test1 \\l \\n \\p \\r \\t \\w", field.getFieldCode());

        msAssert.areEqual("Test1", field.getStyleName());
        msAssert.isTrue(field.getSearchFromBottom());
        msAssert.isTrue(field.getInsertParagraphNumber());
        msAssert.isTrue(field.getInsertRelativePosition());
        msAssert.isTrue(field.getInsertParagraphNumberInRelativeContext());
        msAssert.isTrue(field.getSuppressNonDelimiters());
        msAssert.isTrue(field.getInsertParagraphNumberInFullContext());
    }

    @Test
    public void testSubject() throws Exception
    {
        FieldSubject field = (FieldSubject)createField(FieldType.FIELD_SUBJECT);

        msAssert.areEqual(FieldType.FIELD_SUBJECT, field.getType());
        msAssert.areEqual(" SUBJECT ", field.getFieldCode());

        msAssert.isNull(field.getText());

        field.setText("Test1");
        msAssert.areEqual(" SUBJECT  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getText());
    }

    @Test
    public void testSymbol() throws Exception
    {
        FieldSymbol field = (FieldSymbol)createField(FieldType.FIELD_SYMBOL);

        msAssert.areEqual(FieldType.FIELD_SYMBOL, field.getType());
        msAssert.areEqual(" SYMBOL ", field.getFieldCode());

        msAssert.isNull(field.getCharacterCode());
        msAssert.isFalse(field.isAnsi());
        msAssert.isNull(field.getFontName());
        msAssert.isFalse(field.getDontAffectsLineSpacing());
        msAssert.isFalse(field.isShiftJis());
        msAssert.isNull(field.getFontSize());
        msAssert.isFalse(field.isUnicode());

        field.setCharacterCode("Test1");
        msAssert.areEqual(" SYMBOL  Test1", field.getFieldCode());

        field.isAnsi(true);
        msAssert.areEqual(" SYMBOL  Test1 \\a", field.getFieldCode());

        field.setFontName("Test2");
        msAssert.areEqual(" SYMBOL  Test1 \\a \\f Test2", field.getFieldCode());

        field.setDontAffectsLineSpacing(true);
        msAssert.areEqual(" SYMBOL  Test1 \\a \\f Test2 \\h", field.getFieldCode());

        field.isShiftJis(true);
        msAssert.areEqual(" SYMBOL  Test1 \\a \\f Test2 \\h \\j", field.getFieldCode());

        field.setFontSize("10.5");
        msAssert.areEqual(" SYMBOL  Test1 \\a \\f Test2 \\h \\j \\s 10.5", field.getFieldCode());

        field.isUnicode(true);
        msAssert.areEqual(" SYMBOL  Test1 \\a \\f Test2 \\h \\j \\s 10.5 \\u", field.getFieldCode());

        msAssert.areEqual("Test1", field.getCharacterCode());
        msAssert.isTrue(field.isAnsi());
        msAssert.areEqual("Test2", field.getFontName());
        msAssert.isTrue(field.getDontAffectsLineSpacing());
        msAssert.isTrue(field.isShiftJis());
        msAssert.areEqual("10.5", field.getFontSize());
        msAssert.isTrue(field.isUnicode());
    }

    @Test
    public void testTA() throws Exception
    {
        FieldTA field = (FieldTA)createField(FieldType.FIELD_TOA_ENTRY);

        msAssert.areEqual(FieldType.FIELD_TOA_ENTRY, field.getType());
        msAssert.areEqual(" TA ", field.getFieldCode());

        msAssert.isFalse(field.isBold());
        msAssert.isNull(field.getEntryCategory());
        msAssert.isFalse(field.isItalic());
        msAssert.isNull(field.getLongCitation());
        msAssert.isNull(field.getPageRangeBookmarkName());
        msAssert.isNull(field.getShortCitation());

        field.isBold(true);
        msAssert.areEqual(" TA  \\b", field.getFieldCode());

        field.setEntryCategory("7");
        msAssert.areEqual(" TA  \\b \\c 7", field.getFieldCode());

        field.isItalic(true);
        msAssert.areEqual(" TA  \\b \\c 7 \\i", field.getFieldCode());

        field.setLongCitation("Test1");
        msAssert.areEqual(" TA  \\b \\c 7 \\i \\l Test1", field.getFieldCode());

        field.setPageRangeBookmarkName("Test2");
        msAssert.areEqual(" TA  \\b \\c 7 \\i \\l Test1 \\r Test2", field.getFieldCode());

        field.setShortCitation("Test3");
        msAssert.areEqual(" TA  \\b \\c 7 \\i \\l Test1 \\r Test2 \\s Test3", field.getFieldCode());

        msAssert.isTrue(field.isBold());
        msAssert.areEqual("7", field.getEntryCategory());
        msAssert.isTrue(field.isItalic());
        msAssert.areEqual("Test1", field.getLongCitation());
        msAssert.areEqual("Test2", field.getPageRangeBookmarkName());
        msAssert.areEqual("Test3", field.getShortCitation());
    }

    @Test
    public void testTC() throws Exception
    {
        FieldTC field = (FieldTC)createField(FieldType.FIELD_TOC_ENTRY);

        msAssert.areEqual(FieldType.FIELD_TOC_ENTRY, field.getType());
        msAssert.areEqual(" TC ", field.getFieldCode());

        msAssert.isNull(field.getText());
        msAssert.isNull(field.getTypeIdentifier());
        msAssert.isNull(field.getEntryLevel());
        msAssert.isFalse(field.hasEntryLevelSwitch());
        msAssert.isFalse(field.getOmitPageNumber());

        field.setText("Test1");
        msAssert.areEqual(" TC  Test1", field.getFieldCode());

        field.setTypeIdentifier("Test2");
        msAssert.areEqual(" TC  Test1 \\f Test2", field.getFieldCode());

        field.setEntryLevel("3");
        msAssert.areEqual(" TC  Test1 \\f Test2 \\l 3", field.getFieldCode());

        field.setOmitPageNumber(true);
        msAssert.areEqual(" TC  Test1 \\f Test2 \\l 3 \\n", field.getFieldCode());

        msAssert.areEqual("Test1", field.getText());
        msAssert.areEqual("Test2", field.getTypeIdentifier());
        msAssert.areEqual("3", field.getEntryLevel());
        msAssert.isTrue(field.hasEntryLevelSwitch());
        msAssert.areEqual(3, field.getEntryLevelAsInt32().getValue());
        msAssert.isTrue(field.getOmitPageNumber());
    }

    @Test
    public void testTemplate() throws Exception
    {
        FieldTemplate field = (FieldTemplate)createField(FieldType.FIELD_TEMPLATE);

        msAssert.areEqual(FieldType.FIELD_TEMPLATE, field.getType());
        msAssert.areEqual(" TEMPLATE ", field.getFieldCode());

        msAssert.isFalse(field.getIncludeFullPath());

        field.setIncludeFullPath(true);
        msAssert.areEqual(" TEMPLATE  \\p", field.getFieldCode());

        msAssert.isTrue(field.getIncludeFullPath());
    }

    @Test
    public void testTime() throws Exception
    {
        FieldTime field = (FieldTime)createField(FieldType.FIELD_TIME);

        msAssert.areEqual(FieldType.FIELD_TIME, field.getType());
        msAssert.areEqual(" TIME ", field.getFieldCode());
    }

    @Test
    public void testTitle() throws Exception
    {
        FieldTitle field = (FieldTitle)createField(FieldType.FIELD_TITLE);

        msAssert.areEqual(FieldType.FIELD_TITLE, field.getType());
        msAssert.areEqual(" TITLE ", field.getFieldCode());

        msAssert.isNull(field.getText());

        field.setText("Test1");
        msAssert.areEqual(" TITLE  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getText());
    }

    @Test
    public void testToa() throws Exception
    {
        FieldToa field = (FieldToa)createField(FieldType.FIELD_TOA);

        msAssert.areEqual(FieldType.FIELD_TOA, field.getType());
        msAssert.areEqual(" TOA ", field.getFieldCode());

        msAssert.isNull(field.getBookmarkName());
        msAssert.isNull(field.getEntryCategory());
        msAssert.isNull(field.getSequenceSeparator());
        msAssert.isNull(field.getEntrySeparator());
        msAssert.isFalse(field.getRemoveEntryFormatting());
        msAssert.isNull(field.getPageRangeSeparator());
        msAssert.isFalse(field.getUseHeading());
        msAssert.isNull(field.getPageNumberListSeparator());
        msAssert.isFalse(field.getUsePassim());
        msAssert.isNull(field.getSequenceName());

        field.setBookmarkName("Test1");
        msAssert.areEqual(" TOA  \\b Test1", field.getFieldCode());

        field.setEntryCategory("7");
        msAssert.areEqual(" TOA  \\b Test1 \\c 7", field.getFieldCode());

        field.setSequenceSeparator("Test2");
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2", field.getFieldCode());

        field.setEntrySeparator("Test3");
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3", field.getFieldCode());

        field.setRemoveEntryFormatting(true);
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3 \\f", field.getFieldCode());

        field.setPageRangeSeparator("Test4");
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3 \\f \\g Test4", field.getFieldCode());

        field.setUseHeading(true);
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3 \\f \\g Test4 \\h", field.getFieldCode());

        field.setPageNumberListSeparator("Test6");
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3 \\f \\g Test4 \\h \\l Test6", field.getFieldCode());

        field.setUsePassim(true);
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3 \\f \\g Test4 \\h \\l Test6 \\p", field.getFieldCode());

        field.setSequenceName("Test7");
        msAssert.areEqual(" TOA  \\b Test1 \\c 7 \\d Test2 \\e Test3 \\f \\g Test4 \\h \\l Test6 \\p \\s Test7", field.getFieldCode());

        msAssert.areEqual("Test1", field.getBookmarkName());
        msAssert.areEqual("7", field.getEntryCategory());
        msAssert.areEqual("Test2", field.getSequenceSeparator());
        msAssert.areEqual("Test3", field.getEntrySeparator());
        msAssert.isTrue(field.getRemoveEntryFormatting());
        msAssert.areEqual("Test4", field.getPageRangeSeparator());
        msAssert.isTrue(field.getUseHeading());
        msAssert.areEqual("Test6", field.getPageNumberListSeparator());
        msAssert.isTrue(field.getUsePassim());
        msAssert.areEqual("Test7", field.getSequenceName());
    }

    @Test
    public void testToc() throws Exception
    {
        FieldToc field = (FieldToc)createField(FieldType.FIELD_TOC);

        msAssert.areEqual(FieldType.FIELD_TOC, field.getType());
        msAssert.areEqual(" TOC ", field.getFieldCode());

        msAssert.isNull(field.getCaptionlessTableOfFiguresLabel());
        msAssert.isNull(field.getBookmarkName());
        msAssert.isNull(field.getTableOfFiguresLabel());
        msAssert.isNull(field.getSequenceSeparator());
        msAssert.isNull(field.getEntryIdentifier());
        msAssert.isFalse(field.getInsertHyperlinks());
        msAssert.isNull(field.getEntryLevelRange());
        msAssert.isNull(field.getPageNumberOmittingLevelRange());
        msAssert.isNull(field.getHeadingLevelRange());
        msAssert.isNull(field.getEntrySeparator());
        msAssert.isNull(field.getPrefixedSequenceIdentifier());
        msAssert.isNull(field.getCustomStyles());
        msAssert.isFalse(field.getUseParagraphOutlineLevel());
        msAssert.isFalse(field.getPreserveTabs());
        msAssert.isFalse(field.getPreserveLineBreaks());
        msAssert.isFalse(field.getHideInWebLayout());

        field.setCaptionlessTableOfFiguresLabel("Test1");
        msAssert.areEqual(" TOC  \\a Test1", field.getFieldCode());

        field.setBookmarkName("Test2");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2", field.getFieldCode());

        field.setTableOfFiguresLabel("Test3");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3", field.getFieldCode());

        field.setSequenceSeparator("Test4");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4", field.getFieldCode());

        field.setEntryIdentifier("Test5");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5", field.getFieldCode());

        field.setInsertHyperlinks(true);
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h", field.getFieldCode());

        field.setEntryLevelRange("Test6");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6", field.getFieldCode());

        field.setPageNumberOmittingLevelRange("Test7");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7", field.getFieldCode());

        field.setHeadingLevelRange("Test8");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8", field.getFieldCode());

        field.setEntrySeparator("Test9");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9", field.getFieldCode());

        field.setPrefixedSequenceIdentifier("Test10");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9 \\s Test10", field.getFieldCode());

        field.setCustomStyles("Test11");
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9 \\s Test10 \\t Test11", field.getFieldCode());

        field.setUseParagraphOutlineLevel(true);
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9 \\s Test10 \\t Test11 \\u", field.getFieldCode());

        field.setPreserveTabs(true);
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9 \\s Test10 \\t Test11 \\u \\w", field.getFieldCode());

        field.setPreserveLineBreaks(true);
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9 \\s Test10 \\t Test11 \\u \\w \\x", field.getFieldCode());

        field.setHideInWebLayout(true);
        msAssert.areEqual(" TOC  \\a Test1 \\b Test2 \\c Test3 \\d Test4 \\f Test5 \\h \\l Test6 \\n Test7 \\o Test8 \\p Test9 \\s Test10 \\t Test11 \\u \\w \\x \\z", field.getFieldCode());

        msAssert.areEqual("Test1", field.getCaptionlessTableOfFiguresLabel());
        msAssert.areEqual("Test2", field.getBookmarkName());
        msAssert.areEqual("Test3", field.getTableOfFiguresLabel());
        msAssert.areEqual("Test4", field.getSequenceSeparator());
        msAssert.areEqual("Test5", field.getEntryIdentifier());
        msAssert.isTrue(field.getInsertHyperlinks());
        msAssert.areEqual("Test6", field.getEntryLevelRange());
        msAssert.areEqual("Test7", field.getPageNumberOmittingLevelRange());
        msAssert.areEqual("Test8", field.getHeadingLevelRange());
        msAssert.areEqual("Test9", field.getEntrySeparator());
        msAssert.areEqual("Test10", field.getPrefixedSequenceIdentifier());
        msAssert.areEqual("Test11", field.getCustomStyles());
        msAssert.isTrue(field.getUseParagraphOutlineLevel());
        msAssert.isTrue(field.getPreserveTabs());
        msAssert.isTrue(field.getPreserveLineBreaks());
        msAssert.isTrue(field.getHideInWebLayout());
    }

    @Test
    public void testUserAddress() throws Exception
    {
        FieldUserAddress field = (FieldUserAddress)createField(FieldType.FIELD_USER_ADDRESS);

        msAssert.areEqual(FieldType.FIELD_USER_ADDRESS, field.getType());
        msAssert.areEqual(" USERADDRESS ", field.getFieldCode());

        msAssert.isNull(field.getUserAddress());

        field.setUserAddress("Test1");
        msAssert.areEqual(" USERADDRESS  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getUserAddress());
    }

    @Test
    public void testUserInitials() throws Exception
    {
        FieldUserInitials field = (FieldUserInitials)createField(FieldType.FIELD_USER_INITIALS);

        msAssert.areEqual(FieldType.FIELD_USER_INITIALS, field.getType());
        msAssert.areEqual(" USERINITIALS ", field.getFieldCode());

        msAssert.isNull(field.getUserInitials());

        field.setUserInitials("Test1");
        msAssert.areEqual(" USERINITIALS  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getUserInitials());
    }

    @Test
    public void testUserName() throws Exception
    {
        FieldUserName field = (FieldUserName)createField(FieldType.FIELD_USER_NAME);

        msAssert.areEqual(FieldType.FIELD_USER_NAME, field.getType());
        msAssert.areEqual(" USERNAME ", field.getFieldCode());

        msAssert.isNull(field.getUserName());

        field.setUserName("Test1");
        msAssert.areEqual(" USERNAME  Test1", field.getFieldCode());

        msAssert.areEqual("Test1", field.getUserName());
    }

    @Test
    public void testXE() throws Exception
    {
        FieldXE field = (FieldXE)createField(FieldType.FIELD_INDEX_ENTRY);

        msAssert.areEqual(FieldType.FIELD_INDEX_ENTRY, field.getType());
        msAssert.areEqual(" XE ", field.getFieldCode());

        msAssert.isNull(field.getText());
        msAssert.isFalse(field.isBold());
        msAssert.isNull(field.getEntryType());
        msAssert.isFalse(field.isItalic());
        msAssert.isNull(field.getPageRangeBookmarkName());
        msAssert.isNull(field.getPageNumberReplacement());
        msAssert.isNull(field.getYomi());

        field.setText("Test1");
        msAssert.areEqual(" XE  Test1", field.getFieldCode());

        field.isBold(true);
        msAssert.areEqual(" XE  Test1 \\b", field.getFieldCode());

        field.setEntryType("Test2");
        msAssert.areEqual(" XE  Test1 \\b \\f Test2", field.getFieldCode());

        field.isItalic(true);
        msAssert.areEqual(" XE  Test1 \\b \\f Test2 \\i", field.getFieldCode());

        field.setPageRangeBookmarkName("Test3");
        msAssert.areEqual(" XE  Test1 \\b \\f Test2 \\i \\r Test3", field.getFieldCode());

        field.setPageNumberReplacement("Test4");
        msAssert.areEqual(" XE  Test1 \\b \\f Test2 \\i \\r Test3 \\t Test4", field.getFieldCode());

        field.setYomi("Test5");
        msAssert.areEqual(" XE  Test1 \\b \\f Test2 \\i \\r Test3 \\t Test4 \\y Test5", field.getFieldCode());

        msAssert.areEqual("Test1", field.getText());
        msAssert.isTrue(field.isBold());
        msAssert.areEqual("Test2", field.getEntryType());
        msAssert.isTrue(field.isItalic());
        msAssert.areEqual("Test3", field.getPageRangeBookmarkName());
        msAssert.areEqual("Test4", field.getPageNumberReplacement());
        msAssert.areEqual("Test5", field.getYomi());
    }


    /// <summary>
    /// WORDSNET-5113 In ASK field answer is stored in a bookmark. If used this bookmark in IF field, MS Word takes a value from ASK field..
    /// </summary>
    @Test
    public void testDefect5113() throws Exception
    {
        Document doc = TestUtil.open("Fields\\FieldTypes\\TestDefect5113.docx");
        doc.updateFields();
        TestUtil.saveCheckGold(doc, "Fields\\FieldTypes\\TestDefect5113.docx");
    }

    /// <summary>
    /// WORDSNET-5020 If the field code is enclosed in quotes, it should be parsed correctly
    /// </summary>
    @Test
    public void testDefect5020() throws Exception
    {
        Document doc = TestUtil.open("Fields\\FieldTypes\\TestDefect5020.docx");
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc, false);
        msAssert.isInstanceOf(FieldCreateDate.class, fields.get(0));
    }

    private Field createField(/*FieldType*/int fieldType) throws Exception
    {
        if (mDoc == null)
            mDoc = new Document();

        DocumentBuilder builder = new DocumentBuilder(mDoc);
        return builder.insertField(fieldType, true);
    }

    /// <summary>
    /// WORDSNET-3602 Consider updating invalid IF fields.
    /// </summary>
    @Test
    public void testJira3602() throws Exception
    {
        Document doc = TestUtil.openUpdateFields("Fields\\FieldTypes\\TestJira3602.docx");
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc, false);

        msAssert.areEqual("True Text", fields.get(0).getResult());
        msAssert.areEqual("1", fields.get(1).getResult());
        msAssert.areEqual("0", fields.get(2).getResult());
        msAssert.areEqual("-1", fields.get(3).getResult());
        msAssert.areEqual("*1", fields.get(4).getResult());
        msAssert.areEqual("aaa1", fields.get(5).getResult());
        msAssert.areEqual("Error! Unknown op code for conditional.", fields.get(6).getResult());
        msAssert.areEqual("Error! Unknown op code for conditional.", fields.get(7).getResult());
        msAssert.areEqual("True Text", fields.get(8).getResult());
        msAssert.areEqual("Error! Unknown op code for conditional.", fields.get(9).getResult());
        msAssert.areEqual("True Text", fields.get(10).getResult());
        msAssert.areEqual("True Text", fields.get(11).getResult());
        msAssert.areEqual("True Text", fields.get(12).getResult());
        msAssert.areEqual("True Text", fields.get(13).getResult());
        msAssert.areEqual("True Text", fields.get(14).getResult());
        msAssert.areEqual("True Text", fields.get(15).getResult());
        msAssert.areEqual("False Text", fields.get(16).getResult());
    }

    /// <summary>
    /// WORDSNET-4102 Portuguese style name in STYLEREF field was not translated.
    /// </summary>
    @Test
    public void testJira4102() throws Exception
    {
        Document doc = TestUtil.openUpdateFields("Fields\\FieldTypes\\TestJira4102.docx");

        // No update page layout in FOSS.

        String asText;
        MemoryStream stream = TestUtil.save(doc, SaveFormat.TEXT);
        try /*JAVA: was using*/
    	{
        StreamReader reader = new StreamReader(stream);
        try /*JAVA: was using*/
        	{
            asText = reader.readToEnd();
        	}
        finally { if (reader != null) reader.close(); }
    	}
        finally { if (stream != null) stream.close(); }

        if (asText.contains(FieldStyleRef.NO_TEXT_OF_STYLE_ERROR_MESSAGE))
            Assert.fail("Portuguese style name is not translated on updating STYLEREF field.");
    }

    /// <summary>
    /// Tests how the field types normalization works.
    /// </summary>
    @Test
    public void testNormalizeFieldTypes() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertField(" DATE ", null);

        Document document = builder.getDocument();
        Run run = (Run)document.getNodeById("1.0.0.0");
        run.setText(" PAGE \\* roman ");

        document.normalizeFieldTypes();

        Field field = FieldExtractor.extractToCollection(document).get(0);
        msAssert.isInstanceOf(FieldPage.class, field);
        msAssert.areEqual(FieldType.FIELD_PAGE, field.getType());
        msAssert.areEqual(FieldType.FIELD_PAGE, field.getStart().getFieldType());
        msAssert.areEqual(FieldType.FIELD_PAGE, field.getSeparator().getFieldType());
        msAssert.areEqual(FieldType.FIELD_PAGE, field.getEnd().getFieldType());
    }

    /// <summary>
    /// WORDSNET-13246 Replacement of URL (UNC) is losing \ and breaking links (FieldHyperlink.Address).
    /// WORDSNET-13247 FieldHyperlink.Address ignores slashes ("\") while replacing hyperlinks.
    /// </summary>
    @Test
    public void testJira13246And13247() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        FieldHyperlink field = (FieldHyperlink)builder.insertField(" HYPERLINK \\\\\\\\host\\\\folder\\\\file.docx");

        field.setAddress("\\\\new host\\new folder\\new file.docx");

        msAssert.areEqual("\\\\new host\\new folder\\new file.docx", field.getAddress());
        msAssert.areEqual(" HYPERLINK \"\\\\\\\\new host\\\\new folder\\\\new file.docx\"", field.getFieldCode());
    }


    /// <summary>
    /// WORDSNET-10033 Field.Type return incorrect value for SaveDate field when it is hidden.
    /// </summary>
    @Test
    public void test10033() throws Exception
    {
        Document document = TestUtil.open("Fields\\FieldTypes\\Test10033.docx");

        Field field = document.getRange().getFields().get(0);

        msAssert.areEqual(FieldType.FIELD_NONE, field.getType());
    }

    /// <summary>
    /// Required for C++.
    /// </summary>
    private Document mDoc;
}

