package com.vardhanreddy1706.URLEncoder.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/*iss   → url-encoder-api
sub   → authenticated user’s email
iat   → token creation time
exp   → token expiration time
roles → user authorities such as ROLE_USER */
@Service 
public class JwtTokenService {

    // header.payload.signature

    private final JwtEncoder jwtEncoder;
    private final String issuer;
    private final Duration accessTokenExpiration;

    public JwtTokenService (
        JwtEncoder jwtEncoder,
        @Value("${app.jwt.issuer}") String issuer,
        @Value("${app.jwt.access-token-expiration}")
        Duration accessTokenExpiration 
    ){
        this.jwtEncoder = jwtEncoder;
        this.issuer = issuer;
        this.accessTokenExpiration = accessTokenExpiration;

    }

    public String generateToken(Authentication authentication){
        Instant issuedAt = Instant.now();
        Instant expriesAt = issuedAt.plus(accessTokenExpiration);

        List<String> roles = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer(issuer)
        .issuedAt(issuedAt)
        .expiresAt(expriesAt)
        .subject(authentication.getName())
        .claim("roles", roles)
        .build();

        JwsHeader header = JwsHeader 
        .with(SignatureAlgorithm.RS256)
        .type("JWT")
        .build();

        return jwtEncoder
        .encode(JwtEncoderParameters.from(header,claims))
        .getTokenValue();
    }

    public long getAccessTokenExpirationSeconds() {
    return accessTokenExpiration.toSeconds();
}
}
