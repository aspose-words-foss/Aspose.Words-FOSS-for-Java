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

/**
 * https://github.com/jai-imageio/jai-imageio-core/blob/master/src/main/java/com/github/jaiimageio/plugins/tiff/TIFFTag.java
 */
 class TiffTagType
{
    /** Flag for 8 bit unsigned integers. */
    public static final int TIFF_BYTE        =  1;

    /** Flag for null-terminated ASCII strings. */
    public static final int TIFF_ASCII       =  2;

    /** Flag for 16 bit unsigned integers. */
    public static final int TIFF_SHORT       =  3;

    /** Flag for 32 bit unsigned integers. */
    public static final int TIFF_LONG        =  4;

    /** Flag for pairs of 32 bit unsigned integers. */
    public static final int TIFF_RATIONAL    =  5;

    /** Flag for 8 bit signed integers. */
    public static final int TIFF_SBYTE       =  6;

    /** Flag for 8 bit uninterpreted bytes. */
    public static final int TIFF_UNDEFINED   =  7;

    /** Flag for 16 bit signed integers. */
    public static final int TIFF_SSHORT      =  8;

    /** Flag for 32 bit signed integers. */
    public static final int TIFF_SLONG       =  9;

    /** Flag for pairs of 32 bit signed integers. */
    public static final int TIFF_SRATIONAL   = 10;

    /** Flag for 32 bit IEEE floats. */
    public static final int TIFF_FLOAT       = 11;

    /** Flag for 64 bit IEEE doubles. */
    public static final int TIFF_DOUBLE      = 12;

    /**
     * Flag for IFD pointer defined in TIFF Tech Note 1 in
     * TIFF Specification Supplement 1.
     */
    public static final int TIFF_IFD_POINTER = 13;

    /**
     * The numerically smallest constant representing a TIFF data type.
     */
    public static final int MIN_DATATYPE = TIFF_BYTE;

    /**
     * The numerically largest constant representing a TIFF data type.
     */
    public static final int MAX_DATATYPE = TIFF_IFD_POINTER;

    private static final int[] sizeOfType = {
            0, //  0 = n/a
            1, //  1 = byte
            1, //  2 = ascii
            2, //  3 = short
            4, //  4 = long
            8, //  5 = rational
            1, //  6 = sbyte
            1, //  7 = undefined
            2, //  8 = sshort
            4, //  9 = slong
            8, // 10 = srational
            4, // 11 = float
            8, // 12 = double
            4, // 13 = IFD_POINTER
    };

    public static int getSizeOfType(int dataType)
    {
        if (dataType < 1 || dataType > 13)
            throw new IllegalArgumentException("dataType out of range! " + dataType);

        return sizeOfType[dataType];
    }
}
