// Ported to Java by Aspose from Mono,
// https://github.com/mono/mono : mcs/class/corlib/System/NumberFormatter.cs
//
// Author:
//   Kazuki Oikawa (kazuki@panicode.com)
//   Eyal Alaluf (eyala@mainsoft.com)
//
// Copyright (C) 2004 Novell, Inc (http://www.novell.com)
// Copyright (C) 2008 Mainsoft Co. (http://www.mainsoft.com)
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

package com.aspose.ms.System;

import java.text.DecimalFormatSymbols;

/**
 * This class is a Java-version of System.CustomInfo class taken from Mono project ->
 * https://github.com/mono/mono/blob/master/mcs/class/corlib/System/NumberFormatter.cs
 * com.aspose.CustomInfo is used to format double values according to MS Word formatting.
 * com.aspose.ms.System.CustomInfo(this class) is used to format double values according to .NET formatting.
 * MS Word formatting is slightly different from .NET formatting (WORDSNET-8614).
 */
class CustomInfo
{
    public boolean isUseGroup() {
        return UseGroup;
    }

    public void setUseGroup(boolean useGroup) {
        UseGroup = useGroup;
    }

    public int getDecimalDigits() {
        return DecimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        DecimalDigits = decimalDigits;
    }

    public int getDecimalPointPos() {
        return DecimalPointPos;
    }

    public void setDecimalPointPos(int decimalPointPos) {
        DecimalPointPos = decimalPointPos;
    }

    public int getDecimalTailSharpDigits() {
        return DecimalTailSharpDigits;
    }

    public void setDecimalTailSharpDigits(int decimalTailSharpDigits) {
        DecimalTailSharpDigits = decimalTailSharpDigits;
    }

    public int getIntegerDigits() {
        return IntegerDigits;
    }

    public void setIntegerDigits(int integerDigits) {
        IntegerDigits = integerDigits;
    }

    public int getIntegerHeadSharpDigits() {
        return IntegerHeadSharpDigits;
    }

    public void setIntegerHeadSharpDigits(int integerHeadSharpDigits) {
        IntegerHeadSharpDigits = integerHeadSharpDigits;
    }

    public int getIntegerHeadPos() {
        return IntegerHeadPos;
    }

    public void setIntegerHeadPos(int integerHeadPos) {
        IntegerHeadPos = integerHeadPos;
    }

    public boolean isUseExponent() {
        return UseExponent;
    }

    public void setUseExponent(boolean useExponent) {
        UseExponent = useExponent;
    }

    public int getExponentDigits() {
        return ExponentDigits;
    }

    public void setExponentDigits(int exponentDigits) {
        ExponentDigits = exponentDigits;
    }

    public int getExponentTailSharpDigits() {
        return ExponentTailSharpDigits;
    }

    public void setExponentTailSharpDigits(int exponentTailSharpDigits) {
        ExponentTailSharpDigits = exponentTailSharpDigits;
    }

    public boolean isExponentNegativeSignOnly() {
        return ExponentNegativeSignOnly;
    }

    public void setExponentNegativeSignOnly(boolean exponentNegativeSignOnly) {
        ExponentNegativeSignOnly = exponentNegativeSignOnly;
    }

    public int getDividePlaces() {
        return DividePlaces;
    }

    public void setDividePlaces(int dividePlaces) {
        DividePlaces = dividePlaces;
    }

    public int getPercents() {
        return Percents;
    }

    public void setPercents(int percents) {
        Percents = percents;
    }

    public int getPermilles() {
        return Permilles;
    }

    public void setPermilles(int permilles) {
        Permilles = permilles;
    }

    private boolean UseGroup = false;
    private int DecimalDigits = 0;
    private int DecimalPointPos = -1;
    private int DecimalTailSharpDigits = 0;
    private int IntegerDigits = 0;
    private int IntegerHeadSharpDigits = 0;
    private int IntegerHeadPos = 0;
    private boolean UseExponent = false;
    private int ExponentDigits = 0;
    private int ExponentTailSharpDigits = 0;
    private boolean ExponentNegativeSignOnly = true;
    private int DividePlaces = 0;
    private int Percents = 0;
    private int Permilles = 0;

    public static void getActiveSection(
            String format, /*ref*/ boolean[] positive, boolean zero, /*ref*/ int[] offset, /*ref*/ int[] length)
    {
        int[] lens = new int [3];
        int index = 0;
        int lastPos = 0;
        boolean quoted = false;

        for (int i = 0; i < format.length(); i++) {
            char c = format.charAt(i);

            if (c == '\"' || c == '\'') {
                if (i == 0 || format.charAt(i - 1) != '\\')
                    quoted = !quoted;

                continue;
            }

            if (c == ';' && !quoted && (i == 0 || format.charAt(i - 1) != '\\')) {
                lens [index++] = i - lastPos;
                lastPos = i + 1;
                if (index == 3)
                    break;
            }
        }

        if (index == 0) {
            offset[0] = 0;
            length[0] = format.length();
            return;
        }
        if (index == 1) {
            if (positive[0] || zero) {
                offset[0] = 0;
                length[0] = lens[0];
                return;
            }
            if (lens[0] + 1 < format.length()) {
                positive[0] = true;
                offset[0] = lens [0] + 1;
                length[0] = format.length() - offset[0];
                return;
            }
            else {
                offset[0] = 0;
                length[0] = lens[0];
                return;
            }
        }
        if (index == 2) {
            if (zero) {
                offset[0] = lens[0] + lens[1] + 2;
                length[0] = format.length() - offset[0];
                return;
            }
            if (positive[0]) {
                offset[0] = 0;
                length[0] = lens[0];
                return;
            }
            if (lens[1] > 0) {
                positive[0] = true;
                offset[0] = lens[0] + 1;
                length[0] = lens[1];
                return;
            }
            else {
                offset[0] = 0;
                length[0] = lens[0];
                return;
            }
        }
        if (index == 3) {
            if (zero) {
                offset[0] = lens[0] + lens[1] + 2;
                length[0] = lens[2];
                return;
            }
            if (positive[0]) {
                offset[0] = 0;
                length[0] = lens[0];
                return;
            }
            if (lens [1] > 0) {
                positive[0] = true;
                offset[0] = lens[0] + 1;
                length[0] = lens[1];
                return;
            }
            else {
                offset[0] = 0;
                length[0] = lens[0];
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    public static CustomInfo parse (String format, int offset, int length /* NumberFormatInfo nfi*/)
    {
        char literal = '\0';
        boolean integerArea = true;
        boolean decimalArea = false;
        boolean exponentArea = false;
        boolean sharpContinues = true;

        CustomInfo info = new CustomInfo ();
        int groupSeparatorCounter = 0;

        for (int i = offset; i - offset < length; i++) {
            char c = format.charAt(i);

            if (c == literal && c != '\0') {
                literal = '\0';
                continue;
            }
            if (literal != '\0')
                continue;

            if (exponentArea && (c != '\0' && c != '0' && c != '#')) {
                exponentArea = false;
                integerArea = (info.DecimalPointPos < 0);
                decimalArea = !integerArea;
                i--;
                continue;
            }

            switch (c) {
                case '\\':
                    i++;
                    continue;
                case '\'':
                case '\"':
                    if (c == '\"' || c == '\'') {
                        literal = c;
                    }
                    continue;
                case '#':
                    if (sharpContinues && integerArea)
                        info.IntegerHeadSharpDigits++;
                    else if (decimalArea)
                        info.DecimalTailSharpDigits++;
                    else if (exponentArea)
                        info.ExponentTailSharpDigits++;

                    /* goto case '0'; */
                case '0':
                    if (c != '#') {
                        sharpContinues = false;
                        if (decimalArea)
                            info.DecimalTailSharpDigits = 0;
                        else if (exponentArea)
                            info.ExponentTailSharpDigits = 0;
                    }
                    if (info.IntegerHeadPos == -1)
                        info.IntegerHeadPos = i;

                    if (integerArea) {
                        info.IntegerDigits++;
                        if (groupSeparatorCounter > 0)
                            info.UseGroup = true;
                        groupSeparatorCounter = 0;
                    }
                    else if (decimalArea)
                        info.DecimalDigits++;
                    else if (exponentArea)
                        info.ExponentDigits++;
                    break;
                case 'e':
                case 'E':
                    if (info.UseExponent)
                        break;

                    info.UseExponent = true;
                    integerArea = false;
                    decimalArea = false;
                    exponentArea = true;
                    if (i + 1 - offset < length) {
                        char nc = format.charAt(i + 1);
                        if (nc == '+')
                            info.ExponentNegativeSignOnly = false;
                        if (nc == '+' || nc == '-')
                            i++;
                        else if (nc != '0' && nc != '#') {
                            info.UseExponent = false;
                            if (info.DecimalPointPos < 0)
                                integerArea = true;
                        }
                    }

                    break;
                case '.':
                    integerArea = false;
                    decimalArea = true;
                    exponentArea = false;
                    if (info.DecimalPointPos == -1)
                        info.DecimalPointPos = i;
                    break;
                case '%':
                    info.Percents++;
                    break;
                case '\u2030':
                    info.Permilles++;
                    break;
                case ',':
                    if (integerArea && info.IntegerDigits > 0)
                        groupSeparatorCounter++;
                    break;
                default:
                    break;
            }
        }

        if (info.ExponentDigits == 0)
            info.UseExponent = false;
        else
            info.IntegerHeadSharpDigits = 0;

        if (info.DecimalDigits == 0)
            info.DecimalPointPos = -1;

        info.DividePlaces += groupSeparatorCounter * 3;

        return info;
    }

    public String format(String format, int offset, int length, /*NumberFormatInfo*/DecimalFormatSymbols nfi, boolean positive, StringBuilder sb_int, StringBuilder sb_dec, StringBuilder sb_exp)
    {
        StringBuilder sb = new StringBuilder ();
        char literal = '\0';
        boolean integerArea = true;
        boolean decimalArea = false;
        int intSharpCounter = 0;
        int sb_int_index = 0;
        int sb_dec_index = 0;

        int[] groups = /*nfi.NumberGroupSizes*/{ 3 };
        String groupSeparator = Character.toString(nfi.getGroupingSeparator());
        int intLen = 0, total = 0, groupIndex = 0, counter = 0, groupSize = 0;
        if (UseGroup && groups.length > 0) {
            intLen = sb_int.length();
            for (int i = 0; i < groups.length; i++) {
                total += groups [i];
                if (total <= intLen)
                    groupIndex = i;
            }
            groupSize = groups [groupIndex];
            int fraction = intLen > total ? intLen - total : 0;
            if (groupSize == 0) {
                while (groupIndex >= 0 && groups [groupIndex] == 0)
                    groupIndex--;

                groupSize = fraction > 0 ? fraction : groups [groupIndex];
            }
            if (fraction == 0)
                counter = groupSize;
            else {
                groupIndex += fraction / groupSize;
                counter = fraction % groupSize;
                if (counter == 0)
                    counter = groupSize;
                else
                    groupIndex++;
            }
        }
        else
            UseGroup = false;

        for (int i = offset; i - offset < length; i++) {
            char c = format.charAt(i);

            if (c == literal && c != '\0') {
                literal = '\0';
                continue;
            }
            if (literal != '\0') {
                sb.append(c);
                continue;
            }

            switch (c) {
                case '\\':
                    i++;
                    if (i - offset < length)
                        sb.append(format.charAt(i));
                    continue;
                case '\'':
                case '\"':
                    if (c == '\"' || c == '\'')
                        literal = c;
                    continue;
                case '#':
                    /*goto case '0';*/
                case '0':
                    if (integerArea) {
                        intSharpCounter++;
                        if (IntegerDigits - intSharpCounter < sb_int.length() + sb_int_index || c == '0')
                            while (IntegerDigits - intSharpCounter + sb_int_index < sb_int.length()) {
                                sb.append (sb_int.charAt(sb_int_index++));
                                if (UseGroup && --intLen > 0 && --counter == 0) {
                                    sb.append(groupSeparator);
                                    if (--groupIndex < groups.length && groupIndex >= 0)
                                        groupSize = groups [groupIndex];
                                    counter = groupSize;
                                }
                            }
                        break;
                    }
                    else if (decimalArea) {
                        if (sb_dec_index < sb_dec.length())
                            sb.append(sb_dec.charAt(sb_dec_index++));
                        break;
                    }

                    sb.append(c);
                    break;
                case 'e':
                case 'E':
                    if (sb_exp == null || !UseExponent) {
                        sb.append(c);
                        break;
                    }

                    boolean flag1 = true;
                    boolean flag2 = false;

                    int q;
                    for (q = i + 1; q - offset < length; q++) {
                        if (format.charAt(q) == '0') {
                            flag2 = true;
                            continue;
                        }
                        if (q == i + 1 && (format.charAt(q) == '+' || format.charAt(q) == '-'))
                            continue;
                        if (!flag2)
                            flag1 = false;
                        break;
                    }

                    if (flag1) {
                        i = q - 1;
                        integerArea = (DecimalPointPos < 0);
                        decimalArea = !integerArea;

                        sb.append(c);
                        sb.append(sb_exp);
                        sb_exp = null;
                    }
                    else
                        sb.append(c);

                    break;
                case '.':
                    if (DecimalPointPos == i) {
                        if (DecimalDigits > 0) {
                            while (sb_int_index < sb_int.length())
                                sb.append(sb_int.charAt(sb_int_index++));
                        }
                        if (sb_dec.length() > 0)
                            sb.append(nfi.getDecimalSeparator());
                    }
                    integerArea = false;
                    decimalArea = true;
                    break;
                case ',':
                    break;
                case '%':
                    sb.append(nfi.getPercent());
                    break;
                case '\u2030':
                    sb.append(nfi.getPerMill());
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }

        if (!positive)
            sb.insert(0, nfi.getMinusSign());

        return sb.toString();
    }
}
