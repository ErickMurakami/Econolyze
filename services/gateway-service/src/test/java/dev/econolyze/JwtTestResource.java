package dev.econolyze;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JwtTestResource implements QuarkusTestResourceLifecycleManager {
    private Path publicKeyFile;
    @Override
    public Map<String, String> start() {
        try {
            var generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            var publicKey = generator.generateKeyPair().getPublic();
            String pem = "-----BEGIN PUBLIC KEY-----\n"
                    + Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(publicKey.getEncoded())
                    + "\n-----END PUBLIC KEY-----";
            publicKeyFile = Files.createTempFile("econolyze-test-public-", ".pem");
            Files.writeString(publicKeyFile, pem);
            return Map.of(
                    "mp.jwt.verify.publickey.location", publicKeyFile.toUri().toString(),
                    "quarkus.http.ssl.certificate.files", "",
                    "quarkus.http.ssl.certificate.key-files", "");
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Cannot generate test verification key",
                    e
            );
        }
    }

    @Override
    public void stop() {
        try {
            if (publicKeyFile != null) Files.deleteIfExists(publicKeyFile);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Cannot remove test public key",
                    e
            );
        }
    }
}
