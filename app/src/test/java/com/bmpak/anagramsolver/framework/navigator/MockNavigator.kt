package com.bmpak.anagramsolver.framework.navigator

import com.bmpak.anagramsolver.Tape

class MockNavigator : Navigator {

  var toOnboardingTape = Tape.create<Unit>()
  var toMainScreenTape = Tape.create<Unit>()

  override fun toOnboarding() = toOnboardingTape.add(Unit)
  override fun toMainScreen() = toMainScreenTape.add(Unit)
}
