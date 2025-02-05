package com.example.springbootvtubers.vtubers.myOshi.controller;

import com.example.springbootvtubers.vtubers.myOshi.dto.MyOshiDto;
import com.example.springbootvtubers.vtubers.myOshi.entity.MyOshiEntity;
import com.example.springbootvtubers.vtubers.myOshi.service.MyOshiService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@CrossOrigin(allowedHeaders = "Content-type")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/my-oshis")
@PreAuthorize("isAuthenticated()")
public class MyOshiController {
    private final MyOshiService service;
    private final ModelMapper mapper;
    // LOGGER FROM SLF4j
    private static final Logger LOGGER = LoggerFactory.getLogger(MyOshiController.class);
    // LOGGER FROM LOMBOK SLF4j
    @CrossOrigin(origins = "${APP_CLIENTURL}")
    @GetMapping
    public List<MyOshiDto> getMyOshis(Pageable pageable) {
        int toSkip = pageable.getPageSize() * pageable.getPageNumber();
        //SLF4J
        LOGGER.info("Using SLF4J: Getting my oshi list - getMyOshis()");
        //LOMBOK SLF4j
        log.info("Using SLF4J Lombok: Getting my oshi list - getMyOshis()");
        // Mapstruct is another dto mapper, but it's not straight forward
        var myOshiList = StreamSupport
                .stream(service.findAllMyOshis().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());


        return myOshiList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MyOshiDto getmyOshiById(@PathVariable("id") UUID id) {
        return convertToDto(service.findMyOshiById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteMyOshiById(@PathVariable("id") UUID id) {
        service.removeMyOshiById(id);
    }

    @PostMapping
    public MyOshiDto postMyOshi(@Valid @RequestBody MyOshiDto myOshiDto) {
        var entity = convertToEntity(myOshiDto);
        var myOshi = service.addMyOshi(entity);

        return convertToDto(myOshi);
    }

    @PutMapping("/{id}")
    public void putMyOshi(
            @PathVariable("id") UUID id,
            @Valid @RequestBody MyOshiDto myOshiDto
    ) {
        if (!id.equals(myOshiDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match"
        );

        var myOshiEntity = convertToEntity(myOshiDto);
        service.updateMyOshi(id, myOshiEntity);
    }

    private MyOshiDto convertToDto(MyOshiEntity entity) {
        return mapper.map(entity, MyOshiDto.class);
    }

    private MyOshiEntity convertToEntity(MyOshiDto dto) {
        return mapper.map(dto, MyOshiEntity.class);
    }

}
