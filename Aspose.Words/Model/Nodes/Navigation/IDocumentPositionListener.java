// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// When implemented, receives notifications about <see cref="DocumentPosition"/> movements.
/// </summary>
interface IDocumentPositionListener
{
    /// <summary>
    /// Receives notifications after <see cref="DocumentPosition"/> moved.
    /// </summary>
    /// <param name="movement">Specifies what movement was done.</param>
    public void notifyMoved(/*DocumentPositionMovement*/int movement);
}

