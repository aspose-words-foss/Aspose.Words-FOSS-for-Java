//Konstantin Sidorenko Jan 24, 2007
package com.aspose.ms.System.Text;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msString;

public final class msStringBuilder
{
    public static java.lang.StringBuilder appendLine(java.lang.StringBuilder builder)
    {
        return builder.append("\r\n");
    }

    public static java.lang.StringBuilder appendLine(java.lang.StringBuilder builder, String s)
    {
        builder.append(s);
        return builder.append("\r\n");
    }

    /**
     * Workaround for append null string platform behaviour:
     * .Net appends null string as "", but java appends it just as "null". .Net behaviour is emulated here.
     */
    public static java.lang.StringBuilder append(java.lang.StringBuilder builder, String value)
    {
        if (value != null)
            builder.append(value);

        return builder;
    }

    public static java.lang.StringBuilder append(java.lang.StringBuilder builder, Object value)
    {
        if (value != null)
            builder.append(value);

        return builder;
    }

    /**
     * .Net StringBuilder.Append(char value, int repeatCount).
     *
     * Appends repeated character at the end of this string builder.
     */
    public static java.lang.StringBuilder append(java.lang.StringBuilder builder, char value, int repeatCount)
    {
        if (repeatCount==0)
            return builder;
        if (repeatCount<0)
            throw new IllegalArgumentException("repeatCount is negative!");

        char[] chars = new char[repeatCount];
        //Arrays.fill(chars, value) does the same but with range check and others not needed here.
        for (int i = 0; i < repeatCount; i++)
            chars[i] = value;

        return builder.append(chars);
     }

    /**
     * Almost as .Net StringBuilder.Insert(int index, string value, int count) but uses char instead of string:
     * Inserts one or more copies of a specified _char_ (instead of string) into this instance at the specified character position.
     */
    public static java.lang.StringBuilder insert(java.lang.StringBuilder builder, int index, char value, int repeatCount)
    {
        if (repeatCount==0)
            return builder;
        if (repeatCount<0)
            throw new IllegalArgumentException("repeatCount is negative!");

        char[] chars = new char[repeatCount];
        //Arrays.fill(chars, value) does the same but with range check and others not needed here.
        for (int i = 0; i < repeatCount; i++)
            chars[i] = value;

        return builder.insert(index, chars);
     }

    /**
     * .Net StringBuilder.Insert(int index, string value, int count).
     *
     * Inserts multiple copies of a string into this string builder at the specified position.
     * Existing characters are shifted to make room for the new text.
     * The capacity is adjusted as needed. If value equals String.Empty, this
     * string builder is not changed.
     */
    public static java.lang.StringBuilder insert(java.lang.StringBuilder builder, int index, String value, int count)
    {
        if (value == null || value.length() == 0 || count == 0)
            return builder;

        if (count < 0)
            throw new IllegalArgumentException("count is negative!");

        int currentLength = builder.length();
        if (index < 0 || index > currentLength)
            throw new IllegalArgumentException("index is out of bounds!");

        int requiredLength = currentLength + value.length() * count;
        builder.ensureCapacity(requiredLength);

        String suffix = null;
        if (index < currentLength)
        {
            suffix = builder.substring(index);
            builder.setLength(index);
        }

        for (int i = 0; i < count; i++)
            builder.append(value);

        if (suffix != null)
            builder.append(suffix);

        return builder;
     }

	/**
	 * .Net StringBuilder.Replace(String, String).
	 *
	 * Replaces all occurrences of a specified substring in this instance with another specified string.
	 * oldStr cannot be null or "".
	 * If newStr == null it interpreted as "".
	 */
	public static java.lang.StringBuilder replace(java.lang.StringBuilder builder, java.lang.String oldStr, java.lang.String newStr)
	{
		if (oldStr == null)
			throw new NullPointerException("oldStr");

		if ("".equals(oldStr))
			throw new IllegalArgumentException("oldStr");

		if (newStr == null)
			newStr = "";

		int start = 0;
		int oldLength = oldStr.length();
		int newLength = newStr.length();

		while ((start = builder.indexOf(oldStr, start)) >= 0)
        {
            builder.replace(start, start + oldLength, newStr);
            start += newLength;
        }

		return builder;
	}

    /**
     * Replaces, within a substring of this instance, all occurrences of a specified string with another specified string.
     * @param builder The underlying builder.
     * @param oldValue The string to replace.
     * @param newValue The string that replaces oldValue, or null.
     * @param startIndex The position in this instance where the substring begins.
     * @param count The length of the substring.
     * @return A reference to the underlying StringBuilder instance with all instances of oldValue replaced by newValue in the range from startIndex to startIndex + count - 1.
     *
     * @throws NullPointerException if oldValue is null.
     * @throws IllegalArgumentException if startIndex is not within this instance
     * @throws IllegalArgumentException if The length of oldValue is zero.
     * @throws IllegalArgumentException startIndex or count is less than zero.
     * @throws IllegalArgumentException startIndex plus count indicates a character position not within this instance.
     */
    public static java.lang.StringBuilder replace(java.lang.StringBuilder builder, java.lang.String oldValue, java.lang.String newValue, int startIndex, int count)
    {
        int stringLength = builder.length();
        if (startIndex > stringLength)
            throw new IndexOutOfBoundsException("startIndex is not within this instance");

        if (count < 0 || startIndex > stringLength - count)
            throw new IllegalArgumentException("count cannot be less than zero");

        if (oldValue == null)
            throw new NullPointerException("oldValue is null");

        if (oldValue.isEmpty())
            throw new IllegalArgumentException("oldValue is empty");

        if (startIndex < 0)
            throw new IllegalArgumentException("startIndex cannot be less than zero");

        if (startIndex + count > builder.length())
            throw new IllegalArgumentException("startIndex + count is not within this instance");

        int oldLength = oldValue.length();
        if (count < oldLength)
            return builder;

        if (newValue == null)
            newValue = msString.Empty;

        int newLength = newValue.length();
        int currentLength = startIndex + count;

        while ((startIndex = builder.indexOf(oldValue, startIndex)) >= 0 && ((startIndex + oldLength <= currentLength)))
        {
            builder.replace(startIndex, startIndex + oldLength, newValue);
            startIndex += newLength;
            currentLength = currentLength - oldLength + newLength;
        }

        return builder;
    }

	/**
	 * .Net StringBuilder.Replace(char, char).
	 *
	 * Returns a StringBuilder with all instances of oldChar replaced with newChar.
     * The size of the StringBuilder is unchanged because we're only replacing characters.
	 */
	public static java.lang.StringBuilder replace(java.lang.StringBuilder builder, char oldChar, char newChar)
	{
        for (int i = 0; i < builder.length(); i++)
        {
            if (builder.charAt(i) == oldChar)
                builder.setCharAt(i, newChar);
        }

		return builder;
	}

	/**
	 * .Net StringBuilder.Replace(char, char, int, int).
	 *
	 * Returns a StringBuilder with all instances of oldChar replaced with newChar.
     * The size of the StringBuilder is unchanged because we're only replacing characters.
     * If startIndex and count are specified, we only replace characters in the range from
     * startIndex to startIndex+count
	 */
	public static java.lang.StringBuilder replace(java.lang.StringBuilder builder, char oldChar, char newChar, int startIndex, int count)
	{
        if (startIndex > builder.length())
            throw new IllegalArgumentException("startIndex");
        if (count < 0 || startIndex > builder.length() - count)
            throw new IllegalArgumentException("count");

        for (int i = startIndex; i < startIndex + count; i++)
        {
            if (builder.charAt(i) == oldChar)
                builder.setCharAt(i, newChar);
        }

		return builder;
	}

    /**
     * .Net StringBuilder.AppendFormat() method - single java method for all .Net overloads.
     *
     * Simplified realization: IFormatProvider and ICustomFormatter doesn't supported.
     */
    public static java.lang.StringBuilder appendFormat(java.lang.StringBuilder builder, String format, /*params*/ Object... args)
    {
         if (format == null || args == null)
             throw new IllegalArgumentException(format == null? "format" : "args");

         char[] chars = format.toCharArray();
         int pos = 0;
         int len = chars.length;
         char ch = '\u0000';

         while (true)
         {
             int p = pos;
             int i = pos;
             while (pos < len)
             {
                 ch = chars[pos];

                 pos++;
                 if (ch == '}')
                 {
                     if(pos < len && chars[pos]=='}') // Treat as escape character for }}
                         pos++;
                     else
                         throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                 }

                 if (ch == '{')
                 {
                     if(pos < len && chars[pos]=='{') // Treat as escape character for {{
                     {
                         pos++;
                     }
                     else
                     {
                         pos--;
                         break;
                     }
                 }

                 chars[i++] = ch;
             }
             if (i > p)
                 builder.append(chars, p, i - p);
             if (pos == len)
                 break;
             pos++;
             if (pos == len || (ch = chars[pos]) < '0' || ch > '9')
                 throw new IllegalArgumentException("Invalid Format String at position: " + pos);
             int index = 0;
             do
             {
                 index = index * 10 + ch - '0';
                 pos++;
                 if (pos == len)
                     throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                 ch = chars[pos];
             }
             while (ch >= '0' && ch <= '9' && index < 1000000);

             if (index >= args.length)
                 throw new IllegalArgumentException("Invalid Format String at position: " + pos);

             while (pos < len && (ch=chars[pos]) == ' ')
                 pos++;

             boolean leftJustify = false;
             int width = 0;
             if (ch == ',')
             {
                 pos++;
                 while (pos < len && chars[pos] == ' ') pos++;

                 if (pos == len)
                     throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                 ch = chars[pos];
                 if (ch == '-')
                 {
                     leftJustify = true;
                     pos++;
                     if (pos == len)
                         throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                     ch = chars[pos];
                 }
                 if (ch < '0' || ch > '9')
                     throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                 do
                 {
                     width = width * 10 + ch - '0';
                     pos++;
                     if (pos == len)
                         throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                     ch = chars[pos];
                 }
                 while (ch >= '0' && ch <= '9' && width < 1000000);
             }

             while (pos < len && (ch=chars[pos]) == ' ')
                 pos++;
             Object arg = args[index];
             String fmt = null;
             if (ch == ':')
             {
                 pos++;
                 p = pos;
                 i = pos;
                 while (true)
                 {
                     if (pos == len)
                         throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                     ch = chars[pos];
                     pos++;
                     if (ch == '{')
                     {
                         if(pos < len && chars[pos]=='{')  // Treat as escape character for {{
                             pos++;
                         else
                             throw new IllegalArgumentException("Invalid Format String at position: " + pos);
                     }
                     else if (ch == '}')
                     {
                         if(pos < len && chars[pos]=='}')  // Treat as escape character for }}
                             pos++;
                         else
                         {
                             pos--;
                             break;
                         }
                     }

                     chars[i++] = ch;
                 }
                 if (i > p)
                     fmt = new String(chars, p, i - p); 
             }

             if (ch != '}')
                 throw new IllegalArgumentException("Invalid Format String at position: " + pos);
             pos++;
             String s = null;

	         //insert formatted argument here
			 if (arg == null)
				 s = "";
			 else if (arg instanceof String)
				 s = arg.toString();
			 //IFormattable in .Net:
			 //Note: not all .Net formats supported!
			 //see comments in m$Number.
			 else if (arg instanceof Number)
				 s = msNumber.format(arg, fmt);
			 else if (arg instanceof Guid)
				 s = ((Guid) arg).toString(fmt);
			 else if (arg instanceof DateTime)
				 s = ((DateTime) arg).toString(fmt);
			 else
				 s = arg.toString();

			 if (s == null)
                  s = "";

             int pad = width - s.length();
             if (!leftJustify && pad > 0)
                 msStringBuilder.append(builder, ' ', pad);

             builder.append(s);

             if (leftJustify && pad > 0)
                 msStringBuilder.append(builder, ' ', pad);
         }

         return builder;
    }
}
