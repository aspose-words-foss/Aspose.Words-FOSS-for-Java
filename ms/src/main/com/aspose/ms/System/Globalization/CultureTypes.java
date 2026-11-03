package com.aspose.ms.System.Globalization;

public final class CultureTypes
{
    public static final int NEUTRAL_CULTURES = 0x0001;           // Neutral cultures are cultures like "en", "de", "zh", etc, for enumeration this includes ALL neutrals regardless of other flags
    public static final int SPECIFIC_CULTURES = 0x0002;             // Non-netural cultuers.  Examples are "en-us", "zh-tw", etc., for enumeration this includes ALL specifics regardless of other flags
    public static final int INSTALLED_WIN_32_CULTURES = 0x0004;             // Win32 installed cultures in the system and exists in the framework too., this is effectively all cultures

    public static final int ALL_CULTURES = NEUTRAL_CULTURES | SPECIFIC_CULTURES | INSTALLED_WIN_32_CULTURES;

    private CultureTypes() {}
}
