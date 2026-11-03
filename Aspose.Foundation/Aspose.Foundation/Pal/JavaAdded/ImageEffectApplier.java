package com.aspose;

import com.aspose.ms.System.Drawing.Drawing2D.WrapMode;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.image.ColorModel;
import java.util.HashMap;

class ImageEffectApplier
{

    static BufferedImage flip(BufferedImage src, int wrapMode)
    {
        BufferedImage resultImage = null;
        // apply flipping
        switch (wrapMode)
        {
            case (WrapMode.TILE_FLIP_XY):
                resultImage = flipXY(src);
                break;
            case (WrapMode.TILE_FLIP_X):
                resultImage = flipX(src);
                break;
            case (WrapMode.TILE_FLIP_Y):
                resultImage = flipY(src);
                break;
            case (WrapMode.CLAMP):
                // @nix: We handle CLAMP mode later, see TransformablePaint.
            default:
                // WrapMode.TILE
                resultImage = src;
        }
        return resultImage;
    }


    private static BufferedImage flipX(BufferedImage src)
    {
        // Flip the image horizontally
        // transform.scale(-1.0, 1.0);
        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-src.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage xFlippedImage = op.filter(src, null);
        BufferedImage resultImage = new BufferedImage(src.getWidth() * 2, src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) resultImage.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.drawImage(xFlippedImage, src.getWidth(), 0, null);
        g.dispose();
        if (src.getColorModel() != null && src.getColorModel().isCompatibleRaster(resultImage.getRaster()))
            return new BufferedImage(src.getColorModel(), resultImage.getRaster(), false, null);

        return resultImage;
    }

    private static BufferedImage flipY(BufferedImage src)
    {
        // Flip the image vertically
        //transform.scale(1.0, -1.0);
        AffineTransform yat = AffineTransform.getScaleInstance(1, -1);
        yat.translate(0, -src.getHeight(null));
        AffineTransformOp yop = new AffineTransformOp(yat, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage yFlippedImage = yop.filter(src, null);
        BufferedImage resultImage = new BufferedImage(src.getWidth(), src.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) resultImage.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.drawImage(yFlippedImage, 0, src.getHeight(), null);
        g.dispose();
        if (src.getColorModel() != null && src.getColorModel().isCompatibleRaster(resultImage.getRaster()))
            return new BufferedImage(src.getColorModel(), resultImage.getRaster(), false, null);

        return resultImage;
    }

    private static BufferedImage flipXY(BufferedImage src)
    {
        BufferedImage resultImage = flipX(src);
        resultImage = flipY(resultImage);
        return resultImage;
    }


}
