package com.example.springbootvtubers.vtubers.myOshi.h2.service;


import com.example.springbootvtubers.vtubers.myOshi.controller.MyOshiController;
import com.example.springbootvtubers.vtubers.myOshi.entity.MyOshiEntity;
import com.example.springbootvtubers.vtubers.myOshi.repository.MyOshiRepository;
import com.example.springbootvtubers.vtubers.myOshi.service.MyOshiService;
import com.example.springbootvtubers.vtubers.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class MyOshiH2ServiceTest {
    @Autowired
    private MyOshiRepository repo;
    private MyOshiService service;

    MyOshiEntity myOshi = new MyOshiEntity();

    @BeforeEach
    public void setup() {
        myOshi.setFirstName("Eddie");
        myOshi.setLastName("Brock");
        myOshi.setHouse("MCU");

        service = new MyOshiService(repo);

    }
    @Test
    public void shouldFindAllMyOshi() {
        service.addMyOshi(myOshi);

        Iterable<MyOshiEntity> myOshiList = service.findAllMyOshis();
        MyOshiEntity savedMyOshi = myOshiList.iterator().next();

        assertThat(savedMyOshi).isNotNull();
    }
    @Test
    public void shouldAddMyOshi() {

        service.addMyOshi(myOshi);

        Iterable<MyOshiEntity> myOshiList = service.findAllMyOshis();
        MyOshiEntity savedMyOshi = myOshiList.iterator().next();

        assertThat(myOshi).isEqualTo(savedMyOshi);

    }

    @Test
    public void shouldUpdateMyOshi() {
        MyOshiEntity savedMyOshi  = service.addMyOshi(myOshi);
        savedMyOshi.setHouse("San Francisco");

        service.updateMyOshi(savedMyOshi.getId(), savedMyOshi);
        MyOshiEntity foundMyOshi = service.findMyOshiById(savedMyOshi.getId());

        assertThat(foundMyOshi.getHouse()).isEqualTo("San Francisco");
    }

    @Test
    public void shouldDeleteMyOshi() {
        assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MyOshiEntity savedMyOshi  = service.addMyOshi(myOshi);

                service.removeMyOshiById(savedMyOshi.getId());
                MyOshiEntity foundMyOshi = service.findMyOshiById(savedMyOshi.getId());

                assertThat(foundMyOshi).isNull();
            }
        });
    }

    @Test
    public void shouldFindMyOshiById() {
        MyOshiEntity savedMyOshi  = service.addMyOshi(myOshi);

        MyOshiEntity foundMyOshi = service.findMyOshiById(savedMyOshi.getId());
        assertThat(foundMyOshi).isNotNull();

    }

    @Test
    public void shouldNotFindMyOshiById() {
        assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MyOshiEntity foundMyOshi = service.findMyOshiById(UUID.randomUUID());
                assertThat(foundMyOshi).isNull();
            }
        });

    }



}
