// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Implements the INFO field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Inserts information about a document property.
/// </remarks>
public class FieldInfo extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        IFieldInfoResultProvider provider = getProvider();
        if (provider == null)
            return new FieldUpdateActionInsertErrorMessage(this, UNKNOWN_INFO_TYPE_ERROR);

        FieldCodeDecorator fieldCode = new FieldCodeDecorator(getFieldCodeCache());

        Constant result = provider.getResult(fetchDocument(), fieldCode);

        return new FieldUpdateActionApplyResult(this, result);
    }

    private IFieldInfoResultProvider getProvider()
    {
        String infoType = getInfoType();

        if (msString.isNullOrEmpty(infoType))
            return null;

        /*FieldType*/int infoFieldType = FieldUtil.getFieldType(infoType);

        switch (infoFieldType)
        {
            case FieldType.FIELD_AUTHOR: return FieldAuthor.FieldInfoResultProvider;
            case FieldType.FIELD_COMMENTS: return FieldComments.FieldInfoResultProvider;
            case FieldType.FIELD_CREATE_DATE: return FieldCreateDate.FieldInfoResultProvider;
            case FieldType.FIELD_EDIT_TIME: return FieldEditTime.FieldInfoResultProvider;
            case FieldType.FIELD_FILE_NAME: return FieldFileName.FieldInfoResultProvider;
            case FieldType.FIELD_FILE_SIZE: return FieldFileSize.FieldInfoResultProvider;
            case FieldType.FIELD_KEYWORD: return FieldKeywords.FieldInfoResultProvider;
            case FieldType.FIELD_LAST_SAVED_BY: return FieldLastSavedBy.FieldInfoResultProvider;
            case FieldType.FIELD_NUM_CHARS: return FieldNumChars.FieldInfoResultProvider;
            case FieldType.FIELD_NUM_WORDS: return FieldNumWords.FieldInfoResultProvider;
            case FieldType.FIELD_PRINT_DATE: return FieldPrintDate.FieldInfoResultProvider;
            case FieldType.FIELD_REVISION_NUM: return FieldRevNum.FieldInfoResultProvider;
            case FieldType.FIELD_SAVE_DATE: return FieldSaveDate.FieldInfoResultProvider;
            case FieldType.FIELD_SUBJECT: return FieldSubject.FieldInfoResultProvider;
            case FieldType.FIELD_TEMPLATE: return FieldTemplate.FieldInfoResultProvider;
            case FieldType.FIELD_TITLE: return FieldTitle.FieldInfoResultProvider;
            case FieldType.FIELD_NUM_PAGES: return FieldNumPages.FieldInfoResultProvider;
            default: return null;
        }
    }

    /// <summary>
    /// Gets or sets the type of the document property to insert.
    /// </summary>
    public String getInfoType() { return getFieldCodeCache().getArgumentAsString(INFO_TYPE_ARGUMENT_INDEX); }
    public void setInfoType(String value) throws Exception { getFieldCodeCache().setArgument(INFO_TYPE_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets an optional value that updates the property.
    /// </summary>
    public String getNewValue() { return getFieldCodeCache().getArgumentAsString(NEW_VALUE_ARGUMENT_INDEX); }
    public void setNewValue(String value) throws Exception { getFieldCodeCache().setArgument(NEW_VALUE_ARGUMENT_INDEX, value); }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        // The INFO field ignores CREATEDATE, PRINTDATE and SAVEDATE switches.
        /*FieldSwitchType*/int result = FieldFileSize.getSwitchTypeInternal(switchName);
        if (result != FieldSwitchType.UNKNOWN)
            return result;

        result = FieldFileName.getSwitchTypeInternal(switchName);
        if (result != FieldSwitchType.UNKNOWN)
            return result;

        return FieldTemplate.getSwitchTypeInternal(switchName);
    }

    private static final int INFO_TYPE_ARGUMENT_INDEX = 0;
    private static final int NEW_VALUE_ARGUMENT_INDEX = 1;

    private static final String UNKNOWN_INFO_TYPE_ERROR = "Error! Unknown info keyword.";

    private static class FieldCodeDecorator implements IFieldCode
    {
        FieldCodeDecorator(FieldCode fieldCode)
        {
            mFieldCode = fieldCode;
        }

        public String /*IFieldCode.*/getArgumentAsString(int authorNameArgumentIndex)
        {
            return mFieldCode.getArgumentAsString(authorNameArgumentIndex + 1);
        }

        public boolean /*IFieldCode.*/hasSwitch(String switchName)
        {
            return mFieldCode.hasSwitch(switchName);
        }

        private /*final*/ FieldCode mFieldCode;
    }
}

