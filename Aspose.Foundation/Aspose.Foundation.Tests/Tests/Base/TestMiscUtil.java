// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Collections.SortedList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.SearchOption;
import org.testng.annotations.DataProvider;
import com.aspose.words.ref.Ref;


@Test
public class TestMiscUtil
{
    @Test
    public void testSortedListStringOrder()
    {

        // Default .NET sorted list is case sensitive, but it sorts strings using word comparison.
        // The sorted result actually looks like case-insensitive.
        // On the other hand, Java compares Unicode character values only and this results in
        // different sort order and differences in golds that we want to avoid.
        SortedList list = new SortedList();
        list.add("Marble", null);
        list.add("Apple 2", null);
        list.add("apple 1", null);
        list.add("license", null);

        checkCorrectSorting(list);


        // This part of the test verifies sorting that is the same across both platforms.
        // The solution is to use this comparer in .NET for case-sensitive sorted lists
        // when we want to achieve same sorted order as in java.
        SortedStringListGeneric<Object> list2 = new SortedStringListGeneric<Object>(true);
        list2.add("Marble", null);
        list2.add("Apple 2", null);
        list2.add("apple 1", null);
        list2.add("license", null);

        checkCorrectSorting(list2);
    }

    private static void checkCorrectSorting(SortedStringListGeneric<Object> list)
    {
        msAssert.areEqual("Apple 2", list.getKey(0));
        msAssert.areEqual("Marble", list.getKey(1));
        msAssert.areEqual("apple 1", list.getKey(2));
        msAssert.areEqual("license", list.getKey(3));
    }

    // Called only from the JAVA branch of the test above, so a .NET build sees no caller. Do not remove.
    private static void checkCorrectSorting(SortedList list)
    {
        msAssert.areEqual("Apple 2", list.getKey(0));
        msAssert.areEqual("Marble", list.getKey(1));
        msAssert.areEqual("apple 1", list.getKey(2));
        msAssert.areEqual("license", list.getKey(3));
    }

    /// <summary>
    /// Test that our implementation of scanning subfolders gives the same result as .NET 2.0 implementation.
    ///
    /// If our code works correctly on .NET 2.0 then it should work on .NET 1.1 as well because it doesn't
    /// use any .NET 2.0 specific code. So there is no need to test it on .NET 1.1.
    /// </summary>
    @Test
    public void testGetFilesFromFolder() throws Exception
    {
        String folder = TestFxUtil.buildTestFileName("Rendering");
        ArrayList<String> miscUtilResult = FsPathUtil.getFilesFromFolder(folder, false);
        String[] systemResult = Directory.getFiles(folder);
        msAssert.areEqual(systemResult.length, miscUtilResult.size());

        miscUtilResult = FsPathUtil.getFilesFromFolder(folder, true);
        systemResult = Directory.getFiles(folder, "*", SearchOption.ALL_DIRECTORIES);
        msAssert.areEqual(systemResult.length, miscUtilResult.size());

    }

    /// <summary>
    /// Tests <see cref="StringUtil.TrimEndNonDigits(string)"/> method.
    /// </summary>
    @Test
    public void testTrimEndNonDigits()
    {
        msAssert.areEqual("-1,234.57", StringUtil.trimEndNonDigits("-1,234.57%"));
        msAssert.areEqual("1,234.57", StringUtil.trimEndNonDigits("1,234.57-"));
    }

    /// <summary>
    /// Tests <see cref="UriUtil.CreateUriSafely"/> method.
    /// </summary>
    @Test
    public void testCreateUriSafely()
    {
        msAssert.areNotEqual(null, UriUtil.createUriSafely("file://C://temp//test.doc"));
        msAssert.areNotEqual(null, UriUtil.createUriSafely("file:\\\\C:\\\\temp\\\\test.doc"));
        msAssert.areNotEqual(null, UriUtil.createUriSafely("file://hostname//temp//test.doc"));
        msAssert.areNotEqual(null, UriUtil.createUriSafely("file:\\\\hostname\\\\temp\\\\test.doc"));
        // alexnosk: Currently code doesn't throw for such uri, just counts it as in current dir,
        // because UriKind.RelativeOrAbsolute is used.
        msAssert.areNotEqual(null, UriUtil.createUriSafely("test.doc"));
        msAssert.areNotEqual(null, UriUtil.createUriSafely("./test.doc"));
        msAssert.areNotEqual(null, UriUtil.createUriSafely("../test.doc"));
        msAssert.areNotEqual(null, UriUtil.createUriSafely("abracadabra"));
        // This is invalid one.
        msAssert.areEqual(null, UriUtil.createUriSafely("http://server:port/Identifier"));
    }

    /// <summary>
    /// Tests the <see cref="UriUtil.GetExtension"/> method in scenarios where the base URI is absent.
    /// </summary>
    @Test
    public void testUriUtilGetExtensionNoBaseUri()
    {
        msAssert.areEqual("ext", UriUtil.getExtension(null, "file.ext"));
        msAssert.areEqual("ext2", UriUtil.getExtension(null, "file.ext1.ext2"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, ".ext"));
        msAssert.areEqual("", UriUtil.getExtension(null, "file"));

        msAssert.areEqual("", UriUtil.getExtension(null, "folder/file"));
        msAssert.areEqual("", UriUtil.getExtension(null, "folder\\file"));
        msAssert.areEqual("", UriUtil.getExtension(null, "folder.ext/file"));
        msAssert.areEqual("", UriUtil.getExtension(null, "folder.ext\\file"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "folder/file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "folder\\file.ext"));
        msAssert.areEqual("ext2", UriUtil.getExtension(null, "folder.ext1/file.ext2"));
        msAssert.areEqual("ext2", UriUtil.getExtension(null, "folder.ext1\\file.ext2"));

        msAssert.areEqual("ext", UriUtil.getExtension(null, "c:\\file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "c:\\folder\\file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "\\\\server\\file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "\\\\server\\folder\\file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://folder/file.ext"));

        // URI with additional parts.
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://file.ext?query=value"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://file.ext?query=value&query2=value2"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://file.ext?query=value&query2=value2#id"));

        // There is no such thing as query part in Windows and UNC file paths.
        msAssert.areEqual("ext?query=value", UriUtil.getExtension(null, "c:\\file.ext?query=value"));
        msAssert.areEqual("ext?query=value", UriUtil.getExtension(null, "\\\\server\\file.ext?query=value"));

        // Href contains characters that are not allowed in Windows file paths.
        msAssert.areEqual("", UriUtil.getExtension(null, "?"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "?file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "c:\\file*name.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "c:\\file?name.ext"));
        msAssert.areEqual("", UriUtil.getExtension(null, "http://example.com/?query|value;"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://example.com/file.ext?query|value;"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http://example.com/file.ext?query=value"));

        // Some extreme cases.
        msAssert.areEqual("", UriUtil.getExtension(null, "http:?query=value"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "http:.ext"));
        msAssert.areEqual("", UriUtil.getExtension(null, "http:."));
        msAssert.areEqual("tp:", UriUtil.getExtension(null, "ht.tp:"));
        msAssert.areEqual("tp:", UriUtil.getExtension(null, "ht?.tp:"));
        msAssert.areEqual("ext", UriUtil.getExtension(null, "ht?tp:file.ext"));
        msAssert.areEqual("ext2", UriUtil.getExtension(null, "ht.ext?tp://file.ext2"));
    }

    /// <summary>
    /// Tests the <see cref="UriUtil.GetExtension"/> method in scenarios where the base URI is known.
    /// </summary>
    @Test
    public void testUriUtilGetExtensionWithBaseUri()
    {
        msAssert.areEqual("ext", UriUtil.getExtension("", "file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension("folder", "file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension("c:\\folder", "file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension("\\\\server\\", "file.ext"));
        msAssert.areEqual("ext", UriUtil.getExtension("http://example.com", "file.ext"));
        msAssert.areEqual("ext?query=value", UriUtil.getExtension("folder", "file.ext?query=value"));
        msAssert.areEqual("ext?query=value", UriUtil.getExtension("c:\\folder", "file.ext?query=value"));
        msAssert.areEqual("ext?query=value", UriUtil.getExtension("\\\\server\\", "file.ext?query=value"));
        msAssert.areEqual("ext", UriUtil.getExtension("http://example.com", "file.ext?query=value"));
    }

    @Test
    public void testRemoveSubstring()
    {
        String[] inputs = new String[] { "abcdef defde", "aaaa", "aqaqaqaq", "abababa" };
        String[] subs = new String[] { "def", "aa", "text", "aqa", "a" };
        for (int i = 0; i < inputs.length; i++)
        {
            String str = inputs[i];
            String substr = subs[i];
            msAssert.areEqual(StringUtil.removeSubstring(str, substr), str.replace(substr, ""));
        }
    }

    /// <summary>
    /// Tests the <see cref="StringUtil.Truncate"/> method.
    /// </summary>
    @Test (dataProvider = "testTruncateDataProvider")
    public void testTruncate(String value, int lenght, String expectedResult)
    {
        String actualResult = StringUtil.truncate(value, lenght);
        msAssert.areEqual(expectedResult, actualResult);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testTruncateDataProvider")
	public static Object[][] testTruncateDataProvider()
	{
		return new Object[][]
		{
			{null,  0,  null},
			{null,  5,  null},
			{"",  0,  ""},
			{"",  5,  ""},
			{"abcdefg",  0,  ""},
			{"abcdefg",  5,  "abcde"},
			{"abcdefg",  10,  "abcdefg"},
		};
	}

    /// <summary>
    /// Tests <see cref="UriUtil.FindUri(string,out string)"/>.
    /// </summary>
    @Test (dataProvider = "testFindUriDataProvider")
    public void testFindUri(String s, String expectedUri, String expectedSchemeName, int expectedUriIndex)
    {
        String uri = null;
        Ref<String> referenceToUri = new Ref<String>(uri);
        int uriIndex = UriUtil.findUri(s, /*out*/ referenceToUri);
        uri = referenceToUri.get();
        msAssert.areEqual(expectedUri, uri);
        msAssert.areEqual(expectedUriIndex, uriIndex);

        String schemeName = null;
        Ref<String> referenceToSchemeName = new Ref<String>(schemeName);
        uriIndex = UriUtil.findUriScheme(uri, /*out*/ referenceToSchemeName);
        schemeName = referenceToSchemeName.get();
        msAssert.areEqual(expectedSchemeName, schemeName);
        msAssert.areEqual("".equals(expectedSchemeName) ? -1 : 0, uriIndex);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFindUriDataProvider")
	public static Object[][] testFindUriDataProvider()
	{
		return new Object[][]
		{
			{"",  "",  "",  -1},
			{"https://www.text.com/html/convert.php",  "https://www.text.com/html/convert.php",  "https",  0},
			{"http://www.com/a.php\\http://www.com/b.php",  "http://www.com/a.php",  "http",  0},
			{"text http://w.com/a",  "http://w.com/a",  "http",  5},
			{"http://w.com/индекс",  "http://w.com/индекс",  "http",  0},
			{"text:http://w.com/index",  "text:http://w.com/index",  "text",  0},
			{"ht tp://w.com/index",  "tp://w.com/index",  "tp",  3},
			{"text http://w.com/index other text",  "http://w.com/index",  "http",  5},
			{"simple text",  "",  "",  -1},
			{"scheme:",  "scheme:",  "scheme",  0},
		};
	}

    /// <summary>
    /// Tests <see cref="UriUtil.FindUri(string,out string)"/> when search not from start.
    /// </summary>
    @Test
    public void testFindUriA()
    {
        final String S = "https://www.text.com/html/a.php\\https://www.text.com/html/b.php";
        String uri = null;
        Ref<String> referenceToUri = new Ref<String>(uri);
        int uriIndex = UriUtil.findUri(S, 5, /*out*/ referenceToUri);
        uri = referenceToUri.get();
        msAssert.areEqual("https://www.text.com/html/b.php", uri);
        msAssert.areEqual(32, uriIndex);
    }

    /// <summary>
    /// Tests the <see cref="UriUtil.ConstructAbsoluteUri(string, string)"/> method.
    /// </summary>
    @Test (dataProvider = "testConstructAbsoluteUriDataProvider")
    // This case doesn't work in MS Word or any browser. The base URL must end with a slash in order to work.
    // Not sure if we must support this scenario but it's here for historical reasons.
    // WORDSNET-17894 If relativePart starts with single slash and it has file name then resolve it as "absolute path".
    // WORDSNET-17894 If relativePart starts with double slash then resolve it as "network-path reference".
    // If the relative part is an UNC path then returns relative part only.
    // "https://" and "https:" are not valid base URLs but these cases work in IE, Edge, and MS Word.
    // We don't correct the following cases and process them as is.
    // Not a "network-path reference", because the base URL doesn't start with a scheme. It's a "garbage in, garbage out" scenario.
    // URL contains invalid port number. We don't actually parse parts of URSs so we keep invalid values.
    // URL with query part. We keep percent-encoded characters unchanged.
    // File URI scheme.
    // File URI scheme if relative part is an absolute path.
    // URI with spaces (no pecent-encoding).
    // URIs with backslashes.
    // The relative part is not recognized as absolute path in this case, because it starts with a backslash instead of forward slash.
    // More than two slashes after scheme.
    // No correction for the following cases. We process them as is.
    // WORDSNET-22235 Absolute Uri for Linux path was constructed incorrectly. The fix works only on Unix.
    // So the test case expects different results on Windows and on Unix.
    public void testConstructAbsoluteUri(String baseUrl, String relativePart, String expectedWindows, String expectedUnix) throws Exception
    {
        String expected = (StringUtil.hasChars(expectedUnix) && PlatformUtilPal.isUnixLike()) ? expectedUnix : expectedWindows;

        msAssert.areEqual(expected, UriUtil.constructAbsoluteUri(baseUrl, relativePart));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testConstructAbsoluteUriDataProvider")
	public static Object[][] testConstructAbsoluteUriDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"http://example.org",  "",  "http://example.org/",  ""},
			{"http://example.org/",  "",  "http://example.org/",  ""},
			{"c:\\test",  "",  "c:\\test\\",  ""},
			{"c:\\test\\",  "",  "c:\\test\\",  ""},
			{"",  "relativePart",  "relativePart",  ""},
			{"c:\\images",  "picture.png",  "c:\\images\\picture.png",  ""},
			{"c:\\images\\",  "picture.png",  "c:\\images\\picture.png",  ""},
			{"\\\\server\\",  "picture.png",  "\\\\server\\picture.png",  ""},
			{"http://images",  "picture.png",  "http://images/picture.png",  ""},
			{"http://images/",  "picture.png",  "http://images/picture.png",  ""},
			{"https://en.wikipedia.org/static/images/project-logos",  "/enwiki.png",  "https://en.wikipedia.org/enwiki.png",  ""},
			{"https://google.com/foo/bar",  "/test/enwiki.png",  "https://google.com/test/enwiki.png",  ""},
			{"http://images",  "/picture.png",  "http://images/picture.png",  ""},
			{"http://images/",  "/picture.png",  "http://images/picture.png",  ""},
			{"http://images",  "//picture.png",  "http://picture.png",  ""},
			{"http://images/",  "//picture.png",  "http://picture.png",  ""},
			{"http://images/dir1",  "//dir2/picture.png",  "http://dir2/picture.png",  ""},
			{"https://google.com/foo/bar",  "//test/enwiki.png",  "https://test/enwiki.png",  ""},
			{"",  "//picture.png",  "//picture.png",  ""},
			{"http://images",  "\\\\picture.png",  "\\\\picture.png",  ""},
			{"https://",  "//picture.png",  "https://picture.png",  ""},
			{"https:/",  "//picture.png",  "https://picture.png",  ""},
			{"https:",  "//picture.png",  "https://picture.png",  ""},
			{"https:/",  "/picture.png",  "https://picture.png",  ""},
			{"https://",  "picture.png",  "https://picture.png",  ""},
			{"https:",  "picture.png",  "https:/picture.png",  ""},
			{"https:/",  "picture.png",  "https:/picture.png",  ""},
			{"https:",  "/picture.png",  "https:/picture.png",  ""},
			{"https://",  "/picture.png",  "https:///picture.png",  ""},
			{"c://images",  "//picture.png",  "c://images//picture.png",  ""},
			{"https://google.com:100000/foo/bar",  "//example.org/image.png",  "https://example.org/image.png",  ""},
			{"https://google.com:100000/foo/bar",  "/test/enwiki.png",  "https://google.com:100000/test/enwiki.png",  ""},
			{"https://google.com:100000/foo/bar/",  "test/enwiki.png",  "https://google.com:100000/foo/bar/test/enwiki.png",  ""},
			{"https://google.com:100000/foo/bar",  "test/enwiki.png",  "https://google.com:100000/foo/bar/test/enwiki.png",  ""},
			{"https://google.com/foo/bar",  "/test/enwiki.png?query=abc%20def",  "https://google.com/test/enwiki.png?query=abc%20def",  ""},
			{"file://",  "localhost/c$/WINDOWS/clock.avi",  "file://localhost/c$/WINDOWS/clock.avi",  ""},
			{"file://localhost",  "etc/fstab",  "file://localhost/etc/fstab",  ""},
			{"file:///",  "etc/fstab",  "file:///etc/fstab",  ""},
			{"file:///",  "//localhost/image.png",  "file://localhost/image.png",  ""},
			{"file:///",  "c:/WINDOWS/clock.avi",  "c:/WINDOWS/clock.avi",  ""},
			{"file://locahost/",  "test/some file.png",  "file://locahost/test/some file.png",  ""},
			{"/usr/local",  "foo\\bar\\image.png",  "/usr/local/foo/bar/image.png",  ""},
			{"http://google.com/",  "c:\\foo\\bar.png",  "c:\\foo\\bar.png",  ""},
			{"http://google.com/image.png",  "\\foo\\bar.png",  "http://google.com/foo/bar.png",  ""},
			{"http:\\\\google.com\\",  "\\foo/bar.png",  "http:\\\\google.com\\foo\\bar.png",  ""},
			{"http:\\\\google.com\\",  "/foo/bar.png",  "http:\\\\google.com\\foo\\bar.png",  ""},
			{"https:////example.com/foo/bar",  "//images/picture.png",  "https://images/picture.png",  ""},
			{"https:////example.com/foo/bar",  "images/picture.png",  "https:////example.com/foo/bar/images/picture.png",  ""},
			{"https:////example.com/foo/bar",  "/images/picture.png",  "https:////example.com/images/picture.png",  ""},
			{"/tmp/generateWordFromHtml",  "/tmp/generateWordFromHtml/images/image0.png",  "/tmp/generateWordFromHtml/tmp/generateWordFromHtml/images/image0.png", 
        "/tmp/generateWordFromHtml/images/image0.png"},
		};
	}
}

