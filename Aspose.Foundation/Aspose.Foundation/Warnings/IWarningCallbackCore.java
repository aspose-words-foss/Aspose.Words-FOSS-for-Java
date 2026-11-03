// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2011 by Alexey Titov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Used to warn users.
/// </summary>
public interface IWarningCallbackCore
{
    /// <summary>
    /// Warns user
    /// </summary>
    /// <param name="warningType">Type of the warning.</param>
    /// <param name="source">The module that generates the warning.</param>
    /// <param name="description">Short description of the warning.</param>
    public void warn(/*WarningTypeCore*/int warningType, /*WarningSourceCore*/int source, String description);

    /// <summary>
    /// Warns user
    /// </summary>
    /// <param name="warningType">Type of the warning.</param>
    /// <param name="source">The module that generates the warning.</param>
    /// <param name="format">Format string of short description of the warning.</param>
    /// <param name="formatArgument">Format argument used in format string.</param>
    public void warn(/*WarningTypeCore*/int warningType, /*WarningSourceCore*/int source, String format, Object formatArgument);

    /// <summary>
    /// Warns user
    /// </summary>
    /// <param name="warningType">Type of the warning.</param>
    /// <param name="source">The module that generates the warning.</param>
    /// <param name="format">Format string of short description of the warning.</param>
    /// <param name="arg0">Format argument used in format string.</param>
    /// <param name="arg1">Format argument used in format string.</param>
    public void warn(/*WarningTypeCore*/int warningType, /*WarningSourceCore*/int source, String format, Object arg0, Object arg1);
}

