package com.fotic.webproject.jpadata.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.fotic.webproject.jpadata.domain.BaseEntity;

/**
 * JpaRepositoryFactoryBean重写 ，
 * JpaRepository默认实现类改为@see {@link BaseRepositoryImpl}
 * @see BaseRepositoryImpl
 * @author dgq
 *
 * @param <R>
 * @param <T>
 * @param <I>
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<T , I>, T extends BaseEntity,I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

	public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}
	
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new BaseRepositoryFactory<>(entityManager);
	}
	
	private static class BaseRepositoryFactory<T extends BaseEntity, I extends Serializable> extends JpaRepositoryFactory{
		
		public BaseRepositoryFactory(EntityManager em) {
			super(em);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
				EntityManager entityManager) {
			return new BaseRepositoryImpl<T, I>((Class<T>)information.getDomainType(), entityManager);
		}
		
		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BaseRepositoryImpl.class;
		}
	}
}
