package br.hthenrique.mygoalskotlin.Model

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
    }

    @Test
    fun testUser(){
        val testUser = expectedUser

        assertEquals(expectedUser, testUser)
    }

}