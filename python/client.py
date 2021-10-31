from datetime import datetime
from base64 import urlsafe_b64decode, urlsafe_b64encode

import requests
import threading
import json
import uuid
import hmac
import hashlib

url = "services.interintel.co"
username = '<<USERNAME>>'
password = '<<PASSWORD>>'
api_key = '<<API_KEY>>'
ip_address = '<<YOUR HOST IP ADDRESS (ipv4|ipv6)>>'

def sendSmsMessages(msid, message='' , code='', keyword="",linkid="",transid="", schedule=""):
    current_timestamp = str(datetime.now().timestamp())
    credentials = {
        'username': username,
        'password': password,
    }

    payload = {
        'CHID': 13,
        'timestamp': current_timestamp,
        'ip_address': ip_address,
        'gateway_host': url,
        'lat': 0.0,
        'lng': 0.0,
        'credentials': credentials,
        'MSISDN': msid,
        'message': message,
        'alias': code,
        'linkid': linkid,
        'ext_outbound_id': transid,
        'keyword': keyword,
        'scheduled_send': schedule,
    }
    
    result = createSecurityToken(payload, api_key)
    response = make_sms_request(result, 'SEND%20SMS');
    print(response)


def createSecurityToken(payload, api_key):
    new_payload = {}
    payload_list = []
    for key ,value in payload.items():
        if key != 'sec_hash' and key != 'credentials' and type(value) is not list and type(value) is not dict:
            new_payload[key.lower()] = value

    sorted_dict = dict(sorted(new_payload.items(), key=lambda item: item))

    for key, value in sorted_dict.items():
        p_item = key + '=' + str(value)
        payload_list.append(p_item)
    
    final_payload = '&'.join(payload_list).encode('utf-8')
    
    try:
        secret_key = urlsafe_b64decode(api_key)

        dig = hmac.new(secret_key, msg=final_payload, digestmod=hashlib.sha256).digest()
        payload['sec_hash'] = urlsafe_b64encode(dig).decode("ascii")
        return payload
    except Exception as e:
        print(e)
        

def make_sms_request(payload, service):
    data = json.dumps(payload, indent=4, sort_keys=True, default=str)
    items_count = str(len(payload))
    base_url = f'https://{url}/api/{service}/'
    headers =  {
        'Content-Type: application/json',
		'Content-Length: ' + items_count
    }

    response = requests.post(base_url, data, headers)
    return response.json()

sendSmsMessages('+2547XXXXXXXX', "Hello. This is a test SMS from InterIntel. Thank you","TEST")