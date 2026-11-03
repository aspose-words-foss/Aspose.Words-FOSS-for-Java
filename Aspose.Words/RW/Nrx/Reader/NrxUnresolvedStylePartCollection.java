// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// A collection of <see cref="NrxUnresolvedStylePart"/>.
/// </summary>
class NrxUnresolvedStylePartCollection extends ArrayList<NrxUnresolvedStylePart>
{
    /// <summary>
    /// Resolve the unresolved attributes for styles.
    /// </summary>
    void resolveStyleLinks(INrxStyleIdToIstd resolver)
    {
        for (NrxUnresolvedStylePart unresolved : (Iterable<NrxUnresolvedStylePart>) this)
        {
            Style style = unresolved.Style;

            // WORDSNET-22587, WORDSNET-26219 Word seems to remove `BasedOn` style in a Normal style.
            if ((unresolved.BasedOn != null) && (style.getStyleIdentifier() != StyleIdentifier.NORMAL))
                style.setBasedOnIstd(resolver.resolveStyleIdToIstd(unresolved.BasedOn, StyleIndex.NIL));

            if (unresolved.Next != null)
                style.setNextIstd(resolver.resolveStyleIdToIstd(unresolved.Next, style.getIstd()));

            if (unresolved.Link != null)
                style.setLinkedIstd(resolver.resolveStyleIdToIstd(unresolved.Link, StyleIndex.NIL));
        }
    }
}

