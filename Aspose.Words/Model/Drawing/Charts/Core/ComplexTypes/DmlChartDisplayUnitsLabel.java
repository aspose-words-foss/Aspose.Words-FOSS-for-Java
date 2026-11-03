// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents 5.7.2.46 dispUnitsLbl (Display Units Label) element.
/// This element specifies the display unit label for the value axis in the specified chart.
/// </summary>
class DmlChartDisplayUnitsLabel extends DmlChartTitle implements Cloneable
{
    DmlChartDisplayUnitsLabel(IDmlChartTitleHolder chartTitleHolder)
    {
    	super(chartTitleHolder);
	
    }

    /// <summary>
    /// Clones this instance of display units label.
    /// </summary>
    /*override*/ DmlChartTitle deepClone()
    {
        DmlChartDisplayUnitsLabel lhs = (DmlChartDisplayUnitsLabel)memberwiseClone();

        copyComplexPropertiesTo(lhs);

        return lhs;
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

