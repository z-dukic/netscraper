package com.intmatters.netscraper.service;

import com.intmatters.netscraper.model.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScraperServiceImpl implements ScraperService{

    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;

    @Override
    public Set<ResponseDTO> getVehicleByModel(String vehicleModel) {
        Set<ResponseDTO> responseDTOS = new HashSet<>();

        for (String url: urls) {

                extractDataFromRiyasewana(responseDTOS, url + vehicleModel);
            }

        return responseDTOS;
    }

    private void extractDataFromRiyasewana(Set<ResponseDTO> responseDTOS, String url) {

        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.getElementById("content");

            Elements elements = element.getElementsByTag("a");

            for (Element ads: elements) {
                ResponseDTO responseDTO = new ResponseDTO();

                if (!StringUtils.isEmpty(ads.attr("title")) ) {
                    responseDTO.setTitle(ads.attr("title"));
                    responseDTO.setUrl(ads.attr("href"));
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
