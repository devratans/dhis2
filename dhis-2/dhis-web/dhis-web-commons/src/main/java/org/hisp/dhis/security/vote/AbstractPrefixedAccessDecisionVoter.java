package org.hisp.dhis.security.vote;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: AbstractPrefixedAccessDecisionVoter.java 3160 2007-03-24 20:15:06Z torgeilo $
 */
public abstract class AbstractPrefixedAccessDecisionVoter
    implements AccessDecisionVoter<Object>
{
    private static final Log LOG = LogFactory.getLog( AbstractPrefixedAccessDecisionVoter.class );

    // -------------------------------------------------------------------------
    // Prefix
    // -------------------------------------------------------------------------

    protected String attributePrefix = "";

    public void setAttributePrefix( String attributePrefix )
    {
        this.attributePrefix = attributePrefix;
    }

    // -------------------------------------------------------------------------
    // AccessDecisionVoter implementation
    // -------------------------------------------------------------------------

    public boolean supports( ConfigAttribute configAttribute )
    {
        boolean result = configAttribute.getAttribute() != null
            && configAttribute.getAttribute().startsWith( attributePrefix );

        LOG.debug( "Supports configAttribute: " + configAttribute + ", " + result + " (" + getClass().getSimpleName()
            + ")" );

        return result;
    }
}
