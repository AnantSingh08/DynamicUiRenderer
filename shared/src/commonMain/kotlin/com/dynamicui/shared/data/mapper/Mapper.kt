package com.dynamicui.shared.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}