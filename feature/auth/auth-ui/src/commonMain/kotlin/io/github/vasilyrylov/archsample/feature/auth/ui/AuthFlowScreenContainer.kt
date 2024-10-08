package io.github.vasilyrylov.archsample.feature.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.vasilyrylov.archsample.feature.auth.ui.data.LoginViewState
import io.github.vasilyrylov.archsample.feature.auth.ui.data.RegistrationViewState
import io.github.vasilyrylov.archsample.feature.auth.ui.data.UserAuthorizedViewState
import io.github.vasilyrylov.archsample.feature.auth.ui.screen.LoginScreen
import io.github.vasilyrylov.archsample.feature.auth.ui.screen.RegistrationScreen

@Composable
fun AuthFlowScreenContainer(viewModel: AuthViewModel) {

    val viewState by viewModel.viewState.collectAsState()

    when (val authViewState = viewState) {
        is LoginViewState -> LoginScreen(
            viewState = authViewState,
            onChangeLoginData = viewModel::handleChangeLoginData,
            startAuthenticating = viewModel::startAuthenticating,
            toRegistration = viewModel::toRegistration
        )

        is RegistrationViewState -> RegistrationScreen(
            viewState = authViewState,
            onBackClick = viewModel::toLogin,
            handleChangeRegistrationData = viewModel::handleChangeRegistrationData,
            startRegistration = viewModel::startRegistration,
            declineRegistrationData = viewModel::declineRegistrationData,
            confirmRegistrationData = viewModel::confirmRegistrationData
        )

        is UserAuthorizedViewState -> Unit
    }
}
