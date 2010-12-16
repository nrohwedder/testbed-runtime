package de.uniluebeck.itm.wiseui.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.uniluebeck.itm.wiseui.api.SNAAService;
import de.uniluebeck.itm.wiseui.shared.exception.AuthenticationException;
import de.uniluebeck.itm.wiseui.shared.wiseml.SecretAuthenticationKey;
import eu.wisebed.testbed.api.snaa.helpers.SNAAServiceHelper;
import eu.wisebed.testbed.api.snaa.v1.AuthenticationExceptionException;
import eu.wisebed.testbed.api.snaa.v1.AuthenticationTriple;
import eu.wisebed.testbed.api.snaa.v1.SNAA;
import eu.wisebed.testbed.api.snaa.v1.SNAAExceptionException;
import org.dozer.DozerBeanMapper;

import java.util.Arrays;

@Singleton
public class SNAAServiceImpl extends RemoteServiceServlet implements SNAAService {

    private static final long serialVersionUID = -478318843648335352L;

    private static final String AUTHENTICATION_FAILED = "Authentication failed!";

    private final DozerBeanMapper mapper;

    @Inject
    public SNAAServiceImpl(final DozerBeanMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SecretAuthenticationKey authenticate(final String endpointUrl, final String urn, final String username, final String password) throws AuthenticationException {
        final SNAA snaaService = SNAAServiceHelper.getSNAAService(endpointUrl);
        final AuthenticationTriple authenticationTriple = new AuthenticationTriple();
        authenticationTriple.setUsername(username);
        authenticationTriple.setUrnPrefix(urn);
        authenticationTriple.setPassword(password);
        eu.wisebed.testbed.api.snaa.v1.SecretAuthenticationKey key = null;
        try {
            key = snaaService.authenticate(Arrays.asList(authenticationTriple)).get(0);
        } catch (final AuthenticationExceptionException e) {
            throw new AuthenticationException(AUTHENTICATION_FAILED, e);
        } catch (final SNAAExceptionException e) {
            throw new AuthenticationException(AUTHENTICATION_FAILED, e);
        }

        return mapper.map(key, SecretAuthenticationKey.class);
    }
}
