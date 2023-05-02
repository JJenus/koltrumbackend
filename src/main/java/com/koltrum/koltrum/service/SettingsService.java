package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.PaymentOption;
import com.koltrum.koltrum.model.Setting;
import com.koltrum.koltrum.repository.PaymentOptionRepository;
import com.koltrum.koltrum.repository.SettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {
    @Autowired
    private SettingsRepo settingsRepo;
    @Autowired
    private PaymentOptionRepository paymentOptionRepository;

    public Setting saveSetting(Setting setting){
        List<PaymentOption> options =  new ArrayList<>();
        setting.getPaymentOptions().forEach(option -> {
            if ((option.getName() == null) || (option.getAddress() == null)
                    || (option.getName().isEmpty()) || (option.getAddress().isEmpty())){
//                do nothing
                if(option.getId() != null){
                    try{
                        paymentOptionRepository.deleteById(option.getId());
                    }catch (Exception e){
//                        System.out.println(e.getMessage());
                    }
                }
            }
            else{
                options.add(paymentOptionRepository.save(option));
            }
        });

        setting.setPaymentOptions(options);

        return settingsRepo.save(setting);
    }

    public Setting getSettings() {
        List<Setting> settings = settingsRepo.findAll();
        if (settings.isEmpty()) {
            return Setting.builder().currency("USD")
                    .language("EN")
                    .currencySymbol("$")
                    .createdAt(LocalDateTime.now())
                    .walletAddress(null)
                    .cryptoName("BTC")
                    .updatedAt(LocalDateTime.now()).build();
        }
        return settings.get(0);
    }
}
