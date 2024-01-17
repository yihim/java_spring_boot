package com.example.wizard_info_micro.database;

import com.example.wizard_info_micro.entity.WizardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WizardInfoRepository extends JpaRepository<WizardInfo, UUID> {
    WizardInfo findByName(String name);
}
