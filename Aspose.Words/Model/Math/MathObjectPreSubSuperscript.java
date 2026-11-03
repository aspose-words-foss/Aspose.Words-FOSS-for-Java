// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the Pre-Sub-Superscript object, which consists of a base e and a 
/// subscript and superscript placed to the left of the base.
/// </summary>
class MathObjectPreSubSuperscript extends MathObject
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

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.PRE_SUB_SUPERSCRIPT; }
}

