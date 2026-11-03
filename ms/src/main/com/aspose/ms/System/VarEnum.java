package com.aspose.ms.System;

//JAVA-added
/**
 * Emulates Microsoft system-defined enumeration VarEnum.
 * <p>
 * sk: values of VarEnum I'd get from ms-help://MS.MSDNQTR.2005JAN.1033/stg/stg/propvariant.htm
 */
public final class  VarEnum
{
	private VarEnum() {}

	public static final int VT_NULL = 1;		// This is like a pointer to NULL
	public static final int VT_I_2 = 2;			// 2-byte signed integer value
	public static final int VT_I_4 = 3;			// 4-byte signed integer value
	public static final int VT_R_8 = 5;			// 64-bit IEEE floating point value
	public static final int VT_BSTR = 8;		// Indicates a BSTR string.
	public static final int VT_BOOL = 11;		// Boolean value, a WORD containing 0 (FALSE) or -1 (TRUE)
	public static final int VT_VARIANT = 12;	// A DWORD type indicator followed by the corresponding value. VT_VARIANT can be used only with VT_VECTOR or VT_BYREF.
	public static final int VT_UI_4 = 19;		// 4-byte unsigned integer
	public static final int VT_LPSTR = 30;		// String (non-unicode)
	public static final int VT_LPWSTR = 31;		// a null-terminated Unicode string in the user's default locale
	public static final int VT_FILETIME = 64;	// 64-bit FILETIME structure as defined by Win32
	public static final int VT_BLOB = 65;		// DWORD count of bytes, followed by that many bytes of data. The byte count does not include the four bytes for the length of the count itself
	public static final int VT_VECTOR = 0x1000;	// If the type indicator is combined with VT_VECTOR by using an OR operator, the value is one of the counted array values.
}
