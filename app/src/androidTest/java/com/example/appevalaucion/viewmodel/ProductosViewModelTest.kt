package com.example.appevalaucion.viewmodel

import com.example.appevalaucion.repository.PastelitosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductosViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Seteamos el hilo principal para pruebas
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun listaVacia() {
        // 1. Given (Dado)
        val mockRepository = mockk<PastelitosRepository>(relaxed = true)
        val viewModel = ProductosViewModel()

        // 2. When (Cuando) - Iniciamos (la carga es asíncrona, así que al principio está vacía)
        val productos = viewModel.productos.value

        // 3. Then (Entonces)
        assertTrue(productos.isEmpty())
    }
}
