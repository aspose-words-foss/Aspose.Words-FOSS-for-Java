// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


interface IDmlGuideFactory
{
    public DmlGuide createGuide(String formula, String name, boolean isPreset);

    /// <summary>
    /// Initializes common guides.
    /// </summary>
    /// <remarks>
    /// Described in 5.1.12.56 ST_ShapeType (Preset Shape Types)
    /// </remarks>
    public ArrayList<DmlGuide> createCommonGuides();
}
