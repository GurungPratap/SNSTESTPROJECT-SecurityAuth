package com.sns.core.secauth.service;

import com.sns.core.secauth.model.Clients;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomClientDetails implements ClientDetails {

    private final Clients clients;
    public CustomClientDetails(Clients clients) {
        this.clients = clients;
    }

    public final Clients getClients() {
        return clients;
    }
    @Override
    public String getClientId() {
        return clients.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return clients.getResourceId().stream().map(a->a.getResourceId().toString()).collect(Collectors.toSet());
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clients.getSecrets();
    }

    @Override
    public boolean isScoped() {
        return clients.getScopes().size() > 0 ? true:false;
    }

    @Override
    public Set<String> getScope() {
        return clients.getScopes().stream().map(a -> a.getScope().toString()).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return clients.getGrantTypes().stream().map(a -> a.getName().toString()).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return clients.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(
                        a.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clients.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clients.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
