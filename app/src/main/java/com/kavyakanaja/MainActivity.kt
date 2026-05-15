package com.kavyakanaja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.kavyakanaja.ui.KavyaNavGraph
import com.kavyakanaja.ui.home.MainViewModel
import com.kavyakanaja.ui.theme.KavyaKanajaTheme
import com.kavyakanaja.ui.theme.Parchment

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KavyaKanajaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Parchment
                ) {
                    val navController = rememberNavController()
                    KavyaNavGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
