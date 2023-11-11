package com.plocky.domain.plogging.repository;

import com.plocky.domain.plogging.entity.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {
}
