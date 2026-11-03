// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlTwoArgumentFormula extends DmlFormula
{
    DmlTwoArgumentFormula(String[] formulaParts, IDmlTwoArgumentFormulaCallback callback)
    {
        if (formulaParts.length < 3)
            throw new IllegalArgumentException("Wrong number of formula parts." + "\r\nParameter name: " + "formulaParts");

        mX = formulaParts[1];
        mY = formulaParts[2];
        mCallback = callback;
    }

    DmlTwoArgumentFormula(String[] formulaParts, IDmlTwoArgumentFormulaCallback callback, String source)
    {
    	this(formulaParts, callback);
	
        setSource(source);
    }

    /// <summary>
    /// Calculates the formula.
    /// </summary>
    public /*override*/ double calculate(IDmlGuideValueProvider guideValueProvider)
    {
        double x = DmlFormulaHelper.getValue(getX(), guideValueProvider);
        double y = DmlFormulaHelper.getValue(getY(), guideValueProvider);
        return mCallback.calculate(x, y);
    }

    String getX() { return mX; }
    void setX(String value) { mX = value; }

    String getY() { return mY; }
    void setY(String value) { mY = value; }

    private /*final*/ IDmlTwoArgumentFormulaCallback mCallback;
    private String mX;
    private String mY;
}
