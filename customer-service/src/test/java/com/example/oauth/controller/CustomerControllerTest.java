package com.example.oauth.controller;

import com.example.oauth.api.Customer;
import com.example.oauth.api.CustomerRepository;
import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest  {
    @Autowired
    private MockMvc mockMvc;

    /*
    This actually isn't used during the test,  but it trys to connect to the
    configured IDP during startup.  By Mocking we don't have to worry if the test
    can reach it.
     */
    @MockBean
    JwtDecoder jwtDecoder;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    public void oauth() throws Exception {

        when(customerRepository.findAll())
                .thenReturn(
                        Collections.singletonList(Customer.builder().build())
                );

        JwtAuthenticationToken authenticationToken = createToken();

        this.mockMvc.perform(get("/").with(authentication(authenticationToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@", hasSize(1)));
    }

    @Test
    public void whoami() throws Exception {

        JwtAuthenticationToken authenticationToken = createToken();

        this.mockMvc.perform(get("/whoami").with(authentication(authenticationToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.hello").value("world"))
                .andExpect(jsonPath("@.security.authorities",hasSize(1)));

    }

    private JwtAuthenticationToken createToken() {
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("ROLE_MANAGER"));
        Jwt jwt = new Jwt("n",Instant.now(),Instant.now().plus(Duration.ofDays(1)),Collections.singletonMap("foo","notempty"),Collections.singletonMap("sub","bob"));
        return new JwtAuthenticationToken(jwt, authorities);
    }
}