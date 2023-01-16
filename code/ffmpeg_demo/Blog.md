# #博客大赛#浅谈ffmpeg

## 为什么要分享这个玩意
&nbsp;&nbsp;&nbsp;&nbsp;<font size=4 color=red>**One day,**</font> 我在mac上下载了一部电影，然后想拷贝到U盘中，然后再连接电视上的接口。这时候呢，正当我要拷贝电影的时候，遇到了我的知识盲区了，为啥我的U盘能检测到，但是电脑上的文件没法往U盘里头送，但是U盘里面东西又可以往外面传呢？我尝试了下面几种方法：
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

## ffmpeg的一些基础用法

## 心得

## 展望