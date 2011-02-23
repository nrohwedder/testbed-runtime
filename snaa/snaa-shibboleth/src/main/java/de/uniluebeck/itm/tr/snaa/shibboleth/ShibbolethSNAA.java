/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                 *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote*
 *   products derived from this software without specific prior written permission.                                   *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/

package de.uniluebeck.itm.tr.snaa.shibboleth;

import eu.wisebed.shibboauth.SSAKSerialization;
import eu.wisebed.shibboauth.ShibbolethAuthenticator;
import eu.wisebed.shibboauth.ShibbolethSecretAuthenticationKey;
import eu.wisebed.testbed.api.snaa.authorization.IUserAuthorization;
import eu.wisebed.testbed.api.snaa.authorization.AttributeBasedAuthorization;
import eu.wisebed.testbed.api.snaa.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.*;

import static eu.wisebed.testbed.api.snaa.helpers.Helper.*;

@WebService(endpointInterface = "eu.wisebed.testbed.api.snaa.v1.SNAA", portName = "SNAAPort", serviceName = "SNAAService", targetNamespace = "http://testbed.wisebed.eu/api/snaa/v1/")
public class ShibbolethSNAA implements SNAA {

    private static final Logger log = LoggerFactory.getLogger(ShibbolethSNAA.class);

    protected Set<String> urnPrefixes;

    protected String secretAuthenticationKeyUrl;

    protected IUserAuthorization authorization;

    /**
     * @param urnPrefixes
     * @param secretAuthenticationKeyUrl
     */
    public ShibbolethSNAA(Set<String> urnPrefixes, String secretAuthenticationKeyUrl, IUserAuthorization authorization) {
        this.urnPrefixes = new HashSet<String>(urnPrefixes);
        this.secretAuthenticationKeyUrl = secretAuthenticationKeyUrl;
        this.authorization = authorization;
    }

    @Override
    public List<SecretAuthenticationKey> authenticate(
            @WebParam(name = "authenticationData", targetNamespace = "") List<AuthenticationTriple> authenticationData)
            throws AuthenticationExceptionException, SNAAExceptionException {

        HashSet<SecretAuthenticationKey> keys = new HashSet<SecretAuthenticationKey>();
        log.debug("Starting for " + authenticationData.size() + " urns.");

        assertMinAuthenticationCount(authenticationData, 1);
        assertAllUrnPrefixesServed(urnPrefixes, authenticationData);

        for (AuthenticationTriple triple : authenticationData) {
            ShibbolethAuthenticator sa = new ShibbolethAuthenticator();
            String urn = triple.getUrnPrefix();

            try {

                sa.setUsernameAtIdpDomain(triple.getUsername());
                sa.setPassword(triple.getPassword());
                sa.setUrl(secretAuthenticationKeyUrl);
                sa.authenticate();

                if (sa.isAuthenticated()) {
                    String sak = sa.getAuthenticationPageContent().trim();
                    log.info("Authentication suceeded for urn[" + urn + "] and user[" + triple.getUsername()
                            + "]. Secret authentication key is " + sak);

                    SecretAuthenticationKey secretAuthKey = new SecretAuthenticationKey();
                    ShibbolethSecretAuthenticationKey ssak = new ShibbolethSecretAuthenticationKey(sak, sa.getCookieStore().getCookies());
                    secretAuthKey.setSecretAuthenticationKey(SSAKSerialization.serialize(ssak));
                    secretAuthKey.setUrnPrefix(triple.getUrnPrefix());
                    secretAuthKey.setUsername(triple.getUsername());
                    keys.add(secretAuthKey);
                } else {
                    throw createAuthenticationException("Authentication for urn[" + urn + "] and user["
                            + triple.getUsername() + " failed.");
                }

            } catch (Exception e) {
                throw createSNAAException("Authentication failed :" + e);
            }

        }

        log.debug("Done, returning " + keys.size() + " secret authentication keys");
        return new ArrayList<SecretAuthenticationKey>(keys);

    }

    @Override
    public boolean isAuthorized(
            @WebParam(name = "authenticationData", targetNamespace = "") List<SecretAuthenticationKey> authenticationData,
            @WebParam(name = "action", targetNamespace = "") Action action) throws SNAAExceptionException {

        boolean authorized = true;
        Map<String, List<Object>> authorizeMap = null;

        // Check if we serve all URNs
        assertAllSAKUrnPrefixesServed(urnPrefixes, authenticationData);

        // Check if we have all SecretAuthenticationKeys as sessions
        for (SecretAuthenticationKey key : authenticationData) {

            //check if authorized
            try {
                ShibbolethSecretAuthenticationKey ssak = SSAKSerialization.deserialize(key.getSecretAuthenticationKey());
                ShibbolethAuthenticator sa = new ShibbolethAuthenticator();
                sa.setUrl(secretAuthenticationKeyUrl);
                sa.setSecretAuthenticationKey(ssak.getSecretAuthenticationKey());
                //check authorization
                authorizeMap = sa.isAuthorized(ssak.getCookies());

                if (authorizeMap == null){
                    return false;
                }

                //create Authorization from map
                IUserAuthorization.UserDetails details = new IUserAuthorization.UserDetails();
                details.setUsername(key.getUsername());                
                details.setUserDetails(authorizeMap);

                authorized = authorization.isAuthorized(action, details);
            } catch (Exception e) {
                throw createSNAAException("Authorization failed :" + e);
            }
        }

        log.debug("Done checking authorization, result: " + authorized);
        return authorized;

    }

}