package io.github.vasilyrylov.archsample.feature.root.domain.fsm

import io.github.vasilyrylov.archsample.feature.root.domain.fsm.action.Initialize
import io.github.vasilyrylov.archsample.feature.root.domain.fsm.action.RootFSMAction
import io.github.vasilyrylov.archsample.feature.root.domain.usecase.GetCurrentLoggedInUserUseCase
import ru.kontur.mobile.visualfsm.AsyncWorker
import ru.kontur.mobile.visualfsm.AsyncWorkerTask

class RootAsyncWorker(
    private val getCurrentLoggedInUser: GetCurrentLoggedInUserUseCase
) : AsyncWorker<RootFSMState, RootFSMAction>() {
    override fun onNextState(state: RootFSMState): AsyncWorkerTask<RootFSMState> {
        return when (state) {
            is RootFSMState.AsyncWorkState.Initial -> checkAuthorization(state)
            else -> AsyncWorkerTask.Cancel()
        }
    }

    private fun checkAuthorization(state: RootFSMState): AsyncWorkerTask<RootFSMState> {
        return AsyncWorkerTask.ExecuteIfNotExist(state) {
            val userId = getCurrentLoggedInUser()
            proceed(Initialize(userId))
        }
    }
}
