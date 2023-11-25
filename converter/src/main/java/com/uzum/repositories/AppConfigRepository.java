package com.uzum.repositories;

import com.uzum.models.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppConfigRepository extends JpaRepository<Config, Long> {

    @Query(value = "select secret_key from app_config limit 1", nativeQuery = true)
    String getSecretKey();

}
