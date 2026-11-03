package com.aspose.ms.System.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class EncodingProvider
{
    protected EncodingProvider() { }

    public synchronized static void registerProvider(EncodingProvider provider)
    {
        gProviders.add(provider);
    }

    public static Encoding getEncodingFromProvider(int codePage)
    {
        for (EncodingProvider provider : gProviders)
        {
            Encoding baseEncoding = provider.getEncoding(codePage);
            if (baseEncoding != null)
                return baseEncoding;
        }

        return null;
    }

    public static int getCodePageFromName(String name)
    {
        for (EncodingProvider provider : gProviders)
        {
            Integer codePage = provider.getCodePageByName(name);
            if (codePage != null)
                return codePage;
        }

        return -1;
    }

    public abstract Encoding getEncoding(String name);

    public abstract Encoding getEncoding(int codepage);

    public abstract Integer getCodePageByName(String name);

    private static final List<EncodingProvider> gProviders = new ArrayList<EncodingProvider>(3);

    public static final int CODE_PAGE_ASCII = 20127;
    public static final int CODE_PAGE_UTF7 = 65000;
    public static final int CODE_PAGE_UTF8 = 65001;
    public static final int CODE_PAGE_UNICODE = 1200;
    public static final int CODE_PAGE_BIG_ENDIAN_UNICODE = 1201;
    public static final int CODE_PAGE_UNICODE_32 = 12000;
    public static final int CODE_PAGE_BIG_ENDIAN_UNICODE_32 = 12001;
    public static final int CODE_PAGE_DOS720 = 720;
    public static final int CODE_PAGE_1252 = 1252;
    public static final int CODE_PAGE_1251 = 1251;
    public static final int CODE_PAGE_1255 = 1255;
    public static final int CODE_PAGE_BIG_5 = 950;
}
