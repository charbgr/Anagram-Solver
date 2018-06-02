package com.bmpak.anagramsolver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bmpak.anagramsolver.framework.navigator.RealNavigator

class BootTrampolineActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    //TODO ADD LOGIC TO GO FOR SEARCH OR ONBOARDING
    RealNavigator(this).toOnboarding()
    finish()
  }
}

