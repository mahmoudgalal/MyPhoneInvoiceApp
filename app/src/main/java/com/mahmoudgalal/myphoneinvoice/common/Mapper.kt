package com.mahmoudgalal.myphoneinvoice.common

interface Mapper<in T, out V> {
    infix fun map(t:T):V
}