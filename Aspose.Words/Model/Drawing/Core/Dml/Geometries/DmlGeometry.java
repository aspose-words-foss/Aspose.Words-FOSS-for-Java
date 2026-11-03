// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.Drawing.RectangleF;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


class DmlGeometry implements Cloneable
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

    DmlGeometry deepClone()
    {
        DmlGeometry lhs = (DmlGeometry)memberwiseClone();

        lhs.mGuides = mGuides.deepClone();

        if (lhs.mPaths != null)
        {
            lhs.mPaths = new ArrayList<DmlPath>();
            for (DmlPath path : mPaths)
                msArrayList.add(lhs.mPaths, path.deepClone());
        }

        if (mConnectionSites != null)
        {
            lhs.mConnectionSites = new ArrayList<DmlConnectionSite>();
            for (DmlConnectionSite site : mConnectionSites)
                lhs.mConnectionSites.add(site.deepClone());
        }

        if (mAdjustHandles != null)
        {
            lhs.mAdjustHandles = new ArrayList<DmlAdjustHandle>();
            for (DmlAdjustHandle adjustHandle : mAdjustHandles)
                msArrayList.add(lhs.mAdjustHandles, adjustHandle.deepClone());
        }

        if (mTxbxRect != null)
            lhs.mTxbxRect = mTxbxRect.deepClone();

        return lhs;
    }

    /// <summary>
    /// Gets the guides.
    /// </summary>
    /// <remarks>
    /// Contains adjustable values and guides for the specified shape.
    /// </remarks>
    DmlGuides getGuides() { return mGuides; }

    String getPresetName()
    {
        if (mName == null)
            return "";
        return mName;
    }
    void setPresetName(String value) { mName = value; }

    ArrayList<DmlPath> getPaths() { return mPaths; }

    ArrayList<DmlAdjustHandle> getAdjustHandles() { return mAdjustHandles; }

    /// <summary>
    /// Returns rectangular bounding box for text within a shape.
    /// Note: Guides must be calculated.
    /// </summary>
    RectangleF getTextboxRect()
    {
        if (mTxbxRect == null)
            return RectangleF.Empty;
        return mTxbxRect.getRectangle(mGuides,
            msString.equals(getPresetName(), "uparrow", com.aspose.ms.System.StringComparison.ORDINAL_IGNORE_CASE));
    }

    DmlTextBoxRect getDmlTextboxRect() { return mTxbxRect; }
    void setDmlTextboxRect(DmlTextBoxRect value) { mTxbxRect = value; }

    /// <summary>
    /// Collection of the shape connection sites.
    /// </summary>
    ArrayList<DmlConnectionSite> getConnectionSites() { return mConnectionSites; }
    void setConnectionSites(ArrayList<DmlConnectionSite> value) { mConnectionSites = value; }
    
    private ArrayList<DmlPath> mPaths = new ArrayList<DmlPath>();
    private DmlGuides mGuides = new DmlGuides();
    private ArrayList<DmlConnectionSite> mConnectionSites = new ArrayList<DmlConnectionSite>();

    private String mName;
    private DmlTextBoxRect mTxbxRect;        
    private ArrayList<DmlAdjustHandle> mAdjustHandles = new ArrayList<DmlAdjustHandle>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

