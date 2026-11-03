package com.aspose;

public class ImageProcessingInterruptException extends IllegalStateException
{
    public ImageProcessingInterruptException(String message)
    {
        super(message);
    }

    public ImageProcessingInterruptException()
    {
        super(EXCEPTION_MESSAGE);
    }

    private static final String EXCEPTION_MESSAGE = "Image processing interrupted because com.aspose.words.NativeLibSettings.getInterruptThreadIfImageExceptionThrown() is true";
}
