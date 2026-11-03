// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/05/2017 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Applies <see cref="FontAttr.NoProofing"/> attribute to a field result.
/// </summary>
class NoProofingFormatApplier implements IFieldResultFormatApplier
{
    private NoProofingFormatApplier()
    {
    }

    public void applyFormat(NodeRange result)
    {
        for (Node node : result)
        {
            Inline inline = ms.as(node, Inline.class);
            if (inline == null)
                continue;

            inline.getFont().setNoProofing(true);
        }
    }

    static /*final*/ NoProofingFormatApplier INSTANCE = new NoProofingFormatApplier();
}
