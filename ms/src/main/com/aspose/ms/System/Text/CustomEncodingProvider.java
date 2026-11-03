package com.aspose.ms.System.Text;

import com.aspose.ms.java.charsets.dos720charset.DOS720Charset;

import java.util.HashMap;
import java.util.Map;

class CustomEncodingProvider extends BaseEncodingProvider
{
    @Override
    protected Map<String, Integer> defineAliasToCodePage()
    {
        return new HashMap<String, Integer>()
        {{
            put("Unicode", CODE_PAGE_UNICODE);
            put("UTF-16LE", CODE_PAGE_UNICODE);
            put("cp862", 862);
        }};
    }

    @Override
    protected Map<Integer, Encoding> initSupportedEncodings()
    {
        Map<Integer, Encoding> encodings = new HashMap<Integer, Encoding>();
        encodings.put(1250, createWindows1250());
        encodings.put(1251, createWindows1251());
        encodings.put(1252, createWindows1252());
        encodings.put(1253, createWindows1253());
        encodings.put(1254, createWindows1254());
        encodings.put(1255, createWindows1255());
        encodings.put(1256, createWindows1256());
        encodings.put(1257, createWindows1257());
        encodings.put(1258, createWindows1258());
        encodings.put(CODE_PAGE_UTF7, new UTF7Encoding());
        encodings.put(CODE_PAGE_UTF8, new UTF8Encoding());
        encodings.put(CODE_PAGE_UNICODE, new UTF16LeEncoding());
        encodings.put(CODE_PAGE_BIG_ENDIAN_UNICODE, new UTF16BeEncoding());
        encodings.put(CODE_PAGE_UNICODE_32, new UTF32LeEncoding());
        encodings.put(CODE_PAGE_BIG_ENDIAN_UNICODE_32, new UTF32BeEncoding());
        encodings.put(CODE_PAGE_DOS720, new BaseEncoding(new EncodingDataItem(720, "DOS-720", "DOS-720", "DOS-720", "DOS-720", 1256), new DOS720Charset()));
        encodings.put(862, new BaseEncoding(new EncodingDataItem(862, "DOS-862", "DOS-862", "DOS-862", "DOS-862", 1255, "IBM862")));
        encodings.put(866, new BaseEncoding(new EncodingDataItem(866, "cp866", "cp866", "cp866", "cp866", 1251, "IBM866")));
        return encodings;
    }

    private Encoding createWindows1258()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1258,
                "windows-1258",
                "windows-1258",
                "windows-1258",
                "windows-1258",
                1258)
        );
    }

    private Encoding createWindows1256()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1256,
                "windows-1256",
                "windows-1256",
                "windows-1256",
                "windows-1256",
                1256)
        );
    }

    private Encoding createWindows1255()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1255,
                "windows-1255",
                "windows-1255",
                "windows-1255",
                "windows-1255",
                1255)
        );
    }

    private Encoding createWindows1254()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1254,
                "windows-1254",
                "iso-8859-9",
                "windows-1254",
                "windows-1254",
                1254)
        );
    }

    private Encoding createWindows1251()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1251,
                "windows-1251",
                "koi8-r",
                "windows-1251",
                "windows-1251",
                1251));
    }

    //https://en.wikipedia.org/wiki/Windows-1250  all 'Undefined' mapped to their positions
    private Encoding createWindows1250()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1250,
                "windows-1250",
                "iso-8859-2",
                "windows-1250",
                "windows-1250",
                1250)
        );
    }

    //https://en.wikipedia.org/wiki/Windows-1252  see 'Undefined'
    private Encoding createWindows1252()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1252,
                "Windows-1252",
                "iso-8859-1",
                "Windows-1252",
                "Windows-1252",
                1252)
        )
                .setCharToByteMapping(new HashMap<Integer, Integer>()
                {{
                    put(128, 63);
                    put(142, 63);
                    put(158, 63);
                }})
                .setByteToCharMapping(new HashMap<Integer, Integer>()
                {{
                    put(128, 8364);
                    put(142, 381);
                    put(158, 382);
                }});
    }

    //https://en.wikipedia.org/wiki/Windows-1253  see 'Undefined'
    private Encoding createWindows1253()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1253,
                "Windows-1253",
                "iso-8859-7",
                "Windows-1253",
                "Windows-1253",
                1253)
        )
                .setByteToCharMapping(new HashMap<Integer, Integer>()  // used in getCharsCore
                {{
                    put(131, 402);
                    put(170, 63737);
                    put(240, 960);
                }})
                .setCharToByteMapping(new HashMap<Integer, Integer>() // used in getBytesCore
                {{
                    put(63737, 170);
                }});
    }

    //https://en.wikipedia.org/wiki/Windows-1257 see 'Undefined'
    private Encoding createWindows1257()
    {
        return new SBCSCodePageEncoding(new EncodingDataItem(
                1257,
                "windows-1257",
                "windows-1257",
                "windows-1257",
                "windows-1257",
                1257)
        )
                .setByteToCharMapping(new HashMap<Integer, Integer>()
                {{
                    put(131, 402);
                    put(170, 63737);
                    put(240, 960);
                }});
    }
}
