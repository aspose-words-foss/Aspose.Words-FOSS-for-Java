// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.IO.Path;


/// <summary>
/// Implements the TEMPLATE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the file name of the template used by the current document.
/// </remarks>
public class FieldTemplate extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Document document = fetchDocument();

        String templateName = getResultInternal(document, getFieldCodeCache());

        return new FieldUpdateActionApplyResult(this, templateName);
    }

    private static String getResultInternal(Document document, IFieldCode fieldCode) throws Exception
    {
        final String DEFAULT_TEMPLATE = "Normal.dotm";

        String templateName = document.getAttachedTemplate();

        if (msString.isNullOrEmpty(templateName))
            templateName = document.getFieldOptions().getTemplateName();

        if (msString.isNullOrEmpty(templateName))
            templateName = DEFAULT_TEMPLATE;

        if (!fieldCode.hasSwitch(INCLUDE_FULL_PATH_SWITCH))
            templateName = Path.getFileName(templateName);

        return templateName;
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

    static IFieldInfoResultProvider FieldInfoResultProvider = new TemplateInfoResultProvider();

    private static class TemplateInfoResultProvider implements IFieldInfoResultProvider
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

