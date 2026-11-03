//Konstantin Sidorenko 20/12/11

package com.aspose.ms.System;

import com.aspose.ms.java.IO.JavaURI;
import com.aspose.ms.java.util.JavaUtil;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * A minimal copycat of .NET's Uri.
 */
public class msUri
{
    // UriKind.Absolute in .Net but this accepts Relative too
    public msUri(String uriString) throws URISyntaxException
    {
        initRelativeAbsolute(uriString);
    }

    public msUri(String uriString, /*UriKind*/int uriKind) throws URISyntaxException
	{
        uriString = uriString.trim();

        // usiString needs to be further checked after trimming (see WORDSJAVA-2795)
        if(msString.isNullOrEmpty(uriString))
            throw new URISyntaxException("Empty UriString", uriString);

        switch (uriKind)
        {
            case UriKind.ABSOLUTE:
                //the same as UriKind.RelativeOrAbsolute at the moment
            case UriKind.RELATIVE_OR_ABSOLUTE:
                initRelativeAbsolute(uriString);
                break;
            case UriKind.RELATIVE:
                initRelative(uriString);
                break;
            default:
                throw new IllegalArgumentException("Illegal UriKind: " + uriKind);
        }
    }

    private void initRelative(String uriString)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    private void initRelativeAbsolute(String uriString) throws URISyntaxException
    {
        // Java's URI ctor throws on paths with backslashes, e.g. 'file://PAVEL-PC\Users\Public\aspose%20exchange.txt'
        uriString = uriString.replace('\\', '/');

        if (isUncPath(uriString))
        {
            // skip the leading consecutive slashes
            int pos1 = 0;
            while (pos1 < uriString.length() && isSlash(uriString, pos1))
                pos1++;

            int pos2 = pos1;
            while (pos2 < uriString.length() && !isSlash(uriString, pos2))
                pos2++;

            String host = uriString.substring(pos1, pos2);
            String path = uriString.substring(pos2);

            mUri = new JavaURI("file", host, path, null);
        }
        else if (isDosPath(uriString))
        {
            mUri = new JavaURI("file", "", "/" + uriString, null);
        }
        else
        {
            // uriString represents absolute uri with scheme
            // Skip leading whitespaces, correct slash count.
            uriString = correctSchemeSlashes(uriString);
            // .Net tolerates spaces just before separator: "dir /file.txt".
            uriString = uriString.replace(" ", "%20");
            mUri = new JavaURI(uriString);
            // Fixed TestMiscUtil.testCreateUriSafely().
            // msAssert.areEqual(null, MiscUtil.createUriSafely("http://server:port/Identifier"));
            // .NET throws an exception if port can't be recognized.
            mUri = mUri.parseServerAuthority();
        }
    }


	/**
	 * Skips leading whitespaces, eats spare slashes or adds missed ones for most common protocols:
	 * 'file:/c:/test.pdf' -> 'file:///c:/test.pdf',
	 * 'http:localhost' -> 'http://localhost'
	 * 'http://////localhost' -> 'http://localhost'
     *
     * Short resume about slashes in Uri scheme:
     * 1. 0 slashes: protocol without authority part, like 'mailto:', 'callto:'.
     * 2. 2 slashes: Basic Uri like 'file://host/c:/test.pdf' or 'file://host/etc/test.pdf'.
     * 3. 3 slashes: Special case when host is localhost and it is omitted: 'file:///c:/test.pdf' or 'file:///etc/test.pdf'.
     * 4. 4 slashes - "legacy" URLs. Should be converted to 2 slashes.
     * 5. 4+ slashes - errors that can exist in real files. Should be converted to 2 slashes.
     *
     * So.
     * 1. file scheme + (dos-like path or 3 slashes) => 3 slashes (implicit localhost).
     * 2. non-local file, http, https, ftp (i.e. most common hierarchical protocols) => 2 slashes.
     * 3. 0, 1 slashes for all other protocols are leaved as is.
     * 4. 2+ slashes for all other protocols => 2 slashes.
     *
     * See http://en.wikipedia.org/wiki/URI_scheme for details.
     * 
     * Note: .Net Uri throws on 0 or 1 slash - we don't (as some browsers). It works (i believe) for uri with scheme
     * but result is undefined for absolute Uri without scheme (like '/HOST/c:/test.pdf') - it will be treated as
     * relative local Uri i.e. something like 'X:/HOST/c:/test.pdf' - so i can't even include the case in tests.
	 */
	private static String correctSchemeSlashes(String uri)
	{
        int length = uri.length();
		StringBuilder sb = new StringBuilder(length + 2);
		int pos = 0;
		char c;

		// skip leading whitespace
        while (pos < length && Character.isWhitespace(uri.charAt(pos)))
			pos++;

		// read name of scheme
		while (pos < length && (c = uri.charAt(pos++)) != ':')
			sb.append(c);

		// FIX WORDSJAVA-852: Relative hyperlink with Unicode is improperly saved to Pdf.
		// Uri may not have scheme at all, for instance "~parts~/Body/image1.jpg". Just return the original uri
		// without leading whitespaces in this case.
		if (pos == length)
			return sb.toString();

		String scheme = sb.toString();

		// skip the consecutive slashes
        int slashCount = 0;
		while (pos < length && isSlash(uri, pos))
        {
			pos++;
            slashCount++;
        }

        // Fixed TestSaveToPdf.testJira9384()
        // Unsupported scheme, just return original string as is.
        if ( "notes".equals(scheme) )
            return uri;

        // Most common protocols cause most errors, so they processed in a special way.
        if ( "file".equals(scheme) )
        {
            if (isDosPath(uri, pos) || slashCount == 3)
                sb.append(":///");
            else
                sb.append("://");
        }
        else if ( "http".equals(scheme) || "https".equals(scheme) || "ftp".equals(scheme) )
        {
            sb.append("://");
        }
        else
        {
            // For all others protocols
            switch (slashCount) {
                case 0:
                    sb.append(":");
                    break;
                case 1:
                    sb.append(":/");
                    break;
                default:
                    sb.append("://");
                    break;
            }
        }

		// append the rest of the uri
		sb.append(uri, pos, length);

		return sb.toString();
	}

    public static String escapeUriString(String uri) throws URISyntaxException
    {
        // URI.toASCIIString() escapes string too.
		return new msUri(uri).getAbsoluteUri();
    }

	/**
	 * Returns local paths for local and LAN files. I.e. returned string is
	 * successfully accepted by File() ctor.
	 *
	 * Doesn't work for remote paths: returns "/ftp.aspose.com/Test.docx" instead of
	 * "/Test.docx" for "ftp:/ftp.aspose.com/Test.docx".
	 */
    public String getLocalPath() throws UnsupportedEncodingException
    {
	    String path = mUri.getPath();
        // URL preserves encoded symbols as is (for instance, space can be encoded as '%20').
        // Formally this is right behaviour (as in .Net), but some users asked to process encoded
        // paths inside INCLUDETEXT field - so let's decode it. (here is the simple case, in rare
        // complex cases a path can be encoded several times and we have to decode in loop)
        path = java.net.URLDecoder.decode(path, "UTF8");

        // .Net removes '/' prefix from returned path string - java doesn't.
        // Let's remove manually the prefix from DOS-like paths '/C:\path'.
        if (path.charAt(0) == '/' && isDosPath(path, 1))
            return path.substring(1);

	    // LAN paths can be split to host + path fields.
        String host = mUri.getHost();
        if (JavaUtil.hasChars(host) && isFile())
        {
            if (JavaUtil.isWindows())
            {
                // Windows allows to specify the host name in a local file path using UNC notation ('\\hostname\path' or '//hostname/path').
                // Unix doesn't allow to specify the host name in a local file path. The host name will be omitted in Unix.
                path = "//" + host + path;
            }
        }

	    return path;
    }

    public String getHost()
    {
        return mUri.getHost();
    }

	public String toString()
	{
		String result = mUri.toString();

		// WORDSJAVA-1802. .NET Url class keeps space symbol (' ') and doesn't replace it
        // with %20 sequence in toString() function. Let's emulate .NET behaviour.
		if (result != null)
            result = result.replaceAll("%20", " ");

        return result;
	}

	public boolean isAbsoluteUri()
	{
		return mUri.isAbsolute();
	}

	public String getAbsoluteUri()
	{
		return mUri.toASCIIString();
	}

	public String getScheme()
	{
		return mUri.getScheme();
	}

    public boolean isFile()
    {
		return "file".equals(mUri.getScheme());
	}

	/**
	 * Returns true for Windows LAN paths (UNC) without scheme prefix (like '//KRAFT/Public/test.doc').
	 */
	private boolean isUncPath(String path)
	{
        return path.length() > 1 ?
                ((isSlash(path, 0) && isSlash(path, 1)) && !path.contains(":")) :
                (isSlash(path, 0) && !path.contains(":"));
	}

    /**
     * Returns true for paths without scheme prefix that contains a drive specifier consists of the drive letter
     * followed by ":" and possibly followed by "\" (like 'C:\Temp\test.doc' or 'C|\Temp\test.doc').
     */
	private boolean isDosPath(String str)
	{
		return str.length() > 2 &&
		       Character.isLetter(str.charAt(0)) &&
               (str.charAt(1) == ':' || str.charAt(1) == '|' ) &&
		       isSlash(str, 2);
	}

    /**
     * Returns true for paths without scheme prefix that contains a drive specifier consists of the drive letter
     * followed by ":" and possibly followed by "\" (like 'C:\Temp\test.doc' or 'C|\Temp\test.doc').
     */
	private static boolean isDosPath(String path, int index)
	{
		return path.length() - index > 2 &&
		       Character.isLetter(path.charAt(index)) &&
               (path.charAt(++index) == ':' || path.charAt(index) == '|' ) &&
		       isSlash(path, ++index);
	}

	private static boolean isSlash(String str, int index)
	{
		return str.charAt(index) == '\\' || str.charAt(index) == '/';
	}

	// Returns java.net.URL instance
	public URL toJavaUrl() throws MalformedURLException
	{
		return mUri.toURL();
	}

	// FIX WORDSJAVA-852: Relative hyperlink with Unicode is improperly saved to Pdf.
	// Use URI instead of URL to work with paths like "~parts~/Body/image1.jpg".
	private JavaURI mUri;
}

