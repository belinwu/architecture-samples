package io.github.vasilyrylov.archsample.feature.root.component

import com.arkivanov.decompose.ComponentContext
import io.github.vasilyrylov.archsample.common.component.createKoinScope
import io.github.vasilyrylov.archsample.common.component.createViewModel
import io.github.vasilyrylov.archsample.common.component.registerAndGetSavedState
import io.github.vasilyrylov.archsample.common.component.updateRouterInstance
import io.github.vasilyrylov.archsample.feature.root.component.api.IRootComponentDependencies
import io.github.vasilyrylov.archsample.feature.root.component.di.createRootModule
import io.github.vasilyrylov.archsample.feature.root.ui.RootViewModel
import io.github.vasilyrylov.archsample.feature.root.domain.fsm.RootFeature
import io.github.vasilyrylov.archsample.feature.root.domain.fsm.RootFSMState

class RootFlowComponent(
    componentContext: ComponentContext,
    dependencies: IRootComponentDependencies
) : ComponentContext by componentContext {


    private val savedState: RootFSMState = registerAndGetSavedState(
        key = ROOT_FSM_SAVED_STATE,
        initialValue = RootFSMState.AsyncWorkState.Initial,
        deserialization = RootFSMState.serializer(),
        serialization = RootFSMState.serializer()
    ) {
        koinScope.get<RootFeature>(RootFeature::class).getCurrentState()
    }

    private val koinScope = createKoinScope(
        listOf(createRootModule(savedState, dependencies))
    )

    val router = RootFlowRouter(componentContext, koinScope)

    init {
        koinScope.updateRouterInstance(router)
    }

    @Suppress("UNUSED")
    val viewModel = createViewModel<RootViewModel>(koinScope)

    companion object {
        private const val ROOT_FSM_SAVED_STATE = "ROOT_FSM_SAVED_STATE"
    }
}
