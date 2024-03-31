package com.revsb_11.views.composeScreens.effects

sealed class AddCommentScreenEffect {
    data object BackToPreviousScreen : AddCommentScreenEffect()
}