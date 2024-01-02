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

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 10, minValue = 2, maxValue = 100)

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 10
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }

    fun decreaseBrightness() {
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel.")
    }    
        
    fun printLightInfo() {
        super.printDeviceInfo()
    }
}

class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {

    var tvTurnOnCount = 0
        private set
    
    var lightTurnOnCount = 0
        private set

    //smart TV functions    
    fun turnOnTv() {
        tvTurnOnCount++
        smartTvDevice.turnOn()
    }
    
    fun turnOffTv() {
        tvTurnOnCount--
        smartTvDevice.turnOff()
    }
    
    fun increaseTvVolume(status : Int = tvTurnOnCount) { 
       if (status == 1) {
        	smartTvDevice.increaseSpeakerVolume()
    	}
    }    

    fun decreaseTvVolume(status : Int = tvTurnOnCount) { 
       if (status == 1) {
        	smartTvDevice.decreaseSpeakerVolume()
    	}
    }    
    
    fun changeTvChannelToNext(status : Int = tvTurnOnCount) { 
    	if (status == 1) {
       		smartTvDevice.nextChannel()
        }
    }

    fun changeTvChannelToPrevious(status : Int = tvTurnOnCount) { 
    	if (status == 1) {
       		smartTvDevice.previousChannel()
        }
    }
    
    fun printSmartTvInfo() {
        smartTvDevice.printTVInfo()
    }
    
    //smart light functions
    fun turnOnLight() {
        lightTurnOnCount++
        smartLightDevice.turnOn()
    }

    fun turnOffLight() {
        lightTurnOnCount--
        smartLightDevice.turnOff()
    }

    fun increaseLightBrightness(status : Int = lightTurnOnCount) { 
    	if (status == 1) {
        smartLightDevice.increaseBrightness()
        }
    }

    fun decreaseLightBrightness(status : Int = lightTurnOnCount) { 
    	if (status == 1) {
        smartLightDevice.decreaseBrightness()
        }
    }
    
    fun printSmartLightInfo() {
       smartLightDevice.printLightInfo()
    }    
    
    fun turnOffAllDevices(
        deviceStatus1 : Int = tvTurnOnCount,
		deviceStatus2 : Int = lightTurnOnCount
    ) {
		if (deviceStatus1 == 1) {  
        	turnOffTv()
        }
        if (deviceStatus2 == 1) {  
        	turnOffLight()
        }
    }
}

