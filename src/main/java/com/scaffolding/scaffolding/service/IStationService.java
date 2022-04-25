package com.scaffolding.scaffolding.service;

import com.scaffolding.scaffolding.model.Station;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface IStationService {
    ArrayList<Station> OneStation(String estacion) throws IOException, ParseException;
}
