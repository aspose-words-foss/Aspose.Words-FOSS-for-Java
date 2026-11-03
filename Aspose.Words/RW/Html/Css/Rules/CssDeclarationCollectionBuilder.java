// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/01/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a mutable collection of <see cref="CssDeclaration">CSS declarations</see>.
/// </summary>
/// <remarks>
/// This collection is mutable and declarations stored in it can be modified. If this behavior is undesirable,
/// the immutable <see cref="CssDeclarationCollection"/> should be used instead. The usual strategy is to use a mutable
/// collection during resolution of CSS declarations and to convert it into an immutable collection
/// as soon as all declarations are resolved.
/// This class does not preserve the order in which declarations are added.
/// </remarks>
class CssDeclarationCollectionBuilder implements Iterable<CssDeclaration>
{
    /// <summary>
    /// Creates a new empty collection.
    /// </summary>
    CssDeclarationCollectionBuilder()
    {
        mDeclarations = new CssDeclarationHashtable();
    }

    /// <summary>
    /// Creates a new collection containing the specified declarations.
    /// </summary>
    /// <param name="declarations">
    /// Zero or more instances of <see cref="CssDeclaration"/> that should be added to the collection.
    /// </param>
    CssDeclarationCollectionBuilder(CssDeclarationCollection declarations)
    {
        // Reuse the backing collection until we need to modify it. This optimizes memory usage in scenarios where
        // a builder is created but is not modified. For example, we can create a builder to collapse CSS declarations
        // into shorthand verions and then realize that no declarations can be collapsed.
        mDeclarations = declarations.getHashtable();
    }

    public Iterator<CssDeclaration> iterator()
    {
        return mDeclarations.iterator();
    }

    /// <summary>
    /// Returns an enumerator object that iterates through <see cref="CssDeclaration"/> instances stored in this collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds the specified declaration to the collection.
    /// </summary>
    /// <param name="declaration">CSS declaration.</param> 
    void add(CssDeclaration declaration)
    {
        ensureDeclarationsAreWritable();
        mDeclarations.add(declaration);
    }

    /// <summary>
    /// Adds the specified declarations to the collection.
    /// </summary>
    /// <param name="declarations">CSS declarations (instances of <see cref="CssDeclaration"/>).</param> 
    void add(Iterable<CssDeclaration> declarations)
    {
        ensureDeclarationsAreWritable();
        mDeclarations.add(declarations);
    }

    /// <summary>
    /// Adds the specified declaration to the collection if the declaration is not <c>null</c>.
    /// </summary>
    /// <param name="declaration">CSS declaration.</param> 
    void addIfNotNull(CssDeclaration declaration)
    {
        if (declaration != null)
        {
            ensureDeclarationsAreWritable();
            mDeclarations.add(declaration);
        }
    }

    /// <summary>
    /// Adds the specified declaration to the collection if the declaration is not <c>null</c> and does not exist in
    /// the collection.
    /// </summary>
    /// <param name="declaration">CSS declaration.</param> 
    void addIfNotNullAndNotExist(CssDeclaration declaration)
    {
        if ((declaration != null) && (mDeclarations.get(declaration.getProperty()) == null))
        {
            ensureDeclarationsAreWritable();
            mDeclarations.add(declaration);
        }
    }

    /// <summary>
    /// Adds the specified declaration to the collection replacing a declaration with same property name if any exists.
    /// </summary>
    /// <param name="declaration">CSS declaration.</param> 
    void addOrReplace(CssDeclaration declaration)
    {
        Debug.assertTrue(declaration != null);
        ensureDeclarationsAreWritable();
        mDeclarations.set(declaration.getProperty(), declaration);

        // The purpose of this method is to write a brand new declaration into the collection.
        // As the declaration is new, we reset its flags.
        mDeclarations.setFlags(declaration.getProperty(), CssDeclarationFlags.NONE);
    }

    /// <summary>
    /// Adds the specified declarations to the collection replacing existing declarations with same property names.
    /// </summary>
    /// <param name="declarations">CSS declaration collection (instances of <see cref="CssDeclaration"/>).</param>
    void addOrReplace(Iterable<CssDeclaration> declarations)
    {
        ensureDeclarationsAreWritable();
        for (CssDeclaration declaration : declarations)
        {
            Debug.assertTrue(declaration != null);
            mDeclarations.set(declaration.getProperty(), declaration);

            // The purpose of this method is to write a bunch of brand new declarations into the collection.
            // As the declarations are new, we reset their flags.
            mDeclarations.setFlags(declaration.getProperty(), CssDeclarationFlags.NONE);
        }
    }

    /// <summary>
    /// Adds the specified declarations to the collection replacing existing declarations with same property names if new
    /// declarations are more or equally important as old ones. Old declarations that are more important are not replaced.
    /// </summary>
    /// <param name="declarations">CSS declaration collection (instances of <see cref="CssDeclaration"/>).</param>
    void addOrReplaceIfMoreOrEquallyImportant(Iterable<CssDeclaration> declarations)
    {
        ensureDeclarationsAreWritable();
        for (CssDeclaration declaration : declarations)
        {
            Debug.assertTrue(declaration != null);

            boolean newDeclarationIsMoreOrEquallyImportant = declaration.getImportant();
            if (!newDeclarationIsMoreOrEquallyImportant)
            {
                CssDeclaration existingDeclaration = mDeclarations.get(declaration.getProperty());
                newDeclarationIsMoreOrEquallyImportant = (existingDeclaration == null) || (!existingDeclaration.getImportant());
            }

            if (newDeclarationIsMoreOrEquallyImportant)
            {
                mDeclarations.set(declaration.getProperty(), declaration);
            }

            // The purpose of this method is to write a bunch of brand new declarations into the collection.
            // As the declarations are new, we reset their flags.
            mDeclarations.setFlags(declaration.getProperty(), CssDeclarationFlags.NONE);
        }
    }

    /// <summary>
    /// Replaces an existing declaration with a new one. Preserves the declaration flags.
    /// </summary>
    void replace(CssDeclaration declaration)
    {
        Debug.assertTrue(declaration != null);
        ensureDeclarationsAreWritable();
        mDeclarations.set(declaration.getProperty(), declaration);
    }

    /// <summary>
    /// Removes the declaration for the property with the specified name from the collection.
    /// </summary>
    void remove(String property)
    {
        Debug.assertTrue(StringUtil.hasChars(property));
        Debug.assertTrue(msString.equals(property, msString.toLowerInvariant(property)));

        ensureDeclarationsAreWritable();
        mDeclarations.remove(property);
    }

    /// <summary>
    /// Indicates whether a property comes from our User Agent stylesheet.
    /// </summary>
    boolean isUserAgent(String property)
    {
        return mDeclarations.getFlag(property, CssDeclarationFlags.USER_AGENT);
    }

    /// <summary>
    /// Marks a property as coming from our User Agent stylesheet.
    /// </summary>
    void markUserAgent(String property)
    {
        ensureDeclarationsAreWritable();
        mDeclarations.setFlag(property, CssDeclarationFlags.USER_AGENT, true);
    }

    /// <summary>
    /// Gets all flags that are set for a property.
    /// </summary>
    /*CssDeclarationFlags*/int getFlags(String property)
    {
        return mDeclarations.getFlags(property);
    }

    /// <summary>
    /// Sets flags for a property. Doesn't preserve flags that are currently set for the property.
    /// </summary>
    void setFlags(String property, /*CssDeclarationFlags*/int flags)
    {
        ensureDeclarationsAreWritable();
        mDeclarations.setFlags(property, flags);
    }

    /// <summary>
    /// Copy flags for a property from another collection. The property must exist in this collection (in the target).
    /// </summary>
    void copyFlags(String property, CssDeclarationCollection source)
    {
        Debug.assertTrue(source != null);
        ensureDeclarationsAreWritable();
        mDeclarations.setFlags(property, source.getFlags(property));
    }

    /// <summary>
    /// Creates an immutable copy of this collection.
    /// </summary>
    CssDeclarationCollection getDeclarations()
    {
        if (getCount() != 0)
        {
            // The backing hashtable can be in two modes: shared (when the builder itself and one or more
            // immutable collections work with the hashtable) and unshared (when only the builder itself works
            // with the hashtable). A builder can only modify an unshared hashtable and a hashtable is marked readonly
            // before sharing. If any further modifications are required to an already shared hashtable, it must be cloned. 
            // Initially, a hashtable is unshared, and it becomes shared after a call to GetDeclarations. 
            // Subsequent calls to GetDeclarations use the same shared hashtable. However, any call to a modifying method
            // of CssDeclarationCollectionBuilder (for example, CssDeclarationCollectionBuilder.Add) replaces the shared
            // hashtable inside CssDeclarationCollectionBuilder with its unshared copy that can be modified.
            // This behaviour optimizes performance (~1-2% by tests) and memory usage.
            mDeclarations.makeReadOnly();
            return new CssDeclarationCollection(mDeclarations);
        }

        return CssDeclarationCollection.EMPTY;
    }

    /// <summary>
    /// Ensures the backing collection can be modified (is not readonly). If the collection is readonly, it is replaced
    /// with a writable copy.
    /// </summary>
    private void ensureDeclarationsAreWritable()
    {
        if (mDeclarations.isReadOnly())
        {
            mDeclarations = mDeclarations.getWritableCopy();
        }
    }

    /// <summary>
    /// Returns the number of declarations in the collection.
    /// </summary>
    int getCount() { return mDeclarations.getCount(); }

    /// <summary>
    /// Gets a CSS declaration for the specified property.
    /// </summary>
    /// <param name="property">CSS property name.</param>
    /// <returns>CSS declaration, if found; otherwise, null.</returns>
    CssDeclaration get(String property) { return mDeclarations.get(property); }


    /// <summary>
    /// The backing collection that stores all declarations. The collection can be either read-only or writable.
    /// </summary>
    private CssDeclarationHashtable mDeclarations;
}

