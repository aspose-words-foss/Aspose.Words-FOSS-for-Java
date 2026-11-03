// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Used to validate task pane add-ins.
/// </summary>
class WebExtensionValidator
{
    /// <summary>
    /// Validates office task pane add-ins.
    /// </summary>
    static void visitDocumentStart(DocumentBase doc, SaveInfo saveInfo, IWarningCallback warningCallback)
    {
        Document document = ms.as(doc, Document.class);
        if ((document == null) || (document.getWebExtensionTaskPanes().getCount() == 0))
            return;

        // Task pane add-ins will be missed in the output for non-OOXML format. Note about it.
        if (!saveInfo.isOoxmlFormat())
            warn(warningCallback, WarningType.DATA_LOSS_CATEGORY, WarningStrings.TASK_PANE_ADDINS_WILL_BE_SKIPPED_ON_SAVING);
    }

    /// <summary>
    /// Logs warning into specified warning callback adapter.
    /// </summary>
    private static void warn(IWarningCallback warnCallback, /*WarningType*/int type, String desc)
    {
        WarningUtil.warn(warnCallback, type, WarningSource.VALIDATOR, desc);
    }
}

