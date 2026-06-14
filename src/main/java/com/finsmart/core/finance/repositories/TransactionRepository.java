package com.finsmart.core.finance.repositories;

import com.finsmart.core.finance.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT t FROM Transaction t JOIN FETCH t.account a WHERE a.id = :accountId",
    countQuery = "SELECT count(t) FROM Transaction t WHERE t.account.id = :accountId")
    Page<Transaction> findAllByAccountId(@Param("accountId") UUID accountId, Pageable pageable);
}