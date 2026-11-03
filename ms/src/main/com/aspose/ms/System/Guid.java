// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/guid.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// ported 2/10/2008 by Konstantin Sidorenko
package com.aspose.ms.System;

import java.util.UUID;

public final class Guid implements Comparable<Guid>//JAVA was: struct
{
    public static final Guid Empty = new Guid();
    ////////////////////////////////////////////////////////////////////////////////
    //  Member variables
    ////////////////////////////////////////////////////////////////////////////////
    private int         _a;
    private short       _b;
    private short       _c;
    private byte       _d;
    private byte       _e;
    private byte       _f;
    private byte       _g;
    private byte       _h;
    private byte       _i;
    private byte       _j;
    private byte       _k;



    ////////////////////////////////////////////////////////////////////////////////
    //  Constructors
    ////////////////////////////////////////////////////////////////////////////////

    // Creates a new guid from an array of bytes.
    //
    public Guid(byte[] b)
    {
        if (b==null)
            throw new NullPointerException("b");
        if (b.length != 16)
            throw new IllegalArgumentException("Guid array ctor: b length should be 16.");

	    _a = ((int)b[3] << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8) | (b[0] & 0xff);
        _b = (short)((b[5] << 8) | (b[4] & 0xff));
        _c = (short)((b[7] << 8) | (b[6] & 0xff));
        _d = b[8];
        _e = b[9];
        _f = b[10];
        _g = b[11];
        _h = b[12];
        _i = b[13];
        _j = b[14];
        _k = b[15];
    }

	/**
     * Java analog for Guid(uint a, ushort b, ushort c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k)
	 * JAVA-changed uint to long, ushort to int, byte to short.
	 */
    public Guid(long a, int b, int c, short d, short e, short f, short g, short h, short i, short j, short k)
    {
        _a = (int)a;
        _b = (short)b;
        _c = (short)c;
        _d = (byte) d;
        _e = (byte) e;
        _f = (byte) f;
        _g = (byte) g;
        _h = (byte) h;
        _i = (byte) i;
        _j = (byte) j;
        _k = (byte) k;
    }

    /**
     * The same as above but byte is changed to int.
     */
    public Guid(long a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k)
    {
        _a = (int)a;
        _b = (short)b;
        _c = (short)c;
        _d = (byte) d;
        _e = (byte) e;
        _f = (byte) f;
        _g = (byte) g;
        _h = (byte) h;
        _i = (byte) i;
        _j = (byte) j;
        _k = (byte) k;
    }

	/**
	 * JAVA-added convenient workaround for .Net strong autoboxing.
	 * All parameters are ints so it's convenient to enter values (or rather copying from .net)
	 * in java without endless castings. It is developer's responsibility to provide right values.
	 */
    public Guid(int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k)
    {
        _a = (int)a;
        _b = (short)b;
        _c = (short)c;
        _d = (byte) d;
        _e = (byte) e;
        _f = (byte) f;
        _g = (byte) g;
        _h = (byte) h;
        _i = (byte) i;
        _j = (byte) j;
        _k = (byte) k;
    }

	//JAVA-added: .Net Guid stuct initialization by-default.
    public Guid()
    {
        _a = 0;
        _b = 0;
        _c = 0;
        _d = 0;
        _e = 0;
        _f = 0;
        _g = 0;
        _h = 0;
        _i = 0;
        _j = 0;
        _k = 0;
    }

	//JAVA-deleted: Guid() and NewGuid() used instead
//    private Guid(boolean blank)

	//JAVA-added
	public static Guid fromJava(UUID uuid)
	{
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();

		return new Guid(msb, lsb);
	}

	//JAVA-added
	/**
	 * This static method should be used instead of guid.toJava() to avoid NullPointerException
	 * if guid == null. For instance, the method used by ApiChangeMap.
	 */
	public static UUID toJava(Guid guid)
	{
		return guid == null ? null : guid.toJava();
	}

	public UUID toJava()
	{
		long msb = (long)_a << 32;
		msb |= (_b  & 0xffffL) << 16;
		msb |= _c  & 0xffffL;

		long lsb = (_d  & 0xffL) << 56;
		lsb |= (_e  & 0xffL) << 48;
		lsb |= (_f  & 0xffL) << 40;
		lsb |= (_g  & 0xffL) << 32;
		lsb |= (_h  & 0xffL) << 24;
		lsb |= (_i  & 0xffL) << 16;
		lsb |= (_j  & 0xffL) << 8;
		lsb |= (_k  & 0xffL);

		return new UUID(msb, lsb);
	}

	//JAVA-added analog of java's ctors with two longs.
	public Guid(long msb, long lsb)
	{
		_a = (int)(msb >> 32);
		_b = (short)(msb >> 16);
		_c = (short)msb;
		_d = (byte)(lsb >> 56);
		_e = (byte)(lsb >> 48);
		_f = (byte)(lsb >> 40);
		_g = (byte)(lsb >> 32);
		_h = (byte)(lsb >> 24);
		_i = (byte)(lsb >> 16);
		_j = (byte)(lsb >> 8);
		_k = (byte)lsb;
	}

    // Creates a new guid based on the value in the string.  The value is made up
    // of hex digits speared by the dash ("-"). The string may begin and end with
    // brackets ("{", "}").
    //
    // The string must be of the form dddddddd-dddd-dddd-dddd-dddddddddddd. where
    // d is a hex digit. (That is 8 hex digits, followed by 4, then 4, then 4,
    // then 12) such as: "CA761232-ED42-11CE-BACD-00AA0057B223"
    //
    public Guid(java.lang.String g)
    {
        if (g==null) {
            throw new NullPointerException("g");
        }

        int startPos=0;
        int temp;
        long templ;
        int[] currentPos = {0} ;//JAVA-changed to emulate ref param

        try
        {
            // Check if it's of the form dddddddd-dddd-dddd-dddd-dddddddddddd
            if(g.indexOf('-', 0) >= 0)
            {

                java.lang.String guidString = g.trim();  //Remove Whitespace

                // check to see that it's the proper length
                if (guidString.charAt(0)=='{') {
                    if (guidString.length()!=38 || guidString.charAt(37)!='}') {
                        throw new NumberFormatException("Wrong Guid format sting length: " + g);
                    }
                    startPos=1;
                }
                else if (guidString.charAt(0)=='(')
                {
                    if (guidString.length()!=38 || guidString.charAt(37)!=')')
                    {
                        throw new IllegalStateException("Wrong Guid format sting length: " + g);
                    }
                    startPos=1;
                }
                else if(guidString.length() != 36) {
                    throw new NumberFormatException("Wrong Guid format sting length: " + g);
                }
                if (guidString.charAt(8+startPos) != '-' ||
                    guidString.charAt(13+startPos) != '-' ||
                    guidString.charAt(18+startPos) != '-' ||
                    guidString.charAt(23+startPos) != '-') {
                    throw new NumberFormatException("Wrong format: missed or misplaced dashes: " + g);
                }

                currentPos[0] = startPos;
                _a = tryParse(guidString, currentPos,8);
                ++currentPos[0]; //Increment past the '-';
                _b = (short) tryParse(guidString, currentPos,4);
                ++currentPos[0]; //Increment past the '-';
                _c = (short) tryParse(guidString, currentPos,4);
                ++currentPos[0]; //Increment past the '-';
                temp= tryParse(guidString, currentPos,4);
                ++currentPos[0]; //Increment past the '-';
                startPos=currentPos[0];
                templ = ParseNumbers.stringToLong(guidString, 16, ParseNumbers.NoSpace, currentPos);
                if (currentPos[0] - startPos != 12) {
                    throw new NumberFormatException("Wrong Guid format sting length: " + g);
                }
                _d = (byte)(temp>>8);
                _e = (byte)(temp);
                temp = (int)(templ >> 32);
                _f = (byte)(temp>>8);
                _g = (byte)(temp);
                temp = (int)(templ);
                _h = (byte)(temp>>24);
                _i = (byte)(temp>>16);
                _j = (byte)(temp>>8);
                _k = (byte)(temp);

//JAVA-deleted while ParseNumbers.IsTight don't ported.
/*
				// Else check if it is of the form
				// {0xdddddddd,0xdddd,0xdddd,{0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd,0xdd}}
				else if(g.indexOf('{', 0) >= 0)
				{
					int numStart = 0;
					int numLen = 0;

					// Convert to lower case
					//g = g.ToLower();

					// Eat all of the whitespace
					g = EatAllWhitespace(g);

					// Check for leading '{'
					if(g.charAt(0) != '{')
						throw new IllegalStateException("Format_GuidBrace");

					// Check for '0x'
					if(!IsHexPrefix(g, 1))
						throw new IllegalStateException("Format_GuidHexPrefix: {0xdddddddd, etc}");

					// Find the end of this hex number (since it is not fixed length)
					numStart = 3;
					numLen = g.indexOf(',', numStart) - numStart;
					if(numLen <= 0)
						throw new IllegalStateException("Format_GuidComma");

					// Read in the number
					_a = (int) ParseNumbers.StringToInt(g.substring(numStart, numLen), // first DWORD
														16,                            // hex
														ParseNumbers.IsTight);         // tight parsing

					// Check for '0x'
					if(!IsHexPrefix(g, numStart+numLen+1))
						throw new FormatException(String.Format(CultureInfo.CurrentCulture, Environment.GetResourceString("Format_GuidHexPrefix"), "{0xdddddddd, 0xdddd, etc}"));

					// +3 to get by ',0x'
					numStart = numStart + numLen + 3;
					numLen = g.indexOf(',', numStart) - numStart;
					if(numLen <= 0)
						throw new IllegalStateException("Format_GuidComma");

					// Read in the number
					_b = (short) ParseNumbers.StringToInt(
														  g.Substring(numStart, numLen), // first DWORD
														  16,                            // hex
														  ParseNumbers.IsTight);         // tight parsing

					// Check for '0x'
					if(!IsHexPrefix(g, numStart+numLen+1))
						throw new FormatException(String.Format(CultureInfo.CurrentCulture, Environment.GetResourceString("Format_GuidHexPrefix"), "{0xdddddddd, 0xdddd, 0xdddd, etc}"));

					// +3 to get by ',0x'
					numStart = numStart + numLen + 3;
					numLen = g.indexOf(',', numStart) - numStart;
					if(numLen <= 0)
						throw new IllegalStateException("Format_GuidComma");

					// Read in the number
					_c = (short) ParseNumbers.StringToInt(
														  g.Substring(numStart, numLen), // first DWORD
														  16,                            // hex
														  ParseNumbers.IsTight);         // tight parsing

					// Check for '{'
					if(g.length() <= numStart+numLen+1 || g[numStart+numLen+1] != '{')
						throw new IllegalStateException("Format_GuidBrace"));

					// Prepare for loop
					numLen++;
					byte[] bytes = new byte[8];

					for(int i = 0; i < 8; i++)
					{
						// Check for '0x'
						if(!IsHexPrefix(g, numStart+numLen+1))
							throw new FormatException(String.Format(CultureInfo.CurrentCulture, Environment.GetResourceString("Format_GuidHexPrefix"), "{... { ... 0xdd, ...}}"));

						// +3 to get by ',0x' or '{0x' for first case
						numStart = numStart + numLen + 3;

						// Calculate number length
						if(i < 7)  // first 7 cases
						{
							numLen = g.indexOf(',', numStart) - numStart;
							if(numLen <= 0) {
								throw new IllegalStateException("Format_GuidComma"));
							}
						}
						else       // last case ends with '}', not ','
						{
							numLen = g.indexOf('}', numStart) - numStart;
							if(numLen <= 0) {
								throw new IllegalStateException("Format_GuidBraceAfterLastNumber"));
							}
						}

						// Read in the number
						uint number = (uint)Convert.ToInt32(g.Substring(numStart, numLen),16);
						// check for overflow
						if(number > 255) {
							throw new IllegalStateException("Overflow_Byte"));
						}
						bytes[i] = (byte)number;
					}

					_d = bytes[0];
					_e = bytes[1];
					_f = bytes[2];
					_g = bytes[3];
					_h = bytes[4];
					_i = bytes[5];
					_j = bytes[6];
					_k = bytes[7];

					// Check for last '}'
					if(numStart+numLen+1 >= g.length() || g[numStart+numLen+1] != '}') {
						throw new IllegalStateException("Format_GuidEndBrace"));
					}

					// Check if we have extra characters at the end
					if( numStart+numLen+1 != g.length() -1) {
						throw new IllegalStateException("Format_ExtraJunkAtEnd"));
					}

					return;
				}
				else
				// Check if it's of the form dddddddddddddddddddddddddddddddd
				{
					String guidString = g.Trim();  //Remove Whitespace

					if(guidString.length() != 32) {
						throw new IllegalStateException("Format_GuidInvLen"));
					}

					for(int i= 0; i< guidString.length(); i++) {
						char ch = guidString[i];
						if(ch >= '0' && ch <= '9') {
							continue;
						}
						else {
							char upperCaseCh = Char.ToUpper(ch, CultureInfo.INVARIANT_CULTURE);
							if(upperCaseCh >= 'A' && upperCaseCh <= 'F') {
								continue;
							}
						}

						throw new IllegalStateException("Format_GuidInvalidChar"));
					}

					_a = (int) ParseNumbers.StringToInt(guidString.Substring(startPos, 8), // first DWORD
														16,                            // hex
														ParseNumbers.IsTight);         // tight parsing
					startPos += 8;
					_b = (short) ParseNumbers.StringToInt(guidString.Substring(startPos, 4),
														16,
														ParseNumbers.IsTight);
					startPos += 4;
					_c = (short) ParseNumbers.StringToInt(guidString.Substring(startPos, 4),
														16,
														ParseNumbers.IsTight);

					startPos += 4;
					temp=(short) ParseNumbers.StringToInt(guidString.Substring(startPos, 4),
														16,
														ParseNumbers.IsTight);
					startPos += 4;
					currentPos = startPos;
					templ = ParseNumbers.StringToLong(guidString, 16, startPos, ref currentPos);
					if (currentPos - startPos!=12) {
						throw new FormatException(String.Format(CultureInfo.CurrentCulture, Environment.GetResourceString("Format_GuidInvLen")));
					}
					_d = (byte)(temp>>8);
					_e = (byte)(temp);
					temp = (int)(templ >> 32);
					_f = (byte)(temp>>8);
					_g = (byte)(temp);
					temp = (int)(templ);
					_h = (byte)(temp>>24);
					_i = (byte)(temp>>16);
					_j = (byte)(temp>>8);
					_k = (byte)(temp);
				}
*/
            }
	        else
            {
	            throw new NumberFormatException("Unsupported Guid Format: " + g);
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            throw new IllegalStateException("Format_GuidUnrecognized");
        }
    }

    // Creates a new GUID initialized to the value represented by the arguments.
    //
    public Guid(int a, short b, short c, byte[] d)
    {
        if (d==null)
            throw new NullPointerException("d");
        // Check that array is not too big
        if(d.length != 8)
            throw new ClassCastException("Arg_GuidArrayCtor. d should have length 8.");

        _a  = a;
        _b  = b;
        _c  = c;
        _d = d[0];
        _e = d[1];
        _f = d[2];
        _g = d[3];
        _h = d[4];
        _i = d[5];
        _j = d[6];
        _k = d[7];
    }

    // Creates a new GUID initialized to the value represented by the
    // arguments.  The bytes are specified like this to avoid endianness issues.
    //
    public Guid(int a, short b, short c, byte d, byte e, byte f, byte g, byte h, byte i, byte j, byte k)
    {
        _a = a;
        _b = b;
        _c = c;
        _d = d;
        _e = e;
        _f = f;
        _g = g;
        _h = h;
        _i = i;
        _j = j;
        _k = k;
    }


    private static int tryParse(java.lang.String str, int[] parsePos, int requiredLength) //JAVA-changed parsePos to emulate ref param
    {
        int currStart = parsePos[0];
        // the exception message from ParseNumbers is better
	    //JAVA-changed StringToLong instead of StringToInt since latter causes overloads in java.
        int retVal = (int) ParseNumbers.stringToLong(str, 16, ParseNumbers.NoSpace, parsePos);

        //If we didn't parse enough characters, there's clearly an error.
        if (parsePos[0] - currStart != requiredLength) {
            throw new NumberFormatException("Wrong Guid Format: Invalid Char: " + str);
        }
        return retVal;
    }

    private static java.lang.String eatAllWhitespace(java.lang.String str)
    {
        int newLength = 0;
        char[] chArr = new char[str.length()];
        char curChar;

        // Now get each char from str and if it is not whitespace add it to chArr
        for(int i = 0; i < str.length(); i++)
        {
            curChar = str.charAt(i);
            if(!msChar.isWhiteSpace(curChar))
            {
                chArr[newLength++] = curChar;
            }
        }

        // Return a new string based on chArr
        return new java.lang.String(chArr, 0, newLength);
    }

    private static boolean isHexPrefix(java.lang.String str, int i)
    {
        if(str.charAt(i) == '0' && (str.charAt(i+1) == 'x' || str.charAt(i+1) == 'X'))
            return true;
        else
            return false;
    }


    // Returns an unsigned byte array containing the GUID.
    public byte[] toByteArray()
    {
        byte[] g = new byte[16];

        g[0] = (byte)(_a);
        g[1] = (byte)(_a >> 8);
        g[2] = (byte)(_a >> 16);
        g[3] = (byte)(_a >> 24);
        g[4] = (byte)(_b);
        g[5] = (byte)(_b >> 8);
        g[6] = (byte)(_c);
        g[7] = (byte)(_c >> 8);
        g[8] = _d;
        g[9] = _e;
        g[10] = _f;
        g[11] = _g;
        g[12] = _h;
        g[13] = _i;
        g[14] = _j;
        g[15] = _k;

        return g;
    }


    // Returns the guid in "registry" format.
    public java.lang.String toString()
    {
        return toString("D");
    }

    // This is needed because java equals is overridden.
    // Implementation by Rotor.
    public int hashCode()
    {
        return _a ^ (((_b & 0xffff) << 16) | (_c & 0xffff)) ^ (((_f & 0xff) << 24) | (_k & 0xff));
    }

    // This overrides java equals.
    // Implementation by RK.
    public boolean equals(Object obj)
    {
        return (obj instanceof Guid) && equals(this, (Guid)obj);
    }

    // This is called by the autoporter when it encounters == in C#. It is the actual implementation of equality.
    // Implementation by RK.
    public static boolean equals(Guid a, Guid b)
    {
        if (a == b)
            return true;

        if ((a == null) || (b == null))
            return false;

        if(a._a != b._a)
            return false;
        if(a._b != b._b)
            return false;
        if(a._c != b._c)
            return false;
        if (a._d != b._d)
            return false;
        if (a._e != b._e)
            return false;
        if (a._f != b._f)
            return false;
        if (a._g != b._g)
            return false;
        if (a._h != b._h)
            return false;
        if (a._i != b._i)
            return false;
        if (a._j != b._j)
            return false;
        if (a._k != b._k)
            return false;

        return true;
    }

	//JAVA-deleted
//    public boolean equals(Guid g)

    private int getResult(long me, long them) {
        if (me<them) {
            return -1;
        }
        return 1;
    }

	//JAVA: not needed in java. Needed in .Net (in addition to compareTo(Guid)) because of
	//ugly implementation of generics.
/*
    public int compareTo(Object value) {
        if (value == null) {
            return 1;
        }
        if (!(value instanceof Guid)) {
            throw new IllegalArgumentException("Arg_MustBeGuid");
        }
        Guid g = (Guid)value;

        if (g._a!=this._a) {
            return GetResult((long)this._a, (long)g._a);
        }

        if (g._b!=this._b) {
            return GetResult((long)this._b, (long)g._b);
        }

        if (g._c!=this._c) {
            return GetResult((long)this._c, (long)g._c);
        }

        if (g._d!=this._d) {
            return GetResult((long)this._d, (long)g._d);
        }

        if (g._e!=this._e) {
            return GetResult((long)this._e, (long)g._e);
        }

        if (g._f!=this._f) {
            return GetResult((long)this._f, (long)g._f);
        }

        if (g._g!=this._g) {
            return GetResult((long)this._g, (long)g._g);
        }

        if (g._h!=this._h) {
            return GetResult((long)this._h, (long)g._h);
        }

        if (g._i!=this._i) {
            return GetResult((long)this._i, (long)g._i);
        }

        if (g._j!=this._j) {
            return GetResult((long)this._j, (long)g._j);
        }

        if (g._k!=this._k) {
            return GetResult((long)this._k, (long)g._k);
        }

        return 0;
    }
*/

    public int compareTo(Guid value)
    {
	    if (value == null)
	        return 1;

        if (value._a!=this._a) {
            return getResult((long)this._a, (long)value._a);
        }

        if (value._b!=this._b) {
            return getResult((long)this._b, (long)value._b);
        }

        if (value._c!=this._c) {
            return getResult((long)this._c, (long)value._c);
        }

        if (value._d!=this._d) {
            return getResult((long)this._d, (long)value._d);
        }

        if (value._e!=this._e) {
            return getResult((long)this._e, (long)value._e);
        }

        if (value._f!=this._f) {
            return getResult((long)this._f, (long)value._f);
        }

        if (value._g!=this._g) {
            return getResult((long)this._g, (long)value._g);
        }

        if (value._h!=this._h) {
            return getResult((long)this._h, (long)value._h);
        }

        if (value._i!=this._i) {
            return getResult((long)this._i, (long)value._i);
        }

        if (value._j!=this._j) {
            return getResult((long)this._j, (long)value._j);
        }

        if (value._k!=this._k) {
            return getResult((long)this._k, (long)value._k);
        }

        return 0;
    }

//JAVA-deleted
/*
    public static bool operator ==(
	Guid a, Guid b)
    {
        // Now compare each of the elements
        if(a._a != b._a)
            return false;
        if(a._b != b._b)
            return false;
        if(a._c != b._c)
            return false;
        if(a._d != b._d)
            return false;
        if(a._e != b._e)
            return false;
        if(a._f != b._f)
            return false;
        if(a._g != b._g)
            return false;
        if(a._h != b._h)
            return false;
        if(a._i != b._i)
            return false;
        if(a._j != b._j)
            return false;
        if(a._k != b._k)
            return false;

        return true;
    }

    public static bool operator !=(
	Guid a, Guid b)
    {
        return !(a == b);
    }

    // This will fill in the members of Guid using CoCreateGuid.
    [

	Guid(MethodImplOptions.InternalCall)]
    private extern void CompleteGuid();


    // This will create a new guid.  Since we've now decided that constructors should 0-init,
    // we need a method that allows users to create a guid.
    public static Guid NewGuid() {
        return new Guid(false);
    }

    public java.lang.String ToString(java.lang.String format) {
        return ToString(format, null);
    }
*/

	// This will create a new guid.  Since we've now decided that constructors should 0-init,
	// we need a method that allows users to create a guid.
	public static Guid newGuid()
	{
		return fromJava(UUID.randomUUID());//JAVA-changed
	}

    private static char hexToChar(int a)
    {
        a = a & 0xf;
        return (char) ((a > 9) ? a - 10 + 0x61 : a + 0x30);
    }

    private static int hexsToChars(char[] guidChars, int offset, int a, int b)
    {
        guidChars[offset++] = hexToChar(a>>4);
        guidChars[offset++] = hexToChar(a);
        guidChars[offset++] = hexToChar(b>>4);
        guidChars[offset++] = hexToChar(b);
        return offset;
    }

    // IFormattable interface
    // We currently ignore provider
    public java.lang.String toString(java.lang.String format)
    {
        if (format == null || format.length() == 0)
            format = "D";

        char[] guidChars;
        int offset = 0;
        int strLength = 38;
        boolean dash = true;

        if( format.length() != 1) {
            // all acceptable format string are of length 1
            throw new NumberFormatException("Format_InvalidGuidFormatSpecification");
        }

        char formatCh = format.charAt(0);
        if (formatCh == 'D' || formatCh == 'd') {
            guidChars = new char[36];
            strLength = 36;
        }
        else if (formatCh == 'N' || formatCh == 'n') {
            guidChars = new char[32];
            strLength = 32;
            dash = false;
        }
        else if (formatCh == 'B' || formatCh == 'b') {
            guidChars = new char[38];
            guidChars[offset++] = '{';
            guidChars[37] = '}';
        }
        else if (formatCh == 'P' || formatCh == 'p') {
            guidChars = new char[38];
            guidChars[offset++] = '(';
            guidChars[37] = ')';
        }
        else {
            throw new NumberFormatException("Format_InvalidGuidFormatSpecification");
        }

        offset = hexsToChars(guidChars, offset, _a >> 24, _a >> 16);
        offset = hexsToChars(guidChars, offset, _a >> 8, _a);

        if (dash) guidChars[offset++] = '-';

        offset = hexsToChars(guidChars, offset, _b >> 8, _b);

        if (dash) guidChars[offset++] = '-';

        offset = hexsToChars(guidChars, offset, _c >> 8, _c);

        if (dash) guidChars[offset++] = '-';

        offset = hexsToChars(guidChars, offset, _d, _e);

        if (dash) guidChars[offset++] = '-';

        offset = hexsToChars(guidChars, offset, _f, _g);
        offset = hexsToChars(guidChars, offset, _h, _i);
        offset = hexsToChars(guidChars, offset, _j, _k);

        return new java.lang.String(guidChars, 0, strLength);
    }
}
