package com.SafetyNet.web.controller;

import com.SafetyNet.dto.CoverageDto;
import com.SafetyNet.dto.FireAlertDto;
import com.SafetyNet.dto.FirestationDto;
import com.SafetyNet.dto.HomeDto;
import com.SafetyNet.model.Coverage;
import com.SafetyNet.model.FireAlert;
import com.SafetyNet.model.FireStation;
import com.SafetyNet.model.Home;
import com.SafetyNet.service.FirestationService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FirestationController {

    static final Logger LOGGER = Logger.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "firestation")
    public CoverageDto getCoverage(@RequestParam("stationNumber") int stationNumber){
        LOGGER.info("[GET] Request to get coverage for station number "+stationNumber);
        Coverage coverage = firestationService.getCoverage(stationNumber);
        return coverageToDto(coverage);
    }

    @GetMapping(value = "phoneAlert")
    public List<String> getPhones(@RequestParam("firestation") int stationNumber){
        LOGGER.info("[GET] Request to get phones for station number "+stationNumber);
        return firestationService.getPhonesByStation(stationNumber);
    }

    @GetMapping(value = "fire")
    public FireAlertDto getFireAlert(@RequestParam("address") String address) throws UnsupportedEncodingException {
        String decoded = URLDecoder.decode(address, StandardCharsets.UTF_8.toString());
        LOGGER.info("[GET] Request to get fire alert for address: "+decoded);
        FireAlert fireAlert = firestationService.getFireAlert(address);
        return fireAlertToDto(fireAlert);
    }

    @GetMapping(value = "flood/stations")
    public List<HomeDto> getFloodAlert(@RequestParam("stations") List<Integer> stationNumbers){
        LOGGER.info("[GET] Request to get flood alert for stations: "+stationNumbers.toString());
        List<Home> homes = firestationService.getFloodAlert(stationNumbers);
        return homes.stream().map(this::homeToDto).collect(Collectors.toList());
    }

    @PostMapping(value = "firestation")
    public ResponseEntity<FireStation> addStation(@RequestBody FirestationDto firestationDto){
        LOGGER.info("[POST] Request to add firestation to dataset.");
        firestationService.add(modelMapper.map(firestationDto, FireStation.class));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "firestation")
    public ResponseEntity<FireStation> updateStation(@RequestBody FirestationDto firestationDto){
        LOGGER.info("[PUT] Request to update firestation info.");
        firestationService.update(modelMapper.map(firestationDto, FireStation.class));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "firestation")
    public ResponseEntity deleteAddress (@RequestParam(value = "address", required = false, defaultValue = "") String address,
                                         @RequestParam(value = "station", required = false, defaultValue = "-1") int stationNumber) throws UnsupportedEncodingException {
        if (!address.equals("")){
            String decoded = URLDecoder.decode(address, StandardCharsets.UTF_8.toString());
            LOGGER.info("[DEL] Request to delete firestation for address "+decoded);
            firestationService.deleteAddress(decoded);
        } else if (stationNumber != -1){
            LOGGER.info("[DEL] Request to delete firestation for stationNumber "+stationNumber);
            firestationService.deleteStation(stationNumber);
        }
        return ResponseEntity.ok().build();
    }

    private CoverageDto coverageToDto (Coverage coverage){
        return modelMapper.map(coverage, CoverageDto.class);
    }

    private FireAlertDto fireAlertToDto (FireAlert fireAlert){
        return modelMapper.map(fireAlert, FireAlertDto.class);
    }

    private HomeDto homeToDto(Home home){
        return modelMapper.map(home, HomeDto.class);
    }
}
