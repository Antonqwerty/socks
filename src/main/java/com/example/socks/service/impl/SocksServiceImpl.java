package com.example.socks.service.impl;

import com.example.socks.dao.Socks;
import com.example.socks.dto.SocksDto;
import com.example.socks.exceptions.SocksException;
import com.example.socks.repo.SocksRepository;
import com.example.socks.service.SocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {

    private final SocksRepository socksRepository;
    private final EntityManager entityManager;

    @Override
    public SocksDto incomeSocks(SocksDto socksDto) {
        Socks socks = socksRepository.findByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart());
        if (socks == null) {
            socks = new Socks();
            socks.setColor(socksDto.getColor());
            socks.setCottonPart(socksDto.getCottonPart());
            socks.setQuantity(socksDto.getQuantity());
        } else {
            socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
            socksDto.setQuantity(socks.getQuantity());
        }
        socksRepository.save(socks);
        return socksDto;
    }

    @Override
    public SocksDto outcomeSocks(SocksDto socksDto) {
        Socks socks = socksRepository.findByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart());
        if (socks != null) {
            socks.setQuantity(socks.getQuantity() - socksDto.getQuantity());
            socksDto.setQuantity(socks.getQuantity());
            socksRepository.save(socks);
        } else {
            throw new SocksException("наши носки не найдены");
        }
        return socksDto;
    }

    @Override
    public List<SocksDto> getSocks(String color, Integer cottonPart, String operation) {
        String sql = "SELECT * FROM socks WHERE color = :color AND cotton_part ";
        if ("lessThan".equalsIgnoreCase(operation)) {
            sql += "<";
        } else if ("moreThan".equalsIgnoreCase(operation)) {
            sql += ">";
        } else if ("equal".equalsIgnoreCase(operation)) {
            sql += "=";
        } else {
            throw new SocksException("неизвестный тип операции");
        }
        sql += " :cotton_part";

        List<Socks> list = (List<Socks>) entityManager.createNativeQuery(sql, Socks.class)
                .setParameter("color", color)
                .setParameter("cotton_part", cottonPart)
                .getResultList();
        return list.stream().map(this::mapper).collect(Collectors.toList());
    }

    public SocksDto mapper(Socks socks) {
        SocksDto socksDto = new SocksDto();
        socksDto.setColor(socks.getColor());
        socksDto.setQuantity(socks.getQuantity());
        socksDto.setCottonPart(socks.getCottonPart());
        return socksDto;
    }
}
