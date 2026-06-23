package integrado.prog2;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.entities.Pedido;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;
import integrado.prog2.services.CategoriaService;
import integrado.prog2.services.ProductoService;
import integrado.prog2.services.UsuarioService;
import integrado.prog2.services.PedidoService;
import integrado.prog2.interfaces.Calculable;

/**
 *
 * @author Fausto Tica
 */
public class Main {

    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static PedidoService pedidosService = new PedidoService();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("\n===== FOOD STORE =====");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");

            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    menuCategorias(scanner);
                    break;

                case 2:
                    menuProductos(scanner);
                    break;

                case 3:
                    menuUsuarios(scanner);
                    break;

                case 4:
                    menuPedidos(scanner);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void menuCategorias(Scanner scanner) {

        int opcion;

        do {
            System.out.println("\n===== CATEGORIAS =====");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:

                    if (categoriaService.listar().isEmpty()) {
                        System.out.println("No hay categorias registradas");

                    } else {

                        for (Categoria categoria : categoriaService.listar()) {
                            System.out.println(categoria);
                        }
                    }

                    break;

                case 2:
                    try {
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Descripcion: ");
                        String descripcion = scanner.nextLine();

                        Categoria categoria = new Categoria(nombre, descripcion);

                        categoriaService.crear(categoria);
                        System.out.println("Categoria creada correctamente");

                    } catch (Exception e) {

                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case 3:

                    try {

                        System.out.print("ID de la categoria: ");
                        Long idEditar = scanner.nextLong();
                        scanner.nextLine();

                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        System.out.print("Nueva descripcion: ");
                        String nuevaDescripcion = scanner.nextLine();

                        categoriaService.editar(idEditar, nuevoNombre, nuevaDescripcion);

                        System.out.println("Categoria modificada correctamente");

                    } catch (Exception e) {

                        System.out.println("ERROR: " + e.getMessage());
                    }

                    break;

                case 4:
                    try {
                        for (Categoria cat : categoriaService.listar()) {
                            System.out.println(cat.getId() + " - " + cat.getNombre());
                        }
                        System.out.print("ID de la categoria a eliminar: ");
                        Long idEliminar = scanner.nextLong();
                        scanner.nextLine();

                        
                        boolean tieneProductos = false;
                        for (Producto p : productoService.listar()) {
                            if (p.getCategoria() != null && p.getCategoria().getId().equals(idEliminar)) {
                                tieneProductos = true;
                                break;
                            }
                        }
                        if (tieneProductos) {
                            System.out.println("ADVERTENCIA: Esta categoria tiene productos asociados.");
                        }

                        // NUEVO: confirmación S/N
                        System.out.print("Confirma eliminacion? (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            categoriaService.eliminar(idEliminar);
                            System.out.println("Categoria eliminada correctamente");
                        } else {
                            System.out.println("Operacion cancelada.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 0:
                    break;

                default:

                    System.out.println(
                            "Opcion invalida"
                    );
            }

        } while (opcion != 0);
    }

    private static void menuProductos(Scanner scanner) {

        int opcion;

        do {

            System.out.println("\n===== PRODUCTOS =====");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:

                    if (productoService.listar().isEmpty()) {

                        System.out.println("No hay productos registrados");

                    } else {

                        for (Producto producto : productoService.listar()) {

                            System.out.println(producto);
                        }
                    }

                    break;

                case 2:

                    try {

                        if (categoriaService.listar().isEmpty()) {

                            System.out.println("Debe crear una categoria primero");
                            break;
                        }

                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Precio: ");
                        double precio = scanner.nextDouble();

                        System.out.print("Stock: ");
                        int stock = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Descripcion: ");
                        String descripcion = scanner.nextLine();

                        System.out.println("\nCategorias:");

                        for (Categoria categoria : categoriaService.listar()) {

                            System.out.println(categoria.getId() + " - " + categoria.getNombre());
                        }

                        System.out.print("ID Categoria: ");

                        Long idCategoria = scanner.nextLong();

                        scanner.nextLine();

                        Categoria categoria = categoriaService.buscarPorId(idCategoria);

                        Producto producto = new Producto(nombre, precio, descripcion, stock, "", true, categoria);

                        productoService.crear(producto);

                        System.out.println("Producto creado correctamente");

                    } catch (Exception e) {

                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("ID Producto a editar: ");
                        Long idEditar = scanner.nextLong();
                        scanner.nextLine();

                        Producto prod = productoService.buscarPorId(idEditar);

                        System.out.print("Nuevo Nombre (Actual: " + prod.getNombre() + "): ");
                        String nNombre = scanner.nextLine();
                        if (nNombre.trim().isEmpty()) {
                            nNombre = prod.getNombre();
                        }

                        System.out.print("Nuevo Precio (Actual: " + prod.getPrecio() + "): ");
                        double nPrecio = scanner.nextDouble();

                        System.out.print("Nuevo Stock (Actual: " + prod.getStock() + "): ");
                        int nStock = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Nueva Descripcion (Actual: " + prod.getDescripcion() + "): ");
                        String nDesc = scanner.nextLine();
                        if (nDesc.trim().isEmpty()) {
                            nDesc = prod.getDescripcion();
                        }

                        productoService.editar(idEditar, nNombre, nPrecio, nStock, nDesc);
                        System.out.println("Producto modificado correctamente");
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("ID Producto: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("Confirma eliminacion? (S/N): ");
                        String conf = scanner.nextLine();
                        if (conf.equalsIgnoreCase("S")) {
                            productoService.eliminar(id);
                            System.out.println("Producto eliminado");
                        } else {
                            System.out.println("Operacion cancelada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);
    }

    private static void menuUsuarios(Scanner scanner) {

        int opcion;

        do {

            System.out.println("\n===== USUARIOS =====");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:

                    if (usuarioService.listar().isEmpty()) {

                        System.out.println("No hay usuarios registrados");

                    } else {
                        for (Usuario usuario : usuarioService.listar()) {

                            System.out.println(usuario);
                        }
                    }

                    break;

                case 2:

                    try {

                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();

                        System.out.print("Mail: ");
                        String mail = scanner.nextLine();

                        System.out.print("Celular: ");
                        String celular = scanner.nextLine();

                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        System.out.println("Rol:");
                        System.out.println("1 - ADMIN");
                        System.out.println("2 - USUARIO");

                        int opcionRol = scanner.nextInt();
                        scanner.nextLine();

                        Rol rol;
                        if (opcionRol == 1) {
                            rol = Rol.ADMIN;
                        } else {
                            rol = Rol.USUARIO;
                        }

                        Usuario usuario = new Usuario(nombre, apellido, mail, celular, password, rol);
                        usuarioService.crear(usuario);
                        System.out.println("Usuario creado correctamente");

                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }

                    break;

                case 3:
                    try {
                        System.out.print("ID Usuario: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        Usuario usr = usuarioService.buscarPorId(id);

                        System.out.print("Nuevo nombre (actual: " + usr.getNombre() + "): ");
                        String nombre = scanner.nextLine();
                        if (nombre.trim().isEmpty()) {
                            nombre = usr.getNombre();
                        }

                        System.out.print("Nuevo apellido (actual: " + usr.getApellido() + "): ");
                        String apellido = scanner.nextLine();
                        if (apellido.trim().isEmpty()) {
                            apellido = usr.getApellido();
                        }

                        System.out.print("Nuevo celular (actual: " + usr.getCelular() + "): ");
                        String celular = scanner.nextLine();
                        if (celular.trim().isEmpty()) {
                            celular = usr.getCelular();
                        }

                        
                        System.out.print("Nuevo mail (actual: " + usr.getMail() + ", Enter para no cambiar): ");
                        String nuevoMail = scanner.nextLine();
                        if (!nuevoMail.trim().isEmpty()) {
                            usuarioService.editarMail(id, nuevoMail);
                        }

                        usuarioService.editar(id, nombre, apellido, celular);
                        System.out.println("Usuario modificado correctamente");
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;
                case 4:

                    try {
                        System.out.print("ID Usuario: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        usuarioService.eliminar(id);

                        System.out.println("Usuario eliminado correctamente");

                    } catch (Exception e) {

                        System.out.println("ERROR: " + e.getMessage());
                    }

                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);
    }

    private static void menuPedidos(Scanner scanner) {

        int opcion;

        do {
            System.out.println("\n===== PEDIDOS =====");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar Estado/Forma de Pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:

                    if (pedidosService.listar().isEmpty()) {

                        System.out.println("No hay pedidos registrados");

                    } else {
                        for (Pedido pedido : pedidosService.listar()) {
                            System.out.println(pedido);
                        }
                    }

                    break;

                case 2:
                    try {
                        if (usuarioService.listar().isEmpty() || productoService.listar().isEmpty()) {
                            System.out.println("Debe tener al menos un Usuario y un Producto cargados en el sistema.");
                            break;
                        }

                        System.out.println("\nUsuarios Disponibles:");
                        for (Usuario u : usuarioService.listar()) {
                            System.out.println(u.getId() + " - " + u.getNombre() + " " + u.getApellido());
                        }
                        System.out.print("Seleccione ID de Usuario para el pedido: ");
                        Long idUsr = scanner.nextLong();
                        scanner.nextLine();
                        Usuario usr = usuarioService.buscarPorId(idUsr);

                        Pedido nuevoPedido = new Pedido(usr);

                        boolean agregando = true;
                        while (agregando) {
                            System.out.println("\nProductos Disponibles:");
                            for (Producto p : productoService.listar()) {
                                System.out.println(p.getId() + " - " + p.getNombre() + " (Precio: $" + p.getPrecio() + " | Stock: " + p.getStock() + ")");
                            }

                            System.out.print("ID del Producto a agregar: ");
                            Long idProd = scanner.nextLong();
                            System.out.print("Cantidad: ");
                            int cantidad = scanner.nextInt();
                            scanner.nextLine();

                            if (cantidad <= 0) {
                                throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
                            }

                            Producto prod = productoService.buscarPorId(idProd);

                            if (prod.getStock() < cantidad) {
                                throw new IllegalStateException("Stock insuficiente. Operacion de pedido cancelada por seguridad.");
                            }

                            prod.setStock(prod.getStock() - cantidad);
                            nuevoPedido.addDetallePedido(cantidad, prod.getPrecio(), prod);

                            System.out.print("Desea agregar otro producto al pedido? (S/N): ");
                            String continuar = scanner.nextLine();
                            if (!continuar.equalsIgnoreCase("S")) {
                                agregando = false;
                            }
                        }

                        System.out.println("\nSeleccione Forma de Pago:");
                        System.out.println("1 - TARJETA\n2 - TRANSFERENCIA\n3 - EFECTIVO");
                        int fpOp = scanner.nextInt();
                        scanner.nextLine();
                        if (fpOp == 1) {
                            nuevoPedido.setFormaPago(FormaPago.TARJETA);
                        } else if (fpOp == 2) {
                            nuevoPedido.setFormaPago(FormaPago.TRANSFERENCIA);
                        } else {
                            nuevoPedido.setFormaPago(FormaPago.EFECTIVO);
                        }

                        pedidosService.crear(nuevoPedido);
                        System.out.println("Pedido registrado correctamente. Total: $" + nuevoPedido.getTotal());

                    } catch (Exception e) {
                        System.out.println("ERROR EN OPERACION (Pedido cancelado de raiz): " + e.getMessage());
                    }
                    break;

                case 3: 
                    try {
                        System.out.print("ID Pedido a modificar: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        // Verificamos que el pedido exista antes de preguntar qué modificar
                        pedidosService.buscarPorId(id);

                        System.out.println("¿Que desea modificar?\n1 - Estado del Pedido\n2 - Forma de Pago");
                        int modOp = scanner.nextInt();
                        scanner.nextLine();

                        if (modOp == 1) {
                            System.out.println("Seleccione nuevo Estado:\n1-PENDIENTE, 2-CONFIRMADO, 3-TERMINADO, 4-CANCELADO");
                            int estOp = scanner.nextInt();
                            scanner.nextLine();
                            Estado nuevoEst = Estado.PENDIENTE;
                            if (estOp == 2) {
                                nuevoEst = Estado.CONFIRMADO;
                            } else if (estOp == 3) {
                                nuevoEst = Estado.TERMINADO;
                            } else if (estOp == 4) {
                                nuevoEst = Estado.CANCELADO;
                            }

                            pedidosService.actualizarEstado(id, nuevoEst);
                        } else {
                            System.out.println("Seleccione nueva Forma de Pago:\n1-TARJETA, 2-TRANSFERENCIA, 3-EFECTIVO");
                            int fpOp = scanner.nextInt();
                            scanner.nextLine();
                            FormaPago nFp = (fpOp == 1) ? FormaPago.TARJETA : (fpOp == 2) ? FormaPago.TRANSFERENCIA : FormaPago.EFECTIVO;

                            pedidosService.actualizarFormaPago(id, nFp);
                        }
                        System.out.println("Pedido actualizado con exito.");
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case 4: // HU-PED-04: Eliminar pedido (baja lógica) con confirmación
                    try {
                        System.out.print("ID Pedido a eliminar: ");
                        Long id = scanner.nextLong();
                        scanner.nextLine();

                        // Pedimos confirmación explícita antes de cambiar el estado de eliminado
                        System.out.print("Esta seguro de que desea eliminar el pedido ID " + id + "? (S/N): ");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("S")) {
                            pedidosService.eliminar(id);
                            System.out.println("Pedido eliminado correctamente (Baja logica).");
                        } else {
                            System.out.println("Operacion de eliminacion cancelada.");
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;
                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);
    }
}
