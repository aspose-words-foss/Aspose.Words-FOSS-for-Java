// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/globalization/hebrewnumber.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System.Globalization;

public class HebrewNumber
{

    private static HebrewValue[] HebrewValues = new HebrewValue[]{
            new HebrewValue(HebrewToken.Digit1, 1),
            new HebrewValue(HebrewToken.Digit1, 2),
            new HebrewValue(HebrewToken.Digit1, 3),
            new HebrewValue(HebrewToken.Digit1, 4),
            new HebrewValue(HebrewToken.Digit1, 5),
            new HebrewValue(HebrewToken.Digit6_7, 6),
            new HebrewValue(HebrewToken.Digit6_7, 7),
            new HebrewValue(HebrewToken.Digit1, 8),
            new HebrewValue(HebrewToken.Digit9, 9),
            new HebrewValue(HebrewToken.Digit10, 10),
            new HebrewValue(HebrewToken.Invalid, -1),
            new HebrewValue(HebrewToken.Digit10, 20),
            new HebrewValue(HebrewToken.Digit10, 30),
            new HebrewValue(HebrewToken.Invalid, -1),
            new HebrewValue(HebrewToken.Digit10, 40),
            new HebrewValue(HebrewToken.Invalid, -1),
            new HebrewValue(HebrewToken.Digit10, 50),
            new HebrewValue(HebrewToken.Digit10, 60),
            new HebrewValue(HebrewToken.Digit10, 70),
            new HebrewValue(HebrewToken.Invalid, -1),
            new HebrewValue(HebrewToken.Digit10, 80),
            new HebrewValue(HebrewToken.Invalid, -1),
            new HebrewValue(HebrewToken.Digit10, 90),
            new HebrewValue(HebrewToken.Digit100, 100),
            new HebrewValue(HebrewToken.Digit200_300, 200),
            new HebrewValue(HebrewToken.Digit200_300, 300),
            new HebrewValue(HebrewToken.Digit400, 400)
    };

    private static int[][] m_numberPasingState = new int[][]{
            new int[]{HS.S400, HS.X00, HS.X00, HS.X0, HS.X, HS.X, HS.X, HS.S9, HS._err, HS._err},
            new int[]{HS.S400_400, HS.S400_X00, HS.S400_X00, HS.S400_X0, HS._err, HS._err, HS._err, HS.X00_S9, HS.END, HS.S400_DQ},
            new int[]{HS._err, HS._err, HS.S400_400_100, HS.S400_X0, HS._err, HS._err, HS._err, HS.X00_S9, HS._err, HS.S400_400_DQ},
            new int[]{HS._err, HS._err, HS._err, HS.S400_X00_X0, HS._err, HS._err, HS._err, HS.X00_S9, HS._err, HS.X00_DQ},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS.X0_DQ},
            new int[]{HS._err, HS._err, HS._err, HS.END, HS.END, HS.END, HS.END, HS.END, HS._err, HS._err},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS.X0_DQ},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS.END, HS.END, HS.END, HS.END, HS._err, HS._err},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS.END, HS._err},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS.END, HS.X0_DQ},
            new int[]{HS._err, HS._err, HS._err, HS.S400_X0, HS._err, HS._err, HS._err, HS.X00_S9, HS.END, HS.X00_DQ},
            new int[]{HS.END, HS.END, HS.END, HS.END, HS.END, HS.END, HS.END, HS.END, HS._err, HS._err},
            new int[]{HS._err, HS._err, HS.END, HS.END, HS.END, HS.END, HS.END, HS.END, HS._err, HS._err},
            new int[]{HS._err, HS._err, HS._err, HS.S400_X00_X0, HS._err, HS._err, HS._err, HS.X00_S9, HS._err, HS.X00_DQ},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS.END, HS.S9_DQ},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS._err, HS.S9_DQ},
            new int[]{HS._err, HS._err, HS._err, HS._err, HS._err, HS.END, HS.END, HS._err, HS._err, HS._err}
    };
    private static char maxHebrewNumberCh = ((char) ((0x5d0 + HebrewValues.length) - 1));
    private final int minHebrewNumberCh = 0x5d0;

    private HebrewNumber()
    {
    }

    static boolean IsDigit(char ch)
    {
        if ((ch >= 'א') && (ch <= maxHebrewNumberCh))
        {
            return (HebrewValues[ch - 'א'].value >= 0);
        }
        if (ch != '\'')
        {
            return (ch == '"');
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////
    //
    //  Actions:
    //      Parse the Hebrew number by passing one character at a time.
    //      The state between characters are maintained at HebrewNumberPasingContext.
    //  Returns:
    //      Return a enum of HebrewNumberParsingState.
    //          NotHebrewDigit: The specified ch is not a valid Hebrew digit.
    //          InvalidHebrewNumber: After parsing the specified ch, it will lead into
    //              an invalid Hebrew number text.
    //          FoundEndOfHebrewNumber: A terminal state is reached.  This means that
    //              we find a valid Hebrew number text after the specified ch is parsed.
    //          ContinueParsing: The specified ch is a valid Hebrew digit, and
    //              it will lead into a valid state in the state machine, we should
    //              continue to parse incoming characters.
    //
    ////////////////////////////////////////////////////////////////////////
    static int ParseByChar(char ch, HebrewNumberParsingContext context)
    {
        int singleQuote;
        if (ch == '\'')
        {
            singleQuote = HebrewToken.SingleQuote;
        }
        else if (ch == '"')
        {
            singleQuote = HebrewToken.DoubleQuote;
        }
        else
        {
            int index = ch - 'א';
            if ((index < 0) || (index >= HebrewValues.length))
            {
                return HebrewNumberParsingState.NotHebrewDigit;
            }
            singleQuote = HebrewValues[index].token;
            if (singleQuote == HebrewToken.Invalid)
            {
                return HebrewNumberParsingState.NotHebrewDigit;
            }
            context.result += HebrewValues[index].value;
        }
        context.state = m_numberPasingState[(int) context.state][(int) singleQuote];
        if (context.state == HS._err)
        {
            return HebrewNumberParsingState.InvalidHebrewNumber;
        }
        if (context.state == HS.END)
        {
            return HebrewNumberParsingState.FoundEndOfHebrewNumber;
        }
        return HebrewNumberParsingState.ContinueParsing;
    }


    public static String toString(int Number)
    {
        char ch = '\0';
        StringBuilder builder = new StringBuilder();
        if (Number > 0x1388)
        {
            Number -= 0x1388;
        }
        int num = Number / 100;
        if (num > 0)
        {
            Number -= num * 100;
            for (int i = 0; i < (num / 4); i++)
            {
                builder.append('ת');
            }
            int num4 = num % 4;
            if (num4 > 0)
            {
                builder.append((char) (0x5e6 + num4));
            }
        }
        int num2 = Number / 10;
        Number = Number % 10;
        switch (num2)
        {
            case 0:
                ch = '\0';
                break;

            case 1:
                ch = 'י';
                break;

            case 2:
                ch = 'כ';
                break;

            case 3:
                ch = 'ל';
                break;

            case 4:
                ch = 'מ';
                break;

            case 5:
                ch = 'נ';
                break;

            case 6:
                ch = 'ס';
                break;

            case 7:
                ch = 'ע';
                break;

            case 8:
                ch = 'פ';
                break;

            case 9:
                ch = 'צ';
                break;
        }
        char ch2 = (Number > 0) ? ((char) ((0x5d0 + Number) - 1)) : '\0';
        if ((ch2 == 'ה') && (ch == 'י'))
        {
            ch2 = 'ו';
            ch = 'ט';
        }
        if ((ch2 == 'ו') && (ch == 'י'))
        {
            ch2 = 'ז';
            ch = 'ט';
        }
        if (ch != '\0')
        {
            builder.append(ch);
        }
        if (ch2 != '\0')
        {
            builder.append(ch2);
        }
        if (builder.length() > 1)
        {
            builder.insert(builder.length() - 1, '"');
        }
        else
        {
            builder.append('\'');
        }
        return builder.toString();
    }

    static class HebrewToken
    {
        static final int Digit1 = 4,
        Digit10 = 3,
        Digit100 = 2,
        Digit200_300 = 1,
        Digit400 = 0,
        Digit6_7 = 5,
        Digit7 = 6,
        Digit9 = 7,
        DoubleQuote = 9,
        Invalid = -1,
        SingleQuote = 8;

        private int n;

        HebrewToken(int n)
        {
            this.n = n;
        }

        int toInt()
        {
            return n;
        }
    }

    static class HebrewValue
    {
        int token;
        int value;

        HebrewValue(int token, int value)
        {
            this.token = token;
            this.value = value;
        }
    }

    static class HS
    {
        static final int _err = -1,
        END = 100,
        S400 = 1,
        S400_400 = 2,
        S400_400_100 = 13,
        S400_400_DQ = 12,
        S400_DQ = 11,
        S400_X0 = 4,
        S400_X00 = 3,
        S400_X00_X0 = 6,
        S9 = 14,
        S9_DQ = 0x10,
        Start = 0,
        X = 8,
        X0 = 9,
        X0_DQ = 7,
        X00 = 10,
        X00_DQ = 5,
        X00_S9 = 15;



    }
}

