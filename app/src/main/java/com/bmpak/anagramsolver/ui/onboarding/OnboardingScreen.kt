package com.bmpak.anagramsolver.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.work.Configuration
import androidx.work.WorkManager
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.model.Dictionary
import com.bmpak.anagramsolver.model.Dictionary.ENGLISH
import com.bmpak.anagramsolver.model.Dictionary.FRANCE
import com.bmpak.anagramsolver.model.Dictionary.GERMAN
import com.bmpak.anagramsolver.model.Dictionary.GREEK
import com.bmpak.anagramsolver.model.DownloadStatus
import com.bmpak.anagramsolver.model.DownloadStatus.Downloading
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingPresenter
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.DOWNLOAD_LANGUAGES
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.INSTALL_LANGUAGE
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingStep.PICKING_LANGUAGE
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingView
import com.bmpak.anagramsolver.ui.onboarding.arch.OnboardingViewModel
import com.bmpak.anagramsolver.utils.FlagView
import com.bmpak.anagramsolver.utils.gone
import com.bmpak.anagramsolver.utils.locationInWindow
import com.bmpak.anagramsolver.utils.onEnd
import com.bmpak.anagramsolver.utils.visible
import com.bmpak.anagramsolver.utils.visibleOrGone
import com.bmpak.anagramsolver.utils.visibleOrInvisible
import com.google.android.material.button.MaterialButton


class OnboardingScreen : AppCompatActivity(), OnboardingView {


  private lateinit var presenter: OnboardingPresenter

  private lateinit var rootLayout: ConstraintLayout
  private lateinit var title: TextFlipper
  private lateinit var secondaryTitle: TextFlipper
  private lateinit var secondarySubTitle: TextView

  private lateinit var englishIv: FlagView
  private lateinit var greekIv: FlagView
  private lateinit var frenchIv: FlagView
  private lateinit var germanIv: FlagView

  private lateinit var installBtn: MaterialButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.screen_onboarding)
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
    secondaryTitle = findViewById(R.id.secondary_title)
    secondarySubTitle = findViewById(R.id.secondary_title_subtitle)

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
    secondaryTitle.setCurrentText(R.string.onboarding_pick_languages_title)

    installBtn.setOnClickListener { presenter.installStepClicked() }
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
    secondaryTitle.alpha = 0f
    englishIv.alpha = 0f
    greekIv.alpha = 0f
    frenchIv.alpha = 0f
    germanIv.alpha = 0f

    title.setCurrentText(R.string.onboarding_welcome)
    title.setTextDelayed(R.string.onboarding_lets_you_find, 1500)
    title.setTextDelayed(R.string.onboarding_any_anagram, 3300)
    title.setTextDelayed(R.string.onboarding_first_step, 5000)
    title.setTextDelayed(R.string.onboarding_get_to_know, 6000)

    title.postDelayed({
      val plTitleLoc = secondaryTitle.locationInWindow
      val titleLoc = title.locationInWindow
      val yDelta = (plTitleLoc.y - titleLoc.y).toFloat()
      secondaryTitle.translationY = titleLoc.y.toFloat()

      val animatorSet1 = AnimatorSet()
      animatorSet1.duration = 300
      animatorSet1.playTogether(
          ObjectAnimator.ofFloat(title, "translationY", 0f, yDelta),
          ObjectAnimator.ofFloat(title, "alpha", 1f, 0f),
          ObjectAnimator.ofFloat(secondaryTitle, "translationY", 0f),
          ObjectAnimator.ofFloat(secondaryTitle, "alpha", 0f, 1f)
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
    presenter = OnboardingPresenter.create(this)
  }

  private fun enableInstallButton() {
    installBtn.isEnabled = true
    installBtn.animate().alpha(1f).start()
  }

  private fun disableInstallButton() {
    installBtn.isEnabled = false
    installBtn.animate().alpha(0.5f).start()
  }

  private fun pickUnPickDictionary(dictionary: Dictionary, isAboutToPicked: Boolean) {
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

  private fun bindInstallButton(viewModel: OnboardingViewModel) {
    if (!viewModel.shouldShowInstallStep) {
      installBtn.gone()
    } else {
      if (viewModel.shouldEnableInstallButton) {
        enableInstallButton()
      } else {
        disableInstallButton()
      }
    }
  }

  private fun bindLanguages(viewModel: OnboardingViewModel) {
    when (viewModel.currentStep) {
      PICKING_LANGUAGE -> {
        viewModel.pickedDictionaries.forEach {
          pickUnPickDictionary(it.key, it.value)
        }
      }
      DOWNLOAD_LANGUAGES -> {
        TransitionManager.beginDelayedTransition(rootLayout, AutoTransition())
        viewModel.pickedDictionaries.forEach {
          findDictionaryView(it.key).visibleOrGone(it.value)
        }
      }
    }

    val clickable = viewModel.shouldLanguagesBeClickable
    englishIv.isClickable = clickable
    greekIv.isClickable = clickable
    frenchIv.isClickable = clickable
    germanIv.isClickable = clickable
  }

  private fun bindTitle(viewModel: OnboardingViewModel) {
    secondaryTitle.visibleOrInvisible(viewModel.shouldShowTitle)
    when (viewModel.currentStep) {
      PICKING_LANGUAGE -> secondaryTitle.setCurrentText(viewModel.titleResId)
      INSTALL_LANGUAGE, DOWNLOAD_LANGUAGES -> {
        secondaryTitle.postDelayed({
          secondaryTitle.setText(viewModel.titleResId)
          bindSubTitle(viewModel)
        }, 1500)
      }
    }
  }

  private fun bindSubTitle(viewModel: OnboardingViewModel) {
    if (!viewModel.shouldShowSubtitle) {
      secondarySubTitle.gone()
      return
    }

    secondarySubTitle.visible()
    secondarySubTitle.text = getString(viewModel.subtitleResId)
  }

  override fun bind(viewModel: OnboardingViewModel) {
    bindInstallButton(viewModel)
    bindLanguages(viewModel)
    bindTitle(viewModel)
  }

  override fun showDownloadingFeedback() {
    secondaryTitle.setText(R.string.onboarding_great)
  }

  override fun bindDownloadStatus(dictionary: Dictionary, downloadStatus: DownloadStatus) {
    when (downloadStatus) {
      is Downloading -> {
        findDictionaryView(dictionary).animateToProgress(downloadStatus.percentage)
      }
      else -> {
        // TO DO HANDLE ALL CASES
        secondaryTitle.setText(downloadStatus.toString())
      }
    }
  }
}
