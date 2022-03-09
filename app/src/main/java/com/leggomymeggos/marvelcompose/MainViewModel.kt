package com.leggomymeggos.marvelcompose

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: MainState
) : MavericksViewModel<MainState>(initialState) {

    fun updateFontScale(newScale: Float) {
        setState { copy(fontScale = newScale) }
    }
}

data class MainState(val fontScale: Float = 1f) : MavericksState