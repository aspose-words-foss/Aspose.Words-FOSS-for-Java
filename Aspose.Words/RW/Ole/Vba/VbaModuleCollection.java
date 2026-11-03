// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2019 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DictionaryExtension;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import com.aspose.SortedStringListGeneric;


/// <summary>
/// Represents a collection of <see cref="VbaModule"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-vba-macros/">Working with VBA Macros</a> documentation article.</para>
/// </summary>
public final class VbaModuleCollection implements Iterable<VbaModule>
{
    VbaModuleCollection(VbaProject project)
    {
        mProject = project;
    }

    /// <summary>
    /// Adds a module to the collection.
    /// </summary>
    public void add(VbaModule vbaModule)
    {
        addInternal(vbaModule);
        mProject.markModified();
    }

    /// <summary>
    /// Retrieves a <see cref="VbaModule"/> object by index.
    /// </summary>
    /// <param name="index">Zero-based index of the module to retrieve.</param>
    public VbaModule get(int index) { return mModules.get(index); }

    /// <summary>
    /// Retrieves a <see cref="VbaModule"/> object by name, or Null if not found.
    /// </summary>        
    public VbaModule get(String name) { return DictionaryExtension.getValueOrNull(mModulesByName, name); }


    /// <summary>
    /// Returns the number of VBA modules in the collection.
    /// </summary>
    public int getCount() { return mModules.size(); }

    /// <summary>
    /// Removes the specified module from the collection.
    /// </summary>
    /// <param name="module">The module to remove.</param>
    public void remove(VbaModule module)
    {
        if (module == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "module");

        if (mModules.indexOf(module) >= 0)
        {
            mModulesByName.remove(module.getName());
            mModules.remove(module);

            mProject.markModified();
        }
        else
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "module");
    }

    /// <summary>
    /// Removes all modules from the collection.
    /// </summary>
    void clear()
    {
        mModulesByName.clear();
        mModules.clear();
    }

    /// <summary>
    /// Makes a copy of the object.
    /// </summary>
    VbaModuleCollection deepClone(VbaProject project)
    {
        VbaModuleCollection lhs = new VbaModuleCollection(project);

        for (VbaModule module : mModules)
            lhs.addInternal(module.deepClone());

        return lhs;
    }

    /// <summary>
    /// Adds a module to collection without clearing storage. 
    /// </summary>        
    void addInternal(VbaModule vbaModule)
    {
        if (!StringUtil.hasChars(vbaModule.getName()))
            throw new IllegalArgumentException("A module name must be specified before adding to the VBA Project.");

        if (mModulesByName.containsKey(vbaModule.getName()))
            throw new IllegalArgumentException("Cannot add a VbaModule because a module with the same name already exists.");

        vbaModule.setProject(mProject);
        msArrayList.add(mModules, vbaModule);
        mModulesByName.add(vbaModule.getName(), vbaModule);
    }

    @ReservedForInternalUse @Deprecated
    public Iterator<VbaModule> /*IEnumerable<VbaModule>.*/iterator()
    {
        return mModules.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    private /*final*/ ArrayList<VbaModule> mModules = new ArrayList<VbaModule>();

    /// <summary>
    /// A map of modules sorted by name. 
    /// </summary>
    private /*final*/ SortedStringListGeneric<VbaModule> mModulesByName = new SortedStringListGeneric<VbaModule>();

    /// <summary>
    /// Parent VBA project.
    /// </summary>
    private /*final*/ VbaProject mProject;
}

