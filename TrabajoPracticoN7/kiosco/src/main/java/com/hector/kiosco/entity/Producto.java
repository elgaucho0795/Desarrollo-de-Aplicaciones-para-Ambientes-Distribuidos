package com.hector.kiosco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "productos")
@Getter
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class Producto {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long id;
@Column 
private String nombre;
@Column 
private String detalle;
@Column 
private String codigo;
@Column 
private String precio_compra;
@Column 
private String precio_venta;
}
