# WaveNavigationBar

Jetpack Compose를 위해 설계된 커스텀 Bottom Navigation Bar 라이브러리입니다.
<p>
<img src="https://github.com/pridu/WaveNavigationBar/blob/master/image/Screen_recording.gif" width="300" alt="WaveNavigationBar Demo">
</p>

## Compatibility

안정적인 작동을 확인하려면 아래 호환성 표를 참조하기 바랍니다:

| WaveNavigationBar | Recommended Compose BOM | Minimum Android SDK |
|:---:|:---:|:---:|
| **v1.1.0** | **2024.02.00 or higher** | API 21+ |
| v1.0.0 | 2025.12.01 or higher | API 21+ |

## Installation

build.gradle.kts에 아래 의존성을 추가하세요

```
dependencies {
    implementation("io.github.pridu:wave-navigation-bar:1.0.0")
}
```
## Usage

`WaveNavigationBar`와 `WaveNavigationBarItem`을 사용하여 간단하게 네비게이션 바를 구현할 수 있습니다.

### 1. 기본 구성
```kotlin
WaveNavigationBar(
    selectedItemIndex = selectedDestination, // 선택된 아이템 번호(0부터 시작)
    totalItems = Destination.entries.size, // 전체 아이템 개수
    waveHeight = 24.dp // 선택된 아이템의 네비게이션 영역이 올라오는 높이
    animationSpec = // AnimationSpec<Float> (선택)
)
```
```kotlin
WaveNavigationBarItem(
    selectedItem = selectedDestination == index, // 선택된 아이템인지 여부,
    isColors = true, // 아이템의 색 적용 여부, false면 아이템의 selectedColor와 unselectedColor가 무시
    colors = WaveNavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.outline,
        unselectedTextColor = MaterialTheme.colorScheme.outline
    )
    iconScaleMultiple = 1.8f, // 아이콘 크기 배수 (선택),
    animationSpec = // AnimationSpec<Float> (선택)
)
```

### 2. Scaffold 패딩 처리(중요)

네비게이션 바의 웨이브 효과가 상단으로 솟아오르기 때문에, `Scaffold`의 `innerPadding`을 처리할 때 라이브러리에서 제공하는 `GetPaddingValues`를 사용하는 것을 권장합니다.

```kotlin
Scaffold(
) { contentPadding ->
    AppNavHost(
        paddingValues = GetPaddingValues(contentPadding), // GetPaddingValues를 사용
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
