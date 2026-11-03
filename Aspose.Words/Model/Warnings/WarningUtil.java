// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/08/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

/// <summary>
/// Provides methods to log warning in different ways.
/// </summary>
/// <remarks>
/// AM. The main purpose of this class is remove often used static Warn helper methods.
/// </remarks>
/*static*/ class WarningUtil
{
	/* Simulation of static class by using private constructor */
	private WarningUtil()
	{}

    /// <summary>
    /// Logs warning into specified warning callback adapter.
    /// </summary>
    static void warn(IWarningCallback warningCallback, /*WarningType*/int warningType, /*WarningSource*/int warningSource, String description)
    {
        if (warningCallback != null)
            warningCallback.warning(new WarningInfo(warningType, warningSource, description));
    }

    /// <summary>
    /// Logs warning into specified warning callback adapter.
    /// </summary>
    static void warn(IWarningCallback warningCallback, /*WarningType*/int warningType, /*WarningSource*/int warningSource, String description, /*params*/ Object... args)
    {
        if (warningCallback != null)
            warningCallback.warning(new WarningInfo(warningType, warningSource, msString.format(description, args)));
    }

    /// <summary>
    /// Logs Unexpected warning into specified warning callback adapter.
    /// </summary>
    static void warnUnexpected(IWarningCallback warningCallback, /*WarningSource*/int warningSource, String description, /*params*/ Object... args)
    {
        warn(warningCallback, WarningType.UNEXPECTED_CONTENT, warningSource, description, args);
    }

    /// <summary>
    /// Logs Unexpected warning with Unknown source into specified warning callback adapter.
    /// </summary>
    static void warnUnexpected(IWarningCallback warningCallback, String description, /*params*/ Object... args)
    {
        warnUnexpected(warningCallback, WarningSource.UNKNOWN, description, args);
    }

    /// <summary>
    /// Logs DataLoss warning into specified warning callback adapter.
    /// </summary>
    static void warnDataLoss(IWarningCallback warningCallback, /*WarningSource*/int warningSource, String description, /*params*/ Object... args)
    {
        warn(warningCallback, WarningType.DATA_LOSS, warningSource, description, args);
    }

    /// <summary>
    /// Logs DataLoss warning with Unknown source into specified warning callback adapter.
    /// </summary>
    static void warnDataLoss(IWarningCallback warningCallback, String description, /*params*/ Object... args)
    {
        warnDataLoss(warningCallback, WarningSource.UNKNOWN, description, args);
    }

    /// <summary>
    /// Converts load format to appropriate warning source.
    /// </summary>
    static /*WarningSource*/int toLoadWarningSource(/*LoadFormat*/int lf)
    {
        switch (lf)
        {
            case LoadFormat.DOCM:
            case LoadFormat.DOTM:
            case LoadFormat.DOTX:
            case LoadFormat.FLAT_OPC:
            case LoadFormat.FLAT_OPC_MACRO_ENABLED:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
            case LoadFormat.DOCX:
                return WarningSource.DOCX;
            case LoadFormat.RTF:
                return WarningSource.RTF;
            case LoadFormat.WORD_ML:
                return WarningSource.WORD_ML;
            case LoadFormat.ODT:
                return WarningSource.ODT;
            case LoadFormat.DOC:
                return WarningSource.DOC;
            case LoadFormat.TEXT:
                return WarningSource.TEXT;
            case LoadFormat.PDF:
                return WarningSource.PDF;
            case LoadFormat.MARKDOWN:
                return WarningSource.MARKDOWN;
            case LoadFormat.MHTML:
            case LoadFormat.HTML:
                return WarningSource.HTML;
            case LoadFormat.CHM:
                return WarningSource.CHM;
            default:
                return WarningSource.UNKNOWN;
        }
    }

    /// <summary>
    /// Converts save format to appropriate warning source.
    /// </summary>
    static /*WarningSource*/int toSaveWarningSource(/*SaveFormat*/int sf)
    {
        switch (sf)
        {
            case SaveFormat.DOCM:
            case SaveFormat.DOTM:
            case SaveFormat.DOTX:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
            case SaveFormat.DOCX:
                return WarningSource.DOCX;
            case SaveFormat.RTF:
                return WarningSource.RTF;
            case SaveFormat.WORD_ML:
                return WarningSource.WORD_ML;
            case SaveFormat.ODT:
            case SaveFormat.OTT:
                return WarningSource.ODT;
            case SaveFormat.DOC:
                return WarningSource.DOC;
            case SaveFormat.TEXT:
                return WarningSource.TEXT;
            case SaveFormat.PDF:
                return WarningSource.PDF;
            case SaveFormat.MARKDOWN:
                return WarningSource.MARKDOWN;
            case SaveFormat.MHTML:
            case SaveFormat.HTML:
                return WarningSource.HTML;
            case SaveFormat.XAML_FIXED:
            case SaveFormat.XAML_FLOW:
            case SaveFormat.XAML_FLOW_PACK:
                return WarningSource.XAML;
            case SaveFormat.XPS:
            case SaveFormat.OPEN_XPS:
                return WarningSource.XPS;
            default:
                return WarningSource.UNKNOWN;
        }
    }
}

