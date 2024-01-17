package com.example.magic_wand_catalogue_micro.service;

import com.example.magic_wand_catalogue_micro.business.DetailsValidation;
import com.example.magic_wand_catalogue_micro.database.MagicWandCatalogueRepository;
import com.example.magic_wand_catalogue_micro.entity.MagicWandCatalogue;
import com.example.magic_wand_catalogue_micro.exception.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

@Service
public class MagicWandCatalogueServiceImpl implements MagicWandCatalogueService {

    @Autowired
    private MagicWandCatalogueRepository magicWandCatalogueRepository;

    @Autowired
    private DetailsValidation detailsValidation;

    private static final Logger logger = LoggerFactory.getLogger(MagicWandCatalogueServiceImpl.class);

    @Override
    public MagicWandCatalogue saveMagicWandCatalogue(MagicWandCatalogue magicWandCatalogue) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueService.saveMagicWandCatalogue");
        try {
            MagicWandCatalogue existMagicWandCatalogue = magicWandCatalogueRepository.findByName(magicWandCatalogue.getName().trim());
            if (existMagicWandCatalogue != null) {
                throw new MagicWandCatalogueExistException("Magic wand catalogue name exists, consider change to another name.");
            }
            MagicWandCatalogue validatedMagicWandCatalogue = detailsValidation.magicWandCatalogueDetailsValidation(magicWandCatalogue);
            validatedMagicWandCatalogue.setName(magicWandCatalogue.getName().trim());
            validatedMagicWandCatalogue.setDescription(magicWandCatalogue.getDescription().trim());
            return magicWandCatalogueRepository.save(validatedMagicWandCatalogue);
        } catch (NullPointerException e) {
            throw new InvalidMagicWandCatalogueDetailsException("Fields must not be null.");
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<MagicWandCatalogue> getAllMagicWandCatalogue() throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueService.getAllMagicWandCatalogue");
        try {
            if (magicWandCatalogueRepository.findAll().isEmpty()) {
                throw new NoMagicWandCatalogueFoundException("There is no magic wand catalogue in the database.");
            }
            return magicWandCatalogueRepository.findAll();
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public MagicWandCatalogue getMagicWandCatalogueById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueService.getMagicWandCatalogueById");
        try {
            return magicWandCatalogueRepository.findById(UUID.fromString(id)).orElseThrow(() -> new MagicWandCatalogueIdNotFoundException("Magic wand catalogue Id does not exist. -- " + id));
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public MagicWandCatalogue updateMagicWandCatalogueById(String id, MagicWandCatalogue magicWandCatalogue) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueService.updateMagicWandCatalogueById");
        try {
            MagicWandCatalogue existingMagicWandCatalogue = magicWandCatalogueRepository.findById(UUID.fromString(id)).orElseThrow(() -> new MagicWandCatalogueIdNotFoundException("Magic wand catalogue Id does not exist. -- " + id));
            MagicWandCatalogue existMagicWandCatalogueName = magicWandCatalogueRepository.findByName(magicWandCatalogue.getName().trim());
            if (existMagicWandCatalogueName == null || existMagicWandCatalogueName != null && existingMagicWandCatalogue.getName().equalsIgnoreCase(magicWandCatalogue.getName().trim())) {
                MagicWandCatalogue validatedMagicWandCatalogue = detailsValidation.magicWandCatalogueDetailsValidation(magicWandCatalogue);
                existingMagicWandCatalogue.setName(validatedMagicWandCatalogue.getName().trim());
                existingMagicWandCatalogue.setDescription(validatedMagicWandCatalogue.getDescription().trim());
                existingMagicWandCatalogue.setAgeLimit(validatedMagicWandCatalogue.getAgeLimit());
                existingMagicWandCatalogue.setStock(validatedMagicWandCatalogue.getStock());
                return magicWandCatalogueRepository.save(existingMagicWandCatalogue);
            } else {
                throw new MagicWandCatalogueExistException("Magic wand name exists, consider change to another name");
            }
        } catch (NullPointerException e) {
            throw new InvalidMagicWandCatalogueDetailsException("Fields must not be null.");
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public MagicWandCatalogue updateMagicWandCatalogueStockById(String id, MagicWandCatalogue magicWandCatalogue) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueService.updateMagicWandCatalogueStockById");
        try {
            MagicWandCatalogue existingMagicWandCatalogue = magicWandCatalogueRepository.findById(UUID.fromString(id)).orElseThrow(() -> new MagicWandCatalogueIdNotFoundException("Magic wand catalogue Id does not exist. -- " + id));
            if (magicWandCatalogue.getStock() < 0) {
                throw new InvalidMagicWandCatalogueDetailsException("Magic wand catalogue stock must not be having negative numbers.");
            }
            existingMagicWandCatalogue.setStock(magicWandCatalogue.getStock());
            return magicWandCatalogueRepository.save(existingMagicWandCatalogue);
        } catch (NullPointerException e) {
            throw new InvalidMagicWandCatalogueDetailsException("Fields must not be null.");
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }

    @Override
    public String deleteMagicWandCatalogueById(String id) throws HttpRequestMethodNotSupportedException {
        logger.info("Server MagicWandCatalogueService.deleteMagicWandCatalogueById");
        try {
            if (!magicWandCatalogueRepository.findById(UUID.fromString(id)).isPresent()) {
                throw new MagicWandCatalogueIdNotFoundException("Magic wand catalogue Id does not exist. -- " + id);
            }
            magicWandCatalogueRepository.deleteById(UUID.fromString(id));
            return "Magic wand catalogue has been deleted successfully !\tId: " + id;
        } catch (HttpServerErrorException e) {
            throw new ServerErrorException(e.getLocalizedMessage());
        }
    }
}
