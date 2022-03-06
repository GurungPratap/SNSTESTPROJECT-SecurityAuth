package com.sns.core.secauth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientScope {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String scope;

    @JoinColumn(name = "clients")
    @ManyToOne
    private Clients clients;
}
