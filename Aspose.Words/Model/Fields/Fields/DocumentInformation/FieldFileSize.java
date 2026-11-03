// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.FileInfo;
import com.aspose.MathUtil;


/// <summary>
/// Implements the FILESIZE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Retrieves the size of the current document's file or 0 if the size cannot be determined.</p>
/// <p>In the current implementation, uses the <see cref="Document.OriginalFileName"/> property to retrieve
/// the file name used to determine the file size.</p>
/// </remarks>
public class FieldFileSize extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore()
    {
        Int32Constant fileSize = getResultInternal(fetchDocument(), getFieldCodeCache());

        return new FieldUpdateActionApplyResult(this, fileSize);
    }

    private static Int32Constant getResultInternal(Document document, IFieldCode fieldCode)
    {
        int fileSize = 0;

        String fileName = document.getOriginalFileName();
        if (fileName != null)
        {
            try
            {
                FileInfo fileInfo = new FileInfo(fileName);
                fileSize = (int)fileInfo.getLength();    // I don't think there are that huge Word documents.

                // It seems like Word performs rounding this way.
                if (fieldCode.hasSwitch(IS_IN_MEGABYTES_SWITCH))
                    fileSize = MathUtil.doubleToInt((double)fileSize / (1000 * 1000));
                else if (fieldCode.hasSwitch(IS_IN_KILOBYTES_SWITCH))
                    fileSize = MathUtil.doubleToInt((double)fileSize / 1000.0);
            }
            catch(Exception e)
            {
                // Swallow the exception.
            }
        }

        return new Int32Constant(fileSize);
    }

    /// <summary>
    /// Gets or sets whether to display the file size in kilobytes.
    /// </summary>
    public boolean isInKilobytes() { return getFieldCodeCache().hasSwitch(IS_IN_KILOBYTES_SWITCH); }
    public void isInKilobytes(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_IN_KILOBYTES_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to display the file size in megabytes.
    /// </summary>
    public boolean isInMegabytes() { return getFieldCodeCache().hasSwitch(IS_IN_MEGABYTES_SWITCH); }
    public void isInMegabytes(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_IN_MEGABYTES_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        return getSwitchTypeInternal(switchName);
    }

    static /*FieldSwitchType*/int getSwitchTypeInternal(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_IN_KILOBYTES_SWITCH*/0:
            case /*IS_IN_MEGABYTES_SWITCH*/1:
                return FieldSwitchType.FLAG;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final String IS_IN_KILOBYTES_SWITCH = "\\k";
    private static final String IS_IN_MEGABYTES_SWITCH = "\\m";

    static IFieldInfoResultProvider FieldInfoResultProvider = new FileSizeInfoResultProvider();

    private static class FileSizeInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode)
        {
            return getResultInternal(document, fieldCode);
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\k",
		"\\m"
	);

}

