package com.example.magic_wand_catalogue_micro.database;

import com.example.magic_wand_catalogue_micro.entity.MagicWandCatalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MagicWandCatalogueRepository extends JpaRepository<MagicWandCatalogue, UUID> {
    MagicWandCatalogue findByName(String name);
}
