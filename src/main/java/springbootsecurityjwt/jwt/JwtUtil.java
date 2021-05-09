package springbootsecurityjwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springbootsecurityjwt.pojo.TokenRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${token-secret:test}")
    private String secret;

    public String extractMerchantName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(TokenRequest tokenRequest) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("payload",tokenRequest.getPayload());
        String secretKey = secret;
        return createToken(claims,tokenRequest.getClientName(),secretKey);
    }

    public String createToken(Map<String, Object> claims, String clientName , String secretKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(clientName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public Boolean validateToken(String token, String orderId) {
        final String username = extractMerchantName(token);
        //validate the token signature
        return !isTokenExpired(token);
    }
}

