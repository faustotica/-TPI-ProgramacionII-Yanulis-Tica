/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fausto Tica
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;

    private Usuario usuario;

    private List<DetallePedido> detalles;

    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.detalles = new ArrayList<>();
    }

    public Pedido(Usuario usuario) {
        this();
        this.usuario = usuario;
        this.estado = Estado.PENDIENTE;
    }

    public void addDetallePedido(int cantidad, Double precio, Producto producto) {

        DetallePedido detalle = new DetallePedido();

        detalle.setCantidad(cantidad);
        detalle.setProducto(producto);

        detalle.setSubtotal(cantidad * producto.getPrecio());

        detalles.add(detalle);
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        detalles.removeIf(detalle -> detalle.getProducto().getId().equals(producto.getId()));
    }

    @Override
    public void calcularTotal() {
        this.total = 0;

        for (DetallePedido detalle : detalles) {

            this.total += detalle.getSubtotal();
        }

    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    @Override
    public String toString() {
        return "Pedido{"
                + "id=" + id
                + ", usuario=" + usuario.getNombre()
                + ", fecha=" + fecha
                + ", estado=" + estado
                + ", total=" + total
                + '}';
    }
}
