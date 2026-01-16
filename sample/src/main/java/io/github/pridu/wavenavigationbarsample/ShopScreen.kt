package io.github.pridu.wavenavigationbarsample

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme

@Composable
fun ShopScreen(
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        item {
            Text(
                text = "First item",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }

        items(30) { index ->
            Text(
                text = "Item: ${index + 1}",
                modifier = Modifier.padding(16.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }

        item {
            Text(
                text = "Last item",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopScreenPreview() {
    WaveNavigationBarSampleTheme {
        ShopScreen(PaddingValues(0.dp))
    }
}