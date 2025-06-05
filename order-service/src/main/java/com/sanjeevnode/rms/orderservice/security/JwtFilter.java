package com.sanjeevnode.rms.orderservice.security;

import com.sanjeevnode.rms.orderservice.dto.UserDTO;
import com.sanjeevnode.rms.orderservice.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
           return;
        }

        String token = authHeader.substring(7);
        UserDTO user = authService.validateToken(token);

        if (user == null) {
            throw new InvalidTokenException("Invalid token");
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
    }
}
