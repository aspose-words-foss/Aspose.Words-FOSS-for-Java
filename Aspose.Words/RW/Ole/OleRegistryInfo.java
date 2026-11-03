// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.StringToObjDictionary;
import java.util.HashMap;


/// <summary>
/// Windows Registry substitute. Holds OLE related information.
/// </summary>
class OleRegistryInfo
{
    private OleRegistryInfo(String fileExt, String clsId, String progId, String userType, String iconName, /*OleEmbeddingType*/int embeddingType)
    {
        ProgId = progId;
        ClsId = clsId;
        UserType = userType;
        FileExt = fileExt;
        IconName = iconName;
        EmbeddingType = embeddingType;
    }

    /// <summary>
    /// Returns OLE information by given ProgId value. If no information found returns OLE Packager record.
    /// </summary>
    static OleRegistryInfo getByProgId(String progId)
    {
        OleRegistryInfo oleInfo = G_MAP_BY_PROG_ID.get(progId);
        if (oleInfo == null)
            oleInfo = getOlePackage();

        return oleInfo;
    }

    /// <summary>
    /// Returns OLE information by given file extension. If no information found returns OLE Packager record.
    /// </summary>
    static OleRegistryInfo getByFileExt(String fileExt)
    {
        OleRegistryInfo oleInfo = G_MAP_BY_FILE_EXT.get(fileExt);
        if (oleInfo == null)
            oleInfo = getOlePackage();

        return oleInfo;
    }

    /// <summary>
    /// Returns OLE information by given ClsId. If no information found returns OLE Packager record.
    /// </summary>
    static OleRegistryInfo getByClsId(Guid clsId)
    {
        String formattedClsId = msString.format("{{{0}}}", msString.toUpperInvariant(clsId.toString()));
        OleRegistryInfo oleInfo = G_MAP_BY_CLS_ID.get(formattedClsId);

        return (oleInfo == null)
            ? getOlePackage()
            : oleInfo;
    }

    static OleRegistryInfo getOlePackage()
    {
        return G_PACKAGE;
    }

    /// <summary>
    /// Gets ClsId for embedded document type (ProgId). Returns string of empty Guid if given progId is not found.
    /// </summary>
    /// <remarks>
    /// This is used during document roundtrip and does not return OLE Packager record.
    /// Instead it returns empty ClsId which is written if information about OLE object is missed in our database.
    /// </remarks>
    static String getClsId(String progId)
    {
        OleRegistryInfo oleInfo = G_MAP_BY_PROG_ID.get(progId);

        return (oleInfo != null) ? oleInfo.ClsId : Guid.Empty.toString();
    }

    /// <summary>
    /// Gets default mapped extension by ClsID.
    /// </summary>
    static String getExtensionByClsId(Guid guid)
    {
        String clsId = msString.format("{{{0}}}", guid.toString().toLowerCase());

        if (G_MAP_EXT_BY_CLS_ID.containsKey(clsId))
            return (G_MAP_EXT_BY_CLS_ID.get(clsId));

        return ".bin";
    }

    private static void add(String clsId, String progId, String userType)
    {
        add(null, clsId, progId, userType, null, OleEmbeddingType.OLE_1_PACKAGE);
    }

    private static void add(String fileExt, String clsId, String progId, String userType, String iconName, /*OleEmbeddingType*/int type)
    {
        Debug.assertTrue(progId != null);
        Debug.assertTrue(clsId != null);

        OleRegistryInfo oleInfo = new OleRegistryInfo(fileExt, clsId, progId, userType, iconName, type);
        msArrayList.add(G_OLE_INFOS, oleInfo);

        // File extension can be null for certain entries.
        // This means that the OLE object can't be saved to file so we should not map them to file extension.
        if (fileExt != null)
        {
            G_MAP_BY_FILE_EXT.add(fileExt, oleInfo);

            // One ClsId can be used for different extensions so we use only the first one.
            String id = clsId.toLowerCase();
            if (!G_MAP_EXT_BY_CLS_ID.containsKey(id))
                msDictionary.add(G_MAP_EXT_BY_CLS_ID, id, fileExt);
        }

        G_MAP_BY_PROG_ID.add(progId, oleInfo);
        if (!G_MAP_BY_CLS_ID.containsKey(clsId))
            G_MAP_BY_CLS_ID.add(clsId, oleInfo);
    }

    /*final*/ String ProgId;
    /*final*/ String ClsId;
    /*final*/ String UserType;
    /*final*/ String FileExt;
    /*final*/ String IconName;
    /*final*/ /*OleEmbeddingType*/int EmbeddingType;

    static final String PACKAGE_CLS_ID = "0003000c-0000-0000-c000-000000000046";

    private static /*final*/ ArrayList<OleRegistryInfo> G_OLE_INFOS;

    private static /*final*/ StringToObjDictionary<OleRegistryInfo> G_MAP_BY_FILE_EXT =
        new StringToObjDictionary<OleRegistryInfo>();
    private static /*final*/ StringToObjDictionary<OleRegistryInfo> G_MAP_BY_PROG_ID =
        new StringToObjDictionary<OleRegistryInfo>();
    private static /*final*/ StringToObjDictionary<OleRegistryInfo> G_MAP_BY_CLS_ID =
        new StringToObjDictionary<OleRegistryInfo>();
    private static /*final*/ HashMap<String, String> G_MAP_EXT_BY_CLS_ID =
        new HashMap<String, String>();

    private static /*final*/ OleRegistryInfo G_PACKAGE;

    static/* OleRegistryInfo()*/
    {
        G_OLE_INFOS = new ArrayList<OleRegistryInfo>();

        // When searching through clsIDs when no ProgID provided only the first record will be chosen.
        // So the order of added pairs "extension/{clsid} for duplicating clsIDs is important.

        // Word.12
        add(".docx", "{F4754C9B-64F5-4B40-8AF4-679732AC0607}", "Word.Document.12", "Microsoft Word Document", "docx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".dotx", "{912ABC52-36E2-4714-8E62-A8B73CA5E390}", "Word.Template.12", "Microsoft Word Template", "dotx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".docm", "{18A06B6B-2F3F-4E2B-A611-52BE631B2D22}", "Word.DocumentMacroEnabled.12", "Microsoft Word Macro-Enabled Document", "docm", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".dotm", "{8A624388-AA27-43E0-89F8-2A12BFF7BCCD}", "Word.TemplateMacroEnabled.12", "Microsoft Word Macro-Enabled Template", "dotm", OleEmbeddingType.OLE_2_STREAM_PACKAGE);

        // Word.8
        add(".doc", "{00020906-0000-0000-C000-000000000046}", "Word.Document.8", "Microsoft Word 97 - 2003 Document", "doc", OleEmbeddingType.OLE_2_STORAGE);
        add(".dot", "{00020906-0000-0000-C000-000000000046}", "Word.Template.8", "Microsoft Word 97 - 2003 Template", "dot", OleEmbeddingType.OLE_2_STORAGE);

        // Excel.12
        add(".xlsx", "{00020830-0000-0000-C000-000000000046}", "Excel.Sheet.12", "Microsoft Excel Worksheet", "xlsx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".xlsb", "{00020833-0000-0000-C000-000000000046}", "Excel.SheetBinaryMacroEnabled.12", "Microsoft Excel Binary Worksheet", "xlsb", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".xlsm", "{00020832-0000-0000-C000-000000000046}", "Excel.SheetMacroEnabled.12", "Microsoft Excel Macro-Enabled Worksheet", "xlsm", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".xltx", "{00020830-0000-0000-C000-000000000046}", "Excel.Template", "Microsoft Excel Template", "xltx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".xltm", "{00020832-0000-0000-C000-000000000046}", "Excel.TemplateMacroEnabled", "Microsoft Excel Macro-Enabled Template", "xltm", OleEmbeddingType.OLE_2_STREAM_PACKAGE);

        // Excel.8
        add(".xls", "{00020820-0000-0000-C000-000000000046}", "Excel.Sheet.8", "Microsoft Excel 97-2003 Worksheet", "xls", OleEmbeddingType.OLE_2_STORAGE);
        add(".xlt", "{00020820-0000-0000-C000-000000000046}", "Excel.Template.8", "Microsoft Excel Template", "xlt", OleEmbeddingType.OLE_2_STORAGE);

        // OpenDocument
        add(".odp", "{C282417B-2662-44B8-8A94-3BFF61C50900}", "PowerPoint.OpenDocumentPresentation.12", "OpenDocument Presentation", "odp", OleEmbeddingType.OLE_2_STREAM_EMBEDDED_ODF);
        add(".ods", "{EABCECDB-CC1C-4A6F-B4E3-7F888A5ADFC8}", "Excel.OpenDocumentSpreadsheet.12", "OpenDocument Spreadsheet", "ods", OleEmbeddingType.OLE_2_STREAM_EMBEDDED_ODF);
        add(".odt", "{1B261B22-AC6A-4E68-A870-AB5080E8687B}", "Word.OpenDocumentText.12", "OpenDocument Text", "odt", OleEmbeddingType.OLE_2_STREAM_EMBEDDED_ODF);

        // PowerPoint.12
        add(".potx", "{75D01070-1234-44E9-82F6-DB5B39A47C13}", "PowerPoint.Template.12", "Microsoft PowerPoint Template", "potx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".potm", "{AA14F9C9-62B5-4637-8AC4-8F25BF29D5A7}", "PowerPoint.TemplateMacroEnabled.12", "Microsoft PowerPoint Macro-Enabled Design Template", "potm",
            OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".pptx", "{CF4F55F4-8F87-4D47-80BB-5808164BB3F8}", "PowerPoint.Show.12", "Microsoft PowerPoint Presentation", "pptx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".pptm", "{DC020317-E6E2-4A62-B9FA-B3EFE16626F4}", "PowerPoint.ShowMacroEnabled.12", "Microsoft PowerPoint Macro-Enabled Presentation", "pptm", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".ppsx", "{CF4F55F4-8F87-4D47-80BB-5808164BB3F8}", "PowerPoint.SlideShow.12", "Microsoft PowerPoint Slide Show", "ppsx", OleEmbeddingType.OLE_2_STREAM_PACKAGE);
        add(".ppsm", "{DC020317-E6E2-4A62-B9FA-B3EFE16626F4}", "PowerPoint.SlideShowMacroEnabled.12", "Microsoft PowerPoint Macro-Enabled Slide Show", "ppsm", OleEmbeddingType.OLE_2_STREAM_PACKAGE);

        // AM Couldn't find how to get this file so use OLE Packager embedding mode for a while.
        add(".sldx", "{048EB43E-2059-422F-95E0-557DA96038AF}", "PowerPoint.Slide.12", "Microsoft PowerPoint Slide", "sldx", OleEmbeddingType.OLE_1_PACKAGE);
        add(".sldm", "{3C18EAE4-BC25-4134-B7DF-1ECA1337DDDC}", "PowerPoint.SlideMacroEnabled.12", "Microsoft PowerPoint Macro-Enabled Slide", "sldx", OleEmbeddingType.OLE_1_PACKAGE);

        // PowerPoint.8
        add(".ppt", "{64818D10-4F9B-11CF-86EA-00AA00B929E8}", "PowerPoint.Show.8", "Microsoft PowerPoint 97-2003 Presentation", "ppt", OleEmbeddingType.OLE_2_STORAGE);
        add(".pot", "{64818D11-4F9B-11CF-86EA-00AA00B929E8}", "PowerPoint.Template.8", "Microsoft PowerPoint 97-2003 Template", "pot", OleEmbeddingType.OLE_2_STORAGE);
        add(".pps", "{64818D10-4F9B-11CF-86EA-00AA00B929E8}", "PowerPoint.SlideShow.8", "Microsoft PowerPoint 97-2003 Slide Show", "pps", OleEmbeddingType.OLE_2_STORAGE);

        // PDF
        add(".pdf", "{B801CA65-A1FC-11D0-85AD-444553540000}", "AcroExch.Document.7", "Adobe Acrobat Document", "pdf", OleEmbeddingType.OLE_2_STREAM_CONTENTS);

        //AutoCad
        add(".dwg", "{6a221957-2d85-42a7-8e19-be33950d1deb}", "AutoCAD.Drawing.19", "Autodesk AutoCAD Document", null, OleEmbeddingType.OLE_2_STREAM_CONTENTS);

        // BMP
        add(".bmp", "{0003000A-0000-0000-C000-000000000046}", "PBrush", "Bitmap Image", "bmp", OleEmbeddingType.OLE_1_NATIVE_STREAM);

        // Visio
        add(".vsd", "{00021A14-0000-0000-C000-000000000046}", "Visio.Drawing.11", "Microsoft Visio Document", "vsd", OleEmbeddingType.OLE_2_STORAGE);
        add(".vsdx", "{00021a15-0000-0000-c000-000000000046}", "Visio.Drawing.15", "Microsoft Visio Document", "vsd", OleEmbeddingType.OLE_2_STREAM_PACKAGE);

        // MS Project
        add(".mpp", "{74b78f3a-c8c8-11d1-be11-00c04fb6faf1}", "MSProject.Project.9", "Microsoft Project 9.0", "mpp", OleEmbeddingType.OLE_2_STORAGE);

        add(".html", "{25336920-03F9-11CF-8FD0-00AA00686F13}", "htmlfile", "HTML Document", "html", OleEmbeddingType.OLE_1_PACKAGE);

        // Equation.3
        add(null, "{0002ce02-0000-0000-c000-000000000046}", "Equation.3", "Microsoft Equation 3.0", null, OleEmbeddingType.OLE_2_STREAM_EQUATION_NATIVE);

        add(null, "{e4c18d40-1cd5-101c-b325-00aa001f3168}", "OrgPlusWOPX.4", "Organization Chart Add-in for Microsoft Office", "xls", OleEmbeddingType.OLE_2_STORAGE);

        // Below object either can't be saved to file or suspicious to be inserted from file.
        // For example I think we can't distinguish between Excel.Sheet.5 and Excel.Sheet.8, because they both have .xls file extension.
        // Still they can be inserted from stream using progId.
        add(null, "{64818D11-4F9B-11CF-86EA-00AA00B929E8}", "PowerPoint.Slide.8", "Microsoft PowerPoint 97-2003 Slide", "ppt", OleEmbeddingType.OLE_2_STORAGE);
        add("{00030007-0000-0000-C000-000000000046}", "MSDraw", "Microsoft Word Picture");
        add("{0003000B-0000-0000-C000-000000000046}", "Equation", "Microsoft Equation 2.0");
        add("{0003000D-0000-0000-C000-000000000046}", "SoundRec", "");
        add("{0003000E-0000-0000-C000-000000000046}", "MPlayer", "");
        add("{0004c8d8-0000-0000-c000-000000000046}", "WPGraphic21", "");
        add("{EA7BAE70-FB3B-11CD-A903-00AA00510EA3}", "PowerPoint.Show.7", "");
        add("{EA7BAE71-FB3B-11CD-A903-00AA00510EA3}", "PowerPoint.Slide.7", "");
        add("{64818D10-4F9B-11CF-86EA-00AA00B929E8}", "PowerPoint.Wizard.8", "");
        add("{00020900-0000-0000-C000-000000000046}", "Word.Document.6", "");
        add("{00020901-0000-0000-C000-000000000046}", "Word.Picture.6", "");
        add(null, "{00020907-0000-0000-C000-000000000046}", "Word.Picture.8", "Image Microsoft Office Word", null, OleEmbeddingType.OLE_2_STORAGE);
        add("{00020906-0000-0000-C000-000000000046}", "Word.RTF.8", "");
        add("{00024500-0000-0000-C000-000000000046}", "Excel.Application.12", "");
        add("{00020811-0000-0000-C000-000000000046}", "Excel.Chart.5", "");
        add(null, "{00020821-0000-0000-C000-000000000046}", "Excel.Chart.8", "Microsoft Excel Chart", "xls", OleEmbeddingType.OLE_2_STORAGE);
        add("{00020832-0000-0000-C000-000000000046}", "Excel.CSV", "");
        add("{00020810-0000-0000-C000-000000000046}", "Excel.Sheet.5", "");
        add("{D3E34B21-9D75-101A-8C3D-00AA001A1652}", "Paint.Picture", "");
        add("{448BB771-CFE2-47C4-BCDF-1FBF378E202C}", "opendocument.DrawDocument.1", "");
        add("{73fddc80-aea9-101a-98a7-00aa00374959}", "WordPad.Document.1", "Document WordPad");

        // Special record. It returned if OLE object is unknown.
        G_PACKAGE = new OleRegistryInfo(null, PACKAGE_CLS_ID, "Package", "OLE Package", OleUtil.PACKAGER, OleEmbeddingType.OLE_1_PACKAGE);
        add(G_PACKAGE.FileExt, G_PACKAGE.ClsId, G_PACKAGE.ProgId, G_PACKAGE.UserType, G_PACKAGE.IconName, OleEmbeddingType.OLE_1_PACKAGE);
    }
}

