package com.example.magic_wand_catalogue_micro.business;

import com.example.magic_wand_catalogue_micro.controller.MagicWandCatalogueController;
import com.example.magic_wand_catalogue_micro.database.MagicWandCatalogueRepository;
import com.example.magic_wand_catalogue_micro.entity.MagicWandCatalogue;
import com.example.magic_wand_catalogue_micro.exception.server.InvalidMagicWandCatalogueDetailsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DetailsValidationImpl implements DetailsValidation {

    @Autowired
    private MagicWandCatalogueRepository magicWandCatalogueRepository;

    private static final Logger logger = LoggerFactory.getLogger(MagicWandCatalogueController.class);

    @Override
    public MagicWandCatalogue magicWandCatalogueDetailsValidation(MagicWandCatalogue magicWandCatalogue) {
        logger.info("Server DetailsValidation.magicWandCatalogueDetailsValidation");
        if (magicWandCatalogue.getName().trim().equalsIgnoreCase("") || magicWandCatalogue.getDescription().trim().equalsIgnoreCase("")) {
            throw new InvalidMagicWandCatalogueDetailsException("Magic wand catalogue name or description must not be empty.");
        }

        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#^$%&*(),.+=|<>?{}\\[\\]~-]");

        Matcher hasDigit = digit.matcher(magicWandCatalogue.getName().trim());
        Matcher hasSpecial = special.matcher(magicWandCatalogue.getName().trim());

        if (hasDigit.find() || hasSpecial.find()) {
            throw new InvalidMagicWandCatalogueDetailsException("Magic wand catalogue name must not be containing special characters and numbers.");
        }

        if (magicWandCatalogue.getDescription().trim().length() > 100) {
            throw new InvalidMagicWandCatalogueDetailsException("Magic wand catalogue description has exceed the characters' limit -- 100");
        }

        if (magicWandCatalogue.getAgeLimit() < 18 || magicWandCatalogue.getAgeLimit() > 70) {
            throw new InvalidMagicWandCatalogueDetailsException("Magic wand catalogue age limit must be between 18 to 70 years of age.");
        }

        if (magicWandCatalogue.getStock() < 0) {
            throw new InvalidMagicWandCatalogueDetailsException("Magic wand catalogue stock must not be having negative numbers.");
        }

        return magicWandCatalogue;
    }
}
