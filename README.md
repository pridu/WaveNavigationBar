# WaveNavigationBar

A custom Bottom Navigation Bar library designed for Jetpack Compose.

<p>
<img src="https://github.com/pridu/WaveNavigationBar/blob/master/image/Screen_recording.gif" width="300" alt="WaveNavigationBar Demo">
<img src="https://github.com/pridu/WaveNavigationBar/blob/master/image/Screen_recording_windowInsets.gif" width="300" alt="WaveNavigationBar Demo2">
</p>

## Compatibility

To ensure stability and access to all features (like custom indicator colors), please check the compatibility table below:

| WaveNavigationBar | Recommended Compose BOM  | Minimum Android SDK |
|:-----------------:|:------------------------:|:---:|
|    **v1.2.0**     | **2024.02.00 or higher** | API 21+ |
|      v1.1.1       |   2024.02.00 or higher   | API 21+ |
|      v1.1.0       |   2024.02.00 or higher   | API 24+ |
|      v1.0.0       |   2025.12.01 or higher   | API 24+ |

## Installation

Add the following dependency to your `build.gradle.kts` file:

> [!IMPORTANT]
> Starting from **v1.1.0**, the library is optimized for **Compose BOM 2024.02.00**. Using an older version may result in `NoSuchMethodError`.

```kotlin
dependencies {
    implementation("io.github.pridu:wave-navigation-bar:1.2.0")
}
```

## Usage

You can easily implement a navigation bar using `WaveNavigationBar` and `WaveNavigationBarItem`.

### 1. Basic Configuration
```kotlin
WaveNavigationBar(
    selectedItemIndex = selectedDestination, // Selected item index (starting from 0)
    totalItems = Destination.entries.size, // Total number of items
    waveHeight = 24.dp, // Height of the rising wave effect
    containerColor = MaterialTheme.colorScheme.primary, 
    animationSpec = spring() // AnimationSpec<Float> (Optional)
)
```

```kotlin
WaveNavigationBarItem(
    selected = selectedDestination == index, // Whether the item is selected
    isColors = true, // If false, both selected and unselected colors
                     // in the 'colors' parameter will be ignored. (Default: true)
    colors = WaveNavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
        unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
        unselectedTextColor = MaterialTheme.colorScheme.inversePrimary
    ),
    iconScaleMultiple = 1.8f, // Icon scale multiplier (Optional)
    animationSpec = spring() // AnimationSpec<Float> (Optional)
)
```

### 2. Scaffold Padding Handling (Important)

Since the wave effect rises above the navigation bar's bounds, it is highly recommended to use the library's `GetPaddingValues` function when handling the `innerPadding` from a `Scaffold`.

```kotlin
Scaffold(
) { contentPadding ->
    // Use GetPaddingValues to ensure the wave effect doesn't overlap with content
    AppNavHost(
        paddingValues = GetPaddingValues(contentPadding), 
    )
}
```

```kotlin
NavHost(
) {
    Destination.entries.forEach { destination ->
        composable(destination.route) {
            when (destination) {
                Destination.HOME -> HomeScreen(paddingValues = paddingValues)
            }
        }
    }
}
```
```kotlin
@Composable
fun HomeScreen(
    paddingValues: PaddingValues
) {
    Text(
        modifier = Modifier.padding(paddingValues = paddingValues)
    )
}
```

## License
```
Copyright 2026 sj.kim

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
file except in compliance with the License. 
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed 
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
CONDITIONS OF ANY KIND, either express or implied. See the License for the specific 
language governing permissions and limitations under the License.
```
