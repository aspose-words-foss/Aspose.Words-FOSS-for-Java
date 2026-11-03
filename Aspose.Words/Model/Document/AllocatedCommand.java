// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArrayUtil;

/// <summary>
/// http://www.documentinteropinitiative.org/additionalinfo/sect1.aspx#_Toc253758987
/// 1.2.1.1.1. acd (Allocated Command)
/// </summary>
class AllocatedCommand
{
    /// <summary>
    /// Ctor. By the default this creates an "unused" command.
    /// </summary>
    AllocatedCommand()
    {
    }

    AllocatedCommand(/*FixedCommandIdentifier*/int fciBasedOn, byte[] argValue)
    {
        this.FciBasedOn = fciBasedOn;
        this.ArgValue = argValue;
    }

    /// <summary>
    /// Specifies the fixed command that shall be used for this allocated command.
    /// </summary>
    /*FixedCommandIdentifier*/int FciBasedOn;

    /// <summary>
    /// Specifies the argument that should be passed to the fixed command.
    /// </summary>
    byte[] ArgValue = ArrayUtil.EMPTY_BYTE_ARRAY;
}

