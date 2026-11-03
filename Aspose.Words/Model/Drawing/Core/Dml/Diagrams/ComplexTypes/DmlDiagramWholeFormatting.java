// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.3.9 whole (Whole E2O Formatting)
/// </summary>
class DmlDiagramWholeFormatting
{
    DmlOutline getOutline()
    { 
        if (mOutline == null)
            mOutline = new DmlOutline();
        return mOutline;
    }
    void setOutline(DmlOutline value) { mOutline = value; }

    DmlShapeEffectsCollection getEffects() { return mEffects; }
    void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    private DmlOutline mOutline;
    private DmlShapeEffectsCollection mEffects;
}

