package com.techmaster.blogappbackend.controller;
import com.techmaster.blogappbackend.dto.AuthResponse;
import com.techmaster.blogappbackend.entity.User;
import com.techmaster.blogappbackend.mapper.UserMapper;
import com.techmaster.blogappbackend.repository.UserRepository;
import com.techmaster.blogappbackend.request.LoginRequest;
import com.techmaster.blogappbackend.security.CustomUserDetailsService;
import com.techmaster.blogappbackend.security.JwtUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("login-handle")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // Lưu dữ liệu vào context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // TODO: Tạo ra token -> trả về thông tin sau khi đăng nhập
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());

            // tao token
            String jwtToken = jwtUtils.generateToken(userDetails);
            User user = userRepository.findByEmail(authentication.getName()).orElse(null);

            return ResponseEntity.ok(new AuthResponse(
                    UserMapper.toUserDto(user),
                    jwtToken,
                    true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
