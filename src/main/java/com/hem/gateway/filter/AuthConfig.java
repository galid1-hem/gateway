package com.hem.gateway.filter;

import com.hem.gateway.util.PemUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "hemjtk")
@Getter
@Setter
public class AuthConfig {
    private String issuer;
    private String privateKeyPath;
    private String publicKeyPath;
    private RSAPublicKey publicRSA;
    private RSAPrivateKey privateRSA;

    private void initPrivate() {
        try {
            privateRSA = (RSAPrivateKey) PemUtils.readPrivateKeyFromFile(getPrivateKeyPath(), "RSA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPublic() {
        try {
            publicRSA = (RSAPublicKey) PemUtils.readPublicKeyFromFile(getPublicKeyPath(), "RSA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RSAPrivateKey getRSAPrivateKey() {
        if(privateRSA == null) initPrivate();
        return privateRSA;
    }

    public RSAPublicKey getRSAPublicKey() {
        if(publicRSA == null) initPublic();
        return publicRSA;
    }
}
