// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Intended for complex attributes, which comparing behaviour should be different than behaviour of the
/// <see cref="object.Equals(object)"/> method.
/// </summary>
/// <remarks>
/// <see cref="FontAttr.FarEastLayout"/> and <see cref="FontAttr.FitText"/> attributes have an ID property that
/// should not be included in value comparision when an attribute collection is collapsed.
/// </remarks>
interface ICustomEquality
{
    /// <summary>
    /// Returns <b>true</b> if this attribute and the specified one have the same value.
    /// </summary>
    public boolean hasSameValue(ICustomEquality attr);
}

