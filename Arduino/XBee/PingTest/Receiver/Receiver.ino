int myData = 0;
int const ledpin = 13;

void setup(){
  // Start up our serial port, we configured our XBEE devices for 9600 bps.
  Serial.begin(115200);
  pinMode(ledpin, OUTPUT);
}

void loop(){
  if(Serial.available()){
    myData = Serial.read();
    if(myData == '1'){
      digitalWrite(ledpin, HIGH);
    } else if(myData == '0') {
      digitalWrite(ledpin, LOW);
    }
  }
}
