package cl.patrones.examen.productos.descuentos.decorator;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.descuentos.estrategia.Descuento;
import cl.patrones.examen.productos.descuentos.estrategia.SelectorDescuentos;
import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;

public class ProductoConDescuento implements Producto {

    private final Producto producto;
    private final long montoDescuento;
    private final int porcentajeDescuento;

    public ProductoConDescuento(
            Producto producto,
            SelectorDescuentos selector,
            ContextoDescuento contexto) {

        this.producto = producto;

        var descuento = selector.seleccionar(producto, contexto);

        this.montoDescuento = descuento
                .map(d -> d.calcularDescuento(producto))
                .orElse(0L);

        this.porcentajeDescuento = descuento
                .map(Descuento::obtenerPorcentaje)
                .orElse(0);
    }

    @Override
    public String getSku() {
        return producto.getSku();
    }

    @Override
    public String getNombre() {
        return producto.getNombre();
    }

    @Override
    public String getImagen() {
        return producto.getImagen();
    }

    @Override
    public Long getCosto() {
        return producto.getCosto();
    }

    @Override
    public Long getPrecioLista() {
        return producto.getPrecioLista();
    }

    @Override
    public Long getDescuento() {
        return montoDescuento;
    }

    @Override
    public Long getPrecioFinal() {
        return getPrecioLista() - getDescuento();
    }

    @Override
    public cl.patrones.examen.productos.domain.Categoria getCategoria() {
        return producto.getCategoria();
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
}
