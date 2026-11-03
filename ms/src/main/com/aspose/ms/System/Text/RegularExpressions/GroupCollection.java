package com.aspose.ms.System.Text.RegularExpressions;

import java.util.regex.Matcher;

/**
 * Simplified copycat of .Net System.Text.RegularExpressions.GroupCollection.
 *
 * Only methods used by Aspose.Words are implemented.
 */
public class GroupCollection
{
    GroupCollection(Match parent)
    {
        mParent = parent;
    }

    public Group get(int index)
    {
        String result = null;
        int start = 0;

        if (mParent.getSuccess() && (index >= 0) && (index < getCount()))
        {
            Matcher matcher = mParent.toJava();
            result = matcher.group(index); //java returns null here if unsuccessful.
            start = matcher.start(index);
        }

        if (result == null)
            return Group.EmptyGroup;
        else
            return new Group(result, start, true);
    }

    public int getCount()
    {
        // FIX WORDSJAVA-2238.
        // Java always counts capturing groups, even if there is no match.
        // But .NET doesn't count and returns one (the entire pattern).
        if (!mParent.getSuccess())
            return 1;

        // FIX WORDSJAVA-753.
        // Group zero denotes the entire pattern by convention.
        // Java ignores this group when it returns count of groups, but .NET doesn't.
        return mParent.toJava().groupCount() + 1;
    }

    private Match mParent;
}
