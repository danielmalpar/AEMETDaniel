package com.scaffolding.scaffolding.service;

import com.scaffolding.scaffolding.model.Station;
import com.scaffolding.scaffolding.repository.StationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StationServiceTest {

    @Mock//este sirve para falsear un objeto
    private StationRepository stationRepository;


    @InjectMocks//este crea un objeto real
    private StationService stationService;


    @Test
    void oneStation() throws IOException, ParseException {
        String idema = "1111X";
        ArrayList<Station> arraylistStation = new ArrayList<>();
        assertEquals(stationService.OneStation(idema), (arraylistStation));
    }
}

