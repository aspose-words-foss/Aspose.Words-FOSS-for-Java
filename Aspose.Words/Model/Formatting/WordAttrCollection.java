// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msArray;


/// <summary>
/// Collection of attribute values that can have formatting revision.
/// </summary>
abstract class WordAttrCollection extends AttrCollection
{
    /// <summary>
    /// Specifies modes to get attribute value.
    /// </summary>
    /// <remarks>
    /// AM. This enum is just temporary solution, I was forced to make it by following reasons:
    /// - I cannot use RevisionsView as it is going to be public later.
    ///
    /// - There is some inconsistency with expand flags. For example we can set both AfterChanges and Revised flags but
    ///   this has no meaning at all.
    ///
    /// - More inconsistency is that for example RunPrExpandFlags.DocumentDefaults also has no meaning at this level.
    ///
    /// - Most important is that we have single method to get attribute value but
    ///   have different flags for run and paragraph formatting: RunPrExpandFlags and ParaPrExpandFlags.
    ///   That means that we have to convert both flag types into one type anyway.
    ///
    /// It seems that we need to reconsider expand flags approach and make some rework to avoid inconsistency.
    /// </remarks>
    protected /*enum*/ final class ValueVersion
    {
        private ValueVersion(){}
        
        /// <summary>
        /// Gets original attribute value.
        /// </summary>
        public static final int ORIGINAL = 0;

        /// <summary>
        /// Gets final attribute value.
        /// </summary>
        public static final int FINAL = 1;

        /// <summary>
        /// Gets raw after changes attribute value. Has meaning during DOCX import only.
        /// </summary>
        public static final int AFTER_CHANGES = 2;

        public static final int length = 3;
    }

    Object fetchAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getAttr(key, toValueVersion(revisionsView), true);
    }

    Object getDirectAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return getAttr(key, toValueVersion(revisionsView), false);
    }

    protected Object getAttr(int key, /*ValueVersion*/int valueVersion, boolean globalDefaults)
    {
        Object value = getDirectAttrValueVersion(key, valueVersion);

        if (!globalDefaults)
            return value;

        return (value != null) ? value : getDefaults().get(key);
    }

    private static /*ValueVersion*/int toValueVersion(/*RevisionsView*/int view)
    {
        return (view == RevisionsView.FINAL) ? ValueVersion.FINAL : ValueVersion.ORIGINAL;
    }

    private Object getDirectAttrValueVersion(int key, /*ValueVersion*/int valueVersion)
    {
        // WORDSNET-28929 Empty format revision might be Istd revision.
        if ((valueVersion == ValueVersion.AFTER_CHANGES) && getFormatRevision() != null)
            return getFormatRevision().getRevPr().get(key);

        if ((valueVersion == ValueVersion.FINAL) && hasFormatRevision())
        {
            WordAttrCollection revPr = getFormatRevision().getRevPr();

            // This might be calculated differently for complex attributes. Let's postpone for a while.
            Object value = revPr.get(key);
            if (value != null)
                return value;

            // Format revision can contain Istd attribute.
            // In this case we need to "reset" original formatting i.e we should get formatting from revision only so return null immediately.
            if (revPr.contains(ParaAttr.ISTD) || revPr.contains(FontAttr.ISTD))
                return null;
        }

        return this.get(key);
    }

    /// <summary>
    /// Sets the specified value in the desired collection depending on revisionsView parameter.
    /// </summary>
    /// <remarks>
    /// The method does not replicate MS Word functionality for reserving formatting attributes in the change tracking mode.
    /// The method does not move attributes from one collection to another. It also does not create the format revision if its value is null.
    /// The method assumes that one of the two points is true:
    /// 1. The current collection contains the attributes before the change, Original state. FormatRevision.RevPr contains the delta which
    ///    is expands through AcceptFormatRevision() brings this collection to Final state.
    /// 2. The current collection contains the actual attributes, and Original and Final states are identical.
    ///    FormatRevision.RevPr is strictly equal to null.
    /// </remarks>
    void setAttr(int key, Object value, /*RevisionsView*/int revisionsView)
    {
        if ((getFormatRevision() != null) && (revisionsView == RevisionsView.FINAL))
            getFormatRevision().getRevPr().setAttr(key, value);
        else
            setAttr(key, value);
    }

    /// <summary>
    /// Removes the attribute from the desired attribute collection based on revisionsView parameter.
    /// </summary>
    /// <remarks>
    /// See the remarks to SetAttr(key, value, revisionsView).
    /// </remarks>
    void remove(int key, /*RevisionsView*/int revisionsView)
    {
        if ((getFormatRevision() != null) && (revisionsView == RevisionsView.FINAL))
            getFormatRevision().getRevPr().remove(key);
        else
            remove(key);
    }

    /// <summary>
    /// Rejects formatting revisions on this attribute collection.
    /// </summary>
    void rejectFormatRevision()
    {
        remove(RevisionAttr.FORMAT_REVISION);
    }

    /// <summary>
    /// Accepts formatting revisions on this attribute collection.
    /// </summary>
    /*virtual*/ void acceptFormatRevision()
    {
        // The default implementation does not perform any special handling when the style changes in the revision.
        acceptFormatRevisionCore(-1, null);
    }

    /// <summary>
    /// Accepts formatting revisions on this attribute collection.
    ///
    /// Accepting a revision means replacing current attributes with the attributes
    /// from the revision collection and deleting the revision collection.
    ///
    /// If the style was applied during a formatting revision it looks like
    /// we should clear all existing attributes except special things (number, insert and delete revisions).
    /// The parameters allow for this to be done for different collection types.
    /// </summary>
    /// <param name="istdKey">They key that is used for the style attribute. -1 if not used.</param>
    /// <param name="keysToPreserve">Array of keys to preserve, null if none.</param>
    protected void acceptFormatRevisionCore(int istdKey, int[] keysToPreserve)
    {
        FormatRevision revision = getFormatRevision();
        if (revision != null)
        {
            WordAttrCollection revPr = revision.getRevPr();

            // WORDSNET-3081 If the style was applied during a formatting revision it looks like
            // we should clear all existing attributes. Exclude special things like numbering revision.
            if ((istdKey >= 0) && (revPr.getDirectAttr(istdKey) != null) && (keysToPreserve != null))
                clear(keysToPreserve);

            // We should expand attributes rather than just copy in order to correctly
            // expand complex attributes.
            remove(RevisionAttr.FORMAT_REVISION);
            revPr.expandTo(this);
        }

        remove(RevisionAttr.FORMAT_REVISION);
    }

    /// <summary>
    /// Clears collection except attributes with given keys.
    /// </summary>
    void clear(int[] excludeKeys)
    {
        if ((excludeKeys == null) || (excludeKeys.length == 0))
        {
            // Clear entire collection.
            clear();
        }
        else
        {
            // Clear exclude given keys.
            int i = 0;
            while (i < getCount())
                if (msArray.indexOf((excludeKeys), (getKey(i)), 0, (excludeKeys).length) < 0)
                    removeAt(i);
                else
                    i++;
        }
    }

    /// <summary>
    /// Gets direct complex attribute if exist otherwise sets cloned default and returns it.
    /// </summary>
    /// <remarks>
    /// We need the instance of complex attribute to set its part.
    /// </remarks>
    protected Object getOrCreateComplexAttr(int key)
    {
        Object directValue = this.get(key);

        if (directValue != null)
            return directValue;

        Object defaultValue = getDefaults().get(key);

        Object clonedValue = ((IComplexAttr)defaultValue).deepCloneComplexAttr();
        setAttr(key, clonedValue);

        return clonedValue;
    }

    /// <summary>
    /// Returns true if there is not attribute in the formatting revision, attributes are collapsed.
    /// </summary>
    /// <remarks>
    /// Note: MS Word does not show revisions without changes for accept/reject. However, table revisions for rows and
    /// cells in both cases with or without changes MS Word show for accept/reject as single revision on table level.
    /// </remarks>
    boolean hasEmptyFormatRevision() { return (getFormatRevision() != null) && !hasFormatRevision(); }

    /// <summary>
    /// Returns true if there is at least one attribute in the formatting revision.
    /// </summary>
    boolean hasFormatRevision() { return (getFormatRevision() != null) && (getFormatRevision().getRevPr().getCount() > 0); }

    /// <summary>
    /// Returns true if there is delete revision.
    /// </summary>
    boolean hasDeleteRevision() { return getDeleteRevision() != null; }

    /// <summary>
    /// Returns true if there is insert revision.
    /// </summary>
    boolean hasInsertRevision() { return getInsertRevision() != null; }

    /// <summary>
    /// Returns true if the collection has format, insert or delete revision.
    /// </summary>
    boolean hasRevisions() { return (hasFormatRevision() || hasInsertRevision() || hasDeleteRevision() || hasMoveFromRevision() || hasMoveToRevision()); }

    /// <summary>
    /// Returns true if there is moveFrom revision.
    /// </summary>
    boolean hasMoveFromRevision() { return getMoveFromRevision() != null; }

    /// <summary>
    /// Returns true if there is moveTo revision.
    /// </summary>
    boolean hasMoveToRevision() { return getMoveToRevision() != null; }

    /// <summary>
    /// Gets the formatting revision for this collection.
    /// Note this can be null. But even when not null, it can have zero attributes
    /// which still means there is no formatting revision. So use <see cref="HasFormatRevision"/>
    /// to reliably detect a revision.
    /// </summary>
    FormatRevision getFormatRevision() { return (FormatRevision)getDirectAttr(RevisionAttr.FORMAT_REVISION); }
    void setFormatRevision(FormatRevision value) { setAttr(RevisionAttr.FORMAT_REVISION, value); }

    /// <summary>
    /// Gets the delete revision for this collection.
    /// Note this can be null. If null does not have delete revision.
    /// </summary>
    EditRevision getDeleteRevision() { return (EditRevision)getDirectAttr(RevisionAttr.DELETE_REVISION); }
    void setDeleteRevision(EditRevision value) { setAttr(RevisionAttr.DELETE_REVISION, value); }

    /// <summary>
    /// Gets the insert revision for this collection.
    /// Note this can be null. If null does not have insert revision.
    /// </summary>
    EditRevision getInsertRevision() { return (EditRevision)getDirectAttr(RevisionAttr.INSERT_REVISION); }
    void setInsertRevision(EditRevision value) { setAttr(RevisionAttr.INSERT_REVISION, value); }

    /// <summary>
    /// Gets the MoveFrom revision for this collection.
    /// Note this can be null. If null does not have MoveFrom revision.
    /// </summary>
    MoveRevision getMoveFromRevision() { return (MoveRevision)getDirectAttr(RevisionAttr.MOVE_FROM_REVISION); }
    void setMoveFromRevision(MoveRevision value) { setAttr(RevisionAttr.MOVE_FROM_REVISION, value); }

    /// <summary>
    /// Gets the MoveTo revision for this collection.
    /// Note this can be null. If null does not have MoveTo revision.
    /// </summary>
    MoveRevision getMoveToRevision() { return (MoveRevision)getDirectAttr(RevisionAttr.MOVE_TO_REVISION); }
    void setMoveToRevision(MoveRevision value) { setAttr(RevisionAttr.MOVE_TO_REVISION, value); }

    /// <summary>
    /// Used to save direct formatting of table nodes before TableFormattingExpander expands table styles.
    /// </summary>
    WordAttrCollection getSysDirectAttrs() { return (contains(SYS_DIRECT_ATTRS_BACKUP) ? ((AttrCollectionBackup)this.get(SYS_DIRECT_ATTRS_BACKUP)).getPr() : this); }
    void setSysDirectAttrs(WordAttrCollection value) { setAttr(SYS_DIRECT_ATTRS_BACKUP, new AttrCollectionBackup(value)); }

    /// <summary>
    /// Internal attribute.
    /// Used to "backup" direct attributes of table nodes before TableFormattingExpander expands table styles.
    /// Exists only while document is being saved.
    /// </summary>
    static final int SYS_DIRECT_ATTRS_BACKUP = 9999;
}

