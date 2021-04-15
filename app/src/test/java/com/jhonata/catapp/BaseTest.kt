package com.jhonata.catapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    open fun setup() {
        Dispatchers.setMain(dispatcher)
    }
}