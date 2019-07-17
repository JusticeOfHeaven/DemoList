# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
# 指定cmake最低支持的版本
cmake_minimum_required(VERSION 3.4.1)

# 查找指定目录下所有源文件，并将源文件名称列表保存到 DIR_SRCS 变量
# 不能查找子目录
# aux_source_directory(. DIR_SRCS)

# add_library 添加一个库、或者导入一个预编译的库
# 1、添加库：
# * 添加一个库文件，名为<name>
# * 指定STATIC,SHARED,MODULE参数来指定库的类型
#       STATIC---静态库   SHARED--动态库   MODULE--在使用dyld的系统有效，若不支持，等同于SHARED
# * EXCLUDE_FROM_ALL 表示该库不会被默认构建
# * source1 source2 ... sourceN  用来指定库的源文件
# 命令：add_library(<name> [STATIC | SHARED | MODULE] [EXCLUDE_FROM_ALL] source1 source2 ... sourceN)
#
# 2、导入一个预编译库
# * 添加一个已存在的预编译库，名为<name>
# * 一般配合set_target_properties 使用
# 命令：add_library(<name> <SHARED|STATIC|MODULE|UNKNOW> IMPORTED)
# 比如：add_library(test SHARED IMPORTED)
#       set_target_properties(
#           test #指明目标库名
#           PROPERTIES IMPORTED_LOCATION #指明要设置的参数
#           库路径/${ANDROID_ABI}/libtest.so #导入库的路径
#       )
add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             native-lib.cpp )

# 添加可执行文件
# add_executable(<name> ${SRC_LIST})
# 添加编译参数，为当前路径以及子目录的源文件加入由-D 引入的define flag
# add_definitions(-DFOO -DDEbug ...)
# 如果当前目录下还有子目录时可以使用 add_subdirectory()，子目录中也需要包含有CMakeLists.txt
# add_subdirectory(sub_dir [binary_dir])
# 设置某个路径的属性
# set_directory_properties(PROPERTIES prop1 value1 prop2 value2)
# 在给定的作用域内设置一个命名属性
# set_property(<GLOBAL|
#               DIRECTTORY [dir] |
#               TARGET [target ...] |
#               SOURCE [src1 ...] |
#               TEST [test1 ...] |
#               CACHE [entry1 ...]>
#               [APPEND]
#               PROPERTY <name> [value ...])

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
# 将若干库链接到库文件
target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )