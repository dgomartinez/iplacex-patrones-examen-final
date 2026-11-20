package cl.patrones.examen.productos.descuentos.estrategia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;

public class DescuentoPorDia implements Descuento {

    private final DayOfWeek diaAplicable;
    private final String categoriaAplicable;
    private final int porcentaje;

    public DescuentoPorDia(
            DayOfWeek diaAplicable,
            String categoriaAplicable,
            int porcentaje) {

        this.diaAplicable = diaAplicable;
        this.categoriaAplicable = categoriaAplicable;
        this.porcentaje = porcentaje;
    }

    @Override
    public boolean aplica(Producto producto, ContextoDescuento contexto) {
        return contexto.dia() == diaAplicable
                && producto.getCategoria() != null
                && categoriaAplicable.equals(
                        producto.getCategoria().getNombre());
    }

    @Override
    public long calcularDescuento(Producto producto) {
        return BigDecimal.valueOf(producto.getPrecioLista())
                .multiply(BigDecimal.valueOf(porcentaje))
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .longValueExact();
    }

    @Override
    public int obtenerPorcentaje() {
        return porcentaje;
    }
}
