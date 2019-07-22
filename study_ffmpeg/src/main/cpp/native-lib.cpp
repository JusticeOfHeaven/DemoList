#include <jni.h>
#include <string>
#include <unistd.h>
#include <android/native_window_jni.h>
#include <zconf.h>

//混合编译
extern "C" {
//#include <internal.h>
//#include <libavcodec/codec2utils.h>
#include <libavutil/imgutils.h>
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
}
extern "C" JNIEXPORT jstring JNICALL
//com.hello.study_ffmpeg.FFmpegMainActivity
Java_com_hello_study_1ffmpeg_FFmpegMainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(av_version_info());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_hello_study_1ffmpeg_Player_native_1start(JNIEnv *env, jobject instance,
                                                  jstring absolutePath_, jobject surface) {
    const char *absolutePath = env->GetStringUTFChars(absolutePath_, 0);

    // FFmpeg 视频绘制
    //1、初始化网络模块
    avformat_network_init();
    //2、针对当前视频的总上下文
    AVFormatContext *formatContext = avformat_alloc_context();
    //3、解压超时
    AVDictionary *opts = NULL;
    //设置超时时间3秒，最后一个参数位标志位，如果位0，会自动将值添加到前面的字典中
    av_dict_set(&opts, "timeout", "3000000", 0);
    // 返回值，0代表成功
    int result = avformat_open_input(&formatContext, absolutePath, NULL, &opts);
    avformat_find_stream_info(formatContext, NULL);
    if (result) {
        return;
    }
    //4、解析流 通知ffmpeg将流解析出来
    int vidio_stream_idx = -1;
    // 解析出来之后,遍历视频文件有多少个流
    for (int i = 0; i < formatContext->nb_streams; ++i) {
        //AVMEDIA_TYPE_VIDEO:视频流；AVMEDIA_TYPE_AUDIO：音频流；AVMEDIA_TYPE_SUBTITLE：字幕流
        if (formatContext->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            // 记录视频流的索引
            vidio_stream_idx = i;
            break;
        }
    }
    //5、拿视频流的解码参数
    //AVCodecParameters 有对视频流的宽高等参数
    AVCodecParameters *codecpar = formatContext->streams[vidio_stream_idx]->codecpar;

    //获取视频流的解码器
    AVCodec *dec = avcodec_find_decoder(codecpar->codec_id);
    //获取解码器上下文
    AVCodecContext *codecContext = avcodec_alloc_context3(dec);
    //将解码器参数 copy 到解码器上下文
    avcodec_parameters_to_context(codecContext, codecpar);
    //6、开始进行解码 yuv数据
    avcodec_open2(codecContext, dec, NULL);
    // 实例化 AVPacket
    AVPacket *packet = av_packet_alloc();
    //从视频流里面读一个数据包(压缩数据) ，最终得到的frame数据要转换为rgb绘制到屏幕上，需要用Swscontext进行转换
    SwsContext *swsContext = sws_getContext(codecContext->width, codecContext->height,
                                            codecContext->pix_fmt,
                                            codecContext->width, codecContext->height,
                                            AV_PIX_FMT_RGBA, SWS_BILINEAR,
                                            0, 0, 0);


    // 通过底层ANativeWindow进行绘制
    ANativeWindow *nativeWindow = ANativeWindow_fromSurface(env, surface);
    // 对ANativeWindow缓冲区进行设置
    ANativeWindow_setBuffersGeometry(nativeWindow, codecContext->width, codecContext->height,
                                     WINDOW_FORMAT_RGBA_8888);
    //缓冲区所需要的buffer
    ANativeWindow_Buffer outBuffer;
    int frameCount = 0;

    // av_read_frame 返回值小于0，说明读到文件末尾
    while (av_read_frame(formatContext, packet) >= 0) {
        avcodec_send_packet(codecContext, packet);
        // 要及时从队列中取出packet，否则队列满了，会报异常
        AVFrame *frame = av_frame_alloc();
        result = avcodec_receive_frame(codecContext, frame);
        if (result == AVERROR(EAGAIN)) {
            continue;
        } else if (result < 0) {
            // 表明读到末尾流
            break;
        }
        //7、绘制到屏幕上，是rgb到格式
        //接收到容器
        uint8_t *dst_data[0];
        //每一行到首地址
        int dst_linesize[0];
        av_image_alloc(dst_data, dst_linesize, codecContext->width, codecContext->height,
                       AV_PIX_FMT_RGBA, 1);
        if (packet->stream_index == vidio_stream_idx) {

            if (result == 0) {
                // 加锁   ARect 对当前对window进行限制
                ANativeWindow_lock(nativeWindow, &outBuffer, NULL);
                //绘制
                sws_scale(swsContext, reinterpret_cast<const uint8_t *const *>(frame->data), frame->linesize, 0, frame->height, dst_data,
                          dst_linesize);

                uint8_t *dst = (uint8_t *)(outBuffer.bits);
                // 拿到一行有多少个字节 RGBA
                int32_t destStride = outBuffer.stride * 4;
                int src_linesize = dst_linesize[0];
                uint8_t *firstWindow = static_cast<uint8_t *>(outBuffer.bits);
                // 输入源
                uint8_t *src_data = dst_data[0];
                for (int i = 0; i < outBuffer.height; ++i) {
                    // 内存拷贝进行渲染
                    memcpy(firstWindow + i * destStride, src_data + i * src_linesize, destStride);
                }

                // 解锁
                ANativeWindow_unlockAndPost(nativeWindow);
                usleep(1000 * 16);
                av_frame_free(&frame);
            }
            
        }
    }
    ANativeWindow_release(nativeWindow);
    avcodec_close(codecContext);
    avformat_free_context(formatContext);
    env->ReleaseStringUTFChars(absolutePath_, absolutePath);
}