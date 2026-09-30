package net.bi4vmr.tool.kotlin.common.base

/**
 * 语法扩展工具。
 *
 * @author bi4vmr@outlook.com
 * @since 1.0.0
 */


/*
 * ----- 数据类型转换 -----
 */

/**
 * 将布尔值转为整型值。
 * <p>
 * C语言风格的通用数值转换， `true` 对应 `1` ； `false` 对应 `0` 。
 *
 * @return 输入值为 `true` 时返回 `1` ；否则返回 `0` 。
 */
fun Boolean.toInt(): Int = if (this) 1 else 0

/**
 * 将布尔值转为长整型值。
 * <p>
 * C语言风格的通用数值转换， `true` 对应 `1` ； `false` 对应 `0` 。
 *
 * @return 输入值为 `true` 时返回 `1` ；否则返回 `0` 。
 */
fun Boolean.toLong(): Long = if (this) 1 else 0

/**
 * 将布尔值转为短整型值。
 * <p>
 * C语言风格的通用数值转换， `true` 对应 `1` ； `false` 对应 `0` 。
 *
 * @return 输入值为 `true` 时返回 `1` ；否则返回 `0` 。
 */
fun Boolean.toShort(): Short = if (this) 1 else 0

/**
 * 将布尔值转为字节型值。
 * <p>
 * C语言风格的通用数值转换， `true` 对应 `1` ； `false` 对应 `0` 。
 *
 * @return 输入值为 `true` 时返回 `1` ；否则返回 `0` 。
 */
fun Boolean.toByte(): Byte = if (this) 1 else 0

/**
 * 将整型值转为布尔值。
 * <p>
 * C语言风格的通用数值转换， `1` 对应 `true` ； 其他值对应 `false` 。
 *
 * @return 输入值为 `1` 时返回 `true` ；否则返回 `0` 。
 */
fun Int.toBoolean(): Boolean = (this == 1)

/**
 * 将长整型值转为布尔值。
 * <p>
 * C语言风格的通用数值转换， `1` 对应 `true` ； 其他值对应 `false` 。
 *
 * @return 输入值为 `1` 时返回 `true` ；否则返回 `0` 。
 */
fun Long.toBoolean(): Boolean = (this == 1L)

/**
 * 将短整型值转为布尔值。
 * <p>
 * C语言风格的通用数值转换， `1` 对应 `true` ； 其他值对应 `false` 。
 *
 * @return 输入值为 `1` 时返回 `true` ；否则返回 `0` 。
 */
fun Short.toBoolean(): Boolean {
    val value: Short = 1
    return this == value
}

/**
 * 将字节型值转为布尔值。
 * <p>
 * C语言风格的通用数值转换， `1` 对应 `true` ； 其他值对应 `false` 。
 *
 * @return 输入值为 `1` 时返回 `true` ；否则返回 `0` 。
 */
fun Byte.toBoolean(): Boolean {
    val value: Byte = 1
    return this == value
}
