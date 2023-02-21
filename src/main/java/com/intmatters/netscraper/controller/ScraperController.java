package com.intmatters.netscraper.controller;

import com.intmatters.netscraper.model.ResponseDTO;
import com.intmatters.netscraper.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping(path = "/")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @GetMapping(path = "/{laneType}")
    public Set<ResponseDTO> getChampionLane(@PathVariable String laneType) {
        return  scraperService.getChampionLane(laneType);
    }

}
