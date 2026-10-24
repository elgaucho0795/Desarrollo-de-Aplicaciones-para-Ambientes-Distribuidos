package com.hector.kiosco.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hector.kiosco.entity.Producto;
import com.hector.kiosco.service.ProductoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController 
@RequestMapping ("/productos")
@CrossOrigin(origins = "*")

public class ProductoController {
    private final ProductoService productoService;

    ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    @GetMapping()
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosProductos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable ("id")Long id) throws BadRequestException{
        Optional<Producto> productoBuscado = productoService.obtenerProductoPorid(id);
        if(productoBuscado.isPresent()){
            return ResponseEntity. ok(productoBuscado.get());
        }
        else{
            throw new BadRequestException("Producto no encontrado con el id: " + id);
        }
    }
   @PostMapping()
   public ResponseEntity<Producto> GuardarProducto(@RequestBody Producto producto) {
       
       return ResponseEntity.ok(productoService.guardarProducto(producto));
   }
   @DeleteMapping("/{id}")
   public ResponseEntity<String> eliminarProducto(@PathVariable ("id") Long id ) throws BadRequestException{

       Optional<Producto> productoBuscado = productoService.obtenerProductoPorid(id);
       if(productoBuscado.isPresent()){
           productoService.eliminarProducto(id);
           return ResponseEntity.ok("Producto eliminado con exito");
       }
       else{
           throw new BadRequestException("Producto no encontrado con el id: " + id);
       }
    
   }
    
}
