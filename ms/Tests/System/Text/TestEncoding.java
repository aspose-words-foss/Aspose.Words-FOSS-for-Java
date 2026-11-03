// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/12/2006 by Konstantin Sidorenko
// 2016/04/18 by Anatoliy Sidorenko


package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Text.Encoding;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.ms.System.Text.Decoder;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


@Test
public class TestEncoding
{
    //>>>>>>>>>>>>>>>>>>>>>>>> Encoder

    /// <summary>
    /// "Convenience method" java.nio.charset.CharsetDecoder.decode(ByteBuffer in) can't properly decode
    /// single one-byte symbol of multibyte encodings(like Shift-Jis) - it just returns an empty char array.
    /// FIXED.Hand made:) "convenience method" used instead for workaround.
    /// </summary>
    @Test
    public void testOneByteSymbolOfMultiByteEncoding()
    {
        Encoding encoding = Encoding.getEncoding("shift_jis");

        byte[] bytes = { (byte) 58 };
        char[] chars = encoding.getChars(bytes);
        msAssert.areEqual(':', chars[0]);
    }

    @Test
    public void testChinese()
    {
        //the string contains both simplified and traditional chinese signs.
        String inp = "华东哗 鸃 ";

        //JAVA: windows wrongly calls 936 codepage as GB2312 - really it is extended GB2312 - GBK
        //with minor MS addition (euro sign).
        //GB2312 accepts only simplified chinese, GBK supersets GB2312 with traditional chinese.
        Encoding encoding = Encoding.getEncoding("gb2312");
        byte[] bytes = encoding.getBytes(inp);
        String outp = encoding.getString(bytes);
        msAssert.areEqual(inp, outp);

        //GB18030 is the last official chinese encoding. support both simplified and traditional signs.
        encoding = Encoding.getEncoding("GB18030");
        bytes = encoding.getBytes(inp);
        outp = encoding.getString(bytes);
        msAssert.areEqual(inp, outp);

        //Unicode can handle chinese as well.
        encoding = Encoding.getUnicode();
        bytes = encoding.getBytes(inp);
        outp = encoding.getString(bytes);
        msAssert.areEqual(inp, outp);
    }

    /// <summary>
    /// IDEA 8.0 changes the mean of Default File Encoding configuration param.
    ///
    /// In earler versions we get IDEA Default encoding (utf-8) by Encoding.getDefault() (uses Charset.defaultCharset() internally).
    /// But since IDEA 8.0 we get the system encoding (windows-1252) in the same case.
    ///
    /// As a result some tests can't roundtrip text strings since them encoded and decoded by different encodings.
    /// For instance: TestCustomers.TestJatApostrophe() (old version, changed now) - doc.save(txt) encodes strings
    /// using utf-8, but stream.toString() under IDEA 8 decodes bytes to string using system encoding windows-1252.
    /// </summary>
    @Test
    public void testApostrophe()
    {
        String inp = "\u2019";
        Encoding encoding = Encoding.getEncoding("utf-8");

        byte[] bytes = encoding.getBytes(inp);
        String outp = encoding.getString(bytes);
        msAssert.areEqual(inp, outp);

        //IDEA 8: default encoding is windows-1252 (system default).
        //IDEA 7 and earler: default encoding is IDEA default encoding (utf-8).
        Encoding defaultEncoging = Encoding.getDefault();
        Debug.writeLine("defaultEncoging = " + defaultEncoging);

        //under IDEA 8 some old tests can't roundtrip text strings since them encoded and decoded by different encodings.
        encoding = Encoding.getEncoding("Windows-1252");
        outp = encoding.getString(bytes);
        msAssert.isFalse(inp.equals(outp));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testDefaultEncoding()
    {
        msAssert.areEqual(Encoding.getEncoding(-1).getCodePage(), Encoding.getUnicode().getCodePage());
    }

    @Test
    public void testDefaultEncoding2()
    {
        msAssert.areEqual(Encoding.getEncoding(0).getCodePage(), Encoding.getUnicode().getCodePage());
    }

    @Test
    public void testSomeEncodings()
    {
        msAssert.areEqual(Encoding.getEncoding(1200).getHeaderName(), Encoding.getUnicode().getHeaderName());

        msAssert.areEqual(Encoding.getEncoding(20932).getHeaderName(), "EUC-JP");
        msAssert.areEqual(Encoding.getEncoding(51932).getHeaderName(), "euc-jp");

        msAssert.areEqual(Encoding.getEncoding("euc-jp").getHeaderName(), "euc-jp");
        msAssert.areEqual(Encoding.getEncoding("EUC-JP").getHeaderName(), "euc-jp");

        msAssert.areEqual(Encoding.getEncoding(65001).getHeaderName(), Encoding.getEncoding("utf-8").getHeaderName());
        msAssert.areEqual(Encoding.getEncoding(28595).getHeaderName(), Encoding.getEncoding("iso-8859-5").getHeaderName());
        msAssert.areEqual(Encoding.getEncoding(20866).getHeaderName(), Encoding.getEncoding("koi8-r").getHeaderName());
        msAssert.areEqual(Encoding.getEncoding(1251).getHeaderName(), Encoding.getEncoding("Windows-1251").getHeaderName());
        msAssert.areEqual(Encoding.getEncoding(950).getHeaderName(), Encoding.getEncoding("big5").getHeaderName());
        msAssert.areEqual(Encoding.getEncoding(874).getHeaderName(), Encoding.getEncoding("windows-874").getHeaderName());
    }

    @Test (dataProvider = "testAllEncodingsDataProvider")
    public void testAllEncodings(int codePage, String name)
    {
        msAssert.areEqual(Encoding.getEncoding(codePage).getHeaderName(), Encoding.getEncoding(name).getHeaderName());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testAllEncodingsDataProvider")
	public static Object[][] testAllEncodingsDataProvider()
	{
		return new Object[][]
		{
			{37,  "IBM037"},
			{437,  "IBM437"},
			{500,  "IBM500"},
			{708,  "ASMO-708"},
			{720,  "DOS-720"},
			{737,  "ibm737"},
			{775,  "ibm775"},
			{850,  "ibm850"},
			{852,  "ibm852"},
			{855,  "IBM855"},
			{857,  "ibm857"},
			{858,  "IBM00858"},
			{860,  "IBM860"},
			{861,  "ibm861"},
			{862,  "DOS-862"},
			{863,  "IBM863"},
			{864,  "IBM864"},
			{865,  "IBM865"},
			{866,  "cp866"},
			{869,  "ibm869"},
			{870,  "IBM870"},
			{874,  "windows-874"},
			{875,  "cp875"},
			{932,  "shift_jis"},
			{936,  "gb2312"},
			{949,  "ks_c_5601-1987"},
			{950,  "big5"},
			{1026,  "IBM1026"},
			{1047,  "IBM01047"},
			{1140,  "IBM01140"},
			{1141,  "IBM01141"},
			{1142,  "IBM01142"},
			{1143,  "IBM01143"},
			{1144,  "IBM01144"},
			{1145,  "IBM01145"},
			{1146,  "IBM01146"},
			{1147,  "IBM01147"},
			{1148,  "IBM01148"},
			{1149,  "IBM01149"},
			{1200,  "utf-16"},
			{1201,  "utf-16BE"},
			{1250,  "windows-1250"},
			{1251,  "windows-1251"},
			{1252,  "Windows-1252"},
			{1253,  "windows-1253"},
			{1254,  "windows-1254"},
			{1255,  "windows-1255"},
			{1256,  "windows-1256"},
			{1257,  "windows-1257"},
			{1258,  "windows-1258"},
			{1361,  "Johab"},
			{10000,  "macintosh"},
			{10001,  "x-mac-japanese"},
			{10002,  "x-mac-chinesetrad"},
			{10003,  "x-mac-korean"},
			{10004,  "x-mac-arabic"},
			{10005,  "x-mac-hebrew"},
			{10006,  "x-mac-greek"},
			{10007,  "x-mac-cyrillic"},
			{10008,  "x-mac-chinesesimp"},
			{10010,  "x-mac-romanian"},
			{10017,  "x-mac-ukrainian"},
			{10021,  "x-mac-thai"},
			{10029,  "x-mac-ce"},
			{10079,  "x-mac-icelandic"},
			{10081,  "x-mac-turkish"},
			{10082,  "x-mac-croatian"},
			{12000,  "utf-32"},
			{12001,  "utf-32BE"},
			{20000,  "x-Chinese-CNS"},
			{20001,  "x-cp20001"},
			{20002,  "x-Chinese-Eten"},
			{20003,  "x-cp20003"},
			{20004,  "x-cp20004"},
			{20005,  "x-cp20005"},
			{20105,  "x-IA5"},
			{20106,  "x-IA5-German"},
			{20107,  "x-IA5-Swedish"},
			{20108,  "x-IA5-Norwegian"},
			{20127,  "us-ascii"},
			{20261,  "x-cp20261"},
			{20269,  "x-cp20269"},
			{20273,  "IBM273"},
			{20277,  "IBM277"},
			{20278,  "IBM278"},
			{20280,  "IBM280"},
			{20284,  "IBM284"},
			{20285,  "IBM285"},
			{20290,  "IBM290"},
			{20297,  "IBM297"},
			{20420,  "IBM420"},
			{20423,  "IBM423"},
			{20424,  "IBM424"},
			{20833,  "x-EBCDIC-KoreanExtended"},
			{20838,  "IBM-Thai"},
			{20866,  "koi8-r"},
			{20871,  "IBM871"},
			{20880,  "IBM880"},
			{20905,  "IBM905"},
			{20924,  "IBM00924"},
			{20936,  "x-cp20936"},
			{20949,  "x-cp20949"},
			{21025,  "cp1025"},
			{21866,  "koi8-u"},
			{28591,  "iso-8859-1"},
			{28592,  "iso-8859-2"},
			{28593,  "iso-8859-3"},
			{28594,  "iso-8859-4"},
			{28595,  "iso-8859-5"},
			{28596,  "iso-8859-6"},
			{28597,  "iso-8859-7"},
			{28598,  "iso-8859-8"},
			{28599,  "iso-8859-9"},
			{28603,  "iso-8859-13"},
			{28605,  "iso-8859-15"},
			{29001,  "x-Europa"},
			{38598,  "iso-8859-8-i"},
			{50220,  "iso-2022-jp"},
			{50221,  "csISO2022JP"},
			{50222,  "iso-2022-jp"},
			{50225,  "iso-2022-kr"},
			{50227,  "x-cp50227"},
			{51932,  "euc-jp"},
			{51936,  "EUC-CN"},
			{51949,  "euc-kr"},
			{52936,  "hz-gb-2312"},
			{54936,  "GB18030"},
			{57002,  "x-iscii-de"},
			{57003,  "x-iscii-be"},
			{57004,  "x-iscii-ta"},
			{57005,  "x-iscii-te"},
			{57006,  "x-iscii-as"},
			{57007,  "x-iscii-or"},
			{57008,  "x-iscii-ka"},
			{57009,  "x-iscii-ma"},
			{57010,  "x-iscii-gu"},
			{57011,  "x-iscii-pa"},
			{65000,  "utf-7"},
			{65001,  "utf-8"},
		};
	}

    @Test
    public void testUtf7()
    {
        Encoding utf7 = Encoding.getEncoding("utf-7");

        // This just throws if can't get the charset. This can happen if our utf-7 is not properly registered.
        utf7.getDecoder();

        msAssert.areEqual(Encoding.getEncoding(65000).getHeaderName(), Encoding.getEncoding("utf-7").getHeaderName());
    }

    @Test
    public void testGetBytes()
    {
        String testStr = "Test String Тестовая строчка 123 /n.";

        byte[] unicodeBytes =
        {
            (byte) 84, (byte) 0, (byte) 101, (byte) 0, (byte) 115, (byte) 0, (byte) 116, (byte) 0, (byte) 32, (byte) 0, (byte) 83, (byte) 0, (byte) 116, (byte) 0, (byte) 114, (byte) 0, (byte) 105, (byte) 0, (byte) 110, (byte) 0, (byte) 103, (byte) 0,
            (byte) 32, (byte) 0, (byte) 34, (byte) 4, (byte) 53, (byte) 4, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 62, (byte) 4, (byte) 50, (byte) 4, (byte) 48, (byte) 4, (byte) 79, (byte) 4, (byte) 32, (byte) 0, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 64, (byte) 4, (byte) 62, (byte) 4, (byte) 71, (byte) 4, (byte) 58, (byte) 4,
            (byte) 48, (byte) 4, (byte) 32, (byte) 0, (byte) 49, (byte) 0, (byte) 50, (byte) 0, (byte) 51, (byte) 0, (byte) 32, (byte) 0, (byte) 47, (byte) 0, (byte) 110, (byte) 0, (byte) 46, (byte) 0
        };

        byte[] win1252Bytes =
        {
            (byte) 84, (byte) 101, (byte) 115, (byte) 116, (byte) 32, (byte) 83, (byte) 116, (byte) 114, (byte) 105, (byte) 110, (byte) 103, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63,
            (byte) 63, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 32, (byte) 49, (byte) 50, (byte) 51, (byte) 32, (byte) 47, (byte) 110, (byte) 46
        };

        byte[] bytes = Encoding.getUnicode().getBytes(testStr);
        msAssert.areEqual(bytes, unicodeBytes);

        bytes = Encoding.getEncoding("Windows-1252").getBytes(testStr);
        msAssert.areEqual(bytes, win1252Bytes);
    }

    @Test
    public void testGetBytesFromCharsIndexed()
    {
        String testStr = "Test string Тестовая строчка 123 /n.";
        char[] chars = testStr.toCharArray();

        byte[] unicodeBytes =
        {
            (byte) 84, (byte) 0, (byte) 101, (byte) 0, (byte) 115, (byte) 0, (byte) 116, (byte) 0, (byte) 32, (byte) 0, (byte) 83, (byte) 0, (byte) 116, (byte) 0, (byte) 114, (byte) 0, (byte) 105, (byte) 0, (byte) 110, (byte) 0, (byte) 103, (byte) 0,
            (byte) 32, (byte) 0, (byte) 34, (byte) 4, (byte) 53, (byte) 4, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 62, (byte) 4, (byte) 50, (byte) 4, (byte) 48, (byte) 4, (byte) 79, (byte) 4, (byte) 32, (byte) 0, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 64, (byte) 4, (byte) 62, (byte) 4, (byte) 71, (byte) 4, (byte) 58, (byte) 4,
            (byte) 48, (byte) 4, (byte) 32, (byte) 0, (byte) 49, (byte) 0, (byte) 50, (byte) 0, (byte) 51, (byte) 0, (byte) 32, (byte) 0, (byte) 47, (byte) 0, (byte) 110, (byte) 0, (byte) 46, (byte) 0
        };

        byte[] bytes = Encoding.getUnicode().getBytes(chars, 0, 1);
        bytes = Encoding.getUnicode().getBytes(chars, chars.length - 1, 1);
        bytes = Encoding.getUnicode().getBytes(chars, 10, 1);
        bytes = Encoding.getUnicode().getBytes(chars, 10, 2);
        bytes = Encoding.getUnicode().getBytes(chars, 10, 3);
    }

    @Test
    public void testGetString()
    {
        String testStrUnicode = "Test String Тестовая строчка 123 /n.";
        String testStrWin1252 = "Test String ???????? ??????? 123 /n.";

        byte[] unicodeBytes =
        {
            (byte) 84, (byte) 0, (byte) 101, (byte) 0, (byte) 115, (byte) 0, (byte) 116, (byte) 0, (byte) 32, (byte) 0, (byte) 83, (byte) 0, (byte) 116, (byte) 0, (byte) 114, (byte) 0, (byte) 105, (byte) 0, (byte) 110, (byte) 0, (byte) 103, (byte) 0,
            (byte) 32, (byte) 0, (byte) 34, (byte) 4, (byte) 53, (byte) 4, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 62, (byte) 4, (byte) 50, (byte) 4, (byte) 48, (byte) 4, (byte) 79, (byte) 4, (byte) 32, (byte) 0, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 64, (byte) 4, (byte) 62, (byte) 4, (byte) 71, (byte) 4, (byte) 58, (byte) 4,
            (byte) 48, (byte) 4, (byte) 32, (byte) 0, (byte) 49, (byte) 0, (byte) 50, (byte) 0, (byte) 51, (byte) 0, (byte) 32, (byte) 0, (byte) 47, (byte) 0, (byte) 110, (byte) 0, (byte) 46, (byte) 0
        };

        byte[] win1252Bytes =
        {
            (byte) 84, (byte) 101, (byte) 115, (byte) 116, (byte) 32, (byte) 83, (byte) 116, (byte) 114, (byte) 105, (byte) 110, (byte) 103, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63,
            (byte) 63, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 32, (byte) 49, (byte) 50, (byte) 51, (byte) 32, (byte) 47, (byte) 110, (byte) 46
        };

        String gotten = Encoding.getUnicode().getString(unicodeBytes);
        msAssert.areEqual(gotten, testStrUnicode);

        gotten = Encoding.getEncoding("Windows-1252").
        getString(win1252Bytes);
        msAssert.areEqual(gotten, testStrWin1252);
    }

    @Test
    public void testGetChars()
    {
        String testStrUnicode = "Test String Тестовая строчка 123 /n.";
        String testStrWin1252 = "Test String ???????? ??????? 123 /n.";

        byte[] unicodeBytes =
        {
            (byte) 84, (byte) 0, (byte) 101, (byte) 0, (byte) 115, (byte) 0, (byte) 116, (byte) 0, (byte) 32, (byte) 0, (byte) 83, (byte) 0, (byte) 116, (byte) 0, (byte) 114, (byte) 0, (byte) 105, (byte) 0, (byte) 110, (byte) 0, (byte) 103, (byte) 0,
            (byte) 32, (byte) 0, (byte) 34, (byte) 4, (byte) 53, (byte) 4, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 62, (byte) 4, (byte) 50, (byte) 4, (byte) 48, (byte) 4, (byte) 79, (byte) 4, (byte) 32, (byte) 0, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 64, (byte) 4, (byte) 62, (byte) 4, (byte) 71, (byte) 4, (byte) 58, (byte) 4,
            (byte) 48, (byte) 4, (byte) 32, (byte) 0, (byte) 49, (byte) 0, (byte) 50, (byte) 0, (byte) 51, (byte) 0, (byte) 32, (byte) 0, (byte) 47, (byte) 0, (byte) 110, (byte) 0, (byte) 46, (byte) 0
        };

        byte[] win1252Bytes =
        {
            (byte) 84, (byte) 101, (byte) 115, (byte) 116, (byte) 32, (byte) 83, (byte) 116, (byte) 114, (byte) 105, (byte) 110, (byte) 103, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63,
            (byte) 63, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 32, (byte) 49, (byte) 50, (byte) 51, (byte) 32, (byte) 47, (byte) 110, (byte) 46
        };
        int[] count = { 10, 20, 72, 10, 10 };
        //UNICODE
        Encoding encoding = Encoding.getUnicode();
        msAssert.areEqual(encoding.getMaxCharCount(10), count[0]);
        msAssert.areEqual(encoding.getMaxByteCount(10), count[1]);

        int bytesConsumed = encoding.getMaxCharCount(unicodeBytes.length);
        msAssert.areEqual(bytesConsumed, count[2]);
        char[] chars = new char[bytesConsumed];
        chars = encoding.getChars(unicodeBytes);
        String strUnicode = new String(chars);
        msAssert.areEqual(strUnicode, testStrUnicode);

        //WINDOWS_1252
        encoding = Encoding.getEncoding("Windows-1252");
        msAssert.areEqual(encoding.getMaxCharCount(10), count[3]);
        msAssert.areEqual(encoding.getMaxByteCount(10), count[4]);

        bytesConsumed = encoding.getMaxCharCount(win1252Bytes.length);
        msAssert.areEqual(bytesConsumed, win1252Bytes.length);
        chars = encoding.getChars(win1252Bytes);
        String strWin1252 = new String(chars);
        msAssert.areEqual(strWin1252, testStrWin1252);
    }

    // Check that only one Encoding instance is created like in .NET.
    @Test
    public void testSingleInstance()
    {
        Encoding utf7 = Encoding.getEncoding("utf-7");
        msAssert.areSame(utf7, Encoding.getEncoding("utf-7"));
        msAssert.areSame(utf7, Encoding.getEncoding(65000));

        Encoding unicode = Encoding.getUnicode();
        msAssert.areSame(unicode, Encoding.getUnicode());
        msAssert.areSame(unicode, Encoding.getEncoding(1200));
    }

    @Test
    public void testGetPreamble()
    {
        // UTF 8
        msAssert.areEqual(3, Encoding.getEncoding("utf-8").getPreamble().length);
        msAssert.areEqual(3, new UTF8Encoding(true).getPreamble().length);
        msAssert.areEqual(0, new UTF8Encoding(false).getPreamble().length);

        // Unicode
        msAssert.areEqual(2, Encoding.getUnicode().getPreamble().length);
        msAssert.areEqual(2, Encoding.getBigEndianUnicode().getPreamble().length);
        msAssert.areEqual(4, Encoding.getUTF32().getPreamble().length);
        msAssert.areEqual(4, Encoding.getEncoding("utf-32be").getPreamble().length);

        // Other
        msAssert.areEqual(0, Encoding.getEncoding(1252).getPreamble().length);
    }

    @Test
    public void testJiraJ1459()
    {
        char[] chars = { (char)128, (char)129, (char)141, (char)142, (char)143, (char)144, (char)157, (char)158 };
        Encoding dstEncoding = Encoding.getEncoding(1252);
        byte[] actualBytes = dstEncoding.getBytes(chars);
        byte[] expectedBytes = { (byte)63, (byte)129, (byte)141, (byte)63, (byte)143, (byte)144, (byte)157, (byte)63 };
        // check that 128, 142 and 158 get replaced by '?', the rest stay the same
        msAssert.areEqual(expectedBytes, actualBytes);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>> Decoder

    @Test
    public void testDecoderGetChars() throws Exception
    {
        String testStrUnicode = "Test String Тестовая строчка 123 /n.";
        String testStrWin1252 = "Test String ???????? ??????? 123 /n.";

        byte[] unicodeBytes =
        {
            (byte) 84, (byte) 0, (byte) 101, (byte) 0, (byte) 115, (byte) 0, (byte) 116, (byte) 0, (byte) 32, (byte) 0, (byte) 83, (byte) 0, (byte) 116, (byte) 0, (byte) 114, (byte) 0, (byte) 105, (byte) 0, (byte) 110, (byte) 0, (byte) 103, (byte) 0,
            (byte) 32, (byte) 0, (byte) 34, (byte) 4, (byte) 53, (byte) 4, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 62, (byte) 4, (byte) 50, (byte) 4, (byte) 48, (byte) 4, (byte) 79, (byte) 4, (byte) 32, (byte) 0, (byte) 65, (byte) 4, (byte) 66, (byte) 4, (byte) 64, (byte) 4, (byte) 62, (byte) 4, (byte) 71, (byte) 4, (byte) 58, (byte) 4,
            (byte) 48, (byte) 4, (byte) 32, (byte) 0, (byte) 49, (byte) 0, (byte) 50, (byte) 0, (byte) 51, (byte) 0, (byte) 32, (byte) 0, (byte) 47, (byte) 0, (byte) 110, (byte) 0, (byte) 46, (byte) 0
        };

        byte[] win1252Bytes =
        {
            (byte) 84, (byte) 101, (byte) 115, (byte) 116, (byte) 32, (byte) 83, (byte) 116, (byte) 114, (byte) 105, (byte) 110, (byte) 103, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63,
            (byte) 63, (byte) 32, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 63, (byte) 32, (byte) 49, (byte) 50, (byte) 51, (byte) 32, (byte) 47, (byte) 110, (byte) 46
        };

        Decoder decoder = Encoding.getUnicode().getDecoder();
        char[] chars = new char[Encoding.getUnicode().getMaxCharCount(unicodeBytes.length)];
        decoder.getChars(unicodeBytes, 0, unicodeBytes.length, chars, 0);
        String unicodeStr = msString.trim(new String(chars), '\u0000');
        msAssert.areEqual(unicodeStr, testStrUnicode);

        decoder = Encoding.getEncoding("Windows-1252").getDecoder();
        chars = new char[Encoding.getDefault().getMaxCharCount(win1252Bytes.length)];
        decoder.getChars(win1252Bytes, 0, win1252Bytes.length, chars, 0);
        String wingStr = msString.trim(new String(chars));
        msAssert.areEqual(wingStr, testStrWin1252);
    }

    @Test
    public void testJiraJ1859()
    {
        byte[] bytes = Encoding.getEncoding(1252).getBytes("14â€ Encouraged");
        String s = new String(Encoding.getUTF8().getChars(bytes));

        msAssert.areEqual(s, "14” Encouraged");
    }

    @Test
    public void testJiraJ1967()
    {
        Encoding encoding = Encoding.getEncoding(1201);
        msAssert.areEqual("utf-16BE", encoding.getHeaderName());
        msAssert.areEqual(1201, encoding.getCodePage());
        msAssert.areEqual("utf-16BE", encoding.getWebName());
    }

    @Test
    public void testJava2326()
    {
        SystemPal.saveCulture();
        // JAVA-deleted SaveUICulture(): No analog in Java.
        try
        {
            SystemPal.setStandardCulture();
            // JAVA-deleted SetStandardUICulture(): No analog in Java.

            final String ENCODING_NAME = "IBM290";
        Encoding e = Encoding.fromJava(java.nio.charset.Charset.forName(ENCODING_NAME));
            msAssert.areEqual("IBM290", e.getHeaderName());
            msAssert.areEqual("IBM290", e.getWebName());
            msAssert.areEqual(0, e.getPreamble().length);

        }
        finally
        {
            SystemPal.restoreCulture();
            // JAVA-deleted RestoreUICulture(): No analog in Java.
        }
    }
}

