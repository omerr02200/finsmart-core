package com.finsmart.core.finance.services;

import com.finsmart.core.auth.entities.User;
import com.finsmart.core.finance.dto.TransactionRequest;
import com.finsmart.core.finance.dto.TransactionResponse;
import com.finsmart.core.finance.entities.Account;
import com.finsmart.core.finance.entities.Transaction;
import com.finsmart.core.finance.entities.TransactionType;
import com.finsmart.core.finance.exception.AccountNotFoundException;
import com.finsmart.core.finance.exception.InsufficientBalanceException;
import com.finsmart.core.finance.repositories.AccountRepository;
import com.finsmart.core.finance.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private UUID getCurrentUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        Account account = accountRepository.findByIdAndUserId(request.accountId(), getCurrentUserId())
                .orElseThrow(() -> new AccountNotFoundException("İşlem yapılacak hesap bulunamadı veya erişim yetkiniz yok."));

        if(request.type() == TransactionType.EXPENSE) {
            if(account.getBalance().compareTo(request.amount()) < 0) {
                throw new InsufficientBalanceException("Hesabınızda bu işlem için yeterli bakiye bulunmamaktadır.");
            }
            account.setBalance(account.getBalance().subtract(request.amount()));
        } else {
            account.setBalance(account.getBalance().add(request.amount()));
        }

        Transaction transaction = Transaction.builder()
                .account(account)
                .type(request.type())
                .category(request.category())
                .amount(request.amount())
                .description(request.description())
                .transactionDate(request.transactionDate())
                .build();

        transaction = transactionRepository.saveAndFlush(transaction);
        accountRepository.save(account);

        return mapToResponse(transaction);
    }

    public Page<TransactionResponse> getAccountTransactions(UUID accountId, Pageable pageable) {
        accountRepository.findByIdAndUserId(accountId, getCurrentUserId())
                .orElseThrow( () -> new AccountNotFoundException("Hesap bulunamadı veya erişim yetkiniz yok."));

        Page<Transaction> transactions = transactionRepository.findAllByAccountId(accountId, pageable);
        return transactions.map(this::mapToResponse);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getAccount().getName(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );
    }
}
