package com.leggomymeggos.marvelcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import com.leggomymeggos.marvelcompose.ui.main.CharacterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MavericksView {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainActivityComposable(resources.configuration.fontScale) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFontScale(resources.configuration.fontScale)
    }

    override fun invalidate() = withState(viewModel) {
        setContent { MainActivityComposable(it.fontScale) }
    }
}

@Composable
fun MainActivityComposable(fontScale: Float = 1.0f) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.app_name)) }
                )
            }
        ) {
            CharacterScreen(fontScale)
        }
    }
}
