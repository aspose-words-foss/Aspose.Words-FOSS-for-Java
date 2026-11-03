// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2023 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import com.aspose.words.ref.Ref;


/// <summary>
/// Represents bibliography source contributors.
/// </summary>
public final class ContributorCollection implements Iterable<Contributor>
{
    ContributorCollection()
    {
    }

    void setContributor(/*ContributorType*/int type, Contributor contributor)
    {
        if (contributor != null)
            mContributors.put(type, contributor);
        else
            msDictionary.remove(mContributors, type);
    }

    /// <summary>
    /// Gets or sets the artist of a source.
    /// </summary>
    public Contributor getArtist() { return tryGetContributor(ContributorType.ARTIST); }
    public void setArtist(Contributor value) { setContributor(ContributorType.ARTIST, value); }

    /// <summary>
    /// Gets or sets the author of a source.
    /// </summary>
    public Contributor getAuthor() { return tryGetContributor(ContributorType.AUTHOR); }
    public void setAuthor(Contributor value) { setContributor(ContributorType.AUTHOR, value); }

    /// <summary>
    /// Gets or sets the book author of a source.
    /// </summary>
    public Contributor getBookAuthor() { return tryGetContributor(ContributorType.BOOK_AUTHOR); }
    public void setBookAuthor(Contributor value) { setContributor(ContributorType.BOOK_AUTHOR, value); }

    /// <summary>
    /// Gets or sets the compiler of a source.
    /// </summary>
    public Contributor getCompiler() { return tryGetContributor(ContributorType.COMPILER); }
    public void setCompiler(Contributor value) { setContributor(ContributorType.COMPILER, value); }

    /// <summary>
    /// Gets or sets the composer of a source.
    /// </summary>
    public Contributor getComposer() { return tryGetContributor(ContributorType.COMPOSER); }
    public void setComposer(Contributor value) { setContributor(ContributorType.COMPOSER, value); }

    /// <summary>
    /// Gets or sets the conductor of a source.
    /// </summary>
    public Contributor getConductor() { return tryGetContributor(ContributorType.CONDUCTOR); }
    public void setConductor(Contributor value) { setContributor(ContributorType.CONDUCTOR, value); }

    /// <summary>
    /// Gets or sets the counsel of a source.
    /// </summary>
    public Contributor getCounsel() { return tryGetContributor(ContributorType.COUNSEL); }
    public void setCounsel(Contributor value) { setContributor(ContributorType.COUNSEL, value); }

    /// <summary>
    /// Gets or sets the director of a source.
    /// </summary>
    public Contributor getDirector() { return tryGetContributor(ContributorType.DIRECTOR); }
    public void setDirector(Contributor value) { setContributor(ContributorType.DIRECTOR, value); }

    /// <summary>
    /// Gets or sets the editor of a source.
    /// </summary>
    public Contributor getEditor() { return tryGetContributor(ContributorType.EDITOR); }
    public void setEditor(Contributor value) { setContributor(ContributorType.EDITOR, value); }

    /// <summary>
    /// Gets or sets the interviewee of a source.
    /// </summary>
    public Contributor getInterviewee() { return tryGetContributor(ContributorType.INTERVIEWEE); }
    public void setInterviewee(Contributor value) { setContributor(ContributorType.INTERVIEWEE, value); }

    /// <summary>
    /// Gets or sets the interviewer of a source.
    /// </summary>
    public Contributor getInterviewer() { return tryGetContributor(ContributorType.INTERVIEWER); }
    public void setInterviewer(Contributor value) { setContributor(ContributorType.INTERVIEWER, value); }

    /// <summary>
    /// Gets or sets the inventor of a source.
    /// </summary>
    public Contributor getInventor() { return tryGetContributor(ContributorType.INVENTOR); }
    public void setInventor(Contributor value) { setContributor(ContributorType.INVENTOR, value); }

    /// <summary>
    /// Gets or sets the performer of a source.
    /// </summary>
    public Contributor getPerformer() { return tryGetContributor(ContributorType.PERFORMER); }
    public void setPerformer(Contributor value) { setContributor(ContributorType.PERFORMER, value); }

    /// <summary>
    /// Gets or sets the producer of a source.
    /// </summary>
    public Contributor getProducer() { return tryGetContributor(ContributorType.PRODUCER); }
    public void setProducer(Contributor value) { setContributor(ContributorType.PRODUCER, value); }

    /// <summary>
    /// Gets or sets the translator of a source.
    /// </summary>
    public Contributor getTranslator() { return tryGetContributor(ContributorType.TRANSLATOR); }
    public void setTranslator(Contributor value) { setContributor(ContributorType.TRANSLATOR, value); }

    /// <summary>
    /// Gets or sets the writer of a source.
    /// </summary>
    public Contributor getWriter() { return tryGetContributor(ContributorType.WRITER); }
    public void setWriter(Contributor value) { setContributor(ContributorType.WRITER, value); }

    Contributor getContributor(/*ContributorType*/int type)
    {
        return mContributors.get(type);
    }

    private Contributor tryGetContributor(/*ContributorType*/int type)
    {
        Contributor contributor = null;
        Ref<Contributor> referenceToContributor = new Ref<Contributor>(contributor);
        msDictionary.tryGetValue(mContributors, type, /*out*/ referenceToContributor);
        contributor = referenceToContributor.get();
        return contributor;
    }

    ContributorCollection deepClone()
    {
        ContributorCollection lhs = new ContributorCollection();

        for (/*ContributorType*/int contributorType : (Iterable</*ContributorType*/Integer>) getContributorTypes())
            lhs.setContributor(contributorType, getContributor(contributorType).deepClone());

        return lhs;
    }

    boolean isEmpty() { return mContributors.size() == 0; }

    Iterable<Integer> getContributorTypes() { return mContributors.keySet(); }

    @ReservedForInternalUse @Deprecated
    public Iterator<Contributor> /*IEnumerable<Contributor>.*/iterator()
    {
        return mContributors.values().iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ Map<Integer, Contributor> mContributors = new HashMap<Integer, Contributor>();
}

