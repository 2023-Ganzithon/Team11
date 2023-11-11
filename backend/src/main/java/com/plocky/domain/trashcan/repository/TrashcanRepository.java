package com.plocky.domain.trashcan.repository;

import com.plocky.domain.trashCategory.entity.TrashCategory;
import com.plocky.domain.trashcan.entity.Trashcan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrashcanRepository extends JpaRepository<Trashcan, Long> {
}
