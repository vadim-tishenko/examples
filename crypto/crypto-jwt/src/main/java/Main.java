import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * https://jwt.io/
 * https://github.com/jwtk/jjwt
 * <p>
 * https://www.cryptosys.net/pki/rsakeyformats.html
 * https://help.globalscape.com/help/archive/eft6-2/mergedprojects/eft/server_ssh_key_formats.htm
 * https://stackoverflow.com/questions/12749858/rsa-public-key-format
 * <p>
 * https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file
 *
 * тут запись и загрузка ключей с Base64
 * https://www.sql.ru/forum/379952/kak-preobrazovat-public-private-key-v-stroku-i-obratno?hl=privatekey
 */

public class Main {
    public static void main(String[] args) throws Exception {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        KeyPair keyPair = getKeyPairFromDisk();
//        Key key2 = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder()
                .setSubject("Joe")
                .claim("c1", "value1")
                .claim("c2", "value2")
                .signWith(keyPair.getPrivate()).compact();

        System.out.println(jws);

        Jws<Claims> res = Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(jws);
        Object c1 = res.getBody().get("c1");
        Object c2 = res.getBody().get("c2");
        String subj = res.getBody().getSubject();

        System.out.printf("c1:%s c2:%s subj:%s\n", c1, c2, subj);

//        KeyPair kp = getKeyPairFromDisk();
//
//        kp.toString();

    }

    private static KeyPair getKeyPairFromDisk() throws Exception {
        byte[] privateBytes = Files.readAllBytes(Paths.get(Main.class.getClassLoader().getResource("keys/private_key.der").toURI()));
        byte[] publicBytes = Files.readAllBytes(Paths.get(Main.class.getClassLoader().getResource("keys/public_key.der").toURI()));

        PrivateKey privateKey = KeyUtils.getPrivateKey(privateBytes);
        PublicKey publicKey = KeyUtils.getPublicKey(publicBytes);

        return new KeyPair(publicKey, privateKey);
    }
}
