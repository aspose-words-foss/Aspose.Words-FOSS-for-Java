// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/object.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

//sk 24/11/2009

package com.aspose.ms.System;

public class msObject
{
	/**
	 * Analog of System.Object.Equals(Object,objB)
	 * Accepts null arguments.
	 * Adapted from Rotor.
	 */
	public static boolean equals(Object a, Object b)
	{
	    if (a == b)
	        return true;

		return !(a == null || b == null) && a.equals(b);
	}

    // Analog of System.Object.ReferenceEquals
    public static boolean referenceEquals(Object a, Object b)
    {
        // RK In Java comparing two objects using the operator always compares them by reference.
        return (a == b);
    }
}
