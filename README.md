## MediaResizer [![](https://jitpack.io/v/WindSekirun/MediaResizer.svg)](https://jitpack.io/#WindSekirun/MediaResizer) 
[![Kotlin](https://img.shields.io/badge/kotlin-1.2.0-blue.svg)](http://kotlinlang.org)	[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MediaResizer-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6700)

Resizeing image / video with *very small effort* written in **Kotlin**.

Minimum API Level is **18(Android 4.3)**

It support... 

* Image Resizing with keeping Aspect Ratio
* Video Resizing with Hardware-level (using [android-transcoder](https://github.com/ypresto/android-transcoder))
    * Encoding in 720p
    * Encoding in 480p
    * Encoding in 960x540
    * Can be set value of Bitrate of Video, Bitrate of Audio, Channel of Audio
* Resizing media with pre-well-made builder
* **100% Java Interop Library**, it can use with Java within Kotlin-plugin integrated project

### Usages
*rootProject/build.gradle*
```	
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

*app/build.gradle*
```
dependencies {
    implementation 'com.github.WindSekirun:MediaResizer:1.1.0'
}
```

### Initialization
For use ```.setScanRequest(ScanRequest.TRUE)```, Please add this statement in your Application class.

#### Kotlin
```Kotlin
MediaResizerGlobal.initializeApplication(this)
```

#### Java
```Java
MediaResizerGlobal.initializeApplication(this);
MediaResizerGlobal.INSTANCE.initializeApplication(this); // use this statement in 1.0.0
```

### Resizing Image
MediaResizer support Image Resizing with keeping Aspect Ratio of picture.

from 1.1.0, MediaResizer support 'resizing image by synchronous'. To use, use ```MediaResizer.processSynchronously``` instead ```MediaResizer.process```. (Thanks to @zeallat )

* [Documentation of ImageResizeOption](https://windsekirun.github.io/MediaResizer/-media-resizer/pyxis.uzuki.live.mediaresizer.data/-image-resize-option/-builder/index.html)
* [Documentation of ResizeOption](https://windsekirun.github.io/MediaResizer/-media-resizer/pyxis.uzuki.live.mediaresizer.data/-resize-option/-builder/index.html)

#### [Kotlin](https://github.com/WindSekirun/MediaResizer/blob/master/sample/src/main/java/pyxis/uzuki/live/mediaresizersample/activity/KotlinActivity.kt)
```Kotlin
val resizeOption = ImageResizeOption.Builder()
                .setImageProcessMode(ImageMode.ResizeAndCompress)
                .setImageResolution(1280, 720)
                .setBitmapFilter(false)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setCompressQuality(75)
                .setScanRequest(ScanRequest.TRUE)
                .build()
        
val option = ResizeOption.Builder()
                .setMediaType(MediaType.IMAGE)
                .setImageResizeOption(resizeOption)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->  // doesn't require when using ```processSynchronously```
                    txtStatus.text = displayImageResult(code, path, output)
                    progress.dismiss()
                })
                .build()

MediaResizer.process(option)
````

#### [Java](https://github.com/WindSekirun/MediaResizer/blob/master/sample/src/main/java/pyxis/uzuki/live/mediaresizersample/activity/JavaActivity.java)

```Java
ImageResizeOption resizeOption = new ImageResizeOption.Builder()
                .setImageProcessMode(ImageMode.ResizeAndCompress)
                .setImageResolution(1280, 720)
                .setBitmapFilter(false)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setCompressQuality(75)
                .setScanRequest(ScanRequest.TRUE)
                .build();

ResizeOption option = new ResizeOption.Builder()
                .setMediaType(MediaType.IMAGE)
                .setImageResizeOption(resizeOption)
                .setTargetPath(path)
                .setOutputPath(imageFile.getAbsolutePath())
                .setCallback((code, output) -> {
                    txtStatus.setText(ResultBuilder.displayImageResult(code, path, output));
                    progress.dismiss();
                }).build();

MediaResizer.process(option);
```

### Resizing Video
MediaResizer support Video Resizing with Hardware-level using [android-transcoder](https://github.com/ypresto/android-transcoder)

Video can be resizing when video has 16:9 ratio, see [android-transcoder #40 comment](https://github.com/ypresto/android-transcoder/issues/40)

* [Documentation of VideoResizeOption](https://windsekirun.github.io/MediaResizer/-media-resizer/pyxis.uzuki.live.mediaresizer.data/-video-resize-option/-builder/index.html)
* [Documentation of ResizeOption](https://windsekirun.github.io/MediaResizer/-media-resizer/pyxis.uzuki.live.mediaresizer.data/-resize-option/-builder/index.html)

#### [Kotlin](https://github.com/WindSekirun/MediaResizer/blob/master/sample/src/main/java/pyxis/uzuki/live/mediaresizersample/activity/KotlinActivity.kt)
```Kotlin
val resizeOption = VideoResizeOption.Builder()
                .setVideoResolutionType(VideoResolutionType.AS720)
                .setVideoBitrate(1000 * 1000)
                .setAudioBitrate(128 * 1000)
                .setAudioChannel(1)
                .setScanRequest(ScanRequest.TRUE)
                .build()

val option = ResizeOption.Builder()
                .setMediaType(MediaType.VIDEO)
                .setVideoResizeOption(resizeOption)
                .setTargetPath(path)
                .setOutputPath(imageFile.absolutePath)
                .setCallback({ code, output ->
                    txtStatus.text = displayVideoResult(code, path, output)
                    progress.dismiss()
                })
                .build()

MediaResizer.process(option)
````

#### [Java](https://github.com/WindSekirun/MediaResizer/blob/master/sample/src/main/java/pyxis/uzuki/live/mediaresizersample/activity/JavaActivity.java)

```Java
VideoResizeOption resizeOption = new VideoResizeOption.Builder()
                .setVideoResolutionType(VideoResolutionType.AS720)
                .setVideoBitrate(1000 * 1000)
                .setAudioBitrate(128 * 1000)
                .setAudioChannel(1)
                .setScanRequest(ScanRequest.TRUE)
                .build();

ResizeOption option = new ResizeOption.Builder()
                .setMediaType(MediaType.VIDEO)
                .setVideoResizeOption(resizeOption)
                .setTargetPath(path)
                .setOutputPath(imageFile.getAbsolutePath())
                .setCallback((code, output) -> {
                    txtStatus.setText(ResultBuilder.displayVideoResult(code, path, output));
                    progress.dismiss();
                }).build();

MediaResizer.process(option);
```

### Sample
You can use sample of MediaResizer, [APK is here](https://github.com/WindSekirun/MediaResizer/raw/master/sample-debug.apk)

### Third-party libraries
MediaResizer use Third-parth libraries for development.

* [android-transcoder](https://github.com/ypresto/android-transcoder)
* [RichUtilsKt](https://github.com/WindSekirun/RichUtilsKt)
* [PyxInjector](https://github.com/WindSekirun/PyxInjector)

for details, see [third-party.md](https://github.com/WindSekirun/MediaResizer/blob/master/third-party.md)

### License 
```
Copyright 2017 WindSekirun (DongGil, Seo)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
