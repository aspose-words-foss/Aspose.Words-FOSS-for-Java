// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.EnumUtilPal;
import com.aspose.ms.System.Collections.msArrayList;


class DocxNamespaces
{
    DocxNamespaces(boolean isStrict)
    {
        mIsStrict = isStrict;
    }

    /// <summary>
    /// Check this is MS Word 2010 namespace.
    /// </summary>
    static boolean isWord2010Namespace(String nameSpace)
    {
        return G_XMLNS_2010.contains(nameSpace);
    }

    /// <summary>
    /// Checks if this is a MS Word 2016 namespace.
    /// </summary>
    static boolean isWord2016Namespace(String nameSpace)
    {
        return G_XMLNS_2016.contains(nameSpace);
    }

    /// <summary>
    /// Check this is well known MS Word namespace.
    /// </summary>
    static boolean isWellKnownNamespace(String nameSpace)
    {
        return G_WELL_KNOWN_NAMESPACES.contains(nameSpace);
    }

    /// <summary>
    /// Returns Docx namespace by name.
    /// </summary>
    private String getNamespace(/*DocxNamespace*/int docxNamespace)
    {
        return getNamespace(docxNamespace, mIsStrict);
    }

    /// <summary>
    /// Returns Docx namespace by name and conformance type.
    /// </summary>
    static String getNamespace(/*DocxNamespace*/int docxNamespace, boolean isStrict)
    {
        String[] docxNamespaces = isStrict ? G_DOCX_NAMESPACES_STRICT : G_DOCX_NAMESPACES_TRANSITIONAL;
        return docxNamespaces[(int)docxNamespace];
    }

    /// <summary>
    /// Converts ISO Strict namespace URL to corresponding ISO Transitional one. 
    /// If a specified URL is unknown, returns it without changes.
    /// </summary>
    static String toIsoTransitional(String namespaceUrl)
    {
        for (int i = 0; i < G_DOCX_NAMESPACES_STRICT.length; i++)
        {
            if (msString.equals(G_DOCX_NAMESPACES_STRICT[i], namespaceUrl))
                return G_DOCX_NAMESPACES_TRANSITIONAL[i];
        }
        return namespaceUrl;
    }

    /// <summary>
    /// Converts ISO Transitional namespace URL to corresponding ISO Strict one. 
    /// If the specified URL is unknown, returns it without changes.
    /// </summary>
    static String toIsoStrict(String namespaceUrl)
    {
        for (int i = 0; i < G_DOCX_NAMESPACES_TRANSITIONAL.length; i++)
        {
            if (msString.equals(G_DOCX_NAMESPACES_TRANSITIONAL[i], namespaceUrl))
                return G_DOCX_NAMESPACES_STRICT[i];
        }
        return namespaceUrl;
    }

    String getRelationships() { return getNamespace(DocxNamespace.RELATIONSHIPS); }
    String getMain() { return getNamespace(DocxNamespace.MAIN); }
    String getWmlBeta() { return getNamespace(DocxNamespace.WML_BETA); }
    String getSchemaLibrary() { return getNamespace(DocxNamespace.SCHEMA_LIBRARY); }
    String getCustomXml() { return getNamespace(DocxNamespace.CUSTOM_XML); }
    String getDrawingML() { return getNamespace(DocxNamespace.DRAWING_ML); }
    String getDrawingMLMain() { return getNamespace(DocxNamespace.DRAWING_ML_MAIN); }
    String getDrawingMLPicture() { return getNamespace(DocxNamespace.DRAWING_ML_PICTURE); }
    String getDrawingMLDiagram() { return getNamespace(DocxNamespace.DRAWING_ML_DIAGRAM); }
    String getDrawingMLChart() { return getNamespace(DocxNamespace.DRAWING_ML_CHART); }
    String getDrawingMLLockedCanvas() { return getNamespace(DocxNamespace.DRAWING_ML_LOCKED_CANVAS); }
    String getActiveX() { return getNamespace(DocxNamespace.ACTIVE_X); }
    String getDigitalSignature() { return getNamespace(DocxNamespace.DIGITAL_SIGNATURE); }
    String getMicrosoftDigitalSignature() { return getNamespace(DocxNamespace.MICROSOFT_DIGITAL_SIGNATURE); }
    String getMarkupCompatibility() { return getNamespace(DocxNamespace.MARKUP_COMPATIBILITY); }
    String getWordprocessingCanvas() { return getNamespace(DocxNamespace.WORDPROCESSING_CANVAS); }
    String getDrawingMLIso29500() { return getNamespace(DocxNamespace.DRAWING_ML_ISO_29500); }
    String getWordprocessingGroup() { return getNamespace(DocxNamespace.WORDPROCESSING_GROUP); }
    String getWordprocessingInk() { return getNamespace(DocxNamespace.WORDPROCESSING_INK); }
    String getWordrocessingShape() { return getNamespace(DocxNamespace.WORDROCESSING_SHAPE); }
    String getMath() { return getNamespace(DocxNamespace.MATH); }
    String getW14Markup() { return getNamespace(DocxNamespace.W1_4_MARKUP); }
    String getW15Markup() { return getNamespace(DocxNamespace.W1_5_MARKUP); }
    String getChartEx() { return getNamespace(DocxNamespace.DRAWING_ML_CHART_EX); }
    String getChartEx1() { return getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_1); }
    String getChartEx2() { return getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_2); }
    String getChartEx3() { return getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_3); }
    String getChartEx4() { return getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_4); }
    String getChartEx5() { return getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_5); }
    String getW16Symex() { return getNamespace(DocxNamespace.W1_6_SYMEX); }
    String getW16Cid() { return getNamespace(DocxNamespace.W1_6_CID); }
    String getW16Markup() { return getNamespace(DocxNamespace.W1_6_MARKUP); }
    String getW16Cex() { return getNamespace(DocxNamespace.W1_6_CEX); }
    String getW16Sdtdh() { return getNamespace(DocxNamespace.W1_6_SDTDH); }
    String getCommentReaction() { return getNamespace(DocxNamespace.COMMENT_REACTION); }

    private static void initDocxNamespacesTransitional()
    {
        //From package.
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.RELATIONSHIPS] = "http://schemas.openxmlformats.org/officeDocument/2006/relationships";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.MAIN] = "http://schemas.openxmlformats.org/wordprocessingml/2006/main";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.WML_BETA] = "http://schemas.microsoft.com/office/word/2006/wordml";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.SCHEMA_LIBRARY] = "http://schemas.openxmlformats.org/schemaLibrary/2006/main";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.CUSTOM_XML] = "http://schemas.openxmlformats.org/officeDocument/2006/customXml";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML] = "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_MAIN] = "http://schemas.openxmlformats.org/drawingml/2006/main";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_PICTURE] = "http://schemas.openxmlformats.org/drawingml/2006/picture";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_DIAGRAM] = "http://schemas.openxmlformats.org/drawingml/2006/diagram";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART] = "http://schemas.openxmlformats.org/drawingml/2006/chart";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_LOCKED_CANVAS] = "http://schemas.openxmlformats.org/drawingml/2006/lockedCanvas";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.ACTIVE_X] = "http://schemas.microsoft.com/office/2006/activeX";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DIGITAL_SIGNATURE] = "http://schemas.openxmlformats.org/package/2006/digital-signature";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.MICROSOFT_DIGITAL_SIGNATURE] = "http://schemas.microsoft.com/office/2006/digsig";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_DRAWING] = "http://schemas.openxmlformats.org/drawingml/2006/chartDrawing";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.MATH] = NrxNamespaces.MATH;

        // Word 2010 additional namespaces.
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.MARKUP_COMPATIBILITY] = "http://schemas.openxmlformats.org/markup-compatibility/2006";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.WORDPROCESSING_CANVAS] = "http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_ISO_29500] = "http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.WORDPROCESSING_GROUP] = "http://schemas.microsoft.com/office/word/2010/wordprocessingGroup";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.WORDPROCESSING_INK] = "http://schemas.microsoft.com/office/word/2010/wordprocessingInk";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.WORDROCESSING_SHAPE] = "http://schemas.microsoft.com/office/word/2010/wordprocessingShape";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_4_MARKUP] = "http://schemas.microsoft.com/office/word/2010/wordml";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_DIAGRAM_2008] = "http://schemas.microsoft.com/office/drawing/2008/diagram";

        // Word 2012
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_5_MARKUP] = "http://schemas.microsoft.com/office/word/2012/wordml";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_2007] = "http://schemas.microsoft.com/office/drawing/2007/8/2/chart";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_COLOR_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartColorStyle";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartStyle";

        // Word 2016
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_EX] = "http://schemas.microsoft.com/office/drawing/2014/chartex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_EX_1] = "http://schemas.microsoft.com/office/drawing/2015/9/8/chartex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_EX_2] = "http://schemas.microsoft.com/office/drawing/2015/10/21/chartex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_EX_3] = "http://schemas.microsoft.com/office/drawing/2016/5/9/chartex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_EX_4] = "http://schemas.microsoft.com/office/drawing/2016/5/10/chartex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.DRAWING_ML_CHART_EX_5] = "http://schemas.microsoft.com/office/drawing/2016/5/11/chartex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_6_SYMEX] =
            "http://schemas.microsoft.com/office/word/2015/wordml/symex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_6_CID] =
            "http://schemas.microsoft.com/office/word/2016/wordml/cid";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_6_MARKUP] =
            "http://schemas.microsoft.com/office/word/2018/wordml";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_6_CEX] =
            "http://schemas.microsoft.com/office/word/2018/wordml/cex";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.W1_6_SDTDH] =
            "http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.CHART_16_AC] =
            "http://schemas.microsoft.com/office/drawing/2014/chart/ac";
        G_DOCX_NAMESPACES_TRANSITIONAL[(int)DocxNamespace.COMMENT_REACTION] =
            "http://schemas.microsoft.com/office/comments/2020/reactions";
    }

    private static void initDocxNamespacesStrict()
    {
        //From package.
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.RELATIONSHIPS] = "http://purl.oclc.org/ooxml/officeDocument/relationships";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.MAIN] = "http://purl.oclc.org/ooxml/wordprocessingml/main";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.WML_BETA] = "http://schemas.microsoft.com/office/word/2006/wordml";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.SCHEMA_LIBRARY] = "http://schemas.openxmlformats.org/schemaLibrary/2006/main"; // Is the same for strict.
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.CUSTOM_XML] = "http://purl.oclc.org/ooxml/officeDocument/customXml";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML] = "http://purl.oclc.org/ooxml/drawingml/wordprocessingDrawing";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_MAIN] = "http://purl.oclc.org/ooxml/drawingml/main";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_PICTURE] = "http://purl.oclc.org/ooxml/drawingml/picture";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_DIAGRAM] = "http://purl.oclc.org/ooxml/drawingml/diagram";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART] = "http://purl.oclc.org/ooxml/drawingml/chart";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_LOCKED_CANVAS] = "http://purl.oclc.org/ooxml/drawingml/lockedCanvas";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.ACTIVE_X] = "http://schemas.microsoft.com/office/2006/activeX";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DIGITAL_SIGNATURE] = "http://schemas.openxmlformats.org/package/2006/digital-signature"; // Is the same for strict.
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.MICROSOFT_DIGITAL_SIGNATURE] = "http://schemas.microsoft.com/office/2006/digsig"; // Is the same for strict.
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_DRAWING] = "http://purl.oclc.org/ooxml/drawingml/chartDrawing";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.MATH] = "http://purl.oclc.org/ooxml/officeDocument/math";

        // Word 2010 additional namespaces.
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.MARKUP_COMPATIBILITY] = "http://schemas.openxmlformats.org/markup-compatibility/2006";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.WORDPROCESSING_CANVAS] = "http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_ISO_29500] = "http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.WORDPROCESSING_GROUP] = "http://schemas.microsoft.com/office/word/2010/wordprocessingGroup";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.WORDPROCESSING_INK] = "http://schemas.microsoft.com/office/word/2010/wordprocessingInk";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.WORDROCESSING_SHAPE] = "http://schemas.microsoft.com/office/word/2010/wordprocessingShape";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_4_MARKUP] = "http://schemas.microsoft.com/office/word/2010/wordml";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_DIAGRAM_2008] = "http://schemas.microsoft.com/office/drawing/2008/diagram";

        // Word 2012
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_5_MARKUP] = "http://schemas.microsoft.com/office/word/2012/wordml";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_2007] = "http://schemas.microsoft.com/office/drawing/2007/8/2/chart";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_COLOR_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartColorStyle";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_STYLE] = "http://schemas.microsoft.com/office/2011/relationships/chartStyle";

        // Word 2016
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_EX] = "http://schemas.microsoft.com/office/drawing/2014/chartex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_EX_1] = "http://schemas.microsoft.com/office/drawing/2015/9/8/chartex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_EX_2] = "http://schemas.microsoft.com/office/drawing/2015/10/21/chartex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_EX_3] = "http://schemas.microsoft.com/office/drawing/2016/5/9/chartex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_EX_4] = "http://schemas.microsoft.com/office/drawing/2016/5/10/chartex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.DRAWING_ML_CHART_EX_5] = "http://schemas.microsoft.com/office/drawing/2016/5/11/chartex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_6_SYMEX] =
            "http://schemas.microsoft.com/office/word/2015/wordml/symex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_6_CID] =
            "http://schemas.microsoft.com/office/word/2016/wordml/cid";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_6_MARKUP] =
            "http://schemas.microsoft.com/office/word/2018/wordml";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_6_CEX] =
            "http://schemas.microsoft.com/office/word/2018/wordml/cex";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.W1_6_SDTDH] =
            "http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.CHART_16_AC] =
            "http://schemas.microsoft.com/office/drawing/2014/chart/ac";
        G_DOCX_NAMESPACES_STRICT[(int)DocxNamespace.COMMENT_REACTION] =
            "http://schemas.microsoft.com/office/comments/2020/reactions";
    }

    private /*final*/ boolean mIsStrict;
    private static /*final*/ ArrayList<String> G_XMLNS_2010 = new ArrayList<String>();
    private static /*final*/ ArrayList<String> G_XMLNS_2016 = new ArrayList<String>();
    private static /*final*/ ArrayList<String> G_WELL_KNOWN_NAMESPACES = new ArrayList<String>();
    private static /*final*/ String[] G_DOCX_NAMESPACES_STRICT;
    private static /*final*/ String[] G_DOCX_NAMESPACES_TRANSITIONAL;
    
    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* DocxNamespaces()*/
    {
        int docxNamespaceCount = EnumUtilPal.getEffectiveArrayLength((41));

        G_DOCX_NAMESPACES_STRICT = new String[docxNamespaceCount];
        initDocxNamespacesStrict();

        G_DOCX_NAMESPACES_TRANSITIONAL = new String[docxNamespaceCount];
        initDocxNamespacesTransitional();

        // Populate gXmlns2010 array list with Word 2010 namespaces, which we are using to check 
        // whether Choice can be matched upon reading AlternateContent.
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.WORDPROCESSING_CANVAS, false));
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.DRAWING_ML_ISO_29500, false));
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.W1_4_MARKUP, false));
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.WORDPROCESSING_GROUP, false));
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.WORDPROCESSING_INK, false));
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.WORDROCESSING_SHAPE, false));
        msArrayList.add(G_XMLNS_2010, getNamespace(DocxNamespace.MARKUP_COMPATIBILITY, false));

        // The gXmlns2016 list contains Word 2016 namespaces and is used to check 
        // whether Choice can be matched upon reading AlternateContent.
        msArrayList.add(G_XMLNS_2016, getNamespace(DocxNamespace.DRAWING_ML_CHART_EX, false));
        msArrayList.add(G_XMLNS_2016, getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_1, false));
        msArrayList.add(G_XMLNS_2016, getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_2, false));
        msArrayList.add(G_XMLNS_2016, getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_3, false));
        msArrayList.add(G_XMLNS_2016, getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_4, false));
        msArrayList.add(G_XMLNS_2016, getNamespace(DocxNamespace.DRAWING_ML_CHART_EX_5, false));

        msArrayList.add(G_WELL_KNOWN_NAMESPACES, NrxNamespaces.VML);
        msArrayList.add(G_WELL_KNOWN_NAMESPACES, NrxNamespaces.OFFICE);
        msArrayList.add(G_WELL_KNOWN_NAMESPACES, NrxNamespaces.WORD);
        msArrayList.add(G_WELL_KNOWN_NAMESPACES, NrxNamespaces.MATH);
    }
}

