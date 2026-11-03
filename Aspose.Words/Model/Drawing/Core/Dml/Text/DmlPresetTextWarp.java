// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// 20.1.9.19 prstTxWarp (Preset Text Warp)
/// This element specifies when a preset geometric shape should be used to transform 
/// a piece of text. This operation is known formally as a text warp. The generating 
/// application should be able to render all preset geometries enumerated in the ST_TextShapeType list.
/// </summary>
class DmlPresetTextWarp implements Cloneable
{
    /// <summary>
    /// Adds the path.
    /// </summary>
    /// <param name="path">The path. If the path is null then method do nothing.</param>
    void addPath(DmlPath path)
    {
        if (path == null)
            return;
        msArrayList.add(mPaths, path);
    }

    DmlPresetTextWarp deepClone()
    {
        DmlPresetTextWarp lhs = (DmlPresetTextWarp)memberwiseClone();

        lhs.mGuides = mGuides.deepClone();

        if (lhs.mPaths != null)
        {
            lhs.mPaths = new ArrayList<DmlPath>();
            for (DmlPath path : mPaths)
                msArrayList.add(lhs.mPaths, path.deepClone());
        }

        return lhs;
    }

    /*DmlTextShapeType*/int getTextShapeType() { return mTextShapeType; }
    void setTextShapeType(/*DmlTextShapeType*/int value) { mTextShapeType = value; }

    /// <summary>
    /// Gets the guides.
    /// </summary>
    /// <remarks>
    /// Contains adjustable values and guides for the specified shape.
    /// </remarks>
    DmlGuides getGuides() { return mGuides; }

    ArrayList<DmlPath> getPaths() { return mPaths; }

    private ArrayList<DmlPath> mPaths = new ArrayList<DmlPath>();
    private /*DmlTextShapeType*/int mTextShapeType = DmlTextShapeType.NONE;
    private DmlGuides mGuides = new DmlGuides();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
