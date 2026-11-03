// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.WarningTypeCore;
import com.aspose.WarningSourceCore;


/// <summary>
/// Contains information about a warning that Aspose.Words issued during document loading or saving.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not create instances of this class. Objects of this class are created
/// and passed by Aspose.Words to the <see cref="IWarningCallback.Warning"/> method.</para>
///
/// <seealso cref="IWarningCallback"/>
/// </remarks>
public class WarningInfo
{
    /// <summary>
    /// No public ctor.
    /// </summary>
    WarningInfo(/*WarningType*/int warningType, /*WarningSource*/int source, String description)
    {
        mWarningType = warningType;
        mSource = source;
        mDescription = description;
    }

    static /*WarningType*/int convertWarningTypeCoreToWarningType(/*WarningTypeCore*/int warningTypeCore)
    {
        switch (warningTypeCore)
        {
            case WarningTypeCore.DATA_LOSS:
                return WarningType.DATA_LOSS;
            case WarningTypeCore.MAJOR_FORMATTING_LOSS:
                return WarningType.MAJOR_FORMATTING_LOSS;
            case WarningTypeCore.MINOR_FORMATTING_LOSS:
                return WarningType.MINOR_FORMATTING_LOSS;
            case WarningTypeCore.FONT_SUBSTITUTION:
                return WarningType.FONT_SUBSTITUTION;
            case WarningTypeCore.UNEXPECTED_CONTENT:
                return WarningType.UNEXPECTED_CONTENT;
            default:
                return WarningType.MINOR_FORMATTING_LOSS;
        }
    }

    static /*WarningSource*/int convertWarningSourceCoreToWarningSource(/*WarningSourceCore*/int warningSourceCore)
    {
        switch (warningSourceCore)
        {
            case WarningSourceCore.SHAPES:
                return WarningSource.SHAPES;
            case WarningSourceCore.DRAWING_ML:
                return WarningSource.DRAWING_ML;
            case WarningSourceCore.METAFILE:
                return WarningSource.METAFILE;
            case WarningSourceCore.XPS:
                return WarningSource.XPS;
            case WarningSourceCore.PDF:
                return WarningSource.PDF;
            case WarningSourceCore.IMAGE:
                return WarningSource.IMAGE;
            case WarningSourceCore.SVM:
                return WarningSource.SVM;
            case WarningSourceCore.FONT:
                return WarningSource.FONT;
            default:
                return WarningSource.UNKNOWN;
        }
    }

    static String warningSourceToString(/*WarningSource*/int warningSource)
    {
        switch (warningSource)
        {
            case WarningSource.UNKNOWN:
                return "Unknown";
            case WarningSource.LAYOUT:
                return "Layout";
            case WarningSource.DRAWING_ML:
                return "DrawingML";
            case WarningSource.OFFICE_MATH:
                return "OfficeMath";
            case WarningSource.SHAPES:
                return "Shapes";
            case WarningSource.METAFILE:
                return "Metafile";
            case WarningSource.XPS:
                return "Xps";
            case WarningSource.PDF:
                return "Pdf";
            case WarningSource.IMAGE:
                return "Image";
            case WarningSource.DOCX:
                return "Docx";
            case WarningSource.DOC:
                return "Doc";
            case WarningSource.TEXT:
                return "Text";
            case WarningSource.RTF:
                return "Rtf";
            case WarningSource.WORD_ML:
                return "WordML";
            case WarningSource.NRX:
                return "Nrx";
            case WarningSource.ODT:
                return "Odt";
            case WarningSource.HTML:
                return "Html";
            case WarningSource.SVM:
                return "Svm";
            case WarningSource.FONT:
                return "Font";
            case WarningSource.SVG:
                return "Svg";
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "warningSource");
        }
    }

    /// <summary>
    /// Returns the type of the warning.
    /// </summary>
    public /*WarningType*/int getWarningType() { return mWarningType; }

    /// <summary>
    /// Returns the description of the warning.
    /// </summary>
    public String getDescription() { return mDescription; }

    /// <summary>
    /// Returns the source of the warning.
    /// </summary>
    public /*WarningSource*/int getSource() { return mSource; }


    private /*final*/ /*WarningType*/int mWarningType;
    private /*final*/ /*WarningSource*/int mSource;
    private /*final*/ String mDescription;
}

