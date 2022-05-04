package com.scaffolding.scaffolding.controller;

import com.scaffolding.scaffolding.model.Station;
import com.scaffolding.scaffolding.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

@RequestMapping(value="/Station")

@RestController
public class Controller {
    @Autowired
    private StationService stationService;



    @RequestMapping(value = "/listOneStation/{estacion}", method = RequestMethod.GET)
    public ArrayList<Station> Onestation(@PathVariable("estacion")String estacion) throws ParseException, IOException {
        return stationService.OneStation(estacion);
                        }

    @RequestMapping(value = "/listAllStations", method = RequestMethod.GET)
    public void AllStation() throws IOException, ParseException {
        stationService.AllStations();
    }


}
