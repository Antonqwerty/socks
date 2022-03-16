package com.example.socks.service;

import com.example.socks.dto.SocksDto;

import java.util.List;

public interface SocksService {
    SocksDto incomeSocks(SocksDto socksDto);

    SocksDto outcomeSocks(SocksDto socksDto);

    List<SocksDto> getSocks(String color, Integer cottonPart, String operation);
}
