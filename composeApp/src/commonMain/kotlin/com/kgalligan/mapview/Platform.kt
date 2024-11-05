package com.kgalligan.mapview

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform