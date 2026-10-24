package com.hector.kiosco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hector.kiosco.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
