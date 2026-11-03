package com.aspose.ms.System.Text;

public abstract class EncoderFallback
{
    public abstract EncoderFallbackBuffer createFallbackBuffer();

    public abstract int getMaxCharCount() ;
}
