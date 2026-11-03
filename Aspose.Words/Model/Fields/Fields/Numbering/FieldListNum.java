// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;


/// <summary>
/// Implements the LISTNUM field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldListNum extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ NodeRange getFakeResult() throws Exception
    {
        return FieldNumUtil.getFakeResultNodeRange(this);
    }

    public /*override*/ Node remove() throws Exception
    {
        ensureFieldNumberingRevisions();

        return super.remove();
    }

    private void ensureFieldNumberingRevisions() throws Exception
    {
        if (!getDocument().isTrackRevisionsEnabled())
            return;

        ArrayList<Field> fields = FieldExtractor.extractToCollection(getDocument(), false, FieldType.FIELD_LIST_NUM);
        for (int index = fields.size() - 1; index >= 0; index--)
        {
            Field field = fields.get(index);
            if (field.getEnd().getNumberRevision() != null)
                fields.remove(index);
        }

        if (fields.size() == 0)
            return;

        Document document = fetchDocument();

        document.updateListLabels();

        EditSession editSession = document.getEditSession();

        for (Field field : fields)
        {
            String result = field.getDisplayResult();
            if (msString.isNullOrEmpty(result))
                continue;

            field.getEnd().setNumberRevision(new FieldNumberRevision(
                editSession.getAuthor(),
                editSession.getDateTime(),
                result));
        }
    }

    /// <summary>
    /// Gets or sets the name of the abstract numbering definition used for the numbering.
    /// </summary>
    public String getListName() { return getFieldCodeCache().getArgumentAsString(LIST_NAME_ARGUMENT_INDEX); }
    public void setListName(String value) throws Exception { getFieldCodeCache().setArgument(LIST_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Returns a value indicating whether the name of an abstract numbering definition
    /// is provided by the field's code.
    /// </summary>
    public boolean hasListName() { return getFieldCodeCache().hasArgument(LIST_NAME_ARGUMENT_INDEX); }

    /// <summary>
    /// Gets or sets the level in the list, overriding the default behavior of the field.
    /// </summary>
    public String getListLevel() { return getFieldCodeCache().getSwitchArgumentAsString(LIST_LEVEL_SWITCH); }
    public void setListLevel(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(LIST_LEVEL_SWITCH, value); }

    int getListLevelAsInt32() { return parseIntSwitchValue(getListLevel()); }

    /// <summary>
    /// Gets normalized <see cref="ListLevel"/> value. A negative value means error or absence.
    /// </summary>
    int getListLevelCore()
    {
        int value = getListLevelAsInt32();

        // Negative means error or absence. Zero means one (i.e. zero for list levels). MS Word behavior.
        if (value <= 0)
            return value;

        // List levels are 0-based and LISTNUM's ones are 1-based.
        return value - 1;
    }

    /// <summary>
    /// Gets or sets the starting value for this field.
    /// </summary>
    public String getStartingNumber() { return getFieldCodeCache().getSwitchArgumentAsString(STARTING_NUMBER_SWITCH); }
    public void setStartingNumber(String value) throws Exception { getFieldCodeCache().setSwitchAsInt32(STARTING_NUMBER_SWITCH, value); }

    int getStartingNumberAsInt32() { return parseIntSwitchValue(getStartingNumber()); }

    /// <summary>
    /// Gets normalized <see cref="StartingNumber"/> value. A negative value means error or absence.
    /// </summary>
    int getStartingNumberCore()
    {
        int value = getStartingNumberAsInt32();

        // MS Word considers only four first digits of the value.
        while (value > 9999)
            value /= 10;

        // Negative means error or absence. Everything else is acceptable.
        return value;
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*LIST_LEVEL_SWITCH*/0:
            case /*STARTING_NUMBER_SWITCH*/1:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    private static int parseIntSwitchValue(String switchValue)
    {
        String value = switchValue;
        if (msString.isNullOrEmpty(value) || !Character.isDigit(value.charAt(0)))
            return -1;

        // WORDSNET-13387 MS Word allows trailing non-digits symbols, but does not allow leading.
        value = StringUtil.trimEndNonDigits(value);

        int listLevel = FormatterPal.tryParseInt(value);
        return listLevel != Integer.MIN_VALUE ? listLevel : -1;
    }

    private static final int LIST_NAME_ARGUMENT_INDEX = 0;

    private static final String LIST_LEVEL_SWITCH = "\\l";
    private static final String STARTING_NUMBER_SWITCH = "\\s";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\l",
		"\\s"
	);

}

