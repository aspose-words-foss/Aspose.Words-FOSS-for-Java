package com.aspose.ms.System.Text;

public abstract class DecoderFallbackBuffer
{
    public abstract boolean movePrevious();

    public void reset() {}

    public abstract boolean fallback(byte[] bytesUnknown, int index);

    public abstract char getNextChar();

    public abstract int getRemaining();
}
