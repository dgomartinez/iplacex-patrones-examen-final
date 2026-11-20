package cl.patrones.examen.productos.descuentos.estrategia;

import java.util.List;
import java.util.Optional;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;

public class SelectorDescuentos {

    private final List<Descuento> descuentos;

    public SelectorDescuentos(List<Descuento> descuentos) {
        this.descuentos = descuentos;
    }

    public Optional<Descuento> seleccionar(
            Producto producto,
            ContextoDescuento contexto) {

        return descuentos.stream()
                .filter(descuento -> descuento.aplica(producto, contexto))
                .max((d1, d2) -> Long.compare(
                        d1.calcularDescuento(producto),
                        d2.calcularDescuento(producto)
                ));
    }
}
