package com.example.appevalaucion.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private lateinit var viewModel: UsuarioViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UsuarioViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun registrarUsuarioActualizaElStateflowDelUsuario() = runTest {
        // Given
        val nombre = "Test User"
        val email = "test@example.com"
        val contrasena = "password123"

        // When
        viewModel.registrarUsuario(nombre, email, contrasena)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val usuarioActual = viewModel.usuario.value
        assertNotNull(usuarioActual)
        assertEquals(nombre, usuarioActual?.nombre)
        assertEquals(email, usuarioActual?.email)
        assertEquals(contrasena, usuarioActual?.contrasena)
    }

    @Test
    fun loginActualizaElUsuarioCuandoLasCredencialesSonCorrectas() = runTest {
        // Given
        val email = "test@duoc.cl"
        val contrasena = "password"

        // When
        viewModel.login(email, contrasena)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val usuarioActual = viewModel.usuario.value
        assertNotNull(usuarioActual)
        assertEquals("Pablo", usuarioActual?.nombre)
        assertEquals(email, usuarioActual?.email)
    }

    @Test
    fun loginNoActualizaElUsuarioCuandoElEmailEstaVacio() = runTest {
        // Given
        val email = ""
        val contrasena = "password"

        // When
        viewModel.login(email, contrasena)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val usuarioActual = viewModel.usuario.value
        assertNull(usuarioActual)
    }

    @Test
    fun logoutLimpiaLosDatosDelUsuario() = runTest {
        // Given
        viewModel.login("test@duoc.cl", "password")
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.usuario.value)

        // When
        viewModel.logout()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertNull(viewModel.usuario.value)
    }
}
