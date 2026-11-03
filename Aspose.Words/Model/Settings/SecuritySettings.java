package com.aspose.words;

import com.aspose.CryptoUtilPal;

// Sets FIPS mode. Works on Java only at the moment.

/// <summary>
/// Specifies security settings for a document.
/// </summary>
/// <remarks>
/// Aspose.Words for Java is able to work in both FIPS mode and non-FIPS mode.
/// By default it works in non-FIPS mode.
/// In order to enable FIPS mode one should call SecuritySettings.startFipsMode().
/// It is not possible to return back to non-FIPS inside current thread.
/// Also custom SecureRandom could be used via setSecureRandom() method.
/// </remarks>
public class SecuritySettings
{
    /// <summary>
    /// Turns on FIPS mode.
    /// Transition back to non-FIPS mode is impossible inside current thread.
    /// </summary>
    public static void startFipsMode()
    {
        CryptoUtilPal.startFipsMode();
    }

    /// <summary>
    /// Indicates whether Aspose.Words works in FIPS mode.
    /// </summary>
    /// <returns>true if Aspose.Words works in FIPS mode.</returns>
    public static boolean isInFipsMode()
    {
        return CryptoUtilPal.isInFipsMode();
    }
}
