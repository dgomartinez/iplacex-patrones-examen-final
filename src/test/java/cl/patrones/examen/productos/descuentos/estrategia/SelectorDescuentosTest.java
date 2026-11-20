package cl.patrones.examen.productos.descuentos.estrategia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.List;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.descuentos.contexto.ContextoDescuento;

class SelectorDescuentosTest {

    private final SelectorDescuentos selector =
            new SelectorDescuentos(List.of(
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
            ));

    private Producto crearProducto(String categoriaNombre, long precio) {

        Producto producto = mock(Producto.class);
        Categoria categoria = mock(Categoria.class);

        when(producto.getPrecioLista()).thenReturn(precio);
        when(producto.getCategoria()).thenReturn(categoria);
        when(categoria.getNombre()).thenReturn(categoriaNombre);

        return producto;
    }

    @Test
    void debeAplicarSeisPorCientoLosLunesEnCompresores() {

        Producto producto = crearProducto("Compresores de Aire", 100000L);
        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.MONDAY, false);

        var resultado = selector.seleccionar(producto, contexto);

        assertTrue(resultado.isPresent());
        assertEquals(6, resultado.get().obtenerPorcentaje());
        assertEquals(6000L, resultado.get().calcularDescuento(producto));
    }

    @Test
    void debeAplicarOchoPorCientoLosMartesEnEsmeriles() {

        Producto producto = crearProducto("Esmeriles Angulares", 100000L);
        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.TUESDAY, false);

        var resultado = selector.seleccionar(producto, contexto);

        assertTrue(resultado.isPresent());
        assertEquals(8, resultado.get().obtenerPorcentaje());
        assertEquals(8000L, resultado.get().calcularDescuento(producto));
    }

    @Test
    void debeAplicarDiezPorCientoLosMiercolesEnTaladros() {

        Producto producto = crearProducto("Taladros Percutores", 100000L);
        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.WEDNESDAY, false);

        var resultado = selector.seleccionar(producto, contexto);

        assertTrue(resultado.isPresent());
        assertEquals(10, resultado.get().obtenerPorcentaje());
        assertEquals(10000L, resultado.get().calcularDescuento(producto));
    }

    @Test
    void debeAplicarCincoPorCientoALosEmpleados() {

        Producto producto = crearProducto("Compresores de Aire", 100000L);
        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.FRIDAY, true);

        var resultado = selector.seleccionar(producto, contexto);

        assertTrue(resultado.isPresent());
        assertEquals(5, resultado.get().obtenerPorcentaje());
        assertEquals(5000L, resultado.get().calcularDescuento(producto));
    }

    @Test
    void debeSeleccionarSoloElMayorDescuentoCuandoCoinciden() {

        Producto producto = crearProducto("Compresores de Aire", 100000L);
        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.MONDAY, true);

        var resultado = selector.seleccionar(producto, contexto);

        assertTrue(resultado.isPresent());
        assertEquals(6, resultado.get().obtenerPorcentaje());
        assertEquals(6000L, resultado.get().calcularDescuento(producto));
    }

    @Test
    void noDebeAplicarDescuentoCuandoNoCorresponde() {

        Producto producto = crearProducto("Compresores de Aire", 100000L);
        ContextoDescuento contexto =
                new ContextoDescuento(DayOfWeek.FRIDAY, false);

        var resultado = selector.seleccionar(producto, contexto);

        assertTrue(resultado.isEmpty());
    }
}
