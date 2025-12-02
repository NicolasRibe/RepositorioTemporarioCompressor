package br.com.compress.comunica_compress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compress.comunica_compress.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    Optional<Usuario> findByEmail(String email);   
}
