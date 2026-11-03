// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


class WindowsComparersFactory extends AbstractComparerFactory
{
    public /*override*/ AbstractGoldComparer getZipComparer() { return G_ZIP_COMPARER; }
    public /*override*/ AbstractGoldComparer getXpsComparer() { return G_XPS_COMPARER; }
    public /*override*/ AbstractGoldComparer getTextComparer() { return G_TEXT_COMPARER; }
    public /*override*/ AbstractGoldComparer getEmfComparer() { return G_EMF_COMPARER; }
    public /*override*/ AbstractGoldComparer getMhtmlComparer() { return G_MHTML_COMPARER; }
    public /*override*/ AbstractGoldComparer getImageComparer() { return G_IMAGE_COMPARER; }
    public /*override*/ AbstractGoldComparer getWoffComparer() { return G_WOFF_COMPARER; }
    public /*override*/ AbstractGoldComparer getEpsComparer() { return G_EPS_COMPARER; }

    // FOSS

    private static /*final*/ AbstractGoldComparer G_ZIP_COMPARER = new ZipFileComparer();
    private static /*final*/ AbstractGoldComparer G_XPS_COMPARER = new XpsComparer();
    private static /*final*/ AbstractGoldComparer G_TEXT_COMPARER = new TextFileComparer();
    private static /*final*/ AbstractGoldComparer G_EMF_COMPARER = new EmfComparer();
    private static /*final*/ AbstractGoldComparer G_MHTML_COMPARER = new MhtmlFileComparer();
    private static /*final*/ AbstractGoldComparer G_IMAGE_COMPARER = new ImageFileComparer();
    private static /*final*/ AbstractGoldComparer G_WOFF_COMPARER = new WoffComparer();
    private static /*final*/ AbstractGoldComparer G_EPS_COMPARER = new EpsComparer();
    // FOSS
}

