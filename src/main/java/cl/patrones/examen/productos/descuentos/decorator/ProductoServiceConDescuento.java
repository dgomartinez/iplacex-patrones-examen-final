package cl.patrones.examen.productos.descuentos.decorator;

import java.util.List;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.service.ProductoService;
import cl.patrones.examen.productos.descuentos.contexto.ProveedorContextoDescuento;
import cl.patrones.examen.productos.descuentos.estrategia.SelectorDescuentos;

public class ProductoServiceConDescuento implements ProductoService {

    private final ProductoService productoService;
    private final SelectorDescuentos selectorDescuentos;
    private final ProveedorContextoDescuento proveedorContexto;

    public ProductoServiceConDescuento(
            ProductoService productoService,
            SelectorDescuentos selectorDescuentos,
            ProveedorContextoDescuento proveedorContexto) {

        this.productoService = productoService;
        this.selectorDescuentos = selectorDescuentos;
        this.proveedorContexto = proveedorContexto;
    }

    @Override
    public List<? extends Producto> getProductos() {

        var contexto = proveedorContexto.obtenerContexto();

        return productoService.getProductos()
                .stream()
                .map(producto -> new ProductoConDescuento(
                        producto,
                        selectorDescuentos,
                        contexto))
                .toList();
    }
}
