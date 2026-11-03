// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether there is a line break at the start of a math run <see cref="Run"/>, 
/// or at the start of the <see cref="MathObjectBox"/> object, such
/// that the line wraps at the start of the run or box object.
/// 
/// RK It is a bit unusual to implement an attribute as a complex attribute. I would prefer it to be simple int, but let's keep as is for now.
/// </summary>
class MathLineBreak implements IComplexAttr, Cloneable
{
    public boolean isInheritedComplexAttr() { return false; }

    public IComplexAttr deepCloneComplexAttr()
    {
        return (IComplexAttr)memberwiseClone();
    }

    public /*override*/ int hashCode()
    {
        return (/*int.hashCode()*/mAlignment);
    }

    /// <summary><list>
    /// <item>
    /// If in range 1 to 255 inclusive, Specifies how to align the current line of the equation with the first line of the equation.
    /// by defining the index of the operator which shall be used as the alignment point for the current line of mathematical text. 
    /// </item>
    /// <item>
    /// If equal to 0 (Default), Align with the left margin of the first run of mathematical text.
    /// </item>
    /// <item>
    /// Value of alignment needs to be less than the index of the first operator in the equation with a manual break.
    /// </item>
    /// </list></summary>
    /// <remarks>        
    /// A line can be aligned to any operator on the previous line; this attribute specifies exactly which
    /// operator shall be the target of that alignment in cases where there are multiple
    /// operators.  
    /// </remarks>
    int getAlignment() { return mAlignment; }
    void setAlignment(int value) { mAlignment = (value > 255) ? 255 : (value < 0) ? 0 : value; }
    
    /// <summary>
    /// Returns true if <see cref="Alignment"/> is default.
    /// </summary>
    boolean isDefaultAlignment() { return mAlignment == 0; }

    private int mAlignment;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

