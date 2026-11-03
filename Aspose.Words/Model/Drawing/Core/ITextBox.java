// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/16/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the common properties of DrawingML and Shape that are required for rendering of their text box content.
/// Interface is created for unification of text boxes layout and abstraction from underlying technology used by
/// graphics object.
/// </summary>
interface ITextBox
{
    public /*TextBoxWrapMode*/int getTextBoxWrapMode_ITextBox();
    public /*LayoutFlow*/int getTextboxLayoutFlow_ITextBox();
    public boolean hasVerticalTextFlow_ITextBox();
    public /*ShapeMarkupLanguage*/byte getMarkupLanguage_ITextBox();

    /// <summary>
    /// Gets the sum of horizontal margins.
    /// </summary>
    /// <remarks>
    /// It is used to calculate how much space is left for text box contents in a container of a certain size.
    /// </remarks>
    public float getHorizontalMargins_ITextBox();
}

