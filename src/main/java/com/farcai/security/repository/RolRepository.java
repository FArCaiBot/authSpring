package com.farcai.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farcai.security.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    public Optional<Rol> findByNombre(String nombre);

}
