package com.SafetyNet.service;

import com.SafetyNet.model.*;

import java.util.List;

public interface IFirestationService {
    void add (FireStation fireStation);
    void update (FireStation fireStation);
    void deleteAddress (String address);
    void deleteStation (int stationNumber);
    Coverage getCoverage(int station);
    List<String> getPhonesByStation(int station);
    FireAlert getFireAlert(String address);
    List<Home> getFloodAlert(List<Integer> stationNumbers);
}
