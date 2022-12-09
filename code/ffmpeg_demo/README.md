#ffmpeg java demo

* What is ffmpeg
* High frequency using library
  * libavcodec, contains all encoders and decoders, which is used to encode or decode the resource you input
  * libavformat, deal with the <font color=red>**muxers**</font> or <font color=red>**demuxers**</font> for various container. container is a file which is used to save your video, audio streams, and anyclose <font color=red size=3 font=black>what is muxer and demuxer in pic?</font>
  * libavfilter, used to modify audio or video
  * libavdevice, used to support different input and output devices
  * libavutil,
  * libwscale,
  * libswreasample
* ffmpeg package tools
  * ffmpeg, itself is a command line tool which is used to process the video or audio resource
  * ffplay, which is a minimal video player, you can use it play a large wide of video format
  * ffprobe, which is used to extract video or audio information
## How to setup it?
* how to get it?
  * No official builds
  * Build from source code, download it from `github` repo
  * Download Pre-build packages, Use package management tool, like `homebrew` and `APT`
* support OS
  * macOS `brew install ffmpeg`
  * linux 
  * Windows
## How to use its sub-component tool
* ffmprobe
  * When you install the ffmpeg in brew, you can use ffmpeg, ffmprobe or ffmplay directly. For the part, you will use ffmprobe to analyze the video or audio resource.
  * Input the following command, `ffprobe xxxx.mp4`, you will get the following result with log.
    <img width="1293" alt="image" src="https://user-images.githubusercontent.com/96409339/205582566-4f4bdc33-242e-4884-ad27-8b68c20fc433.png">
    * If you don't need any log, you can input the following command, `ffprobe -v error bunny_1080p_60fps.mp4 -show_format`, you will retrieve the whole video format details
    ```xml
    [FORMAT]
    filename=bunny_1080p_60fps.mp4
    nb_streams=3
    nb_programs=0
    format_name=mov,mp4,m4a,3gp,3g2,mj2
    format_long_name=QuickTime / MOV
    start_time=0.000000
    duration=634.533333
    size=355856562
    bit_rate=4486529
    probe_score=100
    TAG:major_brand=isom
    TAG:minor_version=1
    TAG:compatible_brands=isomavc1
    TAG:creation_time=2013-12-16T17:59:32.000000Z
    TAG:title=Big Buck Bunny, Sunflower version
    TAG:artist=Blender Foundation 2008, Janus Bager Kristensen 2013
    TAG:comment=Creative Commons Attribution 3.0 - http://bbb3d.renderfarming.net
    TAG:genre=Animation
    TAG:composer=Sacha Goedegebure
    [/FORMAT]
    ```
    * If you want to know the details of the stream, you can input `ffprobe -v error bunny_1080p_60fps.mp4 -show_format -show_streams`
      * with parameter `-print_format`, you can only <font color=red size=3>**format**</font> output csv or json
      ```json
      {
         "programs": [],
         "streams": [
         {
            "codec_name": "h264"
         },
         {
            "codec_name": "mp3"
         },
         {
            "codec_name": "ac3",
            "side_data_list": [{}]
         }
       ]
      }
      ```
      * with parameter  `-show_stream -select-streams v`, only output video stream
      ```json
       {
         "programs": [],
         "streams": [{
            "codec_name": "h264"}]
       }
      ```
      * with parameter `-show_entries stream=codec_name`, only output codec name, you might need to remove the other parameters like `-show_stream` or `-show_format`
      * with parameter `-print_format default=noprint_wrappers=1`, remove outside tag
      ```text
       codec_name=h264
       codec_name=mp3
       codec_name=ac3
      ```
      * with parameter `-print_format default=noprint_wrappers=1:nokey=1`, you can get the only value without key
      ```text
      h264
      mp3
      ac3
      ```
      * You can analyze the stream information by URL directly without downloading it to loacl machine
      `ffprobe -v error <http url link>`
    * [more details, www.ffmpeg.org/ffprobe.html](https://www.ffmpeg.org/ffprobe.html)
* ffplay
  * with the parameter `-x , -y`, you can control the size of the video window
  * with the parameter `-noborder`, you can remove the border of the video window
  * with the parameter `-top -left`, you can control the position of the video window
  * with the parameter '-fs', you can get the full screen video window
  * with the parameter `-an`, you can disable the audio
  * with the parameter `vn`, you can disable the video
    * tips, you can press key `W` to switch the show-mode
  * with the parameter `-showmode waves`, you can get the video wave
  * with the parameter `-loop <times>`, you can get looping video
  * with pressing key `S`, you can step through the frames of a video one frame at a time
* ffmpeg
  * overview of how to edit media
  ![overview](https://user-images.githubusercontent.com/96409339/206382829-5b872ed9-97a5-4b17-88c6-eae1c76651e4.png)
  * details
    * input & output
      * `ffmpeg -i <protocol>:<device>`, file is default, you also can choose the network pattern, like http, ftp, rtp etc.
      * according to the above processing, you can run the following command like `ffmpeg -i input.mov ...add some filter... output.mp4`
      * with the parameter, `-f extension`, you can do the muxing or demux use the command. During the input process, it was used to demux. During the output process, it was used to mux.
        * `ffmpeg -i input.mov ... -f ... output.extension`
      * with parameter `pipe:0`, don't know very well
      * capture, I guess this can be used to record screen;
        * `ffmpeg -f avfoundation -list_devices true -i ""` list the useful device in the current machine
        * screen
          * `ffmpeg -f avfoundation -i ":1" ....`
        * audio (microphone)
          * `ffmpeg -f avfoundation -i ":2" ....`
        * webcam
          * `ffmpeg -f avfoundation -i "default" ....`
      * testing
        * `ffmpeg -f lavfi -i color=color=red...`, **lavfi** is a virtual space
    * stream selection
      ![the example of streams in container](https://user-images.githubusercontent.com/96409339/206649881-25b41eed-9089-408e-a477-1daebf2695be.png) 
      * For one container, generally speaking, it includes one video stream and one more audios streams
      * how to select a stream, <input-index>:<stream-type>:<stream-index>, these three inputs will control the stream you want to select.
      * `ffmpeg -v error -y -i bunny_1080p_60fps.mp4 -to 1 bunny_1080p_60fps-1s.mp4`, the command is used to edit a video
        * `-y` override the file anyway
        * `-to` cut the video until 1 second
        * For default, ffmpeg only picks up one video and one audio
          * get all, with parameter `-map 0`
          * get video, with parameter `-map 0:v`
          * get audio, with parameter `-map 0:a`
          * get first audio, with parameter `-map 0:a:0`
      * You also can process multiple data together, integration some video, layout(logo), audio together
        * `ffmpeg -v error -y -i bunny_1080p_60fps.mp4 -i bullfinch.mov -to 1 -map 0:v:0 -map 1:a:0 bunny_1080p_60fps-1s-mix.mp4`
## Media Concepts
* image
  * pixel, is a 2D point, has color RGB or YUV, alpha value transparency
  * resolution, the quality of the image of the number of pixel of the image
  * Aspect Ratio, the radio of the width and height
* Audio
  * smallest digital representation of a sound
  * 8/16/24/32 bit value, more bits means high quality video source
  * audio frequency, how many samples during the per second, the higher, the higher quality
  * audio channel, which means that several audios will be played together
    * for example, one audio channel is named 'Mono', two is named 'Stereo' and both of them are called `channel layouts`
  * audio track, organize different audio channels
    * for example, an audio can obtain multilingual channel, an English channel or a French chanel, and also it can take a background chanel, some of them can be mixed
* Video
  * sequence of images, has the time duration, Each image is called a frame
  * Video frame rate(FPS), Frame per second
  * video compression, not very clear about the concept? remove redundant information, Spatial redundancy (**within frame**), Temporal redundancy (**across frame**). <font color=red>**How to make it?**</font>
* Codec & Container
  ![the overview of Codec and Container](https://user-images.githubusercontent.com/96409339/206092175-0a9d1d2c-a173-4407-93a8-7b1e4b306db2.png) 
  * Codec
    * encode, because video is a very big file, if we transfer it directly, which is an impractical idea. and decode is used to play the video
    * some encode algorithm
      * video, H.264, H.265, VP9, Prores, DNxHd, 
      * audio, PCM, AAC, MP3
      * the benefits of different codec algorithm
  * Container
    * use to package or wrapper a media file
    * format
      * video, MP4, MXF, QT/MOV, MKV
      * audio, WAV, M4A
* Transcoding
  * transfer one codec to another one
  * why we need transcode, because it depends on different situation, sometimes, we need take care of quality, being smaller during the transporting in the internet
  * using scenario
    * Transmuxing, From one container to another
    * thumbnail generation, preview image, search hit, hover-scrubbing on the web page, poster frame
    * Frame rate conversion
      * support different TV standards
      * higher FPS: preserve slow motion quality for editing
      * lower FPS: playback or streaming
    * Bitrate conversion
      * quality the higher bitrate the higher quality
      * smaller storage space
      * support different network bandwidth
    * change GOP size
      * good for editing based on I-frame (keyframe) only, 保持一个关键帧，然后可以继续操作，就可以得到动画
      * good for compression and streaming based on long GOP
    * overlay
      * logo ....
    * subtitle/capture
    * timecode
    * covert video resolution
    * Audio volume adjustment
    * Audio mixing eg: mix the person speaking voice and the background voice
    * audio resampling, changing the frequency