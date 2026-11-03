// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.Debug;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Implements a SYMBOL field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Retrieves the character whose code point value is specified in decimal or hexadecimal.
/// </remarks>
public class FieldSymbol extends Field implements IFieldCodeTokenInfoProvider
{
    /*override*/ NodeRange getFakeResult()
    {
        Inline sourceNode = getResultFormatSourceNode();
        RunPr resultFormat = sourceNode != null
            ? AttrCollectionExtension.deepClone(sourceNode.getRunPr())
            : null;
        ParaPr resultParagraphFormat = sourceNode != null && sourceNode.getParentParagraph() != null
            ? AttrCollectionExtension.deepClone(sourceNode.getParentParagraph().getParaPr())
            : null;

        Run run = new Run(getDocument(), getResultText(), resultFormat);
        Paragraph para = new Paragraph(getDocument(), resultParagraphFormat, new RunPr());
        para.appendChild(run);

        if (StringUtil.hasChars(getFontName()))
            run.getFont().setName(getFontName());
        if (getFontSizeAsDouble() > 0d)
            run.getFont().setSize(getFontSizeAsDouble());

        return new NodeRange(run, run);
    }

    private String getResultText()
    {
        // WORDSNET-11095 Field can have incorrect syntax, without character code.
        if (!getFieldCodeCache().hasArgument(0))
            return ERRONEOUS_RESULT;

        int intCharCode = getIntCharacterCode();

        // This condition includes int.MinValue (parsing error), negative values, and zero.
        if (intCharCode <= 0)
            return ERRONEOUS_RESULT;

        if (!isUnicode())
        {
            // WORDSNET-12021 The symbol is considered ANSI unless the /u char is specified explicitly.

            if (intCharCode > 0xFF)
                return ERRONEOUS_RESULT;

            return new String(G_ANSI_ENCODING.getChars(new byte[] { (byte)intCharCode }));
        }
        else
        {
            // Since the .NET string is Unicode string, we can simply cast the character code.
            return Character.toString(((char)intCharCode));
        }
    }

    private Inline getResultFormatSourceNode()
    {
        for (Node node : getFieldCodeRange())
        {
            Inline inline = ms.as(node, Inline.class);
            if (inline != null)
                return inline;
        }

        Debug.fail("Could not find source node");

        return null;
    }

    /// <summary>
    /// Gets or sets the character's code point value in decimal or hexadecimal.
    /// </summary>
    public String getCharacterCode() { return getFieldCodeCache().getArgumentAsString(CHARACTER_CODE_ARGUMENT_INDEX); }
    public void setCharacterCode(String value) throws Exception { getFieldCodeCache().setArgument(CHARACTER_CODE_ARGUMENT_INDEX, value); }

    /// <summary>
    /// Gets the code of the character retrieved by the field.
    /// </summary>
    private int getIntCharacterCode()
    {
        return isHex()
            ? FormatterPal.tryParseHex(getCharacterCode().substring(2))
            : FormatterPal.tryParseInt(getCharacterCode());
    }

    @ReservedForInternalUse @Deprecated
    public /*FieldSwitchType*/int /*IFieldCodeTokenInfoProvider.*/getSwitchType(String switchName)
    {
        switch (gStringSwitchMap.of(switchName))
        {
            case /*IS_ANSI_SWITCH*/0:
            case /*DONT_AFFECTS_LINE_SPACING_SWITCH*/1:
            case /*IS_SHIFT_JIS_SWITCH*/2:
            case /*IS_UNICODE_SWITCH*/3:
                {
                    return FieldSwitchType.FLAG;
                }
            case /*FONT_NAME_SWITCH*/4:
            case /*FONT_SIZE_SWITCH*/5:
                {
                    return FieldSwitchType.HAS_ARGUMENT;
                }
            default:
                {
                    return FieldSwitchType.UNKNOWN;
                }
        }
    }

    private boolean isHex()
    {
        return msString.startsWith(getCharacterCode(), "0x", StringComparison.ORDINAL) ||
            msString.startsWith(getCharacterCode(), "0X", StringComparison.ORDINAL);
    }

    /// <summary>
    /// Gets or sets the name of the font of the character retrieved by the field.
    /// </summary>
    public String getFontName() { return getFieldCodeCache().getSwitchArgumentAsString(FONT_NAME_SWITCH); }
    public void setFontName(String value) throws Exception { getFieldCodeCache().setSwitch(FONT_NAME_SWITCH, value); }

    /// <summary>
    /// Gets or sets the size in points of the font of the character retrieved by the field.
    /// </summary>
    public String getFontSize() { return getFieldCodeCache().getSwitchArgumentAsString(FONT_SIZE_SWITCH); }
    public void setFontSize(String value) throws Exception { getFieldCodeCache().setSwitchAsDouble(FONT_SIZE_SWITCH, value); }

    private double getFontSizeAsDouble() { return getFieldCodeCache().getSwitchArgumentAsDouble(FONT_SIZE_SWITCH); }

    /// <summary>
    /// Gets or sets whether the character code is interpreted as the value of an ANSI character.
    /// </summary>
    public boolean isAnsi() { return getFieldCodeCache().hasSwitch(IS_ANSI_SWITCH); }
    public void isAnsi(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_ANSI_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the character code is interpreted as the value of a Unicode character.
    /// </summary>
    public boolean isUnicode() { return getFieldCodeCache().hasSwitch(IS_UNICODE_SWITCH); }
    public void isUnicode(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_UNICODE_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the character code is interpreted as the value of a SHIFT-JIS character.
    /// </summary>
    public boolean isShiftJis() { return getFieldCodeCache().hasSwitch(IS_SHIFT_JIS_SWITCH); }
    public void isShiftJis(boolean value) throws Exception { getFieldCodeCache().setSwitch(IS_SHIFT_JIS_SWITCH, value); }

    /// <summary>
    /// Gets or sets whether the character retrieved by the field affects the line spacing of the paragraph.
    /// </summary>
    public boolean getDontAffectsLineSpacing() { return getFieldCodeCache().hasSwitch(DONT_AFFECTS_LINE_SPACING_SWITCH); }
    public void setDontAffectsLineSpacing(boolean value) throws Exception { getFieldCodeCache().setSwitch(DONT_AFFECTS_LINE_SPACING_SWITCH, value); }

    private static final int CHARACTER_CODE_ARGUMENT_INDEX = 0;

    private static final String IS_ANSI_SWITCH = "\\a";
    private static final String FONT_NAME_SWITCH = "\\f";
    private static final String DONT_AFFECTS_LINE_SPACING_SWITCH = "\\h";
    private static final String IS_SHIFT_JIS_SWITCH = "\\j";
    private static final String FONT_SIZE_SWITCH = "\\s";
    private static final String IS_UNICODE_SWITCH = "\\u";

    /// <summary>
    /// This string is displayed when symbol code parsing goes wrong.
    /// </summary>
    private static final String ERRONEOUS_RESULT = "###";

    private static /*final*/ Encoding G_ANSI_ENCODING = Encoding.getEncoding(1252);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\a",
		"\\h",
		"\\j",
		"\\u",
		"\\f",
		"\\s"
	);

}

