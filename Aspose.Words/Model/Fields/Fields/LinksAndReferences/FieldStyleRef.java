// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;

/// <summary>
/// Implements the STYLEREF field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// The STYLEREF is used to reference a fragment of text within the document that is formatted with
/// the specified style.
/// </remarks>
public class FieldStyleRef extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ /*FieldUpdateStage*/int getUpdateStage()
    {
        return FieldUpdateStage.DEFERRED_UPDATE_REF;
    }

    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        String styleName = getStyleName();
        if (!StringUtil.hasChars(styleName))
            return new FieldUpdateActionInsertErrorMessage(this, NO_STYLE_NAME_GIVEN_ERROR_MESSAGE);

        // List labels should be up-to-date before the final update of the field.
        getUpdater().requestExternalAction(new ExternalActionUpdateListLabels(fetchDocument()), FieldUpdateStage.DEFERRED_UPDATE_REF);

        StyleSearchResult searchResult = StyleFinder.findStyle(this, styleName);
        if (searchResult == null)
            return new FieldUpdateActionInsertErrorMessage(this, NO_TEXT_OF_STYLE_ERROR_MESSAGE);

        String result = getParagraphNumberOrRelativePosition(searchResult);
        if (result != null)
            return new FieldUpdateActionApplyResult(this, result);

        return new FieldUpdateActionApplyResult(this, extractTextFromSearchResult(searchResult), ParagraphBreakCharReplacement.PARAGRAPH_BREAK_CHAR);
    }

    private String getParagraphNumberOrRelativePosition(StyleSearchResult searchResult) throws Exception
    {
        if (getInsertParagraphNumber())
        {
            return FieldRefUtil.getParagraphNumber(
                this,
                null,
                searchResult.getParagraph(),
                getSuppressNonDelimiters(),
                getInsertRelativePosition());
        }

        if (getInsertTrimmedParagraphNumberUndocumented())
        {
            // WORDSNET-11300 MS Word handles '\s' switch similar to '\n' switch, but not equally.
            return FieldRefUtil.getTrimmedParagraphNumber(
                this,
                searchResult.getParagraph(),
                getInsertRelativePosition());
        }

        if (getInsertParagraphNumberInRelativeContext())
        {
            return FieldRefUtil.getParagraphNumberInRelativeContext(
                this,
                null,
                searchResult.getParagraph(),
                getStart().getParentParagraph(),
                getSuppressNonDelimiters(),
                null,
                getInsertRelativePosition());
        }

        if (getInsertParagraphNumberInFullContext())
        {
            return FieldRefUtil.getParagraphNumberInFullContext(
                this,
                null,
                searchResult.getParagraph(),
                getSuppressNonDelimiters(),
                null,
                getInsertRelativePosition());
        }

        if (getInsertRelativePosition())
        {
            String result = FieldRefUtil.getRelativePosition(this, !searchResult.isForwardDirection());
            if (!msString.isNullOrEmpty(result))
                return result;
        }

        return null;
    }

    /// <summary>
    /// Extract up to 256 symbols.
    /// </summary>
    /// <param name="searchResult"></param>
    private String extractTextFromSearchResult(StyleSearchResult searchResult)
    {
        final int MAX_TEXT_LENGTH = 256;  // Word's limitation.

        String result;
        if (searchResult.hasIndexRange())
        {
            int startIndex = Math.min(searchResult.getStartIndex(), searchResult.getEndIndex());
            int endIndex = Math.max(searchResult.getStartIndex(), searchResult.getEndIndex());

            NodeCollection childNodes = StyleFinder.getParagraphInlines(searchResult.getParagraph());
            Node start = childNodes.get(startIndex);
            Node end = childNodes.get(endIndex);

            // FOSS

            NodeTextCollectorOptions collectorOptions = new NodeTextCollectorOptions();
            collectorOptions.isFieldResultMode(true);
            collectorOptions.setAllowHiddenText(false);
            // FOSS

            result = msString.replace(msString.trim(NodeTextCollector.getText(start, true, end, true, collectorOptions)), ControlChar.NON_BREAKING_SPACE_CHAR, ControlChar.SPACE_CHAR);

            if (result.length() > MAX_TEXT_LENGTH)
                result = result.substring((0), (0) + (MAX_TEXT_LENGTH));
        }
        else
        {
            result = "";
        }

        if ((result.length() < MAX_TEXT_LENGTH) && searchResult.isParagraphBreakIncluded())
        {
            // MS Word inserts a run with the carriage return character instead of a new paragraph here.
            result = msString.plusEqOperator(result, ControlChar.PARAGRAPH_BREAK);
        }

        return result;
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*SEARCH_FROM_BOTTOM_SWITCH*/0:
            case /*INSERT_PARAGRAPH_NUMBER_SWITCH*/1:
            case /*INSERT_TRIMMED_PARAGRAPH_NUMBER_SWITCH_UNDOCUMENTED*/2:
            case /*INSERT_RELATIVE_POSITION_SWITCH*/3:
            case /*INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH*/4:
            case /*SUPPRESS_NON_DELIMITERS_SWITCH*/5:
            case /*INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH*/6:
                return FieldSwitchType.FLAG;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets or sets the name of the style by which the text to search for is formatted.
    /// </summary>
    public String getStyleName() { return getFieldCodeCache().getArgumentAsString(STYLE_NAME_ARGUMENT_INDEX); }
    public void setStyleName(String value) throws Exception { getFieldCodeCache().setArgument(STYLE_NAME_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets or sets whether to search from the bottom of the current page, rather from the top.
    /// </summary>
    public boolean getSearchFromBottom() { return getFieldCodeCache().hasSwitch(SEARCH_FROM_BOTTOM_SWITCH); }
    public void setSearchFromBottom(boolean value) throws Exception { getFieldCodeCache().setSwitch(SEARCH_FROM_BOTTOM_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph exactly as it appears in the document.
    /// </summary>
    public boolean getInsertParagraphNumber() { return getFieldCodeCache().hasSwitch(INSERT_PARAGRAPH_NUMBER_SWITCH); }
    public void setInsertParagraphNumber(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_PARAGRAPH_NUMBER_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the relative position of the referenced paragraph.
    /// </summary>
    public boolean getInsertRelativePosition() { return getFieldCodeCache().hasSwitch(INSERT_RELATIVE_POSITION_SWITCH); }
    public void setInsertRelativePosition(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_RELATIVE_POSITION_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph in relative context.
    /// </summary>
    public boolean getInsertParagraphNumberInRelativeContext() { return getFieldCodeCache().hasSwitch(INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH); }
    public void setInsertParagraphNumberInRelativeContext(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to suppress non-delimiter characters.
    /// </summary>
    public boolean getSuppressNonDelimiters() { return getFieldCodeCache().hasSwitch(SUPPRESS_NON_DELIMITERS_SWITCH); }
    public void setSuppressNonDelimiters(boolean value) throws Exception { getFieldCodeCache().setSwitch(SUPPRESS_NON_DELIMITERS_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph in full context.
    /// </summary>
    public boolean getInsertParagraphNumberInFullContext() { return getFieldCodeCache().hasSwitch(INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH); }
    public void setInsertParagraphNumberInFullContext(boolean value) throws Exception { getFieldCodeCache().setSwitch(INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to insert the paragraph number of the referenced paragraph exactly as it appears in the document.
    /// This switch is undocumented in a Word's documentation but Word handles one as "\n" switch.
    /// </summary>
    private boolean getInsertTrimmedParagraphNumberUndocumented() { return getFieldCodeCache().hasSwitch(INSERT_TRIMMED_PARAGRAPH_NUMBER_SWITCH_UNDOCUMENTED); }

    private static final int STYLE_NAME_ARGUMENT_INDEX = 0;

    private static final String SEARCH_FROM_BOTTOM_SWITCH = "\\l";
    private static final String INSERT_PARAGRAPH_NUMBER_SWITCH = "\\n";
    private static final String INSERT_TRIMMED_PARAGRAPH_NUMBER_SWITCH_UNDOCUMENTED = "\\s";
    private static final String INSERT_RELATIVE_POSITION_SWITCH = "\\p";
    private static final String INSERT_PARAGRAPH_NUMBER_IN_RELATIVE_CONTEXT_SWITCH = "\\r";
    private static final String SUPPRESS_NON_DELIMITERS_SWITCH = "\\t";
    private static final String INSERT_PARAGRAPH_NUMBER_IN_FULL_CONTEXT_SWITCH = "\\w";
    static final String NO_TEXT_OF_STYLE_ERROR_MESSAGE = "Error! No text of specified style in document.";
    private static final String NO_STYLE_NAME_GIVEN_ERROR_MESSAGE = "Error! No style name given.";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\l",
		"\\n",
		"\\s",
		"\\p",
		"\\r",
		"\\t",
		"\\w"
	);

}

