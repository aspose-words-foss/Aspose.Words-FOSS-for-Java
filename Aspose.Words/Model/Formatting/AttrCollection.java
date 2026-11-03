// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.SortedShortListIntegerFallback;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ArrayUtil;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Collection of attribute values and their keys.
/// This is the backbone of the mechanism for objects to store and inherit attributes.
/// </summary>
abstract class AttrCollection extends SortedShortListIntegerFallback
{
    public /*override*/ Object get(int key)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.get(key) : super.get(key);
    }
    public /*override*/ void set(int key, Object value)
    {
        beforeChange();
        super.set(key, value);
    }

    public /*override*/ int getCount()
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.getCount() : super.getCount();
    }

    public /*override*/ int getKey(int index)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.getKey(index) : super.getKey(index);
    }

    public /*override*/ Object getByIndex(int index)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.getByIndex(index) : super.getByIndex(index);
    }

    public /*override*/ void remove(int key)
    {
        beforeChange();
        super.remove(key);
    }

    public /*override*/ void removeAt(int key)
    {
        beforeChange();
        super.removeAt(key);
    }

    /// <summary>
    /// Gets the attribute specified directly in the collection, return null if not found.
    /// </summary>
    public Object getDirectAttr(int key)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.getDirectAttr(key) : super.get(key);
    }

    public /*override*/ boolean containsKey(int key)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.containsKey(key) : super.containsKey(key);
    }

    public /*override*/ boolean contains(int key)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.contains(key) : super.contains(key);
    }

    public /*override*/ int indexOfKey(int key)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.indexOfKey(key) : super.indexOfKey(key);
    }

    public /*override*/ void clear()
    {
        beforeChange();
        super.clear();
    }

    public /*override*/ void add(int key, Object value)
    {
        beforeChange();
        super.add(key, value);
    }

    public /*override*/ boolean containsAnyKey(/*params*/ int... keys)
    {
        return (getInternState() == InternState.INTERNED) ? mPoolItem.Pr.containsAnyKey(keys) : super.containsAnyKey(keys);
    }

    /*InternState*/int getInternState()
    {
        if (mPoolItem == null)
            return InternState.NONE;

        if (msObject.referenceEquals(mPoolItem.Pr, this))
            return InternState.POOLED;

        return InternState.INTERNED;
    }

    void beforeChange()
    {
        if (mPoolItem != null)
            mPoolItem.getInternManager().remove(this);
    }

    /// <summary>
    /// Gets the attribute from the collection or from default attributes, throws if cannot get it.
    /// </summary>
    Object fetchAttr(int key)
    {
        Object value = getDirectAttr(key);
        return (value != null) ? value : fetchInheritedAttr(key);
    }

    /// <summary>
    /// This implementation gets the attribute from the global defaults, throws if not found.
    /// </summary>
    public Object fetchInheritedAttr(int key)
    {
        AttrCollection defaults = getDefaults();

        int index = defaults.indexOfKey(key);
        if (index < 0)
            throw new IllegalStateException(msString.format("Requested default value for an unknown attribute {0}.", key));

        return defaults.getByIndex(index);
    }

    /// <summary>
    /// Stores an attribute in the collection.
    /// </summary>
    public void setAttr(int key, Object value)
    {
        // Adding null attributes to collections is not normally allowed.
        Debug.assertTrue(value != null);

        beforeChange();

        super.set(key, value);
    }

    /// <summary>
    /// Stores an attribute in the collection if the value is not null.
    /// </summary>
    public void setAttrIfNotNull(int key, Object value)
    {
        if (value != null)
            setAttr(key, value);
    }

    /// <summary>
    /// Stores an attribute in the collection if the value is not default value.
    /// </summary>
    public void setAttrIfNotDefault(int key, Object value)
    {
        Object defaultValue = fetchInheritedAttr(key);
        if ((defaultValue == null) || !value.equals(defaultValue))
            setAttr(key, value);
    }

    /// <summary>
    /// Only add attribute to this collection if it is non-default (isNonDefault is true).
    /// This method helps maintain collections where all attributes are not equal to defaults.
    /// </summary>
    /// <remarks>
    /// Used in <see cref="MathObject"/> hierarchy. In all descendant objects the attributes are added only
    /// if they are non-default.
    /// </remarks>
    protected void setAttr(int key, Object value, boolean isNonDefault)
    {
        if (isNonDefault)
            setAttr(key, value);
        else
            remove(key);
    }

    /// <summary>
    /// Moves the attribute to the specified collection.
    /// </summary>
    void moveTo(AttrCollection dst, int key)
    {
        Object value = getDirectAttr(key);
        if (value != null)
        {
            remove(key);
            dst.setAttr(key, value);
        }
    }

    /// <summary>
    /// Mirrors the specified attribute to the specified collection.
    /// </summary>
    /// <remarks>
    /// Note that attribute absence is also copied i.e attribute missing in source collection is removed from destination.
    /// </remarks>
    void mirrorTo(AttrCollection dst, int key)
    {
        Object value = getDirectAttr(key);
        if (value != null)
        {
            dst.setAttr(key, value);
        }
        else
        {
            dst.remove(key);
        }
    }

    /// <summary>
    /// Mirrors the specified attributes to the specified collection.
    /// </summary>
    /// <remarks>
    /// Note that attribute absence is also copied i.e attribute missing in source collection is removed from destination.
    /// </remarks>
    void mirrorTo(AttrCollection dst, /*params*/ int... keys)
    {
        for (int key : keys)
            mirrorTo(dst, key);
    }

    /// <summary>
    /// Copies the attribute collection to destination collection.
    /// </summary>
    /// <remarks>
    /// Similar to ExpandTo method but differs in complex attributes handling: does not clone and does not merge complex attribute values.
    /// </remarks>
    void copyTo(AttrCollection dst)
    {
        for (int i = 0; i < getCount(); i++)
        {
            int key = getKey(i);
            dst.setAttr(key, this.get(key));
        }
    }

    /// <summary>
    /// Copies the attribute value of given key to destination collection.
    /// </summary>
    /// <remarks>
    /// Similar to ExpandTo method but differs in complex attributes handling:
    /// does not clone and does not merge complex attribute values.
    /// </remarks>
    void copyTo(AttrCollection dst, int key)
    {
        Object val = this.get(key);

        if(val != null)
            dst.setAttr(key, val);
    }

    /// <summary>
    /// Adds attribute to this collection if there is no such attribute in it.
    /// </summary>
    void setAttrNoOverride(int key, Object value)
    {
        if (!containsKey(key))
            setAttr(key, value);
    }

    /// <summary>
    /// Concrete classes to implement and return a collection of default attributes.
    /// </summary>
    protected abstract AttrCollection getDefaults();

    /// <summary>
    /// Creates a deep copy of the collection.
    ///
    /// Attributes that implement the IAttr interface are deep copied,
    /// but value types are value copied.
    /// </summary>
    /*virtual*/ AttrCollection cloneCore()
    {
        // Cloned collection should be uninterned.
        AttrCollection lhs = (getInternState() == InternState.INTERNED)
            ? (AttrCollection)mPoolItem.Pr.createEmptyCopy()
            : (AttrCollection)super.createEmptyCopy();

        lhs.mPoolItem = null;

        for (int i = 0; i < getCount(); i++)
        {
            int key = getKey(i);
            Object srcValue = getByIndex(i);
            Object newValue;

            if (srcValue instanceof IComplexAttr)
            {
                IComplexAttr srcComplex = (IComplexAttr)srcValue;

                // The value is a complex attribute that can be inherited.
                // We do not clone inherited attributes because they can (and should be) safely discarded.
                if (srcComplex.isInheritedComplexAttr())
                    continue;

                if (srcValue instanceof ComplexFontName)
                {
                    // WORDSJAVA-1127 Aspose.Words for Java consumes too much memory during PDF conversion.
                    // ComplexFontName is immutable object so we don't need to clone it.
                    newValue = srcValue;
                }
                else
                {
                    newValue = srcComplex.deepCloneComplexAttr();
                }
            }
            else
            {
                // All other attributes - we treat as immutable and don't create a deep clone.
                newValue = srcValue;
            }

            lhs.set(key, newValue);
        }

        return lhs;
    }

    /// <summary>
    /// Expands formatting specified in this collection into the destination collection.
    /// Basically attributes that exist in this collection either override or somehow modify
    /// the attributes in the destination collection.
    ///
    /// DstAttrs is the BeforeChanges attributes.
    /// This is the PositiveDifference attributes.
    /// This method performs AfterChanges = BeforeChanges + PositiveDifference.
    /// DstAttrs becomes AfterChanges.
    /// </summary>
    void expandTo(AttrCollection dstAttrs)
    {
        expandTo(dstAttrs, (IExpandedAttrSaver)null); // Casting for C++ to make it select correct overload. See WORDSCPP-532.
    }

    /// <summary>
    /// Expands formatting with an option to include default collection to dstCollection.
    /// </summary>
    void expandTo(AttrCollection dstAttrs, boolean isAllowDefault)
    {
        if (isAllowDefault)
        {
            AttrCollection defaults = getDefaults();
            Debug.assertTrue(!msObject.referenceEquals(this, defaults));
            defaults.expandTo(dstAttrs);
        }
        expandTo(dstAttrs);
    }

    void expandTo(AttrCollection dstAttrs, IExpandedAttrSaver saver)
    {
        if (dstAttrs == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "dstAttrs");

        for (int i = 0; i < getCount(); i++)
        {
            int key = getKey(i);
            Object srcValue = getByIndex(i);
            expandTo(dstAttrs, key, srcValue, saver);
        }
    }

    private static void expandTo(AttrCollection dstAttrs, int key, Object srcValue, IExpandedAttrSaver saver)
    {
        Object newValue;

        if (srcValue instanceof IComplexAttr)
        {
            IComplexAttr srcComplex = (IComplexAttr)srcValue;

            // If the attribute is inherited, in this context it basically means "missing".
            if (srcComplex.isInheritedComplexAttr())
                return;

            if (srcComplex instanceof IExpandableAttr)
            {
                // The attribute has to be merged with the parent value.
                IExpandableAttr parentExpandable = (IExpandableAttr)dstAttrs.get(key);
                newValue = ((IExpandableAttr)srcComplex).expand(parentExpandable);
            }
            else if (srcComplex instanceof ComplexFontName)
            {
                // WORDSJAVA-1127 Aspose.Words for Java consumes too much memory during PDF conversion.
                // ComplexFontName is immutable object so we don't need to clone it.
                newValue = srcComplex;
            }
            else
            {
                // The attribute just has to be cloned.
                newValue = srcComplex.deepCloneComplexAttr();
            }
        }
        else if (srcValue instanceof AttrBoolEx)
        {
            AttrBoolEx srcBoolExValue = (AttrBoolEx)srcValue;
            newValue = srcBoolExValue.resolveFetchAttr(dstAttrs, key);
        }
        else
        {
            // All other attributes - we treat as immutable and don't create a deep clone.
            newValue = srcValue;
        }

        if (saver != null)
            saver.save(dstAttrs, key, newValue);
        else
            dstAttrs.set(key, newValue);
    }

    /// <summary>
    /// Expands formatting for the specified attribute keys.
    /// </summary>
    /// <param name="dstAttrs"></param>
    /// <param name="keys"></param>
    void expandToInclusive(AttrCollection dstAttrs, /*params*/ int... keys)
    {
        if (dstAttrs == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "dstAttrs");

        if (keys == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "keys");

        for (int key : keys)
        {
            Object srcValue = this.get(key);
            if (srcValue != null)
                expandTo(dstAttrs, key, srcValue, null);
        }
    }

    /// <summary>
    /// Performs inverse of expanding attributes.
    /// Updates this collection by comparing attributes in this collection with the specified base.
    ///
    /// BaseAttrs is the BeforeChanges attributes.
    /// This is the AfterChanges attributes.
    /// This method performs PositiveDifference = AfterChanges - BeforeChanges.
    /// This becomes PositiveDifference.
    /// </summary>
    void collapse(AttrCollection baseAttrs)
    {
        collapse(baseAttrs, -1);
    }

    /// <summary>
    /// Performs inverse of expanding attributes.
    /// See comments above.
    /// This method is used in RTF reader where seems to be a need to preserve the istd attribute
    /// during collapse. I can't remember exactly why, will figure out later.
    /// </summary>
    /// <param name="baseAttrs">BeforeChanges collection.</param>
    /// <param name="keysToIgnore">Ids of the attributes to ignore during collapsing.</param>
    void collapse(AttrCollection baseAttrs, /*params*/ int... keysToIgnore)
    {
        if (baseAttrs == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "baseAttrs");

        for (int baseIndex = 0; baseIndex < baseAttrs.getCount(); baseIndex++)
        {
            int key = baseAttrs.getKey(baseIndex);

            if (ArrayUtil.contains(keysToIgnore, key) || isIgnoredOnCollapse(key))
                continue;

            int childIndex = indexOfKey(key);
            if (childIndex >= 0)
            {
                // The value exists both in the base collection and in the child collection.
                // We must compare the values to decide what to do.
                Object baseValue = baseAttrs.getByIndex(baseIndex);
                Object childValue = getByIndex(childIndex);
                if (areSameValues(childValue, baseValue))
                {
                    // The value is same in the base collection and in the child collection.
                    // This is a result of expanding, we reverse it by removing the child attribute.
                    removeAt(childIndex);
                }
                else
                {
                    // The value is different in the base collection and in the child collection.
                    // It means a value is directly specified in the child collection.
                    // For an expandable attribute (such as tab stops), need to collapse it.
                    // For other attributes simply do nothing and the child value will be kept.
                    collapseExpandableAttrIfNeeded(childValue, baseValue);
                }
            }
            else
            {
                // The value exists in the base collection, but not in the child collection.
                // We must add a default value to the child collection.

                // This gets a default value.
                Object childValue = fetchInheritedAttr(key);

                // If we have a complex attribute, we must clone.
                if (childValue instanceof IComplexAttr)
                {
                    // WORDSJAVA-1127 Aspose.Words for Java consumes too much memory during PDF conversion.
                    // ComplexFontName is immutable object so we don't need to clone it.
                    if (!(childValue instanceof ComplexFontName))
                    {
                        childValue = ((IComplexAttr)childValue).deepCloneComplexAttr();
                    }

                    // If it is a tab stop, it needs to be collapsed relative to the base tab stops.
                    Object baseValue = baseAttrs.getByIndex(baseIndex);
                    collapseExpandableAttrIfNeeded(childValue, baseValue);
                }

                this.set(key, childValue);
            }
        }
    }

    /// <summary>
    /// Removes values from specified collection which AW ignores while collapsing.
    /// </summary>
    void removeIgnorableOnCollapseValues()
    {
        for (int key : getKeys())
        {
            if (isIgnoredOnCollapse(key))
                remove(key);
        }
    }

    /// <summary>
    /// Check given attribute to be excluded from collapse process.
    /// </summary>
    private static boolean isIgnoredOnCollapse(int key)
    {
        switch (key)
        {
            // Revision keys
            case RevisionAttr.FORMAT_REVISION:
            case RevisionAttr.INSERT_REVISION:
            case RevisionAttr.DELETE_REVISION:
            case RevisionAttr.MOVE_FROM_REVISION:
            case RevisionAttr.MOVE_TO_REVISION:
            case RevisionAttr.NUMBER_REVISION:

            // Rsids keys
            case FontAttr.RSID_R_PR:
            case FontAttr.RSID_R:
            case ParaAttr.RSID_P:

            // Internal keys
            case TableAttr.SYS_CALCULATED_TABLE_GRID:
            case TableAttr.SYS_TABLE_GRID:
            case TableAttr.SYS_TABLE_GRID_FOR_NEW_ALGORITHM:
            case FontAttr.SYS_SYMBOL:

            case ParaAttr.SYS_ALIGNMENT_97:
            case ParaAttr.SYS_FIRST_LINE_INDENT_97:
            case ParaAttr.SYS_LEFT_INDENT_97:
            case ParaAttr.SYS_RIGHT_INDENT_97:
            // WORDSNET-18265 Added attribute.
            case WordAttrCollection.SYS_DIRECT_ATTRS_BACKUP:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Compares this collection with another.
    /// </summary>
    boolean equals(AttrCollection coll, int[] keysToIgnore)
    {
        return equals(this, coll, keysToIgnore);
    }

    /// <summary>
    /// Checks whether this collection contains any non-inherited attribute except for the given subset.
    /// </summary>
    boolean containsNonInheritedAttrsNotInList(int[] keysToIgnore)
    {
        for (int pos = 0; pos < getCount(); ++pos)
        {
            int key = getKey(pos);
            if (ArrayUtil.binarySearch(keysToIgnore, 0, keysToIgnore.length, key) < 0)
            {
                Object attr = getByIndex(pos);
                if (!isInheritedComplexAttribute(attr))
                    return true;
            }
        }
        return false;
    }

    /// <summary>
    /// Compares the two collections.
    /// </summary>
    static boolean equals(AttrCollection coll1, AttrCollection coll2, int[] keysToIgnore)
    {
        // Since this is sorted collection we can skip ignored keys and compare very next keys and values.
        // In the loop:
        //   Find first non-ignored attribute in each of two collections.
        //   If both are found then compare values.
        //   If both are not found that's okay - no difference found.
        //   If one of collections has an attribute but other has not, check if it is a complex attribute that
        //   has inherited value, otherwise consider collections different.

        int pos1 = 0;
        int pos2 = 0;
        int key1 = 0;
        int key2 = 0;
        boolean result = true;

        do
        {
            RefInt referenceToPos1 = new RefInt(pos1);
            RefInt referenceToKey1 = new RefInt(key1);
            boolean found1 = findNonIgnoredKey(coll1, /*ref*/ referenceToPos1, /*ref*/ referenceToKey1, keysToIgnore);
            pos1 = referenceToPos1.get();
            key1 = referenceToKey1.get();
            RefInt referenceToPos2 = new RefInt(pos2);
            RefInt referenceToKey2 = new RefInt(key2);
            boolean found2 = findNonIgnoredKey(coll2, /*ref*/ referenceToPos2, /*ref*/ referenceToKey2, keysToIgnore);
            pos2 = referenceToPos2.get();
            key2 = referenceToKey2.get();

            if (!found1 && !found2)
                break;

            if (found1 && found2 && (key1 != key2))
            {
                // Process key that is present in one of the collections but none in the other.
                if (coll2.containsKey(key1))
                    found1 = false;
                else
                    found2 = false;
            }

            if (found1 && found2)
                result = coll1.getByIndex(pos1++).equals(coll2.getByIndex(pos2++));
            else if (found1)
                result = isInheritedComplexAttribute(coll1.getByIndex(pos1++));
            else
                result = isInheritedComplexAttribute(coll2.getByIndex(pos2++));
        }
        while (result);

        return result;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified value is a complex attribute that has inherited value.
    /// </summary>
    private static boolean isInheritedComplexAttribute(Object value1)
    {
        return (value1 instanceof IComplexAttr) && ((IComplexAttr)value1).isInheritedComplexAttr();
    }

    /// <summary>
    /// Removes attributes which are equal to global defaults.
    /// </summary>
    void removeGlobalDefaults()
    {
        AttrCollection globalDefaults = getDefaults();
        removeEquals(globalDefaults);
    }

    /// <summary>
    /// Removes attributes, which are the same as in the specified other attribute collection.
    /// </summary>
    void removeEquals(AttrCollection otherAttrs)
    {
        for (int i = 0; i < otherAttrs.getCount(); i++)
        {
            int key = otherAttrs.getKey(i);

            int index = indexOfKey(key);
            if (index >= 0)
            {
                // Values exist both in this and in the other collection.
                // If they are equal, we remove it from this collection.
                Object otherValue = otherAttrs.getByIndex(i);
                Object value = getByIndex(index);
                // Some defaults can be null, for example ShapeAttr.ImageBytes.
                // Maybe there will be special check for that, but for now just skip nulls.
                if (otherValue != null && otherValue.equals(value))
                    removeAt(index);
            }
        }
    }

    /// <summary>
    /// Returns a copy of the array of collection keys.
    /// </summary>
    int[] getKeys()
    {
        int[] keys = new int[getCount()];
        for (int i = 0; i < getCount(); i++)
            keys[i] = getKey(i);

        return keys;
    }

    /// <summary>
    /// Finds first non-ignored key starting from pos in collection coll.
    /// If anything is found then returns true and sets pos to that key.
    /// </summary>
    private static boolean findNonIgnoredKey(AttrCollection coll, /*ref*/RefInt pos, /*ref*/RefInt key, int[] keysToIgnore)
    {
        int collCount = coll.getCount();
        int ignoreCount = (keysToIgnore != null) ? keysToIgnore.length : 0;
        while (pos.get() < collCount)
        {
            key.set(coll.getKey(pos.get()));
            if ((keysToIgnore == null) || (ArrayUtil.binarySearch(keysToIgnore, 0, ignoreCount, key.get()) < 0))
                return true;

            pos.set(pos.get() + 1)/*++Indexer*/;
        }
        return false;
    }

    /// <summary>
    /// Returns <b>true</b> if the specified attribute values are the same.
    /// </summary>
    private static boolean areSameValues(Object childValue, Object baseValue)
    {
        return (childValue instanceof ICustomEquality)
            ? ((ICustomEquality)childValue).hasSameValue((ICustomEquality)baseValue)
            : baseValue.equals(childValue);
    }

    private static void collapseExpandableAttrIfNeeded(Object childValue, Object baseValue)
    {
        if (childValue instanceof IExpandableAttr)
        {
            // Reverse merge the tab stop collection.
            ((IExpandableAttr)childValue).collapse((IExpandableAttr)baseValue);
        }
    }

    /// <summary>
    /// Calculates hash code for collection.
    /// </summary>
    /// <remarks>
    /// AM. I think that attribute collections within one document which have equal set of keys are equal with high probability.
    /// That's why I don't take attribute values into calculation.
    /// </remarks>
    /// <returns></returns>
    public /*override*/ int hashCode()
    {
        int hashCode = 0;
        for (int i = 0; i < getCount(); i++)
        {
            int key = getKey(i);

            hashCode += key;
            hashCode += (hashCode << 10);
            hashCode ^= (hashCode >> 6);
        }

        return hashCode;
    }

    /// <summary>
    /// Calculates hash code for this collection. Allows to specify keys to ignore during calculation.
    /// </summary>
    int hashCode(int[] keysToIgnore)
    {
        int hashCode = 0;

        for (int i = 0; i < getCount(); i++)
        {
            int key = getKey(i);
            if (ArrayUtil.binarySearch(keysToIgnore, 0, keysToIgnore.length, key) < 0)
            {
                hashCode = (hashCode * 397) ^ (/*int.hashCode()*/key);

                Object attr = getByIndex(i);
                if ((attr != null) && !isInheritedComplexAttribute(attr))
                    hashCode = (hashCode * 397) ^ attr.hashCode();
            }
        }

        return hashCode;
    }

    public boolean equals(AttrCollection rhs)
    {
        // Generated by ReSharper.
        if (msObject.referenceEquals(null, rhs))
            return false;
        if (msObject.referenceEquals(this, rhs))
            return true;

        return equals(rhs, null);
    }

    /// <summary>
    /// Determines whether the specified object is equal in value to the current object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        // Generated by ReSharper.
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        return equals(ms.as(obj, AttrCollection.class));
    }

    /// <summary>
    /// Checks if the option that is specified by its key is not defined or has default value.
    /// </summary>
    boolean isDefaultValue(int key)
    {
        Object value = getDirectAttr(key);
        if (value == null)
            return true;

        Object defaultValue = fetchInheritedAttr(key);
        if (defaultValue == null)
            return false;

        return value.equals(defaultValue);
    }

    /// <summary>
    /// Reference to pooled collection which holds actual attribute values.
    /// </summary>
    InternPoolItem getPoolItem() { return mPoolItem; }
    void setPoolItem(InternPoolItem value)
    {
        Debug.assertTrue(value == null || mPoolItem == null);
        mPoolItem = value;
    }

    private InternPoolItem mPoolItem;

}

