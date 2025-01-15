package ec.com.sofka.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface TokenProvider {

    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);

    Claims extractAllClaims(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String extractUsername(String token);

    Boolean isTokenExpired(String token);

    Boolean isTokenValid(String token, UserDetails userDetails);
}
