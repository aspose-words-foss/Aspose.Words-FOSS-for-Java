// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;


/// <summary>
/// Provides a set of methods that build a field code string based on an existing field code, adding, replacing,
/// or removing an argument or a switch.
/// </summary>
/*static*/ class FieldCodeUpdater
{
	/* Simulation of static class by using private constructor */
	private FieldCodeUpdater()
	{}

    static String encodeArgument(String argumentText)
    {
        return encodeArgument(argumentText, false, false);
    }

    static String encodeArgument(String argumentText, boolean forceDoubleQuotes)
    {
        return encodeArgument(argumentText, false, forceDoubleQuotes);
    }

    private static String encodeArgument(String argumentText, boolean isVerbatim, boolean forceDoubleQuotes)
    {
        StringBuilder builder = new StringBuilder(argumentText.length());
        boolean containsWhitespaces = false;
        for (int i = 0; i < argumentText.length(); i++)
        {
        	char symbol = argumentText.charAt(i);
            if (FieldCodeParser.isDoubleQuote(symbol) || (symbol == '\\'))
                builder.append('\\');

            builder.append(symbol);

            containsWhitespaces = containsWhitespaces || msChar.isWhiteSpace(symbol);
        }

        // If the argument is an empty string or contains any whitespaces, enclose it in double quotes.
        if (!isVerbatim && (forceDoubleQuotes || (builder.length() == 0) || containsWhitespaces))
            builder.insert(0, '"').append('"');

        return builder.toString();
    }

    static void appendSwitch(FieldCode fieldCode, String switchName, String switchArgument)
    {
        // Picture switch should always be first.
        if ((FieldSwitch.isPictureSwitch(switchName)))
            appendSwitchBeforeFirstSwitch(fieldCode, switchName, switchArgument);
        else
            appendSwitchToEnd(fieldCode, switchName, switchArgument);
    }

    private static void appendSwitchBeforeFirstSwitch(FieldCode fieldCode, String switchName, String switchArgument)
    {
        if (fieldCode.getSwitches().size() == 0)
        {
            appendSwitchToEnd(fieldCode, switchName, switchArgument);
            return;
        }

        DocumentBuilder builder = new DocumentBuilder(fieldCode.getField().fetchDocument());
        moveBeforeFirstSwitch(fieldCode, builder);

        builder.write(switchName);
        if (switchArgument != null)
            writeArgumentText(builder, true, switchArgument, false);

        builder.write(" ");
    }

    private static void appendSwitchToEnd(FieldCode fieldCode, String switchName, String switchArgument)
    {
        DocumentBuilder builder = new DocumentBuilder(fieldCode.getField().fetchDocument());
        moveBeforeFieldCodeEnd(fieldCode, builder);

        builder.write(msString.format(" {0}", switchName));

        if (switchArgument != null)
            writeArgumentText(builder, true, switchArgument, false);
    }

    static void removeSwitch(FieldSwitch fieldSwitch) throws Exception
    {
        NodeRange range = fieldSwitch.getArgument() == null ?
            fieldSwitch.getRange() :
            new NodeRange(fieldSwitch.getRange().getStart(), fieldSwitch.getArgument().getRange().getEnd());

        range.remove();
    }

    static void appendArguments(FieldCode fieldCode, String[] argumentsTexts, boolean lastArgumentIsVerbatim)
    {
        DocumentBuilder builder = new DocumentBuilder(fieldCode.getField().fetchDocument());
        boolean separateFromPrevious = false;
        boolean separateFromNext = false;

        boolean moved = false;
        if (fieldCode.getSwitches().size() != 0)
        {
            moved = moveAfterLastArgument(fieldCode, builder);
            separateFromNext = moved;
        }

        if (!moved)
        {
            moveBeforeFieldCodeEnd(fieldCode, builder);
            separateFromPrevious = true;
        }

        for (int i = 0; i < argumentsTexts.length; i++)
        {
            String argumentText = argumentsTexts[i];
            boolean isVerbatimArgument = lastArgumentIsVerbatim && (i == argumentsTexts.length - 1);

            writeArgumentText(builder, separateFromPrevious, argumentText, isVerbatimArgument);

            if (separateFromNext && !isVerbatimArgument)
                builder.write(" ");
        }
    }

    static void updateArgument(FieldCode fieldCode, FieldArgument argument, String argumentText, boolean isVerbatimArgument) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder(fieldCode.getField().fetchDocument());
        NodeRange argumentActualRange = getFieldArgumentActualRange(argument);
        builder.moveTo(argumentActualRange.getStart().getNode());

        writeArgumentText(builder, false, argumentText, isVerbatimArgument);

        argumentActualRange.remove();
    }

    static void removeArgument(FieldArgument argument) throws Exception
    {
        getFieldArgumentActualRange(argument).remove();
    }

    static void appendTrailingSpace(FieldCode fieldCode)
    {
        DocumentBuilder builder = new DocumentBuilder(fieldCode.getField().fetchDocument());
        moveBeforeFieldCodeEnd(fieldCode, builder);

        builder.write(" ");
    }

    private static NodeRange getFieldArgumentActualRange(FieldArgument argument)
    {
        return !argument.getCompleteFieldRange().isVoid()
                   ? argument.getCompleteFieldRange()
                   : argument.getRange();
    }

    private static void moveBeforeFieldCodeEnd(FieldCode fieldCode, DocumentBuilder builder)
    {
        Node refNode = fieldCode.getField().getFieldCodeEnd();
        builder.moveTo(refNode);
    }

    private static boolean moveAfterLastArgument(FieldCode fieldCode, DocumentBuilder builder)
    {
        FieldArgument lastArgument = fieldCode.getArgument(fieldCode.getArguments().size() - 1);
        if (lastArgument == null)
        {
            moveBeforeFirstSwitch(fieldCode, builder);
            return true;
        }

        FieldSwitch switchAfterLastArgument = null;
        boolean lastArgumentReached = false;
        for (Object element : fieldCode.getElements())
        {
            if (lastArgumentReached)
            {
                switchAfterLastArgument = (FieldSwitch)element;
                break;
            }

            if (element == lastArgument)
                lastArgumentReached = true;
        }

        if (switchAfterLastArgument == null)
            return false;

        Node refNode = switchAfterLastArgument.getRange().getStart().getNode();
        builder.moveTo(refNode);
        return true;
    }

    private static void moveBeforeFirstSwitch(FieldCode fieldCode, DocumentBuilder builder)
    {
        FieldSwitch fieldSwitch = fieldCode.getSwitch(0);
        Node refNode = fieldSwitch.getRange().getStart().getNode();
        builder.moveTo(refNode);
    }

    private static void writeArgumentText(DocumentBuilder builder, boolean separate, String argumentText, boolean isVerbatimArgument)
    {
        if (separate)
            builder.write(" ");

        builder.write(argumentText == null ? "\"\"" : encodeArgument(argumentText, isVerbatimArgument, false));
    }
}

