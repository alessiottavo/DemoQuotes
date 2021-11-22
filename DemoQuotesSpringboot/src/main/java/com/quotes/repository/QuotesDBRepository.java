package com.quotes.repository;

import com.quotes.model.QuotesElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesDBRepository extends CrudRepository<QuotesElement,Long> {
}
