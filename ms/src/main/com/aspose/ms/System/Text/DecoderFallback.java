package com.aspose.ms.System.Text;

public abstract class DecoderFallback
{
    public static DecoderFallback getReplacementFallback() {return  null;}
    public static DecoderFallback getExceptionFallback() { return  null;}
    public abstract int getMaxCharCount();

    public abstract DecoderFallbackBuffer createFallbackBuffer();
}
