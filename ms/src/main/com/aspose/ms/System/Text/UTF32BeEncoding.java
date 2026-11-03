package com.aspose.ms.System.Text;

public class UTF32BeEncoding extends BaseUnicodeEncoding
{
    public UTF32BeEncoding()
    {
        this(true);
    }

    public UTF32BeEncoding(boolean bom)
    {
        super(new EncodingDataItem(12001, "utf-32BE", "utf-32BE", "utf-32BE", "utf-32BE", 1200), bom);
    }

    @Override
    public byte[] getPreamble()
    {
        return mUseBOM ? Utf32BEPreamble : gNoPreamble;
    }

    protected static final byte[] Utf32BEPreamble = new byte[]{0, 0, (byte) 0xfe, (byte) 0xff};
}
