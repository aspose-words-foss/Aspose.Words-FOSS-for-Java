// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/01/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Allows to configure document hyphenation options.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-hyphenation/">Working with Hyphenation</a> documentation article.</para>
/// </summary>
public class HyphenationOptions implements Cloneable
{
    /// <summary>
    /// Makes a deep clone.
    /// </summary>
    HyphenationOptions deepClone()
    {
        return (HyphenationOptions)this.memberwiseClone();
    }

    /// <summary>
    /// Sets the <see cref="ConsecutiveHyphenLimit"/> property without checking input value.
    /// </summary>
    void setConsecutiveHyphenLimitSafe(int value)
    {
        mConsecutiveHyphenLimit = value;
    }

    /// <summary>
    /// Sets the <see cref="HyphenationZone"/> property without checking input value.
    /// </summary>
    void setHyphenationZoneSafe(int value)
    {
        mHyphenationZone = value;
    }

    /// <summary>
    /// Returns true if input value is acceptable value for <see cref="ConsecutiveHyphenLimit"/>.
    /// </summary>
    private static boolean isValidConsecutiveHyphenLimit(int value)
    {
        return (value >= 0) && (value <= 32767);
    }

    /// <summary>
    /// Returns true if input value is acceptable value for <see cref="HyphenationZone"/>.
    /// </summary>
    private static boolean isValidHyphenationZone(int value)
    {
        return (value > 0) && (value <= 31680);
    }

    /// <summary>
    /// Gets or sets value determining whether automatic hyphenation is turned on for the document.
    /// Default value for this property is <c>false</c>.
    /// </summary>
    public boolean getAutoHyphenation() { return mAutoHyphenation; }
    public void setAutoHyphenation(boolean value) { mAutoHyphenation = value; }

    /// <summary>
    /// Gets or sets the maximum number of consecutive lines that can end with hyphens.
    /// Default value for this property is 0.
    /// </summary>
    /// <remarks>
    /// <p>If value of this property is set to 0, any number of consecutive lines can end with hyphens.</p>
    /// <p>The property does not have effect when saving to fixed page formats e.g. PDF.</p>
    /// </remarks>
    public int getConsecutiveHyphenLimit() { return mConsecutiveHyphenLimit; }
    public void setConsecutiveHyphenLimit(int value)
    {
        if (!isValidConsecutiveHyphenLimit(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        mConsecutiveHyphenLimit = value;
    }

    /// <summary>        
    /// Gets or sets the distance in 1/20 of a point from the right margin within which you do not want
    /// to hyphenate words.
    /// Default value for this property is 360 (0.25 inch).
    /// </summary>
    /// <dev>
    /// MS Word does not hyphenate words starting inside the hyphenation zone. 
    /// A smaller zone reduces the raggedness of the right margin, but more words may require hyphens. 
    /// A larger zone increases the raggedness of the right margin, but fewer words may require hyphens.
    /// </dev>
    public int getHyphenationZone() { return mHyphenationZone; }
    public void setHyphenationZone(int value)
    {
        if (!isValidHyphenationZone(value))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        mHyphenationZone = value;
    }

    /// <summary>
    /// Gets or sets value determining whether words written in all capital letters are hyphenated.
    /// Default value for this property is <c>true</c>.
    /// </summary>
    public boolean getHyphenateCaps() { return mHyphenateCaps; }
    public void setHyphenateCaps(boolean value) { mHyphenateCaps = value; }

    private boolean mAutoHyphenation;
    private int mConsecutiveHyphenLimit = CONSECUTIVE_HYPHEN_LIMIT_DEFAULT;
    private int mHyphenationZone = HYPHENATION_ZONE_DEFAULT;
    private boolean mHyphenateCaps = HYPHENATE_CAPS_DEFAULT;

    static final int CONSECUTIVE_HYPHEN_LIMIT_DEFAULT = 0;
    static final int HYPHENATION_ZONE_DEFAULT = 360;
    static final boolean HYPHENATE_CAPS_DEFAULT = true;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

