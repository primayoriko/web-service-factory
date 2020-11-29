package com.factory.service;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.factory.service.AuthService;
import com.factory.model.User;
import com.factory.service.Service;

import org.postgresql.util.MD5Digest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@WebService(endpointInterface = "com.factory.service.AuthService")
@HandlerChain(file="handlers.xml")
public class AuthServiceImpl extends Service implements AuthService {
    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    @Override
    public String register(String username, String email,
            String name, String password) {
        if (username == null || email == null || name == null || password == null) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'username' or 'email' or 'name' or 'password' is not specified", "Client");
        }
        if (username.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            throw generateSoapFaultException(400, 
                    "Client Request Error: parameter 'username' or 'email' or 'name' or 'password' is empty", "Client");
        }
        try{
            initConnection();

            if (isExists(username, email)) {
                throw generateSoapFaultException(400, 
                    "Client Request Error: username or email already exist", "Client");
            }

            String sql = "INSERT INTO users(username, email, name, password) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            String hashedPassword = User.hashPassword(username, password);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, name);
            ps.setString(4, hashedPassword);

            int rs = ps.executeUpdate();

            if(rs == 1)
                return String.format("user %s with username %s is successfully registered!", name, username);
            else
                throw generateSoapFaultException(500, 
                        "Server Error: Error registering user", "Server");

        } catch (SOAPFaultException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public String login(String username, String password) {
        if (username == null || password == null) {
            throw generateSoapFaultException(400,
                    "Client Request Error: parameter 'username' or 'password' is not specified", "Client");
        }
        try {
            initConnection();

            ps = conn.prepareStatement("SELECT * FROM users WHERE username='" + username + "'");

            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) { // Check not empty
                rs.next();
                User user = new User(rs);
                if (user.validatePassword(password)) {
                    return createJWT(user.getUsername(), "WS Factory", user.getName(), 0);
                } else {
                    throw generateSoapFaultException(403, "Auth Error: Password do not match", "Client");
                }
            }

            System.out.println("Record empty");
            throw generateSoapFaultException(404, "Client Request Error: User not found.", "Client");
        } catch (SOAPFaultException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    @Override
    public Boolean isAlreadyExists(String username, String email) {
        if (username == null) {
            username = "";
        }
        if (email == null) {
            email = "";
        }
        try {
            initConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        }

        return this.isExists(username, email);
    }
    
    @Override
    public Boolean validateJwt(String jwt) {
        if (jwt == null) {
            throw generateSoapFaultException(400,
                    "Client Request Error: parameter 'jwt' is not specified", "Client");
        }
        try {
            decodeJWT(jwt);
            return true;
        } catch (SignatureException e) {
            throw generateSoapFaultException(403,
                    "Invalid JWT", "Client");
        }
    }
    
    private boolean isExists(String username, String email) {
        try {
            ps = conn
                    .prepareStatement("SELECT * FROM users WHERE username='" + username + "' OR email='" + email + "'");

            rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
    
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    
    public static Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
