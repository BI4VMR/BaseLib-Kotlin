package net.bi4vmr.tool.kotlin.common.reflect

import net.bi4vmr.tool.java.common.reflect.ReflectUtil
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * JVM反射相关工具。
 *
 * @author bi4vmr@outlook.com
 * @since 1.0.0
 */
object ReflectUtilKT {

    /**
     * 设置 `by lazy {}` 属性的值。
     *
     * 忽略 `lazy {}` 块中的逻辑，强制设置参数所指定的值到属性中，可在单元测试中覆盖某些难以模拟的分支，不建议在业务代码中使用。
     *
     * 本方法默认不处理异常，遇到异常将会传递给调用者。
     *
     * @param[target] 目标对象实例。
     * @param[name]   目标属性名称。
     * @param[value]  属性值。
     * @throws[NoSuchElementException] 若在当前类和父类中均未找到指定的属性，则抛出该异常。
     */
    fun setLazyPropertyValueUnsafe(target: Any, name: String, value: Any?) {
        val property = try {
            // 首先尝试查找当前类的属性
            target::class.declaredMemberProperties.first { it.name == name }
        } catch (e: NoSuchElementException) {
            // 如果当前类没有该属性，则尝试查找从父类继承的属性。
            target::class.memberProperties.first { it.name == name }
        }
        property.isAccessible = true

        // 访问属性的后备字段，取得当前属性的值。
        val jvmField = property.javaField
            ?: throw IllegalArgumentException("Kotlin property [$name] does not have a backing field!")
        val current = jvmField.get(target)

        // 兼容性措施：如果目标属性是一个非Lazy委托属性，尝试直接设置新的值。
        if (current !is Lazy<*>) {
            jvmField.set(target, value)
            return
        }

        /*
         * 如果目标属性是一个Lazy委托属性，需要修改Lazy接口实现类内部的值，通常名称为 `_value` ，外部访问Lazy属性时通过 `value` 属性的
         * Getter方法实际上即访问实现类内部属性的值。
         */
        ReflectUtil.setFieldValueUnsafe(current, "_value", value)
    }

    /**
     * 设置 `by lazy {}` 属性的值。
     *
     * 忽略 `lazy {}` 块中的逻辑，强制设置参数所指定的值到属性中，可在单元测试中覆盖某些难以模拟的分支，不建议在业务代码中使用。
     *
     * 本方法不会抛出异常，如果目标属性不存在或出现其他错误，则忽略这些错误。。
     *
     * @param[target] 目标对象实例。
     * @param[name]   目标属性名称。
     * @param[value]  属性值。
     * @return `true` 表示设置成功， `false` 表示设置失败，有异常出现。
     */
    fun setLazyPropertyValue(target: Any, name: String, value: Any?): Boolean {
        return try {
            setLazyPropertyValueUnsafe(target, name, value)
            true
        } catch (e: Exception) {
            false
        }
    }
}
