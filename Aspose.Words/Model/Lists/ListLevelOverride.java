// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;
import com.aspose.Pair;
import com.aspose.ms.System.msNumber;


/// <summary>
/// Defines override settings for a list level.
/// </summary>
class ListLevelOverride implements Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <remarks>
    /// AM. This constructor is used in DOC reader. We don't have level number when read list to which this override is belong to.
    /// So ListLevel creation is postponed till actual level number read.
    /// </remarks>
    ListLevelOverride()
    {
    }

    ListLevelOverride(DocumentBase document, int levelNumber)
    {
        mListLevel = new ListLevel(document, levelNumber);
    }

    ListLevelOverride deepClone(DocumentBase destination)
    {
        ListLevelOverride lhs = (ListLevelOverride)memberwiseClone();
        lhs.mListLevel = mListLevel.deepClone(destination);
        return lhs;
    }

    /// <summary>
    /// Implements the MS Word's logic that decides whether to get the start at value 
    /// from this object or from the list level properties override.
    /// </summary>
    int getStartAtReal() { return (IsStartAt && IsFormatting) ? getListLevel().getStartAt() : StartAtRaw; }

    /// <summary>
    /// Use this in MS Word importers and exporters only.
    /// Represents the number this level starts at (overrides the list starting number).
    /// Only makes sense when <see cref="IsStartAt"/> is true.
    /// When IsFormatting = false, use this value. But when IsFormatting = true, use StartAt from ListLevel.
    /// </summary>
    /// <remarks>Default value of 1 is a fix for WORDSNET-18475 Note that StartAt from ListLevel has default of 1 also.</remarks>
    int StartAtRaw = 1;

    /// <summary>
    /// True if the start-at value is overridden. But see note above.
    /// </summary>
    boolean IsStartAt;

    /// <summary>
    /// True if the formatting is overridden and you should get all formatting from the
    /// <see cref="ListLevel"/> property.
    /// </summary>
    boolean IsFormatting;

    boolean WriteStartAt = true;

    int Undocumented1;

    int Undocumented2;

    /// <summary>
    /// Defines the formatting override settings.
    /// This should only be used when <see cref="IsFormatting"/> is true.
    /// </summary>
    /// <remarks>
    /// AM. This setter is used in DOC reader. We don't have level number when read list to which this override is belong to.
    /// So ListLevel creation is postponed till actual level number read.
    /// </remarks>
    ListLevel getListLevel() { return mListLevel; }
    void setListLevel(ListLevel value)
    {
        if (mListLevel != null)
            throw new IllegalStateException("ListLevel object already assigned.");

        mListLevel = value;
    }

    /// <summary>
    /// Compares current override with other.
    /// </summary>
    /// <param name="other">Override to be compared.</param>
    /// <param name="alreadyComparedLinkedStyles">HashSet for collecting already compared styles to avoid dead loops.</param>
    boolean equals(ListLevelOverride other, HashSetGeneric<Pair> alreadyComparedLinkedStyles)
    {
        if (!getListLevel().equalsCore(other.getListLevel(), alreadyComparedLinkedStyles))
            return false;

        // DS: Skip cases with formatting for a while.
        if (IsFormatting || other.IsFormatting)
            return true;

        // WORDSNET-18958 Comparison of overrides should take in attention starting value override.
        // Starting value overrides are equal when both overridden with same values or both not overridden.
        return (IsStartAt == other.IsStartAt) && (!IsStartAt || (StartAtRaw == other.StartAtRaw));
    }

    /// <summary>
    /// Calculates hash code for this object.
    /// </summary>
    /// <dev>
    /// To be compatible with the <see cref="Equals(ListLevelOverride, HashSetGeneric&lt;Pair&gt;)"/> method, only
    /// properties that affect visual representation of the list are included into the calculation. Object ID and
    /// similar properties should be ignored.
    /// </dev>
    public /*override*/ int hashCode()
    {
        int hashCode = getListLevel().hashCode();
        if (IsFormatting)
            return hashCode;

        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(IsStartAt);
        if (IsStartAt)
            hashCode = (hashCode * 397) ^ (/*int.hashCode()*/StartAtRaw);

        return hashCode;
    }

    private ListLevel mListLevel;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

