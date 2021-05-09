package springbootsecurityjwt.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.filter.OncePerRequestFilter;
import springbootsecurityjwt.jwt.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${token-secret:test}")
    private String secret;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String merchantName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            merchantName = jwtUtil.extractMerchantName(token);
        }

        if (merchantName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String validateTokenGenerated = jwtUtil.createToken(new HashMap<>() , merchantName , secret);

            if (jwtUtil.validateToken(token, "Dummy")) {
                 log.info("Token validated successfully ");
            } else {
                throw  new ResourceAccessException("Invalid Token found , please refresh your token");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
