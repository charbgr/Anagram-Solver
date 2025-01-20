package com.github.charbgr.anagramsolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.charbgr.anagramsolver.designsystem.theme.AppTheme
import com.github.charbgr.anagramsolver.designsystem.theme.text.AppText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppText(
                        text = "Hello world!",
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }
            }
        }
    }
}
