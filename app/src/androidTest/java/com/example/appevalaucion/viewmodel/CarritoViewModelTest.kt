package com.example.appevalaucion.viewmodel

import com.example.appevalaucion.model.Carrito
import com.example.appevalaucion.model.CarritoItem
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.repository.CarritoRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel
    private lateinit var repository: CarritoRepository
    private val testDispatcher = StandardTestDispatcher()

    // Datos de prueba
    private val pastelito1 = Pastelitos(
        id = "aaa",
        nombre = "Pastel de Chocolate",
        categoria = "Test",
        descripcion = "Desc",
        precio = 10.0,
        imagenUrl = "a"
    )
    private val pastelito2 = Pastelitos(
        id = "bbb",
        nombre = "Pastel de Vainilla",
        categoria = "Test",
        descripcion = "Desc",
        precio = 12.0,
        imageUrl = "b"
    )

    private val usuarioId = 1L
    private val carritoId = 1L

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()

        // Inyectar el mock en el ViewModel
        viewModel = CarritoViewModel().apply {
            // Si tu ViewModel usa constructor injection, pásalo aquí
            // O usa reflection para inyectar el mock
        }


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun cargarCarritoObtieneItemsCorrectamente() = runTest {
        // Given
        val carrito = Carrito(id = carritoId, usuario = null, items = emptyList())
        val items = listOf(
            CarritoItem(id = 1L, carrito = carrito, pastelitos = pastelito1, cantidad = 1)
        )

        coEvery { repository.getCarritoByUsuario(usuarioId) } returns Response.success(carrito)
        coEvery { repository.getItemsByCarrito(carritoId) } returns Response.success(items)

        // When
        viewModel.cargarCarrito(usuarioId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val carritoItems = viewModel.carritoItems.first()
        assertEquals(1, carritoItems.size)
        assertEquals(pastelito1.id, carritoItems[0].pastelitos?.id)

        coVerify { repository.getCarritoByUsuario(usuarioId) }
        coVerify { repository.getItemsByCarrito(carritoId) }
    }

    @Test
    fun actualizarCantidadModificaCantidadDelItem() = runTest {
        // Given
        val carrito = Carrito(id = carritoId, usuario = null, items = emptyList())
        val itemId = 1L
        val item = CarritoItem(id = itemId, carrito = carrito, pastelitos = pastelito1, cantidad = 1)
        val itemActualizado = item.copy(cantidad = 3)

        coEvery { repository.getCarritoByUsuario(usuarioId) } returns Response.success(carrito)
        coEvery { repository.getItemsByCarrito(carritoId) } returns Response.success(listOf(item))
        coEvery { repository.updateCarritoItem(itemId, any()) } returns Response.success(itemActualizado)

        viewModel.cargarCarrito(usuarioId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.actualizarCantidad(itemId, 3)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { repository.updateCarritoItem(eq(itemId), any()) }
    }

    @Test
    fun eliminarItemEliminaDelCarrito() = runTest {
        // Given
        val carrito = Carrito(id = carritoId, usuario = null, items = emptyList())
        val itemId = 1L
        val item = CarritoItem(id = itemId, carrito = carrito, pastelitos = pastelito1, cantidad = 1)

        coEvery { repository.getCarritoByUsuario(usuarioId) } returns Response.success(carrito)
        coEvery { repository.getItemsByCarrito(carritoId) } returnsMany listOf(
            Response.success(listOf(item)),
            Response.success(emptyList())
        )
        coEvery { repository.deleteCarritoItem(itemId) } returns Response.success(Unit)

        viewModel.cargarCarrito(usuarioId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.eliminarItem(itemId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { repository.deleteCarritoItem(itemId) }
    }

    @Test
    fun vaciarCarritoEliminaTodosLosItems() = runTest {
        // Given
        val carrito = Carrito(id = carritoId, usuario = null, items = emptyList())
        val items = listOf(
            CarritoItem(id = 1L, carrito = carrito, pastelitos = pastelito1, cantidad = 1),
            CarritoItem(id = 2L, carrito = carrito, pastelitos = pastelito2, cantidad = 2)
        )

        coEvery { repository.getCarritoByUsuario(usuarioId) } returns Response.success(carrito)
        coEvery { repository.getItemsByCarrito(carritoId) } returns Response.success(items)
        coEvery { repository.deleteCarritoItem(any()) } returns Response.success(Unit)

        viewModel.cargarCarrito(usuarioId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.vaciarCarrito()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.carritoItems.first().isEmpty())
        coVerify(exactly = 2) { repository.deleteCarritoItem(any()) }
    }

    @Test
    fun precioTotalCalculaCorrectamente() = runTest {
        // Given
        val carrito = Carrito(id = carritoId, usuario = null, items = emptyList())
        val items = listOf(
            CarritoItem(id = 1L, carrito = carrito, pastelitos = pastelito1, cantidad = 2),
            CarritoItem(id = 2L, carrito = carrito, pastelitos = pastelito2, cantidad = 1)
        )

        coEvery { repository.getCarritoByUsuario(usuarioId) } returns Response.success(carrito)
        coEvery { repository.getItemsByCarrito(carritoId) } returns Response.success(items)

        // When
        viewModel.cargarCarrito(usuarioId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val total = viewModel.precioTotal.first()
        assertEquals(32.0, total, 0.001)
    }

    @Test
    fun precioTotalEsCeroSiCarritoVacio() = runTest {
        // When
        val total = viewModel.precioTotal.first()

        // Then
        assertEquals(0.0, total, 0.001)
    }
}
