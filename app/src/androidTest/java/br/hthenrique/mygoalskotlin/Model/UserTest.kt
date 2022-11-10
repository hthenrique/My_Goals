package br.hthenrique.mygoalskotlin.Model

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test

internal class UserTest{
    private val expectedUser: User = User()

    @Before
    fun setup(){
        expectedUser.uid = "102jdq09wjeqiwu91"
        expectedUser.name = "Test"
        expectedUser.email = "test@test.com"
        expectedUser.position = "attacker"
        expectedUser.goals = 100
        expectedUser.matches = 80
        expectedUser.lastUpdate = "20220906101040"
    }

    @Test
    fun testUser(){
        val testUser = expectedUser

        assertEquals(expectedUser, testUser)
        assertNotNull(testUser.toString())
    }

}