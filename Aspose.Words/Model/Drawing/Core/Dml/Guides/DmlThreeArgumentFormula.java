// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



class DmlThreeArgumentFormula extends DmlFormula
{
    DmlThreeArgumentFormula(String[] formulaParts, IDmlThreeArgumentFormulaCallback callback)
    {
        if (formulaParts.length < 4)
            throw new IllegalArgumentException("Wrong number of formula parts." + "\r\nParameter name: " + "formulaParts");

        mX = formulaParts[1];
        mY = formulaParts[2];
        mZ = formulaParts[3];
        mCallback = callback;
    }
            
    DmlThreeArgumentFormula(String[] formulaParts, IDmlThreeArgumentFormulaCallback callback,
        String source)
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
        double z = DmlFormulaHelper.getValue(getZ(), guideValueProvider);
        return mCallback.calculate(x, y, z);
    }

    String getX() { return mX; }
    void setX(String value) { mX = value; }

    String getY() { return mY; }
    void setY(String value) { mY = value; }

    String getZ() { return mZ; }
    void setZ(String value) { mZ = value; }

    private /*final*/ IDmlThreeArgumentFormulaCallback mCallback;
    private String mX;
    private String mY;
    private String mZ;
}
