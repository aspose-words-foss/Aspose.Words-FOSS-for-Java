// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents any unknown (or unsupported by our CSS engine) element definition.
/// You should not register this class in <see cref="DefaultElementStyleResolver" />.
/// </summary>
class UnknownElementDef extends ElementDef
{
    protected /*override*/ void applyStyles(IElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Unknown elements have no default CSS style.
    }
}

