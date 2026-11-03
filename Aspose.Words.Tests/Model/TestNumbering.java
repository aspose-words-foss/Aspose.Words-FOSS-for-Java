// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.JapaneseNumber;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests concerning numbering which are independent on file formats though they may read files to build valid model.
/// </summary>
@Test
public class TestNumbering
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testKanjiNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestKanjiNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.KANJI, paras.get(0).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("〇.", listLabel(paras, 0)); // 0
        msAssert.areEqual("一.", listLabel(paras, 1)); // 1
        msAssert.areEqual("二.", listLabel(paras, 2)); // 2
        msAssert.areEqual("三.", listLabel(paras, 3)); // 3
        msAssert.areEqual("四.", listLabel(paras, 4)); // 4
        msAssert.areEqual("五.", listLabel(paras, 5)); // 5
        msAssert.areEqual("六.", listLabel(paras, 6)); // 6
        msAssert.areEqual("七.", listLabel(paras, 7)); // 7
        msAssert.areEqual("八.", listLabel(paras, 8)); // 8
        msAssert.areEqual("九.", listLabel(paras, 9)); // 9
        msAssert.areEqual("一〇.", listLabel(paras, 10)); // 10
        msAssert.areEqual("一一.", listLabel(paras, 11)); // 11
        msAssert.areEqual("二一.", listLabel(paras, 12)); // 21
        msAssert.areEqual("一二一.", listLabel(paras, 13)); // 121
        msAssert.areEqual("二二一.", listLabel(paras, 14)); // 221
        msAssert.areEqual("一二三四.", listLabel(paras, 15)); // 1234
        msAssert.areEqual("二三四五.", listLabel(paras, 16)); // 2345
        msAssert.areEqual("一二三四五.", listLabel(paras, 17)); // 12345
        msAssert.areEqual("二三四五六.", listLabel(paras, 18)); // 23456
        msAssert.areEqual("三二七六七.", listLabel(paras, 19)); // 32767
    }

    @Test
    public void testBigKanjiNumbering()
    {
        final int MRD = 10000;
        msAssert.areEqual("九百八十三万六千七百三", JapaneseNumber.toJapaneseCounting(983 * MRD + 6703));
        msAssert.areEqual("二十億三千六百五十二万千八百一", JapaneseNumber.toJapaneseCounting(20 * MRD * MRD + 3652 * MRD + 1801));
    }

    @Test
    public void testKanjiDigitNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestKanjiDigitNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.KANJI_DIGIT, paras.get(0).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("〇.", listLabel(paras, 0)); // 0
        msAssert.areEqual("一.", listLabel(paras, 1)); // 1
        msAssert.areEqual("二.", listLabel(paras, 2)); // 2
        msAssert.areEqual("三.", listLabel(paras, 3)); // 3
        msAssert.areEqual("四.", listLabel(paras, 4)); // 4
        msAssert.areEqual("五.", listLabel(paras, 5)); // 5
        msAssert.areEqual("六.", listLabel(paras, 6)); // 6
        msAssert.areEqual("七.", listLabel(paras, 7)); // 7
        msAssert.areEqual("八.", listLabel(paras, 8)); // 8
        msAssert.areEqual("九.", listLabel(paras, 9)); // 9
        msAssert.areEqual("十.", listLabel(paras, 10)); // 10
        msAssert.areEqual("十一.", listLabel(paras, 11)); // 11
        msAssert.areEqual("二十一.", listLabel(paras, 12)); // 21
        msAssert.areEqual("百二十一.", listLabel(paras, 13)); // 121
        msAssert.areEqual("二百二十一.", listLabel(paras, 14)); // 221
        msAssert.areEqual("千二百三十四.", listLabel(paras, 15)); // 1234
        msAssert.areEqual("二千三百四十五.", listLabel(paras, 16)); // 2345
        msAssert.areEqual("一万二千三百四十五.", listLabel(paras, 17)); // 12345
        msAssert.areEqual("二万三千四百五十六.", listLabel(paras, 18)); // 23456
        msAssert.areEqual("三万二千七百六十七.", listLabel(paras, 19)); // 32767
    }


    /// <summary>
    /// WORDSNET-4628 <see cref="NumberStyle.ArabicFullWidth"/> numbering style was not supported.
    /// </summary>
    @Test
    public void testArabicFullWidthNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestArabicFullWidthNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("０．", listLabel(paragraphs, 0));
        msAssert.areEqual("１．", listLabel(paragraphs, 1));
        msAssert.areEqual("２．", listLabel(paragraphs, 2));
        msAssert.areEqual("３．", listLabel(paragraphs, 3));
        msAssert.areEqual("４．", listLabel(paragraphs, 4));
        msAssert.areEqual("５．", listLabel(paragraphs, 5));
        msAssert.areEqual("６．", listLabel(paragraphs, 6));
        msAssert.areEqual("７．", listLabel(paragraphs, 7));
        msAssert.areEqual("８．", listLabel(paragraphs, 8));
        msAssert.areEqual("９．", listLabel(paragraphs, 9));
        msAssert.areEqual("１０．", listLabel(paragraphs, 10));
        msAssert.areEqual("３２７６７．", listLabel(paragraphs, 11));
    }

    /// <summary>
    /// WORDSNET-4628 <see cref="NumberStyle.Aiueo"/> numbering style was not supported.
    /// WORDSNET-26576 <see cref="NumberStyle.AiueoHalfWidth"/> numbering style was not supported.
    /// </summary>
    @Test (dataProvider = "testAiueoNumberingDataProvider")
    public void testAiueoNumbering(String document, boolean isHalfWidth) throws Exception
    {
        Document doc = TestUtil.open("Model/List/" + document + ".docx");
        doc.updateListLabels();

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        String AiueoNumbers = "0アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンアイウエアソ";
        String AiueoHalfWidthNumbers = "0ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｱｲｳｴｱｿ";
        String numbers = isHalfWidth ? AiueoHalfWidthNumbers : AiueoNumbers;

        final int NUMBER_OF_PARAGRAPHS = 53;
        for (int i =0; i < NUMBER_OF_PARAGRAPHS; i++)
            msAssert.areEqual("(" + numbers.charAt(i) + ")", listLabel(paragraphs, i));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAiueoNumberingDataProvider")
	public static Object[][] testAiueoNumberingDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestAiueoNumbering",   false},
			{"TestAiueoHalfwidthNumbering",  true},
		};
	}

    /// <summary>
    /// WORDSNET-4628 <see cref="NumberStyle.NumberInCircle"/> numbering style was not supported.
    /// </summary>
    @Test
    public void testNumberInCircleNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestNumberInCircleNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("①", listLabel(paragraphs, 0));
        msAssert.areEqual("②", listLabel(paragraphs, 1));
        msAssert.areEqual("③", listLabel(paragraphs, 2));
        msAssert.areEqual("④", listLabel(paragraphs, 3));
        msAssert.areEqual("⑤", listLabel(paragraphs, 4));
        msAssert.areEqual("⑥", listLabel(paragraphs, 5));
        msAssert.areEqual("⑦", listLabel(paragraphs, 6));
        msAssert.areEqual("⑧", listLabel(paragraphs, 7));
        msAssert.areEqual("⑨", listLabel(paragraphs, 8));
        msAssert.areEqual("⑩", listLabel(paragraphs, 9));
        msAssert.areEqual("⑪", listLabel(paragraphs, 10));
        msAssert.areEqual("⑫", listLabel(paragraphs, 11));
        msAssert.areEqual("⑬", listLabel(paragraphs, 12));
        msAssert.areEqual("⑭", listLabel(paragraphs, 13));
        msAssert.areEqual("⑮", listLabel(paragraphs, 14));
        msAssert.areEqual("⑯", listLabel(paragraphs, 15));
        msAssert.areEqual("⑰", listLabel(paragraphs, 16));
        msAssert.areEqual("⑱", listLabel(paragraphs, 17));
        msAssert.areEqual("⑲", listLabel(paragraphs, 18));
        msAssert.areEqual("⑳", listLabel(paragraphs, 19));
        msAssert.areEqual("21", listLabel(paragraphs, 20));
    }

    /// <summary>
    /// WORDSNET-4628 <see cref="NumberStyle.Iroha"/> numbering style was not supported.
    /// </summary>
    @Test
    public void testIrohaNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestIrohaNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paragraphs = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("0)", listLabel(paragraphs, 0)); // 0
        msAssert.areEqual("イ)", listLabel(paragraphs, 1)); // 1
        msAssert.areEqual("ロ)", listLabel(paragraphs, 2)); // ...
        msAssert.areEqual("ハ)", listLabel(paragraphs, 3));
        msAssert.areEqual("ニ)", listLabel(paragraphs, 4));
        msAssert.areEqual("ホ)", listLabel(paragraphs, 5));
        msAssert.areEqual("ヘ)", listLabel(paragraphs, 6));
        msAssert.areEqual("ト)", listLabel(paragraphs, 7));
        msAssert.areEqual("チ)", listLabel(paragraphs, 8));
        msAssert.areEqual("リ)", listLabel(paragraphs, 9));
        msAssert.areEqual("ヌ)", listLabel(paragraphs, 10));
        msAssert.areEqual("ル)", listLabel(paragraphs, 11));
        msAssert.areEqual("ヲ)", listLabel(paragraphs, 12));
        msAssert.areEqual("ワ)", listLabel(paragraphs, 13));
        msAssert.areEqual("カ)", listLabel(paragraphs, 14));
        msAssert.areEqual("ヨ)", listLabel(paragraphs, 15));
        msAssert.areEqual("タ)", listLabel(paragraphs, 16));
        msAssert.areEqual("レ)", listLabel(paragraphs, 17));
        msAssert.areEqual("ソ)", listLabel(paragraphs, 18));
        msAssert.areEqual("ツ)", listLabel(paragraphs, 19));
        msAssert.areEqual("ネ)", listLabel(paragraphs, 20));
        msAssert.areEqual("ナ)", listLabel(paragraphs, 21));
        msAssert.areEqual("ラ)", listLabel(paragraphs, 22));
        msAssert.areEqual("ム)", listLabel(paragraphs, 23));
        msAssert.areEqual("ウ)", listLabel(paragraphs, 24));
        msAssert.areEqual("ヰ)", listLabel(paragraphs, 25));
        msAssert.areEqual("ノ)", listLabel(paragraphs, 26));
        msAssert.areEqual("オ)", listLabel(paragraphs, 27));
        msAssert.areEqual("ク)", listLabel(paragraphs, 28));
        msAssert.areEqual("ヤ)", listLabel(paragraphs, 29));
        msAssert.areEqual("マ)", listLabel(paragraphs, 30));
        msAssert.areEqual("ケ)", listLabel(paragraphs, 31));
        msAssert.areEqual("フ)", listLabel(paragraphs, 32));
        msAssert.areEqual("コ)", listLabel(paragraphs, 33));
        msAssert.areEqual("エ)", listLabel(paragraphs, 34));
        msAssert.areEqual("テ)", listLabel(paragraphs, 35));
        msAssert.areEqual("ア)", listLabel(paragraphs, 36));
        msAssert.areEqual("サ)", listLabel(paragraphs, 37));
        msAssert.areEqual("キ)", listLabel(paragraphs, 38));
        msAssert.areEqual("ユ)", listLabel(paragraphs, 39));
        msAssert.areEqual("メ)", listLabel(paragraphs, 40));
        msAssert.areEqual("ミ)", listLabel(paragraphs, 41));
        msAssert.areEqual("シ)", listLabel(paragraphs, 42));
        msAssert.areEqual("ヱ)", listLabel(paragraphs, 43));
        msAssert.areEqual("ヒ)", listLabel(paragraphs, 44));
        msAssert.areEqual("モ)", listLabel(paragraphs, 45));
        msAssert.areEqual("セ)", listLabel(paragraphs, 46));
        msAssert.areEqual("ス)", listLabel(paragraphs, 47));
        msAssert.areEqual("ン)", listLabel(paragraphs, 48));
        msAssert.areEqual("イ)", listLabel(paragraphs, 49)); // 49
        msAssert.areEqual("ロ)", listLabel(paragraphs, 50)); // 50
        msAssert.areEqual("イ)", listLabel(paragraphs, 51)); // 481
        msAssert.areEqual("ケ)", listLabel(paragraphs, 52)); // 32767
    }

    @Test
    public void testRomanNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestRomanNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        int index = 0;

        msAssert.areEqual("i.", listLabel(paras, index++));
        msAssert.areEqual("ii.", listLabel(paras, index++));
        msAssert.areEqual("iii.", listLabel(paras, index++));
        msAssert.areEqual("iv.", listLabel(paras, index++));
        msAssert.areEqual("v.", listLabel(paras, index++));
        msAssert.areEqual("vi.", listLabel(paras, index++));
        msAssert.areEqual("vii.", listLabel(paras, index++));
        msAssert.areEqual("viii.", listLabel(paras, index++));
        msAssert.areEqual("ix.", listLabel(paras, index++));
        msAssert.areEqual("x.", listLabel(paras, index++));
        msAssert.areEqual("xi.", listLabel(paras, index++));
        msAssert.areEqual("xx.", listLabel(paras, index++));
        msAssert.areEqual("xxx.", listLabel(paras, index++));
        msAssert.areEqual("xl.", listLabel(paras, index++));
        msAssert.areEqual("xlix.", listLabel(paras, index++));
        msAssert.areEqual("l.", listLabel(paras, index++));
        msAssert.areEqual("lx.", listLabel(paras, index++));
        msAssert.areEqual("lxx.", listLabel(paras, index++));
        msAssert.areEqual("lxxx.", listLabel(paras, index++));
        msAssert.areEqual("xc.", listLabel(paras, index++));
        msAssert.areEqual("xcix.", listLabel(paras, index++));
        msAssert.areEqual("c.", listLabel(paras, index++));
        msAssert.areEqual("cc.", listLabel(paras, index++));
        msAssert.areEqual("ccc.", listLabel(paras, index++));
        msAssert.areEqual("cd.", listLabel(paras, index++));
        msAssert.areEqual("d.", listLabel(paras, index++));
        msAssert.areEqual("dc.", listLabel(paras, index++));
        msAssert.areEqual("dcc.", listLabel(paras, index++));
        msAssert.areEqual("dcclxvii.", listLabel(paras, index++));
        msAssert.areEqual("dccc.", listLabel(paras, index++));
        msAssert.areEqual("cm.", listLabel(paras, index++));
        msAssert.areEqual("m.", listLabel(paras, index++));
        msAssert.areEqual("mmmmmmmmmcmxcix.", listLabel(paras, index++));
        msAssert.areEqual("mmmmmmmmmm.", listLabel(paras, index++));
        msAssert.areEqual("mmmmmmmmmmmmmmmmmmmm.", listLabel(paras, index++));
        msAssert.areEqual("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm.", listLabel(paras, index++));
        msAssert.areEqual("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmdcclxvii.", listLabel(paras, index));
    }

    @Test
    public void testKanjiWithLeadingZeroes()
    {
        msAssert.areEqual("〇〇〇一二", JapaneseNumber.toJapaneseDigital(12, 5));
    }


    /// <summary>
    /// Added support for <see cref="NumberStyle.TradChinNum2"/> numbering style.
    /// </summary>
    @Test
    public void testIdeographLegalTraditionalNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestIdeographLegalTraditional.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.TRAD_CHIN_NUM_2, paras.get(0).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("壹、", listLabel(paras, 0)); // 1
        msAssert.areEqual("貳、", listLabel(paras, 1)); // 2
        msAssert.areEqual("參、", listLabel(paras, 2)); // 3
        msAssert.areEqual("肆、", listLabel(paras, 3)); // 4
        msAssert.areEqual("伍、", listLabel(paras, 4)); // 5
        msAssert.areEqual("陸、", listLabel(paras, 5)); // 6
        msAssert.areEqual("柒、", listLabel(paras, 6)); // 7
        msAssert.areEqual("捌、", listLabel(paras, 7)); // 8
        msAssert.areEqual("玖、", listLabel(paras, 8)); // 9
        msAssert.areEqual("壹拾、", listLabel(paras, 9)); // 10
        msAssert.areEqual("壹拾壹、", listLabel(paras, 10)); // 11
        msAssert.areEqual("貳拾、", listLabel(paras, 11)); // 20
        msAssert.areEqual("貳拾壹、", listLabel(paras, 12)); // 21
        msAssert.areEqual("伍拾、", listLabel(paras, 13)); // 50
        msAssert.areEqual("壹佰、", listLabel(paras, 14)); // 100
        msAssert.areEqual("壹佰零壹、", listLabel(paras, 15)); // 101
        msAssert.areEqual("壹佰壹拾壹、", listLabel(paras, 16)); // 111
        msAssert.areEqual("壹佰貳拾壹、", listLabel(paras, 17)); // 121
        msAssert.areEqual("伍佰、", listLabel(paras, 18)); // 500
        msAssert.areEqual("壹仟、", listLabel(paras, 19)); // 1000
        msAssert.areEqual("壹仟零壹、", listLabel(paras, 20)); // 1001
        msAssert.areEqual("壹萬、", listLabel(paras, 21)); // 10000
        msAssert.areEqual("壹萬零壹、", listLabel(paras, 22)); // 10001
        msAssert.areEqual("壹萬零壹拾壹、", listLabel(paras, 23)); // 10011
        msAssert.areEqual("壹萬零壹佰零壹、", listLabel(paras, 24)); // 10101
        msAssert.areEqual("壹萬零壹佰壹拾壹、", listLabel(paras, 25)); // 10111
        msAssert.areEqual("壹萬壹仟壹佰壹拾壹、", listLabel(paras, 26)); // 11111
        msAssert.areEqual("壹萬參仟肆佰伍拾陸、", listLabel(paras, 27)); // 13456
        msAssert.areEqual("貳萬柒仟捌佰玖拾、", listLabel(paras, 28)); // 27890
        msAssert.areEqual("參萬貳仟柒佰陸拾柒、", listLabel(paras, 29)); // 32767
        msAssert.areEqual("參萬貳仟柒佰陸拾捌、", listLabel(paras, 30)); // 32768
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.TradChinNum3"/> numbering style.
    /// </summary>
    @Test
    public void testTaiwaneseCountingThousandNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestTaiwaneseCountingThousand.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.TRAD_CHIN_NUM_3, paras.get(0).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("一、", listLabel(paras, 0)); // 1
        msAssert.areEqual("二、", listLabel(paras, 1)); // 2
        msAssert.areEqual("三、", listLabel(paras, 2)); // 3
        msAssert.areEqual("四、", listLabel(paras, 3)); // 4
        msAssert.areEqual("五、", listLabel(paras, 4)); // 5
        msAssert.areEqual("六、", listLabel(paras, 5)); // 6
        msAssert.areEqual("七、", listLabel(paras, 6)); // 7
        msAssert.areEqual("八、", listLabel(paras, 7)); // 8
        msAssert.areEqual("九、", listLabel(paras, 8)); // 9
        msAssert.areEqual("十、", listLabel(paras, 9)); // 10
        msAssert.areEqual("十一、", listLabel(paras, 10)); // 11
        msAssert.areEqual("二十、", listLabel(paras, 11)); // 20
        msAssert.areEqual("二十一、", listLabel(paras, 12)); // 21
        msAssert.areEqual("五十、", listLabel(paras, 13)); // 50
        msAssert.areEqual("一百、", listLabel(paras, 14)); // 100
        msAssert.areEqual("一百零一、", listLabel(paras, 15)); // 101
        msAssert.areEqual("一百一十一、", listLabel(paras, 16)); // 111
        msAssert.areEqual("一百二十一、", listLabel(paras, 17)); // 121
        msAssert.areEqual("五百、", listLabel(paras, 18)); // 121
        msAssert.areEqual("一千、", listLabel(paras, 19)); // 1000
        msAssert.areEqual("一千零一、", listLabel(paras, 20)); // 1001
        msAssert.areEqual("五千、", listLabel(paras, 21)); // 5000
        msAssert.areEqual("一萬、", listLabel(paras, 22)); // 10000
        msAssert.areEqual("一萬零一、", listLabel(paras, 23)); // 10001
        msAssert.areEqual("一萬零五、", listLabel(paras, 24)); // 10005
        msAssert.areEqual("一萬零一十一、", listLabel(paras, 25)); // 10011
        msAssert.areEqual("一萬零一百零一、", listLabel(paras, 26)); // 10101
        msAssert.areEqual("一萬零一百一十一、", listLabel(paras, 27)); // 10111
        msAssert.areEqual("一萬一千一百一十一、", listLabel(paras, 28)); // 11111
        msAssert.areEqual("一萬三千四百五十六、", listLabel(paras, 29)); // 13456
        msAssert.areEqual("二萬七千八百九十、", listLabel(paras, 30)); // 27890
        msAssert.areEqual("三萬二千七百六十七、", listLabel(paras, 31)); // 32767
        msAssert.areEqual("三萬二千七百六十八、", listLabel(paras, 32)); // 32768
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.SimpChinNum3"/> numbering style.
    /// </summary>
    @Test
    public void testChineseCountingThousandNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestChineseCountingThousand.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.SIMP_CHIN_NUM_3, paras.get(0).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("一、", listLabel(paras, 0)); // 1
        msAssert.areEqual("二、", listLabel(paras, 1)); // 2
        msAssert.areEqual("三、", listLabel(paras, 2)); // 3
        msAssert.areEqual("四、", listLabel(paras, 3)); // 4
        msAssert.areEqual("五、", listLabel(paras, 4)); // 5
        msAssert.areEqual("六、", listLabel(paras, 5)); // 6
        msAssert.areEqual("七、", listLabel(paras, 6)); // 7
        msAssert.areEqual("八、", listLabel(paras, 7)); // 8
        msAssert.areEqual("九、", listLabel(paras, 8)); // 9
        msAssert.areEqual("十、", listLabel(paras, 9)); // 10
        msAssert.areEqual("十一、", listLabel(paras, 10)); // 11
        msAssert.areEqual("二十、", listLabel(paras, 11)); // 20
        msAssert.areEqual("二十一、", listLabel(paras, 12)); // 21
        msAssert.areEqual("五十、", listLabel(paras, 13)); // 50
        msAssert.areEqual("一百、", listLabel(paras, 14)); // 100
        msAssert.areEqual("一百〇一、", listLabel(paras, 15)); // 101
        msAssert.areEqual("一百〇五、", listLabel(paras, 16)); // 105
        msAssert.areEqual("一百一十一、", listLabel(paras, 17)); // 111
        msAssert.areEqual("一百二十一、", listLabel(paras, 18)); // 121
        msAssert.areEqual("五百、", listLabel(paras, 19)); // 500
        msAssert.areEqual("一千、", listLabel(paras, 20)); // 1000
        msAssert.areEqual("一千〇一、", listLabel(paras, 21)); // 1001
        msAssert.areEqual("一千〇五、", listLabel(paras, 22)); // 1005
        msAssert.areEqual("五千、", listLabel(paras, 23)); // 5000
        msAssert.areEqual("一万、", listLabel(paras, 24)); // 10000
        msAssert.areEqual("一万〇一、", listLabel(paras, 25)); // 10001
        msAssert.areEqual("一万〇五、", listLabel(paras, 26)); // 10005
        msAssert.areEqual("一万〇一十一、", listLabel(paras, 27)); // 10011
        msAssert.areEqual("一万〇一百〇一、", listLabel(paras, 28)); // 10101
        msAssert.areEqual("一万〇一百一十一、", listLabel(paras, 29)); // 10111
        msAssert.areEqual("一万一千一百一十一、", listLabel(paras, 30)); // 11111
        msAssert.areEqual("一万三千四百五十六、", listLabel(paras, 31)); // 13456
        msAssert.areEqual("二万七千八百九十、", listLabel(paras, 32)); // 27890
        msAssert.areEqual("三万二千七百六十七、", listLabel(paras, 33)); // 32767
        msAssert.areEqual("三万二千七百六十八、", listLabel(paras, 34)); // 32768
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.SimpChinNum1"/> numbering style.
    /// </summary>
    @Test
    public void testChineseCountingNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestChineseCounting.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.SIMP_CHIN_NUM_1, paras.get(0).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("一、", listLabel(paras, 0)); // 1
        msAssert.areEqual("二、", listLabel(paras, 1)); // 2
        msAssert.areEqual("三、", listLabel(paras, 2)); // 3
        msAssert.areEqual("四、", listLabel(paras, 3)); // 4
        msAssert.areEqual("五、", listLabel(paras, 4)); // 5
        msAssert.areEqual("六、", listLabel(paras, 5)); // 6
        msAssert.areEqual("七、", listLabel(paras, 6)); // 7
        msAssert.areEqual("八、", listLabel(paras, 7)); // 8
        msAssert.areEqual("九、", listLabel(paras, 8)); // 9
        msAssert.areEqual("十、", listLabel(paras, 9)); // 10
        msAssert.areEqual("十一、", listLabel(paras, 10)); // 11
        msAssert.areEqual("二十、", listLabel(paras, 11)); // 20
        msAssert.areEqual("二十一、", listLabel(paras, 12)); // 21
        msAssert.areEqual("五十、", listLabel(paras, 13)); // 50
        msAssert.areEqual("一○○、", listLabel(paras, 14)); // 100
        msAssert.areEqual("一○一、", listLabel(paras, 15)); // 101
        msAssert.areEqual("一○五、", listLabel(paras, 16)); // 105
        msAssert.areEqual("一一一、", listLabel(paras, 17)); // 111
        msAssert.areEqual("一二一、", listLabel(paras, 18)); // 121
        msAssert.areEqual("五○○、", listLabel(paras, 19)); // 500
        msAssert.areEqual("一○○○、", listLabel(paras, 20)); // 1000
        msAssert.areEqual("一○○一、", listLabel(paras, 21)); // 1001
        msAssert.areEqual("一○○五、", listLabel(paras, 22)); // 1005
        msAssert.areEqual("五○○○、", listLabel(paras, 23)); // 5000
        msAssert.areEqual("一○○○○、", listLabel(paras, 24)); // 10000
        msAssert.areEqual("一○○○一、", listLabel(paras, 25)); // 10001
        msAssert.areEqual("一○○○五、", listLabel(paras, 26)); // 10005
        msAssert.areEqual("一○○一一、", listLabel(paras, 27)); // 10011
        msAssert.areEqual("一○一○一、", listLabel(paras, 28)); // 10101
        msAssert.areEqual("一○一一一、", listLabel(paras, 29)); // 10111
        msAssert.areEqual("一一一一一、", listLabel(paras, 30)); // 11111
        msAssert.areEqual("一三四五六、", listLabel(paras, 31)); // 13456
        msAssert.areEqual("二七八九○、", listLabel(paras, 32)); // 27890
        msAssert.areEqual("三二七六七、", listLabel(paras, 33)); // 32767
        msAssert.areEqual("三二七六八、", listLabel(paras, 34)); // 32768
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.SimpChinNum2"/> numbering style.
    /// WORDSNET-15957 Rendering issue of Chinese number list
    /// </summary>
    @Test (dataProvider = "testChineseSimplifiedNumberingDataProvider")
    public void testChineseSimplifiedNumbering(int value, String expected)
    {
        String actual = NumberConverter.numberToLocalizedString(value, NumberStyle.SIMP_CHIN_NUM_2, false);
        msAssert.areEqual(expected, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testChineseSimplifiedNumberingDataProvider")
	public static Object[][] testChineseSimplifiedNumberingDataProvider()
	{
		return new Object[][]
		{
			{1,  "壹"},
			{2,  "贰"},
			{3,  "叁"},
			{4,  "肆"},
			{5,  "伍"},
			{6,  "陆"},
			{7,  "柒"},
			{8,  "捌"},
			{9,  "玖"},
			{10,  "壹拾"},
			{11,  "壹拾壹"},
			{34,  "叁拾肆"},
			{50,  "伍拾"},
			{100,  "壹佰"},
			{102,  "壹佰零贰"},
			{110,  "壹佰壹拾"},
			{1000,  "壹仟"},
			{2060,  "贰仟零陆拾"},
			{2093,  "贰仟零玖拾叁"},
			{3500,  "叁仟伍佰"},
			{3604,  "叁仟陆佰零肆"},
			{4987,  "肆仟玖佰捌拾柒"},
			{5000,  "伍仟"},
			{10000,  "壹万"},
			{10008,  "壹万零捌"},
			{10309,  "壹万零叁佰零玖"},
			{30000,  "叁万"},
			{45000,  "肆万伍仟"},
			{56001,  "伍万陆仟零壹"},
			{67890,  "陆万柒仟捌佰玖拾"},
			{500000,  "伍拾万"},
			{1000200,  "壹佰万零贰佰"},
			{50000003,  "伍仟万零叁"},
		};
	}

    /// <summary>
    /// Added support for <see cref="NumberStyle.Zodiac1"/> numbering style.
    /// </summary>
    @Test
    public void testZodiac1Numbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestZodiac1.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.ZODIAC_1, paras.get(1).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("甲", listLabel(paras, 1)); // 1
        msAssert.areEqual("乙", listLabel(paras, 2)); // 2
        msAssert.areEqual("丙", listLabel(paras, 3)); // 3
        msAssert.areEqual("丁", listLabel(paras, 4)); // 4
        msAssert.areEqual("戊", listLabel(paras, 5)); // 5
        msAssert.areEqual("己", listLabel(paras, 6)); // 6
        msAssert.areEqual("庚", listLabel(paras, 7)); // 7
        msAssert.areEqual("辛", listLabel(paras, 8)); // 8
        msAssert.areEqual("壬", listLabel(paras, 9)); // 9
        msAssert.areEqual("癸", listLabel(paras, 10)); // 10
        msAssert.areEqual("11", listLabel(paras, 11)); // 11
        msAssert.areEqual("12", listLabel(paras, 12)); // 12
        msAssert.areEqual("100", listLabel(paras, 100)); // 100
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.Zodiac2"/> numbering style.
    /// </summary>
    @Test
    public void testZodiac2Numbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestZodiac2.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.ZODIAC_2, paras.get(1).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("子", listLabel(paras, 1)); // 1
        msAssert.areEqual("丑", listLabel(paras, 2)); // 2
        msAssert.areEqual("寅", listLabel(paras, 3)); // 3
        msAssert.areEqual("卯", listLabel(paras, 4)); // 4
        msAssert.areEqual("辰", listLabel(paras, 5)); // 5
        msAssert.areEqual("巳", listLabel(paras, 6)); // 6
        msAssert.areEqual("午", listLabel(paras, 7)); // 7
        msAssert.areEqual("未", listLabel(paras, 8)); // 8
        msAssert.areEqual("申", listLabel(paras, 9)); // 9
        msAssert.areEqual("酉", listLabel(paras, 10)); // 10
        msAssert.areEqual("戍", listLabel(paras, 11)); // 11
        msAssert.areEqual("亥", listLabel(paras, 12)); // 12
        msAssert.areEqual("13", listLabel(paras, 13)); // 13
        msAssert.areEqual("14", listLabel(paras, 14)); // 14
        msAssert.areEqual("100", listLabel(paras, 100)); // 100
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.Zodiac3"/> numbering style.
    /// </summary>
    @Test
    public void testZodiac3Numbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestZodiac3.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.ZODIAC_3, paras.get(1).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("甲子", listLabel(paras, 1)); // 1
        msAssert.areEqual("乙丑", listLabel(paras, 2)); // 2
        msAssert.areEqual("丙寅", listLabel(paras, 3)); // 3
        msAssert.areEqual("丁卯", listLabel(paras, 4)); // 4
        msAssert.areEqual("戊辰", listLabel(paras, 5)); // 5
        msAssert.areEqual("己巳", listLabel(paras, 6)); // 6
        msAssert.areEqual("庚午", listLabel(paras, 7)); // 7
        msAssert.areEqual("辛未", listLabel(paras, 8)); // 8
        msAssert.areEqual("壬申", listLabel(paras, 9)); // 9
        msAssert.areEqual("癸酉", listLabel(paras, 10)); // 10
        msAssert.areEqual("甲戍", listLabel(paras, 11)); // 11
        msAssert.areEqual("乙亥", listLabel(paras, 12)); // 12
        msAssert.areEqual("丙子", listLabel(paras, 13)); // 13
        msAssert.areEqual("丁丑", listLabel(paras, 14)); // 14
        msAssert.areEqual("戊寅", listLabel(paras, 15)); // 15
        msAssert.areEqual("己卯", listLabel(paras, 16)); // 16
        msAssert.areEqual("庚辰", listLabel(paras, 17)); // 17
        msAssert.areEqual("癸未", listLabel(paras, 20)); // 20
        msAssert.areEqual("丁亥", listLabel(paras, 24)); // 24
        msAssert.areEqual("戊子", listLabel(paras, 25)); // 25
        msAssert.areEqual("癸巳", listLabel(paras, 30)); // 30
        msAssert.areEqual("甲午", listLabel(paras, 31)); // 31
        msAssert.areEqual("己亥", listLabel(paras, 36)); // 36
        msAssert.areEqual("庚子", listLabel(paras, 37)); // 37
        msAssert.areEqual("癸卯", listLabel(paras, 40)); // 40
        msAssert.areEqual("癸丑", listLabel(paras, 50)); // 50
        msAssert.areEqual("丙寅", listLabel(paras, 63)); // 63
        msAssert.areEqual("丁卯", listLabel(paras, 64)); // 64
        msAssert.areEqual("戊辰", listLabel(paras, 65)); // 65
        msAssert.areEqual("庚辰", listLabel(paras, 77)); // 77
        msAssert.areEqual("辛卯", listLabel(paras, 88)); // 88
        msAssert.areEqual("壬寅", listLabel(paras, 99)); // 99
        msAssert.areEqual("癸卯", listLabel(paras, 100)); // 100
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.GB2"/> numbering style.
    /// </summary>
    @Test
    public void testGb2Numbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestGb2.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.GB_2, paras.get(1).getListFormat().getListLevel().getNumberStyle());

        msAssert.areEqual("⑴", listLabel(paras, 1)); // 1
        msAssert.areEqual("⑵", listLabel(paras, 2)); // 2
        msAssert.areEqual("⑶", listLabel(paras, 3)); // 3
        msAssert.areEqual("⑷", listLabel(paras, 4)); // 4
        msAssert.areEqual("⑸", listLabel(paras, 5)); // 5
        msAssert.areEqual("⑹", listLabel(paras, 6)); // 6
        msAssert.areEqual("⑺", listLabel(paras, 7)); // 7
        msAssert.areEqual("⑻", listLabel(paras, 8)); // 8
        msAssert.areEqual("⑼", listLabel(paras, 9)); // 9
        msAssert.areEqual("⑽", listLabel(paras, 10)); // 10
        msAssert.areEqual("⑾", listLabel(paras, 11)); // 11
        msAssert.areEqual("⑿", listLabel(paras, 12)); // 12
        msAssert.areEqual("⒀", listLabel(paras, 13)); // 13
        msAssert.areEqual("⒁", listLabel(paras, 14)); // 14
        msAssert.areEqual("⒂", listLabel(paras, 15)); // 15
        msAssert.areEqual("⒃", listLabel(paras, 16)); // 16
        msAssert.areEqual("⒄", listLabel(paras, 17)); // 17
        msAssert.areEqual("⒅", listLabel(paras, 18)); // 18
        msAssert.areEqual("⒆", listLabel(paras, 19)); // 19
        msAssert.areEqual("⒇", listLabel(paras, 20)); // 20
        msAssert.areEqual("21", listLabel(paras, 21)); // 21
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.Custom"/> numbering style.
    /// Tests "001, 002, 003, ..." custom style.
    /// </summary>
    @Test
    public void testCustomLeadingZero2() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestCustomLeadingZero2.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.CUSTOM, paras.get(1).getListFormat().getListLevel().getNumberStyle());
        msAssert.areEqual("001, 002, 003, ...", paras.get(1).getListFormat().getListLevel().getCustomNumberStyle());

        msAssert.areEqual("001.", listLabel(paras, 1));
        msAssert.areEqual("002.", listLabel(paras, 2));
        msAssert.areEqual("009.", listLabel(paras, 3));
        msAssert.areEqual("010.", listLabel(paras, 4));
        msAssert.areEqual("011.", listLabel(paras, 5));
        msAssert.areEqual("099.", listLabel(paras, 6));
        msAssert.areEqual("100.", listLabel(paras, 7));
        msAssert.areEqual("101.", listLabel(paras, 8));
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.Custom"/> numbering style.
    /// Tests "0001, 0002, 0003, ..." custom style.
    /// </summary>
    @Test
    public void testCustomLeadingZero3() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestCustomLeadingZero3.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.CUSTOM, paras.get(1).getListFormat().getListLevel().getNumberStyle());
        msAssert.areEqual("0001, 0002, 0003, ...", paras.get(1).getListFormat().getListLevel().getCustomNumberStyle());

        msAssert.areEqual("0001.", listLabel(paras, 1));
        msAssert.areEqual("0002.", listLabel(paras, 2));
        msAssert.areEqual("0009.", listLabel(paras, 3));
        msAssert.areEqual("0010.", listLabel(paras, 4));
        msAssert.areEqual("0011.", listLabel(paras, 5));
        msAssert.areEqual("0099.", listLabel(paras, 6));
        msAssert.areEqual("0100.", listLabel(paras, 7));
        msAssert.areEqual("0101.", listLabel(paras, 8));
        msAssert.areEqual("0999.", listLabel(paras, 9));
        msAssert.areEqual("1000.", listLabel(paras, 10));
        msAssert.areEqual("1001.", listLabel(paras, 11));
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.Custom"/> numbering style.
    /// Tests "00001, 00002, 00003, ..." custom style.
    /// </summary>
    @Test
    public void testCustomLeadingZero4() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestCustomLeadingZero4.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual(NumberStyle.CUSTOM, paras.get(1).getListFormat().getListLevel().getNumberStyle());
        msAssert.areEqual("00001, 00002, 00003, ...", paras.get(1).getListFormat().getListLevel().getCustomNumberStyle());

        msAssert.areEqual("00001.", listLabel(paras, 1));
        msAssert.areEqual("00002.", listLabel(paras, 2));
        msAssert.areEqual("00009.", listLabel(paras, 3));
        msAssert.areEqual("00010.", listLabel(paras, 4));
        msAssert.areEqual("00011.", listLabel(paras, 5));
        msAssert.areEqual("00099.", listLabel(paras, 6));
        msAssert.areEqual("00100.", listLabel(paras, 7));
        msAssert.areEqual("00101.", listLabel(paras, 8));
        msAssert.areEqual("00999.", listLabel(paras, 9));
        msAssert.areEqual("01000.", listLabel(paras, 10));
        msAssert.areEqual("01001.", listLabel(paras, 11));
        msAssert.areEqual("09999.", listLabel(paras, 12));
        msAssert.areEqual("10000.", listLabel(paras, 13));
        msAssert.areEqual("10001.", listLabel(paras, 14));
    }

    /// <summary>
    /// WORDSNET-5914 IndexOutOfRangeException was thrown on saving Rtf or call of doc.UpdateListLabels.
    /// </summary>
    @Test
    public void testJira5914() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestJira5914.docx");

        // FOSS: Rtf writer removed; UpdateListLabels alone reproduces the WORDSNET-5914 fix (no crash).
        doc.updateListLabels();
    }

    @Test
    public void testHexNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestHexNumbering.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        int index = 0;

        msAssert.areEqual("1.", listLabel(paras, index++));
        msAssert.areEqual("2.", listLabel(paras, index++));
        msAssert.areEqual("3.", listLabel(paras, index++));
        msAssert.areEqual("4.", listLabel(paras, index++));
        msAssert.areEqual("5.", listLabel(paras, index++));
        msAssert.areEqual("6.", listLabel(paras, index++));
        msAssert.areEqual("7.", listLabel(paras, index++));
        msAssert.areEqual("8.", listLabel(paras, index++));
        msAssert.areEqual("9.", listLabel(paras, index++));
        msAssert.areEqual("A.", listLabel(paras, index++));
        msAssert.areEqual("B.", listLabel(paras, index++));
        msAssert.areEqual("C.", listLabel(paras, index++));
        msAssert.areEqual("D.", listLabel(paras, index++));
        msAssert.areEqual("E.", listLabel(paras, index++));
        msAssert.areEqual("F.", listLabel(paras, index++));
        msAssert.areEqual("10.", listLabel(paras, index++));
        msAssert.areEqual("11.", listLabel(paras, index++));
        msAssert.areEqual("12.", listLabel(paras, index++));
        msAssert.areEqual("13.", listLabel(paras, index++));
        msAssert.areEqual("14.", listLabel(paras, index++));

        msAssert.areEqual("7FFD.", listLabel(paras, 20));
        msAssert.areEqual("7FFE.", listLabel(paras, 21));
        msAssert.areEqual("7FFF.", listLabel(paras, 22));
    }

    /// <summary>
    /// WORDSNET-7760 Support revisions in list numbers.
    /// andrnosk: If LayoutOptionsCore.RevisionOptions.IsShowOriginalRevision is not set then ParaPr.FormatRevision.RevPr
    /// is used instead of ParaPr upon list labels generating.
    /// </summary>
    @Test
    public void testJira7760() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestJira7760.docx");

        doc.updateListLabels();

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();

        msAssert.isFalse(pc.get(0).getParaPr().hasFormatRevision());
        msAssert.isTrue(pc.get(1).getParaPr().hasFormatRevision());

        // Start number was changed.
        checkListRevision(pc.get(1), "2.", "4.");
        checkListRevision(pc.get(2), "3.", "5.");
        checkListRevision(pc.get(3), "4.", "6.");

        // List was removed.
        checkListRevision(pc.get(5), "1.0", "");
        checkListRevision(pc.get(6), "2.0", "");

        checkListRevision(pc.get(13), "\uf0b7", "3.");

        checkListRevision(pc.get(16), "II.", "2.");
        checkListRevision(pc.get(17), "III.", "3.");

        checkListRevision(pc.get(30), "1.0", "1.0");
        checkListRevision(pc.get(31), "2.0", "a.");
        checkListRevision(pc.get(32), "3.0", "2.0");
        checkListRevision(pc.get(33), "4.0", "3.0");

        checkListRevision(pc.get(35), "1.", "1.");
        checkListRevision(pc.get(36), "2.", "1.1.");
        checkListRevision(pc.get(37), "3.", "1.1.1.");
        checkListRevision(pc.get(38), "4.", "2.");

        checkListRevision(pc.get(40), "1.", "I.");
        checkListRevision(pc.get(41), "1.1.", "a.");
        checkListRevision(pc.get(42), "1.1.1.", "i.");
        checkListRevision(pc.get(43), "1.1.1.1.", "1.");
    }

    /// <summary>
    /// WORDSNET-7972 Model incorrectly computes Istd of the final revision in lists.
    /// </summary>
    @Test
    public void testJira7760Istd() throws Exception
    {
        Document doc = TestUtil.open("Model/List/TestJira7760.docx");

        Paragraph p0 = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 0, true);
        Paragraph p1 = (Paragraph)doc.getChild(NodeType.PARAGRAPH, 1, true);

        msAssert.isNull(p0.getParaPr().getFormatRevision());
        msAssert.isNotNull(p1.getParaPr().getFormatRevision());

        // Calculate AfterChanges.
        ParaPr afterChanges = AttrCollectionExtension.deepClone(p1.getParaPr());
        afterChanges.acceptFormatRevision();

        msAssert.areEqual(p1.getParaPr().getIstd(), afterChanges.getIstd());
    }



    /// <summary>
    /// WORDSNET-6440 List numbering of Bullets is incorrect when converting to fixed page formats
    /// Implemented converting to "Ideograph Legal Traditional" and "Taiwanese Counting Thousand" numbers formats
    /// </summary>
    @Test
    public void testJira6440() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira6440.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("壹、", listLabel(paras, 0));
        msAssert.areEqual("一、", listLabel(paras, 1));
        msAssert.areEqual("二、", listLabel(paras, 2));
        msAssert.areEqual("三、", listLabel(paras, 3));
        msAssert.areEqual("貳、", listLabel(paras, 4));
        msAssert.areEqual("參、", listLabel(paras, 6));
    }

    /// <summary>
    /// WORDSNET-9150 Doc to Pdf conversion issue with Chinese bullet numbers
    /// Implemented converting to "Chinese Counting Thousand" numbers format
    /// </summary>
    @Test
    public void testJira9150() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira9150.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("(一)", listLabel(paras, 0));
        msAssert.areEqual("(二)", listLabel(paras, 1));
    }


    /// <summary>
    /// WORDSNET-11880 Added support for the Arabic Alpha and the Arabic Abjad numbering styles.
    /// </summary>
    @Test
    public void testJira11880_ArabicAlphaAndAbjadNumbering() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira11880_ArabicAlphaAndAbjadNumbering.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // Test Arabic Alpha numbering
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1 + 28; i++)
        {
            String label = listLabel(paras, i);
            int len = label.length() - 1;

            msStringBuilder.append(sb, label.substring((0), (0) + (len)));
        }

        msAssert.areEqual(sb.toString(), "أبتثجحخدذرزسشصضطظعغفقكلمنهوي");
        msAssert.areEqual("أأ.", listLabel(paras, 29));
        msAssert.areEqual("أأأ.", listLabel(paras, 57));
        msAssert.areEqual("أأأأ.", listLabel(paras, 85));
        msAssert.areEqual("أأأأأأ.", listLabel(paras, 141));
        msAssert.areEqual("أأأأأأأ.", listLabel(paras, 169));
        msAssert.areEqual("يييييييييييييي.", listLabel(paras, 392));
        msAssert.areEqual("أ.", listLabel(paras, 393));

        // Test Arabic Abjad numbering
        sb = new StringBuilder();
        for (int i = 402; i < 402 + 28; i++)
        {
            String label = listLabel(paras, i);
            int len = label.length() - 1;

            msStringBuilder.append(sb, label.substring((0), (0) + (len)));
        }

        msAssert.areEqual(sb.toString(), "أبجدهوزحطيكلمنسعفصقرشتثخذضغظ");
    }



    /// <summary>
    /// WORDSNET-12648 Hebrew list labels are not rendered correctly in output PDF.
    /// Numbers 15 and 16 have special representation in Hebrew1.
    /// </summary>
    @Test
    public void testJira12648() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira12648.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("טו.", listLabel(paras, 14));
        msAssert.areEqual("טז.", listLabel(paras, 15));
    }



    /// <summary>
    /// Relates to WORDSNET-10153 Tests that label is changed even if paragraph has no revisions.
    /// </summary>
    @Test
    public void testJira10153B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira10153B.docx");
        doc.updateListLabels();

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();
        msAssert.isTrue(pc.get(0).isDeleteRevision());
        checkListRevision(pc.get(0), 1, 1, "1.", "");

        // Second paragraph has no revisions at all but label is changed since above paragraph is deleted.
        msAssert.isFalse(pc.get(1).hasRevisions());
        checkListRevision(pc.get(1), 1, 1, "2.", "1.");
    }

    /// <summary>
    /// Relates to WORDSNET-10153 Tests that ListId is fetched properly.
    /// </summary>
    @Test
    public void testJira10153C() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira10153C.docx");

        doc.updateListLabels();

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();
        checkListRevision(pc.get(0), 0, 2, "", "I.");
        checkListRevision(pc.get(1), 0, 1, "", "1.");
        checkListRevision(pc.get(2), 0, 2, "", "II.");
    }

    /// <summary>
    /// Relates to WORDSNET-10153 Tests various list revision cases.
    /// </summary>
    @Test
    public void testJira10153D() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira10153D.xml");

        doc.updateListLabels();

        ParagraphCollection pc = doc.getFirstSection().getBody().getParagraphs();
        checkListRevision(pc.get(1), "1.", "1.");
        checkListRevision(pc.get(2), "", "2.");
        checkListRevision(pc.get(3), "2.", "");
        checkListRevision(pc.get(4), "", "a)");
        checkListRevision(pc.get(5), "a)", "b)");
        checkListRevision(pc.get(6), "b)", "");
    }

    /// <summary>
    /// Relates to WORDSNET-10153 Tests that left indent is fetched properly.
    /// </summary>
    @Test
    public void testJira10153E() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira10153E.docx");

        Paragraph para = doc.getFirstSection().getBody().getParagraphs().get(0);

        msAssert.areEqual(0, para.fetchParaAttr(ParaAttr.LEFT_INDENT, RevisionsView.ORIGINAL));
        msAssert.areEqual(720, para.fetchParaAttr(ParaAttr.LEFT_INDENT, RevisionsView.FINAL));
    }


    /// <summary>
    /// Relates to WORDSNET-10153 Tests bulleted list revision.
    /// </summary>
    @Test
    public void testJira9354() throws Exception
    {
        Document doc = TestUtil.open("Model\\Revision\\TestJira9354.docx");
        doc.updateListLabels();

        Paragraph p = doc.getFirstSection().getBody().getParagraphs().get(5);
        checkListRevision(p, "", "\uf0b7");
    }


    /// <summary>
    /// WORDSNET-13685 Docx to Pdf conversion issue with ListLabel.
    /// Should increment skipped levels when not yet processed any level of list
    /// instead of it is level '0'.
    /// </summary>
    @Test
    public void testJira13685() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira13685.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        for (int i = 2; i < 8; i++)
            msAssert.isTrue(paras.get(i).getListLabel().getLabelString().startsWith("2.1"));
    }


    /// <summary>
    /// WORDSNET-14331 ListLabel.LabelString does not return correct value for number style GB2.
    /// Implemented GB2 numbering.
    /// </summary>
    @Test
    public void testJira14331() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira14331.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("(1)", listLabel(paras, 1));
        msAssert.areEqual("⑴", listLabel(paras, 5));
    }

    /// <summary>
    /// WORDSNET-14571 Unable to save a Word document as a PDF - document has special number formatting.
    /// Implemented custom numbering style.
    /// </summary>
    @Test
    public void testJira14571() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira14571.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        for (int i = 0; i < 8; i++)
        {
            String expectedListLabel = msString.format("[000{0}]", i + 1);
            msAssert.areEqual(expectedListLabel, listLabel(paras, i));
        }
    }

    /// <summary>
    /// WORDSNET-14640 System.ArgumentOutOfRangeException is thrown while saving document to Png.
    /// This issue duplicates <see cref="TestJira14571"/>.
    /// </summary>
    @Test
    public void testJira14640() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestJira14640.docx");
        doc.updateListLabels();

        Paragraph para = (Paragraph)doc.getFirstSection().getBody().getChild(NodeType.PARAGRAPH, 3, false);
        msAssert.areEqual("[0001]", para.getListLabel().getLabelString());
    }



    @Test
    public void testListFormatFinalRevision() throws Exception
    {
        Document document = TestUtil.open("Model\\List\\TestListFormatFinalRevision.docx");

        ListFormat listFormat = document.getFirstSection().getBody().getFirstParagraph().getListFormat();

        msAssert.areEqual(0, listFormat.getListId());
        msAssert.areEqual(1, listFormat.getListIdFinal());
        msAssert.areEqual(0, listFormat.getListLevelNumber());
        msAssert.areEqual(0, listFormat.getListLevelNumberFinal());
        msAssert.isNull(listFormat.getList());
        msAssert.isNotNull(listFormat.getListFinal());
        msAssert.isNull(listFormat.getListLevel());
        msAssert.isNotNull(listFormat.getListLevelFinal());
    }



    /// <summary>
    /// WORDSNET-18309 Incorrect list numbers in output PDF.
    /// Document contains several nested fields. We must update paragraph list labels only for
    /// paragraphs at the topmost field result.
    /// </summary>
    @Test
    public void testJira18309() throws Exception
    {
        // Document contains "IF" field, which contains "QUOTE" field with list paragraphs.
        // Paragraphs from "QUOTE" fieldcode and fieldresult should not be considered during list labels update.
        Document doc = TestUtil.open("Model\\List\\TestJira18309.docx");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // Verify the root issue.
        doc.updateListLabels();

        msAssert.areEqual("1.", listLabel(paras, 8));
        msAssert.areEqual("paragraph 5\r", paras.get(8).getText());

        msAssert.areEqual("2.", listLabel(paras, 9));
        msAssert.areEqual("paragraph 6\r", paras.get(9).getText());

        msAssert.areEqual("", listLabel(paras, 15));
        msAssert.areEqual("", listLabel(paras, 16));

        // Verify customer's use case.
        doc.updateFields();
        doc.updateListLabels();

        msAssert.areEqual("", listLabel(paras, 8));
        msAssert.areEqual("", listLabel(paras, 9));

        msAssert.areEqual("1.", listLabel(paras, 15));
        msAssert.areEqual("paragraph 7\r", paras.get(15).getText());

        msAssert.areEqual("2.", listLabel(paras, 16));
        msAssert.areEqual("paragraph 8\r", paras.get(16).getText());
    }

    /// <summary>
    /// WORDSNET-18365 Vietnamese text becomes number in PDF - PAGE field.
    /// Added support for Vietnamese numbering.
    /// </summary>
    @Test (dataProvider = "testVietnameseNumberingDataProvider")
    //>>>>>>>> #region  Cases
    //<<<<<<<< #endregion 
    public void testVietnameseNumbering(int value, String expected)
    {
        String actual = NumberConverter.numberToLocalizedString(value, NumberStyle.VIET_CARDINAL_TEXT, false);
        msAssert.areEqual(expected, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testVietnameseNumberingDataProvider")
	public static Object[][] testVietnameseNumberingDataProvider()
	{
		return new Object[][]
		{
			{0,  "không"},
			{1,  "một"},
			{2,  "hai"},
			{3,  "ba"},
			{4,  "bốn"},
			{5,  "năm"},
			{6,  "sáu"},
			{7,  "bảy"},
			{8,  "tám"},
			{9,  "chín"},
			{10,  "mười"},
			{11,  "mười một"},
			{12,  "mười hai"},
			{13,  "mười ba"},
			{14,  "mười bốn"},
			{15,  "mười lăm"},
			{16,  "mười sáu"},
			{17,  "mười bảy"},
			{18,  "mười tám"},
			{19,  "mười chín"},
			{20,  "hai mươi"},
			{21,  "hai mươi mốt"},
			{22,  "hai mươi hai"},
			{23,  "hai mươi ba"},
			{24,  "hai mươi bốn"},
			{25,  "hai mươi lăm"},
			{26,  "hai mươi sáu"},
			{27,  "hai mươi bảy"},
			{28,  "hai mươi tám"},
			{29,  "hai mươi chín"},
			{30,  "ba mươi"},
			{31,  "ba mươi mốt"},
			{32,  "ba mươi hai"},
			{33,  "ba mươi ba"},
			{34,  "ba mươi bốn"},
			{35,  "ba mươi lăm"},
			{36,  "ba mươi sáu"},
			{37,  "ba mươi bảy"},
			{38,  "ba mươi tám"},
			{39,  "ba mươi chín"},
			{40,  "bốn mươi"},
			{41,  "bốn mươi mốt"},
			{42,  "bốn mươi hai"},
			{43,  "bốn mươi ba"},
			{44,  "bốn mươi bốn"},
			{45,  "bốn mươi lăm"},
			{46,  "bốn mươi sáu"},
			{47,  "bốn mươi bảy"},
			{48,  "bốn mươi tám"},
			{49,  "bốn mươi chín"},
			{50,  "năm mươi"},
			{51,  "năm mươi mốt"},
			{52,  "năm mươi hai"},
			{53,  "năm mươi ba"},
			{54,  "năm mươi bốn"},
			{55,  "năm mươi lăm"},
			{56,  "năm mươi sáu"},
			{57,  "năm mươi bảy"},
			{58,  "năm mươi tám"},
			{59,  "năm mươi chín"},
			{60,  "sáu mươi"},
			{61,  "sáu mươi mốt"},
			{62,  "sáu mươi hai"},
			{63,  "sáu mươi ba"},
			{64,  "sáu mươi bốn"},
			{65,  "sáu mươi lăm"},
			{66,  "sáu mươi sáu"},
			{67,  "sáu mươi bảy"},
			{68,  "sáu mươi tám"},
			{69,  "sáu mươi chín"},
			{70,  "bảy mươi"},
			{71,  "bảy mươi mốt"},
			{72,  "bảy mươi hai"},
			{73,  "bảy mươi ba"},
			{74,  "bảy mươi bốn"},
			{75,  "bảy mươi lăm"},
			{76,  "bảy mươi sáu"},
			{77,  "bảy mươi bảy"},
			{78,  "bảy mươi tám"},
			{79,  "bảy mươi chín"},
			{80,  "tám mươi"},
			{81,  "tám mươi mốt"},
			{82,  "tám mươi hai"},
			{83,  "tám mươi ba"},
			{84,  "tám mươi bốn"},
			{85,  "tám mươi lăm"},
			{86,  "tám mươi sáu"},
			{87,  "tám mươi bảy"},
			{88,  "tám mươi tám"},
			{89,  "tám mươi chín"},
			{90,  "chín mươi"},
			{91,  "chín mươi mốt"},
			{92,  "chín mươi hai"},
			{93,  "chín mươi ba"},
			{94,  "chín mươi bốn"},
			{95,  "chín mươi lăm"},
			{96,  "chín mươi sáu"},
			{97,  "chín mươi bảy"},
			{98,  "chín mươi tám"},
			{99,  "chín mươi chín"},
			{100,  "một trăm"},
			{101,  "một trăm lẻ một"},
			{102,  "một trăm lẻ hai"},
			{103,  "một trăm lẻ ba"},
			{104,  "một trăm lẻ bốn"},
			{105,  "một trăm lẻ năm"},
			{106,  "một trăm lẻ sáu"},
			{107,  "một trăm lẻ bảy"},
			{108,  "một trăm lẻ tám"},
			{109,  "một trăm lẻ chín"},
			{110,  "một trăm mười"},
			{111,  "một trăm mười một"},
			{112,  "một trăm mười hai"},
			{113,  "một trăm mười ba"},
			{114,  "một trăm mười bốn"},
			{115,  "một trăm mười lăm"},
			{116,  "một trăm mười sáu"},
			{117,  "một trăm mười bảy"},
			{118,  "một trăm mười tám"},
			{119,  "một trăm mười chín"},
			{170,  "một trăm bảy mươi"},
			{180,  "một trăm tám mươi"},
			{185,  "một trăm tám mươi lăm"},
			{193,  "một trăm chín mươi ba"},
			{200,  "hai trăm"},
			{222,  "hai trăm hai mươi hai"},
			{300,  "ba trăm"},
			{400,  "bốn trăm"},
			{500,  "năm trăm"},
			{555,  "năm trăm năm mươi lăm"},
			{600,  "sáu trăm"},
			{700,  "bảy trăm"},
			{768,  "bảy trăm sáu mươi tám"},
			{800,  "tám trăm"},
			{900,  "chín trăm"},
			{999,  "chín trăm chín mươi chín"},
			{1000,  "một ngàn"},
			{1001,  "không"},
			{1002,  "không"},
			{1003,  "không"},
		};
	}

    /// <summary>
    /// WORDSNET-19971 Incorrect list numbers with custom numbering format and greek numerals.
    /// Added support for Greek numbering.
    /// </summary>
    @Test (dataProvider = "testGreekNumberingDataProvider")
    //>>>>>>>> #region  Cases
    //<<<<<<<< #endregion 
    public void testGreekNumbering(int value, String expected)
    {
        String actual = NumberConverter.numberToLocalizedString(value, NumberStyle.LOWERCASE_LETTER, "α, β, γ, ...", false, 0);
        msAssert.areEqual(expected, actual);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testGreekNumberingDataProvider")
	public static Object[][] testGreekNumberingDataProvider()
	{
		return new Object[][]
		{
			{0,  ""},
			{10000,  "α"},
			{10006,  "ζ"},
			{-1,  ""},
			{-100,  ""},
			{25,  "κε"},
			{27,  "κζ"},
			{56,  "νστ"},
			{132,  "ρλβ"},
			{1027,  ",ακζ"},
			{1,  "α"},
			{2,  "β"},
			{3,  "γ"},
			{4,  "δ"},
			{5,  "ε"},
			{6,  "στ"},
			{7,  "ζ"},
			{8,  "η"},
			{9,  "θ"},
			{10,  "ι"},
			{20,  "κ"},
			{30,  "λ"},
			{40,  "μ"},
			{50,  "ν"},
			{60,  "ξ"},
			{70,  "ο"},
			{80,  "π"},
			{90,  "ϟ"},
			{100,  "ρ"},
			{200,  "σ"},
			{300,  "τ"},
			{400,  "υ"},
			{500,  "φ"},
			{600,  "χ"},
			{700,  "ψ"},
			{800,  "ω"},
			{900,  "ϡ"},
			{1000,  ",α"},
			{2000,  ",β"},
			{3000,  ",γ"},
			{4000,  ",δ"},
			{5000,  ",ε"},
			{6000,  ",στ"},
			{7000,  ",ζ"},
			{8000,  ",η"},
			{9000,  ",θ"},
		};
	}

    /// <summary>
    /// WORDSNET-19971 Incorrect list numbers in output PDF.
    /// Document contains custom numbering format with greek numerals.
    /// Support for greek numerals has been added.
    /// </summary>
    @Test
    public void test19971() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test19971.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("Α.", listLabel(paras, 0));
        msAssert.areEqual("Β.", listLabel(paras, 1));
        msAssert.areEqual("Γ.", listLabel(paras, 2));
        msAssert.areEqual("Δ.", listLabel(paras, 3));
        msAssert.areEqual("Ε.", listLabel(paras, 4));
        msAssert.areEqual("ΣΤ.", listLabel(paras, 5));
        msAssert.areEqual("Ζ.", listLabel(paras, 6));
        msAssert.areEqual("Η.", listLabel(paras, 7));

    }


    /// <summary>
    /// Related with WORDSNET-22207
    /// Checks how starting number changes when next level of text line is less than previous.
    /// </summary>
    @Test
    public void test22207A() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22207A.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("32.64.128.256", listLabel(paras, 0));
        msAssert.areEqual("32.64.21", listLabel(paras, 1));
        msAssert.areEqual("32.11", listLabel(paras, 2));
        msAssert.areEqual("1.", listLabel(paras, 3));
    }

    /// <summary>
    /// Related with WORDSNET-22207
    /// Checks how starting number changes when next level of text line is more than previous for 2 units.
    /// </summary>
    @Test
    public void test22207B() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22207B.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("32.", listLabel(paras, 0));
        msAssert.areEqual("32.10.20", listLabel(paras, 1));
        msAssert.areEqual("32.11", listLabel(paras, 2));
    }

    /// <summary>
    /// Related with WORDSNET-22207
    /// Checks how starting number changes when next level of text line is less than previous and
    /// then more than previous for 2 units.
    /// </summary>
    @Test
    public void test22207C() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22207C.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("32.64", listLabel(paras, 0));
        msAssert.areEqual("6.", listLabel(paras, 1));
        msAssert.areEqual("6.10.20", listLabel(paras, 2));
    }

    /// <summary>
    /// Related with WORDSNET-22207
    /// Checks how starting number changes when next level of text line is equal to previous.
    /// </summary>
    @Test
    public void test22207D() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22207D.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("32.64", listLabel(paras, 0));
        msAssert.areEqual("32.65", listLabel(paras, 1));
        msAssert.areEqual("32.65.20", listLabel(paras, 2));
    }

    /// <summary>
    /// Related with WORDSNET-22207
    /// Checks that starting number for initial list level is changed when next line has less level than initial
    /// for this list.
    /// </summary>
    @Test
    public void test22207E() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22207E.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("32.65.20", listLabel(paras, 2));
        msAssert.areEqual("32.66", listLabel(paras, 3));
        msAssert.areEqual("6.", listLabel(paras, 4));
        msAssert.areEqual("6.10.20.30", listLabel(paras, 5));
    }

    /// <summary>
    /// WORDSNET-22478 Thai characters are converted to numbers in PDF.
    /// "ThaiArabic" number style is not taken in attention while updating list labels and page numbers.
    /// </summary>
    @Test
    public void test22478() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test22478.docx");
        doc.updateListLabels();
        doc.updateFields();

        // FOSS: the header page number comes from a PAGE field, whose value is layout-dependent and
        // cannot be computed with the rendering/layout engine removed; the list-label checks below
        // (the WORDSNET-22478 subject) are model-level and remain.
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();
        msAssert.areEqual("๙๙๙๘.", paras.get(0).getListLabel().getLabelString());
        msAssert.areEqual("๙๙๙๙.", paras.get(1).getListLabel().getLabelString());
        msAssert.areEqual("๑๐๐๐๐.", paras.get(2).getListLabel().getLabelString());
        msAssert.areEqual("๑๐๐๐๑.", paras.get(3).getListLabel().getLabelString());
        msAssert.areEqual("๑.", paras.get(4).getListLabel().getLabelString());
        msAssert.areEqual("๒.", paras.get(5).getListLabel().getLabelString());
        msAssert.areEqual("๓.", paras.get(6).getListLabel().getLabelString());
        msAssert.areEqual("๔.", paras.get(7).getListLabel().getLabelString());
    }

    /// <summary>
    /// WORDSNET-23547 New OpenXML File Format attribute for bulleted and numbered lists
    /// </summary>
    @Test
    public void test23547() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\List\\Test23547", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(1314290636, doc.getLists().get(0).getDurableId());
    }

    /// <summary>
    /// WORDSNET-23735 Wrong list numbering due to loss and non-use of DurableId attribute values
    /// The test checks that when a list containing <see cref="Lists.List.DurableId"/> property value is imported to
    /// a document, required compliance is set to the document, and the DurableId value is preserved when resaving.
    /// </summary>
    @Test
    public void test23735Saving() throws Exception
    {
        final String FILE_NAME = "Model\\List\\Test23735.docx";
        Document sourceDoc = TestUtil.open(FILE_NAME);
        StructuredDocumentTag sdt = (StructuredDocumentTag)sourceDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        Document destinationDoc = new Document();
        Body body = destinationDoc.getFirstSection().getBody();
        body.removeAllChildren();

        for (Node node : (Iterable<Node>) sdt.getChildNodes(NodeType.ANY, false))
        {
            Node importNode = destinationDoc.importNode(node, true, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            body.appendChild(importNode);
        }

        destinationDoc = TestUtil.saveOpen(destinationDoc, "Model\\List\\Test23735Saving.docx", null, false);

        Paragraph listParagraph = destinationDoc.getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(24866079, listParagraph.getListFormat().getList().getDurableId());
    }

    /// <summary>
    /// WORDSNET-23735 Wrong list numbering due to loss and non-use of DurableId attribute values
    /// The test checks that when a list is being imported during updating an XML-mapped SDT, corresponding list
    /// is searched with using <see cref="Lists.List.DurableId"/> property values.
    /// </summary>
    @Test
    public void test23735Updating() throws Exception
    {
        final String FILE_NAME = "Model\\List\\Test23735Updating.docx";
        Document doc = TestUtil.open(FILE_NAME);
        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
        {
            if (sdt.getXmlMapping().isMapped())
                sdt.getXmlMapping().updateContent();
        }

        TestUtil.save(doc, FILE_NAME, null, true, GoldLevel.EXPORT_ONLY);
    }


    /// <summary>
    /// WORDSNET-23889 Wrong list numbering in SDT bound to custom XML part
    /// </summary>
    @Test
    public void test23889() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test23889.docx");
        int listCount = doc.getLists().getCount();
        int listDefCount = doc.getLists().getListDefCount();

        Node[] sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).toArray();
        for (Node sdt : sdts)
            ((StructuredDocumentTag)sdt).getXmlMapping().updateContent();

        // Check that paragraph list numbers are correct.
        ListLabelUpdater.updateListLabels(doc);
        StructuredDocumentTag sdt2 = (StructuredDocumentTag)sdts[1];
        Paragraph paragraph3 = (Paragraph)sdt2.getChildNodes(NodeType.ANY, false).get(2);
        msAssert.areEqual(2, paragraph3.getListLabel().getLabelValue());

        // Check that no new lists are created during updating the SDTs.
        msAssert.areEqual(listCount, doc.getLists().getCount());
        msAssert.areEqual(listDefCount, doc.getLists().getListDefCount());

        // Checks that duplicate DurableId values are removed.
        List newList = doc.getLists().addCopy(doc.getLists().get(doc.getLists().getCount() - 1));
        msAssert.areNotEqual(0, newList.getDurableId());
        TestUtil.executeValidator(doc, SaveFormat.DOCX);
        msAssert.areEqual(0, newList.getDurableId());
    }

    /// <summary>
    /// WORDSNET-24169 Wrong list numbering in SDT bound to custom XML part
    /// When importing nodes, Aspose.Words does not use an existing list at some cases but creates a new one.
    /// Implemented that when <see cref="ImportFormatOptions"/> is not set to <see cref="NodeImporter"/>, an
    /// existing list is used when importing nodes.
    /// </summary>
    @Test
    public void test24169() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test24169.docx");
        CompositeNode sdt = (CompositeNode)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);

        Document destinationDocument = new Document();
        destinationDocument.copyStylesFromTemplate(doc);
        destinationDocument.getFirstSection().getBody().removeAllChildren();
        int listDefCount = destinationDocument.getLists().getListDefCount();

        for (Node node : (Iterable<Node>) sdt.getChildNodes(NodeType.ANY, false))
        {
            Node importNode = destinationDocument.importNode(node, true, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            destinationDocument.getLastSection().getBody().appendChild(importNode);
        }

        // Check that paragraph list numbers are correct.
        ListLabelUpdater.updateListLabels(destinationDocument);
        ParagraphCollection paragraphs = destinationDocument.getFirstSection().getBody().getParagraphs();
        Paragraph paragraph2 = paragraphs.get(1);
        Paragraph paragraph3 = paragraphs.get(2);
        msAssert.areEqual(1, paragraph2.getListLabel().getLabelValue());
        msAssert.areEqual(2, paragraph3.getListLabel().getLabelValue());

        // Check that lists of the numbering items have the same list definition.
        msAssert.isFalse(paragraph2.getListFormat().getList().getListId() == paragraph3.getListFormat().getList().getListId());
        msAssert.isTrue(paragraph2.getListFormat().getList().getListDefId() == paragraph3.getListFormat().getList().getListDefId());

        // Check that no new list definitions are created during importing paragraphs.
        msAssert.areEqual(listDefCount, destinationDocument.getLists().getListDefCount());
    }

    /// <summary>
    /// WORDSNET-24444 List list labels are incorrect after rendering document.
    /// The Word does not increment the counter for a missing in the content levels (when level has increased by more
    /// than one) in the case when previously occurred level in the content between "lvlRestart" and current levels.
    /// Mimic the Word to fix the issue.
    /// </summary>
    @Test
    public void test24444() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test24444.docx");
        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        final String LVL_7_LABEL = "1 1 {0} 1 a i 0 1";
        final String LVL_6_LABEL = "1 1 {0} 1 a i 1";

        msAssert.areEqual(msString.format(LVL_7_LABEL, 1), listLabel(paras, 2));
        msAssert.areEqual(msString.format(LVL_6_LABEL, 1), listLabel(paras, 3));
        msAssert.areEqual(msString.format(LVL_7_LABEL, 2), listLabel(paras, 8));
        msAssert.areEqual(msString.format(LVL_6_LABEL, 2), listLabel(paras, 9));
        msAssert.areEqual(msString.format(LVL_7_LABEL, 3), listLabel(paras, 14));
        msAssert.areEqual(msString.format(LVL_6_LABEL, 3), listLabel(paras, 15));
        msAssert.areEqual(msString.format(LVL_7_LABEL, 4), listLabel(paras, 20));
        msAssert.areEqual(msString.format(LVL_6_LABEL, 4), listLabel(paras, 21));

        msAssert.areEqual("1 1 5 1 a i 1 1", listLabel(paras, 25));
        msAssert.areEqual("1 1 5 1 a i 2", listLabel(paras, 26));
    }



    /// <summary>
    /// WORDSNET-26909 Invalid numbering culture in docx-to-PDF conversion.
    /// The ganada (Korean Ganada Numbering) is not implemented in <see cref="NumberConverterCore.NumberToString"/>.
    /// </summary>
    @Test
    public void test26909() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test26909.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("가.", listLabel(paras, 0));
        msAssert.areEqual("나.", listLabel(paras, 1));
        msAssert.areEqual("다.", listLabel(paras, 2));
        msAssert.areEqual("라.", listLabel(paras, 3));
        msAssert.areEqual("마.", listLabel(paras, 4));
        msAssert.areEqual("바.", listLabel(paras, 5));
        msAssert.areEqual("사.", listLabel(paras, 6));
        msAssert.areEqual("아.", listLabel(paras, 7));
        msAssert.areEqual("자.", listLabel(paras, 8));
        msAssert.areEqual("차.", listLabel(paras, 9));
        msAssert.areEqual("카.", listLabel(paras, 10));
        msAssert.areEqual("타.", listLabel(paras, 11));
        msAssert.areEqual("파.", listLabel(paras, 12));
        msAssert.areEqual("하.", listLabel(paras, 13));

        // Only the first 14 numbers are present in the Ganada and the rest are repeated in a loop.
        msAssert.areEqual("가.", listLabel(paras, 14));
        msAssert.areEqual("나.", listLabel(paras, 15));
        msAssert.areEqual("다.", listLabel(paras, 16));
        msAssert.areEqual("라.", listLabel(paras, 17));
        msAssert.areEqual("마.", listLabel(paras, 18));
        msAssert.areEqual("바.", listLabel(paras, 19));
        msAssert.areEqual("사.", listLabel(paras, 20));
        msAssert.areEqual("아.", listLabel(paras, 21));
        msAssert.areEqual("자.", listLabel(paras, 22));
        msAssert.areEqual("차.", listLabel(paras, 23));
        msAssert.areEqual("카.", listLabel(paras, 24));
        msAssert.areEqual("타.", listLabel(paras, 25));
        msAssert.areEqual("파.", listLabel(paras, 26));
        msAssert.areEqual("하.", listLabel(paras, 27));

        msAssert.areEqual("가.", listLabel(paras, 28));
        msAssert.areEqual("나.", listLabel(paras, 29));
        msAssert.areEqual("다.", listLabel(paras, 30));
        msAssert.areEqual("라.", listLabel(paras, 31));
        msAssert.areEqual("마.", listLabel(paras, 32));
        msAssert.areEqual("바.", listLabel(paras, 33));
        msAssert.areEqual("사.", listLabel(paras, 34));

        // Check 0 fallbacks to arabic representation.
        msAssert.areEqual("0.", listLabel(paras, 35));
    }

    /// <summary>
    /// Added support for <see cref="NumberStyle.Custom"/> numbering style.
    /// Tests ListLevel.CustomNumberStyle property setter.
    /// </summary>
    @Test
    public void testCustomNumberStyleFormatSetter() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestCustomLeadingZero4.docx");

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        doc.updateListLabels();

        msAssert.areEqual("00001.", listLabel(paras, 1));
        msAssert.areEqual("00002.", listLabel(paras, 2));

        // Change and check again.
        paras.get(1).getListFormat().getListLevel().setCustomNumberStyleFormat("001, 002, 003, ...");

        doc.updateListLabels();

        msAssert.areEqual("001.", listLabel(paras, 1));
        msAssert.areEqual("002.", listLabel(paras, 2));

        // Check roundtrip.
        doc = TestUtil.saveOpen(doc, "Model\\List\\TestCustomNumberStyleFormatSetter.docx", new OoxmlSaveOptions(), false);
        msAssert.areEqual("001, 002, 003, ...", doc.getFirstSection().getBody().getParagraphs().get(1).getListFormat().getListLevel().getCustomNumberStyleFormat());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testCustomNumberStyleFormatSetterNull() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\TestCustomLeadingZero4.docx");
        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // Try set null.
        paras.get(1).getListFormat().getListLevel().setCustomNumberStyleFormat(null);
    }

    /// <summary>
    /// WORDSNET-27187 Chinese becomes Arabic numbering after conversion to HTML.
    /// The koreanDigital2 numbering format is not implemented in <see cref="NumberConverterCore.NumberToString"/>.
    /// </summary>
    @Test
    public void test27187() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test27187.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // 0 - 21
        msAssert.areEqual("零.", listLabel(paras, 0));
        msAssert.areEqual("一.", listLabel(paras, 1));
        msAssert.areEqual("二.", listLabel(paras, 2));
        msAssert.areEqual("三.", listLabel(paras, 3));
        msAssert.areEqual("四.", listLabel(paras, 4));
        msAssert.areEqual("五.", listLabel(paras, 5));
        msAssert.areEqual("六.", listLabel(paras, 6));
        msAssert.areEqual("七.", listLabel(paras, 7));
        msAssert.areEqual("八.", listLabel(paras, 8));
        msAssert.areEqual("九.", listLabel(paras, 9));
        msAssert.areEqual("一零.", listLabel(paras, 10));
        msAssert.areEqual("一一.", listLabel(paras, 11));
        msAssert.areEqual("一二.", listLabel(paras, 12));
        msAssert.areEqual("一三.", listLabel(paras, 13));
        msAssert.areEqual("一四.", listLabel(paras, 14));
        msAssert.areEqual("一五.", listLabel(paras, 15));
        msAssert.areEqual("一六.", listLabel(paras, 16));
        msAssert.areEqual("一七.", listLabel(paras, 17));
        msAssert.areEqual("一八.", listLabel(paras, 18));
        msAssert.areEqual("一九.", listLabel(paras, 19));
        msAssert.areEqual("二零.", listLabel(paras, 20));
        msAssert.areEqual("二一.", listLabel(paras, 21));

        msAssert.areEqual("九九.", listLabel(paras, 22)); //99
        msAssert.areEqual("一零零.", listLabel(paras, 23)); // 100

        msAssert.areEqual("一零零零.", listLabel(paras, 24)); // 1000
        msAssert.areEqual("一零零一.", listLabel(paras, 25)); // 1001
        msAssert.areEqual("一零零二.", listLabel(paras, 26)); // 1002
    }

    /// <summary>
    /// WORDSNET-27573 Korean Numbering is changed to Arabic after rendering.
    /// The chosung (Korean Chosung Numbering) is not implemented in <see cref="NumberConverterCore.NumberToString"/>.
    /// </summary>
    @Test
    public void test27573Chosung() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test27573Chosung.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        msAssert.areEqual("ㄱ.", listLabel(paras, 0));
        msAssert.areEqual("ㄴ.", listLabel(paras, 1));
        msAssert.areEqual("ㄷ.", listLabel(paras, 2));
        msAssert.areEqual("ㄹ.", listLabel(paras, 3));
        msAssert.areEqual("ㅁ.", listLabel(paras, 4));
        msAssert.areEqual("ㅂ.", listLabel(paras, 5));
        msAssert.areEqual("ㅅ.", listLabel(paras, 6));
        msAssert.areEqual("ㅇ.", listLabel(paras, 7));
        msAssert.areEqual("ㅈ.", listLabel(paras, 8));
        msAssert.areEqual("ㅊ.", listLabel(paras, 9));
        msAssert.areEqual("ㅋ.", listLabel(paras, 10));
        msAssert.areEqual("ㅌ.", listLabel(paras, 11));
        msAssert.areEqual("ㅍ.", listLabel(paras, 12));
        msAssert.areEqual("ㅎ.", listLabel(paras, 13));

        // Only the first 14 numbers are present in the Chosung and the rest are repeated in a loop.
        msAssert.areEqual("ㄱ.", listLabel(paras, 14));
        msAssert.areEqual("ㄴ.", listLabel(paras, 15));
        msAssert.areEqual("ㄷ.", listLabel(paras, 16));
        msAssert.areEqual("ㄹ.", listLabel(paras, 17));
        msAssert.areEqual("ㅁ.", listLabel(paras, 18));
        msAssert.areEqual("ㅂ.", listLabel(paras, 19));
        msAssert.areEqual("ㅅ.", listLabel(paras, 20));
        msAssert.areEqual("ㅇ.", listLabel(paras, 21));
        msAssert.areEqual("ㅈ.", listLabel(paras, 22));
        msAssert.areEqual("ㅊ.", listLabel(paras, 23));
        msAssert.areEqual("ㅋ.", listLabel(paras, 24));
        msAssert.areEqual("ㅌ.", listLabel(paras, 25));
        msAssert.areEqual("ㅍ.", listLabel(paras, 26));
        msAssert.areEqual("ㅎ.", listLabel(paras, 27));

        msAssert.areEqual("ㄱ.", listLabel(paras, 28));
        msAssert.areEqual("ㄴ.", listLabel(paras, 29));
        msAssert.areEqual("ㄷ.", listLabel(paras, 30));
        msAssert.areEqual("ㄹ.", listLabel(paras, 31));
        msAssert.areEqual("ㅁ.", listLabel(paras, 32));
        msAssert.areEqual("ㅂ.", listLabel(paras, 33));
        msAssert.areEqual("ㅅ.", listLabel(paras, 34));
    }


    /// <summary>
    /// WORDSNET-28954 Numeral format is changed after rendering document.
    /// The taiwaneseCounting (Taiwanese Counting System) is not implemented in <see cref="NumberConverterCore.NumberToString"/>.
    /// </summary>
    @Test
    public void test28954() throws Exception
    {
        Document doc = TestUtil.open("Model\\List\\Test28954taiwaneseCounting.docx");

        doc.updateListLabels();

        ParagraphCollection paras = doc.getFirstSection().getBody().getParagraphs();

        // 1-9
        msAssert.areEqual("一.", listLabel(paras, 0));
        msAssert.areEqual("二.", listLabel(paras, 1));
        msAssert.areEqual("三.", listLabel(paras, 2));
        msAssert.areEqual("四.", listLabel(paras, 3));
        msAssert.areEqual("五.", listLabel(paras, 4));
        msAssert.areEqual("六.", listLabel(paras, 5));
        msAssert.areEqual("七.", listLabel(paras, 6));
        msAssert.areEqual("八.", listLabel(paras, 7));
        msAssert.areEqual("九.", listLabel(paras, 8));

        // 10-15
        msAssert.areEqual("十.", listLabel(paras, 9));
        msAssert.areEqual("十一.", listLabel(paras, 10));
        msAssert.areEqual("十二.", listLabel(paras, 11));
        msAssert.areEqual("十三.", listLabel(paras, 12));
        msAssert.areEqual("十四.", listLabel(paras, 13));
        msAssert.areEqual("十五.", listLabel(paras, 14));

        // 20-22
        msAssert.areEqual("二十.", listLabel(paras, 15));
        msAssert.areEqual("二十一.", listLabel(paras, 16));
        msAssert.areEqual("二十二.", listLabel(paras, 17));

        // 30-31
        msAssert.areEqual("三十.", listLabel(paras, 18));
        msAssert.areEqual("三十一.", listLabel(paras, 19));

        // 40
        msAssert.areEqual("四十.", listLabel(paras, 20));
        // 50
        msAssert.areEqual("五十.", listLabel(paras, 21));
        // 60
        msAssert.areEqual("六十.", listLabel(paras, 22));
        // 70
        msAssert.areEqual("七十.", listLabel(paras, 23));
        // 80
        msAssert.areEqual("八十.", listLabel(paras, 24));
        // 90
        msAssert.areEqual("九十.", listLabel(paras, 25));

        // 99-112
        msAssert.areEqual("九十九.", listLabel(paras, 26));
        msAssert.areEqual("一○○.", listLabel(paras, 27));
        msAssert.areEqual("一○一.", listLabel(paras, 28));
        msAssert.areEqual("一○二.", listLabel(paras, 29));
        msAssert.areEqual("一○三.", listLabel(paras, 30));
        msAssert.areEqual("一○四.", listLabel(paras, 31));
        msAssert.areEqual("一○五.", listLabel(paras, 32));
        msAssert.areEqual("一○六.", listLabel(paras, 33));
        msAssert.areEqual("一○七.", listLabel(paras, 34));
        msAssert.areEqual("一○八.", listLabel(paras, 35));
        msAssert.areEqual("一○九.", listLabel(paras, 36));
        msAssert.areEqual("一一○.", listLabel(paras, 37));
        msAssert.areEqual("一一一.", listLabel(paras, 38));
        msAssert.areEqual("一一二.", listLabel(paras, 39));

        // 120-121
        msAssert.areEqual("一二○.", listLabel(paras, 40));
        msAssert.areEqual("一二一.", listLabel(paras, 41));

        // 200-202
        msAssert.areEqual("二○○.", listLabel(paras, 42));
        msAssert.areEqual("二○一.", listLabel(paras, 43));
        msAssert.areEqual("二○二.", listLabel(paras, 44));

        // 999-1001
        msAssert.areEqual("九九九.", listLabel(paras, 45));
        msAssert.areEqual("一○○○.", listLabel(paras, 46));
        msAssert.areEqual("一○○一.", listLabel(paras, 47));

        // 2000
        msAssert.areEqual("二○○○.", listLabel(paras, 48));

        // 10,000-10,001
        msAssert.areEqual("一○○○○.", listLabel(paras, 49));
        msAssert.areEqual("一○○○一.", listLabel(paras, 50));

        // 11,111
        msAssert.areEqual("一一一一一.", listLabel(paras, 51));

        // 32767
        msAssert.areEqual("三二七六七.", listLabel(paras, 52));
    }

    private static void checkListRevision(Paragraph p, int originalListId, int finalListId, String originalLabel, String finalLabel)
    {
        msAssert.areEqual(originalListId, p.fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.ORIGINAL));
        msAssert.areEqual(finalListId, p.fetchParaAttr(ParaAttr.LIST_ID, RevisionsView.FINAL));

        checkListRevision(p, originalLabel, finalLabel);
    }

    private static void checkListRevision(Paragraph p, String originalLabel, String finalLabel)
    {
        msAssert.areEqual(originalLabel, p.getListLabel().getLabelStringOriginal());
        msAssert.areEqual(finalLabel, p.getListLabel().getLabelStringFinal());
    }

    /// <summary>
    /// Returns list label for the paragraph with the specified index from the collection.
    /// </summary>
    private static String listLabel(ParagraphCollection paras, int paraIndex)
    {
        return paras.get(paraIndex).getListLabel().getLabelString();
    }
}

