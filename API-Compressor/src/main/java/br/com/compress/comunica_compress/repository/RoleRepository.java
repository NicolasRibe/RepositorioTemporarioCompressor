package br.com.compress.comunica_compress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compress.comunica_compress.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByNome(String nome);
}
