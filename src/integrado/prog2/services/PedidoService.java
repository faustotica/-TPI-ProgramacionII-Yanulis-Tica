/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.services;

import integrado.prog2.entities.Pedido;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fausto Tica
 */
public class PedidoService {

    private List<Pedido> pedidos;
    private Long nextId;

    public PedidoService() {
        pedidos = new ArrayList<>();
        nextId = 1L;
    }

    public void crear(Pedido pedido) {

        pedido.setId(nextId++);
        pedido.calcularTotal();
        pedidos.add(pedido);
    }

    public List<Pedido> listar() {

        List<Pedido> activos = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (!pedido.isEliminado()) {

                activos.add(pedido);
            }
        }

        return activos;
    }

    public Pedido buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        for (Pedido pedido : pedidos) {

            if (pedido.getId().equals(id)
                    && !pedido.isEliminado()) {

                return pedido;
            }
        }

        throw new EntidadNoEncontradaException("Pedido no encontrado");
    }

    public void actualizarEstado(Long id, Estado estado) throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setEstado(estado);
    }

    public void actualizarFormaPago(Long id, FormaPago formaPago) throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setFormaPago(formaPago);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setEliminado(true);
    }

}
