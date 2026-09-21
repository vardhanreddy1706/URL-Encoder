package com.vardhanreddy1706.URLEncoder.Config;


import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtKeyConfig {

    @Value("${app.jwt.keystore-location}")
    private Resource keystoreResource;

    @Value("${app.jwt.keystore-password}")
    private String keystorePassword;

    @Value("${app.jwt.private-key-password}")
    private String privateKeyPassword;

    @Value("${app.jwt.key-alias}")
    private String keyAlias;

    @Bean
    public KeyPair jwtKeyPair() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            try (InputStream inputStream = keystoreResource.getInputStream()) {
                keyStore.load(
                        inputStream,
                        keystorePassword.toCharArray()
                );
            }

            Key key = keyStore.getKey(
                    keyAlias,
                    privateKeyPassword.toCharArray()
            );

            if (!(key instanceof RSAPrivateKey privateKey)) {
                throw new IllegalStateException(
                        "The configured JWT key is not an RSA private key"
                );
            }

            var certificate = keyStore.getCertificate(keyAlias);

            if (certificate == null
                    || !(certificate.getPublicKey()
                    instanceof RSAPublicKey publicKey)) {
                throw new IllegalStateException(
                        "RSA public key certificate was not found"
                );
            }

            return new KeyPair(publicKey, privateKey);

        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Could not load JWT keys from the keystore",
                    exception
            );
        }
    }

    @Bean
    public JwtEncoder jwtEncoder(KeyPair jwtKeyPair) {
        return NimbusJwtEncoder.withKeyPair(
                (RSAPublicKey) jwtKeyPair.getPublic(),
                (RSAPrivateKey) jwtKeyPair.getPrivate()
        ).build();
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair jwtKeyPair) {
        return NimbusJwtDecoder.withPublicKey(
                (RSAPublicKey) jwtKeyPair.getPublic()
        ).build();
    }
}