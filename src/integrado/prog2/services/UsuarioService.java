/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.services;

import integrado.prog2.entities.Usuario;
import integrado.prog2.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fausto Tica
 */
public class UsuarioService {

    private List<Usuario> usuarios;
    private Long nextId;

    public UsuarioService() {

        usuarios = new ArrayList<>();
        nextId = 1L;
    }

    public void crear(Usuario usuario) {

        for (Usuario u : usuarios) {

            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(usuario.getMail())) {

                throw new IllegalArgumentException("Mail ya registrado");
            }
        }

        usuario.setId(nextId++);

        usuarios.add(usuario);
    }

    public List<Usuario> listar() {

        List<Usuario> activos = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()) {

                activos.add(usuario);
            }
        }

        return activos;
    }

    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {

        for (Usuario usuario : usuarios) {

            if (usuario.getId().equals(id)
                    && !usuario.isEliminado()) {

                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("Usuario no encontrado");
    }

    public void editar(Long id, String nombre, String apellido, String celular) throws EntidadNoEncontradaException {

        Usuario usuario = buscarPorId(id);

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCelular(celular);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {

        Usuario usuario = buscarPorId(id);

        usuario.setEliminado(true);
    }

    public void editarMail(Long id, String nuevoMail) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && !u.getId().equals(id)
                    && u.getMail().equalsIgnoreCase(nuevoMail)) {
                throw new IllegalArgumentException("El mail ya está registrado por otro usuario.");
            }
        }
        Usuario usuario = buscarPorId(id);
        usuario.setMail(nuevoMail);
    }

}
