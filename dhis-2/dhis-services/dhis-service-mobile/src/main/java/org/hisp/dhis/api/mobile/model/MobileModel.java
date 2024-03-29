package org.hisp.dhis.api.mobile.model;

/*
 * Copyright (c) 2010, University of Oslo
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MobileModel
    implements DataStreamSerializable
{
    private String clientVersion;

    private ActivityPlan activityPlan;

    private List<Program> programs;

    private Date serverCurrentDate;

    private List<DataSet> datasets;

    private Collection<String> locales;

    public ActivityPlan getActivityPlan()
    {
        return activityPlan;
    }

    public void setActivityPlan( ActivityPlan activityPlan )
    {
        this.activityPlan = activityPlan;
    }

    public List<Program> getPrograms()
    {
        return programs;
    }

    public void setPrograms( List<Program> programs )
    {
        this.programs = programs;
    }

    public Date getServerCurrentDate()
    {
        return serverCurrentDate;
    }

    public void setServerCurrentDate( Date serverCurrentDate )
    {
        this.serverCurrentDate = serverCurrentDate;
    }

    public List<DataSet> getDatasets()
    {
        return datasets;
    }

    public void setDatasets( List<DataSet> datasets )
    {
        this.datasets = datasets;
    }

    public Collection<String> getLocales()
    {
        return locales;
    }

    public void setLocales( Collection<String> locales )
    {
        this.locales = locales;
    }

    public String getClientVersion()
    {
        return clientVersion;
    }

    public void setClientVersion( String clientVersion )
    {
        this.clientVersion = clientVersion;
    }

    @Override
    public void serialize( DataOutputStream dout )
        throws IOException
    {
        if ( this.getClientVersion().equals( DataStreamSerializable.TWO_POINT_EIGHT ) )
        {
            this.serializeVerssion2_8( dout );
        }
        else
        {
            this.serializeVerssion2_9( dout );
        }
    }

    @Override
    public void deSerialize( DataInputStream dataInputStream )
        throws IOException
    {

    }

    @Override
    public void serializeVerssion2_8( DataOutputStream dout )
        throws IOException
    {
        if ( programs == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            dout.writeInt( programs.size() );

            for ( Program prog : programs )
            {
                prog.serializeVerssion2_8( dout );
            }
        }

        // Write ActivityPlans
        if ( this.activityPlan == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            this.activityPlan.serializeVerssion2_8( dout );
        }

        // Write current server's date
        dout.writeLong( serverCurrentDate.getTime() );

        // Write DataSets
        if ( datasets == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            dout.writeInt( datasets.size() );
            for ( DataSet ds : datasets )
            {
                ds.serializeVerssion2_8( dout );
            }
        }

        // Write Locales
        if ( locales == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            dout.writeInt( locales.size() );
            for ( String locale : locales )
            {
                dout.writeUTF( locale );
            }
        }
    }

    @Override
    public void serializeVerssion2_9( DataOutputStream dout )
        throws IOException
    {
        if ( programs == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            dout.writeInt( programs.size() );

            for ( Program prog : programs )
            {
                prog.serializeVerssion2_9( dout );
            }
        }

        // Write ActivityPlans
        if ( this.activityPlan == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            this.activityPlan.serializeVerssion2_9( dout );
        }

        // Write current server's date
        dout.writeLong( serverCurrentDate.getTime() );

        // Write DataSets
        if ( datasets == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            dout.writeInt( datasets.size() );
            for ( DataSet ds : datasets )
            {
                ds.serializeVerssion2_9( dout );
            }
        }

        // Write Locales
        if ( locales == null )
        {
            dout.writeInt( 0 );
        }
        else
        {
            dout.writeInt( locales.size() );
            for ( String locale : locales )
            {
                dout.writeUTF( locale );
            }
        }
    }

}
