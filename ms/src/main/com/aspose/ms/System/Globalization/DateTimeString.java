// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/datetimeparse.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;

//
// This is a string parsing helper which wraps a String object.
// It has a Index property which tracks
// the current parsing pointer of the string.
//
public class DateTimeString
{
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public char getCurrent() {
        return current;
    }

    public void setCurrent(char current) {
        this.current = current;
    }

    //
    // Value propery: stores the real string to be parsed.
    //
    private String value;

    //
    // Index property: points to the character that we are currently parsing.
    //
    private int index;

    // The length of Value string.
    private int len;

    // The current chracter to be looked at.
    private char current;

    private msDateTimeFormatInfo dtfi;

    private static char[] whiteSpaceChecks = new char[]{' ', '\u00A0'};
    private static int maxDateTimeNumberDigits = 8;
    public static final String GMTName = "GMT";
    public static final String ZuluName = "Z";


    public DateTimeString(String dateTimeString, msDateTimeFormatInfo dtfi)
    {
        this.dtfi = dtfi;
        this.index = -1;
        this.value = dateTimeString;
        this.len = dateTimeString.length();
        current = '\0';
    }

    //
    // Advance the Index.
    // Return true if Index is NOT at the end of the string.
    //
    // Typical usage:
    // while (str.GetNext())
    // {
    //     char ch = str.GetChar()
    // }
    public boolean getNext()
    {
        index++;
        if (index < len)
        {
            current = value.charAt(index);
            return true;
        }
        return false;
    }

    public boolean advance(int count)
    {
        index += count;
        if (index < len)
        {
            current = value.charAt(index);
            return (true);
        }
        return (false);
    }

    // Used by DateTime.TryParse() to get the next token.
    public TokenTypeValue getRegularToken(msDateTimeFormatInfo dtfi)
    {
        TokenTypeValue token = new TokenTypeValue();
        if (index >= len)
        {
            token.type = TokenType.EndOfString;
            return token;
        }

        token.type = TokenType.UnknownToken;

        //Start:
        //JAVA-added method to resolve 'Start' label and goto to it:
        nextRegularToken(token, dtfi);
        return token;
    }

    //JAVA-added method to resolve 'Start' label and goto to it
    private void nextRegularToken(TokenTypeValue token, msDateTimeFormatInfo dtfi)
    {
        if (isDigit(current))
        {
            token.value = current - '0';
            int start = index;
            int value;

            while (++index < len)
            {
                current = this.value.charAt(index);
                value = current - '0';
                if (value >= 0 && value <= 9)
                    token.value = token.value * 10 + value;
                else
                    break;
            }

            if (index - start > DateTimeString.maxDateTimeNumberDigits)
            {
                token.type = TokenType.NumberToken;
                token.value = -1;
            }
            else if (index - start < 3)
                token.type = TokenType.NumberToken;
            else
                token.type = TokenType.YearNumberToken;
        }
        else if (isWhiteSpace(current))
        {
            while (++index < len)
            {
                current = this.value.charAt(index);
                if (!(isWhiteSpace(current)))
                {
                    nextRegularToken(token, dtfi);// goto Start;
                    return;
                }
            }
            token.type = TokenType.EndOfString;
        }
        else
        {
            dtfi.tokenize(TokenType.RegularTokenMask, token, this);
        }
    }

    //
    // This is the lexer. Check the character at the current index, and put the found token in dtok and
    // some raw date/time information in raw.
    //
    public static boolean lex(int dps, /*ref*/ DateTimeString str, /*ref*/ DateTimeToken dtok, /*ref*/ DateTimeRawInfo raw, /*ref*/ DateTimeResult result, /*ref*/ msDateTimeFormatInfo dtfi)
    {
        // JAVA-changed: TokenTypeValue reference tye encapsulates TokenType enum and int tokenValue
        // (both value types) in case to remove out and ref params.
        TokenTypeValue tokenTypeValue;

        SeparatorToken sep = null;
        dtok.dtt = DTT.Unk;  // Assume the token is unkown.

        tokenTypeValue = str.getRegularToken(dtfi);

        // Look at the regular token.
        switch (tokenTypeValue.type)
        {
            case TokenType.NumberToken:
            case TokenType.YearNumberToken:
                if (raw.numCount == 3 || tokenTypeValue.value == -1)
                {
                    //result.SetFailure(ParseFailureKind.Format, "Format_BadDateTime", null);
                    return false;
                }

                //
                // This is a digit.
                //
                // If the previous parsing state is DS.T_NNt (like 12:01), and we got another number,
                // so we will have a terminal state DS.TX_NNN (like 12:01:02).
                // If the previous parsing state is DS.T_Nt (like 12:), and we got another number,
                // so we will have a terminal state DS.TX_NN (like 12:01).
                //
                // Look ahead to see if the following character is a decimal point or timezone offset.
                // This enables us to parse time in the forms of:
                //  "11:22:33.1234" or "11:22:33-08".

                if ((dps == DS.T_NNt) && (str.index < (str.len - 1)))
                {
                    char nextCh = str.value.charAt(str.index);
                    if (nextCh == '.')
                    {
                        // While ParseFraction can fail, it just means that there were no digits after
                        // the dot. In this case ParseFraction just ----s the dot. This is actually
                        // valid for cultures like Albanian, that join the time marker to the time with
                        // with a dot: e.g. "9:03.MD"
                        raw.fraction = DateTimeParse.parseFraction(str);
                    }
                }

                if (((dps == DS.T_NNt) || (dps == DS.T_Nt)) && (str.index < (str.len - 1)))
                {
                    char c = str.value.charAt(str.index);
                    int num3 = 0;
                    while (msChar.isWhiteSpace(c) && ((str.index + num3) < (str.len - 1)))
                    {
                        num3++;
                        c = str.value.charAt(str.index + num3);
                    }
                    switch (c)
                    {
                        case '+':
                        case '-':
                            str.index += num3;

                            result.flags |= ParseFlags.TimeZoneUsed;
                            TimeSpan timeZone = DateTimeParse.parseTimeZone(str);
                            if (timeZone == null)
                                return false;
                            result.timeZoneOffset = timeZone;
                            break;
                    }
                }

                dtok.num = tokenTypeValue.value;
                if (tokenTypeValue.type != TokenType.YearNumberToken)
                {
                    sep = str.getSeparatorToken();
                    switch (sep.type)
                    {
                        case TokenType.SEP_End:
                            dtok.dtt = DTT.NumEnd;
                            raw.addNumber(dtok.num);
                            return true;
                        case TokenType.SEP_Space:
                            dtok.dtt = DTT.NumSpace;
                            raw.addNumber(dtok.num);
                            return true;
                        case TokenType.SEP_Am:
                        case TokenType.SEP_Pm:
                            dtok.dtt = DTT.NumAmpm;
                            raw.timeMark = (sep.type == TokenType.SEP_Am) ? TM.AM : TM.PM;
                            raw.addNumber(dtok.num);
                            if (((dps != DS.T_NNt) && (dps != DS.T_Nt)) || handleTimeZone(str, result))
                                return true;

                            return false;

                        case TokenType.SEP_Time:
                            dtok.dtt = DTT.NumTimesep;
                            raw.addNumber(dtok.num);
                            return true;

                        case TokenType.SEP_YearSuff:
                            dtok.num = DateTimeParse.toFourDigitYear(tokenTypeValue.value);
                            dtok.dtt = DTT.NumDatesuff;
                            dtok.suffix = sep.type;
                            return true;

                        case TokenType.SEP_Date:
                            dtok.dtt = DTT.NumDatesep;
                            raw.addNumber(dtok.num);
                            return true;

                        case TokenType.SEP_MonthSuff:
                        case TokenType.SEP_DaySuff:
                            dtok.dtt = DTT.NumDatesuff;
                            dtok.suffix = sep.type;
                            return true;

                        case TokenType.SEP_HourSuff:
                        case TokenType.SEP_MinuteSuff:
                        case TokenType.SEP_SecondSuff:
                            dtok.dtt = DTT.NumTimesuff;
                            dtok.suffix = sep.type;
                            return true;

                        case TokenType.SEP_LocalTimeMark:
                            dtok.dtt = DTT.NumLocalTimeMark;
                            raw.addNumber(dtok.num);
                            return true;

                        case TokenType.SEP_DateOrOffset:
                            if ((dateParsingStates[dps][4] == DS.ERROR) && (dateParsingStates[dps][3] > DS.ERROR))
                            {
                                str.index = sep.indexBeforeSeparator;
                                str.current = sep.charBeforeSeparator;
                                dtok.dtt = DTT.NumSpace;
                            }
                            else
                                dtok.dtt = DTT.NumDatesep;

                            raw.addNumber(dtok.num);
                            return true;
                    }
                    return false;
                }

                raw.year = tokenTypeValue.value;
                //
                // If we have number which has 3 or more digits (like "001" or "0001"),
                // we assume this number is a year. Save the currnet raw.numCount in
                // raw.year.
                //
                sep = str.getSeparatorToken();
                switch (sep.type)
                {
                    case TokenType.SEP_Pm:
                    case TokenType.SEP_Am:
                        raw.timeMark = (sep.type == TokenType.SEP_Am) ? TM.AM : TM.PM;
                        dtok.dtt = DTT.YearSpace;
                        return true;
                    case TokenType.SEP_Date:
                        dtok.dtt = DTT.YearDateSep;
                        return true;

                    case TokenType.SEP_YearSuff:
                    case TokenType.SEP_MonthSuff:
                    case TokenType.SEP_DaySuff:
                        dtok.dtt = DTT.NumDatesuff;
                        dtok.suffix = sep.type;
                        return true;

                    case TokenType.SEP_End:
                        dtok.dtt = DTT.YearEnd;
                        return true;

                    case TokenType.SEP_Space:
                        dtok.dtt = DTT.YearSpace;
                        return true;

                    case TokenType.SEP_HourSuff:
                    case TokenType.SEP_MinuteSuff:
                    case TokenType.SEP_SecondSuff:
                        dtok.dtt = DTT.NumTimesuff;
                        dtok.suffix = sep.type;
                        return true;

                    case TokenType.SEP_DateOrOffset:
                        // The separator is either a date separator or the start of a time zone offset. If the token will complete the date then
                        // process just the number and roll back the index so that the outer loop can attempt to parse the time zone offset.
                        if ((dateParsingStates[dps][13] == DS.ERROR) && (dateParsingStates[dps][12] > DS.ERROR))
                        {
                            str.index = sep.indexBeforeSeparator;
                            str.current = sep.charBeforeSeparator;
                            dtok.dtt = DTT.YearSpace;
                        }
                        else
                        {
                            dtok.dtt = DTT.YearDateSep;
                        }
                        return true;
                }
                return false;

            // main switch
            case TokenType.Am:
            case TokenType.Pm:
                raw.timeMark = tokenTypeValue.value;
                return true;

            case TokenType.MonthToken:
            {
                //
                // This is a month name
                //
                SeparatorToken type8 = str.getSeparatorToken();
                if (type8.type > TokenType.SEP_Space)
                {
                    switch (type8.type)
                    {
                        case TokenType.SEP_Date:
                            dtok.dtt = DTT.MonthDatesep;
                            raw.month = tokenTypeValue.value;
                            return true;

                        case TokenType.SEP_DateOrOffset:
                            // The separator is either a date separator or the start of a time zone offset. If the token will complete the date then
                            // process just the number and roll back the index so that the outer loop can attempt to parse the time zone offset.
                            if ((dateParsingStates[dps][8] == DS.ERROR) && (dateParsingStates[dps][7] > DS.ERROR))
                            {
                                str.index = type8.indexBeforeSeparator;
                                str.current = type8.charBeforeSeparator;
                                dtok.dtt = DTT.MonthSpace;
                            }
                            else
                                dtok.dtt = DTT.MonthDatesep;

                            raw.month = tokenTypeValue.value;
                            return true;
                    }
                }
                else
                {
                    switch (type8.type)
                    {
                        case TokenType.SEP_End:
                            dtok.dtt = DTT.MonthEnd;
                            raw.month = tokenTypeValue.value;
                            return true;

                        case TokenType.SEP_Space:
                            dtok.dtt = DTT.MonthSpace;
                            raw.month = tokenTypeValue.value;
                            return true;
                    }
                }
                return false;
            }

            case TokenType.EndOfString:
                dtok.dtt = DTT.End;
                break;

            case TokenType.DayOfWeekToken:
                //
                // This is a day of week name.
                //
                if (raw.dayOfWeek != -1)
                {
                    return false;
                }
                raw.dayOfWeek = tokenTypeValue.value;
                dtok.dtt = DTT.DayOfWeek;
                break;

            case TokenType.TimeZoneToken:
                //
                // This is a timezone designator
                //
                // NOTENOTE : for now, we only support "GMT" and "Z" (for Zulu time).
                //
                dtok.dtt = DTT.TimeZone;
                result.flags |= ParseFlags.TimeZoneUsed;
                result.timeZoneOffset = new TimeSpan(0L);
                result.flags |= ParseFlags.TimeZoneUtc;
                break;

            case TokenType.EraToken:
                result.era = dtok.num;
                dtok.dtt = DTT.Era;
                break;

            case TokenType.UnknownToken:
                if (!Character.isLetter(str.current))
                {
                    if (((str.current == '-') || (str.current == '+')))
                    {
                        int index = str.index;
                        TimeSpan timeZone = DateTimeParse.parseTimeZone(str);
                        result.timeZoneOffset = timeZone;
                        if (timeZone != null)
                        {
                            result.flags |= ParseFlags.TimeZoneUsed;
                            return true;
                        }

                        str.index = index;
                    }
                    if (verifyValidPunctuation(str))
                        return true;

                    return false;
                }
                return false;

            case TokenType.HebrewNumber:
                if (tokenTypeValue.value < 100)
                {
                    dtok.num = tokenTypeValue.value;
                    raw.addNumber(dtok.num);
                    SeparatorToken type2 = str.getSeparatorToken();
                    switch ((type2.type))
                    {
                        case TokenType.SEP_Date:
                        case TokenType.SEP_Space:
                            dtok.dtt = DTT.NumDatesep;
                            return true;

                        case TokenType.SEP_DateOrOffset:
                            if ((dateParsingStates[(int) dps][4] == DS.ERROR) && (dateParsingStates[(int) dps][3] > DS.ERROR))
                            {
                                str.index = type2.indexBeforeSeparator;
                                str.current = type2.charBeforeSeparator;
                                dtok.dtt = DTT.NumSpace;
                            }
                            else
                            {
                                dtok.dtt = DTT.NumDatesep;
                            }
                            return true;

                        case TokenType.SEP_End:
                            dtok.dtt = DTT.NumEnd;
                            return true;
                    }
                    return false;
                }
                if (raw.year != -1)
                {
                    return false;
                }
                raw.year = tokenTypeValue.value;
                SeparatorToken type2 = str.getSeparatorToken();
                switch (type2.type)
                {
                    case TokenType.SEP_End:
                        dtok.dtt = DTT.YearEnd;
                        return true;

                    case TokenType.SEP_Space:
                        dtok.dtt = DTT.YearSpace;
                        return true;

                    case TokenType.SEP_DateOrOffset:
                        if (dateParsingStates[(int) dps][12] > DS.ERROR)
                        {
                            str.index = type2.indexBeforeSeparator;
                            str.current = type2.charBeforeSeparator;
                            dtok.dtt = DTT.YearSpace;
                            return true;
                        }
                        break;
                }
                return false;


        }

        return true;
    }

    public int matchSpecifiedWords(String target, boolean checkWordBoundary)
    {
        int num = this.value.length() - this.index;
        int matchLength = target.length();
        if ((matchLength > num) || (compare(this.value, this.index, matchLength, target, 0, matchLength, true) != 0))
        {
            int startIndex = 0;
            int index = this.index;
            int num4 = msString.indexOfAny(target, whiteSpaceChecks, startIndex);
            if (num4 == -1)
                return -1;

            do
            {
                int num5 = num4 - startIndex;
                if (index >= (this.value.length() - num5))
                    return -1;

                if (num5 == 0)
                    matchLength--;

                else
                {
                    if (!msChar.isWhiteSpace(this.value.charAt(index + num5)))
                        return -1;

                    if (compare(this.value, index, num5, target, startIndex, num5, true) != 0)
                        return -1;

                    index = (index + num5) + 1;
                }
                startIndex = num4 + 1;
                while ((index < this.value.length()) && msChar.isWhiteSpace(this.value.charAt(index)))
                {
                    index++;
                    matchLength++;
                }
            }
            while ((num4 = msString.indexOfAny(target, whiteSpaceChecks, startIndex)) >= 0);
            if (startIndex < target.length())
            {
                int num6 = target.length() - startIndex;
                if (index > (this.value.length() - num6))
                    return -1;

                if (compare(this.value, index, num6, target, startIndex, num6, true) != 0)
                    return -1;
            }
        }
        if (checkWordBoundary)
        {
            int num7 = this.index + matchLength;
            if ((num7 < this.value.length()) && Character.isLetter(this.value.charAt(num7)))
                return -1;
        }
        return matchLength;
    }

    //
    // Eat White Space ahead of the current position
    //
    // Return false if end of string is encountered.
    //
    public void skipWhiteSpaces()
    {
        // Look ahead to see if the next character
        // is a whitespace.
        while ((this.index + 1) < this.len)
        {
            char c = this.value.charAt(this.index + 1);
            if (!msChar.isWhiteSpace(c))
                return;
            this.index++;
        }
    }

    public boolean match(char ch)
    {
        if (++this.index < this.len)
        {
            if (this.value.charAt(this.index) == ch)
            {
                this.current = ch;
                return true;
            }
            this.index--;
        }
        return false;
    }

    //
    // Get the current character.
    //
    public int getChar()
    {
        return this.value.charAt(this.index);
    }

    public boolean getNextDigit()
    {
        if (++this.index >= this.len)
        {
            return false;
        }
        return isDigit(this.value.charAt(index));
    }

    //
    // Convert the current character to a digit, and return it.
    //
    public int getDigit()
    {
        return (this.value.charAt(this.index) - '0');
    }

    public DTSubString getSubString()
    {
        DTSubString str = new DTSubString();
        str.index = this.index;
        str.s = this.value;
        str.value = 0;
        str.length = 0;
        while ((this.index + str.length) < this.len)
        {
            int /*DTSubStringType*/ number;
            char ch = this.value.charAt(this.index + str.length);
            if ((ch >= '0') && (ch <= '9'))
                number = DTSubStringType.Number;
            else
                number = DTSubStringType.Other;

            if (str.length == 0)
                str.type = number;
            else if (str.type != number)
                break;

            str.length++;
            if (number != DTSubStringType.Number)
                break;

            if (str.length > 8)
            {
                str.type = DTSubStringType.Invalid;
                return str;
            }
            int num = ch - '0';
            str.value = (str.value * 10) + num;
        }
        if (str.length == 0)
        {
            str.type = DTSubStringType.End;
            return str;
        }
        return str;
    }

    public void consumeSubString(DTSubString sub)
    {
        this.index = sub.index + sub.length;
        if (this.index < this.len)
        {
            this.current = this.value.charAt(this.index);
        }
    }

    public static boolean isDigit(char ch)
    {
        return (ch >= '0' && ch <= '9');
    }

    private boolean isWhiteSpace(char current)
    {
        return Character.isWhitespace(current);
    }

    private static boolean handleTimeZone(DateTimeString str, DateTimeResult result)
    {
        if (str.index < (str.len - 1))
        {
            char c = str.value.charAt(str.index);
            int num = 0;
            while (msChar.isWhiteSpace(c) && ((str.index + num) < (str.len - 1)))
            {
                num++;
                c = str.value.charAt(str.index + num);
            }
            switch (c)
            {
                case '+':
                case '-':
                    str.index += num;
                    TimeSpan timeZone = DateTimeParse.parseTimeZone(str);
                    result.timeZoneOffset = timeZone;
                    if (timeZone == null)
                    {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    private static boolean verifyValidPunctuation(DateTimeString str)
    {
        // Compatability Behavior. Allow trailing nulls and surrounding hashes
        char ch = str.value.charAt(str.index);
        if (ch == '#')
        {
            boolean foundStart = false;
            boolean foundEnd = false;
            for (int i = 0; i < str.len; i++)
            {
                ch = str.value.charAt(i);
                if (ch == '#')
                {
                    if (foundStart)
                    {
                        if (foundEnd)
                        {
                            // Having more than two hashes is invalid
                            return false;
                        }
                        else
                            foundEnd = true;
                    }
                    else
                        foundStart = true;
                }
                else if (ch == '\0')
                {
                    // Allow nulls only at the end
                    if (!foundEnd)
                    {
                        return false;
                    }
                }
                else if ((!msChar.isWhiteSpace(ch)))
                {
                    // Anthyhing other than whitespace outside hashes is invalid
                    if (!foundStart || foundEnd)
                    {
                        return false;
                    }
                }
            }
            if (!foundEnd)
            {
                // The has was un-paired
                return false;
            }
            // Valid Hash usage: eat the hash and continue.
            str.getNext();
            return true;
        }
        else if (ch == '\0')
        {
            for (int i = str.index; i < str.len; i++)
            {
                if (str.value.charAt(i) != '\0')
                {
                    // Nulls are only valid if they are the only trailing character
                    return false;
                }
            }
            // Move to the end of the string
            str.index = str.len;
            return true;
        }
        return false;
    }

    private SeparatorToken getSeparatorToken()
    {
        SeparatorToken sep = new SeparatorToken();
        sep.indexBeforeSeparator = index;
        sep.charBeforeSeparator = current;

        if (!skipWhiteSpaceCurrent())
        {
            sep.type = TokenType.SEP_End;
            // Reach the end of the string.
            return sep;
        }
        if (!isDigit(current))
        {
            TokenTypeValue tokenTypeValue = new TokenTypeValue();
            boolean found = dtfi.tokenize(TokenType.SeparatorTokenMask, tokenTypeValue, this);
            sep.type = tokenTypeValue.type;
            if (!found)
                sep.type = TokenType.SEP_Space;
        }
        else
            sep.type = TokenType.SEP_Space;

        return sep;
    }

    //
    // Skip white spaces from the current position
    //
    // Return false if end of string is encountered.
    //
    private boolean skipWhiteSpaceCurrent()
    {
        if (index >= len)
            return false;

        if (!isWhiteSpace(current))
            return true;

        while (++index < len)
        {
            current = value.charAt(index);
            if (!isWhiteSpace(current))
                return true;
            // Nothing here.
        }
        return false;
    }

    public int compare(String string1, int offset1, int length1, String string2, int offset2, int length2, boolean ignoreCase)
    {
        // int len1 = offset1 + string1.length();
        int minLen = Math.min(string1.length(), string2.length());

        int len1 = offset1 + minLen;
        len1 = len1 > string1.length() ? string1.length() : len1;

        int len2 = offset2 + minLen;
        len2 = len2 > string2.length() ? string2.length() : len2;

        String sub1 = string1.substring(offset1, len1);
        String sub2 = string2.substring(offset2, len2);

        if (ignoreCase)
            return sub1.compareToIgnoreCase(sub2);

        return sub1.compareTo(sub2);
    }

    //
    // Get the number of repeat character after the current character.
    // For a string "hh:mm:ss" at Index of 3. GetRepeatCount() = 2, and Index
    // will point to the second ':'.
    //
    public int getRepeatCount()
    {
        char repeatChar = value.charAt(index);
        int pos = index + 1;
        while ((pos < len) && (value.charAt(pos) == repeatChar))
        {
            pos++;
        }
        int repeatCount = (pos - index);
        // Update the Index to the end of the repeated characters.
        // So the following GetNext() opeartion will get
        // the next character to be parsed.
        index = pos - 1;
        return (repeatCount);
    }

    public void trimTail()
    {
        int i = len - 1;
        while (i >= 0 && msChar.isWhiteSpace(value.charAt(i)))
        {
            i--;
        }
        value = value.substring(0, i + 1);
        len = value.length();
    }

    // Trim the trailing spaces within a quoted string.
    // Call this after TrimTail() is done.
    public void removeTrailingInQuoteSpaces()
    {
        int i = len - 1;
        if (i <= 1)
        {
            return;
        }
        char ch = value.charAt(i);
        // Check if the last character is a quote.
        if (ch == '\'' || ch == '\"')
        {
            if (msChar.isWhiteSpace(value.charAt(i - 1)))
            {
                i--;
                while (i >= 1 && msChar.isWhiteSpace(value.charAt(i - 1)))
                {
                    i--;
                }
                value = remove(value, i, value.length() - 1 - i);
                len = value.length();
            }
        }
    }

    public String remove(String str, int start, int end)
    {
        return msString.remove(str, start, end);
    }

    // Trim the leading spaces within a quoted string.
    // Call this after the leading spaces before quoted string are trimmed.
    public void removeLeadingInQuoteSpaces()
    {
        if (len <= 2)
        {
            return;
        }

        int i = 0;
        char ch = value.charAt(i);
        // Check if the last character is a quote.
        if (ch == '\'' || ch == '\"')
        {
            while ((i + 1) < len && msChar.isWhiteSpace(value.charAt(i + 1)))
            {
                i++;
            }
            if (i != 0)
            {
                value = remove(value, 1, i);
                len = value.length();
            }
        }
    }

    public boolean matchSpecifiedWord(String target)
    {
        return matchSpecifiedWord(target, target.length() + index);
    }

    private boolean matchSpecifiedWord(String target, int endIndex)
    {
        int count = endIndex - index;

        if (count != target.length())
        {
            return false;
        }

        if (index + count > len)
        {
            return false;
        }

        return (compare(value, index, count, target, 0, count, true) == 0);
    }

    //################ HELPERS ##################

    public static class TokenTypeValue
    {
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        private int type;
        private int value;
    }

    static class SeparatorToken
    {
        private int type;
        private int indexBeforeSeparator;
        private char charBeforeSeparator;
    }

    public static class TokenType
    {
        // The valid token should start from 1.

        // Regular tokens. The range is from 0x00 ~ 0xff.
        public static final int
                NumberToken = 1,    // The number.  E.g. "12"
                YearNumberToken = 2,    // The number which is considered as year number, which has 3 or more digits.  E.g. "2003"
                Am = 3,    // AM timemark. E.g. "AM"
                Pm = 4,    // PM timemark. E.g. "PM"
                MonthToken = 5,    // A word (or words) that represents a month name.  E.g. "[....]"
                EndOfString = 6,    // End of string
                DayOfWeekToken = 7,    // A word (or words) that represents a day of week name.  E.g. "Monday" or "Mon"
                TimeZoneToken = 8,    // A word that represents a timezone name. E.g. "GMT"
                EraToken = 9,    // A word that represents a era name. E.g. "A.D."
                DateWordToken = 10,   // A word that can appear in a DateTime string, but serves no parsing semantics.  E.g. "de" in Spanish culture.
                UnknownToken = 11,   // An unknown word, which signals an error in parsing.
                HebrewNumber = 12,   // A number that is composed of Hebrew text.  Hebrew calendar uses Hebrew digits for year values, month values, and day values.
                JapaneseEraToken = 13,   // Era name for JapaneseCalendar
                TEraToken = 14,   // Era name for ----Calenadr
                IgnorableSymbol = 15,   // A separator like "," that is equivalent to whitespace


                // Separator tokens.
                SEP_Unk = 0x100,         // Unknown separator.
                SEP_End = 0x200,    // The end of the parsing string.
                SEP_Space = 0x300,    // Whitespace (including comma).
                SEP_Am = 0x400,    // AM timemark. E.g. "AM"
                SEP_Pm = 0x500,    // PM timemark. E.g. "PM"
                SEP_Date = 0x600,    // date separator. E.g. "/"
                SEP_Time = 0x700,    // time separator. E.g. ":"
                SEP_YearSuff = 0x800,    // Chinese/Japanese/Korean year suffix.
                SEP_MonthSuff = 0x900,    // Chinese/Japanese/Korean month suffix.
                SEP_DaySuff = 0xa00,    // Chinese/Japanese/Korean day suffix.
                SEP_HourSuff = 0xb00,   // Chinese/Japanese/Korean hour suffix.
                SEP_MinuteSuff = 0xc00,   // Chinese/Japanese/Korean minute suffix.
                SEP_SecondSuff = 0xd00,   // Chinese/Japanese/Korean second suffix.
                SEP_LocalTimeMark = 0xe00,   // 'T', used in ISO 8601 format.
                SEP_DateOrOffset = 0xf00,   // '-' which could be a date separator or start of a time zone offset

                RegularTokenMask = 0x00ff,
                SeparatorTokenMask = 0xff00;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Date Token Types
    //
    // Following is the set of tokens that can be generated from a date
    // string. Notice that the legal set of trailing separators have been
    // folded in with the date number, and month name tokens. This set
    // of tokens is chosen to reduce the number of date parse states.
    //
    ////////////////////////////////////////////////////////////////////////////
    static final class DTT
    {
        public static final int
                End = 0,    // '\0'
                NumEnd = 1,    // Num[ ]*[\0]
                NumAmpm = 2,    // Num[ ]+AmPm
                NumSpace = 3,    // Num[ ]+^[Dsep|Tsep|'0\']
                NumDatesep = 4,    // Num[ ]*Dsep
                NumTimesep = 5,    // Num[ ]*Tsep
                MonthEnd = 6,    // Month[ ]*'\0'
                MonthSpace = 7,    // Month[ ]+^[Dsep|Tsep|'\0']
                MonthDatesep = 8,    // Month[ ]*Dsep
                NumDatesuff = 9,    // Month[ ]*DSuff
                NumTimesuff = 10,   // Month[ ]*TSuff
                DayOfWeek = 11,   // Day of week name
                YearSpace = 12,   // Year+^[Dsep|Tsep|'0\']
                YearDateSep = 13,  // Year+Dsep
                YearEnd = 14,  // Year+['\0']
                TimeZone = 15,  // timezone name
                Era = 16,  // era name
                NumUTCTimeMark = 17,      // Num + 'Z'
                // When you add a new token which will be in the
                // state table, add it after NumLocalTimeMark.
                Unk = 18,   // unknown
                NumLocalTimeMark = 19,    // Num + 'T'
                Max = 20;   // marker
    }

    static final class TM
    {
        public static final int
                NotSet = -1,
                AM = 0,
                PM = 1;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // DateTime parsing state enumeration (DS.*)
    //
    ////////////////////////////////////////////////////////////////////////////

    static final class DS
    {
        public static final int BEGIN = 0,
        N = 1,        // have one number
        NN = 2,        // have two numbers

        // The following are known to be part of a date

        D_Nd = 3,        // date string: have number followed by date separator
        D_NN = 4,        // date string: have two numbers
        D_NNd = 5,        // date string: have two numbers followed by date separator

        D_M = 6,        // date string: have a month
        D_MN = 7,        // date string: have a month and a number
        D_NM = 8,        // date string: have a number and a month
        D_MNd = 9,        // date string: have a month and number followed by date separator
        D_NDS = 10,       // date string: have one number followed a date suffix.

        D_Y = 11,        // date string: have a year.
        D_YN = 12,        // date string: have a year and a number
        D_YNd = 13,        // date string: have a year and a number and a date separator
        D_YM = 14,        // date string: have a year and a month
        D_YMd = 15,        // date string: have a year and a month and a date separator
        D_S = 16,       // have numbers followed by a date suffix.
        T_S = 17,       // have numbers followed by a time suffix.

        // The following are known to be part of a time

        T_Nt = 18,          // have num followed by time separator
        T_NNt = 19,       // have two numbers followed by time separator

        ERROR = 20,

        // The following are terminal states. These all have an action
        // associated with them; and transition back to BEGIN.

        DX_NN = 21,       // day from two numbers
        DX_NNN = 22,       // day from three numbers
        DX_MN = 23,       // day from month and one number
        DX_NM = 24,       // day from month and one number
        DX_MNN = 25,       // day from month and two numbers
        DX_DS = 26,       // a set of date suffixed numbers.
        DX_DSN = 27,       // day from date suffixes and one number.
        DX_NDS = 28,       // day from one number and date suffixes .
        DX_NNDS = 29,       // day from one number and date suffixes .

        DX_YNN = 30,       // date string: have a year and two number
        DX_YMN = 31,       // date string: have a year, a month, and a number.
        DX_YN = 32,       // date string: have a year and one number
        DX_YM = 33,       // date string: have a year, a month.
        TX_N = 34,       // time from one number (must have ampm)
        TX_NN = 35,       // time from two numbers
        TX_NNN = 36,       // time from three numbers
        TX_TS = 37,       // a set of time suffixed numbers.
        DX_NNY = 38;
    }

    //
    // The buffer to store the parsing token.
    //
    static class DateTimeToken
    {
        public int getDtt() {
            return dtt;
        }

        public void setDtt(int dtt) {
            this.dtt = dtt;
        }

        public int getSuffix() {
            return suffix;
        }

        public void setSuffix(int suffix) {
            this.suffix = suffix;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        private int dtt;    // Store the token
        private int suffix; // Store the CJK Year/Month/Day suffix (if any)
        private int num;    // Store the number that we are parsing (if any)
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // NOTE: The following state machine table is dependent on the order of the
    // DS and DTT enumerations.
    //
    // For each non terminal state, the following table defines the next state
    // for each given date token type.
    //
    ////////////////////////////////////////////////////////////////////////////
    //          End       NumEnd      NumAmPm     NumSpace    NumDaySep   NumTimesep  MonthEnd    MonthSpace  MonthDSep   NumDateSuff NumTimeSuff     DayOfWeek     YearSpace   YearDateSep YearEnd     TimeZone   Era         UTCTimeMark
    static int[][] dateParsingStates =
            {
                    new int[]{DS.BEGIN, DS.ERROR, DS.TX_N, DS.N, DS.D_Nd, DS.T_Nt, DS.ERROR, DS.D_M, DS.D_M, DS.D_S, DS.T_S, DS.BEGIN, DS.D_Y, DS.D_Y, DS.ERROR, DS.BEGIN, DS.BEGIN, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_NN, DS.ERROR, DS.NN, DS.D_NNd, DS.ERROR, DS.DX_NM, DS.D_NM, DS.D_MNd, DS.D_NDS, DS.ERROR, DS.N, DS.D_YN, DS.D_YNd, DS.DX_YN, DS.N, DS.N, DS.ERROR},
                    new int[]{DS.DX_NN, DS.DX_NNN, DS.TX_N, DS.DX_NNN, DS.ERROR, DS.T_Nt, DS.DX_MNN, DS.DX_MNN, DS.ERROR, DS.ERROR, DS.T_S, DS.NN, DS.DX_NNY, DS.ERROR, DS.DX_NNY, DS.NN, DS.NN, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_NN, DS.ERROR, DS.D_NN, DS.D_NNd, DS.ERROR, DS.DX_NM, DS.D_MN, DS.D_MNd, DS.ERROR, DS.ERROR, DS.D_Nd, DS.D_YN, DS.D_YNd, DS.DX_YN, DS.ERROR, DS.D_Nd, DS.ERROR},
                    new int[]{DS.DX_NN, DS.DX_NNN, DS.TX_N, DS.DX_NNN, DS.ERROR, DS.T_Nt, DS.DX_MNN, DS.DX_MNN, DS.ERROR, DS.DX_DS, DS.T_S, DS.D_NN, DS.DX_NNY, DS.ERROR, DS.DX_NNY, DS.ERROR, DS.D_NN, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_NNN, DS.DX_NNN, DS.DX_NNN, DS.ERROR, DS.ERROR, DS.DX_MNN, DS.DX_MNN, DS.ERROR, DS.DX_DS, DS.ERROR, DS.D_NNd, DS.DX_NNY, DS.ERROR, DS.DX_NNY, DS.ERROR, DS.D_NNd, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_MN, DS.ERROR, DS.D_MN, DS.D_MNd, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_M, DS.D_YM, DS.D_YMd, DS.DX_YM, DS.ERROR, DS.D_M, DS.ERROR},
                    new int[]{DS.DX_MN, DS.DX_MNN, DS.DX_MNN, DS.DX_MNN, DS.ERROR, DS.T_Nt, DS.ERROR, DS.ERROR, DS.ERROR, DS.DX_DS, DS.T_S, DS.D_MN, DS.DX_YMN, DS.ERROR, DS.DX_YMN, DS.ERROR, DS.D_MN, DS.ERROR},
                    new int[]{DS.DX_NM, DS.DX_MNN, DS.DX_MNN, DS.DX_MNN, DS.ERROR, DS.T_Nt, DS.ERROR, DS.ERROR, DS.ERROR, DS.DX_DS, DS.T_S, DS.D_NM, DS.DX_YMN, DS.ERROR, DS.DX_YMN, DS.ERROR, DS.D_NM, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_MNN, DS.ERROR, DS.DX_MNN, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_MNd, DS.DX_YMN, DS.ERROR, DS.DX_YMN, DS.ERROR, DS.D_MNd, DS.ERROR},
                    new int[]{DS.DX_NDS, DS.DX_NNDS, DS.DX_NNDS, DS.DX_NNDS, DS.ERROR, DS.T_Nt, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_NDS, DS.T_S, DS.D_NDS, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_NDS, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_YN, DS.ERROR, DS.D_YN, DS.D_YNd, DS.ERROR, DS.DX_YM, DS.D_YM, DS.D_YMd, DS.D_YM, DS.ERROR, DS.D_Y, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_Y, DS.ERROR},
                    new int[]{DS.DX_YN, DS.DX_YNN, DS.DX_YNN, DS.DX_YNN, DS.ERROR, DS.ERROR, DS.DX_YMN, DS.DX_YMN, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YN, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YN, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_YNN, DS.DX_YNN, DS.DX_YNN, DS.ERROR, DS.ERROR, DS.DX_YMN, DS.DX_YMN, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YN, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YN, DS.ERROR},
                    new int[]{DS.DX_YM, DS.DX_YMN, DS.DX_YMN, DS.DX_YMN, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YM, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YM, DS.ERROR},
                    new int[]{DS.ERROR, DS.DX_YMN, DS.DX_YMN, DS.DX_YMN, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YM, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_YM, DS.ERROR},
                    new int[]{DS.DX_DS, DS.DX_DSN, DS.TX_N, DS.T_Nt, DS.ERROR, DS.T_Nt, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_S, DS.T_S, DS.D_S, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_S, DS.ERROR},
                    new int[]{DS.TX_TS, DS.TX_TS, DS.TX_TS, DS.T_Nt, DS.D_Nd, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.D_S, DS.T_S, DS.T_S, DS.ERROR, DS.ERROR, DS.ERROR, DS.T_S, DS.T_S, DS.ERROR},
                    new int[]{DS.ERROR, DS.TX_NN, DS.TX_NN, DS.TX_NN, DS.ERROR, DS.T_NNt, DS.DX_NM, DS.D_NM, DS.ERROR, DS.ERROR, DS.T_S, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.T_Nt, DS.T_Nt, DS.TX_NN},
                    new int[]{DS.ERROR, DS.TX_NNN, DS.TX_NNN, DS.TX_NNN, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.ERROR, DS.T_S, DS.T_NNt, DS.ERROR, DS.ERROR, DS.ERROR, DS.T_NNt, DS.T_NNt, DS.TX_NNN},
            };

    //
    // This will store the result of the parsing.  And it will be eventually
    // used to construct a DateTime instance.
    //
    static class DateTimeResult
    {
        public int getYear() {
            return Year;
        }

        public int setYear(int year) {
            Year = year;
            return year;
        }

        public int getMonth() {
            return Month;
        }

        public void setMonth(int month) {
            Month = month;
        }

        public int getDay() {
            return Day;
        }

        public void setDay(int day) {
            Day = day;
        }

        public int getHour() {
            return Hour;
        }

        public void setHour(int hour) {
            Hour = hour;
        }

        public int getMinute() {
            return Minute;
        }

        public void setMinute(int minute) {
            Minute = minute;
        }

        public int getSecond() {
            return Second;
        }

        public void setSecond(int second) {
            Second = second;
        }

        public double getFraction() {
            return fraction;
        }

        public void setFraction(double fraction) {
            this.fraction = fraction;
        }

        public int getEra() {
            return era;
        }

        public void setEra(int era) {
            this.era = era;
        }

        public TimeSpan getTimeZoneOffset() {
            return timeZoneOffset;
        }

        public void setTimeZoneOffset(TimeSpan timeZoneOffset) {
            this.timeZoneOffset = timeZoneOffset;
        }

        public Calendar getCalendar() {
            return calendar;
        }

        public void setCalendar(Calendar calendar) {
            this.calendar = calendar;
        }

        public DateTime getParsedDate() {
            return parsedDate;
        }

        public void setParsedDate(DateTime parsedDate) {
            this.parsedDate = parsedDate;
        }

        public int getFlags() {
            return flags;
        }

        public void setFlags(int flags) {
            this.flags = flags;
        }

        private int Year;
        private int Month;
        private int Day;
        //
        // Set time defualt to 00:00:00.
        //
        private int Hour;
        private int Minute;
        private int Second;
        private double fraction;
        private int era;
        // public ParseFlags flags;
        private TimeSpan timeZoneOffset;
        private Calendar calendar;
        private DateTime parsedDate;
        private int flags;

        void init()
        {
            Year = -1;
            Month = -1;
            Day = -1;
            fraction = -1;
            era = -1;
        }

        void setDate(int year, int month, int day)
        {
            this.Year = year;
            this.Month = month;
            this.Day = day;
        }

        public String toString()
        {
            return "hour=" + Hour + " min=" + Minute + " sec=" + Second + " year=" + Year + " month=" + Month + " day=" +
                   Day;
        }
    }

    //
    // The buffer to store temporary parsing information.
    //
    static class DateTimeRawInfo
    {
        private int[] num;
        private int numCount;
        private int month;
        private int year;
        private int dayOfWeek;
        private int era;
        private int timeMark;
        private double fraction;

        public int getNumCount() {
            return numCount;
        }

        public void setNumCount(int numCount) {
            this.numCount = numCount;
        }

        public int getMonth() {
            return month;
        }

        public int setMonth(int month) {
            this.month = month;
            return month;
        }

        public int getYear() {
            return year;
        }

        public int setYear(int year) {
            this.year = year;
            return year;
        }

        public int getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(int dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public int getEra() {
            return era;
        }

        public void setEra(int era) {
            this.era = era;
        }

        public int getTimeMark() {
            return timeMark;
        }

        public void setTimeMark(int timeMark) {
            this.timeMark = timeMark;
        }

        public double getFraction() {
            return fraction;
        }

        public void setFraction(double fraction) {
            this.fraction = fraction;
        }

        public boolean isTimeZone() {
            return timeZone;
        }

        public void setTimeZone(boolean timeZone) {
            this.timeZone = timeZone;
        }

        private boolean timeZone;

        void init(int[] numberBuffer)
        {
            this.month = -1;
            this.year = -1;
            this.dayOfWeek = -1;
            this.era = -1;
            this.timeMark = TM.NotSet;
            this.fraction = -1.0;
            this.num = numberBuffer;
        }

        void addNumber(int value)
        {
            this.num[this.numCount++] = value;
        }

        int getNumber(int index)
        {
            return this.num[index];
        }
    }

    static int ORDER_YMD = 0;     // The order of date is Year/Month/Day.
    static int ORDER_MDY = 1;     // The order of date is Month/Day/Year.
    static int ORDER_DMY = 2;     // The order of date is Day/Month/Year.
    static int ORDER_YDM = 3;     // The order of date is Year/Day/Month
    static int ORDER_YM = 4;     // Year/Month order.
    static int ORDER_MY = 5;     // Month/Year order.
    static int ORDER_MD = 6;     // Month/Day order.
    static int ORDER_DM = 7;     // Day/Month order.
    static int ORDER_ERROR = -1;

    static class DTSubString
    {
        private static String s;
        private static int index;
        private static int length;
        private static int /*DTSubStringType*/ type;
        private static int value;

        public static String getS() {
            return s;
        }

        public static void setS(String s) {
            DTSubString.s = s;
        }

        public static int getIndex() {
            return index;
        }

        public static void setIndex(int index) {
            DTSubString.index = index;
        }

        public static int getLength() {
            return length;
        }

        public static void setLength(int length) {
            DTSubString.length = length;
        }

        public static int getType() {
            return type;
        }

        public static void setType(int type) {
            DTSubString.type = type;
        }

        public static int getValue() {
            return value;
        }

        public static void setValue(int value) {
            DTSubString.value = value;
        }

        public static char getChar(int relativeIndex)
        {
            return s.charAt(index + relativeIndex);
        }
    }

    public static class DTSubStringType
    {
        public final static int Unknown = 0,
        Invalid = 1,
        Number = 2,
        End = 3,
        Other = 4;
    }

    //[Flags]
    public static class ParseFlags
    {
        public static final int CaptureOffset = 0x800,
                HaveDate = 0x80,
                HaveDay = 4,
                HaveHour = 8,
                HaveMinute = 0x10,
                HaveMonth = 2,
                HaveSecond = 0x20,
                HaveTime = 0x40,
                HaveYear = 1,
                ParsedMonthName = 0x400,
                Rfc1123Pattern = 0x2000,
                TimeZoneUsed = 0x100,
                TimeZoneUtc = 0x200,
                UtcSortPattern = 0x4000,
                YearDefault = 0x1000;
    }

    // This is the helper data structure used in ParseExact().
    public static class ParsingInfo
    {

        private static Calendar calendar;
        private static int dayOfWeek;
        private static int timeMark;

        private static boolean fUseHour12;
        private static boolean fUseTwoDigitYear;
        private static boolean fAllowInnerWhite;
        private static boolean fAllowTrailingWhite;
        private static boolean fCustomNumberParser;
        // public static boolean  DateTimeParse.MatchNumberDelegate parseNumberDelegate;

        public static Calendar getCalendar() {
            return calendar;
        }

        public static void setCalendar(Calendar calendar) {
            ParsingInfo.calendar = calendar;
        }

        public static int getDayOfWeek() {
            return dayOfWeek;
        }

        public static void setDayOfWeek(int dayOfWeek) {
            ParsingInfo.dayOfWeek = dayOfWeek;
        }

        public static int getTimeMark() {
            return timeMark;
        }

        public static void setTimeMark(int timeMark) {
            ParsingInfo.timeMark = timeMark;
        }

        public static boolean isfUseHour12() {
            return fUseHour12;
        }

        public static void setfUseHour12(boolean fUseHour12) {
            ParsingInfo.fUseHour12 = fUseHour12;
        }

        public static boolean isfUseTwoDigitYear() {
            return fUseTwoDigitYear;
        }

        public static void setfUseTwoDigitYear(boolean fUseTwoDigitYear) {
            ParsingInfo.fUseTwoDigitYear = fUseTwoDigitYear;
        }

        public static boolean isfAllowInnerWhite() {
            return fAllowInnerWhite;
        }

        public static void setfAllowInnerWhite(boolean fAllowInnerWhite) {
            ParsingInfo.fAllowInnerWhite = fAllowInnerWhite;
        }

        public static boolean isfAllowTrailingWhite() {
            return fAllowTrailingWhite;
        }

        public static void setfAllowTrailingWhite(boolean fAllowTrailingWhite) {
            ParsingInfo.fAllowTrailingWhite = fAllowTrailingWhite;
        }

        public static boolean isfCustomNumberParser() {
            return fCustomNumberParser;
        }

        public static void setfCustomNumberParser(boolean fCustomNumberParser) {
            ParsingInfo.fCustomNumberParser = fCustomNumberParser;
        }

        public static void init()
        {
            dayOfWeek = -1;
            timeMark = TM.NotSet;
            fUseTwoDigitYear = false;
        }

        // only m_hebrewNumberParser used as delegate in .Net
        public int parseNumberDelegate(DateTimeString str, int tokenLen)
        {
            return DateTimeParse.parseDigits(str, tokenLen);
        }
    }
}
