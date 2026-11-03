package com.aspose;

import com.aspose.ms.java.logging.Log;

import java.awt.color.ICC_Profile;
import java.io.File;

/**
 * Returns "platform-standard" ICC profile for CMYK images that carry none. See WORDSJAVA-1855.
 *
 * The library ships no CMYK profile of its own. When the platform has none either, the instance is null
 * and ImageReaderHelper converts CMYK to RGB arithmetically: the colors come out somewhat brighter than with a profile.
 */
class CmykIccProfile
{
    private static ICC_Profile Instance = null;

    public static ICC_Profile getInstance() {
        return Instance;
    }

    public static void setInstance(ICC_Profile instance) {
        Instance = instance;
    }

    private static final String[] PLATFORM_LOCAL_ICC_PROFILES = new String[]
    {
        "C:/Windows/System32/spool/drivers/color/RSWOP.icm",            // windows
        "/System/Library/ColorSync/Profiles/Generic CMYK Profile.icc",  // mac
        "/usr/share/color/icc/ghostscript/default_cmyk.icc"             // linux with Ghostscript installed
    };

    private static void readProfiles()
    {
        // Try local profiles.
        for (int i = 0; i < PLATFORM_LOCAL_ICC_PROFILES.length; i++)
        {
            try
            {
                if (new File(PLATFORM_LOCAL_ICC_PROFILES[i]).exists())
                {
                    Instance = ICC_Profile.getInstance(PLATFORM_LOCAL_ICC_PROFILES[i]);
                    break;
                }
            }
            catch (Throwable e)
            {
                Log.warn(e);
            }
        }
    }

    static
    {
        readProfiles();
    }
}
