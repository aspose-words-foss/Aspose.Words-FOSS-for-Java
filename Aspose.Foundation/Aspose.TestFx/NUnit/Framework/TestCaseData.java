package com.aspose.ms.NUnit.Framework;

/**
 * Ported version of NUnit.Framework.TestCaseData
 * Created by nile on 16.08.2014.
 */
public class TestCaseData
{
    public TestCaseData(Object ... args)
    {
        arguments = args;
    }

    public Object[] getArguments()
    {
        return arguments;
    }

    public Object getResult()
    {
        return result;
    }

    public TestCaseData returns(Object result)
    {
        this.result = result;
        return this;
    }

    public TestCaseData setName(String name)
    {
        this.setTestName(name);
        return this;
    }

    public String getTestName()
    {
        return mTestName;
    }

    public void setTestName(String value)
    {
        mTestName = value;
    }

    private final Object[] arguments;
    private Object result;
    private String mTestName;
}
