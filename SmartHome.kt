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

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
    
    fun increaseSpeakerVolume() {
        speakerVolume++
        println("$name speaker volume increased to $speakerVolume.")
    }
    
    fun decreaseSpeakerVolume() {
        speakerVolume--
        println("$name speaker volume decreased to $speakerVolume")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel increased to $channelNumber.")
    }

    fun previousChannel() {
        channelNumber--
        println("Channnel reduced to $channelNumber")
    }        
    
    fun printTVInfo() {
	    super.printDeviceInfo()
    }
}
