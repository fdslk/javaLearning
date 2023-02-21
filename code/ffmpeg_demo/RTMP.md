# Streaming protocol
### 可以用来干什么？
* 这个协议的作用是用来规范流媒体的delivery和ingest（streaming protocol via TCP）
### 协议有哪些？
* 普通的
  * RTMP（real-time message protocol） 
    * RMTP使用的是TCP协议做的底层的传输协议，这样就可以保证低延迟，但是RMTP依赖于flash的播放器，但是在浏览器中已经停止了对`flash`的支持，所以浏览器在播放的时候就需要安装其他的插件
  * SRT
  * HTTP
  * WebRTC
* adaptive streaming protocol
  * HLS HLS是一种stream的解决方案 (streaming protocol based on http)
    * 协议使用包括两部分，第一部分是描述文件，`m3u8`， 第二部分是可播放的`ts`音视频文件
    * 其实真正需要的，应该说是可以使用`RTMP server`来播放`HLS`的adaptive streaming的文件，两者是一种平行的关系，都是不同的streaming的协议
  * DASH
### 为什么不能直接播放流
### 如何使用
* m3u8文件如何播放
  * 直接使用ffplay
    * `ffplay -v quiet -y 200 <path of the playlist resource>`
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
      * 把manifest嵌入到[html](https://github.com/Fdslk/javaLearning/blob/master/code/ffmpeg_demo/src/main/resources/video..html) 中，使用现成的js库播放。或者还可以使用[原生的js代码](https://github.com/Fdslk/javaLearning/blob/master/code/ffmpeg_demo/src/main/resources/adaptive-media-player.html) 处理的web，来播放manifest  
  * 搭建RTMP server
    * 方法一
      * 搭建`Ant Media server`
        * 什么是？
          * is one of the fastest-growing and most popular streaming engines. Ant Media Server supports WebRTC, CMAF, HLS, RTMP, RTSP, and more for your critical business streaming needs
          * 如果是个人用户可以免费使用，但是如果是企业级用户则需要付费
          * 更多的细节部分，大家可以参阅[官网](https://resources.antmedia.io/docs)
        * how to
          * 下载 [ant-media-server](https://github.com/ant-media/Ant-Media-Server/releases/download/ams-v2.5.3/ant-media-server-community-2.5.3.zip) 安装包
          * cd `zip` 包的路径
          * 下载 `ant-media-server` 安装脚本 `wget https://raw.githubusercontent.com/ant-media/Scripts/master/install_ant-media-server.sh && chmod 755 install_ant-media-server.sh`
          * 使用脚本 `sudo ./install_ant-media-server.sh -i <ANT_MEDIA_SERVER_INSTALLATION_FILE>`
            * tips
              * 如果在`mac`上，直接运行 `unzip <ANT_MEDIA_SERVER_INSTALLATION_FILE>`，因为上述的安装脚本是针对于`Ubuntu`的linux系统，对于mac不适用
              * 或者还可以使用[docker](https://github.com/Fdslk/javaLearning/blob/master/code/ffmpeg_demo/Dockerfile)，如果直接使用Java的基镜像时，还需要考虑当前的系统是否被AMS支持
          * 使用
            * 启动 `sudo service antmedia stop`
            * 终止 `sudo service antmedia start`
            * 查看当前`ant media server`的状态 `sudo service antmedia status`
            * tips
              * 如果在Mac上，可以直接进入到安装包中，然后运行 `./start.sh`，需要保证当前系统的java版本为<font size=3 color=blue>**java11**</font>以上
          * 在浏览器中输入`http://SERVERHOST:5080/` ，能够在浏览器中看到以下的界面
            * ![部署成功](https://user-images.githubusercontent.com/6279298/217185934-1d2f733a-f230-42f0-b24b-90d851558267.png)
          * 注册之后，登录到主界面，发布直播流
            * ![主界面](https://user-images.githubusercontent.com/6279298/219321664-d205b8d1-d19b-44bd-a5ca-e9853c9b4300.png)
          * 进入到主界面之后
            * 转播stream resource
              * ![live stream output](https://user-images.githubusercontent.com/6279298/219325653-0d7b411d-6ec5-4191-968a-19ee0fff1914.gif)
              * tips: 使用**QuickTime Player**录屏，同样可以使用FFmpeg将mov文件转换成.gif文件
                * ```text
                  ffmpeg  -i  <input file> \                                                                                       ok  base py  17:32:41
                    -vf "fps=10,scale=320:-1:flags=lanczos,split[s0][s1];[s0]palettegen[p];[s1][p]paletteuse" \
                    -loop 0 output.gif
                  ```
            * 转播playlist
              * ![playlist output](https://user-images.githubusercontent.com/6279298/219523963-2e42d6f5-3cfe-41c4-8b48-4666988da521.gif)
    * 方法二
      * 搭建nginx+RTMP
        * 安装nginx `brew install nginx`
        * 安装nginx rtmp module，`brew install nginx-full --with-rtmp-module`
          * tip
            * 当运行上述命令式报出以下错误时，[`Error: invalid option: --with-rtmp-module`](https://stackoverflow.com/questions/61931028/installing-homebrew-nginx-rtmp-in-macos-movaje-10-14) 可以运行 `brew tap denji/nginx`，然后再安装rtmp module
            * 安装成功之后会有以下提示
              * ```text
                Docroot is: /usr/local/var/www
                The default port has been set in /usr/local/etc/nginx/nginx.conf to 8080 so that
                nginx can run without sudo.
                
                nginx will load all files in /usr/local/etc/nginx/servers/.
                
                - Tips -
                  Run port 80:
                  $ sudo chown root:wheel /usr/local/opt/nginx-full/bin/nginx
                  $ sudo chmod u+s /usr/local/opt/nginx-full/bin/nginx
                  Reload config:
                  $ nginx -s reload
                  Reopen Logfile:
                  $ nginx -s reopen
                  Stop process:
                  $ nginx -s stop
                  Waiting on exit process
                  $ nginx -s quit
                
                To start denji/nginx/nginx-full now and restart at login:
                brew services start denji/nginx/nginx-full
                Or, if you don't want/need a background service you can just run:
                nginx
                ```
        * 使用ffmpeg推流
          * ```
            ffmpeg -v quiet -i "<stream resource locatopm>" \
              -vf "scale=-2:200,drawtext=fontfile='c\:/Windows/Fonts/courbd.ttf':text=RTMP:fontsize=30:x=10:y=20:fontcolor=#000000:box=1:boxborderw=5:boxcolor=#ff888888" \
              -vcodec libx264 -f flv <rtmp server url>
            ```
        * 使用ffplay验证推流成功
          * `ffplay -v quiet <rtmp server url>`
          * ![ffplay play pulling streaming successfully](https://user-images.githubusercontent.com/6279298/220020493-dc16cbc8-2a4a-4ae4-a442-5f4674266d2d.gif)
        * 使用VLC验证推流成功
          * 下载[VLC](https://get.videolan.org/vlc/3.0.18/macosx/vlc-3.0.18-intel64.dmg)
          * ![verify streaming by VLC](https://user-images.githubusercontent.com/6279298/220246480-5f8027db-87e2-490d-8c7e-1acaa4a95dce.gif)
    * 方法三
      * 搭建[rtsp-simple-server](https://github.com/aler9/rtsp-simple-server) 服务器
        * 使用方式
          * 下载[server release package](https://github.com/aler9/rtsp-simple-server/releases) ，unzip该文件包，然后`./rtsp-simple-server`, 就可以使用了
          * 使用docker方式启动
          * OpenWRT
        * 推流
          * `ffmpeg -re -stream_loop -1 -i http://localhost:8080/nature.mp4  -c copy -f rtsp rtsp://localhost:8554/mystream`
        * 验证
          * ffmpeg
          * vlc
  * 搭建SRT server
    * how to
