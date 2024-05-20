package com.starlight.nexus.command.data.processor

interface Processor<T, R> {

    fun process(type: T): R

}