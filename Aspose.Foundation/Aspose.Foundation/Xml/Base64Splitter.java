// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/10/2009 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Convert;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// We need this class because we sometimes need to write different line breaks
/// to match what MS Word is writing. Yet .NET only allows one type of line breaks in the Convert class.
/// </summary>
public class Base64Splitter
{
    public Base64Splitter(byte[] data, int index, int count)
    {
        mBase64Data = com.aspose.ms.System.Convert.toBase64String(data, index, count);
    }

    public String getNext()
    {
        int length = Math.min(LINE_LENGTH, mBase64Data.length() - mCurPos);
        String result = mBase64Data.substring((mCurPos), (mCurPos) + (length));
        mCurPos += length;
        return result;
    }

    public boolean isEof() { return mCurPos >= mBase64Data.length(); }

    public static String convert(byte[] data, String separator)
    {
        Base64Splitter splitter = new Base64Splitter(data, 0, data.length);
        StringBuilder builder = new StringBuilder();
        while (true)
        {
            msStringBuilder.append(builder, splitter.getNext());
            if (splitter.isEof())
                break;

            msStringBuilder.append(builder, separator);
        }
        // AM. I found that separator must be written at end otherwise Word fails to read Ink data.
        msStringBuilder.append(builder, separator);
        
        return builder.toString();
    }
        
    private /*final*/ String mBase64Data;
    private int mCurPos;

    private static final int LINE_LENGTH = 76;
}


