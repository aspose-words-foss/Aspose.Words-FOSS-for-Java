package com.aspose.ms.System.Security.Cryptography;

/**
 * Java simplified implementation of
 * https://docs.microsoft.com/en-us/dotnet/api/system.security.cryptography.keysizes
 *
 * Determines the set of valid key sizes for the symmetric cryptographic algorithms.
 */
public final class KeySizes
{
    // This structure is used for returning the set of legal key sizes and
    // block sizes of the symmetric algorithms.
    // Note: this class should be sealed, otherwise someone could sub-class it and the read-only
    // properties we depend on can have setters. Ideally, we should have a struct here (value type)
    // but we use what we have now and try to close the hole allowing someone to specify an invalid key size

    public KeySizes(int minSize, int maxSize, int skipSize)
    {
        m_minSize = minSize; m_maxSize = maxSize; m_skipSize = skipSize;
    }

    public int getMinSize()
    {
        return m_minSize;
    }

    public int getMaxSize()
    {
        return m_maxSize;
    }

    public int getSkipSize()
    {
        return m_skipSize;
    }

    private int m_minSize;
    private int m_maxSize;
    private int m_skipSize;
}
