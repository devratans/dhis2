package org.hisp.dhis.common;

/*
 * Copyright (c) 2004-2012, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the HISP project nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.Collection;

/**
 * @author Lars Helge Overland
 */
public interface GenericStore<T>
{
    /**
     * Class of the object for this store.
     */
    Class<T> getClazz();
    
    /**
     * Saves the given object instance.
     * 
     * @param object the object instance.
     * @return the generated identifier.
     */
    int save( T object );

    /**
     * Updates the given object instance.
     * 
     * @param object the object instance.
     */
    void update( T object );

    /**
     * Saves or updates the given object instance depending on the object's
     * persistent state.
     * 
     * @param object the object instance.
     */
    void saveOrUpdate( T object );

    /**
     * Retrieves the object with the given identifier. This method will first
     * look in the current Session, then hit the database if not existing.
     * 
     * @param id the object identifier.
     * @return the object identified by the given identifier.
     */
    T get( int id );

    /**
     * Retrieves the object with the given identifier, assuming it exists.
     * 
     * @param id the object identifier.
     * @return the object identified by the given identifier or a generated
     *         proxy.
     */
    T load( int id );
    
    /**
     * Retrieves a Collection of objects where the name is like the given name.
     * 
     * @param name the name.
     * @return a Collection of objects.
     */
    Collection<T> getLikeName( String name );

    /**
     * Retrieves a Collection of all objects.
     * 
     * @return a Collection of all objects.
     */
    Collection<T> getAll();

    /**
     * Retrieves a Collection of all objects (sorted on name).
     *
     * @return a Collection of all objects.
     */
    Collection<T> getAllSorted();

    /**
     * Removes the given object instance.
     * 
     * @param object the object instance to delete.
     */
    void delete( T object );
    
    /**
     * Gets the count of objects.
     * 
     * @return the count of objects.
     */
    int getCount();
}
