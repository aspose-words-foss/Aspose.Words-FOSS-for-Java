package com.aspose.ms.System.Text.RegularExpressions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Simplified copycat of .Net System.Text.RegularExpressions.MatchCollection.
 *
 * Only methods used by Aspose.Words are implemented.
 */
public class MatchCollection implements Iterable<Match>
{
    void add(Match match)
    {
        mMatches.add(match);
    }

    public int getCount()
    {
        return mMatches.size();
    }

    public Match get(int index)
    {
        return mMatches.get(index);
    }

    public Iterator<Match> iterator()
    {
        return mMatches.iterator();
    }
    
    private ArrayList<Match> mMatches = new ArrayList<Match>();
}
