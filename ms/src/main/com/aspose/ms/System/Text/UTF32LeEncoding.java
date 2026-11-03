package com.aspose.ms.System.Text;

public class UTF32LeEncoding extends BaseUnicodeEncoding
{
    public UTF32LeEncoding()
    {
        this(true);
    }

    public UTF32LeEncoding(boolean bom)
    {
        super(new EncodingDataItem(12000, "utf-32", "utf-32", "utf-32", "utf-32", 1200, "UTF-32LE"), bom);
    }

    @Override
    public byte[] getPreamble()
    {
        return mUseBOM ? Utf32LEPreamble : gNoPreamble;
    }

    protected static final byte[] Utf32LEPreamble = new byte[]{(byte) 0xff, (byte) 0xfe, 0, 0};
}
