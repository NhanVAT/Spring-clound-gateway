package evnict.gateway.util;

import evnict.gateway.exception.JwtTokenMalformedException;
import evnict.gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.publicKey}")
  private String publicKey;

  public Claims getClaims(final String token) {
    try {
      //      Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret),
      //          SignatureAlgorithm.HS256.getJcaName());

      //Lấy public key RSA 256
      byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      PublicKey pubKey = factory.generatePublic(spec);

      Claims body = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();

      return body;
    } catch (Exception e) {
      System.out.println(e.getMessage() + " => " + e);
      return null;
    }
  }

  public void validateToken(final String token)
      throws JwtTokenMalformedException, JwtTokenMissingException, NoSuchAlgorithmException, InvalidKeySpecException {
    try {
      //      Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtSecret),
      //          SignatureAlgorithm.HS256.getJcaName());

      byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
      KeyFactory factory = KeyFactory.getInstance("RSA");
      PublicKey pubKey = factory.generatePublic(spec);

      Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token);
    } catch (SignatureException ex) {
      throw new JwtTokenMalformedException("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      throw new JwtTokenMalformedException("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      throw new JwtTokenMalformedException("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      throw new JwtTokenMalformedException("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      throw new JwtTokenMissingException("JWT claims string is empty.");
    }
  }
}
