package com.bmpak.anagramsolver.framework.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bmpak.anagramsolver.ui.search.SearchScreen
import com.bmpak.anagramsolver.ui.onboarding.OnboardingScreen

class RealNavigator(
    private val context: Context
) : Navigator {

  override fun toOnboarding() {
    val intent = createIntent(OnboardingScreen::class.java)
    context.startActivity(intent)
  }

  override fun toMainScreen() {
    val intent = createIntent(SearchScreen::class.java)
    context.startActivity(intent)
  }

  private fun createIntent(klazz: Class<*>): Intent = Intent(context, klazz).apply {
    if (context !is Activity) {
      this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
  }
}
