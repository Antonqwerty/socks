package com.example.socks.controller;

import com.example.socks.dto.SocksDto;
import com.example.socks.exceptions.SocksException;
import com.example.socks.service.SocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Slf4j
public class SocksController {

    private final SocksService socksService;

    @PostMapping(value = "/income", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SocksDto socksIncome(@RequestBody  @Valid SocksDto socksDto) {
        return socksService.incomeSocks(socksDto);
    }

    @PostMapping(value = "/outcome", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SocksDto socksOutcome(@RequestBody @Valid SocksDto socksDto) {
        return socksService.outcomeSocks(socksDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SocksDto> getSocks(@RequestParam String color,
                                   @RequestParam Integer cottonPart,
                                   @RequestParam String operation) {
        return socksService.getSocks(color, cottonPart, operation);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SocksException.class, MethodArgumentNotValidException.class})
    public void handleException(Exception e) {
        log.error(e.getMessage());
    }

}
