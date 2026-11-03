// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2016 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;
import com.aspose.StringToIntDictionary;
import com.aspose.Debug;
import java.util.Iterator;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// A hashtable that maps CSS property names to corresponding declarations. Each declaration can be marked with flags,
/// which are useful, for example to indicate the source of the declaration.
/// </summary>
/// <remarks>
/// CSS property should be lower-case.
/// After all modifications are done, the hashtable can be marked as readonly in order to make it suitable for usage in
/// immutable classes.
/// </remarks>
class CssDeclarationHashtable implements Iterable<CssDeclaration>
{
    CssDeclarationHashtable()
    {
    	this(new StringToObjDictionary<CssDeclaration>(), null);
	
        // Empty constructor.
    }

    private CssDeclarationHashtable(StringToObjDictionary<CssDeclaration> declarationStorage,
        StringToIntDictionary flagsStorage)
    {
        Debug.assertTrue(declarationStorage != null);
        mDeclarationStorage = declarationStorage;
        mFlags = flagsStorage;
    }

    public Iterator<CssDeclaration> iterator()
    {
        return mDeclarationStorage.getValues().iterator();
    }

    /// <summary>
    /// Gets an enumerator that iterates through instances of <see cref="CssDeclaration"/> stored in this collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Creates a copy of this hashtable. The copy is guaranteed to be writable.
    /// </summary>
    /// <returns>A copy of this hashtable.</returns>
    CssDeclarationHashtable getWritableCopy()
    {
        // SPEED. Don't create a flags storage if the collection doesn't use them.
        StringToIntDictionary flagsCopy = ((mFlags != null) && (mFlags.getCount() > 0))
            ? new StringToIntDictionary(mFlags)
            : null;
        return new CssDeclarationHashtable(new StringToObjDictionary<CssDeclaration>(mDeclarationStorage), flagsCopy);
    }

    /// <summary>
    /// Adds a declaration to the hasthable.
    /// </summary>
    /// <param name="declaration">A declaration to be added.</param>
    void add(CssDeclaration declaration)
    {
        Debug.assertTrue(declaration != null);
        ensureHashtableIsWritable();
        addUnsafe(declaration);
    }

    /// <summary>
    /// Adds zero or more declarations to the hasthable.
    /// </summary>
    /// <param name="declarations">Declarations to be added. Instances of <see cref="CssDeclaration"/>.</param>
    void add(Iterable<CssDeclaration> declarations)
    {
        Debug.assertTrue(declarations != null);
        ensureHashtableIsWritable();
        for (CssDeclaration declaration : declarations)
        {
            Debug.assertTrue(declaration != null);
            addUnsafe(declaration);
        }
    }

    /// <summary>
    /// Adds zero or more declarations to the hasthable.
    /// </summary>
    /// <param name="declarations">Declarations to be added. Instances of <see cref="CssDeclaration"/>.</param>
    void add(CssDeclaration[] declarations)
    {
        Debug.assertTrue(declarations != null);
        ensureHashtableIsWritable();
        for (CssDeclaration declaration : declarations)
        {
            Debug.assertTrue(declaration != null);
            addUnsafe(declaration);
        }
    }

    /// <summary>
    /// Removes a declaration for the specified CSS property from the hashtable.
    /// </summary>
    /// <param name="property">A lower-case CSS property name.</param>
    void remove(String property)
    {
        Debug.assertTrue(StringUtil.hasChars(property));
        Debug.assertTrue(msString.equals(property, msString.toLowerInvariant(property)));

        ensureHashtableIsWritable();
        mDeclarationStorage.remove(property);
        if (mFlags != null)
        {
            mFlags.remove(property);
        }
    }

    /// <summary>
    /// Gets the value of a flag for a declaration.
    /// </summary>
    boolean getFlag(String property, /*CssDeclarationFlags*/int flag)
    {
        return (getFlags(property) & flag) != 0;
    }

    /// <summary>
    /// Modifies the value of a flag for a declaration. Preserves values of other flags.
    /// </summary>
    void setFlag(String property, /*CssDeclarationFlags*/int flag, boolean value)
    {
        /*CssDeclarationFlags*/int oldFlags = getFlags(property);
        /*CssDeclarationFlags*/int newFlags = (value)
            ? oldFlags | flag
            : oldFlags & (~flag);
        setFlags(property, newFlags);
    }

    /// <summary>
    /// Gets all flags that are set for a declaration.
    /// </summary>
    /*CssDeclarationFlags*/int getFlags(String property)
    {
        Debug.assertTrue(StringUtil.hasChars(property));
        Debug.assertTrue(msString.equals(property, msString.toLowerInvariant(property)));

        // We don't write empty flags each time we add a property, because there are situations where we don't need flags
        // at all - we just want a simple collection of properties. Flags are only filled when set explicitly
        // so it is a normal case if flags for a property don't exist.
        
        if (mFlags == null)
        {
            return CssDeclarationFlags.NONE;
        }

        return mFlags.containsKey(property) ? (/*CssDeclarationFlags*/int) mFlags.get(property) : CssDeclarationFlags.NONE;
    }

    /// <summary>
    /// Sets flag values for a declaration. Replaces old flags with the specified ones.
    /// </summary>
    void setFlags(String property, /*CssDeclarationFlags*/int flags)
    {
        Debug.assertTrue(StringUtil.hasChars(property));
        Debug.assertTrue(msString.equals(property, msString.toLowerInvariant(property)));

        ensureHashtableIsWritable();

        // SPEED. Don't create the flags storage unless any flags are set.
        if ((flags == CssDeclarationFlags.NONE) && (mFlags == null))
        {
            return;
        }
        
        // Ensure that declarations and flags are in sync.
        Debug.assertTrue(mDeclarationStorage.containsKey(property));

        if (mFlags == null)
        {
            // SPEED. Usually flags are set after all declarations are written. Create a flag storage large enough
            // to store flags for every declaration.
            mFlags = new StringToIntDictionary(mDeclarationStorage.getCount());
        }

        mFlags.set(property, (int) flags);
    }

    /// <summary>
    /// Makes the hashtable readonly. No further addition or removal of declarations will be allowed for this hashtable.
    /// </summary>
    void makeReadOnly()
    {
        mIsReadonly = true;
    }

    /// <summary>
    /// Gets the number of declarations in the hashtable.
    /// </summary>
    int getCount() { return mDeclarationStorage.getCount(); }

    /// <summary>
    /// Indicates whether the hashtable is readonly. If a hashtable is readonly, no declarations can be added to or removed
    /// from it.
    /// </summary>
    boolean isReadOnly() { return mIsReadonly; }

    /// <summary>
    /// Gets a declaration by CSS property name.
    /// </summary>
    /// <param name="property">A lower-case CSS property name.</param>
    /// <returns>A declaration or <c>null</c> if there is no declaration for the specified property name.</returns>
    CssDeclaration get(String property)
    {
        Debug.assertTrue(StringUtil.hasChars(property));
        Debug.assertTrue(msString.equals(property, msString.toLowerInvariant(property)));

        return mDeclarationStorage.get(property);
    }
    void set(String property, CssDeclaration value)
    {
        Debug.assertTrue(msString.equals(value.getProperty(), property));

        ensureHashtableIsWritable();
        mDeclarationStorage.set(value.getProperty(), value);
    }

    private void ensureHashtableIsWritable()
    {
        if (mIsReadonly)
        {
            throw new IllegalStateException();
        }
    }

    private void addUnsafe(CssDeclaration declaration)
    {
        mDeclarationStorage.add(declaration.getProperty(), declaration);
    }

    // Two different synchronous dictionaries of simple values (one for integer flags and another for references
    // to immutable declarations) seem easier to clone than one dictionary of complex objects (mutable classes).
    // Another advantage is that flags can be filled only when needed and they won't occupy memory if not used.
    private /*final*/ StringToObjDictionary<CssDeclaration> mDeclarationStorage;
    /// <summary>
    /// Flags of declarations stored in this collection. <c>null</c> unless any flags are set.
    /// </summary>
    /// <remarks>
    /// The flag is CssDeclarationFlags Enum type. Stored as int value for autoportability to Java.
    /// </remarks>
    private StringToIntDictionary mFlags;

    private boolean mIsReadonly;
}

