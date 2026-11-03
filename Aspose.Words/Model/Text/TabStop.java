// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.msNumber;
import com.aspose.Debug;


/// <summary>
/// Represents a single custom tab stop. The <see cref="TabStop"/> object is a member of the
/// <see cref="TabStopCollection"/> collection.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p>Normally, a tab stop specifies a position where a tab stop exists. But because
/// tab stops can be inherited from parent styles, it might be needed for the child object
/// to define explicitly that there is no tab stop at a given position. To clear
/// an inherited tab stop at a given position, create a <see cref="TabStop"/> object and set
/// <see cref="Alignment"/> to <see cref="TabAlignment.Clear"/>.</p>
///
/// <p>For more information see <see cref="TabStopCollection"/>.</p>
///
/// <seealso cref="ParagraphFormat"/>
/// <seealso cref="TabStopCollection"/>
/// <seealso cref="Aspose.Words.Document.DefaultTabStop"/>
/// </remarks>
public final class TabStop implements Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TabStop()
    {
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    public TabStop(double position)
    {
    	this(position, TabAlignment.LEFT, TabLeader.NONE);
	
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="position">The position of the tab stop in points.</param>
    /// <param name="alignment">A <see cref="TabAlignment"/> value that
    /// specifies the alignment of text at this tab stop.</param>
    /// <param name="leader">A <see cref="TabLeader"/> value that specifies
    /// the type of the leader line displayed under the tab character.</param>
    public TabStop(double position, /*TabAlignment*/int alignment, /*TabLeader*/int leader)
    {
    	this(ConvertUtilCore.pointToTwip(position), alignment, leader);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    TabStop(int positionTwips, /*TabAlignment*/int alignment, /*TabLeader*/int leader)
    {
        mPositionTwips = positionTwips;
        mAlignment = alignment;
        mLeader = leader;
    }

    /// <summary>
    /// Creates legacy tab stop instance.
    /// </summary>
    static TabStop createLegacyTabStop(double position, /*ListTrailingCharacter*/int trailingCharacter)
    {
        TabStop tabStop = new TabStop(position, TabAlignment.LIST, TabLeader.NONE);
        tabStop.isLegacyTab(true);

        tabStop.mTrailingCharacter = trailingCharacter;

        return tabStop;
    }

    TabStop deepClone()
    {
        return (TabStop)memberwiseClone();
    }

    /// <summary>
    /// Compares with the specified <see cref="TabStop"/>.
    /// </summary>
    public boolean equals(TabStop rhs)
    {
        return
            (mPositionTwips == rhs.mPositionTwips) &&
            (mAlignment == rhs.mAlignment) &&
            (mLeader == rhs.mLeader) &&
            (mToleranceTwips == rhs.mToleranceTwips) &&
            (mUndocumented40 == rhs.mUndocumented40);
    }

    /// <summary>
    /// Calculates hash code for this object.
    /// </summary>
    /// <javaName>int hashCode()</javaName>
    public /*override*/ int hashCode()
    {
        int hashCode = (/*int.hashCode()*/mPositionTwips);
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/mAlignment;
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/mLeader;
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/mToleranceTwips);
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(mUndocumented40);
        return hashCode;
    }

    /// <summary>
    /// Gets the position of the tab stop in points.
    /// </summary>
    public double getPosition() { return ConvertUtilCore.twipToPoint(mPositionTwips); }

    /// <summary>
    /// Gets or sets the alignment of text at this tab stop.
    /// </summary>
    public /*TabAlignment*/int getAlignment() { return mAlignment; }
    public void setAlignment(/*TabAlignment*/int value)
    {
        mAlignment = value;
    }

    /// <summary>
    /// Gets or sets the type of the leader line displayed under the tab character.
    /// </summary>
    public /*TabLeader*/int getLeader() { return mLeader; }
    public void setLeader(/*TabLeader*/int value)
    {
        mLeader = value;
    }

    /// <summary>
    /// Returns <c>true</c> if this tab stop clears any existing tab stops in this position.
    /// </summary>
    public boolean isClear() { return mAlignment == TabAlignment.CLEAR; }

    int getPositionTwips() { return mPositionTwips; }
    void setPositionTwips(int value)
    {
        mPositionTwips = value;
    }

    /// <summary>
    /// Tolerance on both sides of the position to delete tab stops, twips.
    /// </summary>
    int getToleranceTwips() { return mToleranceTwips; }
    void setToleranceTwips(int value)
    {
        mToleranceTwips = value;
    }

    /// <summary>
    /// An undocumented flag that is sometimes set on tab stops in MS Word.
    /// </summary>
    boolean getUndocumented40() { return mUndocumented40; }
    void setUndocumented40(boolean value)
    {
        mUndocumented40 = value;
    }

    /// <summary>
    /// Gets trailing character used for legacy tab stop.
    /// </summary>
    /*ListTrailingCharacter*/int getLegacyListTrailingCharacter()
    {
        Debug.assertTrue(isLegacyTab(), "Should be called for legacy tab stop only.");
        return (mTrailingCharacter);
    }

    /// <summary>
    /// True, if tab stop is produced by legacy list formatting.
    /// </summary>
    boolean isLegacyTab() { return mIsLegacyTab; }; private void isLegacyTab(boolean value) { mIsLegacyTab = value; };

    private boolean mIsLegacyTab;

    private int mPositionTwips;
    private /*TabAlignment*/int mAlignment;
    private /*TabLeader*/int mLeader;
    /// <summary>
    /// I'd really love to remove tolerances some day.
    /// Looks like RTF and WordML do not have this feature, only DOC does.
    /// </summary>
    private int mToleranceTwips;
    private boolean mUndocumented40;
    private /*ListTrailingCharacter*/int mTrailingCharacter;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

