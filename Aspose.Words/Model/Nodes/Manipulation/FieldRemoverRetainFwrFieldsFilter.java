// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2019 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class FieldRemoverRetainFwrFieldsFilter implements IFieldRemoverFilter
{
    private FieldRemoverRetainFwrFieldsFilter()
    {
    }

    public boolean /*IFieldRemoverFilter.*/needReplaceFieldWithResult(FieldChar fieldChar)
    {
        return FieldUtil.getSeparatorPresence(fieldChar.getFieldType()) != FieldSeparatorPresence.NEVER;
    }

    static /*final*/ IFieldRemoverFilter INSTANCE = new FieldRemoverRetainFwrFieldsFilter();
}

