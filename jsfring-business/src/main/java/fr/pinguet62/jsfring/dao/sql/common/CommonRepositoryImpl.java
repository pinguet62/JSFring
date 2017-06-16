package fr.pinguet62.jsfring.dao.sql.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * Implementation of shared methods of {@link CommonRepository}.
 *
 * @param <T>
 *            The type of {@link Entity}.
 * @param <ID>
 *            The type of {@link Entity} id.
 */
@NoRepositoryBean
public class CommonRepositoryImpl<T, ID extends Serializable> extends QuerydslJpaRepository<T, ID>
implements CommonRepository<T, ID> {

    /**
     * Required by Spring Data.
     *
     * @param entityInformation
     *            The {@link JpaEntityInformation}. Must be not {@code null}.
     * @param entityManager
     *            The {@link EntityManager}. Must be not {@code null}.
     * @see RepositoryFactoryBeanSupport
     */
    public CommonRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

}