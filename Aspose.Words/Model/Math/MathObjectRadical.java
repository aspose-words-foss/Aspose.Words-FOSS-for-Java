// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the radical object (root of n power), consisting of a radical, a base e, and an optional degree deg.
/// </summary>
/// <remarks>If the degree argument is omitted, radical is considered to be sqare root.</remarks>
class MathObjectRadical extends MathObject
{
    /*override*/ boolean canInsert(Node node)
    {
        if (super.canInsert(node))
            return true;
            
        if (node.getNodeType() == NodeType.OFFICE_MATH)
        {
            // Radical object can host arguments and also special object degree.
            return ((OfficeMath)node).getMathObject().getMathObjectType() == MathObjectType.DEGREE;
        }

        return false;
    }
    
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.RADICAL; }

    /// <summary>
    /// Specifies the per-object option to hide the degree of a radical. Every rad has a degree, but the degree can
    /// appear or not appear.
    /// Default is false.
    /// </summary>
    boolean isHideDegree() { return (/*boolean*/Boolean)fetchAttr(MathAttr.DEGREE_HIDE); }
    void isHideDegree(boolean value) { setAttr(MathAttr.DEGREE_HIDE, value, value); }
}

