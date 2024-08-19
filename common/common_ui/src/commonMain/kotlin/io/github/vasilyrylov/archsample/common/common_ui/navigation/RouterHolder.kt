package io.github.vasilyrylov.archsample.common.common_ui.navigation

class RouterHolder<T>(private var routerInstance: T?) {
    val router: T
        get() = routerInstance ?: error("Router is not initialized")

    fun updateInstance(newRouterInstance: T) {
        routerInstance = newRouterInstance
    }
}
