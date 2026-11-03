// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the sub-superscript object, which consists of a base e,
/// a reduced-size script placed below  and to the right, 
/// and a reduced-size scr placed above and to the right.
/// </summary>
class MathObjectSubSuperscript extends MathObject
{
    /*override*/ boolean canInsert(Node node)
    {
        if (super.canInsert(node))
            return true;
            
        if (node.getNodeType() == NodeType.OFFICE_MATH)
        {
            // This object can host arguments and also special objects subscript part and
            // superscript part.
            /*MathObjectType*/int type = ((OfficeMath)node).getMathObject().getMathObjectType();
            return (type == MathObjectType.SUBSCRIPT_PART) || (type == MathObjectType.SUPERSCRIPT_PART);
        }

        return false;
    }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.SUB_SUPERSCRIPT; }

    /// <summary>
    /// Specifies the alignment of scripts in the subscript/superscript function. When 
    /// true, subscripts and superscripts are aligned to each other. 
    /// When false, they are kerned to the shape of the base. 
    /// Default: false.
    /// </summary>
    boolean isAlignScripts() { return (/*boolean*/Boolean)fetchAttr(MathAttr.IS_ALIGN_SCRIPTS); }
    void isAlignScripts(boolean value) { setAttr(MathAttr.IS_ALIGN_SCRIPTS, value, value); }
}

