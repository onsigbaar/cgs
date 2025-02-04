package com.example.springbootvtubers.vtubers.myOshi.service;

import java.util.UUID;

import com.example.springbootvtubers.vtubers.myOshi.entity.MyOshiEntity;
import com.example.springbootvtubers.vtubers.exception.NotFoundException;
import com.example.springbootvtubers.vtubers.myOshi.repository.MyOshiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MyOshiService {

    private final MyOshiRepository repo;

    public Iterable<MyOshiEntity> findAllMyOshis() {
        return repo.findAll();
    }

    public MyOshiEntity findMyOshiById(UUID id) {
        return findOrThrow(id);
    }

    public void removeMyOshiById(UUID id) {
        repo.deleteById(id);
    }

    public MyOshiEntity addMyOshi(MyOshiEntity myOshi) {
        return repo.save(myOshi);
    }

    public void updateMyOshi(UUID id, MyOshiEntity myOshi) {
        findOrThrow(id);
        repo.save(myOshi);
    }

    private MyOshiEntity findOrThrow(final UUID id) {
        return repo
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Vtubers by id " + id + " was not found")
                );
    }
}

