package com.aspose;

import com.aspose.ms.System.Drawing.msSize;
import com.aspose.ms.java.debug.JavaDebug;

import static java.lang.String.format;

/**
 * This class creates ImageReader which can fallback to the next reader in the chain if the current reader cannot perform the reading.
 * Not thread-safe.
 */
public class ImageReaderBuilder extends ChainBuilder<ImageReader>
{
    public ImageReaderBuilder withTWM()
    {
        items.add(new TWMReader());
        return this;
    }

    public ImageReaderBuilder withImageIo()
    {
        items.add(new ImageIoReader());
        return this;
    }

    public ImageReader build()
    {
        if (items.isEmpty())
        {
            if (JavaDebug.ON)
                System.out.println("There are no available readers. ImageIO is used by default");

            items.add(new ImageIoReader());
        }

        return (ImageReader) buildFromChainResetItems();
    }

    public static class Options
    {
        private Integer inFormat;
        private boolean isCmyk;
        private boolean emulateRasterOperations;
        private long scaledSize;
        private int maxWidth;
        private double horizontalResolution = ImageConstants.STANDARD_RESOLUTION;
        private double verticalResolution = ImageConstants.STANDARD_RESOLUTION;
        private IFontProvider fontProvider;

        public Integer inFormat()
        {
            return inFormat;
        }

        public Options withInFormat(Integer inFormat)
        {
            this.inFormat = inFormat;
            return this;
        }

        public boolean isCmyk()
        {
            return isCmyk;
        }

        public Options withIsCmyk(boolean isCmyk)
        {
            this.isCmyk = isCmyk;
            return this;
        }

        public boolean emulateRasterOperations()
        {
            return emulateRasterOperations;
        }

        public Options withEmulateRasterOperations(boolean emulateRasterOperations)
        {
            this.emulateRasterOperations = emulateRasterOperations;
            return this;
        }

        public long scaledSize()
        {
            return scaledSize;
        }

        public Options withScaledSize(/*msSize*/long scaledSize)
        {
            requireNonNegativeParameterMsSize("scaledSize", scaledSize);
            this.scaledSize = scaledSize;
            return this;
        }

        public int maxWidth()
        {
            return maxWidth;
        }

        public Options withMaxWidth(int maxWidth)
        {
            requireNonNegativeParameter("maxWidth", maxWidth);
            this.maxWidth = maxWidth;
            return this;
        }

        public Options withFontProvider(IFontProvider fontProvider)
        {
            this.fontProvider = fontProvider;
            return this;
        }

        public IFontProvider fontProvider()
        {
            return fontProvider;
        }

        public Options withHorizontalResolution(double res)
        {
            this.horizontalResolution = res;
            return this;
        }

        public Options withVerticalResolution(double res)
        {
            this.verticalResolution = res;
            return this;
        }

        public double horizontalResolution()
        {
            return horizontalResolution;
        }

        public double verticalResolution()
        {
            return verticalResolution;
        }

        private static void requireNonNegativeParameter(String parameterName, long parameterValue)
        {
            if (parameterValue < 0)
            {
                String message = format("Parameter %s should be non-negative but %d received", parameterName, parameterValue);
                throw new IllegalArgumentException(message);
            }
        }

        private static void requireNonNegativeParameterMsSize(String name, /*msSize*/long value)
        {
            if (msSize.getWidth(value) < 0 || msSize.getHeight(value) < 0)
            {
                String message = format("msSize parameter %s should be non-negative but received width: %d, height: %d",
                        name, msSize.getWidth(value), msSize.getHeight(value));

                throw new IllegalArgumentException(message);
            }
        }
    }

}
