// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/05/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// This class is for tests purposes only. Current implementation meaningfully adds formatting difference while
/// an instance cloning. It can be used to determine operations, which generally does not change results. However,
/// it is necessary to check that these operations were performed or not.
/// </summary>
class ListDefStub extends ListDef
{
    public ListDefStub(ListDef origListDef)
    {
    	super(origListDef.getDocument(),
        origListDef.getDocument().getLists().makeUniqueListDefId(), origListDef.getListType(), origListDef.getTemplateCode());
	
        for (ListLevel level : origListDef.getLevels().deepClone(getDocument()))
            getLevels().set(level.getLevelNumber(), level);

        isRestartAtEachSection(origListDef.isRestartAtEachSection());
        setListStyleIstd(origListDef.getListStyleIstd());
        setName(origListDef.getName());
    }

    /// <summary>
    /// Clones list definition and changes its left indent.
    /// </summary>
    /*override*/ ListDef deepClone(DocumentBase document, int listDefId)
    {
        // Some value that we add to the original ListDef indent
        // to make it different with its clone.
        final int INDENT_DIFFERENCE_WITH_ORIGINAL = 10;

        ListDef clonedListDef = super.deepClone(document, listDefId);
        ListLevel level = clonedListDef.getLevels().fetchListLevel(0);
        level.getParaPr().setLeftIndent(level.getParaPr().getLeftIndent() + INDENT_DIFFERENCE_WITH_ORIGINAL);

        return clonedListDef;
    }
}

