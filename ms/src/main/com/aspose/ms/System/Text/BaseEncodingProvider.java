package com.aspose.ms.System.Text;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

abstract class BaseEncodingProvider extends EncodingProvider
{
    protected BaseEncodingProvider()
    {
        mSupportedEncodings = initSupportedEncodings();
        for (Encoding encoding : mSupportedEncodings.values())
            mSupportedNames.put(encoding.getEncodingInfoName(), encoding.getCodePage());

        mSupportedNames.putAll(defineAliasToCodePage());
    }

    @Override
    public Encoding getEncoding(String name)
    {
        Integer codePage = mSupportedNames.get(name);
        return codePage == null ? null : mSupportedEncodings.get(codePage);
    }

    @Override
    public Encoding getEncoding(int codepage)
    {
        return mSupportedEncodings.get(codepage);
    }

    @Override
    public Integer getCodePageByName(String name)
    {
        return mSupportedNames.get(name);
    }

    protected Map<String, Integer> defineAliasToCodePage() {return Collections.emptyMap();}

    public Collection<Encoding> getAvailableEncodings()
    {
        return mSupportedEncodings.values();
    }

    protected abstract Map<Integer, Encoding> initSupportedEncodings();

    private final Map<Integer, Encoding> mSupportedEncodings;
    private final Map<String, Integer> mSupportedNames = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
}
