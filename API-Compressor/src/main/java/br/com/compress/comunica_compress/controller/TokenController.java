// package br.com.compress.comunica_compress.controller;

// import java.time.Instant;

// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.oauth2.jwt.JwtClaimsSet;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import br.com.compress.comunica_compress.dto.LoginRequestDTO;
// import br.com.compress.comunica_compress.dto.LoginResponseDTO;
// import br.com.compress.comunica_compress.repository.UsuarioRepository;


// @RestController
// public class TokenController {

//     private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
//     private final JwtEncoder jwtEncoder;

//     private final UsuarioRepository usuarioRepository;

//     public TokenController(JwtEncoder jwtEncoder, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//         this.jwtEncoder = null;
//         this.usuarioRepository = usuarioRepository;
//         this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//     }

//     @PostMapping("/login")
//     public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
//         var usuario = usuarioRepository.findByEmail(loginRequest.email());

//         if (usuario.isEmpty() || !usuario.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)){
//             throw new BadCredentialsException("Usuario ou senha invalidos!");
//         }
        
//         var now = Instant.now();
//         var expiresIn = 300L;

//         var claims = JwtClaimsSet.builder()
//                         .issuer("api_compressor")
//                         .subject(usuario.get().getEmail())
//                         .issuedAt(now)
//                         .expiresAt(now.plusSeconds(expiresIn))
//                         .build();
        
//         var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

//         return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
//     }
    
// }
