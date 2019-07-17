# 定义模块的当前路径，固定写法
LOCAL_PATH := $(call my-dir)

# 清空当前环境变量
include $(CLEAR_VARS)

# 当前模块名
LOCAL_MODULE := hello-jni

# 当前模块包含的源代码文件,
# 如果有多个源文件，就空格隔开
LOCAL_SRC_FILES := hello-jni.c

# 生成一个动态库
include $(BUILD_SHARED_LIBRARY)