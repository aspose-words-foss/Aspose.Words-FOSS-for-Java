// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/10/2023 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a class to calculate and update footnote reference indexes.
/// </summary>
/// <seealso cref="Footnote.Index"/>
/// <seealso cref="Footnote.ActualReferenceMark"/>
/// <seealso cref="Document.UpdateActualReferenceMarks"/>
class FootnoteIndexUpdater extends DocumentVisitor
{
    /// <summary>
    /// Ctor. It is not possible to create instances of this class externally.
    /// </summary>
    private FootnoteIndexUpdater(Document doc)
    {
        // FOSS
        mFootnoteIndexGenerator = new IndexGenerator();
        mEndnoteIndexGenerator = new IndexGenerator();
    }

    /// <summary>
    /// Calculates actual footnote reference indexes and updates the <see cref="Footnote.Index"/> properties.
    /// </summary>
    static void execute(Document doc) throws Exception
    {
        doc.accept(new FootnoteIndexUpdater(doc));
    }

    public /*override*/ /*VisitorAction*/int visitSectionStart(Section section)
    {
        mFootnoteIndexGenerator.setOptions(section.getPageSetup().getFootnoteOptions());
        mFootnoteIndexGenerator.resetIndexInSection();

        mEndnoteIndexGenerator.setOptions(section.getPageSetup().getEndnoteOptions());
        mEndnoteIndexGenerator.resetIndexInSection();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFootnoteStart(Footnote footnote)
    {
        if (!footnote.isAuto())
            return VisitorAction.CONTINUE;

        footnote.setIndex((footnote.getFootnoteType() == FootnoteType.FOOTNOTE)
            ? mFootnoteIndexGenerator.getNextIndex(footnote)
            : mEndnoteIndexGenerator.getNextIndex(footnote));

        return VisitorAction.CONTINUE;
    }

    private /*final*/ IndexGenerator mFootnoteIndexGenerator;
    private /*final*/ IndexGenerator mEndnoteIndexGenerator;

    /// <summary>
    /// Stores the current state of the index generation process and generates the next index value.
    /// </summary>
    private static class IndexGenerator
    {
        void resetIndexInSection()
        {
            mIndexInSection = 0;
        }

        int getNextIndex(Node footnote)
        {
            // Looks like MS Word uses Continuous numbering rule if start number is not 1.
            /*FootnoteNumberingRule*/int rule = (getOptions().getStartNumber() == 1)
                ? getOptions().getRestartRule()
                : FootnoteNumberingRule.CONTINUOUS;

            int index;

            switch (rule)
            {
                case FootnoteNumberingRule.RESTART_SECTION:
                    index = mIndexInSection;
                    break;
                case FootnoteNumberingRule.RESTART_PAGE:
                    // FOSS
                    index = mIndexInPage;
                    break;
                case FootnoteNumberingRule.CONTINUOUS:
                default:
                    index = mIndexInDocument;
                    break;
            }

            mIndexInDocument++;
            mIndexInSection++;
            mIndexInPage++;

            return index + getOptions().getStartNumber();
        }

        IFootnoteOptions getOptions() { return mOptions; }; void setOptions(IFootnoteOptions value) { mOptions = value; };

        private IFootnoteOptions mOptions;

        private int mIndexInDocument;
        private int mIndexInSection;
        private int mIndexInPage;
    }
}

