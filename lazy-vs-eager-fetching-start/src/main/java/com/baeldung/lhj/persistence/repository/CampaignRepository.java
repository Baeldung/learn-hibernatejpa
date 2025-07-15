package com.baeldung.lhj.persistence.repository;

import com.baeldung.lhj.persistence.model.Campaign;

import java.util.List;

public interface CampaignRepository {
    Campaign findById(Long id);

    Campaign save(Campaign campaign);

    List<Campaign> findAll();
}
