// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies the Lower-Limit object, consisting of text on the baseline and reduced-size text
/// immediately below it.
/// </summary>
class MathObjectLowerLimit extends MathObject
{
    /*override*/ boolean canInsert(Node node)
    {
        if (super.canInsert(node))
            return true;
            
        if (node.getNodeType() == NodeType.OFFICE_MATH)
        {
            // lower limit can host both e argument and limit object.
            return ((OfficeMath)node).getMathObject().getMathObjectType() == MathObjectType.LIMIT;
        }

        return false;
    }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return MathObjectType.LOWER_LIMIT; }
}

