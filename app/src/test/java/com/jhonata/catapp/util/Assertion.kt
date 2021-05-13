package com.jhonata.catapp.util

import org.junit.Assert

class Assertion<T>(private val target: T) {
    infix fun isEqualTo(other: T) {
        Assert.assertEquals(other, target)
    }

    infix fun isDifferentFrom(other: T) {
        Assert.assertNotEquals(other, target)
    }
}