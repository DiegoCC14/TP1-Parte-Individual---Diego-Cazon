package com.todocodeacademy.Ferreteria.controller;

import com.todocodeacademy.Ferreteria.model.Producto;
import com.todocodeacademy.Ferreteria.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoWebController {

    private final IProductoService prodServ;

    public ProductoWebController(IProductoService prodServ) {
        this.prodServ = prodServ;
    }

    // Redirección de la raíz al listado
    @GetMapping("/")
    public String inicio() {
        return "redirect:/productos";
    }

    // GET: Listado de productos
    @GetMapping("/productos")
    public String traerProductos(Model model) {
        model.addAttribute("productos", prodServ.traerProductos());
        return "productos/lista";
    }

    // GET: Mostrar formulario para nuevo producto
    @GetMapping("/productos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Registrar Nuevo Producto");
        return "productos/formulario";
    }

    // POST: Guardar producto (alta o modificación)
    @PostMapping("/productos/crear")
    public String crearProducto(@ModelAttribute Producto producto, Model model) {
        Producto resultado;

        if (producto.getCodProducto() == null) {
            resultado = prodServ.crearProducto(producto);
        } else {
            resultado = prodServ.editarProducto(producto.getCodProducto(), producto);
        }

        if (resultado == null) {
            model.addAttribute("producto", producto);
            model.addAttribute("titulo", producto.getCodProducto() == null ? "Registrar Nuevo Producto" : "Editar Producto");
            model.addAttribute("error", "Revisá los datos ingresados. Nombre, marca y categoría son obligatorios. El precio debe ser mayor a cero y el stock no puede ser negativo.");
            return "productos/formulario";
        }

        return "redirect:/productos";
    }

    // GET: Mostrar formulario para editar producto existente
    @GetMapping("/productos/editar/{codProd}")
    public String mostrarFormularioEditar(@PathVariable Long codProd, Model model) {
        Producto producto = prodServ.buscarProducto(codProd);
        if (producto == null) {
            return "redirect:/productos";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        return "productos/formulario";
    }

    // POST: Eliminar producto
    @PostMapping("/productos/eliminar/{codProd}")
    public String eliminarProducto(@PathVariable Long codProd) {
        prodServ.eliminarProducto(codProd);
        return "redirect:/productos";
    }
}
