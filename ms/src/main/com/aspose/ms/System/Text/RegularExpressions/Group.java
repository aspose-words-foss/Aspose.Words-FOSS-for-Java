//14/07/2009 Konstantin Sidorenko

package com.aspose.ms.System.Text.RegularExpressions;

/**
 * Simplified copycat of .Net System.Text.RegularExpressions.Group.
 *
 * Only functionality used by Aspose.Words are implemented.
 */
public class Group
{
	public Group(String value, int start, boolean success)
	{
		mValue = value;
        mStart = start;
		mSuccess = success;
	}

	//internal. used by Match.groups() to return empty group.
	static Group EmptyGroup = new Group("", 0, false);

	public boolean getSuccess()
	{
		return mSuccess;
	}

	public String getValue()
	{
		return mValue;
	}

    public int getIndex()
    {
        return mStart;
    }

    public int getLength()
    {
        return mValue.length();
    }

	private boolean mSuccess;
	private String mValue;
    private int mStart;
}
