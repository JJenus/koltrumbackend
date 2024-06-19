package com.koltrum.koltrum.controller;

import com.koltrum.koltrum.model.*;
import com.koltrum.koltrum.service.AppUserService;
import com.koltrum.koltrum.service.AuthService;
import com.koltrum.koltrum.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    TransactionService transactionService;

    @Autowired
    AuthService authService;

//    @Autowired
//    private KYCRepo kYCRepo;

    @GetMapping
    public List<AppUser> appUsers(){
        return appUserService.getUsers();
    }

    @PutMapping
    public AppUser update(@RequestBody AppUser appUser){
        return appUserService.update(appUser);
    }

    @GetMapping("/{id}/sessions")
    ResponseEntity<List<LoginSession>> getLoggedInSession(@PathVariable("id") Long userId){
        return ResponseEntity.ok(authService.getSessions(userId));
    }

//    @PostMapping("/deposit")
//    public DepositTransaction depositTransaction(@RequestBody DepositTransaction transaction){
//        return transactionService.makeDeposit(transaction);
//    }

    @PostMapping("/transfer")
    public Transfer depositTransaction(@RequestBody Transfer transaction){
        return transactionService.transfer(transaction);
    }

    @PostMapping("/withdraw")
    public WithdrawalTransaction depositTransaction(@RequestBody WithdrawalTransaction transaction){
        return transactionService.withdraw(transaction);
    }

    @PutMapping("/withdraw")
    public WithdrawalTransaction confirm(@RequestBody WithdrawalTransaction transaction) throws IllegalAccessException {
        return transactionService.confirmWithdrawal(transaction);
    }

    @GetMapping("/{id}/transactions")
    public Map<String, List<?>> getTransactions(@PathVariable("id") Long id){
        return transactionService.getUserTransactions(id);
    }

//    @PostMapping("/verify")
//    public FirramoResponse verify(@RequestBody KYC verification){
//        return appUserService.verify(verification);
//    }

//    @GetMapping("/{id}/kyc")
//    public KYC getVer(@PathVariable("id") Long id){
//        return kYCRepo.findByUserId(id).orElse(null);
//    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        appUserService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public AppUser getUser(@PathVariable("id") Long id){
        return appUserService.getUser(id);
    }

    @GetMapping("/{id}/projects")
    public List<UserProject> getProjects(@PathVariable("id") Long id){
        return appUserService.getProjects(id);
    }
    
    @GetMapping("/subscriptions")
    public List<UserProject> getSubs(){
        return appUserService.getAllProjects();
    }

    @GetMapping("/withdrawals/{id}")
    public List<WithdrawalTransaction> getWithdrawals(@PathVariable("id") Long id){
        return appUserService.getUserProjectWithdrawals(id);
    }

    @PostMapping("/subscribe")
    public UserProject subscribe(@RequestBody UserProject userProject){
        return appUserService.subscribeToProject(userProject);
    }

    @GetMapping("/{id}/wallets")
    public List<Wallet> getWallets(@PathVariable("id") Long id){
        return appUserService.wallets(id);
    }

    @PostMapping("/wallet")
    public Wallet saveWallet(@RequestBody Wallet wallet){
        return appUserService.createWallet(wallet);
    }
}
