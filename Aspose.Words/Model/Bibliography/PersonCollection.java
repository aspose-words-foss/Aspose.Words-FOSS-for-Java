// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Represents a list of persons who are bibliography source contributors.
/// </summary>
public final class PersonCollection extends Contributor implements Iterable<Person>
{
    /// <summary>
    /// Initialize a new instance of the <see cref="PersonCollection"/> class.
    /// </summary>
    public PersonCollection()
    {

    }

    /// <summary>
    /// Initialize a new instance of the <see cref="PersonCollection"/> class.
    /// </summary>
    public PersonCollection(Iterable<Person> persons)
    {
        for (Person person : persons)
            add(person);
    }

    /// <summary>
    /// Initialize a new instance of the <see cref="PersonCollection"/> class.
    /// </summary>
    public PersonCollection(/*params*/ Person... persons)
    {
        for (Person person : persons)
            add(person);
    }

    /// <summary>
    /// Adds a <see cref="Person"/> to the collection.
    /// </summary>
    /// <param name="person">The person to add to the collection.</param>
    public void add(Person person)
    {
        mPersons.add(person);
    }

    /// <summary>
    /// Removes the person from the collection.
    /// </summary>
    /// <param name="person">The person to remove from the collection.</param>
    public boolean remove(Person person)
    {
        return mPersons.remove(person);
    }

    /// <summary>
    /// Removes the person at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the person to remove.</param>
    public void removeAt(int index)
    {
        mPersons.remove(index);
    }

    /// <summary>
    /// Removes all items from the collection.
    /// </summary>
    public void clear()
    {
        mPersons.clear();
    }

    /// <summary>
    /// Determines whether the collection contains a specific person.
    /// </summary>
    /// <param name="person">The person to locate in the collection.</param>
    public boolean contains(Person person)
    {
        return mPersons.contains(person);
    }

    /// <summary>
    /// Gets the number of persons contained in the collection.
    /// </summary>
    public int getCount() { return mPersons.size(); }

    /// <summary>
    /// Gets or sets a person at the specified index.
    /// </summary>
    /// <param name="index">An index into the collection.</param>
    public Person get(int index) { return mPersons.get(index); }
    public void set(int index, Person value) { mPersons.set(index, value); }

    /*override*/ Contributor deepClone()
    {
        PersonCollection lhs = new PersonCollection();

        for (Person person : mPersons)
            lhs.add(person.deepClone());

        return lhs;
    }

    @ReservedForInternalUse @Deprecated
    public Iterator<Person> /*IEnumerable<Person>.*/iterator()
    {
        return mPersons.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ ArrayList<Person> mPersons = new ArrayList<Person>();
}

