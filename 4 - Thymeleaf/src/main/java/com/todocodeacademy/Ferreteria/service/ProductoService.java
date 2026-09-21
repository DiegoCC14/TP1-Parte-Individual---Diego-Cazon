package com.todocodeacademy.Ferreteria.service;

import com.todocodeacademy.Ferreteria.model.Producto;
import com.todocodeacademy.Ferreteria.repository.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    private final IProductoRepository prodRepo;

    public ProductoService(IProductoRepository prodRepo) {
        this.prodRepo = prodRepo;
    }

    @Override
    public List<Producto> traerProductos() {
        return prodRepo.findAll();
    }

    @Override
    public Producto buscarProducto(Long codProd) {
        if (codProd == null) {
            return null;
        }
        return prodRepo.findById(codProd).orElse(null);
    }

    @Override
    public Producto crearProducto(Producto prod) {
        if (prod == null) {
            return null;
        }

        if (!validarDatos(prod)) {
            return null;
        }

        // El ID se autogenera en la base de datos
        prod.setCodProducto(null);
        return prodRepo.save(prod);
    }

    @Override
    public Producto editarProducto(Long codProd, Producto prod) {
        if (codProd == null || prod == null) {
            return null;
        }

        Producto prodExistente = buscarProducto(codProd);
        if (prodExistente == null) {
            return null;
        }

        if (!validarDatos(prod)) {
            return null;
        }

        // Actualizamos los datos del producto existente
        prodExistente.setNombre(prod.getNombre());
        prodExistente.setMarca(prod.getMarca());
        prodExistente.setCategoria(prod.getCategoria());
        prodExistente.setPrecio(prod.getPrecio());
        prodExistente.setStock(prod.getStock());
        prodExistente.setDescripcion(prod.getDescripcion());

        return prodRepo.save(prodExistente);
    }

    @Override
    public boolean eliminarProducto(Long codProd) {
        if (codProd == null) {
            return false;
        }

        Producto prodExistente = buscarProducto(codProd);
        if (prodExistente == null) {
            return false;
        }

        prodRepo.delete(prodExistente);
        return true;
    }

    public boolean validarDatos(Producto prod) {
        if (prod == null) {
            return false;
        }

        if (prod.getNombre() == null || prod.getNombre().trim().isEmpty()) {
            return false;
        }

        if (prod.getMarca() == null || prod.getMarca().trim().isEmpty()) {
            return false;
        }

        if (prod.getCategoria() == null || prod.getCategoria().trim().isEmpty()) {
            return false;
        }

        if (prod.getPrecio() == null || prod.getPrecio() <= 0) {
            return false;
        }

        if (prod.getStock() < 0) {
            return false;
        }

        return true;
    }
}
