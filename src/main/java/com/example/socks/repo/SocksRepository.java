package com.example.socks.repo;

import com.example.socks.dao.Socks;
import org.springframework.data.repository.CrudRepository;

public interface SocksRepository extends CrudRepository<Socks, Long> {
    Socks findByColorAndCottonPart(String color, Integer cottonPart);
}
