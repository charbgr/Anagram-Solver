plugins {
    id("com.github.anagramsolver.android")
}

android {
    namespace = "com.github.charbgr.anagramsolver.designsystem"
}

appAndroid {
    compose(
        ui = true,
        foundation = true,
        material = true
    )
}
