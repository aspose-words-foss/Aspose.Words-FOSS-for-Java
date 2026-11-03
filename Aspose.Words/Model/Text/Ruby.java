// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Language;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements Phonetic Guide. See [ECMA-376] 17.3.3.25 ruby (Phonetic Guide)
/// </summary>
class Ruby implements IComplexAttr, Cloneable
{
    public boolean /*IComplexAttr.*/isInheritedComplexAttr() { return false; }

    public IComplexAttr /*IComplexAttr.*/deepCloneComplexAttr()
    {
        return deepClone();
    }

    public /*override*/ int hashCode()
    {
        int hashCode = /*enum.hashCode()=(int)value*/Alignment;
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/TopSize);
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/BaseSize);
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/Distance);
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/Language;

        for (RubyChunk chunk : mTop)
            hashCode = (hashCode * 397) ^ chunk.hashCode();

        for (RubyChunk chunk : mBase)
            hashCode = (hashCode * 397) ^ chunk.hashCode();

        return hashCode;
    }

    Ruby deepClone()
    {
        Ruby clonedRuby = (Ruby)memberwiseClone();

        clonedRuby.mTop = getTop().deepClone();
        clonedRuby.mBase = getBase().deepClone();

        return clonedRuby;
    }

    /// <summary>
    /// Validates ruby and pushes extra text before <paramref name="rubyRun"/>.
    /// </summary>
    void validate(Run rubyRun)
    {
        String extraRun = cutToMaxLength();

        // Create extra run if needed.
        if (extraRun.length() > 0)
        {
            Run newRun = new Run(rubyRun.getDocument(), extraRun, AttrCollectionExtension.deepClone(rubyRun.getRunPr()));
            newRun.getRunPr().remove(FontAttr.RUBY);
            rubyRun.insertPrevious(newRun);
        }

        // AM. Word converts ruby back to equation field upon some conditions.
        // There is very strange behavior. I think that Word convert every ruby to equation field internally but
        // if ruby Top contains equation field Word cannot convert it back. I made tens of tests but still cannot get logic completely.
        // Put simple guard to EQ keyword to get ES customer satisfied.
        if (getTop().getText().contains("EQ"))
        {
            Node[] fieldNodes = RubyConverter.convertToFieldNodes(rubyRun);
            for (Node fieldNode : fieldNodes)
                rubyRun.insertPrevious(fieldNode);

            rubyRun.remove();
        }
    }

    /// <summary>
    /// Cuts Top and Base to maximum allowed and returns extra text.
    /// </summary>
    /// <remarks>
    /// AM. Very weird Word logic.
    /// Instead of just limiting Top/Base text it pushes extra characters to run before the ruby.
    /// </remarks>
    private String cutToMaxLength()
    {
        if (getBase().getText().length() <= MAX_LENGTH && getTop().getText().length() <= MAX_LENGTH)
            return "";

        String newTop = getTop().getText();
        String newBase = getBase().getText();
        String extraRun = "";
        int count = 0;
        if (getBase().getText().length() > MAX_LENGTH)
        {
            count = getBase().getText().length() - MAX_LENGTH;
            // Move extra characters to the end of Top string.
            newTop = newTop + getBase().getText().substring((0), (0) + (count));
            newBase = getBase().getText().substring(count);
        }

        if (newTop.length() > MAX_LENGTH)
        {
            count = newTop.length() - MAX_LENGTH;
        }

        extraRun = newTop.substring((0), (0) + (count));
        newTop = newTop.substring(count);

        RubyChunk newTopChunk = new RubyChunk();
        newTopChunk.Text = newTop;
        newTopChunk.RunPr = getTop().get(0).RunPr;

        RubyChunk newBaseChunk = new RubyChunk();
        newBaseChunk.Text = newBase;
        newBaseChunk.RunPr = getBase().get(0).RunPr;

        getTop().clear();
        getBase().clear();

        getTop().add(newTopChunk);
        getBase().add(newBaseChunk);

        return extraRun;
    }

    /*RubyAlignment*/int Alignment;
    int TopSize;
    int BaseSize;
    int Distance;
    /*Language*/int Language;

    RubyChunkCollection getTop() { return mTop; }

    RubyChunkCollection getBase() { return mBase; }

    /// <summary>
    /// Gets the text of this ruby in a form "base(top)".
    /// </summary>
    String getText()
    {
        return msString.format("{0}({1})", mBase.getText(), mTop.getText());
    }

    private RubyChunkCollection mTop = new RubyChunkCollection();
    private RubyChunkCollection mBase = new RubyChunkCollection();

    // Seems to be maximal allowed length of ruby parts.
    private static final int MAX_LENGTH = 30;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

