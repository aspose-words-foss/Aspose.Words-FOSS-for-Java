// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/10/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Represents a path.
/// </summary>
/// <remarks>
/// 20.1.9.15 path (Shape Path)
/// This element specifies a creation path consisting of a series of moves, lines and curves that when 
/// combined forms a geometric shape. This element is only utilized if a custom geometry is specified. 
/// </remarks>
class DmlPath implements Cloneable
{
    /// <summary>
    /// Adds the path part.
    /// </summary>
    /// <param name="dmlPathPart">The DML path part. If null then the method do nothing.</param>
    void addPathPart(IDmlPathPart dmlPathPart)
    {
        if (dmlPathPart == null)
            return;
        msArrayList.add(mPathParts, dmlPathPart);
    }

    /// <summary>
    /// Clones this instance of DML path.
    /// </summary>
    DmlPath deepClone()
    {
        DmlPath lhs = (DmlPath)memberwiseClone();

        if (lhs.mPathParts != null)
        {
            lhs.mPathParts = new ArrayList<IDmlPathPart>();
            for (IDmlPathPart part : mPathParts)
                msArrayList.add(lhs.mPathParts, part.deepClone());
        }

        return lhs;
    }

    /// <summary>
    /// Gets the path parts.
    /// </summary>
    ArrayList<IDmlPathPart> getPathParts() { return mPathParts; }

    /// <summary>
    /// Gets or sets the width.
    /// Once set, <see cref="WidthDefined"/> assigns to true.
    /// </summary>
    /// <remarks>
    /// Specifies the width, or maximum x coordinate that should be used for within the path coordinate 
    /// system. This value determines the horizontal placement of all points within the corresponding 
    /// path as they are all calculated using this width attribute as the max x coordinate.
    /// </remarks>
    double getWidth() { return mWidth; }
    void setWidth(double value)
    {
        mWidth = value;
        mWidthDefined = true;
    }

    /// <summary>
    /// Gets or sets the height. 
    /// Once set, <see cref="HeightDefined"/> assigns to true.
    /// </summary>
    /// <remarks>
    /// Specifies the height, or maximum y coordinate that should be used for within the path coordinate 
    /// system. This value determines the vertical placement of all points within the corresponding 
    /// path as they are all calculated using this height attribute as the max y coordinate.
    /// </remarks>
    double getHeight() { return mHeight; }
    void setHeight(double value)
    {
        mHeight = value;
        mHeightDefined = true;
    }

    /// <summary>
    /// Gets or sets the stroke .
    /// </summary>
    /// <remarks>
    /// Specifies if the corresponding path should have a path stroke shown. This is a Boolean value that 
    /// affect the outline of the path. If this attribute is omitted, a value of true is assumed.
    /// </remarks>
    boolean getStroke() { return mStroke; }
    void setStroke(boolean value) { mStroke = value; }

    /// <summary>
    /// Specifies how the corresponding path should be filled. 
    /// If this attribute is omitted, a value of "norm" is assumed.
    /// </summary>
    /*DmlPathFillMode*/int getFillMode() { return mFillMode; }
    void setFillMode(/*DmlPathFillMode*/int value) { mFillMode = value; }

    /// <summary>
    /// Shows if <see cref="Width"/> was defined in the document. 
    /// </summary>
    /// <remarks>
    /// Used for writing purposes.
    /// </remarks>
    boolean getWidthDefined() { return mWidthDefined; }

    /// <summary>
    /// Shows if <see cref="Height"/> was defined in the document. 
    /// </summary>
    /// <remarks>
    /// Used for writing purposes.
    /// </remarks>        
    boolean getHeightDefined() { return mHeightDefined; }

    /// <summary>
    /// Specifies that the use of 3D extrusions are possible on this path. 
    /// </summary>
    boolean getExtrusionOk() { return mExtrusionOk; }
    void setExtrusionOk(boolean value) { mExtrusionOk = value; }        

    private /*DmlPathFillMode*/int mFillMode;

    private double mHeight = -1; 
    private ArrayList<IDmlPathPart> mPathParts = new ArrayList<IDmlPathPart>();
    private boolean mStroke = true;
    private double mWidth = -1; 
    private boolean mWidthDefined = false;
    private boolean mHeightDefined = false;
    private boolean mExtrusionOk = false;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
