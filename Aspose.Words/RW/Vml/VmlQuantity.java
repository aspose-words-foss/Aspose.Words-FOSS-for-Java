// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.MathUtil;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;


/// <summary>
/// In DocxImport we have a number of occasions where we need to parse a string which contains
/// some numeric value (in various forms from integer to floating double) and some units ('pt', 'in', 'fd', etc.).
/// I have contemplated for some time how to parse such strings in a most expedient way and finally
/// decided to write a special class for handling them.
/// </summary>
class VmlQuantity
{
    VmlQuantity(String value)
    {
    	this(value, "");
	
    }

    VmlQuantity(String value, String defaultUnits)
    {
        mSource = value;

        if (!msString.equals(value, ""))
        {
            // The index of a character where number part ends and units part starts.
            int splitIndex;

            for (splitIndex = value.length() - 1; splitIndex >= 0; splitIndex--)
            {
                if (value.charAt(splitIndex) >= '0' && value.charAt(splitIndex) <= '9')
                    break;
            }

            String number = value.substring((0), (0) + (splitIndex + 1));
            mNumber = FormatterPal.tryParseDoubleInvariant(number);
            mIsValid = !Double.isNaN(mNumber);

            mUnits = msString.trim(value.substring((splitIndex + 1), (splitIndex + 1) + (value.length() - splitIndex - 1)));

            if (!StringUtil.hasChars(mUnits))
                mUnits = defaultUnits;
        }
    }

    /// <summary>
    /// Returns true if the source string can be interpreted as degrees value.
    /// </summary>
    boolean isDegrees() { return mIsValid && (("fd".equals(mUnits)) || ("f".equals(mUnits)) || isWithoutUnits()); }

    /// <summary>
    /// Returns true if the source string can be interpreted as distance value.
    /// </summary>
    boolean isDistance()
    {
        return mIsValid && (isDistanceUnits() || isWithoutUnits() || isZero());
    }

    /// <summary>
    /// Returns true if the source string can be interpreted as fixed value.
    /// </summary>
    boolean isFixed() { return mIsValid && (("f".equals(mUnits)) || isWithoutUnits()); }

    /// <summary>
    /// Returns true if the source string can be interpreted as percent value.
    /// </summary>
    boolean isPercent() { return mIsValid && (("%".equals(mUnits)) || isZero()); }

    /// <summary>
    /// Returns true if the source string has no units defined.
    /// </summary>
    boolean isWithoutUnits() { return msString.equals(mUnits, ""); }

    /// <summary>
    /// Converts quantity value from distance to EMUs.  Make sure that IsDistance is true before calling.
    /// </summary>
    int distanceToEmus()
    {
        return MathUtil.doubleToInt(distanceToPoints() * 12700.0);
    }

    /// <summary>
    /// Returns true if the source string has numeric value of '0' with no units defined.
    /// </summary>
    private boolean isZero() { return (mNumber == 0) && isWithoutUnits(); }

    /// <summary>
    /// Returns true if the source string is a numeric value with no units defined.
    /// </summary>
    private boolean isDimensionless() { return mIsValid && isWithoutUnits(); }

    /// <summary>
    /// Returns true if the source string has distance units (in, px, pt, mm, cm).
    /// </summary>
    private boolean isDistanceUnits() { return ("in".equals(mUnits)) || ("px".equals(mUnits)) || ("pt".equals(mUnits)) || ("mm".equals(mUnits)) || ("cm".equals(mUnits)); }

    /// <summary>
    /// Converts quantity value from distance to points. Make sure that IsDistance is true before calling.
    /// </summary>
    double distanceToPoints()
    {
        Debug.assertTrue(isDistance(), msString.format("'{0}' is not a valid distance value.", mSource));

        switch (gStringSwitchMap.of(mUnits))
        {
            case /*""*/0:
                return mNumber;
            case /*"px"*/1:
                // andrnosk: WORDSNET-7525 According to specification ECMA 376 possible units designators are
                // cm, mm, in, pt, pc, or px. That is why px is added.
                return ConvertUtilCore.pixelToPoint(mNumber);
            case /*"pt"*/2:
                return mNumber;
            case /*"in"*/3:
                return ConvertUtilCore.inchToPoint(mNumber);
            case /*"mm"*/4:
                return ConvertUtilCore.mmToPoint(mNumber);
            case /*"cm"*/5:
                // WORDSNET-11683 The problem occurred because size of shape in the document is specified in cm units.
                return ConvertUtilCore.cmToPoint(mNumber);
            default:
                // If the unit is not known then just return numeric part.
                // Other options are to return 0 or throw, but I do not feel inclined to them.
                // It is up to the caller to check if this instance IsDistance before calling ToPoints.
                return 0.0;
        }
    }

    /// <summary>
    /// Converts quantity value from distance to PathValue.
    /// </summary>
    PathValue distanceToPathValue()
    {
        // andrnosk: WORDSNET-4576 PathValue should be in Twips.
        return new PathValue(ConvertUtilCore.pointToTwip(distanceToPoints()), false);
    }

    /// <summary>
    /// Converts quantity value from degrees to fixed. Make sure that IsDegrees is true before calling.
    /// </summary>
    int degreesToFixed()
    {
        Debug.assertTrue(isDegrees(), msString.format("'{0}' is not a valid degrees value.", mSource));

        // WORDSNET-19749 Support the postfix "f".
        switch (gStringSwitchMap.of(mUnits))
        {
            case /*"fd"*/6:
            case /*"f"*/7:
                return MathUtil.doubleToInt(mNumber);
            case /*""*/0:
                return MathUtil.doubleToInt(mNumber * (double) 0x10000);
            default:
                return 0;
        }
    }

    /// <summary>
    /// Converts quantity value from percent to integer. Make sure that IsPercent is true before calling.
    /// </summary>
    int percentToInt()
    {
        Debug.assertTrue(isPercent(), msString.format("'{0}' is not a valid percent value.", mSource));

        if (isPercent())
            return MathUtil.doubleToInt(mNumber);
        else
            return 0;
    }

    /// <summary>
    /// Returns quantity value as fixed integer. Make sure that IsFixed is true before calling.
    /// </summary>
    int toFixed()
    {
        Debug.assertTrue(isFixed(), msString.format("'{0}' is not a valid fixed value.", mSource));

        if (isFixed())
        {
            if (isDimensionless())
                return MathUtil.doubleToInt(mNumber * (double) 0x10000);
            else
                return MathUtil.doubleToInt(mNumber);
        }
        else
            return 0;
    }

    private /*final*/ String mSource;
    private /*final*/ double mNumber;
    private /*final*/ String mUnits = "";
    private /*final*/ boolean mIsValid;

    /// <summary>
    /// Pixels (px) units string.
    /// </summary>
    static final String PIXEL_UNITS = "px";

    /// <summary>
    /// Points (pt) unit string.
    /// </summary>
    static final String POINT_UNITS = "pt";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"",
		"px",
		"pt",
		"in",
		"mm",
		"cm",
		"fd",
		"f"
	);

}

