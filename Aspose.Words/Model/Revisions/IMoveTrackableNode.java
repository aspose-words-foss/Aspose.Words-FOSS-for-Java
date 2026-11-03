// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/04/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Interface for nodes which support tracking of "move" revision.
/// </summary>
/// <remarks>
/// <see cref="ITrackableNode"/> was spitted into two interfaces, because there are nodes which support only "move" revisions,
/// for example, node with type "Comment".
/// </remarks>
interface IMoveTrackableNode
{
    public MoveRevision getMoveFromRevision(); public void setMoveFromRevision(MoveRevision value);

    public MoveRevision getMoveToRevision(); public void setMoveToRevision(MoveRevision value);

    public void removeMoveRevisions();
}

