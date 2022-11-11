package com.astro.parking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astro.parking.controller.dto.ParkingCreateDTO;
import com.astro.parking.controller.dto.ParkingDTO;
import com.astro.parking.controller.mapper.ParkingMapper;
import com.astro.parking.model.Parking;
import com.astro.parking.service.ParkingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper){
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }
    
    @GetMapping
    @ApiOperation("Find all Parkings")
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkingList =  parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        
        return ResponseEntity.ok(result);

    }
    
    @GetMapping("/{id}")
    @ApiOperation("Find by id in Parkings")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking =  parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Parking")
    public ResponseEntity delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping
    @ApiOperation("Create Parking")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO parkingDTO) {

        var parkingCreate = parkingMapper.toParkingCreate(parkingDTO);

        Parking parking =  parkingService.create(parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("/{id}")
    @ApiOperation("Update Parking")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO parkingDTO) {

        var parkingCreate = parkingMapper.toParkingCreate(parkingDTO);

        Parking parking =  parkingService.update(id, parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PostMapping("/{id}")
    @ApiOperation("Check-out Parking")
    public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id){
        Parking parking = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }
}
