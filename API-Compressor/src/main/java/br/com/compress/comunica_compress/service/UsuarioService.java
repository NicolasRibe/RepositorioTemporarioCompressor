package br.com.compress.comunica_compress.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compress.comunica_compress.model.Usuario;
import br.com.compress.comunica_compress.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
