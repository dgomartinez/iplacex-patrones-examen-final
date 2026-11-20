package cl.patrones.examen.productos.descuentos.estrategia;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;

public class DescuentoEmpleado implements Descuento {

    private static final int PORCENTAJE = 5;

    @Override
    public boolean aplica(Producto producto, ContextoDescuento contexto) {
        return contexto.esEmpleado();
    }

    @Override
    public long calcularDescuento(Producto producto) {
        return BigDecimal.valueOf(producto.getPrecioLista())
                .multiply(BigDecimal.valueOf(PORCENTAJE))
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .longValueExact();
    }

    @Override
    public int obtenerPorcentaje() {
        return PORCENTAJE;
    }
}
