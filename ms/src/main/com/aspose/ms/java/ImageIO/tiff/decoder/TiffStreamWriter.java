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
package com.aspose.ms.java.ImageIO.tiff.decoder;

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.java.IO.ImageStreamWriter;

import java.nio.ByteOrder;

public class TiffStreamWriter extends ImageStreamWriter
{
    public void writeTIFFField(TIFFField field) throws Exception
    {
        int count = field.getCount();
        Object data = field.getData();
        if (data == null)
            return;

        switch (field.getType())
        {
            case TiffTagType.TIFF_ASCII:
                for (int i = 0; i < count; i++)
                {
                    String s = ((String[]) data)[i];
                    int length = s.length();
                    for (int j = 0; j < length; j++)
                        writeByte((byte) (s.charAt(j) & 0xff));

                    writeByte((byte) 0);
                }
                break;
            case TiffTagType.TIFF_UNDEFINED:
            case TiffTagType.TIFF_BYTE:
            case TiffTagType.TIFF_SBYTE:
                writeBytes((byte[]) data);
                break;
            case TiffTagType.TIFF_SHORT:
                writeChars((char[]) data, 0, ((char[]) data).length);
                break;
            case TiffTagType.TIFF_SSHORT:
                writeShorts((short[]) data, 0, ((short[]) data).length);
                break;
            case TiffTagType.TIFF_SLONG:
                writeInts((int[]) data, 0, ((int[]) data).length);
                break;
            case TiffTagType.TIFF_LONG:
                for (int i = 0; i < count; i++)
                    writeInt((int) (((long[]) data)[i]));

                break;
            case TiffTagType.TIFF_IFD_POINTER:
                writeInt(0); // will need to be backpatched
                break;
            case TiffTagType.TIFF_FLOAT:
                writeFloats((float[]) data, 0, ((float[]) data).length);
                break;
            case TiffTagType.TIFF_DOUBLE:
                writeDoubles((double[]) data, 0, ((double[]) data).length);
                break;
            case TiffTagType.TIFF_SRATIONAL:
                for (int i = 0; i < count; i++)
                {
                    writeInt(((int[][]) data)[i][0]);
                    writeInt(((int[][]) data)[i][1]);
                }
                break;
            case TiffTagType.TIFF_RATIONAL:
                for (int i = 0; i < count; i++)
                {
                    long num = ((long[][]) data)[i][0];
                    long den = ((long[][]) data)[i][1];
                    writeInt((int) num);
                    writeInt((int) den);
                }
                break;
            default:
                // error
        }
    }

    public TiffStreamWriter(ByteOrder byteOrder, Stream stream) throws Exception
    {
        super(byteOrder, stream);
    }
}
