package org.hisp.dhis.system.grid;

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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hisp.dhis.common.Grid;
import org.hisp.dhis.common.GridHeader;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lars Helge Overland
 * @version $Id$
 */
public class GridTest
{
    private Grid grid;
    
    private GridHeader headerA;
    private GridHeader headerB;
    private GridHeader headerC;
    
    @Before
    public void setUp()
    {
        grid = new ListGrid();
        
        headerA = new GridHeader( "ColA", "colA", String.class.getName(), false, false );
        headerB = new GridHeader( "ColB", "colB", String.class.getName(), false, false );
        headerC = new GridHeader( "ColC", "colC", String.class.getName(), true, false );
        
        grid.addHeader( headerA );
        grid.addHeader( headerB );
        grid.addHeader( headerC );
        
        grid.addRow();        
        grid.addValue( 11 );
        grid.addValue( 12 );
        grid.addValue( 13 );

        grid.addRow();        
        grid.addValue( 21 );
        grid.addValue( 22 );
        grid.addValue( 23 );

        grid.addRow();        
        grid.addValue( 31 );
        grid.addValue( 32 );
        grid.addValue( 33 );

        grid.addRow();        
        grid.addValue( 41 );
        grid.addValue( 42 );
        grid.addValue( 43 );
    }
    
    @Test
    public void testGetHeight()
    {
        assertEquals( 4, grid.getHeight() );
    }
    
    @Test
    public void testGetWidth()
    {
        assertEquals( 3, grid.getWidth() );
    }
        
    @Test
    public void testGetRow()
    {
        List<Object> rowA = grid.getRow( 0 );
        
        assertTrue( rowA.size() == 3 );
        assertTrue( rowA.contains( 11 ) );
        assertTrue( rowA.contains( 12 ) );
        assertTrue( rowA.contains( 13 ) );
        
        List<Object> rowB = grid.getRow( 1 );
        
        assertTrue( rowB.size() == 3 );
        assertTrue( rowB.contains( 21 ) );
        assertTrue( rowB.contains( 22 ) );
        assertTrue( rowB.contains( 23 ) );
    }

    @Test
    public void testGetHeaders()
    {
        assertEquals( 3, grid.getHeaders().size() );
    }
    
    @Test
    public void tetsGetVisibleHeaders()
    {
        assertEquals( 2, grid.getVisibleHeaders().size() );
        assertTrue( grid.getVisibleHeaders().contains( headerA ) );
        assertTrue( grid.getVisibleHeaders().contains( headerB ) );
    }

    @Test
    public void testGetRows()
    {
        assertEquals( 4, grid.getRows().size() );
        assertEquals( 3, grid.getWidth() );
    }

    @Test
    public void testGetGetVisibleRows()
    {
        assertEquals( 4, grid.getVisibleRows().size() );
        assertEquals( 2, grid.getVisibleRows().get( 0 ).size() );
        assertEquals( 2, grid.getVisibleRows().get( 1 ).size() );
        assertEquals( 2, grid.getVisibleRows().get( 2 ).size() );
        assertEquals( 2, grid.getVisibleRows().get( 3 ).size() );
    }
    
    @Test
    public void testGetColumn()
    {        
        List<Object> column1 = grid.getColumn( 1 );
        
        assertEquals( 4, column1.size() );
        assertTrue( column1.contains( 12 ) );
        assertTrue( column1.contains( 22 ) );
        assertTrue( column1.contains( 32 ) );
        assertTrue( column1.contains( 42 ) );

        List<Object> column2 = grid.getColumn( 2 );
        
        assertEquals( 4, column2.size() );
        assertTrue( column2.contains( 13 ) );
        assertTrue( column2.contains( 23 ) );
        assertTrue( column2.contains( 33 ) );
        assertTrue( column2.contains( 43 ) );
    }
    
    @Test
    public void testAddColumn()
    {
        List<Object> columnValues = new ArrayList<Object>();
        columnValues.add( 14 );
        columnValues.add( 24 );
        columnValues.add( 34 );
        columnValues.add( 44 );
        
        grid.addColumn( columnValues );
        
        List<Object> column3 = grid.getColumn( 3 );
        
        assertEquals( 4, column3.size() );
        assertTrue( column3.contains( 14 ) );
        assertTrue( column3.contains( 24 ) );
        assertTrue( column3.contains( 34 ) );
        assertTrue( column3.contains( 44 ) );
        
        List<Object> row2 = grid.getRow( 1 );
        
        assertEquals( 4, row2.size() );
        assertTrue( row2.contains( 21 ) );
        assertTrue( row2.contains( 22 ) );
        assertTrue( row2.contains( 23 ) );
        assertTrue( row2.contains( 24 ) );
    }
    
    @Test
    public void testRemoveColumn()
    {
        assertEquals( 3, grid.getWidth() );
        
        grid.removeColumn( 2 );
        
        assertEquals( 2, grid.getWidth() );
    }
    
    @Test
    public void testRemoveColumnByHeader()
    {
        assertEquals( 3, grid.getWidth() );
        
        grid.removeColumn( headerB );
        
        assertEquals( 2, grid.getWidth() );
    }

    @Test
    public void testLimit()
    {
        assertEquals( 4, grid.getRows().size() );
        
        grid.limitGrid( 2 );
        
        assertEquals( 2, grid.getRows().size() );
        
        List<Object> rowA = grid.getRow( 0 );
        assertTrue( rowA.contains( 11 ) );

        List<Object> rowB = grid.getRow( 1 );        
        assertTrue( rowB.contains( 21 ) );
        
        grid.limitGrid( 0 );
        
        assertEquals( 2, grid.getRows().size() );
    }
    
    @Test
    public void testLimitShortList()
    {
        assertEquals( 4, grid.getRows().size() );
        
        grid.limitGrid( 6 );
        
        assertEquals( 4, grid.getRows().size() );

        grid.limitGrid( 4 );
        
        assertEquals( 4, grid.getRows().size() );
    }
    
    @Test
    public void testLimits()
    {
        assertEquals( 4, grid.getRows().size() );
        
        grid.limitGrid( 1, 3 );
        
        assertEquals( 2, grid.getRows().size() );

        List<Object> rowA = grid.getRow( 0 );
        assertTrue( rowA.contains( 21 ) );

        List<Object> rowB = grid.getRow( 1 );        
        assertTrue( rowB.contains( 31 ) );        
    }
    
    @Test
    public void testSortA()
    {
        Grid grid = new ListGrid();
        
        grid.addRow().addValue( 1 ).addValue( "a" );
        grid.addRow().addValue( 2 ).addValue( "b" );
        grid.addRow().addValue( 3 ).addValue( "c" );
        
        grid.sortGrid( 2, 1 );

        List<Object> row1 = grid.getRow( 0 );
        assertTrue( row1.contains( "c" ) );

        List<Object> row2 = grid.getRow( 1 );
        assertTrue( row2.contains( "b" ) );
        
        List<Object> row3 = grid.getRow( 2 );
        assertTrue( row3.contains( "a" ) );
    }

    @Test
    public void testSortB()
    {
        Grid grid = new ListGrid();
        
        grid.addRow().addValue( 3 ).addValue( "a" );
        grid.addRow().addValue( 2 ).addValue( "b" );
        grid.addRow().addValue( 1 ).addValue( "c" );
        
        grid.sortGrid( 1, -1 );

        List<Object> row1 = grid.getRow( 0 );
        assertTrue( row1.contains( 1 ) );

        List<Object> row2 = grid.getRow( 1 );
        assertTrue( row2.contains( 2 ) );
        
        List<Object> row3 = grid.getRow( 2 );
        assertTrue( row3.contains( 3 ) );       
    }

    @Test
    public void testSortC()
    {
        Grid grid = new ListGrid();

        grid.addRow().addValue( 1 ).addValue( "c" );
        grid.addRow().addValue( 3 ).addValue( "a" );
        grid.addRow().addValue( 2 ).addValue( "b" );
        
        grid.sortGrid( 1, 1 );

        List<Object> row1 = grid.getRow( 0 );
        assertTrue( row1.contains( 3 ) );

        List<Object> row2 = grid.getRow( 1 );
        assertTrue( row2.contains( 2 ) );
        
        List<Object> row3 = grid.getRow( 2 );
        assertTrue( row3.contains( 1 ) );
    }
    
    @Test
    public void testSortD()
    {
        Grid grid = new ListGrid();
        
        grid.addRow().addValue( "a" ).addValue( "a" ).addValue( 5.2 );
        grid.addRow().addValue( "b" ).addValue( "b" ).addValue( 0.0 );
        grid.addRow().addValue( "c" ).addValue( "c" ).addValue( 108.1 );
        grid.addRow().addValue( "d" ).addValue( "d" ).addValue( 45.0 );
        grid.addRow().addValue( "e" ).addValue( "e" ).addValue( 4043.9 );
        grid.addRow().addValue( "f" ).addValue( "f" ).addValue( 0.1 );
        
        grid = grid.sortGrid( 3, 1 );
        
        List<Object> row1 = grid.getRow( 0 );
        assertTrue( row1.contains( 4043.9 ) );

        List<Object> row2 = grid.getRow( 1 );
        assertTrue( row2.contains( 108.1 ) );
        
        List<Object> row3 = grid.getRow( 2 );
        assertTrue( row3.contains( 45.0 ) );

        List<Object> row4 = grid.getRow( 3 );
        assertTrue( row4.contains( 5.2 ) );

        List<Object> row5 = grid.getRow( 4 );
        assertTrue( row5.contains( 0.1 ) );

        List<Object> row6 = grid.getRow( 5 );
        assertTrue( row6.contains( 0.0 ) );    
    }

    @Test
    public void testSortE()
    {
        Grid grid = new ListGrid();

        grid.addRow().addValue( "two" ).addValue( 2 );
        grid.addRow().addValue( "null" ).addValue( null );
        grid.addRow().addValue( "three" ).addValue( 3 );
        
        grid.sortGrid( 2, 1 );

        List<Object> row1 = grid.getRow( 0 );
        assertTrue( row1.contains( "three" ) );

        List<Object> row2 = grid.getRow( 1 );
        assertTrue( row2.contains( "two" ) );
        
        List<Object> row3 = grid.getRow( 2 );
        assertTrue( row3.contains( "null" ) );
    }

    @Test
    public void testSortF()
    {
        Grid grid = new ListGrid();

        grid.addRow().addValue( "two" ).addValue( 2 );
        grid.addRow().addValue( "null" ).addValue( null );
        grid.addRow().addValue( "one" ).addValue( 1 );
        
        grid.sortGrid( 2, -1 );

        List<Object> row1 = grid.getRow( 0 );
        assertTrue( row1.contains( "null" ) );
        
        List<Object> row2 = grid.getRow( 1 );
        assertTrue( row2.contains( "one" ) );

        List<Object> row3 = grid.getRow( 2 );
        assertTrue( row3.contains( "two" ) );        
    }
    
    @Test
    public void testGridRowComparator()
    {
        List<List<Object>> lists = new ArrayList<List<Object>>();
        List<Object> l1 = getList( "b", "b", 50 );
        List<Object> l2 = getList( "c", "c", 400 );
        List<Object> l3 = getList( "a", "a", 6 );
        lists.add( l1 );
        lists.add( l2 );
        lists.add( l3 );
        
        Comparator<List<Object>> comparator = new ListGrid.GridRowComparator( 2, -1 );
        Collections.sort( lists, comparator );
                
        assertEquals( l3, lists.get( 0 ) );
        assertEquals( l1, lists.get( 1 ) );
        assertEquals( l2, lists.get( 2 ) );
    }
    
    @Test
    public void testAddRegressionColumn()
    {
        grid = new ListGrid();

        grid.addRow();        
        grid.addValue( 10.0 );
        grid.addRow();        
        grid.addValue( 50.0 );
        grid.addRow();        
        grid.addValue( 20.0 );
        grid.addRow();        
        grid.addValue( 60.0 );
        
        grid.addRegressionColumn( 0, true );
        
        List<Object> column = grid.getColumn( 1 );
        
        assertTrue( column.size() == 4 );
        assertTrue( column.contains( 17.0 ) );
        assertTrue( column.contains( 29.0 ) );
        assertTrue( column.contains( 41.0 ) );
        assertTrue( column.contains( 53.0 ) );
    }
    
    @Test
    public void testAddCumulativeColumn()
    {
        grid = new ListGrid();

        grid.addRow();        
        grid.addValue( 10.0 );
        grid.addRow();        
        grid.addValue( 50.0 );
        grid.addRow();        
        grid.addValue( 20.0 );
        grid.addRow();        
        grid.addValue( 60.0 );
        
        grid.addCumulativeColumn( 0, true );

        List<Object> column = grid.getColumn( 1 );
        
        assertTrue( column.size() == 4 );
        assertTrue( column.contains( 10.0 ) );
        assertTrue( column.contains( 60.0 ) );
        assertTrue( column.contains( 80.0 ) );
        assertTrue( column.contains( 140.0 ) );
    }

    @Test
    public void testJRDataSource() throws Exception
    {
        assertTrue( grid.next() );
        assertEquals( 11, grid.getFieldValue( new MockJRField( "colA" ) ) );
        assertEquals( 12, grid.getFieldValue( new MockJRField( "colB" ) ) );
        assertEquals( 13, grid.getFieldValue( new MockJRField( "colC" ) ) );

        assertTrue( grid.next() );
        assertEquals( 21, grid.getFieldValue( new MockJRField( "colA" ) ) );
        assertEquals( 22, grid.getFieldValue( new MockJRField( "colB" ) ) );
        assertEquals( 23, grid.getFieldValue( new MockJRField( "colC" ) ) );

        assertTrue( grid.next() );
        assertEquals( 31, grid.getFieldValue( new MockJRField( "colA" ) ) );
        assertEquals( 32, grid.getFieldValue( new MockJRField( "colB" ) ) );
        assertEquals( 33, grid.getFieldValue( new MockJRField( "colC" ) ) );

        assertTrue( grid.next() );
        assertEquals( 41, grid.getFieldValue( new MockJRField( "colA" ) ) );
        assertEquals( 42, grid.getFieldValue( new MockJRField( "colB" ) ) );
        assertEquals( 43, grid.getFieldValue( new MockJRField( "colC" ) ) );
        
        assertFalse( grid.next() );
    }
    
    private static List<Object> getList( Object... items )
    {
        List<Object> list = new ArrayList<Object>();
        
        for ( Object item : items )
        {
            list.add( item );
        }
        
        return list;
    }
}
