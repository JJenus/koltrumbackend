package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.AppUser;
import com.koltrum.koltrum.model.DepositTransaction;
import com.koltrum.koltrum.model.Transfer;
import com.koltrum.koltrum.model.WithdrawalTransaction;
import com.koltrum.koltrum.repository.DepositRepo;
import com.koltrum.koltrum.repository.TransferRepo;
import com.koltrum.koltrum.repository.WithdrawalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private DepositRepo depositRepo;
    @Autowired
    WithdrawalRepo withdrawalRepo;
    @Autowired
    TransferRepo transferRepo;
    @Autowired
    AppUserService userService;


//    public DepositTransaction makeDeposit(DepositTransaction transaction){
//        DepositTransaction depositTransaction = depositRepo.save(transaction);
//
//        AppUser user = userService.getUser(transaction.getUserId());
//        if (user == null){
//            return null;
//        }
//
//        int balance = Integer.parseInt( user.getBalance().getAmount()) + Integer.parseInt( transaction.getAmount());
//        user.getBalance().setAmount(""+balance);
//        userService.save(user); //update user
//
//        if (transaction.getSource() == null || !transaction.getSource().equalsIgnoreCase("ADMIN")){
//            depositRepo.save(transaction);
//        }
//
//        return depositTransaction;
//    }
//
//    public DepositTransaction silent(DepositTransaction transaction){
//        DepositTransaction deposit = depositRepo.save(transaction);
//
//        KYC kyc = KYC.builder()
//                .fee(transaction.getAmount())
//                .transactionId(deposit.getId())
//                .userId(deposit.getUserId())
//                .build();
//
//        kYCRepo.save(kyc);
//
//        return depositRepo.save(transaction);
//    }

    public Transfer transfer(Transfer transfer){
        AppUser fromUser = userService.getUser(transfer.getUserId());
        if (fromUser != null){
            return null;
        }
        AppUser toUser = userService.getUser(transfer.getToUserId());
        if (toUser != null){
            return null;
        }

        try{
            int balance = Integer.parseInt( fromUser.getBalance().getAmount()) - Integer.parseInt( transfer.getAmount());
            fromUser.getBalance().setAmount(""+balance);

            balance = Integer.parseInt( toUser.getBalance().getAmount()) + Integer.parseInt( transfer.getAmount());
            toUser.getBalance().setAmount(""+balance);

            userService.save(fromUser);
            userService.save(toUser);
        } catch (Exception e){
            transfer.setStatus("failed");
        }

        return transferRepo.save(transfer);
    }

    public WithdrawalTransaction withdraw(WithdrawalTransaction withdrawalTransaction){
        AppUser user = userService.getUser(withdrawalTransaction.getUserId());
        if (user == null){
            return null;
        }

        try{
            int balance = Integer.parseInt( user.getBalance().getAmount()) - Integer.parseInt( withdrawalTransaction.getAmount());
            user.getBalance().setAmount(""+balance);
            //makeTransaction()
            userService.save(user); //update user
        } catch (Exception e){
            withdrawalTransaction.setStatus("failed");
        }
        return withdrawalRepo.save(withdrawalTransaction);
    }

    public List<Transfer> getTransfers(Long userId){
        return transferRepo.findByUserIdOrToUserId(userId, userId);
    }

    public List<WithdrawalTransaction> getWithdrawals(Long userId){
        return withdrawalRepo.findByUserId(userId);
    }

    public Map<String, List<?>> getUserTransactions(Long userId){
        Map<String, List<?>> map = new HashMap<>();
        map.put("transfers", getTransfers(userId));
        map.put("withdrawals", getWithdrawals(userId));
        map.put("deposits", getDeposits(userId));

        return map;
    }


    public List<DepositTransaction> getDeposits(Long userId){
        return depositRepo.findByUserId(userId);
    }
    // GEt ALL

    public List<Transfer> getAllTransfers(){
        return transferRepo.findAll();
    }

    public List<WithdrawalTransaction> getAllWithdrawals(){
        return withdrawalRepo.findAll();
    }

    public List<DepositTransaction> getAllDeposits(){
        return depositRepo.findAll();
    }
}
