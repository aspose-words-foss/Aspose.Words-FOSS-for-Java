// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2019 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class FieldRemoverDefaultFilter implements IFieldRemoverFilter
{
    private FieldRemoverDefaultFilter()
    {
    }

    public boolean /*IFieldRemoverFilter.*/needReplaceFieldWithResult(FieldChar fieldChar)
    {
        return true;
    }

    static /*final*/ IFieldRemoverFilter INSTANCE = new FieldRemoverDefaultFilter();
}

