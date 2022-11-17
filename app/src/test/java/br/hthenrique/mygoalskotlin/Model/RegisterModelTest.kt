package br.hthenrique.mygoalskotlin.Model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class RegisterModelTest{
    private val expectedRegisterModel: RegisterModel = RegisterModel()
    @Before
    fun setUp(){
        expectedRegisterModel.password = "Test"
        expectedRegisterModel.confirmPassword = "Test"
    }

    @Test
    fun registerModelFillTest(){
        val registerModel = expectedRegisterModel
        assertEquals(expectedRegisterModel.password, registerModel.password)
        assertEquals(expectedRegisterModel.confirmPassword, registerModel.confirmPassword)
    }
}