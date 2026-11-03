// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 24/11/15 by Anatoly Sidorenko

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.msString;

/**
 * Java incomplete implementation of
 * https://msdn.microsoft.com/en-us/library/System.Globalization.TextInfo.aspx
 */
public class TextInfo implements Cloneable
{
    /**
     * Used only by CultureInfo.
     *
     * @param cultureData
     */
    TextInfo(CultureData cultureData)
    {
        listSeparator = msString.isNullOrEmpty(cultureData.getListSeparator()) ? "," : cultureData.getListSeparator();
        mANSICodePage = cultureData.getANSICodePage();
    }

    public String getListSeparator()
    {
        return listSeparator;
    }

    public void setListSeparator(String listSeparator) { this.listSeparator = listSeparator; }

    public int getANSICodePage()
    {
        return mANSICodePage;
    }

    private String listSeparator;
    private int mANSICodePage;
}
