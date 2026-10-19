package net.bi4vmr.tool.kotlin.external.adb.ktx

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.bi4vmr.tool.kotlin.external.adb.ADBController
import net.bi4vmr.tool.kotlin.external.adb.ADBEventListener
import net.bi4vmr.tool.kotlin.external.adb.ADBServiceListener
import net.bi4vmr.tool.kotlin.external.adb.model.ADBDevice

/**
 * ADB 相关状态的协程封装。
 */
class ADBObserver {

    /**
     * ADB 服务状态。
     */
    private val _serviceState = MutableStateFlow(false)

    /**
     * ADB 服务状态。
     */
    val serviceState: StateFlow<Boolean> = _serviceState

    /**
     * ADB 设备列表。
     */
    private val _devicesState = MutableStateFlow<List<ADBDevice>>(emptyList())

    /**
     * ADB 设备列表。
     */
    val devicesState: StateFlow<List<ADBDevice>> = _devicesState


    /**
     * 初始化。
     *
     * 开始监听设备变化。
     */
    fun init() {
        ADBController.addServiceListener(serviceListener)
        ADBController.addEventListener(eventListener)
        _serviceState.value = ADBController.isInitialized()
        _devicesState.value = ADBController.getDevices()
    }

    /**
     * 注销。
     *
     * 停止监听设备变化，若要再次监听必须调用 [init] 方法。
     */
    fun terminate() {
        _devicesState.value = emptyList()
        _serviceState.value = false
        ADBController.removeEventListener(eventListener)
        ADBController.removeServiceListener(serviceListener)
    }


    /**
     * ADB 服务状态监听器实现。
     */
    private val serviceListener = object : ADBServiceListener {

        override fun onInitDone(state: Boolean) {
            _serviceState.value = state
        }

        override fun onTerminate() {
            _devicesState.value = emptyList()
            _serviceState.value = false
        }
    }

    /**
     * ADB 设备列表监听器实现。
     */
    private val eventListener = object : ADBEventListener {

        override fun onDeviceListChange(devices: List<ADBDevice>) {
            _devicesState.value = devices
        }
    }
}
