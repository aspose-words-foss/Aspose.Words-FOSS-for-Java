// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// A collection of <see cref="PersonInternal"/> objects that represent contact information for authors of comments and
/// revisions in a document.
/// </summary>
class PersonCollectionInternal implements Iterable<PersonInternal>, Cloneable
{
    public Iterator<PersonInternal> iterator()
    {
        return mPersons.iterator();
    }

    /// <summary>
    /// Gets the enumerator object that will enumerate persons that are stored in the collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Makes a deep copy of the person collection. Suitable when copying a complete document.
    /// </summary>
    PersonCollectionInternal deepClone()
    {
        PersonCollectionInternal lhs = (PersonCollectionInternal)memberwiseClone();

        lhs.mPersons = new ArrayList<PersonInternal>();
        for (PersonInternal person : this)
            lhs.add(person.deepClone());

        return lhs;
    }

    /// <summary>
    /// Adds the specified person into the collection.
    /// </summary>
    void add(PersonInternal person)
    {
        msArrayList.add(mPersons, person);
    }

    /// <summary>
    /// Creates a person with the specified values of its properties and adds it into the collection.
    /// </summary>
    PersonInternal add(String author, /*ContactIdentityProvider*/int provider, String userId)
    {
        PersonInternal person = new PersonInternal();
        person.setAuthor(author);
        person.setIdentityProvider(provider);
        person.setUserId(userId);

        add(person);

        return person;
    }

    /// <summary>
    /// Gets a person by index.
    /// </summary>
    PersonInternal get(int index) { return mPersons.get(index); }

    /// <summary>
    /// Gets the number of items in the collection.
    /// </summary>
    public int getCount() { return mPersons.size(); }

    private ArrayList<PersonInternal> mPersons = new ArrayList<PersonInternal>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

