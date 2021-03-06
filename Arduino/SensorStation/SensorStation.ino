#include "Ultrasonic.h"
#include "PIR.h"
#include "Photoresistor.h"
#include "SensorPacketBuilder.h"
#include <XBee.h>

Ultrasonic ultrasonic(3,2);
PIR pir(8);
Photoresistor photoresistor(1);
SensorPacketBuilder sensorPacketBuilder;

byte buildArray[64];
//XBee radio vars and call back functions
XBeeWithCallbacks xbee;
XBeeAddress64 addr64 = XBeeAddress64(0x0, 0x0);

//uses Printers.h so Serial.print works differently
void zbReceive(ZBRxResponse& rx, uintptr_t) {
  //do something with the data
}
//
void setup()
{
  Serial.begin(9600);
  xbee.setSerial(Serial);
  // Called when an actual packet received
  xbee.onZBRxResponse(zbReceive);
  memset(buildArray, 0, 64);
}

//For debugging prints char in binary
void printbincharpad(char c)
{
  int i;
  for (i = 7; i >= 0; --i)
    {
      Serial.write( (c & (1 << i)) ? '1' : '0' );
    }
  Serial.print('\n');
}

void loop()
{
  // ********** Analog readings *********
  // 32 bit analog
  unsigned long distance = ultrasonic.getDistance();
  // 10 bit analog
  unsigned int lightIntensity = photoresistor.getLightIntensity();

  // ********** digital readings *********
  // digital sensor
  boolean motion = pir.getMotionDetected();

  // packet header
  sensorPacketBuilder.add(2, 3); // numAnalog
  sensorPacketBuilder.add(0, 3); // indexAnalog. No emulatable analog sensor
  sensorPacketBuilder.add(3, 2); // Analog size 1 = 32 bits
  sensorPacketBuilder.add(2, 2);// Analog size 2 = 10 bits
  sensorPacketBuilder.add(1, 4);// num digital
  sensorPacketBuilder.add(0, 4);// index digital. No emulatable digital sensor

  // body
  sensorPacketBuilder.add(distance, 32);// analog val 1 = distance
  sensorPacketBuilder.add(lightIntensity, 10);// analog val 2 = light
  sensorPacketBuilder.add(motion, 1);// digital val 1 = pir

  int packetSize = sensorPacketBuilder.build(buildArray);

  sendData(buildArray, packetSize);

  // Continuously let xbee read packets and call callbacks.
  xbee.loop();
  //act on received data in the call back method zbReceive
  
  memset(buildArray, 0, 64);

}

void sendData(byte*  toSend, int sendLen){
  ZBTxRequest zbTx = ZBTxRequest(addr64, (uint8_t *)toSend, sendLen);
  xbee.sendAndWait(zbTx, 100);
  ZBTxStatusResponse txStatus = ZBTxStatusResponse(); //not sure whether better to have as global or local
  //Re-sends, and forgets, if not succesful
  if(xbee.readPacket()){
    if(xbee.getResponse().getApiId() == ZB_TX_STATUS_RESPONSE) {
      xbee.getResponse().getZBTxStatusResponse(txStatus);
      if (txStatus.getDeliveryStatus() == SUCCESS) {
        return;
      }
    }
  }
  xbee.send(zbTx);
}
