// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


class DmlGuideFactory implements IDmlGuideFactory
{
    public DmlGuide createGuide(String formula, String name, boolean isPreset)
    {
        if (formula == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "formula");
        if (name == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "name");
        DmlFormula dmlFormula = getFormulaFactory().create(formula);
        return new DmlGuide(name, dmlFormula, isPreset);
    }

    DmlGuide createGuide(String formula, String name)
    {
        return createGuide(formula, name, false);
    }

    /// <summary>
    /// Initializes common guides.
    /// </summary>
    /// <remarks>
    /// Described in 5.1.12.56 ST_ShapeType (Preset Shape Types)
    /// </remarks>
    public ArrayList<DmlGuide> createCommonGuides()
    {
        ArrayList<DmlGuide> commonGuides = new ArrayList<DmlGuide>(34);
        msArrayList.add(commonGuides, createGuide("val 16200000", "3cd4"));
        msArrayList.add(commonGuides, createGuide("val 8100000", "3cd8"));
        msArrayList.add(commonGuides, createGuide("val 13500000", "5cd8"));
        msArrayList.add(commonGuides, createGuide("val 18900000", "7cd8"));
        msArrayList.add(commonGuides, createGuide("val 10800000", "cd2"));
        msArrayList.add(commonGuides, createGuide("val 5400000", "cd4"));
        msArrayList.add(commonGuides, createGuide("val 2700000", "cd8"));

        msArrayList.add(commonGuides, createGuide("val h", "b"));
        msArrayList.add(commonGuides, createGuide("val 0", "l"));
        msArrayList.add(commonGuides, createGuide("val 0", "t"));
        msArrayList.add(commonGuides, createGuide("val w", "r"));

        msArrayList.add(commonGuides, createGuide("*/ w 1.0 2.0", "hc"));
        msArrayList.add(commonGuides, createGuide("*/ h 1.0 2.0", "hd2"));
        msArrayList.add(commonGuides, createGuide("*/ h 1.0 3.0", "hd3")); // Not described in specs
        msArrayList.add(commonGuides, createGuide("*/ h 1.0 4.0", "hd4"));
        msArrayList.add(commonGuides, createGuide("*/ h 1.0 5.0", "hd5"));
        msArrayList.add(commonGuides, createGuide("*/ h 1.0 6.0", "hd6"));
        msArrayList.add(commonGuides, createGuide("*/ h 1.0 8.0", "hd8"));

        msArrayList.add(commonGuides, createGuide("*/ h 1.0 2.0", "vc"));
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 2.0", "wd2"));
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 3.0", "wd3")); // Not described in specs
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 4.0", "wd4"));
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 5.0", "wd5"));
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 6.0", "wd6"));
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 8.0", "wd8"));
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 10.0", "wd10"));

        // WORDSNET-26190
        // Text box rectangle for rtTriangle preset had an unrecognized value.
        msArrayList.add(commonGuides, createGuide("*/ w 1.0 12.0", "wd12")); // Not described in specs

        msArrayList.add(commonGuides, createGuide("*/ w 1.0 32.0", "wd32")); // Not described in specs

        msArrayList.add(commonGuides, createGuide("max w h", "ls"));
        msArrayList.add(commonGuides, createGuide("min w h", "ss"));
        msArrayList.add(commonGuides, createGuide("*/ ss 1.0 2.0", "ssd2"));
        msArrayList.add(commonGuides, createGuide("*/ ss 1.0 4.0", "ssd4"));
        msArrayList.add(commonGuides, createGuide("*/ ss 1.0 6.0", "ssd6"));
        msArrayList.add(commonGuides, createGuide("*/ ss 1.0 8.0", "ssd8"));
        msArrayList.add(commonGuides, createGuide("*/ ss 1.0 16.0", "ssd16"));
        msArrayList.add(commonGuides, createGuide("*/ ss 1.0 32.0", "ssd32")); // Not described in specs
        return commonGuides;
    }

    IDmlFormulaFactory getFormulaFactory() { return mFormulaFactory; }
    void setFormulaFactory(IDmlFormulaFactory value) { mFormulaFactory = value; }

    private IDmlFormulaFactory mFormulaFactory = new DmlFormulaFactory();
}

