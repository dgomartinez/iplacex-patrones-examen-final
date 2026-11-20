package cl.patrones.examen.productos.descuentos.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.List;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;
import cl.patrones.examen.productos.descuentos.estrategia.Descuento;
import cl.patrones.examen.productos.descuentos.estrategia.DescuentoEmpleado;
import cl.patrones.examen.productos.descuentos.estrategia.DescuentoPorDia;
import cl.patrones.examen.productos.descuentos.estrategia.SelectorDescuentos;

class ProductoConDescuentoTest {

    private SelectorDescuentos crearSelector() {
        List<Descuento> descuentos = List.of(
                new DescuentoPorDia(
                        DayOfWeek.MONDAY,
                        "Compresores de Aire",
                        6),
                new DescuentoPorDia(
                        DayOfWeek.TUESDAY,
                        "Esmeriles Angulares",
                        8),
                new DescuentoPorDia(
                        DayOfWeek.WEDNESDAY,
                        "Taladros Percutores",
                        10),
                new DescuentoEmpleado()
        );

        return new SelectorDescuentos(descuentos);
    }

    private Producto crearProducto() {
        Producto producto = mock(Producto.class);
        Categoria categoria = mock(Categoria.class);

        when(producto.getSku()).thenReturn("COMP-001");
        when(producto.getNombre()).thenReturn("Compresor de prueba");
        when(producto.getImagen()).thenReturn("compresor.jpg");
        when(producto.getCosto()).thenReturn(80000L);
        when(producto.getPrecioLista()).thenReturn(100000L);
        when(producto.getCategoria()).thenReturn(categoria);
        when(categoria.getNombre()).thenReturn("Compresores de Aire");

        return producto;
    }

    @Test
    void debeCalcularDescuentoYPrecioFinalCorrectamente() {

        Producto producto = crearProducto();

        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.MONDAY, false);

        ProductoConDescuento decorado = new ProductoConDescuento(
                producto,
                crearSelector(),
                contexto);

        assertEquals(6000L, decorado.getDescuento());
        assertEquals(94000L, decorado.getPrecioFinal());
        assertEquals(6, decorado.getPorcentajeDescuento());
    }

    @Test
    void debeConservarLosDatosOriginalesDelProducto() {

        Producto producto = crearProducto();

        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.FRIDAY, false);

        ProductoConDescuento decorado = new ProductoConDescuento(
                producto,
                crearSelector(),
                contexto);

        assertEquals("COMP-001", decorado.getSku());
        assertEquals("Compresor de prueba", decorado.getNombre());
        assertEquals("compresor.jpg", decorado.getImagen());
        assertEquals(80000L, decorado.getCosto());
        assertEquals(100000L, decorado.getPrecioLista());
        assertEquals("Compresores de Aire",
                decorado.getCategoria().getNombre());
    }

    @Test
    void noDebeModificarElPrecioCuandoNoExisteDescuento() {

        Producto producto = crearProducto();

        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.FRIDAY, false);

        ProductoConDescuento decorado = new ProductoConDescuento(
                producto,
                crearSelector(),
                contexto);

        assertEquals(0L, decorado.getDescuento());
        assertEquals(100000L, decorado.getPrecioFinal());
        assertEquals(0, decorado.getPorcentajeDescuento());
    }
}
