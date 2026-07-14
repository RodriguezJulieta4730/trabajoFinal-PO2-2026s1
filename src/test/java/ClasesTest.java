import Clases.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClasesTest {
    @Test
    void test01_Cliente() {
        Cliente cliente = new Cliente("julieta123", 278976543L, "juli@Gmail", "Calle 123", "23456778,970,07/27");

        assertEquals("julieta123", cliente.getAlias());
        assertEquals(278976543L, cliente.getCbu());
        assertEquals("juli@Gmail", cliente.getEmail());
        assertEquals("Calle 123", cliente.getDireccion());
        assertEquals("23456778,970,07/27", cliente.getDatosTarjeta());
    }
}
