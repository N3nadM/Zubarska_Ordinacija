package com.simpletask.Zadatak_za_praksu.controller;

import com.simpletask.Zadatak_za_praksu.dto.AppointmentTimeRequestDTO;
import com.simpletask.Zadatak_za_praksu.dto.MakeAppointmentDTO;
import com.simpletask.Zadatak_za_praksu.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<?> makeAppointment(@RequestBody MakeAppointmentDTO makeAppointmentDTO){
        try{
            return new ResponseEntity<>(appointmentService.makeAppointment(makeAppointmentDTO), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/available")
    public ResponseEntity<?> getAvailableAppointments(@RequestBody AppointmentTimeRequestDTO appointmentTimeRequestDTO) {
        try {
            return new ResponseEntity<>(appointmentService.getAvailableAppointmentsByDate(appointmentTimeRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mine-patient/{id}")
    public ResponseEntity<?> getAllMyReserved(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(appointmentService.getAllMineReserved(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mine-dentist/{id}")
    public ResponseEntity<?> getAllMyAppointmentsForDentist(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(appointmentService.getAllMyAppointmentsForDentist(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(appointmentService.cancelAppointment(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
