/*
 * This file is derived from jai-imageio-core (https://github.com/jai-imageio/jai-imageio-core),
 * reduced and adapted by Aspose. The notice of the original follows.
 *
 * Copyright (c) 2005 Sun Microsystems, Inc. All  Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this  list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for
 * use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
// Copyright (c) 2001-2017 Aspose Pty Ltd. All Rights Reserved.
// August 2017 by Slava Durin
package com.aspose.ms.java.ImageIO.tiff.decoder;

import com.aspose.ms.java.IO.ImageStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Retrieves and decodes the value of tags described in {@link TiffTagType}.
 */
public class TiffTagValue
{
    /**
     * Reads the tag's value from the stream according to the given type.
     */
    public static Object readTagValue(ImageStreamReader mReader, int type, int count) throws Exception
    {
        Object obj = null;
        switch (type)
        {
            case TiffTagType.TIFF_BYTE:
            case TiffTagType.TIFF_SBYTE:
            case TiffTagType.TIFF_UNDEFINED:
                byte[] bvalues = new byte[count];
                mReader.readFully(bvalues, 0, count);
                obj = bvalues;
                break;

            case TiffTagType.TIFF_ASCII:
                byte[] charvalues = new byte[count];
                mReader.readFully(charvalues, 0, count);

                // Can be multiple strings
                List<String> v = new ArrayList<String>();
                boolean inString = false;
                int prevIndex = 0;
                for (int index = 0; index <= count; index++)
                {
                    if (index < count && charvalues[index] != 0)
                    {
                        if (!inString)
                        {
                            // start of string
                            prevIndex = index;
                            inString = true;
                        }
                    }
                    else
                    { // null or special case at end of string
                        if (inString)
                        {
                            // end of string
                            String s = new String(charvalues, prevIndex, index - prevIndex);
                            v.add(s);
                            inString = false;
                        }
                    }
                }

                count = v.size();
                String[] strings;
                if (count != 0)
                {
                    strings = new String[count];
                    for (int c = 0; c < count; c++)
                        strings[c] = v.get(c);
                }
                else
                {
                    // This case has been observed when the value of
                    // 'count' recorded in the field is non-zero but
                    // the value portion contains all nulls.
                    count = 1;
                    strings = new String[]{""};
                }

                obj = strings;
                break;


            case TiffTagType.TIFF_SHORT:
                char[] cvalues = new char[count];
                for (int j = 0; j < count; j++)
                    cvalues[j] = (char) (mReader.readUnsignedShort());

                obj = cvalues;
                break;

            case TiffTagType.TIFF_LONG:
            case TiffTagType.TIFF_IFD_POINTER:
                long[] lvalues = new long[count];
                for (int j = 0; j < count; j++)
                    lvalues[j] = mReader.readUnsignedInt();

                obj = lvalues;
                break;

            case TiffTagType.TIFF_RATIONAL:
                long[][] llvalues = new long[count][2];
                for (int j = 0; j < count; j++)
                {
                    llvalues[j][0] = mReader.readUnsignedInt();
                    llvalues[j][1] = mReader.readUnsignedInt();
                }
                obj = llvalues;
                break;

            case TiffTagType.TIFF_SSHORT:
                short[] svalues = new short[count];
                for (int j = 0; j < count; j++)
                    svalues[j] = mReader.readShort();

                obj = svalues;
                break;

            case TiffTagType.TIFF_SLONG:
                int[] ivalues = new int[count];
                for (int j = 0; j < count; j++)
                    ivalues[j] = mReader.readInt();

                obj = ivalues;
                break;

            case TiffTagType.TIFF_SRATIONAL:
                int[][] iivalues = new int[count][2];
                for (int j = 0; j < count; j++)
                {
                    iivalues[j][0] = mReader.readInt();
                    iivalues[j][1] = mReader.readInt();
                }
                obj = iivalues;
                break;

            case TiffTagType.TIFF_FLOAT:
                float[] fvalues = new float[count];
                for (int j = 0; j < count; j++)
                    fvalues[j] = mReader.readFloat();

                obj = fvalues;
                break;

            case TiffTagType.TIFF_DOUBLE:
                double[] dvalues = new double[count];
                for (int j = 0; j < count; j++)
                    dvalues[j] = mReader.readDouble();

                obj = dvalues;
                break;

            default:
                // XXX Warning
                System.out.println("BAD TAG VALUE: " + type);
                break;
        }
        return obj;
    }

    /**
     * @return string representation of the tag's value
     */
    public static String getValue(Object value)
    {
        if (value == null)
            return "null";

        if (value.getClass().isArray())
        {
            if (value instanceof byte[])
                return Arrays.toString((byte[]) value);
            if (value instanceof short[])
                return Arrays.toString((short[]) value);
            if (value instanceof int[])
                return Arrays.toString((int[]) value);
            if (value instanceof long[])
                return Arrays.toString((long[]) value);
            if (value instanceof float[])
                return Arrays.toString((float[]) value);
            if (value instanceof double[])
                return Arrays.toString((double[]) value);
            if (value instanceof boolean[])
                return Arrays.toString((boolean[]) value);
            if (value instanceof char[])
                return Arrays.toString((char[]) value);

            return Arrays.toString((Object[]) value);
        }

        return value.toString();
    }

    public static byte[] getAsBytes(Object value) { return (byte[]) value; }

    public static char[] getAsChars(Object data)
    {
        return (char[]) data;
    }

    public static String getAsString(int index, int type, Object data)
    {
        switch (type)
        {
            case TiffTagType.TIFF_BYTE:
            case TiffTagType.TIFF_UNDEFINED:
                return String.valueOf(((byte[]) data)[index] & 0xff);
            case TiffTagType.TIFF_SBYTE:
                return String.valueOf(((byte[]) data)[index]);
            case TiffTagType.TIFF_SHORT:
                return String.valueOf(((char[]) data)[index] & 0xffff);
            case TiffTagType.TIFF_SSHORT:
                return String.valueOf(((short[]) data)[index]);
            case TiffTagType.TIFF_SLONG:
                return String.valueOf(((int[]) data)[index]);
            case TiffTagType.TIFF_LONG:
            case TiffTagType.TIFF_IFD_POINTER:
                return String.valueOf((int) ((long[]) data)[index]);
            case TiffTagType.TIFF_FLOAT:
                return String.valueOf((int) ((float[]) data)[index]);
            case TiffTagType.TIFF_DOUBLE:
                return String.valueOf((int) ((double[]) data)[index]);
            case TiffTagType.TIFF_SRATIONAL:
                int[] ivalue = getAsSRational(index, data);
                return String.valueOf((int) ((double) ivalue[0] / ivalue[1]));
            case TiffTagType.TIFF_RATIONAL:
                long[] lvalue = getAsRational(index, data);
                return String.valueOf((int) ((double) lvalue[0] / lvalue[1]));
            case TiffTagType.TIFF_ASCII:
                String s = ((String[]) data)[index];
                return s;
            default:
                throw new ClassCastException();
        }
    }

    public static int[] getAsSRational(int index, Object data) { return ((int[][]) data)[index]; }

    public static long[] getAsRational(int index, Object data) { return ((long[][]) data)[index]; }

    public static long[] getAsLongs(Object data) { return (long[]) data; }

    public static long getAsLong(int index, int type, Object data)
    {
        switch (type)
        {
            case TiffTagType.TIFF_BYTE:
            case TiffTagType.TIFF_UNDEFINED:
                return ((byte[]) data)[index] & 0xff;
            case TiffTagType.TIFF_SBYTE:
                return ((byte[]) data)[index];
            case TiffTagType.TIFF_SHORT:
                return ((char[]) data)[index] & 0xffff;
            case TiffTagType.TIFF_SSHORT:
                return ((short[]) data)[index];
            case TiffTagType.TIFF_SLONG:
                return ((int[]) data)[index];
            case TiffTagType.TIFF_LONG:
            case TiffTagType.TIFF_IFD_POINTER:
                return ((long[]) data)[index];
            case TiffTagType.TIFF_SRATIONAL:
                int[] ivalue = getAsSRational(index, data);
                return (long) ((double) ivalue[0] / ivalue[1]);
            case TiffTagType.TIFF_RATIONAL:
                long[] lvalue = getAsRational(index, data);
                return (long) ((double) lvalue[0] / lvalue[1]);
            case TiffTagType.TIFF_ASCII:
                String s = ((String[]) data)[index];
                return (long) Double.parseDouble(s);
            default:
                throw new ClassCastException();
        }
    }

    public static int getAsInt(int index, int type, Object data)
    {
        switch (type)
        {
            case TiffTagType.TIFF_BYTE:
            case TiffTagType.TIFF_UNDEFINED:
                return ((byte[]) data)[index] & 0xff;
            case TiffTagType.TIFF_SBYTE:
                return ((byte[]) data)[index];
            case TiffTagType.TIFF_SHORT:
                return ((char[]) data)[index] & 0xffff;
            case TiffTagType.TIFF_SSHORT:
                return ((short[]) data)[index];
            case TiffTagType.TIFF_SLONG:
                return ((int[]) data)[index];
            case TiffTagType.TIFF_LONG:
            case TiffTagType.TIFF_IFD_POINTER:
                return (int) ((long[]) data)[index];
            case TiffTagType.TIFF_FLOAT:
                return (int) ((float[]) data)[index];
            case TiffTagType.TIFF_DOUBLE:
                return (int) ((double[]) data)[index];
            case TiffTagType.TIFF_SRATIONAL:
                int[] ivalue = getAsSRational(index, data);
                return (int) ((double) ivalue[0] / ivalue[1]);
            case TiffTagType.TIFF_RATIONAL:
                long[] lvalue = getAsRational(index, data);
                return (int) ((double) lvalue[0] / lvalue[1]);
            case TiffTagType.TIFF_ASCII:
                String s = ((String[]) data)[index];
                return (int) Double.parseDouble(s);
            default:
                throw new ClassCastException();
        }
    }

    public static int[] getAsInts(Object data)
    {
        if (data instanceof int[])
        {
            return (int[]) data;
        }
        else if (data instanceof char[])
        {
            char[] cdata = (char[]) data;
            int[] idata = new int[cdata.length];
            for (int i = 0; i < cdata.length; i++)
                idata[i] = (int) (cdata[i] & 0xffff);

            return idata;
        }
        else if (data instanceof short[])
        {
            short[] sdata = (short[]) data;
            int[] idata = new int[sdata.length];
            for (int i = 0; i < sdata.length; i++)
                idata[i] = (int) sdata[i];

            return idata;
        }
        else
        {
            throw new ClassCastException("Data not char[], short[], or int[]!");
        }
    }
}
