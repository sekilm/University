package org.example.p9.core.repository;

import org.example.p9.core.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MainRepo<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
