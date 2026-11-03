// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Progress processor for the post-loading stage.
/// Uses numeric values to calculate estimated progress.
/// </summary>
class PostLoadingProgressProcessor extends IncrementalLoadingProgressProcessor
{
    PostLoadingProgressProcessor(
        DocumentBase docBase,
        LoadOptions loadOptions)
    {
    	super(docBase, loadOptions.getProgressCallback(), LoadingStageType.PROCESSING, 0);
	
        if (hasCallback() && isProgressSupported())
            OverallCount = Doc.getChildNodes(NodeType.PARAGRAPH, true).getCount();
    }
}

