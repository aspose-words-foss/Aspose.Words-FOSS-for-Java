// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/06/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Performs cleanup actions during fields update process.
/// </summary>
interface IFieldCleaner
{
    /// <summary>
    /// Removes empty paragraph according to <see cref="FieldUpdateCleanupActions.RemoveContainingParagraphIfEmpty"/>.
    /// </summary>
    /// <returns>Returns a value indicating if the paragraph was actually removed.</returns>
    public boolean removeEmptyParagraph(Paragraph paragraph);

    /// <summary>
    /// Removes field code according to <see cref="FieldUpdateCleanupActions.RemoveContainingFieldCode"/>.
    /// </summary>
    /// <returns>Returns a value indicating if the field code was actually removed.</returns>
    public boolean removeFieldCode(FieldUpdateContext context) throws Exception;

    /// <summary>
    /// Performs postponed removals.
    /// </summary>
    public void finalizeCleanup() throws Exception;
}

