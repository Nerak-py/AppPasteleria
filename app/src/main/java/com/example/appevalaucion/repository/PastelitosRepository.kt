package com.example.appevalaucion.repository

import android.util.Log
import com.example.appevalaucion.model.Pastelitos
import com.example.appevalaucion.remote.RetrofitInstance

class PastelitosRepository {
    private val apiService = RetrofitInstance.api

    suspend fun recuperarPastelitos(): List<Pastelitos>? {
        return try {
            Log.d("API", "Iniciando petición GET pastelitos")
            val response = apiService.getPastelitos()
            Log.d("API", "Respuesta recibida: código ${response.code()}")

            if (response.isSuccessful) {
                Log.d("API", "Éxito: ${response.body()?.size ?: 0} pastelitos")
                response.body()
            } else {
                Log.e("API", "Error: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API", "Excepción: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun grabarPastelito(p: Pastelitos): Boolean{
        val response = apiService.savePastelito(p)
        if (response.isSuccessful){
            return true
        }else{
            return false
        }
    }


    suspend fun encontrarPastelito(id: String): Pastelitos?{
        return try {
            val response = apiService.findPastelito(id)
            if (response.isSuccessful){
                response.body()
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

    suspend fun eliminarPastelito(id: String): Boolean{
        return try {
            val response = apiService.deletePastelito(id)
            response.isSuccessful
        }catch (e: Exception){
            false
        }
    }

    suspend fun updatePastelito(pastelitos: Pastelitos): Pastelitos?{
        return try {
            val response = apiService.updatePastelito(id = pastelitos.id, pastelitos = pastelitos)
            if (response.isSuccessful){
                response.body()
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }
}