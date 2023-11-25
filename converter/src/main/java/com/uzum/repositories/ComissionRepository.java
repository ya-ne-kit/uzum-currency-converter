package com.uzum.repositories;

import com.uzum.models.Comission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComissionRepository extends JpaRepository<Comission, Long> {
    Comission getComissionByFromAndTo(String from, String to);
}
