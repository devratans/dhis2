/*
 * Copyright (c) 2004-2009, University of Oslo
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

package org.hisp.dhis.patient.scheduling;

import java.util.Map;

/**
 * @author Chau Thu Tran
 *
 * @version CaseAggregateConditionSchedulingManager.java 10:42:58 AM Oct 10, 2012 $
 */
public interface CaseAggregateConditionSchedulingManager
{
    final String TASK_AGGREGATE_QUERY_BUILDER_LAST_12_MONTHS = "aggregateLast12MonthsTask";
    final String TASK_AGGREGATE_QUERY_BUILDER_LAST_6_MONTS = "aggregateLast6MonthsTask";
    final String TASK_AGGREGATE_QUERY_BUILDER_FROM_6_TO_12_MONTS = "aggregateFrom6To12MonthsTask";
    
    void scheduleTasks();
    
    void scheduleTasks( Map<String, String> keyCronMap );
    
    void stopTasks();
    
    void executeTasks();
    
    Map<String, String> getScheduledTasks();
    
    String getTaskStatus();   
}
