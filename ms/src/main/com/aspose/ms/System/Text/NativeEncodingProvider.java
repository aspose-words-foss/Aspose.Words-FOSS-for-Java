package com.aspose.ms.System.Text;

import java.nio.charset.Charset;
import java.util.*;

class NativeEncodingProvider extends BaseEncodingProvider
{
    NativeEncodingProvider()
    {
        int codePageStart = CODE_PAGE_JAVA_START;
        for (Charset charset : Charset.availableCharsets().values())
        {
            String charsetName = charset.displayName();
            mSupportedCodePages.put(codePageStart, charsetName);
            mSupportedNames.put(charsetName, codePageStart);
            codePageStart++;
        }
    }

    @Override
    public Encoding getEncoding(String name)
    {
        // Encoding cache it
        return mSupportedNames.containsKey(name)
                ? new BaseEncoding(new EncodingDataItem(mSupportedNames.get(name), name))
                : null;
    }

    @Override
    public Collection<Encoding> getAvailableEncodings()
    {
        return Collections.emptyList();
    }

    @Override
    public Encoding getEncoding(int codepage)
    {
        // Encoding cache it
        return mSupportedCodePages.containsKey(codepage)
                ? new BaseEncoding(new EncodingDataItem(codepage, mSupportedCodePages.get(codepage)))
                : null;
    }

    @Override
    public Integer getCodePageByName(String name)
    {
        return mSupportedNames.get(name);
    }

    @Override
    protected Map<Integer, Encoding> initSupportedEncodings()
    {
        return Collections.emptyMap();
    }

    private static final int CODE_PAGE_JAVA_START = 65535 + 1;

    private final Map<Integer, String> mSupportedCodePages = new HashMap<Integer, String>();
    private final Map<String, Integer> mSupportedNames = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
}
