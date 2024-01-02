/*
 * Copyright (C) 2024 Simple Android Smart Control
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 */


open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "online"
        protected set

    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }
    
    open fun printDeviceInfo() {
        println("\tDevice Info::: \nName: $name\nCategory: $category\nDevice: $deviceType")
    }
}

