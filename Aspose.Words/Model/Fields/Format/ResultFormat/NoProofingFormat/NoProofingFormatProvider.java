// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/05/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Provides special formatting for <see cref="FieldMergeField"/> field result.
/// </summary>
class NoProofingFormatProvider implements IFieldResultFormatProvider
{
    private NoProofingFormatProvider()
    {
    }

    public Inline getSourceNode()
    {
        return null;
    }

    public IFieldResultFormatApplier getFormatApplier()
    {
        return NoProofingFormatApplier.INSTANCE;
    }

    static /*final*/ NoProofingFormatProvider INSTANCE = new NoProofingFormatProvider();
}
