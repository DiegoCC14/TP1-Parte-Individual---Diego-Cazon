package com.todocodeacademy.Ferreteria.controller;

import com.todocodeacademy.Ferreteria.model.Producto;
import com.todocodeacademy.Ferreteria.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final IProductoService prodServ;

    public ProductoRestController(IProductoService prodServ) {
        this.prodServ = prodServ;
    }

    // READ: Traer todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> traerProductos() {
        return ResponseEntity.ok(prodServ.traerProductos());
    }

    // READ: Buscar producto específico por código
    @GetMapping("/{codProd}")
    public ResponseEntity<?> buscarProducto(@PathVariable Long codProd) {
        Producto prod = prodServ.buscarProducto(codProd);
        if (prod == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encuentra un producto con el código: " + codProd);
        }
        return ResponseEntity.ok(prod);
    }

    // CREATE: Registrar un nuevo producto
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto prod) {
        Producto productoCreado = prodServ.crearProducto(prod);
        if (productoCreado == null) {
            return ResponseEntity.badRequest()
                    .body("Los datos del producto no son válidos. Verifique nombre, marca, categoría, precio (>0) y stock (>=0).");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    // UPDATE: Modificar datos de un producto existente
    @PutMapping("/{codProd}")
    public ResponseEntity<?> editarProducto(@PathVariable Long codProd, @RequestBody Producto prodAModificar) {
        Producto prodEditado = prodServ.editarProducto(codProd, prodAModificar);
        if (prodEditado == null) {
            return ResponseEntity.badRequest()
                    .body("No fue posible editar el producto. Verifique que exista el código " + codProd + " y que los datos sean válidos.");
        }
        return ResponseEntity.ok(prodEditado);
    }

    // DELETE: Eliminar producto
    @DeleteMapping("/{codProd}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long codProd) {
        boolean eliminado = prodServ.eliminarProducto(codProd);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un producto con el código: " + codProd);
        }
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}
