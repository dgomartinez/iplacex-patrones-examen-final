package cl.patrones.examen;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import cl.patrones.examen.productos.service.ProductoService;
import cl.patrones.examen.productos.service.ProductoServiceImpl;
import cl.patrones.examen.productos.descuentos.contexto.ProveedorContextoDescuento;
import cl.patrones.examen.productos.descuentos.decorator.ProductoServiceConDescuento;
import cl.patrones.examen.productos.descuentos.estrategia.Descuento;
import cl.patrones.examen.productos.descuentos.estrategia.DescuentoEmpleado;
import cl.patrones.examen.productos.descuentos.estrategia.DescuentoPorDia;
import cl.patrones.examen.productos.descuentos.estrategia.SelectorDescuentos;

@Configuration
public class Productor {

    @Bean
    ProductoServiceImpl productoServiceImpl() {
        return new ProductoServiceImpl();
    }

    @Bean
    Descuento descuentoLunes() {
        return new DescuentoPorDia(
                DayOfWeek.MONDAY,
                "Compresores de Aire",
                6);
    }

    @Bean
    Descuento descuentoMartes() {
        return new DescuentoPorDia(
                DayOfWeek.TUESDAY,
                "Esmeriles Angulares",
                8);
    }

    @Bean
    Descuento descuentoMiercoles() {
        return new DescuentoPorDia(
                DayOfWeek.WEDNESDAY,
                "Taladros Percutores",
                10);
    }

    @Bean
    Descuento descuentoEmpleado() {
        return new DescuentoEmpleado();
    }

    @Bean
    SelectorDescuentos selectorDescuentos(List<Descuento> descuentos) {
        return new SelectorDescuentos(descuentos);
    }

    @Bean
    @Primary
    ProductoService productoServiceConDescuento(
            ProductoServiceImpl productoServiceImpl,
            SelectorDescuentos selectorDescuentos,
            ProveedorContextoDescuento proveedorContexto) {

        return new ProductoServiceConDescuento(
                productoServiceImpl,
                selectorDescuentos,
                proveedorContexto);
    }
}
