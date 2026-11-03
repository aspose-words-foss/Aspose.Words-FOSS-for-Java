package com.aspose.ms.System.Diagnostics;

public class DefaultTraceListener extends TraceListener
{
    @Override
    public String getName()
    {
        return "Default";
    }

    @Override
    public void writeLine(String message)
    {
        writeLine(message, null);
    }

    @Override
    public void writeLine(String message, String category)
    {
        writeIndent();
        writeCategory(category);
        System.out.println(message);
    }

    @Override
    public void write(String message)
    {
        write(message, null);
    }

    @Override
    public void write(String message, String category)
    {
        writeIndent();
        writeCategory(category);
        System.out.print(message);
    }

    @Override
    public void writeIndent()
    {
        System.out.print(getIndent());
    }

    /**
     * Gets or sets a value indicating whether the application is running in user-interface mode.
     * Not implemented.
     */
    public void setAssertUiEnabled(boolean value)
    {
        // not implemented.
    }

    private void writeCategory(String category)
    {
        if (category != null && !category.isEmpty())
        {
            System.out.print(category);
            System.out.print(": ");
        }
    }
}
