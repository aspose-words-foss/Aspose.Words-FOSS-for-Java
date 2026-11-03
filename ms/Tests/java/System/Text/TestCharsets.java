package com.aspose.ms.System.Text;

import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

/**
 * This class helps to make sure that Aspose supports all the charsets supported by Java. For around 100 of them we
 * override code page numbers and charset names for the .NET compatibility purposes. The rest must be supported
 * seamlessly because users can set any Java's charset via {@link Encoding#fromJava(java.nio.charset.Charset)}.
 * <p>
 * This class tests encode/decode methods to make sure they don't cause any exceptions in case non-overridden
 * encodings.
 *
 * @see com.aspose.words.LoadOptions
 */
public class TestCharsets
{

    /**
     * Tests that if an user set the encoding then the behaviour is the same as in Java
     *
     * @throws Exception
     */
    @Test
    public void testJavaCharsetsNotInterfereWithNetEncodings() throws Exception
    {
        final String testString = "A";
        for (Charset charset : Charset.availableCharsets().values())
        {
            try
            {
                // Users can choose any Java encoding and get the expected results
                Encoding createdFromJava = Encoding.fromJava(charset);
                msAssert.assertNotNull(createdFromJava);
                msAssert.assertTrue(createdFromJava.getCodePage() > Encoding.NET_CODE_PAGE_MAX);
                byte[] actualBytes = createdFromJava.getBytes(testString);
                byte[] javaBytes = testString.getBytes(charset.name());
                msAssert.areEqual(javaBytes, actualBytes);
            }
            catch (UnsupportedOperationException uoe)
            {
                if (!WITHOUT_ENCODE_SUPPORT.contains(charset))
                    throw uoe;
            }
        }
    }

    @Test(dataProvider = "userRequestedJavaCharsets")
    public void testUserRequestedCharsets(Charset charset)
    {
        String inputText = "123a";
        Encoding encoding = Encoding.getEncoding(charset.displayName());

        byte[] encodedViaAspose = encoding.getBytes(inputText);
        String decodedViaAspose = encoding.getString(encodedViaAspose);

        Assert.assertTrue(encodedViaAspose.length > 0);
        Assert.assertEquals(decodedViaAspose, "123a");
    }

    @Test(dataProvider = "allJavaCharsets")
    public void testAllJavaSupportedCharsets(Charset charset)
    {
        String charsetName = charset.displayName();
        int codePage = Encoding.getEncoding(charsetName).getCodePage();

        // Check if Aspose overrides the encoding data
        if (DOT_NET_COMPATIBLE_CHARSET_NAMES.contains(charsetName))
        {
            String message = String.format("Code page for .NET-compatible charset [%s] should be greater than zero " +
                    "but [%d] encountered.", charsetName, codePage);
            Assert.assertTrue(codePage > 0, message);
        }
        else if (CUSTOM_ENCODINGS.contains(charsetName))
            Assert.assertTrue(codePage > 0);

        else
        {
            // The rest encodings Java supports
            String message = String.format("Code page for Java-only charset [%s] should be greater than " + Encoding.NET_CODE_PAGE_MAX +
                    "but [%d] encountered.", charsetName, codePage);
            Assert.assertTrue(codePage > Encoding.NET_CODE_PAGE_MAX, message);
        }
    }

    @Test(dataProvider = "regularJavaCharsets")
    public void testEncodeToRegularCharset(Charset charset)
    {
        String inputText = "123";

        byte[] encodedViaJava = inputText.getBytes(charset);
        byte[] encodedViaAspose = Encoding.getEncoding(charset.displayName()).getBytes(inputText);

        Assert.assertEquals(encodedViaAspose, encodedViaJava);
    }

    @Test(dataProvider = "regularJavaCharsets")
    public void testDecodeFromRegularCharset(Charset charset)
    {
        String inputText = "123";

        Encoding encoding = Encoding.getEncoding(charset.displayName());
        byte[] encodedViaAspose = encoding.getBytes(inputText);
        String decodedViaAspose = encoding.getString(encodedViaAspose);

        Assert.assertEquals(decodedViaAspose, inputText);
    }

    @Test(dataProvider = "charsetsWithoutEncodeSupport")
    public void testDecodeWithoutEncodeSupport(Charset charset)
    {
        // We pass arbitrary bytes because we only need to ensure that Aspose decodes the input in the same way as pure
        // Java does. It doesn't matter here how Java actually performs inverse transformation of byte array.
        byte[] inputBytes = {11, 22, 33, 44};

        String decodedViaJava = charset.decode(ByteBuffer.wrap(inputBytes)).toString();
        String decodedViaAspose = Encoding.getEncoding(charset.displayName()).getString(inputBytes);

        Assert.assertEquals(decodedViaAspose, decodedViaJava);
    }

    @Test
    public void testUtf16()
    {
        String inputText = "123";
        Charset charset = Charset.forName("UTF-16");

        byte[] encodedViaJava = inputText.getBytes(charset);
        byte[] encodedViaAspose = Encoding.getEncoding(charset.displayName()).getBytes(inputText);

        byte[] littleEndianWithoutBomFromJava = new byte[inputText.length() * 2];
        for (int i = 0; i < littleEndianWithoutBomFromJava.length; i += 2)
        {
            littleEndianWithoutBomFromJava[i] = encodedViaJava[2 + i + 1];
            littleEndianWithoutBomFromJava[i + 1] = encodedViaJava[2 + i];
        }

        Assert.assertEquals(encodedViaAspose, littleEndianWithoutBomFromJava);
    }

    @Test
    public void testUtf32()
    {
        String inputText = "123";
        Charset charset = Charset.forName("UTF-32");

        byte[] encodedViaJava = inputText.getBytes(charset);
        byte[] encodedViaAspose = Encoding.getEncoding(charset.displayName()).getBytes(inputText);

        byte[] littleEndianFromJava = new byte[inputText.length() * 4];
        for (int i = 0; i < littleEndianFromJava.length; i += 4)
        {
            littleEndianFromJava[i] = encodedViaJava[i + 3];
            littleEndianFromJava[i + 1] = encodedViaJava[i + 2];
            littleEndianFromJava[i + 2] = encodedViaJava[i + 1];
            littleEndianFromJava[i + 3] = encodedViaJava[i];
        }

        Assert.assertEquals(encodedViaAspose, littleEndianFromJava);
    }

    @Test(dataProvider = "charsetsWithSpecificRange")
    public void testEncodeWithSpecificRange(Charset charset, String inputString)
    {
        byte[] encodedViaJava = inputString.getBytes(charset);
        byte[] encodedViaAspose = Encoding.getEncoding(charset.displayName()).getBytes(inputString);

        Assert.assertEquals(encodedViaAspose, encodedViaJava);
    }

    @Test(dataProvider = "charsetsWithSpecificRange")
    public void testDecodeWithSpecificRange(Charset charset, String inputString)
    {
        byte[] encodedViaAspose = Encoding.getEncoding(charset.displayName()).getBytes(inputString);

        Encoding encoding = Encoding.getEncoding(charset.displayName());
        String decodedViaAspose = encoding.getString(encodedViaAspose);

        Assert.assertEquals(decodedViaAspose, inputString);
    }

    @DataProvider(name = "regularJavaCharsets")
    public Object[][] regularJavaCharsets()
    {
        java.util.List<Charset> charsets = new ArrayList<>();
        for (Charset charset : Charset.availableCharsets().values())
        {
            if (WITHOUT_ENCODE_SUPPORT.contains(charset) ||
                    WITH_DIFFERENT_BYTE_ORDER.contains(charset) ||
                    WITH_SPECIFIC_CHARACTER_RANGE.contains(charset))
                continue;
            charsets.add(charset);
        }
        Object[][] parameters = new Object[charsets.size()][1];
        for (int i = 0; i < parameters.length; i++)
            parameters[i] = new Object[]{charsets.get(i)};

        return parameters;
    }

    @DataProvider(name = "charsetsWithoutEncodeSupport")
    public static Object[][] charsetsWithoutEncodeSupport()
    {
        Object[][] parameters = new Object[WITHOUT_ENCODE_SUPPORT.size()][1];
        Iterator<Charset> charsets = WITHOUT_ENCODE_SUPPORT.iterator();

        for (int i = 0; i < parameters.length; i++)
            parameters[i] = new Object[]{charsets.next()};

        return parameters;
    }

    @DataProvider(name = "allJavaCharsets")
    public static Object[][] allJavaCharsets()
    {
        Collection<Charset> allJavaCharsets = Charset.availableCharsets().values();
        Object[][] parameters = new Object[allJavaCharsets.size()][];
        Iterator<Charset> charsetsIterator = allJavaCharsets.iterator();
        for (int i = 0; i < parameters.length; i++)
            parameters[i] = new Object[]{charsetsIterator.next()};
        return parameters;
    }

    @DataProvider(name = "userRequestedJavaCharsets")
    public static Object[][] userRequestedJavaCharsets()
    {
        return new Object[][]{
                new Object[]{Charset.forName("Big5-HKSCS")},
                new Object[]{Charset.forName("CESU-8")},
                new Object[]{Charset.forName("IBM868")},
                new Object[]{Charset.forName("IBM290")},
                new Object[]{Charset.forName("IBM918")},
        };
    }

    @DataProvider(name = "charsetsWithSpecificRange")
    public Object[][] charsetsWithSpecificRange()
    {
        return new Object[][]{
                {Charset.forName("JIS_X0212-1990"), String.format("%c%c%c", 161, 164, 166)},
                {Charset.forName("x-IBM300"), String.format("%c%c%c", 167, 168, 176)},
                {Charset.forName("x-IBM834"), String.format("%c%c%c", 161, 164, 167)},
                {Charset.forName("x-JIS0208"), String.format("%c%c%c", 162, 163, 167)},
                {Charset.forName("x-MacDingbat"), String.format("%c%c%c", 8594, 8596, 8597)}
        };
    }

    private static final Set<Charset> WITHOUT_ENCODE_SUPPORT = new TreeSet<>();
    private static final Set<Charset> WITH_DIFFERENT_BYTE_ORDER = new TreeSet<>();
    private static final Set<Charset> WITH_SPECIFIC_CHARACTER_RANGE = new TreeSet<>();
    private static final Set<String> DOT_NET_COMPATIBLE_CHARSET_NAMES = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    private static final Set<String> CUSTOM_ENCODINGS = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    static
    {
        // A few of charsets in Java don't support encoding, e.g. throw an
        // exception when corresponding encoder is being requested. So we
        // test only decoding from such charsets.
        WITHOUT_ENCODE_SUPPORT.add(Charset.forName("ISO-2022-CN"));
        WITHOUT_ENCODE_SUPPORT.add(Charset.forName("x-JISAutoDetect"));
    }

    static
    {
        // Java decodes UTF-16 and UTF-32 as Big Endian. Even more,
        // in case of UTF-16 Java produces BOM while Aspose doesn't.
        // So we need to test those encodings independently from others.
        WITH_DIFFERENT_BYTE_ORDER.add(Charset.forName("UTF-16"));
        WITH_DIFFERENT_BYTE_ORDER.add(Charset.forName("UTF-32"));
    }

    static
    {
        // Some Java charsets don't support all characters but work only on
        // a specific range. For example, in case of JIS_X0212-1990 encoding
        // only characters starting from #161 can be encoded and decoded correctly.
        // This is why we cannot test those encodings on the same input string.
        WITH_SPECIFIC_CHARACTER_RANGE.add(Charset.forName("JIS_X0212-1990"));
        WITH_SPECIFIC_CHARACTER_RANGE.add(Charset.forName("x-IBM300"));
        WITH_SPECIFIC_CHARACTER_RANGE.add(Charset.forName("x-IBM834"));
        WITH_SPECIFIC_CHARACTER_RANGE.add(Charset.forName("x-JIS0208"));
        WITH_SPECIFIC_CHARACTER_RANGE.add(Charset.forName("x-MacDingbat"));
    }

    static
    {
        for (Encoding enc : new NetEncodingProvider().getAvailableEncodings())
            DOT_NET_COMPATIBLE_CHARSET_NAMES.add(enc.getEncodingInfoName());
    }

    static
    {
        CustomEncodingProvider customProvider = new CustomEncodingProvider();
        for (Encoding enc : customProvider.getAvailableEncodings())
            CUSTOM_ENCODINGS.add(enc.getEncodingInfoName());

        // The provider also answers to a few aliases, the Java name "UTF-16LE" among them: that charset is no Java-only
        // one, it is .NET code page 1200.
        CUSTOM_ENCODINGS.addAll(customProvider.defineAliasToCodePage().keySet());
    }
}