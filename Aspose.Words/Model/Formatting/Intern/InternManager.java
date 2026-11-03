// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import java.util.Map;
import java.util.HashMap;


/// <summary>
/// Implements interning for attribute collections <see cref="AttrCollection" />.
/// </summary>
/// <remarks>
/// Attribute interning is a process similar to C# string interning. If equal attribute collections are added to the intern manager only one copy 
/// of actual attribute values remains in memory. First added collection become <see cref="InternState.Pooled" /> and holds actual values, other collections
/// are cleared and go to <see cref-="InternState.Interned" /> state.
/// 
/// Although idea is clear there are many cases that is needed to be carefully analyzed, for example, complex attributes, cloning, etc. 
/// That's the reason we decided to try very limited implementation first. Most important thing is that collections can be interned only during saving and 
/// this eliminates cases when customer can change collection or complex attribute value and we don't need to handle whole document life cycle.
/// </remarks>
class InternManager extends DocumentVisitor
{
    /// <summary>
    /// Specifies InternManager working mode.
    /// </summary>
    private /*enum*/ final class Mode
    {
        private Mode(){}
        
        /// <summary>
        /// InternaManager interns attributes currently.
        /// </summary>
        public static final int ADD = 0;

        /// <summary>
        /// InternaManager un-interns attributes currently.
        /// </summary>
        public static final int REMOVE = 1;

        public static final int length = 2;
    }

    //>>>>>>>> #region  DocumentVisitor

    public /*override*/ /*VisitorAction*/int visitDocumentStart(Document doc)
    {
        for (Style style : doc.getStyles())
        {
            doc.InternManager.process(style.getRunPr());

            if(style.getType() == StyleType.PARAGRAPH)
                doc.InternManager.process(style.getParaPr());

            if(style.getType() == StyleType.TABLE)
                doc.InternManager.process(((TableStyle)style).getTablePr());
        }

        for (ListDef listDef : doc.getLists().getListDefs())
            for (ListLevel level : listDef.getLevels())
            {
                doc.InternManager.process(level.getRunPr());
                doc.InternManager.process(level.getParaPr());
            }
        
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para)
    {
        para.getDocument().InternManager.process(para.getParaPr());
        para.getDocument().InternManager.process(para.getParagraphBreakRunPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRowStart(Row row)
    {
        row.getDocument().InternManager.process(row.getTablePr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell)
    {
        cell.getDocument().InternManager.process(cell.getCellPr());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run)
    {
        run.getDocument().InternManager.process(run.getRunPr());
        return VisitorAction.CONTINUE;
    }

    //<<<<<<<< #endregion  DocumentVisitor

    /// <summary>
    /// Interns <see cref="InternManager" /> whole document.
    /// </summary>
    static void add(DocumentBase doc) throws Exception
    {
        if (doc.InternManager == null)
            doc.InternManager = new InternManager();

        doc.InternManager.mMode = Mode.ADD;
        doc.accept(doc.InternManager);
    }

    /// <summary>
    /// Un-interns whole document.
    /// </summary>
    static void remove(DocumentBase doc) throws Exception
    {
        if (doc.InternManager == null)
            return;

        doc.InternManager.mMode = Mode.REMOVE;
        doc.accept(doc.InternManager);

        doc.InternManager = null;
    }

    /// <summary>
    /// Interns <see cref="InternManager" /> attribute collection if possible.
    /// </summary>
    void add(AttrCollection pr)
    {
        CalledCount++;

        // Do not intern empty collections.
        if (pr.getCount() == 0)
        {
            EmptyCount++;
            return;
        }

        // Do not intern already interned collection.
        if (pr.getInternState() != InternState.NONE)
        {
            RepeatedCount++;
            return;
        }

        boolean hasComplexAttrs = false;
        for (int i = 0; i < pr.getCount(); i++)
        {
            Object srcValue = pr.getByIndex(i);

            if (srcValue instanceof IComplexAttr)
            {
                hasComplexAttrs = true;

                // Don't know how to handle inherited values.
                if ((!(srcValue instanceof InternableComplexAttr)) || ((IComplexAttr)srcValue).isInheritedComplexAttr())
                {
                    ComplexCount++;
                    return;
                }
            }
        }

        if(hasComplexAttrs)
            ComplexCount++;

        InternPoolItem poolItem = DictionaryExtension.getValueOrNull(mPool, pr);

        // Attach all complex attrs to collection.
        for (int i = 0; i < pr.getCount(); i++)
        {
            Object srcValue = pr.getByIndex(i);
            InternableComplexAttr internable = ms.as(srcValue, InternableComplexAttr.class);
            
            if (internable != null)
                internable.attach(pr);
        }

        if (poolItem == null)
        {
            poolItem = new InternPoolItem(this);
            poolItem.Pr = pr;
            poolItem.Id = Id++;
            msDictionary.add(mPool, pr, poolItem);

            PooledCount++;

            Debug.writeLineIf(mPool.size() % 1000 == 0, msString.format("Pool size: {0}", mPool.size()));
        }
        else
        {
            poolItem.RefCount++;
            pr.clear();

            InternedCount++;
        }

        pr.setPoolItem(poolItem);

    }

    /// <summary>
    /// Un-intern attribute collection.
    /// </summary>
    void remove(AttrCollection pr)
    {
        switch (pr.getInternState())
        {
            case InternState.INTERNED:
                unIntern(pr);
                break;
            case InternState.POOLED:
                unPool(pr);
                break;
            case InternState.NONE:
                // Do nothing
                break;
            default:
                Debug.assertTrue(false, "Unexpected value.");
                break;
        }
    }

    private void process(AttrCollection pr)
    {
        if (mMode == Mode.ADD)
        {
            add(pr);
        }
        else
        {
            remove(pr);
        }
    }

    private void unIntern(AttrCollection pr)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	UnInternedCount++;

        	InternPoolItem poolItem = pr.getPoolItem();
        	poolItem.RefCount--;

        	pr.setPoolItem(null);

        	for (int i = 0; i < poolItem.Pr.getCount(); i++)
        	{
            	int key = poolItem.Pr.getKey(i);
            	Object srcValue = poolItem.Pr.getByIndex(i);

            	if (srcValue instanceof InternableComplexAttr)
            	{
                	Debug.assertTrue(srcValue instanceof IComplexAttr);

                	IComplexAttr complexAttr = (IComplexAttr)srcValue;
                	if (!complexAttr.isInheritedComplexAttr())
                	{
                    	Object dstValue = complexAttr.deepCloneComplexAttr();
                    	((InternableComplexAttr)dstValue).detach();
                    	pr.set(key, dstValue);
                	}
            	}
            	else
            	{
                	// We should not have IComplexAttr which are not inherited from InternableComplexAttr.
                	Debug.assertTrue(!(srcValue instanceof IComplexAttr));

                	pr.set(key, srcValue);
            	}
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    private void unPool(AttrCollection pr)
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	UnPooledCount++;
        	Debug.assertTrue(pr.getInternState() == InternState.POOLED);

        	InternPoolItem poolItem = DictionaryExtension.getValueOrNull(mPool, pr);
        	pr.setPoolItem(null);
        	msDictionary.remove(mPool, pr);

        	if (poolItem.RefCount == 0)
        	{
            	// This is only instance and we can easily unpool this item.
        	}
        	else
        	{
            	// We need to preserve attributes in pool.
            	AttrCollection newPr = AttrCollectionExtension.deepClone(pr);

            	poolItem.Pr = newPr;
            	msDictionary.add(mPool, newPr, poolItem);
            	newPr.setPoolItem(poolItem);
        	}
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    Map<AttrCollection, InternPoolItem> getPool() { return mPool; }

    /// <summary>
    /// Counts how many collections tried to be interned.
    /// </summary>
    int CalledCount;

    /// <summary>
    /// Counts how many collections were interned.
    /// </summary>
    int InternedCount;

    /// <summary>
    /// Counts how many collections were pooled.
    /// </summary>
    int PooledCount;

    /// <summary>
    /// Shows how many collections with complex attributes tried to be interned.
    /// </summary>
    int ComplexCount;
    
    /// <summary>
    /// Counts how many empty collections tried to be interned.
    /// </summary>
    int EmptyCount;
    
    /// <summary>
    /// Counts how many already interned collections tried to be interned.
    /// </summary>
    int RepeatedCount;

    /// <summary>
    /// Counts how many collections where un-pooled.
    /// </summary>
    int UnPooledCount;
    
    /// <summary>
    /// Counts how many collections where un-interned.
    /// </summary>
    int UnInternedCount;

    int Id = 1;

    private /*Mode*/int mMode;

    private /*final*/ HashMap<AttrCollection, InternPoolItem> mPool = 
        new HashMap<AttrCollection, InternPoolItem>();
}

