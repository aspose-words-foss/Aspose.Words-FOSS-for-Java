// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/dayofweek.cs
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
 * .net System.DayOfWeek stolen from Rotor.
 */
public final class DayOfWeek //JAVA was: enum
{
	public static final int SUNDAY = 0;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;

	public static String getName(int day)
	{
		switch (day)
        {
            case 0: return "Sunday";
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
        }
        return "";
	}
}
