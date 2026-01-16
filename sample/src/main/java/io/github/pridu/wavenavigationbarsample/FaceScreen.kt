package io.github.pridu.wavenavigationbarsample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.pridu.wavenavigationbarsample.ui.theme.WaveNavigationBarSampleTheme
import kotlin.random.Random

@Composable
fun FaceScreen(
    paddingValues: PaddingValues
) {
    val randomNumbers = rememberSaveable {
        List(50) { Random.nextInt(1, 1000) }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(paddingValues = paddingValues),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(randomNumbers.size) { index ->
            NumberItem(randomNumbers[index])
        }
    }
}

@Composable
fun NumberItem(number: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = RectangleShape,
        border = BorderStroke(0.5.dp, Color.LightGray)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = number.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FaceScreenPreview() {
    WaveNavigationBarSampleTheme {
        FaceScreen(PaddingValues(0.dp))
    }
}