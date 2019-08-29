import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.KeyPair;

/**
   https://jwt.io/
   https://github.com/jwtk/jjwt

 https://www.cryptosys.net/pki/rsakeyformats.html
 https://help.globalscape.com/help/archive/eft6-2/mergedprojects/eft/server_ssh_key_formats.htm
 https://stackoverflow.com/questions/12749858/rsa-public-key-format

 https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file

 */

public class Main {
    public static void main(String[] args) {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
//        Key key2 = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder()
                .setSubject("Joe")
                .claim("c1","value1")
                .claim("c2","value2")
                .signWith(keyPair.getPrivate()).compact();

        System.out.println(jws);

        Jws<Claims> res = Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(jws);
        Object c1 = res.getBody().get("c1");
        Object c2 = res.getBody().get("c2");
        String subj = res.getBody().getSubject();

        System.out.printf("c1:%s c2:%s subj:%s\n",c1,c2,subj);
    }
}
