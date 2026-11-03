// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/datetimekind.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

/**
 * Helper Java code to port Aspose.Word C# source, 23/10/2007
 *
 * @author Konstantin Sidorenko
 */

package com.aspose.ms.System;

/**
 * .net System.DateTimeKind stolen from Rotor.
 */
// This enum is used to identify DateTime instances in cases when they are known to be in local time,
// UTC time or if this information has not been specified or is not applicable.
public final class DateTimeKind //JAVA-changed from enum
{
	//JAVA: long value used due conflict of int in some method signatures.
	public static final long UNSPECIFIED = 0L;
	public static final long UTC = 1L;
	public static final long LOCAL = 2L;
}
