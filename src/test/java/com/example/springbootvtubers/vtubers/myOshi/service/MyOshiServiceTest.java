package com.example.springbootvtubers.vtubers.myOshi.service;

import com.example.springbootvtubers.vtubers.myOshi.repository.MyOshiRepository;
import com.example.springbootvtubers.vtubers.myOshi.entity.MyOshiEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MyOshiServiceTest {

    @Mock
    private MyOshiRepository myOshiRepository;

    @InjectMocks
    private MyOshiService underTest;


    @Test
    void canFindAllMyOshis() {
        // when
        underTest.findAllMyOshis();
        // then
        verify(myOshiRepository).findAll();
    }

    @Test
    void canAddMyOshi() {
        // given
        MyOshiEntity myOshi = new MyOshiEntity(
                UUID.randomUUID(),
                "Bunao",
                "Lakandula",
                "Tondo",
                "Datu of Tondo",
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").format(new Date())
        );

        // when
        underTest.addMyOshi(myOshi);

        // then
        ArgumentCaptor<MyOshiEntity> myOshiDtoArgumentCaptor = ArgumentCaptor.forClass(
                MyOshiEntity.class
        );
        verify(myOshiRepository).save(myOshiDtoArgumentCaptor.capture());
        MyOshiEntity capturedMyOshi = myOshiDtoArgumentCaptor.getValue();

        assertThat(capturedMyOshi).isEqualTo(myOshi);
    }
}