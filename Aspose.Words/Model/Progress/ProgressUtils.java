// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/*static*/ class ProgressUtils
{
	/* Simulation of static class by using private constructor */
	private ProgressUtils()
	{}

    // This method may be removed when all formats will be supported.
    static boolean isLoadingProgressSupported(/*LoadFormat*/int lf)
    {
        switch (lf)
        {
            case LoadFormat.DOCX:
            case LoadFormat.DOCM:
            case LoadFormat.DOTM:
            case LoadFormat.DOTX:
            case LoadFormat.FLAT_OPC:
            case LoadFormat.FLAT_OPC_MACRO_ENABLED:
            case LoadFormat.FLAT_OPC_TEMPLATE:
            case LoadFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
            case LoadFormat.MARKDOWN:
            case LoadFormat.TEXT:
            case LoadFormat.WORD_ML:
            case LoadFormat.RTF:
            case LoadFormat.DOC:
            case LoadFormat.DOT:
            case LoadFormat.ODT:
            case LoadFormat.OTT:
            case LoadFormat.PDF:
                return true;
            default:
                return false;
        }
    }

    // This method may be removed when all formats will be supported.
    static boolean isSavingProgressSupported(/*SaveFormat*/int sf)
    {
        switch (sf)
        {
            case SaveFormat.DOCX:
            case SaveFormat.DOCM:
            case SaveFormat.DOTM:
            case SaveFormat.DOTX:
            case SaveFormat.FLAT_OPC:
            case SaveFormat.FLAT_OPC_MACRO_ENABLED:
            case SaveFormat.FLAT_OPC_TEMPLATE:
            case SaveFormat.FLAT_OPC_TEMPLATE_MACRO_ENABLED:
                return true;
            case SaveFormat.DOC:
            case SaveFormat.DOT:
                return true;
            case SaveFormat.HTML:
            case SaveFormat.MHTML:
            case SaveFormat.EPUB:
            case SaveFormat.AZW_3:
            case SaveFormat.MOBI:
            case SaveFormat.XAML_FLOW:
            case SaveFormat.XAML_FLOW_PACK:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Conditionally warns that progress notification is not supported yet.
    /// </summary>
    static void warnWhenProgressUnsupported(
        /*LoadFormat*/int loadFormat,
        IWarningCallback warningCallback,
        IDocumentLoadingCallback loadingProgressCallback)
    {
        if (isLoadingProgressSupported(loadFormat) || (loadingProgressCallback == null))
            return;

        // Notify a client about progress feature limitations. Once per attempt of document loading.
        WarningUtil.warn(
                warningCallback,
                WarningType.HINT,
                WarningUtil.toLoadWarningSource(loadFormat),
                msString.format(WarningStrings.LOADING_PROGRESS_UNSUPPORTED, loadFormat));
    }

    /// <summary>
    /// Conditionally warns that progress notification is not supported yet.
    /// </summary>
    static void warnWhenProgressUnsupported(
        /*SaveFormat*/int saveFormat,
        IWarningCallback warningCallback,
        IDocumentSavingCallback savingProgressCallback)
    {
        if (isSavingProgressSupported(saveFormat) || (savingProgressCallback == null))
            return;

        // Notify a client about progress feature limitations. Once per attempt of document saving.
        WarningUtil.warn(
                warningCallback,
                WarningType.HINT,
                WarningUtil.toSaveWarningSource(saveFormat),
                msString.format(WarningStrings.SAVING_PROGRESS_UNSUPPORTED, saveFormat));
    }
}

