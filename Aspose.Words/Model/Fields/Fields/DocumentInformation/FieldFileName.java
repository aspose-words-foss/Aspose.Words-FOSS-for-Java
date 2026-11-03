// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Implements the FILENAME field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Retrieves the name of the current document from its storage location.</p>
/// <p>In the current implementation, uses the <see cref="Document.OriginalFileName"/> property to retrieve
/// the file name. If the document was loaded from a stream or created blank, uses the name of the file that is being saved to (if known).</p>
/// </remarks>
public class FieldFileName extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Document document = fetchDocument();

        String fileName = getResultInternal(document, getFieldCodeCache());

        return new FieldUpdateActionApplyResult(this, fileName);
    }

    private static String getResultInternal(Document document, IFieldCode fieldCode) throws Exception
    {
        String fileName = document.getFieldOptions().getFileName();
        if (!StringUtil.hasChars(fileName))
            fileName = document.getOriginalFileName();
        if (!StringUtil.hasChars(fileName))
            fileName = document.getSavedFileName();

        if (StringUtil.hasChars(fileName) && !fieldCode.hasSwitch(INCLUDE_FULL_PATH_SWITCH))
            fileName = Path.getFileName(fileName);

        return fileName;
    }

    /// <summary>
    /// Gets or sets whether to include the full file path name.
    /// </summary>
    public boolean getIncludeFullPath() { return getFieldCodeCache().hasSwitch(INCLUDE_FULL_PATH_SWITCH); }
    public void setIncludeFullPath(boolean value) throws Exception { getFieldCodeCache().setSwitch(INCLUDE_FULL_PATH_SWITCH, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        return getSwitchTypeInternal(switchName);
    }

    static /*FieldSwitchType*/int getSwitchTypeInternal(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*INCLUDE_FULL_PATH_SWITCH*/0:
                return FieldSwitchType.FLAG;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final String INCLUDE_FULL_PATH_SWITCH = "\\p";

    static IFieldInfoResultProvider FieldInfoResultProvider = new FileNameInfoResultProvider();

    private static class FileNameInfoResultProvider implements IFieldInfoResultProvider
    {
        public Constant /*IFieldInfoResultProvider.*/getResult(Document document, IFieldCode fieldCode) throws Exception
        {
            return new StringConstant(getResultInternal(document, fieldCode));
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\p"
	);

}

