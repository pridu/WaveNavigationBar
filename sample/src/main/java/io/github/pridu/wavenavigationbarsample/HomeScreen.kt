package io.github.pridu.wavenavigationbarsample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme

@Composable
fun HomeScreen(
    paddingValues: PaddingValues
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .wrapContentSize(Alignment.Center),
        text = "Hello World!"
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WaveNavigationBarSampleTheme {
        HomeScreen(PaddingValues(0.dp))
    }
}