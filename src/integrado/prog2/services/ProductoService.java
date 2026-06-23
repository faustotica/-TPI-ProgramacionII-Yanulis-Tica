/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.services;

import integrado.prog2.entities.Producto;
import integrado.prog2.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fausto Tica
 */
public class ProductoService {

    private List<Producto> productos;
    private Long nextId;

    public ProductoService() {

        productos = new ArrayList<>();
        nextId = 1L;
    }

    public void crear(Producto producto) {

        if (producto.getPrecio() < 0) {

            throw new IllegalArgumentException(
                    "Precio invalido"
            );
        }

        if (producto.getStock() < 0) {

            throw new IllegalArgumentException(
                    "Stock invalido"
            );
        }

        producto.setId(nextId++);

        productos.add(producto);
    }

    public void editar(Long id, String nombre, double precio, int stock, String descripcion) throws EntidadNoEncontradaException {

        Producto producto = buscarPorId(id);

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
    }

    public List<Producto> listar() {

        List<Producto> activos = new ArrayList<>();

        for (Producto producto : productos) {

            if (!producto.isEliminado()) {

                activos.add(producto);
            }
        }

        return activos;
    }

    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {

        for (Producto producto : productos) {

            if (producto.getId().equals(id)
                    && !producto.isEliminado()) {

                return producto;
            }
        }

        throw new EntidadNoEncontradaException(
                "Producto no encontrado"
        );
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {

        Producto producto = buscarPorId(id);

        producto.setEliminado(true);
    }
}
