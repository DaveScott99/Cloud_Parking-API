package com.astro.parking.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.astro.parking.exception.ParkingNotFoundException;
import com.astro.parking.model.Parking;

@Service
public class ParkingService {
    
    private static Map<String, Parking> parkingMap = new HashMap<>();
 
    static {
        var id = getUUID();
        var id1 = getUUID();
        Parking parking = new Parking(id, "DMS-1111", "SC", "CELTA", "PRETO");
        Parking parking1 = new Parking(id1, "AWD-2345", "SP", "VW GOL", "BRANCO");
        parkingMap.put(id, parking);
        parkingMap.put(id1, parking1);
    }

    public List<Parking> findAll(){
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    public Parking findById(String id){
        
        Parking parking = parkingMap.get(id);

        if (parking == null){
            throw new ParkingNotFoundException(id);
        }

        return parking;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(uuid, parkingCreate);
        return parkingCreate;
    }

    public void delete(String id) {
        findById(id);

        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parkingCreate) {
        Parking parking =  findById(id);

        parking.setColor(parkingCreate.getColor());

        parkingMap.replace(id, parking);

        return parking;
    }

}
