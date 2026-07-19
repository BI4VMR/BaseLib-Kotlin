package net.bi4vmr.tool.kotlin.external.adb.ktx

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.bi4vmr.tool.kotlin.external.adb.ADBController
import net.bi4vmr.tool.kotlin.external.adb.ADBDevice
import net.bi4vmr.tool.kotlin.external.adb.ADBEventListener
import net.bi4vmr.tool.kotlin.external.adb.ADBServiceListener
import net.bi4vmr.tool.kotlin.external.adb.ktx.ADBObserver.init

/**
 * ADB 设备状态的 Flow 封装。
 */
object ADBObserver {

    private val _serviceState = MutableStateFlow(false)

    /**
     * ADB 服务状态。
     */
    val serviceState: StateFlow<Boolean> = _serviceState

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


    private val serviceListener = object : ADBServiceListener {

        override fun onInitDone(state: Boolean) {
            _serviceState.value = state
        }

        override fun onTerminate() {
            _devicesState.value = emptyList()
            _serviceState.value = false
        }
    }

    private val eventListener = object : ADBEventListener {

        override fun onDeviceListChange(devices: List<ADBDevice>) {
            _devicesState.value = devices
        }

        override fun onDeviceConnect(device: ADBDevice) {
            _devicesState.value = ADBController.getDevices()
        }

        override fun onDeviceDisconnect(device: ADBDevice) {
            _devicesState.value = ADBController.getDevices()
        }

        override fun onDeviceStateChange(device: ADBDevice) {
            _devicesState.value = ADBController.getDevices()
        }
    }
}
