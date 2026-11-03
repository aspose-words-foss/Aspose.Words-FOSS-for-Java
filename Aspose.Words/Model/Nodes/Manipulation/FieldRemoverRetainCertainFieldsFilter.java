// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2019 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;


class FieldRemoverRetainCertainFieldsFilter implements IFieldRemoverFilter
{
    FieldRemoverRetainCertainFieldsFilter(/*params*/ /*FieldType*/int... fieldTypes)
    {
        mFieldTypes = new HashSetGeneric<Integer>();
        for (/*FieldType*/int fieldType : fieldTypes)
            mFieldTypes.add(fieldType);
    }

    public boolean /*IFieldRemoverFilter.*/needReplaceFieldWithResult(FieldChar fieldChar)
    {
        return !mFieldTypes.contains(fieldChar.getFieldType());
    }

    private /*final*/ HashSetGeneric<Integer> mFieldTypes;
}

