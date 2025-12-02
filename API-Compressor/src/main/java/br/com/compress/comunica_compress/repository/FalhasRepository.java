package br.com.compress.comunica_compress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compress.comunica_compress.model.Falha;

public interface FalhasRepository extends JpaRepository<Falha, String>{
    
}
