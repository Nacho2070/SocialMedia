package com.User.UserMicroservice.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
@NoRepositoryBean
public interface Repository<T, ID  extends Serializable > extends CrudRepository<T, ID>{
}
