package com.sns.core.secauth.payloads;

import com.sns.core.secauth.util.EncryptionAlgorithm;
import lombok.Data;

import java.util.List;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private EncryptionAlgorithm encryptionAlgorithm;
    private List<String> authorities;
}
