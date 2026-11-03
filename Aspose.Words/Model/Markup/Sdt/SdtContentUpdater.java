// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/11/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.LocaleConverter;
import com.aspose.StringUtil;
import com.aspose.SystemPal;
import com.aspose.ms.System.DateTime;


/// <summary>
/// Implements updating content of not data bound SDT.
/// </summary>
class SdtContentUpdater
{
    /// <summary>
    /// Updates structured document tag content without bound data.
    /// </summary>
    static void updateNonBoundDataContent(StructuredDocumentTag sdt)
    {
        if (!sdt.getXmlMapping().isEmpty() || (sdt.getDocument().getNodeType() == NodeType.GLOSSARY_DOCUMENT))
            return;

        switch (sdt.getSdtType())
        {
            case SdtType.DATE:
                updateDateFromFullDate(sdt);
                break;

            case SdtType.DROP_DOWN_LIST:
            case SdtType.COMBO_BOX:
                updateSelectedItemContent(sdt);
                break;

            case SdtType.CHECKBOX:
                SdtContentHelper.updateCheckboxContent(sdt);
                break;

            default:
                // Do nothing.
                break;
        }
    }

    /// <summary>
    /// If combobox or dropdown list has selected value set, then we'll have to set IsShowingPlaceholderText to false and
    /// and update sdt content with value text to make sure MS Word shows it ok.
    /// </summary>
    private static void updateSelectedItemContent(StructuredDocumentTag sdt)
    {
        SdtListItemCollection items = sdt.getListItems();
        if (items.getSelectedValue() != null)
        {
            sdt.isShowingPlaceholderText(false);
            SdtContentHelper.insertContent(sdt, new Run(sdt.fetchDocument(), items.getSelectedValue().getDisplayText()), true);
        }
    }

    /// <summary>
    /// Updates content of Sdt with FullDate according to the current sdt datetime display format.
    /// </summary>
    private static void updateDateFromFullDate(StructuredDocumentTag sdt)
    {
        if (!((SdtDate)sdt.getControlProperties()).getNeedToUpdateContent())
            return;

        String fieldUpdateCultureName = LocaleConverter.localeToDocxTag(sdt.getDateDisplayLocale());
        boolean needSetLocation = StringUtil.hasChars(fieldUpdateCultureName);

        try
        {
            if (needSetLocation)
            {
                SystemPal.saveCulture();
                SystemPal.setCulture(fieldUpdateCultureName);
            }

            if (!DateTime.equals(sdt.getFullDateInternal(), DateTime.MinValue))
            {
                String result = WordUtil.formatDateTime(sdt.getFullDateInternal(), sdt.getDateDisplayFormat(), sdt.getDateDisplayLocale());

                SdtContentHelper.setDisplayText(sdt, result);
            }
            else
            {
                SdtContentHelper.replaceContentWithPlaceholder(sdt);
            }
        }
        finally
        {
            if (needSetLocation)
                SystemPal.restoreCulture();
        }
    }
}

