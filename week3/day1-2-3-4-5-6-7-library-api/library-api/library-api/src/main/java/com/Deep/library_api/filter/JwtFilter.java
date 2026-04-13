    package com.Deep.library_api.filter;

    import com.Deep.library_api.service.CustomerUserDetailsService;
    import com.Deep.library_api.util.JwtUtil;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;

    @Component
    public class JwtFilter extends OncePerRequestFilter {

        private final JwtUtil jwtUtil;
        private final CustomerUserDetailsService userDetailsService;

        public JwtFilter(JwtUtil jwtUtil, CustomerUserDetailsService userDetailsService) {
            this.jwtUtil = jwtUtil;
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");
                if(authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));

                    if(jwtUtil.validateToken(token , userDetails)){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                                );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response); // passes request and response to next filter

        }
    }
