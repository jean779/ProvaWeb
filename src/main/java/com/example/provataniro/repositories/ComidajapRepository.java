package com.example.provataniro.repositories;

import com.example.provataniro.models.Comidajap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComidajapRepository extends JpaRepository<Comidajap, Long> {
    public List<Comidajap> findAllByDeletedIsNull();
    //deleted
    public List<Comidajap> findAllByDeletedIsNotNull();
}
