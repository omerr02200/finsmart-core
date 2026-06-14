package com.finsmart.core.finance.services;

import com.finsmart.core.auth.entities.User;
import com.finsmart.core.finance.dto.AccountRequest;
import com.finsmart.core.finance.dto.AccountResponse;
import com.finsmart.core.finance.entities.Account;
import com.finsmart.core.finance.exception.AccountNotFoundException;
import com.finsmart.core.finance.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private UUID getCurrentUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        Account account = Account.builder()
                .userId(getCurrentUserId())
                .name(request.name())
                .build();
        accountRepository.save(account);
        return mapToResponse(account);
    }

    public List<AccountResponse> getUserAccounts() {
        List<Account> accounts = accountRepository.findAllByUserId(getCurrentUserId());
        return accounts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(UUID accountId) {
        Account account = accountRepository.findByIdAndUserId(accountId, getCurrentUserId())
                .orElseThrow(() -> new AccountNotFoundException("Hesap bulunamadı veya bu hesaba erişim yetkiniz yok."));
        return mapToResponse(account);
    }

    private AccountResponse mapToResponse(Account account) {
        return new  AccountResponse(
                account.getId(),
                account.getName(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }
}
