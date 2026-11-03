// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/08/2019 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// When implemented, used by <see cref="FieldRemover"/> to determine
/// wherever certain field should be replaced with result or not.
/// </summary>
interface IFieldRemoverFilter
{
    public boolean needReplaceFieldWithResult(FieldChar fieldChar);
}

