// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ArgumentUtil;
import com.aspose.ListUtil;


/// <summary>
/// 20.1.8.21 custDash (Custom Dash)
/// This element specifies a custom dashing scheme.
/// It is a list of dash stop elements which represent
/// building block atoms upon which the custom dashing scheme is built.
/// </summary>
class DmlCustomDash extends DmlDash
{
    /*override*/ DmlDash deepClone()
    {
        DmlCustomDash result = new DmlCustomDash();
        result.setDashStops(new ArrayList<DmlDashStop>(getDashStops().size()));
        for (DmlDashStop dashStop : getDashStops())
            msArrayList.add(result.getDashStops(), dashStop.deepClone());

        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlCustomDash value = (DmlCustomDash)obj;

        return (value.getDashType() == getDashType()) && ListUtil.checkAreEquals(value.getDashStops(), getDashStops());
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getDashType();
        for (DmlDashStop stop : getDashStops())
            hash ^= stop.hashCode();
        return hash;
    }

    ArrayList<DmlDashStop> getDashStops()
    {
        if (mDashStops == null)
            mDashStops = new ArrayList<DmlDashStop>();
        return mDashStops;
    }
    void setDashStops(ArrayList<DmlDashStop> value) { mDashStops = value; }

    /*override*/ /*DmlDashType*/int getDashType() { return DmlDashType.CUSTOM_DASH; }

    private ArrayList<DmlDashStop> mDashStops;
}

