package com.utechia.domain.usecases

interface SearchUseCase<R> {

    fun execute(name:String):MutableList<R>

}