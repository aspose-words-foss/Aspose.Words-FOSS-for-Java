// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Path;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Implements conversion of web formats (SVG, HTML, etc. anything that Chrome can open) to Png files.
/// </summary>
/*static*/ class WebToPngClient
{
	/* Simulation of static class by using private constructor */
	private WebToPngClient()
	{}

    /// <summary>
    /// Default path to the server executable.
    /// You can override this using <see cref="ENVAR"/> environment variable.
    /// </summary>
    static final String SERVER = "x:/awnet/aspose.foundation/tools/imageservices/bin/aspose.webtopng.exe";
    static /*final*/ String PIPE = com.aspose.ms.System.IO.Path.getFileNameWithoutExtension(SERVER) + ".pipe";
    static /*final*/ String MUTEX = com.aspose.ms.System.IO.Path.getFileNameWithoutExtension(SERVER) + ".mutex";
    static /*final*/ String ENVAR = com.aspose.ms.System.IO.Path.getFileNameWithoutExtension(SERVER) + ".path";

    /// <summary>
    /// Converts <paramref name="sourceFiles"/> into Png images in <paramref name="outputFolder"/>
    /// </summary>
    static void convert(String outputFolder, String[] sourceFiles) throws Exception
    {
        if (sourceFiles.length <= 0)
            throw new IllegalArgumentException("sourceFiles");

        ArrayList<String> p = msArrayList.ctor(sourceFiles);
        p.add(0, "-o" + outputFolder);
        ClientUtil.postServerJobGetResults(MUTEX, PIPE, SERVER, ENVAR, msArrayList.toArray(p, new String[0]));
    }

    /// <summary>
    /// Returns file name which would be used for saving image of the uri.
    ///
    /// NOTE This method can generate same file name for different uri where only punctuation is different.
    /// NOTE This method does not verify that Uri is valid and absolute, call <see cref="IsValidAbsoluteUri(string)"/> beforehand.
    /// </summary>
    /// <param name="uri">A valid absolute uri of the resource.</param>
    /// <returns>A file name</returns>
    static String getOutputFileName(String uri)
    {
        // source was validated when parameters were parsed
        Match m = _URI_SCHEME.match(uri);
        if (m.getSuccess())
            uri = uri.substring(m.getLength());

        uri = UriUtil.unescapeHref(uri);

        final String VALID_CHARS = "!\"#$%'()+,-.;=@_`{}~";
        StringBuilder sb = new StringBuilder(uri);
        for (int i = 0; i < uri.length(); i++)
        {
        	char c = uri.charAt(i);
            if (Character.isLetterOrDigit(c) || VALID_CHARS.indexOf(c) >= 0)
                sb.append(c);
            else
                sb.append('_');
        }
        msStringBuilder.append(sb, ".PNG");
        return sb.toString();
    }

    private static /*final*/ Regex _URI_SCHEME = new Regex("^[a-z]+://", RegexOptions.COMPILED | RegexOptions.SINGLELINE | RegexOptions.IGNORE_CASE);
}

