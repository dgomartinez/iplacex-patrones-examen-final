package cl.patrones.examen.productos.descuentos.contexto;

import java.time.DayOfWeek;

public record ContextoDescuento(
        DayOfWeek dia,
        boolean esEmpleado
) {
}