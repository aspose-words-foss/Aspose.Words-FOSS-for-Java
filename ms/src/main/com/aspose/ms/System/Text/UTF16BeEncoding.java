package com.aspose.ms.System.Text;

public class UTF16BeEncoding extends BaseUnicodeEncoding
{
    public UTF16BeEncoding()
    {
        this(true);
    }

    public UTF16BeEncoding(boolean bom)
    {
        super(new EncodingDataItem(1201, "utf-16BE", "utf-16BE", "utf-16BE", "utf-16BE", 1200), bom);
    }

    @Override
    public byte[] getPreamble()
    {
        return mUseBOM ? Utf16BEPreamble : gNoPreamble;
    }

    protected static final byte[] Utf16BEPreamble = new byte[]{(byte) 0xfe, (byte) 0xff};
 }
