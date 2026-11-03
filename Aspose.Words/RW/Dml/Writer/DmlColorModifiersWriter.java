// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/16/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/*static*/ class DmlColorModifiersWriter
{
	/* Simulation of static class by using private constructor */
	private DmlColorModifiersWriter()
	{}

    static void write(String prefix, ArrayList<IDmlColorModifier> colorModifiers, IDmlShapeWriterContext writer)
    {
        for (IDmlColorModifier modifier : colorModifiers)
            modifier.write(prefix, writer);
    }
}

