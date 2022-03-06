package com.sns.core.secauth.service;

import com.sns.core.secauth.model.Clients;
import com.sns.core.secauth.repository.ClientsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CustomClientsDetailsImpl implements ClientDetailsService {

    @Autowired
    private ClientsRepository clientsRepository;

    Logger logger = LoggerFactory.getLogger(CustomClientsDetailsImpl.class);

    @Override
    public CustomClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Supplier<ClientRegistrationException> exceptionSupplier =
                () -> new ClientRegistrationException(
                        "Problem during Registration!");
        Clients clients = clientsRepository
                .findClientsByClientId(clientId)
                .orElseThrow(exceptionSupplier);

        return new CustomClientDetails(clients);

    }
}