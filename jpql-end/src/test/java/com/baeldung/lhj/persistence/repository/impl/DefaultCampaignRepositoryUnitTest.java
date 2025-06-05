package com.baeldung.lhj.persistence.repository.impl;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class DefaultCampaignRepositoryUnitTest {
    CampaignRepository campaignRepository = new DefaultCampaignRepository();

    @Test
    public void givenExistingCampaign_whenFindById_thenCampaignRetrieved() {
        // given
        Campaign existingCampaign = new Campaign("C-1", "Campaign 1", "Campaign 1 Description");
        campaignRepository.save(existingCampaign);

        // when
        Campaign retrievedCampaign = campaignRepository.findById(existingCampaign.getId()).get();

        // then
        Assertions.assertEquals(existingCampaign, retrievedCampaign);
    }

    @Test
    public void givenExistingCampaign_whenFindByNonExistingId_thenNoCampaignRetrieved() {
        // given
        Campaign existingCampaign = new Campaign("C-2", "Campaign 2", "Campaign 2 Description");
        campaignRepository.save(existingCampaign);

        // when
        Optional<Campaign> retrievedCampaign = campaignRepository.findById(99L);

        // then
        Assertions.assertTrue(retrievedCampaign.isEmpty());
    }

    @Test
    public void givenCampaign_whenFindByCodeAndName_thenCampaignRetrieved() {
        // given
        String campaignCode = "C-4";
        String campaignName = "Campaign 4";
        Campaign campaign = new Campaign(campaignCode, campaignName, "Campaign 4 Description");
        campaignRepository.save(campaign);

        // when
        Optional<Campaign> retrievedCampaign = campaignRepository.findByCodeAndName(campaignCode, campaignName);

        // then
        Assertions.assertTrue(retrievedCampaign.isPresent());
        Assertions.assertEquals(campaign, retrievedCampaign.get());
    }

}