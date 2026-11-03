// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/04/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


/// <summary>
/// Represents a table of authorities categories.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class ToaCategories
{

    public ToaCategories()
    {
        mCategoryNames.set(1, "Cases");
        mCategoryNames.set(2, "Statutes");
        mCategoryNames.set(3, "Other Authorities");
        mCategoryNames.set(4, "Rules");
        mCategoryNames.set(5, "Treatises");
        mCategoryNames.set(6, "Regulations");
        mCategoryNames.set(7, "Constitutional Provisions");
    }

    /// <summary>
    /// Gets the default table of authorities categories.
    /// </summary>
    /// <remarks>
    /// Use the <see cref="FieldOptions.ToaCategories"/> property to specify table of authorities categories for a single document.
    /// </remarks>
    public static ToaCategories getDefaultCategories() { return mDefaultCategories; };

    private static ToaCategories mDefaultCategories;

    /// <summary>
    /// Gets or sets the category heading by category number.
    /// </summary>
    public String get(int number)
    {
        String categoryName = mCategoryNames.get(number);
        return categoryName != null
            ? categoryName
            : Integer.toString(number);
    }
    public void set(int number, String value) { mCategoryNames.set(number, value); }

    private /*final*/ IntToObjDictionary<String> mCategoryNames = new IntToObjDictionary<String>();
    static/* ToaCategories()*/
    {
        mDefaultCategories = new ToaCategories();
    }
}

