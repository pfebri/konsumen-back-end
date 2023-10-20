package com.crud.test.repository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.crud.test.model.Konsumen;

@Repository
public class JdbcTutorialRepository implements KonsumenRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Konsumen konsumen) {
        return jdbcTemplate.update("INSERT INTO tb_konsumen (nama, alamat, kota, provinsi, status) VALUES(?,?,?,?,?)",
            new Object[] { konsumen.getNama(), konsumen.getAlamat(), konsumen.getKota(), konsumen.getProvinsi(), konsumen.getStatus() });
    }

    @Override
    public int update(Konsumen konsumen) {
        return jdbcTemplate.update("UPDATE tb_konsumen SET nama=?, alamat=?, kota=?, provinsi=?, status=? WHERE id=?",
        new Object[] { konsumen.getNama(), konsumen.getAlamat(), konsumen.getKota(), konsumen.getProvinsi(), konsumen.getStatus(), konsumen.getId() });
    }

    @Override
    public Konsumen findById(int id) {
        try {
            Konsumen konsumen = jdbcTemplate.queryForObject("SELECT id, nama, alamat, kota, provinsi, to_char(tgl_registrasi, 'YYYY-MM-DD HH:MM:ss') tgl_registrasi, status FROM tb_konsumen WHERE id=?",
                BeanPropertyRowMapper.newInstance(Konsumen.class), id);
      
            return konsumen;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM tb_konsumen WHERE id=?", id);
    }

    @Override
    public List<Konsumen> findAll() {
        return jdbcTemplate.query("SELECT id, nama, alamat, kota, provinsi, to_char(tgl_registrasi, 'YYYY-MM-DD HH:MM:ss') tgl_registrasi, status from tb_konsumen", BeanPropertyRowMapper.newInstance(Konsumen.class));
    }

    @Override
    public List<Konsumen> findByStatus(String status) {
        return jdbcTemplate.query("SELECT id, nama, alamat, kota, provinsi, to_char(tgl_registrasi, 'YYYY-MM-DD HH:MM:ss') tgl_registrasi, status from tb_konsumen WHERE status=?",
        BeanPropertyRowMapper.newInstance(Konsumen.class), status);
    }

    @Override
    public List<Konsumen> findByFilter(String filter) {
        String query = "SELECT id, nama, alamat, kota, provinsi, to_char(tgl_registrasi, 'YYYY-MM-DD HH:MM:ss') tgl_registrasi, status from tb_konsumen "
                    + " WHERE LOWER(nama) LIKE LOWER('%" + filter + "%')"
                    + " OR LOWER(alamat) LIKE LOWER('%" + filter +"%')"
                    + " OR LOWER(kota) LIKE LOWER('%" + filter +"%')"
                    + " OR LOWER(provinsi) LIKE LOWER('%" + filter +"%')";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Konsumen.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from tb_konsumen");
    }
    
}
