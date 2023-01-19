# 浅谈ffmpeg

## 为什么要分享这个玩意
&nbsp;&nbsp;&nbsp;&nbsp;<font size=4 color=red>**One day,**</font> 我在Mac上下载了一部电影，然后想拷贝到U盘中，然后再连接电视上的接口。这时候呢，正当我要拷贝电影的时候，遇到了我的知识盲区了，为啥我的U盘能检测到，但是电脑上的文件没法往U盘里头送，但是U盘里面东西又可以往外面传呢？我尝试了下面几种方法：
* 翻箱倒柜，找出其他的U盘，一个个的试&nbsp;&nbsp;&nbsp;&nbsp;&#10006;
* 给电脑加一个hub，然后把U盘插到hub上&nbsp;&nbsp;&nbsp;&nbsp;&#10006; 
* 尝试不同的Mac&nbsp;&nbsp;&nbsp;&nbsp;&#10006;

&nbsp;&nbsp;&nbsp;&nbsp;没法子了，只能Google一下了，其实都是格式的问题，U盘和移动硬盘一般都是NTFS格式的，而Mac是不支持这个格式磁盘的写入的，所以在Mac上无法拷贝文件到U盘。
<img src="https://user-images.githubusercontent.com/6279298/211694505-dac30b23-e055-4201-85c5-54c09544cb95.png" alt="Girl in a jacket" width="60" height="60">

&nbsp;&nbsp;&nbsp;&nbsp;算了要不我直接在Mac上看吧，Mac上的唯一的播放器只有`QuickTimer`, 但是此播放器能支持的格式又是有限了，下载的电影格式一般都是`mkv`格式为主，所以QuickTime自然也就不支持的咯。这时候，解决办法想了两个。要么下载一个可以支持其他格式的播放器，要么就是转格式。我选择了后者，作为新生代农民工的我呢还是想要用自己的手艺解决这个问题，那就是写代码搞定，其实是没有找到比较好的工具。
&nbsp;&nbsp;&nbsp;&nbsp;开始Google，`how to convert mkv to mp4 in java` <img width="200" alt="how to convert mkv to mp4 in java" src="https://user-images.githubusercontent.com/6279298/211951360-4aa64823-af8b-4005-815e-53942aa3c2e5.png"> , 
`how to convert mkv to mp4 in python`<img width="200" alt="how to convert mkv to mp4 in python" src="https://user-images.githubusercontent.com/6279298/211951383-352e6dde-5ca9-4615-be68-32ba84f1cf60.png"> ,
`~ in golang` <img width="200" alt="how to convert mkv to mp4 in golang" src="https://user-images.githubusercontent.com/6279298/211951464-68d133a1-3748-4d34-a5f3-2f08333f43ed.png">
从每个搜索结果中都能发现一个相同的工具<font color=red size=3>**ffmpeg**</font>, 很好奇这个是干嘛的的东西，大家就接着往下看吧

## ffmpeg是什么
&nbsp;&nbsp;&nbsp;&nbsp;<font>**FFmpeg**</font>是一个免费的开源软件项目，由一套处理视频、音频和其他多媒体文件和流的库和程序组成。ffmpeg的主要的是被当做命令行工具来使用，ffmpeg主要用于处理视频和音频文件。它被广泛用于格式转换、基本编辑(修剪和拼接)、视频缩放、视频后期制作效果</p>
&nbsp;&nbsp;&nbsp;&nbsp;FFmpeg除了本身的工具之外，实际上是被分成了是三个工具`ffmpeg`，`ffprobe`以及`ffplay`。[ffprobe](http://ffmpeg.org/ffprobe.html)主要用于分析流媒体数据，提取流媒体中的音视频信息，[ffplay](http://ffmpeg.org/ffplay.html)是ffmpeg开发的minimal的视频播放器，但是这三类工具都是命令行工具，对于不太了解计算机的同学来说有一定的上手难度。
&nbsp;&nbsp;&nbsp;&nbsp;ffmpeg整体都是基于`C++`开发的一套框架，因为是开源项目，所以有很大的DIY空间。于此同时，对于不太熟悉`C++`的同学来说，如果想用ffmpeg来做一些二次开发，现在也有很多其他语言的wrapper包，以下这几个是star数比较多，而且提了issue作者会给回答的项目：
- java 
  - [javacpp-presets](https://github.com/bytedeco/javacpp-presets/tree/master/ffmpeg)
  - [ffmpeg-cli-wrapper](https://github.com/bramp/ffmpeg-cli-wrapper)
- go
  - [ffmpeg-go](https://github.com/u2takey/ffmpeg-go)

&nbsp;&nbsp;&nbsp;&nbsp;这里还有一个小提醒给大家，ffmpeg这么好用，其实很多的播放器都是基于了ffmpeg做了二次开发，那么对于使用ffmpeg的开发者来说，就有一条不成文的规则，如果你使用了这个框架，你就需要开源你的项目，否则你可能会被钉在ffmpeg的[Hall of Shame(耻辱柱)](http://ffmpeg.org/shame.html)

&nbsp;&nbsp;&nbsp;&nbsp;下面给大家顺带说一下，ffmpeg中使用的一些高频率的library
- libavcodec, 提供了一个通用的编码/解码框架，包含音频、视频和字幕流的多个解码器和编码器
- libavformat, 为音频、视频和字幕流的多路复用和解复用(muxing和demuxing)提供了一个通用框架
- libavfilter，音视频流的处理，包括视频的比特率修改，音频的频率的修改，以及多个视频的合并等等操作
- libavdevice，提供了一个通用框架，用于从许多常见的多媒体输入/输出设备抓取和呈现，并支持多种输入和输出设备，例如Video4Linux2、VfW、DShow和ALSA

## ffmpeg的一些基础用法

### 安装
* 大家可以根据自己的平台下载对应的安装包，在[ffmpeg官网](http://ffmpeg.org/download.html#get-sources)可以直接下载
* 在Mac电脑上还可以使用`brew`直接安装，运行以下命令：`brew install ffmpeg`

### 使用

#### ffplay

&nbsp;&nbsp;&nbsp;&nbsp;ffplay使用方式非常简单，这里给大家介绍一些简单的命令
* `ffplay <input source path>` 这样就是以ffplay提供的默认设置播放视频
* `ffplay -x -y <input source path>` 这样就可以指定视频播放窗口的大小
* `ffplay -fs <input source path>` 视频开始以全屏模式播放 </p>

&nbsp;&nbsp;&nbsp;&nbsp;除此之外，ffplay还很多其他的option，大家如果有兴趣可以参照官网的[说明](http://ffmpeg.org/ffplay.html#Main-options)尝试

#### ffprobe

&nbsp;&nbsp;&nbsp;&nbsp;ffprobe主要就是用于分析流媒体中的信息，比如说，你想知道，该视频中有几个视频流，有几个音频流，以及各自的编码方式是什么，等等诸如此类的东西，再给大家分享一些简单的命令
* `ffprobe -v error <input source path> -show_format`, 用这个命令你就可以看到你这个容器中的流媒体的一些基本数据，如果你觉得用默认格式输出不好看的话，还可以添加一个参数，`-print_format json`，其中的参数`v`代表的是verbose
  * json文件中展现了container的信息，其中包括了container的文件名称，bitrate等等
    ```json
    {
      "format": {
          "filename": "bullfinch.mov",
          "nb_streams": 2,
          "nb_programs": 0,
          "format_name": "mov,mp4,m4a,3gp,3g2,mj2",
          "format_long_name": "QuickTime / MOV",
          "start_time": "0.000000",
          "duration": "5.022000",
          "size": "143724856",
          "bit_rate": "228952379",
          "probe_score": 100,
          "tags": {
              "major_brand": "qt  ",
              "minor_version": "512",
              "compatible_brands": "qt  ",
              "encoder": "Lavf58.45.100"
          }
      }
    }
    ```
* `ffprobe -v error <input source path> -show_streams` 这里会展示出所有的stream的信息，所以使用参数`-select_streams`来选择某一种流媒体，再在后面添加一个`v`或者`a`。前者代表是video，后者是audio
  * 视频流的数据，这里会列出当前流媒体中视频流中的编码方式等等数据
  ```json
  {
    "streams": [
        {
            "index": 0,
            "codec_name": "prores",
            "codec_long_name": "Apple ProRes (iCodec Pro)",
            "profile": "HQ",
            "codec_type": "video",
            "codec_tag_string": "apch",
            "codec_tag": "0x68637061",
            "width": 1920,
            "height": 1080,
            "coded_width": 1920,
            "coded_height": 1080,
            "closed_captions": 0,
            "film_grain": 0,
            "has_b_frames": 0,
            "sample_aspect_ratio": "1:1",
            "display_aspect_ratio": "16:9",
            "pix_fmt": "yuv422p10le",
            "level": -99,
            "color_range": "tv",
            "field_order": "progressive",
            "refs": 1,
            "id": "0x1",
            "r_frame_rate": "30000/1001",
            "avg_frame_rate": "30000/1001",
            "time_base": "1/30000",
            "start_pts": 0,
            "start_time": "0.000000",
            "duration_ts": 150150,
            "duration": "5.005000",
            "bit_rate": "229589904",
            "bits_per_raw_sample": "10",
            "nb_frames": "150",
            "disposition": {
                "default": 1,
                "dub": 0,
                "original": 0,
                "comment": 0,
                "lyrics": 0,
                "karaoke": 0,
                "forced": 0,
                "hearing_impaired": 0,
                "visual_impaired": 0,
                "clean_effects": 0,
                "attached_pic": 0,
                "timed_thumbnails": 0,
                "captions": 0,
                "descriptions": 0,
                "metadata": 0,
                "dependent": 0,
                "still_image": 0
            },
            "tags": {
                "language": "eng",
                "handler_name": "VideoHandler",
                "vendor_id": "FFMP",
                "encoder": "Lavc58.91.100 prores_ks"
            }
        }
    ]
  }
  ```
  * 如果我们想过滤一些只有我们关心的数据我可以使用`-show_entries stream=codec_name,codec_type,duration`，来选择我们想要的数据，这样返回的数据就会比较简洁
  ```json
  {
    "programs": [

    ],
    "streams": [
        {
            "codec_name": "prores",
            "codec_type": "video",
            "duration": "5.005000"
        }
    ]
  }
  ```

#### ffmpeg

&nbsp;&nbsp;&nbsp;&nbsp;ffmpeg应该来说是这个里面BOSS了，能干的事情简直是太多了，说转换格式，剪辑视频，合并视频，添加视频的overlay，替换流媒体的音频，操作音频（合并声道，修改声道），生产playback文件等等

* 转换格式就是ffmpeg非常基础的技能了，`ffmpeg -v error -y -i <input source path> <output file name>.mp4`。场景我们在网络上下载了一段视频，扩展名是`mkv`。这个显然是无法在Mac直接播放的
```json
{
    "programs": [

    ],
    "streams": [
        {
            "codec_name": "h264",
            "codec_long_name": "H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10"
        }
    ],
    "format": {
        "filename": "test.mkv",
        "nb_streams": 2,
        "nb_programs": 0,
        "format_name": "matroska,webm",
        "format_long_name": "Matroska / WebM",
        "start_time": "0.000000",
        "duration": "5.016000",
        "size": "1941253",
        "bit_rate": "3096097",
        "probe_score": 100,
        "tags": {
            "COMPATIBLE_BRANDS": "qt  ",
            "MAJOR_BRAND": "qt  ",
            "MINOR_VERSION": "512",
            "ENCODER": "Lavf59.16.100"
        }
    }
}
```
我们来使用ffmpeg的转换技能，把`mkv`格式的文件转换成`mov`, `ffmpeg -v error -y -i test.mkv  test.mov`，这里不用指定流的编码方式，因为在转换的过程中会设定默认的合适的codec方法，等上一会，视频就是转好了当我点击的时候给我来了个这个<img width="100" alt="image" src="https://user-images.githubusercontent.com/6279298/213137069-6b427048-435f-4f68-a81f-3f2fd6401974.png">，打开是能打开，但是看不了视频，只闻其声不见其人。我用ffprobe分析了一下stream的数据，发现codec的方式h264，quicktime是不支持的。
这时我添加了一个参数`ffmpeg -v error -y -i test.mkv  -vcodec prores test.mov`。这波操作，OK了，可以打开了</p>
&#8194;&#8194;&#8194;&#8194;这时又有细心的朋友发现了些啥，为啥同样视频只是经过了一次转换，大小相差这么大。<img width="200" alt="image" src="https://user-images.githubusercontent.com/6279298/213139598-262c3a73-3881-42a8-92c2-e2a1e0279bda.png">。具体原因是啥呢，我再用ffprobe分析一波。原来是因为`bitrate`相差了很多哦，这玩意就是用来决定视频的清晰度的。查了一下文档，原来ffmpeg在不设定bitrate的时候，为了加快转换速度自己又自作主张的加了点戏，减少了一些bitrate。
<img width="1031" alt="image" src="https://user-images.githubusercontent.com/6279298/213140159-19c49e5d-72a3-4b9e-85ca-a1bf2d6bd7c1.png">
&#8194;&#8194;&#8194;&#8194;那如果我想保持原来的大小该怎么办呢？这里我们可以使用`two-pass`的方式来设置想要转换的视频的bitrate的大小
```text
ffmpeg -v error -y -i test.mkv -vcodec prores -b:v 4M -pass 1 -f null /dev/null
&& ffmpeg -v error -y -i test.mkv -vcodec prores -b:v 4M -pass 2 test.mov
```

## 心得

&#8194;&#8194;&#8194;&#8194;故事讲到这里，算是已经都讲完了，也实现了在Mac上直接播放的愿望。其实在学习ffmpeg的过程中还是了解到了很多图像相关的概念的，在这里我只是介绍了ffmpeg冰山一角的能力。当然在学习的过程中也越遇到一些自己一知半解的问题，如果大家有相关经验可以一块交流啊。


&#8194;&#8194;&#8194;&#8194;<font size=5>**最后**</font>预祝大家<font size=6 color=red face="微软雅黑">**新**</font><font size=4 color=yellow face="微软雅黑">**年**</font><font size=5 color=green face="微软雅黑">**快**</font><font size=8 color=pink face="微软雅黑">**乐**</font> <font size=4>**!!!**</font>
