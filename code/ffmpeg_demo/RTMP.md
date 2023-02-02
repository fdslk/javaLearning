# RTMP (real-time message protocol)

* 这个协议的作用是用来规范流媒体的delivery和ingest（streaming protocol via TCP）
* 和HLS之间的关系是什么？HLS是一种stream的解决方案 (streaming protocol based on http)
  * 协议使用包括两部分，第一部分是描述文件，`m3u8`， 第二部分是可播放的`ts`音视频文件
* 我们为什么需要RTMP
  * RMTP使用的是TCP协议做的底层的传输协议，这样就可以保证低延迟，但是RMTP依赖于flash的播放器，但是在浏览器中已经停止了对`flash`的支持，所以浏览器在播放的时候就需要安装其他的插件
  * 其实真正需要的，应该说是可以使用`RTMP server`来播放`HLS`的adaptive streaming的文件，两者是一种平行的关系，都是不同的streaming的协议
    * m3u8文件如何播放
      * 直接使用ffplay
        * `ffplay -v quiet -y 200 <path of the resource>`
      * 浏览器直接播放（html5的video），不需要额外的传输服务器
        * 在本地班playlist的manifest用server host起来，这里我们可以使用node环境的来把本地的某个`path`host起来
          * `npm install http-server -g`
          * host文件夹
            * cd到需要host的path下
              * `cd <path>`
              * `http-server`
            * 直接host需要访问的path
              * `http-server <path>`
          * ![host successfully](https://user-images.githubusercontent.com/6279298/216209862-96b23edb-4a75-4137-8619-ac4976a1d195.png)
          * tips:
            * 必须使用http server，如果直接使用本机的file server是无法工作的
            * `mov`转换成ts文件无法播放，但是MPEG-4的是可以？
      * 搭建RTMP server
        * how to
      * 搭建SRT server
        * how to
