// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.lang.Float;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msChar;
import com.aspose.words.ref.RefBoolean;
import com.aspose.words.ref.RefFloat;


public class TestSetting
{
    static TestSetting load(int index, String key, String value, /*params*/ String... attached)
    {
        // This happens when we try to set value for setting which was not loaded by default or read from configuration.
        // We want index for the TestSetting UI. If we do not provide it then settings will appear at random on the form.
        Debug.assertTrue(index >= 0);

        TestSetting result = new TestSetting(index, key, attached);

        Debug.assertTrue(result.getKind() == KindEnum.UNDEFINED);
        boolean b = false;
        float f = 0.0f;
        result.Values = tokenizeListQuoteless(value);
        if (result.Values.size() > 1)
            result.setKind(KindEnum.ARRAY);
        else
    	{ RefBoolean referenceToB = new RefBoolean(b); boolean outRefCondition0 = tryParseBool(result.Values.get(0), /*out*/ referenceToB); b = referenceToB.get(); if (outRefCondition0)
            result.setKind(KindEnum.BOOL);
        else	{ RefFloat referenceToF = new RefFloat(f); boolean outRefCondition1 = tryParseFloat(result.Values.get(0), /*out*/ referenceToF); f = referenceToF.get(); if (outRefCondition1)
            result.setKind(KindEnum.FLOAT);
        else
            result.setKind(KindEnum.STRING);	}
    	}
        return result;
    }

    static TestSetting create(int index, String key, Object value, /*params*/ String... attached)
    {
        Debug.assertTrue(index >= 0);

        TestSetting result = new TestSetting(index, key, attached);

        if (value instanceof /*boolean*/Boolean)
        {
            result.setKind(KindEnum.BOOL);
            result.Values = new ArrayList<String>(); { result.Values.add((/*boolean*/Boolean)value ? YES : NO); }
        }
        else if (value instanceof String)
        {
            result.setKind(KindEnum.STRING);
            result.Values = new ArrayList<String>(); { result.Values.add(value.toString()); }
        }
        else if (value instanceof /*float*/Float)
        {
            result.setKind(KindEnum.FLOAT);
            result.Values = new ArrayList<String>(); { result.Values.add(value.toString()); }
        }
        else if (value instanceof String[])
        {
            result.setKind(KindEnum.ARRAY);
            result.Values = msArrayList.ctor((String[])value);
        }
        else
            throw new IllegalArgumentException("value");

        return result;
    }

    /*enum*/ final class KindEnum
{
        private KindEnum(){}
        
        public static final int UNDEFINED = 0;
        public static final int BOOL = 1;
        public static final int FLOAT = 2;
        public static final int STRING = 3;
        public static final int ARRAY = 4;

        public static final int length = 5;
}

    /*KindEnum*/int getKind() { return mKind; }; private void setKind(/*KindEnum*/int value) { mKind = value; };

    private /*KindEnum*/int mKind;

    int getIndex() { return mIndex; }; private void setIndex(int value) { mIndex = value; };

    private int mIndex;

    String getKey() { return mKey; }; private void setKey(String value) { mKey = value; };

    private String mKey;

    String[] getAttached() { return mAttached; }; private void setAttached(String[] value) { mAttached = value; };

    private String[] mAttached;

    boolean getAsBool() throws Exception
    {
        checkThrowIfWrongKind(KindEnum.BOOL);
        return toBool(Values.get(0));
    }

    float getAsFloat() throws Exception
    {
        checkThrowIfWrongKind(KindEnum.FLOAT);
        return Float.parseFloat(Values.get(0));
    }

    String getAsString() throws Exception
    {
        checkThrowIfWrongKind(KindEnum.STRING);
        return Values.get(0).replace("\\,", ",");
    }

    String[] getAsArray() throws Exception
    {
        checkThrowIfWrongKind(KindEnum.ARRAY);
        return msArrayList.toArray(Values, new String[0]);
    }

    boolean getLeader()
    {
        // if any attached line is empty, then new group is started
        for (String a : getAttached())
            if (msString.isNullOrWhiteSpace(a))
                return true;

        return false;
    }

    String getDescription()
    {
        // last attached line is a comment with description
        return getAttached().length > 0 && getAttached()[getAttached().length - 1].length() > 0
            ? getAttached()[getAttached().length - 1].substring(1) : null;
    }

    /// <summary>
    /// Updates value. Takes <paramref name="value"/> of the actual type.
    /// </summary>
    void updateValue(Object value)
    {
        Debug.assertTrue(getKind() != KindEnum.UNDEFINED);

        // this happens when called from constructor
        boolean init = Values == null;
        if (init)
            Values = new ArrayList<String>(); { Values.add(""); }

        switch (getKind())
        {
            case KindEnum.BOOL:
                Debug.assertTrue(value instanceof /*boolean*/Boolean);
                Values.set(0, (/*boolean*/Boolean)value ? TestSetting.YES : TestSetting.NO);
                break;
            case KindEnum.STRING:
                Debug.assertTrue(value instanceof String);
                Values.set(0, (String)value);
                break;
            case KindEnum.FLOAT:
                Debug.assertTrue(value instanceof /*float*/Float || value instanceof /*int*/Integer);
                Values.set(0, value.toString());
                break;
            case KindEnum.ARRAY:
                String[] input = (String[])value;
                if (init)
                {
                    Values = msArrayList.ctor(input);
                }
                else
                {
                    int index = Values.indexOf(input[0]);
                    Debug.assertTrue(index >= 0);
                    // the first item is the selected value
                    if (index > 0)
                    {
                        String temp = Values.get(0);
                        Values.set(0, Values.get(index));
                        Values.set(index, temp);
                    }
                    // extend values if passed contains new ones
                    // if passed has fewer values do not change existing ones
                    HashSetGeneric<String> existing = new HashSetGeneric<String>(Values);
                    for (String str : input)
                        if (!existing.contains(str))
                            existing.add(str);
                }
                break;
        }
    }

    /// <summary>
    /// This is used to serialize value into configuration file and build UI form.
    /// </summary>
    String getSerializedValue()
    {
        StringBuilder sb = new StringBuilder();
        // this implementation escapes ',' in the values, so that TokenizeListQuoteless can parse it back
        boolean first = true;
        for (String v : Values)
        {
            if (!first)
                sb.append(',');
            first = false;
            msStringBuilder.append(sb, v.replace(",", "\\,"));
        }
        return sb.toString();
    }

    public /*override*/ String toString()
    {
        return msString.format("{0}={1} ({2})", getKey(), getSerializedValue(), getDescription());
    }

    /// <summary>
    /// Converts string of comma separated values into list of values.
    /// This version allows untrimmed values and escapes quotes.
    /// </summary>
    private static ArrayList<String> tokenizeListQuoted(String value)
    {
        if (value == null)
            value = "";

        StringBuilder sb = new StringBuilder();
        ArrayList<String> result = new ArrayList<String>();
        boolean insideString = false;
        char last = ' ';
        for (char c : value.toCharArray())
        {
            if (insideString)
            {
                if (c == '"')
                {
                    if (last == '\\')
                    {
                        sb.setCharAt(sb.length() - 1, c);
                    }
                    else
                    {
                        insideString = false;
                    }
                }
                else
                {
                    sb.append(c);
                }
            }
            else
            {
                if (msChar.isWhiteSpace(c))
                {
                    // ignore
                }
                else if (c == ',')
                {
                    msArrayList.add(result, sb.toString());
                    sb = new StringBuilder();
                }
                else if (c == '"')
                {
                    if (last == '\\')
                    {
                        sb.setCharAt(sb.length() - 1, '"');
                    }
                    else
                    {
                        insideString = true;
                    }
                }
                else
                {
                    sb.append(c);
                }
            }
            last = c;
        }
        msArrayList.add(result, sb.toString());

        return result;
    }

    /// <summary>
    /// Converts string of comma separated values into list of values.
    /// This version trims values and escapes comma.
    /// </summary>
    private static ArrayList<String> tokenizeListQuoteless(String value)
    {
        if (value == null)
            value = "";

        StringBuilder sb = new StringBuilder();
        ArrayList<String> result = new ArrayList<String>();
        char last = ' ';
        for (char c : value.toCharArray())
        {
            if (c == ',')
            {
                if (last == '\\')
                {
                    sb.setCharAt(sb.length() - 1, c);
                }
                else
                {
                    msArrayList.add(result, msString.trim(sb.toString()));
                    sb = new StringBuilder();
                }
            }
            else
            {
                sb.append(c);
            }
            last = c;
        }
        msArrayList.add(result, msString.trim(sb.toString()));

        return result;
    }

    private static boolean toBool(String value)
    {
        boolean result = false;
        RefBoolean referenceToResult = new RefBoolean(result);
        boolean outRefCondition2 = !tryParseBool(value, /*out*/ referenceToResult);
        result = referenceToResult.get();
        if (outRefCondition2)
            throw new IllegalStateException("Cannot convert '" + value + "' to bool.");
        return result;
    }

    /// <summary>
    /// Converts "y", "yes", "true" and "1" and "n", "no", "false" and "0" to boolean.
    /// </summary>
    private static boolean tryParseBool(String value, /*out*/RefBoolean result)
    {
        switch (gStringSwitchMap.of(value.toLowerCase()))
        {
            case /*"yes"*/0:
            case /*"y"*/1:
            case /*"1"*/2:
            case /*"t"*/3:
            case /*"true"*/4:
                result.set(true);
                return true;
            case /*"no"*/5:
            case /*"n"*/6:
            case /*"f"*/7:
            case /*"false"*/8:
            case /*"0"*/9:
                result.set(false);
                return true;
            default:
                result.set(false);
                return false;
        }
    }

    /// <summary>
    /// Converts string to float.
    /// </summary>
    private static boolean tryParseFloat(String value, /*out*/RefFloat result)
    {
        result.set(FormatterPal.tryParseFloatInvariant(value));
        return (!Float.isNaN(result.get()));
    }

    private void checkThrowIfWrongKind(/*params*/ /*KindEnum*/int... kinds) throws Exception
    {
        for (/*KindEnum*/int kind : kinds)
            if (getKind() == kind)
                return;
        throw new Exception("expected '" + getKind() + "' value");
    }

    private TestSetting(int index, String key, /*params*/ String... attached)
    {
        setIndex(index);
        setKey(key);
        setAttached(new String[attached.length]);
        System.arraycopy(attached, 0, getAttached(), 0, attached.length);
    }

    private ArrayList<String> Values;
    static final String YES = "yes";
    static final String NO = "no";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"yes",
		"y",
		"1",
		"t",
		"true",
		"no",
		"n",
		"f",
		"false",
		"0"
	);

}

