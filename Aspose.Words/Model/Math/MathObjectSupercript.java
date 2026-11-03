// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the superscript object sSup, which consists of a base e and 
/// a reduced-size script placed above and to the right.
/// </summary>
class MathObjectSuperscript extends MathObject
{
    /*override*/ boolean canInsert(Node node)
    {
        if (super.canInsert(node))
            return true;
            
        if (node.getNodeType() == NodeType.OFFICE_MATH)
        {
            // This object can host arguments and also special object superscript part.
            return (((OfficeMath)node).getMathObject().getMathObjectType() == MathObjectType.SUPERSCRIPT_PART);
        }

        return false;
    }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.SUPERSCRIPT; }
}

