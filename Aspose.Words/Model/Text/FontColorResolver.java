// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Resolves font auto color into black or white depending on the shading color.
/// </summary>
class FontColorResolver
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FontColorResolver()
    {
        // Lets mShadingStack.Peek() be always valid.
        mShadingStack.push(null);
    }

    void pushShading(Shading shading)
    {
        mShadingStack.push(isVisible(shading) ? shading : mShadingStack.peek());
    }

    void popShading()
    {
        mShadingStack.pop();
    }

    /// <summary>
    /// Resolves automatic font color to black or white depending on the current background.
    /// </summary>
    DrColor resolveFontColor(DrColor color)
    {
        // If there is no background, assume it is white paper, make the color black.
        return resolveFontColor(color, DrColor.BLACK);
    }

    /// <summary>
    /// Resolves automatic font color to black or white depending on the current background.
    /// If there is no background returns <paramref name="defaultColor"/>
    /// </summary>
    DrColor resolveFontColor(DrColor color, DrColor defaultColor)
    {
        if (!color.isEmpty())
            return color;

        Shading shading = mShadingStack.peek();

        // At the moment only Shading objects can be on the stack, but later
        // I might have backgrounds of VML shapes on the stack too.

        // If there is no background, use default color.
        return (shading == null) ? defaultColor : resolveAutoFontColorOnShading(shading);
    }

    /// <summary>
    /// Returns true if the specified shading is visible for rendering, i.e. is not the same as its background shading.
    /// </summary>
    boolean isRenderShading(Shading shading, boolean optimizeOutput)
    {
        // WORDSNET-15199 Skip rendering shading of background color only if SAveOptions.OptimizeOutput flag is set.
        if (!optimizeOutput)
            return true;

        if (!isVisible(shading))
            return false;

        // Always return true if current shading has texture.
        if (((shading.getTexture() != TextureIndex.TEXTURE_NONE && shading.getTexture() != TextureIndex.TEXTURE_NIL)))
            return true;

        // This is supposed to be the same shading as passed as parameter.
        // Keep it to restore stack when finish processing.
        Shading currentShading = StackExtension.top(mShadingStack);

        popShading();

        Shading backShading = StackExtension.top(mShadingStack);

        boolean result;
        // If background shading has texture, the passes shading must be rendered.
        if ((backShading != null) &&
            (backShading.getTexture() != TextureIndex.TEXTURE_NONE) &&
            (backShading.getTexture() != TextureIndex.TEXTURE_NIL))
        {
            result = true;
        }
        else
        {
            DrColor backColor = (backShading != null) ? backShading.getBackgroundPatternColorInternal() : G_PAPER_COLOR;
            result = !DrColor.equals(backColor, shading.getBackgroundPatternColorInternal());
        }

        // Restore current shading.
        mShadingStack.push(currentShading);

        return result;
    }

    /// <summary>
    /// In Microsoft Word, auto background color is resolved into "paper" or window background color.
    /// </summary>
    static DrColor resolveBackgroundPatternColor(DrColor color)
    {
        return (color.isEmpty()) ? G_PAPER_COLOR : color;
    }

    /// <summary>
    /// Auto foreground color of the texture seems to be always black in Microsoft Word.
    /// </summary>
    static DrColor resolveForegroundPatternColor(DrColor color)
    {
        return (color.isEmpty()) ? DrColor.BLACK : color;
    }

    /// <summary>
    /// Returns the color of the font (black or white) to be used on the given shading.
    /// You need to call this only if your font color is auto color.
    /// </summary>
    /// <param name="shading">The background texture and color. Must not be <c>null</c>.</param>
    /// <returns>Black or white font color.</returns>
    static DrColor resolveAutoFontColorOnShading(Shading shading)
    {
        /*TextureIndex*/int texture = shading.getTexture();

        if ((texture == TextureIndex.TEXTURE_NONE) || (texture == TextureIndex.TEXTURE_NIL))
        {
            if (shading.getBackgroundPatternColorInternal().isEmpty())
            {
                // There is no texture and no background color,
                // so the background is assumed white paper and the font is black.
                return DrColor.BLACK;
            }
            else
            {
                // There is no texture, but background color is specified, it results in a solid background.
                float luminance = shading.getBackgroundPatternColorInternal().getLuminance();
                return getAutoColorFromBackLuminance(luminance);
            }
        }
        else if (WordUtil.isComplexTexture(texture))
        {
            // The pattern is a texture (not a percent fill) and from what I can see
            // Microsoft Word always uses black font color on it regardless of the pattern colors.
            return DrColor.BLACK;
        }
        else
        {
            // The pattern is a percent fill.
            float fillDensity = (float)WordUtil.getFillingLevelByTextureIndex(texture);
            // Automatic fill color is resolved to black in MS Word.
            DrColor actualFillColor = resolveForegroundPatternColor(shading.getForegroundPatternColorInternal());
            float foreLuminance = actualFillColor.getLuminance() * fillDensity;

            // For example, if fill density is 80% then background density is 20%.
            float backDensity = 1.0f - fillDensity;
            // In Microsoft Word, the automatic background color is resolved to "paper" color.
            DrColor actualBackColor = resolveBackgroundPatternColor(shading.getBackgroundPatternColorInternal());
            float backLuminance = actualBackColor.getLuminance() * backDensity;

            // It is a percent shading. The appropriate parts of luminance of background
            // and foreground color are combined to create the total luminance of the background.
            float luminance = foreLuminance + backLuminance;
            return getAutoColorFromBackLuminance(luminance);
        }
    }

    private static DrColor getAutoColorFromBackLuminance(float luminance)
    {
        return (luminance > BACKGROUND_LUMINANCE_THRESHOLD) ? DrColor.BLACK : DrColor.WHITE;
    }

    /// <summary>
    /// Checks that shading is visible. Returns <c>false</c> for <c>null</c> parameter.
    /// </summary>
    private static boolean isVisible(Shading shading)
    {
        return (shading != null) && (shading.isVisible());
    }

    private /*final*/ msStack<Shading> mShadingStack = new msStack<Shading>();

    /// <summary>
    /// Auto font color is black on backgrounds that are brighter than this threshold.
    /// Auto font color is white on backgrounds that are darker than this threshold.
    /// Found this value experimentally in MS Word. It is close, but not EXACTLY like MS Word.
    /// It is still possible to come up with colors where incorrect font color will be selected.
    ///
    /// The current value is relevant for MS Word 2016 and later versions.
    /// Earlier versions used ~0.2383f as a luminance threshold.
    ///
    /// See https://www.w3.org/TR/AERT/#color-contrast for more info about luminance calculation.
    /// </summary>
    private static final float BACKGROUND_LUMINANCE_THRESHOLD = 0.2345f;

    /// <summary>
    /// Automatic background pattern color is resolved into paper color in Microsoft Word.
    /// </summary>
    private static /*final*/ DrColor G_PAPER_COLOR = DrColor.WHITE;
}


