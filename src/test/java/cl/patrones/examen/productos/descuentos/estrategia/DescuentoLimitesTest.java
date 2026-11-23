
package cl.patrones.examen.productos.descuentos.estrategia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.productos.domain.Producto;

class DescuentoLimitesTest {

    private Producto crearProducto(long precio) {
        Producto producto = mock(Producto.class);
        when(producto.getPrecioLista()).thenReturn(precio);
        return producto;
    }

    @Test
    void debeRedondearCorrectamenteElDescuentoEmpleadoDelCincoPorCiento() {

        DescuentoEmpleado descuento = new DescuentoEmpleado();

        assertEquals(0L, descuento.calcularDescuento(crearProducto(9L)));
        assertEquals(1L, descuento.calcularDescuento(crearProducto(10L)));
        assertEquals(1L, descuento.calcularDescuento(crearProducto(11L)));
    }

    @Test
    void debeRedondearCorrectamenteElDescuentoDiarioDelSeisPorCiento() {

        DescuentoPorDia descuento = new DescuentoPorDia(
                DayOfWeek.MONDAY,
                "Compresores de Aire",
                6);

        assertEquals(1L, descuento.calcularDescuento(crearProducto(24L)));
        assertEquals(2L, descuento.calcularDescuento(crearProducto(25L)));
        assertEquals(2L, descuento.calcularDescuento(crearProducto(26L)));
    }

    @Test
    void debeRedondearCorrectamenteElDescuentoDiarioDelOchoPorCiento() {

        DescuentoPorDia descuento = new DescuentoPorDia(
                DayOfWeek.TUESDAY,
                "Esmeriles Angulares",
                8);

        assertEquals(1L, descuento.calcularDescuento(crearProducto(18L)));
        assertEquals(2L, descuento.calcularDescuento(crearProducto(19L)));
        assertEquals(2L, descuento.calcularDescuento(crearProducto(20L)));
    }

    @Test
    void debeRedondearCorrectamenteElDescuentoDiarioDelDiezPorCiento() {

        DescuentoPorDia descuento = new DescuentoPorDia(
                DayOfWeek.WEDNESDAY,
                "Taladros Percutores",
                10);

        assertEquals(1L, descuento.calcularDescuento(crearProducto(14L)));
        assertEquals(2L, descuento.calcularDescuento(crearProducto(15L)));
        assertEquals(2L, descuento.calcularDescuento(crearProducto(16L)));
    }
}
