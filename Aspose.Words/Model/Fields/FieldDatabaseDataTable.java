// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Provides data for the <see cref="FieldDatabase"/> field result.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldDatabaseDataTable
{
    /// <summary>
    /// Initializes a new instance of the <see cref="FieldDatabaseDataTable"/> class.
    /// </summary>
    public FieldDatabaseDataTable(/*params*/ String... columnNames)
    {
        mColumnNames = columnNames;
        mRows = new ArrayList<FieldDatabaseDataRow>();
    }

    /// <summary>
    /// Gets columns that belong to this table.
    /// </summary>
    public String[] getColumnNames() { return mColumnNames; };

    private  String[] mColumnNames;

    /// <summary>
    /// Gets rows that belong to this table.
    /// </summary>
    public ArrayList<FieldDatabaseDataRow> getRows() { return mRows; };

    private ArrayList<FieldDatabaseDataRow> mRows;
}

