//#define LED1 2
//#define LED2 3
//#define LED3 4
int segundo = 0;
String serialIn = "";
int nextNotification = 0;
int actualPin = 0;

boolean accion = false;

String activo = "";

void setup() {
  Serial.begin(9600);
  nextNotification = 0;
  pinMode(13,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(11,OUTPUT);
  pinMode(7,OUTPUT);
  pinMode(6,OUTPUT);
  pinMode(5,OUTPUT);
  
}

void loop() {

if(accion){
  digitalWrite(actualPin,LOW);
  digitalWrite(7,LOW);
  
   if(activo == "1"){
    actualPin = 11;
    digitalWrite(11,HIGH);
    digitalWrite(7,HIGH);
  }else if(activo == "2"){
     actualPin = 12;
    digitalWrite(12,HIGH);
    digitalWrite(7,HIGH);
  }else if(activo == "3"){
     actualPin = 13;
     digitalWrite(13,HIGH);
     digitalWrite(7,HIGH);
  }else{
    
  }
  accion = false;
  
  }

 /*
 digitalWrite(13,LOW);
 digitalWrite(12,LOW);
 digitalWrite(11,LOW);
 digitalWrite(7,LOW);
  */

  delay(500);
  
}

void serialEvent() {
  while (Serial.available()) {
   
    serialIn = Serial.readString();
    activo = serialIn;
    accion = true;
   
  }
}
