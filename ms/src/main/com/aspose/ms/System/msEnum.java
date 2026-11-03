//Konstantin Sidorenko 28/10/11

package com.aspose.ms.System;

import com.aspose.ms.java.debug.JavaDebug;
import com.aspose.ms.java.logging.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class msEnum
{
	/**
	 * Analog of System.Enum.GetName(System.Type enumType, object value):
	 * Retrieves the name of the constant in the specified enumeration that has the specified value.
	 *
	 * This method works only for enums backed by int.
	 * If finds nothing returns just string representation of given constant name.
	 *
	 * NOTE: Using of Enum.GetName(), .ToString() and .Parse() is prohibited by the Aspose.Words
	 * code guidelines. This code should be used only for debug and test purposes.
	 */
	public static String getName(Class enumType, int constantValue)
	{
		try
		{
			//In java we use static constants instead of .Net enums so let's use reflection to retrieve the name.
			Field[] fields = enumType.getDeclaredFields();
			for (Field field : fields)
			{
				//change to byte, long, etc. for methods that works with enums backed by bytes, longs, etc.
				if (field.getType() == int.class)
				{
					field.setAccessible(true);
					int value = field.getInt(null);
					if (value == constantValue)
						return field.getName();
				}
			}
		}
		catch (Exception e) { /* do nothing */ }

		//Found nothing or threw security exception
		return Integer.toString(constantValue);
	}

	/**
	 * NOTE: Use this method only for debug!
     * The method introduces a bug in production code for enums that not in public API: Proguard
     * optimizes internal code - inlines enum values and deletes them from the Enum class.
     * As a result, msEnum.isDefined() always returns false for non-public-api enums.
	 */
	public static boolean isDefined(Class enumType, int value)
	{
		try
		{
			Method getValues = enumType.getMethod("getValues", null);
			int[] values = (int[]) getValues.invoke(enumType, null);
			Arrays.sort(values);
			return Arrays.binarySearch(values, value) >= 0;
		}
		catch (Exception e)
		{
			Log.warn(e);

			return false;
		}
	}
}
