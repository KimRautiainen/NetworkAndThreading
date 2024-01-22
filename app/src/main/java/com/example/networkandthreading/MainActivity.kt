package com.example.networkandthreading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.networkandthreading.ui.theme.NetworkAndThreadingTheme
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetworkAndThreadingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReadFile()
                }
            }
        }
    }
}

@Composable
fun ReadFile() {
    val fileContent = remember { mutableStateOf("") }
    val buttonText = remember { mutableStateOf("Read File") }

Column {
    Button(onClick = {
        buttonText.value = "Loading..."
        thread {
        try {
            val myUrl = URL("https://users.metropolia.fi/~jarkkov/koe.txt")
            val myConn = myUrl.openConnection() as HttpsURLConnection
            myConn.requestMethod = "GET"

            val content = myConn.inputStream.bufferedReader().use { it.readText() }
            fileContent.value = content
            buttonText.value = "Read File"
        }catch (e: Exception){
            fileContent.value = "Error: ${e.message}"
            buttonText.value = "Read File"
        }
    }
    }) {
        Text(buttonText.value)
    }
    Text(fileContent.value)
}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetworkAndThreadingTheme {
        ReadFile()
    }
}