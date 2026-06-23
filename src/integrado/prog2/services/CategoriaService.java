/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fausto Tica
 */
public class CategoriaService {

    private List<Categoria> categorias;
    private Long nextId;

    public CategoriaService() {
        categorias = new ArrayList<>();
        nextId = 1L;
    }

    public List<Categoria> listar() {
        List<Categoria> activas = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                activas.add(categoria);
            }
        }
        return activas;
    }

    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {

        for (Categoria categoria : categorias) {
            if (categoria.getId().equals(id) && !categoria.isEliminado()) {
                return categoria;
            }
        }
        throw new EntidadNoEncontradaException("Categoria no encontrada");
    }

    public void crear(Categoria categoria) {
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(categoria.getNombre())) {
                throw new IllegalArgumentException("Ya existe una categoria con ese nombre");
            }
        }

        categoria.setId(nextId++);
        categorias.add(categoria);
    }

    public void editar(Long id, String nombre, String descripcion) throws EntidadNoEncontradaException {

        Categoria categoria = buscarPorId(id);

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {

        Categoria categoria = buscarPorId(id);
        categoria.setEliminado(true);
    }
}
