package io.github.pridu.wavenavigationbarsample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme

@Composable
fun InfoScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        Text(
            text = "TOP",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
        )
        Text(
            text = "BOTTOM",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
        Text(
            text = "CENTER",
            modifier = Modifier
                .align(Alignment.Center)
        )
        Text(
            text = "LEFT",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
        )
        Text(
            text = "RIGHT",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InfoScreenPreview() {
    WaveNavigationBarSampleTheme {
        InfoScreen(PaddingValues(0.dp))
    }
}