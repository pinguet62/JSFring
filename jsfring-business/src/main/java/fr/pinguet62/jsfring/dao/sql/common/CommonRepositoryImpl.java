package fr.pinguet62.jsfring.dao.sql.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

import com.querydsl.jpa.impl.JPAQuery;

/**
 * Implementation of shared methods of {@link CommonRepository}.
 *
 * @param <T>
 *            The type of {@link Entity}.
 * @param <ID>
 *            The type of {@link Entity} id.
 */
public class CommonRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID>
implements CommonRepository<T, ID> {

    private final EntityManager entityManager;

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
        this.entityManager = entityManager;
    }

    @Override
    public List<T> find(JPAQuery<T> query) {
        JPAQuery<T> emQuery = query.clone(entityManager);
        return emQuery.fetch();
    }

}