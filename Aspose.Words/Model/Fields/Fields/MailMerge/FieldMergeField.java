// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Implements the MERGEFIELD field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the name of a data field within the merge characters in a mail merge main document.
/// When the main document is merged with the selected data source, information from the specified
/// data field is inserted in place of the merge field.
/// </remarks>
public class FieldMergeField extends Field implements IFieldCodeTokenInfoProvider
{
    FieldMergeField()
    {
    	this(false);
	
    }

    private FieldMergeField(boolean isSurrogate)
    {
        mIsSurrogate = isSurrogate;
    }

    /// <summary>
    /// Used to create a merge field to replace during mail merge.
    /// The field created is not actually a merge field because its code is still the surrogate's
    /// code. So actually it stays, for example, a MACROBUTTON field, but behaving like a merge field.
    /// </summary>
    static FieldMergeField createFromSurrogate(IMergeFieldSurrogate surrogate) throws Exception
    {
        FieldMergeField field = new FieldMergeField(true);
        field.initialize(surrogate.getStart(), surrogate.getSeparator(), surrogate.getEnd());
        field.setFieldNameNoPrefix(surrogate.getMergeFieldName());

        if (field.getFieldTypeFromCode() == FieldType.FIELD_MERGE_BARCODE)
            field.mMergeFieldType = MergeFieldType.BARCODE;

        return field;
    }

    /// <summary>
    /// Checks whether the specified field can be treated as MERGEFIELD surrogate. If it can, returns
    /// the corresponding <see cref="IMergeFieldSurrogate"/> instance. Otherwise, returns <c>null</c>.
    /// </summary>
    static IMergeFieldSurrogate getMergeFieldSurrogate(Field field) throws Exception
    {
        IMergeFieldSurrogate surrogate = ms.as(field, IMergeFieldSurrogate.class);
        if ((surrogate != null) && surrogate.canWorkAsMergeField())
            return surrogate;

        return null;
    }

    /// <summary>
    /// Two stage construction.
    /// </summary>
    /*override*/ void initialize(FieldStart start, FieldSeparator separator, FieldEnd end)
    {
        super.initialize(start, separator, end);
        parseFieldCode();
    }

    /*override*/ void parseFieldCode()
    {
        super.parseFieldCode();

        // Only parse the code if the field is a real merge field, not based on a surrogate.
        if (!mIsSurrogate)
            parseFieldCode(getFieldCodeCache());
    }

    /// <summary>
    /// Parses field text various parts of the field. Public for unit testing.
    ///    Note that instead of MERGEFIELD there can be basically anything as Word French version
    ///    using CHAMPFUSION as field code.
    ///
    /// Accepts:
    ///    MERGEFIELD FirstName
    ///    MERGEFIELD "First Name"
    ///    MERGEFIELD  FirstName
    ///    MERGEFIELD "Table Start:First Name"
    ///    MERGEFIELD Image:FirstName
    ///    MERGEFIELD FirstName \b sometext
    ///    MERGEFIELD FirstName \b "some text" \@ "date format"
    ///    MERGEFIELD FirstName \b beforetext \f aftertext \# "$#,##0.00;($#,##0.00);Zero"
    ///
    /// </summary>
    private void parseFieldCode(FieldCode fieldCode)
    {
        FieldMergeFieldParamBag result = parseFieldCodeToParamBag(fieldCode);
        if (result != null)
        {
            setPrefix(result.getPrefix());
            setFieldNameNoPrefix(result.getFieldName());
            mMergeFieldType = result.getMergeFieldType();
            mImageWidth = result.getImageWidth();
            mImageHeight = result.getImageHeight();
        }
    }

    /// <summary>
    /// Parses a full merge field name into an optional prefix and the field name.
    /// For example TableStart:MyTable.
    /// Understands ":" or "_" as the separator between the prefix and the field name.
    /// But note that "_" still can be used in the field name, for example "TableStart:Table_1".
    /// </summary>
    static FieldMergeFieldParamBag parseFieldCodeToParamBag(FieldCode fieldCode)
    {
        String fullFieldName = fieldCode.getArgumentAsString(0);
        if (fullFieldName == null)
            return null;

        // Fill related member variables as for a common MERGEFIELD.
        FieldMergeFieldParamBag result = new FieldMergeFieldParamBag();
        result.setPrefix("");
        result.setFieldName(fullFieldName);
        result.setMergeFieldType(MergeFieldType.COMMON);
        result.setImageWidth(null);
        result.setImageHeight(null);

        // Try to find a prefix separator. If it is not found, the field is surely common, simply return.
        int prefixSeparatorIndex = msString.indexOfAny(fullFieldName, G_NAME_PREFIX_SEPARATORS);
        if (prefixSeparatorIndex == -1)
            return result;

        // The field is suspected to have a special meaning, get its prefix for further analysis.
        String prefix = fullFieldName.substring((0), (0) + (prefixSeparatorIndex));

        if (processPossibleImageField(prefix, result))
        {
            result.setMergeFieldType(MergeFieldType.IMAGE);
        }
        else
        {
            // The field is common, simply return.
            return result;
        }

        // The field is not common, set related member variables.
        result.setPrefix(prefix);
        result.setFieldName(fullFieldName.substring(prefixSeparatorIndex + 1));
        return result;
    }

    /// <summary>
    /// Parses a full merge field name into an optional prefix and the field name.
    /// For example TableStart:MyTable.
    /// Understands ":" or "_" as the separator between the prefix and the field name.
    /// But note that "_" still can be used in the field name, for example "TableStart:Table_1".
    /// </summary>
    static FieldMergeFieldParamBag parseFieldCodeToParamBag(String fieldCode)
    {
        return parseFieldCodeToParamBag(new FieldCode(fieldCode, new FieldMergeField()));
    }

    private static boolean processPossibleImageField(String prefix, FieldMergeFieldParamBag param)
    {
        Match match = G_IMAGE_SIZE_REGEX.match(prefix);
        if (!match.getSuccess())
            return false;

        if (!StringUtil.hasChars(match.getGroups().get(IMAGE_SIZE_GROUP_INDEX).getValue()))
            return true;

        MergeFieldImageDimension imageWidth = MergeFieldImageDimension.tryParse(
            match.getGroups().get(IMAGE_WIDTH_VALUE_GROUP_INDEX).getValue(),
            match.getGroups().get(IMAGE_WIDTH_UNIT_GROUP_INDEX).getValue());
        if (imageWidth == null)
            return true;

        MergeFieldImageDimension imageHeight = MergeFieldImageDimension.tryParse(
            match.getGroups().get(IMAGE_HEIGHT_VALUE_GROUP_INDEX).getValue(),
            match.getGroups().get(IMAGE_HEIGHT_UNIT_GROUP_INDEX).getValue());
        if (imageHeight == null)
            return true;

        // Set image dimensions only if they are both successfully parsed.
        // Otherwise, the original image size is to be used.
        param.setImageWidth(imageWidth);
        param.setImageHeight(imageHeight);

        return true;
    }

    /// <summary>
    /// Gets the Microsoft Word field type.
    /// </summary>
    public /*override*/ /*FieldType*/int getType()
    {
        return FieldType.FIELD_MERGE_FIELD;
    }

    /*override*/ FieldUpdateAction updateCore()
    {
        // The update was launched directly. Simply set the result to the field name enclosed into
        // French double quotes as Word does.
        String result = msString.format(
            "{0}«{1}»{2}",
            getTextBefore(),
            getFieldCodeCache().getArgumentAsString(0),
            getTextAfter());

        return new FieldUpdateActionApplyResult(this, result, false);
    }

    /*override*/ void endUpdate() throws Exception
    {
        // WORDSNET-11981 Implement MERGEBARCODE
        if (getFieldTypeFromCode() == FieldType.FIELD_MERGE_BARCODE)
            FieldBarcodeUtil.replaceMergeBarcode(this);

        super.endUpdate();
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_MAPPED_SWITCH*/0:
            case /*IS_VERTICAL_FORMATTING_SWITCH*/1:
                return FieldSwitchType.FLAG;
            case /*TEXT_BEFORE_SWITCH*/2:
            case /*TEXT_AFTER_SWITCH*/3:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Returns the image width which has been achieved through the field code parsing.
    /// </summary>
    /// <returns></returns>
    MergeFieldImageDimension getImageWidthCopy()
    {
        return getImageDimensionCopy(mImageWidth);
    }

    /// <summary>
    /// Returns the image height which has been achieved through the field code parsing.
    /// </summary>
    /// <returns></returns>
    MergeFieldImageDimension getImageHeightCopy()
    {
        return getImageDimensionCopy(mImageHeight);
    }

    private static MergeFieldImageDimension getImageDimensionCopy(MergeFieldImageDimension source)
    {
        return (source == null) ? new MergeFieldImageDimension() : source.deepClone();
    }

    /// <summary>
    /// Returns a value indicating whether the specified <see cref="string"/> contains any characters
    /// reserved to separate a merge field's name from its prefix.
    /// </summary>
    static boolean containsNamePrefixSeparator(String value)
    {
        Debug.assertTrue(value != null);

        return (msString.indexOfAny(value, G_NAME_PREFIX_SEPARATORS) != -1);
    }

    static String getMergeFieldName(String prefix, String fieldName)
    {
        return msString.isNullOrEmpty(prefix)
            ? fieldName
            : msString.format("{0}:{1}", prefix, fieldName);
    }

    /// <summary>
    /// Gets whether this field is a mail merge region start mark.
    /// </summary>
    boolean isRegionStartMark() { return (mMergeFieldType == MergeFieldType.REGION_START); }

    /// <summary>
    /// Gets whether this field is a mail merge region start mark.
    /// </summary>
    boolean isRegionEndMark() { return (mMergeFieldType == MergeFieldType.REGION_END); }

    /// <summary>
    /// Returns a value indicating whether this field is used to insert an image into the document.
    /// </summary>
    boolean isImageField() { return (mMergeFieldType == MergeFieldType.IMAGE); }

    /// <summary>
    /// Extended merge field names could have a prefix such as TableStart, TableEnd or Image.
    /// This is a way for me to support extended fields such a mail merge image, or
    /// formatted text in the future.
    /// </summary>
    String getPrefix() { return mPrefix; }; private void setPrefix(String value) { mPrefix = value; };

    private String mPrefix;

    /// <summary>
    /// Returns just the name of the data field. Any prefix is stripped to the prefix property.
    /// </summary>
    public String getFieldNameNoPrefix() { return mFieldNameNoPrefix; }; private void setFieldNameNoPrefix(String value) { mFieldNameNoPrefix = value; };

    private String mFieldNameNoPrefix;

    /// <summary>
    /// Gets or sets the name of a data field.
    /// </summary>
    public String getFieldName() { return getFieldCodeCache().getArgumentAsString(FIELD_NAME_ARGUMENT_INDEX); }
    public void setFieldName(String value) throws Exception { getFieldCodeCache().setArgument(FIELD_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets the text to be inserted before the field if the field is not blank.
    /// </summary>
    public String getTextBefore() { return getFieldCodeCache().getSwitchArgumentAsString(TEXT_BEFORE_SWITCH); }
    public void setTextBefore(String value) throws Exception { getFieldCodeCache().setSwitch(TEXT_BEFORE_SWITCH, value); }

    /// <summary>
    /// Gets or sets the text to be inserted after the field if the field is not blank.
    /// </summary>
    public String getTextAfter() { return getFieldCodeCache().getSwitchArgumentAsString(TEXT_AFTER_SWITCH); }
    public void setTextAfter(String value) throws Exception { getFieldCodeCache().setSwitch(TEXT_AFTER_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether this field is a mapped field.
    /// </summary>
    public boolean isMapped() { return getFieldCodeCache().hasSwitch(IS_MAPPED_SWITCH); }
    public void isMapped(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_MAPPED_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to enable character conversion for vertical formatting.
    /// </summary>
    public boolean isVerticalFormatting() { return getFieldCodeCache().hasSwitch(IS_VERTICAL_FORMATTING_SWITCH); }
    public void isVerticalFormatting(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_VERTICAL_FORMATTING_SWITCH, value); }

    /// <summary>
    /// Specifies how a particular merge field should be treated.
    /// </summary>
    /*enum*/ final class MergeFieldType
    {
        private MergeFieldType(){}
        
        public static final int COMMON = 0;
        public static final int IMAGE = 1;
        public static final int BARCODE = 2;
        public static final int REGION_START = 3;
        public static final int REGION_END = 4;

        public static final int length = 5;
    }

    private /*final*/ boolean mIsSurrogate;

    private /*MergeFieldType*/int mMergeFieldType;

    private MergeFieldImageDimension mImageWidth;
    private MergeFieldImageDimension mImageHeight;

    private static final int IMAGE_SIZE_GROUP_INDEX = 3;
    private static final int IMAGE_WIDTH_VALUE_GROUP_INDEX = 4;
    private static final int IMAGE_WIDTH_UNIT_GROUP_INDEX = 5;
    private static final int IMAGE_HEIGHT_VALUE_GROUP_INDEX = 6;
    private static final int IMAGE_HEIGHT_UNIT_GROUP_INDEX = 7;

    /// <summary>
    /// Represents the regex to parse a MERGEFIELD image size. All possible MERGEFIELD image dimension units
    /// should be listed in its pattern.
    /// </summary>
    private static /*final*/ Regex G_IMAGE_SIZE_REGEX = new Regex(
        "\\Aimage(\\s*\\(\\s*(((.+?)\\s*(pt|%|)\\s*;\\s*(.+?)\\s*(pt|%|))|.*?)\\s*\\)\\s*)??\\z",
        RegexOptions.COMPILED | RegexOptions.IGNORE_CASE | RegexOptions.CULTURE_INVARIANT);

    private static /*final*/ char[] G_NAME_PREFIX_SEPARATORS = { ':', '_' };
    private static final int FIELD_NAME_ARGUMENT_INDEX = 0;

    private static final String TEXT_BEFORE_SWITCH = "\\b";
    private static final String TEXT_AFTER_SWITCH = "\\f";
    private static final String IS_MAPPED_SWITCH = "\\m";
    private static final String IS_VERTICAL_FORMATTING_SWITCH = "\\v";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\m",
		"\\v",
		"\\b",
		"\\f"
	);

}

