// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;

/// <summary>
/// Implements the XE field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Defines the text and page number for an index entry, which is used by an INDEX field.
/// </remarks>
public class FieldXE extends Field implements IFieldCodeTokenInfoProvider
{
    /// <summary>
    /// Returns a value indicating whether this instance of XE field should be processed while an INDEX field result
    /// building.
    /// </summary>
    /// <param name="fieldCode">Cached XE field properties. If not specified then direct values are used.</param>
    boolean shouldBeProcessed(FieldCodeXE fieldCode)
    {
        return isValid() && hasValidText(fieldCode);
    }

    /// <summary>
    /// Returns a value indicating whether this instance of XE field is placed in a valid place of the document.
    /// </summary>
    private boolean isValid()
    {
        Node node = getStart();
        while (true) // return inside.
        {
            node = node.getStoryAncestor(NodeType.ANY);
            if (node == null)
                return false;

            switch (node.getNodeType())
            {
                case NodeType.SHAPE:
                case NodeType.GROUP_SHAPE:
                    // Index entries in shapes are accessed if they are inside bodies.
                    // So get the next one story ancestor.
                    break;
                case NodeType.DOCUMENT:
                    // This is the replacement of NodeType.Body. See NodeUtil.IsStoryNodeType() for details.
                    // Index entries inside bodies are accessed.
                    return true;
                default:
                    // Index entries inside headers, footers, comments or footnotes are not accessed.
                    return false;
            }
        }
    }

    /// <summary>
    /// Returns a value indicating whether this instance of XE field has a valid text, i.e. the text that can be
    /// included to an INDEX field result.
    /// </summary>
    /// <param name="fieldCode">Cached XE field properties. If not specified then direct values are used.</param>
    private static boolean hasValidText(FieldCodeXE fieldCode)
    {
        // Entries with an empty or whitespace text should not be processed.
        String text = fieldCode.getText();
        if (text == null)
            return false;

        int index = StringUtil.indexOfNonWhitespace(text);
        if (index == -1)
            return false;

        // Entries with empty subentries of the min level should not be processed.
        return text.charAt(index) != IndexEntry.SUBENTRY_SEPARATOR;
    }

    /// <summary>
    /// A shortcut method.
    /// </summary>
    private static /*NodeType*/int getStoryAncestorType(Node node)
    {
        return node.getStoryAncestor(NodeType.ANY).getNodeType();
    }

    /// <summary>
    /// Formats the specified page number according to a page number style specified for the field start's
    /// parent section.
    /// </summary>
    String formatPageNumber(int pageNumber)
    {
        Section formatSection = (Section)getStart().getAncestor(NodeType.SECTION);
        return NumberConverter.numberToString(pageNumber, formatSection.getPageSetup().getPageNumberStyle(), false);
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_BOLD_SWITCH*/0:
            case /*IS_ITALIC_SWITCH*/1:
                return FieldSwitchType.FLAG;
            case /*ENTRY_TYPE_SWITCH*/2:
            case /*PAGE_RANGE_BOOKMARK_NAME_SWITCH*/3:
            case /*PAGE_NUMBER_REPLACEMENT_SWITCH*/4:
            case /*YOMI_SWITCH*/5:
                return FieldSwitchType.HAS_ARGUMENT;
            default:
                return FieldSwitchType.UNKNOWN;
        }
    }

    /// <summary>
    /// Gets or sets the text of the entry.
    /// </summary>
    public String getText() { return getFieldCodeCache().getArgumentAsString(TEXT_ARGUMENT_INDEX); }
    public void setText(String value) throws Exception { getFieldCodeCache().setArgument(TEXT_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets a node range of the text argument.
    /// </summary>
    NodeRange getTextRange() { return getFieldCodeCache().getArgumentRange(TEXT_ARGUMENT_INDEX); }

    /// <summary>
    /// Gets or sets whether to apply bold formatting to the entry's page number.
    /// </summary>
    public boolean isBold() { return getFieldCodeCache().hasSwitch(IS_BOLD_SWITCH); }
    public void isBold(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_BOLD_SWITCH, value); }

    /// <summary>
    /// Gets or sets an index entry type.
    /// </summary>
    public String getEntryType() { return getFieldCodeCache().getSwitchArgumentAsString(ENTRY_TYPE_SWITCH); }
    public void setEntryType(String value) throws Exception { getFieldCodeCache().setSwitch(ENTRY_TYPE_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether to apply italic formatting to the entry's page number.
    /// </summary>
    public boolean isItalic() { return getFieldCodeCache().hasSwitch(IS_ITALIC_SWITCH); }
    public void isItalic(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_ITALIC_SWITCH, value); }

    /// <summary>
    /// Gets or sets the name of the bookmark that marks a range of pages that is inserted as the entry's page number.
    /// </summary>
    public String getPageRangeBookmarkName() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_RANGE_BOOKMARK_NAME_SWITCH); }
    public void setPageRangeBookmarkName(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_RANGE_BOOKMARK_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets text used in place of a page number.
    /// </summary>
    public String getPageNumberReplacement() { return getFieldCodeCache().getSwitchArgumentAsString(PAGE_NUMBER_REPLACEMENT_SWITCH); }
    public void setPageNumberReplacement(String value) throws Exception { getFieldCodeCache().setSwitch(PAGE_NUMBER_REPLACEMENT_SWITCH, value); }

    /// <summary>
    /// Gets a node range of the text argument to be used in place of a page number.
    /// </summary>
    NodeRange getPageNumberReplacementRange() { return getFieldCodeCache().getSwitchArgumentRange(PAGE_NUMBER_REPLACEMENT_SWITCH); }

    /// <summary>
    /// Gets or sets the yomi (first phonetic character for sorting indexes) for the index entry
    /// </summary>
    public String getYomi() { return getFieldCodeCache().getSwitchArgumentAsString(YOMI_SWITCH); }
    public void setYomi(String value) throws Exception { getFieldCodeCache().setSwitch(YOMI_SWITCH, value); }

    static final int TEXT_ARGUMENT_INDEX = 0;

    private static final String IS_BOLD_SWITCH = "\\b";
    private static final String ENTRY_TYPE_SWITCH = "\\f";
    private static final String IS_ITALIC_SWITCH = "\\i";
    private static final String PAGE_RANGE_BOOKMARK_NAME_SWITCH = "\\r";
    private static final String PAGE_NUMBER_REPLACEMENT_SWITCH = "\\t";
    private static final String YOMI_SWITCH = "\\y";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\b",
		"\\i",
		"\\f",
		"\\r",
		"\\t",
		"\\y"
	);

}

