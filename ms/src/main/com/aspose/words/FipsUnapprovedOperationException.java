package com.aspose.words;

/**
 * Represents the exception that is thrown when incorrectly trying to use cryptography.
 */
public class FipsUnapprovedOperationException extends RuntimeException
{
    public FipsUnapprovedOperationException(String e)
    {
        super(e);
    }
}
