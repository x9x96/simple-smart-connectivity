/*
 * Copyright (C) 2024 Simple Android Smart Control
 * by: Israel Oyetunji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {    
	var smartHome = SmartHome(SmartTvDevice("Android TV", "Entertainment"), SmartLightDevice("Google Light", "Utility"))
    
   println("START of running (19) Test cases\n\n")
    
   println("::T_001:: testing on Tv")
   smartHome.turnOnTv()

    println("\n::T_002:: testing printing Smart Tv info")    
    smartHome.printSmartTvInfo()

    println("\n::T_003:: testing Tv INCREASE vol to 4")
    smartHome.increaseTvVolume()
	smartHome.increaseTvVolume()
    
    println("\n::T_004:: testing REDUCE Tv vol to 0")
    smartHome.decreaseTvVolume()
    smartHome.decreaseTvVolume()
    smartHome.decreaseTvVolume()
    smartHome.decreaseTvVolume()
    
    println("\n::T_005:: testing REDUCE Tv vol: should not go below 0")
    smartHome.decreaseTvVolume()
    smartHome.decreaseTvVolume()
    
    println("\n::T_006:: testing NEXT Tv channel to 2")
    smartHome.changeTvChannelToNext()
    println("\n::T_007:: testing PREV Tv channel to 1")
    smartHome.changeTvChannelToPrevious()
    
    println("\n::T_008:: testing off Tv")
    smartHome.turnOffTv()
    println("\n::T_009:: testing Tv vol should not respond because Tv is off")
    smartHome.increaseTvVolume()

    println("\n::T_010:: testing on Light")
    smartHome.turnOnLight()   
    
    println("\n::T_011:: testing printing Smart Light info")    
    smartHome.printSmartLightInfo()    
    
    println("\n::T_012:: testing INCREASE Light brightness to 11")
	smartHome.increaseLightBrightness()

    println("\n::T_013:: testing DECREASE Light brightness to lowest = 2")
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
	smartHome.decreaseLightBrightness()
   smartHome.decreaseLightBrightness()

	println("\n::T_014:: testing DECREASE Light brightness: should not go below 2")    
	smartHome.decreaseLightBrightness()
   smartHome.decreaseLightBrightness()
    
   println("\n::T_015:: testing off Light\n")
   smartHome.turnOffLight()    
   println("\n::T_016:: testing Light brightness should not respond because light is off")
   smartHome.increaseLightBrightness()

    println("\n::: testing on Light AGAIN")
    smartHome.turnOnLight()
    
    println("\n::T_017:: testing to off all when ONLY Light is on")
    smartHome.turnOffAllDevices()

    println("\n::: testing on Tv AGAIN")
    smartHome.turnOnTv()

    println("\n::T_018:: testing to off all when ONLY Tv is on")
    smartHome.turnOffAllDevices()

    println("\n::: testing on Light")
    smartHome.turnOnLight()
    println("::: testing on Tv")
    smartHome.turnOnTv()

    println("\n::T_019:: testing to off BOTH Light AND Tv")
    smartHome.turnOffAllDevices()
    
    println("\n\nEND of running (19) Test cases")    
}
