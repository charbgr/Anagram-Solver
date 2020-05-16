package com.bmpak.anagramsolver.framework.arch

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.BiFunction

/**
 * Generic StateReducer to eliminate boilerplate.
 */

abstract class StateReducer<State, Event> {

  abstract val initialState: State

  protected abstract fun reduce(
      currentState: State,
      reactToEvent: Event
  ): State

  val reducer = BiFunction<State, Event, State> { currentState, reactToEvent ->
    reduce(currentState, reactToEvent)
  }
}


fun <State, Event>Flowable<Event>.withStateReducer(
    stateReducer: StateReducer<State, Event>
) = scan(stateReducer.initialState, stateReducer.reducer)
