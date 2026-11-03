// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the fraction object, consisting of a numerator and denominator separated by a fraction
/// bar. The fraction bar can be horizontal or diagonal, depending on the fraction properties. The fraction object is
/// also used to represent the stack function, which places one element above another, with no fraction bar.
/// </summary>
class MathObjectFraction extends MathObject
{
     /// <summary>
    /// Can only host m:numerator and m:denominator.
    /// </summary>
    /*override*/ boolean canInsert(Node node)
    {
        if (node.getNodeType() == NodeType.OFFICE_MATH)
        {
            MathObject mathObject = ((OfficeMath)node).getMathObject();
            return (mathObject.getMathObjectType() == MathObjectType.NUMERATOR) || (mathObject.getMathObjectType() == MathObjectType.DENOMINATOR);
        }
                
        return false;
    }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.FRACTION; }
    
    /// <summary>
    /// This element specifies the type of fraction sign for this fraction object.
    /// Default: FractionType.Default
    /// </summary>
    /*MathFractionType*/int getFractionType() { return (/*MathFractionType*/Integer)fetchAttr(MathAttr.FRACTION_TYPE); }
    void setFractionType(/*MathFractionType*/int value) { setAttr(MathAttr.FRACTION_TYPE, value, value != MathFractionType.DEFAULT); }
}

