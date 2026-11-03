// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Interface for nodes which support revision tracking.
/// </summary>
interface ITrackableNode extends IMoveTrackableNode
{
    public EditRevision getInsertRevision(); public void setInsertRevision(EditRevision value);

    public EditRevision getDeleteRevision(); public void setDeleteRevision(EditRevision value);
}

