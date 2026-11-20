package cl.patrones.examen.productos.descuentos.estrategia;

import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;
import cl.patrones.examen.productos.domain.Producto;

public interface Descuento {

    boolean aplica(Producto producto, ContextoDescuento contexto);

    long calcularDescuento(Producto producto);

    int obtenerPorcentaje();
}
