package net.bi4vmr.tool.kotlin.media.exif.util

import java.io.File

/**
 * ExifTool 可执行文件工具。
 *
 * 自动侦测可执行文件位置的相关工具。
 *
 * @author bi4vmr@outlook.com
 * @since 1.0.0
 */
expect object ExifToolExecutableUtil {

    /**
     * 获取当前平台可执行文件名称。
     *
     * @return 文件名称。
     */
    fun getExecutableName(): String

    /**
     * 自动侦测可执行文件的位置。
     *
     * 按照以下顺序查找可执行文件：
     *
     * 1. 环境变量 `PATH` 。
     * 2. 常见路径。
     *
     * 通过 CLI 调用本工具时，进程通常能够继承父进程的环境变量；通过 GUI 调用本工具时，系统不会暴露所有环境变量，此时只能遍历常见路径猜测
     * 可执行文件的位置。
     *
     * @return 可执行文件。未找到时将返回空值。
     */
    fun detectExecutable(): File?
}
