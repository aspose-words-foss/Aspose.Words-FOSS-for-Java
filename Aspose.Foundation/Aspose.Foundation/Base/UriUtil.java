// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/10/2006 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msUri;
import com.aspose.ms.System.UriKind;
import java.net.URISyntaxException;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.words.ref.Ref;


/// <summary>
/// RK I would have preferred using only System.Uri to deal with URIs, but unfortunately
/// System.Uri does not support relative URIs and we can encounter relative URIs in Word documents.
/// This is a set of utility functions that we use to deal with URIs in Word documents.
///
/// There is a problem with naming. Strictly speaking, a URIs are like these:
/// http://example/resource.txt#frag01
/// ../../../resource.txt
///
/// But MS Word can store either a URI, an absolute DOS file path or UNC file path in a single field.
/// Therefore it requires some logic when handling. For example, we should escape/unescape URI only,
/// never DOS or UNC path.
///
/// So how should I call a value that can be URI, DOS or UNC path? In this code, I call them Href.
/// </summary>
public /*static*/ class UriUtil
{
	/* Simulation of static class by using private constructor */
	private UriUtil()
	{}

    /// <summary>
    /// Returns extension of a resource file name from a Href.
    /// </summary>
    /// <param name="baseHref">Base Href of the resource. </param>
    /// <param name="resourceHref">Resource Href.</param>
    /// <returns>
    /// Extension of the resource file name, without the leading dot character.
    /// If the file name has no extension, an empty string is returned.
    /// </returns>
    /// <remarks>
    /// This method correctly works with URIs that have a query part, like 'http://example.com/file.ext?query=value'.
    /// Unlike <see cref="Path.GetExtension"/>, this method doesn't thow an exception in case the resource Href is an URI
    /// that contains characters not allowed in a Windows file path.
    /// </remarks>
    public static String getExtension(String baseHref, String resourceHref)
    {
        String absoluteHref = constructAbsoluteUri(baseHref, resourceHref);
        if (!StringUtil.hasChars(absoluteHref))
        {
            return "";
        }

        // Hrefs that have a scheme are URIs, which can contain a query part. Let's skip it.
        int fileNameEndPosition = absoluteHref.length();
        if (isHrefWithScheme(absoluteHref))
        {
            int schemeEndPosition = absoluteHref.indexOf(':');
            Debug.assertTrue(schemeEndPosition > 0);
            int queryStartPosition = absoluteHref.indexOf('?');
            // Also protect against invalid schemes with '?' inside, like this: ht?tp://example.com
            if (queryStartPosition > schemeEndPosition)
            {
                fileNameEndPosition = queryStartPosition;
            }
        }

        if (fileNameEndPosition <= 0)
        {
            return "";
        }

        // We know where the file name ends. Let's move backward and read its extension - the trailing part.
        for (int extensionStartPosition = fileNameEndPosition - 1; extensionStartPosition >= 0; extensionStartPosition--)
        {
            char currentChar = absoluteHref.charAt(extensionStartPosition);
            if (currentChar == '.')
            {
                // Don't include the dot character into the result.
                return absoluteHref.substring((extensionStartPosition + 1), (extensionStartPosition + 1) + (fileNameEndPosition - extensionStartPosition - 1));
            }
            if ((currentChar == '\\') || (currentChar == '/'))
            {
                break;
            }
        }
        return "";
    }

    /// <summary>
    /// Makes full uri path from base uri and given uri. Leaves escaped characters 'as is'.
    /// <seealso cref="ConstructUnescapedAbsoluteUri"/>.
    /// </summary>
    /// <remarks>
    /// For historical reasons, this method always treats the last segment of the path in the base URI as a folder rather
    /// than as a file. For example, "http://example.org/image" + "foo/bar" = "http://example.org/image/foo/bar", instead
    /// of "http://example.org/foo/bar" (the "image" segment is not replaced by the "foo" segment).
    /// </remarks>
    public static String constructAbsoluteUri(String baseUri, String originalUri)
    {
        // WORDSNET-22235 The previous condition did not check Linux absolute path.
        // Simple check for Linux absolute path does not work properly on Linux,
        // so added an additional check for 'fake' Linux absolute path.
        return isAbsoluteHref(originalUri) && !isFakeAbsoluteLocalFilePathUnix(baseUri, originalUri)
            ? originalUri
            : combineHref(baseUri, originalUri);
    }

    /// <summary>
    /// Makes full uri path from base uri and given uri. Unescapes the result if required by the uri scheme.
    /// <seealso cref="ConstructAbsoluteUri"/>.
    /// </summary>
    public static String constructUnescapedAbsoluteUri(String baseUri, String originalUri)
    {
        String absoluteUri = constructAbsoluteUri(baseUri, originalUri);

        // WORDSNET-8682 A URI without scheme are processed as local file paths, and file system routines
        // do not accept percent-encoded characters in file paths, so characters in a URI without scheme are unescaped.
        // A URI with scheme (http:, file:, etc.) is processed as a remote resource reference (that is, as a normal URI),
        // which require percent-encoding of certain characters, so characters in a URI with scheme are left escaped.
        if (!isHrefWithScheme(absoluteUri))
        {
            absoluteUri = unescapeHref(absoluteUri);
        }

        return absoluteUri;
    }

    /// <summary>
    /// Gets the hyperlink part that is before the "#".
    /// </summary>
    public static String getAddress(String href)
    {
        Debug.assertTrue(href != null);

        int hashPos = href.indexOf('#');
        if (hashPos < 0)
            return href;
        else
            return href.substring((0), (0) + (hashPos));
    }

    /// <summary>
    /// Gets the hyperlink part that is after the "#" excluding the "#".
    /// </summary>
    public static String getSubAddress(String href)
    {
        Debug.assertTrue(href != null);

        int hashPos = href.indexOf('#');
        if (hashPos < 0)
            return "";
        else
            return msString.trimEnd(href.substring(hashPos + 1), '\'', '"');
    }

    /// <summary>
    /// If subaddress is not empty or null, returns address + "#" + subAddress, otherwise returns just address.
    /// </summary>
    public static String appendSubAddress(String address, String subAddress)
    {
        if (address == null)
            address = "";

        if (StringUtil.hasChars(subAddress))
            address = msString.plusEqOperator(address, "#" + subAddress);

        return address;
    }

    /// <summary>
    /// Returns true if the string seems to be a URI with a scheme.
    /// </summary>
    public static boolean isHrefWithScheme(String href)
    {
        // WORDSNET-17141 Path starting with "\\?\C:\" is a DOS device path.
        if (msString.startsWith(href, "\\\\?\\", StringComparison.ORDINAL))
            return false;

        // Example: http://myserver/myfolder/myfile.doc returns true,
        // but X:\test\test.txt returns false
        return (href.indexOf(':') > 1);
    }

    /// <summary>
    /// Returns true if the string seems to be a an absolute local file path or UNC path.
    /// </summary>
    public static boolean isFilePath(String href)
    {
        return isAbsoluteLocalFilePath(href) || isUncPath(href);
    }

    /// <summary>
    /// Returns true if the string seems to be an absolute local file path.
    /// </summary>
    public static boolean isAbsoluteLocalFilePath(String href)
    {
        return isAbsoluteLocalFilePathWindows(href) || isAbsoluteLocalFilePathUnix(href);
    }

    public static boolean isAbsoluteLocalFilePathWindows(String href)
    {
        // DOS/Windows: X:\MyFolder\MyFile.doc
        return href.length() > 2 && isDriveLetter(href.charAt(0)) && href.charAt(1) == ':' && (href.charAt(2) == '\\' || href.charAt(2) == '/');
    }

    public static boolean isAbsoluteLocalFilePathUnix(String href)
    {
        // Linux/MacOS/Android absolute path starts with root directory '/'. But do not count here two slashes '//'.
        return href.length() > 1 && href.charAt(0) == '/' && href.charAt(1) != '/' && PlatformUtilPal.isUnixLike();
    }

    /// <summary>
    /// Returns true if the string seems to be a UNC path.
    /// </summary>
    public static boolean isUncPath(String href)
    {
        // Example: \\myserver\myfolder\myfile.doc
        return msString.startsWith(href, "\\\\", StringComparison.ORDINAL);
    }

    /// <summary>
    /// Returns true if original Uri starts with slash, like absolute path in Unix,
    /// but baseUri has a scheme. In this case originalUri should not be considered as absolute.
    /// </summary>
    private static boolean isFakeAbsoluteLocalFilePathUnix(String baseUri, String originalUri)
    {
        return isAbsoluteLocalFilePathUnix(originalUri) && isHrefWithScheme(baseUri);
    }

    /// <summary>
    /// Returns true if the string starts with "cid:".
    /// </summary>
    public static boolean isCid(String href)
    {
        return msString.startsWith(href, "cid:", StringComparison.ORDINAL);
    }

    private static boolean isDriveLetter(char c)
    {
        return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
    }

    /// <summary>
    /// The base uri can be a local or remote uri.
    /// I'm not using the Uri class because if baseUri does not end with a slash,
    /// its ctor will treat the last subfolder as the file name.
    /// </summary>
    public static String combineHref(String basePart, String relativePart)
    {
        if (!StringUtil.hasChars(basePart))
        {
            return relativePart;
        }

        // This is our best guess at using the correct path separator.
        // If there are forward slashes already, use one of them, otherwise use backward slash,
        char separator = (basePart.indexOf('\\') >= 0) ? '\\' : '/';

        // In the resulting href, all separators should be uniform. Make sure slashes used in the relative part
        // are same as in the base part.
        char mirroredSeparator = (separator == '\\') ? '/' : '\\';

        // Canonicalize separators.
        basePart = msString.replace(basePart, mirroredSeparator, separator);
        relativePart = msString.replace(relativePart, mirroredSeparator, separator);

        // WORDSNET-17894 Resolving a relative URI is more than just appending the relative part to the base part.
        // We must also take into account cases where the relative part is a network path (starts with two slashes).
        // For details, see https://tools.ietf.org/html/rfc3986#section-4.2
        if (isHrefWithScheme(basePart) && msString.startsWith(relativePart, "//", StringComparison.ORDINAL))
        {
            // Canonicalize separators.
            basePart = msString.replace(basePart, '\\', '/');
            relativePart = msString.replace(relativePart, '\\', '/');

            // Note that if we get here, the base URI must start with a scheme part.
            return getScheme(basePart) + ":" + relativePart;
        }

        // WORDSNET-17894 Resolving a relative URI is more than just appending the relative part to the base part.
        // We must also take into account cases where the relative part is an absolute path (starts with a slash).
        // For details, see https://tools.ietf.org/html/rfc3986#section-4.2
        if (isHrefWithScheme(basePart) && msString.startsWith(relativePart, "/", StringComparison.ORDINAL))
        {
            // Canonicalize separators.
            basePart = msString.replace(basePart, '\\', '/');
            relativePart = msString.replace(relativePart, '\\', '/');

            // The relative URI is an absolute path (for example, "/foo/bar/image.png").
            // Replace the path part of the base URI with the path specified in the relative URI.
            String schemeAndAuthority = getSchemeAndAuthority(basePart);
            if (StringUtil.hasChars(schemeAndAuthority))
            {
                return schemeAndAuthority + relativePart;
            }

            // If the base URI is malformed or doesn't have an authority part, process it as is.
            return basePart + relativePart;
        }

        boolean basePartEndsWithSeparator = (basePart.charAt(basePart.length() - 1) == separator);
        boolean relativePartStartsWithSeparator = msString.startsWith(relativePart, Character.toString(separator), StringComparison.ORDINAL);

        // WORDSNET-26575 Prevent extra slash char in a href.
        if (basePartEndsWithSeparator && relativePartStartsWithSeparator)
        {
            return msString.trimEnd(basePart, separator) + relativePart;
        }

        if (basePartEndsWithSeparator || relativePartStartsWithSeparator)
        {
            return basePart + relativePart;
        }

        return basePart + separator + relativePart;
    }

    public static String getScheme(String uri)
    {
        Debug.assertTrue(isHrefWithScheme(uri));

        int schemeEndIndex = uri.indexOf(':');

        return uri.substring((0), (0) + (schemeEndIndex));
    }

    public static String getSchemeAndAuthority(String uri)
    {
        Debug.assertTrue(isHrefWithScheme(uri));

        // This regex matches the scheme and the authority parts of an URI.
        Match match = Regex.match(uri, "^[^:]*:[/]*[^/]+");

        return match.getSuccess() ? match.getValue() : "";
    }

    /// <summary>
    /// Gets directory path from given HREF.
    /// </summary>
    public static String getDirectoryHref(String href)
    {
        int index = msString.lastIndexOfAny(href, G_SLASHES);
        return (index < 0) ? "." : href.substring((0), (0) + (Math.max(index, 1)));
    }

    /// <summary>
    /// Gets directory path from given HREF.
    /// </summary>
    /// <remarks>There were problems with TestImportMhtmlOther.TestDecodeUriWindows1251 test when
    /// <see cref="GetDirectoryHref"/> method was modified, so it's better to introduce new method and leave
    /// <see cref="GetDirectoryHref"/> method as is for now.</remarks>
    public static String getDirectoryHrefWithScheme(String href)
    {
        Debug.assertTrue(isHrefWithScheme(href) && !hasFileScheme(href));

        String schemeAndAuthority = getSchemeAndAuthority(href);

        String hrefWithoutSchemeAndAuthority = href.substring(schemeAndAuthority.length());

        int indexOfLastSlash = msString.lastIndexOfAny(hrefWithoutSchemeAndAuthority, G_SLASHES);

        return (indexOfLastSlash != -1)
            ? schemeAndAuthority + hrefWithoutSchemeAndAuthority.substring((0), (0) + (indexOfLastSlash + 1))
            : schemeAndAuthority;
    }

    /// <summary>
    /// Checks whether HREF represents a path with extension.
    ///
    /// RK This looks pretty nasty to me. What "path", what "href" you are talking about?
    /// </summary>
    public static boolean isPathWithExtension(String href)
    {
        int indexDot = href.lastIndexOf('.');
        int indexSlash = msString.lastIndexOfAny(href, G_SLASHES);
        int indexSchemaEnd = msString.indexOf(href, "://", StringComparison.ORDINAL);

        // If all -1 then condition is also okay.
        return (indexDot > indexSlash) && ((indexSchemaEnd < 0) || (indexSchemaEnd + 2 != indexSlash));
    }

    /// <summary>
    /// Returns true if the string starts with the "#" sign.
    /// </summary>
    public static boolean isSubAddressOnly(String href)
    {
        return msString.startsWith(href, "#", StringComparison.ORDINAL);
    }

    /// <summary>
    /// Escapes the specified string if it needs escaping and is not already escaped.
    /// </summary>
    public static String escapeHref(String href)
    {
        return isHrefThatNeedsEscaping(href) ? escapeHrefAnyway(href) : href;
    }

    /// <summary>
    /// Escapes the specified string.
    /// </summary>
    public static String escapeHrefAnyway(String href)
    {
        href = msString.trim(href);

        StringBuilder builder = new StringBuilder();
        int index = 0;

        while (index < href.length())
        {
            char ch = href.charAt(index);

            if (isCharThatNeedsEscaping(ch))
            {
                appendEscapeChar(builder, ch);
            }
            else
            {
                builder.append(ch);
            }

            index++;
        }

        return builder.toString();
    }

    /// <summary>
    /// Unescapes provided string if it needs unescaping.
    /// </summary>
    public static String unescapeHref(String href)
    {
        if (!StringUtil.hasChars(href))
            return href;

        StringBuilder builder = new StringBuilder();

        int index = 0;

        while (index < href.length())
        {
            char ch = href.charAt(index);

            if ((ch == '%') && (index + 2 < href.length()))
            {
                String escapeString = href.substring((index), (index) + (3));

                if (isEscapedCharString(escapeString))
                {
                    builder.append(unescapeCharString(escapeString));
                    index += 3;
                    continue;
                }
            }

            builder.append(ch);

            index++;
        }

        return builder.toString();
    }

    private static boolean isCharThatNeedsEscaping(char ch)
    {
        return CHARS_THAT_ARE_ESCAPED_IN_URI.indexOf(ch) >= 0;
    }

    /// <summary>
    /// Checks if the provided path string is escaped or does not need escaping.
    /// </summary>
    public static boolean isHrefThatNeedsEscaping(String href)
    {
        // Check if path string is empty or null.
        if (!StringUtil.hasChars(href))
            return false;

        // Local file and UNC paths do not need escaping.
        if (isAbsoluteLocalFilePathWindows(href) || isUncPath(href))
            return false;

        // Check if path string contains characters invalid for escaped URI.
        if (msString.indexOfAny(href, G_CHARS_NOT_VALID_IN_ESCAPED_URI) >= 0)
            return true;

        // Check if all '%' characters correspond to valid URI escapes.
        for (int i = 0; i < href.length(); i++)
        {
            if ((href.charAt(i) == '%') && (i + 2 < href.length()) && !isEscapedCharString(href.substring((i), (i) + (3))))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns true if the URI, UNC path or file path is absolute (rooted).
    ///
    /// This method is useful to call before trying to construct a .NET Uri object because
    /// the .NET Uri object will throw if the uri is relative.
    /// </summary>
    public static boolean isAbsoluteHref(String href)
    {
        return
            isHrefWithScheme(href) ||
            isAbsoluteLocalFilePath(href) ||
            isUncPath(href);
    }

    /// <summary>
    /// Checks if the provided string is an escaped char.
    /// </summary>
    private static boolean isEscapedCharString(String s)
    {
        return (s.length() == 3) && (s.charAt(0) == '%') && StringUtil.isHexDigit(s.charAt(1)) && StringUtil.isHexDigit(s.charAt(2));
    }

    private static char unescapeCharString(String s)
    {
        return (char)(StringUtil.hexCharToDigit(s.charAt(1)) * 0x10 + StringUtil.hexCharToDigit(s.charAt(2)));
    }

    private static void appendEscapeChar(StringBuilder stringBuilder, char ch)
    {
        appendEscapeChar(stringBuilder, (byte)ch);
    }

    private static void appendEscapeChar(StringBuilder stringBuilder, byte ch)
    {
        stringBuilder.append('%');
        stringBuilder.append(StringUtil.byteToHex(ch));
    }

    /// <summary>
    /// Converts relative filename to absolute filename.
    /// Returns unchanged if given name is already absolute filename.
    /// </summary>
    public static String getAbsoluteFileName(String masterFileName, String fileName) throws Exception
    {
        // WORDSNET-10529 Loading document from the stream results to passing here null value, when reading subDoc.
        if ((masterFileName != null) && (msString.equals(Path.getFileName(fileName), fileName)))
            return Path.combine(Path.getDirectoryName(masterFileName), Path.getFileName(fileName));

        return fileName;
    }

    /// <summary>
    /// Creates instance of Uri class safely.
    /// We can't use Uri.IsWellFormedUriString Method for checking because of compatibility with .Net 1.1.
    /// </summary>
    /// <param name="uriString">Uri string.</param>
    /// <returns>Uri object or null if was passed invalid uri string.</returns>
    public static msUri createUriSafely(String uriString)
    {
        if (!StringUtil.hasChars(uriString))
            return null;

        try
        {
            // alexnosk: Added UriKind to accept relative paths as a valid Uris,
            // this is needed to properly handle hyperlinks in XAML Flow.
            return new msUri(uriString, UriKind.RELATIVE_OR_ABSOLUTE);
        }
        catch (URISyntaxException e)
        {
            return null;
        }
    }

    /// <summary>
    /// Returns true if the Uri has the file scheme.
    /// </summary>
    public static boolean hasFileScheme(String uri)
    {
        return msString.startsWith(uri, FILE_SCHEME_PREFIX, StringComparison.ORDINAL);
    }

    /// <summary>
    /// Removes file scheme prefix from the Uri string.
    /// </summary>
    public static String removeFileSchemePrefix(String uri)
    {
        Debug.assertTrue(hasFileScheme(uri));

        String localUri = uri.substring(FILE_SCHEME_PREFIX.length());
        // As we remove protocol name, we should check that path does not start from the single '\' character
        // and precede it with the additional '\' to make it valid in this case.
        if ((localUri.length() > 1) && (localUri.charAt(0) == '\\') && (localUri.charAt(1) != '\\'))
            localUri = '\\' + localUri;

        return localUri;
    }

    /// <summary>
    /// Adds the file scheme prefix "file:///".
    /// </summary>
    public static String addFileSchemePrefix(String uri)
    {
        return FILE_SCHEME_PREFIX + uri;
    }

    /// <summary>
    /// Replaces the file scheme prefix "file:///".
    /// </summary>
    public static String replaceFileProtocolPrefix(String uri)
    {
        return uri.replace(FILE_SCHEME_PREFIX, "");
    }

    /// <summary>
    /// Converts any href to proper URI according to RFC 3986/2396 and also escapes Unicode according to RFC 3987.
    /// </summary>
    public static String hrefToUri(String href)
    {
        if (!StringUtil.hasChars(href))
            return href;

        if (isAbsoluteLocalFilePathWindows(href) || isUncPath(href))
        {
            // Experiments show that for file paths MW does not resolve % escaped sequences.
            // I.e. for "C:\%41bc.docx" href MW opens "C:\%41bc.docx" file and not "C:\Abc.docx".
            // Uri class always resolves escaped sequences so explicitly escape % char before passing href into Uri.
            String path = href.replace("%", "%25");

            // Try to handle path with Uri class. Seems that it handles paths the same way as MW.
            String pathEscaped = tryNativeEscape(path);
            if (pathEscaped != null)
                return pathEscaped;

            // Sometimes native class cant handle paths. In this case we should manually add file prefix and escape the
            // string.
            return escapeGeneralHrefToUri(addFileSchemePrefix(path));
        }

        // Try to handle other hrefs with Uri class. Seems that it handles Uri the same way as MW.
        String uriEscaped = tryNativeEscape(href);
        if (uriEscaped != null)
            return uriEscaped;

        // Consider all other hrefs as relative. Uri class can't handle them so process them by own method.
        // Also here goes the Unix-like paths. Seems that Uri class can't handle them. And it seems that MW also
        // handles them the same way as other relative paths.
        return escapeGeneralHrefToUri(href);
    }

    /// <summary>
    /// Returns index of the first occurrence of a valid URI scheme name within a specified string.
    /// The out param <paramref name="schemeName"/> is returned without trailing comma ':'.
    /// </summary>
    public static int findUriScheme(String s, /*out*/Ref<String> schemeName)
    {
        return findUriScheme(s, 0, /*out*/ schemeName);
    }

    /// <summary>
    /// Returns index of the first occurrence of a valid URI scheme name within a specified string.
    /// The out param <paramref name="schemeName"/> is returned without trailing comma ':'.
    /// </summary>
    /// <remarks>
    /// See remark at https://learn.microsoft.com/en-us/dotnet/api/system.uri.checkschemename for rules.
    /// </remarks>
    public static int findUriScheme(String s, int index, /*out*/Ref<String> schemeName)
    {
        schemeName.set("");
        int i = index;
        // The scheme name must begin with a letter.
        while ((i < s.length()) && !Character.isLetter(s.charAt(i)))
            i++;

        int schemeIndex = i;

        i++;
        char c = '\u0000';
        while (i < s.length())
        {
            c = s.charAt(i);
            // The scheme name must contain only letters, digits, and the characters ".", "+", or "-".
            if (!Character.isLetterOrDigit(c) && (c != '.') && (c != '+') && (c != '-'))
                break;
            i++;
        }

        // The scheme name must end with a colon.
        if (c != ':')
        {
            // Try rest of input string.
            if (++i < s.length())
                return findUriScheme(s, i, /*out*/ schemeName);

            return -1;
        }

        schemeName.set(s.substring((schemeIndex), (schemeIndex) + (i - schemeIndex)));
        return schemeIndex;
    }

    /// <summary>
    /// Returns index of the first occurrence of a valid URI within a specified string.
    /// </summary>
    public static int findUri(String s, /*out*/Ref<String> uri)
    {
        return findUri(s, 0, /*out*/ uri);
    }

    /// <summary>
    /// Returns index of the first occurrence of a valid URI within a specified string.
    /// </summary>
    public static int findUri(String s, int index, /*out*/Ref<String> uri)
    {
        uri.set("");

        String schemeName = null;
        Ref<String> referenceToSchemeName = new Ref<String>(schemeName);
        int schemeIndex = findUriScheme(s, index, /*out*/ referenceToSchemeName);
        schemeName = referenceToSchemeName.get();
        if (schemeIndex == -1)
            return -1;

        int i = schemeIndex + schemeName.length();
        while (i < s.length())
        {
            char c = s.charAt(i);
            // The uri must contain only letters, digits and some other characters
            // in accordance with https://www.ietf.org/rfc/rfc3986.txt
            if (!Character.isLetterOrDigit(c) && !ArrayUtil.findCharInArray(G_VALID_URI_CHARS, c))
                break;
            i++;
        }

        uri.set(s.substring((schemeIndex), (schemeIndex) + (i - schemeIndex)));
        return schemeIndex;
    }

    /// <summary>
    /// Replaces JavaScript URIs with "javascript:void(0)".
    /// </summary>
    public static String removeJavaScript(String href)
    {
        return StringUtil.startsWithOrdinalIgnoreCase(href, "javascript:")
            ? "javascript:void(0)"
            : href;
    }

    private static String tryNativeEscape(String href)
    {
        msUri uri = createUriSafely(href);
        if (uri == null || !uri.isAbsoluteUri())
            return null;

        String escaped;
        // JAVA-changed from uri.GetComponents(UriComponents.AbsoluteUri, UriFormat.UriEscaped):
        escaped = uri.getAbsoluteUri();
        // For some reason Uri class does not escape Latin-1 Supplement chars (0x80-0xFF). So additionally escape
        // all remaining Unicode.
        return escapeUnicode(escaped);
    }

    /// <summary>
    /// Own implementation of href escaping to URI (RFC 3986/2396).
    /// Native escaping works better but can't handle some cases. Also absolute file paths are escaped a bit
    /// differently than other hrefs by MW.
    /// </summary>
    private static String escapeGeneralHrefToUri(String href)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < href.length(); i++)
        {
        	char ch = href.charAt(i);
            // Note: For general hrefs (not absolute file path) MW seems to keep escape sequences intact even if
            // they points to files. I.e. MW opens a path ".\%41bc.docx" as "Abc.docx" and not as "%41bc.docx".
            // So do not process escape sequences to fit MW behavior.

            if (ch == '\\')
            {
                // Backslash delimiters from Windows path should be replaced with forward slash in URI.
                builder.append('/');
            }
            else if (isRegularAsciiChar(ch) && ch != ' ')
            {
                // It seems that MW does not escape all regular ASCII chars excepting space
                // (i.e. both reserved and unreserved URL chars).
                builder.append(ch);
            }
            else if (isAsciiChar(ch))
            {
                // Escape all remaining ASCII chars. Here goes space char and control chars.
                // Control chars probably are not valid for href but escape them just in case.
                appendEscapeChar(builder, ch);
            }
            else
            {
                // Just append all non-ASCII chars. They will be escaped later.
                builder.append(ch);
            }
        }

        return escapeUnicode(builder.toString());
    }

    private static boolean isRegularAsciiChar(char ch)
    {
        return ch >= ' ' && ch <= '~';
    }

    private static boolean isAsciiChar(char ch)
    {
        return ch < 0x80;
    }

    private static String escapeUnicode(String href)
    {
        if (!isUnicodeEscapeRequired(href))
            return href;

        // RFC 3986/2396 defines only ASCII chars in the URI.
        // RFC 3987 defines that remaining Unicode chars should be % escaped by they UTF8 codes.
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < href.length())
        {
            char ch = href.charAt(i++);

            if (!isAsciiChar(ch))
            {
                String s = msString.newString(ch, 1);
                if (UnicodeUtil.isSurrogatePair(href, i - 1))
                {
                    s = href.substring((i - 1), (i - 1) + (2));
                    i++;
                }

                appendEscapedUtf8(builder, s);
            }
            else
            {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    private static boolean isUnicodeEscapeRequired(String href)
    {
        for (int i = 0; i < href.length(); i++)
        {
        	char c = href.charAt(i);
            if (!isAsciiChar(c))
                return true;
        }

        return false;
    }

    private static void appendEscapedUtf8(StringBuilder builder, String s)
    {
        byte[] utf8Data = new UTF8Encoding().getBytes(s);
        for (byte b : utf8Data)
            appendEscapeChar(builder, b);
    }

    /// <summary>
    /// The characters valid in URI in addition to Letters and Digits.
    /// </summary>
    /// <remarks>https://www.ietf.org/rfc/rfc3986.txt</remarks>
    private static /*final*/ char[] G_VALID_URI_CHARS =
    {
        '-', '.', '_', '~', ':', '/', '?', '#', '[', ']', '@', '!', '$', '&', '\'', '(', ')', '*', '+', ',', ';', '%', '='
    };

    private static final String CHARS_THAT_ARE_ESCAPED_IN_URI = " <>{}|^[]`\"%#";

    private static /*final*/ char[] G_CHARS_NOT_VALID_IN_ESCAPED_URI = CHARS_THAT_ARE_ESCAPED_IN_URI
        .replace("%", "")
        .toCharArray();

    private static /*final*/ char[] G_SLASHES = { '/', '\\' };

    /// <summary>
    /// This string is the prefix of file scheme URIs.
    /// </summary>
    private static final String FILE_SCHEME_PREFIX = "file:///";
}

