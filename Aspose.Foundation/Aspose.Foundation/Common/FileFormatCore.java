// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2012 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Helper class with operations over <see cref="FileFormat"/>.
///
/// For now, provides conversion to/from:
/// 1. IANA Content Type;
/// 2. Standard file extensions.
/// </summary>
public /*static*/ class FileFormatCore
{
	/* Simulation of static class by using private constructor */
	private FileFormatCore()
	{}

    /// <summary>
    /// Converts IANA Content Type to <see cref="FileFormat"/>.
    /// </summary>
    public static /*FileFormat*/int fromContentType(String contentType)
    {
        if (!StringUtil.hasChars(contentType))
            return FileFormat.UNKNOWN;

        return G_FROM_CONTENT_TYPE.getSafe(contentType, FileFormat.UNKNOWN);
    }

    /// <summary>
    /// Converts file extension to <see cref="FileFormat"/>.
    /// </summary>
    public static /*FileFormat*/int fromExt(String extension)
    {
        if (!StringUtil.hasChars(extension))
            return FileFormat.UNKNOWN;

        if (msString.startsWith(extension, ".", StringComparison.ORDINAL))
            extension = extension.substring(1);

        return G_FROM_EXTENSION.getSafe(extension, FileFormat.UNKNOWN);
    }

    /// <summary>
    /// Converts <see cref="FileFormat"/> to IANA Content Type.
    /// </summary>
    public static String toContentType(/*FileFormat*/int fileFormat)
    {
        String contentType = G_TO_CONTENT_TYPE.get((int)fileFormat);
        return contentType;
    }

    /// <summary>
    /// Converts <see cref="FileFormat"/> to standard file extension.
    /// </summary>
    public static String toExt(/*FileFormat*/int fileFormat)
    {
        String extension = G_TO_EXTENSION.get((int)fileFormat);
        return extension;
    }

    /// <summary>
    /// Used instead of enum.ToString() that is prohibited by Aspose.Auckland practics.
    /// </summary>
    public static String toString(/*FileFormat*/int fileFormat)
    {
        String name = G_TO_STRING.get((int)fileFormat);
        Debug.assertTrue(StringUtil.hasChars(name));
        return name;
    }

    private static void initMap(/*FileFormat*/int fileFormat, String name, String contentType, String ext)
    {
        if (!G_TO_STRING.containsKey((int)fileFormat))
            G_TO_STRING.add((int)fileFormat, name);

        // Null Content Type is used to define several extensions for a file format.
        if (contentType != null)
        {
            if (!G_TO_CONTENT_TYPE.containsKey((int)fileFormat))
                G_TO_CONTENT_TYPE.add((int)fileFormat, contentType);

            // First appearance of a content type in the list defines conversion to file format.
            if (!G_FROM_CONTENT_TYPE.containsKey(contentType))
                G_FROM_CONTENT_TYPE.add(contentType, fileFormat);
        }

        if (ext != null)
        {
            // First appearance of a file format in the list defines conversion to extension.
            if (!G_TO_EXTENSION.containsKey((int)fileFormat))
                G_TO_EXTENSION.add((int)fileFormat, ext);

            // First appearance of an extension in the list define conversion to file format.
            if (!G_FROM_EXTENSION.containsKey(ext))
                G_FROM_EXTENSION.add(ext, fileFormat);
        }
    }

    private static /*final*/ SortedIntegerListGeneric<String> G_TO_STRING = new SortedIntegerListGeneric<String>(50);
    private static /*final*/ SortedStringListGeneric<Integer> G_FROM_CONTENT_TYPE = new SortedStringListGeneric<Integer>(false);
    private static /*final*/ SortedStringListGeneric<Integer> G_FROM_EXTENSION = new SortedStringListGeneric<Integer>(false);

    private static /*final*/ SortedIntegerListGeneric<String> G_TO_CONTENT_TYPE = new SortedIntegerListGeneric<String>();
    private static /*final*/ SortedIntegerListGeneric<String> G_TO_EXTENSION = new SortedIntegerListGeneric<String>();

    static/* FileFormatCore()*/
    {
        // WORDSNET-22697 We can't associate the 'xml' file extension with the XML file format,
        // because WordML documents use that extension too.
        initMap(FileFormat.XML, "Xml", "text/xml", null);
        initMap(FileFormat.XML, "Xml", "application/xml", null);
        initMap(FileFormat.TIFF, "Tiff", "image/tiff", "tif");
        initMap(FileFormat.TIFF, "Tiff", null, "tiff");
        initMap(FileFormat.BMP, "Bmp", "image/bmp", "bmp");
        initMap(FileFormat.PNG, "Png", "image/png", "png");
        initMap(FileFormat.JPEG, "Jpeg", "image/jpeg", "jpeg");
        initMap(FileFormat.JPEG, "Jpeg", null, "jpg");
        initMap(FileFormat.EMF, "Emf", "image/x-emf", "emf");
        initMap(FileFormat.WMF, "Wmf", "image/x-wmf", "wmf");
        initMap(FileFormat.PICT, "Pict", "image/x-pict", "pict");
        initMap(FileFormat.PICT, "Pict", null, "pct");
        initMap(FileFormat.GIF, "Gif", "image/gif", "gif");
        initMap(FileFormat.WEB_P, "WebP", "image/webp", "webp");

        initMap(FileFormat.XPS, "Xps", "application/vnd.ms-xpsdocument", "xps");
        initMap(FileFormat.OPEN_XPS, "Oxps", "application/oxps", "oxps");
        initMap(FileFormat.PDF, "Pdf", "application/pdf", "pdf");
        initMap(FileFormat.PS, "Ps", "application/postscript", "ps");
        initMap(FileFormat.EPS, "Eps", "image/x-eps", "eps");
        initMap(FileFormat.PCL, "Pcl", "application/pcl", "pcl");
        initMap(FileFormat.SVG, "Svg", "image/svg+xml", "svg");
        initMap(FileFormat.EPUB, "Epub", "application/epub+zip", "epub");
        initMap(FileFormat.AZW_3, "Azw3", "application/x-mobi8-ebook", "azw3");
        initMap(FileFormat.HTML, "Html", "text/html", "html");
        initMap(FileFormat.HTML, "Html", null, "htm");
        initMap(FileFormat.HTML, "Html", null, "xhtml");
        initMap(FileFormat.HTML_FIXED, "HtmlFixed", null, "html");
        initMap(FileFormat.TXT, "Txt", "text/plain", "txt");

        initMap(FileFormat.MARKDOWN, "Md", "text/markdown", "md");
        initMap(FileFormat.MARKDOWN, "Md", null, "markdown");
        initMap(FileFormat.MARKDOWN, "Md", null, "markdn");
        initMap(FileFormat.MARKDOWN, "Md", null, "mdown");
        initMap(FileFormat.MARKDOWN, "Md", null, "mdwn");
        initMap(FileFormat.MARKDOWN, "Md", null, "mkdn");
        initMap(FileFormat.MARKDOWN, "Md", null, "mkd");

        initMap(FileFormat.DOC, "Doc", "application/msword", "doc");
        initMap(FileFormat.DOT, "Dot", "application/msword", "dot");
        initMap(FileFormat.DOC_PRE_WORD_97, "DocPreWord97", null, "doc");
        initMap(FileFormat.RTF, "Rtf", "application/rtf", "rtf");
        initMap(FileFormat.WORD_ML, "WordML", "text/xml", "wml");
        initMap(FileFormat.WORD_ML, "WordML", null, "wordml");
        initMap(FileFormat.WORD_ML, "WordML", null, "xml");
        initMap(FileFormat.MHTML, "Mhtml", "multipart/related", "mht");
        initMap(FileFormat.MHTML, "Mhtml", null, "mhtml");
        initMap(FileFormat.MHTML, "Mhtml", null, "mhtm");
        initMap(FileFormat.MHTML, "Mhtml", null, "msg");
        initMap(FileFormat.MHTML, "Mhtml", null, "eml");

        initMap(FileFormat.MOBI, "Mobi", "application/x-mobipocket-ebook", "mobi");
        initMap(FileFormat.MOBI, "Mobi", null, "prc");

        initMap(FileFormat.CHM, "Chm", "application/vnd.ms-htmlhelp", "chm");

        initMap(FileFormat.DOCX, "Docx",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
        initMap(FileFormat.DOCM, "Docm", "application/vnd.ms-word.document.macroEnabled.12", "docm");
        initMap(FileFormat.DOTX, "Dotx",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.template", "dotx");
        initMap(FileFormat.DOTM, "Dotm", "application/vnd.ms-word.template.macroEnabled.12", "dotm");
        initMap(FileFormat.ODT, "Odt", "application/vnd.oasis.opendocument.text", "odt");
        initMap(FileFormat.OTT, "Ott", "application/vnd.oasis.opendocument.text-template", "ott");

        // AM. I'm not sure about .fopc extension here. Word uses .xml for any FlatOpc.
        initMap(FileFormat.FLAT_OPC, "FlatOpc",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document+xml", "fopc");
        initMap(FileFormat.FLAT_OPC_MACRO_ENABLED, "FlatOpcMacroEnabled",
            "application/vnd.ms-word.document.macroEnabled.main+xml", "fopc");
        initMap(FileFormat.FLAT_OPC_TEMPLATE, "FlatOpcTemplate",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml", "fopc");
        initMap(FileFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED, "FlatOpcTemplateMacroEnabled",
            "application/vnd.ms-word.template.macroEnabledTemplate.main+xml", "fopc");

        initMap(FileFormat.XAML_FLOW, "XamlFlow", "application/xaml+xml", "xaml");
        initMap(FileFormat.XAML_FLOW_PACK, "XamlFlowPack", "application/xaml+xml", "xamlpack");
        initMap(FileFormat.XAML_FIXED, "XamlFixed", "application/xml", "xaml");

        initMap(FileFormat.MOV, "QuickTimeMovie", "application/movie", "mov");
        initMap(FileFormat.ICO, "Ico", "image/x-icon", "ico");
        initMap(FileFormat.ODTTF, "Odttf", "application/vnd.openxmlformats-officedocument.obfuscatedFont", "odttf");

        initMap(FileFormat.XLSX, "Xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");

        initMap(FileFormat.MS_WORKS, "Wps", null, "wps");

        initMap(FileFormat.DOCLING, "Docling", "application/json", "json");
    }
}

