package com.hector.kiosco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hector.kiosco.entity.Producto;
import com.hector.kiosco.repository.ProductoRepository;

@Service 

public class ProductoService  {
    private final ProductoRepository pr;

    public ProductoService(ProductoRepository pr) {
        this.pr = pr;
    }

    public Producto guardarProducto(Producto producto){
        return pr.save(producto);
    }
    public List<Producto> obtenerTodosProductos(){
        return pr.findAll();
    }
    public Optional<Producto> obtenerProductoPorid(Long id){
        return pr.findById(id);
    }
    public void eliminarProducto(Long id){
        pr.deleteById(id);
    }
    public Producto actualizarProducto(Producto producto){
        return pr.save(producto);
    }
}
