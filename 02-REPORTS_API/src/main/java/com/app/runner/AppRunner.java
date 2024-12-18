package com.app.runner;

import com.app.entity.EligibilityDetails;
import com.app.repository.EligibilityDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
    private EligibilityDetailsRepo repo;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        EligibilityDetails entity1 = new EligibilityDetails();
        entity1.setEligId(1);
        entity1.setName("John");
        entity1.setMobile(123665465l);
        entity1.setGender('M');
        entity1.setSsn(6868686l);
        entity1.setPlanName("SNAP");
        entity1.setPlanStatus("Approved");
        entity1.setEmail("john@gmail.com");
        repo.save(entity1);

        EligibilityDetails entity2 = new EligibilityDetails();
        entity2.setEligId(2);
        entity2.setName("Smith");
        entity2.setMobile(223665465l);
        entity2.setGender('M');
        entity2.setSsn(6868686l);
        entity2.setPlanName("CCAP");
        entity2.setPlanStatus("Approved");
        entity2.setEmail("smith@gmail.com");
        repo.save(entity2);

        EligibilityDetails entity3 = new EligibilityDetails();
        entity3.setEligId(3);
        entity3.setName("Paul");
        entity3.setMobile(323665465l);
        entity3.setGender('M');
        entity3.setSsn(6868686l);
        entity3.setPlanName("Medicaid");
        entity3.setPlanStatus("Closed");
        entity3.setEmail("paul@gmail.com");
        repo.save(entity3);
    }
}
