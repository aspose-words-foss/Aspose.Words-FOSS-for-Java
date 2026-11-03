// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/10/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import java.util.Collection;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;
import com.aspose.ms.System.IDisposable;


/// <summary>
/// Implements the EQ field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldEQ extends Field
{
    /// <summary>
    /// Returns Office Math object corresponded to the EQ field.
    /// </summary>
    /// <returns>
    /// Returns <c>null</c> if field code is empty or invalid, otherwise an <see cref="OfficeMath"/> instance.
    /// </returns>
    public OfficeMath asOfficeMath() throws Exception
    {
        return ms.as(eqFieldCodeToOfficeMath(), OfficeMath.class);
    }

    /*override*/ NodeRange getFakeResult() throws Exception
    {
        Node eq = eqFieldCodeToOfficeMath();
        Paragraph paragraph = getFakePara();
        paragraph.appendChild(eq);

        return new NodeRange(eq, eq);
    }

    /// <summary>
    /// Creates <see cref="Paragraph"/> for Office Math object.
    /// </summary>
    private Paragraph getFakePara()
    {
        Paragraph paragraph = new Paragraph(getDocument(), AttrCollectionExtension.deepClone(getStart().getParentParagraph().getParaPr()), new RunPr());

        Cell parentCell = getStart().getParentParagraph().getParentCell();
        // WORDSNET-28592 Save cell settings for created Office Math object.
        if (parentCell != null)
        {
            Table table = new Table(getDocument());
            Row row = new Row(getDocument());
            table.appendChild(row);
            Cell cell = new Cell(getDocument(), parentCell.getCellPr());
            cell.appendChild(paragraph);
            row.appendChild(cell);
        }

        return paragraph;
    }

    /// <summary>
    /// Parses EQ field code and converts it to <see cref="OfficeMath"/> node,
    /// that can be rendered using the corresponding ToAps converter.
    /// </summary>
    private Node eqFieldCodeToOfficeMath() throws Exception
    {
        // EQ field consists of set of \control Word tokens and data enclosed in parentheses.
        // First token specifies which type of math we have, it can be:
        // \a \b \d \f \i \l \o \r \s and \x.
        // Each can be followed by its optional tokens, that specifies options of the current math.
        // Main token and optional tokens are followed by data enclosed in parentheses.
        // Data items are separated by coma or semicolon (depending on culture).
        // Each data item can be a new token.

        RichString fieldCode = FieldEQCodeParser.parse(this);

        try
        {
            // WORDSNET-8831 If we encounter an empty EQ field, we should show nothing,
            // that is why return an empty run here.
            fieldCode = fieldCode.trim();
            if (fieldCode.getLength() <= 0)
                return new Run(getDocument());

            mRunPr = AttrCollectionExtension.deepClone(getStart().getRunPr());

            // Enclose whole formula in OfficeMath paragraph and OMath.
            // This is how most of normal OfficeMaths look.
            OfficeMath para = new OfficeMath(getDocument(), new MathObjectOMathPara(), mRunPr);
            OfficeMath oMath = new OfficeMath(getDocument(), new MathObjectOMath(), mRunPr);
            // Set flag that indicates the OfficeMath is the result of EQ field conversion.
            // Flag is set only for top level OfficeMath.
            para.isConvertedFromEQ(true);
            para.appendChild(oMath);
            addChildren(oMath, readMathNodes(fieldCode));

            return para;
        }
        catch(Exception e)
        {
            // Return "Error!", if the code did not manage to convert EQ field to OffceMath.
            Run errorRun = new Run(getDocument(), "Error!");
            errorRun.setRunPr(AttrCollectionExtension.deepClone(getStart().getRunPr()));
            errorRun.getFont().setBold(true);
            return errorRun;
        }
    }

    /// <summary>
    /// The core method for reading EQ field, reads field code character by character
    /// and builds the corresponding nodes collection.
    /// </summary>
    private ArrayList<Node> readMathNodes(RichString fieldCode)
    {
        ArrayList<Node> mathNodes = new ArrayList<Node>();

        // There is nothing to parse, return empty collection.
        if (fieldCode == null || fieldCode.getLength() == 0)
            return mathNodes;

        StringBuilder currentToken = new StringBuilder();
        RichStringBuilder currentData = new RichStringBuilder();
        ArrayList<String> tokens = new ArrayList<String>();
        ArrayList<RichString> data = new ArrayList<RichString>();
        ArrayList<RichChar> openBrackets = new ArrayList<RichChar>();

        for (int i = 0; i < fieldCode.getLength(); i++)
        {
            RichChar c = fieldCode.getInternal(i);

            // WORDSNET-28618 If field EQ contains underline settings, the mathematical formula must also be underlined.
            setUnderlineSettings(c);
       
            if (isTokenStart(c.getCharacter()) && (openBrackets.size() == 0))
            {
                readTokenStart(c, currentToken, tokens);
            }
            else if (isDataStart(c.getCharacter()))
            {
                readDataStart(c, currentToken, tokens, openBrackets, currentData);
            }
            else if (isDataEnd(c.getCharacter()))
            {
                // WORDSNET-28652 Empty brackets () in a displacement EQ field indicate that the displacement is
                // applied to the following character.
                if (tokens.size() > 0 && isDisplacementToken(tokens.get(tokens.size() - 1)) &&
                    (openBrackets.size() == 1) && (i < fieldCode.getLength() - 1))
                {
                    msArrayList.add(data, fieldCode.getInternal(i + 1).toRichString());
                    i++;
                }

                readDataEnd(c, currentToken, tokens, openBrackets, currentData, data, mathNodes);
            }
            else if (isDataSeparator(c.getCharacter()))
            {
                readDataSeparator(c, openBrackets, currentData, data);
            }
            else
            {
                readText(c, currentToken, openBrackets, currentData, mathNodes);
            }
        }

        return mathNodes;
    }

    /// <summary>
    /// Indicates whether the current token specifies left or right displacement
    /// </summary>
    private static boolean isDisplacementToken(String token)
    {
        return msString.startsWith(token, LEFT_DISPLACEMENT, StringComparison.ORDINAL) ||
            msString.startsWith(token, RIGHT_DISPLACEMENT, StringComparison.ORDINAL);
    }

    /// <summary>
    /// Sets underline settings for <see cref="RunPr"/> for the math formula.
    /// </summary>
    private void setUnderlineSettings(RichChar c)
    {
        if (c.getRunPr().getUnderline() == Underline.NONE)
            return;

        mRunPr.setUnderline(c.getRunPr().getUnderline());
        mRunPr.setUnderlineColor(c.getRunPr().getUnderlineColor());
    }

    private static void readTokenStart(RichChar c, StringBuilder currentToken, Collection<String> tokens)
    {
        if (currentToken.length() != 0)
        {
            tokens.add(msString.trim(currentToken.toString()));
            currentToken.setLength(0);
        }

        currentToken.append(c.getCharacter());
    }

    private static void readDataStart(
        RichChar c,
        StringBuilder currentToken,
        Collection<String> tokens,
        Collection<RichChar> openBrackets,
        RichStringBuilder currentData)
    {
        // If length of the current token is 1, it means we just opened it,
        // and open parenthesis should be considered as a part of token.
        if (currentToken.length() == 1)
        {
            currentToken.append(c.getCharacter());
            return;
        }

        if ((currentToken.length() != 0) && (openBrackets.size() == 0))
        {
            tokens.add(msString.trim(currentToken.toString()));
            currentToken.setLength(0);
        }

        if (openBrackets.size() > 0)
            currentData.appendInternal(c);

        openBrackets.add(c);
    }

    private void readDataEnd(
        RichChar c,
        StringBuilder currentToken,
        ArrayList<String> tokens,
        ArrayList<RichChar> openBrackets,
        RichStringBuilder currentData,
        ArrayList<RichString> data,
        Collection<Node> mathNodes)
    {
        // If length of the current token is 1, it means we just opened it,
        // and close parenthesis should be considered as a part of token.
        if (currentToken.length() == 1)
        {
            currentToken.append(c.getCharacter());
            return;
        }

        if (openBrackets.size() == 1)
        {
            data.add(currentData.toRichString());
            currentData.clear();
            currentToken.setLength(0);

            // WORDSNET-11226 Brackets can contain common text.
            if (tokens.size() != 0)
                mathNodes.add(eqToOfficeMath(tokens, data));
            else
                addText(openBrackets.get(0), data.get(0), c, mathNodes);

            tokens.clear();
            data.clear();
        }

        openBrackets.remove(openBrackets.size() - 1);

        if (openBrackets.size() > 0)
            currentData.appendInternal(c);
    }

    private static void readDataSeparator(
        RichChar c,
        Collection<RichChar> openBrackets,
        RichStringBuilder currentData,
        Collection<RichString> data)
    {
        if (openBrackets.size() == 1)
        {
            // WORDSNET-28687 Whitespace inside brackets is significant and must be preserved; therefore, the string
            // should not be trimmed.
            data.add(currentData.toRichString());
            currentData.clear();
        }
        else
        {
            currentData.appendInternal(c);
        }
    }

    private void readText(
        RichChar c,
        StringBuilder currentToken,
        Collection<RichChar> openBrackets,
        RichStringBuilder currentData,
        Collection<Node> mathNodes)
    {
        if (openBrackets.size() > 0)
            currentData.appendInternal(c);
        else if (currentToken.length() != 0)
            currentToken.append(c.getCharacter());
        else
            addText(c, mathNodes);
    }

    private void addText(RichChar c, Collection<Node> mathNodes)
    {
        Run run = new Run(getDocument(), Character.toString(c.getCharacter()));
        run.setRunPr(c.getRunPr());
        mathNodes.add(run);
    }

    private void addText(RichChar openBracket, RichString text, RichChar closeBracket,
        Collection<Node> mathNodes)
    {
        addText(openBracket, mathNodes);

        for (int i = 0; i < text.getLength(); i++)
            addText(text.getInternal(i), mathNodes);

        addText(closeBracket, mathNodes);
    }

    /// <summary>
    /// Converts QE field token and its data to the corresponding <see cref="OfficeMath"/> node.
    /// </summary>
    private OfficeMath eqToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        String keyToken = tokens.get(0);
       
        // \a \b \d \f \i \l \o \r \s and \x.
        // WORDSNET-11226 Token can be uppercase.
        switch (gStringSwitchMap.of(keyToken.toLowerCase()))
        {
            case /*"\\a"*/0:
                return arrayToOfficeMath(tokens, data);
            case /*"\\b"*/1:
                return bracketToOfficeMath(tokens, data);
            case /*"\\d"*/2:
                return displaceToOfficeMath(tokens, data);
            case /*"\\f"*/3:
                return fractionToOfficeMath(data);
            case /*"\\i"*/4:
                return integralToOfficeMath(tokens, data);
            case /*"\\l"*/5:
                return listToOfficeMath(data);
            case /*"\\o"*/6:
                return overstrikeToOfficeMath(tokens, data);
            case /*"\\r"*/7:
                return radicalToOfficeMath(data);
            case /*"\\s"*/8:
                return scriptToOfficeMath(tokens, data);
            case /*"\\x"*/9:
                return boxToOfficeMath(tokens, data);
            default:
                throw new IllegalArgumentException("Unexpected token.");
        }
    }

    /// <summary>
    /// Converts \a EQ token (array) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath arrayToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        MathObjectMatrix matrixObject = new MathObjectMatrix();
        matrixObject.isEqArray(true);
        OfficeMath matrix = new OfficeMath(getDocument(), matrixObject, mRunPr);

        MathMatrixColumnPr columnPr = new MathMatrixColumnPr();
        int columnsCount = 1;

        for (String token : tokens)
        {
            Match match = G_TOKEN_REGEX.match(token);

            String tokenName = msString.trim(match.getGroups().get(1).getValue());
            int tokenValue = StringUtil.hasChars(match.getGroups().get(2).getValue()) ? FormatterPal.parseInt(match.getGroups().get(2).getValue()) : 0;

            switch (gStringSwitchMap.of(tokenName))
            {
                case /*"\\al"*/10:
                    columnPr.setHorizontalAlignment(HorizontalAlignment.LEFT);
                    break;
                case /*"\\ac"*/11:
                    columnPr.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    break;
                case /*"\\ar"*/12:
                    columnPr.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                    break;
                case /*"\\co"*/13:
                    // WORDSNET-19803 The number of columns cannot be less than 1.
                    columnsCount = Math.max(columnsCount, tokenValue);
                    break;
                case /*"\\vs"*/14:
                    matrixObject.setRowSpacingRule(MathSpacingRule.EXACTLY);
                    matrixObject.setRowSpacing(ConvertUtilCore.pointToTwip(tokenValue));
                    break;
                case /*"\\hs"*/15:
                    matrixObject.setColumnSpacingRule(MathSpacingRule.EXACTLY);
                    matrixObject.setColumnGap(ConvertUtilCore.pointToTwip(tokenValue));
                    break;
                default:
                    break;
            }
        }

        // Add columns.
        for (int i = 0; i < columnsCount; i++)
            matrixObject.getColumnPrCollection().add(columnPr.deepClone());
        // Set the minimum width. This is an experimental value.
        matrixObject.setMinimumColumnWidth(25);

        // Add rows.
        int currentColumn = 0;
        int rowCount = 0;
        OfficeMath currentRow = null;

        for (RichString item : data)
        {
            // Create a new row if needed
            if (currentRow == null || currentColumn == columnsCount)
            {
                currentRow = createMatrixRow(matrix, mRunPr, false);
                currentColumn = 0;
                rowCount++;
            }

            MathObjectArgumentBase argBase = new MathObjectArgumentBase(MathObjectType.ARGUMENT);
            OfficeMath argument = new OfficeMath(getDocument(), argBase,mRunPr);
            addChildren(argument, readMathNodes(item));
            currentRow.appendChild(argument);
            currentColumn++;
        }

        // WORDSNET-28688 Use enlarged row spacing if rows count is greater than 2. The method is experimental.
        if (matrixObject.getRowSpacingRule() == MathSpacingRule.DEFAULT && rowCount > 2)
        {
            matrixObject.setRowSpacingRule(MathSpacingRule.EXACTLY);
            matrixObject.setRowSpacing(ConvertUtilCore.pointToTwip(2.5));
        }

        return matrix;
    }

    /// <summary>
    /// Converts \b EQ token (bracket) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath bracketToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        MathObjectDelimiter bracketsObject = new MathObjectDelimiter();
        String prevToken = "";

        for (String token : tokens)
            switch (gStringSwitchMap.of(token))
            {
                case /*"\\b"*/1:
                    prevToken = token;
                    break;
                case /*"\\rc"*/16:
                case /*"\\lc"*/17:
                case /*"\\bc"*/18:
                    // WORDSNET-19803 If \\rc,\\lc,\\bc are specified, then the default character for brackets is empty.
                    if ("\\b".equals(prevToken))
                    {
                        final char EMPTY_CHAR = Character.MIN_VALUE;
                        bracketsObject.setBeginningCharacter(EMPTY_CHAR);
                        bracketsObject.setEndingCharacter(EMPTY_CHAR);
                    }
                    prevToken = token;
                    break;
                default:
                {
                    char c = token.length() > 1 ? token.charAt(1) : ' ';
                    switch (gStringSwitchMap.of(prevToken))
                    {
                        case /*"\\rc"*/16:
                            bracketsObject.setEndingCharacter(c);
                            break;
                        case /*"\\lc"*/17:
                            bracketsObject.setBeginningCharacter(c);
                            break;
                        case /*"\\bc"*/18:
                            bracketsObject.setBeginningCharacter(c);
                            bracketsObject.setEndingCharacter(getClosingBracket(c));
                            break;
                        default:
                            break;
                    }

                    break;
                }
            }

        OfficeMath brackets = new OfficeMath(getDocument(), bracketsObject, mRunPr);
        OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
        addChildren(argument, readMathNodes(data.get(0)));
        brackets.appendChild(argument);

        return brackets;
    }

    /// <summary>
    /// Converts \d EQ token (displace) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath displaceToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        OfficeMath displacement = new OfficeMath(getDocument(), new MathObjectPhantom(), mRunPr);
        OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
        displacement.appendChild(argument);
        for (String token : tokens)
        {
            Match match = G_TOKEN_REGEX.match(token);
            String tokenName = msString.toLowerInvariant(msString.trim(match.getGroups().get(1).getValue()));
            String valueString = match.getGroups().get(2).getValue();
            int tokenValue = StringUtil.hasChars(valueString) ? FormatterPal.parseInt(valueString) : 0;

            switch (gStringSwitchMap.of(tokenName))
            {
                case /*LEFT_DISPLACEMENT*/19:
                    argument.setDisplacement(-tokenValue);
                    break;
                case /*RIGHT_DISPLACEMENT*/20:
                    argument.setDisplacement(tokenValue);
                    break;
                case /*UNDERLINE_SPACE_UP*/21:
                default:
                    break;
            }
        }

        if (data.size() > 0)
            addChildren(argument, readMathNodes(data.get(0)));

        return displacement;
    }

    /// <summary>
    /// Converts \f EQ token (fraction) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath fractionToOfficeMath(ArrayList<RichString> data)
    {
        OfficeMath fraction = new OfficeMath(getDocument(), new MathObjectFraction(), mRunPr);

        OfficeMath numerator = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.NUMERATOR), mRunPr);
        OfficeMath denominator = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.DENOMINATOR), mRunPr);

        fraction.appendChild(numerator);
        fraction.appendChild(denominator);

        addChildren(numerator, readMathNodes(data.get(0)));
        addChildren(denominator, readMathNodes(data.get(1)));

        return fraction;
    }

    /// <summary>
    /// Converts \i EQ token (integral) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath integralToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        MathObjectNAry naryObject = new MathObjectNAry();
        naryObject.setLimitLocation(MathLimitLocation.UNDER_OVER);
        String prevToken = "";
        naryObject.setGrowToMatchOperand(true);

        for (String token : tokens)
        {
            switch (gStringSwitchMap.of(token))
            {
                case /*"\\su"*/22:
                    naryObject.setCharacter('\u2211');
                    break;
                case /*"\\pr"*/23:
                    naryObject.setCharacter('\u220F');
                    break;
                case /*"\\in"*/24:
                    naryObject.setLimitLocation(MathLimitLocation.SUBSCRIPT_SUPERSCRIPT);
                    break;
                case /*"\\fc"*/25:
                case /*"\\vc"*/26:
                    prevToken = token;
                    break;
                default:
                {
                    switch (gStringSwitchMap.of(prevToken))
                    {
                        case /*"\\fc"*/25:
                            naryObject.setCharacter(token.charAt(1));
                            naryObject.setGrowToMatchOperand(false);
                            break;
                        case /*"\\vc"*/26:
                            naryObject.setCharacter(token.charAt(1));
                            naryObject.setGrowToMatchOperand(true);
                            break;
                        default:
                            break;
                    }

                    break;
                }
            }
        }

        OfficeMath nary = new OfficeMath(getDocument(), naryObject, mRunPr);

        OfficeMath subscriptPart = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.SUBSCRIPT_PART), mRunPr);
        OfficeMath superscriptPart = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.SUPERSCRIPT_PART), mRunPr);
        OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);

        nary.appendChild(subscriptPart);
        nary.appendChild(superscriptPart);
        nary.appendChild(argument);

        addChildren(subscriptPart, readMathNodes(data.get(0)));
        addChildren(superscriptPart, readMathNodes(data.get(1)));
        addChildren(argument, readMathNodes(data.get(2)));

        return nary;
    }

    /// <summary>
    /// Converts \l EQ token (list) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath listToOfficeMath(ArrayList<RichString> data)
    {
        // List must display list of arguments separated by data delimiter.
        MathObjectDelimiter delimiterObject = new MathObjectDelimiter();
        delimiterObject.setBeginningCharacter('\u0000');
        delimiterObject.setEndingCharacter('\u0000');
        delimiterObject.setSeparatorCharacter(getDataSeparator());

        OfficeMath delimiter = new OfficeMath(getDocument(), delimiterObject, mRunPr);

        for (RichString val : data)
        {
            OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
            delimiter.appendChild(argument);
            addChildren(argument, readMathNodes(val));
        }

        return delimiter;
    }

    /// <summary>
    /// Converts \o EQ token (overstrike) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath overstrikeToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        MathObjectMatrix matrixObject = new MathObjectMatrix();
        matrixObject.setBaseJustification(MathBaseJustification.TOP);
        OfficeMath matrix = new OfficeMath(getDocument(), matrixObject, mRunPr);
        OfficeMath row = createMatrixRow(matrix, mRunPr, true);

        MathMatrixColumnPr columnPr = new MathMatrixColumnPr();
        for (String token : tokens)
        {
            switch (gStringSwitchMap.of(token))
            {
                case /*"\\al"*/10:
                    columnPr.setHorizontalAlignment(HorizontalAlignment.LEFT);
                    break;
                case /*"\\ac"*/11:
                    columnPr.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    break;
                case /*"\\ar"*/12:
                    columnPr.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                    break;
                default:
                    break;
            }
        }

        for (RichString val : data)
        {
            OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
            row.appendChild(argument);
            matrixObject.getColumnPrCollection().add(columnPr.deepClone());
            addChildren(argument, readMathNodes(val));
        }

        return matrix;
    }

    /// <summary>
    /// Converts \r EQ token (radical) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath radicalToOfficeMath(ArrayList<RichString> data)
    {
        MathObjectRadical radicalObject = new MathObjectRadical();
        OfficeMath radical = new OfficeMath(getDocument(), radicalObject, mRunPr);

        if (data.size() == 1)
        {
            // Add empty degree as MS Word does.
            OfficeMath degree = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.DEGREE), mRunPr);
            radical.appendChild(degree);

            OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
            addChildren(argument, readMathNodes(data.get(0)));
            radical.appendChild(argument);

            radicalObject.isHideDegree(true);
        }
        else
        {
            OfficeMath degree = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.DEGREE), mRunPr);
            addChildren(degree, readMathNodes(data.get(0)));
            radical.appendChild(degree);

            OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
            addChildren(argument, readMathNodes(data.get(1)));
            radical.appendChild(argument);
        }

        return radical;
    }

    /// <summary>
    /// Converts \s EQ token (superscript or subscript) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath scriptToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        // Super and sub scripts in QE field behaves differently that in OfficeMath,
        // That is why we represent it as matrix with two rows and one column.
        MathObjectMatrix matrixObject = new MathObjectMatrix();
        matrixObject.setRowSpacingRule(MathSpacingRule.EXACTLY);
        matrixObject.getColumnPrCollection().add(new MathMatrixColumnPr());
        OfficeMath matrix = new OfficeMath(getDocument(), matrixObject, mRunPr);

        OfficeMath row0 = createMatrixRow(matrix, mRunPr, false);
        OfficeMath argument0 = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
        row0.appendChild(argument0);

        OfficeMath row1 = createMatrixRow(matrix, mRunPr, false);
        OfficeMath argument1 = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
        row1.appendChild(argument1);

        for (String token : tokens)
        {
            // WORDSNET-27858 The position can be negative. Regex should be changed. 
            Match match = G_TOKEN_REGEX.match(token);

            String tokenName = msString.trim(match.getGroups().get(1).getValue());
            String valueString = match.getGroups().get(2).getValue();

            // Tokens \s, \ai, \di are not currently supported. Do nothing.
            if (!"\\up".equals(tokenName) && !"\\do".equals(tokenName))
                continue;

            // If token is not specified directly, MS Word uses 2 as the default value.
            int tokenValue = StringUtil.hasChars(valueString) ? FormatterPal.parseInt(valueString) : 2;
            ArrayList<Node> nodes = readMathNodes(data.get(0));
            boolean swapArguments = ("\\up".equals(tokenName) && tokenValue < 0) || ("\\do".equals(tokenName) && tokenValue >= 0);
            OfficeMath upArg = swapArguments ? argument1 : argument0;
            OfficeMath downArg = swapArguments ? argument0 : argument1;
            addScriptElements(upArg, downArg, nodes);
            matrixObject.setRowSpacing(ConvertUtilCore.pointToTwip(Math.abs(tokenValue)));
            matrixObject.setBaseJustification(swapArguments ? MathBaseJustification.TOP : MathBaseJustification.BOTTOM);
        }

        return matrix;
    }

    /// <summary>
    /// Adds script elements.
    /// </summary>
    private void addScriptElements(OfficeMath arg0, OfficeMath arg1, ArrayList<Node> nodes)
    {
        addChildren(arg0, nodes);
        // Add dummy row value. If we do not add it, height of row will be zero that might lead into incorrect result.
        RunPr run = getArgumentRunPr(arg0);
        RichString dummyString = RichString.createFromString(ControlChar.SPACE, run);
        ArrayList<Node> dummyNodes = readMathNodes(dummyString);
        addChildren(arg1, dummyNodes);
    }

    /// <summary>
    /// Returns RunPr of the first child run of the specified OfficeMath,
    /// if there are no runs, returns RunPr of the OfficeMath itself.
    /// </summary>
    private static RunPr getArgumentRunPr(OfficeMath math)
    {
        Run run = (Run)math.getChild(NodeType.RUN, 0, true);
        return (run != null) ? run.getRunPr() : math.getRunPr();
    }

    /// <summary>
    /// Converts \b EQ token (box) to the corresponding OfficeMath node.
    /// </summary>
    private OfficeMath boxToOfficeMath(ArrayList<String> tokens, ArrayList<RichString> data)
    {
        MathObjectBorderBox borderBoxObject = new MathObjectBorderBox();

        borderBoxObject.setHideBottomEdge(true);
        borderBoxObject.setHideLeftEdge(true);
        borderBoxObject.setHideRightEdge(true);
        borderBoxObject.setHideTopEdge(true);

        for (String token : tokens)
        {
            switch (gStringSwitchMap.of(token))
            {
                case /*"\\to"*/27:
                    borderBoxObject.setHideTopEdge(false);
                    break;
                case /*"\\bo"*/28:
                    borderBoxObject.setHideBottomEdge(false);
                    break;
                case /*"\\le"*/29:
                    borderBoxObject.setHideLeftEdge(false);
                    break;
                case /*"\\ri"*/30:
                    borderBoxObject.setHideRightEdge(false);
                    break;
                default:
                    break;
            }
        }

        OfficeMath borderBox = new OfficeMath(getDocument(), borderBoxObject, mRunPr);
        OfficeMath argument = new OfficeMath(getDocument(), new MathObjectArgumentBase(MathObjectType.ARGUMENT), mRunPr);
        addChildren(argument, readMathNodes(data.get(0)));
        borderBox.appendChild(argument);

        return borderBox;
    }

    /// <summary>
    /// Creates matrix row and adds it to the parent matrix.
    /// </summary>
    private static OfficeMath createMatrixRow(OfficeMath parentMatrix, RunPr runPr, boolean isOverstrikeRow)
    {
        MathObjectMatrixRow matrixRow = new MathObjectMatrixRow();
        matrixRow.isOverstrikeRow(isOverstrikeRow);
        OfficeMath row = new OfficeMath(parentMatrix.getDocument(), matrixRow, runPr);
        parentMatrix.appendChild(row);

        return row;
    }

    /// <summary>
    /// Adds all nodes from children collection, to the parent node.
    /// </summary>
    private static void addChildren(CompositeNode parent, ArrayList<Node> children)
    {
        for (Node child : children)
            parent.appendChild(child);
    }

    /// <summary>
    /// Returns closing bracket that corresponds the specified open bracket.
    /// If there is no corresponding closing bracket returns the original open bracket.
    /// </summary>
    private static char getClosingBracket(char openBracket)
    {
        switch (openBracket)
        {
            case '{':
                return '}';
            case '[':
                return ']';
            case '(':
                return ')';
            case '<':
                return '>';
            default:
                return openBracket;
        }
    }

    /// <summary>
    /// Returns true if the specified char is token start.
    /// </summary>
    private static boolean isTokenStart(char c)
    {
        return (c == '\\');
    }

    /// <summary>
    /// Returns true if the specified char is data start.
    /// </summary>
    private static boolean isDataStart(char c)
    {
        return (c == '(');
    }

    /// <summary>
    /// Returns true if the specified char is data end.
    /// </summary>
    private static boolean isDataEnd(char c)
    {
        return (c == ')');
    }

    /// <summary>
    /// Returns true if the specified char is data separator.
    /// </summary>
    private static boolean isDataSeparator(char c)
    {
        return (c == getDataSeparator());
    }

    /// <summary>
    /// Returns either semicolon or coma depending on culture.
    /// </summary>
    private static char getDataSeparator() { return FormatterPal.getDecimalSeparatorCurrent() == ',' ? ';' : ','; }

    private static final String LEFT_DISPLACEMENT = "\\ba";
    private static final String RIGHT_DISPLACEMENT = "\\fo";
    private static final String UNDERLINE_SPACE_UP = "\\li";
    private RunPr mRunPr;
    private static /*final*/ Regex G_TOKEN_REGEX = new Regex("(\\\\[^\\d-]+)(-?\\d+)?", RegexOptions.COMPILED);

    private final static class FieldEQCodeParser implements IDisposable
    {
        private /*final*/ NodeEnumerator mNodeEnumerator;
        private /*final*/ FieldCharCounter mFieldCharCounter;

        private FieldEQCodeParser(NodeRange range)
        {
            mNodeEnumerator = new NodeEnumerator(range);
            mFieldCharCounter = new FieldCharCounter();
        }

        static RichString parse(Field field) throws Exception
        {
            FieldArgument argument = field.getFieldCodeCache().getArgument(0);
            if (argument == null)
                return RichString.EMPTY;

            FieldEQCodeParser parser = new FieldEQCodeParser(argument.getRange());
            try /*JAVA: was using*/
        	{
                return parser.parse();
        	}
            finally { if (parser != null) parser.dispose(); }
        }

        private RichString parse() throws Exception
        {
            RichStringBuilder builder = new RichStringBuilder();

            while (true)
            {
                if (!mNodeEnumerator.moveToNextNode(true, false))
                    break;

                mFieldCharCounter.visitNode(getCurrentNode());

                processNode(builder);
            }

            return builder.toRichString();
        }

        private void processNode(RichStringBuilder builder) throws Exception
        {
            switch (getCurrentNode().getNodeType())
            {
                case NodeType.FIELD_START:
                    processField(builder);
                    break;
                case NodeType.RUN:
                    if (mFieldCharCounter.isInFieldCode())
                        break;
                    processRun(builder);
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        private void processField(RichStringBuilder builder) throws Exception
        {
            FieldStart fieldStart = (FieldStart)getCurrentNode();

            if (mFieldCharCounter.getFieldsDepth() != 1)
                return;

            switch (fieldStart.getFieldType())
            {
                case FieldType.FIELD_SYMBOL:
                case FieldType.FIELD_AUTO_NUM:
                case FieldType.FIELD_AUTO_NUM_LEGAL:
                case FieldType.FIELD_AUTO_NUM_OUTLINE:
                case FieldType.FIELD_GO_TO_BUTTON:
                case FieldType.FIELD_MACRO_BUTTON:
                    NodeRange fakeResult = fieldStart.getField().getFakeResult();
                    processNodeRange(builder, fakeResult);
                    break;
                case FieldType.FIELD_EQUATION:
                    // WORDSNET-22098 Append any nested EQ field codes recursively and then convert them to OfficeMath as single field code.
                    builder.appendInternal(parse(fieldStart.getField()));
                    break;
                default:
                    // Do nothing.
                    break;
            }
        }

        private static void processNodeRange(RichStringBuilder builder, NodeRange range)
        {
            if (range == null)
                return;

            for (Node node : range)
            {
                Run run = ms.as(node, Run.class);
                if (run != null)
                    builder.append(run.getText(), run.getRunPr());
            }
        }

        private void processRun(RichStringBuilder builder)
        {
            if (getCurrentNodePosition().isEnd())
                return;

            Run run = (Run)getCurrentNode();

            builder.append(run.getText().charAt(getCurrentNodeOffset()), run.getRunPr());
        }

        private Node getCurrentNode() { return mNodeEnumerator.getCurrentNode(); }

        private DocumentPosition getCurrentNodePosition() { return mNodeEnumerator.getCurrentPosition(); }

        private int getCurrentNodeOffset() { return mNodeEnumerator.getCurrentPosition().getOffset(); }

        public void /*IDisposable.*/dispose()
        {
            mNodeEnumerator.dispose();
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"\\a",
		"\\b",
		"\\d",
		"\\f",
		"\\i",
		"\\l",
		"\\o",
		"\\r",
		"\\s",
		"\\x",
		"\\al",
		"\\ac",
		"\\ar",
		"\\co",
		"\\vs",
		"\\hs",
		"\\rc",
		"\\lc",
		"\\bc",
		"\\ba",
		"\\fo",
		"\\li",
		"\\su",
		"\\pr",
		"\\in",
		"\\fc",
		"\\vc",
		"\\to",
		"\\bo",
		"\\le",
		"\\ri"
	);

}

