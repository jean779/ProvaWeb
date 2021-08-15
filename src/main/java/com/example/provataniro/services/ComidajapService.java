package com.example.provataniro.services;

import com.example.provataniro.models.Comidajap;
import com.example.provataniro.repositories.ComidajapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComidajapService {
    private ComidajapRepository repository;

    @Autowired
    public void setRepository(ComidajapRepository repository) {
        this.repository = repository;
    }

    public Comidajap getById(Long id) {
        return this.repository.getById(id);
    }


    public List<Comidajap> findAllActiveComidajapList() { return this.repository.findAllByDeletedIsNull(); }



    public void save(Comidajap cjap) {
        this.repository.save(cjap);
    }


    public void delete(Long id) {
        Comidajap cjap = this.repository.getById(id);
        cjap.setDeleted(new Date());
        this.repository.save(cjap);
    }
}
