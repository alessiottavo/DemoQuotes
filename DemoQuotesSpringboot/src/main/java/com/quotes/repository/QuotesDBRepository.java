package com.quotes.repository;

import com.quotes.model.QuotesElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotesDBRepository extends JpaRepository<QuotesElement,Long>{

    @Query("SELECT x FROM QuotesElement x WHERE x.author LIKE %?1")
    List<QuotesElement> getQuotesElementByAuthor(String author);

}
