package com.crud.test.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.test.model.Konsumen;
import com.crud.test.repository.KonsumenRepository;

@CrossOrigin(origins = "http://localhost:5780")
@RestController
@RequestMapping("/api")
@ResponseBody
public class KonsumenController {
    @Autowired
    KonsumenRepository konsumenRepository;

    @GetMapping("/konsumen")
    public ResponseEntity<List<Konsumen>> getAllKonsumen(@RequestParam(required = false) String filter) {
        try {
            List<Konsumen> konsumens = new ArrayList<Konsumen>();
      
            if (filter != null && filter != ""){
                konsumenRepository.findByFilter(filter).forEach(konsumens::add);
            } else{
                konsumenRepository.findAll().forEach(konsumens::add);
            }  
      
            if (konsumens.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
      
            return new ResponseEntity<>(konsumens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/konsumen")
    public ResponseEntity<String> createKonsumen(@RequestBody Konsumen konsumen) {
        try {
            konsumenRepository.save(new Konsumen(konsumen.getNama(), konsumen.getAlamat(), konsumen.getKota(), konsumen.getProvinsi(), konsumen.getStatus()));
            return new ResponseEntity<>("Konsumen was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/konsumen/{id}")
    public ResponseEntity<Konsumen> getKonsumenById(@PathVariable("id") int id) {
        Konsumen konsumen = konsumenRepository.findById(id);

        if (konsumen != null) {
            return new ResponseEntity<>(konsumen, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/konsumen/{id}")
    public ResponseEntity<String> updateKonsumen(@PathVariable("id") int id, @RequestBody Konsumen konsumen) {
        Konsumen cekKonsumen = konsumenRepository.findById(id);

        if (cekKonsumen != null) {
            cekKonsumen.setId(id);
            cekKonsumen.setNama(konsumen.getNama());
            cekKonsumen.setAlamat(konsumen.getAlamat());
            cekKonsumen.setKota(konsumen.getKota());
            cekKonsumen.setProvinsi(konsumen.getProvinsi());
            cekKonsumen.setStatus(konsumen.getStatus());

            konsumenRepository.update(cekKonsumen);
            return new ResponseEntity<>("Konsumen was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Konsumen with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/konsumen/{id}")
    public ResponseEntity<String> deleteKonsumen(@PathVariable("id") int id) {
        try {
            int result = konsumenRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Konsumen with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Konsumen was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Konsumen.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/konsumen")
    public ResponseEntity<String> deleteAllKonsumens() {
        try {
            int numRows = konsumenRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Konsumen(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Konsumens.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/konsumen/status")
    public ResponseEntity<List<Konsumen>> findByStatus(@RequestParam(required = true) String status) {
        try {
            List<Konsumen> konsumens = konsumenRepository.findByStatus(status);

            if (konsumens.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(konsumens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
