# RTMP (real-time message protocol)

* 这个协议的作用是用来规范流媒体的delivery和ingest（streaming protocol via TCP）
* 和HLS之间的关系是什么？HLS是一种stream的解决方案 (streaming protocol based on http)
  * 协议使用包括两部分，第一部分是描述文件，`m3u8`， 第二部分是可播放的`ts`音视频文件
* 我们为什么需要RTMP
  * RMTP使用的是TCP协议做的底层的传输协议，这样就可以保证低延迟，但是RMTP依赖于flash的播放器，但是在浏览器中已经停止了对`flash`的支持，所以浏览器在播放的时候就需要安装其他的插件
  * 应该说是需要`RTMP server`来播放`HLS`
    * m3u8文件如何播放
      * 直接使用ffplay
        * `ffplay -v quiet -y 200 <path of the resource>`
      * 浏览器直接播放（html5的video），不需要额外的传输服务器
        * how to
      * 搭建RTMP server
        * how to
      * 搭建SRT server
        * how to
