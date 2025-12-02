package br.com.compress.comunica_compress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compress.comunica_compress.model.Alerta;

public interface AlertasRepository extends JpaRepository<Alerta, String> {
    
}
