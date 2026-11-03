package com.aspose.ms.System.Text;

public abstract class EncoderFallbackBuffer
{
    public abstract boolean fallback(char charUnknown, int index);

    public abstract boolean fallback(char charUnknownHigh, char charUnknownLow, int index);

    public abstract char getNextChar();

    public abstract boolean movePrevious();

    public abstract int getRemaining();
}
