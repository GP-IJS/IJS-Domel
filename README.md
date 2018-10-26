# IJS-Domel

## Pošiljanje zahtev

Zahtevek:
Tip: POST
Naslov: http://address/RequestServer/RequestController
Atributi:
  - Content-Type: application/json
  - Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQx

Vsebina:
```json
{
    "p1":0.3234124,
    "p2":0.32552,
    "p3":0.8232254,
    "p4":0.48578306998502985,
    "p5":0.42394658961714515,
    "p6":0.62344,
    "p7":0.523,
    "p8":0.66224,
    "p9":0.6532,
    "p10":0.44244,
    "p11":0.874235,
    "p12":0.47541547411894813,
    "p13":0.62435,
    "p14":"0.3",
    "p15":0.52343,
    "p16":0.42342
}
```

Odgovor:
```json
{
    "id": "ea02045e-48ab-49f0-8725-f61caa048689",
    "in_queue": 3
}
```

## Branje rezultatov

Zahtevek:
Tip: GET
Naslov: http://address/RequestServer/ResultController?requestID=ea02045e-48ab-49f0-8725-f61caa048689
Atributi:
  - Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQx

Vsebina:
//

Odgovor:

```json
{
    "requestID": "ea02045e-48ab-49f0-8725-f61caa048689",
    "status": "Success",
    "parameters": {
        "p1": 0.3234124,
        "p2": 0.32552,
        "p3": 0.8232254,
        "p4": 0.48578306998502985,
        "p5": 0.42394658961714515,
        "p6": 0.62344,
        "p7": 0.523,
        "p8": 0.66224,
        "p9": 0.6532,
        "p10": 0.44244,
        "p12": 0.954225,
        "p11": 0.874235,
        "p14": "0.",
        "p13": 0.62435,
        "p16": 0.42342,
        "p15": 0.52343
    },
    "result": {
        "o1": 0.3,
        "o2": 0.0328323269779,
        "o3": 0.043390846111499994,
        "o4": 0.013576988672500002,
        "o5": 0.005901437405799999,
        "o6": 0.0905787135774,
        "o7": 0.130414668,
        "o8": 0.006150589936910001
    },
    "in_queue": 0,
    "arrival_date": "Oct 26, 2018 9:22:21 AM",
    "finished_date": "Oct 26, 2018 9:38:50 AM",
    "started_date": "Oct 26, 2018 9:38:20 AM",
    "processing_time": 30
}
```

## Vse zahteve uporabnika

Zahtevek:
Tip: GET
Naslov: http://address/RequestServer/UserRequestsController
Atributi:
  - Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQx

Vsebina:
//

Odgovor:
```json
[
    {
        "requestID": "57694246-7eb6-4e82-811a-da1354fcb0ce",
        "status": "Success",
        "parameters": {
            "p1": 0.5214,
            "...":"...",
            "p16": 0.5145
        },
        "result": {
            "o1": 0.3,
            "...":"...",
            "o8": 0.006150589936910001
        },
        "in_queue": -1,
        "arrival_date": "Oct 17, 2018 1:13:15 PM",
        "finished_date": "Oct 17, 2018 1:23:13 PM",
        "started_date": "Oct 17, 2018 1:22:43 PM",
        "processing_time": 30
    },
    {
        "requestID": "9af33451-d96d-4cb2-b165-9a9b9781a5b0",
        "status": "Success",
        "parameters": {
            "p1": 0.5214,
            "...":"...",
            "p16": 0.42342,
        },
        "result": {
            "o1": 0.3,
            "...":"...",
            "o8": 0.006150589936910001
        },
        "in_queue": -1,
        "arrival_date": "Oct 17, 2018 12:52:31 PM",
        "finished_date": "Oct 17, 2018 1:22:33 PM",
        "started_date": "Oct 17, 2018 1:22:03 PM",
        "processing_time": 30
    },
    {
        "requestID": "9af3a832-ce17-4e5d-896e-482535b5c4ca",
        "status": "Success",
        "parameters": {
            "p1": 0.5214,
            "...":"...",
            "p16": 0.42342,
        },
        "result": {
            "o1": 0.3,
            "...":"...",
            "o8": 0.006150589936910001
        },
        "in_queue": -1,
        "arrival_date": "Oct 17, 2018 12:52:35 PM",
        "finished_date": "Oct 17, 2018 1:23:13 PM",
        "started_date": "Oct 17, 2018 1:22:43 PM",
        "processing_time": 30
    },
    
]
```

## Status strežnika

Zahtevek:
Tip: GET
Naslov: http://address/RequestServer/StatusController
Atributi:
  - Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQx

Vsebina:
//

Odgovor:
```json
{
    "requests": {
        "Success": 6
    },
    "server_online": true
}
```


