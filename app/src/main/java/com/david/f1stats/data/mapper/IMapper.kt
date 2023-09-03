package com.david.f1stats.data.mapper

interface IMapper<F,T> {
    fun fromMap(from:F):T
}
