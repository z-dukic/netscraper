package com.intmatters.netscraper.service;

import com.intmatters.netscraper.model.ResponseDTO;

import java.util.Set;

public interface ScraperService {

    Set<ResponseDTO> getVehicleByModel(String vehicleModel);

}
