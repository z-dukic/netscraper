package com.intmatters.netscraper.service;

import com.intmatters.netscraper.model.ResponseDTO;
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
public class ScraperServiceImpl implements ScraperService {

    //URL koji je dobio
    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;

    @Override
    public Set<ResponseDTO> getChampionLane(String laneType) {
        //Responsovi tipa {"name":"ahri", "url":"njen url"}
        Set<ResponseDTO> responseDTOS = new HashSet<>();

        for (String url : urls) {

            //Svi url
            extractDataFromOPGG(responseDTOS, url + laneType);
        }

        return responseDTOS;
    }

    private void extractDataFromOPGG(Set<ResponseDTO> responseDTOS, String url) {

        try {
            //Gets everything from that URL
            Document document = Jsoup.connect(url).get();
            System.out.println(document);

            //Gets everything from that div
            Element element = document.getElementById("content-container");
            System.out.println(element);


            //Gets everything from that tag ("strong")
            Elements elements = element.getElementsByTag("strong");
            System.out.println(elements);


            for (Element ads : elements) {
                ResponseDTO responseDTO = new ResponseDTO();

                //Everything in tags <strong /> gets picked
                if (ads.tagName().equals("strong")) {

                    //Save Champion into a variable
                    String champion = ads.text();

                    //Removes <strong /> tags and set it as title (from responseDTO object)
                    responseDTO.setTitle(champion);

                    //Creates URL
                    responseDTO.setUrl("https://www.op.gg/champions/" + champion + "/mid/build?region=global&tier=platinum_plus");
                }
                if (responseDTO.getUrl() != null) responseDTOS.add(responseDTO);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
