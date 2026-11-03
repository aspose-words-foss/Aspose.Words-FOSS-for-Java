// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2022 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Provides utility methods for the <see cref="FieldDatabase"/> field.
/// </summary>
/*static*/ class FieldDatabaseUtils
{
	/* Simulation of static class by using private constructor */
	private FieldDatabaseUtils()
	{}

    static FieldDatabaseDataTable trimRecords(FieldDatabaseDataTable dataTable, int firstRecord, int lastRecord)
    {
        ResultDataTableBuilder builder = new ResultDataTableBuilder(dataTable);

        if (firstRecord == Integer.MIN_VALUE && lastRecord == Integer.MIN_VALUE)
            builder.appendAll();
        else if (firstRecord == Integer.MIN_VALUE)
            trimEnd(builder, lastRecord);
        else if (lastRecord == Integer.MIN_VALUE)
            trimStart(builder, firstRecord);
        else
            trimStartEnd(builder, firstRecord, lastRecord);

        return builder.build();
    }

    private static void trimStartEnd(ResultDataTableBuilder builder, int firstRecord, int lastRecord)
    {
        Debug.assertTrue(firstRecord != Integer.MIN_VALUE);
        Debug.assertTrue(lastRecord != Integer.MIN_VALUE);

        if (firstRecord > 0 && lastRecord > 0)
        {
            if (firstRecord < lastRecord)
                builder.appendRange(firstRecord, lastRecord);
            else // firstRecord >= lastRecord
                builder.appendRange(lastRecord, firstRecord);
        }
        else if (firstRecord == 0)
        {
            // empty
        }
        else if (lastRecord == 0)
        {
            if (firstRecord > 0)
            {
                builder.appendLast();
                builder.appendTo(firstRecord);
            }
            else if (firstRecord == -1)
            {
                builder.appendFirst();
            }
            else // firstRecord < -1
            {
                builder.appendLast();
                builder.appendAll();
            }
        }
        else if (firstRecord > 0)
        {
            builder.appendFrom(firstRecord);
        }
        else if (lastRecord > 0)
        {
            if (firstRecord == -1)
                builder.appendTo(lastRecord);
            else // firstRecord < -1
                builder.appendFrom(lastRecord);
        }
        else // firstRecord < 0 && lastRecord < 0
        {
            trimNegativeStartEnd(builder, firstRecord, lastRecord);
        }
    }

    private static void trimNegativeStartEnd(ResultDataTableBuilder builder, int firstRecord, int lastRecord)
    {
        Debug.assertTrue(firstRecord < 0);
        Debug.assertTrue(lastRecord < 0);

        firstRecord = -firstRecord;
        lastRecord = -lastRecord;

        if (firstRecord == 1)
        {
            builder.appendAll();
        }
        else if (firstRecord < BOUND && lastRecord == 1)
        {
            int count = firstRecord + 1;
            for (int i = 0; i < count; i++)
            {
                int index = builder.getSourceCount() - count + 1 + i;
                if (index < 1)
                    index = 1;
                builder.append(index);
            }

            builder.appendAll();
        }
        else if (firstRecord < BOUND && lastRecord < BOUND)
        {
            trimStartEndNegativeRange(builder, firstRecord, lastRecord);
        }
        else if (firstRecord <= BOUND && lastRecord <= BOUND)
        {
            builder.appendLast();
        }
        else if (firstRecord > BOUND && lastRecord > BOUND)
        {
            trimStartEndNegativeRange(builder, firstRecord, lastRecord);
        }
        else if (firstRecord > BOUND)
        {
            trimBound(builder, firstRecord);
        }
        else // lastRecord > bound
        {
            trimBound(builder, lastRecord);
        }
    }

    private static void trimStartEndNegativeRange(ResultDataTableBuilder builder, int firstRecord, int lastRecord)
    {
        int from;
        int to;
        if (firstRecord < lastRecord)
        {
            from = builder.getSourceCount() - lastRecord;
            to = builder.getSourceCount() - firstRecord;
        }
        else
        {
            from = builder.getSourceCount() - firstRecord;
            to = builder.getSourceCount() - lastRecord;
        }

        int count = to - from + 1;

        for (int i = 0; i < count; i++)
        {
            int index = from + i;
            if (index < 1)
                index = 1;
            builder.append(index);
        }
    }

    private static void trimStart(ResultDataTableBuilder builder, int firstRecord)
    {
        Debug.assertTrue(firstRecord != Integer.MIN_VALUE);
        if (firstRecord == -1)
        {
            builder.appendAll();
        }
        else if (firstRecord < -1)
        {
            trimBound(builder, -firstRecord);
        }
        else if (firstRecord > 0)
        {
            builder.appendFrom(firstRecord);
        }
        else // firstRecord == 0
        {
            // empty
        }
    }

    private static void trimEnd(ResultDataTableBuilder builder, int lastRecord)
    {
        Debug.assertTrue(lastRecord != Integer.MIN_VALUE);

        if (lastRecord < 0)
        {
            builder.appendAll();
        }
        else if (lastRecord == 0)
        {
            builder.appendLast();
            builder.appendFirst();
        }
        else // lastRecord > 0
        {
            builder.appendRange(1, lastRecord);
        }
    }

    private static void trimBound(ResultDataTableBuilder builder, int number)
    {
        int count = number - BOUND;
        for (int i = 0; i < count; i++)
        {
            int index = builder.getSourceCount() - number + i;
            if (index < 1)
                index = 1;
            builder.append(index);
        }

        builder.appendLast();
    }

    private static final int BOUND = 16;

    private static class ResultDataTableBuilder
    {
        ResultDataTableBuilder(FieldDatabaseDataTable source)
        {
            Debug.assertTrue(source.getRows().size() > 0);
            mSource = source;
            mRows = new ArrayList<Integer>(mSource.getRows().size());
        }

        int getSourceCount() { return mSource.getRows().size(); }

        void appendAll()
        {
            appendRange(1, mSource.getRows().size());
        }

        // 1-based
        void appendRange(int from, int to)
        {
            for (int i = from; i <= to; i++)
                append(i);
        }

        // 1-based
        void appendFrom(int from)
        {
            appendRange(from, mSource.getRows().size());
        }

        // 1-based
        void appendTo(int to)
        {
            appendRange(1, to);
        }

        // 1-based
        void append(int index)
        {
            if(index >= 1 && index <= mSource.getRows().size())
                msArrayList.add(mRows, index - 1);
        }

        void appendFirst()
        {
            append(1);
        }

        void appendLast()
        {
            append(mSource.getRows().size());
        }

        FieldDatabaseDataTable build()
        {
            if (mRows.size() == 0)
                return new FieldDatabaseDataTable();

            if (returnSource())
                return mSource;

            FieldDatabaseDataTable clone = new FieldDatabaseDataTable(mSource.getColumnNames());

            for (int row : mRows)
                clone.getRows().add(mSource.getRows().get(row));

            return clone;
        }

        private boolean returnSource()
        {
            if (mRows.size() != mSource.getRows().size())
                return false;

            for (int i = 0; i < mRows.size(); i++)
                if (mRows.get(i) != i)
                    return false;

            return true;
        }

        private /*final*/ ArrayList<Integer> mRows;
        private /*final*/ FieldDatabaseDataTable mSource;
    }
}

