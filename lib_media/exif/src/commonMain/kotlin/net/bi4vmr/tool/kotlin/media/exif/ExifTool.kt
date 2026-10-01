package net.bi4vmr.tool.kotlin.media.exif

import net.bi4vmr.tool.kotlin.media.exif.ExifTool.deleteTags
import net.bi4vmr.tool.kotlin.media.exif.ExifTool.readTag
import net.bi4vmr.tool.kotlin.media.exif.ExifTool.readTags
import net.bi4vmr.tool.kotlin.media.exif.ExifTool.writeTag
import net.bi4vmr.tool.kotlin.media.exif.ExifTool.writeTags
import net.bi4vmr.tool.kotlin.media.exif.constant.ExifTag
import java.io.File

/**
 * Exif 工具。
 *
 * [ExifTool](https://exiftool.org/) 的命令行封装，支持从图像文件解析或修改 Exif 标签。
 *
 * @author bi4vmr@outlook.com
 * @since 1.0.0
 */
expect object ExifTool {


    /*
     * ----- 读取标签 -----
     */

    /**
     * 读取一组自定义标签。
     *
     * 本方法用于读取工具内置枚举类 [ExifTag] 未包含的标签，对于受支持的标签建议使用 [readTags] 方法。
     *
     * @param[file] 目标文件。
     * @param[tags] 标签名称数组。默认值为空数组。若数组内容为空，则读取所有标签。
     * @param[rawValue] 是否显示原始值。默认值为 `false` 。 `true` 表示以原始格式输出； `false` 表示以人类可读格式输出，具体样式可参考
     * [ExifTag] 中各标签的注释。
     * @return Map 键为标签名称；值为标签的值，当指定的标签不存在时值为空。内部实现为 [LinkedHashMap] ，元素顺序始终与 [tags] 一致。
     * 如果文件或标签不存在，则返回空集合。
     */
    fun readCustomTags(
        file: File,
        tags: Array<String> = emptyArray(),
        rawValue: Boolean = false
    ): Map<String, String?>

    /**
     * 读取一组标签。
     *
     * @param[file] 目标文件。
     * @param[tags] 标签数组。默认值为空数组。若数组内容为空，则读取所有标签。
     * @param[rawValue] 是否显示原始值。默认值为 `false` 。 `true` 表示以原始格式输出； `false` 表示以人类可读格式输出，具体样式可参考
     * [ExifTag] 中各标签的注释。
     * @return Map 键为标签；值为标签的值，当指定的标签不存在时值为空。内部实现为 [LinkedHashMap] ，元素顺序始终与 [tags] 一致。
     * 如果文件或标签不存在，则返回空集合。
     */
    fun readTags(file: File, tags: Array<ExifTag>, rawValue: Boolean = false): Map<ExifTag, String?>

    /**
     * 读取自定义标签。
     *
     * 本方法用于读取工具内置枚举类 [ExifTag] 未包含的标签，对于受支持的标签建议使用 [readTag] 方法。
     *
     * @param[file] 目标文件。
     * @param[tag] 标签名称。
     * @param[rawValue] 是否显示原始值。默认值为 `false` 。 `true` 表示以原始格式输出； `false` 表示以人类可读格式输出，具体样式可参考
     * [ExifTag] 中各标签的注释。
     * @return 标签的值。如果文件或标签不存在，则返回空值。
     */
    fun readCustomTag(file: File, tag: String, rawValue: Boolean = false): String?

    /**
     * 读取标签。
     *
     * @param[file] 目标文件。
     * @param[tag] 标签。
     * @param[rawValue] 是否显示原始值。默认值为 `false` 。 `true` 表示以原始格式输出； `false` 表示以人类可读格式输出，具体样式可参考
     * [ExifTag] 中各标签的注释。
     * @return 标签的值。如果文件或标签不存在，则返回空值。
     */
    fun readTag(file: File, tag: ExifTag, rawValue: Boolean = false): String?

    /**
     * 读取所有标签。
     *
     * @param[file] 目标文件。
     * @param[rawValue] 是否显示原始值。默认值为 `false` 。 `true` 表示以原始格式输出； `false` 表示以人类可读格式输出，具体样式可参考
     * [ExifTag] 中各标签的注释。
     * @return Map 键为标签；值为标签的值。
     */
    fun readAllTags(file: File, rawValue: Boolean = false): Map<String, String>


    /*
     * ----- 写入标签 -----
     */

    /**
     * 写入一组自定义标签。
     *
     * 本方法用于写入工具内置枚举类 [ExifTag] 未包含的标签，对于受支持的标签建议使用 [writeTags] 方法。
     *
     * @param[input] 输入文件或目录。
     * @param[tags] 标签和值。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    fun writeCustomTags(input: File, tags: Map<String, String>, output: File? = null): Boolean

    /**
     * 写入一组标签。
     *
     * @param[input] 输入文件或目录。
     * @param[tags] 标签和值。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    fun writeTags(input: File, tags: Map<ExifTag, String>, output: File? = null): Boolean

    /**
     * 写入自定义标签。
     *
     * 本方法用于写入工具内置枚举类 [ExifTag] 未包含的标签，对于受支持的标签建议使用 [writeTag] 方法。
     *
     * @param[input] 输入文件或目录。
     * @param[tag] 标签名称。
     * @param[value] 标签值。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    fun writeCustomTag(input: File, tag: String, value: String, output: File? = null): Boolean

    /**
     * 写入标签。
     *
     * @param[input] 输入文件或目录。
     * @param[tag] 标签。
     * @param[value] 标签值。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    fun writeTag(input: File, tag: ExifTag, value: String, output: File? = null): Boolean


    /*
     * ----- 移除标签 -----
     */

    /**
     * 移除一组自定义标签。
     *
     * 本方法用于修改工具内置枚举类 [ExifTag] 未包含的标签，对于内置标签建议使用 [deleteTags] 方法。
     *
     * @param[input] 输入文件或目录。
     * @param[tags] 标签数组。若数组为空，则移除所有标签。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    @JvmStatic
    @JvmOverloads
    fun deleteCustomTags(
        input: File,
        tags: Array<String> = emptyArray(),
        output: File? = null
    ): Boolean
    /**
     * 移除一组标签。
     *
     * @param[input] 输入文件或目录。
     * @param[tags] 标签数组。若数组为空，则移除所有标签。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    @JvmStatic
    @JvmOverloads
    fun deleteTags(
        input: File,
        tags: Array<ExifTag> = emptyArray(),
        output: File? = null
    ): Boolean

    /**
     * 移除自定义标签。
     *
     * 本方法用于修改工具内置枚举类 [ExifTag] 未包含的标签，对于内置标签建议使用 [deleteTags] 方法。
     *
     * @param[input] 输入文件或目录。
     * @param[tag] 标签。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    @JvmStatic
    @JvmOverloads
    fun deleteCustomTag(input: File, tag: String, output: File? = null): Boolean

    /**
     * 移除标签。
     *
     * @param[input] 输入文件或目录。
     * @param[tag] 标签数组。若数组为空，则移除所有标签。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    @JvmStatic
    @JvmOverloads
    fun deleteTag(input: File, tag: ExifTag, output: File? = null): Boolean

    /**
     * 清除所有标签。
     *
     * @param[input] 待处理的文件或目录。
     * @param[output] 输出目录。默认值为空。如果为空值表示直接修改原始文件。若输出目录存在同名文件将跳过它们。
     * @return 操作结果。 `true` 表示修改成功； `false` 表示修改失败。
     */
    @JvmStatic
    @JvmOverloads
    fun clearTags(input: File, output: File? = null): Boolean


    /*
     * ----- 可执行文件管理 -----
     */

    /**
     * 是否自动侦测可执行文件位置。
     *
     * @return `true` 表示自动侦测； `false` 表示使用用户指定的路径。
     */
    fun isAutoDetectExecutable(): Boolean

    /**
     * 获取当前 ExifTool 可执行文件。
     *
     * @return 可执行文件。如果当前采用自动侦测但没有找到可执行文件，则返回空值。
     */
    fun getExecutableFile(): File?

    /**
     * 设置 ExifTool 可执行文件路径。
     *
     * @param[path] 可执行文件路径。若为空值则启用自动侦测。
     */
    fun setExecutableFile(path: String? = null)
}
