// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/// <summary>
/// Implements the INCLUDE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts all or part of the text and graphics contained in another document.
/// </remarks>
public class FieldInclude extends Field implements IFieldCodeTokenInfoProvider, IFieldIncludeTextCode
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        return FieldIncludeTextUpdater.update(this);
    }

    /// <summary>
    /// Gets or sets the location of the document.
    /// </summary>
    public String getSourceFullName() { return getFieldCodeCache().getArgumentAsString(SOURCE_FULL_NAME_ARGUMENT_INDEX); }
    public void setSourceFullName(String value) throws Exception { getFieldCodeCache().setArgument(SOURCE_FULL_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the name of the bookmark in the document to include.
    /// </summary>
    public String getBookmarkName() { return getFieldCodeCache().getArgumentAsString(BOOKMARK_NAME_INDEX); }
    public void setBookmarkName(String value) throws Exception { getFieldCodeCache().setArgument(BOOKMARK_NAME_INDEX, value); }

    /// <summary>
    /// Gets or sets whether to prevent fields in the included document from being updated.
    /// </summary>
    public boolean getLockFields() { return getFieldCodeCache().hasSwitch(LOCK_FIELDS_SWITCH); }
    public void setLockFields(boolean value) throws Exception { getFieldCodeCache().setSwitch(LOCK_FIELDS_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of the text converter for the format of the included file.
    /// </summary>
    public String getTextConverter() { return getFieldCodeCache().getSwitchArgumentAsString(TEXT_CONVERTER_SWITCH); }
    public void setTextConverter(String value) throws Exception { getFieldCodeCache().setSwitch(TEXT_CONVERTER_SWITCH, value); }

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludeTextCode.SourceFullName

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //String IFieldIncludeTextCode.BookmarkName

    //JAVA-deleted Namesake Explicit Interface Implementation property:
    //boolean IFieldIncludeTextCode.LockFields

    @ReservedForInternalUse @Deprecated
    public String /*IFieldIncludeTextCode.*/getNamespaceMappings() { return null; }

    @ReservedForInternalUse @Deprecated
    public String /*IFieldIncludeTextCode.*/getXPath() { return null; }

    @ReservedForInternalUse @Deprecated
    public String /*IFieldIncludeTextCode.*/getXslTransformation() { return null; }

    @ReservedForInternalUse @Deprecated
    public int /*IFieldIncludeTextCode.*/getSourceFullNameArgumentIndex() { return SOURCE_FULL_NAME_ARGUMENT_INDEX; }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*LOCK_FIELDS_SWITCH*/0:
                return FieldSwitchType.FLAG;
            case /*TEXT_CONVERTER_SWITCH*/1:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static final int SOURCE_FULL_NAME_ARGUMENT_INDEX = 0;
    private static final int BOOKMARK_NAME_INDEX = 1;

    private static final String LOCK_FIELDS_SWITCH = "\\!";
    private static final String TEXT_CONVERTER_SWITCH = "\\c";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\!",
		"\\c"
	);

}

