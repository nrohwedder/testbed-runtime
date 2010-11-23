package de.uniluebeck.itm.webui.server.rpc;

import java.util.Arrays;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;

import de.uniluebeck.itm.webui.api.SNAAService;
import de.uniluebeck.itm.webui.shared.exception.AuthenticationException;
import eu.wisebed.testbed.api.snaa.helpers.SNAAServiceHelper;
import eu.wisebed.testbed.api.snaa.v1.AuthenticationExceptionException;
import eu.wisebed.testbed.api.snaa.v1.AuthenticationTriple;
import eu.wisebed.testbed.api.snaa.v1.SNAA;
import eu.wisebed.testbed.api.snaa.v1.SNAAExceptionException;

@Singleton
public class SNAAServiceImpl extends RemoteServiceServlet implements SNAAService {

    /**
     * 
     */
    private static final long serialVersionUID = -478318843648335352L;

    @Override
    public void authenticate(final String endpointUrl, final String urn, final String username, final String password) throws AuthenticationException {
        final SNAA snaaService = SNAAServiceHelper.getSNAAService(endpointUrl);
        final AuthenticationTriple authenticationTriple = new AuthenticationTriple();
        authenticationTriple.setUsername(username);
        authenticationTriple.setUrnPrefix(urn);
        authenticationTriple.setPassword(password);
        try {
            snaaService.authenticate(Arrays.asList(authenticationTriple));
        } catch (final AuthenticationExceptionException e) {
            throw new AuthenticationException("Authentication failed", e);
        } catch (final SNAAExceptionException e) {
            throw new AuthenticationException("Authentication failed due to an error", e);
        }
    }

}
