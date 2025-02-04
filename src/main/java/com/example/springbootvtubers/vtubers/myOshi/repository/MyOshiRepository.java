package com.example.springbootvtubers.vtubers.myOshi.repository;

import com.example.springbootvtubers.vtubers.myOshi.entity.MyOshiEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MyOshiRepository extends CrudRepository<MyOshiEntity, UUID> {
}
