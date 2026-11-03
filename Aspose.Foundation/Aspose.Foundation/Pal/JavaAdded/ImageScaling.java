// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 23/07/2013 by Pavel Gorbunov
// 15/09/2014 modified by Nail Islamov
// 13/10/2014 modified by Pavel Gorbunov
// 04/08/2025 modified by Alexey Maslov

package com.aspose;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Perform scaling images.
 * Basic idea is described in http://stackoverflow.com/questions/875856/interpolation-algorithms-when-downscaling?rq=1
 * and http://stackoverflow.com/questions/384991/what-is-the-best-image-downscaling-algorithm-quality-wise.
 * However there are few modifications.
 */
public class ImageScaling
{
	// Scale bitmap
	public static BufferedImage getArgbScaledInstance(BufferedImage image, final int targetWidth, final int targetHeight)
	{
		// Use idea from http://stackoverflow.com/questions/384991/what-is-the-best-image-downscaling-algorithm-quality-wise
		// with few modifications.
		// Pixel is considered as a square region of some RGB color.
		// For every pixel in target picture, calculate appropriate region in the source picture.
		// Tha target pixel may cover one or more source pixels.
		// In other words the source pixels split region of target pixel into few rectangular regions of different colors.
		// We calculate these colors and fraction of area of each region in total area of target pixel.
		// To get the color of the target pixel, we calculate the average of these colors, considering their areas.
		// So, if region of a target pixel consists of:
		// 1/2 of a yellow region (R=255, G=255, B=0),
		// 1/3 of a green region (R=0, G=255, B=0),
		// 1/6 of a gray region (R=127, G=127, B=127),
		// then target pixel has the following color components:
		// R = 1/2*255 + 1/3*0 + 1/6*127,
		// G = 1/2*255 + 1/3*255 + 1/6*127,
		// B = 1/2*0 + 1/3*0 + 1/6*127,

		final int imageWidth = image.getWidth();
		final int imageHeight = image.getHeight();

		// FIX WORDSJAVA-931: Use raw data buffer to improve performance
        final PixelExtractor extractor = getSuitableExtractor(image);

		BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

		// FIX WORDSJAVA-931: Use raw data buffer to improve performance
		final int[] resultData = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();

        final float tileWidth = ((float) imageWidth) / targetWidth;
        final float tileHeight = ((float) imageHeight) / targetHeight;
        final float tileSquare = ((float) (imageWidth * imageHeight) / (targetWidth * targetHeight));

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = (targetHeight + numThreads - 1) / numThreads;

        List<Runnable> tasks = new ArrayList<>(numThreads);
        for (int i = 0; i < numThreads; i++)
        {
            final int startRow = i * chunkSize;
            final int endRow = Math.min((i + 1) * chunkSize, targetHeight);

            tasks.add(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int y = startRow; y < endRow; y++)
                    {
                        int tmp = y * targetWidth;
                        float tileTop = y * tileHeight;
                        float tileTopPlushHeight = tileTop + tileHeight;
                        float tileBottom = Math.min(tileTopPlushHeight, imageHeight);

                        for (int x = 0; x < targetWidth; x++)
                        {
                            float tileLeft = x * tileWidth;
                            float tileLeftPlusWidth = tileLeft + tileWidth;
                            float tileRight = Math.min(tileLeftPlusWidth, imageWidth);
                            resultData[tmp + x] = calculateColor(extractor, tileLeft, tileTop, tileRight, tileBottom, tileSquare);
                        }
                    }
                }
            });
        }

        for (Runnable task : tasks)
            executor.execute(task);

        executor.shutdown();

        try
        {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while scaling image", e);
        }

		return result;
	}

    /**
	 * Calculate RGB color of target pixel region.
	 * The coordinate system is related to the source image.
	 * @param extractor
	 * @param left X-coordinate of the top-left corner of the target pixel region.
	 * @param top Y-coordinate of the top-left corner of the target pixel region.
	 * @param right X-coordinate of the right-bottom corner of the target pixel region.
	 * @param bottom Y-coordinate of the right-bottom corner of the target pixel region.
	 */
    private static int calculateColor(PixelExtractor extractor, float left, float top, float right, float bottom, float square)
    {
        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;
        float a = 0.0f;

        int pX, pY;
        int pixel;
        float nextX, nextY, tmp, squareFraction;
        float x;
        float y = top;
        float squareAlpha = square;

        // Calculate area of each rectangular region which is covered by the target pixel region.
        // Calculate RGB components based on fraction of those areas in total area of the target pixel region.
        while (y < bottom)
        {
            pY = (int) y;
            float pY1 = pY + 1;
            nextY = (pY1 <= bottom) ? pY1 : bottom; //min(pY + 1, bottom);
            tmp = (nextY - y);

            x = left;
            while (x < right)
            {
                pX = (int) x;

                float pX1 = pX + 1;
                nextX = (pX1 <= right) ? pX1 : right; //min(pX + 1, right);
                squareFraction = (nextX - x) * tmp;
                pixel = extractor.getPixel(pX, pY);

                int pA = ((pixel >>> 24) & 0xFF);
                int pR = ((pixel >>> 16) & 0xFF);
                int pG = ((pixel >>> 8) & 0xFF);
                int pB = (pixel & 0xFF);

                if (pA < 255)
                {
                    // Apply alpha
                    float visibility = pA / 255.0f;

                    a += pA * squareFraction;
                    r += pR * visibility * squareFraction;
                    g += pG * visibility * squareFraction;
                    b += pB * visibility * squareFraction;
                    square -= squareFraction * (1 - visibility);

                }
                else
                {
                    // Optimization if there is no alpha
                    a += pA * squareFraction;
                    r += pR * squareFraction;
                    g += pG * squareFraction;
                    b += pB * squareFraction;
                }

                x = nextX;
            }

            y = nextY;
        }

        a /= squareAlpha;
        r /= square;
        g /= square;
        b /= square;

        return roundColor(a) << 24 | roundColor(r) << 16 | roundColor(g) << 8 | roundColor(b);
    }

    private static int roundColor(float c)
    {
        return Math.min(Math.round(c), 255);
    }

    private static PixelExtractor getSuitableExtractor(BufferedImage image)
    {
        switch (image.getType())
        {
            case BufferedImage.TYPE_INT_ARGB:
                return new IntArgbPixelExtractor(image);

            case BufferedImage.TYPE_INT_RGB:
                return new IntRgbPixelExtractor(image);

            case BufferedImage.TYPE_INT_BGR:
                return new IntBgrPixelExtractor(image);

            case BufferedImage.TYPE_3BYTE_BGR:
                return new ThreeByteBgrPixelExtractor(image);

            case BufferedImage.TYPE_4BYTE_ABGR:
                return new FourByteAbgrPixelExtractor(image);

            case BufferedImage.TYPE_BYTE_BINARY:
            case BufferedImage.TYPE_BYTE_INDEXED:
                return new ByteIndexedPixelExtractor(image);

            default:
                // FIX WORDSJAVA-931: It's not guaranteed that input image has TYPE_INT_ARGB color model
                // So, if not, convert it to TYPE_INT_ARGB for later raw data access
                image = PalBitmapCore.convertTo(image, BufferedImage.TYPE_INT_ARGB);
                return new IntArgbPixelExtractor(image);
        }
    }
}

interface PixelExtractor
{
    int getPixel(int x, int y);
}

final class ByteIndexedPixelExtractor implements PixelExtractor
{
    ByteIndexedPixelExtractor(BufferedImage image)
    {
        mWidth = image.getWidth();
        mData = (byte[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
        IndexColorModel icm = (IndexColorModel)image.getColorModel();
        mColorMap = new int[icm.getMapSize()];
        icm.getRGBs(mColorMap);
    }

    public final int getPixel(int x, int y)
    {
        return mColorMap[mData[y * mWidth + x] & 0xFF];
    }

    private final byte[] mData;
    private final int[] mColorMap;
    private final int mWidth;
}

final class IntArgbPixelExtractor implements PixelExtractor
{
    IntArgbPixelExtractor(BufferedImage image)
    {
        mWidth = image.getWidth();
        mData = (int[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
    }

    public final int getPixel(int x, int y)
    {
        return mData[y * mWidth + x];
    }

    private final int[] mData;
    private final int mWidth;
}

final class IntRgbPixelExtractor implements PixelExtractor
{
    IntRgbPixelExtractor(BufferedImage image)
    {
        mWidth = image.getWidth();
        mData = (int[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
    }

    public final int getPixel(int x, int y)
    {
        // Set alpha channel to max value
        return mData[y * mWidth + x] | (0xFF << 24);
    }

    private final int[] mData;
    private final int mWidth;
}

final class IntBgrPixelExtractor implements PixelExtractor
{
    IntBgrPixelExtractor(BufferedImage image)
    {
        mWidth = image.getWidth();
        mData = (int[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
    }

    public final int getPixel(int x, int y)
    {
        // Set alpha channel to max value and flip R and B
        int pixel = mData[y * mWidth + x];
        int b = ((pixel >>> 16) & 0xFF);
        int g = ((pixel >>> 8) & 0xFF);
        int r = (pixel & 0xFF);
        return 0xFF << 24 | r << 16 | g << 8 | b;
    }

    private final int[] mData;
    private final int mWidth;
}

final class ThreeByteBgrPixelExtractor implements PixelExtractor
{
    ThreeByteBgrPixelExtractor(BufferedImage image)
    {
        mWidth = image.getWidth();
        mData = (byte[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
    }

    public final int getPixel(int x, int y)
    {
        int offset = (y * mWidth + x) * 3;
        int r = mData[offset] & 0xFF;
        int g = mData[offset + 1] & 0xFF;
        int b = mData[offset + 2] & 0xFF;
        return 0xFF << 24 | r << 16 | g << 8 | b;
    }

    private final byte[] mData;
    private final int mWidth;
}

final class FourByteAbgrPixelExtractor implements PixelExtractor
{
    FourByteAbgrPixelExtractor(BufferedImage image)
    {
        mWidth = image.getWidth();
        mData = (byte[])image.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
    }

    public final int getPixel(int x, int y)
    {
        int offset = (y * mWidth + x) * 4;
        int r = mData[offset] & 0xFF;
        int g = mData[offset + 1] & 0xFF;
        int b = mData[offset + 2] & 0xFF;
        int a = mData[offset + 3] & 0xFF;
        return a << 24 | r << 16 | g << 8 | b;
    }

    private final byte[] mData;
    private final int mWidth;
}