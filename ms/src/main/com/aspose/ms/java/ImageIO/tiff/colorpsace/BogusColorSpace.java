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
package com.aspose.ms.java.ImageIO.tiff.colorpsace;

import java.awt.color.ColorSpace;

public class BogusColorSpace extends ColorSpace
{
    private static int getType(int numComponents)
    {
        if (numComponents < 1)
            throw new IllegalArgumentException("numComponents < 1!");

        int type;
        switch (numComponents)
        {
            case 1:
                type = ColorSpace.TYPE_GRAY;
                break;
            default:
                // Based on the constant definitions TYPE_2CLR=12 through
                // TYPE_FCLR=25. This will return unknown types for
                // numComponents > 15.
                type = numComponents + 10;
        }

        return type;
    }

    public BogusColorSpace(int numComponents)
    {
        super(getType(numComponents), numComponents);
    }

    public float[] toRGB(float[] colorvalue)
    {
        if (colorvalue.length < getNumComponents())
            throw new ArrayIndexOutOfBoundsException("colorvalue.length < getNumComponents()");

        float[] rgbvalue = new float[3];
        System.arraycopy(colorvalue, 0, rgbvalue, 0, Math.min(3, getNumComponents()));
        return colorvalue;
    }

    public float[] fromRGB(float[] rgbvalue)
    {
        if (rgbvalue.length < 3)
            throw new ArrayIndexOutOfBoundsException("rgbvalue.length < 3");

        float[] colorvalue = new float[getNumComponents()];
        System.arraycopy(rgbvalue, 0, colorvalue, 0, Math.min(3, colorvalue.length));
        return rgbvalue;
    }

    public float[] toCIEXYZ(float[] colorvalue)
    {
        if (colorvalue.length < getNumComponents())
            throw new ArrayIndexOutOfBoundsException("colorvalue.length < getNumComponents()");

        float[] xyzvalue = new float[3];
        System.arraycopy(colorvalue, 0, xyzvalue, 0, Math.min(3, getNumComponents()));
        return colorvalue;
    }

    public float[] fromCIEXYZ(float[] xyzvalue)
    {
        if (xyzvalue.length < 3)
            throw new ArrayIndexOutOfBoundsException("xyzvalue.length < 3");

        float[] colorvalue = new float[getNumComponents()];
        System.arraycopy(xyzvalue, 0, colorvalue, 0, Math.min(3, colorvalue.length));
        return xyzvalue;
    }
}
