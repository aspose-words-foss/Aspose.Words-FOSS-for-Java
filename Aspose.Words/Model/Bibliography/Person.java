// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents individual (a person) bibliography source contributor.
/// </summary>
public final class Person
{
    /// <summary>
    /// Initialize a new instance of the <see cref="Person"/> class.
    /// </summary>
    /// <param name="last">The last name.</param>
    /// <param name="first">The last name.</param>
    /// <param name="middle">The last name.</param>
    public Person(String last, String first, String middle)
    {
        setLast(last);
        setFirst(first);
        setMiddle(middle);
    }

    /// <summary>
    /// Gets or sets the last name of a person.
    /// </summary>
    public String getLast() { return mLast; }; public void setLast(String value) { mLast = value; };

    private String mLast;

    /// <summary>
    /// Gets or sets the first name of a person.
    /// </summary>
    public String getFirst() { return mFirst; }; public void setFirst(String value) { mFirst = value; };

    private String mFirst;

    /// <summary>
    /// Gets or sets the middle name of a person.
    /// </summary>
    public String getMiddle() { return mMiddle; }; public void setMiddle(String value) { mMiddle = value; };

    private String mMiddle;

    Person deepClone()
    {
        return new Person(getLast(), getFirst(), getMiddle());
    }

    // moved from PersonEqualityComparer for Java porting
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        Person person = ms.as(obj, Person.class);
        return (person != null) && equals(person);
    }

    private boolean equals(Person other)
    {
        return (msString.equals(getLast(), other.getLast())) &&
               (msString.equals(getFirst(), other.getFirst())) &&
               (msString.equals(getMiddle(), other.getMiddle()));
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hashCode = getLast() != null ? getLast().hashCode() : 0;
            hashCode = (hashCode * 397) ^ (getFirst() != null ? getFirst().hashCode() : 0);
            hashCode = (hashCode * 397) ^ (getMiddle() != null ? getMiddle().hashCode() : 0);
            return hashCode;
        }
    }
}

