package com.sns.core.secauth.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String clientId;
    private String secrets;

    private int accessTokenValidity;
    private String registeredRedirectUri;
    private int accessTokenValiditySeconds;
    private int refreshTokenValiditySeconds;

    @OneToMany(mappedBy = "clients", fetch = FetchType.EAGER)
    private List<ClientResourceId> resourceId;

    @OneToMany(mappedBy = "clients", fetch = FetchType.LAZY)
    private List<ClientScope> scopes;

    @OneToMany(mappedBy = "clients", fetch = FetchType.LAZY)
    private List<ClientAuthority> authorities;

    @OneToMany(mappedBy = "clients", fetch = FetchType.LAZY)
    private List<ClientGrantTypes> grantTypes;


    private String additionalInformation;
}
