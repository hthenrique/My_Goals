package br.hthenrique.mygoalskotlin.Model

import br.hthenrique.mygoalskotlin.Model.User
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

internal class UserTest{
    private val expectedUser: User =
        User()

    @Before
    fun setup(){
        expectedUser.name = "Test"
        expectedUser.email = "test@test.com"
        expectedUser.password = "Test#Test1"
    }

    @Test
    fun testUser(){
        val testUser = expectedUser

        assertEquals(expectedUser, testUser)
    }

}