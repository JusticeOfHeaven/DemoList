cmake_minimum_required(VERSION 3.4.1)

# 将cpp文件下的所有头文件编译进 SOURCE 目录下
file(GLOB SOURCE src/main/cpp/*.cpp)

add_library( # Sets the name of the library.
        wangyiplayer
        SHARED
        ${SOURCE})

# 引入头文件
include_directories(src/main/cpp/include)
# 定义静态库路径
set(my_lib_path ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI})
# CMAKE_CXX_FLAGS 一个标志位，传递编译路径 ，将libs下的静态文件添加到工程中，这样就能找到avcodec等静态库
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${my_lib_path}")
find_library(
        log-lib
        log )


target_link_libraries(
        wangyiplayer
        avfilter avformat avcodec avutil swresample swscale
        ${log-lib}
        android
        z
        OpenSLES
)