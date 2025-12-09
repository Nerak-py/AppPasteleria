package com.example.appevalaucion.viewmodel

import com.example.appevalaucion.model.Pastelitos
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel

    // Datos de prueba
    private val pastelito1 = Pastelitos(
        id = "1",
        nombre = "Pastel de Chocolate",
        categoria = "Test",
        descripcion = "Desc",
        precio = 10.0,
        image = "a"
    )
    private val pastelito2 = Pastelitos(
        id = "2",
        nombre = "Pastel de Fresa",
        categoria = "Test",
        descripcion = "Desc",
        precio = 12.0,
        precioOferta = 9.0,
        image = "b"
    )

    @Before
    fun setUp() {
        viewModel = CarritoViewModel()
    }

    @Test
    fun anadirAlCarritoAnadeNuevoItemSiNoExiste() {
        // When
        viewModel.anadirAlCarrito(pastelito1)

        // Then
        assertEquals(1, viewModel.carritoItems.size)
        assertEquals("1", viewModel.carritoItems[0].producto.id)
        assertEquals(1, viewModel.carritoItems[0].cantidad)
    }

    @Test
    fun anadirAlCarritoIncrementaCantidadSiItemYaExiste() {
        // Given
        viewModel.anadirAlCarrito(pastelito1)

        // When
        viewModel.anadirAlCarrito(pastelito1)

        // Then
        assertEquals(1, viewModel.carritoItems.size)
        assertEquals(2, viewModel.carritoItems[0].cantidad)
    }

    @Test
    fun sumarCantidadIncrementaCantidadDelItem() {
        // Given
        viewModel.anadirAlCarrito(pastelito1)
        val item = viewModel.carritoItems[0]

        // When
        viewModel.sumarCantidad(item)

        // Then
        assertEquals(2, viewModel.carritoItems[0].cantidad)
    }

    @Test
    fun restarCantidadDisminuyeCantidadSiEsMayorA1() {
        // Given
        viewModel.anadirAlCarrito(pastelito1)
        viewModel.anadirAlCarrito(pastelito1) // cantidad = 2
        val item = viewModel.carritoItems[0]

        // When
        viewModel.restarCantidad(item)

        // Then
        assertEquals(1, viewModel.carritoItems[0].cantidad)
    }

    @Test
    fun restarCantidadEliminaItemSiCantidadEs1() {
        // Given
        viewModel.anadirAlCarrito(pastelito1)
        val item = viewModel.carritoItems[0]

        // When
        viewModel.restarCantidad(item)

        // Then
        assertTrue(viewModel.carritoItems.isEmpty())
    }

    @Test
    fun eliminarDelCarritoEliminaItemDelCarrito() {
        // Given
        viewModel.anadirAlCarrito(pastelito1)
        val item = viewModel.carritoItems[0]

        // When
        viewModel.eliminarDelCarrito(item)

        // Then
        assertTrue(viewModel.carritoItems.isEmpty())
    }

    @Test
    fun obtenerPrecioTotalCalculaTotalCorrectamente() {
        // Given
        viewModel.anadirAlCarrito(pastelito1) // 10.0
        viewModel.anadirAlCarrito(pastelito2) // 9.0 (oferta)
        viewModel.anadirAlCarrito(pastelito2) // 9.0 (oferta) -> cantidad = 2

        // When
        val total = viewModel.obtenerPrecioTotal()

        // Then
        // total = 10.0 + (9.0 * 2) = 28.0
        assertEquals(28.0, total, 0.001)
    }

    @Test
    fun obtenerPrecioTotalDevuelveCeroSiCarritoEstaVacio() {
        // When
        val total = viewModel.obtenerPrecioTotal()

        // Then
        assertEquals(0.0, total, 0.001)
    }
}
