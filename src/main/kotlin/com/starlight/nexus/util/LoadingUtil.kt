package com.starlight.nexus.util

object LoadingUtil : Runnable{

    private lateinit var loading: String

    override fun run() {
        val loadings: List<String> = listOf(".", "..", "...")
        val b = intArrayOf(0)
        if (b[0] == loadings.size) b[0] = 0
        loading = loadings[b[0]++]
    }

    fun getLoadingAnimation() : String {
        return loading
    }

}