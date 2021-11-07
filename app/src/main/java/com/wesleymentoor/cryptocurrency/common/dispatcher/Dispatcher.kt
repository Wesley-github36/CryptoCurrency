package com.wesleymentoor.cryptocurrency.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    fun ioDispatcher(): CoroutineDispatcher
    fun defaultDispatcher(): CoroutineDispatcher
    fun mainDispatcher(): CoroutineDispatcher
}