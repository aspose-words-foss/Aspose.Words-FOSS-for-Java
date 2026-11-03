// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import java.util.Map;
import java.util.Iterator;


public final class OpcRelationshipCollection implements Iterable<OpcRelationship>, Cloneable
{
    public OpcRelationshipCollection(String partName)
    {
        mPartName = partName;
        createTables();
    }

    /// <summary>
    /// Adds a relationship. Returns the relationship id.
    /// If a relationship to the specified target already exists, returns the existing relationship id.
    /// </summary>
    public String add(String relType, String absoluteTarget, boolean isExternal)
    {
        if (isExternal)
        {
            // RK It looks like MS Word writes full URL with protocol for file paths.
            if (UriUtil.isFilePath(absoluteTarget))
                absoluteTarget = UriUtil.escapeHrefAnyway(UriUtil.addFileSchemePrefix(absoluteTarget));
            else if (UriUtil.hasFileScheme(absoluteTarget))
                absoluteTarget = UriUtil.escapeHrefAnyway(UriUtil.unescapeHref(absoluteTarget));
        }
        else
            absoluteTarget = OpcPackageBase.makeRelative(mPartName, absoluteTarget);

        OpcRelationship rel = (OpcRelationship)mTargetToRelTable.get(getTypedTarget(absoluteTarget, relType));

        // WORDSNET-27535 Write separate relationship for external file links.
        // WORDSNET-27670 Write all external links as separate relationships.
        if ((rel != null) && !isExternal)
        {
            // RK We are returning the existing relationship id and it is a good thing for OOXML files.
            // For example, the main document can refer to the same image multiple times,
            // it is just one relationship.
            return rel.getId();
        }

        String relId = msString.format("rId{0}", (mTargetToRelTable.getCount() + 1));

        add(relId, relType, absoluteTarget, isExternal);
        return relId;
    }

    /// <summary>
    /// This is used to add relationships when loading a package.
    /// For resiliency purposes, if a duplicate id is encountered, it overrides the existing value.
    /// </summary>
    public void add(String relId, String relType, String absoluteTarget, boolean isExternal)
    {
        // WORDSNET-15049 Replace back slash in the target path with slash ("/") for internal package parts.
        if (!isExternal)
            absoluteTarget = absoluteTarget.replace("\\", "/");

        OpcRelationship rel = new OpcRelationship(relId, relType, absoluteTarget, isExternal);
        add(rel);
    }

    /// <summary>
    /// Removes relationship from the collection.
    /// </summary>
    public void remove(String relId)
    {
        OpcRelationship rel = getById(relId);

        mIdToRelTable.remove(rel.getId());
        mTargetToRelTable.remove(rel.getTarget());
    }

    /// <summary>
    /// Gets relationship by its id. Returns null if not found.
    /// </summary>
    public OpcRelationship getById(String id)
    {
        return DictionaryExtension.getValueOrNull(mIdToRelTable, id);
    }

    /// <summary>
    /// Gets a first relationship that matches the specified relationship type.
    /// </summary>
    public OpcRelationship getFirstByType(String type)
    {
        for (Map.Entry<String, OpcRelationship> pair : (Iterable<Map.Entry<String, OpcRelationship>>) mIdToRelTable)
        {
            if (msString.equals(pair.getValue().getType(), type))
                return pair.getValue();
        }
        return null;
    }

    /// <summary>
    /// Clones this instance of relationship collection.
    /// </summary>
    OpcRelationshipCollection deepClone()
    {
        OpcRelationshipCollection lhs = (OpcRelationshipCollection)memberwiseClone();

        lhs.createTables();
        for (OpcRelationship rel : mIdToRelTable.getValues())
            lhs.add(rel.deepClone());

        return lhs;
    }

    public Iterator<OpcRelationship> iterator()
    {
        return mIdToRelTable.getValues().iterator();
    }

    /// <summary>
    /// Creates internal tables to store relationship items.
    /// </summary>
    private void createTables()
    {
        mIdToRelTable = new SortedStringListGeneric<OpcRelationship>(true);
        mTargetToRelTable = new StringListGeneric<OpcRelationship>();
    }

    /// <summary>
    /// Adds relationship object into this collection.
    /// </summary>
    private void add(OpcRelationship rel)
    {
        mIdToRelTable.set(rel.getId(), rel);
        mTargetToRelTable.add(getTypedTarget(rel.getTarget(), rel.getType()), rel);
    }
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public int getCount() { return mIdToRelTable.getCount(); }

    /// <summary>
    /// Gets the name of the part to which these relationships belong to.
    /// </summary>
    public String getPartName() { return mPartName; }

    private static String getTypedTarget(String target, String type)
    {
        return msString.format("{0}{1}", target, type);
    }

    private /*final*/ String mPartName;
    private SortedStringListGeneric<OpcRelationship> mIdToRelTable;
    private StringListGeneric<OpcRelationship> mTargetToRelTable;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

