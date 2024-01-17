package com.example.wizard_info_micro.dao;

import com.example.wizard_info_micro.dto.WizardInfoRequestDto;

public interface WizardInfoDao {
    boolean findDuplicatedName(WizardInfoRequestDto wizardInfoRequestDto);
}
