package com.globits.cms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsArticleGroup;

@Repository
public interface CmsArticleGroupRepository extends JpaRepository<CmsArticleGroup, UUID> {

}
