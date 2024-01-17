package com.example.wizard_info_micro.service;

import com.example.wizard_info_micro.business.DetailsValidation;
import com.example.wizard_info_micro.dao.WizardInfoDao;
import com.example.wizard_info_micro.database.WizardInfoRepository;
import com.example.wizard_info_micro.dto.WizardInfoRequestDto;
import com.example.wizard_info_micro.dto.WizardInfoResponseDto;
import com.example.wizard_info_micro.entity.WizardInfo;
import com.example.wizard_info_micro.exception.server.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WizardInfoServiceImpl implements WizardInfoService {

    @Autowired
    private WizardInfoRepository wizardInfoRepository;

    @Autowired
    private DetailsValidation detailsValidation;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WizardInfoDao wizardInfoDao;

    private static final Logger logger = LoggerFactory.getLogger(WizardInfoServiceImpl.class);

    @Override
    public WizardInfoResponseDto saveWizardInfo(WizardInfoRequestDto wizardInfoRequestDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoService.saveWizardInfo");
        try {
            if (wizardInfoDao.findDuplicatedName(wizardInfoRequestDto)) {
                throw new WizardInfoExistException("Wizard name exists, consider change to another name.");
            } else {
                detailsValidation.wizardInfoValidation(wizardInfoRequestDto);
                WizardInfoRequestDto validatedWizardInfo = detailsValidation.wizardInfoValidation(wizardInfoRequestDto);
                WizardInfo saveWizardInfoToDb = modelMapper.map(validatedWizardInfo, WizardInfo.class);
                String joinedDate = String.valueOf(java.time.LocalDate.now());
                saveWizardInfoToDb.setName(validatedWizardInfo.getName().trim());
                saveWizardInfoToDb.setJoinedDate(joinedDate);
                saveWizardInfoToDb.setActive(true);
                wizardInfoRepository.save(saveWizardInfoToDb);
                return modelMapper.map(saveWizardInfoToDb, WizardInfoResponseDto.class);
            }
        } catch (NullPointerException e) {
            throw new InvalidWizardInfoDetailsException("Fields must not be null.");
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<WizardInfoResponseDto> getAllWizardInfo() throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoService.getAllWizardInfo");
        try {
            if (wizardInfoRepository.findAll().isEmpty()) {
                throw new NoWizardInfoFoundException("There is no wizard info in the database.");
            }
            return wizardInfoRepository.findAll().stream().map(wizardInfo -> modelMapper.map(wizardInfo, WizardInfoResponseDto.class)).collect(Collectors.toList());
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }


    @Override
    public WizardInfoResponseDto getWizardInfoById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoService.getWizardInfoById");
        try {
            WizardInfo wizardInfo = wizardInfoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new WizardIdNotFoundException("Wizard Id does not exist. -- " + id));
            return modelMapper.map(wizardInfo, WizardInfoResponseDto.class);
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public WizardInfoResponseDto updateWizardInfoById(String id, WizardInfoRequestDto wizardInfoRequestDto) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoService.updateWizardInfoById");
        try {
            WizardInfo existingWizardInfo = wizardInfoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new WizardIdNotFoundException("Wizard Id does not exist. -- " + id));
            boolean existWizardInfoName = wizardInfoDao.findDuplicatedName(wizardInfoRequestDto);
            if (!existWizardInfoName || existingWizardInfo.getName().equalsIgnoreCase(wizardInfoRequestDto.getName().trim())) {
                WizardInfoRequestDto validatedWizardInfo = detailsValidation.wizardInfoValidation(wizardInfoRequestDto);
                existingWizardInfo.setName(validatedWizardInfo.getName().trim());
                existingWizardInfo.setAge(validatedWizardInfo.getAge());
                existingWizardInfo.setActive(validatedWizardInfo.isActive());
                wizardInfoRepository.save(existingWizardInfo);
                return modelMapper.map(existingWizardInfo, WizardInfoResponseDto.class);
            } else {
                throw new WizardInfoExistException("Wizard name exists, consider change to another name.");
            }
        } catch (NullPointerException e) {
            throw new InvalidWizardInfoDetailsException("Fields must not be null.");
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public String deleteWizardInfo(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server WizardInfoService.deleteWizardInfo");
        try {
            if (!wizardInfoRepository.findById(UUID.fromString(id)).isPresent()) {
                throw new WizardIdNotFoundException("Wizard Id does not exist. -- " + id);
            }
            wizardInfoRepository.deleteById(UUID.fromString(id));
            return "Wizard info has been deleted successfully !\tId: " + id;
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }
}
