// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlOneArgumentFormula extends DmlFormula
{
    DmlOneArgumentFormula(String[] formulaParts, IDmlOneArgumentFormulaCallback callback)
    {
        if (formulaParts.length < 2)
            throw new IllegalArgumentException("Wrong number of formula parts." + "\r\nParameter name: " + "formulaParts");

        mX = formulaParts[1];
        mCallback = callback;
    }

    DmlOneArgumentFormula(String[] formulaParts, IDmlOneArgumentFormulaCallback callback, String source)
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
        return mCallback.calculate(x);
    }

    String getX() { return mX; }
    void setX(String value) { mX = value; }

    private /*final*/ IDmlOneArgumentFormulaCallback mCallback;
    private String mX;
}
