package br.hthenrique.mygoalskotlin.Model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class LoginModelTest{
    private val expectedLoginModel: LoginModel = LoginModel()

    @Before
    fun setUp(){
        expectedLoginModel.password = "Test"
    }

    @Test
    fun loginModelFillTest(){
        val loginModel = expectedLoginModel
        assertEquals(expectedLoginModel.password, loginModel.password)
    }
}