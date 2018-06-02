package com.bmpak.anagramsolver.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.framework.navigator.RealNavigator
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.FRANCE
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingPresenter
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingView
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingViewModel
import com.bmpak.anagramsolver.utils.FlagView
import com.bmpak.anagramsolver.utils.locationInWindow
import com.bmpak.anagramsolver.utils.onEnd
import com.google.android.material.button.MaterialButton


class OnboardingScreen : AppCompatActivity(), OnboardingView {

  private lateinit var presenter: OnboardingPresenter

  private lateinit var rootLayout: ConstraintLayout
  private lateinit var title: TextFlipper
  private lateinit var pickLanguageTitle: TextView

  private lateinit var englishIv: FlagView
  private lateinit var greekIv: FlagView
  private lateinit var frenchIv: FlagView
  private lateinit var germanIv: FlagView

  private lateinit var installBtn: MaterialButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_onboarding)
    findViews()
    setUpViews()
    setUpPresenter()
    animateBackground()
    animateContent()
  }

  override fun onResume() {
    presenter.init(this)
    super.onResume()
  }

  override fun onStop() {
    presenter.destroy()
    super.onStop()
  }

  private fun findViews() {
    rootLayout = findViewById(R.id.onboarding_screen_frame)
    title = findViewById(R.id.onboarding_title)
    pickLanguageTitle = findViewById(R.id.pick_language_title)

    englishIv = findViewById(R.id.english)
    greekIv = findViewById(R.id.greek)
    frenchIv = findViewById(R.id.french)
    germanIv = findViewById(R.id.german)

    installBtn = findViewById(R.id.onboarding_install_button)
  }

  private fun setUpViews() {
    englishIv.setOnClickListener { presenter.dictionaryClicked(ENGLISH) }
    greekIv.setOnClickListener { presenter.dictionaryClicked(GREEK) }
    frenchIv.setOnClickListener { presenter.dictionaryClicked(FRANCE) }
    germanIv.setOnClickListener { presenter.dictionaryClicked(GERMAN) }

    installBtn.setOnClickListener {
      Toast.makeText(this, presenter.viewModel.pickedDictionaries.toString(),
          Toast.LENGTH_LONG).show()
      presenter.installDictionaries()
    }
  }

  private fun animateBackground() {
    with(rootLayout.background) {
      this as AnimationDrawable
      setEnterFadeDuration(4000)
      setExitFadeDuration(2000)
      start()
    }
  }

  private fun animateContent() {
    installBtn.alpha = 0f
    pickLanguageTitle.alpha = 0f
    englishIv.alpha = 0f
    greekIv.alpha = 0f
    frenchIv.alpha = 0f
    germanIv.alpha = 0f

    title.setCurrentText(R.string.onboarding_welcome)
    changeTitleTextDelayed(R.string.onboarding_lets_you_find, 1500)
    changeTitleTextDelayed(R.string.onboarding_any_anagram, 3300)
    changeTitleTextDelayed(R.string.onboarding_first_step, 5000)
    changeTitleTextDelayed(R.string.onboarding_get_to_know, 6000)

    title.postDelayed({
      val plTitleLoc = pickLanguageTitle.locationInWindow
      val titleLoc = title.locationInWindow
      val yDelta = (plTitleLoc.y - titleLoc.y).toFloat()
      pickLanguageTitle.translationY = titleLoc.y.toFloat()

      val animatorSet1 = AnimatorSet()
      animatorSet1.duration = 300
      animatorSet1.playTogether(
          ObjectAnimator.ofFloat(title, "translationY", 0f, yDelta),
          ObjectAnimator.ofFloat(title, "alpha", 1f, 0f),
          ObjectAnimator.ofFloat(pickLanguageTitle, "translationY", 0f),
          ObjectAnimator.ofFloat(pickLanguageTitle, "alpha", 0f, 1f)
      )

      val animatorSet3 = AnimatorSet()
      animatorSet3.playTogether(
          ObjectAnimator.ofFloat(englishIv, "alpha", 0f, 1f),
          ObjectAnimator.ofFloat(greekIv, "alpha", 0f, 1f),
          ObjectAnimator.ofFloat(frenchIv, "alpha", 0f, 1f),
          ObjectAnimator.ofFloat(germanIv, "alpha", 0f, 1f),
          ObjectAnimator.ofFloat(installBtn, "alpha", 0f, 0.5f)
      )

      AnimatorSet().apply {
        playSequentially(animatorSet1, animatorSet3)
        onEnd { presenter.initialOnboardingAnimationEnd() }
        start()
      }
    }, 7200)
  }

  private fun setUpPresenter() {
    presenter = OnboardingPresenter(RealNavigator(this))
  }

  private fun changeTitleTextDelayed(resId: Int, duration: Long = 300) {
    title.postDelayed({ title.setText(resId) }, duration)
  }

  private fun enableInstallButton() {
    installBtn.isEnabled = true
    installBtn.animate().alpha(1f).start()
  }

  private fun disableInstallButton() {
    installBtn.isEnabled = false
    installBtn.animate().alpha(0.5f).start()
  }

  private fun toggleFlag(dictionary: Dictionary, isAboutToPicked: Boolean) {
    val view = findDictionaryView(dictionary)
    if (isAboutToPicked) {
      view.pick()
    } else {
      view.unpick()
    }
  }

  private fun findDictionaryView(dictionary: Dictionary): FlagView = when (dictionary) {
    ENGLISH -> englishIv
    GREEK -> greekIv
    FRANCE -> frenchIv
    GERMAN -> germanIv
  }

  override fun bind(viewModel: OnboardingViewModel) {
    if (viewModel.shouldEnableInstallButton) {
      enableInstallButton()
    } else {
      disableInstallButton()
    }

    viewModel.pickedDictionaries.forEach {
      toggleFlag(it.key, it.value)
    }
  }
}
