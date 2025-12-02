package br.com.compress.comunica_compress.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.compress.comunica_compress.model.Role;
import br.com.compress.comunica_compress.model.Usuario;
import br.com.compress.comunica_compress.repository.RoleRepository;
import br.com.compress.comunica_compress.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private  final RoleRepository  roleRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository RoleRepository,
            UsuarioRepository UsuarioRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = RoleRepository;
        this.usuarioRepository = UsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
 
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByNome(Role.Values.ADMIN.name());
        var userAdmin = usuarioRepository.findByEmail("admin@gmail.com");
        userAdmin.ifPresentOrElse(
            user -> { 
                System.out.println("Admin ja existe!");
            }, 
            ()-> {
                Usuario user = new Usuario();
                user.setEmail("admin@gmail.com");
                user.setSenha(passwordEncoder.encode("admin123"));
                user.setRoles(Set.of(roleAdmin));  
                
                usuarioRepository.save(user);
            }
            );
    }

}
