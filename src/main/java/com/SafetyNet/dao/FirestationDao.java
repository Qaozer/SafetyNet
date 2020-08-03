package com.SafetyNet.dao;

import com.SafetyNet.model.FireStation;

import java.util.List;

public interface FirestationDao {
    public List<FireStation> getFirestationsList();
    public void add(FireStation fireStation);
    public void update(FireStation fireStation);
    public void delete(int stationNumber);
    public void delete(String address);
}
