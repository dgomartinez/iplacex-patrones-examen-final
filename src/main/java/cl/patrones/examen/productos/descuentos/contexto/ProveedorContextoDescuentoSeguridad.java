package cl.patrones.examen.productos.descuentos.contexto;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ProveedorContextoDescuentoSeguridad
        implements ProveedorContextoDescuento {

    private final Clock clock;

    public ProveedorContextoDescuentoSeguridad() {
        this(Clock.system(ZoneId.of("America/Santiago")));
    }

    public ProveedorContextoDescuentoSeguridad(Clock clock) {
        this.clock = clock;
    }

    @Override
    public ContextoDescuento obtenerContexto() {

        DayOfWeek dia = LocalDate.now(clock).getDayOfWeek();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean esEmpleado = false;

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            esEmpleado = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(rol -> rol.equals("ROLE_EMPLEADO"));
        }

        return new ContextoDescuento(dia, esEmpleado);
    }
}
