package io.github.pridu.wavenavigationbarsample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme

@Composable
fun CallScreen(
    paddingValues: PaddingValues
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues = paddingValues)
    ) {
        repeat(50) { index ->
            Text(
                text = "Number ${index + 1}",
                modifier = Modifier.padding(16.dp)
            )

            if (index != 49) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallScreenPreview() {
    WaveNavigationBarSampleTheme {
        CallScreen(PaddingValues(0.dp))
    }
}