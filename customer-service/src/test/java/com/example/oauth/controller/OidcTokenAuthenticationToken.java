package com.example.oauth.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class OidcTokenAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken {
    private static final long serialVersionUID = 550L;
    private final Map<String, Object> attributes;

    public OidcTokenAuthenticationToken(OidcUser principal, OidcIdToken credentials, Collection<? extends GrantedAuthority> authorities) {
        super(credentials, principal, credentials, authorities);
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap(principal.getAttributes()));
        this.setAuthenticated(true);
    }

    public Map<String, Object> getTokenAttributes() {
        return this.attributes;
    }
}
