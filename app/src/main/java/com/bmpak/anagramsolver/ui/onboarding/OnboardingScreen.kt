package com.bmpak.anagramsolver.ui.onboarding

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bmpak.anagramsolver.R

class OnboardingScreen : AppCompatActivity() {

  private lateinit var rootLayout: ViewGroup

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_onboarding)
    findViews()
    startBackgroundFading()
  }

  private fun findViews() {
    rootLayout = findViewById(R.id.onboarding_screen_frame)
  }

  private fun startBackgroundFading() {
    with(rootLayout.background) {
      this as AnimationDrawable
      setEnterFadeDuration(4000)
      setExitFadeDuration(2000)
      start()
    }
  }
}
