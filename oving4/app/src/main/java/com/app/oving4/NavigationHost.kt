package com.app.oving4

import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.oving4.ui.screens.FriendDetail
import com.app.oving4.ui.screens.FriendForm
import com.app.oving4.ui.screens.FriendList

@Composable
fun FriendNavHost(
    modifier: Modifier = Modifier,
    viewModel: FriendViewModel = viewModel()
) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = FriendListNav,
        modifier = modifier
    ) {
        composable<FriendListNav> {
            FriendList(
                friends = viewModel.friends,
                onFriendClick = { index -> nav.navigate(FriendDetailNav(index)) },
                onAddClick = { nav.navigate(FriendFormNav) }
            )
        }

        composable<FriendFormNav> {
            FriendForm(
                name = viewModel.name,
                birthday = viewModel.birthday,
                buttonText = stringResource(R.string.add_friend),
                onNameChange = { viewModel.changeName(it) },
                onBirthdayChange = { viewModel.changeBirthday(it) },
                onSubmit = {
                    viewModel.addFriend()
                    nav.popBackStack()
                },
                onBack = {
                    viewModel.clearForm()
                    nav.popBackStack()
                }
            )
        }

        composable<FriendEditNav> { entry ->
            val route: FriendEditNav = entry.toRoute()

            LaunchedEffect(route.index) {
                viewModel.startEdit(route.index)
            }

            FriendForm(
                name = viewModel.name,
                birthday = viewModel.birthday,
                buttonText = stringResource(R.string.save_friend),
                onNameChange = { viewModel.changeName(it) },
                onBirthdayChange = { viewModel.changeBirthday(it) },
                onSubmit = {
                    viewModel.saveEdit(route.index)
                    nav.popBackStack()
                },
                onBack = {
                    viewModel.clearForm()
                    nav.popBackStack()
                }
            )
        }

        composable<FriendDetailNav> { entry ->
            val route: FriendDetailNav = entry.toRoute()
            val friend = viewModel.friends[route.index]
            FriendDetail(
                friend = friend,
                onEditClick = { nav.navigate(FriendEditNav(route.index)) },
                onBack = { nav.popBackStack() }
            )
        }
    }
}