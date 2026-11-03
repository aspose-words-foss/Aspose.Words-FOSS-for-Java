package com.aspose.ms.java.charsets.dos720charset;

import com.aspose.ms.System.msString;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * Single Byte charset provider.
 * At the moment it provides DOS720 only.
 */
public class SingleByteCharsetProvider extends java.nio.charset.spi.CharsetProvider
{

    public Charset charsetForName(String charsetName)
    {
        if (msString.isNullOrEmpty(charsetName))
            return null;

        charsetName = charsetName.toUpperCase();
        for (Charset charset : mCharsets)
        {
            if (charset.name().equals(charsetName) || charset.aliases().contains(charsetName))
                return charset;
        }

        return null;
    }

    public Iterator charsets()
    {
        return mCharsets.iterator();
    }

    public SingleByteCharsetProvider()
    {
        mCharsets = Arrays.asList(new DOS720Charset());
    }

    private final List<DOS720Charset> mCharsets;
}
