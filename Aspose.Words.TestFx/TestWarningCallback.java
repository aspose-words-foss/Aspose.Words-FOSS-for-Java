// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msString;
import java.util.ArrayList;


/// <summary>
/// Collects all warnings for later assertions.
/// </summary>
public class TestWarningCallback implements IWarningCallback
{
    public void warning(WarningInfo info)
    {
        msArrayList.add(mWarnings, info);
        warningAdded();
    }

    public WarningInfo get(int i) { return mWarnings.get(i); }

    /// <summary>
    /// Clears warning collection.
    /// </summary>
    public void clear()
    {
        mWarnings.clear();
    }

    public int getCount() { return mWarnings.size(); }

    /// <summary>
    /// Returns true if a warning with the specified properties has been generated.
    /// </summary>
    public boolean contains(/*WarningSource*/int source, /*WarningType*/int type, String description)
    {
        for (WarningInfo warning : mWarnings)
        {
            if ((warning.getSource() == source) && (warning.getWarningType() == type) && (msString.equals(warning.getDescription(), description)))
                return true;
        }
        return false;
    }

    protected /*virtual*/ void warningAdded()
    {
        // Can be overridden in a subclass
    }

    private /*final*/ ArrayList<WarningInfo> mWarnings = new ArrayList<WarningInfo>();
}

