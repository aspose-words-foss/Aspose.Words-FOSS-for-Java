// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 02.11.2013 by Konstantin Sidorenko

package com.aspose.ms.System.Drawing;

import java.awt.*;

/**
 * Java implementation of System.Drawing.SystemColors.
 *
 * Each property of the class is the color of a Windows display element.
 */
public final class SystemColors
{
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the active window's border.
    /// </summary>
    public static Color getActiveBorder() { return SystemColor.activeCaptionBorder; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background of the active window's title bar.
    /// </summary>
    public static Color getActiveCaption() { return SystemColor.activeCaption; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the text in the active window's title bar.
    /// </summary>
    public static Color getActiveCaptionText() { return SystemColor.activeCaptionText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the application workspace.
    /// </summary>
    public static Color getAppWorkspace() { return SystemColor.window; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the face color of a 3-D element.
    /// </summary>
    public static Color getButtonFace() { return SystemColor.control; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the highlight color of a 3-D element.
    /// </summary>
    public static Color getButtonHighlight() { return SystemColor.controlLtHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the shadow color of a 3-D element.
    /// </summary>
    public static Color getButtonShadow() { return SystemColor.controlShadow; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the face color of a 3-D element.
    /// </summary>
    public static Color getControl() { return SystemColor.control; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the shadow color of a 3-D element.
    /// </summary>
    public static Color getControlDark() { return SystemColor.controlShadow; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the dark shadow color of a 3-D element.
    /// </summary>
    public static Color getControlDarkDark() { return SystemColor.controlDkShadow; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the light color of a 3-D element.
    /// </summary>
    public static Color getControlLight() { return SystemColor.controlHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the highlight color of a 3-D element.
    /// </summary>
    public static Color getControlLightLight() { return SystemColor.controlLtHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of text in a 3-D element.
    /// </summary>
    public static Color getControlText() { return SystemColor.controlText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the desktop.
    /// </summary>
    public static Color getDesktop() { return SystemColor.desktop; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the lightest color in the color gradient of an active window's title bar.
    /// </summary>
    public static Color getGradientActiveCaption() { return SystemColor.activeCaption; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the lightest color in the color gradient of an inactive window's title bar.
    /// </summary>
    public static Color getGradientInactiveCaption() { return SystemColor.inactiveCaption; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of dimmed text.
    /// </summary>
    public static Color getGrayText() { return SystemColor.textInactiveText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background of selected items.
    /// </summary>
    public static Color getHighlight() { return SystemColor.textHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the text of selected items.
    /// </summary>
    public static Color getHighlightText() { return SystemColor.textHighlightText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color used to designate a hot-tracked item.
    /// </summary>
    public static Color getHotTrack() { return SystemColor.controlLtHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of an inactive window's border.
    /// </summary>
    public static Color getInactiveBorder() { return SystemColor.inactiveCaptionBorder; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background of an inactive window's title bar.
    /// </summary>
    public static Color getInactiveCaption() { return SystemColor.inactiveCaption; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the text in an inactive window's title bar.
    /// </summary>
    public static Color getInactiveCaptionText() { return SystemColor.inactiveCaptionText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background of a ToolTip.
    /// </summary>
    public static Color getInfo() { return SystemColor.text; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the text of a ToolTip.
    /// </summary>
    public static Color getInfoText() { return SystemColor.textText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of a menu's background.
    /// </summary>
    public static Color getMenu() { return SystemColor.menu; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background of a menu bar.
    /// </summary>
    public static Color getMenuBar() { return SystemColor.controlHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color used to highlight menu items when the menu appears as a flat menu.
    /// </summary>
    public static Color getMenuHighlight() { return SystemColor.controlLtHighlight; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of a menu's text.
    /// </summary>
    public static Color getMenuText() { return SystemColor.menuText; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background of a scroll bar.
    /// </summary>
    public static Color getScrollBar() { return SystemColor.scrollbar; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the background in the client area of a window.
    /// </summary>
    public static Color getWindow() { return SystemColor.window; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of a window frame.
    /// </summary>
    public static Color getWindowFrame() { return SystemColor.windowBorder; }
    /// <summary>
    /// Gets a <see cref="T:System.Drawing.Color"/> structure that is the color of the text in the client area of a window.
    /// </summary>
    public static Color getWindowText() { return SystemColor.windowText; }
}
