// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This element specifies a math paragraph, or display math zone, 
/// that contains one or more oMath elements that are in display mode.
/// </summary>
class MathObjectOMathPara extends MathObject
{
    /*override*/ boolean canInsert(Node node)
    {
        // OMath object is only allowed inside OMathPara
        return (node.getNodeType() == NodeType.OFFICE_MATH) &&
                (((OfficeMath)node).getMathObject().getMathObjectType() == MathObjectType.O_MATH);
    }
    
    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.O_MATH_PARA; }

    /// <summary>
    /// Specifies justification of the math paragraph (a series of adjacent instances of mathematical text 
    /// within the same paragraph).
    /// Default: should be obtained from <see cref="MathProperties.DefaultJustification"/>
    /// </summary>
    /*OfficeMathJustification*/int getJustification() { return (/*OfficeMathJustification*/Integer)fetchAttr(MathAttr.JUSTIFICATION); }
    void setJustification(/*OfficeMathJustification*/int value) { setAttr(MathAttr.JUSTIFICATION, value, value != OfficeMathJustification.DEFAULT); }
}

