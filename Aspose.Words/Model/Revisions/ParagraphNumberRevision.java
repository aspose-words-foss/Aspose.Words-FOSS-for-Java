// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/06/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msNumber;


/// <summary>
/// Used to track revision marking data for paragraph numbers.
///
/// Normally, revisions are only stored when they are not accepted by the user.
/// But numbering revision in Word documents can be stored both in the accepted and non-accepted state.
/// Also, DOC file can have this object stored in some "dead" state that I don't fully understand.
///
/// Initially we had code that supported numbering revision in all states and preserved it
/// in the model and during open/save. But that was too buggy. So I decided to store only
/// non-accepted numbering revisions in the model. This simplified the code greatly.
/// The codecs now load all numbering revisions regardless of their state, but <see cref="DocumentPostLoader"/>
/// deletes accepted and dead numbering revisions.
///
///
/// In the DOC file there are two "entities" that represent number revisions:
/// a boolean flag that indicates a revision was inserted and a NUMRM structure.
/// This class combines the above two into a single entity, it is easier to work with one.
///
/// Here is how it is all represented in the model:
///
/// *** List formatting added to a paragraph while tracking changes is on:
/// ParaPr.ListId is non-zero
/// ParaPr.NumberRevision.IsInserted = true
/// ParaPr.NumberRevision.WasNumbered = false
/// ParaPr.NumberRevision.NumberLocations, NumberStyles, NumberValues all zeros.
///
/// *** ACCEPTED: List formatting accepted on a paragraph:
/// ParaPr.ListId is non-zero
/// ParaPr.NumberRevision.IsInserted = false
/// ParaPr.NumberRevision.WasNumbered = true
/// ParaPr.NumberRevision.NumberLocations, NumberStyles, NumberValues set to "previous" value.
///
/// *** List formatting deleted while tracking changes is on:
/// ParaPr.ListId is zero
/// ParaPr.NumberRevision.IsInserted = false
/// ParaPr.NumberRevision.WasNumbered = true
/// ParaPr.NumberRevision.NumberLocations, NumberStyles, NumberValues set to "previous" value.
///
/// *** DEAD? An old formatting revision that remains after changes were accepted?
/// ParaPr.ListId any?
/// ParaPr.NumberRevision.IsInserted = false
/// ParaPr.NumberRevision.WasNumbered = false
/// ParaPr.NumberRevision.NumberLocations, NumberStyles, NumberValues set to "previous" value?
/// </summary>
class ParagraphNumberRevision extends RevisionBase implements IComplexAttr, Cloneable
{
    ParagraphNumberRevision()
    {
    	super("", DateTime.MinValue);
	
        mNumberStyles = new /*NumberStyle*/int[ListLevel.MAX_LEVELS];
        mNumberValues = new int[ListLevel.MAX_LEVELS];
        mNumberLocations = new byte[ListLevel.MAX_LEVELS];
        setNumberFormat("");
    }

    public /*override*/ int hashCode()
    {
        int hashCode = msNumber.booleanHashCode(isInsertion());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(getWasNumbered());
        for (byte value : getNumberLocations())
            hashCode = (hashCode * 397) ^ (/*byte.hashCode()*/(int) (value));
        for (/*NumberStyle*/int value : getNumberStyles())
            hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/value;
        for (int value : getNumberValues())
            hashCode = (hashCode * 397) ^ (/*int.hashCode()*/value);
        if (getNumberFormat() != null)
            hashCode = (hashCode * 397) ^ getNumberFormat().hashCode();
        return hashCode;
    }

    /// <summary>
    /// See comments above. Returns true if the change is numbering insertion or change/deletion.
    /// </summary>
    boolean isActive() { return isInsertion() || isNumbering(); }

    /// <summary>
    /// Returns true if this revision is a "numbering change".
    /// Numbering change means the number was either changed or deleted.
    /// We cannot tell the difference between change and deletion by looking at this object alone.
    /// See comments above.
    /// </summary>
    boolean isNumbering()
    {
        if (getWasNumbered())
        {
            // RK Theoretically, a number revision is a "numbering change" if WasNumbered is true.
            // However, in some documents (especially loaded from DOC) it seems we need to check
            // if other properties were changed too. Therefore this code is somewhat a hack
            // that checks for "changes" by comparing with default (bullet formatting).
            for (/*NumberStyle*/int numberStyle : getNumberStyles())
            {
                if (numberStyle != NumberStyle.BULLET)
                    return true;
            }
        }

        return false;
    }

    /// <summary>
    /// True indicates the paragraph has a "list formatting added" revision.
    /// It is expected that paragraph's list id is also set when this is true.
    /// See comments above.
    /// </summary>
    boolean isInsertion() { return mIsInsertion; }; void isInsertion(boolean value) { mIsInsertion = value; };

    private boolean mIsInsertion;

    /// <summary>
    /// The DOC documentation says:
    /// True if this paragraph was numbered when revision mark tracking was turned on.
    /// See comments above.
    /// </summary>
    boolean getWasNumbered() { return mWasNumbered; }; void setWasNumbered(boolean value) { mWasNumbered = value; };

    private boolean mWasNumbered;

    /// <summary>
    /// Indexes into NumberFormat of the locations of paragraph number place holders for each level.
    /// </summary>
    byte[] getNumberLocations() { return mNumberLocations; };

    private byte[] mNumberLocations;

    /// <summary>
    /// Number styles for the paragraph number place holders for each level.
    /// </summary>
    /*NumberStyle*/int[] getNumberStyles() { return mNumberStyles; };

    private /*NumberStyle*/int[] mNumberStyles;

    /// <summary>
    /// Numerical value for each level place holder in NumberFormat.
    /// </summary>
    int[] getNumberValues() { return mNumberValues; };

    private int[] mNumberValues;

    /// <summary>
    /// The text string for the paragraph number, containing level place holders.
    /// </summary>
    String getNumberFormat() { return mNumberFormat; }; void setNumberFormat(String value) { mNumberFormat = value; };

    private String mNumberFormat;

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean isInheritedComplexAttr() { return false; }

    /// <summary>
    /// Reserved for system use. IComplexAttr.
    /// </summary>
    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        // RK This is supposed to create a deep clone, but I don't see a point of cloning number locations, styles and values
        // because we never change those in the model. If we start changing them, then we need to clone them here.
        return (ParagraphNumberRevision)memberwiseClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

