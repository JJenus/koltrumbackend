package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.*;
import com.koltrum.koltrum.repository.AppUserRepo;
import com.koltrum.koltrum.repository.UserProjectRepo;
import com.koltrum.koltrum.repository.WalletRepository;
import com.koltrum.koltrum.repository.WithdrawalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    AppUserRepo userRepo;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserProjectRepo userProjectRepository;
    @Autowired
    private WithdrawalRepo withdrawalRepo;

    public AppUser getUser(Long id){
        return userRepo.findById(id).orElse(null);
    }

    public List<AppUser> getUsers(){
        return userRepo.findAll();
    }

    public AppUser update(AppUser user){
        Optional<AppUser> optionalAppUser = userRepo.findById(user.getId());
        if (!optionalAppUser.isPresent()) {
            return null;
        }

        return userRepo.save(user);
    }

    public void delUser(Long id){
        userRepo.deleteById(id);
    }

    public AppUser getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public AppUser save(AppUser user) {
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> userRes = userRepo.findByEmail(email);
        if(!userRes.isPresent())
            throw new UsernameNotFoundException("Could not findUser with email = " + email);
        AppUser user = userRes.get();

        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        for (Role role: user.getRoles()){
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(email, user.getPassword(), roles);
    }

    public void deleteUser(Long id) {
        if (userRepo.findById(id).isPresent()){
            userRepo.deleteById(id);
        }else {
            throw new UsernameNotFoundException("User doesn't exist");
        }
    }

    public Wallet createWallet(Wallet wallet){
        return walletRepository.save(wallet);
    }

    public List<Wallet> wallets(Long userId){
        return walletRepository.findByUserId(userId);
    }

    public List<UserProject> getProjects(Long id) {
        List<UserProject> userProjects = userProjectRepository.findByUserId(id);

        userProjects.forEach(project -> {
            Long projectId = project.getId();
            List<WithdrawalTransaction> transactions = withdrawalRepo.findByUserProjectId(projectId);
            double amount = 0;
            for (WithdrawalTransaction tr: transactions){
                amount += Double.parseDouble(tr.getAmount());
            }

            amount = Double.parseDouble(project.getPlan().getReturns().replace(",","")) - amount;
            project.getPlan().setReturns(String.format("%.2f", amount));
        });

        return userProjects;
    }

    public UserProject getProject(Long id) {
        return userProjectRepository.findById(id).orElse(null);
    }

    public UserProject subscribeToProject(UserProject userProject) {
        return userProjectRepository.save(userProject);
    }

    public List<UserProject> getAllProjects() {

        return userProjectRepository.findAll();
    }

    public List<WithdrawalTransaction> getUserProjectWithdrawals(Long id) {
        return withdrawalRepo.findByUserProjectId(id);
    }
}
