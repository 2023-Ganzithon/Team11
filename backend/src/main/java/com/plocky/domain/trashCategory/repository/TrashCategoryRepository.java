package com.plocky.domain.trashCategory.repository;

import com.plocky.domain.plogging.entity.Plogging;
import com.plocky.domain.trashCategory.entity.TrashCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrashCategoryRepository extends JpaRepository<TrashCategory, Long> {

}
