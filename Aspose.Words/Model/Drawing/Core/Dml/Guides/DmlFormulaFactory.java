// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


class DmlFormulaFactory implements IDmlFormulaFactory
{
    public DmlFormula create(String formula)
    {
        String[] formulaParts = parseFormula(formula);

        String formulaType = formulaParts[0];

        DmlFormula dmlFormula;
        switch (gStringSwitchMap.of(formulaType))
        {
            case /*"*//*"*/0:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new MultiplyDivideCallback());
                break;
            case /*"+-"*/1:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new AddSubtractCallback());
                break;
            case /*"+/"*/2:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new AddDivideCallback());
                break;
            case /*"?:"*/3:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new IfElseCallback());
                break;
            case /*"abs"*/4:
                dmlFormula = new DmlOneArgumentFormula(formulaParts, new AbsoluteValueCallback());
                break;
            case /*"at2"*/5:
                dmlFormula = new DmlTwoArgumentFormula(formulaParts, new ArcTanCallback());
                break;
            case /*"cat2"*/6:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new CosineArcTanCallback());
                break;
            case /*"cos"*/7:
                dmlFormula = new DmlTwoArgumentFormula(formulaParts, new CosineCallback());
                break;
            case /*"max"*/8:
                dmlFormula = new DmlTwoArgumentFormula(formulaParts, new MaximumValueCallback());
                break;
            case /*"min"*/9:
                dmlFormula = new DmlTwoArgumentFormula(formulaParts, new MinimumValueCallback());
                break;
            case /*"mod"*/10:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new ModuloCallback());
                break;
            case /*"pin"*/11:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new PinToCallback());
                break;
            case /*"sat2"*/12:
                dmlFormula = new DmlThreeArgumentFormula(formulaParts, new SineArcTanCallback());
                break;
            case /*"sin"*/13:
                dmlFormula = new DmlTwoArgumentFormula(formulaParts, new SineCallback());
                break;
            case /*"sqrt"*/14:
                dmlFormula = new DmlOneArgumentFormula(formulaParts, new SquareRootCallback());
                break;
            case /*"tan"*/15:
                dmlFormula = new DmlTwoArgumentFormula(formulaParts, new TangentCallback());
                break;
            case /*"val"*/16:
                dmlFormula = new DmlOneArgumentFormula(formulaParts, new LiteralValueCallback());
                break;
            default:
                throw new IllegalArgumentException(msString.format("Unknown formula type '{0}'.", formulaType) + "\r\nParameter name: " + "formula");
        }

        dmlFormula.setSource(formula);

        return dmlFormula;
    }

    private static String[] parseFormula(String formula)
    {
        String[] formulaParts = msString.split(formula, ' ');
        formulaParts = removeEmptyEntities(formulaParts);
        if (formulaParts.length == 0)
            throw new IllegalArgumentException(msString.format("Bad formula '{0}'.", formula) + "\r\nParameter name: " + "formula");
        return formulaParts;
    }

    private static String[] removeEmptyEntities(String[] parts)
    {
        int nonEmptyCount = 0;
        for (String s : parts)
        {
            if (StringUtil.hasChars(s))
                nonEmptyCount++;
        }

        String[] result = new String[nonEmptyCount];
        int i = 0;
        for (String s : parts)
        {
            if (StringUtil.hasChars(s))
                result[i++] = s;
        }

        return result;
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"*/",
		"+-",
		"+/",
		"?:",
		"abs",
		"at2",
		"cat2",
		"cos",
		"max",
		"min",
		"mod",
		"pin",
		"sat2",
		"sin",
		"sqrt",
		"tan",
		"val"
	);

}
