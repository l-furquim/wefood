package com.lucas.profile_ms.services;

import com.lucas.profile_ms.domains.profile.dto.ConfirmCodeDto;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import com.lucas.profile_ms.domains.restaurant.dto.CreateRestaurantDto;
import com.lucas.profile_ms.domains.restaurant.dto.DeleteRestaurantDto;

import java.util.List;

public interface IRestaurantService {

    Restaurant createConfirmation(CreateRestaurantDto data);
    void confirmCode(ConfirmCodeDto data);
    void delete(DeleteRestaurantDto data);
    Restaurant findById(Long id);
    List<Restaurant> getAll();
    List<Restaurant> getAllBranchAccounts(String domainEmail);

}
