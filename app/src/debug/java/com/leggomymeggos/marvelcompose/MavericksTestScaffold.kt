package com.leggomymeggos.marvelcompose

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.launcher.MavericksLauncherMockActivity
import com.airbnb.mvrx.mocking.MavericksViewMocks
import com.airbnb.mvrx.mocking.MockableMavericksView
import com.airbnb.mvrx.mocking.mockSingleViewModel
import com.leggomymeggos.marvelcompose.data.Character
import com.leggomymeggos.marvelcompose.ui.main.CharacterListViewModel
import com.leggomymeggos.marvelcompose.ui.main.CharacterScreen
import com.leggomymeggos.marvelcompose.ui.main.State
import dagger.hilt.android.AndroidEntryPoint

// TODO fix this so the test uses a mock viewModel

class MavericksTestApplication : MainApplication() {
    override fun onCreate() {
        super.onCreate()
        MavericksLauncherMockActivity.activityToShowMock = MavericksTestActivity::class
    }
}

@AndroidEntryPoint
class MavericksTestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mavericks)
    }
}

class MavericksTestFragment : Fragment(), MockableMavericksView {
    private val viewModel: CharacterListViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                CharacterScreen(viewModel)
            }
        }
    }

    override fun provideMocks(): MavericksViewMocks<out MockableMavericksView, out Parcelable> {
        return mockSingleViewModel(
            viewModelReference = MavericksTestFragment::viewModel,
            defaultState = State(),
            defaultArgs = null
        ) {
            state("Empty list") {
                State()
            }
            state("Populated list") {
                State(characterList = listOf(
                    Character(id = 1, name = "Spider-Man", thumbnailUrl = null),
                    Character(id = 2, name = "Venom", thumbnailUrl = null),
                ))
            }
        }
    }

    // this fragment is only to be run in tests; the state will never update
    // so the view never needs to be invalidated
    override fun invalidate() = Unit
}