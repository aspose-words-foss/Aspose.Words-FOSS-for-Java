// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.IntToObjDictionary;
import java.util.Iterator;


/// <summary>
/// Represents collection of shape effects.
/// </summary>
class DmlShapeEffectsCollection implements Iterable<DmlShapeEffect>, Cloneable
{
    DmlShapeEffectsCollection()
    {
    	this(false, false);
	
    }

    DmlShapeEffectsCollection(boolean isTheme, boolean isEffectDag)
    {
        mIsTheme = isTheme;
        mIsEffectDag = isEffectDag;
    }

    DmlShapeEffectsCollection deepClone()
    {
        DmlShapeEffectsCollection lhs = (DmlShapeEffectsCollection)memberwiseClone();

        lhs.mEffects = new IntToObjDictionary<DmlShapeEffect>();
        for (DmlShapeEffect srcEffect : mEffects.getValues())
            lhs.addEffect(srcEffect.deepClone());

        return lhs;
    }

    /// <summary>
    /// Add effect to the collection.
    /// </summary>
    void addEffect(DmlShapeEffect effect)
    {
        if (effect != null)
            mEffects.set((int)effect.getEffectType(), effect);
    }

    /// <summary>
    /// Removes effect from the collection.
    /// </summary>
    void removeEffect(/*DmlShapeEffectType*/int type)
    {
        mEffects.remove((int)type);
    }

    public Iterator<DmlShapeEffect> iterator()
    {
        return new EffectsEnumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Returns effect of the specified type from the collection.
    /// </summary>
    DmlShapeEffect getByDmlShapeEffectType(/*DmlShapeEffectType*/int effectType) { return mEffects.get((int)effectType); }

    int getCount() { return mEffects.getCount(); }

    /// <summary>
    /// Flag indicates that collection represents effects applied through document theme.
    /// </summary>
    boolean isTheme() { return mIsTheme; }
    void isTheme(boolean value) { mIsTheme = value; }

    /// <summary>
    /// Flag indicates that effects are applied in the order specified by the container type <see cref="DagType"/>.
    /// </summary>
    boolean isEffectDag() { return mIsEffectDag; }
    /// <summary>
    /// Specifies an optional name for this DAG list of effects, so that it can be referred to later.
    /// Must be unique across all effect trees and effect containers.
    /// </summary>
    String getDagName() { return mDagName; }
    void setDagName(String value) { mDagName = value; }

    /// <summary>
    /// Specifies the type of DAG list of effects, either sibling or tree.
    /// Default is <see cref="EffectDagType.Sibling"/>
    /// </summary>
    /*EffectDagType*/int getDagType() { return mDagType; }
    void setDagType(/*EffectDagType*/int value) { mDagType = value; }

    private IntToObjDictionary<DmlShapeEffect> mEffects = new IntToObjDictionary<DmlShapeEffect>();
    private boolean mIsTheme;
    private /*final*/ boolean mIsEffectDag;
    private String mDagName;
    private /*EffectDagType*/int mDagType;

    /// <summary>
    /// Class wrapper for Enumerator.
    /// We cannot use mEffects.GetEnumerator() directly because it returns instance of <see cref="IntToObjDictionary{T}.Enumerator"/>,
    /// but we need <see cref="IEnumerator{T}"/>.
    /// </summary>
    private final static class EffectsEnumerator implements Iterator<DmlShapeEffect>
    {
        public EffectsEnumerator(DmlShapeEffectsCollection collection)
        {
            mEnumerator = collection.mEffects.iterator();
        }

        public boolean hasNext()
        {
            return mEnumerator.moveNext();
        }

        public void reset()
        {
            mEnumerator.reset();
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        public DmlShapeEffect next() { return mEnumerator.getCurrentValue(); }

        private /*final*/ IntToObjDictionary<DmlShapeEffect>.Enumerator mEnumerator;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

