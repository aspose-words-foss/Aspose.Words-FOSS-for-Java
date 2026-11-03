// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.3.1 bg (Background Formatting)
/// </summary>
class DmlDiagramBackground
{
    DmlFill getFill()
    {
        // If fill isn't specified then use new DmlStyleFill.
        if (mFill == null)
            mFill = new DmlStyleFill();

        return mFill;
    }
    void setFill(DmlFill value) { mFill = value; }

    DmlShapeEffectsCollection getEffects() { return mEffects; }
    void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    private DmlFill mFill;
    private DmlShapeEffectsCollection mEffects;
}

