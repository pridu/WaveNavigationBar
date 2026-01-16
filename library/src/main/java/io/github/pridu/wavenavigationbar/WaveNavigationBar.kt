package io.github.pridu.wavenavigationbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


internal val NavigationBarItemHorizontalPadding: Dp = 0.dp
internal const val FineTuning = 20f
internal var itemWaveHeight: Dp = 24.dp
internal val previousSelectedItems = mutableListOf<Int>()
internal var previousSelectedItemIndex by mutableIntStateOf(0)

class SelectedItemCutoutShape(
    private val selectedItemIndex: Int,
    private val totalItems: Int,
    private val waveHeightDp: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val prvPath = Path()
        val combinedPath = Path()

        val itemWidth = size.width / totalItems
        val itemTop = with(density) { itemWaveHeight.toPx() }
        val waveHeightPx = with(density) { waveHeightDp.toPx() }
        val selectedItemCenterX = (selectedItemIndex * itemWidth) + (itemWidth / 2f)
        val prvSelectedItemCenterX = (previousSelectedItemIndex * itemWidth) + (itemWidth / 2f)

        if (previousSelectedItemIndex != selectedItemIndex) {
            prvPath.moveTo(x = prvSelectedItemCenterX - itemTop * 2.3f - FineTuning, y = itemTop)
            prvPath.lineTo(x = prvSelectedItemCenterX - itemTop * 2.3f - FineTuning, y = itemTop)

            prvPath.cubicTo(
                x1 = prvSelectedItemCenterX - itemTop * 1.3f, y1 = itemTop,
                x2 = prvSelectedItemCenterX - itemTop * 1.3f, y2 = waveHeightPx,
                x3 = prvSelectedItemCenterX, y3 = waveHeightPx
            )
            prvPath.cubicTo(
                x1 = prvSelectedItemCenterX + itemTop * 1.3f, y1 = waveHeightPx,
                x2 = prvSelectedItemCenterX + itemTop * 1.3f, y2 = itemTop,
                x3 = prvSelectedItemCenterX + itemTop * 2.3f + FineTuning, y3 = itemTop
            )

            prvPath.lineTo(x = prvSelectedItemCenterX + itemTop * 2.3f + FineTuning, y = itemTop)

            if (itemTop - waveHeightPx == 0f) {
                if (previousSelectedItems.size == 1)
                    previousSelectedItems.clear()

                previousSelectedItemIndex = selectedItemIndex
            }
        }

        if (selectedItemCenterX - waveHeightPx * 2.3f - FineTuning >= 0) {
            path.moveTo(x = 0f, y = itemTop)
            path.lineTo(x = 0f, y = itemTop)
        } else {
            path.moveTo(x = selectedItemCenterX - waveHeightPx * 2.3f - FineTuning, y = itemTop)
        }

        path.lineTo(x = selectedItemCenterX - waveHeightPx * 2.3f - FineTuning, y = itemTop)

        path.cubicTo(
            x1 = selectedItemCenterX - waveHeightPx * 1.3f, y1 = itemTop,
            x2 = selectedItemCenterX - waveHeightPx * 1.3f, y2 = itemTop - waveHeightPx,
            x3 = selectedItemCenterX, y3 = itemTop - waveHeightPx
        )
        path.cubicTo(
            x1 = selectedItemCenterX + waveHeightPx * 1.3f, y1 = itemTop - waveHeightPx,
            x2 = selectedItemCenterX + waveHeightPx * 1.3f, y2 = itemTop,
            x3 = selectedItemCenterX + waveHeightPx * 2.3f + FineTuning, y3 = itemTop
        )

        if (selectedItemCenterX + waveHeightPx * 2.3f + FineTuning <= size.width)
            path.lineTo(x = size.width, y = itemTop)
        else
            path.lineTo(x = selectedItemCenterX + waveHeightPx * 2.3f + FineTuning, y = itemTop)

        path.lineTo(x = size.width, y = size.height)
        path.lineTo(x = 0f, y = size.height)

        combinedPath.addPath(path)
        combinedPath.addPath(prvPath)
        path.close()
        prvPath.close()
        combinedPath.close()
        return Outline.Generic(combinedPath)
    }
}

class GetPaddingValues(private val paddingValues: PaddingValues) : PaddingValues {
    override fun calculateBottomPadding(): Dp {
        return paddingValues.calculateBottomPadding() - itemWaveHeight
    }

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return paddingValues.calculateLeftPadding(layoutDirection)
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return paddingValues.calculateRightPadding(layoutDirection)
    }

    override fun calculateTopPadding(): Dp {
        return paddingValues.calculateTopPadding()
    }
}

object NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

@Composable
fun WaveNavigationBar(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int = 0,
    totalItems: Int = 5,
    waveHeight: Dp = 24.dp,
    animationSpec: AnimationSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow
    ),
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    itemWaveHeight = waveHeight
    val animatedWaveHeight = remember(selectedItemIndex) { Animatable(0f) }

    if (previousSelectedItems.isEmpty() || selectedItemIndex != previousSelectedItems[previousSelectedItems.size - 1]) {
        previousSelectedItems.add(selectedItemIndex)

        val size = previousSelectedItems.size
        if (size > 1) {
            previousSelectedItemIndex = previousSelectedItems[size - 2]

            for (i in 0 until size - 1)
                previousSelectedItems.removeAt(i)
        }
    }

    LaunchedEffect(selectedItemIndex) {
        animatedWaveHeight.animateTo(
            targetValue = waveHeight.value,
            animationSpec = animationSpec
        )
    }

    Surface(
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        modifier = modifier
            .heightIn(
                min = 50.dp + waveHeight,
                max = 70.dp + waveHeight
            )
            .clip(
                shape = SelectedItemCutoutShape(
                    selectedItemIndex = selectedItemIndex,
                    totalItems = totalItems,
                    waveHeightDp = animatedWaveHeight.value.dp
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .padding(top = waveHeight + 4.dp)
                .defaultMinSize(minHeight = 50.0.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(NavigationBarItemHorizontalPadding),
            verticalAlignment = Alignment.Bottom,
            content = content
        )
    }
}

@Composable
fun RowScope.WaveNavigationBarItem(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    selectedItem: Boolean,
    iconScaleMultiple: Float = 1.8f,
    animationSpec: AnimationSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow
    )
) {
    val scale by animateFloatAsState(
        targetValue = if (selectedItem) iconScaleMultiple else 1.0f,
        animationSpec = animationSpec,
        label = "iconScaleAnimation"
    )

    val iconTransformOrigin = remember { TransformOrigin(0.5f, 1.0f) }

    NavigationBarItem(
        modifier = modifier,
        icon = {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        transformOrigin = iconTransformOrigin
                    )
            ) {
                icon()
            }
        },
        label = label,
        selected = false,
        onClick = onClick,
        interactionSource = NoRippleInteractionSource,
        enabled = enabled,
        alwaysShowLabel = alwaysShowLabel,
        colors = colors
    )
}