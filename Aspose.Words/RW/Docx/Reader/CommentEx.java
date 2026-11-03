// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/09/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Utility class to store extended comment information. Used only during DOCX import.
/// </summary>
class CommentEx
{
    CommentEx(int parentParaId, boolean done)
    {
        ParentParaId = parentParaId;
        Done = done;
    }

    /// <summary>
    /// Paragraph Id of last paragraph of parent comment.
    /// </summary>
    int ParentParaId;

    /// <summary>
    /// Indicates that comment is marked as done.
    /// </summary>
    boolean Done;
}

